package cn.fly.verify;

import android.os.Process;

/* loaded from: classes.dex */
public abstract class z extends Thread {
    private boolean a;

    protected abstract void a();

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        try {
            if (this.a) {
                Process.setThreadPriority(-19);
            }
            a();
        } catch (Throwable th) {
            v.a(th, "unexpected error");
        }
    }
}