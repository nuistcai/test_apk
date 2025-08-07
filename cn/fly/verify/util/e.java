package cn.fly.verify.util;

import android.text.TextUtils;
import android.util.Log;
import cn.fly.FlySDK;
import cn.fly.commons.CSCenter;
import cn.fly.tools.utils.DH;
import cn.fly.verify.u;
import cn.fly.verify.util.k;
import cn.fly.verify.v;

/* loaded from: classes.dex */
public class e {
    private static String a;
    private static String b;
    private static String c;
    private static String d;
    private static String e;
    private static String f;
    private static String g;

    public interface a<T> {
        void onResult(T t, Throwable th);
    }

    public static String a() {
        if (TextUtils.isEmpty(a)) {
            a = l.l();
        }
        return a;
    }

    public static void a(final a<Boolean> aVar) {
        if (aVar != null) {
            try {
                v.a("DH request");
                DH.requester(FlySDK.getContext()).getNetworkTypeForce(true).request(new DH.DHResponder() { // from class: cn.fly.verify.util.e.3
                    @Override // cn.fly.tools.utils.DH.DHResponder
                    public void onResponse(DH.DHResponse dHResponse) {
                        try {
                            v.a("DH response");
                            String unused = e.f = dHResponse.getNetworkTypeForce(new int[0]);
                            aVar.onResult(Boolean.valueOf("2g".equalsIgnoreCase(e.f) || "3g".equalsIgnoreCase(e.f) || "4g".equalsIgnoreCase(e.f) || "5g".equalsIgnoreCase(e.f) || "wifi".equalsIgnoreCase(e.f)), null);
                        } catch (Throwable th) {
                            v.a(th);
                        }
                    }
                });
            } catch (Throwable th) {
                if ((th instanceof ClassNotFoundException) || (th instanceof NoClassDefFoundError) || (th instanceof NoSuchMethodException) || (th instanceof NoSuchMethodError)) {
                    Log.e("[FlyVerify] ==>%s", "本产品进行了架构升级优化，为保证正常使用SDK，请确保相关架包升级到了最新版本，或者可至官网联系技术支持");
                }
                v.a(th);
                aVar.onResult(false, th);
            }
        }
    }

    public static void a(final k.a aVar, final u uVar) {
        try {
            DH.RequestBuilder networkTypeForce = (!TextUtils.isEmpty(a) ? DH.requester(FlySDK.getContext()).getCarrierForce(true) : DH.requester(FlySDK.getContext()).getSignMD5().getCarrierForce(true)).getNetworkTypeForce(true);
            v.a("DH request");
            networkTypeForce.request(new DH.DHResponder() { // from class: cn.fly.verify.util.e.1
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    try {
                        v.a("DH response");
                        if (uVar != null) {
                            uVar.a((String) null, (String) null, "dh");
                        }
                        if (TextUtils.isEmpty(e.a)) {
                            String unused = e.a = dHResponse.getSignMD5();
                        }
                        String unused2 = e.b = dHResponse.getCarrierForce(new int[0]);
                        String unused3 = e.f = dHResponse.getNetworkTypeForce(new int[0]);
                        if (aVar != null) {
                            aVar.run();
                        }
                    } catch (Throwable th) {
                        v.a(th);
                    }
                }
            });
        } catch (Throwable th) {
            if ((th instanceof ClassNotFoundException) || (th instanceof NoClassDefFoundError) || (th instanceof NoSuchMethodException) || (th instanceof NoSuchMethodError)) {
                Log.e("[FlyVerify] ==>%s", "本产品进行了架构升级优化，为保证正常使用SDK，请确保相关架包升级到了最新版本，或者可至官网联系技术支持");
            }
            v.a(th);
            if (aVar != null) {
                aVar.a(th);
            }
        }
        try {
            DH.requester(FlySDK.getContext()).getIMSI().getDeviceKey().getSimSerialNumber().getOD().request(new DH.DHResponder() { // from class: cn.fly.verify.util.e.2
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    try {
                        String unused = e.c = dHResponse.getIMSI();
                        String unused2 = e.d = dHResponse.getDeviceKey();
                        String unused3 = e.e = dHResponse.getSimSerialNumber();
                        String unused4 = e.g = dHResponse.getOD();
                    } catch (Throwable th2) {
                        v.a(th2);
                    }
                }
            });
        } catch (Throwable th2) {
            v.a(th2);
        }
    }

    public static String b() {
        if (TextUtils.isEmpty(b) || "-1".equals(b)) {
            b = l.k();
        }
        return b;
    }

    public static void b(final a<String> aVar) {
        try {
            DH.requester(FlySDK.getContext()).getNetworkTypeForce(true).request(new DH.DHResponder() { // from class: cn.fly.verify.util.e.4
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    try {
                        String unused = e.f = dHResponse.getNetworkTypeForce(new int[0]);
                        if (aVar != null) {
                            aVar.onResult(e.f, null);
                        }
                    } catch (Throwable th) {
                        v.a(th);
                    }
                }
            });
        } catch (Throwable th) {
            v.a(th);
            aVar.onResult("none", th);
        }
    }

    public static String c() {
        return c;
    }

    public static String d() {
        return d;
    }

    public static String e() throws InterruptedException {
        try {
            if (!CSCenter.getInstance().isSystemInfoAvailable()) {
                return CSCenter.getInstance().getROMVersion();
            }
        } catch (Throwable th) {
        }
        final DH.DHResponse[] dHResponseArr = {null};
        DH.requester(FlySDK.getContext()).getMIUIVersion().request(new DH.DHResponder() { // from class: cn.fly.verify.util.e.5
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) throws Throwable {
                dHResponseArr[0] = dHResponse;
            }
        });
        for (long j = 300; j > 0; j -= 30) {
            if (dHResponseArr[0] != null) {
                return dHResponseArr[0].getMIUIVersion();
            }
            try {
                Thread.sleep(30L);
            } catch (InterruptedException e2) {
            }
        }
        return null;
    }

    public static String f() {
        return e;
    }

    public static String g() {
        return f;
    }

    public static String h() {
        return g;
    }

    public static String i() {
        try {
            return DH.SyncMtd.getManufacturerForFly();
        } catch (Throwable th) {
            return DH.SyncMtd.getManufacturer();
        }
    }

    public static String j() {
        try {
            return DH.SyncMtd.getBrandForFly();
        } catch (Throwable th) {
            return DH.SyncMtd.getBrand();
        }
    }

    public static String k() {
        try {
            return DH.SyncMtd.getModelForFly();
        } catch (Throwable th) {
            return DH.SyncMtd.getModel();
        }
    }

    public static String l() {
        try {
            return DH.SyncMtd.getOSVersionNameForFly();
        } catch (Throwable th) {
            return DH.SyncMtd.getOSVersionName();
        }
    }

    public static int m() {
        try {
            return DH.SyncMtd.getOSVersionIntForFly();
        } catch (Throwable th) {
            return DH.SyncMtd.getOSVersionInt();
        }
    }
}