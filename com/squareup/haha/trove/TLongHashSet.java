package com.squareup.haha.trove;

/* loaded from: classes.dex */
public class TLongHashSet extends TLongHash {
    public final boolean add(long val) {
        int index = insertionIndex(val);
        if (index < 0) {
            return false;
        }
        byte previousState = this._states[index];
        this._set[index] = val;
        this._states[index] = 1;
        postInsertHook(previousState == 0);
        return true;
    }

    @Override // com.squareup.haha.trove.THash
    protected void rehash(int newCapacity) {
        int oldCapacity = this._set.length;
        long[] oldSet = this._set;
        byte[] oldStates = this._states;
        this._set = new long[newCapacity];
        this._states = new byte[newCapacity];
        int i = oldCapacity;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                if (oldStates[i2] == 1) {
                    long o = oldSet[i2];
                    int index = insertionIndex(o);
                    this._set[index] = o;
                    this._states[index] = 1;
                }
                i = i2;
            } else {
                return;
            }
        }
    }

    @Override // com.squareup.haha.trove.THash, java.util.Map
    public void clear() {
        super.clear();
        long[] set = this._set;
        byte[] states = this._states;
        int i = set.length;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                set[i2] = 0;
                states[i2] = 0;
                i = i2;
            } else {
                return;
            }
        }
    }

    public boolean equals(Object other) {
        if (!(other instanceof TLongHashSet)) {
            return false;
        }
        final TLongHashSet that = (TLongHashSet) other;
        if (that.size() != size()) {
            return false;
        }
        return forEach(new TLongProcedure(this) { // from class: com.squareup.haha.trove.TLongHashSet.1
            @Override // com.squareup.haha.trove.TLongProcedure
            public final boolean execute(long value) {
                return that.contains(value);
            }
        });
    }

    public int hashCode() {
        HashProcedure p = new HashProcedure();
        forEach(p);
        return p.h;
    }

    final class HashProcedure implements TLongProcedure {
        int h;

        HashProcedure() {
        }

        @Override // com.squareup.haha.trove.TLongProcedure
        public final boolean execute(long key) {
            this.h += TLongHashSet.this._hashingStrategy.computeHashCode(key);
            return true;
        }
    }
}