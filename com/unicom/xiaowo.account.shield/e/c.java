package com.unicom.xiaowo.account.shield.e;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public class c {
    private static c f;
    private List<a> d;
    private Network a = null;
    private ConnectivityManager.NetworkCallback b = null;
    private ConnectivityManager c = null;
    private Timer e = null;

    public interface a {
        void a(boolean z, Network network);
    }

    private c() {
        this.d = null;
        this.d = new ArrayList();
    }

    public static c a() {
        if (f == null) {
            synchronized (c.class) {
                if (f == null) {
                    f = new c();
                }
            }
        }
        return f;
    }

    public void a(Context context, String str, a aVar) {
        if (Build.VERSION.SDK_INT >= 21) {
            a(context, aVar);
        } else {
            aVar.a(true, null);
        }
    }

    private synchronized void a(Context context, a aVar) {
        if (this.a != null) {
            aVar.a(true, this.a);
            return;
        }
        a(aVar);
        if (this.b == null || this.d.size() < 2) {
            try {
                this.c = (ConnectivityManager) context.getSystemService("connectivity");
                NetworkRequest.Builder builder = new NetworkRequest.Builder();
                builder.addTransportType(0);
                builder.addCapability(12);
                NetworkRequest networkRequestBuild = builder.build();
                this.b = new ConnectivityManager.NetworkCallback() { // from class: com.unicom.xiaowo.account.shield.e.c.1
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onLost(Network network) {
                        super.onLost(network);
                        g.a("Network onLost");
                        c.this.b();
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onUnavailable() {
                        super.onUnavailable();
                        g.a("Network onUnavailable");
                        c.this.a(false, (Network) null);
                        c.this.b();
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public void onAvailable(Network network) {
                        super.onAvailable(network);
                        g.a("Network onAvailable");
                        c.this.a = network;
                        c.this.a(true, network);
                        try {
                            NetworkInfo networkInfo = c.this.c.getNetworkInfo(c.this.a);
                            String extraInfo = networkInfo.getExtraInfo();
                            g.a("APN:" + networkInfo.toString());
                            if (!TextUtils.isEmpty(extraInfo)) {
                                h.d(extraInfo);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                int i = 3000;
                if (h.f() < 3000) {
                    i = 2000;
                }
                if (Build.VERSION.SDK_INT >= 26) {
                    this.c.requestNetwork(networkRequestBuild, this.b, i);
                } else {
                    this.e = new Timer();
                    this.e.schedule(new TimerTask() { // from class: com.unicom.xiaowo.account.shield.e.c.2
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            c.this.a(false, (Network) null);
                        }
                    }, i);
                    this.c.requestNetwork(networkRequestBuild, this.b);
                }
            } catch (Exception e) {
                e.printStackTrace();
                a(false, (Network) null);
            }
        }
    }

    public synchronized void b() {
        try {
            if (this.e != null) {
                this.e.cancel();
                this.e = null;
            }
            if (Build.VERSION.SDK_INT >= 21 && this.c != null && this.b != null) {
                this.c.unregisterNetworkCallback(this.b);
            }
            this.c = null;
            this.b = null;
            this.a = null;
            this.d.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void a(a aVar) {
        try {
            this.d.add(aVar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(boolean z, Network network) {
        try {
            if (this.e != null) {
                this.e.cancel();
                this.e = null;
            }
            Iterator<a> it = this.d.iterator();
            while (it.hasNext()) {
                it.next().a(z, network);
            }
            this.d.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}