package cn.fly.tools;

import android.content.pm.ApplicationInfo;
import android.content.pm.Signature;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.m;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import java.io.File;

/* loaded from: classes.dex */
public class c {
    public static int a(ApplicationInfo applicationInfo, String str) {
        if (applicationInfo == null || !a("1001", str)) {
            return -1;
        }
        return applicationInfo.uid;
    }

    public static String b(ApplicationInfo applicationInfo, String str) {
        if (applicationInfo == null || !a("1004", str)) {
            return null;
        }
        return applicationInfo.name;
    }

    public static int c(ApplicationInfo applicationInfo, String str) {
        if (applicationInfo == null || !a("1005", str)) {
            return -1;
        }
        return applicationInfo.labelRes;
    }

    public static CharSequence d(ApplicationInfo applicationInfo, String str) {
        if (applicationInfo == null || !a("1006", str)) {
            return null;
        }
        return applicationInfo.nonLocalizedLabel;
    }

    public static boolean e(ApplicationInfo applicationInfo, String str) {
        return applicationInfo != null && a("1007", str) && applicationInfo.enabled;
    }

    public static String f(ApplicationInfo applicationInfo, String str) {
        if (applicationInfo == null || !a("1008", str)) {
            return null;
        }
        return applicationInfo.processName;
    }

    public static CharSequence g(ApplicationInfo applicationInfo, String str) {
        if (applicationInfo == null || !a("1101", str)) {
            return null;
        }
        return a(applicationInfo);
    }

    private static CharSequence a(ApplicationInfo applicationInfo) {
        CharSequence charSequenceLoadLabel = null;
        try {
            synchronized ("1101") {
                File file = new File(FlySDK.getContext().getFilesDir(), ".llnps");
                if (!file.exists()) {
                    file.createNewFile();
                    charSequenceLoadLabel = applicationInfo.loadLabel(FlySDK.getContext().getPackageManager());
                    file.delete();
                }
            }
        } catch (Throwable th) {
        }
        return charSequenceLoadLabel;
    }

    public static ApplicationInfo a(Object obj, String str) {
        if (obj == null || !a("2001", str)) {
            return null;
        }
        return (ApplicationInfo) ReflectHelper.getInstanceField(obj, m.a("015flliAfk>efk4fkfm(g2ggMgUghfm"), null);
    }

    public static Signature[] b(Object obj, String str) {
        if (obj == null || !a("2002", str)) {
            return null;
        }
        return (Signature[]) ReflectHelper.getInstanceField(obj, m.a("0108hkfkgl(gfk,fifl-hQhk"), null);
    }

    public static String c(Object obj, String str) {
        return (obj == null || !a("2004", str)) ? "1.0" : (String) ReflectHelper.getInstanceField(obj, m.a("0115ff4h2flhkfkfm;g5gi5fDfh7h"), "1.0");
    }

    public static long d(Object obj, String str) {
        if (obj == null || !a("2005", str)) {
            return 0L;
        }
        return ((Long) ReflectHelper.getInstanceField(obj, m.a("016[ghfkflhk;k!gg>g3hk%kfiiZhefkfh0h"), 0L)).longValue();
    }

    public static long e(Object obj, String str) {
        if (obj == null || !a("2006", str)) {
            return 0L;
        }
        return ((Long) ReflectHelper.getInstanceField(obj, m.a("014if.hk6k]gm*l4fe7fkh)hefkfh,h"), 0L)).longValue();
    }

    public static int f(Object obj, String str) {
        if (obj == null || !a("2007", str)) {
            return 0;
        }
        return ((Integer) ReflectHelper.getInstanceField(obj, m.a("0116ffUh5flhkfkfmJgCgffmfe-h"), 0)).intValue();
    }

    public static long g(Object obj, String str) {
        if (obj == null || !a("2101", str)) {
            return 0L;
        }
        return ((Long) ReflectHelper.invokeInstanceMethodNoThrow(obj, m.a("018UglEhk4hgfm=gTglimGh;flhkfkfm,g)gffmfeXh"), 0L, new Object[0])).longValue();
    }

    public static boolean a(String str, String str2) {
        String str3 = (String) cn.fly.commons.c.a("aps", (Object) null);
        if (str3 == null) {
            return true;
        }
        String[] strArrSplit = str3.split(";");
        if (TextUtils.equals(str2, DH.SyncMtd.getPackageName())) {
            if (strArrSplit.length > 1) {
                return !strArrSplit[1].contains(str);
            }
            return true;
        }
        if (strArrSplit.length > 0) {
            return !strArrSplit[0].contains(str);
        }
        return true;
    }
}