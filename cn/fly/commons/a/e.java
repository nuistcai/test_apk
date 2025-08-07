package cn.fly.commons.a;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.ad;
import cn.fly.commons.w;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class e extends c {
    public e() {
        super("l", 0L, w.b("004f+di;ci"), 86400L, a("l", (Long) 0L));
    }

    @Override // cn.fly.commons.a.c
    protected void a() throws Throwable {
        long jB = ad.b().b("key_lgwst", 0L);
        if (DH.SyncMtd.checkPermission(w.b("036cdWcbcicjchcbck]ieHcicechehehchcjWdWckdcejecdfhcfhcgfeddfbddcgdkebecebfh")) && DH.SyncMtd.checkPermission(w.b("036cd8cbcicjchcbck3ie-cicechehehchcjUdUckecdcdcfhdkdkcgfeddfbddcgdkebecebfh")) && System.currentTimeMillis() - jB >= 1800000) {
            C0041r.a(new cn.fly.tools.utils.d<ArrayList<HashMap<String, Object>>>() { // from class: cn.fly.commons.a.e.1
                @Override // cn.fly.tools.utils.d
                public void a(ArrayList<HashMap<String, Object>> arrayList) {
                    ad.b().a("key_lgwst", System.currentTimeMillis());
                    e.this.a(arrayList);
                }
            });
        } else {
            a((ArrayList<HashMap<String, Object>>) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ArrayList<HashMap<String, Object>> arrayList) {
        try {
            a(arrayList, 2);
            a(arrayList, 1);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
    }

    private void a(final ArrayList<HashMap<String, Object>> arrayList, final int i) {
        DH.RequestBuilder mbcdi = DH.requester(FlySDK.getContext()).getMcdi().getMbcdi();
        if (i == 1) {
            mbcdi.getPosCommForce(30, 0, true, false);
        } else {
            mbcdi.getPosCommForce(0, 15, true, false);
        }
        mbcdi.request(new DH.DHResponder() { // from class: cn.fly.commons.a.e.2
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                List posCommForce = dHResponse.getPosCommForce(new int[0]);
                if (posCommForce != null && !posCommForce.isEmpty()) {
                    List<HashMap<String, Object>> listA = e.this.a(posCommForce);
                    if (listA != null && !listA.isEmpty()) {
                        for (HashMap<String, Object> map : listA) {
                            if (map != null && !map.isEmpty()) {
                                e.this.a(map, map);
                                String mcdi = dHResponse.getMcdi();
                                String mbcdi2 = dHResponse.getMbcdi();
                                if (!TextUtils.isEmpty(mbcdi2)) {
                                    map.put("cbsmt", mbcdi2);
                                }
                                if (!TextUtils.isEmpty(mcdi)) {
                                    map.put("cssmt", mcdi);
                                }
                                if (e.this.h()) {
                                    map.put("pt", 1);
                                } else {
                                    map.put("pt", 2);
                                }
                                map.put("lctpmt", Integer.valueOf(i));
                                if (arrayList != null && !arrayList.isEmpty()) {
                                    map.put("wilmt", arrayList);
                                }
                                e.this.a("LCMT", map);
                            }
                        }
                    }
                    if (i == 1) {
                        cn.fly.tools.utils.e.a().a(posCommForce.get(posCommForce.size() - 1));
                    }
                }
            }
        });
    }
}