package com.alibaba.fastjson;

import androidx.core.view.MotionEventCompat;
import com.alibaba.fastjson.asm.Opcodes;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/* loaded from: classes.dex */
public abstract class JSONValidator implements Cloneable, Closeable {
    protected char ch;
    protected boolean eof;
    protected Type type;
    private Boolean validateResult;
    protected int pos = -1;
    protected int count = 0;
    protected boolean supportMultiValue = false;

    public enum Type {
        Object,
        Array,
        Value
    }

    abstract void next();

    public static JSONValidator fromUtf8(byte[] jsonBytes) {
        return new UTF8Validator(jsonBytes);
    }

    public static JSONValidator fromUtf8(InputStream is) {
        return new UTF8InputStreamValidator(is);
    }

    public static JSONValidator from(String jsonStr) {
        return new UTF16Validator(jsonStr);
    }

    public static JSONValidator from(Reader r) {
        return new ReaderValidator(r);
    }

    public boolean isSupportMultiValue() {
        return this.supportMultiValue;
    }

    public JSONValidator setSupportMultiValue(boolean supportMultiValue) {
        this.supportMultiValue = supportMultiValue;
        return this;
    }

    public Type getType() {
        if (this.type == null) {
            validate();
        }
        return this.type;
    }

    public boolean validate() {
        if (this.validateResult != null) {
            return this.validateResult.booleanValue();
        }
        while (any()) {
            skipWhiteSpace();
            this.count++;
            if (this.eof) {
                this.validateResult = true;
                return true;
            }
            if (this.supportMultiValue) {
                skipWhiteSpace();
                if (this.eof) {
                    this.validateResult = true;
                    return true;
                }
            } else {
                this.validateResult = false;
                return false;
            }
        }
        this.validateResult = false;
        return false;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean any() {
        switch (this.ch) {
            case MotionEventCompat.AXIS_GENERIC_3 /* 34 */:
                next();
                while (!this.eof) {
                    if (this.ch == '\\') {
                        next();
                        if (this.ch == 'u') {
                            next();
                            next();
                            next();
                            next();
                            next();
                        } else {
                            next();
                        }
                    } else {
                        if (this.ch == '\"') {
                            next();
                            this.type = Type.Value;
                            return true;
                        }
                        next();
                    }
                }
                return false;
            case MotionEventCompat.AXIS_GENERIC_12 /* 43 */:
            case MotionEventCompat.AXIS_GENERIC_14 /* 45 */:
            case '0':
            case Opcodes.V1_5 /* 49 */:
            case '2':
            case '3':
            case '4':
            case '5':
            case Opcodes.ISTORE /* 54 */:
            case Opcodes.LSTORE /* 55 */:
            case Opcodes.FSTORE /* 56 */:
            case Opcodes.DSTORE /* 57 */:
                if (this.ch == '-' || this.ch == '+') {
                    next();
                    skipWhiteSpace();
                    if (this.ch < '0' || this.ch > '9') {
                        return false;
                    }
                }
                do {
                    next();
                    if (this.ch >= '0') {
                    }
                    if (this.ch == '.') {
                        next();
                        if (this.ch < '0' || this.ch > '9') {
                            return false;
                        }
                        while (this.ch >= '0' && this.ch <= '9') {
                            next();
                        }
                    }
                    if (this.ch != 'e' || this.ch == 'E') {
                        next();
                        if (this.ch != '-' || this.ch == '+') {
                            next();
                        }
                        if (this.ch >= '0' || this.ch > '9') {
                            return false;
                        }
                        next();
                        while (this.ch >= '0' && this.ch <= '9') {
                            next();
                        }
                    }
                    this.type = Type.Value;
                    return true;
                } while (this.ch <= '9');
                if (this.ch == '.') {
                }
                if (this.ch != 'e') {
                    next();
                    if (this.ch != '-') {
                        next();
                        if (this.ch >= '0') {
                        }
                        return false;
                    }
                }
                this.type = Type.Value;
                return true;
            case '[':
                next();
                skipWhiteSpace();
                if (this.ch == ']') {
                    next();
                    this.type = Type.Array;
                    return true;
                }
                while (any()) {
                    skipWhiteSpace();
                    if (this.ch == ',') {
                        next();
                        skipWhiteSpace();
                    } else {
                        if (this.ch != ']') {
                            return false;
                        }
                        next();
                        this.type = Type.Array;
                        return true;
                    }
                }
                return false;
            case 'f':
                next();
                if (this.ch != 'a') {
                    return false;
                }
                next();
                if (this.ch != 'l') {
                    return false;
                }
                next();
                if (this.ch != 's') {
                    return false;
                }
                next();
                if (this.ch != 'e') {
                    return false;
                }
                next();
                if (!isWhiteSpace(this.ch) && this.ch != ',' && this.ch != ']' && this.ch != '}' && this.ch != 0) {
                    return false;
                }
                this.type = Type.Value;
                return true;
            case 'n':
                next();
                if (this.ch != 'u') {
                    return false;
                }
                next();
                if (this.ch != 'l') {
                    return false;
                }
                next();
                if (this.ch != 'l') {
                    return false;
                }
                next();
                if (!isWhiteSpace(this.ch) && this.ch != ',' && this.ch != ']' && this.ch != '}' && this.ch != 0) {
                    return false;
                }
                this.type = Type.Value;
                return true;
            case 't':
                next();
                if (this.ch != 'r') {
                    return false;
                }
                next();
                if (this.ch != 'u') {
                    return false;
                }
                next();
                if (this.ch != 'e') {
                    return false;
                }
                next();
                if (!isWhiteSpace(this.ch) && this.ch != ',' && this.ch != ']' && this.ch != '}' && this.ch != 0) {
                    return false;
                }
                this.type = Type.Value;
                return true;
            case '{':
                next();
                while (isWhiteSpace(this.ch)) {
                    next();
                }
                if (this.ch == '}') {
                    next();
                    this.type = Type.Object;
                    return true;
                }
                while (this.ch == '\"') {
                    fieldName();
                    skipWhiteSpace();
                    if (this.ch != ':') {
                        return false;
                    }
                    next();
                    skipWhiteSpace();
                    if (!any()) {
                        return false;
                    }
                    skipWhiteSpace();
                    if (this.ch == ',') {
                        next();
                        skipWhiteSpace();
                    } else {
                        if (this.ch != '}') {
                            return false;
                        }
                        next();
                        this.type = Type.Object;
                        return true;
                    }
                }
                return false;
            default:
                return false;
        }
    }

    protected void fieldName() {
        next();
        while (true) {
            if (this.ch == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                    next();
                } else {
                    next();
                }
            } else {
                if (this.ch == '\"') {
                    next();
                    return;
                }
                next();
            }
        }
    }

