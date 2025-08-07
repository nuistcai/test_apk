package com.squareup.haha.guava.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class ArrayListMultimap<K, V> extends AbstractListMultimap<K, V> {
    private transient int expectedValuesPerKey;

    @Override // com.squareup.haha.guava.collect.AbstractListMultimap, com.squareup.haha.guava.collect.AbstractMultimap, com.squareup.haha.guava.collect.Multimap
    public final /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap, com.squareup.haha.guava.collect.Multimap
    public final /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap, com.squareup.haha.guava.collect.Multimap
    public final /* bridge */ /* synthetic */ boolean containsEntry(Object x0, Object x1) {
        return super.containsEntry(x0, x1);
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    public final /* bridge */ /* synthetic */ boolean containsValue(Object x0) {
        return super.containsValue(x0);
    }

    @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap, com.squareup.haha.guava.collect.AbstractMultimap
    public final /* bridge */ /* synthetic */ Collection entries() {
        return super.entries();
    }

    @Override // com.squareup.haha.guava.collect.AbstractListMultimap, com.squareup.haha.guava.collect.AbstractMultimap
    public final /* bridge */ /* synthetic */ boolean equals(Object x0) {
        return super.equals(x0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.squareup.haha.guava.collect.AbstractListMultimap, com.squareup.haha.guava.collect.AbstractMapBasedMultimap, com.squareup.haha.guava.collect.Multimap
    public final /* bridge */ /* synthetic */ List get(Object obj) {
        return super.get((ArrayListMultimap<K, V>) obj);
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    public final /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.squareup.haha.guava.collect.AbstractListMultimap, com.squareup.haha.guava.collect.AbstractMapBasedMultimap, com.squareup.haha.guava.collect.AbstractMultimap, com.squareup.haha.guava.collect.Multimap
    public final /* bridge */ /* synthetic */ boolean put(Object obj, Object obj2) {
        return super.put(obj, obj2);
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap, com.squareup.haha.guava.collect.Multimap
    public final /* bridge */ /* synthetic */ boolean remove(Object x0, Object x1) {
        return super.remove(x0, x1);
    }

    @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap, com.squareup.haha.guava.collect.Multimap
    public final /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    public final /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap, com.squareup.haha.guava.collect.AbstractMultimap, com.squareup.haha.guava.collect.Multimap
    public final /* bridge */ /* synthetic */ Collection values() {
        return super.values();
    }

    public static <K, V> ArrayListMultimap<K, V> create() {
        return new ArrayListMultimap<>();
    }

    private ArrayListMultimap() {
        super(new HashMap());
        this.expectedValuesPerKey = 3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.squareup.haha.guava.collect.AbstractListMultimap, com.squareup.haha.guava.collect.AbstractMapBasedMultimap
    public final List<V> createCollection() {
        return new ArrayList(this.expectedValuesPerKey);
    }
}