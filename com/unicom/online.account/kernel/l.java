package com.unicom.online.account.kernel;

import android.content.Context;

/* loaded from: classes.dex */
public class l {
    private static final String a = l.class.getSimpleName();
    private static int b = 0;
    private static int c = 0;

    public static String a(Context context) {
        if (c <= 0) {
            return null;
        }
        String strB = b();
        try {
            return m.a(context, a(strB, strB));
        } catch (Exception e) {
            c = 0;
            return null;
        }
    }

    private static String a(String str, String str2) {
        return "OAlog" + str + str2;
    }

    private static void a() {
        c = c > 0 ? c - 1 : 0;
    }

    public static void a(Context context, int i) {
        String strB = b();
        if (i == 0) {
            strB = a + b;
        }
        m.c(context, a(strB, strB));
        a();
    }

    public static boolean a(Context context, String str) {
        boolean zA = false;
        if (c >= 20) {
            if (b > 2147483627) {
                m.d(context, "OAlog");
                c = 0;
                b = 0;
                c = 0;
            } else {
                a(context, 0);
                b++;
            }
        }
        if (c >= 0) {
            c++;
        } else {
            c = 0;
        }
        String strB = b();
        if (an.b(str).booleanValue()) {
            String strA = a(strB, strB);
            if (an.b(strA).booleanValue() && !(zA = m.a(context, strA, str))) {
                a();
            }
        }
        return zA;
    }

    private static String b() {
        return a + ((b + c) - 1);
    }
}