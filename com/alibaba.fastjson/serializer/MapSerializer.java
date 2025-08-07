package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes.dex */
public class MapSerializer extends SerializeFilterable implements ObjectSerializer {
    public static MapSerializer instance = new MapSerializer();
    private static final int NON_STRINGKEY_AS_STRING = SerializerFeature.of(new SerializerFeature[]{SerializerFeature.BrowserCompatible, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.BrowserSecure});

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        write(serializer, object, fieldName, fieldType, features, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x018c A[Catch: all -> 0x039f, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x039f, blocks: (B:35:0x0063, B:51:0x00a8, B:52:0x00b5, B:54:0x00bb, B:72:0x010c, B:90:0x014c, B:108:0x018c, B:126:0x01cc, B:141:0x01fe, B:158:0x0236, B:161:0x0243, B:170:0x0253), top: B:241:0x0063 }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01cc A[Catch: all -> 0x039f, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x039f, blocks: (B:35:0x0063, B:51:0x00a8, B:52:0x00b5, B:54:0x00bb, B:72:0x010c, B:90:0x014c, B:108:0x018c, B:126:0x01cc, B:141:0x01fe, B:158:0x0236, B:161:0x0243, B:170:0x0253), top: B:241:0x0063 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x029e A[Catch: all -> 0x037c, TryCatch #0 {all -> 0x037c, blocks: (B:175:0x0287, B:177:0x029e, B:180:0x02b4, B:182:0x02ba, B:184:0x02bf, B:185:0x02c2, B:187:0x02ca, B:188:0x02cd, B:203:0x0300, B:204:0x030e, B:206:0x0317, B:208:0x0324, B:210:0x032c, B:212:0x0330, B:214:0x0335, B:216:0x0340, B:219:0x034a, B:220:0x035e, B:191:0x02d5, B:192:0x02d8, B:194:0x02e0, B:199:0x02f4, B:200:0x02f7, B:196:0x02e8, B:198:0x02ec, B:172:0x026e), top: B:237:0x0287 }] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x02ba A[Catch: all -> 0x037c, TryCatch #0 {all -> 0x037c, blocks: (B:175:0x0287, B:177:0x029e, B:180:0x02b4, B:182:0x02ba, B:184:0x02bf, B:185:0x02c2, B:187:0x02ca, B:188:0x02cd, B:203:0x0300, B:204:0x030e, B:206:0x0317, B:208:0x0324, B:210:0x032c, B:212:0x0330, B:214:0x0335, B:216:0x0340, B:219:0x034a, B:220:0x035e, B:191:0x02d5, B:192:0x02d8, B:194:0x02e0, B:199:0x02f4, B:200:0x02f7, B:196:0x02e8, B:198:0x02ec, B:172:0x026e), top: B:237:0x0287 }] */
    /* JADX WARN: Removed duplicated region for block: B:189:0x02d2  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x02b4 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:257:0x030e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0300 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x010c A[Catch: all -> 0x039f, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x039f, blocks: (B:35:0x0063, B:51:0x00a8, B:52:0x00b5, B:54:0x00bb, B:72:0x010c, B:90:0x014c, B:108:0x018c, B:126:0x01cc, B:141:0x01fe, B:158:0x0236, B:161:0x0243, B:170:0x0253), top: B:241:0x0063 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x014c A[Catch: all -> 0x039f, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x039f, blocks: (B:35:0x0063, B:51:0x00a8, B:52:0x00b5, B:54:0x00bb, B:72:0x010c, B:90:0x014c, B:108:0x018c, B:126:0x01cc, B:141:0x01fe, B:158:0x0236, B:161:0x0243, B:170:0x0253), top: B:241:0x0063 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features, boolean unwrapped) throws Throwable {
        Map<?, ?> map;
        SerialContext parent;
        Object entryKey;
        Iterator<Map.Entry<?, ?>> it;
        Object entryKey2;
        Object value;
        Class<?> preClazz;
        Object value2;
        ObjectSerializer preWriter;
        Class<?> preClazz2;
        ObjectSerializer preWriter2;
        Type valueType;
        MapSerializer mapSerializer = this;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        Map<?, ?> map2 = (Map) object;
        int mapSortFieldMask = SerializerFeature.MapSortField.mask;
        if ((out.features & mapSortFieldMask) == 0 && (features & mapSortFieldMask) == 0) {
            map = map2;
        } else {
            Map<?, ?> map3 = map2 instanceof JSONObject ? ((JSONObject) map2).getInnerMap() : map2;
            if ((map3 instanceof SortedMap) || (map3 instanceof LinkedHashMap)) {
                map = map3;
            } else {
                try {
                    map = new TreeMap<>((Map<? extends Object, ? extends Object>) map3);
                } catch (Exception e) {
                }
            }
        }
        if (serializer.containsReference(object)) {
            serializer.writeReference(object);
            return;
        }
        SerialContext parent2 = serializer.context;
        serializer.setContext(parent2, object, fieldName, 0);
        if (!unwrapped) {
            try {
                out.write(123);
            } catch (Throwable th) {
                th = th;
                parent = parent2;
                serializer.context = parent;
                throw th;
            }
        }
        try {
            serializer.incrementIndent();
            boolean first = true;
            if (out.isEnabled(SerializerFeature.WriteClassName)) {
                String typeKey = serializer.config.typeKey;
                Class<?> mapClass = map.getClass();
                boolean containsKey = (mapClass == JSONObject.class || mapClass == HashMap.class || mapClass == LinkedHashMap.class) && map.containsKey(typeKey);
                if (!containsKey) {
                    out.writeFieldName(typeKey);
                    out.writeString(object.getClass().getName());
                    first = false;
                }
            }
            Iterator<Map.Entry<?, ?>> it2 = map.entrySet().iterator();
            Class<?> preClazz3 = null;
            ObjectSerializer preWriter3 = null;
            boolean first2 = first;
            while (it2.hasNext()) {
                Map.Entry entry = it2.next();
                Object value3 = entry.getValue();
                Object entryKey3 = entry.getKey();
                List<PropertyPreFilter> preFilters = serializer.propertyPreFilters;
                if (preFilters != null) {
                    if (preFilters.size() <= 0) {
                        List<PropertyPreFilter> preFilters2 = mapSerializer.propertyPreFilters;
                        if (preFilters2 == null || preFilters2.size() <= 0) {
                            List<PropertyFilter> propertyFilters = serializer.propertyFilters;
                            if (propertyFilters == null || propertyFilters.size() <= 0) {
                                List<PropertyFilter> propertyFilters2 = mapSerializer.propertyFilters;
                                if (propertyFilters2 == null || propertyFilters2.size() <= 0) {
                                    List<NameFilter> nameFilters = serializer.nameFilters;
                                    if (nameFilters != null && nameFilters.size() > 0) {
                                        if (entryKey3 == null || (entryKey3 instanceof String)) {
                                            entryKey3 = mapSerializer.processKey(serializer, object, (String) entryKey3, value3);
                                        } else if (entryKey3.getClass().isPrimitive() || (entryKey3 instanceof Number)) {
                                            String strKey = JSON.toJSONString(entryKey3);
                                            entryKey3 = mapSerializer.processKey(serializer, object, strKey, value3);
                                        }
                                    }
                                    List<NameFilter> nameFilters2 = mapSerializer.nameFilters;
                                    if (nameFilters2 == null || nameFilters2.size() <= 0) {
                                        entryKey = entryKey3;
                                        if (entryKey != null || (entryKey instanceof String)) {
                                            it = it2;
                                            entryKey2 = entryKey;
                                            value = value3;
                                            preClazz = preClazz3;
                                            parent = parent2;
                                            try {
                                                value2 = processValue(serializer, null, object, (String) entryKey2, value, features);
                                                if (value2 != null || SerializerFeature.isEnabled(out.features, features, SerializerFeature.WriteMapNullValue)) {
                                                    if (entryKey2 instanceof String) {
                                                        String key = (String) entryKey2;
                                                        if (!first2) {
                                                            out.write(44);
                                                        }
                                                        if (out.isEnabled(SerializerFeature.PrettyFormat)) {
                                                            serializer.println();
                                                        }
                                                        out.writeFieldName(key, true);
                                                    } else {
                                                        if (!first2) {
                                                            out.write(44);
                                                        }
                                                        if ((out.isEnabled(NON_STRINGKEY_AS_STRING) || SerializerFeature.isEnabled(features, SerializerFeature.WriteNonStringKeyAsString)) && !(entryKey2 instanceof Enum)) {
                                                            String strEntryKey = JSON.toJSONString(entryKey2);
                                                            serializer.write(strEntryKey);
                                                        } else {
                                                            serializer.write(entryKey2);
                                                        }
                                                        out.write(58);
                                                    }
                                                    first2 = false;
                                                    if (value2 == null) {
                                                        out.writeNull();
                                                        parent2 = parent;
                                                        it2 = it;
                                                        preClazz3 = preClazz;
                                                        mapSerializer = this;
                                                    } else {
                                                        Class<?> clazz = value2.getClass();
                                                        Class<?> preClazz4 = preClazz;
                                                        if (clazz != preClazz4) {
                                                            preClazz2 = clazz;
                                                            preWriter = serializer.getObjectWriter(clazz);
                                                        } else {
                                                            preWriter = preWriter3;
                                                            preClazz2 = preClazz4;
                                                        }
                                                        if (!SerializerFeature.isEnabled(features, SerializerFeature.WriteClassName) || !(preWriter instanceof JavaBeanSerializer)) {
                                                            preWriter2 = preWriter;
                                                            preWriter2.write(serializer, value2, entryKey2, null, features);
                                                        } else if (fieldType instanceof ParameterizedType) {
                                                            ParameterizedType parameterizedType = (ParameterizedType) fieldType;
                                                            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                                                            if (actualTypeArguments.length == 2) {
                                                                Type valueType2 = actualTypeArguments[1];
                                                                valueType = valueType2;
                                                                JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) preWriter;
                                                                preWriter2 = preWriter;
                                                                javaBeanSerializer.writeNoneASM(serializer, value2, entryKey2, valueType, features);
                                                            } else {
                                                                valueType = null;
                                                                JavaBeanSerializer javaBeanSerializer2 = (JavaBeanSerializer) preWriter;
                                                                preWriter2 = preWriter;
                                                                javaBeanSerializer2.writeNoneASM(serializer, value2, entryKey2, valueType, features);
                                                            }
                                                        } else {
                                                            valueType = null;
                                                            JavaBeanSerializer javaBeanSerializer22 = (JavaBeanSerializer) preWriter;
                                                            preWriter2 = preWriter;
                                                            javaBeanSerializer22.writeNoneASM(serializer, value2, entryKey2, valueType, features);
                                                        }
                                                        parent2 = parent;
                                                        preClazz3 = preClazz2;
                                                        preWriter3 = preWriter2;
                                                        it2 = it;
                                                        mapSerializer = this;
                                                    }
                                                } else {
                                                    parent2 = parent;
                                                    it2 = it;
                                                    preClazz3 = preClazz;
                                                    mapSerializer = this;
                                                }
                                            } catch (Throwable th2) {
                                                th = th2;
                                                serializer.context = parent;
                                                throw th;
                                            }
                                        } else {
                                            boolean objectOrArray = (entryKey instanceof Map) || (entryKey instanceof Collection);
                                            if (objectOrArray) {
                                                it = it2;
                                                entryKey2 = entryKey;
                                                value2 = value3;
                                                preClazz = preClazz3;
                                                parent = parent2;
                                            } else {
                                                String strKey2 = JSON.toJSONString(entryKey);
                                                it = it2;
                                                entryKey2 = entryKey;
                                                preClazz = preClazz3;
                                                parent = parent2;
                                                value2 = processValue(serializer, null, object, strKey2, value3, features);
                                            }
                                            if (value2 != null) {
                                            }
                                            if (entryKey2 instanceof String) {
                                            }
                                            first2 = false;
                                            if (value2 == null) {
                                            }
                                        }
                                    } else {
                                        if (entryKey3 == null || (entryKey3 instanceof String)) {
                                            entryKey = mapSerializer.processKey(serializer, object, (String) entryKey3, value3);
                                        } else if (entryKey3.getClass().isPrimitive() || (entryKey3 instanceof Number)) {
                                            String strKey3 = JSON.toJSONString(entryKey3);
                                            entryKey = mapSerializer.processKey(serializer, object, strKey3, value3);
                                        }
                                        if (entryKey != null) {
                                            it = it2;
                                            entryKey2 = entryKey;
                                            value = value3;
                                            preClazz = preClazz3;
                                            parent = parent2;
                                        } else {
                                            it = it2;
                                            entryKey2 = entryKey;
                                            value = value3;
                                            preClazz = preClazz3;
                                            parent = parent2;
                                        }
                                        value2 = processValue(serializer, null, object, (String) entryKey2, value, features);
                                        if (value2 != null) {
                                        }
                                        if (entryKey2 instanceof String) {
                                        }
                                        first2 = false;
                                        if (value2 == null) {
                                        }
                                    }
                                } else if (entryKey3 == null || (entryKey3 instanceof String)) {
                                    if (!mapSerializer.apply(serializer, object, (String) entryKey3, value3)) {
                                        it = it2;
                                        preClazz = preClazz3;
                                        parent = parent2;
                                    }
                                    parent2 = parent;
                                    it2 = it;
                                    preClazz3 = preClazz;
                                    mapSerializer = this;
                                } else if (entryKey3.getClass().isPrimitive() || (entryKey3 instanceof Number)) {
                                    String strKey4 = JSON.toJSONString(entryKey3);
                                    if (!mapSerializer.apply(serializer, object, strKey4, value3)) {
                                        it = it2;
                                        preClazz = preClazz3;
                                        parent = parent2;
                                    }
                                    parent2 = parent;
                                    it2 = it;
                                    preClazz3 = preClazz;
                                    mapSerializer = this;
                                }
                            } else if (entryKey3 == null || (entryKey3 instanceof String)) {
                                if (!mapSerializer.apply(serializer, object, (String) entryKey3, value3)) {
                                    it = it2;
                                    preClazz = preClazz3;
                                    parent = parent2;
                                }
                                parent2 = parent;
                                it2 = it;
                                preClazz3 = preClazz;
                                mapSerializer = this;
                            } else if (entryKey3.getClass().isPrimitive() || (entryKey3 instanceof Number)) {
                                String strKey5 = JSON.toJSONString(entryKey3);
                                if (!mapSerializer.apply(serializer, object, strKey5, value3)) {
                                    it = it2;
                                    preClazz = preClazz3;
                                    parent = parent2;
                                }
                                parent2 = parent;
                                it2 = it;
                                preClazz3 = preClazz;
                                mapSerializer = this;
                            }
                        } else if (entryKey3 == null || (entryKey3 instanceof String)) {
                            if (!mapSerializer.applyName(serializer, object, (String) entryKey3)) {
                                it = it2;
                                preClazz = preClazz3;
                                parent = parent2;
                            }
                            parent2 = parent;
                            it2 = it;
                            preClazz3 = preClazz;
                            mapSerializer = this;
                        } else if (entryKey3.getClass().isPrimitive() || (entryKey3 instanceof Number)) {
                            String strKey6 = JSON.toJSONString(entryKey3);
                            if (!mapSerializer.applyName(serializer, object, strKey6)) {
                                it = it2;
                                preClazz = preClazz3;
                                parent = parent2;
                            }
                            parent2 = parent;
                            it2 = it;
                            preClazz3 = preClazz;
                            mapSerializer = this;
                        }
                    } else if (entryKey3 == null || (entryKey3 instanceof String)) {
                        if (!mapSerializer.applyName(serializer, object, (String) entryKey3)) {
                            it = it2;
                            preClazz = preClazz3;
                            parent = parent2;
                        }
                        parent2 = parent;
                        it2 = it;
                        preClazz3 = preClazz;
                        mapSerializer = this;
                    } else if (entryKey3.getClass().isPrimitive() || (entryKey3 instanceof Number)) {
                        String strKey7 = JSON.toJSONString(entryKey3);
                        if (!mapSerializer.applyName(serializer, object, strKey7)) {
                            it = it2;
                            preClazz = preClazz3;
                            parent = parent2;
                        }
                        parent2 = parent;
                        it2 = it;
                        preClazz3 = preClazz;
                        mapSerializer = this;
                    }
                }
            }
            serializer.context = parent2;
            serializer.decrementIdent();
            if (out.isEnabled(SerializerFeature.PrettyFormat) && map.size() > 0) {
                serializer.println();
            }
            if (unwrapped) {
                return;
            }
            out.write(125);
        } catch (Throwable th3) {
            th = th3;
            parent = parent2;
        }
    }
}