package cn.com.chinatelecom.account.sdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import cn.com.chinatelecom.account.api.CtSetting;
import cn.com.chinatelecom.account.api.a;
import cn.com.chinatelecom.account.api.c;
import cn.com.chinatelecom.account.api.d;
import cn.com.chinatelecom.account.sdk.a.b;
import cn.com.chinatelecom.account.sdk.ui.PrivacyWebviewActivity;

/* loaded from: classes.dex */
public final class CtAuth {
    public static final String CT_PRIVACY_TITLE = "服务与隐私协议";
    private static volatile CtAuth b;
    private d e = new d() { // from class: cn.com.chinatelecom.account.sdk.CtAuth.2
        @Override // cn.com.chinatelecom.account.api.d
        public void a(String str, String str2) {
            if (CtAuth.c) {
                Log.i(str, str2);
            }
        }

        @Override // cn.com.chinatelecom.account.api.d
        public void a(String str, String str2, Throwable th) {
            if (CtAuth.c) {
                Log.w(str, str2);
                if (th != null) {
                    th.printStackTrace();
                }
            }
        }
    };
    private static final String a = CtAuth.class.getSimpleName();
    private static boolean c = false;
    private static boolean d = false;

    public static String getCtPrivacyUrl() {
        return b.b();
    }

    public static CtAuth getInstance() {
        if (b == null) {
            synchronized (CtAuth.class) {
                if (b == null) {
                    b = new CtAuth();
                }
            }
        }
        return b;
    }

    public void finishAuthActivity() {
        cn.com.chinatelecom.account.sdk.a.d.a().d();
        cn.com.chinatelecom.account.sdk.a.d.f();
    }

    public void finishMiniAuthActivity() {
        cn.com.chinatelecom.account.sdk.a.d.a().e();
        cn.com.chinatelecom.account.sdk.a.d.f();
    }

    public String getOperatorType() {
        return a.a().c();
    }

    public void init(Context context, String str, String str2, boolean z) {
        c = z;
        cn.com.chinatelecom.account.api.b.a();
        a.a().a(context, str, str2, this.e);
    }

    public boolean isMobileDataEnabled() {
        return a.a().b();
    }

    public void openAuthActivity(Context context, AuthPageConfig authPageConfig, AuthViewConfig authViewConfig, ResultListener resultListener) {
        a.a(a, "called openAuthActivity()");
        if (context == null || TextUtils.isEmpty(cn.com.chinatelecom.account.api.b.b()) || TextUtils.isEmpty(cn.com.chinatelecom.account.api.b.c())) {
            throw new IllegalArgumentException("Please call the init method");
        }
        if (!d) {
            a.a(a, "Please call the requestPreLogin method");
            return;
        }
        if (authPageConfig == null) {
            a.a(a, "The authPageConfig is empty");
            return;
        }
        cn.com.chinatelecom.account.sdk.a.d.a().a(authPageConfig);
        cn.com.chinatelecom.account.sdk.a.d.a().a(authViewConfig);
        cn.com.chinatelecom.account.sdk.a.a.a().a(resultListener);
        cn.com.chinatelecom.account.sdk.a.d.a().a(context);
    }

    public void openAuthActivity(Context context, AuthPageConfig authPageConfig, ResultListener resultListener) {
        openAuthActivity(context, authPageConfig, null, resultListener);
    }

    public void openMiniAuthActivity(Context context, AuthPageConfig authPageConfig, AuthViewConfig authViewConfig, ResultListener resultListener) {
        a.a(a, "called openCenterMiniAuthActivity()");
        if (context == null || TextUtils.isEmpty(cn.com.chinatelecom.account.api.b.b()) || TextUtils.isEmpty(cn.com.chinatelecom.account.api.b.c())) {
            throw new IllegalArgumentException("Please call the init method");
        }
        if (!d) {
            a.a(a, "Please call the requestPreLogin method");
            return;
        }
        if (authPageConfig == null) {
            a.a(a, "The authPageConfig is empty");
            return;
        }
        cn.com.chinatelecom.account.sdk.a.d.a().a(authPageConfig);
        cn.com.chinatelecom.account.sdk.a.d.a().a(authViewConfig);
        cn.com.chinatelecom.account.sdk.a.a.a().a(resultListener);
        cn.com.chinatelecom.account.sdk.a.d.a().b(context);
    }

    public void openMiniAuthActivity(Context context, AuthPageConfig authPageConfig, ResultListener resultListener) {
        openMiniAuthActivity(context, authPageConfig, null, resultListener);
    }

    public void openWebviewActivity(Context context, String str, String str2) {
        try {
            Intent intent = new Intent(context, (Class<?>) PrivacyWebviewActivity.class);
            intent.putExtra("privacyProtocolUrl", str);
            intent.putExtra("privacyProtocolTitle", str2);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestPreLogin(CtSetting ctSetting, final ResultListener resultListener) {
        d = true;
        a.a().a(ctSetting, new c() { // from class: cn.com.chinatelecom.account.sdk.CtAuth.1
            @Override // cn.com.chinatelecom.account.api.c
            public void a(String str) {
                cn.com.chinatelecom.account.sdk.a.a.a().a(str, resultListener);
                try {
                    a.c.sendBroadcast(new Intent("cn.com.chinatelecom.account.sdk.preAuth.Action"));
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    public void setDomainName(String str, String str2, String str3) {
        a.a().a(str, str2, str3);
    }
}