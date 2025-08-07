package cn.fly.mgs.a;

import cn.fly.tools.FlyLog;

/* loaded from: classes.dex */
public class e {
    private static e a = new e();

    private e() {
    }

    public static e a() {
        return a;
    }

    public void a(String str) {
        FlyLog.getInstance().d("[IDS][MGS]" + str, new Object[0]);
    }

    public void b(String str) {
        FlyLog.getInstance().d("[IDS][MGS]" + str, new Object[0]);
    }

    public void a(Throwable th) {
        FlyLog.getInstance().d(th, "[IDS][MGS]", new Object[0]);
    }

    public void a(String str, Throwable th) {
        FlyLog.getInstance().d(th, "[IDS][MGS] " + str, new Object[0]);
    }

    public void b(Throwable th) {
        FlyLog.getInstance().e(th, "[IDS][MGS]", new Object[0]);
    }
}