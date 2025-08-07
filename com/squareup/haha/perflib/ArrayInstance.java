package com.squareup.haha.perflib;

import com.squareup.haha.perflib.io.HprofBuffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class ArrayInstance extends Instance {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int mLength;
    private final Type mType;
    private final long mValuesOffset;

    public ArrayInstance(long id, StackTrace stack, Type type, int length, long valuesOffset) {
        super(id, stack);
        this.mType = type;
        this.mLength = length;
        this.mValuesOffset = valuesOffset;
    }

    public Object[] getValues() {
        Object[] values = new Object[this.mLength];
        getBuffer().setPosition(this.mValuesOffset);
        for (int i = 0; i < this.mLength; i++) {
            values[i] = readValue(this.mType);
        }
        return values;
    }

    private byte[] asRawByteArray(int start, int elementCount) {
        getBuffer().setPosition(this.mValuesOffset);
        if (this.mType == Type.OBJECT) {
            throw new AssertionError();
        }
        if (start + elementCount > this.mLength) {
            throw new AssertionError();
        }
        byte[] bytes = new byte[this.mType.getSize() * elementCount];
        getBuffer().readSubSequence(bytes, this.mType.getSize() * start, this.mType.getSize() * elementCount);
        return bytes;
    }

    public char[] asCharArray(int offset, int length) {
        if (this.mType != Type.CHAR) {
            throw new AssertionError();
        }
        CharBuffer charBuffer = ByteBuffer.wrap(asRawByteArray(offset, length)).order(HprofBuffer.HPROF_BYTE_ORDER).asCharBuffer();
        char[] result = new char[length];
        charBuffer.get(result);
        return result;
    }

    @Override // com.squareup.haha.perflib.Instance
    public final int getSize() {
        return this.mLength * this.mHeap.mSnapshot.getTypeSize(this.mType);
    }

    @Override // com.squareup.haha.perflib.Instance
    public final void accept(Visitor visitor) {
        visitor.visitArrayInstance(this);
        if (this.mType == Type.OBJECT) {
            Object[] arr$ = getValues();
            for (Object value : arr$) {
                if (value instanceof Instance) {
                    if (!this.mReferencesAdded) {
                        ((Instance) value).addReference(null, this);
                    }
                    visitor.visitLater(this, (Instance) value);
                }
            }
            this.mReferencesAdded = true;
        }
    }

    @Override // com.squareup.haha.perflib.Instance
    public ClassObj getClassObj() {
        if (this.mType == Type.OBJECT) {
            return super.getClassObj();
        }
        return this.mHeap.mSnapshot.findClass(Type.getClassNameOfPrimitiveArray(this.mType));
    }

    public Type getArrayType() {
        return this.mType;
    }

    public final String toString() {
        String className = getClassObj().getClassName();
        String className2 = className;
        if (className.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI)) {
            className2 = className2.substring(0, className2.length() - 2);
        }
        return String.format("%s[%d]@%d (0x%x)", className2, Integer.valueOf(this.mLength), Long.valueOf(getUniqueId()), Long.valueOf(getUniqueId()));
    }
}