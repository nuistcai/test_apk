package cn.fly.mcl.a;

import android.os.Bundle;
import android.text.TextUtils;
import cn.fly.commons.m;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.network.StringPart;
import cn.fly.tools.utils.HashonHelper;
import java.io.Serializable;
import java.util.HashMap;

/* loaded from: classes.dex */
public class b implements Serializable {
    public String a;
    public String b;
    public String c;
    public String d;
    public int e;
    public int f;
    public int g;

    public static HashMap<String, String> a(String str) {
        if (!TextUtils.isEmpty(str)) {
            return HashonHelper.fromJson(str);
        }
        return null;
    }

    public static b a(Bundle bundle) {
        b bVar = new b();
        if (bundle != null) {
            bVar.a = bundle.getString(m.a("004k1ge'lh"));
            bVar.b = bundle.getString("url");
            bVar.c = bundle.getString("headers");
            bVar.e = bundle.getInt("chunkLength");
            bVar.d = bundle.getString("body");
            bVar.f = bundle.getInt("readTimout");
            bVar.g = bundle.getInt("connectionTimeout");
        }
        return bVar;
    }

    public static Bundle a(String str, String str2, HashMap<String, String> map, StringPart stringPart, int i, NetworkHelper.NetworkTimeOut networkTimeOut) {
        Bundle bundle = new Bundle();
        bundle.putString(m.a("004kEgeGlh"), str);
        bundle.putString("url", str2);
        HashMap map2 = new HashMap();
        if (map != null) {
            map2.putAll(map);
        }
        new HashonHelper();
        bundle.putString("headers", HashonHelper.fromHashMap(map2));
        bundle.putInt("chunkLength", i);
        if (stringPart != null) {
            bundle.putString("body", stringPart.toString());
        }
        bundle.putInt("readTimout", networkTimeOut.readTimout);
        bundle.putInt("connectionTimeout", networkTimeOut.connectionTimeout);
        return bundle;
    }
}