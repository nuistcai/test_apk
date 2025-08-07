package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.SerializeBeanInfo;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tencent.bugly.Bugly;
import java.io.InputStream;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class TypeUtils {
    private static Object OPTIONAL_EMPTY = null;
    private static boolean OPTIONAL_ERROR = false;
    private static Function<Map<String, Class<?>>, Void> addBaseClassMappingsFunction = null;
    private static BiFunction<Object, Class, Object> castFunction = null;
    private static Function<Object, Object> castToSqlDateFunction = null;
    private static Function<Object, Object> castToSqlTimeFunction = null;
    public static Function<Object, Object> castToTimestampFunction = null;
    private static Class class_deque = null;
    public static boolean compatibleWithFieldName = false;
    public static boolean compatibleWithJavaBean = false;
    public static final long fnv1a_64_magic_hashcode = -3750763034362895579L;
    public static final long fnv1a_64_magic_prime = 1099511628211L;
    private static Function<Class, Boolean> isClobFunction;
    private static final Set<String> isProxyClassNames;
    private static volatile Map<Class, String[]> kotlinIgnores;
    private static volatile boolean kotlinIgnores_error;
    private static volatile boolean kotlin_class_klass_error;
    private static volatile boolean kotlin_error;
    private static volatile Constructor kotlin_kclass_constructor;
    private static volatile Method kotlin_kclass_getConstructors;
    private static volatile Method kotlin_kfunction_getParameters;
    private static volatile Method kotlin_kparameter_getName;
    private static volatile Class kotlin_metadata;
    private static volatile boolean kotlin_metadata_error;
    private static Class<?> optionalClass;
    private static Method oracleDateMethod;
    private static Method oracleTimestampMethod;
    private static Class<?> pathClass;
    private static final Map primitiveTypeMap;
    private static Class<? extends Annotation> transientClass;
    private static final Pattern NUMBER_WITH_TRAILING_ZEROS_PATTERN = Pattern.compile("\\.0*$");
    private static boolean setAccessibleEnable = true;
    private static boolean oracleTimestampMethodInited = false;
    private static boolean oracleDateMethodInited = false;
    private static boolean optionalClassInited = false;
    private static boolean transientClassInited = false;
    private static Class<? extends Annotation> class_OneToMany = null;
    private static boolean class_OneToMany_error = false;
    private static Class<? extends Annotation> class_ManyToMany = null;
    private static boolean class_ManyToMany_error = false;
    private static Method method_HibernateIsInitialized = null;
    private static boolean method_HibernateIsInitialized_error = false;
    private static ConcurrentMap<String, Class<?>> mappings = new ConcurrentHashMap(256, 0.75f, 1);
    private static boolean pathClass_error = false;
    private static Class<? extends Annotation> class_JacksonCreator = null;
    private static boolean class_JacksonCreator_error = false;
    private static volatile Class class_XmlAccessType = null;
    private static volatile Class class_XmlAccessorType = null;
    private static volatile boolean classXmlAccessorType_error = false;
    private static volatile Method method_XmlAccessorType_value = null;
    private static volatile Field field_XmlAccessType_FIELD = null;
    private static volatile Object field_XmlAccessType_FIELD_VALUE = null;

    static {
        compatibleWithJavaBean = false;
        compatibleWithFieldName = false;
        class_deque = null;
        try {
            compatibleWithJavaBean = "true".equals(IOUtils.getStringProperty(IOUtils.FASTJSON_COMPATIBLEWITHJAVABEAN));
            compatibleWithFieldName = "true".equals(IOUtils.getStringProperty(IOUtils.FASTJSON_COMPATIBLEWITHFIELDNAME));
        } catch (Throwable th) {
        }
        try {
            class_deque = Class.forName("java.util.Deque");
        } catch (Throwable th2) {
        }
        isClobFunction = new Function<Class, Boolean>() { // from class: com.alibaba.fastjson.util.TypeUtils.1
            @Override // com.alibaba.fastjson.util.Function
            public Boolean apply(Class clazz) {
                return Boolean.valueOf(Clob.class.isAssignableFrom(clazz));
            }
        };
        castToSqlDateFunction = new Function<Object, Object>() { // from class: com.alibaba.fastjson.util.TypeUtils.2
            @Override // com.alibaba.fastjson.util.Function
            public Object apply(Object value) throws NumberFormatException {
                if (value == null) {
                    return null;
                }
                if (value instanceof Date) {
                    return (Date) value;
                }
                if (value instanceof java.util.Date) {
                    return new Date(((java.util.Date) value).getTime());
                }
                if (value instanceof Calendar) {
                    return new Date(((Calendar) value).getTimeInMillis());
                }
                long longValue = 0;
                if (value instanceof BigDecimal) {
                    longValue = TypeUtils.longValue((BigDecimal) value);
                } else if (value instanceof Number) {
                    longValue = ((Number) value).longValue();
                }
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                        return null;
                    }
                    if (TypeUtils.isNumber(strVal)) {
                        longValue = Long.parseLong(strVal);
                    } else {
                        JSONScanner scanner = new JSONScanner(strVal);
                        if (scanner.scanISO8601DateIfMatch(false)) {
                            longValue = scanner.getCalendar().getTime().getTime();
                        } else {
                            throw new JSONException("can not cast to Timestamp, value : " + strVal);
                        }
                    }
                }
                if (longValue <= 0) {
                    throw new JSONException("can not cast to Date, value : " + value);
                }
                return new Date(longValue);
            }
        };
        castToSqlTimeFunction = new Function<Object, Object>() { // from class: com.alibaba.fastjson.util.TypeUtils.3
            @Override // com.alibaba.fastjson.util.Function
            public Object apply(Object value) throws NumberFormatException {
                if (value == null) {
                    return null;
                }
                if (value instanceof Time) {
                    return (Time) value;
                }
                if (value instanceof java.util.Date) {
                    return new Time(((java.util.Date) value).getTime());
                }
                if (value instanceof Calendar) {
                    return new Time(((Calendar) value).getTimeInMillis());
                }
                long longValue = 0;
                if (value instanceof BigDecimal) {
                    longValue = TypeUtils.longValue((BigDecimal) value);
                } else if (value instanceof Number) {
                    longValue = ((Number) value).longValue();
                }
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal)) {
                        return null;
                    }
                    if (TypeUtils.isNumber(strVal)) {
                        longValue = Long.parseLong(strVal);
                    } else {
                        if (strVal.length() == 8 && strVal.charAt(2) == ':' && strVal.charAt(5) == ':') {
                            return Time.valueOf(strVal);
                        }
                        JSONScanner scanner = new JSONScanner(strVal);
                        if (scanner.scanISO8601DateIfMatch(false)) {
                            longValue = scanner.getCalendar().getTime().getTime();
                        } else {
                            throw new JSONException("can not cast to Timestamp, value : " + strVal);
                        }
                    }
                }
                if (longValue <= 0) {
                    throw new JSONException("can not cast to Date, value : " + value);
                }
                return new Time(longValue);
            }
        };
        castToTimestampFunction = new Function<Object, Object>() { // from class: com.alibaba.fastjson.util.TypeUtils.4
            @Override // com.alibaba.fastjson.util.Function
            public Object apply(Object value) throws NumberFormatException {
                if (value == null) {
                    return null;
                }
                if (value instanceof Calendar) {
                    return new Timestamp(((Calendar) value).getTimeInMillis());
                }
                if (value instanceof Timestamp) {
                    return (Timestamp) value;
                }
                if (value instanceof java.util.Date) {
                    return new Timestamp(((java.util.Date) value).getTime());
                }
                long longValue = 0;
                if (value instanceof BigDecimal) {
                    longValue = TypeUtils.longValue((BigDecimal) value);
                } else if (value instanceof Number) {
                    longValue = ((Number) value).longValue();
                }
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                        return null;
                    }
                    if (strVal.endsWith(".000000000")) {
                        strVal = strVal.substring(0, strVal.length() - 10);
                    } else if (strVal.endsWith(".000000")) {
                        strVal = strVal.substring(0, strVal.length() - 7);
                    }
                    if (strVal.length() == 29 && strVal.charAt(4) == '-' && strVal.charAt(7) == '-' && strVal.charAt(10) == ' ' && strVal.charAt(13) == ':' && strVal.charAt(16) == ':' && strVal.charAt(19) == '.') {
                        int year = TypeUtils.num(strVal.charAt(0), strVal.charAt(1), strVal.charAt(2), strVal.charAt(3));
                        int month = TypeUtils.num(strVal.charAt(5), strVal.charAt(6));
                        int day = TypeUtils.num(strVal.charAt(8), strVal.charAt(9));
                        int hour = TypeUtils.num(strVal.charAt(11), strVal.charAt(12));
                        int minute = TypeUtils.num(strVal.charAt(14), strVal.charAt(15));
                        int second = TypeUtils.num(strVal.charAt(17), strVal.charAt(18));
                        int nanos = TypeUtils.num(strVal.charAt(20), strVal.charAt(21), strVal.charAt(22), strVal.charAt(23), strVal.charAt(24), strVal.charAt(25), strVal.charAt(26), strVal.charAt(27), strVal.charAt(28));
                        return new Timestamp(year - 1900, month - 1, day, hour, minute, second, nanos);
                    }
                    if (TypeUtils.isNumber(strVal)) {
                        longValue = Long.parseLong(strVal);
                    } else {
                        JSONScanner scanner = new JSONScanner(strVal);
                        if (scanner.scanISO8601DateIfMatch(false)) {
                            longValue = scanner.getCalendar().getTime().getTime();
                        } else {
                            throw new JSONException("can not cast to Timestamp, value : " + strVal);
                        }
                    }
                }
                return new Timestamp(longValue);
            }
        };
        castFunction = new BiFunction<Object, Class, Object>() { // from class: com.alibaba.fastjson.util.TypeUtils.5
            @Override // com.alibaba.fastjson.util.BiFunction
            public Object apply(Object obj, Class clazz) {
                if (clazz == Date.class) {
                    return TypeUtils.castToSqlDate(obj);
                }
                if (clazz == Time.class) {
                    return TypeUtils.castToSqlTime(obj);
                }
                if (clazz == Timestamp.class) {
                    return TypeUtils.castToTimestamp(obj);
                }
                return null;
            }
        };
        addBaseClassMappingsFunction = new Function<Map<String, Class<?>>, Void>() { // from class: com.alibaba.fastjson.util.TypeUtils.6
            @Override // com.alibaba.fastjson.util.Function
            public Void apply(Map<String, Class<?>> mappings2) {
                Class[] classes = {Time.class, Date.class, Timestamp.class};
                for (Class clazz : classes) {
                    if (clazz != null) {
                        mappings2.put(clazz.getName(), clazz);
                    }
                }
                return null;
            }
        };
        addBaseClassMappings();
        primitiveTypeMap = new HashMap<Class, String>(8) { // from class: com.alibaba.fastjson.util.TypeUtils.7
            {
                put(Boolean.TYPE, "Z");
                put(Character.TYPE, "C");
                put(Byte.TYPE, "B");
                put(Short.TYPE, "S");
                put(Integer.TYPE, "I");
                put(Long.TYPE, "J");
                put(Float.TYPE, "F");
                put(Double.TYPE, "D");
            }
        };
        isProxyClassNames = new HashSet<String>(6) { // from class: com.alibaba.fastjson.util.TypeUtils.8
            {
                add("net.sf.cglib.proxy.Factory");
                add("org.springframework.cglib.proxy.Factory");
                add("javassist.util.proxy.ProxyObject");
                add("org.apache.ibatis.javassist.util.proxy.ProxyObject");
                add("org.hibernate.proxy.HibernateProxy");
                add("org.springframework.context.annotation.ConfigurationClassEnhancer$EnhancedConfiguration");
            }
        };
        OPTIONAL_ERROR = false;
    }

    public static boolean isXmlField(Class clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Annotation annotation;
        if (class_XmlAccessorType == null && !classXmlAccessorType_error) {
            try {
                class_XmlAccessorType = Class.forName("javax.xml.bind.annotation.XmlAccessorType");
            } catch (Throwable th) {
                classXmlAccessorType_error = true;
            }
        }
        if (class_XmlAccessorType == null || (annotation = getAnnotation((Class<?>) clazz, (Class<Annotation>) class_XmlAccessorType)) == null) {
            return false;
        }
        if (method_XmlAccessorType_value == null && !classXmlAccessorType_error) {
            try {
                method_XmlAccessorType_value = class_XmlAccessorType.getMethod("value", new Class[0]);
            } catch (Throwable th2) {
                classXmlAccessorType_error = true;
            }
        }
        if (method_XmlAccessorType_value == null) {
            return false;
        }
        Object value = null;
        if (!classXmlAccessorType_error) {
            try {
                value = method_XmlAccessorType_value.invoke(annotation, new Object[0]);
            } catch (Throwable th3) {
                classXmlAccessorType_error = true;
            }
        }
        if (value == null) {
            return false;
        }
        if (class_XmlAccessType == null && !classXmlAccessorType_error) {
            try {
                class_XmlAccessType = Class.forName("javax.xml.bind.annotation.XmlAccessType");
                field_XmlAccessType_FIELD = class_XmlAccessType.getField("FIELD");
                field_XmlAccessType_FIELD_VALUE = field_XmlAccessType_FIELD.get(null);
            } catch (Throwable th4) {
                classXmlAccessorType_error = true;
            }
        }
        return value == field_XmlAccessType_FIELD_VALUE;
    }

    public static Annotation getXmlAccessorType(Class clazz) {
        if (class_XmlAccessorType == null && !classXmlAccessorType_error) {
            try {
                class_XmlAccessorType = Class.forName("javax.xml.bind.annotation.XmlAccessorType");
            } catch (Throwable th) {
                classXmlAccessorType_error = true;
            }
        }
        if (class_XmlAccessorType == null) {
            return null;
        }
        return getAnnotation((Class<?>) clazz, class_XmlAccessorType);
    }

    public static boolean isClob(Class clazz) {
        Boolean isClob = (Boolean) ModuleUtil.callWhenHasJavaSql(isClobFunction, clazz);
        if (isClob != null) {
            return isClob.booleanValue();
        }
        return false;
    }

    public static String castToString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static Byte castToByte(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return Byte.valueOf(byteValue((BigDecimal) obj));
        }
        if (obj instanceof Number) {
            return Byte.valueOf(((Number) obj).byteValue());
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
            return Byte.valueOf(Byte.parseByte(str));
        }
        if (obj instanceof Boolean) {
            return Byte.valueOf(((Boolean) obj).booleanValue() ? (byte) 1 : (byte) 0);
        }
        throw new JSONException("can not cast to byte, value : " + obj);
    }

    public static Character castToChar(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Character) {
            return (Character) value;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.length() != 1) {
                throw new JSONException("can not cast to char, value : " + value);
            }
            return Character.valueOf(strVal.charAt(0));
        }
        throw new JSONException("can not cast to char, value : " + value);
    }

    public static Short castToShort(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return Short.valueOf(shortValue((BigDecimal) obj));
        }
        if (obj instanceof Number) {
            return Short.valueOf(((Number) obj).shortValue());
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
            return Short.valueOf(Short.parseShort(str));
        }
        if (obj instanceof Boolean) {
            return Short.valueOf(((Boolean) obj).booleanValue() ? (short) 1 : (short) 0);
        }
        throw new JSONException("can not cast to short, value : " + obj);
    }

    public static BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Float) {
            if (Float.isNaN(((Float) value).floatValue()) || Float.isInfinite(((Float) value).floatValue())) {
                return null;
            }
        } else if (value instanceof Double) {
            if (Double.isNaN(((Double) value).doubleValue()) || Double.isInfinite(((Double) value).doubleValue())) {
                return null;
            }
        } else {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            }
            if (value instanceof BigInteger) {
                return new BigDecimal((BigInteger) value);
            }
            if ((value instanceof Map) && ((Map) value).size() == 0) {
                return null;
            }
        }
        String strVal = value.toString();
        if (strVal.length() == 0 || strVal.equalsIgnoreCase("null")) {
            return null;
        }
        if (strVal.length() > 65535) {
            throw new JSONException("decimal overflow");
        }
        return new BigDecimal(strVal);
    }

    public static BigInteger castToBigInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Float) {
            Float floatValue = (Float) value;
            if (Float.isNaN(floatValue.floatValue()) || Float.isInfinite(floatValue.floatValue())) {
                return null;
            }
            return BigInteger.valueOf(floatValue.longValue());
        }
        if (value instanceof Double) {
            Double doubleValue = (Double) value;
            if (Double.isNaN(doubleValue.doubleValue()) || Double.isInfinite(doubleValue.doubleValue())) {
                return null;
            }
            return BigInteger.valueOf(doubleValue.longValue());
        }
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            if (scale > -1000 && scale < 1000) {
                return ((BigDecimal) value).toBigInteger();
            }
        }
        String strVal = value.toString();
        if (strVal.length() == 0 || strVal.equalsIgnoreCase("null")) {
            return null;
        }
        if (strVal.length() > 65535) {
            throw new JSONException("decimal overflow");
        }
        return new BigInteger(strVal);
    }

    public static Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Float.valueOf(((Number) value).floatValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != -1) {
                strVal = strVal.replaceAll(",", "");
            }
            return Float.valueOf(Float.parseFloat(strVal));
        }
        if (value instanceof Boolean) {
            return Float.valueOf(((Boolean) value).booleanValue() ? 1.0f : 0.0f);
        }
        throw new JSONException("can not cast to float, value : " + value);
    }

    public static Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Double.valueOf(((Number) value).doubleValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != -1) {
                strVal = strVal.replaceAll(",", "");
            }
            return Double.valueOf(Double.parseDouble(strVal));
        }
        if (value instanceof Boolean) {
            return Double.valueOf(((Boolean) value).booleanValue() ? 1.0d : 0.0d);
        }
        throw new JSONException("can not cast to double, value : " + value);
    }

    public static java.util.Date castToDate(Object value) {
        return castToDate(value, null);
    }

    public static java.util.Date castToDate(Object value, String format) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (value == null) {
            return null;
        }
        if (value instanceof java.util.Date) {
            return (java.util.Date) value;
        }
        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }
        long longValue = -1;
        if (value instanceof BigDecimal) {
            long longValue2 = longValue((BigDecimal) value);
            return new java.util.Date(longValue2);
        }
        if (value instanceof Number) {
            long longValue3 = ((Number) value).longValue();
            if ("unixtime".equals(format)) {
                longValue3 *= 1000;
            }
            return new java.util.Date(longValue3);
        }
        if (value instanceof String) {
            String strVal = (String) value;
            JSONScanner dateLexer = new JSONScanner(strVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.getCalendar();
                    return calendar.getTime();
                }
                dateLexer.close();
                if (strVal.startsWith("/Date(") && strVal.endsWith(")/")) {
                    strVal = strVal.substring(6, strVal.length() - 2);
                }
                if (strVal.indexOf(45) > 0 || strVal.indexOf(43) > 0 || format != null) {
                    if (format == null) {
                        int len = strVal.length();
                        if (len == JSON.DEFFAULT_DATE_FORMAT.length() || (len == 22 && JSON.DEFFAULT_DATE_FORMAT.equals("yyyyMMddHHmmssSSSZ"))) {
                            format = JSON.DEFFAULT_DATE_FORMAT;
                        } else if (len == 10) {
                            format = "yyyy-MM-dd";
                        } else if (len == "yyyy-MM-dd HH:mm:ss".length()) {
                            format = "yyyy-MM-dd HH:mm:ss";
                        } else if (len == 29 && strVal.charAt(26) == ':' && strVal.charAt(28) == '0') {
                            format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
                        } else if (len == 23 && strVal.charAt(19) == ',') {
                            format = "yyyy-MM-dd HH:mm:ss,SSS";
                        } else {
                            format = "yyyy-MM-dd HH:mm:ss.SSS";
                        }
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format, JSON.defaultLocale);
                    dateFormat.setTimeZone(JSON.defaultTimeZone);
                    try {
                        return dateFormat.parse(strVal);
                    } catch (ParseException e) {
                        throw new JSONException("can not cast to Date, value : " + strVal);
                    }
                }
                if (strVal.length() == 0) {
                    return null;
                }
                longValue = Long.parseLong(strVal);
            } finally {
                dateLexer.close();
            }
        }
        if (longValue == -1) {
            Class<?> clazz = value.getClass();
            if ("oracle.sql.TIMESTAMP".equals(clazz.getName())) {
                if (oracleTimestampMethod == null && !oracleTimestampMethodInited) {
                    try {
                        oracleTimestampMethod = clazz.getMethod("toJdbc", new Class[0]);
                    } catch (NoSuchMethodException e2) {
                    } catch (Throwable th) {
                        oracleTimestampMethodInited = true;
                        throw th;
                    }
                    oracleTimestampMethodInited = true;
                }
                try {
                    Object result = oracleTimestampMethod.invoke(value, new Object[0]);
                    return (java.util.Date) result;
                } catch (Exception e3) {
                    throw new JSONException("can not cast oracle.sql.TIMESTAMP to Date", e3);
                }
            }
            if ("oracle.sql.DATE".equals(clazz.getName())) {
                if (oracleDateMethod == null && !oracleDateMethodInited) {
                    try {
                        oracleDateMethod = clazz.getMethod("toJdbc", new Class[0]);
                    } catch (NoSuchMethodException e4) {
                    } catch (Throwable th2) {
                        oracleDateMethodInited = true;
                        throw th2;
                    }
                    oracleDateMethodInited = true;
                }
                try {
                    Object result2 = oracleDateMethod.invoke(value, new Object[0]);
                    return (java.util.Date) result2;
                } catch (Exception e5) {
                    throw new JSONException("can not cast oracle.sql.DATE to Date", e5);
                }
            }
            throw new JSONException("can not cast to Date, value : " + value);
        }
        return new java.util.Date(longValue);
    }

    public static Object castToSqlDate(Object value) {
        return ModuleUtil.callWhenHasJavaSql(castToSqlDateFunction, value);
    }

    public static long longExtractValue(Number number) {
        if (number instanceof BigDecimal) {
            return ((BigDecimal) number).longValueExact();
        }
        return number.longValue();
    }

    public static Object castToSqlTime(Object value) {
        return ModuleUtil.callWhenHasJavaSql(castToSqlTimeFunction, value);
    }

    public static Object castToTimestamp(Object value) {
        return ModuleUtil.callWhenHasJavaSql(castToTimestampFunction, value);
    }

    static int num(char c0, char c1) {
        if (c0 >= '0' && c0 <= '9' && c1 >= '0' && c1 <= '9') {
            return ((c0 - '0') * 10) + (c1 - '0');
        }
        return -1;
    }

    static int num(char c0, char c1, char c2, char c3) {
        if (c0 >= '0' && c0 <= '9' && c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9' && c3 >= '0' && c3 <= '9') {
            return ((c0 - '0') * 1000) + ((c1 - '0') * 100) + ((c2 - '0') * 10) + (c3 - '0');
        }
        return -1;
    }

    static int num(char c0, char c1, char c2, char c3, char c4, char c5, char c6, char c7, char c8) {
        if (c0 >= '0' && c0 <= '9' && c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9' && c3 >= '0' && c3 <= '9' && c4 >= '0' && c4 <= '9' && c5 >= '0' && c5 <= '9' && c6 >= '0' && c6 <= '9' && c7 >= '0' && c7 <= '9' && c8 >= '0' && c8 <= '9') {
            return ((c0 - '0') * 100000000) + ((c1 - '0') * 10000000) + ((c2 - '0') * 1000000) + ((c3 - '0') * 100000) + ((c4 - '0') * 10000) + ((c5 - '0') * 1000) + ((c6 - '0') * 100) + ((c7 - '0') * 10) + (c8 - '0');
        }
        return -1;
    }

    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '+' || ch == '-') {
                if (i != 0) {
                    return false;
                }
            } else if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    public static Long castToLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return Long.valueOf(longValue((BigDecimal) value));
        }
        if (value instanceof Number) {
            return Long.valueOf(((Number) value).longValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != -1) {
                strVal = strVal.replaceAll(",", "");
            }
            try {
                return Long.valueOf(Long.parseLong(strVal));
            } catch (NumberFormatException e) {
                JSONScanner dateParser = new JSONScanner(strVal);
                Calendar calendar = null;
                if (dateParser.scanISO8601DateIfMatch(false)) {
                    calendar = dateParser.getCalendar();
                }
                dateParser.close();
                if (calendar != null) {
                    return Long.valueOf(calendar.getTimeInMillis());
                }
            }
        }
        if (value instanceof Map) {
            Map map = (Map) value;
            if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
                Iterator iter = map.values().iterator();
                iter.next();
                Object value2 = iter.next();
                return castToLong(value2);
            }
        }
        if (value instanceof Boolean) {
            return Long.valueOf(((Boolean) value).booleanValue() ? 1L : 0L);
        }
        throw new JSONException("can not cast to long, value : " + value);
    }

    public static byte byteValue(BigDecimal decimal) {
        if (decimal == null) {
            return (byte) 0;
        }
        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.byteValue();
        }
        return decimal.byteValueExact();
    }

    public static short shortValue(BigDecimal decimal) {
        if (decimal == null) {
            return (short) 0;
        }
        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.shortValue();
        }
        return decimal.shortValueExact();
    }

    public static int intValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }
        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.intValue();
        }
        return decimal.intValueExact();
    }

    public static long longValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0L;
        }
        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.longValue();
        }
        return decimal.longValueExact();
    }

    public static Integer castToInt(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof BigDecimal) {
            return Integer.valueOf(intValue((BigDecimal) obj));
        }
        if (obj instanceof Number) {
            return Integer.valueOf(((Number) obj).intValue());
        }
        if (obj instanceof String) {
            String strReplaceAll = (String) obj;
            if (strReplaceAll.length() == 0 || "null".equals(strReplaceAll) || "NULL".equals(strReplaceAll)) {
                return null;
            }
            if (strReplaceAll.indexOf(44) != -1) {
                strReplaceAll = strReplaceAll.replaceAll(",", "");
            }
            Matcher matcher = NUMBER_WITH_TRAILING_ZEROS_PATTERN.matcher(strReplaceAll);
            if (matcher.find()) {
                strReplaceAll = matcher.replaceAll("");
            }
            return Integer.valueOf(Integer.parseInt(strReplaceAll));
        }
        if (obj instanceof Boolean) {
            return Integer.valueOf(((Boolean) obj).booleanValue() ? 1 : 0);
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
                Iterator it = map.values().iterator();
                it.next();
                return castToInt(it.next());
            }
        }
        throw new JSONException("can not cast to int, value : " + obj);
    }

    public static byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        if (value instanceof String) {
            return IOUtils.decodeBase64((String) value);
        }
        throw new JSONException("can not cast to byte[], value : " + value);
    }

    public static Boolean castToBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof BigDecimal) {
            return Boolean.valueOf(intValue((BigDecimal) value) == 1);
        }
        if (value instanceof Number) {
            return Boolean.valueOf(((Number) value).intValue() == 1);
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if ("true".equalsIgnoreCase(strVal) || "1".equals(strVal)) {
                return Boolean.TRUE;
            }
            if (Bugly.SDK_IS_DEV.equalsIgnoreCase(strVal) || "0".equals(strVal)) {
                return Boolean.FALSE;
            }
            if ("Y".equalsIgnoreCase(strVal) || "T".equals(strVal)) {
                return Boolean.TRUE;
            }
            if ("F".equalsIgnoreCase(strVal) || "N".equals(strVal)) {
                return Boolean.FALSE;
            }
        }
        throw new JSONException("can not cast to boolean, value : " + value);
    }

    public static <T> T castToJavaBean(Object obj, Class<T> cls) {
        return (T) cast(obj, (Class) cls, ParserConfig.getGlobalInstance());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T cast(Object obj, Class<T> cls, ParserConfig parserConfig) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Object obj2;
        if (obj == 0) {
            if (cls == Integer.TYPE) {
                return (T) 0;
            }
            if (cls == Long.TYPE) {
                return (T) 0L;
            }
            if (cls == Short.TYPE) {
                return (T) (short) 0;
            }
            if (cls == Byte.TYPE) {
                return (T) (byte) 0;
            }
            if (cls == Float.TYPE) {
                return (T) Float.valueOf(0.0f);
            }
            if (cls == Double.TYPE) {
                return (T) Double.valueOf(0.0d);
            }
            if (cls != Boolean.TYPE) {
                return null;
            }
            return (T) Boolean.FALSE;
        }
        if (cls == null) {
            throw new IllegalArgumentException("clazz is null");
        }
        if (cls == obj.getClass()) {
            return obj;
        }
        if (obj instanceof Map) {
            if (cls == Map.class) {
                return obj;
            }
            Map map = (Map) obj;
            if (cls == Object.class && !map.containsKey(JSON.DEFAULT_TYPE_KEY)) {
                return obj;
            }
            return (T) castToJavaBean((Map) obj, cls, parserConfig);
        }
        if (cls.isArray()) {
            if (obj instanceof Collection) {
                Collection collection = (Collection) obj;
                int i = 0;
                T t = (T) Array.newInstance(cls.getComponentType(), collection.size());
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    Array.set(t, i, cast(it.next(), (Class) cls.getComponentType(), parserConfig));
                    i++;
                }
                return t;
            }
            if (cls == byte[].class) {
                return (T) castToBytes(obj);
            }
        }
        if (cls.isAssignableFrom(obj.getClass())) {
            return obj;
        }
        if (cls == Boolean.TYPE || cls == Boolean.class) {
            return (T) castToBoolean(obj);
        }
        if (cls == Byte.TYPE || cls == Byte.class) {
            return (T) castToByte(obj);
        }
        if (cls == Character.TYPE || cls == Character.class) {
            return (T) castToChar(obj);
        }
        if (cls == Short.TYPE || cls == Short.class) {
            return (T) castToShort(obj);
        }
        if (cls == Integer.TYPE || cls == Integer.class) {
            return (T) castToInt(obj);
        }
        if (cls == Long.TYPE || cls == Long.class) {
            return (T) castToLong(obj);
        }
        if (cls == Float.TYPE || cls == Float.class) {
            return (T) castToFloat(obj);
        }
        if (cls == Double.TYPE || cls == Double.class) {
            return (T) castToDouble(obj);
        }
        if (cls == String.class) {
            return (T) castToString(obj);
        }
        if (cls == BigDecimal.class) {
            return (T) castToBigDecimal(obj);
        }
        if (cls == BigInteger.class) {
            return (T) castToBigInteger(obj);
        }
        if (cls == java.util.Date.class) {
            return (T) castToDate(obj);
        }
        T t2 = (T) ModuleUtil.callWhenHasJavaSql(castFunction, obj, cls);
        if (t2 != null) {
            return t2;
        }
        if (cls.isEnum()) {
            return (T) castToEnum(obj, cls, parserConfig);
        }
        if (Calendar.class.isAssignableFrom(cls)) {
            java.util.Date dateCastToDate = castToDate(obj);
            if (cls == Calendar.class) {
                obj2 = (T) Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
            } else {
                try {
                    obj2 = (T) ((Calendar) cls.newInstance());
                } catch (Exception e) {
                    throw new JSONException("can not cast to : " + cls.getName(), e);
                }
            }
            ((Calendar) obj2).setTime(dateCastToDate);
            return (T) obj2;
        }
        String name = cls.getName();
        if (name.equals("javax.xml.datatype.XMLGregorianCalendar")) {
            java.util.Date dateCastToDate2 = castToDate(obj);
            Calendar calendar = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
            calendar.setTime(dateCastToDate2);
            return (T) CalendarCodec.instance.createXMLGregorianCalendar(calendar);
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
            if (cls == Currency.class) {
                return (T) Currency.getInstance(str);
            }
            if (cls == Locale.class) {
                return (T) toLocale(str);
            }
            if (name.startsWith("java.time.")) {
                return (T) JSON.parseObject(JSON.toJSONString(str), cls);
            }
        }
        if (parserConfig.get(cls) != null) {
            return (T) JSON.parseObject(JSON.toJSONString(obj), cls);
        }
        throw new JSONException("can not cast to : " + cls.getName());
    }

    public static Locale toLocale(String strVal) {
        String[] items = strVal.split("_");
        if (items.length == 1) {
            return new Locale(items[0]);
        }
        if (items.length == 2) {
            return new Locale(items[0], items[1]);
        }
        return new Locale(items[0], items[1], items[2]);
    }

    public static <T> T castToEnum(Object obj, Class<T> cls, ParserConfig parserConfig) {
        try {
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() == 0) {
                    return null;
                }
                if (parserConfig == null) {
                    parserConfig = ParserConfig.getGlobalInstance();
                }
                ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
                if (deserializer instanceof EnumDeserializer) {
                    return (T) ((EnumDeserializer) deserializer).getEnumByHashCode(fnv1a_64(str));
                }
                return (T) Enum.valueOf(cls, str);
            }
            if (obj instanceof BigDecimal) {
                int iIntValue = intValue((BigDecimal) obj);
                T[] enumConstants = cls.getEnumConstants();
                if (iIntValue < enumConstants.length) {
                    return enumConstants[iIntValue];
                }
            }
            if (obj instanceof Number) {
                int iIntValue2 = ((Number) obj).intValue();
                T[] enumConstants2 = cls.getEnumConstants();
                if (iIntValue2 < enumConstants2.length) {
                    return enumConstants2[iIntValue2];
                }
            }
            throw new JSONException("can not cast to : " + cls.getName());
        } catch (Exception e) {
            throw new JSONException("can not cast to : " + cls.getName(), e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T cast(Object obj, Type type, ParserConfig parserConfig) {
        if (obj == 0) {
            return null;
        }
        if (type instanceof Class) {
            return (T) cast(obj, (Class) type, parserConfig);
        }
        if (type instanceof ParameterizedType) {
            return (T) cast(obj, (ParameterizedType) type, parserConfig);
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
        }
        if (type instanceof TypeVariable) {
            return obj;
        }
        throw new JSONException("can not cast to : " + type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, java.util.HashMap, java.util.Map] */
    /* JADX WARN: Type inference failed for: r2v15, types: [T, java.util.Map$Entry] */
    /* JADX WARN: Type inference failed for: r4v0, types: [T, java.util.ArrayList, java.util.List] */
    public static <T> T cast(Object obj, ParameterizedType parameterizedType, ParserConfig parserConfig) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Object objCast;
        T t;
        Object objCast2;
        Type rawType = parameterizedType.getRawType();
        if (rawType == List.class || rawType == ArrayList.class) {
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (obj instanceof List) {
                List list = (List) obj;
                ?? r4 = (T) new ArrayList(list.size());
                for (Object obj2 : list) {
                    if (type instanceof Class) {
                        if (obj2 != null && obj2.getClass() == JSONObject.class) {
                            objCast = ((JSONObject) obj2).toJavaObject((Class) type, parserConfig, 0);
                        } else {
                            objCast = cast(obj2, (Class<Object>) type, parserConfig);
                        }
                    } else {
                        objCast = cast(obj2, type, parserConfig);
                    }
                    r4.add(objCast);
                }
                return r4;
            }
        }
        if (rawType == Set.class || rawType == HashSet.class || rawType == TreeSet.class || rawType == Collection.class || rawType == List.class || rawType == ArrayList.class) {
            Type type2 = parameterizedType.getActualTypeArguments()[0];
            if (obj instanceof Iterable) {
                if (rawType == Set.class || rawType == HashSet.class) {
                    t = (T) new HashSet();
                } else if (rawType == TreeSet.class) {
                    t = (T) new TreeSet();
                } else {
                    t = (T) new ArrayList();
                }
                for (T t2 : (Iterable) obj) {
                    if (type2 instanceof Class) {
                        if (t2 != null && t2.getClass() == JSONObject.class) {
                            objCast2 = ((JSONObject) t2).toJavaObject((Class) type2, parserConfig, 0);
                        } else {
                            objCast2 = cast((Object) t2, (Class<Object>) type2, parserConfig);
                        }
                    } else {
                        objCast2 = cast(t2, type2, parserConfig);
                    }
                    ((Collection) t).add(objCast2);
                }
                return t;
            }
        }
        if (rawType == Map.class || rawType == HashMap.class) {
            Type type3 = parameterizedType.getActualTypeArguments()[0];
            Type type4 = parameterizedType.getActualTypeArguments()[1];
            if (obj instanceof Map) {
                ?? r2 = (T) new HashMap();
                for (Map.Entry entry : ((Map) obj).entrySet()) {
                    r2.put(cast(entry.getKey(), type3, parserConfig), cast(entry.getValue(), type4, parserConfig));
                }
                return r2;
            }
        }
        if ((obj instanceof String) && ((String) obj).length() == 0) {
            return null;
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length == 1 && (parameterizedType.getActualTypeArguments()[0] instanceof WildcardType)) {
            return (T) cast(obj, rawType, parserConfig);
        }
        if (rawType == Map.Entry.class && (obj instanceof Map) && ((Map) obj).size() == 1) {
            ?? r22 = (T) ((Map.Entry) ((Map) obj).entrySet().iterator().next());
            Object value = r22.getValue();
            if (actualTypeArguments.length == 2 && (value instanceof Map)) {
                r22.setValue(cast(value, actualTypeArguments[1], parserConfig));
            }
            return r22;
        }
        if (rawType instanceof Class) {
            if (parserConfig == null) {
                parserConfig = ParserConfig.global;
            }
            ObjectDeserializer deserializer = parserConfig.getDeserializer(rawType);
            if (deserializer != null) {
                return (T) deserializer.deserialze(new DefaultJSONParser(JSON.toJSONString(obj), parserConfig), parameterizedType, null);
            }
        }
        throw new JSONException("can not cast to : " + parameterizedType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T castToJavaBean(Map<String, Object> map, Class<T> cls, ParserConfig parserConfig) throws ClassNotFoundException {
        JSONObject jSONObject;
        int iIntValue;
        try {
            if (cls == StackTraceElement.class) {
                String str = (String) map.get("className");
                String str2 = (String) map.get("methodName");
                String str3 = (String) map.get("fileName");
                Number number = (Number) map.get("lineNumber");
                if (number == null) {
                    iIntValue = 0;
                } else if (number instanceof BigDecimal) {
                    iIntValue = ((BigDecimal) number).intValueExact();
                } else {
                    iIntValue = number.intValue();
                }
                return (T) new StackTraceElement(str, str2, str3, iIntValue);
            }
            Object obj = map.get(JSON.DEFAULT_TYPE_KEY);
            if (obj instanceof String) {
                String str4 = (String) obj;
                if (parserConfig == null) {
                    parserConfig = ParserConfig.global;
                }
                Class<?> clsCheckAutoType = parserConfig.checkAutoType(str4, null);
                if (clsCheckAutoType == null) {
                    throw new ClassNotFoundException(str4 + " not found");
                }
                if (!clsCheckAutoType.equals(cls)) {
                    return (T) castToJavaBean(map, clsCheckAutoType, parserConfig);
                }
            }
            if (cls.isInterface()) {
                if (map instanceof JSONObject) {
                    jSONObject = (JSONObject) map;
                } else {
                    jSONObject = new JSONObject(map);
                }
                if (parserConfig == null) {
                    parserConfig = ParserConfig.getGlobalInstance();
                }
                return parserConfig.get(cls) != null ? (T) JSON.parseObject(JSON.toJSONString(jSONObject), cls) : (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cls}, jSONObject);
            }
            if (cls == Locale.class) {
                Object obj2 = map.get("language");
                Object obj3 = map.get("country");
                if (obj2 instanceof String) {
                    String str5 = (String) obj2;
                    if (obj3 instanceof String) {
                        return (T) new Locale(str5, (String) obj3);
                    }
                    if (obj3 == null) {
                        return (T) new Locale(str5);
                    }
                }
            }
            if (cls == String.class && (map instanceof JSONObject)) {
                return (T) map.toString();
            }
            if (cls == JSON.class && (map instanceof JSONObject)) {
                return map;
            }
            if (cls == LinkedHashMap.class && (map instanceof JSONObject)) {
                T t = (T) ((JSONObject) map).getInnerMap();
                if (t instanceof LinkedHashMap) {
                    return t;
                }
            }
            if (cls.isInstance(map)) {
                return map;
            }
            if (cls == JSONObject.class) {
                return (T) new JSONObject(map);
            }
            if (parserConfig == null) {
                parserConfig = ParserConfig.getGlobalInstance();
            }
            JavaBeanDeserializer javaBeanDeserializer = null;
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
            }
            if (javaBeanDeserializer == null) {
                throw new JSONException("can not get javaBeanDeserializer. " + cls.getName());
            }
            return (T) javaBeanDeserializer.createInstance(map, parserConfig);
        } catch (Exception e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    private static void addBaseClassMappings() {
        mappings.put("byte", Byte.TYPE);
        mappings.put("short", Short.TYPE);
        mappings.put("int", Integer.TYPE);
        mappings.put("long", Long.TYPE);
        mappings.put("float", Float.TYPE);
        mappings.put("double", Double.TYPE);
        mappings.put("boolean", Boolean.TYPE);
        mappings.put("char", Character.TYPE);
        mappings.put("[byte", byte[].class);
        mappings.put("[short", short[].class);
        mappings.put("[int", int[].class);
        mappings.put("[long", long[].class);
        mappings.put("[float", float[].class);
        mappings.put("[double", double[].class);
        mappings.put("[boolean", boolean[].class);
        mappings.put("[char", char[].class);
        mappings.put("[B", byte[].class);
        mappings.put("[S", short[].class);
        mappings.put("[I", int[].class);
        mappings.put("[J", long[].class);
        mappings.put("[F", float[].class);
        mappings.put("[D", double[].class);
        mappings.put("[C", char[].class);
        mappings.put("[Z", boolean[].class);
        Class[] classes = {Object.class, Cloneable.class, loadClass("java.lang.AutoCloseable"), Exception.class, RuntimeException.class, IllegalAccessError.class, IllegalAccessException.class, IllegalArgumentException.class, IllegalMonitorStateException.class, IllegalStateException.class, IllegalThreadStateException.class, IndexOutOfBoundsException.class, InstantiationError.class, InstantiationException.class, InternalError.class, InterruptedException.class, LinkageError.class, NegativeArraySizeException.class, NoClassDefFoundError.class, NoSuchFieldError.class, NoSuchFieldException.class, NoSuchMethodError.class, NoSuchMethodException.class, NullPointerException.class, NumberFormatException.class, OutOfMemoryError.class, SecurityException.class, StackOverflowError.class, StringIndexOutOfBoundsException.class, TypeNotPresentException.class, VerifyError.class, StackTraceElement.class, HashMap.class, LinkedHashMap.class, Hashtable.class, TreeMap.class, java.util.IdentityHashMap.class, WeakHashMap.class, LinkedHashMap.class, HashSet.class, LinkedHashSet.class, TreeSet.class, ArrayList.class, TimeUnit.class, ConcurrentHashMap.class, AtomicInteger.class, AtomicLong.class, Collections.EMPTY_MAP.getClass(), Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Number.class, String.class, BigDecimal.class, BigInteger.class, BitSet.class, Calendar.class, java.util.Date.class, Locale.class, UUID.class, SimpleDateFormat.class, JSONObject.class, JSONPObject.class, JSONArray.class};
        for (Class clazz : classes) {
            if (clazz != null) {
                mappings.put(clazz.getName(), clazz);
            }
        }
        ModuleUtil.callWhenHasJavaSql(addBaseClassMappingsFunction, mappings);
    }

    public static void clearClassMapping() {
        mappings.clear();
        addBaseClassMappings();
    }

    public static void addMapping(String className, Class<?> clazz) {
        mappings.put(className, clazz);
    }

    public static Class<?> loadClass(String className) {
        return loadClass(className, null);
    }

    public static boolean isPath(Class<?> clazz) {
        if (pathClass == null && !pathClass_error) {
            try {
                pathClass = Class.forName("java.nio.file.Path");
            } catch (Throwable th) {
                pathClass_error = true;
            }
        }
        if (pathClass != null) {
            return pathClass.isAssignableFrom(clazz);
        }
        return false;
    }

    public static Class<?> getClassFromMapping(String className) {
        return mappings.get(className);
    }

    public static Class<?> loadClass(String className, ClassLoader classLoader) {
        return loadClass(className, classLoader, false);
    }

    public static Class<?> loadClass(String className, ClassLoader classLoader, boolean cache) {
        if (className == null || className.length() == 0) {
            return null;
        }
        if (className.length() > 198) {
            throw new JSONException("illegal className : " + className);
        }
        Class<?> clazz = mappings.get(className);
        if (clazz != null) {
            return clazz;
        }
        if (className.charAt(0) == '[') {
            Class<?> componentType = loadClass(className.substring(1), classLoader);
            return Array.newInstance(componentType, 0).getClass();
        }
        if (className.startsWith("L") && className.endsWith(";")) {
            String newClassName = className.substring(1, className.length() - 1);
            return loadClass(newClassName, classLoader);
        }
        if (classLoader != null) {
            try {
                clazz = classLoader.loadClass(className);
                if (cache) {
                    mappings.put(className, clazz);
                }
                return clazz;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null && contextClassLoader != classLoader) {
                clazz = contextClassLoader.loadClass(className);
                if (cache) {
                    mappings.put(className, clazz);
                }
                return clazz;
            }
        } catch (Throwable th) {
        }
        try {
            clazz = Class.forName(className);
            if (cache) {
                mappings.put(className, clazz);
            }
            return clazz;
        } catch (Throwable th2) {
            return clazz;
        }
    }

    public static SerializeBeanInfo buildBeanInfo(Class<?> beanType, Map<String, String> aliasMap, PropertyNamingStrategy propertyNamingStrategy) {
        return buildBeanInfo(beanType, aliasMap, propertyNamingStrategy, false);
    }

    public static SerializeBeanInfo buildBeanInfo(Class<?> beanType, Map<String, String> aliasMap, PropertyNamingStrategy propertyNamingStrategy, boolean fieldBased) throws SecurityException {
        PropertyNamingStrategy propertyNamingStrategy2;
        String[] orders;
        String typeName;
        String typeKey;
        int features;
        List<FieldInfo> listComputeGetters;
        FieldInfo[] fields;
        List<FieldInfo> sortedFieldList;
        FieldInfo[] sortedFields;
        List<FieldInfo> listComputeGetters2;
        PropertyNamingStrategy propertyNamingStrategy3;
        JSONType jsonType = (JSONType) getAnnotation(beanType, JSONType.class);
        String typeKey2 = null;
        if (jsonType == null) {
            propertyNamingStrategy2 = propertyNamingStrategy;
            orders = null;
            typeName = null;
            typeKey = null;
            features = 0;
        } else {
            String[] orders2 = jsonType.orders();
            String typeName2 = jsonType.typeName();
            if (typeName2.length() == 0) {
                typeName2 = null;
            }
            PropertyNamingStrategy jsonTypeNaming = jsonType.naming();
            if (jsonTypeNaming == PropertyNamingStrategy.NeverUseThisValueExceptDefaultValue) {
                propertyNamingStrategy3 = propertyNamingStrategy;
            } else {
                propertyNamingStrategy3 = jsonTypeNaming;
            }
            int features2 = SerializerFeature.of(jsonType.serialzeFeatures());
            for (Class<?> supperClass = beanType.getSuperclass(); supperClass != null && supperClass != Object.class; supperClass = supperClass.getSuperclass()) {
                JSONType superJsonType = (JSONType) getAnnotation(supperClass, JSONType.class);
                if (superJsonType == null) {
                    break;
                }
                typeKey2 = superJsonType.typeKey();
                if (typeKey2.length() != 0) {
                    break;
                }
            }
            for (Class<?> interfaceClass : beanType.getInterfaces()) {
                JSONType superJsonType2 = (JSONType) getAnnotation(interfaceClass, JSONType.class);
                if (superJsonType2 != null) {
                    typeKey2 = superJsonType2.typeKey();
                    if (typeKey2.length() != 0) {
                        break;
                    }
                }
            }
            if (typeKey2 != null && typeKey2.length() == 0) {
                typeKey2 = null;
            }
            orders = orders2;
            typeName = typeName2;
            typeKey = typeKey2;
            propertyNamingStrategy2 = propertyNamingStrategy3;
            features = features2;
        }
        Map<String, Field> fieldCacheMap = new HashMap<>();
        ParserConfig.parserAllFieldToCache(beanType, fieldCacheMap);
        if (fieldBased) {
            listComputeGetters = computeGettersWithFieldBase(beanType, aliasMap, false, propertyNamingStrategy2);
        } else {
            listComputeGetters = computeGetters(beanType, jsonType, aliasMap, fieldCacheMap, false, propertyNamingStrategy2);
        }
        List<FieldInfo> fieldInfoList = listComputeGetters;
        FieldInfo[] fields2 = new FieldInfo[fieldInfoList.size()];
        fieldInfoList.toArray(fields2);
        if (orders == null || orders.length == 0) {
            fields = fields2;
            List<FieldInfo> sortedFieldList2 = new ArrayList<>(fieldInfoList);
            Collections.sort(sortedFieldList2);
            sortedFieldList = sortedFieldList2;
        } else {
            if (fieldBased) {
                listComputeGetters2 = computeGettersWithFieldBase(beanType, aliasMap, true, propertyNamingStrategy2);
                fields = fields2;
            } else {
                fields = fields2;
                listComputeGetters2 = computeGetters(beanType, jsonType, aliasMap, fieldCacheMap, true, propertyNamingStrategy2);
            }
            sortedFieldList = listComputeGetters2;
        }
        FieldInfo[] sortedFields2 = new FieldInfo[sortedFieldList.size()];
        sortedFieldList.toArray(sortedFields2);
        if (!Arrays.equals(sortedFields2, fields)) {
            sortedFields = sortedFields2;
        } else {
            sortedFields = fields;
        }
        return new SerializeBeanInfo(beanType, jsonType, typeName, typeKey, features, fields, sortedFields);
    }

    public static List<FieldInfo> computeGettersWithFieldBase(Class<?> clazz, Map<String, String> aliasMap, boolean sorted, PropertyNamingStrategy propertyNamingStrategy) {
        Map<String, FieldInfo> fieldInfoMap = new LinkedHashMap<>();
        for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Field[] fields = currentClass.getDeclaredFields();
            computeFields(currentClass, aliasMap, propertyNamingStrategy, fieldInfoMap, fields);
        }
        return getFieldInfos(clazz, sorted, fieldInfoMap);
    }

    public static List<FieldInfo> computeGetters(Class<?> clazz, Map<String, String> aliasMap) {
        return computeGetters(clazz, aliasMap, true);
    }

    public static List<FieldInfo> computeGetters(Class<?> clazz, Map<String, String> aliasMap, boolean sorted) {
        JSONType jsonType = (JSONType) getAnnotation(clazz, JSONType.class);
        Map<String, Field> fieldCacheMap = new HashMap<>();
        ParserConfig.parserAllFieldToCache(clazz, fieldCacheMap);
        return computeGetters(clazz, jsonType, aliasMap, fieldCacheMap, sorted, PropertyNamingStrategy.CamelCase);
    }

    /* JADX WARN: Removed duplicated region for block: B:170:0x038c  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x041c  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0448  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<FieldInfo> computeGetters(Class<?> clazz, JSONType jsonType, Map<String, String> aliasMap, Map<String, Field> fieldCacheMap, boolean sorted, PropertyNamingStrategy propertyNamingStrategy) throws SecurityException {
        int i;
        short[] paramNameMapping;
        int i2;
        Method[] methods;
        Map<String, FieldInfo> fieldInfoMap;
        int ordinal;
        Constructor[] constructors;
        Annotation[][] paramAnnotationArrays;
        String[] paramNames;
        JSONField annotation;
        String methodName;
        Method method;
        Class<?> returnType;
        Map<String, FieldInfo> fieldInfoMap2;
        int ordinal2;
        JSONField annotation2;
        Map<String, FieldInfo> fieldInfoMap3;
        int i3;
        Map<String, FieldInfo> fieldInfoMap4;
        String propertyName;
        Field field;
        Map<String, String> map;
        int parserFeatures;
        String label;
        int parserFeatures2;
        int serialzeFeatures;
        JSONField fieldAnnotation;
        String propertyName2;
        int i4;
        String propertyName3;
        String propertyName4;
        Field field2;
        Map<String, String> map2;
        int parserFeatures3;
        Boolean fieldAnnotationAndNameExists;
        int parserFeatures4;
        String label2;
        int serialzeFeatures2;
        JSONField fieldAnnotation2;
        String propertyName5;
        char ch;
        String propertyName6;
        JSONField annotation3;
        Field field3;
        Constructor creatorConstructor;
        Constructor[] constructors2;
        Class<?> cls = clazz;
        Map<String, String> map3 = aliasMap;
        Map<String, Field> map4 = fieldCacheMap;
        Map<String, FieldInfo> fieldInfoMap5 = new LinkedHashMap<>();
        boolean kotlin2 = isKotlin(clazz);
        Constructor[] constructors3 = null;
        Annotation[][] paramAnnotationArrays2 = (Annotation[][]) null;
        String[] paramNames2 = null;
        short[] paramNameMapping2 = null;
        Method[] methods2 = clazz.getMethods();
        try {
            Arrays.sort(methods2, new MethodInheritanceComparator());
        } catch (Throwable th) {
        }
        int length = methods2.length;
        int i5 = 0;
        while (i5 < length) {
            Method method2 = methods2[i5];
            String methodName2 = method2.getName();
            int ordinal3 = 0;
            int serialzeFeatures3 = 0;
            int parserFeatures5 = 0;
            String label3 = null;
            if (Modifier.isStatic(method2.getModifiers())) {
                i = length;
                paramNameMapping = paramNameMapping2;
                i2 = i5;
                methods = methods2;
                fieldInfoMap = fieldInfoMap5;
            } else {
                Map<String, FieldInfo> fieldInfoMap6 = fieldInfoMap5;
                Class<?> returnType2 = method2.getReturnType();
                if (returnType2.equals(Void.TYPE)) {
                    i = length;
                    paramNameMapping = paramNameMapping2;
                    i2 = i5;
                    methods = methods2;
                    fieldInfoMap = fieldInfoMap6;
                } else if (method2.getParameterTypes().length != 0) {
                    i = length;
                    paramNameMapping = paramNameMapping2;
                    i2 = i5;
                    methods = methods2;
                    fieldInfoMap = fieldInfoMap6;
                } else if (returnType2 == ClassLoader.class || returnType2 == InputStream.class) {
                    i = length;
                    paramNameMapping = paramNameMapping2;
                    i2 = i5;
                    methods = methods2;
                    fieldInfoMap = fieldInfoMap6;
                } else if (returnType2 == Reader.class) {
                    i = length;
                    paramNameMapping = paramNameMapping2;
                    i2 = i5;
                    methods = methods2;
                    fieldInfoMap = fieldInfoMap6;
                } else {
                    if (!methodName2.equals("getMetaClass")) {
                        i = length;
                    } else {
                        i = length;
                        if (returnType2.getName().equals("groovy.lang.MetaClass")) {
                            paramNameMapping = paramNameMapping2;
                            i2 = i5;
                            methods = methods2;
                            fieldInfoMap = fieldInfoMap6;
                        }
                    }
                    if (methodName2.equals("getSuppressed") && method2.getDeclaringClass() == Throwable.class) {
                        paramNameMapping = paramNameMapping2;
                        i2 = i5;
                        methods = methods2;
                        fieldInfoMap = fieldInfoMap6;
                    } else if (kotlin2 && isKotlinIgnore(cls, methodName2)) {
                        paramNameMapping = paramNameMapping2;
                        i2 = i5;
                        methods = methods2;
                        fieldInfoMap = fieldInfoMap6;
                    } else {
                        boolean fieldAnnotationAndNameExists2 = false;
                        JSONField annotation4 = (JSONField) getAnnotation(method2, JSONField.class);
                        if (annotation4 == null) {
                            annotation4 = getSuperMethodAnnotation(cls, method2);
                        }
                        if (annotation4 != null || !kotlin2) {
                            paramNameMapping = paramNameMapping2;
                            ordinal = 0;
                            constructors = constructors3;
                            paramAnnotationArrays = paramAnnotationArrays2;
                            paramNames = paramNames2;
                            annotation = annotation4;
                        } else {
                            if (constructors3 != null || (creatorConstructor = getKotlinConstructor((constructors3 = clazz.getDeclaredConstructors()))) == null) {
                                annotation3 = annotation4;
                            } else {
                                paramAnnotationArrays2 = getParameterAnnotations(creatorConstructor);
                                paramNames2 = getKoltinConstructorParameters(clazz);
                                if (paramNames2 == null) {
                                    annotation3 = annotation4;
                                } else {
                                    String[] paramNames_sorted = new String[paramNames2.length];
                                    annotation3 = annotation4;
                                    System.arraycopy(paramNames2, 0, paramNames_sorted, 0, paramNames2.length);
                                    Arrays.sort(paramNames_sorted);
                                    short[] paramNameMapping3 = new short[paramNames2.length];
                                    short p = 0;
                                    while (true) {
                                        constructors2 = constructors3;
                                        if (p >= paramNames2.length) {
                                            break;
                                        }
                                        int index = Arrays.binarySearch(paramNames_sorted, paramNames2[p]);
                                        paramNameMapping3[index] = p;
                                        int index2 = p + 1;
                                        p = (short) index2;
                                        constructors3 = constructors2;
                                    }
                                    paramNames2 = paramNames_sorted;
                                    paramNameMapping2 = paramNameMapping3;
                                    constructors3 = constructors2;
                                }
                            }
                            if (paramNames2 == null || paramNameMapping2 == null || !methodName2.startsWith("get")) {
                                constructors = constructors3;
                                paramAnnotationArrays = paramAnnotationArrays2;
                                paramNames = paramNames2;
                                ordinal = 0;
                            } else {
                                String propertyName7 = decapitalize(methodName2.substring(3));
                                int p2 = Arrays.binarySearch(paramNames2, propertyName7);
                                if (p2 >= 0) {
                                    constructors = constructors3;
                                    ordinal = 0;
                                } else {
                                    constructors = constructors3;
                                    int i6 = 0;
                                    while (true) {
                                        ordinal = ordinal3;
                                        int ordinal4 = paramNames2.length;
                                        if (i6 >= ordinal4) {
                                            break;
                                        }
                                        if (!propertyName7.equalsIgnoreCase(paramNames2[i6])) {
                                            i6++;
                                            ordinal3 = ordinal;
                                        } else {
                                            p2 = i6;
                                            break;
                                        }
                                    }
                                }
                                if (p2 < 0) {
                                    paramAnnotationArrays = paramAnnotationArrays2;
                                    paramNames = paramNames2;
                                } else {
                                    short index3 = paramNameMapping2[p2];
                                    Annotation[] paramAnnotations = paramAnnotationArrays2[index3];
                                    if (paramAnnotations != null) {
                                        int length2 = paramAnnotations.length;
                                        paramAnnotationArrays = paramAnnotationArrays2;
                                        int i7 = 0;
                                        while (true) {
                                            if (i7 >= length2) {
                                                paramNames = paramNames2;
                                                break;
                                            }
                                            int i8 = length2;
                                            Annotation paramAnnotation = paramAnnotations[i7];
                                            paramNames = paramNames2;
                                            if (!(paramAnnotation instanceof JSONField)) {
                                                i7++;
                                                length2 = i8;
                                                paramNames2 = paramNames;
                                            } else {
                                                annotation3 = (JSONField) paramAnnotation;
                                                break;
                                            }
                                        }
                                    } else {
                                        paramAnnotationArrays = paramAnnotationArrays2;
                                        paramNames = paramNames2;
                                    }
                                    if (annotation3 == null && (field3 = ParserConfig.getFieldFromCache(propertyName7, map4)) != null) {
                                        annotation = (JSONField) getAnnotation(field3, JSONField.class);
                                        paramNameMapping = paramNameMapping2;
                                    }
                                }
                            }
                            paramNameMapping = paramNameMapping2;
                            annotation = annotation3;
                        }
                        if (annotation == null) {
                            methodName = methodName2;
                            method = method2;
                            i2 = i5;
                            methods = methods2;
                            returnType = returnType2;
                            fieldInfoMap2 = fieldInfoMap6;
                            ordinal2 = ordinal;
                        } else {
                            if (!annotation.serialize()) {
                                i2 = i5;
                                methods = methods2;
                                fieldInfoMap = fieldInfoMap6;
                            } else {
                                int ordinal5 = annotation.ordinal();
                                serialzeFeatures3 = SerializerFeature.of(annotation.serialzeFeatures());
                                parserFeatures5 = Feature.of(annotation.parseFeatures());
                                if (annotation.name().length() != 0) {
                                    String propertyName8 = annotation.name();
                                    if (map3 == null) {
                                        propertyName6 = propertyName8;
                                    } else {
                                        String propertyName9 = map3.get(propertyName8);
                                        if (propertyName9 != null) {
                                            propertyName6 = propertyName9;
                                        } else {
                                            i2 = i5;
                                            methods = methods2;
                                            fieldInfoMap = fieldInfoMap6;
                                        }
                                    }
                                    i2 = i5;
                                    methods = methods2;
                                    FieldInfo fieldInfo = new FieldInfo(propertyName6, method2, null, clazz, null, ordinal5, serialzeFeatures3, parserFeatures5, annotation, null, null);
                                    fieldInfoMap6.put(propertyName6, fieldInfo);
                                    fieldInfoMap = fieldInfoMap6;
                                } else {
                                    methodName = methodName2;
                                    method = method2;
                                    i2 = i5;
                                    methods = methods2;
                                    returnType = returnType2;
                                    fieldInfoMap2 = fieldInfoMap6;
                                    if (annotation.label().length() == 0) {
                                        ordinal2 = ordinal5;
                                    } else {
                                        label3 = annotation.label();
                                        ordinal2 = ordinal5;
                                    }
                                }
                            }
                            constructors3 = constructors;
                            paramAnnotationArrays2 = paramAnnotationArrays;
                            paramNames2 = paramNames;
                        }
                        String methodName3 = methodName;
                        if (!methodName3.startsWith("get")) {
                            annotation2 = annotation;
                            fieldInfoMap3 = fieldInfoMap2;
                            i3 = 3;
                        } else {
                            if (methodName3.length() < 4 || methodName3.equals("getClass")) {
                                fieldInfoMap = fieldInfoMap2;
                            } else if (methodName3.equals("getDeclaringClass") && clazz.isEnum()) {
                                fieldInfoMap = fieldInfoMap2;
                            } else {
                                char c3 = methodName3.charAt(3);
                                Field field4 = null;
                                if (Character.isUpperCase(c3) || c3 > 512) {
                                    if (compatibleWithJavaBean) {
                                        i4 = 3;
                                        propertyName3 = decapitalize(methodName3.substring(3));
                                    } else {
                                        i4 = 3;
                                        propertyName3 = getPropertyNameByMethodName(methodName3);
                                    }
                                    propertyName4 = getPropertyNameByCompatibleFieldName(map4, methodName3, propertyName3, i4);
                                } else if (c3 == '_') {
                                    propertyName4 = methodName3.substring(3);
                                    field4 = map4.get(propertyName4);
                                    if (field4 == null) {
                                        String propertyName10 = methodName3.substring(4);
                                        field4 = ParserConfig.getFieldFromCache(propertyName10, map4);
                                        if (field4 != null) {
                                            propertyName4 = propertyName10;
                                        } else {
                                            propertyName4 = propertyName4;
                                        }
                                    }
                                } else if (c3 == 'f') {
                                    propertyName4 = methodName3.substring(3);
                                } else if (methodName3.length() >= 5 && Character.isUpperCase(methodName3.charAt(4))) {
                                    propertyName4 = decapitalize(methodName3.substring(3));
                                } else {
                                    propertyName4 = methodName3.substring(3);
                                    field4 = ParserConfig.getFieldFromCache(propertyName4, map4);
                                    if (field4 == null) {
                                        fieldInfoMap = fieldInfoMap2;
                                    }
                                }
                                boolean ignore = isJSONTypeIgnore(cls, propertyName4);
                                if (ignore) {
                                    fieldInfoMap = fieldInfoMap2;
                                } else {
                                    if (field4 == null) {
                                        field4 = ParserConfig.getFieldFromCache(propertyName4, map4);
                                    }
                                    if (field4 == null && propertyName4.length() > 1 && (ch = propertyName4.charAt(1)) >= 'A' && ch <= 'Z') {
                                        String javaBeanCompatiblePropertyName = decapitalize(methodName3.substring(3));
                                        field2 = ParserConfig.getFieldFromCache(javaBeanCompatiblePropertyName, map4);
                                        if (field2 != null) {
                                        }
                                        if (map2 == null) {
                                        }
                                        if (propertyNamingStrategy == null) {
                                            propertyName5 = propertyName4;
                                            annotation2 = annotation;
                                            fieldInfoMap3 = fieldInfoMap2;
                                            i3 = 3;
                                            FieldInfo fieldInfo2 = new FieldInfo(propertyName5, method, field2, clazz, null, parserFeatures4, serialzeFeatures2, parserFeatures3, annotation, fieldAnnotation2, label2);
                                            fieldInfoMap3.put(propertyName5, fieldInfo2);
                                            ordinal2 = parserFeatures4;
                                            serialzeFeatures3 = serialzeFeatures2;
                                            parserFeatures5 = parserFeatures3;
                                            label3 = label2;
                                        }
                                    } else {
                                        field2 = field4;
                                        if (field2 != null) {
                                            map2 = aliasMap;
                                            parserFeatures3 = parserFeatures5;
                                            fieldAnnotationAndNameExists = false;
                                            parserFeatures4 = ordinal2;
                                            label2 = label3;
                                            serialzeFeatures2 = serialzeFeatures3;
                                            fieldAnnotation2 = null;
                                        } else {
                                            JSONField fieldAnnotation3 = (JSONField) getAnnotation(field2, JSONField.class);
                                            if (fieldAnnotation3 == null) {
                                                map2 = aliasMap;
                                                parserFeatures3 = parserFeatures5;
                                                fieldAnnotationAndNameExists = false;
                                                parserFeatures4 = ordinal2;
                                                label2 = label3;
                                                serialzeFeatures2 = serialzeFeatures3;
                                                fieldAnnotation2 = fieldAnnotation3;
                                            } else if (!fieldAnnotation3.serialize()) {
                                                fieldInfoMap = fieldInfoMap2;
                                            } else {
                                                int ordinal6 = fieldAnnotation3.ordinal();
                                                int serialzeFeatures4 = SerializerFeature.of(fieldAnnotation3.serialzeFeatures());
                                                int parserFeatures6 = Feature.of(fieldAnnotation3.parseFeatures());
                                                if (fieldAnnotation3.name().length() == 0) {
                                                    map2 = aliasMap;
                                                } else {
                                                    fieldAnnotationAndNameExists2 = true;
                                                    String propertyName11 = fieldAnnotation3.name();
                                                    if (aliasMap == null) {
                                                        propertyName4 = propertyName11;
                                                        map2 = aliasMap;
                                                    } else {
                                                        String propertyName12 = aliasMap.get(propertyName11);
                                                        if (propertyName12 != null) {
                                                            propertyName4 = propertyName12;
                                                            map2 = aliasMap;
                                                        } else {
                                                            fieldInfoMap = fieldInfoMap2;
                                                        }
                                                    }
                                                }
                                                if (fieldAnnotation3.label().length() == 0) {
                                                    fieldAnnotation2 = fieldAnnotation3;
                                                    parserFeatures4 = ordinal6;
                                                    parserFeatures3 = parserFeatures6;
                                                    fieldAnnotationAndNameExists = fieldAnnotationAndNameExists2;
                                                    label2 = label3;
                                                    serialzeFeatures2 = serialzeFeatures4;
                                                } else {
                                                    parserFeatures4 = ordinal6;
                                                    serialzeFeatures2 = serialzeFeatures4;
                                                    parserFeatures3 = parserFeatures6;
                                                    fieldAnnotationAndNameExists = fieldAnnotationAndNameExists2;
                                                    label2 = fieldAnnotation3.label();
                                                    fieldAnnotation2 = fieldAnnotation3;
                                                }
                                            }
                                        }
                                        if (map2 == null && (propertyName4 = map2.get(propertyName4)) == null) {
                                            fieldInfoMap = fieldInfoMap2;
                                        } else {
                                            if (propertyNamingStrategy == null && !fieldAnnotationAndNameExists.booleanValue()) {
                                                propertyName5 = propertyNamingStrategy.translate(propertyName4);
                                            } else {
                                                propertyName5 = propertyName4;
                                            }
                                            annotation2 = annotation;
                                            fieldInfoMap3 = fieldInfoMap2;
                                            i3 = 3;
                                            FieldInfo fieldInfo22 = new FieldInfo(propertyName5, method, field2, clazz, null, parserFeatures4, serialzeFeatures2, parserFeatures3, annotation, fieldAnnotation2, label2);
                                            fieldInfoMap3.put(propertyName5, fieldInfo22);
                                            ordinal2 = parserFeatures4;
                                            serialzeFeatures3 = serialzeFeatures2;
                                            parserFeatures5 = parserFeatures3;
                                            label3 = label2;
                                        }
                                    }
                                }
                            }
                            constructors3 = constructors;
                            paramAnnotationArrays2 = paramAnnotationArrays;
                            paramNames2 = paramNames;
                        }
                        if (!methodName3.startsWith("is") || methodName3.length() < i3) {
                            fieldInfoMap = fieldInfoMap3;
                            map4 = fieldCacheMap;
                        } else {
                            Class<?> returnType3 = returnType;
                            if (returnType3 != Boolean.TYPE && returnType3 != Boolean.class) {
                                fieldInfoMap = fieldInfoMap3;
                                map4 = fieldCacheMap;
                            } else {
                                char c2 = methodName3.charAt(2);
                                Field field5 = null;
                                if (Character.isUpperCase(c2)) {
                                    if (!compatibleWithJavaBean) {
                                        propertyName2 = Character.toLowerCase(methodName3.charAt(2)) + methodName3.substring(i3);
                                    } else {
                                        propertyName2 = decapitalize(methodName3.substring(2));
                                    }
                                    fieldInfoMap4 = fieldInfoMap3;
                                    map4 = fieldCacheMap;
                                    propertyName = getPropertyNameByCompatibleFieldName(map4, methodName3, propertyName2, 2);
                                } else {
                                    fieldInfoMap4 = fieldInfoMap3;
                                    map4 = fieldCacheMap;
                                    if (c2 == '_') {
                                        propertyName = methodName3.substring(i3);
                                        field5 = map4.get(propertyName);
                                        if (field5 == null && (field5 = ParserConfig.getFieldFromCache((propertyName = methodName3.substring(2)), map4)) == null) {
                                            propertyName = propertyName;
                                        }
                                    } else if (c2 == 'f') {
                                        propertyName = methodName3.substring(2);
                                    } else {
                                        propertyName = methodName3.substring(2);
                                        field5 = ParserConfig.getFieldFromCache(propertyName, map4);
                                        if (field5 == null) {
                                            fieldInfoMap = fieldInfoMap4;
                                        }
                                    }
                                }
                                boolean ignore2 = isJSONTypeIgnore(clazz, propertyName);
                                if (ignore2) {
                                    fieldInfoMap = fieldInfoMap4;
                                } else {
                                    if (field5 == null) {
                                        field5 = ParserConfig.getFieldFromCache(propertyName, map4);
                                    }
                                    if (field5 != null) {
                                        field = field5;
                                    } else {
                                        field = ParserConfig.getFieldFromCache(methodName3, map4);
                                    }
                                    if (field == null) {
                                        map = aliasMap;
                                        parserFeatures = parserFeatures5;
                                        label = label3;
                                        parserFeatures2 = ordinal2;
                                        serialzeFeatures = serialzeFeatures3;
                                        fieldAnnotation = null;
                                    } else {
                                        JSONField fieldAnnotation4 = (JSONField) getAnnotation(field, JSONField.class);
                                        if (fieldAnnotation4 == null) {
                                            map = aliasMap;
                                            parserFeatures = parserFeatures5;
                                            label = label3;
                                            parserFeatures2 = ordinal2;
                                            serialzeFeatures = serialzeFeatures3;
                                            fieldAnnotation = fieldAnnotation4;
                                        } else if (!fieldAnnotation4.serialize()) {
                                            fieldInfoMap = fieldInfoMap4;
                                        } else {
                                            int ordinal7 = fieldAnnotation4.ordinal();
                                            int serialzeFeatures5 = SerializerFeature.of(fieldAnnotation4.serialzeFeatures());
                                            int parserFeatures7 = Feature.of(fieldAnnotation4.parseFeatures());
                                            if (fieldAnnotation4.name().length() == 0) {
                                                map = aliasMap;
                                            } else {
                                                propertyName = fieldAnnotation4.name();
                                                map = aliasMap;
                                                if (map != null && (propertyName = map.get(propertyName)) == null) {
                                                    fieldInfoMap = fieldInfoMap4;
                                                }
                                            }
                                            if (fieldAnnotation4.label().length() == 0) {
                                                parserFeatures = parserFeatures7;
                                                label = label3;
                                                parserFeatures2 = ordinal7;
                                                serialzeFeatures = serialzeFeatures5;
                                                fieldAnnotation = fieldAnnotation4;
                                            } else {
                                                parserFeatures = parserFeatures7;
                                                label = fieldAnnotation4.label();
                                                parserFeatures2 = ordinal7;
                                                serialzeFeatures = serialzeFeatures5;
                                                fieldAnnotation = fieldAnnotation4;
                                            }
                                        }
                                    }
                                    if (map != null && (propertyName = map.get(propertyName)) == null) {
                                        fieldInfoMap = fieldInfoMap4;
                                    } else {
                                        if (propertyNamingStrategy != null) {
                                            propertyName = propertyNamingStrategy.translate(propertyName);
                                        }
                                        if (fieldInfoMap4.containsKey(propertyName)) {
                                            fieldInfoMap = fieldInfoMap4;
                                        } else {
                                            FieldInfo fieldInfo3 = new FieldInfo(propertyName, method, field, clazz, null, parserFeatures2, serialzeFeatures, parserFeatures, annotation2, fieldAnnotation, label);
                                            fieldInfoMap = fieldInfoMap4;
                                            fieldInfoMap.put(propertyName, fieldInfo3);
                                        }
                                    }
                                }
                            }
                        }
                        constructors3 = constructors;
                        paramAnnotationArrays2 = paramAnnotationArrays;
                        paramNames2 = paramNames;
                    }
                }
            }
            i5 = i2 + 1;
            cls = clazz;
            map3 = aliasMap;
            fieldInfoMap5 = fieldInfoMap;
            length = i;
            paramNameMapping2 = paramNameMapping;
            methods2 = methods;
        }
        Map<String, FieldInfo> fieldInfoMap7 = fieldInfoMap5;
        Field[] fields = clazz.getFields();
        computeFields(clazz, aliasMap, propertyNamingStrategy, fieldInfoMap7, fields);
        return getFieldInfos(clazz, sorted, fieldInfoMap7);
    }

    private static List<FieldInfo> getFieldInfos(Class<?> clazz, boolean sorted, Map<String, FieldInfo> fieldInfoMap) {
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        String[] orders = null;
        JSONType annotation = (JSONType) getAnnotation(clazz, JSONType.class);
        if (annotation != null) {
            orders = annotation.orders();
        }
        if (orders != null && orders.length > 0) {
            LinkedHashMap<String, FieldInfo> map = new LinkedHashMap<>(fieldInfoMap.size());
            for (FieldInfo field : fieldInfoMap.values()) {
                map.put(field.name, field);
            }
            for (String item : orders) {
                FieldInfo field2 = map.get(item);
                if (field2 != null) {
                    fieldInfoList.add(field2);
                    map.remove(item);
                }
            }
            fieldInfoList.addAll(map.values());
        } else {
            fieldInfoList.addAll(fieldInfoMap.values());
            if (sorted) {
                Collections.sort(fieldInfoList);
            }
        }
        return fieldInfoList;
    }

    private static void computeFields(Class<?> clazz, Map<String, String> aliasMap, PropertyNamingStrategy propertyNamingStrategy, Map<String, FieldInfo> fieldInfoMap, Field[] fields) {
        int ordinal;
        int serialzeFeatures;
        int parserFeatures;
        String label;
        String propertyName;
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                JSONField fieldAnnotation = (JSONField) getAnnotation(field, JSONField.class);
                String propertyName2 = field.getName();
                if (fieldAnnotation == null) {
                    ordinal = 0;
                    serialzeFeatures = 0;
                    parserFeatures = 0;
                    label = null;
                } else if (fieldAnnotation.serialize()) {
                    int ordinal2 = fieldAnnotation.ordinal();
                    int serialzeFeatures2 = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                    int parserFeatures2 = Feature.of(fieldAnnotation.parseFeatures());
                    if (fieldAnnotation.name().length() != 0) {
                        propertyName2 = fieldAnnotation.name();
                    }
                    if (fieldAnnotation.label().length() == 0) {
                        ordinal = ordinal2;
                        serialzeFeatures = serialzeFeatures2;
                        parserFeatures = parserFeatures2;
                        label = null;
                    } else {
                        String label2 = fieldAnnotation.label();
                        ordinal = ordinal2;
                        serialzeFeatures = serialzeFeatures2;
                        parserFeatures = parserFeatures2;
                        label = label2;
                    }
                }
                if (aliasMap == null || (propertyName2 = aliasMap.get(propertyName2)) != null) {
                    if (propertyNamingStrategy == null) {
                        propertyName = propertyName2;
                    } else {
                        propertyName = propertyNamingStrategy.translate(propertyName2);
                    }
                    if (!fieldInfoMap.containsKey(propertyName)) {
                        FieldInfo fieldInfo = new FieldInfo(propertyName, null, field, clazz, null, ordinal, serialzeFeatures, parserFeatures, null, fieldAnnotation, label);
                        fieldInfoMap.put(propertyName, fieldInfo);
                    }
                }
            }
        }
    }

    private static String getPropertyNameByCompatibleFieldName(Map<String, Field> fieldCacheMap, String methodName, String propertyName, int fromIdx) {
        if (compatibleWithFieldName && !fieldCacheMap.containsKey(propertyName)) {
            String tempPropertyName = methodName.substring(fromIdx);
            return fieldCacheMap.containsKey(tempPropertyName) ? tempPropertyName : propertyName;
        }
        return propertyName;
    }

    public static JSONField getSuperMethodAnnotation(Class<?> clazz, Method method) throws SecurityException {
        JSONField annotation;
        JSONField annotation2;
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            Class<?>[] types = method.getParameterTypes();
            for (Class<?> interfaceClass : interfaces) {
                for (Method interfaceMethod : interfaceClass.getMethods()) {
                    Class<?>[] interfaceTypes = interfaceMethod.getParameterTypes();
                    if (interfaceTypes.length == types.length && interfaceMethod.getName().equals(method.getName())) {
                        boolean match = true;
                        int i = 0;
                        while (true) {
                            if (i >= types.length) {
                                break;
                            }
                            if (interfaceTypes[i].equals(types[i])) {
                                i++;
                            } else {
                                match = false;
                                break;
                            }
                        }
                        if (match && (annotation2 = (JSONField) getAnnotation(interfaceMethod, JSONField.class)) != null) {
                            return annotation2;
                        }
                    }
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && Modifier.isAbstract(superClass.getModifiers())) {
            Class<?>[] types2 = method.getParameterTypes();
            for (Method interfaceMethod2 : superClass.getMethods()) {
                Class<?>[] interfaceTypes2 = interfaceMethod2.getParameterTypes();
                if (interfaceTypes2.length == types2.length && interfaceMethod2.getName().equals(method.getName())) {
                    boolean match2 = true;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= types2.length) {
                            break;
                        }
                        if (interfaceTypes2[i2].equals(types2[i2])) {
                            i2++;
                        } else {
                            match2 = false;
                            break;
                        }
                    }
                    if (match2 && (annotation = (JSONField) getAnnotation(interfaceMethod2, JSONField.class)) != null) {
                        return annotation;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isJSONTypeIgnore(Class<?> clazz, String propertyName) {
        JSONType jsonType = (JSONType) getAnnotation(clazz, JSONType.class);
        if (jsonType != null) {
            String[] fields = jsonType.includes();
            if (fields.length > 0) {
                for (String field : fields) {
                    if (propertyName.equals(field)) {
                        return false;
                    }
                }
                return true;
            }
            for (String field2 : jsonType.ignores()) {
                if (propertyName.equals(field2)) {
                    return true;
                }
            }
        }
        if (clazz.getSuperclass() == Object.class || clazz.getSuperclass() == null) {
            return false;
        }
        return isJSONTypeIgnore(clazz.getSuperclass(), propertyName);
    }

    public static boolean isGenericParamType(Type type) {
        Type superType;
        if (type instanceof ParameterizedType) {
            return true;
        }
        return (type instanceof Class) && (superType = ((Class) type).getGenericSuperclass()) != Object.class && isGenericParamType(superType);
    }

    public static Type getGenericParamType(Type type) {
        if (!(type instanceof ParameterizedType) && (type instanceof Class)) {
            return getGenericParamType(((Class) type).getGenericSuperclass());
        }
        return type;
    }

    public static Type unwrapOptional(Type type) {
        if (!optionalClassInited) {
            try {
                optionalClass = Class.forName("java.util.Optional");
            } catch (Exception e) {
            } catch (Throwable th) {
                optionalClassInited = true;
                throw th;
            }
            optionalClassInited = true;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType() == optionalClass) {
                return parameterizedType.getActualTypeArguments()[0];
            }
        }
        return type;
    }

    public static Class<?> getClass(Type type) {
        if (type.getClass() == Class.class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        }
        if (type instanceof TypeVariable) {
            Type boundType = ((TypeVariable) type).getBounds()[0];
            if (boundType instanceof Class) {
                return (Class) boundType;
            }
            return getClass(boundType);
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getClass(upperBounds[0]);
            }
            return Object.class;
        }
        return Object.class;
    }

    public static Field getField(Class<?> clazz, String fieldName, Field[] declaredFields) {
        char c0;
        char c1;
        for (Field field : declaredFields) {
            String itemName = field.getName();
            if (fieldName.equals(itemName)) {
                return field;
            }
            if (fieldName.length() > 2 && (c0 = fieldName.charAt(0)) >= 'a' && c0 <= 'z' && (c1 = fieldName.charAt(1)) >= 'A' && c1 <= 'Z' && fieldName.equalsIgnoreCase(itemName)) {
                return field;
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            return getField(superClass, fieldName, superClass.getDeclaredFields());
        }
        return null;
    }

    public static int getSerializeFeatures(Class<?> clazz) {
        JSONType annotation = (JSONType) getAnnotation(clazz, JSONType.class);
        if (annotation == null) {
            return 0;
        }
        return SerializerFeature.of(annotation.serialzeFeatures());
    }

    public static int getParserFeatures(Class<?> clazz) {
        JSONType annotation = (JSONType) getAnnotation(clazz, JSONType.class);
        if (annotation == null) {
            return 0;
        }
        return Feature.of(annotation.parseFeatures());
    }

    public static String decapitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
            return name;
        }
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    public static String getPropertyNameByMethodName(String methodName) {
        return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
    }

    static void setAccessible(AccessibleObject obj) {
        if (!setAccessibleEnable || obj.isAccessible()) {
            return;
        }
        try {
            obj.setAccessible(true);
        } catch (Throwable th) {
            setAccessibleEnable = false;
        }
    }

    public static Type getCollectionItemType(Type fieldType) {
        if (fieldType instanceof ParameterizedType) {
            return getCollectionItemType((ParameterizedType) fieldType);
        }
        if (fieldType instanceof Class) {
            return getCollectionItemType((Class<?>) fieldType);
        }
        return Object.class;
    }

    private static Type getCollectionItemType(Class<?> clazz) {
        return clazz.getName().startsWith("java.") ? Object.class : getCollectionItemType(getCollectionSuperType(clazz));
    }

    private static Type getCollectionItemType(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (rawType == Collection.class) {
            return getWildcardTypeUpperBounds(actualTypeArguments[0]);
        }
        Class<?> rawClass = (Class) rawType;
        Map<TypeVariable, Type> actualTypeMap = createActualTypeMap(rawClass.getTypeParameters(), actualTypeArguments);
        Type superType = getCollectionSuperType(rawClass);
        if (superType instanceof ParameterizedType) {
            Class<?> superClass = getRawClass(superType);
            Type[] superClassTypeParameters = ((ParameterizedType) superType).getActualTypeArguments();
            if (superClassTypeParameters.length > 0) {
                return getCollectionItemType(makeParameterizedType(superClass, superClassTypeParameters, actualTypeMap));
            }
            return getCollectionItemType(superClass);
        }
        return getCollectionItemType((Class<?>) superType);
    }

    private static Type getCollectionSuperType(Class<?> clazz) {
        Type assignable = null;
        for (Type type : clazz.getGenericInterfaces()) {
            Class<?> rawClass = getRawClass(type);
            if (rawClass == Collection.class) {
                return type;
            }
            if (Collection.class.isAssignableFrom(rawClass)) {
                assignable = type;
            }
        }
        return assignable == null ? clazz.getGenericSuperclass() : assignable;
    }

    private static Map<TypeVariable, Type> createActualTypeMap(TypeVariable[] typeParameters, Type[] actualTypeArguments) {
        int length = typeParameters.length;
        Map<TypeVariable, Type> actualTypeMap = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            actualTypeMap.put(typeParameters[i], actualTypeArguments[i]);
        }
        return actualTypeMap;
    }

    private static ParameterizedType makeParameterizedType(Class<?> rawClass, Type[] typeParameters, Map<TypeVariable, Type> actualTypeMap) {
        int length = typeParameters.length;
        Type[] actualTypeArguments = new Type[length];
        for (int i = 0; i < length; i++) {
            actualTypeArguments[i] = getActualType(typeParameters[i], actualTypeMap);
        }
        return new ParameterizedTypeImpl(actualTypeArguments, null, rawClass);
    }

    private static Type getActualType(Type typeParameter, Map<TypeVariable, Type> actualTypeMap) {
        if (typeParameter instanceof TypeVariable) {
            return actualTypeMap.get(typeParameter);
        }
        if (typeParameter instanceof ParameterizedType) {
            return makeParameterizedType(getRawClass(typeParameter), ((ParameterizedType) typeParameter).getActualTypeArguments(), actualTypeMap);
        }
        if (typeParameter instanceof GenericArrayType) {
            return new GenericArrayTypeImpl(getActualType(((GenericArrayType) typeParameter).getGenericComponentType(), actualTypeMap));
        }
        return typeParameter;
    }

    private static Type getWildcardTypeUpperBounds(Type type) {
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            Type[] upperBounds = wildcardType.getUpperBounds();
            return upperBounds.length > 0 ? upperBounds[0] : Object.class;
        }
        return type;
    }

    public static Class<?> getCollectionItemClass(Type fieldType) {
        if (fieldType instanceof ParameterizedType) {
            Type actualTypeArgument = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
            if (actualTypeArgument instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) actualTypeArgument;
                Type[] upperBounds = wildcardType.getUpperBounds();
                if (upperBounds.length == 1) {
                    actualTypeArgument = upperBounds[0];
                }
            }
            if (actualTypeArgument instanceof Class) {
                Class<?> itemClass = (Class) actualTypeArgument;
                if (!Modifier.isPublic(itemClass.getModifiers())) {
                    throw new JSONException("can not create ASMParser");
                }
                return itemClass;
            }
            throw new JSONException("can not create ASMParser");
        }
        return Object.class;
    }

    public static Type checkPrimitiveArray(GenericArrayType genericArrayType) throws ClassNotFoundException {
        Type genericComponentType = genericArrayType.getGenericComponentType();
        String prefix = "[";
        while (genericComponentType instanceof GenericArrayType) {
            genericComponentType = ((GenericArrayType) genericComponentType).getGenericComponentType();
            prefix = prefix + prefix;
        }
        if (!(genericComponentType instanceof Class)) {
            return genericArrayType;
        }
        Class<?> ck = (Class) genericComponentType;
        if (!ck.isPrimitive()) {
            return genericArrayType;
        }
        try {
            String postfix = (String) primitiveTypeMap.get(ck);
            if (postfix == null) {
                return genericArrayType;
            }
            Type clz = Class.forName(prefix + postfix);
            return clz;
        } catch (ClassNotFoundException e) {
            return genericArrayType;
        }
    }

    public static Set createSet(Type type) {
        Type itemType;
        Class<?> rawClass = getRawClass(type);
        if (rawClass == AbstractCollection.class || rawClass == Collection.class) {
            Set set = new HashSet();
            return set;
        }
        if (rawClass.isAssignableFrom(HashSet.class)) {
            Set set2 = new HashSet();
            return set2;
        }
        if (rawClass.isAssignableFrom(LinkedHashSet.class)) {
            Set set3 = new LinkedHashSet();
            return set3;
        }
        if (rawClass.isAssignableFrom(TreeSet.class)) {
            Set set4 = new TreeSet();
            return set4;
        }
        if (rawClass.isAssignableFrom(EnumSet.class)) {
            if (type instanceof ParameterizedType) {
                itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                itemType = Object.class;
            }
            Set set5 = EnumSet.noneOf((Class) itemType);
            return set5;
        }
        try {
            Set set6 = (Set) rawClass.newInstance();
            return set6;
        } catch (Exception e) {
            throw new JSONException("create instance error, class " + rawClass.getName());
        }
    }

    public static Collection createCollection(Type type) {
        Type itemType;
        Class<?> rawClass = getRawClass(type);
        if (rawClass == AbstractCollection.class || rawClass == Collection.class) {
            Collection list = new ArrayList();
            return list;
        }
        if (rawClass.isAssignableFrom(HashSet.class)) {
            Collection list2 = new HashSet();
            return list2;
        }
        if (rawClass.isAssignableFrom(LinkedHashSet.class)) {
            Collection list3 = new LinkedHashSet();
            return list3;
        }
        if (rawClass.isAssignableFrom(TreeSet.class)) {
            Collection list4 = new TreeSet();
            return list4;
        }
        if (rawClass.isAssignableFrom(ArrayList.class)) {
            Collection list5 = new ArrayList();
            return list5;
        }
        if (rawClass.isAssignableFrom(EnumSet.class)) {
            if (type instanceof ParameterizedType) {
                itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                itemType = Object.class;
            }
            Collection list6 = EnumSet.noneOf((Class) itemType);
            return list6;
        }
        if (rawClass.isAssignableFrom(Queue.class) || (class_deque != null && rawClass.isAssignableFrom(class_deque))) {
            Collection list7 = new LinkedList();
            return list7;
        }
        try {
            Collection list8 = (Collection) rawClass.newInstance();
            return list8;
        } catch (Exception e) {
            throw new JSONException("create instance error, class " + rawClass.getName());
        }
    }

    public static Class<?> getRawClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getRawClass(((ParameterizedType) type).getRawType());
        }
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            Type[] upperBounds = wildcardType.getUpperBounds();
            if (upperBounds.length == 1) {
                return getRawClass(upperBounds[0]);
            }
            throw new JSONException("TODO");
        }
        throw new JSONException("TODO");
    }

    public static boolean isProxy(Class<?> clazz) {
        for (Class<?> item : clazz.getInterfaces()) {
            String interfaceName = item.getName();
            if (isProxyClassNames.contains(interfaceName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTransient(Method method) throws NoSuchMethodException, SecurityException {
        if (method == null) {
            return false;
        }
        if (!transientClassInited) {
            try {
                transientClass = Class.forName("java.beans.Transient");
            } catch (Exception e) {
            } catch (Throwable th) {
                transientClassInited = true;
                throw th;
            }
            transientClassInited = true;
        }
        if (transientClass == null) {
            return false;
        }
        Annotation annotation = getAnnotation(method, (Class<Annotation>) transientClass);
        return annotation != null;
    }

    public static boolean isAnnotationPresentOneToMany(Method method) {
        if (method == null) {
            return false;
        }
        if (class_OneToMany == null && !class_OneToMany_error) {
            try {
                class_OneToMany = Class.forName("javax.persistence.OneToMany");
            } catch (Throwable th) {
                class_OneToMany_error = true;
            }
        }
        return class_OneToMany != null && method.isAnnotationPresent(class_OneToMany);
    }

    public static boolean isAnnotationPresentManyToMany(Method method) {
        if (method == null) {
            return false;
        }
        if (class_ManyToMany == null && !class_ManyToMany_error) {
            try {
                class_ManyToMany = Class.forName("javax.persistence.ManyToMany");
            } catch (Throwable th) {
                class_ManyToMany_error = true;
            }
        }
        if (class_ManyToMany != null) {
            return method.isAnnotationPresent(class_OneToMany) || method.isAnnotationPresent(class_ManyToMany);
        }
        return false;
    }

    public static boolean isHibernateInitialized(Object object) {
        if (object == null) {
            return false;
        }
        if (method_HibernateIsInitialized == null && !method_HibernateIsInitialized_error) {
            try {
                Class<?> class_Hibernate = Class.forName("org.hibernate.Hibernate");
                method_HibernateIsInitialized = class_Hibernate.getMethod("isInitialized", Object.class);
            } catch (Throwable th) {
                method_HibernateIsInitialized_error = true;
            }
        }
        if (method_HibernateIsInitialized != null) {
            try {
                Boolean initialized = (Boolean) method_HibernateIsInitialized.invoke(null, object);
                return initialized.booleanValue();
            } catch (Throwable th2) {
            }
        }
        return true;
    }

    public static double parseDouble(String str) {
        int len = str.length();
        if (len > 10) {
            return Double.parseDouble(str);
        }
        boolean negative = false;
        long longValue = 0;
        int scale = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '-' && i == 0) {
                negative = true;
            } else if (ch == '.') {
                if (scale != 0) {
                    return Double.parseDouble(str);
                }
                int scale2 = (len - i) - 1;
                scale = scale2;
            } else if (ch >= '0' && ch <= '9') {
                int digit = ch - '0';
                longValue = (10 * longValue) + digit;
            } else {
                return Double.parseDouble(str);
            }
        }
        if (negative) {
            longValue = -longValue;
        }
        switch (scale) {
            case 1:
                double d = longValue;
                Double.isNaN(d);
                break;
            case 2:
                double d2 = longValue;
                Double.isNaN(d2);
                break;
            case 3:
                double d3 = longValue;
                Double.isNaN(d3);
                break;
            case 4:
                double d4 = longValue;
                Double.isNaN(d4);
                break;
            case 5:
                double d5 = longValue;
                Double.isNaN(d5);
                break;
            case 6:
                double d6 = longValue;
                Double.isNaN(d6);
                break;
            case 7:
                double d7 = longValue;
                Double.isNaN(d7);
                break;
            case 8:
                double d8 = longValue;
                Double.isNaN(d8);
                break;
            case 9:
                double d9 = longValue;
                Double.isNaN(d9);
                break;
        }
        return Double.parseDouble(str);
    }

    public static float parseFloat(String str) {
        int len = str.length();
        if (len >= 10) {
            return Float.parseFloat(str);
        }
        boolean negative = false;
        long longValue = 0;
        int scale = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '-' && i == 0) {
                negative = true;
            } else if (ch == '.') {
                if (scale != 0) {
                    return Float.parseFloat(str);
                }
                int scale2 = (len - i) - 1;
                scale = scale2;
            } else if (ch >= '0' && ch <= '9') {
                int digit = ch - '0';
                longValue = (10 * longValue) + digit;
            } else {
                return Float.parseFloat(str);
            }
        }
        if (negative) {
            longValue = -longValue;
        }
        switch (scale) {
        }
        return Float.parseFloat(str);
    }

    public static long fnv1a_64_extract(String key) {
        long hashCode = fnv1a_64_magic_hashcode;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch != '_' && ch != '-') {
                if (ch >= 'A' && ch <= 'Z') {
                    ch = (char) (ch + ' ');
                }
                hashCode = (hashCode ^ ch) * fnv1a_64_magic_prime;
            }
        }
        return hashCode;
    }

    public static long fnv1a_64_lower(String key) {
        long hashCode = fnv1a_64_magic_hashcode;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                ch = (char) (ch + ' ');
            }
            hashCode = (hashCode ^ ch) * fnv1a_64_magic_prime;
        }
        return hashCode;
    }

    public static long fnv1a_64(String key) {
        long hashCode = fnv1a_64_magic_hashcode;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            hashCode = (hashCode ^ ch) * fnv1a_64_magic_prime;
        }
        return hashCode;
    }

    public static boolean isKotlin(Class clazz) {
        if (kotlin_metadata == null && !kotlin_metadata_error) {
            try {
                kotlin_metadata = Class.forName("kotlin.Metadata");
            } catch (Throwable th) {
                kotlin_metadata_error = true;
            }
        }
        return kotlin_metadata != null && clazz.isAnnotationPresent(kotlin_metadata);
    }

    public static Constructor getKotlinConstructor(Constructor[] constructors) {
        return getKotlinConstructor(constructors, null);
    }

    public static Constructor getKotlinConstructor(Constructor[] constructors, String[] paramNames) {
        Constructor creatorConstructor = null;
        for (Constructor constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if ((paramNames == null || parameterTypes.length == paramNames.length) && ((parameterTypes.length <= 0 || !parameterTypes[parameterTypes.length - 1].getName().equals("kotlin.jvm.internal.DefaultConstructorMarker")) && (creatorConstructor == null || creatorConstructor.getParameterTypes().length < parameterTypes.length))) {
                creatorConstructor = constructor;
            }
        }
        return creatorConstructor;
    }

    public static String[] getKoltinConstructorParameters(Class clazz) {
        if (kotlin_kclass_constructor == null && !kotlin_class_klass_error) {
            try {
                Class class_kotlin_kclass = Class.forName("kotlin.reflect.jvm.internal.KClassImpl");
                kotlin_kclass_constructor = class_kotlin_kclass.getConstructor(Class.class);
            } catch (Throwable th) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kclass_constructor == null) {
            return null;
        }
        if (kotlin_kclass_getConstructors == null && !kotlin_class_klass_error) {
            try {
                Class class_kotlin_kclass2 = Class.forName("kotlin.reflect.jvm.internal.KClassImpl");
                kotlin_kclass_getConstructors = class_kotlin_kclass2.getMethod("getConstructors", new Class[0]);
            } catch (Throwable th2) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kfunction_getParameters == null && !kotlin_class_klass_error) {
            try {
                Class class_kotlin_kfunction = Class.forName("kotlin.reflect.KFunction");
                kotlin_kfunction_getParameters = class_kotlin_kfunction.getMethod("getParameters", new Class[0]);
            } catch (Throwable th3) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kparameter_getName == null && !kotlin_class_klass_error) {
            try {
                Class class_kotlinn_kparameter = Class.forName("kotlin.reflect.KParameter");
                kotlin_kparameter_getName = class_kotlinn_kparameter.getMethod("getName", new Class[0]);
            } catch (Throwable th4) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_error) {
            return null;
        }
        Object constructor = null;
        try {
            Object kclassImpl = kotlin_kclass_constructor.newInstance(clazz);
            Iterable it = (Iterable) kotlin_kclass_getConstructors.invoke(kclassImpl, new Object[0]);
            Iterator iterator = it.iterator();
            while (iterator.hasNext()) {
                Object item = iterator.next();
                List parameters = (List) kotlin_kfunction_getParameters.invoke(item, new Object[0]);
                if (constructor == null || parameters.size() != 0) {
                    constructor = item;
                }
                iterator.hasNext();
            }
            if (constructor == null) {
                return null;
            }
            List parameters2 = (List) kotlin_kfunction_getParameters.invoke(constructor, new Object[0]);
            String[] names = new String[parameters2.size()];
            for (int i = 0; i < parameters2.size(); i++) {
                Object param = parameters2.get(i);
                names[i] = (String) kotlin_kparameter_getName.invoke(param, new Object[0]);
            }
            return names;
        } catch (Throwable e) {
            e.printStackTrace();
            kotlin_error = true;
            return null;
        }
    }

    private static boolean isKotlinIgnore(Class clazz, String methodName) {
        String[] ignores;
        if (kotlinIgnores == null && !kotlinIgnores_error) {
            try {
                Map<Class, String[]> map = new HashMap<>();
                Class charRangeClass = Class.forName("kotlin.ranges.CharRange");
                map.put(charRangeClass, new String[]{"getEndInclusive", "isEmpty"});
                Class intRangeClass = Class.forName("kotlin.ranges.IntRange");
                map.put(intRangeClass, new String[]{"getEndInclusive", "isEmpty"});
                Class longRangeClass = Class.forName("kotlin.ranges.LongRange");
                map.put(longRangeClass, new String[]{"getEndInclusive", "isEmpty"});
                Class floatRangeClass = Class.forName("kotlin.ranges.ClosedFloatRange");
                map.put(floatRangeClass, new String[]{"getEndInclusive", "isEmpty"});
                Class doubleRangeClass = Class.forName("kotlin.ranges.ClosedDoubleRange");
                map.put(doubleRangeClass, new String[]{"getEndInclusive", "isEmpty"});
                kotlinIgnores = map;
            } catch (Throwable th) {
                kotlinIgnores_error = true;
            }
        }
        return (kotlinIgnores == null || (ignores = kotlinIgnores.get(clazz)) == null || Arrays.binarySearch(ignores, methodName) < 0) ? false : true;
    }

    public static <A extends Annotation> A getAnnotation(Class<?> cls, Class<A> cls2) {
        A a = (A) cls.getAnnotation(cls2);
        Class<?> cls3 = null;
        Type mixInAnnotations = JSON.getMixInAnnotations(cls);
        if (mixInAnnotations instanceof Class) {
            cls3 = (Class) mixInAnnotations;
        }
        if (cls3 != null) {
            A a2 = (A) cls3.getAnnotation(cls2);
            Annotation[] annotations = cls3.getAnnotations();
            if (a2 == null && annotations.length > 0) {
                for (Annotation annotation : annotations) {
                    a2 = (A) annotation.annotationType().getAnnotation(cls2);
                    if (a2 != null) {
                        break;
                    }
                }
            }
            if (a2 != null) {
                return a2;
            }
        }
        Annotation[] annotations2 = cls.getAnnotations();
        if (a == null && annotations2.length > 0) {
            for (Annotation annotation2 : annotations2) {
                a = (A) annotation2.annotationType().getAnnotation(cls2);
                if (a != null) {
                    break;
                }
            }
        }
        return a;
    }

    public static <A extends Annotation> A getAnnotation(Field field, Class<A> cls) throws NoSuchFieldException {
        A a;
        A a2 = (A) field.getAnnotation(cls);
        Class<?> cls2 = null;
        Type mixInAnnotations = JSON.getMixInAnnotations(field.getDeclaringClass());
        if (mixInAnnotations instanceof Class) {
            cls2 = (Class) mixInAnnotations;
        }
        if (cls2 != null) {
            Field declaredField = null;
            String name = field.getName();
            Class<?> superclass = cls2;
            while (true) {
                if (superclass == null || superclass == Object.class) {
                    break;
                }
                try {
                    declaredField = superclass.getDeclaredField(name);
                    break;
                } catch (NoSuchFieldException e) {
                    superclass = superclass.getSuperclass();
                }
            }
            if (declaredField != null && (a = (A) declaredField.getAnnotation(cls)) != null) {
                return a;
            }
        }
        return a2;
    }

    public static <A extends Annotation> A getAnnotation(Method method, Class<A> cls) throws NoSuchMethodException, SecurityException {
        A a;
        A a2 = (A) method.getAnnotation(cls);
        Class<?> cls2 = null;
        Type mixInAnnotations = JSON.getMixInAnnotations(method.getDeclaringClass());
        if (mixInAnnotations instanceof Class) {
            cls2 = (Class) mixInAnnotations;
        }
        if (cls2 != null) {
            Method declaredMethod = null;
            String name = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> superclass = cls2;
            while (true) {
                if (superclass == null || superclass == Object.class) {
                    break;
                }
                try {
                    declaredMethod = superclass.getDeclaredMethod(name, parameterTypes);
                    break;
                } catch (NoSuchMethodException e) {
                    superclass = superclass.getSuperclass();
                }
            }
            if (declaredMethod != null && (a = (A) declaredMethod.getAnnotation(cls)) != null) {
                return a;
            }
        }
        return a2;
    }

    public static Annotation[][] getParameterAnnotations(Method method) {
        Annotation[][] mixInAnnotations;
        Annotation[][] targetAnnotations = method.getParameterAnnotations();
        Class<?> clazz = method.getDeclaringClass();
        Class<?> mixInClass = null;
        Type type = JSON.getMixInAnnotations(clazz);
        if (type instanceof Class) {
            mixInClass = (Class) type;
        }
        if (mixInClass != null) {
            Method mixInMethod = null;
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> currClass = mixInClass;
            while (true) {
                if (currClass == null || currClass == Object.class) {
                    break;
                }
                try {
                    mixInMethod = currClass.getDeclaredMethod(methodName, parameterTypes);
                    break;
                } catch (NoSuchMethodException e) {
                    currClass = currClass.getSuperclass();
                }
            }
            if (mixInMethod != null && (mixInAnnotations = mixInMethod.getParameterAnnotations()) != null) {
                return mixInAnnotations;
            }
        }
        return targetAnnotations;
    }

    public static Annotation[][] getParameterAnnotations(Constructor constructor) {
        Annotation[][] mixInAnnotations;
        Annotation[][] targetAnnotations = constructor.getParameterAnnotations();
        Class<?> clazz = constructor.getDeclaringClass();
        Class<?> mixInClass = null;
        Type type = JSON.getMixInAnnotations(clazz);
        if (type instanceof Class) {
            mixInClass = (Class) type;
        }
        if (mixInClass != null) {
            Constructor mixInConstructor = null;
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            List<Class<?>> enclosingClasses = new ArrayList<>(2);
            for (Class<?> enclosingClass = mixInClass.getEnclosingClass(); enclosingClass != null; enclosingClass = enclosingClass.getEnclosingClass()) {
                enclosingClasses.add(enclosingClass);
            }
            int level = enclosingClasses.size();
            Class<?> currClass = mixInClass;
            while (true) {
                if (currClass == null || currClass == Object.class) {
                    break;
                }
                try {
                    if (level != 0) {
                        Class<?>[] outerClassAndParameterTypes = new Class[parameterTypes.length + level];
                        System.arraycopy(parameterTypes, 0, outerClassAndParameterTypes, level, parameterTypes.length);
                        for (int i = level; i > 0; i--) {
                            outerClassAndParameterTypes[i - 1] = enclosingClasses.get(i - 1);
                        }
                        mixInConstructor = mixInClass.getDeclaredConstructor(outerClassAndParameterTypes);
                    } else {
                        mixInConstructor = mixInClass.getDeclaredConstructor(parameterTypes);
                    }
                } catch (NoSuchMethodException e) {
                    level--;
                    currClass = currClass.getSuperclass();
                }
            }
            if (mixInConstructor != null && (mixInAnnotations = mixInConstructor.getParameterAnnotations()) != null) {
                return mixInAnnotations;
            }
        }
        return targetAnnotations;
    }

    public static boolean isJacksonCreator(Method method) {
        if (method == null) {
            return false;
        }
        if (class_JacksonCreator == null && !class_JacksonCreator_error) {
            try {
                class_JacksonCreator = Class.forName("com.fasterxml.jackson.annotation.JsonCreator");
            } catch (Throwable th) {
                class_JacksonCreator_error = true;
            }
        }
        return class_JacksonCreator != null && method.isAnnotationPresent(class_JacksonCreator);
    }

    public static Object optionalEmpty(Type type) {
        Class clazz;
        if (OPTIONAL_ERROR || (clazz = getClass(type)) == null) {
            return null;
        }
        String className = clazz.getName();
        if (!"java.util.Optional".equals(className)) {
            return null;
        }
        if (OPTIONAL_EMPTY == null) {
            try {
                Method empty = Class.forName(className).getMethod("empty", new Class[0]);
                OPTIONAL_EMPTY = empty.invoke(null, new Object[0]);
            } catch (Throwable th) {
                OPTIONAL_ERROR = true;
            }
        }
        return OPTIONAL_EMPTY;
    }

    public static class MethodInheritanceComparator implements Comparator<Method> {
        @Override // java.util.Comparator
        public int compare(Method m1, Method m2) {
            int cmp = m1.getName().compareTo(m2.getName());
            if (cmp != 0) {
                return cmp;
            }
            Class<?> class1 = m1.getReturnType();
            Class<?> class2 = m2.getReturnType();
            if (class1.equals(class2)) {
                return 0;
            }
            if (class1.isAssignableFrom(class2)) {
                return -1;
            }
            return class2.isAssignableFrom(class1) ? 1 : 0;
        }
    }
}