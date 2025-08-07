package cn.fly.mgs.a;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import cn.fly.commons.w;
import cn.fly.mgs.OnAppActiveListener;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.tools.utils.UIHandler;

/* loaded from: classes.dex */
public class g {
    private static OnAppActiveListener a;
    private static int b = 1;
    private static volatile boolean c = false;

    /* renamed from: cn.fly.mgs.a.g$1, reason: invalid class name */
    class AnonymousClass1 extends h {
        final /* synthetic */ Intent a;
        final /* synthetic */ boolean b;
        final /* synthetic */ Context c;

        AnonymousClass1(Intent intent, boolean z, Context context) {
            this.a = intent;
            this.b = z;
            this.c = context;
        }

        /* JADX WARN: Type inference failed for: r0v6, types: [cn.fly.mgs.a.g$1$2] */
        @Override // cn.fly.tools.utils.j
        protected void a() throws Throwable {
            try {
                e.a().a("[GD][R]intent: " + this.a);
                final String stringExtra = this.a.getStringExtra(w.b("004]cb>chc"));
                final String stringExtra2 = this.a.getStringExtra("workId");
                final String stringExtra3 = this.a.getStringExtra(w.b("006cii_dg:eJdb"));
                final String stringExtra4 = this.a.getStringExtra(w.b("003i1dgdi"));
                final String stringExtra5 = this.a.getStringExtra(w.b("0046cbcfchcb"));
                final String stringExtra6 = this.a.getStringExtra("guardId");
                int intExtra = this.a.getIntExtra("acServiceType", 0);
                int intExtra2 = this.a.getIntExtra("busType", 0);
                e.a().a("[GD][R]acSvcType: " + intExtra + ", busType: " + intExtra2 + ", fmAct: " + this.b + ", uld: " + g.c);
                if (intExtra == 1 || intExtra2 == 2001) {
                    int unused = g.b = 3;
                } else if (intExtra == 2 || intExtra2 == 2002) {
                    int unused2 = g.b = 4;
                } else if ("selfpush000".equals(stringExtra2) && "selfpush000".equals(stringExtra6)) {
                    int unused3 = g.b = 100;
                }
                if (!this.b && g.b == 1) {
                    int unused4 = g.b = 2;
                }
                if (!TextUtils.isEmpty(stringExtra)) {
                    UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.mgs.a.g.1.1
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            ReflectHelper.invokeStaticMethodNoThrow(ReflectHelper.importClassNoThrow(w.b("023b!cjceckcecjeeckHiEcfehKgLehcbdgckgbcjeefkcfehLg"), null), "addGuardMessage", null, stringExtra);
                            return false;
                        }
                    });
                }
                if (!g.c) {
                    boolean unused5 = g.c = true;
                    new h() { // from class: cn.fly.mgs.a.g.1.2
                        @Override // cn.fly.tools.utils.j
                        public void a() throws Throwable {
                            sleep(1000L);
                            d.a(stringExtra5, stringExtra3, stringExtra4, stringExtra6, cn.fly.mcl.b.a.a(), stringExtra2, g.b);
                        }
                    }.start();
                    UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.mgs.a.g.1.3
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            try {
                                e.a().a("Pd succ. Callback now");
                                DH.requester(AnonymousClass1.this.c).getAInfoForPkg(AnonymousClass1.this.c.getPackageName(), 128).request(new DH.DHResponder() { // from class: cn.fly.mgs.a.g.1.3.1
                                    @Override // cn.fly.tools.utils.DH.DHResponder
                                    public void onResponse(DH.DHResponse dHResponse) {
                                        ApplicationInfo aInfoForPkg = dHResponse.getAInfoForPkg(new int[0]);
                                        if (aInfoForPkg != null) {
                                            String string = aInfoForPkg.metaData.getString("guard_listener");
                                            if (!TextUtils.isEmpty(string)) {
                                                try {
                                                    e.a().a("Pd succ. Callback[mnf] " + string);
                                                    ((OnAppActiveListener) Class.forName(string).newInstance()).onAppActive(AnonymousClass1.this.c, g.b);
                                                } catch (Throwable th) {
                                                    e.a().b(th);
                                                }
                                            }
                                        }
                                        if (g.a != null) {
                                            e.a().a("Pd succ. Callback[setter]");
                                            g.a.onAppActive(AnonymousClass1.this.c, g.b);
                                        }
                                    }
                                });
                                return false;
                            } catch (Throwable th) {
                                e.a().a(th);
                                if (g.a != null) {
                                    g.a.onAppActive(AnonymousClass1.this.c, g.b);
                                    return false;
                                }
                                return false;
                            }
                        }
                    });
                }
            } catch (Throwable th) {
                e.a().a(th);
            }
        }
    }

    public static void a(Context context, Intent intent, boolean z) {
        new AnonymousClass1(intent, z, context).start();
    }

    public static void a(OnAppActiveListener onAppActiveListener) {
        a = onAppActiveListener;
    }
}