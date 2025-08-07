package com.squareup.leakcanary;

/* loaded from: classes.dex */
public interface WatchExecutor {
    public static final WatchExecutor NONE = new WatchExecutor() { // from class: com.squareup.leakcanary.WatchExecutor.1
        @Override // com.squareup.leakcanary.WatchExecutor
        public void execute(Retryable retryable) {
        }
    };

    void execute(Retryable retryable);
}