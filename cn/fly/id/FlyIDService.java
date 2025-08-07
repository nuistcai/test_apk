package cn.fly.id;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import cn.fly.FlySDK;
import cn.fly.mgs.a.e;
import cn.fly.mgs.a.g;
import cn.fly.tools.proguard.ClassKeeper;

/* loaded from: classes.dex */
public class FlyIDService extends Service implements ClassKeeper {
    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        try {
            FlySDK.init(getApplicationContext());
            e.a().a("FlyIDService onCreate");
        } catch (Throwable th) {
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        a(intent);
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            a(intent);
        }
        return super.onStartCommand(intent, i, i2);
    }

    private void a(Intent intent) {
        g.a(this, intent, false);
    }
}