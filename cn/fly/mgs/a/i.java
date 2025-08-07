package cn.fly.mgs.a;

import cn.fly.FlySDK;
import cn.fly.tools.utils.SharePrefrenceHelper;

/* loaded from: classes.dex */
public class i {
    private static SharePrefrenceHelper a;

    private static void e() {
        if (a == null) {
            a = new SharePrefrenceHelper(FlySDK.getContext());
            a.open("gu", 0);
        }
    }

    public static synchronized void a(boolean z) {
        e();
        a.putInt("device_switch_local_cache", Integer.valueOf(z ? 1 : 0));
    }

    public static synchronized Boolean a() {
        Boolean bool;
        e();
        int i = a.getInt("device_switch_local_cache", -1);
        if (i == 1) {
            bool = true;
        } else if (i == 0) {
            bool = false;
        } else {
            bool = null;
        }
        return bool;
    }

    public static synchronized void b(boolean z) {
        e();
        a.putInt("device_switch_remote_cache", Integer.valueOf(z ? 1 : 0));
    }

    public static synchronized Boolean b() {
        Boolean bool;
        e();
        int i = a.getInt("device_switch_remote_cache", -1);
        if (i == 1) {
            bool = true;
        } else if (i == 0) {
            bool = false;
        } else {
            bool = null;
        }
        return bool;
    }

    public static synchronized void a(String str) {
        e();
        a.putString("duid_remote_cache", str);
    }

    public static synchronized String c() {
        e();
        return a.getString("duid_remote_cache", "");
    }

    public static synchronized void b(String str) {
        e();
        a.putString("guard_id_remote_cache", str);
    }

    public static synchronized String d() {
        e();
        return a.getString("guard_id_remote_cache", "");
    }
}