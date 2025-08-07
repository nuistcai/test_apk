package cn.fly.commons.cc;

import androidx.core.view.MotionEventCompat;
import cn.fly.commons.cc.w;
import cn.fly.commons.cc.y;
import cn.fly.commons.cc.z;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes.dex */
public class x {
    public int a;
    public String b;
    public int c;
    public String d;
    public String e;
    public String f;
    public int g;
    public String h;
    public int i;
    public int j;
    public int k;
    public String l;
    public Object[] m;
    public String n;
    public String[] o;
    public String p;
    public Object q;
    public int r;
    public int s;

    public x(int i) {
        this.a = i;
    }

    public x() {
    }

    public void a(w.a aVar) throws Throwable {
        int i = 0;
        switch (this.a) {
            case 1:
                this.h = (String) aVar.b();
                aVar.a();
                break;
            case 2:
                this.q = aVar.b();
                break;
            case 3:
                this.h = (String) aVar.b();
                break;
            case 4:
                this.k = ((Integer) aVar.b()).intValue();
                break;
            case 5:
                this.k = ((Integer) aVar.b()).intValue();
                break;
            case 6:
                this.s = ((Integer) aVar.b()).intValue();
                break;
            case 7:
                this.r = ((Integer) aVar.b()).intValue();
                break;
            case 9:
                this.h = (String) aVar.b();
                break;
            case 10:
                this.d = (String) aVar.b();
                this.e = (String) aVar.b();
                break;
            case 11:
                this.l = (String) aVar.b();
                break;
            case 12:
                this.p = (String) aVar.b();
                this.i = ((Integer) aVar.b()).intValue();
                break;
            case 13:
                this.n = (String) aVar.b();
                this.l = (String) aVar.b();
                break;
            case 14:
                this.n = (String) aVar.b();
                this.p = (String) aVar.b();
                this.i = ((Integer) aVar.b()).intValue();
                break;
            case 16:
                this.i = ((Integer) aVar.b()).intValue();
                break;
            case 17:
                this.n = (String) aVar.b();
                break;
            case 18:
                this.n = (String) aVar.b();
                this.i = ((Integer) aVar.b()).intValue();
                break;
            case 19:
                this.h = (String) aVar.b();
                break;
            case 20:
                this.f = (String) aVar.b();
                break;
            case 21:
                this.f = (String) aVar.b();
                this.g = ((Integer) aVar.b()).intValue();
                this.g += aVar.c();
                break;
            case 22:
                this.f = (String) aVar.b();
                this.g = ((Integer) aVar.b()).intValue();
                this.g += aVar.c();
                break;
            case 24:
                this.l = (String) aVar.b();
                break;
            case 26:
                this.n = (String) aVar.b();
                this.l = (String) aVar.b();
                break;
            case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                this.n = (String) aVar.b();
                break;
            case 29:
                this.h = (String) aVar.b();
                this.i = ((Integer) aVar.b()).intValue();
                this.j = ((Integer) aVar.b()).intValue();
                this.j += aVar.c();
                break;
            case 31:
                this.h = (String) aVar.b();
                this.i = ((Integer) aVar.b()).intValue();
                break;
            case 32:
                this.i = ((Integer) aVar.b()).intValue();
                break;
            case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                this.d = (String) aVar.b();
                this.e = (String) aVar.b();
                break;
            case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                int iIntValue = ((Integer) aVar.b()).intValue();
                this.o = new String[iIntValue];
                while (i < iIntValue) {
                    this.o[i] = (String) aVar.b();
                    aVar.a();
                    i++;
                }
                break;
            case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                int iIntValue2 = ((Integer) aVar.b()).intValue();
                this.m = new Object[iIntValue2];
                while (i < iIntValue2) {
                    this.m[i] = aVar.b();
                    i++;
                }
                break;
            case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                int iIntValue3 = ((Integer) aVar.b()).intValue();
                this.o = new String[iIntValue3];
                while (i < iIntValue3) {
                    this.o[i] = (String) aVar.b();
                    i++;
                }
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v114, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v115, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v121, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v122, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    public void a(a aVar) throws Throwable {
        Object bigDecimal;
        InputStream byteArrayInputStream;
        boolean z;
        OutputStream fileOutputStream;
        int length;
        int iIntValue;
        Object objSubstring;
        int i;
        Object yVar;
        switch (this.a) {
            case 1:
                aVar.b(this.h, aVar.a());
                return;
            case 2:
                aVar.a(this.q);
                return;
            case 3:
                aVar.a(aVar.b(this.h));
                return;
            case 4:
                Object objA = aVar.a();
                Object objA2 = aVar.a();
                switch (this.k) {
                    case 12:
                        if (objA == null) {
                            if (objA2 == null) {
                                aVar.a((Object) true);
                                return;
                            } else {
                                aVar.a((Object) false);
                                return;
                            }
                        }
                        if (!(objA instanceof Number) || !(objA2 instanceof Number)) {
                            aVar.a(Boolean.valueOf(objA.equals(objA2)));
                            return;
                        } else {
                            aVar.a(Boolean.valueOf(((Number) objA).doubleValue() == ((Number) objA2).doubleValue()));
                            return;
                        }
                    case 13:
                        if (objA == null) {
                            if (objA2 == null) {
                                aVar.a((Object) false);
                                return;
                            } else {
                                aVar.a((Object) true);
                                return;
                            }
                        }
                        if (!(objA instanceof Number) || !(objA2 instanceof Number)) {
                            aVar.a(Boolean.valueOf(!objA.equals(objA2)));
                            return;
                        } else {
                            aVar.a(Boolean.valueOf(((Number) objA).doubleValue() != ((Number) objA2).doubleValue()));
                            return;
                        }
                    case 14:
                        if (!(objA instanceof Number) || !(objA2 instanceof Number)) {
                            aVar.a(Boolean.valueOf(((Comparable) objA).compareTo(objA2) < 0));
                            return;
                        } else {
                            aVar.a(Boolean.valueOf(((Number) objA).doubleValue() < ((Number) objA2).doubleValue()));
                            return;
                        }
                    case 15:
                        if (!(objA instanceof Number) || !(objA2 instanceof Number)) {
                            aVar.a(Boolean.valueOf(((Comparable) objA).compareTo(objA2) > 0));
                            return;
                        } else {
                            aVar.a(Boolean.valueOf(((Number) objA).doubleValue() > ((Number) objA2).doubleValue()));
                            return;
                        }
                    case 16:
                        if (!(objA instanceof Number) || !(objA2 instanceof Number)) {
                            aVar.a(Boolean.valueOf(((Comparable) objA).compareTo(objA2) <= 0));
                            return;
                        } else {
                            aVar.a(Boolean.valueOf(((Number) objA).doubleValue() <= ((Number) objA2).doubleValue()));
                            return;
                        }
                    case 17:
                        if (!(objA instanceof Number) || !(objA2 instanceof Number)) {
                            aVar.a(Boolean.valueOf(((Comparable) objA).compareTo(objA2) >= 0));
                            return;
                        } else {
                            aVar.a(Boolean.valueOf(((Number) objA).doubleValue() >= ((Number) objA2).doubleValue()));
                            return;
                        }
                    case 18:
                        if (String.class.equals(objA2)) {
                            aVar.a(objA == null ? null : String.valueOf(objA));
                            return;
                        }
                        if (Number.class.equals(objA2)) {
                            String strValueOf = String.valueOf(objA);
                            if (strValueOf.contains(".")) {
                                try {
                                    bigDecimal = Float.valueOf(Float.parseFloat(strValueOf));
                                } catch (Throwable th) {
                                    try {
                                        bigDecimal = Double.valueOf(Double.parseDouble(strValueOf));
                                    } catch (Throwable th2) {
                                        bigDecimal = new BigDecimal(strValueOf);
                                    }
                                }
                            } else {
                                try {
                                    bigDecimal = Integer.valueOf(Integer.parseInt(strValueOf));
                                } catch (Throwable th3) {
                                    try {
                                        bigDecimal = Long.valueOf(Long.parseLong(strValueOf));
                                    } catch (Throwable th4) {
                                        bigDecimal = new BigInteger(strValueOf);
                                    }
                                }
                            }
                            aVar.a(bigDecimal);
                            return;
                        }
                        if (Double.class.equals(objA2) || Double.TYPE.equals(objA2)) {
                            aVar.a(Double.valueOf(String.valueOf(objA)));
                            return;
                        }
                        if (Float.class.equals(objA2) || Float.TYPE.equals(objA2)) {
                            aVar.a(Float.valueOf(Double.valueOf(String.valueOf(objA)).floatValue()));
                            return;
                        }
                        if (Integer.class.equals(objA2) || Integer.TYPE.equals(objA2)) {
                            aVar.a(Integer.valueOf(Double.valueOf(String.valueOf(objA)).intValue()));
                            return;
                        }
                        if (Long.class.equals(objA2) || Long.TYPE.equals(objA2)) {
                            aVar.a(Long.valueOf(Double.valueOf(String.valueOf(objA)).longValue()));
                            return;
                        }
                        if (Short.class.equals(objA2) || Short.TYPE.equals(objA2)) {
                            aVar.a(Short.valueOf(Double.valueOf(String.valueOf(objA)).shortValue()));
                            return;
                        }
                        if (Character.class.equals(objA2) || Character.TYPE.equals(objA2)) {
                            if (objA instanceof Integer) {
                                aVar.a(Character.valueOf((char) ((Integer) objA).intValue()));
                                return;
                            }
                            if (objA instanceof Long) {
                                aVar.a(Character.valueOf((char) ((Long) objA).longValue()));
                                return;
                            }
                            if (objA instanceof Short) {
                                aVar.a(Character.valueOf((char) ((Short) objA).shortValue()));
                                return;
                            }
                            if (objA instanceof Byte) {
                                aVar.a(Character.valueOf((char) ((Byte) objA).byteValue()));
                                return;
                            } else if (objA instanceof Double) {
                                aVar.a(Character.valueOf((char) ((Double) objA).doubleValue()));
                                return;
                            } else {
                                if (objA instanceof Float) {
                                    aVar.a(Character.valueOf((char) ((Float) objA).floatValue()));
                                    return;
                                }
                                throw new RuntimeException("Bad operator at line: " + this.b + "(" + this.c + ")");
                            }
                        }
                        if (Byte.class.equals(objA2) || Byte.TYPE.equals(objA2)) {
                            aVar.a(Byte.valueOf(Double.valueOf(String.valueOf(objA)).byteValue()));
                            return;
                        }
                        if (Boolean.class.equals(objA2)) {
                            if (objA == null) {
                                aVar.a((Object) false);
                                return;
                            }
                            if (!(objA instanceof Number)) {
                                if (objA instanceof String) {
                                    aVar.a(Boolean.valueOf(((String) objA).trim().toLowerCase().equals(cn.fly.commons.w.b("004h+cicfBe"))));
                                    return;
                                } else if (objA instanceof Boolean) {
                                    aVar.a(objA);
                                    return;
                                } else {
                                    aVar.a((Object) true);
                                    return;
                                }
                            }
                            aVar.a(Boolean.valueOf(Double.valueOf(objA.toString()).doubleValue() == 0.0d));
                            return;
                        }
                        if (BigInteger.class.equals(objA2)) {
                            aVar.a(new BigInteger(String.valueOf(objA)));
                            return;
                        } else if (BigDecimal.class.equals(objA2)) {
                            aVar.a(new BigDecimal(String.valueOf(objA)));
                            return;
                        } else {
                            aVar.a(((Class) objA2).cast(objA));
                            return;
                        }
                    case 19:
                        aVar.a(Boolean.valueOf(((Class) objA2).isInstance(objA)));
                        return;
                    case 20:
                        if (objA instanceof Collection) {
                            if (objA2 instanceof Collection) {
                                ((Collection) objA).addAll((Collection) objA2);
                                return;
                            } else {
                                ((Collection) objA).add(objA2);
                                return;
                            }
                        }
                        if ((objA instanceof Map) && (objA2 instanceof Map)) {
                            ((Map) objA).putAll((Map) objA2);
                            return;
                        }
                        if (objA2 instanceof String) {
                            byteArrayInputStream = new ByteArrayInputStream(((String) objA2).getBytes("utf-8"));
                            z = true;
                        } else if (objA2 instanceof byte[]) {
                            byteArrayInputStream = new ByteArrayInputStream((byte[]) objA2);
                            z = true;
                        } else if (objA2 instanceof File) {
                            byteArrayInputStream = new FileInputStream((File) objA2);
                            z = true;
                        } else if (objA2 instanceof InputStream) {
                            byteArrayInputStream = (InputStream) objA2;
                            z = false;
                        } else if (objA2 instanceof Serializable) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                            objectOutputStream.writeObject(objA2);
                            objectOutputStream.flush();
                            objectOutputStream.close();
                            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                            z = true;
                        } else {
                            throw new RuntimeException("Bad operator at line: " + this.b + "(" + this.c + ")");
                        }
                        if (objA instanceof File) {
                            File file = (File) objA;
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            fileOutputStream = new FileOutputStream(file, true);
                        } else if (objA instanceof OutputStream) {
                            fileOutputStream = (OutputStream) objA;
                            z = false;
                        } else {
                            throw new RuntimeException("Bad operator at line: " + this.b + "(" + this.c + ")");
                        }
                        byte[] bArr = new byte[1024];
                        for (int i2 = byteArrayInputStream.read(bArr); i2 != -1; i2 = byteArrayInputStream.read(bArr)) {
                            fileOutputStream.write(bArr, 0, i2);
                        }
                        fileOutputStream.flush();
                        if (z) {
                            byteArrayInputStream.close();
                        }
                        fileOutputStream.close();
                        return;
                    case 21:
                        if (objA == null) {
                            objA = "null";
                        }
                        if (objA2 == null) {
                            objA2 = "null";
                        }
                        if ((objA instanceof Number) && (objA2 instanceof Number)) {
                            if ((objA instanceof Double) || (objA2 instanceof Double)) {
                                aVar.a(Double.valueOf(((Number) objA).doubleValue() + ((Number) objA2).doubleValue()));
                                return;
                            }
                            if ((objA instanceof Float) || (objA2 instanceof Float)) {
                                aVar.a(Float.valueOf(((Number) objA).floatValue() + ((Number) objA2).floatValue()));
                                return;
                            }
                            if ((objA instanceof Long) || (objA2 instanceof Long)) {
                                aVar.a(Long.valueOf(((Number) objA).longValue() + ((Number) objA2).longValue()));
                                return;
                            }
                            if ((objA instanceof Integer) || (objA2 instanceof Integer)) {
                                aVar.a(Integer.valueOf(((Number) objA).intValue() + ((Number) objA2).intValue()));
                                return;
                            } else if ((objA instanceof Short) || (objA2 instanceof Short)) {
                                aVar.a(Integer.valueOf(((Number) objA).shortValue() + ((Number) objA2).shortValue()));
                                return;
                            } else {
                                aVar.a(Integer.valueOf(((Number) objA).byteValue() + ((Number) objA2).byteValue()));
                                return;
                            }
                        }
                        aVar.a(String.valueOf(objA) + String.valueOf(objA2));
                        return;
                    case 22:
                        if ((objA instanceof Number) && (objA2 instanceof Number)) {
                            if ((objA instanceof Double) || (objA2 instanceof Double)) {
                                aVar.a(Double.valueOf(((Number) objA).doubleValue() - ((Number) objA2).doubleValue()));
                                return;
                            }
                            if ((objA instanceof Float) || (objA2 instanceof Float)) {
                                aVar.a(Float.valueOf(((Number) objA).floatValue() - ((Number) objA2).floatValue()));
                                return;
                            }
                            if ((objA instanceof Long) || (objA2 instanceof Long)) {
                                aVar.a(Long.valueOf(((Number) objA).longValue() - ((Number) objA2).longValue()));
                                return;
                            }
                            if ((objA instanceof Integer) || (objA2 instanceof Integer)) {
                                aVar.a(Integer.valueOf(((Number) objA).intValue() - ((Number) objA2).intValue()));
                                return;
                            } else if ((objA instanceof Short) || (objA2 instanceof Short)) {
                                aVar.a(Integer.valueOf(((Number) objA).shortValue() - ((Number) objA2).shortValue()));
                                return;
                            } else {
                                aVar.a(Integer.valueOf(((Number) objA).byteValue() - ((Number) objA2).byteValue()));
                                return;
                            }
                        }
                        throw new RuntimeException("Bad operator at line: " + this.b + "(" + this.c + ")");
                    case 23:
                        if ((objA instanceof Number) && (objA2 instanceof Number)) {
                            if ((objA instanceof Double) || (objA2 instanceof Double)) {
                                aVar.a(Double.valueOf(((Number) objA).doubleValue() * ((Number) objA2).doubleValue()));
                                return;
                            }
                            if ((objA instanceof Float) || (objA2 instanceof Float)) {
                                aVar.a(Float.valueOf(((Number) objA).floatValue() * ((Number) objA2).floatValue()));
                                return;
                            }
                            if ((objA instanceof Long) || (objA2 instanceof Long)) {
                                aVar.a(Long.valueOf(((Number) objA).longValue() * ((Number) objA2).longValue()));
                                return;
                            }
                            if ((objA instanceof Integer) || (objA2 instanceof Integer)) {
                                aVar.a(Integer.valueOf(((Number) objA).intValue() * ((Number) objA2).intValue()));
                                return;
                            } else if ((objA instanceof Short) || (objA2 instanceof Short)) {
                                aVar.a(Integer.valueOf(((Number) objA).shortValue() * ((Number) objA2).shortValue()));
                                return;
                            } else {
                                aVar.a(Integer.valueOf(((Number) objA).byteValue() * ((Number) objA2).byteValue()));
                                return;
                            }
                        }
                        throw new RuntimeException("Bad operator at line: " + this.b + "(" + this.c + ")");
                    case 24:
                        if ((objA instanceof Number) && (objA2 instanceof Number)) {
                            if ((objA instanceof Double) || (objA2 instanceof Double)) {
                                aVar.a(Double.valueOf(((Number) objA).doubleValue() / ((Number) objA2).doubleValue()));
                                return;
                            }
                            if ((objA instanceof Float) || (objA2 instanceof Float)) {
                                aVar.a(Float.valueOf(((Number) objA).floatValue() / ((Number) objA2).floatValue()));
                                return;
                            }
                            if ((objA instanceof Long) || (objA2 instanceof Long)) {
                                aVar.a(Long.valueOf(((Number) objA).longValue() / ((Number) objA2).longValue()));
                                return;
                            }
                            if ((objA instanceof Integer) || (objA2 instanceof Integer)) {
                                aVar.a(Integer.valueOf(((Number) objA).intValue() / ((Number) objA2).intValue()));
                                return;
                            } else if ((objA instanceof Short) || (objA2 instanceof Short)) {
                                aVar.a(Integer.valueOf(((Number) objA).shortValue() / ((Number) objA2).shortValue()));
                                return;
                            } else {
                                aVar.a(Integer.valueOf(((Number) objA).byteValue() / ((Number) objA2).byteValue()));
                                return;
                            }
                        }
                        throw new RuntimeException("Bad operator at line: " + this.b + "(" + this.c + ")");
                    case 25:
                        if ((objA instanceof Number) && (objA2 instanceof Number)) {
                            if ((objA instanceof Double) || (objA2 instanceof Double)) {
                                aVar.a(Double.valueOf(((Number) objA).doubleValue() % ((Number) objA2).doubleValue()));
                                return;
                            }
                            if ((objA instanceof Float) || (objA2 instanceof Float)) {
                                aVar.a(Float.valueOf(((Number) objA).floatValue() % ((Number) objA2).floatValue()));
                                return;
                            }
                            if ((objA instanceof Long) || (objA2 instanceof Long)) {
                                aVar.a(Long.valueOf(((Number) objA).longValue() % ((Number) objA2).longValue()));
                                return;
                            }
                            if ((objA instanceof Integer) || (objA2 instanceof Integer)) {
                                aVar.a(Integer.valueOf(((Number) objA).intValue() % ((Number) objA2).intValue()));
                                return;
                            } else if ((objA instanceof Short) || (objA2 instanceof Short)) {
                                aVar.a(Integer.valueOf(((Number) objA).shortValue() % ((Number) objA2).shortValue()));
                                return;
                            } else {
                                aVar.a(Integer.valueOf(((Number) objA).byteValue() % ((Number) objA2).byteValue()));
                                return;
                            }
                        }
                        throw new RuntimeException("Bad operator at line: " + this.b + "(" + this.c + ")");
                    default:
                        throw new RuntimeException("Bad operator at line: " + this.b + "(" + this.c + ")");
                }
            case 5:
                Object objA3 = aVar.a();
                switch (this.k) {
                    case 26:
                        aVar.a(Boolean.valueOf(!((Boolean) objA3).booleanValue()));
                        return;
                    default:
                        throw new RuntimeException("Bad operator at line: " + this.b + "(" + this.c + ")");
                }
            case 6:
                ArrayList arrayList = new ArrayList();
                if (this.s != 1) {
                    for (int i3 = 0; i3 < this.s; i3++) {
                        arrayList.add(aVar.a());
                    }
                } else {
                    Object objA4 = aVar.a();
                    if (objA4 != null && objA4.getClass().isArray()) {
                        int length2 = Array.getLength(objA4);
                        for (int i4 = 0; i4 < length2; i4++) {
                            arrayList.add(Array.get(objA4, i4));
                        }
                    } else {
                        arrayList.add(objA4);
                    }
                }
                aVar.a(arrayList);
                return;
            case 7:
                HashMap map = new HashMap();
                for (int i5 = 0; i5 < this.r; i5++) {
                    map.put(aVar.a(), aVar.a());
                }
                aVar.a(map);
                return;
            case 8:
                Object objA5 = aVar.a();
                Object objA6 = aVar.a();
                if (objA5 instanceof List) {
                    List list = (List) objA5;
                    if (objA6 instanceof z) {
                        Number[] numberArrB = ((z) objA6).b();
                        int iIntValue2 = numberArrB[0].intValue();
                        if (iIntValue2 < 0) {
                            iIntValue2 += list.size();
                        }
                        int iIntValue3 = numberArrB[1].intValue();
                        if (iIntValue3 < 0) {
                            iIntValue3 += list.size();
                        }
                        objSubstring = list.subList(iIntValue2, iIntValue3);
                    } else {
                        int iIntValue4 = ((Integer) objA6).intValue();
                        if (iIntValue4 < 0) {
                            iIntValue4 += list.size();
                        }
                        objSubstring = list.get(iIntValue4);
                    }
                } else if (objA5 instanceof Map) {
                    objSubstring = ((Map) objA5).get(objA6);
                } else if (objA5.getClass().isArray()) {
                    if (objA6 instanceof z) {
                        int length3 = Array.getLength(objA5);
                        Number[] numberArrB2 = ((z) objA6).b();
                        int iIntValue5 = numberArrB2[0].intValue();
                        if (iIntValue5 < 0) {
                            iIntValue5 += length3;
                        }
                        int iIntValue6 = numberArrB2[1].intValue();
                        if (iIntValue6 < 0) {
                            iIntValue6 += length3;
                        }
                        int i6 = iIntValue6 - iIntValue5;
                        Object objNewInstance = Array.newInstance(objA5.getClass().getComponentType(), i6);
                        System.arraycopy(objA5, iIntValue5, objNewInstance, 0, i6);
                        objSubstring = objNewInstance;
                    } else {
                        int iIntValue7 = ((Integer) objA6).intValue();
                        if (iIntValue7 < 0) {
                            iIntValue7 += Array.getLength(objA5);
                        }
                        objSubstring = Array.get(objA5, iIntValue7);
                    }
                } else if (objA5 instanceof String) {
                    String str = (String) objA5;
                    if (objA6 instanceof z) {
                        Number[] numberArrB3 = ((z) objA6).b();
                        iIntValue = numberArrB3[0].intValue();
                        length = numberArrB3[1].intValue();
                    } else {
                        length = str.length();
                        iIntValue = ((Integer) objA6).intValue();
                    }
                    if (iIntValue < 0) {
                        iIntValue += str.length();
                    }
                    if (length < 0) {
                        length += str.length();
                    }
                    objSubstring = str.substring(iIntValue, length);
                } else {
                    throw new IllegalArgumentException(objA5.getClass().getName() + " is not entry");
                }
                aVar.a(objSubstring);
                return;
            case 9:
                aVar.a(aVar.a(this.h));
                return;
            case 10:
                try {
                    aVar.a(this.e, Class.forName(this.d));
                    return;
                } catch (Throwable th5) {
                    return;
                }
            case 11:
                a(aVar.a(), aVar.b);
                return;
            case 12:
                Object objA7 = aVar.a();
                Object[] objArr = new Object[this.i];
                for (int i7 = 0; i7 < this.i; i7++) {
                    objArr[i7] = aVar.a();
                }
                a(objA7, objArr, aVar.b);
                return;
            case 13:
                a(aVar.a(this.n), aVar.b);
                return;
            case 14:
                Class<?> clsA = aVar.a(this.n);
                Object[] objArr2 = new Object[this.i];
                for (int i8 = 0; i8 < this.i; i8++) {
                    objArr2[i8] = aVar.a();
                }
                a(clsA, objArr2, aVar.b);
                return;
            case 15:
                Object objA8 = aVar.a();
                x xVar = new x(11);
                xVar.b = this.b;
                xVar.c = this.c;
                xVar.l = (String) aVar.a();
                xVar.a(objA8, aVar.b);
                return;
            case 16:
                Object objA9 = aVar.a();
                x xVar2 = new x(12);
                xVar2.b = this.b;
                xVar2.c = this.c;
                xVar2.p = (String) aVar.a();
                xVar2.i = this.i;
                Object[] objArr3 = new Object[this.i];
                for (int i9 = 0; i9 < this.i; i9++) {
                    objArr3[i9] = aVar.a();
                }
                xVar2.a(objA9, objArr3, aVar.b);
                return;
            case 17:
                Class<?> clsA2 = aVar.a(this.n);
                x xVar3 = new x(13);
                xVar3.b = this.b;
                xVar3.c = this.c;
                xVar3.l = (String) aVar.a();
                xVar3.a(clsA2, aVar.b);
                return;
            case 18:
                Class<?> clsA3 = aVar.a(this.n);
                x xVar4 = new x(14);
                xVar4.b = this.b;
                xVar4.c = this.c;
                xVar4.n = this.n;
                xVar4.p = (String) aVar.a();
                xVar4.i = this.i;
                Object[] objArr4 = new Object[this.i];
                for (int i10 = 0; i10 < this.i; i10++) {
                    objArr4[i10] = aVar.a();
                }
                xVar4.a(clsA3, objArr4, aVar.b);
                return;
            case 19:
                aVar.a(this.h, aVar.a());
                return;
            case 20:
            default:
                return;
            case 21:
                if (!((Boolean) aVar.a()).booleanValue()) {
                    aVar.a = this.g;
                    return;
                }
                return;
            case 22:
                aVar.a = this.g;
                return;
            case 23:
                Object objA10 = aVar.a();
                Object objA11 = aVar.a();
                Object objA12 = aVar.a();
                if (objA10 instanceof List) {
                    List list2 = (List) objA10;
                    int iIntValue8 = ((Integer) objA11).intValue();
                    if (iIntValue8 < 0) {
                        iIntValue8 += list2.size();
                    }
                    list2.set(iIntValue8, objA12);
                    return;
                }
                if (objA10 instanceof Map) {
                    ((Map) objA10).put(objA11, objA12);
                    return;
                } else {
                    if (objA10.getClass().isArray()) {
                        int iIntValue9 = ((Integer) objA11).intValue();
                        if (iIntValue9 < 0) {
                            iIntValue9 += Array.getLength(objA10);
                        }
                        Array.set(objA10, iIntValue9, objA12);
                        return;
                    }
                    throw new IllegalArgumentException(objA10.getClass().getName() + " is not entry");
                }
            case 24:
                b(aVar.a(), aVar.b);
                return;
            case 25:
                Object objA13 = aVar.a();
                x xVar5 = new x(24);
                xVar5.b = this.b;
                xVar5.c = this.c;
                xVar5.l = (String) aVar.a();
                xVar5.b(objA13, aVar.b);
                return;
            case 26:
                b(aVar.a(this.n), aVar.b);
                return;
            case MotionEventCompat.AXIS_RELATIVE_X /* 27 */:
                Class<?> clsA4 = aVar.a(this.n);
                x xVar6 = new x(26);
                xVar6.b = this.b;
                xVar6.c = this.c;
                xVar6.l = (String) aVar.a();
                xVar6.b(clsA4, aVar.b);
                return;
            case MotionEventCompat.AXIS_RELATIVE_Y /* 28 */:
                if (aVar.c != null) {
                    aVar.c.add(aVar.a());
                }
                aVar.d = true;
                aVar.e = true;
                return;
            case 29:
                int i11 = aVar.a;
                if (this.j > 0) {
                    aVar.a = this.j;
                    i = this.j;
                } else {
                    i = aVar.a;
                    int i12 = aVar.a + 1;
                    int i13 = 1;
                    while (i13 > 0) {
                        x xVar7 = aVar.f.get(i12);
                        if (xVar7.a == 29) {
                            i13++;
                        } else if (xVar7.a == 30) {
                            i13--;
                        }
                        if (i13 == 0) {
                            aVar.a = i12;
                            i = i12;
                        }
                        i12++;
                    }
                }
                int i14 = i11 + 1;
                if (i14 == i) {
                    yVar = y.a(this.h, this.i, aVar.f, aVar.g, i14, i, aVar.b);
                } else {
                    yVar = new y(this.h, this.i, aVar.f, aVar.g, i14, i, aVar.b);
                }
                if (this.h != null) {
                    aVar.b(this.h, yVar);
                    return;
                } else {
                    aVar.a(yVar);
                    return;
                }
            case 30:
                aVar.e = true;
                return;
            case 31:
                Object objB = aVar.b(this.h);
                if (!(objB instanceof y)) {
                    if (objB instanceof Method) {
                        aVar.b.a((Method) objB, this.i);
                        return;
                    }
                    throw new NoSuchMethodException(this.h + " at line: " + this.b + "(" + this.c + ")");
                }
                y yVar2 = (y) objB;
                Object[] objArr5 = new Object[this.i];
                for (int i15 = 0; i15 < this.i; i15++) {
                    objArr5[i15] = aVar.a();
                }
                LinkedList<Object> linkedListB = yVar2.b(objArr5);
                if (linkedListB.size() > 0) {
                    aVar.a(linkedListB.get(0));
                    return;
                }
                return;
            case 32:
                Object objA14 = aVar.a();
                if (!(objA14 instanceof y)) {
                    if (objA14 instanceof Method) {
                        aVar.b.a((Method) objA14, this.i);
                        return;
                    }
                    throw new RuntimeException("at line: " + this.b + "(" + this.c + ")");
                }
                y yVar3 = (y) objA14;
                Object[] objArr6 = new Object[this.i];
                for (int i16 = 0; i16 < this.i; i16++) {
                    objArr6[i16] = aVar.a();
                }
                LinkedList<Object> linkedListB2 = yVar3.b(objArr6);
                if (linkedListB2.size() > 0) {
                    aVar.a(linkedListB2.get(0));
                    return;
                }
                return;
            case MotionEventCompat.AXIS_GENERIC_2 /* 33 */:
                aVar.b = aVar.b.b();
                return;
            case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                aVar.b = aVar.b.c();
                return;
            case MotionEventCompat.AXIS_GENERIC_4 /* 35 */:
                try {
                    aVar.a(this.e, aVar.a(this.d));
                    return;
                } catch (Throwable th6) {
                    return;
                }
            case MotionEventCompat.AXIS_GENERIC_5 /* 36 */:
                for (int i17 = 0; i17 < this.o.length; i17++) {
                    aVar.b(this.o[i17], aVar.a());
                }
                return;
            case MotionEventCompat.AXIS_GENERIC_6 /* 37 */:
                for (int i18 = 0; i18 < this.m.length; i18++) {
                    aVar.a(this.m[i18]);
                }
                return;
            case MotionEventCompat.AXIS_GENERIC_7 /* 38 */:
                for (String str2 : this.o) {
                    aVar.a(aVar.b(str2));
                }
                return;
        }
    }

    void a(Object obj, r rVar) throws Throwable {
        Field declaredField;
        if (obj instanceof Map) {
            rVar.a(((Map) obj).get(this.l));
            return;
        }
        if (cn.fly.commons.w.b("006fed+di3hg").equals(this.l) && obj.getClass().isArray()) {
            rVar.a(Integer.valueOf(Array.getLength(obj)));
            return;
        }
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            try {
                declaredField = superclass.getDeclaredField(this.l);
            } catch (Throwable th) {
                declaredField = null;
            }
            if (declaredField != null && !Modifier.isStatic(declaredField.getModifiers())) {
                declaredField.setAccessible(true);
                rVar.a(declaredField.get(obj));
                return;
            }
        }
        x xVar = new x(12);
        xVar.b = this.b;
        xVar.c = this.c;
        xVar.p = cn.fly.commons.w.b("003 diPeh") + Character.toUpperCase(this.l.charAt(0)) + this.l.substring(1);
        xVar.i = 0;
        xVar.a(obj, new Object[0], rVar);
    }

