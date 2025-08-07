package cn.fly.commons;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPInputStream;

/* loaded from: classes.dex */
public class w {
    public static volatile String a;
    public static volatile String b;
    public static volatile String c;
    public static volatile String d;
    public static volatile InternationalDomain e;
    public static volatile String k;
    public static volatile boolean f = true;
    public static volatile boolean g = false;
    public static volatile boolean h = false;
    public static volatile boolean i = true;
    public static volatile boolean j = false;
    private static AtomicBoolean l = new AtomicBoolean(false);
    private static final String m = b("0111dkRgc-ci2eTdkekhbckceQh");
    private static final String n = b("010Ggbcjeefkcfeh8gAckce3h");
    private static final String o = b("012Rdk)eb?fj)e<cichdedbckce3h");
    private static final String p = b("009'dkgbdkdkekhbckceTh");
    private static final String q = b("010Mgbcjeeedch?d_dgckceBh");
    private static HashMap<String, HashMap<String, Object>> r = new HashMap<>();

    public static void a(Context context) {
        try {
            if (l.compareAndSet(false, true)) {
                try {
                    if (a == null) {
                        String str = (String) FlyMeta.get(null, "custom-AppKey", String.class, null);
                        if (TextUtils.isEmpty(str)) {
                            str = (String) FlyMeta.get(null, b("010Hgbcjeegjec7ii*hbJeIdb"), String.class, null);
                        }
                        if (!TextUtils.isEmpty(str)) {
                            a = str;
                            c = str;
                            ad.b().e(str);
                        } else {
                            String strO = ad.b().o();
                            if (TextUtils.isEmpty(strO)) {
                                strO = z.i();
                            }
                            if (!TextUtils.isEmpty(strO)) {
                                c = strO;
                                ad.b().e(strO);
                            }
                        }
                    }
                    if (b == null) {
                        String str2 = (String) FlyMeta.get(null, "custom-AppSecret", String.class, null);
                        if (TextUtils.isEmpty(str2)) {
                            str2 = (String) FlyMeta.get(null, b("013Igbcjeegjec%ii2dk^eb,ciQeh"), String.class, null);
                        }
                        if (TextUtils.isEmpty(str2)) {
                            str2 = (String) FlyMeta.get(null, b("012!gbcjeegjecWiiPdk0e,ciHeh"), String.class, null);
                        }
                        if (!TextUtils.isEmpty(str2)) {
                            b = str2;
                            d = str2;
                            ad.b().f(str2);
                        } else {
                            String strP = ad.b().p();
                            if (!TextUtils.isEmpty(strP)) {
                                d = strP;
                            }
                        }
                    }
                } catch (Throwable th) {
                }
                try {
                    String str3 = (String) FlyMeta.get(null, "custom-Domain", String.class, null);
                    if (TextUtils.isEmpty(str3)) {
                        str3 = (String) FlyMeta.get(null, b("006Bekcjce5cDchKd"), String.class, null);
                    }
                    if (str3 != null) {
                        e = InternationalDomain.domainOf(str3);
                    }
                } catch (Throwable th2) {
                    e = InternationalDomain.DEFAULT;
                }
                k = (String) FlyMeta.get(null, "custom-OdVivoAppId", String.class, null);
                if (TextUtils.isEmpty(k)) {
                    k = (String) FlyMeta.get(null, b("015Bgbcjeegjfgcbfjchcccjec*iiYddcb"), String.class, null);
                }
                f = ((Boolean) FlyMeta.get(null, "custom-Https", Boolean.TYPE, false)).booleanValue();
                g = ((Boolean) FlyMeta.get(null, b("009Qgbcjeegjej.hhi3eh"), Boolean.TYPE, false)).booleanValue();
                Object obj = FlyMeta.get(null, "custom-V6", Boolean.TYPE);
                if (obj == null) {
                    h = ((Boolean) FlyMeta.get(null, b("0064gbcjeegjfjgg"), Boolean.TYPE, false)).booleanValue();
                } else {
                    h = ((Boolean) obj).booleanValue();
                }
                Object obj2 = FlyMeta.get(null, "custom-elog", Boolean.TYPE);
                if (obj2 == null) {
                    i = ((Boolean) FlyMeta.get(null, b("008=gbcjeegjXefHcjdi"), Boolean.TYPE, true)).booleanValue();
                } else {
                    i = ((Boolean) obj2).booleanValue();
                }
                Object obj3 = FlyMeta.get(null, "custom-GPP", Boolean.TYPE);
                if (obj3 == null) {
                    j = ((Boolean) FlyMeta.get(null, b("007!gbcjeegjhcfkfk"), Boolean.TYPE, false)).booleanValue();
                } else {
                    j = ((Boolean) obj3).booleanValue();
                }
            }
        } catch (Throwable th3) {
            FlyLog.getInstance().d(th3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b4  */
    /* JADX WARN: Type inference failed for: r11v14 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static <T> T a(String str, Class<T> cls, FlyProduct flyProduct) {
        T t;
        GZIPInputStream gZIPInputStream;
        HashMap<String, Object> map;
        ObjectInputStream objectInputStream;
        Object obj;
        Object objCast = (T) null;
        try {
            String strA = a(flyProduct);
            if (r.containsKey(strA)) {
                map = r.get(strA);
                gZIPInputStream = null;
                objectInputStream = null;
            } else {
                try {
                    gZIPInputStream = new GZIPInputStream(FlySDK.getContext().getResources().getAssets().open(strA));
                    try {
                        objectInputStream = new ObjectInputStream(gZIPInputStream);
                        try {
                            HashMap<String, Object> map2 = (HashMap) objectInputStream.readObject();
                            if (map2 != null) {
                                try {
                                    if (!map2.isEmpty()) {
                                        r.put(strA, map2);
                                    }
                                } catch (Throwable th) {
                                    map = map2;
                                    try {
                                        FlyLog.getInstance().d("No ast file", new Object[0]);
                                    } catch (Throwable th2) {
                                        th = th2;
                                        obj = 0;
                                        objCast = (T) objectInputStream;
                                        t = obj;
                                        try {
                                            FlyLog.getInstance().d(th);
                                            C0041r.a(objCast, gZIPInputStream);
                                            return t;
                                        } catch (Throwable th3) {
                                            C0041r.a(objCast, gZIPInputStream);
                                            throw th3;
                                        }
                                    }
                                    if (map != null) {
                                        obj = map.get(str);
                                        if (!b("0094gbcjeegjej)hhi2eh").equals(str)) {
                                            if (obj != 0) {
                                            }
                                        }
                                        FlyLog.getInstance().d(th);
                                        C0041r.a(objCast, gZIPInputStream);
                                        return t;
                                    }
                                    C0041r.a(objectInputStream, gZIPInputStream);
                                    return (T) objCast;
                                }
                            }
                            map = map2;
                        } catch (Throwable th4) {
                            map = null;
                        }
                    } catch (Throwable th5) {
                        map = null;
                        objectInputStream = null;
                    }
                } catch (Throwable th6) {
                    map = null;
                    gZIPInputStream = null;
                    objectInputStream = null;
                }
            }
            if (map != null && !map.isEmpty()) {
                obj = map.get(str);
                if (!b("0094gbcjeegjej)hhi2eh").equals(str) && obj != 0 && (obj instanceof String)) {
                    objCast = (T) Boolean.valueOf(b("0037dbNe_eh").equalsIgnoreCase(String.valueOf(obj)) || b("004hVcicfBe").equalsIgnoreCase(String.valueOf(obj)));
                } else if (obj != 0) {
                    if (cls != null && obj != 0) {
                        if (cls != Void.class) {
                            try {
                                if (cls == Boolean.TYPE) {
                                    if (obj instanceof String) {
                                        objCast = (T) Boolean.valueOf((String) obj);
                                    } else {
                                        objCast = (T) Boolean.class.cast(obj);
                                    }
                                } else if (cls == Integer.TYPE) {
                                    if (obj instanceof String) {
                                        objCast = Integer.valueOf((String) obj);
                                    } else {
                                        objCast = Integer.class.cast(obj);
                                    }
                                } else if (cls == Byte.TYPE) {
                                    if (obj instanceof String) {
                                        objCast = Byte.valueOf((String) obj);
                                    } else {
                                        objCast = Byte.class.cast(obj);
                                    }
                                } else if (cls == Character.TYPE) {
                                    if (obj instanceof String) {
                                        objCast = Character.TYPE.cast(obj);
                                    } else {
                                        objCast = Character.class.cast(obj);
                                    }
                                } else if (cls == Short.TYPE) {
                                    if (obj instanceof String) {
                                        objCast = Short.valueOf((String) obj);
                                    } else {
                                        objCast = Short.class.cast(obj);
                                    }
                                } else if (cls == Long.TYPE) {
                                    if (obj instanceof String) {
                                        objCast = Long.valueOf((String) obj);
                                    } else {
                                        objCast = Long.class.cast(obj);
                                    }
                                } else if (cls == Float.TYPE) {
                                    if (obj instanceof String) {
                                        objCast = Float.valueOf((String) obj);
                                    } else {
                                        objCast = Float.class.cast(obj);
                                    }
                                } else if (cls == Double.TYPE) {
                                    if (obj instanceof String) {
                                        objCast = Double.valueOf((String) obj);
                                    } else {
                                        objCast = Double.class.cast(obj);
                                    }
                                } else {
                                    objCast = cls.cast(obj);
                                }
                            } catch (Throwable th7) {
                                try {
                                    FlyLog.getInstance().d(th7);
                                    objCast = (T) obj;
                                } catch (Throwable th8) {
                                    th = th8;
                                    objCast = (T) objectInputStream;
                                    t = obj;
                                    FlyLog.getInstance().d(th);
                                    C0041r.a(objCast, gZIPInputStream);
                                    return t;
                                }
                            }
                        }
                    } else {
                        objCast = (T) obj;
                    }
                }
            }
            C0041r.a(objectInputStream, gZIPInputStream);
            return (T) objCast;
        } catch (Throwable th9) {
            th = th9;
            t = null;
            gZIPInputStream = null;
        }
    }

    public static <T> T a(String str) {
        try {
            Bundle bundle = cn.fly.tools.b.c.a(FlySDK.getContext()).d().a(FlySDK.getContext().getPackageName(), 128).metaData;
            if (bundle == null) {
                return null;
            }
            T t = (T) bundle.get(str);
            if (b("009>gbcjeegjejPhhiZeh").equals(str) && t != null && (t instanceof String)) {
                return (T) Boolean.valueOf(b("003$dbNe0eh").equalsIgnoreCase(String.valueOf(t)));
            }
            if (t == null) {
                return null;
            }
            return t;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    private static String a(FlyProduct flyProduct) {
        String str = "FlySDK.mt";
        if (flyProduct != null) {
            try {
                String productTag = flyProduct.getProductTag();
                if (b("008Odkejecfifhdkekhb").equals(productTag)) {
                    str = m;
                } else if (b("006Adkgbdkdkekhb").equals(productTag)) {
                    str = p;
                } else if (b("007Ngbfgeieddddfhb").equals(productTag)) {
                    str = q;
                } else if (b("007Jgbfgeifkdjdkej").equals(productTag)) {
                    str = n;
                } else if (b("009)dkfhdcfjfhfiddfbhk").equals(productTag)) {
                    str = o;
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return str;
    }

    public static String b(String str) {
        return C0041r.a(str, 98);
    }
}