package cn.fly.mcl.tcp;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.ab;
import cn.fly.commons.ac;
import cn.fly.commons.ad;
import cn.fly.commons.j;
import cn.fly.commons.n;
import cn.fly.commons.q;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.i;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class PSIDManager {
    private static PSIDManager a;
    private byte[] d = new byte[0];
    private AtomicBoolean e = new AtomicBoolean(false);
    private AtomicBoolean f = new AtomicBoolean(false);
    private int g = 1;
    private String c = ad.b().v();
    private String b = cn.fly.mgs.a.f.a().f();

    private PSIDManager() {
    }

    public static PSIDManager a() {
        if (a == null) {
            synchronized (PSIDManager.class) {
                if (a == null) {
                    a = new PSIDManager();
                }
            }
        }
        return a;
    }

    public String b() throws NoPsrdException {
        String str;
        synchronized (this.d) {
            Callable<Map<String, Object>> callableC = ac.c();
            str = null;
            if (callableC != null) {
                cn.fly.mcl.c.b.a().b("getPsrd: Nw PU5H");
                try {
                    Map<String, Object> mapCall = callableC.call();
                    if (mapCall != null && mapCall.containsKey("psid")) {
                        str = (String) mapCall.get("psid");
                    }
                    cn.fly.mcl.c.b.a().b("getPsrd: " + str);
                    if (TextUtils.isEmpty(str)) {
                        cn.fly.mcl.c.b.a().b("getPsrd: No val frm PU5H");
                        throw new NoPsrdException();
                    }
                } catch (Throwable th) {
                    cn.fly.mcl.c.b.a().b("getPsrd: Exc frm PU5H");
                    throw new NoPsrdException();
                }
            } else {
                cn.fly.mcl.c.b.a().b("getPsrd: No/Od PU5H");
            }
        }
        return str;
    }

    public String c() {
        return this.b + DH.SyncMtd.getPackageName();
    }

    public String d() throws NoPsrdException {
        String strB = b();
        if (!TextUtils.isEmpty(strB)) {
            return strB;
        }
        return c();
    }

    public boolean e() throws NoPsrdException {
        String strB = b();
        boolean z = (strB == null || strB.equals(this.c)) ? false : true;
        cn.fly.mcl.c.b.a().b("isRsrdChg: " + z);
        return z;
    }

    public void f() {
        ab.a.execute(new i() { // from class: cn.fly.mcl.tcp.PSIDManager.1
            @Override // cn.fly.tools.utils.i
            protected void a() throws Throwable {
                try {
                    int iD = ac.d();
                    cn.fly.mcl.c.b.a().b("chk migrt, pu5h sta: " + iD);
                    if (iD == 1) {
                        PSIDManager.this.h();
                    } else if (iD == 2) {
                        PSIDManager.this.g();
                    }
                } catch (Throwable th) {
                    cn.fly.mcl.c.b.a().b("migr: f");
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        try {
            String strB = b();
            cn.fly.mcl.c.b.a().b("chk migrt, nrd: " + strB + ", ord: " + this.c + ", done: " + this.f);
            if (!strB.equals(this.c) && this.f.compareAndSet(false, true)) {
                if (TextUtils.isEmpty(this.c)) {
                    a(strB);
                } else {
                    a(this.c, strB);
                }
                ad.b().g(strB);
                this.c = strB;
            }
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().b("migr: f");
        }
    }

    private void a(String str, String str2) throws Throwable {
        cn.fly.mcl.c.b.a().b("=> rid chg migrt");
        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
        networkTimeOut.readTimout = 10000;
        networkTimeOut.connectionTimeout = 5000;
        HashMap<String, Object> map = new HashMap<>();
        map.put(n.a("006bhh9cfTd;ca"), FlySDK.getAppkey());
        map.put(n.a("003h'cfch"), DH.SyncMtd.getPackageName());
        map.put("ridOld", str);
        map.put("ridNew", str2);
        map.put(n.a("004hebg"), Integer.valueOf(DH.SyncMtd.getPlatformCode()));
        map.put("appVer", String.valueOf(DH.SyncMtd.getAppVersion()));
        String str3 = j.a().a("tcig") + "/tcp/push/pbsr";
        cn.fly.mcl.c.b.a().b("url : " + str3 + " -> bd : " + map);
        String strHttpPostNew = new NetworkHelper().httpPostNew(str3, map, null, networkTimeOut);
        cn.fly.mcl.c.b.a().b("url : " + str3 + " -> rp : " + strHttpPostNew);
        HashonHelper.fromJson(strHttpPostNew);
        cn.fly.mcl.c.b.a().b("<= rid chg migrt");
    }

    private void a(String str) throws Throwable {
        cn.fly.mcl.c.b.a().b("=> d2r migrt");
        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
        networkTimeOut.readTimout = 10000;
        networkTimeOut.connectionTimeout = 5000;
        HashMap<String, Object> map = new HashMap<>();
        map.put(n.a("006bhhIcfBd<ca"), FlySDK.getAppkey());
        map.put(n.a("003h1cfch"), DH.SyncMtd.getPackageName());
        map.put("duidOld", this.b);
        map.put("ridNew", str);
        map.put(n.a("004hebg"), Integer.valueOf(DH.SyncMtd.getPlatformCode()));
        map.put("appVer", String.valueOf(DH.SyncMtd.getAppVersion()));
        String str2 = j.a().a("tcig") + "/tcp/push/pdctr";
        cn.fly.mcl.c.b.a().b("url : " + str2 + " -> bd : " + map);
        String strHttpPostNew = new NetworkHelper().httpPostNew(str2, map, null, networkTimeOut);
        cn.fly.mcl.c.b.a().b("url : " + str2 + " -> rp : " + strHttpPostNew);
        HashonHelper.fromJson(strHttpPostNew);
        cn.fly.mcl.c.b.a().b("<= d2r migrt");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() throws InterruptedException {
        if (cn.fly.mgs.a.f.a().d() && this.e.compareAndSet(false, true)) {
            b(cn.fly.mgs.a.f.a().c(), cn.fly.mgs.a.f.a().e());
        }
    }

    private void b(String str, String str2) throws InterruptedException {
        try {
            cn.fly.mcl.c.b.a().b("=> did chg migrt");
            NetworkHelper networkHelper = new NetworkHelper();
            NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
            networkTimeOut.connectionTimeout = 2000;
            networkTimeOut.readTimout = 5000;
            HashMap<String, Object> map = new HashMap<>();
            map.put(n.a("006bhh7cfQdDca"), q.a());
            map.put(n.a("003h=cfch"), DH.SyncMtd.getPackageName());
            map.put("duidOld", str2);
            map.put("duidNew", str);
            map.put("appVer", DH.SyncMtd.getAppVersionName());
            map.put(n.a("004hebg"), Integer.valueOf(DH.SyncMtd.getPlatformCode()));
            String str3 = j.a().a("tcig") + "/tcp/push/pbsd";
            cn.fly.mcl.c.b.a().b("[Request]TP url = " + str3 + "\nheaders = " + ((Object) null) + "\nvalues = " + map);
            String strHttpPostNew = networkHelper.httpPostNew(str3, map, null, networkTimeOut);
            cn.fly.mcl.c.b.a().b("[Response]TP url = " + str3 + "\nresp = " + strHttpPostNew);
            HashMap mapFromJson = HashonHelper.fromJson(strHttpPostNew);
            if (mapFromJson != null && !mapFromJson.isEmpty() && !"200".equals(String.valueOf(mapFromJson.get(n.a("004a*bibaMd"))))) {
                throw new Throwable("Req failed: " + strHttpPostNew);
            }
            cn.fly.mcl.c.b.a().b("<= did chg migrt");
            this.g = 1;
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
            if (this.g < 3) {
                try {
                    Thread.sleep(this.g * 1000);
                } catch (InterruptedException e) {
                    cn.fly.mcl.c.b.a().a(th);
                }
                this.g++;
                b(str, str2);
                return;
            }
            this.g = 1;
        }
    }

    public static class NoPsrdException extends Exception {
        public NoPsrdException() {
            super("No PSRD got from Pu5h");
        }
    }
}