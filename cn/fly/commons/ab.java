package cn.fly.commons;

import android.text.TextUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class ab {
    public static final ThreadPoolExecutor a = new ThreadPoolExecutor(2, Math.max(2, 5), 60, TimeUnit.SECONDS, new SynchronousQueue(), new b(0), new a());
    public static final ThreadPoolExecutor b = new ThreadPoolExecutor(1, 1, 120, TimeUnit.SECONDS, new LinkedBlockingQueue(), new b(1));
    public static final ExecutorService c = Executors.newCachedThreadPool(new b(2));
    public static final ExecutorService d = Executors.newCachedThreadPool(new b(3));
    public static final ExecutorService e = Executors.newCachedThreadPool(new b(4));

    private static class b implements ThreadFactory {
        private static final AtomicInteger a = new AtomicInteger(1);
        private final ThreadGroup b;
        private final AtomicInteger c = new AtomicInteger(1);
        private final String d;

        b(int i) {
            SecurityManager securityManager = System.getSecurityManager();
            this.b = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            if (TextUtils.isEmpty("M-")) {
                this.d = w.b("005i?cjcj_fQgj") + a.getAndIncrement() + w.b("008*gjOhg4ci>ec[cbgj");
            } else {
                this.d = "M-PL-" + i + "-" + a.getAndIncrement() + "-";
            }
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.b, runnable, this.d + this.c.getAndIncrement(), 0L);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != 5) {
                thread.setPriority(5);
            }
            return thread;
        }
    }

    private static class a implements RejectedExecutionHandler {
        @Override // java.util.concurrent.RejectedExecutionHandler
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                cn.fly.commons.a.l.a().b(500L, runnable);
            } catch (Throwable th) {
            }
        }
    }
}