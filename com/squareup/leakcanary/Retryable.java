package com.squareup.leakcanary;

/* loaded from: classes.dex */
public interface Retryable {

    public enum Result {
        DONE,
        RETRY
    }

    Result run();
}