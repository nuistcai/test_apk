package com.squareup.haha.trove;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
abstract class TIterator {
    protected int _expectedSize;
    private THash _hash;
    protected int _index;

    protected abstract int nextIndex();

    public TIterator(THash hash) {
        this._hash = hash;
        this._expectedSize = this._hash.size();
        this._index = this._hash.capacity();
    }

    public boolean hasNext() {
        return nextIndex() >= 0;
    }

    public void remove() {
        if (this._expectedSize != this._hash.size()) {
            throw new ConcurrentModificationException();
        }
        this._hash.stopCompactingOnRemove();
        try {
            this._hash.removeAt(this._index);
            this._hash.startCompactingOnRemove(false);
            this._expectedSize--;
        } catch (Throwable th) {
            this._hash.startCompactingOnRemove(false);
            throw th;
        }
    }

    protected final void moveToNextIndex() {
        int iNextIndex = nextIndex();
        this._index = iNextIndex;
        if (iNextIndex < 0) {
            throw new NoSuchElementException();
        }
    }
}