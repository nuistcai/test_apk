package com.cmic.gen.sdk.b;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.telephony.SubscriptionManager;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.TelephonyUtils;
import java.lang.reflect.InvocationTargetException;

/* compiled from: UMCTelephonyManagement.java */
/* renamed from: com.cmic.gen.sdk.b.a, reason: use source file name */
/* loaded from: classes.dex */
public class UMCTelephonyManagement {
    private static UMCTelephonyManagement a;
    private static long b = 0;
    private a c = null;

    /* compiled from: UMCTelephonyManagement.java */
    /* renamed from: com.cmic.gen.sdk.b.a$a */
    public static class a {
        private int a = -1;
        private int b = -1;

        public int a() {
            return this.b;
        }
    }

    private UMCTelephonyManagement() {
    }

    public static UMCTelephonyManagement a() {
        if (a == null) {
            a = new UMCTelephonyManagement();
        }
        return a;
    }

    public a b() {
        if (this.c == null) {
            return new a();
        }
        return this.c;
    }

    public void a(Context context, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long jCurrentTimeMillis = System.currentTimeMillis() - b;
        if (jCurrentTimeMillis < 5000 && jCurrentTimeMillis > 0) {
            return;
        }
        this.c = new a();
        if (!z) {
            return;
        }
        a(context);
        if (TelephonyUtils.e() && TelephonyUtils.d()) {
            LogUtils.b("UMCTelephonyManagement", "华为手机兼容性处理");
            if (this.c.b == 0 || this.c.b == 1) {
                if (this.c.a == -1) {
                    this.c.a = this.c.b;
                }
                this.c.b = -1;
            }
            if ((this.c.a != -1 || this.c.b != -1) && Build.VERSION.SDK_INT >= 21) {
                b(context);
            }
        }
        b = System.currentTimeMillis();
    }

    private void a(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object objInvoke;
        if (Build.VERSION.SDK_INT < 22) {
            this.c.a = -1;
            return;
        }
        SubscriptionManager subscriptionManagerFrom = SubscriptionManager.from(context.getApplicationContext());
        if (subscriptionManagerFrom != null) {
            try {
                if (this.c.a == -1 && Build.VERSION.SDK_INT >= 24) {
                    this.c.b = SubscriptionManager.getDefaultDataSubscriptionId();
                    LogUtils.b("UMCTelephonyManagement", "android 7.0及以上手机getDefaultDataSubscriptionId适配成功: dataSubId = " + this.c.b);
                    return;
                }
            } catch (Exception e) {
                LogUtils.a("UMCTelephonyManagement", "android 7.0及以上手机getDefaultDataSubscriptionId适配失败");
            }
            try {
                objInvoke = subscriptionManagerFrom.getClass().getMethod("getDefaultDataSubId", new Class[0]).invoke(subscriptionManagerFrom, new Object[0]);
            } catch (Exception e2) {
                LogUtils.a("UMCTelephonyManagement", "readDefaultDataSubId-->getDefaultDataSubId 反射出错");
            }
            if (!(objInvoke instanceof Integer) && !(objInvoke instanceof Long)) {
                try {
                    Object objInvoke2 = subscriptionManagerFrom.getClass().getMethod("getDefaultDataSubscriptionId", new Class[0]).invoke(subscriptionManagerFrom, new Object[0]);
                    if ((objInvoke2 instanceof Integer) || (objInvoke2 instanceof Long)) {
                        this.c.b = ((Integer) objInvoke2).intValue();
                        LogUtils.b("UMCTelephonyManagement", "反射getDefaultDataSubscriptionId适配成功: dataSubId = " + this.c.b);
                        return;
                    }
                    return;
                } catch (Exception e3) {
                    LogUtils.a("UMCTelephonyManagement", "getDefaultDataSubscriptionId-->getDefaultDataSubscriptionId 反射出错");
                    return;
                }
            }
            this.c.b = ((Integer) objInvoke).intValue();
            LogUtils.b("UMCTelephonyManagement", "android 7.0以下手机getDefaultDataSubId适配成功: dataSubId = " + this.c.b);
        }
    }

    private void b(Context context) {
        LogUtils.b("UMCTelephonyManagement", "readSimInfoDbStart");
        Uri uri = Uri.parse("content://telephony/siminfo");
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = contentResolver.query(uri, new String[]{"_id", "sim_id"}, "sim_id>=?", new String[]{"0"}, null);
                if (cursorQuery != null) {
                    while (cursorQuery.moveToNext()) {
                        int i = cursorQuery.getInt(cursorQuery.getColumnIndex("sim_id"));
                        int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex("_id"));
                        if (this.c.a == -1 && this.c.b != -1 && this.c.b == i2) {
                            this.c.a = i;
                            LogUtils.b("UMCTelephonyManagement", "通过读取sim db获取数据流量卡的卡槽值：" + i);
                        }
                        if (this.c.a == i) {
                            this.c.b = i2;
                        }
                    }
                }
            } catch (Exception e) {
                LogUtils.a("UMCTelephonyManagement", "readSimInfoDb error");
                if (cursorQuery != null) {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            LogUtils.b("UMCTelephonyManagement", "readSimInfoDbEnd");
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }
}