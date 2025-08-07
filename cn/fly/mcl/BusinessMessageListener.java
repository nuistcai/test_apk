package cn.fly.mcl;

import cn.fly.tools.proguard.EverythingKeeper;

/* loaded from: classes.dex */
public interface BusinessMessageListener extends EverythingKeeper {
    void messageReceived(int i, String str, String str2);
}