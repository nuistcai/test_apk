package cn.fly.commons;

import android.os.SystemClock;
import java.util.List;

/* loaded from: classes.dex */
public class e {
    public static boolean a() {
        return a(w.b("0037cjchFc")) && CSCenter.getInstance().isOaidEnable();
    }

    public static boolean b() {
        return a(w.b("003cfc"));
    }

    public static boolean c() {
        return a(w.b("003-efch9c"));
    }

    public static boolean d() {
        return a(w.b("003]ef fc"));
    }

    public static boolean e() {
        return a(w.b("002fc")) && CSCenter.getInstance().isLocationDataEnable();
    }

    public static boolean f() {
        return a(w.b("0036cjVfc")) && CSCenter.getInstance().isLocationDataEnable();
    }

    public static boolean g() {
        return a(w.b("003;decj c")) && CSCenter.getInstance().isLocationDataEnable();
    }

    public static boolean h() {
        return a(w.b("003.eeeh7c"));
    }

    public static boolean i() {
        return a("na");
    }

    private static boolean a(String str) {
        List list = (List) c.a(w.b("003VehcjZb"), (Object) null);
        return w.b("0038cjchIc").equals(str) ? list != null && list.contains(w.b("0039cjch)c")) : "na".equals(str) ? list != null && list.contains("na") : list == null || list.contains(str);
    }

    public static boolean j() {
        return SystemClock.elapsedRealtime() - m.a().c() <= ((long) ((Integer) c.a(w.b("003)ehch3h"), 600)).intValue()) * 1000;
    }
}