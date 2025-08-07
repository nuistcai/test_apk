package com.squareup.haha.perflib;

/* loaded from: classes.dex */
public final class HahaSpy {
    public static Instance allocatingThread(Instance instance) {
        int threadSerialNumber;
        Snapshot snapshot = instance.mHeap.mSnapshot;
        if (instance instanceof RootObj) {
            threadSerialNumber = ((RootObj) instance).mThread;
        } else {
            threadSerialNumber = instance.mStack.mThreadSerialNumber;
        }
        ThreadObj thread = snapshot.getThread(threadSerialNumber);
        return snapshot.findInstance(thread.mId);
    }

    private HahaSpy() {
        throw new AssertionError();
    }
}