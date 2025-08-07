package cn.fly.commons;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyHandlerThread;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.ActivityTracker;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class m {
    private static m a;
    private volatile Handler c;
    private volatile long f;
    private final HashSet<l> b = new HashSet<>();
    private String d = null;
    private volatile long e = -1;

    public static synchronized m a() {
        if (a == null) {
            a = new m();
            if (a.c != null) {
                a.c.sendEmptyMessage(0);
            }
        }
        return a;
    }

    public void a(l lVar) {
        if (lVar == null) {
            return;
        }
        synchronized (this.b) {
            if (this.b.contains(lVar)) {
                return;
            }
            if (this.c != null) {
                Message message = new Message();
                message.what = 3;
                message.obj = lVar;
                this.c.sendMessage(message);
            }
        }
    }

    public boolean b() {
        return this.e == 0;
    }

    private m() {
        this.f = 0L;
        this.f = SystemClock.elapsedRealtime();
        this.c = FlyHandlerThread.newHandler(TextUtils.isEmpty("M-") ? null : "M-H-" + a("004Giehljmjh"), new Handler.Callback() { // from class: cn.fly.commons.m.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        m.this.e = SystemClock.elapsedRealtime();
                        m.this.a(false);
                        m.this.d();
                        return false;
                    case 1:
                        m.this.a(true);
                        return false;
                    case 2:
                        m.this.a(((Long) message.obj).longValue(), true);
                        return false;
                    case 3:
                        try {
                            l lVar = (l) message.obj;
                            if (lVar != null) {
                                m.this.b.add(lVar);
                                lVar.a(m.this.e > 0, true, 0L);
                            }
                        } catch (Throwable th) {
                            FlyLog.getInstance().d(th);
                        }
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        if (z) {
            a(true, false, 0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j, boolean z) {
        if (z) {
            a(false, false, j);
        }
    }

    private void a(boolean z, boolean z2, long j) {
        synchronized (this.b) {
            Iterator<l> it = this.b.iterator();
            while (it.hasNext()) {
                it.next().a(z, z2, j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        ActivityTracker.getInstance(FlySDK.getContext()).addTracker(new ActivityTracker.Tracker() { // from class: cn.fly.commons.FBManager$2
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
        });
    }

    public long c() {
        return this.f;
    }

    public static String a(String str) {
        return C0041r.a(str, 101);
    }
}