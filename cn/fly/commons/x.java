package cn.fly.commons;

import android.text.TextUtils;
import android.util.Base64;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.HashonHelper;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class x {
    private ScheduledExecutorService a = Executors.newScheduledThreadPool(0);

    public void a() {
        if (DH.SyncMtd.isAut()) {
            y.a().e(y.a().e() + 1).h();
        }
    }

    public void b() {
        try {
            if (DH.SyncMtd.isAut()) {
                if (k()) {
                    FlyLog.getInstance().d("[PRE] try", new Object[0]);
                    boolean z = y.a().e() >= j();
                    boolean z2 = System.currentTimeMillis() > i();
                    boolean zG = y.a().g();
                    if (z2 && z) {
                        if (!c() && DH.SyncMtd.getOSVersionInt() >= 17) {
                            a(h());
                        }
                    } else if (zG) {
                        g();
                    }
                    return;
                }
                FlyLog.getInstance().d("[PRE] esc", new Object[0]);
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    private boolean c() {
        final boolean[] zArr = {false};
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        DH.requester(FlySDK.getContext()).debugable().checkDebbing().isRooted().request(new DH.DHResponder() { // from class: cn.fly.commons.x.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                FlyLog.getInstance().d("[PRE] ckDb: " + dHResponse.checkDebbing() + ", db: " + dHResponse.debugable() + ", iRt: " + dHResponse.isRooted(), new Object[0]);
                if (dHResponse.checkDebbing() || dHResponse.debugable() || dHResponse.isRooted()) {
                    zArr[0] = true;
                }
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await(300L, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
        }
        return zArr[0] || e() || d() || f();
    }

    private boolean d() {
        String manufacturer = DH.SyncMtd.getManufacturer();
        String brand = DH.SyncMtd.getBrand();
        if (TextUtils.isEmpty(manufacturer) || !manufacturer.toLowerCase().contains(cn.fly.commons.a.l.a("006Jfkelelfk2hg"))) {
            if (!TextUtils.isEmpty(brand) && brand.toLowerCase().contains(cn.fly.commons.a.l.a("0062fkelelfkHhg"))) {
                return true;
            }
            return false;
        }
        return true;
    }

    private boolean e() {
        try {
            return Class.forName(FlySDK.getContext().getPackageName() + cn.fly.commons.a.l.a("012Pemgkehej7hRedfeel3fFfgejfk")).getField(cn.fly.commons.a.l.a("0056gmhjgkflje")).getBoolean(null);
        } catch (Throwable th) {
            return false;
        }
    }

    private boolean f() {
        try {
            FlySDK.getContext().getClassLoader().loadClass(cn.fly.commons.a.l.a("021hgeEfiCdefe4ekfdemgfSge.fife_efeMekfd"));
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        try {
            CountDownLatch countDownLatchG = z.g();
            FlyLog.getInstance().d(DH.SyncMtd.isInMainProcess() ? "[PRE] main" : "[PRE] sub", new Object[0]);
            c.j();
            z.a(countDownLatchG);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    private int h() {
        int iIntValue = ((Integer) y.a().b("key_cdt", -1)).intValue();
        if (iIntValue != -1) {
            return iIntValue;
        }
        return new SecureRandom().nextInt(30) + 270;
    }

    private long i() {
        long jLongValue = ((Long) y.a().b("key_nat", 0L)).longValue();
        if (jLongValue == 0) {
            long jD = y.a().d();
            if (jD == 0) {
                jD = System.currentTimeMillis();
                y.a().a(jD);
            }
            long jB = jD + (y.a().b(15) * 86400000);
            y.a().a("key_nat", Long.valueOf(jB)).h();
            return jB;
        }
        return jLongValue;
    }

    private int j() {
        try {
            int iC = y.a().c(Integer.MIN_VALUE);
            if (iC != Integer.MIN_VALUE) {
                return iC;
            }
            return 0;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return 0;
        }
    }

    private boolean k() {
        try {
            int iB = y.a().b(Integer.MIN_VALUE);
            int iJ = j();
            if ((iB != Integer.MIN_VALUE && iB < 0) || (iJ != Integer.MIN_VALUE && iJ < 0)) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return false;
        }
    }

    private void a(long j) {
        try {
            cn.fly.tools.utils.i iVar = new cn.fly.tools.utils.i() { // from class: cn.fly.commons.x.2
                @Override // cn.fly.tools.utils.i
                public void a() {
                    int iIntValue;
                    int iIntValue2;
                    boolean zBooleanValue;
                    int iNextInt;
                    int iIntValue3;
                    try {
                        long jD = y.a().d();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put(cn.fly.commons.a.l.a("004khej"), "1");
                        map.put(cn.fly.commons.a.l.a("006ekkMfiVgFfd"), q.a());
                        map.put(cn.fly.commons.a.l.a("006TgjfdgjeeJg)ek"), String.valueOf(DH.SyncMtd.getOSVersionInt()));
                        map.put(cn.fly.commons.a.l.a("007Ffg2edj>elekfd"), DH.SyncMtd.getManufacturer());
                        map.put(cn.fly.commons.a.l.a("005Degeled7gh"), DH.SyncMtd.getModel());
                        map.put(cn.fly.commons.a.l.a("006ekkk$fifk"), DH.SyncMtd.getPackageName());
                        map.put(cn.fly.commons.a.l.a("002jKgj"), Long.valueOf(System.currentTimeMillis()));
                        map.put("ait", Long.valueOf(jD));
                        map.put("dc", ac.a(0));
                        map.put("clv", Integer.valueOf(FlySDK.SDK_VERSION_CODE));
                        long jF = y.a().f();
                        if (jF > 0) {
                            map.put("acv", DH.SyncMtd.getAppVersionName());
                            map.put("cvit", Long.valueOf(jF));
                        }
                        String strB = ad.b().b("key_ched_od", (String) null);
                        if (!TextUtils.isEmpty(strB)) {
                            try {
                                strB = Base64.encodeToString(Data.AES128Encode(Data.MD5(DH.SyncMtd.getManufacturer()), strB), 2);
                                map.put(cn.fly.commons.a.l.a("004Vegelejed"), strB);
                            } catch (Throwable th) {
                                FlyLog.getInstance().d(th);
                            }
                        }
                        String strB2 = f.b();
                        if (!TextUtils.isEmpty(strB2)) {
                            map.put(cn.fly.commons.a.l.a("004+edehejed"), strB2);
                        }
                        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
                        networkTimeOut.connectionTimeout = 5000;
                        networkTimeOut.readTimout = 3000;
                        String strJsonPost = new NetworkHelper().jsonPost(j.a().a("gcfg") + cn.fly.commons.a.l.a("007mkem:fkHd:fg"), map, null, networkTimeOut);
                        HashMap mapFromJson = HashonHelper.fromJson(strJsonPost);
                        if (!"200".equals(String.valueOf(mapFromJson.get(cn.fly.commons.a.l.a("004d@eled,g"))))) {
                            throw new Throwable("response is illegal: " + strJsonPost);
                        }
                        HashMap map2 = (HashMap) mapFromJson.get(cn.fly.commons.a.l.a("004_ed-eje"));
                        if (map2 == null || map2.isEmpty()) {
                            throw new Throwable("data is illegal: " + map2);
                        }
                        y.a().e(0);
                        Object obj = map2.get("wd");
                        if (obj == null) {
                            iIntValue = 0;
                        } else {
                            iIntValue = ((Integer) obj).intValue();
                        }
                        Object obj2 = map2.get("wf");
                        if (obj2 == null) {
                            iIntValue2 = 0;
                        } else {
                            iIntValue2 = ((Integer) obj2).intValue();
                        }
                        Object obj3 = map2.get("ds");
                        if (obj3 == null) {
                            zBooleanValue = false;
                        } else {
                            zBooleanValue = ((Boolean) obj3).booleanValue();
                        }
                        Object obj4 = map2.get("cdt");
                        if (obj4 != null) {
                            iNextInt = ((Integer) obj4).intValue();
                        } else {
                            iNextInt = new SecureRandom().nextInt(30) + 270;
                        }
                        Object obj5 = map2.get("ait");
                        if (obj5 instanceof Long) {
                            long jLongValue = ((Long) obj5).longValue();
                            y.a().a("key_nat", Long.valueOf(jD == jLongValue ? (iIntValue * 86400000) + System.currentTimeMillis() : (iIntValue * 86400000) + jLongValue)).a(jLongValue);
                        }
                        Object obj6 = map2.get("ccd");
                        if (!(obj6 instanceof Integer)) {
                            iIntValue3 = 0;
                        } else {
                            iIntValue3 = ((Integer) obj6).intValue();
                        }
                        y.a().d(iIntValue).a("key_wt_tms", Integer.valueOf(iIntValue2)).a("key_iksccd", Integer.valueOf(iIntValue3)).a(zBooleanValue).a("key_cdt", Integer.valueOf(iNextInt)).h();
                        if (zBooleanValue) {
                            FlyLog.getInstance().d("[PRE] ds", new Object[0]);
                            x.this.g();
                        } else if (iIntValue3 == 1) {
                            b.a().a(strB, strB2);
                        }
                    } catch (Throwable th2) {
                        FlyLog.getInstance().d(th2);
                    }
                }
            };
            FlyLog.getInstance().d("[PRE] dy: " + j, new Object[0]);
            this.a.schedule(iVar, j, TimeUnit.SECONDS);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }
}