package cn.fly.commons;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.SparseArray;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.MDP;
import cn.fly.tools.network.NetCommunicator;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.FileLocker;
import cn.fly.tools.utils.FileUtils;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ResHelper;
import cn.fly.tools.utils.SQLiteHelper;
import com.alibaba.fastjson.asm.Opcodes;
import com.tencent.bugly.BuglyStrategy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class d {
    private static d a;
    private static volatile SQLiteHelper.SingleTableDB b = null;

    public static synchronized d a() {
        if (a == null) {
            a = new d();
        }
        return a;
    }

    private d() {
        try {
            File dataCacheFile = ResHelper.getDataCacheFile(FlySDK.getContext(), n.a, true);
            if (dataCacheFile.exists() && dataCacheFile.length() > 209715200) {
                dataCacheFile.delete();
                dataCacheFile = ResHelper.getDataCacheFile(FlySDK.getContext(), n.a, true);
            }
            b = SQLiteHelper.getDatabase(dataCacheFile.getAbsolutePath(), w.b("008SekIchc5ej,eci") + "_1");
            b.addField(w.b("004h;chce3e"), w.b("004heEdhXh"), true);
            b.addField(w.b("004Tcb$chc"), w.b("004he!dhVh"), true);
            b bVarB = b.b();
            if (bVarB != null) {
                cn.fly.commons.a.l.a().a(0L, Opcodes.GETFIELD, bVarB);
            }
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
    }

    public void a(long j, HashMap<String, Object> map) {
        boolean zA = c.a();
        FlyLog.getInstance().d("DH PD: " + map.get(w.b("004hPdbWie")) + ", to: " + zA, new Object[0]);
        if (!zA) {
            return;
        }
        ab.b.execute(a.b(j, map));
    }

    private static class a implements Runnable {
        private static final a[] a = new a[3];
        private long b;
        private HashMap<String, Object> c;

        private a(long j, HashMap<String, Object> map) {
            this.b = j;
            this.c = map;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static a b(long j, HashMap<String, Object> map) {
            a[] aVarArr = a;
            synchronized (aVarArr) {
                for (int i = 0; i < 3; i++) {
                    a aVar = aVarArr[i];
                    if (aVar != null) {
                        aVar.b = j;
                        if (aVar.c != null) {
                            aVar.c.clear();
                        }
                        aVar.c = map;
                        aVarArr[i] = null;
                        return aVar;
                    }
                }
                return new a(j, map);
            }
        }

        private void a() {
            try {
                a[] aVarArr = a;
                synchronized (aVarArr) {
                    for (int i = 0; i < 3; i++) {
                        if (aVarArr[i] == null) {
                            this.b = 0L;
                            if (this.c != null) {
                                this.c.clear();
                            }
                            this.c = null;
                            aVarArr[i] = this;
                            return;
                        }
                    }
                }
            } catch (Throwable th) {
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                u.a(u.a(u.b), new t() { // from class: cn.fly.commons.d.a.1
                    @Override // cn.fly.commons.t
                    public boolean a(FileLocker fileLocker) {
                        DH.requester(FlySDK.getContext()).getDetailNetworkTypeForStatic().request(new DH.DHResponder() { // from class: cn.fly.commons.d.a.1.1
                            @Override // cn.fly.tools.utils.DH.DHResponder
                            public void onResponse(DH.DHResponse dHResponse) throws Throwable {
                                b bVarB;
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(w.b("004hDchce$e"), String.valueOf(a.this.b));
                                if (a.this.c != null) {
                                    a.this.c.put(w.b("006cii:dgHe;db"), q.a());
                                    a.this.c.put(w.b("006ciii-dgdi"), DH.SyncMtd.getPackageName());
                                    a.this.c.put(w.b("006ciiGcc6eXci"), DH.SyncMtd.getAppVersionName());
                                    long jLongValue = ((Long) c.a(w.b("010Ieh9h6ciLcheHdidbddcb"), 0L)).longValue();
                                    if (jLongValue != 0) {
                                        a.this.c.put(w.b("010AehZh)ciMcheCdidbddcb"), Long.valueOf(jLongValue));
                                    }
                                }
                                contentValues.put(w.b("004(cbUchc"), Base64.encodeToString(Data.AES128Encode(Data.rawMD5(DH.SyncMtd.getManufacturerForFly()), HashonHelper.fromHashMap(a.this.c).getBytes("utf-8")), 2));
                                SQLiteHelper.insert(d.b, contentValues);
                                long jLongValue2 = ((Long) c.a(w.b("004=cbReLcf_i"), 2L)).longValue();
                                if (w.b("004dJcj:de").equals(dHResponse.getDetailNetworkTypeForStatic())) {
                                    jLongValue2 = 120;
                                }
                                if (!c.c() || (bVarB = b.b()) == null) {
                                    return;
                                }
                                if (jLongValue2 <= 0) {
                                    bVarB.run();
                                } else {
                                    if (cn.fly.commons.a.l.a().a(jLongValue2, bVarB)) {
                                        return;
                                    }
                                    bVarB.c();
                                }
                            }
                        });
                        return false;
                    }
                });
            } finally {
                try {
                } finally {
                }
            }
        }
    }

    public static class b implements Runnable {
        private static final b[] b = new b[1];
        public boolean a = false;

        static {
            b[0] = new b();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static b b() {
            synchronized (b) {
                b bVar = b[0];
                if (bVar != null) {
                    b[0] = null;
                    if (bVar.a) {
                        bVar.a = false;
                    }
                    return bVar;
                }
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c() {
            synchronized (b) {
                if (b[0] == null) {
                    b[0] = this;
                }
            }
            this.a = false;
        }

        @Override // java.lang.Runnable
        public void run() {
            DH.requester(FlySDK.getContext()).getDeviceKey().getDetailNetworkTypeForStatic().getDataNtTypeStrict().request(new DH.DHResponder() { // from class: cn.fly.commons.d.b.1
                /* JADX WARN: Code restructure failed: missing block: B:9:0x001e, code lost:
                
                    cn.fly.commons.a.l.a().d();
                 */
                @Override // cn.fly.tools.utils.DH.DHResponder
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void onResponse(DH.DHResponse dHResponse) {
                    try {
                        String[][] strArr = new String[50][];
                        int iA = b.this.a(strArr);
                        while (true) {
                            if (iA <= 0) {
                                break;
                            }
                            SparseArray sparseArrayA = b.this.a(strArr, iA, dHResponse);
                            if (sparseArrayA.size() == 0 && b.this.a) {
                                break;
                            }
                            if (sparseArrayA.size() > 0) {
                                b.this.a((SparseArray<String>) sparseArrayA);
                            }
                            if (iA < 50) {
                                break;
                            } else {
                                iA = b.this.a(strArr);
                            }
                        }
                    } finally {
                        b.this.c();
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int a(String[][] strArr) {
            long j;
            int i = 0;
            Cursor cursorQuery = null;
            try {
                cursorQuery = SQLiteHelper.query(d.b, new String[]{w.b("004hLchce'e"), w.b("004Scb:chc")}, null, null, "time desc");
                if (cursorQuery == null) {
                    return 0;
                }
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Throwable th) {
                        }
                    }
                    return 0;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                int i2 = 0;
                do {
                    try {
                        String[] strArr2 = {cursorQuery.getString(0), cursorQuery.getString(1)};
                        try {
                            j = Long.parseLong(strArr2[0]);
                        } catch (Throwable th2) {
                            j = -1;
                        }
                        if (j <= jCurrentTimeMillis) {
                            strArr[i2] = strArr2;
                            i2++;
                        }
                        if (i2 >= strArr.length) {
                            break;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        i = i2;
                        try {
                            FlyLog.getInstance().w(th);
                            if (cursorQuery != null) {
                                try {
                                    cursorQuery.close();
                                } catch (Throwable th4) {
                                }
                            }
                            return i;
                        } finally {
                            if (cursorQuery != null) {
                                try {
                                    cursorQuery.close();
                                } catch (Throwable th5) {
                                }
                            }
                        }
                    }
                } while (cursorQuery.moveToNext());
                if (cursorQuery == null) {
                    return i2;
                }
                try {
                    cursorQuery.close();
                    return i2;
                } catch (Throwable th6) {
                    return i2;
                }
            } catch (Throwable th7) {
                th = th7;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int a(SparseArray<String> sparseArray) {
            try {
                StringBuilder sb = new StringBuilder();
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    if (sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append('\'').append(sparseArray.valueAt(i)).append('\'');
                }
                try {
                    return SQLiteHelper.delete(d.b, "time in (" + sb.toString() + ")", null);
                } catch (Throwable th) {
                    FlyLog.getInstance().w(th);
                    return SQLiteHelper.delete(d.b, "time in (" + sb.toString() + ")", null);
                }
            } catch (Throwable th2) {
                FlyLog.getInstance().w(th2);
                return 0;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public SparseArray<String> a(String[][] strArr, int i, DH.DHResponse dHResponse) {
            HashMap<String, Object> map;
            ArrayList arrayList;
            SparseArray<String> sparseArray = new SparseArray<>();
            try {
                map = new HashMap<>();
                map.put(w.b("004ifch"), Integer.valueOf(DH.SyncMtd.getPlatformCode()));
                map.put(w.b("0063cb6e?ccch5be"), dHResponse.getDeviceKey());
                map.put(w.b("005ScecjcbJef"), DH.SyncMtd.getModelForFly());
                map.put(w.b("004Ocbcfchcb"), f.a((FlyProduct) null));
                map.put(w.b("011deh4efcjcidgThEdb^ie"), dHResponse.getDetailNetworkTypeForStatic());
                map.put(w.b("0159cbPchcKdfPeh(efcjcidgebdb1ie"), Integer.valueOf(dHResponse.getDataNtTypeStrict()));
                arrayList = new ArrayList();
                byte[] bArrRawMD5 = Data.rawMD5(DH.SyncMtd.getManufacturerForFly());
                for (int i2 = 0; i2 < i; i2++) {
                    String[] strArr2 = strArr[i2];
                    try {
                        HashMap mapFromJson = HashonHelper.fromJson(new String(Data.AES128Decode(bArrRawMD5, Base64.decode(strArr2[1], 2)), "utf-8").trim());
                        sparseArray.put(i2, strArr2[0]);
                        String str = (String) mapFromJson.get(w.b("004hAdb1ie"));
                        if (TextUtils.equals(str, "ALSAMT") || TextUtils.equals(str, "LCMT") || TextUtils.equals(str, "O_LCMT") || TextUtils.equals(str, "WIMT") || TextUtils.equals(str, "WLMT") || TextUtils.equals(str, "BSIOMT")) {
                            cn.fly.commons.b.a().a(str, mapFromJson);
                        }
                        arrayList.add(mapFromJson);
                    } catch (Throwable th) {
                        FlyLog.getInstance().w(th);
                    }
                }
            } catch (Throwable th2) {
                FlyLog.getInstance().w(th2);
            }
            if (arrayList.isEmpty()) {
                return new SparseArray<>();
            }
            map.put(w.b("005Ocb,chc(eh"), arrayList);
            map.put(w.b("005h<cjdg*ed"), ae.a().b());
            HashMap<String, String> map2 = new HashMap<>();
            map2.put(w.b("013'djehZeQcigjddcbYedh;chPhCdb"), ac.e());
            map2.put(w.b("004Qcecjchcb"), cn.fly.tools.b.c.a(FlySDK.getContext()).d().ao());
            NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
            networkTimeOut.readTimout = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
            networkTimeOut.connectionTimeout = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
            if (!"200".equals(String.valueOf(HashonHelper.fromJson((String) new NetCommunicator(1024, "ceeef5035212dfe7c6a0acdc0ef35ce5b118aab916477037d7381f85c6b6176fcf57b1d1c3296af0bb1c483fe5e1eb0ce9eb2953b44e494ca60777a1b033cc07", "191737288d17e660c4b61440d5d14228a0bf9854499f9d68d8274db55d6d954489371ecf314f26bec236e58fac7fffa9b27bcf923e1229c4080d49f7758739e5bd6014383ed2a75ce1be9b0ab22f283c5c5e11216c5658ba444212b6270d629f2d615b8dfdec8545fb7d4f935b0cc10b6948ab4fc1cb1dd496a8f94b51e888dd", networkTimeOut).requestWithoutEncode(false, map2, map, j.a().a("gclg") + "/v6/gcl", false)).get(w.b("006.eh*hch,cfeh"))))) {
                sparseArray.clear();
            }
            return sparseArray;
        }
    }

    public static void a(Object... objArr) {
        try {
            try {
                i.a().a(13);
                ResHelper.deleteFileAndFolder(b(objArr));
            } catch (Throwable th) {
                try {
                    i.a().a(5, th);
                    ResHelper.deleteFileAndFolder(null);
                } catch (Throwable th2) {
                    try {
                        ResHelper.deleteFileAndFolder(null);
                    } catch (Throwable th3) {
                        i.a().a(4, th3);
                    }
                    throw th2;
                }
            }
        } catch (Throwable th4) {
            i.a().a(4, th4);
        }
    }

    private static File b(Object... objArr) throws Throwable {
        int i;
        InputStream fileInputStream;
        File file;
        FileOutputStream fileOutputStream;
        i iVarA;
        String str = (String) objArr[0];
        String str2 = (String) objArr[1];
        String str3 = (String) objArr[4];
        String str4 = (String) objArr[5];
        InputStream inputStream = null;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            File file2 = new File(FlySDK.getContext().getFilesDir(), w.b("003-eh%bb"));
            byte[] bArr = (byte[]) objArr[2];
            try {
                i = Integer.parseInt(String.valueOf(objArr[3]));
            } catch (Throwable th) {
                i = 0;
            }
            if (bArr != null && i > 0 && bArr.length >= i && str.equals(Data.MD5(bArr, 0, i))) {
                fileInputStream = new ByteArrayInputStream(bArr, 0, i);
            } else {
                File file3 = new File(file2, w.b("008bVcjFd4deckehTbb"));
                if (file3.exists() && str.equals(Data.MD5(file3))) {
                    fileInputStream = new FileInputStream(file3);
                } else {
                    i.a().a(20);
                    file3.delete();
                    fileInputStream = null;
                }
            }
            if (fileInputStream == null) {
                file = null;
                inputStream = fileInputStream;
            } else {
                try {
                    file = new File(file2, String.valueOf(System.currentTimeMillis()));
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file4 = new File(file, file.getName() + w.b("004)ckfcchLi"));
                    try {
                        fileOutputStream = new FileOutputStream(file4);
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = null;
                    }
                    try {
                        Data.AES128Decode(str2, fileInputStream, fileOutputStream);
                        C0041r.a(fileInputStream, fileOutputStream);
                        try {
                            if (!k.a().b()) {
                                i.a().a(19);
                            } else {
                                i.a().a(14);
                                cn.fly.commons.a.c.a(str, file4, str3, str4);
                            }
                            try {
                                ResHelper.deleteFileAndFolder(file);
                            } catch (Throwable th3) {
                                th = th3;
                                iVarA = i.a();
                                iVarA.a(4, th);
                                C0041r.a(inputStream);
                                return file;
                            }
                        } catch (Throwable th4) {
                            try {
                                i.a().a(6, th4);
                                try {
                                    ResHelper.deleteFileAndFolder(file);
                                } catch (Throwable th5) {
                                    th = th5;
                                    iVarA = i.a();
                                    iVarA.a(4, th);
                                    C0041r.a(inputStream);
                                    return file;
                                }
                            } finally {
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        C0041r.a(fileInputStream, fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    inputStream = fileInputStream;
                    C0041r.a(inputStream);
                    throw th;
                }
            }
            C0041r.a(inputStream);
            return file;
        } catch (Throwable th8) {
            th = th8;
            C0041r.a(inputStream);
            throw th;
        }
    }

    public static void a(final ArrayList<HashMap<String, Object>> arrayList, final cn.fly.tools.utils.d<Void> dVar) throws Throwable {
        if (arrayList != null && !arrayList.isEmpty()) {
            DH.requester(FlySDK.getContext()).getDeviceKey().getMIUIVersionForFly().getAdvertisingID().request(new DH.DHResponder() { // from class: cn.fly.commons.d.1
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    boolean zBooleanValue;
                    String str;
                    String str2;
                    String str3;
                    Object obj;
                    try {
                        File file = new File(FlySDK.getContext().getFilesDir(), w.b("003]eheeHf"));
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        final ArrayList arrayList2 = new ArrayList();
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            HashMap map = (HashMap) it.next();
                            try {
                                Boolean bool = (Boolean) map.get(w.b("002cKeh"));
                                zBooleanValue = bool != null ? bool.booleanValue() : false;
                                str = (String) map.get(w.b("002+de f"));
                                str2 = (String) map.get("m");
                                str3 = (String) map.get("args");
                                obj = map.get(w.b("002:chcb"));
                            } catch (Throwable th) {
                                h.a().a(2, 50, th, ResHelper.forceCast(map.get(w.b("002Xchcb")), -1) + "");
                            }
                            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
                                String strA = f.a((FlyProduct) null);
                                HashMap map2 = new HashMap();
                                map2.put(w.b("0040cbcfchcb"), strA);
                                map2.put(w.b("005hAcjdgBed"), ae.a().b());
                                map2.put(w.b("004,cecjchcb"), cn.fly.tools.b.c.a(FlySDK.getContext()).d().an());
                                map2.put(w.b("010-ehcbdgfj.eAciehchcjQd"), Integer.valueOf(FlySDK.SDK_VERSION_CODE));
                                map2.put(w.b("006cii5dg5eJdb"), q.a());
                                map2.put(w.b("009cii+dkXeb8ci[eh"), FlySDK.getAppSecret());
                                map2.put(w.b("006*cbcjceVc@ch[d"), FlySDK.getDmn().getDomain());
                                map2.put(w.b("010JdecjciObe4ej+hhi5eh"), Boolean.valueOf(FlySDK.checkForceHttps()));
                                map2.put(w.b("009>decjci1beSddXiMccgg"), Boolean.valueOf(FlySDK.checkV6()));
                                map2.put(w.b("004ebeQdh"), Long.valueOf(((Long) c.a(w.b("004ebe)dh"), 5L)).longValue()));
                                map2.put(w.b("002b1cb"), (String) c.a(w.b("002bWcb"), w.b("006Pgdgdgegegege")));
                                map2.put("usridt", ac.g());
                                map2.put(w.b("002!chcb"), obj);
                                if (!TextUtils.isEmpty(str3)) {
                                    map2.put("args", HashonHelper.fromJson(str3));
                                }
                                map2.put(w.b("008Lcb!eLccch$beAddcb"), dHResponse.getDeviceKey());
                                map2.put("imei", null);
                                map2.put("imsi", null);
                                map2.put("sno", null);
                                map2.put("ssno", null);
                                map2.put("miui", dHResponse.getMIUIVersionForFly());
                                map2.put(w.b("005!cecjcb$ef"), DH.SyncMtd.getModelForFly());
                                map2.put(w.b("0074de?cbhLcjcidb"), DH.SyncMtd.getManufacturerForFly());
                                map2.put(w.b("005ReeciScdTcb"), DH.SyncMtd.getBrandForFly());
                                map2.put(w.b("005cNcbehchcb"), dHResponse.getAdvertisingID());
                                map2.put(w.b("006ciiSccRe_ci"), DH.SyncMtd.getAppVersionName());
                                map2.put("appVerCode", Integer.valueOf(DH.SyncMtd.getAppVersion()));
                                map2.put(w.b("011icb)dg]cFdi5e7dfRc1ceRe"), DH.SyncMtd.getPackageName());
                                map2.put(w.b("005)eeehehchcb"), null);
                                map2.put("osint", Integer.valueOf(DH.SyncMtd.getOSVersionIntForFly()));
                                map2.put("osname", DH.SyncMtd.getOSVersionNameForFly());
                                map2.put("mdpName", MDP.class.getName());
                                String strFromHashMap = HashonHelper.fromHashMap(map2);
                                String strCheckHttpRequestUrl = NetCommunicator.checkHttpRequestUrl(str);
                                if (!TextUtils.isEmpty(str2)) {
                                    File file2 = new File(file, str2);
                                    if (zBooleanValue) {
                                        arrayList2.add(file2.getAbsolutePath());
                                    }
                                    d.b(String.valueOf(obj), file2, zBooleanValue, strCheckHttpRequestUrl, str2, strFromHashMap);
                                }
                            }
                        }
                        FileUtils.deleteFilesInDirWithFilter(file, new FileFilter() { // from class: cn.fly.commons.d.1.1
                            @Override // java.io.FileFilter
                            public boolean accept(File file3) {
                                return !arrayList2.contains(file3.getAbsolutePath());
                            }
                        });
                    } finally {
                        try {
                        } finally {
                        }
                    }
                }
            });
        } else {
            dVar.a(null);
        }
    }

    public static String a(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iArr.length; i++) {
            if (iArr[i] < z.f().length()) {
                sb.append((char) (r2.charAt(iArr[i]) - 2));
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final String str, final File file, final boolean z, final String str2, final String str3, final String str4) {
        new Thread(new Runnable() { // from class: cn.fly.commons.d.2
            @Override // java.lang.Runnable
            public void run() {
                Throwable th;
                int i;
                FileOutputStream fileOutputStream;
                ByteArrayOutputStream byteArrayOutputStream;
                try {
                    ByteArrayOutputStream byteArrayOutputStream2 = null;
                    if (z) {
                        try {
                            if (file.exists() && str3.equals(Data.MD5(file))) {
                                if (d.b(str, 5, file.getAbsolutePath(), null, str4)) {
                                    return;
                                }
                                file.delete();
                                return;
                            }
                            int i2 = 6;
                            try {
                                if (file.exists()) {
                                    file.delete();
                                }
                                i = 7;
                                try {
                                    try {
                                        fileOutputStream = new FileOutputStream(file);
                                    } catch (Throwable th2) {
                                        th = th2;
                                        fileOutputStream = null;
                                    }
                                    try {
                                        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
                                        networkTimeOut.readTimout = 60000;
                                        networkTimeOut.connectionTimeout = 15000;
                                        new NetworkHelper().download(str2, fileOutputStream, networkTimeOut);
                                        C0041r.a(fileOutputStream);
                                        if (file.length() <= 0 || !TextUtils.equals(str3, Data.MD5(file))) {
                                            if (file.exists()) {
                                                file.delete();
                                            }
                                        } else if (!d.b(str, 7, file.getAbsolutePath(), null, str4)) {
                                            file.delete();
                                        }
                                        return;
                                    } catch (Throwable th3) {
                                        th = th3;
                                        C0041r.a(fileOutputStream);
                                        if (file.length() > 0 && TextUtils.equals(str3, Data.MD5(file))) {
                                            if (!d.b(str, 7, file.getAbsolutePath(), null, str4)) {
                                                file.delete();
                                            }
                                            i2 = 7;
                                        } else if (file.exists()) {
                                            file.delete();
                                        }
                                        try {
                                            throw th;
                                        } catch (Throwable th4) {
                                            i = i2;
                                            th = th4;
                                        }
                                    }
                                } catch (Throwable th5) {
                                    th = th5;
                                }
                            } catch (Throwable th6) {
                                th = th6;
                                i = 6;
                            }
                        } catch (Throwable th7) {
                            th = th7;
                            i = 5;
                        }
                    } else {
                        if (file.exists()) {
                            file.delete();
                        }
                        try {
                            try {
                                byteArrayOutputStream = new ByteArrayOutputStream();
                            } catch (Throwable th8) {
                                th = th8;
                            }
                            try {
                                NetworkHelper.NetworkTimeOut networkTimeOut2 = new NetworkHelper.NetworkTimeOut();
                                networkTimeOut2.readTimout = 60000;
                                networkTimeOut2.connectionTimeout = 15000;
                                new NetworkHelper().download(str2, byteArrayOutputStream, networkTimeOut2);
                                C0041r.a(byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                if (byteArray.length <= 0 || !TextUtils.equals(str3, Data.MD5(byteArray))) {
                                    return;
                                }
                                try {
                                    d.b(str, 9, null, byteArray, str4);
                                    return;
                                } catch (Throwable th9) {
                                    th = th9;
                                    i = 9;
                                }
                            } catch (Throwable th10) {
                                th = th10;
                                byteArrayOutputStream2 = byteArrayOutputStream;
                                C0041r.a(byteArrayOutputStream2);
                                throw th;
                            }
                        } catch (Throwable th11) {
                            th = th11;
                            i = 8;
                        }
                    }
                } catch (Throwable th12) {
                    th = th12;
                    i = 13;
                }
                h.a().a(5, i, th, str);
                FlyLog.getInstance().d(th);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String str, int i, String str2, byte[] bArr, String str3) {
        try {
            Method method = null;
            boolean z = false;
            for (Method method2 : cn.fly.tools.c.a.class.getMethods()) {
                Annotation[] annotations = method2.getAnnotations();
                if (annotations != null) {
                    int length = annotations.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        Annotation annotation = annotations[i2];
                        if (annotation == null || annotation.annotationType() != cn.fly.tools.c.b.class) {
                            i2++;
                        } else {
                            method = method2;
                            z = true;
                            break;
                        }
                    }
                    if (z) {
                        break;
                    }
                }
            }
            if (bArr != null) {
                cn.fly.commons.cc.a.a(FlySDK.getContext(), bArr, str3, method);
            } else {
                cn.fly.commons.cc.a.a(FlySDK.getContext(), str2, str3, method);
            }
            return true;
        } catch (Throwable th) {
            try {
                h.a().a(6, i, th, str);
                FlyLog.getInstance().d(th);
            } catch (Throwable th2) {
            }
            return false;
        }
    }
}