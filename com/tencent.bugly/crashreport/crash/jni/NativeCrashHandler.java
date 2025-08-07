package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.io.File;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public class NativeCrashHandler implements com.tencent.bugly.crashreport.a {
    private static NativeCrashHandler a;
    private static boolean l = false;
    private static boolean m = false;
    private static boolean o = true;
    private final Context b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final w d;
    private NativeExceptionHandler e;
    private String f;
    private final boolean g;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private com.tencent.bugly.crashreport.crash.b n;

    protected native boolean appendNativeLog(String str, String str2, String str3);

    protected native boolean appendWholeNativeLog(String str);

    protected native String getNativeKeyValueList();

    protected native String getNativeLog();

    protected native boolean putNativeKeyValue(String str, String str2);

    protected native String regist(String str, boolean z, int i);

    protected native String removeNativeKeyValue(String str);

    protected native void setNativeInfo(int i, String str);

    protected native void testCrash();

    protected native String unregist();

    static /* synthetic */ boolean a(NativeCrashHandler nativeCrashHandler, int i, String str) {
        return nativeCrashHandler.a(999, str);
    }

    private NativeCrashHandler(Context context, com.tencent.bugly.crashreport.common.info.a aVar, com.tencent.bugly.crashreport.crash.b bVar, w wVar, boolean z, String str) {
        this.b = z.a(context);
        try {
            if (z.a(str)) {
                str = context.getDir("bugly", 0).getAbsolutePath();
            }
        } catch (Throwable th) {
            str = "/data/data/" + com.tencent.bugly.crashreport.common.info.a.a(context).c + "/app_bugly";
        }
        this.n = bVar;
        this.f = str;
        this.c = aVar;
        this.d = wVar;
        this.g = z;
        this.e = new a(context, aVar, bVar, com.tencent.bugly.crashreport.common.strategy.a.a());
    }

    public static synchronized NativeCrashHandler getInstance(Context context, com.tencent.bugly.crashreport.common.info.a aVar, com.tencent.bugly.crashreport.crash.b bVar, com.tencent.bugly.crashreport.common.strategy.a aVar2, w wVar, boolean z, String str) {
        if (a == null) {
            a = new NativeCrashHandler(context, aVar, bVar, wVar, z, str);
        }
        return a;
    }

    public static synchronized NativeCrashHandler getInstance() {
        return a;
    }

    public synchronized String getDumpFilePath() {
        return this.f;
    }

    public synchronized void setDumpFilePath(String str) {
        this.f = str;
    }

    public static void setShouldHandleInJava(boolean z) {
        o = z;
        if (a != null) {
            a.a(999, new StringBuilder().append(z).toString());
        }
    }

    public static boolean isShouldHandleInJava() {
        return o;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(17:13|(1:15)(16:17|(1:19)|76|21|(1:23)|24|(1:26)|29|(1:31)(1:32)|33|(1:35)(1:36)|37|(1:39)|40|41|42)|16|76|21|(0)|24|(0)|29|(0)(0)|33|(0)(0)|37|(0)|40|41|42) */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0083 A[Catch: all -> 0x0092, TryCatch #1 {all -> 0x0092, blocks: (B:21:0x0079, B:23:0x0083, B:24:0x0085, B:26:0x008f), top: B:76:0x0079 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008f A[Catch: all -> 0x0092, TRY_LEAVE, TryCatch #1 {all -> 0x0092, blocks: (B:21:0x0079, B:23:0x0083, B:24:0x0085, B:26:0x008f), top: B:76:0x0079 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0097 A[Catch: all -> 0x00fa, TryCatch #3 {all -> 0x00fa, blocks: (B:11:0x0015, B:13:0x001d, B:15:0x004f, B:16:0x005e, B:29:0x0093, B:31:0x0097, B:33:0x00a6, B:35:0x00aa, B:37:0x00b9, B:39:0x00d1, B:40:0x00e7, B:36:0x00b2, B:32:0x009f, B:17:0x0063, B:19:0x0069), top: B:80:0x0015, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009f A[Catch: all -> 0x00fa, TryCatch #3 {all -> 0x00fa, blocks: (B:11:0x0015, B:13:0x001d, B:15:0x004f, B:16:0x005e, B:29:0x0093, B:31:0x0097, B:33:0x00a6, B:35:0x00aa, B:37:0x00b9, B:39:0x00d1, B:40:0x00e7, B:36:0x00b2, B:32:0x009f, B:17:0x0063, B:19:0x0069), top: B:80:0x0015, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00aa A[Catch: all -> 0x00fa, TryCatch #3 {all -> 0x00fa, blocks: (B:11:0x0015, B:13:0x001d, B:15:0x004f, B:16:0x005e, B:29:0x0093, B:31:0x0097, B:33:0x00a6, B:35:0x00aa, B:37:0x00b9, B:39:0x00d1, B:40:0x00e7, B:36:0x00b2, B:32:0x009f, B:17:0x0063, B:19:0x0069), top: B:80:0x0015, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b2 A[Catch: all -> 0x00fa, TryCatch #3 {all -> 0x00fa, blocks: (B:11:0x0015, B:13:0x001d, B:15:0x004f, B:16:0x005e, B:29:0x0093, B:31:0x0097, B:33:0x00a6, B:35:0x00aa, B:37:0x00b9, B:39:0x00d1, B:40:0x00e7, B:36:0x00b2, B:32:0x009f, B:17:0x0063, B:19:0x0069), top: B:80:0x0015, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d1 A[Catch: all -> 0x00fa, TryCatch #3 {all -> 0x00fa, blocks: (B:11:0x0015, B:13:0x001d, B:15:0x004f, B:16:0x005e, B:29:0x0093, B:31:0x0097, B:33:0x00a6, B:35:0x00aa, B:37:0x00b9, B:39:0x00d1, B:40:0x00e7, B:36:0x00b2, B:32:0x009f, B:17:0x0063, B:19:0x0069), top: B:80:0x0015, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized void a(boolean z) {
        StringBuilder sbAppend;
        if (this.j) {
            x.d("[Native] Native crash report has already registered.", new Object[0]);
            return;
        }
        if (this.i) {
            try {
                String strRegist = regist(this.f, z, 1);
                if (strRegist != null) {
                    x.a("[Native] Native Crash Report enable.", new Object[0]);
                    x.c("[Native] Check extra jni for Bugly NDK v%s", strRegist);
                    String strReplace = "2.1.1".replace(".", "");
                    String strReplace2 = "2.3.0".replace(".", "");
                    String strReplace3 = strRegist.replace(".", "");
                    if (strReplace3.length() != 2) {
                        if (strReplace3.length() == 1) {
                            sbAppend = new StringBuilder().append(strReplace3).append("00");
                        }
                        if (Integer.parseInt(strReplace3) >= Integer.parseInt(strReplace)) {
                            l = true;
                        }
                        if (Integer.parseInt(strReplace3) >= Integer.parseInt(strReplace2)) {
                            m = true;
                        }
                        if (m) {
                            x.d("[Native] Info setting jni can not be accessed.", new Object[0]);
                        } else {
                            x.a("[Native] Info setting jni can be accessed.", new Object[0]);
                        }
                        if (l) {
                            x.d("[Native] Extra jni can not be accessed.", new Object[0]);
                        } else {
                            x.a("[Native] Extra jni can be accessed.", new Object[0]);
                        }
                        this.c.o = strRegist;
                        if (!this.c.f.contains("-".concat(this.c.o))) {
                            this.c.f = this.c.f.concat("-").concat(this.c.o);
                        }
                        x.a("comInfo.sdkVersion %s", this.c.f);
                        this.j = true;
                        return;
                    }
                    sbAppend = new StringBuilder().append(strReplace3).append("0");
                    strReplace3 = sbAppend.toString();
                    if (Integer.parseInt(strReplace3) >= Integer.parseInt(strReplace)) {
                    }
                    if (Integer.parseInt(strReplace3) >= Integer.parseInt(strReplace2)) {
                    }
                    if (m) {
                    }
                    if (l) {
                    }
                    this.c.o = strRegist;
                    if (!this.c.f.contains("-".concat(this.c.o))) {
                    }
                    x.a("comInfo.sdkVersion %s", this.c.f);
                    this.j = true;
                    return;
                }
            } catch (Throwable th) {
                x.c("[Native] Failed to load Bugly SO file.", new Object[0]);
            }
        } else if (this.h) {
            try {
                Class cls = Integer.TYPE;
                String str = (String) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler2", null, new Class[]{String.class, String.class, cls, cls}, new Object[]{this.f, com.tencent.bugly.crashreport.common.info.b.a(this.b, false), Integer.valueOf(z ? 1 : 5), 1});
                if (str == null) {
                    Class[] clsArr = {String.class, String.class, Integer.TYPE};
                    String str2 = this.f;
                    String strA = com.tencent.bugly.crashreport.common.info.b.a(this.b, false);
                    com.tencent.bugly.crashreport.common.info.a.b();
                    str = (String) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler", null, clsArr, new Object[]{str2, strA, Integer.valueOf(com.tencent.bugly.crashreport.common.info.a.K())});
                }
                if (str != null) {
                    this.j = true;
                    this.c.o = str;
                    Boolean bool = (Boolean) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "checkExtraJni", null, new Class[]{String.class}, new Object[]{str});
                    if (bool != null) {
                        l = bool.booleanValue();
                    }
                    z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", null, new Class[]{Boolean.TYPE}, new Object[]{true});
                    z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "setLogMode", null, new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(z ? 1 : 5)});
                    return;
                }
            } catch (Throwable th2) {
            }
        }
        this.i = false;
        this.h = false;
    }

    public synchronized void startNativeMonitor() {
        if (!this.i && !this.h) {
            String str = "Bugly";
            boolean z = !z.a(this.c.n);
            String str2 = this.c.n;
            if (z) {
                str = str2;
            } else {
                this.c.getClass();
            }
            this.i = a(str, z);
            if (this.i || this.h) {
                a(this.g);
                if (l) {
                    setNativeAppVersion(this.c.k);
                    setNativeAppChannel(this.c.m);
                    setNativeAppPackage(this.c.c);
                    setNativeUserId(this.c.g());
                    setNativeIsAppForeground(this.c.a());
                    setNativeLaunchTime(this.c.a);
                }
                return;
            }
            return;
        }
        a(this.g);
    }

    public void checkUploadRecordCrash() {
        this.d.a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.1
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                if (z.a(NativeCrashHandler.this.b, "native_record_lock", 10000L)) {
                    if (!NativeCrashHandler.o) {
                        NativeCrashHandler.a(NativeCrashHandler.this, 999, Bugly.SDK_IS_DEV);
                    }
                    CrashDetailBean crashDetailBeanA = b.a(NativeCrashHandler.this.b, NativeCrashHandler.this.f, NativeCrashHandler.this.e);
                    if (crashDetailBeanA != null) {
                        x.a("[Native] Get crash from native record.", new Object[0]);
                        if (!NativeCrashHandler.this.n.a(crashDetailBeanA)) {
                            NativeCrashHandler.this.n.a(crashDetailBeanA, 3000L, false);
                        }
                        b.a(false, NativeCrashHandler.this.f);
                    }
                    NativeCrashHandler.this.a();
                    z.b(NativeCrashHandler.this.b, "native_record_lock");
                    return;
                }
                x.a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
            }
        });
    }

    private static boolean a(String str, boolean z) {
        boolean z2;
        try {
            x.a("[Native] Trying to load so: %s", str);
            if (z) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
        } catch (Throwable th) {
            th = th;
            z2 = false;
        }
        try {
            x.a("[Native] Successfully loaded SO: %s", str);
            return true;
        } catch (Throwable th2) {
            th = th2;
            z2 = true;
            x.d(th.getMessage(), new Object[0]);
            x.d("[Native] Failed to load so: %s", str);
            return z2;
        }
    }

    private synchronized void c() {
        if (!this.j) {
            x.d("[Native] Native crash report has already unregistered.", new Object[0]);
            return;
        }
        try {
        } catch (Throwable th) {
            x.c("[Native] Failed to close native crash report.", new Object[0]);
        }
        if (unregist() != null) {
            x.a("[Native] Successfully closed native crash report.", new Object[0]);
            this.j = false;
            return;
        }
        try {
            z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", null, new Class[]{Boolean.TYPE}, new Object[]{false});
            this.j = false;
            x.a("[Native] Successfully closed native crash report.", new Object[0]);
            return;
        } catch (Throwable th2) {
            x.c("[Native] Failed to close native crash report.", new Object[0]);
            this.i = false;
            this.h = false;
            return;
        }
    }

    public void testNativeCrash() {
        if (!this.i) {
            x.d("[Native] Bugly SO file has not been load.", new Object[0]);
        } else {
            testCrash();
        }
    }

    public void testNativeCrash(boolean z, boolean z2, boolean z3) {
        a(16, new StringBuilder().append(z).toString());
        a(17, new StringBuilder().append(z2).toString());
        a(18, new StringBuilder().append(z3).toString());
        testNativeCrash();
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.e;
    }

    protected final void a() {
        long jB = z.b() - c.g;
        long jB2 = z.b() + 86400000;
        File file = new File(this.f);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                    int i = 0;
                    int i2 = 0;
                    for (File file2 : fileArrListFiles) {
                        long jLastModified = file2.lastModified();
                        if (jLastModified < jB || jLastModified >= jB2) {
                            x.a("[Native] Delete record file: %s", file2.getAbsolutePath());
                            i++;
                            if (file2.delete()) {
                                i2++;
                            }
                        }
                    }
                    x.c("[Native] Number of record files overdue: %d, has deleted: %d", Integer.valueOf(i), Integer.valueOf(i2));
                }
            } catch (Throwable th) {
                x.a(th);
            }
        }
    }

    public void removeEmptyNativeRecordFiles() {
        b.c(this.f);
    }

    private synchronized void b(boolean z) {
        if (z) {
            startNativeMonitor();
        } else {
            c();
        }
    }

    public synchronized boolean isUserOpened() {
        return this.k;
    }

    private synchronized void c(boolean z) {
        if (this.k != z) {
            x.a("user change native %b", Boolean.valueOf(z));
            this.k = z;
        }
    }

    public synchronized void setUserOpened(boolean z) {
        c(z);
        boolean zIsUserOpened = isUserOpened();
        com.tencent.bugly.crashreport.common.strategy.a aVarA = com.tencent.bugly.crashreport.common.strategy.a.a();
        if (aVarA != null) {
            zIsUserOpened = zIsUserOpened && aVarA.c().g;
        }
        if (zIsUserOpened != this.j) {
            x.a("native changed to %b", Boolean.valueOf(zIsUserOpened));
            b(zIsUserOpened);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0031 A[Catch: all -> 0x0043, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0005, B:7:0x000b, B:8:0x001a, B:10:0x0026, B:14:0x002d, B:16:0x0031), top: B:22:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void onStrategyChanged(StrategyBean strategyBean) {
        boolean z;
        if (strategyBean != null) {
            if (strategyBean.g != this.j) {
                x.d("server native changed to %b", Boolean.valueOf(strategyBean.g));
            }
            z = !com.tencent.bugly.crashreport.common.strategy.a.a().c().g && this.k;
            if (z != this.j) {
                x.a("native changed to %b", Boolean.valueOf(z));
                b(z);
            }
        } else if (com.tencent.bugly.crashreport.common.strategy.a.a().c().g) {
            if (z != this.j) {
            }
        }
    }

    public boolean appendLogToNative(String str, String str2, String str3) {
        if ((!this.h && !this.i) || !l || str == null || str2 == null || str3 == null) {
            return false;
        }
        try {
            if (this.i) {
                return appendNativeLog(str, str2, str3);
            }
            Boolean bool = (Boolean) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "appendNativeLog", null, new Class[]{String.class, String.class, String.class}, new Object[]{str, str2, str3});
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (UnsatisfiedLinkError e) {
            l = false;
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public String getLogFromNative() {
        if ((!this.h && !this.i) || !l) {
            return null;
        }
        try {
            if (this.i) {
                return getNativeLog();
            }
            return (String) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "getNativeLog", null, null, null);
        } catch (UnsatisfiedLinkError e) {
            l = false;
            return null;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public boolean putKeyValueToNative(String str, String str2) {
        if ((!this.h && !this.i) || !l || str == null || str2 == null) {
            return false;
        }
        try {
            if (this.i) {
                return putNativeKeyValue(str, str2);
            }
            Boolean bool = (Boolean) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "putNativeKeyValue", null, new Class[]{String.class, String.class}, new Object[]{str, str2});
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (UnsatisfiedLinkError e) {
            l = false;
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    private boolean a(int i, String str) {
        if (!this.i || !m) {
            return false;
        }
        try {
            setNativeInfo(i, str);
            return true;
        } catch (UnsatisfiedLinkError e) {
            m = false;
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public boolean filterSigabrtSysLog() {
        return a(998, "true");
    }

    public boolean setNativeAppVersion(String str) {
        return a(10, str);
    }

    public boolean setNativeAppChannel(String str) {
        return a(12, str);
    }

    public boolean setNativeAppPackage(String str) {
        return a(13, str);
    }

    public boolean setNativeUserId(String str) {
        return a(11, str);
    }

    @Override // com.tencent.bugly.crashreport.a
    public boolean setNativeIsAppForeground(boolean z) {
        return a(14, z ? "true" : Bugly.SDK_IS_DEV);
    }

    public boolean setNativeLaunchTime(long j) {
        try {
            return a(15, String.valueOf(j));
        } catch (NumberFormatException e) {
            if (!x.a(e)) {
                e.printStackTrace();
                return false;
            }
            return false;
        }
    }
}