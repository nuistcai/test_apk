package cn.fly.commons.cc;

import cn.fly.commons.cc.y;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class t {
    private static final HashMap<String, Class<?>> a = new HashMap<>();
    private final HashMap<String, HashMap<String, String[][]>> b = new HashMap<>();
    private final HashMap<Class<?>, s<?>> c = new HashMap<>();

    static {
        a.put(cn.fly.commons.w.b("003-chZdh"), Integer.TYPE);
        a.put(cn.fly.commons.w.b("006OcbcjcfeeOfe"), Double.TYPE);
        a.put("long", Long.TYPE);
        a.put(cn.fly.commons.w.b("005?deCfQcj+ch"), Float.TYPE);
        a.put("boolean", Boolean.TYPE);
        a.put("short", Short.TYPE);
        a.put("byte", Byte.TYPE);
        a.put(cn.fly.commons.w.b("004bgc!ci"), Character.TYPE);
        a.put("void", Void.TYPE);
    }

    public t() {
        a(y.a.class, y.a.class);
    }

    public void a(byte[] bArr) throws Throwable {
        String str;
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr), "utf-8"));
        try {
            HashMap<String, String[][]> map = null;
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                String strSubstring = line.substring(0, 2);
                String strSubstring2 = line.substring(2);
                if (":P".equals(strSubstring)) {
                    arrayList.addAll(Arrays.asList(strSubstring2.split("#")));
                } else if (":C".equals(strSubstring)) {
                    String str2 = (String) arrayList.get(Integer.parseInt(strSubstring2));
                    map = this.b.get(str2);
                    if (map == null) {
                        map = new HashMap<>();
                        this.b.put(str2, map);
                    }
                } else {
                    String[] strArrSplit = strSubstring2.split("#");
                    String str3 = (String) arrayList.get(Integer.parseInt(strArrSplit[0]));
                    String[][] strArr = new String[Integer.parseInt(strArrSplit[1])][];
                    for (int i = 2; i < strArrSplit.length; i++) {
                        if (strArrSplit[i].startsWith("+")) {
                            str = "+";
                        } else {
                            str = null;
                        }
                        if (strArrSplit[i].length() > 1) {
                            String[] strArrSplit2 = strArrSplit[i].substring(1).split(",");
                            String[] strArr2 = new String[strArrSplit2.length + 1];
                            strArr2[0] = str;
                            int i2 = 0;
                            while (i2 < strArrSplit2.length) {
                                int i3 = i2 + 1;
                                strArr2[i3] = (String) arrayList.get(Integer.parseInt(strArrSplit2[i2]));
                                i2 = i3;
                            }
                            strArr[i - 2] = strArr2;
                        } else {
                            strArr[i - 2] = new String[]{str};
                        }
                    }
                    map.put(str3, strArr);
                }
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    public void a(Class<?> cls, Class<? extends s<?>> cls2) {
        try {
            s<?> sVarNewInstance = cls2.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            if (this.c.get(cls) == null) {
                this.c.put(cls, sVarNewInstance);
            }
        } catch (Throwable th) {
        }
    }

    public boolean a(Object obj, Class<?> cls, String str, Object[] objArr, r rVar) throws Throwable {
        s<?> sVar = null;
        for (Class<?> superclass = cls; sVar == null && superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
            sVar = this.c.get(superclass);
        }
        if (sVar == null) {
            return false;
        }
        boolean[] zArr = new boolean[1];
        Object[] objArr2 = new Object[1];
        Throwable[] thArr = new Throwable[1];
        boolean zA = sVar.a(obj, cls, str, objArr, zArr, objArr2, thArr);
        if (zA) {
            if (thArr[0] != null) {
                throw thArr[0];
            }
            if (!zArr[0]) {
                rVar.a(objArr2[0]);
            }
        }
        return zA;
    }

    public boolean[] a(Class<?>[] clsArr, Object[] objArr, boolean[] zArr) {
        zArr[0] = true;
        if (clsArr.length != objArr.length) {
            return null;
        }
        boolean[] zArr2 = new boolean[clsArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj != null) {
                Class<?> cls = clsArr[i];
                if (cls.isInterface() && (obj instanceof y)) {
                    zArr2[i] = true;
                    zArr[0] = false;
                } else {
                    Class<?> cls2 = obj.getClass();
                    if (!b(cls, cls2) && !cls.isAssignableFrom(cls2)) {
                        return null;
                    }
                    zArr2[i] = false;
                }
            }
        }
        return zArr2;
    }

    private boolean b(Class<?> cls, Class<?> cls2) {
        return (cls == Byte.TYPE && cls2 == Byte.class) || (cls == Short.TYPE && (cls2 == Short.class || cls2 == Byte.class || cls2 == Character.class)) || ((cls == Character.TYPE && (cls2 == Character.class || cls2 == Short.class || cls2 == Byte.class)) || ((cls == Integer.TYPE && (cls2 == Integer.class || cls2 == Short.class || cls2 == Byte.class || cls2 == Character.class)) || ((cls == Long.TYPE && (cls2 == Long.class || cls2 == Integer.class || cls2 == Short.class || cls2 == Byte.class || cls2 == Character.class)) || ((cls == Float.TYPE && (cls2 == Float.class || cls2 == Long.class || cls2 == Integer.class || cls2 == Short.class || cls2 == Byte.class || cls2 == Character.class)) || ((cls == Double.TYPE && (cls2 == Double.class || cls2 == Float.class || cls2 == Long.class || cls2 == Integer.class || cls2 == Short.class || cls2 == Byte.class || cls2 == Character.class)) || (cls == Boolean.TYPE && cls2 == Boolean.class))))));
    }

    public Object[] a(r rVar, Class<?>[] clsArr, Object[] objArr, boolean[] zArr) {
        Object[] objArr2 = new Object[zArr.length];
        for (int i = 0; i < zArr.length; i++) {
            if (objArr[i] != null) {
                if (zArr[i]) {
                    objArr2[i] = rVar.a(objArr[i], true, clsArr[i]);
                } else {
                    objArr2[i] = objArr[i];
                }
            }
        }
        return objArr2;
    }

    /*  JADX ERROR: NoSuchElementException in pass: ReplaceNewArray
        java.util.NoSuchElementException
        	at java.base/java.util.TreeMap.key(Unknown Source)
        	at java.base/java.util.TreeMap.lastKey(Unknown Source)
        	at jadx.core.dex.visitors.ReplaceNewArray.processNewArray(ReplaceNewArray.java:171)
        	at jadx.core.dex.visitors.ReplaceNewArray.processInsn(ReplaceNewArray.java:72)
        	at jadx.core.dex.visitors.ReplaceNewArray.visit(ReplaceNewArray.java:53)
        */
    public java.lang.reflect.Constructor a(java.lang.Class<?> r12, java.lang.Object[] r13, boolean[][] r14) throws java.lang.Throwable {
        /*
            r11 = this;
            java.util.HashMap<java.lang.String, java.util.HashMap<java.lang.String, java.lang.String[][]>> r0 = r11.b
            java.lang.String r1 = r12.getName()
            java.lang.Object r0 = r0.get(r1)
            java.util.HashMap r0 = (java.util.HashMap) r0
            if (r0 == 0) goto L5a
            java.lang.String r1 = "006+hdchMdFch)h-hf"
            java.lang.String r1 = cn.fly.commons.w.b(r1)
            java.lang.Object r0 = r0.get(r1)
            java.lang.String[][] r0 = (java.lang.String[][]) r0
            if (r0 == 0) goto L5a
            int r1 = r0.length
            r2 = 0
            r3 = 0
        L1f:
            if (r3 >= r1) goto L5a
            r4 = r0[r3]
            int r5 = r4.length
            r6 = 1
            int r5 = r5 - r6
            int r7 = r13.length
            if (r5 != r7) goto L57
            int r5 = r13.length
            java.lang.Class[] r7 = new java.lang.Class[r5]
            r8 = 0
        L2e:
            if (r8 >= r5) goto L43
            int r9 = r8 + 1
            r10 = r4[r9]
            java.lang.Class r10 = r11.a(r10)
            r7[r8] = r10
            r8 = r7[r8]
            if (r8 != 0) goto L41
        L3f:
            r4 = 1
            goto L44
        L41:
            r8 = r9
            goto L2e
        L43:
            r4 = 0
        L44:
            if (r4 != 0) goto L57
            boolean[] r4 = new boolean[r6]
            boolean[] r5 = r11.a(r7, r13, r4)
            if (r5 == 0) goto L57
            r14[r2] = r5
            r14[r6] = r4
            java.lang.reflect.Constructor r12 = r12.getDeclaredConstructor(r7)
            return r12
        L57:
            int r3 = r3 + 1
            goto L1f
        L5a:
            r12 = 0
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.fly.commons.cc.t.a(java.lang.Class, java.lang.Object[], boolean[][]):java.lang.reflect.Constructor");
    }

    /*  JADX ERROR: NoSuchElementException in pass: ReplaceNewArray
        java.util.NoSuchElementException
        	at java.base/java.util.TreeMap.key(Unknown Source)
        	at java.base/java.util.TreeMap.lastKey(Unknown Source)
        	at jadx.core.dex.visitors.ReplaceNewArray.processNewArray(ReplaceNewArray.java:171)
        	at jadx.core.dex.visitors.ReplaceNewArray.processInsn(ReplaceNewArray.java:72)
        	at jadx.core.dex.visitors.ReplaceNewArray.visit(ReplaceNewArray.java:53)
        */
    public java.lang.reflect.Method a(java.lang.Class<?> r16, java.lang.String r17, boolean r18, java.lang.Object[] r19, boolean[][] r20) throws java.lang.Throwable {
        /*
            r15 = this;
            r0 = r15
            r1 = r17
            r2 = r19
            java.util.HashMap<java.lang.String, java.util.HashMap<java.lang.String, java.lang.String[][]>> r3 = r0.b
            java.lang.String r4 = r16.getName()
            java.lang.Object r3 = r3.get(r4)
            java.util.HashMap r3 = (java.util.HashMap) r3
            if (r3 == 0) goto L73
            java.lang.Object r3 = r3.get(r1)
            java.lang.String[][] r3 = (java.lang.String[][]) r3
            if (r3 == 0) goto L73
            int r4 = r3.length
            r5 = 0
            r6 = 0
        L1e:
            if (r6 >= r4) goto L73
            r7 = r3[r6]
            r8 = r7[r5]
            r9 = 1
            if (r8 == 0) goto L29
            r8 = 1
            goto L2a
        L29:
            r8 = 0
        L2a:
            r10 = r18
            if (r10 != r8) goto L30
            r8 = 1
            goto L31
        L30:
            r8 = 0
        L31:
            if (r8 == 0) goto L6e
            int r8 = r7.length
            int r8 = r8 - r9
            int r11 = r2.length
            if (r8 != r11) goto L6e
            int r8 = r2.length
            java.lang.Class[] r11 = new java.lang.Class[r8]
            r12 = 0
        L3d:
            if (r12 >= r8) goto L52
            int r13 = r12 + 1
            r14 = r7[r13]
            java.lang.Class r14 = r15.a(r14)
            r11[r12] = r14
            r12 = r11[r12]
            if (r12 != 0) goto L50
        L4e:
            r7 = 1
            goto L53
        L50:
            r12 = r13
            goto L3d
        L52:
            r7 = 0
        L53:
            if (r7 != 0) goto L6b
            boolean[] r7 = new boolean[r9]
            boolean[] r8 = r15.a(r11, r2, r7)
            if (r8 == 0) goto L68
            r20[r5] = r8
            r20[r9] = r7
            r7 = r16
            java.lang.reflect.Method r1 = r7.getDeclaredMethod(r1, r11)
            return r1
        L68:
            r7 = r16
            goto L70
        L6b:
            r7 = r16
            goto L70
        L6e:
            r7 = r16
        L70:
            int r6 = r6 + 1
            goto L1e
        L73:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.fly.commons.cc.t.a(java.lang.Class, java.lang.String, boolean, java.lang.Object[], boolean[][]):java.lang.reflect.Method");
    }

    private Class<?> a(String str) {
        Class<?> cls = a.get(str);
        if (cls == null) {
            try {
                return Class.forName(str);
            } catch (Throwable th) {
                return null;
            }
        }
        return cls;
    }
}