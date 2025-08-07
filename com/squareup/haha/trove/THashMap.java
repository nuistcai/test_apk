package com.squareup.haha.trove;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class THashMap<K, V> extends TObjectHash<K> implements Map<K, V> {
    protected transient V[] _values;

    public THashMap() {
    }

    public THashMap(TObjectHashingStrategy<K> strategy) {
        super(strategy);
    }

    public THashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public THashMap(int initialCapacity, TObjectHashingStrategy<K> strategy) {
        super(initialCapacity, strategy);
    }

    public THashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public THashMap(int initialCapacity, float loadFactor, TObjectHashingStrategy<K> strategy) {
        super(initialCapacity, loadFactor, strategy);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public THashMap(Map<K, V> map) {
        this(map.size());
        putAll(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public THashMap(Map<K, V> map, TObjectHashingStrategy<K> strategy) {
        this(map.size(), strategy);
        putAll(map);
    }

    @Override // com.squareup.haha.trove.TObjectHash, com.squareup.haha.trove.THash
    public THashMap<K, V> clone() {
        THashMap<K, V> tHashMap = (THashMap) super.clone();
        tHashMap._values = (V[]) ((Object[]) this._values.clone());
        return tHashMap;
    }

    @Override // com.squareup.haha.trove.TObjectHash, com.squareup.haha.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = (V[]) new Object[up];
        return up;
    }

    @Override // java.util.Map
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("null keys not supported");
        }
        V previous = null;
        int iInsertionIndex = insertionIndex(key);
        int index = iInsertionIndex;
        boolean z = iInsertionIndex < 0;
        boolean alreadyStored = z;
        if (z) {
            index = (-index) - 1;
            previous = this._values[index];
        }
        Object oldKey = this._set[index];
        this._set[index] = key;
        this._values[index] = value;
        if (!alreadyStored) {
            postInsertHook(oldKey == null);
        }
        return previous;
    }

    @Override // java.util.Map
    public boolean equals(Object other) {
        if (!(other instanceof Map)) {
            return false;
        }
        Map<K, V> that = (Map) other;
        if (that.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(that));
    }

    @Override // java.util.Map
    public int hashCode() {
        THashMap<K, V>.HashProcedure p = new HashProcedure();
        forEachEntry(p);
        return p.h;
    }

    final class HashProcedure implements TObjectObjectProcedure<K, V> {
        int h;

        HashProcedure() {
        }

        @Override // com.squareup.haha.trove.TObjectObjectProcedure
        public final boolean execute(K key, V value) {
            this.h += THashMap.this._hashingStrategy.computeHashCode(key) ^ (value == null ? 0 : value.hashCode());
            return true;
        }
    }

    static final class EqProcedure<K, V> implements TObjectObjectProcedure<K, V> {
        private final Map<K, V> _otherMap;

        EqProcedure(Map<K, V> otherMap) {
            this._otherMap = otherMap;
        }

        @Override // com.squareup.haha.trove.TObjectObjectProcedure
        public final boolean execute(K key, V value) {
            V oValue = this._otherMap.get(key);
            if (oValue != value) {
                return oValue != null && oValue.equals(value);
            }
            return true;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachKey(TObjectProcedure<K> tObjectProcedure) {
        return forEach(tObjectProcedure);
    }

    public boolean forEachValue(TObjectProcedure<V> procedure) {
        V[] values = this._values;
        Object[] set = this._set;
        int i = values.length;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                if (set[i2] == null || set[i2] == REMOVED || procedure.execute(values[i2])) {
                    i = i2;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachEntry(TObjectObjectProcedure<K, V> tObjectObjectProcedure) {
        Object[] keys = this._set;
        V[] values = this._values;
        int i = keys.length;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                if (keys[i2] == null || keys[i2] == REMOVED || tObjectObjectProcedure.execute(keys[i2], values[i2])) {
                    i = i2;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean retainEntries(TObjectObjectProcedure<K, V> tObjectObjectProcedure) {
        Object[] keys = this._set;
        V[] values = this._values;
        stopCompactingOnRemove();
        boolean modified = false;
        try {
            int i = keys.length;
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    if (keys[i2] == null || keys[i2] == REMOVED || tObjectObjectProcedure.execute(keys[i2], values[i2])) {
                        i = i2;
                    } else {
                        removeAt(i2);
                        modified = true;
                        i = i2;
                    }
                } else {
                    return modified;
                }
            }
        } finally {
            startCompactingOnRemove(modified);
        }
    }

    public void transformValues(TObjectFunction<V, V> function) {
        V[] values = this._values;
        Object[] set = this._set;
        int i = values.length;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                if (set[i2] != null && set[i2] != REMOVED) {
                    values[i2] = function.execute$7713a341();
                }
                i = i2;
            } else {
                return;
            }
        }
    }

    @Override // com.squareup.haha.trove.THash
    protected void rehash(int i) {
        int length = this._set.length;
        Object[] objArr = this._set;
        V[] vArr = this._values;
        this._set = new Object[i];
        this._values = (V[]) new Object[i];
        int i2 = length;
        while (true) {
            int i3 = i2 - 1;
            if (i2 > 0) {
                if (objArr[i3] == null || objArr[i3] == REMOVED) {
                    i2 = i3;
                } else {
                    Object obj = objArr[i3];
                    int iInsertionIndex = insertionIndex(obj);
                    if (iInsertionIndex < 0) {
                        throwObjectContractViolation(this._set[(-iInsertionIndex) - 1], obj);
                    }
                    this._set[iInsertionIndex] = obj;
                    this._values[iInsertionIndex] = vArr[i3];
                    i2 = i3;
                }
            } else {
                return;
            }
        }
    }

    @Override // java.util.Map
    public V get(Object key) {
        int index = index(key);
        if (index < 0) {
            return null;
        }
        return this._values[index];
    }

    @Override // com.squareup.haha.trove.THash, java.util.Map
    public void clear() {
        if (size() != 0) {
            super.clear();
            Object[] keys = this._set;
            V[] values = this._values;
            int i = keys.length;
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    keys[i2] = null;
                    values[i2] = null;
                    i = i2;
                } else {
                    return;
                }
            }
        }
    }

    @Override // java.util.Map
    public V remove(Object key) {
        int index = index(key);
        if (index < 0) {
            return null;
        }
        V prev = this._values[index];
        removeAt(index);
        return prev;
    }

    @Override // com.squareup.haha.trove.TObjectHash, com.squareup.haha.trove.THash
    protected void removeAt(int index) {
        this._values[index] = null;
        super.removeAt(index);
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return new ValueView();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return new KeyView();
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntryView();
    }

    @Override // java.util.Map
    public boolean containsValue(Object val) {
        Object[] set = this._set;
        V[] vals = this._values;
        if (val == null) {
            int i = vals.length;
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    if (set[i2] != null && set[i2] != REMOVED && val == vals[i2]) {
                        return true;
                    }
                    i = i2;
                } else {
                    return false;
                }
            }
        } else {
            int i3 = vals.length;
            while (true) {
                int i4 = i3 - 1;
                if (i3 > 0) {
                    if (set[i4] != null && set[i4] != REMOVED && (val == vals[i4] || val.equals(vals[i4]))) {
                        break;
                    }
                    i3 = i4;
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    @Override // java.util.Map
    public boolean containsKey(Object key) {
        return contains(key);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(map.size());
        for (Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    public class ValueView extends MapBackedView {
        protected ValueView() {
            super();
        }

        @Override // com.squareup.haha.trove.THashMap.MapBackedView, java.util.Set, java.util.Collection, java.lang.Iterable
        public final Iterator<V> iterator() {
            return new THashIterator<V>(THashMap.this) { // from class: com.squareup.haha.trove.THashMap.ValueView.1
                @Override // com.squareup.haha.trove.THashIterator
                protected final V objectAtIndex(int index) {
                    return THashMap.this._values[index];
                }
            };
        }

        @Override // com.squareup.haha.trove.THashMap.MapBackedView
        public final boolean containsElement(V value) {
            return THashMap.this.containsValue(value);
        }

        @Override // com.squareup.haha.trove.THashMap.MapBackedView
        public final boolean removeElement(V value) {
            boolean changed = false;
            Object[] values = THashMap.this._values;
            Object[] set = THashMap.this._set;
            int i = values.length;
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    if ((set[i2] == null || set[i2] == TObjectHash.REMOVED || value != values[i2]) && (values[i2] == null || !values[i2].equals(value))) {
                        i = i2;
                    } else {
                        THashMap.this.removeAt(i2);
                        changed = true;
                        i = i2;
                    }
                } else {
                    return changed;
                }
            }
        }
    }

    public class EntryView extends MapBackedView {
        @Override // com.squareup.haha.trove.THashMap.MapBackedView
        public final /* bridge */ /* synthetic */ boolean containsElement(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            Object obj2 = THashMap.this.get(entry.getKey());
            Object value = entry.getValue();
            if (value == obj2) {
                return true;
            }
            if (obj2 == null || !obj2.equals(value)) {
                return false;
            }
            return true;
        }

        @Override // com.squareup.haha.trove.THashMap.MapBackedView
        public final /* bridge */ /* synthetic */ boolean removeElement(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            int iIndex = THashMap.this.index(entry.getKey());
            if (iIndex < 0) {
                return false;
            }
            Object value = entry.getValue();
            if (value != THashMap.this._values[iIndex] && (value == null || !value.equals(THashMap.this._values[iIndex]))) {
                return false;
            }
            THashMap.this.removeAt(iIndex);
            return true;
        }

        EntryView() {
            super();
        }

        final class EntryIterator extends THashIterator<Map.Entry<K, V>> {
            @Override // com.squareup.haha.trove.THashIterator
            public final /* bridge */ /* synthetic */ Object objectAtIndex(int i) {
                return new Entry(THashMap.this._set[i], THashMap.this._values[i], i);
            }

            EntryIterator(THashMap<K, V> map) {
                super(map);
            }
        }

        @Override // com.squareup.haha.trove.THashMap.MapBackedView, java.util.Set, java.util.Collection, java.lang.Iterable
        public final Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(THashMap.this);
        }
    }

    abstract class MapBackedView<E> implements Set<E> {
        public abstract boolean containsElement(E e);

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public abstract Iterator<E> iterator();

        public abstract boolean removeElement(E e);

        MapBackedView() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return containsElement(obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            return removeElement(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            for (Object element : collection) {
                if (!contains(element)) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            boolean changed = false;
            for (Object element : collection) {
                if (remove(element)) {
                    changed = true;
                }
            }
            return changed;
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            THashMap.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(E obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return THashMap.this.size();
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            Object[] result = new Object[size()];
            Iterator e = iterator();
            int i = 0;
            while (e.hasNext()) {
                result[i] = e.next();
                i++;
            }
            return result;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0 */
        /* JADX WARN: Type inference failed for: r6v5 */
        /* JADX WARN: Type inference failed for: r6v7 */
        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            int size = size();
            if (tArr.length < size) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
            }
            Iterator<E> it = iterator();
            ?? r2 = tArr;
            for (int i = 0; i < size; i++) {
                r2[i] = it.next();
            }
            if (tArr.length > size) {
                tArr[size] = 0;
            }
            return tArr;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return THashMap.this.isEmpty();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            boolean changed = false;
            Iterator i = iterator();
            while (i.hasNext()) {
                if (!collection.contains(i.next())) {
                    i.remove();
                    changed = true;
                }
            }
            return changed;
        }
    }

    public class KeyView extends MapBackedView {
        KeyView() {
            super();
        }

        @Override // com.squareup.haha.trove.THashMap.MapBackedView, java.util.Set, java.util.Collection, java.lang.Iterable
        public final Iterator<K> iterator() {
            return new TObjectHashIterator(THashMap.this);
        }

        @Override // com.squareup.haha.trove.THashMap.MapBackedView
        public final boolean removeElement(K key) {
            return THashMap.this.remove(key) != null;
        }

        @Override // com.squareup.haha.trove.THashMap.MapBackedView
        public final boolean containsElement(K key) {
            return THashMap.this.contains(key);
        }
    }

    final class Entry implements Map.Entry<K, V> {
        private final int index;
        private final K key;
        private V val;

        Entry(K key, V value, int index) {
            this.key = key;
            this.val = value;
            this.index = index;
        }

        @Override // java.util.Map.Entry
        public final K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final V getValue() {
            return this.val;
        }

        @Override // java.util.Map.Entry
        public final V setValue(V o) {
            if (THashMap.this._values[this.index] != this.val) {
                throw new ConcurrentModificationException();
            }
            THashMap.this._values[this.index] = o;
            V prev = this.val;
            this.val = o;
            return prev;
        }
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(this._size);
        SerializationProcedure writeProcedure = new SerializationProcedure(stream);
        if (!forEachEntry(writeProcedure)) {
            throw writeProcedure.exception;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
        stream.defaultReadObject();
        int size = stream.readInt();
        setUp(size);
        while (true) {
            int size2 = size - 1;
            if (size > 0) {
                put(stream.readObject(), stream.readObject());
                size = size2;
            } else {
                return;
            }
        }
    }
}