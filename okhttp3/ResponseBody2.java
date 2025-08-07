package okhttp3;

import kotlin.Metadata;
import okio.BufferedSource;

/* compiled from: ResponseBody.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\n\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"okhttp3/ResponseBody$Companion$asResponseBody$1", "Lokhttp3/ResponseBody;", "contentLength", "", "contentType", "Lokhttp3/MediaType;", "source", "Lokio/BufferedSource;", "okhttp"}, k = 1, mv = {1, 1, 15})
/* renamed from: okhttp3.ResponseBody$Companion$asResponseBody$1 */
/* loaded from: classes.dex */
public final class ResponseBody2 extends ResponseBody {
    final /* synthetic */ long $contentLength;
    final /* synthetic */ MediaType $contentType;

    ResponseBody2(MediaType $captured_local_variable$1, long $captured_local_variable$2) {
        contentType = $captured_local_variable$1;
        contentLength = $captured_local_variable$2;
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: contentType, reason: from getter */
    public MediaType get$contentType() {
        return contentType;
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: contentLength, reason: from getter */
    public long get$contentLength() {
        return contentLength;
    }

    @Override // okhttp3.ResponseBody
    /* renamed from: source, reason: from getter */
    public BufferedSource get$this_asResponseBody() {
        return asResponseBody;
    }
}