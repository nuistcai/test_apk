package com.squareup.leakcanary;

import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import com.squareup.leakcanary.internal.FutureResult;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class AndroidHeapDumper implements HeapDumper {
    final Context context;
    private final LeakDirectoryProvider leakDirectoryProvider;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public AndroidHeapDumper(Context context, LeakDirectoryProvider leakDirectoryProvider) {
        this.leakDirectoryProvider = leakDirectoryProvider;
        this.context = context.getApplicationContext();
    }

    @Override // com.squareup.leakcanary.HeapDumper
    public File dumpHeap() throws IOException {
        File heapDumpFile = this.leakDirectoryProvider.newHeapDumpFile();
        if (heapDumpFile == RETRY_LATER) {
            return RETRY_LATER;
        }
        FutureResult<Toast> waitingForToast = new FutureResult<>();
        showToast(waitingForToast);
        if (!waitingForToast.wait(5L, TimeUnit.SECONDS)) {
            CanaryLog.d("Did not dump heap, too much time waiting for Toast.", new Object[0]);
            return RETRY_LATER;
        }
        Toast toast = waitingForToast.get();
        try {
            Debug.dumpHprofData(heapDumpFile.getAbsolutePath());
            cancelToast(toast);
            return heapDumpFile;
        } catch (Exception e) {
            CanaryLog.d(e, "Could not dump heap", new Object[0]);
            return RETRY_LATER;
        }
    }

    private void showToast(final FutureResult<Toast> waitingForToast) {
        this.mainHandler.post(new Runnable() { // from class: com.squareup.leakcanary.AndroidHeapDumper.1
            @Override // java.lang.Runnable
            public void run() {
                final Toast toast = new Toast(AndroidHeapDumper.this.context);
                toast.setGravity(16, 0, 0);
                toast.setDuration(1);
                LayoutInflater inflater = LayoutInflater.from(AndroidHeapDumper.this.context);
                toast.setView(inflater.inflate(R.layout.leak_canary_heap_dump_toast, (ViewGroup) null));
                toast.show();
                Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.squareup.leakcanary.AndroidHeapDumper.1.1
                    @Override // android.os.MessageQueue.IdleHandler
                    public boolean queueIdle() {
                        waitingForToast.set(toast);
                        return false;
                    }
                });
            }
        });
    }

    private void cancelToast(final Toast toast) {
        this.mainHandler.post(new Runnable() { // from class: com.squareup.leakcanary.AndroidHeapDumper.2
            @Override // java.lang.Runnable
            public void run() {
                toast.cancel();
            }
        });
    }
}