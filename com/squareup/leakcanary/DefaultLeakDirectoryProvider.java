package com.squareup.leakcanary;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import com.squareup.leakcanary.internal.RequestStoragePermissionActivity;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public final class DefaultLeakDirectoryProvider implements LeakDirectoryProvider {
    private static final int ANALYSIS_MAX_DURATION_MS = 600000;
    private static final int DEFAULT_MAX_STORED_HEAP_DUMPS = 7;
    private static final String HPROF_SUFFIX = ".hprof";
    private static final String PENDING_HEAPDUMP_SUFFIX = "_pending.hprof";
    private final Context context;
    private final int maxStoredHeapDumps;
    private volatile boolean permissionNotificationDisplayed;
    private volatile boolean writeExternalStorageGranted;

    public DefaultLeakDirectoryProvider(Context context) {
        this(context, 7);
    }

    public DefaultLeakDirectoryProvider(Context context, int maxStoredHeapDumps) {
        if (maxStoredHeapDumps < 1) {
            throw new IllegalArgumentException("maxStoredHeapDumps must be at least 1");
        }
        this.context = context.getApplicationContext();
        this.maxStoredHeapDumps = maxStoredHeapDumps;
    }

    @Override // com.squareup.leakcanary.LeakDirectoryProvider
    public List<File> listFiles(FilenameFilter filter) {
        if (!hasStoragePermission()) {
            requestWritePermissionNotification();
        }
        List<File> files = new ArrayList<>();
        File[] externalFiles = externalStorageDirectory().listFiles(filter);
        if (externalFiles != null) {
            files.addAll(Arrays.asList(externalFiles));
        }
        File[] appFiles = appStorageDirectory().listFiles(filter);
        if (appFiles != null) {
            files.addAll(Arrays.asList(appFiles));
        }
        return files;
    }

    @Override // com.squareup.leakcanary.LeakDirectoryProvider
    public File newHeapDumpFile() {
        List<File> pendingHeapDumps = listFiles(new FilenameFilter() { // from class: com.squareup.leakcanary.DefaultLeakDirectoryProvider.1
            @Override // java.io.FilenameFilter
            public boolean accept(File dir, String filename) {
                return filename.endsWith(DefaultLeakDirectoryProvider.PENDING_HEAPDUMP_SUFFIX);
            }
        });
        for (File file : pendingHeapDumps) {
            if (System.currentTimeMillis() - file.lastModified() < 600000) {
                CanaryLog.d("Could not dump heap, previous analysis still is in progress.", new Object[0]);
                return HeapDumper.RETRY_LATER;
            }
        }
        cleanupOldHeapDumps();
        File storageDirectory = externalStorageDirectory();
        if (!directoryWritableAfterMkdirs(storageDirectory)) {
            if (!hasStoragePermission()) {
                CanaryLog.d("WRITE_EXTERNAL_STORAGE permission not granted", new Object[0]);
                requestWritePermissionNotification();
            } else {
                String state = Environment.getExternalStorageState();
                if (!"mounted".equals(state)) {
                    CanaryLog.d("External storage not mounted, state: %s", state);
                } else {
                    CanaryLog.d("Could not create heap dump directory in external storage: [%s]", storageDirectory.getAbsolutePath());
                }
            }
            storageDirectory = appStorageDirectory();
            if (!directoryWritableAfterMkdirs(storageDirectory)) {
                CanaryLog.d("Could not create heap dump directory in app storage: [%s]", storageDirectory.getAbsolutePath());
                return HeapDumper.RETRY_LATER;
            }
        }
        return new File(storageDirectory, UUID.randomUUID().toString() + PENDING_HEAPDUMP_SUFFIX);
    }

    @Override // com.squareup.leakcanary.LeakDirectoryProvider
    public void clearLeakDirectory() {
        List<File> allFilesExceptPending = listFiles(new FilenameFilter() { // from class: com.squareup.leakcanary.DefaultLeakDirectoryProvider.2
            @Override // java.io.FilenameFilter
            public boolean accept(File dir, String filename) {
                return !filename.endsWith(DefaultLeakDirectoryProvider.PENDING_HEAPDUMP_SUFFIX);
            }
        });
        for (File file : allFilesExceptPending) {
            boolean deleted = file.delete();
            if (!deleted) {
                CanaryLog.d("Could not delete file %s", file.getPath());
            }
        }
    }

    private boolean hasStoragePermission() {
        if (Build.VERSION.SDK_INT < 23 || this.writeExternalStorageGranted) {
            return true;
        }
        this.writeExternalStorageGranted = this.context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        return this.writeExternalStorageGranted;
    }

    private void requestWritePermissionNotification() {
        if (this.permissionNotificationDisplayed) {
            return;
        }
        this.permissionNotificationDisplayed = true;
        PendingIntent pendingIntent = RequestStoragePermissionActivity.createPendingIntent(this.context);
        String contentTitle = this.context.getString(R.string.leak_canary_permission_notification_title);
        CharSequence packageName = this.context.getPackageName();
        String contentText = this.context.getString(R.string.leak_canary_permission_notification_text, packageName);
        LeakCanaryInternals.showNotification(this.context, contentTitle, contentText, pendingIntent, -558907665);
    }

    private File externalStorageDirectory() {
        File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        return new File(downloadsDirectory, "leakcanary-" + this.context.getPackageName());
    }

    private File appStorageDirectory() {
        File appFilesDirectory = this.context.getFilesDir();
        return new File(appFilesDirectory, "leakcanary");
    }

    private boolean directoryWritableAfterMkdirs(File directory) {
        boolean success = directory.mkdirs();
        return (success || directory.exists()) && directory.canWrite();
    }

    private void cleanupOldHeapDumps() {
        List<File> hprofFiles = listFiles(new FilenameFilter() { // from class: com.squareup.leakcanary.DefaultLeakDirectoryProvider.3
            @Override // java.io.FilenameFilter
            public boolean accept(File dir, String filename) {
                return filename.endsWith(DefaultLeakDirectoryProvider.HPROF_SUFFIX);
            }
        });
        int filesToRemove = hprofFiles.size() - this.maxStoredHeapDumps;
        if (filesToRemove > 0) {
            CanaryLog.d("Removing %d heap dumps", Integer.valueOf(filesToRemove));
            Collections.sort(hprofFiles, new Comparator<File>() { // from class: com.squareup.leakcanary.DefaultLeakDirectoryProvider.4
                @Override // java.util.Comparator
                public int compare(File lhs, File rhs) {
                    return Long.valueOf(lhs.lastModified()).compareTo(Long.valueOf(rhs.lastModified()));
                }
            });
            for (int i = 0; i < filesToRemove; i++) {
                boolean deleted = hprofFiles.get(i).delete();
                if (!deleted) {
                    CanaryLog.d("Could not delete old hprof file %s", hprofFiles.get(i).getPath());
                }
            }
        }
    }
}