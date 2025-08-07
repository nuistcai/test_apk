package cn.fly.commons.a;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.ad;
import cn.fly.tools.FlyLog;
import cn.fly.tools.b.h;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class f extends c {
    private static volatile a c;

    public f() {
        super(cn.fly.commons.n.a("002FbiPe"), 0L, cn.fly.commons.n.a("006)bi=eHch7bhf"), 60L, a(cn.fly.commons.n.a("002FbiPe"), (Long) 0L));
    }

    @Override // cn.fly.commons.a.c
    protected void d() {
        if (c == null) {
            c = new a();
        }
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        HashMap<String, Object> map;
        if (this.b != null) {
            FlyLog.getInstance().d("[cl] paramObj not null", new Object[0]);
            Object obj = this.b;
            ArrayList arrayList = new ArrayList();
            arrayList.add(obj);
            List<HashMap<String, Object>> listA = a((List) arrayList);
            if (listA != null && !listA.isEmpty() && (map = listA.get(listA.size() - 1)) != null && !map.isEmpty()) {
                map.put("pt", 4);
                a("O_LCMT", map);
                return;
            }
            return;
        }
        n();
    }

    private void n() {
        DH.requester(FlySDK.getContext()).getPosCommForce(0, 0, true, false).getMbcdi().getMcdi().request(new DH.DHResponder() { // from class: cn.fly.commons.a.f.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                List<HashMap<String, Object>> listA;
                HashMap map;
                JSONObject jSONObject;
                if (dHResponse.getPosCommForce(new int[0]) != null && !dHResponse.getPosCommForce(new int[0]).isEmpty() && (listA = f.this.a(dHResponse.getPosCommForce(new int[0]))) != null && !listA.isEmpty()) {
                    int i = 1;
                    HashMap<String, Object> map2 = listA.get(listA.size() - 1);
                    if (map2 != null && !map2.isEmpty()) {
                        f.this.a(map2, map2);
                        if (map2.get("nl") == null) {
                            map = null;
                        } else {
                            map = (HashMap) map2.get("nl");
                        }
                        String mcdi = dHResponse.getMcdi();
                        String mbcdi = dHResponse.getMbcdi();
                        if (!TextUtils.isEmpty(mbcdi)) {
                            map2.put("cbsmt", mbcdi);
                        }
                        if (!TextUtils.isEmpty(mcdi)) {
                            map2.put("cssmt", mcdi);
                        }
                        if (map != null && !map.isEmpty()) {
                            TreeMap treeMap = new TreeMap();
                            treeMap.put("ltdmt", map.get("ltdmt"));
                            treeMap.put("lndmt", map.get("lndmt"));
                            jSONObject = new JSONObject(treeMap);
                        } else {
                            jSONObject = new JSONObject(f.this.b(dHResponse.getPosCommForce(new int[0]).get(listA.size() - 1)));
                        }
                        String strMD5 = Data.MD5(jSONObject.toString());
                        String strB = ad.b().b(ad.g, (String) null);
                        long jB = ad.b().b(ad.h, 0L);
                        long jLongValue = ((Long) f.this.a(cn.fly.commons.n.a("006>bi%e8chLbhe"), (String) 3600L)).longValue() * 1000;
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (TextUtils.isEmpty(strB) || !strB.equals(strMD5) || jCurrentTimeMillis - jB >= jLongValue) {
                            if (!f.this.h()) {
                                if (jCurrentTimeMillis - jB >= jLongValue) {
                                    i = 2;
                                } else {
                                    i = 3;
                                }
                            }
                            map2.put("pt", Integer.valueOf(i));
                            if (map != null && !map.isEmpty()) {
                                map.put("pt", Integer.valueOf(i));
                            }
                            f.this.a("O_LCMT", map2);
                            ad.b().a(ad.g, strMD5);
                            ad.b().a(ad.h, jCurrentTimeMillis);
                        }
                    }
                }
            }
        });
    }

    public class a {
        private long b;
        private long c;
        private AtomicInteger d;

        private a() {
            this.d = new AtomicInteger(0);
            cn.fly.tools.utils.e.a().a(new e.a() { // from class: cn.fly.commons.a.f.a.1
                @Override // cn.fly.tools.utils.e.a
                public void a() {
                    if (cn.fly.commons.c.c()) {
                        if (System.currentTimeMillis() - a.this.b >= ((Integer) cn.fly.commons.c.a("gpdi", 120)).intValue() * 1000) {
                            FlyLog.getInstance().d("[cl] tme > ", new Object[0]);
                            a.this.a();
                            a.this.b = System.currentTimeMillis();
                        }
                        a.this.b();
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            this.d.getAndSet(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            if (this.d.get() < 3 && System.currentTimeMillis() - this.c >= ((Integer) cn.fly.commons.c.a("gpdi", 120)).intValue() * 1000) {
                c();
            }
        }

        private void c() {
            float fA;
            Object objC = cn.fly.tools.utils.e.a().c();
            this.d.getAndIncrement();
            this.c = System.currentTimeMillis();
            Object objB = cn.fly.tools.utils.e.a().b();
            if (objC != null && objB != null) {
                try {
                    fA = new h.a(objC).a(objB);
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                    return;
                }
            } else {
                fA = 0.0f;
            }
            if (objC != null) {
                if (objB == null || fA > ((Float) cn.fly.commons.c.a("gped", Float.valueOf(10.0f))).floatValue()) {
                    FlyLog.getInstance().d("[cl] cur != las", new Object[0]);
                    cn.fly.tools.utils.e.a().a(objC);
                    f.this.a(objC);
                    d.a().a(f.this, 0L, 0);
                }
            }
        }
    }
}