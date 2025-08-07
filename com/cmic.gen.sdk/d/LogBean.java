package com.cmic.gen.sdk.d;

import android.text.TextUtils;
import com.cmic.gen.sdk.auth.AuthnHelperCore;
import com.cmic.gen.sdk.c.b.RequestParameter;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LogBean.java */
/* renamed from: com.cmic.gen.sdk.d.b, reason: use source file name */
/* loaded from: classes.dex */
public class LogBean extends RequestParameter {
    private String A;
    private JSONArray o;
    private String x;
    private String y;
    private String z;
    private String b = null;
    private String c = null;
    private String d = null;
    private String e = null;
    private String f = null;
    private String g = null;
    private String h = null;
    private String i = null;
    private String j = null;
    private String k = "";
    private String l = null;
    private String m = null;
    private String n = null;
    private String p = null;
    private String q = null;
    private String r = null;
    private String s = null;
    private String t = null;
    private String u = null;
    private String v = null;
    private String w = null;
    public CopyOnWriteArrayList<Throwable> a = new CopyOnWriteArrayList<>();

    public void b(String str) {
        this.b = str;
    }

    public void c(String str) {
        this.v = str;
    }

    public void d(String str) {
        this.w = str;
    }

    public void e(String str) {
        this.r = str;
    }

    public void f(String str) {
        this.m = str;
    }

    public void g(String str) {
        this.l = str;
    }

    public void h(String str) {
        this.k = str;
    }

    public void i(String str) {
        this.d = str;
    }

    public void j(String str) {
        this.e = str;
    }

    public void k(String str) {
        this.f = str;
    }

    public void l(String str) {
        this.i = str;
    }

    void a(JSONArray jSONArray) {
        this.o = jSONArray;
    }

    void m(String str) {
        this.u = str;
    }

    void n(String str) {
        this.p = str;
    }

    void o(String str) {
        this.s = str;
    }

    void p(String str) {
        this.t = str;
    }

    public void q(String str) {
        this.n = str;
    }

    void r(String str) {
        this.c = str;
    }

    public void s(String str) {
        this.g = str;
    }

    public void t(String str) {
        this.h = str;
    }

    public void u(String str) {
        this.j = str;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public String a() {
        return null;
    }

    void v(String str) {
        this.q = str;
    }

    public void w(String str) {
        this.x = str;
    }

    public void y(String str) {
        this.y = str;
    }

    public void z(String str) {
        this.z = str;
    }

    public void A(String str) {
        this.A = str;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appid", this.b);
            jSONObject.put("traceId", this.c);
            jSONObject.put("appName", this.d);
            jSONObject.put("appVersion", this.e);
            jSONObject.put("sdkVersion", AuthnHelperCore.SDK_VERSION);
            jSONObject.put("clientType", "android");
            jSONObject.put("timeOut", this.f);
            jSONObject.put("requestTime", this.g);
            jSONObject.put("responseTime", this.h);
            jSONObject.put("elapsedTime", this.i);
            jSONObject.put("requestType", this.j);
            jSONObject.put("interfaceType", this.k);
            jSONObject.put("interfaceCode", this.l);
            jSONObject.put("interfaceElasped", this.m);
            jSONObject.put("loginType", this.n);
            jSONObject.put("exceptionStackTrace", this.o);
            jSONObject.put("operatorType", this.p);
            jSONObject.put("networkType", this.q);
            jSONObject.put("brand", this.r);
            jSONObject.put("reqDevice", this.s);
            jSONObject.put("reqSystem", this.t);
            jSONObject.put("simCardNum", this.u);
            jSONObject.put("imsiState", this.v);
            jSONObject.put("resultCode", this.w);
            jSONObject.put("AID", this.x);
            jSONObject.put("sysOperType", this.y);
            jSONObject.put("scripType", this.z);
            if (!TextUtils.isEmpty(this.A)) {
                jSONObject.put("networkTypeByAPI", this.A);
            }
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