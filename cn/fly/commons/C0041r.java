package cn.fly.commons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.security.NetworkSecurityPolicy;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.tools.utils.UIHandler;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/* renamed from: cn.fly.commons.r, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0041r {
    private static volatile String a = null;
    private static final byte[] b = new byte[0];

    public static Context a() {
        try {
            Object objB = b();
            if (objB != null) {
                return (Context) ReflectHelper.invokeInstanceMethod(objB, cn.fly.commons.a.l.a("014Pfk4gjCgeKkkh6ej-dejJejel-f"), new Object[0]);
            }
            return null;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    public static Object b() {
        final ReflectHelper.a<Void, Object> aVar = new ReflectHelper.a<Void, Object>() { // from class: cn.fly.commons.r.1
            @Override // cn.fly.tools.utils.ReflectHelper.a
            public Object a(Void r4) {
                return ReflectHelper.invokeStaticMethodNoThrow(ReflectHelper.importClassNoThrow(cn.fly.commons.a.l.a("026ef?edekelejedem5ekkLemge9dj@ejeeejCjMfdgd+iPek ge7ed"), null), cn.fly.commons.a.l.a("021dTehekek_gfj<ge)dj%ejeeej<jLfdgd=i@ek)ge(ed"), null, new Object[0]);
            }
        };
        Object objA = aVar.a(null);
        if (objA != null) {
            return objA;
        }
        final Object obj = new Object();
        final Object[] objArr = new Object[1];
        synchronized (obj) {
            UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.commons.r.2
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    Object obj2;
                    synchronized (obj) {
                        try {
                            objArr[0] = aVar.a(null);
                            obj2 = obj;
                        } catch (Throwable th) {
                            try {
                                FlyLog.getInstance().w(th);
                                obj2 = obj;
                            } catch (Throwable th2) {
                                obj.notify();
                                throw th2;
                            }
                        }
                        obj2.notify();
                    }
                    return false;
                }
            });
            try {
                obj.wait();
            } catch (Throwable th) {
                FlyLog.getInstance().w(th);
            }
        }
        return objArr[0];
    }

    public static void a(Closeable... closeableArr) {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
            }
        }
    }

    public static void a(final cn.fly.tools.utils.d<ArrayList<HashMap<String, Object>>> dVar) {
        DH.requester(FlySDK.getContext()).getMwlfo().getMbcdi().request(new DH.DHResponder() { // from class: cn.fly.commons.r.3
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                ArrayList arrayList = new ArrayList();
                try {
                    ArrayList<HashMap<String, Object>> mwlfo = dHResponse.getMwlfo();
                    if (mwlfo != null && !mwlfo.isEmpty()) {
                        ArrayList<String> arrayListG = c.g();
                        if (arrayListG != null && !arrayListG.isEmpty()) {
                            String mbcdi = dHResponse.getMbcdi();
                            Iterator<HashMap<String, Object>> it = mwlfo.iterator();
                            while (it.hasNext()) {
                                HashMap<String, Object> next = it.next();
                                Object obj = next.get(cn.fly.commons.a.l.a("005Cgkfmfmffgm"));
                                if (obj != null && String.valueOf(obj).equals(mbcdi)) {
                                    next.put(cn.fly.commons.a.l.a("010(eieieiRd3ehekfeel6ff"), true);
                                    mbcdi = null;
                                }
                                HashMap map = new HashMap();
                                Iterator<String> it2 = arrayListG.iterator();
                                while (it2.hasNext()) {
                                    String next2 = it2.next();
                                    Object obj2 = next.get(next2);
                                    if (obj2 != null) {
                                        map.put(next2, obj2);
                                    }
                                }
                                if (!map.isEmpty()) {
                                    arrayList.add(map);
                                }
                            }
                        }
                        dVar.a(null);
                        return;
                    }
                } catch (Throwable th) {
                    FlyLog.getInstance().w(th);
                }
                cn.fly.tools.utils.d dVar2 = dVar;
                if (arrayList.isEmpty()) {
                    arrayList = null;
                }
                dVar2.a(arrayList);
            }
        });
    }

    public static String a(String str) {
        return a(str, false);
    }

    public static String a(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String strA = "";
        if (str.startsWith(cn.fly.commons.a.l.a("007ijjklmm"))) {
            str = str.replace(cn.fly.commons.a.l.a("007ijjklmm"), "");
        }
        if (str.startsWith("https://")) {
            str = str.replace("https://", "");
        }
        if (FlySDK.checkV6()) {
            strA = cn.fly.commons.a.l.a("002Ieeii");
        } else {
            switch (FlySDK.getDmn()) {
                case JP:
                    strA = "jp";
                    break;
                case US:
                    strA = cn.fly.commons.a.l.a("002Oehgj");
                    break;
            }
        }
        if (!TextUtils.isEmpty(strA) && !str.startsWith(strA + ".")) {
            return b(cn.fly.commons.a.l.a("007ijjklmm") + strA + "-" + str, z);
        }
        return b(cn.fly.commons.a.l.a("007ijjklmm") + str, z);
    }

    public static String b(String str) {
        return b(str, false);
    }

    public static String b(String str, boolean z) {
        Uri uri;
        String scheme;
        try {
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        boolean zCheckFH = FlySDK.checkFH(z);
        if (zCheckFH || (DH.SyncMtd.getOSVersionIntForFly() >= 23 && !NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted())) {
            str = str.trim();
            if (str.startsWith(cn.fly.commons.a.l.a("007ijjklmm")) && (uri = Uri.parse(str.trim())) != null && (scheme = uri.getScheme()) != null && scheme.equals(cn.fly.commons.a.l.a("004ijjk"))) {
                String host = uri.getHost();
                String path = uri.getPath();
                String query = uri.getQuery();
                if (host != null) {
                    int port = uri.getPort();
                    host = host + ((port <= 0 || port == 80) ? "" : ":" + port);
                    if (!zCheckFH && DH.SyncMtd.getOSVersionIntForFly() >= 24 && ((Boolean) ReflectHelper.invokeInstanceMethod(NetworkSecurityPolicy.getInstance(), cn.fly.commons.a.l.a("027Iejgjfe5hge+ek2jgBfj1j>gdekJe6fgfgejLd5hmOg!ekegej5jjg,ed"), host)).booleanValue()) {
                        return str;
                    }
                }
                StringBuilder sbAppend = new StringBuilder().append("https://").append(host);
                if (path == null) {
                    path = "";
                }
                return sbAppend.append(path).append(query != null ? "?" + query : "").toString();
            }
        }
        return str;
    }

    public static void a(File file) throws Throwable {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            b(file);
            return;
        }
        String[] list = file.list();
        if (list == null || list.length == 0) {
            b(file);
            return;
        }
        for (String str : list) {
            File file2 = new File(file, str);
            if (file2.isDirectory()) {
                a(file2);
            } else {
                b(file2);
            }
        }
        b(file);
    }

    public static Object c(String str) throws Throwable {
        return ReflectHelper.invokeInstanceMethod(ReflectHelper.invokeStaticMethod(ReflectHelper.importClass(cn.fly.commons.a.l.a("017Hih2e]eeTeLem=hefFfkemhkehNfj=ejeg@g")), cn.fly.commons.a.l.a("0104fkTgj0hkehJfj.ejeg2g"), new Object[0]), cn.fly.commons.a.l.a("004g*fjAgd"), new Object[]{str}, new Class[]{String.class});
    }

    public static void a(View view) {
        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe("input_method");
        if (systemServiceSafe == null) {
            return;
        }
        ((InputMethodManager) systemServiceSafe).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void b(View view) {
        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe("input_method");
        if (systemServiceSafe == null) {
            return;
        }
        ((InputMethodManager) systemServiceSafe).toggleSoftInputFromWindow(view.getWindowToken(), 2, 0);
    }

    public static Intent a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        return DH.SyncMtd.getOSVersionIntForFly() < 33 ? (Intent) ReflectHelper.invokeInstanceMethod(FlySDK.getContext(), cn.fly.commons.a.l.a("016=ek.g5fkejgj<jg*ekhk:gdg4ejee!g0ek"), new Object[]{broadcastReceiver, intentFilter}, new Class[]{BroadcastReceiver.class, IntentFilter.class}, null) : (Intent) ReflectHelper.invokeInstanceMethod(FlySDK.getContext(), cn.fly.commons.a.l.a("016Nek(g[fkejgj6jgFekhk9gdg0ejeeRg6ek"), new Object[]{broadcastReceiver, intentFilter, 4}, new Class[]{BroadcastReceiver.class, IntentFilter.class, Integer.TYPE}, null);
    }

    public static void a(BroadcastReceiver broadcastReceiver) {
        ReflectHelper.invokeInstanceMethod(FlySDK.getContext(), cn.fly.commons.a.l.a("018Geh2fAekIg:fkejgj]jg8ekhkIgdg<ejeeCgBek"), new Object[]{broadcastReceiver}, new Class[]{BroadcastReceiver.class}, null);
    }

    public static byte[] c() throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        DataOutputStream dataOutputStream;
        Throwable th;
        SecureRandom secureRandom;
        try {
            secureRandom = new SecureRandom();
            byteArrayOutputStream = new ByteArrayOutputStream();
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
            dataOutputStream = null;
        }
        try {
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        } catch (Throwable th3) {
            th = th3;
            dataOutputStream = null;
            th = th;
            a(dataOutputStream, byteArrayOutputStream);
            throw th;
        }
        try {
            dataOutputStream.writeLong(secureRandom.nextLong());
            dataOutputStream.writeLong(secureRandom.nextLong());
            dataOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            a(dataOutputStream, byteArrayOutputStream);
            return byteArray;
        } catch (Throwable th4) {
            th = th4;
            a(dataOutputStream, byteArrayOutputStream);
            throw th;
        }
    }

    public static Object d(String str) {
        try {
            return FlySDK.getContext().getSystemService(str);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    public static boolean a(long j, long j2) {
        if (j > 0 && j2 > 0) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return simpleDateFormat.format(new Date(j)).equals(simpleDateFormat.format(new Date(j2)));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return false;
            }
        }
        return false;
    }

    private static void b(File file) {
        ReflectHelper.invokeInstanceMethod(file, cn.fly.commons.a.l.a("006*edPghgjg"), null, null, null);
    }

    public static String d() {
        if (TextUtils.isEmpty(a)) {
            synchronized (b) {
                try {
                    if (TextUtils.isEmpty(a)) {
                        a = new cn.fly.tools.utils.c(FlySDK.getContext()).a();
                    }
                } finally {
                }
            }
        }
        return a;
    }

    static Object a(File file, byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Throwable th) {
                th = th;
                byteArrayOutputStream = null;
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
            fileInputStream = null;
        }
        try {
            byte[] bArr2 = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr2);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr2, 0, i);
                } else {
                    Object objA = a(bArr, byteArrayOutputStream.toByteArray());
                    a(byteArrayOutputStream, fileInputStream);
                    return objA;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            try {
                FlyLog.getInstance().d(th);
                a(byteArrayOutputStream, fileInputStream);
                return null;
            } catch (Throwable th4) {
                a(byteArrayOutputStream, fileInputStream);
                throw th4;
            }
        }
    }

    static Object a(byte[] bArr, byte[] bArr2) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        Throwable th;
        ObjectInputStream objectInputStream;
        if (bArr2 == null || bArr2.length == 0) {
            return null;
        }
        try {
            byteArrayInputStream = new ByteArrayInputStream(Data.paddingDecode(bArr, bArr2));
            try {
                objectInputStream = new ObjectInputStream(byteArrayInputStream);
            } catch (Throwable th2) {
                th = th2;
                objectInputStream = null;
            }
        } catch (Throwable th3) {
            byteArrayInputStream = null;
            th = th3;
            objectInputStream = null;
        }
        try {
            Object object = objectInputStream.readObject();
            a(objectInputStream, byteArrayInputStream);
            return object;
        } catch (Throwable th4) {
            th = th4;
            a(objectInputStream, byteArrayInputStream);
            throw th;
        }
    }

    static byte[] a(byte[] bArr, Object obj) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream;
        if (obj != null) {
            ObjectOutputStream objectOutputStream2 = null;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
            }
            try {
                objectOutputStream.writeObject(obj);
                byte[] bArrAES128Encode = Data.AES128Encode(bArr, byteArrayOutputStream.toByteArray());
                a(objectOutputStream, byteArrayOutputStream);
                return bArrAES128Encode;
            } catch (Throwable th3) {
                th = th3;
                objectOutputStream2 = objectOutputStream;
                a(objectOutputStream2, byteArrayOutputStream);
                throw th;
            }
        }
        return new byte[0];
    }

    static void a(File file, byte[] bArr, Object obj) {
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            byte[] bArrA = a(bArr, obj);
            if (bArrA.length <= 0) {
                fileOutputStream = null;
            } else {
                fileOutputStream = new FileOutputStream(file);
                try {
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream);
                    try {
                        bufferedOutputStream2.write(bArrA);
                        bufferedOutputStream2.flush();
                        bufferedOutputStream = bufferedOutputStream2;
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream = bufferedOutputStream2;
                        try {
                            FlyLog.getInstance().d(th);
                            a(bufferedOutputStream, fileOutputStream);
                            return;
                        } catch (Throwable th2) {
                            a(bufferedOutputStream, fileOutputStream);
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
            a(bufferedOutputStream, fileOutputStream);
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
    }

    public static boolean e() {
        if (DH.SyncMtd.getOSVersionInt() >= 36) {
            return true;
        }
        try {
            String systemProperties = DH.SyncMtd.getSystemProperties("ro.build.version.release_or_codename");
            if (TextUtils.equals("Baklava", systemProperties)) {
                return true;
            }
            return Integer.parseInt(systemProperties) >= 16;
        } catch (Throwable th) {
            return true;
        }
    }

    public static String a(String str, int i) throws NumberFormatException {
        int i2;
        int i3 = 0;
        int i4 = 3;
        if (str.startsWith("00")) {
            i2 = Integer.parseInt(str.substring(2, 3));
        } else if (str.startsWith("0")) {
            i2 = Integer.parseInt(str.substring(1, 3));
        } else {
            i2 = Integer.parseInt(str.substring(0, 3));
        }
        char[] charArray = str.toCharArray();
        int[] iArr = new int[i2];
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        while (i4 < charArray.length) {
            if (charArray[i4] < 'a') {
                z = !z;
            } else {
                if (z) {
                    iArr[i3] = charArray[i4] - i;
                } else {
                    iArr[i3] = (charArray[i4] - i) * 10;
                    i4++;
                    iArr[i3] = iArr[i3] + (charArray[i4] - i);
                }
                sb.append(iArr[i3]);
                i3++;
            }
            i4++;
        }
        return d.a(iArr);
    }
}