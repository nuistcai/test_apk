package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SegmentedByteString.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u0005\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 J2\u00020\u0001:\u0001JB\u001d\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000eH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\u0015\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0011H\u0010¢\u0006\u0002\b\u0015J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0096\u0002JV\u0010\u001a\u001a\u00020\u001b2K\u0010\u001c\u001aG\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b( \u0012\u0013\u0012\u00110!¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b(\"\u0012\u0013\u0012\u00110!¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u001b0\u001dH\u0082\bJf\u0010\u001a\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020!2K\u0010\u001c\u001aG\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b( \u0012\u0013\u0012\u00110!¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b(\"\u0012\u0013\u0012\u00110!¢\u0006\f\b\u001e\u0012\b\b\u001f\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u001b0\u001dH\u0082\bJ\r\u0010&\u001a\u00020!H\u0010¢\u0006\u0002\b'J\b\u0010(\u001a\u00020!H\u0016J\b\u0010)\u001a\u00020\u0011H\u0016J\u001d\u0010*\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010+\u001a\u00020\u0001H\u0010¢\u0006\u0002\b,J\u0018\u0010-\u001a\u00020!2\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010.\u001a\u00020!H\u0016J\r\u0010/\u001a\u00020\u0004H\u0010¢\u0006\u0002\b0J\u0015\u00101\u001a\u0002022\u0006\u00103\u001a\u00020!H\u0010¢\u0006\u0002\b4J\u0018\u00105\u001a\u00020!2\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010.\u001a\u00020!H\u0016J(\u00106\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020!2\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u00107\u001a\u00020!2\u0006\u0010#\u001a\u00020!H\u0016J(\u00106\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020!2\u0006\u0010\u0018\u001a\u00020\u00012\u0006\u00107\u001a\u00020!2\u0006\u0010#\u001a\u00020!H\u0016J\u0010\u00108\u001a\u00020!2\u0006\u00103\u001a\u00020!H\u0002J\u0010\u00109\u001a\u00020\u00112\u0006\u0010:\u001a\u00020;H\u0016J\u0018\u0010<\u001a\u00020\u00012\u0006\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020!H\u0016J\b\u0010=\u001a\u00020\u0001H\u0016J\b\u0010>\u001a\u00020\u0001H\u0016J\b\u0010?\u001a\u00020\u0004H\u0016J\b\u0010@\u001a\u00020\u0001H\u0002J\b\u0010A\u001a\u00020\u0011H\u0016J\u0010\u0010B\u001a\u00020\u001b2\u0006\u0010C\u001a\u00020DH\u0016J\u0015\u0010B\u001a\u00020\u001b2\u0006\u0010E\u001a\u00020FH\u0010¢\u0006\u0002\bGJ\b\u0010H\u001a\u00020IH\u0002R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006K"}, d2 = {"Lokio/SegmentedByteString;", "Lokio/ByteString;", "segments", "", "", "directory", "", "([[B[I)V", "getDirectory", "()[I", "getSegments", "()[[B", "[[B", "asByteBuffer", "Ljava/nio/ByteBuffer;", "kotlin.jvm.PlatformType", "base64", "", "base64Url", "digest", "algorithm", "digest$jvm", "equals", "", "other", "", "forEachSegment", "", "action", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "data", "", "offset", "byteCount", "beginIndex", "endIndex", "getSize", "getSize$jvm", "hashCode", "hex", "hmac", "key", "hmac$jvm", "indexOf", "fromIndex", "internalArray", "internalArray$jvm", "internalGet", "", "pos", "internalGet$jvm", "lastIndexOf", "rangeEquals", "otherOffset", "segment", "string", "charset", "Ljava/nio/charset/Charset;", "substring", "toAsciiLowercase", "toAsciiUppercase", "toByteArray", "toByteString", "toString", "write", "out", "Ljava/io/OutputStream;", "buffer", "Lokio/Buffer;", "write$jvm", "writeReplace", "Ljava/lang/Object;", "Companion", "jvm"}, k = 1, mv = {1, 1, 11})
/* loaded from: classes.dex */
public final class SegmentedByteString extends ByteString {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final transient int[] directory;
    private final transient byte[][] segments;

    private SegmentedByteString(byte[][] segments, int[] directory) {
        super(ByteString.EMPTY.getData());
        this.segments = segments;
        this.directory = directory;
    }

    public /* synthetic */ SegmentedByteString(byte[][] segments, int[] directory, DefaultConstructorMarker $constructor_marker) {
        this(segments, directory);
    }

    public final byte[][] getSegments() {
        return this.segments;
    }

    public final int[] getDirectory() {
        return this.directory;
    }

