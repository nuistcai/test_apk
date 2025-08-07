package com.squareup.haha.guava.collect;

import java.io.Serializable;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
final class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
    private K key;
    private V value;

    ImmutableEntry(@Nullable K key, @Nullable V value) {
        this.key = key;
        this.value = value;
    }

    @Override // com.squareup.haha.guava.collect.AbstractMapEntry, java.util.Map.Entry
    @Nullable
    public final K getKey() {
        return this.key;
    }

    @Override // com.squareup.haha.guava.collect.AbstractMapEntry, java.util.Map.Entry
    @Nullable
    public final V getValue() {
        return this.value;
    }

    @Override // com.squareup.haha.guava.collect.AbstractMapEntry, java.util.Map.Entry
    public final V setValue(V value) {
        throw new UnsupportedOperationException();
    }
}