package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import java.util.Iterator;

/* loaded from: classes.dex */
abstract class TransformedIterator<F, T> implements Iterator<T> {
    private Iterator<? extends F> backingIterator;

    abstract T transform(F f);

    TransformedIterator(Iterator<? extends F> backingIterator) {
        this.backingIterator = (Iterator) Joiner.checkNotNull(backingIterator);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.backingIterator.hasNext();
    }

    @Override // java.util.Iterator
    public final T next() {
        return transform(this.backingIterator.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.backingIterator.remove();
    }
}