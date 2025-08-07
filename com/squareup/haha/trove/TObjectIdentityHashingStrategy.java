package com.squareup.haha.trove;

/* loaded from: classes.dex */
public final class TObjectIdentityHashingStrategy<T> implements TObjectHashingStrategy<T> {
    @Override // com.squareup.haha.trove.TObjectHashingStrategy
    public final int computeHashCode(T object) {
        return System.identityHashCode(object);
    }

    @Override // com.squareup.haha.trove.TObjectHashingStrategy
    public final boolean equals(T o1, T o2) {
        return o1 == o2;
    }
}