package com.squareup.leakcanary;

/* loaded from: classes.dex */
public interface DebuggerControl {
    public static final DebuggerControl NONE = new DebuggerControl() { // from class: com.squareup.leakcanary.DebuggerControl.1
        @Override // com.squareup.leakcanary.DebuggerControl
        public boolean isDebuggerAttached() {
            return false;
        }
    };

    boolean isDebuggerAttached();
}