package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.BuglyBroadcastReceiver;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.crashreport.crash.d;
import com.tencent.bugly.proguard.n;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.x;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public class CrashModule extends a {
    public static final int MODULE_ID = 1004;
    private static int c = 0;
    private static CrashModule e = new CrashModule();
    private long a;
    private BuglyStrategy.a b;
    private boolean d = false;

    public static CrashModule getInstance() {
        e.id = 1004;
        return e;
    }

    public synchronized boolean hasInitialized() {
        return this.d;
    }

    @Override // com.tencent.bugly.a
    public synchronized void init(Context context, boolean z, BuglyStrategy buglyStrategy) {
        if (context != null) {
            if (!this.d) {
                x.a("Initializing crash module.", new Object[0]);
                n nVarA = n.a();
                int i = c + 1;
                c = i;
                nVarA.a(1004, i);
                this.d = true;
                CrashReport.setContext(context);
                a(context, buglyStrategy);
                c cVarA = c.a(1004, context, z, this.b, (o) null, (String) null);
                cVarA.e();
                if (buglyStrategy != null) {
                    cVarA.a(buglyStrategy.getCallBackType());
                    cVarA.a(buglyStrategy.getCloseErrorCallback());
                }
                cVarA.m();
                if (buglyStrategy == null || buglyStrategy.isEnableNativeCrashMonitor()) {
                    cVarA.g();
                } else {
                    x.a("[crash] Closed native crash monitor!", new Object[0]);
                    cVarA.f();
                }
                if (buglyStrategy == null || buglyStrategy.isEnableANRCrashMonitor()) {
                    cVarA.h();
                } else {
                    x.a("[crash] Closed ANR monitor!", new Object[0]);
                    cVarA.i();
                }
                cVarA.a(buglyStrategy != null ? buglyStrategy.getAppReportDelay() : 0L);
                cVarA.l();
                d.a(context);
                BuglyBroadcastReceiver buglyBroadcastReceiver = BuglyBroadcastReceiver.getInstance();
                buglyBroadcastReceiver.addFilter("android.net.conn.CONNECTIVITY_CHANGE");
                buglyBroadcastReceiver.register(context);
                n nVarA2 = n.a();
                int i2 = c - 1;
                c = i2;
                nVarA2.a(1004, i2);
            }
        }
    }

    private synchronized void a(Context context, BuglyStrategy buglyStrategy) {
        if (buglyStrategy == null) {
            return;
        }
        String libBuglySOFilePath = buglyStrategy.getLibBuglySOFilePath();
        if (!TextUtils.isEmpty(libBuglySOFilePath)) {
            com.tencent.bugly.crashreport.common.info.a.a(context).n = libBuglySOFilePath;
            x.a("setted libBugly.so file path :%s", libBuglySOFilePath);
        }
        if (buglyStrategy.getCrashHandleCallback() != null) {
            this.b = buglyStrategy.getCrashHandleCallback();
            x.a("setted CrashHanldeCallback", new Object[0]);
        }
        if (buglyStrategy.getAppReportDelay() > 0) {
            this.a = buglyStrategy.getAppReportDelay();
            x.a("setted delay: %d", Long.valueOf(this.a));
        }
    }

    @Override // com.tencent.bugly.a
    public void onServerStrategyChanged(StrategyBean strategyBean) {
        c cVarA;
        if (strategyBean != null && (cVarA = c.a()) != null) {
            cVarA.a(strategyBean);
        }
    }

    @Override // com.tencent.bugly.a
    public String[] getTables() {
        return new String[]{"t_cr"};
    }
}