package com.alibaba.fastjson.util;

import com.alibaba.fastjson.asm.Opcodes;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.UByte;
import okio.Utf8;

/* loaded from: classes.dex */
public class UTF8Decoder extends CharsetDecoder {
    private static final Charset charset = Charset.forName("UTF-8");

    public UTF8Decoder() {
        super(charset, 1.0f, 1.0f);
    }

    private static boolean isNotContinuation(int b) {
        return (b & Opcodes.CHECKCAST) != 128;
    }

    private static boolean isMalformed2(int b1, int b2) {
        return (b1 & 30) == 0 || (b2 & Opcodes.CHECKCAST) != 128;
    }

    private static boolean isMalformed3(int b1, int b2, int b3) {
        return ((b1 != -32 || (b2 & 224) != 128) && (b2 & Opcodes.CHECKCAST) == 128 && (b3 & Opcodes.CHECKCAST) == 128) ? false : true;
    }

    private static boolean isMalformed4(int b2, int b3, int b4) {
        return ((b2 & Opcodes.CHECKCAST) == 128 && (b3 & Opcodes.CHECKCAST) == 128 && (b4 & Opcodes.CHECKCAST) == 128) ? false : true;
    }

    private static CoderResult lookupN(ByteBuffer src, int n) {
        for (int i = 1; i < n; i++) {
            if (isNotContinuation(src.get())) {
                return CoderResult.malformedForLength(i);
            }
        }
        return CoderResult.malformedForLength(n);
    }

    public static CoderResult malformedN(ByteBuffer src, int nb) {
        switch (nb) {
            case 1:
                int b1 = src.get();
                if ((b1 >> 2) == -2) {
                    return src.remaining() < 4 ? CoderResult.UNDERFLOW : lookupN(src, 5);
                }
                if ((b1 >> 1) == -2) {
                    if (src.remaining() < 5) {
                        return CoderResult.UNDERFLOW;
                    }
                    return lookupN(src, 6);
                }
                return CoderResult.malformedForLength(1);
            case 2:
                return CoderResult.malformedForLength(1);
            case 3:
                int b12 = src.get();
                int b2 = src.get();
                return CoderResult.malformedForLength(((b12 == -32 && (b2 & 224) == 128) || isNotContinuation(b2)) ? 1 : 2);
            case 4:
                int b13 = src.get() & 255;
                int b22 = src.get() & UByte.MAX_VALUE;
                if (b13 > 244 || ((b13 == 240 && (b22 < 144 || b22 > 191)) || ((b13 == 244 && (b22 & 240) != 128) || isNotContinuation(b22)))) {
                    return CoderResult.malformedForLength(1);
                }
                return isNotContinuation(src.get()) ? CoderResult.malformedForLength(2) : CoderResult.malformedForLength(3);
            default:
                throw new IllegalStateException();
        }
    }

    private static CoderResult malformed(ByteBuffer src, int sp, CharBuffer dst, int dp, int nb) {
        src.position(sp - src.arrayOffset());
        CoderResult cr = malformedN(src, nb);
        src.position(sp);
        dst.position(dp);
        return cr;
    }

    private static CoderResult xflow(Buffer src, int sp, int sl, Buffer dst, int dp, int nb) {
        src.position(sp);
        dst.position(dp);
        return (nb == 0 || sl - sp < nb) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
    }

    private CoderResult decodeArrayLoop(ByteBuffer src, CharBuffer dst) {
        byte[] srcArray = src.array();
        int srcPosition = src.arrayOffset() + src.position();
        int srcLength = src.arrayOffset() + src.limit();
        char[] destArray = dst.array();
        int destPosition = dst.arrayOffset() + dst.position();
        int destLength = dst.arrayOffset() + dst.limit();
        int destLengthASCII = destPosition + Math.min(srcLength - srcPosition, destLength - destPosition);
        while (destPosition < destLengthASCII && srcArray[srcPosition] >= 0) {
            destArray[destPosition] = (char) srcArray[srcPosition];
            destPosition++;
            srcPosition++;
        }
        int srcPosition2 = srcPosition;
        int destPosition2 = destPosition;
        while (srcPosition2 < srcLength) {
            int b1 = srcArray[srcPosition2];
            if (b1 >= 0) {
                if (destPosition2 >= destLength) {
                    return xflow(src, srcPosition2, srcLength, dst, destPosition2, 1);
                }
                destArray[destPosition2] = (char) b1;
                srcPosition2++;
                destPosition2++;
            } else if ((b1 >> 5) == -2) {
                if (srcLength - srcPosition2 < 2 || destPosition2 >= destLength) {
                    return xflow(src, srcPosition2, srcLength, dst, destPosition2, 2);
                }
                int b2 = srcArray[srcPosition2 + 1];
                if (isMalformed2(b1, b2)) {
                    return malformed(src, srcPosition2, dst, destPosition2, 2);
                }
                destArray[destPosition2] = (char) (((b1 << 6) ^ b2) ^ Utf8.MASK_2BYTES);
                srcPosition2 += 2;
                destPosition2++;
            } else if ((b1 >> 4) == -2) {
                if (srcLength - srcPosition2 < 3 || destPosition2 >= destLength) {
                    return xflow(src, srcPosition2, srcLength, dst, destPosition2, 3);
                }
                int b22 = srcArray[srcPosition2 + 1];
                int b3 = srcArray[srcPosition2 + 2];
                if (isMalformed3(b1, b22, b3)) {
                    return malformed(src, srcPosition2, dst, destPosition2, 3);
                }
                destArray[destPosition2] = (char) ((((b1 << 12) ^ (b22 << 6)) ^ b3) ^ 8064);
                srcPosition2 += 3;
                destPosition2++;
            } else {
                if ((b1 >> 3) != -2) {
                    return malformed(src, srcPosition2, dst, destPosition2, 1);
                }
                if (srcLength - srcPosition2 < 4 || destLength - destPosition2 < 2) {
                    return xflow(src, srcPosition2, srcLength, dst, destPosition2, 4);
                }
                int b23 = srcArray[srcPosition2 + 1];
                int b32 = srcArray[srcPosition2 + 2];
                int b4 = srcArray[srcPosition2 + 3];
                int uc = ((b1 & 7) << 18) | ((b23 & 63) << 12) | ((b32 & 63) << 6) | (b4 & 63);
                if (isMalformed4(b23, b32, b4) || uc < 65536 || uc > 1114111) {
                    return malformed(src, srcPosition2, dst, destPosition2, 4);
                }
                int destPosition3 = destPosition2 + 1;
                destArray[destPosition2] = (char) ((((uc - 65536) >> 10) & 1023) | 55296);
                destArray[destPosition3] = (char) (((uc - 65536) & 1023) | Utf8.LOG_SURROGATE_HEADER);
                srcPosition2 += 4;
                destPosition2 = destPosition3 + 1;
            }
        }
        return xflow(src, srcPosition2, srcLength, dst, destPosition2, 0);
    }

    @Override // java.nio.charset.CharsetDecoder
    protected CoderResult decodeLoop(ByteBuffer src, CharBuffer dst) {
        return decodeArrayLoop(src, dst);
    }
}