package com.tencent.bugly.crashreport.common.info;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import androidx.core.os.EnvironmentCompat;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public class b {
    private static final String[] a = {"/su", "/su/bin/su", "/sbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su", "/system/xbin/su", "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr", "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd", "/system/bin/conbb", "/system/xbin/conbb"};
    private static final String[] b = {"com.ami.duosupdater.ui", "com.ami.launchmetro", "com.ami.syncduosservices", "com.bluestacks.home", "com.bluestacks.windowsfilemanager", "com.bluestacks.settings", "com.bluestacks.bluestackslocationprovider", "com.bluestacks.appsettings", "com.bluestacks.bstfolder", "com.bluestacks.BstCommandProcessor", "com.bluestacks.s2p", "com.bluestacks.setup", "com.kaopu001.tiantianserver", "com.kpzs.helpercenter", "com.kaopu001.tiantianime", "com.android.development_settings", "com.android.development", "com.android.customlocale2", "com.genymotion.superuser", "com.genymotion.clipboardproxy", "com.uc.xxzs.keyboard", "com.uc.xxzs", "com.blue.huang17.agent", "com.blue.huang17.launcher", "com.blue.huang17.ime", "com.microvirt.guide", "com.microvirt.market", "com.microvirt.memuime", "cn.itools.vm.launcher", "cn.itools.vm.proxy", "cn.itools.vm.softkeyboard", "cn.itools.avdmarket", "com.syd.IME", "com.bignox.app.store.hd", "com.bignox.launcher", "com.bignox.app.phone", "com.bignox.app.noxservice", "com.android.noxpush", "com.haimawan.push", "me.haima.helpcenter", "com.windroy.launcher", "com.windroy.superuser", "com.windroy.launcher", "com.windroy.ime", "com.android.flysilkworm", "com.android.emu.inputservice", "com.tiantian.ime", "com.microvirt.launcher", "me.le8.androidassist", "com.vphone.helper", "com.vphone.launcher", "com.duoyi.giftcenter.giftcenter"};
    private static final String[] c = {"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/qemud", "/dev/qemu_pipe", "/dev/socket/baseband_genyd", "/dev/socket/genyd"};
    private static String d = null;
    private static String e = null;

    public static String a() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
                return "fail";
            }
            return "fail";
        }
    }

    public static String b() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
                return "fail";
            }
            return "fail";
        }
    }

    public static int c() {
        try {
            return Build.VERSION.SDK_INT;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
                return -1;
            }
            return -1;
        }
    }

    public static String d() {
        return "null";
    }

    public static String e() {
        return "null";
    }

    public static String a(Context context) {
        String string = "fail";
        if (context == null) {
            return "fail";
        }
        try {
            string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            if (string == null) {
                return "null";
            }
            return string.toLowerCase();
        } catch (Throwable th) {
            if (!x.a(th)) {
                x.a("Failed to get Android ID.", new Object[0]);
            }
            return string;
        }
    }

    public static String f() {
        return "null";
    }

    public static String b(Context context) {
        String simSerialNumber = "fail";
        if (context == null) {
            return "fail";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return "fail";
            }
            simSerialNumber = telephonyManager.getSimSerialNumber();
            if (simSerialNumber == null) {
                return "null";
            }
            return simSerialNumber;
        } catch (Throwable th) {
            x.a("Failed to get SIM serial number.", new Object[0]);
            return simSerialNumber;
        }
    }

    public static String g() {
        try {
            return Build.SERIAL;
        } catch (Throwable th) {
            x.a("Failed to get hardware serial number.", new Object[0]);
            return "fail";
        }
    }

    private static boolean u() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
                return false;
            }
            return false;
        }
    }

    public static String a(Context context, boolean z) {
        String property = null;
        if (z) {
            try {
                String strA = z.a(context, "ro.product.cpu.abilist");
                if (z.a(strA) || strA.equals("fail")) {
                    strA = z.a(context, "ro.product.cpu.abi");
                }
                if (!z.a(strA) && !strA.equals("fail")) {
                    x.b(b.class, "ABI list: " + strA, new Object[0]);
                    property = strA.split(",")[0];
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                return "fail";
            }
        }
        if (property == null) {
            property = System.getProperty("os.arch");
        }
        return property;
    }

    public static long h() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return statFs.getBlockCount() * statFs.getBlockSize();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -1L;
        }
    }

    public static long i() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return statFs.getAvailableBlocks() * statFs.getBlockSize();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -1L;
        }
    }

    public static long j() throws IOException {
        FileReader fileReader;
        Throwable th;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader, 2048);
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
            }
        } catch (Throwable th3) {
            fileReader = null;
            th = th3;
            bufferedReader = null;
        }
        try {
            String line = bufferedReader.readLine();
            if (line != null) {
                long j = Long.parseLong(line.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) << 10;
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    if (!x.a(e2)) {
                        e2.printStackTrace();
                    }
                }
                try {
                    fileReader.close();
                } catch (IOException e3) {
                    if (!x.a(e3)) {
                        e3.printStackTrace();
                    }
                }
                return j;
            }
            try {
                bufferedReader.close();
            } catch (IOException e4) {
                if (!x.a(e4)) {
                    e4.printStackTrace();
                }
            }
            try {
                fileReader.close();
                return -1L;
            } catch (IOException e5) {
                if (x.a(e5)) {
                    return -1L;
                }
                e5.printStackTrace();
                return -1L;
            }
        } catch (Throwable th4) {
            th = th4;
            try {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e6) {
                        if (!x.a(e6)) {
                            e6.printStackTrace();
                        }
                    }
                }
                if (fileReader == null) {
                    return -2L;
                }
                try {
                    fileReader.close();
                    return -2L;
                } catch (IOException e7) {
                    if (x.a(e7)) {
                        return -2L;
                    }
                    e7.printStackTrace();
                    return -2L;
                }
            } catch (Throwable th5) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e8) {
                        if (!x.a(e8)) {
                            e8.printStackTrace();
                        }
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e9) {
                        if (!x.a(e9)) {
                            e9.printStackTrace();
                        }
                    }
                }
                throw th5;
            }
        }
    }

    public static long k() throws IOException {
        FileReader fileReader;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            fileReader = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader, 2048);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileReader = null;
        }
        try {
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            if (line == null) {
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    if (!x.a(e2)) {
                        e2.printStackTrace();
                    }
                }
                try {
                    fileReader.close();
                } catch (IOException e3) {
                    if (!x.a(e3)) {
                        e3.printStackTrace();
                    }
                }
                return -1L;
            }
            long j = (Long.parseLong(line.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) << 10) + 0;
            String line2 = bufferedReader.readLine();
            if (line2 == null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    if (!x.a(e4)) {
                        e4.printStackTrace();
                    }
                }
                try {
                    fileReader.close();
                } catch (IOException e5) {
                    if (!x.a(e5)) {
                        e5.printStackTrace();
                    }
                }
                return -1L;
            }
            long j2 = j + (Long.parseLong(line2.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) << 10);
            String line3 = bufferedReader.readLine();
            if (line3 == null) {
                try {
                    bufferedReader.close();
                } catch (IOException e6) {
                    if (!x.a(e6)) {
                        e6.printStackTrace();
                    }
                }
                try {
                    fileReader.close();
                } catch (IOException e7) {
                    if (!x.a(e7)) {
                        e7.printStackTrace();
                    }
                }
                return -1L;
            }
            long j3 = j2 + (Long.parseLong(line3.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) << 10);
            try {
                bufferedReader.close();
            } catch (IOException e8) {
                if (!x.a(e8)) {
                    e8.printStackTrace();
                }
            }
            try {
                fileReader.close();
            } catch (IOException e9) {
                if (!x.a(e9)) {
                    e9.printStackTrace();
                }
            }
            return j3;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader2 = bufferedReader;
            try {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e10) {
                        if (!x.a(e10)) {
                            e10.printStackTrace();
                        }
                    }
                }
                if (fileReader == null) {
                    return -2L;
                }
                try {
                    fileReader.close();
                    return -2L;
                } catch (IOException e11) {
                    if (x.a(e11)) {
                        return -2L;
                    }
                    e11.printStackTrace();
                    return -2L;
                }
            } catch (Throwable th4) {
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e12) {
                        if (!x.a(e12)) {
                            e12.printStackTrace();
                        }
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e13) {
                        if (!x.a(e13)) {
                            e13.printStackTrace();
                        }
                    }
                }
                throw th4;
            }
        }
    }

    public static long l() {
        if (!u()) {
            return 0L;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return statFs.getBlockCount() * statFs.getBlockSize();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
                return -2L;
            }
            return -2L;
        }
    }

    public static long m() {
        if (!u()) {
            return 0L;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return statFs.getAvailableBlocks() * statFs.getBlockSize();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
                return -2L;
            }
            return -2L;
        }
    }

    public static String n() {
        try {
            return Locale.getDefault().getCountry();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static String o() {
        try {
            return Build.BRAND;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String c(Context context) {
        NetworkInfo activeNetworkInfo;
        TelephonyManager telephonyManager;
        try {
            activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e2) {
            if (!x.a(e2)) {
            }
        }
        if (activeNetworkInfo == null) {
            return null;
        }
        if (activeNetworkInfo.getType() == 1) {
            return "WIFI";
        }
        if (activeNetworkInfo.getType() != 0 || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "EVDO_0";
            case 6:
                return "EVDO_A";
            case 7:
                return "1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "iDen";
            case 12:
                return "EVDO_B";
            case 13:
                return "LTE";
            case 14:
                return "eHRPD";
            case 15:
                return "HSPA+";
            default:
                return "MOBILE(" + networkType + ")";
        }
        if (!x.a(e2)) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        e2.printStackTrace();
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    public static String d(Context context) throws IOException {
        String strA = z.a(context, "ro.miui.ui.version.name");
        if (!z.a(strA) && !strA.equals("fail")) {
            return "XiaoMi/MIUI/" + strA;
        }
        String strA2 = z.a(context, "ro.build.version.emui");
        if (!z.a(strA2) && !strA2.equals("fail")) {
            return "HuaWei/EMOTION/" + strA2;
        }
        String strA3 = z.a(context, "ro.lenovo.series");
        if (!z.a(strA3) && !strA3.equals("fail")) {
            return "Lenovo/VIBE/" + z.a(context, "ro.build.version.incremental");
        }
        String strA4 = z.a(context, "ro.build.nubia.rom.name");
        if (!z.a(strA4) && !strA4.equals("fail")) {
            return "Zte/NUBIA/" + strA4 + "_" + z.a(context, "ro.build.nubia.rom.code");
        }
        String strA5 = z.a(context, "ro.meizu.product.model");
        if (!z.a(strA5) && !strA5.equals("fail")) {
            return "Meizu/FLYME/" + z.a(context, "ro.build.display.id");
        }
        String strA6 = z.a(context, "ro.build.version.opporom");
        if (!z.a(strA6) && !strA6.equals("fail")) {
            return "Oppo/COLOROS/" + strA6;
        }
        String strA7 = z.a(context, "ro.vivo.os.build.display.id");
        if (!z.a(strA7) && !strA7.equals("fail")) {
            return "vivo/FUNTOUCH/" + strA7;
        }
        String strA8 = z.a(context, "ro.aa.romver");
        if (!z.a(strA8) && !strA8.equals("fail")) {
            return "htc/" + strA8 + "/" + z.a(context, "ro.build.description");
        }
        String strA9 = z.a(context, "ro.lewa.version");
        if (!z.a(strA9) && !strA9.equals("fail")) {
            return "tcl/" + strA9 + "/" + z.a(context, "ro.build.display.id");
        }
        String strA10 = z.a(context, "ro.gn.gnromvernumber");
        if (!z.a(strA10) && !strA10.equals("fail")) {
            return "amigo/" + strA10 + "/" + z.a(context, "ro.build.display.id");
        }
        String strA11 = z.a(context, "ro.build.tyd.kbstyle_version");
        if (!z.a(strA11) && !strA11.equals("fail")) {
            return "dido/" + strA11;
        }
        return z.a(context, "ro.build.fingerprint") + "/" + z.a(context, "ro.build.rom.id");
    }

    public static String e(Context context) {
        return z.a(context, "ro.board.platform");
    }

    public static boolean p() {
        boolean z;
        String[] strArr = a;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (!new File(strArr[i]).exists()) {
                i++;
            } else {
                z = true;
                break;
            }
        }
        return (Build.TAGS != null && Build.TAGS.contains("test-keys")) || z;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0068 A[Catch: all -> 0x008f, TRY_LEAVE, TryCatch #5 {all -> 0x008f, blocks: (B:6:0x0023, B:8:0x0029, B:9:0x002c, B:11:0x0031, B:13:0x003f, B:21:0x005a, B:23:0x0068, B:30:0x0080), top: B:57:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String q() throws IOException {
        BufferedReader bufferedReader;
        try {
            StringBuilder sb = new StringBuilder();
            if (new File("/sys/block/mmcblk0/device/type").exists()) {
                bufferedReader = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/type"));
                try {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        sb.append(line);
                    }
                    bufferedReader.close();
                } catch (Throwable th) {
                }
            } else {
                bufferedReader = null;
            }
            sb.append(",");
            if (!new File("/sys/block/mmcblk0/device/name").exists()) {
                sb.append(",");
                if (new File("/sys/block/mmcblk0/device/cid").exists()) {
                }
                String string = sb.toString();
                if (bufferedReader != null) {
                }
                return string;
            }
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/name"));
            try {
                String line2 = bufferedReader2.readLine();
                if (line2 != null) {
                    sb.append(line2);
                }
                bufferedReader2.close();
                bufferedReader = bufferedReader2;
                sb.append(",");
                if (new File("/sys/block/mmcblk0/device/cid").exists()) {
                    BufferedReader bufferedReader3 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/cid"));
                    try {
                        String line3 = bufferedReader3.readLine();
                        if (line3 != null) {
                            sb.append(line3);
                        }
                        bufferedReader = bufferedReader3;
                    } catch (Throwable th2) {
                        bufferedReader = bufferedReader3;
                    }
                }
                String string2 = sb.toString();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e2) {
                        x.a(e2);
                    }
                }
                return string2;
            } catch (Throwable th3) {
                bufferedReader = bufferedReader2;
            }
        } catch (Throwable th4) {
            bufferedReader = null;
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e3) {
                x.a(e3);
            }
        }
        return null;
    }

    public static String f(Context context) throws IOException {
        StringBuilder sb = new StringBuilder();
        String strA = z.a(context, "ro.genymotion.version");
        if (strA != null) {
            sb.append("ro.genymotion.version");
            sb.append("|");
            sb.append(strA);
            sb.append("\n");
        }
        String strA2 = z.a(context, "androVM.vbox_dpi");
        if (strA2 != null) {
            sb.append("androVM.vbox_dpi");
            sb.append("|");
            sb.append(strA2);
            sb.append("\n");
        }
        String strA3 = z.a(context, "qemu.sf.fake_camera");
        if (strA3 != null) {
            sb.append("qemu.sf.fake_camera");
            sb.append("|");
            sb.append(strA3);
        }
        return sb.toString();
    }

    public static String g(Context context) throws IOException {
        BufferedReader bufferedReader;
        Throwable th;
        String line;
        StringBuilder sb = new StringBuilder();
        if (d == null) {
            d = z.a(context, "ro.secure");
        }
        if (d != null) {
            sb.append("ro.secure");
            sb.append("|");
            sb.append(d);
            sb.append("\n");
        }
        if (e == null) {
            e = z.a(context, "ro.debuggable");
        }
        if (e != null) {
            sb.append("ro.debuggable");
            sb.append("|");
            sb.append(e);
            sb.append("\n");
        }
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/self/status"));
            do {
                try {
                    line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        x.a(th);
                        return sb.toString();
                    } finally {
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e2) {
                                x.a(e2);
                            }
                        }
                    }
                }
            } while (!line.startsWith("TracerPid:"));
            if (line != null) {
                String strTrim = line.substring(10).trim();
                sb.append("tracer_pid");
                sb.append("|");
                sb.append(strTrim);
            }
            String string = sb.toString();
            try {
                bufferedReader.close();
            } catch (IOException e3) {
                x.a(e3);
            }
            return string;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004e A[Catch: all -> 0x00a8, TRY_LEAVE, TryCatch #0 {all -> 0x00a8, blocks: (B:3:0x000f, B:6:0x001c, B:14:0x0040, B:16:0x004e, B:24:0x0071, B:26:0x007f), top: B:44:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0071 A[Catch: all -> 0x00a8, PHI: r5
  0x0071: PHI (r5v4 java.io.BufferedReader) = (r5v3 java.io.BufferedReader), (r5v9 java.io.BufferedReader) binds: [B:15:0x004c, B:21:0x006c] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #0 {all -> 0x00a8, blocks: (B:3:0x000f, B:6:0x001c, B:14:0x0040, B:16:0x004e, B:24:0x0071, B:26:0x007f), top: B:44:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007f A[Catch: all -> 0x00a8, TRY_LEAVE, TryCatch #0 {all -> 0x00a8, blocks: (B:3:0x000f, B:6:0x001c, B:14:0x0040, B:16:0x004e, B:24:0x0071, B:26:0x007f), top: B:44:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a4 A[Catch: IOException -> 0x00af, TRY_ENTER, TryCatch #1 {IOException -> 0x00af, blocks: (B:38:0x00ab, B:35:0x00a4), top: B:47:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ab A[Catch: IOException -> 0x00af, TRY_LEAVE, TryCatch #1 {IOException -> 0x00af, blocks: (B:38:0x00ab, B:35:0x00a4), top: B:47:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String r() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            if (new File("/sys/class/power_supply/ac/online").exists()) {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/sys/class/power_supply/ac/online"));
                try {
                    String line = bufferedReader2.readLine();
                    if (line != null) {
                        sb.append("ac_online");
                        sb.append("|");
                        sb.append(line);
                    }
                    bufferedReader2.close();
                    bufferedReader = bufferedReader2;
                    sb.append("\n");
                } catch (Throwable th) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    return sb.toString();
                }
                if (new File("/sys/class/power_supply/usb/online").exists()) {
                    sb.append("\n");
                    if (new File("/sys/class/power_supply/battery/capacity").exists()) {
                    }
                    if (bufferedReader != null) {
                    }
                } else {
                    BufferedReader bufferedReader3 = new BufferedReader(new FileReader("/sys/class/power_supply/usb/online"));
                    try {
                        String line2 = bufferedReader3.readLine();
                        if (line2 != null) {
                            sb.append("usb_online");
                            sb.append("|");
                            sb.append(line2);
                        }
                        bufferedReader3.close();
                        bufferedReader = bufferedReader3;
                        sb.append("\n");
                        if (new File("/sys/class/power_supply/battery/capacity").exists()) {
                            BufferedReader bufferedReader4 = new BufferedReader(new FileReader("/sys/class/power_supply/battery/capacity"));
                            try {
                                String line3 = bufferedReader4.readLine();
                                if (line3 != null) {
                                    sb.append("battery_capacity");
                                    sb.append("|");
                                    sb.append(line3);
                                }
                                bufferedReader4.close();
                                bufferedReader = bufferedReader4;
                            } catch (Throwable th2) {
                                bufferedReader = bufferedReader4;
                                if (bufferedReader != null) {
                                }
                                return sb.toString();
                            }
                        }
                    } catch (Throwable th3) {
                        bufferedReader = bufferedReader3;
                        if (bufferedReader != null) {
                        }
                        return sb.toString();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                }
            } else {
                sb.append("\n");
                if (new File("/sys/class/power_supply/usb/online").exists()) {
                }
            }
        } catch (IOException e2) {
            x.a(e2);
        }
        return sb.toString();
    }

    public static String h(Context context) throws IOException {
        StringBuilder sb = new StringBuilder();
        String strA = z.a(context, "gsm.sim.state");
        if (strA != null) {
            sb.append("gsm.sim.state");
            sb.append("|");
            sb.append(strA);
        }
        sb.append("\n");
        String strA2 = z.a(context, "gsm.sim.state2");
        if (strA2 != null) {
            sb.append("gsm.sim.state2");
            sb.append("|");
            sb.append(strA2);
        }
        return sb.toString();
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0032 -> B:32:0x0047). Please report as a decompilation issue!!! */
    public static long s() throws IOException {
        BufferedReader bufferedReader;
        float fCurrentTimeMillis = 0.0f;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/proc/uptime"));
            } catch (Throwable th) {
            }
        } catch (IOException e2) {
            x.a(e2);
        }
        try {
            String line = bufferedReader.readLine();
            if (line != null) {
                fCurrentTimeMillis = (System.currentTimeMillis() / 1000) - Float.parseFloat(line.split(" ")[0]);
            }
            bufferedReader.close();
        } catch (Throwable th2) {
            bufferedReader2 = bufferedReader;
            try {
                x.a("Failed to get boot time of device.", new Object[0]);
                if (bufferedReader2 != null) {
                    bufferedReader2.close();
                }
                return (long) fCurrentTimeMillis;
            } catch (Throwable th3) {
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e3) {
                        x.a(e3);
                    }
                }
                throw th3;
            }
        }
        return (long) fCurrentTimeMillis;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Duplicate predecessors in PHI insn: B:26:0x0036, 0x0036: PHI (r2v2 ?? I:java.io.File) = (r2v6 ?? I:java.io.File) binds: [B:26:0x0036] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.dex.instructions.PhiInsn.bindArg(PhiInsn.java:44)
        	at jadx.core.dex.visitors.ConstructorVisitor.insertPhiInsn(ConstructorVisitor.java:157)
        	at jadx.core.dex.visitors.ConstructorVisitor.processInvoke(ConstructorVisitor.java:91)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:56)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    public static boolean i(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Duplicate predecessors in PHI insn: B:26:0x0036, 0x0036: PHI (r2v2 ?? I:java.io.File) = (r2v6 ?? I:java.io.File) binds: [B:26:0x0036] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.dex.instructions.PhiInsn.bindArg(PhiInsn.java:44)
        	at jadx.core.dex.visitors.ConstructorVisitor.insertPhiInsn(ConstructorVisitor.java:157)
        	at jadx.core.dex.visitors.ConstructorVisitor.processInvoke(ConstructorVisitor.java:91)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:56)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r4v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:224)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:169)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:405)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(Unknown Source)
        	at java.base/java.util.stream.AbstractPipeline.copyInto(Unknown Source)
        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(Unknown Source)
        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(Unknown Source)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(Unknown Source)
        	at java.base/java.util.stream.AbstractPipeline.evaluate(Unknown Source)
        	at java.base/java.util.stream.ReferencePipeline.forEach(Unknown Source)
        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:103)
        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
        	at jadx.core.ProcessClass.process(ProcessClass.java:79)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:309)
        */

    private static String k(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < b.length; i++) {
            try {
                packageManager.getPackageInfo(b[i], 1);
                arrayList.add(Integer.valueOf(i));
            } catch (PackageManager.NameNotFoundException e2) {
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList.toString();
    }

    public static boolean j(Context context) {
        return (((l(context) | w()) | x()) | v()) > 0;
    }

    private static int v() throws NoSuchMethodException, SecurityException {
        try {
            Method method = Class.forName("android.app.ActivityManagerNative").getMethod("getDefault", new Class[0]);
            method.setAccessible(true);
            if (method.invoke(null, new Object[0]).getClass().getName().startsWith("$Proxy")) {
                return 256;
            }
            return 0;
        } catch (Exception e2) {
            return 256;
        }
    }

    private static int l(Context context) {
        int i;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getInstallerPackageName("de.robv.android.xposed.installer");
            i = 1;
        } catch (Exception e2) {
            i = 0;
        }
        try {
            packageManager.getInstallerPackageName("com.saurik.substrate");
            return i | 2;
        } catch (Exception e3) {
            return i;
        }
    }

    private static int w() throws Exception {
        try {
            throw new Exception("detect hook");
        } catch (Exception e2) {
            int i = 0;
            int i2 = 0;
            for (StackTraceElement stackTraceElement : e2.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("main")) {
                    i |= 4;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    i |= 8;
                }
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") && stackTraceElement.getMethodName().equals("invoked")) {
                    i |= 16;
                }
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit") && (i2 = i2 + 1) == 2) {
                    i |= 32;
                }
            }
            return i;
        }
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x00bc: MOVE (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:53:0x00bc */
    private static int x() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        IOException e2;
        UnsupportedEncodingException e3;
        FileNotFoundException e4;
        int i = 0;
        BufferedReader bufferedReader3 = null;
        try {
            try {
                try {
                    HashSet hashSet = new HashSet();
                    bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + Process.myPid() + "/maps"), "utf-8"));
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            }
                            if (line.endsWith(".so") || line.endsWith(".jar")) {
                                hashSet.add(line.substring(line.lastIndexOf(" ") + 1));
                            }
                        } catch (FileNotFoundException e5) {
                            e4 = e5;
                            e4.printStackTrace();
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return i;
                        } catch (UnsupportedEncodingException e6) {
                            e3 = e6;
                            e3.printStackTrace();
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return i;
                        } catch (IOException e7) {
                            e2 = e7;
                            e2.printStackTrace();
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return i;
                        }
                    }
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        Object next = it.next();
                        if (((String) next).toLowerCase().contains("xposed")) {
                            i |= 64;
                        }
                        if (((String) next).contains("com.saurik.substrate")) {
                            i |= 128;
                        }
                    }
                    bufferedReader2.close();
                } catch (FileNotFoundException e8) {
                    bufferedReader2 = null;
                    e4 = e8;
                } catch (UnsupportedEncodingException e9) {
                    bufferedReader2 = null;
                    e3 = e9;
                } catch (IOException e10) {
                    bufferedReader2 = null;
                    e2 = e10;
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader3 != null) {
                        try {
                            bufferedReader3.close();
                        } catch (IOException e11) {
                            e11.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e12) {
                e12.printStackTrace();
            }
            return i;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader3 = bufferedReader;
        }
    }

    public static boolean t() {
        double dMaxMemory = Runtime.getRuntime().maxMemory();
        Double.isNaN(dMaxMemory);
        float f = (float) (dMaxMemory / 1048576.0d);
        double d2 = Runtime.getRuntime().totalMemory();
        Double.isNaN(d2);
        float f2 = (float) (d2 / 1048576.0d);
        float f3 = f - f2;
        x.c("maxMemory : %f", Float.valueOf(f));
        x.c("totalMemory : %f", Float.valueOf(f2));
        x.c("freeMemory : %f", Float.valueOf(f3));
        return f3 < 10.0f;
    }
}