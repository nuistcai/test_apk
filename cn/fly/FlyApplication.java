package cn.fly;

import android.app.Application;
import cn.fly.tools.proguard.ProtectedMemberKeeper;

/* loaded from: classes.dex */
public class FlyApplication extends Application implements ProtectedMemberKeeper {
    protected String getAppkey() {
        return null;
    }

    protected String getAppSecret() {
        return null;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        FlySDK.init(this, getAppkey(), getAppSecret());
    }
}