package com.cmic.gen.sdk.c.b;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: GetSdkPageOptionParameter.java */
/* renamed from: com.cmic.gen.sdk.c.b.f, reason: use source file name */
/* loaded from: classes.dex */
public class GetSdkPageOptionParameter extends RequestParameter {
    private String a;
    private String b;
    private String c;

    public void b(String str) {
        this.a = str;
    }

    public void c(String str) {
        this.b = str;
    }

    public String c() {
        return this.b;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public String a() {
        return this.a;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sign", this.c);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    protected String a_(String str) {
        return "";
    }

    public void d(String str) {
        this.c = str;
    }
}