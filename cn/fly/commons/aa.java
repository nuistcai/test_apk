package cn.fly.commons;

import android.content.pm.ApplicationInfo;
import android.media.MediaDrm;
import android.os.Build;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.a;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class aa {
    private static volatile aa e = null;
    private HashMap<String, Integer> f;
    private volatile String a = null;
    private volatile String b = null;
    private volatile String c = null;
    private volatile String d = null;
    private final byte[] g = new byte[0];
    private final byte[] h = new byte[0];

    private aa() {
    }

    public static aa a() {
        if (e == null) {
            synchronized (aa.class) {
                if (e == null) {
                    e = new aa();
                }
            }
        }
        return e;
    }

    public String b() {
        return "2";
    }

    public String c() {
        if (TextUtils.isEmpty(this.b)) {
            String strB = ad.b().b("key_rdt2", (String) null);
            if (!TextUtils.isEmpty(strB)) {
                this.b = strB;
            }
        }
        return this.b;
    }

    public boolean d() {
        if (TextUtils.isEmpty(this.b)) {
            synchronized (this) {
                if (TextUtils.isEmpty(this.b)) {
                    return TextUtils.isEmpty(ad.b().b("key_rdt2", (String) null));
                }
                return false;
            }
        }
        return false;
    }

    public synchronized String e() {
        String strC;
        strC = c();
        if (TextUtils.isEmpty(strC)) {
            strC = j();
            this.b = strC;
            if (!TextUtils.isEmpty(strC)) {
                ad.b().a("key_rdt2", strC);
            }
        }
        return strC;
    }

    public String a(boolean z) {
        String strC = c();
        if (TextUtils.isEmpty(strC)) {
            strC = b(z);
            this.b = strC;
            if (!TextUtils.isEmpty(strC)) {
                ad.b().a("key_rdt2", strC);
            }
        }
        return strC;
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, this.b)) {
            FlyLog.getInstance().d("rddd saveRD pre is " + this.b + " cur is " + str, new Object[0]);
            ad.b().a("key_rdt2", str);
        }
    }

    private String j() {
        if (!TextUtils.isEmpty(g())) {
            return "12" + c(g());
        }
        if (!TextUtils.isEmpty(f())) {
            return "22" + c(f());
        }
        if (!TextUtils.isEmpty(k())) {
            return "32" + c(this.d);
        }
        return "42" + c(UUID.randomUUID().toString());
    }

    private String b(boolean z) {
        String strG;
        if (z) {
            strG = h();
        } else {
            strG = g();
        }
        if (!TextUtils.isEmpty(strG)) {
            return "12" + c(strG);
        }
        return "42" + c(UUID.randomUUID().toString());
    }

    public String f() {
        if (TextUtils.isEmpty(this.c)) {
            synchronized (this.h) {
                if (TextUtils.isEmpty(this.c)) {
                    this.c = m();
                }
            }
        }
        return this.c;
    }

    private String k() {
        DH.requester(FlySDK.getContext()).getOD().request(new DH.DHResponder() { // from class: cn.fly.commons.aa.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                String od = dHResponse.getOD();
                List<String> listAsList = Arrays.asList("00000000-0000-0000-0000-000000000000", "00000000000000000000000000000000");
                a.c cVarJ = ad.b().j();
                if (cVarJ != null && cVarJ.d() != null) {
                    listAsList = cVarJ.d();
                }
                if (!TextUtils.isEmpty(od) && !listAsList.contains(od)) {
                    aa.this.d = od;
                }
            }
        });
        return this.d;
    }

    public String g() {
        if (TextUtils.isEmpty(this.a)) {
            synchronized (this.g) {
                if (TextUtils.isEmpty(this.a) && c.d.get()) {
                    try {
                        this.a = l();
                        b(this.a);
                    } catch (Throwable th) {
                        FlyLog.getInstance().d(th);
                    }
                }
            }
        }
        return this.a;
    }

    public String h() {
        if (!TextUtils.isEmpty(this.a)) {
            return this.a;
        }
        LinkedHashMap linkedHashMap = (LinkedHashMap) ad.b().a("key_drds");
        if (linkedHashMap != null && linkedHashMap.size() > 0) {
            return (String) linkedHashMap.keySet().iterator().next();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                HashMap map = (HashMap) ad.b().a("key_drds");
                if (map == null) {
                    map = new HashMap();
                }
                if (map.containsKey(str)) {
                    int iIntValue = ((Integer) map.get(str)).intValue();
                    if (iIntValue < 100000) {
                        map.put(str, Integer.valueOf(iIntValue + 1));
                    }
                } else {
                    map.put(str, 1);
                }
                ArrayList<Map.Entry> arrayList = new ArrayList(map.entrySet());
                Collections.sort(arrayList, new Comparator<Map.Entry<String, Integer>>() { // from class: cn.fly.commons.aa.2
                    @Override // java.util.Comparator
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public int compare(Map.Entry<String, Integer> entry, Map.Entry<String, Integer> entry2) {
                        return entry2.getValue().compareTo(entry.getValue());
                    }
                });
                for (int size = arrayList.size(); size > 7; size--) {
                    arrayList.remove(size - 1);
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : arrayList) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
                ad.b().a("key_drds", linkedHashMap);
                this.f = new LinkedHashMap();
                int iMin = Math.min(3, arrayList.size());
                for (int i = 0; i < iMin; i++) {
                    Map.Entry entry2 = (Map.Entry) arrayList.get(i);
                    this.f.put(entry2.getKey(), entry2.getValue());
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public HashMap<String, Integer> i() {
        return this.f;
    }

    private String c(String str) {
        StringBuilder sb = new StringBuilder(str);
        String manufacturerForFly = DH.SyncMtd.getManufacturerForFly();
        String modelForFly = DH.SyncMtd.getModelForFly();
        if (!TextUtils.isEmpty(manufacturerForFly)) {
            sb.append(manufacturerForFly.trim().toUpperCase());
        }
        if (!TextUtils.isEmpty(modelForFly)) {
            sb.append(modelForFly.trim().toUpperCase());
        }
        return Data.MD5(sb.toString());
    }

    private String l() throws Throwable {
        if (DH.SyncMtd.getOSVersionIntForFly() < 18) {
            return null;
        }
        String brandForFly = DH.SyncMtd.getBrandForFly();
        String manufacturerForFly = DH.SyncMtd.getManufacturerForFly();
        if ("SMARTISAN".equalsIgnoreCase(brandForFly) || "SMARTISAN".equalsIgnoreCase(manufacturerForFly)) {
            return null;
        }
        final String[] strArr = {null};
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ab.a.execute(new cn.fly.tools.utils.i() { // from class: cn.fly.commons.aa.3
            @Override // cn.fly.tools.utils.i
            protected void a() {
                MediaDrm mediaDrm;
                long jCurrentTimeMillis = System.currentTimeMillis();
                String strB = w.b("061fBdhieccggeggg>eQdehjgi7ebNckcc,f.ggeh'beZdecbgkckchdicheehjggiehjgcck:h-didh_bJdhgheeEb-eeckcjehdighcbgdgegdDbUgggegehjhjTb@geeg");
                UUID uuid = new UUID(-1301668207276963122L, -6645017420763422227L);
                MediaDrm mediaDrm2 = null;
                try {
                    try {
                        mediaDrm = new MediaDrm(uuid);
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        cn.fly.tools.a.d.a(FlySDK.getContext()).a(mediaDrm.getClass(), mediaDrm, w.b("012dchSchcc]e-cgeh^eh)cf*i"), new Class[]{Object.class, byte[].class, String.class}, new Object[]{new WeakReference(mediaDrm), aa.this.a(uuid), strB}, (Object[]) null);
                        byte[] propertyByteArray = mediaDrm.getPropertyByteArray(w.b("014;cb%eJccchBbe+djWdUchcdcf,e*ddcb"));
                        strArr[0] = Data.byteToHex(propertyByteArray, 0, propertyByteArray.length);
                        FlyLog.getInstance().d("rddd wv c " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
                        countDownLatch.countDown();
                        if (DH.SyncMtd.getOSVersionIntForFly() >= 28) {
                            mediaDrm.release();
                        } else {
                            mediaDrm.release();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        mediaDrm2 = mediaDrm;
                        try {
                            FlyLog.getInstance().d(th);
                            countDownLatch.countDown();
                            if (DH.SyncMtd.getOSVersionIntForFly() >= 28) {
                                if (mediaDrm2 != null) {
                                    mediaDrm2.release();
                                }
                            } else if (mediaDrm2 != null) {
                                mediaDrm2.release();
                            }
                        } catch (Throwable th3) {
                            try {
                                countDownLatch.countDown();
                                if (DH.SyncMtd.getOSVersionIntForFly() >= 28) {
                                    if (mediaDrm2 != null) {
                                        mediaDrm2.release();
                                    }
                                } else if (mediaDrm2 != null) {
                                    mediaDrm2.release();
                                }
                                throw th3;
                            } catch (Throwable th4) {
                                FlyLog.getInstance().d(th4);
                                throw th3;
                            }
                        }
                    }
                } catch (Throwable th5) {
                    FlyLog.getInstance().d(th5);
                }
            }
        });
        countDownLatch.await(1L, TimeUnit.SECONDS);
        return strArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] a(UUID uuid) {
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        byte[] bArr = new byte[16];
        for (int i = 0; i < 8; i++) {
            int i2 = (7 - i) * 8;
            bArr[i] = (byte) (mostSignificantBits >>> i2);
            bArr[i + 8] = (byte) (leastSignificantBits >>> i2);
        }
        return bArr;
    }

    private String m() {
        long j;
        final String[] strArr = new String[1];
        if (c.a(w.b("003cff"))) {
            try {
                strArr[0] = ad.b().b("key_pddt", (String) null);
                if (!TextUtils.isEmpty(strArr[0])) {
                    long jB = ad.b().b("key_lgpdt", 0L);
                    try {
                        j = Long.parseLong(String.valueOf(c.a(w.b("006.ehdbehdiIci"), 604800))) * 1000;
                    } catch (Throwable th) {
                        j = 604800000;
                    }
                    if (System.currentTimeMillis() - jB < j) {
                        FlyLog.getInstance().d("rddd che p useable", new Object[0]);
                        return strArr[0];
                    }
                }
                if ((w.b("004-ccchcccj").equalsIgnoreCase(DH.SyncMtd.getManufacturerForFly()) && DH.SyncMtd.getOSVersionIntForFly() <= 25) || (w.b("006g)cfGc efVe+ch").equalsIgnoreCase(DH.SyncMtd.getManufacturerForFly()) && DH.SyncMtd.getOSVersionIntForFly() <= 22)) {
                    return null;
                }
                final List<String> listN = n();
                if (!listN.isEmpty()) {
                    final CountDownLatch countDownLatch = new CountDownLatch(1);
                    final StringBuilder sb = new StringBuilder();
                    DH.RequestBuilder requestBuilderRequester = DH.requester(FlySDK.getContext());
                    Iterator<String> it = listN.iterator();
                    while (it.hasNext()) {
                        requestBuilderRequester.getAInfoForPkg(it.next(), 1);
                    }
                    requestBuilderRequester.request(new DH.DHResponder() { // from class: cn.fly.commons.aa.4
                        @Override // cn.fly.tools.utils.DH.DHResponder
                        public void onResponse(DH.DHResponse dHResponse) {
                            int i = 0;
                            for (int i2 = 0; i2 < listN.size(); i2++) {
                                try {
                                    ApplicationInfo aInfoForPkg = dHResponse.getAInfoForPkg(i2);
                                    if (aInfoForPkg != null) {
                                        sb.append((String) listN.get(i2));
                                        sb.append(cn.fly.tools.c.a(aInfoForPkg, (String) listN.get(i2)));
                                        i++;
                                    }
                                } finally {
                                    countDownLatch.countDown();
                                }
                            }
                            if (i > 0) {
                                sb.append(Build.BRAND.toUpperCase(Locale.ROOT)).append(Build.MODEL.toUpperCase(Locale.ROOT)).append(Build.MANUFACTURER.toUpperCase(Locale.ROOT));
                                sb.append(i);
                                strArr[0] = Data.MD5(sb.toString());
                                ad.b().a("key_pddt", strArr[0]);
                                ad.b().a("key_lgpdt", System.currentTimeMillis());
                            }
                        }
                    });
                    try {
                        countDownLatch.await(1000L, TimeUnit.MILLISECONDS);
                    } catch (Throwable th2) {
                    }
                }
            } catch (Throwable th3) {
                FlyLog.getInstance().d(th3);
            }
        }
        return strArr[0];
    }

    private List<String> n() {
        final ArrayList arrayList = new ArrayList();
        DH.requester(FlySDK.getContext()).getSA().request(new DH.DHResponder() { // from class: cn.fly.commons.aa.5
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                if (dHResponse.getSA() != null && !dHResponse.getSA().isEmpty()) {
                    Iterator<HashMap<String, String>> it = dHResponse.getSA().iterator();
                    while (it.hasNext()) {
                        String str = it.next().get(w.b("003iWdgdi"));
                        if (str != null && !str.contains("com.google.android") && !str.contains("com.miui.packageinstaller")) {
                            arrayList.add(str);
                        }
                    }
                    Collections.sort(arrayList);
                }
            }
        });
        return arrayList;
    }
}