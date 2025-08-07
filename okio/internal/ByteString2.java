package okio.internal;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.Base64;
import okio.ByteString;
import okio.Platform;
import okio.Util;

/* compiled from: ByteString.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0017\u001a\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0002\u001a\u0010\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\tH\u0000\u001a\u0010\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\f\u0010\u0010\u001a\u00020\u0011*\u00020\u0001H\u0000\u001a\f\u0010\u0012\u001a\u00020\u0011*\u00020\u0001H\u0000\u001a\u0014\u0010\u0013\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0000\u001a\u000e\u0010\u0015\u001a\u0004\u0018\u00010\u0001*\u00020\u0011H\u0000\u001a\f\u0010\u0016\u001a\u00020\u0001*\u00020\u0011H\u0000\u001a\f\u0010\u0017\u001a\u00020\u0001*\u00020\u0011H\u0000\u001a\u0014\u0010\u0018\u001a\u00020\u0019*\u00020\u00012\u0006\u0010\u001a\u001a\u00020\tH\u0000\u001a\u0014\u0010\u0018\u001a\u00020\u0019*\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u0001H\u0000\u001a\u0016\u0010\u001b\u001a\u00020\u0019*\u00020\u00012\b\u0010\u0014\u001a\u0004\u0018\u00010\u001cH\u0000\u001a\u0014\u0010\u001d\u001a\u00020\u001e*\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u0007H\u0000\u001a\f\u0010 \u001a\u00020\u0007*\u00020\u0001H\u0000\u001a\f\u0010!\u001a\u00020\u0007*\u00020\u0001H\u0000\u001a\f\u0010\"\u001a\u00020\u0011*\u00020\u0001H\u0000\u001a\u001c\u0010#\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010$\u001a\u00020\u0007H\u0000\u001a\f\u0010%\u001a\u00020\t*\u00020\u0001H\u0000\u001a\u001c\u0010&\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010$\u001a\u00020\u0007H\u0000\u001a,\u0010'\u001a\u00020\u0019*\u00020\u00012\u0006\u0010(\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0007H\u0000\u001a,\u0010'\u001a\u00020\u0019*\u00020\u00012\u0006\u0010(\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0007H\u0000\u001a\u0014\u0010+\u001a\u00020\u0019*\u00020\u00012\u0006\u0010,\u001a\u00020\tH\u0000\u001a\u0014\u0010+\u001a\u00020\u0019*\u00020\u00012\u0006\u0010,\u001a\u00020\u0001H\u0000\u001a\u001c\u0010-\u001a\u00020\u0001*\u00020\u00012\u0006\u0010.\u001a\u00020\u00072\u0006\u0010/\u001a\u00020\u0007H\u0000\u001a\f\u00100\u001a\u00020\u0001*\u00020\u0001H\u0000\u001a\f\u00101\u001a\u00020\u0001*\u00020\u0001H\u0000\u001a\f\u00102\u001a\u00020\t*\u00020\u0001H\u0000\u001a\f\u00103\u001a\u00020\u0011*\u00020\u0001H\u0000\u001a\f\u00104\u001a\u00020\u0011*\u00020\u0001H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"COMMON_EMPTY", "Lokio/ByteString;", "getCOMMON_EMPTY", "()Lokio/ByteString;", "HEX_DIGITS", "", "codePointIndexToCharIndex", "", "s", "", "codePointCount", "commonOf", "data", "decodeHexDigit", "c", "", "commonBase64", "", "commonBase64Url", "commonCompareTo", "other", "commonDecodeBase64", "commonDecodeHex", "commonEncodeUtf8", "commonEndsWith", "", "suffix", "commonEquals", "", "commonGetByte", "", "pos", "commonGetSize", "commonHashCode", "commonHex", "commonIndexOf", "fromIndex", "commonInternalArray", "commonLastIndexOf", "commonRangeEquals", "offset", "otherOffset", "byteCount", "commonStartsWith", "prefix", "commonSubstring", "beginIndex", "endIndex", "commonToAsciiLowercase", "commonToAsciiUppercase", "commonToByteArray", "commonToString", "commonUtf8", "jvm"}, k = 2, mv = {1, 1, 11})
/* renamed from: okio.internal.ByteStringKt, reason: use source file name */
/* loaded from: classes.dex */
public final class ByteString2 {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final ByteString COMMON_EMPTY = ByteString.INSTANCE.of(new byte[0]);

