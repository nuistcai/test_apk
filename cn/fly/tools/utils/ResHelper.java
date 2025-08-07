package cn.fly.tools.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.a.l;
import cn.fly.commons.y;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.tools.utils.DH;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes.dex */
public class ResHelper implements PublicMemberKeeper {
    private static float a;
    private static int b;
    private static Uri c;

    public static int getLayoutRes(Context context, String str) {
        return getResId(context, l.a("006he;fdelehLj"), str);
    }

    public static int getIdRes(Context context, String str) {
        return getResId(context, l.a("002^ejed"), str);
    }

    public static int getStringRes(Context context, String str) {
        return getResId(context, "string", str);
    }

    public static int getStringArrayRes(Context context, String str) {
        return getResId(context, "array", str);
    }

    public static int getBitmapRes(Context context, String str) {
        int resId = getResId(context, "drawable", str);
        if (resId <= 0) {
            return getResId(context, "mipmap", str);
        }
        return resId;
    }

    public static int getStyleRes(Context context, String str) {
        return getResId(context, "style", str);
    }

    public static int getColorRes(Context context, String str) {
        return getResId(context, "color", str);
    }

    public static int getRawRes(Context context, String str) {
        return getResId(context, "raw", str);
    }

    public static int getAnimRes(Context context, String str) {
        return getResId(context, "anim", str);
    }

    public static int[] getStyleableRes(Context context, String str) {
        try {
            Object staticField = ReflectHelper.getStaticField(ReflectHelper.importClass(context.getPackageName() + ".R$styleable"), str);
            if (staticField == null) {
                return new int[0];
            }
            if (staticField.getClass().isArray()) {
                return (int[]) staticField;
            }
            return new int[]{((Integer) staticField).intValue()};
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return new int[0];
        }
    }

