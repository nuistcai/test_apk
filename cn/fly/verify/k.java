package cn.fly.verify;

import android.view.View;
import cn.fly.verify.OAuthPageEventCallback;
import java.util.List;

/* loaded from: classes.dex */
public class k {
    private static k a;
    private List<View> b;
    private List<View> c;
    private View d;
    private CustomViewClickListener e;
    private CustomViewClickListener f;
    private OAuthPageEventCallback.OAuthPageEventWrapper g;
    private boolean h;
    private PageCallback i;
    private VerifyCallback j;

    private k() {
    }

    public static k a() {
        if (a == null) {
            synchronized (k.class) {
                if (a == null) {
                    a = new k();
                }
            }
        }
        return a;
    }

    public void a(View view) {
        this.d = view;
    }

    public void a(OAuthPageEventCallback.OAuthPageEventWrapper oAuthPageEventWrapper) {
        this.g = oAuthPageEventWrapper;
    }

    public void a(PageCallback pageCallback) {
        this.i = pageCallback;
    }

    public void a(VerifyCallback verifyCallback) {
        this.j = verifyCallback;
    }

    public void a(List<View> list, CustomViewClickListener customViewClickListener) {
        this.b = list;
        this.e = customViewClickListener;
    }

    public void a(boolean z) {
        this.h = z;
    }

    public List<View> b() {
        return this.b;
    }

    public void b(List<View> list, CustomViewClickListener customViewClickListener) {
        this.c = list;
        this.f = customViewClickListener;
    }

    public List<View> c() {
        return this.c;
    }

    public CustomViewClickListener d() {
        return this.e;
    }

    public CustomViewClickListener e() {
        return this.f;
    }

    public View f() {
        return this.d;
    }

    public void g() {
        this.b = null;
        this.d = null;
        this.c = null;
        this.f = null;
        this.e = null;
        this.g = null;
        this.i = null;
    }

    public OAuthPageEventCallback.OAuthPageEventWrapper h() {
        return this.g;
    }

    public boolean i() {
        return this.h;
    }

    public PageCallback j() {
        return this.i;
    }
}