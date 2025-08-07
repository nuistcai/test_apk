package cn.fly.tools.network;

import cn.fly.tools.proguard.PublicMemberKeeper;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class ByteCounterInputStream extends InputStream implements PublicMemberKeeper {
    private InputStream a;
    private long b;
    private OnReadListener c;

    public ByteCounterInputStream(InputStream inputStream) {
        this.a = inputStream;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i = this.a.read();
        if (i >= 0) {
            this.b++;
            if (this.c != null) {
                this.c.onRead(this.b);
            }
        }
        return i;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.a.read(bArr, i, i2);
        if (i3 > 0) {
            this.b += i3;
            if (this.c != null) {
                this.c.onRead(this.b);
            }
        }
        return i3;
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.a.mark(i);
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.a.markSupported();
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        this.a.reset();
        this.b = 0L;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        return this.a.skip(j);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.a.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }

    public void setOnInputStreamReadListener(OnReadListener onReadListener) {
        this.c = onReadListener;
    }
}