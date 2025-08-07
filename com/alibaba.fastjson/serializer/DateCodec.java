package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class DateCodec extends AbstractDateDeserializer implements ObjectSerializer, ObjectDeserializer {
    public static final DateCodec instance = new DateCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        Date date;
        char[] buf;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        Class<?> clazz = object.getClass();
        if (clazz == java.sql.Date.class && !out.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
            long millis = ((java.sql.Date) object).getTime();
            int offset = serializer.timeZone.getOffset(millis);
            if ((offset + millis) % 86400000 == 0 && !SerializerFeature.isEnabled(out.features, features, SerializerFeature.WriteClassName)) {
                out.writeString(object.toString());
                return;
            }
        }
        if (clazz == Time.class) {
            long millis2 = ((Time) object).getTime();
            if ("unixtime".equals(serializer.getDateFormatPattern())) {
                long seconds = millis2 / 1000;
                out.writeLong(seconds);
                return;
            } else if ("millis".equals(serializer.getDateFormatPattern())) {
                out.writeLong(millis2);
                return;
            } else if (millis2 < 86400000) {
                out.writeString(object.toString());
                return;
            }
        }
        int nanos = 0;
        if (clazz == Timestamp.class) {
            Timestamp ts = (Timestamp) object;
            nanos = ts.getNanos();
        }
        if (object instanceof Date) {
            date = (Date) object;
        } else {
            date = TypeUtils.castToDate(object);
        }
        if ("unixtime".equals(serializer.getDateFormatPattern())) {
            long seconds2 = date.getTime() / 1000;
            out.writeLong(seconds2);
            return;
        }
        if ("millis".equals(serializer.getDateFormatPattern())) {
            out.writeLong(date.getTime());
            return;
        }
        if (out.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
            DateFormat format = serializer.getDateFormat();
            if (format == null) {
                String dateFormatPattern = serializer.getFastJsonConfigDateFormatPattern();
                if (dateFormatPattern == null) {
                    dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
                }
                format = new SimpleDateFormat(dateFormatPattern, serializer.locale);
                format.setTimeZone(serializer.timeZone);
            }
            String text = format.format(date);
            out.writeString(text);
            return;
        }
        if (out.isEnabled(SerializerFeature.WriteClassName) && clazz != fieldType) {
            if (clazz == Date.class) {
                out.write("new Date(");
                out.writeLong(((Date) object).getTime());
                out.write(41);
                return;
            } else {
                out.write(123);
                out.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                serializer.write(clazz.getName());
                out.writeFieldValue(',', "val", ((Date) object).getTime());
                out.write(125);
                return;
            }
        }
        long time = date.getTime();
        if (out.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
            char quote = out.isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : Typography.quote;
            out.write(quote);
            Calendar calendar = Calendar.getInstance(serializer.timeZone, serializer.locale);
            calendar.setTimeInMillis(time);
            int year = calendar.get(1);
            int month = calendar.get(2) + 1;
            int day = calendar.get(5);
            int hour = calendar.get(11);
            int minute = calendar.get(12);
            int second = calendar.get(13);
            int millis3 = calendar.get(14);
            if (nanos > 0) {
                buf = "0000-00-00 00:00:00.000000000".toCharArray();
                IOUtils.getChars(nanos, 29, buf);
                IOUtils.getChars(second, 19, buf);
                IOUtils.getChars(minute, 16, buf);
                IOUtils.getChars(hour, 13, buf);
                IOUtils.getChars(day, 10, buf);
                IOUtils.getChars(month, 7, buf);
                IOUtils.getChars(year, 4, buf);
            } else if (millis3 != 0) {
                buf = "0000-00-00T00:00:00.000".toCharArray();
                IOUtils.getChars(millis3, 23, buf);
                IOUtils.getChars(second, 19, buf);
                IOUtils.getChars(minute, 16, buf);
                IOUtils.getChars(hour, 13, buf);
                IOUtils.getChars(day, 10, buf);
                IOUtils.getChars(month, 7, buf);
                IOUtils.getChars(year, 4, buf);
            } else if (second == 0 && minute == 0 && hour == 0) {
                buf = "0000-00-00".toCharArray();
                IOUtils.getChars(day, 10, buf);
                IOUtils.getChars(month, 7, buf);
                IOUtils.getChars(year, 4, buf);
            } else {
                buf = "0000-00-00T00:00:00".toCharArray();
                IOUtils.getChars(second, 19, buf);
                IOUtils.getChars(minute, 16, buf);
                IOUtils.getChars(hour, 13, buf);
                IOUtils.getChars(day, 10, buf);
                IOUtils.getChars(month, 7, buf);
                IOUtils.getChars(year, 4, buf);
            }
            if (nanos > 0) {
                int i = 0;
                while (true) {
                    int minute2 = minute;
                    if (i >= 9) {
                        break;
                    }
                    int off = (buf.length - i) - 1;
                    int millis4 = millis3;
                    if (buf[off] != '0') {
                        break;
                    }
                    i++;
                    minute = minute2;
                    millis3 = millis4;
                }
                out.write(buf, 0, buf.length - i);
                out.write(quote);
                return;
            }
            out.write(buf);
            float timeZoneF = calendar.getTimeZone().getOffset(calendar.getTimeInMillis()) / 3600000.0f;
            int timeZone = (int) timeZoneF;
            if (timeZone == 0.0d) {
                out.write(90);
            } else {
                if (timeZone > 9) {
                    out.write(43);
                    out.writeInt(timeZone);
                } else if (timeZone > 0) {
                    out.write(43);
                    out.write(48);
                    out.writeInt(timeZone);
                } else if (timeZone < -9) {
                    out.write(45);
                    out.writeInt(-timeZone);
                } else if (timeZone < 0) {
                    out.write(45);
                    out.write(48);
                    out.writeInt(-timeZone);
                }
                out.write(58);
                int offSet = (int) (Math.abs(timeZoneF - timeZone) * 60.0f);
                out.append((CharSequence) String.format("%02d", Integer.valueOf(offSet)));
            }
            out.write(quote);
            return;
        }
        out.writeLong(time);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [T, java.util.Calendar] */
    /* JADX WARN: Type inference failed for: r4v4, types: [T, java.util.Calendar] */
    @Override // com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer
    public <T> T cast(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        if (obj2 == 0) {
            return null;
        }
        if (obj2 instanceof Date) {
            return obj2;
        }
        if (obj2 instanceof BigDecimal) {
            return (T) new Date(TypeUtils.longValue((BigDecimal) obj2));
        }
        if (obj2 instanceof Number) {
            return (T) new Date(((Number) obj2).longValue());
        }
        if (!(obj2 instanceof String)) {
            throw new JSONException("parse error");
        }
        String strSubstring = (String) obj2;
        if (strSubstring.length() == 0) {
            return null;
        }
        if (strSubstring.length() == 23 && strSubstring.endsWith(" 000")) {
            strSubstring = strSubstring.substring(0, 19);
        }
        JSONScanner jSONScanner = new JSONScanner(strSubstring);
        try {
            if (jSONScanner.scanISO8601DateIfMatch(false)) {
                ?? r0 = (T) jSONScanner.getCalendar();
                return type == Calendar.class ? r0 : (T) r0.getTime();
            }
            jSONScanner.close();
            String dateFomartPattern = defaultJSONParser.getDateFomartPattern();
            if (strSubstring.length() == dateFomartPattern.length() || (strSubstring.length() == 22 && dateFomartPattern.equals("yyyyMMddHHmmssSSSZ")) || (strSubstring.indexOf(84) != -1 && dateFomartPattern.contains("'T'") && strSubstring.length() + 2 == dateFomartPattern.length())) {
                try {
                    return (T) defaultJSONParser.getDateFormat().parse(strSubstring);
                } catch (ParseException e) {
                }
            }
            if (strSubstring.startsWith("/Date(") && strSubstring.endsWith(")/")) {
                strSubstring = strSubstring.substring(6, strSubstring.length() - 2);
            }
            if ("0000-00-00".equals(strSubstring) || "0000-00-00T00:00:00".equalsIgnoreCase(strSubstring) || "0001-01-01T00:00:00+08:00".equalsIgnoreCase(strSubstring)) {
                return null;
            }
            int iLastIndexOf = strSubstring.lastIndexOf(124);
            if (iLastIndexOf > 20) {
                TimeZone timeZone = TimeZone.getTimeZone(strSubstring.substring(iLastIndexOf + 1));
                if (!"GMT".equals(timeZone.getID())) {
                    JSONScanner jSONScanner2 = new JSONScanner(strSubstring.substring(0, iLastIndexOf));
                    try {
                        if (jSONScanner2.scanISO8601DateIfMatch(false)) {
                            ?? r4 = (T) jSONScanner2.getCalendar();
                            r4.setTimeZone(timeZone);
                            return type == Calendar.class ? r4 : (T) r4.getTime();
                        }
                    } finally {
                    }
                }
            }
            return (T) new Date(Long.parseLong(strSubstring));
        } finally {
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}