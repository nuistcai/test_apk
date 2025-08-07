package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
final class SingletonImmutableList<E> extends ImmutableList<E> {
    private transient E element;

    @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator iterator() {
        return Iterators.singletonIterator(this.element);
    }

    SingletonImmutableList(E e) {
        this.element = (E) Joiner.checkNotNull(e);
    }

    @Override // java.util.List
    public final E get(int index) {
        Joiner.checkElementIndex(index, 1);
        return this.element;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
    public final int indexOf(@Nullable Object object) {
        return this.element.equals(object) ? 0 : -1;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
    public final int lastIndexOf(@Nullable Object object) {
        return indexOf(object);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return 1;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
    public final ImmutableList<E> subList(int i, int i2) {
        Joiner.checkPositionIndexes(i, i2, 1);
        return i == i2 ? (ImmutableList<E>) ImmutableList.EMPTY : this;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList
    public final ImmutableList<E> reverse() {
        return this;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(@Nullable Object object) {
        return this.element.equals(object);
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.Collection, java.util.List
    public final boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof List)) {
            return false;
        }
        List<?> that = (List) object;
        return that.size() == 1 && this.element.equals(that.get(0));
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.Collection, java.util.List
    public final int hashCode() {
        return this.element.hashCode() + 31;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        String elementToString = this.element.toString();
        return new StringBuilder(elementToString.length() + 2).append('[').append(elementToString).append(']').toString();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return false;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableCollection
    final boolean isPartialView() {
        return false;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection
    final int copyIntoArray(Object[] dst, int offset) {
        dst[offset] = this.element;
        return offset + 1;
    }
}