package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import android.os.Build;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class c {
    public static int a = 0;
    public static boolean b = false;
    public static int c = 2;
    public static boolean d = true;
    public static int e = 20480;
    public static int f = 20480;
    public static long g = 604800000;
    public static String h = null;
    public static boolean i = false;
    public static String j = null;
    public static int k = 5000;
    public static boolean l = true;
    public static boolean m = false;
    public static String n = null;
    public static String o = null;
    private static c r;
    public final b p;
    private final Context q;
    private final e s;
    private final NativeCrashHandler t;
    private com.tencent.bugly.crashreport.common.strategy.a u;
    private w v;
    private final com.tencent.bugly.crashreport.crash.anr.b w;
    private Boolean x;
    private int y = 31;
    private boolean z = false;

    private c(int i2, Context context, w wVar, boolean z, BuglyStrategy.a aVar, o oVar, String str) {
        a = i2;
        Context contextA = z.a(context);
        this.q = contextA;
        this.u = com.tencent.bugly.crashreport.common.strategy.a.a();
        this.v = wVar;
        this.p = new b(i2, contextA, u.a(), p.a(), this.u, aVar, oVar);
        com.tencent.bugly.crashreport.common.info.a aVarA = com.tencent.bugly.crashreport.common.info.a.a(contextA);
        this.s = new e(contextA, this.p, this.u, aVarA);
        this.t = NativeCrashHandler.getInstance(contextA, aVarA, this.p, this.u, wVar, z, str);
        aVarA.F = this.t;
        this.w = new com.tencent.bugly.crashreport.crash.anr.b(contextA, this.u, aVarA, wVar, this.p);
    }

    public static synchronized c a(int i2, Context context, boolean z, BuglyStrategy.a aVar, o oVar, String str) {
        if (r == null) {
            r = new c(1004, context, w.a(), z, aVar, null, null);
        }
        return r;
    }

    public static synchronized c a() {
        return r;
    }

    public final void a(StrategyBean strategyBean) {
        this.s.a(strategyBean);
        this.t.onStrategyChanged(strategyBean);
        this.w.c();
        w.a().a(new AnonymousClass2(), 3000L);
    }

    public final boolean b() {
        Boolean bool = this.x;
        if (bool != null) {
            return bool.booleanValue();
        }
        String str = com.tencent.bugly.crashreport.common.info.a.b().d;
        List<r> listA = p.a().a(1);
        ArrayList arrayList = new ArrayList();
        if (listA != null && listA.size() > 0) {
            for (r rVar : listA) {
                if (str.equals(rVar.c)) {
                    this.x = true;
                    arrayList.add(rVar);
                }
            }
            if (arrayList.size() > 0) {
                p.a().a(arrayList);
            }
            return true;
        }
        this.x = false;
        return false;
    }

    public final synchronized void c() {
        this.s.a();
        this.t.setUserOpened(true);
        if (Build.VERSION.SDK_INT <= 19) {
            this.w.a(true);
        } else {
            this.w.d();
        }
    }

    public final synchronized void d() {
        this.s.b();
        this.t.setUserOpened(false);
        this.w.e();
    }

    public final void e() {
        this.s.a();
    }

    public final void f() {
        this.t.setUserOpened(false);
    }

    public final void g() {
        this.t.setUserOpened(true);
    }

    public final void h() {
        if (Build.VERSION.SDK_INT <= 19) {
            this.w.a(true);
        } else {
            this.w.d();
        }
    }

    public final void i() {
        this.w.e();
    }

    public final synchronized void a(boolean z, boolean z2, boolean z3) {
        this.t.testNativeCrash(z, z2, z3);
    }

    public final synchronized void j() {
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (i2 < 30) {
                try {
                    x.a("try main sleep for make a test anr! try:%d/30 , kill it if you don't want to wait!", Integer.valueOf(i3));
                    z.b(5000L);
                    i2 = i3;
                } catch (Throwable th) {
                    if (x.a(th)) {
                        return;
                    }
                    th.printStackTrace();
                    return;
                }
            }
        }
    }

    public final boolean k() {
        return this.w.a();
    }

    public final void a(final Thread thread, final Throwable th, boolean z, String str, byte[] bArr, final boolean z2) {
        final String str2 = null;
        final byte[] bArr2 = null;
        final boolean z3 = false;
        this.v.a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.c.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    x.c("post a throwable %b", Boolean.valueOf(z3));
                    c.this.s.a(thread, th, false, str2, bArr2);
                    if (z2) {
                        x.a("clear user datas", new Object[0]);
                        com.tencent.bugly.crashreport.common.info.a.a(c.this.q).C();
                    }
                } catch (Throwable th2) {
                    if (!x.b(th2)) {
                        th2.printStackTrace();
                    }
                    x.e("java catch error: %s", th.toString());
                }
            }
        });
    }

    public final void a(CrashDetailBean crashDetailBean) {
        this.p.e(crashDetailBean);
    }

    /* compiled from: BUGLY */
    /* renamed from: com.tencent.bugly.crashreport.crash.c$2, reason: invalid class name */
    final class AnonymousClass2 extends Thread {
        AnonymousClass2() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            List<CrashDetailBean> list;
            if (!z.a(c.this.q, "local_crash_lock", 10000L)) {
                return;
            }
            List<CrashDetailBean> listA = c.this.p.a();
            if (listA != null && listA.size() > 0) {
                x.c("Size of crash list: %s", Integer.valueOf(listA.size()));
                int size = listA.size();
                if (size <= 20) {
                    list = listA;
                } else {
                    ArrayList arrayList = new ArrayList();
                    Collections.sort(listA);
                    for (int i = 0; i < 20; i++) {
                        arrayList.add(listA.get((size - 1) - i));
                    }
                    list = arrayList;
                }
                c.this.p.a(list, 0L, false, false, false);
            }
            z.b(c.this.q, "local_crash_lock");
        }
    }

    public final void a(long j2) {
        w.a().a(new AnonymousClass2(), j2);
    }

    public final void l() {
        this.t.checkUploadRecordCrash();
    }

    public final void m() {
        if (com.tencent.bugly.crashreport.common.info.a.b().d.equals(AppInfo.a(this.q))) {
            this.t.removeEmptyNativeRecordFiles();
        }
    }

    public final void a(int i2) {
        this.y = i2;
    }

    public final void a(boolean z) {
        this.z = z;
    }

    public final boolean n() {
        return this.z;
    }

    public final boolean o() {
        return (this.y & 16) > 0;
    }

    public final boolean p() {
        return (this.y & 8) > 0;
    }

    public final boolean q() {
        return (this.y & 4) > 0;
    }

    public final boolean r() {
        return (this.y & 2) > 0;
    }

    public final boolean s() {
        return (this.y & 1) > 0;
    }
}