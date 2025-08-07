package com.squareup.haha.trove;

import java.io.Serializable;

/* loaded from: classes.dex */
public interface TObjectHashingStrategy<T> extends Serializable {
    int computeHashCode(T t);

    boolean equals(T t, T t2);

    static {
        new TObjectIdentityHashingStrategy();
        new TObjectCanonicalHashingStrategy();
    }
}