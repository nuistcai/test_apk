package cn.fly.commons.c;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import cn.fly.commons.c.h;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;

/* loaded from: classes.dex */
public class k extends h {
    public k(Context context) {
        super(context);
    }

    @Override // cn.fly.commons.c.h
    protected h.b b() {
        h.b bVar = new h.b();
        bVar.a = a(cn.fly.commons.n.a("007;chVdgGefdbccdj"), (String) null);
        return bVar;
    }

    private String a(String str, String str2) {
        Bundle bundleB = b(str, str2);
        if (a(bundleB)) {
            return bundleB.getString(cn.fly.commons.n.a("002'bgba"));
        }
        if (bundleB != null) {
            return bundleB.getString(cn.fly.commons.n.a("007?bd:dTdgdg$b=ch8d"));
        }
        return null;
    }

    private Bundle b(String str, String str2) {
        try {
            Uri uri = Uri.parse(cn.fly.commons.n.a("036a(bi^cgdcgijjacZbjLcGbeddbg7b0bjbgba2dcgEbg4g^caUjEbgbaPdcgHbgKgEca"));
            int oSVersionIntForFly = DH.SyncMtd.getOSVersionIntForFly();
            if (oSVersionIntForFly >= 17) {
                ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = this.a.getContentResolver().acquireUnstableContentProviderClient(uri);
                Bundle bundleCall = contentProviderClientAcquireUnstableContentProviderClient.call(str, str2, null);
                if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                    if (oSVersionIntForFly >= 24) {
                        contentProviderClientAcquireUnstableContentProviderClient.release();
                    } else {
                        contentProviderClientAcquireUnstableContentProviderClient.release();
                    }
                }
                return bundleCall;
            }
            if (oSVersionIntForFly >= 11) {
                return this.a.getContentResolver().call(uri, str, str2, (Bundle) null);
            }
            return null;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    private boolean a(Bundle bundle) {
        return bundle != null && bundle.getInt(cn.fly.commons.n.a("004a+bibaHd"), -1) == 0;
    }
}