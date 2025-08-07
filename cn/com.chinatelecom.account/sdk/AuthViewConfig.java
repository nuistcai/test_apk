package cn.com.chinatelecom.account.sdk;

import android.text.SpannableStringBuilder;
import cn.com.chinatelecom.account.sdk.inter.OnViewClickListener;
import java.util.List;

/* loaded from: classes.dex */
public class AuthViewConfig {
    public static final int STATE_DEFAULT_CHECKED = 0;
    public static final int STATE_DEFAULT_UNCHECKED = -1;
    public int A;
    public int B;
    public int C;
    public String D;
    public int E;
    public int F;
    public int G;
    public int H;
    public int I;
    public int J;
    public String K;
    public int L;
    public int M;
    public boolean N;
    public int O;
    public int P;
    public int Q;
    public int R;
    public int S;
    public int T;
    public String U;
    public int V;
    public int W;
    public int X;
    public int Y;
    public int Z;
    public int a;
    public int aA;
    public OnViewClickListener aB;
    public List<Integer> aC;
    public OnViewClickListener aD;
    public SpannableStringBuilder aE;
    public int aa;
    public int ab;
    public int ac;
    public String ad;
    public String ae;
    public PrivacyAgreementConfig af;
    public int ag;
    public int ah;
    public int ai;
    public int aj;
    public int ak;
    public String al;
    public int am;
    public int an;
    public int ao;
    public int ap;
    public int aq;
    public int ar;
    public int as;
    public int at;
    public String au;
    public String av;
    public int aw;
    public int ax;
    public int ay;
    public int az;
    public boolean b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public String h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public boolean o;
    public int p;
    public int q;
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;
    public int w;
    public int x;
    public int y;
    public int z;

    public static class Builder {
        private int A;
        private String B;
        private int C;
        private int D;
        private int E;
        private int F;
        private int G;
        private int H;
        private String I;
        private int J;
        private int K;
        private boolean L;
        private int M;
        private int N;
        private int O;
        private int P;
        private int Q;
        private SpannableStringBuilder R;
        private PrivacyAgreementConfig S;
        private int T;
        private String U;
        private int V;
        private int W;
        private int X;
        private int Y;
        private int Z;
        private int a;
        private OnViewClickListener aA;
        private List<Integer> aB;
        private OnViewClickListener aC;
        private int aa;
        private int ab;
        private int ac;
        private String ad;
        private String ae;
        private int af;
        private int ag;
        private int ah;
        private int ai;
        private int aj;
        private String ak;
        private int al;
        private int am;
        private int an;
        private int ao;
        private int ap;
        private int aq;
        private int ar;
        private int as;
        private String at;
        private String au;
        private int av;
        private int aw;
        private int ax;
        private int ay;
        private int az;
        private int b;
        private int c;
        private int d;
        private int e;
        private String f;
        private int g;
        private int h;
        private int i;
        private int j;
        private int k;
        private int l;
        private boolean m;
        private int n;
        private int o;
        private int p;
        private int q;
        private int r;
        private int s;
        public int staBgColor;
        public boolean staTextDark;
        private int t;
        private int u;
        private int v;
        private int w;
        private int x;
        private int y;
        private int z;

        public AuthViewConfig build() {
            return new AuthViewConfig(this);
        }

        public Builder setBrandView(int i, int i2) {
            this.s = i;
            this.t = i2;
            return this;
        }

        @Deprecated
        public Builder setCtAccountPrivacyProtocolLink(int i, int i2, int i3) {
            this.X = i;
            this.Y = i2;
            this.Z = i3;
            return this;
        }

        @Deprecated
        public Builder setCustomPrivacyProtocolLink(int i, int i2, int i3, String str, String str2) {
            this.aa = i;
            this.ab = i2;
            this.ac = i3;
            this.ad = str;
            this.ae = str2;
            return this;
        }

        public Builder setDesinNumberView(int i, int i2, int i3, int i4) {
            this.o = i;
            this.p = i2;
            this.q = i3;
            this.r = i4;
            return this;
        }

        @Deprecated
        public Builder setDialogCtAccountPrivacyProtocolLink(int i, int i2, int i3) {
            this.an = i;
            this.ao = i2;
            this.ap = i3;
            return this;
        }

        @Deprecated
        public Builder setDialogCustomPrivacyProtocolLink(int i, int i2, int i3, String str, String str2) {
            this.aq = i;
            this.ar = i2;
            this.as = i3;
            this.at = str;
            this.au = str2;
            return this;
        }

        @Deprecated
        public Builder setDialogPrivacyText(int i, String str, int i2, int i3) {
            this.aj = i;
            this.ak = str;
            this.al = i2;
            this.am = i3;
            return this;
        }

        public Builder setDialogView(int i, int i2, int i3, int i4) {
            this.af = i;
            this.ag = i2;
            this.ah = i3;
            this.ai = i4;
            return this;
        }

        public Builder setLoginBtnView(int i, String str, int i2, int i3) {
            this.A = i;
            this.B = str;
            this.C = i2;
            this.D = i3;
            return this;
        }

        public Builder setLoginLoadingView(int i, int i2) {
            this.E = i;
            this.F = i2;
            return this;
        }

