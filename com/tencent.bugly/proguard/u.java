package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import javax.crypto.NoSuchPaddingException;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class u {
    private static u b = null;
    public boolean a;
    private final Context d;
    private long f;
    private long g;
    private String k;
    private Map<Integer, Long> e = new HashMap();
    private LinkedBlockingQueue<Runnable> h = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Runnable> i = new LinkedBlockingQueue<>();
    private final Object j = new Object();
    private byte[] l = null;
    private long m = 0;
    private byte[] n = null;
    private long o = 0;
    private String p = null;
    private long q = 0;
    private final Object r = new Object();
    private boolean s = false;
    private final Object t = new Object();
    private int u = 0;
    private final p c = p.a();

    static /* synthetic */ boolean a(u uVar, boolean z) {
        uVar.s = false;
        return false;
    }

    static /* synthetic */ int b(u uVar) {
        int i = uVar.u - 1;
        uVar.u = i;
        return i;
    }

    private u(Context context) throws ClassNotFoundException {
        this.k = null;
        this.a = true;
        this.d = context;
        try {
            Class.forName("android.util.Base64");
        } catch (ClassNotFoundException e) {
            x.a("[UploadManager] Error: Can not find Base64 class, will not use stronger security way to upload", new Object[0]);
            this.a = false;
        }
        if (this.a) {
            StringBuilder sb = new StringBuilder();
            sb.append("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDP9x32s5pPtZBXzJBz2GWM/sbTvVO2+RvW0PH01IdaBxc/").append("fB6fbHZocC9T3nl1+J5eAFjIRVuV8vHDky7Qo82Mnh0PVvcZIEQvMMVKU8dsMQopxgsOs2gkSHJwgWdinKNS8CmWobo6pFwPUW11lMv714jAUZRq2GBOqiO2vQI6iwIDAQAB");
            this.k = sb.toString();
        }
    }

    public static synchronized u a(Context context) {
        if (b == null) {
            b = new u(context);
        }
        return b;
    }

    public static synchronized u a() {
        return b;
    }

    public final void a(int i, ap apVar, String str, String str2, t tVar, long j, boolean z) {
        try {
            a(new v(this.d, i, apVar.g, com.tencent.bugly.proguard.a.a((Object) apVar), str, str2, tVar, this.a, z), true, true, j);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    public final void a(int i, int i2, byte[] bArr, String str, String str2, t tVar, int i3, int i4, boolean z, Map<String, String> map) {
        try {
            a(new v(this.d, i, i2, bArr, str, str2, tVar, this.a, i3, i4, false, map), z, false, 0L);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    public final void a(int i, ap apVar, String str, String str2, t tVar, boolean z) {
        a(i, apVar.g, com.tencent.bugly.proguard.a.a((Object) apVar), str, str2, tVar, 0, 0, z, null);
    }

    public final long a(boolean z) {
        long jC;
        long jB = z.b();
        int i = z ? 5 : 3;
        List<r> listA = this.c.a(i);
        if (listA != null && listA.size() > 0) {
            jC = 0;
            try {
                r rVar = listA.get(0);
                if (rVar.e >= jB) {
                    jC = z.c(rVar.g);
                    if (i == 3) {
                        this.f = jC;
                    } else {
                        this.g = jC;
                    }
                    listA.remove(rVar);
                }
            } catch (Throwable th) {
                x.a(th);
            }
            if (listA.size() > 0) {
                this.c.a(listA);
            }
        } else {
            jC = z ? this.g : this.f;
        }
        x.c("[UploadManager] Local network consume: %d KB", Long.valueOf(jC / 1024));
        return jC;
    }

    protected final synchronized void a(long j, boolean z) {
        int i = z ? 5 : 3;
        r rVar = new r();
        rVar.b = i;
        rVar.e = z.b();
        rVar.c = "";
        rVar.d = "";
        rVar.g = z.c(j);
        this.c.b(i);
        this.c.a(rVar);
        if (z) {
            this.g = j;
        } else {
            this.f = j;
        }
        x.c("[UploadManager] Network total consume: %d KB", Long.valueOf(j / 1024));
    }

    public final synchronized void a(int i, long j) {
        if (i >= 0) {
            this.e.put(Integer.valueOf(i), Long.valueOf(j));
            r rVar = new r();
            rVar.b = i;
            rVar.e = j;
            rVar.c = "";
            rVar.d = "";
            rVar.g = new byte[0];
            this.c.b(i);
            this.c.a(rVar);
            x.c("[UploadManager] Uploading(ID:%d) time: %s", Integer.valueOf(i), z.a(j));
            return;
        }
        x.e("[UploadManager] Unknown uploading ID: %d", Integer.valueOf(i));
    }

    public final synchronized long a(int i) {
        long j = 0;
        if (i >= 0) {
            Long l = this.e.get(Integer.valueOf(i));
            if (l != null) {
                return l.longValue();
            }
            List<r> listA = this.c.a(i);
            if (listA != null && listA.size() > 0) {
                if (listA.size() > 1) {
                    for (r rVar : listA) {
                        if (rVar.e > j) {
                            j = rVar.e;
                        }
                    }
                    this.c.b(i);
                } else {
                    try {
                        j = listA.get(0).e;
                    } catch (Throwable th) {
                        x.a(th);
                    }
                }
            }
        } else {
            x.e("[UploadManager] Unknown upload ID: %d", Integer.valueOf(i));
        }
        return j;
    }

    public final boolean b(int i) {
        if (com.tencent.bugly.b.c) {
            x.c("Uploading frequency will not be checked if SDK is in debug mode.", new Object[0]);
            return true;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - a(i);
        x.c("[UploadManager] Time interval is %d seconds since last uploading(ID: %d).", Long.valueOf(jCurrentTimeMillis / 1000), Integer.valueOf(i));
        if (jCurrentTimeMillis >= 30000) {
            return true;
        }
        x.a("[UploadManager] Data only be uploaded once in %d seconds.", 30L);
        return false;
    }

    private static boolean c() {
        x.c("[UploadManager] Drop security info of database (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            p pVarA = p.a();
            if (pVarA == null) {
                x.d("[UploadManager] Failed to get Database", new Object[0]);
                return false;
            }
            return pVarA.a(555, "security_info", (o) null, true);
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    private boolean d() {
        x.c("[UploadManager] Record security info to database (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            p pVarA = p.a();
            if (pVarA == null) {
                x.d("[UploadManager] Failed to get database", new Object[0]);
                return false;
            }
            StringBuilder sb = new StringBuilder();
            if (this.n != null) {
                sb.append(Base64.encodeToString(this.n, 0));
                sb.append("#");
                if (this.o != 0) {
                    sb.append(Long.toString(this.o));
                } else {
                    sb.append("null");
                }
                sb.append("#");
                if (this.p != null) {
                    sb.append(this.p);
                } else {
                    sb.append("null");
                }
                sb.append("#");
                if (this.q != 0) {
                    sb.append(Long.toString(this.q));
                } else {
                    sb.append("null");
                }
                pVarA.a(555, "security_info", sb.toString().getBytes(), (o) null, true);
                return true;
            }
            x.c("[UploadManager] AES key is null, will not record", new Object[0]);
            return false;
        } catch (Throwable th) {
            x.a(th);
            c();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e() {
        boolean z;
        x.c("[UploadManager] Load security info from database (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            p pVarA = p.a();
            if (pVarA == null) {
                x.d("[UploadManager] Failed to get database", new Object[0]);
                return false;
            }
            Map<String, byte[]> mapA = pVarA.a(555, (o) null, true);
            if (mapA != null && mapA.containsKey("security_info")) {
                String str = new String(mapA.get("security_info"));
                String[] strArrSplit = str.split("#");
                if (strArrSplit.length == 4) {
                    if (!strArrSplit[0].isEmpty() && !strArrSplit[0].equals("null")) {
                        try {
                            this.n = Base64.decode(strArrSplit[0], 0);
                            z = false;
                        } catch (Throwable th) {
                            x.a(th);
                            z = true;
                        }
                        if (!z) {
                            this.o = Long.parseLong(strArrSplit[1]);
                        }
                        if (!z) {
                            this.p = strArrSplit[2];
                        }
                        if (!z) {
                            this.q = Long.parseLong(strArrSplit[3]);
                        }
                    } else {
                        z = false;
                        if (!z && !strArrSplit[1].isEmpty() && !strArrSplit[1].equals("null")) {
                            try {
                                this.o = Long.parseLong(strArrSplit[1]);
                            } catch (Throwable th2) {
                                x.a(th2);
                                z = true;
                            }
                        }
                        if (!z && !strArrSplit[2].isEmpty() && !strArrSplit[2].equals("null")) {
                            this.p = strArrSplit[2];
                        }
                        if (!z && !strArrSplit[3].isEmpty() && !strArrSplit[3].equals("null")) {
                            try {
                                this.q = Long.parseLong(strArrSplit[3]);
                            } catch (Throwable th3) {
                                x.a(th3);
                                z = true;
                            }
                        }
                    }
                } else {
                    x.a("SecurityInfo = %s, Strings.length = %d", str, Integer.valueOf(strArrSplit.length));
                    z = true;
                }
                if (z) {
                    c();
                }
            }
            return true;
        } catch (Throwable th4) {
            x.a(th4);
            return false;
        }
    }

    protected final boolean b() {
        if (this.p == null || this.q == 0) {
            return false;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() + this.m;
        if (this.q >= jCurrentTimeMillis) {
            return true;
        }
        x.c("[UploadManager] Session ID expired time from server is: %d(%s), but now is: %d(%s)", Long.valueOf(this.q), new Date(this.q).toString(), Long.valueOf(jCurrentTimeMillis), new Date(jCurrentTimeMillis).toString());
        return false;
    }

    public final void b(boolean z) {
        synchronized (this.r) {
            x.c("[UploadManager] Clear security context (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            this.n = null;
            this.p = null;
            this.q = 0L;
        }
        if (z) {
            c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006e A[Catch: all -> 0x015c, TRY_LEAVE, TryCatch #4 {, blocks: (B:8:0x001c, B:11:0x0049, B:12:0x0050, B:22:0x0064, B:27:0x006e, B:33:0x008f, B:32:0x0082, B:36:0x0095, B:42:0x00b6, B:41:0x00a9, B:43:0x00b9, B:17:0x0059, B:19:0x005d, B:29:0x0078, B:38:0x009f), top: B:94:0x001c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0095 A[Catch: all -> 0x015c, TRY_LEAVE, TryCatch #4 {, blocks: (B:8:0x001c, B:11:0x0049, B:12:0x0050, B:22:0x0064, B:27:0x006e, B:33:0x008f, B:32:0x0082, B:36:0x0095, B:42:0x00b6, B:41:0x00a9, B:43:0x00b9, B:17:0x0059, B:19:0x005d, B:29:0x0078, B:38:0x009f), top: B:94:0x001c, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0153  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void c(int i) {
        int i2;
        int i3;
        int i4;
        if (i < 0) {
            x.a("[UploadManager] Number of task to execute should >= 0", new Object[0]);
            return;
        }
        w wVarA = w.a();
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        final LinkedBlockingQueue linkedBlockingQueue2 = new LinkedBlockingQueue();
        synchronized (this.j) {
            x.c("[UploadManager] Try to poll all upload task need and put them into temp queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            int size = this.h.size();
            final int size2 = this.i.size();
            if (size == 0 && size2 == 0) {
                x.c("[UploadManager] There is no upload task in queue.", new Object[0]);
                return;
            }
            if (i != 0) {
                if (i < size) {
                    size2 = 0;
                } else if (i < size + size2) {
                    size2 = i - size;
                    i = size;
                }
                if (wVarA != null) {
                    size2 = 0;
                    while (i2 < i) {
                    }
                    while (i3 < size2) {
                    }
                    if (i > 0) {
                    }
                    while (i4 < i) {
                    }
                    if (size2 > 0) {
                    }
                    if (wVarA == null) {
                    }
                }
            } else {
                i = size;
                if (wVarA != null || !wVarA.c()) {
                    size2 = 0;
                }
                for (i2 = 0; i2 < i; i2++) {
                    Runnable runnablePeek = this.h.peek();
                    if (runnablePeek == null) {
                        break;
                    }
                    try {
                        linkedBlockingQueue.put(runnablePeek);
                        this.h.poll();
                    } catch (Throwable th) {
                        x.e("[UploadManager] Failed to add upload task to temp urgent queue: %s", th.getMessage());
                    }
                }
                for (i3 = 0; i3 < size2; i3++) {
                    Runnable runnablePeek2 = this.i.peek();
                    if (runnablePeek2 == null) {
                        break;
                    }
                    try {
                        linkedBlockingQueue2.put(runnablePeek2);
                        this.i.poll();
                    } catch (Throwable th2) {
                        x.e("[UploadManager] Failed to add upload task to temp urgent queue: %s", th2.getMessage());
                    }
                }
                if (i > 0) {
                    x.c("[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(i), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                }
                for (i4 = 0; i4 < i; i4++) {
                    final Runnable runnable = (Runnable) linkedBlockingQueue.poll();
                    if (runnable == null) {
                        break;
                    }
                    synchronized (this.j) {
                        if (this.u >= 2 && wVarA != null) {
                            wVarA.a(runnable);
                        } else {
                            x.a("[UploadManager] Create and start a new thread to execute a upload task: %s", "BUGLY_ASYNC_UPLOAD");
                            if (z.a(new Runnable() { // from class: com.tencent.bugly.proguard.u.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    runnable.run();
                                    synchronized (u.this.j) {
                                        u.b(u.this);
                                    }
                                }
                            }, "BUGLY_ASYNC_UPLOAD") != null) {
                                synchronized (this.j) {
                                    this.u++;
                                }
                            } else {
                                x.d("[UploadManager] Failed to start a thread to execute asynchronous upload task, will try again next time.", new Object[0]);
                                a(runnable, true);
                            }
                        }
                    }
                }
                if (size2 > 0) {
                    x.c("[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(size2), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                }
                if (wVarA == null) {
                    wVarA.a(new Runnable(this) { // from class: com.tencent.bugly.proguard.u.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Runnable runnable2;
                            for (int i5 = 0; i5 < size2 && (runnable2 = (Runnable) linkedBlockingQueue2.poll()) != null; i5++) {
                                runnable2.run();
                            }
                        }
                    });
                    return;
                }
                return;
            }
        }
    }

    private boolean a(Runnable runnable, boolean z) {
        if (runnable == null) {
            x.a("[UploadManager] Upload task should not be null", new Object[0]);
            return false;
        }
        try {
            x.c("[UploadManager] Add upload task to queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            synchronized (this.j) {
                if (z) {
                    this.h.put(runnable);
                } else {
                    this.i.put(runnable);
                }
            }
            return true;
        } catch (Throwable th) {
            x.e("[UploadManager] Failed to add upload task to queue: %s", th.getMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Runnable runnable, long j) {
        if (runnable == null) {
            x.d("[UploadManager] Upload task should not be null", new Object[0]);
            return;
        }
        x.c("[UploadManager] Execute synchronized upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        Thread threadA = z.a(runnable, "BUGLY_SYNC_UPLOAD");
        if (threadA == null) {
            x.e("[UploadManager] Failed to start a thread to execute synchronized upload task, add it to queue.", new Object[0]);
            a(runnable, true);
            return;
        }
        try {
            threadA.join(j);
        } catch (Throwable th) {
            x.e("[UploadManager] Failed to join upload synchronized task with message: %s. Add it to queue.", th.getMessage());
            a(runnable, true);
            c(0);
        }
    }

    private void a(Runnable runnable, boolean z, boolean z2, long j) {
        if (runnable == null) {
            x.d("[UploadManager] Upload task should not be null", new Object[0]);
        }
        x.c("[UploadManager] Add upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (this.p != null) {
            if (b()) {
                x.c("[UploadManager] Sucessfully got session ID, try to execute upload task now (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                if (z2) {
                    a(runnable, j);
                    return;
                } else {
                    a(runnable, z);
                    c(0);
                    return;
                }
            }
            x.a("[UploadManager] Session ID is expired, drop it (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            b(false);
        }
        synchronized (this.t) {
            if (this.s) {
                a(runnable, z);
                return;
            }
            this.s = true;
            x.c("[UploadManager] Initialize security context now (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            if (z2) {
                a(new a(this.d, runnable, j), 0L);
                return;
            }
            a(runnable, z);
            a aVar = new a(this.d);
            x.a("[UploadManager] Create and start a new thread to execute a task of initializing security context: %s", "BUGLY_ASYNC_UPLOAD");
            if (z.a(aVar, "BUGLY_ASYNC_UPLOAD") == null) {
                x.d("[UploadManager] Failed to start a thread to execute task of initializing security context, try to post it into thread pool.", new Object[0]);
                w wVarA = w.a();
                if (wVarA != null) {
                    wVarA.a(aVar);
                    return;
                }
                x.e("[UploadManager] Asynchronous thread pool is unavailable now, try next time.", new Object[0]);
                synchronized (this.t) {
                    this.s = false;
                }
            }
        }
    }

    public final void a(int i, aq aqVar) {
        if (!this.a) {
            return;
        }
        boolean z = true;
        if (i == 2) {
            x.c("[UploadManager] Session ID is invalid, will clear security context (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            b(true);
        } else {
            synchronized (this.t) {
                if (!this.s) {
                    return;
                }
                if (aqVar != null) {
                    x.c("[UploadManager] Record security context (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                    try {
                        Map<String, String> map = aqVar.g;
                        if (map != null && map.containsKey("S1") && map.containsKey("S2")) {
                            this.m = aqVar.e - System.currentTimeMillis();
                            x.c("[UploadManager] Time lag of server is: %d", Long.valueOf(this.m));
                            this.p = map.get("S1");
                            x.c("[UploadManager] Session ID from server is: %s", this.p);
                            if (this.p.length() > 0) {
                                try {
                                    this.q = Long.parseLong(map.get("S2"));
                                    x.c("[UploadManager] Session expired time from server is: %d(%s)", Long.valueOf(this.q), new Date(this.q).toString());
                                    if (this.q < 1000) {
                                        x.d("[UploadManager] Session expired time from server is less than 1 second, will set to default value", new Object[0]);
                                        this.q = 259200000L;
                                    }
                                } catch (NumberFormatException e) {
                                    x.d("[UploadManager] Session expired time is invalid, will set to default value", new Object[0]);
                                    this.q = 259200000L;
                                }
                                if (d()) {
                                    z = false;
                                } else {
                                    x.c("[UploadManager] Failed to record database", new Object[0]);
                                }
                                c(0);
                            } else {
                                x.c("[UploadManager] Session ID from server is invalid, try next time", new Object[0]);
                            }
                        }
                    } catch (Throwable th) {
                        x.a(th);
                    }
                    if (z) {
                        b(false);
                    }
                } else {
                    x.c("[UploadManager] Fail to init security context and clear local info (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                    b(false);
                }
            }
        }
        synchronized (this.t) {
            if (this.s) {
                this.s = false;
                z.b(this.d, "security_info");
            }
        }
    }

    /* compiled from: BUGLY */
    class a implements Runnable {
        private final Context a;
        private final Runnable b;
        private final long c;

        public a(Context context) {
            this.a = context;
            this.b = null;
            this.c = 0L;
        }

        public a(Context context, Runnable runnable, long j) {
            this.a = context;
            this.b = runnable;
            this.c = j;
        }

        @Override // java.lang.Runnable
        public final void run() throws InterruptedException {
            if (z.a(this.a, "security_info", 30000L)) {
                if (!u.this.e()) {
                    x.d("[UploadManager] Failed to load security info from database", new Object[0]);
                    u.this.b(false);
                }
                if (u.this.p != null) {
                    if (u.this.b()) {
                        x.c("[UploadManager] Sucessfully got session ID, try to execute upload tasks now (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                        if (this.b != null) {
                            u.this.a(this.b, this.c);
                        }
                        u.this.c(0);
                        z.b(this.a, "security_info");
                        synchronized (u.this.t) {
                            u.a(u.this, false);
                        }
                        return;
                    }
                    x.a("[UploadManager] Session ID is expired, drop it.", new Object[0]);
                    u.this.b(true);
                }
                byte[] bArrA = z.a(128);
                if (bArrA != null && (bArrA.length << 3) == 128) {
                    u.this.n = bArrA;
                    x.c("[UploadManager] Execute one upload task for requesting session ID (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                    if (this.b != null) {
                        u.this.a(this.b, this.c);
                        return;
                    } else {
                        u.this.c(1);
                        return;
                    }
                }
                x.d("[UploadManager] Failed to create AES key (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                u.this.b(false);
                z.b(this.a, "security_info");
                synchronized (u.this.t) {
                    u.a(u.this, false);
                }
                return;
            }
            x.c("[UploadManager] Sleep %d try to lock security file again (pid=%d | tid=%d)", 5000, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            z.b(5000L);
            if (z.a(this, "BUGLY_ASYNC_UPLOAD") == null) {
                x.d("[UploadManager] Failed to start a thread to execute task of initializing security context, try to post it into thread pool.", new Object[0]);
                w wVarA = w.a();
                if (wVarA != null) {
                    wVarA.a(this);
                } else {
                    x.e("[UploadManager] Asynchronous thread pool is unavailable now, try next time.", new Object[0]);
                }
            }
        }
    }

    public final byte[] a(byte[] bArr) {
        if (this.n == null || (this.n.length << 3) != 128) {
            x.d("[UploadManager] AES key is invalid (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return null;
        }
        return z.a(1, bArr, this.n);
    }

    public final byte[] b(byte[] bArr) {
        if (this.n == null || (this.n.length << 3) != 128) {
            x.d("[UploadManager] AES key is invalid (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return null;
        }
        return z.a(2, bArr, this.n);
    }

    public final boolean a(Map<String, String> map) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (map == null) {
            return false;
        }
        x.c("[UploadManager] Integrate security to HTTP headers (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (this.p != null) {
            map.put("secureSessionId", this.p);
            return true;
        }
        if (this.n == null || (this.n.length << 3) != 128) {
            x.d("[UploadManager] AES key is invalid", new Object[0]);
            return false;
        }
        if (this.l == null) {
            this.l = Base64.decode(this.k, 0);
            if (this.l == null) {
                x.d("[UploadManager] Failed to decode RSA public key", new Object[0]);
                return false;
            }
        }
        byte[] bArrB = z.b(1, this.n, this.l);
        if (bArrB == null) {
            x.d("[UploadManager] Failed to encrypt AES key", new Object[0]);
            return false;
        }
        String strEncodeToString = Base64.encodeToString(bArrB, 0);
        if (TextUtils.isEmpty(strEncodeToString)) {
            x.e("[UploadManager] Failed to encode AES key", new Object[0]);
            return false;
        }
        map.put("raKey", strEncodeToString);
        return true;
    }
}