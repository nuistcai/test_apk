package cn.com.chinatelecom.account.api.b;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes.dex */
public abstract class e implements Runnable {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private boolean isCompleted = false;
    private a timeOutTask;
    private long timeout;

    private static class a implements Runnable {
        private e a;

        public a(e eVar) {
            this.a = eVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.a != null) {
                this.a.timeout();
            }
        }
    }

    public e() {
    }

    public e(long j) {
        this.timeout = j;
    }

    private void checkTimeOut() {
        this.timeOutTask = new a(this);
        handler.postDelayed(this.timeOutTask, this.timeout);
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void removeTimeoutTask() {
        try {
            if (this.timeOutTask != null) {
                handler.removeCallbacks(this.timeOutTask);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.timeout > 0) {
            checkTimeOut();
        }
        runTask();
    }

    public abstract void runTask();

    public void setCompleted(boolean z) {
        this.isCompleted = z;
    }

    public void timeout() {
    }
}