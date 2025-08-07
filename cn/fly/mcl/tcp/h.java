package cn.fly.mcl.tcp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import androidx.core.view.PointerIconCompat;
import cn.fly.FlySDK;
import cn.fly.commons.FlyProduct;
import cn.fly.commons.ab;
import cn.fly.commons.ac;
import cn.fly.commons.ad;
import cn.fly.commons.j;
import cn.fly.commons.o;
import cn.fly.commons.w;
import cn.fly.mcl.BusinessCallBack;
import cn.fly.mcl.BusinessMessageCallback;
import cn.fly.mcl.BusinessMessageListener;
import cn.fly.mcl.FlyMCL;
import cn.fly.mcl.TcpStatus;
import cn.fly.mcl.TcpStatusListener;
import cn.fly.mcl.a;
import cn.fly.mcl.tcp.PSIDManager;
import cn.fly.mgs.OnIdChangeListener;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.ActivityTracker;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.UIHandler;
import cn.fly.tools.utils.i;
import com.alibaba.fastjson.asm.Opcodes;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes.dex */
public class h implements d {
    private static volatile h n;
    private TcpStatus A;
    public long a;
    public String b;
    public ArrayList<String> d;
    public boolean g;
    public boolean h;
    public boolean i;
    public boolean j;
    public String k;
    public long l;
    public boolean m;
    private NetworkHelper o;
    private HashonHelper p;
    private f q;
    private String r;
    private String s;
    private Context t;
    private FlyMCL.ELPMessageListener u;
    private HashMap<Integer, HashSet<BusinessMessageListener>> v;
    private boolean w;
    private cn.fly.mcl.c.c x;
    private OnIdChangeListener y;
    private TcpStatusListener z;
    public AtomicLong c = new AtomicLong(0);
    public int e = 270;
    public int f = 500;

    public boolean a() {
        return this.w;
    }

    public static h b() {
        if (n == null) {
            synchronized (h.class) {
                if (n == null) {
                    n = new h();
                }
            }
        }
        return n;
    }

    private h() {
        cn.fly.mcl.c.b.a().b("TP tpHelper init");
        this.q = new f(this);
        this.o = new NetworkHelper();
        this.p = new HashonHelper();
        this.v = new HashMap<>();
        this.x = new cn.fly.mcl.c.c(FlySDK.getContext());
        this.t = FlySDK.getContext();
    }

    public void a(OnIdChangeListener onIdChangeListener) {
        this.y = onIdChangeListener;
    }

    public void a(String str) {
        this.x.b(str);
    }

    public void a(FlyMCL.ELPMessageListener eLPMessageListener) {
        this.u = eLPMessageListener;
    }

