package com.cmic.gen.sdk.c.c;

import android.util.Base64;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.Constants2;
import com.cmic.gen.sdk.a.UmcConfigBean;
import com.cmic.gen.sdk.auth.AuthnHelperCore;
import com.cmic.gen.sdk.auth.AuthnResult;
import com.cmic.gen.sdk.c.a.ConnectionInterceptor;
import com.cmic.gen.sdk.c.a.RetryAndRedirectInterceptor;
import com.cmic.gen.sdk.c.a.WifiChangeInterceptor;
import com.cmic.gen.sdk.c.b.GetConfigParameter;
import com.cmic.gen.sdk.c.b.GetPrePhoneScripParameter;
import com.cmic.gen.sdk.c.b.GetSdkPageOptionParameter;
import com.cmic.gen.sdk.c.b.LogReportParameter;
import com.cmic.gen.sdk.c.b.ScripAndTokenParameter;
import com.cmic.gen.sdk.c.d.HttpErrorResponse;
import com.cmic.gen.sdk.c.d.HttpSuccessResponse;
import com.cmic.gen.sdk.c.d.IResponse;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.RSAUtil;
import com.cmic.gen.sdk.e.SharedPreferencesUtil;
import com.cmic.gen.sdk.e.TelephonyUtils;
import com.cmic.gen.sdk.e.TimeUtils2;
import com.cmic.gen.sdk.e.UmcUtils;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: BaseRequest.java */
/* renamed from: com.cmic.gen.sdk.c.c.a, reason: use source file name */
/* loaded from: classes.dex */
public class BaseRequest {
    private static BaseRequest a = null;

    private BaseRequest() {
    }

    public static BaseRequest a() {
        if (a == null) {
            synchronized (BaseRequest.class) {
                if (a == null) {
                    a = new BaseRequest();
                }
            }
        }
        return a;
    }

    public void a(boolean z, ConcurrentBundle concurrentBundle, RequestCallback requestCallback) {
        GetConfigParameter getConfigParameter = new GetConfigParameter();
        getConfigParameter.b("1.0");
        getConfigParameter.c("Android");
        getConfigParameter.d(SharedPreferencesUtil.b("AID", ""));
        getConfigParameter.e(z ? "1" : "0");
        getConfigParameter.f(AuthnHelperCore.SDK_VERSION);
        getConfigParameter.g(concurrentBundle.b("appid"));
        getConfigParameter.h(getConfigParameter.x("iYm0HAnkxQtpvN44"));
        a(new HttpRequest("https://" + concurrentBundle.b().c() + "/client/uniConfig", getConfigParameter, "POST", concurrentBundle.b("traceId")), requestCallback, concurrentBundle);
    }

    public String a(String str) {
        LogUtils.a("BaseRequest", "sha256=" + str);
        try {
            return Base64.encodeToString(MessageDigest.getInstance("SHA-256").digest(str.getBytes("UTF-8")), 0).replace("\n", "");
        } catch (Exception e) {
            return null;
        }
    }

    public void a(ConcurrentBundle concurrentBundle, RequestCallback requestCallback) {
        GetSdkPageOptionParameter getSdkPageOptionParameter = new GetSdkPageOptionParameter();
        getSdkPageOptionParameter.b(concurrentBundle.b("appid"));
        getSdkPageOptionParameter.c(TimeUtils2.a());
        getSdkPageOptionParameter.d(a("1.0" + concurrentBundle.b("traceId") + getSdkPageOptionParameter.a() + getSdkPageOptionParameter.c() + concurrentBundle.b("appkey")));
        concurrentBundle.b();
        a(new HttpRequest("https://onekey2.cmpassport.com/unisdk/getSdkPageOption", getSdkPageOptionParameter, "POST", concurrentBundle.b("traceId")), requestCallback, concurrentBundle);
    }

