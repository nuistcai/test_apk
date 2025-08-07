package cn.fly.verify.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Looper;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.CSCenter;
import cn.fly.commons.FLYVERIFY;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetCommunicator;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.ExecutorDispatcher;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.verify.util.e;
import cn.fly.verify.util.k;
import cn.fly.verify.v;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public class l {
    private static volatile String a;
    private static Integer b;
    private static Integer d;
    private static List<Integer> f;
    private static Object c = new Object();
    private static Object e = new Object();
    private static Object g = new Object();

    public static int a() {
        Integer num;
        try {
            num = (Integer) a(new cn.fly.verify.c<Integer>() { // from class: cn.fly.verify.util.l.1
                /* JADX WARN: Removed duplicated region for block: B:29:0x0067  */
                @Override // cn.fly.verify.c
                /* renamed from: e, reason: merged with bridge method [inline-methods] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public Integer d() {
                    int i = 0;
                    try {
                        String strB = e.b();
                        if ("45412".equals(strB) || "46000".equals(strB) || "46002".equals(strB) || "46004".equals(strB) || "46007".equals(strB) || "46008".equals(strB)) {
                            i = 1;
                        } else if ("46001".equals(strB) || "46006".equals(strB) || "46009".equals(strB)) {
                            i = 2;
                        } else if ("46003".equals(strB) || "46005".equals(strB)) {
                            i = 3;
                        } else if ("46011".equals(strB)) {
                        }
                    } catch (Throwable th) {
                        v.a(th, "Check mobile data encountered exception");
                    }
                    return Integer.valueOf(i);
                }
            });
        } catch (Throwable th) {
            num = null;
        }
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0081 A[Catch: all -> 0x0089, TryCatch #1 {, blocks: (B:4:0x0003, B:32:0x0081, B:33:0x0087, B:7:0x0009, B:31:0x007e, B:8:0x0010, B:16:0x0029, B:21:0x0033, B:24:0x003c, B:25:0x004c, B:27:0x0051, B:28:0x0078, B:15:0x0023), top: B:42:0x0003, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int a(boolean z) {
        boolean zIsPhoneStateDataEnable;
        int iValueOf;
        int iIntValue;
        synchronized (c) {
            if (b == null || z) {
                b = -1;
                try {
                } catch (Throwable th) {
                    v.a(th);
                }
                if (e.m() >= 22) {
                    boolean z2 = true;
                    try {
                        zIsPhoneStateDataEnable = CSCenter.getInstance().isPhoneStateDataEnable();
                    } catch (Throwable th2) {
                        v.a("no cscenter ");
                        zIsPhoneStateDataEnable = true;
                    }
                    if (cn.fly.verify.e.s() != 1) {
                        z2 = false;
                    }
                    if (!z2) {
                        v.a("not allowed slots");
                    }
                    if (zIsPhoneStateDataEnable && z2) {
                        iValueOf = Integer.valueOf(((SubscriptionManager) DH.SyncMtd.getSystemServiceSafe("telephony_subscription_service")).getActiveSubscriptionInfoCount());
                    } else if (z2) {
                        b = Integer.valueOf(CSCenter.getInstance().getActiveSubscriptionInfoCount());
                        v.a("cscenter getsubcount " + b);
                        iIntValue = b.intValue();
                    } else {
                        iValueOf = -1;
                    }
                    b = iValueOf;
                    iIntValue = b.intValue();
                } else {
                    iIntValue = b.intValue();
                }
            }
        }
        return iIntValue;
    }

    public static <T> T a(final cn.fly.verify.c<T> cVar) throws Throwable {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return cVar.a();
        }
        Thread thread = new Thread(new k.a() { // from class: cn.fly.verify.util.l.3
            @Override // cn.fly.verify.util.k.a
            public void a() {
                if (cVar != null) {
                    cVar.a();
                }
            }
        });
        thread.start();
        thread.join(800L);
        T tB = cVar.b();
        if (tB != null || cVar.c() == null) {
            return tB;
        }
        throw cVar.c();
    }

    public static String a(int i) {
        return i == 1 ? "CMCC" : i == 2 ? "CUCC" : i == 3 ? "CTCC" : "UNKNOWN";
    }

    public static String a(int i, String str) {
        int stringRes = cn.fly.tools.utils.ResHelper.getStringRes(FlySDK.getContext(), "fly_verify_error_msg_" + i);
        return stringRes > 0 ? FlySDK.getContext().getString(stringRes) : str;
    }

    public static String a(String str, String str2) {
        int stringRes = n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_" + str);
        return stringRes > 0 ? n.d(stringRes) : str2;
    }

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter((Writer) stringWriter, true));
        stringWriter.getBuffer().toString();
        return stringWriter.getBuffer().toString();
    }

    public static void a(ExecutorDispatcher.SafeRunnable safeRunnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            safeRunnable.run();
        } else {
            try {
                new Thread(safeRunnable).start();
            } catch (Throwable th) {
            }
        }
    }

    public static void a(e.a<Boolean> aVar) {
        e.a(aVar);
    }

    public static boolean a(Context context) {
        return b(context) == 1;
    }

    public static int b(Context context) {
        try {
            if (c(context)) {
                return ((Boolean) DH.SyncMtd.invokeInstanceMethod((ConnectivityManager) DH.SyncMtd.getSystemServiceSafe("connectivity"), "getMobileDataEnabled", new Object[0])).booleanValue() ? 1 : 0;
            }
            return 0;
        } catch (Throwable th) {
            v.a(th, "isMobileDataEnabled");
            return -1;
        }
    }

    public static int b(boolean z) {
        boolean zIsPhoneStateDataEnable;
        int iIntValue;
        synchronized (e) {
            if (d == null || z) {
                try {
                    try {
                        zIsPhoneStateDataEnable = CSCenter.getInstance().isPhoneStateDataEnable();
                    } catch (Throwable th) {
                        v.a("no cscenter ");
                        zIsPhoneStateDataEnable = true;
                    }
                    boolean z2 = cn.fly.verify.e.s() == 1;
                    if (!z2) {
                        v.a("not allowed slots");
                    }
                    if (zIsPhoneStateDataEnable && z2) {
                        d = 0;
                        try {
                            Class<?> clsLoadClass = FlySDK.getContext().getClassLoader().loadClass("android.os.SystemProperties");
                            String str = ((String) clsLoadClass.getMethod("get", String.class).invoke(clsLoadClass, "gsm.sim.state")) + ((String) clsLoadClass.getMethod("get", String.class).invoke(clsLoadClass, "gsm.sim.state.2"));
                            if (!TextUtils.isEmpty(str)) {
                                String[] strArrSplit = str.split(",");
                                if (strArrSplit.length > 0) {
                                    for (String str2 : strArrSplit) {
                                        if (!TextUtils.isEmpty(str2) && !"ABSENT".equals(str2) && !"NOT_READY".equals(str2)) {
                                            d = Integer.valueOf(d.intValue() + 1);
                                        }
                                    }
                                }
                            }
                        } catch (Throwable th2) {
                            v.a(th2);
                        }
                    } else if (z2) {
                        d = Integer.valueOf(CSCenter.getInstance().getActiveSubscriptionInfoCount());
                        v.a("cscenter getsubcount2 " + d);
                    } else {
                        d = -1;
                    }
                } catch (Throwable th3) {
                    v.a(th3);
                    d = -1;
                }
                iIntValue = d.intValue();
            } else {
                iIntValue = d.intValue();
            }
        }
        return iIntValue;
    }

    public static String b() {
        try {
            return e.b();
        } catch (Throwable th) {
            v.a(th, "getMNC encountered exception");
            return "-1";
        }
    }

    public static void b(final e.a<Boolean> aVar) {
        e.b(new e.a<String>() { // from class: cn.fly.verify.util.l.2
            @Override // cn.fly.verify.util.e.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onResult(String str, Throwable th) {
                if (aVar != null) {
                    aVar.onResult(Boolean.valueOf("wifi".equalsIgnoreCase(str)), th);
                }
            }
        });
    }

    public static String c() {
        switch (a()) {
            case 1:
                return "CMCC";
            case 2:
                return "CUCC";
            case 3:
                return "CTCC";
            default:
                return "UNKNOWN";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0084 A[Catch: all -> 0x0088, TryCatch #2 {, blocks: (B:4:0x0003, B:38:0x0084, B:39:0x0086, B:7:0x0009, B:37:0x0081, B:8:0x0010, B:16:0x0029, B:21:0x0033, B:24:0x003c, B:29:0x005c, B:31:0x0062, B:32:0x0066, B:34:0x006c, B:26:0x004b, B:15:0x0023, B:12:0x0019), top: B:50:0x0003, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<Integer> c(boolean z) {
        boolean zIsPhoneStateDataEnable;
        List<SubscriptionInfo> activeSubscriptionInfoList;
        List<Integer> list;
        synchronized (g) {
            if (f == null || z) {
                f = new ArrayList();
                try {
                } catch (Throwable th) {
                    v.a(th);
                }
                if (e.m() >= 22) {
                    boolean z2 = true;
                    try {
                        zIsPhoneStateDataEnable = CSCenter.getInstance().isPhoneStateDataEnable();
                    } catch (Throwable th2) {
                        v.a("no cscenter");
                        zIsPhoneStateDataEnable = true;
                    }
                    if (cn.fly.verify.e.r() != 1) {
                        z2 = false;
                    }
                    if (!z2) {
                        v.a("not allowed sids");
                    }
                    if (zIsPhoneStateDataEnable && z2) {
                        activeSubscriptionInfoList = ((SubscriptionManager) DH.SyncMtd.getSystemServiceSafe("telephony_subscription_service")).getActiveSubscriptionInfoList();
                    } else if (z2) {
                        activeSubscriptionInfoList = CSCenter.getInstance().getActiveSubscriptionInfoList();
                        v.a("cscenter getsublist");
                    } else {
                        activeSubscriptionInfoList = null;
                    }
                    if (activeSubscriptionInfoList == null || activeSubscriptionInfoList.isEmpty()) {
                        list = f;
                    } else {
                        Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
                        while (it.hasNext()) {
                            f.add(Integer.valueOf(it.next().getSubscriptionId()));
                        }
                        list = f;
                    }
                }
            }
        }
        return list;
    }

    public static boolean c(Context context) {
        try {
            if (e.m() > 22) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager.getSimState() == 1) {
                    return false;
                }
                if (telephonyManager.getSimState() == 0) {
                    return false;
                }
            }
        } catch (Throwable th) {
        }
        return true;
    }

    public static boolean d() {
        String strG = e.g();
        return "wifi".equalsIgnoreCase(strG) || "2g".equalsIgnoreCase(strG) || "3g".equalsIgnoreCase(strG) || "4g".equalsIgnoreCase(strG) || "5g".equalsIgnoreCase(strG);
    }

    public static Activity e() {
        try {
            Map map = (Map) ReflectHelper.getInstanceField(DH.SyncMtd.currentActivityThread(), "mActivities");
            for (Object obj : map.values()) {
                if (!((Boolean) ReflectHelper.getInstanceField(obj, "paused")).booleanValue()) {
                    return (Activity) ReflectHelper.getInstanceField(obj, "activity");
                }
            }
            for (Object obj2 : map.values()) {
                if (!((Boolean) ReflectHelper.getInstanceField(obj2, "stopped")).booleanValue()) {
                    return (Activity) ReflectHelper.getInstanceField(obj2, "activity");
                }
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public static String f() {
        String string = UUID.randomUUID().toString();
        try {
            string = UUID.nameUUIDFromBytes((string + System.currentTimeMillis() + Math.random()).getBytes("utf8")).toString();
        } catch (Throwable th) {
        }
        return !TextUtils.isEmpty(string) ? string.replace("-", "") : string;
    }

    public static int g() {
        try {
            if (cn.fly.verify.e.q() == 0) {
                v.a("not allowed sid");
                return -1;
            }
            if (e.m() >= 30) {
                return SubscriptionManager.getActiveDataSubscriptionId();
            }
            if (e.m() >= 24) {
                return SubscriptionManager.getDefaultDataSubscriptionId();
            }
            return -1;
        } catch (Exception e2) {
            return -1;
        }
    }

    public static String h() {
        String strB = b();
        try {
            if ("45412".equals(strB)) {
                return "CMHK";
            }
            if (!"46000".equals(strB) && !"46002".equals(strB) && !"46004".equals(strB) && !"46007".equals(strB) && !"46008".equals(strB)) {
                if (!"46001".equals(strB) && !"46006".equals(strB) && !"46009".equals(strB)) {
                    if (!"46003".equals(strB) && !"46005".equals(strB)) {
                        if (!"46011".equals(strB)) {
                            return "UNKNOWN";
                        }
                    }
                    return "CTCC";
                }
                return "CUCC";
            }
            return "CMCC";
        } catch (Throwable th) {
            return "UNKNOWN";
        }
    }

    public static String i() {
        String strH = h();
        return "CMHK".equals(strH) ? "CMCC" : strH;
    }

    public static String j() {
        if (TextUtils.isEmpty(a)) {
            a = NetCommunicator.getDUID(new FLYVERIFY());
        }
        return a;
    }

    public static String k() {
        try {
            return ((TelephonyManager) FlySDK.getContext().getSystemService("phone")).getSimOperator();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String l() {
        try {
            return Data.MD5(FlySDK.getContext().getPackageManager().getPackageInfo(FlySDK.getContext().getPackageName(), 64).signatures[0].toByteArray());
        } catch (Exception e2) {
            FlyLog.getInstance().w(e2);
            return null;
        }
    }

    public static int m() {
        int iB = b(FlySDK.getContext());
        String strG = e.g();
        if (iB == 1 && "wifi".equalsIgnoreCase(strG)) {
            return 1;
        }
        if (iB == 1 && !"none".equalsIgnoreCase(strG)) {
            return 2;
        }
        if (iB == 1) {
            return 3;
        }
        if (iB == -1 && "wifi".equalsIgnoreCase(strG)) {
            return 4;
        }
        if (iB == -1 && !"none".equalsIgnoreCase(strG)) {
            return 5;
        }
        if (iB == -1) {
            return 6;
        }
        if (iB == 0 && "wifi".equalsIgnoreCase(strG)) {
            return 7;
        }
        return (iB != 0 || "none".equalsIgnoreCase(strG)) ? 9 : 8;
    }
}