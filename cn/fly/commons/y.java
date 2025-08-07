package cn.fly.commons;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.FileLocker;
import cn.fly.tools.utils.ResHelper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public class y {
    private static volatile y a = null;
    private a b = new a();

    private y() {
    }

    public static y a() {
        if (a == null) {
            synchronized (y.class) {
                if (a == null) {
                    a = new y();
                }
            }
        }
        return a;
    }

    public int b() {
        int iIntValue = ((Integer) this.b.b(ad.e, -1)).intValue();
        if (iIntValue == -1 && (iIntValue = ad.b().b(ad.e, -1)) != -1) {
            a(iIntValue);
        }
        return iIntValue;
    }

    public void a(int i) {
        this.b.a(ad.e, Integer.valueOf(i)).a();
    }

    public int b(int i) {
        int iIntValue = ((Integer) this.b.b("key_wt_dys", Integer.valueOf(i))).intValue();
        if (iIntValue != i) {
            return iIntValue;
        }
        int iB = ad.b().b(i);
        if (iB != i) {
            this.b.a("key_wt_dys", Integer.valueOf(iB)).a();
        }
        return iB;
    }

    public int c(int i) {
        int iIntValue = ((Integer) this.b.b("key_wt_tms", Integer.valueOf(i))).intValue();
        if (iIntValue != i) {
            return iIntValue;
        }
        int iC = ad.b().c(i);
        if (iC != i) {
            this.b.a("key_wt_tms", Integer.valueOf(iC)).a();
        }
        return iC;
    }

    public void c() {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (!TextUtils.equals((String) this.b.b("key_acv", FlySDK.SDK_VERSION_NAME), DH.SyncMtd.getAppVersionName())) {
                a("key_acv", DH.SyncMtd.getAppVersionName()).a("key_cvi", Long.valueOf(System.currentTimeMillis())).h();
            } else if (jCurrentTimeMillis == ((Long) this.b.b("key_cvi", Long.valueOf(jCurrentTimeMillis))).longValue()) {
                a("key_cvi", Long.valueOf(System.currentTimeMillis())).h();
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    public y d(int i) {
        this.b.a("key_wt_dys", Integer.valueOf(Math.min(10000, i)));
        return this;
    }

    public long d() {
        if (DH.SyncMtd.isAut()) {
            long jLongValue = ((Long) this.b.b("key_fst_lnch_tm", 0L)).longValue();
            if (jLongValue > 0) {
                return jLongValue;
            }
            long jU = ad.b().u();
            if (jU > 0) {
                a(jU).h();
            } else {
                jU = ad.b().l();
                if (jU > 0) {
                    a(jU).h();
                }
            }
            if (jU == 0) {
                a(System.currentTimeMillis()).h();
            }
            return jU;
        }
        return System.currentTimeMillis();
    }

    public y a(long j) {
        if (DH.SyncMtd.isAut()) {
            this.b.a("key_fst_lnch_tm", Long.valueOf(j));
        }
        return this;
    }

    public y e(int i) {
        this.b.a("key_lch_tms", Integer.valueOf(Math.min(10000, i)));
        return this;
    }

    public int e() {
        int iIntValue = ((Integer) this.b.b("key_lch_tms", Integer.MIN_VALUE)).intValue();
        if (iIntValue != Integer.MIN_VALUE) {
            return iIntValue;
        }
        int iM = ad.b().m();
        e(iM);
        return iM;
    }

    public long f() {
        long jCurrentTimeMillis = System.currentTimeMillis() - ((Long) b("key_cvi", Long.valueOf(System.currentTimeMillis()))).longValue();
        if (jCurrentTimeMillis > 1000) {
            return jCurrentTimeMillis / 1000;
        }
        return 0L;
    }

    public boolean g() {
        if (((Boolean) this.b.b("keyR_drt_lch", false)).booleanValue()) {
            return true;
        }
        boolean zN = ad.b().n();
        if (zN) {
            this.b.a("keyR_drt_lch", (Object) true).a();
        }
        return zN;
    }

    public y a(boolean z) {
        this.b.a("keyR_drt_lch", Boolean.valueOf(z));
        return this;
    }

    public y a(String str, Object obj) {
        this.b.a(str, obj);
        return this;
    }

    public <T> T b(String str, T t) {
        return (T) this.b.b(str, t);
    }

    public boolean h() {
        return this.b.a();
    }

    static class a {
        private final File a;
        private volatile byte[] b;
        private final ReentrantReadWriteLock c;
        private Map<String, Object> d;
        private volatile boolean e;

        private a() {
            Map<String, Object> mapC;
            this.c = new ReentrantReadWriteLock();
            this.d = new HashMap();
            this.e = false;
            this.a = ResHelper.getDataCacheFile(FlySDK.getContext(), w.b("005Jck.iiKcbge"));
            if (this.a.exists() && this.a.length() > 0 && (mapC = c()) != null && !mapC.isEmpty()) {
                this.d.putAll(mapC);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte[] b() {
            if (this.b == null) {
                try {
                    this.b = w.b("016,ciPcdUcbcj3cScbee8cXcbdigegdiegkgh").getBytes("UTF-8");
                } catch (Throwable th) {
                }
            }
            return this.b;
        }

        public a a(String str, Object obj) {
            this.c.writeLock().lock();
            try {
                this.d.put(str, obj);
                this.e = true;
                return this;
            } finally {
                this.c.writeLock().unlock();
            }
        }

        public <T> T b(String str, T t) {
            this.c.readLock().lock();
            try {
                return (T) ResHelper.forceCast(this.d.get(str), t);
            } finally {
                this.c.readLock().unlock();
            }
        }

        public boolean a() {
            this.c.writeLock().lock();
            try {
                if (this.e) {
                    a(this.d);
                }
                this.c.writeLock().unlock();
                return true;
            } catch (Throwable th) {
                this.c.writeLock().unlock();
                return false;
            }
        }

        private void a(final Map<String, Object> map) throws IOException {
            u.a(u.a(u.k), new t() { // from class: cn.fly.commons.y.a.1
                @Override // cn.fly.commons.t
                public boolean a(FileLocker fileLocker) {
                    C0041r.a(a.this.a, a.this.b(), map);
                    a.this.e = false;
                    return false;
                }
            });
        }

        private Map<String, Object> c() {
            final Map<String, Object>[] mapArr = {null};
            u.a(u.a(u.k), new t() { // from class: cn.fly.commons.y.a.2
                @Override // cn.fly.commons.t
                public boolean a(FileLocker fileLocker) {
                    try {
                        mapArr[0] = (Map) C0041r.a(a.this.a, a.this.b());
                    } catch (Throwable th) {
                        FlyLog.getInstance().d(th);
                    }
                    return false;
                }
            });
            return mapArr[0];
        }
    }
}