    public void b(ConcurrentBundle concurrentBundle, RequestCallback requestCallback) throws JSONException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        HttpRequest httpRequest;
        String strA;
        LogUtils.b("BaseRequest", "time+" + System.currentTimeMillis());
        LogUtils.b("BaseRequest", "methodCostTimePre+" + concurrentBundle.b("methodCostTimePre", 0L));
        concurrentBundle.a("methodChain", concurrentBundle.b("methodChain") + ",100006");
        concurrentBundle.a("methodCostTime", concurrentBundle.b("methodCostTime") + "," + (System.currentTimeMillis() - concurrentBundle.b("methodCostTimePre", 0L)));
        concurrentBundle.a("methodCostTimePre", System.currentTimeMillis());
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put("stackInfo", jSONObject2);
            jSONObject2.put("appid", concurrentBundle.b("appid"));
            jSONObject2.put("packageName", concurrentBundle.b("apppackage"));
            jSONObject2.put("methodCostTime", concurrentBundle.b("methodCostTime"));
            jSONObject2.put("pathOfCallingMethod", concurrentBundle.b("pathOfCallingMethod"));
            jSONObject.put("methodChain", concurrentBundle.b("methodChain"));
            int iC = concurrentBundle.c("networktype");
            ScripAndTokenParameter scripAndTokenParameter = new ScripAndTokenParameter();
            scripAndTokenParameter.a(jSONObject);
            scripAndTokenParameter.b("1.0");
            scripAndTokenParameter.c(AuthnHelperCore.SDK_VERSION);
            scripAndTokenParameter.v(AuthnHelperCore.SDK_VERSION);
            scripAndTokenParameter.d(concurrentBundle.b("appid"));
            scripAndTokenParameter.e(concurrentBundle.b("operatortype"));
            scripAndTokenParameter.f(iC + "");
            scripAndTokenParameter.g(TelephonyUtils.a());
            scripAndTokenParameter.h(TelephonyUtils.b());
            scripAndTokenParameter.i(TelephonyUtils.c());
            scripAndTokenParameter.j("0");
            scripAndTokenParameter.k("3.1");
            scripAndTokenParameter.l(UmcUtils.b());
            scripAndTokenParameter.m(TimeUtils2.a());
            scripAndTokenParameter.o(concurrentBundle.b("apppackage"));
            scripAndTokenParameter.p(concurrentBundle.b("appsign"));
            scripAndTokenParameter.w(concurrentBundle.b("operator"));
            scripAndTokenParameter.a(SharedPreferencesUtil.b("AID", ""));
            if (concurrentBundle.c("logintype") != 3 && !concurrentBundle.b("isRisk", false)) {
                scripAndTokenParameter.z(concurrentBundle.b("userCapaid"));
                if (concurrentBundle.c("logintype") == 1) {
                    scripAndTokenParameter.z("200");
                } else {
                    scripAndTokenParameter.z("50");
                }
                scripAndTokenParameter.s("authz");
            } else {
                scripAndTokenParameter.s("pre");
            }
            UmcUtils.a(concurrentBundle, "scripAndTokenForHttps");
            UmcConfigBean umcConfigBeanB = concurrentBundle.b();
            if (concurrentBundle.b("isCacheScrip", false) || concurrentBundle.c("logintype") == 1 || concurrentBundle.b("isGotScrip", false)) {
                scripAndTokenParameter.y(concurrentBundle.b("phonescrip"));
                scripAndTokenParameter.n(scripAndTokenParameter.x(concurrentBundle.b("appkey")));
                httpRequest = new HttpRequest("https://" + umcConfigBeanB.a() + "/unisdk/rs/scripAndTokenForHttpsBx", scripAndTokenParameter, "POST", concurrentBundle.b("traceId"));
            } else {
                GetPrePhoneScripParameter getPrePhoneScripParameter = new GetPrePhoneScripParameter();
                getPrePhoneScripParameter.a(concurrentBundle.a(Constants2.a.a));
                getPrePhoneScripParameter.b(concurrentBundle.a(Constants2.a.b));
                getPrePhoneScripParameter.a(scripAndTokenParameter);
                getPrePhoneScripParameter.a(false);
                concurrentBundle.a("isCloseIpv4", umcConfigBeanB.h());
                concurrentBundle.a("isCloseIpv6", umcConfigBeanB.i());
                String str = "https://" + umcConfigBeanB.b() + "/unisdk/rs/scripAndTokenForHttpsBx";
                if (!concurrentBundle.b("use2048PublicKey", false)) {
                    strA = RSAUtil.a().a(concurrentBundle.a(Constants2.a.a));
                } else {
                    LogUtils.a("BaseRequest", "使用2对应的编码");
                    getPrePhoneScripParameter.b("2");
                    strA = RSAUtil.a().b(concurrentBundle.a(Constants2.a.a));
                }
                getPrePhoneScripParameter.c(strA);
                httpRequest = new HttpGetPrephoneRequest(str, getPrePhoneScripParameter, "POST", concurrentBundle.b("traceId"));
                if (iC == 3) {
                    httpRequest.a(true);
                    concurrentBundle.a("doNetworkSwitch", true);
                } else {
                    httpRequest.a(false);
                    concurrentBundle.a("doNetworkSwitch", false);
                }
            }
            httpRequest.a("dnsParseResult", "1");
            httpRequest.a("interfaceVersion", "3.1");
            a(httpRequest, requestCallback, concurrentBundle);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void a(JSONObject jSONObject, ConcurrentBundle concurrentBundle, RequestCallback requestCallback) {
        LogReportParameter logReportParameter = new LogReportParameter();
        LogReportParameter.a aVar = new LogReportParameter.a();
        LogReportParameter.b bVar = new LogReportParameter.b();
        bVar.e(UmcUtils.b());
        bVar.f(TimeUtils2.a());
        bVar.b("2.0");
        bVar.c(concurrentBundle.b("appid", ""));
        bVar.d(bVar.x(""));
        aVar.a(jSONObject);
        logReportParameter.a(aVar);
        logReportParameter.a(bVar);
        a(new HttpRequest("https://" + concurrentBundle.b().d() + "/log/logReport", logReportParameter, "POST", concurrentBundle.b("traceId")), requestCallback, concurrentBundle);
    }

