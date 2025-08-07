package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;

/* loaded from: classes.dex */
public class JSONArray extends JSON implements List<Object>, Cloneable, RandomAccess, Serializable {
    private static final long serialVersionUID = 1;
    protected transient Type componentType;
    private final List<Object> list;
    protected transient Object relatedArray;

    public JSONArray() {
        this.list = new ArrayList();
    }

    public JSONArray(List<Object> list) {
        if (list == null) {
            throw new IllegalArgumentException("list is null.");
        }
        this.list = list;
    }

    public JSONArray(int initialCapacity) {
        this.list = new ArrayList(initialCapacity);
    }

    public Object getRelatedArray() {
        return this.relatedArray;
    }

    public void setRelatedArray(Object relatedArray) {
        this.relatedArray = relatedArray;
    }

    public Type getComponentType() {
        return this.componentType;
    }

    public void setComponentType(Type componentType) {
        this.componentType = componentType;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.list.size();
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object o) {
        return this.list.contains(o);
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<Object> iterator() {
        return this.list.iterator();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.list.toArray(tArr);
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(Object e) {
        return this.list.add(e);
    }

    public JSONArray fluentAdd(Object e) {
        this.list.add(e);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object o) {
        return this.list.remove(o);
    }

    public JSONArray fluentRemove(Object o) {
        this.list.remove(o);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> c) {
        return this.list.containsAll(c);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<?> c) {
        return this.list.addAll(c);
    }

    public JSONArray fluentAddAll(Collection<?> c) {
        this.list.addAll(c);
        return this;
    }

    @Override // java.util.List
    public boolean addAll(int index, Collection<? extends Object> collection) {
        return this.list.addAll(index, collection);
    }

    public JSONArray fluentAddAll(int index, Collection<?> c) {
        this.list.addAll(index, c);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> c) {
        return this.list.removeAll(c);
    }

    public JSONArray fluentRemoveAll(Collection<?> c) {
        this.list.removeAll(c);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> c) {
        return this.list.retainAll(c);
    }

    public JSONArray fluentRetainAll(Collection<?> c) {
        this.list.retainAll(c);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.list.clear();
    }

    public JSONArray fluentClear() {
        this.list.clear();
        return this;
    }

    @Override // java.util.List
    public Object set(int index, Object element) {
        if (index == -1) {
            this.list.add(element);
            return null;
        }
        if (this.list.size() <= index) {
            for (int i = this.list.size(); i < index; i++) {
                this.list.add(null);
            }
            this.list.add(element);
            return null;
        }
        return this.list.set(index, element);
    }

    public JSONArray fluentSet(int index, Object element) {
        set(index, element);
        return this;
    }

    @Override // java.util.List
    public void add(int index, Object element) {
        this.list.add(index, element);
    }

    public JSONArray fluentAdd(int index, Object element) {
        this.list.add(index, element);
        return this;
    }

    @Override // java.util.List
    public Object remove(int index) {
        return this.list.remove(index);
    }

    public JSONArray fluentRemove(int index) {
        this.list.remove(index);
        return this;
    }

    @Override // java.util.List
    public int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    @Override // java.util.List
    public int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    @Override // java.util.List
    public ListIterator<Object> listIterator() {
        return this.list.listIterator();
    }

    @Override // java.util.List
    public ListIterator<Object> listIterator(int index) {
        return this.list.listIterator(index);
    }

    @Override // java.util.List
    public List<Object> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    @Override // java.util.List
    public Object get(int index) {
        return this.list.get(index);
    }

    public JSONObject getJSONObject(int index) {
        Object value = this.list.get(index);
        if (value instanceof JSONObject) {
            return (JSONObject) value;
        }
        if (value instanceof Map) {
            return new JSONObject((Map<String, Object>) value);
        }
        return (JSONObject) toJSON(value);
    }

    public JSONArray getJSONArray(int index) {
        Object value = this.list.get(index);
        if (value instanceof JSONArray) {
            return (JSONArray) value;
        }
        if (value instanceof List) {
            return new JSONArray((List<Object>) value);
        }
        return (JSONArray) toJSON(value);
    }

    public <T> T getObject(int i, Class<T> cls) {
        return (T) TypeUtils.castToJavaBean(this.list.get(i), cls);
    }

    public <T> T getObject(int i, Type type) {
        Object obj = this.list.get(i);
        if (type instanceof Class) {
            return (T) TypeUtils.castToJavaBean(obj, (Class) type);
        }
        return (T) JSON.parseObject(JSON.toJSONString(obj), type, new Feature[0]);
    }

    public Boolean getBoolean(int index) {
        Object value = get(index);
        if (value == null) {
            return null;
        }
        return TypeUtils.castToBoolean(value);
    }

    public boolean getBooleanValue(int index) {
        Object value = get(index);
        if (value == null) {
            return false;
        }
        return TypeUtils.castToBoolean(value).booleanValue();
    }

    public Byte getByte(int index) {
        Object value = get(index);
        return TypeUtils.castToByte(value);
    }

    public byte getByteValue(int index) {
        Object value = get(index);
        Byte byteVal = TypeUtils.castToByte(value);
        if (byteVal == null) {
            return (byte) 0;
        }
        return byteVal.byteValue();
    }

    public Short getShort(int index) {
        Object value = get(index);
        return TypeUtils.castToShort(value);
    }

    public short getShortValue(int index) {
        Object value = get(index);
        Short shortVal = TypeUtils.castToShort(value);
        if (shortVal == null) {
            return (short) 0;
        }
        return shortVal.shortValue();
    }

    public Integer getInteger(int index) {
        Object value = get(index);
        return TypeUtils.castToInt(value);
    }

    public int getIntValue(int index) {
        Object value = get(index);
        Integer intVal = TypeUtils.castToInt(value);
        if (intVal == null) {
            return 0;
        }
        return intVal.intValue();
    }

    public Long getLong(int index) {
        Object value = get(index);
        return TypeUtils.castToLong(value);
    }

    public long getLongValue(int index) {
        Object value = get(index);
        Long longVal = TypeUtils.castToLong(value);
        if (longVal == null) {
            return 0L;
        }
        return longVal.longValue();
    }

    public Float getFloat(int index) {
        Object value = get(index);
        return TypeUtils.castToFloat(value);
    }

    public float getFloatValue(int index) {
        Object value = get(index);
        Float floatValue = TypeUtils.castToFloat(value);
        if (floatValue == null) {
            return 0.0f;
        }
        return floatValue.floatValue();
    }

    public Double getDouble(int index) {
        Object value = get(index);
        return TypeUtils.castToDouble(value);
    }

    public double getDoubleValue(int index) {
        Object value = get(index);
        Double doubleValue = TypeUtils.castToDouble(value);
        if (doubleValue == null) {
            return 0.0d;
        }
        return doubleValue.doubleValue();
    }

    public BigDecimal getBigDecimal(int index) {
        Object value = get(index);
        return TypeUtils.castToBigDecimal(value);
    }

    public BigInteger getBigInteger(int index) {
        Object value = get(index);
        return TypeUtils.castToBigInteger(value);
    }

    public String getString(int index) {
        Object value = get(index);
        return TypeUtils.castToString(value);
    }

    public Date getDate(int index) {
        Object value = get(index);
        return TypeUtils.castToDate(value);
    }

    public Object getSqlDate(int index) {
        Object value = get(index);
        return TypeUtils.castToSqlDate(value);
    }

    public Object getTimestamp(int index) {
        Object value = get(index);
        return TypeUtils.castToTimestamp(value);
    }

    public <T> List<T> toJavaList(Class<T> clazz) {
        ArrayList arrayList = new ArrayList(size());
        ParserConfig config = ParserConfig.getGlobalInstance();
        Iterator<Object> it = iterator();
        while (it.hasNext()) {
            Object item = it.next();
            arrayList.add(TypeUtils.cast(item, (Class) clazz, config));
        }
        return arrayList;
    }

    public Object clone() {
        return new JSONArray(new ArrayList(this.list));
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JSONArray) {
            return this.list.equals(((JSONArray) obj).list);
        }
        return this.list.equals(obj);
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.list.hashCode();
    }

    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        JSONObject.SecureObjectInputStream.ensureFields();
        if (JSONObject.SecureObjectInputStream.fields != null && !JSONObject.SecureObjectInputStream.fields_error) {
            ObjectInputStream secIn = new JSONObject.SecureObjectInputStream(in);
            try {
                secIn.defaultReadObject();
                return;
            } catch (NotActiveException e) {
            }
        }
        in.defaultReadObject();
        for (Object item : this.list) {
            if (item != null) {
                String typeName = item.getClass().getName();
                if (TypeUtils.getClassFromMapping(typeName) == null) {
                    ParserConfig.global.checkAutoType(typeName, null);
                }
            }
        }
    }
}