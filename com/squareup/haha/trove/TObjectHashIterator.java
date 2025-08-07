package com.squareup.haha.trove;

/* loaded from: classes.dex */
final class TObjectHashIterator<E> extends THashIterator<E> {
    private TObjectHash<E> _objectHash;

    public TObjectHashIterator(TObjectHash<E> hash) {
        super(hash);
        this._objectHash = hash;
    }

    @Override // com.squareup.haha.trove.THashIterator
    protected final E objectAtIndex(int i) {
        return (E) this._objectHash._set[i];
    }
}