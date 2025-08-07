package com.cmic.gen.sdk.c.d;

/* compiled from: HttpErrorResponse.java */
/* renamed from: com.cmic.gen.sdk.c.d.a, reason: use source file name */
/* loaded from: classes.dex */
public class HttpErrorResponse {
    private int a;
    private String b;

    private HttpErrorResponse(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public int a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public static HttpErrorResponse a(int i) {
        switch (i) {
            case 102102:
                return new HttpErrorResponse(102102, "网络异常");
            case 102223:
                return new HttpErrorResponse(102223, "数据解析异常");
            case 102508:
                return new HttpErrorResponse(102508, "数据网络切换失败");
            case 200025:
                return new HttpErrorResponse(200025, "登录超时");
            case 200039:
                return new HttpErrorResponse(200039, "电信取号接口失败");
            case 200050:
                return new HttpErrorResponse(200050, "EOF异常");
            default:
                return new HttpErrorResponse(i, "网络异常");
        }
    }
}