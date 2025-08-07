package com.cmic.gen.sdk.e;

import android.content.Context;
import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.e.SharedPreferencesUtil;
import com.cmic.gen.sdk.e.ThreadUtils;

/* compiled from: PhoneScripUtils.java */
/* renamed from: com.cmic.gen.sdk.e.i, reason: use source file name */
/* loaded from: classes.dex */
public class PhoneScripUtils {
    private static String a;
    private static String b;
    private static String c;
    private static String d;
    private static long e = 0;
    private static int f = -1;

    public static void a(boolean z, boolean z2) {
        SharedPreferencesUtil.a aVarA = SharedPreferencesUtil.a();
        aVarA.a("phonescripstarttime");
        aVarA.a("phonescripcache");
        aVarA.a("securityphone");
        aVarA.a("securityphonecache");
        aVarA.a("pre_sim_key");
        aVarA.a("phonescripversion");
        if (z2) {
            aVarA.a();
        } else {
            aVarA.b();
        }
        if (z) {
            a = null;
            d = null;
            e = 0L;
            f = -1;
        }
    }

    public static void a(final Context context, final String str, final String str2, long j, final String str3, String str4) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str3) && j > 0) {
            LogUtils.b("PhoneScripUtils", "save phone scrip simKey = " + str3);
            a = str;
            long j2 = j * 1000;
            e = System.currentTimeMillis() + j2;
            LogUtils.b("sLifeTime", e + "");
            d = str3;
            f = 1;
            if (!"operator".equals(str4)) {
                ThreadUtils.a(new ThreadUtils.a() { // from class: com.cmic.gen.sdk.e.i.1
                    @Override // com.cmic.gen.sdk.e.ThreadUtils.a
                    protected void a() {
                        LogUtils.b("PhoneScripUtils", "start save scrip to sp in sub thread");
                        PhoneScripUtils.b(context, str, str2, PhoneScripUtils.e, str3);
                    }
                });
            } else if (j2 > 3600000) {
                e = System.currentTimeMillis() + 3600000;
            } else {
                e = System.currentTimeMillis() + j2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String str, String str2, long j, String str3) {
        String strA = KeystoreUtil.a(context, str);
        if (!TextUtils.isEmpty(strA)) {
            SharedPreferencesUtil.a aVarA = SharedPreferencesUtil.a();
            aVarA.a("phonescripcache", strA);
            aVarA.a("phonescripstarttime", j);
            aVarA.a("phonescripversion", 1);
            aVarA.a("pre_sim_key", str3);
            aVarA.b();
        }
    }

    public static String a(Context context) {
        if (TextUtils.isEmpty(a)) {
            String strB = SharedPreferencesUtil.b("phonescripcache", "");
            if (TextUtils.isEmpty(strB)) {
                LogUtils.a("PhoneScripUtils", "null");
                return null;
            }
            e = SharedPreferencesUtil.a("phonescripstarttime", 0L);
            d = SharedPreferencesUtil.b("pre_sim_key", "");
            f = SharedPreferencesUtil.a("phonescripversion", -1);
            String strC = KeystoreUtil.c(context, strB);
            a = strC;
            return strC;
        }
        return a;
    }

    public static void a(Context context, String str, String str2) {
        b = str;
        c = str2;
        LogUtils.a("PhoneScripUtils", "number=" + str);
        String strB = KeystoreUtil.b(context, str);
        LogUtils.a("PhoneScripUtils", "encryptStr=" + strB);
        if (!TextUtils.isEmpty(strB)) {
            SharedPreferencesUtil.a aVarA = SharedPreferencesUtil.a();
            aVarA.a("securityphonecache", strB);
            aVarA.a("operatortypecache", c);
            aVarA.b();
        }
    }

    public static String b(Context context) {
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        String strB = SharedPreferencesUtil.b("securityphone", "");
        if (!TextUtils.isEmpty(strB)) {
            return strB;
        }
        String strB2 = SharedPreferencesUtil.b("securityphonecache", "");
        LogUtils.a("PhoneScripUtils", "encryptData=" + strB2);
        if (TextUtils.isEmpty(strB2)) {
            LogUtils.a("PhoneScripUtils", "null");
            return null;
        }
        return KeystoreUtil.c(context, strB2);
    }

    private static boolean c() {
        if (TextUtils.isEmpty(a)) {
            return !TextUtils.isEmpty(SharedPreferencesUtil.b("phonescripcache", "")) && a(SharedPreferencesUtil.a("phonescripstarttime", 0L));
        }
        LogUtils.b("PhoneScripUtils", d + " " + e);
        return a(e);
    }

    private static boolean a(long j) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        LogUtils.b("PhoneScripUtils", j + "");
        LogUtils.b("PhoneScripUtils", jCurrentTimeMillis + "");
        return j - jCurrentTimeMillis > 10000;
    }

    public static long a() {
        long j;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (!TextUtils.isEmpty(a)) {
            LogUtils.b("PhoneScripUtils", d + " " + e);
            j = (e - jCurrentTimeMillis) - 10000;
        } else {
            String strB = SharedPreferencesUtil.b("phonescripcache", "");
            long jA = SharedPreferencesUtil.a("phonescripstarttime", 0L);
            if (TextUtils.isEmpty(strB)) {
                j = 0;
            } else {
                j = (jA - jCurrentTimeMillis) - 10000;
            }
        }
        return Math.max(j / 1000, 0L);
    }

    private static int a(String str) {
        String strB;
        if (!TextUtils.isEmpty(d)) {
            strB = d;
        } else {
            strB = SharedPreferencesUtil.b("pre_sim_key", "");
            d = strB;
        }
        if (TextUtils.isEmpty(strB)) {
            return 0;
        }
        if (strB.equals(str)) {
            return 1;
        }
        return 2;
    }

    public static boolean a(ConcurrentBundle concurrentBundle) {
        int iA = a(concurrentBundle.b("scripKey"));
        concurrentBundle.a("imsiState", iA + "");
        LogUtils.b("PhoneScripUtils", "simState = " + iA);
        if (iA == 0) {
            return false;
        }
        if (f == -1) {
            f = SharedPreferencesUtil.a("phonescripversion", -1);
        }
        if (f != 1) {
            a(true, false);
            KeystoreUtil.a();
            LogUtils.b("PhoneScripUtils", "phoneScriptVersion change");
            return false;
        }
        if (iA == 2) {
            a(true, false);
            return false;
        }
        return c();
    }
}