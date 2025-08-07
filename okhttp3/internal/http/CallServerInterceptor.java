package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealConnection;
import okio.BufferedSink;
import okio.Okio;

/* compiled from: CallServerInterceptor.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lokhttp3/internal/http/CallServerInterceptor;", "Lokhttp3/Interceptor;", "forWebSocket", "", "(Z)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean forWebSocket) {
        this.forWebSocket = forWebSocket;
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x018f  */
    @Override // okhttp3.Interceptor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Long lValueOf;
        ResponseBody responseBodyBody;
        Intrinsics.checkParameterIsNotNull(chain, "chain");
        RealInterceptorChain realChain = (RealInterceptorChain) chain;
        Exchange exchange = realChain.exchange();
        Request request = realChain.getRequest();
        RequestBody requestBody = request.body();
        long sentRequestMillis = System.currentTimeMillis();
        exchange.writeRequestHeaders(request);
        boolean responseHeadersStarted = false;
        Response.Builder responseBuilder = (Response.Builder) null;
        if (!HttpMethod.permitsRequestBody(request.method()) || requestBody == null) {
            exchange.noRequestBody();
        } else {
            if (StringsKt.equals("100-continue", request.header("Expect"), true)) {
                exchange.flushRequest();
                responseHeadersStarted = true;
                exchange.responseHeadersStart();
                responseBuilder = exchange.readResponseHeaders(true);
            }
            if (responseBuilder != null) {
                exchange.noRequestBody();
                RealConnection realConnectionConnection = exchange.connection();
                if (realConnectionConnection == null) {
                    Intrinsics.throwNpe();
                }
                if (!realConnectionConnection.isMultiplexed()) {
                    exchange.noNewExchangesOnConnection();
                }
            } else if (requestBody.isDuplex()) {
                exchange.flushRequest();
                requestBody.writeTo(Okio.buffer(exchange.createRequestBody(request, true)));
            } else {
                BufferedSink bufferedRequestBody = Okio.buffer(exchange.createRequestBody(request, false));
                requestBody.writeTo(bufferedRequestBody);
                bufferedRequestBody.close();
            }
        }
        if (requestBody == null || !requestBody.isDuplex()) {
            exchange.finishRequest();
        }
        if (!responseHeadersStarted) {
            exchange.responseHeadersStart();
        }
        if (responseBuilder == null) {
            Response.Builder responseHeaders = exchange.readResponseHeaders(false);
            if (responseHeaders == null) {
                Intrinsics.throwNpe();
            }
            responseBuilder = responseHeaders;
        }
        Response.Builder builderRequest = responseBuilder.request(request);
        RealConnection realConnectionConnection2 = exchange.connection();
        if (realConnectionConnection2 == null) {
            Intrinsics.throwNpe();
        }
        Response response = builderRequest.handshake(realConnectionConnection2.getHandshake()).sentRequestAtMillis(sentRequestMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
        int code = response.code();
        if (code == 100) {
            Response.Builder responseHeaders2 = exchange.readResponseHeaders(false);
            if (responseHeaders2 == null) {
                Intrinsics.throwNpe();
            }
            Response.Builder builderRequest2 = responseHeaders2.request(request);
            RealConnection realConnectionConnection3 = exchange.connection();
            if (realConnectionConnection3 == null) {
                Intrinsics.throwNpe();
            }
            response = builderRequest2.handshake(realConnectionConnection3.getHandshake()).sentRequestAtMillis(sentRequestMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
            code = response.code();
        }
        exchange.responseHeadersEnd(response);
        Response response2 = (this.forWebSocket && code == 101) ? response.newBuilder().body(Util.EMPTY_RESPONSE).build() : response.newBuilder().body(exchange.openResponseBody(response)).build();
        if (!StringsKt.equals("close", response2.request().header("Connection"), true)) {
            lValueOf = null;
            if (StringsKt.equals("close", Response.header$default(response2, "Connection", null, 2, null), true)) {
            }
            if (code != 204 || code == 205) {
                responseBodyBody = response2.body();
                if ((responseBodyBody == null ? responseBodyBody.getContentLength() : -1L) > 0) {
                    StringBuilder sbAppend = new StringBuilder().append("HTTP ").append(code).append(" had non-zero Content-Length: ");
                    ResponseBody responseBodyBody2 = response2.body();
                    if (responseBodyBody2 != null) {
                        lValueOf = Long.valueOf(responseBodyBody2.getContentLength());
                    }
                    throw new ProtocolException(sbAppend.append(lValueOf).toString());
                }
            }
            return response2;
        }
        lValueOf = null;
        exchange.noNewExchangesOnConnection();
        if (code != 204) {
            responseBodyBody = response2.body();
            if ((responseBodyBody == null ? responseBodyBody.getContentLength() : -1L) > 0) {
            }
        }
        return response2;
    }
}