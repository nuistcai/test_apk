package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import com.tencent.bugly.proguard.z;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class d {
    private static d a = null;
    private com.tencent.bugly.crashreport.common.strategy.a b;
    private com.tencent.bugly.crashreport.common.info.a c;
    private b d;
    private Context e;

    static /* synthetic */ void a(d dVar) {
        x.c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class<?> cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            dVar.c.getClass();
            z.a(cls, "sdkPackageName", "".equals("") ? "com.tencent.bugly" : "com.tencent.bugly.", (Object) null);
            x.c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable th) {
            x.a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    static /* synthetic */ void a(d dVar, Thread thread, int i, String str, String str2, String str3, Map map) {
        String str4;
        String str5;
        String str6;
        int i2 = i;
        Thread threadCurrentThread = thread == null ? Thread.currentThread() : thread;
        switch (i2) {
            case 4:
                str4 = "Unity";
                break;
            case 5:
            case 6:
                str4 = "Cocos";
                break;
            case 7:
            default:
                x.d("[ExtraCrashManager] Unknown extra crash type: %d", Integer.valueOf(i));
                return;
            case 8:
                str4 = "H5";
                break;
        }
        x.e("[ExtraCrashManager] %s Crash Happen", str4);
        try {
            if (!dVar.b.b()) {
                x.d("[ExtraCrashManager] There is no remote strategy, but still store it.", new Object[0]);
            }
            StrategyBean strategyBeanC = dVar.b.c();
            if (strategyBeanC.g || !dVar.b.b()) {
                switch (i2) {
                    case 5:
                    case 6:
                        if (!strategyBeanC.l) {
                            x.e("[ExtraCrashManager] %s report is disabled.", str4);
                            x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                            return;
                        }
                        break;
                    case 8:
                        if (!strategyBeanC.m) {
                            x.e("[ExtraCrashManager] %s report is disabled.", str4);
                            x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                            return;
                        }
                        break;
                }
                if (i2 == 8) {
                    i2 = 5;
                }
                CrashDetailBean crashDetailBean = new CrashDetailBean();
                crashDetailBean.C = com.tencent.bugly.crashreport.common.info.b.k();
                crashDetailBean.D = com.tencent.bugly.crashreport.common.info.b.i();
                crashDetailBean.E = com.tencent.bugly.crashreport.common.info.b.m();
                crashDetailBean.F = dVar.c.p();
                crashDetailBean.G = dVar.c.o();
                crashDetailBean.H = dVar.c.q();
                crashDetailBean.w = z.a(dVar.e, c.e, (String) null);
                crashDetailBean.b = i2;
                crashDetailBean.e = dVar.c.h();
                crashDetailBean.f = dVar.c.k;
                crashDetailBean.g = dVar.c.w();
                crashDetailBean.m = dVar.c.g();
                crashDetailBean.n = str;
                crashDetailBean.o = str2;
                str5 = "";
                if (str3 != null) {
                    String[] strArrSplit = str3.split("\n");
                    str5 = strArrSplit.length > 0 ? strArrSplit[0] : "";
                    str6 = str3;
                } else {
                    str6 = "";
                }
                crashDetailBean.p = str5;
                crashDetailBean.q = str6;
                crashDetailBean.r = System.currentTimeMillis();
                crashDetailBean.u = z.b(crashDetailBean.q.getBytes());
                crashDetailBean.z = z.a(c.f, false);
                crashDetailBean.A = dVar.c.d;
                crashDetailBean.B = threadCurrentThread.getName() + "(" + threadCurrentThread.getId() + ")";
                crashDetailBean.I = dVar.c.y();
                crashDetailBean.h = dVar.c.v();
                crashDetailBean.M = dVar.c.a;
                crashDetailBean.N = dVar.c.a();
                crashDetailBean.Q = dVar.c.H();
                crashDetailBean.R = dVar.c.I();
                crashDetailBean.S = dVar.c.B();
                crashDetailBean.T = dVar.c.G();
                if (!c.a().n()) {
                    dVar.d.d(crashDetailBean);
                }
                crashDetailBean.y = y.a();
                if (crashDetailBean.O == null) {
                    crashDetailBean.O = new LinkedHashMap();
                }
                if (map != null) {
                    crashDetailBean.O.putAll(map);
                }
                b.a(str4, z.a(), dVar.c.d, threadCurrentThread.getName(), str + "\n" + str2 + "\n" + str3, crashDetailBean);
                if (!dVar.d.a(crashDetailBean)) {
                    dVar.d.a(crashDetailBean, 3000L, false);
                }
                x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
            x.e("[ExtraCrashManager] Crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
            b.a(str4, z.a(), dVar.c.d, threadCurrentThread.getName(), str + "\n" + str2 + "\n" + str3, null);
            x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
        } catch (Throwable th) {
            try {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
            } catch (Throwable th2) {
                x.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                throw th2;
            }
        }
    }

    private d(Context context) {
        c cVarA = c.a();
        if (cVarA == null) {
            return;
        }
        this.b = com.tencent.bugly.crashreport.common.strategy.a.a();
        this.c = com.tencent.bugly.crashreport.common.info.a.a(context);
        this.d = cVarA.p;
        this.e = context;
        w.a().a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.d.1
            @Override // java.lang.Runnable
            public final void run() {
                d.a(d.this);
            }
        });
    }

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    public static void a(final Thread thread, final int i, final String str, final String str2, final String str3, final Map<String, String> map) {
        w.a().a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.d.2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    if (d.a != null) {
                        d.a(d.a, thread, i, str, str2, str3, map);
                    } else {
                        x.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    }
                } catch (Throwable th) {
                    if (!x.b(th)) {
                        th.printStackTrace();
                    }
                    x.e("[ExtraCrashManager] Crash error %s %s %s", str, str2, str3);
                }
            }
        });
    }
}