package kotlin.time;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: measureTime.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a,\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0005\u001a0\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a0\u0010\u0000\u001a\u00020\u0001*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\n\u001a4\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b"}, d2 = {"measureTime", "Lkotlin/time/Duration;", "block", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)D", "measureTimedValue", "Lkotlin/time/TimedValue;", "T", "Lkotlin/time/Clock;", "(Lkotlin/time/Clock;Lkotlin/jvm/functions/Function0;)D", "kotlin-stdlib"}, k = 2, mv = {1, 1, 15})
/* renamed from: kotlin.time.MeasureTimeKt, reason: use source file name */
/* loaded from: classes.dex */
public final class measureTime {
    public static final double measureTime(Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        Clock $this$measureTime$iv = MonoClock.INSTANCE;
        Clock4 mark$iv = $this$measureTime$iv.markNow();
        block.invoke();
        return mark$iv.elapsedNow();
    }

    public static final double measureTime(Clock measureTime, Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(measureTime, "$this$measureTime");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Clock4 mark = measureTime.markNow();
        block.invoke();
        return mark.elapsedNow();
    }

    public static final <T> TimedValue<T> measureTimedValue(Function0<? extends T> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        Clock $this$measureTimedValue$iv = MonoClock.INSTANCE;
        Clock4 mark$iv = $this$measureTimedValue$iv.markNow();
        Object result$iv = block.invoke();
        return new TimedValue<>(result$iv, mark$iv.elapsedNow(), null);
    }

    public static final <T> TimedValue<T> measureTimedValue(Clock measureTimedValue, Function0<? extends T> block) {
        Intrinsics.checkParameterIsNotNull(measureTimedValue, "$this$measureTimedValue");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Clock4 mark = measureTimedValue.markNow();
        Object result = block.invoke();
        return new TimedValue<>(result, mark.elapsedNow(), null);
    }
}