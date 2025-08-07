package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import com.squareup.haha.guava.collect.Maps;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
abstract class AbstractMapBasedMultimap<K, V> extends AbstractMultimap<K, V> implements Serializable {
    private transient Map<K, Collection<V>> map;
    private transient int totalSize;

    abstract Collection<V> createCollection();

    static /* synthetic */ Iterator access$100(AbstractMapBasedMultimap x0, Collection x1) {
        return x1 instanceof List ? ((List) x1).listIterator() : x1.iterator();
    }

    static /* synthetic */ int access$208(AbstractMapBasedMultimap x0) {
        int i = x0.totalSize;
        x0.totalSize = i + 1;
        return i;
    }

    static /* synthetic */ int access$210(AbstractMapBasedMultimap x0) {
        int i = x0.totalSize;
        x0.totalSize = i - 1;
        return i;
    }

    static /* synthetic */ int access$212(AbstractMapBasedMultimap x0, int x1) {
        int i = x0.totalSize + x1;
        x0.totalSize = i;
        return i;
    }

    static /* synthetic */ int access$220(AbstractMapBasedMultimap x0, int x1) {
        int i = x0.totalSize - x1;
        x0.totalSize = i;
        return i;
    }

    static /* synthetic */ int access$400(AbstractMapBasedMultimap x0, Object x1) {
        Collection collection = (Collection) Maps.safeRemove(x0.map, x1);
        if (collection == null) {
            return 0;
        }
        int size = collection.size();
        collection.clear();
        x0.totalSize -= size;
        return size;
    }

    protected AbstractMapBasedMultimap(Map<K, Collection<V>> map) {
        Joiner.checkArgument(map.isEmpty());
        this.map = map;
    }

