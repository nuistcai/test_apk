package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ThrowableDeserializer extends JavaBeanDeserializer {
    public ThrowableDeserializer(ParserConfig mapping, Class<?> clazz) {
        super(mapping, clazz, clazz);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) throws Throwable {
        Throwable th;
        Object obj2;
        Object obj3;
        String strStringVal;
        ThrowableDeserializer throwableDeserializer = this;
        DefaultJSONParser defaultJSONParser2 = defaultJSONParser;
        JSONLexer jSONLexer = defaultJSONParser2.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (defaultJSONParser.getResolveStatus() == 2) {
            defaultJSONParser2.setResolveStatus(0);
        } else if (jSONLexer.token() != 12) {
            throw new JSONException("syntax error");
        }
        Throwable th2 = null;
        Class<?> cls = null;
        if (type != null && (type instanceof Class)) {
            Class<?> cls2 = (Class) type;
            if (Throwable.class.isAssignableFrom(cls2)) {
                cls = cls2;
            }
        }
        String str = null;
        StackTraceElement[] stackTraceElementArr = null;
        Map<String, Object> map = null;
        while (true) {
            String strScanSymbol = jSONLexer.scanSymbol(defaultJSONParser.getSymbolTable());
            if (strScanSymbol == null) {
                if (jSONLexer.token() == 13) {
                    jSONLexer.nextToken(16);
                    th = th2;
                    break;
                }
                if (jSONLexer.token() != 16 || !jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            jSONLexer.nextTokenWithColon(4);
            if (JSON.DEFAULT_TYPE_KEY.equals(strScanSymbol)) {
                if (jSONLexer.token() == 4) {
                    Class<?> clsCheckAutoType = defaultJSONParser.getConfig().checkAutoType(jSONLexer.stringVal(), Throwable.class, jSONLexer.getFeatures());
                    jSONLexer.nextToken(16);
                    cls = clsCheckAutoType;
                    obj3 = null;
                } else {
                    throw new JSONException("syntax error");
                }
            } else if ("message".equals(strScanSymbol)) {
                if (jSONLexer.token() != 8) {
                    if (jSONLexer.token() == 4) {
                        strStringVal = jSONLexer.stringVal();
                    } else {
                        throw new JSONException("syntax error");
                    }
                } else {
                    strStringVal = null;
                }
                jSONLexer.nextToken();
                str = strStringVal;
                obj3 = null;
            } else if ("cause".equals(strScanSymbol)) {
                obj3 = null;
                th2 = (Throwable) throwableDeserializer.deserialze(defaultJSONParser2, null, "cause");
            } else {
                obj3 = null;
                if ("stackTrace".equals(strScanSymbol)) {
                    stackTraceElementArr = (StackTraceElement[]) defaultJSONParser2.parseObject((Class) StackTraceElement[].class);
                } else {
                    if (map == null) {
                        map = new HashMap();
                    }
                    map.put(strScanSymbol, defaultJSONParser.parse());
                }
            }
            if (jSONLexer.token() == 13) {
                jSONLexer.nextToken(16);
                th = th2;
                break;
            }
            throwableDeserializer = this;
            defaultJSONParser2 = defaultJSONParser;
        }
        if (cls == null) {
            obj2 = (T) new Exception(str, th);
        } else {
            if (!Throwable.class.isAssignableFrom(cls)) {
                throw new JSONException("type not match, not Throwable. " + cls.getName());
            }
            try {
                Throwable thCreateException = throwableDeserializer.createException(str, th, cls);
                if (thCreateException != null) {
                    obj2 = (T) thCreateException;
                } else {
                    obj2 = (T) new Exception(str, th);
                }
            } catch (Exception e) {
                throw new JSONException("create instance error", e);
            }
        }
        if (stackTraceElementArr != null) {
            ((Throwable) obj2).setStackTrace(stackTraceElementArr);
        }
        if (map != null) {
            JavaBeanDeserializer javaBeanDeserializer = null;
            if (cls != null) {
                if (cls == throwableDeserializer.clazz) {
                    javaBeanDeserializer = this;
                } else {
                    ObjectDeserializer deserializer = defaultJSONParser.getConfig().getDeserializer(cls);
                    if (deserializer instanceof JavaBeanDeserializer) {
                        javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                    }
                }
            }
            if (javaBeanDeserializer != null) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    FieldDeserializer fieldDeserializer = javaBeanDeserializer.getFieldDeserializer(key);
                    if (fieldDeserializer != null) {
                        FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                        if (!fieldInfo.fieldClass.isInstance(value)) {
                            value = TypeUtils.cast(value, fieldInfo.fieldType, defaultJSONParser.getConfig());
                        }
                        fieldDeserializer.setValue(obj2, value);
                    }
                }
            }
        }
        return (T) obj2;
    }

    private Throwable createException(String message, Throwable cause, Class<?> exClass) throws Exception {
        Constructor<?> defaultConstructor = null;
        Constructor<?> messageConstructor = null;
        Constructor<?> causeConstructor = null;
        for (Constructor<?> constructor : exClass.getConstructors()) {
            Class<?>[] types = constructor.getParameterTypes();
            if (types.length == 0) {
                defaultConstructor = constructor;
            } else if (types.length == 1 && types[0] == String.class) {
                messageConstructor = constructor;
            } else if (types.length == 2 && types[0] == String.class && types[1] == Throwable.class) {
                causeConstructor = constructor;
            }
        }
        if (causeConstructor != null) {
            return (Throwable) causeConstructor.newInstance(message, cause);
        }
        if (messageConstructor != null) {
            return (Throwable) messageConstructor.newInstance(message);
        }
        if (defaultConstructor != null) {
            return (Throwable) defaultConstructor.newInstance(new Object[0]);
        }
        return null;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }
}