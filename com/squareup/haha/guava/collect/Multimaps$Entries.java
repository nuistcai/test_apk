package com.squareup.haha.guava.collect;

import java.util.AbstractCollection;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
abstract class Multimaps$Entries<K, V> extends AbstractCollection<Map.Entry<K, V>> {
    abstract Multimap<K, V> multimap();

    Multimaps$Entries() {
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return multimap().size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean contains(@Nullable Object o) {
        if (o instanceof Map.Entry) {
            Map.Entry<?, ?> entry = (Map.Entry) o;
            return multimap().containsEntry(entry.getKey(), entry.getValue());
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(@Nullable Object o) {
        if (o instanceof Map.Entry) {
            Map.Entry<?, ?> entry = (Map.Entry) o;
            return multimap().remove(entry.getKey(), entry.getValue());
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public void clear() {
        multimap().clear();
    }
}