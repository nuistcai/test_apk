package cn.fly.tools.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Looper;
import android.text.TextUtils;
import cn.fly.commons.CSCenter;
import cn.fly.commons.a.l;
import cn.fly.commons.m;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class e {
    private static e b;
    private Context a;
    private Object c;
    private PackageManager d;
    private ConcurrentHashMap<String, Object> e = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Integer> f = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Long> g = new ConcurrentHashMap<>();

    private e(Context context) {
        this.a = context;
    }

    public static e a(Context context) {
        if (b == null) {
            synchronized (e.class) {
                if (b == null) {
                    b = new e(context);
                }
            }
        }
        return b;
    }

    public String a(String str) {
        return a(str, "");
    }

    public String a(String str, String str2) {
        Object objInvokeStaticMethodNoThrow = ReflectHelper.invokeStaticMethodNoThrow(ReflectHelper.importClassNoThrow(m.a("027fgNfeflfmfkfefnfmhkfngngehk3khXfhinflfm]lhUfl=kAfkIhNhk"), null), m.a("003.gl,hk"), str2, str);
        if (objInvokeStaticMethodNoThrow != null) {
            return String.valueOf(objInvokeStaticMethodNoThrow);
        }
        return str2;
    }

    public Enumeration<NetworkInterface> a() {
        try {
            return NetworkInterface.getNetworkInterfaces();
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    public List<ResolveInfo> a(Intent intent, int i) {
        if (cn.fly.commons.e.b()) {
            return (List) ReflectHelper.invokeInstanceMethod(this.a.getPackageManager(), m.a("019*fgfi^h0flgeggIgkhgk!gnKhZflfffk*eh)hk"), new Object[]{intent, Integer.valueOf(i)}, new Class[]{Intent.class, Integer.TYPE}, null);
        }
        return null;
    }

    public ResolveInfo b(Intent intent, int i) {
        if (cn.fly.commons.e.b()) {
            return (ResolveInfo) ReflectHelper.invokeInstanceMethod(this.a.getPackageManager(), m.a("015Qfl9h*hkfm9iDffPh hfYekHfkfffk4k!ge"), new Object[]{intent, Integer.valueOf(i)}, new Class[]{Intent.class, Integer.TYPE}, null);
        }
        return null;
    }

    public Object a(String str, int i, boolean z) throws Throwable {
        if (this.d == null) {
            this.d = this.a.getPackageManager();
        }
        if (z) {
            return DH.SyncMtd.invokeInstanceMethod(this.d, m.a("014@gl4hkPinDfeUgjJfFglSh(gg)g4ghfm"), new Object[]{str, Integer.valueOf(i)}, new Class[]{String.class, Integer.TYPE});
        }
        boolean zEquals = str.equals(DH.SyncMtd.getPackageName());
        if (zEquals || cn.fly.commons.e.b()) {
            if (DH.SyncMtd.getOSVersionIntForFly() <= 25 || zEquals) {
                return DH.SyncMtd.invokeInstanceMethod(this.d, m.a("014<glZhkQinOfeRgj2f0gl3hBggZgAghfm"), new Object[]{str, Integer.valueOf(i)}, new Class[]{String.class, Integer.TYPE});
            }
            Object objA = j.a(this.a, str, i);
            if (objA == null) {
                return DH.SyncMtd.invokeInstanceMethod(this.d, m.a("014.glLhkIinUfe7gjLfUglBhGgg,gYghfm"), new Object[]{str, Integer.valueOf(i)}, new Class[]{String.class, Integer.TYPE});
            }
            return objA;
        }
        return null;
    }

    public void a(String str, long j, float f, Object obj) {
        if (cn.fly.commons.e.e()) {
            try {
                if (cn.fly.tools.utils.e.a().a(str)) {
                    Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(m.a("008i9fm4efk1fkfmLg"));
                    Class<?> cls = Class.forName(m.a("033fg6feflfmfkfefn6iSfmEefk-fkfm_gLfnhgfm,efkWfkfm7g2hgfkhkJkhgh.fl"));
                    if (systemServiceSafe != null) {
                        ReflectHelper.invokeInstanceMethod(systemServiceSafe, m.a("022Ofl;hTfgfiHh+hkJk:hgfm0efk7fkfm4g8gm^l@fe>fkhJhk"), new Object[]{str, Long.valueOf(j), Float.valueOf(f), obj, l.a().c()}, new Class[]{String.class, Long.TYPE, Float.TYPE, cls, Looper.class});
                    }
                }
            } catch (Throwable th) {
                FlyLog.getInstance().w(th);
            }
        }
    }

    public Object b(String str) {
        Object systemServiceSafe;
        if (cn.fly.commons.e.f() && cn.fly.tools.utils.e.a().a(str) && (systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(m.a("008i$fm<efkSfkfmLg"))) != null) {
            return ReflectHelper.invokeInstanceMethodNoThrow(systemServiceSafe, m.a("020$gl8hkRhgBf:hk kIkeGgKfmhi!gKhgfm:efkWfkfm:g"), null, str);
        }
        return null;
    }

    public int b() {
        if (!c.a(this.a).d().e(m.a("035fgFfeflfmfkfefnQlh$flfhfkhkhkfkfmDg8fnilikhfhnfjinhmijgiikfjgnhehfheik"))) {
            return -1;
        }
        if (CSCenter.getInstance().isPhoneStateDataEnable()) {
            if (this.c == null) {
                this.c = DH.SyncMtd.getSystemServiceSafe(m.a("005lj-fm(gh"));
            }
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(this.c, m.a("014Egl]hkMgi9hkDhifmflgjhege1lh"), -1, new Object[0])).intValue();
        }
        return CSCenter.getInstance().getNetworkType();
    }

    public int c() {
        if (DH.SyncMtd.getOSVersionIntForFly() < 24 || !DH.SyncMtd.checkPermission(m.a("035fgVfeflfmfkfefn^lh9flfhfkhkhkfkfmYg1fnilikhfhnfjinhmijgiikfjgnhehfheik"))) {
            return -1;
        }
        if (CSCenter.getInstance().isPhoneStateDataEnable()) {
            if (this.c == null) {
                this.c = DH.SyncMtd.getSystemServiceSafe(m.a("005lj'fmGgh"));
            }
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(this.c, m.a("018JglFhk<hnKfkfBgi;hkWhifmflgjhege<lh"), -1, new Object[0])).intValue();
        }
        return CSCenter.getInstance().getNetworkType();
    }

    public Enumeration<InetAddress> a(NetworkInterface networkInterface) {
        return (Enumeration) ReflectHelper.invokeInstanceMethodNoThrow(networkInterface, m.a("016<glKhk8ggMghk!hffefefl8h>hkhkChZhk"), null, new Object[0]);
    }

    public ApplicationInfo a(String str, int i) throws PackageManager.NameNotFoundException {
        if (this.d == null) {
            this.d = this.a.getPackageManager();
        }
        if (TextUtils.equals(str, this.a.getPackageName()) || cn.fly.commons.e.b()) {
            return this.d.getApplicationInfo(str, i);
        }
        return null;
    }

    public ApplicationInfo d() {
        return this.a.getApplicationInfo();
    }
}