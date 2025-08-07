package com.cmic.gen.sdk.c.b;

import com.cmic.gen.sdk.e.MD5STo16Byte;
import org.json.JSONObject;

/* compiled from: RequestParameter.java */
/* renamed from: com.cmic.gen.sdk.c.b.h, reason: use source file name */
/* loaded from: classes.dex */
public abstract class RequestParameter {
    public abstract String a();

    protected abstract String a_(String str);

    public abstract JSONObject b();

    public String x(String str) {
        return MD5STo16Byte.a(a_(str)).toLowerCase();
    }
}