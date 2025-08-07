package cn.fly.tools.utils;

import android.content.Context;
import android.location.GnssStatus;
import android.os.Handler;
import cn.fly.FlySDK;
import cn.fly.commons.CSCenter;
import cn.fly.commons.a.l;
import cn.fly.commons.m;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class e {
    private static e a;
    private static final Object j = new Object();
    private volatile Object d;
    private volatile Class<?> f;
    private long g;
    private a h;
    private volatile Object i;
    private volatile List b = new ArrayList();
    private volatile List c = new ArrayList();
    private volatile Object e = g();

    public interface a {
        void a();
    }

    private e() {
    }

    public static e a() {
        if (a == null) {
            synchronized (e.class) {
                if (a == null) {
                    a = new e();
                }
            }
        }
        return a;
    }

    public void a(a aVar) {
        this.h = aVar;
        e();
    }

    private void e() {
        if (cn.fly.commons.e.g()) {
            try {
                if (DH.SyncMtd.checkPermission(m.a("039fg9feflfmfkfefn,lh!flfhfkhkhkfkfmGgIfnhfgfgfikgngnfjiegggiikfjhgijgfhfheggijgi"))) {
                    if (this.d == null) {
                        this.d = DH.SyncMtd.getSystemServiceSafe(m.a("008i5fmKefk_fkfm1g"));
                    }
                    if (this.d == null) {
                        return;
                    }
                    if (DH.SyncMtd.getOSVersionIntForFly() < 31 && FlySDK.getContext().getApplicationInfo().targetSdkVersion < 31) {
                        l.a().b().post(new Runnable() { // from class: cn.fly.tools.utils.e.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    ReflectHelper.invokeInstanceMethod(e.this.d, m.a("020fWfefekf[lShkgn:kfkRfihkhgfkhkGkhgh9fl"), new Object[]{e.this.f()}, new Class[]{Class.forName(m.a("026fg+feflfmfkfefnUiHfm:efkWfkfm>g(fnkfOl9hkgnXkfkYfihk") + "$" + m.a("0087hgfkhk<khgh1fl"))});
                                    FlyLog.getInstance().d("[212] rg < 31", new Object[0]);
                                } catch (Throwable th) {
                                    FlyLog.getInstance().d(th, "%s", "[cl]");
                                }
                            }
                        });
                    } else if (DH.SyncMtd.getOSVersionIntForFly() >= 31) {
                        ReflectHelper.invokeInstanceMethod(this.d, m.a("0260fl;h+glfkhk$khNflkf8g=hkhkgn6kfk[fihkgf>fii<hhYfeEgj"), new Object[]{h(), l.a().b()}, new Class[]{GnssStatus.Callback.class, Handler.class});
                        FlyLog.getInstance().d("[212] rg >= 31", new Object[0]);
                    }
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th, "%s", "[212]");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object f() throws Throwable {
        HashMap map = new HashMap();
        final int iIdentityHashCode = System.identityHashCode(map);
        map.put(m.a("018'fmAg$kfAl=hkgnIkfk>fihkgf>jfg0glVhOfe"), new ReflectHelper.a<Object[], Object>() { // from class: cn.fly.tools.utils.e.2
            @Override // cn.fly.tools.utils.ReflectHelper.a
            public Object a(Object[] objArr) {
                if (objArr != null && ((Integer) objArr[0]).intValue() == 4) {
                    e.this.i();
                    return null;
                }
                return null;
            }
        });
        map.put("equals", new ReflectHelper.a<Object[], Object>() { // from class: cn.fly.tools.utils.e.3
            @Override // cn.fly.tools.utils.ReflectHelper.a
            public Object a(Object[] objArr) {
                if (objArr == null || objArr[0] == null) {
                    return false;
                }
                return Boolean.valueOf(objArr[0].hashCode() == iIdentityHashCode);
            }
        });
        map.put(m.a("008jfLhk'j+gffmfe9h"), new ReflectHelper.a<Object[], Object>() { // from class: cn.fly.tools.utils.e.4
            @Override // cn.fly.tools.utils.ReflectHelper.a
            public Object a(Object[] objArr) {
                return Integer.valueOf(iIdentityHashCode);
            }
        });
        return ReflectHelper.createProxy((Map<String, ReflectHelper.a<Object[], Object>>) map, (Class<?>[]) new Class[]{Class.forName(m.a("026fg5feflfmfkfefnWi%fmJefkVfkfmXgMfnkf9lWhkgnPkfk)fihk") + "$" + m.a("008ZhgfkhkMkhgh2fl"))});
    }

    private Object g() {
        HashMap map = new HashMap();
        final int iIdentityHashCode = System.identityHashCode(map);
        try {
            map.put(m.a("017Nfm g3hgfm[efkZfkfm6g2gf1jfg=glUh(fe"), new ReflectHelper.a<Object[], Object>() { // from class: cn.fly.tools.utils.e.5
                @Override // cn.fly.tools.utils.ReflectHelper.a
                public Object a(Object[] objArr) {
                    if (objArr != null) {
                        try {
                            if (objArr.length > 0) {
                                FlyLog.getInstance().d("[212] oncge" + objArr[0], new Object[0]);
                                if (objArr[0] instanceof List) {
                                    e.this.c.addAll((List) objArr[0]);
                                } else {
                                    e.this.c.add(objArr[0]);
                                }
                            }
                        } catch (Throwable th) {
                            try {
                                FlyLog.getInstance().d(th);
                                synchronized (e.j) {
                                    e.j.notifyAll();
                                }
                            } catch (Throwable th2) {
                                synchronized (e.j) {
                                    e.j.notifyAll();
                                    throw th2;
                                }
                            }
                        }
                    }
                    e.this.j();
                    synchronized (e.j) {
                        e.j.notifyAll();
                    }
                    return null;
                }
            });
            map.put("equals", new ReflectHelper.a<Object[], Object>() { // from class: cn.fly.tools.utils.e.6
                @Override // cn.fly.tools.utils.ReflectHelper.a
                public Object a(Object[] objArr) {
                    FlyLog.getInstance().d("equals " + objArr, new Object[0]);
                    if (objArr == null || objArr[0] == null) {
                        return false;
                    }
                    return Boolean.valueOf(objArr[0].hashCode() == iIdentityHashCode);
                }
            });
            map.put(m.a("008jf)hk$jZgffmfeHh"), new ReflectHelper.a<Object[], Object>() { // from class: cn.fly.tools.utils.e.7
                @Override // cn.fly.tools.utils.ReflectHelper.a
                public Object a(Object[] objArr) {
                    FlyLog.getInstance().d(m.a("008jfThkFjKgffmfe h"), new Object[0]);
                    return Integer.valueOf(iIdentityHashCode);
                }
            });
            return ReflectHelper.createProxy((Map<String, ReflectHelper.a<Object[], Object>>) map, (Class<?>[]) new Class[]{k()});
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    private GnssStatus.Callback h() {
        if (DH.SyncMtd.getOSVersionIntForFly() < 31) {
            return null;
        }
        return new GnssStatus.Callback() { // from class: cn.fly.tools.utils.e.8
            @Override // android.location.GnssStatus.Callback
            public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
                super.onSatelliteStatusChanged(gnssStatus);
                e.this.i();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        try {
            if (this.h != null) {
                this.h.a();
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th, "%s", "[cl]");
        }
    }

    public List a(Context context, int i, int i2, boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList();
        if (CSCenter.getInstance().isLocationDataEnable()) {
            arrayList.addAll(a(z2));
            if (arrayList.isEmpty()) {
                synchronized (j) {
                    arrayList.addAll(a(z2));
                    if (arrayList.isEmpty()) {
                        arrayList.addAll(a(context, i, i2, z));
                    }
                }
            }
        } else {
            arrayList.add(CSCenter.getInstance().getLocation());
        }
        return arrayList;
    }

    private List a(Context context, int i, int i2, boolean z) {
        Object objB;
        Object objB2;
        ArrayList arrayList = new ArrayList();
        try {
            if (DH.SyncMtd.checkPermission(m.a("039fg?feflfmfkfefn'lhRflfhfkhkhkfkfmXgRfnhfgfgfikgngnfjiegggiikfjhgijgfhfheggijgi")) || DH.SyncMtd.checkPermission(m.a("041fgIfeflfmfkfefnTlh7flfhfkhkhkfkfmJg7fnhfgfgfikgngnfjgfijhfilgnikfjhgijgfhfheggijgi"))) {
                if (this.d == null) {
                    this.d = DH.SyncMtd.getSystemServiceSafe(m.a("008i?fm-efk9fkfm5g"));
                }
                if (this.d == null) {
                    return null;
                }
                synchronized (j) {
                    if (i != 0) {
                        try {
                            if (a(this.d, m.a("003TglVl*hk"))) {
                                a(context, m.a("0032gl4l hk"), i * 1000);
                            }
                        } finally {
                        }
                    }
                    if ((i2 != 0) && a(this.d, m.a("007ghk>hifmflgj"))) {
                        a(context, m.a("007ghk6hifmflgj"), i2 * 1000);
                    }
                }
            }
            if (this.c.isEmpty() && z) {
                Object objB3 = b(m.a("003FglZl9hk"));
                if (objB3 != null) {
                    this.c.add(objB3);
                }
                if (this.c.isEmpty() && (objB2 = b(m.a("007ghk.hifmflgj"))) != null) {
                    this.c.add(objB2);
                }
                if (this.c.isEmpty() && (objB = b("passive")) != null) {
                    this.c.add(objB);
                }
            }
            if (!this.c.isEmpty()) {
                for (Object obj : this.c) {
                    if (obj != null) {
                        this.b.add(ReflectHelper.newInstance(ReflectHelper.importClass(m.a("025fg>feflfmfkfefn>iYfmXefkBfkfm4gQfnhgfmGefk9fkfmOg")), obj));
                        arrayList.add(ReflectHelper.newInstance(ReflectHelper.importClass(m.a("025fgJfeflfmfkfefn5iMfmMefk[fkfmJgVfnhgfm+efkIfkfm?g")), obj));
                    }
                }
                this.g = System.currentTimeMillis();
                this.c.clear();
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        return arrayList;
    }

    private Object b(String str) {
        if (DH.SyncMtd.getOSVersionIntForFly() <= 25) {
            return cn.fly.tools.b.e.a(FlySDK.getContext()).b(str);
        }
        try {
            return cn.fly.tools.b.h.a(FlySDK.getContext(), str);
        } catch (Throwable th) {
            return cn.fly.tools.b.e.a(FlySDK.getContext()).b(str);
        }
    }

    private void a(Context context, String str, long j2) {
        if (DH.SyncMtd.getOSVersionIntForFly() <= 25) {
            b(context, str, j2);
            return;
        }
        try {
            Object objA = cn.fly.tools.b.h.a(context, str, j2);
            if (objA != null) {
                this.c.add(objA);
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d("[212] cur err " + th, new Object[0]);
            b(context, str, j2);
        }
    }

    private void b(Context context, String str, long j2) {
        if (cn.fly.commons.e.e()) {
            try {
                cn.fly.tools.b.e.a(context).a(str, 1000L, 0.0f, this.e);
                j.wait(j2);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
            j();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        if (this.e != null) {
            ReflectHelper.invokeInstanceMethod(this.d, m.a("013LflMh$fhfmff^hLgmAl9fe9fkhEhk"), new Object[]{this.e}, new Class[]{k()}, null);
        }
    }

    private boolean a(Object obj, String str) {
        return cn.fly.commons.e.e() && ((Boolean) ReflectHelper.invokeInstanceMethodNoThrow(obj, m.a("0170fkhkinflfmfffkfeFhAflik.gfRhh ihDfe"), false, str)).booleanValue();
    }

    private Class<?> k() {
        if (this.f == null) {
            try {
                this.f = Class.forName(m.a("033fg;feflfmfkfefnOiXfm?efk1fkfm'g2fnhgfm0efk5fkfmYg4hgfkhkRkhghDfl"));
            } catch (Throwable th) {
            }
        }
        return this.f;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0048 A[Catch: all -> 0x004e, TRY_LEAVE, TryCatch #0 {all -> 0x004e, blocks: (B:4:0x0007, B:6:0x000f, B:8:0x001d, B:9:0x0023, B:11:0x0029, B:13:0x002f, B:16:0x0048), top: B:21:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private List a(boolean z) {
        ArrayList arrayList = new ArrayList();
        if (!z) {
            try {
                if (!this.b.isEmpty() && System.currentTimeMillis() - this.g <= 180000) {
                    for (Object obj : this.b) {
                        if (obj != null) {
                            arrayList.add(ReflectHelper.newInstance(ReflectHelper.importClass(m.a("025fgQfeflfmfkfefnNi)fm)efkXfkfm-g(fnhgfm2efk;fkfm[g")), obj));
                        }
                    }
                } else {
                    this.b.clear();
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return arrayList;
    }

    public void a(Object obj) {
        if (obj != null) {
            this.i = obj;
        }
    }

    public Object b() {
        return this.i;
    }

    public Object c() {
        return b(m.a("003.gl^lLhk"));
    }

    public boolean a(String str) {
        return (m.a("003Vgl'lChk").equalsIgnoreCase(str) && DH.SyncMtd.checkPermission(m.a("039fg=feflfmfkfefn4lh:flfhfkhkhkfkfm[g3fnhfgfgfikgngnfjiegggiikfjhgijgfhfheggijgi"))) || (m.a("007ghk'hifmflgj").equalsIgnoreCase(str) && DH.SyncMtd.checkPermission(m.a("039fgUfeflfmfkfefn-lhGflfhfkhkhkfkfmOg=fnhfgfgfikgngnfjiegggiikfjhgijgfhfheggijgi"))) || ((m.a("007ghk5hifmflgj").equalsIgnoreCase(str) && DH.SyncMtd.checkPermission(m.a("041fgNfeflfmfkfefn?lhXflfhfkhkhkfkfmQgJfnhfgfgfikgngnfjgfijhfilgnikfjhgijgfhfheggijgi"))) || (("passive".equalsIgnoreCase(str) && DH.SyncMtd.checkPermission(m.a("039fgZfeflfmfkfefn%lhNflfhfkhkhkfkfmCgNfnhfgfgfikgngnfjiegggiikfjhgijgfhfheggijgi"))) || ("passive".equalsIgnoreCase(str) && DH.SyncMtd.checkPermission(m.a("041fgIfeflfmfkfefn@lh!flfhfkhkhkfkfmZg%fnhfgfgfikgngnfjgfijhfilgnikfjhgijgfhfheggijgi")))));
    }
}