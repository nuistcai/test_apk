package com.cmic.gen.sdk.c.b;

import android.util.Base64;
import com.cmic.gen.sdk.e.AESUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: GetPrePhoneScripParameter.java */
/* renamed from: com.cmic.gen.sdk.c.b.e, reason: use source file name */
/* loaded from: classes.dex */
public class GetPrePhoneScripParameter extends RequestParameter {
    private BaseScripParameter a;
    private byte[] b;
    private String c;
    private byte[] d;
    private String e;
    private boolean f = false;

    public void a(boolean z) {
        this.f = z;
    }

    public void b(String str) {
        this.e = str;
    }

    public void a(byte[] bArr) {
        this.b = bArr;
    }

    public void c(String str) {
        this.c = str;
    }

    public void b(byte[] bArr) {
        this.d = bArr;
    }

    public void a(BaseScripParameter baseScripParameter) {
        this.a = baseScripParameter;
    }

    public BaseScripParameter c() {
        return this.a;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public String a() {
        return this.a.a();
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (this.f) {
            try {
                jSONObject.put("encrypted", this.c);
                jSONObject.put("encryptedIV", Base64.encodeToString(this.d, 0));
                jSONObject.put("reqdata", AESUtils.a(this.b, this.a.toString(), this.d));
                jSONObject.put("securityreinforce", this.e);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    protected String a_(String str) {
        return null;
    }
}