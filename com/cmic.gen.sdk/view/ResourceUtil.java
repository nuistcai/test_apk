package com.cmic.gen.sdk.view;

import android.content.Context;
import android.content.res.Resources;

/* compiled from: ResourceUtil.java */
/* renamed from: com.cmic.gen.sdk.view.c, reason: use source file name */
/* loaded from: classes.dex */
public class ResourceUtil {
    public static int a(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str, str2, context.getPackageName());
    }

    public static int a(Context context, String str) {
        int iA = a(context, str, "id");
        if (iA == 0) {
            throw new Resources.NotFoundException(str);
        }
        return iA;
    }

    public static int b(Context context, String str) {
        int iA = a(context, str, "drawable");
        if (iA == 0) {
            throw new Resources.NotFoundException(str);
        }
        return iA;
    }

    public static int c(Context context, String str) {
        int iA = a(context, str, "anim");
        if (iA == 0) {
            throw new Resources.NotFoundException(str);
        }
        return iA;
    }
}