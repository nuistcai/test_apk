package cn.com.chinatelecom.account.sdk.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import cn.com.chinatelecom.account.api.d.f;
import cn.com.chinatelecom.account.api.d.g;
import cn.com.chinatelecom.account.api.d.j;

/* loaded from: classes.dex */
public final class MiniAuthActivity extends cn.com.chinatelecom.account.sdk.ui.a {
    private static final String s = MiniAuthActivity.class.getSimpleName();
    private static MiniAuthActivity t = null;
    private a u = null;
    private String v = null;

    private class a extends BroadcastReceiver {
        private a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            MiniAuthActivity.this.g();
        }
    }

    public static synchronized MiniAuthActivity a() {
        return t;
    }

    private void a(int i, int i2, int i3) {
        try {
            Point pointA = this.q.a((Activity) this);
            if (i == 0) {
                double d = pointA.x;
                Double.isNaN(d);
                i = (int) (d * 0.8d);
            }
            if (i2 == 0) {
                double d2 = pointA.x;
                Double.isNaN(d2);
                i2 = (int) (d2 * 0.8d);
            }
            if (i3 == 0) {
                i3 = 17;
            }
            Window window = getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.width = i;
                attributes.height = i2;
                window.setAttributes(attributes);
                window.setGravity(i3);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void k() {
        f.a(b()).a(cn.com.chinatelecom.account.api.d.d.a(this)).c("MiniLogin").b(g.f(this)).f(g.i(this));
    }

    private void l() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("cn.com.chinatelecom.account.sdk.preAuth.Action");
            this.u = new a();
            registerReceiver(this.u, intentFilter);
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
        synchronized (MiniAuthActivity.class) {
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
        synchronized (MiniAuthActivity.class) {
            t = this;
        }
        l();
        a(this.o.I(), this.o.J(), this.o.K());
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