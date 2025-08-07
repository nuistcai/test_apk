package okio;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: -Base64.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0001*\u00020\u0007H\u0000\u001a\u0016\u0010\b\u001a\u00020\u0007*\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\u0001H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006\n"}, d2 = {"BASE64", "", "getBASE64", "()[B", "BASE64_URL_SAFE", "getBASE64_URL_SAFE", "decodeBase64ToArray", "", "encodeBase64", "map", "jvm"}, k = 2, mv = {1, 1, 11})
/* renamed from: okio.-Base64, reason: invalid class name */
/* loaded from: classes.dex */
public final class Base64 {
    private static final byte[] BASE64 = ByteString.INSTANCE.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData();
    private static final byte[] BASE64_URL_SAFE = ByteString.INSTANCE.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData();

    public static final byte[] getBASE64() {
        return BASE64;
    }

    public static final byte[] getBASE64_URL_SAFE() {
        return BASE64_URL_SAFE;
    }

    public static final byte[] decodeBase64ToArray(String receiver) {
        int bits;
        char c;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        int limit = receiver.length();
        while (limit > 0 && ((c = receiver.charAt(limit - 1)) == '=' || c == '\n' || c == '\r' || c == ' ' || c == '\t')) {
            limit--;
        }
        byte[] out = new byte[(int) ((limit * 6) / 8)];
        int outCount = 0;
        int inCount = 0;
        int word = 0;
        for (int pos = 0; pos < limit; pos++) {
            char c2 = receiver.charAt(pos);
            if ('A' <= c2 && 'Z' >= c2) {
                bits = c2 - 'A';
            } else if ('a' <= c2 && 'z' >= c2) {
                bits = c2 - 'G';
            } else if ('0' <= c2 && '9' >= c2) {
                bits = c2 + 4;
            } else if (c2 == '+' || c2 == '-') {
                bits = 62;
            } else if (c2 == '/' || c2 == '_') {
                bits = 63;
            } else {
                if (c2 != '\n' && c2 != '\r' && c2 != ' ' && c2 != '\t') {
                    return null;
                }
            }
            word = (word << 6) | bits;
            inCount++;
            if (inCount % 4 == 0) {
                int outCount2 = outCount + 1;
                out[outCount] = (byte) (word >> 16);
                int outCount3 = outCount2 + 1;
                out[outCount2] = (byte) (word >> 8);
                out[outCount3] = (byte) word;
                outCount = outCount3 + 1;
            }
        }
        int lastWordChars = inCount % 4;
        switch (lastWordChars) {
            case 1:
                return null;
            case 2:
                out[outCount] = (byte) ((word << 12) >> 16);
                outCount++;
                break;
            case 3:
                int word2 = word << 6;
                int outCount4 = outCount + 1;
                out[outCount] = (byte) (word2 >> 16);
                outCount = outCount4 + 1;
                out[outCount4] = (byte) (word2 >> 8);
                break;
        }
        if (outCount == out.length) {
            return out;
        }
        byte[] prefix = new byte[outCount];
        Platform.arraycopy(out, 0, prefix, 0, outCount);
        return prefix;
    }

    public static /* bridge */ /* synthetic */ String encodeBase64$default(byte[] bArr, byte[] bArr2, int i, Object obj) {
        if ((i & 1) != 0) {
            bArr2 = BASE64;
        }
        return encodeBase64(bArr, bArr2);
    }

    public static final String encodeBase64(byte[] receiver, byte[] map) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(map, "map");
        int length = ((receiver.length + 2) / 3) * 4;
        byte[] out = new byte[length];
        int index = 0;
        int end = receiver.length - (receiver.length % 3);
        int b0 = 0;
        while (b0 < end) {
            int i = b0 + 1;
            int b02 = receiver[b0];
            int i2 = i + 1;
            int b1 = receiver[i];
            int i3 = i2 + 1;
            int b2 = receiver[i2];
            int index2 = index + 1;
            out[index] = map[(b02 & 255) >> 2];
            int index3 = index2 + 1;
            out[index2] = map[((b02 & 3) << 4) | ((b1 & 255) >> 4)];
            int index4 = index3 + 1;
            out[index3] = map[((b1 & 15) << 2) | ((b2 & 255) >> 6)];
            index = index4 + 1;
            out[index4] = map[b2 & 63];
            b0 = i3;
        }
        switch (receiver.length - end) {
            case 1:
                int i4 = receiver[b0];
                int index5 = index + 1;
                out[index] = map[(i4 & 255) >> 2];
                int index6 = index5 + 1;
                out[index5] = map[(i4 & 3) << 4];
                byte b = (byte) 61;
                out[index6] = b;
                out[index6 + 1] = b;
                break;
            case 2:
                int i5 = b0 + 1;
                int b03 = receiver[b0];
                int b12 = receiver[i5];
                int index7 = index + 1;
                out[index] = map[(b03 & 255) >> 2];
                int index8 = index7 + 1;
                out[index7] = map[((b03 & 3) << 4) | ((b12 & 255) >> 4)];
                out[index8] = map[(b12 & 15) << 2];
                out[index8 + 1] = (byte) 61;
                break;
        }
        return Platform.toUtf8String(out);
    }
}