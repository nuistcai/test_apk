package cn.fly.mgs.a;

import cn.fly.FlySDK;
import cn.fly.commons.CSCenter;
import cn.fly.tools.utils.DH;

/* loaded from: classes.dex */
public class b {
    /* JADX WARN: Type inference failed for: r0v2, types: [cn.fly.mgs.a.b$1] */
    public static void a() {
        if (CSCenter.getInstance().isAppListDataEnable()) {
            new h() { // from class: cn.fly.mgs.a.b.1
                @Override // cn.fly.tools.utils.j
                protected void a() throws Throwable {
                    boolean zIsInMainProcess = DH.SyncMtd.isInMainProcess();
                    e.a().a("mgs init, main p: " + zIsInMainProcess);
                    if (zIsInMainProcess && !FlySDK.isForb()) {
                        f.a().b();
                        c.a().b();
                    }
                }
            }.start();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [cn.fly.mgs.a.b$2] */
    public static void a(final boolean z, final boolean z2) {
        new h() { // from class: cn.fly.mgs.a.b.2
            @Override // cn.fly.tools.utils.j
            protected void a() throws Throwable {
                d.a(z, z2);
                i.b(z);
                String strF = f.a().f();
                String strA = cn.fly.mcl.b.a.a();
                e.a().a("[setDS] save buff DId: " + strF + ", GId: " + strA);
                i.a(strF);
                i.b(strA);
            }
        }.start();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [cn.fly.mgs.a.b$3] */
    public static void a(final boolean z) {
        new h() { // from class: cn.fly.mgs.a.b.3
            @Override // cn.fly.tools.utils.j
            protected void a() throws Throwable {
                Boolean boolB = i.b();
                if (boolB == null) {
                    b.a(z, false);
                    return;
                }
                String strF = f.a().f();
                String strC = i.c();
                String strA = cn.fly.mcl.b.a.a();
                String strD = i.d();
                e.a().a("[setDS] currDId: " + strF + ", buffDId: " + strC);
                e.a().a("[setDS] currGId: " + strA + ", buffGId: " + strD);
                if (!strC.equals(strF) || !strD.equals(strA)) {
                    b.a(z, true);
                }
                if (z != boolB.booleanValue()) {
                    b.a(z, false);
                }
            }
        }.start();
        i.a(z);
    }

    public static boolean b() {
        Boolean boolC = c();
        if (boolC == null) {
            boolC = true;
        }
        return boolC.booleanValue();
    }

    private static Boolean c() {
        Boolean boolA = i.a();
        Boolean boolB = i.b();
        if (boolA != null && (boolB == null || boolA != boolB)) {
            a(boolA.booleanValue(), false);
        }
        return boolA;
    }
}