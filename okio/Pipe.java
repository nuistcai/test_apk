package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Pipe.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0011\u001a\u00020\nJ\r\u0010\u0011\u001a\u00020\nH\u0007¢\u0006\u0002\b J\r\u0010\u0018\u001a\u00020\u0019H\u0007¢\u0006\u0002\b!J&\u0010\"\u001a\u00020\u001f*\u00020\n2\u0017\u0010#\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u001f0$¢\u0006\u0002\b%H\u0082\bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0011\u001a\u00020\n8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u001a\u0010\u0012\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0018\u001a\u00020\u00198\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0015\"\u0004\b\u001d\u0010\u0017¨\u0006&"}, d2 = {"Lokio/Pipe;", "", "maxBufferSize", "", "(J)V", "buffer", "Lokio/Buffer;", "getBuffer$jvm", "()Lokio/Buffer;", "foldedSink", "Lokio/Sink;", "getFoldedSink$jvm", "()Lokio/Sink;", "setFoldedSink$jvm", "(Lokio/Sink;)V", "getMaxBufferSize$jvm", "()J", "sink", "sinkClosed", "", "getSinkClosed$jvm", "()Z", "setSinkClosed$jvm", "(Z)V", "source", "Lokio/Source;", "()Lokio/Source;", "sourceClosed", "getSourceClosed$jvm", "setSourceClosed$jvm", "fold", "", "-deprecated_sink", "-deprecated_source", "forward", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "jvm"}, k = 1, mv = {1, 1, 11})
/* loaded from: classes.dex */
public final class Pipe {
    private final Buffer buffer = new Buffer();
    private Sink foldedSink;
    private final long maxBufferSize;
    private final Sink sink;
    private boolean sinkClosed;
    private final Source source;
    private boolean sourceClosed;

