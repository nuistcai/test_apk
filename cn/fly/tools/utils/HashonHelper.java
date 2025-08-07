package cn.fly.tools.utils;

import cn.fly.commons.n;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.tools.utils.FlyPersistence;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.text.Typography;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class HashonHelper implements PublicMemberKeeper {

    public interface a {
        Object a();
    }

    public static <T> HashMap<String, T> fromJson(String str) {
        if (str == null || str.isEmpty()) {
            return new HashMap<>();
        }
        try {
            if (str.startsWith("[") && str.endsWith("]")) {
                str = "{\"fakelist\":" + str + "}";
            }
            return a(new JSONObject(str));
        } catch (Throwable th) {
            FlyLog.getInstance().w(str);
            FlyLog.getInstance().w(th);
            return new HashMap<>();
        }
    }

    private static <T> HashMap<String, T> a(JSONObject jSONObject) throws Throwable {
        FlyPersistence.h.AnonymousClass1 anonymousClass1 = (HashMap<String, T>) new HashMap();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object objOpt = jSONObject.opt(next);
            if (JSONObject.NULL.equals(objOpt)) {
                objOpt = null;
            }
            if (objOpt != null) {
                if (objOpt instanceof JSONObject) {
                    objOpt = a((JSONObject) objOpt);
                } else if (objOpt instanceof JSONArray) {
                    objOpt = a((JSONArray) objOpt);
                }
                anonymousClass1.put(next, objOpt);
            }
        }
        return anonymousClass1;
    }

    private static ArrayList<Object> a(JSONArray jSONArray) throws Throwable {
        ArrayList<Object> arrayList = new ArrayList<>();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            Object objOpt = jSONArray.opt(i);
            if (objOpt instanceof JSONObject) {
                objOpt = a((JSONObject) objOpt);
            } else if (objOpt instanceof JSONArray) {
                objOpt = a((JSONArray) objOpt);
            }
            arrayList.add(objOpt);
        }
        return arrayList;
    }

    public static <T> String fromHashMap(HashMap<String, T> map) {
        try {
            JSONObject jSONObjectA = a((HashMap) map);
            if (jSONObjectA == null) {
                return "";
            }
            return jSONObjectA.toString();
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return "";
        }
    }

    private static <T> JSONObject a(HashMap<String, T> map) throws Throwable {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof HashMap) {
                value = a((HashMap) value);
            } else if (value instanceof ArrayList) {
                value = a((ArrayList<Object>) value);
            } else if (a(value)) {
                value = a((ArrayList<Object>) b(value));
            }
            jSONObject.put(entry.getKey(), value);
        }
        return jSONObject;
    }

    private static boolean a(Object obj) {
        return (obj instanceof byte[]) || (obj instanceof short[]) || (obj instanceof int[]) || (obj instanceof long[]) || (obj instanceof float[]) || (obj instanceof double[]) || (obj instanceof char[]) || (obj instanceof boolean[]) || (obj instanceof String[]);
    }

    private static ArrayList<?> b(Object obj) {
        int i = 0;
        if (obj instanceof byte[]) {
            ArrayList<?> arrayList = new ArrayList<>();
            byte[] bArr = (byte[]) obj;
            int length = bArr.length;
            while (i < length) {
                arrayList.add(Byte.valueOf(bArr[i]));
                i++;
            }
            return arrayList;
        }
        if (obj instanceof short[]) {
            ArrayList<?> arrayList2 = new ArrayList<>();
            short[] sArr = (short[]) obj;
            int length2 = sArr.length;
            while (i < length2) {
                arrayList2.add(Short.valueOf(sArr[i]));
                i++;
            }
            return arrayList2;
        }
        if (obj instanceof int[]) {
            ArrayList<?> arrayList3 = new ArrayList<>();
            int[] iArr = (int[]) obj;
            int length3 = iArr.length;
            while (i < length3) {
                arrayList3.add(Integer.valueOf(iArr[i]));
                i++;
            }
            return arrayList3;
        }
        if (obj instanceof long[]) {
            ArrayList<?> arrayList4 = new ArrayList<>();
            long[] jArr = (long[]) obj;
            int length4 = jArr.length;
            while (i < length4) {
                arrayList4.add(Long.valueOf(jArr[i]));
                i++;
            }
            return arrayList4;
        }
        if (obj instanceof float[]) {
            ArrayList<?> arrayList5 = new ArrayList<>();
            float[] fArr = (float[]) obj;
            int length5 = fArr.length;
            while (i < length5) {
                arrayList5.add(Float.valueOf(fArr[i]));
                i++;
            }
            return arrayList5;
        }
        if (obj instanceof double[]) {
            ArrayList<?> arrayList6 = new ArrayList<>();
            double[] dArr = (double[]) obj;
            int length6 = dArr.length;
            while (i < length6) {
                arrayList6.add(Double.valueOf(dArr[i]));
                i++;
            }
            return arrayList6;
        }
        if (obj instanceof char[]) {
            ArrayList<?> arrayList7 = new ArrayList<>();
            char[] cArr = (char[]) obj;
            int length7 = cArr.length;
            while (i < length7) {
                arrayList7.add(Character.valueOf(cArr[i]));
                i++;
            }
            return arrayList7;
        }
        if (obj instanceof boolean[]) {
            ArrayList<?> arrayList8 = new ArrayList<>();
            boolean[] zArr = (boolean[]) obj;
            int length8 = zArr.length;
            while (i < length8) {
                arrayList8.add(Boolean.valueOf(zArr[i]));
                i++;
            }
            return arrayList8;
        }
        if (obj instanceof String[]) {
            return new ArrayList<>(Arrays.asList((String[]) obj));
        }
        return null;
    }

    private static JSONArray a(ArrayList<Object> arrayList) throws Throwable {
        JSONArray jSONArray = new JSONArray();
        Iterator<Object> it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof HashMap) {
                next = a((HashMap) next);
            } else if (next instanceof ArrayList) {
                next = a((ArrayList<Object>) next);
            }
            jSONArray.put(next);
        }
        return jSONArray;
    }

    public static String format(String str) {
        try {
            return a("", (HashMap<String, Object>) fromJson(str));
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return "";
        }
    }

    private static String a(String str, HashMap<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        String str2 = str + "\t";
        int i = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append(str2).append(Typography.quote).append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof HashMap) {
                sb.append(a(str2, (HashMap<String, Object>) value));
            } else if (value instanceof ArrayList) {
                sb.append(a(str2, (ArrayList<Object>) value));
            } else if (value instanceof String) {
                sb.append(Typography.quote).append(value).append(Typography.quote);
            } else {
                sb.append(value);
            }
            i++;
        }
        sb.append('\n').append(str).append('}');
        return sb.toString();
    }

    private static String a(String str, ArrayList<Object> arrayList) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        String str2 = str + "\t";
        Iterator<Object> it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append(str2);
            if (next instanceof HashMap) {
                sb.append(a(str2, (HashMap<String, Object>) next));
            } else if (next instanceof ArrayList) {
                sb.append(a(str2, (ArrayList<Object>) next));
            } else if (next instanceof String) {
                sb.append(Typography.quote).append(next).append(Typography.quote);
            } else {
                sb.append(next);
            }
            i++;
        }
        sb.append('\n').append(str).append(']');
        return sb.toString();
    }

    public static String fromObject(Object obj) {
        Object objC;
        try {
            objC = c(obj);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            objC = null;
        }
        if (objC == null) {
            return "";
        }
        if (objC instanceof ArrayList) {
            HashMap map = new HashMap();
            map.put(n.a("004e<bgdg'g"), objC);
            return fromHashMap(map).substring("{\"list\":".length(), r2.length() - 1).trim();
        }
        return fromHashMap((HashMap) objC);
    }

    private static Object c(Object obj) throws Throwable {
        if (obj == null || obj.getClass().isPrimitive() || (obj instanceof String) || (obj instanceof Number) || (obj instanceof Character) || (obj instanceof Boolean)) {
            return obj;
        }
        if (obj instanceof a) {
            return c(((a) obj).a());
        }
        if (obj instanceof Enum) {
            HashMap map = new HashMap();
            map.put(n.a("004dc%bebd"), ((Enum) obj).name());
            return map;
        }
        if (obj.getClass().isArray()) {
            ArrayList arrayList = new ArrayList();
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                arrayList.add(c(Array.get(obj, i)));
            }
            return arrayList;
        }
        if (obj instanceof Collection) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                arrayList2.add(c(it.next()));
            }
            return arrayList2;
        }
        if (obj instanceof Map) {
            HashMap map2 = new HashMap();
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                Object key = entry.getKey();
                if (key instanceof String) {
                    map2.put((String) key, c(entry.getValue()));
                }
            }
            return map2;
        }
        ArrayList arrayList3 = new ArrayList();
        for (Class<?> superclass = obj.getClass(); !superclass.equals(Object.class); superclass = superclass.getSuperclass()) {
            arrayList3.add(0, superclass);
        }
        ArrayList arrayList4 = new ArrayList();
        Iterator it2 = arrayList3.iterator();
        while (it2.hasNext()) {
            for (Field field : ((Class) it2.next()).getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && !field.getName().contains("$")) {
                    arrayList4.add(field);
                }
            }
        }
        HashMap map3 = new HashMap();
        Iterator it3 = arrayList4.iterator();
        while (it3.hasNext()) {
            Field field2 = (Field) it3.next();
            field2.setAccessible(true);
            map3.put(field2.getName(), c(field2.get(obj)));
        }
        return map3;
    }

    public static <T> T fromJson(String str, Class<T> cls) {
        Type[] actualTypeArguments;
        HashMap mapFromJson = fromJson(str);
        Object obj = mapFromJson;
        if (str.startsWith("[")) {
            obj = mapFromJson;
            if (str.endsWith("]")) {
                obj = mapFromJson.get(n.a("008'cd5bScf]de[bgdg7g"));
            }
        }
        try {
            Type genericSuperclass = cls.getGenericSuperclass();
            if (!(genericSuperclass instanceof ParameterizedType)) {
                actualTypeArguments = null;
            } else {
                actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            }
            return (T) a(obj, cls, actualTypeArguments);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    /* JADX WARN: Type inference failed for: r9v11, types: [T, java.util.Collection] */
    /* JADX WARN: Type inference failed for: r9v9, types: [T, java.util.Map] */
    private static <T> T a(Object obj, Class<T> cls, Type[] typeArr) throws Throwable {
        Type[] actualTypeArguments;
        Field declaredField;
        Type type;
        Type type2;
        Object objA;
        Object objA2;
        Type type3;
        int i = 0;
        if (cls.isPrimitive() || Number.class.isAssignableFrom(cls) || cls.equals(Character.class)) {
            if (cls.equals(Boolean.TYPE) || cls.equals(Boolean.class)) {
                return (T) Boolean.valueOf(n.a("004gFbhbeEd").equals(String.valueOf(obj)));
            }
            if (cls.equals(Character.TYPE) || cls.equals(Character.class)) {
                return (T) Character.valueOf(String.valueOf(obj).charAt(0));
            }
            if (cls.equals(Byte.TYPE) || cls.equals(Byte.class)) {
                return (T) Byte.valueOf(String.valueOf(obj));
            }
            if (cls.equals(Short.TYPE) || cls.equals(Short.class)) {
                return (T) Short.valueOf(String.valueOf(obj));
            }
            if (cls.equals(Integer.TYPE) || cls.equals(Integer.class)) {
                return (T) Integer.valueOf(String.valueOf(obj));
            }
            if (cls.equals(Long.TYPE) || cls.equals(Long.class)) {
                return (T) Long.valueOf(String.valueOf(obj));
            }
            if (cls.equals(Float.TYPE) || cls.equals(Float.class)) {
                return (T) Float.valueOf(String.valueOf(obj));
            }
            return (T) Double.valueOf(String.valueOf(obj));
        }
        if (a.class.isAssignableFrom(cls)) {
            try {
                return (T) ReflectHelper.invokeStaticMethod(ReflectHelper.importClass(cls.getName()), n.a("0077bbGbe beQdBefcd"), obj);
            } catch (Throwable th) {
                return null;
            }
        }
        if (cls.equals(String.class) || cls.equals(Boolean.class)) {
            return obj;
        }
        if (cls.isEnum()) {
            return (T) Enum.valueOf(cls, String.valueOf(((HashMap) obj).get(n.a("004dcZbebd"))));
        }
        if (cls.isArray()) {
            ArrayList arrayList = (ArrayList) obj;
            Class<?> componentType = cls.getComponentType();
            T t = (T) Array.newInstance(componentType, arrayList.size());
            int size = arrayList.size();
            while (i < size) {
                Array.set(t, i, a(arrayList.get(i), componentType, null));
                i++;
            }
            return t;
        }
        if (Collection.class.isAssignableFrom(cls)) {
            ?? r9 = (T) ((Collection) cls.newInstance());
            if (typeArr != null && typeArr.length > 0) {
                type3 = typeArr[0];
            } else {
                type3 = null;
            }
            ArrayList arrayList2 = (ArrayList) obj;
            int size2 = arrayList2.size();
            while (i < size2) {
                if (type3 != null && (type3 instanceof Class) && !type3.equals(Object.class)) {
                    r9.add(a(arrayList2.get(i), (Class) type3, null));
                } else if (type3 != null && (type3 instanceof ParameterizedType)) {
                    ParameterizedType parameterizedType = (ParameterizedType) type3;
                    r9.add(a(arrayList2.get(i), (Class) parameterizedType.getRawType(), parameterizedType.getActualTypeArguments()));
                } else {
                    r9.add(arrayList2.get(i));
                }
                i++;
            }
            return r9;
        }
        if (Map.class.isAssignableFrom(cls)) {
            ?? r92 = (T) ((Map) cls.newInstance());
            if (typeArr != null && typeArr.length > 1) {
                type2 = typeArr[0];
                type = typeArr[1];
            } else {
                type = null;
                type2 = null;
            }
            HashMap map = (HashMap) obj;
            for (Object obj2 : map.keySet()) {
                if (type2 != null && (type2 instanceof Class) && !type.equals(Object.class)) {
                    objA = a(obj2, (Class) type2, null);
                } else if (type2 != null && (type2 instanceof ParameterizedType)) {
                    ParameterizedType parameterizedType2 = (ParameterizedType) type2;
                    objA = a(obj2, (Class) parameterizedType2.getRawType(), parameterizedType2.getActualTypeArguments());
                } else {
                    objA = obj2;
                }
                if (type != null && (type instanceof Class) && !type.equals(Object.class)) {
                    objA2 = a(map.get(obj2), (Class) type, null);
                } else if (type != null && (type instanceof ParameterizedType)) {
                    ParameterizedType parameterizedType3 = (ParameterizedType) type;
                    objA2 = a(map.get(obj2), (Class) parameterizedType3.getRawType(), parameterizedType3.getActualTypeArguments());
                } else {
                    objA2 = map.get(obj2);
                }
                r92.put(objA, objA2);
            }
            return r92;
        }
        ArrayList arrayList3 = new ArrayList();
        for (Class<T> superclass = cls; !superclass.equals(Object.class); superclass = superclass.getSuperclass()) {
            arrayList3.add(superclass);
        }
        HashMap map2 = (HashMap) obj;
        HashMap map3 = new HashMap();
        for (String str : map2.keySet()) {
            if (map2.get(str) != null) {
                Iterator it = arrayList3.iterator();
                while (true) {
                    if (it.hasNext()) {
                        try {
                            declaredField = ((Class) it.next()).getDeclaredField(str);
                        } catch (Throwable th2) {
                            declaredField = null;
                        }
                        if (declaredField != null) {
                            map3.put(str, declaredField);
                            break;
                        }
                    }
                }
            }
        }
        T t2 = (T) ReflectHelper.newInstance(ReflectHelper.getName(cls), new Object[0]);
        for (String str2 : map3.keySet()) {
            Object obj3 = map2.get(str2);
            Field field = (Field) map3.get(str2);
            Class<?> type4 = field.getType();
            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType)) {
                actualTypeArguments = null;
            } else {
                actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
            }
            field.setAccessible(true);
            field.set(t2, a(obj3, type4, actualTypeArguments));
        }
        return t2;
    }
}