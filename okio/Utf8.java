package okio;

import com.alibaba.fastjson.asm.Opcodes;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Utf8.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000D\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0001H\u0080\b\u001a\u0011\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0007H\u0080\b\u001a1\u0010\u0010\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u0017\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u0018\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u0019\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u001a\u001a\u00020\u0016*\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a1\u0010\u001c\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\b\u001a%\u0010\u001d\u001a\u00020\u001e*\u00020\u001b2\b\b\u0002\u0010\u0012\u001a\u00020\u00012\b\b\u0002\u0010\u0013\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u001f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"HIGH_SURROGATE_HEADER", "", "LOG_SURROGATE_HEADER", "MASK_2BYTES", "MASK_3BYTES", "MASK_4BYTES", "REPLACEMENT_BYTE", "", "REPLACEMENT_CHARACTER", "", "REPLACEMENT_CODE_POINT", "isIsoControl", "", "codePoint", "isUtf8Continuation", "byte", "process2Utf8Bytes", "", "beginIndex", "endIndex", "yield", "Lkotlin/Function1;", "", "process3Utf8Bytes", "process4Utf8Bytes", "processUtf16Chars", "processUtf8Bytes", "", "processUtf8CodePoints", "utf8Size", "", "size", "jvm"}, k = 2, mv = {1, 1, 11})
/* loaded from: classes.dex */
public final class Utf8 {
    public static final int HIGH_SURROGATE_HEADER = 55232;
    public static final int LOG_SURROGATE_HEADER = 56320;
    public static final int MASK_2BYTES = 3968;
    public static final int MASK_3BYTES = -123008;
    public static final int MASK_4BYTES = 3678080;
    public static final byte REPLACEMENT_BYTE = 63;
    public static final char REPLACEMENT_CHARACTER = 65533;
    public static final int REPLACEMENT_CODE_POINT = 65533;

    public static final long size(String str) {
        return size$default(str, 0, 0, 3, null);
    }

    public static final long size(String str, int i) {
        return size$default(str, i, 0, 2, null);
    }

