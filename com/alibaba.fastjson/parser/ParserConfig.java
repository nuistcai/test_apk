package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ClassReader;
import com.alibaba.fastjson.asm.TypeCollector;
import com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory;
import com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.EnumCreatorDeserializer;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JSONPDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;
import com.alibaba.fastjson.parser.deserializer.NumberDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessable;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessableDeserializer;
import com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.StackTraceElementDeserializer;
import com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer;
import com.alibaba.fastjson.parser.deserializer.TimeDeserializer;
import com.alibaba.fastjson.serializer.AtomicCodec;
import com.alibaba.fastjson.serializer.AwtCodec;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.BigIntegerCodec;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.alibaba.fastjson.serializer.ByteBufferCodec;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.CharArrayCodec;
import com.alibaba.fastjson.serializer.CharacterCodec;
import com.alibaba.fastjson.serializer.CollectionCodec;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.FloatCodec;
import com.alibaba.fastjson.serializer.GuavaCodec;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.JodaCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.MiscCodec;
import com.alibaba.fastjson.serializer.ObjectArrayCodec;
import com.alibaba.fastjson.serializer.ReferenceCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.spi.Module;
import com.alibaba.fastjson.support.moneta.MonetaCodec;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.Function;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.ModuleUtil;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.alibaba.fastjson.util.ServiceLoader;
import com.alibaba.fastjson.util.TypeUtils;
import com.tencent.bugly.Bugly;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.AccessControlException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.xml.datatype.XMLGregorianCalendar;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class ParserConfig {
    public static final String AUTOTYPE_ACCEPT = "fastjson.parser.autoTypeAccept";
    public static final String AUTOTYPE_SUPPORT_PROPERTY = "fastjson.parser.autoTypeSupport";
    public static final boolean AUTO_SUPPORT;
    private static final String[] AUTO_TYPE_ACCEPT_LIST;
    public static final String[] DENYS;
    public static final String[] DENYS_INTERNAL;
    public static final String DENY_PROPERTY = "fastjson.parser.deny";
    public static final String DENY_PROPERTY_INTERNAL = "fastjson.parser.deny.internal";
    private static final long[] INTERNAL_WHITELIST_HASHCODES;
    public static final boolean SAFE_MODE;
    public static final String SAFE_MODE_PROPERTY = "fastjson.parser.safeMode";
    private static boolean awtError;
    public static ParserConfig global;
    private static boolean guavaError;
    private static Function<Class<?>, Boolean> isPrimitiveFuncation;
    private static boolean jdk8Error;
    private static boolean jodaError;
    private long[] acceptHashCodes;
    private boolean asmEnable;
    protected ASMDeserializerFactory asmFactory;
    private volatile List<AutoTypeCheckHandler> autoTypeCheckHandlers;
    private boolean autoTypeSupport;
    public boolean compatibleWithJavaBean;
    protected ClassLoader defaultClassLoader;
    private long[] denyHashCodes;
    private final IdentityHashMap<Type, ObjectDeserializer> deserializers;
    public final boolean fieldBased;
    private final Callable<Void> initDeserializersWithJavaSql;
    private long[] internalDenyHashCodes;
    private boolean jacksonCompatible;
    private final IdentityHashMap<Type, IdentityHashMap<Type, ObjectDeserializer>> mixInDeserializers;
    private List<Module> modules;
    public PropertyNamingStrategy propertyNamingStrategy;
    private boolean safeMode;
    public final SymbolTable symbolTable;
    private final ConcurrentMap<String, Class<?>> typeMapping;

    public interface AutoTypeCheckHandler {
        Class<?> handler(String str, Class<?> cls, int i);
    }

    static {
        String property = IOUtils.getStringProperty(DENY_PROPERTY_INTERNAL);
        DENYS_INTERNAL = splitItemsFormProperty(property);
        String property2 = IOUtils.getStringProperty(DENY_PROPERTY);
        DENYS = splitItemsFormProperty(property2);
        String property3 = IOUtils.getStringProperty(AUTOTYPE_SUPPORT_PROPERTY);
        AUTO_SUPPORT = "true".equals(property3);
        String property4 = IOUtils.getStringProperty(SAFE_MODE_PROPERTY);
        SAFE_MODE = "true".equals(property4);
        String property5 = IOUtils.getStringProperty(AUTOTYPE_ACCEPT);
        String[] items = splitItemsFormProperty(property5);
        if (items == null) {
            items = new String[0];
        }
        AUTO_TYPE_ACCEPT_LIST = items;
        INTERNAL_WHITELIST_HASHCODES = new long[]{-6976602508726000783L, -6293031534589903644L, 59775428743665658L, 7267793227937552092L};
        global = new ParserConfig();
        awtError = false;
        jdk8Error = false;
        jodaError = false;
        guavaError = false;
        isPrimitiveFuncation = new Function<Class<?>, Boolean>() { // from class: com.alibaba.fastjson.parser.ParserConfig.2
            @Override // com.alibaba.fastjson.util.Function
            public Boolean apply(Class<?> clazz) {
                return Boolean.valueOf(clazz == Date.class || clazz == Time.class || clazz == Timestamp.class);
            }
        };
    }

    public static ParserConfig getGlobalInstance() {
        return global;
    }

    public ParserConfig() {
        this(false);
    }

    public ParserConfig(boolean fieldBase) {
        this(null, null, fieldBase);
    }

    public ParserConfig(ClassLoader parentClassLoader) {
        this(null, parentClassLoader, false);
    }

    public ParserConfig(ASMDeserializerFactory asmFactory) {
        this(asmFactory, null, false);
    }

    private ParserConfig(ASMDeserializerFactory asmFactory, ClassLoader parentClassLoader, boolean fieldBased) {
        this.deserializers = new IdentityHashMap<>();
        this.mixInDeserializers = new IdentityHashMap<>(16);
        this.typeMapping = new ConcurrentHashMap(16, 0.75f, 1);
        this.asmEnable = !ASMUtils.IS_ANDROID;
        this.symbolTable = new SymbolTable(4096);
        this.autoTypeSupport = AUTO_SUPPORT;
        this.jacksonCompatible = false;
        this.compatibleWithJavaBean = TypeUtils.compatibleWithJavaBean;
        this.modules = new ArrayList();
        this.safeMode = SAFE_MODE;
        this.denyHashCodes = new long[]{-9164606388214699518L, -8754006975464705441L, -8720046426850100497L, -8649961213709896794L, -8614556368991373401L, -8382625455832334425L, -8165637398350707645L, -8109300701639721088L, -7966123100503199569L, -7921218830998286408L, -7775351613326101303L, -7768608037458185275L, -7766605818834748097L, -6835437086156813536L, -6316154655839304624L, -6179589609550493385L, -6149130139291498841L, -6149093380703242441L, -6088208984980396913L, -6025144546313590215L, -5939269048541779808L, -5885964883385605994L, -5767141746063564198L, -5764804792063216819L, -5472097725414717105L, -5194641081268104286L, -5076846148177416215L, -4837536971810737970L, -4836620931940850535L, -4733542790109620528L, -4703320437989596122L, -4608341446948126581L, -4537258998789938600L, -4438775680185074100L, -4314457471973557243L, -4150995715611818742L, -4082057040235125754L, -3975378478825053783L, -3967588558552655563L, -3935185854875733362L, TypeUtils.fnv1a_64_magic_hashcode, -3319207949486691020L, -3077205613010077203L, -3053747177772160511L, -2995060141064716555L, -2825378362173150292L, -2533039401923731906L, -2439930098895578154L, -2378990704010641148L, -2364987994247679115L, -2262244760619952081L, -2192804397019347313L, -2095516571388852610L, -1872417015366588117L, -1800035667138631116L, -1650485814983027158L, -1589194880214235129L, -1363634950764737555L, -965955008570215305L, -905177026366752536L, -831789045734283466L, -803541446955902575L, -731978084025273882L, -666475508176557463L, -582813228520337988L, -254670111376247151L, -219577392946377768L, -190281065685395680L, -26639035867733124L, -9822483067882491L, 4750336058574309L, 33238344207745342L, 156405680656087946L, 218512992947536312L, 313864100207897507L, 386461436234701831L, 744602970950881621L, 823641066473609950L, 860052378298585747L, 1073634739308289776L, 1153291637701043748L, 1203232727967308606L, 1214780596910349029L, 1268707909007641340L, 1459860845934817624L, 1502845958873959152L, 1534439610567445754L, 1698504441317515818L, 1818089308493370394L, 2078113382421334967L, 2164696723069287854L, 2622551729063269307L, 2653453629929770569L, 2660670623866180977L, 2731823439467737506L, 2836431254737891113L, 2930861374593775110L, 3058452313624178956L, 3085473968517218653L, 3089451460101527857L, 3114862868117605599L, 3129395579983849527L, 3256258368248066264L, 3452379460455804429L, 3547627781654598988L, 3637939656440441093L, 3688179072722109200L, 3718352661124136681L, 3730752432285826863L, 3740226159580918099L, 3794316665763266033L, 3977090344859527316L, 4000049462512838776L, 4046190361520671643L, 4147696707147271408L, 4193204392725694463L, 4215053018660518963L, 4241163808635564644L, 4254584350247334433L, 4319304524795015394L, 4814658433570175913L, 4841947709850912914L, 4904007817188630457L, 5100336081510080343L, 5120543992130540564L, 5274044858141538265L, 5347909877633654828L, 5450448828334921485L, 5474268165959054640L, 5545425291794704408L, 5596129856135573697L, 5688200883751798389L, 5751393439502795295L, 5916409771425455946L, 5944107969236155580L, 6007332606592876737L, 6090377589998869205L, 6280357960959217660L, 6456855723474196908L, 6511035576063254270L, 6534946468240507089L, 6584624952928234050L, 6734240326434096246L, 6742705432718011780L, 6800727078373023163L, 6854854816081053523L, 7045245923763966215L, 7123326897294507060L, 7164889056054194741L, 7179336928365889465L, 7240293012336844478L, 7347653049056829645L, 7375862386996623731L, 7442624256860549330L, 7617522210483516279L, 7658177784286215602L, 8055461369741094911L, 8064026652676081192L, 8389032537095247355L, 8409640769019589119L, 8488266005336625107L, 8537233257283452655L, 8711531061028787095L, 8735538376409180149L, 8838294710098435315L, 8861402923078831179L, 9140390920032557669L, 9140416208800006522L, 9144212112462101475L};
        long[] hashCodes = new long[AUTO_TYPE_ACCEPT_LIST.length];
        for (int i = 0; i < AUTO_TYPE_ACCEPT_LIST.length; i++) {
            hashCodes[i] = TypeUtils.fnv1a_64(AUTO_TYPE_ACCEPT_LIST[i]);
        }
        Arrays.sort(hashCodes);
        this.acceptHashCodes = hashCodes;
        this.initDeserializersWithJavaSql = new Callable<Void>() { // from class: com.alibaba.fastjson.parser.ParserConfig.1
            @Override // java.util.concurrent.Callable
            public Void call() {
                ParserConfig.this.deserializers.put(Timestamp.class, SqlDateDeserializer.instance_timestamp);
                ParserConfig.this.deserializers.put(Date.class, SqlDateDeserializer.instance);
                ParserConfig.this.deserializers.put(Time.class, TimeDeserializer.instance);
                ParserConfig.this.deserializers.put(java.util.Date.class, DateCodec.instance);
                return null;
            }
        };
        this.fieldBased = fieldBased;
        if (asmFactory == null && !ASMUtils.IS_ANDROID) {
            try {
                if (parentClassLoader == null) {
                    asmFactory = new ASMDeserializerFactory(new ASMClassLoader());
                } else {
                    asmFactory = new ASMDeserializerFactory(parentClassLoader);
                }
            } catch (ExceptionInInitializerError e) {
            } catch (NoClassDefFoundError e2) {
            } catch (AccessControlException e3) {
            }
        }
        this.asmFactory = asmFactory;
        if (asmFactory == null) {
            this.asmEnable = false;
        }
        initDeserializers();
        addItemsToDeny(DENYS);
        addItemsToDeny0(DENYS_INTERNAL);
        addItemsToAccept(AUTO_TYPE_ACCEPT_LIST);
    }

    private void initDeserializers() {
        this.deserializers.put(SimpleDateFormat.class, MiscCodec.instance);
        this.deserializers.put(Calendar.class, CalendarCodec.instance);
        this.deserializers.put(XMLGregorianCalendar.class, CalendarCodec.instance);
        this.deserializers.put(JSONObject.class, MapDeserializer.instance);
        this.deserializers.put(JSONArray.class, CollectionCodec.instance);
        this.deserializers.put(Map.class, MapDeserializer.instance);
        this.deserializers.put(HashMap.class, MapDeserializer.instance);
        this.deserializers.put(LinkedHashMap.class, MapDeserializer.instance);
        this.deserializers.put(TreeMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentHashMap.class, MapDeserializer.instance);
        this.deserializers.put(Collection.class, CollectionCodec.instance);
        this.deserializers.put(List.class, CollectionCodec.instance);
        this.deserializers.put(ArrayList.class, CollectionCodec.instance);
        this.deserializers.put(Object.class, JavaObjectDeserializer.instance);
        this.deserializers.put(String.class, StringCodec.instance);
        this.deserializers.put(StringBuffer.class, StringCodec.instance);
        this.deserializers.put(StringBuilder.class, StringCodec.instance);
        this.deserializers.put(Character.TYPE, CharacterCodec.instance);
        this.deserializers.put(Character.class, CharacterCodec.instance);
        this.deserializers.put(Byte.TYPE, NumberDeserializer.instance);
        this.deserializers.put(Byte.class, NumberDeserializer.instance);
        this.deserializers.put(Short.TYPE, NumberDeserializer.instance);
        this.deserializers.put(Short.class, NumberDeserializer.instance);
        this.deserializers.put(Integer.TYPE, IntegerCodec.instance);
        this.deserializers.put(Integer.class, IntegerCodec.instance);
        this.deserializers.put(Long.TYPE, LongCodec.instance);
        this.deserializers.put(Long.class, LongCodec.instance);
        this.deserializers.put(BigInteger.class, BigIntegerCodec.instance);
        this.deserializers.put(BigDecimal.class, BigDecimalCodec.instance);
        this.deserializers.put(Float.TYPE, FloatCodec.instance);
        this.deserializers.put(Float.class, FloatCodec.instance);
        this.deserializers.put(Double.TYPE, NumberDeserializer.instance);
        this.deserializers.put(Double.class, NumberDeserializer.instance);
        this.deserializers.put(Boolean.TYPE, BooleanCodec.instance);
        this.deserializers.put(Boolean.class, BooleanCodec.instance);
        this.deserializers.put(Class.class, MiscCodec.instance);
        this.deserializers.put(char[].class, new CharArrayCodec());
        this.deserializers.put(AtomicBoolean.class, BooleanCodec.instance);
        this.deserializers.put(AtomicInteger.class, IntegerCodec.instance);
        this.deserializers.put(AtomicLong.class, LongCodec.instance);
        this.deserializers.put(AtomicReference.class, ReferenceCodec.instance);
        this.deserializers.put(WeakReference.class, ReferenceCodec.instance);
        this.deserializers.put(SoftReference.class, ReferenceCodec.instance);
        this.deserializers.put(UUID.class, MiscCodec.instance);
        this.deserializers.put(TimeZone.class, MiscCodec.instance);
        this.deserializers.put(Locale.class, MiscCodec.instance);
        this.deserializers.put(Currency.class, MiscCodec.instance);
        this.deserializers.put(Inet4Address.class, MiscCodec.instance);
        this.deserializers.put(Inet6Address.class, MiscCodec.instance);
        this.deserializers.put(InetSocketAddress.class, MiscCodec.instance);
        this.deserializers.put(File.class, MiscCodec.instance);
        this.deserializers.put(URI.class, MiscCodec.instance);
        this.deserializers.put(URL.class, MiscCodec.instance);
        this.deserializers.put(Pattern.class, MiscCodec.instance);
        this.deserializers.put(Charset.class, MiscCodec.instance);
        this.deserializers.put(JSONPath.class, MiscCodec.instance);
        this.deserializers.put(Number.class, NumberDeserializer.instance);
        this.deserializers.put(AtomicIntegerArray.class, AtomicCodec.instance);
        this.deserializers.put(AtomicLongArray.class, AtomicCodec.instance);
        this.deserializers.put(StackTraceElement.class, StackTraceElementDeserializer.instance);
        this.deserializers.put(Serializable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(Cloneable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(Comparable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(Closeable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(JSONPObject.class, new JSONPDeserializer());
        ModuleUtil.callWhenHasJavaSql(this.initDeserializersWithJavaSql);
    }

    private static String[] splitItemsFormProperty(String property) {
        if (property != null && property.length() > 0) {
            return property.split(",");
        }
        return null;
    }

    public void configFromPropety(Properties properties) {
        String property = properties.getProperty(DENY_PROPERTY);
        String[] items = splitItemsFormProperty(property);
        addItemsToDeny(items);
        String property2 = properties.getProperty(AUTOTYPE_ACCEPT);
        String[] items2 = splitItemsFormProperty(property2);
        addItemsToAccept(items2);
        String property3 = properties.getProperty(AUTOTYPE_SUPPORT_PROPERTY);
        if ("true".equals(property3)) {
            this.autoTypeSupport = true;
        } else if (Bugly.SDK_IS_DEV.equals(property3)) {
            this.autoTypeSupport = false;
        }
    }

    private void addItemsToDeny0(String[] items) {
        if (items == null) {
            return;
        }
        for (String item : items) {
            addDenyInternal(item);
        }
    }

    private void addItemsToDeny(String[] items) {
        if (items == null) {
            return;
        }
        for (String item : items) {
            addDeny(item);
        }
    }

    private void addItemsToAccept(String[] items) {
        if (items == null) {
            return;
        }
        for (String item : items) {
            addAccept(item);
        }
    }

    public boolean isSafeMode() {
        return this.safeMode;
    }

    public void setSafeMode(boolean safeMode) {
        this.safeMode = safeMode;
    }

    public boolean isAutoTypeSupport() {
        return this.autoTypeSupport;
    }

    public void setAutoTypeSupport(boolean autoTypeSupport) {
        this.autoTypeSupport = autoTypeSupport;
    }

    public boolean isAsmEnable() {
        return this.asmEnable;
    }

    public void setAsmEnable(boolean asmEnable) {
        this.asmEnable = asmEnable;
    }

    public IdentityHashMap<Type, ObjectDeserializer> getDerializers() {
        return this.deserializers;
    }

    public IdentityHashMap<Type, ObjectDeserializer> getDeserializers() {
        return this.deserializers;
    }

    public ObjectDeserializer getDeserializer(Type type) {
        ObjectDeserializer deserializer = get(type);
        if (deserializer != null) {
            return deserializer;
        }
        if (type instanceof Class) {
            return getDeserializer((Class) type, type);
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                return getDeserializer((Class) rawType, type);
            }
            return getDeserializer(rawType);
        }
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            Type[] upperBounds = wildcardType.getUpperBounds();
            if (upperBounds.length == 1) {
                Type upperBoundType = upperBounds[0];
                return getDeserializer(upperBoundType);
            }
        }
        return JavaObjectDeserializer.instance;
    }

    public ObjectDeserializer getDeserializer(Class<?> clazz, Type type) {
        Type type2;
        ObjectDeserializer deserializer;
        Class<?> mappingTo;
        ObjectDeserializer deserializer2 = get(type);
        if (deserializer2 == null && (type instanceof ParameterizedTypeImpl)) {
            Type innerType = TypeReference.intern((ParameterizedTypeImpl) type);
            deserializer2 = get(innerType);
        }
        if (deserializer2 != null) {
            return deserializer2;
        }
        if (type != null) {
            type2 = type;
        } else {
            type2 = clazz;
        }
        ObjectDeserializer deserializer3 = get(type2);
        if (deserializer3 != null) {
            return deserializer3;
        }
        JSONType annotation = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
        if (annotation != null && (mappingTo = annotation.mappingTo()) != Void.class) {
            return getDeserializer(mappingTo, mappingTo);
        }
        if ((type2 instanceof WildcardType) || (type2 instanceof TypeVariable) || (type2 instanceof ParameterizedType)) {
            deserializer3 = get(clazz);
        }
        if (deserializer3 != null) {
            return deserializer3;
        }
        ObjectDeserializer deserializer4 = deserializer3;
        for (Module module : this.modules) {
            deserializer4 = module.createDeserializer(this, clazz);
            if (deserializer4 != null) {
                putDeserializer(type2, deserializer4);
                return deserializer4;
            }
        }
        String className = clazz.getName().replace(Typography.dollar, '.');
        if (className.startsWith("java.awt.") && AwtCodec.support(clazz) && !awtError) {
            String[] names = {"java.awt.Point", "java.awt.Font", "java.awt.Rectangle", "java.awt.Color"};
            try {
                for (String name : names) {
                    if (name.equals(className)) {
                        Type cls = Class.forName(name);
                        ObjectDeserializer deserializer5 = AwtCodec.instance;
                        putDeserializer(cls, deserializer5);
                        return deserializer5;
                    }
                }
            } catch (Throwable th) {
                awtError = true;
            }
            deserializer4 = AwtCodec.instance;
        }
        if (!jdk8Error) {
            try {
                if (className.startsWith("java.time.")) {
                    String[] names2 = {"java.time.LocalDateTime", "java.time.LocalDate", "java.time.LocalTime", "java.time.ZonedDateTime", "java.time.OffsetDateTime", "java.time.OffsetTime", "java.time.ZoneOffset", "java.time.ZoneRegion", "java.time.ZoneId", "java.time.Period", "java.time.Duration", "java.time.Instant"};
                    for (String name2 : names2) {
                        if (name2.equals(className)) {
                            Type cls2 = Class.forName(name2);
                            ObjectDeserializer deserializer6 = Jdk8DateCodec.instance;
                            putDeserializer(cls2, deserializer6);
                            return deserializer6;
                        }
                    }
                } else if (className.startsWith("java.util.Optional")) {
                    String[] names3 = {"java.util.Optional", "java.util.OptionalDouble", "java.util.OptionalInt", "java.util.OptionalLong"};
                    for (String name3 : names3) {
                        if (name3.equals(className)) {
                            Type cls3 = Class.forName(name3);
                            ObjectDeserializer deserializer7 = OptionalCodec.instance;
                            putDeserializer(cls3, deserializer7);
                            return deserializer7;
                        }
                    }
                }
            } catch (Throwable th2) {
                jdk8Error = true;
            }
        }
        if (!jodaError) {
            try {
                if (className.startsWith("org.joda.time.")) {
                    String[] names4 = {"org.joda.time.DateTime", "org.joda.time.LocalDate", "org.joda.time.LocalDateTime", "org.joda.time.LocalTime", "org.joda.time.Instant", "org.joda.time.Period", "org.joda.time.Duration", "org.joda.time.DateTimeZone", "org.joda.time.format.DateTimeFormatter"};
                    for (String name4 : names4) {
                        if (name4.equals(className)) {
                            Type cls4 = Class.forName(name4);
                            ObjectDeserializer objectDeserializer = JodaCodec.instance;
                            deserializer4 = objectDeserializer;
                            putDeserializer(cls4, objectDeserializer);
                            return deserializer4;
                        }
                    }
                }
            } catch (Throwable th3) {
                jodaError = true;
            }
        }
        if (!guavaError && className.startsWith("com.google.common.collect.")) {
            try {
                String[] names5 = {"com.google.common.collect.HashMultimap", "com.google.common.collect.LinkedListMultimap", "com.google.common.collect.LinkedHashMultimap", "com.google.common.collect.ArrayListMultimap", "com.google.common.collect.TreeMultimap"};
                for (String name5 : names5) {
                    if (name5.equals(className)) {
                        Type cls5 = Class.forName(name5);
                        ObjectDeserializer objectDeserializer2 = GuavaCodec.instance;
                        deserializer4 = objectDeserializer2;
                        putDeserializer(cls5, objectDeserializer2);
                        return deserializer4;
                    }
                }
            } catch (ClassNotFoundException e) {
                guavaError = true;
            }
        }
        if (className.equals("java.nio.ByteBuffer")) {
            ObjectDeserializer objectDeserializer3 = ByteBufferCodec.instance;
            deserializer4 = objectDeserializer3;
            putDeserializer(clazz, objectDeserializer3);
        }
        if (className.equals("java.nio.file.Path")) {
            ObjectDeserializer objectDeserializer4 = MiscCodec.instance;
            deserializer4 = objectDeserializer4;
            putDeserializer(clazz, objectDeserializer4);
        }
        if (clazz == Map.Entry.class) {
            ObjectDeserializer objectDeserializer5 = MiscCodec.instance;
            deserializer4 = objectDeserializer5;
            putDeserializer(clazz, objectDeserializer5);
        }
        if (className.equals("org.javamoney.moneta.Money")) {
            ObjectDeserializer objectDeserializer6 = MonetaCodec.instance;
            deserializer4 = objectDeserializer6;
            putDeserializer(clazz, objectDeserializer6);
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            for (AutowiredObjectDeserializer autowired : ServiceLoader.load(AutowiredObjectDeserializer.class, classLoader)) {
                for (Type forType : autowired.getAutowiredFor()) {
                    putDeserializer(forType, autowired);
                }
            }
        } catch (Exception e2) {
        }
        if (deserializer4 == null) {
            deserializer4 = get(type2);
        }
        if (deserializer4 != null) {
            return deserializer4;
        }
        if (clazz.isEnum()) {
            if (this.jacksonCompatible) {
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (TypeUtils.isJacksonCreator(method)) {
                        ObjectDeserializer deserializer8 = createJavaBeanDeserializer(clazz, type2);
                        putDeserializer(type2, deserializer8);
                        return deserializer8;
                    }
                }
            }
            Class mixInType = (Class) JSON.getMixInAnnotations(clazz);
            JSONType jsonType = (JSONType) TypeUtils.getAnnotation((Class<?>) (mixInType != null ? mixInType : clazz), JSONType.class);
            if (jsonType != null) {
                Class<?> deserClass = jsonType.deserializer();
                try {
                    ObjectDeserializer deserializer9 = (ObjectDeserializer) deserClass.newInstance();
                    putDeserializer(clazz, deserializer9);
                    return deserializer9;
                } catch (Throwable th4) {
                }
            }
            Method jsonCreatorMethod = null;
            if (mixInType != null) {
                Method mixedCreator = getEnumCreator(mixInType, clazz);
                if (mixedCreator != null) {
                    try {
                        jsonCreatorMethod = clazz.getMethod(mixedCreator.getName(), mixedCreator.getParameterTypes());
                    } catch (Exception e3) {
                    }
                }
            } else {
                jsonCreatorMethod = getEnumCreator(clazz, clazz);
            }
            if (jsonCreatorMethod != null) {
                ObjectDeserializer deserializer10 = new EnumCreatorDeserializer(jsonCreatorMethod);
                putDeserializer(clazz, deserializer10);
                return deserializer10;
            }
            deserializer = getEnumDeserializer(clazz);
        } else if (clazz.isArray()) {
            deserializer = ObjectArrayCodec.instance;
        } else if (clazz == Set.class || clazz == HashSet.class || clazz == Collection.class || clazz == List.class || clazz == ArrayList.class || Collection.class.isAssignableFrom(clazz)) {
            deserializer = CollectionCodec.instance;
        } else if (Map.class.isAssignableFrom(clazz)) {
            deserializer = MapDeserializer.instance;
        } else if (Throwable.class.isAssignableFrom(clazz)) {
            deserializer = new ThrowableDeserializer(this, clazz);
        } else if (PropertyProcessable.class.isAssignableFrom(clazz)) {
            deserializer = new PropertyProcessableDeserializer(clazz);
        } else if (clazz == InetAddress.class) {
            deserializer = MiscCodec.instance;
        } else {
            deserializer = createJavaBeanDeserializer(clazz, type2);
        }
        putDeserializer(type2, deserializer);
        return deserializer;
    }

    private static Method getEnumCreator(Class clazz, Class enumClass) throws SecurityException {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers()) && method.getReturnType() == enumClass && method.getParameterTypes().length == 1) {
                JSONCreator jsonCreator = (JSONCreator) method.getAnnotation(JSONCreator.class);
                if (jsonCreator != null) {
                    return method;
                }
            }
        }
        return null;
    }

    protected ObjectDeserializer getEnumDeserializer(Class<?> clazz) {
        return new EnumDeserializer(clazz);
    }

    public void initJavaBeanDeserializers(Class<?>... classes) {
        if (classes == null) {
            return;
        }
        for (Class<?> type : classes) {
            if (type != null) {
                ObjectDeserializer deserializer = createJavaBeanDeserializer(type, type);
                putDeserializer(type, deserializer);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:91:0x012c, code lost:
    
        r0 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ObjectDeserializer createJavaBeanDeserializer(Class<?> clazz, Type type) {
        boolean asmEnable = this.asmEnable & (!this.fieldBased);
        int i = 0;
        if (asmEnable) {
            JSONType jsonType = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
            if (jsonType != null) {
                Class<?> deserializerClass = jsonType.deserializer();
                if (deserializerClass != Void.class) {
                    try {
                        Object deseralizer = deserializerClass.newInstance();
                        if (deseralizer instanceof ObjectDeserializer) {
                            return (ObjectDeserializer) deseralizer;
                        }
                    } catch (Throwable th) {
                    }
                }
                asmEnable = jsonType.asm() && jsonType.parseFeatures().length == 0;
            }
            if (asmEnable) {
                Class<?> superClass = JavaBeanInfo.getBuilderClass(clazz, jsonType);
                if (superClass == null) {
                    superClass = clazz;
                }
                while (true) {
                    if (!Modifier.isPublic(superClass.getModifiers())) {
                        asmEnable = false;
                        break;
                    }
                    superClass = superClass.getSuperclass();
                    if (superClass == Object.class || superClass == null) {
                        break;
                    }
                }
            }
        }
        if (clazz.getTypeParameters().length != 0) {
            asmEnable = false;
        }
        if (asmEnable && this.asmFactory != null && this.asmFactory.classLoader.isExternalClass(clazz)) {
            asmEnable = false;
        }
        if (asmEnable) {
            asmEnable = ASMUtils.checkName(clazz.getSimpleName());
        }
        if (asmEnable) {
            if (clazz.isInterface()) {
                asmEnable = false;
            }
            JavaBeanInfo beanInfo = JavaBeanInfo.build(clazz, type, this.propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean, this.jacksonCompatible);
            if (asmEnable && beanInfo.fields.length > 200) {
                asmEnable = false;
            }
            Constructor<?> defaultConstructor = beanInfo.defaultConstructor;
            if (asmEnable && defaultConstructor == null && !clazz.isInterface()) {
                asmEnable = false;
            }
            FieldInfo[] fieldInfoArr = beanInfo.fields;
            int length = fieldInfoArr.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                FieldInfo fieldInfo = fieldInfoArr[i];
                if (fieldInfo.getOnly) {
                    asmEnable = false;
                    break;
                }
                Class<?> fieldClass = fieldInfo.fieldClass;
                if (!Modifier.isPublic(fieldClass.getModifiers())) {
                    asmEnable = false;
                    break;
                }
                if (fieldClass.isMemberClass() && !Modifier.isStatic(fieldClass.getModifiers())) {
                    asmEnable = false;
                    break;
                }
                if (fieldInfo.getMember() != null && !ASMUtils.checkName(fieldInfo.getMember().getName())) {
                    asmEnable = false;
                    break;
                }
                JSONField annotation = fieldInfo.getAnnotation();
                if ((annotation != null && (!ASMUtils.checkName(annotation.name()) || annotation.format().length() != 0 || annotation.deserializeUsing() != Void.class || annotation.parseFeatures().length != 0 || annotation.unwrapped())) || (fieldInfo.method != null && fieldInfo.method.getParameterTypes().length > 1)) {
                    break;
                }
                if (fieldClass.isEnum()) {
                    ObjectDeserializer fieldDeser = getDeserializer(fieldClass);
                    if (!(fieldDeser instanceof EnumDeserializer)) {
                        asmEnable = false;
                        break;
                    }
                }
                i++;
            }
        }
        if (asmEnable && clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
            asmEnable = false;
        }
        if (asmEnable && TypeUtils.isXmlField(clazz)) {
            asmEnable = false;
        }
        if (!asmEnable) {
            return new JavaBeanDeserializer(this, clazz, type);
        }
        JavaBeanInfo beanInfo2 = JavaBeanInfo.build(clazz, type, this.propertyNamingStrategy);
        try {
            return this.asmFactory.createJavaBeanDeserializer(this, beanInfo2);
        } catch (JSONException e) {
            return new JavaBeanDeserializer(this, beanInfo2);
        } catch (NoSuchMethodException e2) {
            return new JavaBeanDeserializer(this, clazz, type);
        } catch (Exception e3) {
            throw new JSONException("create asm deserializer error, " + clazz.getName(), e3);
        }
    }

    public FieldDeserializer createFieldDeserializer(ParserConfig mapping, JavaBeanInfo beanInfo, FieldInfo fieldInfo) {
        Class<?> clazz = beanInfo.clazz;
        Class<?> fieldClass = fieldInfo.fieldClass;
        Class<?> deserializeUsing = null;
        JSONField annotation = fieldInfo.getAnnotation();
        if (annotation != null && (deserializeUsing = annotation.deserializeUsing()) == Void.class) {
            deserializeUsing = null;
        }
        if (deserializeUsing == null && (fieldClass == List.class || fieldClass == ArrayList.class)) {
            return new ArrayListTypeFieldDeserializer(mapping, clazz, fieldInfo);
        }
        return new DefaultFieldDeserializer(mapping, clazz, fieldInfo);
    }

    public void putDeserializer(Type type, ObjectDeserializer deserializer) {
        Type mixin = JSON.getMixInAnnotations(type);
        if (mixin != null) {
            IdentityHashMap<Type, ObjectDeserializer> mixInClasses = this.mixInDeserializers.get(type);
            if (mixInClasses == null) {
                mixInClasses = new IdentityHashMap<>(4);
                this.mixInDeserializers.put(type, mixInClasses);
            }
            mixInClasses.put(mixin, deserializer);
            return;
        }
        this.deserializers.put(type, deserializer);
    }

    public ObjectDeserializer get(Type type) {
        Type mixin = JSON.getMixInAnnotations(type);
        if (mixin == null) {
            return this.deserializers.get(type);
        }
        IdentityHashMap<Type, ObjectDeserializer> mixInClasses = this.mixInDeserializers.get(type);
        if (mixInClasses == null) {
            return null;
        }
        return mixInClasses.get(mixin);
    }

    public ObjectDeserializer getDeserializer(FieldInfo fieldInfo) {
        return getDeserializer(fieldInfo.fieldClass, fieldInfo.fieldType);
    }

    public boolean isPrimitive(Class<?> clazz) {
        return isPrimitive2(clazz);
    }

    public static boolean isPrimitive2(Class<?> clazz) {
        Boolean primitive = Boolean.valueOf(clazz.isPrimitive() || clazz == Boolean.class || clazz == Character.class || clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class || clazz == BigInteger.class || clazz == BigDecimal.class || clazz == String.class || clazz == java.util.Date.class || clazz.isEnum());
        if (!primitive.booleanValue()) {
            primitive = (Boolean) ModuleUtil.callWhenHasJavaSql(isPrimitiveFuncation, clazz);
        }
        if (primitive != null) {
            return primitive.booleanValue();
        }
        return false;
    }

    public static void parserAllFieldToCache(Class<?> clazz, Map<String, Field> fieldCacheMap) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!fieldCacheMap.containsKey(fieldName)) {
                fieldCacheMap.put(fieldName, field);
            }
        }
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            parserAllFieldToCache(clazz.getSuperclass(), fieldCacheMap);
        }
    }

    public static Field getFieldFromCache(String fieldName, Map<String, Field> fieldCacheMap) {
        Field field = fieldCacheMap.get(fieldName);
        if (field == null) {
            field = fieldCacheMap.get("_" + fieldName);
        }
        if (field == null) {
            field = fieldCacheMap.get("m_" + fieldName);
        }
        if (field == null) {
            char c0 = fieldName.charAt(0);
            if (c0 >= 'a' && c0 <= 'z') {
                char[] chars = fieldName.toCharArray();
                chars[0] = (char) (chars[0] - ' ');
                String fieldNameX = new String(chars);
                field = fieldCacheMap.get(fieldNameX);
            }
            if (fieldName.length() > 2) {
                char c1 = fieldName.charAt(1);
                if (c0 >= 'a' && c0 <= 'z' && c1 >= 'A' && c1 <= 'Z') {
                    for (Map.Entry<String, Field> entry : fieldCacheMap.entrySet()) {
                        if (fieldName.equalsIgnoreCase(entry.getKey())) {
                            return entry.getValue();
                        }
                    }
                    return field;
                }
                return field;
            }
            return field;
        }
        return field;
    }

    public ClassLoader getDefaultClassLoader() {
        return this.defaultClassLoader;
    }

    public void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        this.defaultClassLoader = defaultClassLoader;
    }

    public void addDenyInternal(String name) {
        if (name == null || name.length() == 0) {
            return;
        }
        long hash = TypeUtils.fnv1a_64(name);
        if (this.internalDenyHashCodes == null) {
            this.internalDenyHashCodes = new long[]{hash};
            return;
        }
        if (Arrays.binarySearch(this.internalDenyHashCodes, hash) >= 0) {
            return;
        }
        long[] hashCodes = new long[this.internalDenyHashCodes.length + 1];
        hashCodes[hashCodes.length - 1] = hash;
        System.arraycopy(this.internalDenyHashCodes, 0, hashCodes, 0, this.internalDenyHashCodes.length);
        Arrays.sort(hashCodes);
        this.internalDenyHashCodes = hashCodes;
    }

    public void addDeny(String name) {
        if (name == null || name.length() == 0) {
            return;
        }
        long hash = TypeUtils.fnv1a_64(name);
        if (Arrays.binarySearch(this.denyHashCodes, hash) >= 0) {
            return;
        }
        long[] hashCodes = new long[this.denyHashCodes.length + 1];
        hashCodes[hashCodes.length - 1] = hash;
        System.arraycopy(this.denyHashCodes, 0, hashCodes, 0, this.denyHashCodes.length);
        Arrays.sort(hashCodes);
        this.denyHashCodes = hashCodes;
    }

    public void addAccept(String name) {
        if (name == null || name.length() == 0) {
            return;
        }
        long hash = TypeUtils.fnv1a_64(name);
        if (Arrays.binarySearch(this.acceptHashCodes, hash) >= 0) {
            return;
        }
        long[] hashCodes = new long[this.acceptHashCodes.length + 1];
        hashCodes[hashCodes.length - 1] = hash;
        System.arraycopy(this.acceptHashCodes, 0, hashCodes, 0, this.acceptHashCodes.length);
        Arrays.sort(hashCodes);
        this.acceptHashCodes = hashCodes;
    }

    public Class<?> checkAutoType(Class type) {
        if (get(type) != null) {
            return type;
        }
        return checkAutoType(type.getName(), null, JSON.DEFAULT_PARSER_FEATURE);
    }

    public Class<?> checkAutoType(String typeName, Class<?> expectClass) {
        return checkAutoType(typeName, expectClass, JSON.DEFAULT_PARSER_FEATURE);
    }

    /* JADX WARN: Removed duplicated region for block: B:202:0x03b0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x03b8  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x03bc  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x047a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Class<?> checkAutoType(String typeName, Class<?> expectClass, int features) throws Throwable {
        boolean expectClassFlag;
        long fullHash;
        int mask;
        long fullHash2;
        String className;
        boolean jsonType;
        Class<?> clazz;
        boolean z;
        boolean jsonType2;
        Class<?> clazz2;
        long hash;
        long hash2;
        Class<?> clazz3;
        if (typeName == null) {
            return null;
        }
        if (this.autoTypeCheckHandlers != null) {
            for (AutoTypeCheckHandler h : this.autoTypeCheckHandlers) {
                Class<?> type = h.handler(typeName, expectClass, features);
                if (type != null) {
                    return type;
                }
            }
        }
        int safeModeMask = Feature.SafeMode.mask;
        boolean safeMode = (!this.safeMode && (features & safeModeMask) == 0 && (JSON.DEFAULT_PARSER_FEATURE & safeModeMask) == 0) ? false : true;
        if (safeMode) {
            throw new JSONException("safeMode not support autoType : " + typeName);
        }
        int mask2 = Feature.SupportAutoType.mask;
        boolean autoTypeSupport = (!this.autoTypeSupport && (features & mask2) == 0 && (JSON.DEFAULT_PARSER_FEATURE & mask2) == 0) ? false : true;
        if (typeName.length() >= 192 || typeName.length() < 3) {
            throw new JSONException("autoType is not support. " + typeName);
        }
        if (expectClass == null) {
            expectClassFlag = false;
        } else {
            long expectHash = TypeUtils.fnv1a_64(expectClass.getName());
            expectClassFlag = (expectHash == -8024746738719829346L || expectHash == 3247277300971823414L || expectHash == -5811778396720452501L || expectHash == -1368967840069965882L || expectHash == 2980334044947851925L || expectHash == 5183404141909004468L || expectHash == 7222019943667248779L || expectHash == -2027296626235911549L || expectHash == -2114196234051346931L || expectHash == -2939497380989775398L) ? false : true;
        }
        String className2 = typeName.replace(Typography.dollar, '.');
        long h1 = (className2.charAt(0) ^ TypeUtils.fnv1a_64_magic_hashcode) * TypeUtils.fnv1a_64_magic_prime;
        if (h1 == -5808493101479473382L) {
            throw new JSONException("autoType is not support. " + typeName);
        }
        if ((className2.charAt(className2.length() - 1) ^ h1) * TypeUtils.fnv1a_64_magic_prime == 655701488918567152L) {
            throw new JSONException("autoType is not support. " + typeName);
        }
        String className3 = className2;
        long h3 = (className3.charAt(2) ^ ((((className2.charAt(0) ^ TypeUtils.fnv1a_64_magic_hashcode) * TypeUtils.fnv1a_64_magic_prime) ^ className2.charAt(1)) * TypeUtils.fnv1a_64_magic_prime)) * TypeUtils.fnv1a_64_magic_prime;
        long fullHash3 = TypeUtils.fnv1a_64(className3);
        boolean internalWhite = Arrays.binarySearch(INTERNAL_WHITELIST_HASHCODES, fullHash3) >= 0;
        if (this.internalDenyHashCodes != null) {
            long hash3 = h3;
            int i = 3;
            while (i < className3.length()) {
                long fullHash4 = fullHash3;
                long hash4 = (hash3 ^ className3.charAt(i)) * TypeUtils.fnv1a_64_magic_prime;
                if (Arrays.binarySearch(this.internalDenyHashCodes, hash4) >= 0) {
                    throw new JSONException("autoType is not support. " + typeName);
                }
                i++;
                hash3 = hash4;
                fullHash3 = fullHash4;
            }
            fullHash = fullHash3;
        } else {
            fullHash = fullHash3;
        }
        if (internalWhite) {
            mask = mask2;
            fullHash2 = fullHash;
        } else if (autoTypeSupport || expectClassFlag) {
            long fullHash5 = h3;
            int i2 = 3;
            while (i2 < className3.length()) {
                int mask3 = mask2;
                long hash5 = fullHash5 ^ className3.charAt(i2);
                long hash6 = hash5 * TypeUtils.fnv1a_64_magic_prime;
                if (Arrays.binarySearch(this.acceptHashCodes, hash6) >= 0 && (clazz3 = TypeUtils.loadClass(typeName, this.defaultClassLoader, true)) != null) {
                    return clazz3;
                }
                if (Arrays.binarySearch(this.denyHashCodes, hash6) < 0 || TypeUtils.getClassFromMapping(typeName) != null) {
                    hash = hash6;
                    hash2 = fullHash;
                } else {
                    hash = hash6;
                    hash2 = fullHash;
                    if (Arrays.binarySearch(this.acceptHashCodes, hash2) < 0) {
                        throw new JSONException("autoType is not support. " + typeName);
                    }
                }
                i2++;
                fullHash = hash2;
                mask2 = mask3;
                fullHash5 = hash;
            }
            mask = mask2;
            fullHash2 = fullHash;
        } else {
            mask = mask2;
            fullHash2 = fullHash;
        }
        Class<?> clazz4 = TypeUtils.getClassFromMapping(typeName);
        if (clazz4 == null) {
            clazz4 = this.deserializers.findClass(typeName);
        }
        if (expectClass == null && clazz4 != null && Throwable.class.isAssignableFrom(clazz4) && !autoTypeSupport) {
            clazz4 = null;
        }
        if (clazz4 == null) {
            clazz4 = this.typeMapping.get(typeName);
        }
        Class<?> clazz5 = internalWhite ? TypeUtils.loadClass(typeName, this.defaultClassLoader, true) : clazz4;
        if (clazz5 != null) {
            if (expectClass == null || clazz5 == HashMap.class || clazz5 == LinkedHashMap.class || expectClass.isAssignableFrom(clazz5)) {
                return clazz5;
            }
            throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
        }
        if (!autoTypeSupport) {
            long hash7 = h3;
            int i3 = 3;
            while (true) {
                long h32 = h3;
                if (i3 >= className3.length()) {
                    className = className3;
                    break;
                }
                char c = className3.charAt(i3);
                String className4 = className3;
                int mask4 = mask;
                long hash8 = (hash7 ^ c) * TypeUtils.fnv1a_64_magic_prime;
                if (Arrays.binarySearch(this.denyHashCodes, hash8) >= 0) {
                    if (typeName.endsWith("Exception") || typeName.endsWith("Error")) {
                        return null;
                    }
                    throw new JSONException("autoType is not support. " + typeName);
                }
                if (Arrays.binarySearch(this.acceptHashCodes, hash8) >= 0) {
                    Class<?> clazz6 = TypeUtils.loadClass(typeName, this.defaultClassLoader, true);
                    if (clazz6 == null) {
                        return expectClass;
                    }
                    if (expectClass == null || !expectClass.isAssignableFrom(clazz6)) {
                        return clazz6;
                    }
                    throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
                }
                i3++;
                hash7 = hash8;
                mask = mask4;
                className3 = className4;
                h3 = h32;
            }
        } else {
            className = className3;
        }
        InputStream is = null;
        try {
            jsonType = false;
            try {
                String resource = typeName.replace('.', '/') + ".class";
                if (this.defaultClassLoader != null) {
                    try {
                        is = this.defaultClassLoader.getResourceAsStream(resource);
                    } catch (Exception e) {
                        clazz = clazz5;
                        z = false;
                        IOUtils.close(is);
                        jsonType2 = jsonType;
                        if (autoTypeSupport) {
                        }
                        if (clazz2 != null) {
                        }
                        if (autoTypeSupport) {
                        }
                    } catch (Throwable th) {
                        th = th;
                        IOUtils.close(is);
                        throw th;
                    }
                } else {
                    is = ParserConfig.class.getClassLoader().getResourceAsStream(resource);
                }
                if (is != null) {
                    ClassReader classReader = new ClassReader(is, true);
                    clazz = clazz5;
                    z = false;
                    try {
                        TypeCollector visitor = new TypeCollector("<clinit>", new Class[0]);
                        classReader.accept(visitor);
                        jsonType2 = visitor.hasJsonType();
                    } catch (Exception e2) {
                        IOUtils.close(is);
                        jsonType2 = jsonType;
                        if (autoTypeSupport) {
                            if (!autoTypeSupport) {
                                z = true;
                                boolean cacheClass = z;
                                clazz2 = TypeUtils.loadClass(typeName, this.defaultClassLoader, cacheClass);
                            }
                        }
                        if (clazz2 != null) {
                        }
                        if (autoTypeSupport) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        IOUtils.close(is);
                        throw th;
                    }
                } else {
                    clazz = clazz5;
                    z = false;
                    jsonType2 = false;
                }
                IOUtils.close(is);
            } catch (Exception e3) {
                clazz = clazz5;
                z = false;
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e4) {
            jsonType = false;
            clazz = clazz5;
            z = false;
        } catch (Throwable th4) {
            th = th4;
        }
        if (autoTypeSupport || jsonType2 || expectClassFlag) {
            if (!autoTypeSupport || jsonType2) {
                z = true;
            }
            boolean cacheClass2 = z;
            clazz2 = TypeUtils.loadClass(typeName, this.defaultClassLoader, cacheClass2);
        } else {
            clazz2 = clazz;
        }
        if (clazz2 != null) {
            if (jsonType2) {
                if (autoTypeSupport) {
                    TypeUtils.addMapping(typeName, clazz2);
                }
                return clazz2;
            }
            if (ClassLoader.class.isAssignableFrom(clazz2) || DataSource.class.isAssignableFrom(clazz2) || RowSet.class.isAssignableFrom(clazz2)) {
                throw new JSONException("autoType is not support. " + typeName);
            }
            if (expectClass != null) {
                if (!expectClass.isAssignableFrom(clazz2)) {
                    throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
                }
                if (autoTypeSupport) {
                    TypeUtils.addMapping(typeName, clazz2);
                }
                return clazz2;
            }
            JavaBeanInfo beanInfo = JavaBeanInfo.build(clazz2, clazz2, this.propertyNamingStrategy);
            if (beanInfo.creatorConstructor != null && autoTypeSupport) {
                throw new JSONException("autoType is not support. " + typeName);
            }
        }
        if (autoTypeSupport) {
            if (clazz2 != null && autoTypeSupport) {
                TypeUtils.addMapping(typeName, clazz2);
            }
            return clazz2;
        }
        if (typeName.endsWith("Exception") || typeName.endsWith("Error")) {
            return null;
        }
        throw new JSONException("autoType is not support. " + typeName);
    }

    public void clearDeserializers() {
        this.deserializers.clear();
        initDeserializers();
    }

    public boolean isJacksonCompatible() {
        return this.jacksonCompatible;
    }

    public void setJacksonCompatible(boolean jacksonCompatible) {
        this.jacksonCompatible = jacksonCompatible;
    }

    public void register(String typeName, Class type) {
        this.typeMapping.putIfAbsent(typeName, type);
    }

    public void register(Module module) {
        this.modules.add(module);
    }

    public void addAutoTypeCheckHandler(AutoTypeCheckHandler h) {
        List<AutoTypeCheckHandler> autoTypeCheckHandlers = this.autoTypeCheckHandlers;
        if (autoTypeCheckHandlers == null) {
            CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
            autoTypeCheckHandlers = copyOnWriteArrayList;
            this.autoTypeCheckHandlers = copyOnWriteArrayList;
        }
        autoTypeCheckHandlers.add(h);
    }
}