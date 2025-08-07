package com.unicom.xiaowo.account.shield.c;

import android.text.TextUtils;
import com.unicom.xiaowo.account.shield.c.a;
import com.unicom.xiaowo.account.shield.e.j;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
class b {
    private a.InterfaceC0040a a;

    public b(a.InterfaceC0040a interfaceC0040a) {
        this.a = null;
        this.a = interfaceC0040a;
    }

    public void a(String str, String str2) throws JSONException {
        try {
            if (this.a == null) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("resultCode", 0);
            jSONObject.put("resultMsg", str);
            if (!TextUtils.isEmpty(str2)) {
                JSONObject jSONObject2 = new JSONObject(str2);
                jSONObject2.put("msgId", j.a("" + System.currentTimeMillis()));
                jSONObject2.put("operatorType", (Object) null);
                jSONObject.put("resultData", jSONObject2);
            } else {
                jSONObject.put("resultData", "");
            }
            jSONObject.put("operatorType", "CU");
            this.a.a(jSONObject.toString());
            this.a = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(int i, String str, String str2) throws JSONException {
        try {
            if (this.a == null) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("resultCode", i);
            jSONObject.put("resultMsg", str);
            jSONObject.put("resultData", "");
            jSONObject.put("traceId", str2);
            jSONObject.put("operatorType", "CU");
            this.a.a(jSONObject.toString());
            this.a = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(int i, String str) throws JSONException {
        a(i, str, "");
    }
}