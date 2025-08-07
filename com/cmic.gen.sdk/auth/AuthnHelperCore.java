package com.cmic.gen.sdk.auth;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.a.UmcConfigBean;
import com.cmic.gen.sdk.a.UmcConfigManager;
import com.cmic.gen.sdk.b.UMCTelephonyManagement;
import com.cmic.gen.sdk.d.LogBean;
import com.cmic.gen.sdk.d.SendLog;
import com.cmic.gen.sdk.e.KeystoreUtil;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.OverTimeUtils;
import com.cmic.gen.sdk.e.PhoneScripUtils;
import com.cmic.gen.sdk.e.SIMUtils;
import com.cmic.gen.sdk.e.SharedPreferencesUtil;
import com.cmic.gen.sdk.e.TelephonyUtils;
import com.cmic.gen.sdk.e.ThreadUtils;
import com.cmic.gen.sdk.e.TimeUtils2;
import com.cmic.gen.sdk.e.UmcUtils;
import com.cmic.gen.sdk.e.WifiNetworkUtils;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthnHelperCore.java */
/* renamed from: com.cmic.gen.sdk.auth.c, reason: use source file name */
/* loaded from: classes.dex */
public class AuthnHelperCore {
    public static final String SDK_VERSION = "quick_login_android_5.9.11";
    private static AuthnHelperCore f = null;
    protected final AuthnBusiness a;
    protected final Context b;
    protected long c;
    protected final Handler d;
    protected String e;
    private final Object g;

