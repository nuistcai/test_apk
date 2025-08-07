package com.cmic.gen.sdk.c.c;

import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.c.b.BaseScripParameter;
import com.cmic.gen.sdk.c.b.GetPrePhoneScripParameter;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.UmcIPUtils;
import java.net.SocketException;

/* compiled from: HttpGetPrephoneRequest.java */
/* renamed from: com.cmic.gen.sdk.c.c.b, reason: use source file name */
/* loaded from: classes.dex */
public class HttpGetPrephoneRequest extends HttpRequest {
    private final GetPrePhoneScripParameter b;
    private boolean c;

    HttpGetPrephoneRequest(String str, GetPrePhoneScripParameter getPrePhoneScripParameter, String str2, String str3) {
        super(str, getPrePhoneScripParameter, str2, str3);
        this.c = false;
        this.b = getPrePhoneScripParameter;
    }

    public void a(ConcurrentBundle concurrentBundle) throws SocketException {
        String[] strArrA;
        BaseScripParameter baseScripParameterC = this.b.c();
        baseScripParameterC.u(concurrentBundle.b("socketip"));
        LogUtils.b("GetPrePhonescripParam", "socket socketip = " + concurrentBundle.b("socketip"));
        if (!this.c) {
            if (concurrentBundle.b("isCloseIpv4", false)) {
                strArrA = null;
            } else {
                strArrA = UmcIPUtils.a(true);
                baseScripParameterC.q(strArrA[0]);
            }
            if (!concurrentBundle.b("isCloseIpv6", false)) {
                if (strArrA == null) {
                    strArrA = UmcIPUtils.a(true);
                }
                baseScripParameterC.r(strArrA[1]);
            }
            this.c = true;
        }
        baseScripParameterC.n(baseScripParameterC.x(concurrentBundle.b("appkey")));
        this.b.a(baseScripParameterC);
        this.b.a(true);
        this.a = this.b.b().toString();
    }
}