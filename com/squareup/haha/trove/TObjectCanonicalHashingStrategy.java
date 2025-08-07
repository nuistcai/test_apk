package com.squareup.haha.trove;

/* loaded from: classes.dex */
final class TObjectCanonicalHashingStrategy<T> implements TObjectHashingStrategy<T> {
    TObjectCanonicalHashingStrategy() {
    }

    @Override // com.squareup.haha.trove.TObjectHashingStrategy
    public final int computeHashCode(T value) {
        if (value != null) {
            return value.hashCode();
        }
        return 0;
    }

    @Override // com.squareup.haha.trove.TObjectHashingStrategy
    public final boolean equals(T value, T value1) {
        return value != null ? value.equals(value1) : value1 == null;
    }
}