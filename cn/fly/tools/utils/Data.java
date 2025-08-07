package cn.fly.tools.utils;

import android.text.TextUtils;
import android.util.Base64;
import cn.fly.commons.C0041r;
import cn.fly.commons.n;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;
import java.util.zip.CRC32;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import kotlin.UByte;

/* loaded from: classes.dex */
public class Data implements PublicMemberKeeper {
    public static byte[] SHA1(String str) throws Throwable {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return SHA1(str.getBytes("utf-8"));
    }

    public static byte[] SHA1(byte[] bArr) throws Throwable {
        MessageDigest messageDigest = MessageDigest.getInstance(n.a("005Ncjdidbfifd"));
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    public static byte[] AES128Encode(String str, String str2) throws Throwable {
        if (str == null || str2 == null) {
            return null;
        }
        byte[] bytes = str.getBytes("UTF-8");
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, Math.min(bytes.length, 16));
        return AES128Encode(bArr, str2);
    }

    public static Cipher getCipher(String str, String str2) throws Throwable {
        Cipher cipher = null;
        if (!TextUtils.isEmpty(str2)) {
            try {
                Provider provider = Security.getProvider(str2);
                if (provider != null) {
                    cipher = Cipher.getInstance(str, provider);
                }
            } catch (Throwable th) {
            }
        }
        if (cipher == null) {
            return Cipher.getInstance(str, str2);
        }
        return cipher;
    }

    public static byte[] AES128Encode(byte[] bArr, String str) throws Throwable {
        if (bArr == null || str == null) {
            return null;
        }
        return AES128Encode(bArr, str.getBytes("UTF-8"));
    }

    public static byte[] EncodeNoPadding(String str, String str2) throws Throwable {
        if (str == null || str2 == null) {
            return null;
        }
        byte[] bytes = str.getBytes("UTF-8");
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, Math.min(bytes.length, 16));
        int length = 16 - (str2.length() % 16);
        StringBuilder sb = new StringBuilder(str2);
        for (int i = 0; i < length; i++) {
            sb.append(" ");
        }
        byte[] bytes2 = sb.toString().getBytes("UTF-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, n.a("003+dbegcj"));
        Cipher cipher = getCipher(n.a("0037dbegcj") + n.a("003jVegcb") + n.a("005UdhSj=cebiej") + n.a("006b-bababg;c0ch"), n.a("0021dhcb"));
        cipher.init(1, secretKeySpec);
        return cipher.doFinal(bytes2);
    }

