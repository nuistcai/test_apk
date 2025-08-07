package com.squareup.leakcanary;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.format.Formatter;
import android.util.Log;
import com.squareup.leakcanary.internal.DisplayLeakActivity;
import com.squareup.leakcanary.internal.HeapAnalyzerService;
import com.squareup.leakcanary.internal.LeakCanaryInternals;

/* loaded from: classes.dex */
public final class LeakCanary {
    public static RefWatcher install(Application application) {
        return refWatcher(application).listenerServiceClass(DisplayLeakService.class).excludedRefs(AndroidExcludedRefs.createAppDefaults().build()).buildAndInstall();
    }

    public static AndroidRefWatcherBuilder refWatcher(Context context) {
        return new AndroidRefWatcherBuilder(context);
    }

    public static void enableDisplayLeakActivity(Context context) {
        LeakCanaryInternals.setEnabled(context, DisplayLeakActivity.class, true);
    }

    public static void setDisplayLeakActivityDirectoryProvider(LeakDirectoryProvider leakDirectoryProvider) {
        DisplayLeakActivity.setLeakDirectoryProvider(leakDirectoryProvider);
    }

    public static String leakInfo(Context context, HeapDump heapDump, AnalysisResult result, boolean detailed) throws PackageManager.NameNotFoundException {
        String info;
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            String info2 = "In " + packageName + ":" + versionName + ":" + versionCode + ".\n";
            String detailedString = "";
            if (result.leakFound) {
                if (result.excludedLeak) {
                    info2 = info2 + "* EXCLUDED LEAK.\n";
                }
                String info3 = info2 + "* " + result.className;
                if (!heapDump.referenceName.equals("")) {
                    info3 = info3 + " (" + heapDump.referenceName + ")";
                }
                info = (info3 + " has leaked:\n" + result.leakTrace.toString() + "\n") + "* Retaining: " + Formatter.formatShortFileSize(context, result.retainedHeapSize) + ".\n";
                if (detailed) {
                    detailedString = "\n* Details:\n" + result.leakTrace.toDetailedString();
                }
            } else {
                info = result.failure != null ? info2 + "* FAILURE in 1.5.4 74837f0:" + Log.getStackTraceString(result.failure) + "\n" : info2 + "* NO LEAK FOUND.\n\n";
            }
            if (detailed) {
                detailedString = detailedString + "* Excluded Refs:\n" + heapDump.excludedRefs;
            }
            return info + "* Reference Key: " + heapDump.referenceKey + "\n* Device: " + Build.MANUFACTURER + " " + Build.BRAND + " " + Build.MODEL + " " + Build.PRODUCT + "\n* Android Version: " + Build.VERSION.RELEASE + " API: " + Build.VERSION.SDK_INT + " LeakCanary: " + BuildConfig.LIBRARY_VERSION + " " + BuildConfig.GIT_SHA + "\n* Durations: watch=" + heapDump.watchDurationMs + "ms, gc=" + heapDump.gcDurationMs + "ms, heap dump=" + heapDump.heapDumpDurationMs + "ms, analysis=" + result.analysisDurationMs + "ms\n" + detailedString;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isInAnalyzerProcess(Context context) {
        return LeakCanaryInternals.isInServiceProcess(context, HeapAnalyzerService.class);
    }

    private LeakCanary() {
        throw new AssertionError();
    }
}