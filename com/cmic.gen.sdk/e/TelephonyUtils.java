package com.cmic.gen.sdk.e;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.lang.reflect.Method;

/* compiled from: TelephonyUtils.java */
/* renamed from: com.cmic.gen.sdk.e.n, reason: use source file name */
/* loaded from: classes.dex */
public class TelephonyUtils {
    private static final String a = Build.BRAND;
    private static final String b = Build.MODEL;
    private static final String c = "android" + Build.VERSION.RELEASE;
    private static final boolean d;
    private static final String e;

    static {
        d = Build.VERSION.SDK_INT <= 28;
        e = Build.MANUFACTURER;
    }

    public static int a(Context context, boolean z, ConcurrentBundle concurrentBundle) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        try {
            connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            if (connectivityManager == null) {
                activeNetworkInfo = null;
            } else {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            int type = activeNetworkInfo.getType();
            if (type == 1) {
                LogUtils.b("TelephonyUtils", "WIFI");
                boolean zA = PermissionUtils.a(context, "android.permission.CHANGE_NETWORK_STATE");
                LogUtils.a("TelephonyUtils", "CHANGE_NETWORK_STATE=" + zA);
                if (zA && z && a(connectivityManager, context, concurrentBundle)) {
                    LogUtils.b("TelephonyUtils", "流量数据 WIFI 同开");
                    return 3;
                }
                return 2;
            }
            if (type == 0) {
                LogUtils.b("TelephonyUtils", "流量");
                return 1;
            }
            return 0;
        }
        return 0;
    }

    private static boolean a(ConnectivityManager connectivityManager, Context context, ConcurrentBundle concurrentBundle) throws NoSuchMethodException, SecurityException {
        try {
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            boolean zBooleanValue = ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
            LogUtils.b("TelephonyUtils", "data is on ---------" + zBooleanValue);
            if (Build.VERSION.SDK_INT >= 26) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    if (telephonyManager.createForSubscriptionId(SubscriptionManager.getDefaultSubscriptionId()).isDataEnabled()) {
                        concurrentBundle.a("networkTypeByAPI", "1");
                    } else {
                        concurrentBundle.a("networkTypeByAPI", "0");
                    }
                }
            } else {
                concurrentBundle.a("networkTypeByAPI", "-1");
            }
            return zBooleanValue;
        } catch (Exception e2) {
            LogUtils.a("TelephonyUtils", "isMobileEnabled ----反射出错-----");
            return false;
        }
    }

    public static String a() {
        return a;
    }

    public static String b() {
        return b;
    }

    public static String c() {
        return c;
    }

    public static boolean d() {
        return d;
    }

    public static boolean e() {
        String str = e;
        LogUtils.a("brand", str);
        return LeakCanaryInternals.HUAWEI.equalsIgnoreCase(str);
    }

    public static boolean a(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return telephonyManager == null || 1 != telephonyManager.getSimState();
    }
}