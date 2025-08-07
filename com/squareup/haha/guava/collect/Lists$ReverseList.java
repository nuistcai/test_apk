package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class Lists$ReverseList<T> extends AbstractList<T> {
    public final List<T> forwardList;

    public Lists$ReverseList(List<T> forwardList) {
        this.forwardList = (List) Joiner.checkNotNull(forwardList);
    }

    private int reverseIndex(int index) {
        int size = size();
        Joiner.checkElementIndex(index, size);
        return (size - 1) - index;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int reversePosition(int index) {
        int size = size();
        Joiner.checkPositionIndex(index, size);
        return size - index;
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int index, @Nullable T element) {
        this.forwardList.add(reversePosition(index), element);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this.forwardList.clear();
    }

    @Override // java.util.AbstractList, java.util.List
    public T remove(int index) {
        return this.forwardList.remove(reverseIndex(index));
    }

    @Override // java.util.AbstractList
    protected void removeRange(int fromIndex, int toIndex) {
        subList(fromIndex, toIndex).clear();
    }

    @Override // java.util.AbstractList, java.util.List
    public T set(int index, @Nullable T element) {
        return this.forwardList.set(reverseIndex(index), element);
    }

    @Override // java.util.AbstractList, java.util.List
    public T get(int index) {
        return this.forwardList.get(reverseIndex(index));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.forwardList.size();
    }

    @Override // java.util.AbstractList, java.util.List
    public List<T> subList(int fromIndex, int toIndex) {
        Joiner.checkPositionIndexes(fromIndex, toIndex, size());
        return Joiner.reverse(this.forwardList.subList(reversePosition(toIndex), reversePosition(fromIndex)));
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<T> iterator() {
        return listIterator();
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<T> listIterator(int index) {
        int start = reversePosition(index);
        final ListIterator<T> forwardIterator = this.forwardList.listIterator(start);
        return new ListIterator<T>() { // from class: com.squareup.haha.guava.collect.Lists$ReverseList.1
            private boolean canRemoveOrSet;

            @Override // java.util.ListIterator
            public final void add(T e) {
                forwardIterator.add(e);
                forwardIterator.previous();
                this.canRemoveOrSet = false;
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final boolean hasNext() {
                return forwardIterator.hasPrevious();
            }

            @Override // java.util.ListIterator
            public final boolean hasPrevious() {
                return forwardIterator.hasNext();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                this.canRemoveOrSet = true;
                return (T) forwardIterator.previous();
            }

            @Override // java.util.ListIterator
            public final int nextIndex() {
                return Lists$ReverseList.this.reversePosition(forwardIterator.nextIndex());
            }

            @Override // java.util.ListIterator
            public final T previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.canRemoveOrSet = true;
                return (T) forwardIterator.next();
            }

            @Override // java.util.ListIterator
            public final int previousIndex() {
                return nextIndex() - 1;
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final void remove() {
                Joiner.checkRemove(this.canRemoveOrSet);
                forwardIterator.remove();
                this.canRemoveOrSet = false;
            }

            @Override // java.util.ListIterator
            public final void set(T e) {
                if (this.canRemoveOrSet) {
                    forwardIterator.set(e);
                    return;
                }
                throw new IllegalStateException();
            }
        };
    }
}