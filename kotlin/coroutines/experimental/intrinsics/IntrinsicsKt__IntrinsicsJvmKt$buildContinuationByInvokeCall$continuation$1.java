package kotlin.coroutines.experimental.intrinsics;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.Coroutines;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IntrinsicsJvm.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0015\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"kotlin/coroutines/experimental/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation$1", "Lkotlin/coroutines/experimental/Continuation;", "", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "resume", "value", "(Lkotlin/Unit;)V", "resumeWithException", "exception", "", "kotlin-stdlib-coroutines"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation$1 implements Coroutines<Unit> {
    final /* synthetic */ Function0 $block;

    public IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation$1(Function0 $captured_local_variable$1) {
        $captured_local_variable$1 = $captured_local_variable$1;
    }

    @Override // kotlin.coroutines.experimental.Coroutines
    public CoroutineContext getContext() {
        return $captured_local_variable$0.getContext();
    }

    @Override // kotlin.coroutines.experimental.Coroutines
    public void resume(Unit value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        Coroutines coroutines = $captured_local_variable$0;
        try {
            Object objInvoke = $captured_local_variable$1.invoke();
            if (objInvoke != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                if (coroutines == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                }
                coroutines.resume(objInvoke);
            }
        } catch (Throwable th) {
            coroutines.resumeWithException(th);
        }
    }

    @Override // kotlin.coroutines.experimental.Coroutines
    public void resumeWithException(Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        $captured_local_variable$0.resumeWithException(exception);
    }
}