package cn.fly.mcl;

import cn.fly.tools.proguard.EverythingKeeper;

/* loaded from: classes.dex */
public interface TcpStatusListener extends EverythingKeeper {
    void onStatus(TcpStatus tcpStatus);
}