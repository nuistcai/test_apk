package com.cmic.gen.sdk.view;

import android.R;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.asm.Opcodes;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class GenAuthThemeConfig {
    public static final String PLACEHOLDER = "$$运营商条款$$";
    private int A;
    private int B;
    private int C;
    private int D;
    private String E;
    private boolean F;
    private int G;
    private boolean H;
    private int I;
    private String J;
    private int K;
    private int L;
    private int M;
    private int N;
    private int O;
    private int P;
    private String Q;
    private boolean R;
    private GenBackPressedListener S;
    private GenLoginClickListener T;
    private GenCheckBoxListener U;
    private GenCheckedChangeListener V;
    private GenAuthLoginListener W;
    private ClauseClickListener X;
    private String Y;
    private String Z;
    private final boolean a;
    private String aA;
    private String aB;
    private int aC;
    private int aD;
    private int aE;
    private int aF;
    private int aG;
    private int aH;
    private int aI;
    private int aJ;
    private boolean aK;
    private boolean aL;
    private String aM;
    private int aa;
    private int ab;
    private boolean ac;
    private String ad;
    private boolean ae;
    private String af;
    private String ag;
    private String ah;
    private String ai;
    private String aj;
    private String ak;
    private String al;
    private String am;
    private int an;
    private boolean ao;
    private int ap;
    private int aq;
    private boolean ar;
    private int as;
    private int at;
    private int au;
    private int av;
    private int aw;
    private boolean ax;
    private String ay;
    private String az;
    private int b;
    private boolean c;
    private View d;
    private int e;
    private int f;
    private String g;
    private boolean h;
    private int i;
    private int j;
    private int k;
    private boolean l;
    private int m;
    private String n;
    private int o;
    private int p;
    private ImageView.ScaleType q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private ImageView.ScaleType w;
    private boolean x;
    private int y;
    private boolean z;

    public int getStatusBarColor() {
        return this.b;
    }

    public boolean isLightColor() {
        return this.c;
    }

    public View getContentView() {
        return this.d;
    }

    public int getLayoutResID() {
        return this.e;
    }

    public int getClauseLayoutResID() {
        return this.f;
    }

    public String getClauseLayoutReturnID() {
        return this.g;
    }

    public boolean isGetWebViewTitle() {
        return this.h;
    }

    public int getNavTextSize() {
        return this.i;
    }

    public int getNavTextColor() {
        return this.j;
    }

    public int getNavColor() {
        return this.k;
    }

    public boolean isNavHidden() {
        return this.l;
    }

    public int getClauseStatusColor() {
        return this.m;
    }

    public String getNavReturnImgPath() {
        return this.n;
    }

    public int getNavReturnImgWidth() {
        return this.o;
    }

    public int getNavReturnImgHeight() {
        return this.p;
    }

    public ImageView.ScaleType getNavReturnImgScaleType() {
        return this.q;
    }

    public int getLogoWidth() {
        return this.r;
    }

    public int getLogoHeight() {
        return this.s;
    }

    public int getLogoOffsetX() {
        return this.t;
    }

    public ImageView.ScaleType getLogoScaleType() {
        return this.w;
    }

    public int getLogoOffsetY() {
        return this.u;
    }

    public int getLogoOffsetY_B() {
        return this.v;
    }

    public boolean isDisplayLogo() {
        return this.x;
    }

    public int getNumberSize() {
        return this.y;
    }

    public boolean isNumberBold() {
        return this.z;
    }

    public int getNumberColor() {
        return this.A;
    }

    public int getNumberOffsetX() {
        return this.B;
    }

    public int getNumFieldOffsetY() {
        return this.C;
    }

    public int getNumFieldOffsetY_B() {
        return this.D;
    }

    public String getLogBtnText() {
        if (this.F) {
            this.E = this.aJ == 1 ? "本機號碼登錄" : this.aJ == 2 ? "Login" : this.E;
        }
        return this.E;
    }

    public int getLogBtnTextSize() {
        return this.G;
    }

    public boolean isLogBtnTextBold() {
        return this.H;
    }

    public int getLogBtnTextColor() {
        return this.I;
    }

    public String getLogBtnBackgroundPath() {
        return this.J;
    }

    public int getLogBtnWidth() {
        return this.K;
    }

    public int getLogBtnHeight() {
        return this.L;
    }

    public int getLogBtnMarginLeft() {
        return this.M;
    }

    public int getLogBtnMarginRight() {
        return this.N;
    }

    public int getLogBtnOffsetY() {
        return this.O;
    }

    public int getLogBtnOffsetY_B() {
        return this.P;
    }

    public String getCheckTipText() {
        if (this.R) {
            this.Q = this.aJ == 1 ? "請勾選同意服務條款" : this.aJ == 2 ? "Please check to agree to the terms of service" : "请勾选同意服务条款";
        }
        return this.Q;
    }

    public GenBackPressedListener getGenBackPressedListener() {
        return this.S;
    }

    public GenLoginClickListener getGenLoginClickListener() {
        return this.T;
    }

    public GenCheckBoxListener getGenCheckBoxListener() {
        return this.U;
    }

    public GenCheckedChangeListener getGenCheckedChangeListener() {
        return this.V;
    }

    public ClauseClickListener getClauseClickListener() {
        return this.X;
    }

    public GenAuthLoginListener getGenAuthLoginListener() {
        return this.W;
    }

    public String getCheckedImgPath() {
        return this.Y;
    }

    public String getUncheckedImgPath() {
        return this.Z;
    }

    public int getCheckedImgWidth() {
        return this.aa;
    }

    public int getCheckedImgHeight() {
        return this.ab;
    }

    public boolean isPrivacyState() {
        return this.ac;
    }

    public String getPrivacy() {
        if (this.ae) {
            return String.format(this.aJ == 1 ? "登錄即同意%s並使用本機號碼登錄" : this.aJ == 2 ? "By logging in, you agree to the %s and use your local phone number to log in" : "登录即同意%s并使用本机号码登录", PLACEHOLDER);
        }
        return this.ad;
    }

    public String getClauseName() {
        return this.af;
    }

    public String getClauseUrl() {
        return this.ag;
    }

    public String getClauseName2() {
        return this.ah;
    }

    public String getClauseUrl2() {
        return this.ai;
    }

    public String getClauseName3() {
        return this.aj;
    }

    public String getClauseUrl3() {
        return this.ak;
    }

    public String getClauseName4() {
        return this.al;
    }

    public String getClauseUrl4() {
        return this.am;
    }

    public int getPrivacyTextSize() {
        return this.an;
    }

    public boolean isPrivacyTextBold() {
        return this.ao;
    }

    public int getClauseBaseColor() {
        return this.ap;
    }

    public int getClauseColor() {
        return this.aq;
    }

    public boolean isPrivacyTextGravityCenter() {
        return this.ar;
    }

    public int getPrivacyMarginLeft() {
        return this.at;
    }

    public int getPrivacyMarginRight() {
        return this.au;
    }

    public int getPrivacyOffsetY() {
        return this.av;
    }

    public int getPrivacyOffsetY_B() {
        return this.aw;
    }

    public boolean isPrivacyBookSymbol() {
        return this.ax;
    }

    public int getCheckBoxLocation() {
        return this.as;
    }

    public String getAuthPageActIn() {
        return this.ay;
    }

    public String getActivityOut() {
        return this.az;
    }

    public String getAuthPageActOut() {
        return this.aA;
    }

    public String getActivityIn() {
        return this.aB;
    }

    public int getWindowWidth() {
        return this.aC;
    }

    public int getWindowHeight() {
        return this.aD;
    }

    public int getWindowX() {
        return this.aE;
    }

    public int getWindowY() {
        return this.aF;
    }

    public int getWindowBottom() {
        return this.aG;
    }

    public int getThemeId() {
        return this.aH;
    }

    public int getPrivacyDialogThemeId() {
        return this.aI;
    }

    public int getAppLanguageType() {
        return this.aJ;
    }

    public boolean isFitsSystemWindows() {
        return this.aK;
    }

    public boolean isBackButton() {
        return this.aL;
    }

    public String getPrivacyAnimation() {
        return this.aM;
    }

    public boolean getWebStorage() {
        return this.a;
    }

    private GenAuthThemeConfig(Builder builder) {
        this.x = false;
        this.F = true;
        this.aj = null;
        this.ak = null;
        this.al = null;
        this.am = null;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
        this.k = builder.k;
        this.l = builder.l;
        this.m = builder.m;
        this.n = builder.n;
        this.o = builder.o;
        this.p = builder.p;
        this.q = builder.q;
        this.t = builder.t;
        this.r = builder.r;
        this.s = builder.s;
        this.u = builder.u;
        this.v = builder.v;
        this.w = builder.w;
        this.x = builder.x;
        this.y = builder.y;
        this.z = builder.z;
        this.A = builder.A;
        this.B = builder.B;
        this.C = builder.C;
        this.D = builder.D;
        this.E = builder.E;
        this.F = builder.F;
        this.G = builder.G;
        this.H = builder.H;
        this.I = builder.I;
        this.J = builder.J;
        this.K = builder.K;
        this.L = builder.L;
        this.M = builder.M;
        this.N = builder.N;
        this.O = builder.O;
        this.P = builder.P;
        this.Q = builder.Q;
        this.R = builder.R;
        this.S = builder.S;
        this.T = builder.T;
        this.U = builder.U;
        this.V = builder.V;
        this.X = builder.W;
        this.Y = builder.Y;
        this.Z = builder.Z;
        this.aa = builder.aa;
        this.ab = builder.ab;
        this.ac = builder.ac;
        this.ad = builder.ae;
        this.ae = builder.ad;
        this.af = builder.af;
        this.ag = builder.ag;
        this.ah = builder.ah;
        this.ai = builder.ai;
        this.aj = builder.aj;
        this.ak = builder.ak;
        this.al = builder.al;
        this.am = builder.am;
        this.an = builder.an;
        this.ao = builder.ao;
        this.ap = builder.ap;
        this.aq = builder.aq;
        this.ar = builder.ar;
        this.at = builder.as;
        this.au = builder.at;
        this.av = builder.au;
        this.aw = builder.av;
        this.ax = builder.aw;
        this.as = builder.ax;
        this.ay = builder.ay;
        this.az = builder.az;
        this.aA = builder.aA;
        this.aB = builder.aB;
        this.aC = builder.aC;
        this.aD = builder.aD;
        this.aE = builder.aE;
        this.aF = builder.aF;
        this.aG = builder.aG;
        this.aH = builder.aH;
        this.aI = builder.aI;
        this.aJ = builder.aJ;
        this.aK = builder.aK;
        this.aL = builder.aL;
        this.aM = builder.aM;
        this.a = builder.a;
        this.W = builder.X;
    }

    public static class Builder {
        private String Q;
        private boolean R;
        private GenBackPressedListener S;
        private GenLoginClickListener T;
        private GenCheckBoxListener U;
        private GenCheckedChangeListener V;
        private ClauseClickListener W;
        private GenAuthLoginListener X;
        public boolean a;
        private String aA;
        private String aB;
        private int aC;
        private int aD;
        private int aE;
        private int aF;
        private String aM;
        private int ax;
        private String ay;
        private String az;
        private String g;
        private boolean h;
        private boolean l;
        private ImageView.ScaleType w;
        private int b = 0;
        private boolean c = false;
        private View d = null;
        private int e = -1;
        private int f = -1;
        private int i = 17;
        private int j = -1;
        private int k = -16742704;
        private int m = -16742704;
        private String n = "return_bg";
        private int o = -2;
        private int p = -2;
        private ImageView.ScaleType q = ImageView.ScaleType.CENTER;
        private int r = 80;
        private int s = 20;
        private int t = -1;
        private int u = -1;
        private int v = -1;
        private boolean x = true;
        private int y = 18;
        private boolean z = false;
        private int A = -16742704;
        private int B = 0;
        private int C = Opcodes.INVOKESTATIC;
        private int D = 0;
        private String E = "本机号码一键登录";
        private boolean F = true;
        private int G = 15;
        private boolean H = false;
        private int I = -1;
        private String J = "umcsdk_login_btn_bg";
        private int K = -1;
        private int L = 36;
        private int M = 46;
        private int N = 46;
        private int O = 254;
        private int P = 0;
        private String Y = "umcsdk_check_image";
        private String Z = "umcsdk_uncheck_image";
        private int aa = 9;
        private int ab = 9;
        private boolean ac = false;
        private boolean ad = true;
        private String ae = "登录即同意$$运营商条款$$并使用本机号码登录";
        private String af = null;
        private String ag = null;
        private String ah = null;
        private String ai = null;
        private String aj = null;
        private String ak = null;
        private String al = null;
        private String am = null;
        private int an = 10;
        private boolean ao = false;
        private int ap = -10066330;
        private int aq = -16007674;
        private boolean ar = false;
        private int as = 52;
        private int at = 52;
        private int au = 0;
        private int av = 30;
        private boolean aw = true;
        private int aG = 0;
        private int aH = -1;
        private int aI = R.style.Theme.Translucent.NoTitleBar;
        private int aJ = 0;
        private boolean aK = true;
        private boolean aL = true;

        public Builder setStatusBar(int statusBarColor, boolean isLightColor) {
            this.b = statusBarColor;
            this.c = isLightColor;
            return this;
        }

        public Builder setAuthContentView(View contentView) {
            this.d = contentView;
            this.e = -1;
            return this;
        }

        public Builder setAuthLayoutResID(int layoutResID) {
            this.e = layoutResID;
            this.d = null;
            return this;
        }

        public Builder setClauseLayoutResID(int layoutResID, String clauseLayoutReturnID) {
            this.f = layoutResID;
            this.g = clauseLayoutReturnID;
            return this;
        }

        public Builder setNavTextGetWebViewTittle(boolean isGetWebViewTittle) {
            this.h = isGetWebViewTittle;
            return this;
        }

        public Builder setNavTextSize(int navTextSize) {
            this.i = navTextSize;
            return this;
        }

        public Builder setNavTextColor(int navTextColor) {
            this.j = navTextColor;
            return this;
        }

        public Builder setNavColor(int navColor) {
            this.k = navColor;
            return this;
        }

        public Builder setNavHidden(boolean isNavHidden) {
            this.l = isNavHidden;
            return this;
        }

        public Builder setClauseStatusColor(int clauseStatusColor) {
            this.m = clauseStatusColor;
            return this;
        }

        public Builder setLogo(int width, int height) {
            this.r = width;
            this.s = height;
            return this;
        }

        public Builder displayLogo(boolean isDisplay) {
            this.x = isDisplay;
            return this;
        }

        public Builder setLogoOffsetX(int offsetX) {
            this.t = offsetX;
            return this;
        }

        public Builder setLogoOffsetY(int offsetY) {
            this.u = offsetY;
            this.v = -1;
            return this;
        }

        public Builder setLogoScaleType(ImageView.ScaleType scaleType) {
            this.w = scaleType;
            return this;
        }

        public Builder setLogoOffsetY_B(int offsetY_B) {
            this.v = offsetY_B;
            this.u = -1;
            return this;
        }

        public Builder setNumberSize(int numberSize, boolean isBold) {
            if (numberSize > 8) {
                this.y = numberSize;
                this.z = isBold;
            }
            return this;
        }

        public Builder setNumberColor(int numberColor) {
            this.A = numberColor;
            return this;
        }

        public Builder setNumberOffsetX(int numberOffsetX) {
            this.B = numberOffsetX;
            return this;
        }

        public Builder setNumFieldOffsetY(int numFieldOffsetY) {
            this.C = numFieldOffsetY;
            this.D = 0;
            return this;
        }

        public Builder setNumFieldOffsetY_B(int numFieldOffsetY_B) {
            this.D = numFieldOffsetY_B;
            this.C = 0;
            return this;
        }

        public Builder setLogBtnText(String logBtnText) {
            if (!TextUtils.isEmpty(logBtnText) && !Pattern.compile("^\\s*\\n*$").matcher(logBtnText).matches()) {
                this.E = logBtnText;
                this.F = false;
            }
            return this;
        }

        public Builder setLogBtnText(String logBtnText, int logBtnTextColor, int logBtnTextSize, boolean isBold) {
            if (!TextUtils.isEmpty(logBtnText) && !Pattern.compile("^\\s*\\n*$").matcher(logBtnText).matches()) {
                this.E = logBtnText;
                this.F = false;
            }
            this.I = logBtnTextColor;
            this.G = logBtnTextSize;
            this.H = isBold;
            return this;
        }

        public Builder setLogBtnTextColor(int logBtnTextColor) {
            this.I = logBtnTextColor;
            return this;
        }

        public Builder setLogBtnImgPath(String logBtnBackgroundPath) {
            this.J = logBtnBackgroundPath;
            return this;
        }

        public Builder setLogBtn(int width, int height) {
            this.K = width;
            this.L = height;
            return this;
        }

        public Builder setLogBtnMargin(int marginLeft, int marginRight) {
            this.M = marginLeft;
            this.N = marginRight;
            return this;
        }

        public Builder setLogBtnOffsetY(int logBtnOffsetY) {
            this.O = logBtnOffsetY;
            this.P = 0;
            return this;
        }

        public Builder setGenBackPressedListener(GenBackPressedListener onClickListener) {
            this.S = onClickListener;
            return this;
        }

        public Builder setLogBtnClickListener(GenLoginClickListener genLoginClickListener) {
            this.T = genLoginClickListener;
            return this;
        }

        public Builder setGenCheckBoxListener(GenCheckBoxListener genCheckBoxListener) {
            this.U = genCheckBoxListener;
            return this;
        }

        public Builder setGenCheckedChangeListener(GenCheckedChangeListener genCheckedChangeListener) {
            this.V = genCheckedChangeListener;
            return this;
        }

        public Builder setClauseClickListener(ClauseClickListener clauseClickListener) {
            this.W = clauseClickListener;
            return this;
        }

        public Builder setGenAuthLoginListener(GenAuthLoginListener genAuthLoginListener) {
            this.X = genAuthLoginListener;
            return this;
        }

        public Builder setLogBtnOffsetY_B(int logBtnOffsetY_B) {
            this.P = logBtnOffsetY_B;
            this.O = 0;
            return this;
        }

        public Builder setCheckTipText(String checkTipText) {
            this.R = TextUtils.isEmpty(checkTipText) || checkTipText.length() > 100;
            this.Q = this.R ? "请勾选同意服务条款" : checkTipText;
            return this;
        }

        public Builder setCheckedImgPath(String checkedImgPath) {
            this.Y = checkedImgPath;
            return this;
        }

        public Builder setUncheckedImgPath(String uncheckedImgPath) {
            this.Z = uncheckedImgPath;
            return this;
        }

        public Builder setCheckBoxImgPath(String checkedImgPath, String uncheckedImgPath, int width, int height) {
            this.Y = checkedImgPath;
            this.Z = uncheckedImgPath;
            this.aa = width;
            this.ab = height;
            return this;
        }

        public Builder setPrivacyState(boolean privacyState) {
            this.ac = privacyState;
            return this;
        }

        public Builder setPrivacyAlignment(String privacy, String clause1, String clauseUrl1, String clause2, String clauseUrl2, String clause3, String clauseUrl3, String clause4, String clauseUrl4) {
            if (privacy.contains(GenAuthThemeConfig.PLACEHOLDER)) {
                this.ad = false;
                this.ae = privacy;
                this.af = clause1;
                this.ag = clauseUrl1;
                this.ah = clause2;
                this.ai = clauseUrl2;
                this.aj = clause3;
                this.ak = clauseUrl3;
                this.al = clause4;
                this.am = clauseUrl4;
            }
            return this;
        }

        public Builder setPrivacyText(int privacyTextSize, int clauseBaseColor, int clauseColor, boolean isGravityCenter, boolean isBold) {
            this.an = privacyTextSize;
            this.ap = clauseBaseColor;
            this.aq = clauseColor;
            this.ar = isGravityCenter;
            this.ao = isBold;
            return this;
        }

        public Builder setClauseColor(int clauseBaseColor, int clauseColor) {
            this.ap = clauseBaseColor;
            this.aq = clauseColor;
            return this;
        }

        public Builder setPrivacyMargin(int privacyMarginLeft, int privacyMarginRight) {
            this.as = privacyMarginLeft;
            this.at = privacyMarginRight;
            return this;
        }

        public Builder setPrivacyOffsetY(int privacyOffsetY) {
            this.au = privacyOffsetY;
            this.av = 0;
            return this;
        }

        public Builder setPrivacyOffsetY_B(int privacyOffsetY_B) {
            this.av = privacyOffsetY_B;
            this.au = 0;
            return this;
        }

        public Builder setPrivacyBookSymbol(boolean haveBookSymbol) {
            this.aw = haveBookSymbol;
            return this;
        }

        public Builder setCheckBoxLocation(int checkBoxLocation) {
            this.ax = checkBoxLocation;
            return this;
        }

        public Builder setAuthPageActIn(String authPageActIn, String activityOut) {
            this.ay = authPageActIn;
            this.az = activityOut;
            return this;
        }

        public Builder setAuthPageActOut(String activityIn, String authPageActOut) {
            this.aA = authPageActOut;
            this.aB = activityIn;
            return this;
        }

        public Builder setAuthPageWindowMode(int windowWidth, int windowHeight) {
            this.aC = windowWidth;
            this.aD = windowHeight;
            return this;
        }

        public Builder setAuthPageWindowOffset(int windowX, int windowY) {
            this.aE = windowX;
            this.aF = windowY;
            return this;
        }

        public Builder setWindowBottom(int windowBottom) {
            this.aG = windowBottom;
            return this;
        }

        public Builder setThemeId(int themeId) {
            this.aH = themeId;
            return this;
        }

        public Builder setClauseTheme(int themeId) {
            this.aI = themeId;
            return this;
        }

        public Builder setAppLanguageType(int appLanguageType) {
            this.aJ = appLanguageType;
            return this;
        }

        public Builder setFitsSystemWindows(boolean isFitsSystemWindows) {
            this.aK = isFitsSystemWindows;
            return this;
        }

        public Builder setBackButton(boolean isBackButton) {
            this.aL = isBackButton;
            return this;
        }

        public Builder setPrivacyAnimation(String animation) {
            this.aM = animation;
            return this;
        }

        public GenAuthThemeConfig build() {
            return new GenAuthThemeConfig(this);
        }

        public Builder setWebDomStorage(boolean b) {
            this.a = b;
            return this;
        }
    }
}