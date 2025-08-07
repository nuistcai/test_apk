package kotlin.collections;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.sequences.Sequence;

/* compiled from: Sequences.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¨\u0006\u0004¸\u0006\u0000"}, d2 = {"kotlin/sequences/SequencesKt__SequencesKt$Sequence$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class SlidingWindowKt$windowedSequence$$inlined$Sequence$1<T> implements Sequence<List<? extends T>> {
    final /* synthetic */ boolean $partialWindows$inlined;
    final /* synthetic */ boolean $reuseBuffer$inlined;
    final /* synthetic */ int $size$inlined;
    final /* synthetic */ int $step$inlined;

    public SlidingWindowKt$windowedSequence$$inlined$Sequence$1(int i, int i2, boolean z, boolean z2) {
        i = i;
        i2 = i2;
        z = z;
        z2 = z2;
    }

    @Override // kotlin.sequences.Sequence
    public Iterator<List<? extends T>> iterator() {
        return SlidingWindow3.windowedIterator(windowedSequence.iterator(), i, i2, z, z2);
    }
}