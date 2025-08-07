package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import java.util.AbstractSet;
import java.util.Collection;

/* loaded from: classes.dex */
abstract class Sets$ImprovedAbstractSet<E> extends AbstractSet<E> {
    Sets$ImprovedAbstractSet() {
    }

    @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> c) {
        return Joiner.removeAllImpl(this, c);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> c) {
        return super.retainAll((Collection) Joiner.checkNotNull(c));
    }
}