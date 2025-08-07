package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.spi.Module;
import com.alibaba.fastjson.support.moneta.MonetaCodec;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.ServiceLoader;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import javax.xml.datatype.XMLGregorianCalendar;
import org.w3c.dom.Node;

/* loaded from: classes.dex */
public class SerializeConfig {
    private boolean asm;
    private ASMSerializerFactory asmFactory;
    private long[] denyClasses;
    private final boolean fieldBased;
    private final IdentityHashMap<Type, IdentityHashMap<Type, ObjectSerializer>> mixInSerializers;
    private List<Module> modules;
    public PropertyNamingStrategy propertyNamingStrategy;
    private final IdentityHashMap<Type, ObjectSerializer> serializers;
    protected String typeKey;
    public static final SerializeConfig globalInstance = new SerializeConfig();
    private static boolean awtError = false;
    private static boolean jdk8Error = false;
    private static boolean oracleJdbcError = false;
    private static boolean springfoxError = false;
    private static boolean guavaError = false;
    private static boolean jodaError = false;

    public String getTypeKey() {
        return this.typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    private final JavaBeanSerializer createASMSerializer(SerializeBeanInfo beanInfo) throws Exception {
        JavaBeanSerializer serializer = this.asmFactory.createJavaBeanSerializer(beanInfo);
        for (int i = 0; i < serializer.sortedGetters.length; i++) {
            FieldSerializer fieldDeser = serializer.sortedGetters[i];
            Class<?> fieldClass = fieldDeser.fieldInfo.fieldClass;
            if (fieldClass.isEnum()) {
                ObjectSerializer fieldSer = getObjectWriter(fieldClass);
                if (!(fieldSer instanceof EnumSerializer)) {
                    serializer.writeDirect = false;
                }
            }
        }
        return serializer;
    }

    public final ObjectSerializer createJavaBeanSerializer(Class<?> clazz) throws SecurityException {
        String className = clazz.getName();
        long hashCode64 = TypeUtils.fnv1a_64(className);
        if (Arrays.binarySearch(this.denyClasses, hashCode64) >= 0) {
            throw new JSONException("not support class : " + className);
        }
        SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, null, this.propertyNamingStrategy, this.fieldBased);
        if (beanInfo.fields.length == 0 && Iterable.class.isAssignableFrom(clazz)) {
            return MiscCodec.instance;
        }
        return createJavaBeanSerializer(beanInfo);
    }

