package cn.fly.tools.network;

import cn.fly.tools.proguard.PublicMemberKeeper;
import java.io.IOException;

/* loaded from: classes.dex */
public interface OnReadListener extends PublicMemberKeeper {
    void onRead(long j) throws IOException;
}