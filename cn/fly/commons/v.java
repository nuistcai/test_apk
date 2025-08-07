package cn.fly.commons;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.a;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.FileLocker;
import cn.fly.tools.utils.FlyRSA;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ReflectHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes.dex */
public class v {
    public static volatile boolean a = false;
    private static v b;
    private File c;
    private final AtomicBoolean f = new AtomicBoolean(false);
    private BigInteger d = new BigInteger("f53c224aefb38daa0825c1b8ea691b16d2e16db10880548afddd780c6670a091a11dafa954ea4a9483797fda1045d2693a08daa48cf9cedce1e8733b857304cb", 16);
    private BigInteger e = new BigInteger("27749621e6ca022469645faed16e8261acf6af822467382d55c24bb9bc02356ab16e76ddc799dc8ba6b4f110411996eeb63505c9dcf969d3fc085d712f0f1a9713b67aa1128d7cc41bda363afb0ec7ade60e542a4e22869395331cc0096de412034551e98bb2629ae1b7168b8bc82006d064ab335d8567283e70beb6a49e9423", 16);

    private v() {
    }

    public static synchronized v a() {
        if (b == null) {
            b = new v();
        }
        return b;
    }

    public void b() {
        if (this.f.compareAndSet(false, true)) {
            FlyLog.getInstance().d("[LGSM] Sd last", new Object[0]);
            ab.a.execute(new c());
        }
    }

