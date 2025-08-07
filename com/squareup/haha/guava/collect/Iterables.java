package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class Iterables {
    static /* synthetic */ Iterator access$100(Iterable x0) {
        return new TransformedIterator<Iterable<? extends T>, Iterator<? extends T>>(x0.iterator()) { // from class: com.squareup.haha.guava.collect.Iterables.3
            @Override // com.squareup.haha.guava.collect.TransformedIterator
            final /* bridge */ /* synthetic */ Object transform(Object x02) {
                return ((Iterable) x02).iterator();
            }
        };
    }

    public static String toString(Iterable<?> iterable) {
        return Iterators.toString(iterable.iterator());
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b) {
        final ImmutableList immutableListOf = ImmutableList.of(a, b);
        Joiner.checkNotNull(immutableListOf);
        return new FluentIterable<T>() { // from class: com.squareup.haha.guava.collect.Iterables.2
            @Override // java.lang.Iterable
            public final Iterator<T> iterator() {
                return Iterators.concat(Iterables.access$100(immutableListOf));
            }
        };
    }
}