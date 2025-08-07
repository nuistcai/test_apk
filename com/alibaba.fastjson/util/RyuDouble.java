package com.alibaba.fastjson.util;

import java.lang.reflect.Array;
import java.math.BigInteger;

/* loaded from: classes.dex */
public final class RyuDouble {
    private static final int[][] POW5_SPLIT = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 326, 4);
    private static final int[][] POW5_INV_SPLIT = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 291, 4);

    static {
        BigInteger mask = BigInteger.ONE.shiftLeft(31).subtract(BigInteger.ONE);
        BigInteger invMask = BigInteger.ONE.shiftLeft(31).subtract(BigInteger.ONE);
        int i = 0;
        while (i < 326) {
            BigInteger pow = BigInteger.valueOf(5L).pow(i);
            int pow5len = pow.bitLength();
            int expectedPow5Bits = i == 0 ? 1 : (int) ((((i * 23219280) + 10000000) - 1) / 10000000);
            if (expectedPow5Bits != pow5len) {
                throw new IllegalStateException(pow5len + " != " + expectedPow5Bits);
            }
            if (i < POW5_SPLIT.length) {
                for (int j = 0; j < 4; j++) {
                    POW5_SPLIT[i][j] = pow.shiftRight((pow5len - 121) + ((3 - j) * 31)).and(mask).intValue();
                }
            }
            if (i < POW5_INV_SPLIT.length) {
                int j2 = pow5len + 121;
                BigInteger inv = BigInteger.ONE.shiftLeft(j2).divide(pow).add(BigInteger.ONE);
                for (int k = 0; k < 4; k++) {
                    if (k == 0) {
                        POW5_INV_SPLIT[i][k] = inv.shiftRight((3 - k) * 31).intValue();
                    } else {
                        POW5_INV_SPLIT[i][k] = inv.shiftRight((3 - k) * 31).and(invMask).intValue();
                    }
                }
            }
            i++;
        }
    }

    public static String toString(double value) {
        char[] result = new char[24];
        int len = toString(value, result, 0);
        return new String(result, 0, len);
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x0366  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int toString(double value, char[] result, int off) {
        int e2;
        long m2;
        int index;
        long dp;
        long dp2;
        long bits11;
        int e10;
        boolean dmIsTrailingZeros;
        int vplength;
        long output;
        int index2;
        int index3;
        int i;
        int i2;
        int pow5Factor_mp;
        int pow5Factor_mm;
        int pow5Factor_mv;
        if (Double.isNaN(value)) {
            int index4 = off + 1;
            result[off] = 'N';
            int index5 = index4 + 1;
            result[index4] = 'a';
            result[index5] = 'N';
            return (index5 + 1) - off;
        }
        if (value == Double.POSITIVE_INFINITY) {
            int index6 = off + 1;
            result[off] = 'I';
            int index7 = index6 + 1;
            result[index6] = 'n';
            int index8 = index7 + 1;
            result[index7] = 'f';
            int index9 = index8 + 1;
            result[index8] = 'i';
            int index10 = index9 + 1;
            result[index9] = 'n';
            int index11 = index10 + 1;
            result[index10] = 'i';
            int index12 = index11 + 1;
            result[index11] = 't';
            result[index12] = 'y';
            return (index12 + 1) - off;
        }
        if (value == Double.NEGATIVE_INFINITY) {
            int index13 = off + 1;
            result[off] = '-';
            int index14 = index13 + 1;
            result[index13] = 'I';
            int index15 = index14 + 1;
            result[index14] = 'n';
            int index16 = index15 + 1;
            result[index15] = 'f';
            int index17 = index16 + 1;
            result[index16] = 'i';
            int index18 = index17 + 1;
            result[index17] = 'n';
            int index19 = index18 + 1;
            result[index18] = 'i';
            int index20 = index19 + 1;
            result[index19] = 't';
            result[index20] = 'y';
            return (index20 + 1) - off;
        }
        long bits = Double.doubleToLongBits(value);
        if (bits == 0) {
            int index21 = off + 1;
            result[off] = '0';
            int index22 = index21 + 1;
            result[index21] = '.';
            result[index22] = '0';
            return (index22 + 1) - off;
        }
        if (bits == Long.MIN_VALUE) {
            int index23 = off + 1;
            result[off] = '-';
            int index24 = index23 + 1;
            result[index23] = '0';
            int index25 = index24 + 1;
            result[index24] = '.';
            result[index25] = '0';
            return (index25 + 1) - off;
        }
        int ieeeExponent = (int) ((bits >>> 52) & 2047);
        long ieeeMantissa = bits & 4503599627370495L;
        if (ieeeExponent == 0) {
            e2 = -1074;
            m2 = ieeeMantissa;
        } else {
            int e22 = ieeeExponent - 1023;
            e2 = e22 - 52;
            m2 = ieeeMantissa | 4503599627370496L;
        }
        boolean sign = bits < 0;
        boolean even = (m2 & 1) == 0;
        long mv = 4 * m2;
        long mp = (4 * m2) + 2;
        int mmShift = (m2 != 4503599627370496L || ieeeExponent <= 1) ? 1 : 0;
        long mm = ((4 * m2) - 1) - mmShift;
        int e23 = e2 - 2;
        boolean dvIsTrailingZeros = false;
        if (e23 < 0) {
            index = off;
            int q = Math.max(0, ((int) (((-e23) * 6989700) / 10000000)) - 1);
            int i3 = (-e23) - q;
            int k = (i3 == 0 ? 1 : (int) ((((i3 * 23219280) + 10000000) - 1) / 10000000)) - 121;
            int j = q - k;
            int actualShift = (j - 93) - 21;
            if (actualShift < 0) {
                throw new IllegalArgumentException("" + actualShift);
            }
            int[] ints = POW5_SPLIT[i3];
            long mHigh = mv >>> 31;
            long mLow = mv & 2147483647L;
            long bits13 = ints[0] * mHigh;
            long bits03 = ints[0] * mLow;
            long bits12 = ints[1] * mHigh;
            long bits02 = ints[1] * mLow;
            int e24 = ints[2];
            long bits112 = e24 * mHigh;
            long bits01 = ints[2] * mLow;
            long bits122 = ints[3];
            long bits10 = bits122 * mHigh;
            long bits00 = ints[3] * mLow;
            long bits022 = (((((((((((bits00 >>> 31) + bits01) + bits10) >>> 31) + bits02) + bits112) >>> 31) + bits03) + bits12) >>> 21) + (bits13 << 10)) >>> actualShift;
            long bits102 = mp >>> 31;
            long mLow2 = mp & 2147483647L;
            long bits132 = ints[0] * bits102;
            long bits032 = ints[0] * mLow2;
            long bits123 = ints[1] * bits102;
            long bits023 = ints[1] * mLow2;
            dp = bits022;
            long dv = ints[2];
            long bits113 = dv * bits102;
            long bits133 = ints[2];
            long bits012 = bits133 * mLow2;
            long bits124 = ints[3];
            long bits103 = bits124 * bits102;
            long bits002 = ints[3] * mLow2;
            long bits114 = (((((((((((bits002 >>> 31) + bits012) + bits103) >>> 31) + bits023) + bits113) >>> 31) + bits032) + bits123) >>> 21) + (bits132 << 10)) >>> actualShift;
            long bits003 = mm >>> 31;
            long mLow3 = mm & 2147483647L;
            long bits134 = ints[0] * bits003;
            long bits033 = ints[0] * mLow3;
            long bits125 = ints[1] * bits003;
            long bits024 = ints[1] * mLow3;
            dp2 = bits114;
            long bits115 = ints[2] * bits003;
            long bits135 = ints[2];
            long bits013 = bits135 * mLow3;
            long bits126 = ints[3];
            long bits104 = bits126 * bits003;
            long bits004 = ints[3] * mLow3;
            bits11 = (((((((((((bits004 >>> 31) + bits013) + bits104) >>> 31) + bits024) + bits115) >>> 31) + bits033) + bits125) >>> 21) + (bits134 << 10)) >>> actualShift;
            e10 = q + e23;
            if (q > 1) {
                if (q < 63) {
                    dvIsTrailingZeros = (mv & ((1 << (q + (-1))) - 1)) == 0;
                    dmIsTrailingZeros = false;
                } else {
                    dmIsTrailingZeros = false;
                }
            } else {
                dvIsTrailingZeros = true;
                if (!even) {
                    dp2--;
                    dmIsTrailingZeros = false;
                } else {
                    dmIsTrailingZeros = mmShift == 1;
                }
            }
        } else {
            int q2 = Math.max(0, ((int) ((e23 * 3010299) / 10000000)) - 1);
            if (q2 == 0) {
                i2 = 1;
            } else {
                long DOUBLE_MANTISSA_MASK = q2;
                i2 = (int) ((((DOUBLE_MANTISSA_MASK * 23219280) + 10000000) - 1) / 10000000);
            }
            int k2 = (i2 + 122) - 1;
            int actualShift2 = ((((-e23) + q2) + k2) - 93) - 21;
            if (actualShift2 < 0) {
                throw new IllegalArgumentException("" + actualShift2);
            }
            int[] ints2 = POW5_INV_SPLIT[q2];
            long mHigh2 = mv >>> 31;
            long mLow4 = mv & 2147483647L;
            long bits136 = ints2[0] * mHigh2;
            int DOUBLE_EXPONENT_MASK = ints2[0];
            long bits034 = DOUBLE_EXPONENT_MASK * mLow4;
            long bits127 = ints2[1] * mHigh2;
            long bits025 = ints2[1] * mLow4;
            long bits116 = ints2[2] * mHigh2;
            long bits014 = ints2[2] * mLow4;
            long bits105 = ints2[3] * mHigh2;
            index = off;
            long bits137 = ints2[3];
            long bits005 = bits137 * mLow4;
            long bits006 = (((((((((((bits005 >>> 31) + bits014) + bits105) >>> 31) + bits025) + bits116) >>> 31) + bits034) + bits127) >>> 21) + (bits136 << 10)) >>> actualShift2;
            long bits035 = mp >>> 31;
            long mLow5 = mp & 2147483647L;
            long bits138 = ints2[0] * bits035;
            long bits036 = ints2[0] * mLow5;
            long bits128 = ints2[1] * bits035;
            long bits026 = ints2[1] * mLow5;
            long dv2 = ints2[2];
            long bits117 = dv2 * bits035;
            long bits015 = ints2[2] * mLow5;
            long bits129 = ints2[3];
            long bits106 = bits129 * bits035;
            long bits007 = ints2[3] * mLow5;
            long bits118 = (((((((((((bits007 >>> 31) + bits015) + bits106) >>> 31) + bits026) + bits117) >>> 31) + bits036) + bits128) >>> 21) + (bits138 << 10)) >>> actualShift2;
            long bits008 = mm >>> 31;
            long mLow6 = mm & 2147483647L;
            long bits139 = ints2[0] * bits008;
            long bits037 = ints2[0] * mLow6;
            long bits1210 = ints2[1] * bits008;
            long bits027 = ints2[1] * mLow6;
            long dp3 = bits118;
            long bits119 = ints2[2] * bits008;
            long bits016 = ints2[2] * mLow6;
            long bits1211 = ints2[3];
            long bits107 = bits1211 * bits008;
            long bits009 = ints2[3] * mLow6;
            bits11 = (((((((((((bits009 >>> 31) + bits016) + bits107) >>> 31) + bits027) + bits119) >>> 31) + bits037) + bits1210) >>> 21) + (bits139 << 10)) >>> actualShift2;
            e10 = q2;
            if (q2 <= 21) {
                if (mv % 5 == 0) {
                    if (mv % 5 != 0) {
                        pow5Factor_mv = 0;
                    } else if (mv % 25 != 0) {
                        pow5Factor_mv = 1;
                    } else if (mv % 125 != 0) {
                        pow5Factor_mv = 2;
                    } else if (mv % 625 != 0) {
                        pow5Factor_mv = 3;
                    } else {
                        int pow5Factor_mv2 = 4;
                        long v = mv / 625;
                        for (long j2 = 0; v > j2 && v % 5 == j2; j2 = 0) {
                            v /= 5;
                            pow5Factor_mv2++;
                        }
                        pow5Factor_mv = pow5Factor_mv2;
                    }
                    dvIsTrailingZeros = pow5Factor_mv >= q2;
                    dmIsTrailingZeros = false;
                } else if (even) {
                    if (mm % 5 != 0) {
                        pow5Factor_mm = 0;
                    } else if (mm % 25 != 0) {
                        pow5Factor_mm = 1;
                    } else if (mm % 125 != 0) {
                        pow5Factor_mm = 2;
                    } else if (mm % 625 != 0) {
                        pow5Factor_mm = 3;
                    } else {
                        int pow5Factor_mm2 = 4;
                        long v2 = mm / 625;
                        for (long j3 = 0; v2 > j3 && v2 % 5 == j3; j3 = 0) {
                            v2 /= 5;
                            pow5Factor_mm2++;
                        }
                        pow5Factor_mm = pow5Factor_mm2;
                    }
                    dmIsTrailingZeros = pow5Factor_mm >= q2;
                } else {
                    if (mp % 5 != 0) {
                        pow5Factor_mp = 0;
                    } else if (mp % 25 != 0) {
                        pow5Factor_mp = 1;
                    } else if (mp % 125 != 0) {
                        pow5Factor_mp = 2;
                    } else if (mp % 625 != 0) {
                        pow5Factor_mp = 3;
                    } else {
                        int pow5Factor_mp2 = 4;
                        long v3 = mp / 625;
                        for (long j4 = 0; v3 > j4 && v3 % 5 == j4; j4 = 0) {
                            v3 /= 5;
                            pow5Factor_mp2++;
                        }
                        pow5Factor_mp = pow5Factor_mp2;
                    }
                    if (pow5Factor_mp >= q2) {
                        dp3--;
                        dmIsTrailingZeros = false;
                    } else {
                        dmIsTrailingZeros = false;
                    }
                }
                dp2 = dp3;
                dp = bits006;
            }
        }
        if (dp2 >= 1000000000000000000L) {
            vplength = 19;
        } else if (dp2 >= 100000000000000000L) {
            vplength = 18;
        } else if (dp2 >= 10000000000000000L) {
            vplength = 17;
        } else if (dp2 >= 1000000000000000L) {
            vplength = 16;
        } else if (dp2 >= 100000000000000L) {
            vplength = 15;
        } else if (dp2 >= 10000000000000L) {
            vplength = 14;
        } else if (dp2 >= 1000000000000L) {
            vplength = 13;
        } else if (dp2 >= 100000000000L) {
            vplength = 12;
        } else if (dp2 >= 10000000000L) {
            vplength = 11;
        } else if (dp2 >= 1000000000) {
            vplength = 10;
        } else if (dp2 >= 100000000) {
            vplength = 9;
        } else if (dp2 >= 10000000) {
            vplength = 8;
        } else if (dp2 >= 1000000) {
            vplength = 7;
        } else if (dp2 >= 100000) {
            vplength = 6;
        } else if (dp2 >= 10000) {
            vplength = 5;
        } else if (dp2 >= 1000) {
            vplength = 4;
        } else if (dp2 >= 100) {
            vplength = 3;
        } else if (dp2 >= 10) {
            vplength = 2;
        } else {
            vplength = 1;
        }
        int exp = (e10 + vplength) - 1;
        boolean scientificNotation = exp < -3 || exp >= 7;
        int removed = 0;
        int lastRemovedDigit = 0;
        if (dmIsTrailingZeros || dvIsTrailingZeros) {
            while (dp2 / 10 > bits11 / 10 && (dp2 >= 100 || !scientificNotation)) {
                dmIsTrailingZeros &= bits11 % 10 == 0;
                dvIsTrailingZeros &= lastRemovedDigit == 0;
                lastRemovedDigit = (int) (dp % 10);
                dp2 /= 10;
                dp /= 10;
                bits11 /= 10;
                removed++;
            }
            if (dmIsTrailingZeros && even) {
                while (bits11 % 10 == 0 && (dp2 >= 100 || !scientificNotation)) {
                    dvIsTrailingZeros &= lastRemovedDigit == 0;
                    lastRemovedDigit = (int) (dp % 10);
                    dp2 /= 10;
                    dp /= 10;
                    bits11 /= 10;
                    removed++;
                }
            }
            if (dvIsTrailingZeros && lastRemovedDigit == 5 && dp % 2 == 0) {
                lastRemovedDigit = 4;
            }
            output = dp + (((dp != bits11 || (dmIsTrailingZeros && even)) && lastRemovedDigit < 5) ? 0 : 1);
        } else {
            while (dp2 / 10 > bits11 / 10 && (dp2 >= 100 || !scientificNotation)) {
                lastRemovedDigit = (int) (dp % 10);
                dp2 /= 10;
                dp /= 10;
                bits11 /= 10;
                removed++;
            }
            output = dp + ((dp == bits11 || lastRemovedDigit >= 5) ? 1 : 0);
        }
        int olength = vplength - removed;
        if (!sign) {
            index2 = index;
        } else {
            index2 = index + 1;
            result[index] = '-';
        }
        if (scientificNotation) {
            int i4 = 0;
            while (i4 < olength - 1) {
                long dm = bits11;
                long dm2 = output % 10;
                int c = (int) dm2;
                output /= 10;
                result[(index2 + olength) - i4] = (char) (c + 48);
                i4++;
                bits11 = dm;
            }
            result[index2] = (char) ((output % 10) + 48);
            result[index2 + 1] = '.';
            int index26 = index2 + olength + 1;
            if (olength == 1) {
                result[index26] = '0';
                index26++;
            }
            int index27 = index26 + 1;
            result[index26] = 'E';
            if (exp < 0) {
                result[index27] = '-';
                exp = -exp;
                index27++;
            }
            if (exp >= 100) {
                int index28 = index27 + 1;
                i = 48;
                result[index27] = (char) ((exp / 100) + 48);
                exp %= 100;
                index27 = index28 + 1;
                result[index28] = (char) ((exp / 10) + 48);
            } else {
                i = 48;
                if (exp >= 10) {
                    result[index27] = (char) ((exp / 10) + 48);
                    index27++;
                }
            }
            result[index27] = (char) ((exp % 10) + i);
            return (index27 + 1) - off;
        }
        if (exp < 0) {
            int index29 = index2 + 1;
            result[index2] = '0';
            index3 = index29 + 1;
            result[index29] = '.';
            int i5 = -1;
            while (i5 > exp) {
                result[index3] = '0';
                i5--;
                index3++;
            }
            int i6 = index3;
            int i7 = 0;
            while (i7 < olength) {
                result[((i6 + olength) - i7) - 1] = (char) ((output % 10) + 48);
                output /= 10;
                index3++;
                i7++;
                e10 = e10;
                vplength = vplength;
            }
        } else if (exp + 1 >= olength) {
            for (int i8 = 0; i8 < olength; i8++) {
                result[((index2 + olength) - i8) - 1] = (char) ((output % 10) + 48);
                output /= 10;
            }
            int index30 = index2 + olength;
            int i9 = olength;
            while (i9 < exp + 1) {
                result[index30] = '0';
                i9++;
                index30++;
            }
            int i10 = index30 + 1;
            result[index30] = '.';
            index3 = i10 + 1;
            result[i10] = '0';
        } else {
            int current = index2 + 1;
            int i11 = 0;
            while (i11 < olength) {
                if ((olength - i11) - 1 == exp) {
                    result[((current + olength) - i11) - 1] = '.';
                    current--;
                }
                result[((current + olength) - i11) - 1] = (char) ((output % 10) + 48);
                output /= 10;
                i11++;
                exp = exp;
            }
            index3 = olength + 1 + index2;
        }
        int current2 = index3 - off;
        return current2;
    }
}