package com.cmic.gen.sdk.c.a;

import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.c.RedirectHandler;
import com.cmic.gen.sdk.c.c.HttpRequest;
import com.cmic.gen.sdk.c.d.HttpErrorResponse;
import com.cmic.gen.sdk.c.d.HttpSuccessResponse;
import com.cmic.gen.sdk.c.d.IResponse;
import com.cmic.gen.sdk.e.LogUtils;

/* compiled from: RetryAndRedirectInterceptor.java */
/* renamed from: com.cmic.gen.sdk.c.a.c, reason: use source file name */
/* loaded from: classes.dex */
public class RetryAndRedirectInterceptor implements IInterceptor {
    private IInterceptor a;
    private IResponse b;
    private final RedirectHandler c = new RedirectHandler();

    @Override // com.cmic.gen.sdk.c.a.IInterceptor
    public void a(HttpRequest httpRequest, IResponse iResponse, ConcurrentBundle concurrentBundle) {
        b(httpRequest, iResponse, concurrentBundle);
    }

    public void a(IInterceptor iInterceptor) {
        this.a = iInterceptor;
    }

    public void b(final HttpRequest httpRequest, final IResponse iResponse, final ConcurrentBundle concurrentBundle) {
        if (this.a != null) {
            this.b = new IResponse() { // from class: com.cmic.gen.sdk.c.a.c.1
                @Override // com.cmic.gen.sdk.c.d.IResponse
                public void a(HttpSuccessResponse httpSuccessResponse) {
                    if (httpSuccessResponse.d()) {
                        RetryAndRedirectInterceptor.this.b(RetryAndRedirectInterceptor.this.c.a(httpRequest, httpSuccessResponse, concurrentBundle), iResponse, concurrentBundle);
                    } else if (!TextUtils.isEmpty(RetryAndRedirectInterceptor.this.c.a())) {
                        RetryAndRedirectInterceptor.this.b(RetryAndRedirectInterceptor.this.c.b(httpRequest, httpSuccessResponse, concurrentBundle), iResponse, concurrentBundle);
                    } else {
                        iResponse.a(httpSuccessResponse);
                    }
                }

                @Override // com.cmic.gen.sdk.c.d.IResponse
                public void a(HttpErrorResponse httpErrorResponse) {
                    if (httpRequest.j()) {
                        LogUtils.a("RetryAndRedirectInterceptor", "retry: " + httpRequest.a());
                        RetryAndRedirectInterceptor.this.b(httpRequest, iResponse, concurrentBundle);
                    } else {
                        iResponse.a(httpErrorResponse);
                    }
                }
            };
            if (httpRequest.g()) {
                this.a.a(httpRequest, this.b, concurrentBundle);
            } else {
                iResponse.a(HttpErrorResponse.a(200025));
            }
        }
    }
}