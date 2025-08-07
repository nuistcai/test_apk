package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KTypeProjection;

/* compiled from: TypeReference.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Lkotlin/reflect/KTypeProjection;", "invoke"}, k = 3, mv = {1, 1, 15})
/* renamed from: kotlin.jvm.internal.TypeReference$asString$args$1 */
/* loaded from: classes.dex */
final class TypeReference2 extends Lambda implements Function1<KTypeProjection, String> {
    TypeReference2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final String invoke(KTypeProjection it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        return this.this$0.asString(it);
    }
}