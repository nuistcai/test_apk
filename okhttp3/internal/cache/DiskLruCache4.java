package okhttp3.internal.cache;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: DiskLruCache.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Ljava/io/IOException;", "invoke"}, k = 3, mv = {1, 1, 15})
/* renamed from: okhttp3.internal.cache.DiskLruCache$newJournalWriter$faultHidingSink$1 */
/* loaded from: classes.dex */
final class DiskLruCache4 extends Lambda implements Function1<IOException, Unit> {
    DiskLruCache4() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(IOException iOException) {
        invoke2(iOException);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke */
    public final void invoke2(IOException it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        if (Thread.holdsLock(this.this$0)) {
            this.this$0.hasJournalErrors = true;
            return;
        }
        throw new AssertionError("Assertion failed");
    }
}