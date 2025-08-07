package cn.fly.verify;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import cn.fly.FlySDK;
import cn.fly.tools.utils.UIHandler;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.datatype.VerifyResult;

/* loaded from: classes.dex */
public abstract class w {
    public String b;
    public String c;
    public int d;
    public Integer e;
    public String f;
    protected d<VerifyResult> g;
    protected u h;
    public String i;
    protected boolean j;
    protected boolean k;
    private int n;
    private int m = 1;
    protected int l = 4000;
    protected Context a = FlySDK.getContext();

    /* JADX INFO: Access modifiers changed from: private */
    public void a(a aVar, d<a> dVar, u uVar) {
        if (dVar != null) {
            if (uVar != null) {
                uVar.a(this.i, this.b, "success_retry_count", String.valueOf(this.n));
                uVar.a(this.i, this.b, "cell_wifi", String.valueOf(d()));
            }
            dVar.a((d<a>) aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(VerifyException verifyException, d<a> dVar, u uVar) {
        if (dVar != null) {
            if (this.m <= 0 || this.k) {
                if (uVar != null) {
                    uVar.a(this.i, this.b, "failure_retry_count", String.valueOf(this.n));
                    uVar.a(this.i, this.b, "cell_wifi", String.valueOf(d()));
                }
                dVar.a(verifyException);
                this.m = 1;
                this.n = 0;
                return;
            }
            this.m--;
            this.n++;
            v.a("retry count = " + this.n);
            if (uVar != null) {
                uVar.a(this.i, this.b, "retry", String.valueOf(this.n));
                uVar.a(this.i, this.b, "cell_wifi", String.valueOf(d()));
            }
            a(dVar);
        }
    }

    private int d() {
        String strG = cn.fly.verify.util.e.g();
        if (this.j && "wifi".equalsIgnoreCase(strG)) {
            return 0;
        }
        if (this.j) {
            return 1;
        }
        if ("wifi".equalsIgnoreCase(strG)) {
            return 2;
        }
        return "none".equalsIgnoreCase(strG) ? 4 : 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(final d<a> dVar) {
        b(new d<a>() { // from class: cn.fly.verify.w.2
            /* JADX WARN: Type inference failed for: r0v2, types: [cn.fly.verify.w$2$1] */
            @Override // cn.fly.verify.d
            public void a(final a aVar) {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    new z() { // from class: cn.fly.verify.w.2.1
                        @Override // cn.fly.verify.z
                        protected void a() {
                            w.this.a(aVar, (d<a>) dVar, w.this.h);
                        }
                    }.start();
                } else {
                    w.this.a(aVar, (d<a>) dVar, w.this.h);
                }
            }

            /* JADX WARN: Type inference failed for: r0v2, types: [cn.fly.verify.w$2$2] */
            @Override // cn.fly.verify.d
            public void a(final VerifyException verifyException) {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    new z() { // from class: cn.fly.verify.w.2.2
                        @Override // cn.fly.verify.z
                        protected void a() {
                            w.this.a(verifyException, (d<a>) dVar, w.this.h);
                        }
                    }.start();
                } else {
                    w.this.a(verifyException, (d<a>) dVar, w.this.h);
                }
            }
        });
    }

    public int a() {
        int i = i.a().i();
        int iE = e.e();
        if (i > 0) {
            this.l = i;
        } else if (iE > 0) {
            this.l = iE;
        }
        return this.l;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [cn.fly.verify.w$1] */
    public void a(final d<a> dVar) {
        if (Looper.getMainLooper() == Looper.myLooper() || this.k) {
            new z() { // from class: cn.fly.verify.w.1
                @Override // cn.fly.verify.z
                protected void a() {
                    w.this.f(dVar);
                }
            }.start();
        } else {
            f(dVar);
        }
    }

    public void a(u uVar) {
        this.h = uVar;
    }

    public void a(String str) {
        e.a.set(str);
        v.a(this.i + " init , appid = " + str);
    }

    public void a(boolean z) {
        this.k = z;
    }

    public abstract void b();

    public void b(final d<a> dVar) {
        long jA = a() + 1000;
        v.a("pre starttime " + jA);
        UIHandler.sendEmptyMessageDelayed(0, jA, new Handler.Callback() { // from class: cn.fly.verify.w.3
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (dVar == null || dVar.a.getAndSet(true)) {
                    return false;
                }
                v.a("pre timeout");
                dVar.a(new VerifyException(m.INNER_TIMEOUT_ERR));
                return false;
            }
        });
        c(new d<a>() { // from class: cn.fly.verify.w.4
            @Override // cn.fly.verify.d
            public void a(a aVar) {
                if (dVar == null || !dVar.a.getAndSet(true)) {
                    if (dVar != null) {
                        v.a("pre success");
                        dVar.a((d) aVar);
                        return;
                    }
                    return;
                }
                v.a("pre success after timeout");
                if (w.this.h != null) {
                    w.this.h.a(w.this.i, w.this.b, "timeout_success");
                    w.this.h.b();
                }
            }

            @Override // cn.fly.verify.d
            public void a(VerifyException verifyException) {
                if (dVar != null && !dVar.a.getAndSet(true)) {
                    v.a("pre fail");
                    dVar.a(verifyException);
                    return;
                }
                if (dVar != null) {
                    v.a("pre fail after timeout");
                    if (w.this.h != null) {
                        t tVarB = w.this.h.b("timeout_error");
                        tVarB.d(w.this.b);
                        tVarB.e(w.this.i);
                        tVarB.b(verifyException.getCode());
                        tVarB.c(verifyException.getMessage());
                        w.this.h.a(tVarB);
                        w.this.h.b();
                    }
                }
            }
        });
    }

    public abstract void c();

    public abstract void c(d<a> dVar);

    public void d(final d<VerifyResult> dVar) {
        long jF = e.f();
        v.a("verify starttimeout " + jF);
        UIHandler.sendEmptyMessageDelayed(0, jF, new Handler.Callback() { // from class: cn.fly.verify.w.5
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (dVar == null || dVar.a.getAndSet(true)) {
                    return false;
                }
                v.a("auth timeout");
                dVar.a(new VerifyException(m.INNER_VERIFY_TIMEOUT_ERR));
                return false;
            }
        });
        e(dVar);
    }

    public abstract void e(d<VerifyResult> dVar);
}