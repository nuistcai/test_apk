package cn.fly.mcl.c;

import cn.fly.tools.FlyLog;

/* loaded from: classes.dex */
public class b {
    private static b a = new b();

    private b() {
    }

    public static b a() {
        return a;
    }

    public void a(String str) {
        FlyLog.getInstance().d("[IDS][MCL]" + str, new Object[0]);
    }

    public void b(String str) {
        FlyLog.getInstance().d("[IDS][MCL]" + str, new Object[0]);
    }

    public void a(Throwable th) {
        FlyLog.getInstance().d(th, "%s", "[IDS][MCL]");
    }

    public void a(String str, Throwable th) {
        FlyLog.getInstance().d(th, "%s", "[IDS][MCL] " + str);
    }
}