package cn.com.chinatelecom.account.sdk.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import cn.com.chinatelecom.account.api.d.f;
import cn.com.chinatelecom.account.api.d.g;
import cn.com.chinatelecom.account.api.d.j;

/* loaded from: classes.dex */
public final class AuthActivity extends cn.com.chinatelecom.account.sdk.ui.a {
    private static final String s = AuthActivity.class.getSimpleName();
    private static AuthActivity t = null;
    private a u = null;
    private String v = null;

    private class a extends BroadcastReceiver {
        private a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            AuthActivity.this.g();
        }
    }

    public static synchronized AuthActivity a() {
        return t;
    }

    private void k() {
        f.a(b()).a(cn.com.chinatelecom.account.api.d.d.a(this)).c("Login").b(g.f(this)).f(g.i(this));
    }

    private void l() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("cn.com.chinatelecom.account.sdk.preAuth.Action");
            this.u = new a();
            registerReceiver(this.u, intentFilter, null, null);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m() {
        try {
            if (this.u != null) {
                unregisterReceiver(this.u);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.ui.a
    protected String b() {
        if (this.v == null) {
            this.v = cn.com.chinatelecom.account.api.d.d.a();
        }
        return this.v;
    }

    @Override // cn.com.chinatelecom.account.sdk.ui.a, cn.com.chinatelecom.account.sdk.ui.c
    public void c() {
        f.a(this.v).b(g.f(this)).c(0L);
        cn.com.chinatelecom.account.sdk.a.a.a().a(j.f());
    }

    public void d() {
        cn.com.chinatelecom.account.api.a.a(s, "finishActivity");
        synchronized (AuthActivity.class) {
            if (t != null && !t.isFinishing()) {
                t.finish();
                t = null;
            }
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        c();
    }

    @Override // cn.com.chinatelecom.account.sdk.ui.a, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        synchronized (AuthActivity.class) {
            t = this;
        }
        l();
        k();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        j();
        m();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
    }
}