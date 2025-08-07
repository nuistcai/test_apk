package cn.fly.tools.network;

import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.FlyProduct;
import cn.fly.commons.ac;
import cn.fly.commons.f;
import cn.fly.commons.o;
import cn.fly.commons.q;
import cn.fly.tools.FlyLog;
import cn.fly.tools.b.c;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.FlyRSA;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.UIHandler;
import cn.fly.tools.utils.i;
import com.tencent.bugly.BuglyStrategy;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes.dex */
public class NetCommunicator implements PublicMemberKeeper {
    public static final String KEY_DUID_PREVIOUS = "duidPrevious";
    public static final String KEY_IS_MODIFIED = "isModified";
    private BigInteger b;
    private BigInteger c;
    private FlyRSA d;
    private NetworkHelper e;
    private NetworkHelper.NetworkTimeOut f;
    private ThreadPoolExecutor g;
    public static final String KEY_DUID = o.a("004+dcdgdidc");
    private static final ThreadPoolExecutor a = new ThreadPoolExecutor(3, 20, 60, TimeUnit.SECONDS, new LinkedBlockingDeque());

    public NetCommunicator(int i, String str, String str2) {
        this(i, str, str2, null);
    }

    public NetCommunicator(int i, String str, String str2, NetworkHelper.NetworkTimeOut networkTimeOut) {
        this.d = new FlyRSA(i);
        this.b = new BigInteger(str, 16);
        this.c = new BigInteger(str2, 16);
        this.e = new NetworkHelper();
        if (networkTimeOut != null) {
            this.f = networkTimeOut;
        } else {
            this.f = new NetworkHelper.NetworkTimeOut();
            this.f.readTimout = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
            this.f.connectionTimeout = 5000;
        }
        this.g = a;
    }

    public void addTcpIntercept(String str) {
    }

    public void removeTcpIntercept(String str) {
    }

    public void setThreadPool(ThreadPoolExecutor threadPoolExecutor) {
        this.g = threadPoolExecutor;
    }

    public <T> void request(HashMap<String, Object> map, String str, boolean z, Callback<T> callback) {
        request(true, null, map, str, z, callback);
    }

    public <T> void request(HashMap<String, String> map, HashMap<String, Object> map2, String str, boolean z, Callback<T> callback) {
        request(true, map, map2, str, z, callback);
    }

