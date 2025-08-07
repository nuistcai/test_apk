package cn.fly.tools.b;

import android.content.Context;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.a.l;
import cn.fly.commons.ab;
import cn.fly.commons.ad;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ResHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class d {
    private static d a;
    private static volatile boolean d = false;
    private Context b;
    private HashMap<String, Object> c;
    private volatile File g;
    private long k;
    private long l;
    private long m;
    private final byte[] e = new byte[0];
    private AtomicBoolean f = new AtomicBoolean(false);
    private ConcurrentLinkedQueue<CountDownLatch> h = new ConcurrentLinkedQueue<>();
    private volatile String i = null;
    private volatile int j = -1;

    public static d a(Context context) {
        if (a == null) {
            synchronized (d.class) {
                if (a == null) {
                    a = new d(context);
                }
            }
        }
        return a;
    }

    private d(Context context) {
        this.b = context;
    }

    public final CountDownLatch a() {
        return a(e());
    }

    public void a(int i) {
        this.j = i;
    }

    public int b() {
        return this.j;
    }

    public final CountDownLatch a(final String str) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        FlyLog.getInstance().d("dhs ofr: " + countDownLatch, new Object[0]);
        this.h.offer(countDownLatch);
        ab.d.execute(new Runnable() { // from class: cn.fly.tools.b.d.1
            @Override // java.lang.Runnable
            public void run() {
                cn.fly.commons.h hVarA;
                Throwable th;
                String str2;
                File file;
                String str3;
                String strA;
                synchronized (d.this.e) {
                    cn.fly.tools.c.a.c.set(true);
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    try {
                        FlyLog.getInstance().d("dhs stch: " + d.this.e(str), new Object[0]);
                        file = new File(FlySDK.getContext().getFilesDir(), l.a("003FededAi"));
                    } catch (Throwable th2) {
                        try {
                            FlyLog.getInstance().d("dhs oops: " + th2.getMessage(), new Object[0]);
                            FlyLog.getInstance().d(th2);
                            d.this.m = System.currentTimeMillis() - jCurrentTimeMillis;
                            FlyLog.getInstance().d("dhs ctd: " + countDownLatch, new Object[0]);
                            countDownLatch.countDown();
                            d.this.h.remove(countDownLatch);
                            FlyLog.getInstance().d("dhs tt " + d.this.m, new Object[0]);
                            if (d.this.m > 3500 && d.this.b() == 16) {
                                StringBuilder sbAppend = new StringBuilder("-t-" + d.this.m).append("-d-").append(d.this.l).append("-l-").append(d.this.k).append(" ");
                                hVarA = cn.fly.commons.h.a();
                                th = new Throwable(sbAppend.toString());
                                str2 = "" + d.this.i;
                            }
                        } finally {
                        }
                    }
                    if (!d.this.e(str)) {
                        boolean unused = d.d = false;
                        ResHelper.deleteFileAndFolder(file);
                        d.this.m = System.currentTimeMillis() - jCurrentTimeMillis;
                        FlyLog.getInstance().d("dhs ctd: " + countDownLatch, new Object[0]);
                        countDownLatch.countDown();
                        d.this.h.remove(countDownLatch);
                        FlyLog.getInstance().d("dhs tt " + d.this.m, new Object[0]);
                        if (d.this.m > 3500 && d.this.b() == 16) {
                            cn.fly.commons.h.a().a(3, 11, new Throwable(("-t-" + d.this.m) + "-d-" + d.this.l + "-l-" + d.this.k + " "), "" + d.this.i);
                        }
                        return;
                    }
                    d.this.a(0);
                    String strB = d.this.b(str);
                    if (TextUtils.isEmpty(strB)) {
                        boolean unused2 = d.d = false;
                        cn.fly.commons.h.a().a(-1, 4, "", "");
                        d.this.m = System.currentTimeMillis() - jCurrentTimeMillis;
                        FlyLog.getInstance().d("dhs ctd: " + countDownLatch, new Object[0]);
                        countDownLatch.countDown();
                        d.this.h.remove(countDownLatch);
                        FlyLog.getInstance().d("dhs tt " + d.this.m, new Object[0]);
                        if (d.this.m > 3500 && d.this.b() == 16) {
                            cn.fly.commons.h.a().a(3, 11, new Throwable(("-t-" + d.this.m) + "-d-" + d.this.l + "-l-" + d.this.k + " "), "" + d.this.i);
                        }
                        return;
                    }
                    try {
                        if (DH.SyncMtd.isInMainProcess()) {
                            str3 = strB;
                        } else {
                            String strReplace = DH.SyncMtd.getCurrentProcessName() + "";
                            String packageName = DH.SyncMtd.getPackageName();
                            if (strReplace.contains(packageName)) {
                                strReplace = strReplace.replace(packageName, "");
                            }
                            str3 = strB + "_" + strReplace.replace(":", "");
                            try {
                                FlyLog.getInstance().d("dhs cld nm " + str3, new Object[0]);
                            } catch (Throwable th3) {
                            }
                        }
                    } catch (Throwable th4) {
                        str3 = strB;
                    }
                    File fileB = d.this.b(file, str3);
                    boolean z = fileB != null && fileB.exists() && fileB.isFile();
                    FlyLog.getInstance().d("dhs cac: " + z, new Object[0]);
                    String strMD5 = Data.MD5(fileB);
                    if (z) {
                        d.this.a(5);
                        boolean zEquals = strB.equals(strMD5);
                        FlyLog.getInstance().d("dhs m5: " + zEquals, new Object[0]);
                        if (zEquals) {
                            FlyLog.getInstance().d("dhs tbm: " + d.this.f.get(), new Object[0]);
                            if (!d.this.f.compareAndSet(false, true)) {
                                strMD5 = "";
                            }
                            strA = strMD5;
                        } else {
                            d.this.a(6);
                            strA = d.this.a(d.this.c(str), fileB, strB);
                        }
                    } else {
                        d.this.a(8);
                        strA = d.this.a(d.this.c(str), fileB, strB);
                    }
                    FlyLog.getInstance().d("dhs cl:  tm5: " + strA + ", cm5: " + d.this.i, new Object[0]);
                    if (!TextUtils.isEmpty(strA) && !strA.equals(d.this.i)) {
                        d.this.a(fileB);
                        HashMap mapA = d.this.a(fileB, strA);
                        if (mapA == null || mapA.isEmpty()) {
                            try {
                                if (fileB.exists()) {
                                    fileB.delete();
                                }
                            } catch (Throwable th5) {
                            }
                            FlyLog.getInstance().d("dhs l fail", new Object[0]);
                        } else {
                            FlyLog.getInstance().d("dhs l succ", new Object[0]);
                            f fVar = new f(mapA);
                            d.this.i = Data.MD5(fileB);
                            boolean unused3 = d.d = c.a(d.this.b).a(fVar);
                            d.this.a(16);
                            FlyLog.getInstance().d("dhs fin", new Object[0]);
                        }
                    }
                    d.this.m = System.currentTimeMillis() - jCurrentTimeMillis;
                    FlyLog.getInstance().d("dhs ctd: " + countDownLatch, new Object[0]);
                    countDownLatch.countDown();
                    d.this.h.remove(countDownLatch);
                    FlyLog.getInstance().d("dhs tt " + d.this.m, new Object[0]);
                    if (d.this.m > 3500 && d.this.b() == 16) {
                        StringBuilder sbAppend2 = new StringBuilder("-t-" + d.this.m).append("-d-").append(d.this.l).append("-l-").append(d.this.k).append(" ");
                        hVarA = cn.fly.commons.h.a();
                        th = new Throwable(sbAppend2.toString());
                        str2 = "" + d.this.i;
                        hVarA.a(3, 11, th, str2);
                    }
                    cn.fly.tools.c.a.c.set(false);
                }
            }
        });
        return countDownLatch;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(File file) {
        if (this.g != null && this.g.exists()) {
            if (this.g.delete()) {
                FlyLog.getInstance().d("dhs dof succ", new Object[0]);
            } else {
                FlyLog.getInstance().d("dhs dof fail", new Object[0]);
            }
        }
        this.g = file;
    }

    public static boolean c() {
        return d;
    }

    public CountDownLatch d() {
        if (this.h != null && !this.h.isEmpty()) {
            return this.h.peek();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HashMap<String, Object> a(File file, String str) {
        HashMap map = new HashMap();
        String strD = ad.b().d();
        if (TextUtils.isEmpty(strD)) {
            strD = HashonHelper.fromHashMap(map);
        }
        HashMap<String, Object> map2 = new HashMap<>();
        if (this.c == null) {
            this.c = new HashMap<>();
            this.c.put("cacheMap", new ConcurrentHashMap());
            this.c.put("invokeTimesMap", new ConcurrentHashMap());
            this.c.put("expireTimeMap", new ConcurrentHashMap());
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            cn.fly.commons.cc.a.a(FlySDK.getContext(), file.getAbsolutePath(), strD, map2, this.c);
            this.k = System.currentTimeMillis() - jCurrentTimeMillis;
            FlyLog.getInstance().d(TextUtils.isEmpty(null) ? String.format("dhs l %d", Long.valueOf(this.k)) : null, new Object[0]);
        } catch (Throwable th) {
            try {
                str = "dhs l e: " + th.getMessage();
                map2.clear();
                cn.fly.commons.h.a().a(5, b(), th, "" + str);
                FlyLog.getInstance().d(th);
            } catch (Throwable th2) {
            }
            this.k = System.currentTimeMillis() - jCurrentTimeMillis;
            if (TextUtils.isEmpty(str)) {
                str = String.format("dhs l %d", Long.valueOf(this.k));
            }
            FlyLog.getInstance().d(str, new Object[0]);
        }
        return map2;
    }

    private String e() {
        try {
            String str = (String) cn.fly.commons.c.b(l.a("002Pgjgj"), (Object) null);
            if (str == null) {
                return (String) cn.fly.commons.c.b(l.a("009>gjggekfmghej jdi"), (Object) null);
            }
            return str;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b(String str) {
        String[] strArrSplit;
        if (!TextUtils.isEmpty(str) && (strArrSplit = str.split("#")) != null && strArrSplit.length == 2) {
            return strArrSplit[0];
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String c(String str) {
        String[] strArrSplit;
        if (!TextUtils.isEmpty(str) && (strArrSplit = str.split("#")) != null && strArrSplit.length == 2) {
            return strArrSplit[1];
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File b(File file, String str) {
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!TextUtils.isEmpty(str)) {
            d(str);
            return new File(file, str);
        }
        return null;
    }

    private void d(String str) {
        File dataCacheFile = ResHelper.getDataCacheFile(this.b, str);
        if (dataCacheFile.exists() && dataCacheFile.length() > 0) {
            dataCacheFile.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e(String str) {
        return (TextUtils.isEmpty(b(str)) || TextUtils.isEmpty(c(str))) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str, File file, String str2) {
        FileOutputStream fileOutputStream;
        if (!TextUtils.isEmpty(str) && file != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            String str3 = null;
            try {
                if (file.exists()) {
                    file.delete();
                }
                fileOutputStream = new FileOutputStream(file);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = null;
            }
            try {
                FlyLog.getInstance().d("dhs d...", new Object[0]);
                new NetworkHelper().download(str, fileOutputStream, null);
                String strMD5 = Data.MD5(file);
                if (TextUtils.equals(str2, strMD5)) {
                    C0041r.a(fileOutputStream);
                    if (TextUtils.isEmpty(null)) {
                        this.l = System.currentTimeMillis() - jCurrentTimeMillis;
                        str3 = String.format("dhs d %d", Long.valueOf(this.l));
                    }
                    FlyLog.getInstance().d(str3, new Object[0]);
                    return strMD5;
                }
                cn.fly.commons.h.a().a(-1, 20, "", str2);
                if (file.exists()) {
                    file.delete();
                }
                C0041r.a(fileOutputStream);
                if (TextUtils.isEmpty(null)) {
                    this.l = System.currentTimeMillis() - jCurrentTimeMillis;
                    str3 = String.format("dhs d %d", Long.valueOf(this.l));
                }
                FlyLog.getInstance().d(str3, new Object[0]);
                return "";
            } catch (Throwable th2) {
                th = th2;
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    str3 = "dhs d e: " + th.getMessage();
                    FlyLog.getInstance().d(th);
                    cn.fly.commons.h.a().a(2, b(), th, "" + str2);
                    C0041r.a(fileOutputStream);
                    if (TextUtils.isEmpty(str3)) {
                        this.l = System.currentTimeMillis() - jCurrentTimeMillis;
                        str3 = String.format("dhs d %d", Long.valueOf(this.l));
                    }
                    FlyLog.getInstance().d(str3, new Object[0]);
                    return "";
                } catch (Throwable th3) {
                    C0041r.a(fileOutputStream);
                    if (TextUtils.isEmpty(str3)) {
                        this.l = System.currentTimeMillis() - jCurrentTimeMillis;
                        str3 = String.format("dhs d %d", Long.valueOf(this.l));
                    }
                    FlyLog.getInstance().d(str3, new Object[0]);
                    throw th3;
                }
            }
        }
        return "";
    }
}