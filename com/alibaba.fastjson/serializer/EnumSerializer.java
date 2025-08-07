package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class EnumSerializer implements ObjectSerializer {
    public static final EnumSerializer instance = new EnumSerializer();
    private final Member member;

    public EnumSerializer() {
        this.member = null;
    }

    public EnumSerializer(Member member) {
        this.member = member;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        Object fieldValue;
        if (this.member == null) {
            SerializeWriter out = serializer.out;
            out.writeEnum((Enum) object);
            return;
        }
        try {
            if (this.member instanceof Field) {
                fieldValue = ((Field) this.member).get(object);
            } else {
                fieldValue = ((Method) this.member).invoke(object, new Object[0]);
            }
            serializer.write(fieldValue);
        } catch (Exception e) {
            throw new JSONException("getEnumValue error", e);
        }
    }
}