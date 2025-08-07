package com.alibaba.fastjson.serializer;

/* loaded from: classes.dex */
public abstract class AfterFilter implements SerializeFilter {
    private static final ThreadLocal<JSONSerializer> serializerLocal = new ThreadLocal<>();
    private static final ThreadLocal<Character> seperatorLocal = new ThreadLocal<>();
    private static final Character COMMA = ',';

    public abstract void writeAfter(Object obj);

    final char writeAfter(JSONSerializer serializer, Object object, char seperator) {
        JSONSerializer last = serializerLocal.get();
        serializerLocal.set(serializer);
        seperatorLocal.set(Character.valueOf(seperator));
        writeAfter(object);
        serializerLocal.set(last);
        return seperatorLocal.get().charValue();
    }

    protected final void writeKeyValue(String key, Object value) {
        JSONSerializer serializer = serializerLocal.get();
        char seperator = seperatorLocal.get().charValue();
        boolean ref = serializer.containsReference(value);
        serializer.writeKeyValue(seperator, key, value);
        if (!ref && serializer.references != null) {
            serializer.references.remove(value);
        }
        if (seperator != ',') {
            seperatorLocal.set(COMMA);
        }
    }
}