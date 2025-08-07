package cn.fly.commons;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import java.util.HashMap;

/* loaded from: classes.dex */
public class q {
    public static boolean a = false;

    public static void a(boolean z) {
        try {
            z.a(z);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
    }

    public static String a() {
        if (TextUtils.isEmpty(w.a) && FlySDK.getContext() != null) {
            w.a(FlySDK.getContext());
        }
        if (TextUtils.isEmpty(w.a)) {
            return w.c;
        }
        return w.a;
    }

    public static int b() {
        int iD = z.d();
        if (iD == 1) {
            return 1;
        }
        if (iD == 0) {
            return -1;
        }
        return 0;
    }

    public static boolean c() {
        int iB = b();
        if (iB == 2 || iB == 1) {
            return c.b();
        }
        return false;
    }

    public static boolean d() {
        int iB = b();
        if (iB != 2 && iB != 1) {
            return true;
        }
        z.g();
        return true ^ c.a();
    }

    public static HashMap<String, Object> a(String str) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(n.a("006bhhBcf1d3ca"), a());
        map.put(n.a("006bhhh]cfch"), DH.SyncMtd.getPackageName());
        map.put(n.a("006bhhFbb<dSbh"), DH.SyncMtd.getAppVersionName());
        map.put(n.a("004hebg"), String.valueOf(DH.SyncMtd.getPlatformCode()));
        map.put(n.a("011cdg4debibhcfDg6caBhd"), str);
        String strB = f.b();
        if (!TextUtils.isEmpty(strB)) {
            map.put(n.a("0048babebgba"), strB);
        }
        return map;
    }

    public static HashMap<String, Object> e() {
        final HashMap<String, Object>[] mapArr = {new HashMap<>()};
        DH.requester(FlySDK.getContext()).getCarrierStrict(false).getDetailNetworkTypeForStatic().getMIUIVersionForFly().getSignMD5().getODH().request(new DH.DHResponder() { // from class: cn.fly.commons.q.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                mapArr[0] = q.a(dHResponse.getDetailNetworkTypeForStatic());
                mapArr[0].put(n.a("006(dgbacfbb-dKbh"), Integer.valueOf(FlySDK.SDK_VERSION_CODE));
                mapArr[0].put(n.a("0049babebgba"), f.a((FlyProduct) null));
                mapArr[0].put(n.a("006bhhHbbQdDbh"), Integer.valueOf(DH.SyncMtd.getAppVersion()));
                mapArr[0].put(n.a("007abKbhbhbgAd%bh"), dHResponse.getCarrierStrict(new int[0]));
                mapArr[0].put(n.a("0054bdbiba de"), DH.SyncMtd.getModelForFly());
                mapArr[0].put(n.a("007+cd)bag8bibhca"), DH.SyncMtd.getManufacturerForFly());
                mapArr[0].put(n.a("006!dgcadgbb'd[bh"), DH.SyncMtd.getOSVersionNameForFly());
                mapArr[0].put(n.a("005Mbebgbb$dIbh"), dHResponse.getMIUIVersionForFly());
                mapArr[0].put(n.a("009;dgcadgbb@dKbhbg]cg"), Integer.valueOf(DH.SyncMtd.getOSVersionIntForFly()));
                mapArr[0].put(n.a("010ae5bg%dcgOdabgbd0d"), Long.valueOf(System.currentTimeMillis()));
                mapArr[0].put(n.a("006bhh6bdbafg"), dHResponse.getSignMD5());
                mapArr[0].put(n.a("0053ddbh3bc4ba"), DH.SyncMtd.getBrandForFly());
                mapArr[0].put("usridt", ac.e());
                mapArr[0].put(n.a("004 bdbibgba"), dHResponse.getODH());
            }
        });
        return mapArr[0];
    }

    public static String a(String str, String str2, String str3, boolean z) {
        if (d()) {
            FlyLog.getInstance().d("isForb: true", new Object[0]);
            return null;
        }
        return j.a().a(str, str2, str3, z);
    }
}