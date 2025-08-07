package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Dns.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"okhttp3/Dns$Companion$SYSTEM$1", "Lokhttp3/Dns;", "lookup", "", "Ljava/net/InetAddress;", "hostname", "", "okhttp"}, k = 1, mv = {1, 1, 15})
/* renamed from: okhttp3.Dns$Companion$SYSTEM$1 */
/* loaded from: classes.dex */
public final class Dns2 implements Dns {
    Dns2() {
    }

    @Override // okhttp3.Dns
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        Intrinsics.checkParameterIsNotNull(hostname, "hostname");
        try {
            InetAddress[] allByName = InetAddress.getAllByName(hostname);
            Intrinsics.checkExpressionValueIsNotNull(allByName, "InetAddress.getAllByName(hostname)");
            return ArraysKt.toList(allByName);
        } catch (NullPointerException e) {
            UnknownHostException $this$apply = new UnknownHostException("Broken system behaviour for dns lookup of " + hostname);
            $this$apply.initCause(e);
            throw $this$apply;
        }
    }
}