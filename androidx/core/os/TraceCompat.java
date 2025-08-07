package androidx.core.os;

import android.os.Trace;

/* loaded from: classes.dex */
public final class TraceCompat {
    public static void beginSection(String sectionName) {
        Trace.beginSection(sectionName);
    }

    public static void endSection() {
        Trace.endSection();
    }

    private TraceCompat() {
    }
}