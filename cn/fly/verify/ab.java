package cn.fly.verify;

import android.os.SystemClock;
import android.text.TextUtils;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.login.impl.cmcc.CmccOAuthProxyActivity;
import cn.fly.verify.util.e;
import com.cmic.gen.sdk.auth.GenAuthnHelper;
import com.cmic.gen.sdk.auth.GenTokenListener;
import com.cmic.gen.sdk.e.PhoneScripUtils;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ab extends w {
    private static ab m;
    private GenAuthnHelper n;

    private ab() {
        v.a("Initialize CmccOneKeyImpl");
        this.i = "CMCC";
        try {
            this.n = GenAuthnHelper.getInstance(this.a);
            if (i.a().o()) {
                GenAuthnHelper.setDebugMode(true);
            }
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
    }

    public static ab d() {
        if (m == null) {
            synchronized (ab.class) {
                if (m == null) {
                    m = new ab();
                }
            }
        }
        return m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(final d<VerifyResult> dVar) {
        this.g = dVar;
        try {
            if (this.h != null) {
                this.h.a(this.i, this.b, "switch_s");
            }
            new cn.fly.verify.util.g().a();
            if (this.h != null) {
                this.h.a(this.i, this.b, "switch_e");
                this.h.a(this.i, this.b, "open_authpage_start");
            }
            this.n.loginAuth(this.b, this.c, new GenTokenListener() { // from class: cn.fly.verify.ab.3
                @Override // com.cmic.gen.sdk.auth.GenTokenListener
                public void onGetTokenComplete(int i, JSONObject jSONObject) {
                    v.a("open authpage result ,jsonObject: " + (jSONObject == null ? null : jSONObject.toString()));
                    cn.fly.verify.datatype.d dVar2 = new cn.fly.verify.datatype.d(i, jSONObject);
                    if (dVar2.a()) {
                        return;
                    }
                    dVar.a(new VerifyException(dVar2.c(), jSONObject != null ? jSONObject.toString() : null));
                }
            });
        } catch (Throwable th) {
            v.a("cm login failed: " + th.getMessage());
            dVar.a(new VerifyException(m.C_VERIFY_CATCH.a(), cn.fly.verify.util.l.a(th)));
        }
    }

    public ab a(String str, String str2) {
        super.a(str);
        this.b = str;
        this.c = str2;
        return m;
    }

    @Override // cn.fly.verify.w
    public void b() {
        try {
            k.a().g();
            if (this.n != null) {
                this.n.quitAuthActivity();
            }
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
    }

    @Override // cn.fly.verify.w
    public void c() {
        try {
            v.a("refreshOAuthPage");
            CmccOAuthProxyActivity cmccOAuthProxyActivity = CmccOAuthProxyActivity.getInstance();
            if (cmccOAuthProxyActivity != null) {
                cmccOAuthProxyActivity.refresh();
            }
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
    }

    @Override // cn.fly.verify.w
    public void c(final d<a> dVar) {
        this.n.setOverTime(a());
        if (this.h != null) {
            this.h.a(this.i, this.b, "switch_s");
        }
        new cn.fly.verify.util.g().a();
        final long jUptimeMillis = SystemClock.uptimeMillis();
        if (this.h != null) {
            this.h.a(this.i, this.b, "switch_e");
            this.h.a(this.i, this.b, "request_start");
        }
        this.j = cn.fly.verify.util.l.a(this.a);
        cn.fly.verify.util.e.b((e.a<String>) null);
        this.n.getPhoneInfo(this.b, this.c, new GenTokenListener() { // from class: cn.fly.verify.ab.1
            @Override // com.cmic.gen.sdk.auth.GenTokenListener
            public void onGetTokenComplete(int i, JSONObject jSONObject) {
                if (ab.this.h != null) {
                    ab.this.h.a(ab.this.i, ab.this.b, "request_end", String.valueOf(SystemClock.uptimeMillis() - jUptimeMillis));
                }
                v.a("preGetAccessToken Obtain access code complete. i: " + i + ", jsonObject: " + (jSONObject == null ? null : jSONObject.toString()));
                cn.fly.verify.datatype.a aVar = new cn.fly.verify.datatype.a(i, jSONObject);
                aVar.a(ab.this.b);
                aVar.a(cn.fly.verify.util.l.g());
                if (!aVar.c()) {
                    if (!ab.this.k) {
                        i.a().a((a) null);
                    }
                    dVar.a(new VerifyException(aVar.i(), jSONObject != null ? jSONObject.toString() : null));
                    return;
                }
                aVar.d(ab.this.f());
                if (ab.this.h != null) {
                    ab.this.h.c(aVar.g());
                }
                if (ab.this.k) {
                    String strJ = aVar.j();
                    if (!TextUtils.isEmpty(strJ)) {
                        ab.this.i = strJ;
                        aVar.e(ab.this.i);
                    }
                } else {
                    aVar.e(ab.this.i);
                    i.a().a(aVar);
                }
                dVar.a((d) aVar);
            }
        });
    }

    public d<VerifyResult> e() {
        return this.g;
    }

    @Override // cn.fly.verify.w
    public void e(final d<VerifyResult> dVar) {
        v.a("getAccessToken Start getting operator token");
        this.n.setAuthThemeConfig(aa.a().a(i.a().c()));
        if (TextUtils.isEmpty(cn.fly.verify.util.j.a())) {
            cn.fly.verify.util.j.a(cn.fly.verify.util.e.f());
        } else if (!TextUtils.isEmpty(cn.fly.verify.util.j.a()) && !cn.fly.verify.util.j.a().equals(cn.fly.verify.util.e.f())) {
            i.a().a((a) null);
            cn.fly.verify.util.j.a(cn.fly.verify.util.e.f());
        }
        a aVarB = i.a().b();
        if (aVarB == null || !aVarB.f(this.b)) {
            v.a("getAccessToken No cache, invoke preGetAccessToken firstly.");
            if (this.h != null) {
                this.h.a(this.i, this.b, "no_upc");
            }
            c(new d<a>() { // from class: cn.fly.verify.ab.2
                @Override // cn.fly.verify.d
                public void a(a aVar) {
                    ab.this.f((d<VerifyResult>) dVar);
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
        v.a("getAccessToken Use cached access token.");
        if (this.h != null) {
            this.h.c(aVarB.g());
            this.h.a(this.i, this.b, "upc", String.valueOf(aVarB.f()));
        }
        f(dVar);
    }

    public String f() {
        try {
            return PhoneScripUtils.b(this.a);
        } catch (Throwable th) {
            v.a(th);
            return null;
        }
    }
}