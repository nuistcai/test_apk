package cn.fly;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import cn.fly.apc.a.a;

/* loaded from: classes.dex */
public class FlyACService extends Service {
    private a a = new a(this);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.a.a();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return this.a.a(intent, i, i2);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.a.a(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.a.b();
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        return this.a.b(intent);
    }

    public boolean a(Intent intent) {
        return super.onUnbind(intent);
    }

    public int a(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }
}