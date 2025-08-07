package cn.com.chinatelecom.account.sdk.ui;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import cn.com.chinatelecom.account.sdk.AuthPageConfig;
import cn.com.chinatelecom.account.sdk.AuthViewConfig;

/* loaded from: classes.dex */
public class b extends Dialog {
    private a a;
    private Context b;
    private AuthPageConfig c;
    private AuthViewConfig d;
    private TextView e;
    private View f;
    private View g;

    public interface a {
        void a();
    }

    public b(Context context, int i) {
        super(context, i);
        this.b = context;
    }

    private void a() {
        this.e = (TextView) findViewById(this.c.l());
        this.f = findViewById(this.c.m());
        this.g = findViewById(this.c.n());
        if (this.d == null || this.d.ak == 0) {
            b();
        }
        this.f.setOnClickListener(new View.OnClickListener() { // from class: cn.com.chinatelecom.account.sdk.ui.b.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                b.this.dismiss();
            }
        });
        this.g.setOnClickListener(new View.OnClickListener() { // from class: cn.com.chinatelecom.account.sdk.ui.b.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (b.this.a != null) {
                    b.this.a.a();
                }
            }
        });
    }

    private void b() {
        try {
            CharSequence text = this.e.getText();
            if (text.length() >= 18) {
                String str = (String) this.e.getTag();
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
                spannableStringBuilder.setSpan(new cn.com.chinatelecom.account.sdk.b.a(this.b, cn.com.chinatelecom.account.sdk.a.a.a().b(null), "", 0, str), 5, 18, 33);
                this.e.setText(spannableStringBuilder);
                this.e.setMovementMethod(LinkMovementMethod.getInstance());
                this.e.setHighlightColor(this.b.getResources().getColor(R.color.transparent));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void c() {
        if (this.d.ag != 0) {
            findViewById(this.d.ag).setBackgroundResource(this.d.ah);
            if (this.d.ai != 0 && (this.f instanceof TextView) && (this.g instanceof TextView)) {
                ((TextView) this.f).setTextColor(this.d.ai);
                ((TextView) this.g).setTextColor(this.d.ai);
            }
            if (this.d.aj != 0 && (this.f instanceof TextView) && (this.g instanceof TextView)) {
                ((TextView) this.f).setTextSize(this.d.aj);
                ((TextView) this.g).setTextSize(this.d.aj);
            }
        }
        if (this.d.ak != 0) {
            if (this.d.af != null) {
                d();
            } else {
                e();
            }
        }
    }

    private void d() {
        TextView textView;
        TextView textView2 = (TextView) findViewById(this.d.ak);
        String str = this.d.af.dialogPrivacyText;
        int i = this.d.af.dialogPrivacyTextColor;
        int i2 = this.d.af.dialogPrivacyTextSize;
        if (i != 0) {
            textView2.setTextColor(i);
        }
        if (i2 != 0) {
            textView2.setTextSize(i2);
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String strA = cn.com.chinatelecom.account.sdk.a.a.a().a(this.d.af);
        String strB = cn.com.chinatelecom.account.sdk.a.a.a().b(this.d.af);
        String str2 = this.d.af.customAgreementTitle;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        if (!TextUtils.isEmpty(strA) && str.contains("$OAT")) {
            if (TextUtils.isEmpty(str2) || !str.contains("$CAT")) {
                textView = textView2;
                int iIndexOf = str.indexOf("$OAT");
                SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(strA);
                spannableStringBuilder2.setSpan(new cn.com.chinatelecom.account.sdk.b.a(this.b, strB, "", this.d.af.dialogOperatorAgreementTitleColor, null), 0, strA.length(), 33);
                spannableStringBuilder.replace(iIndexOf, "$OAT".length() + iIndexOf, (CharSequence) spannableStringBuilder2);
            } else {
                int iIndexOf2 = str.indexOf("$OAT");
                int iIndexOf3 = str.indexOf("$CAT");
                if (iIndexOf2 < iIndexOf3) {
                    SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(strA);
                    SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder(str2);
                    textView = textView2;
                    spannableStringBuilder3.setSpan(new cn.com.chinatelecom.account.sdk.b.a(this.b, strB, "", this.d.af.dialogOperatorAgreementTitleColor, null), 0, strA.length(), 33);
                    spannableStringBuilder4.setSpan(new cn.com.chinatelecom.account.sdk.b.a(this.b, this.d.af.customAgreementLink, str2.replace("《", "").replace("》", ""), this.d.af.dialogCustomAgreementTitleColor, null), 0, str2.length(), 33);
                    spannableStringBuilder.replace(iIndexOf2, iIndexOf2 + "$OAT".length(), (CharSequence) spannableStringBuilder3);
                    int length = (iIndexOf3 + strA.length()) - "$OAT".length();
                    spannableStringBuilder.replace(length, "$CAT".length() + length, (CharSequence) spannableStringBuilder4);
                } else {
                    textView = textView2;
                    SpannableStringBuilder spannableStringBuilder5 = new SpannableStringBuilder(strA);
                    SpannableStringBuilder spannableStringBuilder6 = new SpannableStringBuilder(str2);
                    spannableStringBuilder5.setSpan(new cn.com.chinatelecom.account.sdk.b.a(this.b, strB, "", this.d.af.dialogOperatorAgreementTitleColor, null), 0, strA.length(), 33);
                    spannableStringBuilder6.setSpan(new cn.com.chinatelecom.account.sdk.b.a(this.b, this.d.af.customAgreementLink, str2.replace("《", "").replace("》", ""), this.d.af.dialogCustomAgreementTitleColor, null), 0, str2.length(), 33);
                    spannableStringBuilder.replace(iIndexOf3, "$CAT".length() + iIndexOf3, (CharSequence) spannableStringBuilder6);
                    int length2 = (iIndexOf2 + str2.length()) - "$CAT".length();
                    spannableStringBuilder.replace(length2, "$OAT".length() + length2, (CharSequence) spannableStringBuilder5);
                }
            }
            textView2 = textView;
        }
        textView2.setText(spannableStringBuilder);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        textView2.setHighlightColor(this.b.getResources().getColor(R.color.transparent));
    }

    private void e() {
        TextView textView = (TextView) findViewById(this.d.ak);
        if (!TextUtils.isEmpty(this.d.al)) {
            textView.setText(this.d.al);
        }
        if (this.d.am != 0) {
            textView.setTextColor(this.d.am);
        }
        if (this.d.an != 0) {
            textView.setTextSize(this.d.an);
        }
        if (TextUtils.isEmpty(this.d.al)) {
            return;
        }
        if (this.d.ap == 0 && this.d.as == 0) {
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.d.al);
        if (this.d.ap != 0 && this.d.ao < this.d.ap) {
            spannableStringBuilder.setSpan(new cn.com.chinatelecom.account.sdk.b.a(this.b, cn.com.chinatelecom.account.sdk.a.a.a().b(null), "", this.d.aq, ""), this.d.ao, this.d.ap, 33);
        }
        if (this.d.as != 0 && this.d.ar < this.d.as) {
            spannableStringBuilder.setSpan(new cn.com.chinatelecom.account.sdk.b.a(this.b, this.d.au, this.d.av, this.d.at, ""), this.d.ar, this.d.as, 33);
        }
        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(this.b.getResources().getColor(R.color.transparent));
    }

    public void a(a aVar) {
        this.a = aVar;
        show();
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c = cn.com.chinatelecom.account.sdk.a.d.a().b();
        if (this.c == null) {
            dismiss();
            return;
        }
        this.d = cn.com.chinatelecom.account.sdk.a.d.a().c();
        setContentView(this.c.k());
        setCanceledOnTouchOutside(false);
        a();
        if (this.d != null) {
            c();
        }
    }
}