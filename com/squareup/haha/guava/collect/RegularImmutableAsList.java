package com.squareup.haha.guava.collect;

/* loaded from: classes.dex */
final class RegularImmutableAsList<E> extends ImmutableAsList<E> {
    private final ImmutableCollection<E> delegate;
    private final ImmutableList<? extends E> delegateList;

    private RegularImmutableAsList(ImmutableCollection<E> delegate, ImmutableList<? extends E> delegateList) {
        this.delegate = delegate;
        this.delegateList = delegateList;
    }

    RegularImmutableAsList(ImmutableCollection<E> delegate, Object[] array) {
        this(delegate, ImmutableList.asImmutableList(array));
    }

    @Override // com.squareup.haha.guava.collect.ImmutableAsList
    final ImmutableCollection<E> delegateCollection() {
        return this.delegate;
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, java.util.List
    public final UnmodifiableListIterator<E> listIterator(int i) {
        return this.delegateList.listIterator(i);
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection
    final int copyIntoArray(Object[] dst, int offset) {
        return this.delegateList.copyIntoArray(dst, offset);
    }

    @Override // java.util.List
    public final E get(int index) {
        return this.delegateList.get(index);
    }
}