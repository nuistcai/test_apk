package cn.fly.commons.c;

import android.content.Context;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.ad;
import cn.fly.tools.utils.ResHelper;
import java.io.File;
import java.util.HashMap;

/* loaded from: classes.dex */
public class d {
    private static final String a = cn.fly.commons.n.a("005Bbjbddg!b=dg");

    public static synchronized HashMap<String, Object> a(Context context) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> mapA = a();
        boolean z = mapA != null && mapA.size() > 0;
        if (z) {
            HashMap map2 = new HashMap();
            if (mapA.containsKey(cn.fly.commons.n.a("004Ibebabgba"))) {
                mapA.put(cn.fly.commons.n.a("005b$bebabgba"), mapA.remove(cn.fly.commons.n.a("004)bebabgba")));
            }
            map2.putAll(mapA);
            map.put(cn.fly.commons.n.a("009'cdbgbadgcbYbafd"), map2);
        }
        String strAn = cn.fly.tools.b.c.a(context).d().an();
        if (!z && TextUtils.isEmpty(strAn)) {
            return null;
        }
        map.put(cn.fly.commons.n.a("004AbiNb bgba"), strAn);
        a(strAn);
        return map;
    }

    public static String b(Context context) {
        return e.b(context);
    }

    private static HashMap<String, Object> a() {
        HashMap<String, Object> map;
        File file = new File(FlySDK.getContext().getFilesDir().getAbsolutePath() + cn.fly.commons.n.a("005jBfabiddPj"), a);
        if (!file.exists()) {
            map = null;
        } else {
            map = (HashMap) ResHelper.readObjectFromFile(file.getAbsolutePath());
            ad.b().b("all_ds", map);
            file.delete();
        }
        if (map == null || map.isEmpty()) {
            return (HashMap) ad.b().c("all_ds", null);
        }
        return map;
    }

    private static void a(String str) {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(str)) {
            map.put(cn.fly.commons.n.a("0042biEb2bgba"), str);
        }
        ad.b().b("all_ds", map);
    }
}