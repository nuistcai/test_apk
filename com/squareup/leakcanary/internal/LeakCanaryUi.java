package com.squareup.leakcanary.internal;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;

/* loaded from: classes.dex */
final class LeakCanaryUi {
    static final PorterDuffXfermode CLEAR_XFER_MODE = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    static final int LEAK_COLOR = -5155506;
    static final int LIGHT_GREY = -4539718;
    static final int ROOT_COLOR = -8083771;

    static float dpToPixel(float dp, Resources resources) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.density * dp;
    }

    private LeakCanaryUi() {
        throw new AssertionError();
    }
}