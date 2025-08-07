package com.tencent.bugly.crashreport.biz;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.core.os.EnvironmentCompat;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.biz.a.AnonymousClass2;
import com.tencent.bugly.crashreport.biz.a.RunnableC0039a;
import com.tencent.bugly.crashreport.biz.a.c;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.List;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public class b {
    public static a a;
    private static boolean b;
    private static int g;
    private static long h;
    private static long i;
    private static int c = 10;
    private static long d = 300000;
    private static long e = 30000;
    private static long f = 0;
    private static long j = 0;
    private static Application.ActivityLifecycleCallbacks k = null;
    private static Class<?> l = null;
    private static boolean m = true;

    static /* synthetic */ String a(String str, String str2) {
        return z.a() + "  " + str + "  " + str2 + "\n";
    }

    static /* synthetic */ int g() {
        int i2 = g;
        g = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void c(Context context, BuglyStrategy buglyStrategy) {
        boolean zIsEnableUserInfo;
        boolean zRecordUserInfoOnceADay;
        boolean z;
        if (buglyStrategy == null) {
            zIsEnableUserInfo = true;
            zRecordUserInfoOnceADay = false;
        } else {
            zRecordUserInfoOnceADay = buglyStrategy.recordUserInfoOnceADay();
            zIsEnableUserInfo = buglyStrategy.isEnableUserInfo();
        }
        if (zRecordUserInfoOnceADay) {
            com.tencent.bugly.crashreport.common.info.a aVarA = com.tencent.bugly.crashreport.common.info.a.a(context);
            List<UserInfoBean> listA = a.a(aVarA.d);
            if (listA != null) {
                for (int i2 = 0; i2 < listA.size(); i2++) {
                    UserInfoBean userInfoBean = listA.get(i2);
                    if (userInfoBean.n.equals(aVarA.k) && userInfoBean.b == 1) {
                        long jB = z.b();
                        if (jB <= 0) {
                            break;
                        }
                        if (userInfoBean.e >= jB) {
                            if (userInfoBean.f <= 0) {
                                a aVar = a;
                                w wVarA = w.a();
                                if (wVarA != null) {
                                    wVarA.a(aVar.new AnonymousClass2());
                                }
                            }
                            z = false;
                            if (!z) {
                                return;
                            } else {
                                zIsEnableUserInfo = false;
                            }
                        }
                    }
                }
                z = true;
                if (!z) {
                }
            } else {
                z = true;
                if (!z) {
                }
            }
        }
        com.tencent.bugly.crashreport.common.info.a aVarB = com.tencent.bugly.crashreport.common.info.a.b();
        if (aVarB != null) {
            String className = null;
            boolean z2 = false;
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (stackTraceElement.getMethodName().equals("onCreate")) {
                    className = stackTraceElement.getClassName();
                }
                if (stackTraceElement.getClassName().equals("android.app.Activity")) {
                    z2 = true;
                }
            }
            if (className == null) {
                className = EnvironmentCompat.MEDIA_UNKNOWN;
            } else if (z2) {
                aVarB.a(true);
            } else {
                className = "background";
            }
            aVarB.q = className;
        }
        if (zIsEnableUserInfo) {
            Application application = context.getApplicationContext() instanceof Application ? (Application) context.getApplicationContext() : null;
            if (application != null) {
                try {
                    if (k == null) {
                        k = new Application.ActivityLifecycleCallbacks() { // from class: com.tencent.bugly.crashreport.biz.b.2
                            @Override // android.app.Application.ActivityLifecycleCallbacks
                            public final void onActivityStopped(Activity activity) {
                            }

                            @Override // android.app.Application.ActivityLifecycleCallbacks
                            public final void onActivityStarted(Activity activity) {
                            }

                            @Override // android.app.Application.ActivityLifecycleCallbacks
                            public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                            }

                            @Override // android.app.Application.ActivityLifecycleCallbacks
                            public final void onActivityResumed(Activity activity) {
                                String name;
                                if (activity == null) {
                                    name = EnvironmentCompat.MEDIA_UNKNOWN;
                                } else {
                                    name = activity.getClass().getName();
                                }
                                if (b.l != null && !b.l.getName().equals(name)) {
                                    return;
                                }
                                x.c(">>> %s onResumed <<<", name);
                                com.tencent.bugly.crashreport.common.info.a aVarB2 = com.tencent.bugly.crashreport.common.info.a.b();
                                if (aVarB2 == null) {
                                    return;
                                }
                                aVarB2.E.add(b.a(name, "onResumed"));
                                aVarB2.a(true);
                                aVarB2.q = name;
                                aVarB2.r = System.currentTimeMillis();
                                aVarB2.u = aVarB2.r - b.i;
                                long j2 = aVarB2.r - b.h;
                                if (j2 > (b.f > 0 ? b.f : b.e)) {
                                    aVarB2.d();
                                    b.g();
                                    x.a("[session] launch app one times (app in background %d seconds and over %d seconds)", Long.valueOf(j2 / 1000), Long.valueOf(b.e / 1000));
                                    if (b.g % b.c == 0) {
                                        b.a.a(4, b.m, 0L);
                                        return;
                                    }
                                    b.a.a(4, false, 0L);
                                    long jCurrentTimeMillis = System.currentTimeMillis();
                                    if (jCurrentTimeMillis - b.j > b.d) {
                                        long unused = b.j = jCurrentTimeMillis;
                                        x.a("add a timer to upload hot start user info", new Object[0]);
                                        if (b.m) {
                                            w.a().a(b.a.new RunnableC0039a(null, true), b.d);
                                        }
                                    }
                                }
                            }

                            @Override // android.app.Application.ActivityLifecycleCallbacks
                            public final void onActivityPaused(Activity activity) {
                                String name;
                                if (activity == null) {
                                    name = EnvironmentCompat.MEDIA_UNKNOWN;
                                } else {
                                    name = activity.getClass().getName();
                                }
                                if (b.l != null && !b.l.getName().equals(name)) {
                                    return;
                                }
                                x.c(">>> %s onPaused <<<", name);
                                com.tencent.bugly.crashreport.common.info.a aVarB2 = com.tencent.bugly.crashreport.common.info.a.b();
                                if (aVarB2 == null) {
                                    return;
                                }
                                aVarB2.E.add(b.a(name, "onPaused"));
                                aVarB2.a(false);
                                aVarB2.s = System.currentTimeMillis();
                                aVarB2.t = aVarB2.s - aVarB2.r;
                                long unused = b.h = aVarB2.s;
                                if (aVarB2.t < 0) {
                                    aVarB2.t = 0L;
                                }
                                if (activity != null) {
                                    aVarB2.q = "background";
                                } else {
                                    aVarB2.q = EnvironmentCompat.MEDIA_UNKNOWN;
                                }
                            }

                            @Override // android.app.Application.ActivityLifecycleCallbacks
                            public final void onActivityDestroyed(Activity activity) {
                                String name;
                                if (activity == null) {
                                    name = EnvironmentCompat.MEDIA_UNKNOWN;
                                } else {
                                    name = activity.getClass().getName();
                                }
                                if (b.l != null && !b.l.getName().equals(name)) {
                                    return;
                                }
                                x.c(">>> %s onDestroyed <<<", name);
                                com.tencent.bugly.crashreport.common.info.a aVarB2 = com.tencent.bugly.crashreport.common.info.a.b();
                                if (aVarB2 != null) {
                                    aVarB2.E.add(b.a(name, "onDestroyed"));
                                }
                            }

                            @Override // android.app.Application.ActivityLifecycleCallbacks
                            public final void onActivityCreated(Activity activity, Bundle bundle) {
                                String name;
                                if (activity == null) {
                                    name = EnvironmentCompat.MEDIA_UNKNOWN;
                                } else {
                                    name = activity.getClass().getName();
                                }
                                if (b.l != null && !b.l.getName().equals(name)) {
                                    return;
                                }
                                x.c(">>> %s onCreated <<<", name);
                                com.tencent.bugly.crashreport.common.info.a aVarB2 = com.tencent.bugly.crashreport.common.info.a.b();
                                if (aVarB2 != null) {
                                    aVarB2.E.add(b.a(name, "onCreated"));
                                }
                            }
                        };
                    }
                    application.registerActivityLifecycleCallbacks(k);
                } catch (Exception e2) {
                    if (!x.a(e2)) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        if (m) {
            i = System.currentTimeMillis();
            a.a(1, false, 0L);
            x.a("[session] launch app, new start", new Object[0]);
            a.a();
            w.a().a(a.new c(21600000L), 21600000L);
        }
    }

    public static void a(final Context context, final BuglyStrategy buglyStrategy) {
        long appReportDelay;
        if (b) {
            return;
        }
        m = com.tencent.bugly.crashreport.common.info.a.a(context).e;
        a = new a(context, m);
        b = true;
        if (buglyStrategy == null) {
            appReportDelay = 0;
        } else {
            l = buglyStrategy.getUserInfoActivity();
            appReportDelay = buglyStrategy.getAppReportDelay();
        }
        if (appReportDelay <= 0) {
            c(context, buglyStrategy);
        } else {
            w.a().a(new Runnable() { // from class: com.tencent.bugly.crashreport.biz.b.1
                @Override // java.lang.Runnable
                public final void run() {
                    b.c(context, buglyStrategy);
                }
            }, appReportDelay);
        }
    }

    public static void a(long j2) {
        if (j2 < 0) {
            j2 = com.tencent.bugly.crashreport.common.strategy.a.a().c().q;
        }
        f = j2;
    }

    public static void a(StrategyBean strategyBean, boolean z) {
        if (a != null && !z) {
            a aVar = a;
            w wVarA = w.a();
            if (wVarA != null) {
                wVarA.a(aVar.new AnonymousClass2());
            }
        }
        if (strategyBean == null) {
            return;
        }
        if (strategyBean.q > 0) {
            e = strategyBean.q;
        }
        if (strategyBean.w > 0) {
            c = strategyBean.w;
        }
        if (strategyBean.x > 0) {
            d = strategyBean.x;
        }
    }

    public static void a() {
        if (a != null) {
            a.a(2, false, 0L);
        }
    }

    public static void a(Context context) {
        if (!b || context == null) {
            return;
        }
        Application application = context.getApplicationContext() instanceof Application ? (Application) context.getApplicationContext() : null;
        if (application != null) {
            try {
                if (k != null) {
                    application.unregisterActivityLifecycleCallbacks(k);
                }
            } catch (Exception e2) {
                if (!x.a(e2)) {
                    e2.printStackTrace();
                }
            }
        }
        b = false;
    }
}