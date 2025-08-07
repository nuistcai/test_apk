package com.cmic.gen.sdk.view;

import com.cmic.gen.sdk.e.LogUtils;

/* compiled from: LoginProxy.java */
/* renamed from: com.cmic.gen.sdk.view.b, reason: use source file name */
/* loaded from: classes.dex */
public class LoginProxy {
    private static LoginProxy b = null;
    public int a = 1;
    private a c;

    /* compiled from: LoginProxy.java */
    /* renamed from: com.cmic.gen.sdk.view.b$a */
    public interface a {
        void a();
    }

    public static LoginProxy a() {
        if (b == null) {
            synchronized (LoginProxy.class) {
                if (b == null) {
                    b = new LoginProxy();
                }
            }
        }
        return b;
    }

    public a b() {
        return this.c;
    }

    public void c() {
        if (this.c != null && this.a != 1) {
            this.c = null;
            LogUtils.b("LoginProxy", "mLoginAuthProxy == null");
        }
    }

    public void a(a aVar) {
        this.a = 1;
        this.c = aVar;
    }
}