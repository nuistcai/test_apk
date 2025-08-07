package com.squareup.leakcanary;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.HeapDump;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class AndroidRefWatcherBuilder extends RefWatcherBuilder<AndroidRefWatcherBuilder> {
    private static final long DEFAULT_WATCH_DELAY_MILLIS = TimeUnit.SECONDS.toMillis(5);
    private final Context context;

    AndroidRefWatcherBuilder(Context context) {
        this.context = context.getApplicationContext();
    }

    public AndroidRefWatcherBuilder listenerServiceClass(Class<? extends AbstractAnalysisResultService> listenerServiceClass) {
        return heapDumpListener(new ServiceHeapDumpListener(this.context, listenerServiceClass));
    }

    public AndroidRefWatcherBuilder watchDelay(long delay, TimeUnit unit) {
        return watchExecutor(new AndroidWatchExecutor(unit.toMillis(delay)));
    }

    public AndroidRefWatcherBuilder maxStoredHeapDumps(int maxStoredHeapDumps) {
        LeakDirectoryProvider leakDirectoryProvider = new DefaultLeakDirectoryProvider(this.context, maxStoredHeapDumps);
        LeakCanary.setDisplayLeakActivityDirectoryProvider(leakDirectoryProvider);
        return heapDumper(new AndroidHeapDumper(this.context, leakDirectoryProvider));
    }

    public RefWatcher buildAndInstall() {
        RefWatcher refWatcher = build();
        if (refWatcher != RefWatcher.DISABLED) {
            LeakCanary.enableDisplayLeakActivity(this.context);
            ActivityRefWatcher.install((Application) this.context, refWatcher);
        }
        return refWatcher;
    }

    @Override // com.squareup.leakcanary.RefWatcherBuilder
    protected boolean isDisabled() {
        return LeakCanary.isInAnalyzerProcess(this.context);
    }

    @Override // com.squareup.leakcanary.RefWatcherBuilder
    protected HeapDumper defaultHeapDumper() {
        LeakDirectoryProvider leakDirectoryProvider = new DefaultLeakDirectoryProvider(this.context);
        return new AndroidHeapDumper(this.context, leakDirectoryProvider);
    }

    @Override // com.squareup.leakcanary.RefWatcherBuilder
    protected DebuggerControl defaultDebuggerControl() {
        return new AndroidDebuggerControl();
    }

    @Override // com.squareup.leakcanary.RefWatcherBuilder
    protected HeapDump.Listener defaultHeapDumpListener() {
        return new ServiceHeapDumpListener(this.context, DisplayLeakService.class);
    }

    @Override // com.squareup.leakcanary.RefWatcherBuilder
    protected ExcludedRefs defaultExcludedRefs() {
        return AndroidExcludedRefs.createAppDefaults().build();
    }

    @Override // com.squareup.leakcanary.RefWatcherBuilder
    protected WatchExecutor defaultWatchExecutor() {
        return new AndroidWatchExecutor(DEFAULT_WATCH_DELAY_MILLIS);
    }
}