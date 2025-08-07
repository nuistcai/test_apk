package cn.fly.verify.util;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.SystemClock;
import cn.fly.FlySDK;
import cn.fly.tools.utils.DH;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.util.e;
import cn.fly.verify.v;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class g {
    private static List<Network> d = new ArrayList();
    private static List<ConnectivityManager.NetworkCallback> e = new ArrayList();
    private Network b;
    private ConnectivityManager.NetworkCallback c;
    private long f = 3000;
    public ConnectivityManager a = (ConnectivityManager) DH.SyncMtd.getSystemServiceSafe("connectivity");

    public static void b() {
        if (e.m() < 21 || e.size() <= 0) {
            return;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) DH.SyncMtd.getSystemServiceSafe("connectivity");
            Iterator<ConnectivityManager.NetworkCallback> it = e.iterator();
            while (it.hasNext()) {
                connectivityManager.unregisterNetworkCallback(it.next());
            }
            e.clear();
            if (d.size() > 0) {
                d.clear();
            }
        } catch (Throwable th) {
            v.a(th);
        }
        v.a("release");
    }

    public void a() {
        e.b(new e.a<String>() { // from class: cn.fly.verify.util.g.1
            @Override // cn.fly.verify.util.e.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onResult(String str, Throwable th) {
                if ("wifi".equalsIgnoreCase(str) && l.a(FlySDK.getContext()) && e.m() >= 21) {
                    g.this.c();
                    v.a("switch net");
                }
            }
        });
    }

    public Network c() {
        try {
            d();
            return null;
        } catch (Throwable th) {
            v.a(th);
            return null;
        }
    }

    public Network d() throws Throwable {
        this.b = null;
        if (e.m() >= 21) {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(12);
            builder.addTransportType(0);
            NetworkRequest networkRequestBuild = builder.build();
            this.c = new ConnectivityManager.NetworkCallback() { // from class: cn.fly.verify.util.g.2
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    g.this.b = network;
                    g.d.add(network);
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                }
            };
            e.add(this.c);
            this.a.requestNetwork(networkRequestBuild, this.c);
            long j = 0;
            while (this.b == null) {
                j++;
                SystemClock.sleep(50L);
                if (j > this.f / 50) {
                    throw new VerifyException(1, l.a("switch_timeout", "switch timeout"));
                }
            }
        }
        return this.b;
    }
}