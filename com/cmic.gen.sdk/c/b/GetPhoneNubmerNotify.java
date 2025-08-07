package com.cmic.gen.sdk.c.b;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: GetPhoneNubmerNotify.java */
/* renamed from: com.cmic.gen.sdk.c.b.d, reason: use source file name */
/* loaded from: classes.dex */
public class GetPhoneNubmerNotify extends RequestParameter {
    private final String a;
    private final String b;
    private final String c;
    private String d = "authz";
    private String e;
    private String f;

    public GetPhoneNubmerNotify(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public void b(String str) {
        this.d = str;
    }

    public void c(String str) {
        this.e = str;
    }

    public void d(String str) {
        this.f = str;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public String a() {
        return this.a;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ver", this.b);
            jSONObject.put("data", this.c);
            jSONObject.put("operatortype", this.f);
            jSONObject.put("userCapaid", this.e);
            jSONObject.put("funcType", this.d);
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