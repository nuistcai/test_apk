package cn.fly.tools.b;

import android.app.ActivityManager;
import android.app.Application;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.Proxy;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Environment;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Process;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.text.TextUtils;
import cn.fly.commons.C0041r;
import cn.fly.commons.CSCenter;
import cn.fly.commons.FlyMeta;
import cn.fly.commons.o;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.NtFetcher;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.tools.utils.ResHelper;
import cn.fly.tools.utils.a;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class b {
    private static b b;
    private Context a;
    private volatile int c = -1;

    public static synchronized b a(Context context) {
        if (b == null && context != null) {
            b = new b(context);
        }
        return b;
    }

    private b(Context context) {
        this.a = context.getApplicationContext();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized boolean a() {
        boolean z;
        String strD = C0041r.d();
        if (strD == null || strD.length() != 5) {
            z = false;
        } else {
            if (strD.charAt(3) != '1') {
                if (strD.charAt(4) == '1') {
                }
            }
            z = true;
        }
        return z;
    }

    public String b() {
        String str = Build.MODEL;
        if (!TextUtils.isEmpty(str)) {
            return str.trim();
        }
        return str;
    }

    public String c() {
        return Build.MANUFACTURER;
    }

    public String a(String str) {
        return e.a(this.a).a(str);
    }

    public String d() {
        try {
            String str = c.a(this.a).d().n() + "|" + DH.SyncMtd.getOSVersionIntForFly() + "|" + DH.SyncMtd.getManufacturerForFly() + "|" + l() + "|" + k();
            String strB = b(false);
            if (strB == null) {
                strB = "";
            } else if (strB.length() > 16) {
                strB = strB.substring(0, 16);
            }
            return Data.Base64AES(str, strB);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return "";
        }
    }

    public String e() {
        return c.a(this.a).d().m() + "|" + DH.SyncMtd.getOSVersionIntForFly() + "|" + DH.SyncMtd.getManufacturerForFly() + "|" + l() + "|" + k();
    }

    public int f() {
        return Build.VERSION.SDK_INT;
    }

    public String g() {
        return Build.VERSION.RELEASE;
    }

    public String h() {
        return Locale.getDefault().getLanguage();
    }

    public String i() {
        return this.a.getResources().getConfiguration().locale.getLanguage();
    }

    public String j() {
        return Locale.getDefault().getCountry();
    }

    public String k() {
        int[] screenSize = ResHelper.getScreenSize(this.a);
        if (this.a.getResources().getConfiguration().orientation == 1) {
            return screenSize[0] + "x" + screenSize[1];
        }
        return screenSize[1] + "x" + screenSize[0];
    }

    public String l() {
        String simOperator;
        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(o.a("005jh)dk'ef"));
        if (systemServiceSafe == null) {
            return "-1";
        }
        if (CSCenter.getInstance().isPhoneStateDataEnable()) {
            simOperator = (String) ReflectHelper.invokeInstanceMethodNoThrow(systemServiceSafe, o.a("0144ej8fiMeldidfgh3jf?dj-di$dkdj"), null, new Object[0]);
        } else {
            simOperator = CSCenter.getInstance().getSimOperator();
        }
        if (TextUtils.isEmpty(simOperator)) {
            return "-1";
        }
        return simOperator;
    }

    public String m() {
        String simOperatorName;
        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(o.a("005jhYdk ef"));
        if (systemServiceSafe == null) {
            return null;
        }
        if (CSCenter.getInstance().isPhoneStateDataEnable()) {
            simOperatorName = (String) ReflectHelper.invokeInstanceMethodNoThrow(systemServiceSafe, o.a("018Sej^fi-eldidfgh,jf=dj$diWdkdjeg2d^dfFf"), null, new Object[0]);
        } else {
            simOperatorName = CSCenter.getInstance().getSimOperatorName();
        }
        if (TextUtils.isEmpty(simOperatorName)) {
            return null;
        }
        return simOperatorName;
    }

    public String b(String str) {
        Signature[] signatureArrB;
        try {
            Object objB = str.equals(DH.SyncMtd.getPackageName()) ? c.a(this.a).d().b(false, 0, str, 64) : c.a(this.a).d().a(false, 0, str, 64);
            if (objB != null && (signatureArrB = cn.fly.tools.c.b(objB, str)) != null && signatureArrB.length > 0) {
                return Data.MD5(signatureArrB[0].toByteArray());
            }
            return null;
        } catch (Exception e) {
            FlyLog.getInstance().w(e);
            return null;
        }
    }

    public String a(boolean z) {
        return NtFetcher.getInstance(this.a).getNtType(z);
    }

    public String b(boolean z) {
        String strAv = av();
        if (!z && (TextUtils.isEmpty(strAv) || strAv.length() < 40)) {
            strAv = au();
        }
        if (!TextUtils.isEmpty(strAv) && strAv.length() >= 40 && f(strAv)) {
            return strAv.trim();
        }
        String strAx = ax();
        if (!TextUtils.isEmpty(strAx) && strAx.length() >= 40 && f(strAx)) {
            return strAx.trim();
        }
        String strA = a(40);
        if (!TextUtils.isEmpty(strA)) {
            String strTrim = strA.trim();
            g(strTrim);
            return strTrim;
        }
        return strA;
    }

    private boolean f(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.matches("^[a-z0-9]+$");
    }

    private String au() {
        try {
            return Data.byteToHex(Data.SHA1(((Object) null) + ":" + ((Object) null) + ":" + c.a(this.a).d().n()));
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    public String a(int i) {
        long jCurrentTimeMillis = System.currentTimeMillis() ^ SystemClock.elapsedRealtime();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(jCurrentTimeMillis);
        SecureRandom secureRandom = new SecureRandom();
        for (int i2 = 0; i2 < i; i2++) {
            if (o.a("004chdKdj").equalsIgnoreCase(o.a(secureRandom.nextInt(2) % 2 == 0 ? "004chd,dj" : "003eHdgdf"))) {
                stringBuffer.insert(i2 + 1, (char) (secureRandom.nextInt(26) + 97));
            } else {
                stringBuffer.insert(stringBuffer.length(), secureRandom.nextInt(10));
            }
        }
        return stringBuffer.toString().substring(0, 40);
    }

    private String av() {
        HashMap map;
        HashMap<String, Object> mapAw = aw();
        if (mapAw == null || (map = (HashMap) mapAw.get(o.a("010Cdc2fCdddi1cf9ee(eJefdk"))) == null) {
            return null;
        }
        try {
            return Data.byteToHex(Data.SHA1(((Object) null) + ":" + ((Object) null) + ":" + ((String) map.get(o.a("005Zdfdkdc$fg")))));
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private HashMap<String, Object> aw() {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        HashMap<String, Object> mapA;
        File dataCacheFile = ResHelper.getDataCacheFile(this.a, o.a("014cQdkdfdfKl?dcfffiLlTdldcdgdidc"), true);
        if (dataCacheFile.exists() && dataCacheFile.length() > 0) {
            try {
                fileInputStream = new FileInputStream(dataCacheFile);
                try {
                    objectInputStream = new ObjectInputStream(fileInputStream);
                } catch (Throwable th) {
                    objectInputStream = null;
                }
                try {
                    mapA = (HashMap) objectInputStream.readObject();
                    C0041r.a(objectInputStream, fileInputStream);
                } catch (Throwable th2) {
                    C0041r.a(objectInputStream, fileInputStream);
                    mapA = null;
                    if (mapA != null) {
                        mapA = a(dataCacheFile);
                        if (!mapA.isEmpty()) {
                        }
                    }
                    return null;
                }
            } catch (Throwable th3) {
                fileInputStream = null;
                objectInputStream = null;
            }
            if (mapA != null || mapA.isEmpty()) {
                mapA = a(dataCacheFile);
            }
            if (!mapA.isEmpty()) {
                return (HashMap) mapA.get(o.a("010XdcSf_dddiWcf@eeKeMefdk"));
            }
        }
        return null;
    }

    private HashMap<String, Object> a(File file) {
        return a(c.a(this.a).d().n(), ResHelper.readFromFileNoCompress(file));
    }

    private HashMap<String, Object> a(String str, byte[] bArr) {
        try {
            return HashonHelper.fromJson(Data.AES128Decode(str, bArr));
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return new HashMap<>();
        }
    }

    private String ax() {
        ObjectInputStream objectInputStream;
        FileInputStream fileInputStream;
        File cacheRootFile;
        File file = new File(s(), o.a("008'el2hd9dj:fJelflic"));
        if (file.exists()) {
            File file2 = new File(file, o.a("003=dldceh"));
            if (file2.exists() && (cacheRootFile = ResHelper.getCacheRootFile(this.a, o.a("0039dldceh"))) != null && file2.renameTo(cacheRootFile)) {
                file2.delete();
            }
        }
        File cacheRootFile2 = ResHelper.getCacheRootFile(this.a, o.a("0035dldceh"));
        String strValueOf = null;
        if (cacheRootFile2 != null && !cacheRootFile2.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(cacheRootFile2);
            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
            } catch (Throwable th) {
                th = th;
                objectInputStream = null;
            }
            try {
                Object object = objectInputStream.readObject();
                if (object != null && (object instanceof char[])) {
                    strValueOf = String.valueOf((char[]) object);
                }
                C0041r.a(objectInputStream, fileInputStream);
            } catch (Throwable th2) {
                th = th2;
                try {
                    FlyLog.getInstance().d(th);
                    C0041r.a(objectInputStream, fileInputStream);
                    return strValueOf;
                } catch (Throwable th3) {
                    C0041r.a(objectInputStream, fileInputStream);
                    throw th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            objectInputStream = null;
            fileInputStream = null;
        }
        return strValueOf;
    }

    private void g(String str) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        File cacheRootFile = ResHelper.getCacheRootFile(this.a, o.a("003+dldceh"));
        if (cacheRootFile != null && cacheRootFile.exists()) {
            cacheRootFile.delete();
        }
        ObjectOutputStream objectOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(cacheRootFile);
            try {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
            } catch (Throwable th) {
                th = th;
            }
            try {
                objectOutputStream.writeObject(str.toCharArray());
                objectOutputStream.flush();
                C0041r.a(objectOutputStream, fileOutputStream);
            } catch (Throwable th2) {
                th = th2;
                objectOutputStream2 = objectOutputStream;
                try {
                    FlyLog.getInstance().d(th);
                    C0041r.a(objectOutputStream2, fileOutputStream);
                } catch (Throwable th3) {
                    C0041r.a(objectOutputStream2, fileOutputStream);
                    throw th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
    }

    public String n() {
        return this.a.getPackageName();
    }

    public String o() {
        try {
            ApplicationInfo applicationInfoAq = c.a(this.a).d().aq();
            String packageName = DH.SyncMtd.getPackageName();
            String strB = cn.fly.tools.c.b(applicationInfoAq, packageName);
            if (strB != null) {
                if (DH.SyncMtd.getOSVersionIntForFly() >= 25 && !strB.endsWith(".*")) {
                    ReflectHelper.importClassNoThrow(strB, null);
                } else {
                    return strB;
                }
            }
            int iC = cn.fly.tools.c.c(applicationInfoAq, packageName);
            if (iC > 0) {
                return this.a.getString(iC);
            }
            return String.valueOf(cn.fly.tools.c.d(applicationInfoAq, packageName));
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return "";
        }
    }

    public String c(String str) {
        ApplicationInfo applicationInfoA;
        CharSequence charSequenceG;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            if (TextUtils.isEmpty(str) || (applicationInfoA = c.a(this.a).d().a(str, 1)) == null || (charSequenceG = cn.fly.tools.c.g(applicationInfoA, str)) == null) {
                return null;
            }
            return charSequenceG.toString();
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    public int p() {
        try {
            int iIntValue = ((Integer) FlyMeta.get(null, o.a("011=dd:f!djfididkHeXeddkdc>f"), Integer.class, 0)).intValue();
            if (iIntValue <= 0) {
                Object objB = c.a(this.a).d().b(false, 0, n(), 0);
                if (DH.SyncMtd.getOSVersionIntForFly() >= 28) {
                    return (int) cn.fly.tools.c.g(objB, DH.SyncMtd.getPackageName());
                }
                return cn.fly.tools.c.f(objB, DH.SyncMtd.getPackageName());
            }
            return iIntValue;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return 0;
        }
    }

    public String q() {
        try {
            String str = (String) FlyMeta.get(null, o.a("011Sdd<f+djfididk,e@eg[d=dfCf"), String.class, null);
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            return cn.fly.tools.c.c(c.a(this.a).d().b(false, 0, n(), 0), DH.SyncMtd.getPackageName());
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return "1.0";
        }
    }

    public ArrayList<HashMap<String, String>> a(ArrayList<HashMap<String, String>> arrayList, int i) {
        try {
            FlyLog.getInstance().d("DH PD: fabt " + i, new Object[0]);
            if (arrayList == null || arrayList.isEmpty()) {
                return null;
            }
            ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
            Iterator<HashMap<String, String>> it = arrayList.iterator();
            while (it.hasNext()) {
                HashMap<String, String> next = it.next();
                boolean zEquals = TextUtils.equals("1", next.get(o.a("005Kdififiecfi")));
                if (i != 1 || !zEquals) {
                    if (i != 2 || zEquals) {
                        HashMap<String, String> map = new HashMap<>(next);
                        map.remove(o.a("005Ndififiecfi"));
                        arrayList2.add(map);
                    }
                }
            }
            return arrayList2;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    private HashMap<String, String> ay() {
        try {
            return (HashMap) ResHelper.readObjectFromFile(ResHelper.getDataCacheFile(this.a, o.a("004^dl-deCfi")).getAbsolutePath());
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            ResHelper.getDataCacheFile(this.a, o.a("004%dl)de.fi")).delete();
            return null;
        }
    }

    private void b(HashMap<String, String> map) {
        if (map != null) {
            ResHelper.saveObjectToFile(ResHelper.getDataCacheFile(this.a, o.a("004.dlBde*fi")).getAbsolutePath(), map);
        }
    }

    public ArrayList<HashMap<String, String>> r() {
        if (CSCenter.getInstance().isAppListDataEnable()) {
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            try {
                if (DH.SyncMtd.getOSVersionIntForFly() <= 25) {
                    return a(az());
                }
                ArrayList arrayList2 = (ArrayList) cn.fly.commons.c.a(o.a("004dg(dk(g"), (Object) null);
                if (arrayList2 == null || arrayList2.size() == 0) {
                    arrayList2 = new ArrayList(Arrays.asList("1", "2"));
                }
                for (int i = 0; i < arrayList2.size(); i++) {
                    arrayList = b(Integer.parseInt(String.valueOf(arrayList2.get(i))));
                    if (arrayList != null && !arrayList.isEmpty() && arrayList.size() > 1) {
                        return arrayList;
                    }
                }
                return arrayList;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return new ArrayList<>();
            }
        }
        List<PackageInfo> packageInfos = CSCenter.getInstance().getPackageInfos();
        if (packageInfos != null && !packageInfos.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            for (PackageInfo packageInfo : packageInfos) {
                map.put(packageInfo.packageName, packageInfo);
            }
            return a(map);
        }
        return new ArrayList<>();
    }

    private ArrayList<HashMap<String, String>> b(int i) {
        Set<String> setA;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        try {
            switch (i) {
                case 1:
                    setA = j.a(this.a, 1);
                    break;
                case 2:
                    setA = aA();
                    break;
                case 3:
                    setA = az();
                    break;
                case 4:
                    setA = j.a(this.a, 4);
                    break;
                case 5:
                    setA = aB();
                    break;
                default:
                    setA = null;
                    break;
            }
            if (setA != null && !setA.isEmpty()) {
                arrayList = a(setA);
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        FlyLog.getInstance().d("DH PD: ap " + arrayList.size() + " tpe " + i, new Object[0]);
        return arrayList;
    }

    private ArrayList<HashMap<String, String>> a(Set<String> set) {
        if (cn.fly.commons.e.b() && set != null && !set.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            for (String str : set) {
                map.put(str, c.a(this.a).d().b(true, 0, str, 0));
            }
            if (!map.isEmpty()) {
                return a(map);
            }
        }
        return new ArrayList<>();
    }

    private Set<String> az() {
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        Throwable th;
        Object objC;
        HashSet hashSet = new HashSet();
        if (cn.fly.commons.e.b() && !o.a("005Adf@fKdigddg").equalsIgnoreCase(DH.SyncMtd.getManufacturerForFly()) && j.a()) {
            try {
                try {
                    objC = C0041r.c(o.a("016jBdfifBg[difi]iOifKjdc)ehOdUejGf$fi"));
                    try {
                        inputStream = (InputStream) ReflectHelper.invokeInstanceMethod(objC, o.a("014[ej[fi(ee-ej(dg)i;elWi*dj2fdMdf"), new Object[0]);
                        try {
                            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                            try {
                                bufferedReader = new BufferedReader(inputStreamReader);
                                try {
                                    for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                                        String strTrim = line.trim();
                                        if (strTrim.length() > 8 && strTrim.substring(0, 8).equalsIgnoreCase(o.a("008jdc<eh?dXejFfk"))) {
                                            String strTrim2 = strTrim.substring(8).trim();
                                            if (!TextUtils.isEmpty(strTrim2)) {
                                                hashSet.add(strTrim2);
                                            }
                                        }
                                    }
                                    C0041r.a(bufferedReader, inputStreamReader, inputStream);
                                } catch (Throwable th2) {
                                    th = th2;
                                    try {
                                        FlyLog.getInstance().w(th);
                                        C0041r.a(bufferedReader, inputStreamReader, inputStream);
                                        if (objC != null) {
                                            ReflectHelper.invokeInstanceMethod(objC, o.a("007DdcNfXfiUiAdjdkec"), new Object[0]);
                                        }
                                        return hashSet;
                                    } catch (Throwable th3) {
                                        C0041r.a(bufferedReader, inputStreamReader, inputStream);
                                        if (objC != null) {
                                            try {
                                                ReflectHelper.invokeInstanceMethod(objC, o.a("007DdcNfXfiUiAdjdkec"), new Object[0]);
                                            } catch (Throwable th4) {
                                            }
                                        }
                                        throw th3;
                                    }
                                }
                            } catch (Throwable th5) {
                                bufferedReader = null;
                                th = th5;
                            }
                        } catch (Throwable th6) {
                            bufferedReader = null;
                            th = th6;
                            inputStreamReader = null;
                        }
                    } catch (Throwable th7) {
                        inputStreamReader = null;
                        bufferedReader = null;
                        th = th7;
                        inputStream = null;
                    }
                } catch (Throwable th8) {
                    inputStream = null;
                    inputStreamReader = null;
                    bufferedReader = null;
                    th = th8;
                    objC = null;
                }
                if (objC != null) {
                    ReflectHelper.invokeInstanceMethod(objC, o.a("007DdcNfXfiUiAdjdkec"), new Object[0]);
                }
            } catch (Throwable th9) {
            }
        }
        return hashSet;
    }

    private Set<String> aA() {
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        Throwable th;
        Object objC;
        HashSet hashSet = new HashSet();
        if (cn.fly.commons.e.b()) {
            BufferedReader bufferedReader2 = null;
            try {
                try {
                    objC = C0041r.c(o.a("032c;dfdcif4jdcTehBd^ejNf)ifdedgQf'djechkSdci6didddi9i%diTfZfiifhk_dAif") + o.a("026de0dcdjdkdidcdldi%eifeiAdlLdci(didkGe:dlhcfdeeeg") + " " + o.a("0087hkhkdgfi3f'djiffh"));
                    try {
                        inputStream = (InputStream) ReflectHelper.invokeInstanceMethod(objC, o.a("014>ej'fiLeeIej:dgUi]elTiYdjRfdUdf"), new Object[0]);
                        if (inputStream == null) {
                            inputStreamReader = null;
                        } else {
                            try {
                                inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                                try {
                                    bufferedReader = new BufferedReader(inputStreamReader);
                                } catch (Throwable th2) {
                                    bufferedReader = null;
                                    th = th2;
                                }
                            } catch (Throwable th3) {
                                bufferedReader = null;
                                th = th3;
                                inputStreamReader = null;
                            }
                            try {
                                String strA = o.a("012jdc'eh,d*ej$fLeg^d6dfNfMii");
                                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                                    String strTrim = line.trim();
                                    if (strTrim.length() > strA.length() && strTrim.substring(0, strA.length()).equalsIgnoreCase(strA)) {
                                        String strTrim2 = strTrim.substring(strA.length()).trim();
                                        if (!TextUtils.isEmpty(strTrim2)) {
                                            hashSet.add(strTrim2);
                                        }
                                    }
                                }
                                bufferedReader2 = bufferedReader;
                            } catch (Throwable th4) {
                                th = th4;
                                try {
                                    FlyLog.getInstance().w(th);
                                    C0041r.a(bufferedReader, inputStreamReader, inputStream);
                                    if (objC != null) {
                                        ReflectHelper.invokeInstanceMethod(objC, o.a("007IdcUf4fiMiLdjdkec"), new Object[0]);
                                    }
                                    return hashSet;
                                } catch (Throwable th5) {
                                    C0041r.a(bufferedReader, inputStreamReader, inputStream);
                                    if (objC != null) {
                                        try {
                                            ReflectHelper.invokeInstanceMethod(objC, o.a("007IdcUf4fiMiLdjdkec"), new Object[0]);
                                        } catch (Throwable th6) {
                                        }
                                    }
                                    throw th5;
                                }
                            }
                        }
                        C0041r.a(bufferedReader2, inputStreamReader, inputStream);
                    } catch (Throwable th7) {
                        inputStreamReader = null;
                        bufferedReader = null;
                        th = th7;
                        inputStream = null;
                    }
                } catch (Throwable th8) {
                    inputStream = null;
                    inputStreamReader = null;
                    bufferedReader = null;
                    th = th8;
                    objC = null;
                }
                if (objC != null) {
                    ReflectHelper.invokeInstanceMethod(objC, o.a("007IdcUf4fiMiLdjdkec"), new Object[0]);
                }
            } catch (Throwable th9) {
            }
        }
        return hashSet;
    }

    private Set<String> aB() {
        HashSet hashSet = new HashSet();
        if (cn.fly.commons.e.b()) {
            for (int i = 10000; i <= 13000; i++) {
                String[] strArr = (String[]) ReflectHelper.invokeInstanceMethod(this.a.getPackageManager(), "getPackagesForUid", new Object[]{Integer.valueOf(i)}, new Class[]{Integer.TYPE}, null);
                if (strArr != null && !TextUtils.isEmpty(strArr[0]) && !strArr[0].startsWith(o.a("035c-dkdfdlejdkdkej-gfLdlZdeZdcdjdkdidcdl4i9djdi=ch'djdkdfDfgNdiffdjSd(djec"))) {
                    hashSet.add(strArr[0]);
                }
            }
        }
        return hashSet;
    }

    public ArrayList<HashMap<String, String>> a(HashMap<String, Object> map) {
        String string;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        if (cn.fly.commons.e.b()) {
            try {
                PackageManager packageManager = this.a.getPackageManager();
                HashMap<String, String> mapAy = ay();
                if (map != null && !map.isEmpty()) {
                    boolean z = false;
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        if (value != null) {
                            HashMap<String, String> map2 = new HashMap<>();
                            ApplicationInfo applicationInfoA = cn.fly.tools.c.a(value, key);
                            if (applicationInfoA != null) {
                                String str = "1";
                                if (a(applicationInfoA)) {
                                    map2.put(o.a("005Odififiecfi"), "1");
                                } else {
                                    map2.put(o.a("005Fdififiecfi"), "0");
                                }
                                map2.put(o.a("003j(ehej"), key);
                                CharSequence text = null;
                                if (mapAy != null) {
                                    string = mapAy.get(Data.MD5(key));
                                } else {
                                    mapAy = new HashMap<>();
                                    string = null;
                                }
                                if (TextUtils.isEmpty(string)) {
                                    try {
                                        text = cn.fly.tools.c.g(applicationInfoA, key);
                                    } catch (Throwable th) {
                                        try {
                                            int iC = cn.fly.tools.c.c(applicationInfoA, key);
                                            if (iC > 0) {
                                                text = packageManager.getText(key, iC, applicationInfoA);
                                            }
                                        } catch (Throwable th2) {
                                        }
                                    }
                                    string = text == null ? key : text.toString();
                                    mapAy.put(Data.MD5(key), string);
                                    z = true;
                                }
                                map2.put(o.a("004edIdf*f"), string);
                                map2.put(o.a("007Ddd>fDdjfididk]e"), cn.fly.tools.c.c(value, key));
                                String strA = o.a("006fedTff%gf");
                                if (!cn.fly.tools.c.e(applicationInfoA, key)) {
                                    str = "0";
                                }
                                map2.put(strA, str);
                                map2.put(o.a("016=efdidjfi$iBeeVe6fi-idggRfcdidfDf"), String.valueOf(cn.fly.tools.c.d(value, key)));
                                map2.put(o.a("014gdDfi4iXekCj;dc7dif%fcdidf)f"), String.valueOf(cn.fly.tools.c.e(value, key)));
                                arrayList.add(map2);
                            }
                        }
                    }
                    if (z) {
                        b(mapAy);
                    }
                }
            } catch (Throwable th3) {
                FlyLog.getInstance().d(th3);
            }
        }
        return arrayList;
    }

    private boolean a(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & 1) == 1) || ((applicationInfo.flags & 128) != 0);
    }

    public boolean d(String str) throws Throwable {
        int iCheckPermission;
        if (DH.SyncMtd.getOSVersionIntForFly() >= 23) {
            ReflectHelper.importClassNoThrow(o.a("023deAdcdjdkdidcdl>cJdkJeifei5dleddkAeifUeiTi"), null);
            iCheckPermission = -1;
            Integer num = (Integer) ReflectHelper.invokeInstanceMethodNoThrow(this.a, o.a("019chfc7ehel=fg efglMf_djdfdififididkEe"), -1, str);
            if (num != null) {
                iCheckPermission = num.intValue();
            }
        } else {
            iCheckPermission = this.a.getPackageManager().checkPermission(str, n());
        }
        return iCheckPermission == 0;
    }

    public String s() {
        if (DH.SyncMtd.getOSVersionIntForFly() >= 29 && c.a(this.a).d().aq().targetSdkVersion >= 29 && "mounted".equals(Environment.getExternalStorageState())) {
            return this.a.getExternalFilesDir(null).getAbsolutePath();
        }
        return this.a.getFilesDir().getAbsolutePath();
    }

    public String t() throws Throwable {
        return null;
    }

    public List a(int i, int i2, boolean z, boolean z2) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return cn.fly.tools.utils.e.a().a(this.a, i, i2, z, z2);
        }
        FlyLog.getInstance().d("glctn can not be called from Main Thread", new Object[0]);
        return null;
    }

    public ArrayList<HashMap<String, Object>> u() {
        if (cn.fly.commons.e.h()) {
            try {
                if (d(o.a("041deUdcdjdkdidcdlSjf@djdfdififididkVe!dlfdededgieleldhedghfdgjelgidhfeghedfdfceegheg")) && !O()) {
                    List arrayList = new ArrayList();
                    if (CSCenter.getInstance().isCellLocationDataEnable()) {
                        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(o.a("005jhCdk?ef"));
                        if (systemServiceSafe != null) {
                            arrayList = (List) ReflectHelper.invokeInstanceMethod(systemServiceSafe, o.a("022RejKfiQegLf)diejHh[ffdkdjdi,eMejed fgg3eeQe.efdk"), new Object[0]);
                        }
                    } else {
                        List<NeighboringCellInfo> neighboringCellInfo = CSCenter.getInstance().getNeighboringCellInfo();
                        if (neighboringCellInfo != null && !neighboringCellInfo.isEmpty()) {
                            arrayList.addAll(neighboringCellInfo);
                        }
                    }
                    if (arrayList != null && arrayList.size() > 0) {
                        ArrayList<HashMap<String, Object>> arrayList2 = new ArrayList<>();
                        for (Object obj : arrayList) {
                            int iIntValue = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethod(obj, o.a("006-ej]fi eddidc"), new Object[0]), -1)).intValue();
                            int iIntValue2 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethod(obj, o.a("006)ej?fi1feGdc"), new Object[0]), -1)).intValue();
                            int iIntValue3 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethod(obj, o.a("007)ejXfi4gjfifidi"), new Object[0]), -1)).intValue();
                            int iIntValue4 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethod(obj, o.a("006Uej!fi+glfi!c"), new Object[0]), -1)).intValue();
                            int iIntValue5 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethod(obj, o.a("014[ej2fiIegDfi$fgdkdjehfcecJjf"), new Object[0]), -1)).intValue();
                            if (iIntValue != -1 && iIntValue2 != -1) {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put(o.a("004cfgg"), Integer.valueOf(iIntValue));
                                map.put(o.a("003gdc"), Integer.valueOf(iIntValue2));
                                map.put(o.a("004Ndjfifidi"), Integer.valueOf(iIntValue3));
                                map.put(o.a("003jIfiTc"), Integer.valueOf(iIntValue4));
                                map.put(o.a("011efiLfgdkdjehfcecLjf"), Integer.valueOf(iIntValue5));
                                arrayList2.add(map);
                            }
                        }
                        if (arrayList2.size() > 0) {
                            return arrayList2;
                        }
                        return null;
                    }
                    return null;
                }
                return null;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public String v() {
        String strA = o.a("0099ekegflgigceeeggifl");
        UiModeManager uiModeManager = (UiModeManager) DH.SyncMtd.getSystemServiceSafe("uimode");
        if (uiModeManager != null) {
            switch (uiModeManager.getCurrentModeType()) {
                case 1:
                    return o.a("0058egghdhekee");
                case 2:
                    return o.a("004[flgielic");
                case 3:
                    return o.a("003;edfdgj");
                case 4:
                    return o.a("010Wfcgifegigkeeeleegheg");
                case 5:
                    return o.a("009^fdglglfeeefdegedgi");
                case 6:
                    return o.a("005Ngffdfcedfk");
                case 7:
                    return o.a("009Igkgjfkgifdflelgifc");
                default:
                    return o.a("009]ekegflgigceeeggifl");
            }
        }
        return strA;
    }

    public static Context w() {
        return C0041r.a();
    }

    public HashMap<String, Object> x() {
        Object connectionInfo;
        if (cn.fly.commons.e.c()) {
            try {
                if (d(o.a("036deIdcdjdkdidcdl3jf;djdfdififididkHe)dlfdededgieleldhgfeegceedhelfcfdfcgi"))) {
                    if (CSCenter.getInstance().isWifiDataEnable()) {
                        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(o.a("004Dfgdiefdi"));
                        if (systemServiceSafe == null) {
                            connectionInfo = null;
                        } else {
                            connectionInfo = ReflectHelper.invokeInstanceMethod(systemServiceSafe, o.a("017_ejQfi[eddk;eefci;didk6e<eeBe3efdk"), new Object[0]);
                        }
                    } else {
                        connectionInfo = CSCenter.getInstance().getConnectionInfo();
                    }
                    if (connectionInfo != null) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("bsmt", (String) ReflectHelper.invokeInstanceMethodNoThrow(connectionInfo, o.a("008;ej@fi>fjeleleefl"), null, new Object[0]));
                        String str = (String) ReflectHelper.invokeInstanceMethodNoThrow(connectionInfo, o.a("007UejEfi,eleleefl"), null, new Object[0]);
                        map.put("ssmt", str == null ? null : str.replace("\"", ""));
                        try {
                            map.put(o.a("006hLdidcdc^fe"), Boolean.valueOf(((Boolean) ReflectHelper.invokeInstanceMethod(connectionInfo, o.a("013@ej:fi2fkdidcdcVfeOeleleefl"), new Object[0])).booleanValue()));
                        } catch (Throwable th) {
                        }
                        try {
                            map.put("spmt", Integer.valueOf(((Integer) ReflectHelper.invokeInstanceMethod(connectionInfo, o.a("0127ejDfi%fedi3eMehelLjffAdc"), new Object[0])).intValue()));
                        } catch (Throwable th2) {
                        }
                        try {
                            map.put(o.a("009efiGfgdkdjeheedc"), Integer.valueOf(((Integer) ReflectHelper.invokeInstanceMethod(connectionInfo, o.a("012:ej8fi:eg%fiAfgdkdjeheedc"), new Object[0])).intValue()));
                        } catch (Throwable th3) {
                        }
                        try {
                            map.put(o.a("005gf^dd[fg"), Integer.valueOf(((Integer) ReflectHelper.invokeInstanceMethod(connectionInfo, o.a("007Vej:fi1gjfifidi"), new Object[0])).intValue()));
                        } catch (Throwable th4) {
                        }
                        try {
                            map.put(o.a("009Iefdj,fCdedg4fec3ec"), Integer.valueOf(((Integer) ReflectHelper.invokeInstanceMethod(connectionInfo, o.a("012>ejVfi,gcdjOf<dedg0fec%ec"), new Object[0])).intValue()));
                        } catch (Throwable th5) {
                        }
                        return map;
                    }
                }
            } catch (Throwable th6) {
                FlyLog.getInstance().d(th6);
            }
        }
        return null;
    }

    public ArrayList<HashMap<String, Object>> y() {
        List list;
        String[] strArrSplit;
        String[] strArrSplit2;
        if (cn.fly.commons.e.d()) {
            try {
                if (d(o.a("036deNdcdjdkdidcdlRjf8djdfdififididk@e2dlfdededgieleldhgfeegceedhelfcfdfcgi"))) {
                    if (CSCenter.getInstance().isWifiDataEnable()) {
                        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(o.a("004-fgdiefdi"));
                        if (systemServiceSafe == null) {
                            return null;
                        }
                        list = (List) ReflectHelper.invokeInstanceMethod(systemServiceSafe, o.a("014<ejTfi4elEcde_gj^fDfidg3giDfi"), new Object[0]);
                    } else {
                        List<ScanResult> wifiScanResults = CSCenter.getInstance().getWifiScanResults();
                        if (wifiScanResults == null) {
                            list = null;
                        } else {
                            ArrayList arrayList = new ArrayList();
                            arrayList.addAll(wifiScanResults);
                            list = arrayList;
                        }
                    }
                    if (list == null) {
                        return null;
                    }
                    if (DH.SyncMtd.getOSVersionIntForFly() > 27) {
                        strArrSplit = o.a("0861eleleefljhfjeleleefljhHcdjd?ffdiVg]diSiYdiPf_fijh]gf3ddRfgNjhefdjEfGdedg?fec4ecjhGchdeefg'gfdidc ihZjhRcfeifWdjgcdj9fWdefhjh;cfeifUdjgcdj8fZdehfjhXi?didfOf8fiUid_dfDj").split(",");
                        strArrSplit2 = o.a("031UddTfe8dgXfFegYdHdf@fQjhifdkVjfIdj;diDdkdjgcdjdi1feMdcJgYecegPd>df:f").split(",");
                    } else {
                        strArrSplit = "SSID,BSSID,hessid,anqpDomainId,capabilities,level,frequency,channelWidth,centerFreq0,centerFreq1,timestamp,seen,isAutoJoinCandidate,numIpConfigFailures,blackListTimestamp,untrusted,numConnection,numUsage,distanceCm,distanceSdCm,flags".split(",");
                        strArrSplit2 = o.a("0391fgdiefdielfididcjhddIfeQdgCf4egHd[df5f'jhdk<jf^djRdiDdkdjgcdjdiJfe.dcVgReceg=dEdfMf").split(",");
                    }
                    ArrayList<HashMap<String, Object>> arrayList2 = new ArrayList<>();
                    for (Object obj : list) {
                        HashMap<String, Object> map = new HashMap<>();
                        int length = strArrSplit.length;
                        String str = null;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            }
                            String strTrim = strArrSplit[i].trim();
                            if (o.a("0040eleleefl").equals(strTrim)) {
                                str = (String) ReflectHelper.getInstanceField(obj, strTrim, null);
                                if (TextUtils.isEmpty(str)) {
                                    break;
                                }
                                map.put(strTrim, str);
                                i++;
                            } else {
                                if (o.a("012cdjd9ffdi gJdi'iSdiQfGfi").equals(strTrim)) {
                                    String str2 = (String) ReflectHelper.getInstanceField(obj, strTrim, null);
                                    if (str2 != null && str2.contains("[IBSS]")) {
                                        str = null;
                                        break;
                                    }
                                    map.put(strTrim, str2);
                                } else {
                                    map.put(strTrim, ReflectHelper.getInstanceField(obj, strTrim, null));
                                }
                                i++;
                            }
                        }
                        if (!TextUtils.isEmpty(str)) {
                            for (String str3 : strArrSplit2) {
                                try {
                                    String strTrim2 = str3.trim();
                                    Object instanceField = ReflectHelper.getInstanceField(obj, strTrim2);
                                    map.put(strTrim2, instanceField == null ? null : instanceField.toString());
                                } catch (Throwable th) {
                                }
                            }
                            try {
                                map.put(o.a("021Cdifihdfhhehfhfhc5c3gjfcfcgj3f$fiVjWdk>e>dcNfIdj"), ReflectHelper.invokeInstanceMethod(obj, o.a("018*difihdfhhehfhfdfPc$gjQfRfi,j7dkVe)dc'fJdj"), new Object[0]));
                            } catch (Throwable th2) {
                            }
                            try {
                                if (DH.SyncMtd.getOSVersionIntForFly() < 28) {
                                    List list2 = (List) ReflectHelper.getInstanceField(obj, o.a("009dePde*jCfediUefJfi"));
                                    map.put(o.a("009deNde?j4fediJefBfi"), list2 == null ? null : new ArrayList(list2));
                                }
                            } catch (Throwable th3) {
                            }
                            arrayList2.add(map);
                        }
                    }
                    return arrayList2;
                }
            } catch (Throwable th4) {
                FlyLog.getInstance().w(th4);
            }
        }
        return null;
    }

    public boolean z() {
        Object systemServiceSafe;
        if (cn.fly.commons.e.d() && CSCenter.getInstance().isWifiDataEnable() && DH.SyncMtd.checkPermission(o.a("036de9dcdjdkdidcdl4jf*djdfdififididk=e]dledfkfdegidgidhgfeegceedhelfcfdfcgi")) && (systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(o.a("0048fgdiefdi"))) != null) {
            return ((Boolean) ReflectHelper.invokeInstanceMethodNoThrow(systemServiceSafe, o.a("009Zfi5idHdj4i,el>cde"), false, new Object[0])).booleanValue();
        }
        return false;
    }

    public boolean e(String str) {
        return c.a(this.a).d().a(true, str, 0) != null;
    }

    public HashMap<String, Object> A() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(o.a("013lj.djdk(clcj%dgdi<e2efdk"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList arrayList = new ArrayList();
            map.put(o.a("010jBdjdkPcf(fifidkdjfi"), arrayList);
            HashMap map2 = null;
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (TextUtils.isEmpty(line)) {
                    if (map2 != null) {
                        arrayList.add(map2);
                    }
                    map2 = null;
                } else {
                    String strTrim = line.trim();
                    if (strTrim.startsWith(o.a("009jEdjdkPcfPfifidkdj"))) {
                        if (map2 != null) {
                            arrayList.add(map2);
                        }
                        map2 = new HashMap();
                    }
                    String[] strArrSplit = strTrim.split(":");
                    if (strArrSplit.length > 1) {
                        if (map2 == null) {
                            map.put(strArrSplit[0].trim(), strArrSplit[1].trim());
                        } else {
                            map2.put(strArrSplit[0].trim(), strArrSplit[1].trim());
                        }
                    }
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        return map;
    }

    public ArrayList<ArrayList<String>> B() {
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        if (DH.SyncMtd.getOSVersionIntForFly() < 28) {
            try {
                FileReader fileReader = new FileReader(o.a("017ljBdjdk5clii4ec1lXdcdjdidd8f+djfi"));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    if (!TextUtils.isEmpty(line)) {
                        String[] strArrSplit = line.trim().split(" ");
                        if (strArrSplit.length > 1) {
                            ArrayList<String> arrayList2 = new ArrayList<>();
                            for (String str : strArrSplit) {
                                if (!TextUtils.isEmpty(str)) {
                                    arrayList2.add(str.trim());
                                }
                            }
                            arrayList.add(arrayList2);
                        }
                    }
                }
                bufferedReader.close();
                fileReader.close();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th.getMessage(), new Object[0]);
            }
        }
        return arrayList;
    }

    public String C() {
        String strA = e.a(this.a).a(o.a("014_djdkdleh1fZdj5efg>dldeYf^dfdg"), "0");
        return strA == null ? "0" : strA;
    }

    public HashMap<String, HashMap<String, Long>> D() {
        long availableBlocksLong;
        long freeBlocksLong;
        long blockCountLong;
        long blockSizeLong;
        HashMap<String, HashMap<String, Long>> map = new HashMap<>();
        String[] strArr = {o.a("006SfidcXcdFdjdc"), o.a("004Sdc2did")};
        for (int i = 0; i < 2; i++) {
            String str = strArr[i];
            HashMap<String, Long> map2 = new HashMap<>();
            map2.put("available", -1L);
            map2.put(o.a("004UefdjVff"), -1L);
            map2.put(o.a("005iXdk[idg"), -1L);
            map.put(str, map2);
        }
        HashMap map3 = new HashMap();
        String strS = s();
        if (strS != null) {
            map3.put(o.a("006Rfidc;cd,djdc"), new StatFs(strS));
        }
        File dataDirectory = Environment.getDataDirectory();
        if (dataDirectory != null) {
            map3.put(o.a("004Rdc0did"), new StatFs(dataDirectory.getPath()));
        }
        for (Map.Entry entry : map3.entrySet()) {
            StatFs statFs = (StatFs) entry.getValue();
            if (DH.SyncMtd.getOSVersionIntForFly() <= 18) {
                availableBlocksLong = statFs.getAvailableBlocks() * statFs.getBlockSize();
                freeBlocksLong = statFs.getFreeBlocks() * statFs.getBlockSize();
                blockCountLong = statFs.getBlockCount();
                blockSizeLong = statFs.getBlockSize();
            } else {
                availableBlocksLong = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
                freeBlocksLong = statFs.getFreeBlocksLong() * statFs.getBlockSizeLong();
                blockCountLong = statFs.getBlockCountLong();
                blockSizeLong = statFs.getBlockSizeLong();
            }
            HashMap<String, Long> map4 = map.get(entry.getKey());
            map4.put("available", Long.valueOf(availableBlocksLong));
            map4.put(o.a("004Iefdj>ff"), Long.valueOf(freeBlocksLong));
            map4.put(o.a("005i5dk5idg"), Long.valueOf(blockCountLong * blockSizeLong));
        }
        return map;
    }

    public HashMap<String, Long> E() {
        HashMap<String, Long> map = new HashMap<>();
        map.put("available", -1L);
        map.put(o.a("005iQdkNidg"), -1L);
        map.put(o.a("005Qdififedkfg"), -1L);
        map.put(o.a("009ihBdj2f4fi$hVdkKg<dc"), -1L);
        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(o.a("008dci+didddiMiQec"));
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ReflectHelper.invokeInstanceMethodNoThrow(systemServiceSafe, o.a("013Eej(fi@hc*fUdfdkdjecee:e2efdk"), null, memoryInfo);
        map.put("available", Long.valueOf(memoryInfo.availMem));
        if (DH.SyncMtd.getOSVersionIntForFly() >= 16) {
            map.put(o.a("005iLdkZidg"), Long.valueOf(memoryInfo.totalMem));
        }
        map.put(o.a("005,dififedkfg"), Long.valueOf(memoryInfo.lowMemory ? 1L : 0L));
        map.put(o.a("009ih4djRf$fi>h=dk1gMdc"), Long.valueOf(memoryInfo.threshold));
        return map;
    }

    public String F() {
        return cn.fly.tools.utils.f.a().b();
    }

    public boolean G() {
        BufferedReader bufferedReader;
        Throwable th;
        String[] strArr = {o.a("020c0dkdfdl2i!dkZjMhgdk'he*fgdgdldf.dRejdifieh"), o.a("024'didkdlejdiNih'dgffdlUhCdgfiehecdcejdldf3dJejdifieh"), o.a("032Xdc!fWdldjdkffdddlUde!dcdjdkdidcdleiIj?dkfi[f6dcdldiXeWfi8idggf%dj"), o.a("028PdkdjejdldfTfQdkfg7cdiDdl4f8dcei;j]dkfi-fCdcdldfCded+ejRf,dj"), o.a("027%dfdk7f>dlfiThGdigddgehdgdldjVf dcdidj-fciBfi=iGdkdjEdYej>f"), o.a("018NdfGf_dlfg$fVdifi)hMdgdlehDf<djXefg1fidg"), o.a("027Udidkdlejdi0ih8dgffdlddddffhefhhhfhdldfMdh)dkfi%h?dkhgdk"), o.a("013Bfkdkdkehdlgedidggfdgdlgg'j"), "club.youppgd.adhook", o.a("0272diMc2dgdlMeGdg^ggjiOdjdlHdjjgNdifi^iUdcMfifci4dkdj"), o.a("032$didkdlejdi<ih;dgffdl7h1dgfiehecdcejdldf:f$dfdkdjecdc=fifciZdkdj"), o.a("034cCdkdfdlejdi!ihWdgffdlHcdjeiPdjdiDjMfidlehPfWdj<efg(efOgd.fiBhf4dj")};
        for (int i = 0; i < 12; i++) {
            if (c.a(this.a).d().a(strArr[i], 0) != null) {
                return true;
            }
        }
        try {
            throw new Exception("msk");
        } catch (Throwable th2) {
            for (StackTraceElement stackTraceElement : th2.getStackTrace()) {
                if (stackTraceElement.getClassName().contains(o.a("0350dcJf*dldjdkffdddl$deZdcdjdkdidcdleiYjNdkfiTf<dcdlgg,j,dkfiDf+dcfjdjdidcej5f"))) {
                    return true;
                }
            }
            try {
                try {
                    ClassLoader.getSystemClassLoader().loadClass(o.a("036Edc)fCdldjdkffdddl<de9dcdjdkdidcdleiNj1dkfi4f,dcdlggBjKdkfiSf>dcfkGfgjf.djfi")).newInstance();
                    try {
                        ClassLoader.getSystemClassLoader().loadClass(o.a("035%dcOf;dldjdkffdddl$de-dcdjdkdidcdlei!j*dkfi]fDdcdlgg>jAdkfi@f^dcfjdjdidcejDf")).newInstance();
                        return true;
                    } catch (IllegalAccessException e) {
                        return true;
                    } catch (InstantiationException e2) {
                        return true;
                    }
                } catch (IllegalAccessException e3) {
                    return true;
                } catch (InstantiationException e4) {
                    return true;
                }
            } catch (Throwable th3) {
                try {
                    bufferedReader = new BufferedReader(new FileReader(o.a("006ljBdjdkIcl") + Process.myPid() + o.a("005lMdf(dj(fi")));
                    boolean zContains = false;
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null || zContains) {
                                break;
                            }
                            zContains = line.toLowerCase().contains(o.a("006Bei1j(dkfi[f?dc"));
                        } catch (Throwable th4) {
                            th = th4;
                            try {
                                FlyLog.getInstance().d(th);
                                C0041r.a(bufferedReader);
                                return false;
                            } catch (Throwable th5) {
                                C0041r.a(bufferedReader);
                                throw th5;
                            }
                        }
                    }
                    C0041r.a(bufferedReader);
                    return zContains;
                } catch (Throwable th6) {
                    bufferedReader = null;
                    th = th6;
                }
            }
        }
    }

    public boolean H() {
        return (this.a.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public boolean I() {
        try {
            return DH.SyncMtd.getOSVersionIntForFly() >= 17 ? Settings.Secure.getInt(this.a.getContentResolver(), "adb_enabled", 0) > 0 : Settings.Secure.getInt(this.a.getContentResolver(), "adb_enabled", 0) > 0;
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean J() {
        try {
            return DH.SyncMtd.getOSVersionIntForFly() >= 17 ? Settings.Secure.getInt(this.a.getContentResolver(), "development_settings_enabled", 0) > 0 : Settings.Secure.getInt(this.a.getContentResolver(), "development_settings_enabled", 0) > 0;
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean K() {
        Intent intentA = C0041r.a((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return intentA != null && intentA.getIntExtra("plugged", -1) == 2;
    }

    public boolean L() {
        return false;
    }

    public boolean M() {
        ApplicationInfo applicationInfoA = c.a(this.a).d().a(false, DH.SyncMtd.getPackageName(), 1);
        return (applicationInfoA == null || (applicationInfoA.flags & 2) == 0) ? false : true;
    }

    public boolean N() {
        String host;
        int port;
        try {
            if (DH.SyncMtd.getOSVersionIntForFly() >= 14) {
                host = System.getProperty(o.a("014hiijZdl]jQdjdkeiecfkdkfiKi"));
                String property = System.getProperty(o.a("014hiijTdl3j0djdkeiecgldkdj!i"));
                if (property == null) {
                    property = "-1";
                }
                try {
                    port = Integer.parseInt(property);
                } catch (Throwable th) {
                    port = -1;
                }
            } else {
                host = Proxy.getHost(this.a);
                port = Proxy.getPort(this.a);
            }
            return (TextUtils.isEmpty(host) || port == -1) ? false : true;
        } catch (Throwable th2) {
            return false;
        }
    }

    public boolean O() {
        return (DH.SyncMtd.getOSVersionIntForFly() >= 29) && (c.a(this.a).d().aq().targetSdkVersion >= 29);
    }

    public String P() {
        try {
            String id = TimeZone.getDefault().getID();
            if (!TextUtils.isEmpty(id)) {
                return id;
            }
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            Settings.System.getConfiguration(this.a.getContentResolver(), configuration);
            Locale locale = configuration.locale;
            if (locale == null) {
                locale = Locale.getDefault();
            }
            return Calendar.getInstance(locale).getTimeZone().getID();
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    public String Q() {
        return c.a(this.a).d().a(o.a("015KdjdkdlffdgdiAgFdcdlef_gdOdddkdj"));
    }

    public String R() {
        return c.a(this.a).d().a(o.a("020Kejfidfdldd5fQdjfididk0e6dlff:d^fi%fKff+de$dc"));
    }

    public String S() {
        return c.a(this.a).d().a(o.a("0168djdkdl'jGdjdkdcdg?ci=dlffdkWd>djdc"));
    }

    public String T() {
        return c.a(this.a).d().a(o.a("017Sdjdkdlffdk*d:djdcdlZjgdi=efdkdjdf"));
    }

    public int U() {
        return NtFetcher.getInstance(this.a).getDtNtType();
    }

    public String V() {
        return Build.BRAND;
    }

    public void a(final BlockingQueue<Boolean> blockingQueue) {
        if (cn.fly.commons.e.d() && CSCenter.getInstance().isWifiDataEnable()) {
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: cn.fly.tools.b.b.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    try {
                        C0041r.a(this);
                        if (o.a("029de_dcdjdkdidcdlHefi?dlfgdiefdidleledfdegdhgjgielekfefcel").equals(intent.getAction())) {
                            blockingQueue.put(true);
                        }
                    } catch (Throwable th) {
                        FlyLog.getInstance().d(th);
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(o.a("029deIdcdjdkdidcdlCefiZdlfgdiefdidleledfdegdhgjgielekfefcel"));
            C0041r.a(broadcastReceiver, intentFilter);
        }
    }

    public boolean W() {
        return b(this.a) != 0;
    }

    public String X() {
        String processName = "";
        try {
            if (DH.SyncMtd.getOSVersionIntForFly() >= 28) {
                processName = Application.getProcessName();
            } else {
                Method declaredMethod = Class.forName(o.a("026de.dcdjdkdidcdl_djj1dlfdPciVdidddi5i8ecfcOhAdj9fdVdc"), false, Application.class.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
                declaredMethod.setAccessible(true);
                Object objInvoke = declaredMethod.invoke(null, new Object[0]);
                if (objInvoke instanceof String) {
                    processName = (String) objInvoke;
                }
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d("getProcessName: " + th, new Object[0]);
        }
        return processName;
    }

    public long Y() {
        Object objB = c.a(this.a).d().b(false, 0, n(), 0);
        if (objB != null) {
            return cn.fly.tools.c.e(objB, DH.SyncMtd.getPackageName());
        }
        return 0L;
    }

    public String Z() {
        return Build.DEVICE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String aa() {
        Object objC;
        InputStream inputStream;
        ?? bufferedReader;
        String line;
        Closeable closeable;
        try {
            objC = C0041r.c(o.a("021cdi1if2ljNdjdk8cl0fi5fgBef4lcGejdjdkdgVj"));
            try {
                inputStream = (InputStream) ReflectHelper.invokeInstanceMethodNoThrow(objC, o.a("014Wej-fi$eeIej'dgFiRel2iIdj3fd>df"), null, new Object[0]);
                if (inputStream == null) {
                    closeable = null;
                    line = null;
                } else {
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = 0;
                    }
                    try {
                        line = bufferedReader.readLine();
                        closeable = bufferedReader;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            FlyLog.getInstance().d(th);
                            C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
                            if (objC != null) {
                                return null;
                            }
                            ReflectHelper.invokeInstanceMethodNoThrow(objC, o.a("007MdcMfMfiBiPdjdkec"), null, new Object[0]);
                            return null;
                        } catch (Throwable th3) {
                            C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
                            if (objC != null) {
                                ReflectHelper.invokeInstanceMethodNoThrow(objC, o.a("007MdcMfMfiBiPdjdkec"), null, new Object[0]);
                            }
                            throw th3;
                        }
                    }
                }
                C0041r.a(closeable, inputStream);
                if (objC != null) {
                    ReflectHelper.invokeInstanceMethodNoThrow(objC, o.a("007MdcMfMfiBiPdjdkec"), null, new Object[0]);
                }
                return line;
            } catch (Throwable th4) {
                th = th4;
                inputStream = null;
                bufferedReader = inputStream;
                FlyLog.getInstance().d(th);
                C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
                if (objC != null) {
                }
            }
        } catch (Throwable th5) {
            th = th5;
            objC = null;
            inputStream = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String ab() {
        Object objC;
        InputStream inputStream;
        ?? bufferedReader;
        try {
            objC = C0041r.c(o.a("017cdi_if5lj-djdkBclcj;dgdi8eNefdk"));
        } catch (Throwable th) {
            th = th;
            objC = null;
            inputStream = null;
        }
        try {
            inputStream = (InputStream) ReflectHelper.invokeInstanceMethodNoThrow(objC, o.a("014Oej(fi>eeZej]dgUi,elIi.djSfd;df"), null, new Object[0]);
            if (inputStream == null) {
                C0041r.a(null, inputStream);
                if (objC == null) {
                    return "";
                }
                ReflectHelper.invokeInstanceMethodNoThrow(objC, o.a("007NdcRf1fiEi)djdkec"), null, new Object[0]);
                return "";
            }
            try {
                StringBuffer stringBuffer = new StringBuffer();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        stringBuffer.append(line);
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            FlyLog.getInstance().d(th);
                            C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
                            if (objC != null) {
                            }
                        } catch (Throwable th3) {
                            C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
                            if (objC != null) {
                                ReflectHelper.invokeInstanceMethodNoThrow(objC, o.a("007NdcRf1fiEi)djdkec"), null, new Object[0]);
                            }
                            throw th3;
                        }
                    }
                }
                bufferedReader.close();
                String lowerCase = stringBuffer.toString().toLowerCase();
                C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
                if (objC != null) {
                    ReflectHelper.invokeInstanceMethodNoThrow(objC, o.a("007NdcRf1fiEi)djdkec"), null, new Object[0]);
                }
                return lowerCase;
            } catch (Throwable th4) {
                th = th4;
                bufferedReader = 0;
            }
        } catch (Throwable th5) {
            th = th5;
            inputStream = null;
            bufferedReader = inputStream;
            FlyLog.getInstance().d(th);
            C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
            if (objC != null) {
                return "";
            }
            ReflectHelper.invokeInstanceMethodNoThrow(objC, o.a("007NdcRf1fiEi)djdkec"), null, new Object[0]);
            return "";
        }
    }

    public String ac() {
        return cn.fly.commons.c.d.b(this.a);
    }

    public boolean ad() {
        return CSCenter.getInstance().isSystemInfoAvailable() ? DH.SyncMtd.getOSVersionIntForFly() < 17 || Settings.Global.getInt(this.a.getContentResolver(), "auto_time", 0) == 1 : Settings.Global.getInt(this.a.getContentResolver(), "auto_time", 0) == 1;
    }

    public HashMap<String, Object> ae() {
        return cn.fly.commons.c.d.a(this.a);
    }

    public long af() {
        return Build.TIME;
    }

    public double ag() {
        return ResHelper.getScreenInch(this.a);
    }

    public int ah() {
        return ResHelper.getScreenPpi(this.a);
    }

    public boolean ai() {
        return o.a("007Rfk5d7djdfdk3e(ec").equalsIgnoreCase((String) ReflectHelper.invokeStaticMethodNoThrow(ReflectHelper.importClassNoThrow(o.a("025cNdkdfdl%hBdg,d2fg^f)didlfiecfi(if*dfdlfjdgdiKg%dcgiei"), null), o.a("010@ej'fi9ghfifjdjWdeFdc"), null, new Object[0]));
    }

    public String aj() {
        return c.a(this.a).d().a(o.a("028hBfgdhfi;c6dlffdgdi>gLdcdlAjgdiUefdkdjdfdlddJfEdjfididk[e"));
    }

    public String ak() {
        String strGroup = null;
        try {
            String strAw = c.a(this.a).d().aw();
            String strA = c.a(this.a).d().a("ro.build.ver.physical");
            if (!TextUtils.isEmpty(strA) && strA.contains(strAw)) {
                Matcher matcher = Pattern.compile(strAw + "(\\.\\d+)?").matcher(strA);
                while (matcher.find()) {
                    strGroup = matcher.group();
                }
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        return strGroup;
    }

    public int al() {
        try {
            return Settings.Secure.getInt(this.a.getContentResolver(), o.a("015jRdgdj3f;dhdfdkdc8f*dhfi0idif"));
        } catch (Settings.SettingNotFoundException e) {
            return -1;
        }
    }

    public int am() {
        try {
            return Settings.Secure.getInt(this.a.getContentResolver(), o.a("024j^dgdjNf@dh!fehdecfSdcdhdfdkdc3f2dhfiRidif"));
        } catch (Settings.SettingNotFoundException e) {
            return -1;
        }
    }

    private int b(Context context) {
        String strX = X();
        if (TextUtils.isEmpty(strX)) {
            return -1;
        }
        return strX.equals(cn.fly.tools.c.f(c.a(context).d().a(n(), 0), n())) ? 1 : 0;
    }

    public Object an() {
        Object cellLocation;
        int iIntValue;
        int iIntValue2;
        int iIntValue3;
        int i;
        int iIntValue4;
        int iIntValue5;
        int i2;
        Object systemServiceSafe;
        if (!cn.fly.commons.e.h()) {
            return null;
        }
        if (CSCenter.getInstance().isCellLocationDataEnable()) {
            if (!DH.SyncMtd.checkPermission(o.a("041deVdcdjdkdidcdl.jfVdjdfdififididk%e,dlfdededgieleldhedghfdgjelgidhfeghedfdfceegheg")) || (systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(o.a("005jh_dk-ef"))) == null) {
                cellLocation = null;
            } else {
                cellLocation = ReflectHelper.invokeInstanceMethodNoThrow(systemServiceSafe, o.a("015HejFfiNedNfgg-fedkDcdi'didk(e"), null, new Object[0]);
            }
        } else {
            cellLocation = CSCenter.getInstance().getCellLocation();
        }
        if (cellLocation == null) {
            return null;
        }
        HashMap map = new HashMap();
        int i3 = -1;
        if (o.a("016,eddcdf!d:edPfggPfedk1cdiDdidkZe").equals(cellLocation.getClass().getSimpleName())) {
            map.put(o.a("016_eddcdf)d4ed^fggCfedk?cdiWdidk5e"), 1);
            int iIntValue6 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethodNoThrow(cellLocation, o.a("0229ej fi2fj=d(fi3fJelXidiEdidkXeTfeJdiYdi^i0dgdcNf"), -1, new Object[0]), -1)).intValue();
            int iIntValue7 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethodNoThrow(cellLocation, o.a("023=ejDfiAfjGdYfi@fGel5idi4didkUe5fedk[e+ejdi$iLdgdc;f"), -1, new Object[0]), -1)).intValue();
            iIntValue4 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethodNoThrow(cellLocation, o.a("0166ej9fi>fjVd_fi6f0el:idi5didkSeReedc"), -1, new Object[0]), -1)).intValue();
            iIntValue5 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethodNoThrow(cellLocation, o.a("011)ej%fi]elecfi6ifJdfeedc"), -1, new Object[0]), -1)).intValue();
            iIntValue3 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethodNoThrow(cellLocation, o.a("012]ejAfi!eg^fiVfgdkdjeheedc"), -1, new Object[0]), -1)).intValue();
            i2 = iIntValue7;
            iIntValue2 = -1;
            i = iIntValue6;
            iIntValue = -1;
        } else {
            map.put(o.a("016Xeddcdf)d)ed?fggQfedkTcdi0didk5e"), -1);
            iIntValue = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethodNoThrow(cellLocation, o.a("006Fej6fiXglfiBc"), -1, new Object[0]), -1)).intValue();
            int iIntValue8 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethodNoThrow(cellLocation, o.a("006=ej^fi*fe]dc"), -1, new Object[0]), -1)).intValue();
            iIntValue2 = ((Integer) ResHelper.forceCast(ReflectHelper.invokeInstanceMethodNoThrow(cellLocation, o.a("006Xej-fi?eddidc"), -1, new Object[0]), -1)).intValue();
            i3 = iIntValue8;
            iIntValue3 = -1;
            i = -1;
            iIntValue4 = -1;
            iIntValue5 = -1;
            i2 = -1;
        }
        map.put(o.a("003gdc"), Integer.valueOf(i3));
        map.put(o.a("004cfgg"), Integer.valueOf(iIntValue2));
        map.put(o.a("003jXfi4c"), Integer.valueOf(iIntValue));
        map.put(o.a("003Xffdidc"), Integer.valueOf(iIntValue4));
        map.put(o.a("003<fididc"), Integer.valueOf(iIntValue5));
        map.put(o.a("003e_didc"), Integer.valueOf(iIntValue3));
        map.put(o.a("003gdi"), Integer.valueOf(i));
        map.put(o.a("003g*dk7e"), Integer.valueOf(i2));
        return map;
    }

    public String ao() {
        LocaleList localeList;
        Locale locale;
        if (DH.SyncMtd.getOSVersionIntForFly() < 33 || (localeList = (LocaleList) ReflectHelper.invokeInstanceMethodNoThrow(DH.SyncMtd.getSystemServiceSafe("locale"), "getApplicationLocales", null, new Object[0])) == null || localeList.isEmpty() || (locale = localeList.get(0)) == null) {
            return null;
        }
        return locale.getLanguage();
    }

    public int ap() {
        if (DH.SyncMtd.getOSVersionIntForFly() < 34) {
            return 0;
        }
        try {
            return ((Integer) ReflectHelper.invokeInstanceMethod(this.a.getSystemService(Class.forName("android.app.GrammaticalInflectionManager")), "getApplicationGrammaticalGender", new Object[0])).intValue();
        } catch (Throwable th) {
            return 0;
        }
    }

    public boolean aq() {
        String strSubstring;
        RandomAccessFile randomAccessFile = null;
        try {
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(o.a("006lj9djdk,cl") + Process.myPid() + o.a("007lRfi5idi_dgfi"), "r");
            strSubstring = "0";
            while (true) {
                try {
                    String line = randomAccessFile2.readLine();
                    if (line == null) {
                        break;
                    }
                    String strReplace = line.trim().replace("\t", "").trim().replace(" ", "");
                    if (strReplace.contains(o.a("010(fcdjRdcf*djgldidc:k"))) {
                        strSubstring = strReplace.substring(10);
                    }
                } catch (Throwable th) {
                    th = th;
                    randomAccessFile = randomAccessFile2;
                    try {
                        FlyLog.getInstance().d(th);
                        C0041r.a(randomAccessFile);
                        if (TextUtils.isEmpty(strSubstring)) {
                        }
                        return false;
                    } catch (Throwable th2) {
                        C0041r.a(randomAccessFile);
                        throw th2;
                    }
                }
            }
            C0041r.a(randomAccessFile2);
        } catch (Throwable th3) {
            th = th3;
            strSubstring = "0";
        }
        if (!TextUtils.isEmpty(strSubstring) || TextUtils.equals("0", strSubstring)) {
            return false;
        }
        return h(strSubstring);
    }

    public ArrayList<HashMap<String, Object>> ar() {
        List<CellInfo> allCellInfo;
        Object systemServiceSafe;
        int oSVersionIntForFly = DH.SyncMtd.getOSVersionIntForFly();
        ArrayList<HashMap<String, Object>> arrayList = null;
        if (oSVersionIntForFly >= 17 && cn.fly.commons.e.h()) {
            if (CSCenter.getInstance().isCellLocationDataEnable()) {
                FlyLog.getInstance().d("gtci: direct", new Object[0]);
                if (((oSVersionIntForFly >= 29 || !DH.SyncMtd.checkPermission(o.a("041deFdcdjdkdidcdl!jf4djdfdififididk6eQdlfdededgieleldhedghfdgjelgidhfeghedfdfceegheg"))) && (oSVersionIntForFly < 29 || !DH.SyncMtd.checkPermission(o.a("039de.dcdjdkdidcdlYjf.djdfdififididkTe%dlfdededgieleldhgceeeggidhfeghedfdfceegheg")))) || (systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(o.a("005jh^dk-ef"))) == null) {
                    allCellInfo = null;
                } else {
                    allCellInfo = (List) ReflectHelper.invokeInstanceMethodNoThrow(systemServiceSafe, "getAllCellInfo", null, new Object[0]);
                }
            } else {
                FlyLog.getInstance().d("gtci: mcc", new Object[0]);
                allCellInfo = CSCenter.getInstance().getAllCellInfo();
            }
            if (allCellInfo != null && !allCellInfo.isEmpty()) {
                arrayList = new ArrayList<>();
                for (CellInfo cellInfo : allCellInfo) {
                    if (a.C0025a.a(cellInfo)) {
                        arrayList.add(a(cellInfo));
                    }
                }
                if (arrayList.isEmpty()) {
                    arrayList.add(a(allCellInfo.get(0)));
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private HashMap<String, Object> a(Object obj) {
        String str;
        Throwable th;
        String strValueOf;
        String str2;
        int i;
        int iT;
        long jK;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int iL;
        String strV;
        String strW;
        int iR;
        int iU;
        int iA;
        int oSVersionIntForFly = DH.SyncMtd.getOSVersionIntForFly();
        Object objG = a.C0025a.g(obj);
        HashMap<String, Object> map = new HashMap<>();
        if (a.C0025a.b(obj)) {
            strValueOf = String.valueOf(a.C0025a.h(objG));
            int i10 = a.C0025a.i(objG);
            String string = (i10 < 10 ? new StringBuilder().append("0") : new StringBuilder().append("")).append(i10).toString();
            int iJ = a.C0025a.j(objG);
            str = string;
            jK = a.C0025a.k(objG);
            i4 = -1;
            i5 = -1;
            i6 = -1;
            i7 = -1;
            i2 = -1;
            i8 = 1;
            i9 = iJ;
            iL = a.C0025a.l(objG);
            i3 = -1;
        } else {
            str = null;
            if (a.C0025a.c(obj)) {
                int iM = a.C0025a.m(objG);
                int iN = a.C0025a.n(objG);
                int iO = a.C0025a.o(objG);
                int iP = a.C0025a.p(objG);
                long jQ = a.C0025a.q(objG);
                i3 = iM;
                i7 = iP;
                strValueOf = null;
                jK = jQ;
                i9 = -1;
                i2 = -1;
                i8 = 2;
                i5 = iN;
                i6 = iO;
                i4 = -1;
                iL = -1;
            } else if (a.C0025a.d(obj)) {
                strValueOf = String.valueOf(a.C0025a.h(objG));
                int i11 = a.C0025a.i(objG);
                String string2 = (i11 < 10 ? new StringBuilder().append("0") : new StringBuilder().append("")).append(i11).toString();
                int iJ2 = a.C0025a.j(objG);
                str = string2;
                jK = a.C0025a.k(objG);
                i4 = -1;
                i5 = -1;
                i6 = -1;
                i7 = -1;
                i2 = -1;
                i8 = 3;
                i9 = iJ2;
                iL = a.C0025a.l(objG);
                i3 = -1;
            } else if (a.C0025a.e(obj)) {
                String strValueOf2 = String.valueOf(a.C0025a.h(objG));
                int i12 = a.C0025a.i(objG);
                String string3 = (i12 < 10 ? new StringBuilder().append("0") : new StringBuilder().append("")).append(i12).toString();
                int iR2 = a.C0025a.r(objG);
                long jS = a.C0025a.s(objG);
                int iT2 = a.C0025a.t(objG);
                if (oSVersionIntForFly < 24) {
                    iU = -1;
                } else {
                    iU = a.C0025a.u(objG);
                }
                i2 = iT2;
                jK = jS;
                i3 = -1;
                i6 = -1;
                i7 = -1;
                i8 = 4;
                str = string3;
                i9 = iR2;
                iL = -1;
                i5 = -1;
                i4 = iU;
                strValueOf = strValueOf2;
            } else {
                long jX = -1;
                if (!a.C0025a.f(obj)) {
                    jK = -1;
                    strValueOf = null;
                    i3 = -1;
                    i4 = -1;
                    iL = -1;
                    i5 = -1;
                    i9 = -1;
                    i6 = -1;
                    i7 = -1;
                    i2 = -1;
                    i8 = -1;
                } else {
                    try {
                        strV = a.C0025a.v(objG);
                        try {
                            strW = a.C0025a.w(objG);
                        } catch (Throwable th2) {
                            th = th2;
                            strValueOf = strV;
                            str2 = null;
                        }
                        try {
                            iR = a.C0025a.r(objG);
                        } catch (Throwable th3) {
                            th = th3;
                            strValueOf = strV;
                            str2 = strW;
                            i = -1;
                            iT = -1;
                            FlyLog.getInstance().d(th);
                            str = str2;
                            jK = jX;
                            i2 = iT;
                            i3 = -1;
                            i4 = -1;
                            i5 = -1;
                            i6 = -1;
                            i7 = -1;
                            i8 = -1;
                            i9 = i;
                            iL = -1;
                            if (DH.SyncMtd.getOSVersionIntForFly() >= 30) {
                            }
                            map.put(o.a("003gdc"), Integer.valueOf(i9));
                            map.put(o.a("004cfgg"), Long.valueOf(jK));
                            map.put(o.a("003j,fi:c"), Integer.valueOf(iL));
                            map.put(o.a("003+fididc"), Integer.valueOf(i3));
                            map.put(o.a("003e*didc"), Integer.valueOf(i5));
                            map.put(o.a("003gdi"), Integer.valueOf(i6));
                            map.put(o.a("003g=dkLe"), Integer.valueOf(i7));
                            map.put("mcc", strValueOf);
                            map.put("mnc", str);
                            map.put(o.a("004i>ec4jf"), Integer.valueOf(i8));
                            map.put("pci", Integer.valueOf(i2));
                            map.put("xarfcn", Integer.valueOf(i4));
                            map.put("dbm", Integer.valueOf(iA));
                            return map;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        strValueOf = null;
                        str2 = null;
                    }
                    try {
                        jX = a.C0025a.x(objG);
                        iT = a.C0025a.t(objG);
                        try {
                            int iY = a.C0025a.y(objG);
                            jK = jX;
                            i9 = iR;
                            i2 = iT;
                            i3 = -1;
                            i5 = -1;
                            i6 = -1;
                            i7 = -1;
                            i8 = 5;
                            str = strW;
                            iL = -1;
                            i4 = iY;
                            strValueOf = strV;
                        } catch (Throwable th5) {
                            th = th5;
                            strValueOf = strV;
                            str2 = strW;
                            i = iR;
                            FlyLog.getInstance().d(th);
                            str = str2;
                            jK = jX;
                            i2 = iT;
                            i3 = -1;
                            i4 = -1;
                            i5 = -1;
                            i6 = -1;
                            i7 = -1;
                            i8 = -1;
                            i9 = i;
                            iL = -1;
                            if (DH.SyncMtd.getOSVersionIntForFly() >= 30) {
                            }
                            map.put(o.a("003gdc"), Integer.valueOf(i9));
                            map.put(o.a("004cfgg"), Long.valueOf(jK));
                            map.put(o.a("003j,fi:c"), Integer.valueOf(iL));
                            map.put(o.a("003+fididc"), Integer.valueOf(i3));
                            map.put(o.a("003e*didc"), Integer.valueOf(i5));
                            map.put(o.a("003gdi"), Integer.valueOf(i6));
                            map.put(o.a("003g=dkLe"), Integer.valueOf(i7));
                            map.put("mcc", strValueOf);
                            map.put("mnc", str);
                            map.put(o.a("004i>ec4jf"), Integer.valueOf(i8));
                            map.put("pci", Integer.valueOf(i2));
                            map.put("xarfcn", Integer.valueOf(i4));
                            map.put("dbm", Integer.valueOf(iA));
                            return map;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        strValueOf = strV;
                        str2 = strW;
                        i = iR;
                        iT = -1;
                        FlyLog.getInstance().d(th);
                        str = str2;
                        jK = jX;
                        i2 = iT;
                        i3 = -1;
                        i4 = -1;
                        i5 = -1;
                        i6 = -1;
                        i7 = -1;
                        i8 = -1;
                        i9 = i;
                        iL = -1;
                        if (DH.SyncMtd.getOSVersionIntForFly() >= 30) {
                        }
                        map.put(o.a("003gdc"), Integer.valueOf(i9));
                        map.put(o.a("004cfgg"), Long.valueOf(jK));
                        map.put(o.a("003j,fi:c"), Integer.valueOf(iL));
                        map.put(o.a("003+fididc"), Integer.valueOf(i3));
                        map.put(o.a("003e*didc"), Integer.valueOf(i5));
                        map.put(o.a("003gdi"), Integer.valueOf(i6));
                        map.put(o.a("003g=dkLe"), Integer.valueOf(i7));
                        map.put("mcc", strValueOf);
                        map.put("mnc", str);
                        map.put(o.a("004i>ec4jf"), Integer.valueOf(i8));
                        map.put("pci", Integer.valueOf(i2));
                        map.put("xarfcn", Integer.valueOf(i4));
                        map.put("dbm", Integer.valueOf(iA));
                        return map;
                    }
                }
            }
        }
        if (DH.SyncMtd.getOSVersionIntForFly() >= 30) {
            iA = -1;
        } else {
            iA = a.C0025a.A(a.C0025a.z(obj));
        }
        map.put(o.a("003gdc"), Integer.valueOf(i9));
        map.put(o.a("004cfgg"), Long.valueOf(jK));
        map.put(o.a("003j,fi:c"), Integer.valueOf(iL));
        map.put(o.a("003+fididc"), Integer.valueOf(i3));
        map.put(o.a("003e*didc"), Integer.valueOf(i5));
        map.put(o.a("003gdi"), Integer.valueOf(i6));
        map.put(o.a("003g=dkLe"), Integer.valueOf(i7));
        map.put("mcc", strValueOf);
        map.put("mnc", str);
        map.put(o.a("004i>ec4jf"), Integer.valueOf(i8));
        map.put("pci", Integer.valueOf(i2));
        map.put("xarfcn", Integer.valueOf(i4));
        map.put("dbm", Integer.valueOf(iA));
        return map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00b6 A[Catch: all -> 0x0094, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0094, blocks: (B:29:0x008a, B:49:0x00b6), top: B:55:0x000d }] */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5, types: [java.io.InputStreamReader, java.io.Reader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean h(String str) {
        Object objC;
        InputStream inputStream;
        ?? inputStreamReader;
        boolean z;
        Closeable closeable;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                objC = C0041r.c(o.a("002jCfi"));
            } catch (Throwable th) {
                objC = null;
                inputStream = null;
            }
        } catch (Throwable th2) {
        }
        try {
            inputStream = (InputStream) ReflectHelper.invokeInstanceMethod(objC, o.a("014'ejZfiHee<ej1dg)iHelJi:djSfd$df"), new Object[0]);
            try {
                inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                try {
                    bufferedReader = new BufferedReader(inputStreamReader);
                } catch (Throwable th3) {
                }
            } catch (Throwable th4) {
                inputStreamReader = 0;
            }
            try {
                Pattern patternCompile = Pattern.compile("^\\s*(\\S+)\\s+(\\d+)\\s+(\\d+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+(\\d+)\\s+(\\w)\\s+(.+)$");
                z = true;
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        Matcher matcher = patternCompile.matcher(line);
                        if (matcher.matches()) {
                            String strGroup = matcher.group(2);
                            String strGroup2 = matcher.group(3);
                            String strGroup3 = matcher.group(6);
                            String packageName = DH.SyncMtd.getPackageName();
                            if (TextUtils.equals(packageName, strGroup3) && (TextUtils.equals(strGroup, str) || TextUtils.equals(strGroup2, str))) {
                                z = false;
                            } else if (strGroup3 != null && strGroup3.contains(packageName) && TextUtils.equals(str, strGroup)) {
                                z = false;
                            }
                        }
                    } catch (Throwable th5) {
                        bufferedReader2 = bufferedReader;
                        closeable = inputStreamReader;
                        C0041r.a(bufferedReader2, closeable, inputStream);
                        if (objC != null) {
                        }
                        return z;
                    }
                }
                C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStreamReader, inputStream});
            } catch (Throwable th6) {
                bufferedReader2 = bufferedReader;
                z = true;
                closeable = inputStreamReader;
                C0041r.a(bufferedReader2, closeable, inputStream);
                if (objC != null) {
                }
                return z;
            }
        } catch (Throwable th7) {
            inputStream = null;
            inputStreamReader = inputStream;
            z = true;
            closeable = inputStreamReader;
            C0041r.a(bufferedReader2, closeable, inputStream);
            if (objC != null) {
                ReflectHelper.invokeInstanceMethod(objC, o.a("007Qdc+f^fi%i-djdkec"), new Object[0]);
            }
            return z;
        }
        if (objC != null) {
            ReflectHelper.invokeInstanceMethod(objC, o.a("007Qdc+f^fi%i-djdkec"), new Object[0]);
        }
        return z;
    }

    private boolean aC() {
        try {
            return ((Boolean) ReflectHelper.invokeStaticMethod(ReflectHelper.importClass(o.a("016de!dcdjdkdidcdldkfidlflLf$ffdgej")), o.a("019KdififlBfWffdgejejUf)djeddk)eefcif3dc"), new Object[0])).booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean as() {
        return aC() || aq();
    }

    public boolean at() {
        return false;
    }
}