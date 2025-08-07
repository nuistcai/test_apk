package com.squareup.haha.trove;

/* loaded from: classes.dex */
final class ToObjectArrayProcedure<T> implements TObjectProcedure<T> {
    private int pos;
    private final T[] target;

    public ToObjectArrayProcedure(T[] target) {
        this.target = target;
    }

    @Override // com.squareup.haha.trove.TObjectProcedure
    public final boolean execute(T value) {
        T[] tArr = this.target;
        int i = this.pos;
        this.pos = i + 1;
        tArr[i] = value;
        return true;
    }
}