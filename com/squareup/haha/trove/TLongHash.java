package com.squareup.haha.trove;

import kotlin.jvm.internal.IntCompanionObject;

/* loaded from: classes.dex */
public abstract class TLongHash extends TPrimitiveHash implements TLongHashingStrategy {
    protected TLongHashingStrategy _hashingStrategy = this;
    protected transient long[] _set;

    @Override // com.squareup.haha.trove.TPrimitiveHash, com.squareup.haha.trove.THash
    public Object clone() {
        TLongHash h = (TLongHash) super.clone();
        h._set = (long[]) this._set.clone();
        return h;
    }

    @Override // com.squareup.haha.trove.TPrimitiveHash, com.squareup.haha.trove.THash
    protected int setUp(int initialCapacity) {
        int capacity = super.setUp(initialCapacity);
        this._set = new long[capacity];
        return capacity;
    }

    public final boolean contains(long val) {
        byte[] bArr = this._states;
        long[] jArr = this._set;
        int length = bArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(val) & IntCompanionObject.MAX_VALUE;
        int i = iComputeHashCode % length;
        if (bArr[i] != 0 && (bArr[i] == 2 || jArr[i] != val)) {
            int i2 = (iComputeHashCode % (length - 2)) + 1;
            while (true) {
                i -= i2;
                if (i < 0) {
                    i += length;
                }
                if (bArr[i] == 0 || (bArr[i] != 2 && jArr[i] == val)) {
                    break;
                }
            }
        }
        if (bArr[i] == 0) {
            i = -1;
        }
        return i >= 0;
    }

    public final boolean forEach(TLongProcedure procedure) {
        byte[] states = this._states;
        long[] set = this._set;
        int i = set.length;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return true;
            }
            if (states[i2] != 1 || procedure.execute(set[i2])) {
                i = i2;
            } else {
                return false;
            }
        }
    }

    @Override // com.squareup.haha.trove.TPrimitiveHash, com.squareup.haha.trove.THash
    protected void removeAt(int index) {
        this._set[index] = 0;
        super.removeAt(index);
    }

    protected final int insertionIndex(long val) {
        byte[] states = this._states;
        long[] set = this._set;
        int length = states.length;
        int hash = this._hashingStrategy.computeHashCode(val) & IntCompanionObject.MAX_VALUE;
        int index = hash % length;
        if (states[index] == 0) {
            return index;
        }
        if (states[index] != 1 || set[index] != val) {
            int probe = (hash % (length - 2)) + 1;
            do {
                int i = index - probe;
                index = i;
                if (i < 0) {
                    index += length;
                }
                if (states[index] != 1) {
                    break;
                }
            } while (set[index] != val);
            if (states[index] == 2) {
                int firstRemoved = index;
                while (states[index] != 0 && (states[index] == 2 || set[index] != val)) {
                    int i2 = index - probe;
                    index = i2;
                    if (i2 < 0) {
                        index += length;
                    }
                }
                return states[index] == 1 ? (-index) - 1 : firstRemoved;
            }
            int firstRemoved2 = states[index];
            return firstRemoved2 == 1 ? (-index) - 1 : index;
        }
        return (-index) - 1;
    }

    @Override // com.squareup.haha.trove.TLongHashingStrategy
    public final int computeHashCode(long val) {
        return (int) ((val >> 32) ^ val);
    }
}