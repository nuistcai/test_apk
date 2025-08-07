package com.cmic.gen.sdk.e;

import android.text.TextUtils;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/* compiled from: UmcIPUtils.java */
/* renamed from: com.cmic.gen.sdk.e.q, reason: use source file name */
/* loaded from: classes.dex */
public class UmcIPUtils {
    public static String[] a(boolean z) throws SocketException {
        String[] strArr = {"", ""};
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                if (!z || !networkInterfaceNextElement.getName().toLowerCase().contains("wlan")) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress() && !inetAddressNextElement.isLinkLocalAddress()) {
                            String hostAddress = inetAddressNextElement.getHostAddress();
                            if (!TextUtils.isEmpty(hostAddress)) {
                                if (inetAddressNextElement instanceof Inet6Address) {
                                    sb.append(hostAddress).append(",");
                                } else if (inetAddressNextElement instanceof Inet4Address) {
                                    sb2.append(hostAddress).append(",");
                                }
                            }
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(sb)) {
                sb = sb.delete(sb.length() - 1, sb.length());
            }
            if (!TextUtils.isEmpty(sb2)) {
                sb2 = sb2.delete(sb2.length() - 1, sb2.length());
            }
            strArr[0] = sb2.toString();
            strArr[1] = sb.toString();
            LogUtils.b("UmcIPUtils", "onlyMobileDataIp " + z + " IPV6 ip：" + sb.toString());
            LogUtils.b("UmcIPUtils", "onlyMobileDataIp " + z + " IPV4 ip：" + sb2.toString());
            return strArr;
        } catch (Exception e) {
            e.printStackTrace();
            return strArr;
        }
    }
}