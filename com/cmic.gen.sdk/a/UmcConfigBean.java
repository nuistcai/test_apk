package com.cmic.gen.sdk.a;

/* compiled from: UmcConfigBean.java */
/* renamed from: com.cmic.gen.sdk.a.a, reason: use source file name */
/* loaded from: classes.dex */
public class UmcConfigBean implements Cloneable {
    private String a;
    private String b;
    private String c;
    private String d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private int k;
    private int l;

    private UmcConfigBean() {
        this.a = "rcs.cmpassport.com";
        this.b = "rcs.cmpassport.com";
        this.c = "config2.cmpassport.com";
        this.d = "log2.cmpassport.com:9443";
        this.e = false;
        this.f = false;
        this.g = false;
        this.h = false;
        this.i = false;
        this.j = false;
        this.k = 3;
        this.l = 1;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    public String d() {
        return this.d;
    }

    public boolean e() {
        return this.e;
    }

    public boolean f() {
        return this.f;
    }

    public boolean g() {
        return this.g;
    }

    public boolean h() {
        return this.h;
    }

    public boolean i() {
        return this.i;
    }

    public boolean j() {
        return this.j;
    }

    public int k() {
        return this.k;
    }

    public int l() {
        return this.l;
    }

    /* compiled from: UmcConfigBean.java */
    /* renamed from: com.cmic.gen.sdk.a.a$a */
    public static class a {
        private final UmcConfigBean a = new UmcConfigBean();

        a a(String str) {
            this.a.a = str;
            return this;
        }

        a b(String str) {
            this.a.b = str;
            return this;
        }

        a c(String str) {
            this.a.c = str;
            return this;
        }

        a d(String str) {
            this.a.d = str;
            return this;
        }

        a a(boolean z) {
            this.a.e = z;
            return this;
        }

        a b(boolean z) {
            this.a.f = z;
            return this;
        }

        a c(boolean z) {
            this.a.g = z;
            return this;
        }

        a d(boolean z) {
            this.a.h = z;
            return this;
        }

        a e(boolean z) {
            this.a.i = z;
            return this;
        }

        a f(boolean z) {
            this.a.j = z;
            return this;
        }

        a a(int i) {
            this.a.k = i;
            return this;
        }

        a b(int i) {
            this.a.l = i;
            return this;
        }

        public UmcConfigBean a() {
            return this.a;
        }
    }

    /* renamed from: m, reason: merged with bridge method [inline-methods] */
    public UmcConfigBean clone() throws CloneNotSupportedException {
        return (UmcConfigBean) super.clone();
    }
}