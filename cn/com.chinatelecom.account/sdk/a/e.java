package cn.com.chinatelecom.account.sdk.a;

/* loaded from: classes.dex */
public class e {
    private int a;
    private String b;
    private long c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;

    public int a() {
        return this.a;
    }

    public void a(int i) {
        this.a = i;
    }

    public void a(long j) {
        this.c = j;
    }

    public void a(String str) {
        this.b = str;
    }

    public String b() {
        return this.d;
    }

    public void b(String str) {
        this.d = str;
    }

    public String c() {
        return this.e;
    }

    public void c(String str) {
        this.e = str;
    }

    public String d() {
        return this.f;
    }

    public void d(String str) {
        this.f = str;
    }

    public String e() {
        return this.h;
    }

    public void e(String str) {
        this.h = str;
    }

    public String f() {
        return this.i;
    }

    public void f(String str) {
        this.i = str;
    }

    public void g(String str) {
        this.g = str;
    }

    public boolean g() {
        return this.a == 0 && this.c > System.currentTimeMillis() + 10000;
    }
}