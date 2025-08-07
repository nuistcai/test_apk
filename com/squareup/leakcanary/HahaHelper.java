package com.squareup.leakcanary;

import com.squareup.haha.perflib.ArrayInstance;
import com.squareup.haha.perflib.ClassInstance;
import com.squareup.haha.perflib.ClassObj;
import com.squareup.haha.perflib.Field;
import com.squareup.haha.perflib.Instance;
import com.squareup.haha.perflib.Type;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class HahaHelper {
    private static final Set<String> WRAPPER_TYPES = new HashSet(Arrays.asList(Boolean.class.getName(), Character.class.getName(), Float.class.getName(), Double.class.getName(), Byte.class.getName(), Short.class.getName(), Integer.class.getName(), Long.class.getName()));

    static String fieldToString(Map.Entry<Field, Object> entry) {
        return fieldToString(entry.getKey(), entry.getValue());
    }

    static String fieldToString(ClassInstance.FieldValue fieldValue) {
        return fieldToString(fieldValue.getField(), fieldValue.getValue());
    }

    static String fieldToString(Field field, Object value) {
        return field.getName() + " = " + value;
    }

    static String threadName(Instance holder) {
        List<ClassInstance.FieldValue> values = classInstanceValues(holder);
        Object nameField = fieldValue(values, "name");
        if (nameField == null) {
            return "Thread name not available";
        }
        return asString(nameField);
    }

    static boolean extendsThread(ClassObj clazz) {
        for (ClassObj parentClass = clazz; parentClass.getSuperClassObj() != null; parentClass = parentClass.getSuperClassObj()) {
            if (clazz.getClassName().equals(Thread.class.getName())) {
                return true;
            }
        }
        return false;
    }

    static String asString(Object stringObject) {
        Instance instance = (Instance) stringObject;
        List<ClassInstance.FieldValue> values = classInstanceValues(instance);
        Integer count = (Integer) fieldValue(values, "count");
        Preconditions.checkNotNull(count, "count");
        if (count.intValue() == 0) {
            return "";
        }
        Object value = fieldValue(values, "value");
        Preconditions.checkNotNull(value, "value");
        if (isCharArray(value)) {
            ArrayInstance array = (ArrayInstance) value;
            Integer offset = 0;
            if (hasField(values, "offset")) {
                offset = (Integer) fieldValue(values, "offset");
                Preconditions.checkNotNull(offset, "offset");
            }
            char[] chars = array.asCharArray(offset.intValue(), count.intValue());
            return new String(chars);
        }
        if (isByteArray(value)) {
            ArrayInstance array2 = (ArrayInstance) value;
            try {
                Class cls = Integer.TYPE;
                Method asRawByteArray = ArrayInstance.class.getDeclaredMethod("asRawByteArray", cls, cls);
                asRawByteArray.setAccessible(true);
                byte[] rawByteArray = (byte[]) asRawByteArray.invoke(array2, 0, count);
                return new String(rawByteArray, Charset.forName("UTF-8"));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e2) {
                throw new RuntimeException(e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3);
            }
        }
        throw new UnsupportedOperationException("Could not find char array in " + instance);
    }

    public static boolean isPrimitiveWrapper(Object value) {
        if (!(value instanceof ClassInstance)) {
            return false;
        }
        return WRAPPER_TYPES.contains(((ClassInstance) value).getClassObj().getClassName());
    }

    public static boolean isPrimitiveOrWrapperArray(Object value) {
        if (!(value instanceof ArrayInstance)) {
            return false;
        }
        ArrayInstance arrayInstance = (ArrayInstance) value;
        if (arrayInstance.getArrayType() != Type.OBJECT) {
            return true;
        }
        return WRAPPER_TYPES.contains(arrayInstance.getClassObj().getClassName());
    }

    private static boolean isCharArray(Object value) {
        return (value instanceof ArrayInstance) && ((ArrayInstance) value).getArrayType() == Type.CHAR;
    }

    private static boolean isByteArray(Object value) {
        return (value instanceof ArrayInstance) && ((ArrayInstance) value).getArrayType() == Type.BYTE;
    }

    static List<ClassInstance.FieldValue> classInstanceValues(Instance instance) {
        ClassInstance classInstance = (ClassInstance) instance;
        return classInstance.getValues();
    }

    static <T> T fieldValue(List<ClassInstance.FieldValue> list, String str) {
        for (ClassInstance.FieldValue fieldValue : list) {
            if (fieldValue.getField().getName().equals(str)) {
                return (T) fieldValue.getValue();
            }
        }
        throw new IllegalArgumentException("Field " + str + " does not exists");
    }

    static boolean hasField(List<ClassInstance.FieldValue> values, String fieldName) {
        for (ClassInstance.FieldValue fieldValue : values) {
            if (fieldValue.getField().getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    private HahaHelper() {
        throw new AssertionError();
    }
}