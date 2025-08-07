package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class CollectionCodec implements ObjectSerializer, ObjectDeserializer {
    public static final CollectionCodec instance = new CollectionCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        Type elementType;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        if (!out.isEnabled(SerializerFeature.WriteClassName) && !SerializerFeature.isEnabled(features, SerializerFeature.WriteClassName)) {
            elementType = null;
        } else {
            Type elementType2 = TypeUtils.getCollectionItemType(fieldType);
            elementType = elementType2;
        }
        Collection<?> collection = (Collection) object;
        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);
        if (out.isEnabled(SerializerFeature.WriteClassName)) {
            if (HashSet.class.isAssignableFrom(collection.getClass())) {
                out.append((CharSequence) "Set");
            } else if (TreeSet.class == collection.getClass()) {
                out.append((CharSequence) "TreeSet");
            }
        }
        int i = 0;
        try {
            out.append('[');
            for (Object item : collection) {
                int i2 = i + 1;
                if (i != 0) {
                    out.append(',');
                }
                if (item == null) {
                    out.writeNull();
                } else {
                    Class<?> clazz = item.getClass();
                    if (clazz == Integer.class) {
                        out.writeInt(((Integer) item).intValue());
                    } else if (clazz == Long.class) {
                        out.writeLong(((Long) item).longValue());
                        if (out.isEnabled(SerializerFeature.WriteClassName)) {
                            out.write(76);
                        }
                    } else {
                        ObjectSerializer itemSerializer = serializer.getObjectWriter(clazz);
                        if (!SerializerFeature.isEnabled(features, SerializerFeature.WriteClassName) || !(itemSerializer instanceof JavaBeanSerializer)) {
                            itemSerializer.write(serializer, item, Integer.valueOf(i2 - 1), elementType, features);
                        } else {
                            JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) itemSerializer;
                            javaBeanSerializer.writeNoneASM(serializer, item, Integer.valueOf(i2 - 1), elementType, features);
                        }
                    }
                }
                i = i2;
            }
            out.append(']');
        } finally {
            serializer.context = context;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [T, com.alibaba.fastjson.JSONArray, java.util.Collection] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Collection collection;
        if (defaultJSONParser.lexer.token() == 8) {
            defaultJSONParser.lexer.nextToken(16);
            return null;
        }
        if (type == JSONArray.class) {
            ?? r0 = (T) new JSONArray();
            defaultJSONParser.parseArray((Collection) r0);
            return r0;
        }
        if (defaultJSONParser.lexer.token() == 21) {
            defaultJSONParser.lexer.nextToken();
            collection = (T) TypeUtils.createSet(type);
        } else {
            collection = (T) TypeUtils.createCollection(type);
        }
        defaultJSONParser.parseArray(TypeUtils.getCollectionItemType(type), collection, obj);
        return (T) collection;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 14;
    }
}