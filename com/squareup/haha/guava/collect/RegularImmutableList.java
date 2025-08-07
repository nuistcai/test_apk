package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
final class RegularImmutableList<E> extends ImmutableList<E> {
    private final transient Object[] array;
    private final transient int offset;
    private final transient int size;

    private RegularImmutableList(Object[] array, int offset, int size) {
        this.offset = offset;
        this.size = size;
        this.array = array;
    }

    RegularImmutableList(Object[] array) {
        this(array, 0, array.length);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableCollection
    final boolean isPartialView() {
        return this.size != this.array.length;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection
    final int copyIntoArray(Object[] dst, int dstOff) {
        System.arraycopy(this.array, this.offset, dst, dstOff, this.size);
        return this.size + dstOff;
    }

    @Override // java.util.List
    public final E get(int i) {
        Joiner.checkElementIndex(i, this.size);
        return (E) this.array[this.offset + i];
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
    public final int indexOf(@Nullable Object object) {
        if (object == null) {
            return -1;
        }
        for (int i = 0; i < this.size; i++) {
            if (this.array[this.offset + i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
    public final int lastIndexOf(@Nullable Object object) {
        if (object == null) {
            return -1;
        }
        for (int i = this.size - 1; i >= 0; i--) {
            if (this.array[this.offset + i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList
    final ImmutableList<E> subListUnchecked(int fromIndex, int toIndex) {
        return new RegularImmutableList(this.array, this.offset + fromIndex, toIndex - fromIndex);
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
    public final UnmodifiableListIterator<E> listIterator(int index) {
        return Iterators.forArray(this.array, this.offset, this.size, index);
    }
}