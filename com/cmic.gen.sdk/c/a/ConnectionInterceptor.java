package com.cmic.gen.sdk.c.a;

import android.os.SystemClock;
import android.text.TextUtils;
import com.alibaba.fastjson.asm.Opcodes;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.Constants2;
import com.cmic.gen.sdk.c.Tls12SocketFactory;
import com.cmic.gen.sdk.c.TlsSniSocketFactory;
import com.cmic.gen.sdk.c.b.GetPrePhoneScripParameter;
import com.cmic.gen.sdk.c.b.RequestParameter;
import com.cmic.gen.sdk.c.b.ScripAndTokenParameter;
import com.cmic.gen.sdk.c.c.HttpGetPrephoneRequest;
import com.cmic.gen.sdk.c.c.HttpRequest;
import com.cmic.gen.sdk.c.d.HttpErrorResponse;
import com.cmic.gen.sdk.c.d.HttpSuccessResponse;
import com.cmic.gen.sdk.c.d.IResponse;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.UmcUtils;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/* compiled from: ConnectionInterceptor.java */
/* renamed from: com.cmic.gen.sdk.c.a.a, reason: use source file name */
/* loaded from: classes.dex */
public class ConnectionInterceptor implements IInterceptor {
    private static Tls12SocketFactory a;

    /* JADX WARN: Can't wrap try/catch for region: R(27:(30:7|(1:9)|11|(1:13)(1:14)|144|15|(5:142|17|(5:20|21|156|22|18)|162|23)(1:26)|27|(1:(1:34)(1:35))|36|(1:38)|39|40|(3:42|152|43)(1:44)|45|150|46|47|158|48|(2:49|(3:51|154|52)(1:161))|53|54|(1:56)|57|(1:59)(1:60)|61|62|120|123)|144|15|(0)(0)|27|(2:29|(0)(0))|36|(0)|39|40|(0)(0)|45|150|46|47|158|48|(3:49|(0)(0)|52)|53|54|(0)|57|(0)(0)|61|62|120|123) */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0217, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x021a, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x021b, code lost:
    
        r17 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0220, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0222, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0223, code lost:
    
        r17 = "remote_ip";
        r16 = r13;
        r14 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0239, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x023a, code lost:
    
