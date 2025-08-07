package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import androidx.core.app.NotificationCompat;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class v implements Runnable {
    private int a;
    private int b;
    private final Context c;
    private final int d;
    private final byte[] e;
    private final com.tencent.bugly.crashreport.common.info.a f;
    private final com.tencent.bugly.crashreport.common.strategy.a g;
    private final s h;
    private final u i;
    private final int j;
    private final t k;
    private final t l;
    private String m;
    private final String n;
    private final Map<String, String> o;
    private int p;
    private long q;
    private long r;
    private boolean s;
    private boolean t;

    public v(Context context, int i, int i2, byte[] bArr, String str, String str2, t tVar, boolean z, boolean z2) {
        this(context, i, i2, bArr, str, str2, tVar, z, 2, BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH, z2, null);
    }

    public v(Context context, int i, int i2, byte[] bArr, String str, String str2, t tVar, boolean z, int i3, int i4, boolean z2, Map<String, String> map) {
        this.a = 2;
        this.b = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
        this.m = null;
        this.p = 0;
        this.q = 0L;
        this.r = 0L;
        this.s = true;
        this.t = false;
        this.c = context;
        this.f = com.tencent.bugly.crashreport.common.info.a.a(context);
        this.e = bArr;
        this.g = com.tencent.bugly.crashreport.common.strategy.a.a();
        this.h = s.a(context);
        this.i = u.a();
        this.j = i;
        this.m = str;
        this.n = str2;
        this.k = tVar;
        this.l = null;
        this.s = z;
        this.d = i2;
        if (i3 > 0) {
            this.a = i3;
        }
        if (i4 > 0) {
            this.b = i4;
        }
        this.t = z2;
        this.o = map;
    }

    private void a(aq aqVar, boolean z, int i, String str, int i2) {
        String strValueOf;
        switch (this.d) {
            case 630:
            case 830:
                strValueOf = "crash";
                break;
            case 640:
            case 840:
                strValueOf = "userinfo";
                break;
            default:
                strValueOf = String.valueOf(this.d);
                break;
        }
        if (z) {
            x.a("[Upload] Success: %s", strValueOf);
        } else {
            x.e("[Upload] Failed to upload(%d) %s: %s", Integer.valueOf(i), strValueOf, str);
            if (this.s) {
                this.i.a(i2, (aq) null);
            }
        }
        if (this.q + this.r > 0) {
            this.i.a(this.i.a(this.t) + this.q + this.r, this.t);
        }
        if (this.k != null) {
            this.k.a(z);
        }
        if (this.l != null) {
            this.l.a(z);
        }
    }

    private static boolean a(aq aqVar, com.tencent.bugly.crashreport.common.info.a aVar, com.tencent.bugly.crashreport.common.strategy.a aVar2) throws NumberFormatException {
        if (aqVar == null) {
            x.d("resp == null!", new Object[0]);
            return false;
        }
        if (aqVar.a != 0) {
            x.e("resp result error %d", Byte.valueOf(aqVar.a));
            return false;
        }
        try {
            if (!z.a(aqVar.d) && !com.tencent.bugly.crashreport.common.info.a.b().i().equals(aqVar.d)) {
                p.a().a(com.tencent.bugly.crashreport.common.strategy.a.a, "gateway", aqVar.d.getBytes("UTF-8"), (o) null, true);
                aVar.d(aqVar.d);
            }
            if (!z.a(aqVar.f) && !com.tencent.bugly.crashreport.common.info.a.b().j().equals(aqVar.f)) {
                p.a().a(com.tencent.bugly.crashreport.common.strategy.a.a, "device", aqVar.f.getBytes("UTF-8"), (o) null, true);
                aVar.e(aqVar.f);
            }
        } catch (Throwable th) {
            x.a(th);
        }
        aVar.j = aqVar.e;
        if (aqVar.b == 510) {
            if (aqVar.c == null) {
                x.e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(aqVar.b));
                return false;
            }
            as asVar = (as) a.a(aqVar.c, as.class);
            if (asVar == null) {
                x.e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(aqVar.b));
                return false;
            }
            aVar2.a(asVar);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x026e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0203 A[SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        byte[] bArr;
        String strA;
        byte[] bArrA;
        Map<String, String> map;
        String str;
        boolean z;
        long j;
        String str2 = "Bugly-Version";
        try {
            this.p = 0;
            this.q = 0L;
            this.r = 0L;
            byte[] bArr2 = this.e;
            if (com.tencent.bugly.crashreport.common.info.b.c(this.c) == null) {
                a(null, false, 0, "network is not available", 0);
                return;
            }
            if (bArr2 != null && bArr2.length != 0) {
                int i = 1;
                x.c("[Upload] Run upload task with cmd: %d", Integer.valueOf(this.d));
                if (this.c != null && this.f != null && this.g != null && this.h != null) {
                    StrategyBean strategyBeanC = this.g.c();
                    if (strategyBeanC == null) {
                        a(null, false, 0, "illegal local strategy", 0);
                        return;
                    }
                    HashMap map2 = new HashMap();
                    map2.put("prodId", this.f.f());
                    map2.put("bundleId", this.f.c);
                    map2.put("appVer", this.f.k);
                    if (this.o != null) {
                        map2.putAll(this.o);
                    }
                    int i2 = 2;
                    if (!this.s) {
                        bArr = bArr2;
                    } else {
                        map2.put("cmd", Integer.toString(this.d));
                        map2.put("platformId", Byte.toString((byte) 1));
                        map2.put("sdkVer", this.f.f);
                        map2.put("strategylastUpdateTime", Long.toString(strategyBeanC.p));
                        if (!this.i.a(map2)) {
                            a(null, false, 0, "failed to add security info to HTTP headers", 0);
                            return;
                        }
                        byte[] bArrA2 = z.a(bArr2, 2);
                        if (bArrA2 == null) {
                            a(null, false, 0, "failed to zip request body", 0);
                            return;
                        }
                        byte[] bArrA3 = this.i.a(bArrA2);
                        if (bArrA3 != null) {
                            bArr = bArrA3;
                        } else {
                            a(null, false, 0, "failed to encrypt request body", 0);
                            return;
                        }
                    }
                    this.i.a(this.j, System.currentTimeMillis());
                    t tVar = this.k;
                    t tVar2 = this.l;
                    String str3 = this.m;
                    int i3 = 0;
                    int i4 = -1;
                    int i5 = 0;
                    while (true) {
                        int i6 = i5 + 1;
                        if (i5 < this.a) {
                            if (i6 > i) {
                                Object[] objArr = new Object[i];
                                objArr[0] = Integer.valueOf(i6);
                                x.d("[Upload] Failed to upload last time, wait and try(%d) again.", objArr);
                                z.b(this.b);
                                if (i6 == this.a) {
                                    Object[] objArr2 = new Object[i];
                                    objArr2[0] = this.n;
                                    x.d("[Upload] Use the back-up url at the last time: %s", objArr2);
                                    str3 = this.n;
                                }
                            }
                            Object[] objArr3 = new Object[i];
                            objArr3[0] = Integer.valueOf(bArr.length);
                            x.c("[Upload] Send %d bytes", objArr3);
                            if (!this.s) {
                                strA = str3;
                            } else {
                                strA = a(str3);
                            }
                            Integer numValueOf = Integer.valueOf(this.d);
                            Integer numValueOf2 = Integer.valueOf(Process.myPid());
                            Integer numValueOf3 = Integer.valueOf(Process.myTid());
                            Object[] objArr4 = new Object[4];
                            objArr4[0] = strA;
                            objArr4[i] = numValueOf;
                            objArr4[i2] = numValueOf2;
                            objArr4[3] = numValueOf3;
                            x.c("[Upload] Upload to %s with cmd %d (pid=%d | tid=%d).", objArr4);
                            bArrA = this.h.a(strA, bArr, this, map2);
                            if (bArrA == null) {
                                Object[] objArr5 = new Object[i2];
                                objArr5[0] = Integer.valueOf(i);
                                objArr5[i] = "Failed to upload for no response!";
                                x.e("[Upload] Failed to upload(%d): %s", objArr5);
                                str3 = strA;
                                i5 = i6;
                                i3 = 1;
                            } else {
                                map = this.h.a;
                                if (!this.s) {
                                    break;
                                }
                                if (map == null || map.size() == 0) {
                                    str = str2;
                                    x.d("[Upload] Headers is empty.", new Object[0]);
                                    z = false;
                                    if (z) {
                                        Integer numValueOf4 = Integer.valueOf(Process.myPid());
                                        Integer numValueOf5 = Integer.valueOf(Process.myTid());
                                        Object[] objArr6 = new Object[2];
                                        objArr6[0] = numValueOf4;
                                        objArr6[i] = numValueOf5;
                                        x.c("[Upload] Headers from server is not valid, just try again (pid=%d | tid=%d).", objArr6);
                                        Object[] objArr7 = new Object[2];
                                        objArr7[0] = Integer.valueOf(i);
                                        objArr7[i] = "[Upload] Failed to upload for no status header.";
                                        x.e("[Upload] Failed to upload(%d): %s", objArr7);
                                        if (map != null) {
                                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                                String key = entry.getKey();
                                                String value = entry.getValue();
                                                Object[] objArr8 = new Object[2];
                                                objArr8[0] = key;
                                                objArr8[i] = value;
                                                x.c(String.format("[key]: %s, [value]: %s", objArr8), new Object[0]);
                                            }
                                        }
                                        x.c("[Upload] Failed to upload for no status header.", new Object[0]);
                                        str3 = strA;
                                        i5 = i6;
                                        str2 = str;
                                        i3 = 1;
                                        i2 = 2;
                                    } else {
                                        try {
                                            int i7 = Integer.parseInt(map.get(NotificationCompat.CATEGORY_STATUS));
                                            try {
                                                x.c("[Upload] Status from server is %d (pid=%d | tid=%d).", Integer.valueOf(i7), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                                                if (i7 == 0) {
                                                    i4 = i7;
                                                } else {
                                                    if (i7 == 2) {
                                                        if (this.q + this.r > 0) {
                                                            this.i.a(this.i.a(this.t) + this.q + this.r, this.t);
                                                        }
                                                        this.i.a(i7, (aq) null);
                                                        x.a("[Upload] Session ID is invalid, will try again immediately (pid=%d | tid=%d).", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                                                        this.i.a(this.j, this.d, this.e, this.m, this.n, this.k, this.a, this.b, true, this.o);
                                                        return;
                                                    }
                                                    a(null, false, 1, "status of server is " + i7, i7);
                                                    return;
                                                }
                                            } catch (Throwable th) {
                                                j = 0;
                                                i4 = i7;
                                                x.e("[Upload] Failed to upload(%d): %s", 1, "[Upload] Failed to upload for format of status header is invalid: " + Integer.toString(i4));
                                                str3 = strA;
                                                i5 = i6;
                                                str2 = str;
                                                i = 1;
                                                i3 = 1;
                                                i2 = 2;
                                            }
                                        } catch (Throwable th2) {
                                            j = 0;
                                        }
                                    }
                                } else {
                                    if (!map.containsKey(NotificationCompat.CATEGORY_STATUS)) {
                                        Object[] objArr9 = new Object[i];
                                        objArr9[0] = NotificationCompat.CATEGORY_STATUS;
                                        x.d("[Upload] Headers does not contain %s", objArr9);
                                    } else if (map.containsKey(str2)) {
                                        String str4 = map.get(str2);
                                        if (str4.contains("bugly")) {
                                            str = str2;
                                            Object[] objArr10 = new Object[i];
                                            objArr10[0] = str4;
                                            x.c("[Upload] Bugly version from headers is: %s", objArr10);
                                            z = true;
                                            if (z) {
                                            }
                                        } else {
                                            str = str2;
                                            Object[] objArr11 = new Object[i];
                                            objArr11[0] = str4;
                                            x.d("[Upload] Bugly version is not valid: %s", objArr11);
                                            z = false;
                                            if (z) {
                                            }
                                        }
                                    } else {
                                        Object[] objArr12 = new Object[i];
                                        objArr12[0] = str2;
                                        x.d("[Upload] Headers does not contain %s", objArr12);
                                    }
                                    str = str2;
                                    z = false;
                                    if (z) {
                                    }
                                }
                            }
                        } else {
                            a(null, false, i3, "failed after many attempts", 0);
                            return;
                        }
                    }
                    x.c("[Upload] Received %d bytes", Integer.valueOf(bArrA.length));
                    if (this.s) {
                        if (bArrA.length == 0) {
                            for (Map.Entry<String, String> entry2 : map.entrySet()) {
                                x.c("[Upload] HTTP headers from server: key = %s, value = %s", entry2.getKey(), entry2.getValue());
                            }
                            a(null, false, 1, "response data from server is empty", 0);
                            return;
                        }
                        byte[] bArrB = this.i.b(bArrA);
                        if (bArrB == null) {
                            a(null, false, 1, "failed to decrypt response from server", 0);
                            return;
                        }
                        bArrA = z.b(bArrB, 2);
                        if (bArrA == null) {
                            a(null, false, 1, "failed unzip(Gzip) response from server", 0);
                            return;
                        }
                    }
                    aq aqVarA = a.a(bArrA, this.s);
                    if (aqVarA == null) {
                        a(null, false, 1, "failed to decode response package", 0);
                        return;
                    }
                    if (this.s) {
                        this.i.a(i4, aqVarA);
                    }
                    x.c("[Upload] Response cmd is: %d, length of sBuffer is: %d", Integer.valueOf(aqVarA.b), Integer.valueOf(aqVarA.c == null ? 0 : aqVarA.c.length));
                    if (!a(aqVarA, this.f, this.g)) {
                        a(aqVarA, false, 2, "failed to process response package", 0);
                        return;
                    } else {
                        a(aqVarA, true, 2, "successfully uploaded", 0);
                        return;
                    }
                }
                a(null, false, 0, "illegal access error", 0);
                return;
            }
            a(null, false, 0, "request package is empty!", 0);
        } catch (Throwable th3) {
            if (!x.a(th3)) {
                th3.printStackTrace();
            }
        }
    }

    public final void a(long j) {
        this.p++;
        this.q += j;
    }

    public final void b(long j) {
        this.r += j;
    }

    private static String a(String str) {
        if (z.a(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", str, UUID.randomUUID().toString());
        } catch (Throwable th) {
            x.a(th);
            return str;
        }
    }
}