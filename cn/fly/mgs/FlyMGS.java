package cn.fly.mgs;

import cn.fly.mgs.a.b;
import cn.fly.mgs.a.g;
import cn.fly.tools.proguard.EverythingKeeper;

/* loaded from: classes.dex */
public class FlyMGS implements EverythingKeeper {
    public static final String MGS_TAG = "MGS";

    public static void setOnAppActiveListener(OnAppActiveListener onAppActiveListener) {
        if (onAppActiveListener != null) {
            g.a(onAppActiveListener);
        }
    }

    public static void setDS(boolean z) {
        b.a(z);
    }

    public static boolean getDS() {
        return b.b();
    }
}