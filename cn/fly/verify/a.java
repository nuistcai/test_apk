package cn.fly.verify;

import java.io.Serializable;

/* loaded from: classes.dex */
public class a implements Serializable {
    private boolean a;
    private String b;
    private String c;
    private long d;
    private String e;
    private String f;
    private String g;
    private String h;
    private int i;

    protected a() {
    }

    public a(String str) {
        this();
        this.b = str;
    }

    public int a() {
        return this.i;
    }

    public void a(int i) {
        this.i = i;
    }

    public void a(long j) {
        this.d = j;
    }

    public void a(String str) {
        this.g = str;
    }

    protected void a(boolean z) {
        this.a = z;
    }

    public String b() {
        return this.g;
    }

    protected void b(String str) {
        this.b = str;
    }

    public void c(String str) {
        this.c = str;
    }

    public boolean c() {
        return this.a;
    }

    public String d() {
        return this.b;
    }

    public void d(String str) {
        this.e = str;
    }

    public String e() {
        return this.c;
    }

    public void e(String str) {
        this.h = str;
    }

    public long f() {
        return this.d;
    }

    public boolean f(String str) {
        return f() > System.currentTimeMillis() && str.equals(b()) && cn.fly.verify.util.l.g() == a();
    }

    public String g() {
        return this.e;
    }

    public String h() {
        return this.h;
    }

    public String toString() {
        return "AccessCode{success=" + this.a + ", resp='" + this.b + "', accessCode='" + this.c + "', expireAt=" + this.d + ", securityPhone='" + this.e + "', gwAuth='" + this.f + "', appId='" + this.g + "', carrier='" + this.h + "'}";
    }
}