package okhttp3.internal.platform.android;

import android.util.Log;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: util.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\u001a\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"MAX_LOG_LENGTH", "", "androidLog", "", "level", "message", "", "t", "", "okhttp"}, k = 2, mv = {1, 1, 15})
/* renamed from: okhttp3.internal.platform.android.UtilKt, reason: use source file name */
/* loaded from: classes.dex */
public final class util {
    private static final int MAX_LOG_LENGTH = 4000;

    public static final void androidLog(int level, String message, Throwable t) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        String logMessage = message;
        int logLevel = level != 5 ? 3 : 5;
        if (t != null) {
            logMessage = logMessage + "\n" + Log.getStackTraceString(t);
        }
        int i = 0;
        int length = logMessage.length();
        while (i < length) {
            int newline = StringsKt.indexOf$default((CharSequence) logMessage, '\n', i, false, 4, (Object) null);
            int newline2 = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline2, i + MAX_LOG_LENGTH);
                if (logMessage == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring = logMessage.substring(i, end);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                Log.println(logLevel, "OkHttp", strSubstring);
                i = end;
            } while (i < newline2);
            i++;
        }
    }
}