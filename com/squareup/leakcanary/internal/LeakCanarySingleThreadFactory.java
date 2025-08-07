package com.squareup.leakcanary.internal;

import java.util.concurrent.ThreadFactory;

/* loaded from: classes.dex */
final class LeakCanarySingleThreadFactory implements ThreadFactory {
    private final String threadName;

    LeakCanarySingleThreadFactory(String threadName) {
        this.threadName = "LeakCanary-" + threadName;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, this.threadName);
    }
}