    private void a(final HttpRequest httpRequest, final RequestCallback requestCallback, final ConcurrentBundle concurrentBundle) {
        WifiChangeInterceptor wifiChangeInterceptor = new WifiChangeInterceptor();
        RetryAndRedirectInterceptor retryAndRedirectInterceptor = new RetryAndRedirectInterceptor();
        ConnectionInterceptor connectionInterceptor = new ConnectionInterceptor();
        wifiChangeInterceptor.a(retryAndRedirectInterceptor);
        retryAndRedirectInterceptor.a(connectionInterceptor);
        wifiChangeInterceptor.a(httpRequest, new IResponse() { // from class: com.cmic.gen.sdk.c.c.a.1
            @Override // com.cmic.gen.sdk.c.d.IResponse
            public void a(HttpSuccessResponse httpSuccessResponse) throws JSONException {
                String string;
                if (httpRequest.g()) {
                    try {
                        JSONObject jSONObject = new JSONObject(httpSuccessResponse.c());
                        if (jSONObject.has("resultcode")) {
                            string = jSONObject.getString("resultcode");
                        } else {
                            string = jSONObject.getString("resultCode");
                        }
                        UmcUtils.b(concurrentBundle, string);
                        requestCallback.a(string, jSONObject.optString("desc"), jSONObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                        a(HttpErrorResponse.a(102223));
                    }
                }
            }

            @Override // com.cmic.gen.sdk.c.d.IResponse
            public void a(HttpErrorResponse httpErrorResponse) throws JSONException {
                if (httpRequest.g()) {
                    UmcUtils.b(concurrentBundle, String.valueOf(httpErrorResponse.a()));
                    requestCallback.a(String.valueOf(httpErrorResponse.a()), httpErrorResponse.b(), AuthnResult.a(String.valueOf(httpErrorResponse.a()), httpErrorResponse.b()));
                }
            }
        }, concurrentBundle);
    }
}