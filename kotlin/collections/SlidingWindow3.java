package kotlin.collections;

import androidx.core.view.MotionEventCompat;
import com.alibaba.fastjson.asm.Opcodes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result2;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl5;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceBuilder6;
import kotlin.sequences.SequencesKt;

/* compiled from: SlidingWindow.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000\u001aH\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u001aD\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u000e\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000¨\u0006\u000f"}, d2 = {"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"}, k = 2, mv = {1, 1, 15})
/* renamed from: kotlin.collections.SlidingWindowKt, reason: use source file name */
/* loaded from: classes.dex */
public final class SlidingWindow3 {
    public static final void checkWindowSizeStep(int size, int step) {
        if (!(size > 0 && step > 0)) {
            throw new IllegalArgumentException((size != step ? "Both size " + size + " and step " + step + " must be greater than zero." : "size " + size + " must be greater than zero.").toString());
        }
    }

    public static final <T> Sequence<List<T>> windowedSequence(final Sequence<? extends T> windowedSequence, final int i, final int i2, final boolean z, final boolean z2) {
        Intrinsics.checkParameterIsNotNull(windowedSequence, "$this$windowedSequence");
        checkWindowSizeStep(i, i2);
        return new Sequence<List<? extends T>>() { // from class: kotlin.collections.SlidingWindowKt$windowedSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public Iterator<List<? extends T>> iterator() {
                return SlidingWindow3.windowedIterator(windowedSequence.iterator(), i, i2, z, z2);
            }
        };
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* compiled from: SlidingWindow.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 15})
    @DebugMetadata(c = "kotlin.collections.SlidingWindowKt$windowedIterator$1", f = "SlidingWindow.kt", i = {0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4}, l = {MotionEventCompat.AXIS_GENERIC_2, MotionEventCompat.AXIS_GENERIC_8, MotionEventCompat.AXIS_GENERIC_15, 52, Opcodes.LSTORE}, m = "invokeSuspend", n = {"$this$iterator", "gap", "buffer", "skip", "e", "$this$iterator", "gap", "buffer", "skip", "$this$iterator", "gap", "buffer", "e", "$this$iterator", "gap", "buffer", "$this$iterator", "gap", "buffer"}, s = {"L$0", "I$0", "L$1", "I$1", "L$2", "L$0", "I$0", "L$1", "I$1", "L$0", "I$0", "L$1", "L$2", "L$0", "I$0", "L$1", "L$0", "I$0", "L$1"})
    /* renamed from: kotlin.collections.SlidingWindowKt$windowedIterator$1, reason: invalid class name */
    static final class AnonymousClass1<T> extends ContinuationImpl5 implements Function2<SequenceBuilder6<? super List<? extends T>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Iterator $iterator;
        final /* synthetic */ boolean $partialWindows;
        final /* synthetic */ boolean $reuseBuffer;
        final /* synthetic */ int $size;
        final /* synthetic */ int $step;
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        private SequenceBuilder6 p$;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(int i, int i2, Iterator it, boolean z, boolean z2, Continuation continuation) {
            super(2, continuation);
            this.$step = i;
            this.$size = i2;
            this.$iterator = it;
            this.$reuseBuffer = z;
            this.$partialWindows = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.ContinuationImpl2
        public final Continuation<Unit> create(Object obj, Continuation<?> completion) {
            Intrinsics.checkParameterIsNotNull(completion, "completion");
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$step, this.$size, this.$iterator, this.$reuseBuffer, this.$partialWindows, completion);
            anonymousClass1.p$ = (SequenceBuilder6) obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(obj, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:16:0x00b2  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00e8  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00ec  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x0124 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0125  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x013d  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x017a  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0186  */
        /* JADX WARN: Removed duplicated region for block: B:69:0x01ae  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x01cb  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x00db -> B:25:0x00e4). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:52:0x016d -> B:54:0x0170). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:66:0x01a5 -> B:68:0x01a8). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.ContinuationImpl2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            SequenceBuilder6 sequenceBuilder6;
            int i;
            Iterator it;
            int i2;
            SlidingWindow2 slidingWindow2;
            AnonymousClass1<T> anonymousClass1;
            ArrayList arrayList;
            int i3;
            Iterator it2;
            AnonymousClass1<T> anonymousClass12;
            Object obj2;
            SequenceBuilder6 sequenceBuilder62;
            SequenceBuilder6 sequenceBuilder63;
            int i4;
            ArrayList arrayList2;
            Iterator it3;
            AnonymousClass1<T> anonymousClass13;
            Object obj3;
            SequenceBuilder6 sequenceBuilder64;
            SlidingWindow2 slidingWindow22;
            SlidingWindow2 slidingWindow23;
            SlidingWindow2 slidingWindow24;
            SequenceBuilder6 sequenceBuilder65;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    Result2.throwOnFailure(obj);
                    sequenceBuilder6 = this.p$;
                    i = this.$step - this.$size;
                    if (i >= 0) {
                        arrayList = new ArrayList(this.$size);
                        i3 = 0;
                        it2 = this.$iterator;
                        anonymousClass12 = this;
                        obj2 = coroutine_suspended;
                        sequenceBuilder62 = sequenceBuilder6;
                        while (it2.hasNext()) {
                            Object next = it2.next();
                            if (i3 > 0) {
                                i3--;
                            } else {
                                arrayList.add(next);
                                if (arrayList.size() == anonymousClass12.$size) {
                                    anonymousClass12.L$0 = sequenceBuilder62;
                                    anonymousClass12.I$0 = i;
                                    anonymousClass12.L$1 = arrayList;
                                    anonymousClass12.I$1 = i3;
                                    anonymousClass12.L$2 = next;
                                    anonymousClass12.L$3 = it2;
                                    anonymousClass12.label = 1;
                                    if (sequenceBuilder62.yield(arrayList, anonymousClass12) == obj2) {
                                        return obj2;
                                    }
                                    Object obj4 = obj2;
                                    sequenceBuilder63 = sequenceBuilder62;
                                    coroutine_suspended = obj4;
                                    Iterator it4 = it2;
                                    i4 = i;
                                    arrayList2 = arrayList;
                                    it3 = it4;
                                    if (anonymousClass12.$reuseBuffer) {
                                        arrayList2 = new ArrayList(anonymousClass12.$size);
                                    } else {
                                        arrayList2.clear();
                                    }
                                    i3 = i4;
                                    arrayList = arrayList2;
                                    i = i4;
                                    it2 = it3;
                                    SequenceBuilder6 sequenceBuilder66 = sequenceBuilder63;
                                    obj2 = coroutine_suspended;
                                    sequenceBuilder62 = sequenceBuilder66;
                                    while (it2.hasNext()) {
                                    }
                                }
                            }
                        }
                        if ((true ^ arrayList.isEmpty()) && (anonymousClass12.$partialWindows || arrayList.size() == anonymousClass12.$size)) {
                            anonymousClass12.L$0 = sequenceBuilder62;
                            anonymousClass12.I$0 = i;
                            anonymousClass12.L$1 = arrayList;
                            anonymousClass12.I$1 = i3;
                            anonymousClass12.label = 2;
                            if (sequenceBuilder62.yield(arrayList, anonymousClass12) != obj2) {
                                return obj2;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                    SlidingWindow2 slidingWindow25 = new SlidingWindow2(this.$size);
                    it = this.$iterator;
                    i2 = i;
                    slidingWindow2 = slidingWindow25;
                    anonymousClass1 = this;
                    while (it.hasNext()) {
                        Object next2 = it.next();
                        slidingWindow2.add((SlidingWindow2) next2);
                        if (slidingWindow2.isFull()) {
                            List arrayList3 = anonymousClass1.$reuseBuffer ? slidingWindow2 : new ArrayList(slidingWindow2);
                            anonymousClass1.L$0 = sequenceBuilder6;
                            anonymousClass1.I$0 = i2;
                            anonymousClass1.L$1 = slidingWindow2;
                            anonymousClass1.L$2 = next2;
                            anonymousClass1.L$3 = it;
                            anonymousClass1.label = 3;
                            slidingWindow22 = slidingWindow2;
                            if (sequenceBuilder6.yield(arrayList3, anonymousClass1) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            slidingWindow22.removeFirst(anonymousClass1.$step);
                            slidingWindow2 = slidingWindow22;
                            while (it.hasNext()) {
                            }
                        }
                    }
                    if (anonymousClass1.$partialWindows) {
                        return Unit.INSTANCE;
                    }
                    anonymousClass13 = anonymousClass1;
                    SequenceBuilder6 sequenceBuilder67 = sequenceBuilder6;
                    obj3 = coroutine_suspended;
                    sequenceBuilder64 = sequenceBuilder67;
                    slidingWindow23 = slidingWindow2;
                    if (slidingWindow23.size() <= anonymousClass13.$step) {
                        List arrayList4 = anonymousClass13.$reuseBuffer ? slidingWindow23 : new ArrayList(slidingWindow23);
                        anonymousClass13.L$0 = sequenceBuilder64;
                        anonymousClass13.I$0 = i2;
                        anonymousClass13.L$1 = slidingWindow23;
                        anonymousClass13.label = 4;
                        Object objYield = sequenceBuilder64.yield(arrayList4, anonymousClass13);
                        sequenceBuilder65 = sequenceBuilder64;
                        slidingWindow24 = slidingWindow23;
                        if (objYield == obj3) {
                            return obj3;
                        }
                        slidingWindow24.removeFirst(anonymousClass13.$step);
                        sequenceBuilder64 = sequenceBuilder65;
                        slidingWindow23 = slidingWindow24;
                        if (slidingWindow23.size() <= anonymousClass13.$step) {
                            if (true ^ slidingWindow23.isEmpty()) {
                                anonymousClass13.L$0 = sequenceBuilder64;
                                anonymousClass13.I$0 = i2;
                                anonymousClass13.L$1 = slidingWindow23;
                                anonymousClass13.label = 5;
                                if (sequenceBuilder64.yield(slidingWindow23, anonymousClass13) == obj3) {
                                    return obj3;
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    break;
                case 1:
                    Iterator it5 = (Iterator) this.L$3;
                    Object obj5 = this.L$2;
                    int i5 = this.I$1;
                    arrayList2 = (ArrayList) this.L$1;
                    int i6 = this.I$0;
                    sequenceBuilder63 = (SequenceBuilder6) this.L$0;
                    Result2.throwOnFailure(obj);
                    it3 = it5;
                    i4 = i6;
                    anonymousClass12 = this;
                    if (anonymousClass12.$reuseBuffer) {
                    }
                    i3 = i4;
                    arrayList = arrayList2;
                    i = i4;
                    it2 = it3;
                    SequenceBuilder6 sequenceBuilder662 = sequenceBuilder63;
                    obj2 = coroutine_suspended;
                    sequenceBuilder62 = sequenceBuilder662;
                    while (it2.hasNext()) {
                    }
                    if (true ^ arrayList.isEmpty()) {
                        anonymousClass12.L$0 = sequenceBuilder62;
                        anonymousClass12.I$0 = i;
                        anonymousClass12.L$1 = arrayList;
                        anonymousClass12.I$1 = i3;
                        anonymousClass12.label = 2;
                        if (sequenceBuilder62.yield(arrayList, anonymousClass12) != obj2) {
                        }
                        break;
                    }
                    return Unit.INSTANCE;
                case 2:
                    int i7 = this.I$1;
                    int i8 = this.I$0;
                    Result2.throwOnFailure(obj);
                    return Unit.INSTANCE;
                case 3:
                    it = (Iterator) this.L$3;
                    Object obj6 = this.L$2;
                    SlidingWindow2 slidingWindow26 = (SlidingWindow2) this.L$1;
                    i2 = this.I$0;
                    sequenceBuilder6 = (SequenceBuilder6) this.L$0;
                    Result2.throwOnFailure(obj);
                    anonymousClass1 = this;
                    slidingWindow22 = slidingWindow26;
                    slidingWindow22.removeFirst(anonymousClass1.$step);
                    slidingWindow2 = slidingWindow22;
                    while (it.hasNext()) {
                    }
                    if (anonymousClass1.$partialWindows) {
                    }
                    break;
                case 4:
                    SlidingWindow2 slidingWindow27 = (SlidingWindow2) this.L$1;
                    int i9 = this.I$0;
                    SequenceBuilder6 sequenceBuilder68 = (SequenceBuilder6) this.L$0;
                    Result2.throwOnFailure(obj);
                    anonymousClass13 = this;
                    i2 = i9;
                    obj3 = coroutine_suspended;
                    sequenceBuilder65 = sequenceBuilder68;
                    slidingWindow24 = slidingWindow27;
                    slidingWindow24.removeFirst(anonymousClass13.$step);
                    sequenceBuilder64 = sequenceBuilder65;
                    slidingWindow23 = slidingWindow24;
                    if (slidingWindow23.size() <= anonymousClass13.$step) {
                    }
                    break;
                case 5:
                    int i10 = this.I$0;
                    Result2.throwOnFailure(obj);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    public static final <T> Iterator<List<T>> windowedIterator(Iterator<? extends T> iterator, int size, int step, boolean partialWindows, boolean reuseBuffer) {
        Intrinsics.checkParameterIsNotNull(iterator, "iterator");
        return !iterator.hasNext() ? Collections3.INSTANCE : SequencesKt.iterator(new AnonymousClass1(step, size, iterator, reuseBuffer, partialWindows, null));
    }
}