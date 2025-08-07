package com.cmic.gen.sdk.c.b;

import org.json.JSONObject;

/* compiled from: GetOtherPhoneNumber.java */
/* renamed from: com.cmic.gen.sdk.c.b.c, reason: use source file name */
/* loaded from: classes.dex */
public class GetOtherPhoneNumber extends RequestParameter {
    private final String a;

    public GetOtherPhoneNumber(String str) {
        this.a = str;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public String a() {
        return this.a;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public JSONObject b() {
        return new JSONObject();
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    protected String a_(String str) {
        return null;
    }
}