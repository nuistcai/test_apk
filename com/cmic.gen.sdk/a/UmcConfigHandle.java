package com.cmic.gen.sdk.a;

import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.a.UmcConfigBean;
import com.cmic.gen.sdk.auth.AuthnHelperCore;
import com.cmic.gen.sdk.c.c.BaseRequest;
import com.cmic.gen.sdk.c.c.RequestCallback;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.SharedPreferencesUtil;
import com.cmic.gen.sdk.e.ThreadUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UmcConfigHandle.java */
/* renamed from: com.cmic.gen.sdk.a.b, reason: use source file name */
/* loaded from: classes.dex */
public class UmcConfigHandle {
    private static UmcConfigHandle c;
    private UmcConfigBean a;
    private a e;
    private volatile boolean d = false;
    private final UmcConfigBean b = new UmcConfigBean.a().a();

    /* compiled from: UmcConfigHandle.java */
    /* renamed from: com.cmic.gen.sdk.a.b$a */
    interface a {
        void a(UmcConfigBean umcConfigBean);
    }

    void a(a aVar) {
        this.e = aVar;
    }

    private UmcConfigHandle(boolean z) {
        if (!z) {
            this.a = d();
        } else {
            this.a = this.b;
        }
    }

    public static UmcConfigHandle a(boolean z) {
        if (c == null) {
            synchronized (UmcConfigHandle.class) {
                if (c == null) {
                    c = new UmcConfigHandle(z);
                }
            }
        }
        return c;
    }

