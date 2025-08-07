package com.squareup.haha.trove;

import java.io.IOException;
import java.io.ObjectOutputStream;

/* loaded from: classes.dex */
final class SerializationProcedure implements TIntObjectProcedure, TLongObjectProcedure, TLongProcedure, TObjectObjectProcedure, TObjectProcedure {
    IOException exception;
    private final ObjectOutputStream stream;

    SerializationProcedure(ObjectOutputStream stream) {
        this.stream = stream;
    }

    @Override // com.squareup.haha.trove.TLongProcedure
    public final boolean execute(long val) throws IOException {
        try {
            this.stream.writeLong(val);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // com.squareup.haha.trove.TObjectProcedure
    public final boolean execute(Object val) throws IOException {
        try {
            this.stream.writeObject(val);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // com.squareup.haha.trove.TObjectObjectProcedure
    public final boolean execute(Object key, Object val) throws IOException {
        try {
            this.stream.writeObject(key);
            this.stream.writeObject(val);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // com.squareup.haha.trove.TIntObjectProcedure
    public final boolean execute(int key, Object val) throws IOException {
        try {
            this.stream.writeInt(key);
            this.stream.writeObject(val);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }

    @Override // com.squareup.haha.trove.TLongObjectProcedure
    public final boolean execute(long key, Object val) throws IOException {
        try {
            this.stream.writeLong(key);
            this.stream.writeObject(val);
            return true;
        } catch (IOException e) {
            this.exception = e;
            return false;
        }
    }
}