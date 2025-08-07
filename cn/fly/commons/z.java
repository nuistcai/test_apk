package cn.fly.commons;

import android.os.Process;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.FileLocker;
import cn.fly.tools.utils.HashonHelper;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class z {
    private static volatile boolean a = true;
    private static AtomicInteger b = new AtomicInteger(-1);
    private static AtomicBoolean c = new AtomicBoolean(false);
    private static AtomicBoolean d = new AtomicBoolean(false);
    private static x e = new x();
    private static volatile String f;

    public static void a(final boolean z) {
        ab.a.execute(new cn.fly.tools.utils.i() { // from class: cn.fly.commons.z.1
            @Override // cn.fly.tools.utils.i
            protected void a() {
                int iB;
                cn.fly.tools.c.a.b.set(true);
                cn.fly.tools.b.a();
                if (!TextUtils.isEmpty("M-")) {
                    Thread.currentThread().setName("M-" + cn.fly.commons.a.l.a("004Ohmjmilig"));
                }
                if (!ad.c()) {
                    iB = -1;
                } else {
                    iB = y.a().b();
                }
                if (z.b.get() == -1) {
                    z.b.set(iB);
                }
                if (z.b.get() == 1) {
                    z.b(true, z);
                } else {
                    z.b(false, z);
                }
                FlyLog.getInstance().d((z ? cn.fly.commons.a.l.a("002Kek!g") : "") + "init cfg over. py " + z.b.get(), new Object[0]);
                cn.fly.tools.c.a.b.set(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(boolean z, boolean z2) {
        if (!z2) {
            e.a();
        }
        if (z) {
            if (TextUtils.isEmpty(w.a)) {
                String strO = ad.b().o();
                if (TextUtils.isEmpty(strO)) {
                    strO = i();
                }
                if (!TextUtils.isEmpty(strO)) {
                    w.c = strO;
                    ad.b().e(strO);
                }
            } else {
                w.c = w.a;
                ad.b().e(w.a);
            }
            if (TextUtils.isEmpty(w.b)) {
                String strP = ad.b().p();
                if (!TextUtils.isEmpty(strP)) {
                    w.d = strP;
                }
            } else {
                w.d = w.b;
                ad.b().f(w.b);
            }
            CountDownLatch countDownLatchG = g();
            FlyLog.getInstance().d(DH.SyncMtd.isInMainProcess() ? "main" : "sub", new Object[0]);
            if (!z2) {
                a(countDownLatchG);
                return;
            } else {
                ac.a();
                c.h();
                return;
            }
        }
        if (!z2) {
            e.b();
        }
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [cn.fly.commons.z$2] */
    public static void a(final CountDownLatch countDownLatch) {
        if (c.compareAndSet(false, true)) {
            if (y.a().d() == 0) {
                y.a().a(System.currentTimeMillis()).h();
            }
            y.a().c();
            w.a(FlySDK.getContext());
            k();
            l();
            ac.a();
            c.i();
            new cn.fly.tools.utils.j("PY-C") { // from class: cn.fly.commons.z.2
                @Override // cn.fly.tools.utils.j
                protected void a() {
                    cn.fly.tools.c.a.b.set(true);
                    FlyLog.getInstance().d("g lk st: " + Process.myPid(), new Object[0]);
                    FlyLog.getInstance().d("g lk res: " + u.a(u.a(u.g), new t() { // from class: cn.fly.commons.z.2.1
                        @Override // cn.fly.commons.t
                        public boolean a(FileLocker fileLocker) {
                            FlyLog.getInstance().d("g lk pd: " + Process.myPid() + ", proc st", new Object[0]);
                            long jCurrentTimeMillis = System.currentTimeMillis();
                            ad.w();
                            c.a(countDownLatch);
                            FlyLog.getInstance().d("g lk pd: " + Process.myPid() + ", proc ed, dur: " + (System.currentTimeMillis() - jCurrentTimeMillis) + ", release: y", new Object[0]);
                            return false;
                        }
                    }) + Process.myPid(), new Object[0]);
                    cn.fly.tools.c.a.b.set(false);
                }
            }.start();
        }
    }

    private static void k() {
        try {
            ServerSocketChannel serverSocketChannelOpen = ServerSocketChannel.open();
            serverSocketChannelOpen.configureBlocking(false);
            try {
                serverSocketChannelOpen.socket().bind(new InetSocketAddress(37926));
                v.a = false;
                serverSocketChannelOpen.close();
            } catch (Throwable th) {
                v.a = true;
            }
        } catch (Throwable th2) {
        }
    }

    private static void l() {
        m.a().a(new l() { // from class: cn.fly.commons.z.3
            @Override // cn.fly.commons.l
            public void a(boolean z, boolean z2, long j) {
                if (z) {
                    FlyLog.getInstance().d("fg.", new Object[0]);
                    boolean unused = z.a = true;
                } else {
                    FlyLog.getInstance().d("bg.", new Object[0]);
                    boolean unused2 = z.a = false;
                }
            }
        });
    }

    public static boolean a() {
        return a;
    }

    public static boolean b() {
        return b.get() == 1;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [cn.fly.commons.z$4] */
    public static void b(final boolean z) {
        b.set(z ? 1 : 0);
        FlyLog.getInstance().d("submit py: " + z, new Object[0]);
        new cn.fly.tools.utils.j(cn.fly.commons.a.l.a("004Zhmjmilif")) { // from class: cn.fly.commons.z.4
            @Override // cn.fly.tools.utils.j
            protected void a() {
                int iE = z.e();
                y.a().a(z ? 1 : 0);
                if (z && iE != 1) {
                    CountDownLatch countDownLatchG = z.g();
                    FlyLog.getInstance().d(DH.SyncMtd.isInMainProcess() ? "main" : "sub", new Object[0]);
                    z.a(countDownLatchG);
                    DH.requester(FlySDK.getContext()).getDetailNetworkTypeForStatic().request(new DH.DHResponder() { // from class: cn.fly.commons.z.4.1
                        @Override // cn.fly.tools.utils.DH.DHResponder
                        public void onResponse(DH.DHResponse dHResponse) {
                            try {
                                z.b(z, dHResponse.getDetailNetworkTypeForStatic());
                            } catch (Throwable th) {
                                FlyLog.getInstance().d(th);
                                try {
                                    z.b(z, dHResponse.getDetailNetworkTypeForStatic());
                                } catch (Throwable th2) {
                                    FlyLog.getInstance().d(th2);
                                }
                            }
                        }
                    });
                }
            }
        }.start();
    }

    public static int c() {
        FlyLog.getInstance().d("get py grtd status mem: " + b.get(), new Object[0]);
        return b.get();
    }

    public static int d() {
        int iC = c();
        if (iC != -1) {
            return iC;
        }
        return e();
    }

    public static int e() {
        int iB;
        if (!ad.c()) {
            iB = -1;
        } else {
            iB = y.a().b();
        }
        FlyLog.getInstance().d("get py grtd status cac: " + iB, new Object[0]);
        return iB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(boolean z, String str) throws Throwable {
        HashMap<String, Object> mapA = q.a(str);
        mapA.put(cn.fly.commons.a.l.a("009VejgjgefkekQgg2hm;k"), String.valueOf(z));
        String str2 = j.a().a("gclg") + cn.fly.commons.a.l.a("036mkHekejee]edBfd1mkSelPh6ej<d>fd$meDehZji@elekejhe2ejYejelVfmLgjDjejPehgj");
        HashMap<String, String> map = new HashMap<>();
        map.put(cn.fly.commons.a.l.a("003]fiYg-fd"), q.a());
        map.put(cn.fly.commons.a.l.a("013!flgjDg$ekilffed8gfj=ej4jFfd"), ac.h());
        String strHttpGet = new NetworkHelper().httpGet(str2, mapA, map);
        FlyLog.getInstance().d("RS sp: " + strHttpGet, new Object[0]);
        HashMap mapFromJson = HashonHelper.fromJson(strHttpGet);
        if (mapFromJson == null) {
            throw new Throwable("RS is illegal: " + strHttpGet);
        }
        if (!"200".equals(String.valueOf(mapFromJson.get(cn.fly.commons.a.l.a("004d)eled g"))))) {
            throw new Throwable("RS code is not 200: " + strHttpGet);
        }
    }

    public static String f() {
        return "ecpgnjvr<1fxsowaktq0{EKhPmziWUVCNdy2uDJFH|LYZQGTXRO:43l87;/6MI>\"@A?\\9[)_]5=.(S'~盺朼-";
    }

    public static CountDownLatch g() {
        if (!d.getAndSet(true)) {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).a();
        }
        return new CountDownLatch(0);
    }

    public static boolean h() {
        String strA = q.a();
        return (TextUtils.isEmpty(strA) || TextUtils.isEmpty(strA.trim()) || TextUtils.equals(strA, i())) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String i() {
        String strSubstring;
        if (f == null) {
            try {
                String absolutePath = FlySDK.getContext().getFilesDir().getAbsolutePath();
                if (!TextUtils.isEmpty(absolutePath)) {
                    String strSubstring2 = absolutePath.substring(0, absolutePath.lastIndexOf(cn.fly.commons.a.l.a("001m")));
                    if (!TextUtils.isEmpty(strSubstring2)) {
                        strSubstring = strSubstring2.substring(strSubstring2.lastIndexOf(cn.fly.commons.a.l.a("001m")) + 1);
                    } else {
                        strSubstring = null;
                    }
                    if (!TextUtils.isEmpty(strSubstring)) {
                        String strCRC32 = Data.CRC32(strSubstring.getBytes("utf-8"));
                        if (!TextUtils.isEmpty(strCRC32)) {
                            String strByteToHex = Data.byteToHex(strCRC32.getBytes());
                            if (!TextUtils.isEmpty(strByteToHex)) {
                                f = "s" + strByteToHex;
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return f;
    }
}