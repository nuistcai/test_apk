package cn.fly.mcl.a;

import android.content.Context;
import android.os.Bundle;
import cn.fly.FlySDK;
import cn.fly.apc.b;
import cn.fly.commons.m;
import cn.fly.mcl.FlyMCL;
import cn.fly.mcl.tcp.h;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.network.StringPart;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class a {
    public static final String a = m.a("004AfePfkf");
    private static volatile a b;
    private volatile Set<String> c;
    private String d;

    public static a a() {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a();
                }
            }
        }
        return b;
    }

    private a() {
    }

    public void a(Context context, b.a aVar) {
        this.d = FlyMCL.SDK_TAG;
        cn.fly.apc.b.a(context);
        boolean zIsInMainProcess = DH.SyncMtd.isInMainProcess();
        cn.fly.mcl.c.b.a().b("init apc, main p: " + zIsInMainProcess);
        if (zIsInMainProcess) {
            cn.fly.apc.b.a(this.d, aVar);
        }
    }

    public boolean b() {
        return this.c != null && this.c.size() > 0;
    }

    public void a(final d<Void> dVar) {
        if (!h.b().j) {
            if (dVar != null) {
                dVar.a(null);
            }
        } else {
            final ArrayList arrayList = new ArrayList();
            boolean zIsInMainProcess = DH.SyncMtd.isInMainProcess();
            cn.fly.mcl.c.b.a().b("qy tp svc, main p: " + zIsInMainProcess);
            if (!zIsInMainProcess) {
                arrayList.add(FlySDK.getContext().getPackageName());
            }
            cn.fly.apc.b.a(new d<Set<String>>() { // from class: cn.fly.mcl.a.a.1
                @Override // cn.fly.tools.utils.d
                public void a(Set<String> set) {
                    arrayList.addAll(set);
                    cn.fly.mcl.c.b.a().b("qy : " + arrayList.toString());
                    a.this.c = new LinkedHashSet();
                    for (String str : arrayList) {
                        cn.fly.apc.a aVar = new cn.fly.apc.a();
                        aVar.a = 1;
                        try {
                            cn.fly.mcl.c.b.a().b("sd apc mg : " + aVar.toString() + " to ->" + str);
                            cn.fly.apc.a aVarA = cn.fly.apc.b.a(1, str, a.this.d, aVar, 5000L);
                            if (aVarA != null && aVarA.e != null && aVarA.a == 1 && aVarA.e.getBoolean("isTcpAvailable")) {
                                a.this.c.add(str);
                            }
                        } catch (Throwable th) {
                            cn.fly.mcl.c.b.a().b("query tcp exp : " + th.getMessage());
                        }
                    }
                    cn.fly.mcl.c.b.a().b("apc available pg : " + a.this.c.toString());
                    if (dVar != null) {
                        dVar.a(null);
                    }
                }
            });
        }
    }

    public cn.fly.apc.a a(int i, Bundle bundle, String str, int i2) {
        try {
            cn.fly.apc.a aVar = new cn.fly.apc.a();
            aVar.a = i;
            aVar.e = bundle;
            cn.fly.mcl.c.b.a().b("apc fw mg : " + i + " " + aVar.toString() + " to ->" + str);
            return cn.fly.apc.b.a(1, str, this.d, aVar, i2);
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
            return null;
        }
    }

    public String a(String str, String str2, HashMap<String, String> map, StringPart stringPart, int i, NetworkHelper.NetworkTimeOut networkTimeOut) throws Throwable {
        cn.fly.apc.a aVarA;
        if (b()) {
            ArrayList<String> arrayList = new ArrayList();
            arrayList.addAll(this.c);
            for (String str3 : arrayList) {
                cn.fly.apc.a aVar = new cn.fly.apc.a();
                aVar.a = 2;
                aVar.e = b.a(str, str2, map, stringPart, i, networkTimeOut);
                try {
                    cn.fly.mcl.c.b.a().b("apc sd mg : " + aVar.toString() + " to ->" + str3);
                    aVarA = cn.fly.apc.b.a(1, str3, this.d, aVar, networkTimeOut.readTimout);
                } catch (Throwable th) {
                    cn.fly.mcl.c.b.a().a(th);
                }
                if (aVarA != null && aVarA.a == 2 && aVarA.e != null) {
                    Bundle bundle = aVarA.e;
                    cn.fly.mcl.c.b.a().b("apc receive rp mg : " + bundle.getString(a));
                    str = bundle.getString(a);
                    return str;
                }
                cn.fly.mcl.c.b.a().b("apc receive rp : " + aVarA);
            }
            return null;
        }
        cn.fly.mcl.c.b.a().a("apc list is null");
        return null;
    }

    public cn.fly.apc.a a(String str, cn.fly.apc.a aVar) {
        cn.fly.mcl.c.b.a().a("apc received mg " + aVar + " from -> " + str);
        if (aVar != null) {
            cn.fly.apc.a aVar2 = new cn.fly.apc.a();
            aVar2.a = aVar.a;
            if (aVar.a == 1) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isTcpAvailable", h.b().e());
                aVar2.e = bundle;
                return aVar2;
            }
            if (aVar.a == 2) {
                aVar2.d = b.a(aVar.e);
                return aVar2;
            }
            if (aVar.a == 9004) {
                return aVar2;
            }
            return null;
        }
        return null;
    }

    public void b(String str, cn.fly.apc.a aVar) {
        if (str != null) {
            Bundle bundle = new Bundle();
            bundle.putString(a, str);
            aVar.e = bundle;
        }
    }
}