package cn.fly.verify.util;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.utils.SharePrefrenceHelper;

/* loaded from: classes.dex */
public class b {
    protected static SharePrefrenceHelper a;

    static {
        try {
            a = new SharePrefrenceHelper(FlySDK.getContext());
            a.open("Fly_Cache", 2);
        } catch (Throwable th) {
        }
    }

    public static int a(String str, int i) {
        return a.getInt(str, i);
    }

    public static String a(String str) {
        return a.getString(str);
    }

    public static void a(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            a.remove(str);
        } else {
            a.putString(str, str2);
        }
    }

    public static void b(String str, int i) {
        a.putInt(str, Integer.valueOf(i));
    }
}