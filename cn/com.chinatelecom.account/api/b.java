package cn.com.chinatelecom.account.api;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes.dex */
public class b {
    public static int a = 0;
    public static int b = 1;
    public static int c = 2;
    public static int d = 3;
    private static boolean e = false;

    public static String a(Context context, String str) {
        return Helper.gscret(context, str);
    }

    public static String a(String str) {
        return Helper.sgwret(str);
    }

    public static void a() {
        ClientUtils.setSdkType(1);
    }

    public static boolean a(Context context) {
        String strB = cn.com.chinatelecom.account.api.d.a.b(context);
        return !TextUtils.isEmpty(strB) && strB.equals("1");
    }

    public static String b() {
        return a.a;
    }

    public static String c() {
        return a.b;
    }
}