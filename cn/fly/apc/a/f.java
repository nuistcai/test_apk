package cn.fly.apc.a;

import cn.fly.tools.FlyLog;

/* loaded from: classes.dex */
public class f {
    private static f a = new f();

    private f() {
    }

    public static f a() {
        return a;
    }

    public void a(Throwable th) {
        FlyLog.getInstance().d(th, "%s", "[IDS][APC]");
    }

    public void a(String str, Object... objArr) {
        FlyLog.getInstance().d("[IDS][APC]" + String.format(str, objArr), new Object[0]);
    }

    public void b(String str, Object... objArr) {
        FlyLog.getInstance().i("[IDS][APC]" + String.format(str, objArr));
    }
}