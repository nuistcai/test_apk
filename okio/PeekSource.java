package okio;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PeekSource.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lokio/PeekSource;", "Lokio/Source;", "upstream", "Lokio/BufferedSource;", "(Lokio/BufferedSource;)V", "buffer", "Lokio/Buffer;", "closed", "", "expectedPos", "", "expectedSegment", "Lokio/Segment;", "pos", "", "close", "", "read", "sink", "byteCount", "timeout", "Lokio/Timeout;", "jvm"}, k = 1, mv = {1, 1, 11})
/* loaded from: classes.dex */
public final class PeekSource implements Source {
    private final Buffer buffer;
    private boolean closed;
    private int expectedPos;
    private Segment expectedSegment;
    private long pos;
    private final BufferedSource upstream;

    public PeekSource(BufferedSource upstream) {
        Intrinsics.checkParameterIsNotNull(upstream, "upstream");
        this.upstream = upstream;
        this.buffer = this.upstream.getBuffer();
        this.expectedSegment = this.buffer.head;
        Segment segment = this.buffer.head;
        this.expectedPos = segment != null ? segment.pos : -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0029  */
    @Override // okio.Source
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long read(Buffer sink, long byteCount) throws IOException {
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        boolean z = true;
        if (!(!this.closed)) {
            throw new IllegalStateException("closed".toString());
        }
        if (this.expectedSegment != null) {
            if (this.expectedSegment == this.buffer.head) {
                int i = this.expectedPos;
                Segment segment = this.buffer.head;
                if (segment == null) {
                    Intrinsics.throwNpe();
                }
                if (i != segment.pos) {
                    z = false;
                }
            }
        }
        if (!z) {
            throw new IllegalStateException("Peek source is invalid because upstream source was used".toString());
        }
        this.upstream.request(this.pos + byteCount);
        if (this.expectedSegment == null && this.buffer.head != null) {
            this.expectedSegment = this.buffer.head;
            Segment segment2 = this.buffer.head;
            if (segment2 == null) {
                Intrinsics.throwNpe();
            }
            this.expectedPos = segment2.pos;
        }
        long toCopy = Math.min(byteCount, this.buffer.size() - this.pos);
        if (toCopy <= 0) {
            return -1L;
        }
        this.buffer.copyTo(sink, this.pos, toCopy);
        this.pos += toCopy;
        return toCopy;
    }

    @Override // okio.Source
    /* renamed from: timeout */
    public Timeout getThis$0() {
        return this.upstream.getThis$0();
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.closed = true;
    }
}