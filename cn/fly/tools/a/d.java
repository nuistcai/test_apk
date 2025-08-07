package cn.fly.tools.a;

import android.content.Context;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.CSCenter;
import cn.fly.commons.o;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;

/* loaded from: classes.dex */
public class d {
    private static d a;
    private a b;

    public static synchronized d a(Context context) {
        if (a == null && context != null) {
            a = new d(context);
        }
        return a;
    }

    public boolean b(Context context) {
        return b.b(context);
    }

    private d(Context context) {
        try {
            boolean zA = a();
            boolean zB = b();
            boolean zC = c();
            boolean zIsDREnable = CSCenter.getInstance().isDREnable();
            int i = cn.fly.tools.b.c.a(context).d().aq().targetSdkVersion;
            int oSVersionIntForFly = DH.SyncMtd.getOSVersionIntForFly();
            boolean zE = C0041r.e();
            b("3xu: " + zB + ", 3xd: " + zA + ", dre: " + zIsDREnable + ", obf: " + zC + ", tar: " + i + ", api: " + oSVersionIntForFly + ", ovBklv: " + zE);
            if (i >= 30 && oSVersionIntForFly >= 30) {
                if (zIsDREnable && zA) {
                    b bVar = new b();
                    if (bVar.a(context)) {
                        b("3xd");
                        this.b = bVar;
                    }
                }
                boolean z = (!zB || zC || zE) ? false : true;
                if (this.b == null && z) {
                    c cVar = new c();
                    if (cVar.a(context)) {
                        b("3xu");
                        this.b = cVar;
                    }
                }
                return;
            }
            b("2x");
            this.b = new e();
        } catch (Throwable th) {
        }
    }

    public <T> T a(Class cls, Object obj, String str, Class[] clsArr, Object[] objArr, T t) throws Throwable {
        if (this.b != null) {
            return (T) this.b.a(cls, obj, str, clsArr, objArr);
        }
        return t;
    }

    public <T> T a(String str, Object obj, String str2, Class[] clsArr, Object[] objArr, T t) throws Throwable {
        if (this.b != null) {
            return (T) this.b.a(str, obj, str2, clsArr, objArr);
        }
        return t;
    }

    public <T> T a(String str) throws Throwable {
        if (this.b != null) {
            return (T) this.b.a(str);
        }
        return null;
    }

    public <T> T a(String str, String str2, Object obj, T t) throws Throwable {
        if (this.b != null) {
            return (T) this.b.a(str, str2, obj);
        }
        return t;
    }

    public <T> T a(String str, Class[] clsArr, Object[] objArr) throws Throwable {
        if (this.b != null) {
            return (T) this.b.a(str, clsArr, objArr);
        }
        return null;
    }

    public static void b(String str) {
        FlyLog.getInstance().d("[HH] " + str, new Object[0]);
    }

    public static void a(Throwable th) {
        FlyLog.getInstance().d(th, "[HH] ", new Object[0]);
    }

    private boolean a() {
        return ((Integer) cn.fly.commons.c.a(o.a("002?dcef"), 0)).intValue() == 1;
    }

    private boolean b() {
        return ((Integer) cn.fly.commons.c.a(o.a("002:dgfi"), 1)).intValue() == 1;
    }

    private boolean c() {
        return d();
    }

    private boolean d() {
        boolean z = true;
        try {
            if ("cn.fly.FlySDK".equals(FlySDK.class.getName())) {
                if (o.a("023ceRdlefNgYecdlKcNdkdfdfdk@e@fidledeled:feif)dj").equals(CSCenter.class.getName())) {
                    z = false;
                }
            }
        } catch (Throwable th) {
            a(th);
        }
        b("ck-cn: " + z);
        return z;
    }
}