package kotlin.random;

import kotlin.Metadata;
import kotlin.internal.PlatformImplementations2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PlatformRandom.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010\u0000\u001a\u00020\u0001H\u0081\b\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0000\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0007\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0007¨\u0006\n"}, d2 = {"defaultPlatformRandom", "Lkotlin/random/Random;", "doubleFromParts", "", "hi26", "", "low27", "asJavaRandom", "Ljava/util/Random;", "asKotlinRandom", "kotlin-stdlib"}, k = 2, mv = {1, 1, 15})
/* renamed from: kotlin.random.PlatformRandomKt, reason: use source file name */
/* loaded from: classes.dex */
public final class PlatformRandom6 {
    public static final java.util.Random asJavaRandom(Random asJavaRandom) {
        java.util.Random impl;
        Intrinsics.checkParameterIsNotNull(asJavaRandom, "$this$asJavaRandom");
        PlatformRandom2 platformRandom2 = (PlatformRandom2) (!(asJavaRandom instanceof PlatformRandom2) ? null : asJavaRandom);
        return (platformRandom2 == null || (impl = platformRandom2.getImpl()) == null) ? new PlatformRandom5(asJavaRandom) : impl;
    }

    public static final Random asKotlinRandom(java.util.Random asKotlinRandom) {
        Random impl;
        Intrinsics.checkParameterIsNotNull(asKotlinRandom, "$this$asKotlinRandom");
        PlatformRandom5 platformRandom5 = (PlatformRandom5) (!(asKotlinRandom instanceof PlatformRandom5) ? null : asKotlinRandom);
        return (platformRandom5 == null || (impl = platformRandom5.getImpl()) == null) ? new PlatformRandom(asKotlinRandom) : impl;
    }

    private static final Random defaultPlatformRandom() {
        return PlatformImplementations2.IMPLEMENTATIONS.defaultPlatformRandom();
    }

    public static final double doubleFromParts(int hi26, int low27) {
        double d = (hi26 << 27) + low27;
        double d2 = 9007199254740992L;
        Double.isNaN(d);
        Double.isNaN(d2);
        return d / d2;
    }
}