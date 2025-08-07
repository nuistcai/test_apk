package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class JavaBeanInfo {
    public final Method buildMethod;
    public final Class<?> builderClass;
    public final Class<?> clazz;
    public final Constructor<?> creatorConstructor;
    public Type[] creatorConstructorParameterTypes;
    public String[] creatorConstructorParameters;
    public final Constructor<?> defaultConstructor;
    public final int defaultConstructorParameterSize;
    public final Method factoryMethod;
    public final FieldInfo[] fields;
    public final JSONType jsonType;

    /* renamed from: kotlin, reason: collision with root package name */
    public boolean f3kotlin;
    public Constructor<?> kotlinDefaultConstructor;
    public String[] orders;
    public final int parserFeatures;
    public final FieldInfo[] sortedFields;
    public final String typeKey;
    public final String typeName;

    public JavaBeanInfo(Class<?> clazz, Class<?> builderClass, Constructor<?> defaultConstructor, Constructor<?> creatorConstructor, Method factoryMethod, Method buildMethod, JSONType jsonType, List<FieldInfo> fieldList) {
        FieldInfo[] sortedFields;
        boolean match;
        Annotation[][] paramAnnotationArrays;
        this.clazz = clazz;
        this.builderClass = builderClass;
        this.defaultConstructor = defaultConstructor;
        this.creatorConstructor = creatorConstructor;
        this.factoryMethod = factoryMethod;
        this.parserFeatures = TypeUtils.getParserFeatures(clazz);
        this.buildMethod = buildMethod;
        this.jsonType = jsonType;
        if (jsonType == null) {
            this.typeName = clazz.getName();
            this.typeKey = null;
            this.orders = null;
        } else {
            String typeName = jsonType.typeName();
            String typeKey = jsonType.typeKey();
            this.typeKey = typeKey.length() > 0 ? typeKey : null;
            if (typeName.length() == 0) {
                this.typeName = clazz.getName();
            } else {
                this.typeName = typeName;
            }
            String[] orders = jsonType.orders();
            this.orders = orders.length != 0 ? orders : null;
        }
        this.fields = new FieldInfo[fieldList.size()];
        fieldList.toArray(this.fields);
        FieldInfo[] sortedFields2 = new FieldInfo[this.fields.length];
        if (this.orders == null) {
            System.arraycopy(this.fields, 0, sortedFields2, 0, this.fields.length);
            Arrays.sort(sortedFields2);
        } else {
            LinkedHashMap<String, FieldInfo> map = new LinkedHashMap<>(fieldList.size());
            for (FieldInfo field : this.fields) {
                map.put(field.name, field);
            }
            int i = 0;
            for (String item : this.orders) {
                FieldInfo field2 = map.get(item);
                if (field2 != null) {
                    sortedFields2[i] = field2;
                    map.remove(item);
                    i++;
                }
            }
            Iterator<FieldInfo> it = map.values().iterator();
            while (it.hasNext()) {
                sortedFields2[i] = it.next();
                i++;
            }
        }
        if (!Arrays.equals(this.fields, sortedFields2)) {
            sortedFields = sortedFields2;
        } else {
            sortedFields = this.fields;
        }
        this.sortedFields = sortedFields;
        if (defaultConstructor != null) {
            this.defaultConstructorParameterSize = defaultConstructor.getParameterTypes().length;
        } else if (factoryMethod != null) {
            this.defaultConstructorParameterSize = factoryMethod.getParameterTypes().length;
        } else {
            this.defaultConstructorParameterSize = 0;
        }
        if (creatorConstructor != null) {
            this.creatorConstructorParameterTypes = creatorConstructor.getParameterTypes();
            this.f3kotlin = TypeUtils.isKotlin(clazz);
            if (this.f3kotlin) {
                this.creatorConstructorParameters = TypeUtils.getKoltinConstructorParameters(clazz);
                int i2 = 0;
                try {
                    this.kotlinDefaultConstructor = clazz.getConstructor(new Class[0]);
                } catch (Throwable th) {
                }
                Annotation[][] paramAnnotationArrays2 = TypeUtils.getParameterAnnotations(creatorConstructor);
                int i3 = 0;
                while (i3 < this.creatorConstructorParameters.length && i3 < paramAnnotationArrays2.length) {
                    Annotation[] paramAnnotations = paramAnnotationArrays2[i3];
                    JSONField fieldAnnotation = null;
                    int length = paramAnnotations.length;
                    while (true) {
                        if (i2 >= length) {
                            paramAnnotationArrays = paramAnnotationArrays2;
                            break;
                        }
                        paramAnnotationArrays = paramAnnotationArrays2;
                        Annotation paramAnnotation = paramAnnotations[i2];
                        if (!(paramAnnotation instanceof JSONField)) {
                            i2++;
                            paramAnnotationArrays2 = paramAnnotationArrays;
                        } else {
                            fieldAnnotation = (JSONField) paramAnnotation;
                            break;
                        }
                    }
                    if (fieldAnnotation != null) {
                        String fieldAnnotationName = fieldAnnotation.name();
                        if (fieldAnnotationName.length() > 0) {
                            this.creatorConstructorParameters[i3] = fieldAnnotationName;
                        }
                    }
                    i3++;
                    paramAnnotationArrays2 = paramAnnotationArrays;
                    i2 = 0;
                }
                return;
            }
            if (this.creatorConstructorParameterTypes.length != this.fields.length) {
                match = false;
            } else {
                match = true;
                int i4 = 0;
                while (true) {
                    if (i4 >= this.creatorConstructorParameterTypes.length) {
                        break;
                    }
                    if (this.creatorConstructorParameterTypes[i4] == this.fields[i4].fieldClass) {
                        i4++;
                    } else {
                        match = false;
                        break;
                    }
                }
            }
            if (!match) {
                this.creatorConstructorParameters = ASMUtils.lookupParameterNames(creatorConstructor);
            }
        }
    }

    private static FieldInfo getField(List<FieldInfo> fieldList, String propertyName) {
        for (FieldInfo item : fieldList) {
            if (item.name.equals(propertyName)) {
                return item;
            }
            Field field = item.field;
            if (field != null && item.getAnnotation() != null && field.getName().equals(propertyName)) {
                return item;
            }
        }
        return null;
    }

    static boolean add(List<FieldInfo> fieldList, FieldInfo field) {
        for (int i = fieldList.size() - 1; i >= 0; i--) {
            FieldInfo item = fieldList.get(i);
            if (item.name.equals(field.name) && (!item.getOnly || field.getOnly)) {
                if (item.fieldClass.isAssignableFrom(field.fieldClass)) {
                    fieldList.set(i, field);
                    return true;
                }
                int result = item.compareTo(field);
                if (result < 0) {
                    fieldList.set(i, field);
                    return true;
                }
                return false;
            }
        }
        fieldList.add(field);
        return true;
    }

    public static JavaBeanInfo build(Class<?> clazz, Type type, PropertyNamingStrategy propertyNamingStrategy) {
        return build(clazz, type, propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean, false);
    }

    private static Map<TypeVariable, Type> buildGenericInfo(Class<?> clazz) {
        Class<?> childClass = clazz;
        Class<?> currentClass = clazz.getSuperclass();
        if (currentClass == null) {
            return null;
        }
        Map<TypeVariable, Type> typeVarMap = null;
        while (currentClass != null && currentClass != Object.class) {
            if (childClass.getGenericSuperclass() instanceof ParameterizedType) {
                Type[] childGenericParentActualTypeArgs = ((ParameterizedType) childClass.getGenericSuperclass()).getActualTypeArguments();
                TypeVariable[] currentTypeParameters = currentClass.getTypeParameters();
                for (int i = 0; i < childGenericParentActualTypeArgs.length; i++) {
                    if (typeVarMap == null) {
                        typeVarMap = new HashMap<>();
                    }
                    if (typeVarMap.containsKey(childGenericParentActualTypeArgs[i])) {
                        Type actualArg = typeVarMap.get(childGenericParentActualTypeArgs[i]);
                        typeVarMap.put(currentTypeParameters[i], actualArg);
                    } else {
                        typeVarMap.put(currentTypeParameters[i], childGenericParentActualTypeArgs[i]);
                    }
                }
            }
            childClass = currentClass;
            currentClass = currentClass.getSuperclass();
        }
        return typeVarMap;
    }

    public static JavaBeanInfo build(Class<?> clazz, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean fieldBased, boolean compatibleWithJavaBean) {
        return build(clazz, type, propertyNamingStrategy, fieldBased, compatibleWithJavaBean, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:192:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x041b  */
    /* JADX WARN: Removed duplicated region for block: B:425:0x0aa9  */
    /* JADX WARN: Removed duplicated region for block: B:434:0x0ae2  */
    /* JADX WARN: Removed duplicated region for block: B:444:0x0b89  */
    /* JADX WARN: Removed duplicated region for block: B:447:0x0ba2  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0ba9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static JavaBeanInfo build(Class<?> clazz, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean fieldBased, boolean compatibleWithJavaBean, boolean jacksonCompatible) {
        PropertyNamingStrategy propertyNamingStrategy2;
        Constructor<?> defaultConstructor;
        List<FieldInfo> fieldList;
        PropertyNamingStrategy propertyNamingStrategy3;
        Class<?> builderClass;
        JSONType jsonType;
        Field[] declaredFields;
        Class<?> cls;
        Method[] methods;
        String str;
        Field[] declaredFields2;
        Class<?> builderClass2;
        List<FieldInfo> fieldList2;
        char c;
        String str2;
        int i;
        boolean fieldBased2;
        Field[] fields;
        int i2;
        int i3;
        Method[] methodArr;
        String str3;
        PropertyNamingStrategy propertyNamingStrategy4;
        Field[] declaredFields3;
        String propertyName;
        Field collectionField;
        String propertyName2;
        int i4;
        Class<?> builderClass3;
        Method[] methods2;
        Field[] declaredFields4;
        String str4;
        PropertyNamingStrategy propertyNamingStrategy5;
        Method method;
        Method method2;
        Class<?>[] types;
        String methodName;
        String str5;
        List<String> getMethodNameList;
        int i5;
        Class<?> cls2;
        Field[] declaredFields5;
        String propertyName3;
        Field field;
        Field field2;
        JSONField fieldAnnotation;
        PropertyNamingStrategy propertyNamingStrategy6;
        String propertyName4;
        String withPrefix;
        JSONField annotation;
        Method method3;
        int i6;
        int i7;
        String withPrefix2;
        Class<?> builderClass4;
        Field[] declaredFields6;
        String str6;
        List<FieldInfo> fieldList3;
        int ordinal;
        int serialzeFeatures;
        int parserFeatures;
        String withPrefix3;
        StringBuilder properNameBuilder;
        String withPrefix4;
        String str7;
        List<FieldInfo> fieldList4;
        Constructor<?> creatorConstructor;
        Constructor<?> creatorConstructor2;
        Method factoryMethod;
        Constructor<?>[] constructors;
        String[] paramNames;
        int i8;
        String[] lookupParameterNames;
        Constructor<?> constructor;
        Class<?>[] types2;
        JSONField fieldAnnotation2;
        JSONField fieldAnnotation3;
        int serialzeFeatures2;
        int parserFeatures2;
        String paramName;
        int ordinal2;
        Constructor<?> creatorConstructor3;
        JSONField fieldAnnotation4;
        int ordinal3;
        int serialzeFeatures3;
        int parserFeatures3;
        List<FieldInfo> fieldList5;
        PropertyNamingStrategy propertyNamingStrategy7;
        Annotation[][] paramAnnotationArrays;
        int parserFeatures4;
        int parserFeatures5;
        int serialzeFeatures4;
        String fieldName;
        PropertyNamingStrategy jsonTypeNaming;
        Class<?> cls3 = clazz;
        JSONType jsonType2 = (JSONType) TypeUtils.getAnnotation(cls3, JSONType.class);
        if (jsonType2 != null && (jsonTypeNaming = jsonType2.naming()) != null && jsonTypeNaming != PropertyNamingStrategy.CamelCase) {
            propertyNamingStrategy2 = jsonTypeNaming;
        } else {
            propertyNamingStrategy2 = propertyNamingStrategy;
        }
        Class<?> builderClass5 = getBuilderClass(cls3, jsonType2);
        Field[] declaredFields7 = clazz.getDeclaredFields();
        Method[] methods3 = clazz.getMethods();
        Map<TypeVariable, Type> genericInfo = buildGenericInfo(clazz);
        boolean kotlin2 = TypeUtils.isKotlin(clazz);
        Constructor<?>[] constructors2 = clazz.getDeclaredConstructors();
        if (kotlin2 && constructors2.length != 1) {
            defaultConstructor = null;
        } else if (builderClass5 == null) {
            Constructor<?> defaultConstructor2 = getDefaultConstructor(cls3, constructors2);
            defaultConstructor = defaultConstructor2;
        } else {
            Constructor<?> defaultConstructor3 = getDefaultConstructor(builderClass5, builderClass5.getDeclaredConstructors());
            defaultConstructor = defaultConstructor3;
        }
        Constructor<?> creatorConstructor4 = null;
        Method buildMethod = null;
        Method factoryMethod2 = null;
        List<FieldInfo> fieldList6 = new ArrayList<>();
        if (fieldBased) {
            for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
                computeFields(cls3, type, propertyNamingStrategy2, fieldList6, currentClass.getDeclaredFields());
            }
            if (defaultConstructor != null) {
                TypeUtils.setAccessible(defaultConstructor);
            }
            return new JavaBeanInfo(clazz, builderClass5, defaultConstructor, null, null, null, jsonType2, fieldList6);
        }
        boolean isInterfaceOrAbstract = clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers());
        if ((defaultConstructor != null || builderClass5 != null) && !isInterfaceOrAbstract) {
            fieldList = fieldList6;
            propertyNamingStrategy3 = propertyNamingStrategy2;
            builderClass = builderClass5;
            jsonType = jsonType2;
            declaredFields = declaredFields7;
            cls = cls3;
            methods = methods3;
        } else {
            Type mixInType = JSON.getMixInAnnotations(clazz);
            if (mixInType instanceof Class) {
                Constructor<?>[] mixInConstructors = ((Class) mixInType).getConstructors();
                Constructor<?> mixInCreator = getCreatorConstructor(mixInConstructors);
                if (mixInCreator != null) {
                    try {
                        creatorConstructor4 = cls3.getConstructor(mixInCreator.getParameterTypes());
                    } catch (NoSuchMethodException e) {
                    }
                }
            }
            if (creatorConstructor4 != null) {
                creatorConstructor = creatorConstructor4;
            } else {
                creatorConstructor = getCreatorConstructor(constructors2);
            }
            if (creatorConstructor == null || isInterfaceOrAbstract) {
                List<FieldInfo> fieldList7 = fieldList6;
                PropertyNamingStrategy propertyNamingStrategy8 = propertyNamingStrategy2;
                Method factoryMethod3 = getFactoryMethod(cls3, methods3, jacksonCompatible);
                Class<?> builderClass6 = builderClass5;
                Method factoryMethod4 = factoryMethod3;
                if (factoryMethod3 != null) {
                    TypeUtils.setAccessible(factoryMethod4);
                    String[] lookupParameterNames2 = null;
                    Class<?>[] types3 = factoryMethod4.getParameterTypes();
                    if (types3.length <= 0) {
                        creatorConstructor2 = creatorConstructor;
                        builderClass = builderClass6;
                        jsonType = jsonType2;
                        fieldList = fieldList7;
                        cls = cls3;
                        methods = methods3;
                        propertyNamingStrategy3 = propertyNamingStrategy8;
                        factoryMethod = factoryMethod4;
                        declaredFields = declaredFields7;
                    } else {
                        Annotation[][] paramAnnotationArrays2 = TypeUtils.getParameterAnnotations(factoryMethod4);
                        int i9 = 0;
                        while (i9 < types3.length) {
                            Annotation[] paramAnnotations = paramAnnotationArrays2[i9];
                            int length = paramAnnotations.length;
                            int i10 = 0;
                            while (true) {
                                if (i10 >= length) {
                                    creatorConstructor3 = creatorConstructor;
                                    fieldAnnotation4 = null;
                                    break;
                                }
                                Annotation paramAnnotation = paramAnnotations[i10];
                                creatorConstructor3 = creatorConstructor;
                                if (!(paramAnnotation instanceof JSONField)) {
                                    i10++;
                                    creatorConstructor = creatorConstructor3;
                                } else {
                                    fieldAnnotation4 = (JSONField) paramAnnotation;
                                    break;
                                }
                            }
                            if (fieldAnnotation4 == null && (!jacksonCompatible || !TypeUtils.isJacksonCreator(factoryMethod4))) {
                                throw new JSONException("illegal json creator");
                            }
                            String fieldName2 = null;
                            if (fieldAnnotation4 == null) {
                                ordinal3 = 0;
                                serialzeFeatures3 = 0;
                                parserFeatures3 = 0;
                            } else {
                                fieldName2 = fieldAnnotation4.name();
                                int ordinal4 = fieldAnnotation4.ordinal();
                                int serialzeFeatures5 = SerializerFeature.of(fieldAnnotation4.serialzeFeatures());
                                int parserFeatures6 = Feature.of(fieldAnnotation4.parseFeatures());
                                ordinal3 = ordinal4;
                                serialzeFeatures3 = serialzeFeatures5;
                                parserFeatures3 = parserFeatures6;
                            }
                            if (fieldName2 == null || fieldName2.length() == 0) {
                                if (lookupParameterNames2 == null) {
                                    lookupParameterNames2 = ASMUtils.lookupParameterNames(factoryMethod4);
                                }
                                fieldName2 = lookupParameterNames2[i9];
                            }
                            String fieldName3 = fieldName2;
                            Class<?> fieldClass = types3[i9];
                            Type fieldType = factoryMethod4.getGenericParameterTypes()[i9];
                            List<FieldInfo> fieldList8 = fieldList7;
                            FieldInfo fieldInfo = new FieldInfo(fieldName3, clazz, fieldClass, fieldType, TypeUtils.getField(cls3, fieldName3, declaredFields7), ordinal3, serialzeFeatures3, parserFeatures3);
                            add(fieldList8, fieldInfo);
                            i9++;
                            creatorConstructor = creatorConstructor3;
                            fieldList7 = fieldList8;
                            lookupParameterNames2 = lookupParameterNames2;
                            types3 = types3;
                            builderClass6 = builderClass6;
                            cls3 = clazz;
                        }
                        return new JavaBeanInfo(clazz, builderClass6, null, null, factoryMethod4, null, jsonType2, fieldList7);
                    }
                } else {
                    Type type2 = type;
                    creatorConstructor2 = creatorConstructor;
                    builderClass = builderClass6;
                    List<FieldInfo> fieldList9 = fieldList7;
                    Field[] declaredFields8 = declaredFields7;
                    Method[] methods4 = methods3;
                    Class<?> cls4 = cls3;
                    PropertyNamingStrategy propertyNamingStrategy9 = propertyNamingStrategy8;
                    jsonType = jsonType2;
                    if (isInterfaceOrAbstract) {
                        fieldList = fieldList9;
                        cls = cls4;
                        methods = methods4;
                        propertyNamingStrategy3 = propertyNamingStrategy9;
                        factoryMethod = factoryMethod4;
                        declaredFields = declaredFields8;
                    } else {
                        String className = clazz.getName();
                        if (!kotlin2) {
                            constructors = constructors2;
                        } else {
                            constructors = constructors2;
                            if (constructors.length > 0) {
                                String[] paramNames2 = TypeUtils.getKoltinConstructorParameters(clazz);
                                Constructor<?> creatorConstructor5 = TypeUtils.getKotlinConstructor(constructors, paramNames2);
                                TypeUtils.setAccessible(creatorConstructor5);
                                paramNames = paramNames2;
                                creatorConstructor4 = creatorConstructor5;
                            }
                            if (paramNames != null) {
                                types2 = null;
                            } else {
                                types2 = creatorConstructor4.getParameterTypes();
                            }
                            if (paramNames == null && types2.length == paramNames.length) {
                                Annotation[][] paramAnnotationArrays3 = TypeUtils.getParameterAnnotations(creatorConstructor4);
                                int i11 = 0;
                                while (i11 < types2.length) {
                                    Annotation[] paramAnnotations2 = paramAnnotationArrays3[i11];
                                    String paramName2 = paramNames[i11];
                                    int length2 = paramAnnotations2.length;
                                    List<FieldInfo> fieldList10 = fieldList9;
                                    int i12 = 0;
                                    while (true) {
                                        if (i12 >= length2) {
                                            fieldAnnotation2 = null;
                                            break;
                                        }
                                        int i13 = length2;
                                        Annotation paramAnnotation2 = paramAnnotations2[i12];
                                        Annotation[] paramAnnotations3 = paramAnnotations2;
                                        if (!(paramAnnotation2 instanceof JSONField)) {
                                            i12++;
                                            length2 = i13;
                                            paramAnnotations2 = paramAnnotations3;
                                        } else {
                                            fieldAnnotation2 = (JSONField) paramAnnotation2;
                                            break;
                                        }
                                    }
                                    Class<?> fieldClass2 = types2[i11];
                                    Type fieldType2 = creatorConstructor4.getGenericParameterTypes()[i11];
                                    Field field3 = TypeUtils.getField(cls4, paramName2, declaredFields8);
                                    if (field3 != null && fieldAnnotation2 == null) {
                                        fieldAnnotation3 = (JSONField) TypeUtils.getAnnotation(field3, JSONField.class);
                                    } else {
                                        fieldAnnotation3 = fieldAnnotation2;
                                    }
                                    if (fieldAnnotation3 == null) {
                                        if (!"org.springframework.security.core.userdetails.User".equals(className) || !"password".equals(paramName2)) {
                                            ordinal2 = 0;
                                            paramName = paramName2;
                                            parserFeatures2 = 0;
                                            serialzeFeatures2 = 0;
                                        } else {
                                            ordinal2 = 0;
                                            paramName = paramName2;
                                            parserFeatures2 = Feature.InitStringFieldAsEmpty.mask;
                                            serialzeFeatures2 = 0;
                                        }
                                    } else {
                                        String nameAnnotated = fieldAnnotation3.name();
                                        if (nameAnnotated.length() != 0) {
                                            paramName2 = nameAnnotated;
                                        }
                                        int ordinal5 = fieldAnnotation3.ordinal();
                                        int serialzeFeatures6 = SerializerFeature.of(fieldAnnotation3.serialzeFeatures());
                                        serialzeFeatures2 = serialzeFeatures6;
                                        parserFeatures2 = Feature.of(fieldAnnotation3.parseFeatures());
                                        paramName = paramName2;
                                        ordinal2 = ordinal5;
                                    }
                                    FieldInfo fieldInfo2 = new FieldInfo(paramName, clazz, fieldClass2, fieldType2, field3, ordinal2, serialzeFeatures2, parserFeatures2);
                                    add(fieldList10, fieldInfo2);
                                    i11++;
                                    type2 = type;
                                    cls4 = cls4;
                                    fieldList9 = fieldList10;
                                    methods4 = methods4;
                                    declaredFields8 = declaredFields8;
                                    types2 = types2;
                                    constructors = constructors;
                                    factoryMethod4 = factoryMethod4;
                                    propertyNamingStrategy9 = propertyNamingStrategy9;
                                    paramAnnotationArrays3 = paramAnnotationArrays3;
                                }
                                propertyNamingStrategy3 = propertyNamingStrategy9;
                                Method factoryMethod5 = factoryMethod4;
                                fieldList = fieldList9;
                                cls = cls4;
                                methods = methods4;
                                declaredFields = declaredFields8;
                                if (!kotlin2 && !clazz.getName().equals("javax.servlet.http.Cookie")) {
                                    return new JavaBeanInfo(clazz, builderClass, null, creatorConstructor4, null, null, jsonType, fieldList);
                                }
                                factoryMethod2 = factoryMethod5;
                            } else {
                                throw new JSONException("default constructor not found. " + cls4);
                            }
                        }
                        int length3 = constructors.length;
                        String[] paramNames3 = null;
                        int i14 = 0;
                        Constructor<?> creatorConstructor6 = creatorConstructor2;
                        while (true) {
                            if (i14 >= length3) {
                                paramNames = paramNames3;
                                creatorConstructor4 = creatorConstructor6;
                                break;
                            }
                            Constructor<?> constructor2 = constructors[i14];
                            Class<?>[] parameterTypes = constructor2.getParameterTypes();
                            if (className.equals("org.springframework.security.web.authentication.WebAuthenticationDetails")) {
                                i8 = length3;
                                if (parameterTypes.length != 2 || parameterTypes[0] != String.class || parameterTypes[1] != String.class) {
                                    constructor = creatorConstructor6;
                                    creatorConstructor6 = constructor;
                                    i14++;
                                    length3 = i8;
                                } else {
                                    constructor2.setAccessible(true);
                                    String[] paramNames4 = ASMUtils.lookupParameterNames(constructor2);
                                    creatorConstructor4 = constructor2;
                                    paramNames = paramNames4;
                                    break;
                                }
                            } else {
                                i8 = length3;
                                if (className.equals("org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken")) {
                                    if (parameterTypes.length != 3 || parameterTypes[0] != Object.class || parameterTypes[1] != Object.class || parameterTypes[2] != Collection.class) {
                                        constructor = creatorConstructor6;
                                        creatorConstructor6 = constructor;
                                        i14++;
                                        length3 = i8;
                                    } else {
                                        constructor2.setAccessible(true);
                                        String[] paramNames5 = {"principal", "credentials", "authorities"};
                                        creatorConstructor4 = constructor2;
                                        paramNames = paramNames5;
                                        break;
                                    }
                                } else {
                                    if (className.equals("org.springframework.security.core.authority.SimpleGrantedAuthority")) {
                                        if (parameterTypes.length != 1 || parameterTypes[0] != String.class) {
                                            constructor = creatorConstructor6;
                                        } else {
                                            String[] paramNames6 = {"authority"};
                                            creatorConstructor4 = constructor2;
                                            paramNames = paramNames6;
                                            break;
                                        }
                                    } else {
                                        boolean is_public = (constructor2.getModifiers() & 1) != 0;
                                        if (!is_public || (lookupParameterNames = ASMUtils.lookupParameterNames(constructor2)) == null || lookupParameterNames.length == 0) {
                                            constructor = creatorConstructor6;
                                        } else {
                                            if (creatorConstructor6 != null && paramNames3 != null) {
                                                constructor = creatorConstructor6;
                                                if (lookupParameterNames.length <= paramNames3.length) {
                                                }
                                                i14++;
                                                length3 = i8;
                                            }
                                            paramNames3 = lookupParameterNames;
                                            creatorConstructor6 = constructor2;
                                            i14++;
                                            length3 = i8;
                                        }
                                    }
                                    creatorConstructor6 = constructor;
                                    i14++;
                                    length3 = i8;
                                }
                            }
                        }
                        if (paramNames != null) {
                        }
                        if (paramNames == null) {
                        }
                        throw new JSONException("default constructor not found. " + cls4);
                    }
                }
                creatorConstructor4 = creatorConstructor2;
                factoryMethod2 = factoryMethod;
            } else {
                TypeUtils.setAccessible(creatorConstructor);
                Class<?>[] types4 = creatorConstructor.getParameterTypes();
                String[] lookupParameterNames3 = null;
                if (types4.length <= 0) {
                    fieldList5 = fieldList6;
                    propertyNamingStrategy7 = propertyNamingStrategy2;
                } else {
                    Annotation[][] paramAnnotationArrays4 = TypeUtils.getParameterAnnotations(creatorConstructor);
                    int i15 = 0;
                    while (i15 < types4.length && i15 < paramAnnotationArrays4.length) {
                        Annotation[] paramAnnotations4 = paramAnnotationArrays4[i15];
                        JSONField fieldAnnotation5 = null;
                        int length4 = paramAnnotations4.length;
                        int i16 = 0;
                        while (true) {
                            if (i16 >= length4) {
                                paramAnnotationArrays = paramAnnotationArrays4;
                                break;
                            }
                            Annotation paramAnnotation3 = paramAnnotations4[i16];
                            paramAnnotationArrays = paramAnnotationArrays4;
                            if (!(paramAnnotation3 instanceof JSONField)) {
                                i16++;
                                paramAnnotationArrays4 = paramAnnotationArrays;
                            } else {
                                fieldAnnotation5 = (JSONField) paramAnnotation3;
                                break;
                            }
                        }
                        Class<?> fieldClass3 = types4[i15];
                        Type fieldType3 = creatorConstructor.getGenericParameterTypes()[i15];
                        String fieldName4 = null;
                        Field field4 = null;
                        if (fieldAnnotation5 == null) {
                            parserFeatures4 = 0;
                            parserFeatures5 = 0;
                            serialzeFeatures4 = 0;
                        } else {
                            String fieldName5 = fieldAnnotation5.name();
                            field4 = TypeUtils.getField(cls3, fieldName5, declaredFields7);
                            int ordinal6 = fieldAnnotation5.ordinal();
                            int serialzeFeatures7 = SerializerFeature.of(fieldAnnotation5.serialzeFeatures());
                            int parserFeatures7 = Feature.of(fieldAnnotation5.parseFeatures());
                            fieldName4 = fieldAnnotation5.name();
                            serialzeFeatures4 = serialzeFeatures7;
                            parserFeatures4 = parserFeatures7;
                            parserFeatures5 = ordinal6;
                        }
                        if (fieldName4 == null || fieldName4.length() == 0) {
                            if (lookupParameterNames3 == null) {
                                lookupParameterNames3 = ASMUtils.lookupParameterNames(creatorConstructor);
                            }
                            String fieldName6 = lookupParameterNames3[i15];
                            fieldName = fieldName6;
                        } else {
                            fieldName = fieldName4;
                        }
                        if (field4 == null) {
                            if (lookupParameterNames3 == null) {
                                if (kotlin2) {
                                    lookupParameterNames3 = TypeUtils.getKoltinConstructorParameters(clazz);
                                } else {
                                    lookupParameterNames3 = ASMUtils.lookupParameterNames(creatorConstructor);
                                }
                            }
                            if (lookupParameterNames3.length > i15) {
                                String parameterName = lookupParameterNames3[i15];
                                field4 = TypeUtils.getField(cls3, parameterName, declaredFields7);
                            }
                        }
                        FieldInfo fieldInfo3 = new FieldInfo(fieldName, clazz, fieldClass3, fieldType3, field4, parserFeatures5, serialzeFeatures4, parserFeatures4);
                        add(fieldList6, fieldInfo3);
                        i15++;
                        propertyNamingStrategy2 = propertyNamingStrategy2;
                        paramAnnotationArrays4 = paramAnnotationArrays;
                        lookupParameterNames3 = lookupParameterNames3;
                        types4 = types4;
                        mixInType = mixInType;
                    }
                    fieldList5 = fieldList6;
                    propertyNamingStrategy7 = propertyNamingStrategy2;
                }
                creatorConstructor4 = creatorConstructor;
                fieldList = fieldList5;
                builderClass = builderClass5;
                propertyNamingStrategy3 = propertyNamingStrategy7;
                declaredFields = declaredFields7;
                cls = cls3;
                jsonType = jsonType2;
                methods = methods3;
            }
        }
        if (defaultConstructor != null) {
            TypeUtils.setAccessible(defaultConstructor);
        }
        String str8 = "set";
        Class<?> builderClass7 = builderClass;
        if (builderClass7 == null) {
            str = "set";
            declaredFields2 = declaredFields;
            builderClass2 = builderClass7;
            fieldList2 = fieldList;
            c = 0;
        } else {
            String withPrefix5 = null;
            JSONPOJOBuilder builderAnno = (JSONPOJOBuilder) TypeUtils.getAnnotation(builderClass7, JSONPOJOBuilder.class);
            if (builderAnno != null) {
                withPrefix5 = builderAnno.withPrefix();
            }
            if (withPrefix5 != null) {
                withPrefix = withPrefix5;
            } else {
                withPrefix = "with";
            }
            Method[] methods5 = builderClass7.getMethods();
            int length5 = methods5.length;
            int i17 = 0;
            while (i17 < length5) {
                Method method4 = methods5[i17];
                if (Modifier.isStatic(method4.getModifiers())) {
                    i6 = i17;
                    i7 = length5;
                    withPrefix4 = withPrefix;
                    builderClass4 = builderClass7;
                    str7 = str8;
                    declaredFields6 = declaredFields;
                    fieldList4 = fieldList;
                } else if (!method4.getReturnType().equals(builderClass7)) {
                    i6 = i17;
                    i7 = length5;
                    withPrefix4 = withPrefix;
                    builderClass4 = builderClass7;
                    str7 = str8;
                    declaredFields6 = declaredFields;
                    fieldList4 = fieldList;
                } else {
                    JSONField annotation2 = (JSONField) TypeUtils.getAnnotation(method4, JSONField.class);
                    if (annotation2 != null) {
                        annotation = annotation2;
                    } else {
                        annotation = TypeUtils.getSuperMethodAnnotation(cls, method4);
                    }
                    if (annotation == null) {
                        method3 = method4;
                        i6 = i17;
                        i7 = length5;
                        withPrefix2 = withPrefix;
                        builderClass4 = builderClass7;
                        declaredFields6 = declaredFields;
                        str6 = str8;
                        fieldList3 = fieldList;
                        ordinal = 0;
                        serialzeFeatures = 0;
                        parserFeatures = 0;
                    } else if (!annotation.deserialize()) {
                        i6 = i17;
                        i7 = length5;
                        withPrefix4 = withPrefix;
                        builderClass4 = builderClass7;
                        str7 = str8;
                        declaredFields6 = declaredFields;
                        fieldList4 = fieldList;
                    } else {
                        ordinal = annotation.ordinal();
                        serialzeFeatures = SerializerFeature.of(annotation.serialzeFeatures());
                        parserFeatures = Feature.of(annotation.parseFeatures());
                        if (annotation.name().length() == 0) {
                            method3 = method4;
                            i6 = i17;
                            i7 = length5;
                            withPrefix2 = withPrefix;
                            builderClass4 = builderClass7;
                            declaredFields6 = declaredFields;
                            str6 = str8;
                            fieldList3 = fieldList;
                        } else {
                            i6 = i17;
                            i7 = length5;
                            builderClass4 = builderClass7;
                            List<FieldInfo> fieldList11 = fieldList;
                            declaredFields6 = declaredFields;
                            add(fieldList11, new FieldInfo(annotation.name(), method4, null, clazz, type, ordinal, serialzeFeatures, parserFeatures, annotation, null, null, genericInfo));
                            withPrefix4 = withPrefix;
                            str7 = str8;
                            fieldList4 = fieldList11;
                        }
                    }
                    String methodName2 = method3.getName();
                    if (methodName2.startsWith(str6) && methodName2.length() > 3) {
                        withPrefix3 = withPrefix2;
                        properNameBuilder = new StringBuilder(methodName2.substring(3));
                    } else if (withPrefix2.length() == 0) {
                        withPrefix3 = withPrefix2;
                        properNameBuilder = new StringBuilder(methodName2);
                    } else {
                        withPrefix3 = withPrefix2;
                        if (!methodName2.startsWith(withPrefix3)) {
                            withPrefix4 = withPrefix3;
                            str7 = str6;
                            fieldList4 = fieldList3;
                        } else if (methodName2.length() <= withPrefix3.length()) {
                            withPrefix4 = withPrefix3;
                            str7 = str6;
                            fieldList4 = fieldList3;
                        } else {
                            properNameBuilder = new StringBuilder(methodName2.substring(withPrefix3.length()));
                        }
                    }
                    char c0 = properNameBuilder.charAt(0);
                    if (withPrefix3.length() == 0 || Character.isUpperCase(c0)) {
                        properNameBuilder.setCharAt(0, Character.toLowerCase(c0));
                        withPrefix4 = withPrefix3;
                        str7 = str6;
                        fieldList4 = fieldList3;
                        add(fieldList4, new FieldInfo(properNameBuilder.toString(), method3, null, clazz, type, ordinal, serialzeFeatures, parserFeatures, annotation, null, null, genericInfo));
                    } else {
                        withPrefix4 = withPrefix3;
                        str7 = str6;
                        fieldList4 = fieldList3;
                    }
                }
                i17 = i6 + 1;
                cls = clazz;
                fieldList = fieldList4;
                declaredFields = declaredFields6;
                str8 = str7;
                length5 = i7;
                withPrefix = withPrefix4;
                builderClass7 = builderClass4;
            }
            str = str8;
            declaredFields2 = declaredFields;
            fieldList2 = fieldList;
            builderClass2 = builderClass7;
            if (builderClass2 == null) {
                c = 0;
            } else {
                JSONPOJOBuilder builderAnnotation = (JSONPOJOBuilder) TypeUtils.getAnnotation(builderClass2, JSONPOJOBuilder.class);
                String buildMethodName = null;
                if (builderAnnotation != null) {
                    buildMethodName = builderAnnotation.buildMethod();
                }
                c = 0;
                try {
                    buildMethod = builderClass2.getMethod((buildMethodName == null || buildMethodName.length() == 0) ? "build" : buildMethodName, new Class[0]);
                } catch (NoSuchMethodException e2) {
                } catch (SecurityException e3) {
                }
                if (buildMethod == null) {
                    try {
                        buildMethod = builderClass2.getMethod("create", new Class[0]);
                    } catch (NoSuchMethodException e4) {
                    } catch (SecurityException e5) {
                    }
                }
                if (buildMethod == null) {
                    throw new JSONException("buildMethod not found.");
                }
                TypeUtils.setAccessible(buildMethod);
            }
        }
        int length6 = methods.length;
        int i18 = 0;
        while (true) {
            str2 = "get";
            i = 4;
            if (i18 >= length6) {
                break;
            }
            Method method5 = methods[i18];
            int ordinal7 = 0;
            int serialzeFeatures8 = 0;
            int parserFeatures8 = 0;
            String methodName3 = method5.getName();
            if (Modifier.isStatic(method5.getModifiers())) {
                i4 = i18;
                builderClass3 = builderClass2;
                methods2 = methods;
                declaredFields4 = declaredFields2;
                str4 = str;
                propertyNamingStrategy5 = propertyNamingStrategy3;
            } else {
                Class<?> returnType = method5.getReturnType();
                if (!returnType.equals(Void.TYPE) && !returnType.equals(method5.getDeclaringClass())) {
                    i4 = i18;
                    builderClass3 = builderClass2;
                    methods2 = methods;
                    declaredFields4 = declaredFields2;
                    str4 = str;
                    propertyNamingStrategy5 = propertyNamingStrategy3;
                } else if (method5.getDeclaringClass() == Object.class) {
                    i4 = i18;
                    builderClass3 = builderClass2;
                    methods2 = methods;
                    declaredFields4 = declaredFields2;
                    str4 = str;
                    propertyNamingStrategy5 = propertyNamingStrategy3;
                } else {
                    Class<?>[] types5 = method5.getParameterTypes();
                    if (types5.length == 0) {
                        i4 = i18;
                        builderClass3 = builderClass2;
                        methods2 = methods;
                        declaredFields4 = declaredFields2;
                        str4 = str;
                        propertyNamingStrategy5 = propertyNamingStrategy3;
                    } else if (types5.length > 2) {
                        i4 = i18;
                        builderClass3 = builderClass2;
                        methods2 = methods;
                        declaredFields4 = declaredFields2;
                        str4 = str;
                        propertyNamingStrategy5 = propertyNamingStrategy3;
                    } else {
                        JSONField annotation3 = (JSONField) TypeUtils.getAnnotation(method5, JSONField.class);
                        if (annotation3 != null && types5.length == 2 && types5[c] == String.class && types5[1] == Object.class) {
                            i4 = i18;
                            builderClass3 = builderClass2;
                            add(fieldList2, new FieldInfo("", method5, null, clazz, type, 0, 0, 0, annotation3, null, null, genericInfo));
                            methods2 = methods;
                            declaredFields4 = declaredFields2;
                            str4 = str;
                            propertyNamingStrategy5 = propertyNamingStrategy3;
                        } else {
                            i4 = i18;
                            builderClass3 = builderClass2;
                            if (types5.length != 1) {
                                methods2 = methods;
                                declaredFields4 = declaredFields2;
                                str4 = str;
                                propertyNamingStrategy5 = propertyNamingStrategy3;
                            } else {
                                if (annotation3 == null) {
                                    method = method5;
                                    annotation3 = TypeUtils.getSuperMethodAnnotation(clazz, method);
                                } else {
                                    method = method5;
                                }
                                if (annotation3 == null && methodName3.length() < 4) {
                                    methods2 = methods;
                                    declaredFields4 = declaredFields2;
                                    str4 = str;
                                    propertyNamingStrategy5 = propertyNamingStrategy3;
                                } else {
                                    if (annotation3 == null) {
                                        method2 = method;
                                        types = types5;
                                    } else if (!annotation3.deserialize()) {
                                        methods2 = methods;
                                        declaredFields4 = declaredFields2;
                                        str4 = str;
                                        propertyNamingStrategy5 = propertyNamingStrategy3;
                                    } else {
                                        ordinal7 = annotation3.ordinal();
                                        serialzeFeatures8 = SerializerFeature.of(annotation3.serialzeFeatures());
                                        parserFeatures8 = Feature.of(annotation3.parseFeatures());
                                        if (annotation3.name().length() == 0) {
                                            method2 = method;
                                            types = types5;
                                        } else {
                                            add(fieldList2, new FieldInfo(annotation3.name(), method, null, clazz, type, ordinal7, serialzeFeatures8, parserFeatures8, annotation3, null, null, genericInfo));
                                            methods2 = methods;
                                            declaredFields4 = declaredFields2;
                                            str4 = str;
                                            propertyNamingStrategy5 = propertyNamingStrategy3;
                                        }
                                    }
                                    if (annotation3 == null) {
                                        methodName = methodName3;
                                        str5 = str;
                                        if (!methodName.startsWith(str5)) {
                                            str4 = str5;
                                            methods2 = methods;
                                            declaredFields4 = declaredFields2;
                                            propertyNamingStrategy5 = propertyNamingStrategy3;
                                        }
                                    } else {
                                        methodName = methodName3;
                                        str5 = str;
                                    }
                                    if (builderClass3 != null) {
                                        str4 = str5;
                                        methods2 = methods;
                                        declaredFields4 = declaredFields2;
                                        propertyNamingStrategy5 = propertyNamingStrategy3;
                                    } else {
                                        char c3 = methodName.charAt(3);
                                        Field field5 = null;
                                        if (!kotlin2) {
                                            getMethodNameList = null;
                                        } else {
                                            List<String> getMethodNameList2 = new ArrayList<>();
                                            for (int i19 = 0; i19 < methods.length; i19++) {
                                                if (methods[i19].getName().startsWith("get")) {
                                                    getMethodNameList2.add(methods[i19].getName());
                                                }
                                            }
                                            getMethodNameList = getMethodNameList2;
                                        }
                                        if (Character.isUpperCase(c3) || c3 > 512) {
                                            i5 = 1;
                                            cls2 = clazz;
                                            declaredFields5 = declaredFields2;
                                            if (kotlin2) {
                                                String getMethodName = "g" + methodName.substring(i5);
                                                propertyName3 = TypeUtils.getPropertyNameByMethodName(getMethodName);
                                            } else if (TypeUtils.compatibleWithJavaBean) {
                                                propertyName3 = TypeUtils.decapitalize(methodName.substring(3));
                                            } else {
                                                propertyName3 = TypeUtils.getPropertyNameByMethodName(methodName);
                                            }
                                            if (field5 == null) {
                                                field5 = TypeUtils.getField(cls2, propertyName3, declaredFields5);
                                            }
                                            if (field5 != null && types[0] == Boolean.TYPE) {
                                                String isFieldName = "is" + Character.toUpperCase(propertyName3.charAt(0)) + propertyName3.substring(i5);
                                                field = TypeUtils.getField(cls2, isFieldName, declaredFields5);
                                            } else {
                                                field = field5;
                                            }
                                            if (field != null) {
                                                declaredFields4 = declaredFields5;
                                                field2 = field;
                                                str4 = str5;
                                                methods2 = methods;
                                                fieldAnnotation = null;
                                            } else {
                                                fieldAnnotation = (JSONField) TypeUtils.getAnnotation(field, JSONField.class);
                                                if (fieldAnnotation == null) {
                                                    declaredFields4 = declaredFields5;
                                                    field2 = field;
                                                    str4 = str5;
                                                    methods2 = methods;
                                                } else if (!fieldAnnotation.deserialize()) {
                                                    declaredFields4 = declaredFields5;
                                                    str4 = str5;
                                                    methods2 = methods;
                                                    propertyNamingStrategy5 = propertyNamingStrategy3;
                                                } else {
                                                    ordinal7 = fieldAnnotation.ordinal();
                                                    serialzeFeatures8 = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                                                    parserFeatures8 = Feature.of(fieldAnnotation.parseFeatures());
                                                    if (fieldAnnotation.name().length() == 0) {
                                                        declaredFields4 = declaredFields5;
                                                        field2 = field;
                                                        str4 = str5;
                                                        methods2 = methods;
                                                    } else {
                                                        declaredFields4 = declaredFields5;
                                                        methods2 = methods;
                                                        str4 = str5;
                                                        add(fieldList2, new FieldInfo(fieldAnnotation.name(), method2, field, clazz, type, ordinal7, serialzeFeatures8, parserFeatures8, annotation3, fieldAnnotation, null, genericInfo));
                                                        propertyNamingStrategy5 = propertyNamingStrategy3;
                                                    }
                                                }
                                            }
                                            propertyNamingStrategy6 = propertyNamingStrategy3;
                                            if (propertyNamingStrategy6 != null) {
                                                propertyName4 = propertyName3;
                                            } else {
                                                propertyName4 = propertyNamingStrategy6.translate(propertyName3);
                                            }
                                            propertyNamingStrategy5 = propertyNamingStrategy6;
                                            add(fieldList2, new FieldInfo(propertyName4, method2, field2, clazz, type, ordinal7, serialzeFeatures8, parserFeatures8, annotation3, fieldAnnotation, null, genericInfo));
                                        } else {
                                            if (c3 == '_') {
                                                if (kotlin2) {
                                                    i5 = 1;
                                                    String getMethodName2 = "g" + methodName.substring(1);
                                                    if (!getMethodNameList.contains(getMethodName2)) {
                                                        propertyName3 = "is" + methodName.substring(3);
                                                    } else {
                                                        propertyName3 = methodName.substring(3);
                                                    }
                                                    cls2 = clazz;
                                                    declaredFields5 = declaredFields2;
                                                    field5 = TypeUtils.getField(cls2, propertyName3, declaredFields5);
                                                } else {
                                                    i5 = 1;
                                                    cls2 = clazz;
                                                    declaredFields5 = declaredFields2;
                                                    propertyName3 = methodName.substring(4);
                                                    field5 = TypeUtils.getField(cls2, propertyName3, declaredFields5);
                                                    if (field5 == null && (field5 = TypeUtils.getField(cls2, (propertyName3 = methodName.substring(3)), declaredFields5)) == null) {
                                                        propertyName3 = propertyName3;
                                                    }
                                                }
                                            } else {
                                                i5 = 1;
                                                cls2 = clazz;
                                                declaredFields5 = declaredFields2;
                                                if (c3 == 'f') {
                                                    propertyName3 = methodName.substring(3);
                                                } else if (methodName.length() >= 5 && Character.isUpperCase(methodName.charAt(4))) {
                                                    propertyName3 = TypeUtils.decapitalize(methodName.substring(3));
                                                } else {
                                                    propertyName3 = methodName.substring(3);
                                                    field5 = TypeUtils.getField(cls2, propertyName3, declaredFields5);
                                                    if (field5 == null) {
                                                        declaredFields4 = declaredFields5;
                                                        str4 = str5;
                                                        methods2 = methods;
                                                        propertyNamingStrategy5 = propertyNamingStrategy3;
                                                    }
                                                }
                                            }
                                            if (field5 == null) {
                                            }
                                            if (field5 != null) {
                                            }
                                            field = field5;
                                            if (field != null) {
                                            }
                                            propertyNamingStrategy6 = propertyNamingStrategy3;
                                            if (propertyNamingStrategy6 != null) {
                                            }
                                            propertyNamingStrategy5 = propertyNamingStrategy6;
                                            add(fieldList2, new FieldInfo(propertyName4, method2, field2, clazz, type, ordinal7, serialzeFeatures8, parserFeatures8, annotation3, fieldAnnotation, null, genericInfo));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            i18 = i4 + 1;
            builderClass2 = builderClass3;
            declaredFields2 = declaredFields4;
            methods = methods2;
            str = str4;
            propertyNamingStrategy3 = propertyNamingStrategy5;
            c = 0;
        }
        Class<?> builderClass8 = builderClass2;
        Field[] fields2 = clazz.getFields();
        Field[] declaredFields9 = declaredFields2;
        PropertyNamingStrategy propertyNamingStrategy10 = propertyNamingStrategy3;
        computeFields(clazz, type, propertyNamingStrategy10, fieldList2, fields2);
        Method[] methods6 = clazz.getMethods();
        int length7 = methods6.length;
        int i20 = 0;
        while (i20 < length7) {
            Method method6 = methods6[i20];
            String methodName4 = method6.getName();
            if (methodName4.length() < i) {
                fields = fields2;
                i2 = i20;
                i3 = length7;
                methodArr = methods6;
                str3 = str2;
                propertyNamingStrategy4 = propertyNamingStrategy10;
                declaredFields3 = declaredFields9;
            } else if (Modifier.isStatic(method6.getModifiers())) {
                fields = fields2;
                i2 = i20;
                i3 = length7;
                methodArr = methods6;
                str3 = str2;
                propertyNamingStrategy4 = propertyNamingStrategy10;
                declaredFields3 = declaredFields9;
            } else if (builderClass8 != null || !methodName4.startsWith(str2) || !Character.isUpperCase(methodName4.charAt(3))) {
                fields = fields2;
                i2 = i20;
                i3 = length7;
                methodArr = methods6;
                str3 = str2;
                propertyNamingStrategy4 = propertyNamingStrategy10;
                declaredFields3 = declaredFields9;
            } else if (method6.getParameterTypes().length != 0) {
                fields = fields2;
                i2 = i20;
                i3 = length7;
                methodArr = methods6;
                str3 = str2;
                propertyNamingStrategy4 = propertyNamingStrategy10;
                declaredFields3 = declaredFields9;
            } else if (Collection.class.isAssignableFrom(method6.getReturnType()) || Map.class.isAssignableFrom(method6.getReturnType()) || AtomicBoolean.class == method6.getReturnType() || AtomicInteger.class == method6.getReturnType() || AtomicLong.class == method6.getReturnType()) {
                JSONField annotation4 = (JSONField) TypeUtils.getAnnotation(method6, JSONField.class);
                if (annotation4 != null && annotation4.deserialize()) {
                    fields = fields2;
                    i2 = i20;
                    i3 = length7;
                    methodArr = methods6;
                    str3 = str2;
                    propertyNamingStrategy4 = propertyNamingStrategy10;
                    declaredFields3 = declaredFields9;
                } else {
                    if (annotation4 != null && annotation4.name().length() > 0) {
                        propertyName = annotation4.name();
                        fields = fields2;
                        collectionField = null;
                    } else {
                        propertyName = TypeUtils.getPropertyNameByMethodName(methodName4);
                        Field field6 = TypeUtils.getField(clazz, propertyName, declaredFields9);
                        if (field6 == null) {
                            fields = fields2;
                            collectionField = null;
                        } else {
                            JSONField fieldAnnotation6 = (JSONField) TypeUtils.getAnnotation(field6, JSONField.class);
                            if (fieldAnnotation6 != null && !fieldAnnotation6.deserialize()) {
                                fields = fields2;
                                i2 = i20;
                                i3 = length7;
                                methodArr = methods6;
                                str3 = str2;
                                propertyNamingStrategy4 = propertyNamingStrategy10;
                                declaredFields3 = declaredFields9;
                            } else {
                                fields = fields2;
                                collectionField = null;
                                if (Collection.class.isAssignableFrom(method6.getReturnType()) || Map.class.isAssignableFrom(method6.getReturnType())) {
                                    collectionField = field6;
                                }
                            }
                        }
                    }
                    if (propertyNamingStrategy10 == null) {
                        propertyName2 = propertyName;
                    } else {
                        propertyName2 = propertyNamingStrategy10.translate(propertyName);
                    }
                    FieldInfo fieldInfo4 = getField(fieldList2, propertyName2);
                    if (fieldInfo4 != null) {
                        i2 = i20;
                        i3 = length7;
                        methodArr = methods6;
                        str3 = str2;
                        propertyNamingStrategy4 = propertyNamingStrategy10;
                        declaredFields3 = declaredFields9;
                    } else {
                        i2 = i20;
                        i3 = length7;
                        methodArr = methods6;
                        str3 = str2;
                        propertyNamingStrategy4 = propertyNamingStrategy10;
                        declaredFields3 = declaredFields9;
                        add(fieldList2, new FieldInfo(propertyName2, method6, collectionField, clazz, type, 0, 0, 0, annotation4, null, null, genericInfo));
                    }
                }
            } else {
                fields = fields2;
                i2 = i20;
                i3 = length7;
                methodArr = methods6;
                str3 = str2;
                propertyNamingStrategy4 = propertyNamingStrategy10;
                declaredFields3 = declaredFields9;
            }
            i20 = i2 + 1;
            fields2 = fields;
            methods6 = methodArr;
            str2 = str3;
            length7 = i3;
            propertyNamingStrategy10 = propertyNamingStrategy4;
            declaredFields9 = declaredFields3;
            i = 4;
        }
        PropertyNamingStrategy propertyNamingStrategy11 = propertyNamingStrategy10;
        Field[] declaredFields10 = declaredFields9;
        if (fieldList2.size() == 0) {
            if (!TypeUtils.isXmlField(clazz)) {
                fieldBased2 = fieldBased;
            } else {
                fieldBased2 = true;
            }
            if (fieldBased2) {
                for (Class<?> currentClass2 = clazz; currentClass2 != null; currentClass2 = currentClass2.getSuperclass()) {
                    computeFields(clazz, type, propertyNamingStrategy11, fieldList2, declaredFields10);
                }
            }
        }
        return new JavaBeanInfo(clazz, builderClass8, defaultConstructor, creatorConstructor4, factoryMethod2, buildMethod, jsonType, fieldList2);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void computeFields(Class<?> clazz, Type type, PropertyNamingStrategy propertyNamingStrategy, List<FieldInfo> fieldList, Field[] fields) {
        boolean contains;
        int ordinal;
        int serialzeFeatures;
        int parserFeatures;
        String propertyName;
        int i;
        int i2;
        Map<TypeVariable, Type> genericInfo = buildGenericInfo(clazz);
        int length = fields.length;
        int i3 = 0;
        while (i3 < length) {
            Field field = fields[i3];
            int modifiers = field.getModifiers();
            if ((modifiers & 8) != 0) {
                i = i3;
                i2 = length;
            } else if ((modifiers & 16) != 0) {
                Class<?> fieldType = field.getType();
                boolean supportReadOnly = Map.class.isAssignableFrom(fieldType) || Collection.class.isAssignableFrom(fieldType) || AtomicLong.class.equals(fieldType) || AtomicInteger.class.equals(fieldType) || AtomicBoolean.class.equals(fieldType);
                if (supportReadOnly) {
                    Iterator<FieldInfo> it = fieldList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            contains = false;
                            break;
                        }
                        FieldInfo item = it.next();
                        if (item.name.equals(field.getName())) {
                            contains = true;
                            break;
                        }
                    }
                    if (contains) {
                        i = i3;
                        i2 = length;
                    } else {
                        String propertyName2 = field.getName();
                        JSONField fieldAnnotation = (JSONField) TypeUtils.getAnnotation(field, JSONField.class);
                        if (fieldAnnotation == null) {
                            ordinal = 0;
                            serialzeFeatures = 0;
                            parserFeatures = 0;
                        } else if (fieldAnnotation.deserialize()) {
                            int ordinal2 = fieldAnnotation.ordinal();
                            int serialzeFeatures2 = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                            int parserFeatures2 = Feature.of(fieldAnnotation.parseFeatures());
                            if (fieldAnnotation.name().length() == 0) {
                                ordinal = ordinal2;
                                serialzeFeatures = serialzeFeatures2;
                                parserFeatures = parserFeatures2;
                            } else {
                                propertyName2 = fieldAnnotation.name();
                                ordinal = ordinal2;
                                serialzeFeatures = serialzeFeatures2;
                                parserFeatures = parserFeatures2;
                            }
                        } else {
                            i = i3;
                            i2 = length;
                        }
                        if (propertyNamingStrategy == null) {
                            propertyName = propertyName2;
                        } else {
                            propertyName = propertyNamingStrategy.translate(propertyName2);
                        }
                        i = i3;
                        i2 = length;
                        add(fieldList, new FieldInfo(propertyName, null, field, clazz, type, ordinal, serialzeFeatures, parserFeatures, null, fieldAnnotation, null, genericInfo));
                    }
                } else {
                    i = i3;
                    i2 = length;
                }
            }
            i3 = i + 1;
            length = i2;
        }
    }

    static Constructor<?> getDefaultConstructor(Class<?> clazz, Constructor<?>[] constructors) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }
        Constructor<?> defaultConstructor = null;
        int length = constructors.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Constructor<?> constructor = constructors[i];
            if (constructor.getParameterTypes().length != 0) {
                i++;
            } else {
                defaultConstructor = constructor;
                break;
            }
        }
        if (defaultConstructor == null && clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
            for (Constructor<?> constructor2 : constructors) {
                Class<?>[] types = constructor2.getParameterTypes();
                if (types.length == 1 && types[0].equals(clazz.getDeclaringClass())) {
                    return constructor2;
                }
            }
            return defaultConstructor;
        }
        return defaultConstructor;
    }

    public static Constructor<?> getCreatorConstructor(Constructor[] constructors) {
        Constructor constructor = null;
        for (Constructor constructor2 : constructors) {
            JSONCreator annotation = (JSONCreator) constructor2.getAnnotation(JSONCreator.class);
            if (annotation != null) {
                if (constructor != null) {
                    throw new JSONException("multi-JSONCreator");
                }
                constructor = constructor2;
            }
        }
        if (constructor != null) {
            return constructor;
        }
        for (Constructor constructor3 : constructors) {
            Annotation[][] paramAnnotationArrays = TypeUtils.getParameterAnnotations(constructor3);
            if (paramAnnotationArrays.length != 0) {
                boolean match = true;
                int length = paramAnnotationArrays.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Annotation[] paramAnnotationArray = paramAnnotationArrays[i];
                    boolean paramMatch = false;
                    int length2 = paramAnnotationArray.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length2) {
                            break;
                        }
                        Annotation paramAnnotation = paramAnnotationArray[i2];
                        if (!(paramAnnotation instanceof JSONField)) {
                            i2++;
                        } else {
                            paramMatch = true;
                            break;
                        }
                    }
                    if (paramMatch) {
                        i++;
                    } else {
                        match = false;
                        break;
                    }
                }
                if (!match) {
                    continue;
                } else {
                    if (constructor != null) {
                        throw new JSONException("multi-JSONCreator");
                    }
                    constructor = constructor3;
                }
            }
        }
        return constructor;
    }

    private static Method getFactoryMethod(Class<?> clazz, Method[] methods, boolean jacksonCompatible) {
        Method factoryMethod = null;
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers()) && clazz.isAssignableFrom(method.getReturnType())) {
                JSONCreator annotation = (JSONCreator) TypeUtils.getAnnotation(method, JSONCreator.class);
                if (annotation == null) {
                    continue;
                } else {
                    if (factoryMethod != null) {
                        throw new JSONException("multi-JSONCreator");
                    }
                    factoryMethod = method;
                }
            }
        }
        if (factoryMethod == null && jacksonCompatible) {
            for (Method method2 : methods) {
                if (TypeUtils.isJacksonCreator(method2)) {
                    return method2;
                }
            }
            return factoryMethod;
        }
        return factoryMethod;
    }

    public static Class<?> getBuilderClass(JSONType type) {
        return getBuilderClass(null, type);
    }

    public static Class<?> getBuilderClass(Class<?> clazz, JSONType type) {
        Class<?> builderClass;
        if (clazz != null && clazz.getName().equals("org.springframework.security.web.savedrequest.DefaultSavedRequest")) {
            return TypeUtils.loadClass("org.springframework.security.web.savedrequest.DefaultSavedRequest$Builder");
        }
        if (type == null || (builderClass = type.builder()) == Void.class) {
            return null;
        }
        return builderClass;
    }
}