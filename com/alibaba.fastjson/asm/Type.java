package com.alibaba.fastjson.asm;

import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class Type {
    private final char[] buf;
    private final int len;
    private final int off;
    protected final int sort;
    public static final Type VOID_TYPE = new Type(0, null, 1443168256, 1);
    public static final Type BOOLEAN_TYPE = new Type(1, null, 1509950721, 1);
    public static final Type CHAR_TYPE = new Type(2, null, 1124075009, 1);
    public static final Type BYTE_TYPE = new Type(3, null, 1107297537, 1);
    public static final Type SHORT_TYPE = new Type(4, null, 1392510721, 1);
    public static final Type INT_TYPE = new Type(5, null, 1224736769, 1);
    public static final Type FLOAT_TYPE = new Type(6, null, 1174536705, 1);
    public static final Type LONG_TYPE = new Type(7, null, 1241579778, 1);
    public static final Type DOUBLE_TYPE = new Type(8, null, 1141048066, 1);

    private Type(int sort, char[] buf, int off, int len) {
        this.sort = sort;
        this.buf = buf;
        this.off = off;
        this.len = len;
    }

    public static Type getType(String typeDescriptor) {
        return getType(typeDescriptor.toCharArray(), 0);
    }

    public static int getArgumentsAndReturnSizes(String desc) {
        int c;
        int c2;
        int n = 1;
        int c3 = 1;
        while (true) {
            c = c3 + 1;
            char car = desc.charAt(c3);
            if (car == ')') {
                break;
            }
            if (car == 'L') {
                while (true) {
                    c2 = c + 1;
                    if (desc.charAt(c) == 59) {
                        break;
                    }
                    c = c2;
                }
                n++;
                c3 = c2;
            } else if (car == 'D' || car == 'J') {
                n += 2;
                c3 = c;
            } else {
                n++;
                c3 = c;
            }
        }
        char car2 = desc.charAt(c);
        return (n << 2) | (car2 == 'V' ? 0 : (car2 == 'D' || car2 == 'J') ? 2 : 1);
    }

    private static Type getType(char[] buf, int off) {
        switch (buf[off]) {
            case 'B':
                return BYTE_TYPE;
            case 'C':
                return CHAR_TYPE;
            case 'D':
                return DOUBLE_TYPE;
            case 'F':
                return FLOAT_TYPE;
            case 'I':
                return INT_TYPE;
            case 'J':
                return LONG_TYPE;
            case 'S':
                return SHORT_TYPE;
            case 'V':
                return VOID_TYPE;
            case 'Z':
                return BOOLEAN_TYPE;
            case '[':
                int len = 1;
                while (buf[off + len] == '[') {
                    len++;
                }
                if (buf[off + len] == 'L') {
                    do {
                        len++;
                    } while (buf[off + len] != ';');
                }
                return new Type(9, buf, off, len + 1);
            default:
                int len2 = 1;
                while (buf[off + len2] != ';') {
                    len2++;
                }
                return new Type(10, buf, off + 1, len2 - 1);
        }
    }

    public String getInternalName() {
        return new String(this.buf, this.off, this.len);
    }

    String getDescriptor() {
        return new String(this.buf, this.off, this.len);
    }

    private int getDimensions() {
        int i = 1;
        while (this.buf[this.off + i] == '[') {
            i++;
        }
        return i;
    }

    static Type[] getArgumentTypes(String methodDescriptor) {
        int off;
        char[] buf = methodDescriptor.toCharArray();
        int off2 = 1;
        int size = 0;
        while (true) {
            int off3 = off2 + 1;
            char car = buf[off2];
            if (car == ')') {
                break;
            }
            if (car == 'L') {
                while (true) {
                    off = off3 + 1;
                    if (buf[off3] == ';') {
                        break;
                    }
                    off3 = off;
                }
                size++;
                off2 = off;
            } else if (car == '[') {
                off2 = off3;
            } else {
                size++;
                off2 = off3;
            }
        }
        Type[] args = new Type[size];
        int off4 = 1;
        int size2 = 0;
        while (buf[off4] != ')') {
            args[size2] = getType(buf, off4);
            off4 += args[size2].len + (args[size2].sort == 10 ? 2 : 0);
            size2++;
        }
        return args;
    }

    protected String getClassName() {
        switch (this.sort) {
            case 0:
                return "void";
            case 1:
                return "boolean";
            case 2:
                return "char";
            case 3:
                return "byte";
            case 4:
                return "short";
            case 5:
                return "int";
            case 6:
                return "float";
            case 7:
                return "long";
            case 8:
                return "double";
            case 9:
                Type elementType = getType(this.buf, this.off + getDimensions());
                StringBuilder b = new StringBuilder(elementType.getClassName());
                for (int i = getDimensions(); i > 0; i--) {
                    b.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                }
                return b.toString();
            default:
                return new String(this.buf, this.off, this.len).replace('/', '.');
        }
    }
}