package com.squareup.leakcanary;

import android.util.Log;

/* loaded from: classes.dex */
public final class CanaryLog {
    private static volatile Logger logger = new DefaultLogger();

    public interface Logger {
        void d(String str, Object... objArr);

        void d(Throwable th, String str, Object... objArr);
    }

    private static class DefaultLogger implements Logger {
        DefaultLogger() {
        }

        @Override // com.squareup.leakcanary.CanaryLog.Logger
        public void d(String message, Object... args) {
            String formatted = String.format(message, args);
            if (formatted.length() < 4000) {
                Log.d("LeakCanary", formatted);
                return;
            }
            String[] lines = formatted.split("\n");
            for (String line : lines) {
                Log.d("LeakCanary", line);
            }
        }

        @Override // com.squareup.leakcanary.CanaryLog.Logger
        public void d(Throwable throwable, String message, Object... args) {
            d(String.format(message, args) + '\n' + Log.getStackTraceString(throwable), new Object[0]);
        }
    }

    public static void setLogger(Logger logger2) {
        logger = logger2;
    }

    public static void d(String message, Object... args) {
        Logger logger2 = logger;
        if (logger2 == null) {
            return;
        }
        logger2.d(message, args);
    }

    public static void d(Throwable throwable, String message, Object... args) {
        Logger logger2 = logger;
        if (logger2 == null) {
            return;
        }
        logger2.d(throwable, message, args);
    }

    private CanaryLog() {
        throw new AssertionError();
    }
}