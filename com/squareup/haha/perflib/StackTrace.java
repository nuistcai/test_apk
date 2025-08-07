package com.squareup.haha.perflib;

/* loaded from: classes.dex */
public class StackTrace {
    StackFrame[] mFrames;
    int mSerialNumber;
    int mThreadSerialNumber;
    StackTrace mParent = null;
    int mOffset = 0;

    private StackTrace() {
    }

    public StackTrace(int serial, int thread, StackFrame[] frames) {
        this.mSerialNumber = serial;
        this.mThreadSerialNumber = thread;
        this.mFrames = frames;
    }

    public final StackTrace fromDepth(int startingDepth) {
        StackTrace result = new StackTrace();
        if (this.mParent != null) {
            result.mParent = this.mParent;
        } else {
            result.mParent = this;
        }
        result.mOffset = this.mOffset + startingDepth;
        return result;
    }

    public final void dump() {
        int N = this.mFrames.length;
        for (int i = 0; i < N; i++) {
            System.out.println(this.mFrames[i].toString());
        }
    }
}