package com.cmic.gen.sdk.d;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LogBeanEx.java */
/* renamed from: com.cmic.gen.sdk.d.c, reason: use source file name */
/* loaded from: classes.dex */
public class LogBeanEx extends LogBean {
    public static ArrayList<Throwable> b = new ArrayList<>();
    private JSONObject c = null;
    private JSONArray d;

    public void a(JSONObject jSONObject) {
        this.c = jSONObject;
    }

    @Override // com.cmic.gen.sdk.d.LogBean
    public void a(JSONArray jSONArray) {
        this.d = jSONArray;
    }

    @Override // com.cmic.gen.sdk.d.LogBean, com.cmic.gen.sdk.c.b.RequestParameter
    public JSONObject b() throws JSONException {
        JSONObject jSONObjectB = super.b();
        try {
            jSONObjectB.put(NotificationCompat.CATEGORY_EVENT, this.c);
            jSONObjectB.put("exceptionStackTrace", this.d);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObjectB;
    }
}