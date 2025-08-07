package com.cmic.gen.sdk.a;

import android.content.Context;
import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.a.UmcConfigHandle;
import com.cmic.gen.sdk.auth.AuthnHelperCore;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.SharedPreferencesUtil;

/* compiled from: UmcConfigManager.java */
/* renamed from: com.cmic.gen.sdk.a.c, reason: use source file name */
/* loaded from: classes.dex */
public class UmcConfigManager implements UmcConfigHandle.a {
    private static UmcConfigManager a;
    private UmcConfigBean b;
    private UmcConfigBean c;
    private UmcConfigHandle d;
    private Context e;

    private UmcConfigManager(Context context) {
        this.e = context;
        b();
    }

    private void b() {
        String strB = SharedPreferencesUtil.b("sdk_config_version", "");
        if (!TextUtils.isEmpty(strB) && AuthnHelperCore.SDK_VERSION.equals(strB)) {
            this.d = UmcConfigHandle.a(false);
            this.b = this.d.b();
        } else {
            this.d = UmcConfigHandle.a(true);
            this.b = this.d.a();
            if (!TextUtils.isEmpty(strB)) {
                c();
            }
        }
        this.d.a(this);
        this.c = this.d.a();
    }

    public static UmcConfigManager a(Context context) {
        if (a == null) {
            synchronized (UmcConfigManager.class) {
                if (a == null) {
                    a = new UmcConfigManager(context);
                }
            }
        }
        return a;
    }

    public UmcConfigBean a() {
        try {
            return this.b.clone();
        } catch (CloneNotSupportedException e) {
            return this.c;
        }
    }

    @Override // com.cmic.gen.sdk.a.UmcConfigHandle.a
    public void a(UmcConfigBean umcConfigBean) {
        this.b = umcConfigBean;
    }

    public void a(ConcurrentBundle concurrentBundle) {
        this.d.a(concurrentBundle);
    }

    private void c() {
        LogUtils.b("UmcConfigManager", "delete localConfig");
        this.d.c();
    }
}