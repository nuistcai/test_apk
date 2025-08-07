package com.cmic.gen.sdk.auth;

import android.content.Context;
import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.Constants2;
import com.cmic.gen.sdk.c.c.BaseRequest;
import com.cmic.gen.sdk.c.c.RequestCallback;
import com.cmic.gen.sdk.e.AESUtils;
import com.cmic.gen.sdk.e.KeystoreUtil;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.MD5STo16Byte;
import com.cmic.gen.sdk.e.PhoneScripUtils;
import com.cmic.gen.sdk.e.SharedPreferencesUtil;
import com.cmic.gen.sdk.e.SignUtil2;
import com.cmic.gen.sdk.e.ThreadUtils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthnBusiness.java */
/* renamed from: com.cmic.gen.sdk.auth.a, reason: use source file name */
/* loaded from: classes.dex */
public class AuthnBusiness {
    private static AuthnBusiness c = null;
    private final BaseRequest a = BaseRequest.a();
    private final Context b;

    private AuthnBusiness(Context context) {
        this.b = context.getApplicationContext();
    }

    public static AuthnBusiness a(Context context) {
        if (c == null) {
            synchronized (AuthnBusiness.class) {
                if (c == null) {
                    c = new AuthnBusiness(context);
                }
            }
        }
        return c;
    }

    public void a(ConcurrentBundle concurrentBundle, AuthnCallback authnCallback) {
        LogUtils.b("AuthnBusiness", "LoginCheck method start");
        int iC = concurrentBundle.c("logintype");
        concurrentBundle.a("methodChain", concurrentBundle.b("methodChain") + ",100004");
        concurrentBundle.a("methodCostTime", concurrentBundle.b("methodCostTime") + "," + (System.currentTimeMillis() - concurrentBundle.b("methodCostTimePre", 0L)));
        concurrentBundle.a("methodCostTimePre", System.currentTimeMillis());
        if (concurrentBundle.b("isCacheScrip", false)) {
            String strB = concurrentBundle.b("securityphone", "");
            if (iC == 3) {
                authnCallback.a("103000", "true", concurrentBundle, AuthnResult.a(strB));
                return;
            } else {
                b(concurrentBundle, authnCallback);
                return;
            }
        }
        b(concurrentBundle, authnCallback);
    }

