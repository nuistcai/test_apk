package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import com.tencent.bugly.BuglyStrategy;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class s {
    private static s b;
    public Map<String, String> a = null;
    private Context c;

    private s(Context context) {
        this.c = context;
    }

    public static s a(Context context) {
        if (b == null) {
            b = new s(context);
        }
        return b;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0183 A[Catch: all -> 0x0177, TRY_LEAVE, TryCatch #5 {all -> 0x0177, blocks: (B:24:0x009e, B:26:0x00a6, B:30:0x00b7, B:29:0x00b5, B:50:0x00e2, B:52:0x00ea, B:64:0x0119, B:66:0x0120, B:68:0x0126, B:82:0x0140, B:85:0x0162, B:99:0x017d, B:101:0x0183), top: B:123:0x009e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] a(String str, byte[] bArr, v vVar, Map<String, String> map) {
        int i;
        int responseCode;
        int i2 = 0;
        if (str == null) {
            x.e("Failed for no URL.", new Object[0]);
            return null;
        }
        long length = bArr == null ? 0L : bArr.length;
        int i3 = 1;
        x.c("request: %s, send: %d (pid=%d | tid=%d)", str, Long.valueOf(length), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        String str2 = str;
        int i4 = 0;
        int i5 = 0;
        boolean z = false;
        while (i4 <= 0 && i5 <= 0) {
            if (z) {
                i = i4;
                z = false;
            } else {
                int i6 = i4 + 1;
                if (i6 > i3) {
                    x.c("try time: " + i6, new Object[i2]);
                    SystemClock.sleep(new Random(System.currentTimeMillis()).nextInt(10000) + 10000);
                }
                i = i6;
            }
            String strC = com.tencent.bugly.crashreport.common.info.b.c(this.c);
            if (strC == null) {
                x.d("Failed to request for network not avail", new Object[i2]);
                i4 = i;
                i3 = 1;
            } else {
                vVar.a(length);
                HttpURLConnection httpURLConnectionA = a(str2, bArr, strC, map);
                if (httpURLConnectionA != null) {
                    try {
                        try {
                            responseCode = httpURLConnectionA.getResponseCode();
                        } finally {
                        }
                    } catch (IOException e) {
                        e = e;
                    }
                    if (responseCode == 200) {
                        this.a = a(httpURLConnectionA);
                        byte[] bArrB = b(httpURLConnectionA);
                        vVar.b(bArrB == null ? 0L : bArrB.length);
                        try {
                            httpURLConnectionA.disconnect();
                        } catch (Throwable th) {
                            if (!x.a(th)) {
                                th.printStackTrace();
                            }
                        }
                        return bArrB;
                    }
                    if (responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307) {
                        try {
                            String headerField = httpURLConnectionA.getHeaderField("Location");
                            if (headerField == null) {
                                try {
                                    x.e("Failed to redirect: %d" + responseCode, new Object[0]);
                                    try {
                                        httpURLConnectionA.disconnect();
                                        return null;
                                    } catch (Throwable th2) {
                                        if (x.a(th2)) {
                                            return null;
                                        }
                                        th2.printStackTrace();
                                        return null;
                                    }
                                } catch (IOException e2) {
                                    e = e2;
                                    z = true;
                                    if (!x.a(e)) {
                                    }
                                    try {
                                        httpURLConnectionA.disconnect();
                                    } catch (Throwable th3) {
                                        if (!x.a(th3)) {
                                            th3.printStackTrace();
                                        }
                                    }
                                    i4 = i;
                                    i2 = 0;
                                    i3 = 1;
                                }
                            } else {
                                i5++;
                                try {
                                    try {
                                        Object[] objArr = new Object[2];
                                        objArr[0] = Integer.valueOf(responseCode);
                                        try {
                                            objArr[1] = headerField;
                                            x.c("redirect code: %d ,to:%s", objArr);
                                            str2 = headerField;
                                            i = 0;
                                            z = true;
                                        } catch (IOException e3) {
                                            e = e3;
                                            str2 = headerField;
                                            i = 0;
                                            z = true;
                                            if (!x.a(e)) {
                                            }
                                            httpURLConnectionA.disconnect();
                                            i4 = i;
                                            i2 = 0;
                                            i3 = 1;
                                        }
                                    } catch (IOException e4) {
                                        e = e4;
                                        str2 = headerField;
                                        i = 0;
                                        z = true;
                                        if (!x.a(e)) {
                                        }
                                        httpURLConnectionA.disconnect();
                                        i4 = i;
                                        i2 = 0;
                                        i3 = 1;
                                    }
                                } catch (IOException e5) {
                                    e = e5;
                                }
                            }
                        } catch (IOException e6) {
                            e = e6;
                        }
                    }
                    try {
                        x.d("response code " + responseCode, new Object[0]);
                        long contentLength = httpURLConnectionA.getContentLength();
                        if (contentLength < 0) {
                            contentLength = 0;
                        }
                        vVar.b(contentLength);
                        try {
                            httpURLConnectionA.disconnect();
                        } catch (Throwable th4) {
                            if (!x.a(th4)) {
                                th4.printStackTrace();
                            }
                        }
                    } catch (IOException e7) {
                        e = e7;
                        if (!x.a(e)) {
                            e.printStackTrace();
                        }
                        httpURLConnectionA.disconnect();
                        i4 = i;
                        i2 = 0;
                        i3 = 1;
                    }
                    i4 = i;
                } else {
                    x.c("Failed to execute post.", new Object[0]);
                    vVar.b(0L);
                    i4 = i;
                }
                i2 = 0;
                i3 = 1;
            }
        }
        return null;
    }

    private static Map<String, String> a(HttpURLConnection httpURLConnection) {
        HashMap map = new HashMap();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List<String> list = headerFields.get(str);
            if (list.size() > 0) {
                map.put(str, list.get(0));
            }
        }
        return map;
    }

    private static byte[] b(HttpURLConnection httpURLConnection) {
        BufferedInputStream bufferedInputStream;
        if (httpURLConnection == null) {
            return null;
        }
        try {
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        } catch (Throwable th) {
            th = th;
            bufferedInputStream = null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i = bufferedInputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                bufferedInputStream.close();
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
            return byteArray;
        } catch (Throwable th3) {
            th = th3;
            try {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                u.a().b(true);
                return null;
            } finally {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (Throwable th4) {
                        th4.printStackTrace();
                    }
                }
            }
        }
    }

    private HttpURLConnection a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            x.e("destUrl is null.", new Object[0]);
            return null;
        }
        HttpURLConnection httpURLConnectionA = a(str2, str);
        if (httpURLConnectionA == null) {
            x.e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            httpURLConnectionA.setRequestProperty("wup_version", "3.0");
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    httpURLConnectionA.setRequestProperty(entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8"));
                }
            }
            httpURLConnectionA.setRequestProperty("A37", URLEncoder.encode(str2, "utf-8"));
            httpURLConnectionA.setRequestProperty("A38", URLEncoder.encode(str2, "utf-8"));
            OutputStream outputStream = httpURLConnectionA.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return httpURLConnectionA;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            x.e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    private static HttpURLConnection a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(str2);
            if (a.b() != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(a.b());
            } else if (str != null && str.toLowerCase(Locale.US).contains("wap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setConnectTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
                return null;
            }
            return null;
        }
    }
}