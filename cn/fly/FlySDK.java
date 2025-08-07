package cn.fly;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import cn.fly.commons.C0041r;
import cn.fly.commons.CSCenter;
import cn.fly.commons.FlyProduct;
import cn.fly.commons.InternationalDomain;
import cn.fly.commons.c;
import cn.fly.commons.o;
import cn.fly.commons.q;
import cn.fly.commons.w;
import cn.fly.commons.z;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;

/* loaded from: classes.dex */
public class FlySDK implements PublicMemberKeeper {
    public static final int CHANNEL_APICLOUD = 5;
    public static final int CHANNEL_COCOS = 1;
    public static final int CHANNEL_FLUTTER = 4;
    public static final int CHANNEL_JS = 3;
    public static final int CHANNEL_NATIVE = 0;
    public static final int CHANNEL_QUICKSDK = 6;
    public static final int CHANNEL_REACT_NATIVE = 8;
    public static final int CHANNEL_UNIAPP = 7;
    public static final int CHANNEL_UNITY = 2;
    public static final int SDK_VERSION_CODE;
    public static final String SDK_VERSION_NAME;
    private static volatile Context a;

    static {
        int i;
        String strReplace = "1.0.0";
        try {
            strReplace = "2025-07-11".replace("-", ".");
            i = Integer.parseInt("2025-07-11".replace("-", ""));
        } catch (Throwable th) {
            i = 1;
        }
        SDK_VERSION_CODE = i;
        SDK_VERSION_NAME = strReplace;
    }

    public static synchronized void init(Context context) {
        init(context, null, null);
    }

    public static synchronized void init(Context context, String str) {
        init(context, str, null);
    }

    public static synchronized void init(Context context, String str, String str2) {
        if (context == null) {
            Log.e("SDK", "Init error, context is null");
            return;
        }
        if (a == null) {
            a = context.getApplicationContext();
            w.a = str;
            w.b = str2;
            q.a(false);
        } else if (!TextUtils.isEmpty(str) && !str.equals(w.a)) {
            w.a = str;
            w.b = str2;
            q.a(true);
        }
    }

    public static InternationalDomain getDmn() {
        return w.e == null ? InternationalDomain.DEFAULT : w.e;
    }

    public static boolean checkForceHttps() {
        return checkFH(false);
    }

    public static boolean checkFH(boolean z) {
        if (z) {
            return w.g;
        }
        if (w.f) {
            return w.f;
        }
        return ((Integer) c.a("hs", 1)).intValue() == 1;
    }

    public static boolean checkV6() {
        return w.h;
    }

    public static String getAppkey() {
        if (z.h()) {
            return q.a();
        }
        return null;
    }

    public static String getAppSecret() {
        if (TextUtils.isEmpty(w.b)) {
            return w.d;
        }
        return w.b;
    }

    public static Context getContextSafely() {
        return a;
    }

    public static Context getContext() {
        if (a == null) {
            try {
                Context contextA = C0041r.a();
                if (contextA != null) {
                    init(contextA);
                }
            } catch (Throwable th) {
            }
        }
        return a;
    }

    public static final boolean isGppVer() {
        return w.j;
    }

    public static final boolean isFly() {
        return q.c();
    }

    public static final boolean isForb() {
        return q.d();
    }

    public static final int isAuth() {
        return q.b();
    }

    public static void setChannel(FlyProduct flyProduct, int i) {
        if (isForb()) {
            FlyLog.getInstance().d("isForb: true", new Object[0]);
        } else {
            o.a().a(flyProduct, i);
        }
    }

    public static void submitPolicyGrantResult(boolean z) {
        z.b(z);
    }

    public static void submitPolicyGrantResult(FlyCustomController flyCustomController, boolean z) {
        submitPolicyGrantResult(z);
        updateFlyCustomController(flyCustomController);
    }

    public static boolean getDefaultPrivacy() {
        return true;
    }

    public static int getPrivacyGrantedStatus() {
        return z.c();
    }

    public static String syncGetBSDM(String str, String str2, String str3, boolean z) {
        return q.a(str, str2, str3, z);
    }

    public static void updateFlyCustomController(FlyCustomController flyCustomController) {
        CSCenter.getInstance().updateCustomController(flyCustomController);
    }
}