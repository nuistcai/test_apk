package cn.fly.tools.network;

import cn.fly.tools.proguard.PublicMemberKeeper;
import java.io.InputStream;

/* loaded from: classes.dex */
public interface RawNetworkCallback extends PublicMemberKeeper {
    void onResponse(InputStream inputStream) throws Throwable;
}