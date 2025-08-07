package com.squareup.leakcanary;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue;
import com.squareup.leakcanary.Retryable;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: classes.dex */
public final class AndroidWatchExecutor implements WatchExecutor {
    static final String LEAK_CANARY_THREAD_NAME = "LeakCanary-Heap-Dump";
    private final Handler backgroundHandler;
    private final long initialDelayMillis;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final long maxBackoffFactor;

    public AndroidWatchExecutor(long initialDelayMillis) {
        HandlerThread handlerThread = new HandlerThread(LEAK_CANARY_THREAD_NAME);
        handlerThread.start();
        this.backgroundHandler = new Handler(handlerThread.getLooper());
        this.initialDelayMillis = initialDelayMillis;
        this.maxBackoffFactor = LongCompanionObject.MAX_VALUE / initialDelayMillis;
    }

    @Override // com.squareup.leakcanary.WatchExecutor
    public void execute(Retryable retryable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            waitForIdle(retryable, 0);
        } else {
            postWaitForIdle(retryable, 0);
        }
    }

    void postWaitForIdle(final Retryable retryable, final int failedAttempts) {
        this.mainHandler.post(new Runnable() { // from class: com.squareup.leakcanary.AndroidWatchExecutor.1
            @Override // java.lang.Runnable
            public void run() {
                AndroidWatchExecutor.this.waitForIdle(retryable, failedAttempts);
            }
        });
    }

    void waitForIdle(final Retryable retryable, final int failedAttempts) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.squareup.leakcanary.AndroidWatchExecutor.2
            @Override // android.os.MessageQueue.IdleHandler
            public boolean queueIdle() {
                AndroidWatchExecutor.this.postToBackgroundWithDelay(retryable, failedAttempts);
                return false;
            }
        });
    }

    void postToBackgroundWithDelay(final Retryable retryable, final int failedAttempts) {
        long exponentialBackoffFactor = (long) Math.min(Math.pow(2.0d, failedAttempts), this.maxBackoffFactor);
        long delayMillis = this.initialDelayMillis * exponentialBackoffFactor;
        this.backgroundHandler.postDelayed(new Runnable() { // from class: com.squareup.leakcanary.AndroidWatchExecutor.3
            @Override // java.lang.Runnable
            public void run() {
                Retryable.Result result = retryable.run();
                if (result == Retryable.Result.RETRY) {
                    AndroidWatchExecutor.this.postWaitForIdle(retryable, failedAttempts + 1);
                }
            }
        }, delayMillis);
    }
}