package com.unicom.online.account.kernel;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import kotlin.UByte;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class al {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static int b = 1;
    private static int c = 0;

    public static String a() {
        return ak.d();
    }

    public static String a(int i) {
        return aj.a(i);
    }

    public static String a(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
    }

    public static String a(Context context, String str) {
        try {
            return b(b(context, str));
        } catch (Exception e) {
            aj.a(e);
            return "";
        }
    }

    public static String a(Context context, String str, String str2) {
        try {
            return a(b(context, str), str2.toLowerCase());
        } catch (Exception e) {
            aj.a(e);
            return "";
        }
    }

    public static String a(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(new String(o.b("U0hBMjU2")));
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
            aj.a(e);
            return "";
        }
    }

    public static String a(String str, String str2, String str3) {
        return n.b(str, str2, str3);
    }

    private static String a(List<String> list) throws JSONException {
        StringBuffer stringBufferAppend;
        JSONObject jSONObject = new JSONObject();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                String strSubstring = str.substring(0, 3);
                try {
                    byte[] address = InetAddress.getByName(str).getAddress();
                    if (str != null && str.length() != 0) {
                        if (address.length == 4) {
                            if (!strSubstring.equals("127") && !strSubstring.equals("192")) {
                                stringBufferAppend = stringBuffer.append(str);
                                stringBufferAppend.append("-");
                            }
                        } else if (!str.contains("%") && !"::1".equals(str)) {
                            stringBufferAppend = stringBuffer2.append(str);
                            stringBufferAppend.append("-");
                        }
                    }
                } catch (UnknownHostException e) {
                    aj.a(e);
                    return "{\"privateIp\":\"0.0.0.0\"}";
                }
            }
            if (stringBuffer.length() != 0 && '-' == stringBuffer.charAt(stringBuffer.length() - 1)) {
                StringBuffer stringBufferDeleteCharAt = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                int iMin = Math.min(stringBuffer2.length(), 5);
                for (int i2 = 0; i2 < iMin; i2++) {
                    jSONObject.put("privateIp", stringBufferDeleteCharAt);
                }
            }
            if (stringBuffer2.length() != 0 && '-' == stringBuffer2.charAt(stringBuffer2.length() - 1)) {
                StringBuffer stringBufferDeleteCharAt2 = stringBuffer2.deleteCharAt(stringBuffer2.length() - 1);
                int iMin2 = Math.min(stringBuffer2.length(), 5);
                for (int i3 = 0; i3 < iMin2; i3++) {
                    jSONObject.put("privateIp_v6", stringBufferDeleteCharAt2);
                }
            }
            if (jSONObject.length() > 0) {
                return jSONObject.toString();
            }
        }
        return "{\"privateIp\":\"0.0.0.0\"}";
    }

    private static String a(byte[] bArr) {
        String str = "";
        for (int i = 0; i < bArr.length; i++) {
            if (i != 0) {
                str = str + ":";
            }
            String hexString = Integer.toHexString(bArr[i] & UByte.MAX_VALUE);
            if (hexString.length() == 1) {
                str = str + "0";
            }
            str = str + hexString;
        }
        return str;
    }

    private static String a(byte[] bArr, String str) {
        try {
            return a(MessageDigest.getInstance(str).digest(bArr));
        } catch (Exception e) {
            aj.a(e);
            return "";
        }
    }

    private static boolean a(ConnectivityManager connectivityManager) throws NoSuchMethodException, SecurityException {
        try {
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
        } catch (Exception e) {
            aj.a(e);
            return true;
        }
    }

    public static int b(Context context) {
        int iD = d(context);
        ak.a(iD);
        return iD;
    }

    public static String b(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(new String(o.b("TUQ1")));
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
            aj.a(e);
            return "";
        }
    }

    public static String b(String str, String str2, String str3) {
        return n.a(str, str2, str3);
    }

    private static String b(byte[] bArr) {
        try {
            SM3Digest sM3Digest = new SM3Digest();
            sM3Digest.update(bArr, 0, bArr.length);
            byte[] bArr2 = new byte[sM3Digest.getDigestSize()];
            sM3Digest.doFinal(bArr2, 0);
            return a(bArr2);
        } catch (Exception e) {
            aj.a(e);
            return "";
        }
    }

    public static void b() {
        aj.a();
    }

    private static byte[] b(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            if (packageInfo.packageName.equals(str)) {
                return packageInfo.signatures[0].toByteArray();
            }
            return null;
        } catch (Exception e) {
            aj.a(e);
            return null;
        }
    }

    public static int c() {
        c = 0;
        b = 0;
        return 0;
    }

    public static String c(Context context) throws PackageManager.NameNotFoundException {
        try {
            return (String) context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0));
        } catch (Exception e) {
            return "";
        }
    }

    public static String c(String str) {
        SM3Digest sM3Digest = new SM3Digest();
        byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
        sM3Digest.update(bytes, 0, bytes.length);
        byte[] bArr = new byte[sM3Digest.getDigestSize()];
        sM3Digest.doFinal(bArr, 0);
        return u.a(bArr);
    }

    public static int d() {
        return b;
    }

    private static int d(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        try {
            connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            aj.a(e);
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            if (activeNetworkInfo.getType() == 1) {
                if (a(connectivityManager)) {
                    aj.a("Data and WIFI");
                    return 1;
                }
                aj.a("Only WIFI");
                return 2;
            }
            if (activeNetworkInfo.getType() == 0) {
                aj.a("Only Data");
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (TextUtils.isEmpty(extraInfo)) {
                    return 0;
                }
                ak.d(extraInfo);
                ak.b(ak.e(extraInfo));
                return 0;
            }
            return -1;
        }
        return -1;
    }

    public static int d(String str) {
        try {
            byte[] address = InetAddress.getByName(str).getAddress();
            return (address[0] & UByte.MAX_VALUE) | ((address[3] & UByte.MAX_VALUE) << 24) | ((address[2] & UByte.MAX_VALUE) << 16) | ((address[1] & UByte.MAX_VALUE) << 8);
        } catch (UnknownHostException e) {
            return -1;
        }
    }

    public static int e() {
        return c;
    }

    public static void e(String str) {
        aj.a(0, str);
    }

    public static int f() {
        if (c < 0 || c > b) {
            return b;
        }
        int i = c + 1;
        c = i;
        return i;
    }

    public static String g() {
        try {
            return a(h());
        } catch (JSONException e) {
            return "{\"privateIp\":\"0.0.0.0\"}";
        }
    }

    private static List<String> h() throws SocketException {
        Enumeration<InetAddress> inetAddresses;
        ArrayList arrayList = new ArrayList();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                    if (!networkInterfaceNextElement.isVirtual() && networkInterfaceNextElement.isUp() && (inetAddresses = networkInterfaceNextElement.getInetAddresses()) != null) {
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress inetAddressNextElement = inetAddresses.nextElement();
                            if (!inetAddressNextElement.isLoopbackAddress() && !inetAddressNextElement.isLinkLocalAddress() && !inetAddressNextElement.isMulticastAddress() && !inetAddressNextElement.isAnyLocalAddress() && inetAddressNextElement != null && ((inetAddressNextElement instanceof Inet4Address) || (inetAddressNextElement instanceof Inet6Address))) {
                                arrayList.add(inetAddressNextElement.getHostAddress());
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            aj.a(e);
        }
        return arrayList;
    }
}