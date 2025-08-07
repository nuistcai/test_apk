package com.tencent.bugly.crashreport.common.strategy;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.biz.b;
import com.tencent.bugly.proguard.as;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class a {
    public static int a = 1000;
    private static a b = null;
    private static String h = null;
    private final List<com.tencent.bugly.a> c;
    private Context g;
    private StrategyBean f = null;
    private final StrategyBean e = new StrategyBean();
    private final w d = w.a();

    private a(Context context, List<com.tencent.bugly.a> list) {
        this.g = context;
        this.c = list;
    }

    public static synchronized a a(Context context, List<com.tencent.bugly.a> list) {
        if (b == null) {
            b = new a(context, list);
        }
        return b;
    }

    public final void a(long j) {
        this.d.a(new Thread() { // from class: com.tencent.bugly.crashreport.common.strategy.a.1
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                try {
                    Map<String, byte[]> mapA = p.a().a(a.a, (o) null, true);
                    if (mapA != null) {
                        byte[] bArr = mapA.get("device");
                        byte[] bArr2 = mapA.get("gateway");
                        if (bArr != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(a.this.g).e(new String(bArr));
                        }
                        if (bArr2 != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(a.this.g).d(new String(bArr2));
                        }
                    }
                    a.this.f = a.d();
                    if (a.this.f != null) {
                        if (!z.a(a.h) && z.c(a.h)) {
                            a.this.f.r = a.h;
                            a.this.f.s = a.h;
                        } else if (com.tencent.bugly.crashreport.common.info.a.b() == null || !"oversea".equals(com.tencent.bugly.crashreport.common.info.a.b().z)) {
                            if (TextUtils.isEmpty(a.this.f.r)) {
                                a.this.f.r = StrategyBean.b;
                            }
                            if (TextUtils.isEmpty(a.this.f.s)) {
                                a.this.f.s = StrategyBean.c;
                            }
                        } else if ("http://android.bugly.qq.com/rqd/async".equals(a.this.f.s) || "http://aexception.bugly.qq.com:8012/rqd/async".equals(a.this.f.s) || TextUtils.isEmpty(a.this.f.s)) {
                            a.this.f.r = StrategyBean.b;
                            a.this.f.s = StrategyBean.c;
                        }
                    }
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
                a.this.a(a.this.f, false);
            }
        }, j);
    }

    public static synchronized a a() {
        return b;
    }

    public final synchronized boolean b() {
        return this.f != null;
    }

    public final StrategyBean c() {
        if (this.f != null) {
            if (!z.c(this.f.r)) {
                this.f.r = StrategyBean.b;
            }
            if (!z.c(this.f.s)) {
                this.f.s = StrategyBean.c;
            }
            return this.f;
        }
        if (!z.a(h) && z.c(h)) {
            this.e.r = h;
            this.e.s = h;
        }
        return this.e;
    }

    protected final void a(StrategyBean strategyBean, boolean z) {
        x.c("[Strategy] Notify %s", b.class.getName());
        b.a(strategyBean, z);
        for (com.tencent.bugly.a aVar : this.c) {
            try {
                x.c("[Strategy] Notify %s", aVar.getClass().getName());
                aVar.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    public static void a(String str) {
        if (z.a(str) || !z.c(str)) {
            x.d("URL user set is invalid.", new Object[0]);
        } else {
            h = str;
        }
    }

    public final void a(as asVar) throws NumberFormatException {
        if (asVar == null) {
            return;
        }
        if (this.f != null && asVar.h == this.f.p) {
            return;
        }
        StrategyBean strategyBean = new StrategyBean();
        strategyBean.g = asVar.a;
        strategyBean.i = asVar.c;
        strategyBean.h = asVar.b;
        if (z.a(h) || !z.c(h)) {
            if (z.c(asVar.d)) {
                x.c("[Strategy] Upload url changes to %s", asVar.d);
                strategyBean.r = asVar.d;
            }
            if (z.c(asVar.e)) {
                x.c("[Strategy] Exception upload url changes to %s", asVar.e);
                strategyBean.s = asVar.e;
            }
        }
        if (asVar.f != null && !z.a(asVar.f.a)) {
            strategyBean.u = asVar.f.a;
        }
        if (asVar.h != 0) {
            strategyBean.p = asVar.h;
        }
        if (asVar.g != null && asVar.g.size() > 0) {
            strategyBean.v = asVar.g;
            String str = asVar.g.get("B11");
            if (str != null && str.equals("1")) {
                strategyBean.j = true;
            } else {
                strategyBean.j = false;
            }
            String str2 = asVar.g.get("B3");
            if (str2 != null) {
                strategyBean.y = Long.valueOf(str2).longValue();
            }
            strategyBean.q = asVar.i;
            strategyBean.x = asVar.i;
            String str3 = asVar.g.get("B27");
            if (str3 != null && str3.length() > 0) {
                try {
                    int i = Integer.parseInt(str3);
                    if (i > 0) {
                        strategyBean.w = i;
                    }
                } catch (Exception e) {
                    if (!x.a(e)) {
                        e.printStackTrace();
                    }
                }
            }
            String str4 = asVar.g.get("B25");
            if (str4 != null && str4.equals("1")) {
                strategyBean.l = true;
            } else {
                strategyBean.l = false;
            }
        }
        x.a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean.g), Boolean.valueOf(strategyBean.i), Boolean.valueOf(strategyBean.h), Boolean.valueOf(strategyBean.j), Boolean.valueOf(strategyBean.k), Boolean.valueOf(strategyBean.n), Boolean.valueOf(strategyBean.o), Long.valueOf(strategyBean.q), Boolean.valueOf(strategyBean.l), Long.valueOf(strategyBean.p));
        this.f = strategyBean;
        if (!z.c(asVar.d)) {
            x.c("[Strategy] download url is null", new Object[0]);
            this.f.r = "";
        }
        if (!z.c(asVar.e)) {
            x.c("[Strategy] download crashurl is null", new Object[0]);
            this.f.s = "";
        }
        p.a().b(2);
        r rVar = new r();
        rVar.b = 2;
        rVar.a = strategyBean.e;
        rVar.e = strategyBean.f;
        rVar.g = z.a(strategyBean);
        p.a().a(rVar);
        a(strategyBean, true);
    }

    public static StrategyBean d() {
        List<r> listA = p.a().a(2);
        if (listA != null && listA.size() > 0) {
            r rVar = listA.get(0);
            if (rVar.g != null) {
                return (StrategyBean) z.a(rVar.g, StrategyBean.CREATOR);
            }
            return null;
        }
        return null;
    }
}