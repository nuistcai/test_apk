package cn.fly.apc.a;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import cn.fly.FlyACService;
import cn.fly.commons.a.l;

/* loaded from: classes.dex */
public class a {
    private FlyACService a;
    private volatile boolean b = false;
    private final d c = new d() { // from class: cn.fly.apc.a.a.1
        @Override // cn.fly.apc.a.d
        public e a(e eVar) throws RemoteException {
            cn.fly.apc.a aVar;
            f.a().b("APC msg received. msg: " + eVar, new Object[0]);
            if (a.this.b) {
                f.a().b("inited: " + cn.fly.apc.b.a, new Object[0]);
                if (!cn.fly.apc.b.a) {
                    a.this.b = false;
                    if (eVar != null && (aVar = eVar.a) != null) {
                        Bundle bundle = new Bundle();
                        switch (aVar.a) {
                            case 1001:
                                bundle.putInt("acsActType", 1);
                                break;
                            case 9004:
                                bundle.putInt("acsActType", 2);
                                break;
                        }
                        bundle.putString(l.a("003k=fifk"), eVar.c);
                        c.a().a(bundle);
                    }
                }
            }
            return c.a().a(eVar);
        }
    };

    public a(FlyACService flyACService) {
        this.a = flyACService;
    }

    public void a() {
        try {
            this.b = true;
            cn.fly.apc.b.a(this.a.getApplicationContext());
        } catch (Throwable th) {
            f.a().a(th);
        }
    }

    public int a(Intent intent, int i, int i2) {
        return this.a.a(intent, i, i2);
    }

    public IBinder a(Intent intent) {
        return this.c;
    }

    public void b() {
        this.b = false;
    }

    public boolean b(Intent intent) {
        return this.a.a(intent);
    }
}