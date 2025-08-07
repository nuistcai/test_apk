package com.unicom.online.account.kernel;

import android.content.Context;
import android.net.Network;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class aa {
    public static boolean a = false;
    public static boolean b = false;

    class a implements HostnameVerifier {
        a() {
        }

        @Override // javax.net.ssl.HostnameVerifier
        public final boolean verify(String str, SSLSession sSLSession) {
            if (!TextUtils.isEmpty(str) && sSLSession != null) {
                try {
                    return ak.a(str, ((X509Certificate) sSLSession.getPeerCertificates()[0]).getSubjectDN().getName());
                } catch (SSLPeerUnverifiedException e) {
                    aj.a(e);
                }
            }
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x004f A[Catch: Exception -> 0x004b, TRY_LEAVE, TryCatch #2 {Exception -> 0x004b, blocks: (B:32:0x0047, B:36:0x004f), top: B:43:0x0047 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String a(InputStream inputStream) throws Throwable {
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Exception e) {
                e = e;
                byteArrayOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                if (0 != 0) {
                }
                if (inputStream != null) {
                }
                throw th;
            }
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
                } catch (Exception e2) {
                }
                return str;
            } catch (Exception e3) {
                e = e3;
                aj.a(e);
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Exception e4) {
                        return null;
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                return null;
            }
        } catch (Throwable th3) {
            th = th3;
            if (0 != 0) {
                try {
                    byteArrayOutputStream2.close();
                } catch (Exception e5) {
                    throw th;
                }
            }
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }

    private static String a(String str) {
        return str.contains(":") ? "[" + str + "]" : str;
    }

    public static String a(String str, String str2) throws IOException {
        String strA = "";
        OutputStream outputStream = null;
        try {
            try {
                URL url = new URL(str);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                String protocol = url.getProtocol();
                if (an.b(protocol).booleanValue()) {
                    "https".equals(protocol.toLowerCase(Locale.getDefault()));
                }
                httpURLConnection.setConnectTimeout(50000);
                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1");
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                httpURLConnection.setRequestProperty("connection", "keep-alive");
                httpURLConnection.setRequestProperty("api-protocol", "1.1");
                httpURLConnection.connect();
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(str2.getBytes("utf-8"));
                outputStream.flush();
                outputStream.close();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    strA = a(inputStream);
                    httpURLConnection.disconnect();
                    inputStream.close();
                }
                aj.a("responseCode = " + responseCode + "\nresult = " + strA);
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        aj.a(e);
                    }
                }
                return strA;
            } catch (Exception e2) {
                aj.a(e2);
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e3) {
                        aj.a(e3);
                    }
                }
                return strA;
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e4) {
                    aj.a(e4);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02bb  */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v12, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r11v29 */
    /* JADX WARN: Type inference failed for: r11v30 */
    /* JADX WARN: Type inference failed for: r11v31 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String b(Context context, String str, HashMap<String, String> map, Object obj) throws Throwable {
        String host;
        HttpURLConnection httpURLConnection;
        String strReplaceFirst = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            host = new URL(strReplaceFirst).getHost();
        } catch (MalformedURLException e) {
            aj.a(e);
            host = strReplaceFirst;
        }
        if (host.contains(i.d()) && b && a) {
            if (!TextUtils.isEmpty(ak.a)) {
                strReplaceFirst = strReplaceFirst.replaceFirst(i.d(), a(ak.a));
            }
            b = false;
        }
        if (strReplaceFirst.contains("nisportal")) {
            e.a().a = 1;
        }
        aj.a("\n*********************\nrequestGetHttp() requestUrl - >" + strReplaceFirst.substring(0, 30) + "...\n ...\n \n*********************\n");
        int i = Build.VERSION.SDK_INT;
        ?? r11 = i;
        if (i < 21) {
            int iB = al.b(context);
            r11 = iB;
            if (iB == 1) {
                ag agVar = new ag();
                ag.a(context, host);
                r11 = agVar;
            }
        }
        try {
            try {
                URL url = new URL(strReplaceFirst);
                httpURLConnection = (HttpURLConnection) ((obj == null || Build.VERSION.SDK_INT < 21) ? url.openConnection() : ((Network) obj).openConnection(url));
            } catch (Exception e2) {
                e = e2;
                httpURLConnection = null;
            } catch (Throwable th) {
                th = th;
                r11 = 0;
                if (r11 != 0) {
                }
                throw th;
            }
            try {
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(false);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("GET");
                boolean zC = ac.c();
                httpURLConnection.setInstanceFollowRedirects(zC);
                HttpURLConnection.setFollowRedirects(zC);
                if (map != null) {
                    for (String str2 : map.keySet()) {
                        httpURLConnection.setRequestProperty(str2, map.get(str2));
                    }
                }
                httpURLConnection.addRequestProperty("Connection", "close");
                aj.a("TAG\thttpsURLConnection.connect();\n");
                g gVar = new g();
                gVar.a = strReplaceFirst.substring(0, strReplaceFirst.indexOf("?"));
                gVar.b = System.currentTimeMillis();
                httpURLConnection.connect();
                gVar.c = System.currentTimeMillis();
                gVar.d = 1L;
                e.a().a(gVar);
                aj.a("connect cost:" + (System.currentTimeMillis() - jCurrentTimeMillis));
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                int responseCode = httpURLConnection.getResponseCode();
                aj.a("\n*********************\nrequestGetHttp() statusCode - >" + responseCode + "\n*********************\n");
                aj.a("response cost:" + (System.currentTimeMillis() - jCurrentTimeMillis2));
                if (responseCode == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String strA = a(inputStream);
                    httpURLConnection.getInputStream().close();
                    httpURLConnection.disconnect();
                    inputStream.close();
                    if (!TextUtils.isEmpty(strA)) {
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return strA;
                    }
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 410012);
                    jSONObject.put(NotificationCompat.CATEGORY_MESSAGE, "返回数据体为空");
                    jSONObject.put("data", "requestUrl:".concat(String.valueOf(strReplaceFirst)));
                    String string = jSONObject.toString();
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return string;
                }
                if (responseCode != 302) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("code", 410010);
                    jSONObject2.put(NotificationCompat.CATEGORY_MESSAGE, "Https状态码错误".concat(String.valueOf(responseCode)));
                    jSONObject2.put("data", "requestUrl:".concat(String.valueOf(strReplaceFirst)));
                    String string2 = jSONObject2.toString();
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return string2;
                }
                String headerField = httpURLConnection.getHeaderField("Location");
                aj.a("redirectUrl is \n".concat(String.valueOf(headerField)));
                aj.a("System.currentTimeMillis() is  \n" + System.currentTimeMillis());
                if (TextUtils.isEmpty(headerField)) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("code", 410013);
                    jSONObject3.put(NotificationCompat.CATEGORY_MESSAGE, "无跳转地址");
                    jSONObject3.put("data", host);
                    String string3 = jSONObject3.toString();
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return string3;
                }
                if (headerField.startsWith("https")) {
                    String strA2 = a(context, headerField, null, obj);
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return strA2;
                }
                aj.a("warning http redirectUrl \n" + System.currentTimeMillis());
                String strB = b(context, headerField, null, obj);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return strB;
            } catch (Exception e3) {
                e = e3;
                aj.a(e);
                try {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("code", 410024);
                    jSONObject4.put(NotificationCompat.CATEGORY_MESSAGE, "http异常" + e.getMessage());
                    jSONObject4.put("data", "requestUrl->".concat(String.valueOf(strReplaceFirst)));
                    String string4 = jSONObject4.toString();
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return string4;
                } catch (Exception e4) {
                    if (httpURLConnection == null) {
                        return null;
                    }
                    httpURLConnection.disconnect();
                    return null;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (r11 != 0) {
                r11.disconnect();
            }
            throw th;
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:163:0x04a0 -> B:234:0x0185). Please report as a decompilation issue!!! */
    public final java.lang.String a(android.content.Context r23, java.lang.String r24, java.util.HashMap<java.lang.String, java.lang.String> r25, java.lang.Object r26) {
        /*
            Method dump skipped, instructions count: 1561
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unicom.online.account.kernel.aa.a(android.content.Context, java.lang.String, java.util.HashMap, java.lang.Object):java.lang.String");
    }
}