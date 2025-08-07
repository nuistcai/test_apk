package com.squareup.leakcanary;

import java.io.Serializable;

/* loaded from: classes.dex */
public final class AnalysisResult implements Serializable {
    public final long analysisDurationMs;
    public final String className;
    public final boolean excludedLeak;
    public final Throwable failure;
    public final boolean leakFound;
    public final LeakTrace leakTrace;
    public final long retainedHeapSize;

    public static AnalysisResult noLeak(long analysisDurationMs) {
        return new AnalysisResult(false, false, null, null, null, 0L, analysisDurationMs);
    }

    public static AnalysisResult leakDetected(boolean excludedLeak, String className, LeakTrace leakTrace, long retainedHeapSize, long analysisDurationMs) {
        return new AnalysisResult(true, excludedLeak, className, leakTrace, null, retainedHeapSize, analysisDurationMs);
    }

    public static AnalysisResult failure(Throwable failure, long analysisDurationMs) {
        return new AnalysisResult(false, false, null, null, failure, 0L, analysisDurationMs);
    }

    private AnalysisResult(boolean leakFound, boolean excludedLeak, String className, LeakTrace leakTrace, Throwable failure, long retainedHeapSize, long analysisDurationMs) {
        this.leakFound = leakFound;
        this.excludedLeak = excludedLeak;
        this.className = className;
        this.leakTrace = leakTrace;
        this.failure = failure;
        this.retainedHeapSize = retainedHeapSize;
        this.analysisDurationMs = analysisDurationMs;
    }
}