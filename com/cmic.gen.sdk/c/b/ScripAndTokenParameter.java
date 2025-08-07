package com.cmic.gen.sdk.c.b;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ScripAndTokenParameter.java */
/* renamed from: com.cmic.gen.sdk.c.b.i, reason: use source file name */
/* loaded from: classes.dex */
public class ScripAndTokenParameter extends BaseScripParameter {
    protected String B = "";
    protected String C = "";

    @Override // com.cmic.gen.sdk.c.b.BaseScripParameter
    public void a(String str) {
        this.v = t(str);
    }

    public void y(String str) {
        this.B = t(str);
    }

    public void z(String str) {
        this.C = t(str);
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ver", this.a);
            jSONObject.put("sdkver", this.b);
            jSONObject.put("targetVersion", "0");
            jSONObject.put("appid", this.c);
            jSONObject.put("imsi", this.d);
            jSONObject.put("operatortype", this.e);
            jSONObject.put("networktype", this.f);
            jSONObject.put("mobilebrand", this.g);
            jSONObject.put("mobilemodel", this.h);
            jSONObject.put("mobilesystem", this.i);
            jSONObject.put("clienttype", this.j);
            jSONObject.put("interfacever", this.k);
            jSONObject.put("expandparams", this.l);
            jSONObject.put("msgid", this.m);
            jSONObject.put("timestamp", this.n);
            jSONObject.put("subimsi", this.o);
            jSONObject.put("sign", this.p);
            jSONObject.put("apppackage", this.q);
            jSONObject.put("appsign", this.r);
            jSONObject.put("ipv4_list", this.s);
            jSONObject.put("ipv6_list", this.t);
            jSONObject.put("sdkType", this.u);
            jSONObject.put("tempPDR", this.v);
            jSONObject.put("scrip", this.B);
            jSONObject.put("userCapaid", this.C);
            jSONObject.put("funcType", this.w);
            jSONObject.put("socketip", this.x);
            jSONObject.put("riskControlInfo", this.A);
            jSONObject.put("simOperator", this.z);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    protected String a_(String str) {
        return this.b + this.c + this.d + this.e + this.f + this.g + this.h + this.i + this.j + this.m + this.n + str + this.o + this.q + this.r + this.s + this.t + this.u + this.v + this.B + this.C + this.w + this.x + "0";
    }

    public String toString() {
        return b().toString();
    }
}