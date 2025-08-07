package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/* loaded from: classes.dex */
public class JodaCodec implements ObjectSerializer, ContextObjectSerializer, ObjectDeserializer {
    private static final String formatter_iso8601_pattern_23 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String formatter_iso8601_pattern_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    public static final JodaCodec instance = new JodaCodec();
    private static final String defaultPatttern = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter defaultFormatter = DateTimeFormat.forPattern(defaultPatttern);
    private static final DateTimeFormatter defaultFormatter_23 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter formatter_dt19_tw = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn = DateTimeFormat.forPattern("yyyy年M月d日 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn_1 = DateTimeFormat.forPattern("yyyy年M月d日 H时m分s秒");
    private static final DateTimeFormatter formatter_dt19_kr = DateTimeFormat.forPattern("yyyy년M월d일 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_us = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_eur = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_de = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_in = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_d8 = DateTimeFormat.forPattern("yyyyMMdd");
    private static final DateTimeFormatter formatter_d10_tw = DateTimeFormat.forPattern("yyyy/MM/dd");
    private static final DateTimeFormatter formatter_d10_cn = DateTimeFormat.forPattern("yyyy年M月d日");
    private static final DateTimeFormatter formatter_d10_kr = DateTimeFormat.forPattern("yyyy년M월d일");
    private static final DateTimeFormatter formatter_d10_us = DateTimeFormat.forPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter_d10_eur = DateTimeFormat.forPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatter_d10_de = DateTimeFormat.forPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatter_d10_in = DateTimeFormat.forPattern("dd-MM-yyyy");
    private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormat.forPattern(defaultPatttern).withZone(DateTimeZone.getDefault());
    private static final String formatter_iso8601_pattern = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter formatter_iso8601 = DateTimeFormat.forPattern(formatter_iso8601_pattern);

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }

    /* JADX WARN: Type inference failed for: r4v6, types: [T, org.joda.time.LocalDateTime] */
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (jSONLexer.token() == 4) {
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextToken();
            DateTimeFormatter dateTimeFormatterForPattern = null;
            if (str != null) {
                if (defaultPatttern.equals(str)) {
                    dateTimeFormatterForPattern = defaultFormatter;
                } else {
                    dateTimeFormatterForPattern = DateTimeFormat.forPattern(str);
                }
            }
            if ("".equals(strStringVal)) {
                return null;
            }
            if (type == LocalDateTime.class) {
                if (strStringVal.length() == 10 || strStringVal.length() == 8) {
                    return (T) parseLocalDate(strStringVal, str, dateTimeFormatterForPattern).toLocalDateTime(LocalTime.MIDNIGHT);
                }
                return (T) parseDateTime(strStringVal, dateTimeFormatterForPattern);
            }
            if (type == LocalDate.class) {
                if (strStringVal.length() == 23) {
                    return (T) LocalDateTime.parse(strStringVal).toLocalDate();
                }
                return (T) parseLocalDate(strStringVal, str, dateTimeFormatterForPattern);
            }
            if (type == LocalTime.class) {
                if (strStringVal.length() == 23) {
                    return (T) LocalDateTime.parse(strStringVal).toLocalTime();
                }
                return (T) LocalTime.parse(strStringVal);
            }
            if (type == DateTime.class) {
                if (dateTimeFormatterForPattern == defaultFormatter) {
                    dateTimeFormatterForPattern = ISO_FIXED_FORMAT;
                }
                return (T) parseZonedDateTime(strStringVal, dateTimeFormatterForPattern);
            }
            if (type == DateTimeZone.class) {
                return (T) DateTimeZone.forID(strStringVal);
            }
            if (type == Period.class) {
                return (T) Period.parse(strStringVal);
            }
            if (type == Duration.class) {
                return (T) Duration.parse(strStringVal);
            }
            if (type == Instant.class) {
                boolean z = true;
                for (int i2 = 0; i2 < strStringVal.length(); i2++) {
                    char cCharAt = strStringVal.charAt(i2);
                    if (cCharAt < '0' || cCharAt > '9') {
                        z = false;
                        break;
                    }
                }
                if (z && strStringVal.length() > 8 && strStringVal.length() < 19) {
                    return (T) new Instant(Long.parseLong(strStringVal));
                }
                return (T) Instant.parse(strStringVal);
            }
            if (type == DateTimeFormatter.class) {
                return (T) DateTimeFormat.forPattern(strStringVal);
            }
        } else {
            if (jSONLexer.token() == 2) {
                long jLongValue = jSONLexer.longValue();
                jSONLexer.nextToken();
                TimeZone timeZone = JSON.defaultTimeZone;
                if (timeZone == null) {
                    timeZone = TimeZone.getDefault();
                }
                if (type == DateTime.class) {
                    return (T) new DateTime(jLongValue, DateTimeZone.forTimeZone(timeZone));
                }
                ?? r4 = (T) new LocalDateTime(jLongValue, DateTimeZone.forTimeZone(timeZone));
                if (type == LocalDateTime.class) {
                    return r4;
                }
                if (type == LocalDate.class) {
                    return (T) r4.toLocalDate();
                }
                if (type == LocalTime.class) {
                    return (T) r4.toLocalTime();
                }
                if (type == Instant.class) {
                    return (T) new Instant(jLongValue);
                }
                throw new UnsupportedOperationException();
            }
            if (jSONLexer.token() == 12) {
                JSONObject object = defaultJSONParser.parseObject();
                if (type == Instant.class) {
                    Object obj2 = object.get("epochSecond");
                    if (obj2 instanceof Number) {
                        return (T) Instant.ofEpochSecond(TypeUtils.longExtractValue((Number) obj2));
                    }
                    Object obj3 = object.get("millis");
                    if (obj3 instanceof Number) {
                        return (T) Instant.ofEpochMilli(TypeUtils.longExtractValue((Number) obj3));
                    }
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected LocalDateTime parseDateTime(String text, DateTimeFormatter formatter) throws NumberFormatException {
        DateTimeFormatter formatter2;
        DateTimeFormatter formatter3;
        if (formatter != null) {
            formatter2 = formatter;
        } else {
            if (text.length() == 19) {
                char c4 = text.charAt(4);
                char c7 = text.charAt(7);
                char c10 = text.charAt(10);
                char c13 = text.charAt(13);
                char c16 = text.charAt(16);
                if (c13 == ':' && c16 == ':') {
                    if (c4 == '-' && c7 == '-') {
                        if (c10 == 'T') {
                            formatter2 = formatter_iso8601;
                        } else if (c10 == ' ') {
                            formatter2 = defaultFormatter;
                        }
                    } else if (c4 != '/' || c7 != '/') {
                        char c0 = text.charAt(0);
                        char c1 = text.charAt(1);
                        char c2 = text.charAt(2);
                        char c3 = text.charAt(3);
                        char c5 = text.charAt(5);
                        if (c2 == '/' && c5 == '/') {
                            int v0 = ((c0 - '0') * 10) + (c1 - '0');
                            int v1 = ((c3 - '0') * 10) + (c4 - '0');
                            if (v0 > 12) {
                                formatter3 = formatter_dt19_eur;
                            } else if (v1 > 12) {
                                formatter3 = formatter_dt19_us;
                            } else {
                                String country = Locale.getDefault().getCountry();
                                if (country.equals("US")) {
                                    formatter3 = formatter_dt19_us;
                                } else {
                                    formatter3 = (country.equals("BR") || country.equals("AU")) ? formatter_dt19_eur : formatter;
                                }
                            }
                            formatter2 = formatter3;
                        } else if (c2 == '.' && c5 == '.') {
                            formatter2 = formatter_dt19_de;
                        } else if (c2 == '-' && c5 == '-') {
                            formatter2 = formatter_dt19_in;
                        }
                    } else {
                        formatter2 = formatter_dt19_tw;
                    }
                } else {
                    formatter2 = formatter;
                }
            } else if (text.length() == 23) {
                char c42 = text.charAt(4);
                char c72 = text.charAt(7);
                char c102 = text.charAt(10);
                char c132 = text.charAt(13);
                char c162 = text.charAt(16);
                char c19 = text.charAt(19);
                if (c132 == ':' && c162 == ':' && c42 == '-' && c72 == '-' && c102 == ' ' && c19 == '.') {
                    formatter2 = defaultFormatter_23;
                } else {
                    formatter2 = formatter;
                }
            }
            if (text.length() >= 17) {
                char c43 = text.charAt(4);
                if (c43 == 24180) {
                    if (text.charAt(text.length() - 1) == 31186) {
                        formatter2 = formatter_dt19_cn_1;
                    } else {
                        formatter2 = formatter_dt19_cn;
                    }
                } else if (c43 == 45380) {
                    formatter2 = formatter_dt19_kr;
                }
            }
            char c44 = 1;
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (ch < '0' || ch > '9') {
                    c44 = 0;
                    break;
                }
            }
            if (c44 != 0 && text.length() > 8 && text.length() < 19) {
                long epochMillis = Long.parseLong(text);
                return new LocalDateTime(epochMillis, DateTimeZone.forTimeZone(JSON.defaultTimeZone));
            }
        }
        if (formatter2 == null) {
            return LocalDateTime.parse(text);
        }
        return LocalDateTime.parse(text, formatter2);
    }

    protected LocalDate parseLocalDate(String text, String format, DateTimeFormatter formatter) throws NumberFormatException {
        DateTimeFormatter formatter2;
        if (formatter != null) {
            formatter2 = formatter;
        } else {
            if (text.length() != 8) {
                formatter2 = formatter;
            } else {
                formatter2 = formatter_d8;
            }
            if (text.length() == 10) {
                char c4 = text.charAt(4);
                char c7 = text.charAt(7);
                if (c4 == '/' && c7 == '/') {
                    formatter2 = formatter_d10_tw;
                }
                char c0 = text.charAt(0);
                char c1 = text.charAt(1);
                char c2 = text.charAt(2);
                char c3 = text.charAt(3);
                char c5 = text.charAt(5);
                if (c2 == '/' && c5 == '/') {
                    int v0 = ((c0 - '0') * 10) + (c1 - '0');
                    int v1 = ((c3 - '0') * 10) + (c4 - '0');
                    if (v0 > 12) {
                        formatter2 = formatter_d10_eur;
                    } else if (v1 > 12) {
                        formatter2 = formatter_d10_us;
                    } else {
                        String country = Locale.getDefault().getCountry();
                        if (country.equals("US")) {
                            formatter2 = formatter_d10_us;
                        } else if (country.equals("BR") || country.equals("AU")) {
                            formatter2 = formatter_d10_eur;
                        }
                    }
                } else if (c2 == '.' && c5 == '.') {
                    formatter2 = formatter_d10_de;
                } else if (c2 == '-' && c5 == '-') {
                    formatter2 = formatter_d10_in;
                }
            }
            if (text.length() >= 9) {
                char c42 = text.charAt(4);
                if (c42 == 24180) {
                    formatter2 = formatter_d10_cn;
                } else if (c42 == 45380) {
                    formatter2 = formatter_d10_kr;
                }
            }
            char c43 = 1;
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (ch < '0' || ch > '9') {
                    c43 = 0;
                    break;
                }
            }
            if (c43 != 0 && text.length() > 8 && text.length() < 19) {
                long epochMillis = Long.parseLong(text);
                return new LocalDateTime(epochMillis, DateTimeZone.forTimeZone(JSON.defaultTimeZone)).toLocalDate();
            }
        }
        if (formatter2 == null) {
            return LocalDate.parse(text);
        }
        return LocalDate.parse(text, formatter2);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00f2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected DateTime parseZonedDateTime(String text, DateTimeFormatter formatter) {
        DateTimeFormatter formatter2;
        DateTimeFormatter formatter3;
        if (formatter != null) {
            formatter2 = formatter;
        } else if (text.length() == 19) {
            char c4 = text.charAt(4);
            char c7 = text.charAt(7);
            char c10 = text.charAt(10);
            char c13 = text.charAt(13);
            char c16 = text.charAt(16);
            if (c13 == ':' && c16 == ':') {
                if (c4 == '-' && c7 == '-') {
                    if (c10 == 'T') {
                        formatter3 = formatter_iso8601;
                    } else if (c10 == ' ') {
                        formatter3 = defaultFormatter;
                    }
                    if (text.length() >= 17) {
                    }
                } else {
                    if (c4 != '/' || c7 != '/') {
                        char c0 = text.charAt(0);
                        char c1 = text.charAt(1);
                        char c2 = text.charAt(2);
                        char c3 = text.charAt(3);
                        char c5 = text.charAt(5);
                        if (c2 == '/' && c5 == '/') {
                            int v0 = ((c0 - '0') * 10) + (c1 - '0');
                            int v1 = ((c3 - '0') * 10) + (c4 - '0');
                            if (v0 > 12) {
                                formatter3 = formatter_dt19_eur;
                            } else if (v1 > 12) {
                                formatter3 = formatter_dt19_us;
                            } else {
                                String country = Locale.getDefault().getCountry();
                                if (country.equals("US")) {
                                    formatter3 = formatter_dt19_us;
                                } else {
                                    formatter3 = (country.equals("BR") || country.equals("AU")) ? formatter_dt19_eur : formatter;
                                }
                            }
                        } else if (c2 == '.' && c5 == '.') {
                            formatter3 = formatter_dt19_de;
                        } else if (c2 == '-' && c5 == '-') {
                            formatter3 = formatter_dt19_in;
                        }
                    } else {
                        formatter3 = formatter_dt19_tw;
                    }
                    if (text.length() >= 17) {
                    }
                }
            } else {
                formatter3 = formatter;
                if (text.length() >= 17) {
                    char c42 = text.charAt(4);
                    if (c42 == 24180) {
                        if (text.charAt(text.length() - 1) == 31186) {
                            formatter2 = formatter_dt19_cn_1;
                        } else {
                            formatter2 = formatter_dt19_cn;
                        }
                    } else if (c42 == 45380) {
                        formatter2 = formatter_dt19_kr;
                    }
                } else {
                    formatter2 = formatter3;
                }
            }
        }
        if (formatter2 == null) {
            return DateTime.parse(text);
        }
        return DateTime.parse(text, formatter2);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        if (fieldType == null) {
            fieldType = object.getClass();
        }
        if (fieldType == LocalDateTime.class) {
            int mask = SerializerFeature.UseISO8601DateFormat.getMask();
            LocalDateTime dateTime = (LocalDateTime) object;
            String format = serializer.getDateFormatPattern();
            if (format == null) {
                if ((features & mask) != 0 || serializer.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
                    format = formatter_iso8601_pattern;
                } else if (serializer.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
                    format = JSON.DEFFAULT_DATE_FORMAT;
                } else {
                    int millis = dateTime.getMillisOfSecond();
                    if (millis == 0) {
                        format = formatter_iso8601_pattern_23;
                    } else {
                        format = formatter_iso8601_pattern_29;
                    }
                }
            }
            if (format != null) {
                write(out, (ReadablePartial) dateTime, format);
                return;
            } else {
                out.writeLong(dateTime.toDateTime(DateTimeZone.forTimeZone(JSON.defaultTimeZone)).toInstant().getMillis());
                return;
            }
        }
        out.writeString(object.toString());
    }

    @Override // com.alibaba.fastjson.serializer.ContextObjectSerializer
    public void write(JSONSerializer serializer, Object object, BeanContext context) throws IOException {
        SerializeWriter out = serializer.out;
        String format = context.getFormat();
        write(out, (ReadablePartial) object, format);
    }

    private void write(SerializeWriter out, ReadablePartial object, String format) {
        DateTimeFormatter formatter;
        if (format.equals(formatter_iso8601_pattern)) {
            formatter = formatter_iso8601;
        } else {
            formatter = DateTimeFormat.forPattern(format);
        }
        String text = formatter.print(object);
        out.writeString(text);
    }
}