        r17 = "remote_ip";
        r20 = "";
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0287 A[Catch: all -> 0x02f7, TryCatch #11 {all -> 0x02f7, blocks: (B:103:0x0283, B:105:0x0287, B:107:0x028f, B:109:0x0297), top: B:145:0x0283 }] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0305  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x007b A[Catch: all -> 0x023f, Exception -> 0x0248, TryCatch #12 {Exception -> 0x0248, all -> 0x023f, blocks: (B:3:0x003d, B:5:0x0053, B:11:0x0075, B:13:0x007b, B:14:0x009c, B:7:0x0057, B:9:0x0062), top: B:160:0x003d }] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x00be A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x009c A[Catch: all -> 0x023f, Exception -> 0x0248, TRY_LEAVE, TryCatch #12 {Exception -> 0x0248, all -> 0x023f, blocks: (B:3:0x003d, B:5:0x0053, B:11:0x0075, B:13:0x007b, B:14:0x009c, B:7:0x0057, B:9:0x0062), top: B:160:0x003d }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x01a7 A[EDGE_INSN: B:161:0x01a7->B:53:0x01a7 BREAK  A[LOOP:0: B:49:0x0191->B:52:0x0199], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0100 A[Catch: all -> 0x022f, Exception -> 0x0231, TryCatch #14 {Exception -> 0x0231, all -> 0x022f, blocks: (B:22:0x00d2, B:27:0x00f2, B:29:0x00f6, B:31:0x00fa, B:34:0x0100, B:35:0x0135, B:36:0x013c, B:38:0x015e, B:39:0x0167, B:42:0x0171), top: B:156:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0135 A[Catch: all -> 0x022f, Exception -> 0x0231, TryCatch #14 {Exception -> 0x0231, all -> 0x022f, blocks: (B:22:0x00d2, B:27:0x00f2, B:29:0x00f6, B:31:0x00fa, B:34:0x0100, B:35:0x0135, B:36:0x013c, B:38:0x015e, B:39:0x0167, B:42:0x0171), top: B:156:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x015e A[Catch: all -> 0x022f, Exception -> 0x0231, TryCatch #14 {Exception -> 0x0231, all -> 0x022f, blocks: (B:22:0x00d2, B:27:0x00f2, B:29:0x00f6, B:31:0x00fa, B:34:0x0100, B:35:0x0135, B:36:0x013c, B:38:0x015e, B:39:0x0167, B:42:0x0171), top: B:156:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0171 A[Catch: all -> 0x022f, Exception -> 0x0231, TRY_ENTER, TRY_LEAVE, TryCatch #14 {Exception -> 0x0231, all -> 0x022f, blocks: (B:22:0x00d2, B:27:0x00f2, B:29:0x00f6, B:31:0x00fa, B:34:0x0100, B:35:0x0135, B:36:0x013c, B:38:0x015e, B:39:0x0167, B:42:0x0171), top: B:156:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01e4  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0204  */
    @Override // com.cmic.gen.sdk.c.a.IInterceptor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(HttpRequest httpRequest, IResponse iResponse, ConcurrentBundle concurrentBundle) throws Throwable {
        String str;
        HttpsURLConnection httpsURLConnection;
        String str2;
        OutputStream outputStream;
        int responseCode;
        InputStream inputStream;
        int i;
        HttpErrorResponse httpErrorResponseA;
        int i2;
        String strA;
        URL url;
        final String host;
        RequestParameter requestParameterK;
        boolean z;
        Map<String, String> mapC;
        String strE;
        byte[] bArr;
        int i3;
        IResponse iResponse2 = iResponse;
        String str3 = "remote_ip";
        String str4 = "";
        LogUtils.b("ConnectionInterceptor", "请求地址: " + httpRequest.a());
        httpRequest.a(SystemClock.elapsedRealtime());
        StringBuilder sb = new StringBuilder();
        try {
            strA = httpRequest.a();
            url = new URL(strA);
            host = url.getHost();
            requestParameterK = httpRequest.k();
        } catch (Exception e) {
            e = e;
            str2 = "remote_ip";
            str = "";
            httpsURLConnection = null;
        } catch (Throwable th) {
            th = th;
            str = "";
            httpsURLConnection = null;
        }
        try {
            if ((requestParameterK instanceof ScripAndTokenParameter) || (requestParameterK instanceof GetPrePhoneScripParameter)) {
                String strB = concurrentBundle.b("remote_ip");
                if (!TextUtils.isEmpty(strB)) {
                    url = new URL(strA.replaceFirst(host, strB));
                    httpRequest.a("dnsParseResult", "0");
                    z = true;
                }
                if (httpRequest.h() == null) {
                    LogUtils.b("ConnectionInterceptor", "开始wifi下取号" + url);
                    httpsURLConnection = (HttpsURLConnection) httpRequest.h().openConnection(url);
                } else {
                    LogUtils.b("ConnectionInterceptor", "使用当前网络环境发送请求" + url);
                    httpsURLConnection = (HttpsURLConnection) url.openConnection();
                }
                mapC = httpRequest.c();
                if (mapC == null) {
                    try {
                        for (String str5 : mapC.keySet()) {
                            str = str4;
                            try {
                                httpsURLConnection.addRequestProperty(str5, mapC.get(str5));
                                str4 = str;
                                mapC = mapC;
                            } catch (Exception e2) {
                                e = e2;
                                str2 = "remote_ip";
                                outputStream = null;
                                inputStream = null;
                                i = -1;
                                try {
                                    e.printStackTrace();
                                    LogUtils.a("ConnectionInterceptor", "请求失败: " + httpRequest.a());
                                    concurrentBundle.a().a.add(e);
                                    if (!(e instanceof EOFException)) {
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    responseCode = i;
                                }
                                try {
                                    if (e instanceof UnknownHostException) {
                                    }
                                    a(outputStream);
                                    a(inputStream);
                                    if (httpsURLConnection != null) {
                                    }
                                    LogUtils.b("ConnectionInterceptor", "responseCode: " + i2);
                                    LogUtils.b("ConnectionInterceptor", "responseResult: " + (!TextUtils.isEmpty(sb) ? str : sb.toString()));
                                    switch (i2) {
                                        case Opcodes.GOTO_W /* 200 */:
                                        case 301:
                                        case 302:
                                            break;
                                    }
                                    iResponse2.a(httpErrorResponseA);
                                    return;
                                } catch (Throwable th3) {
                                    th = th3;
                                    responseCode = i2;
                                    a(outputStream);
                                    a(inputStream);
                                    if (httpsURLConnection != null) {
                                        httpsURLConnection.disconnect();
                                    }
                                    LogUtils.b("ConnectionInterceptor", "responseCode: " + responseCode);
                                    LogUtils.b("ConnectionInterceptor", "responseResult: " + (TextUtils.isEmpty(sb) ? str : sb.toString()));
                                    switch (responseCode) {
                                        case Opcodes.GOTO_W /* 200 */:
                                        case 301:
                                        case 302:
                                            a(httpRequest, concurrentBundle);
                                            iResponse2.a((HttpSuccessResponse) null);
                                            throw th;
                                        default:
                                            a(httpRequest, concurrentBundle);
                                            iResponse2.a(HttpErrorResponse.a(responseCode));
                                            throw th;
                                    }
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                outputStream = null;
                                responseCode = -1;
                                inputStream = null;
                                a(outputStream);
                                a(inputStream);
                                if (httpsURLConnection != null) {
                                }
                                LogUtils.b("ConnectionInterceptor", "responseCode: " + responseCode);
                                LogUtils.b("ConnectionInterceptor", "responseResult: " + (TextUtils.isEmpty(sb) ? str : sb.toString()));
                                switch (responseCode) {
                                    case Opcodes.GOTO_W /* 200 */:
                                    case 301:
                                    case 302:
                                        break;
                                }
                            }
                        }
                        str = str4;
                    } catch (Exception e3) {
                        e = e3;
                        str = str4;
                        str2 = "remote_ip";
                        outputStream = null;
                        inputStream = null;
                        i = -1;
                        e.printStackTrace();
                        LogUtils.a("ConnectionInterceptor", "请求失败: " + httpRequest.a());
                        concurrentBundle.a().a.add(e);
                        i2 = !(e instanceof EOFException) ? 200050 : 102102;
                        if ((e instanceof UnknownHostException) && ((httpRequest.k() instanceof ScripAndTokenParameter) || (httpRequest.k() instanceof GetPrePhoneScripParameter))) {
                            concurrentBundle.a(str2, a());
                        }
                        a(outputStream);
                        a(inputStream);
                        if (httpsURLConnection != null) {
                            httpsURLConnection.disconnect();
                        }
                        LogUtils.b("ConnectionInterceptor", "responseCode: " + i2);
                        LogUtils.b("ConnectionInterceptor", "responseResult: " + (!TextUtils.isEmpty(sb) ? str : sb.toString()));
                        switch (i2) {
                            case Opcodes.GOTO_W /* 200 */:
                            case 301:
                            case 302:
                                a(httpRequest, concurrentBundle);
                                iResponse2.a((HttpSuccessResponse) null);
                                return;
                            default:
                                a(httpRequest, concurrentBundle);
                                httpErrorResponseA = HttpErrorResponse.a(i2);
                                break;
                        }
                        iResponse2.a(httpErrorResponseA);
                        return;
                    }
                } else {
                    str = "";
                }
                if ((httpsURLConnection instanceof HttpsURLConnection) && ((requestParameterK instanceof ScripAndTokenParameter) || (requestParameterK instanceof GetPrePhoneScripParameter))) {
                    if (z) {
                        httpsURLConnection.setSSLSocketFactory(a(requestParameterK, concurrentBundle));
                    } else {
                        LogUtils.b("ConnectionInterceptor", "host = " + host);
                        httpsURLConnection.setRequestProperty("Host", host);
                        LogUtils.b("ConnectionInterceptor", "need sni handle");
                        httpsURLConnection.setSSLSocketFactory(new TlsSniSocketFactory(httpsURLConnection, httpRequest.h(), concurrentBundle));
                        httpsURLConnection.setHostnameVerifier(new HostnameVerifier() { // from class: com.cmic.gen.sdk.c.a.a.1
                            @Override // javax.net.ssl.HostnameVerifier
                            public boolean verify(String hostname, SSLSession session) {
                                return HttpsURLConnection.getDefaultHostnameVerifier().verify(host, session);
                            }
                        });
                    }
                }
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setInstanceFollowRedirects(false);
                httpsURLConnection.setConnectTimeout(5000);
                httpsURLConnection.setReadTimeout(5000);
                httpsURLConnection.setDefaultUseCaches(false);
                strE = httpRequest.e();
                httpsURLConnection.setRequestMethod(strE);
                httpsURLConnection.setDoOutput(true);
                if (httpRequest instanceof HttpGetPrephoneRequest) {
                    httpsURLConnection.connect();
                    ((HttpGetPrephoneRequest) httpRequest).a(concurrentBundle);
                }
                if (!strE.endsWith("POST")) {
                    outputStream = httpsURLConnection.getOutputStream();
                    try {
                        outputStream.write(httpRequest.d().getBytes("utf-8"));
                        outputStream.flush();
                    } catch (Exception e4) {
                        e = e4;
                        str2 = "remote_ip";
                        inputStream = null;
                        i = -1;
                        e.printStackTrace();
                        LogUtils.a("ConnectionInterceptor", "请求失败: " + httpRequest.a());
                        concurrentBundle.a().a.add(e);
                        if (!(e instanceof EOFException)) {
                        }
                        if (e instanceof UnknownHostException) {
                            concurrentBundle.a(str2, a());
                        }
                        a(outputStream);
                        a(inputStream);
                        if (httpsURLConnection != null) {
                        }
                        LogUtils.b("ConnectionInterceptor", "responseCode: " + i2);
                        LogUtils.b("ConnectionInterceptor", "responseResult: " + (!TextUtils.isEmpty(sb) ? str : sb.toString()));
                        switch (i2) {
                            case Opcodes.GOTO_W /* 200 */:
                            case 301:
                            case 302:
                                break;
                        }
                        iResponse2.a(httpErrorResponseA);
                        return;
                    } catch (Throwable th5) {
                        th = th5;
                        responseCode = -1;
                        inputStream = null;
                        a(outputStream);
                        a(inputStream);
                        if (httpsURLConnection != null) {
                        }
                        LogUtils.b("ConnectionInterceptor", "responseCode: " + responseCode);
                        LogUtils.b("ConnectionInterceptor", "responseResult: " + (TextUtils.isEmpty(sb) ? str : sb.toString()));
                        switch (responseCode) {
                            case Opcodes.GOTO_W /* 200 */:
                            case 301:
                            case 302:
                                break;
                        }
                    }
                } else {
                    outputStream = null;
                }
                responseCode = httpsURLConnection.getResponseCode();
                inputStream = httpsURLConnection.getInputStream();
                bArr = new byte[2048];
                while (true) {
                    i3 = inputStream.read(bArr);
                    if (i3 > 0) {
                        break;
                    }
                    str2 = str3;
                    try {
                        sb.append(new String(bArr, 0, i3, "utf-8"));
                        iResponse2 = iResponse;
                        str3 = str2;
                    } catch (Exception e5) {
                        e = e5;
                        iResponse2 = iResponse;
                        i = responseCode;
                        e.printStackTrace();
                        LogUtils.a("ConnectionInterceptor", "请求失败: " + httpRequest.a());
                        concurrentBundle.a().a.add(e);
                        if (!(e instanceof EOFException)) {
                        }
                        if (e instanceof UnknownHostException) {
                        }
                        a(outputStream);
                        a(inputStream);
                        if (httpsURLConnection != null) {
                        }
                        LogUtils.b("ConnectionInterceptor", "responseCode: " + i2);
                        LogUtils.b("ConnectionInterceptor", "responseResult: " + (!TextUtils.isEmpty(sb) ? str : sb.toString()));
                        switch (i2) {
                            case Opcodes.GOTO_W /* 200 */:
                            case 301:
                            case 302:
                                break;
                        }
                        iResponse2.a(httpErrorResponseA);
                        return;
                    } catch (Throwable th6) {
                        th = th6;
                        iResponse2 = iResponse;
                        a(outputStream);
                        a(inputStream);
                        if (httpsURLConnection != null) {
                        }
                        LogUtils.b("ConnectionInterceptor", "responseCode: " + responseCode);
                        LogUtils.b("ConnectionInterceptor", "responseResult: " + (TextUtils.isEmpty(sb) ? str : sb.toString()));
                        switch (responseCode) {
                            case Opcodes.GOTO_W /* 200 */:
                            case 301:
                            case 302:
                                break;
                        }
                    }
                }
                str2 = str3;
                HttpSuccessResponse httpSuccessResponse = new HttpSuccessResponse(responseCode, httpsURLConnection.getHeaderFields(), sb.toString());
                a(outputStream);
                a(inputStream);
                if (httpsURLConnection != null) {
                    httpsURLConnection.disconnect();
                }
                LogUtils.b("ConnectionInterceptor", "responseCode: " + responseCode);
                LogUtils.b("ConnectionInterceptor", "responseResult: " + (!TextUtils.isEmpty(sb) ? str : sb.toString()));
                switch (responseCode) {
                    case Opcodes.GOTO_W /* 200 */:
                    case 301:
                    case 302:
                        a(httpRequest, concurrentBundle);
                        iResponse.a(httpSuccessResponse);
                        return;
                    default:
                        iResponse2 = iResponse;
                        a(httpRequest, concurrentBundle);
                        httpErrorResponseA = HttpErrorResponse.a(responseCode);
                        break;
                }
                iResponse2.a(httpErrorResponseA);
                return;
            }
            mapC = httpRequest.c();
            if (mapC == null) {
            }
            if (httpsURLConnection instanceof HttpsURLConnection) {
                if (z) {
                }
            }
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setInstanceFollowRedirects(false);
            httpsURLConnection.setConnectTimeout(5000);
            httpsURLConnection.setReadTimeout(5000);
            httpsURLConnection.setDefaultUseCaches(false);
            strE = httpRequest.e();
            httpsURLConnection.setRequestMethod(strE);
            httpsURLConnection.setDoOutput(true);
            if (httpRequest instanceof HttpGetPrephoneRequest) {
            }
            if (!strE.endsWith("POST")) {
            }
            responseCode = httpsURLConnection.getResponseCode();
            inputStream = httpsURLConnection.getInputStream();
            bArr = new byte[2048];
            while (true) {
                i3 = inputStream.read(bArr);
                if (i3 > 0) {
                }
                sb.append(new String(bArr, 0, i3, "utf-8"));
                iResponse2 = iResponse;
                str3 = str2;
            }
            str2 = str3;
            HttpSuccessResponse httpSuccessResponse2 = new HttpSuccessResponse(responseCode, httpsURLConnection.getHeaderFields(), sb.toString());
            a(outputStream);
            a(inputStream);
            if (httpsURLConnection != null) {
            }
            LogUtils.b("ConnectionInterceptor", "responseCode: " + responseCode);
            LogUtils.b("ConnectionInterceptor", "responseResult: " + (!TextUtils.isEmpty(sb) ? str : sb.toString()));
            switch (responseCode) {
                case Opcodes.GOTO_W /* 200 */:
                case 301:
                case 302:
                    break;
            }
            iResponse2.a(httpErrorResponseA);
            return;
        } catch (Throwable th7) {
            th = th7;
            str = str4;
        }
        z = false;
        if (httpRequest.h() == null) {
        }
    }

    private void a(HttpRequest httpRequest, ConcurrentBundle concurrentBundle) {
        if (!httpRequest.a().contains("uniConfig")) {
            UmcUtils.c(concurrentBundle, String.valueOf(SystemClock.elapsedRealtime() - httpRequest.i()));
        }
    }

    private void a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String a() {
        return Constants2.a[0] + "." + Constants2.a[2] + "." + Constants2.a[4] + "." + Constants2.a[6];
    }

    public synchronized SSLSocketFactory a(RequestParameter requestParameter, ConcurrentBundle concurrentBundle) {
        if (requestParameter instanceof GetPrePhoneScripParameter) {
            Tls12SocketFactory tls12SocketFactory = new Tls12SocketFactory(HttpsURLConnection.getDefaultSSLSocketFactory(), concurrentBundle);
            if (a == null) {
                a = tls12SocketFactory;
            }
            return tls12SocketFactory;
        }
        if (a == null) {
            a = new Tls12SocketFactory(HttpsURLConnection.getDefaultSSLSocketFactory(), concurrentBundle);
        }
        return a;
    }
}