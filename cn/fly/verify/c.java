package cn.fly.verify;

import cn.fly.verify.common.exception.VerifyException;

/* loaded from: classes.dex */
public abstract class c<T> {
    private T a;
    private Throwable b;

    public T a() {
        try {
            this.a = d();
        } catch (VerifyException e) {
            this.b = e;
        }
        return this.a;
    }

    public T b() {
        return this.a;
    }

    public Throwable c() {
        return this.b;
    }

    public abstract T d() throws VerifyException;
}