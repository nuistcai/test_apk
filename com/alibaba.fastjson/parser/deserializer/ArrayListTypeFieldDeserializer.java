package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes.dex */
public class ArrayListTypeFieldDeserializer extends FieldDeserializer {
    private ObjectDeserializer deserializer;
    private int itemFastMatchToken;
    private final Type itemType;

    public ArrayListTypeFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo) {
        super(clazz, fieldInfo);
        Type fieldType = fieldInfo.fieldType;
        if (fieldType instanceof ParameterizedType) {
            Type argType = ((ParameterizedType) fieldInfo.fieldType).getActualTypeArguments()[0];
            if (argType instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) argType;
                Type[] upperBounds = wildcardType.getUpperBounds();
                if (upperBounds.length == 1) {
                    argType = upperBounds[0];
                }
            }
            this.itemType = argType;
            return;
        }
        this.itemType = Object.class;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public int getFastMatchToken() {
        return 14;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public void parseField(DefaultJSONParser parser, Object object, Type objectType, Map<String, Object> fieldValues) {
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token == 8 || (token == 4 && lexer.stringVal().length() == 0)) {
            if (object == null) {
                fieldValues.put(this.fieldInfo.name, null);
                return;
            } else {
                setValue(object, (String) null);
                return;
            }
        }
        ArrayList list = new ArrayList();
        ParseContext context = parser.getContext();
        parser.setContext(context, object, this.fieldInfo.name);
        parseArray(parser, objectType, list);
        parser.setContext(context);
        if (object == null) {
            fieldValues.put(this.fieldInfo.name, list);
        } else {
            setValue(object, list);
        }
    }

    public final void parseArray(DefaultJSONParser parser, Type objectType, Collection array) {
        ObjectDeserializer itemTypeDeser;
        ObjectDeserializer itemTypeDeser2;
        ObjectDeserializer itemTypeDeser3;
        Type itemType = this.itemType;
        ObjectDeserializer itemTypeDeser4 = this.deserializer;
        if (objectType instanceof ParameterizedType) {
            if (itemType instanceof TypeVariable) {
                TypeVariable typeVar = (TypeVariable) itemType;
                ParameterizedType paramType = (ParameterizedType) objectType;
                Class<?> objectClass = null;
                if (paramType.getRawType() instanceof Class) {
                    objectClass = (Class) paramType.getRawType();
                }
                int paramIndex = -1;
                if (objectClass != null) {
                    int i = 0;
                    int size = objectClass.getTypeParameters().length;
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        if (!objectClass.getTypeParameters()[i].getName().equals(typeVar.getName())) {
                            i++;
                        } else {
                            paramIndex = i;
                            break;
                        }
                    }
                }
                if (paramIndex != -1) {
                    itemType = paramType.getActualTypeArguments()[paramIndex];
                    if (!itemType.equals(this.itemType)) {
                        itemTypeDeser4 = parser.getConfig().getDeserializer(itemType);
                    }
                }
            } else if (!(itemType instanceof ParameterizedType)) {
                itemTypeDeser = itemTypeDeser4;
                itemTypeDeser4 = itemTypeDeser;
            } else {
                ParameterizedType parameterizedItemType = (ParameterizedType) itemType;
                Type[] itemActualTypeArgs = parameterizedItemType.getActualTypeArguments();
                if (itemActualTypeArgs.length != 1 || !(itemActualTypeArgs[0] instanceof TypeVariable)) {
                    itemTypeDeser3 = itemTypeDeser4;
                } else {
                    TypeVariable typeVar2 = (TypeVariable) itemActualTypeArgs[0];
                    ParameterizedType paramType2 = (ParameterizedType) objectType;
                    Class<?> objectClass2 = null;
                    if (paramType2.getRawType() instanceof Class) {
                        objectClass2 = (Class) paramType2.getRawType();
                    }
                    int paramIndex2 = -1;
                    if (objectClass2 == null) {
                        itemTypeDeser3 = itemTypeDeser4;
                    } else {
                        int i2 = 0;
                        int size2 = objectClass2.getTypeParameters().length;
                        while (true) {
                            if (i2 >= size2) {
                                itemTypeDeser3 = itemTypeDeser4;
                                break;
                            }
                            itemTypeDeser3 = itemTypeDeser4;
                            if (!objectClass2.getTypeParameters()[i2].getName().equals(typeVar2.getName())) {
                                i2++;
                                itemTypeDeser4 = itemTypeDeser3;
                            } else {
                                paramIndex2 = i2;
                                break;
                            }
                        }
                    }
                    if (paramIndex2 != -1) {
                        itemActualTypeArgs[0] = paramType2.getActualTypeArguments()[paramIndex2];
                        itemType = TypeReference.intern(new ParameterizedTypeImpl(itemActualTypeArgs, parameterizedItemType.getOwnerType(), parameterizedItemType.getRawType()));
                    }
                }
                itemTypeDeser4 = itemTypeDeser3;
            }
        } else {
            itemTypeDeser = itemTypeDeser4;
            if ((itemType instanceof TypeVariable) && (objectType instanceof Class)) {
                Class objectClass3 = (Class) objectType;
                TypeVariable typeVar3 = (TypeVariable) itemType;
                objectClass3.getTypeParameters();
                int i3 = 0;
                int size3 = objectClass3.getTypeParameters().length;
                while (true) {
                    if (i3 >= size3) {
                        break;
                    }
                    TypeVariable item = objectClass3.getTypeParameters()[i3];
                    if (!item.getName().equals(typeVar3.getName())) {
                        i3++;
                    } else {
                        Type[] bounds = item.getBounds();
                        if (bounds.length == 1) {
                            itemType = bounds[0];
                            itemTypeDeser4 = itemTypeDeser;
                        }
                    }
                }
                itemTypeDeser4 = itemTypeDeser;
            } else {
                itemTypeDeser4 = itemTypeDeser;
            }
        }
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token == 14) {
            if (itemTypeDeser4 != null) {
                itemTypeDeser2 = itemTypeDeser4;
            } else {
                ObjectDeserializer itemTypeDeser5 = parser.getConfig().getDeserializer(itemType);
                this.deserializer = itemTypeDeser5;
                this.itemFastMatchToken = this.deserializer.getFastMatchToken();
                itemTypeDeser2 = itemTypeDeser5;
            }
            lexer.nextToken(this.itemFastMatchToken);
            int i4 = 0;
            while (true) {
                if (lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (lexer.token() == 16) {
                        lexer.nextToken();
                    }
                }
                if (lexer.token() != 15) {
                    Object val = itemTypeDeser2.deserialze(parser, itemType, Integer.valueOf(i4));
                    array.add(val);
                    parser.checkListResolve(array);
                    if (lexer.token() == 16) {
                        lexer.nextToken(this.itemFastMatchToken);
                    }
                    i4++;
                } else {
                    lexer.nextToken(16);
                    return;
                }
            }
        } else {
            if (token == 4 && this.fieldInfo.unwrapped) {
                String str = lexer.stringVal();
                lexer.nextToken();
                DefaultJSONParser valueParser = new DefaultJSONParser(str);
                valueParser.parseArray(array);
                return;
            }
            if (itemTypeDeser4 == null) {
                ObjectDeserializer deserializer = parser.getConfig().getDeserializer(itemType);
                this.deserializer = deserializer;
                itemTypeDeser4 = deserializer;
            }
            Object val2 = itemTypeDeser4.deserialze(parser, itemType, 0);
            array.add(val2);
            parser.checkListResolve(array);
        }
    }
}