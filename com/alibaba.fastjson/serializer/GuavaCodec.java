package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class GuavaCodec implements ObjectSerializer, ObjectDeserializer {
    public static GuavaCodec instance = new GuavaCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter serializeWriter = serializer.out;
        if (object instanceof Multimap) {
            Multimap multimap = (Multimap) object;
            serializer.write(multimap.asMap());
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [T, com.google.common.collect.ArrayListMultimap] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Type rawType = type;
        if (type instanceof ParameterizedType) {
            rawType = ((ParameterizedType) type).getRawType();
        }
        if (rawType == ArrayListMultimap.class) {
            ?? r1 = (T) ArrayListMultimap.create();
            for (Map.Entry<String, Object> entry : defaultJSONParser.parseObject().entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Collection) {
                    r1.putAll(entry.getKey(), (List) value);
                } else {
                    r1.put(entry.getKey(), value);
                }
            }
            return r1;
        }
        return null;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 0;
    }
}