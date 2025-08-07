package com.cmic.gen.sdk.e;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.cmic.gen.sdk.b.UMCTelephonyManagement;

/* compiled from: SIMUtils.java */
/* renamed from: com.cmic.gen.sdk.e.k, reason: use source file name */
/* loaded from: classes.dex */
public class SIMUtils {
    private static SIMUtils b;
    private final Context a;
    private long c = 0;
    private String d = "";

    public static void a(Context context) {
        b = new SIMUtils(context);
    }

    private SIMUtils(Context context) {
        this.a = context;
    }

    public static SIMUtils a() {
        return b;
    }

    public String b() {
        try {
            int iA = UMCTelephonyManagement.a().b().a();
            if (iA >= 0) {
                return Integer.toString(iA);
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String a(String str) {
        switch (str) {
            case "46000":
            case "46002":
            case "46007":
            case "46004":
                LogUtils.a("SIMUtils", "中国移动");
                return "1";
            case "46001":
            case "46006":
            case "46009":
                LogUtils.a("SIMUtils", "中国联通");
                return "2";
            case "46003":
            case "46005":
            case "46011":
                LogUtils.a("SIMUtils", "中国电信");
                return "3";
            case "45412":
                LogUtils.b("SIMUtils", "香港移动");
                return "4";
            default:
                return "0";
        }
    }

    public String a(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            str = a(z);
        }
        return a(str);
    }

    public String a(boolean z) {
        if (!z) {
            return "";
        }
        if (System.currentTimeMillis() - this.c > 3000) {
            this.c = System.currentTimeMillis();
            TelephonyManager telephonyManager = (TelephonyManager) this.a.getSystemService("phone");
            if (telephonyManager != null) {
                this.d = telephonyManager.getSimOperator();
                LogUtils.b("SIMUtils", "getSimOperator SysOperator= " + this.d);
                return this.d;
            }
            return "";
        }
        LogUtils.b("SIMUtils", "使用缓存operator Operator= " + this.d);
        return this.d;
    }
}