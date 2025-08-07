package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

/* loaded from: classes.dex */
public class MiscCodec implements ObjectSerializer, ObjectDeserializer {
    private static boolean FILE_RELATIVE_PATH_SUPPORT;
    private static Method method_paths_get;
    public static final MiscCodec instance = new MiscCodec();
    private static boolean method_paths_get_error = false;

    static {
        FILE_RELATIVE_PATH_SUPPORT = false;
        FILE_RELATIVE_PATH_SUPPORT = "true".equals(IOUtils.getStringProperty("fastjson.deserializer.fileRelativePathSupport"));
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws TransformerException, TransformerFactoryConfigurationError, IOException {
        String pattern;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        Class<?> objClass = object.getClass();
        if (objClass == SimpleDateFormat.class) {
            pattern = ((SimpleDateFormat) object).toPattern();
            if (out.isEnabled(SerializerFeature.WriteClassName) && object.getClass() != fieldType) {
                out.write(123);
                out.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                serializer.write(object.getClass().getName());
                out.writeFieldValue(',', "val", pattern);
                out.write(125);
                return;
            }
        } else if (objClass == Class.class) {
            Class<?> clazz = (Class) object;
            pattern = clazz.getName();
        } else {
            if (objClass == InetSocketAddress.class) {
                InetSocketAddress address = (InetSocketAddress) object;
                InetAddress inetAddress = address.getAddress();
                out.write(123);
                if (inetAddress != null) {
                    out.writeFieldName("address");
                    serializer.write(inetAddress);
                    out.write(44);
                }
                out.writeFieldName("port");
                out.writeInt(address.getPort());
                out.write(125);
                return;
            }
            if (object instanceof File) {
                pattern = ((File) object).getPath();
            } else if (object instanceof InetAddress) {
                pattern = ((InetAddress) object).getHostAddress();
            } else if (object instanceof TimeZone) {
                TimeZone timeZone = (TimeZone) object;
                pattern = timeZone.getID();
            } else if (object instanceof Currency) {
                Currency currency = (Currency) object;
                pattern = currency.getCurrencyCode();
            } else {
                if (object instanceof JSONStreamAware) {
                    JSONStreamAware aware = (JSONStreamAware) object;
                    aware.writeJSONString(out);
                    return;
                }
                if (object instanceof Iterator) {
                    Iterator<?> it = (Iterator) object;
                    writeIterator(serializer, out, it);
                    return;
                }
                if (object instanceof Iterable) {
                    Iterator<?> it2 = ((Iterable) object).iterator();
                    writeIterator(serializer, out, it2);
                    return;
                }
                if (object instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry) object;
                    Object objKey = entry.getKey();
                    Object objVal = entry.getValue();
                    if (objKey instanceof String) {
                        String key = (String) objKey;
                        if (objVal instanceof String) {
                            String value = (String) objVal;
                            out.writeFieldValueStringWithDoubleQuoteCheck('{', key, value);
                        } else {
                            out.write(123);
                            out.writeFieldName(key);
                            serializer.write(objVal);
                        }
                    } else {
                        out.write(123);
                        serializer.write(objKey);
                        out.write(58);
                        serializer.write(objVal);
                    }
                    out.write(125);
                    return;
                }
                if (object.getClass().getName().equals("net.sf.json.JSONNull")) {
                    out.writeNull();
                    return;
                } else if (object instanceof Node) {
                    pattern = toString((Node) object);
                } else {
                    throw new JSONException("not support class : " + objClass);
                }
            }
        }
        out.writeString(pattern);
    }

