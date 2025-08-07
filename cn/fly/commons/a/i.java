package cn.fly.commons.a;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.ad;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class i extends c {
    public i() {
        super(cn.fly.commons.m.a("003Yhkgehk"), 0L, cn.fly.commons.m.a("006!hkgehkgl%fl"), 2592000L, a(cn.fly.commons.m.a("003Yhkgehk"), (Long) 0L));
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        DH.requester(FlySDK.getContext()).getSA().request(new DH.DHResponder() { // from class: cn.fly.commons.a.i.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                boolean z;
                ArrayList<HashMap<String, String>> sa = dHResponse.getSA();
                if (sa == null || sa.isEmpty()) {
                    return;
                }
                long jB = ad.b().b(ad.d, 0L);
                long jM = i.this.m() * 1000;
                long jCurrentTimeMillis = System.currentTimeMillis();
                boolean z2 = jCurrentTimeMillis - jM >= jB;
                if (!z2) {
                    ArrayList<HashMap<String, String>> arrayListFromFile = ResHelper.readArrayListFromFile(cn.fly.commons.n.e, true);
                    Iterator<HashMap<String, String>> it = sa.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        String str = it.next().get(cn.fly.commons.m.a("003l9gjgl"));
                        if (!TextUtils.isEmpty(str)) {
                            Iterator<HashMap<String, String>> it2 = arrayListFromFile.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    z = false;
                                    break;
                                } else if (str.equals(it2.next().get(cn.fly.commons.m.a("003lUgjgl")))) {
                                    z = true;
                                    break;
                                }
                            }
                            if (!z) {
                                z2 = true;
                                break;
                            }
                        }
                    }
                }
                if (z2) {
                    i.this.a(0L, "SALMT", sa);
                    ResHelper.saveArrayListToFile(sa, cn.fly.commons.n.e, true);
                    ad.b().a(ad.d, jCurrentTimeMillis);
                }
            }
        });
    }
}