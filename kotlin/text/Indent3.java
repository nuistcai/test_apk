package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: Indent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "line", "invoke"}, k = 3, mv = {1, 1, 15})
/* renamed from: kotlin.text.StringsKt__IndentKt$getIndentFunction$2 */
/* loaded from: classes.dex */
final class Indent3 extends Lambda implements Function1<String, String> {
    final /* synthetic */ String $indent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    Indent3(String str) {
        super(1);
        indent = str;
    }

    @Override // kotlin.jvm.functions.Function1
    public final String invoke(String line) {
        Intrinsics.checkParameterIsNotNull(line, "line");
        return indent + line;
    }
}