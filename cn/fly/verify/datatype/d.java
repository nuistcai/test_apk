package cn.fly.verify.datatype;

import cn.fly.verify.v;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class d extends f {
    private int a;
    private a b;

    private class a extends c {
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;
        private int g;

        private a() {
        }
    }

    private d() {
        this.a = -1;
    }

    public d(int i, JSONObject jSONObject) {
        this.a = -1;
        this.a = i;
        this.b = new a();
        if (jSONObject != null) {
            this.b.b = jSONObject.optString("resultCode");
            this.b.c = jSONObject.optString("authType");
            this.b.d = jSONObject.optString("authTypeDes");
            this.b.f = jSONObject.optString("token");
            this.b.e = jSONObject.optString("openId");
            this.b.g = jSONObject.optInt("SDKRequestCode");
        }
        try {
            super.a(Integer.parseInt(this.b.b));
        } catch (Throwable th) {
            v.a(th, "LoginCmccToken Parse resultCode error");
        }
        super.a("103000".equals(this.b.b));
        super.b(this.b.f);
        super.c(this.b.e);
        HashMap map = new HashMap();
        map.put("result", Integer.valueOf(i));
        map.put("jsonObject", jSONObject);
        super.a(new JSONObject(map).toString());
    }
}