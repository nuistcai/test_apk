package cn.fly.tools.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;
import cn.fly.commons.CSCenter;
import cn.fly.commons.aa;
import cn.fly.commons.w;
import cn.fly.commons.z;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.FileUtils;
import cn.fly.tools.utils.FlyPersistence;
import com.alibaba.fastjson.asm.Opcodes;
import java.io.File;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class i implements cn.fly.tools.b.a {
    private Context d;
    private b e;
    private volatile Set<String> f = new HashSet();
    private ConcurrentHashMap<String, Object> a = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> b = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Long> c = new ConcurrentHashMap<>();

    public i(Context context) {
        this.d = context;
        this.e = b.a(context);
        cn.fly.tools.utils.g.a();
    }

    @Override // cn.fly.tools.b.a
    public boolean a() {
        return ((Boolean) b("ird", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                if (bool == null || !bool.booleanValue()) {
                    return 180000L;
                }
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() {
                return Boolean.valueOf(i.this.e.a());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean b() {
        return ((Boolean) b("cx0", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.12
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                if (bool == null || !bool.booleanValue()) {
                    return 180000L;
                }
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() {
                return Boolean.valueOf(i.this.e.G());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean c() {
        return ((Boolean) b("pd0", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.23
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() {
                return Boolean.valueOf(i.this.e.H());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean d() {
        return ((Boolean) a("dee", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.34
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() {
                return Boolean.valueOf(i.this.e.M());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean e() {
        return this.e.L();
    }

    @Override // cn.fly.tools.b.a
    public boolean f() {
        return ((Boolean) a("ua0", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.45
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() {
                return Boolean.valueOf(i.this.e.K());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean g() {
        return ((Boolean) a("dee1", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.56
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() {
                return Boolean.valueOf(i.this.e.J());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean h() {
        return ((Boolean) a("uee", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.67
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() {
                return Boolean.valueOf(i.this.e.I());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean i() {
        return ((Boolean) a("wpy", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.69
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() {
                return Boolean.valueOf(i.this.e.N());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public String j() {
        return (String) b("agi", new a<String>(null) { // from class: cn.fly.tools.b.i.70
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.t();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String a(boolean z) {
        HashMap<String, Object> mapG = g(z);
        if (mapG != null) {
            return (String) mapG.get("ssmt");
        }
        return null;
    }

    @Override // cn.fly.tools.b.a
    public String b(boolean z) {
        HashMap<String, Object> mapG = g(z);
        if (mapG != null) {
            return (String) mapG.get("bsmt");
        }
        return null;
    }

    @Override // cn.fly.tools.b.a
    public String c(boolean z) {
        return b(z, true);
    }

    @Override // cn.fly.tools.b.a
    public String d(boolean z) {
        return b(z, cn.fly.commons.e.i());
    }

    private String b(boolean z, boolean z2) {
        return (String) a("car", new a<String>(null) { // from class: cn.fly.tools.b.i.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.l();
            }
        }, z, z2);
    }

    @Override // cn.fly.tools.b.a
    public String e(boolean z) {
        return c(z, true);
    }

    @Override // cn.fly.tools.b.a
    public String f(boolean z) {
        return c(z, cn.fly.commons.e.i());
    }

    private String c(boolean z, boolean z2) {
        return (String) a("cne", new a<String>(null) { // from class: cn.fly.tools.b.i.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.m();
            }
        }, z, z2);
    }

    @Override // cn.fly.tools.b.a
    public String k() {
        return (String) b("mvn", new a<String>(null) { // from class: cn.fly.tools.b.i.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.F();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String l() {
        if (CSCenter.getInstance().isSystemInfoAvailable()) {
            return k();
        }
        String strB = cn.fly.tools.utils.g.a().b("mvn", "");
        if (TextUtils.isEmpty(strB)) {
            strB = CSCenter.getInstance().getROMVersion();
        }
        return TextUtils.isEmpty(strB) ? "" : strB;
    }

    @Override // cn.fly.tools.b.a
    public String m() {
        return (String) b("mol", new a<String>(null) { // from class: cn.fly.tools.b.i.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.b();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String n() {
        if (CSCenter.getInstance().isModelAvailable()) {
            return m();
        }
        String strB = cn.fly.tools.utils.g.a().b("mol", "");
        if (TextUtils.isEmpty(strB)) {
            strB = CSCenter.getInstance().getModel();
        }
        return TextUtils.isEmpty(strB) ? "" : strB;
    }

    @Override // cn.fly.tools.b.a
    public String o() {
        return (String) b("mar", new a<String>(null) { // from class: cn.fly.tools.b.i.6
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.c();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String p() {
        if (CSCenter.getInstance().isManufacturerAvailable()) {
            return o();
        }
        String strB = cn.fly.tools.utils.g.a().b("mar", "");
        if (TextUtils.isEmpty(strB)) {
            strB = CSCenter.getInstance().getManufacturer();
        }
        return TextUtils.isEmpty(strB) ? "" : strB;
    }

    @Override // cn.fly.tools.b.a
    public String q() {
        return (String) b("brd", new a<String>(null) { // from class: cn.fly.tools.b.i.7
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.V();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String r() {
        if (CSCenter.getInstance().isManufacturerAvailable()) {
            return q();
        }
        String strA = cn.fly.tools.utils.g.a().a("brd");
        if (TextUtils.isEmpty(strA)) {
            strA = CSCenter.getInstance().getBrand();
        }
        return TextUtils.isEmpty(strA) ? "" : strA;
    }

    @Override // cn.fly.tools.b.a
    public String s() {
        return (String) b("dte", new a<String>(null) { // from class: cn.fly.tools.b.i.8
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.v();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public Object t() {
        return b("gtecloc", new a<Object>(null) { // from class: cn.fly.tools.b.i.9
            @Override // cn.fly.tools.b.i.a
            protected Object b() throws Throwable {
                return i.this.e.an();
            }

            @Override // cn.fly.tools.b.i.a
            protected long a(Object obj) {
                return 180000L;
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public ArrayList<HashMap<String, Object>> u() {
        return (ArrayList) b("bsnbcl", new a<ArrayList<HashMap<String, Object>>>(null) { // from class: cn.fly.tools.b.i.10
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(ArrayList<HashMap<String, Object>> arrayList) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ArrayList<HashMap<String, Object>> b() throws Throwable {
                return i.this.e.u();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public HashMap<String, Object> v() {
        return g(false);
    }

    @Override // cn.fly.tools.b.a
    public HashMap<String, Object> g(boolean z) {
        return (HashMap) b("crtwfo", new a<HashMap<String, Object>>(null) { // from class: cn.fly.tools.b.i.11
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(HashMap<String, Object> map) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public HashMap<String, Object> b() throws Throwable {
                return i.this.e.x();
            }
        }, z);
    }

    @Override // cn.fly.tools.b.a
    public int w() {
        return ((Integer) b("ovit", new a<Integer>(-1) { // from class: cn.fly.tools.b.i.13
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Integer num) {
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Integer b() throws Throwable {
                return Integer.valueOf(i.this.e.f());
            }
        })).intValue();
    }

    @Override // cn.fly.tools.b.a
    public int x() {
        if (CSCenter.getInstance().isSystemInfoAvailable()) {
            return w();
        }
        int iA = cn.fly.tools.utils.g.a().a("ovit", 0);
        if (iA >= 1) {
            return iA;
        }
        int systemVersionCode = CSCenter.getInstance().getSystemVersionCode();
        if (systemVersionCode < 1) {
            return 0;
        }
        return systemVersionCode;
    }

    @Override // cn.fly.tools.b.a
    public String y() {
        return (String) b("ovne", new a<String>(null) { // from class: cn.fly.tools.b.i.14
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.g();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String z() {
        if (CSCenter.getInstance().isSystemInfoAvailable()) {
            return y();
        }
        String strB = cn.fly.tools.utils.g.a().b("ovne", "");
        if (TextUtils.isEmpty(strB)) {
            strB = CSCenter.getInstance().getSystemVersionName();
        }
        return TextUtils.isEmpty(strB) ? "" : strB;
    }

    @Override // cn.fly.tools.b.a
    public String A() {
        return (String) b("ole", new a<String>(null) { // from class: cn.fly.tools.b.i.15
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 3600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.h();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String B() {
        return (String) b("ocy", new a<String>(null) { // from class: cn.fly.tools.b.i.16
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 3600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.j();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public HashMap<String, Object> C() {
        return (HashMap) b("cio0", new a<HashMap<String, Object>>(null) { // from class: cn.fly.tools.b.i.17
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(HashMap<String, Object> map) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public HashMap<String, Object> b() throws Throwable {
                return i.this.e.A();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public ArrayList<ArrayList<String>> D() {
        return (ArrayList) b("tdio", new a<ArrayList<ArrayList<String>>>(null) { // from class: cn.fly.tools.b.i.18
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(ArrayList<ArrayList<String>> arrayList) {
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ArrayList<ArrayList<String>> b() throws Throwable {
                return i.this.e.B();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String E() {
        return (String) b("qkl", new a<String>(null) { // from class: cn.fly.tools.b.i.19
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                if ("0".equals(str)) {
                    return 86400000L;
                }
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.C();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public HashMap<String, HashMap<String, Long>> F() {
        return (HashMap) b("siio", new a<HashMap<String, HashMap<String, Long>>>(null) { // from class: cn.fly.tools.b.i.20
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(HashMap<String, HashMap<String, Long>> map) {
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public HashMap<String, HashMap<String, Long>> b() throws Throwable {
                return i.this.e.D();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public HashMap<String, Long> G() {
        return (HashMap) b("meio", new a<HashMap<String, Long>>(null) { // from class: cn.fly.tools.b.i.21
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(HashMap<String, Long> map) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public HashMap<String, Long> b() throws Throwable {
                return i.this.e.E();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String H() {
        return (String) b("ale", new a<String>(null) { // from class: cn.fly.tools.b.i.22
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.i();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String I() {
        return (String) b("sse", new a<String>(null) { // from class: cn.fly.tools.b.i.24
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.k();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String i(boolean z) {
        return d(z, cn.fly.commons.e.i());
    }

    @Override // cn.fly.tools.b.a
    public String h(boolean z) {
        return d(z, true);
    }

    private String d(final boolean z, boolean z2) {
        String str = (String) a("nte", new a<String>(null) { // from class: cn.fly.tools.b.i.25
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str2) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.a(z);
            }
        }, z, z2);
        if (str == null && !z2) {
            return "forbid";
        }
        return str;
    }

    @Override // cn.fly.tools.b.a
    public String J() {
        return m(false);
    }

    private String m(boolean z) {
        String lowerCase = i(z).toLowerCase();
        if (TextUtils.isEmpty(lowerCase) || w.b("004d3cj.de").equals(lowerCase)) {
            return w.b("004dEcj-de");
        }
        if (lowerCase.startsWith(w.b("002Eghdi")) || lowerCase.startsWith(w.b("002(gkdi")) || lowerCase.startsWith(w.b("002Kiedi")) || lowerCase.startsWith(w.b("002Vgddi"))) {
            return w.b("004beff");
        }
        if (lowerCase.startsWith(w.b("004@efchdech")) || "forbid".equals(lowerCase)) {
            return w.b("004Cefchdech");
        }
        return w.b("005*cjShgeFci");
    }

    @Override // cn.fly.tools.b.a
    public String K() {
        String lowerCase = i(false).toLowerCase();
        if (TextUtils.isEmpty(lowerCase) || w.b("004d8cj%de").equals(lowerCase)) {
            return w.b("004dCcj<de");
        }
        if (lowerCase.startsWith(w.b("0047efchdech"))) {
            return w.b("004 efchdech");
        }
        if (lowerCase.startsWith(w.b("002,ghdi"))) {
            return w.b("002<ghdi");
        }
        if (lowerCase.startsWith(w.b("002*gkdi"))) {
            return w.b("0022gkdi");
        }
        if (lowerCase.startsWith(w.b("002Viedi"))) {
            return w.b("0027iedi");
        }
        if (lowerCase.startsWith(w.b("002(gddi"))) {
            return w.b("0020gddi");
        }
        if (lowerCase.startsWith(w.b("009@eeHf;cfBeh>cjcj%hg"))) {
            return w.b("009Pee8fUcfQehKcjcj@hg");
        }
        return lowerCase;
    }

    @Override // cn.fly.tools.b.a
    public boolean j(boolean z) {
        String strM = m(z);
        return w.b("004Zefchdech").equals(strM) || w.b("004beff").equals(strM);
    }

    @Override // cn.fly.tools.b.a
    public int L() {
        return n(true);
    }

    @Override // cn.fly.tools.b.a
    public int M() {
        return n(cn.fly.commons.e.i());
    }

    private int n(boolean z) {
        return ((Integer) a("dtnttp", (a) new a<Integer>(-1) { // from class: cn.fly.tools.b.i.26
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Integer num) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Integer b() throws Throwable {
                return Integer.valueOf(i.this.e.U());
            }
        }, false, z)).intValue();
    }

    @Override // cn.fly.tools.b.a
    public String N() {
        return (String) b("tize", new a<String>(null) { // from class: cn.fly.tools.b.i.27
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 3600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.P();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String O() {
        return (String) b("flvr", new a<String>(null) { // from class: cn.fly.tools.b.i.28
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 3600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.Q();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String P() {
        return (String) b("babd", new a<String>(null) { // from class: cn.fly.tools.b.i.29
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.R();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String Q() {
        return (String) b("bfsp", new a<String>(null) { // from class: cn.fly.tools.b.i.30
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.S();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String R() {
        return (String) b("bopm", new a<String>(null) { // from class: cn.fly.tools.b.i.31
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.T();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String S() {
        return o(true);
    }

    @Override // cn.fly.tools.b.a
    public String T() {
        return o(cn.fly.commons.e.i());
    }

    private String o(boolean z) {
        if (CSCenter.getInstance().isIpAddressEnable()) {
            if (z) {
                try {
                    Enumeration<NetworkInterface> enumerationA = e.a(this.d).a();
                    while (enumerationA.hasMoreElements()) {
                        Enumeration<InetAddress> enumerationA2 = e.a(this.d).a(enumerationA.nextElement());
                        while (enumerationA2.hasMoreElements()) {
                            InetAddress inetAddressNextElement = enumerationA2.nextElement();
                            if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                                return inetAddressNextElement.getHostAddress();
                            }
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    FlyLog.getInstance().w(th);
                    return null;
                }
            }
            return "0.0.0.0";
        }
        return CSCenter.getInstance().getIpAddress();
    }

    @Override // cn.fly.tools.b.a
    public ArrayList<HashMap<String, String>> a(boolean z, boolean z2) {
        synchronized ("giafce") {
            ArrayList<HashMap<String, String>> arrayListP = p(z2);
            if (z) {
                return this.e.a(arrayListP, 0);
            }
            return this.e.a(arrayListP, 1);
        }
    }

    @Override // cn.fly.tools.b.a
    public ArrayList<HashMap<String, String>> U() {
        return p(false);
    }

    private ArrayList<HashMap<String, String>> p(boolean z) {
        ArrayList<HashMap<String, String>> arrayList;
        synchronized ("gal") {
            arrayList = (ArrayList) b("gal", new a<ArrayList<HashMap<String, String>>>(null) { // from class: cn.fly.tools.b.i.32
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // cn.fly.tools.b.i.a
                public long a(ArrayList<HashMap<String, String>> arrayList2) {
                    Calendar calendar = Calendar.getInstance();
                    long jA = i.this.a(calendar) - calendar.getTimeInMillis();
                    if (jA > 0) {
                        return jA;
                    }
                    return 86400000L;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // cn.fly.tools.b.i.a
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public ArrayList<HashMap<String, String>> b() throws Throwable {
                    return i.this.e.r();
                }
            }, z);
        }
        return arrayList;
    }

    @Override // cn.fly.tools.b.a
    public ArrayList<HashMap<String, String>> V() {
        ArrayList<HashMap<String, String>> arrayListA;
        synchronized ("gsl") {
            arrayListA = this.e.a(p(false), 2);
        }
        return arrayListA;
    }

    @Override // cn.fly.tools.b.a
    public Location a(int i, int i2, boolean z) {
        List listA = a(i, i2, z, false);
        if (listA != null && !listA.isEmpty()) {
            return (Location) listA.get(listA.size() - 1);
        }
        return null;
    }

    @Override // cn.fly.tools.b.a
    public String a(String str) {
        return this.e.a(str);
    }

    @Override // cn.fly.tools.b.a
    public String W() {
        return (String) b("deky", new a<String>(null) { // from class: cn.fly.tools.b.i.33
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.b(false);
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String k(boolean z) {
        return this.e.b(z);
    }

    @Override // cn.fly.tools.b.a
    public boolean b(String str) {
        return this.e.e(str);
    }

    @Override // cn.fly.tools.b.a
    public String X() {
        return (String) b("scph", new a<String>(null) { // from class: cn.fly.tools.b.i.35
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.s();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String Y() {
        return this.e.b(Z());
    }

    @Override // cn.fly.tools.b.a
    public String c(String str) {
        return this.e.b(str);
    }

    @Override // cn.fly.tools.b.a
    public String Z() {
        return (String) b("pne", new a<String>(null) { // from class: cn.fly.tools.b.i.36
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.n();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String aa() {
        return this.e.o();
    }

    @Override // cn.fly.tools.b.a
    public String d(String str) {
        return this.e.c(str);
    }

    @Override // cn.fly.tools.b.a
    public int ab() {
        return this.e.p();
    }

    @Override // cn.fly.tools.b.a
    public String ac() {
        return this.e.q();
    }

    @Override // cn.fly.tools.b.a
    public boolean ad() {
        return ((Boolean) a("imp", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.37
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() throws Throwable {
                return Boolean.valueOf(i.this.e.W());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public String ae() {
        return (String) a("cpne", new a<String>(null) { // from class: cn.fly.tools.b.i.38
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.X();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public boolean af() {
        return z.a();
    }

    @Override // cn.fly.tools.b.a
    public boolean e(String str) {
        try {
            return this.e.d(str);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return false;
        }
    }

    @Override // cn.fly.tools.b.a
    public Context ag() {
        return (Context) a("galct", new a<Context>(null) { // from class: cn.fly.tools.b.i.39
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Context b() throws Throwable {
                if (i.this.d != null) {
                    return i.this.d;
                }
                Context contextW = b.w();
                if (contextW != null) {
                    i.this.d = contextW;
                }
                return contextW;
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public List<ResolveInfo> a(Intent intent, int i) {
        return e.a(this.d).a(intent, i);
    }

    @Override // cn.fly.tools.b.a
    public ResolveInfo b(Intent intent, int i) {
        return e.a(this.d).b(intent, i);
    }

    @Override // cn.fly.tools.b.a
    public PackageInfo a(boolean z, int i, String str, int i2) {
        return b(z, i, str, i2, true);
    }

    private PackageInfo a(boolean z, final int i, final String str, final int i2, final boolean z2) {
        final boolean zA = cn.fly.tools.c.a("1009", str);
        return (PackageInfo) b("gpi-" + i + "-" + str + "-" + i2 + "-" + z2, new a<PackageInfo>(null) { // from class: cn.fly.tools.b.i.40
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(PackageInfo packageInfo) {
                if (zA) {
                    return i;
                }
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public PackageInfo b() throws Throwable {
                return (PackageInfo) e.a(i.this.d).a(str, i2, z2);
            }
        }, z || a(zA, new StringBuilder().append("gpi-").append(i).append("-").append(str).append("-").append(i2).append("-").append(z2).toString(), str));
    }

    @Override // cn.fly.tools.b.a
    public String ah() {
        return this.e.d();
    }

    @Override // cn.fly.tools.b.a
    public String ai() {
        return this.e.e();
    }

    @Override // cn.fly.tools.b.a
    public long aj() {
        return this.e.Y();
    }

    @Override // cn.fly.tools.b.a
    public String ak() {
        return (String) b("dvcnm", new a<String>(null) { // from class: cn.fly.tools.b.i.41
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 3600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.Z();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String al() {
        return (String) b("cgrp", new a<String>(null) { // from class: cn.fly.tools.b.i.42
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.aa();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String am() {
        return (String) b("cinfo", new a<String>(null) { // from class: cn.fly.tools.b.i.43
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.ab();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String an() {
        if (CSCenter.getInstance().isOaidEnable()) {
            String str = null;
            if (cn.fly.commons.e.a()) {
                return (String) b("odmt", new a<String>(str) { // from class: cn.fly.tools.b.i.44
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // cn.fly.tools.b.i.a
                    public long a(String str2) {
                        if (TextUtils.isEmpty(str2)) {
                            return -1L;
                        }
                        return 604800000L;
                    }

                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // cn.fly.tools.b.i.a
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public String b() throws Throwable {
                        return i.this.e.ac();
                    }
                });
            }
            return null;
        }
        return CSCenter.getInstance().getOaid();
    }

    @Override // cn.fly.tools.b.a
    public String ao() {
        String strAn = c.a(this.d).d().an();
        if (!TextUtils.isEmpty(strAn)) {
            try {
                return Base64.encodeToString(Data.AES128Encode(Data.MD5(DH.SyncMtd.getManufacturerForFly()), strAn), 2);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return strAn;
    }

    @Override // cn.fly.tools.b.a
    public HashMap<String, Object> ap() {
        return (HashMap) b("alldmt", new a<HashMap<String, Object>>(null) { // from class: cn.fly.tools.b.i.46
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(HashMap<String, Object> map) {
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public HashMap<String, Object> b() throws Throwable {
                return i.this.e.ae();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public ApplicationInfo aq() {
        final boolean zA = cn.fly.tools.c.a("1009", this.d.getPackageName());
        return (ApplicationInfo) b("gtaif", new a<ApplicationInfo>(null) { // from class: cn.fly.tools.b.i.47
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(ApplicationInfo applicationInfo) {
                if (zA) {
                    return 0L;
                }
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ApplicationInfo b() throws Throwable {
                return e.a(i.this.d).d();
            }
        }, a(zA, "gtaif", Z()));
    }

    @Override // cn.fly.tools.b.a
    public ArrayList<HashMap<String, Object>> ar() {
        return (ArrayList) b("gtwflok", new a<ArrayList<HashMap<String, Object>>>(null) { // from class: cn.fly.tools.b.i.48
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(ArrayList<HashMap<String, Object>> arrayList) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ArrayList<HashMap<String, Object>> b() throws Throwable {
                Boolean bool;
                if (!cn.fly.commons.e.d() || !i.this.e(w.b("036cd(cbcicjchcbckFieNcicechehehchcjYd)ckdcejecdfhcfhcgfeddfbddcgdkebecebfh")) || !i.this.e(w.b("036cd7cbcicjchcbckZieKcicechehehchcjRd ckecdcdcfhdkdkcgfeddfbddcgdkebecebfh"))) {
                    return null;
                }
                LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
                i.this.e.a((BlockingQueue<Boolean>) linkedBlockingQueue);
                i.this.e.z();
                try {
                    bool = (Boolean) linkedBlockingQueue.poll(20000L, TimeUnit.MILLISECONDS);
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                    bool = null;
                }
                if (bool == null || !bool.booleanValue()) {
                    return null;
                }
                return i.this.e.y();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public ApplicationInfo a(String str, int i) {
        return a(false, str, i);
    }

    @Override // cn.fly.tools.b.a
    public ApplicationInfo a(boolean z, final String str, final int i) {
        final boolean zA = cn.fly.tools.c.a("1009", str);
        return (ApplicationInfo) b("gtaiffce-" + str + "-" + i, new a<ApplicationInfo>(null) { // from class: cn.fly.tools.b.i.49
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(ApplicationInfo applicationInfo) {
                if (zA) {
                    return 0L;
                }
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ApplicationInfo b() throws Throwable {
                return e.a(i.this.d).a(str, i);
            }
        }, z || a(zA, new StringBuilder().append("gtaiffce-").append(str).append("-").append(i).toString(), str));
    }

    @Override // cn.fly.tools.b.a
    public long as() {
        return ((Long) b("gtbdt", new a<Long>(0L) { // from class: cn.fly.tools.b.i.50
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Long l) {
                return 3600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Long b() throws Throwable {
                return Long.valueOf(i.this.e.af());
            }
        })).longValue();
    }

    @Override // cn.fly.tools.b.a
    public double at() {
        return ((Double) b("gtscnin", new a<Double>(Double.valueOf(0.0d)) { // from class: cn.fly.tools.b.i.51
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Double d) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Double b() throws Throwable {
                return Double.valueOf(i.this.e.ag());
            }
        })).doubleValue();
    }

    @Override // cn.fly.tools.b.a
    public int au() {
        return ((Integer) b("gtscnppi", new a<Integer>(0) { // from class: cn.fly.tools.b.i.52
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Integer num) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Integer b() throws Throwable {
                return Integer.valueOf(i.this.e.ah());
            }
        })).intValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean av() {
        return ((Boolean) b("ishmos", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.53
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                return 3600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() throws Throwable {
                return Boolean.valueOf(i.this.e.ai());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public String aw() {
        return (String) b("gthmosv", new a<String>(null) { // from class: cn.fly.tools.b.i.54
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 3600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.aj();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String ax() {
        return (String) b("gthmosdtlv", new a<String>(null) { // from class: cn.fly.tools.b.i.55
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 3600000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.ak();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public int ay() {
        return ((Integer) b("hmpmst", new a<Integer>(-1) { // from class: cn.fly.tools.b.i.57
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Integer num) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Integer b() throws Throwable {
                return Integer.valueOf(i.this.e.al());
            }
        })).intValue();
    }

    @Override // cn.fly.tools.b.a
    public int az() {
        return ((Integer) b("hmepmst", new a<Integer>(-1) { // from class: cn.fly.tools.b.i.58
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Integer num) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Integer b() throws Throwable {
                return Integer.valueOf(i.this.e.am());
            }
        })).intValue();
    }

    @Override // cn.fly.tools.b.a
    public String aA() {
        return (String) b("gtinnerlangmt", new a<String>(null) { // from class: cn.fly.tools.b.i.59
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return i.this.e.ao();
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public int aB() {
        return ((Integer) b("gtgramgendt", new a<Integer>(0) { // from class: cn.fly.tools.b.i.60
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Integer num) {
                return 86400000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Integer b() throws Throwable {
                return Integer.valueOf(i.this.e.ap());
            }
        })).intValue();
    }

    @Override // cn.fly.tools.b.a
    public Object b(boolean z, int i, String str, int i2) {
        return b(z, i, str, i2, false);
    }

    @Override // cn.fly.tools.b.a
    public boolean aC() {
        return ((Boolean) a("debbing", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.61
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                return 60000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() throws Throwable {
                return Boolean.valueOf(i.this.e.as());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public List a(final int i, final int i2, final boolean z, final boolean z2) {
        return (List) b("gtelcmefce-" + i + "-" + i2 + "-" + z, new a<List<Location>>(null) { // from class: cn.fly.tools.b.i.62
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(List<Location> list) {
                return 180000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public List<Location> b() throws Throwable {
                return i.this.e.a(i, i2, z, z2);
            }
        }, z2);
    }

    @Override // cn.fly.tools.b.a
    public ArrayList<HashMap<String, Object>> aD() {
        return (ArrayList) b("gteacifo", new a<ArrayList<HashMap<String, Object>>>(null) { // from class: cn.fly.tools.b.i.63
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ArrayList<HashMap<String, Object>> b() throws Throwable {
                return i.this.e.ar();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(ArrayList<HashMap<String, Object>> arrayList) {
                return 180000L;
            }
        });
    }

    @Override // cn.fly.tools.b.a
    public String l(boolean z) {
        return (String) b("gtdm", new a<String>(null) { // from class: cn.fly.tools.b.i.64
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(String str) {
                return 604800000L;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public String b() throws Throwable {
                return aa.a().g();
            }
        }, z);
    }

    @Override // cn.fly.tools.b.a
    public long f(final String str) {
        return ((Long) b("gtlstact-" + str, new a<Long>(-1L) { // from class: cn.fly.tools.b.i.65
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Long b() throws Throwable {
                return Long.valueOf(FileUtils.getLATime(str));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Long l) {
                return 604800000L;
            }
        })).longValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean aE() {
        return ((Boolean) a("gpsavlbmt", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.66
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() throws Throwable {
                return Boolean.valueOf(i.this.e.at());
            }
        })).booleanValue();
    }

    @Override // cn.fly.tools.b.a
    public boolean aF() {
        return ((Boolean) a("isaut", new a<Boolean>(false) { // from class: cn.fly.tools.b.i.68
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean b() throws Throwable {
                return Boolean.valueOf(i.this.e.ad());
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // cn.fly.tools.b.i.a
            public long a(Boolean bool) {
                return 600000L;
            }
        })).booleanValue();
    }

    private PackageInfo b(boolean z, int i, String str, int i2, boolean z2) {
        if (str.equals(DH.SyncMtd.getPackageName())) {
            int i3 = (i2 == 0 || i2 == 1 || i2 == 128 || i2 == 64) ? Opcodes.INSTANCEOF : i2;
            PackageInfo packageInfoA = a(z, i, str, i3, z2);
            if (packageInfoA == null && i3 == 193) {
                return a(z, i, str, i2, z2);
            }
            return packageInfoA;
        }
        return a(z, i, str, i2, z2);
    }

    private boolean a(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue() == -1;
        }
        if (obj instanceof Long) {
            return ((Long) obj).longValue() == -1;
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (!(obj instanceof Collection)) {
            return false;
        }
        return ((Collection) obj).isEmpty();
    }

    private <T> T a(String str, a<T> aVar) {
        return (T) a(str, (a) aVar, false);
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0106: MOVE (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:54:0x0106 */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:66:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private <T> T a(String str, a<T> aVar, boolean z) {
        T tB;
        String str2;
        Object obj;
        String strB = null;
        try {
            if (str == null) {
                h("M|A, key: " + str);
                tB = aVar.b();
            } else {
                Integer num = this.b.get(str);
                try {
                    if (num == null) {
                        obj = null;
                    } else {
                        obj = this.a.get(str);
                        if (obj == null && !z) {
                            return aVar.g;
                        }
                    }
                    Long l = this.c.get(str);
                    boolean z2 = false;
                    boolean z3 = l != null && System.currentTimeMillis() >= l.longValue();
                    if (!z3 && a(obj) && !this.f.contains(str)) {
                        z2 = true;
                    }
                    if (z || obj == null || z3 || z2) {
                        StringBuilder sbAppend = new StringBuilder().append("M|A, key: ").append(str).append("|");
                        if (z) {
                            strB = "FC";
                        } else if (obj == null || z3) {
                            strB = "NVC";
                        } else if (z2) {
                            strB = w.b("002Zdffi");
                        }
                        h(sbAppend.append(strB).toString());
                        T tB2 = aVar.b();
                        this.f.add(str);
                        if (tB2 != null) {
                            this.a.put(str, tB2);
                            if (aVar.a(tB2) > 0) {
                                this.c.put(str, Long.valueOf(System.currentTimeMillis() + aVar.a(tB2)));
                            }
                        }
                        if (num == null) {
                            this.b.put(str, 1);
                        } else {
                            this.b.put(str, Integer.valueOf(num.intValue() + 1));
                        }
                        tB = tB2;
                    } else {
                        h("M|C, key: " + str);
                        tB = (T) obj;
                    }
                } catch (Throwable th) {
                    th = th;
                    strB = str2;
                    if (th instanceof PackageManager.NameNotFoundException) {
                        FlyLog.getInstance().w("Exception: " + th.getClass().getName() + ": " + th.getMessage());
                    } else {
                        FlyLog.getInstance().w(th);
                    }
                    tB = (T) strB;
                    if (tB != null) {
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return tB != null ? aVar.g : tB;
    }

    private <T> T b(String str, a<T> aVar) {
        return (T) b(str, aVar, false);
    }

    private <T> T b(String str, a<T> aVar, boolean z) {
        return (T) a(str, (a) aVar, z, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x005e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private <T> T a(String str, a<T> aVar, boolean z, boolean z2) {
        T tB;
        Object objC;
        boolean z3;
        long jA;
        long jCurrentTimeMillis;
        Object obj = null;
        String strB = null;
        try {
            if (TextUtils.isEmpty(str)) {
                h("F|A, key: " + str);
                tB = aVar.b();
            } else {
                boolean z4 = false;
                if (z) {
                    objC = null;
                    z3 = false;
                } else {
                    z3 = true;
                    try {
                        objC = c(str, aVar);
                        try {
                            if (a(objC)) {
                                if (this.f.contains(str)) {
                                    z3 = false;
                                }
                            }
                        } catch (FlyPersistence.NoValidDataException e) {
                            z4 = true;
                            z3 = false;
                            if (z) {
                                StringBuilder sbAppend = new StringBuilder().append("F|A, key: ").append(str).append("|");
                                if (z) {
                                }
                                h(sbAppend.append(strB).toString());
                                T tB2 = aVar.b();
                                this.f.add(str);
                                jA = aVar.a(tB2);
                                if (jA >= 0) {
                                }
                                tB = tB2;
                            } else {
                                StringBuilder sbAppend2 = new StringBuilder().append("F|A, key: ").append(str).append("|");
                                if (z) {
                                }
                                h(sbAppend2.append(strB).toString());
                                T tB22 = aVar.b();
                                this.f.add(str);
                                jA = aVar.a(tB22);
                                if (jA >= 0) {
                                }
                                tB = tB22;
                            }
                            if (tB != null) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            try {
                                FlyLog.getInstance().d(th);
                                z3 = false;
                                if (z) {
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                obj = objC;
                                if (th instanceof InvocationTargetException) {
                                    String name = th.getClass().getName();
                                    String message = th.getMessage();
                                    Throwable cause = th.getCause();
                                    if (cause != null) {
                                        name = cause.getClass().getName();
                                        message = cause.getMessage();
                                    }
                                    FlyLog.getInstance().w("Exception: " + name + ": " + message);
                                } else if (th instanceof PackageManager.NameNotFoundException) {
                                    FlyLog.getInstance().w("Exception: " + th.getClass().getName() + ": " + th.getMessage());
                                } else {
                                    FlyLog.getInstance().w(th);
                                }
                                tB = obj;
                                if (tB != null) {
                                }
                            }
                            if (tB != null) {
                            }
                        }
                    } catch (FlyPersistence.NoValidDataException e2) {
                        objC = null;
                    } catch (Throwable th3) {
                        th = th3;
                        objC = null;
                    }
                }
                if ((z || z4 || z3) && z2) {
                    StringBuilder sbAppend22 = new StringBuilder().append("F|A, key: ").append(str).append("|");
                    if (z) {
                        strB = "FC";
                    } else if (z4) {
                        strB = "NVC";
                    } else if (z3) {
                        strB = w.b("002Bdffi");
                    }
                    h(sbAppend22.append(strB).toString());
                    T tB222 = aVar.b();
                    this.f.add(str);
                    jA = aVar.a(tB222);
                    if (jA >= 0) {
                        if (jA <= 0) {
                            jCurrentTimeMillis = 0;
                        } else {
                            jCurrentTimeMillis = System.currentTimeMillis() + jA;
                        }
                        a(str, (String) tB222, jCurrentTimeMillis, (a<String>) aVar);
                    }
                    tB = tB222;
                } else {
                    h("F|C, key: " + str + "|" + (z2 ? "AA" : "OC"));
                    tB = objC;
                }
            }
        } catch (Throwable th4) {
            th = th4;
        }
        if (tB != null) {
            return aVar.g;
        }
        return tB;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T c(String str, a<T> aVar) throws FlyPersistence.NoValidDataException {
        Type typeA = a((a) aVar);
        int iA = a(typeA);
        T t = null;
        try {
            if (iA == 1) {
                return (T) cn.fly.tools.utils.g.a().a(str, (Class) ((GenericArrayType) typeA).getGenericComponentType(), (Parcelable[]) aVar.g);
            }
            if (iA != 2 && iA != 4) {
                if (iA != 3) {
                    if (iA == 9) {
                        Class<T> cls = (Class) typeA;
                        if (cls != null) {
                            if (cls == Integer.class) {
                                t = (T) Integer.valueOf(cn.fly.tools.utils.g.a().b(str, ((Integer) aVar.g).intValue()));
                            } else if (cls == Long.class) {
                                t = (T) Long.valueOf(cn.fly.tools.utils.g.a().a(str, ((Long) aVar.g).longValue()));
                            } else if (cls == Double.class) {
                                t = (T) Double.valueOf(cn.fly.tools.utils.g.a().a(str, ((Double) aVar.g).doubleValue()));
                            } else if (cls == Boolean.class) {
                                t = (T) Boolean.valueOf(cn.fly.tools.utils.g.a().a(str, ((Boolean) aVar.g).booleanValue()));
                            } else if (cls == String.class) {
                                t = (T) cn.fly.tools.utils.g.a().c(str, (String) aVar.g);
                            } else if (Parcelable.class.isAssignableFrom(cls)) {
                                t = (T) cn.fly.tools.utils.g.a().a(str, (Class<Class<T>>) cls, (Class<T>) aVar.g);
                            } else {
                                t = (T) cn.fly.tools.utils.g.a().c(str, aVar.g);
                            }
                        }
                        return t;
                    }
                    return (T) cn.fly.tools.utils.g.a().c(str, aVar.g);
                }
                ParameterizedType parameterizedType = (ParameterizedType) typeA;
                Class cls2 = (Class) parameterizedType.getRawType();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type type = actualTypeArguments[0];
                if (actualTypeArguments.length == 2) {
                    type = actualTypeArguments[1];
                }
                if (type instanceof Class) {
                    Class cls3 = (Class) type;
                    if (Parcelable.class.isAssignableFrom(cls3)) {
                        if (cls2 == List.class || cls2 == LinkedList.class || cls2 == ArrayList.class) {
                            t = (T) cn.fly.tools.utils.g.a().c(str, cls3);
                        } else if (cls2 == Map.class || cls2 == HashMap.class || cls2 == TreeMap.class || cls2 == Hashtable.class) {
                            t = (T) cn.fly.tools.utils.g.a().b(str, cls3);
                        }
                    }
                }
                return t;
            }
            return (T) cn.fly.tools.utils.g.a().c(str, aVar.g);
        } catch (FlyPersistence.NoValidDataException e) {
            throw e;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void a(String str, T t, long j, a<T> aVar) {
        try {
            Type typeA = a((a) aVar);
            int iA = a(typeA);
            if (iA == 1) {
                cn.fly.tools.utils.g.a().a(str, (Parcelable[]) t, j);
                return;
            }
            if (iA != 2 && iA != 4) {
                if (iA == 3) {
                    Class cls = (Class) ((ParameterizedType) typeA).getRawType();
                    if (cls == List.class || cls == LinkedList.class || cls == ArrayList.class) {
                        cn.fly.tools.utils.g.a().a(str, (List) t, j);
                    } else if (cls == Map.class || cls == HashMap.class || cls == TreeMap.class || cls == Hashtable.class) {
                        cn.fly.tools.utils.g.a().a(str, (Map) t, j);
                    }
                    return;
                }
                if (iA == 9) {
                    Class cls2 = (Class) typeA;
                    if (cls2 != null) {
                        if (cls2 == Integer.class) {
                            cn.fly.tools.utils.g.a().a(str, (Integer) t, j);
                        } else if (cls2 == Long.class) {
                            cn.fly.tools.utils.g.a().a(str, (Long) t, j);
                        } else if (cls2 == Double.class) {
                            cn.fly.tools.utils.g.a().a(str, (Double) t, j);
                        } else if (cls2 == Boolean.class) {
                            cn.fly.tools.utils.g.a().a(str, (Boolean) t, j);
                        } else if (cls2 == String.class) {
                            cn.fly.tools.utils.g.a().a(str, (String) t, j);
                        } else if (Parcelable.class.isAssignableFrom(cls2)) {
                            cn.fly.tools.utils.g.a().a(str, (Parcelable) t, j);
                        } else {
                            cn.fly.tools.utils.g.a().a(str, t, j);
                        }
                    }
                    return;
                }
                cn.fly.tools.utils.g.a().a(str, t, j);
                return;
            }
            cn.fly.tools.utils.g.a().a(str, t, j);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    private <T> Type a(a<T> aVar) {
        try {
            return ((ParameterizedType) aVar.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    private int a(Type type) {
        if (type instanceof GenericArrayType) {
            return Parcelable.class.isAssignableFrom((Class) ((GenericArrayType) type).getGenericComponentType()) ? 1 : 2;
        }
        if (type instanceof ParameterizedType) {
            try {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type type2 = actualTypeArguments[0];
                if (actualTypeArguments.length == 2) {
                    type2 = actualTypeArguments[1];
                }
                if (type2 instanceof ParameterizedType) {
                    ParameterizedType parameterizedType2 = (ParameterizedType) type2;
                    Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
                    Type type3 = actualTypeArguments2[0];
                    if (actualTypeArguments2.length == 2) {
                        type3 = actualTypeArguments2[1];
                    }
                    if (type3 instanceof Class) {
                    }
                    return 4;
                }
                if (type2 instanceof Class) {
                    return Parcelable.class.isAssignableFrom((Class) type2) ? 3 : 4;
                }
                return -1;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return -1;
            }
        }
        return 9;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long a(Calendar calendar) {
        calendar.add(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    private boolean a(boolean z, String str, String str2) {
        String str3 = "sdir_able_" + (TextUtils.equals(str2, DH.SyncMtd.getPackageName()) ? 1 : 0);
        if (cn.fly.tools.utils.g.a().a(str3, -1) != z) {
            cn.fly.tools.utils.g.a().a(str3, Integer.valueOf(z ? 1 : 0));
            if (!z) {
                return true;
            }
        }
        if (z && !TextUtils.isEmpty(str2)) {
            String str4 = "key_almdf-" + str + "-" + str2;
            long jD = cn.fly.tools.utils.g.a().d(str4);
            long jG = g(str2);
            if (jG != jD) {
                cn.fly.tools.utils.g.a().a(str4, Long.valueOf(jG));
                return true;
            }
            return false;
        }
        return false;
    }

    private long g(String str) {
        String str2;
        if (!TextUtils.isEmpty(str)) {
            ApplicationInfo applicationInfoA = a(true, str, 0);
            if (applicationInfoA == null) {
                str2 = null;
            } else {
                str2 = applicationInfoA.sourceDir;
            }
            if (!TextUtils.isEmpty(str2)) {
                return new File(str2).lastModified();
            }
        }
        return 0L;
    }

    private void h(String str) {
    }

    private static abstract class a<T> {
        public T g;

        protected abstract T b() throws Throwable;

        public a(T t) {
            this.g = t;
        }

        protected long a(T t) {
            return 0L;
        }
    }
}