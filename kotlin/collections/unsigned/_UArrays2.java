package kotlin.collections.unsigned;

import kotlin.Metadata;
import kotlin.UIntArray;
import kotlin.collections.UIterators2;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: _UArrays.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lkotlin/collections/UIntIterator;", "invoke"}, k = 3, mv = {1, 1, 15})
/* renamed from: kotlin.collections.unsigned.UArraysKt___UArraysKt$withIndex$1 */
/* loaded from: classes.dex */
final class _UArrays2 extends Lambda implements Function0<UIterators2> {
    final /* synthetic */ int[] $this_withIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    _UArrays2(int[] iArr) {
        super(0);
        withIndex = iArr;
    }

    @Override // kotlin.jvm.functions.Function0
    public final UIterators2 invoke() {
        return UIntArray.m151iteratorimpl(withIndex);
    }
}