    void b(Object obj, r rVar) throws Throwable {
        Field declaredField;
        Object objA = rVar.a();
        if (obj instanceof Map) {
            ((Map) obj).put(this.l, objA);
            return;
        }
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            try {
                declaredField = superclass.getDeclaredField(this.l);
            } catch (Throwable th) {
                declaredField = null;
            }
            if (declaredField != null && !Modifier.isStatic(declaredField.getModifiers())) {
                declaredField.setAccessible(true);
                declaredField.set(obj, objA);
                return;
            }
        }
        x xVar = new x(12);
        xVar.b = this.b;
        xVar.c = this.c;
        xVar.p = "set" + Character.toUpperCase(this.l.charAt(0)) + this.l.substring(1);
        xVar.i = 1;
        xVar.a(obj, new Object[]{objA}, rVar);
    }

    void a(Class<?> cls, r rVar) throws Throwable {
        Field declaredField;
        while (true) {
            if (cls != null) {
                if ("class".equals(this.l)) {
                    rVar.a(cls);
                    return;
                }
                if (cls.equals(w.class) && cn.fly.commons.w.b("0078ccFe4ciehchcj<d").equals(this.l)) {
                    rVar.a((Object) 70);
                    return;
                }
                if (cls.isEnum()) {
                    Object[] enumConstants = cls.getEnumConstants();
                    if (enumConstants != null) {
                        for (Object obj : enumConstants) {
                            if (((Enum) obj).name().equals(this.l)) {
                                rVar.a(obj);
                                return;
                            }
                        }
                    } else {
                        continue;
                    }
                } else {
                    try {
                        declaredField = cls.getDeclaredField(this.l);
                    } catch (Throwable th) {
                        declaredField = null;
                    }
                    if (declaredField != null && Modifier.isStatic(declaredField.getModifiers())) {
                        declaredField.setAccessible(true);
                        rVar.a(declaredField.get(null));
                        return;
                    }
                    cls = cls.getSuperclass();
                }
            } else {
                x xVar = new x(14);
                xVar.b = this.b;
                xVar.c = this.c;
                xVar.n = this.n;
                xVar.p = cn.fly.commons.w.b("003$di%eh") + Character.toUpperCase(this.l.charAt(0)) + this.l.substring(1);
                xVar.i = 1;
                xVar.a(cls, new Object[0], rVar);
                return;
            }
        }
    }

    void b(Class<?> cls, r rVar) throws Throwable {
        Field declaredField;
        Object objA = rVar.a();
        while (cls != null) {
            try {
                declaredField = cls.getDeclaredField(this.l);
            } catch (Throwable th) {
                declaredField = null;
            }
            if (declaredField != null && Modifier.isStatic(declaredField.getModifiers())) {
                declaredField.setAccessible(true);
                declaredField.set(null, objA);
                return;
            }
            cls = cls.getSuperclass();
        }
        x xVar = new x(14);
        xVar.b = this.b;
        xVar.c = this.c;
        xVar.n = this.n;
        xVar.p = "set" + Character.toUpperCase(this.l.charAt(0)) + this.l.substring(1);
        xVar.i = 1;
        xVar.a(cls, new Object[]{objA}, rVar);
    }

    void a(Class<?> cls, Object[] objArr, r rVar) throws Throwable {
        Class<?>[] parameterTypes;
        boolean[] zArr;
        boolean[] zArrA;
        Object[] objArrA;
        Object[] objArrA2;
        Object[] objArrA3;
        Object[] objArrA4;
        Map map;
        List arrayList;
        Class<?> superclass = cls;
        if ("new".equals(this.p)) {
            if (List.class.isAssignableFrom(superclass) && objArr.length == 1 && objArr[0] != null && objArr[0].getClass().isArray()) {
                int length = Array.getLength(objArr[0]);
                if (superclass.equals(List.class)) {
                    arrayList = new ArrayList(length);
                } else {
                    arrayList = (List) cls.newInstance();
                }
                for (int i = 0; i < length; i++) {
                    arrayList.add(Array.get(objArr[0], i));
                }
                rVar.a(arrayList);
                return;
            }
            if (Map.class.isAssignableFrom(superclass) && objArr.length == 1 && objArr[0] != null) {
                if (superclass.equals(Map.class)) {
                    map = new HashMap();
                } else {
                    map = (Map) cls.newInstance();
                }
                if (objArr[0] instanceof Map) {
                    map.putAll((Map) objArr[0]);
                } else {
                    Class<?> cls2 = Class.forName("org.json.JSONObject");
                    a(map, a(objArr[0], cls2), cls2, Class.forName("org.json.JSONArray"));
                }
                rVar.a(map);
                return;
            }
            if (superclass.equals(z.class)) {
                if (objArr.length == 2) {
                    rVar.a(new z((Number) objArr[0], (Number) objArr[1], null));
                    return;
                } else {
                    if (objArr.length == 3) {
                        rVar.a(new z((Number) objArr[0], (Number) objArr[1], (Number) objArr[2]));
                        return;
                    }
                    throw new NoSuchMethodException("method name: new at line: " + this.b + "(" + this.c + ")");
                }
            }
            boolean[][] zArr2 = new boolean[2][];
            Constructor constructorA = rVar.g().a(superclass, objArr, zArr2);
            if (constructorA != null) {
                if (zArr2[1][0]) {
                    objArrA4 = objArr;
                } else {
                    objArrA4 = rVar.g().a(rVar, constructorA.getParameterTypes(), objArr, zArr2[0]);
                }
                constructorA.setAccessible(true);
                rVar.a(constructorA.newInstance(objArrA4));
                return;
            }
            for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
                Class<?>[] parameterTypes2 = constructor.getParameterTypes();
                boolean[] zArr3 = new boolean[1];
                boolean[] zArrA2 = rVar.g().a(parameterTypes2, objArr, zArr3);
                if (zArrA2 != null) {
                    if (zArr3[0]) {
                        objArrA3 = objArr;
                    } else {
                        objArrA3 = rVar.g().a(rVar, parameterTypes2, objArr, zArrA2);
                    }
                    constructor.setAccessible(true);
                    rVar.a(constructor.newInstance(objArrA3));
                    return;
                }
            }
            throw new NoSuchMethodException("method name: new at line: " + this.b + "(" + this.c + ")");
        }
        if ("fromJson".equals(this.p) && Map.class.isAssignableFrom(superclass) && objArr.length == 1 && objArr[0] != null) {
            this.p = "new";
            a(cls, objArr, rVar);
            return;
        }
        if (superclass.equals(Array.class)) {
            if (this.p.equals(cn.fly.commons.w.b("011deNefddAd*ehOhcdbe")) && objArr.length == 2 && (objArr[1] instanceof Integer)) {
                rVar.a(Array.newInstance((Class<?>) objArr[0], ((Integer) objArr[1]).intValue()));
                return;
            }
            if ("copy".equals(this.p)) {
                if (this.i == 5) {
                    System.arraycopy(objArr[0], Integer.parseInt(String.valueOf(objArr[1])), objArr[2], Integer.parseInt(String.valueOf(objArr[3])), Integer.parseInt(String.valueOf(objArr[44])));
                    return;
                } else {
                    if (this.i == 2) {
                        System.arraycopy(objArr[0], 0, objArr[1], 0, Math.min(Array.getLength(objArr[0]), Array.getLength(objArr[1])));
                        return;
                    }
                    throw new NoSuchMethodException("method name: copy at line: " + this.b + "(" + this.c + ")");
                }
            }
        } else if ("quit".equals(this.p) && superclass.equals(w.class)) {
            rVar.e();
            return;
        }
        if (rVar.g().a((Object) null, cls, this.p, objArr, rVar)) {
            return;
        }
        for (Class<?> superclass2 = superclass; superclass2 != null; superclass2 = superclass2.getSuperclass()) {
            boolean[][] zArr4 = new boolean[2][];
            Method methodA = rVar.g().a(superclass2, this.p, true, objArr, zArr4);
            if (methodA != null) {
                if (zArr4[1][0]) {
                    objArrA2 = objArr;
                } else {
                    objArrA2 = rVar.g().a(rVar, methodA.getParameterTypes(), objArr, zArr4[0]);
                }
                methodA.setAccessible(true);
                if (methodA.getReturnType() == Void.TYPE) {
                    methodA.invoke(null, objArrA2);
                    return;
                } else {
                    rVar.a(methodA.invoke(null, objArrA2));
                    return;
                }
            }
        }
        while (superclass != null) {
            for (Method method : superclass.getDeclaredMethods()) {
                if (method.getName().equals(this.p) && Modifier.isStatic(method.getModifiers()) && (zArrA = rVar.g().a((parameterTypes = method.getParameterTypes()), objArr, (zArr = new boolean[1]))) != null) {
                    if (zArr[0]) {
                        objArrA = objArr;
                    } else {
                        objArrA = rVar.g().a(rVar, parameterTypes, objArr, zArrA);
                    }
                    method.setAccessible(true);
                    if (method.getReturnType() == Void.TYPE) {
                        method.invoke(null, objArrA);
                        return;
                    } else {
                        rVar.a(method.invoke(null, objArrA));
                        return;
                    }
                }
            }
            superclass = superclass.getSuperclass();
        }
        throw new NoSuchMethodException("method name: " + this.p + " at line: " + this.b + "(" + this.c + ")");
    }

    private Object a(Object obj, Class<?> cls) throws Throwable {
        if (obj instanceof ByteArrayOutputStream) {
            return a(((ByteArrayOutputStream) obj).toByteArray(), cls);
        }
        if (obj instanceof byte[]) {
            return a(new String((byte[]) obj, "utf-8"), cls);
        }
        if ((obj instanceof StringBuffer) || (obj instanceof StringBuilder)) {
            return a(obj.toString(), cls);
        }
        if (obj instanceof String) {
            return cls.getConstructor(String.class).newInstance(obj);
        }
        if (obj.getClass().equals(cls)) {
            return obj;
        }
        throw new ClassCastException("Failed to cast " + obj + " to be " + cls.getName() + " at line: " + this.b + "(" + this.c + ")");
    }

    private void a(Map map, Object obj, Class<?> cls, Class<?> cls2) throws Throwable {
        Field declaredField = cls.getDeclaredField("nameValuePairs");
        declaredField.setAccessible(true);
        Map map2 = (Map) declaredField.get(obj);
        Field declaredField2 = cls.getDeclaredField("NULL");
        declaredField2.setAccessible(true);
        Object obj2 = declaredField2.get(null);
        for (Map.Entry entry : map2.entrySet()) {
            map.put(entry.getKey(), a(entry.getValue(), obj2, cls, cls2));
        }
    }

    private Object a(Object obj, Object obj2, Class<?> cls, Class<?> cls2) throws Throwable {
        if (obj == null || obj2.equals(obj)) {
            return null;
        }
        if (obj.getClass().equals(cls)) {
            HashMap map = new HashMap();
            a((Map) map, obj, cls, cls2);
            return map;
        }
        if (obj.getClass().equals(cls2)) {
            Field declaredField = cls2.getDeclaredField("values");
            declaredField.setAccessible(true);
            List list = (List) declaredField.get(obj);
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(a(it.next(), obj2, cls, cls2));
            }
            return arrayList;
        }
        return obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:308:0x05e4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void a(Object obj, Object[] objArr, r rVar) throws Throwable {
        byte[] bArr;
        String[] strArr;
        y yVar;
        String strValueOf;
        Class<?>[] parameterTypes;
        boolean[] zArr;
        boolean[] zArrA;
        Class<?>[] clsArr;
        Object[] objArrA = objArr;
        int i = 0;
        if (obj instanceof Map) {
            Map map = (Map) obj;
            Object obj2 = map.get(this.p);
            if (obj2 == null) {
                if ((cn.fly.commons.w.b("005iQcicjdhdb").equals(this.p) || cn.fly.commons.w.b("011Ncf[dMehHcDdeEeQfkcicjdhdb").equals(this.p)) && objArrA.length == 1 && objArrA[0] != null) {
                    if (objArrA[0] instanceof Class) {
                        clsArr = new Class[]{(Class) objArrA[0]};
                    } else if (objArrA[0] instanceof List) {
                        List list = (List) objArrA[0];
                        clsArr = (Class[]) list.toArray(new Class[list.size()]);
                    } else {
                        throw new NoSuchMethodException("method name: " + this.p + " at line: " + this.b + "(" + this.c + ")");
                    }
                    rVar.a(rVar.a(obj, cn.fly.commons.w.b("005i*cicjdhdb").equals(this.p), clsArr));
                    return;
                }
                if ("iterator".equals(this.p) && objArrA.length == 0) {
                    rVar.a(map.entrySet().iterator());
                    return;
                } else if ("toJson".equals(this.p) && objArrA.length == 0) {
                    rVar.a(Class.forName("org.json.JSONObject").getDeclaredConstructor(Map.class).newInstance(obj));
                    return;
                }
            } else {
                if (obj2 instanceof y) {
                    LinkedList<Object> linkedListB = ((y) obj2).b(objArrA);
                    if (linkedListB.size() > 0) {
                        rVar.a(linkedListB.get(0));
                        return;
                    }
                    return;
                }
                if (obj2 instanceof Method) {
                    rVar.a((Method) obj2, objArrA);
                    return;
                }
            }
        } else if (obj instanceof y) {
            y yVar2 = (y) obj;
            if (!cn.fly.commons.w.b("004heSehIh").equals(this.p)) {
                if (cn.fly.commons.w.b("008b6cfcicidbch]d3di").equals(this.p)) {
                    rVar.a(yVar2.a(rVar, this.b, this.c));
                    return;
                }
            } else {
                rVar.a(yVar2.a(objArrA));
                return;
            }
        } else if (obj instanceof Method) {
            if (!cn.fly.commons.w.b("004heNeh$h").equals(this.p)) {
                if (cn.fly.commons.w.b("013MehPeh9ecHbbe,ehehcheeNfe").equals(this.p) && objArrA.length == 1) {
                    ((Method) obj).setAccessible(((Boolean) objArrA[0]).booleanValue());
                    return;
                }
            } else {
                y.a aVar = new y.a();
                r rVarB = rVar.b();
                try {
                    rVarB.a((Method) obj, objArrA);
                    aVar.b = rVarB.a();
                } catch (Throwable th) {
                    aVar.a = th;
                }
                rVar.a(aVar);
                return;
            }
        } else if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            int size = collection.size();
            if ("toArray".equals(this.p) && objArrA.length == 1 && objArrA[0] != null && (objArrA[0] instanceof Class)) {
                Object objNewInstance = Array.newInstance((Class<?>) objArrA[0], size);
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    Array.set(objNewInstance, i, it.next());
                    i++;
                }
                rVar.a(objNewInstance);
                return;
            }
            if ("filter".equals(this.p) && objArrA.length == 1 && (objArrA[0] instanceof String)) {
                ArrayList arrayList = new ArrayList();
                String str = (String) objArrA[0];
                for (Object obj3 : collection) {
                    if ((obj3 instanceof String) && ((String) obj3).matches(str)) {
                        arrayList.add(obj3);
                    }
                }
                rVar.a(arrayList);
                return;
            }
            if ("replace".equals(this.p) && objArrA.length == 2 && (objArrA[0] instanceof String) && (objArrA[1] instanceof String)) {
                ArrayList arrayList2 = new ArrayList();
                String str2 = (String) objArrA[0];
                String str3 = (String) objArrA[1];
                for (Object obj4 : collection) {
                    if (obj4 instanceof String) {
                        arrayList2.add(((String) obj4).replace(str2, str3));
                    } else {
                        arrayList2.add(obj4);
                    }
                }
                rVar.a(arrayList2);
                return;
            }
        } else if (obj.getClass().isArray()) {
            if ("iterator".equals(this.p) && objArrA.length == 0) {
                ArrayList arrayList3 = new ArrayList();
                int length = Array.getLength(obj);
                while (i < length) {
                    arrayList3.add(Array.get(obj, i));
                    i++;
                }
                rVar.a(arrayList3.iterator());
                return;
            }
            if ("toList".equals(this.p) && objArrA.length == 0) {
                ArrayList arrayList4 = new ArrayList();
                int length2 = Array.getLength(obj);
                while (i < length2) {
                    arrayList4.add(Array.get(obj, i));
                    i++;
                }
                rVar.a(arrayList4);
                return;
            }
            if (obj.getClass().getComponentType() == Byte.TYPE) {
                if (cn.fly.commons.w.b("003Mcecbgh").equals(this.p) && objArrA.length == 0) {
                    byte[] bArr2 = (byte[]) obj;
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr2, 0, bArr2.length);
                    Object objA = a(byteArrayInputStream);
                    byteArrayInputStream.close();
                    rVar.a(objA);
                    return;
                }
                if ("hex".equals(this.p) && objArrA.length == 0) {
                    rVar.a(a((byte[]) obj));
                    return;
                } else if ("sha".equals(this.p) && objArrA.length == 1) {
                    MessageDigest messageDigest = MessageDigest.getInstance((String) objArrA[0]);
                    messageDigest.update((byte[]) obj);
                    rVar.a(messageDigest.digest());
                    return;
                }
            }
        } else if (Iterator.class.isAssignableFrom(obj.getClass())) {
            if ("hasNext".equals(this.p)) {
                rVar.a(Boolean.valueOf(((Iterator) obj).hasNext()));
                return;
            } else if ("next".equals(this.p)) {
                rVar.a(((Iterator) obj).next());
                return;
            } else if ("remove".equals(this.p)) {
                ((Iterator) obj).remove();
                return;
            }
        } else if (obj instanceof z.a) {
            if ("hasNext".equals(this.p) && objArrA.length == 0) {
                rVar.a(Boolean.valueOf(((z.a) obj).a()));
                return;
            } else if ("next".equals(this.p) && objArrA.length == 0) {
                rVar.a(((z.a) obj).b());
                return;
            }
        } else if (obj instanceof z) {
            if ("iterator".equals(this.p) && objArrA.length == 0) {
                rVar.a(((z) obj).a());
                return;
            }
            if ("isInRange".equals(this.p) && objArrA.length == 1) {
                rVar.a(Boolean.valueOf(((z) obj).a((Number) objArrA[0])));
                return;
            }
            if ("contains".equals(this.p) && objArrA.length == 1) {
                rVar.a(Boolean.valueOf(((z) obj).b((Number) objArrA[0])));
                return;
            } else if ("boundary".equals(this.p) && objArrA.length == 0) {
                rVar.a(((z) obj).b());
                return;
            }
        } else if (obj instanceof String) {
            if ("getBytes".equals(this.p)) {
                if (objArrA.length == 0) {
                    rVar.a(((String) obj).getBytes());
                    return;
                } else if (objArrA.length == 1 && (objArrA[0] instanceof String)) {
                    rVar.a(((String) obj).getBytes((String) objArrA[0]));
                    return;
                }
            } else if ("input".equals(this.p)) {
                if (objArrA.length == 0) {
                    rVar.a(new FileInputStream((String) obj));
                    return;
                } else if (objArrA.length == 1 && (objArrA[0] instanceof y)) {
                    FileInputStream fileInputStream = new FileInputStream((String) obj);
                    ((y) objArrA[0]).b(fileInputStream);
                    fileInputStream.close();
                    return;
                }
            } else if (!"output".equals(this.p)) {
                File file = null;
                String strValueOf2 = null;
                String strValueOf3 = null;
                FileInputStream fileInputStream2 = null;
                arrayList = null;
                Collection arrayList5 = null;
                file = null;
                if (!cn.fly.commons.w.b("0124ci*ecDcbfbcicjcefbchVfe").equals(this.p)) {
                    if (!cn.fly.commons.w.b("011Yefcich0he[ebcjfbchIfe").equals(this.p)) {
                        if (!cn.fly.commons.w.b("009;ci7ecOcbedch)de6eh").equals(this.p)) {
                            if (!cn.fly.commons.w.b("010SefcichDheSedch3de+eh").equals(this.p)) {
                                if (!cn.fly.commons.w.b("004e3dhYeb").equals(this.p)) {
                                    if (cn.fly.commons.w.b("007FdecicjceejJe+dh").equals(this.p) && objArrA.length == 0) {
                                        String str4 = (String) obj;
                                        int length3 = str4.length();
                                        if (length3 % 2 == 1) {
                                            length3++;
                                            bArr = new byte[length3 / 2];
                                            str4 = "0" + str4;
                                        } else {
                                            bArr = new byte[length3 / 2];
                                        }
                                        int i2 = 0;
                                        while (i < length3) {
                                            int i3 = i + 2;
                                            bArr[i2] = (byte) Integer.parseInt(str4.substring(i, i3), 16);
                                            i2++;
                                            i = i3;
                                        }
                                        rVar.a(bArr);
                                        return;
                                    }
                                } else {
                                    if (objArrA.length == 0) {
                                        rVar.a(Runtime.getRuntime().exec((String) obj));
                                        return;
                                    }
                                    if (objArrA.length == 1 || objArrA.length == 2) {
                                        if (objArrA[0] instanceof String[]) {
                                            strArr = (String[]) objArrA[0];
                                        } else if (!(objArrA[0] instanceof List)) {
                                            strArr = null;
                                        } else {
                                            List list2 = (List) objArrA[0];
                                            int size2 = list2.size();
                                            String[] strArr2 = new String[size2];
                                            for (int i4 = 0; i4 < size2; i4++) {
                                                Object obj5 = list2.get(i4);
                                                strArr2[i4] = obj5 == null ? null : String.valueOf(obj5);
                                            }
                                            strArr = strArr2;
                                        }
                                        if (objArrA.length == 2 && (objArrA[1] instanceof File)) {
                                            file = (File) objArrA[1];
                                        }
                                        if (strArr != null) {
                                            rVar.a(Runtime.getRuntime().exec((String) obj, strArr, file));
                                            return;
                                        }
                                    }
                                }
                            } else {
                                String str5 = "utf-8";
                                if (objArrA.length >= 1) {
                                    if (objArrA.length == 2 && (objArrA[1] instanceof String)) {
                                        str5 = (String) objArrA[1];
                                    }
                                    if (objArrA[0] instanceof String) {
                                        arrayList5 = new ArrayList();
                                        arrayList5.add(objArrA[0]);
                                    } else if (objArrA[0] instanceof Collection) {
                                        arrayList5 = (Collection) objArrA[0];
                                    } else if (objArrA[0].getClass().isArray()) {
                                        arrayList5 = new ArrayList();
                                        int length4 = Array.getLength(objArrA[0]);
                                        for (int i5 = 0; i5 < length4; i5++) {
                                            arrayList5.add(Array.get(objArrA[0], i5));
                                        }
                                    }
                                }
                                if (arrayList5 != null) {
                                    FileOutputStream fileOutputStream = new FileOutputStream((String) obj);
                                    Iterator it2 = arrayList5.iterator();
                                    while (it2.hasNext()) {
                                        fileOutputStream.write((it2.next() + "\r\n").getBytes(str5));
                                    }
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                    return;
                                }
                            }
                        } else {
                            String str6 = "utf-8";
                            if (objArrA.length == 0) {
                                yVar = null;
                                fileInputStream2 = new FileInputStream((String) obj);
                            } else if (objArrA.length == 1) {
                                if (objArrA[0] instanceof String) {
                                    fileInputStream2 = new FileInputStream((String) obj);
                                    str6 = (String) objArrA[0];
                                    yVar = null;
                                } else if (objArrA[0] instanceof y) {
                                    fileInputStream2 = new FileInputStream((String) obj);
                                    yVar = (y) objArrA[0];
                                } else {
                                    yVar = null;
                                }
                            } else if (objArrA.length == 2 && (objArrA[0] instanceof String) && (objArrA[1] instanceof y)) {
                                fileInputStream2 = new FileInputStream((String) obj);
                                str6 = (String) objArrA[0];
                                yVar = (y) objArrA[1];
                            }
                            if (fileInputStream2 != null) {
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream2, str6));
                                String line = bufferedReader.readLine();
                                if (yVar == null) {
                                    ArrayList arrayList6 = new ArrayList();
                                    while (line != null) {
                                        arrayList6.add(line);
                                        line = bufferedReader.readLine();
                                    }
                                    rVar.a(arrayList6);
                                } else {
                                    while (line != null) {
                                        yVar.b(line);
                                        line = bufferedReader.readLine();
                                    }
                                }
                                bufferedReader.close();
                                return;
                            }
                        }
                    } else {
                        if (objArrA.length == 1) {
                            strValueOf3 = String.valueOf(objArrA[0]);
                            strValueOf = "utf-8";
                        } else if (objArrA.length != 2) {
                            strValueOf = null;
                        } else {
                            strValueOf3 = String.valueOf(objArrA[0]);
                            strValueOf = String.valueOf(objArrA[1]);
                        }
                        if (strValueOf3 != null) {
                            FileOutputStream fileOutputStream2 = new FileOutputStream(strValueOf3);
                            fileOutputStream2.write(((String) obj).getBytes(strValueOf));
                            fileOutputStream2.flush();
                            fileOutputStream2.close();
                            return;
                        }
                    }
                } else {
                    if (objArrA.length == 0) {
                        strValueOf2 = "utf-8";
                    } else if (objArrA.length == 1) {
                        strValueOf2 = String.valueOf(objArrA[0]);
                    }
                    if (strValueOf2 != null) {
                        FileInputStream fileInputStream3 = new FileInputStream((String) obj);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr3 = new byte[4096];
                        for (int i6 = fileInputStream3.read(bArr3); i6 != -1; i6 = fileInputStream3.read(bArr3)) {
                            byteArrayOutputStream.write(bArr3, 0, i6);
                        }
                        fileInputStream3.close();
                        byteArrayOutputStream.flush();
                        byteArrayOutputStream.close();
                        rVar.a(new String(byteArrayOutputStream.toByteArray(), strValueOf2));
                        return;
                    }
                }
            } else if (objArrA.length == 0) {
                rVar.a(new FileOutputStream((String) obj));
                return;
            } else if (objArrA.length == 1 && (objArrA[0] instanceof y)) {
                FileOutputStream fileOutputStream3 = new FileOutputStream((String) obj);
                ((y) objArrA[0]).b(fileOutputStream3);
                fileOutputStream3.flush();
                fileOutputStream3.close();
                return;
            }
        } else if (obj instanceof InputStream) {
            if (!cn.fly.commons.w.b("017hWcjek6chcCdd_di?cfSh%dkAh;ciJecYce").equals(this.p) || objArrA.length != 0) {
                if (!cn.fly.commons.w.b("021h'cjeicfdede4e8ci7e)cbdd>diKcf<hPdkIh>ciCec^ce").equals(this.p) || objArrA.length != 0) {
                    if (!cn.fly.commons.w.b("017h>cjhchiddfkdd;diScfEhTdkZhYciZec'ce").equals(this.p) || objArrA.length != 0) {
                        if (!cn.fly.commons.w.b("019h:cjfgeegf7ebh+dd[di-cf@h0dkLhKciJec2ce").equals(this.p) || objArrA.length != 0) {
                            if (cn.fly.commons.w.b("003Vcecbgh").equals(this.p) && objArrA.length == 0) {
                                a((InputStream) obj);
                            }
                        } else {
                            rVar.a(new ObjectInputStream((InputStream) obj));
                            return;
                        }
                    } else {
                        rVar.a(new GZIPInputStream((InputStream) obj));
                        return;
                    }
                } else {
                    rVar.a(new BufferedInputStream((InputStream) obj));
                    return;
                }
            } else {
                rVar.a(new DataInputStream((InputStream) obj));
                return;
            }
        } else if (obj instanceof OutputStream) {
            if (!cn.fly.commons.w.b("018h8cjekPchc4fgcf7hi,cfYhZdk+hWci*ecKce").equals(this.p) || objArrA.length != 0) {
                if (!cn.fly.commons.w.b("022h;cjeicfdedeZe!ci[e0cbfgcfYhiTcfMh-dk[hZciPec%ce").equals(this.p) || objArrA.length != 0) {
                    if (!cn.fly.commons.w.b("018hFcjhchiddfkfgcf hiHcf!hCdk)h=ciQec,ce").equals(this.p) || objArrA.length != 0) {
                        if (cn.fly.commons.w.b("020hGcjfgeegf*ebh?fgcfPhi%cf'h^dkDhYci-ecIce").equals(this.p) && objArrA.length == 0) {
                            rVar.a(new ObjectOutputStream((OutputStream) obj));
                            return;
                        }
                    } else {
                        rVar.a(new GZIPOutputStream((OutputStream) obj));
                        return;
                    }
                } else {
                    rVar.a(new BufferedOutputStream((OutputStream) obj));
                    return;
                }
            } else {
                rVar.a(new DataOutputStream((OutputStream) obj));
                return;
            }
        } else if (obj instanceof Class) {
            if (cn.fly.commons.w.b("006Zchce:iKcjci%h").equals(this.p)) {
                if (objArrA.length == 0) {
                    Class<?> cls = (Class) obj;
                    rVar.a(cls.getSimpleName(), cls);
                    return;
                } else if (objArrA.length == 1 && (objArrA[0] instanceof String)) {
                    rVar.a((String) objArrA[0], (Class<?>) obj);
                    return;
                }
            }
        } else if (obj instanceof Throwable) {
            if (cn.fly.commons.w.b("005hgMcicjef").equals(this.p) && objArrA.length == 0) {
                throw ((Throwable) obj);
            }
        } else if (obj instanceof BufferedReader) {
            if ("filter".equals(this.p) && objArrA.length > 0 && (objArrA[0] instanceof String)) {
                String str7 = (String) objArrA[0];
                boolean z = objArrA.length == 2 && Boolean.TRUE.equals(objArrA[1]);
                ArrayList arrayList7 = new ArrayList();
                BufferedReader bufferedReader2 = (BufferedReader) obj;
                String line2 = bufferedReader2.readLine();
                while (line2 != null) {
                    if (line2.matches(str7)) {
                        if (z) {
                            line2 = line2.trim();
                        }
                        arrayList7.add(line2);
                    }
                    line2 = bufferedReader2.readLine();
                }
                rVar.a(arrayList7);
                return;
            }
        } else if (AccessibleObject.class.isAssignableFrom(obj.getClass()) && cn.fly.commons.w.b("013YehRehAecLbbeBehehchee-fe").equals(this.p) && objArrA.length == 1) {
            ((AccessibleObject) obj).setAccessible(((Boolean) objArrA[0]).booleanValue());
            return;
        }
        if (cn.fly.commons.w.b("004f*cj^bHdg").equals(this.p) && objArrA.length > 0 && (objArrA[0] instanceof y)) {
            synchronized (obj) {
                y yVar3 = (y) objArrA[0];
                int length5 = objArrA.length - 1;
                Object[] objArr2 = new Object[length5];
                if (objArrA.length > 1) {
                    System.arraycopy(objArrA, 1, objArr2, 0, length5);
                }
                LinkedList<Object> linkedListB2 = yVar3.b(objArr2);
                if (!linkedListB2.isEmpty()) {
                    rVar.a(linkedListB2.get(0));
                }
            }
            return;
        }
        Class<?> superclass = obj.getClass();
        if (rVar.g().a(obj, superclass, this.p, objArr, rVar)) {
            return;
        }
        for (Class<?> superclass2 = superclass; superclass2 != null; superclass2 = superclass2.getSuperclass()) {
            boolean[][] zArr2 = new boolean[2][];
            Method methodA = rVar.g().a(superclass2, this.p, false, objArr, zArr2);
            if (methodA != null) {
                if (!zArr2[1][0]) {
                    objArrA = rVar.g().a(rVar, methodA.getParameterTypes(), objArrA, zArr2[0]);
                }
                methodA.setAccessible(true);
                if (methodA.getReturnType() == Void.TYPE) {
                    methodA.invoke(obj, objArrA);
                    return;
                } else {
                    rVar.a(methodA.invoke(obj, objArrA));
                    return;
                }
            }
        }
        while (superclass != null) {
            for (Method method : superclass.getDeclaredMethods()) {
                if (method.getName().equals(this.p) && !Modifier.isStatic(method.getModifiers()) && (zArrA = rVar.g().a((parameterTypes = method.getParameterTypes()), objArrA, (zArr = new boolean[1]))) != null) {
                    if (!zArr[0]) {
                        objArrA = rVar.g().a(rVar, parameterTypes, objArrA, zArrA);
                    }
                    method.setAccessible(true);
                    if (method.getReturnType() == Void.TYPE) {
                        method.invoke(obj, objArrA);
                        return;
                    } else {
                        rVar.a(method.invoke(obj, objArrA));
                        return;
                    }
                }
            }
            superclass = superclass.getSuperclass();
        }
        throw new NoSuchMethodException("method name: " + this.p + " at line: " + this.b + "(" + this.c + ")");
    }

    private String a(InputStream inputStream) throws Throwable {
        if (inputStream == null) {
            return null;
        }
        byte[] bArr = new byte[1024];
        MessageDigest messageDigest = MessageDigest.getInstance(cn.fly.commons.w.b("003=gbekgh"));
        int i = inputStream.read(bArr);
        while (i != -1) {
            messageDigest.update(bArr, 0, i);
            i = inputStream.read(bArr);
        }
        return a(messageDigest.digest());
    }

    private String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(String.format("%02x", Byte.valueOf(b)));
        }
        return stringBuffer.toString();
    }

    public static class a {
        public int a;
        public r b;
        public List<Object> c;
        public boolean d;
        public boolean e;
        public ArrayList<x> f;
        public ArrayList<Object> g;

        public Object a() {
            return this.b.a();
        }

        public void a(Object obj) {
            this.b.a(obj);
        }

        public Class<?> a(String str) {
            return this.b.b(str);
        }

        public void a(String str, Class<?> cls) {
            this.b.a(str, cls);
        }

        public Object b(String str) {
            return this.b.a(str);
        }

        public void a(String str, Object obj) {
            this.b.b(str, obj);
        }

        public void b(String str, Object obj) {
            this.b.a(str, obj);
        }
    }
}