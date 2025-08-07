package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public class a {
    private static Proxy e;
    protected HashMap<String, HashMap<String, byte[]>> a = new HashMap<>();
    protected String b;
    i c;
    private HashMap<String, Object> d;

    public static aj a(int i) {
        if (i == 1) {
            return new ai();
        }
        if (i == 3) {
            return new ah();
        }
        return null;
    }

    a() {
        new HashMap();
        this.d = new HashMap<>();
        this.b = "GBK";
        this.c = new i();
    }

    public static void a(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            e = null;
        } else {
            e = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i));
        }
    }

    public static void a(InetAddress inetAddress, int i) {
        if (inetAddress == null) {
            e = null;
        } else {
            e = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(inetAddress, i));
        }
    }

    public void a(String str) {
        this.b = str;
    }

    public static at a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        at atVar = new at();
        atVar.a = userInfoBean.e;
        atVar.e = userInfoBean.j;
        atVar.d = userInfoBean.c;
        atVar.c = userInfoBean.d;
        atVar.g = com.tencent.bugly.crashreport.common.info.a.b().i();
        atVar.h = userInfoBean.o == 1;
        switch (userInfoBean.b) {
            case 1:
                atVar.b = (byte) 1;
                break;
            case 2:
                atVar.b = (byte) 4;
                break;
            case 3:
                atVar.b = (byte) 2;
                break;
            case 4:
                atVar.b = (byte) 3;
                break;
            default:
                if (userInfoBean.b < 10 || userInfoBean.b >= 20) {
                    x.e("unknown uinfo type %d ", Integer.valueOf(userInfoBean.b));
                    return null;
                }
                atVar.b = (byte) userInfoBean.b;
                break;
                break;
        }
        atVar.f = new HashMap();
        if (userInfoBean.p >= 0) {
            atVar.f.put("C01", new StringBuilder().append(userInfoBean.p).toString());
        }
        if (userInfoBean.q >= 0) {
            atVar.f.put("C02", new StringBuilder().append(userInfoBean.q).toString());
        }
        if (userInfoBean.r != null && userInfoBean.r.size() > 0) {
            for (Map.Entry<String, String> entry : userInfoBean.r.entrySet()) {
                atVar.f.put("C03_" + entry.getKey(), entry.getValue());
            }
        }
        if (userInfoBean.s != null && userInfoBean.s.size() > 0) {
            for (Map.Entry<String, String> entry2 : userInfoBean.s.entrySet()) {
                atVar.f.put("C04_" + entry2.getKey(), entry2.getValue());
            }
        }
        atVar.f.put("A36", new StringBuilder().append(!userInfoBean.l).toString());
        atVar.f.put("F02", new StringBuilder().append(userInfoBean.g).toString());
        atVar.f.put("F03", new StringBuilder().append(userInfoBean.h).toString());
        atVar.f.put("F04", userInfoBean.j);
        atVar.f.put("F05", new StringBuilder().append(userInfoBean.i).toString());
        atVar.f.put("F06", userInfoBean.m);
        atVar.f.put("F10", new StringBuilder().append(userInfoBean.k).toString());
        x.c("summary type %d vm:%d", Byte.valueOf(atVar.b), Integer.valueOf(atVar.f.size()));
        return atVar;
    }

    public static Proxy b() {
        return e;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00c5 A[PHI: r7
  0x00c5: PHI (r7v2 java.lang.String) = 
  (r7v1 java.lang.String)
  (r7v1 java.lang.String)
  (r7v3 java.lang.String)
  (r7v3 java.lang.String)
  (r7v4 java.lang.String)
  (r7v4 java.lang.String)
 binds: [B:21:0x0055, B:23:0x005b, B:26:0x0067, B:28:0x006d, B:36:0x0089, B:38:0x008f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (true) {
            String str = "map";
            if (i < arrayList.size()) {
                String str2 = arrayList.get(i);
                if (str2.equals("java.lang.Integer") || str2.equals("int")) {
                    str = "int32";
                } else if (str2.equals("java.lang.Boolean") || str2.equals("boolean")) {
                    str = "bool";
                } else if (str2.equals("java.lang.Byte") || str2.equals("byte")) {
                    str = "char";
                } else {
                    String str3 = "double";
                    if (str2.equals("java.lang.Double") || str2.equals("double")) {
                        str = str3;
                    } else {
                        str3 = "float";
                        if (!str2.equals("java.lang.Float") && !str2.equals("float")) {
                            if (str2.equals("java.lang.Long") || str2.equals("long")) {
                                str = "int64";
                            } else {
                                str3 = "short";
                                if (!str2.equals("java.lang.Short") && !str2.equals("short")) {
                                    if (str2.equals("java.lang.Character")) {
                                        throw new IllegalArgumentException("can not support java.lang.Character");
                                    }
                                    if (str2.equals("java.lang.String")) {
                                        str = "string";
                                    } else if (str2.equals("java.util.List")) {
                                        str = "list";
                                    } else if (!str2.equals("java.util.Map")) {
                                        str = str2;
                                    }
                                }
                            }
                        }
                    }
                }
                arrayList.set(i, str);
                i++;
            } else {
                Collections.reverse(arrayList);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    String str4 = arrayList.get(i2);
                    if (str4.equals("list")) {
                        int i3 = i2 - 1;
                        arrayList.set(i3, "<" + arrayList.get(i3));
                        arrayList.set(0, arrayList.get(0) + ">");
                    } else if (str4.equals("map")) {
                        int i4 = i2 - 1;
                        arrayList.set(i4, "<" + arrayList.get(i4) + ",");
                        arrayList.set(0, arrayList.get(0) + ">");
                    } else if (str4.equals("Array")) {
                        int i5 = i2 - 1;
                        arrayList.set(i5, "<" + arrayList.get(i5));
                        arrayList.set(0, arrayList.get(0) + ">");
                    }
                }
                Collections.reverse(arrayList);
                Iterator<String> it = arrayList.iterator();
                while (it.hasNext()) {
                    stringBuffer.append(it.next());
                }
                return stringBuffer.toString();
            }
        }
    }

    public <T> void a(String str, T t) throws UnsupportedEncodingException {
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        }
        if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        }
        if (t instanceof Set) {
            throw new IllegalArgumentException("can not support Set");
        }
        j jVar = new j();
        jVar.a(this.b);
        jVar.a(t, 0);
        byte[] bArrA = l.a(jVar.a());
        HashMap<String, byte[]> map = new HashMap<>(1);
        ArrayList<String> arrayList = new ArrayList<>(1);
        a(arrayList, t);
        map.put(a(arrayList), bArrA);
        this.d.remove(str);
        this.a.put(str, map);
    }

    public static au a(List<UserInfoBean> list, int i) {
        com.tencent.bugly.crashreport.common.info.a aVarB;
        if (list == null || list.size() == 0 || (aVarB = com.tencent.bugly.crashreport.common.info.a.b()) == null) {
            return null;
        }
        aVarB.t();
        au auVar = new au();
        auVar.b = aVarB.d;
        auVar.c = aVarB.h();
        ArrayList<at> arrayList = new ArrayList<>();
        Iterator<UserInfoBean> it = list.iterator();
        while (it.hasNext()) {
            at atVarA = a(it.next());
            if (atVarA != null) {
                arrayList.add(atVarA);
            }
        }
        auVar.d = arrayList;
        auVar.e = new HashMap();
        auVar.e.put("A7", aVarB.g);
        auVar.e.put("A6", aVarB.s());
        auVar.e.put("A5", aVarB.r());
        auVar.e.put("A2", new StringBuilder().append(aVarB.p()).toString());
        auVar.e.put("A1", new StringBuilder().append(aVarB.p()).toString());
        auVar.e.put("A24", aVarB.i);
        auVar.e.put("A17", new StringBuilder().append(aVarB.q()).toString());
        auVar.e.put("A15", aVarB.w());
        auVar.e.put("A13", new StringBuilder().append(aVarB.x()).toString());
        auVar.e.put("F08", aVarB.w);
        auVar.e.put("F09", aVarB.x);
        Map<String, String> mapG = aVarB.G();
        if (mapG != null && mapG.size() > 0) {
            for (Map.Entry<String, String> entry : mapG.entrySet()) {
                auVar.e.put("C04_" + entry.getKey(), entry.getValue());
            }
        }
        switch (i) {
            case 1:
                auVar.a = (byte) 1;
                return auVar;
            case 2:
                auVar.a = (byte) 2;
                return auVar;
            default:
                x.e("unknown up type %d ", Integer.valueOf(i));
                return null;
        }
    }

    public static <T extends k> T a(byte[] bArr, Class<T> cls) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            T tNewInstance = cls.newInstance();
            i iVar = new i(bArr);
            iVar.a("utf-8");
            tNewInstance.a(iVar);
            return tNewInstance;
        } catch (Throwable th) {
            if (!x.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static ap a(Context context, int i, byte[] bArr) {
        com.tencent.bugly.crashreport.common.info.a aVarB = com.tencent.bugly.crashreport.common.info.a.b();
        StrategyBean strategyBeanC = com.tencent.bugly.crashreport.common.strategy.a.a().c();
        if (aVarB == null || strategyBeanC == null) {
            x.e("Can not create request pkg for parameters is invalid.", new Object[0]);
            return null;
        }
        try {
            ap apVar = new ap();
            synchronized (aVarB) {
                apVar.a = 1;
                apVar.b = aVarB.f();
                apVar.c = aVarB.c;
                apVar.d = aVarB.k;
                apVar.e = aVarB.m;
                apVar.f = aVarB.f;
                apVar.g = i;
                apVar.h = bArr == null ? "".getBytes() : bArr;
                apVar.i = aVarB.h;
                apVar.j = aVarB.i;
                apVar.k = new HashMap();
                apVar.l = aVarB.e();
                apVar.m = strategyBeanC.p;
                apVar.o = aVarB.h();
                apVar.p = com.tencent.bugly.crashreport.common.info.b.c(context);
                apVar.q = System.currentTimeMillis();
                apVar.r = aVarB.k();
                apVar.s = aVarB.j();
                apVar.t = aVarB.m();
                apVar.u = aVarB.l();
                apVar.v = aVarB.n();
                apVar.w = apVar.p;
                aVarB.getClass();
                apVar.n = "com.tencent.bugly";
                apVar.k.put("A26", aVarB.y());
                apVar.k.put("A60", aVarB.z());
                apVar.k.put("A61", aVarB.A());
                apVar.k.put("A62", new StringBuilder().append(aVarB.R()).toString());
                apVar.k.put("A63", new StringBuilder().append(aVarB.S()).toString());
                apVar.k.put("F11", new StringBuilder().append(aVarB.B).toString());
                apVar.k.put("F12", new StringBuilder().append(aVarB.A).toString());
                apVar.k.put("G1", aVarB.u());
                apVar.k.put("A64", aVarB.T());
                if (aVarB.D) {
                    apVar.k.put("G2", aVarB.L());
                    apVar.k.put("G3", aVarB.M());
                    apVar.k.put("G4", aVarB.N());
                    apVar.k.put("G5", aVarB.O());
                    apVar.k.put("G6", aVarB.P());
                    apVar.k.put("G7", Long.toString(aVarB.Q()));
                }
                apVar.k.put("D3", aVarB.l);
                if (com.tencent.bugly.b.b != null) {
                    for (com.tencent.bugly.a aVar : com.tencent.bugly.b.b) {
                        if (aVar.versionKey != null && aVar.version != null) {
                            apVar.k.put(aVar.versionKey, aVar.version);
                        }
                    }
                }
                apVar.k.put("G15", z.b("G15", ""));
                apVar.k.put("D4", z.b("D4", "0"));
            }
            u uVarA = u.a();
            if (uVarA != null && !uVarA.a && bArr != null) {
                apVar.h = z.a(apVar.h, 2, 1, strategyBeanC.u);
                if (apVar.h == null) {
                    x.e("reqPkg sbuffer error!", new Object[0]);
                    return null;
                }
            }
            Map<String, String> mapF = aVarB.F();
            if (mapF != null) {
                for (Map.Entry<String, String> entry : mapF.entrySet()) {
                    apVar.k.put(entry.getKey(), entry.getValue());
                }
            }
            return apVar;
        } catch (Throwable th) {
            if (!x.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private void a(ArrayList<String> arrayList, Object obj) {
        if (obj.getClass().isArray()) {
            if (!obj.getClass().getComponentType().toString().equals("byte")) {
                throw new IllegalArgumentException("only byte[] is supported");
            }
            if (Array.getLength(obj) > 0) {
                arrayList.add("java.util.List");
                a(arrayList, Array.get(obj, 0));
                return;
            } else {
                arrayList.add("Array");
                arrayList.add("?");
                return;
            }
        }
        if (obj instanceof Array) {
            throw new IllegalArgumentException("can not support Array, please use List");
        }
        if (obj instanceof List) {
            arrayList.add("java.util.List");
            List list = (List) obj;
            if (list.size() > 0) {
                a(arrayList, list.get(0));
                return;
            } else {
                arrayList.add("?");
                return;
            }
        }
        if (obj instanceof Map) {
            arrayList.add("java.util.Map");
            Map map = (Map) obj;
            if (map.size() > 0) {
                Object next = map.keySet().iterator().next();
                Object obj2 = map.get(next);
                arrayList.add(next.getClass().getName());
                a(arrayList, obj2);
                return;
            }
            arrayList.add("?");
            arrayList.add("?");
            return;
        }
        arrayList.add(obj.getClass().getName());
    }

    public byte[] a() throws UnsupportedEncodingException {
        j jVar = new j(0);
        jVar.a(this.b);
        jVar.a((Map) this.a, 0);
        return l.a(jVar.a());
    }

    public void a(byte[] bArr) {
        this.c.a(bArr);
        this.c.a(this.b);
        HashMap map = new HashMap(1);
        HashMap map2 = new HashMap(1);
        map2.put("", new byte[0]);
        map.put("", map2);
        this.a = this.c.a((Map) map, 0, false);
    }

    public static byte[] a(Object obj) {
        try {
            d dVar = new d();
            dVar.c();
            dVar.a("utf-8");
            dVar.b(1);
            dVar.b("RqdServer");
            dVar.c("sync");
            dVar.a("detail", (String) obj);
            return dVar.a();
        } catch (Throwable th) {
            if (!x.b(th)) {
                th.printStackTrace();
                return null;
            }
            return null;
        }
    }

    public static aq a(byte[] bArr, boolean z) {
        aq aqVar;
        if (bArr != null) {
            try {
                d dVar = new d();
                dVar.c();
                dVar.a("utf-8");
                dVar.a(bArr);
                Object objB = dVar.b("detail", new aq());
                if (!aq.class.isInstance(objB)) {
                    aqVar = null;
                } else {
                    aqVar = (aq) aq.class.cast(objB);
                }
                if (!z && aqVar != null && aqVar.c != null && aqVar.c.length > 0) {
                    x.c("resp buf %d", Integer.valueOf(aqVar.c.length));
                    aqVar.c = z.b(aqVar.c, 2, 1, StrategyBean.d);
                    if (aqVar.c == null) {
                        x.e("resp sbuffer error!", new Object[0]);
                        return null;
                    }
                }
                return aqVar;
            } catch (Throwable th) {
                if (!x.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] a(k kVar) {
        try {
            j jVar = new j();
            jVar.a("utf-8");
            kVar.a(jVar);
            return jVar.b();
        } catch (Throwable th) {
            if (!x.b(th)) {
                th.printStackTrace();
                return null;
            }
            return null;
        }
    }
}