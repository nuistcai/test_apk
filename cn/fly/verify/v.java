package cn.fly.verify;

import android.util.Log;
import cn.fly.tools.log.NLog;

/* loaded from: classes.dex */
public class v {
    private static NLog a;

    public static NLog a(String str, int i) {
        try {
            a = NLog.getInstance(str, i, "cn.fly.verify");
        } catch (Throwable th) {
            Log.d("[FlyVerify] ==>%s", "SLog init error", th);
        }
        return a;
    }

    public static void a(String str) {
        if (a != null) {
            a.d("[FlyVerify] ==>%s", str);
        }
    }

    public static void a(Throwable th) {
        if (a != null) {
            a.d(th);
        }
    }

    public static void a(Throwable th, String str) {
        if (a != null) {
            a.d(th, "[FlyVerify] ==>%s", str);
        }
    }
}