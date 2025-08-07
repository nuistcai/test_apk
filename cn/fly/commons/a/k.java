package cn.fly.commons.a;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.ad;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ResHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;

/* loaded from: classes.dex */
public class k extends c {
    private String c;
    private long d;
    private ArrayList<HashMap<String, String>> e;

    public k() {
        super(cn.fly.commons.m.a("002lf"), 0L, cn.fly.commons.m.a("004lf9hkfl"), 300L, a(cn.fly.commons.m.a("002lf"), (Long) 0L));
        this.c = null;
        this.d = 0L;
        this.e = null;
        try {
            File dataCacheFile = ResHelper.getDataCacheFile(FlySDK.getContext(), cn.fly.commons.n.c, true);
            if (!dataCacheFile.getParentFile().exists()) {
                dataCacheFile.getParentFile().mkdirs();
            }
            if (!dataCacheFile.exists()) {
                dataCacheFile.createNewFile();
            }
            this.c = dataCacheFile.getAbsolutePath();
            this.d = ad.b().b(ad.b, -1L);
        } catch (Throwable th) {
        }
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        DH.requester(FlySDK.getContext()).getIAForce(false, false).request(new DH.DHResponder() { // from class: cn.fly.commons.a.k.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) throws Throwable {
                ArrayList arrayListA = k.this.a(dHResponse.getIAForce(new int[0]));
                if (!TextUtils.isEmpty(k.this.c)) {
                    k.this.a((ArrayList<HashMap<String, String>>) arrayListA, k.this.c, Data.MD5(DH.SyncMtd.getModelForFly()));
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - k.this.d >= ((Long) k.this.a(cn.fly.commons.m.a("005lfSgl!fl"), (String) 3600L)).longValue() * 1000 && k.this.b((ArrayList<HashMap<String, String>>) arrayListA)) {
                    k.this.d = ad.b().b(ad.b, -1L);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<HashMap<String, String>> a(ArrayList<HashMap<String, String>> arrayList) throws Throwable {
        ArrayList<HashMap<String, String>> arrayList2;
        if (TextUtils.isEmpty(this.c)) {
            arrayList2 = null;
        } else {
            arrayList2 = a(this.c, Data.MD5(DH.SyncMtd.getModelForFly()));
        }
        if (arrayList2 == null) {
            arrayList2 = new ArrayList<>();
        }
        if (arrayList2.isEmpty()) {
            ad.b().a("key_rcdat", System.currentTimeMillis());
        }
        if (this.e == null || this.e.isEmpty() || cn.fly.commons.c.a) {
            cn.fly.commons.c.a = false;
            this.e = arrayList;
        }
        ArrayList<HashMap<String, String>> arrayList3 = this.e;
        if (arrayList3 != null) {
            for (int i = 0; i < arrayList3.size(); i++) {
                HashMap<String, String> map = arrayList3.get(i);
                String str = map != null ? map.get(cn.fly.commons.m.a("003lWgjgl")) : null;
                if (!TextUtils.isEmpty(str) && a(str)) {
                    HashMap<String, String> mapB = b(arrayList2, str);
                    mapB.put(cn.fly.commons.m.a("003l>gjgl"), str);
                    mapB.put(cn.fly.commons.m.a("004gfFfhOh"), map.get(cn.fly.commons.m.a("004gfFfhOh")));
                    mapB.put(cn.fly.commons.m.a("007_ff%h:flhkfkfmHg"), map.get(cn.fly.commons.m.a("007_ff%h:flhkfkfmHg")));
                    mapB.put(cn.fly.commons.m.a("008VflfiYgk3fkfhTh<hk"), ((mapB.get(cn.fly.commons.m.a("008<flfi4gk-fkfhUh]hk")) == null ? 0 : Integer.parseInt(String.valueOf(mapB.get(cn.fly.commons.m.a("008<flfi4gk-fkfhUh]hk"))))) + m()) + "");
                    if (!a(arrayList2, str)) {
                        arrayList2.add(mapB);
                    }
                }
            }
        }
        return arrayList2;
    }

    private boolean a(final String str) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        DH.requester(FlySDK.getContext()).getMpfof(true, str, 0).request(new DH.DHResponder() { // from class: cn.fly.commons.a.k.2
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                boolean z = false;
                Object mpfof = dHResponse.getMpfof(new int[0]);
                if (mpfof == null) {
                    atomicBoolean.set(false);
                    return;
                }
                ApplicationInfo applicationInfoA = cn.fly.tools.c.a(mpfof, str);
                if (applicationInfoA != null) {
                    boolean z2 = (applicationInfoA.flags & 1) == 0 && (applicationInfoA.flags & 128) == 0;
                    boolean z3 = (applicationInfoA.flags & 2097152) == 0;
                    AtomicBoolean atomicBoolean2 = atomicBoolean;
                    if (z2 && z3) {
                        z = true;
                    }
                    atomicBoolean2.set(z);
                }
            }
        });
        return atomicBoolean.get();
    }

    private boolean a(ArrayList<HashMap<String, String>> arrayList, String str) {
        Iterator<HashMap<String, String>> it = arrayList.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().get(cn.fly.commons.m.a("003lVgjgl")))) {
                return true;
            }
        }
        return false;
    }

    private HashMap<String, String> b(ArrayList<HashMap<String, String>> arrayList, String str) {
        Iterator<HashMap<String, String>> it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap<String, String> next = it.next();
            if (str.equals(next.get(cn.fly.commons.m.a("003l%gjgl")))) {
                return next;
            }
        }
        return new HashMap<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(ArrayList<HashMap<String, String>> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return false;
        }
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put(cn.fly.commons.m.a("008Lfl%heEfmflfe=fk"), Long.valueOf(ad.b().b("key_rcdat", -1L)));
            a(0L, "PRTMT", arrayList, map, false);
        } catch (Throwable th) {
        }
        ad.b().a(ad.b, System.currentTimeMillis());
        return n();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ArrayList<HashMap<String, String>> arrayList, String str, String str2) throws IOException {
        ResHelper.writeToFileNoCompress(new File(str), a(str2, arrayList));
    }

    private ArrayList<HashMap<String, String>> a(String str, String str2) {
        return a(str2, ResHelper.readFromFileNoCompress(new File(str)));
    }

    private static byte[] a(String str, ArrayList<HashMap<String, String>> arrayList) {
        new HashonHelper();
        String strFromObject = HashonHelper.fromObject(arrayList);
        try {
            return Data.AES128Encode(str, strFromObject);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return strFromObject.getBytes();
        }
    }

    private static ArrayList<HashMap<String, String>> a(String str, byte[] bArr) {
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    return b(Data.AES128PaddingDecode(str.getBytes("UTF-8"), bArr));
                }
            } catch (Throwable th) {
                FlyLog.getInstance().w(th);
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    private static ArrayList<HashMap<String, String>> b(String str) {
        try {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            if (TextUtils.isEmpty(str)) {
                return arrayList;
            }
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(HashonHelper.fromJson(jSONArray.getJSONObject(i).toString()));
            }
            return arrayList;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return new ArrayList<>();
        }
    }

    private boolean n() {
        try {
            File file = new File(this.c);
            file.delete();
            file.createNewFile();
            return true;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return false;
        }
    }
}