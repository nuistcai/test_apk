package cn.fly.tools.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.text.TextUtils;
import cn.fly.commons.C0041r;
import cn.fly.commons.CSCenter;
import cn.fly.commons.o;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.tools.utils.DH;

/* loaded from: classes.dex */
public class NtFetcher implements PublicMemberKeeper {
    private static NtFetcher a;
    private Context b;
    private BroadcastReceiver c;
    private String d;
    private Integer e;

    protected NtFetcher(Context context) {
        this.b = context;
        a();
    }

    public static NtFetcher getInstance(Context context) {
        if (a == null) {
            synchronized (NtFetcher.class) {
                if (a == null) {
                    a = new NtFetcher(context);
                }
            }
        }
        return a;
    }

    private void a() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) DH.SyncMtd.getSystemServiceSafe("connectivity");
            if (DH.SyncMtd.getOSVersionIntForFly() >= 26 && DH.SyncMtd.checkPermission(o.a("039deMdcdjdkdidcdlRjf-djdfdififididkVe=dlfdededgieleldheggifcgfghgjicdhelfcfdfcgi"))) {
                connectivityManager.registerDefaultNetworkCallback(b());
            } else if (DH.SyncMtd.getOSVersionIntForFly() >= 21 && DH.SyncMtd.checkPermission(o.a("039deQdcdjdkdidcdlDjf1djdfdififididkVe(dlfdededgieleldheggifcgfghgjicdhelfcfdfcgi"))) {
                connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().build(), b());
            } else {
                e();
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    public void recycle() {
        f();
    }

    public synchronized String getNtType(boolean z) {
        if (TextUtils.isEmpty(this.d) || z) {
            this.d = g();
        }
        return this.d;
    }

    public synchronized int getDtNtType() {
        if (this.e == null) {
            this.e = Integer.valueOf(d());
        }
        return this.e.intValue();
    }

    private ConnectivityManager.NetworkCallback b() {
        return new ConnectivityManager.NetworkCallback() { // from class: cn.fly.tools.utils.NtFetcher.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                super.onAvailable(network);
                NtFetcher.this.c();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                super.onLost(network);
                NtFetcher.this.c();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLosing(Network network, int i) {
                super.onLosing(network, i);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                super.onLinkPropertiesChanged(network, linkProperties);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onUnavailable() {
                super.onUnavailable();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        this.d = g();
        this.e = Integer.valueOf(d());
    }

    private int d() {
        if (DH.SyncMtd.getSystemServiceSafe("phone") == null || !DH.SyncMtd.checkPermission(o.a("035deNdcdjdkdidcdlBjf7djdfdififididkIeUdlgjgifdfldhglfkgheggidhelfcfdfcgi"))) {
            return -1;
        }
        if (DH.SyncMtd.getOSVersionIntForFly() >= 24) {
            return cn.fly.tools.b.e.a(this.b).c();
        }
        return cn.fly.tools.b.e.a(this.b).b();
    }

    private void e() {
        this.c = new BroadcastReceiver() { // from class: cn.fly.tools.utils.NtFetcher.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equalsIgnoreCase(o.a("036deKdcdjdkdidcdl+efiGdl5cQdk:eeIdledghegeggiedfceegkeefcildhedfkfdegidgi"))) {
                        NtFetcher.this.c();
                    }
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(o.a("036de2dcdjdkdidcdlXefi+dl9c[dk+eeYdledghegeggiedfceegkeefcildhedfkfdegidgi"));
        C0041r.a(this.c, intentFilter);
    }

    private void f() {
        if (this.c != null) {
            C0041r.a(this.c);
            this.c = null;
        }
    }

    private String g() {
        if (DH.SyncMtd.checkPermission(o.a("035de?dcdjdkdidcdlGjf<djdfdififididkGeRdlgjgifdfldhglfkgheggidhelfcfdfcgi")) && CSCenter.getInstance().isPhoneStateDataEnable()) {
            return h();
        }
        return getNetworkTypeDesensitized();
    }

    private String h() {
        Object systemServiceSafe;
        NetworkInfo activeNetworkInfo;
        try {
            if (DH.SyncMtd.checkPermission(o.a("039de8dcdjdkdidcdlLjf_djdfdififididk?eIdlfdededgieleldheggifcgfghgjicdhelfcfdfcgi")) && (systemServiceSafe = DH.SyncMtd.getSystemServiceSafe("connectivity")) != null && (activeNetworkInfo = ((ConnectivityManager) systemServiceSafe).getActiveNetworkInfo()) != null && activeNetworkInfo.isAvailable()) {
                int type = activeNetworkInfo.getType();
                switch (type) {
                    case 0:
                        int iB = cn.fly.tools.b.e.a(this.b).b();
                        if (a(iB)) {
                            return o.a("002?hiid");
                        }
                        if (c(iB)) {
                            return o.a("002Mhlid");
                        }
                        return o.a(d(iB) ? "002 jfid" : "002Oheid");
                    case 1:
                        return o.a("004Cfgdiefdi");
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    default:
                        return String.valueOf(type);
                    case 6:
                        return o.a("005WfgdidfRd'ei");
                    case 7:
                        return o.a("009HffAgJdgFfi4dkdk%ih");
                    case 8:
                        return o.a("005^dcdgdfdfec");
                    case 9:
                        return o.a("008fihf(djMefi");
                }
            }
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
        return o.a("004eBdkPef");
    }

    public String getNetworkTypeDesensitized() {
        ConnectivityManager connectivityManager;
        NetworkInfo networkInfo = null;
        try {
            if (DH.SyncMtd.checkPermission(o.a("039de dcdjdkdidcdl!jfTdjdfdififididkQe>dlfdededgieleldheggifcgfghgjicdhelfcfdfcgi")) && (connectivityManager = (ConnectivityManager) DH.SyncMtd.getSystemServiceSafe("connectivity")) != null) {
                NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(1);
                if (networkInfo2 != null && networkInfo2.getState() == NetworkInfo.State.CONNECTED) {
                    String strA = o.a("004<fgdiefdi");
                    FlyLog.getInstance().d("networkInfo: " + networkInfo2, new Object[0]);
                    return strA;
                }
                NetworkInfo networkInfo3 = connectivityManager.getNetworkInfo(0);
                if (networkInfo3 != null && networkInfo3.getState() == NetworkInfo.State.CONNECTED) {
                    String subtypeName = networkInfo3.getSubtypeName();
                    if (o.a("0028eggj").equalsIgnoreCase(subtypeName)) {
                        String strA2 = o.a("002%hiid");
                        FlyLog.getInstance().d("networkInfo: " + networkInfo3, new Object[0]);
                        return strA2;
                    }
                    if (o.a("003_fefcgi").equalsIgnoreCase(subtypeName)) {
                        String strA3 = o.a("002Shlid");
                        FlyLog.getInstance().d("networkInfo: " + networkInfo3, new Object[0]);
                        return strA3;
                    }
                    if (a(subtypeName)) {
                        String strA4 = o.a("002Gjfid");
                        FlyLog.getInstance().d("networkInfo: " + networkInfo3, new Object[0]);
                        return strA4;
                    }
                    if (!b(subtypeName)) {
                        FlyLog.getInstance().d("networkInfo: " + networkInfo3, new Object[0]);
                        return subtypeName;
                    }
                    String strA5 = o.a("002_heid");
                    FlyLog.getInstance().d("networkInfo: " + networkInfo3, new Object[0]);
                    return strA5;
                }
                NetworkInfo networkInfo4 = connectivityManager.getNetworkInfo(7);
                if (networkInfo4 != null && networkInfo4.getState() == NetworkInfo.State.CONNECTED) {
                    String strA6 = o.a("0097ff>g_dgVfiTdkdk%ih");
                    FlyLog.getInstance().d("networkInfo: " + networkInfo4, new Object[0]);
                    return strA6;
                }
                NetworkInfo networkInfo5 = connectivityManager.getNetworkInfo(8);
                if (networkInfo5 != null && networkInfo5.getState() == NetworkInfo.State.CONNECTED) {
                    String strA7 = o.a("0059dcdgdfdfec");
                    FlyLog.getInstance().d("networkInfo: " + networkInfo5, new Object[0]);
                    return strA7;
                }
                NetworkInfo networkInfo6 = connectivityManager.getNetworkInfo(9);
                if (networkInfo6 != null && networkInfo6.getState() == NetworkInfo.State.CONNECTED) {
                    String strA8 = o.a("008fihf8dj efi");
                    FlyLog.getInstance().d("networkInfo: " + networkInfo6, new Object[0]);
                    return strA8;
                }
                networkInfo = connectivityManager.getNetworkInfo(6);
                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    String strA9 = o.a("0050fgdidf@d)ei");
                    FlyLog.getInstance().d("networkInfo: " + networkInfo, new Object[0]);
                    return strA9;
                }
            }
            FlyLog.getInstance().d("networkInfo: " + networkInfo, new Object[0]);
        } catch (Throwable th) {
            try {
                FlyLog.getInstance().e(th);
                FlyLog.getInstance().d("networkInfo: " + ((Object) null), new Object[0]);
            } catch (Throwable th2) {
                FlyLog.getInstance().d("networkInfo: " + ((Object) null), new Object[0]);
                throw th2;
            }
        }
        return o.a("004e)dk$ef");
    }

    private boolean a(int i) {
        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe("phone");
        if (systemServiceSafe == null) {
            return false;
        }
        if (a(systemServiceSafe) || b(systemServiceSafe)) {
            return true;
        }
        return b(i);
    }

    private boolean a(Object obj) {
        Object serviceState;
        if (obj != null && DH.SyncMtd.checkPermission(o.a("035deKdcdjdkdidcdlJjf]djdfdififididk.eCdlgjgifdfldhglfkgheggidhelfcfdfcgi"))) {
            if (CSCenter.getInstance().isPhoneStateDataEnable()) {
                String manufacturerForFly = DH.SyncMtd.getManufacturerForFly();
                serviceState = null;
                if (!TextUtils.isEmpty(manufacturerForFly) && ((manufacturerForFly.contains(o.a("006hOdg?d=fg.fIdi")) || manufacturerForFly.contains(o.a("006Ifkdg-d1fg7f.di")) || manufacturerForFly.contains(o.a("006Mfkekfdgfgiee"))) && DH.SyncMtd.getOSVersionIntForFly() >= 29)) {
                    serviceState = ReflectHelper.invokeInstanceMethodNoThrow(obj, o.a("015:ejFfi6elEfMdjdddiWcf]elBidif"), null, new Object[0]);
                }
            } else {
                serviceState = CSCenter.getInstance().getServiceState();
            }
            if (serviceState != null && ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(serviceState, o.a("0164ejRfiVfkfgeg)fiSfgdkdjehfcec!jf"), 0, new Object[0])).intValue() == 20) {
                return true;
            }
        }
        return false;
    }

    private boolean b(Object obj) {
        Object serviceState;
        if (obj != null && DH.SyncMtd.checkPermission(o.a("035de?dcdjdkdidcdl-jf$djdfdififididkReGdlgjgifdfldhglfkgheggidhelfcfdfcgi")) && DH.SyncMtd.getOSVersionIntForFly() >= 26) {
            if (CSCenter.getInstance().isPhoneStateDataEnable()) {
                serviceState = ReflectHelper.invokeInstanceMethodNoThrow(obj, o.a("015Yej,fiQelSf(djdddi3cfNel6idif"), null, new Object[0]);
            } else {
                serviceState = CSCenter.getInstance().getServiceState();
            }
            if (serviceState != null && ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(serviceState, o.a("010Aej!fiMegdjelLidif"), 0, new Object[0])).intValue() == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean b(int i) {
        return i == 20;
    }

    private boolean c(int i) {
        return i == 13;
    }

    private boolean d(int i) {
        switch (i) {
            case 0:
                return false;
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return true;
            case 4:
                return false;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
                return false;
            case 8:
                return true;
            case 9:
                return true;
            case 10:
                return true;
            case 11:
                return false;
            case 12:
                return true;
            case 13:
                return true;
            case 14:
                return true;
            case 15:
                return true;
            default:
                return false;
        }
    }

    private boolean a(String str) {
        if (o.a("005?fkelglfdgl").equalsIgnoreCase(str) | o.a("006$gigkflghdhfh").equalsIgnoreCase(str) | o.a("006Fgigkflghdhfd").equalsIgnoreCase(str) | o.a("005Ufkelflglfd").equalsIgnoreCase(str) | o.a("004<fkelglfd").equalsIgnoreCase(str) | o.a("005<fkelekglfd").equalsIgnoreCase(str) | o.a("004 ekhcfcel").equalsIgnoreCase(str) | o.a("005%gifkgjglfl").equalsIgnoreCase(str) | o.a("006]gigkflghdhfj").equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    private boolean b(String str) {
        if (o.a("004Ueeflgieg").equalsIgnoreCase(str) | o.a("004*edflhcfd").equalsIgnoreCase(str) | o.a("004[giflidgi").equalsIgnoreCase(str) | o.a("004Tidglgjel").equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }
}