    public void a(int i, BusinessMessageListener businessMessageListener) {
        HashSet<BusinessMessageListener> hashSet;
        int iIntValue;
        Iterator<Map<String, Object>> it;
        try {
            cn.fly.mcl.c.b.a().b("TP tpHelper addBMListener: bisType = " + i + ", listener = " + businessMessageListener);
            Integer numValueOf = Integer.valueOf(i);
            if (businessMessageListener == null) {
                cn.fly.mcl.c.b.a().b("TP tpHelper addBMListener: remove key = " + numValueOf);
                this.v.remove(numValueOf);
                return;
            }
            if (this.v.containsKey(numValueOf)) {
                hashSet = this.v.get(numValueOf);
            } else {
                HashSet<BusinessMessageListener> hashSet2 = new HashSet<>();
                this.v.put(numValueOf, hashSet2);
                hashSet = hashSet2;
            }
            hashSet.add(businessMessageListener);
            if (g.a().b()) {
                cn.fly.mcl.c.b.a().b("TP tpHelper addBMListener: has cached msg");
                List<Map<String, Object>> listC = g.a().c();
                ArrayList<Map<String, Object>> arrayList = new ArrayList();
                Iterator<Map<String, Object>> it2 = listC.iterator();
                while (it2.hasNext()) {
                    Map<String, Object> next = it2.next();
                    Object obj = next.get("bisType");
                    if (obj == null) {
                        iIntValue = 0;
                    } else {
                        iIntValue = ((Integer) obj).intValue();
                    }
                    final String str = (String) next.get("workId");
                    final String str2 = (String) next.get(g.a);
                    cn.fly.mcl.c.b.a().b("TP tpHelper addBMListener: cachedBisType = " + iIntValue + ", target bisType = " + numValueOf);
                    if (iIntValue != numValueOf.intValue()) {
                        it = it2;
                    } else {
                        Iterator<BusinessMessageListener> it3 = hashSet.iterator();
                        while (it3.hasNext()) {
                            final BusinessMessageListener next2 = it3.next();
                            final int i2 = iIntValue;
                            UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.mcl.tcp.h.1
                                @Override // android.os.Handler.Callback
                                public boolean handleMessage(Message message) {
                                    if (next2 != null) {
                                        try {
                                            cn.fly.mcl.c.b.a().b("TP tpHelper addBMListener: callback to messageReceived. bisType: " + i2 + ", workId: " + str + ", msg: " + str2);
                                            next2.messageReceived(i2, str, str2);
                                            return false;
                                        } catch (Throwable th) {
                                            cn.fly.mcl.c.b.a().a(th);
                                            return false;
                                        }
                                    }
                                    return false;
                                }
                            });
                            it2 = it2;
                        }
                        it = it2;
                        cn.fly.mcl.c.b.a().b("TP tpHelper addBMListener: mark msg to rm. msg = " + next);
                        arrayList.add(next);
                    }
                    it2 = it;
                }
                if (!arrayList.isEmpty()) {
                    for (Map<String, Object> map : arrayList) {
                        cn.fly.mcl.c.b.a().b("TP tpHelper addBMListener: rm msg = " + map);
                        g.a().b(map);
                    }
                }
                return;
            }
            cn.fly.mcl.c.b.a().b("TP tpHelper addBMListener: no cached msg");
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().b("TP tpHelper addBMListener: error");
            cn.fly.mcl.c.b.a().a(th);
        }
    }

    public void a(Context context, String str, String str2) {
        this.t = context;
        this.r = str;
        if (!TextUtils.isEmpty(str2)) {
            this.s = str2;
        }
        g();
        ActivityTracker.getInstance(context).addTracker(cn.fly.mcl.a.a(new a.C0009a() { // from class: cn.fly.mcl.tcp.h.5
            @Override // cn.fly.mcl.a.C0009a
            public void a() {
                h.this.k();
            }

            @Override // cn.fly.mcl.a.C0009a
            public void b() {
                h.this.k();
            }
        }));
        this.x.a();
    }

    public HashMap<String, Object> a(String str, String str2, int i) throws Throwable {
        if (this.q != null) {
            cn.fly.mcl.c.b.a().b("TP rg main = " + str + " , bo = " + str2 + " , out = " + i);
            String[] strArrSplit = str.split(":");
            this.q.a(new InetSocketAddress(strArrSplit[0], Integer.parseInt(strArrSplit[1])), true, true, 5000);
            e eVar = new e(1001, b(this.b, str2));
            eVar.c = this.a;
            e eVar2 = this.q.a(eVar).get(i, TimeUnit.MILLISECONDS);
            if (eVar2 != null && eVar2.b == 1000) {
                eVar2.d = a(this.b, eVar2.d);
                return b(eVar2.d);
            }
            cn.fly.mcl.c.b.a().b("TP rp : " + eVar2);
            return null;
        }
        return null;
    }

    public HashMap<String, Object> a(int i, String str) {
        return a(i, 10000, str);
    }

    public HashMap<String, Object> a(int i, int i2, String str) {
        e eVar;
        if (this.q != null) {
            try {
                String strB = b(this.c.get());
                cn.fly.mcl.c.b.a().b("TP sd ty = " + i + " , bo = " + str + " , out = " + i2);
                if (TextUtils.isEmpty(str)) {
                    eVar = new e(i);
                } else {
                    eVar = new e(i, b(strB, str));
                }
                c cVarA = this.q.a(eVar);
                if (cVarA != null) {
                    e eVar2 = cVarA.get(i2, TimeUnit.MILLISECONDS);
                    if (eVar2 != null && eVar2.b == 1000) {
                        eVar2.d = a(strB, eVar2.d);
                        return b(eVar2.d);
                    }
                    cn.fly.mcl.c.b.a().b("TP rp : " + eVar2);
                    return null;
                }
                cn.fly.mcl.c.b.a().b("TP rp : null");
                return null;
            } catch (Throwable th) {
                cn.fly.mcl.c.b.a().a(th);
                return null;
            }
        }
        return null;
    }

    private void a(long j, boolean z) {
        if (this.q != null) {
            try {
                String strB = b(this.c.get());
                HashMap map = new HashMap();
                map.put("state", Boolean.valueOf(z));
                String strFromHashMap = HashonHelper.fromHashMap(map);
                e eVar = new e(PointerIconCompat.TYPE_CELL, b(strB, strFromHashMap));
                cn.fly.mcl.c.b.a().b("TP sd ty = " + eVar.b + " , u = " + j + " bo : " + strFromHashMap);
                eVar.c = j;
                this.q.a(eVar);
            } catch (Throwable th) {
                cn.fly.mcl.c.b.a().a(th);
            }
        }
    }

    private c a(long j) {
        if (this.q != null) {
            try {
                e eVar = new e(WebSocketProtocol.CLOSE_NO_STATUS_CODE);
                eVar.c = j;
                c cVarA = this.q.a(eVar);
                cn.fly.mcl.c.b.a().b("TP sd ty = " + eVar.b + " , u = " + j + " bo : " + eVar.d);
                return cVarA;
            } catch (Throwable th) {
                cn.fly.mcl.c.b.a().a(th);
                return null;
            }
        }
        return null;
    }

    private void b(long j, boolean z) {
        if (this.q != null) {
            try {
                String strB = b(this.c.get());
                HashMap map = new HashMap();
                map.put("repeat", Boolean.valueOf(z));
                String strFromHashMap = HashonHelper.fromHashMap(map);
                e eVar = new e(PointerIconCompat.TYPE_CROSSHAIR, b(strB, strFromHashMap));
                eVar.c = j;
                this.q.a(eVar);
                cn.fly.mcl.c.b.a().b("TP sd ty = " + eVar.b + " , u = " + j + " bo : " + strFromHashMap);
            } catch (Throwable th) {
                cn.fly.mcl.c.b.a().a(th);
            }
        }
    }

    public boolean a(int i, int i2) {
        if (i2 < 4) {
            if (a(PointerIconCompat.TYPE_HAND, i, (String) null) != null) {
                return true;
            }
            if (i2 == 0 || i2 == 1) {
                a(1000, i2 + 1);
                return false;
            }
            a(3000, i2 + 1);
            return false;
        }
        return false;
    }

    public void a(BusinessCallBack<Boolean> businessCallBack) {
        boolean zC = c();
        if (businessCallBack != null) {
            businessCallBack.callback(Boolean.valueOf(zC));
        }
        if (!zC) {
            cn.fly.mcl.b.a.a.execute(new Runnable() { // from class: cn.fly.mcl.tcp.h.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (!h.b().d()) {
                            h.b().f();
                        }
                        h.this.a(new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mcl.tcp.h.6.1
                            @Override // cn.fly.tools.utils.d
                            public void a(Boolean bool) {
                            }
                        });
                    } catch (Throwable th) {
                    }
                }
            });
        }
    }

    public void b(final BusinessCallBack<Boolean> businessCallBack) {
        final boolean zC = c();
        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.mcl.tcp.h.7
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                try {
                    if (businessCallBack != null) {
                        businessCallBack.callback(Boolean.valueOf(zC));
                        return false;
                    }
                    return false;
                } catch (Throwable th) {
                    cn.fly.mcl.c.b.a().a(th);
                    return false;
                }
            }
        });
    }

    public boolean c() {
        return (this.q == null || !this.q.b() || this.c.get() == 0) ? false : true;
    }

    public boolean d() {
        return this.g && this.h && !this.w && this.d != null && this.d.size() > 0 && !TextUtils.isEmpty(this.b);
    }

    public boolean e() {
        return d() && this.i;
    }

    public void f() {
        boolean zBooleanValue;
        long jIntValue;
        boolean zIsInMainProcess = DH.SyncMtd.isInMainProcess();
        cn.fly.mcl.c.b.a().b("TP cf, main p: " + zIsInMainProcess);
        if (!zIsInMainProcess) {
            return;
        }
        if (TextUtils.isEmpty(this.s) || this.t == null) {
            cn.fly.mcl.c.b.a().b("TP mcl has not been initialized");
            return;
        }
        try {
            String strB = ad.b().b("tcp_config", (String) null);
            if (!TextUtils.isEmpty(strB)) {
                HashMap<String, Object> mapFromJson = HashonHelper.fromJson(strB);
                if (mapFromJson.containsKey("requestTimes")) {
                    Object obj = mapFromJson.get("requestTimes");
                    if (obj != null && (obj instanceof Long)) {
                        jIntValue = ((Long) obj).longValue();
                    } else if (obj != null && (obj instanceof Integer)) {
                        jIntValue = ((Integer) obj).intValue();
                    } else {
                        jIntValue = 0;
                    }
                    if (jIntValue + 86400000 > System.currentTimeMillis() && b().a(mapFromJson) && ad.b().b("use_config", true)) {
                        cn.fly.mcl.c.b.a().b("TP cfg src: cc" + strB);
                        return;
                    }
                }
            }
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().b(th.getMessage());
        }
        try {
            cn.fly.mcl.c.b.a().b("TP cfg no cc");
            HashMap map = (HashMap) cn.fly.commons.c.a("sti", (Object) null);
            HashMap<String, Object> map2 = new HashMap<>();
            if (map != null && !map.isEmpty() && !PSIDManager.a().e()) {
                cn.fly.mcl.c.b.a().b("TP cfg src: g*f");
                map2.put(w.b("004bOcjcbXe"), Integer.valueOf(Opcodes.GOTO_W));
                map2.put(w.b("004Kcb)chc"), map);
            } else {
                final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
                DH.requester(FlySDK.getContext()).checkNetworkAvailableForce(true).request(new DH.DHResponder() { // from class: cn.fly.mcl.tcp.h.8
                    @Override // cn.fly.tools.utils.DH.DHResponder
                    public void onResponse(DH.DHResponse dHResponse) throws Throwable {
                        linkedBlockingQueue.offer(Boolean.valueOf(dHResponse.checkNetworkAvailableForce(new int[0])));
                    }
                });
                try {
                    zBooleanValue = ((Boolean) linkedBlockingQueue.poll(1000L, TimeUnit.MILLISECONDS)).booleanValue();
                } catch (Throwable th2) {
                    cn.fly.mcl.c.b.a().a(th2);
                    zBooleanValue = false;
                }
                cn.fly.mcl.c.b.a().b("TP cfg net: " + zBooleanValue);
                if (zBooleanValue) {
                    cn.fly.mcl.c.b.a().b("TP cfg src: init");
                    NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
                    networkTimeOut.readTimout = 10000;
                    networkTimeOut.connectionTimeout = 5000;
                    String str = "/tcp/config/init";
                    HashMap<String, Object> map3 = new HashMap<>();
                    map3.put(w.b("006cii0dgNeEdb"), this.r);
                    map3.put("pushId", PSIDManager.a().d());
                    map3.put("products", j());
                    if (ac.d() == 2) {
                        map3.put(w.b("004Wcbcfchcb"), this.s);
                        map3.put(w.b("006ciiiSdgdi"), DH.SyncMtd.getPackageName());
                        str = "/tcp/config/v2/init";
                    }
                    String str2 = j.a().a("tcig") + str;
                    cn.fly.mcl.c.b.a().b("TP cf url : " + str2 + " -> bd : " + map3);
                    String strHttpPostNew = this.o.httpPostNew(str2, map3, null, networkTimeOut);
                    cn.fly.mcl.c.b.a().b("TP cf url : " + str2 + " -> rp : " + strHttpPostNew);
                    map2 = HashonHelper.fromJson(strHttpPostNew);
                }
            }
            if (!map2.isEmpty()) {
                map2.put("requestTimes", Long.valueOf(System.currentTimeMillis()));
                if (b().a(map2)) {
                    ad.b().a("use_config", true);
                    ad.b().a("tcp_config", HashonHelper.fromHashMap(map2));
                }
            }
        } catch (Throwable th3) {
            cn.fly.mcl.c.b.a().b(th3.getMessage());
        }
    }

    private String j() {
        ArrayList<FlyProduct> arrayListB = ac.b();
        HashMap<String, Object> mapB = o.a().b();
        StringBuilder sb = new StringBuilder("COMMON;" + FlySDK.SDK_VERSION_CODE);
        int size = arrayListB.size();
        for (int i = 0; i < size; i++) {
            try {
                FlyProduct flyProduct = arrayListB.get(i);
                if (!TextUtils.equals(w.b("0068dcfggbgbfgdf"), flyProduct.getProductTag())) {
                    sb.append(",");
                    sb.append(flyProduct.getProductTag()).append(";").append(flyProduct.getSdkver()).append(";").append(mapB.get(flyProduct.getProductTag()));
                }
            } catch (Throwable th) {
            }
        }
        return sb.toString();
    }

    private boolean a(HashMap<String, Object> map) {
        try {
            this.m = false;
            HashMap<String, Object> mapB = b(map);
            if (mapB.containsKey("domains") && mapB.containsKey("uniqueId") && mapB.containsKey("uniqueKey")) {
                this.d = (ArrayList) mapB.get("domains");
                this.a = ((Long) mapB.get("uniqueId")).longValue();
                this.b = (String) mapB.get("uniqueKey");
                this.e = a(mapB, "tick", this.e);
                this.g = a(mapB, "globalSwitch", 0) == 1;
                this.h = a(mapB, "connectSwitch", 0) == 1;
                this.i = a(mapB, "forwardSwitch", 0) == 1;
                this.j = a(mapB, "bindRequestSwitch", 0) == 1;
                this.f = a(mapB, "wr", this.f);
                if (mapB.containsKey("determineDomain")) {
                    String str = (String) mapB.get("determineDomain");
                    if (!TextUtils.isEmpty(str)) {
                        if (this.d == null) {
                            this.d = new ArrayList<>();
                        }
                        this.d.remove(str);
                        this.d.add(0, str);
                    }
                }
                if (this.d != null && this.d.size() > 0 && !TextUtils.isEmpty(this.b)) {
                    cn.fly.mcl.c.b.a().b("tp cfg load: s");
                    return true;
                }
            }
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
        }
        cn.fly.mcl.c.b.a().b("tp cfg load: f");
        return false;
    }

    public void a(cn.fly.tools.utils.d<Boolean> dVar) {
        a(5000, dVar);
    }

    public synchronized void a(final int i, final cn.fly.tools.utils.d<Boolean> dVar) {
        if (d()) {
            c(new cn.fly.tools.utils.d<String>() { // from class: cn.fly.mcl.tcp.h.9
                @Override // cn.fly.tools.utils.d
                public void a(String str) {
                    boolean zA = h.this.a(h.this.m, h.this.d.get(0), 0, str, i);
                    if (dVar != null) {
                        dVar.a(Boolean.valueOf(zA));
                    }
                }
            });
        } else {
            b().a(TcpStatus.obtain(21).setDetailedMsg("unavailable(global: " + this.g + ", connect: " + this.h + ", forceClose:" + this.w + ")"));
            if (dVar != null) {
                dVar.a(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean a(boolean z, String str, int i, String str2, int i2) {
        String message;
        try {
            if (i < this.d.size() && i < 3) {
                cn.fly.mcl.c.b.a().b("TP rg domain : " + str + " count : " + i);
                try {
                    HashMap<String, Object> mapA = a(str, str2, i2);
                    if (mapA != null && mapA.containsKey(w.b("004h-dbEie"))) {
                        int iIntValue = ((Integer) mapA.get(w.b("004h_dbRie"))).intValue();
                        if (iIntValue == 1 && mapA.containsKey(w.b("005h]cjdg1ed"))) {
                            this.c.set(((Long) mapA.get(w.b("005hQcjdg1ed"))).longValue());
                            b.a().b();
                            cn.fly.mcl.c.b.a().a("TP register success");
                            PSIDManager.a().f();
                            b().a(TcpStatus.obtain(10));
                            return true;
                        }
                        if (iIntValue == 2 && mapA.containsKey(w.b("006Ycbcjce c2ch^d"))) {
                            String str3 = (String) mapA.get(w.b("006FcbcjceMc3chMd"));
                            if (!TextUtils.isEmpty(str3)) {
                                return a(true, str3, 2, str2, i2);
                            }
                        } else if (iIntValue == 3) {
                            this.w = true;
                            this.q.a();
                            PSIDManager.a().f();
                            b().a(TcpStatus.obtain(24).setDetailedMsg("Connection out of limit"));
                            return false;
                        }
                    }
                } catch (Throwable th) {
                    cn.fly.mcl.c.b.a().b("TP register exp : " + th.getMessage());
                }
                int i3 = i + 1;
                if (i3 < this.d.size() && !z) {
                    return a(false, this.d.get(i3), i3, str2, i2);
                }
            }
            message = null;
            ad.b().a("tcp_config", (String) null);
            this.d = null;
        } catch (Throwable th2) {
            message = th2.getMessage();
            cn.fly.mcl.c.b.a().a(th2);
        }
        PSIDManager.a().f();
        b().a(TcpStatus.obtain(24).setDetailedMsg("Exception: " + message));
        return false;
    }

    private void c(final cn.fly.tools.utils.d<String> dVar) {
        final HashMap map = new HashMap();
        try {
            map.put(w.b("006ciiMdgSe)db"), this.r);
            map.put(w.b("006ciiiVdgdi"), this.t.getPackageName());
            map.put(w.b("004ifch"), 1);
            map.put("pushId", PSIDManager.a().d());
            if (ac.d() == 2) {
                map.put(w.b("004Pcbcfchcb"), this.s);
            }
            map.put("guardId", this.k);
            DH.requester(this.t).getMpfo(this.t.getPackageName(), 128).request(new DH.DHResponder() { // from class: cn.fly.mcl.tcp.h.10
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    ApplicationInfo applicationInfoA;
                    Bundle bundle;
                    Object obj;
                    Object mpfo = dHResponse.getMpfo(new int[0]);
                    if (mpfo != null && (applicationInfoA = cn.fly.tools.c.a(mpfo, h.this.t.getPackageName())) != null && (bundle = applicationInfoA.metaData) != null && !bundle.isEmpty() && (obj = bundle.get("fly_id_ver")) != null) {
                        map.put(w.b("0077ccKe ciehchcj6d"), String.valueOf(obj));
                    }
                    HashonHelper unused = h.this.p;
                    String strFromHashMap = HashonHelper.fromHashMap(map);
                    if (dVar != null) {
                        dVar.a(strFromHashMap);
                    }
                }
            });
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
            String strFromHashMap = HashonHelper.fromHashMap(map);
            if (dVar != null) {
                dVar.a(strFromHashMap);
            }
        }
    }

    public void g() {
        if (TextUtils.isEmpty(this.k) || this.l <= 0) {
            String strB = ad.b().b("suid", "");
            long jB = ad.b().b("create_suid_time", 0L);
            if (TextUtils.isEmpty(strB)) {
                strB = UUID.randomUUID().toString();
            }
            if (jB <= 0) {
                jB = System.currentTimeMillis();
            }
            a(strB, jB);
        }
    }

    public synchronized void a(String str, long j) {
        if (this.y != null && !String.valueOf(this.k).equals(str)) {
            this.y.onChanged(this.k, str);
        }
        this.k = str;
        this.l = j;
        ad.b().a("suid", this.k);
        ad.b().a("create_suid_time", this.l);
    }

    public void b(final cn.fly.tools.utils.d<Boolean> dVar) {
        c(new cn.fly.tools.utils.d<String>() { // from class: cn.fly.mcl.tcp.h.11
            @Override // cn.fly.tools.utils.d
            public void a(String str) {
                if (h.this.a(PointerIconCompat.TYPE_HELP, str) != null) {
                    if (dVar != null) {
                        dVar.a(true);
                    }
                } else if (dVar != null) {
                    dVar.a(false);
                }
            }
        });
    }

    @Override // cn.fly.mcl.tcp.d
    public void a(a aVar, e eVar) {
        try {
            c cVarA = a(eVar.c);
            if (TextUtils.isEmpty(eVar.d)) {
                return;
            }
            if (this.c.get() == 0) {
                cn.fly.mcl.c.b.a().b("TP received push msg, but send token is 0");
                return;
            }
            eVar.d = a(b(this.c.get()), eVar.d);
            if (eVar.b == 9001) {
                cn.fly.mcl.c.b.a().b("TP msg push msgType: " + eVar.b + " body = " + eVar.d);
                HashMap<String, Object> mapB = b(eVar.d);
                if (mapB.containsKey(w.b("004QcbSchc"))) {
                    int iA = a(mapB, "expire", 0);
                    String str = (String) mapB.get("workId");
                    String str2 = (String) mapB.get(w.b("004>cbBchc"));
                    boolean z = a(mapB, "needRepeat", 0) == 1;
                    int iA2 = a(mapB, w.b("004h6db ie"), 0);
                    if (iA2 != 1 && iA2 != 2) {
                        boolean zA = a(eVar.c, str, iA, iA2, str2, cVarA);
                        if (z) {
                            b(eVar.c, zA);
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(w.b("004EcbYchc"), str2);
                    bundle.putInt("expire", iA);
                    bundle.putString("workId", str);
                    bundle.putLong("uniqueId", eVar.c);
                    bundle.putInt("msgType", iA2);
                    boolean z2 = a(bundle) == 1;
                    if (z) {
                        b(eVar.c, z2);
                        return;
                    }
                    return;
                }
                return;
            }
            if (eVar.b == 9002) {
                final String str3 = (String) b(eVar.d).get(w.b("006:cbcjce%cZchOd"));
                if (!TextUtils.isEmpty(str3)) {
                    this.m = true;
                    c(new cn.fly.tools.utils.d<String>() { // from class: cn.fly.mcl.tcp.h.12
                        @Override // cn.fly.tools.utils.d
                        public void a(String str4) {
                            h.this.a(true, str3, 2, str4, 5000);
                        }
                    });
                    return;
                }
                return;
            }
            if (eVar.b == 9004) {
                cn.fly.mcl.c.b.a().b("TP mg ty: " + eVar.b + " bo = " + eVar.d);
                a(eVar.c);
                HashMap<String, Object> mapB2 = b(eVar.d);
                if (mapB2.containsKey(w.b("0040cb)chc")) && mapB2.containsKey("targetPackage")) {
                    String str4 = (String) mapB2.get("targetPackage");
                    String str5 = (String) mapB2.get(w.b("004[cbHchc"));
                    int iA3 = a(mapB2, "logicTimeout", 1000);
                    if (!TextUtils.isEmpty(str5) && !TextUtils.isEmpty(str4)) {
                        Bundle bundle2 = new Bundle();
                        bundle2.putString(w.b("004Ncb9chc"), str5);
                        bundle2.putLong("uniqueId", eVar.c);
                        cn.fly.apc.a aVarA = cn.fly.mcl.a.a.a().a(9004, bundle2, str4, iA3);
                        if (aVarA != null && aVarA.e != null) {
                            a(eVar.c, true);
                        } else {
                            cn.fly.mcl.c.b.a().b("TP apc fw rp mg is null");
                            a(eVar.c, false);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
        }
    }

    public int a(Bundle bundle) {
        if (this.u != null) {
            if (a(bundle.getString("workId"), bundle.getInt("expire"))) {
                return 1;
            }
            return this.u.messageReceived(bundle) ? 1 : 0;
        }
        return -1;
    }

    private synchronized boolean a(String str, int i) {
        if (i != 0) {
            if (!TextUtils.isEmpty(str)) {
                if (System.currentTimeMillis() <= this.x.a(str)) {
                    return true;
                }
                this.x.a(str, System.currentTimeMillis() + (i * 1000));
            }
        }
        return false;
    }

    public boolean a(long j, String str, int i, int i2, String str2) {
        try {
            if (a(str, i)) {
                return true;
            }
            a(j, str, i2, str2);
            return false;
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
            return false;
        }
    }

    public boolean a(final long j, final String str, int i, final int i2, final String str2, final c cVar) {
        try {
            if (a(str, i)) {
                return true;
            }
            ab.a.execute(new i() { // from class: cn.fly.mcl.tcp.h.2
                @Override // cn.fly.tools.utils.i
                protected void a() throws Throwable {
                    e eVar;
                    int i3;
                    if (cVar != null) {
                        try {
                            eVar = cVar.get(h.this.f, TimeUnit.MILLISECONDS);
                        } catch (Throwable th) {
                            cn.fly.mcl.c.b.a().a(th);
                            eVar = null;
                        }
                        if (eVar != null && eVar.b == 1000) {
                            cn.fly.mcl.c.b.a().b("TP rp acked: ");
                            i3 = 1;
                        } else {
                            cn.fly.mcl.c.b.a().b("TP rp : " + eVar);
                            i3 = 0;
                        }
                    } else {
                        i3 = 0;
                    }
                    h.this.a(j, str, i2, str2, i3);
                }
            });
            return false;
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
            return false;
        }
    }

    private void a(long j, String str, int i, String str2) {
        a(j, str, i, str2, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j, final String str, int i, String str2, final int i2) {
        try {
            HashMap mapFromJson = HashonHelper.fromJson(str2);
            mapFromJson.put("uniqueId", Long.valueOf(j));
            final String strFromHashMap = HashonHelper.fromHashMap(mapFromJson);
            final Integer numValueOf = Integer.valueOf(i);
            if (this.v.containsKey(numValueOf)) {
                cn.fly.mcl.c.b.a().b("[dealBusinessMsg]TP Biz msg listener detected, callback directly. bisType: " + numValueOf);
                Iterator<BusinessMessageListener> it = this.v.get(numValueOf).iterator();
                while (it.hasNext()) {
                    final BusinessMessageListener next = it.next();
                    UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.mcl.tcp.h.3
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            try {
                                if (next != null) {
                                    if (next instanceof BusinessMessageCallback) {
                                        cn.fly.mcl.c.b.a().b("[dealBusinessMsg]TP callback to messageReceived with st. bisType: " + numValueOf + ", workId: " + str + ", msg: " + strFromHashMap);
                                        ((BusinessMessageCallback) next).messageReceived(i2, numValueOf.intValue(), str, strFromHashMap);
                                    } else {
                                        cn.fly.mcl.c.b.a().b("[dealBusinessMsg]TP callback to messageReceived. bisType: " + numValueOf + ", workId: " + str + ", msg: " + strFromHashMap);
                                        next.messageReceived(numValueOf.intValue(), str, strFromHashMap);
                                    }
                                }
                                return false;
                            } catch (Throwable th) {
                                cn.fly.mcl.c.b.a().a(th);
                                return false;
                            }
                        }
                    });
                }
                return;
            }
            cn.fly.mcl.c.b.a().b("[dealBusinessMsg]TP No biz msg listener detected, cache msg. bisType: " + numValueOf);
            HashMap map = new HashMap();
            map.put("bisType", numValueOf);
            map.put("workId", str);
            map.put(g.a, strFromHashMap);
            g.a().a(map);
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
        }
    }

    @Override // cn.fly.mcl.tcp.d
    public void a(a aVar, Throwable th) {
        cn.fly.mcl.c.b.a().b("TP exceptionCaught : " + (th != null ? th.getMessage() : ""));
    }

    @Override // cn.fly.mcl.tcp.d
    public void a(a aVar) {
        cn.fly.mcl.c.b.a().b("TP sessionOpened");
    }

    @Override // cn.fly.mcl.tcp.d
    public void a(a aVar, boolean z) {
        cn.fly.mcl.c.b.a().b("TP sessionClosed " + z);
        b.a().c();
        if (z) {
            l();
        }
    }

    public String a(String str, String str2) throws Throwable {
        return Data.AES128Decode(str, Base64.decode(str2, 2));
    }

    private String b(String str, String str2) throws Throwable {
        return Base64.encodeToString(Data.AES128Encode(str, str2), 2);
    }

    private String b(long j) {
        return String.format("%16s", Integer.valueOf(Math.abs(Arrays.hashCode(new long[]{j})))).replaceAll(" ", "0").substring(0, 16);
    }

    public String h() throws PSIDManager.NoPsrdException {
        return String.format("%16s", Integer.valueOf(Math.abs(Arrays.hashCode(new Object[]{this.r, PSIDManager.a().d()})))).replaceAll(" ", "0").substring(0, 16);
    }

    private HashMap<String, Object> b(HashMap<String, Object> map) {
        HashMap<String, Object> map2 = new HashMap<>();
        if (a(map, w.b("004b$cjcbCe"), 0) == 200 && map.containsKey(w.b("0040cbIchc"))) {
            return (HashMap) map.get(w.b("004Mcb[chc"));
        }
        return map2;
    }

    private HashMap<String, Object> b(String str) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (!TextUtils.isEmpty(str) && str.startsWith("{")) {
                cn.fly.mcl.c.b.a().b(str);
                return b(HashonHelper.fromJson(str));
            }
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
        }
        return map;
    }

    public static int a(HashMap<String, Object> map, String str, int i) {
        if (map != null && map.containsKey(str)) {
            Object obj = map.get(str);
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        if (c()) {
            return;
        }
        l();
    }

    private void l() {
        cn.fly.mcl.b.a.a.execute(new Runnable() { // from class: cn.fly.mcl.tcp.h.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (!h.this.c()) {
                        if (!h.this.d()) {
                            h.this.f();
                        }
                        h.this.a(new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mcl.tcp.h.4.1
                            @Override // cn.fly.tools.utils.d
                            public void a(Boolean bool) {
                            }
                        });
                    }
                } catch (Throwable th) {
                }
            }
        });
    }

    public void a(TcpStatusListener tcpStatusListener) {
        this.z = tcpStatusListener;
    }

    public void b(TcpStatusListener tcpStatusListener) {
        if (this.z == tcpStatusListener) {
            this.z = null;
        }
    }

    public void a(TcpStatus tcpStatus) {
        this.A = tcpStatus;
    }

    public void b(TcpStatus tcpStatus) {
        if (this.z != null) {
            TcpStatusListener tcpStatusListener = this.z;
            if (this.A != null) {
                tcpStatus = this.A;
            }
            tcpStatusListener.onStatus(tcpStatus);
            i();
        }
    }

    public void i() {
        this.A = null;
    }
}