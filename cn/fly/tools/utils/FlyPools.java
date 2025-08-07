package cn.fly.tools.utils;

import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;

/* loaded from: classes.dex */
public class FlyPools implements PublicMemberKeeper {

    public interface Pool<T> extends PublicMemberKeeper {
        T acquire();

        boolean release(T t);
    }

    protected FlyPools() {
    }

    public static class SimplePool<T> implements Pool<T> {
        private final Object[] a;
        private int b;

        public SimplePool(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            this.a = new Object[i];
        }

        @Override // cn.fly.tools.utils.FlyPools.Pool
        public T acquire() {
            if (this.b > 0) {
                try {
                    int i = this.b - 1;
                    T t = (T) this.a[i];
                    this.a[i] = null;
                    this.b--;
                    return t;
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
            }
            return null;
        }

        @Override // cn.fly.tools.utils.FlyPools.Pool
        public boolean release(T t) {
            if (a(t)) {
                throw new IllegalStateException("Already in the pool!");
            }
            if (this.b < this.a.length) {
                this.a[this.b] = t;
                this.b++;
                return true;
            }
            return false;
        }

        private boolean a(T t) {
            for (int i = 0; i < this.b; i++) {
                if (this.a[i] == t) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class SynchronizedPool<T> extends SimplePool<T> {
        private final Object a;

        public SynchronizedPool(int i, Object obj) {
            super(i);
            this.a = obj;
        }

        public SynchronizedPool(int i) {
            this(i, new Object());
        }

        @Override // cn.fly.tools.utils.FlyPools.SimplePool, cn.fly.tools.utils.FlyPools.Pool
        public T acquire() {
            T t;
            synchronized (this.a) {
                t = (T) super.acquire();
            }
            return t;
        }

        @Override // cn.fly.tools.utils.FlyPools.SimplePool, cn.fly.tools.utils.FlyPools.Pool
        public boolean release(T t) {
            boolean zRelease;
            synchronized (this.a) {
                zRelease = super.release(t);
            }
            return zRelease;
        }
    }
}