    private void a(final ConcurrentBundle concurrentBundle) {
        ThreadUtils.a(new ThreadUtils.a(this.b, concurrentBundle) { // from class: com.cmic.gen.sdk.auth.a.1
            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
            protected void a() {
                AuthnBusiness.this.a.a(concurrentBundle, new RequestCallback() { // from class: com.cmic.gen.sdk.auth.a.1.1
                    @Override // com.cmic.gen.sdk.c.c.RequestCallback
                    public void a(String str, String str2, JSONObject jSONObject) {
                        LogUtils.a("AuthnBusiness", "jsonobj=" + jSONObject.toString());
                        try {
                            String strOptString = jSONObject.optString("authPrivacyLink", "");
                            LogUtils.a("AuthnBusiness", "authPrivacyLinkStr=" + strOptString);
                            JSONObject jSONObject2 = new JSONObject(strOptString.replace("\t", ""));
                            LogUtils.a("AuthnBusiness", "authPrivacyLink=" + jSONObject2.toString());
                            JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("1");
                            LogUtils.a("AuthnBusiness", "cmcc=" + jSONObjectOptJSONObject.toString());
                            JSONObject jSONObjectOptJSONObject2 = jSONObject2.optJSONObject("2");
                            JSONObject jSONObjectOptJSONObject3 = jSONObject2.optJSONObject("3");
                            JSONObject jSONObjectOptJSONObject4 = jSONObject2.optJSONObject("4");
                            SharedPreferencesUtil.a("operator1", jSONObjectOptJSONObject.toString());
                            SharedPreferencesUtil.a("operator2", jSONObjectOptJSONObject2.toString());
                            SharedPreferencesUtil.a("operator3", jSONObjectOptJSONObject3.toString());
                            SharedPreferencesUtil.a("operator4", jSONObjectOptJSONObject4.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void b(final ConcurrentBundle concurrentBundle, final AuthnCallback authnCallback) throws JSONException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        a(concurrentBundle);
        LogUtils.b("AuthnBusiness", "getScripAndToken start");
        concurrentBundle.a("methodChain", concurrentBundle.b("methodChain") + ",100005");
        concurrentBundle.a("methodCostTime", concurrentBundle.b("methodCostTime") + "," + (System.currentTimeMillis() - concurrentBundle.b("methodCostTimePre", 0L)));
        concurrentBundle.a("methodCostTimePre", System.currentTimeMillis());
        boolean zB = concurrentBundle.b("isGotScrip", false);
        LogUtils.b("AuthnBusiness", "isGotScrip = " + zB);
        if (!zB) {
            b(concurrentBundle);
            if (!concurrentBundle.b("isCacheScrip", false)) {
                c(concurrentBundle);
                if (concurrentBundle.c("networktype") == 3 && !"loginAuth".equals(concurrentBundle.b("loginMethod")) && concurrentBundle.c("logintype") != 3) {
                    concurrentBundle.a("isRisk", true);
                }
            }
            if (concurrentBundle.c("logintype") == 1) {
                concurrentBundle.a("userCapaid", "200");
            } else if (concurrentBundle.c("logintype") == 0) {
                concurrentBundle.a("userCapaid", "50");
            }
        }
        this.a.b(concurrentBundle, new RequestCallback() { // from class: com.cmic.gen.sdk.auth.a.2
            @Override // com.cmic.gen.sdk.c.c.RequestCallback
            public void a(String str, String str2, JSONObject jSONObject) throws JSONException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
                AuthnBusiness.this.a(concurrentBundle, authnCallback, str, str2, jSONObject);
            }
        });
    }

    private void b(ConcurrentBundle concurrentBundle) throws NoSuchAlgorithmException {
        String packageName = this.b.getPackageName();
        String strA = MD5STo16Byte.a(SignUtil2.a(this.b, packageName));
        concurrentBundle.a("apppackage", packageName);
        concurrentBundle.a("appsign", strA);
    }

    private void c(ConcurrentBundle concurrentBundle) throws UnsupportedEncodingException {
        byte[] bytes = new byte[0];
        if (concurrentBundle.b("use2048PublicKey", false)) {
            LogUtils.a("AuthnBusiness", "使用2048公钥对应的对称秘钥生成方式");
            bytes = AESUtils.a();
        } else {
            LogUtils.a("AuthnBusiness", "使用1024公钥对应的对称秘钥生成方式");
            try {
                bytes = UUID.randomUUID().toString().substring(0, 16).getBytes("utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        byte[] bArrA = AESUtils.a();
        concurrentBundle.a(Constants2.a.a, bytes);
        concurrentBundle.a(Constants2.a.b, bArrA);
        concurrentBundle.a("authType", "3");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0163  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(ConcurrentBundle concurrentBundle, AuthnCallback authnCallback, String str, String str2, JSONObject jSONObject) throws JSONException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        String strB;
        JSONException jSONException;
        String str3;
        JSONObject jSONObject2;
        String strOptString;
        String strOptString2;
        String strOptString3;
        JSONObject jSONObject3;
        String str4;
        String strOptString4;
        String str5;
        if ("103000".equals(str)) {
            String strOptString5 = jSONObject.optString("resultdata");
            if (TextUtils.isEmpty(strOptString5)) {
                strB = jSONObject.toString();
            } else {
                strB = AESUtils.b(concurrentBundle.a(Constants2.a.a), strOptString5, concurrentBundle.a(Constants2.a.b));
            }
            try {
                jSONObject3 = new JSONObject(strB);
            } catch (JSONException e) {
                jSONException = e;
                str3 = null;
                jSONObject2 = null;
            }
            try {
                strOptString2 = jSONObject3.optString("phonescrip");
                try {
                    strOptString4 = jSONObject3.optString("securityphone");
                } catch (JSONException e2) {
                    jSONException = e2;
                    jSONObject2 = jSONObject3;
                    str3 = null;
                }
                try {
                    strOptString = jSONObject3.optString("operatortype");
                } catch (JSONException e3) {
                    jSONException = e3;
                    str3 = strOptString4;
                    jSONObject2 = jSONObject3;
                    strOptString = null;
                    strOptString3 = null;
                    jSONException.printStackTrace();
                    jSONObject3 = jSONObject2;
                    str4 = strOptString2;
                    strOptString4 = str3;
                    str5 = strOptString3;
                    LogUtils.b("AuthnBusiness", "securityPhone  = " + strOptString4);
                    concurrentBundle.a("openId", str5);
                    concurrentBundle.a("phonescrip", str4);
                    concurrentBundle.a("securityphone", strOptString4);
                    if (!TextUtils.isEmpty(strOptString)) {
                    }
                    if (jSONObject3 != null) {
                    }
                }
            } catch (JSONException e4) {
                jSONException = e4;
                jSONObject2 = jSONObject3;
                str3 = null;
                strOptString = null;
                strOptString2 = null;
                strOptString3 = null;
                jSONException.printStackTrace();
                jSONObject3 = jSONObject2;
                str4 = strOptString2;
                strOptString4 = str3;
                str5 = strOptString3;
                LogUtils.b("AuthnBusiness", "securityPhone  = " + strOptString4);
                concurrentBundle.a("openId", str5);
                concurrentBundle.a("phonescrip", str4);
                concurrentBundle.a("securityphone", strOptString4);
                if (!TextUtils.isEmpty(strOptString)) {
                }
                if (jSONObject3 != null) {
                }
            }
            try {
                strOptString3 = jSONObject3.optString("openId");
            } catch (JSONException e5) {
                jSONException = e5;
                str3 = strOptString4;
                jSONObject2 = jSONObject3;
                strOptString3 = null;
                jSONException.printStackTrace();
                jSONObject3 = jSONObject2;
                str4 = strOptString2;
                strOptString4 = str3;
                str5 = strOptString3;
                LogUtils.b("AuthnBusiness", "securityPhone  = " + strOptString4);
                concurrentBundle.a("openId", str5);
                concurrentBundle.a("phonescrip", str4);
                concurrentBundle.a("securityphone", strOptString4);
                if (!TextUtils.isEmpty(strOptString)) {
                }
                if (jSONObject3 != null) {
                }
            }
            try {
                if (!jSONObject3.has("token")) {
                    if (jSONObject3.has("imageUrl") && jSONObject3.has("displayLogo")) {
                        SharedPreferencesUtil.a aVarA = SharedPreferencesUtil.a();
                        aVarA.a("imageUrl", jSONObject3.optString("imageUrl"));
                        aVarA.a("displayLogo", jSONObject3.optString("displayLogo"));
                        aVarA.b();
                    } else {
                        SharedPreferencesUtil.a aVarA2 = SharedPreferencesUtil.a();
                        aVarA2.a("imageUrl");
                        aVarA2.a("displayLogo");
                        aVarA2.b();
                    }
                }
                if (TextUtils.isEmpty(strOptString3)) {
                    strOptString3 = jSONObject3.optString("pcid");
                }
                KeystoreUtil.a();
                PhoneScripUtils.a(this.b, strOptString4, strOptString);
                str4 = strOptString2;
                str5 = strOptString3;
            } catch (JSONException e6) {
                jSONException = e6;
                str3 = strOptString4;
                jSONObject2 = jSONObject3;
                jSONException.printStackTrace();
                jSONObject3 = jSONObject2;
                str4 = strOptString2;
                strOptString4 = str3;
                str5 = strOptString3;
                LogUtils.b("AuthnBusiness", "securityPhone  = " + strOptString4);
                concurrentBundle.a("openId", str5);
                concurrentBundle.a("phonescrip", str4);
                concurrentBundle.a("securityphone", strOptString4);
                if (!TextUtils.isEmpty(strOptString)) {
                }
                if (jSONObject3 != null) {
                }
            }
            LogUtils.b("AuthnBusiness", "securityPhone  = " + strOptString4);
            concurrentBundle.a("openId", str5);
            concurrentBundle.a("phonescrip", str4);
            concurrentBundle.a("securityphone", strOptString4);
            if (!TextUtils.isEmpty(strOptString)) {
                concurrentBundle.a("operatortype", strOptString);
            }
            if (jSONObject3 != null) {
                if (!concurrentBundle.b("isRisk", false)) {
                    PhoneScripUtils.a(this.b, str4, strOptString4, Long.parseLong(jSONObject3.optString("scripExpiresIn", "0")), concurrentBundle.b("scripKey", ""), concurrentBundle.b("scripType", ""));
                }
                if (concurrentBundle.c("logintype") != 3) {
                    if (!concurrentBundle.b("isRisk", false)) {
                        authnCallback.a(str, str2, concurrentBundle, jSONObject3);
                        return;
                    }
                    concurrentBundle.a("isRisk", false);
                    concurrentBundle.a("isGotScrip", true);
                    b(concurrentBundle, authnCallback);
                    return;
                }
                authnCallback.a(str, "true", concurrentBundle, AuthnResult.a(strOptString4));
                return;
            }
            LogUtils.a("AuthnBusiness", "返回103000，但是数据解析出错");
            authnCallback.a(String.valueOf(102223), "数据解析异常", concurrentBundle, AuthnResult.a(String.valueOf(102223), "数据解析异常"));
            return;
        }
        if (concurrentBundle.c("logintype") == 3) {
            authnCallback.a(str, "true", concurrentBundle, AuthnResult.b(str, str2));
        } else {
            authnCallback.a(str, str2, concurrentBundle, jSONObject);
        }
    }
}