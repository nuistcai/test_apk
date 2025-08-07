package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/* loaded from: classes.dex */
public class AnnotationSerializer implements ObjectSerializer {
    private static volatile Class sun_AnnotationType = null;
    private static volatile boolean sun_AnnotationType_error = false;
    private static volatile Method sun_AnnotationType_getInstance = null;
    private static volatile Method sun_AnnotationType_members = null;
    public static AnnotationSerializer instance = new AnnotationSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        JSONException jSONException;
        Class objClass = object.getClass();
        Class[] interfaces = objClass.getInterfaces();
        if (interfaces.length == 1 && interfaces[0].isAnnotation()) {
            Class annotationClass = interfaces[0];
            if (sun_AnnotationType == null && !sun_AnnotationType_error) {
                try {
                    sun_AnnotationType = Class.forName("sun.reflect.annotation.AnnotationType");
                } finally {
                }
            }
            if (sun_AnnotationType == null) {
                throw new JSONException("not support Type Annotation.");
            }
            if (sun_AnnotationType_getInstance == null && !sun_AnnotationType_error) {
                try {
                    sun_AnnotationType_getInstance = sun_AnnotationType.getMethod("getInstance", Class.class);
                } finally {
                }
            }
            if (sun_AnnotationType_members == null && !sun_AnnotationType_error) {
                try {
                    sun_AnnotationType_members = sun_AnnotationType.getMethod("members", new Class[0]);
                } finally {
                }
            }
            if (sun_AnnotationType_getInstance == null || sun_AnnotationType_error) {
                throw new JSONException("not support Type Annotation.");
            }
            try {
                Object type = sun_AnnotationType_getInstance.invoke(null, annotationClass);
                try {
                    Map<String, Method> members = (Map) sun_AnnotationType_members.invoke(type, new Object[0]);
                    JSONObject json = new JSONObject(members.size());
                    Object val = null;
                    for (Map.Entry<String, Method> entry : members.entrySet()) {
                        try {
                            try {
                                Object val2 = entry.getValue().invoke(object, new Object[0]);
                                val = val2;
                            } catch (IllegalAccessException e) {
                            } catch (InvocationTargetException e2) {
                            }
                        } catch (IllegalAccessException e3) {
                        } catch (InvocationTargetException e4) {
                        }
                        json.put(entry.getKey(), JSON.toJSON(val));
                    }
                    serializer.write(json);
                } finally {
                }
            } finally {
            }
        }
    }
}