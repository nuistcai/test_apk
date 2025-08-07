package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    private final ParserConfig.AutoTypeCheckHandler autoTypeCheckHandler;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private Map<String, FieldDeserializer> fieldDeserializerMap;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] hashArray;
    private transient short[] hashArrayMapping;
    private transient long[] smartMatchHashArray;
    private transient short[] smartMatchHashArrayMapping;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz) {
        this(config, clazz, clazz);
    }

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz, Type type) {
        this(config, JavaBeanInfo.build(clazz, type, config.propertyNamingStrategy, config.fieldBased, config.compatibleWithJavaBean, config.isJacksonCompatible()));
    }

    public JavaBeanDeserializer(ParserConfig config, JavaBeanInfo beanInfo) throws IllegalAccessException, InstantiationException {
        this.clazz = beanInfo.clazz;
        this.beanInfo = beanInfo;
        ParserConfig.AutoTypeCheckHandler autoTypeCheckHandler = null;
        if (beanInfo.jsonType != null && beanInfo.jsonType.autoTypeCheckHandler() != ParserConfig.AutoTypeCheckHandler.class) {
            try {
                autoTypeCheckHandler = beanInfo.jsonType.autoTypeCheckHandler().newInstance();
            } catch (Exception e) {
            }
        }
        this.autoTypeCheckHandler = autoTypeCheckHandler;
        Map<String, FieldDeserializer> alterNameFieldDeserializers = null;
        this.sortedFieldDeserializers = new FieldDeserializer[beanInfo.sortedFields.length];
        int size = beanInfo.sortedFields.length;
        for (int i = 0; i < size; i++) {
            FieldInfo fieldInfo = beanInfo.sortedFields[i];
            FieldDeserializer fieldDeserializer = config.createFieldDeserializer(config, beanInfo, fieldInfo);
            this.sortedFieldDeserializers[i] = fieldDeserializer;
            if (size > 128) {
                if (this.fieldDeserializerMap == null) {
                    this.fieldDeserializerMap = new HashMap();
                }
                this.fieldDeserializerMap.put(fieldInfo.name, fieldDeserializer);
            }
            for (String name : fieldInfo.alternateNames) {
                if (alterNameFieldDeserializers == null) {
                    alterNameFieldDeserializers = new HashMap<>();
                }
                alterNameFieldDeserializers.put(name, fieldDeserializer);
            }
        }
        this.alterNameFieldDeserializers = alterNameFieldDeserializers;
        this.fieldDeserializers = new FieldDeserializer[beanInfo.fields.length];
        int size2 = beanInfo.fields.length;
        for (int i2 = 0; i2 < size2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(beanInfo.fields[i2].name);
        }
    }

    public FieldDeserializer getFieldDeserializer(String key) {
        return getFieldDeserializer(key, null);
    }

    public FieldDeserializer getFieldDeserializer(String key, int[] setFlags) {
        FieldDeserializer fieldDeserializer;
        if (key == null) {
            return null;
        }
        if (this.fieldDeserializerMap != null && (fieldDeserializer = this.fieldDeserializerMap.get(key)) != null) {
            return fieldDeserializer;
        }
        int low = 0;
        int high = this.sortedFieldDeserializers.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            String fieldName = this.sortedFieldDeserializers[mid].fieldInfo.name;
            int cmp = fieldName.compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                if (isSetFlag(mid, setFlags)) {
                    return null;
                }
                return this.sortedFieldDeserializers[mid];
            }
        }
        if (this.alterNameFieldDeserializers == null) {
            return null;
        }
        return this.alterNameFieldDeserializers.get(key);
    }

    public FieldDeserializer getFieldDeserializer(long hash) {
        if (this.hashArray == null) {
            long[] hashArray = new long[this.sortedFieldDeserializers.length];
            for (int i = 0; i < this.sortedFieldDeserializers.length; i++) {
                hashArray[i] = TypeUtils.fnv1a_64(this.sortedFieldDeserializers[i].fieldInfo.name);
            }
            Arrays.sort(hashArray);
            this.hashArray = hashArray;
        }
        int pos = Arrays.binarySearch(this.hashArray, hash);
        if (pos < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            short[] mapping = new short[this.hashArray.length];
            Arrays.fill(mapping, (short) -1);
            for (int i2 = 0; i2 < this.sortedFieldDeserializers.length; i2++) {
                int p = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(this.sortedFieldDeserializers[i2].fieldInfo.name));
                if (p >= 0) {
                    mapping[p] = (short) i2;
                }
            }
            this.hashArrayMapping = mapping;
        }
        short s = this.hashArrayMapping[pos];
        if (s == -1) {
            return null;
        }
        return this.sortedFieldDeserializers[s];
    }

    static boolean isSetFlag(int i, int[] setFlags) {
        int flagIndex;
        return (setFlags == null || (flagIndex = i / 32) >= setFlags.length || (setFlags[flagIndex] & (1 << (i % 32))) == 0) ? false : true;
    }

    public Object createInstance(DefaultJSONParser parser, Type type) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        Object object;
        int i = 0;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            Class<?> clazz = (Class) type;
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            JSONObject obj = new JSONObject();
            Object proxy = Proxy.newProxyInstance(loader, new Class[]{clazz}, obj);
            return proxy;
        }
        if (this.beanInfo.defaultConstructor == null && this.beanInfo.factoryMethod == null) {
            return null;
        }
        if (this.beanInfo.factoryMethod != null && this.beanInfo.defaultConstructorParameterSize > 0) {
            return null;
        }
        try {
            Constructor<?> constructor = this.beanInfo.defaultConstructor;
            if (this.beanInfo.defaultConstructorParameterSize == 0) {
                if (constructor != null) {
                    object = constructor.newInstance(new Object[0]);
                } else {
                    object = this.beanInfo.factoryMethod.invoke(null, new Object[0]);
                }
            } else {
                ParseContext context = parser.getContext();
                if (context == null || context.object == null) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                if (type instanceof Class) {
                    String typeName = ((Class) type).getName();
                    int lastIndex = typeName.lastIndexOf(36);
                    String parentClassName = typeName.substring(0, lastIndex);
                    Object ctxObj = context.object;
                    String parentName = ctxObj.getClass().getName();
                    Object param = null;
                    if (!parentName.equals(parentClassName)) {
                        ParseContext parentContext = context.parent;
                        if (parentContext != null && parentContext.object != null && ("java.util.ArrayList".equals(parentName) || "java.util.List".equals(parentName) || "java.util.Collection".equals(parentName) || "java.util.Map".equals(parentName) || "java.util.HashMap".equals(parentName))) {
                            if (parentContext.object.getClass().getName().equals(parentClassName)) {
                                param = parentContext.object;
                            }
                        } else {
                            param = ctxObj;
                        }
                    } else {
                        param = ctxObj;
                    }
                    if (param == null || ((param instanceof Collection) && ((Collection) param).isEmpty())) {
                        throw new JSONException("can't create non-static inner class instance.");
                    }
                    i = 0;
                    object = constructor.newInstance(param);
                } else {
                    throw new JSONException("can't create non-static inner class instance.");
                }
            }
            if (parser != null && parser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                FieldInfo[] fieldInfoArr = this.beanInfo.fields;
                int length = fieldInfoArr.length;
                while (i < length) {
                    FieldInfo fieldInfo = fieldInfoArr[i];
                    if (fieldInfo.fieldClass == String.class) {
                        try {
                            fieldInfo.set(object, "");
                        } catch (Exception e) {
                            throw new JSONException("create instance error, class " + this.clazz.getName(), e);
                        }
                    }
                    i++;
                }
            }
            return object;
        } catch (JSONException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new JSONException("create instance error, class " + this.clazz.getName(), e3);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, 0);
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, int i) {
        return (T) deserialze(defaultJSONParser, type, obj, null, i, null);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        Enum<?> enumScanEnum;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 14) {
            throw new JSONException("error");
        }
        String strScanTypeName = jSONLexer.scanTypeName(defaultJSONParser.symbolTable);
        if (strScanTypeName != null) {
            ObjectDeserializer seeAlso = getSeeAlso(defaultJSONParser.getConfig(), this.beanInfo, strScanTypeName);
            if (seeAlso == null) {
                seeAlso = defaultJSONParser.getConfig().getDeserializer(defaultJSONParser.getConfig().checkAutoType(strScanTypeName, TypeUtils.getClass(type), jSONLexer.getFeatures()));
            }
            if (seeAlso instanceof JavaBeanDeserializer) {
                return (T) ((JavaBeanDeserializer) seeAlso).deserialzeArrayMapping(defaultJSONParser, type, obj, obj2);
            }
        }
        T t = (T) createInstance(defaultJSONParser, type);
        int i = 0;
        int length = this.sortedFieldDeserializers.length;
        while (true) {
            if (i >= length) {
                break;
            }
            char c = i == length + (-1) ? ']' : ',';
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i];
            Class<?> cls = fieldDeserializer.fieldInfo.fieldClass;
            if (cls == Integer.TYPE) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanInt(c));
            } else if (cls == String.class) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanString(c));
            } else if (cls == Long.TYPE) {
                fieldDeserializer.setValue(t, jSONLexer.scanLong(c));
            } else if (cls.isEnum()) {
                char current = jSONLexer.getCurrent();
                if (current == '\"' || current == 'n') {
                    enumScanEnum = jSONLexer.scanEnum(cls, defaultJSONParser.getSymbolTable(), c);
                } else if (current >= '0' && current <= '9') {
                    enumScanEnum = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.getConfig())).valueOf(jSONLexer.scanInt(c));
                } else {
                    enumScanEnum = scanEnum(jSONLexer, c);
                }
                fieldDeserializer.setValue(t, enumScanEnum);
            } else if (cls == Boolean.TYPE) {
                fieldDeserializer.setValue(t, jSONLexer.scanBoolean(c));
            } else if (cls == Float.TYPE) {
                fieldDeserializer.setValue(t, Float.valueOf(jSONLexer.scanFloat(c)));
            } else if (cls == Double.TYPE) {
                fieldDeserializer.setValue(t, Double.valueOf(jSONLexer.scanDouble(c)));
            } else if (cls == Date.class && jSONLexer.getCurrent() == '1') {
                fieldDeserializer.setValue(t, new Date(jSONLexer.scanLong(c)));
            } else if (cls == BigDecimal.class) {
                fieldDeserializer.setValue(t, jSONLexer.scanDecimal(c));
            } else {
                jSONLexer.nextToken(14);
                fieldDeserializer.setValue(t, defaultJSONParser.parseObject(fieldDeserializer.fieldInfo.fieldType, fieldDeserializer.fieldInfo.name));
                if (jSONLexer.token() == 15) {
                    break;
                }
                check(jSONLexer, c == ']' ? 15 : 16);
            }
            i++;
        }
        jSONLexer.nextToken(16);
        return t;
    }

    protected void check(JSONLexer lexer, int token) {
        if (lexer.token() != token) {
            throw new JSONException("syntax error");
        }
    }

    protected Enum<?> scanEnum(JSONLexer lexer, char seperator) {
        throw new JSONException("illegal enum. " + lexer.info());
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type update terminated with stack overflow, arg: (r10v50 java.lang.Object), method size: 3758
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    protected <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r43, java.lang.reflect.Type r44, java.lang.Object r45, java.lang.Object r46, int r47, int[] r48) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 3758
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object, int, int[]):java.lang.Object");
    }

    protected Enum scanEnum(JSONLexerBase lexer, char[] name_chars, ObjectDeserializer fieldValueDeserilizer) {
        EnumDeserializer enumDeserializer = null;
        if (fieldValueDeserilizer instanceof EnumDeserializer) {
            enumDeserializer = (EnumDeserializer) fieldValueDeserilizer;
        }
        if (enumDeserializer == null) {
            lexer.matchStat = -1;
            return null;
        }
        long enumNameHashCode = lexer.scanEnumSymbol(name_chars);
        if (lexer.matchStat <= 0) {
            return null;
        }
        Enum e = enumDeserializer.getEnumByHashCode(enumNameHashCode);
        if (e == null) {
            if (enumNameHashCode == TypeUtils.fnv1a_64_magic_hashcode) {
                return null;
            }
            if (lexer.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                throw new JSONException("not match enum value, " + enumDeserializer.enumClass);
            }
        }
        return e;
    }

    public boolean parseField(DefaultJSONParser parser, String key, Object object, Type objectType, Map<String, Object> fieldValues) {
        return parseField(parser, key, object, objectType, fieldValues, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0174  */
    /* JADX WARN: Type inference failed for: r25v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r25v4 */
    /* JADX WARN: Type inference failed for: r25v5 */
    /* JADX WARN: Type inference failed for: r25v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean parseField(DefaultJSONParser defaultJSONParser, String str, Object obj, Type type, Map<String, Object> map, int[] iArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        FieldDeserializer fieldDeserializer;
        FieldDeserializer fieldDeserializer2;
        JSONLexer jSONLexer;
        boolean z;
        ?? r25;
        FieldDeserializer fieldDeserializer3;
        FieldDeserializer fieldDeserializer4;
        FieldDeserializer fieldDeserializer5;
        int i;
        int i2;
        String str2;
        JavaBeanDeserializer javaBeanDeserializer = this;
        JSONLexer jSONLexer2 = defaultJSONParser.lexer;
        int i3 = Feature.DisableFieldSmartMatch.mask;
        int i4 = Feature.InitStringFieldAsEmpty.mask;
        if (jSONLexer2.isEnabled(i3) || (javaBeanDeserializer.beanInfo.parserFeatures & i3) != 0) {
            fieldDeserializer = javaBeanDeserializer.getFieldDeserializer(str);
        } else if (jSONLexer2.isEnabled(i4) || (javaBeanDeserializer.beanInfo.parserFeatures & i4) != 0) {
            fieldDeserializer = javaBeanDeserializer.smartMatch(str);
        } else {
            fieldDeserializer = javaBeanDeserializer.smartMatch(str, iArr);
        }
        int i5 = Feature.SupportNonPublicField.mask;
        if (fieldDeserializer != null) {
            fieldDeserializer2 = fieldDeserializer;
            jSONLexer = jSONLexer2;
            z = true;
        } else if (jSONLexer2.isEnabled(i5) || (javaBeanDeserializer.beanInfo.parserFeatures & i5) != 0) {
            if (javaBeanDeserializer.extraFieldDeserializers != null) {
                fieldDeserializer2 = fieldDeserializer;
                i = i5;
            } else {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(1, 0.75f, 1);
                Class<?> superclass = javaBeanDeserializer.clazz;
                while (superclass != null && superclass != Object.class) {
                    Field[] declaredFields = superclass.getDeclaredFields();
                    int length = declaredFields.length;
                    FieldDeserializer fieldDeserializer6 = fieldDeserializer;
                    int i6 = 0;
                    while (i6 < length) {
                        int i7 = length;
                        Field field = declaredFields[i6];
                        Field[] fieldArr = declaredFields;
                        String name = field.getName();
                        if (javaBeanDeserializer.getFieldDeserializer(name) != null) {
                            i2 = i5;
                        } else {
                            int modifiers = field.getModifiers();
                            if ((modifiers & 16) != 0) {
                                i2 = i5;
                            } else if ((modifiers & 8) != 0) {
                                i2 = i5;
                            } else {
                                JSONField jSONField = (JSONField) TypeUtils.getAnnotation(field, JSONField.class);
                                if (jSONField != null) {
                                    i2 = i5;
                                    String strName = jSONField.name();
                                    if (!"".equals(strName)) {
                                        str2 = strName;
                                    }
                                    concurrentHashMap.put(str2, field);
                                } else {
                                    i2 = i5;
                                }
                                str2 = name;
                                concurrentHashMap.put(str2, field);
                            }
                        }
                        i6++;
                        length = i7;
                        declaredFields = fieldArr;
                        i5 = i2;
                    }
                    superclass = superclass.getSuperclass();
                    fieldDeserializer = fieldDeserializer6;
                }
                fieldDeserializer2 = fieldDeserializer;
                i = i5;
                javaBeanDeserializer.extraFieldDeserializers = concurrentHashMap;
            }
            Object obj2 = javaBeanDeserializer.extraFieldDeserializers.get(str);
            if (obj2 != null) {
                if (obj2 instanceof FieldDeserializer) {
                    fieldDeserializer3 = (FieldDeserializer) obj2;
                    jSONLexer = jSONLexer2;
                    r25 = 1;
                } else {
                    Field field2 = (Field) obj2;
                    field2.setAccessible(true);
                    r25 = 1;
                    jSONLexer = jSONLexer2;
                    DefaultFieldDeserializer defaultFieldDeserializer = new DefaultFieldDeserializer(defaultJSONParser.getConfig(), javaBeanDeserializer.clazz, new FieldInfo(str, field2.getDeclaringClass(), field2.getType(), field2.getGenericType(), field2, 0, 0, 0));
                    javaBeanDeserializer.extraFieldDeserializers.put(str, defaultFieldDeserializer);
                    fieldDeserializer3 = defaultFieldDeserializer;
                }
                if (fieldDeserializer3 != null) {
                    JSONLexer jSONLexer3 = jSONLexer;
                    if (!jSONLexer3.isEnabled(Feature.IgnoreNotMatch)) {
                        throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + str);
                    }
                    int i8 = -1;
                    int i9 = 0;
                    while (i9 < javaBeanDeserializer.sortedFieldDeserializers.length) {
                        FieldDeserializer fieldDeserializer7 = javaBeanDeserializer.sortedFieldDeserializers[i9];
                        FieldInfo fieldInfo = fieldDeserializer7.fieldInfo;
                        if (!fieldInfo.unwrapped || !(fieldDeserializer7 instanceof DefaultFieldDeserializer)) {
                            fieldDeserializer5 = fieldDeserializer3;
                        } else if (fieldInfo.field != null) {
                            DefaultFieldDeserializer defaultFieldDeserializer2 = (DefaultFieldDeserializer) fieldDeserializer7;
                            ObjectDeserializer fieldValueDeserilizer = defaultFieldDeserializer2.getFieldValueDeserilizer(defaultJSONParser.getConfig());
                            if (fieldValueDeserilizer instanceof JavaBeanDeserializer) {
                                fieldDeserializer5 = fieldDeserializer3;
                                FieldDeserializer fieldDeserializer8 = ((JavaBeanDeserializer) fieldValueDeserilizer).getFieldDeserializer(str);
                                if (fieldDeserializer8 != null) {
                                    try {
                                        Object objCreateInstance = fieldInfo.field.get(obj);
                                        if (objCreateInstance == null) {
                                            try {
                                                objCreateInstance = ((JavaBeanDeserializer) fieldValueDeserilizer).createInstance(defaultJSONParser, fieldInfo.fieldType);
                                                fieldDeserializer7.setValue(obj, objCreateInstance);
                                            } catch (Exception e) {
                                                e = e;
                                                throw new JSONException("parse unwrapped field error.", e);
                                            }
                                        }
                                        jSONLexer3.nextTokenWithColon(defaultFieldDeserializer2.getFastMatchToken());
                                        fieldDeserializer8.parseField(defaultJSONParser, objCreateInstance, type, map);
                                        i8 = i9;
                                    } catch (Exception e2) {
                                        e = e2;
                                    }
                                }
                            } else {
                                fieldDeserializer5 = fieldDeserializer3;
                                if (fieldValueDeserilizer instanceof MapDeserializer) {
                                    MapDeserializer mapDeserializer = (MapDeserializer) fieldValueDeserilizer;
                                    try {
                                        Map<Object, Object> mapCreateMap = (Map) fieldInfo.field.get(obj);
                                        if (mapCreateMap == null) {
                                            mapCreateMap = mapDeserializer.createMap(fieldInfo.fieldType);
                                            fieldDeserializer7.setValue(obj, mapCreateMap);
                                        }
                                        jSONLexer3.nextTokenWithColon();
                                        mapCreateMap.put(str, defaultJSONParser.parse(str));
                                        i8 = i9;
                                    } catch (Exception e3) {
                                        throw new JSONException("parse unwrapped field error.", e3);
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } else {
                            fieldDeserializer5 = fieldDeserializer3;
                            if (fieldInfo.method.getParameterTypes().length == 2) {
                                jSONLexer3.nextTokenWithColon();
                                Object obj3 = defaultJSONParser.parse(str);
                                try {
                                    Method method = fieldInfo.method;
                                    Object[] objArr = new Object[2];
                                    objArr[0] = str;
                                    objArr[r25] = obj3;
                                    method.invoke(obj, objArr);
                                    i8 = i9;
                                } catch (Exception e4) {
                                    throw new JSONException("parse unwrapped field error.", e4);
                                }
                            } else {
                                continue;
                            }
                        }
                        i9++;
                        javaBeanDeserializer = this;
                        fieldDeserializer3 = fieldDeserializer5;
                    }
                    if (i8 == -1) {
                        defaultJSONParser.parseExtra(obj, str);
                        return false;
                    }
                    if (iArr != null) {
                        int i10 = i8 / 32;
                        iArr[i10] = iArr[i10] | (r25 << (i8 % 32));
                    }
                    return r25;
                }
                FieldDeserializer fieldDeserializer9 = fieldDeserializer3;
                JSONLexer jSONLexer4 = jSONLexer;
                int i11 = -1;
                int i12 = 0;
                while (true) {
                    if (i12 >= javaBeanDeserializer.sortedFieldDeserializers.length) {
                        fieldDeserializer4 = fieldDeserializer9;
                        break;
                    }
                    fieldDeserializer4 = fieldDeserializer9;
                    if (javaBeanDeserializer.sortedFieldDeserializers[i12] != fieldDeserializer4) {
                        i12++;
                        fieldDeserializer9 = fieldDeserializer4;
                    } else {
                        i11 = i12;
                        break;
                    }
                }
                if (i11 != -1 && iArr != null && str.startsWith("_") && isSetFlag(i11, iArr)) {
                    defaultJSONParser.parseExtra(obj, str);
                    return false;
                }
                jSONLexer4.nextTokenWithColon(fieldDeserializer4.getFastMatchToken());
                fieldDeserializer4.parseField(defaultJSONParser, obj, type, map);
                if (iArr != null) {
                    int i13 = i11 / 32;
                    iArr[i13] = iArr[i13] | (r25 << (i11 % 32));
                }
                return r25;
            }
            jSONLexer = jSONLexer2;
            z = true;
        } else {
            fieldDeserializer2 = fieldDeserializer;
            jSONLexer = jSONLexer2;
            z = true;
        }
        fieldDeserializer3 = fieldDeserializer2;
        r25 = z;
        if (fieldDeserializer3 != null) {
        }
    }

    public FieldDeserializer smartMatch(String key) {
        return smartMatch(key, null);
    }

    public FieldDeserializer smartMatch(String key, int[] setFlags) {
        if (key == null) {
            return null;
        }
        FieldDeserializer fieldDeserializer = getFieldDeserializer(key, setFlags);
        if (fieldDeserializer == null) {
            if (this.smartMatchHashArray == null) {
                long[] hashArray = new long[this.sortedFieldDeserializers.length];
                for (int i = 0; i < this.sortedFieldDeserializers.length; i++) {
                    hashArray[i] = this.sortedFieldDeserializers[i].fieldInfo.nameHashCode;
                }
                Arrays.sort(hashArray);
                this.smartMatchHashArray = hashArray;
            }
            long smartKeyHash = TypeUtils.fnv1a_64_lower(key);
            int pos = Arrays.binarySearch(this.smartMatchHashArray, smartKeyHash);
            if (pos < 0) {
                long smartKeyHash1 = TypeUtils.fnv1a_64_extract(key);
                pos = Arrays.binarySearch(this.smartMatchHashArray, smartKeyHash1);
            }
            boolean is = false;
            if (pos < 0) {
                boolean zStartsWith = key.startsWith("is");
                is = zStartsWith;
                if (zStartsWith) {
                    long smartKeyHash2 = TypeUtils.fnv1a_64_extract(key.substring(2));
                    pos = Arrays.binarySearch(this.smartMatchHashArray, smartKeyHash2);
                }
            }
            if (pos >= 0) {
                if (this.smartMatchHashArrayMapping == null) {
                    short[] mapping = new short[this.smartMatchHashArray.length];
                    Arrays.fill(mapping, (short) -1);
                    for (int i2 = 0; i2 < this.sortedFieldDeserializers.length; i2++) {
                        int p = Arrays.binarySearch(this.smartMatchHashArray, this.sortedFieldDeserializers[i2].fieldInfo.nameHashCode);
                        if (p >= 0) {
                            mapping[p] = (short) i2;
                        }
                    }
                    this.smartMatchHashArrayMapping = mapping;
                }
                short s = this.smartMatchHashArrayMapping[pos];
                if (s != -1 && !isSetFlag(s, setFlags)) {
                    fieldDeserializer = this.sortedFieldDeserializers[s];
                }
            }
            if (fieldDeserializer != null) {
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                if ((fieldInfo.parserFeatures & Feature.DisableFieldSmartMatch.mask) != 0) {
                    return null;
                }
                Class fieldClass = fieldInfo.fieldClass;
                if (is && fieldClass != Boolean.TYPE && fieldClass != Boolean.class) {
                    return null;
                }
                return fieldDeserializer;
            }
            return fieldDeserializer;
        }
        return fieldDeserializer;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    private Object createFactoryInstance(ParserConfig config, Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.beanInfo.factoryMethod.invoke(null, value);
    }

    /* JADX WARN: Removed duplicated region for block: B:82:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0195  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object createInstance(Map<String, Object> map, ParserConfig config) throws IllegalAccessException, InstantiationException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        boolean hasNull;
        Integer index;
        Iterator<Map.Entry<String, Object>> it;
        String format;
        Object value;
        float floatValue;
        double doubleValue;
        String input;
        Map<String, Object> map2 = null;
        if (this.beanInfo.creatorConstructor == null && this.beanInfo.factoryMethod == null) {
            Object object = createInstance((DefaultJSONParser) null, this.clazz);
            Iterator<Map.Entry<String, Object>> it2 = map.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<String, Object> entry = it2.next();
                String key = entry.getKey();
                Object value2 = entry.getValue();
                FieldDeserializer fieldDeser = smartMatch(key);
                if (fieldDeser != null) {
                    FieldInfo fieldInfo = fieldDeser.fieldInfo;
                    Field field = fieldDeser.fieldInfo.field;
                    Type paramType = fieldInfo.fieldType;
                    Class<?> fieldClass = fieldInfo.fieldClass;
                    JSONField fieldAnnation = fieldInfo.getAnnotation();
                    if (fieldInfo.declaringClass != null && (!fieldClass.isInstance(value2) || (fieldAnnation != null && fieldAnnation.deserializeUsing() != Void.class))) {
                        if ((value2 instanceof String) && JSONValidator.from((String) value2).validate()) {
                            input = (String) value2;
                        } else {
                            input = JSON.toJSONString(value2);
                        }
                        DefaultJSONParser parser = new DefaultJSONParser(input);
                        fieldDeser.parseField(parser, object, paramType, map2);
                    } else if (field == null || fieldInfo.method != null) {
                        it = it2;
                        format = fieldInfo.format;
                        if (format != null && paramType == Date.class) {
                            value = TypeUtils.castToDate(value2, format);
                        } else if (format == null && (paramType instanceof Class) && ((Class) paramType).getName().equals("java.time.LocalDateTime")) {
                            value = Jdk8DateCodec.castToLocalDateTime(value2, format);
                        } else if (!(paramType instanceof ParameterizedType)) {
                            value = TypeUtils.cast(value2, (ParameterizedType) paramType, config);
                        } else {
                            value = TypeUtils.cast(value2, paramType, config);
                        }
                        fieldDeser.setValue(object, value);
                        it2 = it;
                        map2 = null;
                    } else {
                        Class fieldType = field.getType();
                        if (fieldType == Boolean.TYPE) {
                            if (value2 == Boolean.FALSE) {
                                field.setBoolean(object, false);
                            } else if (value2 != Boolean.TRUE) {
                                it = it2;
                                format = fieldInfo.format;
                                if (format != null) {
                                    if (format == null) {
                                        if (!(paramType instanceof ParameterizedType)) {
                                        }
                                        fieldDeser.setValue(object, value);
                                        it2 = it;
                                        map2 = null;
                                    }
                                }
                            } else {
                                field.setBoolean(object, true);
                            }
                        } else if (fieldType == Integer.TYPE) {
                            if (!(value2 instanceof Number)) {
                                it = it2;
                                format = fieldInfo.format;
                                if (format != null) {
                                }
                            } else {
                                field.setInt(object, ((Number) value2).intValue());
                            }
                        } else if (fieldType == Long.TYPE) {
                            if (value2 instanceof Number) {
                                field.setLong(object, ((Number) value2).longValue());
                                map2 = null;
                            } else {
                                it = it2;
                                format = fieldInfo.format;
                                if (format != null) {
                                }
                            }
                        } else if (fieldType == Float.TYPE) {
                            if (value2 instanceof Number) {
                                field.setFloat(object, ((Number) value2).floatValue());
                                map2 = null;
                            } else if (!(value2 instanceof String)) {
                                it = it2;
                                format = fieldInfo.format;
                                if (format != null) {
                                }
                            } else {
                                String strVal = (String) value2;
                                Iterator<Map.Entry<String, Object>> it3 = it2;
                                if (strVal.length() <= 10) {
                                    floatValue = TypeUtils.parseFloat(strVal);
                                } else {
                                    floatValue = Float.parseFloat(strVal);
                                }
                                field.setFloat(object, floatValue);
                                it2 = it3;
                                map2 = null;
                            }
                        } else {
                            it = it2;
                            if (fieldType == Double.TYPE) {
                                if (value2 instanceof Number) {
                                    field.setDouble(object, ((Number) value2).doubleValue());
                                    it2 = it;
                                    map2 = null;
                                } else if (value2 instanceof String) {
                                    String strVal2 = (String) value2;
                                    if (strVal2.length() <= 10) {
                                        doubleValue = TypeUtils.parseDouble(strVal2);
                                    } else {
                                        doubleValue = Double.parseDouble(strVal2);
                                    }
                                    field.setDouble(object, doubleValue);
                                    it2 = it;
                                    map2 = null;
                                } else {
                                    format = fieldInfo.format;
                                    if (format != null) {
                                    }
                                }
                            } else if (value2 != null && paramType == value2.getClass()) {
                                field.set(object, value2);
                                it2 = it;
                                map2 = null;
                            } else {
                                format = fieldInfo.format;
                                if (format != null) {
                                }
                            }
                        }
                    }
                }
            }
            if (this.beanInfo.buildMethod != null) {
                try {
                    Object builtObj = this.beanInfo.buildMethod.invoke(object, new Object[0]);
                    return builtObj;
                } catch (Exception e) {
                    throw new JSONException("build object error", e);
                }
            }
            return object;
        }
        FieldInfo[] fieldInfoList = this.beanInfo.fields;
        int size = fieldInfoList.length;
        Object[] params = new Object[size];
        Map<String, Integer> missFields = null;
        for (int i = 0; i < size; i++) {
            FieldInfo fieldInfo2 = fieldInfoList[i];
            Object param = map.get(fieldInfo2.name);
            if (param == null) {
                Class<?> fieldClass2 = fieldInfo2.fieldClass;
                if (fieldClass2 == Integer.TYPE) {
                    param = 0;
                } else if (fieldClass2 == Long.TYPE) {
                    param = 0L;
                } else if (fieldClass2 == Short.TYPE) {
                    param = (short) 0;
                } else if (fieldClass2 == Byte.TYPE) {
                    param = (byte) 0;
                } else if (fieldClass2 == Float.TYPE) {
                    param = Float.valueOf(0.0f);
                } else if (fieldClass2 == Double.TYPE) {
                    param = Double.valueOf(0.0d);
                } else if (fieldClass2 == Character.TYPE) {
                    param = '0';
                } else if (fieldClass2 == Boolean.TYPE) {
                    param = false;
                }
                if (missFields == null) {
                    missFields = new HashMap<>();
                }
                missFields.put(fieldInfo2.name, Integer.valueOf(i));
            }
            params[i] = param;
        }
        if (missFields != null) {
            for (Map.Entry<String, Object> entry2 : map.entrySet()) {
                String key2 = entry2.getKey();
                Object value3 = entry2.getValue();
                FieldDeserializer fieldDeser2 = smartMatch(key2);
                if (fieldDeser2 != null && (index = missFields.get(fieldDeser2.fieldInfo.name)) != null) {
                    params[index.intValue()] = value3;
                }
            }
        }
        if (this.beanInfo.creatorConstructor != null) {
            boolean hasNull2 = false;
            if (!this.beanInfo.f3kotlin) {
                hasNull = false;
            } else {
                for (int i2 = 0; i2 < params.length; i2++) {
                    Object param2 = params[i2];
                    if (param2 != null) {
                        if (param2.getClass() != this.beanInfo.fields[i2].fieldClass) {
                            params[i2] = TypeUtils.cast(param2, (Class) this.beanInfo.fields[i2].fieldClass, config);
                        }
                    } else if (this.beanInfo.fields != null && i2 < this.beanInfo.fields.length) {
                        FieldInfo fieldInfo3 = this.beanInfo.fields[i2];
                        if (fieldInfo3.fieldClass == String.class) {
                            hasNull2 = true;
                        }
                    }
                }
                hasNull = hasNull2;
            }
            if (hasNull && this.beanInfo.kotlinDefaultConstructor != null) {
                try {
                    Object object2 = this.beanInfo.kotlinDefaultConstructor.newInstance(new Object[0]);
                    for (int i3 = 0; i3 < params.length; i3++) {
                        Object param3 = params[i3];
                        if (param3 != null && this.beanInfo.fields != null && i3 < this.beanInfo.fields.length) {
                            FieldInfo fieldInfo4 = this.beanInfo.fields[i3];
                            fieldInfo4.set(object2, param3);
                        }
                    }
                    return object2;
                } catch (Exception e2) {
                    throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e2);
                }
            }
            try {
                Object object3 = this.beanInfo.creatorConstructor.newInstance(params);
                return object3;
            } catch (Exception e3) {
                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e3);
            }
        }
        if (this.beanInfo.factoryMethod == null) {
            return null;
        }
        try {
            Object object4 = this.beanInfo.factoryMethod.invoke(null, params);
            return object4;
        } catch (Exception e4) {
            throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), e4);
        }
    }

    public Type getFieldType(int ordinal) {
        return this.sortedFieldDeserializers[ordinal].fieldInfo.fieldType;
    }

    protected Object parseRest(DefaultJSONParser parser, Type type, Object fieldName, Object instance, int features) {
        return parseRest(parser, type, fieldName, instance, features, new int[0]);
    }

    protected Object parseRest(DefaultJSONParser parser, Type type, Object fieldName, Object instance, int features, int[] setFlags) throws Throwable {
        Object value = deserialze(parser, type, fieldName, instance, features, setFlags);
        return value;
    }

    protected static JavaBeanDeserializer getSeeAlso(ParserConfig config, JavaBeanInfo beanInfo, String typeName) {
        if (beanInfo.jsonType == null) {
            return null;
        }
        for (Class<?> seeAlsoClass : beanInfo.jsonType.seeAlso()) {
            ObjectDeserializer seeAlsoDeser = config.getDeserializer(seeAlsoClass);
            if (seeAlsoDeser instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer seeAlsoJavaBeanDeser = (JavaBeanDeserializer) seeAlsoDeser;
                JavaBeanInfo subBeanInfo = seeAlsoJavaBeanDeser.beanInfo;
                if (subBeanInfo.typeName.equals(typeName)) {
                    return seeAlsoJavaBeanDeser;
                }
                JavaBeanDeserializer subSeeAlso = getSeeAlso(config, subBeanInfo, typeName);
                if (subSeeAlso != null) {
                    return subSeeAlso;
                }
            }
        }
        return null;
    }

    protected static void parseArray(Collection collection, ObjectDeserializer deser, DefaultJSONParser parser, Type type, Object fieldName) throws NumberFormatException {
        JSONLexerBase lexer = (JSONLexerBase) parser.lexer;
        int token = lexer.token();
        if (token == 8) {
            lexer.nextToken(16);
            lexer.token();
            return;
        }
        if (token != 14) {
            parser.throwException(token);
        }
        char ch = lexer.getCurrent();
        if (ch == '[') {
            lexer.next();
            lexer.setToken(14);
        } else {
            lexer.nextToken(14);
        }
        if (lexer.token() == 15) {
            lexer.nextToken();
            return;
        }
        int index = 0;
        while (true) {
            Object item = deser.deserialze(parser, type, Integer.valueOf(index));
            collection.add(item);
            index++;
            if (lexer.token() != 16) {
                break;
            }
            char ch2 = lexer.getCurrent();
            if (ch2 == '[') {
                lexer.next();
                lexer.setToken(14);
            } else {
                lexer.nextToken(14);
            }
        }
        int token2 = lexer.token();
        if (token2 != 15) {
            parser.throwException(token2);
        }
        char ch3 = lexer.getCurrent();
        if (ch3 == ',') {
            lexer.next();
            lexer.setToken(16);
        } else {
            lexer.nextToken(16);
        }
    }
}