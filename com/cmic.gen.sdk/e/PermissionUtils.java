package com.cmic.gen.sdk.e;

import android.content.Context;

/* compiled from: PermissionUtils.java */
/* renamed from: com.cmic.gen.sdk.e.h, reason: use source file name */
/* loaded from: classes.dex */
public class PermissionUtils {
    public static boolean a(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }
}