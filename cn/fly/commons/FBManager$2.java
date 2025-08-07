package cn.fly.commons;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import cn.fly.tools.utils.ActivityTracker;

/* loaded from: classes.dex */
class FBManager$2 implements ActivityTracker.Tracker {
    FBManager$2() {
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
            this.a.f = SystemClock.elapsedRealtime();
            if (this.a.e == 0) {
                this.a.e = SystemClock.elapsedRealtime();
                if (this.a.c != null) {
                    this.a.c.sendEmptyMessage(1);
                }
            }
            this.a.d = activity == null ? null : activity.toString();
        } catch (Throwable th) {
        }
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onPaused(Activity activity) {
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onStopped(Activity activity) {
        long jElapsedRealtime;
        try {
            if (this.a.d != null) {
                if (!this.a.d.equals(activity == null ? null : activity.toString())) {
                    return;
                }
            }
            if (this.a.c != null) {
                if (this.a.e > 0) {
                    jElapsedRealtime = SystemClock.elapsedRealtime() - this.a.e;
                } else {
                    jElapsedRealtime = 0;
                }
                Message message = new Message();
                message.what = 2;
                message.obj = Long.valueOf(jElapsedRealtime);
                this.a.c.sendMessage(message);
            }
            this.a.e = 0L;
            this.a.d = null;
        } catch (Throwable th) {
        }
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onDestroyed(Activity activity) {
        if (this.a.e > 0) {
            onStopped(activity);
        }
    }

    @Override // cn.fly.tools.utils.ActivityTracker.Tracker
    public void onSaveInstanceState(Activity activity, Bundle bundle) {
    }
}