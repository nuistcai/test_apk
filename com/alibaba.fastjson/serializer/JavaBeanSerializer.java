package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class JavaBeanSerializer extends SerializeFilterable implements ObjectSerializer {
    protected final SerializeBeanInfo beanInfo;
    protected final FieldSerializer[] getters;
    private volatile transient long[] hashArray;
    private volatile transient short[] hashArrayMapping;
    protected final FieldSerializer[] sortedGetters;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JavaBeanSerializer(Class<?> beanType) {
        this(beanType, (Map<String, String>) null);
    }

    public JavaBeanSerializer(Class<?> beanType, String... aliasList) {
        this(beanType, createAliasMap(aliasList));
    }

    static Map<String, String> createAliasMap(String... aliasList) {
        Map<String, String> aliasMap = new HashMap<>();
        for (String alias : aliasList) {
            aliasMap.put(alias, alias);
        }
        return aliasMap;
    }

    public JSONType getJSONType() {
        return this.beanInfo.jsonType;
    }

    public Class<?> getType() {
        return this.beanInfo.beanType;
    }

    public JavaBeanSerializer(Class<?> beanType, Map<String, String> aliasMap) {
        this(TypeUtils.buildBeanInfo(beanType, aliasMap, null));
    }

    public JavaBeanSerializer(SerializeBeanInfo beanInfo) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        this.beanInfo = beanInfo;
        this.sortedGetters = new FieldSerializer[beanInfo.sortedFields.length];
        for (int i = 0; i < this.sortedGetters.length; i++) {
            this.sortedGetters[i] = new FieldSerializer(beanInfo.beanType, beanInfo.sortedFields[i]);
        }
        if (beanInfo.fields == beanInfo.sortedFields) {
            this.getters = this.sortedGetters;
        } else {
            this.getters = new FieldSerializer[beanInfo.fields.length];
            boolean hashNotMatch = false;
            int i2 = 0;
            while (true) {
                if (i2 >= this.getters.length) {
                    break;
                }
                FieldSerializer fieldSerializer = getFieldSerializer(beanInfo.fields[i2].name);
                if (fieldSerializer == null) {
                    hashNotMatch = true;
                    break;
                } else {
                    this.getters[i2] = fieldSerializer;
                    i2++;
                }
            }
            if (hashNotMatch) {
                System.arraycopy(this.sortedGetters, 0, this.getters, 0, this.sortedGetters.length);
            }
        }
        if (beanInfo.jsonType != null) {
            for (Class<? extends SerializeFilter> filterClass : beanInfo.jsonType.serialzeFilters()) {
                try {
                    SerializeFilter filter = filterClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                    addFilter(filter);
                } catch (Exception e) {
                }
            }
        }
    }

    public void writeDirectNonContext(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        write(serializer, object, fieldName, fieldType, features);
    }

    public void writeAsArray(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        write(serializer, object, fieldName, fieldType, features);
    }

    public void writeAsArrayNonContext(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        write(serializer, object, fieldName, fieldType, features);
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        write(serializer, object, fieldName, fieldType, features, false);
    }

    public void writeNoneASM(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        write(serializer, object, fieldName, fieldType, features, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:398:0x0607, code lost:
    
        r36 = r5;
        r37 = r7;
        r26 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x0616, code lost:
    
        if (r23 == false) goto L401;
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x0618, code lost:
    
        r4 = ',';
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x061b, code lost:
    
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x061c, code lost:
    
        r1 = r40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x061e, code lost:
    
        writeAfter(r39, r1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:406:0x0624, code lost:
    
        if (r26.length <= 0) goto L414;
     */
    /* JADX WARN: Code restructure failed: missing block: B:408:0x062c, code lost:
    
        if (r14.isEnabled(com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat) == false) goto L414;
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x062e, code lost:
    
        r39.decrementIdent();
        r39.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x0635, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x0636, code lost:
    
        r4 = r0;
        r3 = r37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:412:0x063d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:413:0x063e, code lost:
    
        r4 = r0;
        r6 = r17;
        r3 = r37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:414:0x0645, code lost:
    
        if (r44 != false) goto L417;
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x0647, code lost:
    
        r14.append(r36);
     */
    /* JADX WARN: Code restructure failed: missing block: B:418:0x064f, code lost:
    
        r39.context = r37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x0654, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:420:0x0655, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0656, code lost:
    
        r3 = r37;
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x065d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:423:0x065e, code lost:
    
        r3 = r37;
        r4 = r0;
        r6 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:424:0x0664, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x0665, code lost:
    
        r3 = r37;
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:426:0x066e, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:427:0x066f, code lost:
    
        r3 = r37;
        r4 = r0;
        r6 = r17;
     */
    /* JADX WARN: Removed duplicated region for block: B:276:0x03f5  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x03f7 A[Catch: all -> 0x053b, Exception -> 0x0547, TryCatch #21 {Exception -> 0x0547, all -> 0x053b, blocks: (B:130:0x0216, B:133:0x021e, B:135:0x022a, B:137:0x0239, B:139:0x0243, B:142:0x024e, B:144:0x0259, B:146:0x025d, B:149:0x0265, B:151:0x0269, B:152:0x026f, B:154:0x0274, B:156:0x027b, B:160:0x0286, B:162:0x0291, B:164:0x0295, B:167:0x029d, B:170:0x02a3, B:172:0x02a8, B:176:0x02b2, B:178:0x02ba, B:180:0x02c5, B:182:0x02c9, B:185:0x02d1, B:187:0x02d5, B:188:0x02db, B:190:0x02e0, B:192:0x02e7, B:194:0x02ed, B:196:0x02f5, B:198:0x0300, B:200:0x0304, B:203:0x030c, B:205:0x0310, B:206:0x0316, B:208:0x031b, B:210:0x0322, B:213:0x032a, B:215:0x032e, B:217:0x0338, B:221:0x0344, B:223:0x0348, B:225:0x0351, B:227:0x035c, B:229:0x0362, B:231:0x0366, B:234:0x0372, B:236:0x0376, B:238:0x037a, B:241:0x0386, B:243:0x038a, B:245:0x038e, B:248:0x039a, B:250:0x039e, B:252:0x03a2, B:255:0x03b2, B:257:0x03b6, B:259:0x03ba, B:262:0x03c9, B:264:0x03cd, B:266:0x03d1, B:269:0x03e1, B:271:0x03e5, B:273:0x03e9, B:277:0x03f7, B:279:0x03fb, B:281:0x03ff, B:284:0x040b, B:286:0x0418, B:291:0x0424, B:293:0x042a, B:354:0x04fd, B:356:0x0502, B:358:0x0506, B:361:0x0511, B:363:0x0519, B:364:0x0522, B:366:0x0528, B:297:0x0435, B:298:0x0438, B:300:0x0440, B:302:0x044c, B:309:0x0461, B:320:0x0484, B:323:0x048e, B:326:0x0498, B:328:0x04a0, B:329:0x04ad, B:331:0x04b6, B:333:0x04bd, B:334:0x04c1, B:336:0x04c8, B:337:0x04cc, B:339:0x04d1, B:341:0x04d6, B:342:0x04db, B:344:0x04e0, B:346:0x04e4, B:348:0x04e8, B:351:0x04f6, B:353:0x04fa, B:316:0x046e, B:317:0x047a), top: B:490:0x0216 }] */
    /* JADX WARN: Removed duplicated region for block: B:287:0x041c  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x0422  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0430  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x050f  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x0511 A[Catch: all -> 0x053b, Exception -> 0x0547, TryCatch #21 {Exception -> 0x0547, all -> 0x053b, blocks: (B:130:0x0216, B:133:0x021e, B:135:0x022a, B:137:0x0239, B:139:0x0243, B:142:0x024e, B:144:0x0259, B:146:0x025d, B:149:0x0265, B:151:0x0269, B:152:0x026f, B:154:0x0274, B:156:0x027b, B:160:0x0286, B:162:0x0291, B:164:0x0295, B:167:0x029d, B:170:0x02a3, B:172:0x02a8, B:176:0x02b2, B:178:0x02ba, B:180:0x02c5, B:182:0x02c9, B:185:0x02d1, B:187:0x02d5, B:188:0x02db, B:190:0x02e0, B:192:0x02e7, B:194:0x02ed, B:196:0x02f5, B:198:0x0300, B:200:0x0304, B:203:0x030c, B:205:0x0310, B:206:0x0316, B:208:0x031b, B:210:0x0322, B:213:0x032a, B:215:0x032e, B:217:0x0338, B:221:0x0344, B:223:0x0348, B:225:0x0351, B:227:0x035c, B:229:0x0362, B:231:0x0366, B:234:0x0372, B:236:0x0376, B:238:0x037a, B:241:0x0386, B:243:0x038a, B:245:0x038e, B:248:0x039a, B:250:0x039e, B:252:0x03a2, B:255:0x03b2, B:257:0x03b6, B:259:0x03ba, B:262:0x03c9, B:264:0x03cd, B:266:0x03d1, B:269:0x03e1, B:271:0x03e5, B:273:0x03e9, B:277:0x03f7, B:279:0x03fb, B:281:0x03ff, B:284:0x040b, B:286:0x0418, B:291:0x0424, B:293:0x042a, B:354:0x04fd, B:356:0x0502, B:358:0x0506, B:361:0x0511, B:363:0x0519, B:364:0x0522, B:366:0x0528, B:297:0x0435, B:298:0x0438, B:300:0x0440, B:302:0x044c, B:309:0x0461, B:320:0x0484, B:323:0x048e, B:326:0x0498, B:328:0x04a0, B:329:0x04ad, B:331:0x04b6, B:333:0x04bd, B:334:0x04c1, B:336:0x04c8, B:337:0x04cc, B:339:0x04d1, B:341:0x04d6, B:342:0x04db, B:344:0x04e0, B:346:0x04e4, B:348:0x04e8, B:351:0x04f6, B:353:0x04fa, B:316:0x046e, B:317:0x047a), top: B:490:0x0216 }] */
    /* JADX WARN: Removed duplicated region for block: B:373:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:440:0x06c0 A[PHI: r5
  0x06c0: PHI (r5v66 'errorMessage' java.lang.String) = (r5v64 'errorMessage' java.lang.String), (r5v65 'errorMessage' java.lang.String) binds: [B:437:0x069e, B:439:0x06bf] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:442:0x06c6 A[Catch: all -> 0x06dd, TRY_ENTER, TryCatch #27 {all -> 0x06dd, blocks: (B:442:0x06c6, B:452:0x0725, B:454:0x072b, B:455:0x0747, B:457:0x074c, B:460:0x0754, B:461:0x0759, B:446:0x06e2, B:448:0x06e6, B:450:0x06ec, B:451:0x070b), top: B:472:0x06c4 }] */
    /* JADX WARN: Removed duplicated region for block: B:445:0x06e0  */
    /* JADX WARN: Removed duplicated region for block: B:454:0x072b A[Catch: all -> 0x06dd, TryCatch #27 {all -> 0x06dd, blocks: (B:442:0x06c6, B:452:0x0725, B:454:0x072b, B:455:0x0747, B:457:0x074c, B:460:0x0754, B:461:0x0759, B:446:0x06e2, B:448:0x06e6, B:450:0x06ec, B:451:0x070b), top: B:472:0x06c4 }] */
    /* JADX WARN: Removed duplicated region for block: B:457:0x074c A[Catch: all -> 0x06dd, TryCatch #27 {all -> 0x06dd, blocks: (B:442:0x06c6, B:452:0x0725, B:454:0x072b, B:455:0x0747, B:457:0x074c, B:460:0x0754, B:461:0x0759, B:446:0x06e2, B:448:0x06e6, B:450:0x06ec, B:451:0x070b), top: B:472:0x06c4 }] */
    /* JADX WARN: Removed duplicated region for block: B:459:0x0753  */
    /* JADX WARN: Removed duplicated region for block: B:470:0x06a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:521:0x05b1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0158  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features, boolean unwrapped) throws Throwable {
        FieldSerializer[] getters;
        SerialContext parent;
        Throwable th;
        Object obj;
        Exception e;
        FieldSerializer errorFieldSerializer;
        FieldSerializer errorFieldSerializer2;
        char seperator;
        boolean writeClassName;
        char newSeperator;
        boolean skipTransient;
        boolean ignoreNonFieldGetter;
        int i;
        boolean commaFlag;
        char newSeperator2;
        int i2;
        char endSeperator;
        char endSeperator2;
        SerialContext parent2;
        FieldSerializer[] getters2;
        boolean notApply;
        Object propertyValue;
        boolean fieldUnwrappedNull;
        Map map;
        Class<?> fieldCLass;
        Throwable cause;
        Object obj2 = object;
        Object obj3 = fieldName;
        Type type = fieldType;
        SerializeWriter out = serializer.out;
        if (obj2 == null) {
            out.writeNull();
            return;
        }
        if (writeReference(serializer, obj2, features)) {
            return;
        }
        if (out.sortField) {
            getters = this.sortedGetters;
        } else {
            FieldSerializer[] getters3 = this.getters;
            getters = getters3;
        }
        SerialContext parent3 = serializer.context;
        if (!this.beanInfo.beanType.isEnum()) {
            serializer.setContext(parent3, object, fieldName, this.beanInfo.features, features);
        }
        boolean writeAsArray = isWriteAsArray(serializer, features);
        char startSeperator = writeAsArray ? '[' : '{';
        char endSeperator3 = writeAsArray ? ']' : '}';
        if (!unwrapped) {
            try {
                out.append(startSeperator);
            } catch (Exception e2) {
                e = e2;
                errorFieldSerializer = null;
                parent = parent3;
                obj = obj2;
                String errorMessage = "write javaBean error, fastjson version 1.2.83";
                if (obj != null) {
                }
                serializer.context = parent;
                throw th;
            } catch (Throwable th2) {
                th = th2;
                parent = parent3;
            }
        }
        try {
            if (getters.length > 0 && out.isEnabled(SerializerFeature.PrettyFormat)) {
                serializer.incrementIndent();
                serializer.println();
            }
            boolean commaFlag2 = false;
            if ((this.beanInfo.features & SerializerFeature.WriteClassName.mask) == 0 && (SerializerFeature.WriteClassName.mask & features) == 0 && !serializer.isWriteClassName(type, obj2)) {
                errorFieldSerializer2 = null;
            } else {
                Class<?> objClass = object.getClass();
                Type type2 = (objClass == type || !(type instanceof WildcardType)) ? fieldType : TypeUtils.getClass(fieldType);
                if (objClass != type2) {
                    errorFieldSerializer2 = null;
                    try {
                        writeClassName(serializer, this.beanInfo.typeKey, obj2);
                        commaFlag2 = true;
                    } catch (Exception e3) {
                        e = e3;
                        parent = parent3;
                        obj = obj2;
                        errorFieldSerializer = null;
                        String errorMessage2 = "write javaBean error, fastjson version 1.2.83";
                        if (obj != null) {
                        }
                        serializer.context = parent;
                        throw th;
                    } catch (Throwable th3) {
                        th = th3;
                        parent = parent3;
                    }
                } else {
                    errorFieldSerializer2 = null;
                }
            }
            seperator = commaFlag2 ? ',' : (char) 0;
            try {
                writeClassName = out.isEnabled(SerializerFeature.WriteClassName);
                newSeperator = writeBefore(serializer, obj2, seperator);
                boolean commaFlag3 = newSeperator == ',';
                skipTransient = out.isEnabled(SerializerFeature.SkipTransientField);
                ignoreNonFieldGetter = out.isEnabled(SerializerFeature.IgnoreNonFieldGetter);
                i = 0;
                commaFlag = commaFlag3;
            } catch (Exception e4) {
                parent = parent3;
                obj = obj2;
                e = e4;
                errorFieldSerializer = errorFieldSerializer2;
            } catch (Throwable th4) {
                parent = parent3;
                th = th4;
            }
        } catch (Exception e5) {
            parent = parent3;
            obj = obj2;
            e = e5;
            errorFieldSerializer = null;
        } catch (Throwable th5) {
            parent = parent3;
            th = th5;
        }
        while (true) {
            if (i >= getters.length) {
                break;
            }
            try {
                FieldSerializer fieldSerializer = getters[i];
                Field field = fieldSerializer.fieldInfo.field;
                char seperator2 = seperator;
                FieldInfo fieldInfo = fieldSerializer.fieldInfo;
                char newSeperator3 = newSeperator;
                String fieldInfoName = fieldInfo.name;
                int i3 = i;
                Class<?> fieldClass = fieldInfo.fieldClass;
                char endSeperator4 = endSeperator3;
                char startSeperator2 = startSeperator;
                SerialContext parent4 = parent3;
                try {
                    boolean fieldUseSingleQuotes = SerializerFeature.isEnabled(out.features, fieldInfo.serialzeFeatures, SerializerFeature.UseSingleQuotes);
                    boolean directWritePrefix = out.quoteFieldNames && !fieldUseSingleQuotes;
                    if (skipTransient) {
                        try {
                            if (fieldInfo.fieldTransient) {
                                newSeperator2 = newSeperator3;
                                i2 = i3;
                                endSeperator = endSeperator4;
                                endSeperator2 = startSeperator2;
                                parent2 = parent4;
                                getters2 = getters;
                            } else if (ignoreNonFieldGetter && field == null) {
                                newSeperator2 = newSeperator3;
                                i2 = i3;
                                endSeperator = endSeperator4;
                                endSeperator2 = startSeperator2;
                                parent2 = parent4;
                                getters2 = getters;
                            } else {
                                if (applyName(serializer, obj2, fieldInfoName) && applyLabel(serializer, fieldInfo.label)) {
                                    notApply = false;
                                } else if (writeAsArray) {
                                    notApply = true;
                                } else {
                                    newSeperator2 = newSeperator3;
                                    i2 = i3;
                                    endSeperator = endSeperator4;
                                    endSeperator2 = startSeperator2;
                                    parent2 = parent4;
                                    getters2 = getters;
                                }
                                if (fieldInfoName.equals(this.beanInfo.typeKey) && serializer.isWriteClassName(type, obj2)) {
                                    newSeperator2 = newSeperator3;
                                    i2 = i3;
                                    endSeperator = endSeperator4;
                                    endSeperator2 = startSeperator2;
                                    parent2 = parent4;
                                    getters2 = getters;
                                } else {
                                    if (notApply) {
                                        propertyValue = null;
                                    } else {
                                        try {
                                            propertyValue = fieldSerializer.getPropertyValueDirect(obj2);
                                        } catch (InvocationTargetException ex) {
                                            errorFieldSerializer = fieldSerializer;
                                            try {
                                                if (out.isEnabled(SerializerFeature.IgnoreErrorGetter)) {
                                                    errorFieldSerializer2 = errorFieldSerializer;
                                                    propertyValue = null;
                                                } else {
                                                    try {
                                                        throw ex;
                                                    } catch (Exception e6) {
                                                        obj = object;
                                                        e = e6;
                                                        parent = parent4;
                                                        String errorMessage22 = "write javaBean error, fastjson version 1.2.83";
                                                        if (obj != null) {
                                                            if (fieldName == null) {
                                                            }
                                                            if (e.getMessage() != null) {
                                                            }
                                                            if (e instanceof InvocationTargetException) {
                                                            }
                                                            if (cause == null) {
                                                            }
                                                            throw new JSONException(errorMessage22, cause);
                                                        }
                                                        try {
                                                            errorMessage22 = "write javaBean error, fastjson version 1.2.83, class " + object.getClass().getName();
                                                            try {
                                                                if (fieldName == null) {
                                                                    errorMessage22 = errorMessage22 + ", fieldName : " + fieldName;
                                                                } else if (errorFieldSerializer != null && errorFieldSerializer.fieldInfo != null) {
                                                                    FieldInfo fieldInfo2 = errorFieldSerializer.fieldInfo;
                                                                    errorMessage22 = fieldInfo2.method != null ? errorMessage22 + ", method : " + fieldInfo2.method.getName() : errorMessage22 + ", fieldName : " + errorFieldSerializer.fieldInfo.name;
                                                                }
                                                                if (e.getMessage() != null) {
                                                                    errorMessage22 = errorMessage22 + ", " + e.getMessage();
                                                                }
                                                                cause = e instanceof InvocationTargetException ? e.getCause() : null;
                                                                if (cause == null) {
                                                                    cause = e;
                                                                }
                                                                throw new JSONException(errorMessage22, cause);
                                                            } catch (Throwable th6) {
                                                                th = th6;
                                                                th = th;
                                                                serializer.context = parent;
                                                                throw th;
                                                            }
                                                        } catch (Throwable th7) {
                                                            th = th7;
                                                            th = th;
                                                            serializer.context = parent;
                                                            throw th;
                                                        }
                                                        serializer.context = parent;
                                                        throw th;
                                                    } catch (Throwable th8) {
                                                        th = th8;
                                                        parent = parent4;
                                                    }
                                                }
                                            } catch (Exception e7) {
                                                obj = object;
                                                e = e7;
                                                parent = parent4;
                                            } catch (Throwable th9) {
                                                th = th9;
                                                parent = parent4;
                                            }
                                        }
                                    }
                                    if (apply(serializer, obj2, fieldInfoName, propertyValue)) {
                                        Object propertyValue2 = (fieldClass == String.class && "trim".equals(fieldInfo.format) && propertyValue != null) ? ((String) propertyValue).trim() : propertyValue;
                                        String key = processKey(serializer, obj2, fieldInfoName, propertyValue2);
                                        Object originalValue = propertyValue2;
                                        newSeperator2 = newSeperator3;
                                        i2 = i3;
                                        getters2 = getters;
                                        endSeperator = endSeperator4;
                                        endSeperator2 = startSeperator2;
                                        parent2 = parent4;
                                        try {
                                            Object propertyValue3 = processValue(serializer, fieldSerializer.fieldContext, object, fieldInfoName, propertyValue2, features);
                                            if (propertyValue3 == null) {
                                                int serialzeFeatures = fieldInfo.serialzeFeatures;
                                                JSONField jsonField = fieldInfo.getAnnotation();
                                                if (this.beanInfo.jsonType != null) {
                                                    serialzeFeatures |= SerializerFeature.of(this.beanInfo.jsonType.serialzeFeatures());
                                                }
                                                if (jsonField != null && !"".equals(jsonField.defaultValue())) {
                                                    propertyValue3 = jsonField.defaultValue();
                                                } else if (fieldClass == Boolean.class) {
                                                    int defaultMask = SerializerFeature.WriteNullBooleanAsFalse.mask;
                                                    int mask = SerializerFeature.WriteMapNullValue.mask | defaultMask;
                                                    if (writeAsArray || (serialzeFeatures & mask) != 0 || (out.features & mask) != 0) {
                                                        if ((serialzeFeatures & defaultMask) != 0) {
                                                            propertyValue3 = false;
                                                        } else if ((out.features & defaultMask) != 0 && (SerializerFeature.WriteMapNullValue.mask & serialzeFeatures) == 0) {
                                                            propertyValue3 = false;
                                                        }
                                                    }
                                                } else if (fieldClass == String.class) {
                                                    int defaultMask2 = SerializerFeature.WriteNullStringAsEmpty.mask;
                                                    int mask2 = SerializerFeature.WriteMapNullValue.mask | defaultMask2;
                                                    if (writeAsArray || (serialzeFeatures & mask2) != 0 || (out.features & mask2) != 0) {
                                                        if ((serialzeFeatures & defaultMask2) != 0) {
                                                            propertyValue3 = "";
                                                        } else if ((out.features & defaultMask2) != 0 && (SerializerFeature.WriteMapNullValue.mask & serialzeFeatures) == 0) {
                                                            propertyValue3 = "";
                                                        }
                                                    }
                                                } else if (Number.class.isAssignableFrom(fieldClass)) {
                                                    int defaultMask3 = SerializerFeature.WriteNullNumberAsZero.mask;
                                                    int mask3 = SerializerFeature.WriteMapNullValue.mask | defaultMask3;
                                                    if (writeAsArray || (serialzeFeatures & mask3) != 0 || (out.features & mask3) != 0) {
                                                        if ((serialzeFeatures & defaultMask3) != 0) {
                                                            propertyValue3 = 0;
                                                        } else if ((out.features & defaultMask3) != 0 && (SerializerFeature.WriteMapNullValue.mask & serialzeFeatures) == 0) {
                                                            propertyValue3 = 0;
                                                        }
                                                    }
                                                } else if (Collection.class.isAssignableFrom(fieldClass)) {
                                                    int defaultMask4 = SerializerFeature.WriteNullListAsEmpty.mask;
                                                    int mask4 = SerializerFeature.WriteMapNullValue.mask | defaultMask4;
                                                    if (writeAsArray || (serialzeFeatures & mask4) != 0 || (out.features & mask4) != 0) {
                                                        if ((serialzeFeatures & defaultMask4) != 0) {
                                                            propertyValue3 = Collections.emptyList();
                                                        } else if ((out.features & defaultMask4) != 0 && (SerializerFeature.WriteMapNullValue.mask & serialzeFeatures) == 0) {
                                                            propertyValue3 = Collections.emptyList();
                                                        }
                                                    }
                                                } else if (!writeAsArray && !fieldSerializer.writeNull) {
                                                    if (out.isEnabled(SerializerFeature.WriteMapNullValue.mask) || (SerializerFeature.WriteMapNullValue.mask & serialzeFeatures) != 0) {
                                                    }
                                                }
                                                if (propertyValue3 != null || ((!out.notWriteDefaultValue && (fieldInfo.serialzeFeatures & SerializerFeature.NotWriteDefaultValue.mask) == 0 && (this.beanInfo.features & SerializerFeature.NotWriteDefaultValue.mask) == 0) || (((fieldCLass = fieldInfo.fieldClass) != Byte.TYPE || !(propertyValue3 instanceof Byte) || ((Byte) propertyValue3).byteValue() != 0) && ((fieldCLass != Short.TYPE || !(propertyValue3 instanceof Short) || ((Short) propertyValue3).shortValue() != 0) && ((fieldCLass != Integer.TYPE || !(propertyValue3 instanceof Integer) || ((Integer) propertyValue3).intValue() != 0) && ((fieldCLass != Long.TYPE || !(propertyValue3 instanceof Long) || ((Long) propertyValue3).longValue() != 0) && ((fieldCLass != Float.TYPE || !(propertyValue3 instanceof Float) || ((Float) propertyValue3).floatValue() != 0.0f) && ((fieldCLass != Double.TYPE || !(propertyValue3 instanceof Double) || ((Double) propertyValue3).doubleValue() != 0.0d) && (fieldCLass != Boolean.TYPE || !(propertyValue3 instanceof Boolean) || ((Boolean) propertyValue3).booleanValue()))))))))) {
                                                    if (commaFlag) {
                                                        if (!fieldInfo.unwrapped || !(propertyValue3 instanceof Map) || ((Map) propertyValue3).size() != 0) {
                                                            out.write(44);
                                                            if (out.isEnabled(SerializerFeature.PrettyFormat)) {
                                                                serializer.println();
                                                            }
                                                        }
                                                    }
                                                    if (key == fieldInfoName) {
                                                        if (!writeAsArray) {
                                                            out.writeFieldName(key, true);
                                                        }
                                                        serializer.write(propertyValue3);
                                                    } else if (originalValue != propertyValue3) {
                                                        if (!writeAsArray) {
                                                            fieldSerializer.writePrefix(serializer);
                                                        }
                                                        serializer.write(propertyValue3);
                                                    } else {
                                                        if (!writeAsArray) {
                                                            boolean isMap = Map.class.isAssignableFrom(fieldClass);
                                                            boolean isJavaBean = !(fieldClass.isPrimitive() || fieldClass.getName().startsWith("java.")) || fieldClass == Object.class;
                                                            if (writeClassName || !fieldInfo.unwrapped || (!isMap && !isJavaBean)) {
                                                                if (directWritePrefix) {
                                                                    out.write(fieldInfo.name_chars, 0, fieldInfo.name_chars.length);
                                                                } else {
                                                                    fieldSerializer.writePrefix(serializer);
                                                                }
                                                            }
                                                        }
                                                        if (writeAsArray) {
                                                            fieldSerializer.writeValue(serializer, propertyValue3);
                                                        } else {
                                                            JSONField fieldAnnotation = fieldInfo.getAnnotation();
                                                            if (fieldClass == String.class && (fieldAnnotation == null || fieldAnnotation.serializeUsing() == Void.class)) {
                                                                if (propertyValue3 == null) {
                                                                    int serialzeFeatures2 = fieldSerializer.features;
                                                                    if (this.beanInfo.jsonType != null) {
                                                                        serialzeFeatures2 |= SerializerFeature.of(this.beanInfo.jsonType.serialzeFeatures());
                                                                    }
                                                                    if (((SerializerFeature.WriteNullStringAsEmpty.mask & out.features) == 0 || (SerializerFeature.WriteMapNullValue.mask & serialzeFeatures2) != 0) && (SerializerFeature.WriteNullStringAsEmpty.mask & serialzeFeatures2) == 0) {
                                                                        out.writeNull();
                                                                    } else {
                                                                        out.writeString("");
                                                                    }
                                                                } else {
                                                                    String propertyValueString = (String) propertyValue3;
                                                                    if (fieldUseSingleQuotes) {
                                                                        out.writeStringWithSingleQuote(propertyValueString);
                                                                    } else {
                                                                        out.writeStringWithDoubleQuote(propertyValueString, (char) 0);
                                                                    }
                                                                }
                                                            } else if (fieldInfo.unwrapped && (propertyValue3 instanceof Map) && ((Map) propertyValue3).size() == 0) {
                                                                commaFlag = false;
                                                            } else {
                                                                fieldSerializer.writeValue(serializer, propertyValue3);
                                                            }
                                                        }
                                                    }
                                                    fieldUnwrappedNull = false;
                                                    if (fieldInfo.unwrapped && (propertyValue3 instanceof Map)) {
                                                        map = (Map) propertyValue3;
                                                        if (map.size() != 0) {
                                                            fieldUnwrappedNull = true;
                                                        } else if (!serializer.isEnabled(SerializerFeature.WriteMapNullValue)) {
                                                            boolean hasNotNull = false;
                                                            Iterator it = map.values().iterator();
                                                            while (true) {
                                                                if (!it.hasNext()) {
                                                                    break;
                                                                }
                                                                Object value = it.next();
                                                                if (value != null) {
                                                                    hasNotNull = true;
                                                                    break;
                                                                }
                                                            }
                                                            if (!hasNotNull) {
                                                                fieldUnwrappedNull = true;
                                                            }
                                                        }
                                                    }
                                                    if (fieldUnwrappedNull) {
                                                        commaFlag = true;
                                                    }
                                                }
                                            } else if (propertyValue3 != null) {
                                                if (commaFlag) {
                                                }
                                                if (key == fieldInfoName) {
                                                }
                                                fieldUnwrappedNull = false;
                                                if (fieldInfo.unwrapped) {
                                                    map = (Map) propertyValue3;
                                                    if (map.size() != 0) {
                                                    }
                                                }
                                                if (fieldUnwrappedNull) {
                                                }
                                            }
                                        } catch (Exception e8) {
                                            obj = object;
                                            e = e8;
                                            errorFieldSerializer = errorFieldSerializer2;
                                            parent = parent2;
                                            String errorMessage222 = "write javaBean error, fastjson version 1.2.83";
                                            if (obj != null) {
                                            }
                                            serializer.context = parent;
                                            throw th;
                                        } catch (Throwable th10) {
                                            th = th10;
                                            parent = parent2;
                                        }
                                    } else {
                                        newSeperator2 = newSeperator3;
                                        i2 = i3;
                                        endSeperator = endSeperator4;
                                        endSeperator2 = startSeperator2;
                                        parent2 = parent4;
                                        getters2 = getters;
                                    }
                                }
                            }
                            i = i2 + 1;
                            obj2 = object;
                            obj3 = fieldName;
                            type = fieldType;
                            newSeperator = newSeperator2;
                            seperator = seperator2;
                            getters = getters2;
                            startSeperator = endSeperator2;
                            endSeperator3 = endSeperator;
                            parent3 = parent2;
                        } catch (Exception e9) {
                            e = e9;
                            obj = obj2;
                            errorFieldSerializer = errorFieldSerializer2;
                            parent = parent4;
                            String errorMessage2222 = "write javaBean error, fastjson version 1.2.83";
                            if (obj != null) {
                            }
                            serializer.context = parent;
                            throw th;
                        } catch (Throwable th11) {
                            th = th11;
                            parent = parent4;
                        }
                    }
                } catch (Exception e10) {
                    obj = object;
                    e = e10;
                    errorFieldSerializer = errorFieldSerializer2;
                    parent = parent4;
                } catch (Throwable th12) {
                    th = th12;
                    parent = parent4;
                }
            } catch (Exception e11) {
                obj = object;
                e = e11;
                errorFieldSerializer = errorFieldSerializer2;
                parent = parent3;
            } catch (Throwable th13) {
                th = th13;
                parent = parent3;
            }
            serializer.context = parent;
            throw th;
        }
    }

    protected void writeClassName(JSONSerializer serializer, String typeKey, Object object) {
        if (typeKey == null) {
            typeKey = serializer.config.typeKey;
        }
        serializer.out.writeFieldName(typeKey, false);
        String typeName = this.beanInfo.typeName;
        if (typeName == null) {
            Class<?> clazz = object.getClass();
            if (TypeUtils.isProxy(clazz)) {
                clazz = clazz.getSuperclass();
            }
            typeName = clazz.getName();
        }
        serializer.write(typeName);
    }

    public boolean writeReference(JSONSerializer serializer, Object object, int fieldFeatures) {
        SerialContext context = serializer.context;
        int mask = SerializerFeature.DisableCircularReferenceDetect.mask;
        if (context == null || (context.features & mask) != 0 || (fieldFeatures & mask) != 0 || serializer.references == null || !serializer.references.containsKey(object)) {
            return false;
        }
        serializer.writeReference(object);
        return true;
    }

    protected boolean isWriteAsArray(JSONSerializer serializer) {
        return isWriteAsArray(serializer, 0);
    }

    protected boolean isWriteAsArray(JSONSerializer serializer, int fieldFeatrues) {
        int mask = SerializerFeature.BeanToArray.mask;
        return ((this.beanInfo.features & mask) == 0 && !serializer.out.beanToArray && (fieldFeatrues & mask) == 0) ? false : true;
    }

    public Object getFieldValue(Object object, String key) {
        FieldSerializer fieldDeser = getFieldSerializer(key);
        if (fieldDeser == null) {
            throw new JSONException("field not found. " + key);
        }
        try {
            return fieldDeser.getPropertyValue(object);
        } catch (IllegalAccessException ex) {
            throw new JSONException("getFieldValue error." + key, ex);
        } catch (InvocationTargetException ex2) {
            throw new JSONException("getFieldValue error." + key, ex2);
        }
    }

    public Object getFieldValue(Object object, String key, long keyHash, boolean throwFieldNotFoundException) {
        FieldSerializer fieldDeser = getFieldSerializer(keyHash);
        if (fieldDeser == null) {
            if (throwFieldNotFoundException) {
                throw new JSONException("field not found. " + key);
            }
            return null;
        }
        try {
            return fieldDeser.getPropertyValue(object);
        } catch (IllegalAccessException ex) {
            throw new JSONException("getFieldValue error." + key, ex);
        } catch (InvocationTargetException ex2) {
            throw new JSONException("getFieldValue error." + key, ex2);
        }
    }

    public FieldSerializer getFieldSerializer(String key) {
        if (key == null) {
            return null;
        }
        int low = 0;
        int high = this.sortedGetters.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            String fieldName = this.sortedGetters[mid].fieldInfo.name;
            int cmp = fieldName.compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return this.sortedGetters[mid];
            }
        }
        return null;
    }

    public FieldSerializer getFieldSerializer(long hash) {
        int p_t;
        PropertyNamingStrategy[] namingStrategies = null;
        if (this.hashArray == null) {
            namingStrategies = PropertyNamingStrategy.values();
            long[] hashArray = new long[this.sortedGetters.length * namingStrategies.length];
            int index = 0;
            int i = 0;
            while (i < this.sortedGetters.length) {
                String name = this.sortedGetters[i].fieldInfo.name;
                int index2 = index + 1;
                hashArray[index] = TypeUtils.fnv1a_64(name);
                for (PropertyNamingStrategy propertyNamingStrategy : namingStrategies) {
                    String name_t = propertyNamingStrategy.translate(name);
                    if (!name.equals(name_t)) {
                        hashArray[index2] = TypeUtils.fnv1a_64(name_t);
                        index2++;
                    }
                }
                i++;
                index = index2;
            }
            Arrays.sort(hashArray, 0, index);
            this.hashArray = new long[index];
            System.arraycopy(hashArray, 0, this.hashArray, 0, index);
        }
        int pos = Arrays.binarySearch(this.hashArray, hash);
        if (pos < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            if (namingStrategies == null) {
                namingStrategies = PropertyNamingStrategy.values();
            }
            short[] mapping = new short[this.hashArray.length];
            Arrays.fill(mapping, (short) -1);
            for (int i2 = 0; i2 < this.sortedGetters.length; i2++) {
                String name2 = this.sortedGetters[i2].fieldInfo.name;
                int p = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(name2));
                if (p >= 0) {
                    mapping[p] = (short) i2;
                }
                for (PropertyNamingStrategy propertyNamingStrategy2 : namingStrategies) {
                    String name_t2 = propertyNamingStrategy2.translate(name2);
                    if (!name2.equals(name_t2) && (p_t = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(name_t2))) >= 0) {
                        mapping[p_t] = (short) i2;
                    }
                }
            }
            this.hashArrayMapping = mapping;
        }
        short s = this.hashArrayMapping[pos];
        if (s == -1) {
            return null;
        }
        return this.sortedGetters[s];
    }

    public List<Object> getFieldValues(Object object) throws Exception {
        List<Object> fieldValues = new ArrayList<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            fieldValues.add(getter.getPropertyValue(object));
        }
        return fieldValues;
    }

    public List<Object> getObjectFieldValues(Object object) throws Exception {
        List<Object> fieldValues = new ArrayList<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            Class fieldClass = getter.fieldInfo.fieldClass;
            if (!fieldClass.isPrimitive() && !fieldClass.getName().startsWith("java.lang.")) {
                fieldValues.add(getter.getPropertyValue(object));
            }
        }
        return fieldValues;
    }

    public int getSize(Object object) throws Exception {
        int size = 0;
        for (FieldSerializer getter : this.sortedGetters) {
            Object value = getter.getPropertyValueDirect(object);
            if (value != null) {
                size++;
            }
        }
        return size;
    }

    public Set<String> getFieldNames(Object object) throws Exception {
        Set<String> fieldNames = new HashSet<>();
        for (FieldSerializer getter : this.sortedGetters) {
            Object value = getter.getPropertyValueDirect(object);
            if (value != null) {
                fieldNames.add(getter.fieldInfo.name);
            }
        }
        return fieldNames;
    }

    public Map<String, Object> getFieldValuesMap(Object object) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            boolean skipTransient = SerializerFeature.isEnabled(getter.features, SerializerFeature.SkipTransientField);
            FieldInfo fieldInfo = getter.fieldInfo;
            if (!skipTransient || fieldInfo == null || !fieldInfo.fieldTransient) {
                if (getter.fieldInfo.unwrapped) {
                    Object unwrappedValue = getter.getPropertyValue(object);
                    Object map1 = JSON.toJSON(unwrappedValue);
                    if (map1 instanceof Map) {
                        map.putAll((Map) map1);
                    } else {
                        map.put(getter.fieldInfo.name, getter.getPropertyValue(object));
                    }
                } else {
                    map.put(getter.fieldInfo.name, getter.getPropertyValue(object));
                }
            }
        }
        return map;
    }

    protected BeanContext getBeanContext(int orinal) {
        return this.sortedGetters[orinal].fieldContext;
    }

    protected Type getFieldType(int ordinal) {
        return this.sortedGetters[ordinal].fieldInfo.fieldType;
    }

    protected char writeBefore(JSONSerializer jsonBeanDeser, Object object, char seperator) {
        if (jsonBeanDeser.beforeFilters != null) {
            for (BeforeFilter beforeFilter : jsonBeanDeser.beforeFilters) {
                seperator = beforeFilter.writeBefore(jsonBeanDeser, object, seperator);
            }
        }
        if (this.beforeFilters != null) {
            for (BeforeFilter beforeFilter2 : this.beforeFilters) {
                seperator = beforeFilter2.writeBefore(jsonBeanDeser, object, seperator);
            }
        }
        return seperator;
    }

    protected char writeAfter(JSONSerializer jsonBeanDeser, Object object, char seperator) {
        if (jsonBeanDeser.afterFilters != null) {
            for (AfterFilter afterFilter : jsonBeanDeser.afterFilters) {
                seperator = afterFilter.writeAfter(jsonBeanDeser, object, seperator);
            }
        }
        if (this.afterFilters != null) {
            for (AfterFilter afterFilter2 : this.afterFilters) {
                seperator = afterFilter2.writeAfter(jsonBeanDeser, object, seperator);
            }
        }
        return seperator;
    }

    protected boolean applyLabel(JSONSerializer jsonBeanDeser, String label) {
        if (jsonBeanDeser.labelFilters != null) {
            for (LabelFilter propertyFilter : jsonBeanDeser.labelFilters) {
                if (!propertyFilter.apply(label)) {
                    return false;
                }
            }
        }
        if (this.labelFilters != null) {
            for (LabelFilter propertyFilter2 : this.labelFilters) {
                if (!propertyFilter2.apply(label)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}