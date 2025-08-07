package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class ArraySerializer implements ObjectSerializer {
    private final ObjectSerializer compObjectSerializer;
    private final Class<?> componentType;

    public ArraySerializer(Class<?> componentType, ObjectSerializer compObjectSerializer) {
        this.componentType = componentType;
        this.compObjectSerializer = compObjectSerializer;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        Object[] array = (Object[]) object;
        int size = array.length;
        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);
        try {
            out.append('[');
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    out.append(',');
                }
                Object item = array[i];
                if (item != null) {
                    if (item.getClass() == this.componentType) {
                        this.compObjectSerializer.write(serializer, item, Integer.valueOf(i), null, 0);
                    } else {
                        ObjectSerializer itemSerializer = serializer.getObjectWriter(item.getClass());
                        itemSerializer.write(serializer, item, Integer.valueOf(i), null, 0);
                    }
                } else if (out.isEnabled(SerializerFeature.WriteNullStringAsEmpty) && (object instanceof String[])) {
                    out.writeString("");
                } else {
                    out.append((CharSequence) "null");
                }
            }
            out.append(']');
        } finally {
            serializer.context = context;
        }
    }
}