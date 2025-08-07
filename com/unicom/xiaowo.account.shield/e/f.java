package com.unicom.xiaowo.account.shield.e;

import android.os.Build;
import android.text.TextUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class f {
    private ExecutorService a;

    public f() {
        this.a = null;
        this.a = Executors.newFixedThreadPool(1);
    }

    public void finalize() {
        if (this.a != null) {
            this.a.shutdownNow();
            this.a = null;
        }
    }

    public void a(String str, String str2) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appId", h.a());
            jSONObject.put("deviceId", h.i());
            jSONObject.put("brand", Build.BRAND);
            jSONObject.put("model", Build.MODEL);
            jSONObject.put("os", "" + Build.VERSION.SDK_INT);
            jSONObject.put("errCode", str);
            jSONObject.put("message", str2);
            jSONObject.put("sdkVersion", "5.2.0AR002B1125");
            jSONObject.put("apn", h.d());
            jSONObject.put("appName", h.h());
            jSONObject.put("pip", h.e());
            jSONObject.put("netType", "" + h.g());
            jSONObject.put("userTimeout", "" + h.f());
            jSONObject.put("operateTime", "0");
            final String string = jSONObject.toString();
            if (!TextUtils.isEmpty(string)) {
                if (this.a == null) {
                    this.a = Executors.newFixedThreadPool(1);
                }
                this.a.submit(new Runnable() { // from class: com.unicom.xiaowo.account.shield.e.f.1
                    @Override // java.lang.Runnable
                    public void run() throws Throwable {
                        try {
                            new com.unicom.xiaowo.account.shield.d.b().a("https://opencloud.wostore.cn/client/sdk/receive", string);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}