    public static final String commonUtf8(ByteString receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        String result = receiver.getUtf8();
        if (result == null) {
            String result2 = Platform.toUtf8String(receiver.internalArray$jvm());
            receiver.setUtf8$jvm(result2);
            return result2;
        }
        return result;
    }

    public static final String commonBase64(ByteString receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return Base64.encodeBase64$default(receiver.getData(), null, 1, null);
    }

    public static final String commonBase64Url(ByteString receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return Base64.encodeBase64(receiver.getData(), Base64.getBASE64_URL_SAFE());
    }

    public static final String commonHex(ByteString receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        char[] result = new char[receiver.getData().length * 2];
        int c = 0;
        for (int b : receiver.getData()) {
            int c2 = c + 1;
            int other$iv = b >> 4;
            result[c] = HEX_DIGITS[other$iv & 15];
            c = c2 + 1;
            int other$iv2 = 15 & b;
            result[c2] = HEX_DIGITS[other$iv2];
        }
        return new String(result);
    }

    public static final ByteString commonToAsciiLowercase(ByteString receiver) {
        byte b;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        for (int i = 0; i < receiver.getData().length; i++) {
            byte c = receiver.getData()[i];
            byte b2 = (byte) 65;
            if (c >= b2 && c <= (b = (byte) 90)) {
                byte[] data$jvm = receiver.getData();
                byte[] lowercase = Arrays.copyOf(data$jvm, data$jvm.length);
                Intrinsics.checkExpressionValueIsNotNull(lowercase, "java.util.Arrays.copyOf(this, size)");
                int i2 = i + 1;
                lowercase[i] = (byte) (c + 32);
                while (i2 < lowercase.length) {
                    byte c2 = lowercase[i2];
                    if (c2 < b2 || c2 > b) {
                        i2++;
                    } else {
                        lowercase[i2] = (byte) (c2 + 32);
                        i2++;
                    }
                }
                return new ByteString(lowercase);
            }
        }
        return receiver;
    }

    public static final ByteString commonToAsciiUppercase(ByteString receiver) {
        byte b;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        for (int i = 0; i < receiver.getData().length; i++) {
            byte c = receiver.getData()[i];
            byte b2 = (byte) 97;
            if (c >= b2 && c <= (b = (byte) 122)) {
                byte[] data$jvm = receiver.getData();
                byte[] lowercase = Arrays.copyOf(data$jvm, data$jvm.length);
                Intrinsics.checkExpressionValueIsNotNull(lowercase, "java.util.Arrays.copyOf(this, size)");
                int i2 = i + 1;
                lowercase[i] = (byte) (c - 32);
                while (i2 < lowercase.length) {
                    byte c2 = lowercase[i2];
                    if (c2 < b2 || c2 > b) {
                        i2++;
                    } else {
                        lowercase[i2] = (byte) (c2 - 32);
                        i2++;
                    }
                }
                return new ByteString(lowercase);
            }
        }
        return receiver;
    }

    public static final ByteString commonSubstring(ByteString receiver, int beginIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        if (!(beginIndex >= 0)) {
            throw new IllegalArgumentException("beginIndex < 0".toString());
        }
        if (!(endIndex <= receiver.getData().length)) {
            throw new IllegalArgumentException(("endIndex > length(" + receiver.getData().length + ')').toString());
        }
        int subLen = endIndex - beginIndex;
        if (!(subLen >= 0)) {
            throw new IllegalArgumentException("endIndex < beginIndex".toString());
        }
        if (beginIndex == 0 && endIndex == receiver.getData().length) {
            return receiver;
        }
        byte[] copy = new byte[subLen];
        Platform.arraycopy(receiver.getData(), beginIndex, copy, 0, subLen);
        return new ByteString(copy);
    }

