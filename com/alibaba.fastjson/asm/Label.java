package com.alibaba.fastjson.asm;

/* loaded from: classes.dex */
public class Label {
    static final int FORWARD_REFERENCE_HANDLE_MASK = 268435455;
    static final int FORWARD_REFERENCE_TYPE_MASK = -268435456;
    static final int FORWARD_REFERENCE_TYPE_SHORT = 268435456;
    static final int FORWARD_REFERENCE_TYPE_WIDE = 536870912;
    int inputStackTop;
    Label next;
    int outputStackMax;
    int position;
    private int referenceCount;
    private int[] srcAndRefPositions;
    int status;
    Label successor;

    void put(MethodWriter owner, ByteVector out, int source, boolean wideOffset) {
        if ((this.status & 2) != 0) {
            if (wideOffset) {
                out.putInt(this.position - source);
                return;
            } else {
                out.putShort(this.position - source);
                return;
            }
        }
        if (wideOffset) {
            addReference(source, out.length, FORWARD_REFERENCE_TYPE_WIDE);
            out.putInt(-1);
        } else {
            addReference(source, out.length, FORWARD_REFERENCE_TYPE_SHORT);
            out.putShort(-1);
        }
    }

    private void addReference(int sourcePosition, int referencePosition, int referenceType) {
        if (this.srcAndRefPositions == null) {
            this.srcAndRefPositions = new int[6];
        }
        if (this.referenceCount >= this.srcAndRefPositions.length) {
            int[] a = new int[this.srcAndRefPositions.length + 6];
            System.arraycopy(this.srcAndRefPositions, 0, a, 0, this.srcAndRefPositions.length);
            this.srcAndRefPositions = a;
        }
        int[] a2 = this.srcAndRefPositions;
        int i = this.referenceCount;
        this.referenceCount = i + 1;
        a2[i] = sourcePosition;
        int[] iArr = this.srcAndRefPositions;
        int i2 = this.referenceCount;
        this.referenceCount = i2 + 1;
        iArr[i2] = referencePosition | referenceType;
    }

    void resolve(MethodWriter owner, int position, byte[] data) {
        this.status |= 2;
        this.position = position;
        int source = 0;
        while (source < this.referenceCount) {
            int i = source + 1;
            int source2 = this.srcAndRefPositions[source];
            int i2 = i + 1;
            int reference = this.srcAndRefPositions[i];
            int handle = FORWARD_REFERENCE_HANDLE_MASK & reference;
            int offset = position - source2;
            if ((FORWARD_REFERENCE_TYPE_MASK & reference) == FORWARD_REFERENCE_TYPE_SHORT) {
                data[handle] = (byte) (offset >>> 8);
                data[handle + 1] = (byte) offset;
            } else {
                int handle2 = handle + 1;
                data[handle] = (byte) (offset >>> 24);
                int handle3 = handle2 + 1;
                data[handle2] = (byte) (offset >>> 16);
                data[handle3] = (byte) (offset >>> 8);
                data[handle3 + 1] = (byte) offset;
            }
            source = i2;
        }
    }
}