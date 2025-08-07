package cn.fly.mcl.tcp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.ab;
import cn.fly.commons.n;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.UIHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class b {
    private static volatile b a;
    private ScheduledExecutorService d;
    private ScheduledFuture e;
    private int c = -1;
    private Context b = FlySDK.getContext();

    public static b a() {
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b();
                }
            }
        }
        return a;
    }

    private b() {
        C0041r.a(d(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.d = Executors.newSingleThreadScheduledExecutor();
    }

    public void b() {
        try {
            c();
            this.e = this.d.scheduleWithFixedDelay(new Runnable() { // from class: cn.fly.mcl.tcp.b.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (!h.b().c() || !h.b().a(2000, 0)) {
                            b.this.a(new cn.fly.tools.utils.d<Void>() { // from class: cn.fly.mcl.tcp.b.1.1
                                @Override // cn.fly.tools.utils.d
                                public void a(Void r1) {
                                }
                            });
                        } else {
                            cn.fly.mcl.c.b.a().b("TP HB tcp send ping success");
                        }
                    } catch (Throwable th) {
                    }
                }
            }, 0L, h.b().e, TimeUnit.SECONDS);
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a("TP HB timer error", th);
        }
    }

    public boolean c() {
        boolean zCancel = false;
        try {
            if (this.e == null) {
                return false;
            }
            zCancel = this.e.cancel(true);
            cn.fly.mcl.c.b.a().b("TP HB cancel: " + zCancel);
            return zCancel;
        } catch (Throwable th) {
            cn.fly.mcl.c.b.a().a(th);
            return zCancel;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        cn.fly.mcl.c.b.a().b("TP HB onNetworkChanged: " + i + ", last: " + this.c);
        if (this.c == -1) {
            this.c = i;
            return;
        }
        this.c = i;
        if (this.c != 0) {
            if (h.b().c()) {
                cn.fly.mcl.c.b.a().b("TP HB tcp status: true");
            } else {
                UIHandler.sendEmptyMessageDelayed(0, 200L, new Handler.Callback() { // from class: cn.fly.mcl.tcp.b.2
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        cn.fly.mcl.b.a.a.execute(new Runnable() { // from class: cn.fly.mcl.tcp.b.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    if (!h.b().c()) {
                                        if (!h.b().d()) {
                                            h.b().f();
                                        }
                                        cn.fly.mcl.c.b.a().b("TP HB reg tcp");
                                        h.b().a(new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mcl.tcp.b.2.1.1
                                            @Override // cn.fly.tools.utils.d
                                            public void a(Boolean bool) {
                                            }
                                        });
                                    }
                                } catch (Throwable th) {
                                }
                            }
                        });
                        return false;
                    }
                });
            }
        }
    }

    /* renamed from: cn.fly.mcl.tcp.b$3, reason: invalid class name */
    class AnonymousClass3 extends BroadcastReceiver {
        AnonymousClass3() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(final Context context, final Intent intent) {
            try {
                ab.a.execute(new Runnable() { // from class: cn.fly.mcl.tcp.b.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                DH.requester(context).getDetailNetworkTypeForStatic().request(new DH.DHResponder() { // from class: cn.fly.mcl.tcp.b.3.1.1
                                    @Override // cn.fly.tools.utils.DH.DHResponder
                                    public void onResponse(DH.DHResponse dHResponse) {
                                        int i;
                                        String detailNetworkTypeForStatic = dHResponse.getDetailNetworkTypeForStatic();
                                        cn.fly.mcl.c.b.a().b("TP HB receive network: " + detailNetworkTypeForStatic);
                                        if (n.a("004.debgcdbg").equalsIgnoreCase(detailNetworkTypeForStatic)) {
                                            i = 1;
                                        } else if (n.a("0026fggb").equalsIgnoreCase(detailNetworkTypeForStatic)) {
                                            i = 5;
                                        } else if (n.a("002Nfjgb").equalsIgnoreCase(detailNetworkTypeForStatic)) {
                                            i = 4;
                                        } else if (n.a("002Ahdgb").equalsIgnoreCase(detailNetworkTypeForStatic)) {
                                            i = 3;
                                        } else if (!n.a("002 fcgb").equalsIgnoreCase(detailNetworkTypeForStatic)) {
                                            i = 0;
                                        } else {
                                            i = 2;
                                        }
                                        b.this.a(i);
                                    }
                                });
                            }
                        } catch (Throwable th) {
                            cn.fly.mcl.c.b.a().a(th);
                        }
                    }
                });
            } catch (Throwable th) {
                cn.fly.mcl.c.b.a().a(th);
            }
        }
    }

    private BroadcastReceiver d() {
        return new AnonymousClass3();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final cn.fly.tools.utils.d<Void> dVar) {
        if (!h.b().d()) {
            h.b().f();
        }
        h.b().a(new cn.fly.tools.utils.d<Boolean>() { // from class: cn.fly.mcl.tcp.b.4
            @Override // cn.fly.tools.utils.d
            public void a(Boolean bool) {
                if (dVar != null) {
                    dVar.a(null);
                }
            }
        });
    }
}