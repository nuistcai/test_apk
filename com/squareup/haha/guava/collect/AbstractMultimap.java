package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import com.squareup.haha.guava.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
    private transient Map<K, Collection<V>> asMap;
    private transient Collection<Map.Entry<K, V>> entries;
    private transient Set<K> keySet;
    private transient Collection<V> values;

    abstract Map<K, Collection<V>> createAsMap();

    abstract Iterator<Map.Entry<K, V>> entryIterator();

    AbstractMultimap() {
    }

    public boolean containsValue(@Nullable Object value) {
        Iterator i$ = asMap().values().iterator();
        while (i$.hasNext()) {
            if (i$.next().contains(value)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.squareup.haha.guava.collect.Multimap
    public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
        Collection<V> collection = asMap().get(key);
        return collection != null && collection.contains(value);
    }

    @Override // com.squareup.haha.guava.collect.Multimap
    public boolean remove(@Nullable Object key, @Nullable Object value) {
        Collection<V> collection = asMap().get(key);
        return collection != null && collection.remove(value);
    }

    @Override // com.squareup.haha.guava.collect.Multimap
    public boolean put(@Nullable K key, @Nullable V value) {
        return get(key).add(value);
    }

    public Collection<Map.Entry<K, V>> entries() {
        Collection<Map.Entry<K, V>> entries;
        Collection<Map.Entry<K, V>> result = this.entries;
        if (result != null) {
            return result;
        }
        byte b = 0;
        if (this instanceof SetMultimap) {
            entries = new EntrySet(this, b);
        } else {
            entries = new Entries(this, b);
        }
        this.entries = entries;
        return entries;
    }

    class Entries extends Multimaps$Entries<K, V> {
        private Entries() {
        }

        /* synthetic */ Entries(AbstractMultimap x0, byte b) {
            this();
        }

        @Override // com.squareup.haha.guava.collect.Multimaps$Entries
        final Multimap<K, V> multimap() {
            return AbstractMultimap.this;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return AbstractMultimap.this.entryIterator();
        }
    }

    class EntrySet extends Entries implements Set {
        private EntrySet(AbstractMultimap abstractMultimap) {
            super(abstractMultimap, (byte) 0);
        }

        /* synthetic */ EntrySet(AbstractMultimap x0, byte b) {
            this(x0);
        }

        @Override // java.util.Collection, java.util.Set
        public final int hashCode() {
            Iterator it = iterator();
            int iHashCode = 0;
            while (it.hasNext()) {
                Object next = it.next();
                iHashCode = ((iHashCode + (next != null ? next.hashCode() : 0)) ^ (-1)) ^ (-1);
            }
            return iHashCode;
        }

        @Override // java.util.Collection, java.util.Set
        public final boolean equals(@Nullable Object obj) {
            return Joiner.equalsImpl(this, obj);
        }
    }

    public Set<K> keySet() {
        Set<K> result = this.keySet;
        if (result != null) {
            return result;
        }
        Set<K> setCreateKeySet = createKeySet();
        this.keySet = setCreateKeySet;
        return setCreateKeySet;
    }

    Set<K> createKeySet() {
        return new Maps.KeySet(asMap());
    }

    @Override // com.squareup.haha.guava.collect.Multimap
    public Collection<V> values() {
        Collection<V> result = this.values;
        if (result != null) {
            return result;
        }
        Values values = new Values();
        this.values = values;
        return values;
    }

    class Values extends AbstractCollection<V> {
        Values() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public final Iterator<V> iterator() {
            return AbstractMultimap.this.valueIterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final int size() {
            return AbstractMultimap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean contains(@Nullable Object o) {
            return AbstractMultimap.this.containsValue(o);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final void clear() {
            AbstractMultimap.this.clear();
        }
    }

    Iterator<V> valueIterator() {
        return Maps.valueIterator(entries().iterator());
    }

    @Override // com.squareup.haha.guava.collect.Multimap
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> result = this.asMap;
        if (result != null) {
            return result;
        }
        Map<K, Collection<V>> mapCreateAsMap = createAsMap();
        this.asMap = mapCreateAsMap;
        return mapCreateAsMap;
    }

    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Multimap)) {
            return false;
        }
        return asMap().equals(((Multimap) object).asMap());
    }

    public int hashCode() {
        return asMap().hashCode();
    }

    public String toString() {
        return asMap().toString();
    }
}