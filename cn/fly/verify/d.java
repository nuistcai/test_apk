package cn.fly.verify;

import cn.fly.verify.common.exception.VerifyException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public abstract class d<T> {
    public AtomicBoolean a = new AtomicBoolean();

    public abstract void a(VerifyException verifyException);

    public abstract void a(T t);
}