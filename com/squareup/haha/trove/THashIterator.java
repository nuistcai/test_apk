package com.squareup.haha.trove;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/* loaded from: classes.dex */
abstract class THashIterator<V> extends TIterator implements Iterator<V> {
    private TObjectHash _hash;

    protected abstract V objectAtIndex(int i);

    public THashIterator(TObjectHash hash) {
        super(hash);
        this._hash = hash;
    }

    @Override // java.util.Iterator
    public V next() {
        moveToNextIndex();
        return objectAtIndex(this._index);
    }

    @Override // com.squareup.haha.trove.TIterator
    protected final int nextIndex() {
        int i;
        if (this._expectedSize != this._hash.size()) {
            throw new ConcurrentModificationException();
        }
        Object[] set = this._hash._set;
        int i2 = this._index;
        while (true) {
            i = i2 - 1;
            if (i2 <= 0 || !(set[i] == null || set[i] == TObjectHash.REMOVED)) {
                break;
            }
            i2 = i;
        }
        return i;
    }
}