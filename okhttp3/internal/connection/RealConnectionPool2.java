package okhttp3.internal.connection;

import java.io.IOException;
import kotlin.Metadata;
import okhttp3.internal.Util;

/* compiled from: RealConnectionPool.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"okhttp3/internal/connection/RealConnectionPool$cleanupRunnable$1", "Ljava/lang/Runnable;", "run", "", "okhttp"}, k = 1, mv = {1, 1, 15})
/* renamed from: okhttp3.internal.connection.RealConnectionPool$cleanupRunnable$1 */
/* loaded from: classes.dex */
public final class RealConnectionPool2 implements Runnable {
    RealConnectionPool2() {
    }

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
}