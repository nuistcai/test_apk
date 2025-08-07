package com.cmic.gen.sdk.e;

import android.util.Log;

/* compiled from: LogUtils.java */
/* renamed from: com.cmic.gen.sdk.e.c, reason: use source file name */
/* loaded from: classes.dex */
public class LogUtils {
    private static final LogUtils a = new LogUtils();
    private static boolean b = false;

    public static void a(boolean z) {
        b = z;
    }

    public static void a(String str, String str2) {
        if (b) {
            Log.e("CMCC-SDK:" + str, "" + str2);
        }
    }

    public static void b(String str, String str2) {
        if (b) {
            Log.d("CMCC-SDK:" + str, "" + str2);
        }
    }

    public static void c(String str, String str2) {
        if (b) {
            Log.i("CMCC-SDK:" + str, "" + str2);
        }
    }
}