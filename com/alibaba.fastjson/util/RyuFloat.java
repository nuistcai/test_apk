package com.alibaba.fastjson.util;

/* loaded from: classes.dex */
public final class RyuFloat {
    private static final int[][] POW5_SPLIT = {new int[]{536870912, 0}, new int[]{671088640, 0}, new int[]{838860800, 0}, new int[]{1048576000, 0}, new int[]{655360000, 0}, new int[]{819200000, 0}, new int[]{1024000000, 0}, new int[]{640000000, 0}, new int[]{800000000, 0}, new int[]{1000000000, 0}, new int[]{625000000, 0}, new int[]{781250000, 0}, new int[]{976562500, 0}, new int[]{610351562, 1073741824}, new int[]{762939453, 268435456}, new int[]{953674316, 872415232}, new int[]{596046447, 1619001344}, new int[]{745058059, 1486880768}, new int[]{931322574, 1321730048}, new int[]{582076609, 289210368}, new int[]{727595761, 898383872}, new int[]{909494701, 1659850752}, new int[]{568434188, 1305842176}, new int[]{710542735, 1632302720}, new int[]{888178419, 1503507488}, new int[]{555111512, 671256724}, new int[]{693889390, 839070905}, new int[]{867361737, 2122580455}, new int[]{542101086, 521306416}, new int[]{677626357, 1725374844}, new int[]{847032947, 546105819}, new int[]{1058791184, 145761362}, new int[]{661744490, 91100851}, new int[]{827180612, 1187617888}, new int[]{1033975765, 1484522360}, new int[]{646234853, 1196261931}, new int[]{807793566, 2032198326}, new int[]{1009741958, 1466506084}, new int[]{631088724, 379695390}, new int[]{788860905, 474619238}, new int[]{986076131, 1130144959}, new int[]{616297582, 437905143}, new int[]{770371977, 1621123253}, new int[]{962964972, 415791331}, new int[]{601853107, 1333611405}, new int[]{752316384, 1130143345}, new int[]{940395480, 1412679181}};
    private static final int[][] POW5_INV_SPLIT = {new int[]{268435456, 1}, new int[]{214748364, 1717986919}, new int[]{171798691, 1803886265}, new int[]{137438953, 1013612282}, new int[]{219902325, 1192282922}, new int[]{175921860, 953826338}, new int[]{140737488, 763061070}, new int[]{225179981, 791400982}, new int[]{180143985, 203624056}, new int[]{144115188, 162899245}, new int[]{230584300, 1978625710}, new int[]{184467440, 1582900568}, new int[]{147573952, 1266320455}, new int[]{236118324, 308125809}, new int[]{188894659, 675997377}, new int[]{151115727, 970294631}, new int[]{241785163, 1981968139}, new int[]{193428131, 297084323}, new int[]{154742504, 1955654377}, new int[]{247588007, 1840556814}, new int[]{198070406, 613451992}, new int[]{158456325, 61264864}, new int[]{253530120, 98023782}, new int[]{202824096, 78419026}, new int[]{162259276, 1780722139}, new int[]{259614842, 1990161963}, new int[]{207691874, 733136111}, new int[]{166153499, 1016005619}, new int[]{265845599, 337118801}, new int[]{212676479, 699191770}, new int[]{170141183, 988850146}};

    public static String toString(float value) {
        char[] result = new char[15];
        int len = toString(value, result, 0);
        return new String(result, 0, len);
    }

