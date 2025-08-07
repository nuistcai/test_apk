package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.http2.Http2Connection;

/* compiled from: Util.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, d2 = {"<anonymous>", "", "run", "okhttp3/internal/Util$execute$1"}, k = 3, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class Http2Connection$ReaderRunnable$settings$$inlined$tryExecute$1 implements Runnable {
    final /* synthetic */ boolean $clearPrevious$inlined;
    final /* synthetic */ String $name;
    final /* synthetic */ Settings $settings$inlined;
    final /* synthetic */ Http2Connection.ReaderRunnable this$0;

    public Http2Connection$ReaderRunnable$settings$$inlined$tryExecute$1(String str, Http2Connection.ReaderRunnable readerRunnable, boolean z, Settings settings) {
        name$iv = str;
        this = readerRunnable;
        clearPrevious = z;
        settings = settings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String name$iv = name$iv;
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
        String oldName$iv = currentThread.getName();
        currentThread.setName(name$iv);
        try {
            this.applyAndAckSettings(clearPrevious, settings);
        } finally {
            currentThread.setName(oldName$iv);
        }
    }
}