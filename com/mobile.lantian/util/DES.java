package com.mobile.lantian.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes.dex */
public class DES {
    public static final String ALGORITHM = "DES";
    private static final byte[] DEFAULT_INITIALIZATION_VECTOR = "00000000".getBytes();
    public static final String PADDING = "DES/CBC/PKCS5Padding";
    private String padding;
    private AlgorithmParameterSpec paramSpec;

    public DES(String padding, byte[] initializationVectorBytes) {
        this.padding = PADDING;
        this.padding = padding;
        this.paramSpec = new IvParameterSpec(initializationVectorBytes);
    }

    public byte[] decodeBytes(byte[] data, byte[] key) throws Exception {
        Key secretKey = getKey(key);
        Cipher cipher = Cipher.getInstance(this.padding);
        cipher.init(2, secretKey, this.paramSpec);
        return cipher.doFinal(data);
    }

    private static Key getKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    public static byte[] decode(byte[] data, byte[] key) throws Exception {
        return decode(data, key, DEFAULT_INITIALIZATION_VECTOR);
    }

    public static byte[] decode(byte[] data, byte[] key, byte[] ivBytes) throws Exception {
        DES des = new DES(PADDING, ivBytes);
        return des.decodeBytes(data, key);
    }
}