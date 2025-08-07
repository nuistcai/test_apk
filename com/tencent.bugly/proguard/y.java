package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class y {
    private static SimpleDateFormat c;
    private static StringBuilder e;
    private static StringBuilder f;
    private static boolean g;
    private static a h;
    private static String i;
    private static String j;
    private static Context k;
    private static String l;
    private static boolean m;
    private static ExecutorService o;
    private static int p;
    private static boolean b = true;
    public static boolean a = true;
    private static int d = 30720;
    private static boolean n = false;
    private static final Object q = new Object();

    static {
        c = null;
        try {
            c = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
            x.b(th.getCause());
        }
    }

    public static synchronized void a(Context context) {
        if (m || context == null || !a) {
            return;
        }
        try {
            o = Executors.newSingleThreadExecutor();
            f = new StringBuilder(0);
            e = new StringBuilder(0);
            k = context;
            com.tencent.bugly.crashreport.common.info.a aVarA = com.tencent.bugly.crashreport.common.info.a.a(context);
            i = aVarA.d;
            aVarA.getClass();
            j = "";
            l = k.getFilesDir().getPath() + "/buglylog_" + i + "_" + j + ".txt";
            p = Process.myPid();
        } catch (Throwable th) {
        }
        m = true;
    }

    public static void a(int i2) {
        synchronized (q) {
            d = i2;
            if (i2 < 0) {
                d = 0;
            } else if (i2 > 30720) {
                d = 30720;
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (th == null) {
            return;
        }
        String message = th.getMessage();
        if (message == null) {
            message = "";
        }
        a(str, str2, message + '\n' + z.b(th));
    }

    public static synchronized void a(final String str, final String str2, final String str3) {
        if (m && a) {
            try {
                o.execute(new Runnable() { // from class: com.tencent.bugly.proguard.y.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        y.c(str, str2, str3);
                    }
                });
            } catch (Exception e2) {
                x.b(e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void c(String str, String str2, String str3) {
        if (b) {
            d(str, str2, str3);
        } else {
            e(str, str2, str3);
        }
    }

    private static synchronized void d(String str, String str2, String str3) {
        String strA = a(str, str2, str3, Process.myTid());
        synchronized (q) {
            try {
                f.append(strA);
                if (f.length() >= d) {
                    f = f.delete(0, f.indexOf("\u0001\r\n") + 1);
                }
            } finally {
            }
        }
    }

    private static synchronized void e(String str, String str2, String str3) {
        String strA = a(str, str2, str3, Process.myTid());
        synchronized (q) {
            try {
                f.append(strA);
            } catch (Throwable th) {
            }
            if (f.length() <= d) {
                return;
            }
            if (g) {
                return;
            }
            g = true;
            if (h == null) {
                h = new a(l);
            } else if (h.b == null || h.b.length() + f.length() > h.e) {
                h.a();
            }
            if (h.a(f.toString())) {
                f.setLength(0);
                g = false;
            }
        }
    }

    private static String a(String str, String str2, String str3, long j2) {
        String string;
        e.setLength(0);
        if (str3.length() > 30720) {
            str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
        }
        Date date = new Date();
        if (c != null) {
            string = c.format(date);
        } else {
            string = date.toString();
        }
        e.append(string).append(" ").append(p).append(" ").append(j2).append(" ").append(str).append(" ").append(str2).append(": ").append(str3).append("\u0001\r\n");
        return e.toString();
    }

    public static byte[] a() {
        if (b) {
            if (a) {
                return z.a((File) null, f.toString(), "BuglyLog.txt");
            }
            return null;
        }
        return b();
    }

    private static byte[] b() {
        if (!a) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        synchronized (q) {
            if (h != null && h.a && h.b != null && h.b.length() > 0) {
                sb.append(z.a(h.b, 30720, true));
            }
            if (f != null && f.length() > 0) {
                sb.append(f.toString());
            }
        }
        return z.a((File) null, sb.toString(), "BuglyLog.txt");
    }

    /* compiled from: BUGLY */
    public static class a {
        private boolean a;
        private File b;
        private String c;
        private long d;
        private long e = 30720;

        public a(String str) {
            if (str == null || str.equals("")) {
                return;
            }
            this.c = str;
            this.a = a();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a() {
            try {
                this.b = new File(this.c);
                if (this.b.exists() && !this.b.delete()) {
                    this.a = false;
                    return false;
                }
                if (!this.b.createNewFile()) {
                    this.a = false;
                    return false;
                }
                return true;
            } catch (Throwable th) {
                x.a(th);
                this.a = false;
                return false;
            }
        }

        public final boolean a(String str) throws IOException {
            if (!this.a) {
                return false;
            }
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(this.b, true);
                try {
                    fileOutputStream2.write(str.getBytes("UTF-8"));
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                    this.d += r10.length;
                    this.a = true;
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e) {
                    }
                    return true;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    try {
                        x.a(th);
                        this.a = false;
                        return false;
                    } finally {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                            }
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }
}