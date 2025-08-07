package cn.com.chinatelecom.account.sdk.b;

import android.app.Activity;
import android.view.View;
import cn.com.chinatelecom.account.sdk.AuthPageConfig;
import cn.com.chinatelecom.account.sdk.AuthViewConfig;
import cn.com.chinatelecom.account.sdk.inter.UiHandler;

/* loaded from: classes.dex */
public final class d implements UiHandler {
    private Activity a;
    private AuthViewConfig b;
    private AuthPageConfig c;
    private cn.com.chinatelecom.account.sdk.ui.c d;

    public d(Activity activity, AuthViewConfig authViewConfig, AuthPageConfig authPageConfig, cn.com.chinatelecom.account.sdk.ui.c cVar) {
        this.a = activity;
        this.b = authViewConfig;
        this.c = authPageConfig;
        this.d = cVar;
    }

    @Override // cn.com.chinatelecom.account.sdk.inter.UiHandler
    public void closeActivity() {
        this.d.c();
    }

    @Override // cn.com.chinatelecom.account.sdk.inter.UiHandler
    public void continueExecution() {
        if (this.b.aA == this.c.e()) {
            this.d.e();
        } else if (this.b.aA == this.c.h()) {
            this.d.f();
        } else if (this.b.aA == this.c.b()) {
            this.d.c();
        }
        if (this.b.aC != null) {
            for (int i = 0; i < this.b.aC.size(); i++) {
                if (this.b.aC.get(i).intValue() == this.c.e()) {
                    this.d.e();
                } else if (this.b.aC.get(i).intValue() == this.c.h()) {
                    this.d.f();
                } else if (this.b.aC.get(i).intValue() == this.c.b()) {
                    this.d.c();
                }
            }
        }
    }

    @Override // cn.com.chinatelecom.account.sdk.inter.UiHandler
    public void execOtherLoginWayAction() {
        this.d.f();
    }

    @Override // cn.com.chinatelecom.account.sdk.inter.UiHandler
    public void executeLogin() {
        this.d.e();
    }

    @Override // cn.com.chinatelecom.account.sdk.inter.UiHandler
    public View findViewById(int i) {
        if (this.a == null || this.a.isFinishing()) {
            return null;
        }
        return this.a.findViewById(i);
    }
}