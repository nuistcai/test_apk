package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.random.Random;
import kotlin.random.Random2;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: _Ranges.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000n\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\u001a'\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t\u001a\u0012\u0010\u0000\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n\u001a'\u0010\u000b\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u0012\u0010\u000b\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u0012\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\b*\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\t*\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a3\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00012\b\u0010\f\u001a\u0004\u0018\u0001H\u0001¢\u0006\u0002\u0010\u000e\u001a/\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a-\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0012¢\u0006\u0002\u0010\u0013\u001a\u001a\u0010\r\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u001a\u0010\r\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u001a\u0010\r\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0018\u0010\r\u001a\u00020\b*\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u001a\u001a\u0010\r\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\t*\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u001a\u001a\u0010\r\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b \u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0087\n¢\u0006\u0002\u0010\"\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020#2\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\u0087\n¢\u0006\u0002\u0010$\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020)*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\r\u0010*\u001a\u00020\u0018*\u00020\u0016H\u0087\b\u001a\u0014\u0010*\u001a\u00020\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\b*\u00020!H\u0087\b\u001a\u0014\u0010*\u001a\u00020\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\t*\u00020#H\u0087\b\u001a\u0014\u0010*\u001a\u00020\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007\u001a\n\u0010,\u001a\u00020)*\u00020)\u001a\n\u0010,\u001a\u00020&*\u00020&\u001a\n\u0010,\u001a\u00020(*\u00020(\u001a\u0015\u0010-\u001a\u00020)*\u00020)2\u0006\u0010-\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010-\u001a\u00020&*\u00020&2\u0006\u0010-\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010-\u001a\u00020(*\u00020(2\u0006\u0010-\u001a\u00020\tH\u0086\u0004\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000¢\u0006\u0002\u0010/\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u0007H\u0000¢\u0006\u0002\u00100\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\bH\u0000¢\u0006\u0002\u00101\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\tH\u0000¢\u0006\u0002\u00102\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\nH\u0000¢\u0006\u0002\u00103\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\u0006H\u0000¢\u0006\u0002\u00105\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\u0007H\u0000¢\u0006\u0002\u00106\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000¢\u0006\u0002\u00107\u001a\u0013\u00108\u001a\u0004\u0018\u00010\t*\u00020\u0006H\u0000¢\u0006\u0002\u00109\u001a\u0013\u00108\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000¢\u0006\u0002\u0010:\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\u0006H\u0000¢\u0006\u0002\u0010<\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000¢\u0006\u0002\u0010=\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\bH\u0000¢\u0006\u0002\u0010>\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\tH\u0000¢\u0006\u0002\u0010?\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020\u0016*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004¨\u0006A"}, d2 = {"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "random", "Lkotlin/random/Random;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"}, k = 5, mv = {1, 1, 15}, xi = 1, xs = "kotlin/ranges/RangesKt")
/* renamed from: kotlin.ranges.RangesKt___RangesKt, reason: use source file name */
/* loaded from: classes.dex */
public class _Ranges extends Ranges7 {
    private static final int random(Ranges5 $this$random) {
        return RangesKt.random($this$random, Random.INSTANCE);
    }

    private static final long random(Ranges6 $this$random) {
        return RangesKt.random($this$random, Random.INSTANCE);
    }

    private static final char random(Ranges $this$random) {
        return RangesKt.random($this$random, Random.INSTANCE);
    }

    public static final int random(Ranges5 random, Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        try {
            return Random2.nextInt(random2, random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static final long random(Ranges6 random, Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        try {
            return Random2.nextLong(random2, random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static final char random(Ranges random, Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        try {
            return (char) random2.nextInt(random.getFirst(), random.getLast() + 1);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    private static final boolean contains(Ranges5 contains, Integer element) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return element != null && contains.contains(element.intValue());
    }

    private static final boolean contains(Ranges6 contains, Long element) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return element != null && contains.contains(element.longValue());
    }

    private static final boolean contains(Ranges contains, Character element) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return element != null && contains.contains(element.charValue());
    }

    public static final boolean intRangeContains(Range<Integer> contains, byte value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Integer.valueOf(value));
    }

    public static final boolean longRangeContains(Range<Long> contains, byte value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Long.valueOf(value));
    }

    public static final boolean shortRangeContains(Range<Short> contains, byte value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Short.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean doubleRangeContains(Range<Double> contains, byte value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Double.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean floatRangeContains(Range<Float> contains, byte value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Float.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean intRangeContains(Range<Integer> contains, double value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Integer it = RangesKt.toIntExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean longRangeContains(Range<Long> contains, double value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Long it = RangesKt.toLongExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean byteRangeContains(Range<Byte> contains, double value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean shortRangeContains(Range<Short> contains, double value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    public static final boolean floatRangeContains(Range<Float> contains, double value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Float.valueOf((float) value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean intRangeContains(Range<Integer> contains, float value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Integer it = RangesKt.toIntExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean longRangeContains(Range<Long> contains, float value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Long it = RangesKt.toLongExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean byteRangeContains(Range<Byte> contains, float value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean shortRangeContains(Range<Short> contains, float value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    public static final boolean doubleRangeContains(Range<Double> contains, float value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Double.valueOf(value));
    }

    public static final boolean longRangeContains(Range<Long> contains, int value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Long.valueOf(value));
    }

    public static final boolean byteRangeContains(Range<Byte> contains, int value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    public static final boolean shortRangeContains(Range<Short> contains, int value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean doubleRangeContains(Range<Double> contains, int value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Double.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean floatRangeContains(Range<Float> contains, int value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Float.valueOf(value));
    }

    public static final boolean intRangeContains(Range<Integer> contains, long value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Integer it = RangesKt.toIntExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    public static final boolean byteRangeContains(Range<Byte> contains, long value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    public static final boolean shortRangeContains(Range<Short> contains, long value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean doubleRangeContains(Range<Double> contains, long value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Double.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean floatRangeContains(Range<Float> contains, long value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Float.valueOf(value));
    }

    public static final boolean intRangeContains(Range<Integer> contains, short value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Integer.valueOf(value));
    }

    public static final boolean longRangeContains(Range<Long> contains, short value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Long.valueOf(value));
    }

    public static final boolean byteRangeContains(Range<Byte> contains, short value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean doubleRangeContains(Range<Double> contains, short value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Double.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    public static final boolean floatRangeContains(Range<Float> contains, short value) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.contains(Float.valueOf(value));
    }

    public static final Progressions2 downTo(int $this$downTo, byte to) {
        return Progressions2.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions3 downTo(long $this$downTo, byte to) {
        return Progressions3.INSTANCE.fromClosedRange($this$downTo, to, -1L);
    }

    public static final Progressions2 downTo(byte $this$downTo, byte to) {
        return Progressions2.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions2 downTo(short $this$downTo, byte to) {
        return Progressions2.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions downTo(char $this$downTo, char to) {
        return Progressions.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions2 downTo(int $this$downTo, int to) {
        return Progressions2.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions3 downTo(long $this$downTo, int to) {
        return Progressions3.INSTANCE.fromClosedRange($this$downTo, to, -1L);
    }

    public static final Progressions2 downTo(byte $this$downTo, int to) {
        return Progressions2.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions2 downTo(short $this$downTo, int to) {
        return Progressions2.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions3 downTo(int $this$downTo, long to) {
        return Progressions3.INSTANCE.fromClosedRange($this$downTo, to, -1L);
    }

    public static final Progressions3 downTo(long $this$downTo, long to) {
        return Progressions3.INSTANCE.fromClosedRange($this$downTo, to, -1L);
    }

    public static final Progressions3 downTo(byte $this$downTo, long to) {
        return Progressions3.INSTANCE.fromClosedRange($this$downTo, to, -1L);
    }

    public static final Progressions3 downTo(short $this$downTo, long to) {
        return Progressions3.INSTANCE.fromClosedRange($this$downTo, to, -1L);
    }

    public static final Progressions2 downTo(int $this$downTo, short to) {
        return Progressions2.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions3 downTo(long $this$downTo, short to) {
        return Progressions3.INSTANCE.fromClosedRange($this$downTo, to, -1L);
    }

    public static final Progressions2 downTo(byte $this$downTo, short to) {
        return Progressions2.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions2 downTo(short $this$downTo, short to) {
        return Progressions2.INSTANCE.fromClosedRange($this$downTo, to, -1);
    }

    public static final Progressions2 reversed(Progressions2 reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        return Progressions2.INSTANCE.fromClosedRange(reversed.getLast(), reversed.getFirst(), -reversed.getStep());
    }

    public static final Progressions3 reversed(Progressions3 reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        return Progressions3.INSTANCE.fromClosedRange(reversed.getLast(), reversed.getFirst(), -reversed.getStep());
    }

    public static final Progressions reversed(Progressions reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        return Progressions.INSTANCE.fromClosedRange(reversed.getLast(), reversed.getFirst(), -reversed.getStep());
    }

    public static final Progressions2 step(Progressions2 step, int step2) {
        Intrinsics.checkParameterIsNotNull(step, "$this$step");
        RangesKt.checkStepIsPositive(step2 > 0, Integer.valueOf(step2));
        return Progressions2.INSTANCE.fromClosedRange(step.getFirst(), step.getLast(), step.getStep() > 0 ? step2 : -step2);
    }

    public static final Progressions3 step(Progressions3 step, long step2) {
        Intrinsics.checkParameterIsNotNull(step, "$this$step");
        RangesKt.checkStepIsPositive(step2 > 0, Long.valueOf(step2));
        return Progressions3.INSTANCE.fromClosedRange(step.getFirst(), step.getLast(), step.getStep() > 0 ? step2 : -step2);
    }

    public static final Progressions step(Progressions step, int step2) {
        Intrinsics.checkParameterIsNotNull(step, "$this$step");
        RangesKt.checkStepIsPositive(step2 > 0, Integer.valueOf(step2));
        return Progressions.INSTANCE.fromClosedRange(step.getFirst(), step.getLast(), step.getStep() > 0 ? step2 : -step2);
    }

    public static final Byte toByteExactOrNull(int $this$toByteExactOrNull) {
        if (-128 <= $this$toByteExactOrNull && 127 >= $this$toByteExactOrNull) {
            return Byte.valueOf((byte) $this$toByteExactOrNull);
        }
        return null;
    }

    public static final Byte toByteExactOrNull(long $this$toByteExactOrNull) {
        long j = 127;
        if (-128 <= $this$toByteExactOrNull && j >= $this$toByteExactOrNull) {
            return Byte.valueOf((byte) $this$toByteExactOrNull);
        }
        return null;
    }

    public static final Byte toByteExactOrNull(short $this$toByteExactOrNull) {
        short s = (short) 127;
        if (((short) (-128)) <= $this$toByteExactOrNull && s >= $this$toByteExactOrNull) {
            return Byte.valueOf((byte) $this$toByteExactOrNull);
        }
        return null;
    }

    public static final Byte toByteExactOrNull(double $this$toByteExactOrNull) {
        double d = 127;
        if ($this$toByteExactOrNull < -128 || $this$toByteExactOrNull > d) {
            return null;
        }
        return Byte.valueOf((byte) $this$toByteExactOrNull);
    }

    public static final Byte toByteExactOrNull(float $this$toByteExactOrNull) {
        float f = 127;
        if ($this$toByteExactOrNull < -128 || $this$toByteExactOrNull > f) {
            return null;
        }
        return Byte.valueOf((byte) $this$toByteExactOrNull);
    }

    public static final Integer toIntExactOrNull(long $this$toIntExactOrNull) {
        long j = Integer.MIN_VALUE;
        long j2 = IntCompanionObject.MAX_VALUE;
        if (j <= $this$toIntExactOrNull && j2 >= $this$toIntExactOrNull) {
            return Integer.valueOf((int) $this$toIntExactOrNull);
        }
        return null;
    }

    public static final Integer toIntExactOrNull(double $this$toIntExactOrNull) {
        double d = Integer.MIN_VALUE;
        double d2 = IntCompanionObject.MAX_VALUE;
        if ($this$toIntExactOrNull < d || $this$toIntExactOrNull > d2) {
            return null;
        }
        return Integer.valueOf((int) $this$toIntExactOrNull);
    }

    public static final Integer toIntExactOrNull(float $this$toIntExactOrNull) {
        float f = Integer.MIN_VALUE;
        float f2 = IntCompanionObject.MAX_VALUE;
        if ($this$toIntExactOrNull < f || $this$toIntExactOrNull > f2) {
            return null;
        }
        return Integer.valueOf((int) $this$toIntExactOrNull);
    }

    public static final Long toLongExactOrNull(double $this$toLongExactOrNull) {
        double d = Long.MIN_VALUE;
        double d2 = LongCompanionObject.MAX_VALUE;
        if ($this$toLongExactOrNull < d || $this$toLongExactOrNull > d2) {
            return null;
        }
        return Long.valueOf((long) $this$toLongExactOrNull);
    }

    public static final Long toLongExactOrNull(float $this$toLongExactOrNull) {
        float f = Long.MIN_VALUE;
        float f2 = LongCompanionObject.MAX_VALUE;
        if ($this$toLongExactOrNull < f || $this$toLongExactOrNull > f2) {
            return null;
        }
        return Long.valueOf((long) $this$toLongExactOrNull);
    }

    public static final Short toShortExactOrNull(int $this$toShortExactOrNull) {
        if (-32768 <= $this$toShortExactOrNull && 32767 >= $this$toShortExactOrNull) {
            return Short.valueOf((short) $this$toShortExactOrNull);
        }
        return null;
    }

    public static final Short toShortExactOrNull(long $this$toShortExactOrNull) {
        long j = 32767;
        if (-32768 <= $this$toShortExactOrNull && j >= $this$toShortExactOrNull) {
            return Short.valueOf((short) $this$toShortExactOrNull);
        }
        return null;
    }

    public static final Short toShortExactOrNull(double $this$toShortExactOrNull) {
        double d = 32767;
        if ($this$toShortExactOrNull < -32768 || $this$toShortExactOrNull > d) {
            return null;
        }
        return Short.valueOf((short) $this$toShortExactOrNull);
    }

    public static final Short toShortExactOrNull(float $this$toShortExactOrNull) {
        float f = 32767;
        if ($this$toShortExactOrNull < -32768 || $this$toShortExactOrNull > f) {
            return null;
        }
        return Short.valueOf((short) $this$toShortExactOrNull);
    }

    public static final Ranges5 until(int $this$until, byte to) {
        return new Ranges5($this$until, to - 1);
    }

    public static final Ranges6 until(long $this$until, byte to) {
        return new Ranges6($this$until, to - 1);
    }

    public static final Ranges5 until(byte $this$until, byte to) {
        return new Ranges5($this$until, to - 1);
    }

    public static final Ranges5 until(short $this$until, byte to) {
        return new Ranges5($this$until, to - 1);
    }

    public static final Ranges until(char $this$until, char to) {
        return to <= 0 ? Ranges.INSTANCE.getEMPTY() : new Ranges($this$until, (char) (to - 1));
    }

    public static final Ranges5 until(int $this$until, int to) {
        return to <= Integer.MIN_VALUE ? Ranges5.INSTANCE.getEMPTY() : new Ranges5($this$until, to - 1);
    }

    public static final Ranges6 until(long $this$until, int to) {
        return new Ranges6($this$until, to - 1);
    }

    public static final Ranges5 until(byte $this$until, int to) {
        return to <= Integer.MIN_VALUE ? Ranges5.INSTANCE.getEMPTY() : new Ranges5($this$until, to - 1);
    }

    public static final Ranges5 until(short $this$until, int to) {
        return to <= Integer.MIN_VALUE ? Ranges5.INSTANCE.getEMPTY() : new Ranges5($this$until, to - 1);
    }

    public static final Ranges6 until(int $this$until, long to) {
        return to <= Long.MIN_VALUE ? Ranges6.INSTANCE.getEMPTY() : new Ranges6($this$until, to - 1);
    }

    public static final Ranges6 until(long $this$until, long to) {
        return to <= Long.MIN_VALUE ? Ranges6.INSTANCE.getEMPTY() : new Ranges6($this$until, to - 1);
    }

    public static final Ranges6 until(byte $this$until, long to) {
        return to <= Long.MIN_VALUE ? Ranges6.INSTANCE.getEMPTY() : new Ranges6($this$until, to - 1);
    }

    public static final Ranges6 until(short $this$until, long to) {
        return to <= Long.MIN_VALUE ? Ranges6.INSTANCE.getEMPTY() : new Ranges6($this$until, to - 1);
    }

    public static final Ranges5 until(int $this$until, short to) {
        return new Ranges5($this$until, to - 1);
    }

    public static final Ranges6 until(long $this$until, short to) {
        return new Ranges6($this$until, to - 1);
    }

    public static final Ranges5 until(byte $this$until, short to) {
        return new Ranges5($this$until, to - 1);
    }

    public static final Ranges5 until(short $this$until, short to) {
        return new Ranges5($this$until, to - 1);
    }

    public static final <T extends Comparable<? super T>> T coerceAtLeast(T coerceAtLeast, T minimumValue) {
        Intrinsics.checkParameterIsNotNull(coerceAtLeast, "$this$coerceAtLeast");
        Intrinsics.checkParameterIsNotNull(minimumValue, "minimumValue");
        return coerceAtLeast.compareTo(minimumValue) < 0 ? minimumValue : coerceAtLeast;
    }

    public static final byte coerceAtLeast(byte $this$coerceAtLeast, byte minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final short coerceAtLeast(short $this$coerceAtLeast, short minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final int coerceAtLeast(int $this$coerceAtLeast, int minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final long coerceAtLeast(long $this$coerceAtLeast, long minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final float coerceAtLeast(float $this$coerceAtLeast, float minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final double coerceAtLeast(double $this$coerceAtLeast, double minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final <T extends Comparable<? super T>> T coerceAtMost(T coerceAtMost, T maximumValue) {
        Intrinsics.checkParameterIsNotNull(coerceAtMost, "$this$coerceAtMost");
        Intrinsics.checkParameterIsNotNull(maximumValue, "maximumValue");
        return coerceAtMost.compareTo(maximumValue) > 0 ? maximumValue : coerceAtMost;
    }

    public static final byte coerceAtMost(byte $this$coerceAtMost, byte maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final short coerceAtMost(short $this$coerceAtMost, short maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final int coerceAtMost(int $this$coerceAtMost, int maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final long coerceAtMost(long $this$coerceAtMost, long maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final float coerceAtMost(float $this$coerceAtMost, float maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final double coerceAtMost(double $this$coerceAtMost, double maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final <T extends Comparable<? super T>> T coerceIn(T coerceIn, T t, T t2) {
        Intrinsics.checkParameterIsNotNull(coerceIn, "$this$coerceIn");
        if (t != null && t2 != null) {
            if (t.compareTo(t2) > 0) {
                throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + t2 + " is less than minimum " + t + '.');
            }
            if (coerceIn.compareTo(t) < 0) {
                return t;
            }
            if (coerceIn.compareTo(t2) > 0) {
                return t2;
            }
        } else {
            if (t != null && coerceIn.compareTo(t) < 0) {
                return t;
            }
            if (t2 != null && coerceIn.compareTo(t2) > 0) {
                return t2;
            }
        }
        return coerceIn;
    }

    public static final byte coerceIn(byte $this$coerceIn, byte minimumValue, byte maximumValue) {
        if (minimumValue <= maximumValue) {
            return $this$coerceIn < minimumValue ? minimumValue : $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((int) maximumValue) + " is less than minimum " + ((int) minimumValue) + '.');
    }

    public static final short coerceIn(short $this$coerceIn, short minimumValue, short maximumValue) {
        if (minimumValue <= maximumValue) {
            return $this$coerceIn < minimumValue ? minimumValue : $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((int) maximumValue) + " is less than minimum " + ((int) minimumValue) + '.');
    }

    public static final int coerceIn(int $this$coerceIn, int minimumValue, int maximumValue) {
        if (minimumValue <= maximumValue) {
            return $this$coerceIn < minimumValue ? minimumValue : $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
    }

    public static final long coerceIn(long $this$coerceIn, long minimumValue, long maximumValue) {
        if (minimumValue <= maximumValue) {
            return $this$coerceIn < minimumValue ? minimumValue : $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
    }

    public static final float coerceIn(float $this$coerceIn, float minimumValue, float maximumValue) {
        if (minimumValue <= maximumValue) {
            return $this$coerceIn < minimumValue ? minimumValue : $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
    }

    public static final double coerceIn(double $this$coerceIn, double minimumValue, double maximumValue) {
        if (minimumValue <= maximumValue) {
            return $this$coerceIn < minimumValue ? minimumValue : $this$coerceIn > maximumValue ? maximumValue : $this$coerceIn;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
    }

    public static final <T extends Comparable<? super T>> T coerceIn(T coerceIn, Ranges3<T> range) {
        Intrinsics.checkParameterIsNotNull(coerceIn, "$this$coerceIn");
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return (!range.lessThanOrEquals(coerceIn, range.getStart()) || range.lessThanOrEquals(range.getStart(), coerceIn)) ? (!range.lessThanOrEquals(range.getEndInclusive(), coerceIn) || range.lessThanOrEquals(coerceIn, range.getEndInclusive())) ? coerceIn : range.getEndInclusive() : range.getStart();
    }

    public static final <T extends Comparable<? super T>> T coerceIn(T coerceIn, Range<T> range) {
        Intrinsics.checkParameterIsNotNull(coerceIn, "$this$coerceIn");
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof Ranges3) {
            return (T) RangesKt.coerceIn((Comparable) coerceIn, (Ranges3) range);
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return coerceIn.compareTo(range.getStart()) < 0 ? (T) range.getStart() : coerceIn.compareTo(range.getEndInclusive()) > 0 ? (T) range.getEndInclusive() : coerceIn;
    }

    public static final int coerceIn(int $this$coerceIn, Range<Integer> range) {
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof Ranges3) {
            return ((Number) RangesKt.coerceIn(Integer.valueOf($this$coerceIn), (Ranges3<Integer>) range)).intValue();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return $this$coerceIn < ((Number) range.getStart()).intValue() ? ((Number) range.getStart()).intValue() : $this$coerceIn > ((Number) range.getEndInclusive()).intValue() ? ((Number) range.getEndInclusive()).intValue() : $this$coerceIn;
    }

    public static final long coerceIn(long $this$coerceIn, Range<Long> range) {
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof Ranges3) {
            return ((Number) RangesKt.coerceIn(Long.valueOf($this$coerceIn), (Ranges3<Long>) range)).longValue();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return $this$coerceIn < ((Number) range.getStart()).longValue() ? ((Number) range.getStart()).longValue() : $this$coerceIn > ((Number) range.getEndInclusive()).longValue() ? ((Number) range.getEndInclusive()).longValue() : $this$coerceIn;
    }
}