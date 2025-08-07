package okhttp3.internal.http2;

import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.platform.Platform;

/* compiled from: Util.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0004"}, d2 = {"<anonymous>", "", "run", "okhttp3/internal/Util$execute$1", "okhttp3/internal/http2/Http2Connection$ReaderRunnable$$special$$inlined$execute$1"}, k = 3, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class Http2Connection$ReaderRunnable$headers$$inlined$synchronized$lambda$1 implements Runnable {
    final /* synthetic */ List $headerBlock$inlined;
    final /* synthetic */ boolean $inFinished$inlined;
    final /* synthetic */ String $name;
    final /* synthetic */ Http2Stream $newStream$inlined;
    final /* synthetic */ Http2Stream $stream$inlined;
    final /* synthetic */ int $streamId$inlined;
    final /* synthetic */ Http2Connection.ReaderRunnable this$0;

    public Http2Connection$ReaderRunnable$headers$$inlined$synchronized$lambda$1(String str, Http2Stream http2Stream, Http2Connection.ReaderRunnable readerRunnable, Http2Stream http2Stream2, int i, List list, boolean z) {
        str = str;
        http2Stream = http2Stream;
        readerRunnable = readerRunnable;
        http2Stream = http2Stream2;
        i = i;
        list = list;
        z = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String name$iv = str;
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
        String oldName$iv = currentThread.getName();
        currentThread.setName(name$iv);
        try {
            try {
                readerRunnable.this$0.getListener().onStream(http2Stream);
            } catch (IOException e) {
                Platform.INSTANCE.get().log(4, "Http2Connection.Listener failure for " + readerRunnable.this$0.getConnectionName(), e);
                try {
                    http2Stream.close(ErrorCode.PROTOCOL_ERROR, e);
                } catch (IOException e2) {
                }
            }
        } finally {
            currentThread.setName(oldName$iv);
        }
    }
}