package kotlin.text;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;

/* compiled from: CharCategory.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "", "Lkotlin/text/CharCategory;", "invoke"}, k = 3, mv = {1, 1, 15})
/* renamed from: kotlin.text.CharCategory$Companion$categoryMap$2 */
/* loaded from: classes.dex */
final class CharCategory2 extends Lambda implements Function0<Map<Integer, ? extends CharCategory>> {
    public static final CharCategory2 INSTANCE = ;

    CharCategory2() {
    }

    @Override // kotlin.jvm.functions.Function0
    public final Map<Integer, ? extends CharCategory> invoke() {
        CharCategory[] charCategoryArrValues = CharCategory.values();
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(charCategoryArrValues.length), 16);
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        for (CharCategory charCategory : charCategoryArrValues) {
            destination$iv$iv.put(Integer.valueOf(charCategory.getValue()), charCategory);
        }
        return destination$iv$iv;
    }
}