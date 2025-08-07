package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {
    @Override // java.util.Map.Entry
    public abstract K getKey();

    @Override // java.util.Map.Entry
    public abstract V getValue();

    AbstractMapEntry() {
    }

    @Override // java.util.Map.Entry
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map.Entry
    public boolean equals(@Nullable Object object) {
        if (!(object instanceof Map.Entry)) {
            return false;
        }
        Map.Entry<?, ?> that = (Map.Entry) object;
        return Joiner.equal(getKey(), that.getKey()) && Joiner.equal(getValue(), that.getValue());
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        K k = getKey();
        V v = getValue();
        return (v != null ? v.hashCode() : 0) ^ (k == null ? 0 : k.hashCode());
    }

    public String toString() {
        return getKey() + "=" + getValue();
    }
}