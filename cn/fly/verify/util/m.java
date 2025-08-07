package cn.fly.verify.util;

import cn.fly.verify.u;
import cn.fly.verify.v;

/* loaded from: classes.dex */
public class m {
    private boolean a;
    private Object b;
    private String c;
    private u d;

    public m(String str) {
        this.c = str == null ? getClass().getSimpleName() : str;
    }

    public synchronized Object a() {
        if (!this.a) {
            this.b = null;
            this.a = true;
            v.a(this.c + " do lock");
            return null;
        }
        try {
            v.a(this.c + " wait lock");
            wait();
            v.a(this.c + " after wait, result = " + this.b);
            return this.b;
        } catch (Throwable th) {
            v.a(th);
            return null;
        }
    }

    public void a(u uVar) {
        this.d = uVar;
        v.a("last:" + uVar.e());
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x000c A[Catch: all -> 0x002b, TRY_LEAVE, TryCatch #1 {all -> 0x002b, blocks: (B:8:0x0008, B:10:0x000c), top: B:19:0x0008, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void a(Object obj) {
        if (obj != null) {
            this.b = obj;
            try {
                if (this.a) {
                    v.a(this.c + " notify wait");
                    notifyAll();
                    this.a = false;
                }
            } catch (Throwable th) {
                v.a(th);
            }
        } else if (this.a) {
        }
    }

    public synchronized Object b() {
        if (!this.a) {
            return null;
        }
        try {
            v.a(this.c + " wait lock");
            wait();
            v.a(this.c + " after wait, result = " + this.b);
            return this.b;
        } catch (Throwable th) {
            v.a(th);
            return null;
        }
    }

    public u c() {
        v.a("last:" + this.d.e());
        return this.d;
    }
}