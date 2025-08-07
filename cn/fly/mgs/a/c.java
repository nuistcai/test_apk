package cn.fly.mgs.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.core.view.PointerIconCompat;
import cn.fly.FlySDK;
import cn.fly.apc.b;
import cn.fly.commons.a.l;
import cn.fly.commons.k;
import cn.fly.commons.q;
import cn.fly.mcl.FlyMCL;
import cn.fly.mcl.a;
import cn.fly.mgs.FlyMGS;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.ActivityTracker;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class c implements b.a, b.InterfaceC0001b, b.c {
    private static final String[] a = {"cn.fly.intent.FLY_ID_SERVICE", l.a("029d2elegemegelggemejUfjgfjKemidhigkeiffgmeifmhjhkhlfffehj")};
    private static final String[] b = {"cn.fly.id.FlyIDActivity", l.a("024dCelegemegelggemejedemidelggffgmgeKdj:ejeeejDj.fd")};
    private static c c = new c();
    private static AtomicBoolean d = new AtomicBoolean(false);
    private boolean l;
    private boolean m;
    private ExecutorService e = Executors.newSingleThreadExecutor();
    private String f = null;
    private boolean g = false;
    private int h = 0;
    private int i = 0;
    private List<HashMap<String, Object>> j = null;
    private HashMap<String, Integer> k = null;
    private Context n = FlySDK.getContext();

    public static c a() {
        return c;
    }

    private c() {
        ActivityTracker.getInstance(FlySDK.getContext()).addTracker(cn.fly.mcl.a.a(new a.C0009a() { // from class: cn.fly.mgs.a.c.1
            @Override // cn.fly.mcl.a.C0009a
            public void a() {
                if (c.this.l) {
                    c.this.a((String) null, true);
                }
            }
        }));
    }

    private void f() {
        try {
            if (!d.compareAndSet(false, true)) {
                return;
            }
            FlySDK.init(FlySDK.getContext());
            cn.fly.apc.b.a(FlySDK.getContext());
            String strF = f.a().f();
            cn.fly.apc.b.a(FlyMGS.MGS_TAG, this);
            cn.fly.apc.b.a((b.c) this);
            cn.fly.apc.b.a((b.InterfaceC0001b) this);
            cn.fly.mcl.b.a.a(FlySDK.getContext(), q.a(), strF);
            FlyMCL.getSuid();
            e.a().a("init guardId:" + FlyMCL.getSuid() + ", time: " + FlyMCL.getCreateSuidTime());
        } catch (Throwable th) {
            e.a().b(th);
        }
    }

    public void b() throws Throwable {
        f();
        if (!FlyMGS.getDS()) {
            e.a().a("DS off");
        } else {
            DH.requester(FlySDK.getContext()).getAInfoForPkg(FlySDK.getContext().getPackageName(), 128).request(new DH.DHResponder() { // from class: cn.fly.mgs.a.c.9
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    Bundle bundle;
                    ApplicationInfo aInfoForPkg = dHResponse.getAInfoForPkg(new int[0]);
                    String strValueOf = null;
                    if (aInfoForPkg != null && (bundle = aInfoForPkg.metaData) != null && !bundle.isEmpty()) {
                        Object obj = bundle.get("disable_fly_a_guard");
                        if (obj == null) {
                            obj = bundle.get(l.a("019%edejgj]eNggHhg2eiegelggeiKe>eifkeh,eTeked"));
                        }
                        if (obj != null) {
                            strValueOf = String.valueOf(obj);
                        }
                    }
                    e.a().a("active disable gd:" + strValueOf);
                    if (l.a("004jUekeh.g").equals(strValueOf)) {
                        return;
                    }
                    cn.fly.commons.c.a(l.a("002d>ed"), l.a("006Dififigigigig"), 0L);
                    boolean zB = k.a().b();
                    e.a().a("[EC] isClear init: " + zB);
                    if (!zB) {
                        return;
                    }
                    boolean z = ((Integer) cn.fly.commons.c.a(l.a("003ehh"), 1, 0L)).intValue() == 1;
                    e.a().a("als on: " + z);
                    if (z) {
                        c.this.b(new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mgs.a.c.9.1
                            @Override // cn.fly.tools.utils.d
                            public void a(Boolean bool) {
                                try {
                                    e.a().a("[GD] need to gd:" + bool);
                                    if (bool.booleanValue()) {
                                        if (c.this.c()) {
                                            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
                                            e.a().a("[GD] reg main node");
                                            a.a().a(linkedBlockingQueue);
                                            boolean zBooleanValue = ((Boolean) linkedBlockingQueue.take()).booleanValue();
                                            e.a().a("[GD] reg main node suc: " + zBooleanValue);
                                            if (zBooleanValue) {
                                                e.a().a("=> gd");
                                                c.this.a(c.this.g, (String) null);
                                                e.a().a("<= gd");
                                                e.a().a("syn state: " + c.this.g);
                                                if (c.this.g) {
                                                    Thread.sleep(500L);
                                                    e.a().a("=> syn");
                                                    c.this.g();
                                                    e.a().a("<= syn");
                                                    return;
                                                }
                                                return;
                                            }
                                        }
                                        e.a().a("[GD] reg sub node");
                                        a.a().b();
                                    }
                                } catch (Throwable th) {
                                    FlyLog.getInstance().d(th);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final cn.fly.tools.utils.d<List<HashMap<String, String>>> dVar) {
        final ArrayList arrayList = new ArrayList();
        try {
            final String packageName = FlySDK.getContext().getPackageName();
            final HashSet hashSet = new HashSet();
            final ArrayList arrayList2 = new ArrayList();
            DH.RequestBuilder requestBuilderQueryIntentServices = null;
            for (int i = 0; i < a.length; i++) {
                ReflectHelper.importClass("android.content.Intent");
                Intent intent = (Intent) ReflectHelper.newInstance("Intent", a[i]);
                if (requestBuilderQueryIntentServices == null) {
                    requestBuilderQueryIntentServices = DH.requester(FlySDK.getContext());
                }
                requestBuilderQueryIntentServices = requestBuilderQueryIntentServices.queryIntentServices(intent, 0);
            }
            if (requestBuilderQueryIntentServices != null) {
                requestBuilderQueryIntentServices.request(new DH.DHResponder() { // from class: cn.fly.mgs.a.c.10
                    @Override // cn.fly.tools.utils.DH.DHResponder
                    public void onResponse(DH.DHResponse dHResponse) {
                        for (int i2 = 0; i2 < c.a.length; i2++) {
                            try {
                                List<ResolveInfo> listQueryIntentServices = dHResponse.queryIntentServices(i2);
                                if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
                                    for (ResolveInfo resolveInfo : listQueryIntentServices) {
                                        if (!packageName.equals(resolveInfo.serviceInfo.packageName)) {
                                            arrayList2.add(resolveInfo);
                                        }
                                    }
                                }
                            } catch (Throwable th) {
                                FlyLog.getInstance().d(th);
                                if (dVar != null) {
                                    dVar.a(arrayList);
                                    return;
                                }
                                return;
                            }
                        }
                        c.this.k = new HashMap();
                        if (arrayList2 != null && c.this.k != null) {
                            final ArrayList arrayList3 = new ArrayList();
                            DH.RequestBuilder mpfof = null;
                            for (ResolveInfo resolveInfo2 : arrayList2) {
                                if (resolveInfo2.serviceInfo.exported && !packageName.equals(resolveInfo2.serviceInfo.packageName)) {
                                    if (mpfof == null) {
                                        mpfof = DH.requester(FlySDK.getContext());
                                    }
                                    mpfof = mpfof.getMpfof(true, resolveInfo2.serviceInfo.packageName, 128);
                                    arrayList3.add(resolveInfo2.serviceInfo.packageName);
                                }
                            }
                            if (mpfof != null) {
                                mpfof.request(new DH.DHResponder() { // from class: cn.fly.mgs.a.c.10.1
                                    @Override // cn.fly.tools.utils.DH.DHResponder
                                    public void onResponse(DH.DHResponse dHResponse2) {
                                        Bundle bundle;
                                        int i3;
                                        for (int i4 = 0; i4 < arrayList3.size(); i4++) {
                                            try {
                                                ApplicationInfo applicationInfoA = cn.fly.tools.c.a(dHResponse2.getMpfof(i4), (String) arrayList3.get(i4));
                                                if (applicationInfoA == null) {
                                                    bundle = null;
                                                } else {
                                                    bundle = applicationInfoA.metaData;
                                                }
                                                if (bundle != null && !bundle.isEmpty()) {
                                                    Object obj = bundle.get("fly_id_ver");
                                                    if (obj != null) {
                                                        i3 = 0;
                                                    } else {
                                                        obj = bundle.get(l.a("0103egelggeiejedeiee=gHek"));
                                                        i3 = 1;
                                                    }
                                                    if (obj != null && !hashSet.contains(((ResolveInfo) arrayList2.get(i4)).serviceInfo.packageName) && !c.this.c(((ResolveInfo) arrayList2.get(i4)).serviceInfo.packageName)) {
                                                        hashSet.add(((ResolveInfo) arrayList2.get(i4)).serviceInfo.packageName);
                                                        String strValueOf = String.valueOf(obj);
                                                        HashMap map = new HashMap();
                                                        map.put("appPackage", ((ResolveInfo) arrayList2.get(i4)).serviceInfo.packageName);
                                                        map.put("targetVer", strValueOf);
                                                        arrayList.add(map);
                                                        c.this.k.put(((ResolveInfo) arrayList2.get(i4)).serviceInfo.packageName, Integer.valueOf(i3));
                                                    }
                                                }
                                            } catch (Throwable th2) {
                                                FlyLog.getInstance().d(th2);
                                                if (dVar != null) {
                                                    dVar.a(arrayList);
                                                    return;
                                                }
                                                return;
                                            }
                                        }
                                        if (dVar != null) {
                                            dVar.a(arrayList);
                                        }
                                    }
                                });
                            }
                            return;
                        }
                        if (dVar != null) {
                            dVar.a(arrayList);
                        }
                    }
                });
            } else if (dVar != null) {
                dVar.a(arrayList);
            }
        } catch (Throwable th) {
            e.a().b(th);
            if (dVar != null) {
                dVar.a(arrayList);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final cn.fly.tools.utils.d<Boolean> dVar) {
        a(new cn.fly.tools.utils.d<List<HashMap<String, String>>>() { // from class: cn.fly.mgs.a.c.11
            @Override // cn.fly.tools.utils.d
            public void a(List<HashMap<String, String>> list) {
                boolean z = false;
                try {
                    e.a().a("[GD] avlb uplv tg: " + list);
                    HashMap map = (HashMap) d.a(list, FlyMCL.getSuid(), FlyMGS.getDS());
                    e.a().a("[GD] gd resp:" + map);
                    if (map != null && !map.isEmpty()) {
                        c.this.f = (String) ResHelper.forceCast(map.get("workId"), null);
                        c.this.g = ((Boolean) ResHelper.forceCast(map.get("syncIdState"), false)).booleanValue();
                        c.this.i = ((Integer) ResHelper.forceCast(map.get("asMaster"), 0)).intValue();
                        c.this.h = ((Integer) ResHelper.forceCast(map.get("pollTotal"), 0)).intValue();
                        c.this.j = (List) map.get("pkgList");
                        z = true;
                    }
                    if (dVar == null) {
                    }
                } catch (Throwable th) {
                    try {
                        e.a().b(th);
                    } finally {
                        if (dVar != null) {
                            dVar.a(false);
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(String str) {
        try {
            String string = Settings.Secure.getString(FlySDK.getContext().getContentResolver(), "app_lock_list");
            if (!TextUtils.isEmpty(string)) {
                for (String str2 : string.split(";")) {
                    if (str2 != null && str2.equals(str)) {
                        return true;
                    }
                }
            }
        } catch (Throwable th) {
        }
        return false;
    }

    public boolean c() {
        return this.i == 1;
    }

    public void a(final String str, final boolean z) {
        this.e.execute(new Runnable() { // from class: cn.fly.mgs.a.c.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(200L);
                    c.this.a(false, str);
                    if (z) {
                        Thread.sleep(500L);
                        c.this.d();
                    }
                } catch (Throwable th) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01d2 A[Catch: all -> 0x0275, TryCatch #0 {all -> 0x0275, blocks: (B:3:0x0006, B:6:0x002b, B:8:0x0032, B:9:0x0038, B:12:0x0069, B:13:0x0074, B:15:0x007a, B:18:0x00ae, B:21:0x00c0, B:54:0x024b, B:56:0x026b, B:24:0x00ce, B:29:0x0115, B:31:0x011b, B:41:0x019c, B:43:0x01d2, B:45:0x0208, B:47:0x0210, B:49:0x0218, B:50:0x021b, B:52:0x0236, B:51:0x022e, B:35:0x0166, B:37:0x0172, B:39:0x0178), top: B:61:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x022e A[Catch: all -> 0x0275, TryCatch #0 {all -> 0x0275, blocks: (B:3:0x0006, B:6:0x002b, B:8:0x0032, B:9:0x0038, B:12:0x0069, B:13:0x0074, B:15:0x007a, B:18:0x00ae, B:21:0x00c0, B:54:0x024b, B:56:0x026b, B:24:0x00ce, B:29:0x0115, B:31:0x011b, B:41:0x019c, B:43:0x01d2, B:45:0x0208, B:47:0x0210, B:49:0x0218, B:50:0x021b, B:52:0x0236, B:51:0x022e, B:35:0x0166, B:37:0x0172, B:39:0x0178), top: B:61:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(boolean z, String str) {
        ArrayList arrayList;
        ArrayList arrayList2;
        boolean zBooleanValue;
        int i;
        String str2;
        try {
            boolean zB = k.a().b();
            e.a().a("[EC] isClear upl: " + zB);
            if (!zB) {
                return;
            }
            int i2 = 0;
            this.l = false;
            int size = this.h;
            if (z) {
                size = this.j.size();
            }
            e.a().a("[GD] pu whole: " + z + ", pucnt: " + size + ", frm: " + this.j);
            if (size == 0) {
                return;
            }
            ArrayList arrayList3 = new ArrayList();
            Iterator<HashMap<String, Object>> it = this.j.iterator();
            while (true) {
                if (!it.hasNext()) {
                    arrayList = arrayList3;
                    break;
                }
                HashMap<String, Object> next = it.next();
                String str3 = (String) ResHelper.forceCast(next.get(l.a("003kUfifk")), null);
                int iIntValue = ((Integer) ResHelper.forceCast(next.get(l.a("005jDejegWg_gj")), Integer.valueOf(i2))).intValue();
                if (str3 != null) {
                    HashMap map = new HashMap();
                    map.put(l.a("003k!fifk"), str3);
                    if (iIntValue <= 0) {
                        map.put("executeResult", "ooo");
                        arrayList3.add(map);
                    } else {
                        if (size <= 0) {
                            arrayList = arrayList3;
                            break;
                        }
                        int i3 = size - 1;
                        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
                        int iA = a.a().a(str3, linkedBlockingQueue);
                        e.a().a("[GD] chkAlv_SndTp : " + str3 + ", status: " + iA + ", whole: " + z);
                        if (iA == 0 && !z) {
                            if (!str3.equals(str)) {
                                final LinkedBlockingQueue linkedBlockingQueue2 = new LinkedBlockingQueue();
                                a(FlySDK.getContext(), str3, new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mgs.a.c.13
                                    @Override // cn.fly.tools.utils.d
                                    public void a(Boolean bool) {
                                        linkedBlockingQueue2.offer(bool);
                                    }
                                });
                                arrayList2 = arrayList3;
                                zBooleanValue = ((Boolean) linkedBlockingQueue2.poll(1000L, TimeUnit.MILLISECONDS)).booleanValue();
                                e.a().a("[GD] chkAlv_Ap : " + str3 + ", alv: " + zBooleanValue);
                            }
                            e.a().a("[GD] Proc gd: " + str3 + ", alv: " + zBooleanValue + ", times: " + iIntValue + ", pucnt: " + i3);
                            if (zBooleanValue) {
                                final LinkedBlockingQueue linkedBlockingQueue3 = new LinkedBlockingQueue();
                                i = i3;
                                a(2000, str3, this.f, f.a().f(), new cn.fly.tools.utils.d<HashMap<String, Object>>() { // from class: cn.fly.mgs.a.c.14
                                    @Override // cn.fly.tools.utils.d
                                    public void a(HashMap<String, Object> map2) {
                                        linkedBlockingQueue3.offer(map2);
                                    }
                                });
                                HashMap map2 = (HashMap) linkedBlockingQueue3.poll(3000L, TimeUnit.MILLISECONDS);
                                if (map2.get("executeResult") == null) {
                                    str2 = null;
                                } else {
                                    str2 = (String) map2.get("executeResult");
                                }
                                if ("uncertain".equals(str2)) {
                                    this.l = true;
                                }
                                map.putAll(map2);
                                iIntValue--;
                                next.put(l.a("005j(ejeg0g]gj"), Integer.valueOf(iIntValue));
                            } else {
                                i = i3;
                                map.put("executeResult", "alive");
                            }
                            map.put("remainCount", Integer.valueOf(iIntValue));
                            ArrayList arrayList4 = arrayList2;
                            arrayList4.add(map);
                            arrayList3 = arrayList4;
                            size = i;
                            i2 = 0;
                        }
                        arrayList2 = arrayList3;
                        if (iA != 1) {
                            zBooleanValue = false;
                        } else {
                            Boolean bool = (Boolean) linkedBlockingQueue.poll(2000L, TimeUnit.MILLISECONDS);
                            if (bool == null) {
                                zBooleanValue = false;
                            } else {
                                zBooleanValue = bool.booleanValue();
                            }
                            e.a().a("[GD] chkAlv_Tp : " + str3 + ", alv: " + zBooleanValue);
                        }
                        e.a().a("[GD] Proc gd: " + str3 + ", alv: " + zBooleanValue + ", times: " + iIntValue + ", pucnt: " + i3);
                        if (zBooleanValue) {
                        }
                        map.put("remainCount", Integer.valueOf(iIntValue));
                        ArrayList arrayList42 = arrayList2;
                        arrayList42.add(map);
                        arrayList3 = arrayList42;
                        size = i;
                        i2 = 0;
                    }
                }
            }
            e.a().a("[GD] pu rst: " + arrayList);
            if (arrayList.size() > 0) {
                d.a(arrayList, FlyMCL.getSuid(), this.f);
            }
        } catch (Throwable th) {
            e.a().a("[GD] pu excp: " + th.getMessage());
            e.a().a(th);
        }
    }

    public void b(String str) {
        e.a().a("[GD] syn newC : " + str + " synFld : " + this.m);
        if (this.m) {
            this.e.execute(new Runnable() { // from class: cn.fly.mgs.a.c.15
                @Override // java.lang.Runnable
                public void run() {
                    if (c.this.m) {
                        c.this.d();
                    }
                }
            });
        }
    }

    public void d() {
        if (this.g) {
            g();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        Bundle bundle;
        e.a().a("[SYN] frm: " + this.j);
        if (this.j == null || this.j.size() == 0) {
            return;
        }
        String suid = FlyMCL.getSuid();
        long createSuidTime = FlyMCL.getCreateSuidTime();
        Iterator<HashMap<String, Object>> it = this.j.iterator();
        String str = suid;
        while (it.hasNext()) {
            cn.fly.apc.a aVarA = null;
            String str2 = (String) ResHelper.forceCast(it.next().get(l.a("003k5fifk")), null);
            cn.fly.apc.a aVar = new cn.fly.apc.a();
            aVar.a = 1001;
            try {
                aVarA = cn.fly.apc.b.a(1, str2, FlyMGS.MGS_TAG, aVar, 5000L);
            } catch (Throwable th) {
                e.a().b(th);
            }
            e.a().a("[SYN] clt svr ids: " + str2 + ", response:" + aVarA);
            if (aVarA != null && (bundle = aVarA.e) != null) {
                String string = bundle.getString("guardId");
                long j = bundle.getLong(l.a("009jSejeg^gGgjPjeXeg!k"));
                if (!TextUtils.isEmpty(string) && j > 0 && j < createSuidTime) {
                    str = string;
                    createSuidTime = j;
                }
            }
        }
        e.a().a("[SYN] clt done nid :" + str + ", oid: " + suid);
        if (!str.equals(suid)) {
            final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            cn.fly.mcl.b.a.a(str, createSuidTime, new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mgs.a.c.16
                @Override // cn.fly.tools.utils.d
                public void a(Boolean bool) {
                    linkedBlockingQueue.offer(bool);
                }
            });
            try {
                ((Boolean) linkedBlockingQueue.poll(1000L, TimeUnit.MILLISECONDS)).booleanValue();
            } catch (Throwable th2) {
                FlyLog.getInstance().d(th2);
            }
        }
        a(str, createSuidTime);
    }

    private void a(String str, long j) {
        this.m = false;
        Iterator<HashMap<String, Object>> it = this.j.iterator();
        while (it.hasNext()) {
            String str2 = (String) ResHelper.forceCast(it.next().get(l.a("003kCfifk")), null);
            try {
                cn.fly.apc.a aVar = new cn.fly.apc.a();
                aVar.a = PointerIconCompat.TYPE_HELP;
                Bundle bundle = new Bundle();
                bundle.putString("guardId", str);
                bundle.putLong(l.a("009jVejegVg*gj9je>egCk"), j);
                bundle.putString("workId", this.f);
                aVar.e = bundle;
                e.a().a("[SYN] snd nid to svr :" + str2 + ", response: " + cn.fly.apc.b.a(1, str2, FlyMGS.MGS_TAG, aVar, 5000L));
            } catch (Throwable th) {
                e.a().a(th);
                this.m = true;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference failed for: r8v6, types: [cn.fly.mgs.a.c$2] */
    @Override // cn.fly.apc.b.a
    public cn.fly.apc.a a(String str, cn.fly.apc.a aVar, long j) {
        e.a().a("[SYN] onAPCMessageReceive APCMessage:" + aVar + ", pkg:" + str);
        cn.fly.apc.a aVar2 = new cn.fly.apc.a();
        String suid = FlyMCL.getSuid();
        long createSuidTime = FlyMCL.getCreateSuidTime();
        switch (aVar.a) {
            case 1001:
                Bundle bundle = new Bundle();
                bundle.putString("guardId", suid);
                bundle.putLong(l.a("009j-ejegXgCgjUje[eg,k"), createSuidTime);
                bundle.putString(l.a("003kAfifk"), FlySDK.getContext().getPackageName());
                aVar2.e = bundle;
                return aVar2;
            case PointerIconCompat.TYPE_HAND /* 1002 */:
            default:
                return aVar2;
            case PointerIconCompat.TYPE_HELP /* 1003 */:
                Bundle bundle2 = aVar.e;
                if (bundle2 != null) {
                    final String string = bundle2.getString("guardId");
                    final long j2 = bundle2.getLong(l.a("009j+ejeg+gRgjEjeKegKk"));
                    bundle2.getString("workId");
                    if (string != null && j2 > 0 && !suid.equals(string) && j2 < createSuidTime) {
                        new h() { // from class: cn.fly.mgs.a.c.2
                            @Override // cn.fly.tools.utils.j
                            protected void a() throws Throwable {
                                cn.fly.mcl.b.a.a(string, j2, new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mgs.a.c.2.1
                                    @Override // cn.fly.tools.utils.d
                                    public void a(Boolean bool) {
                                    }
                                });
                            }
                        }.start();
                    }
                }
                return aVar2;
        }
    }

    @Override // cn.fly.apc.b.c
    public void a(Bundle bundle) {
        if (bundle != null) {
            Intent intent = new Intent();
            intent.putExtra("workId", bundle.getString("workId"));
            intent.putExtra(l.a("006ekk6fi9g;fd"), bundle.getString(l.a("006ekk6fi9g;fd")));
            intent.putExtra(l.a("004Wedehejed"), bundle.getString(l.a("004Wedehejed")));
            intent.putExtra("guardId", bundle.getString("guardId"));
            intent.putExtra(l.a("003k@fifk"), bundle.getString(l.a("003k@fifk")));
            intent.putExtra("acServiceType", bundle.getInt("acsActType"));
        }
    }

    @Override // cn.fly.apc.b.InterfaceC0001b
    public HashMap<String, Object> a(int i, String str) {
        int i2;
        if (i == 1) {
            i2 = 2001;
        } else if (i != 2) {
            i2 = -1;
        } else {
            i2 = 2002;
        }
        e.a().a("[requestInvokeGd]finalBusType: " + i2);
        if (i2 != -1) {
            HashMap<String, Object> map = new HashMap<>();
            try {
                final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
                a(i2, str, new cn.fly.tools.utils.d<HashMap<String, Object>>() { // from class: cn.fly.mgs.a.c.3
                    @Override // cn.fly.tools.utils.d
                    public void a(HashMap<String, Object> map2) {
                        linkedBlockingQueue.offer(map2);
                    }
                });
                return (HashMap) linkedBlockingQueue.poll(2000L, TimeUnit.MILLISECONDS);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return map;
            }
        }
        return new HashMap<>();
    }

    @Override // cn.fly.apc.b.InterfaceC0001b
    public boolean a(String str) {
        try {
            final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            a(str, new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mgs.a.c.4
                @Override // cn.fly.tools.utils.d
                public void a(Boolean bool) {
                    linkedBlockingQueue.offer(bool);
                }
            });
            return ((Boolean) linkedBlockingQueue.poll(1000L, TimeUnit.MILLISECONDS)).booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    private static void a(Context context, final String str, final cn.fly.tools.utils.d<Boolean> dVar) {
        DH.requester(context).getMpfof(true, str, 0).request(new DH.DHResponder() { // from class: cn.fly.mgs.a.c.5
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                try {
                    Object mpfof = dHResponse.getMpfof(new int[0]);
                    if (mpfof != null) {
                        ApplicationInfo applicationInfoA = cn.fly.tools.c.a(mpfof, str);
                        boolean z = (applicationInfoA.flags & 1) == 0 && (applicationInfoA.flags & 128) == 0;
                        boolean z2 = (applicationInfoA.flags & 2097152) == 0;
                        if (z && z2) {
                            if (dVar != null) {
                                dVar.a(true);
                            }
                        } else if (dVar != null) {
                            dVar.a(false);
                        }
                        return;
                    }
                    if (dVar != null) {
                        dVar.a(false);
                    }
                } catch (Throwable th) {
                    e.a().b(th);
                    if (dVar != null) {
                        dVar.a(false);
                    }
                }
            }
        });
    }

    /* renamed from: cn.fly.mgs.a.c$6, reason: invalid class name */
    class AnonymousClass6 extends cn.fly.tools.utils.d<Boolean> {
        final /* synthetic */ String a;
        final /* synthetic */ cn.fly.tools.utils.d b;
        final /* synthetic */ int c;
        final /* synthetic */ String d;
        final /* synthetic */ String e;

        AnonymousClass6(String str, cn.fly.tools.utils.d dVar, int i, String str2, String str3) {
            this.a = str;
            this.b = dVar;
            this.c = i;
            this.d = str2;
            this.e = str3;
        }

        @Override // cn.fly.tools.utils.d
        public void a(Boolean bool) {
            e.a().a("[GD]target: " + this.a + ", isLv: " + bool);
            if (!bool.booleanValue()) {
                if (c.this.k == null) {
                    c.this.a(new cn.fly.tools.utils.d<List<HashMap<String, String>>>() { // from class: cn.fly.mgs.a.c.6.1
                        @Override // cn.fly.tools.utils.d
                        public void a(List<HashMap<String, String>> list) {
                            if (AnonymousClass6.this.b != null) {
                                c.this.b(AnonymousClass6.this.c, AnonymousClass6.this.a, AnonymousClass6.this.d, AnonymousClass6.this.e, new cn.fly.tools.utils.d<HashMap<String, Object>>() { // from class: cn.fly.mgs.a.c.6.1.1
                                    @Override // cn.fly.tools.utils.d
                                    public void a(HashMap<String, Object> map) {
                                        AnonymousClass6.this.b.a(map);
                                    }
                                });
                            }
                        }
                    });
                    return;
                } else {
                    if (this.b != null) {
                        c.this.b(this.c, this.a, this.d, this.e, new cn.fly.tools.utils.d<HashMap<String, Object>>() { // from class: cn.fly.mgs.a.c.6.2
                            @Override // cn.fly.tools.utils.d
                            public void a(HashMap<String, Object> map) {
                                AnonymousClass6.this.b.a(map);
                            }
                        });
                        return;
                    }
                    return;
                }
            }
            if (this.b != null) {
                this.b.a(new HashMap());
            }
        }
    }

    private void a(int i, String str, String str2, String str3, cn.fly.tools.utils.d<HashMap<String, Object>> dVar) {
        e.a().a("[GD]busType: " + i + ", target: " + str + ", workId: " + str2 + ", duid: " + str3);
        a(FlySDK.getContext(), str, new AnonymousClass6(str, dVar, i, str2, str3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i, final String str, String str2, String str3, final cn.fly.tools.utils.d<HashMap<String, Object>> dVar) {
        final HashMap<String, Object> map = new HashMap<>();
        try {
            ComponentName componentName = new ComponentName(str, b[((Integer) ResHelper.forceCast(this.k.get(str), 0)).intValue()]);
            Intent intent = new Intent();
            intent.addFlags(411041792);
            intent.setComponent(componentName);
            intent.putExtra("workId", str2);
            intent.putExtra(l.a("004<edehejed"), str3);
            intent.putExtra(l.a("006ekk_fiEg6fd"), q.a());
            intent.putExtra(l.a("003kZfifk"), FlySDK.getContext().getPackageName());
            intent.putExtra("guardId", FlyMCL.getSuid());
            intent.putExtra("busType", i);
            long jCurrentTimeMillis = System.currentTimeMillis();
            map.put("startActivityTime", Long.valueOf(jCurrentTimeMillis));
            FlySDK.getContext().startActivity(intent);
            map.put("startActivityDuration", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
            Thread.sleep(320L);
            a(FlySDK.getContext(), str, new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mgs.a.c.7
                @Override // cn.fly.tools.utils.d
                public void a(Boolean bool) {
                    e.a().a("[GD] stAct rst. pkg: " + str + ", lv: " + bool);
                    if (bool.booleanValue()) {
                        map.put("executeResult", "success");
                    } else {
                        map.put("executeResult", "uncertain");
                    }
                    if (dVar != null) {
                        dVar.a(map);
                    }
                }
            });
        } catch (Throwable th) {
            e.a().a(th);
            e.a().a("[GD] stAct rst.  pkg: " + str + ", exception: " + th.getMessage());
            map.put("executeResult", "fail");
            if (dVar != null) {
                dVar.a(map);
            }
        }
    }

    public void a(int i, String str, cn.fly.tools.utils.d<HashMap<String, Object>> dVar) {
        a(i, str, this.f, f.a().f(), dVar);
    }

    private void a(String str, final cn.fly.tools.utils.d<Boolean> dVar) {
        e eVarA;
        StringBuilder sbAppend;
        boolean z;
        final boolean[] zArr = {false};
        try {
            if (this.n.equals(str) && dVar != null) {
                zArr[0] = true;
                dVar.a(Boolean.valueOf(zArr[0]));
            }
            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            int iA = a.a().a(str, linkedBlockingQueue);
            if (iA == 0) {
                a(this.n, str, new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mgs.a.c.8
                    @Override // cn.fly.tools.utils.d
                    public void a(Boolean bool) {
                        if (dVar != null) {
                            zArr[0] = bool.booleanValue();
                            dVar.a(Boolean.valueOf(zArr[0]));
                        }
                    }
                });
            } else if (iA == 1) {
                Boolean bool = (Boolean) linkedBlockingQueue.poll(2000L, TimeUnit.MILLISECONDS);
                if (bool != null) {
                    zArr[0] = bool.booleanValue();
                }
                if (dVar != null) {
                    dVar.a(Boolean.valueOf(zArr[0]));
                }
            }
            eVarA = e.a();
            sbAppend = new StringBuilder().append("checkAppLive appStatus: ").append(iA).append(", isLive: ");
            z = zArr[0];
        } catch (Throwable th) {
            try {
                e.a().a(th);
                if (dVar != null) {
                    dVar.a(Boolean.valueOf(zArr[0]));
                }
                eVarA = e.a();
                sbAppend = new StringBuilder().append("checkAppLive appStatus: ").append(-1).append(", isLive: ");
                z = zArr[0];
            } catch (Throwable th2) {
                e.a().a("checkAppLive appStatus: -1, isLive: " + zArr[0]);
                throw th2;
            }
        }
        eVarA.a(sbAppend.append(z).toString());
    }
}