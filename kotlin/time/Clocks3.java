package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.LongCompanionObject;

/* compiled from: Clocks.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, d2 = {"Lkotlin/time/TestClock;", "Lkotlin/time/AbstractLongClock;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(D)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, k = 1, mv = {1, 1, 15})
/* renamed from: kotlin.time.TestClock, reason: use source file name */
/* loaded from: classes.dex */
public final class Clocks3 extends Clocks2 {
    private long reading;

    public Clocks3() {
        super(TimeUnit.NANOSECONDS);
    }

    @Override // kotlin.time.Clocks2
    /* renamed from: read, reason: from getter */
    protected long getReading() {
        return this.reading;
    }

    /* renamed from: plusAssign-LRDsOJo, reason: not valid java name */
    public final void m980plusAssignLRDsOJo(double duration) {
        long newReading;
        double delta = Duration.m965toDoubleimpl(duration, getUnit());
        long longDelta = (long) delta;
        if (longDelta != Long.MIN_VALUE && longDelta != LongCompanionObject.MAX_VALUE) {
            newReading = this.reading + longDelta;
            if ((this.reading ^ longDelta) >= 0 && (this.reading ^ newReading) < 0) {
                m979overflowLRDsOJo(duration);
            }
        } else {
            double d = this.reading;
            Double.isNaN(d);
            double newReading2 = d + delta;
            if (newReading2 > LongCompanionObject.MAX_VALUE || newReading2 < Long.MIN_VALUE) {
                m979overflowLRDsOJo(duration);
            }
            newReading = (long) newReading2;
        }
        this.reading = newReading;
    }

    /* renamed from: overflow-LRDsOJo, reason: not valid java name */
    private final void m979overflowLRDsOJo(double duration) {
        throw new IllegalStateException("TestClock will overflow if its reading " + this.reading + "ns is advanced by " + Duration.m971toStringimpl(duration) + '.');
    }
}