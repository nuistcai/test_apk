package cn.fly.mgs.a;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.j;
import cn.fly.commons.m;
import cn.fly.commons.q;
import cn.fly.tools.network.NetCommunicator;
import cn.fly.tools.utils.DH;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public class d {
    private static NetCommunicator a;

    public static <T> T a(List<HashMap<String, String>> list, String str, boolean z) throws Throwable {
        HashMap<String, Object> mapA = a();
        mapA.put("guardId", str);
        mapA.put("targetAppInfoDtoList", list);
        mapA.put("deviceSwitch", Integer.valueOf(z ? 1 : 0));
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final String[] strArr = new String[1];
        final int[] iArr = new int[1];
        DH.requester(FlySDK.getContext()).getHmOsDetailedVer().getHmEPMState().request(new DH.DHResponder() { // from class: cn.fly.mgs.a.d.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                strArr[0] = dHResponse.getHmOsDetailedVer();
                iArr[0] = dHResponse.getHmEPMState();
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        mapA.put("hmv", strArr[0]);
        mapA.put("ep", Integer.valueOf(iArr[0]));
        e.a().a("[GD][/v6/gd] request: " + mapA);
        return (T) a("/v7/gd", mapA);
    }

    public static <T> T a(List<HashMap<String, Object>> list, String str, String str2) throws Throwable {
        HashMap<String, Object> mapA = a();
        mapA.put("guardId", str);
        mapA.put("workId", str2);
        mapA.put("pkgList", list);
        e.a().a("[request][/v6/pu] request: " + mapA);
        return (T) a("/v7/pu", mapA);
    }

    public static <T> T a(String str, String str2, String str3, String str4, String str5, String str6, int i) throws Throwable {
        HashMap<String, Object> mapA = a();
        mapA.put("guardId", str5);
        mapA.put("workId", str6);
        mapA.put("pullDuid", str);
        mapA.put("pullAppkey", str2);
        mapA.put("pullPkg", str3);
        mapA.put("pullGuardId", str4);
        mapA.put("pullTime", Long.valueOf(System.currentTimeMillis()));
        mapA.put("actType", Integer.valueOf(i));
        e.a().a("[request][/v6/bpu] request: " + mapA);
        return (T) a("/v7/bpu", mapA);
    }

    public static <T> T a(boolean z, boolean z2) throws Throwable {
        HashMap<String, Object> mapA = a();
        mapA.put("guardId", cn.fly.mcl.b.a.a());
        if (z2) {
            if (!TextUtils.isEmpty(i.c())) {
                mapA.put(m.a("004Zfefifkfe"), i.c());
            }
            if (!TextUtils.isEmpty(i.d())) {
                mapA.put("guardId", i.d());
            }
        }
        mapA.put("deviceSwitch", Integer.valueOf(z ? 1 : 0));
        e.a().a("[request][deviceSwitch/updateV5] request: " + mapA);
        return (T) a("/v7/dsu", mapA);
    }

    private static HashMap<String, Object> a() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("versionTime", "2021.11.17 18:38");
        map.put(m.a("006fll0gjJhWge"), q.a());
        map.put(m.a("006fll2ffDhGfl"), Integer.valueOf(DH.SyncMtd.getAppVersion()));
        map.put("platVersion", DH.SyncMtd.getOSVersionNameForFly());
        map.put(m.a("006flll=gjgl"), FlySDK.getContext().getPackageName());
        map.put(m.a("006Nhkfegjff=hRfl"), 50000);
        map.put(m.a("004:fefifkfe"), f.a().f());
        map.put(m.a("007l1flfmfefiGek"), 1);
        map.put(m.a("004lifk"), 1);
        map.put(m.a("005Whhfl+fg]fe"), DH.SyncMtd.getManufacturerForFly());
        map.put(m.a("0053fhfmfe4hi"), DH.SyncMtd.getModelForFly());
        map.put("modelVersion", DH.SyncMtd.getOSVersionNameForFly());
        return map;
    }

    private static synchronized NetCommunicator b() {
        if (a == null) {
            a = new NetCommunicator(1024, "009cbd92ccef123be840deec0c6ed0547194c1e471d11b6f375e56038458fb18833e5bab2e1206b261495d7e2d1d9e5aa859e6d4b671a8ca5d78efede48e291a3f", "1dfd1d615cb891ce9a76f42d036af7fce5f8b8efaa11b2f42590ecc4ea4cff28f5f6b0726aeb76254ab5b02a58c1d5b486c39d9da1a58fa6ba2f22196493b3a4cbc283dcf749bf63679ee24d185de70c8dfe05605886c9b53e9f569082eabdf98c4fb0dcf07eb9bb3e647903489ff0b5d933bd004af5be4a1022fdda41f347f1");
        }
        return a;
    }

    private static <T> T a(String str, HashMap<String, Object> map) throws Throwable {
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("versionTime", "2021.11.17 18:38");
        return (T) b().requestWithoutEncode(true, map2, map, j.a().a("gdg") + str, true);
    }
}