    public static /* bridge */ /* synthetic */ long size$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return size(str, i, i2);
    }

    public static final long size(String receiver, int beginIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        if (!(beginIndex >= 0)) {
            throw new IllegalArgumentException(("beginIndex < 0: " + beginIndex).toString());
        }
        if (!(endIndex >= beginIndex)) {
            throw new IllegalArgumentException(("endIndex < beginIndex: " + endIndex + " < " + beginIndex).toString());
        }
        if (!(endIndex <= receiver.length())) {
            throw new IllegalArgumentException(("endIndex > string.length: " + endIndex + " > " + receiver.length()).toString());
        }
        long result = 0;
        int i = beginIndex;
        while (i < endIndex) {
            int c = receiver.charAt(i);
            if (c < 128) {
                result++;
                i++;
            } else if (c < 2048) {
                result += 2;
                i++;
            } else if (c < 55296 || c > 57343) {
                result += 3;
                i++;
            } else {
                int low = i + 1 < endIndex ? receiver.charAt(i + 1) : 0;
                if (c > 56319 || low < 56320 || low > 57343) {
                    result++;
                    i++;
                } else {
                    result += 4;
                    i += 2;
                }
            }
        }
        return result;
    }

    public static final boolean isIsoControl(int codePoint) {
        return (codePoint >= 0 && 31 >= codePoint) || (127 <= codePoint && 159 >= codePoint);
    }

    public static final boolean isUtf8Continuation(byte b) {
        int other$iv = 192 & b;
        return other$iv == 128;
    }

    public static final void processUtf8Bytes(String receiver, int beginIndex, int endIndex, Function1<? super Byte, Unit> yield) {
        char cCharAt;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(yield, "yield");
        int index = beginIndex;
        while (index < endIndex) {
            char c = receiver.charAt(index);
            if (c < 128) {
                yield.invoke(Byte.valueOf((byte) c));
                index++;
                while (index < endIndex && receiver.charAt(index) < 128) {
                    yield.invoke(Byte.valueOf((byte) receiver.charAt(index)));
                    index++;
                }
            } else if (c < 2048) {
                yield.invoke(Byte.valueOf((byte) ((c >> 6) | Opcodes.CHECKCAST)));
                yield.invoke(Byte.valueOf((byte) (128 | (c & '?'))));
                index++;
            } else if (55296 > c || 57343 < c) {
                yield.invoke(Byte.valueOf((byte) ((c >> '\f') | 224)));
                yield.invoke(Byte.valueOf((byte) (((c >> 6) & 63) | 128)));
                yield.invoke(Byte.valueOf((byte) (128 | (c & '?'))));
                index++;
            } else if (c > 56319 || endIndex <= index + 1 || 56320 > (cCharAt = receiver.charAt(index + 1)) || 57343 < cCharAt) {
                yield.invoke(Byte.valueOf(REPLACEMENT_BYTE));
                index++;
            } else {
                int codePoint = ((c << '\n') + receiver.charAt(index + 1)) - 56613888;
                yield.invoke(Byte.valueOf((byte) ((codePoint >> 18) | 240)));
                yield.invoke(Byte.valueOf((byte) (((codePoint >> 12) & 63) | 128)));
                yield.invoke(Byte.valueOf((byte) ((63 & (codePoint >> 6)) | 128)));
                yield.invoke(Byte.valueOf((byte) (128 | (codePoint & 63))));
                index += 2;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void processUtf8CodePoints(byte[] $receiver, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        int $i$f$processUtf8CodePoints;
        byte[] receiver = $receiver;
        int i = endIndex;
        int $i$f$processUtf8CodePoints2 = 0;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(yield, "yield");
        int index = beginIndex;
        boolean z = false;
        boolean z2 = false;
        while (index < i) {
            byte b0 = receiver[index];
            if (b0 >= 0) {
                yield.invoke(Integer.valueOf(b0));
                index++;
                while (index < i && receiver[index] >= 0) {
                    yield.invoke(Integer.valueOf(receiver[index]));
                    index++;
                }
                $i$f$processUtf8CodePoints = $i$f$processUtf8CodePoints2;
            } else {
                int other$iv = b0 >> 5;
                int i2 = 2;
                if (other$iv == -2) {
                    if (i <= index + 1) {
                        yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                        $i$f$processUtf8CodePoints = $i$f$processUtf8CodePoints2;
                        i2 = 1;
                    } else {
                        byte b0$iv = $receiver[index];
                        byte b1$iv = $receiver[index + 1];
                        if ((b1$iv & 192) == 128) {
                            int codePoint$iv = (b1$iv ^ ByteCompanionObject.MIN_VALUE) ^ (b0$iv << 6);
                            if (codePoint$iv < 128) {
                                $i$f$processUtf8CodePoints = $i$f$processUtf8CodePoints2;
                                yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                            } else {
                                $i$f$processUtf8CodePoints = $i$f$processUtf8CodePoints2;
                                yield.invoke(Integer.valueOf(codePoint$iv));
                            }
                        } else {
                            yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                            $i$f$processUtf8CodePoints = $i$f$processUtf8CodePoints2;
                            i2 = 1;
                        }
                    }
                    index += i2;
                } else {
                    $i$f$processUtf8CodePoints = $i$f$processUtf8CodePoints2;
                    boolean z3 = z;
                    int other$iv2 = b0 >> 4;
                    if (other$iv2 == -2) {
                        if (i <= index + 2) {
                            yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                            int it = index + 1;
                            if (i > it) {
                                int byte$iv$iv = $receiver[index + 1];
                                int other$iv$iv$iv = 192 & byte$iv$iv;
                                if (!(other$iv$iv$iv == 128)) {
                                    i2 = 1;
                                }
                            }
                        } else {
                            byte b0$iv2 = $receiver[index];
                            byte b1$iv2 = $receiver[index + 1];
                            if ((b1$iv2 & 192) == 128) {
                                byte b2$iv = $receiver[index + 2];
                                int i3 = Opcodes.CHECKCAST;
                                if ((b2$iv & 192) == 128) {
                                    int codePoint$iv2 = (((-123008) ^ b2$iv) ^ (b1$iv2 << 6)) ^ (b0$iv2 << 12);
                                    if (codePoint$iv2 < 2048) {
                                        yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                    } else {
                                        int it2 = (55296 <= codePoint$iv2 && 57343 >= codePoint$iv2) ? REPLACEMENT_CODE_POINT : codePoint$iv2;
                                        yield.invoke(Integer.valueOf(it2));
                                        i3 = codePoint$iv2;
                                    }
                                    i2 = 3;
                                } else {
                                    yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                }
                            } else {
                                yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                i2 = 1;
                            }
                        }
                        index += i2;
                        z = z3;
                    } else {
                        int other$iv3 = b0 >> 3;
                        if (other$iv3 == -2) {
                            boolean z4 = z2;
                            if (i <= index + 3) {
                                yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                int it3 = index + 1;
                                if (i > it3) {
                                    int byte$iv$iv2 = $receiver[index + 1];
                                    int other$iv$iv$iv2 = 192 & byte$iv$iv2;
                                    byte byte$iv$iv3 = other$iv$iv$iv2 == 128 ? (byte) 1 : (byte) 0;
                                    if (byte$iv$iv3 == 0) {
                                        i2 = 1;
                                    } else if (i > index + 2) {
                                        int byte$iv$iv4 = $receiver[index + 2];
                                        int other$iv$iv$iv3 = 192 & byte$iv$iv4;
                                        if (other$iv$iv$iv3 == 128) {
                                            i2 = 3;
                                        }
                                    }
                                }
                            } else {
                                byte b0$iv3 = $receiver[index];
                                byte b1$iv3 = $receiver[index + 1];
                                if ((b1$iv3 & 192) == 128) {
                                    byte b2$iv2 = $receiver[index + 2];
                                    if ((b2$iv2 & 192) == 128) {
                                        byte b3$iv = $receiver[index + 3];
                                        if ((b3$iv & 192) == 128) {
                                            int codePoint$iv3 = (((3678080 ^ b3$iv) ^ (b2$iv2 << 6)) ^ (b1$iv3 << 12)) ^ (b0$iv3 << 18);
                                            if (codePoint$iv3 > 1114111) {
                                                yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                            } else if ((55296 <= codePoint$iv3 && 57343 >= codePoint$iv3) || codePoint$iv3 < 65536) {
                                                int it4 = REPLACEMENT_CODE_POINT;
                                                yield.invoke(Integer.valueOf(it4));
                                            } else {
                                                yield.invoke(Integer.valueOf(codePoint$iv3));
                                            }
                                            i2 = 4;
                                        } else {
                                            yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                            i2 = 3;
                                        }
                                    } else {
                                        yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                    }
                                } else {
                                    yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                    i2 = 1;
                                }
                            }
                            index += i2;
                            z2 = z4;
                            z = z3;
                        } else {
                            yield.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                            index++;
                            z = z3;
                        }
                    }
                }
            }
            receiver = $receiver;
            i = endIndex;
            $i$f$processUtf8CodePoints2 = $i$f$processUtf8CodePoints;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:139:0x0314 A[PHI: r8
  0x0314: PHI (r8v7 'codePoint' int) = (r8v4 'codePoint' int), (r8v5 'codePoint' int), (r8v9 'codePoint' int) binds: [B:148:0x034d, B:145:0x0346, B:138:0x0312] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01e4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void processUtf16Chars(byte[] $receiver, int beginIndex, int endIndex, Function1<? super Character, Unit> yield) {
        int $i$f$processUtf16Chars;
        int codePoint;
        byte[] receiver = $receiver;
        int $i$f$processUtf16Chars2 = 0;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(yield, "yield");
        int index = beginIndex;
        boolean z = false;
        while (index < endIndex) {
            byte b0 = receiver[index];
            if (b0 >= 0) {
                yield.invoke(Character.valueOf((char) b0));
                index++;
                while (index < endIndex && receiver[index] >= 0) {
                    yield.invoke(Character.valueOf((char) receiver[index]));
                    index++;
                }
                $i$f$processUtf16Chars = $i$f$processUtf16Chars2;
            } else {
                int other$iv = b0 >> 5;
                int i = 2;
                if (other$iv == -2) {
                    if (endIndex <= index + 1) {
                        yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                        $i$f$processUtf16Chars = $i$f$processUtf16Chars2;
                        i = 1;
                    } else {
                        byte b0$iv = $receiver[index];
                        byte b1$iv = $receiver[index + 1];
                        if ((b1$iv & 192) == 128) {
                            int codePoint$iv = (b1$iv ^ ByteCompanionObject.MIN_VALUE) ^ (b0$iv << 6);
                            if (codePoint$iv < 128) {
                                $i$f$processUtf16Chars = $i$f$processUtf16Chars2;
                                yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                            } else {
                                $i$f$processUtf16Chars = $i$f$processUtf16Chars2;
                                yield.invoke(Character.valueOf((char) codePoint$iv));
                            }
                        } else {
                            yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                            $i$f$processUtf16Chars = $i$f$processUtf16Chars2;
                            i = 1;
                        }
                    }
                    index += i;
                } else {
                    $i$f$processUtf16Chars = $i$f$processUtf16Chars2;
                    boolean z2 = z;
                    int other$iv2 = b0 >> 4;
                    if (other$iv2 == -2) {
                        if (endIndex <= index + 2) {
                            yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                            int it = index + 1;
                            if (endIndex > it) {
                                int byte$iv$iv = $receiver[index + 1];
                                int other$iv$iv$iv = 192 & byte$iv$iv;
                                if (!(other$iv$iv$iv == 128)) {
                                    i = 1;
                                }
                            }
                        } else {
                            byte b0$iv2 = $receiver[index];
                            byte b1$iv2 = $receiver[index + 1];
                            if ((b1$iv2 & 192) == 128) {
                                byte b2$iv = $receiver[index + 2];
                                int i2 = Opcodes.CHECKCAST;
                                if ((b2$iv & 192) == 128) {
                                    int codePoint$iv2 = (((-123008) ^ b2$iv) ^ (b1$iv2 << 6)) ^ (b0$iv2 << 12);
                                    if (codePoint$iv2 < 2048) {
                                        yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                                    } else {
                                        int it2 = (55296 <= codePoint$iv2 && 57343 >= codePoint$iv2) ? REPLACEMENT_CODE_POINT : codePoint$iv2;
                                        yield.invoke(Character.valueOf((char) it2));
                                        i2 = codePoint$iv2;
                                    }
                                    i = 3;
                                } else {
                                    yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                                }
                            } else {
                                yield.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                                i = 1;
                            }
                        }
                        index += i;
                        z = z2;
                    } else {
                        int other$iv3 = b0 >> 3;
                        if (other$iv3 == -2) {
                            if (endIndex <= index + 3) {
                                if (65533 != 65533) {
                                    yield.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                    yield.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                                } else {
                                    yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                }
                                if (endIndex > index + 1) {
                                    int byte$iv$iv2 = $receiver[index + 1];
                                    int other$iv$iv$iv2 = 192 & byte$iv$iv2;
                                    byte byte$iv$iv3 = other$iv$iv$iv2 == 128 ? (byte) 1 : (byte) 0;
                                    if (byte$iv$iv3 == 0) {
                                        i = 1;
                                    } else if (endIndex > index + 2) {
                                        int byte$iv$iv4 = $receiver[index + 2];
                                        int other$iv$iv$iv3 = 192 & byte$iv$iv4;
                                        if (other$iv$iv$iv3 == 128) {
                                            i = 3;
                                        }
                                    }
                                }
                            } else {
                                byte b0$iv3 = $receiver[index];
                                byte b1$iv3 = $receiver[index + 1];
                                if ((b1$iv3 & 192) == 128) {
                                    byte b2$iv2 = $receiver[index + 2];
                                    if ((b2$iv2 & 192) == 128) {
                                        byte b3$iv = $receiver[index + 3];
                                        if ((b3$iv & 192) == 128) {
                                            int codePoint$iv3 = (((3678080 ^ b3$iv) ^ (b2$iv2 << 6)) ^ (b1$iv3 << 12)) ^ (b0$iv3 << 18);
                                            if (codePoint$iv3 > 1114111) {
                                                if (65533 != 65533) {
                                                    yield.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                                    yield.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                                                } else {
                                                    yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                                }
                                            } else if (55296 <= codePoint$iv3 && 57343 >= codePoint$iv3) {
                                                codePoint = REPLACEMENT_CODE_POINT;
                                                if (65533 != 65533) {
                                                    yield.invoke(Character.valueOf((char) ((codePoint >>> 10) + HIGH_SURROGATE_HEADER)));
                                                    yield.invoke(Character.valueOf((char) ((codePoint & 1023) + LOG_SURROGATE_HEADER)));
                                                } else {
                                                    yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                                }
                                            } else if (codePoint$iv3 < 65536) {
                                                codePoint = REPLACEMENT_CODE_POINT;
                                                if (65533 != 65533) {
                                                }
                                            } else {
                                                codePoint = codePoint$iv3;
                                                if (codePoint != 65533) {
                                                }
                                            }
                                            i = 4;
                                        } else {
                                            if (65533 != 65533) {
                                                yield.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                                yield.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                                            } else {
                                                yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                            }
                                            i = 3;
                                        }
                                    } else if (65533 != 65533) {
                                        yield.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                        yield.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                                    } else {
                                        yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                    }
                                } else {
                                    if (65533 != 65533) {
                                        yield.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                        yield.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                                    } else {
                                        yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                    }
                                    i = 1;
                                }
                            }
                            index += i;
                            z = z2;
                        } else {
                            yield.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                            index++;
                            z = z2;
                        }
                    }
                }
            }
            receiver = $receiver;
            $i$f$processUtf16Chars2 = $i$f$processUtf16Chars;
        }
    }

    public static final int process2Utf8Bytes(byte[] receiver, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(yield, "yield");
        int i = beginIndex + 1;
        Integer numValueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (endIndex <= i) {
            yield.invoke(numValueOf);
            return 1;
        }
        byte b0 = receiver[beginIndex];
        int b1 = receiver[beginIndex + 1];
        int $i$f$and = b1 & Opcodes.CHECKCAST;
        if (!($i$f$and == 128)) {
            yield.invoke(numValueOf);
            return 1;
        }
        int codePoint = (b1 ^ MASK_2BYTES) ^ (b0 << 6);
        if (codePoint < 128) {
            yield.invoke(numValueOf);
            return 2;
        }
        yield.invoke(Integer.valueOf(codePoint));
        return 2;
    }

    public static final int process3Utf8Bytes(byte[] receiver, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(yield, "yield");
        int i = beginIndex + 2;
        Integer numValueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (endIndex <= i) {
            yield.invoke(numValueOf);
            if (endIndex > beginIndex + 1) {
                int byte$iv = receiver[beginIndex + 1];
                int $i$f$and = byte$iv & Opcodes.CHECKCAST;
                if ($i$f$and == 128) {
                    return 2;
                }
            }
            return 1;
        }
        byte b0 = receiver[beginIndex];
        int b1 = receiver[beginIndex + 1];
        int other$iv$iv = 192 & b1;
        if (!(other$iv$iv == 128)) {
            yield.invoke(numValueOf);
            return 1;
        }
        int b2 = receiver[beginIndex + 2];
        int $i$f$and2 = b2 & Opcodes.CHECKCAST;
        if (!($i$f$and2 == 128)) {
            yield.invoke(numValueOf);
            return 2;
        }
        int codePoint = (((-123008) ^ b2) ^ (b1 << 6)) ^ (b0 << 12);
        if (codePoint < 2048) {
            yield.invoke(numValueOf);
            return 3;
        }
        if (55296 <= codePoint && 57343 >= codePoint) {
            yield.invoke(numValueOf);
            return 3;
        }
        yield.invoke(Integer.valueOf(codePoint));
        return 3;
    }

    public static final int process4Utf8Bytes(byte[] receiver, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(yield, "yield");
        int i = beginIndex + 3;
        Integer numValueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (endIndex <= i) {
            yield.invoke(numValueOf);
            if (endIndex > beginIndex + 1) {
                int byte$iv = receiver[beginIndex + 1];
                int other$iv$iv = 192 & byte$iv;
                byte byte$iv2 = other$iv$iv == 128 ? (byte) 1 : (byte) 0;
                if (byte$iv2 != 0) {
                    if (endIndex > beginIndex + 2) {
                        int byte$iv3 = receiver[beginIndex + 2];
                        int $i$f$and = byte$iv3 & Opcodes.CHECKCAST;
                        if ($i$f$and == 128) {
                            return 3;
                        }
                    }
                    return 2;
                }
            }
            return 1;
        }
        byte b0 = receiver[beginIndex];
        int b1 = receiver[beginIndex + 1];
        int other$iv$iv2 = b1 & Opcodes.CHECKCAST;
        if (!(other$iv$iv2 == 128)) {
            yield.invoke(numValueOf);
            return 1;
        }
        int b2 = receiver[beginIndex + 2];
        int $i$f$and2 = b2 & Opcodes.CHECKCAST;
        if (!($i$f$and2 == 128)) {
            yield.invoke(numValueOf);
            return 2;
        }
        int b3 = receiver[beginIndex + 3];
        int other$iv$iv3 = b3 & Opcodes.CHECKCAST;
        if (!(other$iv$iv3 == 128)) {
            yield.invoke(numValueOf);
            return 3;
        }
        int codePoint = (((3678080 ^ b3) ^ (b2 << 6)) ^ (b1 << 12)) ^ (b0 << 18);
        if (codePoint > 1114111) {
            yield.invoke(numValueOf);
            return 4;
        }
        if (55296 <= codePoint && 57343 >= codePoint) {
            yield.invoke(numValueOf);
            return 4;
        }
        if (codePoint < 65536) {
            yield.invoke(numValueOf);
            return 4;
        }
        yield.invoke(Integer.valueOf(codePoint));
        return 4;
    }
}