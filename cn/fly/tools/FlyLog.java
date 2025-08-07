package cn.fly.tools;

import cn.fly.FlySDK;
import cn.fly.tools.log.NLog;
import cn.fly.tools.proguard.EverythingKeeper;

/* loaded from: classes.dex */
public class FlyLog implements EverythingKeeper {
    public static synchronized NLog getInstance() {
        return NLog.getInstance("FlySDK", FlySDK.SDK_VERSION_CODE, "fly.tools");
    }
}