    public static int toString(float value, char[] result, int off) {
        int e2;
        int m2;
        int dp;
        int dm;
        int e10;
        boolean dpIsTrailingZeros;
        boolean dvIsTrailingZeros;
        boolean dmIsTrailingZeros;
        int dp2;
        int dm2;
        int k;
        int index;
        int dm3;
        int index2 = off;
        if (Float.isNaN(value)) {
            int index3 = index2 + 1;
            result[index2] = 'N';
            int index4 = index3 + 1;
            result[index3] = 'a';
            result[index4] = 'N';
            return (index4 + 1) - off;
        }
        if (value == Float.POSITIVE_INFINITY) {
            int index5 = index2 + 1;
            result[index2] = 'I';
            int index6 = index5 + 1;
            result[index5] = 'n';
            int index7 = index6 + 1;
            result[index6] = 'f';
            int index8 = index7 + 1;
            result[index7] = 'i';
            int index9 = index8 + 1;
            result[index8] = 'n';
            int index10 = index9 + 1;
            result[index9] = 'i';
            int index11 = index10 + 1;
            result[index10] = 't';
            result[index11] = 'y';
            return (index11 + 1) - off;
        }
        if (value == Float.NEGATIVE_INFINITY) {
            int index12 = index2 + 1;
            result[index2] = '-';
            int index13 = index12 + 1;
            result[index12] = 'I';
            int index14 = index13 + 1;
            result[index13] = 'n';
            int index15 = index14 + 1;
            result[index14] = 'f';
            int index16 = index15 + 1;
            result[index15] = 'i';
            int index17 = index16 + 1;
            result[index16] = 'n';
            int index18 = index17 + 1;
            result[index17] = 'i';
            int index19 = index18 + 1;
            result[index18] = 't';
            result[index19] = 'y';
            return (index19 + 1) - off;
        }
        int bits = Float.floatToIntBits(value);
        if (bits == 0) {
            int index20 = index2 + 1;
            result[index2] = '0';
            int index21 = index20 + 1;
            result[index20] = '.';
            result[index21] = '0';
            return (index21 + 1) - off;
        }
        if (bits == Integer.MIN_VALUE) {
            int index22 = index2 + 1;
            result[index2] = '-';
            int index23 = index22 + 1;
            result[index22] = '0';
            int index24 = index23 + 1;
            result[index23] = '.';
            result[index24] = '0';
            return (index24 + 1) - off;
        }
        int ieeeExponent = (bits >> 23) & 255;
        int ieeeMantissa = 8388607 & bits;
        if (ieeeExponent == 0) {
            e2 = -149;
            m2 = ieeeMantissa;
        } else {
            int e22 = ieeeExponent - 127;
            e2 = e22 - 23;
            m2 = ieeeMantissa | 8388608;
        }
        boolean sign = bits < 0;
        boolean even = (m2 & 1) == 0;
        int mv = m2 * 4;
        int i = 2;
        int mp = (m2 * 4) + 2;
        int i2 = m2 * 4;
        if (m2 == 8388608 && ieeeExponent > 1) {
            i = 1;
        }
        int mm = i2 - i;
        int e23 = e2 - 2;
        int lastRemovedDigit = 0;
        if (e23 >= 0) {
            int q = (int) ((e23 * 3010299) / 10000000);
            int k2 = ((q == 0 ? 1 : (int) ((((q * 23219280) + 10000000) - 1) / 10000000)) + 59) - 1;
            int i3 = (-e23) + q + k2;
            long pis0 = POW5_INV_SPLIT[q][0];
            long pis1 = POW5_INV_SPLIT[q][1];
            long LOG10_5_NUMERATOR = mv;
            int dv = (int) (((LOG10_5_NUMERATOR * pis0) + ((mv * pis1) >> 31)) >> (i3 - 31));
            k = dv;
            dp2 = (int) (((mp * pis0) + ((mp * pis1) >> 31)) >> (i3 - 31));
            int dm4 = (int) (((mm * pis0) + ((mm * pis1) >> 31)) >> (i3 - 31));
            if (q != 0 && (dp2 - 1) / 10 <= dm4 / 10) {
                int e = q - 1;
                int l = ((e == 0 ? 1 : (int) ((((e * 23219280) + 10000000) - 1) / 10000000)) + 59) - 1;
                int qx = q - 1;
                int ii = (((-e23) + q) - 1) + l;
                dm3 = dm4;
                long mulPow5InvDivPow2 = ((mv * POW5_INV_SPLIT[qx][0]) + ((mv * POW5_INV_SPLIT[qx][1]) >> 31)) >> (ii - 31);
                lastRemovedDigit = (int) (mulPow5InvDivPow2 % 10);
            } else {
                dm3 = dm4;
            }
            e10 = q;
            int pow5Factor_mp = 0;
            int v = mp;
            while (v > 0 && v % 5 == 0) {
                v /= 5;
                pow5Factor_mp++;
            }
            int pow5Factor_mv = 0;
            int v2 = mv;
            while (v2 > 0 && v2 % 5 == 0) {
                v2 /= 5;
                pow5Factor_mv++;
            }
            int pow5Factor_mm = 0;
            int v3 = mm;
            while (v3 > 0 && v3 % 5 == 0) {
                v3 /= 5;
                pow5Factor_mm++;
            }
            dpIsTrailingZeros = pow5Factor_mp >= q;
            dvIsTrailingZeros = pow5Factor_mv >= q;
            boolean dmIsTrailingZeros2 = pow5Factor_mm >= q;
            dmIsTrailingZeros = dmIsTrailingZeros2;
            dm2 = dm3;
        } else {
            int q2 = (int) (((-e23) * 6989700) / 10000000);
            int i4 = (-e23) - q2;
            int k3 = (i4 == 0 ? 1 : (int) ((((i4 * 23219280) + 10000000) - 1) / 10000000)) - 61;
            int j = q2 - k3;
            long ps0 = POW5_SPLIT[i4][0];
            long ps1 = POW5_SPLIT[i4][1];
            int j31 = j - 31;
            int dv2 = (int) (((mv * ps0) + ((mv * ps1) >> 31)) >> j31);
            int dp3 = (int) (((mp * ps0) + ((mp * ps1) >> 31)) >> j31);
            int dm5 = (int) (((mm * ps0) + ((mm * ps1) >> 31)) >> j31);
            if (q2 == 0 || (dp3 - 1) / 10 > dm5 / 10) {
                dp = dp3;
                dm = dm5;
            } else {
                int e3 = i4 + 1;
                int j2 = (q2 - 1) - ((e3 == 0 ? 1 : (int) ((((e3 * 23219280) + 10000000) - 1) / 10000000)) - 61);
                int ix = i4 + 1;
                dp = dp3;
                dm = dm5;
                long mulPow5divPow2 = ((mv * POW5_SPLIT[ix][0]) + ((mv * POW5_SPLIT[ix][1]) >> 31)) >> (j2 - 31);
                lastRemovedDigit = (int) (mulPow5divPow2 % 10);
            }
            e10 = q2 + e23;
            dpIsTrailingZeros = 1 >= q2;
            dvIsTrailingZeros = q2 < 23 && (((1 << (q2 + (-1))) - 1) & mv) == 0;
            dmIsTrailingZeros = (mm % 2 == 1 ? 0 : 1) >= q2;
            dp2 = dp;
            dm2 = dm;
            k = dv2;
        }
        int dplength = 10;
        int factor = 1000000000;
        while (dplength > 0 && dp2 < factor) {
            factor /= 10;
            dplength--;
        }
        int exp = (e10 + dplength) - 1;
        boolean scientificNotation = exp < -3 || exp >= 7;
        int removed = 0;
        if (dpIsTrailingZeros && !even) {
            dp2--;
        }
        while (true) {
            int mm2 = mm;
            int e24 = e23;
            int e25 = dm2 / 10;
            if (dp2 / 10 <= e25 || (dp2 < 100 && scientificNotation)) {
                break;
            }
            dmIsTrailingZeros &= dm2 % 10 == 0;
            dp2 /= 10;
            lastRemovedDigit = k % 10;
            k /= 10;
            dm2 /= 10;
            removed++;
            mm = mm2;
            e23 = e24;
        }
        if (dmIsTrailingZeros && even) {
            while (dm2 % 10 == 0 && (dp2 >= 100 || !scientificNotation)) {
                dp2 /= 10;
                lastRemovedDigit = k % 10;
                k /= 10;
                dm2 /= 10;
                removed++;
            }
        }
        int lastRemovedDigit2 = lastRemovedDigit;
        int dv3 = k;
        if (dvIsTrailingZeros && lastRemovedDigit2 == 5 && dv3 % 2 == 0) {
            lastRemovedDigit2 = 4;
        }
        int output = dv3 + (((dv3 == dm2 && !(dmIsTrailingZeros && even)) || lastRemovedDigit2 >= 5) ? 1 : 0);
        int olength = dplength - removed;
        if (sign) {
            result[index2] = '-';
            index2++;
        }
        if (scientificNotation) {
            int lastRemovedDigit3 = 0;
            while (true) {
                int dv4 = dv3;
                int dv5 = olength - 1;
                if (lastRemovedDigit3 >= dv5) {
                    break;
                }
                int c = output % 10;
                output /= 10;
                result[(index2 + olength) - lastRemovedDigit3] = (char) (c + 48);
                lastRemovedDigit3++;
                dv3 = dv4;
                dm2 = dm2;
            }
            result[index2] = (char) ((output % 10) + 48);
            result[index2 + 1] = '.';
            int index25 = index2 + olength + 1;
            if (olength == 1) {
                result[index25] = '0';
                index25++;
            }
            int index26 = index25 + 1;
            result[index25] = 'E';
            if (exp < 0) {
                result[index26] = '-';
                exp = -exp;
                index26++;
            }
            if (exp >= 10) {
                result[index26] = (char) ((exp / 10) + 48);
                index26++;
            }
            index = index26 + 1;
            result[index26] = (char) ((exp % 10) + 48);
        } else if (exp < 0) {
            int index27 = index2 + 1;
            result[index2] = '0';
            index = index27 + 1;
            result[index27] = '.';
            int i5 = -1;
            while (i5 > exp) {
                result[index] = '0';
                i5--;
                index++;
            }
            int current = index;
            int i6 = 0;
            while (i6 < olength) {
                int i7 = ((current + olength) - i6) - 1;
                int current2 = current;
                int current3 = (output % 10) + 48;
                result[i7] = (char) current3;
                output /= 10;
                index++;
                i6++;
                current = current2;
            }
        } else if (exp + 1 >= olength) {
            for (int i8 = 0; i8 < olength; i8++) {
                result[((index2 + olength) - i8) - 1] = (char) ((output % 10) + 48);
                output /= 10;
            }
            int index28 = index2 + olength;
            int i9 = olength;
            while (i9 < exp + 1) {
                result[index28] = '0';
                i9++;
                index28++;
            }
            int i10 = index28 + 1;
            result[index28] = '.';
            index = i10 + 1;
            result[i10] = '0';
        } else {
            int current4 = index2 + 1;
            int i11 = 0;
            while (i11 < olength) {
                if ((olength - i11) - 1 == exp) {
                    result[((current4 + olength) - i11) - 1] = '.';
                    current4--;
                }
                int i12 = ((current4 + olength) - i11) - 1;
                int current5 = current4;
                int current6 = (output % 10) + 48;
                result[i12] = (char) current6;
                output /= 10;
                i11++;
                current4 = current5;
            }
            index = olength + 1 + index2;
        }
        int current7 = index - off;
        return current7;
    }
}