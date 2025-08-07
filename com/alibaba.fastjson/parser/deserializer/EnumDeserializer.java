package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class EnumDeserializer implements ObjectDeserializer {
    protected final Class<?> enumClass;
    protected long[] enumNameHashCodes;
    protected final Enum[] enums;
    protected final Enum[] ordinalEnums;

    public EnumDeserializer(Class<?> enumClass) throws NoSuchFieldException {
        String[] strArr;
        String jsonFieldName;
        this.enumClass = enumClass;
        this.ordinalEnums = (Enum[]) enumClass.getEnumConstants();
        Map<Long, Enum> enumMap = new HashMap<>();
        for (int i = 0; i < this.ordinalEnums.length; i++) {
            Enum e = this.ordinalEnums[i];
            String name = e.name();
            JSONField jsonField = null;
            try {
                Field field = enumClass.getField(name);
                jsonField = (JSONField) TypeUtils.getAnnotation(field, JSONField.class);
                if (jsonField != null && (jsonFieldName = jsonField.name()) != null && jsonFieldName.length() > 0) {
                    name = jsonFieldName;
                }
            } catch (Exception e2) {
            }
            long hash = TypeUtils.fnv1a_64_magic_hashcode;
            long hash_lower = TypeUtils.fnv1a_64_magic_hashcode;
            for (int j = 0; j < name.length(); j++) {
                char ch = name.charAt(j);
                long hash2 = hash ^ ch;
                int i2 = (ch < 'A' || ch > 'Z') ? ch : ch + ' ';
                hash = hash2 * TypeUtils.fnv1a_64_magic_prime;
                hash_lower = (hash_lower ^ i2) * TypeUtils.fnv1a_64_magic_prime;
            }
            enumMap.put(Long.valueOf(hash), e);
            if (hash != hash_lower) {
                enumMap.put(Long.valueOf(hash_lower), e);
            }
            if (jsonField != null) {
                String[] strArrAlternateNames = jsonField.alternateNames();
                int length = strArrAlternateNames.length;
                int i3 = 0;
                while (i3 < length) {
                    String alterName = strArrAlternateNames[i3];
                    long alterNameHash = TypeUtils.fnv1a_64_magic_hashcode;
                    int j2 = 0;
                    while (true) {
                        strArr = strArrAlternateNames;
                        if (j2 >= alterName.length()) {
                            break;
                        }
                        alterNameHash = (alterNameHash ^ alterName.charAt(j2)) * TypeUtils.fnv1a_64_magic_prime;
                        j2++;
                        strArrAlternateNames = strArr;
                        name = name;
                        jsonField = jsonField;
                    }
                    String name2 = name;
                    JSONField jsonField2 = jsonField;
                    if (alterNameHash != hash && alterNameHash != hash_lower) {
                        enumMap.put(Long.valueOf(alterNameHash), e);
                    }
                    i3++;
                    strArrAlternateNames = strArr;
                    name = name2;
                    jsonField = jsonField2;
                }
            }
        }
        this.enumNameHashCodes = new long[enumMap.size()];
        int i4 = 0;
        for (Long h : enumMap.keySet()) {
            this.enumNameHashCodes[i4] = h.longValue();
            i4++;
        }
        Arrays.sort(this.enumNameHashCodes);
        this.enums = new Enum[this.enumNameHashCodes.length];
        for (int i5 = 0; i5 < this.enumNameHashCodes.length; i5++) {
            long hash3 = this.enumNameHashCodes[i5];
            this.enums[i5] = enumMap.get(Long.valueOf(hash3));
        }
    }

    public Enum getEnumByHashCode(long hashCode) {
        int enumIndex;
        if (this.enums != null && (enumIndex = Arrays.binarySearch(this.enumNameHashCodes, hashCode)) >= 0) {
            return this.enums[enumIndex];
        }
        return null;
    }

    public Enum<?> valueOf(int ordinal) {
        return this.ordinalEnums[ordinal];
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        try {
            JSONLexer jSONLexer = defaultJSONParser.lexer;
            int i = jSONLexer.token();
            if (i == 2) {
                int iIntValue = jSONLexer.intValue();
                jSONLexer.nextToken(16);
                if (iIntValue < 0 || iIntValue >= this.ordinalEnums.length) {
                    throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + iIntValue);
                }
                return (T) this.ordinalEnums[iIntValue];
            }
            if (i == 4) {
                String strStringVal = jSONLexer.stringVal();
                jSONLexer.nextToken(16);
                if (strStringVal.length() == 0) {
                    return null;
                }
                long j = TypeUtils.fnv1a_64_magic_hashcode;
                long j2 = TypeUtils.fnv1a_64_magic_hashcode;
                for (int i2 = 0; i2 < strStringVal.length(); i2++) {
                    char cCharAt = strStringVal.charAt(i2);
                    long j3 = j ^ cCharAt;
                    int i3 = (cCharAt < 'A' || cCharAt > 'Z') ? cCharAt : cCharAt + ' ';
                    j = j3 * TypeUtils.fnv1a_64_magic_prime;
                    j2 = (j2 ^ i3) * TypeUtils.fnv1a_64_magic_prime;
                }
                T t = (T) getEnumByHashCode(j);
                if (t == null && j2 != j) {
                    t = (T) getEnumByHashCode(j2);
                }
                if (t == null && jSONLexer.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                    throw new JSONException("not match enum value, " + this.enumClass.getName() + " : " + strStringVal);
                }
                return t;
            }
            if (i == 8) {
                jSONLexer.nextToken(16);
                return null;
            }
            throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + defaultJSONParser.parse());
        } catch (JSONException e) {
            throw e;
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}