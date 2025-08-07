package cn.fly.verify;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import cn.com.chinatelecom.account.api.CtSetting;
import cn.com.chinatelecom.account.sdk.AuthPageConfig;
import cn.com.chinatelecom.account.sdk.CtAuth;
import cn.com.chinatelecom.account.sdk.ResultListener;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.UIHandler;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.util.e;
import java.lang.reflect.Field;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ac extends w {
    private static ac m;
    private boolean n;
    private final ResultListener o = new ResultListener() { // from class: cn.fly.verify.ac.1
        @Override // cn.com.chinatelecom.account.sdk.ResultListener
        public void onResult(String str) {
            String strA;
            v.a("getAccessToken Obtain access token result: " + str);
            cn.fly.verify.datatype.e eVar = new cn.fly.verify.datatype.e(str);
            if (eVar.c() != 80200) {
                if (eVar.c() == 80201) {
                    ac.this.g.a(new VerifyException(m.INNER_OTHER_LOGIN));
                    if (i.a().n()) {
                    }
                } else {
                    String strB = eVar.b();
                    try {
                        strA = ai.a(ac.this.b, ac.this.d, ac.this.e, ac.this.f);
                    } catch (Throwable th) {
                        v.a(th);
                        strA = null;
                    }
                    if (!eVar.a() || TextUtils.isEmpty(strA)) {
                        ac.this.g.a(new VerifyException(eVar.c(), str));
                    } else {
                        ac.this.g.a((d) new VerifyResult(strB, strA, "CTCC"));
                    }
                    if (i.a().m()) {
                    }
                }
                UIHandler.sendEmptyMessageDelayed(0, 50L, new Handler.Callback() { // from class: cn.fly.verify.ac.1.1
                    @Override // android.os.Handler.Callback
                    public boolean handleMessage(Message message) {
                        cn.com.chinatelecom.account.sdk.a.a.a().a(ac.this.a, false, cn.fly.verify.util.l.f());
                        cn.com.chinatelecom.account.sdk.a.a.a().a(ac.d().o);
                        return false;
                    }
                });
            }
            ac.this.g.a(new VerifyException(m.INNER_CANCEL_LOGIN));
            ac.this.b();
            UIHandler.sendEmptyMessageDelayed(0, 50L, new Handler.Callback() { // from class: cn.fly.verify.ac.1.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    cn.com.chinatelecom.account.sdk.a.a.a().a(ac.this.a, false, cn.fly.verify.util.l.f());
                    cn.com.chinatelecom.account.sdk.a.a.a().a(ac.d().o);
                    return false;
                }
            });
        }
    };

    private ac() {
        v.a("Initialize CtccOneKeyImpl");
        this.i = "CTCC";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, final d<a> dVar) {
        final cn.fly.verify.datatype.b bVar = new cn.fly.verify.datatype.b(str);
        bVar.a(this.b);
        bVar.e(this.i);
        bVar.a(cn.fly.verify.util.l.g());
        if (TextUtils.isEmpty(bVar.g())) {
            bVar.d(l());
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.ac.3
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    if (!bVar.c()) {
                        if (!ac.this.k) {
                            i.a().a((a) null);
                        }
                        dVar.a(new VerifyException(bVar.i(), bVar.d()));
                        return false;
                    }
                    if (!ac.this.k) {
                        i.a().a(bVar);
                    }
                    if (ac.this.h != null) {
                        ac.this.h.c(bVar.g());
                    }
                    dVar.a((d) bVar);
                    return false;
                }
            });
            return;
        }
        if (!bVar.c()) {
            if (!this.k) {
                i.a().a((a) null);
            }
            dVar.a(new VerifyException(bVar.i(), bVar.d()));
        } else {
            if (!this.k) {
                i.a().a(bVar);
            }
            if (this.h != null) {
                this.h.c(bVar.g());
            }
            dVar.a((d<a>) bVar);
        }
    }

    public static ac d() {
        if (m == null) {
            synchronized (ac.class) {
                if (m == null) {
                    m = new ac();
                }
            }
        }
        return m;
    }

    private void f() {
        try {
            if (this.b.equals(cn.com.chinatelecom.account.api.a.a)) {
                return;
            }
            CtAuth.getInstance().init(this.a, this.b, this.c, i.a().o());
            v.a("ct rewrite config");
        } catch (Throwable th) {
            v.a(th);
        }
    }

    private void f(final d<a> dVar) {
        CtSetting ctSetting = new CtSetting(3000, 2000, a());
        try {
            if (this.h != null) {
                this.h.a("CTCC", this.b, "switch_s");
            }
            new cn.fly.verify.util.g().a();
            final long jUptimeMillis = SystemClock.uptimeMillis();
            if (this.h != null) {
                this.h.a("CTCC", this.b, "switch_e");
                this.h.a("CTCC", this.b, "request_start");
            }
            this.j = cn.fly.verify.util.l.a(this.a);
            cn.fly.verify.util.e.b((e.a<String>) null);
            f();
            CtAuth.getInstance().requestPreLogin(ctSetting, new ResultListener() { // from class: cn.fly.verify.ac.2
                @Override // cn.com.chinatelecom.account.sdk.ResultListener
                public void onResult(String str) {
                    v.a("preGetAccessToken Obtain access code result: " + str);
                    try {
                        if (new JSONObject(str).optInt("result") == 0) {
                            String strH = ac.this.h();
                            if (!TextUtils.isEmpty(strH)) {
                                str = strH;
                            }
                        }
                    } catch (Throwable th) {
                    }
                    if (ac.this.h != null) {
                        ac.this.h.a("CTCC", ac.this.b, "request_end", String.valueOf(SystemClock.uptimeMillis() - jUptimeMillis));
                    }
                    ac.this.a(str, (d<a>) dVar);
                }
            });
        } catch (Throwable th) {
            v.a(th, "ct prelogin error");
        }
    }

    private String g() {
        String strA = cn.fly.verify.util.b.a("ctcache");
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        try {
            String strF = ((cn.com.chinatelecom.account.sdk.a.e) HashonHelper.fromJson(strA, cn.com.chinatelecom.account.sdk.a.e.class)).f();
            long jOptLong = new JSONObject(strF).optJSONObject("data").optLong("expiredTime");
            int iA = cn.fly.verify.util.b.a("ctsubid", -1);
            String strA2 = cn.fly.verify.util.b.a("ctappid");
            if (jOptLong <= System.currentTimeMillis() + 10000 || cn.fly.verify.util.l.g() != iA) {
                return null;
            }
            if (this.b.equals(strA2)) {
                return strF;
            }
            return null;
        } catch (Throwable th) {
            v.a(th);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g(d<VerifyResult> dVar) {
        if (i.a().g()) {
            this.g = dVar;
            AuthPageConfig authPageConfigB = aa.a().b();
            Activity activityB = af.a(this.a).b();
            if (activityB == null) {
                activityB = cn.fly.verify.util.l.e();
            }
            if (this.h != null) {
                this.h.a("CTCC", this.b, "open_authpage_start");
            }
            try {
                j();
                f();
                CtAuth.getInstance().openAuthActivity(activityB, authPageConfigB, this.o);
            } catch (Throwable th) {
                v.a(th, "doGetAccessToken Obtain access token exception");
                dVar.a(new VerifyException(m.C_ONE_KEY_OBTAIN_CT_OPERATOR_ACCESS_TOKEN_ERR));
            }
            cn.fly.verify.util.g.b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String h() {
        try {
            cn.com.chinatelecom.account.sdk.a.a aVarA = cn.com.chinatelecom.account.sdk.a.a.a();
            Field declaredField = aVarA.getClass().getDeclaredField("b");
            declaredField.setAccessible(true);
            cn.com.chinatelecom.account.sdk.a.e eVar = (cn.com.chinatelecom.account.sdk.a.e) declaredField.get(aVarA);
            String strF = eVar.f();
            String strC = eVar.c();
            JSONObject jSONObject = new JSONObject(strF);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            jSONObjectOptJSONObject.put("number", strC);
            jSONObject.put("data", jSONObjectOptJSONObject);
            eVar.f(jSONObject.toString());
            String strFromObject = HashonHelper.fromObject(eVar);
            cn.fly.verify.util.b.a("ctcache", strFromObject);
            cn.fly.verify.util.b.b("ctsubid", cn.fly.verify.util.l.g());
            cn.fly.verify.util.b.a("ctappid", this.b);
            v.a("getOrigin response: " + strFromObject);
            return jSONObject.toString();
        } catch (Throwable th) {
            v.a(th);
            return null;
        }
    }

    private void i() throws Throwable {
        cn.com.chinatelecom.account.sdk.a.a aVarA = cn.com.chinatelecom.account.sdk.a.a.a();
        Field declaredField = aVarA.getClass().getDeclaredField("b");
        declaredField.setAccessible(true);
        declaredField.set(aVarA, (cn.com.chinatelecom.account.sdk.a.e) HashonHelper.fromJson(cn.fly.verify.util.b.a("ctcache"), cn.com.chinatelecom.account.sdk.a.e.class));
        v.a("set ct cache");
    }

    private void j() {
        cn.fly.verify.util.b.a("ctcache", (String) null);
        cn.fly.verify.util.b.b("ctsubid", -1);
        cn.fly.verify.util.b.a("ctappid", (String) null);
        v.a("clear ct cache");
    }

    private void k() throws Throwable {
        Field declaredField = CtAuth.class.getDeclaredField("d");
        declaredField.setAccessible(true);
        declaredField.set(CtAuth.getInstance(), true);
        v.a("set ctpre");
    }

    private String l() {
        try {
            return cn.com.chinatelecom.account.sdk.a.a.a().c();
        } catch (Throwable th) {
            v.a(th, "WARNING: Maybe need to upgrade CT");
            return null;
        }
    }

    public ac a(String str, String str2) {
        if (TextUtils.isEmpty(this.b)) {
            super.a(str);
            this.b = str;
            this.c = str2;
            CtAuth.getInstance().init(this.a, str, str2, i.a().o());
        }
        return m;
    }

    @Override // cn.fly.verify.w
    public void b() {
        try {
            k.a().g();
            af.a(this.a).a();
            CtAuth.getInstance().finishAuthActivity();
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
    }

    @Override // cn.fly.verify.w
    public void c() {
        try {
            v.a("refreshOAuthPage");
            af.a(this.a).c();
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
    }

    @Override // cn.fly.verify.w
    public void c(d<a> dVar) {
        String strG = g();
        if (TextUtils.isEmpty(strG)) {
            f(dVar);
            return;
        }
        if (this.h != null) {
            this.h.a("CTCC", this.b, "upc");
        }
        try {
            i();
            k();
            this.n = true;
        } catch (Throwable th) {
            v.a(th);
        }
        a(strG, dVar);
    }

    public d<VerifyResult> e() {
        return this.g;
    }

    @Override // cn.fly.verify.w
    public void e(final d<VerifyResult> dVar) {
        v.a("getAccessToken Start getting operator token");
        if (cn.fly.verify.util.i.a() < 1 && !e.i().contains("simserial")) {
            if (TextUtils.isEmpty(cn.fly.verify.util.j.a()) || TextUtils.equals("-1", cn.fly.verify.util.j.a())) {
                cn.fly.verify.util.j.a(cn.fly.verify.util.e.f());
            } else if (!TextUtils.isEmpty(cn.fly.verify.util.j.a()) && !cn.fly.verify.util.j.a().equals(cn.fly.verify.util.e.f())) {
                i.a().a((a) null);
                cn.fly.verify.util.j.a(cn.fly.verify.util.e.f());
            }
        }
        a aVarB = i.a().b();
        if (aVarB == null || !aVarB.f(this.b)) {
            v.a("getAccessToken No cache, invoke preGetAccessToken firstly.");
            if (this.h != null) {
                this.h.a("CTCC", this.b, "no_upc");
            }
            f(new d<a>() { // from class: cn.fly.verify.ac.4
                @Override // cn.fly.verify.d
                public void a(a aVar) {
                    e.b(0);
                    e.a(aVar.f());
                    ac.this.g((d<VerifyResult>) dVar);
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
        if (this.n) {
            if (this.h != null) {
                this.h.c(aVarB.g());
                this.h.a("CTCC", this.b, "upc", String.valueOf(aVarB.f()));
            }
            this.n = false;
        }
        e.b(2);
        e.a(aVarB.f());
        g(dVar);
    }
}