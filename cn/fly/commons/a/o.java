package cn.fly.commons.a;

import android.text.TextUtils;
import cn.fly.commons.C0041r;
import cn.fly.commons.ad;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.k;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class o extends c {
    private volatile long c;
    private volatile AtomicInteger d;

    public o() {
        super(cn.fly.commons.o.a("002Afg;g"), 0L, cn.fly.commons.o.a("004]fgWgJfidj"), 300L, a(cn.fly.commons.o.a("002Afg;g"), (Long) 0L));
        this.c = 0L;
        this.d = new AtomicInteger(0);
    }

    @Override // cn.fly.commons.a.c
    protected void d() {
        cn.fly.tools.utils.k.a().a(getClass().getName(), new k.a() { // from class: cn.fly.commons.a.o.1
            @Override // cn.fly.tools.utils.k.a
            public void a() {
                if (o.this.f()) {
                    try {
                        long jCurrentTimeMillis = System.currentTimeMillis() - o.this.c;
                        long jIntValue = ((Integer) cn.fly.commons.c.a("wsct", 300)).intValue() * 1000;
                        if (jCurrentTimeMillis >= jIntValue) {
                            o.this.n();
                        } else if (o.this.d.get() == 0) {
                            o.this.d.getAndSet(1);
                            o.this.a((Object) true);
                            d.a().a(o.this, (jIntValue - jCurrentTimeMillis) / 1000, 0);
                        }
                    } catch (Throwable th) {
                        FlyLog.getInstance().d(th);
                    }
                }
            }
        });
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        if (this.b != null && (this.b instanceof Boolean) && ((Boolean) this.b).booleanValue()) {
            this.d.set(0);
        }
        n();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        this.c = System.currentTimeMillis();
        C0041r.a(new cn.fly.tools.utils.d<ArrayList<HashMap<String, Object>>>() { // from class: cn.fly.commons.a.o.2
            @Override // cn.fly.tools.utils.d
            public void a(ArrayList<HashMap<String, Object>> arrayList) {
                if (arrayList != null) {
                    try {
                        if (!arrayList.isEmpty()) {
                            ArrayList arrayList2 = new ArrayList();
                            Iterator<HashMap<String, Object>> it = arrayList.iterator();
                            while (it.hasNext()) {
                                Object obj = it.next().get(cn.fly.commons.o.a("0059fjeleleefl"));
                                if (obj != null) {
                                    arrayList2.add(String.valueOf(obj));
                                }
                            }
                            Collections.sort(arrayList2);
                            String strMD5 = Data.MD5(TextUtils.join("", arrayList2));
                            String strB = ad.b().b(ad.j, (String) null);
                            long jCurrentTimeMillis = System.currentTimeMillis();
                            long jB = ad.b().b(ad.k, 0L);
                            long jIntValue = ((Integer) o.this.a(cn.fly.commons.o.a("005Pfg1gXejBdj"), (String) 7200)).intValue() * 1000;
                            if (strB == null || !strB.equals(strMD5) || jCurrentTimeMillis - jIntValue >= jB) {
                                o.this.a(0L, "WLMT", (Object) arrayList, true);
                                ad.b().a(ad.j, strMD5);
                                ad.b().a(ad.k, jCurrentTimeMillis);
                            }
                        }
                    } catch (Throwable th) {
                        FlyLog.getInstance().w(th);
                    }
                }
            }
        });
    }
}