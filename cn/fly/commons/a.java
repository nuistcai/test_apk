package cn.fly.commons;

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
import com.tencent.bugly.BuglyStrategy;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public final class a {
    private boolean a = false;
    private final byte[] b = new byte[0];

    synchronized String a() {
        C0002a c0002aK = ad.b().k();
        if (c0002aK == null || TextUtils.isEmpty(c0002aK.c())) {
            return null;
        }
        return c0002aK.c();
    }

    synchronized String b() {
        String strA;
        Throwable th;
        try {
            strA = a();
        } catch (Throwable th2) {
            strA = null;
            th = th2;
        }
        try {
        } catch (Throwable th3) {
            th = th3;
            FlyLog.getInstance().d(th);
            return strA;
        }
        if (!TextUtils.isEmpty(strA) && !TextUtils.equals("null", strA)) {
            return strA;
        }
        C0002a c0002aA = new b().a();
        if (c0002aA != null) {
            strA = c0002aA.c();
        }
        return strA;
    }

    public void a(final FlyProduct flyProduct, final cn.fly.tools.utils.d<Void> dVar) {
        FlyLog.getInstance().d("di init", new Object[0]);
        DH.RequestBuilder dm = DH.requester(FlySDK.getContext()).getAdvertisingID().getCarrierStrict(false).getMemoryInfo().getSizeInfo().cx().isRooted().getDeviceType().checkPad().getScreenSize().getDetailNetworkTypeForStatic().getODH().getOD().getAppLastUpdateTime().getMIUIVersionForFly().getInnerAppLanguage().getGrammaticalGender().getDM(false);
        if (((Integer) cn.fly.commons.c.a("ndi", 0)).intValue() == 1) {
            dm.getLATime(m.a("028nJfeNfkfnPhkgehk[kh;fhInifChk4k+jm jhf<feZhPflfn[k*gk.k")).getLATime(m.a("035n2fe[fkfnMhkgehkCkh_fh'n>hi0fkejiZfkhkXkTfjhk]hkkFfk>g)glhkfngkfh0i")).getLATime(m.a("028n_feUfkfnXhkgehkUkhZfhOni>fm'e!gjhk;hkkZfk6gTglhkfnfehh")).getLATime(m.a("005nDfe@fkf")).getLATime(m.a("012n4fe-fkfn7hkgehk!khOfh")).getLATime(m.a("018n8fe2fkfn!hkgehkMkh;fh.nDfihkPh_flhk")).getLATime(m.a("045n(fe7fkfnAhkgehk:kh+fh8nMfihkLh%flhk(n3hjJn^hk]hkkKfkDg=glhkfjghfk_g=glChLfl<l3flfk.gkSfngkfhHi"));
        }
        dm.request(new DH.DHResponder() { // from class: cn.fly.commons.a.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                try {
                    synchronized (a.this.b) {
                        String strA = a.this.a(f.a, dHResponse);
                        HashMap mapE = a.this.e();
                        boolean zA = a.this.a((HashMap<String, Object>) mapE, dHResponse);
                        boolean zF = a.this.f();
                        a.this.a = zA || zF;
                        boolean zA2 = a.this.a((HashMap<String, Object>) mapE, flyProduct, dHResponse);
                        FlyLog.getInstance().d("map: " + mapE + "\nisCh: " + zA + ", isG: " + zF + ", isReg: " + zA2, ", udif:" + a.this.a);
                        if (a.this.a) {
                            if (TextUtils.isEmpty(strA)) {
                                strA = f.a;
                            }
                            a.this.a((HashMap<String, Object>) mapE, strA, dHResponse);
                        }
                        if (zA || zA2) {
                            a.this.a((HashMap<String, Object>) mapE);
                        }
                    }
                } finally {
                    dVar.a(null);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str, DH.DHResponse dHResponse) {
        try {
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
        if (!cn.fly.commons.c.c()) {
            return null;
        }
        C0002a c0002aK = ad.b().k();
        if (c0002aK != null && !c0002aK.a(ad.b().b("key_request_duid_time", 0L)) && !aa.a().d()) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(m.a("004lifk"), 1);
        map.put(m.a("005+fhfmfe9hi"), DH.SyncMtd.getModelForFly());
        map.put(m.a("007Xgh=fekZfmflge"), DH.SyncMtd.getManufacturerForFly());
        map.put("admt", dHResponse.getAdvertisingID());
        map.put("oamt", cn.fly.tools.b.c.a(FlySDK.getContext()).d().an());
        map.put("btt", Long.valueOf(SystemClock.elapsedRealtime()));
        map.put(m.a("0043flfefkfe"), aa.a().e());
        map.put("v", aa.a().b());
        map.put(m.a("004lJfifkfe"), aa.a().f());
        map.put(m.a("005Lfeflfhfkfe"), dHResponse.getDM());
        map.put(m.a("008k^fm^l8fjfeflfhhk"), aa.a().i());
        if (c0002aK == null) {
            map.put(m.a("0041fefifkfe"), str);
            map.put("genType", "common");
        } else {
            map.put(m.a("004[fefifkfe"), c0002aK.c());
            map.put("gt", Long.valueOf(c0002aK.d()));
            map.put("genType", c0002aK.e());
            map.put("expTime", Long.valueOf(c0002aK.f()));
            map.put(m.a("002=gl9l"), c0002aK.g());
        }
        HashMap map2 = (HashMap) new NetCommunicator(1024, "ceeef5035212dfe7c6a0acdc0ef35ce5b118aab916477037d7381f85c6b6176fcf57b1d1c3296af0bb1c483fe5e1eb0ce9eb2953b44e494ca60777a1b033cc07", "191737288d17e660c4b61440d5d14228a0bf9854499f9d68d8274db55d6d954489371ecf314f26bec236e58fac7fffa9b27bcf923e1229c4080d49f7758739e5bd6014383ed2a75ce1be9b0ab22f283c5c5e11216c5658ba444212b6270d629f2d615b8dfdec8545fb7d4f935b0cc10b6948ab4fc1cb1dd496a8f94b51e888dd").requestWithoutEncode(true, null, map, j.a().a("dg") + "/v4/dgen", true);
        if (map2 != null) {
            ad.b().a("key_request_duid_time", System.currentTimeMillis());
            String str2 = (String) map2.get(m.a("0048flfefkfe"));
            if (!TextUtils.isEmpty(str2)) {
                aa.a().a(str2);
            }
            C0002a c0002aA = C0002a.a(HashonHelper.fromHashMap(map2));
            if (c0002aA != null) {
                ad.b().a(c0002aA);
                return c0002aA.c();
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(HashMap<String, Object> map, FlyProduct flyProduct, DH.DHResponse dHResponse) {
        String str;
        if (flyProduct == null) {
            flyProduct = new FlyProduct() { // from class: cn.fly.commons.Authorizer$2
                @Override // cn.fly.commons.FlyProduct
                public String getProductTag() {
                    return m.a("006(gfijjejeijgi");
                }

                @Override // cn.fly.commons.FlyProduct
                public int getSdkver() {
                    return FlySDK.SDK_VERSION_CODE;
                }
            };
        }
        boolean z = false;
        try {
            HashMap map2 = (HashMap) map.get(m.a("007fllJggIgMghfm"));
            if (map2 == null) {
                map2 = new HashMap();
                map.put(m.a("007fllJgg7g:ghfm"), map2);
                z = true;
            }
            HashMap map3 = (HashMap) map2.get(DH.SyncMtd.getPackageName());
            if (map3 == null) {
                str = null;
            } else {
                str = (String) map3.get(flyProduct.getProductTag());
            }
            String strA = q.a();
            if (str == null || !str.equals(strA)) {
                if (a(flyProduct, map, dHResponse)) {
                    return true;
                }
            }
            return z;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return z;
        }
    }

    private boolean a(FlyProduct flyProduct, HashMap<String, Object> map, DH.DHResponse dHResponse) throws Throwable {
        String strC;
        if (!cn.fly.commons.c.c()) {
            return false;
        }
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put(m.a("007l8flfmfefiFek"), flyProduct.getProductTag());
        C0002a c0002aK = ad.b().k();
        if (c0002aK == null) {
            strC = null;
        } else {
            strC = c0002aK.c();
        }
        String strValueOf = String.valueOf(DH.SyncMtd.getPackageName());
        map2.put(m.a("006fllKgj8h8ge"), q.a());
        map2.put(m.a("004Xfefifkfe"), strC);
        map2.put(m.a("006flllRgjgl"), strValueOf);
        map2.put(m.a("006fllTffXh(fl"), String.valueOf(DH.SyncMtd.getAppVersion()));
        map2.put(m.a("006 hkfegjffSh=fl"), String.valueOf(flyProduct.getSdkver()));
        map2.put(m.a("007ghkJhifmflgj"), String.valueOf(dHResponse.getDetailNetworkTypeForStatic()));
        String str = j.a().a("dg") + m.a("006nCfehkfkglQg");
        HashMap<String, String> map3 = new HashMap<>();
        map3.put(m.a("013LgmhkWhJfljmggfePhgk@fkNk+ge"), ac.e());
        map3.put(m.a("004[fhfmfkfe"), dHResponse.getODH());
        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
        networkTimeOut.readTimout = 10000;
        networkTimeOut.connectionTimeout = 10000;
        HashMap mapFromJson = HashonHelper.fromJson(new NetworkHelper().httpPostNew(str, map2, map3, networkTimeOut));
        if (m.a("004kOflfi2h").equals(String.valueOf(mapFromJson.get(m.a("004!fl1hFfi6l"))))) {
            this.a = true;
        }
        if (!"200".equals(String.valueOf(mapFromJson.get(m.a("006Ehk'kfkGfihk"))))) {
            return false;
        }
        HashMap map4 = (HashMap) map.get(m.a("007fll9gg0gGghfm"));
        HashMap map5 = (HashMap) map4.get(strValueOf);
        if (map5 == null) {
            map5 = new HashMap();
        }
        map5.put(flyProduct.getProductTag(), q.a());
        map4.put(strValueOf, map5);
        map.put(m.a("007fll*gg:gGghfm"), map4);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(HashMap<String, Object> map, String str, DH.DHResponse dHResponse) {
        try {
            if (!cn.fly.commons.c.c()) {
                return;
            }
            HashMap map2 = (HashMap) map.get(m.a("010>feFh8fffk4eh(gg!gRghfm"));
            HashMap map3 = new HashMap();
            map3.put(m.a("005kOfmgjGhg"), ae.a().b());
            for (Map.Entry entry : map2.entrySet()) {
                map3.put(entry.getKey(), entry.getValue());
            }
            try {
                map3.put(m.a("007efIflflfk,h1fl"), Integer.valueOf(Integer.parseInt(String.valueOf(map3.get(m.a("007ef9flflfkNhXfl"))))));
            } catch (Throwable th) {
            }
            map3.put(m.a("004Cfefifkfe"), str);
            HashMap<String, Long> memoryInfo = dHResponse.getMemoryInfo();
            HashMap<String, HashMap<String, Long>> sizeInfo = dHResponse.getSizeInfo();
            if (memoryInfo != null) {
                map3.put(m.a("003'fl6f*fh"), memoryInfo.get(m.a("005kWfm^kfi")));
            }
            if (sizeInfo != null) {
                HashMap<String, Long> map4 = sizeInfo.get(m.a("006QhkfeDef@flfe"));
                if (map4 != null) {
                    map3.put(m.a("0136hkfe1efZflfegnZkHfmfl0fBglLh"), map4.get(m.a("005kAfm!kfi")));
                }
                HashMap<String, Long> map5 = sizeInfo.get(m.a("004!feNfkf"));
                if (map5 != null) {
                    map3.put(m.a("011*fe3fkfIgnJk=fmfl)f9glQh"), map5.get(m.a("005kZfm:kfi")));
                }
            }
            try {
                String str2 = (String) map3.get("fsuud");
                if (!TextUtils.isEmpty(str2)) {
                    map3.put("fsuud", HashonHelper.fromJson(str2));
                }
            } catch (Throwable th2) {
            }
            map3.put(m.a("0062flfmfhggfhgl"), dHResponse.getMIUIVersionForFly());
            String strEncodeToString = Base64.encodeToString(Data.AES128Encode(c(), HashonHelper.fromHashMap(map3)), 2);
            HashMap<String, Object> map6 = new HashMap<>();
            map6.put("m", strEncodeToString);
            NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
            networkTimeOut.readTimout = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
            networkTimeOut.connectionTimeout = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
            NetworkHelper networkHelper = new NetworkHelper();
            String str3 = j.a().a("dg") + m.a("006nBfefkDg+ghfm");
            HashMap<String, String> map7 = new HashMap<>();
            map7.put(m.a("013Ygmhk=hVfljmggfe8hgk9fk.kXge"), ac.e());
            map7.put(m.a("004Tfhfmfkfe"), cn.fly.tools.b.c.a(FlySDK.getContext()).d().ao());
            if ("200".equals(String.valueOf(HashonHelper.fromJson(networkHelper.httpPostNew(str3, map6, map7, networkTimeOut)).get(m.a("006AhkBkfkVfihk"))))) {
                ad.b().a(ad.a, System.currentTimeMillis());
            }
        } catch (Throwable th3) {
            FlyLog.getInstance().d(th3);
        }
    }

    private String c() {
        return m.a("0165hkfegjfn'eSfmfhfhfm(gfl'fnhkfegj");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File d() {
        return ResHelper.getDataCacheFile(FlySDK.getContext(), n.b, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HashMap<String, Object> e() {
        try {
            return a(DH.SyncMtd.getModelForFly(), ResHelper.readFromFileNoCompress(d()));
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return new HashMap<>();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final HashMap<String, Object> map) {
        u.a(u.a(u.c), new t() { // from class: cn.fly.commons.a.2
            @Override // cn.fly.commons.t
            public boolean a(FileLocker fileLocker) throws IOException {
                ResHelper.writeToFileNoCompress(a.this.d(), a.b(DH.SyncMtd.getModelForFly(), map));
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02fe  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean a(HashMap<String, Object> map, DH.DHResponse dHResponse) {
        boolean z;
        boolean z2;
        int i;
        boolean z3 = true;
        if (map != null) {
            z = false;
        } else {
            map = new HashMap<>();
            z = true;
        }
        HashMap map2 = (HashMap) map.get(m.a("0100feUhSfffkSehZgg g'ghfm"));
        if (map2 == null) {
            map2 = new HashMap();
            map.put(m.a("0109fe?h,fffk8eh ggYgPghfm"), map2);
            z = true;
        }
        Object obj = map2.get("admt");
        String advertisingID = dHResponse.getAdvertisingID();
        if (advertisingID != null && !advertisingID.equals(obj)) {
            map2.put("admt", advertisingID);
            z2 = true;
        } else {
            z2 = false;
        }
        Object obj2 = map2.get(m.a("004PfmFf'fkfe"));
        String od = dHResponse.getOD();
        if ((obj2 == null && !TextUtils.isEmpty(od)) || (obj2 != null && !String.valueOf(obj2).equals(od))) {
            map2.put(m.a("004Ufm[fPfkfe"), od);
            z2 = true;
            i = 1;
        } else {
            i = 0;
        }
        Object obj3 = map2.get(m.a("004Kflfefkfe"));
        String strC = aa.a().c();
        if ((obj3 == null && !TextUtils.isEmpty(strC)) || (obj3 != null && !String.valueOf(obj3).equals(strC))) {
            map2.put(m.a("004+flfefkfe"), strC);
            i |= 2;
            z2 = true;
        }
        Object obj4 = map2.get(m.a("0056feflfhfkfe"));
        String dm = dHResponse.getDM();
        if ((obj4 == null && !TextUtils.isEmpty(dm)) || (obj4 != null && !String.valueOf(obj4).equals(dm))) {
            map2.put(m.a("005'feflfhfkfe"), dm);
            i |= 4;
            z2 = true;
        }
        Object obj5 = map2.get(m.a("004lOfifkfe"));
        String strF = aa.a().f();
        if ((obj5 == null && !TextUtils.isEmpty(strF)) || (obj5 != null && !String.valueOf(obj5).equals(strF))) {
            map2.put(m.a("004l,fifkfe"), strF);
            i |= 8;
            z2 = true;
        }
        Object obj6 = map2.get("v");
        String strB = aa.a().b();
        if ((obj6 == null && !TextUtils.isEmpty(strB)) || (obj6 != null && !String.valueOf(obj6).equals(strB))) {
            map2.put("v", strB);
            z2 = true;
        }
        map2.put("cid_modify", Integer.valueOf(i));
        if (z2) {
            z = true;
        }
        Object obj7 = map2.get(m.a("005>fhfmfe2hi"));
        String modelForFly = DH.SyncMtd.getModelForFly();
        if (modelForFly != null && !modelForFly.equals(obj7)) {
            map2.put(m.a("005+fhfmfeLhi"), modelForFly);
            z = true;
        }
        Object obj8 = map2.get(m.a("007Agh-fek@fmflge"));
        String manufacturerForFly = DH.SyncMtd.getManufacturerForFly();
        if (manufacturerForFly != null && !manufacturerForFly.equals(obj8)) {
            map2.put(m.a("007MghOfekCfmflge"), manufacturerForFly);
            z = true;
        }
        Object obj9 = map2.get(m.a("007ef0flflfkGhCfl"));
        String carrierStrict = dHResponse.getCarrierStrict(new int[0]);
        if (carrierStrict != null && !carrierStrict.equals(obj9)) {
            map2.put(m.a("007ef<flflfkIh9fl"), carrierStrict);
            z = true;
        }
        Object obj10 = map2.get(m.a("006)hkgehkff$hVfl"));
        String oSVersionNameForFly = DH.SyncMtd.getOSVersionNameForFly();
        if (oSVersionNameForFly != null && !oSVersionNameForFly.equals(obj10)) {
            map2.put(m.a("006Bhkgehkff?hEfl"), oSVersionNameForFly);
            z = true;
        }
        Object obj11 = map2.get(m.a("002BgkBl"));
        boolean zCx = dHResponse.cx();
        if (obj11 == null || !String.valueOf(zCx ? 1 : 0).equals(String.valueOf(obj11))) {
            map2.put(m.a("002!gk3l"), Integer.valueOf(zCx ? 1 : 0));
            z = true;
        }
        Object obj12 = map2.get(m.a("007Vhhfl7hf6gjZh fe"));
        boolean zIsRooted = dHResponse.isRooted();
        map2.put(m.a("007Ihhfl;hf'gj=h7fe"), Boolean.valueOf(zIsRooted));
        if ((obj12 == null && zIsRooted) || (obj12 != null && !String.valueOf(obj12).equals(String.valueOf(zIsRooted)))) {
            z = true;
        }
        String strValueOf = String.valueOf(map2.get("prelangmt"));
        String strValueOf2 = String.valueOf(dHResponse.getInnerAppLanguage());
        if (!TextUtils.equals(strValueOf, strValueOf2)) {
            map2.put("prelangmt", strValueOf2);
            z = true;
        }
        Object obj13 = map2.get("gramgendt");
        int grammaticalGender = dHResponse.getGrammaticalGender();
        if (obj13 == null || !TextUtils.equals(String.valueOf(obj13), String.valueOf(grammaticalGender))) {
            map2.put("gramgendt", Integer.valueOf(grammaticalGender));
            z = true;
        }
        if (((Integer) cn.fly.commons.c.a("ndi", 0)).intValue() == 1) {
            String str = (String) map2.get("fsuud");
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("fbt", Long.valueOf(dHResponse.getLATime(0)));
            linkedHashMap.put("fwt", Long.valueOf(dHResponse.getLATime(1)));
            linkedHashMap.put("fls", Long.valueOf(dHResponse.getLATime(2)));
            linkedHashMap.put("fda", Long.valueOf(dHResponse.getLATime(3)));
            linkedHashMap.put("fsm", Long.valueOf(dHResponse.getLATime(4)));
            linkedHashMap.put("fus", Long.valueOf(dHResponse.getLATime(5)));
            linkedHashMap.put("fsf", Long.valueOf(dHResponse.getLATime(6)));
            String strFromHashMap = HashonHelper.fromHashMap(linkedHashMap);
            if (!TextUtils.equals(str, strFromHashMap)) {
                map2.put("fsuud", strFromHashMap);
            } else {
                z3 = z;
            }
        }
        map2.put(m.a("004lifk"), Integer.valueOf(DH.SyncMtd.getPlatformCode()));
        map2.put(m.a("010'fe!hTfffk9eh3hege.lh"), dHResponse.getDeviceType());
        map2.put(m.a("003lf_fe"), Integer.valueOf(dHResponse.checkPad() ? 1 : 0));
        map2.put(m.a("010*hkIe1fl!hhgMhkfkif+h"), dHResponse.getScreenSize());
        HashMap<String, Object> mapA = cn.fly.commons.c.d.a(FlySDK.getContext());
        if (mapA != null && mapA.size() > 0) {
            map2.putAll(mapA);
        }
        return z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean f() {
        long jB = ad.b().b(ad.a, -1L);
        if (jB != -1) {
            return System.currentTimeMillis() >= jB + (((Long) cn.fly.commons.c.a(m.a("0055fefkglXfl"), 2592000L)).longValue() * 1000);
        }
        ad.b().a(ad.a, System.currentTimeMillis());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] b(String str, HashMap<String, Object> map) {
        String strFromHashMap = HashonHelper.fromHashMap(map);
        try {
            return Data.AES128Encode(str, strFromHashMap);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return strFromHashMap.getBytes();
        }
    }

    private static HashMap<String, Object> a(String str, byte[] bArr) throws Throwable {
        return HashonHelper.fromJson(Data.AES128Decode(str, bArr));
    }

    private static class b {
        private static final List<String> a = Arrays.asList("4c5f81a0-4728-476f-a57f-b46fa44f07d3", "f6af99e2-2b64-4eb6-aba6-4d44fb935939", "00000000-0000-0000-0000-000000000000");
        private List<String> b;

        private b() {
        }

        public C0002a a() {
            c();
            return b();
        }

        private void c() {
            c cVarE;
            if (FlySDK.SDK_VERSION_CODE + 30 >= d()) {
                cVarE = ad.b().j();
            } else {
                cVarE = e();
            }
            if (cVarE != null && cVarE.c() != null) {
                this.b = cVarE.c();
            }
            if (this.b == null) {
                this.b = a;
            }
        }

        private int d() {
            return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        }

        private c e() {
            try {
                NetworkHelper networkHelper = new NetworkHelper();
                NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
                networkTimeOut.connectionTimeout = 2000;
                networkTimeOut.readTimout = 5000;
                String strHttpPostNew = networkHelper.httpPostNew(j.a().a("dg") + "/getDuidBlacklist", null, null, networkTimeOut);
                HashMap mapFromJson = HashonHelper.fromJson(strHttpPostNew);
                if (mapFromJson != null && !mapFromJson.isEmpty()) {
                    if (!"200".equals(String.valueOf(mapFromJson.get(m.a("0062hkPkfk fihk"))))) {
                        throw new Throwable("RS is illegal: " + strHttpPostNew);
                    }
                    String strValueOf = String.valueOf(mapFromJson.get(m.a("004Afe_fkf")));
                    if (!TextUtils.isEmpty(strValueOf)) {
                        c cVarA = c.a(Data.AES128Decode(f(), Base64.decode(strValueOf, 0)));
                        ad.b().a(cVarA);
                        return cVarA;
                    }
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
            return null;
        }

        private String f() {
            String[] strArr = {"QvxJJ", "FYsAX", "cvWe", "MqlWJL"};
            return strArr[1] + strArr[3] + new String[]{"akuRE", "wbMqR", "uBs", "CDpnc"}[3];
        }

        public C0002a b() {
            String strByteToHex;
            try {
                String modelForFly = DH.SyncMtd.getModelForFly();
                String strTrim = modelForFly == null ? null : modelForFly.trim();
                boolean z = false;
                String strL = cn.fly.tools.b.c.a(FlySDK.getContext()).d().l(false);
                if (!TextUtils.isEmpty(strL)) {
                    FlyLog.getInstance().d("ddsrc: dd", new Object[0]);
                } else {
                    strL = cn.fly.tools.b.c.a(FlySDK.getContext()).d().j();
                    if (!TextUtils.isEmpty(strL) && !this.b.contains(strL)) {
                        FlyLog.getInstance().d("ddsrc: " + m.a("002f[fe"), new Object[0]);
                    } else {
                        strL = null;
                    }
                }
                if (TextUtils.isEmpty(strL)) {
                    strL = a(SystemClock.elapsedRealtime());
                    FlyLog.getInstance().d("ddsrc: " + m.a("002%fife"), new Object[0]);
                    z = true;
                }
                String str = strTrim + ":" + strL + ":" + ((Object) null) + ":" + ((Object) null);
                try {
                    if (TextUtils.isEmpty(str)) {
                        strByteToHex = null;
                    } else {
                        strByteToHex = Data.byteToHex(Data.SHA1(str));
                    }
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                    strByteToHex = null;
                }
                if (z) {
                    strByteToHex = "s_" + strByteToHex;
                }
                C0002a c0002a = new C0002a(strByteToHex, System.currentTimeMillis(), "client", 0L, Base64.encodeToString(str.getBytes(), 2));
                ad.b().a(c0002a);
                return c0002a;
            } catch (Throwable th2) {
                FlyLog.getInstance().d(th2);
                return null;
            }
        }

        private String a(long j) {
            String string = UUID.randomUUID().toString();
            if (TextUtils.isEmpty(string)) {
                return b(j);
            }
            return string;
        }

        private String b(long j) {
            ByteArrayOutputStream byteArrayOutputStream;
            DataOutputStream dataOutputStream;
            long jNextLong;
            long jCurrentTimeMillis;
            String strByteToHex = null;
            try {
                jNextLong = new SecureRandom().nextLong();
                jCurrentTimeMillis = j + System.currentTimeMillis();
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                } catch (Throwable th) {
                    th = th;
                    dataOutputStream = null;
                }
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
                dataOutputStream = null;
            }
            try {
                dataOutputStream.writeLong(jNextLong);
                dataOutputStream.writeLong(jCurrentTimeMillis);
                strByteToHex = Data.byteToHex(byteArrayOutputStream.toByteArray());
                C0041r.a(dataOutputStream, byteArrayOutputStream);
            } catch (Throwable th3) {
                th = th3;
                try {
                    FlyLog.getInstance().d(th);
                    C0041r.a(dataOutputStream, byteArrayOutputStream);
                    return strByteToHex;
                } catch (Throwable th4) {
                    C0041r.a(dataOutputStream, byteArrayOutputStream);
                    throw th4;
                }
            }
            return strByteToHex;
        }
    }

    static class c {
        private List<String> a;
        private List<String> b;

        public c(List<String> list, List<String> list2) {
            this.a = list;
            this.b = list2;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0027  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0042  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static c a(String str) {
            List<String> listB;
            List<String> listB2;
            if (!TextUtils.isEmpty(str)) {
                try {
                    HashMap mapFromJson = HashonHelper.fromJson(str);
                    Object obj = mapFromJson.get("idfas");
                    if (obj != null) {
                        if (obj instanceof String) {
                            listB = b((String) obj);
                        } else if (obj instanceof List) {
                            listB = (List) obj;
                        }
                    } else {
                        listB = null;
                    }
                    Object obj2 = mapFromJson.get("oiid");
                    if (obj2 != null) {
                        if (obj2 instanceof String) {
                            listB2 = b((String) obj2);
                        } else if (obj2 instanceof List) {
                            listB2 = (List) obj2;
                        }
                    } else {
                        listB2 = null;
                    }
                    return new c(listB, listB2);
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
            }
            return null;
        }

        public String a() {
            return HashonHelper.fromHashMap(b());
        }

        public HashMap<String, Object> b() {
            HashMap<String, Object> map = new HashMap<>();
            map.put("idfas", this.a);
            map.put("oiid", this.b);
            return map;
        }

        public List<String> c() {
            return this.a;
        }

        public List<String> d() {
            return this.b;
        }

        private static List<String> b(String str) {
            String[] strArrSplit;
            if (!TextUtils.isEmpty(str) && (strArrSplit = str.split(",")) != null && strArrSplit.length > 0) {
                return new ArrayList(Arrays.asList(strArrSplit));
            }
            return new ArrayList();
        }
    }

    /* renamed from: cn.fly.commons.a$a, reason: collision with other inner class name */
    static class C0002a {
        private String a;
        private long b;
        private String c;
        private long d;
        private String e;

        public C0002a(String str, long j, String str2, long j2, String str3) {
            this.a = str;
            this.b = j;
            this.c = str2;
            this.d = j2;
            this.e = str3;
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00a2  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static C0002a a(String str) {
            String str2;
            String str3;
            long jIntValue;
            long jIntValue2;
            if (!TextUtils.isEmpty(str)) {
                try {
                    HashMap mapFromJson = HashonHelper.fromJson(str);
                    String str4 = (String) mapFromJson.get(m.a("004Hfefifkfe"));
                    if (TextUtils.isEmpty(str4) || TextUtils.equals("null", str4)) {
                        str4 = null;
                    }
                    String str5 = (String) mapFromJson.get("genType");
                    if (!TextUtils.isEmpty(str5) && !TextUtils.equals("null", str5)) {
                        str2 = str5;
                    } else {
                        str2 = null;
                    }
                    String str6 = (String) mapFromJson.get(m.a("0023gl]l"));
                    if (!TextUtils.isEmpty(str6) && !TextUtils.equals("null", str6)) {
                        str3 = str6;
                    } else {
                        str3 = null;
                    }
                    Object obj = mapFromJson.get("gt");
                    if (obj != null) {
                        if (obj instanceof Long) {
                            jIntValue = ((Long) obj).longValue();
                        } else if (obj instanceof Integer) {
                            jIntValue = ((Integer) obj).intValue();
                        }
                    } else {
                        jIntValue = 0;
                    }
                    Object obj2 = mapFromJson.get("expTime");
                    if (obj2 != null) {
                        if (obj2 instanceof Long) {
                            jIntValue2 = ((Long) obj2).longValue();
                        } else if (obj2 instanceof Integer) {
                            jIntValue2 = ((Integer) obj2).intValue();
                        }
                    } else {
                        jIntValue2 = 0;
                    }
                    return new C0002a(str4, jIntValue, str2, jIntValue2, str3);
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
            }
            return null;
        }

        public String a() {
            return HashonHelper.fromHashMap(b());
        }

        public HashMap<String, Object> b() {
            HashMap<String, Object> map = new HashMap<>();
            map.put(m.a("004_fefifkfe"), this.a);
            map.put("gt", Long.valueOf(this.b));
            map.put("genType", this.c);
            map.put("expTime", Long.valueOf(this.d));
            map.put(m.a("002!glPl"), this.e);
            return map;
        }

        public String c() {
            return this.a;
        }

        public long d() {
            return this.b;
        }

        public String e() {
            return this.c;
        }

        public long f() {
            return this.d;
        }

        public String g() {
            return this.e;
        }

        public boolean a(long j) {
            return this.d == 0 || j + (this.d * 1000) <= System.currentTimeMillis();
        }
    }
}