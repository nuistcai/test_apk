package cn.fly.verify;

import android.os.SystemClock;
import android.text.TextUtils;
import cn.fly.tools.utils.ExecutorDispatcher;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.util.e;
import com.unicom.online.account.shield.ResultListener;
import com.unicom.online.account.shield.UniAccountHelper;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ad extends w {
    private static ad n;
    public int m = 10000;
    private d<VerifyResult> o;

    private ad() {
        this.i = "CUCC";
        try {
            if (i.a().o()) {
                UniAccountHelper.getInstance().setLogEnable(true);
            }
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
        v.a("Initialize CuccOneKeyImpl");
    }

    public static ad d() {
        if (n == null) {
            synchronized (ad.class) {
                if (n == null) {
                    n = new ad();
                }
            }
        }
        return n;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(final d<VerifyResult> dVar) {
        if (this.h != null) {
            this.h.a("CUCC", this.b, "open_authpage_start");
        }
        ae.a().a(this.a, 1, i.a().m(), i.a().n(), new d<VerifyResult>() { // from class: cn.fly.verify.ad.3
            @Override // cn.fly.verify.d
            public void a(VerifyException verifyException) {
                dVar.a(verifyException);
                cn.fly.verify.util.g.b();
                UniAccountHelper.getInstance().clearCache();
            }

            @Override // cn.fly.verify.d
            public void a(VerifyResult verifyResult) {
                dVar.a((d) verifyResult);
                cn.fly.verify.util.g.b();
                UniAccountHelper.getInstance().clearCache();
            }
        });
    }

    public ad a(String str, String str2) {
        if (TextUtils.isEmpty(this.b)) {
            v.a("Init cucc SDK appid: " + str + ", secret: " + str2);
            super.a(str);
            this.b = str;
            this.c = str2;
            UniAccountHelper.getInstance().init(this.a, this.b);
        }
        return n;
    }

    @Override // cn.fly.verify.w
    public void b() {
        ae.a().b();
    }

    @Override // cn.fly.verify.w
    public void c() {
        v.a("refreshOAuthPage");
        ae.a().c();
    }

    @Override // cn.fly.verify.w
    public void c(final d<a> dVar) {
        v.a(getClass() + "preGetAccessToken ");
        try {
            a aVarB = i.a().b();
            if (aVarB != null && !aVarB.f(this.b)) {
                UniAccountHelper.getInstance().clearCache();
            }
            if (this.h != null) {
                this.h.a("CUCC", this.b, "switch_s");
            }
            new cn.fly.verify.util.g().a();
            final long jUptimeMillis = SystemClock.uptimeMillis();
            if (this.h != null) {
                this.h.a("CUCC", this.b, "switch_e");
                this.h.a("CUCC", this.b, "request_start");
            }
            this.j = cn.fly.verify.util.l.a(this.a);
            cn.fly.verify.util.e.b((e.a<String>) null);
            UniAccountHelper.getInstance().cuGetToken(a(), new ResultListener() { // from class: cn.fly.verify.ad.1
                @Override // com.unicom.online.account.shield.ResultListener
                public void onResult(final String str) {
                    cn.fly.verify.util.l.a(new ExecutorDispatcher.SafeRunnable() { // from class: cn.fly.verify.ad.1.1
                        @Override // cn.fly.tools.utils.ExecutorDispatcher.SafeRunnable
                        public void handleException(Throwable th) {
                            i.a().a((a) null);
                            dVar.a(new VerifyException(m.INNER_OTHER_EXCEPTION_ERR.a(), cn.fly.verify.util.l.a(th)));
                        }

                        @Override // cn.fly.tools.utils.ExecutorDispatcher.SafeRunnable
                        public void safeRun() {
                            int i;
                            try {
                                if (ad.this.h != null) {
                                    ad.this.h.a("CUCC", ad.this.b, "request_end", String.valueOf(SystemClock.uptimeMillis() - jUptimeMillis));
                                }
                                v.a(str);
                                JSONObject jSONObject = new JSONObject(str);
                                String strOptString = jSONObject.optString("resultCode");
                                boolean zEquals = "100".equals(strOptString);
                                if (zEquals) {
                                    String strOptString2 = jSONObject.optString("resultData");
                                    zEquals = !TextUtils.isEmpty(strOptString2);
                                    if (zEquals) {
                                        JSONObject jSONObject2 = new JSONObject(strOptString2);
                                        String strOptString3 = jSONObject2.optString("fakeMobile");
                                        long jOptLong = jSONObject2.optLong("exp");
                                        String strOptString4 = jSONObject2.optString("accessCode");
                                        a aVar = new a(str);
                                        aVar.a(ad.this.b);
                                        aVar.e(ad.this.i);
                                        aVar.d(strOptString3);
                                        aVar.c(strOptString4);
                                        aVar.a(jOptLong);
                                        aVar.a(cn.fly.verify.util.l.g());
                                        if (!ad.this.k) {
                                            i.a().a(aVar);
                                        }
                                        if (ad.this.h != null) {
                                            ad.this.h.c(strOptString3);
                                        }
                                        dVar.a((d) aVar);
                                    }
                                }
                                if (!zEquals) {
                                    if (!ad.this.k) {
                                        i.a().a((a) null);
                                    }
                                    try {
                                        i = Integer.parseInt(strOptString);
                                    } catch (Throwable th) {
                                        i = 0;
                                    }
                                    dVar.a(new VerifyException(i, str));
                                }
                            } catch (Throwable th2) {
                                i.a().a((a) null);
                                dVar.a(new VerifyException(m.INNER_OTHER_EXCEPTION_ERR.a(), cn.fly.verify.util.l.a(th2)));
                            }
                            try {
                                UniAccountHelper.getInstance().releaseNetwork();
                            } catch (Throwable th3) {
                                v.a(th3, "release mobile net error");
                            }
                        }
                    });
                }
            });
        } catch (Throwable th) {
            v.a(th, "CU login error");
        }
    }

    public d<VerifyResult> e() {
        return this.o;
    }

    @Override // cn.fly.verify.w
    public void e(final d<VerifyResult> dVar) {
        v.a(getClass() + "getAccessToken ");
        this.o = dVar;
        if (cn.fly.verify.util.i.a() < 1 && !e.i().contains("simserial")) {
            if (TextUtils.isEmpty(cn.fly.verify.util.j.a())) {
                cn.fly.verify.util.j.a(cn.fly.verify.util.e.f());
            } else if (!TextUtils.isEmpty(cn.fly.verify.util.j.a()) && !cn.fly.verify.util.j.a().equals(cn.fly.verify.util.e.f())) {
                i.a().a((a) null);
                cn.fly.verify.util.j.a(cn.fly.verify.util.e.f());
            }
        }
        a aVarB = i.a().b();
        if (aVarB == null || !aVarB.f(this.b)) {
            v.a("getAccessToken No cache, invoke preGetAccessToken firstly.");
            v.a("getAccessToken No cache, invoke preGetAccessToken firstly.");
            if (this.h != null) {
                this.h.a("CUCC", this.b, "no_upc");
            }
            c(new d<a>() { // from class: cn.fly.verify.ad.2
                @Override // cn.fly.verify.d
                public void a(a aVar) {
                    e.b(0);
                    e.a(aVar.f());
                    ad.this.f((d<VerifyResult>) dVar);
                }

                @Override // cn.fly.verify.d
                public void a(VerifyException verifyException) {
                    if (dVar == null || dVar.a.getAndSet(true)) {
                        return;
                    }
                    dVar.a(verifyException);
                }
            });
            return;
        }
        if (this.h != null) {
            this.h.c(aVarB.g());
            this.h.a("CUCC", this.b, "upc", String.valueOf(aVarB.f()));
        }
        e.b(2);
        e.a(aVarB.f());
        f(dVar);
    }
}