    private static String toString(Node node) throws TransformerException, TransformerFactoryConfigurationError {
        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            DOMSource domSource = new DOMSource(node);
            StringWriter out = new StringWriter();
            transformer.transform(domSource, new StreamResult(out));
            return out.toString();
        } catch (TransformerException e) {
            throw new JSONException("xml node to string error", e);
        }
    }

    protected void writeIterator(JSONSerializer serializer, SerializeWriter out, Iterator<?> it) {
        int i = 0;
        out.write(91);
        while (it.hasNext()) {
            if (i != 0) {
                out.write(44);
            }
            Object item = it.next();
            serializer.write(item);
            i++;
        }
        out.write(93);
    }

    /* JADX WARN: Type inference failed for: r0v64, types: [T, java.text.SimpleDateFormat] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Object obj2;
        String str;
        Type rawType = type;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (rawType == InetSocketAddress.class) {
            if (jSONLexer.token() != 8) {
                defaultJSONParser.accept(12);
                InetAddress inetAddress = null;
                int iIntValue = 0;
                while (true) {
                    String strStringVal = jSONLexer.stringVal();
                    jSONLexer.nextToken(17);
                    if (strStringVal.equals("address")) {
                        defaultJSONParser.accept(17);
                        inetAddress = (InetAddress) defaultJSONParser.parseObject((Class) InetAddress.class);
                    } else if (strStringVal.equals("port")) {
                        defaultJSONParser.accept(17);
                        if (jSONLexer.token() != 2) {
                            throw new JSONException("port is not int");
                        }
                        iIntValue = jSONLexer.intValue();
                        jSONLexer.nextToken();
                    } else {
                        defaultJSONParser.accept(17);
                        defaultJSONParser.parse();
                    }
                    if (jSONLexer.token() == 16) {
                        jSONLexer.nextToken();
                    } else {
                        defaultJSONParser.accept(13);
                        return (T) new InetSocketAddress(inetAddress, iIntValue);
                    }
                }
            } else {
                jSONLexer.nextToken();
                return null;
            }
        } else {
            if (defaultJSONParser.resolveStatus == 2) {
                defaultJSONParser.resolveStatus = 0;
                defaultJSONParser.accept(16);
                if (jSONLexer.token() == 4) {
                    if (!"val".equals(jSONLexer.stringVal())) {
                        throw new JSONException("syntax error");
                    }
                    jSONLexer.nextToken();
                    defaultJSONParser.accept(17);
                    Object obj3 = defaultJSONParser.parse();
                    defaultJSONParser.accept(13);
                    obj2 = obj3;
                } else {
                    throw new JSONException("syntax error");
                }
            } else {
                obj2 = defaultJSONParser.parse();
            }
            if (obj2 == null) {
                str = null;
            } else if (obj2 instanceof String) {
                str = (String) obj2;
            } else {
                if (obj2 instanceof JSONObject) {
                    JSONObject jSONObject = (JSONObject) obj2;
                    if (rawType == Currency.class) {
                        String string = jSONObject.getString("currency");
                        if (string != null) {
                            return (T) Currency.getInstance(string);
                        }
                        String string2 = jSONObject.getString("currencyCode");
                        if (string2 != null) {
                            return (T) Currency.getInstance(string2);
                        }
                    }
                    if (rawType == Map.Entry.class) {
                        return (T) jSONObject.entrySet().iterator().next();
                    }
                    return (T) jSONObject.toJavaObject(rawType);
                }
                throw new JSONException("expect string");
            }
            if (str == null || str.length() == 0) {
                return null;
            }
            if (rawType == UUID.class) {
                return (T) UUID.fromString(str);
            }
            if (rawType == URI.class) {
                return (T) URI.create(str);
            }
            if (rawType == URL.class) {
                try {
                    return (T) new URL(str);
                } catch (MalformedURLException e) {
                    throw new JSONException("create url error", e);
                }
            }
            if (rawType == Pattern.class) {
                return (T) Pattern.compile(str);
            }
            if (rawType == Locale.class) {
                return (T) TypeUtils.toLocale(str);
            }
            if (rawType == SimpleDateFormat.class) {
                ?? r0 = (T) new SimpleDateFormat(str, jSONLexer.getLocale());
                r0.setTimeZone(jSONLexer.getTimeZone());
                return r0;
            }
            if (rawType == InetAddress.class || rawType == Inet4Address.class || rawType == Inet6Address.class) {
                try {
                    return (T) InetAddress.getByName(str);
                } catch (UnknownHostException e2) {
                    throw new JSONException("deserialize inet adress error", e2);
                }
            }
            if (rawType == File.class) {
                if (str.indexOf("..") >= 0 && !FILE_RELATIVE_PATH_SUPPORT) {
                    throw new JSONException("file relative path not support.");
                }
                return (T) new File(str);
            }
            if (rawType == TimeZone.class) {
                return (T) TimeZone.getTimeZone(str);
            }
            if (rawType instanceof ParameterizedType) {
                rawType = ((ParameterizedType) rawType).getRawType();
            }
            if (rawType == Class.class) {
                return (T) TypeUtils.loadClass(str, defaultJSONParser.getConfig().getDefaultClassLoader(), false);
            }
            if (rawType == Charset.class) {
                return (T) Charset.forName(str);
            }
            if (rawType == Currency.class) {
                return (T) Currency.getInstance(str);
            }
            if (rawType == JSONPath.class) {
                return (T) new JSONPath(str);
            }
            if (rawType instanceof Class) {
                String name = ((Class) rawType).getName();
                if (name.equals("java.nio.file.Path")) {
                    try {
                        if (method_paths_get == null && !method_paths_get_error) {
                            method_paths_get = TypeUtils.loadClass("java.nio.file.Paths").getMethod("get", String.class, String[].class);
                        }
                        if (method_paths_get != null) {
                            return (T) method_paths_get.invoke(null, str, new String[0]);
                        }
                        throw new JSONException("Path deserialize erorr");
                    } catch (IllegalAccessException e3) {
                        throw new JSONException("Path deserialize erorr", e3);
                    } catch (NoSuchMethodException e4) {
                        method_paths_get_error = true;
                    } catch (InvocationTargetException e5) {
                        throw new JSONException("Path deserialize erorr", e5);
                    }
                }
                throw new JSONException("MiscCodec not support " + name);
            }
            throw new JSONException("MiscCodec not support " + rawType.toString());
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }
}