    protected boolean string() {
        next();
        while (!this.eof) {
            if (this.ch == '\\') {
                next();
                if (this.ch == 'u') {
                    next();
                    next();
                    next();
                    next();
                    next();
                } else {
                    next();
                }
            } else {
                if (this.ch == '\"') {
                    next();
                    return true;
                }
                next();
            }
        }
        return false;
    }

    void skipWhiteSpace() {
        while (isWhiteSpace(this.ch)) {
            next();
        }
    }

    static final boolean isWhiteSpace(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n' || ch == '\f' || ch == '\b';
    }

    static class UTF8Validator extends JSONValidator {
        private final byte[] bytes;

        public UTF8Validator(byte[] bytes) {
            this.bytes = bytes;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            this.pos++;
            if (this.pos >= this.bytes.length) {
                this.ch = (char) 0;
                this.eof = true;
            } else {
                this.ch = (char) this.bytes[this.pos];
            }
        }
    }

    static class UTF8InputStreamValidator extends JSONValidator {
        private static final ThreadLocal<byte[]> bufLocal = new ThreadLocal<>();
        private byte[] buf;
        private final InputStream is;
        private int end = -1;
        private int readCount = 0;

        public UTF8InputStreamValidator(InputStream is) throws IOException {
            this.is = is;
            this.buf = bufLocal.get();
            if (this.buf != null) {
                bufLocal.set(null);
            } else {
                this.buf = new byte[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() throws IOException {
            if (this.pos < this.end) {
                byte[] bArr = this.buf;
                int i = this.pos + 1;
                this.pos = i;
                this.ch = (char) bArr[i];
                return;
            }
            if (!this.eof) {
                try {
                    int len = this.is.read(this.buf, 0, this.buf.length);
                    this.readCount++;
                    if (len > 0) {
                        this.ch = (char) this.buf[0];
                        this.pos = 0;
                        this.end = len - 1;
                    } else {
                        if (len == -1) {
                            this.pos = 0;
                            this.end = 0;
                            this.buf = null;
                            this.ch = (char) 0;
                            this.eof = true;
                            return;
                        }
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        throw new JSONException("read error");
                    }
                } catch (IOException e) {
                    throw new JSONException("read error");
                }
            }
        }

        @Override // com.alibaba.fastjson.JSONValidator, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.is.close();
        }
    }

    static class UTF16Validator extends JSONValidator {
        private final String str;

        public UTF16Validator(String str) {
            this.str = str;
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() {
            this.pos++;
            if (this.pos >= this.str.length()) {
                this.ch = (char) 0;
                this.eof = true;
            } else {
                this.ch = this.str.charAt(this.pos);
            }
        }

        @Override // com.alibaba.fastjson.JSONValidator
        protected final void fieldName() {
            char ch;
            int i = this.pos;
            do {
                i++;
                if (i >= this.str.length() || (ch = this.str.charAt(i)) == '\\') {
                    next();
                    while (true) {
                        if (this.ch == '\\') {
                            next();
                            if (this.ch == 'u') {
                                next();
                                next();
                                next();
                                next();
                                next();
                            } else {
                                next();
                            }
                        } else if (this.ch == '\"') {
                            next();
                            return;
                        } else if (!this.eof) {
                            next();
                        } else {
                            return;
                        }
                    }
                }
            } while (ch != '\"');
            this.ch = this.str.charAt(i + 1);
            this.pos = i + 1;
        }
    }

    static class ReaderValidator extends JSONValidator {
        private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
        private char[] buf;
        final Reader r;
        private int end = -1;
        private int readCount = 0;

        ReaderValidator(Reader r) throws IOException {
            this.r = r;
            this.buf = bufLocal.get();
            if (this.buf != null) {
                bufLocal.set(null);
            } else {
                this.buf = new char[8192];
            }
            next();
            skipWhiteSpace();
        }

        @Override // com.alibaba.fastjson.JSONValidator
        void next() throws IOException {
            if (this.pos < this.end) {
                char[] cArr = this.buf;
                int i = this.pos + 1;
                this.pos = i;
                this.ch = cArr[i];
                return;
            }
            if (!this.eof) {
                try {
                    int len = this.r.read(this.buf, 0, this.buf.length);
                    this.readCount++;
                    if (len > 0) {
                        this.ch = this.buf[0];
                        this.pos = 0;
                        this.end = len - 1;
                    } else {
                        if (len == -1) {
                            this.pos = 0;
                            this.end = 0;
                            this.buf = null;
                            this.ch = (char) 0;
                            this.eof = true;
                            return;
                        }
                        this.pos = 0;
                        this.end = 0;
                        this.buf = null;
                        this.ch = (char) 0;
                        this.eof = true;
                        throw new JSONException("read error");
                    }
                } catch (IOException e) {
                    throw new JSONException("read error");
                }
            }
        }

        @Override // com.alibaba.fastjson.JSONValidator, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            bufLocal.set(this.buf);
            this.r.close();
        }
    }
}