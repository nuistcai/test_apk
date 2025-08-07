package cn.fly.commons;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.FlyRSA;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ResHelper;
import com.tencent.bugly.BuglyStrategy;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ae {
    private static final String a = cn.fly.commons.a.l.a("002-gdhd");
    private static final String b = cn.fly.commons.a.l.a("005^emel-kjf");
    private static final String c = cn.fly.commons.a.l.a("005FemelQkPedCf");
    private static final String d = cn.fly.commons.a.l.a("016-igifkgimijiijlieikgiHeQgg!d^edfgej");
    private static ae e;
    private String f;
    private Context g = FlySDK.getContext();
    private TreeMap<String, Object> h;

    private ae() {
    }

    public static ae a() {
        if (e == null) {
            synchronized (ae.class) {
                if (e == null) {
                    e = new ae();
                }
            }
        }
        return e;
    }

    public String b() {
        if (TextUtils.isEmpty(this.f)) {
            synchronized (ae.class) {
                if (TextUtils.isEmpty(this.f)) {
                    return d();
                }
            }
        }
        return this.f;
    }

    public String c() {
        String str = this.f;
        if (TextUtils.isEmpty(str)) {
            return e();
        }
        return str;
    }

    private String d() {
        this.h = new TreeMap<>();
        String strA = null;
        try {
            String strE = e();
            boolean zA = a(f());
            if (TextUtils.isEmpty(strE)) {
                strA = a(this.h);
            } else {
                FlyLog.getInstance().d("[%s] %s", a, "tk status: " + zA);
                if (!zA) {
                    strA = strE;
                } else {
                    strA = a(this.h);
                }
            }
            e.f = strA;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        return strA;
    }

    private boolean a(HashMap<String, Object> map) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final String[] strArr = new String[1];
        DH.requester(FlySDK.getContext()).getDeviceKey().request(new DH.DHResponder() { // from class: cn.fly.commons.ae.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                strArr[0] = dHResponse.getDeviceKey();
                countDownLatch.countDown();
            }
        });
        try {
            this.h.put(cn.fly.commons.a.l.a("007%fg)edjMelekfd"), DH.SyncMtd.getManufacturerForFly());
            this.h.put(cn.fly.commons.a.l.a("005+egeledBgh"), DH.SyncMtd.getModelForFly());
            this.h.put(cn.fly.commons.a.l.a("006RgjfdgjeePg<ek"), Integer.valueOf(DH.SyncMtd.getOSVersionIntForFly()));
            countDownLatch.await(100L, TimeUnit.MILLISECONDS);
            String str = strArr[0];
            if (!TextUtils.isEmpty(str)) {
                this.h.put(cn.fly.commons.a.l.a("008]edQg5eeejKdg8ffed"), str);
            }
            this.h.put(cn.fly.commons.a.l.a("004Nedehejed"), f.a((FlyProduct) null));
            String strMD5 = Data.MD5(new JSONObject(this.h).toString());
            TreeMap<String, Object> treeMap = new TreeMap<>();
            treeMap.put(cn.fly.commons.a.l.a("010-fk;gfg;ekGehVidedij"), strMD5);
            b(treeMap);
            if (map == null || map.isEmpty() || !strMD5.equals((String) map.get(cn.fly.commons.a.l.a("0103fkLgfg%ek0ehZidedij")))) {
                return true;
            }
            FlyLog.getInstance().d("[%s] %s", a, "No changes");
            return false;
        } catch (Throwable th) {
            FlyLog.getInstance().e(th);
            return false;
        }
    }

    private String a(TreeMap<String, Object> treeMap) {
        HashMap map;
        String str = null;
        if (!c.c() || treeMap == null || treeMap.isEmpty()) {
            return null;
        }
        try {
            HashMap map2 = new HashMap();
            map2.put(cn.fly.commons.a.l.a("007>fgSedjKelekfd"), treeMap.get(cn.fly.commons.a.l.a("007>fgSedjKelekfd")));
            map2.put(cn.fly.commons.a.l.a("005ZegeledDgh"), treeMap.get(cn.fly.commons.a.l.a("005ZegeledDgh")));
            map2.put(cn.fly.commons.a.l.a("006Ggjfdgjee!gFek"), treeMap.get(cn.fly.commons.a.l.a("006Ggjfdgjee!gFek")));
            map2.put(cn.fly.commons.a.l.a("008.ed[g?eeejUdgWffed"), treeMap.get(cn.fly.commons.a.l.a("008.ed[g?eeejUdgWffed")));
            map2.put(cn.fly.commons.a.l.a("004'edehejed"), treeMap.get(cn.fly.commons.a.l.a("004'edehejed")));
            HashMap<String, Object> map3 = new HashMap<>();
            map3.put(cn.fly.commons.a.l.a("006ekk!fi)g9fd"), q.a());
            map3.put("m", a(HashonHelper.fromHashMap(map2)));
            HashMap<String, String> map4 = new HashMap<>();
            map4.put(cn.fly.commons.a.l.a("013Eflgj(gTekilffed]gfj0ejTjLfd"), ac.e());
            map4.put(cn.fly.commons.a.l.a("004Oegelejed"), cn.fly.tools.b.c.a(FlySDK.getContext()).d().ao());
            NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
            networkTimeOut.readTimout = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
            networkTimeOut.connectionTimeout = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
            HashMap mapFromJson = HashonHelper.fromJson(new NetworkHelper().httpPostNew(j.a().a("gclg") + cn.fly.commons.a.l.a("007mGel3kgfSejed"), map3, map4, networkTimeOut));
            if (!"200".equals(String.valueOf(mapFromJson.get(cn.fly.commons.a.l.a("004d0eledYg")))) || (map = (HashMap) mapFromJson.get(cn.fly.commons.a.l.a("004Sed$eje"))) == null) {
                return null;
            }
            String str2 = (String) map.get(cn.fly.commons.a.l.a("005j'elfiCgf"));
            try {
                e.f = str2;
                b(str2);
                return str2;
            } catch (Throwable th) {
                th = th;
                str = str2;
                FlyLog.getInstance().e(th);
                return str;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private String a(String str) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        GZIPOutputStream gZIPOutputStream;
        BufferedOutputStream bufferedOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2;
        DataOutputStream dataOutputStream;
        byte[] bArrC = C0041r.c();
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
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
            byteArrayOutputStream = null;
            gZIPOutputStream = null;
        }
        try {
            bufferedOutputStream.write(str.getBytes("utf-8"));
            bufferedOutputStream.flush();
            C0041r.a(bufferedOutputStream, gZIPOutputStream, byteArrayOutputStream);
            byte[] bArrAES128Encode = Data.AES128Encode(bArrC, byteArrayOutputStream.toByteArray());
            byte[] bArrEncode = new FlyRSA(1024).encode(bArrC, new BigInteger("ceeef5035212dfe7c6a0acdc0ef35ce5b118aab916477037d7381f85c6b6176fcf57b1d1c3296af0bb1c483fe5e1eb0ce9eb2953b44e494ca60777a1b033cc07", 16), new BigInteger("191737288d17e660c4b61440d5d14228a0bf9854499f9d68d8274db55d6d954489371ecf314f26bec236e58fac7fffa9b27bcf923e1229c4080d49f7758739e5bd6014383ed2a75ce1be9b0ab22f283c5c5e11216c5658ba444212b6270d629f2d615b8dfdec8545fb7d4f935b0cc10b6948ab4fc1cb1dd496a8f94b51e888dd", 16));
            try {
                byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    dataOutputStream = new DataOutputStream(byteArrayOutputStream2);
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Throwable th5) {
                th = th5;
                byteArrayOutputStream2 = null;
            }
            try {
                dataOutputStream.writeInt(bArrEncode.length);
                dataOutputStream.write(bArrEncode);
                dataOutputStream.writeInt(bArrAES128Encode.length);
                dataOutputStream.write(bArrAES128Encode);
                dataOutputStream.flush();
                C0041r.a(dataOutputStream, byteArrayOutputStream2);
                return Base64.encodeToString(byteArrayOutputStream2.toByteArray(), 2);
            } catch (Throwable th6) {
                th = th6;
                bufferedOutputStream2 = dataOutputStream;
                C0041r.a(bufferedOutputStream2, byteArrayOutputStream2);
                throw th;
            }
        } catch (Throwable th7) {
            th = th7;
            bufferedOutputStream2 = bufferedOutputStream;
            C0041r.a(bufferedOutputStream2, gZIPOutputStream, byteArrayOutputStream);
            throw th;
        }
    }

    private void b(String str) {
        FileOutputStream fileOutputStream;
        DataOutputStream dataOutputStream;
        DataOutputStream dataOutputStream2 = null;
        try {
            File dataCacheFile = ResHelper.getDataCacheFile(this.g, b);
            if (dataCacheFile == null) {
                fileOutputStream = null;
            } else {
                fileOutputStream = new FileOutputStream(dataCacheFile);
                try {
                    dataOutputStream = new DataOutputStream(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    dataOutputStream.writeUTF(str);
                    dataOutputStream.flush();
                    dataOutputStream2 = dataOutputStream;
                } catch (Throwable th2) {
                    th = th2;
                    dataOutputStream2 = dataOutputStream;
                    try {
                        FlyLog.getInstance().d(th);
                        C0041r.a(dataOutputStream2, fileOutputStream);
                        return;
                    } catch (Throwable th3) {
                        C0041r.a(dataOutputStream2, fileOutputStream);
                        throw th3;
                    }
                }
            }
            C0041r.a(dataOutputStream2, fileOutputStream);
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
    }

    private String e() throws IOException {
        DataInputStream dataInputStream;
        FileInputStream fileInputStream;
        String utf;
        DataInputStream dataInputStream2 = null;
        try {
            File dataCacheFile = ResHelper.getDataCacheFile(this.g, b);
            if (dataCacheFile.exists() && dataCacheFile.length() > 0) {
                fileInputStream = new FileInputStream(dataCacheFile);
                try {
                    dataInputStream = new DataInputStream(fileInputStream);
                } catch (Throwable th) {
                    th = th;
                    dataInputStream = null;
                }
                try {
                    utf = dataInputStream.readUTF();
                    dataInputStream2 = dataInputStream;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        FlyLog.getInstance().d(th);
                        C0041r.a(dataInputStream, fileInputStream);
                        return null;
                    } catch (Throwable th3) {
                        C0041r.a(dataInputStream, fileInputStream);
                        throw th3;
                    }
                }
            } else {
                utf = null;
                fileInputStream = null;
            }
            C0041r.a(dataInputStream2, fileInputStream);
            return utf;
        } catch (Throwable th4) {
            th = th4;
            dataInputStream = null;
            fileInputStream = null;
        }
    }

    private void b(TreeMap<String, Object> treeMap) throws IOException {
        ResHelper.writeToFileNoCompress(ResHelper.getDataCacheFile(this.g, c), a(d, treeMap));
    }

    private HashMap<String, Object> f() {
        return a(d, ResHelper.readFromFileNoCompress(ResHelper.getDataCacheFile(this.g, c)));
    }

    private byte[] a(String str, TreeMap<String, Object> treeMap) {
        try {
            return Data.EncodeNoPadding(str, new JSONObject(treeMap).toString());
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    private HashMap<String, Object> a(String str, byte[] bArr) {
        try {
            return HashonHelper.fromJson(Data.AES128Decode(str, bArr));
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return new HashMap<>();
        }
    }
}