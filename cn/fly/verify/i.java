package cn.fly.verify;

import android.app.Activity;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.datatype.UiSettings;
import cn.fly.verify.ui.component.LoginAdapter;

/* loaded from: classes.dex */
public class i {
    private static i a;
    private String b;
    private a c;
    private UiSettings d;
    private LandUiSettings e;
    private String f;
    private Class<? extends LoginAdapter> g;
    private int j;
    private Activity l;
    private boolean p;
    private volatile boolean h = true;
    private boolean i = false;
    private boolean k = true;
    private boolean m = true;
    private boolean n = true;
    private boolean o = true;

    private i() {
    }

    public static i a() {
        if (a == null) {
            synchronized (i.class) {
                if (a == null) {
                    a = new i();
                }
            }
        }
        return a;
    }

    public void a(int i) {
        this.j = i;
    }

    public void a(Activity activity) {
        this.l = activity;
    }

    public void a(a aVar) {
        this.c = aVar;
    }

    public void a(LandUiSettings landUiSettings) {
        this.e = landUiSettings;
    }

    public void a(UiSettings uiSettings) {
        this.d = uiSettings;
    }

    public void a(Class<? extends LoginAdapter> cls) {
        this.g = cls;
    }

    public void a(String str) {
        this.f = str;
    }

    public void a(boolean z) {
        this.h = z;
    }

    public a b() {
        return this.c;
    }

    public void b(int i) {
        String str;
        ak.a().a(i);
        switch (i) {
            case 1:
                str = "CMCC";
                break;
            case 2:
                str = "CUCC";
                break;
            case 3:
                str = "CTCC";
                break;
            default:
                return;
        }
        this.b = str;
    }

    public void b(boolean z) {
        this.i = z;
    }

    public UiSettings c() {
        return this.d;
    }

    public void c(boolean z) {
        this.k = z;
    }

    public LandUiSettings d() {
        return this.e;
    }

    public void d(boolean z) {
        this.n = z;
    }

    public String e() {
        return this.f;
    }

    public void e(boolean z) {
        this.o = z;
    }

    public Class<? extends LoginAdapter> f() {
        return this.g;
    }

    public void f(boolean z) {
        this.p = z;
    }

    public boolean g() {
        return this.h;
    }

    public boolean h() {
        return this.i;
    }

    public int i() {
        return this.j;
    }

    public String j() {
        return this.b;
    }

    public boolean k() {
        return this.k;
    }

    public Activity l() {
        return this.l;
    }

    public boolean m() {
        return this.n;
    }

    public boolean n() {
        return this.o;
    }

    public boolean o() {
        return this.p;
    }
}