    public static final byte commonGetByte(ByteString receiver, int pos) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return receiver.getData()[pos];
    }

    public static final int commonGetSize(ByteString receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return receiver.getData().length;
    }

    public static final byte[] commonToByteArray(ByteString receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        byte[] data$jvm = receiver.getData();
        byte[] bArrCopyOf = Arrays.copyOf(data$jvm, data$jvm.length);
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return bArrCopyOf;
    }

    public static final byte[] commonInternalArray(ByteString receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return receiver.getData();
    }

    public static final boolean commonRangeEquals(ByteString receiver, int offset, ByteString other, int otherOffset, int byteCount) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return other.rangeEquals(otherOffset, receiver.getData(), offset, byteCount);
    }

    public static final boolean commonRangeEquals(ByteString receiver, int offset, byte[] other, int otherOffset, int byteCount) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return offset >= 0 && offset <= receiver.getData().length - byteCount && otherOffset >= 0 && otherOffset <= other.length - byteCount && Util.arrayRangeEquals(receiver.getData(), offset, other, otherOffset, byteCount);
    }

    public static final boolean commonStartsWith(ByteString receiver, ByteString prefix) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        return receiver.rangeEquals(0, prefix, 0, prefix.size());
    }

    public static final boolean commonStartsWith(ByteString receiver, byte[] prefix) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        return receiver.rangeEquals(0, prefix, 0, prefix.length);
    }

    public static final boolean commonEndsWith(ByteString receiver, ByteString suffix) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(suffix, "suffix");
        return receiver.rangeEquals(receiver.size() - suffix.size(), suffix, 0, suffix.size());
    }

    public static final boolean commonEndsWith(ByteString receiver, byte[] suffix) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(suffix, "suffix");
        return receiver.rangeEquals(receiver.size() - suffix.length, suffix, 0, suffix.length);
    }

    public static final int commonIndexOf(ByteString receiver, byte[] other, int fromIndex) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        int limit = receiver.getData().length - other.length;
        int i = Math.max(fromIndex, 0);
        if (i <= limit) {
            while (!Util.arrayRangeEquals(receiver.getData(), i, other, 0, other.length)) {
                if (i == limit) {
                    return -1;
                }
                i++;
            }
            return i;
        }
        return -1;
    }

    public static final int commonLastIndexOf(ByteString receiver, byte[] other, int fromIndex) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        int limit = receiver.getData().length - other.length;
        for (int i = Math.min(fromIndex, limit); i >= 0; i--) {
            if (Util.arrayRangeEquals(receiver.getData(), i, other, 0, other.length)) {
                return i;
            }
        }
        return -1;
    }

    public static final boolean commonEquals(ByteString receiver, Object other) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        if (other == receiver) {
            return true;
        }
        return (other instanceof ByteString) && ((ByteString) other).size() == receiver.getData().length && ((ByteString) other).rangeEquals(0, receiver.getData(), 0, receiver.getData().length);
    }

    public static final int commonHashCode(ByteString receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        int result = receiver.getHashCode();
        if (result != 0) {
            return result;
        }
        receiver.setHashCode$jvm(Arrays.hashCode(receiver.getData()));
        return receiver.getHashCode();
    }

    public static final int commonCompareTo(ByteString receiver, ByteString other) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        int sizeA = receiver.size();
        int sizeB = other.size();
        int size = Math.min(sizeA, sizeB);
        for (int i = 0; i < size; i++) {
            int $receiver$iv = receiver.getByte(i);
            int $i$f$and = $receiver$iv & 255;
            byte $receiver$iv2 = other.getByte(i);
            int byteB = $receiver$iv2 & UByte.MAX_VALUE;
            if ($i$f$and != byteB) {
                return $i$f$and < byteB ? -1 : 1;
            }
        }
        if (sizeA == sizeB) {
            return 0;
        }
        return sizeA < sizeB ? -1 : 1;
    }

    public static final ByteString getCOMMON_EMPTY() {
        return COMMON_EMPTY;
    }

    public static final ByteString commonOf(byte[] data) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        byte[] bArrCopyOf = Arrays.copyOf(data, data.length);
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return new ByteString(bArrCopyOf);
    }

    public static final ByteString commonEncodeUtf8(String receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        ByteString byteString = new ByteString(Platform.asUtf8ToByteArray(receiver));
        byteString.setUtf8$jvm(receiver);
        return byteString;
    }

    public static final ByteString commonDecodeBase64(String receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        byte[] decoded = Base64.decodeBase64ToArray(receiver);
        if (decoded != null) {
            return new ByteString(decoded);
        }
        return null;
    }

    public static final ByteString commonDecodeHex(String receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        if (!(receiver.length() % 2 == 0)) {
            throw new IllegalArgumentException(("Unexpected hex string: " + receiver).toString());
        }
        byte[] result = new byte[receiver.length() / 2];
        int length = result.length;
        for (int i = 0; i < length; i++) {
            int d1 = decodeHexDigit(receiver.charAt(i * 2)) << 4;
            int d2 = decodeHexDigit(receiver.charAt((i * 2) + 1));
            result[i] = (byte) (d1 + d2);
        }
        return new ByteString(result);
    }

    private static final int decodeHexDigit(char c) {
        if ('0' <= c && '9' >= c) {
            return c - '0';
        }
        if ('a' <= c && 'f' >= c) {
            return (c - 'a') + 10;
        }
        if ('A' > c || 'F' < c) {
            throw new IllegalArgumentException("Unexpected hex digit: " + c);
        }
        return (c - 'A') + 10;
    }

    public static final String commonToString(ByteString receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        if (receiver.getData().length == 0) {
            return "[size=0]";
        }
        int i = codePointIndexToCharIndex(receiver.getData(), 64);
        if (i == -1) {
            return receiver.getData().length <= 64 ? "[hex=" + receiver.hex() + ']' : "[size=" + receiver.getData().length + " hex=" + commonSubstring(receiver, 0, 64).hex() + "…]";
        }
        String text = receiver.utf8();
        if (text == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String strSubstring = text.substring(0, i);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        String safeText = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(strSubstring, "\\", "\\\\", false, 4, (Object) null), "\n", "\\n", false, 4, (Object) null), "\r", "\\r", false, 4, (Object) null);
        return i < text.length() ? "[size=" + receiver.getData().length + " text=" + safeText + "…]" : "[text=" + safeText + ']';
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x03a4 A[PHI: r14
  0x03a4: PHI (r14v16 'j' int) = (r14v15 'j' int), (r14v18 'j' int) binds: [B:347:0x03a2, B:324:0x0373] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:374:0x03fc  */
    /* JADX WARN: Removed duplicated region for block: B:397:0x0442  */
    /* JADX WARN: Removed duplicated region for block: B:398:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x0498  */
    /* JADX WARN: Removed duplicated region for block: B:450:0x04ee  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:506:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:535:0x05d5  */
    /* JADX WARN: Removed duplicated region for block: B:540:0x05df A[PHI: r12
  0x05df: PHI (r12v6 'j' int) = (r12v2 'j' int), (r12v3 'j' int), (r12v7 'j' int) binds: [B:588:0x0649, B:565:0x061a, B:539:0x05dd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:561:0x0612  */
    /* JADX WARN: Removed duplicated region for block: B:584:0x0641  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:625:0x0097 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final int codePointIndexToCharIndex(byte[] s, int codePointCount) {
        int $i$f$processUtf8CodePoints;
        int endIndex$iv;
        byte[] $receiver$iv;
        int $i$f$process4Utf8Bytes;
        int j;
        int c;
        int $i$f$process3Utf8Bytes;
        int j2;
        int j3;
        int $i$f$process2Utf8Bytes;
        int j4;
        int charCount = 0;
        int j5 = 0;
        int index$iv = 0;
        int endIndex$iv2 = s.length;
        int $i$a$3$process4Utf8Bytes = 0;
        int $i$f$processUtf8CodePoints2 = 0;
        byte[] $receiver$iv2 = s;
        boolean z = false;
        int $i$f$isUtf8Continuation = 0;
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        while (index$iv < endIndex$iv2) {
            byte b0$iv = $receiver$iv2[index$iv];
            if (b0$iv >= 0) {
                int c2 = j5 + 1;
                if (j5 == codePointCount) {
                    return charCount;
                }
                if (b0$iv != 10 && b0$iv != 13) {
                    if (!((b0$iv >= 0 && 31 >= b0$iv) || (Byte.MAX_VALUE <= b0$iv && 159 >= b0$iv))) {
                    }
                    return -1;
                }
                if (b0$iv == 65533) {
                    return -1;
                }
                charCount += b0$iv < 65536 ? 1 : 2;
                index$iv++;
                j5 = c2;
                while (index$iv < endIndex$iv2 && $receiver$iv2[index$iv] >= 0) {
                    int index$iv2 = index$iv + 1;
                    int c3 = $receiver$iv2[index$iv];
                    int j6 = j5 + 1;
                    if (j5 == codePointCount) {
                        return charCount;
                    }
                    if (c3 != 10 && c3 != 13) {
                        boolean z3 = z2;
                        if (!((c3 >= 0 && 31 >= c3) || (127 <= c3 && 159 >= c3))) {
                            z2 = z3;
                            if (c3 == 65533) {
                            }
                        }
                    } else if (c3 == 65533) {
                        charCount += c3 < 65536 ? 1 : 2;
                        index$iv = index$iv2;
                        j5 = j6;
                    }
                    return -1;
                }
                endIndex$iv = endIndex$iv2;
                $i$f$processUtf8CodePoints = $i$f$processUtf8CodePoints2;
                $receiver$iv = $receiver$iv2;
            } else {
                int other$iv$iv = b0$iv >> 5;
                $i$f$processUtf8CodePoints = $i$f$processUtf8CodePoints2;
                int $i$f$process4Utf8Bytes2 = i2;
                if (other$iv$iv == -2) {
                    byte[] $receiver$iv$iv = $receiver$iv2;
                    int $i$f$process2Utf8Bytes2 = $i$f$isUtf8Continuation;
                    if (endIndex$iv2 <= index$iv + 1) {
                        j4 = j5 + 1;
                        if (j5 == codePointCount) {
                            return charCount;
                        }
                        if (65533 != 10 && 65533 != 13) {
                            if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                z = true;
                            }
                            if (!z) {
                            }
                            return -1;
                        }
                        if (65533 == 65533) {
                            return -1;
                        }
                        charCount += 65533 < 65536 ? 1 : 2;
                        $i$f$process2Utf8Bytes = $i$f$process2Utf8Bytes2;
                        i = 1;
                    } else {
                        byte b0$iv$iv = $receiver$iv$iv[index$iv];
                        byte b1$iv$iv = $receiver$iv$iv[index$iv + 1];
                        $i$f$process2Utf8Bytes = $i$f$process2Utf8Bytes2;
                        if ((b1$iv$iv & 192) == 128) {
                            int codePoint$iv$iv = (b1$iv$iv ^ ByteCompanionObject.MIN_VALUE) ^ (b0$iv$iv << 6);
                            if (codePoint$iv$iv < 128) {
                                j4 = j5 + 1;
                                if (j5 == codePointCount) {
                                    return charCount;
                                }
                                int $i$a$1$process2Utf8Bytes = $i$a$3$process4Utf8Bytes;
                                if (65533 != 10 && 65533 != 13) {
                                    if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                        z = true;
                                    }
                                    if (!z) {
                                    }
                                    return -1;
                                }
                                if (65533 == 65533) {
                                    return -1;
                                }
                                charCount += 65533 >= 65536 ? 2 : 1;
                                $i$a$3$process4Utf8Bytes = $i$a$1$process2Utf8Bytes;
                            } else {
                                j4 = j5 + 1;
                                if (j5 == codePointCount) {
                                    return charCount;
                                }
                                if (codePoint$iv$iv != 10 && codePoint$iv$iv != 13) {
                                    if ((codePoint$iv$iv >= 0 && 31 >= codePoint$iv$iv) || (127 <= codePoint$iv$iv && 159 >= codePoint$iv$iv)) {
                                        z = true;
                                    }
                                    if (!z) {
                                    }
                                    return -1;
                                }
                                if (codePoint$iv$iv == 65533) {
                                    return -1;
                                }
                                charCount += codePoint$iv$iv >= 65536 ? 2 : 1;
                            }
                        } else {
                            j4 = j5 + 1;
                            if (j5 == codePointCount) {
                                return charCount;
                            }
                            if (65533 != 10 && 65533 != 13) {
                                if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                    z = true;
                                }
                                if (!z) {
                                }
                                return -1;
                            }
                            if (65533 == 65533) {
                                return -1;
                            }
                            charCount += 65533 < 65536 ? 1 : 2;
                            i = 1;
                        }
                    }
                    index$iv += i;
                    endIndex$iv = endIndex$iv2;
                    $receiver$iv = $receiver$iv2;
                    i2 = $i$f$process4Utf8Bytes2;
                    j5 = j4;
                    $i$f$isUtf8Continuation = $i$f$process2Utf8Bytes;
                } else {
                    int other$iv$iv2 = b0$iv >> 4;
                    if (other$iv$iv2 == -2) {
                        int $i$f$process3Utf8Bytes2 = i;
                        byte[] $receiver$iv$iv2 = $receiver$iv2;
                        if (endIndex$iv2 <= index$iv + 2) {
                            j3 = j5 + 1;
                            if (j5 == codePointCount) {
                                return charCount;
                            }
                            if (65533 != 10 && 65533 != 13) {
                                if (!((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533))) {
                                }
                                return -1;
                            }
                            if (65533 == 65533) {
                                return -1;
                            }
                            charCount += 65533 < 65536 ? 1 : 2;
                            if (endIndex$iv2 > index$iv + 1) {
                                int byte$iv$iv$iv = $receiver$iv$iv2[index$iv + 1];
                                int other$iv$iv$iv$iv = 192 & byte$iv$iv$iv;
                                if (other$iv$iv$iv$iv == 128) {
                                    $i$f$process3Utf8Bytes = $i$f$process3Utf8Bytes2;
                                } else {
                                    $i$f$process3Utf8Bytes = $i$f$process3Utf8Bytes2;
                                    i = 1;
                                }
                            }
                        } else {
                            byte b0$iv$iv2 = $receiver$iv$iv2[index$iv];
                            byte b1$iv$iv2 = $receiver$iv$iv2[index$iv + 1];
                            $i$f$process3Utf8Bytes = $i$f$process3Utf8Bytes2;
                            if ((b1$iv$iv2 & 192) == 128) {
                                byte b2$iv$iv = $receiver$iv$iv2[index$iv + 2];
                                int i3 = $i$f$isUtf8Continuation;
                                if ((b2$iv$iv & 192) == 128) {
                                    int codePoint$iv$iv2 = (((-123008) ^ b2$iv$iv) ^ (b1$iv$iv2 << 6)) ^ (b0$iv$iv2 << 12);
                                    if (codePoint$iv$iv2 < 2048) {
                                        int j7 = j5 + 1;
                                        if (j5 == codePointCount) {
                                            return charCount;
                                        }
                                        if (65533 != 10 && 65533 != 13) {
                                            if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                                z = true;
                                            }
                                            if (!z) {
                                            }
                                            return -1;
                                        }
                                        if (65533 == 65533) {
                                            return -1;
                                        }
                                        charCount += 65533 < 65536 ? 1 : 2;
                                        j3 = j7;
                                    } else if (55296 <= codePoint$iv$iv2 && 57343 >= codePoint$iv$iv2) {
                                        j2 = j5 + 1;
                                        if (j5 == codePointCount) {
                                            return charCount;
                                        }
                                        if (65533 != 10 && 65533 != 13) {
                                            if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                                z = true;
                                            }
                                            if (!z) {
                                            }
                                            return -1;
                                        }
                                        if (65533 == 65533) {
                                            return -1;
                                        }
                                        if (65533 < 65536) {
                                            i = 1;
                                        }
                                        charCount += i;
                                        j3 = j2;
                                    } else {
                                        j2 = j5 + 1;
                                        if (j5 == codePointCount) {
                                            return charCount;
                                        }
                                        if (codePoint$iv$iv2 != 10 && codePoint$iv$iv2 != 13) {
                                            if ((codePoint$iv$iv2 >= 0 && 31 >= codePoint$iv$iv2) || (127 <= codePoint$iv$iv2 && 159 >= codePoint$iv$iv2)) {
                                                z = true;
                                            }
                                            if (!z) {
                                            }
                                            return -1;
                                        }
                                        if (codePoint$iv$iv2 == 65533) {
                                            return -1;
                                        }
                                        if (codePoint$iv$iv2 < 65536) {
                                        }
                                        charCount += i;
                                        j3 = j2;
                                    }
                                    $i$f$isUtf8Continuation = i3;
                                    i = 3;
                                } else {
                                    j3 = j5 + 1;
                                    if (j5 == codePointCount) {
                                        return charCount;
                                    }
                                    if (65533 != 10 && 65533 != 13) {
                                        if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                            z = true;
                                        }
                                        if (!z) {
                                        }
                                        return -1;
                                    }
                                    if (65533 == 65533) {
                                        return -1;
                                    }
                                    charCount += 65533 >= 65536 ? 2 : 1;
                                    $i$f$isUtf8Continuation = i3;
                                }
                            } else {
                                j3 = j5 + 1;
                                if (j5 == codePointCount) {
                                    return charCount;
                                }
                                if (65533 != 10 && 65533 != 13) {
                                    if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                        z = true;
                                    }
                                    if (!z) {
                                    }
                                    return -1;
                                }
                                if (65533 == 65533) {
                                    return -1;
                                }
                                charCount += 65533 < 65536 ? 1 : 2;
                                i = 1;
                            }
                        }
                        index$iv += i;
                        $receiver$iv = $receiver$iv2;
                        i2 = $i$f$process4Utf8Bytes2;
                        j5 = j3;
                        i = $i$f$process3Utf8Bytes;
                        endIndex$iv = endIndex$iv2;
                    } else {
                        int other$iv$iv3 = b0$iv >> 3;
                        if (other$iv$iv3 == -2) {
                            byte[] $receiver$iv$iv3 = $receiver$iv2;
                            if (endIndex$iv2 <= index$iv + 3) {
                                c = j5 + 1;
                                if (j5 == codePointCount) {
                                    return charCount;
                                }
                                int i4 = $i$a$3$process4Utf8Bytes;
                                if (65533 != 10 && 65533 != 13) {
                                    if (!((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533))) {
                                    }
                                    return -1;
                                }
                                if (65533 == 65533) {
                                    return -1;
                                }
                                charCount += 65533 < 65536 ? 1 : 2;
                                if (endIndex$iv2 > index$iv + 1) {
                                    int byte$iv$iv$iv2 = $receiver$iv$iv3[index$iv + 1];
                                    int other$iv$iv$iv$iv2 = 192 & byte$iv$iv$iv2;
                                    byte byte$iv$iv$iv3 = other$iv$iv$iv$iv2 == 128 ? (byte) 1 : (byte) 0;
                                    if (byte$iv$iv$iv3 == 0) {
                                        endIndex$iv = endIndex$iv2;
                                        $i$f$process4Utf8Bytes = $i$f$process4Utf8Bytes2;
                                        $receiver$iv = $receiver$iv2;
                                        $i$a$3$process4Utf8Bytes = i4;
                                        i = 1;
                                    } else if (endIndex$iv2 > index$iv + 2) {
                                        int byte$iv$iv$iv4 = $receiver$iv$iv3[index$iv + 2];
                                        int other$iv$iv$iv$iv3 = 192 & byte$iv$iv$iv4;
                                        if (other$iv$iv$iv$iv3 == 128) {
                                            endIndex$iv = endIndex$iv2;
                                            $i$f$process4Utf8Bytes = $i$f$process4Utf8Bytes2;
                                            $receiver$iv = $receiver$iv2;
                                            $i$a$3$process4Utf8Bytes = i4;
                                            i = 3;
                                        } else {
                                            endIndex$iv = endIndex$iv2;
                                            $i$f$process4Utf8Bytes = $i$f$process4Utf8Bytes2;
                                            $receiver$iv = $receiver$iv2;
                                            $i$a$3$process4Utf8Bytes = i4;
                                        }
                                    }
                                }
                            } else {
                                byte b0$iv$iv3 = $receiver$iv$iv3[index$iv];
                                int b1$iv$iv3 = $receiver$iv$iv3[index$iv + 1];
                                endIndex$iv = endIndex$iv2;
                                int endIndex$iv3 = b1$iv$iv3 & Opcodes.CHECKCAST;
                                $i$f$process4Utf8Bytes = $i$f$process4Utf8Bytes2;
                                if (endIndex$iv3 == 128) {
                                    byte b2$iv$iv2 = $receiver$iv$iv3[index$iv + 2];
                                    $receiver$iv = $receiver$iv2;
                                    if ((b2$iv$iv2 & 192) == 128) {
                                        byte b3$iv$iv = $receiver$iv$iv3[index$iv + 3];
                                        if ((b3$iv$iv & 192) == 128) {
                                            int codePoint$iv$iv3 = (((3678080 ^ b3$iv$iv) ^ (b2$iv$iv2 << 6)) ^ (b1$iv$iv3 << 12)) ^ (b0$iv$iv3 << 18);
                                            if (codePoint$iv$iv3 > 1114111) {
                                                c = j5 + 1;
                                                if (j5 == codePointCount) {
                                                    return charCount;
                                                }
                                                if (65533 != 10 && 65533 != 13) {
                                                    if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                                        z = true;
                                                    }
                                                    if (!z) {
                                                    }
                                                    return -1;
                                                }
                                                if (65533 == 65533) {
                                                    return -1;
                                                }
                                                charCount += 65533 < 65536 ? 1 : 2;
                                            } else if (55296 <= codePoint$iv$iv3 && 57343 >= codePoint$iv$iv3) {
                                                j = j5 + 1;
                                                if (j5 == codePointCount) {
                                                    return charCount;
                                                }
                                                if (65533 != 10 && 65533 != 13) {
                                                    if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                                        z = true;
                                                    }
                                                    if (!z) {
                                                    }
                                                    return -1;
                                                }
                                                if (65533 == 65533) {
                                                    return -1;
                                                }
                                                if (65533 < 65536) {
                                                    i = 1;
                                                }
                                                charCount += i;
                                                c = j;
                                            } else if (codePoint$iv$iv3 < 65536) {
                                                j = j5 + 1;
                                                if (j5 == codePointCount) {
                                                    return charCount;
                                                }
                                                if (65533 != 10 && 65533 != 13) {
                                                    if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                                        z = true;
                                                    }
                                                    if (!z) {
                                                    }
                                                    return -1;
                                                }
                                                if (65533 == 65533) {
                                                    return -1;
                                                }
                                                if (65533 < 65536) {
                                                }
                                                charCount += i;
                                                c = j;
                                            } else {
                                                j = j5 + 1;
                                                if (j5 == codePointCount) {
                                                    return charCount;
                                                }
                                                if (codePoint$iv$iv3 != 10 && codePoint$iv$iv3 != 13) {
                                                    if ((codePoint$iv$iv3 >= 0 && 31 >= codePoint$iv$iv3) || (127 <= codePoint$iv$iv3 && 159 >= codePoint$iv$iv3)) {
                                                        z = true;
                                                    }
                                                    if (!z) {
                                                    }
                                                    return -1;
                                                }
                                                if (codePoint$iv$iv3 == 65533) {
                                                    return -1;
                                                }
                                                if (codePoint$iv$iv3 < 65536) {
                                                }
                                                charCount += i;
                                                c = j;
                                            }
                                            i = 4;
                                        } else {
                                            c = j5 + 1;
                                            if (j5 == codePointCount) {
                                                return charCount;
                                            }
                                            if (65533 != 10 && 65533 != 13) {
                                                if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                                    z = true;
                                                }
                                                if (!z) {
                                                }
                                                return -1;
                                            }
                                            if (65533 == 65533) {
                                                return -1;
                                            }
                                            charCount += 65533 < 65536 ? 1 : 2;
                                            i = 3;
                                        }
                                    } else {
                                        int j8 = j5 + 1;
                                        if (j5 == codePointCount) {
                                            return charCount;
                                        }
                                        if (65533 != 10 && 65533 != 13) {
                                            if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                                z = true;
                                            }
                                            if (!z) {
                                            }
                                            return -1;
                                        }
                                        if (65533 == 65533) {
                                            return -1;
                                        }
                                        charCount += 65533 >= 65536 ? 2 : 1;
                                        c = j8;
                                    }
                                } else {
                                    int j9 = j5 + 1;
                                    if (j5 == codePointCount) {
                                        return charCount;
                                    }
                                    if (65533 != 10 && 65533 != 13) {
                                        if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                            z = true;
                                        }
                                        if (!z) {
                                        }
                                        return -1;
                                    }
                                    if (65533 == 65533) {
                                        return -1;
                                    }
                                    charCount += 65533 < 65536 ? 1 : 2;
                                    $receiver$iv = $receiver$iv2;
                                    c = j9;
                                    i = 1;
                                }
                            }
                            index$iv += i;
                            j5 = c;
                            i2 = $i$f$process4Utf8Bytes;
                        } else {
                            endIndex$iv = endIndex$iv2;
                            $receiver$iv = $receiver$iv2;
                            boolean z4 = z;
                            int j10 = j5 + 1;
                            if (j5 == codePointCount) {
                                return charCount;
                            }
                            if (65533 != 10 && 65533 != 13) {
                                if ((65533 >= 0 && 31 >= 65533) || (127 <= 65533 && 159 >= 65533)) {
                                    z = true;
                                }
                                if (!z) {
                                }
                                return -1;
                            }
                            if (65533 == 65533) {
                                return -1;
                            }
                            charCount += 65533 < 65536 ? 1 : 2;
                            index$iv++;
                            z = z4;
                            j5 = j10;
                            i2 = $i$f$process4Utf8Bytes2;
                        }
                    }
                }
            }
            $i$f$processUtf8CodePoints2 = $i$f$processUtf8CodePoints;
            endIndex$iv2 = endIndex$iv;
            $receiver$iv2 = $receiver$iv;
        }
        return charCount;
    }
}