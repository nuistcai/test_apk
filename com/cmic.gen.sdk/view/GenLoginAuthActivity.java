package com.cmic.gen.sdk.view;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.InputDeviceCompat;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.ConstantsEx;
import com.cmic.gen.sdk.auth.AuthnBusiness;
import com.cmic.gen.sdk.auth.AuthnCallback;
import com.cmic.gen.sdk.auth.GenAuthnHelper;
import com.cmic.gen.sdk.auth.GenTokenListener;
import com.cmic.gen.sdk.d.EventUtils;
import com.cmic.gen.sdk.d.LogBeanEx;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.MD5STo16Byte;
import com.cmic.gen.sdk.e.NoPaddingTextviewUtils;
import com.cmic.gen.sdk.e.OverTimeUtils;
import com.cmic.gen.sdk.e.PhoneScripUtils;
import com.cmic.gen.sdk.e.SharedPreferencesUtil;
import com.cmic.gen.sdk.e.ThreadUtils;
import com.cmic.gen.sdk.e.UmcUtils;
import com.cmic.gen.sdk.view.LoginProxy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GenLoginAuthActivity extends Activity implements View.OnClickListener {
    protected static final String a = GenLoginAuthActivity.class.getSimpleName();
    private String A;
    private String C;
    private GenAuthThemeConfig E;
    private int F;
    private int G;
    private boolean H;
    private ImageView I;
    private Dialog J;
    RelativeLayout b;
    private Handler c;
    private Context d;
    private RelativeLayout e;
    private ServerClauseDialog f;
    private ServerClauseDialog g;
    private ServerClauseDialog h;
    private ServerClauseDialog i;
    private ServerClauseDialog j;
    private ArrayList<ServerClauseDialog> k;
    private ArrayList<String> l;
    private String[] m;
    private String[] n;
    private ConcurrentBundle o;
    private AuthnBusiness p;
    private CheckBox r;
    private RelativeLayout s;
    private RelativeLayout t;
    private GenTokenListener x;
    private RelativeLayout z;
    private String q = "";
    private long u = 0;
    private int v = 0;
    private a w = null;
    private boolean y = true;
    private String B = "";
    private String D = "";

    static /* synthetic */ int a(GenLoginAuthActivity genLoginAuthActivity, int i) {
        int i2 = genLoginAuthActivity.v + i;
        genLoginAuthActivity.v = i2;
        return i2;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            try {
                finish();
            } catch (Exception e) {
                if (this.o == null) {
                    this.o = new ConcurrentBundle(0);
                }
                this.o.a().a.add(e);
                LogUtils.a(a, e.toString());
                e.printStackTrace();
                a("200025", "发生未知错误", this.o, null);
                return;
            }
        }
        this.d = this;
        this.E = GenAuthnHelper.getInstance(this.d).getAuthThemeConfig();
        if (this.E != null) {
            if (this.E.getThemeId() != -1) {
                setTheme(this.E.getThemeId());
            }
            if (this.E.getAuthPageActIn() != null && this.E.getActivityOut() != null) {
                overridePendingTransition(ResourceUtil.c(this, this.E.getAuthPageActIn()), ResourceUtil.c(this, this.E.getActivityOut()));
            }
        }
        EventUtils.a("authPageIn");
        this.u = System.currentTimeMillis();
        this.p = AuthnBusiness.a(this);
        d();
        f();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        try {
            if (this.e != null) {
                this.e.postDelayed(new Runnable() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.1
                    @Override // java.lang.Runnable
                    public void run() {
                        GenLoginAuthActivity.this.setVisible(true);
                        int[] iArr = new int[2];
                        GenLoginAuthActivity.this.t.getLocationOnScreen(iArr);
                        int i = iArr[1];
                        LogUtils.b(GenLoginAuthActivity.a, "mPhoneLayout" + iArr[1]);
                        GenLoginAuthActivity.this.e.getLocationOnScreen(iArr);
                        int i2 = iArr[1];
                        LogUtils.b(GenLoginAuthActivity.a, "mPhoneLayout" + iArr[1]);
                        GenLoginAuthActivity.this.s.getLocationOnScreen(iArr);
                        int i3 = iArr[1];
                        LogUtils.b(GenLoginAuthActivity.a, "mPhoneLayout" + iArr[1]);
                        if (i <= i2 && i <= i3) {
                            GenLoginAuthActivity.this.t.setFocusableInTouchMode(true);
                            GenLoginAuthActivity.this.t.requestFocus();
                        } else if (i2 < i3) {
                            GenLoginAuthActivity.this.e.setFocusableInTouchMode(true);
                            GenLoginAuthActivity.this.e.requestFocus();
                        } else {
                            GenLoginAuthActivity.this.r.setFocusableInTouchMode(true);
                            GenLoginAuthActivity.this.r.requestFocus();
                        }
                    }
                }, 500L);
            }
            if (this.o != null) {
                this.o.a("loginMethod", "loginAuth");
            }
            GenAuthnHelper.getInstance((Context) this).loginPageInCallBack("200087");
        } catch (Exception e) {
            e.printStackTrace();
            this.o.a().a.add(e);
            a("200025", "发生未知错误", this.o, null);
        }
    }

    private void d() {
        this.o = OverTimeUtils.d(getIntent().getStringExtra("traceId"));
        if (this.o == null) {
            this.o = new ConcurrentBundle(0);
        }
        this.x = OverTimeUtils.c(this.o.b("traceId", ""));
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.c = new Handler(getMainLooper());
        this.w = new a(this);
        this.q = this.o.b("securityphone");
        LogUtils.b(a, "mSecurityPhone value is " + this.q);
        String strB = this.o.b("operatortype", "");
        LogUtils.b(a, "operator value is " + strB);
        String strB2 = SharedPreferencesUtil.b("operator1", "");
        String strB3 = SharedPreferencesUtil.b("operator2", "");
        String strB4 = SharedPreferencesUtil.b("operator3", "");
        String strB5 = SharedPreferencesUtil.b("operator4", "");
        if (!TextUtils.isEmpty(strB2) && !TextUtils.isEmpty(strB3) && !TextUtils.isEmpty(strB4) && !TextUtils.isEmpty(strB5)) {
            LogUtils.b(a, "读取接口协议配置缓存 ");
            try {
                JSONObject jSONObject = new JSONObject(SharedPreferencesUtil.b("operator" + strB, ""));
                Iterator<String> itKeys = jSONObject.keys();
                this.m = new String[3];
                String[] strArr = new String[3];
                for (int i = 0; itKeys.hasNext() && i < 3; i++) {
                    String next = itKeys.next();
                    LogUtils.b(a, "读取接口协议 " + jSONObject.optString(next).split("\\^")[0]);
                    LogUtils.b(a, "读取接口协议url " + jSONObject.optString(next).split("\\^")[1]);
                    this.m[i] = jSONObject.optString(next).split("\\^")[0];
                    strArr[i] = jSONObject.optString(next).split("\\^")[1];
                }
                this.A = this.m[this.E.getAppLanguageType()];
                this.B = strArr[this.E.getAppLanguageType()];
            } catch (Exception e) {
                e.printStackTrace();
                a(strB);
            }
        } else {
            a(strB);
        }
        int privacyDialogThemeId = this.E.getPrivacyDialogThemeId();
        if (privacyDialogThemeId == -1) {
            privacyDialogThemeId = R.style.Theme.Translucent.NoTitleBar;
        }
        this.f = new ServerClauseDialog(this.d, privacyDialogThemeId, this.A, this.B);
        this.f.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.6
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == 4 && event.getAction() == 1 && event.getRepeatCount() == 0) {
                    GenLoginAuthActivity.this.f.b();
                }
                return true;
            }
        });
        this.k = new ArrayList<>();
        this.l = new ArrayList<>();
        if (!TextUtils.isEmpty(this.E.getClauseUrl())) {
            this.g = new ServerClauseDialog(this.d, privacyDialogThemeId, this.E.getClauseName(), this.E.getClauseUrl());
            this.g.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.7
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == 4 && event.getAction() == 1 && event.getRepeatCount() == 0) {
                        GenLoginAuthActivity.this.g.b();
                    }
                    return true;
                }
            });
            this.k.add(this.g);
            this.l.add(this.E.getClauseName());
        }
        if (!TextUtils.isEmpty(this.E.getClauseUrl2())) {
            this.h = new ServerClauseDialog(this.d, privacyDialogThemeId, this.E.getClauseName2(), this.E.getClauseUrl2());
            this.h.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.8
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == 4 && event.getAction() == 1 && event.getRepeatCount() == 0) {
                        GenLoginAuthActivity.this.h.b();
                    }
                    return true;
                }
            });
            this.k.add(this.h);
            this.l.add(this.E.getClauseName2());
        }
        if (!TextUtils.isEmpty(this.E.getClauseUrl3())) {
            this.i = new ServerClauseDialog(this.d, privacyDialogThemeId, this.E.getClauseName3(), this.E.getClauseUrl3());
            this.i.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.9
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == 4 && event.getAction() == 1 && event.getRepeatCount() == 0) {
                        GenLoginAuthActivity.this.i.b();
                    }
                    return true;
                }
            });
            this.k.add(this.i);
            this.l.add(this.E.getClauseName3());
        }
        if (!TextUtils.isEmpty(this.E.getClauseUrl4())) {
            this.j = new ServerClauseDialog(this.d, R.style.Theme.Translucent.NoTitleBar, this.E.getClauseName4(), this.E.getClauseUrl4());
            this.j.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.10
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == 4 && event.getAction() == 1 && event.getRepeatCount() == 0) {
                        GenLoginAuthActivity.this.j.b();
                    }
                    return true;
                }
            });
            this.k.add(this.j);
            this.l.add(this.E.getClauseName4());
        }
        k();
        if (this.E.isPrivacyBookSymbol()) {
            for (int i2 = 0; i2 < this.l.size(); i2++) {
                String str = String.format("《%s》", this.l.get(i2));
                this.C = this.C.replaceFirst(this.l.get(i2), str);
                this.l.set(i2, str);
            }
        }
        LoginProxy.a().a(new LoginProxy.a() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.11
            @Override // com.cmic.gen.sdk.view.LoginProxy.a
            public void a() {
                GenLoginAuthActivity.this.c.removeCallbacksAndMessages(null);
                if (GenLoginAuthActivity.this.f != null && GenLoginAuthActivity.this.f.isShowing()) {
                    GenLoginAuthActivity.this.f.dismiss();
                }
                if (GenLoginAuthActivity.this.g != null && GenLoginAuthActivity.this.g.isShowing()) {
                    GenLoginAuthActivity.this.g.dismiss();
                }
                GenLoginAuthActivity.this.a(true);
            }
        });
    }

    public void a(String str) {
        if (this.E.getAppLanguageType() == 1) {
            this.m = ConstantsEx.b;
            this.n = ConstantsEx.e;
            this.D = ConstantsEx.h[1];
        } else if (this.E.getAppLanguageType() == 2) {
            this.m = ConstantsEx.c;
            this.n = ConstantsEx.f;
            this.D = ConstantsEx.h[2];
        } else {
            this.m = ConstantsEx.a;
            this.n = ConstantsEx.d;
            this.D = ConstantsEx.h[0];
        }
        if (str.equals("1")) {
            this.A = this.m[0];
            this.B = this.n[0];
        } else if (str.equals("3")) {
            this.A = this.m[1];
            this.B = this.n[1];
        } else if (str.equals("2")) {
            this.A = this.m[2];
            this.B = this.n[2];
        } else {
            this.A = this.m[3];
            this.B = this.n[3];
        }
    }

    private void e() {
        int privacyMarginLeft;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.t.getLayoutParams();
        if (this.E.getNumFieldOffsetY() > 0 || this.E.getNumFieldOffsetY_B() < 0) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            this.t.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            LogUtils.b(a, "mPhoneLayout.getMeasuredHeight()=" + this.t.getMeasuredHeight());
            if (this.E.getNumFieldOffsetY() > 0 && (this.F - this.t.getMeasuredHeight()) - UmcActivityUtil.a(this.d, this.E.getNumFieldOffsetY()) > 0) {
                LogUtils.b(a, "numberField_top");
                layoutParams.addRule(10, -1);
                layoutParams.setMargins(0, UmcActivityUtil.a(this.d, this.E.getNumFieldOffsetY()), 0, 0);
            } else {
                layoutParams.addRule(12, -1);
            }
        } else if (this.E.getNumFieldOffsetY_B() > 0 && (this.F - this.t.getMeasuredHeight()) - UmcActivityUtil.a(this.d, this.E.getNumFieldOffsetY_B()) > 0) {
            LogUtils.b(a, "numberField_bottom");
            layoutParams.addRule(12, -1);
            layoutParams.setMargins(0, 0, 0, UmcActivityUtil.a(this.d, this.E.getNumFieldOffsetY_B()));
        } else {
            layoutParams.addRule(10, -1);
        }
        this.t.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.e.getLayoutParams();
        int iMax = Math.max(this.E.getLogBtnMarginLeft(), 0);
        int iMax2 = Math.max(this.E.getLogBtnMarginRight(), 0);
        if (this.E.getLogBtnOffsetY() > 0 || this.E.getLogBtnOffsetY_B() < 0) {
            if (this.E.getLogBtnOffsetY() > 0 && this.F - UmcActivityUtil.a(this.d, this.E.getLogBtnHeight() + this.E.getLogBtnOffsetY()) > 0) {
                LogUtils.b(a, "logBtn_top");
                layoutParams2.addRule(10, -1);
                layoutParams2.setMargins(UmcActivityUtil.a(this.d, iMax), UmcActivityUtil.a(this.d, this.E.getLogBtnOffsetY()), UmcActivityUtil.a(this.d, iMax2), 0);
            } else {
                layoutParams2.addRule(12, -1);
                layoutParams2.setMargins(UmcActivityUtil.a(this.d, iMax), 0, UmcActivityUtil.a(this.d, iMax2), 0);
            }
        } else if (this.E.getLogBtnOffsetY_B() > 0 && this.F - UmcActivityUtil.a(this.d, this.E.getLogBtnHeight() + this.E.getLogBtnOffsetY_B()) > 0) {
            LogUtils.b(a, "logBtn_bottom");
            layoutParams2.addRule(12, -1);
            layoutParams2.setMargins(UmcActivityUtil.a(this.d, iMax), 0, UmcActivityUtil.a(this.d, iMax2), UmcActivityUtil.a(this.d, this.E.getLogBtnOffsetY_B()));
        } else {
            layoutParams2.addRule(10, -1);
            layoutParams2.setMargins(UmcActivityUtil.a(this.d, iMax), 0, UmcActivityUtil.a(this.d, iMax2), 0);
        }
        this.e.setLayoutParams(layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.s.getLayoutParams();
        if (this.E.getPrivacyMarginLeft() >= 0) {
            privacyMarginLeft = this.E.getCheckedImgWidth() > 30 ? this.E.getPrivacyMarginLeft() : this.E.getPrivacyMarginLeft() - (30 - this.E.getCheckedImgWidth());
        } else {
            privacyMarginLeft = this.E.getCheckedImgWidth() > 30 ? 0 : -(30 - this.E.getCheckedImgWidth());
        }
        int iMax3 = Math.max(this.E.getPrivacyMarginRight(), 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.s.measure(iMakeMeasureSpec2, iMakeMeasureSpec2);
        if (this.E.getPrivacyOffsetY() > 0 || this.E.getPrivacyOffsetY_B() < 0) {
            if (this.E.getPrivacyOffsetY() > 0 && (this.F - this.s.getMeasuredHeight()) - UmcActivityUtil.a(this.d, this.E.getPrivacyOffsetY()) > 0) {
                LogUtils.b(a, "privacy_top = " + this.s.getMeasuredHeight());
                layoutParams3.addRule(10, -1);
                layoutParams3.setMargins(UmcActivityUtil.a(this.d, privacyMarginLeft), UmcActivityUtil.a(this.d, this.E.getPrivacyOffsetY()), UmcActivityUtil.a(this.d, iMax3), 0);
            } else {
                LogUtils.b(a, "privacy_bottom=" + privacyMarginLeft);
                layoutParams3.addRule(12, -1);
                layoutParams3.setMargins(UmcActivityUtil.a(this.d, privacyMarginLeft), 0, UmcActivityUtil.a(this.d, iMax3), 0);
            }
        } else if (this.E.getPrivacyOffsetY_B() > 0 && (this.F - this.s.getMeasuredHeight()) - UmcActivityUtil.a(this.d, this.E.getPrivacyOffsetY_B()) > 0) {
            LogUtils.b(a, "privacy_bottom=" + this.s.getMeasuredHeight());
            layoutParams3.addRule(12, -1);
            layoutParams3.setMargins(UmcActivityUtil.a(this.d, privacyMarginLeft), 0, UmcActivityUtil.a(this.d, iMax3), UmcActivityUtil.a(this.d, this.E.getPrivacyOffsetY_B()));
        } else {
            layoutParams3.addRule(10, -1);
            layoutParams3.setMargins(UmcActivityUtil.a(this.d, privacyMarginLeft), 0, UmcActivityUtil.a(this.d, iMax3), 0);
            LogUtils.b(a, "privacy_top");
        }
        this.s.setLayoutParams(layoutParams3);
    }

    private void f() {
        int i;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
            getWindow().addFlags(134217728);
            if (this.E.getStatusBarColor() != 0) {
                getWindow().addFlags(Integer.MIN_VALUE);
                getWindow().clearFlags(67108864);
                getWindow().setStatusBarColor(this.E.getStatusBarColor());
                getWindow().setNavigationBarColor(this.E.getStatusBarColor());
            }
        }
        if (Build.VERSION.SDK_INT >= 23 && this.E.isLightColor()) {
            i = 8192;
        } else {
            i = 0;
        }
        if (this.E.isNavHidden()) {
            i |= InputDeviceCompat.SOURCE_TOUCHSCREEN;
        }
        getWindow().getDecorView().setSystemUiVisibility(i);
        this.b = new RelativeLayout(this);
        this.b.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        View contentView = this.E.getContentView();
        if (contentView != null) {
            ViewParent parent = contentView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(contentView);
            }
            this.b.addView(contentView);
        } else if (this.E.getLayoutResID() != -1) {
            getLayoutInflater().inflate(this.E.getLayoutResID(), this.b);
        }
        setContentView(this.b);
        int requestedOrientation = getRequestedOrientation();
        this.F = UmcActivityUtil.b(this.d);
        this.G = UmcActivityUtil.a(this.d);
        if ((requestedOrientation == 1 && this.G > this.F) || (requestedOrientation == 0 && this.G < this.F)) {
            int i2 = this.G;
            this.G = this.F;
            this.F = i2;
        }
        LogUtils.b(a, "orientation = " + requestedOrientation + "--screenWidth = " + this.G + "--screenHeight = " + this.F);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (this.E.getWindowWidth() != 0) {
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            getWindowManager().getDefaultDisplay().getSize(new Point());
            attributes.width = UmcActivityUtil.a(this.d, this.E.getWindowWidth());
            attributes.height = UmcActivityUtil.a(this.d, this.E.getWindowHeight());
            this.G = attributes.width;
            this.F = attributes.height;
            attributes.x = UmcActivityUtil.a(this.d, this.E.getWindowX());
            if (this.E.getWindowBottom() == 1) {
                getWindow().setGravity(80);
            } else {
                attributes.y = UmcActivityUtil.a(this.d, this.E.getWindowY());
            }
            getWindow().setAttributes(attributes);
        }
        this.b.setFitsSystemWindows(this.E.isFitsSystemWindows());
        this.b.setClipToPadding(true);
        try {
            g();
            h();
            this.b.addView(this.t);
            this.b.addView(i());
            this.b.addView(j());
            e();
            this.e.setOnClickListener(this);
            this.z.setOnClickListener(this);
            this.r.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.12
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean b2) {
                    if (GenLoginAuthActivity.this.E.getGenCheckedChangeListener() != null) {
                        GenLoginAuthActivity.this.E.getGenCheckedChangeListener().onCheckedChanged(b2);
                    }
                    if (b2) {
                        if (GenLoginAuthActivity.this.I != null) {
                            GenLoginAuthActivity.this.I.setVisibility(0);
                        }
                        GenLoginAuthActivity.this.e.setEnabled(true);
                        try {
                            GenLoginAuthActivity.this.r.setBackgroundResource(ResourceUtil.b(GenLoginAuthActivity.this, GenLoginAuthActivity.this.E.getCheckedImgPath()));
                        } catch (Exception e) {
                            GenLoginAuthActivity.this.r.setBackgroundResource(ResourceUtil.b(GenLoginAuthActivity.this, "umcsdk_check_image"));
                        }
                        GenLoginAuthActivity.this.r.setContentDescription("复选框 已勾选 " + GenLoginAuthActivity.this.C);
                        return;
                    }
                    if (GenLoginAuthActivity.this.I != null) {
                        GenLoginAuthActivity.this.I.setVisibility(4);
                    }
                    GenLoginAuthActivity.this.e.setEnabled(GenLoginAuthActivity.this.o());
                    try {
                        GenLoginAuthActivity.this.r.setBackgroundResource(ResourceUtil.b(GenLoginAuthActivity.this, GenLoginAuthActivity.this.E.getUncheckedImgPath()));
                    } catch (Exception e2) {
                        GenLoginAuthActivity.this.r.setBackgroundResource(ResourceUtil.b(GenLoginAuthActivity.this, "umcsdk_uncheck_image"));
                    }
                    GenLoginAuthActivity.this.r.setContentDescription("复选框 请双击勾选 " + GenLoginAuthActivity.this.C);
                }
            });
            l();
            try {
                if (this.E.isPrivacyState()) {
                    if (this.I != null) {
                        this.I.setVisibility(0);
                    }
                    this.r.setChecked(true);
                    this.r.setBackgroundResource(ResourceUtil.b(this, this.E.getCheckedImgPath()));
                    this.e.setEnabled(true);
                    this.r.setContentDescription("复选框 已勾选 " + this.C);
                    return;
                }
                this.r.setChecked(false);
                if (this.I != null) {
                    this.I.setVisibility(4);
                }
                this.e.setEnabled(o());
                this.r.setBackgroundResource(ResourceUtil.b(this, this.E.getUncheckedImgPath()));
                this.r.setContentDescription("复选框 请双击勾选 " + this.C);
            } catch (Exception e) {
                this.r.setChecked(false);
            }
        } catch (Exception e2) {
            LogBeanEx.b.add(e2);
            e2.printStackTrace();
            LogUtils.a(a, e2.toString());
            a("200040", "UI资源加载异常", this.o, null);
        }
    }

    private void g() {
        final String strB = SharedPreferencesUtil.b("imageUrl", "");
        String strB2 = SharedPreferencesUtil.b("displayLogo", "");
        LogUtils.c(a, "logoimgUrl=" + strB);
        LogUtils.c(a, "displaylogo=" + strB2);
        LogUtils.c(a, "config.isDisplayLogo()=" + this.E.isDisplayLogo());
        if (!TextUtils.isEmpty(strB) && strB2.equals("1") && this.E.isDisplayLogo()) {
            this.I = new ImageView(this.d);
            this.I.setVisibility(4);
            ThreadUtils.a(new ThreadUtils.a() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.13
                @Override // com.cmic.gen.sdk.e.ThreadUtils.a
                protected void a() {
                    final Bitmap bitmapDecodeFile;
                    try {
                        File externalFilesDir = GenLoginAuthActivity.this.d.getExternalFilesDir("LogoFile");
                        if (!externalFilesDir.exists()) {
                            LogUtils.c(GenLoginAuthActivity.a, "logo文件夹不存在");
                            if (!externalFilesDir.mkdirs()) {
                                LogUtils.c(GenLoginAuthActivity.a, "logo文件夹创建失败");
                            }
                        }
                        String absolutePath = new File(externalFilesDir + "/" + MD5STo16Byte.a(strB)).getAbsolutePath();
                        LogUtils.c(GenLoginAuthActivity.a, "logo文件路径" + absolutePath);
                        File file = new File(absolutePath);
                        if (file.isFile() && file.exists()) {
                            LogUtils.c(GenLoginAuthActivity.a, "读取本地文件");
                            bitmapDecodeFile = BitmapFactory.decodeFile(absolutePath);
                        } else {
                            LogUtils.c(GenLoginAuthActivity.a, "读取网络文件");
                            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(new URL(strB).openStream());
                            try {
                                bitmapDecodeStream.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(absolutePath));
                                LogUtils.c(GenLoginAuthActivity.a, "保存文件到本地");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            bitmapDecodeFile = bitmapDecodeStream;
                        }
                        GenLoginAuthActivity.this.b.post(new Runnable() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.13.1
                            /* JADX WARN: Removed duplicated region for block: B:12:0x00e5  */
                            /* JADX WARN: Removed duplicated region for block: B:16:0x0116  */
                            /* JADX WARN: Removed duplicated region for block: B:25:0x0177  */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public void run() {
                                int iA;
                                int i;
                                int iA2;
                                GenLoginAuthActivity.this.I.setImageBitmap(bitmapDecodeFile);
                                int logoOffsetX = GenLoginAuthActivity.this.E.getLogoOffsetX();
                                int logoOffsetY = GenLoginAuthActivity.this.E.getLogoOffsetY();
                                int logoOffsetY_B = GenLoginAuthActivity.this.E.getLogoOffsetY_B();
                                int iA3 = UmcActivityUtil.a(GenLoginAuthActivity.this.d, GenLoginAuthActivity.this.E.getLogoWidth());
                                int iA4 = UmcActivityUtil.a(GenLoginAuthActivity.this.d, GenLoginAuthActivity.this.E.getLogoHeight());
                                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(iA3, iA4);
                                if (logoOffsetX >= 0) {
                                    float f = logoOffsetX;
                                    if ((GenLoginAuthActivity.this.G - iA3) - UmcActivityUtil.a(GenLoginAuthActivity.this.d, f) > 0) {
                                        iA = UmcActivityUtil.a(GenLoginAuthActivity.this.d, f);
                                        float f2 = logoOffsetY;
                                        LogUtils.b("lyy", GenLoginAuthActivity.this.F + "-" + iA4 + "--" + UmcActivityUtil.a(GenLoginAuthActivity.this.d, f2));
                                        if (logoOffsetY < 0) {
                                            if ((GenLoginAuthActivity.this.F - iA4) - UmcActivityUtil.a(GenLoginAuthActivity.this.d, f2) > 0) {
                                                iA2 = UmcActivityUtil.a(GenLoginAuthActivity.this.d, f2);
                                                i = 0;
                                            } else {
                                                LogUtils.b(GenLoginAuthActivity.a, "logo ALIGN_PARENT_BOTTOM");
                                                layoutParams.addRule(12);
                                                iA2 = 0;
                                                i = 0;
                                            }
                                        } else {
                                            if (logoOffsetY_B >= 0) {
                                                float f3 = logoOffsetY_B;
                                                if ((GenLoginAuthActivity.this.F - iA4) - UmcActivityUtil.a(GenLoginAuthActivity.this.d, f3) > 0) {
                                                    int iA5 = UmcActivityUtil.a(GenLoginAuthActivity.this.d, f3);
                                                    layoutParams.addRule(12);
                                                    i = iA5;
                                                    iA2 = 0;
                                                } else {
                                                    LogUtils.b(GenLoginAuthActivity.a, "logo ALIGN_PARENT_RIGHT");
                                                    layoutParams.addRule(10);
                                                }
                                            } else {
                                                LogUtils.b(GenLoginAuthActivity.a, "log 默认样式");
                                                layoutParams.addRule(6, 13107);
                                            }
                                            iA2 = 0;
                                            i = 0;
                                        }
                                        layoutParams.setMargins(iA, iA2, 0, i);
                                        GenLoginAuthActivity.this.I.setLayoutParams(layoutParams);
                                        if (GenLoginAuthActivity.this.E.getLogoScaleType() != null) {
                                            GenLoginAuthActivity.this.I.setScaleType(GenLoginAuthActivity.this.E.getLogoScaleType());
                                        }
                                        GenLoginAuthActivity.this.b.addView(GenLoginAuthActivity.this.I);
                                    }
                                    LogUtils.b(GenLoginAuthActivity.a, "logo ALIGN_PARENT_RIGHT");
                                    layoutParams.addRule(11);
                                } else {
                                    layoutParams.addRule(1, 13107);
                                }
                                iA = 0;
                                float f22 = logoOffsetY;
                                LogUtils.b("lyy", GenLoginAuthActivity.this.F + "-" + iA4 + "--" + UmcActivityUtil.a(GenLoginAuthActivity.this.d, f22));
                                if (logoOffsetY < 0) {
                                }
                                layoutParams.setMargins(iA, iA2, 0, i);
                                GenLoginAuthActivity.this.I.setLayoutParams(layoutParams);
                                if (GenLoginAuthActivity.this.E.getLogoScaleType() != null) {
                                }
                                GenLoginAuthActivity.this.b.addView(GenLoginAuthActivity.this.I);
                            }
                        });
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }
    }

    private void h() {
        this.t = new RelativeLayout(this);
        this.t.setId(13107);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        this.t.setLayoutParams(layoutParams);
        TextView textView = new TextView(this);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        textView.setGravity(15);
        int numberOffsetX = this.E.getNumberOffsetX();
        if (numberOffsetX == 0) {
            layoutParams.addRule(13);
        } else if (numberOffsetX > 0) {
            float f = numberOffsetX;
            if ((this.G - textView.getWidth()) - UmcActivityUtil.a(this.d, f) > 0) {
                layoutParams2.setMargins(UmcActivityUtil.a(this.d, f), 0, 0, 0);
            } else {
                LogUtils.b(a, "RelativeLayout.ALIGN_PARENT_RIGHT");
                layoutParams2.addRule(11);
            }
        }
        try {
            textView.setTextSize(2, this.E.getNumberSize());
            textView.setContentDescription(this.q.replace("****", "星星星星").replaceAll("(.{1})", "$1 "));
        } catch (Exception e) {
            textView.setTextSize(2, 18.0f);
        }
        NoPaddingTextviewUtils.a(textView, this.q);
        if (this.E.isNumberBold()) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        textView.setId(30583);
        this.t.addView(textView, layoutParams2);
        try {
            textView.setTextColor(this.E.getNumberColor());
        } catch (Exception e2) {
            textView.setTextColor(-13421773);
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.t.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        LogUtils.b(a, "mPhoneLayout.getMeasuredHeight()=" + this.t.getMeasuredHeight());
    }

    private RelativeLayout i() {
        this.e = new RelativeLayout(this);
        this.e.setId(17476);
        this.e.setLayoutParams(new RelativeLayout.LayoutParams(UmcActivityUtil.a(this.d, this.E.getLogBtnWidth()), UmcActivityUtil.a(this.d, this.E.getLogBtnHeight())));
        TextView textView = new TextView(this);
        textView.setTextSize(2, this.E.getLogBtnTextSize());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        textView.setLayoutParams(layoutParams);
        if (this.E.isLogBtnTextBold()) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        this.e.addView(textView);
        textView.setText(this.E.getLogBtnText());
        try {
            textView.setTextColor(this.E.getLogBtnTextColor());
        } catch (Exception e) {
            textView.setTextColor(-1);
        }
        try {
            this.e.setBackgroundResource(ResourceUtil.b(this.d, this.E.getLogBtnBackgroundPath()));
        } catch (Exception e2) {
            e2.printStackTrace();
            this.e.setBackgroundResource(ResourceUtil.b(this.d, "umcsdk_login_btn_bg"));
        }
        return this.e;
    }

    private RelativeLayout j() {
        this.s = new RelativeLayout(this);
        this.s.setHorizontalGravity(1);
        this.s.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        int checkedImgWidth = this.E.getCheckedImgWidth();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(UmcActivityUtil.a(this.d, Math.max(checkedImgWidth, 30)), UmcActivityUtil.a(this.d, Math.max(this.E.getCheckedImgHeight(), 30)));
        if (this.E.getCheckBoxLocation() == 1) {
            layoutParams.addRule(15, -1);
        }
        this.z = new RelativeLayout(this);
        this.z.setId(34952);
        this.z.setLayoutParams(layoutParams);
        this.r = new CheckBox(this);
        this.r.setChecked(false);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(UmcActivityUtil.a(this.d, this.E.getCheckedImgWidth()), UmcActivityUtil.a(this.d, this.E.getCheckedImgHeight()));
        layoutParams2.setMargins(UmcActivityUtil.a(this.d, checkedImgWidth > 30 ? 0.0f : 30 - checkedImgWidth), 0, 0, 0);
        layoutParams2.addRule(11, -1);
        if (this.E.getCheckBoxLocation() == 1) {
            layoutParams2.addRule(15, -1);
        }
        this.r.setLayoutParams(layoutParams2);
        this.z.addView(this.r);
        this.s.addView(this.z);
        TextView textView = new TextView(this);
        textView.setTextSize(2, this.E.getPrivacyTextSize());
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.setMargins(UmcActivityUtil.a(this.d, 5.0f), 0, 0, UmcActivityUtil.a(this.d, 5.0f));
        layoutParams3.addRule(1, 34952);
        textView.setLayoutParams(layoutParams3);
        this.s.addView(textView);
        textView.setTextColor(this.E.getClauseBaseColor());
        SpannableString spannableStringA = UmcActivityUtil.a(this, this.C, this.A, this.B, this.f, this.k, this.l);
        Typeface typefaceDefaultFromStyle = Typeface.defaultFromStyle(0);
        for (int i = 0; i < this.C.length(); i++) {
            if (this.C.charAt(i) == 12289) {
                spannableStringA.setSpan(new StyleSpan(typefaceDefaultFromStyle.getStyle()), i, i + 1, 33);
            }
        }
        textView.setText(spannableStringA);
        textView.setLineSpacing(8.0f, 1.0f);
        textView.setIncludeFontPadding(false);
        if (this.E.isPrivacyTextBold()) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        if (this.E.isPrivacyTextGravityCenter()) {
            textView.setGravity(17);
        }
        textView.setHighlightColor(getResources().getColor(R.color.transparent));
        this.s.setOnClickListener(new View.OnClickListener() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                GenLoginAuthActivity.this.r.setChecked(!GenLoginAuthActivity.this.r.isChecked());
            }
        });
        textView.setMovementMethod(new LinkMovementMethod() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.3
            @Override // android.text.method.LinkMovementMethod, android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
            public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
                boolean zOnTouchEvent = super.onTouchEvent(widget, buffer, event);
                if (!zOnTouchEvent && event.getAction() == 1) {
                    ViewParent parent = widget.getParent();
                    if (parent instanceof ViewGroup) {
                        return ((ViewGroup) parent).performClick();
                    }
                }
                return zOnTouchEvent;
            }
        });
        this.r.setButtonDrawable(new ColorDrawable());
        try {
            this.r.setBackgroundResource(ResourceUtil.b(this, this.E.getUncheckedImgPath()));
        } catch (Exception e) {
            this.r.setBackgroundResource(ResourceUtil.b(this, "umcsdk_uncheck_image"));
        }
        return this.s;
    }

    private String k() {
        this.C = this.E.getPrivacy();
        if (this.E.isPrivacyBookSymbol()) {
            this.A = String.format("《%s》", this.A);
        }
        if (this.C.contains(GenAuthThemeConfig.PLACEHOLDER)) {
            this.C = this.C.replace(GenAuthThemeConfig.PLACEHOLDER, this.A);
        }
        return this.C;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        this.e.setClickable(true);
        this.r.setClickable(true);
    }

    private void m() {
        this.e.setClickable(false);
        this.r.setClickable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        try {
            EventUtils.a("authPageOut");
            a("200020", "登录页面关闭", this.o, null);
        } catch (Exception e) {
            LogBeanEx.b.add(e);
            e.printStackTrace();
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 4 && !event.isCanceled() && event.getRepeatCount() == 0) {
            if (this.E.getGenBackPressedListener() != null) {
                this.E.getGenBackPressedListener().onBackPressed();
            }
            if (this.E.getWindowWidth() == 0 || this.E.isBackButton()) {
                a(false);
                return true;
            }
            return true;
        }
        return true;
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        try {
            this.c.removeCallbacksAndMessages(null);
            EventUtils.a("timeOnAuthPage", (System.currentTimeMillis() - this.u) + "");
            if (this.r.isChecked()) {
                EventUtils.a("authPrivacyState", "1");
            } else {
                EventUtils.a("authPrivacyState", "0");
            }
            EventUtils.a(this.d.getApplicationContext(), this.o);
            EventUtils.a();
            this.J = null;
            LoginProxy.a().c();
            this.w.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            LogUtils.a(a, "GenLoginAuthActivity clear failed");
            LogBeanEx.b.add(e);
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private class c implements Runnable {
        private ConcurrentBundle b;
        private boolean c;

        c(ConcurrentBundle concurrentBundle) {
            this.b = concurrentBundle;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException, NumberFormatException {
            if (a(true)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("resultCode", "102507");
                    jSONObject.put("resultString", "请求超时");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GenLoginAuthActivity.this.y = false;
                EventUtils.a("authClickFailed");
                GenLoginAuthActivity.this.w.sendEmptyMessage(1);
                long jB = this.b.b("loginTime", 0L);
                if (jB != 0) {
                    this.b.a("loginTime", System.currentTimeMillis() - jB);
                }
                GenLoginAuthActivity.this.a("102507", "请求超时", this.b, jSONObject);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized boolean a(boolean z) {
            boolean z2;
            z2 = this.c;
            this.c = z;
            return !z2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        try {
            if (this.v >= 5) {
                Toast.makeText(this.d, "网络不稳定,请返回重试其他登录方式", 1).show();
                this.e.setClickable(true);
                return;
            }
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement stackTraceElement : stackTrace) {
                LogUtils.a("stack", stackTraceElement.getClassName());
                String className = stackTraceElement.getClassName();
                if (!TextUtils.isEmpty(className) && className.contains("com.cmic.gen.sdk.activity") && !sb.toString().contains(className)) {
                    sb.append(className).append(";");
                }
            }
            this.o.a("loginTime", System.currentTimeMillis());
            String strB = this.o.b("traceId", "");
            if (!TextUtils.isEmpty(strB) && OverTimeUtils.a(strB)) {
                String strC = UmcUtils.c();
                this.o.a("traceId", strC);
                OverTimeUtils.a(strC, this.x);
            }
            b();
            m();
            c cVar = new c(this.o);
            this.c.postDelayed(cVar, GenAuthnHelper.getInstance((Context) this).getOverTime());
            ThreadUtils.a(new b(this, cVar));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, ConcurrentBundle concurrentBundle, JSONObject jSONObject) {
        try {
            if (this.c == null) {
                this.c = new Handler(getMainLooper());
                this.w = new a(this);
            }
            this.c.removeCallbacksAndMessages(null);
            if ("103000".equals(str)) {
                if (GenAuthnHelper.getInstance((Context) this) != null && OverTimeUtils.c(concurrentBundle.b("traceId")) != null) {
                    concurrentBundle.a("keepListener", true);
                    GenAuthnHelper.getInstance((Context) this).callBackResult(str, str2, concurrentBundle, jSONObject);
                    return;
                }
                return;
            }
            if ("200020".equals(str)) {
                if (GenAuthnHelper.getInstance((Context) this) != null) {
                    if (OverTimeUtils.c(concurrentBundle.b("traceId")) != null) {
                        GenAuthnHelper.getInstance((Context) this).callBackResult(str, str2, concurrentBundle, jSONObject);
                        a();
                        return;
                    } else {
                        a();
                        return;
                    }
                }
                return;
            }
            concurrentBundle.a("keepListener", true);
            GenAuthnHelper.getInstance((Context) this).callBackResult(str, str2, concurrentBundle, jSONObject);
        } catch (Exception e) {
            LogUtils.a(a, "CallbackResult:未知错误");
            e.printStackTrace();
        }
    }

    public void a() {
        this.c.removeCallbacksAndMessages(null);
        if (this.f != null && this.f.isShowing()) {
            this.f.dismiss();
        }
        if (this.g != null && this.g.isShowing()) {
            this.g.dismiss();
        }
        c();
        this.J = null;
        if (this.J != null && this.J.isShowing()) {
            this.J.dismiss();
        }
        if (this.s != null) {
            this.s.clearAnimation();
        }
        LoginProxy.a().a = 0;
        finish();
        if (this.E.getAuthPageActOut() != null && this.E.getActivityIn() != null) {
            overridePendingTransition(ResourceUtil.c(this, this.E.getActivityIn()), ResourceUtil.c(this, this.E.getAuthPageActOut()));
        }
    }

    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case 17476:
                    if (!this.r.isChecked()) {
                        if (this.E.getGenAuthLoginListener() != null) {
                            EventUtils.a("PrivacyNotSelectedCustom");
                            this.E.getGenAuthLoginListener().onAuthLoginListener(this.d, new AuthLoginCallBack() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.4
                                @Override // com.cmic.gen.sdk.view.AuthLoginCallBack
                                public void onAuthLoginCallBack(boolean b2) {
                                    if (b2) {
                                        GenLoginAuthActivity.this.r.setChecked(true);
                                        GenLoginAuthActivity.a(GenLoginAuthActivity.this, 1);
                                        GenLoginAuthActivity.this.n();
                                    }
                                }
                            });
                            break;
                        } else if (this.E.getPrivacyAnimation() != null) {
                            EventUtils.a("PrivacyNotSelectedShake");
                            this.s.startAnimation(AnimationUtils.loadAnimation(this.d, ResourceUtil.c(this.d, this.E.getPrivacyAnimation())));
                            break;
                        } else if (this.E.getGenCheckBoxListener() != null) {
                            EventUtils.a("PrivacyNotSelectedPopup");
                            this.E.getGenCheckBoxListener().onLoginClick(this.d, null);
                            break;
                        } else if (!TextUtils.isEmpty(this.E.getCheckTipText())) {
                            EventUtils.a("PrivacyNotSelectedToast");
                            Toast.makeText(this.d, this.E.getCheckTipText(), 1).show();
                            break;
                        }
                    }
                    this.v++;
                    n();
                    break;
                case 26214:
                    a(false);
                    break;
                case 34952:
                    if (this.r.isChecked()) {
                        this.r.setChecked(false);
                        break;
                    } else {
                        this.r.setChecked(true);
                        break;
                    }
            }
        } catch (Exception e) {
            LogBeanEx.b.add(e);
            e.printStackTrace();
        }
    }

    private static class a extends Handler {
        WeakReference<GenLoginAuthActivity> a;

        a(GenLoginAuthActivity genLoginAuthActivity) {
            this.a = new WeakReference<>(genLoginAuthActivity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            try {
                a(msg);
            } catch (Exception e) {
                LogBeanEx.b.add(e);
                e.printStackTrace();
            }
        }

        private void a(Message message) {
            GenLoginAuthActivity genLoginAuthActivity = this.a.get();
            if (genLoginAuthActivity != null && message.what == 1) {
                genLoginAuthActivity.c();
                genLoginAuthActivity.l();
            }
        }
    }

    public void b() {
        LogUtils.a(a, "loginClickStart");
        try {
            this.H = true;
            if (this.E.getGenLoginClickListener() != null) {
                this.E.getGenLoginClickListener().onLoginClickStart(this.d, null);
            } else {
                if (this.J != null) {
                    this.J.show();
                    return;
                }
                this.J = new AlertDialog.Builder(this).create();
                this.J.setCancelable(false);
                this.J.setCanceledOnTouchOutside(false);
                this.J.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.5
                    @Override // android.content.DialogInterface.OnKeyListener
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return keyCode == 4;
                    }
                });
                RelativeLayout relativeLayout = new RelativeLayout(this.J.getContext());
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
                ImageView imageView = new ImageView(this.J.getContext());
                imageView.setImageResource(ResourceUtil.b(this.d, "umcsdk_dialog_loading"));
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(80, 80);
                layoutParams.addRule(13, -1);
                relativeLayout.addView(imageView, layoutParams);
                if (this.J.getWindow() != null) {
                    this.J.getWindow().setDimAmount(0.0f);
                }
                this.J.setContentView(relativeLayout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.a(a, "loginClickStart");
    }

    public void c() {
        try {
            LogUtils.a(a, "loginClickComplete");
            if (this.E.getGenLoginClickListener() != null && this.H) {
                this.H = false;
                this.E.getGenLoginClickListener().onLoginClickComplete(this.d, null);
            } else if (this.J != null && this.J.isShowing()) {
                this.J.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean o() {
        if (!TextUtils.isEmpty(this.E.getCheckTipText()) || this.E.getGenCheckBoxListener() != null || this.E.getPrivacyAnimation() != null || this.E.getGenAuthLoginListener() != null) {
            return true;
        }
        return false;
    }

    private static class b extends ThreadUtils.a {
        WeakReference<GenLoginAuthActivity> a;
        WeakReference<c> b;

        protected b(GenLoginAuthActivity genLoginAuthActivity, c cVar) {
            this.a = new WeakReference<>(genLoginAuthActivity);
            this.b = new WeakReference<>(cVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean b() {
            c cVar = this.b.get();
            if (this.a.get() == null || cVar == null) {
                return false;
            }
            return cVar.a(false);
        }

        @Override // com.cmic.gen.sdk.e.ThreadUtils.a
        protected void a() throws JSONException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
            final GenLoginAuthActivity genLoginAuthActivity = this.a.get();
            genLoginAuthActivity.o.a("logintype", 1);
            PhoneScripUtils.a(true, false);
            genLoginAuthActivity.p.b(genLoginAuthActivity.o, new AuthnCallback() { // from class: com.cmic.gen.sdk.view.GenLoginAuthActivity.b.1
                @Override // com.cmic.gen.sdk.auth.AuthnCallback
                public void a(String str, String str2, ConcurrentBundle concurrentBundle, JSONObject jSONObject) throws InterruptedException, NumberFormatException {
                    if (!b.this.b()) {
                        return;
                    }
                    long jB = concurrentBundle.b("loginTime", 0L);
                    String strB = concurrentBundle.b("phonescrip");
                    if (jB != 0) {
                        concurrentBundle.a("loginTime", System.currentTimeMillis() - jB);
                    }
                    if (!"103000".equals(str) || TextUtils.isEmpty(strB)) {
                        genLoginAuthActivity.y = false;
                        EventUtils.a("authClickFailed");
                    } else {
                        EventUtils.a("authClickSuccess");
                        genLoginAuthActivity.y = true;
                    }
                    genLoginAuthActivity.a(str, str2, concurrentBundle, jSONObject);
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    genLoginAuthActivity.w.sendEmptyMessage(1);
                }
            });
        }
    }
}