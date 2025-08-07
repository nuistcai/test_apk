package cn.fly.commons.eventrecoder;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.t;
import cn.fly.commons.u;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.tools.utils.FileLocker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class EventRecorder implements PublicMemberKeeper {
    private static File a;
    private static FileOutputStream b;

    public static final synchronized void prepare() {
        a(new t() { // from class: cn.fly.commons.eventrecoder.EventRecorder.1
            @Override // cn.fly.commons.t
            public boolean a(FileLocker fileLocker) {
                try {
                    File unused = EventRecorder.a = new File(FlySDK.getContext().getFilesDir(), ".mrecord");
                    if (!EventRecorder.a.exists()) {
                        EventRecorder.a.createNewFile();
                    }
                    FileOutputStream unused2 = EventRecorder.b = new FileOutputStream(EventRecorder.a, true);
                    return false;
                } catch (Throwable th) {
                    FlyLog.getInstance().w(th);
                    return false;
                }
            }
        });
    }

    public static final synchronized void addBegin(String str, String str2) {
        a(str + " " + str2 + " 0\n");
    }

    private static final void a(t tVar) {
        u.a(new File(FlySDK.getContext().getFilesDir(), u.a), tVar);
    }

    public static final synchronized void addEnd(String str, String str2) {
        a(str + " " + str2 + " 1\n");
    }

    private static final void a(final String str) {
        a(new t() { // from class: cn.fly.commons.eventrecoder.EventRecorder.2
            @Override // cn.fly.commons.t
            public boolean a(FileLocker fileLocker) {
                try {
                    EventRecorder.b.write(str.getBytes("utf-8"));
                    EventRecorder.b.flush();
                    return false;
                } catch (Throwable th) {
                    FlyLog.getInstance().w(th);
                    return false;
                }
            }
        });
    }

    public static final synchronized String checkRecord(final String str) {
        final LinkedList linkedList = new LinkedList();
        a(new t() { // from class: cn.fly.commons.eventrecoder.EventRecorder.3
            @Override // cn.fly.commons.t
            public boolean a(FileLocker fileLocker) {
                int iIndexOf;
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(EventRecorder.a), "utf-8"));
                    for (String line = bufferedReader.readLine(); !TextUtils.isEmpty(line); line = bufferedReader.readLine()) {
                        String[] strArrSplit = line.split(" ");
                        if (str.equals(strArrSplit[0])) {
                            if ("0".equals(strArrSplit[2])) {
                                linkedList.add(strArrSplit[1]);
                            } else if ("1".equals(strArrSplit[2]) && (iIndexOf = linkedList.indexOf(strArrSplit[1])) != -1) {
                                linkedList.remove(iIndexOf);
                            }
                        }
                    }
                    bufferedReader.close();
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
                return false;
            }
        });
        if (linkedList.size() <= 0) {
            return null;
        }
        return (String) linkedList.get(0);
    }

    public static final synchronized void clear() {
        a(new t() { // from class: cn.fly.commons.eventrecoder.EventRecorder.4
            @Override // cn.fly.commons.t
            public boolean a(FileLocker fileLocker) {
                try {
                    EventRecorder.b.close();
                    EventRecorder.a.delete();
                    File unused = EventRecorder.a = new File(FlySDK.getContext().getFilesDir(), ".mrecord");
                    EventRecorder.a.createNewFile();
                    FileOutputStream unused2 = EventRecorder.b = new FileOutputStream(EventRecorder.a, true);
                    return false;
                } catch (Throwable th) {
                    FlyLog.getInstance().w(th);
                    return false;
                }
            }
        });
    }
}