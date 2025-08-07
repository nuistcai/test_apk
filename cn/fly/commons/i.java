package cn.fly.commons;

import android.os.Message;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetCommunicator;
import cn.fly.tools.utils.ResHelper;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/* loaded from: classes.dex */
public class i {
    private static final String a = cn.fly.commons.a.l.a("004Vemedfd=h");
    private static i b;
    private NetCommunicator c;
    private String f;
    private SimpleDateFormat d = new SimpleDateFormat(cn.fly.commons.a.l.a("025,fdfdfdfdilididilededjgglglOl^egegOl gjgjemfmfmfmjghe"));
    private HashMap<String, Object> e = new HashMap<>();
    private int g = -1;
    private Runnable h = new cn.fly.tools.utils.i() { // from class: cn.fly.commons.i.1
        @Override // cn.fly.tools.utils.i
        protected void a() {
            if (c.c()) {
                i.this.c();
            }
        }
    };

    public static synchronized i a() {
        if (b == null) {
            b = new i();
        }
        return b;
    }

    private i() {
        this.f = null;
        this.f = UUID.randomUUID().toString();
    }

    private synchronized int b() {
        return this.g;
    }

    public synchronized void a(int i) {
        this.g = i;
        if (i == 1 || i == 4 || i == 17 || i == 18 || i == 19 || i == 20 || i == 10) {
        }
    }

