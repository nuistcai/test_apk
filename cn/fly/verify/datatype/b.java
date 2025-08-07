package cn.fly.verify.datatype;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import cn.fly.verify.v;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class b extends cn.fly.verify.a {
    private int a;
    private String b;
    private a c;

    private class a extends c {
        private String b;
        private String c;
        private long d;
        private String e;

        private a() {
        }
    }

    private b() {
        this.a = -1;
    }

    public b(String str) {
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
                this.c.c = jSONObjectOptJSONObject.optString("operatorType");
                this.c.d = jSONObjectOptJSONObject.optLong("expiredTime");
                if (jSONObjectOptJSONObject.has("number")) {
                    this.c.e = jSONObjectOptJSONObject.optString("number");
                    d(this.c.e);
                }
            }
        } catch (JSONException e) {
            v.a(e, "AccessCodeCtcc Parse JSONObject failed.");
            this.c = new a();
        }
        a(this.a == 0);
        if (this.c != null) {
            c(this.c.b);
            a(this.c.d);
            if (TextUtils.isEmpty(this.c.e)) {
                return;
            }
            d(this.c.e);
        }
    }

    public int i() {
        return this.a;
    }
}