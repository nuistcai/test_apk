package cn.fly.verify;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.UIHandler;
import cn.fly.verify.aj;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.util.k;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class x {
    private static volatile x a = null;
    private AtomicBoolean b;
    private volatile boolean c = true;
    private w d;
    private u e;
    private cn.fly.verify.util.m f;
    private cn.fly.verify.util.m g;

    private x() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(a aVar, w wVar, d<a> dVar, u uVar) {
        cn.fly.verify.util.j.b(true);
        i.a().a(aVar);
        if (dVar != null) {
            dVar.a((d<a>) aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final d<a> dVar, final u uVar, final w wVar, final int[] iArr) {
        wVar.a(new d<a>() { // from class: cn.fly.verify.x.2
            @Override // cn.fly.verify.d
            public void a(a aVar) {
                if (x.this.b.get()) {
                    return;
                }
                x.this.b.set(true);
                i.a().a(aVar);
                x.this.a(aVar, wVar, (d<a>) dVar, uVar);
                t tVarB = uVar.b(wVar.i, wVar.b);
                tVarB.b(201);
                uVar.a(tVarB);
                uVar.b();
            }

            @Override // cn.fly.verify.d
            public void a(VerifyException verifyException) {
                iArr[0] = r4[0] - 1;
                if (iArr[0] == 0) {
                    VerifyException verifyException2 = new VerifyException(m.C_UNSUPPORTED_OPERATOR);
                    verifyException2.setExtraDesc(m.INNER_UNKNOWN_OPERATOR_TRIED.b());
                    VerifyException verifyException3 = new VerifyException(m.INNER_UNKNOWN_OPERATOR_TRIED);
                    verifyException3.setExtraDesc(cn.fly.verify.util.e.b());
                    uVar.a(verifyException2, verifyException3);
                    x.this.a(m.C_UNSUPPORTED_OPERATOR, wVar, (d<a>) dVar);
                }
            }
        });
    }

    private void a(final d<VerifyResult> dVar, final w wVar, final u uVar) {
        v.a("doVerifyFromCarrier");
        if (wVar != null) {
            wVar.d(new d<VerifyResult>() { // from class: cn.fly.verify.x.11
                @Override // cn.fly.verify.d
                public void a(VerifyException verifyException) {
                    v.a("doVerifyFromCarrier failed: " + verifyException.toString());
                    m mVarA = uVar != null ? uVar.a(wVar.i, wVar.b, verifyException) : null;
                    VerifyException verifyException2 = mVarA != null ? new VerifyException(mVarA) : new VerifyException(m.C_VERIFY_CATCH);
                    verifyException2.setInner(verifyException);
                    i.a().a((a) null);
                    if (dVar != null) {
                        dVar.a(verifyException2);
                    }
                }

                @Override // cn.fly.verify.d
                public void a(VerifyResult verifyResult) {
                    if (uVar != null) {
                        uVar.a(wVar.i, wVar.b);
                    }
                    i.a().a((a) null);
                    if (dVar != null) {
                        dVar.a((d) verifyResult);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(m mVar, w wVar, d<a> dVar) {
        if (dVar != null) {
            dVar.a(mVar != null ? new VerifyException(mVar) : new VerifyException(m.C_PREVERIFY_CATCH));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final u uVar, final d<a> dVar) {
        cn.fly.verify.util.e.a(new k.a() { // from class: cn.fly.verify.x.4
            @Override // cn.fly.verify.util.k.a
            public void a() throws InterruptedException {
                aj ajVarA = e.a(uVar);
                if (ajVarA == null) {
                    n.a(2);
                    ajVarA = e.a(uVar);
                    if (ajVarA == null) {
                        final VerifyException verifyException = new VerifyException(m.C_CONFIG_ERROR);
                        verifyException.setExtraDesc("has retry");
                        VerifyException verifyException2 = new VerifyException(m.INNER_NO_INIT_RETRY);
                        if (uVar != null) {
                            uVar.a(verifyException, verifyException2);
                        }
                        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.4.1
                            @Override // android.os.Handler.Callback
                            public boolean handleMessage(Message message) {
                                if (dVar == null) {
                                    return false;
                                }
                                dVar.a(verifyException);
                                return false;
                            }
                        });
                        x.this.e(true).a(verifyException2);
                        return;
                    }
                }
                int iA = cn.fly.verify.util.d.a();
                if (uVar != null) {
                    uVar.a(cn.fly.verify.util.l.a(iA), (String) null, "get_cc", String.valueOf(x.this.f()));
                }
                if (iA == 10 && !e.c()) {
                    final VerifyException verifyException3 = new VerifyException(m.C_UNSUPPORTED_OPERATOR);
                    verifyException3.setExtraDesc("unknown operator");
                    VerifyException verifyException4 = new VerifyException(m.INNER_UNKNOWN_OPERATOR);
                    if (uVar != null) {
                        uVar.a(verifyException3, verifyException4);
                    }
                    UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.4.2
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            if (dVar == null) {
                                return false;
                            }
                            dVar.a(verifyException3);
                            return false;
                        }
                    });
                    x.this.e(true).a(verifyException4);
                    return;
                }
                if (iA == 10) {
                    int[] iArr = {1, 3, 2};
                    int[] iArr2 = {3};
                    x.this.b = new AtomicBoolean();
                    for (int i = 0; i < 3; i++) {
                        int i2 = iArr[i];
                        aj.a aVarA = ajVarA.a(i2);
                        if (aVarA != null) {
                            w wVarA = h.a(i2, aVarA.b, aVarA.c, aVarA.d, aVarA.e, aVarA.f, uVar);
                            wVarA.a(true);
                            wVarA.i = "UNKNOWN";
                            x.this.a((d<a>) dVar, uVar, wVarA, iArr2);
                        }
                    }
                    uVar.a((String) null, (String) null, "unknown_try");
                    return;
                }
                aj.a aVarA2 = ajVarA.a(iA);
                if (aVarA2 == null) {
                    final VerifyException verifyException5 = new VerifyException(m.C_UNSUPPORTED_OPERATOR);
                    verifyException5.setExtraDesc("no operator config");
                    VerifyException verifyException6 = new VerifyException(m.INNER_NO_OPERATOR_CONFIG);
                    if (uVar != null) {
                        uVar.a(verifyException5, verifyException6, cn.fly.verify.util.l.a(iA));
                    }
                    UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.4.3
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            if (dVar == null) {
                                return false;
                            }
                            dVar.a(verifyException5);
                            return false;
                        }
                    });
                    x.this.e(true).a(verifyException6);
                    return;
                }
                if (TextUtils.isEmpty(aVarA2.b) || TextUtils.isEmpty(aVarA2.c)) {
                    final VerifyException verifyException7 = new VerifyException(m.C_APPID_NULL);
                    if (uVar != null) {
                        uVar.a(verifyException7, verifyException7, cn.fly.verify.util.l.a(iA));
                    }
                    UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.4.4
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            if (dVar == null) {
                                return false;
                            }
                            dVar.a(verifyException7);
                            return false;
                        }
                    });
                    x.this.e(true).a(verifyException7);
                    return;
                }
                if (uVar != null) {
                    uVar.a(aVarA2.d);
                    uVar.a(cn.fly.verify.util.l.a(iA), aVarA2.b, "get_ci");
                }
                x.this.d = h.a(iA, aVarA2.b, aVarA2.c, aVarA2.d, aVarA2.e, aVarA2.f, uVar);
                x.this.b(dVar, x.this.d, uVar);
            }

            @Override // cn.fly.verify.util.k.a
            public void a(Throwable th) {
                final VerifyException verifyException = new VerifyException(m.C_PREVERIFY_CATCH);
                VerifyException verifyException2 = new VerifyException(m.C_PREVERIFY_CATCH.a(), cn.fly.verify.util.l.a(th));
                if (uVar != null) {
                    uVar.a(verifyException, verifyException2);
                }
                UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.4.5
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        dVar.a(verifyException);
                        return false;
                    }
                });
                x.this.e(true).a(verifyException2);
            }
        }, uVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public boolean a(u uVar, final d dVar, boolean z) {
        final a aVar;
        final VerifyResult verifyResult;
        final VerifyException verifyException;
        Object objA = e(z).a();
        if (objA == null) {
            uVar.a((Integer) 0);
            return false;
        }
        uVar.a((Integer) 1);
        uVar.a((String) null, (String) null, "end_wait");
        u uVarC = e(z).c();
        if (uVarC != null) {
            uVar.c(uVarC.c());
            uVar.a(uVarC.d().intValue());
        }
        if (objA instanceof VerifyException) {
            verifyException = new VerifyException(uVar.a(cn.fly.verify.util.l.i(), (String) null, (VerifyException) objA));
            aVar = null;
            verifyResult = null;
        } else {
            if (objA instanceof a) {
                a aVar2 = (a) objA;
                uVar.a(aVar2.h(), aVar2.b());
                aVar = aVar2;
                verifyResult = null;
            } else if (objA instanceof VerifyResult) {
                VerifyResult verifyResult2 = (VerifyResult) objA;
                uVar.a(verifyResult2.getOperator(), (String) null);
                verifyResult = verifyResult2;
                aVar = null;
                verifyException = 0;
            } else {
                aVar = null;
                verifyResult = null;
            }
            verifyException = verifyResult;
        }
        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.5
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                d dVar2;
                Object obj;
                if (dVar == null) {
                    return false;
                }
                if (aVar != null) {
                    dVar2 = dVar;
                    obj = aVar;
                } else {
                    if (verifyResult == null) {
                        if (verifyException == null) {
                            return false;
                        }
                        dVar.a(verifyException);
                        return false;
                    }
                    dVar2 = dVar;
                    obj = verifyResult;
                }
                dVar2.a((d) obj);
                return false;
            }
        });
        return true;
    }

    public static x b() {
        if (a == null) {
            synchronized (x.class) {
                if (a == null) {
                    a = new x();
                }
            }
        }
        return a;
    }

    private void b(final d<VerifyResult> dVar, u uVar) throws InterruptedException {
        final VerifyException verifyExceptionA = b.a();
        if (verifyExceptionA != null) {
            UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.6
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    dVar.a(verifyExceptionA);
                    return false;
                }
            });
            b().e(false).a(verifyExceptionA);
            return;
        }
        if (i.a().b() != null) {
            String strH = i.a().b().h();
            if (!TextUtils.isEmpty(strH)) {
                String strC = cn.fly.verify.util.l.c();
                if (!strH.equals(strC) && !"UNKNOWN".equals(strC)) {
                    i.a().a((a) null);
                }
            }
        }
        aj ajVarA = e.a(uVar);
        if (ajVarA == null) {
            n.a(2);
            ajVarA = e.a(uVar);
            if (ajVarA == null) {
                final VerifyException verifyException = new VerifyException(m.C_CONFIG_ERROR);
                verifyException.setExtraDesc("has retry");
                VerifyException verifyException2 = new VerifyException(m.INNER_NO_INIT_RETRY);
                if (uVar != null) {
                    uVar.a(verifyException, verifyException2);
                }
                UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.7
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        dVar.a(verifyException);
                        return false;
                    }
                });
                b().e(false).a(verifyException2);
                return;
            }
        }
        int iA = cn.fly.verify.util.d.a();
        if (iA == 10 && i.a().b() != null) {
            iA = cn.fly.verify.util.d.a(i.a().b().h());
        }
        int i = iA;
        if (i == 10) {
            final VerifyException verifyException3 = new VerifyException(m.C_UNSUPPORTED_OPERATOR);
            verifyException3.setExtraDesc("unknown operator");
            VerifyException verifyException4 = new VerifyException(m.INNER_UNKNOWN_OPERATOR);
            if (uVar != null) {
                uVar.a(verifyException3, verifyException4);
            }
            UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.8
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    dVar.a(verifyException3);
                    return false;
                }
            });
            b().e(false).a(verifyException4);
            return;
        }
        if (uVar != null) {
            uVar.a(cn.fly.verify.util.l.a(i), (String) null, "get_cc");
        }
        aj.a aVarA = ajVarA.a(i);
        if (aVarA == null) {
            final VerifyException verifyException5 = new VerifyException(m.C_UNSUPPORTED_OPERATOR);
            verifyException5.setExtraDesc("no operator config");
            VerifyException verifyException6 = new VerifyException(m.INNER_NO_OPERATOR_CONFIG);
            if (uVar != null) {
                uVar.a(verifyException5, verifyException6, cn.fly.verify.util.l.a(i));
            }
            UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.9
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    dVar.a(verifyException5);
                    return false;
                }
            });
            b().e(false).a(verifyException6);
            return;
        }
        if (TextUtils.isEmpty(aVarA.b) || TextUtils.isEmpty(aVarA.c)) {
            final VerifyException verifyException7 = new VerifyException(m.C_APPID_NULL);
            if (uVar != null) {
                uVar.a(verifyException7, verifyException7, cn.fly.verify.util.l.a(i));
            }
            UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.10
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    dVar.a(verifyException7);
                    return false;
                }
            });
            b().e(false).a(verifyException7);
            return;
        }
        if (uVar != null) {
            uVar.a(aVarA.d);
            uVar.a(cn.fly.verify.util.l.a(i), aVarA.b, "get_ci");
        }
        this.d = h.a(i, aVarA.b, aVarA.c, aVarA.d, aVarA.e, aVarA.f, uVar);
        a(dVar, this.d, uVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final d<a> dVar, final w wVar, final u uVar) {
        v.a("doPreVerifyFromCarrier");
        if (wVar != null) {
            wVar.a(new d<a>() { // from class: cn.fly.verify.x.3
                @Override // cn.fly.verify.d
                public void a(a aVar) {
                    x.this.a(aVar, wVar, (d<a>) dVar, uVar);
                    if (uVar == null || uVar.a() == null || uVar.a() != g.PREVERIFY) {
                        return;
                    }
                    uVar.a(wVar.i, wVar.b);
                }

                @Override // cn.fly.verify.d
                public void a(VerifyException verifyException) {
                    x.this.a((uVar == null || uVar.a() == null || uVar.a() != g.PREVERIFY) ? null : uVar.a(wVar.i, wVar.b, verifyException), wVar, (d<a>) dVar);
                }
            });
        }
    }

    private List<w> e() {
        w wVarD;
        ArrayList arrayList = new ArrayList();
        String strJ = i.a().j();
        if (!TextUtils.isEmpty(strJ) && !"UNKNOWN".equals(strJ)) {
            if (this.d != null) {
                wVarD = this.d;
            }
            return arrayList;
        }
        arrayList.add(ab.d());
        arrayList.add(ac.d());
        wVarD = ad.d();
        arrayList.add(wVarD);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int f() {
        boolean zC = cn.fly.verify.util.l.c(FlySDK.getContext());
        return (zC ? 10 : 0) + (cn.fly.verify.util.l.g() > -1 ? 1 : 0);
    }

    public u a() {
        return this.e;
    }

    public void a(final d<a> dVar) {
        final u uVar = new u(g.PREVERIFY);
        cn.fly.verify.util.k.a.execute(new k.a() { // from class: cn.fly.verify.x.1
            @Override // cn.fly.verify.util.k.a
            public void a() {
                Handler.Callback callback;
                if (uVar != null) {
                    uVar.a((String) null, (String) null, "start");
                }
                final VerifyException verifyExceptionA = b.a();
                if (verifyExceptionA != null) {
                    if (uVar != null) {
                        uVar.a(verifyExceptionA, verifyExceptionA);
                    }
                    callback = new Handler.Callback() { // from class: cn.fly.verify.x.1.1
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            if (dVar == null) {
                                return false;
                            }
                            dVar.a(verifyExceptionA);
                            return false;
                        }
                    };
                } else {
                    if (DH.SyncMtd.isInMainProcess()) {
                        int iG = e.g();
                        v.a("preVerifyWay " + iG);
                        d<a> dVar2 = new d<a>() { // from class: cn.fly.verify.x.1.3
                            @Override // cn.fly.verify.d
                            public void a(a aVar) {
                                if (dVar != null) {
                                    dVar.a((d) aVar);
                                }
                                x.this.e(true).a(uVar);
                                x.this.e(true).a(aVar);
                            }

                            @Override // cn.fly.verify.d
                            public void a(VerifyException verifyException) {
                                if (dVar != null) {
                                    dVar.a(verifyException);
                                }
                                x.this.e(true).a(uVar);
                                x.this.e(true).a(verifyException);
                            }
                        };
                        if (iG == 0) {
                            uVar.a((Integer) 0);
                        } else if (iG == 1) {
                            if (x.this.a(uVar, (d) dVar2, true)) {
                                return;
                            }
                        } else {
                            if (iG != 2) {
                                return;
                            }
                            Object objA = x.this.e(true).a();
                            if (objA != null) {
                                uVar.a((String) null, (String) null, "end_wait");
                            }
                            uVar.a(Integer.valueOf(objA != null ? 2 : 0));
                        }
                        x.this.a(uVar, dVar2);
                        return;
                    }
                    v.a("not main process");
                    VerifyException verifyException = new VerifyException(m.INNER_OTHER_EXCEPTION_ERR.a(), "not main process");
                    final VerifyException verifyException2 = new VerifyException(m.C_PREVERIFY_CATCH);
                    uVar.a(verifyException2, verifyException);
                    callback = new Handler.Callback() { // from class: cn.fly.verify.x.1.2
                        @Override // android.os.Handler.Callback
                        public boolean handleMessage(Message message) {
                            if (dVar == null) {
                                return false;
                            }
                            dVar.a(verifyException2);
                            return false;
                        }
                    };
                }
                UIHandler.sendEmptyMessage(0, callback);
            }

            @Override // cn.fly.verify.util.k.a
            public void a(Throwable th) {
                final VerifyException verifyException = new VerifyException(m.C_PREVERIFY_CATCH);
                VerifyException verifyException2 = new VerifyException(m.C_PREVERIFY_CATCH.a(), cn.fly.verify.util.l.a(th));
                if (uVar != null) {
                    uVar.a(verifyException, verifyException2);
                }
                UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.x.1.4
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        dVar.a(verifyException);
                        return false;
                    }
                });
                x.this.e(true).a(uVar);
                x.this.e(true).a(verifyException2);
            }
        });
    }

    public void a(d<VerifyResult> dVar, u uVar) {
        v.a("Start verify");
        b(dVar, uVar);
    }

    public void a(u uVar) {
        this.e = uVar;
    }

    public void a(boolean z) {
        this.c = z;
    }

    public void b(boolean z) {
        v.a("autoFinishOAuthPage" + z);
        i.a().d(z);
    }

    public void c() {
        v.a("Start finishOAuthPage.");
        List<w> listE = e();
        if (listE.isEmpty()) {
            return;
        }
        Iterator<w> it = listE.iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }

    public void c(boolean z) {
        v.a("otherLoginAutoFinishOAuthPage" + z);
        i.a().e(z);
    }

    public void d() {
        List<w> listE = e();
        if (listE.isEmpty()) {
            return;
        }
        Iterator<w> it = listE.iterator();
        while (it.hasNext()) {
            it.next().c();
        }
    }

    public void d(boolean z) {
        v.a("DebugMode is " + z);
        i.a().f(z);
    }

    public cn.fly.verify.util.m e(boolean z) {
        if (z) {
            if (this.f == null) {
                this.f = new cn.fly.verify.util.m("preVerify");
            }
            return this.f;
        }
        if (this.g == null) {
            this.g = new cn.fly.verify.util.m("verify");
        }
        return this.g;
    }
}