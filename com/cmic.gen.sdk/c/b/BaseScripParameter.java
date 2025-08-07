package com.cmic.gen.sdk.c.b;

import java.net.URLEncoder;
import org.json.JSONObject;

/* compiled from: BaseScripParameter.java */
/* renamed from: com.cmic.gen.sdk.c.b.a, reason: use source file name */
/* loaded from: classes.dex */
public abstract class BaseScripParameter extends RequestParameter {
    protected String a = "";
    protected String b = "";
    protected String c = "";
    protected String d = "";
    protected String e = "";
    protected String f = "";
    protected String g = "";
    protected String h = "";
    protected String i = "";
    protected String j = "0";
    protected String k = "1.0";
    protected String l = "";
    protected String m = "";
    protected String n = "";
    protected String o = "";
    protected String p = "";
    protected String q = "";
    protected String r = "";
    protected String s = "";
    protected String t = "";
    protected String u = "001";
    protected String v = "";
    protected String w = "";
    protected String x = "";
    protected String y = "";
    protected String z = "";
    protected JSONObject A = null;

    public void a(String str) {
        this.v = str;
    }

    public void b(String str) {
        this.a = t(str);
    }

    public void c(String str) {
        this.b = t(str);
    }

    public void d(String str) {
        this.c = t(str);
    }

    public void e(String str) {
        this.e = t(str);
    }

    public void f(String str) {
        this.f = t(str);
    }

    public void g(String str) {
        this.g = URLEncoder.encode(t(str));
    }

    public void h(String str) {
        this.h = URLEncoder.encode(t(str));
    }

    public void i(String str) {
        this.i = URLEncoder.encode(t(str));
    }

    public void j(String str) {
        this.j = t(str);
    }

    public void k(String str) {
        this.k = t(str);
    }

    public void l(String str) {
        this.m = t(str);
    }

    public void m(String str) {
        this.n = t(str);
    }

    public void n(String str) {
        this.p = t(str);
    }

    public void o(String str) {
        this.q = t(str);
    }

    public void p(String str) {
        this.r = t(str);
    }

    public void q(String str) {
        this.s = t(str);
    }

    public void r(String str) {
        this.t = t(str);
    }

    public void s(String str) {
        this.w = str;
    }

    protected final String t(String str) {
        return str == null ? "" : str;
    }

    public void u(String str) {
        this.x = str;
    }

    public void v(String str) {
        this.y = str;
    }

    public void a(JSONObject jSONObject) {
        this.A = jSONObject;
    }

    public void w(String str) {
        this.z = str;
    }

    @Override // com.cmic.gen.sdk.c.b.RequestParameter
    public String a() {
        return this.c;
    }
}