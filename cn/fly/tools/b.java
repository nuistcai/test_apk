package cn.fly.tools;

import cn.fly.commons.w;
import java.lang.Thread;

/* loaded from: classes.dex */
public class b implements Thread.UncaughtExceptionHandler {
    private static Thread.UncaughtExceptionHandler a;
    private static volatile boolean b = false;
    private static volatile boolean c = false;

    public static synchronized void a() {
        if (!b && w.i && !c) {
            c = true;
            a = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(new b());
        }
    }

    private b() {
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        try {
            FlyLog.getInstance().d("UE handled, processing...", new Object[0]);
            FlyLog.getInstance().crash(th);
            if (a == null || (a instanceof b)) {
            }
        } catch (Throwable th2) {
            try {
                FlyLog.getInstance().d(th2);
            } finally {
                if (a != null && !(a instanceof b)) {
                    a.uncaughtException(thread, th);
                }
            }
        }
    }
}