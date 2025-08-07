package cn.fly.tools.network;

import cn.fly.commons.C0041r;
import cn.fly.commons.m;
import cn.fly.commons.q;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.EverythingKeeper;
import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ReflectHelper;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import kotlin.text.Typography;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

/* loaded from: classes.dex */
public class NetworkHelper implements EverythingKeeper {
    private static final String CONTENT_TYPE_JSON = "application/json";
    public static int connectionTimeout;
    public static int readTimout;
    public boolean instanceFollowRedirects = followRedirects;
    private static boolean followRedirects = true;
    private static final String CONTENT_TYPE_FORM = m.a("033flli^fk^efkOfkfm%gn@gkjmhihihijmghfmflfhjmfifl,ihge7fmfe$hHfe");

    public static class NetworkTimeOut implements PublicMemberKeeper {
        public int connectionTimeout;
        public int readTimout;
    }

    public String httpGet(String str, HashMap<String, Object> map, HashMap<String, String> map2) throws Throwable {
        NetworkTimeOut networkTimeOut = new NetworkTimeOut();
        networkTimeOut.readTimout = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
        networkTimeOut.connectionTimeout = 10000;
        return httpGetNew(str, map, map2, networkTimeOut);
    }

    public String httpGetNew(String str, HashMap<String, Object> map, HashMap<String, String> map2, NetworkTimeOut networkTimeOut) throws Throwable {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        InputStreamReader inputStreamReader2;
        FlyLog.getInstance().d(String.format("hgt: %s", str) + "\n" + String.format("hd: %s", map2), new Object[0]);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (map != null) {
            String strRequestParamsToUrl = requestParamsToUrl(map);
            if (strRequestParamsToUrl.length() > 0) {
                str = str + "?" + strRequestParamsToUrl;
            }
        }
        HttpURLConnection connection = getConnection(str, networkTimeOut);
        setHeader(connection, map2);
        connection.setInstanceFollowRedirects(this.instanceFollowRedirects);
        connection.connect();
        int responseCode = connection.getResponseCode();
        BufferedReader bufferedReader2 = null;
        if (responseCode == 200) {
            StringBuilder sb = new StringBuilder();
            try {
                inputStreamReader2 = new InputStreamReader(connection.getInputStream(), Charset.forName("utf-8"));
                try {
                    BufferedReader bufferedReader3 = new BufferedReader(inputStreamReader2);
                    try {
                        for (String line = bufferedReader3.readLine(); line != null; line = bufferedReader3.readLine()) {
                            if (sb.length() > 0) {
                                sb.append('\n');
                            }
                            sb.append(line);
                        }
                        C0041r.a(bufferedReader3, inputStreamReader2);
                        connection.disconnect();
                        String string = sb.toString();
                        FlyLog.getInstance().d("use time: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
                        return string;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader3;
                        C0041r.a(bufferedReader2, inputStreamReader2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStreamReader2 = null;
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            try {
                inputStreamReader = new InputStreamReader(connection.getErrorStream(), Charset.forName("utf-8"));
                try {
                    bufferedReader = new BufferedReader(inputStreamReader);
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Throwable th5) {
                th = th5;
                inputStreamReader = null;
            }
            try {
                for (String line2 = bufferedReader.readLine(); line2 != null; line2 = bufferedReader.readLine()) {
                    if (sb2.length() > 0) {
                        sb2.append('\n');
                    }
                    sb2.append(line2);
                }
                C0041r.a(bufferedReader, inputStreamReader);
                connection.disconnect();
                HashMap map3 = new HashMap();
                map3.put(m.a("005h@flflfmfl"), sb2.toString());
                map3.put(m.a("006Vhk*kfkQfihk"), Integer.valueOf(responseCode));
                throw new Throwable(HashonHelper.fromHashMap(map3));
            } catch (Throwable th6) {
                th = th6;
                bufferedReader2 = bufferedReader;
                C0041r.a(bufferedReader2, inputStreamReader);
                throw th;
            }
        }
    }

    public String httpPostNew(String str, HashMap<String, Object> map, HashMap<String, String> map2, NetworkTimeOut networkTimeOut) throws Throwable {
        return postRequest(str, map, map2, networkTimeOut, CONTENT_TYPE_FORM, null);
    }

    private String postRequest(String str, HashMap<String, Object> map, HashMap<String, String> map2, NetworkTimeOut networkTimeOut, String str2, HttpResponseCallback httpResponseCallback) throws Throwable {
        OutputStream outputStream;
        long jCurrentTimeMillis = System.currentTimeMillis();
        FlyLog.getInstance().d("postRequest: " + str + "\nhd: " + map2, new Object[0]);
        HttpURLConnection connection = getConnection(str, networkTimeOut);
        connection.setDoOutput(true);
        connection.setRequestProperty(m.a("0104gffmOgghekYfkfmVg"), "Keep-Alive");
        connection.setRequestProperty("Content-Type", str2);
        if (map2 != null) {
            for (Map.Entry<String, String> entry : map2.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        StringPart stringPart = new StringPart();
        if (map != null) {
            if (CONTENT_TYPE_JSON.equals(str2)) {
                stringPart.append(HashonHelper.fromHashMap(map));
            } else {
                stringPart.append(requestParamsToUrl(map));
            }
        }
        if (CONTENT_TYPE_JSON.equals(str2)) {
            connection.setChunkedStreamingMode(0);
        } else if (CONTENT_TYPE_FORM.equals(str2)) {
            connection.setFixedLengthStreamingMode((int) stringPart.length());
        }
        connection.setInstanceFollowRedirects(this.instanceFollowRedirects);
        connection.connect();
        InputStream inputStream = null;
        try {
            outputStream = connection.getOutputStream();
        } catch (Throwable th) {
            th = th;
            outputStream = null;
        }
        try {
            inputStream = stringPart.toInputStream();
            transferData(inputStream, outputStream);
            C0041r.a(inputStream, outputStream);
            return handleResponse(connection, connection.getResponseCode(), httpResponseCallback, jCurrentTimeMillis);
        } catch (Throwable th2) {
            th = th2;
            C0041r.a(inputStream, outputStream);
            throw th;
        }
    }

    private void transferData(InputStream inputStream, OutputStream outputStream) throws Throwable {
        byte[] bArr = new byte[65536];
        while (true) {
            int i = inputStream.read(bArr);
            if (i > 0) {
                outputStream.write(bArr, 0, i);
            } else {
                outputStream.flush();
                return;
            }
        }
    }

    private String handleResponse(HttpURLConnection httpURLConnection, int i, HttpResponseCallback httpResponseCallback, long j) throws Throwable {
        try {
            if (i == 200 || i < 300) {
                if (httpResponseCallback != null) {
                    httpResponseCallback.onResponse(new HttpConnectionImpl23(httpURLConnection));
                    FlyLog.getInstance().i("use time: " + (System.currentTimeMillis() - j));
                    httpURLConnection.disconnect();
                    return null;
                }
                return readStream(httpURLConnection.getInputStream());
            }
            String stream = readStream(httpURLConnection.getErrorStream());
            HashMap map = new HashMap();
            map.put(m.a("005hOflflfmfl"), stream);
            map.put(m.a("006]hk4kfk4fihk"), Integer.valueOf(i));
            throw new Throwable(HashonHelper.fromHashMap(map));
        } finally {
            httpURLConnection.disconnect();
        }
    }

    private String readStream(InputStream inputStream) throws Throwable {
        InputStreamReader inputStreamReader;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            try {
                BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader);
                while (true) {
                    try {
                        String line = bufferedReader2.readLine();
                        if (line != null) {
                            if (sb.length() > 0) {
                                sb.append('\n');
                            }
                            sb.append(line);
                        } else {
                            C0041r.a(bufferedReader2, inputStreamReader);
                            return sb.toString();
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        C0041r.a(bufferedReader, inputStreamReader);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStreamReader = null;
        }
    }

    /* JADX WARN: Finally extract failed */
    public void httpPostWithBytes(String str, byte[] bArr, HashMap<String, String> map, int i, HttpResponseCallback httpResponseCallback, NetworkTimeOut networkTimeOut) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        OutputStream outputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        DataOutputStream dataOutputStream;
        byte[] bytes;
        long jCurrentTimeMillis = System.currentTimeMillis();
        FlyLog.getInstance().d("hpt: " + str, new Object[0]);
        HttpURLConnection connection = getConnection(str, networkTimeOut);
        connection.setDoOutput(true);
        if (i >= 0) {
            connection.setChunkedStreamingMode(0);
        }
        setHeader(connection, map);
        connection.setRequestProperty(m.a("010XgffmKgghekHfkfm_g"), "Keep-Alive");
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setInstanceFollowRedirects(this.instanceFollowRedirects);
        connection.connect();
        try {
            outputStream = connection.getOutputStream();
        } catch (Throwable th) {
            th = th;
            byteArrayInputStream = null;
            outputStream = null;
        }
        try {
            String strA = q.a();
            if (strA == null) {
                strA = "";
            }
            bytes = strA.getBytes("utf-8");
            byteArrayOutputStream = new ByteArrayOutputStream();
        } catch (Throwable th2) {
            th = th2;
            byteArrayInputStream = null;
            byteArrayOutputStream = null;
            dataOutputStream = null;
            connection.disconnect();
            C0041r.a(byteArrayInputStream, outputStream, dataOutputStream, byteArrayOutputStream);
            FlyLog.getInstance().d("use time: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
            throw th;
        }
        try {
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeInt(bytes.length);
                dataOutputStream.write(bytes);
                dataOutputStream.write(bArr);
                dataOutputStream.flush();
                byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                try {
                    transferData(byteArrayInputStream, outputStream);
                    if (httpResponseCallback != null) {
                        try {
                            httpResponseCallback.onResponse(new HttpConnectionImpl23(connection));
                            connection.disconnect();
                        } finally {
                        }
                    } else {
                        connection.disconnect();
                    }
                    connection.disconnect();
                    C0041r.a(byteArrayInputStream, outputStream, dataOutputStream, byteArrayOutputStream);
                    FlyLog.getInstance().d("use time: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
                } catch (Throwable th3) {
                    th = th3;
                    connection.disconnect();
                    C0041r.a(byteArrayInputStream, outputStream, dataOutputStream, byteArrayOutputStream);
                    FlyLog.getInstance().d("use time: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                byteArrayInputStream = null;
            }
        } catch (Throwable th5) {
            th = th5;
            byteArrayInputStream = null;
            dataOutputStream = null;
            connection.disconnect();
            C0041r.a(byteArrayInputStream, outputStream, dataOutputStream, byteArrayOutputStream);
            FlyLog.getInstance().d("use time: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
            throw th;
        }
    }

    public void download(String str, final OutputStream outputStream, NetworkTimeOut networkTimeOut) throws Throwable {
        final byte[] bArr = new byte[1024];
        rawGet(str, new RawNetworkCallback() { // from class: cn.fly.tools.network.NetworkHelper.1
            @Override // cn.fly.tools.network.RawNetworkCallback
            public void onResponse(InputStream inputStream) throws Throwable {
                int i = inputStream.read(bArr);
                while (i != -1) {
                    outputStream.write(bArr, 0, i);
                    i = inputStream.read(bArr);
                }
            }
        }, networkTimeOut);
        outputStream.flush();
    }

    public void rawGet(String str, RawNetworkCallback rawNetworkCallback, NetworkTimeOut networkTimeOut) throws Throwable {
        rawGet(str, new HashMap<>(), rawNetworkCallback, networkTimeOut);
    }

    public void rawGet(String str, HashMap<String, String> map, RawNetworkCallback rawNetworkCallback, NetworkTimeOut networkTimeOut) throws Throwable {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        long jCurrentTimeMillis = System.currentTimeMillis();
        FlyLog.getInstance().d("rawGet: " + str, new Object[0]);
        HttpURLConnection connection = getConnection(str, networkTimeOut);
        setHeader(connection, map);
        connection.setInstanceFollowRedirects(this.instanceFollowRedirects);
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            if (rawNetworkCallback != null) {
                InputStream inputStream = connection.getInputStream();
                try {
                    rawNetworkCallback.onResponse(inputStream);
                    C0041r.a(inputStream);
                    connection.disconnect();
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        C0041r.a(inputStream);
                        connection.disconnect();
                        throw th2;
                    }
                }
            } else {
                connection.disconnect();
            }
            FlyLog.getInstance().d("use time: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
            return;
        }
        if (isRedirects(connection)) {
            rawGet(connection.getHeaderField(m.a("008YhgfmNefkEfkfm g")), new HashMap<>(), rawNetworkCallback, networkTimeOut);
            return;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader2 = null;
        try {
            inputStreamReader = new InputStreamReader(connection.getErrorStream(), Charset.forName("utf-8"));
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            inputStreamReader = null;
        }
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                if (sb.length() > 0) {
                    sb.append('\n');
                }
                sb.append(line);
            }
            C0041r.a(bufferedReader, inputStreamReader);
            connection.disconnect();
            HashMap map2 = new HashMap();
            map2.put(m.a("005h!flflfmfl"), sb.toString());
            map2.put(m.a("006QhkPkfkNfihk"), Integer.valueOf(responseCode));
            throw new Throwable(HashonHelper.fromHashMap(map2));
        } catch (Throwable th5) {
            th = th5;
            bufferedReader2 = bufferedReader;
            C0041r.a(bufferedReader2, inputStreamReader);
            throw th;
        }
    }

    public void rawGet(String str, HttpResponseCallback httpResponseCallback, NetworkTimeOut networkTimeOut) throws Throwable {
        rawGet(str, new HashMap<>(), httpResponseCallback, networkTimeOut);
    }

    public void rawGet(String str, HashMap<String, String> map, HttpResponseCallback httpResponseCallback, NetworkTimeOut networkTimeOut) throws Throwable {
        long jCurrentTimeMillis = System.currentTimeMillis();
        FlyLog.getInstance().d("rawGet: " + str, new Object[0]);
        HttpURLConnection connection = getConnection(str, networkTimeOut);
        setHeader(connection, map);
        connection.setInstanceFollowRedirects(this.instanceFollowRedirects);
        connection.connect();
        if (isRedirects(connection)) {
            rawGet(connection.getHeaderField(m.a("008%hgfm9efk*fkfm<g")), new HashMap<>(), httpResponseCallback, networkTimeOut);
        } else if (httpResponseCallback != null) {
            try {
                httpResponseCallback.onResponse(new HttpConnectionImpl23(connection));
                connection.disconnect();
            } finally {
            }
        }
        FlyLog.getInstance().d("use time: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
    }

    public void rawPost(String str, HashMap<String, String> map, HTTPPart hTTPPart, int i, HttpResponseCallback httpResponseCallback, NetworkTimeOut networkTimeOut) throws Throwable {
        OutputStream outputStream;
        long jCurrentTimeMillis = System.currentTimeMillis();
        FlyLog.getInstance().d("hptr: " + str, new Object[0]);
        HttpURLConnection connection = getConnection(str, networkTimeOut);
        connection.setDoOutput(true);
        if (i >= 0) {
            connection.setChunkedStreamingMode(0);
        }
        setHeader(connection, map);
        connection.setInstanceFollowRedirects(this.instanceFollowRedirects);
        connection.connect();
        InputStream inputStream = null;
        try {
            outputStream = connection.getOutputStream();
            try {
                inputStream = hTTPPart.toInputStream();
                byte[] bArr = new byte[65536];
                for (int i2 = inputStream.read(bArr); i2 > 0; i2 = inputStream.read(bArr)) {
                    outputStream.write(bArr, 0, i2);
                }
                outputStream.flush();
                C0041r.a(inputStream, outputStream);
                if (httpResponseCallback != null) {
                    try {
                        httpResponseCallback.onResponse(new HttpConnectionImpl23(connection));
                        connection.disconnect();
                    } finally {
                    }
                }
                FlyLog.getInstance().d("use time: " + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
            } catch (Throwable th) {
                th = th;
                C0041r.a(inputStream, outputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            outputStream = null;
        }
    }

    public String requestParamsToUrl(HashMap<String, Object> map) throws Throwable {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String strUrlEncode = Data.urlEncode(entry.getKey(), "utf-8");
            String strUrlEncode2 = entry.getValue() == null ? "" : Data.urlEncode(String.valueOf(entry.getValue()), "utf-8");
            if (sb.length() > 0) {
                sb.append(Typography.amp);
            }
            sb.append(strUrlEncode).append('=').append(strUrlEncode2);
        }
        return sb.toString();
    }

    public HttpURLConnection getConnection(String str, NetworkTimeOut networkTimeOut) throws Throwable {
        Object staticField;
        boolean z;
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        String strA = m.a("012HfhNhkjVfmfehefmgj%hgIhk");
        try {
            staticField = ReflectHelper.getInstanceField(httpURLConnection, strA);
        } catch (Throwable th) {
            staticField = null;
        }
        if (staticField != null) {
            z = false;
        } else {
            strA = "PERMITTED_USER_METHODS";
            try {
                staticField = ReflectHelper.getStaticField("HttpURLConnection", "PERMITTED_USER_METHODS");
                z = true;
            } catch (Throwable th2) {
                z = true;
            }
        }
        if (staticField != null) {
            String[] strArr = (String[]) staticField;
            String[] strArr2 = new String[strArr.length + 1];
            System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
            strArr2[strArr.length] = m.a("005Ninhfhegfhm");
            if (z) {
                ReflectHelper.setStaticField("HttpURLConnection", strA, strArr2);
            } else {
                ReflectHelper.setInstanceField(httpURLConnection, strA, strArr2);
            }
        }
        System.setProperty("http.keepAlive", Bugly.SDK_IS_DEV);
        if (httpURLConnection instanceof HttpsURLConnection) {
            X509HostnameVerifier x509HostnameVerifier = SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
            SSLContext sSLContext = SSLContext.getInstance(m.a("003@hehggn"));
            TrustManager[] trustManagerArr = new TrustManager[0];
            try {
                trustManagerArr = new TrustManager[]{(TrustManager) getTrustManager(httpsURLConnection.getURL().getHost())};
            } catch (Throwable th3) {
                FlyLog.getInstance().e(th3);
            }
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            httpsURLConnection.setSSLSocketFactory(sSLContext.getSocketFactory());
            httpsURLConnection.setHostnameVerifier(x509HostnameVerifier);
        }
        int i = networkTimeOut == null ? connectionTimeout : networkTimeOut.connectionTimeout;
        if (i > 0) {
            httpURLConnection.setConnectTimeout(i);
        }
        int i2 = networkTimeOut == null ? readTimout : networkTimeOut.readTimout;
        if (i2 > 0) {
            httpURLConnection.setReadTimeout(i2);
        }
        return httpURLConnection;
    }

    public String jsonPost(String str, HashMap<String, Object> map, HashMap<String, String> map2, NetworkTimeOut networkTimeOut) throws Throwable {
        final HashMap map3 = new HashMap();
        postRequest(str, map, map2, networkTimeOut, CONTENT_TYPE_JSON, new HttpResponseCallback() { // from class: cn.fly.tools.network.NetworkHelper.2
            @Override // cn.fly.tools.network.HttpResponseCallback
            public void onResponse(HttpConnection httpConnection) throws Throwable {
                InputStreamReader inputStreamReader;
                BufferedReader bufferedReader;
                InputStreamReader inputStreamReader2;
                int responseCode = httpConnection.getResponseCode();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader2 = null;
                if (responseCode == 200 || responseCode == 201) {
                    try {
                        inputStreamReader = new InputStreamReader(httpConnection.getInputStream(), Charset.forName("utf-8"));
                        try {
                            bufferedReader = new BufferedReader(inputStreamReader);
                        } catch (Throwable th) {
                            th = th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStreamReader = null;
                    }
                    try {
                        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                            if (sb.length() > 0) {
                                sb.append('\n');
                            }
                            sb.append(line);
                        }
                        C0041r.a(bufferedReader, inputStreamReader);
                        map3.put(m.a("0035flWhMhk"), sb.toString());
                        return;
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedReader2 = bufferedReader;
                        C0041r.a(bufferedReader2, inputStreamReader);
                        throw th;
                    }
                }
                try {
                    inputStreamReader2 = new InputStreamReader(httpConnection.getErrorStream(), Charset.forName("utf-8"));
                    try {
                        BufferedReader bufferedReader3 = new BufferedReader(inputStreamReader2);
                        try {
                            for (String line2 = bufferedReader3.readLine(); line2 != null; line2 = bufferedReader3.readLine()) {
                                if (sb.length() > 0) {
                                    sb.append('\n');
                                }
                                sb.append(line2);
                            }
                            C0041r.a(bufferedReader3, inputStreamReader2);
                            HashMap map4 = new HashMap();
                            map4.put(m.a("005hCflflfmfl"), sb.toString());
                            map4.put(m.a("006Xhk@kfk]fihk"), Integer.valueOf(responseCode));
                            new HashonHelper();
                            throw new Throwable(HashonHelper.fromHashMap(map4));
                        } catch (Throwable th4) {
                            th = th4;
                            bufferedReader2 = bufferedReader3;
                            C0041r.a(bufferedReader2, inputStreamReader2);
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    inputStreamReader2 = null;
                }
            }
        });
        if (!map3.containsKey(m.a("003?fl@h(hk"))) {
            return null;
        }
        return (String) map3.get(m.a("003 fl)h9hk"));
    }

    public void jsonPost(String str, HashMap<String, Object> map, HashMap<String, String> map2, NetworkTimeOut networkTimeOut, HttpResponseCallback httpResponseCallback) throws Throwable {
        postRequest(str, map, map2, networkTimeOut, CONTENT_TYPE_JSON, httpResponseCallback);
    }

    public void setHeader(URLConnection uRLConnection, HashMap<String, String> map) {
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                uRLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    public static Object getTrustManager(String str) throws Throwable {
        Class<?> cls = Class.forName(m.a("030Jji5f$ff^fTgkfnBghkVfnhkhk9iKfniijkhjjlheflfihkSkXje-fgf[gl'hRfl"));
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{cls}, new a(str));
    }

    public static boolean isRedirects(HttpURLConnection httpURLConnection) {
        try {
            if (httpURLConnection.getResponseCode() != 301 && httpURLConnection.getResponseCode() != 302 && httpURLConnection.getResponseCode() != 304 && httpURLConnection.getResponseCode() != 307) {
                if (httpURLConnection.getResponseCode() != 308) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return false;
        }
    }

    private static class a implements InvocationHandler {
        private Object a;
        private String b;

        private a(String str) throws IllegalAccessException, NoSuchMethodException, NoSuchAlgorithmException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
            try {
                this.b = str;
                Method declaredMethod = Class.forName(m.a("0333jiVfHff6f[gkfnHghk(fnhkhk+iYfnheflfihk<kUje;fgfIglIh:flie.fek^fmflge")).getDeclaredMethod(m.a("011Vgl6hkYggUgZhk_kfgeh"), String.class);
                declaredMethod.setAccessible(true);
                Object objInvoke = declaredMethod.invoke(null, m.a("004Aiijkhjjl"));
                Method method = objInvoke.getClass().getMethod(m.a("004Nfk[g.fkWk"), Class.forName(m.a("022 jiLf5ff-f4fnhk<he=fiflfkRkKgefnkeYh<gegnEk-fmflAh")));
                method.setAccessible(true);
                method.invoke(objInvoke, null);
                Method method2 = objInvoke.getClass().getMethod(m.a("016<glEhk>heflfihkZk$je0fgf_glUh@flhk"), new Class[0]);
                method2.setAccessible(true);
                Object[] objArr = (Object[]) method2.invoke(objInvoke, new Object[0]);
                if (objArr == null || objArr.length == 0) {
                    throw new NoSuchAlgorithmException("no trust manager found.");
                }
                this.a = objArr[0];
            } catch (Exception e) {
                FlyLog.getInstance().d("failed to initialize the standard trust manager: " + e.getMessage(), new Object[0]);
                this.a = null;
            }
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            String name = method.getName();
            if (!name.equals(m.a("018ejhe?gjgf[i$fk7hgkSheflfihkIkh6fe"))) {
                if (name.equals(m.a("018ejheDgjgn:h4flffZhMflheflfihkPkh5fe"))) {
                    Object[] objArr2 = (Object[]) objArr[0];
                    String str = (String) objArr[1];
                    if (objArr2 == null) {
                        throw new IllegalArgumentException("there were no certificates.");
                    }
                    if (objArr2.length == 0) {
                        throw new IllegalArgumentException("certificates is empty.");
                    }
                    if (this.a != null) {
                        for (Object obj2 : objArr2) {
                            Method declaredMethod = obj2.getClass().getDeclaredMethod(m.a("013ejhe-gjim!fi(fkfefk1kZge"), new Class[0]);
                            declaredMethod.setAccessible(true);
                            declaredMethod.invoke(obj2, new Object[0]);
                        }
                        if (DH.SyncMtd.getOSVersionIntForFly() >= 17) {
                            Object objNewInstance = Class.forName("android.net.http.X509TrustManagerExtensions").getConstructor(Class.forName(m.a("030$ji f_ff*fZgkfnYghk]fnhkhk^i7fniijkhjjlheflfihkVk[jeBfgf.gl]h$fl"))).newInstance(this.a);
                            Method declaredMethod2 = objNewInstance.getClass().getDeclaredMethod(m.a("018ejhe,gjgn+h*flffGh flheflfihk8khYfe"), Array.newInstance(Class.forName(m.a("034WjiBf@ffSf=fnhk1he:fiflfk+kAgefn6eh4fl)k,fniijkhjjlgfCh8flBk>fkghfkXefkh")), 0).getClass(), String.class, String.class);
                            declaredMethod2.setAccessible(true);
                            declaredMethod2.invoke(objNewInstance, objArr2, str, this.b);
                            return null;
                        }
                        Method declaredMethod3 = this.a.getClass().getDeclaredMethod(m.a("018ejheXgjgnPhJflffCh^flheflfihk kh2fe"), Array.newInstance(Class.forName(m.a("034Wji*fYffOfAfnhkHhe<fiflfk*kPgefn4ehPfl+k=fniijkhjjlgf?hVflNk=fkghfkDefkh")), 0).getClass(), String.class);
                        declaredMethod3.setAccessible(true);
                        declaredMethod3.invoke(this.a, objArr2, str);
                        return null;
                    }
                    throw new CertificateException("there were one more certificates but no trust manager found.");
                }
                if (name.equals(m.a("0183gl hk[hfVeehlkhLfegghkhkfi>h?flhk"))) {
                    return Array.newInstance(Class.forName(m.a("034@ji]f=ff8fEfnhk)he%fiflfk6k2gefnJehZflAkAfniijkhjjlgfFh_fl2kSfkghfk9efkh")), 0);
                }
                if (name.equals(m.a("008jfChkHjOgffmfe?h"))) {
                    return Integer.valueOf(hashCode());
                }
                if (name.equals("toString")) {
                    return toString();
                }
                return null;
            }
            return null;
        }
    }
}