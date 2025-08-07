package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import com.tencent.bugly.proguard.z;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class a implements NativeExceptionHandler {
    private final Context a;
    private final com.tencent.bugly.crashreport.crash.b b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final com.tencent.bugly.crashreport.common.strategy.a d;

    public a(Context context, com.tencent.bugly.crashreport.common.info.a aVar, com.tencent.bugly.crashreport.crash.b bVar, com.tencent.bugly.crashreport.common.strategy.a aVar2) {
        this.a = context;
        this.b = bVar;
        this.c = aVar;
        this.d = aVar2;
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final CrashDetailBean packageCrashDatas(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, byte[] bArr, Map<String, String> map, boolean z, boolean z2) throws IOException {
        String dumpFilePath;
        int length;
        String str12;
        int iIndexOf;
        boolean zK = c.a().k();
        if (zK) {
            x.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.b = 1;
        crashDetailBean.e = this.c.h();
        crashDetailBean.f = this.c.k;
        crashDetailBean.g = this.c.w();
        crashDetailBean.m = this.c.g();
        crashDetailBean.n = str3;
        crashDetailBean.o = zK ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : "";
        crashDetailBean.p = str4;
        crashDetailBean.q = str5 != null ? str5 : "";
        crashDetailBean.r = j;
        crashDetailBean.u = z.b(crashDetailBean.q.getBytes());
        crashDetailBean.A = str;
        crashDetailBean.B = str2;
        crashDetailBean.I = this.c.y();
        crashDetailBean.h = this.c.v();
        crashDetailBean.i = this.c.J();
        crashDetailBean.v = str8;
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
        if (nativeCrashHandler == null) {
            dumpFilePath = null;
        } else {
            dumpFilePath = nativeCrashHandler.getDumpFilePath();
        }
        String strA = b.a(dumpFilePath, str8);
        if (!z.a(strA)) {
            crashDetailBean.V = strA;
        }
        crashDetailBean.W = b.b(dumpFilePath);
        crashDetailBean.w = b.a(str9, c.e, null, false);
        crashDetailBean.x = b.a(str10, c.e, null, true);
        crashDetailBean.J = str7;
        crashDetailBean.K = str6;
        crashDetailBean.L = str11;
        crashDetailBean.F = this.c.p();
        crashDetailBean.G = this.c.o();
        crashDetailBean.H = this.c.q();
        if (z) {
            crashDetailBean.C = com.tencent.bugly.crashreport.common.info.b.k();
            crashDetailBean.D = com.tencent.bugly.crashreport.common.info.b.i();
            crashDetailBean.E = com.tencent.bugly.crashreport.common.info.b.m();
            if (crashDetailBean.w == null) {
                crashDetailBean.w = z.a(this.a, c.e, (String) null);
            }
            crashDetailBean.y = y.a();
            crashDetailBean.M = this.c.a;
            crashDetailBean.N = this.c.a();
            crashDetailBean.Q = this.c.H();
            crashDetailBean.R = this.c.I();
            crashDetailBean.S = this.c.B();
            crashDetailBean.T = this.c.G();
            crashDetailBean.z = z.a(c.f, false);
            int iIndexOf2 = crashDetailBean.q.indexOf("java:\n");
            if (iIndexOf2 > 0 && (length = iIndexOf2 + "java:\n".length()) < crashDetailBean.q.length()) {
                String strSubstring = crashDetailBean.q.substring(length, crashDetailBean.q.length() - 1);
                if (strSubstring.length() > 0 && crashDetailBean.z.containsKey(crashDetailBean.B) && (iIndexOf = (str12 = crashDetailBean.z.get(crashDetailBean.B)).indexOf(strSubstring)) > 0) {
                    String strSubstring2 = str12.substring(iIndexOf);
                    crashDetailBean.z.put(crashDetailBean.B, strSubstring2);
                    crashDetailBean.q = crashDetailBean.q.substring(0, length);
                    crashDetailBean.q += strSubstring2;
                }
            }
            if (str == null) {
                crashDetailBean.A = this.c.d;
            }
            this.b.d(crashDetailBean);
        } else {
            crashDetailBean.C = -1L;
            crashDetailBean.D = -1L;
            crashDetailBean.E = -1L;
            if (crashDetailBean.w == null) {
                crashDetailBean.w = "this crash is occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            }
            crashDetailBean.M = -1L;
            crashDetailBean.Q = -1;
            crashDetailBean.R = -1;
            crashDetailBean.S = map;
            crashDetailBean.T = this.c.G();
            crashDetailBean.z = null;
            if (str == null) {
                crashDetailBean.A = "unknown(record)";
            }
            if (bArr != null) {
                crashDetailBean.y = bArr;
            }
        }
        return crashDetailBean;
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final void handleNativeException(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7) {
        x.a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(i, i2, j, j2, str, str2, str3, str4, i3, str5, i4, i5, i6, str6, str7, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x01cf A[Catch: all -> 0x02c0, TryCatch #0 {all -> 0x02c0, blocks: (B:3:0x0011, B:6:0x001d, B:14:0x0074, B:17:0x007c, B:19:0x007f, B:21:0x0083, B:23:0x009d, B:24:0x00a6, B:25:0x00b0, B:27:0x00bc, B:29:0x00c7, B:31:0x00cf, B:33:0x00db, B:35:0x00e5, B:38:0x00ec, B:40:0x00ff, B:42:0x0109, B:57:0x0185, B:58:0x01ac, B:60:0x01cf, B:61:0x01d6, B:64:0x01e2, B:66:0x01ea, B:45:0x0112, B:46:0x0129, B:48:0x012f, B:50:0x0140, B:54:0x0163, B:39:0x00f9, B:26:0x00b5, B:9:0x0046, B:10:0x004a, B:12:0x0054), top: B:98:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x024b A[Catch: all -> 0x02bc, TryCatch #2 {all -> 0x02bc, blocks: (B:69:0x0245, B:71:0x024b, B:73:0x0254), top: B:102:0x0245 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0254 A[Catch: all -> 0x02bc, TRY_LEAVE, TryCatch #2 {all -> 0x02bc, blocks: (B:69:0x0245, B:71:0x024b, B:73:0x0254), top: B:102:0x0245 }] */
    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleNativeException2(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7, String[] strArr) {
        String str8;
        String str9;
        String str10;
        boolean z;
        String str11;
        String str12;
        CrashDetailBean crashDetailBeanPackageCrashDatas;
        String dumpFilePath;
        boolean z2;
        String[] strArr2 = strArr;
        x.a("Native Crash Happen v2", new Object[0]);
        try {
            String strA = b.a(str3);
            String strA2 = "UNKNOWN";
            if (i3 > 0) {
                str9 = str + "(" + str5 + ")";
                str8 = "UNKNOWN";
                str10 = "KERNEL";
            } else {
                if (i4 > 0) {
                    strA2 = AppInfo.a(i4);
                }
                if (!strA2.equals(String.valueOf(i4))) {
                    str8 = strA2 + "(" + i4 + ")";
                    str9 = str;
                    str10 = str5;
                } else {
                    str8 = strA2;
                    str9 = str;
                    str10 = str5;
                }
            }
            HashMap map = new HashMap();
            if (strArr2 != null) {
                int i7 = 0;
                while (i7 < strArr2.length) {
                    String str13 = strArr2[i7];
                    if (str13 != null) {
                        x.a("Extra message[%d]: %s", Integer.valueOf(i7), str13);
                        String[] strArrSplit = str13.split("=");
                        if (strArrSplit.length == 2) {
                            map.put(strArrSplit[0], strArrSplit[1]);
                        } else {
                            x.d("bad extraMsg %s", str13);
                        }
                    }
                    i7++;
                    strArr2 = strArr;
                }
            } else {
                x.c("not found extraMsg", new Object[0]);
            }
            String str14 = (String) map.get("HasPendingException");
            if (str14 != null && str14.equals("true")) {
                x.a("Native crash happened with a Java pending exception.", new Object[0]);
                z = true;
            } else {
                z = false;
            }
            String str15 = (String) map.get("ExceptionProcessName");
            if (str15 == null || str15.length() == 0) {
                str11 = this.c.d;
            } else {
                x.c("Name of crash process: %s", str15);
                str11 = str15;
            }
            String str16 = (String) map.get("ExceptionThreadName");
            if (str16 == null || str16.length() == 0) {
                Thread threadCurrentThread = Thread.currentThread();
                str12 = threadCurrentThread.getName() + "(" + threadCurrentThread.getId() + ")";
                long j3 = (j * 1000) + (j2 / 1000);
                String str17 = (String) map.get("SysLogPath");
                String str18 = (String) map.get("JniLogPath");
                if (!this.d.b()) {
                    x.d("no remote but still store!", new Object[0]);
                }
                if (this.d.c().g && this.d.b()) {
                    x.e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
                    com.tencent.bugly.crashreport.crash.b.a("NATIVE_CRASH", z.a(), str11, str12, str9 + "\n" + str2 + "\n" + strA, null);
                    z.b(str4);
                    return;
                }
                String str19 = str9;
                try {
                    crashDetailBeanPackageCrashDatas = packageCrashDatas(str11, str12, j3, str9, str2, strA, str10, str8, str4, str17, str18, str7, null, null, true, z);
                    if (crashDetailBeanPackageCrashDatas != null) {
                        x.e("pkg crash datas fail!", new Object[0]);
                        return;
                    }
                    com.tencent.bugly.crashreport.crash.b.a("NATIVE_CRASH", z.a(), str11, str12, str19 + "\n" + str2 + "\n" + strA, crashDetailBeanPackageCrashDatas);
                    try {
                        boolean z3 = !this.b.b(crashDetailBeanPackageCrashDatas);
                        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance();
                        if (nativeCrashHandler == null) {
                            dumpFilePath = null;
                        } else {
                            dumpFilePath = nativeCrashHandler.getDumpFilePath();
                        }
                        b.a(true, dumpFilePath);
                        if (z3) {
                            this.b.a(crashDetailBeanPackageCrashDatas, 3000L, true);
                        }
                        this.b.c(crashDetailBeanPackageCrashDatas);
                    } catch (Throwable th) {
                        th = th;
                        if (!x.a(th)) {
                            th.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } else {
                x.c("Name of crash thread: %s", str16);
                Iterator<Thread> it = Thread.getAllStackTraces().keySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    }
                    Thread next = it.next();
                    if (next.getName().equals(str16)) {
                        str16 = str16 + "(" + next.getId() + ")";
                        z2 = true;
                        break;
                    }
                }
                str12 = !z2 ? str16 + "(" + i2 + ")" : str16;
                long j32 = (j * 1000) + (j2 / 1000);
                String str172 = (String) map.get("SysLogPath");
                String str182 = (String) map.get("JniLogPath");
                if (!this.d.b()) {
                }
                if (this.d.c().g) {
                }
                String str192 = str9;
                crashDetailBeanPackageCrashDatas = packageCrashDatas(str11, str12, j32, str9, str2, strA, str10, str8, str4, str172, str182, str7, null, null, true, z);
                if (crashDetailBeanPackageCrashDatas != null) {
                }
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }
}