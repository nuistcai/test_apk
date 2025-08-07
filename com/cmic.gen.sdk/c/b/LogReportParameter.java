package com.cmic.gen.sdk.c.b;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LogReportParameter.java */
/* renamed from: com.cmic.gen.sdk.c.b.g, reason: use source file name */
/* loaded from: classes.dex */
public class LogReportParameter extends RequestParameter {
    private b a;
    private a b;

    public void a(b bVar) {
        this.a = bVar;
    }

    public void a(a aVar) {
        this.b = aVar;
    }

    /* compiled from: LogReportParameter.java */
    /* renamed from: com.cmic.gen.sdk.c.b.g$b */
    public static class b extends RequestParameter {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;

        public String c() {
            return this.e;
        }

        public void b(String str) {
            this.e = str;
        }

        @Override // com.cmic.gen.sdk.c.b.RequestParameter
        public String a() {
            return this.d;
        }

        @Override // com.cmic.gen.sdk.c.b.RequestParameter
        public JSONObject b() {
            return null;
        }

        @Override // com.cmic.gen.sdk.c.b.RequestParameter
        protected String a_(String str) {
            return this.e + this.d + this.c + this.b + "@Fdiwmxy7CBDDQNUI";
        }

        public void c(String str) {
            this.d = str;
        }

        public String d() {
            return this.a;
        }

        public void d(String str) {
            this.a = str;
        }

        public String e() {
            return this.b;
        }

        public void e(String str) {
            this.b = str;
        }

        public String f() {
            return this.c;
        }

        public void f(String str) {
            this.c = str;
        }
    }

    /* compiled from: LogReportParameter.java */
    /* renamed from: com.cmic.gen.sdk.c.b.g$a */
    public static class a {
        private JSONObject a;

        public JSONObject a() {
            return this.a;
        }

        public void a(JSONObject jSONObject) {
            this.a = jSONObject;
        }
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public String a() {
        return this.a.d;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject2.put("sign", this.a.d());
            jSONObject2.put("msgid", this.a.e());
            jSONObject2.put("systemtime", this.a.f());
            jSONObject2.put("appid", this.a.a());
            jSONObject2.put("version", this.a.c());
            jSONObject.put("header", jSONObject2);
            jSONObject3.put("log", this.b.a());
            jSONObject.put("body", jSONObject3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    protected String a_(String str) {
        return null;
    }
}