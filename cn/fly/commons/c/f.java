package cn.fly.commons.c;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.text.TextUtils;
import cn.fly.commons.c.h;
import cn.fly.commons.s;
import cn.fly.tools.FlyLog;
import cn.fly.tools.log.NLog;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class f extends h {
    public f(Context context) {
        super(context);
    }

    @Override // cn.fly.commons.c.h
    protected Intent a() {
        Intent intent = new Intent();
        intent.setAction(cn.fly.commons.o.a("028c_dkdfdl?hHdi[hAdk]e8dkdjdldidcdlfk3e$ghBdQeedcel4f+djdddiGcf"));
        intent.setPackage(cn.fly.commons.o.a("014cPdkdfdl)h2diYh)dkCe[dkdjdldidc"));
        return intent;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [long] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.Object[]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x004b -> B:11:0x004d). Please report as a decompilation issue!!! */
    @Override // cn.fly.commons.c.h
    protected h.b a(IBinder iBinder) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        s sVar = new s();
        sVar.a(countDownLatch);
        long jCurrentTimeMillis = System.currentTimeMillis();
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            try {
                parcelObtain.writeInterfaceToken(cn.fly.commons.o.a("042c3dkdfdl4h5diOhEdkQe dkdjdl4cg1dkdgdcfi>fUdjdddi.cf;dldk%d(didcdleeghfdeeflel_f<djdddi+cf"));
                parcelObtain.writeStrongBinder(sVar);
                iBinder.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                countDownLatch.await(2000L, TimeUnit.MILLISECONDS);
                parcelObtain.recycle();
                parcelObtain2.recycle();
            } catch (Throwable th) {
                try {
                    FlyLog.getInstance().d(th);
                    parcelObtain.recycle();
                    parcelObtain2.recycle();
                } catch (Throwable th2) {
                    try {
                        parcelObtain.recycle();
                        parcelObtain2.recycle();
                    } catch (Throwable th3) {
                    }
                    throw th2;
                }
            }
        } catch (Throwable th4) {
        }
        NLog flyLog = FlyLog.getInstance();
        String str = "hord is null ? " + TextUtils.isEmpty(sVar.a()) + " cost " + (System.currentTimeMillis() - jCurrentTimeMillis);
        jCurrentTimeMillis = new Object[0];
        flyLog.d(str, jCurrentTimeMillis);
        if (TextUtils.isEmpty(sVar.a())) {
            return null;
        }
        h.b bVar = new h.b();
        bVar.a = sVar.a();
        return bVar;
    }
}