    public <T> void request(final boolean z, final HashMap<String, String> map, final HashMap<String, Object> map2, final String str, final boolean z2, final Callback<T> callback) {
        this.g.execute(new i() { // from class: cn.fly.tools.network.NetCommunicator.1
            @Override // cn.fly.tools.utils.i
            public void a() {
                try {
                    final Object objRequestSynchronized = NetCommunicator.this.requestSynchronized(z, map, map2, str, z2);
                    if (callback != null) {
                        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.tools.network.NetCommunicator.1.1
                            @Override // android.os.Handler.Callback
                            public boolean handleMessage(Message message) {
                                callback.onResultOk(objRequestSynchronized);
                                return false;
                            }
                        });
                    }
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                    if (callback != null) {
                        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.tools.network.NetCommunicator.1.2
                            @Override // android.os.Handler.Callback
                            public boolean handleMessage(Message message) {
                                callback.onResultError(th);
                                return false;
                            }
                        });
                    }
                }
            }
        });
    }

    public <T> T requestSynchronized(HashMap<String, Object> map, String str, boolean z) throws Throwable {
        return (T) requestSynchronized((HashMap<String, String>) null, map, str, z);
    }

    public <T> T requestSynchronized(HashMap<String, String> map, HashMap<String, Object> map2, String str, boolean z) throws Throwable {
        return (T) requestSynchronized(true, map, map2, str, z);
    }

    public <T> T requestSynchronized(String str, String str2, boolean z) throws Throwable {
        return (T) requestSynchronized((HashMap<String, String>) null, str, str2, z);
    }

    public <T> T requestSynchronized(HashMap<String, String> map, String str, String str2, boolean z) throws Throwable {
        return (T) requestSynchronized(true, map, str, str2, z);
    }

    public <T> T requestSynchronized(boolean z, HashMap<String, String> map, HashMap<String, Object> map2, String str, boolean z2) throws Throwable {
        return (T) a(z, map, a(map2), str, z2, true, true);
    }

    public <T> T requestWithoutEncode(boolean z, HashMap<String, String> map, HashMap<String, Object> map2, String str, boolean z2) throws Throwable {
        return (T) a(z, map, a(map2), str, true, false, z2);
    }

    private String a(HashMap<String, Object> map) {
        if (map == null) {
            return "{}";
        }
        String strFromHashMap = HashonHelper.fromHashMap(map);
        if (strFromHashMap.length() == 0) {
            return "{}";
        }
        return strFromHashMap;
    }

    public <T> T requestSynchronized(boolean z, HashMap<String, String> map, String str, String str2, boolean z2) throws Throwable {
        return (T) a(z, map, str, str2, z2, true, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T a(boolean z, HashMap<String, String> map, String str, String str2, boolean z2, boolean z3, boolean z4) throws Throwable {
        byte[] bArrC = C0041r.c();
        byte[] bArrA = a(bArrC, str, z2);
        String[] strArr = new String[1];
        HttpResponseCallback httpResponseCallbackA = a(bArrC, strArr, z4);
        if (z3) {
            String strEncodeToString = Base64.encodeToString(bArrA, 2);
            HashMap<String, String> mapA = a(z, map, str, strEncodeToString.getBytes("utf-8").length);
            StringPart stringPart = new StringPart();
            stringPart.append(strEncodeToString);
            FlyLog.getInstance().d(">>>  request(" + str2 + "): " + str + "\nheader = " + mapA.toString(), new Object[0]);
            this.e.rawPost(str2, mapA, stringPart, -1, httpResponseCallbackA, this.f);
        } else {
            HashMap<String, String> mapA2 = a(z, map, str, -1);
            FlyLog.getInstance().d(">>>  request(" + str2 + "): " + str + "\nheader = " + mapA2.toString(), new Object[0]);
            this.e.httpPostWithBytes(str2, bArrA, mapA2, -1, httpResponseCallbackA, this.f);
        }
        if (strArr[0] != 0) {
            FlyLog.getInstance().d(">>> response(" + str2 + "): " + strArr[0], new Object[0]);
            if (z4) {
                return (T) a(strArr[0]);
            }
            return (T) strArr[0];
        }
        return null;
    }

    public String requestSynchronizedGet(String str, HashMap<String, Object> map, HashMap<String, String> map2) throws Throwable {
        return this.e.httpGetNew(str, map, map2, this.f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private byte[] a(byte[] bArr, String str, boolean z) throws Throwable {
        byte[] bytes;
        ByteArrayOutputStream byteArrayOutputStream;
        DataOutputStream dataOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2;
        GZIPOutputStream gZIPOutputStream;
        BufferedOutputStream bufferedOutputStream;
        DataOutputStream dataOutputStream2 = null;
        if (z) {
            try {
                byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream2);
                    try {
                        bufferedOutputStream = new BufferedOutputStream(gZIPOutputStream);
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    gZIPOutputStream = null;
                }
            } catch (Throwable th3) {
                th = th3;
                byteArrayOutputStream2 = null;
                gZIPOutputStream = null;
            }
            try {
                bufferedOutputStream.write(str.getBytes("utf-8"));
                bufferedOutputStream.flush();
                C0041r.a(bufferedOutputStream, gZIPOutputStream, byteArrayOutputStream2);
                bytes = byteArrayOutputStream2.toByteArray();
            } catch (Throwable th4) {
                th = th4;
                dataOutputStream2 = bufferedOutputStream;
                C0041r.a(dataOutputStream2, gZIPOutputStream, byteArrayOutputStream2);
                throw th;
            }
        } else {
            bytes = str.getBytes("utf-8");
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            } catch (Throwable th5) {
                th = th5;
            }
            try {
                byte[] bArrEncode = this.d.encode(bArr, this.b, this.c);
                dataOutputStream.writeInt(bArrEncode.length);
                dataOutputStream.write(bArrEncode);
                byte[] bArrAES128Encode = Data.AES128Encode(bArr, bytes);
                dataOutputStream.writeInt(bArrAES128Encode.length);
                dataOutputStream.write(bArrAES128Encode);
                dataOutputStream.flush();
                C0041r.a(dataOutputStream, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            } catch (Throwable th6) {
                th = th6;
                dataOutputStream2 = dataOutputStream;
                C0041r.a(dataOutputStream2, byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th7) {
            th = th7;
            byteArrayOutputStream = null;
        }
    }

    private HashMap<String, String> a(boolean z, HashMap<String, String> map, String str, int i) throws Throwable {
        HashMap<String, String> map2;
        if (!z) {
            map2 = null;
        } else if (i > 0) {
            map2 = a(str, i);
        } else {
            map2 = getCommonDefaultHeaders();
        }
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        if (map != null) {
            map2.putAll(map);
        }
        return map2;
    }

    private HashMap<String, String> a(String str, int i) throws Throwable {
        HashMap<String, String> commonDefaultHeaders = getCommonDefaultHeaders();
        commonDefaultHeaders.put(o.a("004Rfidiej^e"), Data.MD5(str + FlySDK.getAppSecret()));
        commonDefaultHeaders.put(o.a("014*eddk8eifeiJhkfeCfeFej ih"), String.valueOf(i));
        return commonDefaultHeaders;
    }

    private HttpResponseCallback a(final byte[] bArr, final String[] strArr, final boolean z) {
        return new HttpResponseCallback() { // from class: cn.fly.tools.network.NetCommunicator.2
            @Override // cn.fly.tools.network.HttpResponseCallback
            public void onResponse(HttpConnection httpConnection) throws Throwable {
                InputStream inputStream;
                ByteArrayOutputStream byteArrayOutputStream;
                int responseCode = httpConnection.getResponseCode();
                ByteArrayOutputStream byteArrayOutputStream2 = null;
                try {
                    inputStream = responseCode == 200 ? httpConnection.getInputStream() : httpConnection.getErrorStream();
                    try {
                        byteArrayOutputStream = new ByteArrayOutputStream();
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = null;
                }
                try {
                    byte[] bArr2 = new byte[1024];
                    for (int i = inputStream.read(bArr2); i != -1; i = inputStream.read(bArr2)) {
                        byteArrayOutputStream.write(bArr2, 0, i);
                    }
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    if (responseCode != 200) {
                        HashMap mapFromJson = HashonHelper.fromJson(new String(byteArray, "utf-8"));
                        mapFromJson.put(o.a("010hiij6elUidi1dgfi"), Integer.valueOf(responseCode));
                        throw new NetworkError(HashonHelper.fromHashMap(mapFromJson));
                    }
                    if (z) {
                        long jA = NetCommunicator.this.a(httpConnection);
                        if (jA != -1 && jA == byteArray.length) {
                            strArr[0] = NetCommunicator.this.a(bArr, byteArray);
                        } else {
                            HashMap map = new HashMap();
                            map.put(o.a("010hiij[el_idiDdgfi"), Integer.valueOf(responseCode));
                            map.put(o.a("0063fi+idi$dgfi"), -2);
                            map.put(o.a("005fQdjdjdkdj"), "Illegal content length");
                            throw new NetworkError(HashonHelper.fromHashMap(map));
                        }
                    } else {
                        strArr[0] = new String(byteArray, "utf-8");
                    }
                    C0041r.a(byteArrayOutputStream, inputStream);
                } catch (Throwable th3) {
                    th = th3;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    C0041r.a(byteArrayOutputStream2, inputStream);
                    throw th;
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long a(HttpConnection httpConnection) throws Throwable {
        List<String> listA = a(httpConnection, o.a("014[eddkBeifei9hkfe=fe2ej.ih"));
        if (listA != null && listA.size() > 0) {
            return Long.parseLong(listA.get(0));
        }
        return -1L;
    }

    private List<String> a(HttpConnection httpConnection, String str) throws Throwable {
        Map<String, List<String>> headerFields = httpConnection.getHeaderFields();
        if (headerFields != null && !headerFields.isEmpty()) {
            for (String str2 : headerFields.keySet()) {
                if (str2 != null && str2.equals(str)) {
                    return headerFields.get(str2);
                }
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(byte[] bArr, byte[] bArr2) throws Throwable {
        return new String(Data.AES128Decode(bArr, Base64.decode(bArr2, 2)), "utf-8");
    }

    private Object a(String str) throws Throwable {
        if (str == null) {
            HashMap map = new HashMap();
            map.put(o.a("006HfiHidi-dgfi"), -1);
            map.put(o.a("005fVdjdjdkdj"), "RS is empty");
            throw new NetworkError(HashonHelper.fromHashMap(map));
        }
        HashMap mapFromJson = HashonHelper.fromJson(str.trim());
        if (mapFromJson.isEmpty()) {
            HashMap map2 = new HashMap();
            map2.put(o.a("0061fi*idi:dgfi"), -1);
            map2.put(o.a("005f8djdjdkdj"), "RS is empty");
            throw new NetworkError(HashonHelper.fromHashMap(map2));
        }
        Object obj = mapFromJson.get(o.a("003Edj3fZfi"));
        if (obj == null) {
            return mapFromJson.get(o.a("004^dcUdid"));
        }
        return obj;
    }

    public static HashMap<String, String> getCommonDefaultHeaders() throws Throwable {
        HashMap<String, String> map = new HashMap<>();
        map.put(o.a("003>eh$f(ec"), q.a());
        map.put(o.a("0138ekfiVf5djhkeedc+feiCdiLi_ec"), ac.e());
        map.put(o.a("004-dfdkdidc"), c.a(FlySDK.getContext()).d().ao());
        return map;
    }

    public static String dynamicModifyUrl(String str) {
        return C0041r.a(str);
    }

    public static String dynaMU(String str) {
        return C0041r.a(str, true);
    }

    public static String checkHttpRequestUrl(String str) {
        return C0041r.b(str);
    }

    public static String checkHRU(String str) {
        return C0041r.b(str, true);
    }

    public static synchronized String getDUID(FlyProduct flyProduct) {
        return f.a(flyProduct);
    }

    public static synchronized HashMap<String, Object> getDUIDWithModifyInfo(FlyProduct flyProduct) {
        return f.b(flyProduct);
    }

    public static class Callback<T> implements PublicMemberKeeper {
        public void onResultOk(T t) {
        }

        public void onResultError(Throwable th) {
        }
    }

    public static class NetworkError extends Exception implements PublicMemberKeeper {
        private static final long serialVersionUID = -8447657431687664787L;

        public NetworkError(String str) {
            super(str);
        }
    }
}