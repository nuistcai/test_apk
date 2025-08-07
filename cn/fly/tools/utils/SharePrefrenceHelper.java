package cn.fly.tools.utils;

import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;
import cn.fly.commons.C0041r;
import cn.fly.commons.m;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.tools.utils.FlyPersistence;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class SharePrefrenceHelper implements PublicMemberKeeper {
    public static final String SP_CACHE_FOLDER = m.a("0058ieffffgn:l");
    private Context a;
    private volatile FlyPersistence b;

    public SharePrefrenceHelper(Context context) {
        if (context != null) {
            this.a = context.getApplicationContext();
        }
    }

    void a(String str, int i) {
        String str2 = str + "_" + i;
        FlyLog.getInstance().d("[CKCMP] mpf ck os, f: " + str2, new Object[0]);
        h hVar = new h(this.a, "ovsp_" + str2);
        if (a(hVar) && FlyPersistence.b(this.a, str2)) {
            hVar.a();
        }
    }

    public void open(String str) {
        open(str, 0);
    }

    public void open(String str, int i) {
        open(str, i, null);
    }

    public void open(String str, int i, String str2) {
        String str3 = str + "_" + i;
        this.b = new FlyPersistence(this.a, str3, str2);
        a(str3);
    }

    public static boolean isMbSpFileExist(Context context, String str, int i) {
        return a.a(context, str + "_" + i);
    }

    public static boolean isMpfFileExist(Context context, String str, int i) {
        return FlyPersistence.a(context, str + "_" + i);
    }

    public void putString(String str, String str2) {
        putString(str, str2, 0L);
    }

    public void putString(String str, String str2, long j) {
        if (this.b != null) {
            try {
                this.b.a(new FlyPersistence.j(str, str2, j));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public String getString(String str) {
        return getString(str, "");
    }

    public String getString(String str, String str2) {
        try {
            return getStringThrowable(str, str2);
        } catch (FlyPersistence.NoValidDataException e) {
            return str2;
        }
    }

    public String getStringThrowable(String str) throws FlyPersistence.NoValidDataException {
        return getStringThrowable(str, "");
    }

    public String getStringThrowable(String str, String str2) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                String str3 = (String) this.b.a(new FlyPersistence.e(str));
                return TextUtils.isEmpty(str3) ? str2 : str3;
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return str2;
    }

    public void putBoolean(String str, Boolean bool) {
        putBoolean(str, bool, 0L);
    }

    public void putBoolean(String str, Boolean bool, long j) {
        if (this.b != null && bool != null) {
            try {
                this.b.a(new FlyPersistence.j(str, Byte.valueOf((byte) (bool.booleanValue() ? 1 : 0)), j));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public boolean getBoolean(String str, boolean z) {
        try {
            return getBooleanThrowable(str, z);
        } catch (FlyPersistence.NoValidDataException e) {
            return z;
        }
    }

    public boolean getBooleanThrowable(String str) throws FlyPersistence.NoValidDataException {
        return getBooleanThrowable(str, false);
    }

    public boolean getBooleanThrowable(String str, boolean z) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                Object objA = this.b.a((FlyPersistence.e<Object>) new FlyPersistence.e(str));
                if (objA != null) {
                    if (objA instanceof Boolean) {
                        return ((Boolean) objA).booleanValue();
                    }
                    return ((Number) objA).byteValue() == 1;
                }
                return z;
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return z;
    }

    public void putLong(String str, Long l) {
        putLong(str, l, 0L);
    }

    public void putLong(String str, Long l, long j) {
        if (this.b != null && l != null) {
            try {
                this.b.a(new FlyPersistence.j(str, l, j));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public long getLong(String str) {
        return getLong(str, 0L);
    }

    public long getLong(String str, long j) {
        try {
            return getLongThrowable(str, j);
        } catch (FlyPersistence.NoValidDataException e) {
            return j;
        }
    }

    public long getLongThrowable(String str) throws FlyPersistence.NoValidDataException {
        return getLongThrowable(str, 0L);
    }

    public long getLongThrowable(String str, long j) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                Long l = (Long) this.b.a(new FlyPersistence.e(str));
                return l == null ? j : l.longValue();
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return j;
    }

    public void putInt(String str, Integer num) {
        putInt(str, num, 0L);
    }

    public void putInt(String str, Integer num, long j) {
        if (this.b != null && num != null) {
            try {
                this.b.a(new FlyPersistence.j(str, num, j));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public int getInt(String str) {
        return getInt(str, 0);
    }

    public int getInt(String str, int i) {
        try {
            return getIntThrowable(str, i);
        } catch (FlyPersistence.NoValidDataException e) {
            return i;
        }
    }

    public int getIntThrowable(String str) throws FlyPersistence.NoValidDataException {
        return getIntThrowable(str, 0);
    }

    public int getIntThrowable(String str, int i) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                Integer num = (Integer) this.b.a(new FlyPersistence.e(str));
                return num == null ? i : num.intValue();
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return i;
    }

    public void putDouble(String str, Double d) {
        putDouble(str, d, 0L);
    }

    public void putDouble(String str, Double d, long j) {
        if (this.b != null && d != null) {
            try {
                this.b.a(new FlyPersistence.j(str, d, j));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public double getDouble(String str) {
        return getDouble(str, 0.0d);
    }

    public double getDouble(String str, double d) {
        try {
            return getDoubleThrowable(str, d);
        } catch (FlyPersistence.NoValidDataException e) {
            return d;
        }
    }

    public double getDoubleThrowable(String str) throws FlyPersistence.NoValidDataException {
        return getDoubleThrowable(str, 0.0d);
    }

    public double getDoubleThrowable(String str, double d) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                Double d2 = (Double) this.b.a(new FlyPersistence.e(str));
                return d2 == null ? d : d2.doubleValue();
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return d;
    }

    public void putParcel(String str, Parcelable parcelable) {
        putParcel(str, parcelable, 0L);
    }

    public void putParcel(String str, Parcelable parcelable, long j) {
        if (this.b != null) {
            try {
                this.b.a(new FlyPersistence.j(str, parcelable, j) { // from class: cn.fly.tools.utils.SharePrefrenceHelper.1
                    @Override // cn.fly.tools.utils.FlyPersistence.j
                    public Object c() {
                        Object objB = b();
                        if (objB != null) {
                            return new FlyPersistence.b((Parcelable) objB).b();
                        }
                        return null;
                    }
                });
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public <T extends Parcelable> T getParcel(String str, Class<T> cls) {
        return (T) getParcel(str, cls, null);
    }

    public <T extends Parcelable> T getParcel(String str, Class<T> cls, T t) {
        try {
            return (T) getParcelThrowable(str, cls, t);
        } catch (FlyPersistence.NoValidDataException e) {
            return t;
        }
    }

    public <T extends Parcelable> T getParcelThrowable(String str, Class<T> cls) throws FlyPersistence.NoValidDataException {
        return (T) getParcelThrowable(str, cls, null);
    }

    public <T> T getParcelThrowable(String str, Class<T> cls, final T t) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                T t2 = (T) this.b.a(new FlyPersistence.e<T>(str) { // from class: cn.fly.tools.utils.SharePrefrenceHelper.2
                    @Override // cn.fly.tools.utils.FlyPersistence.e
                    public T a(Object obj) {
                        if (obj != null) {
                            return (T) FlyPersistence.b.a((HashMap<Byte, Object>) obj).a((FlyPersistence.b) t);
                        }
                        return (T) t;
                    }
                });
                if (t2 != null) {
                    return t2;
                }
                return t;
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return t;
    }

    public <T extends Parcelable> void putParcelMap(String str, Map<String, T> map) {
        putParcelMap(str, map, 0L);
    }

    public <T extends Parcelable> void putParcelMap(String str, Map<String, T> map, long j) {
        if (this.b != null && map != null && !map.isEmpty()) {
            try {
                this.b.a(new FlyPersistence.j(str, map, j) { // from class: cn.fly.tools.utils.SharePrefrenceHelper.3
                    @Override // cn.fly.tools.utils.FlyPersistence.j
                    public Object c() {
                        Map map2;
                        Object objB = b();
                        if (objB != null) {
                            if (objB instanceof HashMap) {
                                map2 = new HashMap();
                            } else if (objB instanceof Hashtable) {
                                map2 = new Hashtable();
                            } else if (objB instanceof TreeMap) {
                                map2 = new TreeMap();
                            } else {
                                map2 = new HashMap();
                            }
                            for (Map.Entry entry : ((Map) objB).entrySet()) {
                                map2.put(entry.getKey(), new FlyPersistence.b((Parcelable) entry.getValue()).b());
                            }
                            return map2;
                        }
                        return null;
                    }
                });
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public <T extends Parcelable> Map<String, T> getParcelMap(String str, Class<T> cls) {
        return getParcelMap(str, cls, null);
    }

    public <T extends Parcelable> Map<String, T> getParcelMap(String str, Class<T> cls, Map<String, T> map) {
        try {
            return getParcelMapThrowable(str, cls, map);
        } catch (FlyPersistence.NoValidDataException e) {
            return map;
        }
    }

    public <T extends Parcelable> Map<String, T> getParcelMapThrowable(String str, Class<T> cls) throws FlyPersistence.NoValidDataException {
        return getParcelMapThrowable(str, cls, null);
    }

    public <T extends Parcelable> Map<String, T> getParcelMapThrowable(String str, Class<T> cls, final Map<String, T> map) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                Map<String, T> map2 = (Map) this.b.a((FlyPersistence.e) new FlyPersistence.e<Map<String, T>>(str) { // from class: cn.fly.tools.utils.SharePrefrenceHelper.4
                    @Override // cn.fly.tools.utils.FlyPersistence.e
                    /* renamed from: b, reason: merged with bridge method [inline-methods] */
                    public Map<String, T> a(Object obj) {
                        HashMap map3;
                        if (obj != null) {
                            Map map4 = (Map) obj;
                            if (map4 instanceof HashMap) {
                                map3 = new HashMap();
                            } else if (map4 instanceof Hashtable) {
                                map3 = new Hashtable();
                            } else if (map4 instanceof TreeMap) {
                                map3 = new TreeMap();
                            } else {
                                map3 = new HashMap();
                            }
                            for (Map.Entry entry : map4.entrySet()) {
                                map3.put(entry.getKey(), FlyPersistence.b.a((HashMap<Byte, Object>) entry.getValue()).a((FlyPersistence.b) null));
                            }
                            return (Map<String, T>) map3;
                        }
                        return map;
                    }
                });
                if (map2 != null) {
                    return map2;
                }
                return map;
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return map;
    }

    public <T extends Parcelable> void putParcelList(String str, List<T> list) {
        putParcelList(str, list, 0L);
    }

    public <T extends Parcelable> void putParcelList(String str, List<T> list, long j) {
        if (this.b != null && list != null && !list.isEmpty()) {
            this.b.a(new FlyPersistence.j(str, list, j) { // from class: cn.fly.tools.utils.SharePrefrenceHelper.5
                @Override // cn.fly.tools.utils.FlyPersistence.j
                public Object c() {
                    List arrayList;
                    Object objB = b();
                    if (objB != null) {
                        if (!(objB instanceof ArrayList) && (objB instanceof LinkedList)) {
                            arrayList = new LinkedList();
                        } else {
                            arrayList = new ArrayList();
                        }
                        Iterator it = ((List) objB).iterator();
                        while (it.hasNext()) {
                            arrayList.add(new FlyPersistence.b((Parcelable) it.next()).b());
                        }
                        return arrayList;
                    }
                    return null;
                }
            });
        }
    }

    public <T extends Parcelable> List<T> getParcelList(String str, Class<T> cls) {
        return getParcelList(str, cls, null);
    }

    public <T extends Parcelable> List<T> getParcelList(String str, Class<T> cls, List<T> list) {
        try {
            return getParcelListThrowable(str, cls, list);
        } catch (FlyPersistence.NoValidDataException e) {
            return list;
        }
    }

    public <T extends Parcelable> List<T> getParcelListThrowable(String str, Class<T> cls) throws FlyPersistence.NoValidDataException {
        return getParcelListThrowable(str, cls, null);
    }

    public <T extends Parcelable> List<T> getParcelListThrowable(String str, Class<T> cls, final List<T> list) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                List<T> list2 = (List) this.b.a((FlyPersistence.e) new FlyPersistence.e<List<T>>(str) { // from class: cn.fly.tools.utils.SharePrefrenceHelper.6
                    @Override // cn.fly.tools.utils.FlyPersistence.e
                    /* renamed from: b, reason: merged with bridge method [inline-methods] */
                    public List<T> a(Object obj) {
                        ArrayList arrayList;
                        if (obj != null) {
                            List list3 = (List) obj;
                            if (!(list3 instanceof ArrayList) && (list3 instanceof LinkedList)) {
                                arrayList = new LinkedList();
                            } else {
                                arrayList = new ArrayList();
                            }
                            Iterator it = list3.iterator();
                            while (it.hasNext()) {
                                arrayList.add(FlyPersistence.b.a((HashMap<Byte, Object>) it.next()).a((FlyPersistence.b) null));
                            }
                            return arrayList;
                        }
                        return list;
                    }
                });
                if (list2 != null) {
                    return list2;
                }
                return list;
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return list;
    }

    public <T extends Parcelable> void putParcelArray(String str, T[] tArr) {
        putParcelArray(str, tArr, 0L);
    }

    public <T extends Parcelable> void putParcelArray(String str, T[] tArr, long j) {
        if (this.b != null && tArr != null && tArr.length > 0) {
            try {
                this.b.a(new FlyPersistence.j(str, tArr, j) { // from class: cn.fly.tools.utils.SharePrefrenceHelper.7
                    @Override // cn.fly.tools.utils.FlyPersistence.j
                    public Object c() {
                        Object objB = b();
                        if (objB != null) {
                            Parcelable[] parcelableArr = (Parcelable[]) objB;
                            int length = parcelableArr.length;
                            HashMap[] mapArr = new HashMap[length];
                            for (int i = 0; i < length; i++) {
                                mapArr[i] = new FlyPersistence.b(parcelableArr[i]).b();
                            }
                            return mapArr;
                        }
                        return null;
                    }
                });
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public <T extends Parcelable> T[] getParcelArray(String str, Class<T> cls) {
        return (T[]) getParcelArray(str, cls, null);
    }

    public <T extends Parcelable> T[] getParcelArray(String str, Class<T> cls, T[] tArr) {
        try {
            return (T[]) getParcelArrayThrowable(str, cls, tArr);
        } catch (FlyPersistence.NoValidDataException e) {
            return tArr;
        }
    }

    public <T extends Parcelable> T[] getParcelArrayThrowable(String str, Class<T> cls) throws FlyPersistence.NoValidDataException {
        return (T[]) getParcelArrayThrowable(str, cls, null);
    }

    public <T extends Parcelable> T[] getParcelArrayThrowable(String str, final Class<T> cls, final T[] tArr) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                T[] tArr2 = (T[]) ((Parcelable[]) this.b.a((FlyPersistence.e) new FlyPersistence.e<T[]>(str) { // from class: cn.fly.tools.utils.SharePrefrenceHelper.8
                    /* JADX WARN: Incorrect return type in method signature: (Ljava/lang/Object;)[TT; */
                    @Override // cn.fly.tools.utils.FlyPersistence.e
                    /* renamed from: b, reason: merged with bridge method [inline-methods] */
                    public Parcelable[] a(Object obj) {
                        if (obj != null) {
                            HashMap[] mapArr = (HashMap[]) obj;
                            Parcelable[] parcelableArr = (Parcelable[]) Array.newInstance((Class<?>) cls, mapArr.length);
                            for (int i = 0; i < parcelableArr.length; i++) {
                                parcelableArr[i] = FlyPersistence.b.a((HashMap<Byte, Object>) mapArr[i]).a((FlyPersistence.b) null);
                            }
                            return parcelableArr;
                        }
                        return tArr;
                    }
                }));
                if (tArr2 != null) {
                    return tArr2;
                }
                return tArr;
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return tArr;
    }

    @Deprecated
    public void putObj(String str, Object obj) {
        if (obj == null && this.b != null) {
            remove(str);
        } else if (this.b != null) {
            put(str, obj);
        }
    }

    @Deprecated
    public Object getObj(String str, Object obj) {
        return get(str, obj);
    }

    public void put(String str, Object obj) {
        put(str, obj, 0L);
    }

    public void put(String str, Object obj, long j) {
        if (this.b != null) {
            try {
                this.b.a(new FlyPersistence.j(str, obj, j));
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    @Deprecated
    public HashMap<String, Object> getAll() {
        if (this.b != null) {
            try {
                return this.b.b();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return new HashMap<>();
    }

    public Object get(String str) {
        return get(str, null);
    }

    public Object get(String str, Object obj) {
        try {
            return getThrowable(str, obj);
        } catch (FlyPersistence.NoValidDataException e) {
            return obj;
        }
    }

    public Object getThrowable(String str) throws FlyPersistence.NoValidDataException {
        return getThrowable(str, null);
    }

    public Object getThrowable(String str, final Object obj) throws FlyPersistence.NoValidDataException {
        if (this.b != null) {
            try {
                Object objA = this.b.a(new FlyPersistence.e<Object>(str) { // from class: cn.fly.tools.utils.SharePrefrenceHelper.9
                    @Override // cn.fly.tools.utils.FlyPersistence.e
                    public Object a(Object obj2) {
                        if (obj2 != null) {
                            if ((obj2 instanceof String) && SharePrefrenceHelper.this.b((String) obj2)) {
                                try {
                                    return SharePrefrenceHelper.this.a(Base64.decode((String) obj2, 2));
                                } catch (Throwable th) {
                                    FlyLog.getInstance().d("Expected exc: " + th.getMessage(), new Object[0]);
                                    return obj2;
                                }
                            }
                            return obj2;
                        }
                        return obj;
                    }
                });
                if (objA != null) {
                    if ((objA instanceof String) && b((String) objA)) {
                        try {
                            return a(Base64.decode((String) objA, 2));
                        } catch (Throwable th) {
                            FlyLog.getInstance().d("Expected exc: " + th.getMessage(), new Object[0]);
                            return objA;
                        }
                    }
                    return objA;
                }
                return obj;
            } catch (FlyPersistence.NoValidDataException e) {
                throw e;
            } catch (Throwable th2) {
                FlyLog.getInstance().d(th2);
            }
        }
        return obj;
    }

    @Deprecated
    public void putAll(HashMap<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void remove(String str) {
        if (this.b != null) {
            try {
                this.b.a(str);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    public void clear() {
        if (this.b != null) {
            try {
                this.b.a();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    private boolean a(h hVar) {
        try {
            String strC = c(hVar);
            String strB = b();
            int iB = b(hVar);
            int iA = a();
            FlyLog.getInstance().d("[CKCMP] mpf ck os, c_cn: " + strC + ", r_cn: " + strB + ", c_ov: " + iB + ", r_ov: " + iA, new Object[0]);
            return (strC.equals(strB) && iB == iA) ? false : true;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return false;
        }
    }

    private int b(h hVar) {
        int iA = 0;
        try {
            int iB = hVar.b("key_o_verin", 0);
            if (iB == 0) {
                iA = a();
                hVar.a("key_o_verin", iA);
                return iA;
            }
            return iB;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return iA;
        }
    }

    private int a() {
        return Build.VERSION.SDK_INT;
    }

    private String c(h hVar) {
        String strB = "";
        try {
            String strB2 = hVar.b("key_o_cdnm", "");
            if (strB2.equals("")) {
                strB = b();
                hVar.a("key_o_cdnm", strB);
                return strB;
            }
            return strB2;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return strB;
        }
    }

    private String b() {
        return cn.fly.tools.b.e.a(this.a).a("ro.build.version.release_or_codename");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object a(byte[] bArr) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        Throwable th;
        ObjectInputStream objectInputStream;
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        try {
            byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                objectInputStream = new ObjectInputStream(byteArrayInputStream);
            } catch (Throwable th2) {
                th = th2;
                objectInputStream = null;
            }
        } catch (Throwable th3) {
            byteArrayInputStream = null;
            th = th3;
            objectInputStream = null;
        }
        try {
            Object object = objectInputStream.readObject();
            C0041r.a(objectInputStream, byteArrayInputStream);
            return object;
        } catch (Throwable th4) {
            th = th4;
            C0041r.a(objectInputStream, byteArrayInputStream);
            throw th;
        }
    }

    private void a(String str) {
        HashMap<String, Object> mapA;
        if (!getBoolean("k_m_sp_cpt_dn") && a.a(this.a, str)) {
            FlyLog.getInstance().d("[MPF][" + str + "]Compat acquire", new Object[0]);
            a aVar = new a(this.a, str);
            if (this.b != null) {
                mapA = aVar.a();
                if (mapA != null && !mapA.isEmpty()) {
                    putAll(mapA);
                }
                putBoolean("k_m_sp_cpt_dn", true);
            } else {
                mapA = null;
            }
            FlyLog.getInstance().d("[MPF][" + str + "]Compat done, mv: " + (mapA != null ? Integer.valueOf(mapA.size()) : null), new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str) {
        try {
            return Pattern.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$", str);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return false;
        }
    }

    private static final class a {
        private static File c;
        private File a;
        private HashMap<String, Object> b = new HashMap<>();

        public a(Context context, String str) {
            if (context != null) {
                try {
                    this.a = new File(a(context), str);
                    if (!this.a.getParentFile().exists()) {
                        this.a.getParentFile().mkdirs();
                    }
                    if (!this.a.exists()) {
                        this.a.createNewFile();
                    }
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                    return;
                }
            }
            b();
        }

        private void b() {
            InputStreamReader inputStreamReader;
            BufferedReader bufferedReader;
            Throwable th;
            FileInputStream fileInputStream;
            synchronized (this.b) {
                if (this.a != null && this.a.exists()) {
                    try {
                        fileInputStream = new FileInputStream(this.a);
                        try {
                            inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
                            try {
                                bufferedReader = new BufferedReader(inputStreamReader);
                                try {
                                    StringBuilder sb = new StringBuilder();
                                    for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                                        if (sb.length() > 0) {
                                            sb.append("\n");
                                        }
                                        sb.append(line);
                                    }
                                    this.b = HashonHelper.fromJson(sb.toString());
                                    C0041r.a(bufferedReader, inputStreamReader, fileInputStream);
                                } catch (Throwable th2) {
                                    th = th2;
                                    try {
                                        FlyLog.getInstance().w(th);
                                        C0041r.a(bufferedReader, inputStreamReader, fileInputStream);
                                    } catch (Throwable th3) {
                                        C0041r.a(bufferedReader, inputStreamReader, fileInputStream);
                                        throw th3;
                                    }
                                }
                            } catch (Throwable th4) {
                                bufferedReader = null;
                                th = th4;
                            }
                        } catch (Throwable th5) {
                            bufferedReader = null;
                            th = th5;
                            inputStreamReader = null;
                        }
                    } catch (Throwable th6) {
                        inputStreamReader = null;
                        bufferedReader = null;
                        th = th6;
                        fileInputStream = null;
                    }
                }
            }
        }

        private static synchronized File a(Context context) {
            if (c == null) {
                c = new File(context.getFilesDir(), SharePrefrenceHelper.SP_CACHE_FOLDER);
            }
            return c;
        }

        public static synchronized boolean a(Context context, String str) {
            return new File(a(context), str).exists();
        }

        public HashMap<String, Object> a() {
            HashMap<String, Object> map;
            synchronized (this.b) {
                map = new HashMap<>();
                map.putAll(this.b);
            }
            return map;
        }
    }
}