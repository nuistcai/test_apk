package cn.fly.verify.ui.component;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.GravityCompat;
import cn.com.chinatelecom.account.sdk.CtAuth;
import cn.fly.FlySDK;
import cn.fly.tools.utils.ResHelper;
import cn.fly.tools.utils.UIHandler;
import cn.fly.verify.CustomViewClickListener;
import cn.fly.verify.OAuthPageEventCallback;
import cn.fly.verify.UiLocationHelper;
import cn.fly.verify.aa;
import cn.fly.verify.ag;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.d;
import cn.fly.verify.i;
import cn.fly.verify.j;
import cn.fly.verify.k;
import cn.fly.verify.m;
import cn.fly.verify.ui.AgreementPage;
import cn.fly.verify.util.e;
import cn.fly.verify.util.l;
import cn.fly.verify.util.n;
import cn.fly.verify.util.o;
import cn.fly.verify.v;
import cn.fly.verify.y;
import com.mobile.lantian.util.PrivacyDialogUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class OneKeyLoginLayout extends RelativeLayout implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "OneKeyLoginLayout";
    private Activity activity;
    private LoginAdapter adapter;
    private CheckBox agreeCb;
    private RelativeLayout agreementRl;
    private TextView agreementTv;
    private ViewGroup bodyRl;
    private d callback;
    protected TextView centerTv;
    private ViewGroup container;
    private Context context;
    private List<View> copyCustomTitleBarViews;
    private List<View> copyCustomViews;
    private String cusOriginPrivacy1;
    private String cusOriginPrivacy2;
    private String cusOriginPrivacy3;
    private String cusPrivacy1;
    private String cusPrivacy2;
    private String cusPrivacy3;
    private CustomViewClickListener customTitleBarViewClickListener;
    private List<View> customTitleBarViews;
    private CustomViewClickListener customViewClickListener;
    private List<View> customViews;
    private String fakeNum;
    private boolean isLoadingViewHidden;
    private String jsAgreement;
    protected ImageView leftIv;
    private y listener;
    private View loadingView;
    private Button loginBtn;
    private ImageView logoIv;
    private String operatorName;
    private TextView otherTv;
    private RelativeLayout phoneRl;
    private String privacy;
    private String privacyText;
    private TextView securityPhoneTv;
    private ag settings;
    private TextView sloganTv;
    private SpannableString spannableString;
    protected RelativeLayout titleBarRl;
    private OAuthPageEventCallback.OAuthPageEventWrapper wrapper;

    public OneKeyLoginLayout(Context context, Configuration configuration, String str, y yVar) {
        super(context);
        this.cusOriginPrivacy1 = "";
        this.cusPrivacy1 = "";
        this.cusOriginPrivacy2 = "";
        this.cusPrivacy2 = "";
        this.cusOriginPrivacy3 = "";
        this.cusPrivacy3 = "";
        this.operatorName = "";
        this.operatorName = str;
        this.settings = isPortrait(context) ? aa.a().b(i.a().c()) : aa.a().a(i.a().d());
        initLayout(context, yVar);
    }

    public OneKeyLoginLayout(Context context, String str, y yVar) {
        super(context);
        this.cusOriginPrivacy1 = "";
        this.cusPrivacy1 = "";
        this.cusOriginPrivacy2 = "";
        this.cusPrivacy2 = "";
        this.cusOriginPrivacy3 = "";
        this.cusPrivacy3 = "";
        this.operatorName = "";
        this.operatorName = str;
        this.settings = isPortrait(context) ? aa.a().b(i.a().c()) : aa.a().a(i.a().d());
        initLayout(context, yVar);
    }

    public OneKeyLoginLayout(Context context, String str, y yVar, String str2) {
        super(context);
        this.cusOriginPrivacy1 = "";
        this.cusPrivacy1 = "";
        this.cusOriginPrivacy2 = "";
        this.cusPrivacy2 = "";
        this.cusOriginPrivacy3 = "";
        this.cusPrivacy3 = "";
        this.operatorName = "";
        this.operatorName = str;
        this.fakeNum = str2;
        this.settings = isPortrait(context) ? aa.a().b(i.a().c()) : aa.a().a(i.a().d());
        initLayout(context, yVar);
    }

    private void adapterReCreate() {
        this.adapter.onCreate();
    }

    private void addCustomTitlebarViews() {
        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.ui.component.OneKeyLoginLayout.4
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                OneKeyLoginLayout.this.copyCustomTitleBarViews = new ArrayList();
                if (OneKeyLoginLayout.this.customTitleBarViews != null && !OneKeyLoginLayout.this.customTitleBarViews.isEmpty()) {
                    v.a("addCustomTitlebarViews");
                    OneKeyLoginLayout.this.copyCustomTitleBarViews.addAll(OneKeyLoginLayout.this.customTitleBarViews);
                }
                if (OneKeyLoginLayout.this.copyCustomTitleBarViews != null && !OneKeyLoginLayout.this.copyCustomTitleBarViews.isEmpty()) {
                    Collections.reverse(OneKeyLoginLayout.this.copyCustomTitleBarViews);
                    for (View view : OneKeyLoginLayout.this.copyCustomTitleBarViews) {
                        view.setOnClickListener(OneKeyLoginLayout.this);
                        ViewGroup viewGroup = (ViewGroup) view.getParent();
                        if (viewGroup != null) {
                            v.a("removeTitleCustomView");
                            viewGroup.removeView(view);
                        }
                        v.a("addTitleView");
                        OneKeyLoginLayout.this.titleBarRl.addView(view, 0);
                    }
                }
                return false;
            }
        });
    }

    private void addCustomViews() {
        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.verify.ui.component.OneKeyLoginLayout.3
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                OneKeyLoginLayout.this.copyCustomViews = new ArrayList();
                if (OneKeyLoginLayout.this.customViews != null && !OneKeyLoginLayout.this.customViews.isEmpty()) {
                    v.a("copyCustomViews");
                    OneKeyLoginLayout.this.copyCustomViews.addAll(OneKeyLoginLayout.this.customViews);
                }
                if (OneKeyLoginLayout.this.copyCustomViews != null && !OneKeyLoginLayout.this.copyCustomViews.isEmpty()) {
                    Collections.reverse(OneKeyLoginLayout.this.copyCustomViews);
                    for (View view : OneKeyLoginLayout.this.copyCustomViews) {
                        view.setOnClickListener(OneKeyLoginLayout.this);
                        ViewGroup viewGroup = (ViewGroup) view.getParent();
                        if (viewGroup != null) {
                            v.a("removeCustomView");
                            viewGroup.removeView(view);
                        }
                        v.a("addView");
                        OneKeyLoginLayout.this.bodyRl.addView(view, 0);
                    }
                }
                return false;
            }
        });
    }

    private void addLoadingView() {
    }

    private void customizeUi() {
        String str;
        if (this.settings != null) {
            if (this.settings.bn() && this.activity != null) {
                this.activity.getWindow().addFlags(1024);
            } else if (this.activity != null) {
                this.activity.getWindow().clearFlags(1024);
            }
            this.titleBarRl.setBackgroundColor(this.settings.a());
            this.centerTv.setText(this.settings.b());
            this.centerTv.setTextColor(this.settings.c());
            this.centerTv.setTextSize(this.settings.ax());
            this.centerTv.setTypeface(this.settings.bm() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            this.leftIv.setImageDrawable(this.settings.d());
            this.leftIv.setScaleType(this.settings.bA());
            o.a(this.context, this.settings.aA(), this.settings.aB(), this.settings.aC(), this.settings.ay(), this.settings.az(), this.leftIv);
            this.titleBarRl.setVisibility(this.settings.x() ? 8 : 0);
            this.titleBarRl.getBackground().mutate().setAlpha(this.settings.y() ? 0 : 255);
            this.leftIv.setVisibility(this.settings.z() ? 8 : 0);
            this.container.setBackground(this.settings.A());
            this.logoIv.setImageDrawable(this.settings.e());
            this.logoIv.setVisibility(this.settings.G() ? 8 : 0);
            this.logoIv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            o.a(this.context, this.logoIv, this.settings.E(), this.settings.aY(), this.settings.F(), this.settings.aU(), this.settings.C(), this.settings.D(), this.settings.aZ());
            this.securityPhoneTv.setTextColor(this.settings.f());
            this.securityPhoneTv.setTextSize(this.settings.g());
            this.securityPhoneTv.setVisibility(this.settings.au() ? 8 : 0);
            this.securityPhoneTv.setTypeface(this.settings.bo() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            o.a(this.context, this.phoneRl, this.settings.H(), this.settings.ba(), this.settings.I(), this.settings.aV(), this.settings.bb());
            this.otherTv.setText(this.settings.k());
            this.otherTv.setTextColor(this.settings.h());
            this.otherTv.setTextSize(this.settings.i());
            this.otherTv.setTypeface(this.settings.bp() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            this.otherTv.setVisibility(this.settings.j() ? 4 : 0);
            o.a(this.context, this.otherTv, this.settings.ac(), this.settings.bc(), this.settings.ad(), this.settings.aW(), this.settings.bd());
            this.loginBtn.setBackground(this.settings.u());
            this.loginBtn.setText(this.settings.v());
            this.loginBtn.setAllCaps(false);
            this.loginBtn.setTextColor(this.settings.w());
            this.loginBtn.setTextSize(this.settings.R());
            this.loginBtn.setTypeface(this.settings.br() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            this.loginBtn.setVisibility(this.settings.aw() ? 8 : 0);
            o.a(this.context, this.loginBtn, this.settings.U(), this.settings.bf(), this.settings.V(), this.settings.aX(), this.settings.S(), this.settings.T(), this.settings.bg());
            if (this.settings.S() == -1 && this.settings.U() == -1 && this.settings.bf() == -1) {
                o.a(this.context, this.loginBtn);
            }
            this.agreeCb.setVisibility(this.settings.J() ? 8 : 0);
            if (this.settings.J()) {
                this.agreeCb.setChecked(true);
            } else {
                this.agreeCb.setChecked(this.settings.m());
            }
            this.agreeCb.setButtonDrawable(this.settings.l());
            this.agreeCb.setScaleX(this.settings.bN());
            this.agreeCb.setScaleY(this.settings.bO());
            o.a(this.context, this.agreeCb, this.settings.bB(), this.settings.bC(), this.settings.bD(), this.settings.bE());
            if (!TextUtils.isEmpty(this.settings.o())) {
                this.cusOriginPrivacy1 = this.settings.o();
                this.cusPrivacy1 = l.a("cus_privacy_pre_1", "cus_privacy_pre_1") + this.settings.o();
            }
            if (!TextUtils.isEmpty(this.settings.q())) {
                this.cusOriginPrivacy2 = this.settings.q();
                this.cusPrivacy2 = l.a("cus_privacy_pre_2", "cus_privacy_pre_2") + this.settings.q();
            }
            if (!TextUtils.isEmpty(this.settings.s())) {
                this.cusOriginPrivacy3 = this.settings.s();
                this.cusPrivacy3 = l.a("cus_privacy_pre_3", "cus_privacy_pre_3") + this.settings.s();
            }
            if (TextUtils.isEmpty(this.settings.aH()) && TextUtils.isEmpty(this.settings.aI()) && TextUtils.isEmpty(this.settings.aJ()) && TextUtils.isEmpty(this.settings.aL()) && TextUtils.isEmpty(this.settings.aK())) {
                str = String.format(l.a(PrivacyDialogUtils.KEY_PRIVACY, PrivacyDialogUtils.KEY_PRIVACY), this.privacy, this.cusPrivacy1, this.cusPrivacy2, this.cusPrivacy3);
            } else {
                this.privacyText = this.settings.aH() + this.privacy;
                if (!TextUtils.isEmpty(this.settings.o())) {
                    this.privacyText += this.settings.aI() + this.settings.o();
                }
                if (!TextUtils.isEmpty(this.settings.q())) {
                    this.privacyText += this.settings.aJ() + this.settings.q();
                }
                if (!TextUtils.isEmpty(this.settings.s())) {
                    this.privacyText += this.settings.aK() + this.settings.s();
                }
                str = this.privacyText + this.settings.aL();
            }
            this.privacyText = str;
            this.spannableString = getClickableSpan(this.privacyText, this.privacy, this.cusOriginPrivacy1, this.cusOriginPrivacy2, this.cusOriginPrivacy3, this.settings.af(), this.settings.n(), this.settings.O(), this.settings.P(), this.settings.Q());
            this.agreementTv.setText(this.spannableString);
            this.agreementTv.setTypeface(this.settings.bq() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            this.agreementTv.setTextSize(this.settings.ag());
            if (this.settings.ae()) {
                this.agreementTv.setGravity(GravityCompat.START);
            } else {
                this.agreementTv.setGravity(1);
            }
            this.agreementTv.setTextColor(this.settings.af());
            this.agreementTv.setHighlightColor(getResources().getColor(R.color.transparent));
            this.agreementRl.setVisibility(this.settings.av() ? 8 : 0);
            o.a(this.context, this.agreementRl, this.settings.K(), this.settings.L(), this.settings.M(), this.settings.N(), this.settings.be());
            if (this.settings.K() == -1 && this.settings.L() == -1) {
                o.b(this.context, this.agreementRl);
            }
            this.sloganTv.setTextColor(this.settings.aa());
            this.sloganTv.setTextSize(this.settings.Z());
            this.sloganTv.setVisibility(this.settings.ab() ? 8 : 0);
            this.sloganTv.setTypeface(this.settings.bs() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            o.a(this.context, this.sloganTv, this.settings.W(), this.settings.bh(), this.settings.X(), this.settings.Y(), this.settings.bi());
            if (this.settings.bj() != null) {
                this.spannableString = this.settings.bj();
                this.agreementTv.setText(this.spannableString);
            }
        }
    }

    private void doLogin() {
        showLoading();
        this.listener.customizeLogin();
        v.a("customizeLogin");
    }

    private void finishOauthPage() {
        try {
            CtAuth.getInstance().finishAuthActivity();
        } catch (NoClassDefFoundError e) {
        } catch (NoSuchMethodError e2) {
        }
        if (this.activity != null) {
            this.activity.finish();
            this.activity = null;
        }
        k.a().g();
    }

    private LoginAdapter getAdapter() {
        try {
            String strE = i.a().e();
            Class clsF = i.a().f();
            if (clsF == null) {
                if (TextUtils.isEmpty(strE)) {
                    return null;
                }
                clsF = Class.forName(strE);
            }
            Object objNewInstance = clsF.newInstance();
            if (objNewInstance instanceof LoginAdapter) {
                return (LoginAdapter) objNewInstance;
            }
            return null;
        } catch (Throwable th) {
            v.a(th, "getAdapter");
            return null;
        }
    }

    private SpannableString getClickableSpan(String str, String str2, String str3, String str4, String str5, int i, int i2, int i3, int i4, int i5) {
        SpannableString spannableString = new SpannableString(str);
        try {
            int iIndexOf = str.indexOf(str2);
            final boolean z = this.settings != null && this.settings.bt();
            spannableString.setSpan(new ForegroundColorSpan(i), 0, str.length(), 33);
            spannableString.setSpan(new ClickableSpan() { // from class: cn.fly.verify.ui.component.OneKeyLoginLayout.5
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    OneKeyLoginLayout.this.showAgreement();
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setUnderlineText(z);
                }
            }, iIndexOf, str2.length() + iIndexOf, 33);
            spannableString.setSpan(new ForegroundColorSpan(i2), iIndexOf, str2.length() + iIndexOf, 33);
            if (!TextUtils.isEmpty(str3)) {
                int iIndexOf2 = str.indexOf(str3);
                spannableString.setSpan(new ClickableSpan() { // from class: cn.fly.verify.ui.component.OneKeyLoginLayout.6
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        OneKeyLoginLayout.this.gotoAgreementPage(OneKeyLoginLayout.this.settings.p(), 1);
                        if (OneKeyLoginLayout.this.wrapper == null || OneKeyLoginLayout.this.wrapper.cusAgreement1Clicked == null) {
                            return;
                        }
                        OneKeyLoginLayout.this.wrapper.cusAgreement1Clicked.handle();
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setUnderlineText(z);
                    }
                }, iIndexOf2, str3.length() + iIndexOf2, 33);
                spannableString.setSpan(new ForegroundColorSpan(i3), iIndexOf2, str3.length() + iIndexOf2, 33);
            }
            if (!TextUtils.isEmpty(str4)) {
                int iLastIndexOf = str.lastIndexOf(str4);
                spannableString.setSpan(new ClickableSpan() { // from class: cn.fly.verify.ui.component.OneKeyLoginLayout.7
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        OneKeyLoginLayout.this.gotoAgreementPage(OneKeyLoginLayout.this.settings.r(), 2);
                        if (OneKeyLoginLayout.this.wrapper == null || OneKeyLoginLayout.this.wrapper.cusAgreement2Clicked == null) {
                            return;
                        }
                        OneKeyLoginLayout.this.wrapper.cusAgreement2Clicked.handle();
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setUnderlineText(z);
                    }
                }, iLastIndexOf, str4.length() + iLastIndexOf, 33);
                spannableString.setSpan(new ForegroundColorSpan(i4), iLastIndexOf, str4.length() + iLastIndexOf, 33);
            }
            if (!TextUtils.isEmpty(str5)) {
                int iLastIndexOf2 = str.lastIndexOf(str5);
                spannableString.setSpan(new ClickableSpan() { // from class: cn.fly.verify.ui.component.OneKeyLoginLayout.8
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        OneKeyLoginLayout.this.gotoAgreementPage(OneKeyLoginLayout.this.settings.t(), 3);
                        if (OneKeyLoginLayout.this.wrapper == null || OneKeyLoginLayout.this.wrapper.cusAgreement3Clicked == null) {
                            return;
                        }
                        OneKeyLoginLayout.this.wrapper.cusAgreement3Clicked.handle();
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setUnderlineText(z);
                    }
                }, iLastIndexOf2, str5.length() + iLastIndexOf2, 33);
                spannableString.setSpan(new ForegroundColorSpan(i5), iLastIndexOf2, str5.length() + iLastIndexOf2, 33);
            }
        } catch (Throwable th) {
            v.a(th, "getClickableSpan error");
        }
        return spannableString;
    }

    private ArrayList<Integer> getViewLocation(View view) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int[] iArr = new int[2];
        if (view != null) {
            view.getLocationOnScreen(iArr);
            arrayList.add(Integer.valueOf(pxToDp(iArr[0])));
            arrayList.add(Integer.valueOf(pxToDp(iArr[1])));
            arrayList.add(Integer.valueOf(pxToDp(view.getWidth())));
            arrayList.add(Integer.valueOf(pxToDp(view.getHeight())));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gotoAgreementPage(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        AgreementPage agreementPage = new AgreementPage();
        Intent intent = new Intent();
        intent.putExtra("extra_agreement_url", str);
        if (i != -1) {
            intent.putExtra("privacyType", i);
        }
        agreementPage.show(this.context, intent);
    }

    private void initLayout(Context context, y yVar) {
        this.context = context.getApplicationContext();
        this.activity = (Activity) context;
        this.listener = yVar;
        this.loadingView = k.a().f();
        this.isLoadingViewHidden = k.a().i();
        this.customViews = k.a().b();
        this.customViewClickListener = k.a().d();
        this.customTitleBarViews = k.a().c();
        this.customTitleBarViewClickListener = k.a().e();
        this.wrapper = k.a().h();
        this.callback = yVar.getCallback();
        initView();
        customizeUi();
        addCustomViews();
        addCustomTitlebarViews();
        reCreateFromAdapter();
    }

    private void initView() {
        boolean z;
        String strAF;
        String str;
        String str2;
        int i;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.context);
        View viewInflate = layoutInflaterFrom.inflate(ResHelper.getLayoutRes(this.context, "fly_verify_page_one_key_login"), (ViewGroup) null);
        this.bodyRl = (ViewGroup) viewInflate;
        this.container = (ViewGroup) layoutInflaterFrom.inflate(ResHelper.getLayoutRes(this.context, "fly_verify_container"), (ViewGroup) null);
        this.container.addView(viewInflate, new ViewGroup.LayoutParams(-1, -1));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        if (this.settings.aM()) {
            z = this.context.getApplicationInfo().targetSdkVersion < 35;
            if (this.settings.aN()) {
                layoutParams.width = -1;
                layoutParams.height = ResHelper.dipToPx(this.context, this.settings.aR());
                i = 12;
            } else {
                layoutParams.leftMargin = ResHelper.dipToPx(this.context, this.settings.aO());
                layoutParams.topMargin = ResHelper.dipToPx(this.context, this.settings.aP());
                layoutParams.width = ResHelper.dipToPx(this.context, this.settings.aQ());
                layoutParams.height = ResHelper.dipToPx(this.context, this.settings.aR());
                i = 13;
            }
            layoutParams.addRule(i);
            if (this.settings.aS() != null) {
                setBackground(this.settings.aS());
            } else {
                setBackgroundColor(ResHelper.getColorRes(this.context, "fly_verify_background_transparent"));
            }
        } else {
            z = true;
        }
        if (this.settings.bP() != null) {
            this.container.setFitsSystemWindows(this.settings.bP().booleanValue());
        } else {
            this.container.setFitsSystemWindows(z);
        }
        addView(this.container, layoutParams);
        this.logoIv = (ImageView) findViewById(ResHelper.getIdRes(this.context, "fly_verify_page_one_key_login_logo_iv"));
        this.loginBtn = (Button) findViewById(ResHelper.getIdRes(this.context, "fly_verify_page_login_login_btn"));
        this.securityPhoneTv = (TextView) findViewById(ResHelper.getIdRes(this.context, "fly_verify_page_one_key_login_phone"));
        this.agreeCb = (CheckBox) findViewById(ResHelper.getIdRes(this.context, "fly_verify_page_one_key_login_checkbox"));
        this.phoneRl = (RelativeLayout) findViewById(ResHelper.getIdRes(this.context, "fly_verify_page_one_key_login_phone_ll"));
        this.otherTv = (TextView) findViewById(ResHelper.getIdRes(this.context, "fly_verify_page_one_key_login_other_tv"));
        this.agreementRl = (RelativeLayout) findViewById(ResHelper.getIdRes(this.context, "fly_verify_page_login_agreement_container"));
        this.sloganTv = (TextView) findViewById(ResHelper.getIdRes(this.context, "fly_verify_page_login_slogan"));
        this.titleBarRl = (RelativeLayout) findViewById(ResHelper.getIdRes(this.context, "fly_verify_title_bar_container"));
        this.leftIv = (ImageView) findViewById(ResHelper.getIdRes(this.context, "fly_verify_title_bar_left"));
        this.centerTv = (TextView) findViewById(ResHelper.getIdRes(this.context, "fly_verify_title_bar_center"));
        this.agreementTv = (TextView) findViewById(ResHelper.getIdRes(this.context, "fly_verify_page_login_use_this_number"));
        this.bodyRl.setOnClickListener(this);
        this.leftIv.setOnClickListener(this);
        this.loginBtn.setOnClickListener(this);
        this.otherTv.setOnClickListener(this);
        this.titleBarRl.setOnClickListener(this);
        this.agreeCb.setOnCheckedChangeListener(this);
        if (!TextUtils.isEmpty(this.operatorName)) {
            if (TextUtils.equals(this.operatorName, "CTCC")) {
                this.sloganTv.setText(l.a("hint_service_applier", "hint service applier"));
                if (this.settings != null) {
                    strAF = this.settings.aG();
                    this.privacy = strAF;
                } else {
                    str = "agreement_tv";
                    str2 = "agreement tv";
                    strAF = l.a(str, str2);
                    this.privacy = strAF;
                }
            } else {
                if (TextUtils.equals(this.operatorName, "CMCC")) {
                    if (l.h().equals("CMHK")) {
                        this.sloganTv.setText(l.a("hint_service_applier_cmhk", "hint service applier cmhk"));
                        if (this.settings != null) {
                            strAF = this.settings.aE();
                        } else {
                            str = "agreement_tv_cmhk";
                            str2 = "agreement tv cmhk";
                            strAF = l.a(str, str2);
                        }
                    } else {
                        this.sloganTv.setText(l.a("hint_service_applier_cmcc", "hint service applier cmcc"));
                        if (this.settings != null) {
                            strAF = this.settings.aD();
                        } else {
                            str = "agreement_tv_cmcc";
                            str2 = "agreement tv cmcc";
                            strAF = l.a(str, str2);
                        }
                    }
                } else if (TextUtils.equals(this.operatorName, "CUCC")) {
                    this.sloganTv.setText(n.d(ResHelper.getStringRes(this.context, "service_name")));
                    strAF = this.settings != null ? this.settings.aF() : n.d(ResHelper.getStringRes(this.context, "service_and_privacy"));
                }
                this.privacy = strAF;
            }
        }
        this.privacyText = String.format(l.a(PrivacyDialogUtils.KEY_PRIVACY, PrivacyDialogUtils.KEY_PRIVACY), this.privacy, this.cusPrivacy1, this.cusPrivacy2, this.cusPrivacy3);
        this.agreementTv.setMovementMethod(LinkMovementMethod.getInstance());
        this.spannableString = getClickableSpan(this.privacyText, this.privacy, null, null, null, n.c(n.getColorRes(this.context, "fly_verify_text_color_common_black")), n.c(n.getColorRes(this.context, "fly_verify_main_color")), 0, 0, 0);
        this.agreementTv.setText(this.spannableString);
        this.bodyRl.getBackground().mutate().setAlpha(0);
        this.fakeNum = this.listener.getFakeNumber();
        if (TextUtils.isEmpty(this.fakeNum)) {
            return;
        }
        this.phoneRl.setVisibility(0);
        this.securityPhoneTv.setText(this.fakeNum);
    }

    private boolean isPortrait(Context context) {
        if (e.m() == 26) {
            int i = context.getResources().getConfiguration().orientation;
            v.a("configOrientation : " + i);
            return i == 1;
        }
        switch (o.a) {
            case 0:
            case 8:
                return false;
            case 1:
            case 9:
            default:
                return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void login() {
        try {
            showPrivacyDialog();
        } catch (Throwable th) {
            v.a(th, "showPrivacyDialog");
            if (this.callback != null) {
                this.callback.a(new VerifyException(m.C_ONE_KEY_OBTAIN_OPERATOR_ACCESS_TOKEN_ERR.a(), l.a(th)));
            }
            if (this.activity != null) {
                this.activity.finish();
                this.activity = null;
            }
            k.a().g();
        }
    }

    private int pxToDp(int i) {
        return ResHelper.pxToDip(FlySDK.getContext(), i);
    }

    private void reCreateFromAdapter() {
        setActivity();
        setAdapterView();
        adapterReCreate();
    }

    private void setActivity() {
        if (this.adapter == null) {
            this.adapter = getAdapter();
            if (this.adapter == null) {
                this.adapter = new LoginAdapter();
            }
        }
        this.adapter.setActivity(this.activity);
    }

    private void setAdapterView() {
        this.adapter.setBodyView(this.bodyRl);
        this.adapter.setContainerView(this.container);
        this.adapter.setTitleLayout(this.titleBarRl);
        this.adapter.setLogoImage(this.logoIv);
        this.adapter.setLoginBtn(this.loginBtn);
        this.adapter.setSecurityPhoneText(this.securityPhoneTv);
        this.adapter.setAgreementCheckbox(this.agreeCb);
        this.adapter.setPhoneLayout(this.phoneRl);
        this.adapter.setSwitchAccText(this.otherTv);
        this.adapter.setAgreementLayout(this.agreementRl);
        this.adapter.setSloganText(this.sloganTv);
        this.adapter.setLeftCloseImage(this.leftIv);
        this.adapter.setCenterText(this.centerTv);
        this.adapter.setAgreementText(this.agreementTv);
        if (!TextUtils.isEmpty(this.operatorName)) {
            this.adapter.setOperatorName(this.operatorName);
        }
        this.adapter.setOnClickListener(this);
        this.adapter.setOnCheckChangedListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void showAgreement() {
        String str;
        if (!TextUtils.isEmpty(this.operatorName)) {
            str = TextUtils.equals(this.operatorName, "CTCC") ? "https://e.189.cn/sdk/agreement/detail.do" : TextUtils.equals(this.operatorName, "CMCC") ? "CMHK".equals(l.h()) ? "https://wap.cmpassport.com/uni-access/hkContactCm.html?language=SC" : !TextUtils.isEmpty(this.jsAgreement) ? this.jsAgreement : "https://wap.cmpassport.com/resources/html/contract.html" : TextUtils.equals(this.operatorName, "CUCC") ? "https://ms.zzx9.cn/html/oauth/protocol2.html" : "";
        }
        gotoAgreementPage(str, 0);
        if (this.wrapper == null || this.wrapper.agreementClicked == null) {
            return;
        }
        this.wrapper.agreementClicked.handle();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void showHint() throws Resources.NotFoundException {
        Toast toastMakeText;
        if (this.agreeCb.isChecked()) {
            login();
            return;
        }
        if (this.settings != null) {
            if (this.settings.bk() != 0) {
                if (this.settings.bk() == 1) {
                    if (!TextUtils.isEmpty(this.spannableString)) {
                        CommonAlertDialog.showProgressDialog(this.activity, this.spannableString, new j() { // from class: cn.fly.verify.ui.component.OneKeyLoginLayout.1
                            @Override // cn.fly.verify.j
                            public void onResult(Object obj) {
                                OneKeyLoginLayout.this.login();
                            }
                        });
                        return;
                    } else {
                        if (TextUtils.isEmpty(this.privacyText)) {
                            return;
                        }
                        CommonAlertDialog.showProgressDialog(this.activity, this.privacyText, new j() { // from class: cn.fly.verify.ui.component.OneKeyLoginLayout.2
                            @Override // cn.fly.verify.j
                            public void onResult(Object obj) {
                                OneKeyLoginLayout.this.login();
                            }
                        });
                        return;
                    }
                }
                return;
            }
            if (this.settings.bu() != null) {
                toastMakeText = this.settings.bu();
            } else if (TextUtils.isEmpty(this.settings.bl())) {
                toastMakeText = Toast.makeText(this.context, ResHelper.getStringRes(this.context, "fly_verify_page_one_key_login_toast_agreement"), 0);
            } else {
                toastMakeText = Toast.makeText(this.context, this.settings.bl(), 0);
            }
        }
        toastMakeText.show();
    }

    private void showLoading() {
        if (this.isLoadingViewHidden) {
            return;
        }
        if (this.loadingView != null) {
            ViewGroup viewGroup = (ViewGroup) this.loadingView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.loadingView);
            }
            this.loadingView.bringToFront();
            this.loadingView.setVisibility(0);
            addView(this.loadingView);
            return;
        }
        if (e.m() < 17 || !(this.activity == null || this.activity.isDestroyed() || this.activity.isFinishing())) {
            CommonProgressDialog.showProgressDialog(this.activity);
        }
    }

    private void showPrivacyDialog() {
        if (cn.fly.verify.util.i.a() == 1) {
            cn.fly.verify.util.i.b();
        }
        doLogin();
    }

    public boolean getCheckboxState() {
        return this.agreeCb == null || this.agreeCb.isChecked();
    }

    public void getLocation() {
        HashMap<String, List<Integer>> map = new HashMap<>();
        map.put("container", getViewLocation(this.container));
        map.put("titleBar", getViewLocation(this.titleBarRl));
        map.put("logo", getViewLocation(this.logoIv));
        map.put("closeImg", getViewLocation(this.leftIv));
        map.put("titleText", getViewLocation(this.centerTv));
        map.put("loginBtn", getViewLocation(this.loginBtn));
        map.put("phoneNum", getViewLocation(this.phoneRl));
        map.put("argeementCheckbox", getViewLocation(this.agreeCb));
        map.put("agreementContainer", getViewLocation(this.agreementRl));
        map.put("agreementText", getViewLocation(this.agreementTv));
        map.put("slogan", getViewLocation(this.sloganTv));
        map.put("switchAcc", getViewLocation(this.otherTv));
        UiLocationHelper.getInstance().setViewLocations(map);
    }

    public LoginAdapter getLoginAdapter() {
        return this.adapter;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.wrapper == null || this.wrapper.checkboxStatusChanged == null) {
            return;
        }
        this.wrapper.checkboxStatusChanged.handle(z);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws Resources.NotFoundException {
        CustomViewClickListener customViewClickListener;
        int id = view.getId();
        if (id == this.loginBtn.getId()) {
            showHint();
            return;
        }
        if (id == this.otherTv.getId()) {
            this.listener.doOtherLogin();
            return;
        }
        if (id != this.leftIv.getId()) {
            if (id == this.bodyRl.getId()) {
                if (this.settings == null || !this.settings.B()) {
                    return;
                }
            } else {
                if (id != this.titleBarRl.getId()) {
                    if (this.customViews == null || !this.customViews.contains(view)) {
                        if (this.customTitleBarViews == null || !this.customTitleBarViews.contains(view) || this.customTitleBarViewClickListener == null) {
                            return;
                        } else {
                            customViewClickListener = this.customTitleBarViewClickListener;
                        }
                    } else if (this.customViewClickListener == null) {
                        return;
                    } else {
                        customViewClickListener = this.customViewClickListener;
                    }
                    customViewClickListener.onClick(view);
                    return;
                }
                if (this.settings == null || !this.settings.B()) {
                    return;
                }
            }
        }
        this.listener.cancelLogin();
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        try {
            getLocation();
        } catch (Throwable th) {
        }
    }

    public void resetCheckboxState(boolean z) {
        if (this.agreeCb != null) {
            this.agreeCb.setChecked(z);
        }
        if (this.settings.J()) {
            this.agreeCb.setChecked(true);
        }
    }

    public void setFakeNum(String str) {
        this.fakeNum = str;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.phoneRl.setVisibility(0);
        this.securityPhoneTv.setText(str);
    }

    public void setJsAgreement(String str) {
        this.jsAgreement = str;
    }
}