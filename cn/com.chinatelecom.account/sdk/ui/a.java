package cn.com.chinatelecom.account.sdk.ui;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.chinatelecom.account.api.CtSetting;
import cn.com.chinatelecom.account.api.d.f;
import cn.com.chinatelecom.account.api.d.g;
import cn.com.chinatelecom.account.api.d.j;
import cn.com.chinatelecom.account.sdk.AuthPageConfig;
import cn.com.chinatelecom.account.sdk.AuthViewConfig;
import cn.com.chinatelecom.account.sdk.ui.b;

/* loaded from: classes.dex */
public abstract class a extends Activity implements View.OnClickListener, c {
    protected View a;
    protected View b;
    protected View c;
    protected TextView d;
    protected TextView e;
    protected TextView f;
    protected ImageView g;
    protected CheckBox h;
    protected TextView i;
    protected View j;
    protected View k;
    protected View l;
    protected View m;
    protected View n;
    protected AuthPageConfig o;
    protected AuthViewConfig p;
    protected cn.com.chinatelecom.account.sdk.a.a q;
    protected Context r;

    private void a() {
        this.a = findViewById(this.o.b());
        this.d = (TextView) findViewById(this.o.c());
        this.b = findViewById(this.o.e());
        if (this.o.f() != 0) {
            this.g = (ImageView) findViewById(this.o.f());
        }
        this.f = (TextView) findViewById(this.o.g());
        this.i = (TextView) findViewById(this.o.j());
        if (this.o.h() != 0) {
            this.c = findViewById(this.o.h());
        }
        if (this.o.d() != 0) {
            this.e = (TextView) findViewById(this.o.d());
            this.e.setText(cn.com.chinatelecom.account.sdk.a.a.a().d());
            if (cn.com.chinatelecom.account.sdk.a.a.a().e()) {
                this.e.setCompoundDrawables(null, null, null, null);
            }
        }
        if (this.o.i() != 0) {
            this.h = (CheckBox) findViewById(this.o.i());
        }
    }

    private void a(String str, String str2) {
        Intent intent = new Intent(this, (Class<?>) PrivacyWebviewActivity.class);
        intent.putExtra("privacyProtocolUrl", str);
        intent.putExtra("privacyProtocolTitle", str2);
        startActivity(intent);
    }

    private void d() {
        this.a.setOnClickListener(this);
        this.b.setOnClickListener(this);
        if (this.c != null) {
            this.c.setOnClickListener(this);
        }
        if (this.p == null || this.p.T == 0) {
            n();
        }
    }

    private void k() {
        if (this.o.s() != 0) {
            this.j = findViewById(this.o.s());
        }
        if (this.o.t() != 0) {
            this.k = findViewById(this.o.t());
        }
        if (this.o.u() != 0) {
            this.l = findViewById(this.o.u());
        }
        if (this.o.v() != 0) {
            this.m = findViewById(this.o.v());
        }
        if (this.o.w() != 0) {
            this.n = findViewById(this.o.w());
        }
    }

    private void l() {
        if (this.j != null && this.o.x() != null) {
            this.j.setOnClickListener(this.o.x());
        }
        if (this.k != null && this.o.y() != null) {
            this.k.setOnClickListener(this.o.y());
        }
        if (this.l != null && this.o.z() != null) {
            this.l.setOnClickListener(this.o.z());
        }
        if (this.m != null && this.o.A() != null) {
            this.m.setOnClickListener(this.o.A());
        }
        if (this.n == null || this.o.B() == null) {
            return;
        }
        this.n.setOnClickListener(this.o.B());
    }

    private boolean m() {
        return this.h.isChecked();
    }

    private void n() {
        try {
            CharSequence text = this.i.getText();
            if (text.length() >= 18) {
                String str = (String) this.i.getTag();
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
                spannableStringBuilder.setSpan(new cn.com.chinatelecom.account.sdk.b.a(this, cn.com.chinatelecom.account.sdk.a.a.a().b(null), "", 0, str), 5, 18, 33);
                this.i.setText(spannableStringBuilder);
                this.i.setMovementMethod(LinkMovementMethod.getInstance());
                this.i.setHighlightColor(getResources().getColor(R.color.transparent));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void o() {
        cn.com.chinatelecom.account.sdk.b.b bVar = new cn.com.chinatelecom.account.sdk.b.b(this, this.p);
        bVar.a();
        bVar.b();
        bVar.c();
        bVar.d();
        bVar.e();
        bVar.f();
        bVar.g();
        bVar.a(this.o, this);
    }

    protected abstract String b();

    public abstract void c();

    @Override // cn.com.chinatelecom.account.sdk.ui.c
    public void e() {
        f.a(b()).b(g.f(this)).c();
        if (!this.q.b()) {
            i();
            cn.com.chinatelecom.account.api.a.a().a((CtSetting) null, new cn.com.chinatelecom.account.api.c() { // from class: cn.com.chinatelecom.account.sdk.ui.a.1
                @Override // cn.com.chinatelecom.account.api.c
                public void a(String str) {
                    cn.com.chinatelecom.account.sdk.a.a.a().a(a.this, str, (TextView) null);
                }
            });
        } else {
            this.b.setEnabled(false);
            this.b.setClickable(false);
            cn.com.chinatelecom.account.sdk.a.a.a().a((Context) this);
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.ui.c
    public void f() {
        f.a(b()).b(g.f(this)).c(0L);
        this.q.a(j.j());
    }

    protected void g() {
        if (this.d != null) {
            this.d.setText(cn.com.chinatelecom.account.sdk.a.a.a().c());
        }
    }

    protected void h() {
        if (this.o == null) {
            return;
        }
        final b bVar = new b(this, getResources().getIdentifier("CtAuthDialog", "style", getPackageName()));
        bVar.a(new b.a() { // from class: cn.com.chinatelecom.account.sdk.ui.a.2
            @Override // cn.com.chinatelecom.account.sdk.ui.b.a
            public void a() {
                if (bVar != null) {
                    bVar.dismiss();
                }
                a.this.e();
            }
        });
    }

    public void i() {
        try {
            this.b.setEnabled(false);
            this.b.setClickable(false);
            if (this.g == null) {
                return;
            }
            this.g.setVisibility(0);
            Animation animationLoadAnimation = AnimationUtils.loadAnimation(this, getResources().getIdentifier("ct_account_rotate_anim_iv", "anim", getPackageName()));
            animationLoadAnimation.setInterpolator(new LinearInterpolator());
            this.g.startAnimation(animationLoadAnimation);
            this.f.setVisibility(8);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    protected void j() {
        try {
            this.b.setClickable(true);
            this.b.setEnabled(true);
            if (this.g == null) {
                return;
            }
            this.g.clearAnimation();
            this.g.setVisibility(8);
            this.f.setVisibility(0);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == this.o.b()) {
            c();
            return;
        }
        if (view.getId() == this.o.e()) {
            if (this.h == null || m()) {
                e();
                return;
            } else {
                h();
                return;
            }
        }
        if (view.getId() == this.o.h()) {
            f();
        } else if (view.getId() == this.o.j()) {
            a(this.q.b(null), "");
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.r = getApplicationContext();
        this.o = cn.com.chinatelecom.account.sdk.a.d.a().b();
        this.p = cn.com.chinatelecom.account.sdk.a.d.a().c();
        this.q = cn.com.chinatelecom.account.sdk.a.a.a();
        this.q.a((Context) this, false, b());
        if (this.o == null || this.o.a() == 0) {
            c();
            return;
        }
        setContentView(this.o.a());
        a();
        k();
        l();
        d();
        g();
        if (this.p != null) {
            o();
        }
    }
}