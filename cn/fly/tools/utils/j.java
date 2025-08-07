package cn.fly.tools.utils;

import android.text.TextUtils;
import cn.fly.tools.FlyLog;

/* loaded from: classes.dex */
public abstract class j extends Thread {
    protected abstract void a() throws Throwable;

    public j() {
    }

    public j(String str) {
        if (!TextUtils.isEmpty("M-")) {
            setName("M-" + str);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        try {
            a();
        } catch (Throwable th) {
            a(th);
        }
    }

    protected void a(Throwable th) {
        FlyLog.getInstance().d(th);
    }
}