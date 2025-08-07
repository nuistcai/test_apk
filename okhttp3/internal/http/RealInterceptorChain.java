package okhttp3.internal.http;

import androidx.core.app.NotificationCompat;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.Transmitter;

/* compiled from: RealInterceptorChain.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001BU\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\n\u0012\u0006\u0010\u0010\u001a\u00020\n\u0012\u0006\u0010\u0011\u001a\u00020\n¢\u0006\u0002\u0010\u0012J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u0014\u001a\u00020\nH\u0016J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\b\u0010\u0019\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010 \u001a\u00020\nH\u0016R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lokhttp3/internal/http/RealInterceptorChain;", "Lokhttp3/Interceptor$Chain;", "interceptors", "", "Lokhttp3/Interceptor;", "transmitter", "Lokhttp3/internal/connection/Transmitter;", "exchange", "Lokhttp3/internal/connection/Exchange;", "index", "", "request", "Lokhttp3/Request;", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "connectTimeout", "readTimeout", "writeTimeout", "(Ljava/util/List;Lokhttp3/internal/connection/Transmitter;Lokhttp3/internal/connection/Exchange;ILokhttp3/Request;Lokhttp3/Call;III)V", "calls", "connectTimeoutMillis", "connection", "Lokhttp3/Connection;", "proceed", "Lokhttp3/Response;", "readTimeoutMillis", "withConnectTimeout", "timeout", "unit", "Ljava/util/concurrent/TimeUnit;", "withReadTimeout", "withWriteTimeout", "writeTimeoutMillis", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class RealInterceptorChain implements Interceptor.Chain {
    private final Call call;
    private int calls;
    private final int connectTimeout;
    private final Exchange exchange;
    private final int index;
    private final List<Interceptor> interceptors;
    private final int readTimeout;
    private final Request request;
    private final Transmitter transmitter;
    private final int writeTimeout;

    /* JADX WARN: Multi-variable type inference failed */
    public RealInterceptorChain(List<? extends Interceptor> interceptors, Transmitter transmitter, Exchange exchange, int index, Request request, Call call, int connectTimeout, int readTimeout, int writeTimeout) {
        Intrinsics.checkParameterIsNotNull(interceptors, "interceptors");
        Intrinsics.checkParameterIsNotNull(transmitter, "transmitter");
        Intrinsics.checkParameterIsNotNull(request, "request");
        Intrinsics.checkParameterIsNotNull(call, "call");
        this.interceptors = interceptors;
        this.transmitter = transmitter;
        this.exchange = exchange;
        this.index = index;
        this.request = request;
        this.call = call;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
    }

    @Override // okhttp3.Interceptor.Chain
    public Connection connection() {
        Exchange exchange = this.exchange;
        return exchange != null ? exchange.connection() : null;
    }

    @Override // okhttp3.Interceptor.Chain
    /* renamed from: connectTimeoutMillis, reason: from getter */
    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    @Override // okhttp3.Interceptor.Chain
    public Interceptor.Chain withConnectTimeout(int timeout, TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        int millis = Util.checkDuration("timeout", timeout, unit);
        return new RealInterceptorChain(this.interceptors, this.transmitter, this.exchange, this.index, this.request, this.call, millis, this.readTimeout, this.writeTimeout);
    }

    @Override // okhttp3.Interceptor.Chain
    /* renamed from: readTimeoutMillis, reason: from getter */
    public int getReadTimeout() {
        return this.readTimeout;
    }

    @Override // okhttp3.Interceptor.Chain
    public Interceptor.Chain withReadTimeout(int timeout, TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        int millis = Util.checkDuration("timeout", timeout, unit);
        return new RealInterceptorChain(this.interceptors, this.transmitter, this.exchange, this.index, this.request, this.call, this.connectTimeout, millis, this.writeTimeout);
    }

    @Override // okhttp3.Interceptor.Chain
    /* renamed from: writeTimeoutMillis, reason: from getter */
    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    @Override // okhttp3.Interceptor.Chain
    public Interceptor.Chain withWriteTimeout(int timeout, TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        int millis = Util.checkDuration("timeout", timeout, unit);
        return new RealInterceptorChain(this.interceptors, this.transmitter, this.exchange, this.index, this.request, this.call, this.connectTimeout, this.readTimeout, millis);
    }

    /* renamed from: transmitter, reason: from getter */
    public final Transmitter getTransmitter() {
        return this.transmitter;
    }

    public final Exchange exchange() {
        Exchange exchange = this.exchange;
        if (exchange == null) {
            Intrinsics.throwNpe();
        }
        return exchange;
    }

    @Override // okhttp3.Interceptor.Chain
    /* renamed from: call, reason: from getter */
    public Call getCall() {
        return this.call;
    }

    @Override // okhttp3.Interceptor.Chain
    /* renamed from: request, reason: from getter */
    public Request getRequest() {
        return this.request;
    }

    @Override // okhttp3.Interceptor.Chain
    public Response proceed(Request request) {
        Intrinsics.checkParameterIsNotNull(request, "request");
        return proceed(request, this.transmitter, this.exchange);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Response proceed(Request request, Transmitter transmitter, Exchange exchange) throws IOException {
        boolean z;
        Intrinsics.checkParameterIsNotNull(request, "request");
        Intrinsics.checkParameterIsNotNull(transmitter, "transmitter");
        if (this.index >= this.interceptors.size()) {
            throw new AssertionError();
        }
        this.calls++;
        if (this.exchange == null) {
            z = true;
        } else {
            RealConnection realConnectionConnection = this.exchange.connection();
            if (realConnectionConnection == null) {
                Intrinsics.throwNpe();
            }
            if (!realConnectionConnection.supportsUrl(request.url())) {
                z = false;
            }
        }
        if (!z) {
            throw new IllegalStateException(("network interceptor " + this.interceptors.get(this.index - 1) + " must retain the same host and port").toString());
        }
        if (!(this.exchange == null || this.calls <= 1)) {
            throw new IllegalStateException(("network interceptor " + this.interceptors.get(this.index - 1) + " must call proceed() exactly once").toString());
        }
        RealInterceptorChain next = new RealInterceptorChain(this.interceptors, transmitter, exchange, this.index + 1, request, this.call, this.connectTimeout, this.readTimeout, this.writeTimeout);
        Interceptor interceptor = this.interceptors.get(this.index);
        Response response = interceptor.intercept(next);
        if (response == null) {
            throw new NullPointerException("interceptor " + interceptor + " returned null");
        }
        if (!(exchange == null || this.index + 1 >= this.interceptors.size() || next.calls == 1)) {
            throw new IllegalStateException(("network interceptor " + interceptor + " must call proceed() exactly once").toString());
        }
        if (response.body() != null) {
            return response;
        }
        throw new IllegalStateException(("interceptor " + interceptor + " returned a response with no body").toString());
    }
}