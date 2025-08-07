package cn.fly.tools.b;

import android.content.Context;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class c {
    private static c a = new c();
    private volatile Context b;
    private volatile a c;
    private volatile a d;
    private volatile a e;
    private final AtomicBoolean f = new AtomicBoolean(false);

    public static c a(Context context) {
        if (a.b == null && context != null) {
            a.b = context.getApplicationContext();
        }
        return a;
    }

    public CountDownLatch a() {
        b();
        return d.a(this.b).a();
    }

    public void b() {
        if (this.f.compareAndSet(false, true)) {
            d();
            c();
            e.a(this.b);
        }
    }

    public a c() {
        if (this.c == null) {
            this.c = new i(this.b);
        }
        return this.c;
    }

    public a d() {
        if (this.d == null) {
            this.d = new g(this.b);
        }
        return this.d;
    }

    public boolean a(a aVar) {
        this.e = aVar;
        return true;
    }

    public a e() {
        if (this.e == null) {
            return c();
        }
        return this.e;
    }
}