    public static byte[] AES128Encode(byte[] bArr, byte[] bArr2) throws Throwable {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, n.a("0039dbegcj"));
        Cipher cipher = getCipher(n.a("003<dbegcj") + n.a("003j8egcb") + n.a("008'dh,j?ejgacbcjgiej") + n.a("006b-bababgWc<ch"), n.a("002Xdhcb"));
        cipher.init(1, secretKeySpec);
        byte[] bArr3 = new byte[cipher.getOutputSize(bArr2.length)];
        cipher.doFinal(bArr3, cipher.update(bArr2, 0, bArr2.length, bArr3, 0));
        return bArr3;
    }

    public static String AES128Decode(String str, byte[] bArr) throws Throwable {
        if (str == null || bArr == null) {
            return null;
        }
        return new String(AES128Decode(str.getBytes("UTF-8"), bArr), "UTF-8").trim();
    }

    public static byte[] AES128Decode(byte[] bArr, byte[] bArr2) throws Throwable {
        if (bArr == null || bArr2 == null) {
            return null;
        }
        byte[] bArr3 = new byte[16];
        System.arraycopy(bArr, 0, bArr3, 0, Math.min(bArr.length, 16));
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr3, n.a("003%dbegcj"));
        Cipher cipher = getCipher(n.a("003(dbegcj") + n.a("003jPegcb") + n.a("005;dh1j8cebiej") + n.a("006bYbababg=cHch"), n.a("002Kdhcb"));
        cipher.init(2, secretKeySpec);
        byte[] bArr4 = new byte[cipher.getOutputSize(bArr2.length)];
        cipher.doFinal(bArr4, cipher.update(bArr2, 0, bArr2.length, bArr4, 0));
        return bArr4;
    }

    public static String AES128PaddingDecode(byte[] bArr, byte[] bArr2) throws Throwable {
        if (bArr == null || bArr2 == null) {
            return null;
        }
        byte[] bArr3 = new byte[16];
        System.arraycopy(bArr, 0, bArr3, 0, Math.min(bArr.length, 16));
        return new String(paddingDecode(bArr3, bArr2), "UTF-8").trim();
    }

    public static void AES128Decode(String str, InputStream inputStream, OutputStream outputStream) throws Throwable {
        if (str == null) {
            return;
        }
        AES128Decode(str.getBytes("UTF-8"), inputStream, outputStream);
    }

    public static void AES128Decode(byte[] bArr, InputStream inputStream, OutputStream outputStream) throws Throwable {
        CipherInputStream cipherInputStream;
        if (bArr == null || inputStream == null || outputStream == null) {
            return;
        }
        byte[] bArr2 = new byte[16];
        System.arraycopy(bArr, 0, bArr2, 0, Math.min(bArr.length, 16));
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, n.a("003Sdbegcj"));
        Cipher cipher = getCipher(n.a("003)dbegcj") + n.a("003jQegcb") + n.a("008Cdh;j-ejgacbcjgiej") + n.a("006b<bababg>c]ch"), n.a("002>dhcb"));
        cipher.init(2, secretKeySpec);
        CipherInputStream cipherInputStream2 = null;
        try {
            cipherInputStream = new CipherInputStream(inputStream, cipher);
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr3 = new byte[1024];
            for (int i = cipherInputStream.read(bArr3); i != -1; i = cipherInputStream.read(bArr3)) {
                outputStream.write(bArr3, 0, i);
            }
            outputStream.flush();
            C0041r.a(cipherInputStream);
        } catch (Throwable th2) {
            th = th2;
            cipherInputStream2 = cipherInputStream;
            C0041r.a(cipherInputStream2);
            throw th;
        }
    }

    public static byte[] paddingDecode(byte[] bArr, byte[] bArr2) throws Throwable {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, n.a("0038dbegcj"));
        Cipher cipher = getCipher(n.a("0030dbegcj") + n.a("003j5egcb") + n.a("008Mdh[jQejgacbcjgiej") + n.a("006bTbababg5c<ch"), n.a("002Fdhcb"));
        cipher.init(2, secretKeySpec);
        return cipher.doFinal(bArr2);
    }

    public static String byteToHex(byte[] bArr) {
        return byteToHex(bArr, 0, bArr.length);
    }

    public static String byteToHex(byte[] bArr, int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        if (bArr == null) {
            return stringBuffer.toString();
        }
        while (i < i2) {
            stringBuffer.append(String.format("%02x", Byte.valueOf(bArr[i])));
            i++;
        }
        return stringBuffer.toString();
    }

    public static String bytesToHexFaster(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] cArr2 = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            cArr2[i] = cArr[(b >>> 4) & 15];
            int i2 = i + 1;
            cArr2[i2] = cArr[b & 15];
            i = i2 + 1;
        }
        return new String(cArr2);
    }

    public static String MD5(String str) {
        byte[] bArrRawMD5;
        if (str == null || (bArrRawMD5 = rawMD5(str)) == null) {
            return null;
        }
        return bytesToHexFaster(bArrRawMD5);
    }

    public static String MD5(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return MD5(bArr, 0, bArr.length);
    }

    public static String MD5(byte[] bArr, int i, int i2) {
        byte[] bArrRawMD5;
        if (bArr == null || (bArrRawMD5 = rawMD5(bArr, i, i2)) == null) {
            return null;
        }
        return bytesToHexFaster(bArrRawMD5);
    }

    public static String MD5(File file) {
        FileInputStream fileInputStream;
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Throwable th) {
            th = th;
            fileInputStream = null;
        }
        try {
            byte[] bArrRawMD5 = rawMD5(fileInputStream);
            C0041r.a(fileInputStream);
            if (bArrRawMD5 == null) {
                return null;
            }
            return bytesToHexFaster(bArrRawMD5);
        } catch (Throwable th2) {
            th = th2;
            try {
                FlyLog.getInstance().w(th);
                C0041r.a(fileInputStream);
                return null;
            } catch (Throwable th3) {
                C0041r.a(fileInputStream);
                throw th3;
            }
        }
    }

    public static byte[] rawMD5(String str) {
        if (str == null) {
            return null;
        }
        try {
            return rawMD5(str.getBytes("utf-8"));
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    public static byte[] rawMD5(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return rawMD5(bArr, 0, bArr.length);
    }

    public static byte[] rawMD5(byte[] bArr, int i, int i2) {
        ByteArrayInputStream byteArrayInputStream;
        byte[] bArrRawMD5 = null;
        if (bArr == null) {
            return null;
        }
        try {
            byteArrayInputStream = new ByteArrayInputStream(bArr, i, i2);
        } catch (Throwable th) {
            th = th;
            byteArrayInputStream = null;
        }
        try {
            bArrRawMD5 = rawMD5(byteArrayInputStream);
            C0041r.a(byteArrayInputStream);
        } catch (Throwable th2) {
            th = th2;
            try {
                FlyLog.getInstance().w(th);
                C0041r.a(byteArrayInputStream);
                return bArrRawMD5;
            } catch (Throwable th3) {
                C0041r.a(byteArrayInputStream);
                throw th3;
            }
        }
        return bArrRawMD5;
    }

    public static byte[] rawMD5(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            byte[] bArr = new byte[1024];
            MessageDigest messageDigest = MessageDigest.getInstance(n.a("0039fadjfg"));
            int i = inputStream.read(bArr);
            while (i != -1) {
                messageDigest.update(bArr, 0, i);
                i = inputStream.read(bArr);
            }
            return messageDigest.digest();
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    public static String urlEncode(String str) {
        try {
            return urlEncode(str, "utf-8");
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    public static String urlEncode(String str, String str2) throws Throwable {
        String strEncode = TextUtils.isEmpty(str) ? "" : URLEncoder.encode(str, str2);
        if (TextUtils.isEmpty(strEncode)) {
            return strEncode;
        }
        return strEncode.replace("+", "%20");
    }

    public static String Base64AES(String str, String str2) {
        try {
            String strEncodeToString = Base64.encodeToString(AES128Encode(str2, str), 0);
            if (strEncodeToString.contains("\n")) {
                return strEncodeToString.replace("\n", "");
            }
            return strEncodeToString;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    public static String CRC32(byte[] bArr) throws Throwable {
        CRC32 crc32 = new CRC32();
        crc32.update(bArr);
        long value = crc32.getValue();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02x", Integer.valueOf(((byte) (value >>> 56)) & UByte.MAX_VALUE)));
        sb.append(String.format("%02x", Integer.valueOf(((byte) (value >>> 48)) & UByte.MAX_VALUE)));
        sb.append(String.format("%02x", Integer.valueOf(((byte) (value >>> 40)) & UByte.MAX_VALUE)));
        sb.append(String.format("%02x", Integer.valueOf(((byte) (value >>> 32)) & UByte.MAX_VALUE)));
        sb.append(String.format("%02x", Integer.valueOf(((byte) (value >>> 24)) & UByte.MAX_VALUE)));
        sb.append(String.format("%02x", Integer.valueOf(((byte) (value >>> 16)) & UByte.MAX_VALUE)));
        sb.append(String.format("%02x", Integer.valueOf(((byte) (value >>> 8)) & UByte.MAX_VALUE)));
        sb.append(String.format("%02x", Integer.valueOf(((byte) value) & UByte.MAX_VALUE)));
        while (sb.charAt(0) == '0') {
            sb = sb.deleteCharAt(0);
        }
        return sb.toString().toLowerCase();
    }
}