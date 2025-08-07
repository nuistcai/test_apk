package com.squareup.leakcanary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/* loaded from: classes.dex */
public final class ActivityRefWatcher {
    private final Application application;
    private final Application.ActivityLifecycleCallbacks lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.squareup.leakcanary.ActivityRefWatcher.1
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            ActivityRefWatcher.this.onActivityDestroyed(activity);
        }
    };
    private final RefWatcher refWatcher;

    @Deprecated
    public static void installOnIcsPlus(Application application, RefWatcher refWatcher) {
        install(application, refWatcher);
    }

    public static void install(Application application, RefWatcher refWatcher) {
        new ActivityRefWatcher(application, refWatcher).watchActivities();
    }

    public ActivityRefWatcher(Application application, RefWatcher refWatcher) {
        this.application = (Application) Preconditions.checkNotNull(application, "application");
        this.refWatcher = (RefWatcher) Preconditions.checkNotNull(refWatcher, "refWatcher");
    }

    void onActivityDestroyed(Activity activity) {
        this.refWatcher.watch(activity);
    }

    public void watchActivities() {
        stopWatchingActivities();
        this.application.registerActivityLifecycleCallbacks(this.lifecycleCallbacks);
    }

    public void stopWatchingActivities() {
        this.application.unregisterActivityLifecycleCallbacks(this.lifecycleCallbacks);
    }
}