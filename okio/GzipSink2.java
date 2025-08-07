package okio;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GzipSink.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0086\b¨\u0006\u0003"}, d2 = {"gzip", "Lokio/GzipSink;", "Lokio/Sink;", "jvm"}, k = 2, mv = {1, 1, 11})
/* renamed from: okio.-GzipSinkExtensions, reason: use source file name */
/* loaded from: classes.dex */
public final class GzipSink2 {
    public static final GzipSink gzip(Sink receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        return new GzipSink(receiver);
    }
}