    @Override // com.squareup.haha.guava.collect.Multimap
    public int size() {
        return this.totalSize;
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap, com.squareup.haha.guava.collect.Multimap
    public boolean put(@Nullable K key, @Nullable V value) {
        Collection<V> collection = this.map.get(key);
        if (collection != null) {
            if (collection.add(value)) {
                this.totalSize++;
                return true;
            }
            return false;
        }
        Collection<V> collection2 = createCollection();
        if (collection2.add(value)) {
            this.totalSize++;
            this.map.put(key, collection2);
            return true;
        }
        throw new AssertionError("New Collection violated the Collection spec");
    }

    @Override // com.squareup.haha.guava.collect.Multimap
    public void clear() {
        Iterator i$ = this.map.values().iterator();
        while (i$.hasNext()) {
            i$.next().clear();
        }
        this.map.clear();
        this.totalSize = 0;
    }

    @Override // com.squareup.haha.guava.collect.Multimap
    public Collection<V> get(@Nullable K key) {
        Collection<V> collection = this.map.get(key);
        Collection<V> collection2 = collection;
        if (collection == null) {
            collection2 = createCollection();
        }
        return wrapCollection(key, collection2);
    }

    final Collection<V> wrapCollection(@Nullable K key, Collection<V> collection) {
        if (collection instanceof SortedSet) {
            return new WrappedSortedSet(key, (SortedSet) collection, null);
        }
        if (collection instanceof Set) {
            return new WrappedSet(key, (Set) collection);
        }
        if (collection instanceof List) {
            return wrapList(key, (List) collection, null);
        }
        return new WrappedCollection(key, collection, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List wrapList(@Nullable Object obj, List list, @Nullable WrappedCollection wrappedCollection) {
        return list instanceof RandomAccess ? new RandomAccessWrappedList(this, obj, list, wrappedCollection) : new WrappedList(obj, list, wrappedCollection);
    }

    class WrappedCollection extends AbstractCollection<V> {
        final WrappedCollection ancestor;
        private Collection<V> ancestorDelegate;
        Collection<V> delegate;
        final K key;

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        /* JADX WARN: Multi-variable type inference failed */
        WrappedCollection(Object obj, @Nullable Collection collection, WrappedCollection wrappedCollection) {
            this.key = obj;
            this.delegate = collection;
            this.ancestor = wrappedCollection;
            this.ancestorDelegate = wrappedCollection == null ? null : wrappedCollection.delegate;
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        final void refreshIfEmpty() {
            Collection<V> newDelegate;
            if (this.ancestor != null) {
                this.ancestor.refreshIfEmpty();
                if (this.ancestor.delegate != this.ancestorDelegate) {
                    throw new ConcurrentModificationException();
                }
            } else if (this.delegate.isEmpty() && (newDelegate = (Collection) AbstractMapBasedMultimap.this.map.get(this.key)) != null) {
                this.delegate = newDelegate;
            }
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        final void removeIfEmpty() {
            if (this.ancestor != null) {
                this.ancestor.removeIfEmpty();
            } else if (this.delegate.isEmpty()) {
                AbstractMapBasedMultimap.this.map.remove(this.key);
            }
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        final void addToMap() {
            if (this.ancestor == null) {
                AbstractMapBasedMultimap.this.map.put(this.key, this.delegate);
            } else {
                this.ancestor.addToMap();
            }
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            refreshIfEmpty();
            return this.delegate.size();
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.Collection
        public boolean equals(@Nullable Object object) {
            if (object == this) {
                return true;
            }
            refreshIfEmpty();
            return this.delegate.equals(object);
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.Collection
        public int hashCode() {
            refreshIfEmpty();
            return this.delegate.hashCode();
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection
        public String toString() {
            refreshIfEmpty();
            return this.delegate.toString();
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            refreshIfEmpty();
            return new WrappedIterator();
        }

        class WrappedIterator implements Iterator<V> {
            final Iterator<V> delegateIterator;
            private Collection<V> originalDelegate;

            WrappedIterator() {
                this.originalDelegate = WrappedCollection.this.delegate;
                this.delegateIterator = AbstractMapBasedMultimap.access$100(AbstractMapBasedMultimap.this, WrappedCollection.this.delegate);
            }

            WrappedIterator(Iterator<V> delegateIterator) {
                this.originalDelegate = WrappedCollection.this.delegate;
                this.delegateIterator = delegateIterator;
            }

            final void validateIterator() {
                WrappedCollection.this.refreshIfEmpty();
                if (WrappedCollection.this.delegate != this.originalDelegate) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                validateIterator();
                return this.delegateIterator.hasNext();
            }

            @Override // java.util.Iterator
            public V next() {
                validateIterator();
                return this.delegateIterator.next();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.delegateIterator.remove();
                AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
                WrappedCollection.this.removeIfEmpty();
            }
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean add(V value) {
            refreshIfEmpty();
            boolean wasEmpty = this.delegate.isEmpty();
            boolean changed = this.delegate.add(value);
            if (changed) {
                AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this);
                if (wasEmpty) {
                    addToMap();
                }
            }
            return changed;
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int oldSize = size();
            boolean changed = this.delegate.addAll(collection);
            if (changed) {
                int newSize = this.delegate.size();
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, newSize - oldSize);
                if (oldSize == 0) {
                    addToMap();
                }
            }
            return changed;
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object o) {
            refreshIfEmpty();
            return this.delegate.contains(o);
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> c) {
            refreshIfEmpty();
            return this.delegate.containsAll(c);
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            int oldSize = size();
            if (oldSize == 0) {
                return;
            }
            this.delegate.clear();
            AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, oldSize);
            removeIfEmpty();
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object o) {
            refreshIfEmpty();
            boolean changed = this.delegate.remove(o);
            if (changed) {
                AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
                removeIfEmpty();
            }
            return changed;
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> c) {
            if (c.isEmpty()) {
                return false;
            }
            int oldSize = size();
            boolean changed = this.delegate.removeAll(c);
            if (changed) {
                int newSize = this.delegate.size();
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, newSize - oldSize);
                removeIfEmpty();
            }
            return changed;
        }

        /* JADX WARN: Generic types in debug info not equals: com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection != com.squareup.haha.guava.collect.AbstractMapBasedMultimap<K, V>$com.squareup.haha.guava.collect.AbstractMapBasedMultimap$WrappedCollection */
        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> c) {
            Joiner.checkNotNull(c);
            int oldSize = size();
            boolean changed = this.delegate.retainAll(c);
            if (changed) {
                int newSize = this.delegate.size();
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, newSize - oldSize);
                removeIfEmpty();
            }
            return changed;
        }
    }

    class WrappedSet extends WrappedCollection implements Set {
        WrappedSet(K key, @Nullable Set<V> delegate) {
            super(key, delegate, null);
        }

        @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap.WrappedCollection, java.util.AbstractCollection, java.util.Collection
        public final boolean removeAll(Collection<?> c) {
            if (c.isEmpty()) {
                return false;
            }
            int oldSize = size();
            boolean changed = Joiner.removeAllImpl((Set<?>) this.delegate, c);
            if (changed) {
                int newSize = this.delegate.size();
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, newSize - oldSize);
                removeIfEmpty();
            }
            return changed;
        }
    }

    class WrappedSortedSet extends WrappedCollection implements SortedSet {
        WrappedSortedSet(Object obj, @Nullable SortedSet sortedSet, WrappedCollection wrappedCollection) {
            super(obj, sortedSet, wrappedCollection);
        }

        private SortedSet<V> getSortedSetDelegate() {
            return (SortedSet) this.delegate;
        }

        @Override // java.util.SortedSet
        public final Comparator<? super V> comparator() {
            return getSortedSetDelegate().comparator();
        }

        @Override // java.util.SortedSet
        public final V first() {
            refreshIfEmpty();
            return getSortedSetDelegate().first();
        }

        @Override // java.util.SortedSet
        public final V last() {
            refreshIfEmpty();
            return getSortedSetDelegate().last();
        }

        @Override // java.util.SortedSet
        public final SortedSet<V> headSet(V toElement) {
            refreshIfEmpty();
            return new WrappedSortedSet(this.key, getSortedSetDelegate().headSet(toElement), this.ancestor == null ? this : this.ancestor);
        }

        @Override // java.util.SortedSet
        public final SortedSet<V> subSet(V fromElement, V toElement) {
            refreshIfEmpty();
            return new WrappedSortedSet(this.key, getSortedSetDelegate().subSet(fromElement, toElement), this.ancestor == null ? this : this.ancestor);
        }

        @Override // java.util.SortedSet
        public final SortedSet<V> tailSet(V fromElement) {
            refreshIfEmpty();
            return new WrappedSortedSet(this.key, getSortedSetDelegate().tailSet(fromElement), this.ancestor == null ? this : this.ancestor);
        }
    }

    class WrappedList extends WrappedCollection implements List {
        WrappedList(Object obj, @Nullable List list, WrappedCollection wrappedCollection) {
            super(obj, list, wrappedCollection);
        }

        final List<V> getListDelegate() {
            return (List) this.delegate;
        }

        @Override // java.util.List
        public boolean addAll(int index, Collection<? extends V> c) {
            if (c.isEmpty()) {
                return false;
            }
            int oldSize = size();
            boolean changed = getListDelegate().addAll(index, c);
            if (changed) {
                int newSize = this.delegate.size();
                AbstractMapBasedMultimap.access$212(AbstractMapBasedMultimap.this, newSize - oldSize);
                if (oldSize == 0) {
                    addToMap();
                }
            }
            return changed;
        }

        @Override // java.util.List
        public V get(int index) {
            refreshIfEmpty();
            return getListDelegate().get(index);
        }

        @Override // java.util.List
        public V set(int index, V element) {
            refreshIfEmpty();
            return getListDelegate().set(index, element);
        }

        @Override // java.util.List
        public void add(int index, V element) {
            refreshIfEmpty();
            boolean wasEmpty = this.delegate.isEmpty();
            getListDelegate().add(index, element);
            AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this);
            if (wasEmpty) {
                addToMap();
            }
        }

        @Override // java.util.List
        public V remove(int index) {
            refreshIfEmpty();
            V value = getListDelegate().remove(index);
            AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
            removeIfEmpty();
            return value;
        }

        @Override // java.util.List
        public int indexOf(Object o) {
            refreshIfEmpty();
            return getListDelegate().indexOf(o);
        }

        @Override // java.util.List
        public int lastIndexOf(Object o) {
            refreshIfEmpty();
            return getListDelegate().lastIndexOf(o);
        }

        @Override // java.util.List
        public ListIterator<V> listIterator() {
            refreshIfEmpty();
            return new WrappedListIterator();
        }

        @Override // java.util.List
        public ListIterator<V> listIterator(int index) {
            refreshIfEmpty();
            return new WrappedListIterator(index);
        }

        @Override // java.util.List
        public List<V> subList(int fromIndex, int toIndex) {
            refreshIfEmpty();
            return AbstractMapBasedMultimap.this.wrapList(this.key, getListDelegate().subList(fromIndex, toIndex), this.ancestor == null ? this : this.ancestor);
        }

        class WrappedListIterator extends WrappedCollection.WrappedIterator implements ListIterator {
            WrappedListIterator() {
                super();
            }

            public WrappedListIterator(int index) {
                super(WrappedList.this.getListDelegate().listIterator(index));
            }

            private ListIterator<V> getDelegateListIterator() {
                validateIterator();
                return (ListIterator) this.delegateIterator;
            }

            @Override // java.util.ListIterator
            public final boolean hasPrevious() {
                return getDelegateListIterator().hasPrevious();
            }

            @Override // java.util.ListIterator
            public final V previous() {
                return getDelegateListIterator().previous();
            }

            @Override // java.util.ListIterator
            public final int nextIndex() {
                return getDelegateListIterator().nextIndex();
            }

            @Override // java.util.ListIterator
            public final int previousIndex() {
                return getDelegateListIterator().previousIndex();
            }

            @Override // java.util.ListIterator
            public final void set(V value) {
                getDelegateListIterator().set(value);
            }

            @Override // java.util.ListIterator
            public final void add(V value) {
                boolean wasEmpty = WrappedList.this.isEmpty();
                getDelegateListIterator().add(value);
                AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this);
                if (wasEmpty) {
                    WrappedList.this.addToMap();
                }
            }
        }
    }

    class RandomAccessWrappedList extends WrappedList implements RandomAccess {
        RandomAccessWrappedList(AbstractMapBasedMultimap abstractMapBasedMultimap, @Nullable Object obj, List list, @Nullable WrappedCollection wrappedCollection) {
            super(obj, list, wrappedCollection);
        }
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    final Set<K> createKeySet() {
        return this.map instanceof SortedMap ? new SortedKeySet((SortedMap) this.map) : new KeySet(this.map);
    }

    class KeySet extends Maps.KeySet<K, Collection<V>> {
        KeySet(Map<K, Collection<V>> subMap) {
            super(subMap);
        }

        @Override // com.squareup.haha.guava.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            final Iterator<Map.Entry<K, V>> it = this.map.entrySet().iterator();
            return new Iterator<K>() { // from class: com.squareup.haha.guava.collect.AbstractMapBasedMultimap.KeySet.1
                private Map.Entry<K, Collection<V>> entry;

                @Override // java.util.Iterator
                public final boolean hasNext() {
                    return it.hasNext();
                }

                @Override // java.util.Iterator
                public final K next() {
                    this.entry = (Map.Entry) it.next();
                    return this.entry.getKey();
                }

                @Override // java.util.Iterator
                public final void remove() {
                    Joiner.checkRemove(this.entry != null);
                    Collection<V> collection = this.entry.getValue();
                    it.remove();
                    AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, collection.size());
                    collection.clear();
                }
            };
        }

        @Override // com.squareup.haha.guava.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object key) {
            int count = 0;
            Collection<V> collection = (Collection) this.map.remove(key);
            if (collection != null) {
                count = collection.size();
                collection.clear();
                AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, count);
            }
            return count > 0;
        }

        @Override // com.squareup.haha.guava.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            Iterators.clear(iterator());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean containsAll(Collection<?> c) {
            return this.map.keySet().containsAll(c);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public boolean equals(@Nullable Object object) {
            return this == object || this.map.keySet().equals(object);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public int hashCode() {
            return this.map.keySet().hashCode();
        }
    }

    class SortedKeySet extends KeySet implements SortedSet {
        SortedKeySet(SortedMap<K, Collection<V>> subMap) {
            super(subMap);
        }

        private SortedMap<K, Collection<V>> sortedMap() {
            return (SortedMap) this.map;
        }

        @Override // java.util.SortedSet
        public final Comparator<? super K> comparator() {
            return sortedMap().comparator();
        }

        @Override // java.util.SortedSet
        public final K first() {
            return sortedMap().firstKey();
        }

        @Override // java.util.SortedSet
        public final SortedSet<K> headSet(K toElement) {
            return new SortedKeySet(sortedMap().headMap(toElement));
        }

        @Override // java.util.SortedSet
        public final K last() {
            return sortedMap().lastKey();
        }

        @Override // java.util.SortedSet
        public final SortedSet<K> subSet(K fromElement, K toElement) {
            return new SortedKeySet(sortedMap().subMap(fromElement, toElement));
        }

        @Override // java.util.SortedSet
        public final SortedSet<K> tailSet(K fromElement) {
            return new SortedKeySet(sortedMap().tailMap(fromElement));
        }
    }

    abstract class Itr<T> implements Iterator<T> {
        private Iterator<Map.Entry<K, Collection<V>>> keyIterator;
        private K key = null;
        private Collection<V> collection = null;
        private Iterator<V> valueIterator = Iterators.emptyModifiableIterator();

        abstract T output(K k, V v);

        Itr() {
            this.keyIterator = AbstractMapBasedMultimap.this.map.entrySet().iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.keyIterator.hasNext() || this.valueIterator.hasNext();
        }

        @Override // java.util.Iterator
        public T next() {
            if (!this.valueIterator.hasNext()) {
                Map.Entry<K, Collection<V>> mapEntry = this.keyIterator.next();
                this.key = mapEntry.getKey();
                this.collection = mapEntry.getValue();
                this.valueIterator = this.collection.iterator();
            }
            return output(this.key, this.valueIterator.next());
        }

        @Override // java.util.Iterator
        public void remove() {
            this.valueIterator.remove();
            if (this.collection.isEmpty()) {
                this.keyIterator.remove();
            }
            AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
        }
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap, com.squareup.haha.guava.collect.Multimap
    public Collection<V> values() {
        return super.values();
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    final Iterator<V> valueIterator() {
        return new Itr(this) { // from class: com.squareup.haha.guava.collect.AbstractMapBasedMultimap.1
            @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap.Itr
            final V output(K key, V value) {
                return value;
            }
        };
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    public Collection<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    final Iterator<Map.Entry<K, V>> entryIterator() {
        return new Itr(this) { // from class: com.squareup.haha.guava.collect.AbstractMapBasedMultimap.2
            @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap.Itr
            final /* bridge */ /* synthetic */ Object output(Object x0, Object x1) {
                return Maps.immutableEntry(x0, x1);
            }
        };
    }

    @Override // com.squareup.haha.guava.collect.AbstractMultimap
    final Map<K, Collection<V>> createAsMap() {
        return this.map instanceof SortedMap ? new SortedAsMap((SortedMap) this.map) : new AsMap(this.map);
    }

    class AsMap extends Maps.ImprovedAbstractMap<K, Collection<V>> {
        final transient Map<K, Collection<V>> submap;

        @Override // java.util.AbstractMap, java.util.Map
        public /* bridge */ /* synthetic */ Object get(Object x0) {
            Collection<V> collection = (Collection) Maps.safeGet(this.submap, x0);
            if (collection == null) {
                return null;
            }
            return AbstractMapBasedMultimap.this.wrapCollection(x0, collection);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public /* bridge */ /* synthetic */ Object remove(Object x0) {
            Collection<V> collectionRemove = this.submap.remove(x0);
            if (collectionRemove == null) {
                return null;
            }
            Collection<V> collectionCreateCollection = AbstractMapBasedMultimap.this.createCollection();
            collectionCreateCollection.addAll(collectionRemove);
            AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, collectionRemove.size());
            collectionRemove.clear();
            return collectionCreateCollection;
        }

        AsMap(Map<K, Collection<V>> submap) {
            this.submap = submap;
        }

        @Override // com.squareup.haha.guava.collect.Maps.ImprovedAbstractMap
        protected final Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new AsMapEntries();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(Object key) {
            return Maps.safeContainsKey(this.submap, key);
        }

        @Override // com.squareup.haha.guava.collect.Maps.ImprovedAbstractMap, java.util.AbstractMap, java.util.Map
        public Set<K> keySet() {
            return AbstractMapBasedMultimap.this.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.submap.size();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean equals(@Nullable Object object) {
            return this == object || this.submap.equals(object);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int hashCode() {
            return this.submap.hashCode();
        }

        @Override // java.util.AbstractMap
        public String toString() {
            return this.submap.toString();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            if (this.submap == AbstractMapBasedMultimap.this.map) {
                AbstractMapBasedMultimap.this.clear();
            } else {
                Iterators.clear(new AsMapIterator());
            }
        }

        class AsMapEntries extends Maps.EntrySet<K, Collection<V>> {
            AsMapEntries() {
            }

            @Override // com.squareup.haha.guava.collect.Maps.EntrySet
            final Map<K, Collection<V>> map() {
                return AsMap.this;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public final Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return AsMap.this.new AsMapIterator();
            }

            @Override // com.squareup.haha.guava.collect.Maps.EntrySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean contains(Object o) {
                return Collections2.safeContains(AsMap.this.submap.entrySet(), o);
            }

            @Override // com.squareup.haha.guava.collect.Maps.EntrySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean remove(Object o) {
                if (!contains(o)) {
                    return false;
                }
                Map.Entry<?, ?> entry = (Map.Entry) o;
                AbstractMapBasedMultimap.access$400(AbstractMapBasedMultimap.this, entry.getKey());
                return true;
            }
        }

        class AsMapIterator implements Iterator<Map.Entry<K, Collection<V>>> {
            private Collection<V> collection;
            private Iterator<Map.Entry<K, Collection<V>>> delegateIterator;

            AsMapIterator() {
                this.delegateIterator = AsMap.this.submap.entrySet().iterator();
            }

            @Override // java.util.Iterator
            public final /* bridge */ /* synthetic */ Object next() {
                Map.Entry<K, Collection<V>> next = this.delegateIterator.next();
                this.collection = next.getValue();
                AsMap asMap = AsMap.this;
                K key = next.getKey();
                return Maps.immutableEntry(key, AbstractMapBasedMultimap.this.wrapCollection(key, next.getValue()));
            }

            @Override // java.util.Iterator
            public final boolean hasNext() {
                return this.delegateIterator.hasNext();
            }

            @Override // java.util.Iterator
            public final void remove() {
                this.delegateIterator.remove();
                AbstractMapBasedMultimap.access$220(AbstractMapBasedMultimap.this, this.collection.size());
                this.collection.clear();
            }
        }
    }

    class SortedAsMap extends AsMap implements SortedMap {
        private SortedSet<K> sortedKeySet;

        @Override // com.squareup.haha.guava.collect.AbstractMapBasedMultimap.AsMap, com.squareup.haha.guava.collect.Maps.ImprovedAbstractMap, java.util.AbstractMap, java.util.Map
        public final /* bridge */ /* synthetic */ Set keySet() {
            SortedSet<K> sortedSet = this.sortedKeySet;
            if (sortedSet != null) {
                return sortedSet;
            }
            SortedSet<K> sortedSetMo7createKeySet = mo7createKeySet();
            this.sortedKeySet = sortedSetMo7createKeySet;
            return sortedSetMo7createKeySet;
        }

        SortedAsMap(SortedMap<K, Collection<V>> submap) {
            super(submap);
        }

        @Override // java.util.SortedMap
        public final Comparator<? super K> comparator() {
            return ((SortedMap) this.submap).comparator();
        }

        @Override // java.util.SortedMap
        public final K firstKey() {
            return (K) ((SortedMap) this.submap).firstKey();
        }

        @Override // java.util.SortedMap
        public final K lastKey() {
            return (K) ((SortedMap) this.submap).lastKey();
        }

        @Override // java.util.SortedMap
        public final SortedMap<K, Collection<V>> headMap(K toKey) {
            return new SortedAsMap(((SortedMap) this.submap).headMap(toKey));
        }

        @Override // java.util.SortedMap
        public final SortedMap<K, Collection<V>> subMap(K fromKey, K toKey) {
            return new SortedAsMap(((SortedMap) this.submap).subMap(fromKey, toKey));
        }

        @Override // java.util.SortedMap
        public final SortedMap<K, Collection<V>> tailMap(K fromKey) {
            return new SortedAsMap(((SortedMap) this.submap).tailMap(fromKey));
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Override // com.squareup.haha.guava.collect.Maps.ImprovedAbstractMap
        /* renamed from: createKeySet, reason: merged with bridge method [inline-methods] */
        public SortedSet<K> mo7createKeySet() {
            return new SortedKeySet((SortedMap) this.submap);
        }
    }
}