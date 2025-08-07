package cn.fly.commons.a;

import android.content.pm.ApplicationInfo;
import android.util.Base64;
import cn.fly.FlySDK;
import cn.fly.commons.CSCenter;
import cn.fly.commons.FlyProduct;
import cn.fly.commons.ad;
import cn.fly.commons.q;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetCommunicator;
import cn.fly.tools.utils.DH;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class m extends c {
    private static final String c = l.a("016+gfgefmgdeiflhmgfhigegmeigdffidhj");
    private static final String d = l.a("0161gfgefmgdeihdgeffgfhjgmeigmgegdge");

    public m() {
        super(l.a("002kh"), 0L, l.a("005kh?fkXek"), 86400L, a(l.a("002kh"), (Long) 0L));
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        if (CSCenter.getInstance().isAppListDataEnable()) {
            try {
                Thread.sleep(((Long) a(e(), (String) 0L)).longValue() * 1000);
                HashMap<String, Object> map = (HashMap) ad.b().c(d, null);
                if (map != null && !map.isEmpty() && b(map) != null) {
                    a((HashMap<String, Object>) null);
                }
            } catch (Throwable th) {
            }
            o();
        }
    }

    private void o() {
        DH.requester(FlySDK.getContext()).getDetailNetworkTypeForStatic().request(new DH.DHResponder() { // from class: cn.fly.commons.a.m.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) throws Throwable {
                final List list;
                HashMap<String, Object> map = new HashMap<>();
                String strA = q.a();
                String strA2 = cn.fly.commons.f.a((FlyProduct) null);
                map.put(l.a("006ekkBfiHg)fd"), strA);
                map.put(l.a("006ekkk1fifk"), DH.SyncMtd.getPackageName());
                map.put(l.a("006ekk(eeJg3ek"), Integer.valueOf(DH.SyncMtd.getAppVersion()));
                map.put(l.a("004Eedehejed"), strA2);
                map.put(l.a("004khej"), Integer.valueOf(DH.SyncMtd.getPlatformCode()));
                map.put(l.a("011fgjGghelekfiPjIfd?kg"), dHResponse.getDetailNetworkTypeForStatic());
                map.put(l.a("009he8gj.jWhl?kh9ge'j"), Long.valueOf(ad.b().b(m.c, 0L)));
                String strEncodeToString = Base64.encodeToString((strA + ":" + strA2).getBytes("utf-8"), 2);
                map.put(l.a("009heBgjKj5hl!khGffed"), strEncodeToString);
                HashMap map2 = (HashMap) m.b(map, cn.fly.commons.j.a().a("gclg") + l.a("004m?ee?kh"));
                if (map2 != null && map2.size() != 0 && (list = (List) map2.get(l.a("004k fifkgj"))) != null && list.size() > 0) {
                    ad.b().a(m.c, System.currentTimeMillis());
                    final ArrayList arrayList = new ArrayList();
                    FlyLog.getInstance().d("[dhss] vpl", new Object[0]);
                    DH.RequestBuilder requestBuilderRequester = DH.requester(FlySDK.getContext());
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        requestBuilderRequester.getMpfos(DH.GPI_STRATEGY_VALIDITY_3_MINUTE, (String) it.next(), 0);
                    }
                    requestBuilderRequester.request(new DH.DHResponder() { // from class: cn.fly.commons.a.m.1.1
                        @Override // cn.fly.tools.utils.DH.DHResponder
                        public void onResponse(DH.DHResponse dHResponse2) {
                            int size = list.size();
                            for (int i = 0; i < size; i++) {
                                try {
                                    Object mpfos = dHResponse2.getMpfos(i);
                                    if (mpfos != null) {
                                        String str = (String) list.get(i);
                                        ApplicationInfo applicationInfoA = cn.fly.tools.c.a(mpfos, str);
                                        HashMap map3 = new HashMap();
                                        map3.put(l.a("006ekkk8fifk"), str);
                                        map3.put(l.a("006ekk9eeIgFek"), cn.fly.tools.c.c(mpfos, str));
                                        if (applicationInfoA != null) {
                                            boolean z = true;
                                            boolean z2 = (applicationInfoA.flags & 1) == 1;
                                            boolean z3 = (applicationInfoA.flags & 128) != 0;
                                            String strA3 = l.a("005-ejgjgjfdgj");
                                            if (!z2 && !z3) {
                                                z = false;
                                            }
                                            map3.put(strA3, Boolean.valueOf(z));
                                        }
                                        arrayList.add(map3);
                                    }
                                } catch (Throwable th) {
                                    FlyLog.getInstance().d(th);
                                }
                            }
                        }
                    });
                    map.remove(l.a("011fgj%ghelekfi6j*fdQkg"));
                    map.remove(l.a("009he!gjRj>hlXkh=ge?j"));
                    map.remove(l.a("009he)gjHjEhl'khZffed"));
                    map.put(l.a("005(egeled,gh"), DH.SyncMtd.getModelForFly());
                    map.put(l.a("0087edHejgj0ejeg,g"), Long.valueOf(System.currentTimeMillis()));
                    map.put(l.a("002!ejed"), strEncodeToString);
                    map.put(l.a("004kYfifkgj"), arrayList);
                    Object objB = m.this.b(map);
                    if (objB == null) {
                        objB = m.this.b(map);
                    }
                    if (objB == null) {
                        m.this.a(map);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object b(HashMap<String, Object> map) {
        try {
            map.put(l.a("005dkhJge1j"), Long.valueOf(System.currentTimeMillis()));
            return b(map, cn.fly.commons.j.a().a("gclg") + l.a("004mdkh"));
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object b(HashMap<String, Object> map, String str) throws Throwable {
        if (!cn.fly.commons.c.c()) {
            return null;
        }
        return new NetCommunicator(1024, "009cbd92ccef123be840deec0c6ed0547194c1e471d11b6f375e56038458fb18833e5bab2e1206b261495d7e2d1d9e5aa859e6d4b67" + l.a("023)ig@eBie4deRijedjlieNg*fg9g+ed0gXimie,g4ifikig*e:kgfg"), "1dfd1d615cb891ce9a76f42d036af7fce5f8b8efaa11b2f42590ecc4ea4cff28f5f6b0726aeb76254ab5b02a58c1d5b486c39d9da1a58fa6ba2f22196493b3a4cbc283dcf749bf63679ee24d185de70c8dfe05605886c9b53e9f569082eabdf98c4fb0dcf07eb9bb3e647903489ff0b5d933bd004af5be4a1022fdda41f347f1").requestSynchronized(map, str, false);
    }

    public synchronized void a(HashMap<String, Object> map) {
        if (map == null) {
            ad.b().b(d);
        } else {
            ad.b().b(d, map);
        }
    }
}