package com.unicom.xiaowo.account.shield.e;

import android.util.Log;

/* loaded from: classes.dex */
public class g {
    private static boolean a = true;

    public static void a(String str) {
        if (a) {
            Log.i("uniaccount", "5.2.0AR002B1125 " + str);
        }
    }

    public static void b(String str) {
        Log.e("uniaccount", "5.2.0AR002B1125 " + str);
    }

    public static void a(boolean z) {
        a = z;
    }
}