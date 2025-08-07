package com.alibaba.fastjson;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONScanner;

/* loaded from: classes.dex */
public class JSONPatch {

    @JSONType(orders = {"op", "from", "path", "value"})
    public static class Operation {
        public String from;
        public String path;

        @JSONField(name = "op")
        public OperationType type;
        public Object value;
    }

    public enum OperationType {
        add,
        remove,
        replace,
        move,
        copy,
        test
    }

    public static String apply(String original, String patch) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Object object = apply(JSON.parse(original, Feature.OrderedField), patch);
        return JSON.toJSONString(object);
    }

    public static Object apply(Object object, String patch) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Operation[] operations;
        if (isObject(patch)) {
            operations = new Operation[]{(Operation) JSON.parseObject(patch, Operation.class)};
        } else {
            operations = (Operation[]) JSON.parseObject(patch, Operation[].class);
        }
        for (Operation op : operations) {
            JSONPath path = JSONPath.compile(op.path);
            switch (op.type) {
                case add:
                    path.patchAdd(object, op.value, false);
                    break;
                case replace:
                    path.patchAdd(object, op.value, true);
                    break;
                case remove:
                    path.remove(object);
                    break;
                case copy:
                case move:
                    JSONPath from = JSONPath.compile(op.from);
                    Object fromValue = from.eval(object);
                    if (op.type == OperationType.move) {
                        boolean success = from.remove(object);
                        if (!success) {
                            throw new JSONException("json patch move error : " + op.from + " -> " + op.path);
                        }
                    }
                    path.set(object, fromValue);
                    break;
                case test:
                    Object result = path.eval(object);
                    if (result == null) {
                        return Boolean.valueOf(op.value == null);
                    }
                    return Boolean.valueOf(result.equals(op.value));
            }
        }
        return object;
    }

    private static boolean isObject(String patch) {
        if (patch == null) {
            return false;
        }
        for (int i = 0; i < patch.length(); i++) {
            char ch = patch.charAt(i);
            if (!JSONScanner.isWhitespace(ch)) {
                return ch == '{';
            }
        }
        return false;
    }
}