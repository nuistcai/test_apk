package cn.fly.apc.a;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.apc.APCException;
import cn.fly.apc.b;
import cn.fly.commons.k;
import cn.fly.commons.o;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class c {
    private static c a = new c();
    private static final String[] i = {o.a("037c,dkdfdldfdkffdlfi'f!djdddiNcfRdlGdci1didkHeZdlhcghfjdhfdeddhelgigjgkeeedgi"), "cn.fly.service.action.FLY_AC_SERVICE"};
    private b.c f;
    private Bundle g;
    private b.InterfaceC0001b h;
    private HashMap<String, b.a> b = new HashMap<>();
    private b c = new b();
    private byte[] e = new byte[0];
    private HashMap<String, e> d = new HashMap<>();

    private c() {
    }

    public static c a() {
        return a;
    }

    public void a(String str, b.a aVar) {
        f.a().b("[addIpcMsgListener] %s", str);
        this.b.put(str, aVar);
        synchronized (this.e) {
            if (this.d.containsKey(str)) {
                f.a().b("[addIpcMsgListener] %s", "buf msg found, callback right now");
                e eVarRemove = this.d.remove(str);
                aVar.a(eVarRemove.c, eVarRemove.a, eVarRemove.e);
            }
        }
    }

    public cn.fly.apc.a a(int i2, String str, String str2, cn.fly.apc.a aVar, long j) throws Throwable {
        boolean zB = k.a().b();
        f.a().a("[EC] isClear snd mg: " + zB, new Object[0]);
        if (!zB) {
            throw new APCException("ec is not clear");
        }
        if (TextUtils.isEmpty(str)) {
            f.a().b("[sendMessage] pkg not allowed null.", new Object[0]);
            throw new APCException("pkg not allowed null.");
        }
        if (aVar == null) {
            f.a().b("[sendMessage] param not allowed null.", new Object[0]);
            throw new APCException("param not allowed null.");
        }
        switch (i2) {
            case 1:
                return this.c.a(str, str2, aVar, j);
            default:
                f.a().b("type " + i2 + " not support.", new Object[0]);
                throw new APCException("type " + i2 + " not support.");
        }
    }

    public void a(final cn.fly.tools.utils.d<Set<String>> dVar) {
        final HashSet hashSet = new HashSet();
        try {
            boolean zB = k.a().b();
            f.a().a("[EC] isClear apcsvcl: " + zB, new Object[0]);
            if (zB) {
                ReflectHelper.importClass("android.content.Intent");
                DH.RequestBuilder requestBuilderRequester = DH.requester(FlySDK.getContext());
                for (String str : i) {
                    requestBuilderRequester = requestBuilderRequester.queryIntentServices((Intent) ReflectHelper.newInstance("Intent", str), 0);
                }
                requestBuilderRequester.request(new DH.DHResponder() { // from class: cn.fly.apc.a.c.1
                    @Override // cn.fly.tools.utils.DH.DHResponder
                    public void onResponse(DH.DHResponse dHResponse) {
                        int length = c.i.length;
                        for (int i2 = 0; i2 < length; i2++) {
                            List<ResolveInfo> listQueryIntentServices = dHResponse.queryIntentServices(i2);
                            if (listQueryIntentServices != null) {
                                for (ResolveInfo resolveInfo : listQueryIntentServices) {
                                    String str2 = resolveInfo.serviceInfo.packageName;
                                    if (resolveInfo.serviceInfo.exported && !cn.fly.apc.b.a().getPackageName().equals(str2)) {
                                        hashSet.add(resolveInfo.serviceInfo.packageName);
                                    }
                                }
                            }
                        }
                        f.a().b("[getMAPCServiceList] list: %s", hashSet);
                        if (dVar != null) {
                            dVar.a(hashSet);
                        }
                    }
                });
                return;
            }
            if (dVar != null) {
                dVar.a(hashSet);
            }
        } catch (Throwable th) {
            f.a().a(th);
            if (dVar != null) {
                dVar.a(hashSet);
            }
        }
    }

    public e a(e eVar) {
        try {
            b.a aVar = this.b.get(eVar.b);
            f.a().b("[MGRC] innerMessage: %s, listener: %s", eVar, aVar);
            if (aVar != null) {
                cn.fly.apc.a aVarA = aVar.a(eVar.c, eVar.a, eVar.e);
                f.a().b("[MGRC] listener apcMessage: %s", aVarA);
                return new e(aVarA, eVar.b, eVar.e);
            }
            f.a().b("[MGRC] No listener detected, buffer this msg", new Object[0]);
            this.d.put(eVar.b, eVar);
            return null;
        } catch (Throwable th) {
            f.a().b("[MGRC] exception %s", th.getMessage());
            f.a().a(th);
            return null;
        }
    }

    public void a(b.c cVar) {
        f.a().b("[regAcSvcLis] %s", "done");
        this.f = cVar;
        if (this.g != null) {
            f.a().b("[regAcSvcLis] %s", "bufBundle detected, callback");
            this.f.a(new Bundle(this.g));
            this.f = null;
            return;
        }
        f.a().b("[regAcSvcLis] %s", "no bufBundle, nothing to do");
    }

    public void a(b.InterfaceC0001b interfaceC0001b) {
        f.a().b("[regMgReqLis] %s", "done");
        this.h = interfaceC0001b;
    }

    public b.InterfaceC0001b b() {
        return this.h;
    }

    public void a(Bundle bundle) {
        if (this.f != null) {
            f.a().b("[onAcSvcAct] %s", "listener detected, callback");
            this.f.a(bundle);
        } else {
            f.a().b("[onAcSvcAct] %s", "no listener detected, cache");
            this.g = new Bundle(bundle);
        }
    }
}