package com.squareup.haha.guava.collect;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
abstract class AbstractListMultimap<K, V> extends AbstractMapBasedMultimap<K, V> implements ListMultimap<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap
    public abstract List<V> createCollection();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap, com.squareup.haha.guava.collect.Multimap
    public final /* bridge */ /* synthetic */ Collection get(Object obj) {
        return get((AbstractListMultimap<K, V>) obj);
    }

    protected AbstractListMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap, com.squareup.haha.guava.collect.Multimap
    public List<V> get(@Nullable K key) {
        return (List) super.get((AbstractListMultimap<K, V>) key);
    }

    @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap, com.squareup.haha.guava.collect.AbstractMultimap, com.squareup.haha.guava.collect.Multimap
    public boolean put(@Nullable K key, @Nullable V value) {
        return super.put(key, value);
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap, com.squareup.haha.guava.collect.Multimap
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }
}