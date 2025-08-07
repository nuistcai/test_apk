package cn.fly.commons.a;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.ad;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.k;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class n extends c {
    public n() {
        super(l.a("002Eghej"), 0L, l.a("0052ghejfk+ek"), 3600L, a(l.a("002Eghej"), (Long) 0L));
    }

    @Override // cn.fly.commons.a.c
    protected void d() {
        cn.fly.tools.utils.k.a().a(getClass().getName(), new k.a() { // from class: cn.fly.commons.a.n.1
            @Override // cn.fly.tools.utils.k.a
            public void a() {
                if (n.this.f()) {
                    n.this.n();
                }
            }
        });
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        n();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        DH.requester(FlySDK.getContext()).getMwfo().getMwlfo().request(new DH.DHResponder() { // from class: cn.fly.commons.a.n.2
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                HashMap<String, Object> map = new HashMap<>();
                HashMap<String, Object> mwfo = dHResponse.getMwfo();
                if (mwfo == null) {
                    return;
                }
                String str = (String) mwfo.get("bsmt");
                String str2 = (String) mwfo.get("ssmt");
                if (!TextUtils.isEmpty(str)) {
                    ArrayList<HashMap<String, Object>> mwlfo = dHResponse.getMwlfo();
                    if (mwlfo != null && !mwlfo.isEmpty()) {
                        Iterator<HashMap<String, Object>> it = mwlfo.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            HashMap<String, Object> next = it.next();
                            Object obj = next.get(l.a("005Lgkfmfmffgm"));
                            if (obj != null && String.valueOf(obj).equals(str)) {
                                map.putAll(next);
                                break;
                            }
                        }
                        map.remove(l.a("0056gkfmfmffgm"));
                        map.remove(l.a("0040fmfmffgm"));
                    }
                } else if (TextUtils.isEmpty(str2) || l.a("0148jfeh8fSfiGf-elghDfCjggjgjejedjh").equalsIgnoreCase(str2)) {
                    return;
                }
                map.putAll(mwfo);
                map.put("ssmt", str2);
                map.put("bsmt", str);
                n.this.a("WIMT", map, true);
                TreeMap treeMap = new TreeMap();
                treeMap.put("ssmt", str2);
                treeMap.put("bsmt", str);
                ad.b().a(ad.i, Data.MD5(new JSONObject(treeMap).toString()));
            }
        });
    }
}