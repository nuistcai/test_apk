package cn.fly.commons.b;

import android.content.Context;
import android.text.TextUtils;
import cn.fly.commons.ad;
import cn.fly.commons.c;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.g;
import cn.fly.tools.utils.h;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class a {
    private Context a;

    public a(Context context) {
        this.a = context;
    }

    public void a() {
        e("=====>");
        try {
            Map<List<String>, Boolean> mapB = b();
            if (mapB != null && !mapB.isEmpty()) {
                for (Map.Entry<List<String>, Boolean> entry : mapB.entrySet()) {
                    List<String> key = entry.getKey();
                    if (entry.getValue().booleanValue()) {
                        a(key);
                    } else {
                        e("ign: " + key);
                    }
                }
            }
        } catch (Throwable th) {
            a(th);
        }
        e("<=====");
    }

    private void a(List<String> list) {
        e("=> " + list);
        if (list != null && list.size() == 4) {
            try {
                String str = list.get(0);
                String str2 = list.get(1);
                String str3 = list.get(2);
                String str4 = list.get(3);
                int iA = a(str3);
                if (a(str3, iA) && !a(a(str3, str4, iA, (int) null), (Object) null)) {
                    String strA = a(str, str2, (String) null);
                    if (!TextUtils.isEmpty(strA) && !a(a(str3, str4, iA, (int) null), (Object) null)) {
                        a(str3, str4, strA, iA);
                    }
                }
            } catch (Throwable th) {
                a(th);
            }
        } else {
            e("oops");
        }
        e("<= " + list);
    }

    private boolean a(Object obj, Object obj2) {
        boolean z;
        if (!a(obj) && !Objects.equals(obj, obj2)) {
            z = true;
        } else {
            z = false;
        }
        e("val: " + obj + ", def: " + obj2 + ", val ext: " + z);
        return z;
    }

    private boolean a(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue() == -1;
        }
        if (obj instanceof Long) {
            return ((Long) obj).longValue() == -1;
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (!(obj instanceof Collection)) {
            return false;
        }
        return ((Collection) obj).isEmpty();
    }

    private int a(String str) {
        if (ad.a().equals(str)) {
            return 1;
        }
        if (g.b().equals(str)) {
            return 2;
        }
        e("unsupported: " + str);
        return 0;
    }

    private boolean a(String str, int i) {
        boolean zC = false;
        try {
            if (i == 1) {
                zC = ad.c();
            } else if (i == 2) {
                zC = g.c();
            } else {
                e("unsupported: " + str + ", tp: " + i);
            }
        } catch (Throwable th) {
            a(th);
        }
        e("ck slf: " + str + ", t: " + i + ", ext: " + zC);
        return zC;
    }

    private <T> T a(String str, String str2, int i, T t) {
        if (i == 1) {
            return (T) a(str2, (String) t);
        }
        if (i == 2) {
            return (T) b(str2, t);
        }
        e("unsupported: " + str + ", tp: " + i);
        return t;
    }

    private void a(String str, String str2, String str3, int i) {
        e("w2s f: " + str + ", k: " + str2 + ", v: " + str3 + "t: " + i);
        if (i == 1) {
            a(str2, str3);
        } else if (i == 2) {
            a(str2, str3, System.currentTimeMillis() + 604800000);
        } else {
            e("unsupported: " + str + ", tp: " + i);
        }
    }

    private String a(String str, String str2, String str3) {
        try {
            if (b(str)) {
                return new h(this.a, str).b(str2, str3);
            }
            return str3;
        } catch (Throwable th) {
            a(th);
            return str3;
        }
    }

    private <T> T a(String str, T t) {
        return (T) ad.b().c(str, t);
    }

    private void a(String str, String str2) {
        ad.b().a(str, str2);
    }

    private <T> T b(String str, T t) {
        return (T) g.a().b(str, t);
    }

    private void a(String str, String str2, long j) {
        g.a().a(str, str2, j);
    }

    private boolean b(String str) {
        boolean zD;
        try {
            zD = d(c(str));
        } catch (Throwable th) {
            a(th);
            zD = false;
        }
        e("ck tgt: " + str + " ext: " + zD);
        return zD;
    }

    private String c(String str) {
        return this.a.getApplicationInfo().dataDir + "/shared_prefs/" + str + ".xml";
    }

    private boolean d(String str) {
        return new File(str).exists();
    }

    private Map<List<String>, Boolean> b() {
        String[] strArrSplit;
        String[] strArrSplit2;
        HashMap map = new HashMap();
        try {
            String str = (String) c.a("spcfg", "");
            e("ori-cfg: " + str);
            if (str != null && !TextUtils.isEmpty(str.trim()) && (strArrSplit = str.split(";")) != null && strArrSplit.length > 0) {
                for (String str2 : strArrSplit) {
                    if (!TextUtils.isEmpty(str2) && (strArrSplit2 = str2.split(",")) != null && strArrSplit2.length > 0) {
                        ArrayList arrayList = new ArrayList();
                        boolean z = true;
                        for (String str3 : strArrSplit2) {
                            if (TextUtils.isEmpty(str3)) {
                                z = false;
                            }
                            arrayList.add(str3);
                        }
                        if (arrayList.size() != 4) {
                            z = false;
                        }
                        map.put(arrayList, Boolean.valueOf(z));
                    }
                }
            }
        } catch (Throwable th) {
            a(th);
        }
        e("ana-cfg: " + map);
        return map;
    }

    private void e(String str) {
        FlyLog.getInstance().d("[3GTH] " + str, new Object[0]);
    }

    private void a(Throwable th) {
        FlyLog.getInstance().e(th, "[3GTH]", new Object[0]);
    }
}