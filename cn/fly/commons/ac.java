package cn.fly.commons;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import com.mob.commons.MOBLINK;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class ac {
    private static Callable<Map<String, Object>> b;
    private static int c = 0;
    public static final String[] a = {m.a("0089gnhmhfilikgnhnke"), m.a("0061gnjegngnhnke"), m.a("007Ajeijhlhggggike"), m.a("007Ljeijhlingmgnhm"), m.a("009,gnikgfimikilggiekn"), "FLYVERIFY"};
    private static AtomicBoolean d = new AtomicBoolean(false);
    private static AtomicBoolean e = new AtomicBoolean(false);
    private static final HashMap<String, FlyProduct> f = new HashMap<>();

    public static void a() {
        if (z.b()) {
            j();
            ab.a.execute(new cn.fly.tools.utils.i() { // from class: cn.fly.commons.ac.1
                @Override // cn.fly.tools.utils.i
                protected void a() {
                    FlyLog.getInstance().d("init sks start", new Object[0]);
                    ac.b();
                    FlyLog.getInstance().d("init sks over", new Object[0]);
                }
            });
        }
    }

    private static void j() {
        if (z.h() && e.compareAndSet(false, true)) {
            try {
                MOBLINK moblink = new MOBLINK();
                if (moblink instanceof FlyProduct) {
                    moblink.getProductTag();
                }
            } catch (Throwable th) {
            }
        }
    }

    public static void a(FlyProduct flyProduct) {
        synchronized (f) {
            if (flyProduct != null) {
                if (!f.containsKey(flyProduct.getProductTag())) {
                    f.put(flyProduct.getProductTag(), flyProduct);
                }
            }
        }
    }

    public static ArrayList<FlyProduct> b() {
        ArrayList<FlyProduct> arrayList;
        synchronized (f) {
            if (z.b() && z.h() && d.compareAndSet(false, true)) {
                f.putAll(k());
            }
            arrayList = new ArrayList<>();
            arrayList.addAll(f.values());
        }
        return arrayList;
    }

    private static HashMap<String, FlyProduct> k() {
        Class<?> cls;
        HashMap<String, FlyProduct> map = new HashMap<>();
        for (Object obj : p.a) {
            try {
                if (obj instanceof String) {
                    cls = Class.forName(String.valueOf(obj).trim());
                } else {
                    cls = (Class) obj;
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d("[INI]ini sks [" + obj + "] f", new Object[0]);
            }
            if (FlyProduct.class.isAssignableFrom(cls) && !FlyProduct.class.equals(cls)) {
                FlyProduct flyProduct = (FlyProduct) cls.newInstance();
                String productTag = flyProduct.getProductTag();
                FlyLog.getInstance().d("[INI]ini sks [" + productTag + "] s", new Object[0]);
                String[] strArr = a;
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    String str = strArr[i];
                    if (!str.equals(productTag)) {
                        i++;
                    } else {
                        map.put(str, flyProduct);
                        break;
                    }
                }
                if (m.a("007[jeijhlingmgnhm").equals(productTag)) {
                    if (flyProduct instanceof Callable) {
                        c = 2;
                        b = (Callable) flyProduct;
                    } else {
                        c = 1;
                    }
                }
            } else {
                cls.newInstance();
            }
        }
        return map;
    }

    public static Callable<Map<String, Object>> c() {
        return b;
    }

    public static int d() {
        return c;
    }

    public static synchronized String e() {
        return a(b(), 0);
    }

    public static synchronized String f() {
        return a(b(), 1);
    }

    public static synchronized String g() {
        return a(b(), 2);
    }

    public static synchronized String h() {
        return a(b(), 3);
    }

    public static synchronized String i() {
        return a(b(), 4);
    }

    private static synchronized String a(final ArrayList<FlyProduct> arrayList, final int i) {
        final String[] strArr;
        strArr = new String[]{""};
        DH.RequestBuilder carrierStrict = DH.requester(FlySDK.getContext()).getMIUIVersionForFly().getDetailNetworkTypeForStatic().getCarrierStrict(false);
        if (z.b() && i != 3 && i != 4) {
            carrierStrict.getDeviceKey();
        } else {
            carrierStrict.getDeviceKeyFromCache(true);
        }
        carrierStrict.request(new DH.DHResponder() { // from class: cn.fly.commons.ac.2
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) throws Throwable {
                String deviceKeyFromCache;
                String str;
                String str2;
                String strA;
                String str3;
                String str4 = "";
                String strEncode = TextUtils.isEmpty(DH.SyncMtd.getPackageName()) ? "" : URLEncoder.encode(DH.SyncMtd.getPackageName(), "utf-8");
                String strEncode2 = TextUtils.isEmpty(DH.SyncMtd.getAppVersionName()) ? "" : URLEncoder.encode(DH.SyncMtd.getAppVersionName(), "utf-8");
                String strEncode3 = TextUtils.isEmpty(DH.SyncMtd.getManufacturerForFly()) ? "" : URLEncoder.encode(DH.SyncMtd.getManufacturerForFly(), "utf-8");
                String strEncode4 = TextUtils.isEmpty(DH.SyncMtd.getModelForFly()) ? "" : URLEncoder.encode(DH.SyncMtd.getModelForFly(), "utf-8");
                String strEncode5 = TextUtils.isEmpty(dHResponse.getMIUIVersionForFly()) ? "" : URLEncoder.encode(dHResponse.getMIUIVersionForFly(), "utf-8");
                String strEncode6 = TextUtils.isEmpty(DH.SyncMtd.getOSVersionNameForFly()) ? "" : URLEncoder.encode(DH.SyncMtd.getOSVersionNameForFly(), "utf-8");
                HashMap<String, Object> mapB = o.a().b();
                String str5 = m.a("004Whfinin!n") + strEncode + ";" + strEncode2;
                String str6 = m.a("0129gnkngn;nShf7gCfeflfmfkfeli") + DH.SyncMtd.getOSVersionIntForFly() + ";" + strEncode6;
                if (z.b() && i != 3 && i != 4) {
                    deviceKeyFromCache = dHResponse.getDeviceKey();
                } else {
                    deviceKeyFromCache = dHResponse.getDeviceKeyFromCache(new int[0]);
                }
                String str7 = m.a("004Kgnhngg2n") + deviceKeyFromCache;
                String str8 = m.a("003]iejeZn") + strEncode3 + ";" + strEncode4;
                if (!TextUtils.isEmpty(strEncode5)) {
                    str8 = str8 + ";" + strEncode5;
                }
                String str9 = m.a("0035giik0n") + dHResponse.getDetailNetworkTypeForStatic() + ";" + dHResponse.getCarrierStrict(new int[0]);
                String str10 = m.a("0057hg8fgGgl:n") + Locale.getDefault().toString().replace(m.a("002*jmfl"), "-");
                String str11 = m.a("004%gfhgimLn") + FlySDK.SDK_VERSION_CODE;
                String strA2 = m.a("004$gnhnke3n");
                if (arrayList.isEmpty()) {
                    str = "";
                } else {
                    int size = arrayList.size();
                    String str12 = strA2;
                    int i2 = 0;
                    while (i2 < size) {
                        try {
                            FlyProduct flyProduct = (FlyProduct) arrayList.get(i2);
                            if (i2 == 0) {
                                str3 = str4;
                            } else {
                                str3 = str4;
                                try {
                                    str12 = str12 + ",";
                                } catch (Throwable th) {
                                }
                            }
                            try {
                                try {
                                    str12 = str12 + flyProduct.getProductTag() + ";" + flyProduct.getSdkver() + ";" + mapB.get(flyProduct.getProductTag());
                                } catch (Throwable th2) {
                                    str12 = str12;
                                }
                            } catch (Throwable th3) {
                            }
                        } catch (Throwable th4) {
                            str3 = str4;
                        }
                        i2++;
                        str4 = str3;
                    }
                    str = str4;
                    strA2 = str12;
                }
                String str13 = "DC/" + ac.a(i);
                String timezone = DH.SyncMtd.getTimezone();
                if (TextUtils.isEmpty(timezone)) {
                    str2 = str;
                } else {
                    str2 = m.a("003=heklfj") + timezone;
                }
                String strC = ae.a().c();
                String str14 = TextUtils.isEmpty(strC) ? "TID/" : "TID/" + strC;
                int iA = cn.fly.commons.cc.a.a();
                String str15 = "SVM/" + iA;
                if (cn.fly.tools.b.d.c()) {
                    if (!m.a("004Ognhnke[n").equals(strA2)) {
                        strA2 = strA2 + ",";
                    }
                    strA2 = strA2 + "CS;" + iA;
                }
                if (i == 3) {
                    strA = aa.a().a(true);
                } else {
                    strA = aa.a().a(false);
                }
                strArr[0] = str5 + " " + str6 + " " + str7 + " " + str8 + " " + str9 + " " + str10 + " " + str11 + " " + strA2 + " " + str13 + " " + str2 + " " + str14 + " " + str15 + " " + (TextUtils.isEmpty(strA) ? "RD/" : "RD/" + strA);
            }
        });
        return strArr[0];
    }

    public static String a(int i) {
        String str;
        if (!CSCenter.getInstance().isCusControllerNotNull()) {
            str = "11";
        } else {
            str = "13";
        }
        if (i == 1) {
            return "[DC]";
        }
        if (i == 2) {
            return "[DC2]";
        }
        if (i == 4) {
            return "15";
        }
        return str;
    }
}