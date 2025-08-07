package com.cmic.gen.sdk.auth;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.auth.AuthnHelperCore;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.OverTimeUtils;
import com.cmic.gen.sdk.e.PhoneScripUtils;
import com.cmic.gen.sdk.e.ThreadUtils;
import com.cmic.gen.sdk.view.GenAuthThemeConfig;
import com.cmic.gen.sdk.view.GenLoginPageInListener;
import com.cmic.gen.sdk.view.LoginProxy;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GenAuthnHelper extends AuthnHelperCore {
    private static GenAuthnHelper f = null;
    private GenAuthThemeConfig g;
    private GenLoginPageInListener h;

    private GenAuthnHelper(Context context) {
        super(context);
        this.h = null;
    }

    private GenAuthnHelper(Context context, String use2048PublicKey) {
        super(context);
        this.h = null;
        this.e = use2048PublicKey;
    }

    public static GenAuthnHelper getInstance(Context context) {
        if (f == null) {
            synchronized (GenAuthnHelper.class) {
                if (f == null) {
                    f = new GenAuthnHelper(context);
                }
            }
        }
        return f;
    }

    public static GenAuthnHelper getInstance(Context context, String encrypType) {
        if (f == null) {
            synchronized (GenAuthnHelper.class) {
                if (f == null) {
                    f = new GenAuthnHelper(context, encrypType);
                }
            }
        }
        return f;
    }

    public void setAuthThemeConfig(GenAuthThemeConfig genAuthThemeConfig) {
        this.g = genAuthThemeConfig;
    }

    public void deleteAuthThemeConfig() {
        this.g = null;
    }

    public GenAuthThemeConfig getAuthThemeConfig() {
        if (this.g == null) {
            this.g = new GenAuthThemeConfig.Builder().build();
        }
        return this.g;
    }

    public void loginPageInCallBack(String resultCode) {
        if (this.h != null) {
            this.h.onLoginPageInComplete(resultCode);
        }
    }

    @Override // com.cmic.gen.sdk.auth.AuthnHelperCore
    public void getPhoneInfo(String appId, String appKey, GenTokenListener listener) {
        getPhoneInfo(appId, appKey, listener, -1);
    }

    public void getPhoneInfo(final String appId, final String appKey, final GenTokenListener listener, int requestCode) {
        final ConcurrentBundle concurrentBundleA = a(listener);
        concurrentBundleA.a("SDKRequestCode", requestCode);
        concurrentBundleA.a("methodChain", "100001");
        concurrentBundleA.a("methodCostTimePre", System.currentTimeMillis());
        b(concurrentBundleA);
        ThreadUtils.a(new ThreadUtils.a(this.b, concurrentBundleA) { // from class: com.cmic.gen.sdk.auth.GenAuthnHelper.1
            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
            protected void a() {
                if (GenAuthnHelper.this.a(concurrentBundleA, appId, appKey, "preGetMobile", 3, listener)) {
                    GenAuthnHelper.super.a(concurrentBundleA);
                }
            }
        });
    }

    @Override // com.cmic.gen.sdk.auth.AuthnHelperCore
    public void loginAuth(String appId, String appKey, GenTokenListener listener) {
        loginAuth(appId, appKey, listener, -1);
    }

    public void loginAuth(final String appId, final String appKey, final GenTokenListener listener, int requestCode) {
        final ConcurrentBundle concurrentBundleA = a(listener);
        concurrentBundleA.a("SDKRequestCode", requestCode);
        concurrentBundleA.a("methodChain", "100001");
        concurrentBundleA.a("methodCostTimePre", System.currentTimeMillis());
        concurrentBundleA.a("displayLogo", this.g.isDisplayLogo() ? "1" : "0");
        b(concurrentBundleA);
        ThreadUtils.a(new ThreadUtils.a(this.b, concurrentBundleA) { // from class: com.cmic.gen.sdk.auth.GenAuthnHelper.2
            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
            protected void a() {
                if (GenAuthnHelper.this.a(concurrentBundleA, appId, appKey, "loginAuth", 3, listener)) {
                    String strA = PhoneScripUtils.a(GenAuthnHelper.this.b);
                    if (!TextUtils.isEmpty(strA)) {
                        concurrentBundleA.a("phonescrip", strA);
                    }
                    GenAuthnHelper.this.a(concurrentBundleA);
                }
            }
        });
    }

    @Override // com.cmic.gen.sdk.auth.AuthnHelperCore
    public void mobileAuth(String appId, String appKey, GenTokenListener listener) {
        mobileAuth(appId, appKey, listener, -1);
    }

    public void mobileAuth(final String appId, final String appKey, final GenTokenListener listener, int requestCode) {
        final ConcurrentBundle concurrentBundleA = a(listener);
        concurrentBundleA.a("SDKRequestCode", requestCode);
        concurrentBundleA.a("methodChain", "100001");
        concurrentBundleA.a("methodCostTimePre", System.currentTimeMillis());
        b(concurrentBundleA);
        ThreadUtils.a(new ThreadUtils.a(this.b, concurrentBundleA) { // from class: com.cmic.gen.sdk.auth.GenAuthnHelper.3
            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
            protected void a() {
                if (GenAuthnHelper.this.a(concurrentBundleA, appId, appKey, "mobileAuth", 0, listener)) {
                    GenAuthnHelper.super.a(concurrentBundleA);
                }
            }
        });
    }

    private void b(ConcurrentBundle concurrentBundle) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        int iMin = Math.min(stackTrace.length, 5);
        for (int i = 0; i < iMin; i++) {
            stringBuffer.append(stackTrace[i].toString()).append("\n");
        }
        concurrentBundle.a("pathOfCallingMethod", stringBuffer.toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.cmic.gen.sdk.auth.AuthnHelperCore
    public void a(ConcurrentBundle concurrentBundle) {
        final AuthnHelperCore.a aVar = new AuthnHelperCore.a(concurrentBundle);
        this.d.postDelayed(aVar, this.c);
        concurrentBundle.a("methodChain", concurrentBundle.b("methodChain") + ",100003");
        concurrentBundle.a("methodCostTime", concurrentBundle.b("methodCostTime") + "," + (System.currentTimeMillis() - concurrentBundle.b("methodCostTimePre", 0L)));
        concurrentBundle.a("methodCostTimePre", System.currentTimeMillis());
        this.a.a(concurrentBundle, new AuthnCallback() { // from class: com.cmic.gen.sdk.auth.GenAuthnHelper.4
            @Override // com.cmic.gen.sdk.auth.AuthnCallback
            public void a(String str, String str2, ConcurrentBundle concurrentBundle2, JSONObject jSONObject) {
                LogUtils.b("onBusinessComplete", "onBusinessComplete");
                GenAuthnHelper.this.d.removeCallbacks(aVar);
                if ("103000".equals(str) && !OverTimeUtils.a(concurrentBundle2.b("traceId"))) {
                    GenAuthnHelper.b(GenAuthnHelper.this.b, concurrentBundle2);
                } else {
                    GenAuthnHelper.this.callBackResult(str, str2, concurrentBundle2, jSONObject);
                }
            }
        });
    }

    public void quitAuthActivity() {
        try {
            if (LoginProxy.a().b() != null) {
                LoginProxy.a().a = 0;
                LoginProxy.a().b().a();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.a("AuthnHelper", "关闭授权页失败");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, ConcurrentBundle concurrentBundle) {
        String strB = concurrentBundle.b("traceId");
        Intent intent = new Intent();
        intent.putExtra("traceId", strB);
        OverTimeUtils.a(concurrentBundle.b("traceId"), concurrentBundle);
        intent.setClassName(context, "com.cmic.gen.sdk.view.GenLoginAuthActivity");
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public void setPageInListener(GenLoginPageInListener listener) {
        this.h = listener;
    }

    public long getOverTime() {
        return this.c;
    }
}