    /* compiled from: SegmentedByteString.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lokio/SegmentedByteString$Companion;", "", "()V", "of", "Lokio/ByteString;", "buffer", "Lokio/Buffer;", "byteCount", "", "jvm"}, k = 1, mv = {1, 1, 11})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final ByteString of(Buffer buffer, int byteCount) {
            Intrinsics.checkParameterIsNotNull(buffer, "buffer");
            Util.checkOffsetAndCount(buffer.size(), 0L, byteCount);
            int offset = 0;
            int segmentCount = 0;
            Segment s = buffer.head;
            while (offset < byteCount) {
                if (s == null) {
                    Intrinsics.throwNpe();
                }
                if (s.limit == s.pos) {
                    throw new AssertionError("s.limit == s.pos");
                }
                offset += s.limit - s.pos;
                segmentCount++;
                s = s.next;
            }
            byte[][] segments = new byte[segmentCount][];
            int[] directory = new int[segmentCount * 2];
            int offset2 = 0;
            int segmentCount2 = 0;
            Segment s2 = buffer.head;
            while (offset2 < byteCount) {
                if (s2 == null) {
                    Intrinsics.throwNpe();
                }
                segments[segmentCount2] = s2.data;
                offset2 += s2.limit - s2.pos;
                directory[segmentCount2] = Math.min(offset2, byteCount);
                directory[segments.length + segmentCount2] = s2.pos;
                s2.shared = true;
                segmentCount2++;
                s2 = s2.next;
            }
            return new SegmentedByteString(segments, directory, null);
        }
    }

    @Override // okio.ByteString
    public String string(Charset charset) {
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        return toByteString().string(charset);
    }

    @Override // okio.ByteString
    public String base64() {
        return toByteString().base64();
    }

    @Override // okio.ByteString
    public String hex() {
        return toByteString().hex();
    }

    @Override // okio.ByteString
    public ByteString toAsciiLowercase() {
        return toByteString().toAsciiLowercase();
    }

    @Override // okio.ByteString
    public ByteString toAsciiUppercase() {
        return toByteString().toAsciiUppercase();
    }

    @Override // okio.ByteString
    public ByteString digest$jvm(String algorithm) throws NoSuchAlgorithmException {
        Intrinsics.checkParameterIsNotNull(algorithm, "algorithm");
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        int segmentCount$iv = getSegments().length;
        int pos$iv = 0;
        for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
            int segmentPos$iv = getDirectory()[segmentCount$iv + s$iv];
            int nextSegmentOffset$iv = getDirectory()[s$iv];
            byte[] data = getSegments()[s$iv];
            int byteCount = nextSegmentOffset$iv - pos$iv;
            digest.update(data, segmentPos$iv, byteCount);
            pos$iv = nextSegmentOffset$iv;
        }
        byte[] bArrDigest = digest.digest();
        Intrinsics.checkExpressionValueIsNotNull(bArrDigest, "digest.digest()");
        return new ByteString(bArrDigest);
    }

    @Override // okio.ByteString
    public ByteString hmac$jvm(String algorithm, ByteString key) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        Intrinsics.checkParameterIsNotNull(algorithm, "algorithm");
        Intrinsics.checkParameterIsNotNull(key, "key");
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.toByteArray(), algorithm));
            int segmentCount$iv = getSegments().length;
            int pos$iv = 0;
            for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
                int segmentPos$iv = getDirectory()[segmentCount$iv + s$iv];
                int nextSegmentOffset$iv = getDirectory()[s$iv];
                byte[] data = getSegments()[s$iv];
                int byteCount = nextSegmentOffset$iv - pos$iv;
                mac.update(data, segmentPos$iv, byteCount);
                pos$iv = nextSegmentOffset$iv;
            }
            byte[] bArrDoFinal = mac.doFinal();
            Intrinsics.checkExpressionValueIsNotNull(bArrDoFinal, "mac.doFinal()");
            return new ByteString(bArrDoFinal);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override // okio.ByteString
    public String base64Url() {
        return toByteString().base64Url();
    }

    @Override // okio.ByteString
    public ByteString substring(int beginIndex, int endIndex) {
        if (!(beginIndex >= 0)) {
            throw new IllegalArgumentException(("beginIndex=" + beginIndex + " < 0").toString());
        }
        if (!(endIndex <= size())) {
            throw new IllegalArgumentException(("endIndex=" + endIndex + " > length(" + size() + ')').toString());
        }
        int subLen = endIndex - beginIndex;
        if (!(subLen >= 0)) {
            throw new IllegalArgumentException(("endIndex=" + endIndex + " < beginIndex=" + beginIndex).toString());
        }
        if (beginIndex == 0 && endIndex == size()) {
            return this;
        }
        if (beginIndex == endIndex) {
            return ByteString.EMPTY;
        }
        int beginSegment = segment(beginIndex);
        int endSegment = segment(endIndex - 1);
        Object[] objArrCopyOfRange = Arrays.copyOfRange(this.segments, beginSegment, endSegment + 1);
        Intrinsics.checkExpressionValueIsNotNull(objArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        byte[][] newSegments = (byte[][]) objArrCopyOfRange;
        int[] newDirectory = new int[newSegments.length * 2];
        int index = 0;
        if (beginSegment <= endSegment) {
            int s = beginSegment;
            while (true) {
                newDirectory[index] = Math.min(this.directory[s] - beginIndex, subLen);
                int index2 = index + 1;
                newDirectory[index + newSegments.length] = this.directory[this.segments.length + s];
                if (s == endSegment) {
                    break;
                }
                s++;
                index = index2;
            }
        }
        int segmentOffset = beginSegment != 0 ? this.directory[beginSegment - 1] : 0;
        int length = newSegments.length;
        newDirectory[length] = newDirectory[length] + (beginIndex - segmentOffset);
        return new SegmentedByteString(newSegments, newDirectory);
    }

    @Override // okio.ByteString
    public byte internalGet$jvm(int pos) {
        Util.checkOffsetAndCount(this.directory[this.segments.length - 1], pos, 1L);
        int segment = segment(pos);
        int segmentOffset = segment == 0 ? 0 : this.directory[segment - 1];
        int segmentPos = this.directory[this.segments.length + segment];
        return this.segments[segment][(pos - segmentOffset) + segmentPos];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int segment(int pos) {
        int i = Arrays.binarySearch(this.directory, 0, this.segments.length, pos + 1);
        return i >= 0 ? i : i ^ (-1);
    }

    @Override // okio.ByteString
    public int getSize$jvm() {
        return this.directory[this.segments.length - 1];
    }

    @Override // okio.ByteString
    public byte[] toByteArray() {
        byte[] result = new byte[size()];
        int resultPos = 0;
        int segmentCount$iv = getSegments().length;
        int pos$iv = 0;
        for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
            int segmentPos$iv = getDirectory()[segmentCount$iv + s$iv];
            int nextSegmentOffset$iv = getDirectory()[s$iv];
            byte[] data = getSegments()[s$iv];
            int byteCount = nextSegmentOffset$iv - pos$iv;
            Platform.arraycopy(data, segmentPos$iv, result, resultPos, byteCount);
            resultPos += byteCount;
            pos$iv = nextSegmentOffset$iv;
        }
        return result;
    }

    @Override // okio.ByteString
    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    @Override // okio.ByteString
    public void write(OutputStream out) throws IOException {
        Intrinsics.checkParameterIsNotNull(out, "out");
        int segmentCount$iv = getSegments().length;
        int pos$iv = 0;
        for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
            int segmentPos$iv = getDirectory()[segmentCount$iv + s$iv];
            int nextSegmentOffset$iv = getDirectory()[s$iv];
            byte[] data = getSegments()[s$iv];
            int byteCount = nextSegmentOffset$iv - pos$iv;
            out.write(data, segmentPos$iv, byteCount);
            pos$iv = nextSegmentOffset$iv;
        }
    }

    @Override // okio.ByteString
    public void write$jvm(Buffer buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        int segmentCount$iv = getSegments().length;
        int pos$iv = 0;
        for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
            int segmentPos$iv = getDirectory()[segmentCount$iv + s$iv];
            int nextSegmentOffset$iv = getDirectory()[s$iv];
            byte[] data = getSegments()[s$iv];
            int byteCount = nextSegmentOffset$iv - pos$iv;
            Segment segment = new Segment(data, segmentPos$iv, segmentPos$iv + byteCount, true, false);
            if (buffer.head == null) {
                segment.prev = segment;
                segment.next = segment.prev;
                buffer.head = segment.next;
            } else {
                Segment segment2 = buffer.head;
                if (segment2 == null) {
                    Intrinsics.throwNpe();
                }
                Segment segment3 = segment2.prev;
                if (segment3 == null) {
                    Intrinsics.throwNpe();
                }
                segment3.push(segment);
            }
            pos$iv = nextSegmentOffset$iv;
        }
        buffer.setSize$jvm(buffer.size() + size());
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int offset, ByteString other, int otherOffset, int byteCount) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (offset < 0 || offset > size() - byteCount) {
            return false;
        }
        int otherOffset2 = otherOffset;
        int endIndex$iv = offset + byteCount;
        int s$iv = segment(offset);
        int pos$iv = offset;
        while (pos$iv < endIndex$iv) {
            int segmentOffset$iv = s$iv == 0 ? 0 : getDirectory()[s$iv - 1];
            int segmentSize$iv = getDirectory()[s$iv] - segmentOffset$iv;
            int segmentPos$iv = getDirectory()[getSegments().length + s$iv];
            int byteCount$iv = Math.min(endIndex$iv, segmentOffset$iv + segmentSize$iv) - pos$iv;
            int offset$iv = (pos$iv - segmentOffset$iv) + segmentPos$iv;
            byte[] data = getSegments()[s$iv];
            if (!other.rangeEquals(otherOffset2, data, offset$iv, byteCount$iv)) {
                return false;
            }
            otherOffset2 += byteCount$iv;
            pos$iv += byteCount$iv;
            s$iv++;
        }
        return true;
    }

    @Override // okio.ByteString
    public boolean rangeEquals(int offset, byte[] other, int otherOffset, int byteCount) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (offset < 0 || offset > size() - byteCount || otherOffset < 0 || otherOffset > other.length - byteCount) {
            return false;
        }
        int otherOffset2 = otherOffset;
        int endIndex$iv = offset + byteCount;
        int s$iv = segment(offset);
        int pos$iv = offset;
        while (pos$iv < endIndex$iv) {
            int segmentOffset$iv = s$iv == 0 ? 0 : getDirectory()[s$iv - 1];
            int segmentSize$iv = getDirectory()[s$iv] - segmentOffset$iv;
            int segmentPos$iv = getDirectory()[getSegments().length + s$iv];
            int byteCount$iv = Math.min(endIndex$iv, segmentOffset$iv + segmentSize$iv) - pos$iv;
            int offset$iv = (pos$iv - segmentOffset$iv) + segmentPos$iv;
            byte[] data = getSegments()[s$iv];
            if (!Util.arrayRangeEquals(data, offset$iv, other, otherOffset2, byteCount$iv)) {
                return false;
            }
            otherOffset2 += byteCount$iv;
            pos$iv += byteCount$iv;
            s$iv++;
        }
        return true;
    }

    @Override // okio.ByteString
    public int indexOf(byte[] other, int fromIndex) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        return toByteString().indexOf(other, fromIndex);
    }

    @Override // okio.ByteString
    public int lastIndexOf(byte[] other, int fromIndex) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        return toByteString().lastIndexOf(other, fromIndex);
    }

    private final ByteString toByteString() {
        return new ByteString(toByteArray());
    }

    @Override // okio.ByteString
    public byte[] internalArray$jvm() {
        return toByteArray();
    }

    private final void forEachSegment(Function3<? super byte[], ? super Integer, ? super Integer, Unit> action) {
        int segmentCount = getSegments().length;
        int pos = 0;
        for (int s = 0; s < segmentCount; s++) {
            int segmentPos = getDirectory()[segmentCount + s];
            int nextSegmentOffset = getDirectory()[s];
            action.invoke(getSegments()[s], Integer.valueOf(segmentPos), Integer.valueOf(nextSegmentOffset - pos));
            pos = nextSegmentOffset;
        }
    }

    private final void forEachSegment(int beginIndex, int endIndex, Function3<? super byte[], ? super Integer, ? super Integer, Unit> action) {
        int s = segment(beginIndex);
        int pos = beginIndex;
        while (pos < endIndex) {
            int segmentOffset = s == 0 ? 0 : getDirectory()[s - 1];
            int segmentSize = getDirectory()[s] - segmentOffset;
            int segmentPos = getDirectory()[getSegments().length + s];
            int byteCount = Math.min(endIndex, segmentOffset + segmentSize) - pos;
            int offset = (pos - segmentOffset) + segmentPos;
            action.invoke(getSegments()[s], Integer.valueOf(offset), Integer.valueOf(byteCount));
            pos += byteCount;
            s++;
        }
    }

    @Override // okio.ByteString
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return (other instanceof ByteString) && ((ByteString) other).size() == size() && rangeEquals(0, (ByteString) other, 0, size());
    }

    @Override // okio.ByteString
    public int hashCode() {
        int result = getHashCode();
        if (result != 0) {
            return result;
        }
        int result2 = 1;
        int segmentCount$iv = getSegments().length;
        int pos$iv = 0;
        for (int s$iv = 0; s$iv < segmentCount$iv; s$iv++) {
            int segmentPos$iv = getDirectory()[segmentCount$iv + s$iv];
            int nextSegmentOffset$iv = getDirectory()[s$iv];
            byte[] data = getSegments()[s$iv];
            int byteCount = nextSegmentOffset$iv - pos$iv;
            int limit = segmentPos$iv + byteCount;
            for (int i = segmentPos$iv; i < limit; i++) {
                result2 = (result2 * 31) + data[i];
            }
            pos$iv = nextSegmentOffset$iv;
        }
        setHashCode$jvm(result2);
        return result2;
    }

    @Override // okio.ByteString
    public String toString() {
        return toByteString().toString();
    }

    private final Object writeReplace() {
        ByteString byteString = toByteString();
        if (byteString != null) {
            return byteString;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
    }
}