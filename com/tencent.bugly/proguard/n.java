package com.tencent.bugly.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class n {
    private Context c;
    private SharedPreferences f;
    private static n b = null;
    public static final long a = System.currentTimeMillis();
    private Map<Integer, Map<String, m>> e = new HashMap();
    private String d = com.tencent.bugly.crashreport.common.info.a.b().d;

    private n(Context context) {
        this.c = context;
        this.f = context.getSharedPreferences("crashrecord", 0);
    }

    public static synchronized n a(Context context) {
        if (b == null) {
            b = new n(context);
        }
        return b;
    }

    public static synchronized n a() {
        return b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean b(int i) {
        try {
            List<m> listC = c(i);
            if (listC == null) {
                return false;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (m mVar : listC) {
                if (mVar.b != null && mVar.b.equalsIgnoreCase(this.d) && mVar.d > 0) {
                    arrayList.add(mVar);
                }
                if (mVar.c + 86400000 < jCurrentTimeMillis) {
                    arrayList2.add(mVar);
                }
            }
            Collections.sort(arrayList);
            if (arrayList.size() >= 2) {
                if (arrayList.size() <= 0 || ((m) arrayList.get(arrayList.size() - 1)).c + 86400000 >= jCurrentTimeMillis) {
                    return true;
                }
                listC.clear();
                a(i, (int) listC);
                return false;
            }
            listC.removeAll(arrayList2);
            a(i, (int) listC);
            return false;
        } catch (Exception e) {
            x.e("isFrequentCrash failed", new Object[0]);
            return false;
        }
    }

    public final void a(int i, final int i2) {
        final int i3 = 1004;
        w.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.n.1
            @Override // java.lang.Runnable
            public final void run() {
                m mVar;
                try {
                    if (!TextUtils.isEmpty(n.this.d)) {
                        List<m> listC = n.this.c(i3);
                        if (listC == null) {
                            listC = new ArrayList();
                        }
                        if (n.this.e.get(Integer.valueOf(i3)) == null) {
                            n.this.e.put(Integer.valueOf(i3), new HashMap());
                        }
                        if (((Map) n.this.e.get(Integer.valueOf(i3))).get(n.this.d) != null) {
                            mVar = (m) ((Map) n.this.e.get(Integer.valueOf(i3))).get(n.this.d);
                            mVar.d = i2;
                        } else {
                            mVar = new m();
                            mVar.a = i3;
                            mVar.g = n.a;
                            mVar.b = n.this.d;
                            mVar.f = com.tencent.bugly.crashreport.common.info.a.b().k;
                            mVar.e = com.tencent.bugly.crashreport.common.info.a.b().f;
                            mVar.c = System.currentTimeMillis();
                            mVar.d = i2;
                            ((Map) n.this.e.get(Integer.valueOf(i3))).put(n.this.d, mVar);
                        }
                        ArrayList arrayList = new ArrayList();
                        boolean z = false;
                        for (m mVar2 : listC) {
                            if (mVar2.g == mVar.g && mVar2.b != null && mVar2.b.equalsIgnoreCase(mVar.b)) {
                                mVar2.d = mVar.d;
                                z = true;
                            }
                            if ((mVar2.e != null && !mVar2.e.equalsIgnoreCase(mVar.e)) || ((mVar2.f != null && !mVar2.f.equalsIgnoreCase(mVar.f)) || mVar2.d <= 0)) {
                                arrayList.add(mVar2);
                            }
                        }
                        listC.removeAll(arrayList);
                        if (!z) {
                            listC.add(mVar);
                        }
                        n.this.a(i3, (int) listC);
                    }
                } catch (Exception e) {
                    x.e("saveCrashRecord failed", new Object[0]);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r6v8 */
    public synchronized <T extends List<?>> T c(int i) {
        ObjectInputStream objectInputStream;
        try {
            File file = new File(this.c.getDir("crashrecord", 0), new StringBuilder().append(i).toString());
            ?? Exists = file.exists();
            try {
                if (Exists == 0) {
                    return null;
                }
                try {
                    objectInputStream = new ObjectInputStream(new FileInputStream(file));
                    try {
                        T t = (T) objectInputStream.readObject();
                        objectInputStream.close();
                        return t;
                    } catch (IOException e) {
                        x.a("open record file error", new Object[0]);
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        return null;
                    } catch (ClassNotFoundException e2) {
                        x.a("get object error", new Object[0]);
                        if (objectInputStream != null) {
                            objectInputStream.close();
                            return null;
                        }
                        return null;
                    }
                } catch (IOException e3) {
                    objectInputStream = null;
                } catch (ClassNotFoundException e4) {
                    objectInputStream = null;
                } catch (Throwable th) {
                    th = th;
                    Exists = 0;
                    if (Exists != 0) {
                        Exists.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e5) {
            x.e("readCrashRecord error", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0051 A[Catch: all -> 0x0055, Exception -> 0x0057, TRY_ENTER, TryCatch #4 {Exception -> 0x0057, blocks: (B:7:0x0006, B:11:0x002f, B:21:0x0048, B:27:0x0051, B:28:0x0054), top: B:37:0x0006, outer: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized <T extends List<?>> void a(int i, T t) {
        ObjectOutputStream objectOutputStream;
        Throwable th;
        IOException e;
        if (t == null) {
            return;
        }
        try {
            try {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(this.c.getDir("crashrecord", 0), new StringBuilder().append(i).toString())));
            } catch (IOException e2) {
                objectOutputStream = null;
                e = e2;
            } catch (Throwable th2) {
                objectOutputStream = null;
                th = th2;
                if (objectOutputStream != null) {
                }
                throw th;
            }
        } catch (Exception e3) {
            x.e("writeCrashRecord error", new Object[0]);
        }
        try {
            try {
                objectOutputStream.writeObject(t);
                objectOutputStream.close();
            } catch (Throwable th3) {
                th = th3;
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                throw th;
            }
        } catch (IOException e4) {
            e = e4;
            e.printStackTrace();
            x.a("open record file error", new Object[0]);
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
    }

    public final synchronized boolean a(final int i) {
        boolean z;
        z = true;
        try {
            z = this.f.getBoolean(i + "_" + this.d, true);
            w.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.n.2
                @Override // java.lang.Runnable
                public final void run() {
                    n.this.f.edit().putBoolean(i + "_" + n.this.d, !n.this.b(i)).commit();
                }
            });
        } catch (Exception e) {
            x.e("canInit error", new Object[0]);
            return z;
        }
        return z;
    }
}