package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.asm.Opcodes;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Properties;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.text.Typography;
import okio.Utf8;

/* loaded from: classes.dex */
public class IOUtils {
    public static final char[] ASCII_CHARS;
    public static final char[] CA;
    static final char[] DigitOnes;
    static final char[] DigitTens;
    public static final String FASTJSON_COMPATIBLEWITHFIELDNAME = "fastjson.compatibleWithFieldName";
    public static final String FASTJSON_COMPATIBLEWITHJAVABEAN = "fastjson.compatibleWithJavaBean";
    public static final String FASTJSON_PROPERTIES = "fastjson.properties";
    public static final int[] IA;
    static final char[] digits;
    public static final char[] replaceChars;
    static final int[] sizeTable;
    public static final byte[] specicalFlags_doubleQuotes;
    public static final boolean[] specicalFlags_doubleQuotesFlags;
    public static final byte[] specicalFlags_singleQuotes;
    public static final boolean[] specicalFlags_singleQuotesFlags;
    public static final Properties DEFAULT_PROPERTIES = new Properties();
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final boolean[] firstIdentifierFlags = new boolean[256];
    public static final boolean[] identifierFlags = new boolean[256];

    static {
        for (char c = 0; c < firstIdentifierFlags.length; c = (char) (c + 1)) {
            if (c >= 'A' && c <= 'Z') {
                firstIdentifierFlags[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                firstIdentifierFlags[c] = true;
            } else if (c == '_' || c == '$') {
                firstIdentifierFlags[c] = true;
            }
        }
        for (char c2 = 0; c2 < identifierFlags.length; c2 = (char) (c2 + 1)) {
            if (c2 >= 'A' && c2 <= 'Z') {
                identifierFlags[c2] = true;
            } else if (c2 >= 'a' && c2 <= 'z') {
                identifierFlags[c2] = true;
            } else if (c2 == '_') {
                identifierFlags[c2] = true;
            } else if (c2 >= '0' && c2 <= '9') {
                identifierFlags[c2] = true;
            }
        }
        try {
            loadPropertiesFromFile();
        } catch (Throwable th) {
        }
        specicalFlags_doubleQuotes = new byte[Opcodes.IF_ICMPLT];
        specicalFlags_singleQuotes = new byte[Opcodes.IF_ICMPLT];
        specicalFlags_doubleQuotesFlags = new boolean[Opcodes.IF_ICMPLT];
        specicalFlags_singleQuotesFlags = new boolean[Opcodes.IF_ICMPLT];
        replaceChars = new char[93];
        specicalFlags_doubleQuotes[0] = 4;
        specicalFlags_doubleQuotes[1] = 4;
        specicalFlags_doubleQuotes[2] = 4;
        specicalFlags_doubleQuotes[3] = 4;
        specicalFlags_doubleQuotes[4] = 4;
        specicalFlags_doubleQuotes[5] = 4;
        specicalFlags_doubleQuotes[6] = 4;
        specicalFlags_doubleQuotes[7] = 4;
        specicalFlags_doubleQuotes[8] = 1;
        specicalFlags_doubleQuotes[9] = 1;
        specicalFlags_doubleQuotes[10] = 1;
        specicalFlags_doubleQuotes[11] = 4;
        specicalFlags_doubleQuotes[12] = 1;
        specicalFlags_doubleQuotes[13] = 1;
        specicalFlags_doubleQuotes[34] = 1;
        specicalFlags_doubleQuotes[92] = 1;
        specicalFlags_singleQuotes[0] = 4;
        specicalFlags_singleQuotes[1] = 4;
        specicalFlags_singleQuotes[2] = 4;
        specicalFlags_singleQuotes[3] = 4;
        specicalFlags_singleQuotes[4] = 4;
        specicalFlags_singleQuotes[5] = 4;
        specicalFlags_singleQuotes[6] = 4;
        specicalFlags_singleQuotes[7] = 4;
        specicalFlags_singleQuotes[8] = 1;
        specicalFlags_singleQuotes[9] = 1;
        specicalFlags_singleQuotes[10] = 1;
        specicalFlags_singleQuotes[11] = 4;
        specicalFlags_singleQuotes[12] = 1;
        specicalFlags_singleQuotes[13] = 1;
        specicalFlags_singleQuotes[92] = 1;
        specicalFlags_singleQuotes[39] = 1;
        for (int i = 14; i <= 31; i++) {
            specicalFlags_doubleQuotes[i] = 4;
            specicalFlags_singleQuotes[i] = 4;
        }
        for (int i2 = 127; i2 < 160; i2++) {
            specicalFlags_doubleQuotes[i2] = 4;
            specicalFlags_singleQuotes[i2] = 4;
        }
        for (int i3 = 0; i3 < 161; i3++) {
            specicalFlags_doubleQuotesFlags[i3] = specicalFlags_doubleQuotes[i3] != 0;
            specicalFlags_singleQuotesFlags[i3] = specicalFlags_singleQuotes[i3] != 0;
        }
        replaceChars[0] = '0';
        replaceChars[1] = '1';
        replaceChars[2] = '2';
        replaceChars[3] = '3';
        replaceChars[4] = '4';
        replaceChars[5] = '5';
        replaceChars[6] = '6';
        replaceChars[7] = '7';
        replaceChars[8] = 'b';
        replaceChars[9] = 't';
        replaceChars[10] = 'n';
        replaceChars[11] = 'v';
        replaceChars[12] = 'f';
        replaceChars[13] = 'r';
        replaceChars[34] = Typography.quote;
        replaceChars[39] = '\'';
        replaceChars[47] = '/';
        replaceChars[92] = '\\';
        ASCII_CHARS = new char[]{'0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'A', '0', 'B', '0', 'C', '0', 'D', '0', 'E', '0', 'F', '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9', '1', 'A', '1', 'B', '1', 'C', '1', 'D', '1', 'E', '1', 'F', '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9', '2', 'A', '2', 'B', '2', 'C', '2', 'D', '2', 'E', '2', 'F'};
        digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        DigitTens = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
        DigitOnes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        sizeTable = new int[]{9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, IntCompanionObject.MAX_VALUE};
        CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        IA = new int[256];
        Arrays.fill(IA, -1);
        int iS = CA.length;
        for (int i4 = 0; i4 < iS; i4++) {
            IA[CA[i4]] = i4;
        }
        IA[61] = 0;
    }

    public static String getStringProperty(String name) {
        String prop = null;
        try {
            prop = System.getProperty(name);
        } catch (SecurityException e) {
        }
        return prop == null ? DEFAULT_PROPERTIES.getProperty(name) : prop;
    }

    public static void loadPropertiesFromFile() throws IOException {
        InputStream imputStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() { // from class: com.alibaba.fastjson.util.IOUtils.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.security.PrivilegedAction
            public InputStream run() {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                if (cl != null) {
                    return cl.getResourceAsStream(IOUtils.FASTJSON_PROPERTIES);
                }
                return ClassLoader.getSystemResourceAsStream(IOUtils.FASTJSON_PROPERTIES);
            }
        });
        if (imputStream != null) {
            try {
                DEFAULT_PROPERTIES.load(imputStream);
                imputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public static void close(Closeable x) throws IOException {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
            }
        }
    }

    public static int stringSize(long x) {
        long p = 10;
        for (int i = 1; i < 19; i++) {
            if (x < p) {
                return i;
            }
            p *= 10;
        }
        return 19;
    }

    public static void getChars(long i, int index, char[] buf) {
        int charPos = index;
        char sign = 0;
        if (i < 0) {
            sign = '-';
            i = -i;
        }
        while (i > 2147483647L) {
            long q = i / 100;
            int r = (int) (i - (((q << 6) + (q << 5)) + (q << 2)));
            i = q;
            int charPos2 = charPos - 1;
            buf[charPos2] = DigitOnes[r];
            charPos = charPos2 - 1;
            buf[charPos] = DigitTens[r];
        }
        int i2 = (int) i;
        while (i2 >= 65536) {
            int q2 = i2 / 100;
            int r2 = i2 - (((q2 << 6) + (q2 << 5)) + (q2 << 2));
            i2 = q2;
            int charPos3 = charPos - 1;
            buf[charPos3] = DigitOnes[r2];
            charPos = charPos3 - 1;
            buf[charPos] = DigitTens[r2];
        }
        do {
            int q22 = (52429 * i2) >>> 19;
            charPos--;
            buf[charPos] = digits[i2 - ((q22 << 3) + (q22 << 1))];
            i2 = q22;
        } while (i2 != 0);
        if (sign != 0) {
            buf[charPos - 1] = sign;
        }
    }

    public static void getChars(int i, int index, char[] buf) {
        int p = index;
        char sign = 0;
        if (i < 0) {
            sign = '-';
            i = -i;
        }
        while (i >= 65536) {
            int q = i / 100;
            int r = i - (((q << 6) + (q << 5)) + (q << 2));
            i = q;
            int p2 = p - 1;
            buf[p2] = DigitOnes[r];
            p = p2 - 1;
            buf[p] = DigitTens[r];
        }
        do {
            int q2 = (52429 * i) >>> 19;
            p--;
            buf[p] = digits[i - ((q2 << 3) + (q2 << 1))];
            i = q2;
        } while (i != 0);
        if (sign != 0) {
            buf[p - 1] = sign;
        }
    }

    public static void getChars(byte b, int index, char[] buf) {
        int i = b;
        int charPos = index;
        char sign = 0;
        if (i < 0) {
            sign = '-';
            i = -i;
        }
        do {
            int q = (52429 * i) >>> 19;
            int r = i - ((q << 3) + (q << 1));
            charPos--;
            buf[charPos] = digits[r];
            i = q;
        } while (i != 0);
        if (sign != 0) {
            buf[charPos - 1] = sign;
        }
    }

    public static int stringSize(int x) {
        int i = 0;
        while (x > sizeTable[i]) {
            i++;
        }
        return i + 1;
    }

    public static void decode(CharsetDecoder charsetDecoder, ByteBuffer byteBuf, CharBuffer charByte) throws CharacterCodingException {
        try {
            CoderResult cr = charsetDecoder.decode(byteBuf, charByte, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            CoderResult cr2 = charsetDecoder.flush(charByte);
            if (!cr2.isUnderflow()) {
                cr2.throwException();
            }
        } catch (CharacterCodingException x) {
            throw new JSONException("utf8 decode error, " + x.getMessage(), x);
        }
    }

    public static boolean firstIdentifier(char ch) {
        return ch < firstIdentifierFlags.length && firstIdentifierFlags[ch];
    }

    public static boolean isIdent(char ch) {
        return ch < identifierFlags.length && identifierFlags[ch];
    }

    public static byte[] decodeBase64(char[] chars, int offset, int charsLen) {
        if (charsLen == 0) {
            return new byte[0];
        }
        int sIx = offset;
        int eIx = (offset + charsLen) - 1;
        while (sIx < eIx && IA[chars[sIx]] < 0) {
            sIx++;
        }
        while (eIx > 0 && IA[chars[eIx]] < 0) {
            eIx--;
        }
        int pad = chars[eIx] == '=' ? chars[eIx + (-1)] == '=' ? 2 : 1 : 0;
        int cCnt = (eIx - sIx) + 1;
        if (charsLen > 76) {
            sepCnt = chars[76] == '\r' ? cCnt / 78 : 0;
            sepCnt <<= 1;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] bytes = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = (len / 3) * 3;
        while (d < eLen) {
            int sIx2 = sIx + 1;
            int sIx3 = sIx2 + 1;
            int i = (IA[chars[sIx]] << 18) | (IA[chars[sIx2]] << 12);
            int sIx4 = sIx3 + 1;
            int i2 = i | (IA[chars[sIx3]] << 6);
            int sIx5 = sIx4 + 1;
            int i3 = i2 | IA[chars[sIx4]];
            int d2 = d + 1;
            bytes[d] = (byte) (i3 >> 16);
            int d3 = d2 + 1;
            bytes[d2] = (byte) (i3 >> 8);
            int d4 = d3 + 1;
            bytes[d3] = (byte) i3;
            if (sepCnt > 0 && (cc = cc + 1) == 19) {
                sIx5 += 2;
                cc = 0;
            }
            sIx = sIx5;
            d = d4;
        }
        if (d < len) {
            int i4 = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i4 |= IA[chars[sIx]] << (18 - (j * 6));
                j++;
                sIx++;
            }
            int r = 16;
            while (d < len) {
                bytes[d] = (byte) (i4 >> r);
                r -= 8;
                d++;
            }
        }
        return bytes;
    }

    public static byte[] decodeBase64(String chars, int offset, int charsLen) {
        if (charsLen == 0) {
            return new byte[0];
        }
        int sIx = offset;
        int eIx = (offset + charsLen) - 1;
        while (sIx < eIx && IA[chars.charAt(sIx)] < 0) {
            sIx++;
        }
        while (eIx > 0 && IA[chars.charAt(eIx)] < 0) {
            eIx--;
        }
        int pad = chars.charAt(eIx) == '=' ? chars.charAt(eIx + (-1)) == '=' ? 2 : 1 : 0;
        int cCnt = (eIx - sIx) + 1;
        if (charsLen > 76) {
            sepCnt = chars.charAt(76) == '\r' ? cCnt / 78 : 0;
            sepCnt <<= 1;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] bytes = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = (len / 3) * 3;
        while (d < eLen) {
            int sIx2 = sIx + 1;
            int sIx3 = sIx2 + 1;
            int i = (IA[chars.charAt(sIx)] << 18) | (IA[chars.charAt(sIx2)] << 12);
            int sIx4 = sIx3 + 1;
            int i2 = i | (IA[chars.charAt(sIx3)] << 6);
            int sIx5 = sIx4 + 1;
            int i3 = i2 | IA[chars.charAt(sIx4)];
            int d2 = d + 1;
            bytes[d] = (byte) (i3 >> 16);
            int d3 = d2 + 1;
            bytes[d2] = (byte) (i3 >> 8);
            int d4 = d3 + 1;
            bytes[d3] = (byte) i3;
            if (sepCnt > 0 && (cc = cc + 1) == 19) {
                sIx5 += 2;
                cc = 0;
            }
            sIx = sIx5;
            d = d4;
        }
        if (d < len) {
            int i4 = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i4 |= IA[chars.charAt(sIx)] << (18 - (j * 6));
                j++;
                sIx++;
            }
            int r = 16;
            while (d < len) {
                bytes[d] = (byte) (i4 >> r);
                r -= 8;
                d++;
            }
        }
        return bytes;
    }

    public static byte[] decodeBase64(String s) {
        int sLen = s.length();
        if (sLen == 0) {
            return new byte[0];
        }
        int sIx = 0;
        int eIx = sLen - 1;
        while (sIx < eIx && IA[s.charAt(sIx) & 255] < 0) {
            sIx++;
        }
        while (eIx > 0 && IA[s.charAt(eIx) & 255] < 0) {
            eIx--;
        }
        int pad = s.charAt(eIx) == '=' ? s.charAt(eIx + (-1)) == '=' ? 2 : 1 : 0;
        int cCnt = (eIx - sIx) + 1;
        if (sLen > 76) {
            sepCnt = s.charAt(76) == '\r' ? cCnt / 78 : 0;
            sepCnt <<= 1;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] dArr = new byte[len];
        int d = 0;
        int cc = 0;
        int eLen = (len / 3) * 3;
        while (d < eLen) {
            int sIx2 = sIx + 1;
            int sIx3 = sIx2 + 1;
            int i = (IA[s.charAt(sIx)] << 18) | (IA[s.charAt(sIx2)] << 12);
            int sIx4 = sIx3 + 1;
            int i2 = i | (IA[s.charAt(sIx3)] << 6);
            int sIx5 = sIx4 + 1;
            int i3 = i2 | IA[s.charAt(sIx4)];
            int d2 = d + 1;
            dArr[d] = (byte) (i3 >> 16);
            int d3 = d2 + 1;
            dArr[d2] = (byte) (i3 >> 8);
            int d4 = d3 + 1;
            dArr[d3] = (byte) i3;
            if (sepCnt > 0 && (cc = cc + 1) == 19) {
                sIx5 += 2;
                cc = 0;
            }
            sIx = sIx5;
            d = d4;
        }
        if (d < len) {
            int i4 = 0;
            int j = 0;
            while (sIx <= eIx - pad) {
                i4 |= IA[s.charAt(sIx)] << (18 - (j * 6));
                j++;
                sIx++;
            }
            int r = 16;
            while (d < len) {
                dArr[d] = (byte) (i4 >> r);
                r -= 8;
                d++;
            }
        }
        return dArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int encodeUTF8(char[] chars, int offset, int len, byte[] bytes) {
        int uc;
        int dp;
        int sl = offset + len;
        int dp2 = 0;
        int dlASCII = Math.min(len, bytes.length) + 0;
        while (dp2 < dlASCII && chars[offset] < 128) {
            bytes[dp2] = (byte) chars[offset];
            dp2++;
            offset++;
        }
        while (offset < sl) {
            int offset2 = offset + 1;
            char c = chars[offset];
            if (c < 128) {
                bytes[dp2] = (byte) c;
                offset = offset2;
                dp2++;
            } else if (c < 2048) {
                int dp3 = dp2 + 1;
                bytes[dp2] = (byte) ((c >> 6) | Opcodes.CHECKCAST);
                dp2 = dp3 + 1;
                bytes[dp3] = (byte) ((c & '?') | 128);
                offset = offset2;
            } else if (c >= 55296 && c < 57344) {
                int ip = offset2 - 1;
                if (c >= 55296 && c < 56320) {
                    if (sl - ip < 2) {
                        uc = -1;
                    } else {
                        char d = chars[ip + 1];
                        if (d >= 56320 && d < 57344) {
                            uc = ((c << '\n') + d) - 56613888;
                        } else {
                            bytes[dp2] = Utf8.REPLACEMENT_BYTE;
                            offset = offset2;
                            dp2++;
                        }
                    }
                    if (uc >= 0) {
                    }
                    offset = offset2;
                    dp2 = dp;
                } else if (c >= 56320 && c < 57344) {
                    bytes[dp2] = Utf8.REPLACEMENT_BYTE;
                    offset = offset2;
                    dp2++;
                } else {
                    uc = c;
                    if (uc >= 0) {
                        dp = dp2 + 1;
                        bytes[dp2] = Utf8.REPLACEMENT_BYTE;
                    } else {
                        int dp4 = dp2 + 1;
                        bytes[dp2] = (byte) ((uc >> 18) | 240);
                        int dp5 = dp4 + 1;
                        bytes[dp4] = (byte) (((uc >> 12) & 63) | 128);
                        int dp6 = dp5 + 1;
                        bytes[dp5] = (byte) ((63 & (uc >> 6)) | 128);
                        bytes[dp6] = (byte) ((uc & 63) | 128);
                        offset2++;
                        dp = dp6 + 1;
                    }
                    offset = offset2;
                    dp2 = dp;
                }
            } else {
                int dp7 = dp2 + 1;
                bytes[dp2] = (byte) ((c >> '\f') | 224);
                int dp8 = dp7 + 1;
                bytes[dp7] = (byte) ((63 & (c >> 6)) | 128);
                bytes[dp8] = (byte) ((c & '?') | 128);
                offset = offset2;
                dp2 = dp8 + 1;
            }
        }
        return dp2;
    }

    public static int decodeUTF8(byte[] sa, int sp, int len, char[] da) {
        int sl = sp + len;
        int dp = 0;
        int dlASCII = Math.min(len, da.length);
        while (dp < dlASCII && sa[sp] >= 0) {
            da[dp] = (char) sa[sp];
            dp++;
            sp++;
        }
        while (sp < sl) {
            int sp2 = sp + 1;
            int b1 = sa[sp];
            if (b1 < 0) {
                int dp2 = b1 >> 5;
                if (dp2 == -2 && (b1 & 30) != 0) {
                    if (sp2 >= sl) {
                        return -1;
                    }
                    int sp3 = sp2 + 1;
                    int b2 = sa[sp2];
                    if ((b2 & Opcodes.CHECKCAST) != 128) {
                        return -1;
                    }
                    da[dp] = (char) (((b1 << 6) ^ b2) ^ Utf8.MASK_2BYTES);
                    sp = sp3;
                    dp++;
                } else if ((b1 >> 4) == -2) {
                    if (sp2 + 1 >= sl) {
                        return -1;
                    }
                    int sp4 = sp2 + 1;
                    int b22 = sa[sp2];
                    int sp5 = sp4 + 1;
                    int b3 = sa[sp4];
                    if ((b1 == -32 && (b22 & 224) == 128) || (b22 & Opcodes.CHECKCAST) != 128 || (b3 & Opcodes.CHECKCAST) != 128) {
                        return -1;
                    }
                    char c = (char) (((b1 << 12) ^ (b22 << 6)) ^ ((-123008) ^ b3));
                    boolean isSurrogate = c >= 55296 && c < 57344;
                    if (isSurrogate) {
                        return -1;
                    }
                    da[dp] = c;
                    sp = sp5;
                    dp++;
                } else {
                    if ((b1 >> 3) != -2 || sp2 + 2 >= sl) {
                        return -1;
                    }
                    int sp6 = sp2 + 1;
                    int b23 = sa[sp2];
                    int sp7 = sp6 + 1;
                    int b32 = sa[sp6];
                    int sp8 = sp7 + 1;
                    int b4 = sa[sp7];
                    int uc = (((b1 << 18) ^ (b23 << 12)) ^ (b32 << 6)) ^ (3678080 ^ b4);
                    if ((b23 & Opcodes.CHECKCAST) != 128 || (b32 & Opcodes.CHECKCAST) != 128 || (b4 & Opcodes.CHECKCAST) != 128 || uc < 65536 || uc >= 1114112) {
                        return -1;
                    }
                    int dp3 = dp + 1;
                    da[dp] = (char) ((uc >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                    dp = dp3 + 1;
                    da[dp3] = (char) ((uc & 1023) + Utf8.LOG_SURROGATE_HEADER);
                    sp = sp8;
                }
            } else {
                da[dp] = (char) b1;
                sp = sp2;
                dp++;
            }
        }
        return dp;
    }

    public static String readAll(Reader reader) throws IOException {
        StringBuilder buf = new StringBuilder();
        try {
            char[] chars = new char[2048];
            while (true) {
                int len = reader.read(chars, 0, chars.length);
                if (len >= 0) {
                    buf.append(chars, 0, len);
                } else {
                    return buf.toString();
                }
            }
        } catch (Exception ex) {
            throw new JSONException("read string from reader error", ex);
        }
    }

    public static boolean isValidJsonpQueryParam(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            if (ch != '.' && !isIdent(ch)) {
                return false;
            }
        }
        return true;
    }
}