package cn.fly.verify.datatype;

/* loaded from: classes.dex */
public class f extends c {
    private boolean a;
    private String b;
    private String c;
    private int d;
    private long e;
    private String f;

    protected f() {
    }

    public f(String str) {
        this.b = str;
    }

    public void a(int i) {
        this.d = i;
    }

    protected void a(long j) {
        this.e = j;
    }

    protected void a(String str) {
        this.b = str;
    }

    protected void a(boolean z) {
        this.a = z;
    }

    public boolean a() {
        return this.a;
    }

    public String b() {
        return this.c;
    }

    protected void b(String str) {
        this.c = str;
    }

    public int c() {
        return this.d;
    }

    protected void c(String str) {
        this.f = str;
    }
}