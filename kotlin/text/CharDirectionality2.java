package kotlin.text;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;

/* compiled from: CharDirectionality.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "", "Lkotlin/text/CharDirectionality;", "invoke"}, k = 3, mv = {1, 1, 15})
/* renamed from: kotlin.text.CharDirectionality$Companion$directionalityMap$2 */
/* loaded from: classes.dex */
final class CharDirectionality2 extends Lambda implements Function0<Map<Integer, ? extends CharDirectionality>> {
    public static final CharDirectionality2 INSTANCE = ;

    CharDirectionality2() {
    }

    @Override // kotlin.jvm.functions.Function0
    public final Map<Integer, ? extends CharDirectionality> invoke() {
        CharDirectionality[] charDirectionalityArrValues = CharDirectionality.values();
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(charDirectionalityArrValues.length), 16);
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        for (CharDirectionality charDirectionality : charDirectionalityArrValues) {
            destination$iv$iv.put(Integer.valueOf(charDirectionality.getValue()), charDirectionality);
        }
        return destination$iv$iv;
    }
}