package com.squareup.haha.trove;

/* loaded from: classes.dex */
public abstract class THash implements Cloneable {
    protected static final int DEFAULT_INITIAL_CAPACITY = 4;
    protected static final float DEFAULT_LOAD_FACTOR = 0.8f;
    protected transient int _deadkeys;
    protected transient int _free;
    protected final float _loadFactor;
    protected int _maxSize;
    protected transient int _size;

    protected abstract int capacity();

    protected abstract void rehash(int i);

    public THash() {
        this(4, DEFAULT_LOAD_FACTOR);
    }

    public THash(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public THash(int initialCapacity, float loadFactor) {
        this._loadFactor = loadFactor;
        setUp(((int) (initialCapacity / loadFactor)) + 1);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public boolean isEmpty() {
        return this._size == 0;
    }

    public int size() {
        return this._size;
    }

    public void ensureCapacity(int desiredCapacity) {
        if (desiredCapacity > this._maxSize - size()) {
            rehash(PrimeFinder.nextPrime(((int) (desiredCapacity + (size() / this._loadFactor))) + 2));
            computeMaxSize(capacity());
        }
    }

    public void compact() {
        rehash(PrimeFinder.nextPrime(((int) (size() / this._loadFactor)) + 2));
        computeMaxSize(capacity());
    }

    public final void trimToSize() {
        compact();
    }

    protected void removeAt(int index) {
        this._size--;
        this._deadkeys++;
        compactIfNecessary();
    }

    private void compactIfNecessary() {
        if (this._deadkeys > this._size && capacity() > 42) {
            compact();
        }
    }

    public final void stopCompactingOnRemove() {
        if (this._deadkeys < 0) {
            throw new IllegalStateException("Unpaired stop/startCompactingOnRemove");
        }
        this._deadkeys -= capacity();
    }

    public final void startCompactingOnRemove(boolean compact) {
        if (this._deadkeys >= 0) {
            throw new IllegalStateException("Unpaired stop/startCompactingOnRemove");
        }
        this._deadkeys += capacity();
        if (compact) {
            compactIfNecessary();
        }
    }

    public void clear() {
        this._size = 0;
        this._free = capacity();
        this._deadkeys = 0;
    }

    protected int setUp(int initialCapacity) {
        int capacity = PrimeFinder.nextPrime(initialCapacity);
        computeMaxSize(capacity);
        return capacity;
    }

    private void computeMaxSize(int capacity) {
        this._maxSize = Math.min(capacity - 1, (int) (capacity * this._loadFactor));
        this._free = capacity - this._size;
        this._deadkeys = 0;
    }

    protected final void postInsertHook(boolean usedFreeSlot) {
        if (usedFreeSlot) {
            this._free--;
        } else {
            this._deadkeys--;
        }
        int i = this._size + 1;
        this._size = i;
        if (i > this._maxSize || this._free == 0) {
            rehash(PrimeFinder.nextPrime(calculateGrownCapacity()));
            computeMaxSize(capacity());
        }
    }

    protected int calculateGrownCapacity() {
        return capacity() << 1;
    }
}