package cn.fly.commons;

import android.text.TextUtils;
import android.util.Base64;
import cn.fly.FlySDK;
import cn.fly.commons.a;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.SharePrefrenceHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class ad {
    public static final String a = m.a("009)gjUhKgefjfefkgl4fl");
    public static final String b = m.a("0104gj^h>gefj3g[fiFl9flEe'fe");
    public static final String c = m.a("0097gjAhSgefj gKfiXifi");
    public static final String d = m.a("010,gjTh'gefjhk<gCfi-ifi");
    public static final String e = m.a("011ZgjHhUgefjXllCfjglflQkQfe");
    public static final String f = m.a("0316gj^h)gefjEgh_gk]k'fjfiLli,fmLfEfefj%fllBfj!fek9fkff6h_fj5kNfkfh?h");
    public static final String g = m.a("025?gjJh!gefjhhfighghNh(fl'h^fefj_i2fmFefk%fkfmTg'fjfhfejk");
    public static final String h = m.a("0382gj6hEgefj>ghTgk<kSfjfi*liBfm@fCfefjhhfighgh!hFflEhGfefj_i*fmDefk>fkfmUg-fjOkVfkfh0h");
    public static final String i = m.a("014Bhifkghfkfj>if hkDkKfjfk:g4ghfm");
    public static final String j = m.a("018$gjQhYgefjhifkghfkfjYi@fkhk!k.fjPjf>hkQj");
    public static final String k = m.a("030RgjIh8gefjPgh+gk0kLfjfiMli7fm?f:fefjhifkghfkfj,i fkhk.kJfjHk>fkfhZh");
    public static final String l = m.a("0121gj^hBgefjhkhifkGkejhMhk");
    public static final String m = m.a("0221gj?h.gefjhkhifk4kejh(hkfj*k*fkfhVhNhkIkf_fh+l");
    private static final String n = m.a("019Ggj(hBgefjGfllRfjSfek:fkff:hFfjMk(fkfhZh");
    private static final String o = m.a("012Vgj4h;gefjFejfgghi+hk");
    private static AtomicBoolean p = new AtomicBoolean(false);
    private static AtomicBoolean q = new AtomicBoolean(false);
    private static ad r;
    private SharePrefrenceHelper s;

    private ad() {
        if (this.s == null) {
            this.s = new SharePrefrenceHelper(FlySDK.getContext());
            this.s.open("fvv_cms", 1);
        }
    }

    public static String a() {
        return "fvv_cms_1";
    }

    public static synchronized ad b() {
        if (r == null) {
            r = new ad();
        }
        return r;
    }

    public static boolean c() {
        if (FlySDK.getContext() == null) {
            return false;
        }
        if (SharePrefrenceHelper.isMpfFileExist(FlySDK.getContext(), "fvv_cms", 1)) {
            return true;
        }
        boolean zIsMbSpFileExist = SharePrefrenceHelper.isMbSpFileExist(FlySDK.getContext(), "fvv_cms", 1);
        if (zIsMbSpFileExist) {
            return zIsMbSpFileExist;
        }
        return cn.fly.tools.utils.b.a() || cn.fly.tools.utils.b.b();
    }

    public void a(String str, long j2) {
        this.s.putLong(str, Long.valueOf(j2));
    }

    public long b(String str, long j2) {
        return this.s.getLong(str, j2);
    }

    public void a(String str, int i2) {
        this.s.putInt(str, Integer.valueOf(i2));
    }

    public int b(String str, int i2) {
        return this.s.getInt(str, i2);
    }

    public void a(String str, boolean z) {
        this.s.putBoolean(str, Boolean.valueOf(z));
    }

    public boolean b(String str, boolean z) {
        return this.s.getBoolean(str, z);
    }

    public void a(String str, String str2) {
        if (str2 == null) {
            this.s.remove(str);
        } else {
            this.s.putString(str, str2);
        }
    }

    public String b(String str, String str2) {
        return this.s.getString(str, str2);
    }

    public void a(String str, Object obj) {
        this.s.put(str, obj);
    }

    public Object a(String str) {
        return this.s.get(str);
    }

    public void b(String str, Object obj) {
        this.s.put(str, obj);
    }

    public Object c(String str, Object obj) {
        return this.s.get(str, obj);
    }

    public void b(String str) {
        this.s.remove(str);
    }

    public void c(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                str = Base64.encodeToString(Data.AES128Encode(x(), str), 0);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        a(l, str);
    }

    public String d() {
        String strB = b(l, (String) null);
        if (!TextUtils.isEmpty(strB)) {
            try {
                String strX = x();
                return Data.AES128PaddingDecode(strX.getBytes("UTF-8"), Base64.decode(strB, 0));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return strB;
            }
        }
        return strB;
    }

    public void d(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                str = Base64.encodeToString(Data.AES128Encode(x(), str), 0);
                a(m, System.currentTimeMillis());
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        a("key_gfrt", str);
    }

    public String e() {
        String strB = b("key_gfrt", (String) null);
        if (!TextUtils.isEmpty(strB)) {
            try {
                String strX = x();
                return Data.AES128PaddingDecode(strX.getBytes("UTF-8"), Base64.decode(strB, 0));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return strB;
            }
        }
        return strB;
    }

    public void f() {
        c((String) null);
        d((String) null);
    }

    private static String x() {
        return Data.MD5(DH.SyncMtd.getModelForFly());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public HashMap<Long, Long> g() {
        HashMap mapFromJson;
        String string = this.s.getString(n);
        HashMap<Long, Long> map = new HashMap<>();
        if (!TextUtils.isEmpty(string) && (mapFromJson = HashonHelper.fromJson(string)) != null && !mapFromJson.isEmpty()) {
            for (Map.Entry entry : mapFromJson.entrySet()) {
                if (entry != null) {
                    map.put(Long.valueOf(Long.parseLong((String) entry.getKey())), entry.getValue());
                }
            }
        }
        return map;
    }

    public void a(HashMap<Long, Long> map) {
        if (map != null && !map.isEmpty()) {
            HashMap map2 = new HashMap();
            for (Map.Entry<Long, Long> entry : map.entrySet()) {
                if (entry != null) {
                    map2.put(String.valueOf(entry.getKey()), entry.getValue());
                }
            }
            this.s.putString(n, HashonHelper.fromHashMap(map2));
            return;
        }
        this.s.remove(n);
    }

    public HashMap<String, Object> h() {
        String strB = b(o, (String) null);
        if (TextUtils.isEmpty(strB)) {
            return null;
        }
        return HashonHelper.fromJson(strB);
    }

    public void b(HashMap<String, Object> map) {
        a(o, HashonHelper.fromHashMap(map));
    }

    public int i() {
        return b("key_mstrgy", 0);
    }

    public void a(int i2) {
        if (i2 >= 0) {
            a("key_mstrgy", i2);
        }
    }

    public a.c j() {
        return a.c.a(b("key_duid_param_blacklist", (String) null));
    }

    public void a(a.c cVar) {
        String strA;
        if (cVar == null) {
            strA = null;
        } else {
            strA = cVar.a();
        }
        a("key_duid_param_blacklist", strA);
    }

    public a.C0002a k() {
        try {
            String strB = b("key_duid_entity", (String) null);
            if (!TextUtils.isEmpty(strB)) {
                return a.C0002a.a(Data.AES128Decode(DH.SyncMtd.getModelForFly(), Base64.decode(strB, 0)));
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        return null;
    }

    public void a(a.C0002a c0002a) {
        String strA;
        if (c0002a == null) {
            strA = null;
        } else {
            try {
                strA = c0002a.a();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return;
            }
        }
        a("key_duid_entity", Base64.encodeToString(Data.EncodeNoPadding(DH.SyncMtd.getModelForFly(), strA), 0));
    }

    @Deprecated
    public long l() {
        return b("key_a_rmt_tm", 0L);
    }

    @Deprecated
    public int m() {
        return b("key_lch_tms", 0);
    }

    @Deprecated
    public int b(int i2) {
        return b("key_wt_dys", i2);
    }

    public int c(int i2) {
        return b("key_wt_tms", i2);
    }

    @Deprecated
    public boolean n() {
        return b("keyR_drt_lch", false);
    }

    public String o() {
        return b("key_chd_ak", (String) null);
    }

    public void e(String str) {
        a("key_chd_ak", str);
    }

    public String p() {
        return b("key_chd_as", (String) null);
    }

    public void f(String str) {
        a("key_chd_as", str);
    }

    public HashMap<String, HashMap<String, ArrayList<String>>> q() {
        return HashonHelper.fromJson(b("key_chd_busi_dm", (String) null));
    }

    public void c(HashMap<String, HashMap<String, ArrayList<String>>> map) {
        a("key_chd_busi_dm", HashonHelper.fromHashMap(map));
    }

    public HashMap<String, String> r() {
        return HashonHelper.fromJson(b("key_ckd_busi_dm", (String) null));
    }

    public void d(HashMap<String, String> map) {
        a("key_ckd_busi_dm", HashonHelper.fromHashMap(map));
    }

    public ArrayList<String> s() {
        HashMap mapFromJson = HashonHelper.fromJson(b("key_chd_prx_dm", (String) null));
        if (mapFromJson != null && !mapFromJson.isEmpty()) {
            return (ArrayList) mapFromJson.get(m.a("008?ghYf-gj8hi6fkhk6k"));
        }
        return new ArrayList<>();
    }

    public void a(ArrayList<String> arrayList) {
        String strFromObject;
        if (arrayList != null && !arrayList.isEmpty()) {
            strFromObject = HashonHelper.fromObject(arrayList);
        } else {
            strFromObject = null;
        }
        a("key_chd_prx_dm", strFromObject);
    }

    public HashMap<String, Long> t() {
        return HashonHelper.fromJson(b("key_dm_ck_tm", (String) null));
    }

    public void e(HashMap<String, Long> map) {
        a("key_dm_ck_tm", HashonHelper.fromHashMap(map));
    }

    @Deprecated
    public long u() {
        return b("key_fst_lnch_tm", 0L);
    }

    public String v() {
        return b("key_rid", (String) null);
    }

    public void g(String str) {
        a("key_rid", str);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [cn.fly.commons.ad$1] */
    public static void w() {
        if (p.compareAndSet(false, true)) {
            new cn.fly.tools.utils.j(m.a("004Vhnknjmih")) { // from class: cn.fly.commons.ad.1
                @Override // cn.fly.tools.utils.j
                protected void a() {
                    ConcurrentHashMap<String, Object> concurrentHashMapE;
                    synchronized (u.i) {
                        try {
                            u.i.wait(600000L);
                            i.a().a(11);
                            concurrentHashMapE = c.e();
                        } finally {
                        }
                        if (concurrentHashMapE != null && concurrentHashMapE.size() > 0) {
                            i.a().a(12);
                            Object obj = concurrentHashMapE.get("h");
                            Object obj2 = concurrentHashMapE.get("k");
                            Object obj3 = concurrentHashMapE.get("b");
                            Object obj4 = concurrentHashMapE.get("s");
                            Object obj5 = concurrentHashMapE.get("cn");
                            Object obj6 = concurrentHashMapE.get("fn");
                            concurrentHashMapE.clear();
                            d.a(obj, obj2, obj3, obj4, obj5, obj6);
                        }
                    }
                }
            }.start();
        }
        y();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [cn.fly.commons.ad$2] */
    private static void y() {
        if (q.compareAndSet(false, true)) {
            new cn.fly.tools.utils.j("DS-W") { // from class: cn.fly.commons.ad.2
                @Override // cn.fly.tools.utils.j
                protected void a() {
                    synchronized (u.j) {
                        try {
                            u.j.wait();
                            ConcurrentHashMap<String, Object> concurrentHashMapF = c.f();
                            ArrayList arrayList = (ArrayList) concurrentHashMapF.get(m.a("002ik"));
                            concurrentHashMapF.clear();
                            d.a((ArrayList<HashMap<String, Object>>) arrayList, new cn.fly.tools.utils.d<Void>() { // from class: cn.fly.commons.ad.2.1
                                @Override // cn.fly.tools.utils.d
                                public void a(Void r1) {
                                }
                            });
                        } finally {
                        }
                    }
                }
            }.start();
        }
    }
}