package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public abstract class FieldDeserializer {
    protected BeanContext beanContext;
    protected final Class<?> clazz;
    public final FieldInfo fieldInfo;

    public abstract void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map);

    public FieldDeserializer(Class<?> clazz, FieldInfo fieldInfo) {
        this.clazz = clazz;
        this.fieldInfo = fieldInfo;
    }

    public Class<?> getOwnerClass() {
        return this.clazz;
    }

    public int getFastMatchToken() {
        return 0;
    }

    public void setValue(Object object, boolean value) {
        setValue(object, Boolean.valueOf(value));
    }

    public void setValue(Object object, int value) {
        setValue(object, Integer.valueOf(value));
    }

    public void setValue(Object object, long value) {
        setValue(object, Long.valueOf(value));
    }

    public void setValue(Object object, String value) {
        setValue(object, (Object) value);
    }

    public void setValue(Object object, Object value) {
        Method method;
        if (value == null && this.fieldInfo.fieldClass.isPrimitive()) {
            return;
        }
        if (this.fieldInfo.fieldClass == String.class && this.fieldInfo.format != null && this.fieldInfo.format.equals("trim")) {
            value = ((String) value).trim();
        }
        try {
            method = this.fieldInfo.method;
        } catch (Exception e) {
            throw new JSONException("set property error, " + this.clazz.getName() + "#" + this.fieldInfo.name, e);
        }
        if (method != null) {
            if (this.fieldInfo.getOnly) {
                if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                    AtomicInteger atomic = (AtomicInteger) method.invoke(object, new Object[0]);
                    if (atomic != null) {
                        atomic.set(((AtomicInteger) value).get());
                    } else {
                        degradeValueAssignment(this.fieldInfo.field, method, object, value);
                    }
                    return;
                }
                if (this.fieldInfo.fieldClass == AtomicLong.class) {
                    AtomicLong atomic2 = (AtomicLong) method.invoke(object, new Object[0]);
                    if (atomic2 != null) {
                        atomic2.set(((AtomicLong) value).get());
                    } else {
                        degradeValueAssignment(this.fieldInfo.field, method, object, value);
                    }
                    return;
                }
                if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                    AtomicBoolean atomic3 = (AtomicBoolean) method.invoke(object, new Object[0]);
                    if (atomic3 != null) {
                        atomic3.set(((AtomicBoolean) value).get());
                    } else {
                        degradeValueAssignment(this.fieldInfo.field, method, object, value);
                    }
                    return;
                }
                if (Map.class.isAssignableFrom(method.getReturnType())) {
                    try {
                        Map map = (Map) method.invoke(object, new Object[0]);
                        if (map != null) {
                            if (map == Collections.emptyMap()) {
                                return;
                            }
                            if (map.isEmpty() && ((Map) value).isEmpty()) {
                                return;
                            }
                            String mapClassName = map.getClass().getName();
                            if (!mapClassName.equals("java.util.ImmutableCollections$Map1") && !mapClassName.equals("java.util.ImmutableCollections$MapN") && !mapClassName.startsWith("java.util.Collections$Unmodifiable")) {
                                if (map.getClass().getName().equals("kotlin.collections.EmptyMap")) {
                                    degradeValueAssignment(this.fieldInfo.field, method, object, value);
                                    return;
                                }
                                map.putAll((Map) value);
                            }
                            return;
                        }
                        if (value != null) {
                            degradeValueAssignment(this.fieldInfo.field, method, object, value);
                        }
                        return;
                    } catch (InvocationTargetException e2) {
                        degradeValueAssignment(this.fieldInfo.field, method, object, value);
                        return;
                    }
                }
                try {
                    Collection collection = (Collection) method.invoke(object, new Object[0]);
                    if (collection != null && value != null) {
                        String collectionClassName = collection.getClass().getName();
                        if (collection != Collections.emptySet() && collection != Collections.emptyList() && collectionClassName != "java.util.ImmutableCollections$ListN" && collectionClassName != "java.util.ImmutableCollections$List12" && !collectionClassName.startsWith("java.util.Collections$Unmodifiable")) {
                            if (!collection.isEmpty()) {
                                collection.clear();
                            } else if (((Collection) value).isEmpty()) {
                                return;
                            }
                            if (!collectionClassName.equals("kotlin.collections.EmptyList") && !collectionClassName.equals("kotlin.collections.EmptySet")) {
                                collection.addAll((Collection) value);
                            }
                            degradeValueAssignment(this.fieldInfo.field, method, object, value);
                            return;
                        }
                        return;
                    }
                    if (collection == null && value != null) {
                        degradeValueAssignment(this.fieldInfo.field, method, object, value);
                    }
                    return;
                } catch (InvocationTargetException e3) {
                    degradeValueAssignment(this.fieldInfo.field, method, object, value);
                    return;
                }
                throw new JSONException("set property error, " + this.clazz.getName() + "#" + this.fieldInfo.name, e);
            }
            method.invoke(object, value);
            return;
        }
        Field field = this.fieldInfo.field;
        if (this.fieldInfo.getOnly) {
            if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                AtomicInteger atomic4 = (AtomicInteger) field.get(object);
                if (atomic4 != null) {
                    atomic4.set(((AtomicInteger) value).get());
                }
                return;
            }
            if (this.fieldInfo.fieldClass == AtomicLong.class) {
                AtomicLong atomic5 = (AtomicLong) field.get(object);
                if (atomic5 != null) {
                    atomic5.set(((AtomicLong) value).get());
                }
                return;
            }
            if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                AtomicBoolean atomic6 = (AtomicBoolean) field.get(object);
                if (atomic6 != null) {
                    atomic6.set(((AtomicBoolean) value).get());
                }
                return;
            }
            if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                Map map2 = (Map) field.get(object);
                if (map2 != null) {
                    if (map2 != Collections.emptyMap() && !map2.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                        map2.putAll((Map) value);
                    }
                    return;
                }
                return;
            }
            Collection collection2 = (Collection) field.get(object);
            if (collection2 != null && value != null) {
                if (collection2 != Collections.emptySet() && collection2 != Collections.emptyList() && !collection2.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                    collection2.clear();
                    collection2.addAll((Collection) value);
                }
                return;
            }
            return;
        }
        if (field != null) {
            field.set(object, value);
        }
    }

    private static boolean degradeValueAssignment(Field field, Method getMethod, Object object, Object value) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (setFieldValue(field, object, value)) {
            return true;
        }
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + getMethod.getName().substring(3), getMethod.getReturnType());
            setMethod.invoke(object, value);
            return true;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return false;
        }
    }

    private static boolean setFieldValue(Field field, Object object, Object value) throws IllegalAccessException, IllegalArgumentException {
        if (field != null && !Modifier.isFinal(field.getModifiers())) {
            field.set(object, value);
            return true;
        }
        return false;
    }

    public void setWrappedValue(String key, Object value) {
        throw new JSONException("TODO");
    }
}