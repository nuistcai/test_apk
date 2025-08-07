package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class ObjectArrayCodec implements ObjectSerializer, ObjectDeserializer {
    public static final ObjectArrayCodec instance = new ObjectArrayCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        int i;
        char c;
        SerializeWriter out = serializer.out;
        Object[] array = (Object[]) object;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        int size = array.length;
        int end = size - 1;
        if (end == -1) {
            out.append((CharSequence) HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);
        Class<?> preClazz = null;
        try {
            out.append('[');
            if (!out.isEnabled(SerializerFeature.PrettyFormat)) {
                ObjectSerializer preWriter = null;
                int i2 = 0;
                while (i2 < end) {
                    Object item = array[i2];
                    if (item == null) {
                        out.append((CharSequence) "null,");
                        i = i2;
                    } else {
                        if (serializer.containsReference(item)) {
                            serializer.writeReference(item);
                            i = i2;
                            c = ',';
                        } else {
                            Class<?> clazz = item.getClass();
                            if (clazz == preClazz) {
                                i = i2;
                                c = ',';
                                preWriter.write(serializer, item, Integer.valueOf(i2), null, 0);
                            } else {
                                i = i2;
                                c = ',';
                                preClazz = clazz;
                                ObjectSerializer preWriter2 = serializer.getObjectWriter(clazz);
                                preWriter2.write(serializer, item, Integer.valueOf(i), null, 0);
                                preWriter = preWriter2;
                            }
                        }
                        out.append(c);
                    }
                    i2 = i + 1;
                }
                Object item2 = array[end];
                if (item2 == null) {
                    out.append((CharSequence) "null]");
                } else {
                    if (!serializer.containsReference(item2)) {
                        serializer.writeWithFieldName(item2, Integer.valueOf(end));
                    } else {
                        serializer.writeReference(item2);
                    }
                    out.append(']');
                }
                return;
            }
            serializer.incrementIndent();
            serializer.println();
            for (int i3 = 0; i3 < size; i3++) {
                if (i3 != 0) {
                    out.write(44);
                    serializer.println();
                }
                serializer.writeWithFieldName(array[i3], Integer.valueOf(i3));
            }
            serializer.decrementIdent();
            serializer.println();
            out.write(93);
        } finally {
            serializer.context = context;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r13v4, types: [java.lang.reflect.Type[]] */
    /* JADX WARN: Type inference failed for: r4v10, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r4v2, types: [T, byte[]] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Class<?> cls;
        Type type2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = jSONLexer.token();
        if (i == 8) {
            jSONLexer.nextToken(16);
            return null;
        }
        if (i == 4 || i == 26) {
            ?? r4 = (T) jSONLexer.bytesValue();
            jSONLexer.nextToken(16);
            if (r4.length != 0 || type == byte[].class) {
                return r4;
            }
            return null;
        }
        if (type instanceof GenericArrayType) {
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            if (genericComponentType instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) genericComponentType;
                Type type3 = defaultJSONParser.getContext().type;
                if (type3 instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type3;
                    Type rawType = parameterizedType.getRawType();
                    Class<?> cls2 = null;
                    if (rawType instanceof Class) {
                        TypeVariable<Class<T>>[] typeParameters = ((Class) rawType).getTypeParameters();
                        for (int i2 = 0; i2 < typeParameters.length; i2++) {
                            if (typeParameters[i2].getName().equals(typeVariable.getName())) {
                                cls2 = parameterizedType.getActualTypeArguments()[i2];
                            }
                        }
                    }
                    if (cls2 instanceof Class) {
                        cls = cls2;
                        type2 = genericComponentType;
                    } else {
                        cls = Object.class;
                        type2 = genericComponentType;
                    }
                } else {
                    cls = TypeUtils.getClass(typeVariable.getBounds()[0]);
                    type2 = genericComponentType;
                }
            } else {
                cls = TypeUtils.getClass(genericComponentType);
                type2 = genericComponentType;
            }
        } else {
            Class<?> componentType = ((Class) type).getComponentType();
            cls = componentType;
            type2 = componentType;
        }
        JSONArray jSONArray = new JSONArray();
        defaultJSONParser.parseArray(type2, jSONArray, obj);
        return (T) toObjectArray(defaultJSONParser, cls, jSONArray);
    }

    private <T> T toObjectArray(DefaultJSONParser defaultJSONParser, Class<?> cls, JSONArray jSONArray) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Object objectArray;
        if (jSONArray == null) {
            return null;
        }
        int size = jSONArray.size();
        T t = (T) Array.newInstance(cls, size);
        for (int i = 0; i < size; i++) {
            Object obj = jSONArray.get(i);
            if (obj == jSONArray) {
                Array.set(t, i, t);
            } else if (cls.isArray()) {
                if (cls.isInstance(obj)) {
                    objectArray = obj;
                } else {
                    objectArray = toObjectArray(defaultJSONParser, cls, (JSONArray) obj);
                }
                Array.set(t, i, objectArray);
            } else {
                Object objCast = null;
                if (obj instanceof JSONArray) {
                    boolean z = false;
                    JSONArray jSONArray2 = (JSONArray) obj;
                    int size2 = jSONArray2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        if (jSONArray2.get(i2) == jSONArray) {
                            jSONArray2.set(i, t);
                            z = true;
                        }
                    }
                    if (z) {
                        objCast = jSONArray2.toArray();
                    }
                }
                if (objCast == null) {
                    objCast = TypeUtils.cast(obj, (Class<Object>) cls, defaultJSONParser.getConfig());
                }
                Array.set(t, i, objCast);
            }
        }
        jSONArray.setRelatedArray(t);
        jSONArray.setComponentType(cls);
        return t;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 14;
    }
}