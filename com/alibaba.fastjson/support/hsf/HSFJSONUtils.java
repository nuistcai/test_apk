package com.alibaba.fastjson.support.hsf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class HSFJSONUtils {
    static final SymbolTable typeSymbolTable = new SymbolTable(1024);
    static final char[] fieldName_argsTypes = "\"argsTypes\"".toCharArray();
    static final char[] fieldName_argsObjs = "\"argsObjs\"".toCharArray();
    static final char[] fieldName_type = "\"@type\":".toCharArray();

    public static Object[] parseInvocationArguments(String json, MethodLocator methodLocator) throws NumberFormatException {
        Object[] values;
        DefaultJSONParser parser = new DefaultJSONParser(json);
        JSONLexerBase lexer = (JSONLexerBase) parser.getLexer();
        ParseContext rootContext = parser.setContext(null, null);
        int token = lexer.token();
        if (token != 12) {
            if (token == 14) {
                String[] typeNames = lexer.scanFieldStringArray(null, -1, typeSymbolTable);
                lexer.skipWhitespace();
                char ch = lexer.getCurrent();
                if (ch == ']') {
                    Method method = methodLocator.findMethod(null);
                    Type[] argTypes = method.getGenericParameterTypes();
                    Object[] values2 = new Object[typeNames.length];
                    for (int i = 0; i < typeNames.length; i++) {
                        Type argType = argTypes[i];
                        String typeName = typeNames[i];
                        if (argType != String.class) {
                            values2[i] = TypeUtils.cast(typeName, argType, parser.getConfig());
                        } else {
                            values2[i] = typeName;
                        }
                    }
                    return values2;
                }
                if (ch == ',') {
                    lexer.next();
                    lexer.skipWhitespace();
                }
                lexer.nextToken(14);
                Method method2 = methodLocator.findMethod(typeNames);
                Type[] argTypes2 = method2.getGenericParameterTypes();
                Object[] values3 = parser.parseArray(argTypes2);
                lexer.close();
                return values3;
            }
            return null;
        }
        String[] typeNames2 = lexer.scanFieldStringArray(fieldName_argsTypes, -1, typeSymbolTable);
        if (typeNames2 == null && lexer.matchStat == -2) {
            String type = lexer.scanFieldString(fieldName_type);
            if ("com.alibaba.fastjson.JSONObject".equals(type)) {
                typeNames2 = lexer.scanFieldStringArray(fieldName_argsTypes, -1, typeSymbolTable);
            }
        }
        Method method3 = methodLocator.findMethod(typeNames2);
        if (method3 == null) {
            lexer.close();
            JSONObject jsonObject = JSON.parseObject(json);
            Method method4 = methodLocator.findMethod((String[]) jsonObject.getObject("argsTypes", String[].class));
            JSONArray argsObjs = jsonObject.getJSONArray("argsObjs");
            if (argsObjs == null) {
                return null;
            }
            Type[] argTypes3 = method4.getGenericParameterTypes();
            Object[] values4 = new Object[argTypes3.length];
            for (int i2 = 0; i2 < argTypes3.length; i2++) {
                Type type2 = argTypes3[i2];
                values4[i2] = argsObjs.getObject(i2, type2);
            }
            return values4;
        }
        Type[] argTypes4 = method3.getGenericParameterTypes();
        lexer.skipWhitespace();
        if (lexer.getCurrent() == ',') {
            lexer.next();
        }
        if (lexer.matchField2(fieldName_argsObjs)) {
            lexer.nextToken();
            ParseContext context = parser.setContext(rootContext, null, "argsObjs");
            values = parser.parseArray(argTypes4);
            context.object = values;
            parser.accept(13);
            parser.handleResovleTask(null);
        } else {
            values = null;
        }
        parser.close();
        return values;
    }
}