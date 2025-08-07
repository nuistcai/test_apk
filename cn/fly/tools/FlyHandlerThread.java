package cn.fly.tools;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import cn.fly.tools.proguard.EverythingKeeper;

/* loaded from: classes.dex */
public class FlyHandlerThread extends Thread implements EverythingKeeper {
    private Looper looper;
    private int priority;
    private int tid;

    public FlyHandlerThread() {
        this.tid = -1;
        this.priority = 0;
    }

    public FlyHandlerThread(int i) {
        this.tid = -1;
        this.priority = i;
    }

    protected void onLooperPrepared() {
    }

    protected void onLooperPrepared(Looper looper) {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            try {
                realRun();
                this.tid = Process.myTid();
                Looper.prepare();
                synchronized (this) {
                    this.looper = Looper.myLooper();
                    notifyAll();
                }
                Process.setThreadPriority(this.priority);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
            onLooperPrepared(this.looper);
            onLooperPrepared();
            Looper.loop();
            this.tid = -1;
        } catch (Throwable th2) {
            FlyLog.getInstance().d(th2);
        }
    }

    @Deprecated
    public void realRun() {
    }

    public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {
            while (isAlive() && this.looper == null) {
                try {
                    wait();
                } catch (Throwable th) {
                }
            }
        }
        return this.looper;
    }

    public boolean quit() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }

    public int getThreadId() {
        return this.tid;
    }

    public static Handler newHandler(Handler.Callback callback) {
        return newHandler(null, null, callback);
    }

    public static Handler newHandler(String str, Handler.Callback callback) {
        return newHandler(str, null, callback);
    }

    public static Handler newHandler(Runnable runnable, Handler.Callback callback) {
        return newHandler(null, runnable, callback);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0016 A[Catch: all -> 0x001b, LOOP:0: B:8:0x0012->B:10:0x0016, LOOP_END, TRY_LEAVE, TryCatch #1 {all -> 0x001b, blocks: (B:6:0x000c, B:7:0x000f, B:8:0x0012, B:10:0x0016), top: B:25:0x000c, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Handler newHandler(String str, final Runnable runnable, final Handler.Callback callback) {
        final Handler[] handlerArr = new Handler[1];
        FlyHandlerThread flyHandlerThread = new FlyHandlerThread() { // from class: cn.fly.tools.FlyHandlerThread.1
            @Override // cn.fly.tools.FlyHandlerThread, java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    if (runnable != null) {
                        runnable.run();
                    }
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
                super.run();
            }

            @Override // cn.fly.tools.FlyHandlerThread
            protected void onLooperPrepared(Looper looper) {
                synchronized (handlerArr) {
                    handlerArr[0] = new Handler(looper, callback);
                    handlerArr.notifyAll();
                }
            }
        };
        synchronized (handlerArr) {
            if (str != null) {
                try {
                    flyHandlerThread.setName(str);
                    flyHandlerThread.start();
                    while (handlerArr[0] == null) {
                        handlerArr.wait();
                    }
                } finally {
                    return handlerArr[0];
                }
            } else {
                flyHandlerThread.start();
                while (handlerArr[0] == null) {
                }
            }
        }
        return handlerArr[0];
    }
}