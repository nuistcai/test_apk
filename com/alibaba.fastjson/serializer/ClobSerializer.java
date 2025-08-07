package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Clob;
import java.sql.SQLException;

/* loaded from: classes.dex */
public class ClobSerializer implements ObjectSerializer {
    public static final ClobSerializer instance = new ClobSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws SQLException, IOException {
        try {
            if (object == null) {
                serializer.writeNull();
                return;
            }
            Clob clob = (Clob) object;
            Reader reader = clob.getCharacterStream();
            StringBuilder buf = new StringBuilder();
            try {
                char[] chars = new char[2048];
                while (true) {
                    int len = reader.read(chars, 0, chars.length);
                    if (len >= 0) {
                        buf.append(chars, 0, len);
                    } else {
                        String text = buf.toString();
                        reader.close();
                        serializer.write(text);
                        return;
                    }
                }
            } catch (Exception ex) {
                throw new JSONException("read string from reader error", ex);
            }
        } catch (SQLException e) {
            throw new IOException("write clob error", e);
        }
    }
}