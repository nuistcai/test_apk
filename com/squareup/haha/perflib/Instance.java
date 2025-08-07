package com.squareup.haha.perflib;

import com.squareup.haha.guava.collect.ImmutableList;
import com.squareup.haha.perflib.io.HprofBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.UByte;
import kotlin.UShort;
import kotlin.jvm.internal.IntCompanionObject;

/* loaded from: classes.dex */
public abstract class Instance {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long mClassId;
    Heap mHeap;
    protected final long mId;
    private Instance mImmediateDominator;
    private long[] mRetainedSizes;
    int mSize;
    protected final StackTrace mStack;
    int mTopologicalOrder;
    int mDistanceToGcRoot = IntCompanionObject.MAX_VALUE;
    boolean mReferencesAdded = false;
    Instance mNextInstanceToGcRoot = null;
    private final ArrayList<Instance> mHardReferences = new ArrayList<>();
    private ArrayList<Instance> mSoftReferences = null;

    public abstract void accept(Visitor visitor);

    Instance(long id, StackTrace stackTrace) {
        this.mId = id;
        this.mStack = stackTrace;
    }

    public long getId() {
        return this.mId;
    }

    public long getUniqueId() {
        return getId() & this.mHeap.mSnapshot.getIdSizeMask();
    }

    public void setClassId(long classId) {
        this.mClassId = classId;
    }

    public ClassObj getClassObj() {
        return this.mHeap.mSnapshot.findClass(this.mClassId);
    }

    public final int getCompositeSize() {
        CompositeSizeVisitor visitor = new CompositeSizeVisitor();
        visitor.doVisit(ImmutableList.of(this));
        return visitor.getCompositeSize();
    }

    public int getSize() {
        return this.mSize;
    }

    public void setSize(int size) {
        this.mSize = size;
    }

    public void setHeap(Heap heap) {
        this.mHeap = heap;
    }

    public Heap getHeap() {
        return this.mHeap;
    }

    public int getTopologicalOrder() {
        return this.mTopologicalOrder;
    }

    public void setTopologicalOrder(int topologicalOrder) {
        this.mTopologicalOrder = topologicalOrder;
    }

    public Instance getImmediateDominator() {
        return this.mImmediateDominator;
    }

    public void setImmediateDominator(Instance dominator) {
        this.mImmediateDominator = dominator;
    }

    public int getDistanceToGcRoot() {
        return this.mDistanceToGcRoot;
    }

    public Instance getNextInstanceToGcRoot() {
        return this.mNextInstanceToGcRoot;
    }

    public void setDistanceToGcRoot(int newDistance) {
        if (newDistance >= this.mDistanceToGcRoot) {
            throw new AssertionError();
        }
        this.mDistanceToGcRoot = newDistance;
    }

    public void setNextInstanceToGcRoot(Instance instance) {
        this.mNextInstanceToGcRoot = instance;
    }

    public void resetRetainedSize() {
        List<Heap> allHeaps = this.mHeap.mSnapshot.mHeaps;
        if (this.mRetainedSizes == null) {
            this.mRetainedSizes = new long[allHeaps.size()];
        } else {
            Arrays.fill(this.mRetainedSizes, 0L);
        }
        this.mRetainedSizes[allHeaps.indexOf(this.mHeap)] = getSize();
    }

    public void addRetainedSize(int heapIndex, long size) {
        long[] jArr = this.mRetainedSizes;
        jArr[heapIndex] = jArr[heapIndex] + size;
    }

    public long getRetainedSize(int heapIndex) {
        return this.mRetainedSizes[heapIndex];
    }

    public long getTotalRetainedSize() {
        if (this.mRetainedSizes == null) {
            return 0L;
        }
        long totalSize = 0;
        long[] arr$ = this.mRetainedSizes;
        for (long mRetainedSize : arr$) {
            totalSize += mRetainedSize;
        }
        return totalSize;
    }

    public void addReference(Field field, Instance reference) {
        if (reference.getIsSoftReference() && field != null && field.getName().equals("referent")) {
            if (this.mSoftReferences == null) {
                this.mSoftReferences = new ArrayList<>();
            }
            this.mSoftReferences.add(reference);
            return;
        }
        this.mHardReferences.add(reference);
    }

    public ArrayList<Instance> getHardReferences() {
        return this.mHardReferences;
    }

    public ArrayList<Instance> getSoftReferences() {
        return this.mSoftReferences;
    }

    public boolean getIsSoftReference() {
        return false;
    }

    protected Object readValue(Type type) {
        switch (type) {
            case OBJECT:
                long id = readId();
                return this.mHeap.mSnapshot.findInstance(id);
            case BOOLEAN:
                return Boolean.valueOf(getBuffer().readByte() != 0);
            case CHAR:
                return Character.valueOf(getBuffer().readChar());
            case FLOAT:
                return Float.valueOf(getBuffer().readFloat());
            case DOUBLE:
                return Double.valueOf(getBuffer().readDouble());
            case BYTE:
                return Byte.valueOf(getBuffer().readByte());
            case SHORT:
                return Short.valueOf(getBuffer().readShort());
            case INT:
                return Integer.valueOf(getBuffer().readInt());
            case LONG:
                return Long.valueOf(getBuffer().readLong());
            default:
                return null;
        }
    }

    protected long readId() {
        switch (this.mHeap.mSnapshot.getTypeSize(Type.OBJECT)) {
            case 1:
                return getBuffer().readByte();
            case 2:
                return getBuffer().readShort();
            case 4:
                return getBuffer().readInt();
            case 8:
                return getBuffer().readLong();
            default:
                return 0L;
        }
    }

    protected int readUnsignedByte() {
        return getBuffer().readByte() & UByte.MAX_VALUE;
    }

    protected int readUnsignedShort() {
        return getBuffer().readShort() & UShort.MAX_VALUE;
    }

    protected HprofBuffer getBuffer() {
        return this.mHeap.mSnapshot.mBuffer;
    }

    public static class CompositeSizeVisitor extends NonRecursiveVisitor {
        int mSize = 0;

        @Override // com.squareup.haha.perflib.NonRecursiveVisitor
        protected void defaultAction(Instance node) {
            this.mSize += node.getSize();
        }

        public int getCompositeSize() {
            return this.mSize;
        }
    }
}