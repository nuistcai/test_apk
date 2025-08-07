package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class b {
    private static List<File> a = new ArrayList();

    private static Map<String, Integer> d(String str) {
        if (str == null) {
            return null;
        }
        try {
            HashMap map = new HashMap();
            for (String str2 : str.split(",")) {
                String[] strArrSplit = str2.split(":");
                if (strArrSplit.length != 2) {
                    x.e("error format at %s", str2);
                    return null;
                }
                map.put(strArrSplit[0], Integer.valueOf(Integer.parseInt(strArrSplit[1])));
            }
            return map;
        } catch (Exception e) {
            x.e("error format intStateStr %s", str);
            e.printStackTrace();
            return null;
        }
    }

    protected static String a(String str) {
        if (str == null) {
            return "";
        }
        String[] strArrSplit = str.split("\n");
        if (strArrSplit == null || strArrSplit.length == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : strArrSplit) {
            if (!str2.contains("java.lang.Thread.getStackTrace(")) {
                sb.append(str2).append("\n");
            }
        }
        return sb.toString();
    }

    private static CrashDetailBean a(Context context, Map<String, String> map, NativeExceptionHandler nativeExceptionHandler) {
        String str;
        String str2;
        byte[] bArrA;
        String str3;
        String str4;
        String str5;
        HashMap map2;
        if (map == null) {
            return null;
        }
        if (com.tencent.bugly.crashreport.common.info.a.a(context) != null) {
            String str6 = map.get("intStateStr");
            if (str6 == null || str6.trim().length() <= 0) {
                int i = 0;
                x.e("no intStateStr", new Object[i]);
                return null;
            }
            Map<String, Integer> mapD = d(str6);
            if (mapD == null) {
                x.e("parse intSateMap fail", Integer.valueOf(map.size()));
                return null;
            }
            try {
                mapD.get("sino").intValue();
                mapD.get("sud").intValue();
                String str7 = map.get("soVersion");
                if (!TextUtils.isEmpty(str7)) {
                    String str8 = map.get("errorAddr");
                    String str9 = EnvironmentCompat.MEDIA_UNKNOWN;
                    String str10 = str8 == null ? EnvironmentCompat.MEDIA_UNKNOWN : str8;
                    String str11 = map.get("codeMsg");
                    if (str11 == null) {
                        str11 = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                    String str12 = map.get("tombPath");
                    String str13 = str12 == null ? EnvironmentCompat.MEDIA_UNKNOWN : str12;
                    String str14 = map.get("signalName");
                    if (str14 == null) {
                        str14 = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                    map.get("errnoMsg");
                    String str15 = map.get("stack");
                    if (str15 == null) {
                        str15 = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                    String str16 = map.get("jstack");
                    if (str16 != null) {
                        str15 = str15 + "java:\n" + str16;
                    }
                    Integer num = mapD.get("sico");
                    if (num != null && num.intValue() > 0) {
                        str2 = str14 + "(" + str11 + ")";
                        str = "KERNEL";
                    } else {
                        str = str11;
                        str2 = str14;
                    }
                    String str17 = map.get("nativeLog");
                    if (str17 != null && !str17.isEmpty()) {
                        bArrA = z.a((File) null, str17, "BuglyNativeLog.txt");
                    } else {
                        bArrA = null;
                    }
                    String str18 = map.get("sendingProcess");
                    if (str18 == null) {
                        str18 = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                    Integer num2 = mapD.get("spd");
                    if (num2 != null) {
                        str3 = str18 + "(" + num2 + ")";
                    } else {
                        str3 = str18;
                    }
                    String str19 = map.get("threadName");
                    if (str19 == null) {
                        str19 = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                    Integer num3 = mapD.get("et");
                    if (num3 != null) {
                        str4 = str19 + "(" + num3 + ")";
                    } else {
                        str4 = str19;
                    }
                    String str20 = map.get("processName");
                    if (str20 != null) {
                        str9 = str20;
                    }
                    Integer num4 = mapD.get("ep");
                    if (num4 != null) {
                        str5 = str9 + "(" + num4 + ")";
                    } else {
                        str5 = str9;
                    }
                    String str21 = map.get("key-value");
                    if (str21 == null) {
                        map2 = null;
                    } else {
                        HashMap map3 = new HashMap();
                        for (String str22 : str21.split("\n")) {
                            String[] strArrSplit = str22.split("=");
                            if (strArrSplit.length == 2) {
                                map3.put(strArrSplit[0], strArrSplit[1]);
                            }
                        }
                        map2 = map3;
                    }
                    CrashDetailBean crashDetailBeanPackageCrashDatas = nativeExceptionHandler.packageCrashDatas(str5, str4, (mapD.get("etms").intValue() / 1000) + (mapD.get("ets").intValue() * 1000), str2, str10, a(str15), str, str3, str13, map.get("sysLogPath"), map.get("jniLogPath"), str7, bArrA, map2, false, false);
                    if (crashDetailBeanPackageCrashDatas != null) {
                        String str23 = map.get("userId");
                        if (str23 != null) {
                            x.c("[Native record info] userId: %s", str23);
                            crashDetailBeanPackageCrashDatas.m = str23;
                        }
                        String str24 = map.get("sysLog");
                        if (str24 != null) {
                            crashDetailBeanPackageCrashDatas.w = str24;
                        }
                        String str25 = map.get("appVersion");
                        if (str25 != null) {
                            x.c("[Native record info] appVersion: %s", str25);
                            crashDetailBeanPackageCrashDatas.f = str25;
                        }
                        String str26 = map.get("isAppForeground");
                        if (str26 != null) {
                            x.c("[Native record info] isAppForeground: %s", str26);
                            crashDetailBeanPackageCrashDatas.N = str26.equalsIgnoreCase("true");
                        }
                        String str27 = map.get("launchTime");
                        if (str27 != null) {
                            x.c("[Native record info] launchTime: %s", str27);
                            try {
                                crashDetailBeanPackageCrashDatas.M = Long.parseLong(str27);
                            } catch (NumberFormatException e) {
                                if (!x.a(e)) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        crashDetailBeanPackageCrashDatas.z = null;
                        crashDetailBeanPackageCrashDatas.k = true;
                    }
                    return crashDetailBeanPackageCrashDatas;
                }
                x.e("error format at version", new Object[0]);
                return null;
            } catch (Throwable th) {
                x.e("error format", new Object[0]);
                th.printStackTrace();
                return null;
            }
        }
        x.e("abnormal com info not created", new Object[0]);
        return null;
    }

    private static String a(BufferedInputStream bufferedInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        if (bufferedInputStream == null) {
            return null;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(1024);
            while (true) {
                try {
                    int i = bufferedInputStream.read();
                    if (i == -1) {
                        byteArrayOutputStream.close();
                        break;
                    }
                    if (i == 0) {
                        String str = new String(byteArrayOutputStream.toByteArray(), "UTf-8");
                        byteArrayOutputStream.close();
                        return str;
                    }
                    byteArrayOutputStream.write(i);
                } catch (Throwable th) {
                    th = th;
                    try {
                        x.a(th);
                        return null;
                    } finally {
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [boolean] */
    public static CrashDetailBean a(Context context, String str, NativeExceptionHandler nativeExceptionHandler) throws Throwable {
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        if (context == null || str == null || nativeExceptionHandler == null) {
            x.e("get eup record file args error", new Object[0]);
            return null;
        }
        File file = new File(str, "rqd_record.eup");
        if (file.exists()) {
            ?? CanRead = file.canRead();
            try {
                if (CanRead != 0) {
                    try {
                        bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                    } catch (IOException e) {
                        e = e;
                        bufferedInputStream = null;
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedInputStream2 != null) {
                            try {
                                bufferedInputStream2.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                    try {
                        String strA = a(bufferedInputStream);
                        if (strA != null && strA.equals("NATIVE_RQD_REPORT")) {
                            HashMap map = new HashMap();
                            String str2 = null;
                            while (true) {
                                String strA2 = a(bufferedInputStream);
                                if (strA2 == null) {
                                    break;
                                }
                                if (str2 == null) {
                                    str2 = strA2;
                                } else {
                                    map.put(str2, strA2);
                                    str2 = null;
                                }
                            }
                            if (str2 != null) {
                                x.e("record not pair! drop! %s", str2);
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                                return null;
                            }
                            CrashDetailBean crashDetailBeanA = a(context, map, nativeExceptionHandler);
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                            return crashDetailBeanA;
                        }
                        x.e("record read fail! %s", strA);
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                        return null;
                    } catch (IOException e6) {
                        e = e6;
                        e.printStackTrace();
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e7) {
                                e7.printStackTrace();
                            }
                        }
                        return null;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream2 = CanRead;
            }
        }
        return null;
    }

    private static String b(String str, String str2) throws IOException {
        BufferedReader bufferedReaderA = z.a(str, "reg_record.txt");
        if (bufferedReaderA == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = bufferedReaderA.readLine();
            if (line != null && line.startsWith(str2)) {
                int i = 18;
                int i2 = 0;
                int length = 0;
                while (true) {
                    String line2 = bufferedReaderA.readLine();
                    if (line2 == null) {
                        break;
                    }
                    if (i2 % 4 == 0) {
                        if (i2 > 0) {
                            sb.append("\n");
                        }
                        sb.append("  ");
                    } else {
                        if (line2.length() > 16) {
                            i = 28;
                        }
                        sb.append("                ".substring(0, i - length));
                    }
                    length = line2.length();
                    sb.append(line2);
                    i2++;
                }
                sb.append("\n");
                return sb.toString();
            }
            if (bufferedReaderA != null) {
                try {
                    bufferedReaderA.close();
                } catch (Exception e) {
                    x.a(e);
                }
            }
            return null;
        } catch (Throwable th) {
            try {
                x.a(th);
                if (bufferedReaderA != null) {
                    try {
                        bufferedReaderA.close();
                    } catch (Exception e2) {
                        x.a(e2);
                    }
                }
                return null;
            } finally {
                if (bufferedReaderA != null) {
                    try {
                        bufferedReaderA.close();
                    } catch (Exception e3) {
                        x.a(e3);
                    }
                }
            }
        }
    }

    private static String c(String str, String str2) throws IOException {
        BufferedReader bufferedReaderA = z.a(str, "map_record.txt");
        if (bufferedReaderA == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = bufferedReaderA.readLine();
            if (line != null && line.startsWith(str2)) {
                while (true) {
                    String line2 = bufferedReaderA.readLine();
                    if (line2 == null) {
                        break;
                    }
                    sb.append("  ");
                    sb.append(line2);
                    sb.append("\n");
                }
                return sb.toString();
            }
            if (bufferedReaderA != null) {
                try {
                    bufferedReaderA.close();
                } catch (Exception e) {
                    x.a(e);
                }
            }
            return null;
        } catch (Throwable th) {
            try {
                x.a(th);
                if (bufferedReaderA != null) {
                    try {
                        bufferedReaderA.close();
                    } catch (Exception e2) {
                        x.a(e2);
                    }
                }
                return null;
            } finally {
                if (bufferedReaderA != null) {
                    try {
                        bufferedReaderA.close();
                    } catch (Exception e3) {
                        x.a(e3);
                    }
                }
            }
        }
    }

    public static String a(String str, String str2) throws IOException {
        if (str == null || str2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String strB = b(str, str2);
        if (strB != null && !strB.isEmpty()) {
            sb.append("Register infos:\n");
            sb.append(strB);
        }
        String strC = c(str, str2);
        if (strC != null && !strC.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append("System SO infos:\n");
            sb.append(strC);
        }
        return sb.toString();
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str, "backup_record.txt");
        if (!file.exists()) {
            return null;
        }
        return file.getAbsolutePath();
    }

    public static void c(String str) {
        File[] fileArrListFiles;
        if (str == null) {
            return;
        }
        try {
            File file = new File(str);
            if (file.canRead() && file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.canRead() && file2.canWrite() && file2.length() == 0) {
                        file2.delete();
                        x.c("Delete empty record file %s", file2.getAbsoluteFile());
                    }
                }
            }
        } catch (Throwable th) {
            x.a(th);
        }
    }

    public static void a(boolean z, String str) {
        if (str != null) {
            a.add(new File(str, "rqd_record.eup"));
            a.add(new File(str, "reg_record.txt"));
            a.add(new File(str, "map_record.txt"));
            a.add(new File(str, "backup_record.txt"));
            if (z) {
                c(str);
            }
        }
        if (a != null && a.size() > 0) {
            for (File file : a) {
                if (file.exists() && file.canWrite()) {
                    file.delete();
                    x.c("Delete record file %s", file.getAbsoluteFile());
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.lang.String] */
    public static String a(String str, int i, String str2, boolean z) throws IOException {
        BufferedReader bufferedReader = null;
        if (str == null || i <= 0) {
            return null;
        }
        File file = new File(str);
        if (!file.exists() || !file.canRead()) {
            return null;
        }
        x.a("Read system log from native record file(length: %s bytes): %s", Long.valueOf(file.length()), file.getAbsolutePath());
        a.add(file);
        x.c("Add this record file to list for cleaning lastly.", new Object[0]);
        if (str2 == null) {
            return z.a(new File(str), i, z);
        }
        String sb = new StringBuilder();
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                while (true) {
                    try {
                        String line = bufferedReader2.readLine();
                        if (line == null) {
                            break;
                        }
                        if (Pattern.compile(str2 + "[ ]*:").matcher(line).find()) {
                            sb.append(line).append("\n");
                        }
                        if (i > 0 && sb.length() > i) {
                            if (z) {
                                sb.delete(i, sb.length());
                                break;
                            }
                            sb.delete(0, sb.length() - i);
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        try {
                            x.a(th);
                            String string = sb.append("\n[error:" + th.toString() + "]").toString();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                                sb = string;
                                return sb;
                            }
                            return string;
                        } catch (Throwable th2) {
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception e) {
                                    x.a(e);
                                }
                            }
                            throw th2;
                        }
                    }
                }
                String string2 = sb.toString();
                bufferedReader2.close();
                sb = string2;
            } catch (Throwable th3) {
                th = th3;
            }
            return sb;
        } catch (Exception e2) {
            x.a(e2);
            return sb;
        }
    }
}