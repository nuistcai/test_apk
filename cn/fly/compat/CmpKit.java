package cn.fly.compat;

import android.content.Context;
import android.content.Intent;
import cn.fly.mcl.FlyMCL;
import cn.fly.mcl.b.a;
import cn.fly.mgs.a.g;
import cn.fly.tools.proguard.EverythingKeeper;

@Deprecated
/* loaded from: classes.dex */
public class CmpKit implements EverythingKeeper {
    public static void dealPulledIntent(Context context, Intent intent, boolean z) {
        g.a(context, intent, z);
    }

    public static void initMCLink(Context context, String str, String str2) {
        a.a(context, str, str2);
    }

    public static void addELPMessageListener(FlyMCL.ELPMessageListener eLPMessageListener) {
        a.a(eLPMessageListener);
    }
}