package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes.dex */
public class JSONSerializer extends SerializeFilterable {
    protected final SerializeConfig config;
    protected SerialContext context;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private String fastJsonConfigDateFormatPattern;
    private String indent;
    private int indentCount;
    protected Locale locale;
    public final SerializeWriter out;
    protected IdentityHashMap<Object, SerialContext> references;
    protected TimeZone timeZone;

    public JSONSerializer() {
        this(new SerializeWriter(), SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeWriter out) {
        this(out, SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeConfig config) {
        this(new SerializeWriter(), config);
    }

    public JSONSerializer(SerializeWriter out, SerializeConfig config) {
        this.indentCount = 0;
        this.indent = "\t";
        this.references = null;
        this.timeZone = JSON.defaultTimeZone;
        this.locale = JSON.defaultLocale;
        this.out = out;
        this.config = config;
    }

    public String getDateFormatPattern() {
        if (this.dateFormat instanceof SimpleDateFormat) {
            return ((SimpleDateFormat) this.dateFormat).toPattern();
        }
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null && this.dateFormatPattern != null) {
            this.dateFormat = generateDateFormat(this.dateFormatPattern);
        }
        return this.dateFormat;
    }

    private DateFormat generateDateFormat(String dateFormatPattern) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, this.locale);
        dateFormat.setTimeZone(this.timeZone);
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        if (this.dateFormatPattern != null) {
            this.dateFormatPattern = null;
        }
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormatPattern = dateFormat;
        if (this.dateFormat != null) {
            this.dateFormat = null;
        }
    }

    public void setFastJsonConfigDateFormatPattern(String dateFormatPattern) {
        this.fastJsonConfigDateFormatPattern = dateFormatPattern;
    }

    public String getFastJsonConfigDateFormatPattern() {
        return this.fastJsonConfigDateFormatPattern;
    }

    public SerialContext getContext() {
        return this.context;
    }

    public void setContext(SerialContext context) {
        this.context = context;
    }

    public void setContext(SerialContext parent, Object object, Object fieldName, int features) {
        setContext(parent, object, fieldName, features, 0);
    }

    public void setContext(SerialContext parent, Object object, Object fieldName, int features, int fieldFeatures) {
        if (this.out.disableCircularReferenceDetect) {
            return;
        }
        this.context = new SerialContext(parent, object, fieldName, features, fieldFeatures);
        if (this.references == null) {
            this.references = new IdentityHashMap<>();
        }
        this.references.put(object, this.context);
    }

    public void setContext(Object object, Object fieldName) {
        setContext(this.context, object, fieldName, 0);
    }

    public void popContext() {
        if (this.context != null) {
            this.context = this.context.parent;
        }
    }

    public final boolean isWriteClassName(Type fieldType, Object obj) {
        return this.out.isEnabled(SerializerFeature.WriteClassName) && !(fieldType == null && this.out.isEnabled(SerializerFeature.NotWriteRootClassName) && (this.context == null || this.context.parent == null));
    }

    public boolean containsReference(Object value) {
        SerialContext refContext;
        if (this.references == null || (refContext = this.references.get(value)) == null || value == Collections.emptyMap()) {
            return false;
        }
        Object fieldName = refContext.fieldName;
        return fieldName == null || (fieldName instanceof Integer) || (fieldName instanceof String);
    }

    public void writeReference(Object object) {
        SerialContext context = this.context;
        Object current = context.object;
        if (object == current) {
            this.out.write("{\"$ref\":\"@\"}");
            return;
        }
        SerialContext parentContext = context.parent;
        if (parentContext != null && object == parentContext.object) {
            this.out.write("{\"$ref\":\"..\"}");
            return;
        }
        SerialContext rootContext = context;
        while (rootContext.parent != null) {
            rootContext = rootContext.parent;
        }
        if (object == rootContext.object) {
            this.out.write("{\"$ref\":\"$\"}");
            return;
        }
        this.out.write("{\"$ref\":\"");
        String path = this.references.get(object).toString();
        this.out.write(path);
        this.out.write("\"}");
    }

    public boolean checkValue(SerializeFilterable filterable) {
        return (this.valueFilters != null && this.valueFilters.size() > 0) || (this.contextValueFilters != null && this.contextValueFilters.size() > 0) || ((filterable.valueFilters != null && filterable.valueFilters.size() > 0) || ((filterable.contextValueFilters != null && filterable.contextValueFilters.size() > 0) || this.out.writeNonStringValueAsString));
    }

    public boolean hasNameFilters(SerializeFilterable filterable) {
        return (this.nameFilters != null && this.nameFilters.size() > 0) || (filterable.nameFilters != null && filterable.nameFilters.size() > 0);
    }

    public boolean hasPropertyFilters(SerializeFilterable filterable) {
        return (this.propertyFilters != null && this.propertyFilters.size() > 0) || (filterable.propertyFilters != null && filterable.propertyFilters.size() > 0);
    }

    public int getIndentCount() {
        return this.indentCount;
    }

    public void incrementIndent() {
        this.indentCount++;
    }

    public void decrementIdent() {
        this.indentCount--;
    }

    public void println() {
        this.out.write(10);
        for (int i = 0; i < this.indentCount; i++) {
            this.out.write(this.indent);
        }
    }

    public SerializeWriter getWriter() {
        return this.out;
    }

    public String toString() {
        return this.out.toString();
    }

    public void config(SerializerFeature feature, boolean state) {
        this.out.config(feature, state);
    }

    public boolean isEnabled(SerializerFeature feature) {
        return this.out.isEnabled(feature);
    }

    public void writeNull() {
        this.out.writeNull();
    }

    public SerializeConfig getMapping() {
        return this.config;
    }

    public static void write(Writer out, Object object) {
        SerializeWriter writer = new SerializeWriter();
        try {
            try {
                JSONSerializer serializer = new JSONSerializer(writer);
                serializer.write(object);
                writer.writeTo(out);
            } catch (IOException ex) {
                throw new JSONException(ex.getMessage(), ex);
            }
        } finally {
            writer.close();
        }
    }

    public static void write(SerializeWriter out, Object object) {
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.write(object);
    }

    public final void write(Object object) {
        if (object == null) {
            this.out.writeNull();
            return;
        }
        Class<?> clazz = object.getClass();
        ObjectSerializer writer = getObjectWriter(clazz);
        try {
            writer.write(this, object, null, null, 0);
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeAs(Object object, Class type) {
        if (object == null) {
            this.out.writeNull();
            return;
        }
        ObjectSerializer writer = getObjectWriter(type);
        try {
            writer.write(this, object, null, null, 0);
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeWithFieldName(Object object, Object fieldName) {
        writeWithFieldName(object, fieldName, null, 0);
    }

    protected final void writeKeyValue(char seperator, String key, Object value) {
        if (seperator != 0) {
            this.out.write(seperator);
        }
        this.out.writeFieldName(key);
        write(value);
    }

    public final void writeWithFieldName(Object object, Object fieldName, Type fieldType, int fieldFeatures) {
        try {
            if (object == null) {
                this.out.writeNull();
                return;
            }
            Class<?> clazz = object.getClass();
            ObjectSerializer writer = getObjectWriter(clazz);
            writer.write(this, object, fieldName, fieldType, fieldFeatures);
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeWithFormat(Object object, String format) throws IOException {
        if (object instanceof Date) {
            if ("unixtime".equals(format)) {
                long seconds = ((Date) object).getTime() / 1000;
                this.out.writeInt((int) seconds);
                return;
            }
            if ("millis".equals(format)) {
                this.out.writeLong(((Date) object).getTime());
                return;
            }
            DateFormat dateFormat = getDateFormat();
            if (dateFormat == null) {
                if (format != null) {
                    try {
                        dateFormat = generateDateFormat(format);
                    } catch (IllegalArgumentException e) {
                        String format2 = format.replaceAll("T", "'T'");
                        dateFormat = generateDateFormat(format2);
                    }
                } else if (this.fastJsonConfigDateFormatPattern != null) {
                    dateFormat = generateDateFormat(this.fastJsonConfigDateFormatPattern);
                } else {
                    dateFormat = generateDateFormat(JSON.DEFFAULT_DATE_FORMAT);
                }
            }
            String text = dateFormat.format((Date) object);
            this.out.writeString(text);
            return;
        }
        if (object instanceof byte[]) {
            byte[] bytes = (byte[]) object;
            if ("gzip".equals(format) || "gzip,base64".equals(format)) {
                GZIPOutputStream gzipOut = null;
                try {
                    try {
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                        if (bytes.length < 512) {
                            gzipOut = new GZIPOutputStream(byteOut, bytes.length);
                        } else {
                            gzipOut = new GZIPOutputStream(byteOut);
                        }
                        gzipOut.write(bytes);
                        gzipOut.finish();
                        this.out.writeByteArray(byteOut.toByteArray());
                        return;
                    } finally {
                        IOUtils.close(gzipOut);
                    }
                } catch (IOException ex) {
                    throw new JSONException("write gzipBytes error", ex);
                }
            }
            if ("hex".equals(format)) {
                this.out.writeHex(bytes);
                return;
            } else {
                this.out.writeByteArray(bytes);
                return;
            }
        }
        if (object instanceof Collection) {
            Collection collection = (Collection) object;
            Iterator iterator = collection.iterator();
            this.out.write(91);
            for (int i = 0; i < collection.size(); i++) {
                Object item = iterator.next();
                if (i != 0) {
                    this.out.write(44);
                }
                writeWithFormat(item, format);
            }
            this.out.write(93);
            return;
        }
        write(object);
    }

    public final void write(String text) {
        StringCodec.instance.write(this, text);
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        return this.config.getObjectWriter(clazz);
    }

    public void close() {
        this.out.close();
    }
}