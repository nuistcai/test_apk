package kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import okhttp3.internal.ws.WebSocketProtocol;

/* compiled from: UShort.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001fB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u0010J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0013J\u001b\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u0013\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003J\t\u0010$\u001a\u00020\rHÖ\u0001J\u0013\u0010%\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b&\u0010\u0005J\u0013\u0010'\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0005J\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b*\u0010\u0010J\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b+\u0010\u0013J\u001b\u0010)\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b,\u0010\u001fJ\u001b\u0010)\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b-\u0010\u0018J\u001b\u0010.\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b/\u0010\u000bJ\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b1\u0010\u0010J\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b2\u0010\u0013J\u001b\u00100\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u001fJ\u001b\u00100\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u0018J\u001b\u00105\u001a\u0002062\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b:\u0010\u0010J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b;\u0010\u0013J\u001b\u00109\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b<\u0010\u001fJ\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b=\u0010\u0018J\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b?\u0010\u0010J\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u0013J\u001b\u0010>\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u001fJ\u001b\u0010>\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u0018J\u0010\u0010C\u001a\u00020DH\u0087\b¢\u0006\u0004\bE\u0010FJ\u0010\u0010G\u001a\u00020HH\u0087\b¢\u0006\u0004\bI\u0010JJ\u0010\u0010K\u001a\u00020LH\u0087\b¢\u0006\u0004\bM\u0010NJ\u0010\u0010O\u001a\u00020\rH\u0087\b¢\u0006\u0004\bP\u0010QJ\u0010\u0010R\u001a\u00020SH\u0087\b¢\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bW\u0010\u0005J\u000f\u0010X\u001a\u00020YH\u0016¢\u0006\u0004\bZ\u0010[J\u0013\u0010\\\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b]\u0010FJ\u0013\u0010^\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b_\u0010QJ\u0013\u0010`\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\ba\u0010UJ\u0013\u0010b\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\bc\u0010\u0005J\u001b\u0010d\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\be\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006g"}, d2 = {"Lkotlin/UShort;", "", "data", "", "constructor-impl", "(S)S", "data$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-impl", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "hashCode", "inc", "inc-impl", "inv", "inv-impl", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "(S)I", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-impl", "toUInt", "toUInt-impl", "toULong", "toULong-impl", "toUShort", "toUShort-impl", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class UShort implements Comparable<UShort> {
    public static final short MAX_VALUE = -1;
    public static final short MIN_VALUE = 0;
    public static final int SIZE_BITS = 16;
    public static final int SIZE_BYTES = 2;
    private final short data;

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UShort m256boximpl(short s) {
        return new UShort(s);
    }

    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private int m260compareToxj2QHRw(short s) {
        return m261compareToxj2QHRw(this.data, s);
    }

    public static /* synthetic */ void data$annotations() {
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m268equalsimpl(short s, Object obj) {
        return (obj instanceof UShort) && s == ((UShort) obj).getData();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m269equalsimpl0(short s, short s2) {
        throw null;
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m270hashCodeimpl(short s) {
        return s;
    }

    public boolean equals(Object other) {
        return m268equalsimpl(this.data, other);
    }

    public int hashCode() {
        return m270hashCodeimpl(this.data);
    }

    public String toString() {
        return m297toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ short getData() {
        return this.data;
    }

    private /* synthetic */ UShort(short data) {
        this.data = data;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static short m262constructorimpl(short data) {
        return data;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UShort uShort) {
        return m260compareToxj2QHRw(uShort.getData());
    }

    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static final int m257compareTo7apg3OU(short $this, byte other) {
        return Intrinsics.compare(65535 & $this, other & UByte.MAX_VALUE);
    }

    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static int m261compareToxj2QHRw(short $this, short other) {
        return Intrinsics.compare($this & MAX_VALUE, 65535 & other);
    }

    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static final int m259compareToWZ4Q5Ns(short $this, int other) {
        return UnsignedUtils.uintCompare(UInt.m96constructorimpl(65535 & $this), other);
    }

    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m258compareToVKZWuLQ(short $this, long other) {
        return UnsignedUtils.ulongCompare(ULong.m165constructorimpl($this & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m278plus7apg3OU(short $this, byte other) {
        return UInt.m96constructorimpl(UInt.m96constructorimpl(65535 & $this) + UInt.m96constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m281plusxj2QHRw(short $this, short other) {
        return UInt.m96constructorimpl(UInt.m96constructorimpl($this & MAX_VALUE) + UInt.m96constructorimpl(65535 & other));
    }

    /* renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m280plusWZ4Q5Ns(short $this, int other) {
        return UInt.m96constructorimpl(UInt.m96constructorimpl(65535 & $this) + other);
    }

    /* renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m279plusVKZWuLQ(short $this, long other) {
        return ULong.m165constructorimpl(ULong.m165constructorimpl($this & WebSocketProtocol.PAYLOAD_SHORT_MAX) + other);
    }

    /* renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m273minus7apg3OU(short $this, byte other) {
        return UInt.m96constructorimpl(UInt.m96constructorimpl(65535 & $this) - UInt.m96constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m276minusxj2QHRw(short $this, short other) {
        return UInt.m96constructorimpl(UInt.m96constructorimpl($this & MAX_VALUE) - UInt.m96constructorimpl(65535 & other));
    }

    /* renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m275minusWZ4Q5Ns(short $this, int other) {
        return UInt.m96constructorimpl(UInt.m96constructorimpl(65535 & $this) - other);
    }

    /* renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m274minusVKZWuLQ(short $this, long other) {
        return ULong.m165constructorimpl(ULong.m165constructorimpl($this & WebSocketProtocol.PAYLOAD_SHORT_MAX) - other);
    }

    /* renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m287times7apg3OU(short $this, byte other) {
        return UInt.m96constructorimpl(UInt.m96constructorimpl(65535 & $this) * UInt.m96constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m290timesxj2QHRw(short $this, short other) {
        return UInt.m96constructorimpl(UInt.m96constructorimpl($this & MAX_VALUE) * UInt.m96constructorimpl(65535 & other));
    }

    /* renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m289timesWZ4Q5Ns(short $this, int other) {
        return UInt.m96constructorimpl(UInt.m96constructorimpl(65535 & $this) * other);
    }

    /* renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m288timesVKZWuLQ(short $this, long other) {
        return ULong.m165constructorimpl(ULong.m165constructorimpl($this & WebSocketProtocol.PAYLOAD_SHORT_MAX) * other);
    }

    /* renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m264div7apg3OU(short $this, byte other) {
        return UnsignedUtils.m322uintDivideJ1ME1BU(UInt.m96constructorimpl(65535 & $this), UInt.m96constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m267divxj2QHRw(short $this, short other) {
        return UnsignedUtils.m322uintDivideJ1ME1BU(UInt.m96constructorimpl($this & MAX_VALUE), UInt.m96constructorimpl(65535 & other));
    }

    /* renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m266divWZ4Q5Ns(short $this, int other) {
        return UnsignedUtils.m322uintDivideJ1ME1BU(UInt.m96constructorimpl(65535 & $this), other);
    }

    /* renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m265divVKZWuLQ(short $this, long other) {
        return UnsignedUtils.m324ulongDivideeb3DHEI(ULong.m165constructorimpl($this & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m283rem7apg3OU(short $this, byte other) {
        return UnsignedUtils.m323uintRemainderJ1ME1BU(UInt.m96constructorimpl(65535 & $this), UInt.m96constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m286remxj2QHRw(short $this, short other) {
        return UnsignedUtils.m323uintRemainderJ1ME1BU(UInt.m96constructorimpl($this & MAX_VALUE), UInt.m96constructorimpl(65535 & other));
    }

    /* renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m285remWZ4Q5Ns(short $this, int other) {
        return UnsignedUtils.m323uintRemainderJ1ME1BU(UInt.m96constructorimpl(65535 & $this), other);
    }

    /* renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m284remVKZWuLQ(short $this, long other) {
        return UnsignedUtils.m325ulongRemaindereb3DHEI(ULong.m165constructorimpl($this & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: inc-impl, reason: not valid java name */
    private static final short m271incimpl(short $this) {
        return m262constructorimpl((short) ($this + 1));
    }

    /* renamed from: dec-impl, reason: not valid java name */
    private static final short m263decimpl(short $this) {
        return m262constructorimpl((short) ($this - 1));
    }

    /* renamed from: rangeTo-xj2QHRw, reason: not valid java name */
    private static final UIntRange m282rangeToxj2QHRw(short $this, short other) {
        return new UIntRange(UInt.m96constructorimpl($this & MAX_VALUE), UInt.m96constructorimpl(65535 & other), null);
    }

    /* renamed from: and-xj2QHRw, reason: not valid java name */
    private static final short m255andxj2QHRw(short $this, short other) {
        return m262constructorimpl((short) ($this & other));
    }

    /* renamed from: or-xj2QHRw, reason: not valid java name */
    private static final short m277orxj2QHRw(short $this, short other) {
        return m262constructorimpl((short) ($this | other));
    }

    /* renamed from: xor-xj2QHRw, reason: not valid java name */
    private static final short m302xorxj2QHRw(short $this, short other) {
        return m262constructorimpl((short) ($this ^ other));
    }

    /* renamed from: inv-impl, reason: not valid java name */
    private static final short m272invimpl(short $this) {
        return m262constructorimpl((short) ($this ^ (-1)));
    }

    /* renamed from: toByte-impl, reason: not valid java name */
    private static final byte m291toByteimpl(short $this) {
        return (byte) $this;
    }

    /* renamed from: toShort-impl, reason: not valid java name */
    private static final short m296toShortimpl(short $this) {
        return $this;
    }

    /* renamed from: toInt-impl, reason: not valid java name */
    private static final int m294toIntimpl(short $this) {
        return 65535 & $this;
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    private static final long m295toLongimpl(short $this) {
        return $this & WebSocketProtocol.PAYLOAD_SHORT_MAX;
    }

    /* renamed from: toUByte-impl, reason: not valid java name */
    private static final byte m298toUByteimpl(short $this) {
        return UByte.m29constructorimpl((byte) $this);
    }

    /* renamed from: toUShort-impl, reason: not valid java name */
    private static final short m301toUShortimpl(short $this) {
        return $this;
    }

    /* renamed from: toUInt-impl, reason: not valid java name */
    private static final int m299toUIntimpl(short $this) {
        return UInt.m96constructorimpl(65535 & $this);
    }

    /* renamed from: toULong-impl, reason: not valid java name */
    private static final long m300toULongimpl(short $this) {
        return ULong.m165constructorimpl($this & WebSocketProtocol.PAYLOAD_SHORT_MAX);
    }

    /* renamed from: toFloat-impl, reason: not valid java name */
    private static final float m293toFloatimpl(short $this) {
        return 65535 & $this;
    }

    /* renamed from: toDouble-impl, reason: not valid java name */
    private static final double m292toDoubleimpl(short $this) {
        return 65535 & $this;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m297toStringimpl(short $this) {
        return String.valueOf(65535 & $this);
    }
}