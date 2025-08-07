package cn.fly.commons;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.FileLocker;
import cn.fly.tools.utils.ResHelper;
import java.io.File;

/* loaded from: classes.dex */
public class u {
    private static final String l = n.a("011a8bibdbdXje+biHa3cfdgGj");
    public static final String a = l + ".mrlock";
    public static final String b = l + n.a("007,bjba=fe6bi%aNcf");
    public static final String c = l + n.a("011Qbjch-eObidd beWdcbi<a,cf");
    public static final String d = l + n.a("008)bjbacabfHe,biNaFcf");
    public static final String e = l + n.a("008CbjbadgbfEeNbi0aCcf");
    public static final String f = l + ".cl_lock";
    public static final String g = l + ".gcf_lock";
    public static final String h = l + ".mp_lock";
    public static final Object i = new Object();
    public static final Object j = new Object();
    public static final String k = l + ".pv_lock";

    public static synchronized File a(String str) {
        return ResHelper.getDataCacheFile(FlySDK.getContext(), str, true);
    }

    public static boolean a(File file, t tVar) {
        return a(file, true, tVar);
    }

    private static String b(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.endsWith(c)) {
                return c;
            }
            if (str.endsWith(b)) {
                return b;
            }
            if (str.endsWith(d)) {
                return d;
            }
            if (str.endsWith(e)) {
                return e;
            }
            if (str.endsWith(f)) {
                return f;
            }
            if (str.endsWith(g)) {
                return g;
            }
        }
        return str;
    }

    public static boolean a(File file, boolean z, t tVar) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            String absolutePath = file.getAbsolutePath();
            synchronized (b(absolutePath)) {
                FileLocker fileLocker = new FileLocker();
                fileLocker.setLockFile(absolutePath);
                if (!fileLocker.lock(z)) {
                    return false;
                }
                try {
                    if (!tVar.a(fileLocker)) {
                        fileLocker.release();
                    }
                } catch (Throwable th) {
                    fileLocker.release();
                }
                return true;
            }
        } catch (Throwable th2) {
            FlyLog.getInstance().w(th2);
            return true;
        }
    }

    public static boolean a(String str, boolean z, long j2, long j3, t tVar) {
        FileLocker fileLocker = new FileLocker();
        try {
            fileLocker.setLockFile(str);
            if (fileLocker.lock(z, j2, j3)) {
                try {
                    if (!tVar.a(fileLocker)) {
                        fileLocker.release();
                    }
                } catch (Throwable th) {
                    fileLocker.release();
                }
                return true;
            }
            return false;
        } catch (Throwable th2) {
            FlyLog.getInstance().w(th2);
            fileLocker.release();
            return true;
        }
    }
}