package cn.fly.tools.b;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.ResultReceiver;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.m;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public class j {
    static volatile IBinder a;
    private static int b = 0;
    private static volatile int c = Integer.MIN_VALUE;

    /* JADX WARN: Removed duplicated region for block: B:32:0x007a A[Catch: all -> 0x0053, TryCatch #0 {all -> 0x0053, blocks: (B:30:0x0074, B:32:0x007a, B:33:0x007e, B:14:0x0045, B:16:0x004b, B:17:0x004f, B:5:0x0012, B:7:0x002a, B:9:0x0030, B:10:0x0036), top: B:36:0x000e, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007e A[Catch: all -> 0x0053, TRY_LEAVE, TryCatch #0 {all -> 0x0053, blocks: (B:30:0x0074, B:32:0x007a, B:33:0x007e, B:14:0x0045, B:16:0x004b, B:17:0x004f, B:5:0x0012, B:7:0x002a, B:9:0x0030, B:10:0x0036), top: B:36:0x000e, inners: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Set<String> a(Context context, int i) throws Throwable {
        HandlerThread handlerThread = new HandlerThread("M-H-XPL-1");
        handlerThread.start();
        Set<String> setA = null;
        try {
            try {
            } catch (Throwable th) {
                try {
                    FlyLog.getInstance().d(th);
                    if (DH.SyncMtd.getOSVersionIntForFly() >= 18) {
                        handlerThread.quitSafely();
                    } else {
                        handlerThread.quit();
                    }
                } catch (Throwable th2) {
                    try {
                        if (DH.SyncMtd.getOSVersionIntForFly() >= 18) {
                            handlerThread.quitSafely();
                        } else {
                            handlerThread.quit();
                        }
                    } catch (Throwable th3) {
                        FlyLog.getInstance().d(th3);
                    }
                    throw th2;
                }
            }
        } catch (Throwable th4) {
            FlyLog.getInstance().d(th4);
        }
        switch (i) {
            case 1:
                setA = a(context, true, handlerThread);
                if (DH.SyncMtd.getOSVersionIntForFly() >= 18) {
                    handlerThread.quitSafely();
                } else {
                    handlerThread.quit();
                }
                return setA;
            case 4:
                if (!m.a("005,fh4h=fkiffi").equalsIgnoreCase(c.a(context).d().p()) && a()) {
                    setA = a(context, handlerThread);
                }
                if (DH.SyncMtd.getOSVersionIntForFly() >= 18) {
                }
                return setA;
            default:
                if (DH.SyncMtd.getOSVersionIntForFly() >= 18) {
                }
                return setA;
        }
    }

    public static Set<String> a(Context context, HandlerThread handlerThread) throws Throwable {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader = null;
        if (cn.fly.commons.e.b()) {
            File file = new File(context.getFilesDir(), ".tmp11");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, m.a("002+fk;g") + System.currentTimeMillis());
            File file3 = new File(file, "out" + System.currentTimeMillis());
            File file4 = new File(file, NotificationCompat.CATEGORY_ERROR + System.currentTimeMillis());
            if (file3.exists()) {
                file3.delete();
            }
            try {
                a(context, m.a("007lfe3gj0fKglIh"), new String[]{m.a("004i%fkhkXk"), "packages"}, file2, file3, file4, handlerThread);
                if (file3.exists() && file3.length() > 0) {
                    HashSet hashSet = new HashSet();
                    fileInputStream = new FileInputStream(file3);
                    try {
                        inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
                        try {
                            BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader);
                            try {
                                String strA = m.a("008lfe;gj>f4glAhm");
                                for (String line = bufferedReader2.readLine(); line != null; line = bufferedReader2.readLine()) {
                                    String strTrim = line.trim();
                                    if (strTrim.length() > strA.length() && strTrim.substring(0, strA.length()).equalsIgnoreCase(strA)) {
                                        String strTrim2 = strTrim.substring(strA.length()).trim();
                                        if (!TextUtils.isEmpty(strTrim2)) {
                                            hashSet.add(strTrim2);
                                        }
                                    }
                                }
                                C0041r.a(bufferedReader2, inputStreamReader, fileInputStream);
                                file2.delete();
                                file3.delete();
                                file4.delete();
                                return hashSet;
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                C0041r.a(bufferedReader, inputStreamReader, fileInputStream);
                                file2.delete();
                                file3.delete();
                                file4.delete();
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        inputStreamReader = null;
                    }
                } else {
                    C0041r.a(null, null, null);
                    file2.delete();
                    file3.delete();
                    file4.delete();
                }
            } catch (Throwable th4) {
                th = th4;
                fileInputStream = null;
                inputStreamReader = null;
            }
        }
        return null;
    }

    public static Set<String> a(Context context, boolean z, HandlerThread handlerThread) throws Throwable {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        if (cn.fly.commons.e.b()) {
            File file = new File(context.getFilesDir(), ".tmp11");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, m.a("002Efk7g") + System.currentTimeMillis());
            File file3 = new File(file, "out" + System.currentTimeMillis());
            File file4 = new File(file, NotificationCompat.CATEGORY_ERROR + System.currentTimeMillis());
            if (file3.exists()) {
                file3.delete();
            }
            try {
                if (z) {
                    a(context, m.a("007lfeHgj2f5glEh"), new String[]{m.a("0167fgfi7hMflgejmXfek5fkfffk%k4fkIhRhk"), "-a", m.a("026fg$feflfmfkfefnfkCgkhgk5fn+fek;fkfm4g)fnjehfgggi"), "--user", "0"}, file2, file3, file4, handlerThread);
                } else {
                    a(context, m.a("007lfe*gj6f9glVh"), new String[]{m.a("016Cfgfi+hCflgejmWfekFfkfffkRk@fkIh$hk"), "-a", m.a("026fgZfeflfmfkfefnfkHgkhgkZfn9fek2fkfm(gVfnjehfgggi"), "-c", m.a("032fgFfeflfmfkfefnfk%gkhgkUfnYefkh%glfmflgefnhghfgmgigfhmikil"), "--user", "0"}, file2, file3, file4, handlerThread);
                }
                if (file3.exists() && file3.length() > 0) {
                    HashSet hashSet = new HashSet();
                    fileInputStream = new FileInputStream(file3);
                    try {
                        inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
                        try {
                            bufferedReader = new BufferedReader(inputStreamReader);
                        } catch (Throwable th) {
                            th = th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStreamReader = null;
                    }
                    try {
                        String strA = m.a("012lfe3gj9f2glRh5giDf2fhRhOkk");
                        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                            String strTrim = line.trim();
                            if (strTrim.length() > strA.length() && strTrim.substring(0, strA.length()).equalsIgnoreCase(strA)) {
                                String strTrim2 = strTrim.substring(strA.length()).trim();
                                if (!TextUtils.isEmpty(strTrim2)) {
                                    hashSet.add(strTrim2);
                                }
                            }
                        }
                        C0041r.a(bufferedReader, inputStreamReader, fileInputStream);
                        file2.delete();
                        file3.delete();
                        file4.delete();
                        return hashSet;
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedReader2 = bufferedReader;
                        C0041r.a(bufferedReader2, inputStreamReader, fileInputStream);
                        file2.delete();
                        file3.delete();
                        file4.delete();
                        throw th;
                    }
                }
                C0041r.a(null, null, null);
                file2.delete();
                file3.delete();
                file4.delete();
            } catch (Throwable th4) {
                th = th4;
                fileInputStream = null;
                inputStreamReader = null;
            }
        }
        return null;
    }

    private static int a(Context context, String str, String[] strArr, File file, File file2, File file3, HandlerThread handlerThread) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        Object objA;
        FileOutputStream fileOutputStream3 = null;
        try {
            IBinder iBinder = (IBinder) cn.fly.tools.a.d.a(context).a(m.a("025fgEfeflfmfkfefnfmhkfngn6hKflfffkVehHje^fgfDglZhZfl"), (Object) null, m.a("010ZglFhk'gn0h<flfffk!eh"), new Class[]{String.class}, new Object[]{str}, (Object[]) null);
            if (iBinder != null && (objA = cn.fly.tools.a.d.a(context).a(m.a("024fgYfeflfmfkfefnfmhkfngn2jhii:gf(fii hhCfeIgj"))) != null) {
                FileOutputStream fileOutputStream4 = new FileOutputStream(file);
                try {
                    fileOutputStream = new FileOutputStream(file2);
                    try {
                        fileOutputStream2 = new FileOutputStream(file3);
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = null;
                    }
                    try {
                        cn.fly.tools.a.d.a(context).a(IBinder.class, iBinder, m.a("012Yhk,jhii<gffmfhfhMfgBfe"), new Class[]{FileDescriptor.class, FileDescriptor.class, FileDescriptor.class, String[].class, Class.forName(m.a("024fg,feflfmfkfefnfmhkfngnZjhii.gfWfii-hh^fe2gj")), ResultReceiver.class}, new Object[]{fileOutputStream4.getFD(), fileOutputStream.getFD(), fileOutputStream2.getFD(), strArr, objA, new ResultReceiver(new Handler(handlerThread.getLooper()))}, (Object[]) null);
                        C0041r.a(fileOutputStream4, fileOutputStream, fileOutputStream2);
                        return 0;
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream3 = fileOutputStream4;
                        C0041r.a(fileOutputStream3, fileOutputStream, fileOutputStream2);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                    fileOutputStream2 = null;
                }
            } else {
                C0041r.a(null, null, null);
                return -1;
            }
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            fileOutputStream2 = null;
        }
    }

    public static Object a(Context context, String str, int i) throws Throwable {
        return a(context, str, i, c(), a(context));
    }

    private static Object a(Context context, String str, int i, int i2, int i3) throws Throwable {
        if (DH.SyncMtd.getOSVersionInt() < 23) {
            return null;
        }
        if (a == null) {
            a = (IBinder) cn.fly.tools.a.d.a(context).a(m.a("025fg>feflfmfkfefnfmhkfngnFh1flfffk1eh=je8fgf*glYhUfl"), (Object) null, m.a("010VglShkRgn>h8flfffkGeh"), new Class[]{String.class}, new Object[]{m.a("007lfe<gj!f-gl1h")}, (Object[]) null);
        }
        if (a == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(m.a("034fg%feflfmfkfefnSe9fmWgkhgkYfn:lJfhfngginSfeCgj@fEgl-h[jeQfgfZgl+hSfl"));
            parcelObtain.writeString(str);
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            a.transact(i3, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readTypedObject(b());
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
            cn.fly.tools.a.d.a(context).b(context);
        }
    }

    private static Parcelable.Creator<?> b() throws Throwable {
        return (Parcelable.Creator) ReflectHelper.getStaticField(ReflectHelper.importClass(m.a("030fgXfeflfmfkfefn.eRfm@gkhgkRfn<l'fhfninIfe?gj'f.gl=hQggFgGghfm")), m.a("007.gfilikhfheijil"));
    }

    private static int a(Context context) throws Throwable {
        if (b != 0) {
            return b;
        }
        b = ((Integer) cn.fly.tools.a.d.a(context).a(m.a("034fgPfeflfmfkfefn6e?fmBgkhgk<fn'lQfhfnggin<fe*gjHf gl4hOjeUfgfDgl5hCfl") + "$" + m.a("0047gnXkEfihh"), m.a("0264heilhfgignhfgfheggijgifjgl]hkCinMfe-gj)f?glVh]ggKg>ghfm"), null, 0)).intValue();
        return b;
    }

    private static int c() {
        if (c != Integer.MIN_VALUE) {
            return c;
        }
        if (DH.SyncMtd.getOSVersionIntForFly() >= 17) {
            try {
                int iIntValue = ((Integer) ReflectHelper.invokeStaticMethod(ReflectHelper.importClass(m.a("021fg:feflfmfkfefnfmhkfngmhkShKflhm:fgZfe5ih")), m.a("009_glOhk)gmhkPhFflggfe"), new Object[]{Integer.valueOf(Process.myUid())}, new Class[]{Integer.TYPE})).intValue();
                c = iIntValue;
                return iIntValue;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return 0;
    }

    public static boolean a() {
        try {
            if (!m.a("006j[fi f1hi5h<fk").equalsIgnoreCase(DH.SyncMtd.getManufacturerForFly())) {
                return true;
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final int[] iArr = new int[1];
            DH.requester(FlySDK.getContext()).getHmOsDetailedVer().request(new DH.DHResponder() { // from class: cn.fly.tools.b.j.1
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    String hmOsDetailedVer = dHResponse.getHmOsDetailedVer();
                    if (hmOsDetailedVer == null) {
                        hmOsDetailedVer = "";
                    }
                    iArr[0] = "3.0.0.200".compareTo(hmOsDetailedVer);
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            if (iArr[0] > 0) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return true;
        }
    }
}