        public Builder setLoginParentView(int i, int i2, int i3, int i4, int i5, int i6) {
            this.u = i;
            this.v = i2;
            this.w = i3;
            this.x = i4;
            this.y = i5;
            this.z = i6;
            return this;
        }

        public Builder setLogoView(int i, int i2, int i3, int i4, boolean z, int i5) {
            this.i = i;
            this.j = i2;
            this.k = i3;
            this.l = i4;
            this.m = z;
            this.n = i5;
            return this;
        }

        public Builder setNavGoBackView(int i, int i2) {
            this.c = i;
            this.d = i2;
            return this;
        }

        public Builder setNavParentView(int i, int i2) {
            this.a = i;
            this.b = i2;
            return this;
        }

        public Builder setNavTitleView(int i, String str, int i2, int i3) {
            this.e = i;
            this.f = str;
            this.g = i2;
            this.h = i3;
            return this;
        }

        public Builder setOtherLoginView(int i, int i2, String str, int i3, int i4, boolean z) {
            this.G = i;
            this.H = i2;
            this.I = str;
            this.J = i3;
            this.K = i4;
            this.L = z;
            return this;
        }

        public Builder setPrivacyCheckBox(int i, int i2, int i3) {
            this.O = i;
            this.P = i2;
            this.Q = i3;
            return this;
        }

        public Builder setPrivacyGoBackResId(int i, int i2) {
            this.ax = i;
            this.ay = i2;
            return this;
        }

        public Builder setPrivacyParentView(int i, int i2) {
            this.M = i;
            this.N = i2;
            return this;
        }

        public Builder setPrivacyTextView(int i, SpannableStringBuilder spannableStringBuilder) {
            this.T = i;
            this.R = spannableStringBuilder;
            return this;
        }

        @Deprecated
        public Builder setPrivacyTextView(int i, String str, int i2, int i3) {
            this.T = i;
            this.U = str;
            this.V = i2;
            this.W = i3;
            return this;
        }

        @Deprecated
        public Builder setPrivacyTextViewConfig(int i, int i2, PrivacyAgreementConfig privacyAgreementConfig) {
            this.T = i;
            this.aj = i2;
            this.S = privacyAgreementConfig;
            return this;
        }

        public Builder setPrivacyWebviewActivity(int i, int i2) {
            this.av = i;
            this.aw = i2;
            return this;
        }

        public Builder setStatusBarView(int i, boolean z) {
            this.staBgColor = i;
            this.staTextDark = z;
            return this;
        }

        public Builder setViewClickListener(int i, OnViewClickListener onViewClickListener) {
            this.az = i;
            this.aA = onViewClickListener;
            return this;
        }

        public Builder setViewClickListener(List<Integer> list, OnViewClickListener onViewClickListener) {
            this.aB = list;
            this.aC = onViewClickListener;
            return this;
        }
    }

    public AuthViewConfig(Builder builder) {
        this.a = builder.staBgColor;
        this.b = builder.staTextDark;
        this.c = builder.a;
        this.d = builder.b;
        this.e = builder.c;
        this.f = builder.d;
        this.g = builder.e;
        this.h = builder.f;
        this.i = builder.g;
        this.j = builder.h;
        this.k = builder.i;
        this.l = builder.j;
        this.m = builder.k;
        this.n = builder.l;
        this.o = builder.m;
        this.p = builder.n;
        this.q = builder.o;
        this.r = builder.p;
        this.s = builder.q;
        this.t = builder.r;
        this.u = builder.s;
        this.v = builder.t;
        this.w = builder.u;
        this.x = builder.v;
        this.y = builder.w;
        this.z = builder.x;
        this.A = builder.y;
        this.B = builder.z;
        this.C = builder.A;
        this.D = builder.B;
        this.E = builder.C;
        this.F = builder.D;
        this.G = builder.E;
        this.H = builder.F;
        this.I = builder.G;
        this.J = builder.H;
        this.K = builder.I;
        this.L = builder.J;
        this.M = builder.K;
        this.N = builder.L;
        this.O = builder.M;
        this.P = builder.N;
        this.Q = builder.O;
        this.R = builder.P;
        this.S = builder.Q;
        this.T = builder.T;
        this.U = builder.U;
        this.V = builder.V;
        this.W = builder.W;
        this.X = builder.X;
        this.Y = builder.Y;
        this.Z = builder.Z;
        this.aa = builder.aa;
        this.ab = builder.ab;
        this.ac = builder.ac;
        this.ad = builder.ad;
        this.ae = builder.ae;
        this.ag = builder.af;
        this.ah = builder.ag;
        this.ai = builder.ah;
        this.aj = builder.ai;
        this.ak = builder.aj;
        this.al = builder.ak;
        this.am = builder.al;
        this.an = builder.am;
        this.ao = builder.an;
        this.ap = builder.ao;
        this.aq = builder.ap;
        this.ar = builder.aq;
        this.as = builder.ar;
        this.at = builder.as;
        this.au = builder.at;
        this.av = builder.au;
        this.aw = builder.av;
        this.ax = builder.aw;
        this.ay = builder.ax;
        this.az = builder.ay;
        this.af = builder.S;
        this.aA = builder.az;
        this.aB = builder.aA;
        this.aC = builder.aB;
        this.aD = builder.aC;
        this.aE = builder.R;
    }
}