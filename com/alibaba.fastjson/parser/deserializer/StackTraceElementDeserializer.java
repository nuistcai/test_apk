package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class StackTraceElementDeserializer implements ObjectDeserializer {
    public static final StackTraceElementDeserializer instance = new StackTraceElementDeserializer();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        int i = 16;
        if (jSONLexer.token() != 12 && jSONLexer.token() != 16) {
            throw new JSONException("syntax error: " + JSONToken.name(jSONLexer.token()));
        }
        String strStringVal = null;
        String strStringVal2 = null;
        String strStringVal3 = null;
        int iIntValue = 0;
        while (true) {
            String strScanSymbol = jSONLexer.scanSymbol(defaultJSONParser.getSymbolTable());
            if (strScanSymbol == null) {
                if (jSONLexer.token() == 13) {
                    jSONLexer.nextToken(i);
                    break;
                }
                if (jSONLexer.token() != i || !jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            jSONLexer.nextTokenWithColon(4);
            if ("className".equals(strScanSymbol)) {
                if (jSONLexer.token() != 8) {
                    if (jSONLexer.token() == 4) {
                        strStringVal = jSONLexer.stringVal();
                    } else {
                        throw new JSONException("syntax error");
                    }
                } else {
                    strStringVal = null;
                }
            } else if ("methodName".equals(strScanSymbol)) {
                if (jSONLexer.token() != 8) {
                    if (jSONLexer.token() == 4) {
                        strStringVal2 = jSONLexer.stringVal();
                    } else {
                        throw new JSONException("syntax error");
                    }
                } else {
                    strStringVal2 = null;
                }
            } else if ("fileName".equals(strScanSymbol)) {
                if (jSONLexer.token() != 8) {
                    if (jSONLexer.token() == 4) {
                        strStringVal3 = jSONLexer.stringVal();
                    } else {
                        throw new JSONException("syntax error");
                    }
                } else {
                    strStringVal3 = null;
                }
            } else if ("lineNumber".equals(strScanSymbol)) {
                if (jSONLexer.token() == 8) {
                    iIntValue = 0;
                } else if (jSONLexer.token() == 2) {
                    iIntValue = jSONLexer.intValue();
                } else {
                    throw new JSONException("syntax error");
                }
            } else if ("nativeMethod".equals(strScanSymbol)) {
                if (jSONLexer.token() == 8 || jSONLexer.token() == 6 || jSONLexer.token() == 7) {
                    jSONLexer.nextToken(i);
                } else {
                    throw new JSONException("syntax error");
                }
            } else if (strScanSymbol == JSON.DEFAULT_TYPE_KEY) {
                if (jSONLexer.token() == 4) {
                    String strStringVal4 = jSONLexer.stringVal();
                    if (!strStringVal4.equals("java.lang.StackTraceElement")) {
                        throw new JSONException("syntax error : " + strStringVal4);
                    }
                } else if (jSONLexer.token() != 8) {
                    throw new JSONException("syntax error");
                }
            } else if ("moduleName".equals(strScanSymbol)) {
                if (jSONLexer.token() != 8) {
                    if (jSONLexer.token() == 4) {
                        jSONLexer.stringVal();
                    } else {
                        throw new JSONException("syntax error");
                    }
                }
            } else if ("moduleVersion".equals(strScanSymbol)) {
                if (jSONLexer.token() != 8) {
                    if (jSONLexer.token() == 4) {
                        jSONLexer.stringVal();
                    } else {
                        throw new JSONException("syntax error");
                    }
                }
            } else if ("classLoaderName".equals(strScanSymbol)) {
                if (jSONLexer.token() != 8) {
                    if (jSONLexer.token() == 4) {
                        jSONLexer.stringVal();
                    } else {
                        throw new JSONException("syntax error");
                    }
                }
            } else {
                throw new JSONException("syntax error : " + strScanSymbol);
            }
            if (jSONLexer.token() != 13) {
                i = 16;
            } else {
                jSONLexer.nextToken(16);
                break;
            }
        }
        return (T) new StackTraceElement(strStringVal, strStringVal2, strStringVal3, iIntValue);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }
}