    UmcConfigBean a() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ConcurrentBundle concurrentBundle) {
        if (this.d) {
            LogUtils.a("UmcConfigHandle", "正在获取配置中...");
        } else {
            this.d = true;
            BaseRequest.a().a(false, concurrentBundle, new RequestCallback() { // from class: com.cmic.gen.sdk.a.b.1
                @Override // com.cmic.gen.sdk.c.c.RequestCallback
                public void a(String str, String str2, JSONObject jSONObject) {
                    try {
                        if ("103000".equals(str)) {
                            UmcConfigHandle.this.a(jSONObject);
                            SharedPreferencesUtil.a("sdk_config_version", AuthnHelperCore.SDK_VERSION);
                            UmcConfigHandle.this.a = UmcConfigHandle.this.d();
                            if (UmcConfigHandle.this.e != null) {
                                UmcConfigHandle.this.e.a(UmcConfigHandle.this.a);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    UmcConfigHandle.this.d = false;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(JSONObject jSONObject) throws JSONException, NumberFormatException {
        SharedPreferencesUtil.a aVarB = SharedPreferencesUtil.b("sso_config_xf");
        try {
            if (jSONObject.has("client_valid")) {
                aVarB.a("client_valid", System.currentTimeMillis() + (Integer.parseInt(jSONObject.getString("client_valid")) * 60 * 60 * 1000));
            }
            if (jSONObject.has("Configlist")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("Configlist");
                if (jSONObject2.has("CHANGE_HOST")) {
                    String string = jSONObject2.getString("CHANGE_HOST");
                    if (string.contains("M007")) {
                        String strA = a(string, "M007");
                        if (!TextUtils.isEmpty(strA)) {
                            aVarB.a("logHost", strA);
                        }
                    }
                    if (string.contains("M008")) {
                        String strA2 = a(string, "M008");
                        if (!TextUtils.isEmpty(strA2)) {
                            aVarB.a("https_get_phone_scrip_host", strA2);
                        }
                    }
                    if (string.contains("M009")) {
                        String strA3 = a(string, "M009");
                        if (!TextUtils.isEmpty(strA3)) {
                            aVarB.a("config_host", strA3);
                        }
                    }
                } else {
                    aVarB.a("logHost");
                    aVarB.a("https_get_phone_scrip_host");
                    aVarB.a("config_host");
                }
                a(jSONObject2, "CLOSE_FRIEND_WAPKS", "0", aVarB);
                a(jSONObject2, "CLOSE_LOGS_VERSION", "0", aVarB);
                a(jSONObject2, "CLOSE_IPV4_LIST", "0", aVarB);
                a(jSONObject2, "CLOSE_IPV6_LIST", "0", aVarB);
                a(jSONObject2, "CLOSE_M008_SDKVERSION_LIST", "0", aVarB);
                a(jSONObject2, "CLOSE_M008_APPID_LIST", "0", aVarB);
                if (jSONObject2.has("LOGS_CONTROL")) {
                    String[] strArrSplit = jSONObject2.getString("LOGS_CONTROL").replace("h", "").split("&");
                    if (strArrSplit.length == 2 && !TextUtils.isEmpty(strArrSplit[0]) && !TextUtils.isEmpty(strArrSplit[1])) {
                        try {
                            int i = Integer.parseInt(strArrSplit[0]);
                            int i2 = Integer.parseInt(strArrSplit[1]);
                            aVarB.a("maxFailedLogTimes", i);
                            aVarB.a("pauseTime", i2);
                        } catch (Exception e) {
                            LogUtils.a("UmcConfigHandle", "解析日志上报限制时间次数异常");
                        }
                    }
                } else {
                    aVarB.a("maxFailedLogTimes");
                    aVarB.a("pauseTime");
                }
            }
            aVarB.b();
        } catch (Exception e2) {
            LogUtils.a("UmcConfigHandle", "配置项异常，配置失效");
            e2.printStackTrace();
        }
    }

    private String a(String str, String str2) {
        String str3;
        String[] strArrSplit = str.split("&");
        int length = strArrSplit.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                str3 = "";
                break;
            }
            str3 = strArrSplit[i];
            if (str3.contains(str2)) {
                break;
            }
            i++;
        }
        if (!TextUtils.isEmpty(str3)) {
            return str3.substring(str3.lastIndexOf("=") + 1);
        }
        return str3;
    }

    private void a(JSONObject jSONObject, String str, String str2, SharedPreferencesUtil.a aVar) {
        if (jSONObject.has(str)) {
            String strOptString = jSONObject.optString(str, str2);
            if (!"CLOSE_FRIEND_WAPKS".equals(str)) {
                if (!"0".equals(strOptString) && !"1".equals(strOptString)) {
                    return;
                }
            } else {
                if (TextUtils.isEmpty(strOptString)) {
                    return;
                }
                if (!strOptString.contains("CU") && !strOptString.contains("CT") && !strOptString.contains("CM")) {
                    return;
                }
            }
            aVar.a(str, jSONObject.optString(str, str2));
            return;
        }
        aVar.a(str);
    }

    UmcConfigBean b() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public UmcConfigBean d() {
        return new UmcConfigBean.a().a(UmcConfigUtil.b(this.b.a())).c(UmcConfigUtil.a(this.b.c())).b(UmcConfigUtil.b(this.b.b())).d(UmcConfigUtil.c(this.b.d())).d(UmcConfigUtil.a(this.b.h())).e(UmcConfigUtil.b(this.b.i())).a(UmcConfigUtil.e(this.b.e())).b(UmcConfigUtil.d(this.b.f())).c(UmcConfigUtil.c(this.b.g())).f(UmcConfigUtil.f(this.b.j())).a(UmcConfigUtil.a(this.b.k())).b(UmcConfigUtil.b(this.b.l())).a();
    }

    void a(final ConcurrentBundle concurrentBundle) {
        if (UmcConfigUtil.a()) {
            ThreadUtils.a(new ThreadUtils.a() { // from class: com.cmic.gen.sdk.a.b.2
                @Override // com.cmic.gen.sdk.e.ThreadUtils.a
                protected void a() {
                    LogUtils.b("UmcConfigHandle", "开始拉取配置..");
                    UmcConfigHandle.this.b(concurrentBundle);
                }
            });
        }
    }

    void c() {
        SharedPreferencesUtil.a aVarB = SharedPreferencesUtil.b("sso_config_xf");
        aVarB.c();
        aVarB.b();
    }
}