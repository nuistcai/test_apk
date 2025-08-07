package com.tencent.bugly.crashreport.crash.anr;

import android.app.ActivityManager;
import android.content.Context;
import android.os.FileObserver;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.anr.TraceFileHelper;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.aa;
import com.tencent.bugly.proguard.ab;
import com.tencent.bugly.proguard.ac;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import com.tencent.bugly.proguard.z;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class b implements ac {
    private final Context c;
    private final com.tencent.bugly.crashreport.common.info.a d;
    private final w e;
    private final com.tencent.bugly.crashreport.common.strategy.a f;
    private final String g;
    private final com.tencent.bugly.crashreport.crash.b h;
    private FileObserver i;
    private ab k;
    private int l;
    private AtomicInteger a = new AtomicInteger(0);
    private long b = -1;
    private boolean j = true;
    private ActivityManager.ProcessErrorStateInfo m = new ActivityManager.ProcessErrorStateInfo();

    public b(Context context, com.tencent.bugly.crashreport.common.strategy.a aVar, com.tencent.bugly.crashreport.common.info.a aVar2, w wVar, com.tencent.bugly.crashreport.crash.b bVar) {
        this.c = z.a(context);
        this.g = context.getDir("bugly", 0).getAbsolutePath();
        this.d = aVar2;
        this.e = wVar;
        this.f = aVar;
        this.h = bVar;
    }

    private ActivityManager.ProcessErrorStateInfo a(Context context, long j) {
        try {
            x.c("to find!", new Object[0]);
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int i = 0;
            while (true) {
                x.c("waiting!", new Object[0]);
                List<ActivityManager.ProcessErrorStateInfo> processesInErrorState = activityManager.getProcessesInErrorState();
                if (processesInErrorState != null) {
                    for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo : processesInErrorState) {
                        if (processErrorStateInfo.condition == 2) {
                            x.c("found!", new Object[0]);
                            return processErrorStateInfo;
                        }
                    }
                }
                z.b(500L);
                int i2 = i + 1;
                if (i < 20) {
                    i = i2;
                } else {
                    x.c("end!", new Object[0]);
                    return null;
                }
            }
        } catch (Exception e) {
            x.b(e);
            return null;
        } catch (OutOfMemoryError e2) {
            this.m.pid = Process.myPid();
            this.m.shortMsg = "bugly sdk waitForAnrProcessStateChanged encount error:" + e2.getMessage();
            return this.m;
        }
    }

    private CrashDetailBean a(a aVar) {
        int iIndexOf;
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.C = com.tencent.bugly.crashreport.common.info.b.k();
            crashDetailBean.D = com.tencent.bugly.crashreport.common.info.b.i();
            crashDetailBean.E = com.tencent.bugly.crashreport.common.info.b.m();
            crashDetailBean.F = this.d.p();
            crashDetailBean.G = this.d.o();
            crashDetailBean.H = this.d.q();
            if (!com.tencent.bugly.crashreport.common.info.b.t()) {
                crashDetailBean.w = z.a(this.c, c.e, (String) null);
            }
            crashDetailBean.b = 3;
            crashDetailBean.e = this.d.h();
            crashDetailBean.f = this.d.k;
            crashDetailBean.g = this.d.w();
            crashDetailBean.m = this.d.g();
            crashDetailBean.n = "ANR_EXCEPTION";
            crashDetailBean.o = aVar.f;
            crashDetailBean.q = aVar.g;
            crashDetailBean.P = new HashMap();
            crashDetailBean.P.put("BUGLY_CR_01", aVar.e);
            if (crashDetailBean.q == null) {
                iIndexOf = -1;
            } else {
                iIndexOf = crashDetailBean.q.indexOf("\n");
            }
            crashDetailBean.p = iIndexOf > 0 ? crashDetailBean.q.substring(0, iIndexOf) : "GET_FAIL";
            crashDetailBean.r = aVar.c;
            if (crashDetailBean.q != null) {
                crashDetailBean.u = z.b(crashDetailBean.q.getBytes());
            }
            crashDetailBean.z = aVar.b;
            crashDetailBean.A = aVar.a;
            crashDetailBean.B = "main(1)";
            crashDetailBean.I = this.d.y();
            crashDetailBean.h = this.d.v();
            crashDetailBean.i = this.d.J();
            crashDetailBean.v = aVar.d;
            crashDetailBean.L = this.d.o;
            crashDetailBean.M = this.d.a;
            crashDetailBean.N = this.d.a();
            crashDetailBean.Q = this.d.H();
            crashDetailBean.R = this.d.I();
            crashDetailBean.S = this.d.B();
            crashDetailBean.T = this.d.G();
            if (!com.tencent.bugly.crashreport.common.info.b.t()) {
                this.h.d(crashDetailBean);
            }
            crashDetailBean.y = y.a();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return crashDetailBean;
    }

    private static boolean a(String str, String str2, String str3) throws Throwable {
        BufferedWriter bufferedWriter;
        TraceFileHelper.a targetDumpInfo = TraceFileHelper.readTargetDumpInfo(str3, str, true);
        if (targetDumpInfo == null || targetDumpInfo.d == null || targetDumpInfo.d.size() <= 0) {
            x.e("not found trace dump for %s", str3);
            return false;
        }
        File file = new File(str2);
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            if (!file.exists() || !file.canWrite()) {
                x.e("backup file create fail %s", str2);
                return false;
            }
            BufferedWriter bufferedWriter2 = null;
            try {
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                    try {
                        String[] strArr = targetDumpInfo.d.get("main");
                        int i = 3;
                        if (strArr != null && strArr.length >= 3) {
                            bufferedWriter.write("\"main\" tid=" + strArr[2] + " :\n" + strArr[0] + "\n" + strArr[1] + "\n\n");
                            bufferedWriter.flush();
                        }
                        for (Map.Entry<String, String[]> entry : targetDumpInfo.d.entrySet()) {
                            if (entry.getKey().equals("main")) {
                                i = 3;
                            } else {
                                if (entry.getValue() != null && entry.getValue().length >= i) {
                                    bufferedWriter.write("\"" + entry.getKey() + "\" tid=" + entry.getValue()[2] + " :\n" + entry.getValue()[0] + "\n" + entry.getValue()[1] + "\n\n");
                                    bufferedWriter.flush();
                                }
                                i = 3;
                            }
                        }
                        try {
                            bufferedWriter.close();
                        } catch (IOException e) {
                            if (!x.a(e)) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    } catch (IOException e2) {
                        e = e2;
                        bufferedWriter2 = bufferedWriter;
                        if (!x.a(e)) {
                            e.printStackTrace();
                        }
                        x.e("dump trace fail %s", e.getClass().getName() + ":" + e.getMessage());
                        if (bufferedWriter2 != null) {
                            try {
                                bufferedWriter2.close();
                            } catch (IOException e3) {
                                if (!x.a(e3)) {
                                    e3.printStackTrace();
                                }
                            }
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        Throwable th2 = th;
                        if (bufferedWriter == null) {
                            throw th2;
                        }
                        try {
                            bufferedWriter.close();
                            throw th2;
                        } catch (IOException e4) {
                            if (x.a(e4)) {
                                throw th2;
                            }
                            e4.printStackTrace();
                            throw th2;
                        }
                    }
                } catch (IOException e5) {
                    e = e5;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter = null;
            }
        } catch (Exception e6) {
            if (!x.a(e6)) {
                e6.printStackTrace();
            }
            x.e("backup file create error! %s  %s", e6.getClass().getName() + ":" + e6.getMessage(), str2);
            return false;
        }
    }

    public final boolean a() {
        return this.a.get() != 0;
    }

    private boolean a(Context context, String str, ActivityManager.ProcessErrorStateInfo processErrorStateInfo, long j, Map<String, String> map) {
        File file = new File(context.getFilesDir(), "bugly/bugly_trace_" + j + ".txt");
        a aVar = new a();
        aVar.c = j;
        aVar.d = file.getAbsolutePath();
        aVar.a = processErrorStateInfo != null ? processErrorStateInfo.processName : "";
        aVar.f = processErrorStateInfo != null ? processErrorStateInfo.shortMsg : "";
        aVar.e = processErrorStateInfo != null ? processErrorStateInfo.longMsg : "";
        aVar.b = map;
        Thread thread = Looper.getMainLooper().getThread();
        if (map != null) {
            Iterator<String> it = map.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String next = it.next();
                if (next.startsWith(thread.getName())) {
                    aVar.g = map.get(next);
                    break;
                }
            }
        }
        if (TextUtils.isEmpty(aVar.g)) {
            aVar.g = "main stack is null , some error may be encountered.";
        }
        x.c("anr tm:%d\ntr:%s\nproc:%s\nmain stack:%s\nsMsg:%s\n lMsg:%s\n threads:%d", Long.valueOf(aVar.c), aVar.d, aVar.a, aVar.g, aVar.f, aVar.e, Integer.valueOf(aVar.b == null ? 0 : aVar.b.size()));
        if (!this.f.c().j) {
            x.d("ANR Report is closed! print local for helpful!", new Object[0]);
            com.tencent.bugly.crashreport.crash.b.a("ANR", z.a(), aVar.a, "main", aVar.g, null);
            return false;
        }
        x.a("found visiable anr , start to upload!", new Object[0]);
        CrashDetailBean crashDetailBeanA = a(aVar);
        if (crashDetailBeanA == null) {
            x.e("pack anr fail!", new Object[0]);
            return false;
        }
        c.a().a(crashDetailBeanA);
        if (crashDetailBeanA.a >= 0) {
            x.a("backup anr record success!", new Object[0]);
        } else {
            x.d("backup anr record fail!", new Object[0]);
        }
        if (str != null && new File(str).exists()) {
            this.a.set(3);
            if (a(str, aVar.d, aVar.a)) {
                x.a("backup trace success", new Object[0]);
            }
        }
        com.tencent.bugly.crashreport.crash.b.a("ANR", z.a(), aVar.a, "main", aVar.g, crashDetailBeanA);
        if (!this.h.a(crashDetailBeanA)) {
            this.h.a(crashDetailBeanA, 3000L, true);
        }
        this.h.c(crashDetailBeanA);
        return true;
    }

    public final void a(String str) {
        long j;
        long jCurrentTimeMillis;
        synchronized (this) {
            if (this.a.get() != 0) {
                x.c("trace started return ", new Object[0]);
                return;
            }
            this.a.set(1);
            try {
                x.c("read trace first dump for create time!", new Object[0]);
                TraceFileHelper.a firstDumpInfo = TraceFileHelper.readFirstDumpInfo(str, false);
                if (firstDumpInfo == null) {
                    j = -1;
                } else {
                    j = firstDumpInfo.c;
                }
                if (j != -1) {
                    jCurrentTimeMillis = j;
                } else {
                    x.d("trace dump fail could not get time!", new Object[0]);
                    jCurrentTimeMillis = System.currentTimeMillis();
                }
            } finally {
                try {
                } finally {
                }
            }
            if (Math.abs(jCurrentTimeMillis - this.b) < 10000) {
                x.d("should not process ANR too Fre in %d", 10000);
            } else {
                this.b = jCurrentTimeMillis;
                this.a.set(1);
                try {
                    Map<String, String> mapA = z.a(c.f, false);
                    if (mapA == null || mapA.size() <= 0) {
                        x.d("can't get all thread skip this anr", new Object[0]);
                    } else {
                        this.m = a(this.c, 10000L);
                        if (this.m == null) {
                            x.c("proc state is unvisiable!", new Object[0]);
                        } else if (this.m.pid != Process.myPid()) {
                            x.c("not mind proc!", this.m.processName);
                        } else {
                            x.a("found visiable anr , start to process!", new Object[0]);
                            a(this.c, str, this.m, jCurrentTimeMillis, mapA);
                        }
                    }
                } catch (Throwable th) {
                    x.a(th);
                    x.e("get all thread stack fail!", new Object[0]);
                }
            }
        }
    }

    private synchronized void f() {
        if (h()) {
            x.d("start when started!", new Object[0]);
            return;
        }
        this.i = new FileObserver("/data/anr/", 8) { // from class: com.tencent.bugly.crashreport.crash.anr.b.1
            @Override // android.os.FileObserver
            public final void onEvent(int i, String str) {
                if (str == null) {
                    return;
                }
                String str2 = "/data/anr/" + str;
                if (!str2.contains("trace")) {
                    x.d("not anr file %s", str2);
                } else {
                    b.this.a(str2);
                }
            }
        };
        try {
            this.i.startWatching();
            x.a("start anr monitor!", new Object[0]);
            this.e.a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.anr.b.2
                @Override // java.lang.Runnable
                public final void run() {
                    b.this.b();
                }
            });
        } catch (Throwable th) {
            this.i = null;
            x.d("start anr monitor failed!", new Object[0]);
            if (x.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private synchronized void g() {
        if (!h()) {
            x.d("close when closed!", new Object[0]);
            return;
        }
        try {
            this.i.stopWatching();
            this.i = null;
            x.d("close anr monitor!", new Object[0]);
        } catch (Throwable th) {
            x.d("stop anr monitor failed!", new Object[0]);
            if (x.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private synchronized boolean h() {
        return this.i != null;
    }

    private synchronized void b(boolean z) {
        if (z) {
            f();
        } else {
            g();
        }
    }

    private synchronized boolean i() {
        return this.j;
    }

    private synchronized void c(boolean z) {
        if (this.j != z) {
            x.a("user change anr %b", Boolean.valueOf(z));
            this.j = z;
        }
    }

    public final void a(boolean z) {
        c(z);
        boolean zI = i();
        com.tencent.bugly.crashreport.common.strategy.a aVarA = com.tencent.bugly.crashreport.common.strategy.a.a();
        if (aVarA != null) {
            zI = zI && aVarA.c().g;
        }
        if (zI != h()) {
            x.a("anr changed to %b", Boolean.valueOf(zI));
            b(zI);
        }
    }

    protected final void b() {
        int iIndexOf;
        long jB = z.b() - c.g;
        File file = new File(this.g);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                    int length = "bugly_trace_".length();
                    int i = 0;
                    for (File file2 : fileArrListFiles) {
                        String name = file2.getName();
                        if (name.startsWith("bugly_trace_")) {
                            try {
                                iIndexOf = name.indexOf(".txt");
                            } catch (Throwable th) {
                                x.c("Trace file that has invalid format: " + name, new Object[0]);
                            }
                            if (iIndexOf <= 0 || Long.parseLong(name.substring(length, iIndexOf)) < jB) {
                                if (file2.delete()) {
                                    i++;
                                }
                            }
                        }
                    }
                    x.c("Number of overdue trace files that has deleted: " + i, new Object[0]);
                }
            } catch (Throwable th2) {
                x.a(th2);
            }
        }
    }

    public final synchronized void c() {
        x.d("customer decides whether to open or close.", new Object[0]);
    }

    @Override // com.tencent.bugly.proguard.ac
    public final boolean a(aa aaVar) {
        boolean z;
        Map<String, String> mapA;
        HashMap map = new HashMap();
        if (!aaVar.e().equals(Looper.getMainLooper())) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            this.m = a(this.c, 10000L);
            if (this.m == null) {
                x.c("anr handler onThreadBlock proc state is unvisiable!", new Object[0]);
                return false;
            }
            if (this.m.pid != Process.myPid()) {
                x.c("onThreadBlock not mind proc!", this.m.processName);
                return false;
            }
            try {
                mapA = z.a(200000, false);
            } catch (Throwable th) {
                x.b(th);
                map.put("main", th.getMessage());
                mapA = map;
            }
            x.c("onThreadBlock found visiable anr , start to process!", new Object[0]);
            a(this.c, "", this.m, System.currentTimeMillis(), mapA);
        } else {
            x.c("anr handler onThreadBlock only care main thread ,current thread is: %s", aaVar.d());
        }
        return true;
    }

    public final boolean d() {
        if (this.k != null && this.k.isAlive()) {
            return false;
        }
        this.k = new ab();
        ab abVar = this.k;
        StringBuilder sb = new StringBuilder("Bugly-ThreadMonitor");
        int i = this.l;
        this.l = i + 1;
        abVar.setName(sb.append(i).toString());
        this.k.a();
        this.k.a(this);
        return this.k.d();
    }

    public final boolean e() {
        if (this.k == null) {
            return false;
        }
        boolean zC = this.k.c();
        this.k.b();
        this.k.b(this);
        this.k = null;
        return zC;
    }
}