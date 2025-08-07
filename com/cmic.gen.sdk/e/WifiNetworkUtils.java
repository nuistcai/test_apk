package com.cmic.gen.sdk.e;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;

/* compiled from: WifiNetworkUtils.java */
/* renamed from: com.cmic.gen.sdk.e.s, reason: use source file name */
/* loaded from: classes.dex */
public class WifiNetworkUtils {
    private static WifiNetworkUtils a = null;
    private ConnectivityManager b;

    /* compiled from: WifiNetworkUtils.java */
    /* renamed from: com.cmic.gen.sdk.e.s$a */
    public interface a {
        void a(Network network, ConnectivityManager.NetworkCallback networkCallback);
    }

    private WifiNetworkUtils(Context context) {
        try {
            this.b = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WifiNetworkUtils a(Context context) {
        if (a == null) {
            synchronized (WifiNetworkUtils.class) {
                if (a == null) {
                    a = new WifiNetworkUtils(context);
                }
            }
        }
        return a;
    }

    public synchronized void a(final a aVar) {
        if (this.b == null) {
            LogUtils.a("WifiNetworkUtils", "mConnectivityManager 为空");
            aVar.a(null, null);
            return;
        }
        NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addCapability(12).addTransportType(0).build();
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.cmic.gen.sdk.e.s.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                try {
                    if (WifiNetworkUtils.this.b.getNetworkCapabilities(network).hasTransport(0)) {
                        aVar.a(network, this);
                    } else {
                        LogUtils.a("WifiNetworkUtils", "切换失败，未开启数据网络");
                        aVar.a(null, this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    aVar.a(null, this);
                }
            }
        };
        try {
            this.b.requestNetwork(networkRequestBuild, networkCallback);
        } catch (Exception e) {
            e.printStackTrace();
            aVar.a(null, networkCallback);
        }
    }

    public void a(ConnectivityManager.NetworkCallback networkCallback) {
        if (this.b == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT < 21 || networkCallback == null) {
                return;
            }
            LogUtils.b("WifiNetworkUtils", "unregisterNetworkCallback");
            this.b.unregisterNetworkCallback(networkCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}