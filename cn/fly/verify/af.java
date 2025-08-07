package cn.fly.verify;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.chinatelecom.account.sdk.CtAuth;
import cn.com.chinatelecom.account.sdk.ui.AuthActivity;
import cn.fly.tools.FlyUIShell;
import cn.fly.tools.utils.ResHelper;
import cn.fly.verify.OAuthPageEventCallback;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.ui.component.CommonProgressDialog;
import cn.fly.verify.ui.component.OneKeyLoginLayout;
import com.cmic.gen.sdk.view.GenLoginAuthActivity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class af implements Application.ActivityLifecycleCallbacks, View.OnClickListener, y {
    private static af e;
    private Context a;
    private Activity b;
    private Activity c;
    private ViewGroup d;
    private LinearLayout h;
    private CheckBox i;
    private ImageView j;
    private TextView k;
    private TextView l;
    private ag m;
    private String n;
    private OneKeyLoginLayout o;
    private OAuthPageEventCallback.OAuthPageEventWrapper q;
    private u r;
    private Application.ActivityLifecycleCallbacks f = null;
    private ComponentCallbacks g = null;
    private boolean p = false;

    private af(Context context) {
        this.a = null;
        if (context != null) {
            this.a = context.getApplicationContext();
        }
    }

    public static af a(Context context) {
        if (e == null) {
            synchronized (ac.class) {
                if (e == null) {
                    e = new af(context);
                }
            }
        }
        return e;
    }

    private static List<View> a(View view) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                arrayList.add(childAt);
                arrayList.addAll(a(childAt));
            }
        }
        return arrayList;
    }

    private void a(Activity activity) {
        this.o = new OneKeyLoginLayout(activity, "CTCC", this);
        activity.setContentView(this.o);
        this.d = (ViewGroup) ((ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
        if (this.d != null) {
            this.d.setOnClickListener(this);
        }
    }

    private void d() {
        this.q = k.a().h();
        if (this.q != null && this.q.pageOpened != null) {
            try {
                this.q.pageOpened.handle();
            } catch (Throwable th) {
                v.a(th, "pageOpened ==> User Code error");
            }
        }
        PageCallback pageCallbackJ = k.a().j();
        if (pageCallbackJ != null) {
            pageCallbackJ.pageCallback(6119140, cn.fly.verify.util.l.a("oauthpage_opened", "oauthpage opened"));
        }
        i.a().b(true);
    }

    public void a() {
        if (this.b != null) {
            this.b = null;
            this.c = null;
        }
    }

    public Activity b() {
        return this.b;
    }

    public void c() {
        if (this.c != null) {
            if (this.o != null) {
                this.p = this.o.getCheckboxState();
            }
            this.o = new OneKeyLoginLayout(this.c, "CTCC", this);
            this.c.setContentView(this.o);
            this.d = (ViewGroup) ((ViewGroup) this.c.getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
            if (this.d != null) {
                this.d.setOnClickListener(this);
            }
            this.o.resetCheckboxState(this.p);
        }
    }

    @Override // cn.fly.verify.y
    public void cancelLogin() {
        if (this.j != null) {
            this.j.performClick();
        }
        CtAuth.getInstance().finishAuthActivity();
    }

    @Override // cn.fly.verify.y
    public void customizeLogin() {
        if (this.r != null) {
            this.r.a("CTCC", e.a.get(), "login_start");
        }
        if (this.i != null) {
            this.i.setChecked(true);
        }
        if (this.h != null) {
            this.h.performClick();
        }
        if (this.q == null || this.q.loginBtnClicked == null) {
            return;
        }
        try {
            this.q.loginBtnClicked.handle();
        } catch (Throwable th) {
            v.a(th, "loginBtnClicked ==> User Code error");
        }
    }

    @Override // cn.fly.verify.y
    public void doOtherLogin() {
        if (this.k != null) {
            this.k.performClick();
        }
    }

    @Override // cn.fly.verify.y
    public d<VerifyResult> getCallback() {
        return ac.d().e();
    }

    @Override // cn.fly.verify.y
    public String getFakeNumber() {
        return TextUtils.isEmpty(this.n) ? "" : this.n;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        d<VerifyResult> dVarE;
        try {
            if ((activity instanceof FlyUIShell) || (activity instanceof AuthActivity) || (activity instanceof GenLoginAuthActivity)) {
                i.a().a(activity);
                v.a(activity.getClass().getSimpleName() + " onActivityCreated.");
            }
        } catch (Throwable th) {
        }
        try {
            if (activity instanceof AuthActivity) {
                Intent intent = activity.getIntent();
                if (intent == null) {
                    intent = new Intent();
                    intent.putExtra("needSetView", true);
                }
                activity.setIntent(intent);
                if (bundle != null) {
                    activity.finish();
                    return;
                }
                this.r = x.b().a();
                if (cn.fly.verify.util.l.c().equals("CTCC") && (dVarE = ac.d().e()) != null && dVarE.a.getAndSet(true)) {
                    v.a("auth success after timeout");
                    if (this.r != null) {
                        this.r.a("CTCC", (String) null, "timeout_success");
                        this.r.b();
                    }
                    activity.finish();
                    return;
                }
                d();
                cn.fly.verify.util.o.b(activity);
                this.m = cn.fly.verify.util.o.a(activity.getResources().getConfiguration().orientation);
                cn.fly.verify.util.o.a(activity, this.m);
                cn.fly.verify.util.o.b(activity, this.m);
                cn.fly.verify.util.o.a(activity);
                if (cn.fly.verify.util.l.c().equals("CTCC")) {
                    x.b().a(false);
                    if (this.r != null) {
                        this.r.a("CTCC", e.a.get(), "open_authpage_end");
                    }
                }
            }
        } catch (NoClassDefFoundError e2) {
            i.a().b(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        try {
            if ((activity instanceof FlyUIShell) || (activity instanceof AuthActivity) || (activity instanceof GenLoginAuthActivity)) {
                v.a(activity.getClass().getSimpleName() + " onActivityDestroyed.");
                i.a().a((Activity) null);
            }
        } catch (Throwable th) {
        }
        this.b = null;
        try {
            if (activity instanceof AuthActivity) {
                this.c = null;
                if (this.g != null) {
                    activity.getApplication().unregisterComponentCallbacks(this.g);
                    this.g = null;
                }
                if (this.q != null && this.q.pageclosed != null) {
                    try {
                        this.q.pageclosed.handle();
                    } catch (Throwable th2) {
                        v.a(th2, "pageclosed ==> User Code error");
                    }
                }
                CommonProgressDialog.dismissProgressDialog();
                if (this.o != null && this.o.getLoginAdapter() != null) {
                    this.o.getLoginAdapter().onDestroy();
                }
                this.h = null;
                this.i = null;
                this.j = null;
                this.k = null;
                this.l = null;
                this.d = null;
                this.o = null;
                this.q = null;
                i.a().a(true);
                i.a().b(false);
                x.b().a(true);
            }
        } catch (NoClassDefFoundError e2) {
            i.a().a(true);
            i.a().b(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        try {
            if ((activity instanceof FlyUIShell) || (activity instanceof AuthActivity) || (activity instanceof GenLoginAuthActivity)) {
                v.a(activity.getClass().getSimpleName() + " onActivityPaused.");
            }
        } catch (Throwable th) {
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        try {
            if ((activity instanceof FlyUIShell) || (activity instanceof AuthActivity) || (activity instanceof GenLoginAuthActivity)) {
                v.a(activity.getClass().getSimpleName() + " onActivityResumed.");
            }
        } catch (Throwable th) {
        }
        this.b = activity;
        try {
            if (!(activity instanceof AuthActivity) || this.o == null || this.o.getLoginAdapter() == null) {
                return;
            }
            this.o.getLoginAdapter().onResume();
        } catch (NoClassDefFoundError e2) {
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        try {
            if ((activity instanceof FlyUIShell) || (activity instanceof AuthActivity) || (activity instanceof GenLoginAuthActivity)) {
                v.a(activity.getClass().getSimpleName() + " onActivityStarted.");
            }
        } catch (Throwable th) {
        }
        this.b = activity;
        try {
            if (activity instanceof AuthActivity) {
                x.b().a(false);
                this.c = activity;
                ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
                ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(0);
                Intent intent = activity.getIntent();
                boolean booleanExtra = true;
                if (intent != null) {
                    booleanExtra = intent.getBooleanExtra("needSetView", true);
                    intent.putExtra("needSetView", false);
                    activity.setIntent(intent);
                }
                for (View view : a(viewGroup2)) {
                    if (view.getId() == ResHelper.getIdRes(this.a, "ct_account_login_btn")) {
                        this.h = (LinearLayout) view;
                    }
                    if (view.getId() == ResHelper.getIdRes(this.a, "ct_auth_privacy_checkbox")) {
                        this.i = (CheckBox) view;
                    }
                    if (view.getId() == ResHelper.getIdRes(this.a, "ct_account_nav_goback")) {
                        this.j = (ImageView) view;
                    }
                    if (view.getId() == ResHelper.getIdRes(this.a, "ct_account_other_login_way")) {
                        this.k = (TextView) view;
                    }
                    if (view.getId() == ResHelper.getIdRes(this.a, "ct_account_desensphone")) {
                        this.l = (TextView) view;
                    }
                    if (booleanExtra) {
                        viewGroup2.setVisibility(8);
                    }
                }
                if (this.l != null) {
                    this.n = this.l.getText().toString();
                }
                if (booleanExtra) {
                    a(activity);
                }
            }
        } catch (NoClassDefFoundError e2) {
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        try {
            if ((activity instanceof FlyUIShell) || (activity instanceof AuthActivity) || (activity instanceof GenLoginAuthActivity)) {
                v.a(activity.getClass().getSimpleName() + " onActivityStopped.");
            }
        } catch (Throwable th) {
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (this.d == null || id != this.d.getId() || this.m == null || !this.m.aT()) {
            return;
        }
        cancelLogin();
    }
}