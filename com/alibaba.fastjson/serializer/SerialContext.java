package com.alibaba.fastjson.serializer;

import kotlin.text.Typography;

/* loaded from: classes.dex */
public class SerialContext {
    public final int features;
    public final Object fieldName;
    public final Object object;
    public final SerialContext parent;

    public SerialContext(SerialContext parent, Object object, Object fieldName, int features, int fieldFeatures) {
        this.parent = parent;
        this.object = object;
        this.fieldName = fieldName;
        this.features = features;
    }

    public String toString() {
        if (this.parent == null) {
            return "$";
        }
        StringBuilder buf = new StringBuilder();
        toString(buf);
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        if (this.parent == null) {
            buf.append(Typography.dollar);
            return;
        }
        this.parent.toString(buf);
        if (this.fieldName == null) {
            buf.append(".null");
            return;
        }
        if (this.fieldName instanceof Integer) {
            buf.append('[');
            buf.append(((Integer) this.fieldName).intValue());
            buf.append(']');
            return;
        }
        buf.append('.');
        String fieldName = this.fieldName.toString();
        boolean special = false;
        int i = 0;
        while (true) {
            if (i >= fieldName.length()) {
                break;
            }
            char ch = fieldName.charAt(i);
            if ((ch >= '0' && ch <= '9') || ((ch >= 'A' && ch <= 'Z') || ((ch >= 'a' && ch <= 'z') || ch > 128))) {
                i++;
            } else {
                special = true;
                break;
            }
        }
        if (special) {
            for (int i2 = 0; i2 < fieldName.length(); i2++) {
                char ch2 = fieldName.charAt(i2);
                if (ch2 == '\\') {
                    buf.append('\\');
                    buf.append('\\');
                    buf.append('\\');
                } else if ((ch2 >= '0' && ch2 <= '9') || ((ch2 >= 'A' && ch2 <= 'Z') || ((ch2 >= 'a' && ch2 <= 'z') || ch2 > 128))) {
                    buf.append(ch2);
                } else if (ch2 == '\"') {
                    buf.append('\\');
                    buf.append('\\');
                    buf.append('\\');
                } else {
                    buf.append('\\');
                    buf.append('\\');
                }
                buf.append(ch2);
            }
            return;
        }
        buf.append(fieldName);
    }

    public SerialContext getParent() {
        return this.parent;
    }

    public Object getObject() {
        return this.object;
    }

    public Object getFieldName() {
        return this.fieldName;
    }

    public String getPath() {
        return toString();
    }
}