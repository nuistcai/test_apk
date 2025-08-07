package cn.com.chinatelecom.account.api;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.util.Log;
import cn.com.chinatelecom.account.api.d.g;
import java.security.interfaces.RSAPublicKey;

/* loaded from: classes.dex */
public final class ClientUtils {
    public static final int TYPE_SDK_API = 0;
    public static final int TYPE_SDK_BIO = 2;
    public static final int TYPE_SDK_HY = 1;
    private static final String TAG = ClientUtils.class.getSimpleName();
    private static int sdkType = 0;

    public static String enrdata(String str, String str2) {
        try {
            return cn.com.chinatelecom.account.api.a.b.a(str, (RSAPublicKey) cn.com.chinatelecom.account.api.a.b.a(str2));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getApiVersion() {
        return "3.0";
    }

    public static String getAppName(Context context) {
        try {
            int i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.labelRes;
            Log.d("getAppName", "------------------->");
            return context.getResources().getString(i);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getCurrentNetworkType(Context context) {
        return g.f(context);
    }

    public static boolean getHealthy(Context context) {
        try {
            return cn.com.chinatelecom.account.api.d.d.d(context);
        } catch (Throwable th) {
            a.a(TAG, "getHealthy error ：" + th.getMessage(), th);
            return false;
        }
    }

    public static boolean getMacData() {
        try {
            return cn.com.chinatelecom.account.api.d.d.c();
        } catch (Throwable th) {
            a.a(TAG, "getMacData error ：" + th.getMessage(), th);
            return false;
        }
    }

    public static String getMobileBrand() {
        return Build.BRAND;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static boolean getNetSafe(Context context) {
        try {
            return cn.com.chinatelecom.account.api.d.d.c(context);
        } catch (Throwable th) {
            a.a(TAG, "getNetSafe error ：" + th.getMessage(), th);
            return false;
        }
    }

    public static String getOnlineType(Context context) {
        return g.g(context);
    }

    public static String getOperatorType(Context context) {
        return g.h(context);
    }

    public static String getOs() {
        return getMobileBrand() + "-" + getModel() + "-A:" + Build.VERSION.RELEASE;
    }

    public static String getPID() {
        String strSubstring = "";
        try {
            String str = Thread.currentThread().getId() + "" + Process.myPid();
            if (str.length() <= 6) {
                return "ctacco";
            }
            strSubstring = str.substring(0, 6);
            return strSubstring;
        } catch (Exception e) {
            e.printStackTrace();
            return strSubstring;
        }
    }

    public static int getSdkType() {
        return sdkType;
    }

    public static String getSdkVersion() {
        return sdkType == 1 ? "SDK-HY-v4.5.9" : sdkType == 2 ? "SDK-BIOM-v4.5.9" : "SDK-API-v4.5.9";
    }

    public static boolean getTimePass(Context context) {
        try {
            if (!cn.com.chinatelecom.account.api.d.d.b(context)) {
                if (!cn.com.chinatelecom.account.api.d.d.d()) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            a.a(TAG, "getTimePass error ：" + th.getMessage(), th);
            return false;
        }
    }

    public static long getTp() {
        return System.currentTimeMillis();
    }

    public static boolean isJY() {
        return true;
    }

    public static boolean objChange(Object obj, String str) {
        try {
            return cn.com.chinatelecom.account.api.d.d.a(obj, str);
        } catch (Throwable th) {
            a.a(TAG, "objChange error ：" + th.getMessage(), th);
            return false;
        }
    }

    public static void setSdkType(int i) {
        sdkType = i;
    }

    public static String strBuf() {
        try {
            return cn.com.chinatelecom.account.api.d.d.b().toString();
        } catch (Throwable th) {
            a.a(TAG, "strBuf error ：" + th.getMessage(), th);
            return "";
        }
    }
}