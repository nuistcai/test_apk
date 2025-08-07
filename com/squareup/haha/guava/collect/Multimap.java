package com.squareup.haha.guava.collect;

import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public interface Multimap<K, V> {
    Map<K, Collection<V>> asMap();

    void clear();

    boolean containsEntry(@Nullable Object obj, @Nullable Object obj2);

    Collection<V> get(@Nullable K k);

    boolean put(@Nullable K k, @Nullable V v);

    boolean remove(@Nullable Object obj, @Nullable Object obj2);

    int size();

    Collection<V> values();
}