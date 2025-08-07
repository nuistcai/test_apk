package com.squareup.haha.trove;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public class THashSet<E> extends TObjectHash<E> implements Set<E> {
    @Override // java.util.Set, java.util.Collection
    public boolean add(E obj) {
        int index = insertionIndex(obj);
        if (index < 0) {
            return false;
        }
        Object old = this._set[index];
        this._set[index] = obj;
        postInsertHook(old == null);
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean equals(Object other) {
        if (!(other instanceof Set)) {
            return false;
        }
        Set that = (Set) other;
        if (that.size() != size()) {
            return false;
        }
        return containsAll(that);
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        THashSet<E>.HashProcedure p = new HashProcedure();
        forEach(p);
        return p.h;
    }

    final class HashProcedure implements TObjectProcedure<E> {
        int h;

        HashProcedure() {
        }

        @Override // com.squareup.haha.trove.TObjectProcedure
        public final boolean execute(E key) {
            this.h += THashSet.this._hashingStrategy.computeHashCode(key);
            return true;
        }
    }

    @Override // com.squareup.haha.trove.THash
    protected void rehash(int newCapacity) {
        int oldCapacity = this._set.length;
        Object[] oldSet = this._set;
        this._set = new Object[newCapacity];
        int i = oldCapacity;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                if (oldSet[i2] == null || oldSet[i2] == REMOVED) {
                    i = i2;
                } else {
                    Object obj = oldSet[i2];
                    int index = insertionIndex(obj);
                    if (index < 0) {
                        throwObjectContractViolation(this._set[(-index) - 1], obj);
                    }
                    this._set[index] = obj;
                    i = i2;
                }
            } else {
                return;
            }
        }
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        Object[] result = new Object[size()];
        forEach(new ToObjectArrayProcedure(result));
        return result;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v7 */
    @Override // java.util.Set, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        int size = size();
        if (tArr.length < size) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
        }
        ?? r1 = tArr;
        Iterator<E> it = iterator();
        for (int i = 0; i < size; i++) {
            r1[i] = it.next();
        }
        if (tArr.length > size) {
            tArr[size] = 0;
        }
        return tArr;
    }

    @Override // com.squareup.haha.trove.THash, java.util.Map
    public void clear() {
        super.clear();
        Object[] set = this._set;
        int i = set.length;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                set[i2] = null;
                i = i2;
            } else {
                return;
            }
        }
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        int index = index(obj);
        if (index >= 0) {
            removeAt(index);
            return true;
        }
        return false;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new TObjectHashIterator(this);
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
    public boolean addAll(Collection<? extends E> collection) {
        int size = collection.size();
        ensureCapacity(size);
        Iterator<? extends E> it = collection.iterator();
        boolean changed = false;
        while (true) {
            int size2 = size - 1;
            if (size > 0) {
                if (!add(it.next())) {
                    size = size2;
                } else {
                    changed = true;
                    size = size2;
                }
            } else {
                return changed;
            }
        }
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        int size = collection.size();
        Iterator it = collection.iterator();
        boolean changed = false;
        while (true) {
            int size2 = size - 1;
            if (size > 0) {
                if (!remove(it.next())) {
                    size = size2;
                } else {
                    changed = true;
                    size = size2;
                }
            } else {
                return changed;
            }
        }
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        int size = size();
        Iterator it = iterator();
        boolean changed = false;
        while (true) {
            int size2 = size - 1;
            if (size > 0) {
                if (collection.contains(it.next())) {
                    size = size2;
                } else {
                    it.remove();
                    changed = true;
                    size = size2;
                }
            } else {
                return changed;
            }
        }
    }
}