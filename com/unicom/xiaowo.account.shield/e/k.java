package com.unicom.xiaowo.account.shield.e;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/* loaded from: classes.dex */
public class k {
    public static void a(Context context, String str) {
        a(context, "auth02", str);
    }

    public static String a(Context context) throws NoSuchAlgorithmException {
        String strB = b(context, "auth02");
        if (TextUtils.isEmpty(strB)) {
            String strA = j.a(UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis());
            a(context, strA);
            return strA;
        }
        return strB;
    }

    public static String b(Context context, String str) {
        try {
            return context.getSharedPreferences("cu_auth", 0).getString(str, "");
        } catch (Exception e) {
            a(context, str, "");
            return "";
        }
    }

    public static void a(Context context, String str, String str2) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("cu_auth", 0).edit();
            editorEdit.putString(str, str2);
            editorEdit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}