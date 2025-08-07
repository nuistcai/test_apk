package com.squareup.haha.trove;

import kotlin.jvm.internal.IntCompanionObject;

/* loaded from: classes.dex */
public abstract class TObjectHash<T> extends THash implements TObjectHashingStrategy<T> {
    protected final TObjectHashingStrategy<T> _hashingStrategy;
    protected transient Object[] _set;
    public static final Object REMOVED = new Object();
    public static final NULL NULL = new NULL();

    static class NULL {
        NULL() {
        }
    }

    public TObjectHash() {
        this._hashingStrategy = this;
    }

    public TObjectHash(TObjectHashingStrategy<T> strategy) {
        this._hashingStrategy = strategy;
    }

    public TObjectHash(int initialCapacity) {
        super(initialCapacity);
        this._hashingStrategy = this;
    }

    public TObjectHash(int initialCapacity, TObjectHashingStrategy<T> strategy) {
        super(initialCapacity);
        this._hashingStrategy = strategy;
    }

    public TObjectHash(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this._hashingStrategy = this;
    }

    public TObjectHash(int initialCapacity, float loadFactor, TObjectHashingStrategy<T> strategy) {
        super(initialCapacity, loadFactor);
        this._hashingStrategy = strategy;
    }

    @Override // com.squareup.haha.trove.THash
    public TObjectHash<T> clone() {
        TObjectHash<T> h = (TObjectHash) super.clone();
        h._set = (Object[]) this._set.clone();
        return h;
    }

    @Override // com.squareup.haha.trove.THash
    protected int capacity() {
        return this._set.length;
    }

    @Override // com.squareup.haha.trove.THash
    protected void removeAt(int index) {
        this._set[index] = REMOVED;
        super.removeAt(index);
    }

    @Override // com.squareup.haha.trove.THash
    protected int setUp(int initialCapacity) {
        int capacity = super.setUp(initialCapacity);
        this._set = new Object[capacity];
        return capacity;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEach(TObjectProcedure<T> tObjectProcedure) {
        Object[] set = this._set;
        int i = set.length;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                if (set[i2] == null || set[i2] == REMOVED || tObjectProcedure.execute(set[i2])) {
                    i = i2;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean contains(Object obj) {
        return index(obj) >= 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected int index(T t) {
        Object[] objArr = this._set;
        int length = objArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(t) & IntCompanionObject.MAX_VALUE;
        int i = iComputeHashCode % length;
        Object obj = objArr[i];
        Object obj2 = obj;
        if (obj != null && (obj2 == REMOVED || !this._hashingStrategy.equals(obj2, t))) {
            int i2 = (iComputeHashCode % (length - 2)) + 1;
            while (true) {
                int i3 = i - i2;
                i = i3;
                if (i3 < 0) {
                    i += length;
                }
                Object obj3 = objArr[i];
                obj2 = obj3;
                if (obj3 == null || (obj2 != REMOVED && this._hashingStrategy.equals(obj2, t))) {
                    break;
                }
            }
        }
        if (obj2 == null) {
            return -1;
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected int insertionIndex(T t) {
        Object obj;
        Object[] objArr = this._set;
        int length = objArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(t) & IntCompanionObject.MAX_VALUE;
        int i = iComputeHashCode % length;
        Object obj2 = objArr[i];
        if (obj2 != null) {
            if (obj2 == REMOVED || !this._hashingStrategy.equals(obj2, t)) {
                int i2 = (iComputeHashCode % (length - 2)) + 1;
                int i3 = obj2 == REMOVED ? i : -1;
                do {
                    int i4 = i - i2;
                    i = i4;
                    if (i4 < 0) {
                        i += length;
                    }
                    obj = objArr[i];
                    if (i3 == -1 && obj == REMOVED) {
                        i3 = i;
                    }
                    if (obj == null || obj == REMOVED) {
                        break;
                    }
                } while (!this._hashingStrategy.equals(obj, t));
                if (obj == REMOVED) {
                    while (obj != null && (obj == REMOVED || !this._hashingStrategy.equals(obj, t))) {
                        int i5 = i - i2;
                        i = i5;
                        if (i5 < 0) {
                            i += length;
                        }
                        obj = objArr[i];
                    }
                }
                if (obj == null || obj == REMOVED) {
                    return i3 == -1 ? i : i3;
                }
                return (-i) - 1;
            }
            return (-i) - 1;
        }
        return i;
    }

    @Override // com.squareup.haha.trove.TObjectHashingStrategy
    public final int computeHashCode(T o) {
        if (o != null) {
            return o.hashCode();
        }
        return 0;
    }

    @Override // com.squareup.haha.trove.TObjectHashingStrategy
    public final boolean equals(T o1, T o2) {
        return o1 != null ? o1.equals(o2) : o2 == null;
    }

    protected final void throwObjectContractViolation(Object o1, Object o2) throws IllegalArgumentException {
        throw new IllegalArgumentException("Equal objects must have equal hashcodes. During rehashing, Trove discovered that the following two objects claim to be equal (as in java.lang.Object.equals() or TObjectHashingStrategy.equals()) but their hashCodes (or those calculated by your TObjectHashingStrategy) are not equal.This violates the general contract of java.lang.Object.hashCode().  See bullet point two in that method's documentation. object #1 =" + o1 + (o1 == null ? "" : " (" + o1.getClass() + ")") + ", hashCode=" + this._hashingStrategy.computeHashCode(o1) + "; object #2 =" + o2 + (o2 != null ? " (" + o2.getClass() + ")" : "") + ", hashCode=" + this._hashingStrategy.computeHashCode(o2));
    }
}