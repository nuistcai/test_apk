package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes.dex */
public abstract class AbstractDateDeserializer extends ContextObjectDeserializer implements ObjectDeserializer {
    protected abstract <T> T cast(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2);

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) throws Throwable {
        Object time;
        Object obj2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 2) {
            long jLongValue = jSONLexer.longValue();
            jSONLexer.nextToken(16);
            if ("unixtime".equals(str)) {
                jLongValue *= 1000;
            }
            time = Long.valueOf(jLongValue);
        } else if (jSONLexer.token() == 4) {
            String strStringVal = jSONLexer.stringVal();
            if (str != null) {
                if ("yyyy-MM-dd HH:mm:ss.SSSSSSSSS".equals(str) && (type instanceof Class) && ((Class) type).getName().equals("java.sql.Timestamp")) {
                    return (T) TypeUtils.castToTimestamp(strStringVal);
                }
                SimpleDateFormat simpleDateFormat = null;
                try {
                    simpleDateFormat = new SimpleDateFormat(str, defaultJSONParser.lexer.getLocale());
                } catch (IllegalArgumentException e) {
                    if (str.contains("T")) {
                        try {
                            simpleDateFormat = new SimpleDateFormat(str.replaceAll("T", "'T'"), defaultJSONParser.lexer.getLocale());
                        } catch (IllegalArgumentException e2) {
                            throw e;
                        }
                    }
                }
                if (JSON.defaultTimeZone != null) {
                    simpleDateFormat.setTimeZone(defaultJSONParser.lexer.getTimeZone());
                }
                try {
                    obj2 = simpleDateFormat.parse(strStringVal);
                } catch (ParseException e3) {
                    obj2 = null;
                }
                if (obj2 == null && JSON.defaultLocale == Locale.CHINA) {
                    try {
                        simpleDateFormat = new SimpleDateFormat(str, Locale.US);
                    } catch (IllegalArgumentException e4) {
                        if (str.contains("T")) {
                            try {
                                simpleDateFormat = new SimpleDateFormat(str.replaceAll("T", "'T'"), defaultJSONParser.lexer.getLocale());
                            } catch (IllegalArgumentException e5) {
                                throw e4;
                            }
                        }
                    }
                    simpleDateFormat.setTimeZone(defaultJSONParser.lexer.getTimeZone());
                    try {
                        obj2 = simpleDateFormat.parse(strStringVal);
                    } catch (ParseException e6) {
                        obj2 = null;
                    }
                }
                if (obj2 == null) {
                    if (str.equals("yyyy-MM-dd'T'HH:mm:ss.SSS") && strStringVal.length() == 19) {
                        try {
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", JSON.defaultLocale);
                            simpleDateFormat2.setTimeZone(JSON.defaultTimeZone);
                            obj2 = simpleDateFormat2.parse(strStringVal);
                        } catch (ParseException e7) {
                            obj2 = null;
                        }
                    } else {
                        obj2 = null;
                    }
                }
            } else {
                obj2 = null;
            }
            if (obj2 != null) {
                time = obj2;
            } else {
                time = strStringVal;
                jSONLexer.nextToken(16);
                if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                    JSONScanner jSONScanner = new JSONScanner(strStringVal);
                    if (jSONScanner.scanISO8601DateIfMatch()) {
                        time = jSONScanner.getCalendar().getTime();
                    }
                    jSONScanner.close();
                }
            }
        } else if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            time = null;
        } else if (jSONLexer.token() == 12) {
            jSONLexer.nextToken();
            if (jSONLexer.token() == 4) {
                if (JSON.DEFAULT_TYPE_KEY.equals(jSONLexer.stringVal())) {
                    jSONLexer.nextToken();
                    defaultJSONParser.accept(17);
                    Class<?> clsCheckAutoType = defaultJSONParser.getConfig().checkAutoType(jSONLexer.stringVal(), null, jSONLexer.getFeatures());
                    if (clsCheckAutoType != null) {
                        type = clsCheckAutoType;
                    }
                    defaultJSONParser.accept(4);
                    defaultJSONParser.accept(16);
                }
                jSONLexer.nextTokenWithColon(2);
                if (jSONLexer.token() == 2) {
                    long jLongValue2 = jSONLexer.longValue();
                    jSONLexer.nextToken();
                    Long lValueOf = Long.valueOf(jLongValue2);
                    defaultJSONParser.accept(13);
                    time = lValueOf;
                } else {
                    throw new JSONException("syntax error : " + jSONLexer.tokenName());
                }
            } else {
                throw new JSONException("syntax error");
            }
        } else if (defaultJSONParser.getResolveStatus() == 2) {
            defaultJSONParser.setResolveStatus(0);
            defaultJSONParser.accept(16);
            if (jSONLexer.token() == 4) {
                if (!"val".equals(jSONLexer.stringVal())) {
                    throw new JSONException("syntax error");
                }
                jSONLexer.nextToken();
                defaultJSONParser.accept(17);
                time = defaultJSONParser.parse();
                defaultJSONParser.accept(13);
            } else {
                throw new JSONException("syntax error");
            }
        } else {
            time = defaultJSONParser.parse();
        }
        return (T) cast(defaultJSONParser, type, obj, time);
    }
}