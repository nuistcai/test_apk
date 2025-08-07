package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class ASMSerializerFactory implements Opcodes {
    protected final ASMClassLoader classLoader = new ASMClassLoader();
    private final AtomicLong seed = new AtomicLong();
    static final String JSONSerializer = ASMUtils.type(JSONSerializer.class);
    static final String ObjectSerializer = ASMUtils.type(ObjectSerializer.class);
    static final String ObjectSerializer_desc = "L" + ObjectSerializer + ";";
    static final String SerializeWriter = ASMUtils.type(SerializeWriter.class);
    static final String SerializeWriter_desc = "L" + SerializeWriter + ";";
    static final String JavaBeanSerializer = ASMUtils.type(JavaBeanSerializer.class);
    static final String JavaBeanSerializer_desc = "L" + ASMUtils.type(JavaBeanSerializer.class) + ";";
    static final String SerialContext_desc = ASMUtils.desc((Class<?>) SerialContext.class);
    static final String SerializeFilterable_desc = ASMUtils.desc((Class<?>) SerializeFilterable.class);

    static class Context {
        static final int features = 5;
        static final int obj = 2;
        static final int paramFieldName = 3;
        static final int paramFieldType = 4;
        static final int serializer = 1;
        private final SerializeBeanInfo beanInfo;
        private final String className;
        private final FieldInfo[] getters;
        private final boolean nonContext;
        private final boolean writeDirect;
        static int fieldName = 6;
        static int original = 7;
        static int processValue = 8;
        private Map<String, Integer> variants = new HashMap();
        private int variantIndex = 9;

        public Context(FieldInfo[] getters, SerializeBeanInfo beanInfo, String className, boolean writeDirect, boolean nonContext) {
            this.getters = getters;
            this.className = className;
            this.beanInfo = beanInfo;
            this.writeDirect = writeDirect;
            this.nonContext = nonContext || beanInfo.beanType.isEnum();
        }

        public int var(String name) {
            Integer i = this.variants.get(name);
            if (i == null) {
                Map<String, Integer> map = this.variants;
                int i2 = this.variantIndex;
                this.variantIndex = i2 + 1;
                map.put(name, Integer.valueOf(i2));
            }
            Integer i3 = this.variants.get(name);
            return i3.intValue();
        }

        public int var(String name, int increment) {
            Integer i = this.variants.get(name);
            if (i == null) {
                this.variants.put(name, Integer.valueOf(this.variantIndex));
                this.variantIndex += increment;
            }
            Integer i2 = this.variants.get(name);
            return i2.intValue();
        }

        public int getFieldOrinal(String name) {
            int size = this.getters.length;
            for (int i = 0; i < size; i++) {
                FieldInfo item = this.getters[i];
                if (item.name.equals(name)) {
                    int fieldIndex = i;
                    return fieldIndex;
                }
            }
            return -1;
        }
    }

    public JavaBeanSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) throws Exception {
        String classNameFull;
        String classNameType;
        boolean DisableCircularReferenceDetect;
        FieldInfo[] unsortedGetters;
        FieldInfo[] unsortedGetters2;
        int i;
        String str;
        boolean nonContext;
        String methodName;
        boolean writeDirect;
        String classNameType2;
        JSONType jsonType;
        boolean DisableCircularReferenceDetect2;
        ClassWriter cw;
        int i2;
        String classNameFull2;
        Class<?> clazz = beanInfo.beanType;
        if (clazz.isPrimitive()) {
            throw new JSONException("unsupportd class " + clazz.getName());
        }
        JSONType jsonType2 = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
        FieldInfo[] unsortedGetters3 = beanInfo.fields;
        for (FieldInfo fieldInfo : unsortedGetters3) {
            if (fieldInfo.field == null && fieldInfo.method != null && fieldInfo.method.getDeclaringClass().isInterface()) {
                return new JavaBeanSerializer(beanInfo);
            }
        }
        FieldInfo[] getters = beanInfo.sortedFields;
        boolean nativeSorted = beanInfo.sortedFields == beanInfo.fields;
        if (getters.length > 256) {
            return new JavaBeanSerializer(beanInfo);
        }
        for (FieldInfo getter : getters) {
            if (!ASMUtils.checkName(getter.getMember().getName())) {
                return new JavaBeanSerializer(beanInfo);
            }
        }
        String className = "ASMSerializer_" + this.seed.incrementAndGet() + "_" + clazz.getSimpleName();
        Package pkg = ASMSerializerFactory.class.getPackage();
        if (pkg != null) {
            String packageName = pkg.getName();
            String classNameType3 = packageName.replace('.', '/') + "/" + className;
            classNameFull = packageName + "." + className;
            classNameType = classNameType3;
        } else {
            classNameFull = className;
            classNameType = className;
        }
        ClassWriter cw2 = new ClassWriter();
        cw2.visit(49, 33, classNameType, JavaBeanSerializer, new String[]{ObjectSerializer});
        int length = getters.length;
        int i3 = 0;
        while (i3 < length) {
            FieldInfo fieldInfo2 = getters[i3];
            if (fieldInfo2.fieldClass.isPrimitive()) {
                i2 = length;
                classNameFull2 = classNameFull;
            } else if (fieldInfo2.fieldClass == String.class) {
                i2 = length;
                classNameFull2 = classNameFull;
            } else {
                i2 = length;
                classNameFull2 = classNameFull;
                new FieldWriter(cw2, 1, fieldInfo2.name + "_asm_fieldType", "Ljava/lang/reflect/Type;").visitEnd();
                if (List.class.isAssignableFrom(fieldInfo2.fieldClass)) {
                    new FieldWriter(cw2, 1, fieldInfo2.name + "_asm_list_item_ser_", ObjectSerializer_desc).visitEnd();
                }
                new FieldWriter(cw2, 1, fieldInfo2.name + "_asm_ser_", ObjectSerializer_desc).visitEnd();
            }
            i3++;
            length = i2;
            classNameFull = classNameFull2;
        }
        String classNameFull3 = classNameFull;
        MethodVisitor mw = new MethodWriter(cw2, 1, "<init>", "(" + ASMUtils.desc((Class<?>) SerializeBeanInfo.class) + ")V", null, null);
        int i4 = 25;
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "<init>", "(" + ASMUtils.desc((Class<?>) SerializeBeanInfo.class) + ")V");
        int i5 = 0;
        while (i5 < getters.length) {
            FieldInfo fieldInfo3 = getters[i5];
            if (fieldInfo3.fieldClass.isPrimitive()) {
                cw = cw2;
            } else if (fieldInfo3.fieldClass == String.class) {
                cw = cw2;
            } else {
                mw.visitVarInsn(i4, 0);
                if (fieldInfo3.method != null) {
                    mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo3.declaringClass)));
                    mw.visitLdcInsn(fieldInfo3.method.getName());
                    cw = cw2;
                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(ASMUtils.class), "getMethodType", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Type;");
                } else {
                    cw = cw2;
                    mw.visitVarInsn(25, 0);
                    mw.visitLdcInsn(Integer.valueOf(i5));
                    mw.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "getFieldType", "(I)Ljava/lang/reflect/Type;");
                }
                mw.visitFieldInsn(Opcodes.PUTFIELD, classNameType, fieldInfo3.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            }
            i5++;
            cw2 = cw;
            i4 = 25;
        }
        ClassWriter cw3 = cw2;
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(4, 4);
        mw.visitEnd();
        if (jsonType2 != null) {
            for (SerializerFeature featrues : jsonType2.serialzeFeatures()) {
                if (featrues == SerializerFeature.DisableCircularReferenceDetect) {
                    DisableCircularReferenceDetect = true;
                    break;
                }
            }
            DisableCircularReferenceDetect = false;
        } else {
            DisableCircularReferenceDetect = false;
        }
        MethodVisitor mw2 = mw;
        int i6 = 0;
        while (true) {
            String className2 = className;
            unsortedGetters = unsortedGetters3;
            if (i6 >= 3) {
                break;
            }
            boolean nonContext2 = DisableCircularReferenceDetect;
            if (i6 == 0) {
                nonContext = nonContext2;
                writeDirect = true;
                methodName = "write";
            } else if (i6 == 1) {
                nonContext = nonContext2;
                methodName = "writeNormal";
                writeDirect = false;
            } else {
                nonContext = true;
                methodName = "writeDirectNonContext";
                writeDirect = true;
            }
            ClassWriter cw4 = cw3;
            String classNameType4 = classNameType;
            String classNameFull4 = classNameFull3;
            int i7 = i6;
            Context context = new Context(getters, beanInfo, classNameType, writeDirect, nonContext);
            MethodVisitor mw3 = new MethodWriter(cw3, 1, methodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            Label endIf_ = new Label();
            mw3.visitVarInsn(25, 2);
            mw3.visitJumpInsn(Opcodes.IFNONNULL, endIf_);
            mw3.visitVarInsn(25, 1);
            mw3.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeNull", "()V");
            mw3.visitInsn(Opcodes.RETURN);
            mw3.visitLabel(endIf_);
            mw3.visitVarInsn(25, 1);
            mw3.visitFieldInsn(Opcodes.GETFIELD, JSONSerializer, "out", SerializeWriter_desc);
            mw3.visitVarInsn(58, context.var("out"));
            if (nativeSorted || context.writeDirect) {
                classNameType2 = classNameType4;
            } else if (jsonType2 == null || jsonType2.alphabetic()) {
                Label _else = new Label();
                mw3.visitVarInsn(25, context.var("out"));
                mw3.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isSortField", "()Z");
                mw3.visitJumpInsn(Opcodes.IFNE, _else);
                mw3.visitVarInsn(25, 0);
                mw3.visitVarInsn(25, 1);
                mw3.visitVarInsn(25, 2);
                mw3.visitVarInsn(25, 3);
                mw3.visitVarInsn(25, 4);
                mw3.visitVarInsn(21, 5);
                classNameType2 = classNameType4;
                mw3.visitMethodInsn(Opcodes.INVOKEVIRTUAL, classNameType2, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                mw3.visitInsn(Opcodes.RETURN);
                mw3.visitLabel(_else);
            } else {
                classNameType2 = classNameType4;
            }
            if (!context.writeDirect || nonContext) {
                jsonType = jsonType2;
                DisableCircularReferenceDetect2 = DisableCircularReferenceDetect;
            } else {
                Label _direct = new Label();
                Label _directElse = new Label();
                mw3.visitVarInsn(25, 0);
                mw3.visitVarInsn(25, 1);
                jsonType = jsonType2;
                DisableCircularReferenceDetect2 = DisableCircularReferenceDetect;
                mw3.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeDirect", "(L" + JSONSerializer + ";)Z");
                mw3.visitJumpInsn(Opcodes.IFNE, _directElse);
                mw3.visitVarInsn(25, 0);
                mw3.visitVarInsn(25, 1);
                mw3.visitVarInsn(25, 2);
                mw3.visitVarInsn(25, 3);
                mw3.visitVarInsn(25, 4);
                mw3.visitVarInsn(21, 5);
                mw3.visitMethodInsn(Opcodes.INVOKEVIRTUAL, classNameType2, "writeNormal", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                mw3.visitInsn(Opcodes.RETURN);
                mw3.visitLabel(_directElse);
                mw3.visitVarInsn(25, context.var("out"));
                mw3.visitLdcInsn(Integer.valueOf(SerializerFeature.DisableCircularReferenceDetect.mask));
                mw3.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                mw3.visitJumpInsn(Opcodes.IFEQ, _direct);
                mw3.visitVarInsn(25, 0);
                mw3.visitVarInsn(25, 1);
                mw3.visitVarInsn(25, 2);
                mw3.visitVarInsn(25, 3);
                mw3.visitVarInsn(25, 4);
                mw3.visitVarInsn(21, 5);
                mw3.visitMethodInsn(Opcodes.INVOKEVIRTUAL, classNameType2, "writeDirectNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                mw3.visitInsn(Opcodes.RETURN);
                mw3.visitLabel(_direct);
            }
            mw3.visitVarInsn(25, 2);
            mw3.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(clazz));
            mw3.visitVarInsn(58, context.var("entity"));
            generateWriteMethod(clazz, mw3, getters, context);
            mw3.visitInsn(Opcodes.RETURN);
            mw3.visitMaxs(7, context.variantIndex + 2);
            mw3.visitEnd();
            int i8 = i7 + 1;
            mw2 = mw3;
            classNameType = classNameType2;
            className = className2;
            unsortedGetters3 = unsortedGetters;
            jsonType2 = jsonType;
            cw3 = cw4;
            DisableCircularReferenceDetect = DisableCircularReferenceDetect2;
            classNameFull3 = classNameFull4;
            i6 = i8;
        }
        String classNameType5 = classNameType;
        boolean DisableCircularReferenceDetect3 = DisableCircularReferenceDetect;
        ClassWriter cw5 = cw3;
        String classNameFull5 = classNameFull3;
        if (nativeSorted) {
            unsortedGetters2 = unsortedGetters;
            i = Opcodes.GETFIELD;
        } else {
            i = Opcodes.GETFIELD;
            Context context2 = new Context(getters, beanInfo, classNameType5, false, DisableCircularReferenceDetect3);
            MethodVisitor mw4 = new MethodWriter(cw5, 1, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            mw4.visitVarInsn(25, 1);
            mw4.visitFieldInsn(Opcodes.GETFIELD, JSONSerializer, "out", SerializeWriter_desc);
            mw4.visitVarInsn(58, context2.var("out"));
            mw4.visitVarInsn(25, 2);
            mw4.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(clazz));
            mw4.visitVarInsn(58, context2.var("entity"));
            unsortedGetters2 = unsortedGetters;
            generateWriteMethod(clazz, mw4, unsortedGetters2, context2);
            mw4.visitInsn(Opcodes.RETURN);
            mw4.visitMaxs(7, context2.variantIndex + 2);
            mw4.visitEnd();
        }
        int i9 = 0;
        while (i9 < 3) {
            boolean nonContext3 = DisableCircularReferenceDetect3;
            boolean writeDirect2 = false;
            if (i9 == 0) {
                str = "writeAsArray";
                writeDirect2 = true;
            } else if (i9 == 1) {
                str = "writeAsArrayNormal";
            } else {
                writeDirect2 = true;
                nonContext3 = true;
                str = "writeAsArrayNonContext";
            }
            String methodName2 = str;
            Context context3 = new Context(getters, beanInfo, classNameType5, writeDirect2, nonContext3);
            MethodVisitor mw5 = new MethodWriter(cw5, 1, methodName2, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            mw5.visitVarInsn(25, 1);
            mw5.visitFieldInsn(i, JSONSerializer, "out", SerializeWriter_desc);
            mw5.visitVarInsn(58, context3.var("out"));
            mw5.visitVarInsn(25, 2);
            mw5.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(clazz));
            mw5.visitVarInsn(58, context3.var("entity"));
            generateWriteAsArray(clazz, mw5, getters, context3);
            mw5.visitInsn(Opcodes.RETURN);
            mw5.visitMaxs(7, context3.variantIndex + 2);
            mw5.visitEnd();
            i9++;
            unsortedGetters2 = unsortedGetters2;
        }
        byte[] code = cw5.toByteArray();
        Class<?> serializerClass = this.classLoader.defineClassPublic(classNameFull5, code, 0, code.length);
        Constructor<?> constructor = serializerClass.getConstructor(SerializeBeanInfo.class);
        Object instance = constructor.newInstance(beanInfo);
        return (JavaBeanSerializer) instance;
    }

    private void generateWriteAsArray(Class<?> clazz, MethodVisitor mw, FieldInfo[] getters, Context context) throws Exception {
        Label nonPropertyFilters_;
        String str;
        String str2;
        int i;
        int size;
        String str3;
        ASMSerializerFactory aSMSerializerFactory;
        Label notNullEnd_;
        String str4;
        java.lang.reflect.Type elementType;
        Class<?> elementClass;
        char seperator;
        Label for_;
        Label forItemNullEnd_;
        Label forItemClassIfElse_;
        String str5;
        String str6;
        int i2;
        ASMSerializerFactory aSMSerializerFactory2 = this;
        FieldInfo[] fieldInfoArr = getters;
        Label nonPropertyFilters_2 = new Label();
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 0);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "hasPropertyFilters", "(" + SerializeFilterable_desc + ")Z");
        mw.visitJumpInsn(Opcodes.IFNE, nonPropertyFilters_2);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, 3);
        mw.visitVarInsn(25, 4);
        mw.visitVarInsn(21, 5);
        String str7 = ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V";
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "writeNoneASM", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        mw.visitInsn(Opcodes.RETURN);
        mw.visitLabel(nonPropertyFilters_2);
        String str8 = "out";
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(16, 91);
        String str9 = "(I)V";
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        int size2 = fieldInfoArr.length;
        if (size2 == 0) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, 93);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            return;
        }
        int i3 = 0;
        while (i3 < size2) {
            char seperator2 = i3 == size2 + (-1) ? ']' : ',';
            FieldInfo fieldInfo = fieldInfoArr[i3];
            Class<?> fieldClass = fieldInfo.fieldClass;
            mw.visitLdcInsn(fieldInfo.name);
            mw.visitVarInsn(58, Context.fieldName);
            if (fieldClass == Byte.TYPE || fieldClass == Short.TYPE || fieldClass == Integer.TYPE) {
                nonPropertyFilters_ = nonPropertyFilters_2;
                str = str9;
                str2 = str8;
                i = i3;
                size = size2;
                str3 = str7;
                mw.visitVarInsn(25, context.var(str2));
                mw.visitInsn(89);
                aSMSerializerFactory = this;
                aSMSerializerFactory._get(mw, context, fieldInfo);
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeInt", str);
                mw.visitVarInsn(16, seperator2);
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", str);
                str9 = str;
                aSMSerializerFactory2 = aSMSerializerFactory;
                str8 = str2;
                i3 = i + 1;
                str7 = str3;
                nonPropertyFilters_2 = nonPropertyFilters_;
                size2 = size;
                fieldInfoArr = getters;
            } else {
                if (fieldClass == Long.TYPE) {
                    mw.visitVarInsn(25, context.var(str8));
                    mw.visitInsn(89);
                    aSMSerializerFactory2._get(mw, context, fieldInfo);
                    nonPropertyFilters_ = nonPropertyFilters_2;
                    size = size2;
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeLong", "(J)V");
                    mw.visitVarInsn(16, seperator2);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", str9);
                    aSMSerializerFactory = aSMSerializerFactory2;
                    str = str9;
                    str2 = str8;
                    i = i3;
                    str3 = str7;
                } else {
                    nonPropertyFilters_ = nonPropertyFilters_2;
                    size = size2;
                    if (fieldClass == Float.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitInsn(89);
                        aSMSerializerFactory2._get(mw, context, fieldInfo);
                        mw.visitInsn(4);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFloat", "(FZ)V");
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", str9);
                        aSMSerializerFactory = aSMSerializerFactory2;
                        str = str9;
                        str2 = str8;
                        i = i3;
                        str3 = str7;
                    } else if (fieldClass == Double.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitInsn(89);
                        aSMSerializerFactory2._get(mw, context, fieldInfo);
                        mw.visitInsn(4);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeDouble", "(DZ)V");
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", str9);
                        aSMSerializerFactory = aSMSerializerFactory2;
                        str = str9;
                        str2 = str8;
                        i = i3;
                        str3 = str7;
                    } else if (fieldClass == Boolean.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitInsn(89);
                        aSMSerializerFactory2._get(mw, context, fieldInfo);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(Z)V");
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", str9);
                        aSMSerializerFactory = aSMSerializerFactory2;
                        str = str9;
                        str2 = str8;
                        i = i3;
                        str3 = str7;
                    } else if (fieldClass == Character.TYPE) {
                        mw.visitVarInsn(25, context.var(str8));
                        aSMSerializerFactory2._get(mw, context, fieldInfo);
                        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "toString", "(C)Ljava/lang/String;");
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
                        aSMSerializerFactory = aSMSerializerFactory2;
                        str = str9;
                        str2 = str8;
                        i = i3;
                        str3 = str7;
                    } else if (fieldClass == String.class) {
                        mw.visitVarInsn(25, context.var(str8));
                        aSMSerializerFactory2._get(mw, context, fieldInfo);
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
                        aSMSerializerFactory = aSMSerializerFactory2;
                        str = str9;
                        str2 = str8;
                        i = i3;
                        str3 = str7;
                    } else if (fieldClass.isEnum()) {
                        mw.visitVarInsn(25, context.var(str8));
                        mw.visitInsn(89);
                        aSMSerializerFactory2._get(mw, context, fieldInfo);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeEnum", "(Ljava/lang/Enum;)V");
                        mw.visitVarInsn(16, seperator2);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", str9);
                        aSMSerializerFactory = aSMSerializerFactory2;
                        str = str9;
                        str2 = str8;
                        i = i3;
                        str3 = str7;
                    } else if (List.class.isAssignableFrom(fieldClass)) {
                        java.lang.reflect.Type fieldType = fieldInfo.fieldType;
                        if (fieldType instanceof Class) {
                            elementType = Object.class;
                        } else {
                            elementType = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
                        }
                        if (!(elementType instanceof Class) || (elementClass = (Class) elementType) == Object.class) {
                            elementClass = null;
                        }
                        aSMSerializerFactory2._get(mw, context, fieldInfo);
                        i = i3;
                        mw.visitTypeInsn(Opcodes.CHECKCAST, "java/util/List");
                        mw.visitVarInsn(58, context.var("list"));
                        if (elementClass == String.class && context.writeDirect) {
                            mw.visitVarInsn(25, context.var(str8));
                            mw.visitVarInsn(25, context.var("list"));
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(Ljava/util/List;)V");
                            seperator = seperator2;
                            str5 = str8;
                            str3 = str7;
                            i2 = Opcodes.INVOKEVIRTUAL;
                            str6 = str9;
                        } else {
                            Label nullEnd_ = new Label();
                            Label nullElse_ = new Label();
                            seperator = seperator2;
                            mw.visitVarInsn(25, context.var("list"));
                            mw.visitJumpInsn(Opcodes.IFNONNULL, nullElse_);
                            mw.visitVarInsn(25, context.var(str8));
                            java.lang.reflect.Type elementType2 = elementType;
                            String str10 = str7;
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
                            mw.visitJumpInsn(Opcodes.GOTO, nullEnd_);
                            mw.visitLabel(nullElse_);
                            mw.visitVarInsn(25, context.var("list"));
                            mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I");
                            mw.visitVarInsn(54, context.var("size"));
                            mw.visitVarInsn(25, context.var(str8));
                            mw.visitVarInsn(16, 91);
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", str9);
                            Label for_2 = new Label();
                            Label forFirst_ = new Label();
                            Label forEnd_ = new Label();
                            mw.visitInsn(3);
                            mw.visitVarInsn(54, context.var("i"));
                            mw.visitLabel(for_2);
                            mw.visitVarInsn(21, context.var("i"));
                            mw.visitVarInsn(21, context.var("size"));
                            mw.visitJumpInsn(Opcodes.IF_ICMPGE, forEnd_);
                            mw.visitVarInsn(21, context.var("i"));
                            mw.visitJumpInsn(Opcodes.IFEQ, forFirst_);
                            mw.visitVarInsn(25, context.var(str8));
                            mw.visitVarInsn(16, 44);
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", str9);
                            mw.visitLabel(forFirst_);
                            mw.visitVarInsn(25, context.var("list"));
                            mw.visitVarInsn(21, context.var("i"));
                            mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
                            mw.visitVarInsn(58, context.var("list_item"));
                            Label forItemNullEnd_2 = new Label();
                            Label forItemNullElse_ = new Label();
                            String str11 = str9;
                            mw.visitVarInsn(25, context.var("list_item"));
                            mw.visitJumpInsn(Opcodes.IFNONNULL, forItemNullElse_);
                            mw.visitVarInsn(25, context.var(str8));
                            String str12 = str8;
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
                            mw.visitJumpInsn(Opcodes.GOTO, forItemNullEnd_2);
                            mw.visitLabel(forItemNullElse_);
                            Label forItemClassIfEnd_ = new Label();
                            Label forItemClassIfElse_2 = new Label();
                            if (elementClass == null || !Modifier.isPublic(elementClass.getModifiers())) {
                                for_ = for_2;
                                forItemNullEnd_ = forItemNullEnd_2;
                                str3 = str10;
                                forItemClassIfElse_ = forItemClassIfElse_2;
                            } else {
                                mw.visitVarInsn(25, context.var("list_item"));
                                for_ = for_2;
                                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                                mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                                mw.visitJumpInsn(Opcodes.IF_ACMPNE, forItemClassIfElse_2);
                                aSMSerializerFactory2._getListFieldItemSer(context, mw, fieldInfo, elementClass);
                                mw.visitVarInsn(58, context.var("list_item_desc"));
                                Label instanceOfElse_ = new Label();
                                Label instanceOfEnd_ = new Label();
                                if (!context.writeDirect) {
                                    forItemNullEnd_ = forItemNullEnd_2;
                                    str3 = str10;
                                    forItemClassIfElse_ = forItemClassIfElse_2;
                                } else {
                                    mw.visitVarInsn(25, context.var("list_item_desc"));
                                    mw.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
                                    mw.visitJumpInsn(Opcodes.IFEQ, instanceOfElse_);
                                    mw.visitVarInsn(25, context.var("list_item_desc"));
                                    mw.visitTypeInsn(Opcodes.CHECKCAST, JavaBeanSerializer);
                                    mw.visitVarInsn(25, 1);
                                    mw.visitVarInsn(25, context.var("list_item"));
                                    if (context.nonContext) {
                                        mw.visitInsn(1);
                                        forItemNullEnd_ = forItemNullEnd_2;
                                    } else {
                                        mw.visitVarInsn(21, context.var("i"));
                                        forItemNullEnd_ = forItemNullEnd_2;
                                        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                    }
                                    mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                                    mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                    str3 = str10;
                                    forItemClassIfElse_ = forItemClassIfElse_2;
                                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + str3);
                                    mw.visitJumpInsn(Opcodes.GOTO, instanceOfEnd_);
                                    mw.visitLabel(instanceOfElse_);
                                }
                                mw.visitVarInsn(25, context.var("list_item_desc"));
                                mw.visitVarInsn(25, 1);
                                mw.visitVarInsn(25, context.var("list_item"));
                                if (context.nonContext) {
                                    mw.visitInsn(1);
                                } else {
                                    mw.visitVarInsn(21, context.var("i"));
                                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                }
                                mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + str3);
                                mw.visitLabel(instanceOfEnd_);
                                mw.visitJumpInsn(Opcodes.GOTO, forItemClassIfEnd_);
                            }
                            mw.visitLabel(forItemClassIfElse_);
                            mw.visitVarInsn(25, 1);
                            mw.visitVarInsn(25, context.var("list_item"));
                            if (context.nonContext) {
                                mw.visitInsn(1);
                            } else {
                                mw.visitVarInsn(21, context.var("i"));
                                mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                            }
                            if (elementClass == null || !Modifier.isPublic(elementClass.getModifiers())) {
                                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                            } else {
                                mw.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) elementType2)));
                                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                            }
                            mw.visitLabel(forItemClassIfEnd_);
                            mw.visitLabel(forItemNullEnd_);
                            mw.visitIincInsn(context.var("i"), 1);
                            mw.visitJumpInsn(Opcodes.GOTO, for_);
                            mw.visitLabel(forEnd_);
                            str5 = str12;
                            mw.visitVarInsn(25, context.var(str5));
                            mw.visitVarInsn(16, 93);
                            String str13 = SerializeWriter;
                            str6 = str11;
                            i2 = Opcodes.INVOKEVIRTUAL;
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str13, "write", str6);
                            mw.visitLabel(nullEnd_);
                        }
                        mw.visitVarInsn(25, context.var(str5));
                        mw.visitVarInsn(16, seperator);
                        mw.visitMethodInsn(i2, SerializeWriter, "write", str6);
                        aSMSerializerFactory = this;
                        str2 = str5;
                        str = str6;
                    } else {
                        char seperator3 = seperator2;
                        i = i3;
                        str3 = str7;
                        String str14 = str9;
                        String str15 = str8;
                        Label notNullEnd_2 = new Label();
                        Label notNullElse_ = new Label();
                        _get(mw, context, fieldInfo);
                        mw.visitInsn(89);
                        mw.visitVarInsn(58, context.var("field_" + fieldInfo.fieldClass.getName()));
                        mw.visitJumpInsn(Opcodes.IFNONNULL, notNullElse_);
                        mw.visitVarInsn(25, context.var(str15));
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
                        mw.visitJumpInsn(Opcodes.GOTO, notNullEnd_2);
                        mw.visitLabel(notNullElse_);
                        Label classIfEnd_ = new Label();
                        Label classIfElse_ = new Label();
                        mw.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                        mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldClass)));
                        mw.visitJumpInsn(Opcodes.IF_ACMPNE, classIfElse_);
                        _getFieldSer(context, mw, fieldInfo);
                        mw.visitVarInsn(58, context.var("fied_ser"));
                        Label instanceOfElse_2 = new Label();
                        Label instanceOfEnd_2 = new Label();
                        if (!context.writeDirect || !Modifier.isPublic(fieldClass.getModifiers())) {
                            notNullEnd_ = notNullEnd_2;
                            str4 = "writeWithFieldName";
                        } else {
                            mw.visitVarInsn(25, context.var("fied_ser"));
                            mw.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
                            mw.visitJumpInsn(Opcodes.IFEQ, instanceOfElse_2);
                            mw.visitVarInsn(25, context.var("fied_ser"));
                            mw.visitTypeInsn(Opcodes.CHECKCAST, JavaBeanSerializer);
                            mw.visitVarInsn(25, 1);
                            mw.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                            mw.visitVarInsn(25, Context.fieldName);
                            mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldClass)));
                            mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                            notNullEnd_ = notNullEnd_2;
                            str4 = "writeWithFieldName";
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + str3);
                            mw.visitJumpInsn(Opcodes.GOTO, instanceOfEnd_2);
                            mw.visitLabel(instanceOfElse_2);
                        }
                        mw.visitVarInsn(25, context.var("fied_ser"));
                        mw.visitVarInsn(25, 1);
                        mw.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                        mw.visitVarInsn(25, Context.fieldName);
                        mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldClass)));
                        mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                        mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + str3);
                        mw.visitLabel(instanceOfEnd_2);
                        mw.visitJumpInsn(Opcodes.GOTO, classIfEnd_);
                        mw.visitLabel(classIfElse_);
                        String format = fieldInfo.getFormat();
                        mw.visitVarInsn(25, 1);
                        mw.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                        if (format != null) {
                            mw.visitLdcInsn(format);
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
                        } else {
                            mw.visitVarInsn(25, Context.fieldName);
                            if (!(fieldInfo.fieldType instanceof Class) || !((Class) fieldInfo.fieldType).isPrimitive()) {
                                String str16 = str4;
                                mw.visitVarInsn(25, 0);
                                mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, str16, "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                            } else {
                                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, str4, "(Ljava/lang/Object;Ljava/lang/Object;)V");
                            }
                        }
                        mw.visitLabel(classIfEnd_);
                        mw.visitLabel(notNullEnd_);
                        str2 = str15;
                        mw.visitVarInsn(25, context.var(str2));
                        mw.visitVarInsn(16, seperator3);
                        str = str14;
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", str);
                        aSMSerializerFactory = this;
                    }
                }
                str9 = str;
                aSMSerializerFactory2 = aSMSerializerFactory;
                str8 = str2;
                i3 = i + 1;
                str7 = str3;
                nonPropertyFilters_2 = nonPropertyFilters_;
                size2 = size;
                fieldInfoArr = getters;
            }
        }
    }

    private void generateWriteMethod(Class<?> clazz, MethodVisitor mw, FieldInfo[] getters, Context context) throws Exception {
        Label end;
        String writeAsArrayMethodName;
        int i;
        String str;
        String str2;
        boolean hasMethod;
        FieldInfo[] fieldInfoArr = getters;
        Label end2 = new Label();
        int size = fieldInfoArr.length;
        String str3 = "out";
        if (context.writeDirect) {
            end = end2;
        } else {
            Label endSupper_ = new Label();
            Label supper_ = new Label();
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.PrettyFormat.mask));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(Opcodes.IFNE, supper_);
            int length = fieldInfoArr.length;
            end = end2;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    hasMethod = false;
                    break;
                }
                int i3 = length;
                FieldInfo getter = fieldInfoArr[i2];
                if (getter.method == null) {
                    i2++;
                    length = i3;
                } else {
                    hasMethod = true;
                    break;
                }
            }
            if (hasMethod) {
                mw.visitVarInsn(25, context.var("out"));
                mw.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreErrorGetter.mask));
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
                mw.visitJumpInsn(Opcodes.IFEQ, endSupper_);
            } else {
                mw.visitJumpInsn(Opcodes.GOTO, endSupper_);
            }
            mw.visitLabel(supper_);
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(21, 5);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, JavaBeanSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitInsn(Opcodes.RETURN);
            mw.visitLabel(endSupper_);
        }
        if (!context.nonContext) {
            Label endRef_ = new Label();
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(21, 5);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeReference", "(L" + JSONSerializer + ";Ljava/lang/Object;I)Z");
            mw.visitJumpInsn(Opcodes.IFEQ, endRef_);
            mw.visitInsn(Opcodes.RETURN);
            mw.visitLabel(endRef_);
        }
        if (context.writeDirect) {
            if (context.nonContext) {
                writeAsArrayMethodName = "writeAsArrayNonContext";
            } else {
                writeAsArrayMethodName = "writeAsArray";
            }
        } else {
            writeAsArrayMethodName = "writeAsArrayNormal";
        }
        if ((context.beanInfo.features & SerializerFeature.BeanToArray.mask) == 0) {
            Label endWriteAsArray_ = new Label();
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.BeanToArray.mask));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(Opcodes.IFEQ, endWriteAsArray_);
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(21, 5);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, writeAsArrayMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitInsn(Opcodes.RETURN);
            mw.visitLabel(endWriteAsArray_);
        } else {
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(21, 5);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, context.className, writeAsArrayMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitInsn(Opcodes.RETURN);
        }
        String str4 = "(";
        if (!context.nonContext) {
            mw.visitVarInsn(25, 1);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getContext", "()" + SerialContext_desc);
            mw.visitVarInsn(58, context.var("parent"));
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("parent"));
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitLdcInsn(Integer.valueOf(context.beanInfo.features));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(" + SerialContext_desc + "Ljava/lang/Object;Ljava/lang/Object;I)V");
        }
        boolean writeClasName = (context.beanInfo.features & SerializerFeature.WriteClassName.mask) != 0;
        if (writeClasName || !context.writeDirect) {
            Label end_ = new Label();
            Label else_ = new Label();
            Label writeClass_ = new Label();
            if (!writeClasName) {
                mw.visitVarInsn(25, 1);
                mw.visitVarInsn(25, 4);
                mw.visitVarInsn(25, 2);
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
                mw.visitJumpInsn(Opcodes.IFEQ, else_);
            }
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(25, 2);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            mw.visitJumpInsn(Opcodes.IF_ACMPEQ, else_);
            mw.visitLabel(writeClass_);
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, 123);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            if (context.beanInfo.typeKey != null) {
                mw.visitLdcInsn(context.beanInfo.typeKey);
            } else {
                mw.visitInsn(1);
            }
            mw.visitVarInsn(25, 2);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeClassName", "(L" + JSONSerializer + ";Ljava/lang/String;Ljava/lang/Object;)V");
            mw.visitVarInsn(16, 44);
            mw.visitJumpInsn(Opcodes.GOTO, end_);
            mw.visitLabel(else_);
            mw.visitVarInsn(16, 123);
            mw.visitLabel(end_);
        } else {
            mw.visitVarInsn(16, 123);
        }
        mw.visitVarInsn(54, context.var("seperator"));
        if (!context.writeDirect) {
            _before(mw, context);
        }
        if (!context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isNotWriteDefaultValue", "()Z");
            mw.visitVarInsn(54, context.var("notWriteDefaultValue"));
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 0);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "checkValue", "(" + SerializeFilterable_desc + ")Z");
            mw.visitVarInsn(54, context.var("checkValue"));
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 0);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "hasNameFilters", "(" + SerializeFilterable_desc + ")Z");
            mw.visitVarInsn(54, context.var("hasNameFilters"));
        }
        int i4 = 0;
        while (i4 < size) {
            FieldInfo property = fieldInfoArr[i4];
            Class<?> propertyClass = property.fieldClass;
            mw.visitLdcInsn(property.name);
            mw.visitVarInsn(58, Context.fieldName);
            if (propertyClass == Byte.TYPE || propertyClass == Short.TYPE || propertyClass == Integer.TYPE) {
                Class<?> propertyClass2 = propertyClass;
                FieldInfo property2 = property;
                i = i4;
                str = str4;
                str2 = str3;
                _int(clazz, mw, property2, context, context.var(propertyClass2.getName()), 'I');
                i4 = i + 1;
                fieldInfoArr = getters;
                str4 = str;
                str3 = str2;
            } else {
                if (propertyClass == Long.TYPE) {
                    _long(clazz, mw, property, context);
                    i = i4;
                    str = str4;
                    str2 = str3;
                } else if (propertyClass == Float.TYPE) {
                    _float(clazz, mw, property, context);
                    i = i4;
                    str = str4;
                    str2 = str3;
                } else if (propertyClass == Double.TYPE) {
                    _double(clazz, mw, property, context);
                    i = i4;
                    str = str4;
                    str2 = str3;
                } else if (propertyClass == Boolean.TYPE) {
                    i = i4;
                    str = str4;
                    str2 = str3;
                    _int(clazz, mw, property, context, context.var("boolean"), 'Z');
                } else {
                    i = i4;
                    str = str4;
                    str2 = str3;
                    if (propertyClass != Character.TYPE) {
                        if (propertyClass == String.class) {
                            _string(clazz, mw, property, context);
                        } else if (propertyClass != BigDecimal.class) {
                            if (List.class.isAssignableFrom(propertyClass)) {
                                _list(clazz, mw, property, context);
                            } else if (propertyClass.isEnum()) {
                                _enum(clazz, mw, property, context);
                            } else {
                                _object(clazz, mw, property, context);
                            }
                        } else {
                            _decimal(clazz, mw, property, context);
                        }
                    } else {
                        _int(clazz, mw, property, context, context.var("char"), 'C');
                    }
                }
                i4 = i + 1;
                fieldInfoArr = getters;
                str4 = str;
                str3 = str2;
            }
        }
        String str5 = str4;
        String str6 = str3;
        if (!context.writeDirect) {
            _after(mw, context);
        }
        Label _else = new Label();
        Label _end_if = new Label();
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitIntInsn(16, 123);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, _else);
        mw.visitVarInsn(25, context.var(str6));
        mw.visitVarInsn(16, 123);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        mw.visitLabel(_else);
        mw.visitVarInsn(25, context.var(str6));
        mw.visitVarInsn(16, 125);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        mw.visitLabel(_end_if);
        mw.visitLabel(end);
        if (!context.nonContext) {
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("parent"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", str5 + SerialContext_desc + ")V");
        }
    }

    private void _object(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label _end = new Label();
        _nameApply(mw, property, context, _end);
        _get(mw, context, property);
        mw.visitVarInsn(58, context.var("object"));
        _filters(mw, property, context, _end);
        _writeObject(mw, property, context, _end);
        mw.visitLabel(_end);
    }

    private void _enum(Class<?> clazz, MethodVisitor mw, FieldInfo fieldInfo, Context context) {
        Label _not_null = new Label();
        Label _end_if = new Label();
        Label _end = new Label();
        _nameApply(mw, fieldInfo, context, _end);
        _get(mw, context, fieldInfo);
        mw.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Enum");
        mw.visitVarInsn(58, context.var("enum"));
        _filters(mw, fieldInfo, context, _end);
        mw.visitVarInsn(25, context.var("enum"));
        mw.visitJumpInsn(Opcodes.IFNONNULL, _not_null);
        _if_write_null(mw, fieldInfo, context);
        mw.visitJumpInsn(Opcodes.GOTO, _end_if);
        mw.visitLabel(_not_null);
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, context.var("enum"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Enum", "name", "()Ljava/lang/String;");
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValueStringWithDoubleQuote", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitInsn(3);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("enum"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        }
        _seperator(mw, context);
        mw.visitLabel(_end_if);
        mw.visitLabel(_end);
    }

    private void _int(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context, int var, char type) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(54, var);
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(21, var);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;" + type + ")V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _long(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(55, context.var("long", 2));
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(22, context.var("long", 2));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;J)V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _float(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(56, context.var("float"));
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(23, context.var("float"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;F)V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _double(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(57, context.var("double", 2));
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(24, context.var("double", 2));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;D)V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _get(MethodVisitor mw, Context context, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            mw.visitVarInsn(25, context.var("entity"));
            Class<?> declaringClass = method.getDeclaringClass();
            mw.visitMethodInsn(declaringClass.isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, ASMUtils.type(declaringClass), method.getName(), ASMUtils.desc(method));
            if (!method.getReturnType().equals(fieldInfo.fieldClass)) {
                mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldInfo.fieldClass));
                return;
            }
            return;
        }
        mw.visitVarInsn(25, context.var("entity"));
        Field field = fieldInfo.field;
        mw.visitFieldInsn(Opcodes.GETFIELD, ASMUtils.type(fieldInfo.declaringClass), field.getName(), ASMUtils.desc(field.getType()));
        if (!field.getType().equals(fieldInfo.fieldClass)) {
            mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldInfo.fieldClass));
        }
    }

    private void _decimal(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(58, context.var("decimal"));
        _filters(mw, property, context, end_);
        Label if_ = new Label();
        Label else_ = new Label();
        Label endIf_ = new Label();
        mw.visitLabel(if_);
        mw.visitVarInsn(25, context.var("decimal"));
        mw.visitJumpInsn(Opcodes.IFNONNULL, else_);
        _if_write_null(mw, property, context);
        mw.visitJumpInsn(Opcodes.GOTO, endIf_);
        mw.visitLabel(else_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(25, context.var("decimal"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/math/BigDecimal;)V");
        _seperator(mw, context);
        mw.visitJumpInsn(Opcodes.GOTO, endIf_);
        mw.visitLabel(endIf_);
        mw.visitLabel(end_);
    }

    private void _string(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        if (property.name.equals(context.beanInfo.typeKey)) {
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(25, 2);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
            mw.visitJumpInsn(Opcodes.IFNE, end_);
        }
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(58, context.var("string"));
        _filters(mw, property, context, end_);
        Label else_ = new Label();
        Label endIf_ = new Label();
        mw.visitVarInsn(25, context.var("string"));
        mw.visitJumpInsn(Opcodes.IFNONNULL, else_);
        _if_write_null(mw, property, context);
        mw.visitJumpInsn(Opcodes.GOTO, endIf_);
        mw.visitLabel(else_);
        if ("trim".equals(property.format)) {
            mw.visitVarInsn(25, context.var("string"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "trim", "()Ljava/lang/String;");
            mw.visitVarInsn(58, context.var("string"));
        }
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, context.var("string"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValueStringWithDoubleQuoteCheck", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, context.var("string"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
        }
        _seperator(mw, context);
        mw.visitLabel(endIf_);
        mw.visitLabel(end_);
    }

    private void _list(Class<?> clazz, MethodVisitor mw, FieldInfo fieldInfo, Context context) {
        Label _end_if_3;
        Label for_;
        Label forItemClassIfEnd_;
        Label forItemNullEnd_;
        Label forItemClassIfElse_;
        Label forEnd_;
        String str;
        int i;
        int i2;
        Label forItemClassIfEnd_2;
        String writeMethodName;
        java.lang.reflect.Type propertyType = fieldInfo.fieldType;
        java.lang.reflect.Type elementType = TypeUtils.getCollectionItemType(propertyType);
        Class<?> elementClass = null;
        if (elementType instanceof Class) {
            elementClass = (Class) elementType;
        }
        if (elementClass == Object.class || elementClass == Serializable.class) {
            elementClass = null;
        }
        Label end_ = new Label();
        Label else_ = new Label();
        Label endIf_ = new Label();
        _nameApply(mw, fieldInfo, context, end_);
        _get(mw, context, fieldInfo);
        mw.visitTypeInsn(Opcodes.CHECKCAST, "java/util/List");
        mw.visitVarInsn(58, context.var("list"));
        _filters(mw, fieldInfo, context, end_);
        mw.visitVarInsn(25, context.var("list"));
        mw.visitJumpInsn(Opcodes.IFNONNULL, else_);
        _if_write_null(mw, fieldInfo, context);
        mw.visitJumpInsn(Opcodes.GOTO, endIf_);
        mw.visitLabel(else_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(mw, context);
        mw.visitVarInsn(25, context.var("list"));
        mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I");
        mw.visitVarInsn(54, context.var("size"));
        Label _else_3 = new Label();
        Label _end_if_32 = new Label();
        mw.visitVarInsn(21, context.var("size"));
        mw.visitInsn(3);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, _else_3);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitLdcInsn(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(Ljava/lang/String;)V");
        mw.visitJumpInsn(Opcodes.GOTO, _end_if_32);
        mw.visitLabel(_else_3);
        if (!context.nonContext) {
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("list"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        }
        if (elementType == String.class && context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, context.var("list"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(Ljava/util/List;)V");
            _end_if_3 = _end_if_32;
            i = 25;
            i2 = Opcodes.INVOKEVIRTUAL;
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, 91);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            Label for_2 = new Label();
            Label forFirst_ = new Label();
            Label forEnd_2 = new Label();
            mw.visitInsn(3);
            _end_if_3 = _end_if_32;
            mw.visitVarInsn(54, context.var("i"));
            mw.visitLabel(for_2);
            mw.visitVarInsn(21, context.var("i"));
            mw.visitVarInsn(21, context.var("size"));
            mw.visitJumpInsn(Opcodes.IF_ICMPGE, forEnd_2);
            mw.visitVarInsn(21, context.var("i"));
            mw.visitJumpInsn(Opcodes.IFEQ, forFirst_);
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, 44);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
            mw.visitLabel(forFirst_);
            mw.visitVarInsn(25, context.var("list"));
            mw.visitVarInsn(21, context.var("i"));
            mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
            mw.visitVarInsn(58, context.var("list_item"));
            Label forItemNullEnd_2 = new Label();
            Label forItemNullElse_ = new Label();
            mw.visitVarInsn(25, context.var("list_item"));
            mw.visitJumpInsn(Opcodes.IFNONNULL, forItemNullElse_);
            mw.visitVarInsn(25, context.var("out"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "()V");
            mw.visitJumpInsn(Opcodes.GOTO, forItemNullEnd_2);
            mw.visitLabel(forItemNullElse_);
            Label forItemClassIfEnd_3 = new Label();
            Label forItemClassIfElse_2 = new Label();
            if (elementClass == null || !Modifier.isPublic(elementClass.getModifiers())) {
                for_ = for_2;
                forItemClassIfEnd_ = forItemClassIfEnd_3;
                forItemNullEnd_ = forItemNullEnd_2;
                forItemClassIfElse_ = forItemClassIfElse_2;
                forEnd_ = forEnd_2;
                str = "write";
            } else {
                forEnd_ = forEnd_2;
                mw.visitVarInsn(25, context.var("list_item"));
                for_ = for_2;
                forItemNullEnd_ = forItemNullEnd_2;
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                mw.visitJumpInsn(Opcodes.IF_ACMPNE, forItemClassIfElse_2);
                _getListFieldItemSer(context, mw, fieldInfo, elementClass);
                mw.visitVarInsn(58, context.var("list_item_desc"));
                Label instanceOfElse_ = new Label();
                Label instanceOfEnd_ = new Label();
                if (context.writeDirect) {
                    String writeMethodName2 = (context.nonContext && context.writeDirect) ? "writeDirectNonContext" : "write";
                    forItemClassIfElse_ = forItemClassIfElse_2;
                    mw.visitVarInsn(25, context.var("list_item_desc"));
                    mw.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
                    mw.visitJumpInsn(Opcodes.IFEQ, instanceOfElse_);
                    mw.visitVarInsn(25, context.var("list_item_desc"));
                    mw.visitTypeInsn(Opcodes.CHECKCAST, JavaBeanSerializer);
                    mw.visitVarInsn(25, 1);
                    mw.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        mw.visitInsn(1);
                    } else {
                        mw.visitVarInsn(21, context.var("i"));
                        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                    mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                    forItemClassIfEnd_2 = forItemClassIfEnd_3;
                    writeMethodName = "write";
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, writeMethodName2, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    mw.visitJumpInsn(Opcodes.GOTO, instanceOfEnd_);
                    mw.visitLabel(instanceOfElse_);
                } else {
                    forItemClassIfEnd_2 = forItemClassIfEnd_3;
                    forItemClassIfElse_ = forItemClassIfElse_2;
                    writeMethodName = "write";
                }
                mw.visitVarInsn(25, context.var("list_item_desc"));
                mw.visitVarInsn(25, 1);
                mw.visitVarInsn(25, context.var("list_item"));
                if (context.nonContext) {
                    mw.visitInsn(1);
                } else {
                    mw.visitVarInsn(21, context.var("i"));
                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                }
                mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                str = writeMethodName;
                mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, str, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                mw.visitLabel(instanceOfEnd_);
                forItemClassIfEnd_ = forItemClassIfEnd_2;
                mw.visitJumpInsn(Opcodes.GOTO, forItemClassIfEnd_);
            }
            mw.visitLabel(forItemClassIfElse_);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("list_item"));
            if (context.nonContext) {
                mw.visitInsn(1);
            } else {
                mw.visitVarInsn(21, context.var("i"));
                mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            }
            if (elementClass == null || !Modifier.isPublic(elementClass.getModifiers())) {
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            } else {
                mw.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) elementType)));
                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            }
            mw.visitLabel(forItemClassIfEnd_);
            mw.visitLabel(forItemNullEnd_);
            mw.visitIincInsn(context.var("i"), 1);
            mw.visitJumpInsn(Opcodes.GOTO, for_);
            mw.visitLabel(forEnd_);
            i = 25;
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, 93);
            String str2 = SerializeWriter;
            i2 = Opcodes.INVOKEVIRTUAL;
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, str, "(I)V");
        }
        mw.visitVarInsn(i, 1);
        mw.visitMethodInsn(i2, JSONSerializer, "popContext", "()V");
        mw.visitLabel(_end_if_3);
        _seperator(mw, context);
        mw.visitLabel(endIf_);
        mw.visitLabel(end_);
    }

    private void _filters(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        if (property.fieldTransient) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.SkipTransientField.mask));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(Opcodes.IFNE, _end);
        }
        _notWriteDefault(mw, property, context, _end);
        if (context.writeDirect) {
            return;
        }
        _apply(mw, property, context);
        mw.visitJumpInsn(Opcodes.IFEQ, _end);
        _processKey(mw, property, context);
        _processValue(mw, property, context, _end);
    }

    private void _nameApply(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        if (!context.writeDirect) {
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "applyName", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;)Z");
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
            _labelApply(mw, property, context, _end);
        }
        if (property.field == null) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreNonFieldGetter.mask));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(Opcodes.IFNE, _end);
        }
    }

    private void _labelApply(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(property.label);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "applyLabel", "(L" + JSONSerializer + ";Ljava/lang/String;)Z");
        mw.visitJumpInsn(Opcodes.IFEQ, _end);
    }

    private void _writeObject(MethodVisitor mw, FieldInfo fieldInfo, Context context, Label _end) {
        String format;
        Label classIfEnd_;
        Label classIfElse_;
        String writeMethodName;
        String format2 = fieldInfo.getFormat();
        Class<?> fieldClass = fieldInfo.fieldClass;
        Label notNull_ = new Label();
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("object"));
        } else {
            mw.visitVarInsn(25, Context.processValue);
        }
        mw.visitInsn(89);
        mw.visitVarInsn(58, context.var("object"));
        mw.visitJumpInsn(Opcodes.IFNONNULL, notNull_);
        _if_write_null(mw, fieldInfo, context);
        mw.visitJumpInsn(Opcodes.GOTO, _end);
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(mw, context);
        Label classIfEnd_2 = new Label();
        Label classIfElse_2 = new Label();
        if (!Modifier.isPublic(fieldClass.getModifiers()) || ParserConfig.isPrimitive2(fieldClass)) {
            format = format2;
            classIfEnd_ = classIfEnd_2;
            classIfElse_ = classIfElse_2;
        } else {
            mw.visitVarInsn(25, context.var("object"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldClass)));
            mw.visitJumpInsn(Opcodes.IF_ACMPNE, classIfElse_2);
            _getFieldSer(context, mw, fieldInfo);
            mw.visitVarInsn(58, context.var("fied_ser"));
            Label instanceOfElse_ = new Label();
            Label instanceOfEnd_ = new Label();
            mw.visitVarInsn(25, context.var("fied_ser"));
            mw.visitTypeInsn(Opcodes.INSTANCEOF, JavaBeanSerializer);
            mw.visitJumpInsn(Opcodes.IFEQ, instanceOfElse_);
            boolean disableCircularReferenceDetect = (fieldInfo.serialzeFeatures & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
            boolean fieldBeanToArray = (SerializerFeature.BeanToArray.mask & fieldInfo.serialzeFeatures) != 0;
            if (disableCircularReferenceDetect || (context.nonContext && context.writeDirect)) {
                writeMethodName = fieldBeanToArray ? "writeAsArrayNonContext" : "writeDirectNonContext";
            } else {
                writeMethodName = fieldBeanToArray ? "writeAsArray" : "write";
            }
            mw.visitVarInsn(25, context.var("fied_ser"));
            mw.visitTypeInsn(Opcodes.CHECKCAST, JavaBeanSerializer);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("object"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, 0);
            format = format2;
            mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            classIfElse_ = classIfElse_2;
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, writeMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitJumpInsn(Opcodes.GOTO, instanceOfEnd_);
            mw.visitLabel(instanceOfElse_);
            mw.visitVarInsn(25, context.var("fied_ser"));
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("object"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, 0);
            mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitLabel(instanceOfEnd_);
            classIfEnd_ = classIfEnd_2;
            mw.visitJumpInsn(Opcodes.GOTO, classIfEnd_);
        }
        mw.visitLabel(classIfElse_);
        mw.visitVarInsn(25, 1);
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("object"));
        } else {
            mw.visitVarInsn(25, Context.processValue);
        }
        if (format != null) {
            mw.visitLdcInsn(format);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, Context.fieldName);
            if ((fieldInfo.fieldType instanceof Class) && ((Class) fieldInfo.fieldType).isPrimitive()) {
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            } else {
                if (fieldInfo.fieldClass == String.class) {
                    mw.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) String.class)));
                } else {
                    mw.visitVarInsn(25, 0);
                    mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            }
        }
        mw.visitLabel(classIfEnd_);
        _seperator(mw, context);
    }

    private void _before(MethodVisitor mw, Context context) {
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeBefore", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        mw.visitVarInsn(54, context.var("seperator"));
    }

    private void _after(MethodVisitor mw, Context context) {
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "writeAfter", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        mw.visitVarInsn(54, context.var("seperator"));
    }

    private void _notWriteDefault(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        if (context.writeDirect) {
            return;
        }
        Label elseLabel = new Label();
        mw.visitVarInsn(21, context.var("notWriteDefaultValue"));
        mw.visitJumpInsn(Opcodes.IFEQ, elseLabel);
        Class<?> propertyClass = property.fieldClass;
        if (propertyClass == Boolean.TYPE) {
            mw.visitVarInsn(21, context.var("boolean"));
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
        } else if (propertyClass == Byte.TYPE) {
            mw.visitVarInsn(21, context.var("byte"));
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
        } else if (propertyClass == Short.TYPE) {
            mw.visitVarInsn(21, context.var("short"));
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
        } else if (propertyClass == Integer.TYPE) {
            mw.visitVarInsn(21, context.var("int"));
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
        } else if (propertyClass == Long.TYPE) {
            mw.visitVarInsn(22, context.var("long"));
            mw.visitInsn(9);
            mw.visitInsn(Opcodes.LCMP);
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
        } else if (propertyClass == Float.TYPE) {
            mw.visitVarInsn(23, context.var("float"));
            mw.visitInsn(11);
            mw.visitInsn(Opcodes.FCMPL);
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
        } else if (propertyClass == Double.TYPE) {
            mw.visitVarInsn(24, context.var("double"));
            mw.visitInsn(14);
            mw.visitInsn(Opcodes.DCMPL);
            mw.visitJumpInsn(Opcodes.IFEQ, _end);
        }
        mw.visitLabel(elseLabel);
    }

    private void _apply(MethodVisitor mw, FieldInfo property, Context context) {
        Class<?> propertyClass = property.fieldClass;
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, Context.fieldName);
        if (propertyClass == Byte.TYPE) {
            mw.visitVarInsn(21, context.var("byte"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (propertyClass == Short.TYPE) {
            mw.visitVarInsn(21, context.var("short"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (propertyClass == Integer.TYPE) {
            mw.visitVarInsn(21, context.var("int"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (propertyClass == Character.TYPE) {
            mw.visitVarInsn(21, context.var("char"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (propertyClass == Long.TYPE) {
            mw.visitVarInsn(22, context.var("long", 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (propertyClass == Float.TYPE) {
            mw.visitVarInsn(23, context.var("float"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (propertyClass == Double.TYPE) {
            mw.visitVarInsn(24, context.var("double", 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (propertyClass == Boolean.TYPE) {
            mw.visitVarInsn(21, context.var("boolean"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (propertyClass == BigDecimal.class) {
            mw.visitVarInsn(25, context.var("decimal"));
        } else if (propertyClass == String.class) {
            mw.visitVarInsn(25, context.var("string"));
        } else if (propertyClass.isEnum()) {
            mw.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(propertyClass)) {
            mw.visitVarInsn(25, context.var("list"));
        } else {
            mw.visitVarInsn(25, context.var("object"));
        }
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "apply", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
    }

    private void _processValue(MethodVisitor mw, FieldInfo fieldInfo, Context context, Label _end) {
        Label processKeyElse_ = new Label();
        Class<?> fieldClass = fieldInfo.fieldClass;
        if (fieldClass.isPrimitive()) {
            Label checkValueEnd_ = new Label();
            mw.visitVarInsn(21, context.var("checkValue"));
            mw.visitJumpInsn(Opcodes.IFNE, checkValueEnd_);
            mw.visitInsn(1);
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(58, Context.processValue);
            mw.visitJumpInsn(Opcodes.GOTO, processKeyElse_);
            mw.visitLabel(checkValueEnd_);
        }
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 0);
        mw.visitLdcInsn(Integer.valueOf(context.getFieldOrinal(fieldInfo.name)));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "getBeanContext", "(I)" + ASMUtils.desc((Class<?>) BeanContext.class));
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, Context.fieldName);
        if (fieldClass == Byte.TYPE) {
            mw.visitVarInsn(21, context.var("byte"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Short.TYPE) {
            mw.visitVarInsn(21, context.var("short"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Integer.TYPE) {
            mw.visitVarInsn(21, context.var("int"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Character.TYPE) {
            mw.visitVarInsn(21, context.var("char"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Long.TYPE) {
            mw.visitVarInsn(22, context.var("long", 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Float.TYPE) {
            mw.visitVarInsn(23, context.var("float"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Double.TYPE) {
            mw.visitVarInsn(24, context.var("double", 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Boolean.TYPE) {
            mw.visitVarInsn(21, context.var("boolean"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == BigDecimal.class) {
            mw.visitVarInsn(25, context.var("decimal"));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        } else if (fieldClass == String.class) {
            mw.visitVarInsn(25, context.var("string"));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        } else if (fieldClass.isEnum()) {
            mw.visitVarInsn(25, context.var("enum"));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        } else if (List.class.isAssignableFrom(fieldClass)) {
            mw.visitVarInsn(25, context.var("list"));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        } else {
            mw.visitVarInsn(25, context.var("object"));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        }
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "processValue", "(L" + JSONSerializer + ";" + ASMUtils.desc((Class<?>) BeanContext.class) + "Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;");
        mw.visitVarInsn(58, Context.processValue);
        mw.visitVarInsn(25, Context.original);
        mw.visitVarInsn(25, Context.processValue);
        mw.visitJumpInsn(Opcodes.IF_ACMPEQ, processKeyElse_);
        _writeObject(mw, fieldInfo, context, _end);
        mw.visitJumpInsn(Opcodes.GOTO, _end);
        mw.visitLabel(processKeyElse_);
    }

    private void _processKey(MethodVisitor mw, FieldInfo property, Context context) {
        Label _else_processKey = new Label();
        mw.visitVarInsn(21, context.var("hasNameFilters"));
        mw.visitJumpInsn(Opcodes.IFEQ, _else_processKey);
        Class<?> propertyClass = property.fieldClass;
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, Context.fieldName);
        if (propertyClass == Byte.TYPE) {
            mw.visitVarInsn(21, context.var("byte"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (propertyClass == Short.TYPE) {
            mw.visitVarInsn(21, context.var("short"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (propertyClass == Integer.TYPE) {
            mw.visitVarInsn(21, context.var("int"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (propertyClass == Character.TYPE) {
            mw.visitVarInsn(21, context.var("char"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (propertyClass == Long.TYPE) {
            mw.visitVarInsn(22, context.var("long", 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (propertyClass == Float.TYPE) {
            mw.visitVarInsn(23, context.var("float"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (propertyClass == Double.TYPE) {
            mw.visitVarInsn(24, context.var("double", 2));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (propertyClass == Boolean.TYPE) {
            mw.visitVarInsn(21, context.var("boolean"));
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (propertyClass == BigDecimal.class) {
            mw.visitVarInsn(25, context.var("decimal"));
        } else if (propertyClass == String.class) {
            mw.visitVarInsn(25, context.var("string"));
        } else if (propertyClass.isEnum()) {
            mw.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(propertyClass)) {
            mw.visitVarInsn(25, context.var("list"));
        } else {
            mw.visitVarInsn(25, context.var("object"));
        }
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JavaBeanSerializer, "processKey", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
        mw.visitVarInsn(58, Context.fieldName);
        mw.visitLabel(_else_processKey);
    }

    private void _if_write_null(MethodVisitor mw, FieldInfo fieldInfo, Context context) {
        int writeNullFeatures;
        Class<?> propertyClass = fieldInfo.fieldClass;
        Label _if = new Label();
        Label _else = new Label();
        Label _write_null = new Label();
        Label _end_if = new Label();
        mw.visitLabel(_if);
        JSONField annotation = fieldInfo.getAnnotation();
        int features = 0;
        if (annotation != null) {
            features = SerializerFeature.of(annotation.serialzeFeatures());
        }
        JSONType jsonType = context.beanInfo.jsonType;
        if (jsonType != null) {
            features |= SerializerFeature.of(jsonType.serialzeFeatures());
        }
        if (propertyClass == String.class) {
            writeNullFeatures = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullStringAsEmpty.getMask();
        } else if (Number.class.isAssignableFrom(propertyClass)) {
            writeNullFeatures = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullNumberAsZero.getMask();
        } else if (Collection.class.isAssignableFrom(propertyClass)) {
            writeNullFeatures = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullListAsEmpty.getMask();
        } else if (Boolean.class == propertyClass) {
            writeNullFeatures = SerializerFeature.WriteMapNullValue.getMask() | SerializerFeature.WriteNullBooleanAsFalse.getMask();
        } else {
            writeNullFeatures = SerializerFeature.WRITE_MAP_NULL_FEATURES;
        }
        if ((features & writeNullFeatures) == 0) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(writeNullFeatures));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(Opcodes.IFEQ, _else);
        }
        mw.visitLabel(_write_null);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "write", "(I)V");
        _writeFieldName(mw, context);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitLdcInsn(Integer.valueOf(features));
        if (propertyClass == String.class || propertyClass == Character.class) {
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullStringAsEmpty.mask));
        } else if (Number.class.isAssignableFrom(propertyClass)) {
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullNumberAsZero.mask));
        } else if (propertyClass == Boolean.class) {
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullBooleanAsFalse.mask));
        } else if (Collection.class.isAssignableFrom(propertyClass) || propertyClass.isArray()) {
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullListAsEmpty.mask));
        } else {
            mw.visitLdcInsn(0);
        }
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeNull", "(II)V");
        _seperator(mw, context);
        mw.visitJumpInsn(Opcodes.GOTO, _end_if);
        mw.visitLabel(_else);
        mw.visitLabel(_end_if);
    }

    private void _writeFieldName(MethodVisitor mw, Context context) {
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldNameDirect", "(Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitInsn(3);
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
        }
    }

    private void _seperator(MethodVisitor mw, Context context) {
        mw.visitVarInsn(16, 44);
        mw.visitVarInsn(54, context.var("seperator"));
    }

    private void _getListFieldItemSer(Context context, MethodVisitor mw, FieldInfo fieldInfo, Class<?> itemType) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        mw.visitJumpInsn(Opcodes.IFNONNULL, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(Type.getType(ASMUtils.desc(itemType)));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        mw.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
    }

    private void _getFieldSer(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        mw.visitJumpInsn(Opcodes.IFNONNULL, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        mw.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
    }
}