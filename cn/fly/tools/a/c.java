package cn.fly.tools.a;

import android.content.Context;
import cn.fly.commons.w;
import cn.fly.commons.y;
import cn.fly.tools.utils.DH;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public class c implements cn.fly.tools.a.a {
    public boolean a(Context context) {
        try {
            return a.a();
        } catch (Throwable th) {
            return false;
        }
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(Class cls, Object obj, String str, Class[] clsArr, Object[] objArr) throws Throwable {
        return (T) a.a((Class<?>) cls, obj, str, objArr);
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str, Object obj, String str2, Class[] clsArr, Object[] objArr) throws Throwable {
        return (T) a.a(str, obj, str2, objArr);
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str) throws Throwable {
        return (T) a.a(str, new Object[0]);
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str, String str2, Object obj) throws Throwable {
        return (T) a.a(str, str2, obj);
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str, Class[] clsArr, Object[] objArr) throws Throwable {
        return (T) a.a(str, objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b() {
        try {
            b.f fVar = new b.f();
            StringBuilder sbAppend = new StringBuilder().append("").append(fVar.c).append(fVar.d).append(fVar.e);
            Objects.requireNonNull(fVar);
            StringBuilder sbAppend2 = sbAppend.append(0);
            Objects.requireNonNull(fVar);
            String str = sbAppend2.append(0L).toString() + "" + new b.g().c;
            b.d dVar = new b.d();
            String str2 = str + "" + dVar.b + dVar.a;
            b.C0020b c0020b = new b.C0020b();
            String str3 = (str2 + "" + c0020b.a + c0020b.b + c0020b.c + c0020b.d + Arrays.toString(c0020b.e) + c0020b.f + c0020b.g + c0020b.h + c0020b.i + c0020b.j + c0020b.k + c0020b.l + c0020b.m + c0020b.n + c0020b.o + c0020b.p + c0020b.q + c0020b.r + c0020b.s + c0020b.t + c0020b.u + c0020b.v + c0020b.w + c0020b.x + ((int) c0020b.y) + ((int) c0020b.z)) + "" + new b.a().a;
            b.C0021c c0021c = new b.C0021c();
            String str4 = str3 + "" + c0021c.a + c0021c.b + Arrays.toString(c0021c.c) + c0021c.d + c0021c.e;
            b.h hVar = new b.h();
            String str5 = str4 + "" + hVar.c + hVar.d + b.h.a + b.h.b;
            new b.i();
            b.i.c();
            b.i.d();
            b.e.b(new Object[0]);
            new b.e(new Object[]{str5});
        } catch (Throwable th) {
        }
    }

    private static class a {
        private static long a;
        private static long b;
        private static long c;
        private static long d;
        private static long e;
        private static long f;
        private static long g;
        private static long h;
        private static long i;
        private static long j;
        private static long k;
        private static long l;
        private static boolean m = false;

        private static synchronized void a(int i2) {
            y.a().a("usf", Integer.valueOf(i2)).h();
        }

        private static synchronized boolean b() {
            int iIntValue = ((Integer) y.a().b("usf", -1)).intValue();
            if (iIntValue == 1) {
                d.b("3xu ckFe f");
                return false;
            }
            if (iIntValue != -1 && iIntValue != 0) {
                return false;
            }
            a(1);
            return true;
        }

        private static synchronized void c() {
            a(0);
        }

        public static synchronized boolean a() throws Throwable {
            Field[] declaredFields;
            d.b("3xu ck");
            if (DH.SyncMtd.getOSVersionIntForFly() < 29) {
                return false;
            }
            if (C0022c.a()) {
                if (!b() || !d()) {
                    return false;
                }
                try {
                    declaredFields = b.C0021c.class.getDeclaredFields();
                    int length = declaredFields.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        Field field = declaredFields[i2];
                        if (field.getType() != Long.TYPE) {
                            i2++;
                        } else {
                            a = C0022c.a(field);
                            break;
                        }
                    }
                } catch (Throwable th) {
                }
                if (a("", a)) {
                    return false;
                }
                int length2 = declaredFields.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length2) {
                        break;
                    }
                    Field field2 = declaredFields[i3];
                    if (field2.getType() != b.C0020b.class) {
                        i3++;
                    } else {
                        b = C0022c.a(field2);
                        break;
                    }
                }
                if (a("", b)) {
                    return false;
                }
                Field[] declaredFields2 = b.f.class.getDeclaredFields();
                int length3 = declaredFields2.length;
                int i4 = 0;
                while (true) {
                    if (i4 >= length3) {
                        break;
                    }
                    Field field3 = declaredFields2[i4];
                    if (field3.getType() != Long.TYPE) {
                        i4++;
                    } else {
                        c = C0022c.a(field3);
                        break;
                    }
                }
                if (a("", c)) {
                    return false;
                }
                Field[] declaredFields3 = b.g.class.getDeclaredFields();
                int length4 = declaredFields3.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length4) {
                        break;
                    }
                    Field field4 = declaredFields3[i5];
                    if (field4.getType() != b.C0020b.class) {
                        i5++;
                    } else {
                        d = C0022c.a(field4);
                        break;
                    }
                }
                if (a("", d)) {
                    return false;
                }
                Field[] declaredFields4 = b.C0020b.class.getDeclaredFields();
                int length5 = declaredFields4.length;
                int i6 = 0;
                int i7 = 1;
                while (true) {
                    if (i6 >= length5) {
                        break;
                    }
                    Field field5 = declaredFields4[i6];
                    if (field5.getType() == Long.TYPE) {
                        if (i7 == 1) {
                            f = C0022c.a(field5);
                            i7++;
                        } else if (i7 == 2) {
                            e = C0022c.a(field5);
                            i7++;
                        } else if (i7 == 3) {
                            g = C0022c.a(field5);
                            break;
                        }
                    }
                    i6++;
                }
                if (a("", f)) {
                    return false;
                }
                if (a("", e)) {
                    return false;
                }
                if (a("", g)) {
                    return false;
                }
                Field[] declaredFields5 = b.d.class.getDeclaredFields();
                int length6 = declaredFields5.length;
                int i8 = 0;
                while (true) {
                    if (i8 >= length6) {
                        break;
                    }
                    Field field6 = declaredFields5[i8];
                    if (field6.getType() != Member.class) {
                        i8++;
                    } else {
                        h = C0022c.a(field6);
                        break;
                    }
                }
                if (a("", h)) {
                    return false;
                }
                Method[] declaredMethods = b.i.class.getDeclaredMethods();
                int length7 = declaredMethods.length;
                long jA = 0;
                long jA2 = 0;
                int i9 = 0;
                int i10 = 1;
                while (true) {
                    if (i9 >= length7) {
                        break;
                    }
                    Method method = declaredMethods[i9];
                    if (method.getReturnType() == Void.TYPE) {
                        if (i10 != 1) {
                            if (i10 == 2) {
                                method.setAccessible(true);
                                jA = C0022c.a(MethodHandles.lookup().unreflect(method), c);
                                break;
                            }
                        } else {
                            method.setAccessible(true);
                            jA2 = C0022c.a(MethodHandles.lookup().unreflect(method), c);
                            i10++;
                        }
                    }
                    i9++;
                }
                i = jA - jA2;
                if (a("", i)) {
                    return false;
                }
                j = (jA2 - C0022c.a(b.i.class, e)) - i;
                if (a("", j)) {
                    return false;
                }
                Field[] declaredFields6 = b.h.class.getDeclaredFields();
                int length8 = declaredFields6.length;
                MethodHandle methodHandleUnreflectGetter = null;
                MethodHandle methodHandleUnreflectGetter2 = null;
                int i11 = 0;
                int i12 = 1;
                while (true) {
                    if (i11 >= length8) {
                        break;
                    }
                    Field field7 = declaredFields6[i11];
                    if (field7.getType() == Integer.TYPE) {
                        if (i12 != 1) {
                            if (i12 == 2) {
                                field7.setAccessible(true);
                                methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(field7);
                                break;
                            }
                        } else {
                            field7.setAccessible(true);
                            methodHandleUnreflectGetter2 = MethodHandles.lookup().unreflectGetter(field7);
                            i12++;
                        }
                    }
                    i11++;
                }
                long jA3 = C0022c.a(methodHandleUnreflectGetter2, c);
                k = C0022c.a(methodHandleUnreflectGetter, c) - jA3;
                if (a("", k)) {
                    return false;
                }
                l = jA3 - C0022c.a(b.h.class, f);
                if (a("", l)) {
                    return false;
                }
                c.b();
                c();
            }
            m = true;
            return true;
        }

        public static synchronized <T> T a(String str, Object... objArr) throws Throwable {
            return (T) a(Class.forName(str), objArr);
        }

        public static synchronized <T> T a(Class<?> cls, Object... objArr) throws Throwable {
            Method method;
            T t;
            boolean zB = b();
            if (!m || !zB) {
                throw new Throwable("x1 " + m + "|" + zB);
            }
            Method[] declaredMethods = b.e.class.getDeclaredMethods();
            int length = declaredMethods.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    method = null;
                    break;
                }
                method = declaredMethods[i2];
                if (method.getReturnType() == Object.class) {
                    break;
                }
                i2++;
            }
            if (method == null) {
                throw new Throwable("x22");
            }
            Constructor declaredConstructor = b.e.class.getDeclaredConstructor(Object[].class);
            declaredConstructor.setAccessible(true);
            long jA = C0022c.a(cls, e);
            if (jA != 0) {
                int iA = C0022c.a(jA);
                for (int i3 = 0; i3 < iA; i3++) {
                    long j2 = i3;
                    long j3 = i;
                    Long.signum(j2);
                    long j4 = (j2 * j3) + jA + j;
                    C0022c.a(method, a, j4);
                    if (w.b("006PhdchNdOchYhDhf").equals(method.getName())) {
                        C0022c.a(declaredConstructor, a, j4);
                        C0022c.a(declaredConstructor, b, cls);
                        if (a(declaredConstructor.getParameterTypes(), objArr)) {
                            try {
                                t = (T) declaredConstructor.newInstance(objArr);
                                c();
                            } catch (Throwable th) {
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            c();
            throw new NoSuchMethodException("n1");
            return t;
        }

        public static synchronized <T> T a(String str, Object obj, String str2, Object... objArr) throws Throwable {
            return (T) a(Class.forName(str), obj, str2, objArr);
        }

        public static synchronized <T> T a(Class<?> cls, Object obj, String str, Object... objArr) throws Throwable {
            Method method;
            T t;
            boolean zB = b();
            if (!m || !zB) {
                throw new Throwable("x2 " + m + "|" + zB);
            }
            Method[] declaredMethods = b.e.class.getDeclaredMethods();
            int length = declaredMethods.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    method = null;
                    break;
                }
                method = declaredMethods[i2];
                if (method.getReturnType() == Object.class) {
                    break;
                }
                i2++;
            }
            if (method == null) {
                throw new Throwable("x22");
            }
            method.setAccessible(true);
            long jA = C0022c.a(cls, e);
            if (jA != 0) {
                int iA = C0022c.a(jA);
                for (int i3 = 0; i3 < iA; i3++) {
                    long j2 = i3;
                    long j3 = i;
                    Long.signum(j2);
                    C0022c.a(method, a, (j2 * j3) + jA + j);
                    if (str.equals(method.getName()) && a(method.getParameterTypes(), objArr)) {
                        try {
                            t = (T) method.invoke(obj, objArr);
                            c();
                        } catch (Throwable th) {
                        }
                    }
                }
            }
            c();
            throw new NoSuchMethodException("n2");
            return t;
        }

        public static synchronized <T> T a(String str, String str2, Object obj) throws Throwable {
            return (T) a(Class.forName(str), str2, obj);
        }

        public static synchronized <T> T a(Class<?> cls, String str, Object obj) throws Throwable {
            MethodHandle methodHandleUnreflectGetter;
            T t;
            boolean zB = b();
            if (!m || !zB) {
                throw new Throwable("x3 " + m + "|" + zB);
            }
            if (DH.SyncMtd.getOSVersionIntForFly() < 26) {
                throw new Throwable("x33");
            }
            for (Field field : b.h.class.getDeclaredFields()) {
                if (field.getType() == Integer.TYPE && (obj != null || (field.getModifiers() & 8) != 0)) {
                    field.setAccessible(true);
                    methodHandleUnreflectGetter = MethodHandles.lookup().unreflectGetter(field);
                    break;
                }
            }
            methodHandleUnreflectGetter = null;
            if (methodHandleUnreflectGetter == null) {
                throw new Throwable("x34");
            }
            long jA = C0022c.a(cls, obj == null ? g : f);
            if (jA != 0) {
                int iA = C0022c.a(jA);
                for (int i2 = 0; i2 < iA; i2++) {
                    long j2 = i2;
                    long j3 = k;
                    Long.signum(j2);
                    C0022c.a(methodHandleUnreflectGetter, c, (j2 * j3) + jA + l);
                    C0022c.a(methodHandleUnreflectGetter, d, (Object) null);
                    try {
                        MethodHandles.Lookup lookup = MethodHandles.lookup();
                        Method method = lookup.getClass().getMethod(w.b("012<ciHe5ccDecf(ekchci]ebh"), MethodHandle.class);
                        method.setAccessible(true);
                        method.invoke(lookup, methodHandleUnreflectGetter);
                    } catch (Throwable th) {
                    }
                    Field field2 = (Field) C0022c.b(C0022c.b(methodHandleUnreflectGetter, d), h);
                    if (field2.getName().equals(str)) {
                        field2.setAccessible(true);
                        try {
                            t = (T) field2.get(obj);
                            c();
                        } catch (Throwable th2) {
                        }
                    }
                }
            }
            c();
            throw new NoSuchMethodException("n3");
            return t;
        }

        private static boolean d() {
            try {
                int length = b.f.class.getDeclaredFields().length;
                String str = "f" + length;
                if (length != 5) {
                    d.b("3xu ckHpCz " + str);
                    return false;
                }
                int length2 = b.g.class.getDeclaredFields().length;
                String str2 = str + "|f" + length2;
                if (length2 != 1) {
                    d.b("3xu ckHpCz " + str2);
                    return false;
                }
                int length3 = b.d.class.getDeclaredFields().length;
                String str3 = str2 + "|f" + length3;
                if (length3 != 2) {
                    d.b("3xu ckHpCz " + str3);
                    return false;
                }
                int length4 = b.C0020b.class.getDeclaredFields().length;
                String str4 = str3 + "|f" + length4;
                if (length4 != 26) {
                    d.b("3xu ckHpCz " + str4);
                    return false;
                }
                int length5 = b.a.class.getDeclaredFields().length;
                String str5 = str4 + "|f" + length5;
                if (length5 != 1) {
                    d.b("3xu ckHpCz " + str5);
                    return false;
                }
                int length6 = b.C0021c.class.getDeclaredFields().length;
                String str6 = str5 + "|f" + length6;
                if (length6 != 5) {
                    d.b("3xu ckHpCz " + str6);
                    return false;
                }
                int length7 = b.h.class.getDeclaredFields().length;
                String str7 = str6 + "|f" + length7;
                if (length7 != 4) {
                    d.b("3xu ckHpCz " + str7);
                    return false;
                }
                int length8 = b.i.class.getDeclaredMethods().length;
                String str8 = str7 + "|m" + length8;
                if (length8 < 2) {
                    d.b("3xu ckHpCz " + str8);
                    return false;
                }
                int length9 = b.e.class.getDeclaredMethods().length;
                String str9 = str8 + "|m" + length9;
                if (length9 >= 1) {
                    return true;
                }
                d.b("3xu ckHpCz " + str9);
                return false;
            } catch (Throwable th) {
                d.a(th);
                return false;
            }
        }

        private static boolean a(String str, long j2) {
            if (j2 != 0) {
                return false;
            }
            try {
                String str2 = str + j2 + "|";
                d.b("3xu ckZr " + str2.substring(0, str2.length() - 1));
            } catch (Throwable th) {
            }
            return true;
        }

        private static boolean a(Class<?>[] clsArr, Object[] objArr) {
            if ((clsArr == null || clsArr.length == 0) && (objArr == null || objArr.length == 0)) {
                return true;
            }
            if (clsArr.length != objArr.length) {
                return false;
            }
            for (int i2 = 0; i2 < clsArr.length; i2++) {
                if (clsArr[i2].isPrimitive()) {
                    if (clsArr[i2] == Integer.TYPE && !(objArr[i2] instanceof Integer)) {
                        return false;
                    }
                    if (clsArr[i2] == Byte.TYPE && !(objArr[i2] instanceof Byte)) {
                        return false;
                    }
                    if (clsArr[i2] == Character.TYPE && !(objArr[i2] instanceof Character)) {
                        return false;
                    }
                    if (clsArr[i2] == Boolean.TYPE && !(objArr[i2] instanceof Boolean)) {
                        return false;
                    }
                    if (clsArr[i2] == Double.TYPE && !(objArr[i2] instanceof Double)) {
                        return false;
                    }
                    if (clsArr[i2] == Float.TYPE && !(objArr[i2] instanceof Float)) {
                        return false;
                    }
                    if (clsArr[i2] == Long.TYPE && !(objArr[i2] instanceof Long)) {
                        return false;
                    }
                    if (clsArr[i2] == Short.TYPE && !(objArr[i2] instanceof Short)) {
                        return false;
                    }
                } else if (objArr[i2] != null && !clsArr[i2].isInstance(objArr[i2])) {
                    return false;
                }
            }
            return true;
        }
    }

    /* renamed from: cn.fly.tools.a.c$c, reason: collision with other inner class name */
    private static class C0022c {
        private static Object a;
        private static Method b;

        public static boolean a() throws Throwable {
            a = a(Class.forName(w.b("015Uehcf6d,ckcecheh7b0ckdj_dYehDc%de0e")), w.b("0096di!ehKdjZd:eh%cSde[e"), new Class[0]).invoke(null, new Object[0]);
            return a != null;
        }

        public static long a(Object obj, long j) throws Throwable {
            return ((Long) a(a.getClass(), w.b("007]diWeh$edcjKd7di"), Object.class, Long.TYPE).invoke(a, obj, Long.valueOf(j))).longValue();
        }

        public static void a(Object obj, long j, long j2) throws Throwable {
            Class<?> cls = a.getClass();
            String strB = w.b("007i=cfOh$edcjBdPdi");
            Class cls2 = Long.TYPE;
            a(cls, strB, Object.class, cls2, cls2).invoke(a, obj, Long.valueOf(j), Long.valueOf(j2));
        }

        public static Object b(Object obj, long j) throws Throwable {
            return a(a.getClass(), w.b("009Pdi5ehWfgeegfYebh"), Object.class, Long.TYPE).invoke(a, obj, Long.valueOf(j));
        }

        public static void a(Object obj, long j, Object obj2) throws Throwable {
            a(a.getClass(), w.b("009i6cfWhSfgeegfYebh"), Object.class, Long.TYPE, Object.class).invoke(a, obj, Long.valueOf(j), obj2);
        }

        public static int a(long j) throws Throwable {
            return ((Integer) a(a.getClass(), w.b("006<diWehJdd>dh"), Long.TYPE).invoke(a, Long.valueOf(j))).intValue();
        }

        public static long a(Field field) throws Throwable {
            return ((Long) a(a.getClass(), w.b("0172cjeegfSebh]fbchGef=cbfgdedeehHeh"), Field.class).invoke(a, field)).longValue();
        }

        private static Method a(Class cls, String str, Class... clsArr) throws Throwable {
            try {
                if (b == null) {
                    b = Class.class.getDeclaredMethod(w.b("017=diReh ek9ebfc5ciKeZcbgbNehg6cjcb"), String.class, Class[].class);
                }
                Method method = (Method) b.invoke(cls, str, clsArr);
                method.setAccessible(true);
                return method;
            } catch (Throwable th) {
                Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
                declaredMethod.setAccessible(true);
                return declaredMethod;
            }
        }
    }

    private static class b {

        public static class f {
            private MethodType d;
            private f e;
            private final MethodType c = null;
            protected final int a = 0;
            protected final long b = 0;
        }

        public static final class g extends f {
            private final C0020b c = null;
        }

        public static final class d {
            private final f a = null;
            private final Member b = null;
        }

        /* renamed from: cn.fly.tools.a.c$b$b, reason: collision with other inner class name */
        public static final class C0020b {
            private transient ClassLoader a;
            private transient Class<?> b;
            private transient Object c;
            private transient Object d;
            private transient Object[] e;
            private transient String f;
            private transient Class<?> g;
            private transient Object h;
            private transient long i;
            private transient long j;
            private transient long k;
            private transient int l;
            private transient int m;
            private transient int n;
            private transient int o;
            private transient int p;
            private volatile transient int q;
            private transient int r;
            private transient int s;
            private transient int t;
            private transient int u;
            private transient int v;
            private transient int w;
            private transient int x;
            private transient short y;
            private transient short z;
        }

        public static class a {
            private boolean a;
        }

        /* renamed from: cn.fly.tools.a.c$b$c, reason: collision with other inner class name */
        public static final class C0021c extends a {
            private C0020b a;
            private C0020b b;
            private Object[] c;
            private long d;
            private int e;
        }

        public static class h {
            private static int a;
            private static int b;
            private int c;
            private int d;
        }

        public static class i {
            /* JADX INFO: Access modifiers changed from: private */
            public static void c() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static void d() {
            }
        }

        public static class e {
            /* JADX INFO: Access modifiers changed from: private */
            public static Object b(Object... objArr) {
                throw new IllegalStateException("i1");
            }

            private e(Object... objArr) {
                throw new IllegalStateException("i2");
            }
        }
    }
}