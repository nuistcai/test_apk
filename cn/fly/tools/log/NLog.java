package cn.fly.tools.log;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import cn.fly.commons.v;
import cn.fly.commons.w;
import cn.fly.tools.proguard.ClassKeeper;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.util.HashMap;

/* loaded from: classes.dex */
public class NLog implements ClassKeeper, PublicMemberKeeper {
    private static final HashMap<String, NLog> b = new HashMap<>();
    protected boolean a;
    private String c;
    private int d;

    public static NLog getInstance(String str, int i, String str2) {
        NLog nLog;
        synchronized (b) {
            nLog = b.get(str);
            if (nLog == null) {
                nLog = new NLog(str, i);
                b.put(str, nLog);
            }
        }
        return nLog;
    }

    private NLog() {
        this.a = false;
        this.c = null;
        this.d = -1;
    }

    private NLog(String str, int i) {
        this.a = false;
        this.c = str;
        this.d = i;
    }

    public final void dg() {
        this.a = true;
    }

    public final int v(Throwable th) {
        return log(2, th);
    }

    public final int v(Object obj, Object... objArr) {
        return log(2, obj, objArr);
    }

    public final int v(Throwable th, Object obj, Object... objArr) {
        return log(2, th, obj, objArr);
    }

    public final int d(Throwable th) {
        return log(3, th);
    }

    public final int d(Object obj, Object... objArr) {
        return log(3, obj, objArr);
    }

    public final int d(Throwable th, Object obj, Object... objArr) {
        return log(3, th, obj, objArr);
    }

    public final int w(Throwable th) {
        return log(5, th);
    }

    public final int w(Object obj, Object... objArr) {
        return log(5, obj, objArr);
    }

    public final int w(Throwable th, Object obj, Object... objArr) {
        return log(5, th, obj, objArr);
    }

    public final int w(String str) {
        return log(5, str, new Object[0]);
    }

    public final int i(Throwable th) {
        return log(4, th);
    }

    public final int i(Throwable th, Object obj, Object... objArr) {
        return log(4, th, obj, objArr);
    }

    public final int i(Object obj, Object... objArr) {
        return log(4, obj, objArr);
    }

    public final int i(String str) {
        return log(4, str, new Object[0]);
    }

    public final int e(Throwable th) {
        return log(6, th);
    }

    public final int e(Throwable th, Object obj, Object... objArr) {
        return log(6, th, obj, objArr);
    }

    public final int e(Object obj, Object... objArr) {
        return log(6, obj, objArr);
    }

    public final int e(String str) {
        return log(6, str, new Object[0]);
    }

    public final int log(int i, Throwable th) {
        return a(i, 0, a(th));
    }

    public final int log(int i, Object obj, Object... objArr) {
        String string = obj.toString();
        if (objArr.length > 0) {
            string = String.format(string, objArr);
        }
        return a(i, 0, string);
    }

    public final int log(int i, Throwable th, Object obj, Object... objArr) {
        String string = obj.toString();
        StringBuilder sb = new StringBuilder();
        if (objArr.length > 0) {
            string = String.format(string, objArr);
        }
        return a(i, 0, sb.append(string).append('\n').append(a(th)).toString());
    }

    public final void error(Throwable th) {
        error(a(th));
    }

    public final void error(String str) {
        e(str);
    }

    public final void crash(Throwable th) {
        a(6, 1, b(th));
    }

    private String a(Throwable th) {
        try {
            return Log.getStackTraceString(th);
        } catch (Throwable th2) {
            if (th2 instanceof OutOfMemoryError) {
                return w.b("0230diWehQdkEhcbLdgebci[cbeEdkAh4cich8d5dihecjcjce");
            }
            return th2.getMessage();
        }
    }

    private int a(int i, int i2, String str) {
        try {
            String str2 = Process.myPid() + "-" + Process.myTid() + "(" + Thread.currentThread().getName() + ") " + str;
            if (i2 == 1) {
                v.a().a(1, this.c, this.d, str2);
            }
            v.a().a(i, str2);
            return 0;
        } catch (Throwable th) {
            return 0;
        }
    }

    private String b(Throwable th) {
        try {
            String name = th.getClass().getName();
            String strC = c(th);
            String string = "";
            if (th.getStackTrace().length > 0) {
                string = th.getStackTrace()[0].toString();
            }
            Throwable cause = th;
            while (cause != null && cause.getCause() != null) {
                cause = cause.getCause();
            }
            if (cause != null && cause != th) {
                StringBuilder sb = new StringBuilder();
                sb.append(name).append(":").append(strC).append("\n");
                sb.append(string);
                sb.append("\n......");
                sb.append("\nCaused by:\n");
                sb.append(a(cause));
                return sb.toString();
            }
            return a(th);
        } catch (Throwable th2) {
            return a(th);
        }
    }

    private static String c(Throwable th) {
        String message = th.getMessage();
        if (TextUtils.isEmpty(message)) {
            return "";
        }
        if (message.length() <= 1000) {
            return message;
        }
        return message.substring(0, 1000) + "\n[Message over limit size:1000, cut!]";
    }
}