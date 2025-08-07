package com.cmic.gen.sdk.e;

import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.auth.GenTokenListener;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: OverTimeUtils.java */
/* renamed from: com.cmic.gen.sdk.e.f, reason: use source file name */
/* loaded from: classes.dex */
public class OverTimeUtils {
    private static ConcurrentHashMap<String, GenTokenListener> a = new ConcurrentHashMap<>(16);
    private static ConcurrentHashMap<String, ConcurrentBundle> b = new ConcurrentHashMap<>();

    public static boolean a(String str) {
        return !a.containsKey(str);
    }

    public static void a(String str, GenTokenListener genTokenListener) {
        a.put(str, genTokenListener);
    }

    public static void b(String str) {
        a.remove(str);
    }

    public static GenTokenListener c(String str) {
        return a.get(str);
    }

    public static ConcurrentBundle d(String str) {
        if (str != null) {
            return b.get(str);
        }
        return new ConcurrentBundle(0);
    }

    public static void a(String str, ConcurrentBundle concurrentBundle) {
        if (str != null && concurrentBundle != null) {
            b.put(str, concurrentBundle);
        }
    }
}