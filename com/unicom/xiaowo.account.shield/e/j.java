package com.unicom.xiaowo.account.shield.e;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import kotlin.UByte;

/* loaded from: classes.dex */
public class j {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String a(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i = 0; i < bArrDigest.length; i++) {
                int i2 = bArrDigest[i];
                if (i2 < 0) {
                    i2 += 256;
                }
                if (i2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i2));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean a(ConnectivityManager connectivityManager) throws NoSuchMethodException, SecurityException {
        try {
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            boolean zBooleanValue = ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
            g.a("data is on:" + zBooleanValue);
            return zBooleanValue;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static int a(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        try {
            connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            if (activeNetworkInfo.getType() == 1) {
                if (a(connectivityManager)) {
                    g.b("Data and WIFI");
                    return 1;
                }
                g.b("Only WIFI");
                return 2;
            }
            if (activeNetworkInfo.getType() == 0) {
                g.b("Only Data");
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (!TextUtils.isEmpty(extraInfo)) {
                    h.d(extraInfo);
                    return 0;
                }
                return 0;
            }
            return -1;
        }
        return -1;
    }

    public static String b(String str) {
        int iIndexOf = str.indexOf("://");
        if (iIndexOf > 0) {
            str = str.substring(iIndexOf + 3);
        }
        int iIndexOf2 = str.indexOf(58);
        if (iIndexOf2 >= 0) {
            str = str.substring(0, iIndexOf2);
        }
        int iIndexOf3 = str.indexOf(47);
        if (iIndexOf3 >= 0) {
            str = str.substring(0, iIndexOf3);
        }
        int iIndexOf4 = str.indexOf(63);
        if (iIndexOf4 >= 0) {
            return str.substring(0, iIndexOf4);
        }
        return str;
    }

    public static int c(String str) {
        try {
            byte[] address = InetAddress.getByName(str).getAddress();
            return (address[0] & UByte.MAX_VALUE) | ((address[3] & UByte.MAX_VALUE) << 24) | ((address[2] & UByte.MAX_VALUE) << 16) | ((address[1] & UByte.MAX_VALUE) << 8);
        } catch (UnknownHostException e) {
            return -1;
        }
    }

    public static byte[] a(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            if (!packageInfo.packageName.equals(str)) {
                return null;
            }
            return packageInfo.signatures[0].toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String a(byte[] bArr) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(bArr);
            int length = bArrDigest.length;
            char[] cArr = new char[length * 2];
            for (int i = 0; i < length; i++) {
                int i2 = bArrDigest[i] & 15;
                int i3 = i * 2;
                cArr[i3] = a[(bArrDigest[i] & 240) >> 4];
                cArr[i3 + 1] = a[i2];
            }
            return new String(cArr);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String b(Context context, String str) {
        try {
            return a(a(context, str));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String d(String str) {
        try {
            return a(str, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbRkBR4leALApkWRp2ng8zJ2WgI7YEqtMwW9Q1tmRzDLPNhH0ugACfbiStBG4ybdYNHzRlxvOwQ7R0MeN56qEPsv6qieg/HiRXBnQ2hQ2hypo9JHqHx8BX54ESZ+BIf0imjGTcxtHvbzYA04ckmH5Enl2Pkd+R/RZuMK589C7KwQIDAQAB");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String a(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        PublicKey publicKeyE = e(str2);
        Cipher cipher = Cipher.getInstance(new String(com.unicom.xiaowo.account.shield.a.b.b("UlNBL0VDQi9QS0NTMVBhZGRpbmc=")));
        cipher.init(1, publicKeyE);
        return com.unicom.xiaowo.account.shield.a.b.a(cipher.doFinal(str.getBytes()));
    }

    public static PublicKey e(String str) {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(com.unicom.xiaowo.account.shield.a.b.b(str)));
    }

    public static String b(Context context) throws PackageManager.NameNotFoundException {
        try {
            return (String) context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0));
        } catch (Exception e) {
            return "";
        }
    }

    public static String c(Context context) throws SocketException {
        try {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (!networkInterfaceNextElement.getName().toLowerCase().contains("wlan") && !networkInterfaceNextElement.getName().toLowerCase().contains("tun")) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress() && !inetAddressNextElement.isLinkLocalAddress()) {
                            if (inetAddressNextElement instanceof Inet4Address) {
                                sb.append(inetAddressNextElement.getHostAddress()).append(",");
                            }
                            if (inetAddressNextElement instanceof Inet6Address) {
                                sb2.append(inetAddressNextElement.getHostAddress()).append(",");
                            }
                        }
                    }
                }
            }
            if (sb.length() > 0) {
                sb = sb.delete(sb.length() - 1, sb.length());
            }
            if (sb2.length() > 0) {
                sb2 = sb2.delete(sb2.length() - 1, sb2.length());
            }
            String str = sb.toString() + "|" + sb2.toString();
            g.b(str);
            if (!TextUtils.isEmpty(sb.toString())) {
                h.e(sb.toString());
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}