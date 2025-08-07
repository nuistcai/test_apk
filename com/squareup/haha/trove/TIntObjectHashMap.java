package com.squareup.haha.trove;

import com.squareup.haha.guava.base.Joiner;
import kotlin.jvm.internal.IntCompanionObject;

/* loaded from: classes.dex */
public class TIntObjectHashMap<V> extends THash implements TIntHashingStrategy {
    protected final TIntHashingStrategy _hashingStrategy = this;
    private transient int[] _set;
    private transient V[] _values;

    @Override // com.squareup.haha.trove.THash
    public /* bridge */ /* synthetic */ Object clone() {
        TIntObjectHashMap tIntObjectHashMap = (TIntObjectHashMap) super.clone();
        tIntObjectHashMap._values = (V[]) ((Object[]) this._values.clone());
        return tIntObjectHashMap;
    }

    @Override // com.squareup.haha.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = (V[]) new Object[up];
        this._set = new int[up];
        return up;
    }

    @Override // com.squareup.haha.trove.THash
    protected int capacity() {
        return this._values.length;
    }

    public final V put(int i, V v) {
        boolean zIsFree = false;
        V v2 = null;
        int iInsertionIndex = insertionIndex(i);
        boolean z = true;
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            v2 = (V) unwrapNull(this._values[iInsertionIndex]);
            z = false;
        } else {
            zIsFree = isFree(this._values, iInsertionIndex);
        }
        this._set[iInsertionIndex] = i;
        ((V[]) this._values)[iInsertionIndex] = v == null ? TObjectHash.NULL : v;
        if (z) {
            postInsertHook(zIsFree);
        }
        return v2;
    }

    @Override // com.squareup.haha.trove.THash
    protected void rehash(int i) {
        int length = this._set.length;
        int[] iArr = this._set;
        V[] vArr = this._values;
        this._set = new int[i];
        this._values = (V[]) new Object[i];
        int i2 = length;
        while (true) {
            int i3 = i2 - 1;
            if (i2 > 0) {
                if (isFull(vArr, i3)) {
                    int i4 = iArr[i3];
                    int iInsertionIndex = insertionIndex(i4);
                    this._set[iInsertionIndex] = i4;
                    this._values[iInsertionIndex] = vArr[i3];
                }
                i2 = i3;
            } else {
                return;
            }
        }
    }

    public final V get(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return null;
        }
        return (V) unwrapNull(this._values[iIndex]);
    }

    private static <V> V unwrapNull(V value) {
        if (value == TObjectHash.NULL) {
            return null;
        }
        return value;
    }

    @Override // com.squareup.haha.trove.THash, java.util.Map
    public void clear() {
        super.clear();
        int[] keys = this._set;
        Object[] values = this._values;
        int i = keys.length;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                keys[i2] = 0;
                values[i2] = null;
                i = i2;
            } else {
                return;
            }
        }
    }

    protected final int index(int val) {
        int[] set = this._set;
        Object[] values = this._values;
        int length = set.length;
        int hash = this._hashingStrategy.computeHashCode(val) & IntCompanionObject.MAX_VALUE;
        int index = hash % length;
        if (!isFree(values, index) && (isRemoved(values, index) || set[index] != val)) {
            int probe = (hash % (length - 2)) + 1;
            while (true) {
                int i = index - probe;
                index = i;
                if (i < 0) {
                    index += length;
                }
                if (isFree(values, index) || (!isRemoved(values, index) && set[index] == val)) {
                    break;
                }
            }
        }
        if (isFree(values, index)) {
            return -1;
        }
        return index;
    }

    private int insertionIndex(int val) {
        Object[] values = this._values;
        int[] set = this._set;
        int length = set.length;
        int hash = this._hashingStrategy.computeHashCode(val) & IntCompanionObject.MAX_VALUE;
        int index = hash % length;
        if (isFree(values, index)) {
            return index;
        }
        if (!isFull(values, index) || set[index] != val) {
            int probe = (hash % (length - 2)) + 1;
            int firstRemoved = isRemoved(values, index) ? index : -1;
            do {
                int i = index - probe;
                index = i;
                if (i < 0) {
                    index += length;
                }
                if (firstRemoved == -1 && isRemoved(values, index)) {
                    firstRemoved = index;
                }
                if (!isFull(values, index)) {
                    break;
                }
            } while (set[index] != val);
            if (isRemoved(values, index)) {
                while (!isFree(values, index) && (isRemoved(values, index) || set[index] != val)) {
                    int i2 = index - probe;
                    index = i2;
                    if (i2 < 0) {
                        index += length;
                    }
                }
            }
            if (isFull(values, index)) {
                return (-index) - 1;
            }
            return firstRemoved == -1 ? index : firstRemoved;
        }
        return (-index) - 1;
    }

    private static boolean isFull(Object[] values, int index) {
        Object value = values[index];
        return (value == null || value == TObjectHash.REMOVED) ? false : true;
    }

    private static boolean isRemoved(Object[] values, int index) {
        return values[index] == TObjectHash.REMOVED;
    }

    private static boolean isFree(Object[] values, int index) {
        return values[index] == null;
    }

    public boolean equals(Object other) {
        if (!(other instanceof TIntObjectHashMap)) {
            return false;
        }
        TIntObjectHashMap that = (TIntObjectHashMap) other;
        if (that.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(that));
    }

    public int hashCode() {
        TIntObjectHashMap<V>.HashProcedure p = new HashProcedure();
        forEachEntry(p);
        return p.h;
    }

    final class HashProcedure implements TIntObjectProcedure<V> {
        int h;

        HashProcedure() {
        }

        @Override // com.squareup.haha.trove.TIntObjectProcedure
        public final boolean execute(int key, V value) {
            this.h += TIntObjectHashMap.this._hashingStrategy.computeHashCode(key) ^ Joiner.hash(value);
            return true;
        }
    }

    static final class EqProcedure<V> implements TIntObjectProcedure<V> {
        private final TIntObjectHashMap<V> _otherMap;

        EqProcedure(TIntObjectHashMap<V> otherMap) {
            this._otherMap = otherMap;
        }

        @Override // com.squareup.haha.trove.TIntObjectProcedure
        public final boolean execute(int key, V value) {
            if (this._otherMap.index(key) >= 0) {
                V v = this._otherMap.get(key);
                if (value == v || (value != null && value.equals(v))) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override // com.squareup.haha.trove.THash
    protected void removeAt(int i) {
        ((V[]) this._values)[i] = TObjectHash.REMOVED;
        super.removeAt(i);
    }

    public final Object[] getValues() {
        Object[] vals = new Object[size()];
        V[] values = this._values;
        int j = values.length;
        int j2 = 0;
        while (true) {
            int i = j - 1;
            if (j > 0) {
                if (isFull(values, i)) {
                    vals[j2] = unwrapNull(values[i]);
                    j2++;
                    j = i;
                } else {
                    j = i;
                }
            } else {
                return vals;
            }
        }
    }

    public final int[] keys() {
        int[] keys = new int[size()];
        int[] k = this._set;
        Object[] values = this._values;
        int j = k.length;
        int j2 = 0;
        while (true) {
            int i = j - 1;
            if (j > 0) {
                if (!isFull(values, i)) {
                    j = i;
                } else {
                    keys[j2] = k[i];
                    j2++;
                    j = i;
                }
            } else {
                return keys;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean forEachEntry(TIntObjectProcedure<V> tIntObjectProcedure) {
        int[] keys = this._set;
        V[] values = this._values;
        int i = keys.length;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                if (!isFull(values, i2) || tIntObjectProcedure.execute(keys[i2], unwrapNull(values[i2]))) {
                    i = i2;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    @Override // com.squareup.haha.trove.TIntHashingStrategy
    public final int computeHashCode(int val) {
        return val;
    }
}