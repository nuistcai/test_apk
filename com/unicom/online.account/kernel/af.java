package com.unicom.online.account.kernel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.FutureTask;

/* loaded from: classes.dex */
public class af {
    private static af f;
    private Network d = null;
    ConnectivityManager.NetworkCallback a = null;
    ConnectivityManager b = null;
    private List<a> e = new ArrayList();
    Timer c = null;

    public interface a {
        void a(boolean z, Object obj);
    }

    private af() {
    }

    public static af a() {
        if (f == null) {
            synchronized (af.class) {
                if (f == null) {
                    f = new af();
                }
            }
        }
        return f;
    }

    private synchronized void a(a aVar) {
        try {
            this.e.add(aVar);
        } catch (Exception e) {
            aj.a(e);
        }
    }

    private synchronized void b(final Context context, final a aVar) {
        if (this.d != null) {
            aVar.a(true, this.d);
            return;
        }
        a(aVar);
        if (this.a == null || this.e.size() < 2) {
            new Thread(new FutureTask(new Runnable() { // from class: com.unicom.online.account.kernel.af.3
                @Override // java.lang.Runnable
                public final void run() {
                    final af afVar = af.this;
                    try {
                        try {
                            afVar.b = (ConnectivityManager) context.getSystemService("connectivity");
                            NetworkRequest.Builder builder = new NetworkRequest.Builder();
                            builder.addTransportType(0);
                            builder.addCapability(12);
                            NetworkRequest networkRequestBuild = builder.build();
                            afVar.a = new ConnectivityManager.NetworkCallback() { // from class: com.unicom.online.account.kernel.af.1
                                @Override // android.net.ConnectivityManager.NetworkCallback
                                public final void onAvailable(Network network) {
                                    super.onAvailable(network);
                                    aj.a("Network onAvailable");
                                    af.this.d = network;
                                    af.this.a(true, network);
                                    try {
                                        String extraInfo = af.this.b.getNetworkInfo(af.this.d).getExtraInfo();
                                        if (TextUtils.isEmpty(extraInfo)) {
                                            return;
                                        }
                                        ak.d(extraInfo);
                                    } catch (Exception e) {
                                        aj.a(e);
                                    }
                                }

                                @Override // android.net.ConnectivityManager.NetworkCallback
                                public final void onLost(Network network) {
                                    super.onLost(network);
                                    aj.a("Network onLost");
                                    af.this.b();
                                }

                                @Override // android.net.ConnectivityManager.NetworkCallback
                                public final void onUnavailable() {
                                    super.onUnavailable();
                                    aj.a("Network onUnavailable");
                                    af.this.a(false, (Network) null);
                                    af.this.b();
                                }
                            };
                            int iG = ak.g();
                            if (iG > 1) {
                                iG--;
                            }
                            int i = iG * 1000;
                            Log.e("forceMobileConnection", Thread.currentThread().getName());
                            if (Build.VERSION.SDK_INT >= 26) {
                                afVar.b.requestNetwork(networkRequestBuild, afVar.a, i);
                                return;
                            }
                            afVar.c = new Timer();
                            afVar.c.schedule(new TimerTask() { // from class: com.unicom.online.account.kernel.af.2
                                @Override // java.util.TimerTask, java.lang.Runnable
                                public final void run() {
                                    af.this.a(false, (Network) null);
                                }
                            }, i);
                            afVar.b.requestNetwork(networkRequestBuild, afVar.a);
                        } catch (Exception e) {
                            aj.a(e);
                            afVar.a(false, (Network) null);
                        }
                    } catch (Exception e2) {
                        aj.a(e2);
                        afVar.a(false, (Network) null);
                    }
                }
            }, new ao())).start();
        }
    }

    public final void a(Context context, a aVar) {
        if (Build.VERSION.SDK_INT >= 21) {
            b(context, aVar);
        } else {
            aVar.a(true, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized void a(boolean z, Network network) {
        try {
            if (this.c != null) {
                this.c.cancel();
                this.c = null;
            }
            Iterator<a> it = this.e.iterator();
            while (it.hasNext()) {
                it.next().a(z, network);
            }
            this.e.clear();
        } catch (Exception e) {
            aj.a(e);
        }
    }

    public final synchronized void b() {
        try {
            if (this.c != null) {
                this.c.cancel();
                this.c = null;
            }
            if (Build.VERSION.SDK_INT >= 21 && this.b != null && this.a != null) {
                this.b.unregisterNetworkCallback(this.a);
            }
            this.b = null;
            this.a = null;
            this.d = null;
            this.e.clear();
        } catch (Exception e) {
            aj.a(e);
        }
    }
}