package okhttp3;

import androidx.core.app.NotificationCompat;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.Transmitter;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;
import okio.Timeout;

/* compiled from: RealCall.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 '2\u00020\u0001:\u0002&'B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0000H\u0016J\u0010\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0006\u0010\u001d\u001a\u00020\u001cJ\b\u0010\u001e\u001a\u00020\u0007H\u0016J\b\u0010\u001f\u001a\u00020\u0007H\u0016J\u0006\u0010 \u001a\u00020!J\b\u0010\"\u001a\u00020\u0005H\u0016J\b\u0010#\u001a\u00020$H\u0016J\u0006\u0010%\u001a\u00020!R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lokhttp3/RealCall;", "Lokhttp3/Call;", "client", "Lokhttp3/OkHttpClient;", "originalRequest", "Lokhttp3/Request;", "forWebSocket", "", "(Lokhttp3/OkHttpClient;Lokhttp3/Request;Z)V", "getClient", "()Lokhttp3/OkHttpClient;", "executed", "getExecuted", "()Z", "setExecuted", "(Z)V", "getForWebSocket", "getOriginalRequest", "()Lokhttp3/Request;", "transmitter", "Lokhttp3/internal/connection/Transmitter;", "cancel", "", "clone", "enqueue", "responseCallback", "Lokhttp3/Callback;", "execute", "Lokhttp3/Response;", "getResponseWithInterceptorChain", "isCanceled", "isExecuted", "redactedUrl", "", "request", "timeout", "Lokio/Timeout;", "toLoggableString", "AsyncCall", "Companion", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class RealCall implements Call {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final OkHttpClient client;
    private boolean executed;
    private final boolean forWebSocket;
    private final Request originalRequest;
    private Transmitter transmitter;

    private RealCall(OkHttpClient client, Request originalRequest, boolean forWebSocket) {
        this.client = client;
        this.originalRequest = originalRequest;
        this.forWebSocket = forWebSocket;
    }

    public /* synthetic */ RealCall(OkHttpClient client, Request originalRequest, boolean forWebSocket, DefaultConstructorMarker $constructor_marker) {
        this(client, originalRequest, forWebSocket);
    }

    public static final /* synthetic */ Transmitter access$getTransmitter$p(RealCall $this) {
        Transmitter transmitter = $this.transmitter;
        if (transmitter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transmitter");
        }
        return transmitter;
    }

    public final OkHttpClient getClient() {
        return this.client;
    }

    public final Request getOriginalRequest() {
        return this.originalRequest;
    }

    public final boolean getForWebSocket() {
        return this.forWebSocket;
    }

    public final boolean getExecuted() {
        return this.executed;
    }

    public final void setExecuted(boolean z) {
        this.executed = z;
    }

    @Override // okhttp3.Call
    public synchronized boolean isExecuted() {
        return this.executed;
    }

    @Override // okhttp3.Call
    public boolean isCanceled() {
        Transmitter transmitter = this.transmitter;
        if (transmitter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transmitter");
        }
        return transmitter.isCanceled();
    }

    @Override // okhttp3.Call
    public Request request() {
        return this.originalRequest;
    }

    @Override // okhttp3.Call
    public Response execute() {
        synchronized (this) {
            if (!(!this.executed)) {
                throw new IllegalStateException("Already Executed".toString());
            }
            this.executed = true;
            Unit unit = Unit.INSTANCE;
        }
        Transmitter transmitter = this.transmitter;
        if (transmitter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transmitter");
        }
        transmitter.timeoutEnter();
        Transmitter transmitter2 = this.transmitter;
        if (transmitter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transmitter");
        }
        transmitter2.callStart();
        try {
            this.client.dispatcher().executed$okhttp(this);
            return getResponseWithInterceptorChain();
        } finally {
            this.client.dispatcher().finished$okhttp(this);
        }
    }

    @Override // okhttp3.Call
    public void enqueue(Callback responseCallback) {
        Intrinsics.checkParameterIsNotNull(responseCallback, "responseCallback");
        synchronized (this) {
            if (!(!this.executed)) {
                throw new IllegalStateException("Already Executed".toString());
            }
            this.executed = true;
            Unit unit = Unit.INSTANCE;
        }
        Transmitter transmitter = this.transmitter;
        if (transmitter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transmitter");
        }
        transmitter.callStart();
        this.client.dispatcher().enqueue$okhttp(new AsyncCall(this, responseCallback));
    }

    @Override // okhttp3.Call
    public void cancel() throws IOException {
        Transmitter transmitter = this.transmitter;
        if (transmitter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transmitter");
        }
        transmitter.cancel();
    }

    @Override // okhttp3.Call
    public Timeout timeout() {
        Transmitter transmitter = this.transmitter;
        if (transmitter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transmitter");
        }
        return transmitter.timeout();
    }

    @Override // okhttp3.Call
    public RealCall clone() {
        return INSTANCE.newRealCall(this.client, this.originalRequest, this.forWebSocket);
    }

    /* compiled from: RealCall.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0080\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0012\u0010\u0011\u001a\u00020\b2\n\u0010\u0012\u001a\u00060\u0000R\u00020\fJ\b\u0010\u0013\u001a\u00020\bH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lokhttp3/RealCall$AsyncCall;", "Ljava/lang/Runnable;", "responseCallback", "Lokhttp3/Callback;", "(Lokhttp3/RealCall;Lokhttp3/Callback;)V", "callsPerHost", "Ljava/util/concurrent/atomic/AtomicInteger;", "executeOn", "", "executorService", "Ljava/util/concurrent/ExecutorService;", "get", "Lokhttp3/RealCall;", "host", "", "request", "Lokhttp3/Request;", "reuseCallsPerHostFrom", "other", "run", "okhttp"}, k = 1, mv = {1, 1, 15})
    public final class AsyncCall implements Runnable {
        private volatile AtomicInteger callsPerHost;
        private final Callback responseCallback;
        final /* synthetic */ RealCall this$0;

        public AsyncCall(RealCall $outer, Callback responseCallback) {
            Intrinsics.checkParameterIsNotNull(responseCallback, "responseCallback");
            this.this$0 = $outer;
            this.responseCallback = responseCallback;
            this.callsPerHost = new AtomicInteger(0);
        }

        /* renamed from: callsPerHost, reason: from getter */
        public final AtomicInteger getCallsPerHost() {
            return this.callsPerHost;
        }

        public final void reuseCallsPerHostFrom(AsyncCall other) {
            Intrinsics.checkParameterIsNotNull(other, "other");
            this.callsPerHost = other.callsPerHost;
        }

        public final String host() {
            return this.this$0.getOriginalRequest().url().host();
        }

        public final Request request() {
            return this.this$0.getOriginalRequest();
        }

        /* renamed from: get, reason: from getter */
        public final RealCall getThis$0() {
            return this.this$0;
        }

        public final void executeOn(ExecutorService executorService) {
            Intrinsics.checkParameterIsNotNull(executorService, "executorService");
            if (!(!Thread.holdsLock(this.this$0.getClient().dispatcher()))) {
                throw new AssertionError("Assertion failed");
            }
            try {
                try {
                    executorService.execute(this);
                } catch (RejectedExecutionException e) {
                    InterruptedIOException ioException = new InterruptedIOException("executor rejected");
                    ioException.initCause(e);
                    RealCall.access$getTransmitter$p(this.this$0).noMoreExchanges(ioException);
                    this.responseCallback.onFailure(this.this$0, ioException);
                    this.this$0.getClient().dispatcher().finished$okhttp(this);
                }
            } catch (Throwable th) {
                this.this$0.getClient().dispatcher().finished$okhttp(this);
                throw th;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Dispatcher dispatcher;
            String name$iv = "OkHttp " + this.this$0.redactedUrl();
            Thread currentThread = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
            String oldName$iv = currentThread.getName();
            currentThread.setName(name$iv);
            boolean signalledCallback = false;
            try {
                RealCall.access$getTransmitter$p(this.this$0).timeoutEnter();
                try {
                    try {
                        Response response = this.this$0.getResponseWithInterceptorChain();
                        signalledCallback = true;
                        this.responseCallback.onResponse(this.this$0, response);
                        dispatcher = this.this$0.getClient().dispatcher();
                    } catch (IOException e) {
                        if (signalledCallback) {
                            Platform.INSTANCE.get().log(4, "Callback failure for " + this.this$0.toLoggableString(), e);
                        } else {
                            this.responseCallback.onFailure(this.this$0, e);
                        }
                        dispatcher = this.this$0.getClient().dispatcher();
                    }
                    dispatcher.finished$okhttp(this);
                } catch (Throwable th) {
                    this.this$0.getClient().dispatcher().finished$okhttp(this);
                    throw th;
                }
            } finally {
                currentThread.setName(oldName$iv);
            }
        }
    }

    public final String toLoggableString() {
        return (isCanceled() ? "canceled " : "") + (this.forWebSocket ? "web socket" : NotificationCompat.CATEGORY_CALL) + " to " + redactedUrl();
    }

    public final String redactedUrl() {
        return this.originalRequest.url().redact();
    }

    public final Response getResponseWithInterceptorChain() throws IOException {
        List interceptors = new ArrayList();
        CollectionsKt.addAll(interceptors, this.client.interceptors());
        interceptors.add(new RetryAndFollowUpInterceptor(this.client));
        interceptors.add(new BridgeInterceptor(this.client.cookieJar()));
        interceptors.add(new CacheInterceptor(this.client.cache()));
        interceptors.add(ConnectInterceptor.INSTANCE);
        if (!this.forWebSocket) {
            CollectionsKt.addAll(interceptors, this.client.networkInterceptors());
        }
        interceptors.add(new CallServerInterceptor(this.forWebSocket));
        Transmitter transmitter = this.transmitter;
        if (transmitter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transmitter");
        }
        RealInterceptorChain chain = new RealInterceptorChain(interceptors, transmitter, null, 0, this.originalRequest, this, this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis());
        boolean calledNoMoreExchanges = false;
        try {
            try {
                Response response = chain.proceed(this.originalRequest);
                Transmitter transmitter2 = this.transmitter;
                if (transmitter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("transmitter");
                }
                if (transmitter2.isCanceled()) {
                    Util.closeQuietly(response);
                    throw new IOException("Canceled");
                }
                Transmitter transmitter3 = this.transmitter;
                if (transmitter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("transmitter");
                }
                transmitter3.noMoreExchanges(null);
                return response;
            } catch (IOException e) {
                calledNoMoreExchanges = true;
                Transmitter transmitter4 = this.transmitter;
                if (transmitter4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("transmitter");
                }
                IOException iOExceptionNoMoreExchanges = transmitter4.noMoreExchanges(e);
                if (iOExceptionNoMoreExchanges == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Throwable");
                }
                throw iOExceptionNoMoreExchanges;
            }
        } catch (Throwable th) {
            if (!calledNoMoreExchanges) {
                Transmitter transmitter5 = this.transmitter;
                if (transmitter5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("transmitter");
                }
                transmitter5.noMoreExchanges(null);
            }
            throw th;
        }
    }

    /* compiled from: RealCall.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"Lokhttp3/RealCall$Companion;", "", "()V", "newRealCall", "Lokhttp3/RealCall;", "client", "Lokhttp3/OkHttpClient;", "originalRequest", "Lokhttp3/Request;", "forWebSocket", "", "okhttp"}, k = 1, mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final RealCall newRealCall(OkHttpClient client, Request originalRequest, boolean forWebSocket) {
            Intrinsics.checkParameterIsNotNull(client, "client");
            Intrinsics.checkParameterIsNotNull(originalRequest, "originalRequest");
            RealCall $this$apply = new RealCall(client, originalRequest, forWebSocket, null);
            $this$apply.transmitter = new Transmitter(client, $this$apply);
            return $this$apply;
        }
    }
}