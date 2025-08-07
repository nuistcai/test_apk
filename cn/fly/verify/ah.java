package cn.fly.verify;

import android.app.Activity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import cn.fly.tools.utils.ResHelper;
import cn.fly.verify.OAuthPageEventCallback;
import cn.fly.verify.ae;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.ui.BasePage;
import cn.fly.verify.ui.component.CommonProgressDialog;
import cn.fly.verify.ui.component.OneKeyLoginLayout;

/* loaded from: classes.dex */
public class ah extends BasePage implements y {
    private d<VerifyResult> a;
    private ag b;
    private ae.a c;
    private d<a> d;
    private boolean e;
    private boolean f;
    private boolean g = false;
    private int h;
    private ViewGroup i;
    private OneKeyLoginLayout j;
    private OAuthPageEventCallback.OAuthPageEventWrapper k;
    private String l;
    private String m;
    private int n;
    private Integer o;
    private String p;
    private u q;
    private String r;

    public ah(int i, boolean z, boolean z2, d<VerifyResult> dVar, ae.a aVar, String str) {
        this.e = true;
        this.f = true;
        this.e = z;
        this.f = z2;
        this.h = i;
        this.a = dVar;
        this.c = aVar;
        this.l = str;
        ad adVarD = ad.d();
        this.r = "CUCC";
        this.m = adVarD.b;
        this.n = adVarD.d;
        this.o = adVarD.e;
        this.p = adVarD.f;
    }

    private void c() {
        this.j = new OneKeyLoginLayout(this.activity, this.r, this, this.l);
        this.activity.setContentView(this.j);
        if (this.activity != null) {
            this.i = (ViewGroup) ((ViewGroup) this.activity.getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
            if (this.i != null) {
                this.i.setOnClickListener(this);
            }
        }
    }

    public void a() {
        if (this.j != null) {
            this.g = this.j.getCheckboxState();
        }
        onCreate();
        if (this.j != null) {
            this.j.resetCheckboxState(this.g);
        }
        onResume();
    }

    public void b() {
        Activity activity;
        int animRes;
        Activity activity2;
        String str;
        int animRes2;
        k.a().g();
        i.a().a(true);
        i.a().b(false);
        if (this.activity == null || this.b == null) {
            return;
        }
        if (this.b.an()) {
            activity = this.activity;
            animRes = this.b.aq();
            animRes2 = this.b.ar();
        } else {
            if (this.b.ah()) {
                activity = this.activity;
                animRes = ResHelper.getAnimRes(this.activity, "fly_verify_translate_in");
                activity2 = this.activity;
                str = "fly_verify_translate_out";
            } else if (this.b.aj()) {
                activity = this.activity;
                animRes = ResHelper.getAnimRes(this.activity, "fly_verify_translate_right_in");
                activity2 = this.activity;
                str = "fly_verify_translate_left_out";
            } else if (this.b.ai()) {
                activity = this.activity;
                animRes = ResHelper.getAnimRes(this.activity, "fly_verify_translate_bottom_in");
                activity2 = this.activity;
                str = "fly_verify_translate_bottom_out";
            } else {
                if (!this.b.ak()) {
                    if (this.b.al()) {
                        activity = this.activity;
                        animRes = ResHelper.getAnimRes(this.activity, "fly_verify_fade_in");
                        activity2 = this.activity;
                        str = "fly_verify_fade_out";
                    }
                    this.activity.finish();
                }
                activity = this.activity;
                animRes = ResHelper.getAnimRes(this.activity, "fly_verify_zoom_in");
                activity2 = this.activity;
                str = "fly_verify_zoom_out";
            }
            animRes2 = ResHelper.getAnimRes(activity2, str);
        }
        activity.overridePendingTransition(animRes, animRes2);
        this.activity.finish();
    }

    @Override // cn.fly.verify.y
    public void cancelLogin() {
        if (this.a != null) {
            this.a.a(new VerifyException(m.INNER_CANCEL_LOGIN));
        }
        b();
    }

    @Override // cn.fly.verify.y
    public void customizeLogin() {
        final String strA;
        if (this.k != null && this.k.loginBtnClicked != null) {
            try {
                this.k.loginBtnClicked.handle();
            } catch (Throwable th) {
                v.a(th, "loginBtnClicked User code error");
            }
        }
        if (this.q != null) {
            this.q.a(this.r, this.m, "login_start");
        }
        try {
            strA = ai.a(this.m, this.n, this.o, this.p);
        } catch (Throwable th2) {
            v.a(th2);
            strA = null;
        }
        a aVarB = i.a().b();
        if (aVarB == null || !aVarB.f(this.m)) {
            v.a("Get access code from operator server");
            if (this.r.equals("CUCC")) {
                ad.d().c(new d<a>() { // from class: cn.fly.verify.ah.1
                    @Override // cn.fly.verify.d
                    public void a(a aVar) {
                        if (ah.this.a != null && !TextUtils.isEmpty(strA)) {
                            ah.this.a.a((d) new VerifyResult(aVar.e(), strA, ah.this.r));
                        }
                        if (ah.this.e) {
                            ah.this.b();
                        }
                    }

                    @Override // cn.fly.verify.d
                    public void a(VerifyException verifyException) {
                        if (ah.this.a != null) {
                            ah.this.a.a(verifyException);
                        }
                        if (ah.this.e) {
                            ah.this.b();
                        }
                    }
                });
                return;
            }
            return;
        }
        if (this.a != null && !TextUtils.isEmpty(strA)) {
            this.a.a((d<VerifyResult>) new VerifyResult(aVarB.e(), strA, this.r));
        }
        if (this.e) {
            b();
        }
    }

    @Override // cn.fly.verify.y
    public void doOtherLogin() {
        if (this.a != null) {
            this.a.a(new VerifyException(m.INNER_OTHER_LOGIN));
        }
        if (this.f) {
            b();
        }
    }

    @Override // cn.fly.verify.y
    public d<VerifyResult> getCallback() {
        return this.a;
    }

    @Override // cn.fly.verify.ui.BasePage
    protected int getContentViewId() {
        return ResHelper.getLayoutRes(getContext(), "fly_verify_page_one_key_login");
    }

    @Override // cn.fly.verify.y
    public String getFakeNumber() {
        if (!TextUtils.isEmpty(this.l)) {
            return this.l;
        }
        a aVarB = i.a().b();
        return aVarB != null ? aVarB.g() : "";
    }

    @Override // cn.fly.verify.ui.BasePage
    protected void getTitleStyle(BasePage.TitleStyle titleStyle) {
    }

    @Override // cn.fly.tools.FakeActivity
    public void onDestroy() {
        x.b().a(true);
        super.onDestroy();
        this.a = null;
        this.d = null;
        v.a("OneKeyLoginPage onDestroy.");
        if (this.c != null) {
            this.c.a();
        }
        if (this.j != null && this.j.getLoginAdapter() != null) {
            this.j.getLoginAdapter().onDestroy();
        }
        CommonProgressDialog.dismissProgressDialog();
        if (this.k == null || this.k.pageclosed == null) {
            return;
        }
        try {
            this.k.pageclosed.handle();
        } catch (Throwable th) {
            v.a(th, "pageclosed User code error");
        }
    }

    @Override // cn.fly.tools.FakeActivity
    public boolean onKeyEvent(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 4) {
            return super.onKeyEvent(i, keyEvent);
        }
        cancelLogin();
        return true;
    }

