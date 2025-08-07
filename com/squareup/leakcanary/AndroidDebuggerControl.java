package com.squareup.leakcanary;

import android.os.Debug;

/* loaded from: classes.dex */
public final class AndroidDebuggerControl implements DebuggerControl {
    @Override // com.squareup.leakcanary.DebuggerControl
    public boolean isDebuggerAttached() {
        return Debug.isDebuggerConnected();
    }
}