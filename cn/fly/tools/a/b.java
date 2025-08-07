package cn.fly.tools.a;

import android.content.Context;
import android.util.Base64;
import cn.fly.commons.C0041r;
import cn.fly.commons.w;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.tools.utils.ResHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
class b implements a {
    private static final String a = w.b("014OckehgegdegiegcgiKc^ehcbgfccie");
    private static volatile boolean j = false;
    private Method b = null;
    private Method c = null;
    private Method d = null;
    private Method e = null;
    private Method f = null;
    private Method g = null;
    private Method h = null;
    private boolean i = false;

    public boolean a(Context context) {
        FileOutputStream fileOutputStream;
        try {
            ResHelper.deleteFileAndFolder(new File(context.getFilesDir(), w.b("0144ckehgegdegiegcgi:cKehcbgfccge")));
        } catch (Throwable th) {
        }
        try {
            File file = new File(context.getFilesDir(), a);
            FileOutputStream fileOutputStream2 = null;
            if (!file.exists()) {
                byte[] bArrDecode = Base64.decode("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", 2);
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    fileOutputStream.write(bArrDecode);
                    C0041r.a(fileOutputStream);
                    file.setReadOnly();
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream2 = fileOutputStream;
                    C0041r.a(fileOutputStream2);
                    throw th;
                }
            }
            Class cls = (Class) ReflectHelper.invokeInstanceMethod(ReflectHelper.newInstance(ReflectHelper.importClass(w.b("021Qcb0cf]ccchdgckehdbeh_heQceckek!eRdhfbchBfe")), file), w.b("009fTcj:cOcbdc%fc6eheh"), new Object[]{w.b("026b8cjceckcecbck0bch;ckeech<d=cbOe_cickejcheich_dAcb^e<ci"), null}, new Class[]{String.class, ClassLoader.class});
            this.b = cls.getDeclaredMethod(w.b("014e>dh6eXceNihXchcj*d%ehejecfkdd"), String[].class);
            this.b.setAccessible(true);
            this.c = cls.getDeclaredMethod(w.b("010(chEdGcccjdg%e ejecfkdd"), Class.class, Object.class, String.class, Class[].class, Object[].class);
            this.c.setAccessible(true);
            this.d = cls.getDeclaredMethod(w.b("010^ch4d,cccjdgIeZejecfkdd"), String.class, Object.class, String.class, Class[].class, Object[].class);
            this.d.setAccessible(true);
            this.e = cls.getDeclaredMethod(w.b("012de=efejddHdSeh)hcdbe"), String.class);
            this.e.setAccessible(true);
            this.f = cls.getDeclaredMethod(w.b("012de;efejdd0dMeh<hcdbe"), String.class, Class[].class, Object[].class);
            this.f.setAccessible(true);
            this.g = cls.getDeclaredMethod(w.b("0093diSehNejfbchDefOcb"), String.class, String.class, Object.class);
            this.g.setAccessible(true);
            this.h = cls.getDeclaredMethod(w.b("007<di9eh=ejdc)fSfc"), String.class);
            this.h.setAccessible(true);
            this.i = true;
        } finally {
            return this.i;
        }
        return this.i;
    }

    public static boolean b(Context context) {
        if (!j) {
            try {
                File file = new File(context.getFilesDir(), a);
                if (file.exists()) {
                    j = file.delete();
                }
            } catch (Throwable th) {
            }
        }
        return j;
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(Class cls, Object obj, String str, Class[] clsArr, Object[] objArr) throws Throwable {
        if (this.c != null) {
            return (T) this.c.invoke(null, cls, obj, str, clsArr, objArr);
        }
        throw new Throwable("IHA is null");
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str, Object obj, String str2, Class[] clsArr, Object[] objArr) throws Throwable {
        if (this.d != null) {
            return (T) this.d.invoke(null, str, obj, str2, clsArr, objArr);
        }
        throw new Throwable("IHABC is null");
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str) throws Throwable {
        if (this.e != null) {
            return (T) this.e.invoke(null, str);
        }
        throw new Throwable("nHI is null");
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str, String str2, Object obj) throws Throwable {
        if (this.g != null) {
            return (T) this.g.invoke(null, str, str2, obj);
        }
        throw new Throwable("gHF is null");
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str, Class[] clsArr, Object[] objArr) throws Throwable {
        if (this.f != null) {
            return (T) this.f.invoke(null, str, clsArr, objArr);
        }
        throw new Throwable("nHIByParams is null");
    }
}