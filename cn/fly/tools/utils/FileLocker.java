package cn.fly.tools.utils;

import android.os.SystemClock;
import cn.fly.commons.C0041r;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

/* loaded from: classes.dex */
public class FileLocker implements PublicMemberKeeper {
    private FileOutputStream a;
    private FileLock b;
    private FileChannel c;

    public synchronized void setLockFile(String str) {
        try {
            this.a = new FileOutputStream(str);
            this.c = this.a.getChannel();
        } catch (Throwable th) {
            C0041r.a(this.c, this.a);
        }
    }

    public synchronized boolean lock(boolean z) {
        return lock(z, z ? 1000L : 500L, 16L);
    }

    public synchronized boolean lock(boolean z, long j, long j2) {
        boolean zA;
        if (this.a == null) {
            return false;
        }
        try {
            return a(z);
        } catch (Throwable th) {
            if (j > 0 && ((th instanceof OverlappingFileLockException) || (th instanceof IOException))) {
                long jElapsedRealtime = SystemClock.elapsedRealtime() + j;
                while (true) {
                    if (j <= 0) {
                        zA = false;
                        break;
                    }
                    try {
                        Thread.sleep(j2);
                    } catch (Throwable th2) {
                    }
                    try {
                        j = jElapsedRealtime - SystemClock.elapsedRealtime();
                        zA = a(z);
                        break;
                    } catch (Throwable th3) {
                        if (!(th3 instanceof OverlappingFileLockException) && !(th3 instanceof IOException)) {
                            FlyLog.getInstance().w(th);
                            j = -1;
                        } else if (j <= 0) {
                            FlyLog.getInstance().w("OverlappingFileLockException or IOExcept timeout");
                        }
                    }
                }
                if (j > 0) {
                    return zA;
                }
            } else {
                FlyLog.getInstance().w(th);
            }
            if (this.b != null) {
                try {
                    this.b.release();
                } catch (Throwable th4) {
                }
                this.b = null;
            }
            C0041r.a(this.c, this.a);
            return false;
        }
    }

    private boolean a(boolean z) throws Throwable {
        if (z) {
            this.b = this.c.lock();
        } else {
            this.b = this.c.tryLock();
        }
        return this.b != null;
    }

    public synchronized void lock(Runnable runnable, boolean z) {
        if (lock(z) && runnable != null) {
            runnable.run();
        }
    }

    public synchronized void unlock() {
        if (this.b == null) {
            return;
        }
        try {
            this.b.release();
        } catch (Throwable th) {
        }
        this.b = null;
    }

    public synchronized void release() {
        if (this.a == null) {
            return;
        }
        unlock();
        C0041r.a(this.c, this.a);
        this.c = null;
        this.a = null;
    }
}