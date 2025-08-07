package cn.com.chinatelecom.account.api.c;

import android.content.Context;
import android.text.TextUtils;
import cn.com.chinatelecom.account.api.d.j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class b extends f {
    private static final String b = b.class.getSimpleName();

    public b(Context context) {
        super(context);
    }

    public h a(String str, String str2, int i, g gVar) throws Throwable {
        Throwable th;
        BufferedReader bufferedReader;
        Throwable th2;
        IOException iOException;
        InputStream inputStream;
        UnknownHostException unknownHostException;
        SocketTimeoutException socketTimeoutException;
        h hVar = new h();
        BufferedReader bufferedReader2 = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        BufferedReader bufferedReader3 = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        bufferedReader2 = null;
        InputStream inputStream2 = null;
        try {
            try {
                try {
                    HttpURLConnection httpURLConnectionB = b(str, str2, i, gVar);
                    int responseCode = httpURLConnectionB.getResponseCode();
                    int i2 = 0;
                    if (responseCode == 200) {
                        inputStream = httpURLConnectionB.getInputStream();
                        try {
                            StringBuilder sb = new StringBuilder();
                            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            while (true) {
                                try {
                                    String line = bufferedReader.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    sb.append(line).append("\n");
                                } catch (SocketTimeoutException e) {
                                    socketTimeoutException = e;
                                    bufferedReader2 = bufferedReader;
                                    hVar.b = j.b(80005, cn.com.chinatelecom.account.api.a.d.a(j.f) + "-" + gVar.c + "-" + socketTimeoutException.getMessage());
                                    cn.com.chinatelecom.account.api.a.a(b, "SocketTimeoutException-" + gVar.c + "-" + socketTimeoutException.getMessage(), socketTimeoutException);
                                    cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "SocketTimeoutException ：" + socketTimeoutException.getMessage());
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    return hVar;
                                } catch (UnknownHostException e2) {
                                    unknownHostException = e2;
                                    bufferedReader2 = bufferedReader;
                                    hVar.b = j.b(80006, cn.com.chinatelecom.account.api.a.d.a(j.g) + "-" + gVar.c + "-" + unknownHostException.getMessage());
                                    cn.com.chinatelecom.account.api.a.a(b, "UnknownHostException-" + gVar.c + "-" + unknownHostException.getMessage(), unknownHostException);
                                    cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "UnknownHostException ：" + unknownHostException.getMessage());
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    return hVar;
                                } catch (IOException e3) {
                                    iOException = e3;
                                    bufferedReader2 = bufferedReader;
                                    hVar.b = j.b(80007, cn.com.chinatelecom.account.api.a.d.a(j.h) + "-" + gVar.c + "-" + iOException.getMessage());
                                    cn.com.chinatelecom.account.api.a.a(b, "IOException-" + gVar.c + "-" + iOException.getMessage(), iOException);
                                    cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "IOException ：" + iOException.getMessage());
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    return hVar;
                                } catch (Throwable th3) {
                                    th2 = th3;
                                    inputStream2 = inputStream;
                                    try {
                                        hVar.b = j.b(80001, cn.com.chinatelecom.account.api.a.d.a(j.b) + "-" + gVar.c + "-" + th2.getMessage());
                                        cn.com.chinatelecom.account.api.a.a(b, "Throwable-" + gVar.c + "-" + th2.getMessage(), th2);
                                        cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "Throwable ：" + th2.getMessage());
                                        if (bufferedReader != null) {
                                            bufferedReader.close();
                                        }
                                        if (inputStream2 != null) {
                                            inputStream2.close();
                                        }
                                        return hVar;
                                    } catch (Throwable th4) {
                                        th = th4;
                                        if (bufferedReader != null) {
                                            try {
                                                bufferedReader.close();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                                throw th;
                                            }
                                        }
                                        if (inputStream2 == null) {
                                            throw th;
                                        }
                                        inputStream2.close();
                                        throw th;
                                    }
                                }
                            }
                            hVar.a = 0;
                            String string = sb.toString();
                            if (!TextUtils.isEmpty(string)) {
                                hVar.b = new JSONObject(string);
                                cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, null);
                            }
                            cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, null);
                            d dVarA = cn.com.chinatelecom.account.api.d.a.a(this.a, httpURLConnectionB, true);
                            if (dVarA != null) {
                                hVar.c = dVarA.a;
                                hVar.d = dVarA.e;
                                cn.com.chinatelecom.account.api.d.f.a(gVar.d).f(dVarA.c).b(cn.com.chinatelecom.account.api.d.g.f(this.a));
                            }
                            bufferedReader3 = bufferedReader;
                        } catch (SocketTimeoutException e5) {
                            socketTimeoutException = e5;
                        } catch (UnknownHostException e6) {
                            unknownHostException = e6;
                        } catch (IOException e7) {
                            iOException = e7;
                        } catch (Throwable th5) {
                            th2 = th5;
                            bufferedReader = null;
                        }
                    } else {
                        if (responseCode != 302) {
                            hVar.b = j.b(80002, cn.com.chinatelecom.account.api.a.d.a(j.c) + "-" + gVar.c + "-code : " + responseCode);
                            String str3 = " Http response code :" + responseCode;
                            cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, str3);
                            cn.com.chinatelecom.account.api.a.a(b, str3);
                        } else {
                            if (gVar.b < 10) {
                                gVar.b++;
                                gVar.f = false;
                                String headerField = httpURLConnectionB.getHeaderField("Location");
                                d dVarA2 = cn.com.chinatelecom.account.api.d.a.a(httpURLConnectionB);
                                cn.com.chinatelecom.account.api.d.f.a(gVar.d).f(dVarA2.c).b(cn.com.chinatelecom.account.api.d.g.f(this.a));
                                if (!TextUtils.isEmpty(dVarA2.d) && !dVarA2.d.equals("0")) {
                                    i2 = 1;
                                }
                                cn.com.chinatelecom.account.api.a.a(b, " method : " + i2);
                                return a(headerField, null, i2, gVar, false);
                            }
                            hVar.b = j.b(80001, cn.com.chinatelecom.account.api.a.d.a(j.b) + "-Redirect more than 10 times");
                            cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "Redirect more than 10 times");
                        }
                        inputStream = null;
                    }
                    if (bufferedReader3 != null) {
                        bufferedReader3.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (SocketTimeoutException e8) {
                    socketTimeoutException = e8;
                    inputStream = null;
                } catch (UnknownHostException e9) {
                    unknownHostException = e9;
                    inputStream = null;
                } catch (IOException e10) {
                    iOException = e10;
                    inputStream = null;
                } catch (Throwable th6) {
                    th2 = th6;
                    bufferedReader = null;
                }
            } catch (IOException e11) {
                e11.printStackTrace();
            }
            return hVar;
        } catch (Throwable th7) {
            th = th7;
            bufferedReader = bufferedReader2;
            inputStream2 = inputStream;
        }
    }

    @Override // cn.com.chinatelecom.account.api.c.e
    public h a(String str, String str2, int i, g gVar, boolean z) {
        return a(str) ? b(str, str2, i, gVar, z) : a(str, str2, i, gVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:138:0x0295 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0311 A[Catch: IOException -> 0x045d, TRY_ENTER, TryCatch #28 {IOException -> 0x045d, blocks: (B:180:0x0459, B:184:0x0462, B:109:0x0258, B:111:0x025d, B:142:0x0311, B:144:0x0316, B:155:0x03a2, B:157:0x03a7, B:198:0x04fd, B:200:0x0502), top: B:227:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0316 A[Catch: IOException -> 0x045d, TRY_LEAVE, TryCatch #28 {IOException -> 0x045d, blocks: (B:180:0x0459, B:184:0x0462, B:109:0x0258, B:111:0x025d, B:142:0x0311, B:144:0x0316, B:155:0x03a2, B:157:0x03a7, B:198:0x04fd, B:200:0x0502), top: B:227:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0326 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03a2 A[Catch: IOException -> 0x045d, TRY_ENTER, TryCatch #28 {IOException -> 0x045d, blocks: (B:180:0x0459, B:184:0x0462, B:109:0x0258, B:111:0x025d, B:142:0x0311, B:144:0x0316, B:155:0x03a2, B:157:0x03a7, B:198:0x04fd, B:200:0x0502), top: B:227:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03a7 A[Catch: IOException -> 0x045d, TRY_LEAVE, TryCatch #28 {IOException -> 0x045d, blocks: (B:180:0x0459, B:184:0x0462, B:109:0x0258, B:111:0x025d, B:142:0x0311, B:144:0x0316, B:155:0x03a2, B:157:0x03a7, B:198:0x04fd, B:200:0x0502), top: B:227:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0442 A[Catch: all -> 0x046c, TryCatch #7 {all -> 0x046c, blocks: (B:163:0x03b8, B:166:0x03be, B:167:0x03c0, B:168:0x0436, B:170:0x0442, B:172:0x0447, B:171:0x0445), top: B:215:0x03b8 }] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0445 A[Catch: all -> 0x046c, TryCatch #7 {all -> 0x046c, blocks: (B:163:0x03b8, B:166:0x03be, B:167:0x03c0, B:168:0x0436, B:170:0x0442, B:172:0x0447, B:171:0x0445), top: B:215:0x03b8 }] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0454 A[Catch: all -> 0x0450, TRY_LEAVE, TryCatch #22 {all -> 0x0450, blocks: (B:174:0x044c, B:178:0x0454), top: B:225:0x044c }] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0459 A[Catch: IOException -> 0x045d, TRY_ENTER, TryCatch #28 {IOException -> 0x045d, blocks: (B:180:0x0459, B:184:0x0462, B:109:0x0258, B:111:0x025d, B:142:0x0311, B:144:0x0316, B:155:0x03a2, B:157:0x03a7, B:198:0x04fd, B:200:0x0502), top: B:227:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0462 A[Catch: IOException -> 0x045d, TRY_LEAVE, TryCatch #28 {IOException -> 0x045d, blocks: (B:180:0x0459, B:184:0x0462, B:109:0x0258, B:111:0x025d, B:142:0x0311, B:144:0x0316, B:155:0x03a2, B:157:0x03a7, B:198:0x04fd, B:200:0x0502), top: B:227:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x047f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x04fd A[Catch: IOException -> 0x045d, TRY_ENTER, TryCatch #28 {IOException -> 0x045d, blocks: (B:180:0x0459, B:184:0x0462, B:109:0x0258, B:111:0x025d, B:142:0x0311, B:144:0x0316, B:155:0x03a2, B:157:0x03a7, B:198:0x04fd, B:200:0x0502), top: B:227:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0502 A[Catch: IOException -> 0x045d, TRY_LEAVE, TryCatch #28 {IOException -> 0x045d, blocks: (B:180:0x0459, B:184:0x0462, B:109:0x0258, B:111:0x025d, B:142:0x0311, B:144:0x0316, B:155:0x03a2, B:157:0x03a7, B:198:0x04fd, B:200:0x0502), top: B:227:0x0011 }] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0513 A[Catch: IOException -> 0x050e, TRY_LEAVE, TryCatch #20 {IOException -> 0x050e, blocks: (B:205:0x050a, B:209:0x0513), top: B:223:0x050a }] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x03b8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x050a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x044c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:237:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public h b(String str, String str2, int i, g gVar, boolean z) throws Throwable {
        Throwable th;
        InputStream inputStream;
        boolean z2;
        IOException iOException;
        boolean z3;
        UnknownHostException unknownHostException;
        String str3;
        BufferedReader bufferedReader;
        boolean z4;
        SocketTimeoutException socketTimeoutException;
        boolean z5;
        Throwable th2;
        BufferedReader bufferedReader2;
        BufferedReader bufferedReader3;
        InputStream inputStream2;
        BufferedReader bufferedReader4;
        InputStream inputStream3;
        BufferedReader bufferedReader5;
        String str4;
        String strReplace;
        BufferedReader bufferedReader6;
        Context context;
        long jCurrentTimeMillis;
        h hVar = new h();
        boolean z6 = false;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                boolean zC = cn.com.chinatelecom.account.api.d.g.c(this.a);
                boolean zA = a(gVar.f, gVar.g);
                if (zA) {
                    try {
                        try {
                            str4 = str;
                        } catch (SocketTimeoutException e2) {
                            socketTimeoutException = e2;
                            inputStream = null;
                            bufferedReader4 = null;
                            z5 = zA;
                            z6 = false;
                            if (!gVar.e && z5) {
                                hVar.d = z6;
                            }
                            hVar.b = j.b(80005, cn.com.chinatelecom.account.api.a.d.a(j.f) + "-" + gVar.c + "-" + socketTimeoutException.getMessage());
                            cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "SocketTimeoutException : " + socketTimeoutException.getMessage());
                            cn.com.chinatelecom.account.api.a.a(b, "STE_" + gVar.c + "_" + socketTimeoutException.getMessage(), socketTimeoutException);
                            if (bufferedReader4 != null) {
                                bufferedReader4.close();
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            return hVar;
                        } catch (IOException e3) {
                            iOException = e3;
                            inputStream = null;
                            bufferedReader3 = null;
                            z3 = zA;
                            z6 = false;
                            if (!gVar.e && z3) {
                                hVar.d = z6;
                            }
                            hVar.b = j.b(80007, cn.com.chinatelecom.account.api.a.d.a(j.h) + "-" + gVar.c + "-" + iOException.getMessage());
                            cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "IOException : " + iOException.getMessage());
                            cn.com.chinatelecom.account.api.a.a(b, "IOException-" + gVar.c + "-" + iOException.getMessage(), iOException);
                            if (bufferedReader3 != null) {
                                bufferedReader3.close();
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            return hVar;
                        } catch (Throwable th3) {
                            th = th3;
                            inputStream = null;
                            bufferedReader2 = null;
                            z2 = zA;
                            z6 = false;
                            if (!gVar.e && z2) {
                                hVar.d = z6;
                            }
                            hVar.b = j.b(80001, cn.com.chinatelecom.account.api.a.d.a(j.b) + "-" + gVar.c + "-" + th.getMessage());
                            cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "Throwable : " + th.getMessage());
                            cn.com.chinatelecom.account.api.a.a(b, "Throwable-" + gVar.c + "-" + th.getMessage(), th);
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            return hVar;
                        }
                        try {
                            strReplace = str4.replace(gVar.h, gVar.g);
                        } catch (UnknownHostException e4) {
                            e = e4;
                            unknownHostException = e;
                            str3 = str4;
                            bufferedReader = null;
                            inputStream2 = null;
                            z4 = zA;
                            z6 = false;
                            if (!z) {
                            }
                            if (str3.contains(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.g))) {
                            }
                            hVar.d = true;
                            if (bufferedReader != null) {
                            }
                            if (inputStream2 != null) {
                            }
                            if (bufferedReader != null) {
                            }
                            if (inputStream2 != null) {
                            }
                            return hVar;
                        }
                    } catch (UnknownHostException e5) {
                        e = e5;
                        str4 = str;
                    }
                } else {
                    strReplace = str;
                }
                try {
                    if (gVar.b > 0 && !zC) {
                        try {
                            if (!a()) {
                                a(this.a, strReplace);
                            }
                        } catch (UnknownHostException e6) {
                            unknownHostException = e6;
                            str3 = strReplace;
                            bufferedReader = null;
                            inputStream2 = null;
                            z4 = zA;
                            z6 = false;
                            if (!z) {
                                try {
                                    if (!gVar.e && z4) {
                                        hVar.d = z6;
                                    }
                                    hVar.b = j.b(80006, cn.com.chinatelecom.account.api.a.d.a(j.g) + "-" + gVar.c + "-" + unknownHostException.getMessage());
                                    cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "UnknownHostException : " + unknownHostException.getMessage());
                                    cn.com.chinatelecom.account.api.a.a(b, "UnknownHostException-" + gVar.c + "-" + unknownHostException.getMessage(), unknownHostException);
                                } catch (Throwable th4) {
                                    th2 = th4;
                                    inputStream3 = inputStream2;
                                    bufferedReader5 = bufferedReader;
                                    if (bufferedReader5 != null) {
                                        try {
                                            bufferedReader5.close();
                                        } catch (IOException e7) {
                                            e7.printStackTrace();
                                            throw th2;
                                        }
                                    }
                                    if (inputStream3 != null) {
                                        throw th2;
                                    }
                                    inputStream3.close();
                                    throw th2;
                                }
                            }
                            if (str3.contains(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.g))) {
                                hVar.e = "2";
                            } else {
                                hVar.e = "1";
                            }
                            hVar.d = true;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th5) {
                                    if (bufferedReader != null) {
                                    }
                                    if (inputStream2 != null) {
                                    }
                                    return hVar;
                                }
                            }
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                            return hVar;
                        }
                    }
                    HttpsURLConnection httpsURLConnectionC = c(strReplace, str2, i, gVar);
                    int responseCode = httpsURLConnectionC.getResponseCode();
                    if (responseCode == 200) {
                        inputStream = httpsURLConnectionC.getInputStream();
                        try {
                            StringBuilder sb = new StringBuilder();
                            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            while (true) {
                                try {
                                    String line = bufferedReader.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    sb.append(line).append("\n");
                                } catch (SocketTimeoutException e8) {
                                    socketTimeoutException = e8;
                                    bufferedReader4 = bufferedReader;
                                    z5 = zA;
                                    z6 = false;
                                    if (!gVar.e) {
                                    }
                                    hVar.b = j.b(80005, cn.com.chinatelecom.account.api.a.d.a(j.f) + "-" + gVar.c + "-" + socketTimeoutException.getMessage());
                                    cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "SocketTimeoutException : " + socketTimeoutException.getMessage());
                                    cn.com.chinatelecom.account.api.a.a(b, "STE_" + gVar.c + "_" + socketTimeoutException.getMessage(), socketTimeoutException);
                                    if (bufferedReader4 != null) {
                                    }
                                    if (inputStream != null) {
                                    }
                                    return hVar;
                                } catch (UnknownHostException e9) {
                                    unknownHostException = e9;
                                    inputStream2 = inputStream;
                                    str3 = strReplace;
                                    z4 = zA;
                                    z6 = false;
                                    if (!z) {
                                    }
                                    if (str3.contains(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.g))) {
                                    }
                                    hVar.d = true;
                                    if (bufferedReader != null) {
                                    }
                                    if (inputStream2 != null) {
                                    }
                                    if (bufferedReader != null) {
                                    }
                                    if (inputStream2 != null) {
                                    }
                                    return hVar;
                                } catch (IOException e10) {
                                    iOException = e10;
                                    bufferedReader3 = bufferedReader;
                                    z3 = zA;
                                    z6 = false;
                                    if (!gVar.e) {
                                    }
                                    hVar.b = j.b(80007, cn.com.chinatelecom.account.api.a.d.a(j.h) + "-" + gVar.c + "-" + iOException.getMessage());
                                    cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "IOException : " + iOException.getMessage());
                                    cn.com.chinatelecom.account.api.a.a(b, "IOException-" + gVar.c + "-" + iOException.getMessage(), iOException);
                                    if (bufferedReader3 != null) {
                                    }
                                    if (inputStream != null) {
                                    }
                                    return hVar;
                                } catch (Throwable th6) {
                                    th = th6;
                                    bufferedReader2 = bufferedReader;
                                    z2 = zA;
                                    z6 = false;
                                    if (!gVar.e) {
                                    }
                                    hVar.b = j.b(80001, cn.com.chinatelecom.account.api.a.d.a(j.b) + "-" + gVar.c + "-" + th.getMessage());
                                    cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "Throwable : " + th.getMessage());
                                    cn.com.chinatelecom.account.api.a.a(b, "Throwable-" + gVar.c + "-" + th.getMessage(), th);
                                    if (bufferedReader2 != null) {
                                    }
                                    if (inputStream != null) {
                                    }
                                    return hVar;
                                }
                            }
                            hVar.a = 0;
                            String string = sb.toString();
                            if (!TextUtils.isEmpty(string)) {
                                hVar.b = new JSONObject(string);
                                cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, null);
                            }
                            d dVarA = cn.com.chinatelecom.account.api.d.a.a(this.a, httpsURLConnectionC, true);
                            if (dVarA != null) {
                                hVar.c = dVarA.a;
                                cn.com.chinatelecom.account.api.d.f.a(gVar.d).f(dVarA.c);
                            }
                            if (dVarA.e && z) {
                                if (strReplace.contains(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.g))) {
                                    hVar.e = "1";
                                } else {
                                    hVar.e = "2";
                                }
                                hVar.d = true;
                            }
                            if (!z) {
                                if (strReplace.contains(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.f))) {
                                    context = this.a;
                                    jCurrentTimeMillis = 0;
                                } else {
                                    cn.com.chinatelecom.account.api.d.g.a(this.a, "2");
                                    context = this.a;
                                    jCurrentTimeMillis = System.currentTimeMillis();
                                }
                                cn.com.chinatelecom.account.api.d.g.a(context, jCurrentTimeMillis);
                            }
                            bufferedReader6 = bufferedReader;
                        } catch (SocketTimeoutException e11) {
                            socketTimeoutException = e11;
                            bufferedReader4 = null;
                        } catch (UnknownHostException e12) {
                            unknownHostException = e12;
                            inputStream2 = inputStream;
                            str3 = strReplace;
                            bufferedReader = null;
                        } catch (IOException e13) {
                            iOException = e13;
                            bufferedReader3 = null;
                        } catch (Throwable th7) {
                            th = th7;
                            bufferedReader2 = null;
                        }
                    } else {
                        try {
                            if (responseCode != 302) {
                                String str5 = strReplace;
                                if (!z) {
                                    cn.com.chinatelecom.account.api.d.g.a(this.a, 0L);
                                    hVar.b = j.b(80002, cn.com.chinatelecom.account.api.a.d.a(j.c) + "-" + gVar.c + "-code : " + responseCode);
                                    String str6 = "response code ：" + responseCode;
                                    cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, str6);
                                    cn.com.chinatelecom.account.api.a.a(b, str6);
                                    return hVar;
                                }
                                if (str5.contains(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.g))) {
                                    hVar.e = "1";
                                } else {
                                    hVar.e = "2";
                                }
                                hVar.d = true;
                            } else {
                                if (gVar.b < 10) {
                                    gVar.b++;
                                    gVar.f = false;
                                    String headerField = httpsURLConnectionC.getHeaderField("Location");
                                    d dVarA2 = cn.com.chinatelecom.account.api.d.a.a(httpsURLConnectionC);
                                    cn.com.chinatelecom.account.api.d.f.a(gVar.d).f(dVarA2.c).b(cn.com.chinatelecom.account.api.d.g.f(this.a));
                                    int i2 = (TextUtils.isEmpty(dVarA2.d) || dVarA2.d.equals("0")) ? 0 : 1;
                                    cn.com.chinatelecom.account.api.a.a(b, "method : " + i2);
                                    return a(headerField, null, i2, gVar, false);
                                }
                                hVar.b = j.b(80001, cn.com.chinatelecom.account.api.a.d.a(j.b) + "-Redirect more than 10 times");
                                cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "Redirect more than 10 times");
                            }
                            inputStream = null;
                            bufferedReader6 = null;
                        } catch (SocketTimeoutException e14) {
                            e = e14;
                            socketTimeoutException = e;
                            z5 = zA;
                            inputStream = null;
                            bufferedReader4 = null;
                            if (!gVar.e) {
                                hVar.d = z6;
                            }
                            hVar.b = j.b(80005, cn.com.chinatelecom.account.api.a.d.a(j.f) + "-" + gVar.c + "-" + socketTimeoutException.getMessage());
                            cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "SocketTimeoutException : " + socketTimeoutException.getMessage());
                            cn.com.chinatelecom.account.api.a.a(b, "STE_" + gVar.c + "_" + socketTimeoutException.getMessage(), socketTimeoutException);
                            if (bufferedReader4 != null) {
                            }
                            if (inputStream != null) {
                            }
                            return hVar;
                        } catch (UnknownHostException e15) {
                            e = e15;
                            unknownHostException = e;
                            z4 = zA;
                            bufferedReader = null;
                            inputStream2 = null;
                            if (!z) {
                            }
                            if (str3.contains(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.g))) {
                            }
                            hVar.d = true;
                            if (bufferedReader != null) {
                            }
                            if (inputStream2 != null) {
                            }
                            if (bufferedReader != null) {
                            }
                            if (inputStream2 != null) {
                            }
                            return hVar;
                        } catch (IOException e16) {
                            e = e16;
                            iOException = e;
                            z3 = zA;
                            inputStream = null;
                            bufferedReader3 = null;
                            if (!gVar.e) {
                                hVar.d = z6;
                            }
                            hVar.b = j.b(80007, cn.com.chinatelecom.account.api.a.d.a(j.h) + "-" + gVar.c + "-" + iOException.getMessage());
                            cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "IOException : " + iOException.getMessage());
                            cn.com.chinatelecom.account.api.a.a(b, "IOException-" + gVar.c + "-" + iOException.getMessage(), iOException);
                            if (bufferedReader3 != null) {
                            }
                            if (inputStream != null) {
                            }
                            return hVar;
                        } catch (Throwable th8) {
                            th = th8;
                            th = th;
                            z2 = zA;
                            inputStream = null;
                            bufferedReader2 = null;
                            if (!gVar.e) {
                                hVar.d = z6;
                            }
                            hVar.b = j.b(80001, cn.com.chinatelecom.account.api.a.d.a(j.b) + "-" + gVar.c + "-" + th.getMessage());
                            cn.com.chinatelecom.account.api.d.f.a(gVar.d, hVar.b, "Throwable : " + th.getMessage());
                            cn.com.chinatelecom.account.api.a.a(b, "Throwable-" + gVar.c + "-" + th.getMessage(), th);
                            if (bufferedReader2 != null) {
                            }
                            if (inputStream != null) {
                            }
                            return hVar;
                        }
                    }
                    if (bufferedReader6 != null) {
                        bufferedReader6.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (SocketTimeoutException e17) {
                    e = e17;
                    z6 = false;
                } catch (UnknownHostException e18) {
                    e = e18;
                    str3 = strReplace;
                    z6 = false;
                } catch (IOException e19) {
                    e = e19;
                    z6 = false;
                } catch (Throwable th9) {
                    th = th9;
                    z6 = false;
                }
            } catch (SocketTimeoutException e20) {
                z6 = false;
                socketTimeoutException = e20;
                inputStream = null;
                z5 = false;
            } catch (UnknownHostException e21) {
                z6 = false;
                unknownHostException = e21;
                str3 = str;
                bufferedReader = null;
                z4 = false;
            } catch (IOException e22) {
                z6 = false;
                iOException = e22;
                inputStream = null;
                z3 = false;
            } catch (Throwable th10) {
                z6 = false;
                th = th10;
                inputStream = null;
                z2 = false;
            }
            return hVar;
        } catch (Throwable th11) {
            th2 = th11;
            if (bufferedReader5 != null) {
            }
            if (inputStream3 != null) {
            }
        }
    }
}