package com.squareup.haha.trove;

/* loaded from: classes.dex */
public abstract class TPrimitiveHash extends THash {
    protected transient byte[] _states;

    @Override // com.squareup.haha.trove.THash
    public Object clone() {
        TPrimitiveHash h = (TPrimitiveHash) super.clone();
        h._states = (byte[]) this._states.clone();
        return h;
    }

    @Override // com.squareup.haha.trove.THash
    protected int capacity() {
        return this._states.length;
    }

    @Override // com.squareup.haha.trove.THash
    protected void removeAt(int index) {
        this._states[index] = 2;
        super.removeAt(index);
    }

    @Override // com.squareup.haha.trove.THash
    protected int setUp(int initialCapacity) {
        int capacity = super.setUp(initialCapacity);
        this._states = new byte[capacity];
        return capacity;
    }
}