package cn.fly.verify.datatype;

import androidx.core.app.NotificationCompat;
import cn.fly.verify.v;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class e extends f {
    private int a;
    private String b;
    private a c;

    private class a extends c {
        private String b;
        private long c;
        private String d;
        private String e;

        private a() {
        }
    }

    private e() {
        this.a = -1;
    }

    public e(String str) {
        super(str);
        this.a = -1;
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.a = jSONObject.optInt("result");
            this.b = jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            if (jSONObjectOptJSONObject != null) {
                this.c = new a();
                this.c.b = jSONObjectOptJSONObject.optString("accessCode");
                this.c.c = jSONObjectOptJSONObject.optLong("expiredTime");
                this.c.d = jSONObjectOptJSONObject.optString("expiredTime");
                this.c.e = jSONObjectOptJSONObject.optString("authCode");
            }
        } catch (JSONException e) {
            v.a(e, "LoginCtccToken Parse JSONObject failed.");
            this.c = new a();
        }
        super.a(this.a);
        super.a(this.a == 0);
        if (this.c != null) {
            super.b(this.c.b + ":" + this.c.e);
            super.a(this.c.c);
        }
    }
}