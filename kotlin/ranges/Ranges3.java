package kotlin.ranges;

import java.lang.Comparable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Ranges.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\bg\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0005H\u0016J\u001d\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lkotlin/ranges/ClosedFloatingPointRange;", "T", "", "Lkotlin/ranges/ClosedRange;", "contains", "", "value", "(Ljava/lang/Comparable;)Z", "isEmpty", "lessThanOrEquals", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Z", "kotlin-stdlib"}, k = 1, mv = {1, 1, 15})
/* renamed from: kotlin.ranges.ClosedFloatingPointRange, reason: use source file name */
/* loaded from: classes.dex */
public interface Ranges3<T extends Comparable<? super T>> extends Range<T> {
    @Override // kotlin.ranges.Range
    boolean contains(T value);

    @Override // kotlin.ranges.Range
    boolean isEmpty();

    boolean lessThanOrEquals(T a, T b);

    /* compiled from: Ranges.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 15})
    /* renamed from: kotlin.ranges.ClosedFloatingPointRange$DefaultImpls */
    public static final class DefaultImpls {
        public static <T extends Comparable<? super T>> boolean contains(Ranges3<T> ranges3, T value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            return ranges3.lessThanOrEquals(ranges3.getStart(), value) && ranges3.lessThanOrEquals(value, ranges3.getEndInclusive());
        }

        public static <T extends Comparable<? super T>> boolean isEmpty(Ranges3<T> ranges3) {
            return !ranges3.lessThanOrEquals(ranges3.getStart(), ranges3.getEndInclusive());
        }
    }
}