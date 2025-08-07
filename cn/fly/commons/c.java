package cn.fly.commons;

import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetCommunicator;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.FileLocker;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ResHelper;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class c {
    public static long c;
    private static AtomicBoolean e = new AtomicBoolean(false);
    private static AtomicBoolean f = new AtomicBoolean(false);
    private static AtomicBoolean g = new AtomicBoolean(false);
    private static volatile HashMap<String, Object> h = null;
    private static ConcurrentHashMap<String, Object> i = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Object> j = new ConcurrentHashMap<>();
    private static CountDownLatch k = new CountDownLatch(1);
    private static CountDownLatch l = new CountDownLatch(1);
    public static volatile boolean a = false;
    private static volatile boolean m = false;
    private static final AtomicBoolean n = new AtomicBoolean(false);
    private static volatile boolean o = false;
    private static volatile AtomicBoolean p = new AtomicBoolean(false);
    private static AtomicInteger q = new AtomicInteger(2);
    private static volatile int r = -1;
    private static final Object s = new Object();
    public static long b = 0;
    public static AtomicBoolean d = new AtomicBoolean(false);

    public static void a(CountDownLatch countDownLatch) {
        b(countDownLatch);
    }

    public static <T> T a(String str, T t) {
        if (!TextUtils.isEmpty(str) && h != null && CSCenter.getInstance().isConfigEnable()) {
            if (b(h)) {
                h.clear();
                h = new HashMap<>();
                c(2);
            }
            return (T) ResHelper.forceCast(h.get(str), t);
        }
        return t;
    }

    public static <T> T b(String str, T t) {
        if (TextUtils.isEmpty(str)) {
            return t;
        }
        if (h != null) {
            return (T) a(h, str, t);
        }
        return (T) a((HashMap<String, Object>) HashonHelper.fromJson(ad.b().d()), str, t);
    }

    private static <T> T a(HashMap<String, Object> map, String str, T t) {
        if (!TextUtils.isEmpty(str) && !b(map) && a(map)) {
            return (T) ResHelper.forceCast(map.get(str), t);
        }
        return t;
    }

    public static <T> T a(String str, T t, long j2) {
        try {
            if ((h == null || h.isEmpty()) && k.getCount() > 0) {
                if (j2 > 0) {
                    k.await(j2, TimeUnit.MILLISECONDS);
                } else {
                    k.await();
                }
            }
            if (!d(str) && l.getCount() > 0) {
                if (j2 > 0) {
                    l.await(j2, TimeUnit.MILLISECONDS);
                } else {
                    l.await();
                }
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        return (T) a(str, t);
    }

    private static boolean a(HashMap<String, Object> map) {
        return map == null || ((Integer) ResHelper.forceCast(map.get(n.a("002g5bi")), 0)).intValue() == 0;
    }

    public static boolean a() {
        return ((Integer) a(n.a("002g=bi"), 0)).intValue() == 0;
    }

    public static boolean b() {
        return ((Integer) a(n.a("004aHbi[cc"), 0)).intValue() == 1;
    }

    public static boolean c() {
        return (((Integer) a(n.a("002cLbh"), 0)).intValue() == 1) || z.a();
    }

    public static boolean d() {
        return c();
    }

    public static boolean a(String str) {
        return !TextUtils.isEmpty(str) && a() && b() && ((Integer) a(str, 0)).intValue() != 0;
    }

    public static ConcurrentHashMap<String, Object> e() {
        return i;
    }

    public static ConcurrentHashMap<String, Object> f() {
        return j;
    }

    public static ArrayList<String> g() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(n.a("004Ccjcjccdj"));
        arrayList.add(n.a("005$dhcjcjccdj"));
        arrayList.add(n.a("005ed0bb1de"));
        arrayList.add(n.a("0097cdbhTdUbcbe?dcaGca"));
        arrayList.add(n.a("010Dbfbfbf4a;bebhcbbiWcc"));
        return (ArrayList) a(n.a("004Odebgdg8a"), arrayList);
    }

    public static void h() {
        if (a()) {
            c(3);
        }
    }

    static void i() {
        if (r == -1) {
            synchronized (s) {
                if (r == -1) {
                    HashMap mapFromJson = HashonHelper.fromJson(ad.b().d());
                    if (b((HashMap<String, Object>) mapFromJson)) {
                        ad.b().c((String) null);
                        mapFromJson = null;
                    }
                    if (mapFromJson == null || mapFromJson.isEmpty()) {
                        r = 1;
                    } else {
                        r = 0;
                        if (a()) {
                            a((HashMap<String, Object>) mapFromJson, false);
                        }
                    }
                }
            }
        }
    }

    private static void b(CountDownLatch countDownLatch) {
        i();
        if (a()) {
            if (r == 1) {
                FlyLog.getInstance().d("g ch: n", new Object[0]);
                c(1);
                return;
            }
            FlyLog.getInstance().d("g ch: y", new Object[0]);
            boolean z = System.currentTimeMillis() - ad.b().b(ad.m, 0L) < 2000;
            FlyLog.getInstance().d("g ch fre: " + z, new Object[0]);
            if (!z) {
                c(2);
            }
            if (countDownLatch != null) {
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    FlyLog.getInstance().d("g dhs_w cdl: " + countDownLatch, new Object[0]);
                    countDownLatch.await(3500L, TimeUnit.MILLISECONDS);
                    FlyLog.getInstance().d("g dhs_w end, dur: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
            }
            a(true, false, z, 2);
        }
    }

    private static void a(HashMap<String, Object> map, boolean z) {
        h = new HashMap<>();
        if (map != null) {
            h.putAll(map);
            if (z) {
                d.set(true);
                FlyLog.getInstance().d("olCfLd done", new Object[0]);
            }
        }
        try {
            if (z) {
                k.countDown();
                l.countDown();
            } else {
                k.countDown();
            }
        } catch (Throwable th) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(boolean z) {
        v.a().b();
        if (b()) {
            FlyLog.getInstance().d("b db st", new Object[0]);
            f.a((FlyProduct) null);
            if (z) {
                cn.fly.commons.a.d.a().b();
                new cn.fly.commons.b.a(FlySDK.getContext()).a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v1, types: [cn.fly.commons.c$2] */
    /* JADX WARN: Type inference failed for: r4v1, types: [cn.fly.commons.c$1] */
    public static void b(int i2) {
        FlyLog.getInstance().d("b ob st", new Object[0]);
        if (!a() || !b()) {
            if (i2 == 3 || z.b()) {
                r();
            }
            q();
            return;
        }
        final String str = (String) a(n.a("003YcdNca"), (Object) null);
        if (TextUtils.isEmpty(str)) {
            if (i2 == 3 || z.b()) {
                r();
            }
            q();
        } else if (i2 == 3 || e.compareAndSet(false, true)) {
            new cn.fly.tools.utils.j(n.a("003@djgjfi") + i2) { // from class: cn.fly.commons.c.1
                @Override // cn.fly.tools.utils.j
                protected void a() {
                    u.a(u.a(u.d), false, new t() { // from class: cn.fly.commons.c.1.1
                        @Override // cn.fly.commons.t
                        public boolean a(FileLocker fileLocker) {
                            synchronized (c.i) {
                                c.e(str);
                            }
                            return false;
                        }
                    });
                }
            }.start();
        }
        if (!a() || !b()) {
            s();
            p();
            return;
        }
        final String str2 = (String) a("sbr", (Object) null);
        if (TextUtils.isEmpty(str2)) {
            p();
            s();
        } else if (i2 == 3 || f.compareAndSet(false, true)) {
            new cn.fly.tools.utils.j("DS-" + i2) { // from class: cn.fly.commons.c.2
                @Override // cn.fly.tools.utils.j
                protected void a() {
                    u.a(u.a(u.e), false, new t() { // from class: cn.fly.commons.c.2.1
                        @Override // cn.fly.commons.t
                        public boolean a(FileLocker fileLocker) {
                            synchronized (c.j) {
                                c.f(str2);
                            }
                            return false;
                        }
                    });
                }
            }.start();
        }
    }

    private static void p() {
        a(n.a("003AdgddWe"), n.a("007@bjbhdgKe@ddbh$a"));
    }

    /* JADX WARN: Type inference failed for: r6v0, types: [cn.fly.commons.c$3] */
    private static void a(final boolean z, final boolean z2, final boolean z3, final int i2) {
        new cn.fly.tools.utils.j("PY-B" + i2) { // from class: cn.fly.commons.c.3
            @Override // cn.fly.tools.utils.j
            protected void a() {
                FlyLog.getInstance().d("b enter:" + Process.myPid() + ", lbms: " + c.m + ", fc" + z + ", ol: " + z2 + ", gf: " + z3 + ", in: " + i2, new Object[0]);
                if (!c.m) {
                    FlyLog.getInstance().d("b lk st: " + Process.myPid(), new Object[0]);
                    u.a(u.a(u.f), new t() { // from class: cn.fly.commons.c.3.1
                        @Override // cn.fly.commons.t
                        public boolean a(FileLocker fileLocker) {
                            boolean unused = c.m = true;
                            FlyLog.getInstance().d("b lk: " + Process.myPid() + ", proc st", new Object[0]);
                            long jCurrentTimeMillis = System.currentTimeMillis();
                            c.c(z2);
                            if (!z || z3) {
                                c.b(i2);
                            }
                            FlyLog.getInstance().d("b lk: " + Process.myPid() + ", proc ed, dur: " + (System.currentTimeMillis() - jCurrentTimeMillis) + ", release: n", new Object[0]);
                            Looper.prepare();
                            Looper.loop();
                            return true;
                        }
                    });
                    return;
                }
                FlyLog.getInstance().d("b lked already: " + Process.myPid(), new Object[0]);
                c.c(z2);
                if (!z || z3) {
                    c.b(i2);
                }
            }
        }.start();
    }

    private static boolean d(String str) {
        List list = (List) a(n.a("002ae"), (Object) null);
        return (list == null || list.size() == 0 || !list.contains(str)) ? false : true;
    }

    private static void c(int i2) {
        if (g.compareAndSet(false, true)) {
            String str = String.format(n.a("005Acbeafihidg"), Integer.valueOf(i2));
            if (i2 == 2) {
                ab.a.execute(b(str, i2));
            } else {
                b(str, i2).run();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static cn.fly.tools.utils.i b(final String str, final int i2) {
        return new cn.fly.tools.utils.i() { // from class: cn.fly.commons.c.4
            @Override // cn.fly.tools.utils.i
            protected void a() {
                cn.fly.tools.c.a.b.set(true);
                if (!TextUtils.isEmpty("M-")) {
                    Thread.currentThread().setName("M-" + str);
                }
                c.b(new cn.fly.tools.utils.d<HashMap<String, Object>>() { // from class: cn.fly.commons.c.4.1
                    @Override // cn.fly.tools.utils.d
                    public void a(HashMap<String, Object> map) {
                        try {
                            c.b(map, i2);
                            if (map == null) {
                                cn.fly.commons.a.l.a().c(300000L, c.b(str, i2));
                            }
                        } finally {
                            c.g.set(false);
                        }
                    }
                });
                cn.fly.tools.c.a.b.set(false);
            }
        };
    }

    private static void q() {
        a(n.a("0036dg@aa"), n.a("009Xfabidd-j%bjbdbd)aa"), n.a("0164fabidd!ja]bibdbdBjMbadddg<j[bdbaDa"), n.a("005'fabedjZeZbb"), n.a("0126bjcfPg3fdfffbgifcfggidffb"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(HashMap<String, Object> map, int i2) {
        CountDownLatch countDownLatchA;
        if (map == null) {
            HashMap<String, Object> mapFromJson = HashonHelper.fromJson(ad.b().e());
            if (!b(mapFromJson)) {
                map = mapFromJson;
            }
            ad.b().f();
        }
        if (map != null && !map.isEmpty()) {
            countDownLatchA = c(map);
            FlyLog.getInstance().d("sw fin: " + HashonHelper.fromHashMap(map), new Object[0]);
        } else {
            countDownLatchA = null;
        }
        a(map, true);
        cn.fly.tools.c.a.b.set(false);
        if (((Integer) a("dm", 1)).intValue() == 1) {
            if (p.compareAndSet(false, true)) {
                j.a().c();
            }
        } else {
            j.a().b();
        }
        if (!o) {
            u();
        }
        if (countDownLatchA == null) {
            countDownLatchA = cn.fly.tools.b.d.a(FlySDK.getContext()).a();
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            FlyLog.getInstance().d("ge dhs_w cdl: " + countDownLatchA, new Object[0]);
            countDownLatchA.await(3500L, TimeUnit.MILLISECONDS);
            FlyLog.getInstance().d("ge dhs_w end, dur: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        a(false, true, true, i2);
    }

    private static void r() {
        synchronized (u.i) {
            i.a().a(10);
            u.i.notifyAll();
        }
    }

    private static void s() {
        synchronized (u.j) {
            u.j.notifyAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final cn.fly.tools.utils.d<HashMap<String, Object>> dVar) {
        cn.fly.tools.c.a.b.set(true);
        DH.requester(FlySDK.getContext()).getDetailNetworkTypeForStatic().getODH().request(new DH.DHResponder() { // from class: cn.fly.commons.c.5
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                cn.fly.tools.c.a.b.set(true);
                try {
                    HashMap mapB = c.b(dHResponse);
                    while (c.q.get() > 0 && (mapB == null || mapB.isEmpty())) {
                        try {
                            Thread.sleep(30000L);
                        } catch (Throwable th) {
                            FlyLog.getInstance().d(th);
                        }
                        mapB = c.b(dHResponse);
                        if (mapB == null || mapB.isEmpty()) {
                            c.q.getAndDecrement();
                        }
                    }
                    dVar.a(mapB);
                } catch (Throwable th2) {
                    FlyLog.getInstance().d(th2);
                    dVar.a(null);
                }
            }
        });
    }

    private static boolean b(HashMap<String, Object> map) {
        if (map != null) {
            long jLongValue = ((Long) ResHelper.forceCast(map.get(n.a("010Yba'd7bbbg8ad<dabgbdKd")), 0L)).longValue();
            long jIntValue = ((Integer) ResHelper.forceCast(map.get(n.a("004'bhch a]cd")), 86400)).intValue() * 1000;
            if (jLongValue != 0) {
                if (jIntValue > 0) {
                    return System.currentTimeMillis() - jLongValue >= jIntValue;
                }
                if (jIntValue == 0) {
                    return System.currentTimeMillis() - jLongValue >= 86400000;
                }
                return true ^ C0041r.a(System.currentTimeMillis(), jLongValue);
            }
        }
        return false;
    }

    public static void j() {
        o = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HashMap<String, Object> b(DH.DHResponse dHResponse) {
        try {
            String packageName = DH.SyncMtd.getPackageName();
            String strA = q.a();
            HashMap<String, String> map = new HashMap<>();
            map.put(n.a("003*cf7d$ca"), strA);
            map.put(n.a("0130cidgGdVbhficcba7dcg,bgAgBca"), ac.h());
            map.put(n.a("004[bdbibgba"), dHResponse.getODH());
            HashMap<String, Object> mapA = q.a(dHResponse.getDetailNetworkTypeForStatic());
            mapA.put(n.a("002g'dg"), String.valueOf(System.currentTimeMillis()));
            int i2 = 1;
            mapA.put("nbs", 1);
            int privacyGrantedStatus = FlySDK.getPrivacyGrantedStatus();
            if (privacyGrantedStatus != -1) {
                mapA.put(n.a("0090bgdgdbchbhXdd(ej%h"), String.valueOf(privacyGrantedStatus == 1));
            }
            String strA2 = n.a("002Obbff");
            if (!FlySDK.checkV6()) {
                i2 = -1;
            }
            mapA.put(strA2, String.valueOf(i2));
            mapA.put("ait", Long.valueOf(y.a().d()));
            if (ac.d() == 2) {
                FlyLog.getInstance().d("g*f chk PU5H: Nw, psrd", new Object[0]);
                String strV = ad.b().v();
                if (!TextUtils.isEmpty(strV)) {
                    mapA.put("psid", strV);
                }
            } else {
                FlyLog.getInstance().d("g*f chk PU5H: No/Od, psid", new Object[0]);
                String strB = f.b();
                if (!TextUtils.isEmpty(strB)) {
                    mapA.put("psid", strB + packageName);
                }
            }
            String strHttpGet = new NetworkHelper().httpGet(j.a().a("gcfg") + "/v6/gcf", mapA, map);
            HashMap mapFromJson = HashonHelper.fromJson(strHttpGet);
            if (mapFromJson.isEmpty()) {
                return null;
            }
            if (!"200".equals(String.valueOf(mapFromJson.get(n.a("006Vdg<gbgZbedg"))))) {
                throw new Throwable("RS is illegal: " + strHttpGet);
            }
            b = ((Long) mapFromJson.get(n.a("009g1bgbd2dXdg[gbZbdEh"))).longValue();
            c = SystemClock.elapsedRealtime();
            byte[] bArrRawMD5 = Data.rawMD5((strA + ":" + packageName + ":" + b).getBytes("utf-8"));
            String str = (String) ResHelper.forceCast(mapFromJson.get(n.a("0026dg;a")));
            if (str == null) {
                throw new Throwable("RS is illegal: " + strHttpGet);
            }
            String str2 = new String(Data.AES128Decode(bArrRawMD5, Base64.decode(str, 2)), "utf-8");
            FlyLog.getInstance().d("sw: " + str2, new Object[0]);
            HashMap<String, Object> mapFromJson2 = HashonHelper.fromJson(str2);
            if (mapFromJson2.isEmpty()) {
                throw new Throwable("RS is illegal: " + strHttpGet);
            }
            mapFromJson2.put(n.a("010)ba0d;bbbgTadUdabgbdId"), Long.valueOf(System.currentTimeMillis()));
            ad.b().d(HashonHelper.fromHashMap(mapFromJson2));
            return mapFromJson2;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private static CountDownLatch c(HashMap<String, Object> map) {
        String str = (String) ResHelper.forceCast(map.get(n.a("002Gdgdg")), null);
        CountDownLatch countDownLatchA = cn.fly.tools.b.d.a(FlySDK.getContext()).a(str);
        try {
            HashMap<String, Object> map2 = (HashMap) map.get(n.a("002dHbd"));
            String str2 = (String) ResHelper.forceCast((String) map.get(n.a("002a^ba")), n.a("006?fcfcfdfdfdfd"));
            long jLongValue = ((Long) ResHelper.forceCast(map.get(n.a("004dadWcg")), 5L)).longValue();
            HashMap<String, Object> map3 = (HashMap) map.get(n.a("002 chTh"));
            HashMap map4 = (HashMap) map.get(n.a("004 ch2haBba"));
            Integer num = (Integer) map.get(n.a("0042ch.d]bi.g"));
            HashMap map5 = new HashMap();
            map5.put(n.a("002:biUf"), map.get(n.a("002:biUf")));
            map5.put(n.a("002Hdgdg"), str);
            map5.put(n.a("002dVbd"), map2);
            map5.put(n.a("002aVba"), str2);
            map5.put(n.a("004dad=cg"), Long.valueOf(jLongValue));
            map5.put(n.a("004<ch!d(bi0g"), num);
            map5.put(n.a("003+bhbgba"), ResHelper.forceCast((String) map.get(n.a("003+bhbgba")), null));
            map5.put(n.a("003?dgbi%a"), map.get(n.a("003?dgbi%a")));
            map5.put(n.a("003=dgbg4g"), map.get(n.a("003=dgbg4g")));
            map5.put("aps", map.get("aps"));
            map5.put(n.a("005$dg=e9bgEgKdg"), map.get(n.a("005$dg=e9bgEgKdg")));
            map5.put(n.a("003Gbh)hg"), map.get(n.a("003Gbh)hg")));
            map5.put("ndi", map.get("ndi"));
            map5.put("dm", map.get("dm"));
            map5.put("hs", map.get("hs"));
            map5.put("sti", map.get("sti"));
            map5.put(n.a("004=bhch5a,cd"), ResHelper.forceCast(map.get(n.a("004=bhch5a,cd")), 86400));
            map5.put("spcfg", map.get("spcfg"));
            if ((map2 != null && map2.size() > 0 && !TextUtils.isEmpty(str2)) || (map3 != null && map3.size() > 0 && map4 != null && map4.size() > 0)) {
                a(map5, map, map2, map3, map4, num, countDownLatchA);
                k.a().a(map, map2, map3);
            }
            map.put(n.a("010Nba,d bbbg(ad)dabgbdAd"), Long.valueOf(System.currentTimeMillis()));
            ad.b().c(HashonHelper.fromHashMap(map));
            t();
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        return countDownLatchA;
    }

    private static void t() {
        cn.fly.tools.utils.g.a().a(n.a("004'bi;b1bgba"), (Integer) 1);
        cn.fly.tools.utils.g.a().a(n.a("003bee"), (Integer) 1);
        cn.fly.tools.utils.g.a().a(n.a("003eLbiWa"), (Integer) 1);
        cn.fly.tools.utils.g.a().a(n.a("002,debg"), (Integer) 1);
        cn.fly.tools.utils.g.a().a(n.a("0022dddg"), (Integer) 1);
    }

    private static void a(HashMap<String, Object> map, HashMap<String, Object> map2, HashMap<String, Object> map3, HashMap<String, Object> map4, HashMap<String, Object> map5, Integer num, CountDownLatch countDownLatch) {
        if (num != null && num.intValue() == 2) {
            cn.fly.tools.c.a.b.set(false);
            try {
                countDownLatch.await(3500L, TimeUnit.MILLISECONDS);
                FlyLog.getInstance().d("dhs wt geot.2 ovr", new Object[0]);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        boolean zA = k.a().a(true);
        k.a().c().put(n.a("006d7bdbfbhUd9dg"), Boolean.valueOf(zA));
        if (map3 != null && map3.size() > 0 && !zA) {
            FlyLog.getInstance().d("dhs em dg", new Object[0]);
            map2.clear();
            map2.putAll(map);
            map2.putAll(map3);
            return;
        }
        if (map4 != null && map4.size() > 0 && !k.a().a(map5)) {
            FlyLog.getInstance().d("dhs gpe dg", new Object[0]);
            map2.clear();
            map2.putAll(map);
            map2.putAll(map4);
            return;
        }
        map2.remove(n.a("002Ach[h"));
        map2.remove(n.a("002d.bd"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v17, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r13v24, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r4v15, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r4v19, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r4v20, types: [cn.fly.tools.network.NetworkHelper] */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v7, types: [cn.fly.commons.c$6, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v7, types: [cn.fly.tools.network.NetworkHelper] */
    public static void e(String str) {
        String strB;
        File file;
        boolean zBooleanValue;
        ?? fileOutputStream;
        File file2 = null;
        try {
            i.a().a(0);
            strB = C0041r.b(str);
            file = new File(FlySDK.getContext().getFilesDir(), n.a("003Edg5aa"));
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (!k.a().b()) {
                i.a().a(18);
                q();
            } else if (TextUtils.isEmpty(strB)) {
                i.a().a(1);
            } else {
                try {
                    if (c()) {
                        i.a().a(2);
                        HashMap map = (HashMap) new NetCommunicator(1024, "9e87e8d4b8f52f2916d0fb4342aa6b54a81a05666d0bdb23cc5ebf3a07440bc3976adff1ce11c64ddcdbfc017920648217196d51e3165e780e58b5460c525ee9", "13bda4b87eb42ab9e64e6b4f3d17cf8005a4ae94af37bc9fd76ebd91a828f017c81bd63cbe2924e361e20003b9e5f47cdac1f5fba5fca05730a32c5c65869590287207e79a604a2aac429e55f0d35c211367bd226dd5e57df7810f036071854aa1061a0f34b418b9178895a531107c652a428cfa6ecfa65333580ae7e0edf0e1").requestSynchronized(q.e(), strB, false);
                        i.a().a(3);
                        String str2 = (String) map.get(n.a("002TcdIe"));
                        String str3 = (String) map.get("m");
                        Boolean bool = (Boolean) map.get(n.a("002b(dg"));
                        if (bool == null) {
                            zBooleanValue = false;
                        } else {
                            zBooleanValue = bool.booleanValue();
                        }
                        String str4 = (String) map.get(n.a("002bVcf"));
                        String str5 = (String) map.get("cn");
                        String str6 = (String) map.get("fn");
                        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str4)) {
                            i.a().a(4);
                            ResHelper.deleteFileAndFolder(file);
                        } else {
                            synchronized (u.i) {
                                i.clear();
                                i.put("h", str3);
                                i.put("k", str4);
                                i.put("cn", str5);
                                i.put("fn", str6);
                                String strCheckHttpRequestUrl = NetCommunicator.checkHttpRequestUrl(str2);
                                if (zBooleanValue) {
                                    i.a().a(5);
                                    File file3 = new File(file, n.a("008a'bi^c-cdbjdgUaa"));
                                    if (!file3.exists() || !str3.equals(Data.MD5(file3))) {
                                        i.a().a(6);
                                        ResHelper.deleteFileAndFolder(file);
                                        file.mkdirs();
                                        try {
                                            fileOutputStream = new FileOutputStream(file3);
                                        } catch (Throwable th2) {
                                            th = th2;
                                        }
                                        try {
                                            new NetworkHelper().download(strCheckHttpRequestUrl, fileOutputStream, null);
                                            i.a().a(7);
                                            C0041r.a((Closeable[]) new Closeable[]{fileOutputStream});
                                        } catch (Throwable th3) {
                                            th = th3;
                                            file2 = fileOutputStream;
                                            C0041r.a((Closeable[]) new Closeable[]{file2});
                                            throw th;
                                        }
                                    }
                                } else {
                                    i.a().a(8);
                                    ResHelper.deleteFileAndFolder(file);
                                    final byte[][] bArr = new byte[1][];
                                    final int[] iArr = new int[1];
                                    try {
                                        ?? r7 = new ByteArrayOutputStream() { // from class: cn.fly.commons.c.6
                                            @Override // java.io.ByteArrayOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                                            public void close() throws IOException {
                                                super.close();
                                                bArr[0] = this.buf;
                                                iArr[0] = this.count;
                                            }
                                        };
                                        try {
                                            new NetworkHelper().download(strCheckHttpRequestUrl, r7, null);
                                            i.a().a(9);
                                            C0041r.a((Closeable[]) new Closeable[]{r7});
                                            i.put("b", bArr[0]);
                                            i.put("s", Integer.valueOf(iArr[0]));
                                        } catch (Throwable th4) {
                                            th = th4;
                                            file2 = r7;
                                            C0041r.a((Closeable[]) new Closeable[]{file2});
                                            throw th;
                                        }
                                    } catch (Throwable th5) {
                                        th = th5;
                                    }
                                }
                            }
                        }
                    }
                } finally {
                    r();
                }
            }
        } catch (Throwable th6) {
            th = th6;
            file2 = file;
            ResHelper.deleteFileAndFolder(file2);
            i.a().a(2, th);
        }
    }

    private static void a(String... strArr) {
        File filesDir = FlySDK.getContext().getFilesDir();
        for (String str : strArr) {
            try {
                C0041r.a(new File(filesDir, str));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void f(String str) {
        Throwable th;
        File file;
        String strB;
        try {
            strB = C0041r.b(str);
            file = new File(FlySDK.getContext().getFilesDir(), n.a("003Qdgdd5e"));
        } catch (Throwable th2) {
            th = th2;
            file = null;
        }
        try {
            File file2 = new File(FlySDK.getContext().getFilesDir(), n.a("007DbjbhdgJeRddbhBa"));
            if (!k.a().b()) {
                ResHelper.deleteFileAndFolder(file);
                ResHelper.deleteFileAndFolder(file2);
            } else {
                if (!TextUtils.isEmpty(strB)) {
                    if (c()) {
                        HashMap<String, Object> mapE = q.e();
                        mapE.put(n.a("007Nbb-d[bhdgbgbi8c"), String.valueOf(cn.fly.commons.cc.a.a()));
                        ArrayList arrayList = (ArrayList) ((HashMap) new NetCommunicator(1024, "9e87e8d4b8f52f2916d0fb4342aa6b54a81a05666d0bdb23cc5ebf3a07440bc3976adff1ce11c64ddcdbfc017920648217196d51e3165e780e58b5460c525ee9", "13bda4b87eb42ab9e64e6b4f3d17cf8005a4ae94af37bc9fd76ebd91a828f017c81bd63cbe2924e361e20003b9e5f47cdac1f5fba5fca05730a32c5c65869590287207e79a604a2aac429e55f0d35c211367bd226dd5e57df7810f036071854aa1061a0f34b418b9178895a531107c652a428cfa6ecfa65333580ae7e0edf0e1").requestWithoutEncode(false, NetCommunicator.getCommonDefaultHeaders(), mapE, strB, true)).get(n.a("004eEbgdg9g"));
                        if (arrayList != null && !arrayList.isEmpty()) {
                            synchronized (u.j) {
                                j.clear();
                                j.put(n.a("002eg"), arrayList);
                            }
                        }
                        ResHelper.deleteFileAndFolder(file);
                        ResHelper.deleteFileAndFolder(file2);
                        return;
                    }
                    return;
                }
                ResHelper.deleteFileAndFolder(file);
            }
        } catch (Throwable th3) {
            th = th3;
            try {
                h.a().a(9, -1, th, "-1");
                ResHelper.deleteFileAndFolder(file);
            } finally {
                s();
            }
        }
    }

    private static void u() {
        if (z.h() && n.compareAndSet(false, true)) {
            try {
                cn.fly.mgs.a.b.a();
            } catch (Throwable th) {
            }
        }
    }
}