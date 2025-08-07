package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public final class ListSerializer implements ObjectSerializer {
    public static final ListSerializer instance = new ListSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        Type elementType;
        SerialContext context;
        Object item;
        SerialContext context2;
        List<?> list;
        SerializeWriter out;
        boolean writeClassName = serializer.out.isEnabled(SerializerFeature.WriteClassName) || SerializerFeature.isEnabled(features, SerializerFeature.WriteClassName);
        SerializeWriter out2 = serializer.out;
        if (writeClassName) {
            Type elementType2 = TypeUtils.getCollectionItemType(fieldType);
            elementType = elementType2;
        } else {
            elementType = null;
        }
        if (object == null) {
            out2.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        List<?> list2 = (List) object;
        if (list2.size() == 0) {
            out2.append((CharSequence) HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        SerialContext context3 = serializer.context;
        serializer.setContext(context3, object, fieldName, 0);
        try {
            char c = ',';
            try {
                if (out2.isEnabled(SerializerFeature.PrettyFormat)) {
                    out2.append('[');
                    serializer.incrementIndent();
                    int i = 0;
                    for (Object item2 : list2) {
                        if (i != 0) {
                            try {
                                out2.append(c);
                            } catch (Throwable th) {
                                th = th;
                                context = context3;
                            }
                        }
                        serializer.println();
                        if (item2 == null) {
                            context2 = context3;
                            list = list2;
                            out = out2;
                            serializer.out.writeNull();
                        } else if (serializer.containsReference(item2)) {
                            serializer.writeReference(item2);
                            context2 = context3;
                            list = list2;
                            out = out2;
                        } else {
                            ObjectSerializer itemSerializer = serializer.getObjectWriter(item2.getClass());
                            SerialContext itemContext = new SerialContext(context3, object, fieldName, 0, 0);
                            serializer.context = itemContext;
                            context2 = context3;
                            list = list2;
                            out = out2;
                            itemSerializer.write(serializer, item2, Integer.valueOf(i), elementType, features);
                        }
                        i++;
                        list2 = list;
                        context3 = context2;
                        out2 = out;
                        c = ',';
                    }
                    SerialContext context4 = context3;
                    serializer.decrementIdent();
                    serializer.println();
                    out2.append(']');
                    serializer.context = context4;
                    return;
                }
                context = context3;
                char c2 = ',';
                out2.append('[');
                int i2 = 0;
                int size = list2.size();
                ObjectSerializer itemSerializer2 = null;
                while (i2 < size) {
                    try {
                        Object item3 = list2.get(i2);
                        if (i2 != 0) {
                            out2.append(c2);
                        }
                        if (item3 == null) {
                            out2.append((CharSequence) "null");
                        } else {
                            Class<?> clazz = item3.getClass();
                            if (clazz == Integer.class) {
                                out2.writeInt(((Integer) item3).intValue());
                            } else if (clazz == Long.class) {
                                long val = ((Long) item3).longValue();
                                if (writeClassName) {
                                    out2.writeLong(val);
                                    out2.write(76);
                                } else {
                                    out2.writeLong(val);
                                }
                            } else if ((SerializerFeature.DisableCircularReferenceDetect.mask & features) != 0) {
                                ObjectSerializer itemSerializer3 = serializer.getObjectWriter(item3.getClass());
                                itemSerializer3.write(serializer, item3, Integer.valueOf(i2), elementType, features);
                                itemSerializer2 = itemSerializer3;
                            } else {
                                if (out2.disableCircularReferenceDetect) {
                                    item = item3;
                                } else {
                                    item = item3;
                                    SerialContext itemContext2 = new SerialContext(context, object, fieldName, 0, 0);
                                    serializer.context = itemContext2;
                                }
                                if (serializer.containsReference(item)) {
                                    serializer.writeReference(item);
                                } else {
                                    itemSerializer2 = serializer.getObjectWriter(item.getClass());
                                    if ((SerializerFeature.WriteClassName.mask & features) == 0 || !(itemSerializer2 instanceof JavaBeanSerializer)) {
                                        itemSerializer2.write(serializer, item, Integer.valueOf(i2), elementType, features);
                                    } else {
                                        JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) itemSerializer2;
                                        javaBeanSerializer.writeNoneASM(serializer, item, Integer.valueOf(i2), elementType, features);
                                    }
                                }
                            }
                        }
                        i2++;
                        c2 = ',';
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
                out2.append(']');
                serializer.context = context;
                return;
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            context = context3;
        }
        serializer.context = context;
        throw th;
    }
}