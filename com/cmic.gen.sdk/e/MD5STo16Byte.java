package com.cmic.gen.sdk.e;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: MD5STo16Byte.java */
/* renamed from: com.cmic.gen.sdk.e.d, reason: use source file name */
/* loaded from: classes.dex */
public class MD5STo16Byte {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return a(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String a(byte[] bArr) throws NoSuchAlgorithmException {
        if (bArr == null) {
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return UmcUtils.a(messageDigest.digest());
        } catch (Exception e) {
            return "";
        }
    }
}