package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.FieldSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.tencent.bugly.Bugly;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class JSONPath implements JSONAware {
    static final long LENGTH = -1580386065683472715L;
    static final long SIZE = 5614464919154503228L;
    private static ConcurrentMap<String, JSONPath> pathCache = new ConcurrentHashMap(128, 0.75f, 1);
    private boolean hasRefSegment;
    private boolean ignoreNullValue;
    private ParserConfig parserConfig;
    private final String path;
    private Segment[] segments;
    private SerializeConfig serializeConfig;

    interface Filter {
        boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3);
    }

    enum Operator {
        EQ,
        NE,
        GT,
        GE,
        LT,
        LE,
        LIKE,
        NOT_LIKE,
        RLIKE,
        NOT_RLIKE,
        IN,
        NOT_IN,
        BETWEEN,
        NOT_BETWEEN,
        And,
        Or,
        REG_MATCH
    }

    interface Segment {
        Object eval(JSONPath jSONPath, Object obj, Object obj2);

        void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context);
    }

    public JSONPath(String path) {
        this(path, SerializeConfig.getGlobalInstance(), ParserConfig.getGlobalInstance(), true);
    }

    public JSONPath(String path, boolean ignoreNullValue) {
        this(path, SerializeConfig.getGlobalInstance(), ParserConfig.getGlobalInstance(), ignoreNullValue);
    }

    public JSONPath(String path, SerializeConfig serializeConfig, ParserConfig parserConfig, boolean ignoreNullValue) {
        if (path == null || path.length() == 0) {
            throw new JSONPathException("json-path can not be null or empty");
        }
        this.path = path;
        this.serializeConfig = serializeConfig;
        this.parserConfig = parserConfig;
        this.ignoreNullValue = ignoreNullValue;
    }

    protected void init() {
        if (this.segments != null) {
            return;
        }
        if ("*".equals(this.path)) {
            this.segments = new Segment[]{WildCardSegment.instance};
            return;
        }
        JSONPathParser parser = new JSONPathParser(this.path);
        this.segments = parser.explain();
        this.hasRefSegment = parser.hasRefSegment;
    }

    public boolean isRef() {
        try {
            init();
            for (int i = 0; i < this.segments.length; i++) {
                Segment segment = this.segments[i];
                Class segmentType = segment.getClass();
                if (segmentType != ArrayAccessSegment.class && segmentType != PropertySegment.class) {
                    return false;
                }
            }
            return true;
        } catch (JSONPathException e) {
            return false;
        }
    }

    public Object eval(Object rootObject) {
        if (rootObject == null) {
            return null;
        }
        init();
        Object currentObject = rootObject;
        for (int i = 0; i < this.segments.length; i++) {
            Segment segment = this.segments[i];
            currentObject = segment.eval(this, rootObject, currentObject);
        }
        return currentObject;
    }

    public <T> T eval(Object obj, Type type, ParserConfig parserConfig) {
        return (T) TypeUtils.cast(eval(obj), type, parserConfig);
    }

    public <T> T eval(Object obj, Type type) {
        return (T) eval(obj, type, ParserConfig.getGlobalInstance());
    }

    public Object extract(DefaultJSONParser parser) {
        boolean eval;
        if (parser == null) {
            return null;
        }
        init();
        if (this.hasRefSegment) {
            Object root = parser.parse();
            return eval(root);
        }
        if (this.segments.length == 0) {
            return parser.parse();
        }
        Segment lastSegment = this.segments[this.segments.length - 1];
        if ((lastSegment instanceof TypeSegment) || (lastSegment instanceof FloorSegment) || (lastSegment instanceof MultiIndexSegment)) {
            return eval(parser.parse());
        }
        Context context = null;
        int i = 0;
        while (i < this.segments.length) {
            Segment segment = this.segments[i];
            boolean last = i == this.segments.length - 1;
            if (context != null && context.object != null) {
                context.object = segment.eval(this, null, context.object);
            } else {
                if (!last) {
                    Segment nextSegment = this.segments[i + 1];
                    if ((segment instanceof PropertySegment) && ((PropertySegment) segment).deep && ((nextSegment instanceof ArrayAccessSegment) || (nextSegment instanceof MultiIndexSegment) || (nextSegment instanceof MultiPropertySegment) || (nextSegment instanceof SizeSegment) || (nextSegment instanceof PropertySegment) || (nextSegment instanceof FilterSegment))) {
                        eval = true;
                    } else {
                        boolean eval2 = nextSegment instanceof ArrayAccessSegment;
                        if (eval2 && ((ArrayAccessSegment) nextSegment).index < 0) {
                            eval = true;
                        } else {
                            boolean eval3 = nextSegment instanceof FilterSegment;
                            if (eval3) {
                                eval = true;
                            } else {
                                boolean eval4 = segment instanceof WildCardSegment;
                                if (eval4) {
                                    eval = true;
                                } else {
                                    boolean eval5 = segment instanceof MultiIndexSegment;
                                    if (eval5) {
                                        eval = true;
                                    } else {
                                        eval = false;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    eval = true;
                }
                context = new Context(context, eval);
                segment.extract(this, parser, context);
            }
            i++;
        }
        return context.object;
    }

    private static class Context {
        final boolean eval;
        Object object;
        final Context parent;

        public Context(Context parent, boolean eval) {
            this.parent = parent;
            this.eval = eval;
        }
    }

    public boolean contains(Object rootObject) {
        if (rootObject == null) {
            return false;
        }
        init();
        Object currentObject = rootObject;
        for (int i = 0; i < this.segments.length; i++) {
            Object parentObject = currentObject;
            currentObject = this.segments[i].eval(this, rootObject, currentObject);
            if (currentObject == null) {
                return false;
            }
            if (currentObject == Collections.EMPTY_LIST && (parentObject instanceof List)) {
                return ((List) parentObject).contains(currentObject);
            }
        }
        return true;
    }

    public boolean containsValue(Object rootObject, Object value) {
        Object currentObject = eval(rootObject);
        if (currentObject == value) {
            return true;
        }
        if (currentObject == null) {
            return false;
        }
        if (currentObject instanceof Iterable) {
            for (Object item : (Iterable) currentObject) {
                if (eq(item, value)) {
                    return true;
                }
            }
            return false;
        }
        return eq(currentObject, value);
    }

    public int size(Object rootObject) {
        if (rootObject == null) {
            return -1;
        }
        init();
        Object currentObject = rootObject;
        for (int i = 0; i < this.segments.length; i++) {
            currentObject = this.segments[i].eval(this, rootObject, currentObject);
        }
        int i2 = evalSize(currentObject);
        return i2;
    }

    public Set<?> keySet(Object rootObject) {
        if (rootObject == null) {
            return null;
        }
        init();
        Object currentObject = rootObject;
        for (int i = 0; i < this.segments.length; i++) {
            currentObject = this.segments[i].eval(this, rootObject, currentObject);
        }
        return evalKeySet(currentObject);
    }

    public void patchAdd(Object rootObject, Object value, boolean replace) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Object newResult;
        if (rootObject == null) {
            return;
        }
        init();
        Object currentObject = rootObject;
        Object parentObject = null;
        for (int i = 0; i < this.segments.length; i++) {
            parentObject = currentObject;
            Segment segment = this.segments[i];
            currentObject = segment.eval(this, rootObject, currentObject);
            if (currentObject == null && i != this.segments.length - 1 && (segment instanceof PropertySegment)) {
                currentObject = new JSONObject();
                ((PropertySegment) segment).setValue(this, parentObject, currentObject);
            }
        }
        Object result = currentObject;
        if (!replace && (result instanceof Collection)) {
            Collection collection = (Collection) result;
            collection.add(value);
            return;
        }
        if (result != null && !replace) {
            Class<?> resultClass = result.getClass();
            if (resultClass.isArray()) {
                int length = Array.getLength(result);
                Object descArray = Array.newInstance(resultClass.getComponentType(), length + 1);
                System.arraycopy(result, 0, descArray, 0, length);
                Array.set(descArray, length, value);
                newResult = descArray;
            } else if (Map.class.isAssignableFrom(resultClass)) {
                newResult = value;
            } else {
                throw new JSONException("unsupported array put operation. " + resultClass);
            }
        } else {
            newResult = value;
        }
        Segment lastSegment = this.segments[this.segments.length - 1];
        if (lastSegment instanceof PropertySegment) {
            PropertySegment propertySegment = (PropertySegment) lastSegment;
            propertySegment.setValue(this, parentObject, newResult);
        } else {
            if (lastSegment instanceof ArrayAccessSegment) {
                ((ArrayAccessSegment) lastSegment).setValue(this, parentObject, newResult);
                return;
            }
            throw new UnsupportedOperationException();
        }
    }

    public void arrayAdd(Object rootObject, Object... values) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (values == null || values.length == 0 || rootObject == null) {
            return;
        }
        init();
        Object currentObject = rootObject;
        Object parentObject = null;
        for (int i = 0; i < this.segments.length; i++) {
            if (i == this.segments.length - 1) {
                parentObject = currentObject;
            }
            currentObject = this.segments[i].eval(this, rootObject, currentObject);
        }
        Object result = currentObject;
        if (result == null) {
            throw new JSONPathException("value not found in path " + this.path);
        }
        if (result instanceof Collection) {
            Collection collection = (Collection) result;
            for (Object value : values) {
                collection.add(value);
            }
            return;
        }
        Class<?> resultClass = result.getClass();
        if (resultClass.isArray()) {
            int length = Array.getLength(result);
            Object descArray = Array.newInstance(resultClass.getComponentType(), values.length + length);
            System.arraycopy(result, 0, descArray, 0, length);
            for (int i2 = 0; i2 < values.length; i2++) {
                Array.set(descArray, length + i2, values[i2]);
            }
            Segment lastSegment = this.segments[this.segments.length - 1];
            if (lastSegment instanceof PropertySegment) {
                PropertySegment propertySegment = (PropertySegment) lastSegment;
                propertySegment.setValue(this, parentObject, descArray);
                return;
            } else {
                if (lastSegment instanceof ArrayAccessSegment) {
                    ((ArrayAccessSegment) lastSegment).setValue(this, parentObject, descArray);
                    return;
                }
                throw new UnsupportedOperationException();
            }
        }
        throw new JSONException("unsupported array put operation. " + resultClass);
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0099, code lost:
    
        if (r2 != null) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x009b, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x009e, code lost:
    
        if ((r3 instanceof com.alibaba.fastjson.JSONPath.PropertySegment) == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a0, code lost:
    
        r0 = (com.alibaba.fastjson.JSONPath.PropertySegment) r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a5, code lost:
    
        if ((r2 instanceof java.util.Collection) == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00aa, code lost:
    
        if (r13.segments.length <= 1) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ac, code lost:
    
        r4 = r13.segments[r13.segments.length - 2];
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00b7, code lost:
    
        if ((r4 instanceof com.alibaba.fastjson.JSONPath.RangeSegment) != false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00bb, code lost:
    
        if ((r4 instanceof com.alibaba.fastjson.JSONPath.MultiIndexSegment) == false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00bd, code lost:
    
        r5 = (java.util.Collection) r2;
        r6 = false;
        r7 = r5.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c9, code lost:
    
        if (r7.hasNext() == false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00cb, code lost:
    
        r8 = r7.next();
        r9 = r0.remove(r13, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d3, code lost:
    
        if (r9 == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d5, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00d7, code lost:
    
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00dc, code lost:
    
        return r0.remove(r13, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00df, code lost:
    
        if ((r3 instanceof com.alibaba.fastjson.JSONPath.ArrayAccessSegment) == false) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00e8, code lost:
    
        return ((com.alibaba.fastjson.JSONPath.ArrayAccessSegment) r3).remove(r13, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00eb, code lost:
    
        if ((r3 instanceof com.alibaba.fastjson.JSONPath.FilterSegment) == false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00f4, code lost:
    
        return ((com.alibaba.fastjson.JSONPath.FilterSegment) r3).remove(r13, r14, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00fb, code lost:
    
        throw new java.lang.UnsupportedOperationException();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean remove(Object rootObject) {
        if (rootObject == null) {
            return false;
        }
        init();
        Object currentObject = rootObject;
        Object parentObject = null;
        Segment lastSegment = this.segments[this.segments.length - 1];
        int i = 0;
        while (true) {
            if (i >= this.segments.length) {
                break;
            }
            if (i == this.segments.length - 1) {
                parentObject = currentObject;
                break;
            }
            Segment segement = this.segments[i];
            if (i == this.segments.length - 2 && (lastSegment instanceof FilterSegment) && (segement instanceof PropertySegment)) {
                FilterSegment filterSegment = (FilterSegment) lastSegment;
                if (currentObject instanceof List) {
                    PropertySegment propertySegment = (PropertySegment) segement;
                    List list = (List) currentObject;
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        Object item = it.next();
                        Object result = propertySegment.eval(this, rootObject, item);
                        if (result instanceof Iterable) {
                            filterSegment.remove(this, rootObject, result);
                        } else if ((result instanceof Map) && filterSegment.filter.apply(this, rootObject, currentObject, result)) {
                            it.remove();
                        }
                    }
                    return true;
                }
                if (currentObject instanceof Map) {
                    PropertySegment propertySegment2 = (PropertySegment) segement;
                    Object result2 = propertySegment2.eval(this, rootObject, currentObject);
                    if (result2 == null) {
                        return false;
                    }
                    if ((result2 instanceof Map) && filterSegment.filter.apply(this, rootObject, currentObject, result2)) {
                        propertySegment2.remove(this, currentObject);
                        return true;
                    }
                }
            }
            currentObject = segement.eval(this, rootObject, currentObject);
            if (currentObject == null) {
                break;
            }
            i++;
        }
    }

    public boolean set(Object rootObject, Object value) {
        return set(rootObject, value, true);
    }

    public boolean set(Object rootObject, Object value, boolean p) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        if (rootObject == null) {
            return false;
        }
        init();
        Object currentObject = rootObject;
        Object parentObject = null;
        for (int i = 0; i < this.segments.length; i++) {
            parentObject = currentObject;
            Segment segment = this.segments[i];
            currentObject = segment.eval(this, rootObject, currentObject);
            if (currentObject == null) {
                Segment nextSegment = null;
                if (i < this.segments.length - 1) {
                    nextSegment = this.segments[i + 1];
                }
                Object newObj = null;
                if (nextSegment instanceof PropertySegment) {
                    JavaBeanDeserializer beanDeserializer = null;
                    Class<?> fieldClass = null;
                    if (segment instanceof PropertySegment) {
                        String propertyName = ((PropertySegment) segment).propertyName;
                        Class<?> parentClass = parentObject.getClass();
                        JavaBeanDeserializer parentBeanDeserializer = getJavaBeanDeserializer(parentClass);
                        if (parentBeanDeserializer != null) {
                            FieldDeserializer fieldDeserializer = parentBeanDeserializer.getFieldDeserializer(propertyName);
                            fieldClass = fieldDeserializer.fieldInfo.fieldClass;
                            beanDeserializer = getJavaBeanDeserializer(fieldClass);
                        }
                    }
                    if (beanDeserializer != null) {
                        if (beanDeserializer.beanInfo.defaultConstructor != null) {
                            newObj = beanDeserializer.createInstance((DefaultJSONParser) null, fieldClass);
                        } else {
                            return false;
                        }
                    } else {
                        newObj = new JSONObject();
                    }
                } else if (nextSegment instanceof ArrayAccessSegment) {
                    newObj = new JSONArray();
                }
                if (newObj == null) {
                    break;
                }
                if (segment instanceof PropertySegment) {
                    PropertySegment propSegement = (PropertySegment) segment;
                    propSegement.setValue(this, parentObject, newObj);
                    currentObject = newObj;
                } else {
                    if (!(segment instanceof ArrayAccessSegment)) {
                        break;
                    }
                    ArrayAccessSegment arrayAccessSegement = (ArrayAccessSegment) segment;
                    arrayAccessSegement.setValue(this, parentObject, newObj);
                    currentObject = newObj;
                }
            }
        }
        if (parentObject != null) {
            Segment lastSegment = this.segments[this.segments.length - 1];
            if (lastSegment instanceof PropertySegment) {
                PropertySegment propertySegment = (PropertySegment) lastSegment;
                propertySegment.setValue(this, parentObject, value);
                return true;
            }
            if (lastSegment instanceof ArrayAccessSegment) {
                return ((ArrayAccessSegment) lastSegment).setValue(this, parentObject, value);
            }
            throw new UnsupportedOperationException();
        }
        return false;
    }

    public static Object eval(Object rootObject, String path) {
        JSONPath jsonpath = compile(path);
        return jsonpath.eval(rootObject);
    }

    public static Object eval(Object rootObject, String path, boolean ignoreNullValue) {
        JSONPath jsonpath = compile(path, ignoreNullValue);
        return jsonpath.eval(rootObject);
    }

    public static int size(Object rootObject, String path) {
        JSONPath jsonpath = compile(path);
        Object result = jsonpath.eval(rootObject);
        return jsonpath.evalSize(result);
    }

    public static Set<?> keySet(Object rootObject, String path) {
        JSONPath jsonpath = compile(path);
        Object result = jsonpath.eval(rootObject);
        return jsonpath.evalKeySet(result);
    }

    public static boolean contains(Object rootObject, String path) {
        if (rootObject == null) {
            return false;
        }
        JSONPath jsonpath = compile(path);
        return jsonpath.contains(rootObject);
    }

    public static boolean containsValue(Object rootObject, String path, Object value) {
        JSONPath jsonpath = compile(path);
        return jsonpath.containsValue(rootObject, value);
    }

    public static void arrayAdd(Object rootObject, String path, Object... values) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        JSONPath jsonpath = compile(path);
        jsonpath.arrayAdd(rootObject, values);
    }

    public static boolean set(Object rootObject, String path, Object value) {
        JSONPath jsonpath = compile(path);
        return jsonpath.set(rootObject, value);
    }

    public static boolean remove(Object root, String path) {
        JSONPath jsonpath = compile(path);
        return jsonpath.remove(root);
    }

    public static JSONPath compile(String path) {
        if (path == null) {
            throw new JSONPathException("jsonpath can not be null");
        }
        JSONPath jsonpath = pathCache.get(path);
        if (jsonpath == null) {
            JSONPath jsonpath2 = new JSONPath(path);
            if (pathCache.size() >= 1024) {
                return jsonpath2;
            }
            pathCache.putIfAbsent(path, jsonpath2);
            return pathCache.get(path);
        }
        return jsonpath;
    }

    public static JSONPath compile(String path, boolean ignoreNullValue) {
        if (path == null) {
            throw new JSONPathException("jsonpath can not be null");
        }
        JSONPath jsonpath = pathCache.get(path);
        if (jsonpath == null) {
            JSONPath jsonpath2 = new JSONPath(path, ignoreNullValue);
            if (pathCache.size() >= 1024) {
                return jsonpath2;
            }
            pathCache.putIfAbsent(path, jsonpath2);
            return pathCache.get(path);
        }
        return jsonpath;
    }

    public static Object read(String json, String path) {
        return compile(path).eval(JSON.parse(json));
    }

    public static <T> T read(String str, String str2, Type type, ParserConfig parserConfig) {
        return (T) compile(str2).eval(JSON.parse(str), type, parserConfig);
    }

    public static <T> T read(String str, String str2, Type type) {
        return (T) read(str, str2, type, null);
    }

    public static Object extract(String json, String path, ParserConfig config, int features, Feature... optionFeatures) {
        DefaultJSONParser parser = new DefaultJSONParser(json, config, features | Feature.OrderedField.mask);
        JSONPath jsonPath = compile(path);
        Object result = jsonPath.extract(parser);
        parser.lexer.close();
        return result;
    }

    public static Object extract(String json, String path) {
        return extract(json, path, ParserConfig.global, JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
    }

    public static Map<String, Object> paths(Object javaObject) {
        return paths(javaObject, SerializeConfig.globalInstance);
    }

    public static Map<String, Object> paths(Object javaObject, SerializeConfig config) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Map<Object, String> values = new IdentityHashMap<>();
        Map<String, Object> paths = new HashMap<>();
        paths(values, paths, "/", javaObject, config);
        return paths;
    }

    private static void paths(Map<Object, String> values, Map<String, Object> paths, String parent, Object javaObject, SerializeConfig config) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (javaObject == null) {
            return;
        }
        String p = values.put(javaObject, parent);
        if (p != null) {
            Class<?> type = javaObject.getClass();
            boolean basicType = type == String.class || type == Boolean.class || type == Character.class || type == UUID.class || type.isEnum() || (javaObject instanceof Number) || (javaObject instanceof Date);
            if (!basicType) {
                return;
            }
        }
        paths.put(parent, javaObject);
        if (javaObject instanceof Map) {
            Map map = (Map) javaObject;
            for (Object entryObj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) entryObj;
                Object key = entry.getKey();
                if (key instanceof String) {
                    String path = (parent.equals("/") ? new StringBuilder() : new StringBuilder().append(parent)).append("/").append(key).toString();
                    paths(values, paths, path, entry.getValue(), config);
                }
            }
            return;
        }
        if (javaObject instanceof Collection) {
            Collection collection = (Collection) javaObject;
            int i = 0;
            for (Object item : collection) {
                String path2 = (parent.equals("/") ? new StringBuilder() : new StringBuilder().append(parent)).append("/").append(i).toString();
                paths(values, paths, path2, item, config);
                i++;
            }
            return;
        }
        Class<?> clazz = javaObject.getClass();
        if (clazz.isArray()) {
            int len = Array.getLength(javaObject);
            for (int i2 = 0; i2 < len; i2++) {
                Object item2 = Array.get(javaObject, i2);
                String path3 = (parent.equals("/") ? new StringBuilder() : new StringBuilder().append(parent)).append("/").append(i2).toString();
                paths(values, paths, path3, item2, config);
            }
            return;
        }
        if (ParserConfig.isPrimitive2(clazz) || clazz.isEnum()) {
            return;
        }
        ObjectSerializer serializer = config.getObjectWriter(clazz);
        if (serializer instanceof JavaBeanSerializer) {
            JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) serializer;
            try {
                Map<String, Object> fieldValues = javaBeanSerializer.getFieldValuesMap(javaObject);
                for (Map.Entry<String, Object> entry2 : fieldValues.entrySet()) {
                    String key2 = entry2.getKey();
                    if (key2 instanceof String) {
                        String path4 = (parent.equals("/") ? new StringBuilder().append("/").append(key2) : new StringBuilder().append(parent).append("/").append(key2)).toString();
                        paths(values, paths, path4, entry2.getValue(), config);
                    }
                }
            } catch (Exception e) {
                throw new JSONException("toJSON error", e);
            }
        }
    }

    public String getPath() {
        return this.path;
    }

    static class JSONPathParser {
        private char ch;
        private boolean hasRefSegment;
        private int level;
        private final String path;
        private int pos;
        private static final String strArrayRegex = "'\\s*,\\s*'";
        private static final Pattern strArrayPatternx = Pattern.compile(strArrayRegex);

        public JSONPathParser(String path) {
            this.path = path;
            next();
        }

        void next() {
            String str = this.path;
            int i = this.pos;
            this.pos = i + 1;
            this.ch = str.charAt(i);
        }

        char getNextChar() {
            return this.path.charAt(this.pos);
        }

        boolean isEOF() {
            return this.pos >= this.path.length();
        }

        Segment readSegement() {
            if (this.level == 0 && this.path.length() == 1) {
                if (isDigitFirst(this.ch)) {
                    int index = this.ch - '0';
                    return new ArrayAccessSegment(index);
                }
                int index2 = this.ch;
                if ((index2 >= 97 && this.ch <= 'z') || (this.ch >= 'A' && this.ch <= 'Z')) {
                    return new PropertySegment(Character.toString(this.ch), false);
                }
            }
            while (!isEOF()) {
                skipWhitespace();
                if (this.ch == '$') {
                    next();
                    skipWhitespace();
                    if (this.ch == '?') {
                        return new FilterSegment((Filter) parseArrayAccessFilter(false));
                    }
                } else {
                    if (this.ch == '.' || this.ch == '/') {
                        int c0 = this.ch;
                        boolean deep = false;
                        next();
                        if (c0 == 46 && this.ch == '.') {
                            next();
                            deep = true;
                            if (this.path.length() > this.pos + 3 && this.ch == '[' && this.path.charAt(this.pos) == '*' && this.path.charAt(this.pos + 1) == ']' && this.path.charAt(this.pos + 2) == '.') {
                                next();
                                next();
                                next();
                                next();
                            }
                        }
                        if (this.ch == '*' || (deep && this.ch == '[')) {
                            boolean objectOnly = this.ch == '[';
                            if (!isEOF()) {
                                next();
                            }
                            if (deep) {
                                if (objectOnly) {
                                    return WildCardSegment.instance_deep_objectOnly;
                                }
                                return WildCardSegment.instance_deep;
                            }
                            return WildCardSegment.instance;
                        }
                        if (isDigitFirst(this.ch)) {
                            return parseArrayAccess(false);
                        }
                        String propertyName = readName();
                        if (this.ch == '(') {
                            next();
                            if (this.ch == ')') {
                                if (!isEOF()) {
                                    next();
                                }
                                if ("size".equals(propertyName) || "length".equals(propertyName)) {
                                    return SizeSegment.instance;
                                }
                                if ("max".equals(propertyName)) {
                                    return MaxSegment.instance;
                                }
                                if ("min".equals(propertyName)) {
                                    return MinSegment.instance;
                                }
                                if ("keySet".equals(propertyName)) {
                                    return KeySetSegment.instance;
                                }
                                if ("type".equals(propertyName)) {
                                    return TypeSegment.instance;
                                }
                                if ("floor".equals(propertyName)) {
                                    return FloorSegment.instance;
                                }
                                throw new JSONPathException("not support jsonpath : " + this.path);
                            }
                            throw new JSONPathException("not support jsonpath : " + this.path);
                        }
                        return new PropertySegment(propertyName, deep);
                    }
                    if (this.ch == '[') {
                        return parseArrayAccess(true);
                    }
                    if (this.level == 0) {
                        return new PropertySegment(readName(), false);
                    }
                    if (this.ch == '?') {
                        return new FilterSegment((Filter) parseArrayAccessFilter(false));
                    }
                    throw new JSONPathException("not support jsonpath : " + this.path);
                }
            }
            return null;
        }

        public final void skipWhitespace() {
            while (this.ch <= ' ') {
                if (this.ch == ' ' || this.ch == '\r' || this.ch == '\n' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b') {
                    next();
                } else {
                    return;
                }
            }
        }

        Segment parseArrayAccess(boolean acceptBracket) throws NumberFormatException {
            Object object = parseArrayAccessFilter(acceptBracket);
            if (object instanceof Segment) {
                return (Segment) object;
            }
            return new FilterSegment((Filter) object);
        }

        Object parseArrayAccessFilter(boolean acceptBracket) throws NumberFormatException {
            Filter filter;
            String[] containsValues;
            Filter filter2;
            Operator op;
            Filter filter3;
            int end;
            String propName;
            char c2;
            if (acceptBracket) {
                accept('[');
            }
            boolean predicateFlag = false;
            int lparanCount = 0;
            if (this.ch == '?') {
                next();
                accept('(');
                while (true) {
                    lparanCount++;
                    if (this.ch != '(') {
                        break;
                    }
                    next();
                }
                predicateFlag = true;
            }
            skipWhitespace();
            if (predicateFlag || IOUtils.firstIdentifier(this.ch) || Character.isJavaIdentifierStart(this.ch) || this.ch == '\\' || this.ch == '@') {
                if (this.ch == '@') {
                    next();
                    accept('.');
                }
                String propertyName = readName();
                skipWhitespace();
                if (predicateFlag && this.ch == ')') {
                    next();
                    Filter filter4 = new NotNullSegement(propertyName, false);
                    while (this.ch == ' ') {
                        next();
                    }
                    if (this.ch == '&' || this.ch == '|') {
                        filter4 = filterRest(filter4);
                    }
                    if (acceptBracket) {
                        accept(']');
                    }
                    return filter4;
                }
                if (acceptBracket && this.ch == ']') {
                    if (isEOF() && propertyName.equals("last")) {
                        return new MultiIndexSegment(new int[]{-1});
                    }
                    next();
                    Filter filter5 = new NotNullSegement(propertyName, false);
                    while (this.ch == ' ') {
                        next();
                    }
                    if (this.ch == '&' || this.ch == '|') {
                        filter5 = filterRest(filter5);
                    }
                    accept(')');
                    if (predicateFlag) {
                        accept(')');
                    }
                    if (acceptBracket) {
                        accept(']');
                    }
                    return filter5;
                }
                boolean function = false;
                skipWhitespace();
                if (this.ch == '(') {
                    next();
                    accept(')');
                    skipWhitespace();
                    function = true;
                }
                Operator op2 = readOp();
                skipWhitespace();
                if (op2 == Operator.BETWEEN || op2 == Operator.NOT_BETWEEN) {
                    boolean not = op2 == Operator.NOT_BETWEEN;
                    Object startValue = readValue();
                    String name = readName();
                    if (!"and".equalsIgnoreCase(name)) {
                        throw new JSONPathException(this.path);
                    }
                    Object endValue = readValue();
                    if (startValue == null || endValue == null) {
                        throw new JSONPathException(this.path);
                    }
                    if (JSONPath.isInt(startValue.getClass()) && JSONPath.isInt(endValue.getClass())) {
                        return new IntBetweenSegement(propertyName, function, TypeUtils.longExtractValue((Number) startValue), TypeUtils.longExtractValue((Number) endValue), not);
                    }
                    throw new JSONPathException(this.path);
                }
                if (op2 == Operator.IN || op2 == Operator.NOT_IN) {
                    boolean not2 = op2 == Operator.NOT_IN;
                    accept('(');
                    List<Object> valueList = new JSONArray();
                    Object value = readValue();
                    valueList.add(value);
                    while (true) {
                        skipWhitespace();
                        if (this.ch != ',') {
                            break;
                        }
                        next();
                        Object value2 = readValue();
                        valueList.add(value2);
                        lparanCount = lparanCount;
                    }
                    boolean isInt = true;
                    boolean isIntObj = true;
                    boolean isString = true;
                    for (Object item : valueList) {
                        if (item == null) {
                            if (isInt) {
                                isInt = false;
                            }
                        } else {
                            Class<?> clazz = item.getClass();
                            if (isInt && clazz != Byte.class && clazz != Short.class && clazz != Integer.class && clazz != Long.class) {
                                isInt = false;
                                isIntObj = false;
                            }
                            if (isString && clazz != String.class) {
                                isString = false;
                            }
                        }
                    }
                    if (valueList.size() == 1 && valueList.get(0) == null) {
                        if (not2) {
                            filter = new NotNullSegement(propertyName, function);
                        } else {
                            filter = new NullSegement(propertyName, function);
                        }
                        while (this.ch == ' ') {
                            next();
                        }
                        if (this.ch == '&' || this.ch == '|') {
                            filter = filterRest(filter);
                        }
                        accept(')');
                        if (predicateFlag) {
                            accept(')');
                        }
                        if (acceptBracket) {
                            accept(']');
                        }
                        return filter;
                    }
                    if (isInt) {
                        if (valueList.size() == 1) {
                            long value3 = TypeUtils.longExtractValue((Number) valueList.get(0));
                            Operator intOp = not2 ? Operator.NE : Operator.EQ;
                            Filter filter6 = new IntOpSegement(propertyName, function, value3, intOp);
                            while (true) {
                                int lparanCount2 = lparanCount;
                                if (this.ch != ' ') {
                                    break;
                                }
                                next();
                                lparanCount = lparanCount2;
                            }
                            if (this.ch == '&' || this.ch == '|') {
                                filter6 = filterRest(filter6);
                            }
                            accept(')');
                            if (predicateFlag) {
                                accept(')');
                            }
                            if (acceptBracket) {
                                accept(']');
                            }
                            return filter6;
                        }
                        long[] values = new long[valueList.size()];
                        for (int i = 0; i < values.length; i++) {
                            values[i] = TypeUtils.longExtractValue((Number) valueList.get(i));
                        }
                        Filter filter7 = new IntInSegement(propertyName, function, values, not2);
                        while (this.ch == ' ') {
                            next();
                        }
                        if (this.ch == '&' || this.ch == '|') {
                            filter7 = filterRest(filter7);
                        }
                        accept(')');
                        if (predicateFlag) {
                            accept(')');
                        }
                        if (acceptBracket) {
                            accept(']');
                        }
                        return filter7;
                    }
                    if (isString) {
                        if (valueList.size() == 1) {
                            String value4 = (String) valueList.get(0);
                            Operator intOp2 = not2 ? Operator.NE : Operator.EQ;
                            Filter filter8 = new StringOpSegement(propertyName, function, value4, intOp2);
                            while (this.ch == ' ') {
                                next();
                            }
                            if (this.ch == '&' || this.ch == '|') {
                                filter8 = filterRest(filter8);
                            }
                            accept(')');
                            if (predicateFlag) {
                                accept(')');
                            }
                            if (acceptBracket) {
                                accept(']');
                            }
                            return filter8;
                        }
                        String[] values2 = new String[valueList.size()];
                        valueList.toArray(values2);
                        Filter filter9 = new StringInSegement(propertyName, function, values2, not2);
                        while (this.ch == ' ') {
                            next();
                        }
                        if (this.ch == '&' || this.ch == '|') {
                            filter9 = filterRest(filter9);
                        }
                        accept(')');
                        if (predicateFlag) {
                            accept(')');
                        }
                        if (acceptBracket) {
                            accept(']');
                        }
                        return filter9;
                    }
                    if (isIntObj) {
                        Long[] values3 = new Long[valueList.size()];
                        for (int i2 = 0; i2 < values3.length; i2++) {
                            Number item2 = (Number) valueList.get(i2);
                            if (item2 != null) {
                                values3[i2] = Long.valueOf(TypeUtils.longExtractValue(item2));
                            }
                        }
                        Filter filter10 = new IntObjInSegement(propertyName, function, values3, not2);
                        while (this.ch == ' ') {
                            next();
                        }
                        if (this.ch == '&' || this.ch == '|') {
                            filter10 = filterRest(filter10);
                        }
                        accept(')');
                        if (predicateFlag) {
                            accept(')');
                        }
                        if (acceptBracket) {
                            accept(']');
                        }
                        return filter10;
                    }
                    throw new UnsupportedOperationException();
                }
                if (this.ch == '\'' || this.ch == '\"') {
                    String strValue = readString();
                    if (op2 == Operator.RLIKE) {
                        filter2 = new RlikeSegement(propertyName, function, strValue, false);
                    } else if (op2 == Operator.NOT_RLIKE) {
                        filter2 = new RlikeSegement(propertyName, function, strValue, true);
                    } else if (op2 == Operator.LIKE || op2 == Operator.NOT_LIKE) {
                        while (strValue.indexOf("%%") != -1) {
                            strValue = strValue.replaceAll("%%", "%");
                        }
                        boolean not3 = op2 == Operator.NOT_LIKE;
                        int p0 = strValue.indexOf(37);
                        if (p0 == -1) {
                            if (op2 == Operator.LIKE) {
                                op = Operator.EQ;
                            } else {
                                op = Operator.NE;
                            }
                            filter2 = new StringOpSegement(propertyName, function, strValue, op);
                        } else {
                            String[] items = strValue.split("%");
                            String startsWithValue = null;
                            String endsWithValue = null;
                            if (p0 == 0) {
                                if (strValue.charAt(strValue.length() - 1) == '%') {
                                    containsValues = new String[items.length - 1];
                                    System.arraycopy(items, 1, containsValues, 0, containsValues.length);
                                } else {
                                    endsWithValue = items[items.length - 1];
                                    if (items.length <= 2) {
                                        containsValues = null;
                                    } else {
                                        containsValues = new String[items.length - 2];
                                        System.arraycopy(items, 1, containsValues, 0, containsValues.length);
                                    }
                                }
                            } else if (strValue.charAt(strValue.length() - 1) == '%') {
                                if (items.length == 1) {
                                    startsWithValue = items[0];
                                    containsValues = null;
                                } else {
                                    containsValues = items;
                                }
                            } else if (items.length == 1) {
                                startsWithValue = items[0];
                                containsValues = null;
                            } else if (items.length == 2) {
                                startsWithValue = items[0];
                                endsWithValue = items[1];
                                containsValues = null;
                            } else {
                                startsWithValue = items[0];
                                endsWithValue = items[items.length - 1];
                                String[] containsValues2 = new String[items.length - 2];
                                System.arraycopy(items, 1, containsValues2, 0, containsValues2.length);
                                containsValues = containsValues2;
                            }
                            filter2 = new MatchSegement(propertyName, function, startsWithValue, endsWithValue, containsValues, not3);
                        }
                    } else {
                        filter2 = new StringOpSegement(propertyName, function, strValue, op2);
                    }
                    while (this.ch == ' ') {
                        next();
                    }
                    if (this.ch == '&' || this.ch == '|') {
                        filter2 = filterRest(filter2);
                    }
                    if (predicateFlag) {
                        accept(')');
                    }
                    if (acceptBracket) {
                        accept(']');
                    }
                    return filter2;
                }
                if (isDigitFirst(this.ch)) {
                    long value5 = readLongValue();
                    double doubleValue = 0.0d;
                    if (this.ch == '.') {
                        doubleValue = readDoubleValue(value5);
                    }
                    if (doubleValue == 0.0d) {
                        filter3 = new IntOpSegement(propertyName, function, value5, op2);
                    } else {
                        filter3 = new DoubleOpSegement(propertyName, function, doubleValue, op2);
                    }
                    while (this.ch == ' ') {
                        next();
                    }
                    if (lparanCount > 1 && this.ch == ')') {
                        next();
                        lparanCount--;
                    }
                    if (this.ch == '&' || this.ch == '|') {
                        filter3 = filterRest(filter3);
                    }
                    if (predicateFlag) {
                        int i3 = lparanCount - 1;
                        accept(')');
                    }
                    if (acceptBracket) {
                        accept(']');
                    }
                    return filter3;
                }
                if (this.ch == '$') {
                    Segment segment = readSegement();
                    RefOpSegement filter11 = new RefOpSegement(propertyName, function, segment, op2);
                    this.hasRefSegment = true;
                    while (this.ch == ' ') {
                        next();
                    }
                    if (predicateFlag) {
                        accept(')');
                    }
                    if (acceptBracket) {
                        accept(']');
                    }
                    return filter11;
                }
                if (this.ch == '/') {
                    int flags = 0;
                    StringBuilder regBuf = new StringBuilder();
                    while (true) {
                        next();
                        if (this.ch == '/') {
                            break;
                        }
                        if (this.ch == '\\') {
                            next();
                            regBuf.append(this.ch);
                        } else {
                            regBuf.append(this.ch);
                        }
                    }
                    next();
                    if (this.ch == 'i') {
                        next();
                        flags = 0 | 2;
                    }
                    Pattern pattern = Pattern.compile(regBuf.toString(), flags);
                    RegMatchSegement filter12 = new RegMatchSegement(propertyName, function, pattern, op2);
                    if (predicateFlag) {
                        accept(')');
                    }
                    if (acceptBracket) {
                        accept(']');
                    }
                    return filter12;
                }
                int flags2 = this.ch;
                if (flags2 != 110) {
                    if (this.ch == 't') {
                        String name2 = readName();
                        if ("true".equals(name2)) {
                            Filter filter13 = null;
                            if (op2 == Operator.EQ) {
                                filter13 = new ValueSegment(propertyName, function, Boolean.TRUE, true);
                            } else if (op2 == Operator.NE) {
                                filter13 = new ValueSegment(propertyName, function, Boolean.TRUE, false);
                            }
                            if (filter13 != null) {
                                while (this.ch == ' ') {
                                    next();
                                }
                                if (this.ch == '&' || this.ch == '|') {
                                    filter13 = filterRest(filter13);
                                }
                            }
                            if (predicateFlag) {
                                accept(')');
                            }
                            accept(']');
                            if (filter13 != null) {
                                return filter13;
                            }
                            throw new UnsupportedOperationException();
                        }
                    } else if (this.ch == 'f') {
                        String name3 = readName();
                        if (Bugly.SDK_IS_DEV.equals(name3)) {
                            Filter filter14 = null;
                            if (op2 == Operator.EQ) {
                                filter14 = new ValueSegment(propertyName, function, Boolean.FALSE, true);
                            } else if (op2 == Operator.NE) {
                                filter14 = new ValueSegment(propertyName, function, Boolean.FALSE, false);
                            }
                            if (filter14 != null) {
                                while (this.ch == ' ') {
                                    next();
                                }
                                if (this.ch == '&' || this.ch == '|') {
                                    filter14 = filterRest(filter14);
                                }
                            }
                            if (predicateFlag) {
                                accept(')');
                            }
                            accept(']');
                            if (filter14 != null) {
                                return filter14;
                            }
                            throw new UnsupportedOperationException();
                        }
                    }
                } else {
                    String name4 = readName();
                    if ("null".equals(name4)) {
                        Filter filter15 = null;
                        if (op2 == Operator.EQ) {
                            filter15 = new NullSegement(propertyName, function);
                        } else if (op2 == Operator.NE) {
                            filter15 = new NotNullSegement(propertyName, function);
                        }
                        if (filter15 != null) {
                            while (this.ch == ' ') {
                                next();
                            }
                            if (this.ch == '&' || this.ch == '|') {
                                filter15 = filterRest(filter15);
                            }
                        }
                        if (predicateFlag) {
                            accept(')');
                        }
                        accept(']');
                        if (filter15 != null) {
                            return filter15;
                        }
                        throw new UnsupportedOperationException();
                    }
                }
                throw new UnsupportedOperationException();
            }
            int start = this.pos - 1;
            char startCh = this.ch;
            while (this.ch != ']' && this.ch != '/' && !isEOF() && (this.ch != '.' || predicateFlag || predicateFlag || startCh == '\'')) {
                if (this.ch == '\\') {
                    next();
                }
                next();
            }
            if (acceptBracket) {
                end = this.pos - 1;
            } else if (this.ch == '/' || this.ch == '.') {
                int end2 = this.pos;
                end = end2 - 1;
            } else {
                end = this.pos;
            }
            String text = this.path.substring(start, end);
            if (text.indexOf(92) != 0) {
                StringBuilder buf = new StringBuilder(text.length());
                int i4 = 0;
                while (i4 < text.length()) {
                    char ch = text.charAt(i4);
                    if (ch == '\\' && i4 < text.length() - 1 && ((c2 = text.charAt(i4 + 1)) == '@' || ch == '\\' || ch == '\"')) {
                        buf.append(c2);
                        i4++;
                    } else {
                        buf.append(ch);
                    }
                    i4++;
                }
                text = buf.toString();
            }
            if (text.indexOf("\\.") != -1) {
                if (startCh != '\'' || text.length() <= 2 || text.charAt(text.length() - 1) != startCh) {
                    propName = text.replaceAll("\\\\\\.", "\\.");
                    if (propName.indexOf("\\-") != -1) {
                        propName = propName.replaceAll("\\\\-", "-");
                    }
                } else {
                    propName = text.substring(1, text.length() - 1);
                }
                if (predicateFlag) {
                    accept(')');
                }
                return new PropertySegment(propName, false);
            }
            Segment segment2 = buildArraySegement(text);
            if (acceptBracket && !isEOF()) {
                accept(']');
            }
            return segment2;
        }

        Filter filterRest(Filter filter) {
            boolean and = this.ch == '&';
            if ((this.ch == '&' && getNextChar() == '&') || (this.ch == '|' && getNextChar() == '|')) {
                next();
                next();
                boolean paren = false;
                if (this.ch == '(') {
                    paren = true;
                    next();
                }
                while (this.ch == ' ') {
                    next();
                }
                Filter right = (Filter) parseArrayAccessFilter(false);
                filter = new FilterGroup(filter, right, and);
                if (paren && this.ch == ')') {
                    next();
                }
            }
            return filter;
        }

        protected long readLongValue() throws NumberFormatException {
            int beginIndex = this.pos - 1;
            if (this.ch == '+' || this.ch == '-') {
                next();
            }
            while (this.ch >= '0' && this.ch <= '9') {
                next();
            }
            int endIndex = this.pos - 1;
            String text = this.path.substring(beginIndex, endIndex);
            long value = Long.parseLong(text);
            return value;
        }

        protected double readDoubleValue(long longValue) throws NumberFormatException {
            int beginIndex = this.pos - 1;
            next();
            while (this.ch >= '0' && this.ch <= '9') {
                next();
            }
            int endIndex = this.pos - 1;
            String text = this.path.substring(beginIndex, endIndex);
            double value = Double.parseDouble(text);
            double d = longValue;
            Double.isNaN(d);
            return value + d;
        }

        protected Object readValue() {
            skipWhitespace();
            if (isDigitFirst(this.ch)) {
                return Long.valueOf(readLongValue());
            }
            if (this.ch == '\"' || this.ch == '\'') {
                return readString();
            }
            if (this.ch == 'n') {
                String name = readName();
                if ("null".equals(name)) {
                    return null;
                }
                throw new JSONPathException(this.path);
            }
            throw new UnsupportedOperationException();
        }

        static boolean isDigitFirst(char ch) {
            return ch == '-' || ch == '+' || (ch >= '0' && ch <= '9');
        }

        protected Operator readOp() {
            Operator op = null;
            if (this.ch == '=') {
                next();
                if (this.ch == '~') {
                    next();
                    op = Operator.REG_MATCH;
                } else if (this.ch == '=') {
                    next();
                    op = Operator.EQ;
                } else {
                    op = Operator.EQ;
                }
            } else if (this.ch == '!') {
                next();
                accept('=');
                op = Operator.NE;
            } else if (this.ch == '<') {
                next();
                if (this.ch == '=') {
                    next();
                    op = Operator.LE;
                } else {
                    op = Operator.LT;
                }
            } else if (this.ch == '>') {
                next();
                if (this.ch == '=') {
                    next();
                    op = Operator.GE;
                } else {
                    op = Operator.GT;
                }
            }
            if (op == null) {
                String name = readName();
                if ("not".equalsIgnoreCase(name)) {
                    skipWhitespace();
                    String name2 = readName();
                    if ("like".equalsIgnoreCase(name2)) {
                        Operator op2 = Operator.NOT_LIKE;
                        return op2;
                    }
                    if ("rlike".equalsIgnoreCase(name2)) {
                        Operator op3 = Operator.NOT_RLIKE;
                        return op3;
                    }
                    if ("in".equalsIgnoreCase(name2)) {
                        Operator op4 = Operator.NOT_IN;
                        return op4;
                    }
                    if ("between".equalsIgnoreCase(name2)) {
                        Operator op5 = Operator.NOT_BETWEEN;
                        return op5;
                    }
                    throw new UnsupportedOperationException();
                }
                if ("nin".equalsIgnoreCase(name)) {
                    Operator op6 = Operator.NOT_IN;
                    return op6;
                }
                if ("like".equalsIgnoreCase(name)) {
                    Operator op7 = Operator.LIKE;
                    return op7;
                }
                if ("rlike".equalsIgnoreCase(name)) {
                    Operator op8 = Operator.RLIKE;
                    return op8;
                }
                if ("in".equalsIgnoreCase(name)) {
                    Operator op9 = Operator.IN;
                    return op9;
                }
                if ("between".equalsIgnoreCase(name)) {
                    Operator op10 = Operator.BETWEEN;
                    return op10;
                }
                throw new UnsupportedOperationException();
            }
            return op;
        }

        String readName() {
            skipWhitespace();
            if (this.ch != '\\' && !Character.isJavaIdentifierStart(this.ch)) {
                throw new JSONPathException("illeal jsonpath syntax. " + this.path);
            }
            StringBuilder buf = new StringBuilder();
            while (!isEOF()) {
                if (this.ch == '\\') {
                    next();
                    buf.append(this.ch);
                    if (isEOF()) {
                        return buf.toString();
                    }
                    next();
                } else {
                    boolean identifierFlag = Character.isJavaIdentifierPart(this.ch);
                    if (!identifierFlag) {
                        break;
                    }
                    buf.append(this.ch);
                    next();
                }
            }
            if (isEOF() && Character.isJavaIdentifierPart(this.ch)) {
                buf.append(this.ch);
            }
            return buf.toString();
        }

        String readString() {
            char quoate = this.ch;
            next();
            int beginIndex = this.pos - 1;
            while (this.ch != quoate && !isEOF()) {
                next();
            }
            String strValue = this.path.substring(beginIndex, isEOF() ? this.pos : this.pos - 1);
            accept(quoate);
            return strValue;
        }

        void accept(char expect) {
            if (this.ch == ' ') {
                next();
            }
            if (this.ch != expect) {
                throw new JSONPathException("expect '" + expect + ", but '" + this.ch + "'");
            }
            if (!isEOF()) {
                next();
            }
        }

        public Segment[] explain() {
            if (this.path == null || this.path.length() == 0) {
                throw new IllegalArgumentException();
            }
            Segment[] segments = new Segment[8];
            while (true) {
                Segment segment = readSegement();
                if (segment == null) {
                    break;
                }
                if (segment instanceof PropertySegment) {
                    PropertySegment propertySegment = (PropertySegment) segment;
                    if (propertySegment.deep || !propertySegment.propertyName.equals("*")) {
                    }
                }
                if (this.level == segments.length) {
                    Segment[] t = new Segment[(this.level * 3) / 2];
                    System.arraycopy(segments, 0, t, 0, this.level);
                    segments = t;
                }
                int i = this.level;
                this.level = i + 1;
                segments[i] = segment;
            }
            if (this.level == segments.length) {
                return segments;
            }
            Segment[] result = new Segment[this.level];
            System.arraycopy(segments, 0, result, 0, this.level);
            return result;
        }

        Segment buildArraySegement(String indexText) throws NumberFormatException {
            int end;
            int step;
            int indexTextLen = indexText.length();
            char firstChar = indexText.charAt(0);
            char lastChar = indexText.charAt(indexTextLen - 1);
            int commaIndex = indexText.indexOf(44);
            if (indexText.length() > 2 && firstChar == '\'' && lastChar == '\'') {
                String propertyName = indexText.substring(1, indexTextLen - 1);
                if (commaIndex == -1 || !strArrayPatternx.matcher(indexText).find()) {
                    return new PropertySegment(propertyName, false);
                }
                String[] propertyNames = propertyName.split(strArrayRegex);
                return new MultiPropertySegment(propertyNames);
            }
            int colonIndex = indexText.indexOf(58);
            if (commaIndex == -1 && colonIndex == -1) {
                if (TypeUtils.isNumber(indexText)) {
                    try {
                        int index = Integer.parseInt(indexText);
                        return new ArrayAccessSegment(index);
                    } catch (NumberFormatException e) {
                        return new PropertySegment(indexText, false);
                    }
                }
                if (indexText.charAt(0) == '\"' && indexText.charAt(indexText.length() - 1) == '\"') {
                    indexText = indexText.substring(1, indexText.length() - 1);
                }
                return new PropertySegment(indexText, false);
            }
            if (commaIndex != -1) {
                String[] indexesText = indexText.split(",");
                int[] indexes = new int[indexesText.length];
                for (int i = 0; i < indexesText.length; i++) {
                    indexes[i] = Integer.parseInt(indexesText[i]);
                }
                return new MultiIndexSegment(indexes);
            }
            if (colonIndex != -1) {
                String[] indexesText2 = indexText.split(":");
                int[] indexes2 = new int[indexesText2.length];
                for (int i2 = 0; i2 < indexesText2.length; i2++) {
                    String str = indexesText2[i2];
                    if (str.length() == 0) {
                        if (i2 == 0) {
                            indexes2[i2] = 0;
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    } else {
                        indexes2[i2] = Integer.parseInt(str);
                    }
                }
                int start = indexes2[0];
                if (indexes2.length > 1) {
                    end = indexes2[1];
                } else {
                    end = -1;
                }
                if (indexes2.length == 3) {
                    step = indexes2[2];
                } else {
                    step = 1;
                }
                if (end >= 0 && end < start) {
                    throw new UnsupportedOperationException("end must greater than or equals start. start " + start + ",  end " + end);
                }
                if (step <= 0) {
                    throw new UnsupportedOperationException("step must greater than zero : " + step);
                }
                return new RangeSegment(start, end, step);
            }
            throw new UnsupportedOperationException();
        }
    }

    static class SizeSegment implements Segment {
        public static final SizeSegment instance = new SizeSegment();

        SizeSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Integer eval(JSONPath path, Object rootObject, Object currentObject) {
            return Integer.valueOf(path.evalSize(currentObject));
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            Object object = parser.parse();
            context.object = Integer.valueOf(path.evalSize(object));
        }
    }

    static class TypeSegment implements Segment {
        public static final TypeSegment instance = new TypeSegment();

        TypeSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public String eval(JSONPath path, Object rootObject, Object currentObject) {
            if (currentObject == null) {
                return "null";
            }
            if (currentObject instanceof Collection) {
                return "array";
            }
            if (currentObject instanceof Number) {
                return "number";
            }
            if (currentObject instanceof Boolean) {
                return "boolean";
            }
            if ((currentObject instanceof String) || (currentObject instanceof UUID) || (currentObject instanceof Enum)) {
                return "string";
            }
            return "object";
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    static class FloorSegment implements Segment {
        public static final FloorSegment instance = new FloorSegment();

        FloorSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            if (currentObject instanceof JSONArray) {
                JSONArray array = (JSONArray) ((JSONArray) currentObject).clone();
                for (int i = 0; i < array.size(); i++) {
                    Object item = array.get(i);
                    Object newItem = floor(item);
                    if (newItem != item) {
                        array.set(i, newItem);
                    }
                }
                return array;
            }
            return floor(currentObject);
        }

        private static Object floor(Object item) {
            if (item == null) {
                return null;
            }
            if (item instanceof Float) {
                return Double.valueOf(Math.floor(((Float) item).floatValue()));
            }
            if (item instanceof Double) {
                return Double.valueOf(Math.floor(((Double) item).doubleValue()));
            }
            if (item instanceof BigDecimal) {
                BigDecimal decimal = (BigDecimal) item;
                return decimal.setScale(0, RoundingMode.FLOOR);
            }
            if ((item instanceof Byte) || (item instanceof Short) || (item instanceof Integer) || (item instanceof Long) || (item instanceof BigInteger)) {
                return item;
            }
            throw new UnsupportedOperationException();
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    static class MaxSegment implements Segment {
        public static final MaxSegment instance = new MaxSegment();

        MaxSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            Object max = null;
            if (currentObject instanceof Collection) {
                for (Object next : (Collection) currentObject) {
                    if (next != null) {
                        if (max == null) {
                            max = next;
                        } else if (JSONPath.compare(max, next) < 0) {
                            max = next;
                        }
                    }
                }
                return max;
            }
            throw new UnsupportedOperationException();
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    static class MinSegment implements Segment {
        public static final MinSegment instance = new MinSegment();

        MinSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            Object min = null;
            if (currentObject instanceof Collection) {
                for (Object next : (Collection) currentObject) {
                    if (next != null) {
                        if (min == null) {
                            min = next;
                        } else if (JSONPath.compare(min, next) > 0) {
                            min = next;
                        }
                    }
                }
                return min;
            }
            throw new UnsupportedOperationException();
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    static int compare(Object a, Object b) {
        if (a.getClass() == b.getClass()) {
            return ((Comparable) a).compareTo(b);
        }
        Class typeA = a.getClass();
        Class typeB = b.getClass();
        if (typeA == BigDecimal.class) {
            if (typeB == Integer.class) {
                b = new BigDecimal(((Integer) b).intValue());
            } else if (typeB == Long.class) {
                b = new BigDecimal(((Long) b).longValue());
            } else if (typeB == Float.class) {
                b = new BigDecimal(((Float) b).floatValue());
            } else if (typeB == Double.class) {
                b = new BigDecimal(((Double) b).doubleValue());
            }
        } else if (typeA == Long.class) {
            if (typeB == Integer.class) {
                b = new Long(((Integer) b).intValue());
            } else if (typeB == BigDecimal.class) {
                a = new BigDecimal(((Long) a).longValue());
            } else if (typeB == Float.class) {
                a = new Float(((Long) a).longValue());
            } else if (typeB == Double.class) {
                a = new Double(((Long) a).longValue());
            }
        } else if (typeA == Integer.class) {
            if (typeB == Long.class) {
                a = new Long(((Integer) a).intValue());
            } else if (typeB == BigDecimal.class) {
                a = new BigDecimal(((Integer) a).intValue());
            } else if (typeB == Float.class) {
                a = new Float(((Integer) a).intValue());
            } else if (typeB == Double.class) {
                a = new Double(((Integer) a).intValue());
            }
        } else if (typeA == Double.class) {
            if (typeB == Integer.class) {
                b = new Double(((Integer) b).intValue());
            } else if (typeB == Long.class) {
                b = new Double(((Long) b).longValue());
            } else if (typeB == Float.class) {
                b = new Double(((Float) b).floatValue());
            }
        } else if (typeA == Float.class) {
            if (typeB == Integer.class) {
                b = new Float(((Integer) b).intValue());
            } else if (typeB == Long.class) {
                b = new Float(((Long) b).longValue());
            } else if (typeB == Double.class) {
                a = new Double(((Float) a).floatValue());
            }
        }
        return ((Comparable) a).compareTo(b);
    }

    static class KeySetSegment implements Segment {
        public static final KeySetSegment instance = new KeySetSegment();

        KeySetSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            return path.evalKeySet(currentObject);
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    static class PropertySegment implements Segment {
        private final boolean deep;
        private final String propertyName;
        private final long propertyNameHash;

        public PropertySegment(String propertyName, boolean deep) {
            this.propertyName = propertyName;
            this.propertyNameHash = TypeUtils.fnv1a_64(propertyName);
            this.deep = deep;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            if (this.deep) {
                List<Object> results = new ArrayList<>();
                path.deepScan(currentObject, this.propertyName, results);
                return results;
            }
            return path.getPropertyValue(currentObject, this.propertyName, this.propertyNameHash);
        }

        /* JADX WARN: Removed duplicated region for block: B:113:0x00b3 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00c4  */
        @Override // com.alibaba.fastjson.JSONPath.Segment
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) throws NumberFormatException {
            Object value;
            Object value2;
            JSONArray array;
            Object value3;
            JSONLexerBase lexer = (JSONLexerBase) parser.lexer;
            if (this.deep && context.object == null) {
                context.object = new JSONArray();
            }
            if (lexer.token() == 14) {
                if ("*".equals(this.propertyName)) {
                    return;
                }
                lexer.nextToken();
                if (this.deep) {
                    array = (JSONArray) context.object;
                } else {
                    array = new JSONArray();
                }
                while (true) {
                    switch (lexer.token()) {
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            lexer.nextToken();
                            if (lexer.token() != 15) {
                                lexer.nextToken();
                                if (!this.deep && array.size() > 0) {
                                    context.object = array;
                                    return;
                                }
                                return;
                            }
                            if (lexer.token() == 16) {
                                lexer.nextToken();
                            } else {
                                throw new JSONException("illegal json : " + lexer.info());
                            }
                            break;
                        case 9:
                        case 10:
                        case 11:
                        case 13:
                        default:
                            if (lexer.token() != 15) {
                            }
                            break;
                        case 12:
                            if (this.deep) {
                                extract(path, parser, context);
                            } else {
                                int matchStat = lexer.seekObjectToField(this.propertyNameHash, this.deep);
                                if (matchStat == 3) {
                                    switch (lexer.token()) {
                                        case 2:
                                            value3 = lexer.integerValue();
                                            lexer.nextToken();
                                            break;
                                        case 3:
                                        default:
                                            value3 = parser.parse();
                                            break;
                                        case 4:
                                            value3 = lexer.stringVal();
                                            lexer.nextToken();
                                            break;
                                    }
                                    array.add(value3);
                                    if (lexer.token() == 13) {
                                        lexer.nextToken();
                                    } else {
                                        lexer.skipObject(false);
                                    }
                                } else if (matchStat == -1) {
                                    continue;
                                } else {
                                    if (this.deep) {
                                        throw new UnsupportedOperationException(lexer.info());
                                    }
                                    lexer.skipObject(false);
                                }
                            }
                            if (lexer.token() != 15) {
                            }
                            break;
                        case 14:
                            if (this.deep) {
                                extract(path, parser, context);
                            } else {
                                lexer.skipObject(false);
                            }
                            if (lexer.token() != 15) {
                            }
                            break;
                    }
                }
            } else {
                if (!this.deep) {
                    if (lexer.seekObjectToField(this.propertyNameHash, this.deep) == 3 && context.eval) {
                        switch (lexer.token()) {
                            case 2:
                                value2 = lexer.integerValue();
                                lexer.nextToken(16);
                                break;
                            case 3:
                                value2 = lexer.decimalValue();
                                lexer.nextToken(16);
                                break;
                            case 4:
                                value2 = lexer.stringVal();
                                lexer.nextToken(16);
                                break;
                            default:
                                value2 = parser.parse();
                                break;
                        }
                        if (context.eval) {
                            context.object = value2;
                            return;
                        }
                        return;
                    }
                    return;
                }
                while (true) {
                    int matchStat2 = lexer.seekObjectToField(this.propertyNameHash, this.deep);
                    if (matchStat2 != -1) {
                        if (matchStat2 == 3) {
                            if (context.eval) {
                                switch (lexer.token()) {
                                    case 2:
                                        value = lexer.integerValue();
                                        lexer.nextToken(16);
                                        break;
                                    case 3:
                                        value = lexer.decimalValue();
                                        lexer.nextToken(16);
                                        break;
                                    case 4:
                                        value = lexer.stringVal();
                                        lexer.nextToken(16);
                                        break;
                                    default:
                                        value = parser.parse();
                                        break;
                                }
                                if (context.eval) {
                                    if (context.object instanceof List) {
                                        List list = (List) context.object;
                                        if (list.size() == 0 && (value instanceof List)) {
                                            context.object = value;
                                        } else {
                                            list.add(value);
                                        }
                                    } else {
                                        context.object = value;
                                    }
                                }
                            }
                        } else if (matchStat2 == 1 || matchStat2 == 2) {
                            extract(path, parser, context);
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        public void setValue(JSONPath path, Object parent, Object value) {
            if (this.deep) {
                path.deepSet(parent, this.propertyName, this.propertyNameHash, value);
            } else {
                path.setPropertyValue(parent, this.propertyName, this.propertyNameHash, value);
            }
        }

        public boolean remove(JSONPath path, Object parent) {
            return path.removePropertyValue(parent, this.propertyName, this.deep);
        }
    }

    static class MultiPropertySegment implements Segment {
        private final String[] propertyNames;
        private final long[] propertyNamesHash;

        public MultiPropertySegment(String[] propertyNames) {
            this.propertyNames = propertyNames;
            this.propertyNamesHash = new long[propertyNames.length];
            for (int i = 0; i < this.propertyNamesHash.length; i++) {
                this.propertyNamesHash[i] = TypeUtils.fnv1a_64(propertyNames[i]);
            }
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            List<Object> fieldValues = new ArrayList<>(this.propertyNames.length);
            for (int i = 0; i < this.propertyNames.length; i++) {
                Object fieldValue = path.getPropertyValue(currentObject, this.propertyNames[i], this.propertyNamesHash[i]);
                fieldValues.add(fieldValue);
            }
            return fieldValues;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) throws NumberFormatException {
            JSONArray array;
            Object value;
            JSONLexerBase lexer = (JSONLexerBase) parser.lexer;
            if (context.object == null) {
                JSONArray jSONArray = new JSONArray();
                array = jSONArray;
                context.object = jSONArray;
            } else {
                array = (JSONArray) context.object;
            }
            for (int i = array.size(); i < this.propertyNamesHash.length; i++) {
                array.add(null);
            }
            do {
                int index = lexer.seekObjectToField(this.propertyNamesHash);
                int matchStat = lexer.matchStat;
                if (matchStat == 3) {
                    switch (lexer.token()) {
                        case 2:
                            value = lexer.integerValue();
                            lexer.nextToken(16);
                            break;
                        case 3:
                            value = lexer.decimalValue();
                            lexer.nextToken(16);
                            break;
                        case 4:
                            value = lexer.stringVal();
                            lexer.nextToken(16);
                            break;
                        default:
                            value = parser.parse();
                            break;
                    }
                    array.set(index, value);
                } else {
                    return;
                }
            } while (lexer.token() == 16);
        }
    }

    static class WildCardSegment implements Segment {
        public static final WildCardSegment instance = new WildCardSegment(false, false);
        public static final WildCardSegment instance_deep = new WildCardSegment(true, false);
        public static final WildCardSegment instance_deep_objectOnly = new WildCardSegment(true, true);
        private boolean deep;
        private boolean objectOnly;

        private WildCardSegment(boolean deep, boolean objectOnly) {
            this.deep = deep;
            this.objectOnly = objectOnly;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) throws Exception {
            if (!this.deep) {
                return path.getPropertyValues(currentObject);
            }
            List<Object> values = new ArrayList<>();
            path.deepGetPropertyValues(currentObject, values);
            return values;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) throws Exception {
            if (context.eval) {
                Object object = parser.parse();
                if (this.deep) {
                    List<Object> values = new ArrayList<>();
                    if (this.objectOnly) {
                        path.deepGetObjects(object, values);
                    } else {
                        path.deepGetPropertyValues(object, values);
                    }
                    context.object = values;
                    return;
                }
                if (object instanceof JSONObject) {
                    Collection<Object> values2 = ((JSONObject) object).values();
                    JSONArray array = new JSONArray(values2.size());
                    array.addAll(values2);
                    context.object = array;
                    return;
                }
                if (object instanceof JSONArray) {
                    context.object = object;
                    return;
                }
            }
            throw new JSONException("TODO");
        }
    }

    static class ArrayAccessSegment implements Segment {
        private final int index;

        public ArrayAccessSegment(int index) {
            this.index = index;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            return path.getArrayItem(currentObject, this.index);
        }

        public boolean setValue(JSONPath path, Object currentObject, Object value) {
            return path.setArrayItem(path, currentObject, this.index, value);
        }

        public boolean remove(JSONPath path, Object currentObject) {
            return path.removeArrayItem(path, currentObject, this.index);
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            JSONLexerBase lexer = (JSONLexerBase) parser.lexer;
            if (lexer.seekArrayToItem(this.index) && context.eval) {
                context.object = parser.parse();
            }
        }
    }

    static class MultiIndexSegment implements Segment {
        private final int[] indexes;

        public MultiIndexSegment(int[] indexes) {
            this.indexes = indexes;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            List<Object> items = new JSONArray(this.indexes.length);
            for (int i = 0; i < this.indexes.length; i++) {
                Object item = path.getArrayItem(currentObject, this.indexes[i]);
                items.add(item);
            }
            return items;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            if (context.eval) {
                Object object = parser.parse();
                if (object instanceof List) {
                    int[] indexes = new int[this.indexes.length];
                    System.arraycopy(this.indexes, 0, indexes, 0, indexes.length);
                    boolean noneNegative = indexes[0] >= 0;
                    List list = (List) object;
                    if (noneNegative) {
                        for (int i = list.size() - 1; i >= 0; i--) {
                            if (Arrays.binarySearch(indexes, i) < 0) {
                                list.remove(i);
                            }
                        }
                        context.object = list;
                        return;
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    static class RangeSegment implements Segment {
        private final int end;
        private final int start;
        private final int step;

        public RangeSegment(int start, int end, int step) {
            this.start = start;
            this.end = end;
            this.step = step;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            int size = SizeSegment.instance.eval(path, rootObject, currentObject).intValue();
            int start = this.start >= 0 ? this.start : this.start + size;
            int end = this.end >= 0 ? this.end : this.end + size;
            int array_size = ((end - start) / this.step) + 1;
            if (array_size == -1) {
                return null;
            }
            List<Object> items = new ArrayList<>(array_size);
            int i = start;
            while (i <= end && i < size) {
                Object item = path.getArrayItem(currentObject, i);
                items.add(item);
                i += this.step;
            }
            return items;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    static class NotNullSegement extends PropertyFilter {
        public NotNullSegement(String propertyName, boolean function) {
            super(propertyName, function);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            return path.getPropertyValue(item, this.propertyName, this.propertyNameHash) != null;
        }
    }

    static class NullSegement extends PropertyFilter {
        public NullSegement(String propertyName, boolean function) {
            super(propertyName, function);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            return propertyValue == null;
        }
    }

    static class ValueSegment extends PropertyFilter {
        private boolean eq;
        private final Object value;

        public ValueSegment(String propertyName, boolean function, Object value, boolean eq) {
            super(propertyName, function);
            this.eq = true;
            if (value == null) {
                throw new IllegalArgumentException("value is null");
            }
            this.value = value;
            this.eq = eq;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            boolean result = this.value.equals(propertyValue);
            if (!this.eq) {
                return !result;
            }
            return result;
        }
    }

    static class IntInSegement extends PropertyFilter {
        private final boolean not;
        private final long[] values;

        public IntInSegement(String propertyName, boolean function, long[] values, boolean not) {
            super(propertyName, function);
            this.values = values;
            this.not = not;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long longPropertyValue = TypeUtils.longExtractValue((Number) propertyValue);
                for (long value : this.values) {
                    if (value == longPropertyValue) {
                        return !this.not;
                    }
                }
            }
            return this.not;
        }
    }

    static class IntBetweenSegement extends PropertyFilter {
        private final long endValue;
        private final boolean not;
        private final long startValue;

        public IntBetweenSegement(String propertyName, boolean function, long startValue, long endValue, boolean not) {
            super(propertyName, function);
            this.startValue = startValue;
            this.endValue = endValue;
            this.not = not;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long longPropertyValue = TypeUtils.longExtractValue((Number) propertyValue);
                if (longPropertyValue >= this.startValue && longPropertyValue <= this.endValue) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    static class IntObjInSegement extends PropertyFilter {
        private final boolean not;
        private final Long[] values;

        public IntObjInSegement(String propertyName, boolean function, Long[] values, boolean not) {
            super(propertyName, function);
            this.values = values;
            this.not = not;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            int i = 0;
            if (propertyValue == null) {
                Long[] lArr = this.values;
                int length = lArr.length;
                while (i < length) {
                    if (lArr[i] != null) {
                        i++;
                    } else {
                        return !this.not;
                    }
                }
                return this.not;
            }
            if (propertyValue instanceof Number) {
                long longPropertyValue = TypeUtils.longExtractValue((Number) propertyValue);
                Long[] lArr2 = this.values;
                int length2 = lArr2.length;
                while (i < length2) {
                    Long value = lArr2[i];
                    if (value == null || value.longValue() != longPropertyValue) {
                        i++;
                    } else {
                        return !this.not;
                    }
                }
            }
            return this.not;
        }
    }

    static class StringInSegement extends PropertyFilter {
        private final boolean not;
        private final String[] values;

        public StringInSegement(String propertyName, boolean function, String[] values, boolean not) {
            super(propertyName, function);
            this.values = values;
            this.not = not;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            for (String value : this.values) {
                if (value == propertyValue) {
                    return !this.not;
                }
                if (value != null && value.equals(propertyValue)) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    static class IntOpSegement extends PropertyFilter {
        private final Operator op;
        private final long value;
        private BigDecimal valueDecimal;
        private Double valueDouble;
        private Float valueFloat;

        public IntOpSegement(String propertyName, boolean function, long value, Operator op) {
            super(propertyName, function);
            this.value = value;
            this.op = op;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            if (propertyValue instanceof BigDecimal) {
                if (this.valueDecimal == null) {
                    this.valueDecimal = BigDecimal.valueOf(this.value);
                }
                int result = this.valueDecimal.compareTo((BigDecimal) propertyValue);
                switch (this.op) {
                    case EQ:
                        if (result == 0) {
                        }
                        break;
                    case NE:
                        if (result != 0) {
                        }
                        break;
                    case GE:
                        if (result <= 0) {
                        }
                        break;
                    case GT:
                        if (result < 0) {
                        }
                        break;
                    case LE:
                        if (result >= 0) {
                        }
                        break;
                    case LT:
                        if (result > 0) {
                        }
                        break;
                }
                return false;
            }
            if (propertyValue instanceof Float) {
                if (this.valueFloat == null) {
                    this.valueFloat = Float.valueOf(this.value);
                }
                int result2 = this.valueFloat.compareTo((Float) propertyValue);
                switch (this.op) {
                    case EQ:
                        if (result2 == 0) {
                        }
                        break;
                    case NE:
                        if (result2 != 0) {
                        }
                        break;
                    case GE:
                        if (result2 <= 0) {
                        }
                        break;
                    case GT:
                        if (result2 < 0) {
                        }
                        break;
                    case LE:
                        if (result2 >= 0) {
                        }
                        break;
                    case LT:
                        if (result2 > 0) {
                        }
                        break;
                }
                return false;
            }
            if (propertyValue instanceof Double) {
                if (this.valueDouble == null) {
                    this.valueDouble = Double.valueOf(this.value);
                }
                int result3 = this.valueDouble.compareTo((Double) propertyValue);
                switch (this.op) {
                    case EQ:
                        if (result3 == 0) {
                        }
                        break;
                    case NE:
                        if (result3 != 0) {
                        }
                        break;
                    case GE:
                        if (result3 <= 0) {
                        }
                        break;
                    case GT:
                        if (result3 < 0) {
                        }
                        break;
                    case LE:
                        if (result3 >= 0) {
                        }
                        break;
                    case LT:
                        if (result3 > 0) {
                        }
                        break;
                }
                return false;
            }
            long longValue = TypeUtils.longExtractValue((Number) propertyValue);
            switch (this.op) {
                case EQ:
                    if (longValue == this.value) {
                    }
                    break;
                case NE:
                    if (longValue != this.value) {
                    }
                    break;
                case GE:
                    if (longValue >= this.value) {
                    }
                    break;
                case GT:
                    if (longValue > this.value) {
                    }
                    break;
                case LE:
                    if (longValue <= this.value) {
                    }
                    break;
                case LT:
                    if (longValue < this.value) {
                    }
                    break;
            }
            return false;
        }
    }

    static abstract class PropertyFilter implements Filter {
        static long TYPE = TypeUtils.fnv1a_64("type");
        protected final boolean function;
        protected Segment functionExpr;
        protected final String propertyName;
        protected final long propertyNameHash;

        protected PropertyFilter(String propertyName, boolean function) {
            this.propertyName = propertyName;
            this.propertyNameHash = TypeUtils.fnv1a_64(propertyName);
            this.function = function;
            if (function) {
                if (this.propertyNameHash == TYPE) {
                    this.functionExpr = TypeSegment.instance;
                } else {
                    if (this.propertyNameHash == JSONPath.SIZE) {
                        this.functionExpr = SizeSegment.instance;
                        return;
                    }
                    throw new JSONPathException("unsupported funciton : " + propertyName);
                }
            }
        }

        protected Object get(JSONPath path, Object rootObject, Object currentObject) {
            if (this.functionExpr != null) {
                return this.functionExpr.eval(path, rootObject, currentObject);
            }
            return path.getPropertyValue(currentObject, this.propertyName, this.propertyNameHash);
        }
    }

    static class DoubleOpSegement extends PropertyFilter {
        private final Operator op;
        private final double value;

        public DoubleOpSegement(String propertyName, boolean function, double value, Operator op) {
            super(propertyName, function);
            this.value = value;
            this.op = op;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            double doubleValue = ((Number) propertyValue).doubleValue();
            switch (this.op) {
                case EQ:
                    if (doubleValue == this.value) {
                    }
                    break;
                case NE:
                    if (doubleValue != this.value) {
                    }
                    break;
                case GE:
                    if (doubleValue >= this.value) {
                    }
                    break;
                case GT:
                    if (doubleValue > this.value) {
                    }
                    break;
                case LE:
                    if (doubleValue <= this.value) {
                    }
                    break;
                case LT:
                    if (doubleValue < this.value) {
                    }
                    break;
            }
            return false;
        }
    }

    static class RefOpSegement extends PropertyFilter {
        private final Operator op;
        private final Segment refSgement;

        public RefOpSegement(String propertyName, boolean function, Segment refSgement, Operator op) {
            super(propertyName, function);
            this.refSgement = refSgement;
            this.op = op;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            Object refValue = this.refSgement.eval(path, rootObject, rootObject);
            if ((refValue instanceof Integer) || (refValue instanceof Long) || (refValue instanceof Short) || (refValue instanceof Byte)) {
                long value = TypeUtils.longExtractValue((Number) refValue);
                if ((propertyValue instanceof Integer) || (propertyValue instanceof Long) || (propertyValue instanceof Short) || (propertyValue instanceof Byte)) {
                    long longValue = TypeUtils.longExtractValue((Number) propertyValue);
                    switch (this.op) {
                        case EQ:
                            return longValue == value;
                        case NE:
                            return longValue != value;
                        case GE:
                            return longValue >= value;
                        case GT:
                            return longValue > value;
                        case LE:
                            return longValue <= value;
                        case LT:
                            return longValue < value;
                    }
                }
                if (propertyValue instanceof BigDecimal) {
                    BigDecimal valueDecimal = BigDecimal.valueOf(value);
                    int result = valueDecimal.compareTo((BigDecimal) propertyValue);
                    switch (this.op) {
                        case EQ:
                            return result == 0;
                        case NE:
                            return result != 0;
                        case GE:
                            return result <= 0;
                        case GT:
                            return result < 0;
                        case LE:
                            return result >= 0;
                        case LT:
                            return result > 0;
                        default:
                            return false;
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    static class MatchSegement extends PropertyFilter {
        private final String[] containsValues;
        private final String endsWithValue;
        private final int minLength;
        private final boolean not;
        private final String startsWithValue;

        public MatchSegement(String propertyName, boolean function, String startsWithValue, String endsWithValue, String[] containsValues, boolean not) {
            super(propertyName, function);
            this.startsWithValue = startsWithValue;
            this.endsWithValue = endsWithValue;
            this.containsValues = containsValues;
            this.not = not;
            int len = startsWithValue != null ? 0 + startsWithValue.length() : 0;
            len = endsWithValue != null ? len + endsWithValue.length() : len;
            if (containsValues != null) {
                for (String item : containsValues) {
                    len += item.length();
                }
            }
            this.minLength = len;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            String strPropertyValue = propertyValue.toString();
            if (strPropertyValue.length() < this.minLength) {
                return this.not;
            }
            int start = 0;
            if (this.startsWithValue != null) {
                if (strPropertyValue.startsWith(this.startsWithValue)) {
                    start = 0 + this.startsWithValue.length();
                } else {
                    return this.not;
                }
            }
            if (this.containsValues != null) {
                for (String containsValue : this.containsValues) {
                    int index = strPropertyValue.indexOf(containsValue, start);
                    if (index == -1) {
                        return this.not;
                    }
                    start = index + containsValue.length();
                }
            }
            if (this.endsWithValue != null && !strPropertyValue.endsWith(this.endsWithValue)) {
                return this.not;
            }
            return !this.not;
        }
    }

    static class RlikeSegement extends PropertyFilter {
        private final boolean not;
        private final Pattern pattern;

        public RlikeSegement(String propertyName, boolean function, String pattern, boolean not) {
            super(propertyName, function);
            this.pattern = Pattern.compile(pattern);
            this.not = not;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            String strPropertyValue = propertyValue.toString();
            Matcher m = this.pattern.matcher(strPropertyValue);
            boolean match = m.matches();
            if (this.not) {
                return !match;
            }
            return match;
        }
    }

    static class StringOpSegement extends PropertyFilter {
        private final Operator op;
        private final String value;

        public StringOpSegement(String propertyName, boolean function, String value, Operator op) {
            super(propertyName, function);
            this.value = value;
            this.op = op;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (this.op == Operator.EQ) {
                return this.value.equals(propertyValue);
            }
            if (this.op == Operator.NE) {
                return !this.value.equals(propertyValue);
            }
            if (propertyValue == null) {
                return false;
            }
            int compareResult = this.value.compareTo(propertyValue.toString());
            return this.op == Operator.GE ? compareResult <= 0 : this.op == Operator.GT ? compareResult < 0 : this.op == Operator.LE ? compareResult >= 0 : this.op == Operator.LT && compareResult > 0;
        }
    }

    static class RegMatchSegement extends PropertyFilter {
        private final Operator op;
        private final Pattern pattern;

        public RegMatchSegement(String propertyName, boolean function, Pattern pattern, Operator op) {
            super(propertyName, function);
            this.pattern = pattern;
            this.op = op;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            String str = propertyValue.toString();
            Matcher m = this.pattern.matcher(str);
            return m.matches();
        }
    }

    public static class FilterSegment implements Segment {
        private final Filter filter;

        public FilterSegment(Filter filter) {
            this.filter = filter;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            if (currentObject == null) {
                return null;
            }
            List<Object> items = new JSONArray();
            if (currentObject instanceof Iterable) {
                for (Object item : (Iterable) currentObject) {
                    if (this.filter.apply(path, rootObject, currentObject, item)) {
                        items.add(item);
                    }
                }
                return items;
            }
            if (!this.filter.apply(path, rootObject, currentObject, currentObject)) {
                return null;
            }
            return currentObject;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            Object object = parser.parse();
            context.object = eval(path, object, object);
        }

        public boolean remove(JSONPath path, Object rootObject, Object currentObject) {
            if (currentObject == null || !(currentObject instanceof Iterable)) {
                return false;
            }
            Iterator it = ((Iterable) currentObject).iterator();
            while (it.hasNext()) {
                Object item = it.next();
                if (this.filter.apply(path, rootObject, currentObject, item)) {
                    it.remove();
                }
            }
            return true;
        }
    }

    static class FilterGroup implements Filter {
        private boolean and;
        private List<Filter> fitlers = new ArrayList(2);

        public FilterGroup(Filter left, Filter right, boolean and) {
            this.fitlers.add(left);
            this.fitlers.add(right);
            this.and = and;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            if (this.and) {
                for (Filter fitler : this.fitlers) {
                    if (!fitler.apply(path, rootObject, currentObject, item)) {
                        return false;
                    }
                }
                return true;
            }
            for (Filter fitler2 : this.fitlers) {
                if (fitler2.apply(path, rootObject, currentObject, item)) {
                    return true;
                }
            }
            return false;
        }
    }

    protected Object getArrayItem(Object currentObject, int index) {
        if (currentObject == null) {
            return null;
        }
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            if (index >= 0) {
                if (index >= list.size()) {
                    return null;
                }
                return list.get(index);
            }
            if (Math.abs(index) > list.size()) {
                return null;
            }
            return list.get(list.size() + index);
        }
        if (currentObject.getClass().isArray()) {
            int arrayLenth = Array.getLength(currentObject);
            if (index >= 0) {
                if (index >= arrayLenth) {
                    return null;
                }
                return Array.get(currentObject, index);
            }
            if (Math.abs(index) > arrayLenth) {
                return null;
            }
            return Array.get(currentObject, arrayLenth + index);
        }
        if (currentObject instanceof Map) {
            Map map = (Map) currentObject;
            Object value = map.get(Integer.valueOf(index));
            if (value == null) {
                return map.get(Integer.toString(index));
            }
            return value;
        }
        if (currentObject instanceof Collection) {
            Collection collection = (Collection) currentObject;
            int i = 0;
            for (Object item : collection) {
                if (i == index) {
                    return item;
                }
                i++;
            }
            return null;
        }
        if (index == 0) {
            return currentObject;
        }
        throw new UnsupportedOperationException();
    }

    public boolean setArrayItem(JSONPath path, Object currentObject, int index, Object value) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            if (index >= 0) {
                list.set(index, value);
            } else {
                list.set(list.size() + index, value);
            }
            return true;
        }
        Class<?> clazz = currentObject.getClass();
        if (clazz.isArray()) {
            int arrayLenth = Array.getLength(currentObject);
            if (index >= 0) {
                if (index < arrayLenth) {
                    Array.set(currentObject, index, value);
                }
            } else if (Math.abs(index) <= arrayLenth) {
                Array.set(currentObject, arrayLenth + index, value);
            }
            return true;
        }
        throw new JSONPathException("unsupported set operation." + clazz);
    }

    public boolean removeArrayItem(JSONPath path, Object currentObject, int index) {
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            if (index >= 0) {
                if (index >= list.size()) {
                    return false;
                }
                list.remove(index);
                return true;
            }
            int newIndex = list.size() + index;
            if (newIndex < 0) {
                return false;
            }
            list.remove(newIndex);
            return true;
        }
        Class<?> clazz = currentObject.getClass();
        throw new JSONPathException("unsupported set operation." + clazz);
    }

    protected Collection<Object> getPropertyValues(Object currentObject) {
        if (currentObject == null) {
            return null;
        }
        Class<?> currentClass = currentObject.getClass();
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentClass);
        if (beanSerializer != null) {
            try {
                return beanSerializer.getFieldValues(currentObject);
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path, e);
            }
        }
        if (currentObject instanceof Map) {
            Map map = (Map) currentObject;
            return map.values();
        }
        if (currentObject instanceof Collection) {
            return (Collection) currentObject;
        }
        throw new UnsupportedOperationException();
    }

    protected void deepGetObjects(Object currentObject, List<Object> outValues) {
        Class<?> currentClass = currentObject.getClass();
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentClass);
        Collection collection = null;
        if (beanSerializer != null) {
            try {
                collection = beanSerializer.getFieldValues(currentObject);
                outValues.add(currentObject);
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path, e);
            }
        } else if (currentObject instanceof Map) {
            outValues.add(currentObject);
            Map map = (Map) currentObject;
            collection = map.values();
        } else if (currentObject instanceof Collection) {
            collection = (Collection) currentObject;
        }
        if (collection != null) {
            for (Object fieldValue : collection) {
                if (fieldValue != null && !ParserConfig.isPrimitive2(fieldValue.getClass())) {
                    deepGetObjects(fieldValue, outValues);
                }
            }
            return;
        }
        throw new UnsupportedOperationException(currentClass.getName());
    }

    protected void deepGetPropertyValues(Object currentObject, List<Object> outValues) throws Exception {
        Class<?> currentClass = currentObject.getClass();
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentClass);
        Collection collection = null;
        if (beanSerializer != null) {
            try {
                collection = beanSerializer.getFieldValues(currentObject);
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path, e);
            }
        } else if (currentObject instanceof Map) {
            Map map = (Map) currentObject;
            collection = map.values();
        } else if (currentObject instanceof Collection) {
            collection = (Collection) currentObject;
        }
        if (collection != null) {
            for (Object fieldValue : collection) {
                if (fieldValue == null || ParserConfig.isPrimitive2(fieldValue.getClass())) {
                    outValues.add(fieldValue);
                } else {
                    deepGetPropertyValues(fieldValue, outValues);
                }
            }
            return;
        }
        throw new UnsupportedOperationException(currentClass.getName());
    }

    static boolean eq(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.getClass() == b.getClass()) {
            return a.equals(b);
        }
        if (a instanceof Number) {
            if (!(b instanceof Number)) {
                return false;
            }
            return eqNotNull((Number) a, (Number) b);
        }
        return a.equals(b);
    }

    static boolean eqNotNull(Number a, Number b) {
        Class clazzA = a.getClass();
        boolean isIntA = isInt(clazzA);
        Class clazzB = b.getClass();
        boolean isIntB = isInt(clazzB);
        if (a instanceof BigDecimal) {
            BigDecimal decimalA = (BigDecimal) a;
            if (isIntB) {
                return decimalA.equals(BigDecimal.valueOf(TypeUtils.longExtractValue(b)));
            }
        }
        if (isIntA) {
            if (isIntB) {
                return a.longValue() == b.longValue();
            }
            if (b instanceof BigInteger) {
                BigInteger bigIntB = (BigInteger) a;
                BigInteger bigIntA = BigInteger.valueOf(a.longValue());
                return bigIntA.equals(bigIntB);
            }
        }
        if (isIntB && (a instanceof BigInteger)) {
            BigInteger bigIntA2 = (BigInteger) a;
            BigInteger bigIntB2 = BigInteger.valueOf(TypeUtils.longExtractValue(b));
            return bigIntA2.equals(bigIntB2);
        }
        boolean isDoubleA = isDouble(clazzA);
        boolean isDoubleB = isDouble(clazzB);
        return ((isDoubleA && isDoubleB) || ((isDoubleA && isIntB) || (isDoubleB && isIntA))) && a.doubleValue() == b.doubleValue();
    }

    protected static boolean isDouble(Class<?> clazzA) {
        return clazzA == Float.class || clazzA == Double.class;
    }

    protected static boolean isInt(Class<?> clazzA) {
        return clazzA == Byte.class || clazzA == Short.class || clazzA == Integer.class || clazzA == Long.class;
    }

    protected Object getPropertyValue(Object currentObject, String propertyName, long propertyNameHash) {
        Object currentObject2;
        if (currentObject == null) {
            return null;
        }
        if (currentObject instanceof String) {
            try {
                currentObject2 = (JSONObject) JSON.parse((String) currentObject, this.parserConfig);
            } catch (Exception e) {
            }
        } else {
            currentObject2 = currentObject;
        }
        if (currentObject2 instanceof Map) {
            Map map = (Map) currentObject2;
            Object val = map.get(propertyName);
            if (val != null) {
                return val;
            }
            if (SIZE == propertyNameHash || LENGTH == propertyNameHash) {
                return Integer.valueOf(map.size());
            }
            return val;
        }
        Class<?> currentClass = currentObject2.getClass();
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentClass);
        if (beanSerializer != null) {
            try {
                return beanSerializer.getFieldValue(currentObject2, propertyName, propertyNameHash, false);
            } catch (Exception e2) {
                throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + propertyName, e2);
            }
        }
        if (currentObject2 instanceof List) {
            List list = (List) currentObject2;
            if (SIZE == propertyNameHash || LENGTH == propertyNameHash) {
                return Integer.valueOf(list.size());
            }
            List<Object> fieldValues = null;
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (obj == list) {
                    if (fieldValues == null) {
                        fieldValues = new JSONArray(list.size());
                    }
                    fieldValues.add(obj);
                } else {
                    Object itemValue = getPropertyValue(obj, propertyName, propertyNameHash);
                    if (itemValue instanceof Collection) {
                        Collection collection = (Collection) itemValue;
                        if (fieldValues == null) {
                            fieldValues = new JSONArray(list.size());
                        }
                        fieldValues.addAll(collection);
                    } else if (itemValue != null || !this.ignoreNullValue) {
                        if (fieldValues == null) {
                            fieldValues = new JSONArray(list.size());
                        }
                        fieldValues.add(itemValue);
                    }
                }
            }
            if (fieldValues == null) {
                return Collections.emptyList();
            }
            return fieldValues;
        }
        if (currentObject2 instanceof Object[]) {
            Object[] array = (Object[]) currentObject2;
            if (SIZE == propertyNameHash || LENGTH == propertyNameHash) {
                return Integer.valueOf(array.length);
            }
            List<Object> fieldValues2 = new JSONArray(array.length);
            for (Object[] objArr : array) {
                if (objArr == array) {
                    fieldValues2.add(objArr);
                } else {
                    Object itemValue2 = getPropertyValue(objArr, propertyName, propertyNameHash);
                    if (!(itemValue2 instanceof Collection)) {
                        if (itemValue2 != null || !this.ignoreNullValue) {
                            fieldValues2.add(itemValue2);
                        }
                    } else {
                        Collection collection2 = (Collection) itemValue2;
                        fieldValues2.addAll(collection2);
                    }
                }
            }
            return fieldValues2;
        }
        if (currentObject2 instanceof Enum) {
            Enum e3 = (Enum) currentObject2;
            if (-4270347329889690746L == propertyNameHash) {
                return e3.name();
            }
            if (-1014497654951707614L == propertyNameHash) {
                return Integer.valueOf(e3.ordinal());
            }
        }
        if (currentObject2 instanceof Calendar) {
            Calendar e4 = (Calendar) currentObject2;
            if (8963398325558730460L == propertyNameHash) {
                return Integer.valueOf(e4.get(1));
            }
            if (-811277319855450459L == propertyNameHash) {
                return Integer.valueOf(e4.get(2));
            }
            if (-3851359326990528739L == propertyNameHash) {
                return Integer.valueOf(e4.get(5));
            }
            if (4647432019745535567L == propertyNameHash) {
                return Integer.valueOf(e4.get(11));
            }
            if (6607618197526598121L == propertyNameHash) {
                return Integer.valueOf(e4.get(12));
            }
            if (-6586085717218287427L == propertyNameHash) {
                return Integer.valueOf(e4.get(13));
            }
        }
        return null;
    }

    protected void deepScan(Object currentObject, String propertyName, List<Object> results) {
        if (currentObject == null) {
            return;
        }
        if (currentObject instanceof Map) {
            Map<?, ?> map = (Map) currentObject;
            for (Map.Entry entry : map.entrySet()) {
                Object val = entry.getValue();
                if (propertyName.equals(entry.getKey())) {
                    if (val instanceof Collection) {
                        results.addAll((Collection) val);
                    } else {
                        results.add(val);
                    }
                } else if (val != null && !ParserConfig.isPrimitive2(val.getClass())) {
                    deepScan(val, propertyName, results);
                }
            }
            return;
        }
        if (currentObject instanceof Collection) {
            for (Object next : (Collection) currentObject) {
                if (!ParserConfig.isPrimitive2(next.getClass())) {
                    deepScan(next, propertyName, results);
                }
            }
            return;
        }
        Class<?> currentClass = currentObject.getClass();
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentClass);
        if (beanSerializer != null) {
            try {
                FieldSerializer fieldDeser = beanSerializer.getFieldSerializer(propertyName);
                if (fieldDeser != null) {
                    try {
                        Object val2 = fieldDeser.getPropertyValueDirect(currentObject);
                        results.add(val2);
                        return;
                    } catch (IllegalAccessException ex) {
                        throw new JSONException("getFieldValue error." + propertyName, ex);
                    } catch (InvocationTargetException ex2) {
                        throw new JSONException("getFieldValue error." + propertyName, ex2);
                    }
                }
                List<Object> fieldValues = beanSerializer.getFieldValues(currentObject);
                for (Object val3 : fieldValues) {
                    deepScan(val3, propertyName, results);
                }
                return;
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + propertyName, e);
            }
        }
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            for (int i = 0; i < list.size(); i++) {
                Object val4 = list.get(i);
                deepScan(val4, propertyName, results);
            }
        }
    }

    protected void deepSet(Object currentObject, String propertyName, long propertyNameHash, Object value) {
        if (currentObject == null) {
            return;
        }
        if (currentObject instanceof Map) {
            Map map = (Map) currentObject;
            if (map.containsKey(propertyName)) {
                map.get(propertyName);
                map.put(propertyName, value);
                return;
            } else {
                for (Object val : map.values()) {
                    deepSet(val, propertyName, propertyNameHash, value);
                }
                return;
            }
        }
        Class<?> currentClass = currentObject.getClass();
        JavaBeanDeserializer beanDeserializer = getJavaBeanDeserializer(currentClass);
        if (beanDeserializer != null) {
            try {
                FieldDeserializer fieldDeser = beanDeserializer.getFieldDeserializer(propertyName);
                if (fieldDeser != null) {
                    fieldDeser.setValue(currentObject, value);
                    return;
                }
                JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentClass);
                List<Object> fieldValues = beanSerializer.getObjectFieldValues(currentObject);
                for (Object val2 : fieldValues) {
                    deepSet(val2, propertyName, propertyNameHash, value);
                }
                return;
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + propertyName, e);
            }
        }
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            for (int i = 0; i < list.size(); i++) {
                Object val3 = list.get(i);
                deepSet(val3, propertyName, propertyNameHash, value);
            }
        }
    }

    protected boolean setPropertyValue(Object parent, String name, long propertyNameHash, Object value) {
        if (parent instanceof Map) {
            ((Map) parent).put(name, value);
            return true;
        }
        if (parent instanceof List) {
            for (Object element : (List) parent) {
                if (element != null) {
                    setPropertyValue(element, name, propertyNameHash, value);
                }
            }
            return true;
        }
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer(parent.getClass());
        JavaBeanDeserializer beanDeserializer = null;
        if (deserializer instanceof JavaBeanDeserializer) {
            beanDeserializer = (JavaBeanDeserializer) deserializer;
        }
        if (beanDeserializer != null) {
            FieldDeserializer fieldDeserializer = beanDeserializer.getFieldDeserializer(propertyNameHash);
            if (fieldDeserializer == null) {
                return false;
            }
            if (value != null && value.getClass() != fieldDeserializer.fieldInfo.fieldClass) {
                value = TypeUtils.cast(value, fieldDeserializer.fieldInfo.fieldType, this.parserConfig);
            }
            fieldDeserializer.setValue(parent, value);
            return true;
        }
        throw new UnsupportedOperationException();
    }

    protected boolean removePropertyValue(Object parent, String name, boolean deep) {
        if (parent instanceof Map) {
            Object origin = ((Map) parent).remove(name);
            boolean found = origin != null;
            if (deep) {
                Iterator it = ((Map) parent).values().iterator();
                while (it.hasNext()) {
                    removePropertyValue(it.next(), name, deep);
                }
            }
            return found;
        }
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer(parent.getClass());
        JavaBeanDeserializer beanDeserializer = null;
        if (deserializer instanceof JavaBeanDeserializer) {
            beanDeserializer = (JavaBeanDeserializer) deserializer;
        }
        if (beanDeserializer != null) {
            FieldDeserializer fieldDeserializer = beanDeserializer.getFieldDeserializer(name);
            boolean found2 = false;
            if (fieldDeserializer != null) {
                fieldDeserializer.setValue(parent, (String) null);
                found2 = true;
            }
            if (deep) {
                Collection<Object> propertyValues = getPropertyValues(parent);
                for (Object item : propertyValues) {
                    if (item != null) {
                        removePropertyValue(item, name, deep);
                    }
                }
            }
            return found2;
        }
        if (deep) {
            return false;
        }
        throw new UnsupportedOperationException();
    }

    protected JavaBeanSerializer getJavaBeanSerializer(Class<?> currentClass) {
        ObjectSerializer serializer = this.serializeConfig.getObjectWriter(currentClass);
        if (!(serializer instanceof JavaBeanSerializer)) {
            return null;
        }
        JavaBeanSerializer beanSerializer = (JavaBeanSerializer) serializer;
        return beanSerializer;
    }

    protected JavaBeanDeserializer getJavaBeanDeserializer(Class<?> currentClass) {
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer(currentClass);
        if (!(deserializer instanceof JavaBeanDeserializer)) {
            return null;
        }
        JavaBeanDeserializer beanDeserializer = (JavaBeanDeserializer) deserializer;
        return beanDeserializer;
    }

    int evalSize(Object currentObject) {
        if (currentObject == null) {
            return -1;
        }
        if (currentObject instanceof Collection) {
            return ((Collection) currentObject).size();
        }
        if (currentObject instanceof Object[]) {
            return ((Object[]) currentObject).length;
        }
        if (currentObject.getClass().isArray()) {
            return Array.getLength(currentObject);
        }
        if (currentObject instanceof Map) {
            int count = 0;
            for (Object value : ((Map) currentObject).values()) {
                if (value != null) {
                    count++;
                }
            }
            return count;
        }
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentObject.getClass());
        if (beanSerializer == null) {
            return -1;
        }
        try {
            return beanSerializer.getSize(currentObject);
        } catch (Exception e) {
            throw new JSONPathException("evalSize error : " + this.path, e);
        }
    }

    Set<?> evalKeySet(Object currentObject) {
        JavaBeanSerializer beanSerializer;
        if (currentObject == null) {
            return null;
        }
        if (currentObject instanceof Map) {
            return ((Map) currentObject).keySet();
        }
        if ((currentObject instanceof Collection) || (currentObject instanceof Object[]) || currentObject.getClass().isArray() || (beanSerializer = getJavaBeanSerializer(currentObject.getClass())) == null) {
            return null;
        }
        try {
            return beanSerializer.getFieldNames(currentObject);
        } catch (Exception e) {
            throw new JSONPathException("evalKeySet error : " + this.path, e);
        }
    }

    @Override // com.alibaba.fastjson.JSONAware
    public String toJSONString() {
        return JSON.toJSONString(this.path);
    }

    public static Object reserveToArray(Object object, String... paths) {
        JSONArray reserved = new JSONArray();
        if (paths == null || paths.length == 0) {
            return reserved;
        }
        for (String item : paths) {
            JSONPath path = compile(item);
            path.init();
            Object value = path.eval(object);
            reserved.add(value);
        }
        return reserved;
    }

    public static Object reserveToObject(Object object, String... paths) {
        Object value;
        if (paths == null || paths.length == 0) {
            return object;
        }
        JSONObject reserved = new JSONObject(true);
        for (String item : paths) {
            JSONPath path = compile(item);
            path.init();
            Segment lastSegement = path.segments[path.segments.length - 1];
            if ((lastSegement instanceof PropertySegment) && (value = path.eval(object)) != null) {
                path.set(reserved, value);
            }
        }
        return reserved;
    }
}