package cn.fly.tools;

import android.text.TextUtils;
import android.util.Base64;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.m;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ResHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.HashSet;

/* loaded from: classes.dex */
public class a {
    private static final Object a = new Object();
    private static final Object b = new Object();
    private volatile HashSet<String> c = new HashSet<>();
    private File d;
    private int e;
    private String f;

    /* renamed from: cn.fly.tools.a$a, reason: collision with other inner class name */
    public interface InterfaceC0019a {
        void a(String str);

        boolean a(DH.DHResponse dHResponse);
    }

    public a(String str, String str2, int i) {
        this.e = i;
        if (str2 == null) {
            str2 = "null";
        } else if (TextUtils.isDigitsOnly(str2)) {
            str2 = str + str2;
        }
        this.f = str2;
        this.d = ResHelper.getDataCacheFile(FlySDK.getContextSafely(), str);
        if (!this.d.isDirectory()) {
            this.d.mkdirs();
        }
    }

    public void a(String str) throws Throwable {
        a(str, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str) {
        synchronized (this.c) {
            if (this.c.contains(str)) {
                return true;
            }
            this.c.add(str);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str) {
        synchronized (this.c) {
            this.c.remove(str);
        }
    }

    public void a(String str, boolean z) throws Throwable {
        FileWriter fileWriter;
        String name;
        BufferedWriter bufferedWriter;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String strEncodeToString = Base64.encodeToString(str.getBytes("utf-8"), 2);
        if (TextUtils.isEmpty(strEncodeToString)) {
            return;
        }
        synchronized (a) {
            File fileA = a(z);
            BufferedWriter bufferedWriter2 = null;
            try {
                fileWriter = new FileWriter(fileA, true);
                try {
                    bufferedWriter = new BufferedWriter(fileWriter);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileWriter = null;
            }
            try {
                bufferedWriter.newLine();
                bufferedWriter.write(strEncodeToString);
                C0041r.a(bufferedWriter, fileWriter);
                name = fileA.getName();
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter2 = bufferedWriter;
                try {
                    FlyLog.getInstance().d(th);
                    C0041r.a(bufferedWriter2, fileWriter);
                    name = fileA.getName();
                    c(name);
                } catch (Throwable th4) {
                    C0041r.a(bufferedWriter2, fileWriter);
                    c(fileA.getName());
                    throw th4;
                }
            }
            c(name);
        }
    }

    private File a(boolean z) {
        File file;
        File[] fileArrListFiles = this.d.listFiles();
        int i = 3;
        char c = 2;
        if (fileArrListFiles != null && fileArrListFiles.length > 0) {
            int length = fileArrListFiles.length;
            int i2 = 0;
            int i3 = 1;
            while (i2 < length) {
                File file2 = fileArrListFiles[i2];
                String name = file2.getName();
                if (name.startsWith(this.f)) {
                    String[] strArrSplit = name.split("_");
                    if (!z && strArrSplit.length == i) {
                        try {
                            int i4 = Integer.parseInt(strArrSplit[c]);
                            if (i4 < this.e && !b(name)) {
                                File file3 = new File(this.d, a(this.f, "_", Integer.valueOf(i3), "_", Integer.valueOf(i4 + 1)));
                                if (file2.renameTo(file3)) {
                                    return file3;
                                }
                                return file2;
                            }
                        } catch (Throwable th) {
                            FlyLog.getInstance().d(th);
                        }
                    }
                    if (strArrSplit.length > 1) {
                        try {
                            if (Integer.parseInt(strArrSplit[1]) == i3) {
                                i3++;
                            }
                        } catch (Throwable th2) {
                            FlyLog.getInstance().d(th2);
                        }
                    }
                }
                i2++;
                i = 3;
                c = 2;
            }
            file = new File(this.d, a(this.f, "_", Integer.valueOf(i3), "_", 0));
        } else {
            file = new File(this.d, a(this.f, "_", 1, "_", 0));
        }
        try {
            file.createNewFile();
        } catch (Throwable th3) {
        }
        return file;
    }

    public void a(final InterfaceC0019a interfaceC0019a) {
        if (interfaceC0019a == null) {
            return;
        }
        synchronized (b) {
            final File[] fileArrListFiles = this.d.listFiles(new FilenameFilter() { // from class: cn.fly.tools.a.1
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str) {
                    return !TextUtils.isEmpty(str) && str.startsWith(a.this.f);
                }
            });
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                DH.requester(FlySDK.getContext()).getDetailNetworkTypeForStatic().getAppName().getDeviceKey().getODH().request(new DH.DHResponder() { // from class: cn.fly.tools.a.2
                    @Override // cn.fly.tools.utils.DH.DHResponder
                    public void onResponse(DH.DHResponse dHResponse) {
                        BufferedReader bufferedReader;
                        if (m.a("004g7fmOgh").equals(dHResponse.getDetailNetworkTypeForStatic())) {
                            return;
                        }
                        for (File file : fileArrListFiles) {
                            String name = file.getName();
                            if (!a.this.b(name)) {
                                FileReader fileReader = null;
                                try {
                                    FileReader fileReader2 = new FileReader(file);
                                    try {
                                        bufferedReader = new BufferedReader(fileReader2);
                                        while (true) {
                                            try {
                                                String line = bufferedReader.readLine();
                                                if (line == null) {
                                                    break;
                                                } else {
                                                    interfaceC0019a.a(new String(Base64.decode(line, 2), "utf-8"));
                                                }
                                            } catch (Throwable th) {
                                                th = th;
                                                fileReader = fileReader2;
                                                try {
                                                    FlyLog.getInstance().d(th);
                                                    C0041r.a(bufferedReader, fileReader);
                                                    a.this.c(name);
                                                } catch (Throwable th2) {
                                                    C0041r.a(bufferedReader, fileReader);
                                                    a.this.c(name);
                                                    throw th2;
                                                }
                                            }
                                        }
                                        if (interfaceC0019a.a(dHResponse)) {
                                            FlyLog.getInstance().d("[LGSM] D l", new Object[0]);
                                            file.delete();
                                        }
                                        C0041r.a(bufferedReader, fileReader2);
                                    } catch (Throwable th3) {
                                        th = th3;
                                        bufferedReader = null;
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    bufferedReader = null;
                                }
                                a.this.c(name);
                            }
                        }
                    }
                });
            }
        }
    }

    public void a(long j) {
        synchronized (b) {
            File[] fileArrListFiles = this.d.listFiles(new FilenameFilter() { // from class: cn.fly.tools.a.3
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str) {
                    return !TextUtils.isEmpty(str) && str.startsWith(a.this.f);
                }
            });
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                long length = 0;
                for (File file : fileArrListFiles) {
                    length += file.length();
                }
                if (length >= j) {
                    for (File file2 : fileArrListFiles) {
                        file2.delete();
                    }
                }
            }
        }
    }

    private static String a(Object... objArr) {
        if (objArr == null || objArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : objArr) {
            sb.append(obj);
        }
        return sb.toString();
    }
}