package cn.fly.mgs;

import android.content.Context;
import cn.fly.tools.proguard.EverythingKeeper;

/* loaded from: classes.dex */
public interface OnAppActiveListener extends EverythingKeeper {
    void onAppActive(Context context, int i);
}