package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Enumeration;

/* loaded from: classes.dex */
public class EnumerationSerializer implements ObjectSerializer {
    public static EnumerationSerializer instance = new EnumerationSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        Type elementType;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        if (out.isEnabled(SerializerFeature.WriteClassName) && (fieldType instanceof ParameterizedType)) {
            ParameterizedType param = (ParameterizedType) fieldType;
            Type elementType2 = param.getActualTypeArguments()[0];
            elementType = elementType2;
        } else {
            elementType = null;
        }
        Enumeration<?> e = (Enumeration) object;
        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);
        int i = 0;
        try {
            out.append('[');
            while (e.hasMoreElements()) {
                Object item = e.nextElement();
                int i2 = i + 1;
                if (i != 0) {
                    out.append(',');
                }
                if (item != null) {
                    ObjectSerializer itemSerializer = serializer.getObjectWriter(item.getClass());
                    itemSerializer.write(serializer, item, Integer.valueOf(i2 - 1), elementType, 0);
                } else {
                    out.writeNull();
                }
                i = i2;
            }
            out.append(']');
        } finally {
            serializer.context = context;
        }
    }
}