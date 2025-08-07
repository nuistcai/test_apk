package com.cmic.gen.sdk.c.d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: HttpSuccessResponse.java */
/* renamed from: com.cmic.gen.sdk.c.d.b, reason: use source file name */
/* loaded from: classes.dex */
public class HttpSuccessResponse {
    private int a;
    private Map<String, List<String>> b;
    private String c;

    public HttpSuccessResponse(int i, Map<String, List<String>> map, String str) {
        this.a = i;
        this.b = map;
        this.c = str;
    }

    public int a() {
        return this.a;
    }

    public Map<String, List<String>> b() {
        return this.b == null ? new HashMap() : this.b;
    }

    public String c() {
        return this.c == null ? "" : this.c;
    }

    public boolean d() {
        return this.a == 302 || this.a == 301;
    }
}