package com.cmic.gen.sdk.e;

import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.a.UmcConfigBean;
import java.security.SecureRandom;
import java.util.UUID;

/* compiled from: UmcUtils.java */
/* renamed from: com.cmic.gen.sdk.e.r, reason: use source file name */
/* loaded from: classes.dex */
public class UmcUtils {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        char[] cArr = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            cArr[i] = a[(b >>> 4) & 15];
            i = i2 + 1;
            cArr[i2] = a[b & 15];
        }
        return new String(cArr);
    }

    public static byte[] a() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return bArr;
    }

    public static String b() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String c() {
        return d().replace("-", "");
    }

    private static String d() {
        return UUID.randomUUID().toString();
    }

    public static void a(ConcurrentBundle concurrentBundle, String str) {
        if (TextUtils.isEmpty(concurrentBundle.b("interfaceType", ""))) {
            concurrentBundle.a("interfaceType", str);
        } else {
            concurrentBundle.a("interfaceType", concurrentBundle.b("interfaceType") + ";" + str);
        }
    }

    public static void b(ConcurrentBundle concurrentBundle, String str) {
        if (TextUtils.isEmpty(concurrentBundle.b("interfaceCode", ""))) {
            concurrentBundle.a("interfaceCode", str);
        } else {
            concurrentBundle.a("interfaceCode", concurrentBundle.b("interfaceCode") + ";" + str);
        }
    }

    public static void c(ConcurrentBundle concurrentBundle, String str) {
        if (TextUtils.isEmpty(concurrentBundle.b("interfaceElasped", ""))) {
            concurrentBundle.a("interfaceElasped", str);
        } else {
            concurrentBundle.a("interfaceElasped", concurrentBundle.b("interfaceElasped") + ";" + str);
        }
    }

    public static boolean a(UmcConfigBean umcConfigBean) {
        return SharedPreferencesUtil.a("logCloseTime", 0L) + ((long) (((umcConfigBean.l() * 60) * 60) * 1000)) >= System.currentTimeMillis();
    }
}