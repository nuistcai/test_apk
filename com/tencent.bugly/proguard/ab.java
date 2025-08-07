package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.LongCompanionObject;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class ab extends Thread {
    private boolean a = false;
    private List<aa> b = new ArrayList();
    private List<ac> c = new ArrayList();
    private ArrayList<aa> d = new ArrayList<>();

    public final void a() {
        a(new Handler(Looper.getMainLooper()), 5000L);
    }

    public final void b() {
        for (int i = 0; i < this.b.size(); i++) {
            try {
                if (this.b.get(i).d().equals(Looper.getMainLooper().getThread().getName())) {
                    x.c("remove handler::%s", this.b.get(i));
                    this.b.remove(i);
                }
            } catch (Exception e) {
                x.b(e);
                return;
            }
        }
    }

    private void a(Handler handler, long j) {
        if (handler == null) {
            x.e("addThread handler should not be null", new Object[0]);
            return;
        }
        String name = handler.getLooper().getThread().getName();
        for (int i = 0; i < this.b.size(); i++) {
            try {
                if (this.b.get(i).d().equals(handler.getLooper().getThread().getName())) {
                    x.e("addThread fail ,this thread has been added in monitor queue", new Object[0]);
                    return;
                }
            } catch (Exception e) {
                x.b(e);
            }
        }
        this.b.add(new aa(handler, name, 5000L));
    }

    public final boolean c() {
        this.a = true;
        if (!isAlive()) {
            return false;
        }
        try {
            interrupt();
        } catch (Exception e) {
            x.b(e);
        }
        return true;
    }

    public final boolean d() {
        if (isAlive()) {
            return false;
        }
        try {
            start();
            return true;
        } catch (Exception e) {
            x.b(e);
            return false;
        }
    }

    private int e() {
        int iMax = 0;
        for (int i = 0; i < this.b.size(); i++) {
            try {
                iMax = Math.max(iMax, this.b.get(i).c());
            } catch (Exception e) {
                x.b(e);
            }
        }
        return iMax;
    }

    public final void a(ac acVar) {
        if (this.c.contains(acVar)) {
            x.c("addThreadMonitorListeners fail ,this threadMonitorListener has been added in monitor queue", new Object[0]);
        } else {
            this.c.add(acVar);
        }
    }

    public final void b(ac acVar) {
        this.c.remove(acVar);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        while (!this.a) {
            for (int i = 0; i < this.b.size(); i++) {
                try {
                    this.b.get(i).a();
                } catch (Exception e) {
                    x.b(e);
                } catch (OutOfMemoryError e2) {
                    x.b(e2);
                }
            }
            long jUptimeMillis = SystemClock.uptimeMillis();
            for (long jUptimeMillis2 = 2000; jUptimeMillis2 > 0 && !isInterrupted(); jUptimeMillis2 = 2000 - (SystemClock.uptimeMillis() - jUptimeMillis)) {
                sleep(jUptimeMillis2);
            }
            int iE = e();
            if (iE != 0 && iE != 1) {
                this.d.clear();
                for (int i2 = 0; i2 < this.b.size(); i2++) {
                    aa aaVar = this.b.get(i2);
                    if (aaVar.b()) {
                        this.d.add(aaVar);
                        aaVar.a(LongCompanionObject.MAX_VALUE);
                    }
                }
                boolean z = false;
                for (int i3 = 0; i3 < this.d.size(); i3++) {
                    aa aaVar2 = this.d.get(i3);
                    for (int i4 = 0; i4 < this.c.size(); i4++) {
                        if (this.c.get(i4).a(aaVar2)) {
                            z = true;
                        }
                    }
                    if (!z) {
                        aaVar2.f();
                        x.c("although thread is blocked ,may not be anr error,so restore handler check wait time and restart check main thread", new Object[0]);
                    }
                }
            }
        }
    }
}