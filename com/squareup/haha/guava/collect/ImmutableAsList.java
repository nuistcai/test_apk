package com.squareup.haha.guava.collect;

/* loaded from: classes.dex */
abstract class ImmutableAsList<E> extends ImmutableList<E> {
    abstract ImmutableCollection<E> delegateCollection();

    ImmutableAsList() {
    }

    @Override // com.squareup.haha.guava.collect.ImmutableList, com.squareup.haha.guava.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object target) {
        return delegateCollection().contains(target);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return delegateCollection().size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return delegateCollection().isEmpty();
    }

    @Override // com.squareup.haha.guava.collect.ImmutableCollection
    final boolean isPartialView() {
        return delegateCollection().isPartialView();
    }
}