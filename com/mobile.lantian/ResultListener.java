package com.mobile.lantian;

/* loaded from: classes.dex */
public abstract class ResultListener<T> {
    public abstract void onComplete(T t);

    public abstract void onFailure(Throwable th);
}