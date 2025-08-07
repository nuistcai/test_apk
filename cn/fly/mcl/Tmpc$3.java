package cn.fly.mcl;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import cn.fly.tools.utils.ActivityTracker;

/* loaded from: classes.dex */
class Tmpc$3 implements ActivityTracker.Tracker {
    private long b;
    private String c;

    Tmpc$3() {
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onCreated(Activity activity, Bundle bundle) {
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onStarted(Activity activity) {
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onResumed(Activity activity) {
        try {
            if (this.b == 0) {
                this.b = SystemClock.elapsedRealtime();
                c0009a.a();
            }
            this.c = activity == null ? null : activity.toString();
        } catch (Throwable th) {
        }
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onPaused(Activity activity) {
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onStopped(Activity activity) {
        try {
            if (this.c != null) {
                if (!this.c.equals(activity == null ? null : activity.toString())) {
                    return;
                }
            }
            this.b = 0L;
            this.c = null;
            c0009a.b();
        } catch (Throwable th) {
        }
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onDestroyed(Activity activity) {
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onSaveInstanceState(Activity activity, Bundle bundle) {
    }
}