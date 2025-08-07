package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.RyuDouble;
import com.alibaba.fastjson.util.RyuFloat;
import com.tencent.bugly.Bugly;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import kotlin.UByte;
import kotlin.text.Typography;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public final class SerializeWriter extends Writer {
    private static int BUFFER_THRESHOLD;
    static final int nonDirectFeatures;
    protected boolean beanToArray;
    protected boolean browserSecure;
    protected char[] buf;
    protected int count;
    protected boolean disableCircularReferenceDetect;
    protected int features;
    protected char keySeperator;
    protected int maxBufSize;
    protected boolean notWriteDefaultValue;
    protected boolean quoteFieldNames;
    protected long sepcialBits;
    protected boolean sortField;
    protected boolean useSingleQuotes;
    protected boolean writeDirect;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected boolean writeNonStringValueAsString;
    private final Writer writer;
    private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
    private static final ThreadLocal<byte[]> bytesBufLocal = new ThreadLocal<>();
    private static final char[] VALUE_TRUE = ":true".toCharArray();
    private static final char[] VALUE_FALSE = ":false".toCharArray();

    static {
        int serializer_buffer_threshold;
        BUFFER_THRESHOLD = 131072;
        try {
            String prop = IOUtils.getStringProperty("fastjson.serializer_buffer_threshold");
            if (prop != null && prop.length() > 0 && (serializer_buffer_threshold = Integer.parseInt(prop)) >= 64 && serializer_buffer_threshold <= 65536) {
                BUFFER_THRESHOLD = serializer_buffer_threshold * 1024;
            }
        } catch (Throwable th) {
        }
        nonDirectFeatures = SerializerFeature.UseSingleQuotes.mask | 0 | SerializerFeature.BrowserCompatible.mask | SerializerFeature.PrettyFormat.mask | SerializerFeature.WriteEnumUsingToString.mask | SerializerFeature.WriteNonStringValueAsString.mask | SerializerFeature.WriteSlashAsSpecial.mask | SerializerFeature.IgnoreErrorGetter.mask | SerializerFeature.WriteClassName.mask | SerializerFeature.NotWriteDefaultValue.mask;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SerializeWriter() {
        this((Writer) null);
    }

    public SerializeWriter(Writer writer) {
        this(writer, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
    }

    public SerializeWriter(SerializerFeature... features) {
        this((Writer) null, features);
    }

    public SerializeWriter(Writer writer, SerializerFeature... features) {
        this(writer, 0, features);
    }

    public SerializeWriter(Writer writer, int defaultFeatures, SerializerFeature... features) {
        this.maxBufSize = -1;
        this.writer = writer;
        this.buf = bufLocal.get();
        if (this.buf != null) {
            bufLocal.set(null);
        } else {
            this.buf = new char[2048];
        }
        int featuresValue = defaultFeatures;
        for (SerializerFeature feature : features) {
            featuresValue |= feature.getMask();
        }
        this.features = featuresValue;
        computeFeatures();
    }

    public int getMaxBufSize() {
        return this.maxBufSize;
    }

    public void setMaxBufSize(int maxBufSize) {
        if (maxBufSize < this.buf.length) {
            throw new JSONException("must > " + this.buf.length);
        }
        this.maxBufSize = maxBufSize;
    }

    public int getBufferLength() {
        return this.buf.length;
    }

    public SerializeWriter(int initialSize) {
        this((Writer) null, initialSize);
    }

    public SerializeWriter(Writer writer, int initialSize) {
        this.maxBufSize = -1;
        this.writer = writer;
        if (initialSize <= 0) {
            throw new IllegalArgumentException("Negative initial size: " + initialSize);
        }
        this.buf = new char[initialSize];
        computeFeatures();
    }

    public void config(SerializerFeature feature, boolean state) {
        if (state) {
            this.features |= feature.getMask();
            if (feature == SerializerFeature.WriteEnumUsingToString) {
                this.features &= SerializerFeature.WriteEnumUsingName.getMask() ^ (-1);
            } else if (feature == SerializerFeature.WriteEnumUsingName) {
                this.features &= SerializerFeature.WriteEnumUsingToString.getMask() ^ (-1);
            }
        } else {
            this.features &= feature.getMask() ^ (-1);
        }
        computeFeatures();
    }

    protected void computeFeatures() {
        this.quoteFieldNames = (this.features & SerializerFeature.QuoteFieldNames.mask) != 0;
        this.useSingleQuotes = (this.features & SerializerFeature.UseSingleQuotes.mask) != 0;
        this.sortField = (this.features & SerializerFeature.SortField.mask) != 0;
        this.disableCircularReferenceDetect = (this.features & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
        this.beanToArray = (this.features & SerializerFeature.BeanToArray.mask) != 0;
        this.writeNonStringValueAsString = (this.features & SerializerFeature.WriteNonStringValueAsString.mask) != 0;
        this.notWriteDefaultValue = (this.features & SerializerFeature.NotWriteDefaultValue.mask) != 0;
        this.writeEnumUsingName = (this.features & SerializerFeature.WriteEnumUsingName.mask) != 0;
        this.writeEnumUsingToString = (this.features & SerializerFeature.WriteEnumUsingToString.mask) != 0;
        this.writeDirect = this.quoteFieldNames && (this.features & nonDirectFeatures) == 0 && (this.beanToArray || this.writeEnumUsingName);
        this.keySeperator = this.useSingleQuotes ? '\'' : Typography.quote;
        this.browserSecure = (this.features & SerializerFeature.BrowserSecure.mask) != 0;
        this.sepcialBits = this.browserSecure ? 5764610843043954687L : (this.features & SerializerFeature.WriteSlashAsSpecial.mask) != 0 ? 140758963191807L : 21474836479L;
    }

    public boolean isSortField() {
        return this.sortField;
    }

    public boolean isNotWriteDefaultValue() {
        return this.notWriteDefaultValue;
    }

    public boolean isEnabled(SerializerFeature feature) {
        return (this.features & feature.mask) != 0;
    }

    public boolean isEnabled(int feature) {
        return (this.features & feature) != 0;
    }

    @Override // java.io.Writer
    public void write(int c) {
        int newcount = this.count + 1;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                flush();
                newcount = 1;
            }
        }
        this.buf[this.count] = (char) c;
        this.count = newcount;
    }

    @Override // java.io.Writer
    public void write(char[] c, int off, int len) throws IOException {
        if (off < 0 || off > c.length || len < 0 || off + len > c.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return;
        }
        int newcount = this.count + len;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                do {
                    int rest = this.buf.length - this.count;
                    System.arraycopy(c, off, this.buf, this.count, rest);
                    this.count = this.buf.length;
                    flush();
                    len -= rest;
                    off += rest;
                } while (len > this.buf.length);
                newcount = len;
            }
        }
        System.arraycopy(c, off, this.buf, this.count, len);
        this.count = newcount;
    }

    public void expandCapacity(int minimumCapacity) {
        char[] charsLocal;
        if (this.maxBufSize != -1 && minimumCapacity >= this.maxBufSize) {
            throw new JSONException("serialize exceeded MAX_OUTPUT_LENGTH=" + this.maxBufSize + ", minimumCapacity=" + minimumCapacity);
        }
        int newCapacity = this.buf.length + (this.buf.length >> 1) + 1;
        if (newCapacity < minimumCapacity) {
            newCapacity = minimumCapacity;
        }
        char[] newValue = new char[newCapacity];
        System.arraycopy(this.buf, 0, newValue, 0, this.count);
        if (this.buf.length < BUFFER_THRESHOLD && ((charsLocal = bufLocal.get()) == null || charsLocal.length < this.buf.length)) {
            bufLocal.set(this.buf);
        }
        this.buf = newValue;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence csq) throws IOException {
        String s = csq == null ? "null" : csq.toString();
        write(s, 0, s.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence csq, int start, int end) throws IOException {
        String s = (csq == null ? "null" : csq).subSequence(start, end).toString();
        write(s, 0, s.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(char c) {
        write(c);
        return this;
    }

    @Override // java.io.Writer
    public void write(String str, int off, int len) throws IOException {
        int newcount = this.count + len;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                do {
                    int rest = this.buf.length - this.count;
                    str.getChars(off, off + rest, this.buf, this.count);
                    this.count = this.buf.length;
                    flush();
                    len -= rest;
                    off += rest;
                } while (len > this.buf.length);
                newcount = len;
            }
        }
        str.getChars(off, off + len, this.buf, this.count);
        this.count = newcount;
    }

    public void writeTo(Writer out) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        out.write(this.buf, 0, this.count);
    }

    public void writeTo(OutputStream out, String charsetName) throws IOException {
        writeTo(out, Charset.forName(charsetName));
    }

    public void writeTo(OutputStream out, Charset charset) throws IOException {
        writeToEx(out, charset);
    }

    public int writeToEx(OutputStream out, Charset charset) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (charset == IOUtils.UTF8) {
            return encodeToUTF8(out);
        }
        byte[] bytes = new String(this.buf, 0, this.count).getBytes(charset);
        out.write(bytes);
        return bytes.length;
    }

    public char[] toCharArray() {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        char[] newValue = new char[this.count];
        System.arraycopy(this.buf, 0, newValue, 0, this.count);
        return newValue;
    }

    public char[] toCharArrayForSpringWebSocket() {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        char[] newValue = new char[this.count - 2];
        System.arraycopy(this.buf, 1, newValue, 0, this.count - 2);
        return newValue;
    }

    public byte[] toBytes(String charsetName) {
        Charset charsetForName;
        if (charsetName == null || "UTF-8".equals(charsetName)) {
            charsetForName = IOUtils.UTF8;
        } else {
            charsetForName = Charset.forName(charsetName);
        }
        return toBytes(charsetForName);
    }

    public byte[] toBytes(Charset charset) {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (charset == IOUtils.UTF8) {
            return encodeToUTF8Bytes();
        }
        return new String(this.buf, 0, this.count).getBytes(charset);
    }

    private int encodeToUTF8(OutputStream out) throws IOException {
        double d = this.count;
        Double.isNaN(d);
        int bytesLength = (int) (d * 3.0d);
        byte[] bytes = bytesBufLocal.get();
        if (bytes == null) {
            bytes = new byte[8192];
            bytesBufLocal.set(bytes);
        }
        byte[] bytesLocal = bytes;
        if (bytes.length < bytesLength) {
            bytes = new byte[bytesLength];
        }
        int position = IOUtils.encodeUTF8(this.buf, 0, this.count, bytes);
        out.write(bytes, 0, position);
        if (bytes != bytesLocal && bytes.length <= BUFFER_THRESHOLD) {
            bytesBufLocal.set(bytes);
        }
        return position;
    }

    private byte[] encodeToUTF8Bytes() {
        double d = this.count;
        Double.isNaN(d);
        int bytesLength = (int) (d * 3.0d);
        byte[] bytes = bytesBufLocal.get();
        if (bytes == null) {
            bytes = new byte[8192];
            bytesBufLocal.set(bytes);
        }
        byte[] bytesLocal = bytes;
        if (bytes.length < bytesLength) {
            bytes = new byte[bytesLength];
        }
        int position = IOUtils.encodeUTF8(this.buf, 0, this.count, bytes);
        byte[] copy = new byte[position];
        System.arraycopy(bytes, 0, copy, 0, position);
        if (bytes != bytesLocal && bytes.length <= BUFFER_THRESHOLD) {
            bytesBufLocal.set(bytes);
        }
        return copy;
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        return new String(this.buf, 0, this.count);
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.writer != null && this.count > 0) {
            flush();
        }
        if (this.buf.length <= BUFFER_THRESHOLD) {
            bufLocal.set(this.buf);
        }
        this.buf = null;
    }

    @Override // java.io.Writer
    public void write(String text) {
        if (text == null) {
            writeNull();
        } else {
            write(text, 0, text.length());
        }
    }

    public void writeInt(int i) {
        if (i == Integer.MIN_VALUE) {
            write("-2147483648");
            return;
        }
        int size = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int newcount = this.count + size;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                char[] chars = new char[size];
                IOUtils.getChars(i, size, chars);
                write(chars, 0, chars.length);
                return;
            }
        }
        IOUtils.getChars(i, newcount, this.buf);
        this.count = newcount;
    }

    public void writeByteArray(byte[] bytes) {
        if (isEnabled(SerializerFeature.WriteClassName.mask)) {
            writeHex(bytes);
            return;
        }
        int bytesLen = bytes.length;
        char quote = this.useSingleQuotes ? '\'' : Typography.quote;
        if (bytesLen == 0) {
            String emptyString = this.useSingleQuotes ? "''" : "\"\"";
            write(emptyString);
            return;
        }
        char[] CA = IOUtils.CA;
        int eLen = (bytesLen / 3) * 3;
        int charsLen = (((bytesLen - 1) / 3) + 1) << 2;
        int offset = this.count;
        int newcount = this.count + charsLen + 2;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(quote);
                int i = 0;
                while (i < eLen) {
                    int s = i + 1;
                    int s2 = s + 1;
                    int i2 = ((bytes[i] & 255) << 16) | ((bytes[s] & 255) << 8) | (bytes[s2] & 255);
                    write(CA[(i2 >>> 18) & 63]);
                    write(CA[(i2 >>> 12) & 63]);
                    write(CA[(i2 >>> 6) & 63]);
                    write(CA[i2 & 63]);
                    i = s2 + 1;
                }
                int left = bytesLen - eLen;
                if (left > 0) {
                    int i3 = (left == 2 ? (bytes[bytesLen - 1] & UByte.MAX_VALUE) << 2 : 0) | ((bytes[eLen] & UByte.MAX_VALUE) << 10);
                    write(CA[i3 >> 12]);
                    write(CA[(i3 >>> 6) & 63]);
                    write(left == 2 ? CA[i3 & 63] : '=');
                    write(61);
                }
                write(quote);
                return;
            }
            expandCapacity(newcount);
        }
        this.count = newcount;
        int offset2 = offset + 1;
        this.buf[offset] = quote;
        int i4 = 0;
        int d = offset2;
        while (i4 < eLen) {
            int s3 = i4 + 1;
            int s4 = s3 + 1;
            int i5 = ((bytes[i4] & 255) << 16) | ((bytes[s3] & 255) << 8);
            int s5 = s4 + 1;
            int i6 = i5 | (bytes[s4] & 255);
            int d2 = d + 1;
            this.buf[d] = CA[(i6 >>> 18) & 63];
            int d3 = d2 + 1;
            this.buf[d2] = CA[(i6 >>> 12) & 63];
            int d4 = d3 + 1;
            this.buf[d3] = CA[(i6 >>> 6) & 63];
            this.buf[d4] = CA[i6 & 63];
            i4 = s5;
            d = d4 + 1;
        }
        int left2 = bytesLen - eLen;
        if (left2 > 0) {
            int i7 = ((bytes[eLen] & UByte.MAX_VALUE) << 10) | (left2 == 2 ? (bytes[bytesLen - 1] & UByte.MAX_VALUE) << 2 : 0);
            this.buf[newcount - 5] = CA[i7 >> 12];
            this.buf[newcount - 4] = CA[(i7 >>> 6) & 63];
            this.buf[newcount - 3] = left2 == 2 ? CA[i7 & 63] : '=';
            this.buf[newcount - 2] = '=';
        }
        this.buf[newcount - 1] = quote;
    }

    public void writeHex(byte[] bytes) {
        int newcount = this.count + (bytes.length * 2) + 3;
        if (newcount > this.buf.length) {
            expandCapacity(newcount);
        }
        char[] cArr = this.buf;
        int i = this.count;
        this.count = i + 1;
        cArr[i] = 'x';
        char[] cArr2 = this.buf;
        int i2 = this.count;
        this.count = i2 + 1;
        cArr2[i2] = '\'';
        for (byte b : bytes) {
            int a = b & UByte.MAX_VALUE;
            int b0 = a >> 4;
            int b1 = a & 15;
            char[] cArr3 = this.buf;
            int i3 = this.count;
            this.count = i3 + 1;
            int i4 = 48;
            cArr3[i3] = (char) ((b0 < 10 ? 48 : 55) + b0);
            char[] cArr4 = this.buf;
            int i5 = this.count;
            this.count = i5 + 1;
            if (b1 >= 10) {
                i4 = 55;
            }
            cArr4[i5] = (char) (i4 + b1);
        }
        char[] cArr5 = this.buf;
        int i6 = this.count;
        this.count = i6 + 1;
        cArr5[i6] = '\'';
    }

    public void writeFloat(float value, boolean checkWriteClassName) throws IOException {
        if (value != value || value == Float.POSITIVE_INFINITY || value == Float.NEGATIVE_INFINITY) {
            writeNull();
            return;
        }
        int newcount = this.count + 15;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                String str = RyuFloat.toString(value);
                write(str, 0, str.length());
                if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
                    write(70);
                    return;
                }
                return;
            }
        }
        int len = RyuFloat.toString(value, this.buf, this.count);
        this.count += len;
        if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
            write(70);
        }
    }

    public void writeDouble(double value, boolean checkWriteClassName) throws IOException {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            writeNull();
            return;
        }
        int newcount = this.count + 24;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                String str = RyuDouble.toString(value);
                write(str, 0, str.length());
                if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
                    write(68);
                    return;
                }
                return;
            }
        }
        int len = RyuDouble.toString(value, this.buf, this.count);
        this.count += len;
        if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
            write(68);
        }
    }

    public void writeEnum(Enum<?> value) {
        if (value == null) {
            writeNull();
            return;
        }
        String strVal = null;
        if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            strVal = value.name();
        } else if (this.writeEnumUsingToString) {
            strVal = value.toString();
        }
        if (strVal != null) {
            char quote = isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : Typography.quote;
            write(quote);
            write(strVal);
            write(quote);
            return;
        }
        writeInt(value.ordinal());
    }

    public void writeLongAndChar(long i, char c) throws IOException {
        writeLong(i);
        write(c);
    }

    public void writeLong(long i) {
        boolean needQuotationMark = isEnabled(SerializerFeature.BrowserCompatible) && !isEnabled(SerializerFeature.WriteClassName) && (i > 9007199254740991L || i < -9007199254740991L);
        if (i == Long.MIN_VALUE) {
            if (needQuotationMark) {
                write("\"-9223372036854775808\"");
                return;
            } else {
                write("-9223372036854775808");
                return;
            }
        }
        int size = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int newcount = this.count + size;
        if (needQuotationMark) {
            newcount += 2;
        }
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                char[] chars = new char[size];
                IOUtils.getChars(i, size, chars);
                if (needQuotationMark) {
                    write(34);
                    write(chars, 0, chars.length);
                    write(34);
                    return;
                }
                write(chars, 0, chars.length);
                return;
            }
        }
        if (needQuotationMark) {
            this.buf[this.count] = Typography.quote;
            IOUtils.getChars(i, newcount - 1, this.buf);
            this.buf[newcount - 1] = Typography.quote;
        } else {
            IOUtils.getChars(i, newcount, this.buf);
        }
        this.count = newcount;
    }

    public void writeNull() {
        write("null");
    }

    public void writeNull(SerializerFeature feature) {
        writeNull(0, feature.mask);
    }

    public void writeNull(int beanFeatures, int feature) {
        if ((beanFeatures & feature) == 0 && (this.features & feature) == 0) {
            writeNull();
            return;
        }
        if ((SerializerFeature.WriteMapNullValue.mask & beanFeatures) != 0 && ((SerializerFeature.WriteMapNullValue.mask ^ (-1)) & beanFeatures & SerializerFeature.WRITE_MAP_NULL_FEATURES) == 0) {
            writeNull();
            return;
        }
        if (feature == SerializerFeature.WriteNullListAsEmpty.mask) {
            write(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        if (feature == SerializerFeature.WriteNullStringAsEmpty.mask) {
            writeString("");
            return;
        }
        if (feature == SerializerFeature.WriteNullBooleanAsFalse.mask) {
            write(Bugly.SDK_IS_DEV);
        } else if (feature == SerializerFeature.WriteNullNumberAsZero.mask) {
            write(48);
        } else {
            writeNull();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:218:0x059d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x016d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeStringWithDoubleQuote(String text, char seperator) {
        char c;
        if (text == null) {
            writeNull();
            if (seperator != 0) {
                write(seperator);
                return;
            }
            return;
        }
        int len = text.length();
        int newcount = this.count + len + 2;
        if (seperator != 0) {
            newcount++;
        }
        int length = this.buf.length;
        char c2 = Typography.greater;
        char c3 = '\f';
        char c4 = '\b';
        if (newcount > length) {
            if (this.writer != null) {
                write(34);
                int i = 0;
                while (i < text.length()) {
                    char ch = text.charAt(i);
                    if (!isEnabled(SerializerFeature.BrowserSecure) || (ch != '(' && ch != ')' && ch != '<' && ch != c2)) {
                        if (isEnabled(SerializerFeature.BrowserCompatible)) {
                            if (ch == '\b' || ch == '\f' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '\"' || ch == '/' || ch == '\\') {
                                write(92);
                                write(IOUtils.replaceChars[ch]);
                            } else if (ch < ' ') {
                                write(92);
                                write(117);
                                write(48);
                                write(48);
                                write(IOUtils.ASCII_CHARS[ch * 2]);
                                write(IOUtils.ASCII_CHARS[(ch * 2) + 1]);
                            } else if (ch >= 127) {
                                write(92);
                                write(117);
                                write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                                write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                                write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                                write(IOUtils.DIGITS[ch & 15]);
                            } else {
                                write(ch);
                            }
                        } else if ((ch < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch] != 0) || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                            write(92);
                            if (IOUtils.specicalFlags_doubleQuotes[ch] != 4) {
                                write(IOUtils.replaceChars[ch]);
                            } else {
                                write(117);
                                write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                                write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                                write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                                write(IOUtils.DIGITS[ch & 15]);
                            }
                        }
                    } else {
                        write(92);
                        write(117);
                        write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                        write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                        write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                        write(IOUtils.DIGITS[ch & 15]);
                    }
                    i++;
                    c2 = Typography.greater;
                }
                write(34);
                if (seperator != 0) {
                    write(seperator);
                    return;
                }
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count + 1;
        int end = start + len;
        this.buf[this.count] = Typography.quote;
        text.getChars(0, len, this.buf, start);
        this.count = newcount;
        if (isEnabled(SerializerFeature.BrowserCompatible)) {
            int lastSpecialIndex = -1;
            for (int i2 = start; i2 < end; i2++) {
                char ch2 = this.buf[i2];
                if (ch2 == '\"' || ch2 == '/' || ch2 == '\\') {
                    lastSpecialIndex = i2;
                    newcount++;
                } else if (ch2 == '\b' || ch2 == '\f' || ch2 == '\n' || ch2 == '\r' || ch2 == '\t') {
                    lastSpecialIndex = i2;
                    newcount++;
                } else if (ch2 < ' ') {
                    lastSpecialIndex = i2;
                    newcount += 5;
                } else if (ch2 >= 127) {
                    lastSpecialIndex = i2;
                    newcount += 5;
                }
            }
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            this.count = newcount;
            int i3 = lastSpecialIndex;
            while (i3 >= start) {
                char ch3 = this.buf[i3];
                if (ch3 == c4 || ch3 == c3 || ch3 == '\n' || ch3 == '\r' || ch3 == '\t') {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 2, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = IOUtils.replaceChars[ch3];
                    end++;
                } else if (ch3 == '\"' || ch3 == '/' || ch3 == '\\') {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 2, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = ch3;
                    end++;
                } else if (ch3 < ' ') {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 6, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = 'u';
                    this.buf[i3 + 2] = '0';
                    this.buf[i3 + 3] = '0';
                    this.buf[i3 + 4] = IOUtils.ASCII_CHARS[ch3 * 2];
                    this.buf[i3 + 5] = IOUtils.ASCII_CHARS[(ch3 * 2) + 1];
                    end += 5;
                } else if (ch3 >= 127) {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 6, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = 'u';
                    this.buf[i3 + 2] = IOUtils.DIGITS[(ch3 >>> '\f') & 15];
                    this.buf[i3 + 3] = IOUtils.DIGITS[(ch3 >>> '\b') & 15];
                    this.buf[i3 + 4] = IOUtils.DIGITS[(ch3 >>> 4) & 15];
                    this.buf[i3 + 5] = IOUtils.DIGITS[ch3 & 15];
                    end += 5;
                }
                i3--;
                c3 = '\f';
                c4 = '\b';
            }
            if (seperator != 0) {
                this.buf[this.count - 2] = Typography.quote;
                this.buf[this.count - 1] = seperator;
                return;
            } else {
                this.buf[this.count - 1] = Typography.quote;
                return;
            }
        }
        int specialCount = 0;
        int lastSpecialIndex2 = -1;
        int firstSpecialIndex = -1;
        char lastSpecial = 0;
        int i4 = start;
        while (true) {
            c = 8232;
            if (i4 >= end) {
                break;
            }
            char ch4 = this.buf[i4];
            if (ch4 >= ']') {
                if (ch4 >= 127 && (ch4 == 8232 || ch4 == 8233 || ch4 < 160)) {
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i4;
                    }
                    specialCount++;
                    int lastSpecialIndex3 = i4;
                    newcount += 4;
                    lastSpecialIndex2 = lastSpecialIndex3;
                    lastSpecial = ch4;
                }
            } else {
                boolean special = (ch4 < '@' && (this.sepcialBits & (1 << ch4)) != 0) || ch4 == '\\';
                if (special) {
                    specialCount++;
                    int lastSpecialIndex4 = i4;
                    if (ch4 == '(' || ch4 == ')' || ch4 == '<' || ch4 == '>' || (ch4 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch4] == 4)) {
                        newcount += 4;
                    }
                    if (firstSpecialIndex == -1) {
                        lastSpecial = ch4;
                        firstSpecialIndex = i4;
                        lastSpecialIndex2 = lastSpecialIndex4;
                    } else {
                        lastSpecial = ch4;
                        lastSpecialIndex2 = lastSpecialIndex4;
                    }
                }
            }
            i4++;
        }
        if (specialCount > 0) {
            int newcount2 = newcount + specialCount;
            if (newcount2 > this.buf.length) {
                expandCapacity(newcount2);
            }
            this.count = newcount2;
            if (specialCount == 1) {
                if (lastSpecial == 8232) {
                    int srcPos = lastSpecialIndex2 + 1;
                    int destPos = lastSpecialIndex2 + 6;
                    int LengthOfCopy = (end - lastSpecialIndex2) - 1;
                    System.arraycopy(this.buf, srcPos, this.buf, destPos, LengthOfCopy);
                    this.buf[lastSpecialIndex2] = '\\';
                    int lastSpecialIndex5 = lastSpecialIndex2 + 1;
                    this.buf[lastSpecialIndex5] = 'u';
                    int lastSpecialIndex6 = lastSpecialIndex5 + 1;
                    this.buf[lastSpecialIndex6] = '2';
                    int lastSpecialIndex7 = lastSpecialIndex6 + 1;
                    this.buf[lastSpecialIndex7] = '0';
                    int lastSpecialIndex8 = lastSpecialIndex7 + 1;
                    this.buf[lastSpecialIndex8] = '2';
                    this.buf[lastSpecialIndex8 + 1] = '8';
                } else if (lastSpecial == 8233) {
                    int srcPos2 = lastSpecialIndex2 + 1;
                    int destPos2 = lastSpecialIndex2 + 6;
                    int LengthOfCopy2 = (end - lastSpecialIndex2) - 1;
                    System.arraycopy(this.buf, srcPos2, this.buf, destPos2, LengthOfCopy2);
                    this.buf[lastSpecialIndex2] = '\\';
                    int lastSpecialIndex9 = lastSpecialIndex2 + 1;
                    this.buf[lastSpecialIndex9] = 'u';
                    int lastSpecialIndex10 = lastSpecialIndex9 + 1;
                    this.buf[lastSpecialIndex10] = '2';
                    int lastSpecialIndex11 = lastSpecialIndex10 + 1;
                    this.buf[lastSpecialIndex11] = '0';
                    int lastSpecialIndex12 = lastSpecialIndex11 + 1;
                    this.buf[lastSpecialIndex12] = '2';
                    this.buf[lastSpecialIndex12 + 1] = '9';
                } else if (lastSpecial == '(' || lastSpecial == ')' || lastSpecial == '<' || lastSpecial == '>') {
                    int destPos3 = lastSpecialIndex2 + 6;
                    int LengthOfCopy3 = (end - lastSpecialIndex2) - 1;
                    System.arraycopy(this.buf, lastSpecialIndex2 + 1, this.buf, destPos3, LengthOfCopy3);
                    this.buf[lastSpecialIndex2] = '\\';
                    int lastSpecialIndex13 = lastSpecialIndex2 + 1;
                    this.buf[lastSpecialIndex13] = 'u';
                    char ch5 = lastSpecial;
                    int lastSpecialIndex14 = lastSpecialIndex13 + 1;
                    this.buf[lastSpecialIndex14] = IOUtils.DIGITS[(ch5 >>> '\f') & 15];
                    int lastSpecialIndex15 = lastSpecialIndex14 + 1;
                    this.buf[lastSpecialIndex15] = IOUtils.DIGITS[(ch5 >>> '\b') & 15];
                    int lastSpecialIndex16 = lastSpecialIndex15 + 1;
                    this.buf[lastSpecialIndex16] = IOUtils.DIGITS[(ch5 >>> 4) & 15];
                    this.buf[lastSpecialIndex16 + 1] = IOUtils.DIGITS[ch5 & 15];
                } else {
                    char ch6 = lastSpecial;
                    if (ch6 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch6] == 4) {
                        int srcPos3 = lastSpecialIndex2 + 1;
                        int destPos4 = lastSpecialIndex2 + 6;
                        int LengthOfCopy4 = (end - lastSpecialIndex2) - 1;
                        System.arraycopy(this.buf, srcPos3, this.buf, destPos4, LengthOfCopy4);
                        int bufIndex = lastSpecialIndex2;
                        int bufIndex2 = bufIndex + 1;
                        this.buf[bufIndex] = '\\';
                        int bufIndex3 = bufIndex2 + 1;
                        this.buf[bufIndex2] = 'u';
                        int bufIndex4 = bufIndex3 + 1;
                        this.buf[bufIndex3] = IOUtils.DIGITS[(ch6 >>> '\f') & 15];
                        int bufIndex5 = bufIndex4 + 1;
                        this.buf[bufIndex4] = IOUtils.DIGITS[(ch6 >>> '\b') & 15];
                        int bufIndex6 = bufIndex5 + 1;
                        this.buf[bufIndex5] = IOUtils.DIGITS[(ch6 >>> 4) & 15];
                        int i5 = bufIndex6 + 1;
                        this.buf[bufIndex6] = IOUtils.DIGITS[ch6 & 15];
                    } else {
                        int destPos5 = lastSpecialIndex2 + 2;
                        int LengthOfCopy5 = (end - lastSpecialIndex2) - 1;
                        System.arraycopy(this.buf, lastSpecialIndex2 + 1, this.buf, destPos5, LengthOfCopy5);
                        this.buf[lastSpecialIndex2] = '\\';
                        this.buf[lastSpecialIndex2 + 1] = IOUtils.replaceChars[ch6];
                    }
                }
            } else if (specialCount > 1) {
                int textIndex = firstSpecialIndex - start;
                int bufIndex7 = firstSpecialIndex;
                int i6 = textIndex;
                while (i6 < text.length()) {
                    char ch7 = text.charAt(i6);
                    if (this.browserSecure) {
                        if (ch7 != '(' && ch7 != ')' && ch7 != '<') {
                            if (ch7 == '>') {
                            }
                        }
                        int bufIndex8 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        int bufIndex9 = bufIndex8 + 1;
                        this.buf[bufIndex8] = 'u';
                        int bufIndex10 = bufIndex9 + 1;
                        this.buf[bufIndex9] = IOUtils.DIGITS[(ch7 >>> '\f') & 15];
                        int bufIndex11 = bufIndex10 + 1;
                        this.buf[bufIndex10] = IOUtils.DIGITS[(ch7 >>> '\b') & 15];
                        int bufIndex12 = bufIndex11 + 1;
                        this.buf[bufIndex11] = IOUtils.DIGITS[(ch7 >>> 4) & 15];
                        this.buf[bufIndex12] = IOUtils.DIGITS[ch7 & 15];
                        end += 5;
                        bufIndex7 = bufIndex12 + 1;
                    } else if ((ch7 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch7] != 0) || (ch7 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int bufIndex13 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[ch7] == 4) {
                            int bufIndex14 = bufIndex13 + 1;
                            this.buf[bufIndex13] = 'u';
                            int bufIndex15 = bufIndex14 + 1;
                            this.buf[bufIndex14] = IOUtils.DIGITS[(ch7 >>> '\f') & 15];
                            int bufIndex16 = bufIndex15 + 1;
                            this.buf[bufIndex15] = IOUtils.DIGITS[(ch7 >>> '\b') & 15];
                            int bufIndex17 = bufIndex16 + 1;
                            this.buf[bufIndex16] = IOUtils.DIGITS[(ch7 >>> 4) & 15];
                            this.buf[bufIndex17] = IOUtils.DIGITS[ch7 & 15];
                            end += 5;
                            bufIndex7 = bufIndex17 + 1;
                        } else {
                            this.buf[bufIndex13] = IOUtils.replaceChars[ch7];
                            end++;
                            bufIndex7 = bufIndex13 + 1;
                        }
                    } else if (ch7 == c || ch7 == 8233) {
                        int bufIndex18 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        int bufIndex19 = bufIndex18 + 1;
                        this.buf[bufIndex18] = 'u';
                        int bufIndex20 = bufIndex19 + 1;
                        this.buf[bufIndex19] = IOUtils.DIGITS[(ch7 >>> '\f') & 15];
                        int bufIndex21 = bufIndex20 + 1;
                        this.buf[bufIndex20] = IOUtils.DIGITS[(ch7 >>> '\b') & 15];
                        int bufIndex22 = bufIndex21 + 1;
                        this.buf[bufIndex21] = IOUtils.DIGITS[(ch7 >>> 4) & 15];
                        this.buf[bufIndex22] = IOUtils.DIGITS[ch7 & 15];
                        end += 5;
                        bufIndex7 = bufIndex22 + 1;
                    } else {
                        this.buf[bufIndex7] = ch7;
                        bufIndex7++;
                    }
                    i6++;
                    c = 8232;
                }
            }
        }
        if (seperator != 0) {
            this.buf[this.count - 2] = Typography.quote;
            this.buf[this.count - 1] = seperator;
        } else {
            this.buf[this.count - 1] = Typography.quote;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x0368 A[PHI: r25
  0x0368: PHI (r25v30 'lastSpecial' char) = (r25v28 'lastSpecial' char), (r25v31 'lastSpecial' char) binds: [B:152:0x0366, B:148:0x035f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0397 A[PHI: r25
  0x0397: PHI (r25v32 'lastSpecial' char) = (r25v29 'lastSpecial' char), (r25v33 'lastSpecial' char) binds: [B:155:0x036b, B:144:0x0348] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x059e  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0165  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeStringWithDoubleQuote(char[] text, char seperator) {
        char c;
        char lastSpecial;
        boolean special;
        if (text == null) {
            writeNull();
            if (seperator != 0) {
                write(seperator);
                return;
            }
            return;
        }
        int len = text.length;
        int newcount = this.count + len + 2;
        if (seperator != 0) {
            newcount++;
        }
        int length = this.buf.length;
        char c2 = Typography.greater;
        char c3 = '\f';
        char c4 = '\b';
        if (newcount > length) {
            if (this.writer != null) {
                write(34);
                int i = 0;
                while (i < text.length) {
                    char ch = text[i];
                    if (!isEnabled(SerializerFeature.BrowserSecure) || (ch != '(' && ch != ')' && ch != '<' && ch != c2)) {
                        if (isEnabled(SerializerFeature.BrowserCompatible)) {
                            if (ch == '\b' || ch == '\f' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '\"' || ch == '/' || ch == '\\') {
                                write(92);
                                write(IOUtils.replaceChars[ch]);
                            } else if (ch < ' ') {
                                write(92);
                                write(117);
                                write(48);
                                write(48);
                                write(IOUtils.ASCII_CHARS[ch * 2]);
                                write(IOUtils.ASCII_CHARS[(ch * 2) + 1]);
                            } else if (ch >= 127) {
                                write(92);
                                write(117);
                                write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                                write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                                write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                                write(IOUtils.DIGITS[ch & 15]);
                            } else {
                                write(ch);
                            }
                        } else if ((ch < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch] != 0) || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                            write(92);
                            if (IOUtils.specicalFlags_doubleQuotes[ch] != 4) {
                                write(IOUtils.replaceChars[ch]);
                            } else {
                                write(117);
                                write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                                write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                                write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                                write(IOUtils.DIGITS[ch & 15]);
                            }
                        }
                    } else {
                        write(92);
                        write(117);
                        write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                        write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                        write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                        write(IOUtils.DIGITS[ch & 15]);
                    }
                    i++;
                    c2 = Typography.greater;
                }
                write(34);
                if (seperator != 0) {
                    write(seperator);
                    return;
                }
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count + 1;
        int end = start + len;
        this.buf[this.count] = Typography.quote;
        System.arraycopy(text, 0, this.buf, start, text.length);
        this.count = newcount;
        if (isEnabled(SerializerFeature.BrowserCompatible)) {
            int lastSpecialIndex = -1;
            for (int i2 = start; i2 < end; i2++) {
                char ch2 = this.buf[i2];
                if (ch2 == '\"' || ch2 == '/' || ch2 == '\\') {
                    lastSpecialIndex = i2;
                    newcount++;
                } else if (ch2 == '\b' || ch2 == '\f' || ch2 == '\n' || ch2 == '\r' || ch2 == '\t') {
                    lastSpecialIndex = i2;
                    newcount++;
                } else if (ch2 < ' ') {
                    lastSpecialIndex = i2;
                    newcount += 5;
                } else if (ch2 >= 127) {
                    lastSpecialIndex = i2;
                    newcount += 5;
                }
            }
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            this.count = newcount;
            int i3 = lastSpecialIndex;
            while (i3 >= start) {
                char ch3 = this.buf[i3];
                if (ch3 == c4 || ch3 == c3 || ch3 == '\n' || ch3 == '\r' || ch3 == '\t') {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 2, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = IOUtils.replaceChars[ch3];
                    end++;
                } else if (ch3 == '\"' || ch3 == '/' || ch3 == '\\') {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 2, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = ch3;
                    end++;
                } else if (ch3 < ' ') {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 6, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = 'u';
                    this.buf[i3 + 2] = '0';
                    this.buf[i3 + 3] = '0';
                    this.buf[i3 + 4] = IOUtils.ASCII_CHARS[ch3 * 2];
                    this.buf[i3 + 5] = IOUtils.ASCII_CHARS[(ch3 * 2) + 1];
                    end += 5;
                } else if (ch3 >= 127) {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 6, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = 'u';
                    this.buf[i3 + 2] = IOUtils.DIGITS[(ch3 >>> '\f') & 15];
                    this.buf[i3 + 3] = IOUtils.DIGITS[(ch3 >>> '\b') & 15];
                    this.buf[i3 + 4] = IOUtils.DIGITS[(ch3 >>> 4) & 15];
                    this.buf[i3 + 5] = IOUtils.DIGITS[ch3 & 15];
                    end += 5;
                }
                i3--;
                c3 = '\f';
                c4 = '\b';
            }
            if (seperator != 0) {
                this.buf[this.count - 2] = Typography.quote;
                this.buf[this.count - 1] = seperator;
                return;
            } else {
                this.buf[this.count - 1] = Typography.quote;
                return;
            }
        }
        int specialCount = 0;
        int lastSpecialIndex2 = -1;
        int firstSpecialIndex = -1;
        char lastSpecial2 = 0;
        int i4 = start;
        while (true) {
            c = 8232;
            if (i4 >= end) {
                break;
            }
            char ch4 = this.buf[i4];
            if (ch4 >= ']') {
                if (ch4 < 127 || (ch4 != 8232 && ch4 != 8233 && ch4 >= 160)) {
                    lastSpecial = lastSpecial2;
                    lastSpecial2 = lastSpecial;
                } else {
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i4;
                    }
                    specialCount++;
                    int lastSpecialIndex3 = i4;
                    newcount += 4;
                    lastSpecial2 = ch4;
                    lastSpecialIndex2 = lastSpecialIndex3;
                }
            } else {
                if (ch4 < '@') {
                    lastSpecial = lastSpecial2;
                    if ((this.sepcialBits & (1 << ch4)) != 0) {
                        special = true;
                        if (special) {
                            lastSpecial2 = lastSpecial;
                        } else {
                            specialCount++;
                            lastSpecialIndex2 = i4;
                            if (ch4 == '(' || ch4 == ')' || ch4 == '<' || ch4 == '>' || (ch4 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch4] == 4)) {
                                newcount += 4;
                            }
                            if (firstSpecialIndex != -1) {
                                lastSpecial2 = ch4;
                            } else {
                                firstSpecialIndex = i4;
                                lastSpecial2 = ch4;
                            }
                        }
                    }
                } else {
                    lastSpecial = lastSpecial2;
                }
                if (ch4 != '\\') {
                    special = false;
                }
                if (special) {
                }
            }
            i4++;
        }
        char lastSpecial3 = lastSpecial2;
        if (specialCount > 0) {
            int newcount2 = newcount + specialCount;
            if (newcount2 > this.buf.length) {
                expandCapacity(newcount2);
            }
            this.count = newcount2;
            if (specialCount == 1) {
                if (lastSpecial3 != 8232) {
                    if (lastSpecial3 != 8233) {
                        if (lastSpecial3 == '(' || lastSpecial3 == ')' || lastSpecial3 == '<' || lastSpecial3 == '>') {
                            int destPos = lastSpecialIndex2 + 6;
                            int LengthOfCopy = (end - lastSpecialIndex2) - 1;
                            System.arraycopy(this.buf, lastSpecialIndex2 + 1, this.buf, destPos, LengthOfCopy);
                            this.buf[lastSpecialIndex2] = '\\';
                            int lastSpecialIndex4 = lastSpecialIndex2 + 1;
                            this.buf[lastSpecialIndex4] = 'u';
                            int lastSpecialIndex5 = lastSpecialIndex4 + 1;
                            this.buf[lastSpecialIndex5] = IOUtils.DIGITS[(lastSpecial3 >>> '\f') & 15];
                            int lastSpecialIndex6 = lastSpecialIndex5 + 1;
                            this.buf[lastSpecialIndex6] = IOUtils.DIGITS[(lastSpecial3 >>> '\b') & 15];
                            int lastSpecialIndex7 = lastSpecialIndex6 + 1;
                            this.buf[lastSpecialIndex7] = IOUtils.DIGITS[(lastSpecial3 >>> 4) & 15];
                            this.buf[lastSpecialIndex7 + 1] = IOUtils.DIGITS[lastSpecial3 & 15];
                        } else if (lastSpecial3 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[lastSpecial3] == 4) {
                            int srcPos = lastSpecialIndex2 + 1;
                            int destPos2 = lastSpecialIndex2 + 6;
                            int LengthOfCopy2 = (end - lastSpecialIndex2) - 1;
                            System.arraycopy(this.buf, srcPos, this.buf, destPos2, LengthOfCopy2);
                            int bufIndex = lastSpecialIndex2;
                            int bufIndex2 = bufIndex + 1;
                            this.buf[bufIndex] = '\\';
                            int bufIndex3 = bufIndex2 + 1;
                            this.buf[bufIndex2] = 'u';
                            int bufIndex4 = bufIndex3 + 1;
                            this.buf[bufIndex3] = IOUtils.DIGITS[(lastSpecial3 >>> '\f') & 15];
                            int bufIndex5 = bufIndex4 + 1;
                            this.buf[bufIndex4] = IOUtils.DIGITS[(lastSpecial3 >>> '\b') & 15];
                            int bufIndex6 = bufIndex5 + 1;
                            this.buf[bufIndex5] = IOUtils.DIGITS[(lastSpecial3 >>> 4) & 15];
                            int i5 = bufIndex6 + 1;
                            this.buf[bufIndex6] = IOUtils.DIGITS[lastSpecial3 & 15];
                        } else {
                            int destPos3 = lastSpecialIndex2 + 2;
                            int LengthOfCopy3 = (end - lastSpecialIndex2) - 1;
                            System.arraycopy(this.buf, lastSpecialIndex2 + 1, this.buf, destPos3, LengthOfCopy3);
                            this.buf[lastSpecialIndex2] = '\\';
                            this.buf[lastSpecialIndex2 + 1] = IOUtils.replaceChars[lastSpecial3];
                        }
                    } else {
                        int srcPos2 = lastSpecialIndex2 + 1;
                        int destPos4 = lastSpecialIndex2 + 6;
                        int LengthOfCopy4 = (end - lastSpecialIndex2) - 1;
                        System.arraycopy(this.buf, srcPos2, this.buf, destPos4, LengthOfCopy4);
                        this.buf[lastSpecialIndex2] = '\\';
                        int lastSpecialIndex8 = lastSpecialIndex2 + 1;
                        this.buf[lastSpecialIndex8] = 'u';
                        int lastSpecialIndex9 = lastSpecialIndex8 + 1;
                        this.buf[lastSpecialIndex9] = '2';
                        int lastSpecialIndex10 = lastSpecialIndex9 + 1;
                        this.buf[lastSpecialIndex10] = '0';
                        int lastSpecialIndex11 = lastSpecialIndex10 + 1;
                        this.buf[lastSpecialIndex11] = '2';
                        this.buf[lastSpecialIndex11 + 1] = '9';
                    }
                } else {
                    int srcPos3 = lastSpecialIndex2 + 1;
                    int destPos5 = lastSpecialIndex2 + 6;
                    int LengthOfCopy5 = (end - lastSpecialIndex2) - 1;
                    System.arraycopy(this.buf, srcPos3, this.buf, destPos5, LengthOfCopy5);
                    this.buf[lastSpecialIndex2] = '\\';
                    int lastSpecialIndex12 = lastSpecialIndex2 + 1;
                    this.buf[lastSpecialIndex12] = 'u';
                    int lastSpecialIndex13 = lastSpecialIndex12 + 1;
                    this.buf[lastSpecialIndex13] = '2';
                    int lastSpecialIndex14 = lastSpecialIndex13 + 1;
                    this.buf[lastSpecialIndex14] = '0';
                    int lastSpecialIndex15 = lastSpecialIndex14 + 1;
                    this.buf[lastSpecialIndex15] = '2';
                    this.buf[lastSpecialIndex15 + 1] = '8';
                }
            } else if (specialCount > 1) {
                int textIndex = firstSpecialIndex - start;
                int bufIndex7 = firstSpecialIndex;
                int i6 = textIndex;
                while (i6 < text.length) {
                    char ch5 = text[i6];
                    if (this.browserSecure) {
                        if (ch5 != '(' && ch5 != ')' && ch5 != '<') {
                            if (ch5 == '>') {
                            }
                        }
                        int bufIndex8 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        int bufIndex9 = bufIndex8 + 1;
                        this.buf[bufIndex8] = 'u';
                        int bufIndex10 = bufIndex9 + 1;
                        this.buf[bufIndex9] = IOUtils.DIGITS[(ch5 >>> '\f') & 15];
                        int bufIndex11 = bufIndex10 + 1;
                        this.buf[bufIndex10] = IOUtils.DIGITS[(ch5 >>> '\b') & 15];
                        int bufIndex12 = bufIndex11 + 1;
                        this.buf[bufIndex11] = IOUtils.DIGITS[(ch5 >>> 4) & 15];
                        this.buf[bufIndex12] = IOUtils.DIGITS[ch5 & 15];
                        end += 5;
                        bufIndex7 = bufIndex12 + 1;
                    } else if ((ch5 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch5] != 0) || (ch5 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int bufIndex13 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[ch5] == 4) {
                            int bufIndex14 = bufIndex13 + 1;
                            this.buf[bufIndex13] = 'u';
                            int bufIndex15 = bufIndex14 + 1;
                            this.buf[bufIndex14] = IOUtils.DIGITS[(ch5 >>> '\f') & 15];
                            int bufIndex16 = bufIndex15 + 1;
                            this.buf[bufIndex15] = IOUtils.DIGITS[(ch5 >>> '\b') & 15];
                            int bufIndex17 = bufIndex16 + 1;
                            this.buf[bufIndex16] = IOUtils.DIGITS[(ch5 >>> 4) & 15];
                            this.buf[bufIndex17] = IOUtils.DIGITS[ch5 & 15];
                            end += 5;
                            bufIndex7 = bufIndex17 + 1;
                        } else {
                            this.buf[bufIndex13] = IOUtils.replaceChars[ch5];
                            end++;
                            bufIndex7 = bufIndex13 + 1;
                        }
                    } else if (ch5 == c || ch5 == 8233) {
                        int bufIndex18 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        int bufIndex19 = bufIndex18 + 1;
                        this.buf[bufIndex18] = 'u';
                        int bufIndex20 = bufIndex19 + 1;
                        this.buf[bufIndex19] = IOUtils.DIGITS[(ch5 >>> '\f') & 15];
                        int bufIndex21 = bufIndex20 + 1;
                        this.buf[bufIndex20] = IOUtils.DIGITS[(ch5 >>> '\b') & 15];
                        int bufIndex22 = bufIndex21 + 1;
                        this.buf[bufIndex21] = IOUtils.DIGITS[(ch5 >>> 4) & 15];
                        bufIndex7 = bufIndex22 + 1;
                        this.buf[bufIndex22] = IOUtils.DIGITS[ch5 & 15];
                        end += 5;
                    } else {
                        this.buf[bufIndex7] = ch5;
                        bufIndex7++;
                    }
                    i6++;
                    c = 8232;
                }
            }
        }
        if (seperator != 0) {
            this.buf[this.count - 2] = Typography.quote;
            this.buf[this.count - 1] = seperator;
        } else {
            this.buf[this.count - 1] = Typography.quote;
        }
    }

    public void writeFieldNameDirect(String text) {
        int len = text.length();
        int newcount = this.count + len + 3;
        if (newcount > this.buf.length) {
            expandCapacity(newcount);
        }
        int start = this.count + 1;
        this.buf[this.count] = Typography.quote;
        text.getChars(0, len, this.buf, start);
        this.count = newcount;
        this.buf[this.count - 2] = Typography.quote;
        this.buf[this.count - 1] = ':';
    }

    public void write(List<String> list) {
        int offset;
        if (list.isEmpty()) {
            write(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        int offset2 = this.count;
        int i = 0;
        int list_size = list.size();
        while (i < list_size) {
            String text = list.get(i);
            boolean hasSpecial = false;
            if (text == null) {
                hasSpecial = true;
            } else {
                int len = text.length();
                for (int j = 0; j < len; j++) {
                    char ch = text.charAt(j);
                    boolean z = ch < ' ' || ch > '~' || ch == '\"' || ch == '\\';
                    hasSpecial = z;
                    if (z) {
                        break;
                    }
                }
            }
            if (hasSpecial) {
                this.count = offset2;
                write(91);
                for (int j2 = 0; j2 < list.size(); j2++) {
                    String text2 = list.get(j2);
                    if (j2 != 0) {
                        write(44);
                    }
                    if (text2 == null) {
                        write("null");
                    } else {
                        writeStringWithDoubleQuote(text2, (char) 0);
                    }
                }
                write(93);
                return;
            }
            int newcount = text.length() + offset2 + 3;
            if (i == list.size() - 1) {
                newcount++;
            }
            if (newcount > this.buf.length) {
                this.count = offset2;
                expandCapacity(newcount);
            }
            if (i != 0) {
                this.buf[offset2] = ',';
                offset = offset2 + 1;
            } else {
                offset = offset2 + 1;
                this.buf[offset2] = '[';
            }
            int offset3 = offset + 1;
            this.buf[offset] = Typography.quote;
            text.getChars(0, text.length(), this.buf, offset3);
            int offset4 = offset3 + text.length();
            this.buf[offset4] = Typography.quote;
            i++;
            offset2 = offset4 + 1;
        }
        this.buf[offset2] = ']';
        this.count = offset2 + 1;
    }

    public void writeFieldValue(char seperator, String name, char value) {
        write(seperator);
        writeFieldName(name);
        if (value == 0) {
            writeString("\u0000");
        } else {
            writeString(Character.toString(value));
        }
    }

    public void writeFieldValue(char seperator, String name, boolean value) {
        if (!this.quoteFieldNames) {
            write(seperator);
            writeFieldName(name);
            write(value);
            return;
        }
        int intSize = value ? 4 : 5;
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeString(name);
                write(58);
                write(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        this.buf[start] = seperator;
        int nameEnd = start + nameLen + 1;
        this.buf[start + 1] = this.keySeperator;
        name.getChars(0, nameLen, this.buf, start + 2);
        this.buf[nameEnd + 1] = this.keySeperator;
        if (value) {
            System.arraycopy(VALUE_TRUE, 0, this.buf, nameEnd + 2, 5);
        } else {
            System.arraycopy(VALUE_FALSE, 0, this.buf, nameEnd + 2, 6);
        }
    }

    public void write(boolean value) {
        if (value) {
            write("true");
        } else {
            write(Bugly.SDK_IS_DEV);
        }
    }

    public void writeFieldValue(char seperator, String name, int value) {
        if (value == Integer.MIN_VALUE || !this.quoteFieldNames) {
            write(seperator);
            writeFieldName(name);
            writeInt(value);
            return;
        }
        int intSize = value < 0 ? IOUtils.stringSize(-value) + 1 : IOUtils.stringSize(value);
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeFieldName(name);
                writeInt(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        this.buf[start] = seperator;
        int nameEnd = start + nameLen + 1;
        this.buf[start + 1] = this.keySeperator;
        name.getChars(0, nameLen, this.buf, start + 2);
        this.buf[nameEnd + 1] = this.keySeperator;
        this.buf[nameEnd + 2] = ':';
        IOUtils.getChars(value, this.count, this.buf);
    }

    public void writeFieldValue(char seperator, String name, long value) {
        if (value == Long.MIN_VALUE || !this.quoteFieldNames || isEnabled(SerializerFeature.BrowserCompatible.mask)) {
            write(seperator);
            writeFieldName(name);
            writeLong(value);
            return;
        }
        int intSize = value < 0 ? IOUtils.stringSize(-value) + 1 : IOUtils.stringSize(value);
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeFieldName(name);
                writeLong(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        this.buf[start] = seperator;
        int nameEnd = start + nameLen + 1;
        this.buf[start + 1] = this.keySeperator;
        name.getChars(0, nameLen, this.buf, start + 2);
        this.buf[nameEnd + 1] = this.keySeperator;
        this.buf[nameEnd + 2] = ':';
        IOUtils.getChars(value, this.count, this.buf);
    }

    public void writeFieldValue(char seperator, String name, float value) throws IOException {
        write(seperator);
        writeFieldName(name);
        writeFloat(value, false);
    }

    public void writeFieldValue(char seperator, String name, double value) throws IOException {
        write(seperator);
        writeFieldName(name);
        writeDouble(value, false);
    }

    public void writeFieldValue(char seperator, String name, String value) {
        if (this.quoteFieldNames) {
            if (this.useSingleQuotes) {
                write(seperator);
                writeFieldName(name);
                if (value == null) {
                    writeNull();
                    return;
                } else {
                    writeString(value);
                    return;
                }
            }
            if (isEnabled(SerializerFeature.BrowserCompatible)) {
                write(seperator);
                writeStringWithDoubleQuote(name, ':');
                writeStringWithDoubleQuote(value, (char) 0);
                return;
            }
            writeFieldValueStringWithDoubleQuoteCheck(seperator, name, value);
            return;
        }
        write(seperator);
        writeFieldName(name);
        if (value == null) {
            writeNull();
        } else {
            writeString(value);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00e6 A[PHI: r28
  0x00e6: PHI (r28v10 'newcount' int) = (r28v6 'newcount' int), (r28v11 'newcount' int) binds: [B:40:0x00e4, B:36:0x00dd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0126  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeFieldValueStringWithDoubleQuoteCheck(char seperator, String name, String value) {
        int valueLen;
        int newcount;
        int newcount2;
        int newcount3;
        boolean special;
        int nameLen = name.length();
        int newcount4 = this.count;
        if (value == null) {
            valueLen = 4;
            newcount = newcount4 + nameLen + 8;
        } else {
            valueLen = value.length();
            newcount = newcount4 + nameLen + valueLen + 6;
        }
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeStringWithDoubleQuote(name, ':');
                writeStringWithDoubleQuote(value, (char) 0);
                return;
            }
            expandCapacity(newcount);
        }
        this.buf[this.count] = seperator;
        int nameStart = this.count + 2;
        int nameEnd = nameStart + nameLen;
        this.buf[this.count + 1] = Typography.quote;
        name.getChars(0, nameLen, this.buf, nameStart);
        this.count = newcount;
        this.buf[nameEnd] = Typography.quote;
        int index = nameEnd + 1;
        int index2 = index + 1;
        this.buf[index] = ':';
        if (value == null) {
            int index3 = index2 + 1;
            this.buf[index2] = 'n';
            int index4 = index3 + 1;
            this.buf[index3] = 'u';
            int index5 = index4 + 1;
            this.buf[index4] = 'l';
            int i = index5 + 1;
            this.buf[index5] = 'l';
            return;
        }
        int index6 = index2 + 1;
        this.buf[index2] = Typography.quote;
        int valueEnd = index6 + valueLen;
        value.getChars(0, valueLen, this.buf, index6);
        int specialCount = 0;
        int lastSpecialIndex = -1;
        int firstSpecialIndex = -1;
        char lastSpecial = 0;
        int i2 = index6;
        while (i2 < valueEnd) {
            char ch = this.buf[i2];
            int nameLen2 = nameLen;
            if (ch >= ']') {
                if (ch < 127 || (ch != 8232 && ch != 8233 && ch >= 160)) {
                    newcount3 = newcount;
                    newcount = newcount3;
                } else {
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i2;
                    }
                    specialCount++;
                    int lastSpecialIndex2 = i2;
                    newcount += 4;
                    lastSpecialIndex = lastSpecialIndex2;
                    lastSpecial = ch;
                }
            } else {
                if (ch < '@') {
                    newcount3 = newcount;
                    if ((this.sepcialBits & (1 << ch)) != 0) {
                        special = true;
                        if (special) {
                            newcount = newcount3;
                        } else {
                            specialCount++;
                            int lastSpecialIndex3 = i2;
                            lastSpecial = ch;
                            if (ch != '(' && ch != ')' && ch != '<' && ch != '>') {
                                if (ch < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch] == 4) {
                                }
                                if (firstSpecialIndex == -1) {
                                    lastSpecialIndex = lastSpecialIndex3;
                                    newcount = newcount3;
                                } else {
                                    firstSpecialIndex = i2;
                                    lastSpecialIndex = lastSpecialIndex3;
                                    newcount = newcount3;
                                }
                            }
                            newcount3 += 4;
                            if (firstSpecialIndex == -1) {
                            }
                        }
                    }
                } else {
                    newcount3 = newcount;
                }
                if (ch != '\\') {
                    special = false;
                }
                if (special) {
                }
            }
            i2++;
            nameLen = nameLen2;
        }
        int newcount5 = newcount;
        if (specialCount > 0) {
            int newcount6 = newcount5 + specialCount;
            if (newcount6 > this.buf.length) {
                expandCapacity(newcount6);
            }
            this.count = newcount6;
            if (specialCount == 1) {
                if (lastSpecial == 8232) {
                    int srcPos = lastSpecialIndex + 1;
                    int destPos = lastSpecialIndex + 6;
                    int LengthOfCopy = (valueEnd - lastSpecialIndex) - 1;
                    System.arraycopy(this.buf, srcPos, this.buf, destPos, LengthOfCopy);
                    this.buf[lastSpecialIndex] = '\\';
                    int lastSpecialIndex4 = lastSpecialIndex + 1;
                    this.buf[lastSpecialIndex4] = 'u';
                    int lastSpecialIndex5 = lastSpecialIndex4 + 1;
                    this.buf[lastSpecialIndex5] = '2';
                    int lastSpecialIndex6 = lastSpecialIndex5 + 1;
                    this.buf[lastSpecialIndex6] = '0';
                    int lastSpecialIndex7 = lastSpecialIndex6 + 1;
                    this.buf[lastSpecialIndex7] = '2';
                    this.buf[lastSpecialIndex7 + 1] = '8';
                } else {
                    newcount2 = newcount6;
                    if (lastSpecial == 8233) {
                        int srcPos2 = lastSpecialIndex + 1;
                        int destPos2 = lastSpecialIndex + 6;
                        int LengthOfCopy2 = (valueEnd - lastSpecialIndex) - 1;
                        System.arraycopy(this.buf, srcPos2, this.buf, destPos2, LengthOfCopy2);
                        this.buf[lastSpecialIndex] = '\\';
                        int lastSpecialIndex8 = lastSpecialIndex + 1;
                        this.buf[lastSpecialIndex8] = 'u';
                        int lastSpecialIndex9 = lastSpecialIndex8 + 1;
                        this.buf[lastSpecialIndex9] = '2';
                        int lastSpecialIndex10 = lastSpecialIndex9 + 1;
                        this.buf[lastSpecialIndex10] = '0';
                        int lastSpecialIndex11 = lastSpecialIndex10 + 1;
                        this.buf[lastSpecialIndex11] = '2';
                        this.buf[lastSpecialIndex11 + 1] = '9';
                    } else if (lastSpecial == '(' || lastSpecial == ')' || lastSpecial == '<' || lastSpecial == '>') {
                        char ch2 = lastSpecial;
                        int srcPos3 = lastSpecialIndex + 1;
                        int destPos3 = lastSpecialIndex + 6;
                        int LengthOfCopy3 = (valueEnd - lastSpecialIndex) - 1;
                        System.arraycopy(this.buf, srcPos3, this.buf, destPos3, LengthOfCopy3);
                        int bufIndex = lastSpecialIndex;
                        int bufIndex2 = bufIndex + 1;
                        this.buf[bufIndex] = '\\';
                        int bufIndex3 = bufIndex2 + 1;
                        this.buf[bufIndex2] = 'u';
                        int bufIndex4 = bufIndex3 + 1;
                        this.buf[bufIndex3] = IOUtils.DIGITS[(ch2 >>> '\f') & 15];
                        int bufIndex5 = bufIndex4 + 1;
                        this.buf[bufIndex4] = IOUtils.DIGITS[(ch2 >>> '\b') & 15];
                        int bufIndex6 = bufIndex5 + 1;
                        this.buf[bufIndex5] = IOUtils.DIGITS[(ch2 >>> 4) & 15];
                        int i3 = bufIndex6 + 1;
                        this.buf[bufIndex6] = IOUtils.DIGITS[ch2 & 15];
                    } else {
                        char ch3 = lastSpecial;
                        if (ch3 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch3] == 4) {
                            int srcPos4 = lastSpecialIndex + 1;
                            int destPos4 = lastSpecialIndex + 6;
                            int LengthOfCopy4 = (valueEnd - lastSpecialIndex) - 1;
                            System.arraycopy(this.buf, srcPos4, this.buf, destPos4, LengthOfCopy4);
                            int bufIndex7 = lastSpecialIndex;
                            int bufIndex8 = bufIndex7 + 1;
                            this.buf[bufIndex7] = '\\';
                            int bufIndex9 = bufIndex8 + 1;
                            this.buf[bufIndex8] = 'u';
                            int bufIndex10 = bufIndex9 + 1;
                            this.buf[bufIndex9] = IOUtils.DIGITS[(ch3 >>> '\f') & 15];
                            int bufIndex11 = bufIndex10 + 1;
                            this.buf[bufIndex10] = IOUtils.DIGITS[(ch3 >>> '\b') & 15];
                            int bufIndex12 = bufIndex11 + 1;
                            this.buf[bufIndex11] = IOUtils.DIGITS[(ch3 >>> 4) & 15];
                            int i4 = bufIndex12 + 1;
                            this.buf[bufIndex12] = IOUtils.DIGITS[ch3 & 15];
                        } else {
                            int srcPos5 = lastSpecialIndex + 1;
                            int destPos5 = lastSpecialIndex + 2;
                            int LengthOfCopy5 = (valueEnd - lastSpecialIndex) - 1;
                            System.arraycopy(this.buf, srcPos5, this.buf, destPos5, LengthOfCopy5);
                            this.buf[lastSpecialIndex] = '\\';
                            this.buf[lastSpecialIndex + 1] = IOUtils.replaceChars[ch3];
                        }
                    }
                }
            } else {
                newcount2 = newcount6;
                if (specialCount > 1) {
                    int textIndex = firstSpecialIndex - index6;
                    int bufIndex13 = firstSpecialIndex;
                    for (int i5 = textIndex; i5 < value.length(); i5++) {
                        char ch4 = value.charAt(i5);
                        if (this.browserSecure) {
                            if (ch4 != '(' && ch4 != ')' && ch4 != '<') {
                                if (ch4 == '>') {
                                }
                            }
                            int bufIndex14 = bufIndex13 + 1;
                            this.buf[bufIndex13] = '\\';
                            int bufIndex15 = bufIndex14 + 1;
                            this.buf[bufIndex14] = 'u';
                            int bufIndex16 = bufIndex15 + 1;
                            this.buf[bufIndex15] = IOUtils.DIGITS[(ch4 >>> '\f') & 15];
                            int bufIndex17 = bufIndex16 + 1;
                            this.buf[bufIndex16] = IOUtils.DIGITS[(ch4 >>> '\b') & 15];
                            int bufIndex18 = bufIndex17 + 1;
                            this.buf[bufIndex17] = IOUtils.DIGITS[(ch4 >>> 4) & 15];
                            this.buf[bufIndex18] = IOUtils.DIGITS[ch4 & 15];
                            valueEnd += 5;
                            bufIndex13 = bufIndex18 + 1;
                        }
                        if ((ch4 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch4] != 0) || (ch4 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                            int bufIndex19 = bufIndex13 + 1;
                            this.buf[bufIndex13] = '\\';
                            if (IOUtils.specicalFlags_doubleQuotes[ch4] == 4) {
                                int bufIndex20 = bufIndex19 + 1;
                                this.buf[bufIndex19] = 'u';
                                int bufIndex21 = bufIndex20 + 1;
                                this.buf[bufIndex20] = IOUtils.DIGITS[(ch4 >>> '\f') & 15];
                                int bufIndex22 = bufIndex21 + 1;
                                this.buf[bufIndex21] = IOUtils.DIGITS[(ch4 >>> '\b') & 15];
                                int bufIndex23 = bufIndex22 + 1;
                                this.buf[bufIndex22] = IOUtils.DIGITS[(ch4 >>> 4) & 15];
                                this.buf[bufIndex23] = IOUtils.DIGITS[ch4 & 15];
                                valueEnd += 5;
                                bufIndex13 = bufIndex23 + 1;
                            } else {
                                this.buf[bufIndex19] = IOUtils.replaceChars[ch4];
                                valueEnd++;
                                bufIndex13 = bufIndex19 + 1;
                            }
                        } else {
                            if (ch4 == 8232 || ch4 == 8233) {
                                int bufIndex24 = bufIndex13 + 1;
                                this.buf[bufIndex13] = '\\';
                                int bufIndex25 = bufIndex24 + 1;
                                this.buf[bufIndex24] = 'u';
                                int bufIndex26 = bufIndex25 + 1;
                                this.buf[bufIndex25] = IOUtils.DIGITS[(ch4 >>> '\f') & 15];
                                int bufIndex27 = bufIndex26 + 1;
                                this.buf[bufIndex26] = IOUtils.DIGITS[(ch4 >>> '\b') & 15];
                                int bufIndex28 = bufIndex27 + 1;
                                this.buf[bufIndex27] = IOUtils.DIGITS[(ch4 >>> 4) & 15];
                                this.buf[bufIndex28] = IOUtils.DIGITS[ch4 & 15];
                                valueEnd += 5;
                                bufIndex13 = bufIndex28 + 1;
                            } else {
                                this.buf[bufIndex13] = ch4;
                                bufIndex13++;
                            }
                        }
                    }
                }
            }
        }
        this.buf[this.count - 1] = Typography.quote;
    }

    public void writeFieldValueStringWithDoubleQuote(char seperator, String name, String value) {
        int nameLen = name.length();
        int newcount = this.count;
        int valueLen = value.length();
        int newcount2 = newcount + nameLen + valueLen + 6;
        if (newcount2 > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeStringWithDoubleQuote(name, ':');
                writeStringWithDoubleQuote(value, (char) 0);
                return;
            }
            expandCapacity(newcount2);
        }
        this.buf[this.count] = seperator;
        int nameStart = this.count + 2;
        int nameEnd = nameStart + nameLen;
        this.buf[this.count + 1] = Typography.quote;
        name.getChars(0, nameLen, this.buf, nameStart);
        this.count = newcount2;
        this.buf[nameEnd] = Typography.quote;
        int index = nameEnd + 1;
        int index2 = index + 1;
        this.buf[index] = ':';
        this.buf[index2] = Typography.quote;
        value.getChars(0, valueLen, this.buf, index2 + 1);
        this.buf[this.count - 1] = Typography.quote;
    }

    public void writeFieldValue(char seperator, String name, Enum<?> value) {
        if (value == null) {
            write(seperator);
            writeFieldName(name);
            writeNull();
        } else if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            writeEnumFieldValue(seperator, name, value.name());
        } else if (this.writeEnumUsingToString) {
            writeEnumFieldValue(seperator, name, value.toString());
        } else {
            writeFieldValue(seperator, name, value.ordinal());
        }
    }

    private void writeEnumFieldValue(char seperator, String name, String value) {
        if (this.useSingleQuotes) {
            writeFieldValue(seperator, name, value);
        } else {
            writeFieldValueStringWithDoubleQuote(seperator, name, value);
        }
    }

    public void writeFieldValue(char seperator, String name, BigDecimal value) {
        String string;
        write(seperator);
        writeFieldName(name);
        if (value == null) {
            writeNull();
            return;
        }
        int scale = value.scale();
        if (isEnabled(SerializerFeature.WriteBigDecimalAsPlain) && scale >= -100 && scale < 100) {
            string = value.toPlainString();
        } else {
            string = value.toString();
        }
        write(string);
    }

    public void writeString(String text, char seperator) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(text);
            write(seperator);
        } else {
            writeStringWithDoubleQuote(text, seperator);
        }
    }

    public void writeString(String text) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(text);
        } else {
            writeStringWithDoubleQuote(text, (char) 0);
        }
    }

    public void writeString(char[] chars) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(chars);
        } else {
            String text = new String(chars);
            writeStringWithDoubleQuote(text, (char) 0);
        }
    }

    protected void writeStringWithSingleQuote(String text) {
        if (text == null) {
            int newcount = this.count + 4;
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            "null".getChars(0, 4, this.buf, this.count);
            this.count = newcount;
            return;
        }
        int len = text.length();
        int newcount2 = this.count + len + 2;
        char c = '/';
        char c2 = '\\';
        if (newcount2 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                for (int i = 0; i < text.length(); i++) {
                    char ch = text.charAt(i);
                    if (ch <= '\r' || ch == '\\' || ch == '\'' || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[ch]);
                    } else {
                        write(ch);
                    }
                }
                write(39);
                return;
            }
            expandCapacity(newcount2);
        }
        int start = this.count + 1;
        int end = start + len;
        this.buf[this.count] = '\'';
        text.getChars(0, len, this.buf, start);
        this.count = newcount2;
        int specialCount = 0;
        int lastSpecialIndex = -1;
        char lastSpecial = 0;
        int i2 = start;
        while (i2 < end) {
            char ch2 = this.buf[i2];
            if (ch2 <= '\r' || ch2 == '\\' || ch2 == '\'' || (ch2 == c && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                specialCount++;
                int lastSpecialIndex2 = i2;
                lastSpecial = ch2;
                lastSpecialIndex = lastSpecialIndex2;
            }
            i2++;
            c = '/';
        }
        int newcount3 = newcount2 + specialCount;
        if (newcount3 > this.buf.length) {
            expandCapacity(newcount3);
        }
        this.count = newcount3;
        if (specialCount == 1) {
            System.arraycopy(this.buf, lastSpecialIndex + 1, this.buf, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            this.buf[lastSpecialIndex] = '\\';
            this.buf[lastSpecialIndex + 1] = IOUtils.replaceChars[lastSpecial];
        } else if (specialCount > 1) {
            System.arraycopy(this.buf, lastSpecialIndex + 1, this.buf, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            this.buf[lastSpecialIndex] = '\\';
            int lastSpecialIndex3 = lastSpecialIndex + 1;
            this.buf[lastSpecialIndex3] = IOUtils.replaceChars[lastSpecial];
            int end2 = end + 1;
            for (int i3 = lastSpecialIndex3 - 2; i3 >= start; i3--) {
                char ch3 = this.buf[i3];
                if (ch3 > '\r' && ch3 != c2 && ch3 != '\'') {
                    if (ch3 != '/' || !isEnabled(SerializerFeature.WriteSlashAsSpecial)) {
                    }
                }
                System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 2, (end2 - i3) - 1);
                c2 = '\\';
                this.buf[i3] = '\\';
                this.buf[i3 + 1] = IOUtils.replaceChars[ch3];
                end2++;
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    protected void writeStringWithSingleQuote(char[] chars) {
        if (chars == null) {
            int newcount = this.count + 4;
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            "null".getChars(0, 4, this.buf, this.count);
            this.count = newcount;
            return;
        }
        int newcount2 = chars.length;
        int newcount3 = this.count + newcount2 + 2;
        char c = '/';
        char c2 = '\\';
        if (newcount3 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                for (char ch : chars) {
                    if (ch <= '\r' || ch == '\\' || ch == '\'' || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[ch]);
                    } else {
                        write(ch);
                    }
                }
                write(39);
                return;
            }
            expandCapacity(newcount3);
        }
        int start = this.count + 1;
        int end = start + newcount2;
        this.buf[this.count] = '\'';
        System.arraycopy(chars, 0, this.buf, start, chars.length);
        this.count = newcount3;
        int specialCount = 0;
        int lastSpecialIndex = -1;
        char lastSpecial = 0;
        int i = start;
        while (i < end) {
            char ch2 = this.buf[i];
            if (ch2 <= '\r' || ch2 == '\\' || ch2 == '\'' || (ch2 == c && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                specialCount++;
                int lastSpecialIndex2 = i;
                lastSpecial = ch2;
                lastSpecialIndex = lastSpecialIndex2;
            }
            i++;
            c = '/';
        }
        int newcount4 = newcount3 + specialCount;
        if (newcount4 > this.buf.length) {
            expandCapacity(newcount4);
        }
        this.count = newcount4;
        if (specialCount == 1) {
            System.arraycopy(this.buf, lastSpecialIndex + 1, this.buf, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            this.buf[lastSpecialIndex] = '\\';
            this.buf[lastSpecialIndex + 1] = IOUtils.replaceChars[lastSpecial];
        } else if (specialCount > 1) {
            System.arraycopy(this.buf, lastSpecialIndex + 1, this.buf, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            this.buf[lastSpecialIndex] = '\\';
            int lastSpecialIndex3 = lastSpecialIndex + 1;
            this.buf[lastSpecialIndex3] = IOUtils.replaceChars[lastSpecial];
            int end2 = end + 1;
            for (int i2 = lastSpecialIndex3 - 2; i2 >= start; i2--) {
                char ch3 = this.buf[i2];
                if (ch3 > '\r' && ch3 != c2 && ch3 != '\'') {
                    if (ch3 != '/' || !isEnabled(SerializerFeature.WriteSlashAsSpecial)) {
                    }
                }
                System.arraycopy(this.buf, i2 + 1, this.buf, i2 + 2, (end2 - i2) - 1);
                c2 = '\\';
                this.buf[i2] = '\\';
                this.buf[i2 + 1] = IOUtils.replaceChars[ch3];
                end2++;
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    public void writeFieldName(String key) {
        writeFieldName(key, false);
    }

    public void writeFieldName(String key, boolean checkSpecial) {
        if (key == null) {
            write("null:");
            return;
        }
        if (this.useSingleQuotes) {
            if (this.quoteFieldNames) {
                writeStringWithSingleQuote(key);
                write(58);
                return;
            } else {
                writeKeyWithSingleQuoteIfHasSpecial(key);
                return;
            }
        }
        if (this.quoteFieldNames) {
            writeStringWithDoubleQuote(key, ':');
            return;
        }
        boolean hashSpecial = key.length() == 0;
        int i = 0;
        while (true) {
            if (i >= key.length()) {
                break;
            }
            char ch = key.charAt(i);
            boolean special = (ch < '@' && (this.sepcialBits & (1 << ch)) != 0) || ch == '\\';
            if (!special) {
                i++;
            } else {
                hashSpecial = true;
                break;
            }
        }
        if (hashSpecial) {
            writeStringWithDoubleQuote(key, ':');
        } else {
            write(key);
            write(58);
        }
    }

    private void writeKeyWithSingleQuoteIfHasSpecial(String text) {
        byte[] specicalFlags_singleQuotes = IOUtils.specicalFlags_singleQuotes;
        int len = text.length();
        int newcount = this.count + len + 1;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                if (len == 0) {
                    write(39);
                    write(39);
                    write(58);
                    return;
                }
                boolean hasSpecial = false;
                int i = 0;
                while (true) {
                    if (i >= len) {
                        break;
                    }
                    char ch = text.charAt(i);
                    if (ch >= specicalFlags_singleQuotes.length || specicalFlags_singleQuotes[ch] == 0) {
                        i++;
                    } else {
                        hasSpecial = true;
                        break;
                    }
                }
                if (hasSpecial) {
                    write(39);
                }
                for (int i2 = 0; i2 < len; i2++) {
                    char ch2 = text.charAt(i2);
                    if (ch2 < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[ch2] != 0) {
                        write(92);
                        write(IOUtils.replaceChars[ch2]);
                    } else {
                        write(ch2);
                    }
                }
                if (hasSpecial) {
                    write(39);
                }
                write(58);
                return;
            }
            expandCapacity(newcount);
        }
        if (len == 0) {
            int newCount = this.count + 3;
            if (newCount > this.buf.length) {
                expandCapacity(this.count + 3);
            }
            char[] cArr = this.buf;
            int i3 = this.count;
            this.count = i3 + 1;
            cArr[i3] = '\'';
            char[] cArr2 = this.buf;
            int i4 = this.count;
            this.count = i4 + 1;
            cArr2[i4] = '\'';
            char[] cArr3 = this.buf;
            int i5 = this.count;
            this.count = i5 + 1;
            cArr3[i5] = ':';
            return;
        }
        int start = this.count;
        int end = start + len;
        text.getChars(0, len, this.buf, start);
        this.count = newcount;
        boolean hasSpecial2 = false;
        int i6 = start;
        while (i6 < end) {
            char ch3 = this.buf[i6];
            if (ch3 < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[ch3] != 0) {
                if (!hasSpecial2) {
                    newcount += 3;
                    if (newcount > this.buf.length) {
                        expandCapacity(newcount);
                    }
                    this.count = newcount;
                    System.arraycopy(this.buf, i6 + 1, this.buf, i6 + 3, (end - i6) - 1);
                    System.arraycopy(this.buf, 0, this.buf, 1, i6);
                    this.buf[start] = '\'';
                    int i7 = i6 + 1;
                    this.buf[i7] = '\\';
                    i6 = i7 + 1;
                    this.buf[i6] = IOUtils.replaceChars[ch3];
                    end += 2;
                    this.buf[this.count - 2] = '\'';
                    hasSpecial2 = true;
                } else {
                    newcount++;
                    if (newcount > this.buf.length) {
                        expandCapacity(newcount);
                    }
                    this.count = newcount;
                    System.arraycopy(this.buf, i6 + 1, this.buf, i6 + 2, end - i6);
                    this.buf[i6] = '\\';
                    i6++;
                    this.buf[i6] = IOUtils.replaceChars[ch3];
                    end++;
                }
            }
            i6++;
        }
        this.buf[newcount - 1] = ':';
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        if (this.writer == null) {
            return;
        }
        try {
            this.writer.write(this.buf, 0, this.count);
            this.writer.flush();
            this.count = 0;
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public void reset() {
        this.count = 0;
    }
}