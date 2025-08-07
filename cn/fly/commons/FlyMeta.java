package cn.fly.commons;

import android.os.Looper;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;

/* loaded from: classes.dex */
public class FlyMeta implements PublicMemberKeeper {
    public static <T> T get(FlyProduct flyProduct, String str, Class<T> cls) {
        return (T) get(flyProduct, str, cls, null);
    }

    public static <T> T get(FlyProduct flyProduct, String str, Class<T> cls, T t) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            FlyLog.getInstance().w("WARNING: gt mta in main: key = " + str);
        }
        Object objA = w.a(str, cls, flyProduct);
        if (objA == null) {
            objA = w.a(str);
        }
        if (objA != null) {
            return (T) objA;
        }
        return t;
    }
}