    public void a(int i, String str, int i2, String str2) {
        FlyLog.getInstance().d("[LGSM] Sd curr", new Object[0]);
        if (i == 1) {
            new a().a(i2, i, str, str2).run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(final Runnable runnable) {
        if (this.c == null) {
            this.c = new File(FlySDK.getContext().getFilesDir(), n.a("005(bj2e*biFa!cf"));
            if (!this.c.exists()) {
                try {
                    this.c.createNewFile();
                } catch (Throwable th) {
                }
            }
        }
        return u.a(this.c, new t() { // from class: cn.fly.commons.v.1
            @Override // cn.fly.commons.t
            public boolean a(FileLocker fileLocker) {
                runnable.run();
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static cn.fly.tools.a b(int i) {
        return new cn.fly.tools.a(n.a("005-bjbd=aeYch"), n.a("005-bjbd=aeYch") + "-" + i, 50);
    }

    private static class c implements Runnable {
        private Runnable a;

        private c() {
            this.a = new cn.fly.tools.utils.i() { // from class: cn.fly.commons.v.c.1
                @Override // cn.fly.tools.utils.i
                protected void a() {
                    FlyLog.getInstance().d("[LGSM] UCLR", new Object[0]);
                    v.b(1).a(new b());
                }
            };
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (!cn.fly.commons.c.c()) {
                    FlyLog.getInstance().d("[LGSM] ULR Ck nt: FBDN", new Object[0]);
                } else {
                    DH.requester(FlySDK.getContext()).getDetailNetworkTypeForStatic().request(new DH.DHResponder() { // from class: cn.fly.commons.v.c.2
                        @Override // cn.fly.tools.utils.DH.DHResponder
                        public void onResponse(DH.DHResponse dHResponse) {
                            if (n.a("004c[bi8cd").equals(dHResponse.getDetailNetworkTypeForStatic())) {
                                return;
                            }
                            int iIntValue = ((Integer) cn.fly.commons.c.a(n.a("004ad:bhbh"), 0)).intValue();
                            FlyLog.getInstance().d("[LGSM] ULR Ck cerr: " + iIntValue, new Object[0]);
                            if (iIntValue == 1) {
                                v.a().a(c.this.a);
                            } else {
                                v.b(1).a(((Long) cn.fly.commons.c.a("cerr_max", 104857600L)).longValue());
                            }
                        }
                    });
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public int a(int i, String str) {
        if (FlySDK.getContextSafely() != null && a) {
            Intent intent = new Intent();
            intent.setPackage(n.a("015acGbjdg)fb^bh@dMdgbacfbj_e:bich"));
            intent.putExtra(n.a("007hbaVcfSb-ch$d"), FlySDK.getContext().getPackageName());
            intent.putExtra(n.a("008h6bhbgbibhbg[gGca"), i);
            intent.putExtra("ver", FlySDK.SDK_VERSION_CODE);
            intent.putExtra(n.a("0035bddgch"), a(str));
            ReflectHelper.invokeInstanceMethod(FlySDK.getContextSafely(), n.a("013_dgBdc'badhbhbiOb=baXabQdgSg"), new Object[]{intent}, new Class[]{Intent.class}, 0);
        }
        return 0;
    }

    private String a(String str) {
        DataOutputStream dataOutputStream;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] bArrC = C0041r.c();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            } catch (Throwable th) {
                th = th;
                dataOutputStream = null;
            }
            try {
                byte[] bArrEncode = new FlyRSA(1024).encode(bArrC, this.d, this.e);
                dataOutputStream.writeInt(bArrEncode.length);
                dataOutputStream.write(bArrEncode);
                byte[] bArrAES128Encode = Data.AES128Encode(bArrC, str.getBytes("utf-8"));
                dataOutputStream.writeInt(bArrAES128Encode.length);
                dataOutputStream.write(bArrAES128Encode);
                dataOutputStream.flush();
                C0041r.a(dataOutputStream, byteArrayOutputStream);
                return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
            } catch (Throwable th2) {
                th = th2;
                C0041r.a(dataOutputStream, byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th3) {
            FlyLog.getInstance().d(th3);
            return null;
        }
    }

    private static class b implements a.InterfaceC0019a {
        ArrayList<HashMap<String, Object>> a;
        int b;
        String c;

        private b() {
            this.a = new ArrayList<>();
            this.b = -1;
        }

        @Override // cn.fly.tools.a.InterfaceC0019a
        public void a(String str) {
            FlyLog.getInstance().d("[LGSM] ULL onRd " + str, new Object[0]);
            HashMap<String, Object> mapFromJson = HashonHelper.fromJson(str);
            try {
                this.b = Integer.parseInt(String.valueOf(mapFromJson.get(n.a("010[dgbacfei4d>bhdgbgbi1c"))));
            } catch (Throwable th) {
            }
            this.c = (String) mapFromJson.get(n.a("006EdgbacfdaRbUch"));
            this.a.add(mapFromJson);
        }

        @Override // cn.fly.tools.a.InterfaceC0019a
        public boolean a(DH.DHResponse dHResponse) {
            int iIntValue;
            FlyLog.getInstance().d("[LGSM] ULL onUd", new Object[0]);
            HashMap<String, Object> mapA = a(dHResponse, this.b, this.c);
            mapA.put(n.a("006d.bhbhbddgch"), this.a);
            try {
                String strB = b(HashonHelper.fromHashMap(mapA));
                this.a.clear();
                HashMap<String, Object> map = new HashMap<>();
                map.put("m", strB);
                NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
                networkTimeOut.readTimout = 10000;
                networkTimeOut.connectionTimeout = 10000;
                HashMap<String, String> map2 = new HashMap<>();
                map2.put(n.a("013JcidgAdWbhficcbaAdcg=bgKg[ca"), ac.e());
                map2.put(n.a("004Mbdbibgba"), dHResponse.getODH());
                String str = j.a().a("el") + "/errlog";
                FlyLog.getInstance().d("[LGSM] ULL onUd: Req", new Object[0]);
                String strHttpPostNew = new NetworkHelper().httpPostNew(str, map, map2, networkTimeOut);
                FlyLog.getInstance().d("[LGSM] ULL onUd: " + String.format("Resp(%s): %s", str, strHttpPostNew), new Object[0]);
                Object obj = HashonHelper.fromJson(strHttpPostNew).get(n.a("006Udg7gbgMbedg"));
                if (obj == null) {
                    iIntValue = 0;
                } else {
                    iIntValue = ((Integer) obj).intValue();
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d("[LGSM] ULL onUd: E", new Object[0]);
                FlyLog.getInstance().d(th);
            }
            return iIntValue == 200;
        }

        private HashMap<String, Object> a(DH.DHResponse dHResponse, int i, String str) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(n.a("003-cfEd'ca"), q.a());
            map.put(n.a("0044babebgba"), f.a((FlyProduct) null));
            map.put(n.a("004hebg"), Integer.valueOf(DH.SyncMtd.getPlatformCode()));
            map.put(n.a("003*dgbacf"), str);
            map.put(n.a("006PdgbacfbbXd,bh"), Integer.valueOf(i));
            map.put(n.a("007bhhcb-bdSd"), dHResponse.getAppName());
            map.put(n.a("006bhhh^cfch"), DH.SyncMtd.getPackageName());
            map.put(n.a("006bhh5bbDd7bh"), String.valueOf(DH.SyncMtd.getAppVersion()));
            map.put(n.a("005?bdbibaSde"), DH.SyncMtd.getModelForFly());
            if (cn.fly.commons.c.b()) {
                map.put(n.a("008Vba_dVbbbg^adFbgba"), dHResponse.getDeviceKey());
            }
            map.put(n.a("006Fdgcadgbb^d.bh"), String.valueOf(DH.SyncMtd.getOSVersionIntForFly()));
            map.put(n.a("011cdgIdebibhcfOg,caLhd"), dHResponse.getDetailNetworkTypeForStatic());
            return map;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private String b(String str) throws Throwable {
            ByteArrayInputStream byteArrayInputStream;
            Throwable th;
            byte[] bytes;
            GZIPOutputStream gZIPOutputStream;
            Throwable th2;
            try {
                bytes = str.getBytes();
                byteArrayInputStream = new ByteArrayInputStream(bytes);
            } catch (Throwable th3) {
                byteArrayInputStream = null;
                th = th3;
                bytes = null;
            }
            try {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    } catch (Throwable th4) {
                        gZIPOutputStream = null;
                        th2 = th4;
                    }
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = byteArrayInputStream.read(bArr, 0, 1024);
                            if (i != -1) {
                                gZIPOutputStream.write(bArr, 0, i);
                            } else {
                                gZIPOutputStream.flush();
                                C0041r.a(gZIPOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                byteArrayOutputStream.flush();
                                String strEncodeToString = Base64.encodeToString(byteArray, 2);
                                C0041r.a(byteArrayOutputStream, byteArrayInputStream);
                                return strEncodeToString;
                            }
                        }
                    } catch (Throwable th5) {
                        th2 = th5;
                        C0041r.a(gZIPOutputStream);
                        throw th2;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    bytes = null;
                    C0041r.a(bytes, byteArrayInputStream);
                    throw th;
                }
            } catch (Throwable th7) {
                th = th7;
                C0041r.a(bytes, byteArrayInputStream);
                throw th;
            }
        }
    }

    private static class a implements Runnable {
        private int a;
        private int b;
        private String c;
        private String d;

        private a() {
        }

        public a a(int i, int i2, String str, String str2) {
            this.a = i;
            this.b = i2;
            this.c = str;
            this.d = str2;
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                b(this.a, this.b, this.c, this.d);
            } catch (Throwable th) {
                FlyLog.getInstance().w(th);
            }
        }

        private void b(final int i, final int i2, final String str, final String str2) {
            FlyLog.getInstance().d("[LGSM] SLR: onL", new Object[0]);
            if (v.a().a(new cn.fly.tools.utils.i() { // from class: cn.fly.commons.v.a.1
                @Override // cn.fly.tools.utils.i
                protected void a() throws Throwable {
                    FlyLog.getInstance().d("[LGSM] SLR: Ins", new Object[0]);
                    HashMap map = new HashMap();
                    map.put(n.a("010Jdgbacfei9d4bhdgbgbi?c"), Integer.valueOf(i));
                    map.put(n.a("006RdgbacfdaMb[ch"), str);
                    map.put(n.a("004gRca4hd"), Integer.valueOf(i2));
                    map.put(n.a("005dLbhbhAbg"), Long.valueOf(System.currentTimeMillis()));
                    String strEncode = URLEncoder.encode(str2);
                    if (TextUtils.isEmpty(strEncode)) {
                        strEncode = str2;
                    }
                    map.put(n.a("003+bddgch"), Base64.encodeToString(strEncode.getBytes("utf-8"), 2));
                    map.put(n.a("005g1bgbd*d(dg"), 1);
                    FlyLog.getInstance().d("[LGSM] W l " + map, new Object[0]);
                    v.b(i2).a(HashonHelper.fromHashMap(map));
                }
            }) && z.b()) {
                FlyLog.getInstance().d("[LGSM] SLR: U", new Object[0]);
                ab.a.execute(new c());
            }
        }
    }
}