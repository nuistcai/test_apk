package com.squareup.leakcanary;

import com.squareup.leakcanary.HeapDump;
import com.squareup.leakcanary.RefWatcherBuilder;

/* loaded from: classes.dex */
public class RefWatcherBuilder<T extends RefWatcherBuilder<T>> {
    private DebuggerControl debuggerControl;
    private ExcludedRefs excludedRefs;
    private GcTrigger gcTrigger;
    private HeapDump.Listener heapDumpListener;
    private HeapDumper heapDumper;
    private WatchExecutor watchExecutor;

    public final T heapDumpListener(HeapDump.Listener listener) {
        this.heapDumpListener = listener;
        return (T) self();
    }

    public final T excludedRefs(ExcludedRefs excludedRefs) {
        this.excludedRefs = excludedRefs;
        return (T) self();
    }

    public final T heapDumper(HeapDumper heapDumper) {
        this.heapDumper = heapDumper;
        return (T) self();
    }

    public final T debuggerControl(DebuggerControl debuggerControl) {
        this.debuggerControl = debuggerControl;
        return (T) self();
    }

    public final T watchExecutor(WatchExecutor watchExecutor) {
        this.watchExecutor = watchExecutor;
        return (T) self();
    }

    public final T gcTrigger(GcTrigger gcTrigger) {
        this.gcTrigger = gcTrigger;
        return (T) self();
    }

    public final RefWatcher build() {
        HeapDump.Listener heapDumpListener;
        DebuggerControl debuggerControl;
        HeapDumper heapDumper;
        WatchExecutor watchExecutor;
        GcTrigger gcTrigger;
        if (isDisabled()) {
            return RefWatcher.DISABLED;
        }
        ExcludedRefs excludedRefs = this.excludedRefs;
        if (excludedRefs == null) {
            excludedRefs = defaultExcludedRefs();
        }
        HeapDump.Listener heapDumpListener2 = this.heapDumpListener;
        if (heapDumpListener2 != null) {
            heapDumpListener = heapDumpListener2;
        } else {
            heapDumpListener = defaultHeapDumpListener();
        }
        DebuggerControl debuggerControl2 = this.debuggerControl;
        if (debuggerControl2 != null) {
            debuggerControl = debuggerControl2;
        } else {
            debuggerControl = defaultDebuggerControl();
        }
        HeapDumper heapDumper2 = this.heapDumper;
        if (heapDumper2 != null) {
            heapDumper = heapDumper2;
        } else {
            heapDumper = defaultHeapDumper();
        }
        WatchExecutor watchExecutor2 = this.watchExecutor;
        if (watchExecutor2 != null) {
            watchExecutor = watchExecutor2;
        } else {
            watchExecutor = defaultWatchExecutor();
        }
        GcTrigger gcTrigger2 = this.gcTrigger;
        if (gcTrigger2 != null) {
            gcTrigger = gcTrigger2;
        } else {
            gcTrigger = defaultGcTrigger();
        }
        return new RefWatcher(watchExecutor, debuggerControl, gcTrigger, heapDumper, heapDumpListener, excludedRefs);
    }

    protected boolean isDisabled() {
        return false;
    }

    protected GcTrigger defaultGcTrigger() {
        return GcTrigger.DEFAULT;
    }

    protected DebuggerControl defaultDebuggerControl() {
        return DebuggerControl.NONE;
    }

    protected ExcludedRefs defaultExcludedRefs() {
        return ExcludedRefs.builder().build();
    }

    protected HeapDumper defaultHeapDumper() {
        return HeapDumper.NONE;
    }

    protected HeapDump.Listener defaultHeapDumpListener() {
        return HeapDump.Listener.NONE;
    }

    protected WatchExecutor defaultWatchExecutor() {
        return WatchExecutor.NONE;
    }

    protected final T self() {
        return this;
    }
}