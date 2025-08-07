package okhttp3.internal.cache;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import okio.Okio;

/* compiled from: DiskLruCache.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 15})
/* renamed from: okhttp3.internal.cache.DiskLruCache$cleanupRunnable$1 */
/* loaded from: classes.dex */
final class DiskLruCache3 implements Runnable {
    DiskLruCache3() {
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.this$0) {
            if (!this.this$0.initialized || this.this$0.getClosed()) {
                return;
            }
            try {
                this.this$0.trimToSize();
            } catch (IOException e) {
                this.this$0.mostRecentTrimFailed = true;
            }
            try {
            } catch (IOException e2) {
                this.this$0.mostRecentRebuildFailed = true;
                this.this$0.journalWriter = Okio.buffer(Okio.blackhole());
            }
            if (this.this$0.journalRebuildRequired()) {
                this.this$0.rebuildJournal$okhttp();
                this.this$0.redundantOpCount = 0;
                Unit unit = Unit.INSTANCE;
                return;
            }
            Unit unit2 = Unit.INSTANCE;
            return;
        }
    }
}