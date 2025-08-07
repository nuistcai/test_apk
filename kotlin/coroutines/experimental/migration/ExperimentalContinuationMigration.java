package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result2;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.Coroutines;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CoroutinesMigration.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lkotlin/coroutines/experimental/migration/ExperimentalContinuationMigration;", "T", "Lkotlin/coroutines/experimental/Continuation;", "continuation", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/Continuation;)V", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "getContinuation", "()Lkotlin/coroutines/Continuation;", "resume", "", "value", "(Ljava/lang/Object;)V", "resumeWithException", "exception", "", "kotlin-stdlib-coroutines"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
final class ExperimentalContinuationMigration<T> implements Coroutines<T> {
    private final CoroutineContext context;
    private final Continuation<T> continuation;

    /* JADX WARN: Multi-variable type inference failed */
    public ExperimentalContinuationMigration(Continuation<? super T> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        this.continuation = continuation;
        this.context = CoroutinesMigrationKt.toExperimentalCoroutineContext(this.continuation.get$context());
    }

    public final Continuation<T> getContinuation() {
        return this.continuation;
    }

    @Override // kotlin.coroutines.experimental.Coroutines
    public CoroutineContext getContext() {
        return this.context;
    }

    @Override // kotlin.coroutines.experimental.Coroutines
    public void resume(T value) {
        Continuation<T> continuation = this.continuation;
        Result.Companion companion = Result.INSTANCE;
        continuation.resumeWith(Result.m11constructorimpl(value));
    }

    @Override // kotlin.coroutines.experimental.Coroutines
    public void resumeWithException(Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        Continuation<T> continuation = this.continuation;
        Result.Companion companion = Result.INSTANCE;
        continuation.resumeWith(Result.m11constructorimpl(Result2.createFailure(exception)));
    }
}