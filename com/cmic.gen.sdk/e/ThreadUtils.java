package com.cmic.gen.sdk.e;

import android.content.Context;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.auth.AuthnHelperCore;
import java.lang.Thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;

/* compiled from: ThreadUtils.java */
/* renamed from: com.cmic.gen.sdk.e.o, reason: use source file name */
/* loaded from: classes.dex */
public class ThreadUtils {
    private static final ExecutorService a = new ThreadPoolExecutor(0, 30, 60, TimeUnit.SECONDS, new SynchronousQueue());

    public static void a(a aVar) {
        try {
            a.execute(aVar);
        } catch (Exception e) {
            aVar.a.uncaughtException(Thread.currentThread(), e);
        }
    }

    /* compiled from: ThreadUtils.java */
    /* renamed from: com.cmic.gen.sdk.e.o$a */
    public static abstract class a implements Runnable {
        private final Thread.UncaughtExceptionHandler a;

        protected abstract void a();

        protected a() {
            this.a = new Thread.UncaughtExceptionHandler() { // from class: com.cmic.gen.sdk.e.o.a.1
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread t, Throwable e) {
                    e.printStackTrace();
                }
            };
        }

        protected a(final Context context, final ConcurrentBundle concurrentBundle) {
            this.a = new Thread.UncaughtExceptionHandler() { // from class: com.cmic.gen.sdk.e.o.a.2
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread t, Throwable e) throws JSONException {
                    concurrentBundle.a().a.add(e);
                    AuthnHelperCore.getInstance(context).callBackResult("200025", "发生未知错误", concurrentBundle, null);
                }
            };
        }

        @Override // java.lang.Runnable
        public void run() {
            Thread.currentThread().setUncaughtExceptionHandler(this.a);
            a();
            Thread.currentThread().setUncaughtExceptionHandler(null);
        }
    }
}