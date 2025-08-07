package cn.fly.verify;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.utils.SharePrefrenceHelper;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class e {
    private static volatile aj b;
    private static volatile long c;
    private static volatile long f;
    private static SharePrefrenceHelper g;
    private static String h;
    private static String i;
    private static String j;
    private static Integer k;
    private static Integer l;
    private static Integer m;
    private static String n;
    private static volatile int d = -1;
    private static volatile int e = -1;
    public static final AtomicReference<String> a = new AtomicReference<>();

    static {
        try {
            g = new SharePrefrenceHelper(FlySDK.getContext());
            g.open("config_file");
        } catch (Throwable th) {
            v.a(th);
        }
    }

    public static aj a() {
        if (System.currentTimeMillis() > c) {
            v.a("memory config expire");
            b = null;
        }
        return b;
    }

    public static aj a(u uVar) {
        return a(uVar, true);
    }

    public static synchronized aj a(u uVar, boolean z) {
        aj ajVarA;
        ajVarA = a();
        if (ajVarA == null) {
            ajVarA = d();
            if (ajVarA != null && z) {
                if (uVar != null) {
                    uVar.a((String) null, (String) null, "use_ca");
                }
                a(0);
            }
        } else if (z) {
            if (uVar != null) {
                uVar.a((String) null, (String) null, "use_cdn");
            }
            a(2);
        }
        return ajVarA;
    }

    public static void a(int i2) {
        d = i2;
    }

    public static void a(long j2) {
        f = j2;
    }

    public static void a(aj ajVar) {
        b = ajVar;
        g.put("config_key", ajVar);
        long jCurrentTimeMillis = System.currentTimeMillis() + 600000;
        g.putLong("config_expire_time_key", Long.valueOf(jCurrentTimeMillis));
        c = jCurrentTimeMillis;
    }

    public static void a(boolean z) {
        g.putBoolean("unknownTry", Boolean.valueOf(z));
    }

    public static boolean a(String str) {
        ArrayList<String> arrayList;
        aj ajVar = (aj) g.get("config_key");
        if (ajVar == null || (arrayList = ajVar.a(1).m) == null) {
            return false;
        }
        return arrayList.contains(str);
    }

    public static String b() {
        aj ajVar = (aj) g.get("config_key");
        return ajVar == null ? "LphSZLqaUeFdyaQq" : ajVar.a(1).g;
    }

    public static void b(int i2) {
        e = i2;
    }

    public static void b(String str) {
        g.putString("log_key", str);
    }

    public static void c(String str) {
        h = str;
    }

    public static boolean c() {
        return g.getBoolean("unknownTry", false);
    }

    public static aj d() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = g.getLong("config_expire_time_key");
        SharePrefrenceHelper sharePrefrenceHelper = g;
        if (jCurrentTimeMillis <= j2) {
            return (aj) sharePrefrenceHelper.get("config_key");
        }
        sharePrefrenceHelper.remove("config_key");
        v.a("file config expire");
        return null;
    }

    public static void d(String str) {
        i = str;
    }

    public static int e() {
        aj ajVar = (aj) g.get("config_key");
        if (ajVar != null) {
            return ajVar.a(1).i;
        }
        return 4000;
    }

    public static void e(String str) {
        j = str;
    }

    public static int f() {
        aj ajVar = (aj) g.get("config_key");
        if (ajVar != null) {
            return ajVar.a(1).h;
        }
        return 4000;
    }

    public static int g() {
        aj ajVarA = a();
        if (ajVarA == null) {
            ajVarA = d();
        }
        if (ajVarA == null || ajVarA.a(1) == null) {
            return 0;
        }
        return ajVarA.a(1).k;
    }

    public static int h() {
        aj ajVarA = a();
        if (ajVarA == null) {
            ajVarA = d();
        }
        if (ajVarA == null || ajVarA.a(1) == null) {
            return 0;
        }
        return ajVarA.a(1).l;
    }

    public static ArrayList<String> i() {
        aj ajVar = (aj) g.get("config_key");
        return ajVar != null ? ajVar.a(1).m : new ArrayList<>();
    }

    public static String j() {
        return g.getString("log_key");
    }

    public static int k() {
        return d;
    }

    public static int l() {
        int i2 = e;
        e = -1;
        return i2;
    }

    public static long m() {
        long j2 = f;
        f = 0L;
        return j2;
    }

    public static String n() {
        return h;
    }

    public static String o() {
        return i;
    }

    public static String p() {
        return j;
    }

    public static int q() {
        if (u()) {
            return 0;
        }
        if (k == null) {
            k = Integer.valueOf(g.getInt("subIdEnable", 1));
        }
        return k.intValue();
    }

    public static int r() {
        if (u()) {
            return 0;
        }
        if (l == null) {
            l = Integer.valueOf(g.getInt("subIdsEnable", 0));
        }
        return l.intValue();
    }

    public static int s() {
        if (u()) {
            return 0;
        }
        if (m == null) {
            m = Integer.valueOf(g.getInt("slotsEnable", 1));
        }
        return m.intValue();
    }

    public static String[] t() {
        String[] strArrSplit;
        if (TextUtils.isEmpty(n)) {
            n = g.getString("factoryBlst", null);
        }
        if (n == null || (strArrSplit = n.split(",")) == null || strArrSplit.length <= 0) {
            return null;
        }
        return strArrSplit;
    }

    private static boolean u() {
        String[] strArrT = t();
        if (strArrT != null) {
            String strI = cn.fly.verify.util.e.i();
            for (String str : strArrT) {
                if (strI != null && strI.equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }
        return false;
    }
}