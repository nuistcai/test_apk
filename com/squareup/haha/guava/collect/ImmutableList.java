package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class ImmutableList<E> extends ImmutableCollection<E> implements List<E>, RandomAccess {
    static final ImmutableList<Object> EMPTY = new RegularImmutableList(ObjectArrays.EMPTY_ARRAY);

    @Override // java.util.List
    public /* bridge */ /* synthetic */ ListIterator listIterator() {
        return listIterator(0);
    }

    public static <E> ImmutableList<E> of() {
        return (ImmutableList<E>) EMPTY;
    }

    public static <E> ImmutableList<E> of(E element) {
        return new SingletonImmutableList(element);
    }

    public static <E> ImmutableList<E> of(E e1, E e2) {
        return asImmutableList(ObjectArrays.checkElementsNotNull(e1, e2));
    }

    public static <E> ImmutableList<E> copyOf(Collection<? extends E> elements) {
        if (elements instanceof ImmutableCollection) {
            ImmutableList<E> list = ((ImmutableCollection) elements).asList();
            return list.isPartialView() ? asImmutableList(list.toArray()) : list;
        }
        return asImmutableList(ObjectArrays.checkElementsNotNull(elements.toArray()));
    }

    static <E> ImmutableList<E> asImmutableList(Object[] objArr) {
        Object[] objArrArraysCopyOf;
        int length = objArr.length;
        switch (length) {
            case 0:
                return (ImmutableList<E>) EMPTY;
            case 1:
                return new SingletonImmutableList(objArr[0]);
            default:
                if (length >= objArr.length) {
                    objArrArraysCopyOf = objArr;
                } else {
                    objArrArraysCopyOf = ObjectArrays.arraysCopyOf(objArr, length);
                }
                return new RegularImmutableList(objArrArraysCopyOf);
        }
    }

    ImmutableList() {
    }

    @Override // com.squareup.haha.guava.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public UnmodifiableListIterator<E> listIterator(int index) {
        return new AbstractIndexedListIterator<E>(size(), index) { // from class: com.squareup.haha.guava.collect.ImmutableList.1
            @Override // com.squareup.haha.guava.collect.AbstractIndexedListIterator
            protected final E get(int index2) {
                return ImmutableList.this.get(index2);
            }
        };
    }

    @Override // java.util.List
    public int indexOf(@Nullable Object object) {
        if (object == null) {
            return -1;
        }
        ListIterator<E> listIterator = listIterator();
        while (listIterator.hasNext()) {
            if (Joiner.equal(object, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // java.util.List
    public int lastIndexOf(@Nullable Object object) {
        if (object == null) {
            return -1;
        }
        ListIterator<E> listIterator = listIterator(size());
        while (listIterator.hasPrevious()) {
            if (Joiner.equal(object, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(@Nullable Object object) {
        return indexOf(object) >= 0;
    }

    @Override // java.util.List
    public ImmutableList<E> subList(int i, int i2) {
        Joiner.checkPositionIndexes(i, i2, size());
        switch (i2 - i) {
            case 0:
                return (ImmutableList<E>) EMPTY;
            case 1:
                return of((Object) get(i));
            default:
                return subListUnchecked(i, i2);
        }
    }

    ImmutableList<E> subListUnchecked(int fromIndex, int toIndex) {
        return new SubList(fromIndex, toIndex - fromIndex);
    }

    class SubList extends ImmutableList<E> {
        private transient int length;
        private transient int offset;

        @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public final /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
        public final /* bridge */ /* synthetic */ ListIterator listIterator() {
            return listIterator(0);
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
        public final /* bridge */ /* synthetic */ ListIterator listIterator(int x0) {
            return super.listIterator(x0);
        }

        SubList(int offset, int length) {
            this.offset = offset;
            this.length = length;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final int size() {
            return this.length;
        }

        @Override // java.util.List
        public final E get(int index) {
            Joiner.checkElementIndex(index, this.length);
            return ImmutableList.this.get(this.offset + index);
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
        public final ImmutableList<E> subList(int fromIndex, int toIndex) {
            Joiner.checkPositionIndexes(fromIndex, toIndex, this.length);
            return ImmutableList.this.subList(this.offset + fromIndex, this.offset + toIndex);
        }

        @Override // com.squareup.haha.guava.collect.ImmutableCollection
        final boolean isPartialView() {
            return true;
        }
    }

    @Override // java.util.List
    @Deprecated
    public final boolean addAll(int index, Collection<? extends E> newElements) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    public final E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    public final void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    public final E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override // com.squareup.haha.guava.collect.ImmutableCollection
    public final ImmutableList<E> asList() {
        return this;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableCollection
    int copyIntoArray(Object[] dst, int offset) {
        int size = size();
        for (int i = 0; i < size; i++) {
            dst[offset + i] = get(i);
        }
        int i2 = offset + size;
        return i2;
    }

    public ImmutableList<E> reverse() {
        return new ReverseImmutableList(this);
    }

    static class ReverseImmutableList<E> extends ImmutableList<E> {
        private final transient ImmutableList<E> forwardList;

        @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public final /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
        public final /* bridge */ /* synthetic */ ListIterator listIterator() {
            return listIterator(0);
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
        public final /* bridge */ /* synthetic */ ListIterator listIterator(int x0) {
            return super.listIterator(x0);
        }

        ReverseImmutableList(ImmutableList<E> backingList) {
            this.forwardList = backingList;
        }

        private int reverseIndex(int index) {
            return (size() - 1) - index;
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList
        public final ImmutableList<E> reverse() {
            return this.forwardList;
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.List
        public final boolean contains(@Nullable Object object) {
            return this.forwardList.contains(object);
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
        public final int indexOf(@Nullable Object object) {
            int index = this.forwardList.lastIndexOf(object);
            if (index >= 0) {
                return reverseIndex(index);
            }
            return -1;
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
        public final int lastIndexOf(@Nullable Object object) {
            int index = this.forwardList.indexOf(object);
            if (index >= 0) {
                return reverseIndex(index);
            }
            return -1;
        }

        @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
        public final ImmutableList<E> subList(int fromIndex, int toIndex) {
            Joiner.checkPositionIndexes(fromIndex, toIndex, size());
            return this.forwardList.subList(size() - toIndex, size() - fromIndex).reverse();
        }

        @Override // java.util.List
        public final E get(int index) {
            Joiner.checkElementIndex(index, size());
            return this.forwardList.get(reverseIndex(index));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final int size() {
            return this.forwardList.size();
        }

        @Override // com.squareup.haha.guava.collect.ImmutableCollection
        final boolean isPartialView() {
            return this.forwardList.isPartialView();
        }
    }

    @Override // java.util.Collection, java.util.List
    public boolean equals(@Nullable Object obj) {
        if (obj == Joiner.checkNotNull(this)) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list = (List) obj;
        return size() == list.size() && Iterators.elementsEqual(iterator(), list.iterator());
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        int hashCode = 1;
        int n = size();
        for (int i = 0; i < n; i++) {
            hashCode = (((hashCode * 31) + get(i).hashCode()) ^ (-1)) ^ (-1);
        }
        return hashCode;
    }
}