    public synchronized void a(int i, Throwable th) {
        a(i, b(), th, null, "-99");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private synchronized void a(int i, int i2, Throwable th, String str, String str2) {
        if (th == null) {
            FlyLog.getInstance().d(str, new Object[0]);
        } else {
            FlyLog.getInstance().d(th);
        }
        if (f.a()) {
            return;
        }
        final Message message = new Message();
        message.what = 1;
        Long lValueOf = Long.valueOf(System.currentTimeMillis());
        if (th == null) {
            th = str;
        }
        message.obj = new Object[]{lValueOf, th, Integer.valueOf(i), Integer.valueOf(i2), str2};
        ab.b.execute(new cn.fly.tools.utils.i() { // from class: cn.fly.commons.i.2
            @Override // cn.fly.tools.utils.i
            protected void a() {
                i.this.a(message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Message message) {
        String strValueOf;
        if (this.e.size() > 10) {
            c(this.e);
            this.e.clear();
        }
        Object[] objArr = (Object[]) message.obj;
        this.e.put(cn.fly.commons.a.l.a("002Igjed"), this.f);
        ArrayList arrayList = (ArrayList) this.e.get(cn.fly.commons.a.l.a("004h ejgj+j"));
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        HashMap map = new HashMap();
        map.put(cn.fly.commons.a.l.a("002dj"), objArr[0]);
        if (objArr[1] instanceof Throwable) {
            strValueOf = a((Throwable) objArr[1]);
        } else {
            strValueOf = String.valueOf(objArr[1]);
        }
        if (!TextUtils.isEmpty(strValueOf)) {
            strValueOf = strValueOf.replaceAll("\r\n\t", " ").replaceAll("\n\t", " ").replaceAll("\n", " ");
        }
        map.put(cn.fly.commons.a.l.a("0028egfk"), "[" + this.d.format(objArr[0]) + "][" + objArr[2] + "][" + objArr[3] + "][" + objArr[4] + "] " + strValueOf);
        map.put(cn.fly.commons.a.l.a("002gj"), objArr[2]);
        map.put(cn.fly.commons.a.l.a("002k$el"), objArr[3]);
        arrayList.add(map);
        this.e.put(cn.fly.commons.a.l.a("004hVejgjDj"), arrayList);
        if (f.a()) {
            return;
        }
        ab.b.execute(this.h);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        boolean zA;
        File[] fileArrListFiles;
        if (this.e.size() <= 0) {
            zA = true;
        } else {
            zA = a(this.e);
            if (!zA) {
                c(this.e);
            }
            this.e.clear();
        }
        if (zA) {
            File fileE = e();
            if (fileE.exists() && fileE.isDirectory() && (fileArrListFiles = fileE.listFiles()) != null && fileArrListFiles.length > 0) {
                for (File file : fileArrListFiles) {
                    if (a((HashMap<String, Object>) ResHelper.readObjectFromFile(file.getAbsolutePath())) && !file.delete()) {
                        file.delete();
                    }
                }
            }
        }
    }

    private boolean a(HashMap<String, Object> map) {
        try {
            return b(map);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            try {
                return b(map);
            } catch (Throwable th2) {
                FlyLog.getInstance().d(th2);
                return false;
            }
        }
    }

    private boolean b(HashMap<String, Object> map) throws Throwable {
        if (map == null || map.isEmpty()) {
            return true;
        }
        HashMap<String, Object> mapE = q.e();
        mapE.put(cn.fly.commons.a.l.a("006gRekekelekgj"), map);
        d();
        HashMap map2 = (HashMap) this.c.requestWithoutEncode(false, NetCommunicator.getCommonDefaultHeaders(), mapE, j.a().a("dtc") + "/v2/drl", true);
        return map2 == null || map2.isEmpty();
    }

    private void d() {
        if (this.c == null) {
            this.c = new NetCommunicator(1024, "ab0a0a6473d1891d388773574764b239d4ad80cb2fd3a83d81d03901c1548c13fee7c9692c326e6682b239d4c5d0021d1b607642c47ec29f10b0602908c3e6c9", "23c3c8cb41c47dd288cc7f4c218fbc7c839a34e0a0d1b2130e87b7914936b120a2d6570ee7ac66282328d50f2acfd82f2259957c89baea32547758db05de9cd7c6822304c8e45742f24bbbe41c1e12f09e18c6fab4d078065f2e5aaed94c900c66e8bbf8a120eefa7bd1fb52114d529250084f5f6f369ed4ce9645978dd30c51");
        }
    }

    private String a(Throwable th) {
        if (th == null) {
            return "";
        }
        Throwable cause = th;
        while (true) {
            StringWriter stringWriter = null;
            if (cause != null) {
                try {
                    if (cause instanceof UnknownHostException) {
                        C0041r.a(null);
                        return "";
                    }
                    cause = cause.getCause();
                } catch (Throwable th2) {
                    th = th2;
                }
            } else {
                StringWriter stringWriter2 = new StringWriter();
                try {
                    PrintWriter printWriter = new PrintWriter(stringWriter2);
                    th.printStackTrace(printWriter);
                    printWriter.flush();
                    String string = stringWriter2.toString();
                    C0041r.a(stringWriter2);
                    return string;
                } catch (Throwable th3) {
                    th = th3;
                    stringWriter = stringWriter2;
                }
            }
            th = th2;
            try {
                if (th instanceof OutOfMemoryError) {
                    String strA = cn.fly.commons.a.l.a("0238fk?gj1fm1jedKfigdek*edgGfmHj0ekej$f$fkjgeleleg");
                    C0041r.a(stringWriter);
                    return strA;
                }
                String message = th.getMessage();
                C0041r.a(stringWriter);
                return message;
            } catch (Throwable th4) {
                C0041r.a(stringWriter);
                throw th4;
            }
        }
    }

    private File e() {
        return new File(ResHelper.getDataCache(FlySDK.getContext()), a);
    }

    private void c(HashMap<String, Object> map) {
        File[] fileArrListFiles;
        try {
            File fileE = e();
            if (!fileE.exists() || !fileE.isDirectory()) {
                fileE.delete();
                fileE.mkdirs();
            }
            int i = 0;
            File file = new File(fileE, a + "_0");
            if (file.exists() && (fileArrListFiles = fileE.listFiles()) != null && fileArrListFiles.length > 0) {
                file = new File(fileE, a + "_0");
                while (file.exists()) {
                    i++;
                    file = new File(fileE, a + "_" + i);
                }
            }
            ResHelper.saveObjectToFile(file.getPath(), map);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }
}