    public Pipe(long maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
        if (!(this.maxBufferSize >= 1)) {
            throw new IllegalArgumentException(("maxBufferSize < 1: " + this.maxBufferSize).toString());
        }
        this.sink = new Sink() { // from class: okio.Pipe.sink.1
            private final Timeout timeout = new Timeout();

            /* JADX WARN: Code restructure failed: missing block: B:24:0x008b, code lost:
            
                r8 = kotlin.Unit.INSTANCE;
             */
            /* JADX WARN: Code restructure failed: missing block: B:26:0x008e, code lost:
            
                if (r5 == null) goto L60;
             */
            /* JADX WARN: Code restructure failed: missing block: B:27:0x0090, code lost:
            
                r6 = r20.this$0;
                r8 = r5;
                r10 = r8.getTimeout();
                r11 = r6.sink().getTimeout();
                r13 = r10.getTimeoutNanos();
                r10.timeout(okio.Timeout.INSTANCE.minTimeout(r11.getTimeoutNanos(), r10.getTimeoutNanos()), java.util.concurrent.TimeUnit.NANOSECONDS);
             */
            /* JADX WARN: Code restructure failed: missing block: B:28:0x00c0, code lost:
            
                if (r10.getHasDeadline() == false) goto L45;
             */
            /* JADX WARN: Code restructure failed: missing block: B:29:0x00c2, code lost:
            
                r5 = r10.deadlineNanoTime();
             */
            /* JADX WARN: Code restructure failed: missing block: B:30:0x00ca, code lost:
            
                if (r11.getHasDeadline() == false) goto L32;
             */
            /* JADX WARN: Code restructure failed: missing block: B:31:0x00cc, code lost:
            
                r18 = r5;
                r10.deadlineNanoTime(java.lang.Math.min(r10.deadlineNanoTime(), r11.deadlineNanoTime()));
             */
            /* JADX WARN: Code restructure failed: missing block: B:32:0x00de, code lost:
            
                r18 = r5;
             */
            /* JADX WARN: Code restructure failed: missing block: B:34:0x00e4, code lost:
            
                r8.write(r21, r3);
             */
            /* JADX WARN: Code restructure failed: missing block: B:35:0x00e7, code lost:
            
                r10.timeout(r13, java.util.concurrent.TimeUnit.NANOSECONDS);
             */
            /* JADX WARN: Code restructure failed: missing block: B:36:0x00f0, code lost:
            
                if (r11.getHasDeadline() == false) goto L38;
             */
            /* JADX WARN: Code restructure failed: missing block: B:37:0x00f2, code lost:
            
                r10.deadlineNanoTime(r18);
             */
            /* JADX WARN: Code restructure failed: missing block: B:40:0x00fb, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x0112, code lost:
            
                if (r11.getHasDeadline() == false) goto L48;
             */
            /* JADX WARN: Code restructure failed: missing block: B:47:0x0114, code lost:
            
                r10.deadlineNanoTime(r11.deadlineNanoTime());
             */
            /* JADX WARN: Code restructure failed: missing block: B:49:0x011f, code lost:
            
                r8.write(r21, r3);
             */
            /* JADX WARN: Code restructure failed: missing block: B:50:0x0122, code lost:
            
                r10.timeout(r13, java.util.concurrent.TimeUnit.NANOSECONDS);
             */
            /* JADX WARN: Code restructure failed: missing block: B:51:0x012b, code lost:
            
                if (r11.getHasDeadline() == false) goto L92;
             */
            /* JADX WARN: Code restructure failed: missing block: B:52:0x012d, code lost:
            
                r10.clearDeadline();
             */
            /* JADX WARN: Code restructure failed: missing block: B:55:0x0135, code lost:
            
                r0 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:61:0x0148, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:62:0x0149, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Code restructure failed: missing block: B:70:0x015f, code lost:
            
                throw r0;
             */
            /* JADX WARN: Code restructure failed: missing block: B:89:?, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:90:?, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:91:?, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:92:?, code lost:
            
                return;
             */
            @Override // okio.Sink
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void write(Buffer source, long byteCount) throws Throwable {
                Intrinsics.checkParameterIsNotNull(source, "source");
                long byteCount2 = byteCount;
                Sink sink = (Sink) null;
                synchronized (Pipe.this.getBuffer()) {
                    try {
                        if (!(!Pipe.this.getSinkClosed())) {
                            throw new IllegalStateException("closed".toString());
                        }
                        while (true) {
                            if (byteCount2 > 0) {
                                Sink it = Pipe.this.getFoldedSink();
                                if (it != null) {
                                    sink = it;
                                    break;
                                }
                                if (Pipe.this.getSourceClosed()) {
                                    throw new IOException("source is closed");
                                }
                                long bufferSpaceAvailable = Pipe.this.getMaxBufferSize() - Pipe.this.getBuffer().size();
                                if (bufferSpaceAvailable == 0) {
                                    this.timeout.waitUntilNotified(Pipe.this.getBuffer());
                                } else {
                                    long bytesToWrite = Math.min(bufferSpaceAvailable, byteCount2);
                                    Pipe.this.getBuffer().write(source, bytesToWrite);
                                    byteCount2 -= bytesToWrite;
                                    Buffer buffer = Pipe.this.getBuffer();
                                    if (buffer == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                                    }
                                    buffer.notifyAll();
                                }
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                    }
                }
            }

            @Override // okio.Sink, java.io.Flushable
            public void flush() {
                Sink sink = (Sink) null;
                synchronized (Pipe.this.getBuffer()) {
                    if (!(!Pipe.this.getSinkClosed())) {
                        throw new IllegalStateException("closed".toString());
                    }
                    Sink it = Pipe.this.getFoldedSink();
                    if (it != null) {
                        sink = it;
                    } else if (Pipe.this.getSourceClosed() && Pipe.this.getBuffer().size() > 0) {
                        throw new IOException("source is closed");
                    }
                    Unit unit = Unit.INSTANCE;
                }
                if (sink != null) {
                    Pipe this_$iv = Pipe.this;
                    Sink $receiver$iv = sink;
                    Timeout this_$iv$iv = $receiver$iv.getTimeout();
                    Timeout other$iv$iv = this_$iv.sink().getTimeout();
                    long originalTimeout$iv$iv = this_$iv$iv.getTimeoutNanos();
                    this_$iv$iv.timeout(Timeout.INSTANCE.minTimeout(other$iv$iv.getTimeoutNanos(), this_$iv$iv.getTimeoutNanos()), TimeUnit.NANOSECONDS);
                    if (this_$iv$iv.getHasDeadline()) {
                        long originalDeadline$iv$iv = this_$iv$iv.deadlineNanoTime();
                        if (other$iv$iv.getHasDeadline()) {
                            this_$iv$iv.deadlineNanoTime(Math.min(this_$iv$iv.deadlineNanoTime(), other$iv$iv.deadlineNanoTime()));
                        }
                        try {
                            $receiver$iv.flush();
                            this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                            if (!other$iv$iv.getHasDeadline()) {
                                return;
                            }
                            this_$iv$iv.deadlineNanoTime(originalDeadline$iv$iv);
                        } finally {
                        }
                    } else {
                        if (other$iv$iv.getHasDeadline()) {
                            this_$iv$iv.deadlineNanoTime(other$iv$iv.deadlineNanoTime());
                        }
                        try {
                            $receiver$iv.flush();
                            this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                            if (!other$iv$iv.getHasDeadline()) {
                                return;
                            }
                            this_$iv$iv.clearDeadline();
                        } finally {
                        }
                    }
                }
            }

            @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                Sink sink = (Sink) null;
                synchronized (Pipe.this.getBuffer()) {
                    if (Pipe.this.getSinkClosed()) {
                        return;
                    }
                    Sink it = Pipe.this.getFoldedSink();
                    if (it != null) {
                        sink = it;
                    } else {
                        if (Pipe.this.getSourceClosed() && Pipe.this.getBuffer().size() > 0) {
                            throw new IOException("source is closed");
                        }
                        Pipe.this.setSinkClosed$jvm(true);
                        Buffer buffer = Pipe.this.getBuffer();
                        if (buffer == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                        }
                        buffer.notifyAll();
                    }
                    Unit unit = Unit.INSTANCE;
                    if (sink != null) {
                        Pipe this_$iv = Pipe.this;
                        Sink $receiver$iv = sink;
                        Timeout this_$iv$iv = $receiver$iv.getTimeout();
                        Timeout other$iv$iv = this_$iv.sink().getTimeout();
                        long originalTimeout$iv$iv = this_$iv$iv.getTimeoutNanos();
                        this_$iv$iv.timeout(Timeout.INSTANCE.minTimeout(other$iv$iv.getTimeoutNanos(), this_$iv$iv.getTimeoutNanos()), TimeUnit.NANOSECONDS);
                        if (this_$iv$iv.getHasDeadline()) {
                            long originalDeadline$iv$iv = this_$iv$iv.deadlineNanoTime();
                            if (other$iv$iv.getHasDeadline()) {
                                this_$iv$iv.deadlineNanoTime(Math.min(this_$iv$iv.deadlineNanoTime(), other$iv$iv.deadlineNanoTime()));
                            }
                            try {
                                $receiver$iv.close();
                                this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                                if (!other$iv$iv.getHasDeadline()) {
                                    return;
                                }
                                this_$iv$iv.deadlineNanoTime(originalDeadline$iv$iv);
                            } finally {
                            }
                        } else {
                            if (other$iv$iv.getHasDeadline()) {
                                this_$iv$iv.deadlineNanoTime(other$iv$iv.deadlineNanoTime());
                            }
                            try {
                                $receiver$iv.close();
                                this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                                if (!other$iv$iv.getHasDeadline()) {
                                    return;
                                }
                                this_$iv$iv.clearDeadline();
                            } finally {
                            }
                        }
                    }
                }
            }

            @Override // okio.Sink
            /* renamed from: timeout, reason: from getter */
            public Timeout getTimeout() {
                return this.timeout;
            }
        };
        this.source = new Source() { // from class: okio.Pipe.source.1
            private final Timeout timeout = new Timeout();

            @Override // okio.Source
            public long read(Buffer sink, long byteCount) {
                Intrinsics.checkParameterIsNotNull(sink, "sink");
                synchronized (Pipe.this.getBuffer()) {
                    if (!(!Pipe.this.getSourceClosed())) {
                        throw new IllegalStateException("closed".toString());
                    }
                    while (Pipe.this.getBuffer().size() == 0) {
                        if (Pipe.this.getSinkClosed()) {
                            return -1L;
                        }
                        this.timeout.waitUntilNotified(Pipe.this.getBuffer());
                    }
                    long j = Pipe.this.getBuffer().read(sink, byteCount);
                    Buffer buffer = Pipe.this.getBuffer();
                    if (buffer == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer.notifyAll();
                    return j;
                }
            }

            @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                synchronized (Pipe.this.getBuffer()) {
                    Pipe.this.setSourceClosed$jvm(true);
                    Buffer buffer = Pipe.this.getBuffer();
                    if (buffer == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer.notifyAll();
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // okio.Source
            /* renamed from: timeout, reason: from getter */
            public Timeout getTimeout() {
                return this.timeout;
            }
        };
    }

    /* renamed from: getMaxBufferSize$jvm, reason: from getter */
    public final long getMaxBufferSize() {
        return this.maxBufferSize;
    }

    /* renamed from: getBuffer$jvm, reason: from getter */
    public final Buffer getBuffer() {
        return this.buffer;
    }

    /* renamed from: getSinkClosed$jvm, reason: from getter */
    public final boolean getSinkClosed() {
        return this.sinkClosed;
    }

    public final void setSinkClosed$jvm(boolean z) {
        this.sinkClosed = z;
    }

    /* renamed from: getSourceClosed$jvm, reason: from getter */
    public final boolean getSourceClosed() {
        return this.sourceClosed;
    }

    public final void setSourceClosed$jvm(boolean z) {
        this.sourceClosed = z;
    }

    /* renamed from: getFoldedSink$jvm, reason: from getter */
    public final Sink getFoldedSink() {
        return this.foldedSink;
    }

    public final void setFoldedSink$jvm(Sink sink) {
        this.foldedSink = sink;
    }

    public final Sink sink() {
        return this.sink;
    }

    public final Source source() {
        return this.source;
    }

    public final void fold(Sink sink) throws IOException {
        boolean closed;
        Buffer buffer;
        Intrinsics.checkParameterIsNotNull(sink, "sink");
        while (true) {
            synchronized (this.buffer) {
                if (!(this.foldedSink == null)) {
                    throw new IllegalStateException("sink already folded".toString());
                }
                if (this.buffer.exhausted()) {
                    this.sourceClosed = true;
                    this.foldedSink = sink;
                    return;
                }
                closed = this.sinkClosed;
                buffer = new Buffer();
                buffer.write(this.buffer, this.buffer.size());
                Buffer buffer2 = this.buffer;
                if (buffer2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                }
                buffer2.notifyAll();
                Unit unit = Unit.INSTANCE;
            }
            try {
                sink.write(buffer, buffer.size());
                if (closed) {
                    sink.close();
                } else {
                    sink.flush();
                }
            } catch (Throwable th) {
                synchronized (this.buffer) {
                    this.sourceClosed = true;
                    Buffer buffer3 = this.buffer;
                    if (buffer3 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer3.notifyAll();
                    Unit unit2 = Unit.INSTANCE;
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void forward(Sink $receiver, Function1<? super Sink, Unit> function1) {
        long originalDeadline$iv;
        Timeout this_$iv = $receiver.getTimeout();
        Timeout other$iv = sink().getTimeout();
        long originalTimeout$iv = this_$iv.getTimeoutNanos();
        this_$iv.timeout(Timeout.INSTANCE.minTimeout(other$iv.getTimeoutNanos(), this_$iv.getTimeoutNanos()), TimeUnit.NANOSECONDS);
        if (this_$iv.getHasDeadline()) {
            long originalDeadline$iv2 = this_$iv.deadlineNanoTime();
            if (!other$iv.getHasDeadline()) {
                originalDeadline$iv = originalDeadline$iv2;
            } else {
                originalDeadline$iv = originalDeadline$iv2;
                this_$iv.deadlineNanoTime(Math.min(this_$iv.deadlineNanoTime(), other$iv.deadlineNanoTime()));
            }
            try {
                function1.invoke($receiver);
                InlineMarker.finallyStart(1);
                this_$iv.timeout(originalTimeout$iv, TimeUnit.NANOSECONDS);
                if (other$iv.getHasDeadline()) {
                    this_$iv.deadlineNanoTime(originalDeadline$iv);
                }
                InlineMarker.finallyEnd(1);
                return;
            } catch (Throwable th) {
                long originalDeadline$iv3 = originalDeadline$iv;
                InlineMarker.finallyStart(1);
                this_$iv.timeout(originalTimeout$iv, TimeUnit.NANOSECONDS);
                if (other$iv.getHasDeadline()) {
                    this_$iv.deadlineNanoTime(originalDeadline$iv3);
                }
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        if (other$iv.getHasDeadline()) {
            this_$iv.deadlineNanoTime(other$iv.deadlineNanoTime());
        }
        try {
            function1.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            this_$iv.timeout(originalTimeout$iv, TimeUnit.NANOSECONDS);
            if (other$iv.getHasDeadline()) {
                this_$iv.clearDeadline();
            }
            InlineMarker.finallyEnd(1);
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sink", imports = {}))
    /* renamed from: -deprecated_sink, reason: not valid java name and from getter */
    public final Sink getSink() {
        return this.sink;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "source", imports = {}))
    /* renamed from: -deprecated_source, reason: not valid java name and from getter */
    public final Source getSource() {
        return this.source;
    }
}