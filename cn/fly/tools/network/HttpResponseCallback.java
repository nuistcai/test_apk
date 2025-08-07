package cn.fly.tools.network;

import cn.fly.tools.proguard.PublicMemberKeeper;

/* loaded from: classes.dex */
public interface HttpResponseCallback extends PublicMemberKeeper {
    void onResponse(HttpConnection httpConnection) throws Throwable;
}