package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public class Bugly {
    public static final String SDK_IS_DEV = "false";
    private static boolean a;
    public static Boolean isDev;
    public static boolean enable = true;
    public static Context applicationContext = null;
    private static String[] b = {"BuglyCrashModule", "BuglyRqdModule", "BuglyBetaModule"};
    private static String[] c = {"BuglyRqdModule", "BuglyCrashModule", "BuglyBetaModule"};

    public static void init(Context context, String str, boolean z) {
        init(context, str, z, null);
    }

    public static synchronized void init(Context context, String str, boolean z, BuglyStrategy buglyStrategy) {
        if (a) {
            return;
        }
        a = true;
        Context contextA = z.a(context);
        applicationContext = contextA;
        if (contextA == null) {
            Log.e(x.a, "init arg 'context' should not be null!");
            return;
        }
        if (isDev()) {
            b = c;
        }
        for (String str2 : b) {
            try {
                if (str2.equals("BuglyCrashModule")) {
                    b.a(CrashModule.getInstance());
                } else if (!str2.equals("BuglyBetaModule") && !str2.equals("BuglyRqdModule")) {
                    str2.equals("BuglyFeedbackModule");
                }
            } catch (Throwable th) {
                x.b(th);
            }
        }
        b.a = enable;
        b.a(applicationContext, str, z, buglyStrategy);
    }

    public static synchronized String getAppChannel() {
        byte[] bArr;
        com.tencent.bugly.crashreport.common.info.a aVarB = com.tencent.bugly.crashreport.common.info.a.b();
        if (aVarB == null) {
            return null;
        }
        if (TextUtils.isEmpty(aVarB.m)) {
            p pVarA = p.a();
            if (pVarA != null) {
                Map<String, byte[]> mapA = pVarA.a(556, (o) null, true);
                if (mapA != null && (bArr = mapA.get("app_channel")) != null) {
                    return new String(bArr);
                }
            } else {
                return aVarB.m;
            }
        }
        return aVarB.m;
    }

    public static boolean isDev() {
        if (isDev == null) {
            isDev = Boolean.valueOf(Boolean.parseBoolean(SDK_IS_DEV.replace("@", "")));
        }
        return isDev.booleanValue();
    }
}