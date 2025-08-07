package cn.com.chinatelecom.account.sdk.b;

import android.R;
import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.chinatelecom.account.sdk.AuthPageConfig;
import cn.com.chinatelecom.account.sdk.AuthViewConfig;
import cn.com.chinatelecom.account.sdk.ui.e;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class b implements c {
    private Activity a;
    private AuthViewConfig b;

    public b(Activity activity, AuthViewConfig authViewConfig) {
        this.a = activity;
        this.b = authViewConfig;
    }

    private void h() {
        TextView textView;
        TextView textView2 = (TextView) this.a.findViewById(this.b.T);
        String str = this.b.af.privacyText;
        int i = this.b.af.privacyTextColor;
        int i2 = this.b.af.privacyTextSize;
        if (!TextUtils.isEmpty(str)) {
            textView2.setText(str);
        }
        if (i != 0) {
            textView2.setTextColor(i);
        }
        if (i2 != 0) {
            textView2.setTextSize(i2);
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String strA = cn.com.chinatelecom.account.sdk.a.a.a().a(this.b.af);
        String strB = cn.com.chinatelecom.account.sdk.a.a.a().b(this.b.af);
        String str2 = this.b.af.customAgreementTitle;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        if (!TextUtils.isEmpty(strA) && str.contains("$OAT")) {
            if (TextUtils.isEmpty(str2) || !str.contains("$CAT")) {
                textView = textView2;
                int iIndexOf = str.indexOf("$OAT");
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(strA);
                spannableStringBuilder2.setSpan(new a(this.a, strB, "", this.b.af.operatorAgreementTitleColor, null), 0, strA.length(), 33);
                spannableStringBuilder.replace(iIndexOf, "$OAT".length() + iIndexOf, (CharSequence) spannableStringBuilder2);
            } else {
                int iIndexOf2 = str.indexOf("$OAT");
                int iIndexOf3 = str.indexOf("$CAT");
                if (iIndexOf2 < iIndexOf3) {
                    SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(strA);
                    SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder(str2);
                    textView = textView2;
                    spannableStringBuilder3.setSpan(new a(this.a, strB, "", this.b.af.operatorAgreementTitleColor, null), 0, strA.length(), 33);
                    spannableStringBuilder4.setSpan(new a(this.a, this.b.af.customAgreementLink, str2.replace("《", "").replace("》", ""), this.b.af.customAgreementTitleColor, null), 0, str2.length(), 33);
                    spannableStringBuilder.replace(iIndexOf2, iIndexOf2 + "$OAT".length(), (CharSequence) spannableStringBuilder3);
                    int length = (iIndexOf3 + strA.length()) - "$OAT".length();
                    spannableStringBuilder.replace(length, "$CAT".length() + length, (CharSequence) spannableStringBuilder4);
                } else {
                    textView = textView2;
                    SpannableStringBuilder spannableStringBuilder5 = new SpannableStringBuilder(strA);
                    SpannableStringBuilder spannableStringBuilder6 = new SpannableStringBuilder(str2);
                    spannableStringBuilder5.setSpan(new a(this.a, strB, "", this.b.af.operatorAgreementTitleColor, null), 0, strA.length(), 33);
                    spannableStringBuilder6.setSpan(new a(this.a, this.b.af.customAgreementLink, str2.replace("《", "").replace("》", ""), this.b.af.customAgreementTitleColor, null), 0, str2.length(), 33);
                    spannableStringBuilder.replace(iIndexOf3, "$CAT".length() + iIndexOf3, (CharSequence) spannableStringBuilder6);
                    int length2 = (iIndexOf2 + str2.length()) - "$CAT".length();
                    spannableStringBuilder.replace(length2, "$OAT".length() + length2, (CharSequence) spannableStringBuilder5);
                }
            }
            textView2 = textView;
        }
        textView2.setText(spannableStringBuilder);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        textView2.setHighlightColor(this.a.getResources().getColor(R.color.transparent));
    }

    private void i() {
        TextView textView = (TextView) this.a.findViewById(this.b.T);
        if (!TextUtils.isEmpty(this.b.U)) {
            textView.setText(this.b.U);
        }
        if (this.b.V != 0) {
            textView.setTextColor(this.b.V);
        }
        if (this.b.W != 0) {
            textView.setTextSize(this.b.W);
        }
        if (TextUtils.isEmpty(this.b.U)) {
            return;
        }
        if (this.b.Y == 0 && this.b.ab == 0) {
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.b.U);
        if (this.b.Y != 0 && this.b.X < this.b.Y) {
            spannableStringBuilder.setSpan(new a(this.a, cn.com.chinatelecom.account.sdk.a.a.a().b(null), "", this.b.Z, null), this.b.X, this.b.Y, 33);
        }
        if (this.b.ab != 0 && this.b.aa < this.b.ab) {
            spannableStringBuilder.setSpan(new a(this.a, this.b.ad, this.b.ae, this.b.ac, null), this.b.aa, this.b.ab, 33);
        }
        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(this.a.getResources().getColor(R.color.transparent));
    }

    @Override // cn.com.chinatelecom.account.sdk.b.c
    public void a() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (this.b.a != 0) {
            e.a(this.a, this.b.a, this.b.b);
        }
        if (this.b.c != 0 && this.b.d != 0) {
            this.a.findViewById(this.b.c).setBackgroundColor(this.b.d);
        }
        if (this.b.e != 0 && this.b.f != 0) {
            ((ImageView) this.a.findViewById(this.b.e)).setImageResource(this.b.f);
        }
        if (this.b.g != 0) {
            TextView textView = (TextView) this.a.findViewById(this.b.g);
            if (!TextUtils.isEmpty(this.b.h)) {
                textView.setText(this.b.h);
            }
            if (this.b.i != 0) {
                textView.setTextColor(this.b.i);
            }
            if (this.b.j != 0) {
                textView.setTextSize(this.b.j);
            }
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.b.c
    public void a(final AuthPageConfig authPageConfig, final cn.com.chinatelecom.account.sdk.ui.c cVar) {
        if (this.b.aA != 0 && this.b.aB != null) {
            this.a.findViewById(this.b.aA).setOnClickListener(new View.OnClickListener() { // from class: cn.com.chinatelecom.account.sdk.b.b.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    d dVar = new d(b.this.a, b.this.b, authPageConfig, cVar);
                    if (b.this.b.aB != null) {
                        b.this.b.aB.onClick(view, dVar);
                    }
                }
            });
        }
        if (this.b.aC == null || this.b.aD == null) {
            return;
        }
        for (int i = 0; i < this.b.aC.size(); i++) {
            this.a.findViewById(this.b.aC.get(i).intValue()).setOnClickListener(new View.OnClickListener() { // from class: cn.com.chinatelecom.account.sdk.b.b.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    d dVar = new d(b.this.a, b.this.b, authPageConfig, cVar);
                    if (b.this.b.aD != null) {
                        b.this.b.aD.onClick(view, dVar);
                    }
                }
            });
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.b.c
    public void b() {
        if (this.b.k == 0) {
            return;
        }
        ImageView imageView = (ImageView) this.a.findViewById(this.b.k);
        if (this.b.o) {
            imageView.setVisibility(8);
        }
        if (this.b.l != 0) {
            imageView.setImageResource(this.b.l);
        }
        if (this.b.m != 0 && this.b.n != 0) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = this.b.m;
            layoutParams.height = this.b.n;
            imageView.setLayoutParams(layoutParams);
        }
        if (this.b.p != 0) {
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams2.topMargin = this.b.p;
            imageView.setLayoutParams(layoutParams2);
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.b.c
    public void c() {
        if (this.b.q == 0) {
            return;
        }
        TextView textView = (TextView) this.a.findViewById(this.b.q);
        if (this.b.r != 0) {
            textView.setTextColor(this.b.r);
        }
        if (this.b.s != 0) {
            textView.setTextSize(this.b.s);
        }
        if (this.b.t != 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.topMargin = this.b.t;
            textView.setLayoutParams(layoutParams);
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.b.c
    public void d() {
        if (this.b.u == 0) {
            return;
        }
        TextView textView = (TextView) this.a.findViewById(this.b.u);
        if (this.b.v != 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.topMargin = this.b.v;
            textView.setLayoutParams(layoutParams);
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.b.c
    public void e() {
        if (this.b.w != 0) {
            View viewFindViewById = this.a.findViewById(this.b.w);
            if (this.b.x != 0) {
                viewFindViewById.setBackgroundColor(this.b.x);
            }
            if (this.b.y != 0) {
                viewFindViewById.setBackgroundResource(this.b.y);
            }
            if (this.b.z != 0 && this.b.A != 0) {
                ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
                layoutParams.width = this.b.z;
                layoutParams.height = this.b.A;
                viewFindViewById.setLayoutParams(layoutParams);
            }
            if (this.b.B != 0) {
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) viewFindViewById.getLayoutParams();
                layoutParams2.topMargin = this.b.B;
                viewFindViewById.setLayoutParams(layoutParams2);
            }
        }
        if (this.b.C != 0) {
            TextView textView = (TextView) this.a.findViewById(this.b.C);
            if (!TextUtils.isEmpty(this.b.D)) {
                textView.setText(this.b.D);
            }
            if (this.b.E != 0) {
                textView.setTextColor(this.b.E);
            }
            if (this.b.F != 0) {
                textView.setTextSize(this.b.F);
            }
        }
        if (this.b.G != 0) {
            ImageView imageView = (ImageView) this.a.findViewById(this.b.G);
            if (this.b.H != 0) {
                imageView.setImageResource(this.b.H);
            }
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.b.c
    public void f() {
        if (this.b.I == 0) {
            return;
        }
        TextView textView = (TextView) this.a.findViewById(this.b.I);
        if (this.b.J != 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.topMargin = this.b.J;
            textView.setLayoutParams(layoutParams);
        }
        if (!TextUtils.isEmpty(this.b.K)) {
            textView.setText(this.b.K);
        }
        if (this.b.L != 0) {
            textView.setTextColor(this.b.L);
        }
        if (this.b.M != 0) {
            textView.setTextSize(this.b.M);
        }
        if (this.b.N) {
            textView.setVisibility(8);
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.b.c
    public void g() {
        if (this.b.O != 0) {
            View viewFindViewById = this.a.findViewById(this.b.O);
            if (this.b.P != 0) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewFindViewById.getLayoutParams();
                layoutParams.bottomMargin = this.b.P;
                viewFindViewById.setLayoutParams(layoutParams);
            }
        }
        if (this.b.Q != 0) {
            CheckBox checkBox = (CheckBox) this.a.findViewById(this.b.Q);
            if (this.b.R != 0) {
                checkBox.setButtonDrawable(this.b.R);
            }
            if (this.b.S != 0) {
                checkBox.setChecked(false);
            }
        }
        if (this.b.T != 0) {
            if (this.b.aE != null) {
                TextView textView = (TextView) this.a.findViewById(this.b.T);
                textView.setText(this.b.aE);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setHighlightColor(this.a.getResources().getColor(R.color.transparent));
                return;
            }
            if (this.b.af != null) {
                h();
            } else {
                i();
            }
        }
    }
}