package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextObjectSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class Jdk8DateCodec extends ContextObjectDeserializer implements ObjectSerializer, ContextObjectSerializer, ObjectDeserializer {
    private static final String formatter_iso8601_pattern_23 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String formatter_iso8601_pattern_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    public static final Jdk8DateCodec instance = new Jdk8DateCodec();
    private static final String defaultPatttern = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(defaultPatttern);
    private static final DateTimeFormatter defaultFormatter_23 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter formatter_dt19_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn_1 = DateTimeFormatter.ofPattern("yyyy年M月d日 H时m分s秒");
    private static final DateTimeFormatter formatter_dt19_kr = DateTimeFormatter.ofPattern("yyyy년M월d일 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_us = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_de = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_in = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_d8 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter formatter_d10_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter formatter_d10_cn = DateTimeFormatter.ofPattern("yyyy年M月d日");
    private static final DateTimeFormatter formatter_d10_kr = DateTimeFormatter.ofPattern("yyyy년M월d일");
    private static final DateTimeFormatter formatter_d10_us = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter_d10_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatter_d10_de = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatter_d10_in = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormatter.ofPattern(defaultPatttern).withZone(ZoneId.systemDefault());
    private static final String formatter_iso8601_pattern = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter formatter_iso8601 = DateTimeFormatter.ofPattern(formatter_iso8601_pattern);

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) {
        Long l;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (jSONLexer.token() == 4) {
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextToken();
            DateTimeFormatter dateTimeFormatterOfPattern = null;
            if (str != null) {
                if (defaultPatttern.equals(str)) {
                    dateTimeFormatterOfPattern = defaultFormatter;
                } else {
                    dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern(str);
                }
            }
            if ("".equals(strStringVal)) {
                return null;
            }
            if (type == LocalDateTime.class) {
                if (strStringVal.length() == 10 || strStringVal.length() == 8) {
                    return (T) LocalDateTime.of(parseLocalDate(strStringVal, str, dateTimeFormatterOfPattern), LocalTime.MIN);
                }
                return (T) parseDateTime(strStringVal, dateTimeFormatterOfPattern);
            }
            if (type == LocalDate.class) {
                if (strStringVal.length() == 23) {
                    LocalDateTime localDateTime = LocalDateTime.parse(strStringVal);
                    return (T) LocalDate.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
                }
                return (T) parseLocalDate(strStringVal, str, dateTimeFormatterOfPattern);
            }
            if (type == LocalTime.class) {
                if (strStringVal.length() == 23) {
                    LocalDateTime localDateTime2 = LocalDateTime.parse(strStringVal);
                    return (T) LocalTime.of(localDateTime2.getHour(), localDateTime2.getMinute(), localDateTime2.getSecond(), localDateTime2.getNano());
                }
                boolean z = true;
                for (int i2 = 0; i2 < strStringVal.length(); i2++) {
                    char cCharAt = strStringVal.charAt(i2);
                    if (cCharAt < '0' || cCharAt > '9') {
                        z = false;
                        break;
                    }
                }
                if (z && strStringVal.length() > 8 && strStringVal.length() < 19) {
                    return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(strStringVal)), JSON.defaultTimeZone.toZoneId()).toLocalTime();
                }
                return (T) LocalTime.parse(strStringVal);
            }
            if (type == ZonedDateTime.class) {
                if (dateTimeFormatterOfPattern == defaultFormatter) {
                    dateTimeFormatterOfPattern = ISO_FIXED_FORMAT;
                }
                if (dateTimeFormatterOfPattern == null && strStringVal.length() <= 19) {
                    JSONScanner jSONScanner = new JSONScanner(strStringVal);
                    TimeZone timeZone = defaultJSONParser.lexer.getTimeZone();
                    jSONScanner.setTimeZone(timeZone);
                    if (jSONScanner.scanISO8601DateIfMatch(false)) {
                        return (T) ZonedDateTime.ofInstant(jSONScanner.getCalendar().getTime().toInstant(), timeZone.toZoneId());
                    }
                }
                return (T) parseZonedDateTime(strStringVal, dateTimeFormatterOfPattern);
            }
            if (type == OffsetDateTime.class) {
                return (T) OffsetDateTime.parse(strStringVal);
            }
            if (type == OffsetTime.class) {
                return (T) OffsetTime.parse(strStringVal);
            }
            if (type == ZoneId.class) {
                return (T) ZoneId.of(strStringVal);
            }
            if (type == Period.class) {
                return (T) Period.parse(strStringVal);
            }
            if (type == Duration.class) {
                return (T) Duration.parse(strStringVal);
            }
            if (type == Instant.class) {
                boolean z2 = true;
                for (int i3 = 0; i3 < strStringVal.length(); i3++) {
                    char cCharAt2 = strStringVal.charAt(i3);
                    if (cCharAt2 < '0' || cCharAt2 > '9') {
                        z2 = false;
                        break;
                    }
                }
                if (z2 && strStringVal.length() > 8 && strStringVal.length() < 19) {
                    return (T) Instant.ofEpochMilli(Long.parseLong(strStringVal));
                }
                return (T) Instant.parse(strStringVal);
            }
        } else {
            if (jSONLexer.token() == 2) {
                long jLongValue = jSONLexer.longValue();
                jSONLexer.nextToken();
                if ("unixtime".equals(str)) {
                    jLongValue *= 1000;
                } else if ("yyyyMMddHHmmss".equals(str)) {
                    int i4 = (int) (jLongValue / 10000000000L);
                    int i5 = (int) ((jLongValue / 100000000) % 100);
                    int i6 = (int) ((jLongValue / 1000000) % 100);
                    int i7 = (int) ((jLongValue / 10000) % 100);
                    int i8 = (int) ((jLongValue / 100) % 100);
                    int i9 = (int) (jLongValue % 100);
                    if (type == LocalDateTime.class) {
                        return (T) LocalDateTime.of(i4, i5, i6, i7, i8, i9);
                    }
                }
                if (type == LocalDateTime.class) {
                    return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(jLongValue), JSON.defaultTimeZone.toZoneId());
                }
                if (type == LocalDate.class) {
                    return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(jLongValue), JSON.defaultTimeZone.toZoneId()).toLocalDate();
                }
                if (type == LocalTime.class) {
                    return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(jLongValue), JSON.defaultTimeZone.toZoneId()).toLocalTime();
                }
                if (type == ZonedDateTime.class) {
                    return (T) ZonedDateTime.ofInstant(Instant.ofEpochMilli(jLongValue), JSON.defaultTimeZone.toZoneId());
                }
                if (type == Instant.class) {
                    return (T) Instant.ofEpochMilli(jLongValue);
                }
                throw new UnsupportedOperationException();
            }
            if (jSONLexer.token() == 12) {
                JSONObject object = defaultJSONParser.parseObject();
                if (type == Instant.class) {
                    Object obj2 = object.get("epochSecond");
                    Object obj3 = object.get("nano");
                    if ((obj2 instanceof Number) && (obj3 instanceof Number)) {
                        return (T) Instant.ofEpochSecond(TypeUtils.longExtractValue((Number) obj2), TypeUtils.longExtractValue((Number) obj3));
                    }
                    if (obj2 instanceof Number) {
                        return (T) Instant.ofEpochSecond(TypeUtils.longExtractValue((Number) obj2));
                    }
                } else if (type == Duration.class && (l = object.getLong("seconds")) != null) {
                    return (T) Duration.ofSeconds(l.longValue(), object.getLongValue("nano"));
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0124  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected LocalDateTime parseDateTime(String text, DateTimeFormatter formatter) throws NumberFormatException {
        DateTimeFormatter formatter2;
        DateTimeFormatter formatter3;
        DateTimeFormatter formatter4;
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
                            formatter3 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        } else if (c10 == ' ') {
                            formatter3 = defaultFormatter;
                        }
                    } else if (c4 == '/' && c7 == '/') {
                        formatter3 = formatter_dt19_tw;
                    } else {
                        char c0 = text.charAt(0);
                        char c1 = text.charAt(1);
                        char c2 = text.charAt(2);
                        char c3 = text.charAt(3);
                        char c5 = text.charAt(5);
                        if (c2 == '/' && c5 == '/') {
                            int v0 = ((c0 - '0') * 10) + (c1 - '0');
                            int v1 = ((c3 - '0') * 10) + (c4 - '0');
                            if (v0 > 12) {
                                formatter4 = formatter_dt19_eur;
                            } else if (v1 > 12) {
                                formatter4 = formatter_dt19_us;
                            } else {
                                String country = Locale.getDefault().getCountry();
                                if (country.equals("US")) {
                                    formatter4 = formatter_dt19_us;
                                } else {
                                    formatter4 = (country.equals("BR") || country.equals("AU")) ? formatter_dt19_eur : formatter;
                                }
                            }
                            formatter3 = formatter4;
                        } else if (c2 == '.' && c5 == '.') {
                            formatter3 = formatter_dt19_de;
                        } else if (c2 == '-' && c5 == '-') {
                            formatter3 = formatter_dt19_in;
                        }
                    }
                } else {
                    formatter3 = formatter;
                }
            } else if (text.length() == 23) {
                char c42 = text.charAt(4);
                char c72 = text.charAt(7);
                char c102 = text.charAt(10);
                char c132 = text.charAt(13);
                char c162 = text.charAt(16);
                char c19 = text.charAt(19);
                if (c132 == ':' && c162 == ':' && c42 == '-' && c72 == '-' && c102 == ' ' && c19 == '.') {
                    formatter3 = defaultFormatter_23;
                } else {
                    formatter3 = formatter;
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
            } else {
                formatter2 = formatter3;
            }
        }
        if (formatter2 == null) {
            JSONScanner dateScanner = new JSONScanner(text);
            if (dateScanner.scanISO8601DateIfMatch(false)) {
                Instant instant = dateScanner.getCalendar().toInstant();
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            }
            boolean digit = true;
            for (int i = 0; i < text.length(); i++) {
                char ch = text.charAt(i);
                if (ch < '0' || ch > '9') {
                    digit = false;
                    break;
                }
            }
            if (digit && text.length() > 8 && text.length() < 19) {
                long epochMillis = Long.parseLong(text);
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), JSON.defaultTimeZone.toZoneId());
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
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), JSON.defaultTimeZone.toZoneId()).toLocalDate();
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
    /* JADX WARN: Removed duplicated region for block: B:64:0x00fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected ZonedDateTime parseZonedDateTime(String text, DateTimeFormatter formatter) throws NumberFormatException {
        DateTimeFormatter formatter2;
        DateTimeFormatter formatter3;
        boolean digit;
        int i;
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
                        formatter3 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
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
                    digit = true;
                    while (i < text.length()) {
                    }
                    if (digit) {
                        long epochMillis = Long.parseLong(text);
                        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), JSON.defaultTimeZone.toZoneId());
                    }
                } else {
                    formatter2 = formatter3;
                    digit = true;
                    for (i = 0; i < text.length(); i++) {
                        char ch = text.charAt(i);
                        if (ch < '0' || ch > '9') {
                            digit = false;
                            break;
                        }
                    }
                    if (digit && text.length() > 8 && text.length() < 19) {
                        long epochMillis2 = Long.parseLong(text);
                        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMillis2), JSON.defaultTimeZone.toZoneId());
                    }
                }
            }
        }
        if (formatter2 == null) {
            return ZonedDateTime.parse(text);
        }
        return ZonedDateTime.parse(text, formatter2);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [java.time.ZonedDateTime] */
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
                    if (serializer.getFastJsonConfigDateFormatPattern() != null && serializer.getFastJsonConfigDateFormatPattern().length() > 0) {
                        format = serializer.getFastJsonConfigDateFormatPattern();
                    } else {
                        format = JSON.DEFFAULT_DATE_FORMAT;
                    }
                } else {
                    int nano = dateTime.getNano();
                    if (nano == 0) {
                        format = formatter_iso8601_pattern;
                    } else if (nano % 1000000 == 0) {
                        format = formatter_iso8601_pattern_23;
                    } else {
                        format = formatter_iso8601_pattern_29;
                    }
                }
            }
            if (format != null) {
                write(out, dateTime, format);
                return;
            } else {
                out.writeLong(dateTime.atZone(JSON.defaultTimeZone.toZoneId()).toInstant().toEpochMilli());
                return;
            }
        }
        out.writeString(object.toString());
    }

    @Override // com.alibaba.fastjson.serializer.ContextObjectSerializer
    public void write(JSONSerializer serializer, Object object, BeanContext context) throws IOException {
        SerializeWriter out = serializer.out;
        String format = context.getFormat();
        write(out, (TemporalAccessor) object, format);
    }

    /* JADX WARN: Type inference failed for: r1v13, types: [java.time.ZonedDateTime] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.time.ZonedDateTime] */
    private void write(SerializeWriter out, TemporalAccessor object, String format) {
        DateTimeFormatter formatter;
        if ("unixtime".equals(format)) {
            if (object instanceof ChronoZonedDateTime) {
                long seconds = ((ChronoZonedDateTime) object).toEpochSecond();
                out.writeInt((int) seconds);
                return;
            } else if (object instanceof LocalDateTime) {
                long seconds2 = ((LocalDateTime) object).atZone(JSON.defaultTimeZone.toZoneId()).toEpochSecond();
                out.writeInt((int) seconds2);
                return;
            }
        }
        if ("millis".equals(format)) {
            Instant instant = null;
            if (object instanceof ChronoZonedDateTime) {
                instant = ((ChronoZonedDateTime) object).toInstant();
            } else if (object instanceof LocalDateTime) {
                instant = ((LocalDateTime) object).atZone(JSON.defaultTimeZone.toZoneId()).toInstant();
            }
            if (instant != null) {
                long millis = instant.toEpochMilli();
                out.writeLong(millis);
                return;
            }
        }
        if (format == formatter_iso8601_pattern) {
            formatter = formatter_iso8601;
        } else {
            formatter = DateTimeFormatter.ofPattern(format);
        }
        String text = formatter.format(object);
        out.writeString(text);
    }

    public static Object castToLocalDateTime(Object value, String format) {
        if (value == null) {
            return null;
        }
        if (format == null) {
            format = defaultPatttern;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(value.toString(), df);
    }
}