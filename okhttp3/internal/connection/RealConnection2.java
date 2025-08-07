package okhttp3.internal.connection;

import kotlin.Metadata;
import okhttp3.internal.ws.RealWebSocket;
import okio.BufferedSink;
import okio.BufferedSource;

/* compiled from: RealConnection.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"okhttp3/internal/connection/RealConnection$newWebSocketStreams$1", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "close", "", "okhttp"}, k = 1, mv = {1, 1, 15})
/* renamed from: okhttp3.internal.connection.RealConnection$newWebSocketStreams$1 */
/* loaded from: classes.dex */
public final class RealConnection2 extends RealWebSocket.Streams {
    final /* synthetic */ BufferedSink $sink;
    final /* synthetic */ BufferedSource $source;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    RealConnection2(BufferedSource $captured_local_variable$1, BufferedSink $captured_local_variable$2, boolean $super_call_param$3, BufferedSource $super_call_param$4, BufferedSink $super_call_param$5) {
        super($super_call_param$3, $super_call_param$4, $super_call_param$5);
        source = $captured_local_variable$1;
        sink = $captured_local_variable$2;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        exchange.bodyComplete(-1L, true, true, null);
    }
}