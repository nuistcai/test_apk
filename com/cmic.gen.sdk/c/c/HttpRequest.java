package com.cmic.gen.sdk.c.c;

import android.net.Network;
import com.cmic.gen.sdk.auth.AuthnHelperCore;
import com.cmic.gen.sdk.c.b.GetSdkPageOptionParameter;
import com.cmic.gen.sdk.c.b.RequestParameter;
import com.cmic.gen.sdk.e.OverTimeUtils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: HttpRequest.java */
/* renamed from: com.cmic.gen.sdk.c.c.c, reason: use source file name */
/* loaded from: classes.dex */
public class HttpRequest {
    String a;
    private final String b;
    private final Map<String, String> c;
    private final String d;
    private boolean e;
    private final String f;
    private Network g;
    private long h;
    private final String i;
    private int j;
    private final RequestParameter k;

    private HttpRequest(String str, Map<String, String> map, RequestParameter requestParameter, String str2, String str3) {
        this.e = false;
        this.b = str;
        this.k = requestParameter;
        this.c = map == null ? new HashMap<>() : map;
        this.a = requestParameter == null ? "" : requestParameter.b().toString();
        this.d = str2;
        this.f = str3;
        this.i = requestParameter != null ? requestParameter.a() : "";
        this.c.put("interfaceVersion", "1.0");
        if (requestParameter instanceof GetSdkPageOptionParameter) {
            this.c.put("timestamp", ((GetSdkPageOptionParameter) requestParameter).c());
        }
        l();
    }

    public HttpRequest(String str, RequestParameter requestParameter, String str2, String str3) {
        this(str, null, requestParameter, str2, str3);
    }

    private void l() {
        this.c.put("sdkVersion", AuthnHelperCore.SDK_VERSION);
        this.c.put("Content-Type", "application/json");
        this.c.put("CMCC-EncryptType", "STD");
        this.c.put("traceId", this.f);
        this.c.put("appid", this.i);
        this.c.put("connection", "Keep-Alive");
    }

    public void a(String str, String str2) {
        this.c.put(str, str2);
    }

    public String a() {
        return this.b;
    }

    public boolean b() {
        return this.e;
    }

    void a(boolean z) {
        this.e = z;
    }

    public Map<String, String> c() {
        return this.c;
    }

    public String d() {
        return this.a;
    }

    public String e() {
        return this.d;
    }

    public String f() {
        return this.f;
    }

    public boolean g() {
        return !OverTimeUtils.a(this.f) || this.b.contains("logReport") || this.b.contains("uniConfig");
    }

    public Network h() {
        return this.g;
    }

    public void a(Network network) {
        this.g = network;
    }

    public void a(long j) {
        this.h = j;
    }

    public long i() {
        return this.h;
    }

    public boolean j() {
        int i = this.j;
        this.j = i + 1;
        return i < 2;
    }

    public RequestParameter k() {
        return this.k;
    }
}