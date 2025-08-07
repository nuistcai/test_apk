package com.squareup.leakcanary;

import com.squareup.leakcanary.HeapDump;
import com.squareup.leakcanary.Retryable;
import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class RefWatcher {
    public static final RefWatcher DISABLED = new RefWatcherBuilder().build();
    private final DebuggerControl debuggerControl;
    private final ExcludedRefs excludedRefs;
    private final GcTrigger gcTrigger;
    private final HeapDumper heapDumper;
    private final HeapDump.Listener heapdumpListener;
    private final WatchExecutor watchExecutor;
    private final Set<String> retainedKeys = new CopyOnWriteArraySet();
    private final ReferenceQueue<Object> queue = new ReferenceQueue<>();

    RefWatcher(WatchExecutor watchExecutor, DebuggerControl debuggerControl, GcTrigger gcTrigger, HeapDumper heapDumper, HeapDump.Listener heapdumpListener, ExcludedRefs excludedRefs) {
        this.watchExecutor = (WatchExecutor) Preconditions.checkNotNull(watchExecutor, "watchExecutor");
        this.debuggerControl = (DebuggerControl) Preconditions.checkNotNull(debuggerControl, "debuggerControl");
        this.gcTrigger = (GcTrigger) Preconditions.checkNotNull(gcTrigger, "gcTrigger");
        this.heapDumper = (HeapDumper) Preconditions.checkNotNull(heapDumper, "heapDumper");
        this.heapdumpListener = (HeapDump.Listener) Preconditions.checkNotNull(heapdumpListener, "heapdumpListener");
        this.excludedRefs = (ExcludedRefs) Preconditions.checkNotNull(excludedRefs, "excludedRefs");
    }

    public void watch(Object watchedReference) {
        watch(watchedReference, "");
    }

    public void watch(Object watchedReference, String referenceName) {
        if (this == DISABLED) {
            return;
        }
        Preconditions.checkNotNull(watchedReference, "watchedReference");
        Preconditions.checkNotNull(referenceName, "referenceName");
        long watchStartNanoTime = System.nanoTime();
        String key = UUID.randomUUID().toString();
        this.retainedKeys.add(key);
        KeyedWeakReference reference = new KeyedWeakReference(watchedReference, key, referenceName, this.queue);
        ensureGoneAsync(watchStartNanoTime, reference);
    }

    private void ensureGoneAsync(final long watchStartNanoTime, final KeyedWeakReference reference) {
        this.watchExecutor.execute(new Retryable() { // from class: com.squareup.leakcanary.RefWatcher.1
            @Override // com.squareup.leakcanary.Retryable
            public Retryable.Result run() {
                return RefWatcher.this.ensureGone(reference, watchStartNanoTime);
            }
        });
    }

    Retryable.Result ensureGone(KeyedWeakReference reference, long watchStartNanoTime) {
        long gcStartNanoTime = System.nanoTime();
        long watchDurationMs = TimeUnit.NANOSECONDS.toMillis(gcStartNanoTime - watchStartNanoTime);
        removeWeaklyReachableReferences();
        if (this.debuggerControl.isDebuggerAttached()) {
            return Retryable.Result.RETRY;
        }
        if (gone(reference)) {
            return Retryable.Result.DONE;
        }
        this.gcTrigger.runGc();
        removeWeaklyReachableReferences();
        if (!gone(reference)) {
            long startDumpHeap = System.nanoTime();
            long gcDurationMs = TimeUnit.NANOSECONDS.toMillis(startDumpHeap - gcStartNanoTime);
            File heapDumpFile = this.heapDumper.dumpHeap();
            if (heapDumpFile == HeapDumper.RETRY_LATER) {
                return Retryable.Result.RETRY;
            }
            long heapDumpDurationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startDumpHeap);
            this.heapdumpListener.analyze(new HeapDump(heapDumpFile, reference.key, reference.name, this.excludedRefs, watchDurationMs, gcDurationMs, heapDumpDurationMs));
        }
        return Retryable.Result.DONE;
    }

    private boolean gone(KeyedWeakReference reference) {
        return !this.retainedKeys.contains(reference.key);
    }

    private void removeWeaklyReachableReferences() {
        while (true) {
            KeyedWeakReference ref = (KeyedWeakReference) this.queue.poll();
            if (ref != null) {
                this.retainedKeys.remove(ref.key);
            } else {
                return;
            }
        }
    }
}