package okio.internal;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import okio.Utf8;

/* compiled from: -Utf8.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0002*\u00020\u0001¨\u0006\u0004"}, d2 = {"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "jvm"}, k = 2, mv = {1, 1, 11})
/* loaded from: classes.dex */
public final class _Utf8Kt {
    /* JADX WARN: Removed duplicated region for block: B:42:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x022c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final String commonToUtf8String(byte[] receiver) {
        int beginIndex$iv;
        int $i$f$processUtf16Chars;
        int endIndex$iv;
        int $i$f$process4Utf8Bytes;
        int length;
        int c;
        int length2;
        int c2;
        int c3;
        int $i$f$process3Utf8Bytes;
        int length3;
        int length4;
        int $i$f$process2Utf8Bytes;
        int length5;
        int length6;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        char[] chars = new char[receiver.length];
        int length7 = 0;
        int beginIndex$iv2 = 0;
        int endIndex$iv2 = receiver.length;
        char c4 = 0;
        int $i$f$processUtf16Chars2 = 0;
        int length8 = 0;
        int length9 = 0;
        int $i$f$isUtf8Continuation = 0;
        int i = 0;
        int codePoint$iv$iv = 0;
        int i2 = 0;
        while (length8 < endIndex$iv2) {
            byte b0$iv = receiver[length8];
            if (b0$iv >= 0) {
                beginIndex$iv = beginIndex$iv2;
                char c5 = (char) b0$iv;
                int length10 = length7 + 1;
                chars[length7] = c5;
                length8++;
                while (length8 < endIndex$iv2 && receiver[length8] >= 0) {
                    int index$iv = length8 + 1;
                    char c6 = (char) receiver[length8];
                    chars[length10] = c6;
                    length10++;
                    length8 = index$iv;
                }
                endIndex$iv = endIndex$iv2;
                $i$f$processUtf16Chars = $i$f$processUtf16Chars2;
                length7 = length10;
            } else {
                beginIndex$iv = beginIndex$iv2;
                int other$iv$iv = b0$iv >> 5;
                $i$f$processUtf16Chars = $i$f$processUtf16Chars2;
                int i3 = 2;
                if (other$iv$iv == -2) {
                    int $i$f$process2Utf8Bytes2 = $i$f$isUtf8Continuation;
                    if (endIndex$iv2 <= length8 + 1) {
                        char c7 = c4;
                        char c8 = (char) Utf8.REPLACEMENT_CODE_POINT;
                        length6 = length7 + 1;
                        chars[length7] = c8;
                        $i$f$process2Utf8Bytes = $i$f$process2Utf8Bytes2;
                        c4 = c7;
                        i3 = 1;
                    } else {
                        byte b0$iv$iv = receiver[length8];
                        byte b1$iv$iv = receiver[length8 + 1];
                        $i$f$process2Utf8Bytes = $i$f$process2Utf8Bytes2;
                        if ((b1$iv$iv & 192) == 128) {
                            int codePoint$iv$iv2 = (b1$iv$iv ^ ByteCompanionObject.MIN_VALUE) ^ (b0$iv$iv << 6);
                            if (codePoint$iv$iv2 < 128) {
                                char $i$a$1$process2Utf8Bytes = c4;
                                char c9 = (char) Utf8.REPLACEMENT_CODE_POINT;
                                length5 = length7 + 1;
                                chars[length7] = c9;
                                c4 = $i$a$1$process2Utf8Bytes;
                            } else {
                                char c10 = (char) codePoint$iv$iv2;
                                length5 = length7 + 1;
                                chars[length7] = c10;
                            }
                            length6 = length5;
                        } else {
                            char c11 = (char) Utf8.REPLACEMENT_CODE_POINT;
                            chars[length7] = c11;
                            i3 = 1;
                            length6 = length7 + 1;
                        }
                    }
                    length8 += i3;
                    endIndex$iv = endIndex$iv2;
                    length7 = length6;
                    $i$f$isUtf8Continuation = $i$f$process2Utf8Bytes;
                } else {
                    int other$iv$iv2 = b0$iv >> 4;
                    if (other$iv$iv2 == -2) {
                        int $i$f$process3Utf8Bytes2 = i;
                        if (endIndex$iv2 <= length8 + 2) {
                            char c12 = c4;
                            char c13 = (char) Utf8.REPLACEMENT_CODE_POINT;
                            length4 = length7 + 1;
                            chars[length7] = c13;
                            if (endIndex$iv2 > length8 + 1) {
                                int byte$iv$iv$iv = receiver[length8 + 1];
                                int other$iv$iv$iv$iv = 192 & byte$iv$iv$iv;
                                if (other$iv$iv$iv$iv == 128) {
                                    $i$f$process3Utf8Bytes = $i$f$process3Utf8Bytes2;
                                    c4 = c12;
                                } else {
                                    $i$f$process3Utf8Bytes = $i$f$process3Utf8Bytes2;
                                    c4 = c12;
                                    i3 = 1;
                                }
                            }
                        } else {
                            byte b0$iv$iv2 = receiver[length8];
                            int b1$iv$iv2 = receiver[length8 + 1];
                            $i$f$process3Utf8Bytes = $i$f$process3Utf8Bytes2;
                            int i4 = codePoint$iv$iv;
                            if ((b1$iv$iv2 & Opcodes.CHECKCAST) == 128) {
                                byte b2$iv$iv = receiver[length8 + 2];
                                codePoint$iv$iv = Opcodes.CHECKCAST;
                                int i5 = $i$f$isUtf8Continuation;
                                if ((b2$iv$iv & 192) == 128) {
                                    int codePoint$iv$iv3 = (((-123008) ^ b2$iv$iv) ^ (b1$iv$iv2 << 6)) ^ (b0$iv$iv2 << 12);
                                    if (codePoint$iv$iv3 < 2048) {
                                        char $i$a$2$process3Utf8Bytes = c4;
                                        char c14 = (char) Utf8.REPLACEMENT_CODE_POINT;
                                        chars[length7] = c14;
                                        c4 = $i$a$2$process3Utf8Bytes;
                                        length4 = length7 + 1;
                                    } else {
                                        if (55296 <= codePoint$iv$iv3 && 57343 >= codePoint$iv$iv3) {
                                            char c15 = (char) Utf8.REPLACEMENT_CODE_POINT;
                                            length3 = length7 + 1;
                                            chars[length7] = c15;
                                        } else {
                                            char c16 = (char) codePoint$iv$iv3;
                                            length3 = length7 + 1;
                                            chars[length7] = c16;
                                        }
                                        codePoint$iv$iv = codePoint$iv$iv3;
                                        length4 = length3;
                                    }
                                    $i$f$isUtf8Continuation = i5;
                                    i3 = 3;
                                } else {
                                    char c17 = (char) Utf8.REPLACEMENT_CODE_POINT;
                                    length4 = length7 + 1;
                                    chars[length7] = c17;
                                    $i$f$isUtf8Continuation = i5;
                                }
                            } else {
                                char c18 = (char) Utf8.REPLACEMENT_CODE_POINT;
                                length4 = length7 + 1;
                                chars[length7] = c18;
                                codePoint$iv$iv = i4;
                                i3 = 1;
                            }
                        }
                        length8 += i3;
                        endIndex$iv = endIndex$iv2;
                        length7 = length4;
                        i = $i$f$process3Utf8Bytes;
                    } else {
                        int other$iv$iv3 = b0$iv >> 3;
                        if (other$iv$iv3 == -2) {
                            int $i$f$process4Utf8Bytes2 = i2;
                            if (endIndex$iv2 <= length8 + 3) {
                                if (65533 != 65533) {
                                    char c19 = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                    int length11 = length7 + 1;
                                    chars[length7] = c19;
                                    char c20 = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                    c2 = length9;
                                    length2 = length11 + 1;
                                    chars[length11] = c20;
                                } else {
                                    int length12 = length7 + 1;
                                    chars[length7] = Utf8.REPLACEMENT_CHARACTER;
                                    c2 = length9;
                                    length2 = length12;
                                }
                                if (endIndex$iv2 > length8 + 1) {
                                    int byte$iv$iv$iv2 = receiver[length8 + 1];
                                    int other$iv$iv$iv$iv2 = byte$iv$iv$iv2 & Opcodes.CHECKCAST;
                                    if (!(other$iv$iv$iv$iv2 == 128)) {
                                        endIndex$iv = endIndex$iv2;
                                        $i$f$process4Utf8Bytes = $i$f$process4Utf8Bytes2;
                                        i3 = 1;
                                    } else if (endIndex$iv2 > length8 + 2) {
                                        int byte$iv$iv$iv3 = receiver[length8 + 2];
                                        int other$iv$iv$iv$iv3 = byte$iv$iv$iv3 & Opcodes.CHECKCAST;
                                        if (other$iv$iv$iv$iv3 == 128) {
                                            endIndex$iv = endIndex$iv2;
                                            $i$f$process4Utf8Bytes = $i$f$process4Utf8Bytes2;
                                            i3 = 3;
                                        } else {
                                            endIndex$iv = endIndex$iv2;
                                            $i$f$process4Utf8Bytes = $i$f$process4Utf8Bytes2;
                                        }
                                    }
                                }
                            } else {
                                byte b0$iv$iv3 = receiver[length8];
                                byte b1$iv$iv3 = receiver[length8 + 1];
                                endIndex$iv = endIndex$iv2;
                                if ((b1$iv$iv3 & 192) == 128) {
                                    int b2$iv$iv2 = receiver[length8 + 2];
                                    $i$f$process4Utf8Bytes = $i$f$process4Utf8Bytes2;
                                    if ((b2$iv$iv2 & Opcodes.CHECKCAST) == 128) {
                                        byte b3$iv$iv = receiver[length8 + 3];
                                        codePoint$iv$iv = b3$iv$iv;
                                        int other$iv$iv$iv$iv4 = codePoint$iv$iv & Opcodes.CHECKCAST;
                                        if (other$iv$iv$iv$iv4 == 128) {
                                            codePoint$iv$iv = (((3678080 ^ b3$iv$iv) ^ (b2$iv$iv2 << 6)) ^ (b1$iv$iv3 << 12)) ^ (b0$iv$iv3 << 18);
                                            if (codePoint$iv$iv > 1114111) {
                                                if (65533 != 65533) {
                                                    char c21 = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                                    int length13 = length7 + 1;
                                                    chars[length7] = c21;
                                                    char c22 = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                                    c3 = length9;
                                                    length2 = length13 + 1;
                                                    chars[length13] = c22;
                                                } else {
                                                    chars[length7] = Utf8.REPLACEMENT_CHARACTER;
                                                    c3 = length9;
                                                    length2 = length7 + 1;
                                                }
                                                c2 = c3;
                                            } else if (55296 <= codePoint$iv$iv && 57343 >= codePoint$iv$iv) {
                                                if (65533 != 65533) {
                                                    char c23 = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                                    int length14 = length7 + 1;
                                                    chars[length7] = c23;
                                                    char c24 = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                                    c = length9;
                                                    length2 = length14 + 1;
                                                    chars[length14] = c24;
                                                    c2 = c;
                                                } else {
                                                    length = length7 + 1;
                                                    chars[length7] = Utf8.REPLACEMENT_CHARACTER;
                                                    c = length9;
                                                    length2 = length;
                                                    c2 = c;
                                                }
                                            } else if (codePoint$iv$iv < 65536) {
                                                if (65533 != 65533) {
                                                    char c25 = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                                    int length15 = length7 + 1;
                                                    chars[length7] = c25;
                                                    char c26 = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                                    c = length9;
                                                    length2 = length15 + 1;
                                                    chars[length15] = c26;
                                                    c2 = c;
                                                } else {
                                                    length = length7 + 1;
                                                    chars[length7] = Utf8.REPLACEMENT_CHARACTER;
                                                    c = length9;
                                                    length2 = length;
                                                    c2 = c;
                                                }
                                            } else if (codePoint$iv$iv != 65533) {
                                                char c27 = (char) ((codePoint$iv$iv >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                                int length16 = length7 + 1;
                                                chars[length7] = c27;
                                                char c28 = (char) ((codePoint$iv$iv & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                                c = length9;
                                                length2 = length16 + 1;
                                                chars[length16] = c28;
                                                c2 = c;
                                            } else {
                                                length = length7 + 1;
                                                chars[length7] = Utf8.REPLACEMENT_CHARACTER;
                                                c = length9;
                                                length2 = length;
                                                c2 = c;
                                            }
                                            i3 = 4;
                                        } else {
                                            if (65533 != 65533) {
                                                char c29 = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                                int length17 = length7 + 1;
                                                chars[length7] = c29;
                                                char c30 = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                                int $i$a$1$processUtf16Chars = length9;
                                                length2 = length17 + 1;
                                                chars[length17] = c30;
                                                c2 = $i$a$1$processUtf16Chars;
                                            } else {
                                                int length18 = length7 + 1;
                                                chars[length7] = Utf8.REPLACEMENT_CHARACTER;
                                                c2 = length9;
                                                length2 = length18;
                                            }
                                            i3 = 3;
                                        }
                                    } else {
                                        if (65533 != 65533) {
                                            char c31 = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                            int length19 = length7 + 1;
                                            chars[length7] = c31;
                                            char c32 = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                            int $i$a$1$processUtf16Chars2 = length9;
                                            length2 = length19 + 1;
                                            chars[length19] = c32;
                                            c2 = $i$a$1$processUtf16Chars2;
                                        } else {
                                            int length20 = length7 + 1;
                                            chars[length7] = Utf8.REPLACEMENT_CHARACTER;
                                            c2 = length9;
                                            length2 = length20;
                                        }
                                        codePoint$iv$iv = 192;
                                    }
                                } else {
                                    if (65533 != 65533) {
                                        char c33 = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                        int length21 = length7 + 1;
                                        chars[length7] = c33;
                                        char c34 = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                        int $i$a$1$processUtf16Chars3 = length9;
                                        length2 = length21 + 1;
                                        chars[length21] = c34;
                                        c2 = $i$a$1$processUtf16Chars3;
                                    } else {
                                        int length22 = length7 + 1;
                                        chars[length7] = Utf8.REPLACEMENT_CHARACTER;
                                        c2 = length9;
                                        length2 = length22;
                                    }
                                    i3 = 1;
                                    $i$f$process4Utf8Bytes = $i$f$process4Utf8Bytes2;
                                }
                            }
                            length8 += i3;
                            i2 = $i$f$process4Utf8Bytes;
                            int i6 = length2;
                            length9 = c2;
                            length7 = i6;
                        } else {
                            endIndex$iv = endIndex$iv2;
                            chars[length7] = Utf8.REPLACEMENT_CHARACTER;
                            length8++;
                            length7++;
                        }
                    }
                }
            }
            beginIndex$iv2 = beginIndex$iv;
            $i$f$processUtf16Chars2 = $i$f$processUtf16Chars;
            endIndex$iv2 = endIndex$iv;
        }
        return new String(chars, 0, length7);
    }

    public static final byte[] commonAsUtf8ToByteArray(String receiver) {
        char cCharAt;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        byte[] bytes = new byte[receiver.length() * 4];
        int length = receiver.length();
        for (int index = 0; index < length; index++) {
            char b0 = receiver.charAt(index);
            if (b0 >= 128) {
                int size = index;
                int endIndex$iv = receiver.length();
                int index$iv = index;
                boolean z = false;
                while (index$iv < endIndex$iv) {
                    char c$iv = receiver.charAt(index$iv);
                    if (c$iv < 128) {
                        byte c = (byte) c$iv;
                        int size2 = size + 1;
                        bytes[size] = c;
                        index$iv++;
                        while (index$iv < endIndex$iv && receiver.charAt(index$iv) < 128) {
                            int index$iv2 = index$iv + 1;
                            byte c2 = (byte) receiver.charAt(index$iv);
                            bytes[size2] = c2;
                            index$iv = index$iv2;
                            size2++;
                        }
                        size = size2;
                    } else if (c$iv < 2048) {
                        byte c3 = (byte) ((c$iv >> 6) | Opcodes.CHECKCAST);
                        int size3 = size + 1;
                        bytes[size] = c3;
                        byte c4 = (byte) ((c$iv & '?') | 128);
                        bytes[size3] = c4;
                        index$iv++;
                        size = size3 + 1;
                    } else if (55296 > c$iv || 57343 < c$iv) {
                        byte c5 = (byte) ((c$iv >> '\f') | 224);
                        int size4 = size + 1;
                        bytes[size] = c5;
                        byte c6 = (byte) (((c$iv >> 6) & 63) | 128);
                        boolean z2 = z;
                        int size5 = size4 + 1;
                        bytes[size4] = c6;
                        byte c7 = (byte) ((c$iv & '?') | 128);
                        bytes[size5] = c7;
                        index$iv++;
                        size = size5 + 1;
                        z = z2;
                    } else if (c$iv > 56319 || endIndex$iv <= index$iv + 1 || 56320 > (cCharAt = receiver.charAt(index$iv + 1)) || 57343 < cCharAt) {
                        bytes[size] = Utf8.REPLACEMENT_BYTE;
                        index$iv++;
                        size++;
                    } else {
                        int codePoint$iv = ((c$iv << '\n') + receiver.charAt(index$iv + 1)) - 56613888;
                        byte c8 = (byte) ((codePoint$iv >> 18) | 240);
                        int size6 = size + 1;
                        bytes[size] = c8;
                        byte c9 = (byte) (((codePoint$iv >> 12) & 63) | 128);
                        int size7 = size6 + 1;
                        bytes[size6] = c9;
                        byte c10 = (byte) (((codePoint$iv >> 6) & 63) | 128);
                        int size8 = size7 + 1;
                        bytes[size7] = c10;
                        byte c11 = (byte) ((codePoint$iv & 63) | 128);
                        bytes[size8] = c11;
                        index$iv += 2;
                        size = size8 + 1;
                    }
                }
                byte[] bArrCopyOf = Arrays.copyOf(bytes, size);
                Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
                return bArrCopyOf;
            }
            bytes[index] = (byte) b0;
        }
        byte[] bArrCopyOf2 = Arrays.copyOf(bytes, receiver.length());
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf2, "java.util.Arrays.copyOf(this, newSize)");
        return bArrCopyOf2;
    }
}