package cn.fly.commons.a;

import android.os.Handler;
import android.os.Message;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.FlyProduct;
import cn.fly.commons.ab;
import cn.fly.commons.ac;
import cn.fly.commons.q;
import cn.fly.tools.FlyLog;
import cn.fly.tools.MDP;
import cn.fly.tools.b.h;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.tools.utils.ResHelper;
import cn.fly.tools.utils.UIHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public abstract class c implements Runnable {
    private static final WeakHashMap<String, Object> k = new WeakHashMap<>();
    protected int a;
    protected Object b;
    private final String c;
    private final String d;
    private final long e;
    private final long f;
    private volatile long g;
    private final int h;
    private int i;
    private boolean j;
    private long l;

    protected abstract void a() throws Throwable;

    protected c(String str, String str2, long j) {
        this(str, 0L, str2, 0L, j);
    }

    protected c(String str, long j, String str2, long j2, long j3) {
        this.a = 0;
        this.i = 0;
        this.j = false;
        this.c = str;
        this.d = str2;
        this.e = j;
        this.f = j2;
        this.h = getClass().hashCode();
        this.l = j3;
        this.g = System.currentTimeMillis();
    }

    public c a(boolean z) {
        this.j = z;
        if (z) {
            this.l = 0L;
        }
        return this;
    }

    public boolean c() {
        return this.j;
    }

    public c a(Object obj) {
        this.b = obj;
        return this;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.l > 0) {
            l.a().a(this.l, (long) this, this.i);
            this.l = 0L;
            return;
        }
        try {
            if (h()) {
                d();
            }
            if (j()) {
                a();
            }
            b();
            if (this.a < 0) {
            }
        } catch (Throwable th) {
            try {
                FlyLog.getInstance().d(th);
            } finally {
                b();
                if (this.a >= 0) {
                    this.a++;
                }
            }
        }
    }

    protected void d() {
    }

    protected void b() {
        long jM = m();
        if (jM > 0) {
            a(jM);
        } else {
            this.j = true;
        }
    }

    protected void a(int i) {
        this.i = i;
    }

    public String e() {
        return this.c;
    }

    protected boolean f() {
        return ((Long) cn.fly.commons.c.a(this.c, Long.valueOf(this.e))).longValue() != 0 && g();
    }

    protected final boolean g() {
        if ("bs,l,ol,wi,wl,ext,aa,".contains(this.c + ",")) {
            return cn.fly.commons.k.a().b();
        }
        return true;
    }

    protected boolean h() {
        return this.a == 0;
    }

    public boolean i() {
        if (f()) {
            ab.a.execute(this);
            return true;
        }
        return false;
    }

    protected boolean j() {
        boolean zA = cn.fly.commons.c.a();
        boolean zB = cn.fly.commons.c.b();
        if (!zA || !zB) {
            FlyLog.getInstance().d("slt: " + e() + ", to: " + zA + ", conn: " + zB, new Object[0]);
            return false;
        }
        boolean zF = f();
        FlyLog.getInstance().d("slt : " + getClass().getSimpleName() + ", to: " + zA + ", conn: " + zB + ", " + this.c + ": " + zF + ", key: " + a(this.c, (String) 0) + ", gp: " + m() + " , oce " + this.j + " , tt " + this.a, new Object[0]);
        return zF;
    }

    public <T> T a(String str, T t) {
        return (T) cn.fly.commons.c.a(str, t);
    }

    public long k() {
        return this.g;
    }

    public int l() {
        return this.h;
    }

    protected c a(long j) {
        if (j > 0) {
            this.g = System.currentTimeMillis() + (j * 1000);
        } else {
            this.g = -1L;
        }
        return this;
    }

    protected long m() {
        try {
            if (this.d != null) {
                return Long.parseLong(String.valueOf(cn.fly.commons.c.a(this.d, Long.valueOf(this.f))));
            }
            return 0L;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return 0L;
        }
    }

    protected void a(final cn.fly.tools.utils.d<HashMap<String, Object>> dVar) {
        if (((Integer) a(cn.fly.commons.m.a("002Hfm7i"), (String) 0)).intValue() == 1) {
            DH.requester(FlySDK.getContext()).getPosCommForce(0, 0, true, false).request(new DH.DHResponder() { // from class: cn.fly.commons.a.c.1
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    List<HashMap<String, Object>> listA = c.this.a(dHResponse.getPosCommForce(new int[0]));
                    if (listA != null && !listA.isEmpty()) {
                        dVar.a(listA.get(listA.size() - 1));
                    } else {
                        dVar.a(null);
                    }
                }
            });
        } else {
            dVar.a(null);
        }
    }

    protected void a(long j, String str, Object obj) {
        a(j, str, obj, false);
    }

    protected void a(long j, String str, Object obj, boolean z) {
        a(j, str, obj, null, z);
    }

    protected void a(long j, String str, Object obj, HashMap<String, Object> map, boolean z) {
        final long j2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (j > 0) {
            j2 = (j * 1000) + jCurrentTimeMillis;
        } else {
            j2 = jCurrentTimeMillis;
        }
        final HashMap<String, Object> map2 = new HashMap<>();
        map2.put(cn.fly.commons.m.a("004k0geIlh"), str);
        map2.put(cn.fly.commons.m.a("004i,fkhkZk"), obj);
        map2.put(cn.fly.commons.m.a("008KfeBfkhk>fkfhGh"), Long.valueOf(jCurrentTimeMillis));
        if (map != null && !map.isEmpty()) {
            map2.putAll(map);
        }
        if (z) {
            a(new cn.fly.tools.utils.d<HashMap<String, Object>>() { // from class: cn.fly.commons.a.c.2
                @Override // cn.fly.tools.utils.d
                public void a(HashMap<String, Object> map3) {
                    map2.put(cn.fly.commons.m.a("002ei"), map3);
                    c.this.a(map3, map2);
                    cn.fly.commons.d.a().a(j2, map2);
                }
            });
        } else {
            cn.fly.commons.d.a().a(j2, map2);
        }
    }

    protected void a(HashMap<String, Object> map, final HashMap<String, Object> map2) {
        if (map != null && !C0041r.a(((Long) ResHelper.forceCast(map.get(cn.fly.commons.g.a), Long.valueOf(System.currentTimeMillis()))).longValue(), System.currentTimeMillis())) {
            DH.requester(FlySDK.getContext()).getPosCommForce(0, 15, false, true).request(new DH.DHResponder() { // from class: cn.fly.commons.a.c.3
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) throws Throwable {
                    if (dHResponse.getPosCommForce(new int[0]) != null && !dHResponse.getPosCommForce(new int[0]).isEmpty()) {
                        HashMap<String, Object> map3 = c.this.a(dHResponse.getPosCommForce(new int[0])).get(r3.size() - 1);
                        map3.put("pt", 2);
                        map2.put("nl", map3);
                    }
                }
            });
        }
    }

    protected void a(String str, HashMap<String, Object> map) {
        a(str, map, false);
    }

    protected void a(String str, HashMap<String, Object> map, boolean z) {
        final long jCurrentTimeMillis = System.currentTimeMillis();
        final HashMap<String, Object> map2 = new HashMap<>();
        map2.put(cn.fly.commons.m.a("004kGge'lh"), str);
        if (map != null) {
            map2.put(cn.fly.commons.m.a("004AfeIfkf"), map);
        }
        map2.put(cn.fly.commons.m.a("008'fe fkhkRfkfh>h"), Long.valueOf(jCurrentTimeMillis));
        if (z) {
            a(new cn.fly.tools.utils.d<HashMap<String, Object>>() { // from class: cn.fly.commons.a.c.4
                @Override // cn.fly.tools.utils.d
                public void a(HashMap<String, Object> map3) {
                    map2.put(cn.fly.commons.m.a("002ei"), map3);
                    c.this.a(map3, map2);
                    cn.fly.commons.d.a().a(jCurrentTimeMillis, map2);
                }
            });
        } else {
            cn.fly.commons.d.a().a(jCurrentTimeMillis, map2);
        }
    }

    protected TreeMap<String, Object> b(Object obj) {
        if (obj != null) {
            try {
                TreeMap<String, Object> treeMap = new TreeMap<>();
                h.a aVar = new h.a(obj);
                treeMap.put("ltdmt", Double.valueOf(aVar.b()));
                treeMap.put("lndmt", Double.valueOf(aVar.c()));
                return treeMap;
            } catch (Throwable th) {
                return null;
            }
        }
        return null;
    }

    protected List<HashMap<String, Object>> a(List list) {
        if (list != null && !list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                h.a aVar = new h.a(it.next());
                try {
                    HashMap map = new HashMap();
                    map.put("accmt", Float.valueOf(aVar.a()));
                    if (aVar.i()) {
                        map.put("vacmt", Float.valueOf(aVar.j()));
                    }
                    map.put("ltdmt", Double.valueOf(aVar.b()));
                    map.put("lndmt", Double.valueOf(aVar.c()));
                    map.put(cn.fly.commons.g.a, Long.valueOf(aVar.k()));
                    map.put("prvmt", aVar.e());
                    map.put("atdmt", Double.valueOf(aVar.f()));
                    map.put("brmt", Float.valueOf(aVar.g()));
                    map.put("spmt", Float.valueOf(aVar.h()));
                    arrayList.add(map);
                } catch (Throwable th) {
                    FlyLog.getInstance().d("[cl] glfe " + th, new Object[0]);
                }
            }
            return arrayList;
        }
        return null;
    }

    public static void a(String str, File file, String str2, String str3) throws Throwable {
        Object objNewInstance;
        Object objInvokeInstanceMethod = ReflectHelper.invokeInstanceMethod(FlySDK.getContext(), cn.fly.commons.m.a("014Cgl[hk@gf)ifGhkhkhgfmQfDfe$hKfl"), new Object[0]);
        ReflectHelper.importClass(cn.fly.commons.m.a("028Lfe'fiAfffkgjfnhkgehkCkh:fhfnhn%h8gkgf4if=hkhkhgfm$f]feWhBfl"), cn.fly.commons.m.a("028Lfe'fiAfffkgjfnhkgehkCkh:fhfnhn%h8gkgf4if=hkhkhgfm$f]feWhBfl"));
        file.setReadOnly();
        File parentFile = file.getParentFile();
        synchronized (k) {
            objNewInstance = k.get(str);
            if (objNewInstance == null) {
                objNewInstance = ReflectHelper.newInstance(cn.fly.commons.m.a("028Ofe:fiTfffkgjfnhkgehkLkhSfhfnhn[hYgkgf<if;hkhkhgfm*f;feWhRfl"), file.getAbsolutePath(), parentFile.getAbsolutePath(), parentFile.getAbsolutePath(), objInvokeInstanceMethod);
                k.put(str, objNewInstance);
            }
        }
        ResHelper.deleteFileAndFolder(parentFile);
        String strA = cn.fly.commons.f.a((FlyProduct) null);
        final Object objInvokeInstanceMethod2 = ReflectHelper.invokeInstanceMethod(ReflectHelper.invokeInstanceMethod(objNewInstance, cn.fly.commons.m.a("009i7fm_fMfegfGif_hkhk"), str2), cn.fly.commons.m.a("009>glZhk:jeRhkj+fmfe"), str3, String.class);
        HashMap map = new HashMap();
        map.put(cn.fly.commons.m.a("0043fefifkfe"), strA);
        map.put(cn.fly.commons.m.a("004.fhfmfkfe"), cn.fly.tools.b.c.a(FlySDK.getContext()).d().an());
        map.put(cn.fly.commons.m.a("010Whkfegjim@h8flhkfkfm'g"), Integer.valueOf(FlySDK.SDK_VERSION_CODE));
        map.put(cn.fly.commons.m.a("006fllDkeBhYge"), q.a());
        map.put(cn.fly.commons.m.a("009fllOgn1he[fl?hk"), FlySDK.getAppSecret());
        map.put(cn.fly.commons.m.a("006Ufefmfh,f!fkYg"), FlySDK.getDmn().getDomain());
        map.put(cn.fly.commons.m.a("010*ghfmfl,ehAhm:kkl3hk"), Boolean.valueOf(FlySDK.checkForceHttps()));
        map.put(cn.fly.commons.m.a("009BghfmflAehSggHl:ffjj"), Boolean.valueOf(FlySDK.checkV6()));
        map.put(cn.fly.commons.m.a("004hehZgk"), Long.valueOf(((Long) cn.fly.commons.c.a(cn.fly.commons.m.a("004hehCgk"), 5L)).longValue()));
        map.put(cn.fly.commons.m.a("002eIfe"), (String) cn.fly.commons.c.a(cn.fly.commons.m.a("002e!fe"), cn.fly.commons.m.a("006Tjgjgjhjhjhjh")));
        map.put("usridt", ac.f());
        map.put("mdp", MDP.class.getName());
        final String strFromHashMap = HashonHelper.fromHashMap(map);
        ReflectHelper.invokeInstanceMethod(objInvokeInstanceMethod2, cn.fly.commons.m.a("013<hkXhkJhfMeehZhkhkfkhh,ih"), true);
        cn.fly.commons.i.a().a(15);
        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.commons.a.c.5
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                try {
                    cn.fly.commons.i.a().a(16);
                    ReflectHelper.invokeInstanceMethod(objInvokeInstanceMethod2, cn.fly.commons.m.a("0061fk6gFfffmgj:h"), null, new Object[]{strFromHashMap});
                    cn.fly.commons.i.a().a(17);
                } catch (Throwable th) {
                    cn.fly.commons.i.a().a(7, th);
                }
                return false;
            }
        });
    }

    protected static long a(String str, Long l) {
        Map map = (Map) cn.fly.commons.c.a(cn.fly.commons.m.a("005;hkTi6fk-kHhk"), (Object) null);
        if (map == null) {
            return 0L;
        }
        return ((Long) ResHelper.forceCast(map.get(str), l)).longValue();
    }
}