package cn.fly.tools.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.IntCompanionObject;

/* loaded from: classes.dex */
public class ExecutorDispatcher implements PublicMemberKeeper, IExecutor {
    private static volatile ExecutorDispatcher instance;
    private final IExecutor defaultExecutor = new DefaultExecutorImpl();

    public static IExecutor getInstance() {
        if (instance == null) {
            synchronized (ExecutorDispatcher.class) {
                if (instance == null) {
                    instance = new ExecutorDispatcher();
                }
            }
        }
        return instance;
    }

    private ExecutorDispatcher() {
    }

    @Override // cn.fly.tools.utils.IExecutor
    public <T extends SafeRunnable> void executeImmediately(T t) {
        if (t == null) {
            return;
        }
        try {
            this.defaultExecutor.executeImmediately(t);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    @Override // cn.fly.tools.utils.IExecutor
    public <T extends SafeRunnable> void executeSerial(T t) {
        if (t == null) {
            return;
        }
        try {
            this.defaultExecutor.executeSerial(t);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    @Override // cn.fly.tools.utils.IExecutor
    public <T extends SafeRunnable> void executeDuctile(T t) {
        if (t == null) {
            return;
        }
        try {
            this.defaultExecutor.executeDuctile(t);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    @Override // cn.fly.tools.utils.IExecutor
    public <T extends SafeRunnable> void executeDelayed(T t, long j) {
        if (t == null) {
            return;
        }
        try {
            if (j <= 0) {
                this.defaultExecutor.executeDuctile(t);
            } else {
                this.defaultExecutor.executeDelayed(t, j);
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    public static abstract class SafeRunnable implements Runnable {
        public abstract void safeRun();

        public void beforeRun() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                String strName = name();
                if (!TextUtils.isEmpty(strName)) {
                    Thread.currentThread().setName(strName);
                }
                beforeRun();
                safeRun();
                afterRun();
            } catch (Throwable th) {
                try {
                    handleException(th);
                } catch (Throwable th2) {
                }
            }
        }

        public void afterRun() {
        }

        public void handleException(Throwable th) {
        }

        public String name() {
            return "";
        }
    }

    private static final class DefaultExecutorImpl implements IExecutor {
        private static final int N_THREADS = 2;
        private final ExecutorService ductileService;
        private final ExecutorService immediatelyService;
        private final Handler innerHandler;
        private final ExecutorService serialService;

        private DefaultExecutorImpl() {
            this.innerHandler = new Handler(Looper.getMainLooper());
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, IntCompanionObject.MAX_VALUE, 10L, TimeUnit.SECONDS, new SynchronousQueue());
            threadPoolExecutor.allowCoreThreadTimeOut(true);
            this.immediatelyService = threadPoolExecutor;
            this.ductileService = new ThreadPoolExecutor(2, 2, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue());
            this.serialService = Executors.newSingleThreadExecutor();
        }

        @Override // cn.fly.tools.utils.IExecutor
        public <T extends SafeRunnable> void executeImmediately(T t) {
            try {
                this.immediatelyService.execute(t);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }

        @Override // cn.fly.tools.utils.IExecutor
        public <T extends SafeRunnable> void executeSerial(T t) {
            try {
                this.serialService.execute(t);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }

        @Override // cn.fly.tools.utils.IExecutor
        public <T extends SafeRunnable> void executeDuctile(T t) {
            try {
                this.ductileService.execute(t);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }

        @Override // cn.fly.tools.utils.IExecutor
        public <T extends SafeRunnable> void executeDelayed(final T t, long j) {
            if (t == null) {
                return;
            }
            try {
                this.innerHandler.postDelayed(new SafeRunnable() { // from class: cn.fly.tools.utils.ExecutorDispatcher.DefaultExecutorImpl.1
                    @Override // cn.fly.tools.utils.ExecutorDispatcher.SafeRunnable
                    public void safeRun() {
                        DefaultExecutorImpl.this.executeImmediately(t);
                    }
                }, j);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }
}