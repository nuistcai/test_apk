package com.alibaba.fastjson.serializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class JSONObjectCodec implements ObjectSerializer {
    public static final JSONObjectCodec instance = new JSONObjectCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        SerializeWriter out = serializer.out;
        MapSerializer mapSerializer = MapSerializer.instance;
        try {
            Field mapField = object.getClass().getDeclaredField("map");
            if (Modifier.isPrivate(mapField.getModifiers())) {
                mapField.setAccessible(true);
            }
            Object map = mapField.get(object);
            mapSerializer.write(serializer, map, fieldName, fieldType, features);
        } catch (Exception e) {
            out.writeNull();
        }
    }
}