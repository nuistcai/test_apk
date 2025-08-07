package okhttp3.internal.http2;

import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Util.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, d2 = {"<anonymous>", "", "run", "okhttp3/internal/Util$execute$1"}, k = 3, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class Http2Connection$pushHeadersLater$$inlined$tryExecute$1 implements Runnable {
    final /* synthetic */ boolean $inFinished$inlined;
    final /* synthetic */ String $name;
    final /* synthetic */ List $requestHeaders$inlined;
    final /* synthetic */ int $streamId$inlined;
    final /* synthetic */ Http2Connection this$0;

    public Http2Connection$pushHeadersLater$$inlined$tryExecute$1(String str, Http2Connection http2Connection, int i, List list, boolean z) {
        name$iv = str;
        this = http2Connection;
        streamId = i;
        requestHeaders = list;
        inFinished = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0041 A[Catch: IOException -> 0x0039, all -> 0x0063, TryCatch #0 {IOException -> 0x0039, blocks: (B:36:0x002b, B:40:0x003d, B:42:0x0041, B:43:0x0043, B:46:0x0054, B:49:0x0057, B:50:0x0058), top: B:56:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0044  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        String name$iv = name$iv;
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
        String oldName$iv = currentThread.getName();
        currentThread.setName(name$iv);
        try {
            boolean cancel = this.pushObserver.onHeaders(streamId, requestHeaders, inFinished);
            if (cancel) {
                try {
                    this.getWriter().rstStream(streamId, ErrorCode.CANCEL);
                    if (!cancel || inFinished) {
                        synchronized (this) {
                            this.currentPushRequests.remove(Integer.valueOf(streamId));
                        }
                    }
                } catch (IOException e) {
                }
            } else if (!cancel) {
                synchronized (this) {
                }
            }
        } finally {
            currentThread.setName(oldName$iv);
        }
    }
}