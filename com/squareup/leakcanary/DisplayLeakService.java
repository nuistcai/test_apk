package com.squareup.leakcanary;

import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.text.format.Formatter;
import com.squareup.leakcanary.internal.DisplayLeakActivity;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes.dex */
public class DisplayLeakService extends AbstractAnalysisResultService {
    @Override // com.squareup.leakcanary.AbstractAnalysisResultService
    protected final void onHeapAnalyzed(HeapDump heapDump, AnalysisResult result) throws PackageManager.NameNotFoundException, IOException {
        String contentTitle;
        String contentText;
        PendingIntent pendingIntent;
        String leakInfo = LeakCanary.leakInfo(this, heapDump, result, true);
        CanaryLog.d("%s", leakInfo);
        boolean resultSaved = false;
        boolean shouldSaveResult = result.leakFound || result.failure != null;
        if (shouldSaveResult) {
            heapDump = renameHeapdump(heapDump);
            resultSaved = saveResult(heapDump, result);
        }
        if (!shouldSaveResult) {
            contentTitle = getString(R.string.leak_canary_no_leak_title);
            contentText = getString(R.string.leak_canary_no_leak_text);
            pendingIntent = null;
        } else if (resultSaved) {
            pendingIntent = DisplayLeakActivity.createPendingIntent(this, heapDump.referenceKey);
            if (result.failure == null) {
                String size = Formatter.formatShortFileSize(this, result.retainedHeapSize);
                String className = LeakCanaryInternals.classSimpleName(result.className);
                if (result.excludedLeak) {
                    contentTitle = getString(R.string.leak_canary_leak_excluded, new Object[]{className, size});
                } else {
                    contentTitle = getString(R.string.leak_canary_class_has_leaked, new Object[]{className, size});
                }
            } else {
                contentTitle = getString(R.string.leak_canary_analysis_failed);
            }
            contentText = getString(R.string.leak_canary_notification_message);
        } else {
            contentTitle = getString(R.string.leak_canary_could_not_save_title);
            contentText = getString(R.string.leak_canary_could_not_save_text);
            pendingIntent = null;
        }
        int notificationId = (int) (SystemClock.uptimeMillis() / 1000);
        LeakCanaryInternals.showNotification(this, contentTitle, contentText, pendingIntent, notificationId);
        afterDefaultHandling(heapDump, result, leakInfo);
    }

    private boolean saveResult(HeapDump heapDump, AnalysisResult result) throws IOException {
        File resultFile = new File(heapDump.heapDumpFile.getParentFile(), heapDump.heapDumpFile.getName() + ".result");
        FileOutputStream fos = null;
        try {
            try {
                fos = new FileOutputStream(resultFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(heapDump);
                oos.writeObject(result);
                try {
                    fos.close();
                    return true;
                } catch (IOException e) {
                    return true;
                }
            } catch (Throwable th) {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e2) {
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            CanaryLog.d(e3, "Could not save leak analysis result to disk.", new Object[0]);
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e4) {
                }
            }
            return false;
        }
    }

    private HeapDump renameHeapdump(HeapDump heapDump) {
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS'.hprof'", Locale.US).format(new Date());
        File newFile = new File(heapDump.heapDumpFile.getParent(), fileName);
        boolean renamed = heapDump.heapDumpFile.renameTo(newFile);
        if (!renamed) {
            CanaryLog.d("Could not rename heap dump file %s to %s", heapDump.heapDumpFile.getPath(), newFile.getPath());
        }
        return new HeapDump(newFile, heapDump.referenceKey, heapDump.referenceName, heapDump.excludedRefs, heapDump.watchDurationMs, heapDump.gcDurationMs, heapDump.heapDumpDurationMs);
    }

    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
    }
}