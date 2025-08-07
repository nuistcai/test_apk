package com.unicom.xiaowo.account.shield.d;

import android.net.Network;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.tencent.bugly.BuglyStrategy;
import com.unicom.xiaowo.account.shield.UniAccountHelper;
import com.unicom.xiaowo.account.shield.e.d;
import com.unicom.xiaowo.account.shield.e.g;
import com.unicom.xiaowo.account.shield.e.h;
import com.unicom.xiaowo.account.shield.e.j;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class b {
    public String a(String str, HashMap<String, String> map, Network network) {
        String str2;
        HttpsURLConnection httpsURLConnection;
        g.a("url:" + str);
        if (Build.VERSION.SDK_INT < 21) {
            return c(str, map, network);
        }
        try {
            str2 = "https://" + new URL(str).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            str2 = "";
        }
        h.c(str);
        try {
            URL url = new URL(str);
            if (network != null && Build.VERSION.SDK_INT >= 21) {
                httpsURLConnection = (HttpsURLConnection) network.openConnection(url);
            } else {
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
            }
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(false);
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.setInstanceFollowRedirects(false);
            httpsURLConnection.setReadTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
            httpsURLConnection.setConnectTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() { // from class: com.unicom.xiaowo.account.shield.d.b.1
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String str3, SSLSession sSLSession) {
                    if (TextUtils.isEmpty(str3)) {
                        return false;
                    }
                    if (str3.endsWith(".wostore.cn") || str3.endsWith(".10010.com")) {
                        return true;
                    }
                    return false;
                }
            });
            if (map != null) {
                for (String str3 : map.keySet()) {
                    httpsURLConnection.setRequestProperty(str3, map.get(str3));
                }
            }
            if (str.startsWith("https://opencloud.wostore.cn/openapi/netauth/precheck/wp?")) {
                httpsURLConnection.addRequestProperty("Connection", "Keep-Alive");
            } else {
                httpsURLConnection.addRequestProperty("Connection", "close");
            }
            httpsURLConnection.connect();
            if (httpsURLConnection.getResponseCode() == 200) {
                String strA = a(httpsURLConnection.getInputStream());
                if (TextUtils.isEmpty(strA)) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 10012);
                    jSONObject.put(NotificationCompat.CATEGORY_MESSAGE, "10012");
                    jSONObject.put("data", str2);
                    return jSONObject.toString();
                }
                return strA;
            }
            if (httpsURLConnection.getResponseCode() == 302) {
                String headerField = httpsURLConnection.getHeaderField("Location");
                if (!TextUtils.isEmpty(headerField)) {
                    if (headerField.startsWith("https")) {
                        return a(headerField, null, network);
                    }
                    return b(headerField, null, network);
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("code", 10013);
                jSONObject2.put(NotificationCompat.CATEGORY_MESSAGE, "无跳转地址");
                jSONObject2.put("data", str2);
                return jSONObject2.toString();
            }
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("code", 10010);
            jSONObject3.put(NotificationCompat.CATEGORY_MESSAGE, "https状态码" + httpsURLConnection.getResponseCode());
            jSONObject3.put("data", str2);
            return jSONObject3.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            try {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("code", 10011);
                jSONObject4.put(NotificationCompat.CATEGORY_MESSAGE, "https异常" + e2.getMessage());
                jSONObject4.put("data", str2);
                return jSONObject4.toString();
            } catch (Exception e3) {
                return null;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x01c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String b(String str, HashMap<String, String> map, Network network) throws Throwable {
        String str2;
        HttpURLConnection httpURLConnection;
        if (Build.VERSION.SDK_INT < 21) {
            try {
                if (j.a(UniAccountHelper.getInstance().getApplicationContext()) == 1) {
                    new d().a(UniAccountHelper.getInstance().getApplicationContext(), str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            str2 = "http://" + new URL(str).getHost();
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
            str2 = str;
        }
        h.c(str);
        g.a(str);
        HttpURLConnection httpURLConnection2 = null;
        try {
            URL url = new URL(str);
            HttpURLConnection httpURLConnection3 = (network == null || Build.VERSION.SDK_INT < 21) ? (HttpURLConnection) url.openConnection() : (HttpURLConnection) network.openConnection(url);
            try {
                httpURLConnection3.setDoInput(true);
                httpURLConnection3.setDoOutput(false);
                httpURLConnection3.setUseCaches(false);
                httpURLConnection3.setInstanceFollowRedirects(false);
                httpURLConnection3.setReadTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                httpURLConnection3.setConnectTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                httpURLConnection3.setRequestMethod("GET");
                if (map != null) {
                    for (String str3 : map.keySet()) {
                        httpURLConnection3.setRequestProperty(str3, map.get(str3));
                    }
                }
                httpURLConnection3.addRequestProperty("Connection", "close");
                httpURLConnection3.connect();
                if (httpURLConnection3.getResponseCode() == 200) {
                    String strA = a(httpURLConnection3.getInputStream());
                    if (!TextUtils.isEmpty(strA)) {
                        if (httpURLConnection3 != null) {
                            httpURLConnection3.disconnect();
                        }
                        return strA;
                    }
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 10012);
                    jSONObject.put(NotificationCompat.CATEGORY_MESSAGE, "10012");
                    jSONObject.put("data", str2);
                    String string = jSONObject.toString();
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                    return string;
                }
                if (httpURLConnection3.getResponseCode() != 302) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", 10010);
                    jSONObject2.put(NotificationCompat.CATEGORY_MESSAGE, "https状态码" + httpURLConnection3.getResponseCode());
                    jSONObject2.put("data", str2);
                    String string2 = jSONObject2.toString();
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                    return string2;
                }
                String headerField = httpURLConnection3.getHeaderField("Location");
                if (TextUtils.isEmpty(headerField)) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("code", 10013);
                    jSONObject3.put(NotificationCompat.CATEGORY_MESSAGE, "无跳转地址");
                    jSONObject3.put("data", str2);
                    String string3 = jSONObject3.toString();
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                    return string3;
                }
                if (headerField.startsWith("https")) {
                    String strA2 = a(headerField, null, network);
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                    return strA2;
                }
                String strB = b(headerField, null, network);
                if (httpURLConnection3 != null) {
                    httpURLConnection3.disconnect();
                }
                return strB;
            } catch (Exception e3) {
                httpURLConnection = httpURLConnection3;
                e = e3;
                try {
                    e.printStackTrace();
                    try {
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject4.put("code", 10024);
                        jSONObject4.put(NotificationCompat.CATEGORY_MESSAGE, "http异常" + e.getMessage());
                        jSONObject4.put("data", str2);
                        String string4 = jSONObject4.toString();
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return string4;
                    } catch (Exception e4) {
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    th = th;
                    httpURLConnection2 = httpURLConnection;
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                httpURLConnection2 = httpURLConnection3;
                th = th2;
                if (httpURLConnection2 != null) {
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            httpURLConnection = null;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public String c(String str, HashMap<String, String> map, Network network) {
        String str2;
        HttpsURLConnection httpsURLConnection;
        g.a("url:" + str);
        if (Build.VERSION.SDK_INT < 21) {
            try {
                if (j.a(UniAccountHelper.getInstance().getApplicationContext()) == 1) {
                    new d().a(UniAccountHelper.getInstance().getApplicationContext(), str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            str2 = "https://" + new URL(str).getHost();
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
            str2 = "";
        }
        h.c(str);
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, null, new SecureRandom());
            c cVar = new c(sSLContext.getSocketFactory());
            URL url = new URL(str);
            if (network != null && Build.VERSION.SDK_INT >= 21) {
                httpsURLConnection = (HttpsURLConnection) network.openConnection(url);
            } else {
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
            }
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(false);
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.setInstanceFollowRedirects(false);
            httpsURLConnection.setReadTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
            httpsURLConnection.setConnectTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setSSLSocketFactory(cVar);
            httpsURLConnection.setHostnameVerifier(new HostnameVerifier() { // from class: com.unicom.xiaowo.account.shield.d.b.2
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String str3, SSLSession sSLSession) {
                    if (TextUtils.isEmpty(str3)) {
                        return false;
                    }
                    if (str3.endsWith(".wostore.cn") || str3.endsWith(".10010.com")) {
                        return true;
                    }
                    return false;
                }
            });
            if (map != null) {
                for (String str3 : map.keySet()) {
                    httpsURLConnection.setRequestProperty(str3, map.get(str3));
                }
            }
            if (str.startsWith("https://opencloud.wostore.cn/openapi/netauth/precheck/wp?")) {
                httpsURLConnection.addRequestProperty("Connection", "Keep-Alive");
            } else {
                httpsURLConnection.addRequestProperty("Connection", "close");
            }
            httpsURLConnection.connect();
            if (httpsURLConnection.getResponseCode() == 200) {
                String strA = a(httpsURLConnection.getInputStream());
                if (TextUtils.isEmpty(strA)) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 10012);
                    jSONObject.put(NotificationCompat.CATEGORY_MESSAGE, "10012");
                    jSONObject.put("data", str2);
                    return jSONObject.toString();
                }
                return strA;
            }
            if (httpsURLConnection.getResponseCode() == 302) {
                String headerField = httpsURLConnection.getHeaderField("Location");
                if (!TextUtils.isEmpty(headerField)) {
                    if (headerField.startsWith("https")) {
                        return a(headerField, null, network);
                    }
                    return b(headerField, null, network);
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("code", 10013);
                jSONObject2.put(NotificationCompat.CATEGORY_MESSAGE, "无跳转地址");
                jSONObject2.put("data", str2);
                return jSONObject2.toString();
            }
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("code", 10010);
            jSONObject3.put(NotificationCompat.CATEGORY_MESSAGE, "https状态码" + httpsURLConnection.getResponseCode());
            jSONObject3.put("data", str2);
            return jSONObject3.toString();
        } catch (Exception e3) {
            e3.printStackTrace();
            try {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("code", 10011);
                jSONObject4.put(NotificationCompat.CATEGORY_MESSAGE, "https异常" + e3.getMessage());
                jSONObject4.put("data", str2);
                return jSONObject4.toString();
            } catch (Exception e4) {
                return null;
            }
        }
    }

    public String a(String str, String str2) throws Throwable {
        if (Build.VERSION.SDK_INT < 21) {
            return "";
        }
        HttpsURLConnection httpsURLConnection = null;
        try {
            try {
                HttpsURLConnection httpsURLConnection2 = (HttpsURLConnection) new URL(str).openConnection();
                try {
                    httpsURLConnection2.setDoInput(true);
                    httpsURLConnection2.setDoOutput(true);
                    httpsURLConnection2.setUseCaches(false);
                    httpsURLConnection2.setInstanceFollowRedirects(true);
                    httpsURLConnection2.setReadTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                    httpsURLConnection2.setConnectTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                    httpsURLConnection2.setRequestMethod("POST");
                    httpsURLConnection2.setHostnameVerifier(new HostnameVerifier() { // from class: com.unicom.xiaowo.account.shield.d.b.3
                        @Override // javax.net.ssl.HostnameVerifier
                        public boolean verify(String str3, SSLSession sSLSession) {
                            if (TextUtils.isEmpty(str3)) {
                                return false;
                            }
                            if (str3.endsWith(".wostore.cn") || str3.endsWith(".10010.com")) {
                                return true;
                            }
                            return false;
                        }
                    });
                    httpsURLConnection2.addRequestProperty("Connection", "close");
                    httpsURLConnection2.addRequestProperty("Content-Type", FastJsonJsonView.DEFAULT_CONTENT_TYPE);
                    httpsURLConnection2.connect();
                    DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection2.getOutputStream());
                    if (str2 != null) {
                        dataOutputStream.write(str2.getBytes("UTF-8"));
                    }
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    String strA = httpsURLConnection2.getResponseCode() == 200 ? a(httpsURLConnection2.getInputStream()) : "";
                    if (httpsURLConnection2 != null) {
                        httpsURLConnection2.disconnect();
                    }
                    return strA;
                } catch (Exception e) {
                    e = e;
                    httpsURLConnection = httpsURLConnection2;
                    e.printStackTrace();
                    if (httpsURLConnection != null) {
                        httpsURLConnection.disconnect();
                    }
                    return "";
                } catch (Throwable th) {
                    th = th;
                    httpsURLConnection = httpsURLConnection2;
                    if (httpsURLConnection != null) {
                        httpsURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private String a(InputStream inputStream) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                }
                String str = new String(byteArrayOutputStream.toByteArray());
                try {
                    byteArrayOutputStream.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                }
                return str;
            } catch (Exception e2) {
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Exception e3) {
                        return null;
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                return null;
            } catch (Throwable th2) {
                th = th2;
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Exception e4) {
                        throw th;
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            byteArrayOutputStream = null;
        } catch (Throwable th3) {
            byteArrayOutputStream = null;
            th = th3;
        }
    }
}