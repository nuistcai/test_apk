package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class MapDeserializer extends ContextObjectDeserializer implements ObjectDeserializer {
    public static MapDeserializer instance = new MapDeserializer();

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) {
        Map<Object, Object> mapCreateMap;
        if (type == JSONObject.class && defaultJSONParser.getFieldTypeResolver() == null) {
            return (T) defaultJSONParser.parseObject();
        }
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken(16);
            return null;
        }
        boolean z = (type instanceof Class) && "java.util.Collections$UnmodifiableMap".equals(((Class) type).getName());
        if ((jSONLexer.getFeatures() & Feature.OrderedField.mask) != 0) {
            mapCreateMap = createMap(type, jSONLexer.getFeatures());
        } else {
            mapCreateMap = createMap(type);
        }
        ParseContext context = defaultJSONParser.getContext();
        try {
            defaultJSONParser.setContext(context, mapCreateMap, obj);
            Map map = (T) deserialze(defaultJSONParser, type, obj, mapCreateMap, i);
            if (z) {
                map = (T) Collections.unmodifiableMap(map);
            }
            return (T) map;
        } finally {
            defaultJSONParser.setContext(context);
        }
    }

    protected Object deserialze(DefaultJSONParser parser, Type type, Object fieldName, Map map) {
        return deserialze(parser, type, fieldName, map, 0);
    }

    protected Object deserialze(DefaultJSONParser parser, Type type, Object fieldName, Map map, int features) {
        Type valueType;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type keyType = parameterizedType.getActualTypeArguments()[0];
            if (map.getClass().getName().equals("org.springframework.util.LinkedMultiValueMap")) {
                valueType = List.class;
            } else {
                valueType = parameterizedType.getActualTypeArguments()[1];
            }
            if (String.class == keyType) {
                return parseMap(parser, (Map<String, Object>) map, valueType, fieldName, features);
            }
            return parseMap(parser, (Map<Object, Object>) map, keyType, valueType, fieldName);
        }
        return parser.parseObject(map, fieldName);
    }

    public static Map parseMap(DefaultJSONParser parser, Map<String, Object> map, Type valueType, Object fieldName) {
        return parseMap(parser, map, valueType, fieldName, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:120:0x0266, code lost:
    
        r17.setContext(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x026a, code lost:
    
        return r18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Map parseMap(DefaultJSONParser parser, Map<String, Object> map, Type valueType, Object fieldName, int features) throws Throwable {
        String key;
        Class<?> clazz;
        Object value;
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        Class<?> cls = null;
        if (token != 12) {
            if (token == 4) {
                String stringVal = lexer.stringVal();
                if (stringVal.length() == 0 || stringVal.equals("null")) {
                    return null;
                }
            }
            String msg = "syntax error, expect {, actual " + lexer.tokenName();
            if (fieldName instanceof String) {
                msg = (msg + ", fieldName ") + fieldName;
            }
            String msg2 = (msg + ", ") + lexer.info();
            if (token != 4) {
                JSONArray array = new JSONArray();
                parser.parseArray(array, fieldName);
                if (array.size() == 1) {
                    Object first = array.get(0);
                    if (first instanceof JSONObject) {
                        return (JSONObject) first;
                    }
                }
            }
            throw new JSONException(msg2);
        }
        ParseContext context = parser.getContext();
        int i = 0;
        while (true) {
            try {
                lexer.skipWhitespace();
                char ch = lexer.getCurrent();
                if (lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (ch == ',') {
                        lexer.next();
                        lexer.skipWhitespace();
                        ch = lexer.getCurrent();
                    }
                }
                if (ch == '\"') {
                    key = lexer.scanSymbol(parser.getSymbolTable(), Typography.quote);
                    lexer.skipWhitespace();
                    if (lexer.getCurrent() != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos());
                    }
                } else {
                    if (ch == '}') {
                        lexer.next();
                        lexer.resetStringPosition();
                        lexer.nextToken(16);
                        parser.setContext(context);
                        return map;
                    }
                    if (ch == '\'') {
                        if (!lexer.isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("syntax error");
                        }
                        key = lexer.scanSymbol(parser.getSymbolTable(), '\'');
                        lexer.skipWhitespace();
                        if (lexer.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + lexer.pos());
                        }
                    } else {
                        if (!lexer.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                            throw new JSONException("syntax error");
                        }
                        key = lexer.scanSymbolUnQuoted(parser.getSymbolTable());
                        lexer.skipWhitespace();
                        char ch2 = lexer.getCurrent();
                        if (ch2 != ':') {
                            throw new JSONException("expect ':' at " + lexer.pos() + ", actual " + ch2);
                        }
                    }
                }
                lexer.next();
                lexer.skipWhitespace();
                lexer.getCurrent();
                lexer.resetStringPosition();
                if (key == JSON.DEFAULT_TYPE_KEY) {
                    try {
                        if (!lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                            try {
                                if (!Feature.isEnabled(features, Feature.DisableSpecialKeyDetect)) {
                                    String typeName = lexer.scanSymbol(parser.getSymbolTable(), Typography.quote);
                                    ParserConfig config = parser.getConfig();
                                    if (typeName.equals("java.util.HashMap")) {
                                        clazz = HashMap.class;
                                    } else if (typeName.equals("java.util.LinkedHashMap")) {
                                        clazz = LinkedHashMap.class;
                                    } else if (config.isSafeMode()) {
                                        clazz = HashMap.class;
                                    } else {
                                        try {
                                            clazz = config.checkAutoType(typeName, cls, lexer.getFeatures());
                                        } catch (JSONException e) {
                                            clazz = HashMap.class;
                                        }
                                    }
                                    if (!Map.class.isAssignableFrom(clazz)) {
                                        ObjectDeserializer deserializer = config.getDeserializer(clazz);
                                        lexer.nextToken(16);
                                        parser.setResolveStatus(2);
                                        if (context != null && !(fieldName instanceof Integer)) {
                                            parser.popContext();
                                        }
                                        Map map2 = (Map) deserializer.deserialze(parser, clazz, fieldName);
                                        parser.setContext(context);
                                        return map2;
                                    }
                                    lexer.nextToken(16);
                                    if (lexer.token() == 13) {
                                        lexer.nextToken(16);
                                        parser.setContext(context);
                                        return map;
                                    }
                                }
                                i++;
                                cls = null;
                            } catch (Throwable th) {
                                th = th;
                                parser.setContext(context);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
                lexer.nextToken();
                if (i != 0) {
                    parser.setContext(context);
                }
                if (lexer.token() == 8) {
                    value = null;
                    lexer.nextToken();
                } else {
                    try {
                        value = parser.parseObject(valueType, key);
                    } catch (Throwable th3) {
                        th = th3;
                        parser.setContext(context);
                        throw th;
                    }
                }
                map.put(key, value);
                parser.checkMapResolve(map, key);
                parser.setContext(context, value, key);
                parser.setContext(context);
                int tok = lexer.token();
                if (tok == 20 || tok == 15) {
                    break;
                }
                if (tok == 13) {
                    lexer.nextToken();
                    parser.setContext(context);
                    return map;
                }
                i++;
                cls = null;
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }

    public static Object parseMap(DefaultJSONParser parser, Map<Object, Object> map, Type keyType, Type valueType, Object fieldName) {
        Object key;
        JSONLexer lexer = parser.lexer;
        if (lexer.token() != 12 && lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + lexer.tokenName());
        }
        ObjectDeserializer keyDeserializer = parser.getConfig().getDeserializer(keyType);
        ObjectDeserializer valueDeserializer = parser.getConfig().getDeserializer(valueType);
        lexer.nextToken(keyDeserializer.getFastMatchToken());
        ParseContext context = parser.getContext();
        while (lexer.token() != 13) {
            try {
                if (lexer.token() == 4 && lexer.isRef() && !lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    Object object = null;
                    lexer.nextTokenWithColon(4);
                    if (lexer.token() != 4) {
                        throw new JSONException("illegal ref, " + JSONToken.name(lexer.token()));
                    }
                    String ref = lexer.stringVal();
                    if ("..".equals(ref)) {
                        ParseContext parentContext = context.parent;
                        object = parentContext.object;
                    } else if ("$".equals(ref)) {
                        ParseContext rootContext = context;
                        while (rootContext.parent != null) {
                            rootContext = rootContext.parent;
                        }
                        object = rootContext.object;
                    } else {
                        parser.addResolveTask(new DefaultJSONParser.ResolveTask(context, ref));
                        parser.setResolveStatus(1);
                    }
                    lexer.nextToken(13);
                    if (lexer.token() != 13) {
                        throw new JSONException("illegal ref");
                    }
                    lexer.nextToken(16);
                    return object;
                }
                if (map.size() == 0 && lexer.token() == 4 && JSON.DEFAULT_TYPE_KEY.equals(lexer.stringVal()) && !lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    lexer.nextTokenWithColon(4);
                    lexer.nextToken(16);
                    if (lexer.token() == 13) {
                        lexer.nextToken();
                        return map;
                    }
                    lexer.nextToken(keyDeserializer.getFastMatchToken());
                }
                if (lexer.token() == 4 && (keyDeserializer instanceof JavaBeanDeserializer)) {
                    String keyStrValue = lexer.stringVal();
                    lexer.nextToken();
                    DefaultJSONParser keyParser = new DefaultJSONParser(keyStrValue, parser.getConfig(), parser.getLexer().getFeatures());
                    keyParser.setDateFormat(parser.getDateFomartPattern());
                    key = keyDeserializer.deserialze(keyParser, keyType, null);
                } else {
                    key = keyDeserializer.deserialze(parser, keyType, null);
                }
                if (lexer.token() != 17) {
                    throw new JSONException("syntax error, expect :, actual " + lexer.token());
                }
                lexer.nextToken(valueDeserializer.getFastMatchToken());
                Object value = valueDeserializer.deserialze(parser, valueType, key);
                parser.checkMapResolve(map, key);
                map.put(key, value);
                if (lexer.token() == 16) {
                    lexer.nextToken(keyDeserializer.getFastMatchToken());
                }
            } finally {
                parser.setContext(context);
            }
        }
        lexer.nextToken(16);
        return map;
    }

    public Map<Object, Object> createMap(Type type) {
        return createMap(type, JSON.DEFAULT_GENERATE_FEATURE);
    }

    public Map<Object, Object> createMap(Type type, int featrues) {
        if (type == Properties.class) {
            return new Properties();
        }
        if (type == Hashtable.class) {
            return new Hashtable();
        }
        if (type == IdentityHashMap.class) {
            return new IdentityHashMap();
        }
        if (type == SortedMap.class || type == TreeMap.class) {
            return new TreeMap();
        }
        if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
            return new ConcurrentHashMap();
        }
        if (type == Map.class) {
            return (Feature.OrderedField.mask & featrues) != 0 ? new LinkedHashMap() : new HashMap();
        }
        if (type == HashMap.class) {
            return new HashMap();
        }
        if (type == LinkedHashMap.class) {
            return new LinkedHashMap();
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (EnumMap.class.equals(rawType)) {
                Type[] actualArgs = parameterizedType.getActualTypeArguments();
                return new EnumMap((Class) actualArgs[0]);
            }
            return createMap(rawType, featrues);
        }
        Class<?> clazz = (Class) type;
        if (clazz.isInterface()) {
            throw new JSONException("unsupport type " + type);
        }
        if ("java.util.Collections$UnmodifiableMap".equals(clazz.getName())) {
            return new HashMap();
        }
        try {
            return (Map) clazz.newInstance();
        } catch (Exception e) {
            throw new JSONException("unsupport type " + type, e);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }
}