    AuthnHelperCore(Context context) {
        this.c = 8000L;
        this.g = new Object();
        this.b = context.getApplicationContext();
        this.d = new Handler(this.b.getMainLooper());
        this.a = AuthnBusiness.a(this.b);
        WifiNetworkUtils.a(this.b);
        SharedPreferencesUtil.a(this.b);
        SIMUtils.a(this.b);
        ThreadUtils.a(new ThreadUtils.a() { // from class: com.cmic.gen.sdk.auth.c.1
            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
            protected void a() {
                String strB = SharedPreferencesUtil.b("AID", "");
                LogUtils.b("AuthnHelperCore", "aid = " + strB);
                if (TextUtils.isEmpty(strB)) {
                    AuthnHelperCore.this.a();
                }
                if (KeystoreUtil.a(AuthnHelperCore.this.b, true)) {
                    LogUtils.b("AuthnHelperCore", "生成androidkeystore成功");
                } else {
                    LogUtils.b("AuthnHelperCore", "生成androidkeystore失败");
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        String str = "%" + UmcUtils.b();
        LogUtils.b("AuthnHelperCore", "generate aid = " + str);
        SharedPreferencesUtil.a("AID", str);
    }

    private AuthnHelperCore(Context context, String str) {
        this(context);
        this.e = str;
    }

    public static AuthnHelperCore getInstance(Context context) {
        if (f == null) {
            synchronized (AuthnHelperCore.class) {
                if (f == null) {
                    f = new AuthnHelperCore(context);
                }
            }
        }
        return f;
    }

    public static AuthnHelperCore getInstance(Context context, String encrypType) {
        if (f == null) {
            synchronized (AuthnHelperCore.class) {
                if (f == null) {
                    f = new AuthnHelperCore(context, encrypType);
                }
            }
        }
        return f;
    }

    public void loginAuth(final String appId, final String appKey, final GenTokenListener listener) {
        final ConcurrentBundle concurrentBundleA = a(listener);
        ThreadUtils.a(new ThreadUtils.a(this.b, concurrentBundleA) { // from class: com.cmic.gen.sdk.auth.c.2
            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
            protected void a() {
                if (AuthnHelperCore.this.a(concurrentBundleA, appId, appKey, "loginAuth", 1, listener)) {
                    AuthnHelperCore.this.a(concurrentBundleA);
                }
            }
        });
    }

    public void mobileAuth(final String appId, final String appKey, final GenTokenListener listener) {
        final ConcurrentBundle concurrentBundleA = a(listener);
        ThreadUtils.a(new ThreadUtils.a(this.b, concurrentBundleA) { // from class: com.cmic.gen.sdk.auth.c.3
            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
            protected void a() {
                if (AuthnHelperCore.this.a(concurrentBundleA, appId, appKey, "mobileAuth", 0, listener)) {
                    AuthnHelperCore.this.a(concurrentBundleA);
                }
            }
        });
    }

    public void getPhoneInfo(final String appId, final String appKey, final GenTokenListener listener) {
        final ConcurrentBundle concurrentBundleA = a(listener);
        ThreadUtils.a(new ThreadUtils.a(this.b, concurrentBundleA) { // from class: com.cmic.gen.sdk.auth.c.4
            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
            protected void a() {
                if (AuthnHelperCore.this.a(concurrentBundleA, appId, appKey, "preGetMobile", 3, listener)) {
                    AuthnHelperCore.this.a(concurrentBundleA);
                }
            }
        });
    }

    protected ConcurrentBundle a(GenTokenListener genTokenListener) {
        ConcurrentBundle concurrentBundle = new ConcurrentBundle(64);
        String strC = UmcUtils.c();
        concurrentBundle.a(new LogBean());
        concurrentBundle.a("traceId", strC);
        LogUtils.a("traceId", strC);
        if (genTokenListener != null) {
            OverTimeUtils.a(strC, genTokenListener);
        }
        return concurrentBundle;
    }

    /* compiled from: AuthnHelperCore.java */
    /* renamed from: com.cmic.gen.sdk.auth.c$a */
    protected class a implements Runnable {
        private final ConcurrentBundle b;

        a(ConcurrentBundle concurrentBundle) {
            this.b = concurrentBundle;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            JSONObject jSONObjectA = AuthnResult.a("200023", "登录超时");
            AuthnHelperCore.this.callBackResult(jSONObjectA.optString("resultCode", "200023"), jSONObjectA.optString("desc", "登录超时"), this.b, jSONObjectA);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(ConcurrentBundle concurrentBundle) {
        final a aVar = new a(concurrentBundle);
        this.d.postDelayed(aVar, this.c);
        concurrentBundle.a("methodChain", concurrentBundle.b("methodChain") + ",100003");
        concurrentBundle.a("methodCostTime", concurrentBundle.b("methodCostTime") + "," + (System.currentTimeMillis() - concurrentBundle.b("methodCostTimePre", 0L)));
        concurrentBundle.a("methodCostTimePre", System.currentTimeMillis());
        this.a.a(concurrentBundle, new AuthnCallback() { // from class: com.cmic.gen.sdk.auth.c.5
            @Override // com.cmic.gen.sdk.auth.AuthnCallback
            public void a(String str, String str2, ConcurrentBundle concurrentBundle2, JSONObject jSONObject) throws JSONException {
                AuthnHelperCore.this.d.removeCallbacks(aVar);
                AuthnHelperCore.this.callBackResult(str, str2, concurrentBundle2, jSONObject);
            }
        });
    }

    protected boolean a(ConcurrentBundle concurrentBundle, String str, String str2, String str3, int i, GenTokenListener genTokenListener) throws IllegalAccessException, JSONException, IllegalArgumentException, InvocationTargetException {
        boolean zA;
        UmcConfigBean umcConfigBeanA = UmcConfigManager.a(this.b).a();
        concurrentBundle.a("methodChain", concurrentBundle.b("methodChain") + ",100002");
        concurrentBundle.a("methodCostTime", String.valueOf(System.currentTimeMillis() - concurrentBundle.b("methodCostTimePre", 0L)));
        concurrentBundle.a("methodCostTimePre", System.currentTimeMillis());
        concurrentBundle.a(umcConfigBeanA);
        concurrentBundle.a("use2048PublicKey", "rsa2048".equals(this.e));
        concurrentBundle.a("systemStartTime", SystemClock.elapsedRealtime());
        concurrentBundle.a("starttime", TimeUtils2.a());
        concurrentBundle.a("loginMethod", str3);
        concurrentBundle.a("appkey", str2);
        concurrentBundle.a("appid", str);
        concurrentBundle.a("timeOut", String.valueOf(this.c));
        boolean zA2 = TelephonyUtils.a(this.b);
        UMCTelephonyManagement.a().a(this.b, zA2);
        String strB = SIMUtils.a().b();
        String strA = SIMUtils.a().a(zA2);
        String strA2 = SIMUtils.a().a(strA, zA2);
        concurrentBundle.a("operator", strA);
        concurrentBundle.a("operatortype", strA2);
        concurrentBundle.a("logintype", i);
        LogUtils.b("AuthnHelperCore", "subId = " + strB);
        if (!TextUtils.isEmpty(strB)) {
            LogUtils.a("AuthnHelperCore", "使用subId作为缓存key = " + strB);
            concurrentBundle.a("scripType", "subid");
            concurrentBundle.a("scripKey", strB);
        } else if (!TextUtils.isEmpty(strA)) {
            LogUtils.a("AuthnHelperCore", "使用operator作为缓存key = " + strA);
            concurrentBundle.a("scripType", "operator");
            concurrentBundle.a("scripKey", strA);
        }
        int iA = TelephonyUtils.a(this.b, zA2, concurrentBundle);
        concurrentBundle.a("networktype", iA);
        if (!zA2) {
            concurrentBundle.a("authType", String.valueOf(0));
            callBackResult("200010", "无法识别sim卡或没有sim卡", concurrentBundle, null);
            return false;
        }
        if (genTokenListener == null) {
            callBackResult("102203", "listener不能为空", concurrentBundle, null);
            return false;
        }
        if (umcConfigBeanA.g()) {
            callBackResult("200082", "服务器繁忙，请稍后重试", concurrentBundle, null);
            return false;
        }
        if (TextUtils.isEmpty(str == null ? "" : str.trim())) {
            callBackResult("102203", "appId 不能为空", concurrentBundle, null);
            return false;
        }
        if (TextUtils.isEmpty(str2 == null ? "" : str2.trim())) {
            callBackResult("102203", "appkey不能为空", concurrentBundle, null);
            return false;
        }
        if (iA == 0) {
            callBackResult("102101", "未检测到网络", concurrentBundle, null);
            return false;
        }
        if ("2".equals(strA2) && umcConfigBeanA.f()) {
            callBackResult("200082", "服务器繁忙，请稍后重试", concurrentBundle, null);
            return false;
        }
        if ("3".equals(strA2) && umcConfigBeanA.e()) {
            callBackResult("200082", "服务器繁忙，请稍后重试", concurrentBundle, null);
            return false;
        }
        synchronized (this.g) {
            zA = PhoneScripUtils.a(concurrentBundle);
            if (zA) {
                concurrentBundle.a("securityphone", PhoneScripUtils.b(this.b));
                if (3 != i) {
                    String strA3 = PhoneScripUtils.a(this.b);
                    LogUtils.b("AuthnHelperCore", "解密phoneScript " + (!TextUtils.isEmpty(strA3)));
                    if (!TextUtils.isEmpty(strA3)) {
                        concurrentBundle.a("phonescrip", strA3);
                    } else {
                        zA = false;
                    }
                    PhoneScripUtils.a(true, false);
                }
            }
            concurrentBundle.a("isCacheScrip", zA);
            LogUtils.b("AuthnHelperCore", "isCachePhoneScrip = " + zA);
        }
        if (iA == 2 && !zA) {
            callBackResult("102103", "无数据网络", concurrentBundle, null);
            return false;
        }
        return true;
    }

    public static void setDebugMode(boolean isDebug) {
        LogUtils.a(isDebug);
    }

    public void callBackResult(String resultCode, String resultDes, ConcurrentBundle concurrentBundle, JSONObject json) throws JSONException {
        final JSONObject jSONObjectA;
        try {
            String strB = concurrentBundle.b("traceId");
            final int iB = concurrentBundle.b("SDKRequestCode", -1);
            if (!OverTimeUtils.a(strB)) {
                synchronized (this) {
                    final GenTokenListener genTokenListenerC = OverTimeUtils.c(strB);
                    if (json == null || !json.optBoolean("keepListener", false)) {
                        OverTimeUtils.b(strB);
                    }
                    if (genTokenListenerC == null) {
                        return;
                    }
                    concurrentBundle.a("systemEndTime", SystemClock.elapsedRealtime());
                    concurrentBundle.a("endtime", TimeUtils2.a());
                    int iC = concurrentBundle.c("logintype");
                    if (json == null) {
                        json = AuthnResult.a(resultCode, resultDes);
                    }
                    if (iC == 3) {
                        jSONObjectA = AuthnResult.a(resultCode, concurrentBundle, json);
                    } else {
                        jSONObjectA = AuthnResult.a(resultCode, resultDes, concurrentBundle, json);
                    }
                    jSONObjectA.put("traceId", strB);
                    jSONObjectA.put("scripExpiresIn", String.valueOf(PhoneScripUtils.a()));
                    this.d.post(new Runnable() { // from class: com.cmic.gen.sdk.auth.c.6
                        @Override // java.lang.Runnable
                        public void run() {
                            genTokenListenerC.onGetTokenComplete(iB, jSONObjectA);
                        }
                    });
                    UmcConfigManager.a(this.b).a(concurrentBundle);
                    if (!concurrentBundle.b().j() && !UmcUtils.a(concurrentBundle.b())) {
                        a(this.b, resultCode, concurrentBundle);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(final Context context, final String str, final ConcurrentBundle concurrentBundle) {
        ThreadUtils.a(new ThreadUtils.a() { // from class: com.cmic.gen.sdk.auth.c.7
            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
            protected void a() {
                if ("200023".equals(str)) {
                    SystemClock.sleep(8000L);
                }
                new SendLog().a(context, str, concurrentBundle);
            }
        });
    }

    public void setOverTime(long overTime) {
        this.c = overTime;
    }

    public JSONObject getNetworkType(Context context) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            boolean zA = TelephonyUtils.a(this.b);
            UMCTelephonyManagement.a().a(context, zA);
            String strA = SIMUtils.a().a(null, zA);
            int iA = TelephonyUtils.a(context, zA, new ConcurrentBundle(1));
            jSONObject.put("operatortype", strA);
            jSONObject.put("networktype", iA + "");
            LogUtils.b("AuthnHelperCore", "网络类型: " + iA);
            LogUtils.b("AuthnHelperCore", "运营商类型: " + strA);
            return jSONObject;
        } catch (Exception e) {
            try {
                jSONObject.put("errorDes", "发生未知错误");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            return jSONObject;
        }
    }

    public void delScrip() {
        try {
            PhoneScripUtils.a(true, true);
            LogUtils.b("AuthnHelperCore", "删除scrip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}