package cn.fly.verify;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import cn.fly.FlySDK;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.UIHandler;
import cn.fly.verify.OAuthPageEventCallback;
import cn.fly.verify.ResultCallback;
import cn.fly.verify.VerifyResultCallback;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.datatype.UiSettings;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.ui.component.LoginAdapter;
import cn.fly.verify.util.k;

/* loaded from: classes.dex */
public class l {
    private static volatile l a = null;
    private u b;

    /* renamed from: cn.fly.verify.l$5, reason: invalid class name */
    class AnonymousClass5 extends k.a {
        final /* synthetic */ VerifyCallback a;
        final /* synthetic */ PageCallback b;
        final /* synthetic */ u c;

        AnonymousClass5(VerifyCallback verifyCallback, PageCallback pageCallback, u uVar) {
            this.a = verifyCallback;
            this.b = pageCallback;
            this.c = uVar;
        }

        @Override // cn.fly.verify.util.k.a
        public void a() {
            if (this.a != null) {
                k.a().a(this.a);
            }
            if (this.b != null) {
                if (!cn.fly.verify.util.l.d()) {
                    UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.l.5.1
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            AnonymousClass5.this.b.pageCallback(m.C_AUTHPAGE_NO_NET.a(), cn.fly.verify.util.l.a("network_unexist", "network unopend"));
                            return false;
                        }
                    });
                }
                k.a().a(this.b);
            }
            v.a("verify" + i.a().g());
            if (i.a().g()) {
                d<VerifyResult> dVar = new d<VerifyResult>() { // from class: cn.fly.verify.l.5.3
                    @Override // cn.fly.verify.d
                    public void a(final VerifyException verifyException) {
                        v.a("verify error " + verifyException);
                        final VerifyException inner = verifyException.getInner();
                        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.l.5.3.2
                            @Override // android.os.Handler.Callback
                            public boolean handleMessage(Message message) {
                                if (AnonymousClass5.this.a == null) {
                                    return false;
                                }
                                verifyException.setInner(null);
                                cn.fly.verify.util.c.a(AnonymousClass5.this.b, AnonymousClass5.this.a, verifyException, inner);
                                return false;
                            }
                        });
                        x.b().e(false).a(AnonymousClass5.this.c);
                        x.b().e(false).a(inner);
                    }

                    @Override // cn.fly.verify.d
                    public void a(final VerifyResult verifyResult) {
                        v.a("verify success!");
                        cn.fly.verify.util.j.b(true);
                        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.l.5.3.1
                            @Override // android.os.Handler.Callback
                            public boolean handleMessage(Message message) {
                                if (AnonymousClass5.this.a.isCanceled()) {
                                    return false;
                                }
                                AnonymousClass5.this.a.onComplete(verifyResult);
                                return false;
                            }
                        });
                        x.b().e(false).a(AnonymousClass5.this.c);
                        x.b().e(false).a(verifyResult);
                    }
                };
                x.b().a(this.c);
                x.b().a(dVar, this.c);
            } else {
                VerifyException verifyException = new VerifyException(m.C_AUTPAGE_OPENED);
                if (this.c != null) {
                    this.c.a(verifyException, verifyException);
                }
                UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.l.5.2
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        AnonymousClass5.this.a.onFailure(new VerifyException(m.C_AUTPAGE_OPENED.a(), cn.fly.verify.util.l.a("oauthpage_opened", "oauthpage_opened")));
                        return false;
                    }
                });
                x.b().e(false).a(verifyException);
                v.a("auth page not finish ,ignore");
            }
        }

        @Override // cn.fly.verify.util.k.a
        public void a(Throwable th) {
            if (th != null) {
                final VerifyException verifyException = new VerifyException(m.C_VERIFY_CATCH);
                VerifyException verifyException2 = new VerifyException(m.INNER_OTHER_EXCEPTION_ERR.a(), cn.fly.verify.util.l.a(th));
                if (this.c != null) {
                    this.c.a(verifyException, verifyException2);
                }
                UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.l.5.4
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        v.a("verify error " + verifyException);
                        AnonymousClass5.this.a.onFailure(verifyException);
                        return false;
                    }
                });
                x.b().e(false).a(verifyException2);
            }
        }
    }

    private l() {
        e();
    }

    public static l a() {
        if (a == null) {
            synchronized (l.class) {
                if (a == null) {
                    a = new l();
                }
            }
        }
        return a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(u uVar, PageCallback pageCallback, VerifyCallback verifyCallback) {
        cn.fly.verify.util.e.a(new AnonymousClass5(verifyCallback, pageCallback, uVar), uVar);
    }

    private void e() {
        try {
            ((Application) FlySDK.getContext().getApplicationContext()).registerActivityLifecycleCallbacks(af.a(FlySDK.getContext()));
        } catch (Throwable th) {
            v.a(th, "registerActivityLifecycleCallbacks");
        }
    }

    public void a(int i) {
        if (i <= 0) {
            i = 4000;
        }
        i.a().a(i);
    }

    public void a(OAuthPageEventCallback oAuthPageEventCallback) {
        OAuthPageEventCallback.OAuthPageEventResultCallback oAuthPageEventResultCallback = new OAuthPageEventCallback.OAuthPageEventResultCallback();
        if (oAuthPageEventCallback != null) {
            oAuthPageEventCallback.initCallback(oAuthPageEventResultCallback);
        }
        k.a().a(new OAuthPageEventCallback.OAuthPageEventWrapper(oAuthPageEventResultCallback));
    }

    public void a(final OperationCallback operationCallback) {
        if (operationCallback == null) {
            return;
        }
        x.b().a(new d<a>() { // from class: cn.fly.verify.l.2
            @Override // cn.fly.verify.d
            public void a(a aVar) {
                cn.fly.verify.util.j.b(true);
                UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.l.2.1
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        if (operationCallback.isCanceled()) {
                            return false;
                        }
                        operationCallback.onComplete(null);
                        return false;
                    }
                });
            }

            @Override // cn.fly.verify.d
            public void a(final VerifyException verifyException) {
                v.a("preVerify error " + verifyException);
                UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.l.2.2
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        if (operationCallback.isCanceled()) {
                            return false;
                        }
                        operationCallback.onFailure(verifyException);
                        return false;
                    }
                });
            }
        });
    }

    public void a(final PageCallback pageCallback, final VerifyCallback verifyCallback) {
        if (verifyCallback == null) {
            return;
        }
        final u uVar = this.b != null ? this.b : new u(g.VERIFY);
        cn.fly.verify.util.k.a.execute(new k.a() { // from class: cn.fly.verify.l.4
            /* JADX WARN: Removed duplicated region for block: B:11:0x0054  */
            @Override // cn.fly.verify.util.k.a
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void a() {
                if (uVar != null) {
                    uVar.a((String) null, (String) null, "start");
                }
                if (!DH.SyncMtd.isInMainProcess()) {
                    v.a("not main process");
                    VerifyException verifyException = new VerifyException(m.INNER_OTHER_EXCEPTION_ERR.a(), "not main process");
                    final VerifyException verifyException2 = new VerifyException(m.C_VERIFY_CATCH);
                    uVar.a(verifyException2, verifyException);
                    UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.l.4.1
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            v.a("verify error " + verifyException2);
                            verifyCallback.onFailure(verifyException2);
                            return false;
                        }
                    });
                    return;
                }
                int iH = e.h();
                v.a("verifyWay " + iH);
                if (iH == 0) {
                    uVar.b(0);
                } else {
                    Object objB = x.b().e(true).b();
                    Object objA = x.b().e(false).a();
                    if (objB != null || objA != null) {
                        uVar.b(1);
                        uVar.a((String) null, (String) null, "end_wait");
                    }
                }
                l.this.a(uVar, pageCallback, verifyCallback);
            }

            @Override // cn.fly.verify.util.k.a
            public void a(Throwable th) {
                final VerifyException verifyException = new VerifyException(m.C_VERIFY_CATCH);
                VerifyException verifyException2 = new VerifyException(m.C_VERIFY_CATCH.a(), cn.fly.verify.util.l.a(th));
                if (uVar != null) {
                    uVar.a(verifyException, verifyException2);
                }
                UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.l.4.2
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        verifyCallback.onFailure(verifyException);
                        return false;
                    }
                });
                x.b().e(false).a(uVar);
                x.b().e(false).a(verifyException2);
            }
        });
    }

    public void a(ResultCallback<Void> resultCallback) {
        ResultCallback.b<Void> bVar = new ResultCallback.b<>();
        if (resultCallback != null) {
            resultCallback.initCallback(bVar);
        }
        final ResultCallback.a aVar = new ResultCallback.a(bVar);
        a(new OperationCallback() { // from class: cn.fly.verify.l.1
            @Override // cn.fly.verify.OperationCallback
            public void onComplete(Object obj) {
                if (aVar.a != null) {
                    aVar.a.a(null);
                }
            }

            @Override // cn.fly.verify.OperationCallback
            public void onFailure(VerifyException verifyException) {
                if (aVar.b != null) {
                    aVar.b.a(verifyException);
                }
            }
        });
    }

    public void a(VerifyCallback verifyCallback) {
        a(null, verifyCallback);
    }

    public void a(VerifyResultCallback verifyResultCallback) {
        VerifyResultCallback.c cVar = new VerifyResultCallback.c();
        if (verifyResultCallback != null) {
            verifyResultCallback.initCallback(cVar);
        }
        final VerifyResultCallback.d dVar = new VerifyResultCallback.d(cVar);
        a(new VerifyCallback() { // from class: cn.fly.verify.l.3
            @Override // cn.fly.verify.OperationCallback
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onComplete(VerifyResult verifyResult) {
                if (dVar.a != null) {
                    dVar.a.a(verifyResult);
                }
            }

            @Override // cn.fly.verify.OperationCallback
            public void onFailure(VerifyException verifyException) {
                if (dVar.b != null) {
                    dVar.b.a(verifyException);
                }
            }

            @Override // cn.fly.verify.VerifyCallback
            public void onOtherLogin() {
                if (dVar.c != null) {
                    dVar.c.a();
                }
            }

            @Override // cn.fly.verify.VerifyCallback
            public void onUserCanceled() {
                if (dVar.d != null) {
                    dVar.d.a();
                }
            }
        });
    }

    public void a(LandUiSettings landUiSettings) {
        v.a("Set customized LandUiSettings");
        i.a().a(landUiSettings);
    }

    public void a(UiSettings uiSettings) {
        v.a("Set customized uiSettings");
        i.a().a(uiSettings);
    }

    public void a(u uVar) {
        this.b = uVar;
    }

    public void a(Class<? extends LoginAdapter> cls) {
        i.a().a(cls);
    }

    public void a(String str) {
        i.a().a(str);
    }

    public void a(boolean z) {
        x.b().b(z);
    }

    public void b() {
        x.b().c();
        PageCallback pageCallbackJ = k.a().j();
        if (pageCallbackJ != null) {
            pageCallbackJ.pageCallback(6119151, cn.fly.verify.util.l.a("call_finish_method", " call finish method"));
        }
    }

    public void b(boolean z) {
        x.b().c(z);
    }

    public void c() {
        x.b().d();
    }

    public void c(boolean z) {
        x.b().d(z);
    }

    @Deprecated
    public boolean d() {
        return true;
    }
}