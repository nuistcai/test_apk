package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CharArrayCodec implements ObjectDeserializer {
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T deserialze(DefaultJSONParser defaultJSONParser) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 4) {
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
            return (T) strStringVal.toCharArray();
        }
        if (jSONLexer.token() == 2) {
            Number numberIntegerValue = jSONLexer.integerValue();
            jSONLexer.nextToken(16);
            return (T) numberIntegerValue.toString().toCharArray();
        }
        Object obj = defaultJSONParser.parse();
        if (obj instanceof String) {
            return (T) ((String) obj).toCharArray();
        }
        if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            boolean z = true;
            Iterator it = collection.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if ((next instanceof String) && ((String) next).length() != 1) {
                    z = false;
                    break;
                }
            }
            if (!z) {
                throw new JSONException("can not cast to char[]");
            }
            char[] cArr = new char[collection.size()];
            int i = 0;
            Iterator it2 = collection.iterator();
            while (it2.hasNext()) {
                cArr[i] = ((String) it2.next()).charAt(0);
                i++;
            }
            return cArr;
        }
        if (obj == null) {
            return null;
        }
        return (T) JSON.toJSONString(obj).toCharArray();
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }
}