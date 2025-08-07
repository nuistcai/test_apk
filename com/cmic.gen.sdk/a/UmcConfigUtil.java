package com.cmic.gen.sdk.a;

import android.text.TextUtils;
import com.cmic.gen.sdk.e.SharedPreferencesUtil;

/* compiled from: UmcConfigUtil.java */
/* renamed from: com.cmic.gen.sdk.a.d, reason: use source file name */
/* loaded from: classes.dex */
class UmcConfigUtil {
    static boolean a() {
        return System.currentTimeMillis() >= SharedPreferencesUtil.a("sso_config_xf", "client_valid", 0L);
    }

    static boolean a(boolean z) {
        String str;
        if (z) {
            str = "1";
        } else {
            str = "0";
        }
        return "1".equals(SharedPreferencesUtil.a("sso_config_xf", "CLOSE_IPV4_LIST", str));
    }

    static boolean b(boolean z) {
        String str;
        if (z) {
            str = "1";
        } else {
            str = "0";
        }
        return "1".equals(SharedPreferencesUtil.a("sso_config_xf", "CLOSE_IPV6_LIST", str));
    }

    static boolean c(boolean z) {
        String str;
        if (z) {
            str = "1";
        } else {
            str = "0";
        }
        return "1".equals(SharedPreferencesUtil.a("sso_config_xf", "CLOSE_M008_APPID_LIST", str)) || "1".equals(SharedPreferencesUtil.a("sso_config_xf", "CLOSE_M008_SDKVERSION_LIST", str));
    }

    static String a(String str) {
        String strA = SharedPreferencesUtil.a("sso_config_xf", "config_host", (String) null);
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        return str;
    }

    static String b(String str) {
        String strA = SharedPreferencesUtil.a("sso_config_xf", "https_get_phone_scrip_host", (String) null);
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        return str;
    }

    static String c(String str) {
        String strA = SharedPreferencesUtil.a("sso_config_xf", "logHost", "");
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        return str;
    }

    static boolean d(boolean z) {
        return SharedPreferencesUtil.a("sso_config_xf", "CLOSE_FRIEND_WAPKS", z ? "CU" : "").contains("CU");
    }

    static boolean e(boolean z) {
        return SharedPreferencesUtil.a("sso_config_xf", "CLOSE_FRIEND_WAPKS", z ? "CT" : "").contains("CT");
    }

    static boolean f(boolean z) {
        return "1".equals(SharedPreferencesUtil.a("sso_config_xf", "CLOSE_LOGS_VERSION", z ? "1" : "0"));
    }

    static int a(int i) {
        return SharedPreferencesUtil.a("sso_config_xf", "maxFailedLogTimes", i);
    }

    static int b(int i) {
        return SharedPreferencesUtil.a("sso_config_xf", "pauseTime", i);
    }
}