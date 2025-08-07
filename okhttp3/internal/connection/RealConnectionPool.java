package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.Proxy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Transmitter;
import okhttp3.internal.platform.Platform;

/* compiled from: RealConnectionPool.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000i\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003*\u0001\n\u0018\u0000 12\u00020\u0001:\u00011B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0005J\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u0014J\u0006\u0010$\u001a\u00020\u0003J\u0006\u0010%\u001a\u00020\u001dJ\u0006\u0010&\u001a\u00020\u0003J\u0018\u0010'\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u0005H\u0002J\u000e\u0010(\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020\u0014J.\u0010)\u001a\u00020\r2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u000e\u0010.\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010/2\u0006\u00100\u001a\u00020\rR\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u00062"}, d2 = {"Lokhttp3/internal/connection/RealConnectionPool;", "", "maxIdleConnections", "", "keepAliveDuration", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "(IJLjava/util/concurrent/TimeUnit;)V", "cleanupRunnable", "okhttp3/internal/connection/RealConnectionPool$cleanupRunnable$1", "Lokhttp3/internal/connection/RealConnectionPool$cleanupRunnable$1;", "cleanupRunning", "", "getCleanupRunning", "()Z", "setCleanupRunning", "(Z)V", "connections", "Ljava/util/ArrayDeque;", "Lokhttp3/internal/connection/RealConnection;", "keepAliveDurationNs", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase", "()Lokhttp3/internal/connection/RouteDatabase;", "cleanup", "now", "connectFailed", "", "failedRoute", "Lokhttp3/Route;", "failure", "Ljava/io/IOException;", "connectionBecameIdle", "connection", "connectionCount", "evictAll", "idleConnectionCount", "pruneAndGetAllocationCount", "put", "transmitterAcquirePooledConnection", "address", "Lokhttp3/Address;", "transmitter", "Lokhttp3/internal/connection/Transmitter;", "routes", "", "requireMultiplexed", "Companion", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class RealConnectionPool {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(0, IntCompanionObject.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
    private final RealConnectionPool2 cleanupRunnable;
    private boolean cleanupRunning;
    private final ArrayDeque<RealConnection> connections;
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;
    private final RouteDatabase routeDatabase;

    /* JADX WARN: Type inference failed for: r0v2, types: [okhttp3.internal.connection.RealConnectionPool$cleanupRunnable$1] */
    public RealConnectionPool(int maxIdleConnections, long keepAliveDuration, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull(timeUnit, "timeUnit");
        this.maxIdleConnections = maxIdleConnections;
        this.keepAliveDurationNs = timeUnit.toNanos(keepAliveDuration);
        this.cleanupRunnable = new Runnable() { // from class: okhttp3.internal.connection.RealConnectionPool$cleanupRunnable$1
            @Override // java.lang.Runnable
            public void run() throws IOException {
                while (true) {
                    long waitNanos = this.this$0.cleanup(System.nanoTime());
                    if (waitNanos == -1) {
                        return;
                    }
                    try {
                        Util.lockAndWaitNanos(this.this$0, waitNanos);
                    } catch (InterruptedException e) {
                        this.this$0.evictAll();
                    }
                }
            }
        };
        this.connections = new ArrayDeque<>();
        this.routeDatabase = new RouteDatabase();
        if (!(keepAliveDuration > 0)) {
            throw new IllegalArgumentException(("keepAliveDuration <= 0: " + keepAliveDuration).toString());
        }
    }

    public final RouteDatabase getRouteDatabase() {
        return this.routeDatabase;
    }

    public final boolean getCleanupRunning() {
        return this.cleanupRunning;
    }

    public final void setCleanupRunning(boolean z) {
        this.cleanupRunning = z;
    }

    public final synchronized int idleConnectionCount() {
        int count$iv;
        Iterable $this$count$iv = this.connections;
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            count$iv = 0;
        } else {
            count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                RealConnection it = (RealConnection) element$iv;
                if (it.getTransmitters().isEmpty() && (count$iv = count$iv + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return count$iv;
    }

    public final synchronized int connectionCount() {
        return this.connections.size();
    }

    public final boolean transmitterAcquirePooledConnection(Address address, Transmitter transmitter, List<Route> routes, boolean requireMultiplexed) {
        Intrinsics.checkParameterIsNotNull(address, "address");
        Intrinsics.checkParameterIsNotNull(transmitter, "transmitter");
        if (!Thread.holdsLock(this)) {
            throw new AssertionError("Assertion failed");
        }
        Iterator<RealConnection> it = this.connections.iterator();
        while (it.hasNext()) {
            RealConnection connection = it.next();
            if (!requireMultiplexed || connection.isMultiplexed()) {
                if (connection.isEligible$okhttp(address, routes)) {
                    Intrinsics.checkExpressionValueIsNotNull(connection, "connection");
                    transmitter.acquireConnectionNoEvents(connection);
                    return true;
                }
            }
        }
        return false;
    }

    public final void put(RealConnection connection) {
        Intrinsics.checkParameterIsNotNull(connection, "connection");
        if (!Thread.holdsLock(this)) {
            throw new AssertionError("Assertion failed");
        }
        if (!this.cleanupRunning) {
            this.cleanupRunning = true;
            executor.execute(this.cleanupRunnable);
        }
        this.connections.add(connection);
    }

    public final boolean connectionBecameIdle(RealConnection connection) {
        Intrinsics.checkParameterIsNotNull(connection, "connection");
        if (!Thread.holdsLock(this)) {
            throw new AssertionError("Assertion failed");
        }
        if (connection.getNoNewExchanges() || this.maxIdleConnections == 0) {
            this.connections.remove(connection);
            return true;
        }
        notifyAll();
        return false;
    }

    public final void evictAll() {
        List evictedConnections = new ArrayList();
        synchronized (this) {
            Iterator i = this.connections.iterator();
            Intrinsics.checkExpressionValueIsNotNull(i, "connections.iterator()");
            while (i.hasNext()) {
                RealConnection connection = i.next();
                if (connection.getTransmitters().isEmpty()) {
                    connection.setNoNewExchanges(true);
                    Intrinsics.checkExpressionValueIsNotNull(connection, "connection");
                    evictedConnections.add(connection);
                    i.remove();
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        Iterator it = evictedConnections.iterator();
        while (it.hasNext()) {
            Util.closeQuietly(((RealConnection) it.next()).socket());
        }
    }

    public final long cleanup(long now) throws IOException {
        int inUseConnectionCount = 0;
        int idleConnectionCount = 0;
        RealConnection realConnection = (RealConnection) null;
        long longestIdleDurationNs = Long.MIN_VALUE;
        synchronized (this) {
            Iterator<RealConnection> it = this.connections.iterator();
            while (it.hasNext()) {
                RealConnection connection = it.next();
                Intrinsics.checkExpressionValueIsNotNull(connection, "connection");
                if (pruneAndGetAllocationCount(connection, now) > 0) {
                    inUseConnectionCount++;
                } else {
                    idleConnectionCount++;
                    long idleDurationNs = now - connection.getIdleAtNanos();
                    if (idleDurationNs > longestIdleDurationNs) {
                        longestIdleDurationNs = idleDurationNs;
                        realConnection = connection;
                    }
                }
            }
            if (longestIdleDurationNs < this.keepAliveDurationNs && idleConnectionCount <= this.maxIdleConnections) {
                if (idleConnectionCount > 0) {
                    return this.keepAliveDurationNs - longestIdleDurationNs;
                }
                if (inUseConnectionCount > 0) {
                    return this.keepAliveDurationNs;
                }
                this.cleanupRunning = false;
                return -1L;
            }
            this.connections.remove(realConnection);
            if (realConnection == null) {
                Intrinsics.throwNpe();
            }
            Util.closeQuietly(realConnection.socket());
            return 0L;
        }
    }

    private final int pruneAndGetAllocationCount(RealConnection connection, long now) {
        List references = connection.getTransmitters();
        int i = 0;
        while (i < references.size()) {
            Reference<Transmitter> reference = references.get(i);
            if (reference.get() != null) {
                i++;
            } else {
                if (reference == null) {
                    throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.connection.Transmitter.TransmitterReference");
                }
                Transmitter.TransmitterReference transmitterRef = (Transmitter.TransmitterReference) reference;
                String message = "A connection to " + connection.getRoute().address().url() + " was leaked. Did you forget to close a response body?";
                Platform.INSTANCE.get().logCloseableLeak(message, transmitterRef.getCallStackTrace());
                references.remove(i);
                connection.setNoNewExchanges(true);
                if (references.isEmpty()) {
                    connection.setIdleAtNanos$okhttp(now - this.keepAliveDurationNs);
                    return 0;
                }
            }
        }
        return references.size();
    }

    public final void connectFailed(Route failedRoute, IOException failure) {
        Intrinsics.checkParameterIsNotNull(failedRoute, "failedRoute");
        Intrinsics.checkParameterIsNotNull(failure, "failure");
        if (failedRoute.proxy().type() != Proxy.Type.DIRECT) {
            Address address = failedRoute.address();
            address.proxySelector().connectFailed(address.url().uri(), failedRoute.proxy().address(), failure);
        }
        this.routeDatabase.failed(failedRoute);
    }

    /* compiled from: RealConnectionPool.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lokhttp3/internal/connection/RealConnectionPool$Companion;", "", "()V", "executor", "Ljava/util/concurrent/ThreadPoolExecutor;", "get", "Lokhttp3/internal/connection/RealConnectionPool;", "connectionPool", "Lokhttp3/ConnectionPool;", "okhttp"}, k = 1, mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final RealConnectionPool get(ConnectionPool connectionPool) {
            Intrinsics.checkParameterIsNotNull(connectionPool, "connectionPool");
            return connectionPool.getDelegate();
        }
    }
}