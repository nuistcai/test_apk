package com.squareup.leakcanary.internal;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Process;
import com.squareup.leakcanary.CanaryLog;
import com.squareup.leakcanary.R;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public final class LeakCanaryInternals {
    public static final String HUAWEI = "HUAWEI";
    public static final String LENOVO = "LENOVO";
    public static final String LG = "LGE";
    public static final String MEIZU = "Meizu";
    public static final String MOTOROLA = "motorola";
    private static final String NOTIFICATION_CHANNEL_ID = "leakcanary";
    public static final String NVIDIA = "NVIDIA";
    public static final String SAMSUNG = "samsung";
    public static final String VIVO = "vivo";
    private static final Executor fileIoExecutor = newSingleThreadExecutor("File-IO");

    public static void executeOnFileIoThread(Runnable runnable) {
        fileIoExecutor.execute(runnable);
    }

    public static String classSimpleName(String className) {
        int separator = className.lastIndexOf(46);
        if (separator == -1) {
            return className;
        }
        return className.substring(separator + 1);
    }

    public static void setEnabled(Context context, final Class<?> componentClass, final boolean enabled) {
        final Context appContext = context.getApplicationContext();
        executeOnFileIoThread(new Runnable() { // from class: com.squareup.leakcanary.internal.LeakCanaryInternals.1
            @Override // java.lang.Runnable
            public void run() {
                LeakCanaryInternals.setEnabledBlocking(appContext, componentClass, enabled);
            }
        });
    }

    public static void setEnabledBlocking(Context appContext, Class<?> componentClass, boolean enabled) {
        ComponentName component = new ComponentName(appContext, componentClass);
        PackageManager packageManager = appContext.getPackageManager();
        int newState = enabled ? 1 : 2;
        packageManager.setComponentEnabledSetting(component, newState, 1);
    }

    public static boolean isInServiceProcess(Context context, Class<? extends Service> serviceClass) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 4);
            String mainProcess = packageInfo.applicationInfo.processName;
            ComponentName component = new ComponentName(context, serviceClass);
            try {
                ServiceInfo serviceInfo = packageManager.getServiceInfo(component, 0);
                if (serviceInfo.processName.equals(mainProcess)) {
                    CanaryLog.d("Did not expect service %s to run in main process %s", serviceClass, mainProcess);
                    return false;
                }
                int myPid = Process.myPid();
                ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
                ActivityManager.RunningAppProcessInfo myProcess = null;
                List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
                if (runningProcesses != null) {
                    Iterator<ActivityManager.RunningAppProcessInfo> it = runningProcesses.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ActivityManager.RunningAppProcessInfo process = it.next();
                        if (process.pid == myPid) {
                            myProcess = process;
                            break;
                        }
                    }
                }
                if (myProcess == null) {
                    CanaryLog.d("Could not find running process for %d", Integer.valueOf(myPid));
                    return false;
                }
                return myProcess.processName.equals(serviceInfo.processName);
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        } catch (Exception e2) {
            CanaryLog.d(e2, "Could not get package info for %s", context.getPackageName());
            return false;
        }
    }

    public static void showNotification(Context context, CharSequence contentTitle, CharSequence contentText, PendingIntent pendingIntent, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        Notification.Builder builder = new Notification.Builder(context).setSmallIcon(R.drawable.leak_canary_notification).setWhen(System.currentTimeMillis()).setContentTitle(contentTitle).setContentText(contentText).setAutoCancel(true).setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= 26) {
            String channelName = context.getString(R.string.leak_canary_notification_channel);
            setupNotificationChannel(channelName, notificationManager, builder);
        }
        Notification notification = builder.build();
        notificationManager.notify(notificationId, notification);
    }

    private static void setupNotificationChannel(String channelName, NotificationManager notificationManager, Notification.Builder builder) {
        if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, 3);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
    }

    public static Executor newSingleThreadExecutor(String threadName) {
        return Executors.newSingleThreadExecutor(new LeakCanarySingleThreadFactory(threadName));
    }

    private LeakCanaryInternals() {
        throw new AssertionError();
    }
}