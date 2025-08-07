package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class FieldSerializer implements Comparable<FieldSerializer> {
    protected boolean browserCompatible;
    protected boolean disableCircularReferenceDetect;
    private final String double_quoted_fieldPrefix;
    protected int features;
    protected BeanContext fieldContext;
    public final FieldInfo fieldInfo;
    private String format;
    protected boolean persistenceXToMany;
    private RuntimeSerializerInfo runtimeInfo;
    protected boolean serializeUsing = false;
    private String single_quoted_fieldPrefix;
    private String un_quoted_fieldPrefix;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected final boolean writeNull;

    public FieldSerializer(Class<?> beanType, FieldInfo fieldInfo) throws SecurityException {
        JSONType jsonType;
        this.writeEnumUsingToString = false;
        this.writeEnumUsingName = false;
        this.disableCircularReferenceDetect = false;
        this.persistenceXToMany = false;
        this.fieldInfo = fieldInfo;
        this.fieldContext = new BeanContext(beanType, fieldInfo);
        if (beanType != null && (jsonType = (JSONType) TypeUtils.getAnnotation(beanType, JSONType.class)) != null) {
            for (SerializerFeature feature : jsonType.serialzeFeatures()) {
                if (feature == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                } else if (feature == SerializerFeature.WriteEnumUsingName) {
                    this.writeEnumUsingName = true;
                } else if (feature == SerializerFeature.DisableCircularReferenceDetect) {
                    this.disableCircularReferenceDetect = true;
                } else if (feature == SerializerFeature.BrowserCompatible) {
                    this.features |= SerializerFeature.BrowserCompatible.mask;
                    this.browserCompatible = true;
                } else if (feature == SerializerFeature.WriteMapNullValue) {
                    this.features |= SerializerFeature.WriteMapNullValue.mask;
                }
            }
        }
        fieldInfo.setAccessible();
        this.double_quoted_fieldPrefix = Typography.quote + fieldInfo.name + "\":";
        boolean writeNull = false;
        JSONField annotation = fieldInfo.getAnnotation();
        if (annotation != null) {
            SerializerFeature[] serializerFeatureArrSerialzeFeatures = annotation.serialzeFeatures();
            int length = serializerFeatureArrSerialzeFeatures.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if ((serializerFeatureArrSerialzeFeatures[i].getMask() & SerializerFeature.WRITE_MAP_NULL_FEATURES) == 0) {
                    i++;
                } else {
                    writeNull = true;
                    break;
                }
            }
            this.format = annotation.format();
            if (this.format.trim().length() == 0) {
                this.format = null;
            }
            for (SerializerFeature feature2 : annotation.serialzeFeatures()) {
                if (feature2 == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                } else if (feature2 == SerializerFeature.WriteEnumUsingName) {
                    this.writeEnumUsingName = true;
                } else if (feature2 == SerializerFeature.DisableCircularReferenceDetect) {
                    this.disableCircularReferenceDetect = true;
                } else if (feature2 == SerializerFeature.BrowserCompatible) {
                    this.browserCompatible = true;
                }
            }
            this.features |= SerializerFeature.of(annotation.serialzeFeatures());
        }
        this.writeNull = writeNull;
        this.persistenceXToMany = TypeUtils.isAnnotationPresentOneToMany(fieldInfo.method) || TypeUtils.isAnnotationPresentManyToMany(fieldInfo.method);
    }

    public void writePrefix(JSONSerializer serializer) throws IOException {
        SerializeWriter out = serializer.out;
        if (out.quoteFieldNames) {
            boolean useSingleQuotes = SerializerFeature.isEnabled(out.features, this.fieldInfo.serialzeFeatures, SerializerFeature.UseSingleQuotes);
            if (useSingleQuotes) {
                if (this.single_quoted_fieldPrefix == null) {
                    this.single_quoted_fieldPrefix = '\'' + this.fieldInfo.name + "':";
                }
                out.write(this.single_quoted_fieldPrefix);
                return;
            }
            out.write(this.double_quoted_fieldPrefix);
            return;
        }
        if (this.un_quoted_fieldPrefix == null) {
            this.un_quoted_fieldPrefix = this.fieldInfo.name + ":";
        }
        out.write(this.un_quoted_fieldPrefix);
    }

    public Object getPropertyValueDirect(Object object) throws IllegalAccessException, InvocationTargetException {
        Object fieldValue = this.fieldInfo.get(object);
        if (this.persistenceXToMany && !TypeUtils.isHibernateInitialized(fieldValue)) {
            return null;
        }
        return fieldValue;
    }

    public Object getPropertyValue(Object object) throws IllegalAccessException, InvocationTargetException {
        Object propertyValue = this.fieldInfo.get(object);
        if (this.format != null && propertyValue != null && (this.fieldInfo.fieldClass == Date.class || this.fieldInfo.fieldClass == java.sql.Date.class)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(this.format, JSON.defaultLocale);
            dateFormat.setTimeZone(JSON.defaultTimeZone);
            return dateFormat.format(propertyValue);
        }
        return propertyValue;
    }

    @Override // java.lang.Comparable
    public int compareTo(FieldSerializer o) {
        return this.fieldInfo.compareTo(o.fieldInfo);
    }

    public void writeValue(JSONSerializer serializer, Object propertyValue) throws Exception {
        ObjectSerializer valueSerializer;
        Class<?> runtimeFieldClass;
        if (this.runtimeInfo == null) {
            if (propertyValue == null) {
                runtimeFieldClass = this.fieldInfo.fieldClass;
                if (runtimeFieldClass == Byte.TYPE) {
                    runtimeFieldClass = Byte.class;
                } else if (runtimeFieldClass == Short.TYPE) {
                    runtimeFieldClass = Short.class;
                } else if (runtimeFieldClass == Integer.TYPE) {
                    runtimeFieldClass = Integer.class;
                } else if (runtimeFieldClass == Long.TYPE) {
                    runtimeFieldClass = Long.class;
                } else if (runtimeFieldClass == Float.TYPE) {
                    runtimeFieldClass = Float.class;
                } else if (runtimeFieldClass == Double.TYPE) {
                    runtimeFieldClass = Double.class;
                } else if (runtimeFieldClass == Boolean.TYPE) {
                    runtimeFieldClass = Boolean.class;
                }
            } else {
                runtimeFieldClass = propertyValue.getClass();
            }
            ObjectSerializer fieldSerializer = null;
            JSONField fieldAnnotation = this.fieldInfo.getAnnotation();
            if (fieldAnnotation != null && fieldAnnotation.serializeUsing() != Void.class) {
                fieldSerializer = (ObjectSerializer) fieldAnnotation.serializeUsing().newInstance();
                this.serializeUsing = true;
            } else {
                if (this.format != null) {
                    if (runtimeFieldClass == Double.TYPE || runtimeFieldClass == Double.class) {
                        fieldSerializer = new DoubleSerializer(this.format);
                    } else if (runtimeFieldClass == Float.TYPE || runtimeFieldClass == Float.class) {
                        fieldSerializer = new FloatCodec(this.format);
                    }
                }
                if (fieldSerializer == null) {
                    fieldSerializer = serializer.getObjectWriter(runtimeFieldClass);
                }
            }
            this.runtimeInfo = new RuntimeSerializerInfo(fieldSerializer, runtimeFieldClass);
        }
        RuntimeSerializerInfo runtimeInfo = this.runtimeInfo;
        int fieldFeatures = (this.disableCircularReferenceDetect ? this.fieldInfo.serialzeFeatures | SerializerFeature.DisableCircularReferenceDetect.mask : this.fieldInfo.serialzeFeatures) | this.features;
        if (propertyValue == null) {
            SerializeWriter out = serializer.out;
            if (this.fieldInfo.fieldClass == Object.class && out.isEnabled(SerializerFeature.WRITE_MAP_NULL_FEATURES)) {
                out.writeNull();
                return;
            }
            Class<?> runtimeFieldClass2 = runtimeInfo.runtimeFieldClass;
            if (Number.class.isAssignableFrom(runtimeFieldClass2)) {
                out.writeNull(this.features, SerializerFeature.WriteNullNumberAsZero.mask);
                return;
            }
            if (String.class == runtimeFieldClass2) {
                out.writeNull(this.features, SerializerFeature.WriteNullStringAsEmpty.mask);
                return;
            }
            if (Boolean.class == runtimeFieldClass2) {
                out.writeNull(this.features, SerializerFeature.WriteNullBooleanAsFalse.mask);
                return;
            }
            if (Collection.class.isAssignableFrom(runtimeFieldClass2) || runtimeFieldClass2.isArray()) {
                out.writeNull(this.features, SerializerFeature.WriteNullListAsEmpty.mask);
                return;
            }
            ObjectSerializer fieldSerializer2 = runtimeInfo.fieldSerializer;
            if (out.isEnabled(SerializerFeature.WRITE_MAP_NULL_FEATURES) && (fieldSerializer2 instanceof JavaBeanSerializer)) {
                out.writeNull();
                return;
            } else {
                fieldSerializer2.write(serializer, null, this.fieldInfo.name, this.fieldInfo.fieldType, fieldFeatures);
                return;
            }
        }
        if (this.fieldInfo.isEnum) {
            if (this.writeEnumUsingName) {
                serializer.out.writeString(((Enum) propertyValue).name());
                return;
            } else if (this.writeEnumUsingToString) {
                serializer.out.writeString(((Enum) propertyValue).toString());
                return;
            }
        }
        Class<?> valueClass = propertyValue.getClass();
        if (valueClass == runtimeInfo.runtimeFieldClass || this.serializeUsing) {
            ObjectSerializer valueSerializer2 = runtimeInfo.fieldSerializer;
            valueSerializer = valueSerializer2;
        } else {
            valueSerializer = serializer.getObjectWriter(valueClass);
        }
        if (this.format != null && !(valueSerializer instanceof DoubleSerializer) && !(valueSerializer instanceof FloatCodec)) {
            if (valueSerializer instanceof ContextObjectSerializer) {
                ((ContextObjectSerializer) valueSerializer).write(serializer, propertyValue, this.fieldContext);
                return;
            } else {
                serializer.writeWithFormat(propertyValue, this.format);
                return;
            }
        }
        if (this.fieldInfo.unwrapped) {
            if (valueSerializer instanceof JavaBeanSerializer) {
                JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) valueSerializer;
                javaBeanSerializer.write(serializer, propertyValue, this.fieldInfo.name, this.fieldInfo.fieldType, fieldFeatures, true);
                return;
            } else if (valueSerializer instanceof MapSerializer) {
                MapSerializer mapSerializer = (MapSerializer) valueSerializer;
                mapSerializer.write(serializer, propertyValue, this.fieldInfo.name, this.fieldInfo.fieldType, fieldFeatures, true);
                return;
            }
        }
        if ((this.features & SerializerFeature.WriteClassName.mask) != 0 && valueClass != this.fieldInfo.fieldClass && (valueSerializer instanceof JavaBeanSerializer)) {
            ((JavaBeanSerializer) valueSerializer).write(serializer, propertyValue, this.fieldInfo.name, this.fieldInfo.fieldType, fieldFeatures, false);
            return;
        }
        if (this.browserCompatible && (this.fieldInfo.fieldClass == Long.TYPE || this.fieldInfo.fieldClass == Long.class)) {
            long value = ((Long) propertyValue).longValue();
            if (value > 9007199254740991L || value < -9007199254740991L) {
                serializer.getWriter().writeString(Long.toString(value));
                return;
            }
        }
        valueSerializer.write(serializer, propertyValue, this.fieldInfo.name, this.fieldInfo.fieldType, fieldFeatures);
    }

    static class RuntimeSerializerInfo {
        final ObjectSerializer fieldSerializer;
        final Class<?> runtimeFieldClass;

        public RuntimeSerializerInfo(ObjectSerializer fieldSerializer, Class<?> runtimeFieldClass) {
            this.fieldSerializer = fieldSerializer;
            this.runtimeFieldClass = runtimeFieldClass;
        }
    }
}