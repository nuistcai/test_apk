package com.cmic.gen.sdk.c;

import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.c.b.GetOtherPhoneNumber;
import com.cmic.gen.sdk.c.b.GetPhoneNubmerNotify;
import com.cmic.gen.sdk.c.b.RequestParameter;
import com.cmic.gen.sdk.c.c.HttpRequest;
import com.cmic.gen.sdk.c.d.HttpSuccessResponse;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.UmcUtils;
import java.util.List;
import java.util.Map;

/* compiled from: RedirectHandler.java */
/* renamed from: com.cmic.gen.sdk.c.b, reason: use source file name */
/* loaded from: classes.dex */
public class RedirectHandler {
    private String a;
    private String b;

    public HttpRequest a(HttpRequest httpRequest, HttpSuccessResponse httpSuccessResponse, ConcurrentBundle concurrentBundle) {
        List<String> list;
        Map<String, List<String>> mapB = httpSuccessResponse.b();
        if (TextUtils.isEmpty(this.a) && (list = mapB.get("pplocation")) != null && list.size() > 0) {
            this.a = list.get(0);
        }
        UmcUtils.b(concurrentBundle, String.valueOf(httpSuccessResponse.a()));
        List<String> list2 = mapB.get("Location");
        if (list2 == null || list2.isEmpty()) {
            list2 = mapB.get("Location".toLowerCase());
        }
        String strB = concurrentBundle.b("operatortype", "0");
        if (list2 != null && list2.size() > 0) {
            this.b = list2.get(0);
            if (!TextUtils.isEmpty(this.b)) {
                if ("2".equals(strB)) {
                    UmcUtils.a(concurrentBundle, "getUnicomMobile");
                } else if ("3".equals(strB)) {
                    UmcUtils.a(concurrentBundle, "getTelecomMobile");
                } else {
                    UmcUtils.a(concurrentBundle, "NONE");
                }
            }
        } else if ("2".equals(strB)) {
            UmcUtils.a(concurrentBundle, "getUCMobileCostTime1");
        } else if ("3".equals(strB)) {
            UmcUtils.a(concurrentBundle, "getTCMobileCostTime1");
        } else {
            UmcUtils.a(concurrentBundle, "NONE");
        }
        LogUtils.b("Location", this.b);
        HttpRequest httpRequestA = a(this.b, httpRequest.f(), "GET", new GetOtherPhoneNumber(httpRequest.k().a()));
        httpRequestA.a(httpRequest.h());
        return httpRequestA;
    }

    public HttpRequest b(HttpRequest httpRequest, HttpSuccessResponse httpSuccessResponse, ConcurrentBundle concurrentBundle) {
        String strB = concurrentBundle.b("operatortype", "0");
        if ("2".equals(strB)) {
            UmcUtils.a(concurrentBundle, "getNewUnicomPhoneNumberNotify");
        } else if ("3".equals(strB)) {
            UmcUtils.a(concurrentBundle, "getNewTelecomPhoneNumberNotify");
        } else {
            UmcUtils.a(concurrentBundle, "NONE");
        }
        UmcUtils.b(concurrentBundle, String.valueOf(httpSuccessResponse.a()));
        GetPhoneNubmerNotify getPhoneNubmerNotify = new GetPhoneNubmerNotify(httpRequest.k().a(), "1.0", httpSuccessResponse.c());
        getPhoneNubmerNotify.c(concurrentBundle.b("userCapaid"));
        getPhoneNubmerNotify.d(concurrentBundle.b("operatortype"));
        if (concurrentBundle.c("logintype") == 3 || concurrentBundle.b("isRisk", false)) {
            getPhoneNubmerNotify.b("pre");
        } else {
            getPhoneNubmerNotify.b("authz");
        }
        HttpRequest httpRequestA = a(this.a, httpRequest.f(), "POST", getPhoneNubmerNotify);
        httpRequestA.a(httpRequest.h());
        this.a = null;
        return httpRequestA;
    }

    private HttpRequest a(String str, String str2, String str3, RequestParameter requestParameter) {
        HttpRequest httpRequest = new HttpRequest(str, requestParameter, str3, str2);
        if (str3.equals("GET")) {
            httpRequest.a("Content-Type", "application/x-www-form-urlencoded");
        }
        return httpRequest;
    }

    public String a() {
        return this.a;
    }
}