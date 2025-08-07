package cn.fly.verify;

/* loaded from: classes.dex */
public class t {
    private String a;
    private String b;
    private int c;
    private String d;
    private int e;
    private String f;
    private long g;
    private long h;
    private long i;
    private boolean j;
    private boolean k;
    private String l;
    private boolean m;
    private String o;
    private String p;
    private boolean q;
    private String s;
    private Integer t;
    private Integer u;
    private Integer v;
    private Integer w;
    private boolean r = false;
    private final long n = System.currentTimeMillis();

    public t(g gVar, String str) {
        String str2;
        switch (gVar) {
            case INIT:
                str2 = "init";
                break;
            case PREVERIFY:
                str2 = "preVerify";
                break;
            case AUTHPAGE:
                str2 = "authPageOpend";
                break;
            case VERIFY:
                str2 = "verify";
                break;
            default:
                this.b = str;
        }
        this.a = str2;
        this.b = str;
    }

    public void a(int i) {
        this.c = i;
    }

    public void a(long j) {
        this.g = j;
    }

    public void a(Integer num) {
        this.t = num;
    }

    public void a(String str) {
        this.p = str;
    }

    public void a(boolean z) {
        this.r = z;
    }

    public boolean a() {
        return this.r;
    }

    public String b() {
        return this.p;
    }

    public void b(int i) {
        this.e = i;
    }

    public void b(long j) {
        this.h = j;
    }

    public void b(Integer num) {
        this.v = num;
    }

    public void b(String str) {
        this.d = str;
    }

    public void b(boolean z) {
        this.m = z;
    }

    public String c() {
        return this.a;
    }

    public void c(int i) {
        this.u = Integer.valueOf(i);
    }

    public void c(long j) {
        this.i = j;
    }

    public void c(Integer num) {
        this.w = num;
    }

    public void c(String str) {
        this.f = str;
    }

    public void c(boolean z) {
        this.q = z;
    }

    public String d() {
        return this.b;
    }

    public void d(String str) {
        this.l = str;
    }

    public int e() {
        return this.c;
    }

    public void e(String str) {
        this.o = str;
    }

    public String f() {
        return this.d;
    }

    public void f(String str) {
        this.s = str;
    }

    public int g() {
        return this.e;
    }

    public String h() {
        return this.f;
    }

    public long i() {
        return this.g;
    }

    public long j() {
        return this.h;
    }

    public long k() {
        return this.i;
    }

    public boolean l() {
        return this.j;
    }

    public boolean m() {
        return this.k;
    }

    public String n() {
        return this.l;
    }

    public boolean o() {
        return this.m;
    }

    public boolean p() {
        return this.q;
    }

    public String q() {
        return this.o;
    }

    public String r() {
        return this.s;
    }

    public Integer s() {
        return this.t;
    }

    public Integer t() {
        return this.u;
    }

    public Integer u() {
        return this.v;
    }

    public Integer v() {
        return this.w;
    }
}