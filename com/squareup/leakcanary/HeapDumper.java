package com.squareup.leakcanary;

import java.io.File;

/* loaded from: classes.dex */
public interface HeapDumper {
    public static final HeapDumper NONE = new HeapDumper() { // from class: com.squareup.leakcanary.HeapDumper.1
        @Override // com.squareup.leakcanary.HeapDumper
        public File dumpHeap() {
            return RETRY_LATER;
        }
    };
    public static final File RETRY_LATER = null;

    File dumpHeap();
}