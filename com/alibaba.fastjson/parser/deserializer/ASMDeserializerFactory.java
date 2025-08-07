package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class ASMDeserializerFactory implements Opcodes {
    static final String DefaultJSONParser = ASMUtils.type(DefaultJSONParser.class);
    static final String JSONLexerBase = ASMUtils.type(JSONLexerBase.class);
    public final ASMClassLoader classLoader;
    protected final AtomicLong seed = new AtomicLong();

    public ASMDeserializerFactory(ClassLoader parentClassLoader) {
        this.classLoader = parentClassLoader instanceof ASMClassLoader ? (ASMClassLoader) parentClassLoader : new ASMClassLoader(parentClassLoader);
    }

    public ObjectDeserializer createJavaBeanDeserializer(ParserConfig config, JavaBeanInfo beanInfo) throws Exception {
        String packageName;
        String classNameType;
        Class<?> clazz = beanInfo.clazz;
        if (clazz.isPrimitive()) {
            throw new IllegalArgumentException("not support type :" + clazz.getName());
        }
        String className = "FastjsonASMDeserializer_" + this.seed.incrementAndGet() + "_" + clazz.getSimpleName();
        Package pkg = ASMDeserializerFactory.class.getPackage();
        if (pkg != null) {
            String packageName2 = pkg.getName();
            String classNameType2 = packageName2.replace('.', '/') + "/" + className;
            packageName = packageName2 + "." + className;
            classNameType = classNameType2;
        } else {
            packageName = className;
            classNameType = className;
        }
        ClassWriter cw = new ClassWriter();
        cw.visit(49, 33, classNameType, ASMUtils.type(JavaBeanDeserializer.class), null);
        _init(cw, new Context(classNameType, config, beanInfo, 3));
        _createInstance(cw, new Context(classNameType, config, beanInfo, 3));
        _deserialze(cw, new Context(classNameType, config, beanInfo, 5));
        _deserialzeArrayMapping(cw, new Context(classNameType, config, beanInfo, 4));
        byte[] code = cw.toByteArray();
        Class<?> deserClass = this.classLoader.defineClassPublic(packageName, code, 0, code.length);
        Constructor<?> constructor = deserClass.getConstructor(ParserConfig.class, JavaBeanInfo.class);
        Object instance = constructor.newInstance(config, beanInfo);
        return (ObjectDeserializer) instance;
    }

    private void _setFlag(MethodVisitor mw, Context context, int i) {
        String varName = "_asm_flag_" + (i / 32);
        mw.visitVarInsn(21, context.var(varName));
        mw.visitLdcInsn(Integer.valueOf(1 << i));
        mw.visitInsn(128);
        mw.visitVarInsn(54, context.var(varName));
    }

    private void _isFlag(MethodVisitor mw, Context context, int i, Label label) {
        mw.visitVarInsn(21, context.var("_asm_flag_" + (i / 32)));
        mw.visitLdcInsn(Integer.valueOf(1 << i));
        mw.visitInsn(126);
        mw.visitJumpInsn(Opcodes.IFEQ, label);
    }

    private void _deserialzeArrayMapping(ClassWriter cw, Context context) {
        int i;
        Label typeNameNotNull_;
        int fieldListSize;
        FieldInfo[] sortedFieldInfoList;
        int i2;
        MethodVisitor mw = new MethodWriter(cw, 1, "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        defineVarLexer(context, mw);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getSymbolTable", "()" + ASMUtils.desc((Class<?>) SymbolTable.class));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanTypeName", "(" + ASMUtils.desc((Class<?>) SymbolTable.class) + ")Ljava/lang/String;");
        mw.visitVarInsn(58, context.var("typeName"));
        Label typeNameNotNull_2 = new Label();
        mw.visitVarInsn(25, context.var("typeName"));
        mw.visitJumpInsn(Opcodes.IFNULL, typeNameNotNull_2);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc((Class<?>) ParserConfig.class));
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, ASMUtils.type(JavaBeanDeserializer.class), "beanInfo", ASMUtils.desc((Class<?>) JavaBeanInfo.class));
        mw.visitVarInsn(25, context.var("typeName"));
        mw.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(JavaBeanDeserializer.class), "getSeeAlso", "(" + ASMUtils.desc((Class<?>) ParserConfig.class) + ASMUtils.desc((Class<?>) JavaBeanInfo.class) + "Ljava/lang/String;)" + ASMUtils.desc((Class<?>) JavaBeanDeserializer.class));
        mw.visitVarInsn(58, context.var("userTypeDeser"));
        mw.visitVarInsn(25, context.var("userTypeDeser"));
        mw.visitTypeInsn(Opcodes.INSTANCEOF, ASMUtils.type(JavaBeanDeserializer.class));
        mw.visitJumpInsn(Opcodes.IFEQ, typeNameNotNull_2);
        mw.visitVarInsn(25, context.var("userTypeDeser"));
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, 3);
        mw.visitVarInsn(25, 4);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        mw.visitInsn(Opcodes.ARETURN);
        mw.visitLabel(typeNameNotNull_2);
        _createInstance(context, mw);
        FieldInfo[] sortedFieldInfoList2 = context.beanInfo.sortedFields;
        int fieldListSize2 = sortedFieldInfoList2.length;
        int i3 = 0;
        while (i3 < fieldListSize2) {
            boolean last = i3 == fieldListSize2 + (-1);
            char seperator = last ? ']' : ',';
            FieldInfo fieldInfo = sortedFieldInfoList2[i3];
            Class<?> fieldClass = fieldInfo.fieldClass;
            Type fieldType = fieldInfo.fieldType;
            int fieldListSize3 = fieldListSize2;
            if (fieldClass == Byte.TYPE || fieldClass == Short.TYPE || fieldClass == Integer.TYPE) {
                i = i3;
                FieldInfo fieldInfo2 = fieldInfo;
                typeNameNotNull_ = typeNameNotNull_2;
                char seperator2 = seperator;
                fieldListSize = fieldListSize3;
                sortedFieldInfoList = sortedFieldInfoList2;
                mw.visitVarInsn(25, context.var("lexer"));
                mw.visitVarInsn(16, seperator2);
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                mw.visitVarInsn(54, context.var_asm(fieldInfo2));
                i3 = i + 1;
                fieldListSize2 = fieldListSize;
                sortedFieldInfoList2 = sortedFieldInfoList;
                typeNameNotNull_2 = typeNameNotNull_;
            } else {
                FieldInfo[] sortedFieldInfoList3 = sortedFieldInfoList2;
                typeNameNotNull_ = typeNameNotNull_2;
                boolean last2 = last;
                if (fieldClass == Byte.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label valueNullEnd_ = new Label();
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    mw.visitLdcInsn(5);
                    mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_);
                    mw.visitInsn(1);
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    mw.visitLabel(valueNullEnd_);
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Short.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label valueNullEnd_2 = new Label();
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    mw.visitLdcInsn(5);
                    mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_2);
                    mw.visitInsn(1);
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    mw.visitLabel(valueNullEnd_2);
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Integer.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label valueNullEnd_3 = new Label();
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    mw.visitLdcInsn(5);
                    mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_3);
                    mw.visitInsn(1);
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    mw.visitLabel(valueNullEnd_3);
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Long.TYPE) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                    mw.visitVarInsn(55, context.var_asm(fieldInfo, 2));
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Long.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label valueNullEnd_4 = new Label();
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    mw.visitLdcInsn(5);
                    mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_4);
                    mw.visitInsn(1);
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    mw.visitLabel(valueNullEnd_4);
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Boolean.TYPE) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanBoolean", "(C)Z");
                    mw.visitVarInsn(54, context.var_asm(fieldInfo));
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Float.TYPE) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFloat", "(C)F");
                    mw.visitVarInsn(56, context.var_asm(fieldInfo));
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Float.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFloat", "(C)F");
                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label valueNullEnd_5 = new Label();
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    mw.visitLdcInsn(5);
                    mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_5);
                    mw.visitInsn(1);
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    mw.visitLabel(valueNullEnd_5);
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Double.TYPE) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDouble", "(C)D");
                    mw.visitVarInsn(57, context.var_asm(fieldInfo, 2));
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Double.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDouble", "(C)D");
                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    Label valueNullEnd_6 = new Label();
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                    mw.visitLdcInsn(5);
                    mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_6);
                    mw.visitInsn(1);
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    mw.visitLabel(valueNullEnd_6);
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Character.TYPE) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                    mw.visitInsn(3);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C");
                    mw.visitVarInsn(54, context.var_asm(fieldInfo));
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == String.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanString", "(C)Ljava/lang/String;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == BigDecimal.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDecimal", "(C)Ljava/math/BigDecimal;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == Date.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanDate", "(C)Ljava/util/Date;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass == UUID.class) {
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanUUID", "(C)Ljava/util/UUID;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    i = i3;
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                } else if (fieldClass.isEnum()) {
                    Label enumNumIf_ = new Label();
                    Label enumNumErr_ = new Label();
                    Label enumStore_ = new Label();
                    Label enumQuote_ = new Label();
                    int i4 = i3;
                    int i5 = context.var("lexer");
                    mw.visitVarInsn(25, i5);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
                    mw.visitInsn(89);
                    mw.visitVarInsn(54, context.var("ch"));
                    mw.visitLdcInsn(110);
                    mw.visitJumpInsn(Opcodes.IF_ICMPEQ, enumQuote_);
                    mw.visitVarInsn(21, context.var("ch"));
                    mw.visitLdcInsn(34);
                    mw.visitJumpInsn(Opcodes.IF_ICMPNE, enumNumIf_);
                    mw.visitLabel(enumQuote_);
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldClass)));
                    mw.visitVarInsn(25, 1);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getSymbolTable", "()" + ASMUtils.desc((Class<?>) SymbolTable.class));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanEnum", "(Ljava/lang/Class;" + ASMUtils.desc((Class<?>) SymbolTable.class) + "C)Ljava/lang/Enum;");
                    mw.visitJumpInsn(Opcodes.GOTO, enumStore_);
                    mw.visitLabel(enumNumIf_);
                    mw.visitVarInsn(21, context.var("ch"));
                    mw.visitLdcInsn(48);
                    mw.visitJumpInsn(Opcodes.IF_ICMPLT, enumNumErr_);
                    mw.visitVarInsn(21, context.var("ch"));
                    mw.visitLdcInsn(57);
                    mw.visitJumpInsn(Opcodes.IF_ICMPGT, enumNumErr_);
                    _getFieldDeser(context, mw, fieldInfo);
                    mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(EnumDeserializer.class));
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanInt", "(C)I");
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(EnumDeserializer.class), "valueOf", "(I)Ljava/lang/Enum;");
                    mw.visitJumpInsn(Opcodes.GOTO, enumStore_);
                    mw.visitLabel(enumNumErr_);
                    mw.visitVarInsn(25, 0);
                    mw.visitVarInsn(25, context.var("lexer"));
                    mw.visitVarInsn(16, seperator);
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "scanEnum", "(L" + JSONLexerBase + ";C)Ljava/lang/Enum;");
                    mw.visitLabel(enumStore_);
                    mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
                    mw.visitVarInsn(58, context.var_asm(fieldInfo));
                    fieldListSize = fieldListSize3;
                    sortedFieldInfoList = sortedFieldInfoList3;
                    i = i4;
                } else {
                    int i6 = i3;
                    if (Collection.class.isAssignableFrom(fieldClass)) {
                        Class<?> itemClass = TypeUtils.getCollectionItemClass(fieldType);
                        if (itemClass == String.class) {
                            if (fieldClass == List.class || fieldClass == Collections.class || fieldClass == ArrayList.class) {
                                mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(ArrayList.class));
                                mw.visitInsn(89);
                                mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(ArrayList.class), "<init>", "()V");
                            } else {
                                mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldClass)));
                                mw.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/Class;)Ljava/util/Collection;");
                            }
                            mw.visitVarInsn(58, context.var_asm(fieldInfo));
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitVarInsn(25, context.var_asm(fieldInfo));
                            mw.visitVarInsn(16, seperator);
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanStringArray", "(Ljava/util/Collection;C)V");
                            Label valueNullEnd_7 = new Label();
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
                            mw.visitLdcInsn(5);
                            mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_7);
                            mw.visitInsn(1);
                            mw.visitVarInsn(58, context.var_asm(fieldInfo));
                            mw.visitLabel(valueNullEnd_7);
                            i2 = i6;
                        } else {
                            Label notError_ = new Label();
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
                            mw.visitVarInsn(54, context.var("token"));
                            mw.visitVarInsn(21, context.var("token"));
                            int token = i6 == 0 ? 14 : 16;
                            mw.visitLdcInsn(Integer.valueOf(token));
                            mw.visitJumpInsn(Opcodes.IF_ICMPEQ, notError_);
                            mw.visitVarInsn(25, 1);
                            mw.visitLdcInsn(Integer.valueOf(token));
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "throwException", "(I)V");
                            mw.visitLabel(notError_);
                            Label quickElse_ = new Label();
                            Label quickEnd_ = new Label();
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
                            mw.visitVarInsn(16, 91);
                            mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElse_);
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
                            mw.visitInsn(87);
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitLdcInsn(14);
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
                            mw.visitJumpInsn(Opcodes.GOTO, quickEnd_);
                            mw.visitLabel(quickElse_);
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitLdcInsn(14);
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
                            mw.visitLabel(quickEnd_);
                            i2 = i6;
                            _newCollection(mw, fieldClass, i2, false);
                            mw.visitInsn(89);
                            mw.visitVarInsn(58, context.var_asm(fieldInfo));
                            _getCollectionFieldItemDeser(context, mw, fieldInfo, itemClass);
                            mw.visitVarInsn(25, 1);
                            mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(itemClass)));
                            mw.visitVarInsn(25, 3);
                            mw.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(JavaBeanDeserializer.class), "parseArray", "(Ljava/util/Collection;" + ASMUtils.desc((Class<?>) ObjectDeserializer.class) + "L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)V");
                        }
                        i = i2;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                    } else if (fieldClass.isArray()) {
                        mw.visitVarInsn(25, context.var("lexer"));
                        mw.visitLdcInsn(14);
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
                        mw.visitVarInsn(25, 1);
                        mw.visitVarInsn(25, 0);
                        mw.visitLdcInsn(Integer.valueOf(i6));
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "parseObject", "(Ljava/lang/reflect/Type;)Ljava/lang/Object;");
                        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
                        mw.visitVarInsn(58, context.var_asm(fieldInfo));
                        i = i6;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                    } else {
                        Label objElseIf_ = new Label();
                        Label objEndIf_ = new Label();
                        if (fieldClass == Date.class) {
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
                            mw.visitLdcInsn(49);
                            mw.visitJumpInsn(Opcodes.IF_ICMPNE, objElseIf_);
                            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(Date.class));
                            mw.visitInsn(89);
                            mw.visitVarInsn(25, context.var("lexer"));
                            mw.visitVarInsn(16, seperator);
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanLong", "(C)J");
                            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(Date.class), "<init>", "(J)V");
                            mw.visitVarInsn(58, context.var_asm(fieldInfo));
                            mw.visitJumpInsn(Opcodes.GOTO, objEndIf_);
                        }
                        mw.visitLabel(objElseIf_);
                        _quickNextToken(context, mw, 14);
                        i = i6;
                        fieldListSize = fieldListSize3;
                        sortedFieldInfoList = sortedFieldInfoList3;
                        _deserObject(context, mw, fieldInfo, fieldClass, i);
                        mw.visitVarInsn(25, context.var("lexer"));
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
                        mw.visitLdcInsn(15);
                        mw.visitJumpInsn(Opcodes.IF_ICMPEQ, objEndIf_);
                        mw.visitVarInsn(25, 0);
                        mw.visitVarInsn(25, context.var("lexer"));
                        if (!last2) {
                            mw.visitLdcInsn(16);
                        } else {
                            mw.visitLdcInsn(15);
                        }
                        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "check", "(" + ASMUtils.desc((Class<?>) JSONLexer.class) + "I)V");
                        mw.visitLabel(objEndIf_);
                    }
                }
                i3 = i + 1;
                fieldListSize2 = fieldListSize;
                sortedFieldInfoList2 = sortedFieldInfoList;
                typeNameNotNull_2 = typeNameNotNull_;
            }
        }
        _batchSet(context, mw, false);
        Label quickElse_2 = new Label();
        Label quickElseIf_ = new Label();
        Label quickElseIfEOI_ = new Label();
        Label quickEnd_2 = new Label();
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
        mw.visitInsn(89);
        mw.visitVarInsn(54, context.var("ch"));
        mw.visitVarInsn(16, 44);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIf_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        mw.visitInsn(87);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(16);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, quickEnd_2);
        mw.visitLabel(quickElseIf_);
        mw.visitVarInsn(21, context.var("ch"));
        mw.visitVarInsn(16, 93);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIfEOI_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        mw.visitInsn(87);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(15);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, quickEnd_2);
        mw.visitLabel(quickElseIfEOI_);
        mw.visitVarInsn(21, context.var("ch"));
        mw.visitVarInsn(16, 26);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElse_2);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        mw.visitInsn(87);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(20);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, quickEnd_2);
        mw.visitLabel(quickElse_2);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(16);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        mw.visitLabel(quickEnd_2);
        mw.visitVarInsn(25, context.var("instance"));
        mw.visitInsn(Opcodes.ARETURN);
        mw.visitMaxs(5, context.variantIndex);
        mw.visitEnd();
    }

    private void _deserialze(ClassWriter cw, Context context) {
        int fieldListSize;
        Label notMatch_;
        int i;
        String str;
        String str2;
        String str3;
        Label continue_2;
        Label continue_3;
        Label reset_;
        Label end_;
        int fieldListSize2;
        int i2;
        String str4;
        Label reset_2;
        Label end_2;
        JavaBeanInfo beanInfo;
        String str5;
        Label reset_3;
        Label super_;
        if (context.fieldInfoList.length == 0) {
            return;
        }
        for (FieldInfo fieldInfo : context.fieldInfoList) {
            Class<?> fieldClass = fieldInfo.fieldClass;
            Type fieldType = fieldInfo.fieldType;
            if (fieldClass == Character.TYPE) {
                return;
            }
            if (Collection.class.isAssignableFrom(fieldClass)) {
                if (fieldType instanceof ParameterizedType) {
                    Type itemType = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
                    if (!(itemType instanceof Class)) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
        JavaBeanInfo beanInfo2 = context.beanInfo;
        context.fieldInfoList = beanInfo2.sortedFields;
        String str6 = "(L";
        MethodVisitor mw = new MethodWriter(cw, 1, "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
        Label reset_4 = new Label();
        Label super_2 = new Label();
        Label return_ = new Label();
        Label end_3 = new Label();
        defineVarLexer(context, mw);
        Label next_ = new Label();
        String str7 = "lexer";
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(14);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, next_);
        if ((beanInfo2.parserFeatures & Feature.SupportArrayToBean.mask) == 0) {
            mw.visitVarInsn(25, context.var("lexer"));
            mw.visitVarInsn(21, 4);
            mw.visitLdcInsn(Integer.valueOf(Feature.SupportArrayToBean.mask));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(II)Z");
            mw.visitJumpInsn(Opcodes.IFEQ, next_);
        }
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, 3);
        mw.visitInsn(1);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, context.className, "deserialzeArrayMapping", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        mw.visitInsn(Opcodes.ARETURN);
        mw.visitLabel(next_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(Integer.valueOf(Feature.SortFeidFastMatch.mask));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(I)Z");
        Label continue_ = new Label();
        mw.visitJumpInsn(Opcodes.IFNE, continue_);
        mw.visitJumpInsn(Opcodes.GOTO_W, super_2);
        mw.visitLabel(continue_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(context.clazz.getName());
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanType", "(Ljava/lang/String;)I");
        mw.visitLdcInsn(-1);
        Label continue_22 = new Label();
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, continue_22);
        mw.visitJumpInsn(Opcodes.GOTO_W, super_2);
        mw.visitLabel(continue_22);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        mw.visitVarInsn(58, context.var("mark_context"));
        mw.visitInsn(3);
        mw.visitVarInsn(54, context.var("matchedCount"));
        _createInstance(context, mw);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        mw.visitVarInsn(58, context.var("context"));
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var("context"));
        mw.visitVarInsn(25, context.var("instance"));
        mw.visitVarInsn(25, 3);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + "Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc((Class<?>) ParseContext.class));
        mw.visitVarInsn(58, context.var("childContext"));
        mw.visitVarInsn(25, context.var("lexer"));
        String str8 = "matchStat";
        String str9 = "I";
        mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, "matchStat", "I");
        mw.visitLdcInsn(4);
        Label continue_32 = new Label();
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, continue_32);
        mw.visitJumpInsn(Opcodes.GOTO_W, return_);
        mw.visitLabel(continue_32);
        mw.visitInsn(3);
        Label continue_23 = continue_22;
        mw.visitIntInsn(54, context.var("matchStat"));
        int fieldListSize3 = context.fieldInfoList.length;
        int i3 = 0;
        while (i3 < fieldListSize3) {
            mw.visitInsn(3);
            mw.visitVarInsn(54, context.var("_asm_flag_" + (i3 / 32)));
            i3 += 32;
            continue_32 = continue_32;
            return_ = return_;
        }
        Label continue_33 = continue_32;
        Label return_2 = return_;
        int i4 = context.var("lexer");
        mw.visitVarInsn(25, i4);
        mw.visitLdcInsn(Integer.valueOf(Feature.InitStringFieldAsEmpty.mask));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "isEnabled", "(I)Z");
        mw.visitIntInsn(54, context.var("initStringFieldAsEmpty"));
        int i5 = 0;
        while (i5 < fieldListSize3) {
            FieldInfo fieldInfo2 = context.fieldInfoList[i5];
            Class<?> fieldClass2 = fieldInfo2.fieldClass;
            if (fieldClass2 == Boolean.TYPE || fieldClass2 == Byte.TYPE || fieldClass2 == Short.TYPE || fieldClass2 == Integer.TYPE) {
                end_2 = end_3;
                beanInfo = beanInfo2;
                str5 = str6;
                reset_3 = reset_4;
                super_ = super_2;
                mw.visitInsn(3);
                mw.visitVarInsn(54, context.var_asm(fieldInfo2));
                i5++;
                beanInfo2 = beanInfo;
                super_2 = super_;
                end_3 = end_2;
                reset_4 = reset_3;
                str6 = str5;
            } else {
                if (fieldClass2 == Long.TYPE) {
                    mw.visitInsn(9);
                    beanInfo = beanInfo2;
                    super_ = super_2;
                    mw.visitVarInsn(55, context.var_asm(fieldInfo2, 2));
                    end_2 = end_3;
                    str5 = str6;
                    reset_3 = reset_4;
                } else {
                    beanInfo = beanInfo2;
                    super_ = super_2;
                    if (fieldClass2 == Float.TYPE) {
                        mw.visitInsn(11);
                        mw.visitVarInsn(56, context.var_asm(fieldInfo2));
                        end_2 = end_3;
                        str5 = str6;
                        reset_3 = reset_4;
                    } else if (fieldClass2 == Double.TYPE) {
                        mw.visitInsn(14);
                        mw.visitVarInsn(57, context.var_asm(fieldInfo2, 2));
                        end_2 = end_3;
                        str5 = str6;
                        reset_3 = reset_4;
                    } else {
                        if (fieldClass2 == String.class) {
                            Label flagEnd_ = new Label();
                            Label flagElse_ = new Label();
                            end_2 = end_3;
                            mw.visitVarInsn(21, context.var("initStringFieldAsEmpty"));
                            mw.visitJumpInsn(Opcodes.IFEQ, flagElse_);
                            _setFlag(mw, context, i5);
                            mw.visitVarInsn(25, context.var("lexer"));
                            reset_3 = reset_4;
                            str5 = str6;
                            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "stringDefaultValue", "()Ljava/lang/String;");
                            mw.visitJumpInsn(Opcodes.GOTO, flagEnd_);
                            mw.visitLabel(flagElse_);
                            mw.visitInsn(1);
                            mw.visitLabel(flagEnd_);
                        } else {
                            end_2 = end_3;
                            str5 = str6;
                            reset_3 = reset_4;
                            mw.visitInsn(1);
                        }
                        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass2));
                        mw.visitVarInsn(58, context.var_asm(fieldInfo2));
                    }
                }
                i5++;
                beanInfo2 = beanInfo;
                super_2 = super_;
                end_3 = end_2;
                reset_4 = reset_3;
                str6 = str5;
            }
        }
        Label end_4 = end_3;
        String str10 = str6;
        Label reset_5 = reset_4;
        Label super_3 = super_2;
        int i6 = 0;
        while (i6 < fieldListSize3) {
            FieldInfo fieldInfo3 = context.fieldInfoList[i6];
            Class<?> fieldClass3 = fieldInfo3.fieldClass;
            Type fieldType2 = fieldInfo3.fieldType;
            Label notMatch_2 = new Label();
            if (fieldClass3 == Boolean.TYPE) {
                mw.visitVarInsn(25, context.var(str7));
                mw.visitVarInsn(25, 0);
                mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldBoolean", "([C)Z");
                mw.visitVarInsn(54, context.var_asm(fieldInfo3));
                fieldListSize = fieldListSize3;
                i = i6;
                notMatch_ = notMatch_2;
                str = str10;
            } else if (fieldClass3 == Byte.TYPE) {
                fieldListSize = fieldListSize3;
                mw.visitVarInsn(25, context.var(str7));
                mw.visitVarInsn(25, 0);
                notMatch_ = notMatch_2;
                mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                mw.visitVarInsn(54, context.var_asm(fieldInfo3));
                i = i6;
                str = str10;
            } else {
                fieldListSize = fieldListSize3;
                notMatch_ = notMatch_2;
                if (fieldClass3 == Byte.class) {
                    mw.visitVarInsn(25, context.var(str7));
                    mw.visitVarInsn(25, 0);
                    i = i6;
                    mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                    mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                    mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                    mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                    Label valueNullEnd_ = new Label();
                    mw.visitVarInsn(25, context.var(str7));
                    mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
                    mw.visitLdcInsn(5);
                    mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_);
                    mw.visitInsn(1);
                    mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                    mw.visitLabel(valueNullEnd_);
                    str = str10;
                } else {
                    i = i6;
                    if (fieldClass3 == Short.TYPE) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                        mw.visitVarInsn(54, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == Short.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        Label valueNullEnd_2 = new Label();
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
                        mw.visitLdcInsn(5);
                        mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_2);
                        mw.visitInsn(1);
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        mw.visitLabel(valueNullEnd_2);
                        str = str10;
                    } else if (fieldClass3 == Integer.TYPE) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                        mw.visitVarInsn(54, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == Integer.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldInt", "([C)I");
                        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        Label valueNullEnd_3 = new Label();
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
                        mw.visitLdcInsn(5);
                        mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_3);
                        mw.visitInsn(1);
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        mw.visitLabel(valueNullEnd_3);
                        str = str10;
                    } else if (fieldClass3 == Long.TYPE) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldLong", "([C)J");
                        mw.visitVarInsn(55, context.var_asm(fieldInfo3, 2));
                        str = str10;
                    } else if (fieldClass3 == Long.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldLong", "([C)J");
                        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        Label valueNullEnd_4 = new Label();
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
                        mw.visitLdcInsn(5);
                        mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_4);
                        mw.visitInsn(1);
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        mw.visitLabel(valueNullEnd_4);
                        str = str10;
                    } else if (fieldClass3 == Float.TYPE) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloat", "([C)F");
                        mw.visitVarInsn(56, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == Float.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloat", "([C)F");
                        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        Label valueNullEnd_5 = new Label();
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
                        mw.visitLdcInsn(5);
                        mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_5);
                        mw.visitInsn(1);
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        mw.visitLabel(valueNullEnd_5);
                        str = str10;
                    } else if (fieldClass3 == Double.TYPE) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDouble", "([C)D");
                        mw.visitVarInsn(57, context.var_asm(fieldInfo3, 2));
                        str = str10;
                    } else if (fieldClass3 == Double.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDouble", "([C)D");
                        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        Label valueNullEnd_6 = new Label();
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
                        mw.visitLdcInsn(5);
                        mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNullEnd_6);
                        mw.visitInsn(1);
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        mw.visitLabel(valueNullEnd_6);
                        str = str10;
                    } else if (fieldClass3 == String.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldString", "([C)Ljava/lang/String;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == Date.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDate", "([C)Ljava/util/Date;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == UUID.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldUUID", "([C)Ljava/util/UUID;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == BigDecimal.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldDecimal", "([C)Ljava/math/BigDecimal;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == BigInteger.class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldBigInteger", "([C)Ljava/math/BigInteger;");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == int[].class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldIntArray", "([C)[I");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == float[].class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloatArray", "([C)[F");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3 == float[][].class) {
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldFloatArray2", "([C)[[F");
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                        str = str10;
                    } else if (fieldClass3.isEnum()) {
                        mw.visitVarInsn(25, 0);
                        mw.visitVarInsn(25, context.var(str7));
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                        _getFieldDeser(context, mw, fieldInfo3);
                        str = str10;
                        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "scanEnum", str + JSONLexerBase + ";[C" + ASMUtils.desc((Class<?>) ObjectDeserializer.class) + ")Ljava/lang/Enum;");
                        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass3));
                        mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                    } else {
                        str = str10;
                        if (Collection.class.isAssignableFrom(fieldClass3)) {
                            mw.visitVarInsn(25, context.var(str7));
                            mw.visitVarInsn(25, 0);
                            mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo3), "[C");
                            Class<?> itemClass = TypeUtils.getCollectionItemClass(fieldType2);
                            if (itemClass == String.class) {
                                mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldClass3)));
                                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "scanFieldStringArray", "([CLjava/lang/Class;)" + ASMUtils.desc((Class<?>) Collection.class));
                                mw.visitVarInsn(58, context.var_asm(fieldInfo3));
                            } else {
                                i2 = i;
                                str2 = str9;
                                str3 = str8;
                                continue_2 = continue_23;
                                fieldListSize2 = fieldListSize;
                                continue_3 = continue_33;
                                str4 = str7;
                                end_ = end_4;
                                reset_ = return_2;
                                _deserialze_list_obj(context, mw, reset_5, fieldInfo3, fieldClass3, itemClass, i2);
                                if (i2 != fieldListSize2 - 1) {
                                    reset_2 = reset_5;
                                } else {
                                    Label reset_6 = reset_5;
                                    _deserialize_endCheck(context, mw, reset_6);
                                    reset_2 = reset_6;
                                }
                            }
                        } else {
                            str2 = str9;
                            str3 = str8;
                            continue_2 = continue_23;
                            continue_3 = continue_33;
                            reset_ = return_2;
                            end_ = end_4;
                            Label reset_7 = reset_5;
                            fieldListSize2 = fieldListSize;
                            i2 = i;
                            str4 = str7;
                            reset_2 = reset_7;
                            _deserialze_obj(context, mw, reset_7, fieldInfo3, fieldClass3, i2);
                            if (i2 == fieldListSize2 - 1) {
                                _deserialize_endCheck(context, mw, reset_2);
                            }
                        }
                        str10 = str;
                        reset_5 = reset_2;
                        end_4 = end_;
                        return_2 = reset_;
                        str9 = str2;
                        str8 = str3;
                        continue_23 = continue_2;
                        continue_33 = continue_3;
                        fieldListSize3 = fieldListSize2;
                        str7 = str4;
                        i6 = i2 + 1;
                    }
                }
            }
            mw.visitVarInsn(25, context.var(str7));
            mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
            Label flag_ = new Label();
            mw.visitJumpInsn(Opcodes.IFLE, flag_);
            i2 = i;
            _setFlag(mw, context, i2);
            mw.visitLabel(flag_);
            mw.visitVarInsn(25, context.var(str7));
            mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
            mw.visitInsn(89);
            mw.visitVarInsn(54, context.var(str8));
            mw.visitLdcInsn(-1);
            Label reset_8 = reset_5;
            mw.visitJumpInsn(Opcodes.IF_ICMPEQ, reset_8);
            mw.visitVarInsn(25, context.var(str7));
            mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
            Label notMatch_3 = notMatch_;
            mw.visitJumpInsn(Opcodes.IFLE, notMatch_3);
            mw.visitVarInsn(21, context.var("matchedCount"));
            mw.visitInsn(4);
            mw.visitInsn(96);
            mw.visitVarInsn(54, context.var("matchedCount"));
            mw.visitVarInsn(25, context.var(str7));
            mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
            mw.visitLdcInsn(4);
            Label end_5 = end_4;
            mw.visitJumpInsn(Opcodes.IF_ICMPEQ, end_5);
            mw.visitLabel(notMatch_3);
            if (i2 == fieldListSize - 1) {
                mw.visitVarInsn(25, context.var(str7));
                mw.visitFieldInsn(Opcodes.GETFIELD, JSONLexerBase, str8, str9);
                mw.visitLdcInsn(4);
                mw.visitJumpInsn(Opcodes.IF_ICMPNE, reset_8);
                str2 = str9;
                str3 = str8;
                end_ = end_5;
                reset_2 = reset_8;
                continue_2 = continue_23;
                continue_3 = continue_33;
                reset_ = return_2;
                fieldListSize2 = fieldListSize;
                str4 = str7;
            } else {
                str2 = str9;
                str3 = str8;
                end_ = end_5;
                reset_2 = reset_8;
                continue_2 = continue_23;
                continue_3 = continue_33;
                reset_ = return_2;
                fieldListSize2 = fieldListSize;
                str4 = str7;
            }
            str10 = str;
            reset_5 = reset_2;
            end_4 = end_;
            return_2 = reset_;
            str9 = str2;
            str8 = str3;
            continue_23 = continue_2;
            continue_33 = continue_3;
            fieldListSize3 = fieldListSize2;
            str7 = str4;
            i6 = i2 + 1;
        }
        int fieldListSize4 = fieldListSize3;
        Label return_3 = return_2;
        Label reset_9 = reset_5;
        String str11 = str10;
        mw.visitLabel(end_4);
        if (!context.clazz.isInterface() && !Modifier.isAbstract(context.clazz.getModifiers())) {
            _batchSet(context, mw);
        }
        mw.visitLabel(return_3);
        _setContext(context, mw);
        mw.visitVarInsn(25, context.var("instance"));
        Method buildMethod = context.beanInfo.buildMethod;
        if (buildMethod != null) {
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(context.getInstClass()), buildMethod.getName(), "()" + ASMUtils.desc(buildMethod.getReturnType()));
        }
        mw.visitInsn(Opcodes.ARETURN);
        mw.visitLabel(reset_9);
        _batchSet(context, mw);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, 3);
        mw.visitVarInsn(25, context.var("instance"));
        mw.visitVarInsn(21, 4);
        int flagSize = fieldListSize4 / 32;
        if (fieldListSize4 != 0 && fieldListSize4 % 32 != 0) {
            flagSize++;
        }
        if (flagSize == 1) {
            mw.visitInsn(4);
        } else {
            mw.visitIntInsn(16, flagSize);
        }
        mw.visitIntInsn(Opcodes.NEWARRAY, 10);
        for (int i7 = 0; i7 < flagSize; i7++) {
            mw.visitInsn(89);
            if (i7 == 0) {
                mw.visitInsn(3);
            } else if (i7 == 1) {
                mw.visitInsn(4);
            } else {
                mw.visitIntInsn(16, i7);
            }
            mw.visitVarInsn(21, context.var("_asm_flag_" + i7));
            mw.visitInsn(79);
        }
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "parseRest", str11 + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;Ljava/lang/Object;I[I)Ljava/lang/Object;");
        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(context.clazz));
        mw.visitInsn(Opcodes.ARETURN);
        mw.visitLabel(super_3);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, 3);
        mw.visitVarInsn(21, 4);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "deserialze", str11 + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
        mw.visitInsn(Opcodes.ARETURN);
        mw.visitMaxs(10, context.variantIndex);
        mw.visitEnd();
    }

    private void defineVarLexer(Context context, MethodVisitor mw) {
        mw.visitVarInsn(25, 1);
        mw.visitFieldInsn(Opcodes.GETFIELD, DefaultJSONParser, "lexer", ASMUtils.desc((Class<?>) JSONLexer.class));
        mw.visitTypeInsn(Opcodes.CHECKCAST, JSONLexerBase);
        mw.visitVarInsn(58, context.var("lexer"));
    }

    private void _createInstance(Context context, MethodVisitor mw) {
        JavaBeanInfo beanInfo = context.beanInfo;
        Constructor<?> defaultConstructor = beanInfo.defaultConstructor;
        if (Modifier.isPublic(defaultConstructor.getModifiers())) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(context.getInstClass()));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(defaultConstructor.getDeclaringClass()), "<init>", "()V");
        } else {
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 0);
            mw.visitFieldInsn(Opcodes.GETFIELD, ASMUtils.type(JavaBeanDeserializer.class), "clazz", "Ljava/lang/Class;");
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;");
            mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(context.getInstClass()));
        }
        mw.visitVarInsn(58, context.var("instance"));
    }

    private void _batchSet(Context context, MethodVisitor mw) {
        _batchSet(context, mw, true);
    }

    private void _batchSet(Context context, MethodVisitor mw, boolean flag) {
        int size = context.fieldInfoList.length;
        for (int i = 0; i < size; i++) {
            Label notSet_ = new Label();
            if (flag) {
                _isFlag(mw, context, i, notSet_);
            }
            FieldInfo fieldInfo = context.fieldInfoList[i];
            _loadAndSet(context, mw, fieldInfo);
            if (flag) {
                mw.visitLabel(notSet_);
            }
        }
    }

    private void _loadAndSet(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Class<?> fieldClass = fieldInfo.fieldClass;
        Type fieldType = fieldInfo.fieldType;
        if (fieldClass == Boolean.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(21, context.var_asm(fieldInfo));
            _set(context, mw, fieldInfo);
            return;
        }
        if (fieldClass == Byte.TYPE || fieldClass == Short.TYPE || fieldClass == Integer.TYPE || fieldClass == Character.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(21, context.var_asm(fieldInfo));
            _set(context, mw, fieldInfo);
            return;
        }
        if (fieldClass == Long.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(22, context.var_asm(fieldInfo, 2));
            if (fieldInfo.method != null) {
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(context.getInstClass()), fieldInfo.method.getName(), ASMUtils.desc(fieldInfo.method));
                if (!fieldInfo.method.getReturnType().equals(Void.TYPE)) {
                    mw.visitInsn(87);
                    return;
                }
                return;
            }
            mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
            return;
        }
        if (fieldClass == Float.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(23, context.var_asm(fieldInfo));
            _set(context, mw, fieldInfo);
            return;
        }
        if (fieldClass == Double.TYPE) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(24, context.var_asm(fieldInfo, 2));
            _set(context, mw, fieldInfo);
            return;
        }
        if (fieldClass == String.class) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, context.var_asm(fieldInfo));
            _set(context, mw, fieldInfo);
            return;
        }
        if (fieldClass.isEnum()) {
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, context.var_asm(fieldInfo));
            _set(context, mw, fieldInfo);
        } else {
            if (Collection.class.isAssignableFrom(fieldClass)) {
                mw.visitVarInsn(25, context.var("instance"));
                Type itemType = TypeUtils.getCollectionItemClass(fieldType);
                if (itemType == String.class) {
                    mw.visitVarInsn(25, context.var_asm(fieldInfo));
                    mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
                } else {
                    mw.visitVarInsn(25, context.var_asm(fieldInfo));
                }
                _set(context, mw, fieldInfo);
                return;
            }
            mw.visitVarInsn(25, context.var("instance"));
            mw.visitVarInsn(25, context.var_asm(fieldInfo));
            _set(context, mw, fieldInfo);
        }
    }

    private void _set(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            Class<?> declaringClass = method.getDeclaringClass();
            mw.visitMethodInsn(declaringClass.isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL, ASMUtils.type(fieldInfo.declaringClass), method.getName(), ASMUtils.desc(method));
            if (!fieldInfo.method.getReturnType().equals(Void.TYPE)) {
                mw.visitInsn(87);
                return;
            }
            return;
        }
        mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(fieldInfo.declaringClass), fieldInfo.field.getName(), ASMUtils.desc(fieldInfo.fieldClass));
    }

    private void _setContext(Context context, MethodVisitor mw) {
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var("context"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + ")V");
        Label endIf_ = new Label();
        mw.visitVarInsn(25, context.var("childContext"));
        mw.visitJumpInsn(Opcodes.IFNULL, endIf_);
        mw.visitVarInsn(25, context.var("childContext"));
        mw.visitVarInsn(25, context.var("instance"));
        mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(ParseContext.class), "object", "Ljava/lang/Object;");
        mw.visitLabel(endIf_);
    }

    private void _deserialize_endCheck(Context context, MethodVisitor mw, Label reset_) {
        mw.visitIntInsn(21, context.var("matchedCount"));
        mw.visitJumpInsn(Opcodes.IFLE, reset_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(13);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, reset_);
        _quickNextTokenComma(context, mw);
    }

    private void _deserialze_list_obj(Context context, MethodVisitor mw, Label reset_, FieldInfo fieldInfo, Class<?> fieldClass, Class<?> itemType, int i) {
        String str;
        String str2;
        String str3;
        Label _end_if = new Label();
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "matchField", "([C)Z");
        mw.visitJumpInsn(Opcodes.IFEQ, _end_if);
        _setFlag(mw, context, i);
        Label valueNotNull_ = new Label();
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(8);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, valueNotNull_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(16);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, _end_if);
        mw.visitLabel(valueNotNull_);
        Label storeCollection_ = new Label();
        Label endSet_ = new Label();
        Label lbacketNormal_ = new Label();
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(21);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, endSet_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(14);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        _newCollection(mw, fieldClass, i, true);
        mw.visitJumpInsn(Opcodes.GOTO, storeCollection_);
        mw.visitLabel(endSet_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(14);
        mw.visitJumpInsn(Opcodes.IF_ICMPEQ, lbacketNormal_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(12);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, reset_);
        _newCollection(mw, fieldClass, i, false);
        mw.visitVarInsn(58, context.var_asm(fieldInfo));
        _getCollectionFieldItemDeser(context, mw, fieldInfo, itemType);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(itemType)));
        mw.visitInsn(3);
        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(ObjectDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        mw.visitVarInsn(58, context.var("list_item_value"));
        mw.visitVarInsn(25, context.var_asm(fieldInfo));
        mw.visitVarInsn(25, context.var("list_item_value"));
        if (fieldClass.isInterface()) {
            str = "list_item_value";
            mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(fieldClass), "add", "(Ljava/lang/Object;)Z");
        } else {
            str = "list_item_value";
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(fieldClass), "add", "(Ljava/lang/Object;)Z");
        }
        mw.visitInsn(87);
        mw.visitJumpInsn(Opcodes.GOTO, _end_if);
        mw.visitLabel(lbacketNormal_);
        _newCollection(mw, fieldClass, i, false);
        mw.visitLabel(storeCollection_);
        mw.visitVarInsn(58, context.var_asm(fieldInfo));
        boolean isPrimitive = ParserConfig.isPrimitive2(fieldInfo.fieldClass);
        _getCollectionFieldItemDeser(context, mw, fieldInfo, itemType);
        if (isPrimitive) {
            mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(ObjectDeserializer.class), "getFastMatchToken", "()I");
            mw.visitVarInsn(54, context.var("fastMatchToken"));
            mw.visitVarInsn(25, context.var("lexer"));
            mw.visitVarInsn(21, context.var("fastMatchToken"));
            str2 = "nextToken";
            str3 = "(I)V";
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, str2, str3);
        } else {
            str2 = "nextToken";
            str3 = "(I)V";
            mw.visitInsn(87);
            mw.visitLdcInsn(12);
            mw.visitVarInsn(54, context.var("fastMatchToken"));
            _quickNextToken(context, mw, 12);
        }
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        mw.visitVarInsn(58, context.var("listContext"));
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var_asm(fieldInfo));
        mw.visitLdcInsn(fieldInfo.name);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)" + ASMUtils.desc((Class<?>) ParseContext.class));
        mw.visitInsn(87);
        Label loop_ = new Label();
        Label loop_end_ = new Label();
        mw.visitInsn(3);
        mw.visitVarInsn(54, context.var("i"));
        mw.visitLabel(loop_);
        mw.visitVarInsn(25, context.var("lexer"));
        String str4 = str2;
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(15);
        mw.visitJumpInsn(Opcodes.IF_ICMPEQ, loop_end_);
        mw.visitVarInsn(25, 0);
        String str5 = str3;
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(itemType)));
        mw.visitVarInsn(21, context.var("i"));
        mw.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(ObjectDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        String str6 = str;
        mw.visitVarInsn(58, context.var(str6));
        mw.visitIincInsn(context.var("i"), 1);
        mw.visitVarInsn(25, context.var_asm(fieldInfo));
        mw.visitVarInsn(25, context.var(str6));
        if (fieldClass.isInterface()) {
            mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(fieldClass), "add", "(Ljava/lang/Object;)Z");
        } else {
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(fieldClass), "add", "(Ljava/lang/Object;)Z");
        }
        mw.visitInsn(87);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var_asm(fieldInfo));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "checkListResolve", "(Ljava/util/Collection;)V");
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(16);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, loop_);
        if (isPrimitive) {
            mw.visitVarInsn(25, context.var("lexer"));
            mw.visitVarInsn(21, context.var("fastMatchToken"));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, str4, str5);
        } else {
            _quickNextToken(context, mw, 12);
        }
        mw.visitJumpInsn(Opcodes.GOTO, loop_);
        mw.visitLabel(loop_end_);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, context.var("listContext"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setContext", "(" + ASMUtils.desc((Class<?>) ParseContext.class) + ")V");
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "token", "()I");
        mw.visitLdcInsn(15);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, reset_);
        _quickNextTokenComma(context, mw);
        mw.visitLabel(_end_if);
    }

    private void _quickNextToken(Context context, MethodVisitor mw, int token) {
        Label quickElse_ = new Label();
        Label quickEnd_ = new Label();
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
        if (token == 12) {
            mw.visitVarInsn(16, 123);
        } else if (token == 14) {
            mw.visitVarInsn(16, 91);
        } else {
            throw new IllegalStateException();
        }
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElse_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        mw.visitInsn(87);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(Integer.valueOf(token));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        mw.visitLabel(quickElse_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(Integer.valueOf(token));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "(I)V");
        mw.visitLabel(quickEnd_);
    }

    private void _quickNextTokenComma(Context context, MethodVisitor mw) {
        Label quickElse_ = new Label();
        Label quickElseIf0_ = new Label();
        Label quickElseIf1_ = new Label();
        Label quickElseIf2_ = new Label();
        Label quickEnd_ = new Label();
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "getCurrent", "()C");
        mw.visitInsn(89);
        mw.visitVarInsn(54, context.var("ch"));
        mw.visitVarInsn(16, 44);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIf0_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        mw.visitInsn(87);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(16);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        mw.visitLabel(quickElseIf0_);
        mw.visitVarInsn(21, context.var("ch"));
        mw.visitVarInsn(16, 125);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIf1_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        mw.visitInsn(87);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(13);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        mw.visitLabel(quickElseIf1_);
        mw.visitVarInsn(21, context.var("ch"));
        mw.visitVarInsn(16, 93);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElseIf2_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "next", "()C");
        mw.visitInsn(87);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(15);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        mw.visitLabel(quickElseIf2_);
        mw.visitVarInsn(21, context.var("ch"));
        mw.visitVarInsn(16, 26);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, quickElse_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitLdcInsn(20);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "setToken", "(I)V");
        mw.visitJumpInsn(Opcodes.GOTO, quickEnd_);
        mw.visitLabel(quickElse_);
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "nextToken", "()V");
        mw.visitLabel(quickEnd_);
    }

    private void _getCollectionFieldItemDeser(Context context, MethodVisitor mw, FieldInfo fieldInfo, Class<?> itemType) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        mw.visitJumpInsn(Opcodes.IFNONNULL, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc((Class<?>) ParserConfig.class));
        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(itemType)));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(ParserConfig.class), "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        mw.visitFieldInsn(Opcodes.PUTFIELD, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
    }

    private void _newCollection(MethodVisitor mw, Class<?> fieldClass, int i, boolean set) {
        if (fieldClass.isAssignableFrom(ArrayList.class) && !set) {
            mw.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList");
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(LinkedList.class) && !set) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(LinkedList.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(LinkedList.class), "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(HashSet.class)) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(HashSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(TreeSet.class)) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(TreeSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(TreeSet.class), "<init>", "()V");
        } else if (fieldClass.isAssignableFrom(LinkedHashSet.class)) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(LinkedHashSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(LinkedHashSet.class), "<init>", "()V");
        } else if (set) {
            mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(HashSet.class));
            mw.visitInsn(89);
            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(HashSet.class), "<init>", "()V");
        } else {
            mw.visitVarInsn(25, 0);
            mw.visitLdcInsn(Integer.valueOf(i));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            mw.visitMethodInsn(Opcodes.INVOKESTATIC, ASMUtils.type(TypeUtils.class), "createCollection", "(Ljava/lang/reflect/Type;)Ljava/util/Collection;");
        }
        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
    }

    private void _deserialze_obj(Context context, MethodVisitor mw, Label reset_, FieldInfo fieldInfo, Class<?> fieldClass, int i) {
        Label matched_ = new Label();
        Label _end_if = new Label();
        mw.visitVarInsn(25, context.var("lexer"));
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldName(fieldInfo), "[C");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, JSONLexerBase, "matchField", "([C)Z");
        mw.visitJumpInsn(Opcodes.IFNE, matched_);
        mw.visitInsn(1);
        mw.visitVarInsn(58, context.var_asm(fieldInfo));
        mw.visitJumpInsn(Opcodes.GOTO, _end_if);
        mw.visitLabel(matched_);
        _setFlag(mw, context, i);
        mw.visitVarInsn(21, context.var("matchedCount"));
        mw.visitInsn(4);
        mw.visitInsn(96);
        mw.visitVarInsn(54, context.var("matchedCount"));
        _deserObject(context, mw, fieldInfo, fieldClass, i);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getResolveStatus", "()I");
        mw.visitLdcInsn(1);
        mw.visitJumpInsn(Opcodes.IF_ICMPNE, _end_if);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getLastResolveTask", "()" + ASMUtils.desc((Class<?>) DefaultJSONParser.ResolveTask.class));
        mw.visitVarInsn(58, context.var("resolveTask"));
        mw.visitVarInsn(25, context.var("resolveTask"));
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getContext", "()" + ASMUtils.desc((Class<?>) ParseContext.class));
        mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "ownerContext", ASMUtils.desc((Class<?>) ParseContext.class));
        mw.visitVarInsn(25, context.var("resolveTask"));
        mw.visitVarInsn(25, 0);
        mw.visitLdcInsn(fieldInfo.name);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldDeserializer", "(Ljava/lang/String;)" + ASMUtils.desc((Class<?>) FieldDeserializer.class));
        mw.visitFieldInsn(Opcodes.PUTFIELD, ASMUtils.type(DefaultJSONParser.ResolveTask.class), "fieldDeserializer", ASMUtils.desc((Class<?>) FieldDeserializer.class));
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(0);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "setResolveStatus", "(I)V");
        mw.visitLabel(_end_if);
    }

    private void _deserObject(Context context, MethodVisitor mw, FieldInfo fieldInfo, Class<?> fieldClass, int i) {
        _getFieldDeser(context, mw, fieldInfo);
        Label instanceOfElse_ = new Label();
        Label instanceOfEnd_ = new Label();
        if ((fieldInfo.parserFeatures & Feature.SupportArrayToBean.mask) != 0) {
            mw.visitInsn(89);
            mw.visitTypeInsn(Opcodes.INSTANCEOF, ASMUtils.type(JavaBeanDeserializer.class));
            mw.visitJumpInsn(Opcodes.IFEQ, instanceOfElse_);
            mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(JavaBeanDeserializer.class));
            mw.visitVarInsn(25, 1);
            if (fieldInfo.fieldType instanceof Class) {
                mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            } else {
                mw.visitVarInsn(25, 0);
                mw.visitLdcInsn(Integer.valueOf(i));
                mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
            }
            mw.visitLdcInsn(fieldInfo.name);
            mw.visitLdcInsn(Integer.valueOf(fieldInfo.parserFeatures));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;I)Ljava/lang/Object;");
            mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
            mw.visitVarInsn(58, context.var_asm(fieldInfo));
            mw.visitJumpInsn(Opcodes.GOTO, instanceOfEnd_);
            mw.visitLabel(instanceOfElse_);
        }
        mw.visitVarInsn(25, 1);
        if (fieldInfo.fieldType instanceof Class) {
            mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        } else {
            mw.visitVarInsn(25, 0);
            mw.visitLdcInsn(Integer.valueOf(i));
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(JavaBeanDeserializer.class), "getFieldType", "(I)Ljava/lang/reflect/Type;");
        }
        mw.visitLdcInsn(fieldInfo.name);
        mw.visitMethodInsn(Opcodes.INVOKEINTERFACE, ASMUtils.type(ObjectDeserializer.class), "deserialze", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;Ljava/lang/Object;)Ljava/lang/Object;");
        mw.visitTypeInsn(Opcodes.CHECKCAST, ASMUtils.type(fieldClass));
        mw.visitVarInsn(58, context.var_asm(fieldInfo));
        mw.visitLabel(instanceOfEnd_);
    }

    private void _getFieldDeser(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldDeserName(fieldInfo), ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        mw.visitJumpInsn(Opcodes.IFNONNULL, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, DefaultJSONParser, "getConfig", "()" + ASMUtils.desc((Class<?>) ParserConfig.class));
        mw.visitLdcInsn(com.alibaba.fastjson.asm.Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ASMUtils.type(ParserConfig.class), "getDeserializer", "(Ljava/lang/reflect/Type;)" + ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        mw.visitFieldInsn(Opcodes.PUTFIELD, context.className, context.fieldDeserName(fieldInfo), ASMUtils.desc((Class<?>) ObjectDeserializer.class));
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(Opcodes.GETFIELD, context.className, context.fieldDeserName(fieldInfo), ASMUtils.desc((Class<?>) ObjectDeserializer.class));
    }

    static class Context {
        static final int fieldName = 3;
        static final int parser = 1;
        static final int type = 2;
        private final JavaBeanInfo beanInfo;
        private final String className;
        private final Class<?> clazz;
        private FieldInfo[] fieldInfoList;
        private int variantIndex;
        private final Map<String, Integer> variants = new HashMap();

        public Context(String className, ParserConfig config, JavaBeanInfo beanInfo, int initVariantIndex) {
            this.variantIndex = -1;
            this.className = className;
            this.clazz = beanInfo.clazz;
            this.variantIndex = initVariantIndex;
            this.beanInfo = beanInfo;
            this.fieldInfoList = beanInfo.fields;
        }

        public Class<?> getInstClass() {
            Class<?> instClass = this.beanInfo.builderClass;
            if (instClass == null) {
                return this.clazz;
            }
            return instClass;
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

        public int var_asm(FieldInfo fieldInfo) {
            return var(fieldInfo.name + "_asm");
        }

        public int var_asm(FieldInfo fieldInfo, int increment) {
            return var(fieldInfo.name + "_asm", increment);
        }

        public String fieldName(FieldInfo fieldInfo) {
            return validIdent(fieldInfo.name) ? fieldInfo.name + "_asm_prefix__" : "asm_field_" + TypeUtils.fnv1a_64_extract(fieldInfo.name);
        }

        public String fieldDeserName(FieldInfo fieldInfo) {
            return validIdent(fieldInfo.name) ? fieldInfo.name + "_asm_deser__" : "_asm_deser__" + TypeUtils.fnv1a_64_extract(fieldInfo.name);
        }

        boolean validIdent(String name) {
            for (int i = 0; i < name.length(); i++) {
                char ch = name.charAt(i);
                if (ch == 0) {
                    if (!IOUtils.firstIdentifier(ch)) {
                        return false;
                    }
                } else if (!IOUtils.isIdent(ch)) {
                    return false;
                }
            }
            return true;
        }
    }

    private void _init(ClassWriter cw, Context context) {
        int size = context.fieldInfoList.length;
        for (int i = 0; i < size; i++) {
            FieldWriter fw = new FieldWriter(cw, 1, context.fieldName(context.fieldInfoList[i]), "[C");
            fw.visitEnd();
        }
        int size2 = context.fieldInfoList.length;
        for (int i2 = 0; i2 < size2; i2++) {
            FieldInfo fieldInfo = context.fieldInfoList[i2];
            Class<?> fieldClass = fieldInfo.fieldClass;
            if (!fieldClass.isPrimitive()) {
                if (Collection.class.isAssignableFrom(fieldClass)) {
                    FieldWriter fw2 = new FieldWriter(cw, 1, fieldInfo.name + "_asm_list_item_deser__", ASMUtils.desc((Class<?>) ObjectDeserializer.class));
                    fw2.visitEnd();
                } else {
                    FieldWriter fw3 = new FieldWriter(cw, 1, context.fieldDeserName(fieldInfo), ASMUtils.desc((Class<?>) ObjectDeserializer.class));
                    fw3.visitEnd();
                }
            }
        }
        MethodVisitor mw = new MethodWriter(cw, 1, "<init>", "(" + ASMUtils.desc((Class<?>) ParserConfig.class) + ASMUtils.desc((Class<?>) JavaBeanInfo.class) + ")V", null, null);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(JavaBeanDeserializer.class), "<init>", "(" + ASMUtils.desc((Class<?>) ParserConfig.class) + ASMUtils.desc((Class<?>) JavaBeanInfo.class) + ")V");
        int size3 = context.fieldInfoList.length;
        for (int i3 = 0; i3 < size3; i3++) {
            FieldInfo fieldInfo2 = context.fieldInfoList[i3];
            mw.visitVarInsn(25, 0);
            mw.visitLdcInsn("\"" + fieldInfo2.name + "\":");
            mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "toCharArray", "()[C");
            mw.visitFieldInsn(Opcodes.PUTFIELD, context.className, context.fieldName(fieldInfo2), "[C");
        }
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(4, 4);
        mw.visitEnd();
    }

    private void _createInstance(ClassWriter cw, Context context) {
        Constructor<?> defaultConstructor = context.beanInfo.defaultConstructor;
        if (!Modifier.isPublic(defaultConstructor.getModifiers())) {
            return;
        }
        MethodVisitor mw = new MethodWriter(cw, 1, "createInstance", "(L" + DefaultJSONParser + ";Ljava/lang/reflect/Type;)Ljava/lang/Object;", null, null);
        mw.visitTypeInsn(Opcodes.NEW, ASMUtils.type(context.getInstClass()));
        mw.visitInsn(89);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, ASMUtils.type(context.getInstClass()), "<init>", "()V");
        mw.visitInsn(Opcodes.ARETURN);
        mw.visitMaxs(3, 3);
        mw.visitEnd();
    }
}