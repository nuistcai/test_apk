package cn.com.chinatelecom.account.sdk.a;

import androidx.core.app.NotificationCompat;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class c {
    private static final String a = c.class.getSimpleName();

    public static e a(String str) {
        String string;
        e eVar = new e();
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt = jSONObject.optInt("result");
            String strOptString = jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE);
            String strOptString2 = jSONObject.optString("ifaaMsg");
            eVar.a(iOptInt);
            eVar.a(strOptString);
            eVar.g(strOptString2);
            jSONObject.remove("ifaaMsg");
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            if (iOptInt != 0 || jSONObjectOptJSONObject == null) {
                eVar.e(jSONObject.toString());
                string = jSONObject.toString();
            } else {
                long jOptLong = (jSONObjectOptJSONObject.optLong("expiredTime") * 1000) + System.currentTimeMillis();
                eVar.a(jOptLong);
                eVar.c(jSONObjectOptJSONObject.optString("number"));
                eVar.b(jSONObjectOptJSONObject.optString("operatorType"));
                eVar.d(cn.com.chinatelecom.account.api.b.a(jSONObjectOptJSONObject.optString("gwAuth")));
                jSONObjectOptJSONObject.put("expiredTime", jOptLong);
                jSONObjectOptJSONObject.remove("gwAuth");
                jSONObjectOptJSONObject.remove("number");
                jSONObject.put("data", jSONObjectOptJSONObject);
                eVar.e(jSONObject.toString());
                jSONObject.put("data", jSONObjectOptJSONObject);
                string = jSONObject.toString();
            }
            eVar.f(string);
            return eVar;
        } catch (Throwable th) {
            cn.com.chinatelecom.account.api.a.a(a, "parse result exception", th);
            return eVar;
        }
    }

    public static String a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.optInt("result") == 0) {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            jSONObjectOptJSONObject.put("authCode", str2);
            jSONObject.put("data", jSONObjectOptJSONObject);
        }
        return jSONObject.toString();
    }
}