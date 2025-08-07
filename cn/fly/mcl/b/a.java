package cn.fly.mcl.b;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.apc.b;
import cn.fly.commons.ad;
import cn.fly.commons.m;
import cn.fly.mcl.BusinessMessageListener;
import cn.fly.mcl.FlyMCL;
import cn.fly.mcl.TcpStatus;
import cn.fly.mcl.tcp.h;
import cn.fly.mgs.OnIdChangeListener;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.HttpResponseCallback;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.network.StringPart;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.UIHandler;
import cn.fly.tools.utils.d;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class a {
    public static final ExecutorService a = Executors.newSingleThreadExecutor();
    private static AtomicBoolean b = new AtomicBoolean(false);
    private static NetworkHelper c = new NetworkHelper();
    private static CopyOnWriteArraySet<String> d = new CopyOnWriteArraySet<>();

    public static void a(Context context, String str, String str2) {
        Context applicationContext;
        cn.fly.mcl.c.b.a().a("mcl ini");
        if (context == null) {
            applicationContext = FlySDK.getContext();
        } else {
            applicationContext = context.getApplicationContext();
        }
        h.b().a(applicationContext, str, str2);
        cn.fly.mcl.a.a.a().a(applicationContext, new C0011a());
        ad.b().a("use_config", false);
        b(0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final int i, int i2) {
        cn.fly.mcl.c.b.a().b("sched: count: " + i + ", delay: " + i2);
        UIHandler.sendEmptyMessageDelayed(0, i2 * 1000, new Handler.Callback() { // from class: cn.fly.mcl.b.a.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                try {
                    if (!h.b().c()) {
                        a.a.execute(a.c(i));
                        return false;
                    }
                    return false;
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                    return false;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Runnable c(final int i) {
        return new Runnable() { // from class: cn.fly.mcl.b.a.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    a.d(i);
                } catch (Throwable th) {
                    cn.fly.mcl.c.b.a().a(th);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(final int i) {
        boolean zIsInMainProcess = DH.SyncMtd.isInMainProcess();
        cn.fly.mcl.c.b.a().b("tp rgs, main p: " + zIsInMainProcess);
        final boolean z = !zIsInMainProcess && cn.fly.mcl.a.a.a().b();
        a(5000, new d<Boolean>() { // from class: cn.fly.mcl.b.a.4
            @Override // cn.fly.tools.utils.d
            public void a(Boolean bool) {
                if (!bool.booleanValue()) {
                    if (!z && !h.b().a()) {
                        a.b(i + 1, 30);
                        return;
                    } else {
                        h.b().b(TcpStatus.obtain(22));
                        return;
                    }
                }
                h.b().i();
                h.b().b(TcpStatus.obtain(10));
            }
        });
    }

    private static void a(int i, final d<Boolean> dVar) {
        if (b.getAndSet(true)) {
            return;
        }
        try {
            boolean zIsInMainProcess = DH.SyncMtd.isInMainProcess();
            cn.fly.mcl.c.b.a().b("init tp, main p: " + zIsInMainProcess);
            if (zIsInMainProcess) {
                h.b().f();
                if (!h.b().g) {
                    b.set(false);
                    h.b().a(TcpStatus.obtain(21).setDetailedMsg("global: " + h.b().g));
                    if (dVar != null) {
                        dVar.a(false);
                    }
                    return;
                }
                if (h.b().d()) {
                    h.b().a(i, new d<Boolean>() { // from class: cn.fly.mcl.b.a.5
                        @Override // cn.fly.tools.utils.d
                        public void a(Boolean bool) {
                            if (bool.booleanValue()) {
                                cn.fly.mcl.tcp.b.a();
                                if (!h.b().i) {
                                    a.b(new d<Void>() { // from class: cn.fly.mcl.b.a.5.1
                                        @Override // cn.fly.tools.utils.d
                                        public void a(Void r2) {
                                            a.b.set(false);
                                            if (dVar != null) {
                                                dVar.a(true);
                                            }
                                        }
                                    });
                                    return;
                                }
                                a.b.set(false);
                                if (dVar != null) {
                                    dVar.a(true);
                                    return;
                                }
                                return;
                            }
                            cn.fly.mcl.c.b.a().a("tp reg failed");
                            a.b(new d<Void>() { // from class: cn.fly.mcl.b.a.5.2
                                @Override // cn.fly.tools.utils.d
                                public void a(Void r2) {
                                    a.b.set(false);
                                    if (dVar != null) {
                                        dVar.a(false);
                                    }
                                }
                            });
                        }
                    });
                    return;
                } else {
                    h.b().a(TcpStatus.obtain(21).setDetailedMsg("unavailable(global: " + h.b().g + ", connect: " + h.b().h + ")"));
                    cn.fly.mcl.c.b.a().a("tp reg avail false");
                }
            } else {
                h.b().a(TcpStatus.obtain(21).setDetailedMsg("sub process"));
            }
            b(new d<Void>() { // from class: cn.fly.mcl.b.a.6
                @Override // cn.fly.tools.utils.d
                public void a(Void r2) {
                    a.b.set(false);
                    if (dVar != null) {
                        dVar.a(false);
                    }
                }
            });
        } finally {
            try {
            } finally {
            }
        }
    }

    private static void d() {
        try {
        } finally {
            try {
            } finally {
            }
        }
        if (b.getAndSet(true)) {
            return;
        }
        boolean zIsInMainProcess = DH.SyncMtd.isInMainProcess();
        cn.fly.mcl.c.b.a().b("rgs tp, main p: " + zIsInMainProcess);
        if (zIsInMainProcess) {
            if (!h.b().g) {
                b.set(false);
            } else if (h.b().d()) {
                h.b().a(3000, new d<Boolean>() { // from class: cn.fly.mcl.b.a.7
                    @Override // cn.fly.tools.utils.d
                    public void a(Boolean bool) {
                        if (bool.booleanValue()) {
                            cn.fly.mcl.tcp.b.a();
                            a.b.set(false);
                        } else {
                            cn.fly.mcl.c.b.a().a("tp reg failed");
                        }
                    }
                });
            } else {
                a.execute(new Runnable() { // from class: cn.fly.mcl.b.a.8
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (!h.b().c()) {
                                if (!h.b().d()) {
                                    h.b().f();
                                }
                                h.b().a(new d<Boolean>() { // from class: cn.fly.mcl.b.a.8.1
                                    @Override // cn.fly.tools.utils.d
                                    public void a(Boolean bool) {
                                        if (!h.b().i) {
                                            a.b(new d<Void>() { // from class: cn.fly.mcl.b.a.8.1.1
                                                @Override // cn.fly.tools.utils.d
                                                public void a(Void r1) {
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        } catch (Throwable th) {
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final d<Void> dVar) {
        if (!cn.fly.mcl.a.a.a().b()) {
            cn.fly.mcl.a.a.a().a(new d<Void>() { // from class: cn.fly.mcl.b.a.9
                @Override // cn.fly.tools.utils.d
                public void a(Void r2) {
                    if (dVar != null) {
                        dVar.a(null);
                    }
                }
            });
        } else if (dVar != null) {
            dVar.a(null);
        }
    }

    public static String a(boolean z, String str, HashMap<String, Object> map, HashMap<String, String> map2, NetworkHelper.NetworkTimeOut networkTimeOut) throws Throwable {
        if (!z && d != null) {
            Iterator<String> it = d.iterator();
            while (it.hasNext()) {
                if (str.equals(it.next())) {
                    String strHttpGetNew = c.httpGetNew(str, map, map2, networkTimeOut);
                    cn.fly.mcl.c.b.a().a("mcl htp");
                    return strHttpGetNew;
                }
            }
        }
        if (map != null) {
            String strA = a(map);
            if (strA.length() > 0) {
                str = str + "?" + strA;
            }
            map = null;
        }
        if (h.b().e()) {
            if (!h.b().c()) {
                d();
            }
            if (h.b().c()) {
                HashMap<String, Object> mapA = h.b().a(1004, networkTimeOut.readTimout, a("GET", str, map2, null));
                if (mapA != null) {
                    cn.fly.mcl.c.b.a().a("mcl tp");
                    return HashonHelper.fromHashMap(mapA);
                }
                if (!z) {
                    String strHttpGetNew2 = c.httpGetNew(str, map, map2, networkTimeOut);
                    cn.fly.mcl.c.b.a().a("mcl htp");
                    return strHttpGetNew2;
                }
            }
        }
        if (z) {
            return null;
        }
        if (!h.b().e()) {
            return c.httpGetNew(str, null, map2, networkTimeOut);
        }
        if (!cn.fly.mcl.a.a.a().b()) {
            b(5, 0);
        }
        return a(str, map2, networkTimeOut);
    }

    public static void a(boolean z, String str, HashMap<String, String> map, StringPart stringPart, int i, HttpResponseCallback httpResponseCallback, NetworkHelper.NetworkTimeOut networkTimeOut) throws Throwable {
        if (!z && d != null && !d.isEmpty()) {
            Iterator<String> it = d.iterator();
            while (it.hasNext()) {
                if (str.equals(it.next())) {
                    c.rawPost(str, map, stringPart, i, httpResponseCallback, networkTimeOut);
                    return;
                }
            }
        }
        if (h.b().e()) {
            if (!h.b().c()) {
                d();
            }
            if (h.b().c()) {
                HashMap<String, Object> mapA = h.b().a(1004, networkTimeOut.readTimout, a("POST", str, map, stringPart));
                if (mapA != null) {
                    httpResponseCallback.onResponse(cn.fly.mcl.a.a(new b(mapA)));
                    return;
                } else if (!z) {
                    c.rawPost(str, map, stringPart, i, httpResponseCallback, networkTimeOut);
                    return;
                }
            }
        }
        if (z) {
            httpResponseCallback.onResponse(null);
        } else {
            if (h.b().e()) {
                if (!cn.fly.mcl.a.a.a().b()) {
                    b(5, 0);
                }
                a(str, map, stringPart, i, httpResponseCallback, networkTimeOut);
                return;
            }
            c.rawPost(str, map, stringPart, i, httpResponseCallback, networkTimeOut);
        }
    }

    private static String a(String str, String str2, HashMap<String, String> map, StringPart stringPart) {
        HashMap map2 = new HashMap();
        map2.put(m.a("004k8ge-lh"), str);
        map2.put("url", str2);
        HashMap map3 = new HashMap();
        if (map != null) {
            map3.putAll(map);
        }
        map2.put("headers", map3);
        if (stringPart != null) {
            map2.put("body", stringPart.toString());
        }
        return HashonHelper.fromHashMap(map2);
    }

    private static String a(String str, HashMap<String, String> map, NetworkHelper.NetworkTimeOut networkTimeOut) throws Throwable {
        String strA = cn.fly.mcl.a.a.a().a("GET", str, map, null, 0, networkTimeOut);
        if (!TextUtils.isEmpty(strA)) {
            cn.fly.mcl.c.b.a().a("mcl apc");
            return strA;
        }
        String strHttpGetNew = c.httpGetNew(str, null, map, networkTimeOut);
        cn.fly.mcl.c.b.a().a("mcl htp");
        return strHttpGetNew;
    }

    private static void a(String str, HashMap<String, String> map, StringPart stringPart, int i, HttpResponseCallback httpResponseCallback, NetworkHelper.NetworkTimeOut networkTimeOut) throws Throwable {
        String strA = cn.fly.mcl.a.a.a().a("POST", str, map, stringPart, i, networkTimeOut);
        if (!TextUtils.isEmpty(strA)) {
            httpResponseCallback.onResponse(cn.fly.mcl.a.a(new b(HashonHelper.fromJson(strA), true)));
        } else {
            c.rawPost(str, map, stringPart, i, httpResponseCallback, networkTimeOut);
        }
    }

    private static String a(HashMap<String, Object> map) throws Throwable {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String strUrlEncode = Data.urlEncode(entry.getKey(), "utf-8");
            String strUrlEncode2 = entry.getValue() == null ? "" : Data.urlEncode(String.valueOf(entry.getValue()), "utf-8");
            if (sb.length() > 0) {
                sb.append(Typography.amp);
            }
            sb.append(strUrlEncode).append('=').append(strUrlEncode2);
        }
        return sb.toString();
    }

    public static String a() {
        h.b().g();
        return h.b().k;
    }

    public static void a(OnIdChangeListener onIdChangeListener) {
        h.b().a(onIdChangeListener);
        h.b().g();
    }

    public static long b() {
        h.b().g();
        return h.b().l;
    }

    public static void a(String str, long j, final d<Boolean> dVar) {
        h.b().a(str, j);
        try {
            if (h.b().d()) {
                if (!h.b().c()) {
                    a(3000, new d<Boolean>() { // from class: cn.fly.mcl.b.a.10
                        @Override // cn.fly.tools.utils.d
                        public void a(Boolean bool) {
                            h.b().b(new d<Boolean>() { // from class: cn.fly.mcl.b.a.10.1
                                @Override // cn.fly.tools.utils.d
                                public void a(Boolean bool2) {
                                    if (dVar != null) {
                                        dVar.a(bool2);
                                    }
                                }
                            });
                        }
                    });
                } else {
                    h.b().b(new d<Boolean>() { // from class: cn.fly.mcl.b.a.2
                        @Override // cn.fly.tools.utils.d
                        public void a(Boolean bool) {
                            if (dVar != null) {
                                dVar.a(bool);
                            }
                        }
                    });
                }
            } else if (dVar != null) {
                dVar.a(false);
            }
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
            if (dVar != null) {
                dVar.a(false);
            }
        }
    }

    public static void a(FlyMCL.ELPMessageListener eLPMessageListener) {
        h.b().a(eLPMessageListener);
    }

    public static void a(int i, BusinessMessageListener businessMessageListener) {
        h.b().a(i, businessMessageListener);
    }

    /* renamed from: cn.fly.mcl.b.a$a, reason: collision with other inner class name */
    private static class C0011a implements b.a {
        private C0011a() {
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:36:0x013f -> B:43:0x0151). Please report as a decompilation issue!!! */
        @Override // cn.fly.apc.b.a
        public cn.fly.apc.a a(String str, cn.fly.apc.a aVar, long j) {
            cn.fly.apc.a aVarA = cn.fly.mcl.a.a.a().a(str, aVar);
            if (aVarA != null) {
                try {
                    if (aVarA.d != null && (aVarA.d instanceof cn.fly.mcl.a.b)) {
                        cn.fly.mcl.a.b bVar = (cn.fly.mcl.a.b) aVarA.d;
                        NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
                        networkTimeOut.readTimout = bVar.f;
                        networkTimeOut.connectionTimeout = bVar.g;
                        if ("POST".equals(bVar.a)) {
                            aVarA.d = null;
                            a.a(true, bVar.b, cn.fly.mcl.a.b.a(bVar.c), new StringPart().append(bVar.d), bVar.e, cn.fly.mcl.a.a(bVar.b, aVarA), networkTimeOut);
                        } else if ("GET".equals(bVar.a)) {
                            cn.fly.mcl.a.a.a().b(a.a(true, bVar.b, null, cn.fly.mcl.a.b.a(bVar.c), networkTimeOut), aVarA);
                        }
                    } else if (aVarA.a == 9004) {
                        String string = aVar.e.getString(m.a("004'fe_fkf"));
                        long j2 = aVar.e.getLong("uniqueId");
                        if (!TextUtils.isEmpty(string)) {
                            try {
                                HashMap mapFromJson = HashonHelper.fromJson(h.b().a(h.b().h(), string));
                                String str2 = (String) mapFromJson.get("workId");
                                int iA = h.a((HashMap<String, Object>) mapFromJson, "expire", 0);
                                boolean z = h.a((HashMap<String, Object>) mapFromJson, "needRepeat", 0) == 1;
                                int iA2 = h.a((HashMap<String, Object>) mapFromJson, m.a("004k<geGlh"), 0);
                                String str3 = (String) mapFromJson.get(m.a("004)feWfkf"));
                                if (iA2 == 1 || iA2 == 2) {
                                    String str4 = "repeat";
                                    boolean z2 = z;
                                    Bundle bundle = new Bundle();
                                    bundle.putString(m.a("004Xfe>fkf"), str3);
                                    bundle.putString("workId", str2);
                                    bundle.putLong("uniqueId", aVar.e.getLong("uniqueId"));
                                    bundle.putInt("expire", iA);
                                    bundle.putInt("msgType", iA2);
                                    int iA3 = h.b().a(bundle);
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putBoolean("needRepeat", z2);
                                    bundle2.putInt(str4, iA3);
                                    aVarA.e = bundle2;
                                } else {
                                    boolean zA = h.b().a(j2, str2, iA, iA2, str3);
                                    Bundle bundle3 = new Bundle();
                                    bundle3.putBoolean("needRepeat", z);
                                    bundle3.putInt("repeat", zA ? 1 : 0);
                                    aVarA.e = bundle3;
                                }
                            } catch (Throwable th) {
                                cn.fly.mcl.c.b.a().a(th);
                            }
                        }
                    }
                } catch (Throwable th2) {
                    cn.fly.mcl.c.b.a().a(th2);
                }
            }
            return aVarA;
        }
    }
}