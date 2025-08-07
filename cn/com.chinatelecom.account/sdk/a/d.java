package cn.com.chinatelecom.account.sdk.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import cn.com.chinatelecom.account.sdk.AuthPageConfig;
import cn.com.chinatelecom.account.sdk.AuthViewConfig;
import cn.com.chinatelecom.account.sdk.ui.AuthActivity;
import cn.com.chinatelecom.account.sdk.ui.MiniAuthActivity;

/* loaded from: classes.dex */
public class d {
    private static AuthPageConfig a;
    private static AuthViewConfig b;
    private static volatile d c;

    public static d a() {
        if (c == null) {
            synchronized (d.class) {
                if (c == null) {
                    c = new d();
                }
            }
        }
        return c;
    }

    public static synchronized void f() {
        a = null;
        b = null;
    }

    public void a(Context context) {
        if (cn.com.chinatelecom.account.api.b.a(context)) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), "cn.com.chinatelecom.account.sdk.ui.AuthActivity");
        context.startActivity(intent);
        if (a != null && a.C() && (context instanceof Activity)) {
            ((Activity) context).overridePendingTransition(a.E(), a.F());
        }
    }

    public void a(AuthPageConfig authPageConfig) {
        synchronized (d.class) {
            a = authPageConfig;
        }
    }

    public void a(AuthViewConfig authViewConfig) {
        synchronized (d.class) {
            b = authViewConfig;
        }
    }

    public AuthPageConfig b() {
        AuthPageConfig authPageConfig;
        synchronized (d.class) {
            authPageConfig = a;
        }
        return authPageConfig;
    }

    public void b(Context context) {
        if (cn.com.chinatelecom.account.api.b.a(context)) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), "cn.com.chinatelecom.account.sdk.ui.MiniAuthActivity");
        context.startActivity(intent);
        if (a != null && a.C() && (context instanceof Activity)) {
            ((Activity) context).overridePendingTransition(a.E(), a.F());
        }
    }

    public AuthViewConfig c() {
        AuthViewConfig authViewConfig;
        synchronized (d.class) {
            authViewConfig = b;
        }
        return authViewConfig;
    }

    public void d() {
        AuthActivity authActivityA = AuthActivity.a();
        if (authActivityA != null) {
            authActivityA.d();
            if (a == null || !a.D()) {
                return;
            }
            authActivityA.overridePendingTransition(a.G(), a.H());
        }
    }

    public void e() {
        MiniAuthActivity miniAuthActivityA = MiniAuthActivity.a();
        if (miniAuthActivityA != null) {
            miniAuthActivityA.d();
            if (a == null || !a.D()) {
                return;
            }
            miniAuthActivityA.overridePendingTransition(a.G(), a.H());
        }
    }
}