    @Override // cn.fly.verify.ui.BasePage
    protected boolean onLeftEvent() {
        cancelLogin();
        return true;
    }

    @Override // cn.fly.tools.FakeActivity
    public void onResume() {
        super.onResume();
        if (this.j == null || this.j.getLoginAdapter() == null) {
            return;
        }
        this.j.getLoginAdapter().onResume();
    }

    @Override // cn.fly.verify.ui.BasePage
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        int id = view.getId();
        if (this.i == null || id != this.i.getId() || this.b == null || !this.b.aT()) {
            return;
        }
        cancelLogin();
    }

    @Override // cn.fly.verify.ui.BasePage
    protected void onViewCreated(Throwable th) {
        this.q = x.b().a();
        if (this.q != null && th != null && this.a != null) {
            this.a.a(new VerifyException(m.INNER_OTHER_EXCEPTION_ERR.a(), cn.fly.verify.util.l.a(th)));
            finish();
            return;
        }
        d<VerifyResult> dVarE = ad.d().e();
        if (dVarE != null && dVarE.a.getAndSet(true)) {
            v.a("auth success after timeout");
            if (this.q != null) {
                this.q.a(this.r, this.m, "timeout_success");
                this.q.b();
            }
            finish();
            return;
        }
        this.k = k.a().h();
        if (this.k != null && this.k.pageOpened != null) {
            try {
                this.k.pageOpened.handle();
            } catch (Throwable th2) {
                v.a(th2, "pageOpened User code error");
            }
        }
        PageCallback pageCallbackJ = k.a().j();
        if (pageCallbackJ != null) {
            pageCallbackJ.pageCallback(6119140, cn.fly.verify.util.l.a("oauthpage_opened", "oauthpage opened"));
        }
        i.a().b(true);
        cn.fly.verify.util.o.a(this.activity);
        if (this.activity != null) {
            this.b = cn.fly.verify.util.o.a(this.activity.getResources().getConfiguration().orientation);
            cn.fly.verify.util.o.a(this.activity, this.b);
            cn.fly.verify.util.o.b(this.activity);
            cn.fly.verify.util.o.b(this.activity, this.b);
            if ((this.b == null || !this.b.as()) && cn.fly.verify.util.e.m() >= 21) {
                this.activity.getWindow().clearFlags(Integer.MIN_VALUE);
            }
            this.i = (ViewGroup) ((ViewGroup) this.activity.getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
            if (this.i != null) {
                this.i.setOnClickListener(this);
            }
            c();
        }
        x.b().a(false);
        if (this.q != null) {
            this.q.a(this.r, this.m, "open_authpage_end");
        }
    }
}