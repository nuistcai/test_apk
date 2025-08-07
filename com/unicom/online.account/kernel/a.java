package com.unicom.online.account.kernel;

import android.util.Log;

/* loaded from: classes.dex */
public final class a {
    private static boolean a = false;

    public static void a(Exception exc) {
        if (a) {
            exc.printStackTrace();
        }
    }

    public static void a(String str) {
        if (a) {
            Log.i("uniaccount", "6.3.0 ".concat(String.valueOf(str)));
        }
    }

    public static void a(boolean z) {
        a = z;
    }

    public static void b(String str) {
        Log.e("uniaccount", "6.3.0 ".concat(String.valueOf(str)));
    }
}