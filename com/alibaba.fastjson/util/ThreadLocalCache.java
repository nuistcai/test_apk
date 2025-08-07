package com.alibaba.fastjson.util;

import java.lang.ref.SoftReference;
import java.nio.charset.CharsetDecoder;

/* loaded from: classes.dex */
public class ThreadLocalCache {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int BYTES_CACH_INIT_SIZE = 1024;
    public static final int BYTES_CACH_INIT_SIZE_EXP = 10;
    public static final int BYTES_CACH_MAX_SIZE = 131072;
    public static final int BYTES_CACH_MAX_SIZE_EXP = 17;
    public static final int CHARS_CACH_INIT_SIZE = 1024;
    public static final int CHARS_CACH_INIT_SIZE_EXP = 10;
    public static final int CHARS_CACH_MAX_SIZE = 131072;
    public static final int CHARS_CACH_MAX_SIZE_EXP = 17;
    private static final ThreadLocal<SoftReference<char[]>> charsBufLocal = new ThreadLocal<>();
    private static final ThreadLocal<CharsetDecoder> decoderLocal = new ThreadLocal<>();
    private static final ThreadLocal<SoftReference<byte[]>> bytesBufLocal = new ThreadLocal<>();

    public static CharsetDecoder getUTF8Decoder() {
        CharsetDecoder decoder = decoderLocal.get();
        if (decoder == null) {
            CharsetDecoder decoder2 = new UTF8Decoder();
            decoderLocal.set(decoder2);
            return decoder2;
        }
        return decoder;
    }

    public static void clearChars() {
        charsBufLocal.set(null);
    }

    public static char[] getChars(int length) {
        SoftReference<char[]> ref = charsBufLocal.get();
        if (ref == null) {
            return allocate(length);
        }
        char[] chars = ref.get();
        if (chars == null) {
            return allocate(length);
        }
        if (chars.length < length) {
            return allocate(length);
        }
        return chars;
    }

    private static char[] allocate(int length) {
        if (length > 131072) {
            return new char[length];
        }
        int allocateLength = getAllocateLengthExp(10, 17, length);
        char[] chars = new char[allocateLength];
        charsBufLocal.set(new SoftReference<>(chars));
        return chars;
    }

    private static int getAllocateLengthExp(int minExp, int maxExp, int length) {
        if ((1 << maxExp) < length) {
            throw new AssertionError();
        }
        int part = length >>> minExp;
        if (part > 0) {
            return 1 << (32 - Integer.numberOfLeadingZeros(length - 1));
        }
        return 1 << minExp;
    }

    public static void clearBytes() {
        bytesBufLocal.set(null);
    }

    public static byte[] getBytes(int length) {
        SoftReference<byte[]> ref = bytesBufLocal.get();
        if (ref == null) {
            return allocateBytes(length);
        }
        byte[] bytes = ref.get();
        if (bytes == null) {
            return allocateBytes(length);
        }
        if (bytes.length < length) {
            return allocateBytes(length);
        }
        return bytes;
    }

    private static byte[] allocateBytes(int length) {
        if (length > 131072) {
            return new byte[length];
        }
        int allocateLength = getAllocateLengthExp(10, 17, length);
        byte[] chars = new byte[allocateLength];
        bytesBufLocal.set(new SoftReference<>(chars));
        return chars;
    }
}