package cn.fly.mcl;

import cn.fly.tools.proguard.EverythingKeeper;

/* loaded from: classes.dex */
public interface BusinessCallBack<T> extends EverythingKeeper {
    void callback(T t);
}