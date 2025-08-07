package cn.fly.tools.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.util.HashSet;

/* loaded from: classes.dex */
public class ActivityTracker implements PublicMemberKeeper {
    private static ActivityTracker a;
    private HashSet<Tracker> b = new HashSet<>();

    private interface EachTracker extends PublicMemberKeeper {
        void each(Tracker tracker);
    }

    public interface Tracker extends PublicMemberKeeper {
        void onCreated(Activity activity, Bundle bundle);

        void onDestroyed(Activity activity);

        void onPaused(Activity activity);

        void onResumed(Activity activity);

        void onSaveInstanceState(Activity activity, Bundle bundle);

        void onStarted(Activity activity);

        void onStopped(Activity activity);
    }

    private ActivityTracker(Context context) {
        a(context);
    }

    public static synchronized ActivityTracker getInstance(Context context) {
        if (a == null) {
            a = new ActivityTracker(context);
        }
        return a;
    }

    private void a(Context context) {
        try {
            ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: cn.fly.tools.utils.ActivityTracker.1
                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityCreated(Activity activity, Bundle bundle) {
                    ActivityTracker.this.a(activity, bundle);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity) {
                    ActivityTracker.this.a(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityResumed(Activity activity) {
                    ActivityTracker.this.b(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityPaused(Activity activity) {
                    ActivityTracker.this.c(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStopped(Activity activity) {
                    ActivityTracker.this.d(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityDestroyed(Activity activity) {
                    ActivityTracker.this.e(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                    ActivityTracker.this.b(activity, bundle);
                }
            });
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
    }

    public void addTracker(Tracker tracker) {
        synchronized (this.b) {
            this.b.add(tracker);
        }
    }

    public void removeTracker(Tracker tracker) {
        synchronized (this.b) {
            this.b.remove(tracker);
        }
    }

    private void a(EachTracker eachTracker) {
        Tracker[] trackerArr;
        try {
            synchronized (this.b) {
                trackerArr = (Tracker[]) this.b.toArray(new Tracker[this.b.size()]);
            }
            for (Tracker tracker : trackerArr) {
                if (tracker != null) {
                    eachTracker.each(tracker);
                }
            }
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Activity activity, final Bundle bundle) {
        a(new EachTracker() { // from class: cn.fly.tools.utils.ActivityTracker.2
            @Override // cn.fly.tools.utils.ActivityTracker.EachTracker
            public void each(Tracker tracker) {
                tracker.onCreated(activity, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Activity activity) {
        a(new EachTracker() { // from class: cn.fly.tools.utils.ActivityTracker.3
            @Override // cn.fly.tools.utils.ActivityTracker.EachTracker
            public void each(Tracker tracker) {
                tracker.onStarted(activity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final Activity activity) {
        a(new EachTracker() { // from class: cn.fly.tools.utils.ActivityTracker.4
            @Override // cn.fly.tools.utils.ActivityTracker.EachTracker
            public void each(Tracker tracker) {
                tracker.onResumed(activity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(final Activity activity) {
        a(new EachTracker() { // from class: cn.fly.tools.utils.ActivityTracker.5
            @Override // cn.fly.tools.utils.ActivityTracker.EachTracker
            public void each(Tracker tracker) {
                tracker.onPaused(activity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(final Activity activity) {
        a(new EachTracker() { // from class: cn.fly.tools.utils.ActivityTracker.6
            @Override // cn.fly.tools.utils.ActivityTracker.EachTracker
            public void each(Tracker tracker) {
                tracker.onStopped(activity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(final Activity activity) {
        a(new EachTracker() { // from class: cn.fly.tools.utils.ActivityTracker.7
            @Override // cn.fly.tools.utils.ActivityTracker.EachTracker
            public void each(Tracker tracker) {
                tracker.onDestroyed(activity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final Activity activity, final Bundle bundle) {
        a(new EachTracker() { // from class: cn.fly.tools.utils.ActivityTracker.8
            @Override // cn.fly.tools.utils.ActivityTracker.EachTracker
            public void each(Tracker tracker) {
                tracker.onSaveInstanceState(activity, bundle);
            }
        });
    }
}