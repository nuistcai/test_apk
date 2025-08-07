package cn.fly.commons.c;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import cn.fly.commons.c.h;
import cn.fly.tools.FlyLog;

/* loaded from: classes.dex */
public class r extends h {
    public r(Context context) {
        super(context);
    }

    @Override // cn.fly.commons.c.h
    protected Intent a() {
        e();
        Intent intent = new Intent();
        intent.setClassName(cn.fly.commons.o.a("012cJdkdfdldfdcdidcdldffiSd"), cn.fly.commons.o.a("033cTdkdfdldfdcdidcdldffiNd'dlfiKfAdjdddi[cf8dlhcfi!d.eedcel:f2djdddi]cf"));
        intent.setAction(cn.fly.commons.o.a("033c[dkdfdlffdgWeHdldffi2d4dlYdci;didkTe6dlffdi1e,dc[i.dkdlfi9fGdjdddi5cf"));
        intent.putExtra(cn.fly.commons.o.a("025cPdkdfdlffdg:e!dldffi1dTdl*jdCdj=d-dfdl*j.ehejMedBdf8f"), this.b);
        return intent;
    }

    @Override // cn.fly.commons.c.h
    protected h.b a(IBinder iBinder) {
        h.b bVar = new h.b();
        bVar.a = a(cn.fly.commons.o.a("004ZdkZdGdidc"), iBinder, cn.fly.commons.o.a("026cQdkdfdlffdgCe>dl7g[diffdlhcfi4d=eedcee9eif5djefZdcf"), 3, new String[0]);
        return bVar;
    }

    private void e() {
        try {
            Intent intent = new Intent();
            intent.setClassName(cn.fly.commons.o.a("012c0dkdfdldfdcdidcdldffi*d"), cn.fly.commons.o.a("033c;dkdfdldfdcdidcdldffiWdHdlfiAf2djdddi+cf!dlhcfi@d]ic0g,elEf;djdddiVcf"));
            intent.setAction(cn.fly.commons.o.a("032cBdkdfdlffdg!e6dldffi]dKdl=dciRdidk<e)dlfiOidXdj)iMdlfiGf%djdddi cf"));
            intent.putExtra(cn.fly.commons.o.a("025c,dkdfdlffdg$e?dldffiZdAdlUjdCdjDd<dfdl*jNehejFed;dfJf"), this.b);
            intent.putExtra(cn.fly.commons.o.a("026c dkdfdlffdgHe0dldffi7dVdl1jdIdjZd0dfdldjdgJe%di<eOfiPfi"), true);
            this.a.startService(intent);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }
}