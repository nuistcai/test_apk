package okhttp3.internal.connection;

import androidx.core.app.NotificationCompat;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RouteSelector;
import okhttp3.internal.http.ExchangeCode;

/* compiled from: ExchangeFinder.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u001e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0010J0\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020 2\u0006\u0010$\u001a\u00020\u0010H\u0002J8\u0010%\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020 2\u0006\u0010$\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u0010H\u0002J\u0006\u0010&\u001a\u00020\u0010J\u0006\u0010\u000f\u001a\u00020\u0010J\b\u0010'\u001a\u00020\u0010H\u0002J\u0006\u0010(\u001a\u00020)R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lokhttp3/internal/connection/ExchangeFinder;", "", "transmitter", "Lokhttp3/internal/connection/Transmitter;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "address", "Lokhttp3/Address;", NotificationCompat.CATEGORY_CALL, "Lokhttp3/Call;", "eventListener", "Lokhttp3/EventListener;", "(Lokhttp3/internal/connection/Transmitter;Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Address;Lokhttp3/Call;Lokhttp3/EventListener;)V", "connectingConnection", "Lokhttp3/internal/connection/RealConnection;", "hasStreamFailure", "", "nextRouteToTry", "Lokhttp3/Route;", "routeSelection", "Lokhttp3/internal/connection/RouteSelector$Selection;", "routeSelector", "Lokhttp3/internal/connection/RouteSelector;", "find", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "chain", "Lokhttp3/Interceptor$Chain;", "doExtensiveHealthChecks", "findConnection", "connectTimeout", "", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "findHealthyConnection", "hasRouteToTry", "retryCurrentRoute", "trackFailure", "", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class ExchangeFinder {
    private final Address address;
    private final Call call;
    private RealConnection connectingConnection;
    private final RealConnectionPool connectionPool;
    private final EventListener eventListener;
    private boolean hasStreamFailure;
    private Route nextRouteToTry;
    private RouteSelector.Selection routeSelection;
    private final RouteSelector routeSelector;
    private final Transmitter transmitter;

    public ExchangeFinder(Transmitter transmitter, RealConnectionPool connectionPool, Address address, Call call, EventListener eventListener) {
        Intrinsics.checkParameterIsNotNull(transmitter, "transmitter");
        Intrinsics.checkParameterIsNotNull(connectionPool, "connectionPool");
        Intrinsics.checkParameterIsNotNull(address, "address");
        Intrinsics.checkParameterIsNotNull(call, "call");
        Intrinsics.checkParameterIsNotNull(eventListener, "eventListener");
        this.transmitter = transmitter;
        this.connectionPool = connectionPool;
        this.address = address;
        this.call = call;
        this.eventListener = eventListener;
        this.routeSelector = new RouteSelector(this.address, this.connectionPool.getRouteDatabase(), this.call, this.eventListener);
    }

    public final ExchangeCode find(OkHttpClient client, Interceptor.Chain chain, boolean doExtensiveHealthChecks) {
        Intrinsics.checkParameterIsNotNull(client, "client");
        Intrinsics.checkParameterIsNotNull(chain, "chain");
        int connectTimeout = chain.getConnectTimeout();
        int readTimeout = chain.getReadTimeout();
        int writeTimeout = chain.getWriteTimeout();
        int pingIntervalMillis = client.pingIntervalMillis();
        boolean connectionRetryEnabled = client.retryOnConnectionFailure();
        try {
            RealConnection resultConnection = findHealthyConnection(connectTimeout, readTimeout, writeTimeout, pingIntervalMillis, connectionRetryEnabled, doExtensiveHealthChecks);
            return resultConnection.newCodec$okhttp(client, chain);
        } catch (IOException e) {
            trackFailure();
            throw new RouteException(e);
        } catch (RouteException e2) {
            trackFailure();
            throw e2;
        }
    }

    private final RealConnection findHealthyConnection(int connectTimeout, int readTimeout, int writeTimeout, int pingIntervalMillis, boolean connectionRetryEnabled, boolean doExtensiveHealthChecks) throws Throwable {
        while (true) {
            RealConnection candidate = findConnection(connectTimeout, readTimeout, writeTimeout, pingIntervalMillis, connectionRetryEnabled);
            synchronized (this.connectionPool) {
                if (candidate.getSuccessCount() == 0) {
                    return candidate;
                }
                Unit unit = Unit.INSTANCE;
                if (!candidate.isHealthy(doExtensiveHealthChecks)) {
                    candidate.noNewExchanges();
                } else {
                    return candidate;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e5  */
    /* JADX WARN: Type inference failed for: r10v1, types: [T, okhttp3.internal.connection.RealConnection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final RealConnection findConnection(int connectTimeout, int readTimeout, int writeTimeout, int pingIntervalMillis, boolean connectionRetryEnabled) throws Throwable {
        Socket socketReleaseConnectionNoEvents;
        Socket toClose;
        boolean foundPooledConnection = false;
        RealConnection connection = (RealConnection) null;
        Route next = (Route) null;
        Ref.ObjectRef releasedConnection = new Ref.ObjectRef();
        synchronized (this.connectionPool) {
            if (!this.transmitter.isCanceled()) {
                this.hasStreamFailure = false;
                releasedConnection.element = this.transmitter.getConnection();
                if (this.transmitter.getConnection() != null) {
                    RealConnection connection2 = this.transmitter.getConnection();
                    if (connection2 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (connection2.getNoNewExchanges()) {
                        socketReleaseConnectionNoEvents = this.transmitter.releaseConnectionNoEvents();
                    }
                } else {
                    socketReleaseConnectionNoEvents = null;
                }
                toClose = socketReleaseConnectionNoEvents;
                if (this.transmitter.getConnection() != null) {
                    connection = this.transmitter.getConnection();
                    releasedConnection.element = null;
                }
                if (connection == null) {
                    if (this.connectionPool.transmitterAcquirePooledConnection(this.address, this.transmitter, null, false)) {
                        foundPooledConnection = true;
                        connection = this.transmitter.getConnection();
                    } else if (this.nextRouteToTry != null) {
                        next = this.nextRouteToTry;
                        this.nextRouteToTry = null;
                    } else if (retryCurrentRoute()) {
                        RealConnection connection3 = this.transmitter.getConnection();
                        if (connection3 == null) {
                            Intrinsics.throwNpe();
                        }
                        next = connection3.getRoute();
                    }
                }
                Unit unit = Unit.INSTANCE;
            } else {
                throw new IOException("Canceled");
            }
        }
        if (toClose != null) {
            Util.closeQuietly(toClose);
        }
        if (((RealConnection) releasedConnection.element) != null) {
            EventListener eventListener = this.eventListener;
            Call call = this.call;
            RealConnection realConnection = (RealConnection) releasedConnection.element;
            if (realConnection == null) {
                Intrinsics.throwNpe();
            }
            eventListener.connectionReleased(call, realConnection);
        }
        if (foundPooledConnection) {
            EventListener eventListener2 = this.eventListener;
            Call call2 = this.call;
            if (connection == null) {
                Intrinsics.throwNpe();
            }
            eventListener2.connectionAcquired(call2, connection);
        }
        if (connection != null) {
            if (connection == null) {
                Intrinsics.throwNpe();
            }
            return connection;
        }
        boolean newRouteSelection = false;
        if (next == null) {
            if (this.routeSelection != null) {
                RouteSelector.Selection selection = this.routeSelection;
                if (selection == null) {
                    Intrinsics.throwNpe();
                }
                if (!selection.hasNext()) {
                }
            } else {
                newRouteSelection = true;
                this.routeSelection = this.routeSelector.next();
            }
        }
        List<Route> routes = (List) null;
        synchronized (this.connectionPool) {
            if (this.transmitter.isCanceled()) {
                throw new IOException("Canceled");
            }
            if (newRouteSelection) {
                RouteSelector.Selection selection2 = this.routeSelection;
                if (selection2 == null) {
                    Intrinsics.throwNpe();
                }
                routes = selection2.getRoutes();
                if (this.connectionPool.transmitterAcquirePooledConnection(this.address, this.transmitter, routes, false)) {
                    foundPooledConnection = true;
                    connection = this.transmitter.getConnection();
                }
            }
            if (!foundPooledConnection) {
                if (next == null) {
                    RouteSelector.Selection selection3 = this.routeSelection;
                    if (selection3 == null) {
                        Intrinsics.throwNpe();
                    }
                    next = selection3.next();
                }
                RealConnectionPool realConnectionPool = this.connectionPool;
                if (next == null) {
                    Intrinsics.throwNpe();
                }
                connection = new RealConnection(realConnectionPool, next);
                this.connectingConnection = connection;
            }
            Unit unit2 = Unit.INSTANCE;
        }
        if (foundPooledConnection) {
            EventListener eventListener3 = this.eventListener;
            Call call3 = this.call;
            if (connection == null) {
                Intrinsics.throwNpe();
            }
            eventListener3.connectionAcquired(call3, connection);
            if (connection == null) {
                Intrinsics.throwNpe();
            }
            return connection;
        }
        if (connection == null) {
            Intrinsics.throwNpe();
        }
        connection.connect(connectTimeout, readTimeout, writeTimeout, pingIntervalMillis, connectionRetryEnabled, this.call, this.eventListener);
        RouteDatabase routeDatabase = this.connectionPool.getRouteDatabase();
        if (connection == null) {
            Intrinsics.throwNpe();
        }
        routeDatabase.connected(connection.getRoute());
        Socket socket = (Socket) null;
        synchronized (this.connectionPool) {
            this.connectingConnection = null;
            if (this.connectionPool.transmitterAcquirePooledConnection(this.address, this.transmitter, routes, true)) {
                if (connection == null) {
                    Intrinsics.throwNpe();
                }
                connection.setNoNewExchanges(true);
                if (connection == null) {
                    Intrinsics.throwNpe();
                }
                socket = connection.socket();
                connection = this.transmitter.getConnection();
                this.nextRouteToTry = next;
            } else {
                RealConnectionPool realConnectionPool2 = this.connectionPool;
                if (connection == null) {
                    Intrinsics.throwNpe();
                }
                realConnectionPool2.put(connection);
                Transmitter transmitter = this.transmitter;
                if (connection == null) {
                    Intrinsics.throwNpe();
                }
                transmitter.acquireConnectionNoEvents(connection);
            }
            Unit unit3 = Unit.INSTANCE;
        }
        if (socket != null) {
            Util.closeQuietly(socket);
        }
        EventListener eventListener4 = this.eventListener;
        Call call4 = this.call;
        if (connection == null) {
            Intrinsics.throwNpe();
        }
        eventListener4.connectionAcquired(call4, connection);
        if (connection == null) {
            Intrinsics.throwNpe();
        }
        return connection;
    }

    public final RealConnection connectingConnection() {
        if (!Thread.holdsLock(this.connectionPool)) {
            throw new AssertionError("Assertion failed");
        }
        return this.connectingConnection;
    }

    public final void trackFailure() {
        if (!(!Thread.holdsLock(this.connectionPool))) {
            throw new AssertionError("Assertion failed");
        }
        synchronized (this.connectionPool) {
            this.hasStreamFailure = true;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean hasStreamFailure() {
        boolean z;
        synchronized (this.connectionPool) {
            z = this.hasStreamFailure;
        }
        return z;
    }

    public final boolean hasRouteToTry() {
        synchronized (this.connectionPool) {
            boolean z = true;
            if (this.nextRouteToTry != null) {
                return true;
            }
            if (retryCurrentRoute()) {
                RealConnection connection = this.transmitter.getConnection();
                if (connection == null) {
                    Intrinsics.throwNpe();
                }
                this.nextRouteToTry = connection.getRoute();
                return true;
            }
            RouteSelector.Selection selection = this.routeSelection;
            if (!(selection != null ? selection.hasNext() : false) && !this.routeSelector.hasNext()) {
                z = false;
            }
            return z;
        }
    }

    private final boolean retryCurrentRoute() {
        if (this.transmitter.getConnection() != null) {
            RealConnection connection = this.transmitter.getConnection();
            if (connection == null) {
                Intrinsics.throwNpe();
            }
            if (connection.getRouteFailureCount() == 0) {
                RealConnection connection2 = this.transmitter.getConnection();
                if (connection2 == null) {
                    Intrinsics.throwNpe();
                }
                if (Util.canReuseConnectionFor(connection2.getRoute().address().url(), this.address.url())) {
                    return true;
                }
            }
        }
        return false;
    }
}