package okhttp3.internal.http2;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;

/* compiled from: Util.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, d2 = {"<anonymous>", "", "run", "okhttp3/internal/Util$execute$1"}, k = 3, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class Http2Connection$pushDataLater$$inlined$execute$1 implements Runnable {
    final /* synthetic */ Buffer $buffer$inlined;
    final /* synthetic */ int $byteCount$inlined;
    final /* synthetic */ boolean $inFinished$inlined;
    final /* synthetic */ String $name;
    final /* synthetic */ int $streamId$inlined;
    final /* synthetic */ Http2Connection this$0;

    public Http2Connection$pushDataLater$$inlined$execute$1(String str, Http2Connection http2Connection, int i, Buffer buffer, int i2, boolean z) {
        name$iv = str;
        this = http2Connection;
        streamId = i;
        buffer = buffer;
        byteCount = i2;
        inFinished = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String name$iv = name$iv;
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
        String oldName$iv = currentThread.getName();
        currentThread.setName(name$iv);
        try {
            boolean cancel = this.pushObserver.onData(streamId, buffer, byteCount, inFinished);
            if (cancel) {
                this.getWriter().rstStream(streamId, ErrorCode.CANCEL);
            }
            if (cancel || inFinished) {
                synchronized (this) {
                    this.currentPushRequests.remove(Integer.valueOf(streamId));
                }
            }
        } catch (IOException e) {
        } catch (Throwable th) {
            currentThread.setName(oldName$iv);
            throw th;
        }
        currentThread.setName(oldName$iv);
    }
}