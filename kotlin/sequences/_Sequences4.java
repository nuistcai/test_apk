package kotlin.sequences;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: _Sequences.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "T", "it", "invoke", "(Ljava/lang/Object;)Z"}, k = 3, mv = {1, 1, 15})
/* renamed from: kotlin.sequences.SequencesKt___SequencesKt$minus$3$iterator$1 */
/* loaded from: classes.dex */
final class _Sequences4<T> extends Lambda implements Function1<T, Boolean> {
    final /* synthetic */ Collection $other;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    _Sequences4(Collection collection) {
        super(1);
        other = collection;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Boolean invoke(Object obj) {
        return Boolean.valueOf(invoke2((_Sequences4<T>) obj));
    }

    /* renamed from: invoke */
    public final boolean invoke2(T t) {
        return other.contains(t);
    }
}