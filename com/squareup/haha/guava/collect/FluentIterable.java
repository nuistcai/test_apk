package com.squareup.haha.guava.collect;

/* loaded from: classes.dex */
public abstract class FluentIterable<E> implements Iterable<E> {
    private final Iterable<E> iterable = this;

    protected FluentIterable() {
    }

    public String toString() {
        return Iterables.toString(this.iterable);
    }
}