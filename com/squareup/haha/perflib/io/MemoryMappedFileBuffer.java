package com.squareup.haha.perflib.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public class MemoryMappedFileBuffer implements HprofBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DEFAULT_PADDING = 1024;
    private static final int DEFAULT_SIZE = 1073741824;
    private final int mBufferSize;
    private final ByteBuffer[] mByteBuffers;
    private long mCurrentPosition;
    private final long mLength;
    private final int mPadding;

    MemoryMappedFileBuffer(File f, int bufferSize, int padding) throws IOException {
        this.mBufferSize = bufferSize;
        this.mPadding = padding;
        this.mLength = f.length();
        int shards = ((int) (this.mLength / this.mBufferSize)) + 1;
        this.mByteBuffers = new ByteBuffer[shards];
        FileInputStream inputStream = new FileInputStream(f);
        long offset = 0;
        for (int i = 0; i < shards; i++) {
            try {
                long size = Math.min(this.mLength - offset, this.mBufferSize + this.mPadding);
                this.mByteBuffers[i] = inputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, offset, size);
                this.mByteBuffers[i].order(HPROF_BYTE_ORDER);
                offset += this.mBufferSize;
            } finally {
                inputStream.close();
            }
        }
        this.mCurrentPosition = 0L;
    }

    public MemoryMappedFileBuffer(File f) throws IOException {
        this(f, DEFAULT_SIZE, 1024);
    }

    public void dispose() {
        for (int i = 0; i < this.mByteBuffers.length; i++) {
            try {
                this.mByteBuffers[i].cleaner().clean();
            } catch (Exception e) {
                return;
            }
        }
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public byte readByte() {
        byte result = this.mByteBuffers[getIndex()].get(getOffset());
        this.mCurrentPosition++;
        return result;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public void read(byte[] b) {
        int index = getIndex();
        this.mByteBuffers[index].position(getOffset());
        if (b.length <= this.mByteBuffers[index].remaining()) {
            this.mByteBuffers[index].get(b, 0, b.length);
        } else {
            int split = this.mBufferSize - this.mByteBuffers[index].position();
            this.mByteBuffers[index].get(b, 0, split);
            this.mByteBuffers[index + 1].position(0);
            this.mByteBuffers[index + 1].get(b, split, b.length - split);
        }
        this.mCurrentPosition += b.length;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public void readSubSequence(byte[] b, int sourceStart, int length) {
        if (length >= this.mLength) {
            throw new AssertionError();
        }
        this.mCurrentPosition += sourceStart;
        int index = getIndex();
        this.mByteBuffers[index].position(getOffset());
        if (b.length <= this.mByteBuffers[index].remaining()) {
            this.mByteBuffers[index].get(b, 0, b.length);
        } else {
            int split = this.mBufferSize - this.mByteBuffers[index].position();
            this.mByteBuffers[index].get(b, 0, split);
            int start = split;
            int remainingMaxLength = Math.min(length - split, b.length - split);
            int remainingShardCount = ((r4 + this.mBufferSize) - 1) / this.mBufferSize;
            for (int i = 0; i < remainingShardCount; i++) {
                int maxToRead = Math.min(remainingMaxLength, this.mBufferSize);
                this.mByteBuffers[index + 1 + i].position(0);
                this.mByteBuffers[index + 1 + i].get(b, start, maxToRead);
                start += maxToRead;
                remainingMaxLength -= maxToRead;
            }
        }
        this.mCurrentPosition += Math.min(b.length, length);
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public char readChar() {
        char result = this.mByteBuffers[getIndex()].getChar(getOffset());
        this.mCurrentPosition += 2;
        return result;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public short readShort() {
        short result = this.mByteBuffers[getIndex()].getShort(getOffset());
        this.mCurrentPosition += 2;
        return result;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public int readInt() {
        int result = this.mByteBuffers[getIndex()].getInt(getOffset());
        this.mCurrentPosition += 4;
        return result;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public long readLong() {
        long result = this.mByteBuffers[getIndex()].getLong(getOffset());
        this.mCurrentPosition += 8;
        return result;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public float readFloat() {
        float result = this.mByteBuffers[getIndex()].getFloat(getOffset());
        this.mCurrentPosition += 4;
        return result;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public double readDouble() {
        double result = this.mByteBuffers[getIndex()].getDouble(getOffset());
        this.mCurrentPosition += 8;
        return result;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public void setPosition(long position) {
        this.mCurrentPosition = position;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public long position() {
        return this.mCurrentPosition;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public boolean hasRemaining() {
        return this.mCurrentPosition < this.mLength;
    }

    @Override // com.squareup.haha.perflib.io.HprofBuffer
    public long remaining() {
        return this.mLength - this.mCurrentPosition;
    }

    private int getIndex() {
        return (int) (this.mCurrentPosition / this.mBufferSize);
    }

    private int getOffset() {
        return (int) (this.mCurrentPosition % this.mBufferSize);
    }
}