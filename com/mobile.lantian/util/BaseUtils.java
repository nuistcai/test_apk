package com.mobile.lantian.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes.dex */
public class BaseUtils {
    public static boolean isEmpty(Object target) {
        if (target == null) {
            return true;
        }
        if ((target instanceof String) && target.equals("")) {
            return true;
        }
        if (target instanceof Collection) {
            return ((Collection) target).isEmpty();
        }
        if (target instanceof Map) {
            return ((Map) target).isEmpty();
        }
        return target.getClass().isArray() && Array.getLength(target) == 0;
    }
}