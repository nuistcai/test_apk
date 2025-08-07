package cn.fly.commons;

import android.text.TextUtils;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.HashonHelper;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public class j {
    private static volatile j d;
    private ArrayList<String> g;
    private volatile HashMap<String, Long> h;
    private ReentrantReadWriteLock i;
    private ReentrantReadWriteLock j;
    private static final CountDownLatch c = new CountDownLatch(1);
    public static HashMap<String, String> a = new HashMap<>();
    private static final ArrayList<String> b = new ArrayList<>(Arrays.asList("cfgc.zztfly.com"));
    private volatile CountDownLatch k = c;
    private volatile boolean l = true;
    private volatile HashMap<String, HashMap<String, ArrayList<String>>> e = ad.b().q();
    private volatile HashMap<String, String> f = ad.b().r();

    static {
        a.put("gcfg", "cfgc.zztfly.com");
        a.put("gclg", "upc.zztfly.com");
        a.put("el", "errc.zztfly.com");
        a.put("dg", "devc.zztfly.com");
        a.put("dtc", "fdl.zztfly.com");
        a.put("tcig", "tgc.zztfly.com");
        a.put("gdg", "gd.zztfly.com");
    }

    private j() {
        this.g = ad.b().s();
        if (this.g == null || this.g.isEmpty()) {
            this.g = b;
        }
        this.h = ad.b().t();
        this.i = new ReentrantReadWriteLock();
        this.j = new ReentrantReadWriteLock();
    }

    public static j a() {
        if (d == null) {
            synchronized (j.class) {
                if (d == null) {
                    d = new j();
                }
            }
        }
        return d;
    }

    public void b() {
        this.l = false;
    }

    public String a(String str) {
        return C0041r.a(a().a("FCOMMON", str, a.get(str), false));
    }

    public String a(String str, String str2, String str3, boolean z) {
        HashMap<String, ArrayList<String>> map;
        ArrayList<String> arrayList;
        FlyLog.getInstance().d("DM get: " + str + "-" + str2 + "-" + str3 + "-" + z, new Object[0]);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || !this.l) {
            FlyLog.getInstance().d("DM Params 'sName' or 'aName' is null", new Object[0]);
            return str3;
        }
        boolean z2 = this.k.getCount() == 0;
        try {
            if (this.i.readLock().tryLock(3000L, TimeUnit.MILLISECONDS) && this.e != null && this.e.containsKey(str) && (map = this.e.get(str)) != null && map.containsKey(str2) && (arrayList = map.get(str2)) != null && !arrayList.isEmpty()) {
                Iterator<String> it = arrayList.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (z && a(str, str2)) {
                        if (a(str, str2, next)) {
                            FlyLog.getInstance().d("DM rtn [cac|chk]: " + str + "-" + str2 + ": " + next, new Object[0]);
                            this.f.put(str + "-" + str2, next);
                            ad.b().d(this.f);
                            try {
                                this.i.readLock().unlock();
                            } catch (Throwable th) {
                                FlyLog.getInstance().d(th, "DM " + th.getMessage(), new Object[0]);
                            }
                            return next;
                        }
                    } else {
                        if (this.f.containsKey(str + "-" + str2)) {
                            String str4 = this.f.get(str + "-" + str2);
                            FlyLog.getInstance().d("DM rtn [cac|chk_abt]: " + str + "-" + str2 + ": " + str4, new Object[0]);
                            try {
                                this.i.readLock().unlock();
                            } catch (Throwable th2) {
                                FlyLog.getInstance().d(th2, "DM " + th2.getMessage(), new Object[0]);
                            }
                            return str4;
                        }
                        if (!TextUtils.isEmpty(next)) {
                            FlyLog.getInstance().d("DM rtn [cac]: " + str + "-" + str2 + ": " + next, new Object[0]);
                            try {
                                this.i.readLock().unlock();
                            } catch (Throwable th3) {
                                FlyLog.getInstance().d(th3, "DM " + th3.getMessage(), new Object[0]);
                            }
                            return next;
                        }
                    }
                }
            }
            try {
                this.i.readLock().unlock();
            } catch (Throwable th4) {
                FlyLog.getInstance().d(th4, "DM " + th4.getMessage(), new Object[0]);
            }
        } catch (Throwable th5) {
            try {
                FlyLog.getInstance().d(th5, "DM " + th5.getMessage(), new Object[0]);
                try {
                    this.i.readLock().unlock();
                } catch (Throwable th6) {
                    FlyLog.getInstance().d(th6, "DM " + th6.getMessage(), new Object[0]);
                }
            } catch (Throwable th7) {
                try {
                    this.i.readLock().unlock();
                } catch (Throwable th8) {
                    FlyLog.getInstance().d(th8, "DM " + th8.getMessage(), new Object[0]);
                }
                throw th7;
            }
        }
        try {
            this.f.remove(str + "-" + str2);
            ad.b().d(this.f);
            if (!z || !a(str, str2)) {
                if (!this.f.containsKey(str + "-" + str2)) {
                    FlyLog.getInstance().d("DM rtn [def]" + str + "-" + str2 + ": " + str3, new Object[0]);
                    return str3;
                }
                String str5 = this.f.get(str + "-" + str2);
                FlyLog.getInstance().d("DM rtn [def|chk_abt]: " + str + "-" + str2 + ": " + str5, new Object[0]);
                return str5;
            }
            if (a(str, str2, str3)) {
                FlyLog.getInstance().d("DM rtn [def|chk_true]: " + str + "-" + str2 + ": " + str3, new Object[0]);
                this.f.put(str + "-" + str2, str3);
                ad.b().d(this.f);
                return str3;
            }
            if (z2) {
                FlyLog.getInstance().d("DM rtn [def|chk_false]" + str + "-" + str2 + ": " + str3, new Object[0]);
                return str3;
            }
            if (this.k.await(5000L, TimeUnit.MILLISECONDS)) {
                FlyLog.getInstance().d("DM awt next", new Object[0]);
                return a(str, str2, str3, z);
            }
            FlyLog.getInstance().d("DM rtn [def|awt_to]" + str + "-" + str2 + ": " + str3, new Object[0]);
            return str3;
        } catch (Throwable th9) {
            FlyLog.getInstance().d(th9, "DM " + th9.getMessage(), new Object[0]);
            FlyLog.getInstance().d("DM rtn [def|exp]" + str + "-" + str2 + ": " + str3, new Object[0]);
            return str3;
        }
    }

    public void c() {
        if (this.k == c || this.k.getCount() == 0) {
            FlyLog.getInstance().d("DM obt start", new Object[0]);
            this.k = new CountDownLatch(1);
            ab.a.execute(new Runnable() { // from class: cn.fly.commons.j.1
                @Override // java.lang.Runnable
                public void run() {
                    j.this.a(j.this.k, 0);
                }
            });
            return;
        }
        FlyLog.getInstance().d("DM obt abort", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:101:0x02de A[Catch: all -> 0x02e6, TRY_LEAVE, TryCatch #6 {all -> 0x02e6, blocks: (B:85:0x026d, B:87:0x0277, B:89:0x027d, B:90:0x0286, B:92:0x028c, B:94:0x0298, B:96:0x029c, B:98:0x02a2, B:99:0x02d2, B:101:0x02de), top: B:144:0x026d, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0372 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(CountDownLatch countDownLatch, int i) {
        ArrayList arrayList;
        try {
            if (this.g != null && i < this.g.size()) {
                String strA = C0041r.a(this.g.get(i) + "/dm");
                HashMap<String, Object> map = new HashMap<>();
                map.put(o.a("006djj?ehQf]ec"), q.a());
                NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
                networkTimeOut.connectionTimeout = 3000;
                networkTimeOut.readTimout = 5000;
                String strHttpGetNew = new NetworkHelper().httpGetNew(strA, map, null, networkTimeOut);
                FlyLog.getInstance().d("DM resp: " + strHttpGetNew, new Object[0]);
                HashMap mapFromJson = HashonHelper.fromJson(strHttpGetNew);
                if (mapFromJson == null || mapFromJson.isEmpty()) {
                    a(countDownLatch, i + 1);
                } else {
                    Object obj = mapFromJson.get(o.a("004c1dkdc*f"));
                    if (obj == null) {
                        a(countDownLatch, i + 1);
                    } else if (((Integer) obj).intValue() != 200) {
                        a(countDownLatch, i + 1);
                    } else {
                        HashMap map2 = (HashMap) mapFromJson.get(o.a("004QdcPdid"));
                        if (map2 == null || map2.isEmpty()) {
                            ad.b().a((ArrayList<String>) null);
                            ad.b().c((HashMap<String, HashMap<String, ArrayList<String>>>) null);
                        } else {
                            try {
                                HashMap map3 = (HashMap) map2.get(o.a("004Rdc-did"));
                                if (map3 == null || map3.isEmpty()) {
                                    ad.b().c((HashMap<String, HashMap<String, ArrayList<String>>>) null);
                                } else {
                                    HashMap map4 = new HashMap();
                                    for (Map.Entry entry : map3.entrySet()) {
                                        String str = (String) entry.getKey();
                                        HashMap map5 = (HashMap) entry.getValue();
                                        HashMap map6 = new HashMap();
                                        if (map5 != null && !map5.isEmpty()) {
                                            for (Map.Entry entry2 : map5.entrySet()) {
                                                String str2 = (String) entry2.getKey();
                                                ArrayList arrayList2 = (ArrayList) entry2.getValue();
                                                ArrayList arrayList3 = new ArrayList();
                                                if (arrayList2 != null && !arrayList2.isEmpty()) {
                                                    Iterator it = arrayList2.iterator();
                                                    while (it.hasNext()) {
                                                        String str3 = (String) it.next();
                                                        if (b(str3)) {
                                                            arrayList3.add(str3);
                                                        }
                                                    }
                                                }
                                                if (!arrayList3.isEmpty()) {
                                                    map6.put(str2, arrayList3);
                                                }
                                            }
                                        }
                                        if (!map6.isEmpty()) {
                                            map4.put(str, map6);
                                        }
                                    }
                                    if (!map4.isEmpty()) {
                                        try {
                                            FlyLog.getInstance().d("DM busi w 2 cac: " + map4, new Object[0]);
                                            if (this.i.writeLock().tryLock(3000L, TimeUnit.MILLISECONDS)) {
                                                this.e.clear();
                                                this.e.putAll(map4);
                                                ad.b().c(this.e);
                                            }
                                            try {
                                                this.i.writeLock().unlock();
                                            } catch (Throwable th) {
                                                FlyLog.getInstance().d(th, "DM " + th.getMessage(), new Object[0]);
                                            }
                                        } catch (Throwable th2) {
                                            try {
                                                FlyLog.getInstance().d(th2, "DM " + th2.getMessage(), new Object[0]);
                                                try {
                                                    this.i.writeLock().unlock();
                                                } catch (Throwable th3) {
                                                    FlyLog.getInstance().d(th3, "DM " + th3.getMessage(), new Object[0]);
                                                }
                                            } finally {
                                            }
                                        }
                                    } else {
                                        FlyLog.getInstance().d("DM busi no avai dm", new Object[0]);
                                    }
                                }
                            } finally {
                                try {
                                    arrayList = (ArrayList) map2.get("p");
                                    if (arrayList != null) {
                                        ad.b().a((ArrayList<String>) null);
                                    }
                                } finally {
                                }
                            }
                            try {
                                arrayList = (ArrayList) map2.get("p");
                                if (arrayList != null || arrayList.isEmpty()) {
                                    ad.b().a((ArrayList<String>) null);
                                } else {
                                    ArrayList arrayList4 = new ArrayList();
                                    Iterator it2 = arrayList.iterator();
                                    while (it2.hasNext()) {
                                        String str4 = (String) it2.next();
                                        if (b(str4)) {
                                            arrayList4.add(str4);
                                        }
                                    }
                                    if (!arrayList4.isEmpty()) {
                                        FlyLog.getInstance().d("DM prx w 2 cac: " + arrayList4, new Object[0]);
                                        this.g.clear();
                                        this.g.addAll(arrayList4);
                                        ad.b().a(this.g);
                                    } else {
                                        FlyLog.getInstance().d("DM prx no avai dm", new Object[0]);
                                    }
                                }
                            } catch (Throwable th4) {
                                FlyLog.getInstance().d(th4, "DM " + th4.getMessage(), new Object[0]);
                            }
                        }
                    }
                }
            } else {
                FlyLog.getInstance().w("DM No pdm");
            }
        } catch (Throwable th5) {
            try {
                FlyLog.getInstance().d(th5, "DM " + th5.getMessage(), new Object[0]);
                a(countDownLatch, i + 1);
                if (countDownLatch.getCount() <= 0) {
                }
            } finally {
                if (countDownLatch.getCount() > 0) {
                }
            }
        }
    }

    private boolean a(String str, String str2) {
        Long l;
        boolean z = true;
        try {
            if (this.j.readLock().tryLock(3000L, TimeUnit.MILLISECONDS)) {
                String str3 = str + "_" + str2;
                if (this.h != null && this.h.containsKey(str3) && (l = this.h.get(str3)) != null) {
                    if (System.currentTimeMillis() - l.longValue() < 1800000) {
                        z = false;
                    }
                }
            }
            try {
                this.j.readLock().unlock();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th, "DM " + th.getMessage(), new Object[0]);
            }
        } catch (Throwable th2) {
            try {
                FlyLog.getInstance().d(th2, "DM " + th2.getMessage(), new Object[0]);
                try {
                    this.j.readLock().unlock();
                } catch (Throwable th3) {
                    FlyLog.getInstance().d(th3, "DM " + th3.getMessage(), new Object[0]);
                }
            } catch (Throwable th4) {
                try {
                    this.j.readLock().unlock();
                } catch (Throwable th5) {
                    FlyLog.getInstance().d(th5, "DM " + th5.getMessage(), new Object[0]);
                }
                throw th4;
            }
        }
        FlyLog.getInstance().d("DM ck dur: " + str + "-" + str2 + ", pass: " + z, new Object[0]);
        return z;
    }

    private boolean b(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                InetAddress[] allByName = InetAddress.getAllByName(str);
                if (allByName != null) {
                    for (InetAddress inetAddress : allByName) {
                        if (!c(inetAddress.getHostAddress())) {
                            FlyLog.getInstance().d("DM ck ht: " + str + ", fai", new Object[0]);
                            return false;
                        }
                    }
                }
                FlyLog.getInstance().d("DM ck ht: " + str + ", suc", new Object[0]);
                return true;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th, "DM " + th.getMessage(), new Object[0]);
            }
        }
        FlyLog.getInstance().d("DM ck ht: " + str + ", fai_emp|exp", new Object[0]);
        return false;
    }

    private boolean a(String str, String str2, String str3) {
        boolean zB = b(str3);
        if (zB) {
            try {
                if (this.j.writeLock().tryLock(3000L, TimeUnit.MILLISECONDS)) {
                    this.h.put(str + "_" + str2, Long.valueOf(System.currentTimeMillis()));
                    ad.b().e(this.h);
                }
                try {
                    this.j.writeLock().unlock();
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th, "DM " + th.getMessage(), new Object[0]);
                }
            } catch (Throwable th2) {
                try {
                    FlyLog.getInstance().d(th2, "DM " + th2.getMessage(), new Object[0]);
                    try {
                        this.j.writeLock().unlock();
                    } catch (Throwable th3) {
                        FlyLog.getInstance().d(th3, "DM " + th3.getMessage(), new Object[0]);
                    }
                } catch (Throwable th4) {
                    try {
                        this.j.writeLock().unlock();
                    } catch (Throwable th5) {
                        FlyLog.getInstance().d(th5, "DM " + th5.getMessage(), new Object[0]);
                    }
                    throw th4;
                }
            }
        }
        return zB;
    }

    private static boolean c(String str) {
        if (TextUtils.isEmpty(str) || str.equals("127.0.0.1") || str.startsWith("10.") || str.startsWith("192.168")) {
            return false;
        }
        if (str.startsWith("172.")) {
            String[] strArrSplit = str.split("\\.");
            if (strArrSplit.length > 1) {
                try {
                    int i = Integer.parseInt(strArrSplit[1]);
                    return i < 16 || i > 31;
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th, "DM " + th.getMessage(), new Object[0]);
                }
            }
        }
        return true;
    }
}