    /* JADX WARN: Code restructure failed: missing block: B:124:0x017d, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x017f, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0106, code lost:
    
        r5 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ObjectSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) {
        FieldInfo[] fieldInfoArr;
        SerializeConfig serializeConfig = this;
        JSONType jsonType = beanInfo.jsonType;
        boolean asm = serializeConfig.asm && !serializeConfig.fieldBased;
        if (jsonType != null) {
            Class<?> serializerClass = jsonType.serializer();
            if (serializerClass != Void.class) {
                try {
                    Object seralizer = serializerClass.newInstance();
                    if (seralizer instanceof ObjectSerializer) {
                        return (ObjectSerializer) seralizer;
                    }
                } catch (Throwable th) {
                }
            }
            if (!jsonType.asm()) {
                asm = false;
            }
            if (asm) {
                for (SerializerFeature feature : jsonType.serialzeFeatures()) {
                    if (SerializerFeature.WriteNonStringValueAsString == feature || SerializerFeature.WriteEnumUsingToString == feature || SerializerFeature.NotWriteDefaultValue == feature || SerializerFeature.BrowserCompatible == feature) {
                        asm = false;
                        break;
                    }
                }
            }
            if (asm) {
                Class<? extends SerializeFilter>[] filterClasses = jsonType.serialzeFilters();
                if (filterClasses.length != 0) {
                    asm = false;
                }
            }
        }
        Class<?> serializerClass2 = beanInfo.beanType;
        if (!Modifier.isPublic(beanInfo.beanType.getModifiers())) {
            return new JavaBeanSerializer(beanInfo);
        }
        if ((asm && serializeConfig.asmFactory.classLoader.isExternalClass(serializerClass2)) || serializerClass2 == Serializable.class || serializerClass2 == Object.class) {
            asm = false;
        }
        if (asm && !ASMUtils.checkName(serializerClass2.getSimpleName())) {
            asm = false;
        }
        if (asm && beanInfo.beanType.isInterface()) {
            asm = false;
        }
        if (asm) {
            FieldInfo[] fieldInfoArr2 = beanInfo.fields;
            int length = fieldInfoArr2.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                FieldInfo fieldInfo = fieldInfoArr2[i];
                Field field = fieldInfo.field;
                if (field != null && !field.getType().equals(fieldInfo.fieldClass)) {
                    asm = false;
                    break;
                }
                Method method = fieldInfo.method;
                if (method != null && !method.getReturnType().equals(fieldInfo.fieldClass)) {
                    asm = false;
                    break;
                }
                if (fieldInfo.fieldClass.isEnum() && serializeConfig.get(fieldInfo.fieldClass) != EnumSerializer.instance) {
                    asm = false;
                    break;
                }
                JSONField annotation = fieldInfo.getAnnotation();
                if (annotation == null) {
                    fieldInfoArr = fieldInfoArr2;
                } else {
                    String format = annotation.format();
                    if (format.length() != 0 && (fieldInfo.fieldClass != String.class || !"trim".equals(format))) {
                        break;
                    }
                    if (!ASMUtils.checkName(annotation.name()) || annotation.jsonDirect() || annotation.serializeUsing() != Void.class || annotation.unwrapped()) {
                        break;
                    }
                    SerializerFeature[] serializerFeatureArrSerialzeFeatures = annotation.serialzeFeatures();
                    int length2 = serializerFeatureArrSerialzeFeatures.length;
                    int i2 = 0;
                    while (i2 < length2) {
                        fieldInfoArr = fieldInfoArr2;
                        SerializerFeature feature2 = serializerFeatureArrSerialzeFeatures[i2];
                        if (SerializerFeature.WriteNonStringValueAsString != feature2 && SerializerFeature.WriteEnumUsingToString != feature2 && SerializerFeature.NotWriteDefaultValue != feature2 && SerializerFeature.BrowserCompatible != feature2 && SerializerFeature.WriteClassName != feature2) {
                            i2++;
                            fieldInfoArr2 = fieldInfoArr;
                        } else {
                            asm = false;
                            break;
                        }
                    }
                    fieldInfoArr = fieldInfoArr2;
                    if (!TypeUtils.isAnnotationPresentOneToMany(method) && !TypeUtils.isAnnotationPresentManyToMany(method)) {
                        if (annotation.defaultValue() != null && !"".equals(annotation.defaultValue())) {
                            asm = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                i++;
                serializeConfig = this;
                fieldInfoArr2 = fieldInfoArr;
            }
        }
        if (asm) {
            try {
                ObjectSerializer asmSerializer = createASMSerializer(beanInfo);
                if (asmSerializer != null) {
                    return asmSerializer;
                }
            } catch (ClassCastException e) {
            } catch (ClassFormatError e2) {
            } catch (ClassNotFoundException e3) {
            } catch (OutOfMemoryError e4) {
                if (e4.getMessage().indexOf("Metaspace") != -1) {
                    throw e4;
                }
            } catch (Throwable e5) {
                throw new JSONException("create asm serializer error, verson 1.2.83, class " + serializerClass2, e5);
            }
        }
        return new JavaBeanSerializer(beanInfo);
    }

    public boolean isAsmEnable() {
        return this.asm;
    }

    public void setAsmEnable(boolean asmEnable) {
        if (ASMUtils.IS_ANDROID) {
            return;
        }
        this.asm = asmEnable;
    }

    public static SerializeConfig getGlobalInstance() {
        return globalInstance;
    }

    public SerializeConfig() {
        this(8192);
    }

    public SerializeConfig(boolean fieldBase) {
        this(8192, fieldBase);
    }

    public SerializeConfig(int tableSize) {
        this(tableSize, false);
    }

    public SerializeConfig(int tableSize, boolean fieldBase) {
        this.asm = !ASMUtils.IS_ANDROID;
        this.typeKey = JSON.DEFAULT_TYPE_KEY;
        this.denyClasses = new long[]{4165360493669296979L, 4446674157046724083L};
        this.modules = new ArrayList();
        this.fieldBased = fieldBase;
        this.serializers = new IdentityHashMap<>(tableSize);
        this.mixInSerializers = new IdentityHashMap<>(16);
        try {
            if (this.asm) {
                this.asmFactory = new ASMSerializerFactory();
            }
        } catch (Throwable th) {
            this.asm = false;
        }
        initSerializers();
    }

    private void initSerializers() {
        put(Boolean.class, (ObjectSerializer) BooleanCodec.instance);
        put(Character.class, (ObjectSerializer) CharacterCodec.instance);
        put(Byte.class, (ObjectSerializer) IntegerCodec.instance);
        put(Short.class, (ObjectSerializer) IntegerCodec.instance);
        put(Integer.class, (ObjectSerializer) IntegerCodec.instance);
        put(Long.class, (ObjectSerializer) LongCodec.instance);
        put(Float.class, (ObjectSerializer) FloatCodec.instance);
        put(Double.class, (ObjectSerializer) DoubleSerializer.instance);
        put(BigDecimal.class, (ObjectSerializer) BigDecimalCodec.instance);
        put(BigInteger.class, (ObjectSerializer) BigIntegerCodec.instance);
        put(String.class, (ObjectSerializer) StringCodec.instance);
        put(byte[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put(short[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put(int[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put(long[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put(float[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put(double[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put(boolean[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put(char[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put(Object[].class, (ObjectSerializer) ObjectArrayCodec.instance);
        put(Class.class, (ObjectSerializer) MiscCodec.instance);
        put(SimpleDateFormat.class, (ObjectSerializer) MiscCodec.instance);
        put(Currency.class, (ObjectSerializer) new MiscCodec());
        put(TimeZone.class, (ObjectSerializer) MiscCodec.instance);
        put(InetAddress.class, (ObjectSerializer) MiscCodec.instance);
        put(Inet4Address.class, (ObjectSerializer) MiscCodec.instance);
        put(Inet6Address.class, (ObjectSerializer) MiscCodec.instance);
        put(InetSocketAddress.class, (ObjectSerializer) MiscCodec.instance);
        put(File.class, (ObjectSerializer) MiscCodec.instance);
        put(Appendable.class, (ObjectSerializer) AppendableSerializer.instance);
        put(StringBuffer.class, (ObjectSerializer) AppendableSerializer.instance);
        put(StringBuilder.class, (ObjectSerializer) AppendableSerializer.instance);
        put(Charset.class, (ObjectSerializer) ToStringSerializer.instance);
        put(Pattern.class, (ObjectSerializer) ToStringSerializer.instance);
        put(Locale.class, (ObjectSerializer) ToStringSerializer.instance);
        put(URI.class, (ObjectSerializer) ToStringSerializer.instance);
        put(URL.class, (ObjectSerializer) ToStringSerializer.instance);
        put(UUID.class, (ObjectSerializer) ToStringSerializer.instance);
        put(AtomicBoolean.class, (ObjectSerializer) AtomicCodec.instance);
        put(AtomicInteger.class, (ObjectSerializer) AtomicCodec.instance);
        put(AtomicLong.class, (ObjectSerializer) AtomicCodec.instance);
        put(AtomicReference.class, (ObjectSerializer) ReferenceCodec.instance);
        put(AtomicIntegerArray.class, (ObjectSerializer) AtomicCodec.instance);
        put(AtomicLongArray.class, (ObjectSerializer) AtomicCodec.instance);
        put(WeakReference.class, (ObjectSerializer) ReferenceCodec.instance);
        put(SoftReference.class, (ObjectSerializer) ReferenceCodec.instance);
        put(LinkedList.class, (ObjectSerializer) CollectionCodec.instance);
    }

    public void addFilter(Class<?> clazz, SerializeFilter filter) {
        Object objectWriter = getObjectWriter(clazz);
        if (objectWriter instanceof SerializeFilterable) {
            SerializeFilterable filterable = (SerializeFilterable) objectWriter;
            if (this != globalInstance && filterable == MapSerializer.instance) {
                MapSerializer newMapSer = new MapSerializer();
                put((Type) clazz, (ObjectSerializer) newMapSer);
                newMapSer.addFilter(filter);
                return;
            }
            filterable.addFilter(filter);
        }
    }

    public void config(Class<?> clazz, SerializerFeature feature, boolean value) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
        ObjectSerializer serializer = getObjectWriter(clazz, false);
        if (serializer == null) {
            SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, null, this.propertyNamingStrategy);
            if (value) {
                beanInfo.features |= feature.mask;
            } else {
                beanInfo.features &= feature.mask ^ (-1);
            }
            put((Type) clazz, createJavaBeanSerializer(beanInfo));
            return;
        }
        if (serializer instanceof JavaBeanSerializer) {
            JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) serializer;
            SerializeBeanInfo beanInfo2 = javaBeanSerializer.beanInfo;
            int originalFeaturs = beanInfo2.features;
            if (value) {
                beanInfo2.features |= feature.mask;
            } else {
                beanInfo2.features &= feature.mask ^ (-1);
            }
            if (originalFeaturs == beanInfo2.features) {
                return;
            }
            Class<?> serializerClass = serializer.getClass();
            if (serializerClass != JavaBeanSerializer.class) {
                ObjectSerializer newSerializer = createJavaBeanSerializer(beanInfo2);
                put((Type) clazz, newSerializer);
            }
        }
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        return getObjectWriter(clazz, true);
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz, boolean create) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
        Class<?> handlerClass;
        JSONType jsonType;
        ClassLoader classLoader;
        ObjectSerializer writer = get(clazz);
        if (writer != null) {
            return writer;
        }
        try {
            for (Object o : ServiceLoader.load(AutowiredObjectSerializer.class, Thread.currentThread().getContextClassLoader())) {
                if (o instanceof AutowiredObjectSerializer) {
                    AutowiredObjectSerializer autowired = (AutowiredObjectSerializer) o;
                    for (Type forType : autowired.getAutowiredFor()) {
                        put(forType, (ObjectSerializer) autowired);
                    }
                }
            }
        } catch (ClassCastException e) {
        }
        ObjectSerializer writer2 = get(clazz);
        if (writer2 == null && (classLoader = JSON.class.getClassLoader()) != Thread.currentThread().getContextClassLoader()) {
            try {
                for (Object o2 : ServiceLoader.load(AutowiredObjectSerializer.class, classLoader)) {
                    if (o2 instanceof AutowiredObjectSerializer) {
                        AutowiredObjectSerializer autowired2 = (AutowiredObjectSerializer) o2;
                        for (Type forType2 : autowired2.getAutowiredFor()) {
                            put(forType2, (ObjectSerializer) autowired2);
                        }
                    }
                }
            } catch (ClassCastException e2) {
            }
            writer2 = get(clazz);
        }
        for (Module module : this.modules) {
            writer2 = module.createSerializer(this, clazz);
            if (writer2 != null) {
                put((Type) clazz, writer2);
                return writer2;
            }
        }
        if (writer2 == null) {
            String className = clazz.getName();
            if (Map.class.isAssignableFrom(clazz)) {
                ObjectSerializer objectSerializer = MapSerializer.instance;
                writer2 = objectSerializer;
                put((Type) clazz, objectSerializer);
            } else if (List.class.isAssignableFrom(clazz)) {
                ObjectSerializer objectSerializer2 = ListSerializer.instance;
                writer2 = objectSerializer2;
                put((Type) clazz, objectSerializer2);
            } else if (Collection.class.isAssignableFrom(clazz)) {
                ObjectSerializer objectSerializer3 = CollectionCodec.instance;
                writer2 = objectSerializer3;
                put((Type) clazz, objectSerializer3);
            } else if (Date.class.isAssignableFrom(clazz)) {
                ObjectSerializer objectSerializer4 = DateCodec.instance;
                writer2 = objectSerializer4;
                put((Type) clazz, objectSerializer4);
            } else if (JSONAware.class.isAssignableFrom(clazz)) {
                ObjectSerializer objectSerializer5 = JSONAwareSerializer.instance;
                writer2 = objectSerializer5;
                put((Type) clazz, objectSerializer5);
            } else if (JSONSerializable.class.isAssignableFrom(clazz)) {
                ObjectSerializer objectSerializer6 = JSONSerializableSerializer.instance;
                writer2 = objectSerializer6;
                put((Type) clazz, objectSerializer6);
            } else if (JSONStreamAware.class.isAssignableFrom(clazz)) {
                ObjectSerializer objectSerializer7 = MiscCodec.instance;
                writer2 = objectSerializer7;
                put((Type) clazz, objectSerializer7);
            } else if (clazz.isEnum()) {
                Class mixedInType = (Class) JSON.getMixInAnnotations(clazz);
                if (mixedInType != null) {
                    jsonType = (JSONType) TypeUtils.getAnnotation((Class<?>) mixedInType, JSONType.class);
                } else {
                    jsonType = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
                }
                if (jsonType != null && jsonType.serializeEnumAsJavaBean()) {
                    ObjectSerializer objectSerializerCreateJavaBeanSerializer = createJavaBeanSerializer(clazz);
                    writer2 = objectSerializerCreateJavaBeanSerializer;
                    put((Type) clazz, objectSerializerCreateJavaBeanSerializer);
                } else {
                    Member member = null;
                    if (mixedInType != null) {
                        Member mixedInMember = getEnumValueField(mixedInType);
                        if (mixedInMember != null) {
                            try {
                                if (mixedInMember instanceof Method) {
                                    Method mixedInMethod = (Method) mixedInMember;
                                    member = clazz.getMethod(mixedInMethod.getName(), mixedInMethod.getParameterTypes());
                                }
                            } catch (Exception e3) {
                            }
                        }
                    } else {
                        member = getEnumValueField(clazz);
                    }
                    if (member != null) {
                        ObjectSerializer enumSerializer = new EnumSerializer(member);
                        writer2 = enumSerializer;
                        put((Type) clazz, enumSerializer);
                    } else {
                        ObjectSerializer enumSerializer2 = getEnumSerializer();
                        writer2 = enumSerializer2;
                        put((Type) clazz, enumSerializer2);
                    }
                }
            } else {
                Class<?> superClass = clazz.getSuperclass();
                if (superClass != null && superClass.isEnum()) {
                    JSONType jsonType2 = (JSONType) TypeUtils.getAnnotation(superClass, JSONType.class);
                    if (jsonType2 != null && jsonType2.serializeEnumAsJavaBean()) {
                        ObjectSerializer objectSerializerCreateJavaBeanSerializer2 = createJavaBeanSerializer(clazz);
                        writer2 = objectSerializerCreateJavaBeanSerializer2;
                        put((Type) clazz, objectSerializerCreateJavaBeanSerializer2);
                    } else {
                        ObjectSerializer enumSerializer3 = getEnumSerializer();
                        writer2 = enumSerializer3;
                        put((Type) clazz, enumSerializer3);
                    }
                } else if (clazz.isArray()) {
                    Class<?> componentType = clazz.getComponentType();
                    ObjectSerializer compObjectSerializer = getObjectWriter(componentType);
                    ObjectSerializer arraySerializer = new ArraySerializer(componentType, compObjectSerializer);
                    writer2 = arraySerializer;
                    put((Type) clazz, arraySerializer);
                } else if (Throwable.class.isAssignableFrom(clazz)) {
                    SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, null, this.propertyNamingStrategy);
                    beanInfo.features |= SerializerFeature.WriteClassName.mask;
                    ObjectSerializer javaBeanSerializer = new JavaBeanSerializer(beanInfo);
                    writer2 = javaBeanSerializer;
                    put((Type) clazz, javaBeanSerializer);
                } else if (TimeZone.class.isAssignableFrom(clazz) || Map.Entry.class.isAssignableFrom(clazz)) {
                    ObjectSerializer objectSerializer8 = MiscCodec.instance;
                    writer2 = objectSerializer8;
                    put((Type) clazz, objectSerializer8);
                } else if (Appendable.class.isAssignableFrom(clazz)) {
                    ObjectSerializer objectSerializer9 = AppendableSerializer.instance;
                    writer2 = objectSerializer9;
                    put((Type) clazz, objectSerializer9);
                } else if (Charset.class.isAssignableFrom(clazz)) {
                    ObjectSerializer objectSerializer10 = ToStringSerializer.instance;
                    writer2 = objectSerializer10;
                    put((Type) clazz, objectSerializer10);
                } else if (Enumeration.class.isAssignableFrom(clazz)) {
                    ObjectSerializer objectSerializer11 = EnumerationSerializer.instance;
                    writer2 = objectSerializer11;
                    put((Type) clazz, objectSerializer11);
                } else if (Calendar.class.isAssignableFrom(clazz) || XMLGregorianCalendar.class.isAssignableFrom(clazz)) {
                    ObjectSerializer objectSerializer12 = CalendarCodec.instance;
                    writer2 = objectSerializer12;
                    put((Type) clazz, objectSerializer12);
                } else if (TypeUtils.isClob(clazz)) {
                    ObjectSerializer objectSerializer13 = ClobSerializer.instance;
                    writer2 = objectSerializer13;
                    put((Type) clazz, objectSerializer13);
                } else if (TypeUtils.isPath(clazz)) {
                    ObjectSerializer objectSerializer14 = ToStringSerializer.instance;
                    writer2 = objectSerializer14;
                    put((Type) clazz, objectSerializer14);
                } else if (Iterator.class.isAssignableFrom(clazz)) {
                    ObjectSerializer objectSerializer15 = MiscCodec.instance;
                    writer2 = objectSerializer15;
                    put((Type) clazz, objectSerializer15);
                } else if (Node.class.isAssignableFrom(clazz)) {
                    ObjectSerializer objectSerializer16 = MiscCodec.instance;
                    writer2 = objectSerializer16;
                    put((Type) clazz, objectSerializer16);
                } else {
                    int i = 0;
                    if (className.startsWith("java.awt.") && AwtCodec.support(clazz) && !awtError) {
                        try {
                            String[] names = {"java.awt.Color", "java.awt.Font", "java.awt.Point", "java.awt.Rectangle"};
                            for (String name : names) {
                                if (name.equals(className)) {
                                    Type cls = Class.forName(name);
                                    ObjectSerializer objectSerializer17 = AwtCodec.instance;
                                    writer2 = objectSerializer17;
                                    put(cls, objectSerializer17);
                                    return writer2;
                                }
                            }
                        } catch (Throwable th) {
                            awtError = true;
                        }
                    }
                    if (!jdk8Error && (className.startsWith("java.time.") || className.startsWith("java.util.Optional") || className.equals("java.util.concurrent.atomic.LongAdder") || className.equals("java.util.concurrent.atomic.DoubleAdder"))) {
                        try {
                            String[] names2 = {"java.time.LocalDateTime", "java.time.LocalDate", "java.time.LocalTime", "java.time.ZonedDateTime", "java.time.OffsetDateTime", "java.time.OffsetTime", "java.time.ZoneOffset", "java.time.ZoneRegion", "java.time.Period", "java.time.Duration", "java.time.Instant"};
                            for (String name2 : names2) {
                                if (name2.equals(className)) {
                                    Type cls2 = Class.forName(name2);
                                    ObjectSerializer writer3 = Jdk8DateCodec.instance;
                                    put(cls2, writer3);
                                    return writer3;
                                }
                            }
                            String[] names3 = {"java.util.Optional", "java.util.OptionalDouble", "java.util.OptionalInt", "java.util.OptionalLong"};
                            for (String name3 : names3) {
                                if (name3.equals(className)) {
                                    Type cls3 = Class.forName(name3);
                                    ObjectSerializer writer4 = OptionalCodec.instance;
                                    put(cls3, writer4);
                                    return writer4;
                                }
                            }
                            String[] names4 = {"java.util.concurrent.atomic.LongAdder", "java.util.concurrent.atomic.DoubleAdder"};
                            for (String name4 : names4) {
                                if (name4.equals(className)) {
                                    Type cls4 = Class.forName(name4);
                                    ObjectSerializer writer5 = AdderSerializer.instance;
                                    put(cls4, writer5);
                                    return writer5;
                                }
                            }
                        } catch (Throwable th2) {
                            jdk8Error = true;
                        }
                    }
                    if (!oracleJdbcError && className.startsWith("oracle.sql.")) {
                        try {
                            String[] names5 = {"oracle.sql.DATE", "oracle.sql.TIMESTAMP"};
                            for (String name5 : names5) {
                                if (name5.equals(className)) {
                                    Type cls5 = Class.forName(name5);
                                    ObjectSerializer objectSerializer18 = DateCodec.instance;
                                    writer2 = objectSerializer18;
                                    put(cls5, objectSerializer18);
                                    return writer2;
                                }
                            }
                        } catch (Throwable th3) {
                            oracleJdbcError = true;
                        }
                    }
                    if (!springfoxError && className.equals("springfox.documentation.spring.web.json.Json")) {
                        try {
                            Type cls6 = Class.forName("springfox.documentation.spring.web.json.Json");
                            ObjectSerializer objectSerializer19 = SwaggerJsonSerializer.instance;
                            writer2 = objectSerializer19;
                            put(cls6, objectSerializer19);
                            return writer2;
                        } catch (ClassNotFoundException e4) {
                            springfoxError = true;
                        }
                    }
                    if (!guavaError && className.startsWith("com.google.common.collect.")) {
                        try {
                            String[] names6 = {"com.google.common.collect.HashMultimap", "com.google.common.collect.LinkedListMultimap", "com.google.common.collect.LinkedHashMultimap", "com.google.common.collect.ArrayListMultimap", "com.google.common.collect.TreeMultimap"};
                            for (String name6 : names6) {
                                if (name6.equals(className)) {
                                    Type cls7 = Class.forName(name6);
                                    ObjectSerializer objectSerializer20 = GuavaCodec.instance;
                                    writer2 = objectSerializer20;
                                    put(cls7, objectSerializer20);
                                    return writer2;
                                }
                            }
                        } catch (ClassNotFoundException e5) {
                            guavaError = true;
                        }
                    }
                    if (className.equals("net.sf.json.JSONNull")) {
                        ObjectSerializer writer6 = MiscCodec.instance;
                        put((Type) clazz, writer6);
                        return writer6;
                    }
                    if (className.equals("org.json.JSONObject")) {
                        ObjectSerializer writer7 = JSONObjectCodec.instance;
                        put((Type) clazz, writer7);
                        return writer7;
                    }
                    if (!jodaError && className.startsWith("org.joda.")) {
                        try {
                            String[] names7 = {"org.joda.time.LocalDate", "org.joda.time.LocalDateTime", "org.joda.time.LocalTime", "org.joda.time.Instant", "org.joda.time.DateTime", "org.joda.time.Period", "org.joda.time.Duration", "org.joda.time.DateTimeZone", "org.joda.time.UTCDateTimeZone", "org.joda.time.tz.CachedDateTimeZone", "org.joda.time.tz.FixedDateTimeZone"};
                            for (String name7 : names7) {
                                if (name7.equals(className)) {
                                    Type cls8 = Class.forName(name7);
                                    ObjectSerializer objectSerializer21 = JodaCodec.instance;
                                    writer2 = objectSerializer21;
                                    put(cls8, objectSerializer21);
                                    return writer2;
                                }
                            }
                        } catch (ClassNotFoundException e6) {
                            jodaError = true;
                        }
                    }
                    if ("java.nio.HeapByteBuffer".equals(className)) {
                        ObjectSerializer writer8 = ByteBufferCodec.instance;
                        put((Type) clazz, writer8);
                        return writer8;
                    }
                    if ("org.javamoney.moneta.Money".equals(className)) {
                        ObjectSerializer writer9 = MonetaCodec.instance;
                        put((Type) clazz, writer9);
                        return writer9;
                    }
                    if ("com.google.protobuf.Descriptors$FieldDescriptor".equals(className)) {
                        ObjectSerializer writer10 = ToStringSerializer.instance;
                        put((Type) clazz, writer10);
                        return writer10;
                    }
                    Class<?>[] interfaces = clazz.getInterfaces();
                    if (interfaces.length == 1 && interfaces[0].isAnnotation()) {
                        put((Type) clazz, AnnotationSerializer.instance);
                        return AnnotationSerializer.instance;
                    }
                    if (TypeUtils.isProxy(clazz)) {
                        Class<?> superClazz = clazz.getSuperclass();
                        ObjectSerializer superWriter = getObjectWriter(superClazz);
                        put((Type) clazz, superWriter);
                        return superWriter;
                    }
                    if (Proxy.isProxyClass(clazz)) {
                        Class<?> handlerClass2 = null;
                        if (interfaces.length == 2) {
                            handlerClass = interfaces[1];
                        } else {
                            int length = interfaces.length;
                            while (true) {
                                if (i >= length) {
                                    handlerClass = handlerClass2;
                                    break;
                                }
                                Class<?> proxiedInterface = interfaces[i];
                                if (!proxiedInterface.getName().startsWith("org.springframework.aop.")) {
                                    if (handlerClass2 != null) {
                                        handlerClass = null;
                                        break;
                                    }
                                    handlerClass2 = proxiedInterface;
                                }
                                i++;
                            }
                        }
                        if (handlerClass != null) {
                            ObjectSerializer superWriter2 = getObjectWriter(handlerClass);
                            put((Type) clazz, superWriter2);
                            return superWriter2;
                        }
                    }
                    if (create) {
                        writer2 = createJavaBeanSerializer(clazz);
                        put((Type) clazz, writer2);
                    }
                }
            }
            if (writer2 == null) {
                return get(clazz);
            }
            return writer2;
        }
        return writer2;
    }

    private static Member getEnumValueField(Class clazz) throws SecurityException {
        Member member = null;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getReturnType() != Void.class) {
                JSONField jsonField = (JSONField) method.getAnnotation(JSONField.class);
                if (jsonField == null) {
                    continue;
                } else {
                    if (member != null) {
                        return null;
                    }
                    member = method;
                }
            }
        }
        for (Field field : clazz.getFields()) {
            JSONField jsonField2 = (JSONField) field.getAnnotation(JSONField.class);
            if (jsonField2 != null) {
                if (member != null) {
                    return null;
                }
                member = field;
            }
        }
        return member;
    }

    protected ObjectSerializer getEnumSerializer() {
        return EnumSerializer.instance;
    }

    public final ObjectSerializer get(Type type) {
        Type mixin = JSON.getMixInAnnotations(type);
        if (mixin == null) {
            return this.serializers.get(type);
        }
        IdentityHashMap<Type, ObjectSerializer> mixInClasses = this.mixInSerializers.get(type);
        if (mixInClasses == null) {
            return null;
        }
        return mixInClasses.get(mixin);
    }

    public boolean put(Object type, Object value) {
        return put((Type) type, (ObjectSerializer) value);
    }

    public boolean put(Type type, ObjectSerializer value) {
        Type mixin = JSON.getMixInAnnotations(type);
        if (mixin != null) {
            IdentityHashMap<Type, ObjectSerializer> mixInClasses = this.mixInSerializers.get(type);
            if (mixInClasses == null) {
                mixInClasses = new IdentityHashMap<>(4);
                this.mixInSerializers.put(type, mixInClasses);
            }
            return mixInClasses.put(mixin, value);
        }
        return this.serializers.put(type, value);
    }

    public void configEnumAsJavaBean(Class<? extends Enum>... enumClasses) {
        for (Class<? extends Enum> enumClass : enumClasses) {
            put((Type) enumClass, createJavaBeanSerializer(enumClass));
        }
    }

    public void setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        this.propertyNamingStrategy = propertyNamingStrategy;
    }

    public void clearSerializers() {
        this.serializers.clear();
        initSerializers();
    }

    public void register(Module module) {
        this.modules.add(module);
    }
}