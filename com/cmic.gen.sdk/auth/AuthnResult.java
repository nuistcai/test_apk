package com.cmic.gen.sdk.auth;

import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.e.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AuthnResult.java */
/* renamed from: com.cmic.gen.sdk.auth.d, reason: use source file name */
/* loaded from: classes.dex */
public class AuthnResult {
    public static JSONObject a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", str);
            jSONObject.put("desc", str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    static JSONObject a(String str, ConcurrentBundle concurrentBundle, JSONObject jSONObject) throws JSONException {
        String[] strArr = {"未知", "移动", "联通", "电信", "香港移动"};
        try {
            String strB = concurrentBundle.b("operatortype", "0");
            if ("0".equals(strB) || TextUtils.isEmpty(strB)) {
                if ("103000".equals(str)) {
                    jSONObject.put("operatorType", strArr[1]);
                } else {
                    jSONObject.put("operatorType", strArr[0]);
                }
            } else {
                jSONObject.put("operatorType", strArr[Integer.parseInt(strB)]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    static JSONObject a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", "103000");
            jSONObject.put("desc", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    static JSONObject b(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("resultCode", str);
            jSONObject.put("desc", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    static JSONObject a(String str, String str2, ConcurrentBundle concurrentBundle, JSONObject jSONObject) throws JSONException, NumberFormatException {
        String str3;
        String str4 = "0";
        JSONObject jSONObject2 = new JSONObject();
        try {
            int i = Integer.parseInt(concurrentBundle.b("authType", "0"));
            int iC = concurrentBundle.c("networktype");
            if (i == 3) {
                if (iC == 3) {
                    str3 = "WIFI下网关鉴权";
                    str4 = "1";
                } else {
                    str3 = "网关鉴权";
                    str4 = "2";
                }
            } else {
                str3 = "其他";
            }
            jSONObject2.put("resultCode", str);
            jSONObject2.put("authType", str4);
            jSONObject2.put("authTypeDes", str3);
            if ("103000".equals(str)) {
                if (1 == concurrentBundle.c("logintype")) {
                    jSONObject2.put("openId", concurrentBundle.b("openId"));
                }
                jSONObject2.put("token", jSONObject.optString("token"));
                jSONObject2.put("tokenExpiresIn", jSONObject.optString("tokenExpiresIn"));
            } else {
                jSONObject2.put("desc", str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.b("AuthnResult", "返回参数:" + jSONObject2.toString());
        return jSONObject2;
    }
}