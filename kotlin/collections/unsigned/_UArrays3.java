package kotlin.collections.unsigned;

import kotlin.Metadata;
import kotlin.ULongArray;
import kotlin.collections.UIterators3;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: _UArrays.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lkotlin/collections/ULongIterator;", "invoke"}, k = 3, mv = {1, 1, 15})
/* renamed from: kotlin.collections.unsigned.UArraysKt___UArraysKt$withIndex$2 */
/* loaded from: classes.dex */
final class _UArrays3 extends Lambda implements Function0<UIterators3> {
    final /* synthetic */ long[] $this_withIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    _UArrays3(long[] jArr) {
        super(0);
        withIndex = jArr;
    }

    @Override // kotlin.jvm.functions.Function0
    public final UIterators3 invoke() {
        return ULongArray.m220iteratorimpl(withIndex);
    }
}