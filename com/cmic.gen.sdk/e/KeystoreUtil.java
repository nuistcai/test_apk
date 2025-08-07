package com.cmic.gen.sdk.e;

import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import android.util.Base64;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.security.auth.x500.X500Principal;

/* compiled from: KeystoreUtil.java */
/* renamed from: com.cmic.gen.sdk.e.b, reason: use source file name */
/* loaded from: classes.dex */
public class KeystoreUtil {
    private static byte[] a = null;

    public static boolean a(Context context, boolean z) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (keyStore.getKey("CMCC_SDK_V1", null) != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (z) {
            return b(context);
        }
        return false;
    }

    private static boolean b(Context context) throws InterruptedException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
                keyGenerator.init(new KeyGenParameterSpec.Builder("CMCC_SDK_V1", 3).setDigests("SHA-256", "SHA-512").setBlockModes("CBC").setEncryptionPaddings("PKCS7Padding").setRandomizedEncryptionRequired(false).setKeySize(256).build());
                Thread.sleep(1000L);
                keyGenerator.generateKey();
                return true;
            } catch (Exception e) {
                LogUtils.a("KeystoreUtil", e.getMessage());
                return false;
            }
        }
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(1, 30);
        try {
            KeyPairGeneratorSpec keyPairGeneratorSpecBuild = new KeyPairGeneratorSpec.Builder(context).setAlias("CMCC_SDK_V1").setSubject(new X500Principal("CN=CMCC_SDK_V1")).setSerialNumber(BigInteger.TEN).setStartDate(calendar.getTime()).setEndDate(calendar2.getTime()).build();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
            keyPairGenerator.initialize(keyPairGeneratorSpecBuild);
            Thread.sleep(1000L);
            keyPairGenerator.generateKeyPair();
            return true;
        } catch (Exception e2) {
            LogUtils.a("KeystoreUtil", e2.getMessage());
            return false;
        }
    }

    static String a(Context context, String str) {
        byte[] bArrA = a(context);
        if (bArrA != null) {
            return AESUtils.a(bArrA, str, a);
        }
        a();
        return null;
    }

    static String b(Context context, String str) {
        byte[] bArrA = a(context);
        if (bArrA != null) {
            return AESUtils.a(bArrA, str, a);
        }
        a();
        return null;
    }

    static String c(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            byte[] bArrA = a(context);
            if (bArrA != null) {
                return AESUtils.b(bArrA, str, a);
            }
            a();
            return null;
        }
        return null;
    }

    public static synchronized byte[] a(Context context) {
        Cipher cipher;
        byte[] bArrDoFinal;
        Cipher cipher2;
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (!a(context, false)) {
                return null;
            }
            String strB = b();
            boolean z = true;
            if (!TextUtils.isEmpty(strB)) {
                a = Base64.decode(c(), 0);
                byte[] bArrDecode = Base64.decode(strB, 0);
                Key key = keyStore.getKey("CMCC_SDK_V1", null);
                if (key == null) {
                    return null;
                }
                if (key instanceof SecretKey) {
                    cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                    cipher.init(2, key, new IvParameterSpec(a));
                    LogUtils.b("KeystoreUtil", "使用aes");
                } else {
                    if (!(key instanceof PrivateKey)) {
                        return null;
                    }
                    cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA256AndMGF1Padding");
                    cipher.init(2, key);
                    LogUtils.b("KeystoreUtil", "使用rsa");
                }
                bArrDoFinal = cipher.doFinal(bArrDecode);
                StringBuilder sbAppend = new StringBuilder().append("是否解密出秘钥：");
                if (TextUtils.isEmpty(Base64.encodeToString(bArrDoFinal, 0))) {
                    z = false;
                }
                LogUtils.b("KeystoreUtil", sbAppend.append(z).toString());
            } else {
                bArrDoFinal = UmcUtils.a();
                a = UmcUtils.a();
                Key key2 = keyStore.getKey("CMCC_SDK_V1", null);
                if (key2 instanceof SecretKey) {
                    LogUtils.b("KeystoreUtil", "随机生成aes秘钥");
                    cipher2 = Cipher.getInstance("AES/CBC/PKCS7Padding");
                    cipher2.init(1, key2, new IvParameterSpec(a));
                } else {
                    if (!(key2 instanceof PrivateKey)) {
                        return null;
                    }
                    PublicKey publicKey = keyStore.getCertificate("CMCC_SDK_V1").getPublicKey();
                    Cipher cipher3 = Cipher.getInstance("RSA/ECB/OAEPWithSHA256AndMGF1Padding");
                    LogUtils.b("KeystoreUtil", "生成rsa密");
                    cipher3.init(1, publicKey);
                    cipher2 = cipher3;
                }
                String strEncodeToString = Base64.encodeToString(cipher2.doFinal(bArrDoFinal), 0);
                String strEncodeToString2 = Base64.encodeToString(a, 0);
                HashMap map = new HashMap();
                map.put("AES_IV", strEncodeToString2);
                map.put("AES_KEY", strEncodeToString);
                SharedPreferencesUtil.a(map);
            }
            LogUtils.b("KeystoreUtil", "是否解密出秘钥：" + Base64.encodeToString(bArrDoFinal, 0));
            return bArrDoFinal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void a() {
        SharedPreferencesUtil.a("AES_KEY");
    }

    private static String b() {
        return SharedPreferencesUtil.b("AES_KEY", "");
    }

    private static String c() {
        return SharedPreferencesUtil.b("AES_IV", "");
    }
}