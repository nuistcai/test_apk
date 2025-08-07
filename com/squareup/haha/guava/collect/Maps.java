package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Function;
import com.squareup.haha.guava.base.Joiner;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import kotlin.jvm.internal.IntCompanionObject;

/* loaded from: classes.dex */
public final class Maps {

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    static abstract class EntryFunction implements Function<Map.Entry<?, ?>, Object> {
        public static final EntryFunction KEY = new EntryFunction("KEY", 0) { // from class: com.squareup.haha.guava.collect.Maps.EntryFunction.1
            {
                byte b = 0;
            }

            @Override // com.squareup.haha.guava.base.Function
            public final /* bridge */ /* synthetic */ Object apply(Map.Entry<?, ?> entry) {
                return entry.getKey();
            }
        };
        public static final EntryFunction VALUE = new EntryFunction("VALUE", 1) { // from class: com.squareup.haha.guava.collect.Maps.EntryFunction.2
            {
                int i = 1;
                byte b = 0;
            }

            @Override // com.squareup.haha.guava.base.Function
            public final /* bridge */ /* synthetic */ Object apply(Map.Entry<?, ?> entry) {
                return entry.getValue();
            }
        };

        private EntryFunction(String str, int i) {
        }

        /* synthetic */ EntryFunction(String x0, int x1, byte b) {
            this(x0, x1);
        }
    }

    static <K, V> Iterator<K> keyIterator(Iterator<Map.Entry<K, V>> entryIterator) {
        return Iterators.transform(entryIterator, EntryFunction.KEY);
    }

    static <K, V> Iterator<V> valueIterator(Iterator<Map.Entry<K, V>> entryIterator) {
        return Iterators.transform(entryIterator, EntryFunction.VALUE);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static int capacity(int expectedSize) {
        if (expectedSize >= 3) {
            if (expectedSize < 1073741824) {
                return (expectedSize / 3) + expectedSize;
            }
            return IntCompanionObject.MAX_VALUE;
        }
        if (expectedSize >= 0) {
            return expectedSize + 1;
        }
        throw new IllegalArgumentException("expectedSize cannot be negative but was: " + expectedSize);
    }

    public static <K, V> Map.Entry<K, V> immutableEntry(@Nullable K key, @Nullable V value) {
        return new ImmutableEntry(key, value);
    }

    static abstract class ImprovedAbstractMap<K, V> extends AbstractMap<K, V> {
        private transient Set<Map.Entry<K, V>> entrySet;
        private transient Set<K> keySet;
        private transient Collection<V> values;

        abstract Set<Map.Entry<K, V>> createEntrySet();

        ImprovedAbstractMap() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> result = this.entrySet;
            if (result != null) {
                return result;
            }
            Set<Map.Entry<K, V>> setCreateEntrySet = createEntrySet();
            this.entrySet = setCreateEntrySet;
            return setCreateEntrySet;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            Set<K> result = this.keySet;
            if (result != null) {
                return result;
            }
            Set<K> setMo7createKeySet = mo7createKeySet();
            this.keySet = setMo7createKeySet;
            return setMo7createKeySet;
        }

        /* renamed from: createKeySet */
        Set<K> mo7createKeySet() {
            return new KeySet(this);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Collection<V> values() {
            Collection<V> result = this.values;
            if (result != null) {
                return result;
            }
            Values values = new Values(this);
            this.values = values;
            return values;
        }
    }

    static <V> V safeGet(Map<?, V> map, @Nullable Object key) {
        Joiner.checkNotNull(map);
        try {
            return map.get(key);
        } catch (ClassCastException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    static boolean safeContainsKey(Map<?, ?> map, Object key) {
        Joiner.checkNotNull(map);
        try {
            return map.containsKey(key);
        } catch (ClassCastException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    static <V> V safeRemove(Map<?, V> map, Object key) {
        Joiner.checkNotNull(map);
        try {
            return map.remove(key);
        } catch (ClassCastException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    static {
        new Joiner.MapJoiner(Collections2.STANDARD_JOINER, "=", (byte) 0);
    }

    static class KeySet<K, V> extends Sets$ImprovedAbstractSet<K> {
        final Map<K, V> map;

        KeySet(Map<K, V> map) {
            this.map = (Map) Joiner.checkNotNull(map);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return Maps.keyIterator(this.map.entrySet().iterator());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            return this.map.containsKey(o);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (contains(o)) {
                this.map.remove(o);
                return true;
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.map.clear();
        }
    }

    static class Values<K, V> extends AbstractCollection<V> {
        private Map<K, V> map;

        Values(Map<K, V> map) {
            this.map = (Map) Joiner.checkNotNull(map);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public final Iterator<V> iterator() {
            return Maps.valueIterator(this.map.entrySet().iterator());
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean remove(Object o) {
            try {
                return super.remove(o);
            } catch (UnsupportedOperationException e) {
                for (Map.Entry<K, V> entry : this.map.entrySet()) {
                    if (Joiner.equal(o, entry.getValue())) {
                        this.map.remove(entry.getKey());
                        return true;
                    }
                }
                return false;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean removeAll(Collection<?> c) {
            try {
                return super.removeAll((Collection) Joiner.checkNotNull(c));
            } catch (UnsupportedOperationException e) {
                Set<K> toRemove = new HashSet<>();
                for (Map.Entry<K, V> entry : this.map.entrySet()) {
                    if (c.contains(entry.getValue())) {
                        toRemove.add(entry.getKey());
                    }
                }
                return this.map.keySet().removeAll(toRemove);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean retainAll(Collection<?> c) {
            try {
                return super.retainAll((Collection) Joiner.checkNotNull(c));
            } catch (UnsupportedOperationException e) {
                Set<K> toRetain = new HashSet<>();
                for (Map.Entry<K, V> entry : this.map.entrySet()) {
                    if (c.contains(entry.getValue())) {
                        toRetain.add(entry.getKey());
                    }
                }
                return this.map.keySet().retainAll(toRetain);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final int size() {
            return this.map.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final boolean contains(@Nullable Object o) {
            return this.map.containsValue(o);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final void clear() {
            this.map.clear();
        }
    }

    static abstract class EntrySet<K, V> extends Sets$ImprovedAbstractSet<Map.Entry<K, V>> {
        abstract Map<K, V> map();

        EntrySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return map().size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            map().clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) o;
            Object key = entry.getKey();
            Object objSafeGet = Maps.safeGet(map(), key);
            if (Joiner.equal(objSafeGet, entry.getValue())) {
                return objSafeGet != null || map().containsKey(key);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean isEmpty() {
            return map().isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object o) {
            if (contains(o)) {
                Map.Entry<?, ?> entry = (Map.Entry) o;
                return map().keySet().remove(entry.getKey());
            }
            return false;
        }

        @Override // com.squareup.haha.guava.collect.Sets$ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> c) {
            try {
                return super.removeAll((Collection) Joiner.checkNotNull(c));
            } catch (UnsupportedOperationException e) {
                return Joiner.removeAllImpl(this, c.iterator());
            }
        }

        @Override // com.squareup.haha.guava.collect.Sets$ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> c) {
            try {
                return super.retainAll((Collection) Joiner.checkNotNull(c));
            } catch (UnsupportedOperationException e) {
                Set<Object> keys = new HashSet<>(Maps.capacity(c.size()));
                for (Object o : c) {
                    if (contains(o)) {
                        Map.Entry<?, ?> entry = (Map.Entry) o;
                        keys.add(entry.getKey());
                    }
                }
                return map().keySet().retainAll(keys);
            }
        }
    }
}