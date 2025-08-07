package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessable;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses = new HashSet();
    private String[] autoTypeAccept;
    private boolean autoTypeEnable;
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected FieldTypeResolver fieldTypeResolver;
    public final Object input;
    protected transient BeanContext lastBeanContext;
    public final JSONLexer lexer;
    private int objectKeyLevel;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    static {
        Class<?>[] classes = {Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class, String.class};
        primitiveClasses.addAll(Arrays.asList(classes));
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormatPattern = dateFormat;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        setDateFormat(dateFormat);
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DefaultJSONParser(String input) {
        this(input, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String input, ParserConfig config) {
        this(input, new JSONScanner(input, JSON.DEFAULT_PARSER_FEATURE), config);
    }

    public DefaultJSONParser(String input, ParserConfig config, int features) {
        this(input, new JSONScanner(input, features), config);
    }

    public DefaultJSONParser(char[] input, int length, ParserConfig config, int features) {
        this(input, new JSONScanner(input, length, features), config);
    }

    public DefaultJSONParser(JSONLexer lexer) {
        this(lexer, ParserConfig.getGlobalInstance());
    }

    public DefaultJSONParser(JSONLexer lexer, ParserConfig config) {
        this((Object) null, lexer, config);
    }

    public DefaultJSONParser(Object input, JSONLexer lexer, ParserConfig config) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.objectKeyLevel = 0;
        this.autoTypeAccept = null;
        this.lexer = lexer;
        this.input = input;
        this.config = config;
        this.symbolTable = config.symbolTable;
        int ch = lexer.getCurrent();
        if (ch == 123) {
            lexer.next();
            ((JSONLexerBase) lexer).token = 12;
        } else if (ch == 91) {
            lexer.next();
            ((JSONLexerBase) lexer).token = 14;
        } else {
            lexer.nextToken();
        }
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public String getInput() {
        if (this.input instanceof char[]) {
            return new String((char[]) this.input);
        }
        return this.input.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:156:0x02e1, code lost:
    
        r5.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02ec, code lost:
    
        if (r5.token() != 13) goto L180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02ee, code lost:
    
        r5.nextToken(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02f1, code lost:
    
        r0 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x02fa, code lost:
    
        if ((r23.config.getDeserializer(r8) instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02fc, code lost:
    
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r24, (java.lang.Class<java.lang.Object>) r8, r23.config);
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0303, code lost:
    
        if (r0 != null) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0307, code lost:
    
        if (r8 != java.lang.Cloneable.class) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0309, code lost:
    
        r0 = new java.util.HashMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0316, code lost:
    
        if ("java.util.Collections$EmptyMap".equals(r0) == false) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0318, code lost:
    
        r0 = java.util.Collections.emptyMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0324, code lost:
    
        if ("java.util.Collections$UnmodifiableMap".equals(r0) == false) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0326, code lost:
    
        r0 = java.util.Collections.unmodifiableMap(new java.util.HashMap());
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0335, code lost:
    
        r0 = r8.newInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0336, code lost:
    
        setContext(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x033a, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x033b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0343, code lost:
    
        throw new com.alibaba.fastjson.JSONException("create instance error", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0344, code lost:
    
        setResolveStatus(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x034a, code lost:
    
        if (r23.context == null) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x034c, code lost:
    
        if (r3 == null) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x0350, code lost:
    
        if ((r3 instanceof java.lang.Integer) != false) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0358, code lost:
    
        if ((r23.context.fieldName instanceof java.lang.Integer) != false) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x035a, code lost:
    
        popContext();
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0361, code lost:
    
        if (r24.size() <= 0) goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0363, code lost:
    
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r24, (java.lang.Class<java.lang.Object>) r8, r23.config);
        setResolveStatus(0);
        parseObject(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x0371, code lost:
    
        setContext(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x0374, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0375, code lost:
    
        r0 = r23.config.getDeserializer(r8);
        r4 = r0.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0385, code lost:
    
        if (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class.isAssignableFrom(r4) == false) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x0389, code lost:
    
        if (r4 == com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x038d, code lost:
    
        if (r4 == com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.class) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x038f, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x0396, code lost:
    
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer) == false) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0398, code lost:
    
        setResolveStatus(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x039c, code lost:
    
        r14 = r0.deserialze(r23, r8, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x03a1, code lost:
    
        setContext(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x03a4, code lost:
    
        return r14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object parseObject(Map object, Object fieldName) throws Throwable {
        char ch;
        Object key;
        String str;
        Map input;
        Object value;
        Class<?> clazz;
        Class<?> clazz2;
        Object obj = fieldName;
        String str2 = "parse number key error";
        JSONLexer lexer = this.lexer;
        if (lexer.token() == 8) {
            lexer.nextToken();
            return null;
        }
        if (lexer.token() == 13) {
            lexer.nextToken();
            return object;
        }
        if (lexer.token() == 4 && lexer.stringVal().length() == 0) {
            lexer.nextToken();
            return object;
        }
        if (lexer.token() != 12 && lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + lexer.tokenName() + ", " + lexer.info());
        }
        ParseContext context = this.context;
        try {
            boolean isJsonObjectMap = object instanceof JSONObject;
            Map map = isJsonObjectMap ? ((JSONObject) object).getInnerMap() : object;
            ParseContext context2 = context;
            boolean setContextFlag = false;
            while (true) {
                try {
                    lexer.skipWhitespace();
                    char ch2 = lexer.getCurrent();
                    if (lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (ch2 == ',') {
                            lexer.next();
                            lexer.skipWhitespace();
                            ch2 = lexer.getCurrent();
                        }
                        ch = ch2;
                    } else {
                        ch = ch2;
                    }
                    boolean isObjectKey = false;
                    if (ch == '\"') {
                        key = lexer.scanSymbol(this.symbolTable, Typography.quote);
                        lexer.skipWhitespace();
                        if (lexer.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + lexer.pos() + ", name " + key);
                        }
                    } else {
                        if (ch == '}') {
                            lexer.next();
                            lexer.resetStringPosition();
                            lexer.nextToken();
                            if (!setContextFlag) {
                                if (this.context != null && obj == this.context.fieldName && object == this.context.object) {
                                    context2 = this.context;
                                } else {
                                    ParseContext contextR = setContext(object, fieldName);
                                    if (context2 == null) {
                                        context2 = contextR;
                                    }
                                }
                            }
                            setContext(context2);
                            return object;
                        }
                        if (ch == '\'') {
                            if (!lexer.isEnabled(Feature.AllowSingleQuotes)) {
                                throw new JSONException("syntax error");
                            }
                            key = lexer.scanSymbol(this.symbolTable, '\'');
                            lexer.skipWhitespace();
                            if (lexer.getCurrent() != ':') {
                                throw new JSONException("expect ':' at " + lexer.pos());
                            }
                        } else {
                            if (ch == 26) {
                                throw new JSONException("syntax error");
                            }
                            if (ch == ',') {
                                throw new JSONException("syntax error");
                            }
                            if ((ch >= '0' && ch <= '9') || ch == '-') {
                                lexer.resetStringPosition();
                                lexer.scanNumber();
                                try {
                                    Object key2 = lexer.token() == 2 ? lexer.integerValue() : lexer.decimalValue(true);
                                    key = (lexer.isEnabled(Feature.NonStringKeyAsString) || isJsonObjectMap) ? key2.toString() : key2;
                                    if (lexer.getCurrent() != ':') {
                                        throw new JSONException(str2 + lexer.info());
                                    }
                                } catch (NumberFormatException e) {
                                    throw new JSONException(str2 + lexer.info());
                                }
                            } else if (ch == '{' || ch == '[') {
                                int i = this.objectKeyLevel;
                                this.objectKeyLevel = i + 1;
                                if (i > 512) {
                                    throw new JSONException("object key level > 512");
                                }
                                lexer.nextToken();
                                key = parse();
                                isObjectKey = true;
                            } else {
                                if (!lexer.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                                    throw new JSONException("syntax error");
                                }
                                key = lexer.scanSymbolUnQuoted(this.symbolTable);
                                lexer.skipWhitespace();
                                char ch3 = lexer.getCurrent();
                                if (ch3 != ':') {
                                    throw new JSONException("expect ':' at " + lexer.pos() + ", actual " + ch3);
                                }
                            }
                        }
                    }
                    if (!isObjectKey) {
                        lexer.next();
                        lexer.skipWhitespace();
                    }
                    char ch4 = lexer.getCurrent();
                    lexer.resetStringPosition();
                    if (key == JSON.DEFAULT_TYPE_KEY && !lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                        String typeName = lexer.scanSymbol(this.symbolTable, Typography.quote);
                        if (!lexer.isEnabled(Feature.IgnoreAutoType)) {
                            Class<?> clazz3 = null;
                            if (object != null && object.getClass().getName().equals(typeName)) {
                                Class<?> clazz4 = object.getClass();
                                clazz2 = clazz4;
                            } else if ("java.util.HashMap".equals(typeName)) {
                                clazz2 = HashMap.class;
                            } else if ("java.util.LinkedHashMap".equals(typeName)) {
                                clazz2 = LinkedHashMap.class;
                            } else {
                                boolean allDigits = true;
                                int i2 = 0;
                                while (i2 < typeName.length()) {
                                    char c = typeName.charAt(i2);
                                    clazz = clazz3;
                                    if (c >= '0' && c <= '9') {
                                        i2++;
                                        clazz3 = clazz;
                                    }
                                    allDigits = false;
                                }
                                clazz = clazz3;
                                clazz2 = !allDigits ? this.config.checkAutoType(typeName, null, lexer.getFeatures()) : clazz;
                            }
                            if (clazz2 != null) {
                                break;
                            }
                            map.put(JSON.DEFAULT_TYPE_KEY, typeName);
                        }
                    } else {
                        if (key == "$ref" && context2 != null) {
                            if (object == null || object.size() == 0) {
                                if (!lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                                    lexer.nextToken(4);
                                    if (lexer.token() != 4) {
                                        throw new JSONException("illegal ref, " + JSONToken.name(lexer.token()));
                                    }
                                    String ref = lexer.stringVal();
                                    lexer.nextToken(13);
                                    if (lexer.token() != 16) {
                                        Object refValue = null;
                                        if ("@".equals(ref)) {
                                            if (this.context != null) {
                                                ParseContext thisContext = this.context;
                                                Object thisObj = thisContext.object;
                                                if ((thisObj instanceof Object[]) || (thisObj instanceof Collection)) {
                                                    refValue = thisObj;
                                                } else if (thisContext.parent != null) {
                                                    refValue = thisContext.parent.object;
                                                }
                                            }
                                        } else if ("..".equals(ref)) {
                                            if (context2.object != null) {
                                                refValue = context2.object;
                                            } else {
                                                addResolveTask(new ResolveTask(context2, ref));
                                                setResolveStatus(1);
                                            }
                                        } else if ("$".equals(ref)) {
                                            ParseContext rootContext = context2;
                                            while (rootContext.parent != null) {
                                                rootContext = rootContext.parent;
                                            }
                                            if (rootContext.object != null) {
                                                refValue = rootContext.object;
                                            } else {
                                                addResolveTask(new ResolveTask(rootContext, ref));
                                                setResolveStatus(1);
                                            }
                                        } else {
                                            JSONPath jsonpath = JSONPath.compile(ref);
                                            if (jsonpath.isRef()) {
                                                addResolveTask(new ResolveTask(context2, ref));
                                                setResolveStatus(1);
                                            } else {
                                                refValue = new JSONObject().fluentPut("$ref", ref);
                                            }
                                        }
                                        if (lexer.token() != 13) {
                                            throw new JSONException("syntax error, " + lexer.info());
                                        }
                                        lexer.nextToken(16);
                                        setContext(context2);
                                        return refValue;
                                    }
                                    map.put(key, ref);
                                }
                            }
                        }
                        if (!setContextFlag) {
                            if (this.context != null && obj == this.context.fieldName && object == this.context.object) {
                                context2 = this.context;
                            } else {
                                ParseContext contextR2 = setContext(object, fieldName);
                                if (context2 == null) {
                                    context2 = contextR2;
                                }
                                setContextFlag = true;
                            }
                        }
                        if (object.getClass() == JSONObject.class && key == null) {
                            key = "null";
                        }
                        if (ch4 == '\"') {
                            lexer.scanString();
                            String strValue = lexer.stringVal();
                            value = strValue;
                            if (lexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                                JSONScanner iso8601Lexer = new JSONScanner(strValue);
                                if (iso8601Lexer.scanISO8601DateIfMatch()) {
                                    value = iso8601Lexer.getCalendar().getTime();
                                }
                                iso8601Lexer.close();
                            }
                            map.put(key, value);
                        } else if ((ch4 < '0' || ch4 > '9') && ch4 != '-') {
                            if (ch4 == '[') {
                                lexer.nextToken();
                                Collection jSONArray = new JSONArray();
                                boolean z = obj != null && fieldName.getClass() == Integer.class;
                                if (obj == null) {
                                    setContext(context2);
                                }
                                parseArray(jSONArray, key);
                                map.put(key, lexer.isEnabled(Feature.UseObjectArray) ? jSONArray.toArray() : jSONArray);
                                if (lexer.token() == 13) {
                                    lexer.nextToken();
                                    setContext(context2);
                                    return object;
                                }
                                if (lexer.token() != 16) {
                                    throw new JSONException("syntax error");
                                }
                                str = str2;
                            } else if (ch4 == '{') {
                                lexer.nextToken();
                                boolean parentIsArray = obj != null && fieldName.getClass() == Integer.class;
                                if (lexer.isEnabled(Feature.CustomMapDeserializer)) {
                                    MapDeserializer mapDeserializer = (MapDeserializer) this.config.getDeserializer(Map.class);
                                    input = (lexer.getFeatures() & Feature.OrderedField.mask) != 0 ? mapDeserializer.createMap(Map.class, lexer.getFeatures()) : mapDeserializer.createMap(Map.class);
                                } else {
                                    input = new JSONObject(lexer.isEnabled(Feature.OrderedField));
                                }
                                ParseContext ctxLocal = parentIsArray ? null : setContext(this.context, input, key);
                                Object obj2 = null;
                                boolean objParsed = false;
                                if (this.fieldTypeResolver != null) {
                                    String resolveFieldName = key != null ? key.toString() : null;
                                    str = str2;
                                    Type fieldType = this.fieldTypeResolver.resolve(object, resolveFieldName);
                                    if (fieldType != null) {
                                        ObjectDeserializer fieldDeser = this.config.getDeserializer(fieldType);
                                        obj2 = fieldDeser.deserialze(this, fieldType, key);
                                        objParsed = true;
                                    }
                                } else {
                                    str = str2;
                                }
                                if (!objParsed) {
                                    obj2 = parseObject(input, key);
                                }
                                if (ctxLocal != null && input != obj2) {
                                    ctxLocal.object = object;
                                }
                                if (key != null) {
                                    checkMapResolve(object, key.toString());
                                }
                                map.put(key, obj2);
                                if (parentIsArray) {
                                    setContext(obj2, key);
                                }
                                if (lexer.token() == 13) {
                                    lexer.nextToken();
                                    setContext(context2);
                                    setContext(context2);
                                    return object;
                                }
                                if (lexer.token() != 16) {
                                    throw new JSONException("syntax error, " + lexer.tokenName());
                                }
                                if (parentIsArray) {
                                    popContext();
                                } else {
                                    setContext(context2);
                                }
                            } else {
                                str = str2;
                                lexer.nextToken();
                                map.put(key, parse());
                                if (lexer.token() == 13) {
                                    lexer.nextToken();
                                    setContext(context2);
                                    return object;
                                }
                                if (lexer.token() != 16) {
                                    throw new JSONException("syntax error, position at " + lexer.pos() + ", name " + key);
                                }
                            }
                            obj = fieldName;
                            str2 = str;
                        } else {
                            lexer.scanNumber();
                            value = lexer.token() == 2 ? lexer.integerValue() : lexer.decimalValue(lexer.isEnabled(Feature.UseBigDecimal));
                            map.put(key, value);
                        }
                        lexer.skipWhitespace();
                        char ch5 = lexer.getCurrent();
                        if (ch5 != ',') {
                            if (ch5 != '}') {
                                throw new JSONException("syntax error, position at " + lexer.pos() + ", name " + key);
                            }
                            lexer.next();
                            lexer.resetStringPosition();
                            lexer.nextToken();
                            setContext(value, key);
                            setContext(context2);
                            return object;
                        }
                        lexer.next();
                        str = str2;
                        obj = fieldName;
                        str2 = str;
                    }
                } catch (Throwable th) {
                    th = th;
                    context = context2;
                    setContext(context);
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public void setConfig(ParserConfig config) {
        this.config = config;
    }

    public <T> T parseObject(Class<T> cls) {
        return (T) parseObject(cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return (T) parseObject(type, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T parseObject(Type type, Object obj) {
        int i = this.lexer.token();
        if (i == 8) {
            this.lexer.nextToken();
            return (T) TypeUtils.optionalEmpty(type);
        }
        if (i == 4) {
            if (type == byte[].class) {
                T t = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t;
            }
            if (type == char[].class) {
                String strStringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) strStringVal.toCharArray();
            }
        }
        ObjectDeserializer deserializer = this.config.getDeserializer(type);
        try {
            if (deserializer.getClass() == JavaBeanDeserializer.class) {
                if (this.lexer.token() != 12 && this.lexer.token() != 14) {
                    throw new JSONException("syntax error,expect start with { or [,but actually start with " + this.lexer.tokenName());
                }
                return (T) ((JavaBeanDeserializer) deserializer).deserialze(this, type, obj, 0);
            }
            return (T) deserializer.deserialze(this, type, obj);
        } catch (JSONException e) {
            throw e;
        } catch (Throwable th) {
            throw new JSONException(th.getMessage(), th);
        }
    }

    public <T> List<T> parseArray(Class<T> clazz) {
        List<T> array = new ArrayList<>();
        parseArray((Class<?>) clazz, (Collection) array);
        return array;
    }

    public void parseArray(Class<?> clazz, Collection array) {
        parseArray((Type) clazz, array);
    }

    public void parseArray(Type type, Collection array) {
        parseArray(type, array, null);
    }

    public void parseArray(Type type, Collection array, Object fieldName) {
        ObjectDeserializer deserializer;
        Object val;
        String value;
        int token = this.lexer.token();
        if (token == 21 || token == 22) {
            this.lexer.nextToken();
            token = this.lexer.token();
        }
        if (token != 14) {
            throw new JSONException("field " + fieldName + " expect '[', but " + JSONToken.name(token) + ", " + this.lexer.info());
        }
        if (Integer.TYPE == type) {
            deserializer = IntegerCodec.instance;
            this.lexer.nextToken(2);
        } else if (String.class == type) {
            deserializer = StringCodec.instance;
            this.lexer.nextToken(4);
        } else {
            deserializer = this.config.getDeserializer(type);
            this.lexer.nextToken(deserializer.getFastMatchToken());
        }
        ParseContext context = this.context;
        setContext(array, fieldName);
        int i = 0;
        while (true) {
            try {
                if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (this.lexer.token() == 16) {
                        this.lexer.nextToken();
                    }
                }
                if (this.lexer.token() != 15) {
                    if (Integer.TYPE == type) {
                        Object val2 = IntegerCodec.instance.deserialze(this, null, null);
                        array.add(val2);
                    } else if (String.class == type) {
                        if (this.lexer.token() == 4) {
                            value = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj = parse();
                            if (obj == null) {
                                value = null;
                            } else {
                                String value2 = obj.toString();
                                value = value2;
                            }
                        }
                        array.add(value);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                            val = null;
                        } else {
                            Object val3 = Integer.valueOf(i);
                            val = deserializer.deserialze(this, type, val3);
                        }
                        array.add(val);
                        checkListResolve(array);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(deserializer.getFastMatchToken());
                    }
                    i++;
                } else {
                    setContext(context);
                    this.lexer.nextToken(16);
                    return;
                }
            } catch (Throwable th) {
                setContext(context);
                throw th;
            }
        }
    }

    public Object[] parseArray(Type[] types) {
        Object value;
        Class<?> clazz;
        Object obj = null;
        int i = 8;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        int i2 = 14;
        if (this.lexer.token() != 14) {
            throw new JSONException("syntax error : " + this.lexer.tokenName());
        }
        Object[] list = new Object[types.length];
        if (types.length == 0) {
            this.lexer.nextToken(15);
            if (this.lexer.token() != 15) {
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(16);
            return new Object[0];
        }
        this.lexer.nextToken(2);
        int i3 = 0;
        while (i3 < types.length) {
            if (this.lexer.token() == i) {
                value = null;
                this.lexer.nextToken(16);
            } else {
                Type type = types[i3];
                if (type == Integer.TYPE || type == Integer.class) {
                    if (this.lexer.token() == 2) {
                        Object value2 = Integer.valueOf(this.lexer.intValue());
                        this.lexer.nextToken(16);
                        value = value2;
                    } else {
                        Object value3 = parse();
                        value = TypeUtils.cast(value3, type, this.config);
                    }
                } else if (type == String.class) {
                    if (this.lexer.token() == 4) {
                        Object value4 = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                        value = value4;
                    } else {
                        Object value5 = parse();
                        value = TypeUtils.cast(value5, type, this.config);
                    }
                } else {
                    boolean isArray = false;
                    Class<?> componentType = null;
                    if (i3 == types.length - 1 && (type instanceof Class) && (((clazz = (Class) type) != byte[].class && clazz != char[].class) || this.lexer.token() != 4)) {
                        isArray = clazz.isArray();
                        componentType = clazz.getComponentType();
                    }
                    if (!isArray || this.lexer.token() == i2) {
                        value = this.config.getDeserializer(type).deserialze(this, type, Integer.valueOf(i3));
                    } else {
                        List<Object> varList = new ArrayList<>();
                        ObjectDeserializer deserializer = this.config.getDeserializer(componentType);
                        int fastMatch = deserializer.getFastMatchToken();
                        if (this.lexer.token() != 15) {
                            while (true) {
                                Object item = deserializer.deserialze(this, type, obj);
                                varList.add(item);
                                if (this.lexer.token() != 16) {
                                    break;
                                }
                                this.lexer.nextToken(fastMatch);
                                obj = null;
                            }
                            if (this.lexer.token() != 15) {
                                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                            }
                        }
                        Object value6 = TypeUtils.cast(varList, type, this.config);
                        value = value6;
                    }
                }
            }
            list[i3] = value;
            if (this.lexer.token() == 15) {
                break;
            }
            if (this.lexer.token() != 16) {
                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
            }
            if (i3 == types.length - 1) {
                this.lexer.nextToken(15);
            } else {
                this.lexer.nextToken(2);
            }
            i3++;
            obj = null;
            i = 8;
            i2 = 14;
        }
        if (this.lexer.token() != 15) {
            throw new JSONException("syntax error");
        }
        this.lexer.nextToken(16);
        return list;
    }

    public void parseObject(Object object) {
        Object fieldValue;
        Class<?> clazz = object.getClass();
        JavaBeanDeserializer beanDeser = null;
        ObjectDeserializer deserializer = this.config.getDeserializer(clazz);
        if (deserializer instanceof JavaBeanDeserializer) {
            beanDeser = (JavaBeanDeserializer) deserializer;
        }
        if (this.lexer.token() != 12 && this.lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
        while (true) {
            String key = this.lexer.scanSymbol(this.symbolTable);
            if (key == null) {
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                } else if (this.lexer.token() != 16 || !this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            FieldDeserializer fieldDeser = null;
            if (beanDeser != null) {
                fieldDeser = beanDeser.getFieldDeserializer(key);
            }
            if (fieldDeser == null) {
                if (!this.lexer.isEnabled(Feature.IgnoreNotMatch)) {
                    throw new JSONException("setter not found, class " + clazz.getName() + ", property " + key);
                }
                this.lexer.nextTokenWithColon();
                parse();
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken();
                    return;
                }
            } else {
                Class<?> fieldClass = fieldDeser.fieldInfo.fieldClass;
                Type fieldType = fieldDeser.fieldInfo.fieldType;
                if (fieldClass == Integer.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    fieldValue = IntegerCodec.instance.deserialze(this, fieldType, null);
                } else if (fieldClass == String.class) {
                    this.lexer.nextTokenWithColon(4);
                    fieldValue = StringCodec.deserialze(this);
                } else {
                    Object fieldValue2 = Long.TYPE;
                    if (fieldClass == fieldValue2) {
                        this.lexer.nextTokenWithColon(2);
                        fieldValue = LongCodec.instance.deserialze(this, fieldType, null);
                    } else {
                        ObjectDeserializer fieldValueDeserializer = this.config.getDeserializer(fieldClass, fieldType);
                        this.lexer.nextTokenWithColon(fieldValueDeserializer.getFastMatchToken());
                        fieldValue = fieldValueDeserializer.deserialze(this, fieldType, null);
                    }
                }
                fieldDeser.setValue(object, fieldValue);
                if (this.lexer.token() != 16 && this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                }
            }
        }
    }

    public Object parseArrayWithType(Type collectionType) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypes = ((ParameterizedType) collectionType).getActualTypeArguments();
        if (actualTypes.length != 1) {
            throw new JSONException("not support type " + collectionType);
        }
        Type actualTypeArgument = actualTypes[0];
        if (actualTypeArgument instanceof Class) {
            List<Object> array = new ArrayList<>();
            parseArray((Class<?>) actualTypeArgument, (Collection) array);
            return array;
        }
        if (actualTypeArgument instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) actualTypeArgument;
            Type upperBoundType = wildcardType.getUpperBounds()[0];
            if (Object.class.equals(upperBoundType)) {
                if (wildcardType.getLowerBounds().length == 0) {
                    return parse();
                }
                throw new JSONException("not support type : " + collectionType);
            }
            List<Object> array2 = new ArrayList<>();
            parseArray((Class<?>) upperBoundType, (Collection) array2);
            return array2;
        }
        if (actualTypeArgument instanceof TypeVariable) {
            TypeVariable<?> typeVariable = (TypeVariable) actualTypeArgument;
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length != 1) {
                throw new JSONException("not support : " + typeVariable);
            }
            Type boundType = bounds[0];
            if (boundType instanceof Class) {
                List<Object> array3 = new ArrayList<>();
                parseArray((Class<?>) boundType, (Collection) array3);
                return array3;
            }
        }
        if (actualTypeArgument instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) actualTypeArgument;
            List<Object> array4 = new ArrayList<>();
            parseArray(parameterizedType, array4);
            return array4;
        }
        throw new JSONException("TODO : " + collectionType);
    }

    public void acceptType(String typeName) {
        JSONLexer lexer = this.lexer;
        lexer.nextTokenWithColon();
        if (lexer.token() != 4) {
            throw new JSONException("type not match error");
        }
        if (typeName.equals(lexer.stringVal())) {
            lexer.nextToken();
            if (lexer.token() == 16) {
                lexer.nextToken();
                return;
            }
            return;
        }
        throw new JSONException("type not match error");
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public void setResolveStatus(int resolveStatus) {
        this.resolveStatus = resolveStatus;
    }

    public Object getObject(String path) {
        for (int i = 0; i < this.contextArrayIndex; i++) {
            if (path.equals(this.contextArray[i].toString())) {
                return this.contextArray[i].object;
            }
        }
        return null;
    }

    public void checkListResolve(Collection array) {
        if (this.resolveStatus == 1) {
            if (array instanceof List) {
                int index = array.size() - 1;
                List list = (List) array;
                ResolveTask task = getLastResolveTask();
                task.fieldDeserializer = new ResolveFieldDeserializer(this, list, index);
                task.ownerContext = this.context;
                setResolveStatus(0);
                return;
            }
            ResolveTask task2 = getLastResolveTask();
            task2.fieldDeserializer = new ResolveFieldDeserializer(array);
            task2.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public void checkMapResolve(Map object, Object fieldName) {
        if (this.resolveStatus == 1) {
            ResolveFieldDeserializer fieldResolver = new ResolveFieldDeserializer(object, fieldName);
            ResolveTask task = getLastResolveTask();
            task.fieldDeserializer = fieldResolver;
            task.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public Object parseObject(Map object) {
        return parseObject(object, (Object) null);
    }

    public JSONObject parseObject() {
        JSONObject object = new JSONObject(this.lexer.isEnabled(Feature.OrderedField));
        Object parsedObject = parseObject((Map) object);
        if (parsedObject instanceof JSONObject) {
            return (JSONObject) parsedObject;
        }
        if (parsedObject == null) {
            return null;
        }
        return new JSONObject((Map<String, Object>) parsedObject);
    }

    public final void parseArray(Collection array) {
        parseArray(array, (Object) null);
    }

    public final void parseArray(Collection collection, Object obj) {
        Number numberDecimalValue;
        Object time;
        Object object;
        JSONLexer jSONLexer = this.lexer;
        if (jSONLexer.token() == 21 || jSONLexer.token() == 22) {
            jSONLexer.nextToken();
        }
        if (jSONLexer.token() != 14) {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(jSONLexer.token()) + ", pos " + jSONLexer.pos() + ", fieldName " + obj);
        }
        jSONLexer.nextToken(4);
        if (this.context != null && this.context.level > 512) {
            throw new JSONException("array level > 512");
        }
        ParseContext parseContext = this.context;
        setContext(collection, obj);
        int i = 0;
        while (true) {
            try {
                try {
                    if (jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (jSONLexer.token() == 16) {
                            jSONLexer.nextToken();
                        }
                    }
                    switch (jSONLexer.token()) {
                        case 2:
                            Number numberIntegerValue = jSONLexer.integerValue();
                            jSONLexer.nextToken(16);
                            object = numberIntegerValue;
                            break;
                        case 3:
                            if (jSONLexer.isEnabled(Feature.UseBigDecimal)) {
                                numberDecimalValue = jSONLexer.decimalValue(true);
                            } else {
                                numberDecimalValue = jSONLexer.decimalValue(false);
                            }
                            jSONLexer.nextToken(16);
                            object = numberDecimalValue;
                            break;
                        case 4:
                            String strStringVal = jSONLexer.stringVal();
                            jSONLexer.nextToken(16);
                            if (!jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                                object = strStringVal;
                                break;
                            } else {
                                JSONScanner jSONScanner = new JSONScanner(strStringVal);
                                if (jSONScanner.scanISO8601DateIfMatch()) {
                                    time = jSONScanner.getCalendar().getTime();
                                } else {
                                    time = strStringVal;
                                }
                                jSONScanner.close();
                                object = time;
                                break;
                            }
                        case 6:
                            Boolean bool = Boolean.TRUE;
                            jSONLexer.nextToken(16);
                            object = bool;
                            break;
                        case 7:
                            Boolean bool2 = Boolean.FALSE;
                            jSONLexer.nextToken(16);
                            object = bool2;
                            break;
                        case 8:
                            object = null;
                            jSONLexer.nextToken(4);
                            break;
                        case 12:
                            object = parseObject(new JSONObject(jSONLexer.isEnabled(Feature.OrderedField)), Integer.valueOf(i));
                            break;
                        case 14:
                            JSONArray jSONArray = new JSONArray();
                            parseArray(jSONArray, Integer.valueOf(i));
                            if (!jSONLexer.isEnabled(Feature.UseObjectArray)) {
                                object = jSONArray;
                                break;
                            } else {
                                object = jSONArray.toArray();
                                break;
                            }
                        case 15:
                            jSONLexer.nextToken(16);
                            return;
                        case 20:
                            throw new JSONException("unclosed jsonArray");
                        case 23:
                            object = null;
                            jSONLexer.nextToken(4);
                            break;
                        default:
                            object = parse();
                            break;
                    }
                    collection.add(object);
                    checkListResolve(collection);
                    if (jSONLexer.token() == 16) {
                        jSONLexer.nextToken(4);
                    }
                    i++;
                } catch (ClassCastException e) {
                    throw new JSONException("unkown error", e);
                }
            } finally {
                setContext(parseContext);
            }
        }
    }

    public ParseContext getContext() {
        return this.context;
    }

    public ParseContext getOwnerContext() {
        return this.context.parent;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        return this.resolveTaskList;
    }

    public void addResolveTask(ResolveTask task) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(task);
    }

    public ResolveTask getLastResolveTask() {
        return this.resolveTaskList.get(this.resolveTaskList.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setContext(ParseContext context) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = context;
    }

    public void popContext() {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = this.context.parent;
        if (this.contextArrayIndex <= 0) {
            return;
        }
        this.contextArrayIndex--;
        this.contextArray[this.contextArrayIndex] = null;
    }

    public ParseContext setContext(Object object, Object fieldName) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, object, fieldName);
    }

    public ParseContext setContext(ParseContext parent, Object object, Object fieldName) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        this.context = new ParseContext(parent, object, fieldName);
        addContext(this.context);
        return this.context;
    }

    private void addContext(ParseContext context) {
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        if (this.contextArray == null) {
            this.contextArray = new ParseContext[8];
        } else if (i >= this.contextArray.length) {
            int newLen = (this.contextArray.length * 3) / 2;
            ParseContext[] newArray = new ParseContext[newLen];
            System.arraycopy(this.contextArray, 0, newArray, 0, this.contextArray.length);
            this.contextArray = newArray;
        }
        this.contextArray[i] = context;
    }

    public Object parse() {
        return parse(null);
    }

    public Object parseKey() {
        if (this.lexer.token() == 18) {
            String value = this.lexer.stringVal();
            this.lexer.nextToken(16);
            return value;
        }
        return parse(null);
    }

    public Object parse(Object fieldName) {
        Map jSONObject;
        JSONLexer lexer = this.lexer;
        switch (lexer.token()) {
            case 2:
                Number intValue = lexer.integerValue();
                lexer.nextToken();
                return intValue;
            case 3:
                Object value = lexer.decimalValue(lexer.isEnabled(Feature.UseBigDecimal));
                lexer.nextToken();
                return value;
            case 4:
                String stringLiteral = lexer.stringVal();
                lexer.nextToken(16);
                if (lexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                    JSONScanner iso8601Lexer = new JSONScanner(stringLiteral);
                    try {
                        if (iso8601Lexer.scanISO8601DateIfMatch()) {
                            return iso8601Lexer.getCalendar().getTime();
                        }
                    } finally {
                        iso8601Lexer.close();
                    }
                }
                return stringLiteral;
            case 5:
            case 10:
            case 11:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 24:
            case 25:
            default:
                throw new JSONException("syntax error, " + lexer.info());
            case 6:
                lexer.nextToken();
                return Boolean.TRUE;
            case 7:
                lexer.nextToken();
                return Boolean.FALSE;
            case 8:
                lexer.nextToken();
                return null;
            case 9:
                lexer.nextToken(18);
                if (lexer.token() != 18) {
                    throw new JSONException("syntax error");
                }
                lexer.nextToken(10);
                accept(10);
                long time = lexer.integerValue().longValue();
                accept(2);
                accept(11);
                return new Date(time);
            case 12:
                if (isEnabled(Feature.UseNativeJavaObject)) {
                    jSONObject = lexer.isEnabled(Feature.OrderedField) ? new HashMap() : new LinkedHashMap();
                } else {
                    jSONObject = new JSONObject(lexer.isEnabled(Feature.OrderedField));
                }
                Map object = jSONObject;
                return parseObject(object, fieldName);
            case 14:
                Collection array = isEnabled(Feature.UseNativeJavaObject) ? new ArrayList() : new JSONArray();
                parseArray(array, fieldName);
                if (lexer.isEnabled(Feature.UseObjectArray)) {
                    return array.toArray();
                }
                return array;
            case 18:
                String identifier = lexer.stringVal();
                if ("NaN".equals(identifier)) {
                    lexer.nextToken();
                    return null;
                }
                throw new JSONException("syntax error, " + lexer.info());
            case 20:
                if (lexer.isBlankInput()) {
                    return null;
                }
                throw new JSONException("unterminated json string, " + lexer.info());
            case 21:
                lexer.nextToken();
                HashSet<Object> set = new HashSet<>();
                parseArray(set, fieldName);
                return set;
            case 22:
                lexer.nextToken();
                TreeSet<Object> treeSet = new TreeSet<>();
                parseArray(treeSet, fieldName);
                return treeSet;
            case 23:
                lexer.nextToken();
                return null;
            case 26:
                byte[] bytes = lexer.bytesValue();
                lexer.nextToken();
                return bytes;
        }
    }

    public void config(Feature feature, boolean state) {
        this.lexer.config(feature, state);
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public final void accept(int token) {
        JSONLexer lexer = this.lexer;
        if (lexer.token() == token) {
            lexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(token) + ", actual " + JSONToken.name(lexer.token()));
    }

    public final void accept(int token, int nextExpectToken) {
        JSONLexer lexer = this.lexer;
        if (lexer.token() == token) {
            lexer.nextToken(nextExpectToken);
        } else {
            throwException(token);
        }
    }

    public void throwException(int token) {
        throw new JSONException("syntax error, expect " + JSONToken.name(token) + ", actual " + JSONToken.name(this.lexer.token()));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        JSONLexer lexer = this.lexer;
        try {
            if (lexer.isEnabled(Feature.AutoCloseSource) && lexer.token() != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(lexer.token()));
            }
        } finally {
            lexer.close();
        }
    }

    public Object resolveReference(String ref) {
        if (this.contextArray == null) {
            return null;
        }
        for (int i = 0; i < this.contextArray.length && i < this.contextArrayIndex; i++) {
            ParseContext context = this.contextArray[i];
            if (context.toString().equals(ref)) {
                return context.object;
            }
        }
        return null;
    }

    public void handleResovleTask(Object value) {
        Object refValue;
        if (this.resolveTaskList == null) {
            return;
        }
        int size = this.resolveTaskList.size();
        for (int i = 0; i < size; i++) {
            ResolveTask task = this.resolveTaskList.get(i);
            String ref = task.referenceValue;
            Object object = null;
            if (task.ownerContext != null) {
                object = task.ownerContext.object;
            }
            if (ref.startsWith("$")) {
                refValue = getObject(ref);
                if (refValue == null) {
                    try {
                        JSONPath jsonpath = new JSONPath(ref, SerializeConfig.getGlobalInstance(), this.config, true);
                        if (jsonpath.isRef()) {
                            refValue = jsonpath.eval(value);
                        }
                    } catch (JSONPathException e) {
                    }
                }
            } else {
                refValue = task.context.object;
            }
            FieldDeserializer fieldDeser = task.fieldDeserializer;
            if (fieldDeser != null) {
                if (refValue != null && refValue.getClass() == JSONObject.class && fieldDeser.fieldInfo != null && !Map.class.isAssignableFrom(fieldDeser.fieldInfo.fieldClass)) {
                    Object root = this.contextArray[0].object;
                    JSONPath jsonpath2 = JSONPath.compile(ref);
                    if (jsonpath2.isRef()) {
                        refValue = jsonpath2.eval(root);
                    }
                }
                Object root2 = fieldDeser.getOwnerClass();
                if (root2 != null && !fieldDeser.getOwnerClass().isInstance(object) && task.ownerContext.parent != null) {
                    ParseContext ctx = task.ownerContext.parent;
                    while (true) {
                        if (ctx == null) {
                            break;
                        }
                        if (!fieldDeser.getOwnerClass().isInstance(ctx.object)) {
                            ctx = ctx.parent;
                        } else {
                            object = ctx.object;
                            break;
                        }
                    }
                }
                fieldDeser.setValue(object, refValue);
            }
        }
    }

    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext context, String referenceValue) {
            this.context = context;
            this.referenceValue = referenceValue;
        }
    }

    public void parseExtra(Object object, String key) {
        Object value;
        JSONLexer lexer = this.lexer;
        lexer.nextTokenWithColon();
        Type type = null;
        if (this.extraTypeProviders != null) {
            for (ExtraTypeProvider extraProvider : this.extraTypeProviders) {
                type = extraProvider.getExtraType(object, key);
            }
        }
        if (type == null) {
            value = parse();
        } else {
            value = parseObject(type);
        }
        if (object instanceof ExtraProcessable) {
            ExtraProcessable extraProcessable = (ExtraProcessable) object;
            extraProcessable.processExtra(key, value);
            return;
        }
        if (this.extraProcessors != null) {
            for (ExtraProcessor process : this.extraProcessors) {
                process.processExtra(object, key, value);
            }
        }
        if (this.resolveStatus == 1) {
            this.resolveStatus = 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:86:0x0251, code lost:
    
        return r12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Object parse(PropertyProcessable object, Object fieldName) {
        String key;
        Object value;
        if (this.lexer.token() != 12) {
            String msg = "syntax error, expect {, actual " + this.lexer.tokenName();
            if (fieldName instanceof String) {
                msg = (msg + ", fieldName ") + fieldName;
            }
            String msg2 = (msg + ", ") + this.lexer.info();
            JSONArray array = new JSONArray();
            parseArray(array, fieldName);
            if (array.size() == 1) {
                Object first = array.get(0);
                if (first instanceof JSONObject) {
                    return (JSONObject) first;
                }
            }
            throw new JSONException(msg2);
        }
        ParseContext context = this.context;
        int i = 0;
        while (true) {
            try {
                this.lexer.skipWhitespace();
                char ch = this.lexer.getCurrent();
                if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (ch == ',') {
                        this.lexer.next();
                        this.lexer.skipWhitespace();
                        ch = this.lexer.getCurrent();
                    }
                }
                if (ch == '\"') {
                    key = this.lexer.scanSymbol(this.symbolTable, Typography.quote);
                    this.lexer.skipWhitespace();
                    if (this.lexer.getCurrent() != ':') {
                        throw new JSONException("expect ':' at " + this.lexer.pos());
                    }
                } else {
                    if (ch == '}') {
                        this.lexer.next();
                        this.lexer.resetStringPosition();
                        this.lexer.nextToken(16);
                        return object;
                    }
                    if (ch == '\'') {
                        if (!this.lexer.isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("syntax error");
                        }
                        key = this.lexer.scanSymbol(this.symbolTable, '\'');
                        this.lexer.skipWhitespace();
                        if (this.lexer.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + this.lexer.pos());
                        }
                    } else {
                        if (!this.lexer.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                            throw new JSONException("syntax error");
                        }
                        key = this.lexer.scanSymbolUnQuoted(this.symbolTable);
                        this.lexer.skipWhitespace();
                        char ch2 = this.lexer.getCurrent();
                        if (ch2 != ':') {
                            throw new JSONException("expect ':' at " + this.lexer.pos() + ", actual " + ch2);
                        }
                    }
                }
                this.lexer.next();
                this.lexer.skipWhitespace();
                this.lexer.getCurrent();
                this.lexer.resetStringPosition();
                if (key != JSON.DEFAULT_TYPE_KEY || this.lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    this.lexer.nextToken();
                    if (i != 0) {
                        setContext(context);
                    }
                    Type valueType = object.getType(key);
                    if (this.lexer.token() == 8) {
                        value = null;
                        this.lexer.nextToken();
                    } else {
                        value = parseObject(valueType, key);
                    }
                    object.apply(key, value);
                    setContext(context, value, key);
                    setContext(context);
                    int tok = this.lexer.token();
                    if (tok == 20 || tok == 15) {
                        break;
                    }
                    if (tok == 13) {
                        this.lexer.nextToken();
                        return object;
                    }
                } else {
                    String typeName = this.lexer.scanSymbol(this.symbolTable, Typography.quote);
                    Class<?> clazz = this.config.checkAutoType(typeName, null, this.lexer.getFeatures());
                    if (!Map.class.isAssignableFrom(clazz)) {
                        ObjectDeserializer deserializer = this.config.getDeserializer(clazz);
                        this.lexer.nextToken(16);
                        setResolveStatus(2);
                        if (context != null && !(fieldName instanceof Integer)) {
                            popContext();
                        }
                        return (Map) deserializer.deserialze(this, clazz, fieldName);
                    }
                    this.lexer.nextToken(16);
                    if (this.lexer.token() == 13) {
                        this.lexer.nextToken(16);
                        return object;
                    }
                }
                i++;
            } finally {
                setContext(context);
            }
        }
    }
}