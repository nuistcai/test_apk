package cn.fly.tools.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.a.l;
import cn.fly.commons.ad;
import cn.fly.commons.n;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class k {
    private static volatile k a = null;
    private BroadcastReceiver b;
    private final ConcurrentHashMap<String, a> c = new ConcurrentHashMap<>();
    private volatile long d = 0;

    public interface a {
        void a();
    }

    private k() {
        this.b = null;
        if (cn.fly.commons.e.c() || cn.fly.commons.e.d()) {
            this.b = new BroadcastReceiver() { // from class: cn.fly.tools.utils.k.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    k.a().a(context, intent);
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(n.a("029bc+babhbibgbabjFcdgYbjdebgcdbgbjcjdadbdaegbfcbdidbcegbeg"));
            C0041r.a(this.b, intentFilter);
        }
    }

    public static k a() {
        if (a == null) {
            synchronized (k.class) {
                if (a == null) {
                    a = new k();
                }
            }
        }
        return a;
    }

    public void a(String str, a aVar) {
        if (aVar != null && str != null && !this.c.containsKey(str)) {
            this.c.put(str, aVar);
        }
    }

    public void a(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            if (n.a("029bcVbabhbibgbabj[cdg$bjdebgcdbgbjcjdadbdaegbfcbdidbcegbeg").equals(intent.getAction()) && intent.getParcelableExtra(n.a("011cdg_debibhcfccYc_cdbi")) != null) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.d > 2000) {
                    this.d = jCurrentTimeMillis;
                    l.a().b(2500L, new i() { // from class: cn.fly.tools.utils.k.2
                        @Override // cn.fly.tools.utils.i
                        protected void a() {
                            if (cn.fly.commons.e.j()) {
                                DH.requester(FlySDK.getContext()).getMwfoForce(true).request(new DH.DHResponder() { // from class: cn.fly.tools.utils.k.2.1
                                    @Override // cn.fly.tools.utils.DH.DHResponder
                                    public void onResponse(DH.DHResponse dHResponse) {
                                        HashMap<String, Object> mwfoForce = dHResponse.getMwfoForce(new int[0]);
                                        if (mwfoForce == null) {
                                            return;
                                        }
                                        String str = (String) mwfoForce.get("ssmt");
                                        String str2 = (String) mwfoForce.get("bsmt");
                                        FlyLog.getInstance().d("[MCM] cdi " + str + " bcdi " + str2 + " len " + k.a().c.size(), new Object[0]);
                                        if (TextUtils.isEmpty(str2) && (TextUtils.isEmpty(str) || n.a("014$gcbeXcLcf3c,bide(c@gddgdgbgbage").equalsIgnoreCase(str))) {
                                            return;
                                        }
                                        TreeMap treeMap = new TreeMap();
                                        treeMap.put("ssmt", str);
                                        treeMap.put("bsmt", str2);
                                        String strMD5 = Data.MD5(new JSONObject(treeMap).toString());
                                        String strB = ad.b().b(ad.i, (String) null);
                                        if (strB == null || !strB.equals(strMD5)) {
                                            Iterator it = k.this.c.values().iterator();
                                            while (it.hasNext()) {
                                                ((a) it.next()).a();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }
}