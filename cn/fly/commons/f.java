package cn.fly.commons;

import android.content.Context;
import android.text.TextUtils;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetCommunicator;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes.dex */
public final class f {
    private static volatile Boolean b;
    private static volatile String c;
    static volatile String a = null;
    private static volatile boolean d = false;
    private static HashSet<String> e = new HashSet<>();
    private static final a f = new a();

    public static boolean a() {
        return !c.a();
    }

    public static String a(Context context) {
        return cn.fly.tools.b.c.a(context).d().ao();
    }

    public static String b() {
        if (a()) {
            return null;
        }
        if (TextUtils.isEmpty(a)) {
            String strA = d().a();
            if (!TextUtils.isEmpty(strA) && TextUtils.isEmpty(a)) {
                a = strA;
            }
        }
        return a;
    }

    public static synchronized String a(FlyProduct flyProduct) {
        HashMap<String, Object> mapB = b(flyProduct);
        if (mapB == null) {
            return null;
        }
        return (String) mapB.get(NetCommunicator.KEY_DUID);
    }

    public static synchronized HashMap<String, Object> b(final FlyProduct flyProduct) {
        boolean z;
        HashMap<String, Object> map;
        if (flyProduct == null) {
            z = false;
        } else {
            ac.a(flyProduct);
            z = !e.contains(flyProduct.getProductTag());
            if (z) {
                e.add(flyProduct.getProductTag());
            }
        }
        if (TextUtils.isEmpty(a)) {
            a = d().b();
            z = true;
        }
        FlyLog.getInstance().d("aut pro: " + flyProduct + ", ndReg: " + z + ", hsReged: " + d, new Object[0]);
        if (z || !d) {
            ab.a.execute(new cn.fly.tools.utils.i() { // from class: cn.fly.commons.f.1
                @Override // cn.fly.tools.utils.i
                protected void a() {
                    if (c.a(m.a("002@fefk"))) {
                        boolean unused = f.d = true;
                        if (!c.d()) {
                            int i = 0;
                            while (i < 5) {
                                i++;
                                try {
                                    Thread.sleep(5000L);
                                    if (c.d()) {
                                        break;
                                    }
                                } catch (Throwable th) {
                                }
                            }
                        }
                        if (c.d()) {
                            f.d().a(flyProduct, new cn.fly.tools.utils.d<Void>() { // from class: cn.fly.commons.f.1.1
                                @Override // cn.fly.tools.utils.d
                                public void a(Void r1) {
                                }
                            });
                        }
                    }
                }
            });
        }
        if (b == null) {
            String strB = ad.b().b("key_curr_passed_duid", (String) null);
            c = strB;
            if (!TextUtils.isEmpty(strB) && !strB.equals(a)) {
                b = true;
            } else {
                b = false;
            }
        }
        ad.b().a("key_curr_passed_duid", a);
        map = new HashMap<>();
        map.put(NetCommunicator.KEY_DUID, a);
        map.put(NetCommunicator.KEY_IS_MODIFIED, Boolean.valueOf(b.booleanValue()));
        map.put(NetCommunicator.KEY_DUID_PREVIOUS, c);
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static a d() {
        return f;
    }
}