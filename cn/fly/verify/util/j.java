package cn.fly.verify.util;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.utils.SharePrefrenceHelper;

/* loaded from: classes.dex */
public class j {
    private static SharePrefrenceHelper a;

    static {
        try {
            a = new SharePrefrenceHelper(FlySDK.getContext());
            a.open("FlyVerify_SPDB_V2", 1);
        } catch (Throwable th) {
        }
    }

    public static String a() {
        String string = a.getString("sim_serial");
        return TextUtils.isEmpty(string) ? "" : string;
    }

    public static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            a.remove("sim_serial");
        } else {
            a.putString("sim_serial", str);
        }
    }

    public static void a(boolean z) {
        a.putBoolean("policy_grant_result", Boolean.valueOf(z));
    }

    public static void b(boolean z) {
        a.putBoolean("key_preverify_success", Boolean.valueOf(z));
    }

    public static boolean b() {
        return a.getBoolean("policy_grant_result", true);
    }
}