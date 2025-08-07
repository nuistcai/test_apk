package com.cmic.gen.sdk.c.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.c.c.HttpRequest;
import com.cmic.gen.sdk.c.d.HttpErrorResponse;
import com.cmic.gen.sdk.c.d.HttpSuccessResponse;
import com.cmic.gen.sdk.c.d.IResponse;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.ThreadUtils;
import com.cmic.gen.sdk.e.WifiNetworkUtils;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: WifiChangeInterceptor.java */
/* renamed from: com.cmic.gen.sdk.c.a.d, reason: use source file name */
/* loaded from: classes.dex */
public class WifiChangeInterceptor implements IInterceptor {
    private IInterceptor a;

    @Override // com.cmic.gen.sdk.c.a.IInterceptor
    public void a(final HttpRequest httpRequest, final IResponse iResponse, final ConcurrentBundle concurrentBundle) {
        if (!httpRequest.b()) {
            b(httpRequest, iResponse, concurrentBundle);
            return;
        }
        final WifiNetworkUtils wifiNetworkUtilsA = WifiNetworkUtils.a((Context) null);
        if (Build.VERSION.SDK_INT >= 21) {
            wifiNetworkUtilsA.a(new WifiNetworkUtils.a() { // from class: com.cmic.gen.sdk.c.a.d.1
                private final AtomicBoolean f = new AtomicBoolean(false);

                @Override // com.cmic.gen.sdk.e.WifiNetworkUtils.a
                public void a(final Network network, final ConnectivityManager.NetworkCallback networkCallback) {
                    if (!this.f.getAndSet(true)) {
                        ThreadUtils.a(new ThreadUtils.a(null, concurrentBundle) { // from class: com.cmic.gen.sdk.c.a.d.1.1
                            @Override // com.cmic.gen.sdk.e.ThreadUtils.a
                            protected void a() {
                                if (network != null) {
                                    LogUtils.b("WifiChangeInterceptor", "onAvailable");
                                    httpRequest.a(network);
                                    WifiChangeInterceptor.this.b(httpRequest, iResponse, concurrentBundle);
                                } else {
                                    iResponse.a(HttpErrorResponse.a(102508));
                                }
                                wifiNetworkUtilsA.a(networkCallback);
                            }
                        });
                    }
                }
            });
        } else {
            LogUtils.a("WifiChangeInterceptor", "低版本不在支持wifi切换");
            iResponse.a(HttpErrorResponse.a(102508));
        }
    }

    public void b(HttpRequest httpRequest, final IResponse iResponse, ConcurrentBundle concurrentBundle) {
        if (this.a != null) {
            this.a.a(httpRequest, new IResponse() { // from class: com.cmic.gen.sdk.c.a.d.2
                @Override // com.cmic.gen.sdk.c.d.IResponse
                public void a(HttpSuccessResponse httpSuccessResponse) {
                    iResponse.a(httpSuccessResponse);
                }

                @Override // com.cmic.gen.sdk.c.d.IResponse
                public void a(HttpErrorResponse httpErrorResponse) {
                    iResponse.a(httpErrorResponse);
                }
            }, concurrentBundle);
        }
    }

    public void a(IInterceptor iInterceptor) {
        this.a = iInterceptor;
    }
}