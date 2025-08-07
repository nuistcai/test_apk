package cn.com.chinatelecom.account.sdk;

import android.view.View;

/* loaded from: classes.dex */
public class AuthPageConfig {
    public static final int BOTTOM = 80;
    public static final int CENTER = 17;
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private int J;
    private int K;
    private int L;
    private int M;
    private View.OnClickListener N;
    private View.OnClickListener O;
    private View.OnClickListener P;
    private View.OnClickListener Q;
    private View.OnClickListener R;
    private boolean S;
    private boolean T;
    private int U;
    private int V;
    private int W;
    private int X;
    private int Y;
    private int Z;
    private int a;
    private int aa;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private String t;
    private String u;
    private int v;
    private int w;
    private String x;
    private String y;
    private String z;

    public static class Builder {
        private int A;
        private int B;
        private int C;
        private int D;
        private int E;
        private int F;
        private int G;
        private int H;
        private int I;
        private int J;
        private int K;
        private int L;
        private int M;
        private View.OnClickListener N;
        private View.OnClickListener O;
        private View.OnClickListener P;
        private View.OnClickListener Q;
        private View.OnClickListener R;
        private boolean S;
        private boolean T;
        private int U;
        private int V;
        private int W;
        private int X;
        private int Y;
        private int Z;
        private int a;
        private int aa;
        private int b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;
        private int j;
        private int k;
        private int l;
        private int m;
        private int n;
        private int o;
        private int p;
        private int q;
        private int r;
        private int s;
        private String t;
        private String u;
        private int v;
        private int w;
        private String x;
        private String y;
        private String z;

        public AuthPageConfig build() {
            return new AuthPageConfig(this);
        }

        public Builder setAuthActivityLayoutId(int i) {
            this.a = i;
            return this;
        }

        public Builder setAuthActivityViewIds(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f = i5;
            this.g = i6;
            this.h = i7;
            this.i = i8;
            this.j = i9;
            return this;
        }

        public Builder setBiomAuthActivityLayoutId(int i) {
            this.k = i;
            return this;
        }

        public Builder setBiomAuthActivityViewIds(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.l = i;
            this.m = i2;
            this.n = i3;
            this.o = i5;
            this.p = i6;
            this.q = i7;
            this.r = i8;
            this.s = i9;
            return this;
        }

        public Builder setBiomDialogTitle(String str) {
            this.z = str;
            return this;
        }

        @Deprecated
        public Builder setExtendView1(int i, View.OnClickListener onClickListener) {
            this.I = i;
            this.N = onClickListener;
            return this;
        }

        @Deprecated
        public Builder setExtendView2(int i, View.OnClickListener onClickListener) {
            this.J = i;
            this.O = onClickListener;
            return this;
        }

        @Deprecated
        public Builder setExtendView3(int i, View.OnClickListener onClickListener) {
            this.K = i;
            this.P = onClickListener;
            return this;
        }

        @Deprecated
        public Builder setExtendView4(int i, View.OnClickListener onClickListener) {
            this.L = i;
            this.Q = onClickListener;
            return this;
        }

        @Deprecated
        public Builder setExtendView5(int i, View.OnClickListener onClickListener) {
            this.M = i;
            this.R = onClickListener;
            return this;
        }

        public Builder setFaceAuth(String str, int i, String str2) {
            this.u = str;
            this.w = i;
            this.y = str2;
            return this;
        }

        public Builder setFingerprintAuth(String str, int i, String str2) {
            this.t = str;
            this.v = i;
            this.x = str2;
            return this;
        }

        public Builder setFinishActivityTransition(int i, int i2) {
            this.T = true;
            this.W = i;
            this.X = i2;
            return this;
        }

        public Builder setMiniAuthActivityStyle(int i, int i2, int i3) {
            this.Y = i;
            this.Z = i2;
            this.aa = i3;
            return this;
        }

        public Builder setPrivacyDialogLayoutId(int i) {
            this.A = i;
            return this;
        }

        public Builder setPrivacyDialogViewIds(int i, int i2, int i3) {
            this.B = i;
            this.C = i2;
            this.D = i3;
            return this;
        }

        public Builder setStartActivityTransition(int i, int i2) {
            this.S = true;
            this.U = i;
            this.V = i2;
            return this;
        }

        public Builder setWebviewActivityLayoutId(int i) {
            this.E = i;
            return this;
        }

        public Builder setWebviewActivityViewIds(int i, int i2, int i3) {
            this.F = i;
            this.G = i2;
            this.H = i3;
            return this;
        }
    }

    public AuthPageConfig(Builder builder) {
        this.a = builder.a;
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
        this.r = builder.r;
        this.s = builder.s;
        this.t = builder.t;
        this.v = builder.v;
        this.x = builder.x;
        this.z = builder.z;
        this.u = builder.u;
        this.w = builder.w;
        this.y = builder.y;
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
        this.W = builder.W;
        this.X = builder.X;
        this.Y = builder.Y;
        this.Z = builder.Z;
        this.aa = builder.aa;
    }

    public View.OnClickListener A() {
        return this.Q;
    }

    public View.OnClickListener B() {
        return this.R;
    }

    public boolean C() {
        return this.S;
    }

    public boolean D() {
        return this.T;
    }

    public int E() {
        return this.U;
    }

    public int F() {
        return this.V;
    }

    public int G() {
        return this.W;
    }

    public int H() {
        return this.X;
    }

    public int I() {
        return this.Y;
    }

    public int J() {
        return this.Z;
    }

    public int K() {
        return this.aa;
    }

    public int a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public int e() {
        return this.e;
    }

    public int f() {
        return this.f;
    }

    public int g() {
        return this.g;
    }

    public int h() {
        return this.h;
    }

    public int i() {
        return this.i;
    }

    public int j() {
        return this.j;
    }

    public int k() {
        return this.A;
    }

    public int l() {
        return this.B;
    }

    public int m() {
        return this.C;
    }

    public int n() {
        return this.D;
    }

    public int o() {
        return this.E;
    }

    public int p() {
        return this.F;
    }

    public int q() {
        return this.G;
    }

    public int r() {
        return this.H;
    }

    public int s() {
        return this.I;
    }

    public int t() {
        return this.J;
    }

    public int u() {
        return this.K;
    }

    public int v() {
        return this.L;
    }

    public int w() {
        return this.M;
    }

    public View.OnClickListener x() {
        return this.N;
    }

    public View.OnClickListener y() {
        return this.O;
    }

    public View.OnClickListener z() {
        return this.P;
    }
}