    public static int getResId(Context context, String str, String str2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return 0;
        }
        String packageName = context.getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            return 0;
        }
        int identifier = context.getResources().getIdentifier(str2, str, packageName);
        if (identifier <= 0) {
            identifier = context.getResources().getIdentifier(str2.toLowerCase(), str, packageName);
        }
        if (identifier <= 0) {
            FlyLog.getInstance().w("failed to parse " + str + " resource \"" + str2 + "\"");
        }
        return identifier;
    }

    public static int designToDevice(Context context, int i, int i2) {
        if (b == 0) {
            int[] screenSize = getScreenSize(context);
            b = screenSize[0] < screenSize[1] ? screenSize[0] : screenSize[1];
        }
        return (int) (((i2 * b) / i) + 0.5f);
    }

    public static int designToDevice(Context context, float f, int i) {
        if (a <= 0.0f) {
            a = context.getResources().getDisplayMetrics().density;
        }
        return (int) (((i * a) / f) + 0.5f);
    }

    public static int dipToPx(Context context, int i) {
        if (a <= 0.0f) {
            a = context.getResources().getDisplayMetrics().density;
        }
        return (int) ((i * a) + 0.5f);
    }

    public static int pxToDip(Context context, int i) {
        if (a <= 0.0f) {
            a = context.getResources().getDisplayMetrics().density;
        }
        return (int) ((i / a) + 0.5f);
    }

    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static float[] getDensityXYDpi(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new float[]{displayMetrics.xdpi, displayMetrics.ydpi};
    }

    public static float getDensity(Context context) {
        if (a <= 0.0f) {
            a = context.getResources().getDisplayMetrics().density;
        }
        return a;
    }

    public static int[] getScreenSize(Context context) {
        WindowManager windowManager;
        Display defaultDisplay = null;
        try {
            windowManager = (WindowManager) DH.SyncMtd.getSystemServiceSafe("window");
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            windowManager = null;
        }
        if (windowManager == null) {
            return new int[]{0, 0};
        }
        try {
            defaultDisplay = windowManager.getDefaultDisplay();
        } catch (Throwable th2) {
            FlyLog.getInstance().w(th2);
        }
        if (defaultDisplay == null) {
            try {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
            } catch (Throwable th3) {
                FlyLog.getInstance().w(th3);
                return new int[]{0, 0};
            }
        }
        try {
            Point point = new Point();
            Method method = defaultDisplay.getClass().getMethod(l.a("011+fk9gj6hkIgeh$fmejheKg"), Point.class);
            method.setAccessible(true);
            method.invoke(defaultDisplay, point);
            return new int[]{point.x, point.y};
        } catch (Throwable th4) {
            FlyLog.getInstance().w(th4);
            return new int[]{0, 0};
        }
    }

    public static int getScreenWidth(Context context) {
        return getScreenSize(context)[0];
    }

    public static int getScreenHeight(Context context) {
        return getScreenSize(context)[1];
    }

    public static double getScreenInch(Context context) {
        try {
            int screenWidth = getScreenWidth(context);
            int screenHeight = getScreenHeight(context);
            float[] densityXYDpi = getDensityXYDpi(context);
            if (densityXYDpi == null || densityXYDpi.length != 2) {
                return 0.0d;
            }
            double d = screenWidth / densityXYDpi[0];
            double d2 = screenHeight / densityXYDpi[1];
            Double.isNaN(d);
            Double.isNaN(d);
            Double.isNaN(d2);
            Double.isNaN(d2);
            return new BigDecimal(Math.sqrt((d * d) + (d2 * d2))).setScale(1, 4).doubleValue();
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return 0.0d;
        }
    }

    public static int getScreenPpi(Context context) {
        try {
            int screenWidth = getScreenWidth(context);
            int screenHeight = getScreenHeight(context);
            return (int) Math.round(Math.sqrt((screenWidth * screenWidth) + (screenHeight * screenHeight)) / getScreenInch(context));
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return 0;
        }
    }

    public static File getCacheRootFile(Context context, String str) {
        try {
            String cacheRoot = getCacheRoot(context);
            if (cacheRoot != null) {
                File file = new File(cacheRoot, str);
                if (!file.getParentFile().exists() || !file.getParentFile().isDirectory()) {
                    file.getParentFile().delete();
                    file.getParentFile().mkdirs();
                }
                return file;
            }
            return null;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    public static String getCacheRoot(Context context) {
        return getCacheRoot(context, false);
    }

    public static String getCacheRoot(Context context, boolean z) {
        String dataCache;
        if (z) {
            dataCache = null;
        } else {
            try {
                dataCache = getDataCache(context);
            } catch (Throwable th) {
                FlyLog.getInstance().w(th);
                return null;
            }
        }
        String sandboxPath = DH.SyncMtd.getSandboxPath();
        if (sandboxPath != null) {
            dataCache = sandboxPath + l.a("001m") + "fvv";
        }
        if (TextUtils.isEmpty(dataCache)) {
            return null;
        }
        File file = new File(dataCache);
        if (!file.exists() || !file.isDirectory()) {
            file.delete();
            file.mkdirs();
        }
        return dataCache;
    }

    public static String getDataCache(Context context) {
        String str = context.getFilesDir().getAbsolutePath() + l.a("001m") + "fvv";
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            file.delete();
            file.mkdirs();
        }
        return str;
    }

    public static File getDataCacheFile(Context context, String str) {
        return getDataCacheFile(context, str, false);
    }

    public static File getDataCacheFile(Context context, String str, boolean z) {
        File file = new File(getDataCache(context), str);
        if (z && !file.exists()) {
            try {
                File parentFile = file.getParentFile();
                if (parentFile != null && !parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return file;
    }

    public static String getCachePath(Context context, String str) {
        String str2 = context.getFilesDir().getAbsolutePath() + l.a("001m") + "fvv" + l.a("007mdedigm");
        try {
            String sandboxPath = DH.SyncMtd.getSandboxPath();
            if (sandboxPath != null) {
                str2 = sandboxPath + l.a("001m") + "fvv" + l.a("001m") + DH.SyncMtd.getPackageName() + l.a("007mdedigm");
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        if (!TextUtils.isEmpty(str)) {
            str2 = str2 + str + l.a("001m");
        }
        File file = new File(str2);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return str2;
    }

    public static void deleteFileAndFolder(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        String[] list = file.list();
        if (list == null || list.length <= 0) {
            file.delete();
            return;
        }
        for (String str : list) {
            File file2 = new File(file, str);
            if (file2.isDirectory()) {
                deleteFileAndFolder(file2);
            } else {
                file2.delete();
            }
        }
        file.delete();
    }

    @Deprecated
    public static int parseInt(String str) throws Throwable {
        return parseInt(str, 10);
    }

    @Deprecated
    public static int parseInt(String str, int i) throws Throwable {
        return Integer.parseInt(str, i);
    }

    @Deprecated
    public static long parseLong(String str) throws Throwable {
        return parseLong(str, 10);
    }

    @Deprecated
    public static long parseLong(String str, int i) throws Throwable {
        return Long.parseLong(str, i);
    }

    public static <T> T forceCast(Object obj) {
        return (T) forceCast(obj, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T forceCast(Object obj, T t) {
        if (obj != 0) {
            try {
                if (obj instanceof Integer) {
                    int iIntValue = ((Integer) obj).intValue();
                    if (t instanceof Long) {
                        return (T) Long.valueOf(iIntValue);
                    }
                }
                return obj;
            } catch (Throwable th) {
            }
        }
        return t;
    }

    public static boolean copyFile(String str, String str2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || !new File(str).exists()) {
            return false;
        }
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                fileOutputStream = new FileOutputStream(str2);
            } catch (Throwable th) {
                fileOutputStream = null;
            }
        } catch (Throwable th2) {
            fileOutputStream = null;
        }
        try {
            copyFile(fileInputStream, fileOutputStream);
            C0041r.a(fileInputStream, fileOutputStream);
            return true;
        } catch (Throwable th3) {
            fileInputStream2 = fileInputStream;
            C0041r.a(fileInputStream2, fileOutputStream);
            return false;
        }
    }

    public static void copyFile(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws Throwable {
        byte[] bArr = new byte[65536];
        int i = fileInputStream.read(bArr);
        while (i > 0) {
            fileOutputStream.write(bArr, 0, i);
            i = fileInputStream.read(bArr);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static boolean saveObjectToFile(String str, Object obj) {
        File file;
        GZIPOutputStream gZIPOutputStream;
        ObjectOutputStream objectOutputStream;
        if (!TextUtils.isEmpty(str)) {
            FileOutputStream fileOutputStream = null;
            try {
                file = new File(str);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                file = null;
            }
            if (obj == null) {
                return true;
            }
            if (!file.getParentFile().exists() || !file.getParentFile().isDirectory()) {
                file.getParentFile().delete();
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            if (file != null) {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        gZIPOutputStream = new GZIPOutputStream(fileOutputStream2);
                        try {
                            objectOutputStream = new ObjectOutputStream(gZIPOutputStream);
                        } catch (Throwable th2) {
                            th = th2;
                            objectOutputStream = null;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        gZIPOutputStream = null;
                        objectOutputStream = null;
                    }
                    try {
                        objectOutputStream.writeObject(obj);
                        objectOutputStream.flush();
                        objectOutputStream.close();
                        C0041r.a(objectOutputStream, gZIPOutputStream, fileOutputStream2);
                        return true;
                    } catch (Throwable th4) {
                        th = th4;
                        fileOutputStream = fileOutputStream2;
                        try {
                            FlyLog.getInstance().d(th);
                            C0041r.a(objectOutputStream, gZIPOutputStream, fileOutputStream);
                            return false;
                        } catch (Throwable th5) {
                            C0041r.a(objectOutputStream, gZIPOutputStream, fileOutputStream);
                            throw th5;
                        }
                    }
                } catch (Throwable th6) {
                    th = th6;
                    gZIPOutputStream = null;
                    objectOutputStream = null;
                }
            }
        }
        return false;
    }

    public static Object readObjectFromFile(String str) {
        File file;
        GZIPInputStream gZIPInputStream;
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        if (!TextUtils.isEmpty(str)) {
            try {
                file = new File(str);
                if (!file.exists()) {
                    file = null;
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                file = null;
            }
            if (file != null) {
                try {
                    fileInputStream = new FileInputStream(file);
                    try {
                        gZIPInputStream = new GZIPInputStream(fileInputStream);
                        try {
                            objectInputStream = new ObjectInputStream(gZIPInputStream);
                        } catch (Throwable th2) {
                            th = th2;
                            objectInputStream = null;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        gZIPInputStream = null;
                        objectInputStream = null;
                    }
                    try {
                        Object object = objectInputStream.readObject();
                        objectInputStream.close();
                        C0041r.a(objectInputStream, gZIPInputStream, fileInputStream);
                        return object;
                    } catch (Throwable th4) {
                        th = th4;
                        try {
                            FlyLog.getInstance().d(th);
                            C0041r.a(objectInputStream, gZIPInputStream, fileInputStream);
                            return null;
                        } catch (Throwable th5) {
                            C0041r.a(objectInputStream, gZIPInputStream, fileInputStream);
                            throw th5;
                        }
                    }
                } catch (Throwable th6) {
                    th = th6;
                    gZIPInputStream = null;
                    fileInputStream = null;
                    objectInputStream = null;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void writeToFileNoCompress(File file, byte[] bArr) throws IOException {
        FileChannel fileChannel;
        FileOutputStream fileOutputStream;
        if (file != null && bArr != null) {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    FlyLog.getInstance().d(e);
                }
            }
            if (file.exists()) {
                FileChannel channel = null;
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (Throwable th) {
                    th = th;
                    fileChannel = null;
                }
                try {
                    channel = fileOutputStream.getChannel();
                    channel.write(ByteBuffer.wrap(bArr));
                    channel.force(true);
                    C0041r.a(channel, fileOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    fileChannel = channel;
                    channel = fileOutputStream;
                    try {
                        FlyLog.getInstance().d(th);
                        C0041r.a(fileChannel, channel);
                    } catch (Throwable th3) {
                        C0041r.a(fileChannel, channel);
                        throw th3;
                    }
                }
            }
        }
    }

    public static byte[] readFromFileNoCompress(File file) {
        FileChannel channel;
        FileInputStream fileInputStream;
        if (file != null && file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    channel = fileInputStream.getChannel();
                } catch (Throwable th) {
                    th = th;
                    channel = null;
                }
            } catch (Throwable th2) {
                th = th2;
                channel = null;
                fileInputStream = null;
            }
            try {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) channel.size());
                while (channel.read(byteBufferAllocate) > 0) {
                }
                byte[] bArrArray = byteBufferAllocate.array();
                C0041r.a(channel, fileInputStream);
                return bArrArray;
            } catch (Throwable th3) {
                th = th3;
                try {
                    FlyLog.getInstance().d(th);
                    C0041r.a(channel, fileInputStream);
                    return null;
                } catch (Throwable th4) {
                    C0041r.a(channel, fileInputStream);
                    throw th4;
                }
            }
        }
        return null;
    }

    public static ArrayList<HashMap<String, String>> readArrayListFromFile(String str) {
        return readArrayListFromFile(str, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v3 */
    public static ArrayList<HashMap<String, String>> readArrayListFromFile(String str, boolean z) {
        GZIPInputStream gZIPInputStream;
        InputStreamReader inputStreamReader;
        Closeable closeable;
        BufferedReader bufferedReader;
        File dataCacheFile = getDataCacheFile(FlySDK.getContext(), str, false);
        if (dataCacheFile.exists() && dataCacheFile.length() > 0) {
            FileInputStream fileInputStream = null;
            try {
                ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                FileInputStream fileInputStream2 = new FileInputStream(dataCacheFile);
                try {
                    gZIPInputStream = new GZIPInputStream(fileInputStream2);
                    try {
                        inputStreamReader = new InputStreamReader(gZIPInputStream, "utf-8");
                        try {
                            bufferedReader = new BufferedReader(inputStreamReader);
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = 0;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStreamReader = null;
                        bufferedReader = inputStreamReader;
                        fileInputStream = fileInputStream2;
                        closeable = bufferedReader;
                        try {
                            FlyLog.getInstance().d(th);
                            C0041r.a(closeable, inputStreamReader, gZIPInputStream, fileInputStream);
                            return new ArrayList<>();
                        } catch (Throwable th3) {
                            C0041r.a(closeable, inputStreamReader, gZIPInputStream, fileInputStream);
                            throw th3;
                        }
                    }
                    try {
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            if (z) {
                                line = new String(Base64.decode(line, 2), "utf-8");
                            }
                            arrayList.add(HashonHelper.fromJson(line));
                            line = bufferedReader.readLine();
                        }
                        C0041r.a(bufferedReader, inputStreamReader, gZIPInputStream, fileInputStream2);
                        return arrayList;
                    } catch (Throwable th4) {
                        th = th4;
                        fileInputStream = fileInputStream2;
                        closeable = bufferedReader;
                        FlyLog.getInstance().d(th);
                        C0041r.a(closeable, inputStreamReader, gZIPInputStream, fileInputStream);
                        return new ArrayList<>();
                    }
                } catch (Throwable th5) {
                    th = th5;
                    gZIPInputStream = null;
                    inputStreamReader = null;
                }
            } catch (Throwable th6) {
                th = th6;
                gZIPInputStream = null;
                inputStreamReader = null;
                closeable = null;
            }
        }
        return new ArrayList<>();
    }

    public static void saveArrayListToFile(ArrayList<HashMap<String, String>> arrayList, String str) {
        saveArrayListToFile(arrayList, str, false);
    }

    public static void saveArrayListToFile(ArrayList<HashMap<String, String>> arrayList, String str, boolean z) {
        GZIPOutputStream gZIPOutputStream;
        OutputStreamWriter outputStreamWriter;
        Closeable closeable = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(getDataCacheFile(FlySDK.getContext(), str, true));
            try {
                gZIPOutputStream = new GZIPOutputStream(fileOutputStream);
                try {
                    outputStreamWriter = new OutputStreamWriter(gZIPOutputStream, "utf-8");
                } catch (Throwable th) {
                    th = th;
                    outputStreamWriter = null;
                }
                try {
                    Iterator<HashMap<String, String>> it = arrayList.iterator();
                    while (it.hasNext()) {
                        String strFromHashMap = HashonHelper.fromHashMap(it.next());
                        if (z) {
                            strFromHashMap = new String(Base64.encode(strFromHashMap.getBytes("utf-8"), 2), "utf-8");
                        }
                        outputStreamWriter.append((CharSequence) strFromHashMap).append('\n');
                    }
                    C0041r.a(outputStreamWriter, gZIPOutputStream, fileOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    closeable = fileOutputStream;
                    try {
                        FlyLog.getInstance().d(th);
                        C0041r.a(outputStreamWriter, gZIPOutputStream, closeable);
                    } catch (Throwable th3) {
                        C0041r.a(outputStreamWriter, gZIPOutputStream, closeable);
                        throw th3;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                gZIPOutputStream = null;
                outputStreamWriter = null;
            }
        } catch (Throwable th5) {
            th = th5;
            gZIPOutputStream = null;
            outputStreamWriter = null;
        }
    }

    public static void closeIOs(Closeable... closeableArr) {
        C0041r.a(closeableArr);
    }

    public static <T> boolean isEqual(T t, T t2) {
        return !((t == null && t2 != null) || !(t == null || t.equals(t2)));
    }

    public static String getImageCachePath(Context context) {
        return getCachePath(context, "images");
    }

    public static void clearCache(Context context) throws Throwable {
        deleteFileAndFolder(new File(getCachePath(context, null)));
    }

    @Deprecated
    public static long dateToLong(String str) {
        try {
            Date date = new Date(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.getTimeInMillis();
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return 0L;
        }
    }

    @Deprecated
    public static long getFileSize(String str) throws Throwable {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        return getFileSize(new File(str));
    }

    @Deprecated
    public static long getFileSize(File file) throws Throwable {
        if (!file.exists()) {
            return 0L;
        }
        if (file.isDirectory()) {
            int fileSize = 0;
            for (String str : file.list()) {
                fileSize = (int) (fileSize + getFileSize(new File(file, str)));
            }
            return fileSize;
        }
        return file.length();
    }

    @Deprecated
    public static Uri pathToContentUri(Context context, String str) {
        try {
            if (DH.SyncMtd.checkPermission(l.a("040ef-edekelejedemLkgQekegejgjgjejel*f1emhkhjgegmeihjhhgdhjhkfhgegfeifmgdhihkgejehj"))) {
                Cursor cursorQuery = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{str}, null);
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    return Uri.withAppendedPath(Uri.parse("content://media/external/images/media"), "" + cursorQuery.getInt(cursorQuery.getColumnIndex("_id")));
                }
                if (new File(str).exists()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_data", str);
                    return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                }
                return null;
            }
            return null;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    @Deprecated
    public static Bundle urlToBundle(String str) {
        int iIndexOf = str.indexOf("://");
        try {
            URL url = new URL(iIndexOf >= 0 ? l.a("007ijjklmm") + str.substring(iIndexOf + 1) : l.a("007ijjklmm") + str);
            Bundle bundleDecodeUrl = decodeUrl(url.getQuery());
            bundleDecodeUrl.putAll(decodeUrl(url.getRef()));
            return bundleDecodeUrl;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return new Bundle();
        }
    }

    @Deprecated
    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            for (String str2 : str.split("&")) {
                String[] strArrSplit = str2.split("=");
                if (strArrSplit.length < 2 || strArrSplit[1] == null) {
                    bundle.putString(URLDecoder.decode(strArrSplit[0]), "");
                } else {
                    bundle.putString(URLDecoder.decode(strArrSplit[0]), URLDecoder.decode(strArrSplit[1]));
                }
            }
        }
        return bundle;
    }

    @Deprecated
    public static long strToDate(String str) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str, new ParsePosition(0)).getTime();
    }

    @Deprecated
    public static String encodeUrl(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj == null) {
                obj = "";
            }
            if (z) {
                z = false;
            } else {
                sb.append("&");
            }
            sb.append(Data.urlEncode(str) + "=" + Data.urlEncode(String.valueOf(obj)));
        }
        return sb.toString();
    }

    @Deprecated
    public static long dateStrToLong(String str) {
        return new SimpleDateFormat("yyyy-MM-dd").parse(str, new ParsePosition(0)).getTime();
    }

    @Deprecated
    public static Uri videoPathToContentUri(Context context, String str) {
        try {
            if (DH.SyncMtd.checkPermission(l.a("040efBedekelejedemCkg4ekegejgjgjejelAfWemhkhjgegmeihjhhgdhjhkfhgegfeifmgdhihkgejehj"))) {
                Cursor cursorQuery = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{str}, null);
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    return Uri.withAppendedPath(Uri.parse("content://media/external/video/media"), "" + cursorQuery.getInt(cursorQuery.getColumnIndex("_id")));
                }
                if (new File(str).exists()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_data", str);
                    return context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
                }
                return null;
            }
            return null;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    @Deprecated
    public static synchronized Uri getMediaUri(Context context, String str, String str2) {
        Uri uri;
        final Object obj = new Object();
        c = null;
        MediaScannerConnection.scanFile(context, new String[]{str}, new String[]{str2}, new MediaScannerConnection.OnScanCompletedListener() { // from class: cn.fly.tools.utils.ResHelper.1
            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String str3, Uri uri2) {
                Uri unused = ResHelper.c = uri2;
                synchronized (obj) {
                    obj.notifyAll();
                }
            }
        });
        try {
            if (c == null) {
                synchronized (obj) {
                    obj.wait(10000L);
                }
            }
        } catch (Throwable th) {
        }
        uri = c;
        c = null;
        return uri;
    }

    public static long getCaliSysTime() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (cn.fly.commons.c.b != 0 && cn.fly.commons.c.c != 0) {
            return cn.fly.commons.c.b + (SystemClock.elapsedRealtime() - cn.fly.commons.c.c);
        }
        return jCurrentTimeMillis;
    }

    public static long getFLT() {
        return y.a().d();
    }
}