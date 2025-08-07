package cn.fly.commons.c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.IBinder;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.commons.c.h;
import cn.fly.tools.utils.DH;
import java.security.MessageDigest;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import kotlin.UByte;

/* loaded from: classes.dex */
public class m extends h {
    protected String c;
    private String d;

    public m(Context context) {
        super(context);
        this.c = cn.fly.commons.a.l.a("025dBelegem*igUfdXjek4emel3kgfCejedemffhiIkgf6ffgm");
    }

    @Override // cn.fly.commons.c.h
    protected Intent a() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(cn.fly.commons.a.l.a("017d.elegem6igUfdSjek-emelXkgf0ejed"), cn.fly.commons.a.l.a("033dMelegemXig+fd$jek:emelWkgfMejedemffed+gfj:ejfgfdfmYgSekeeejPdg")));
        intent.setAction(cn.fly.commons.a.l.a("040edjXejelZfOemUd?elegem<ig%fd!jek+emel]kgf4ejedemhihmhjfheiffgmeifmhjhkhlfffehj"));
        return intent;
    }

    @Override // cn.fly.commons.c.h
    public h.b a(IBinder iBinder) {
        h.b bVar = new h.b();
        bVar.a = a(iBinder, cn.fly.commons.a.l.a("004?hiflffgm"));
        return bVar;
    }

    private final String a(IBinder iBinder, String str) {
        Signature[] signatureArrB;
        if (TextUtils.isEmpty(this.d)) {
            try {
                final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
                DH.requester(FlySDK.getContext()).getMpfo(this.b, 64).request(new DH.DHResponder() { // from class: cn.fly.commons.c.m.1
                    @Override // cn.fly.tools.utils.DH.DHResponder
                    public void onResponse(DH.DHResponse dHResponse) {
                        if (dHResponse.getMpfo(new int[0]) != null) {
                            linkedBlockingQueue.offer(dHResponse.getMpfo(new int[0]));
                        } else {
                            linkedBlockingQueue.offer(Boolean.FALSE);
                        }
                    }
                });
                Object objPoll = linkedBlockingQueue.poll(300L, TimeUnit.MILLISECONDS);
                if (objPoll instanceof Boolean) {
                    signatureArrB = null;
                } else {
                    signatureArrB = cn.fly.tools.c.b(objPoll, this.b);
                }
                if (signatureArrB != null && signatureArrB.length > 0) {
                    byte[] byteArray = signatureArrB[0].toByteArray();
                    MessageDigest messageDigest = MessageDigest.getInstance(cn.fly.commons.a.l.a("0043fmglgeig"));
                    if (messageDigest != null) {
                        byte[] bArrDigest = messageDigest.digest(byteArray);
                        StringBuilder sb = new StringBuilder();
                        for (byte b : bArrDigest) {
                            sb.append(Integer.toHexString((b & UByte.MAX_VALUE) | 256).substring(1, 3));
                        }
                        this.d = sb.toString();
                    }
                }
            } catch (Throwable th) {
            }
        }
        return a(str, iBinder, this.c, 1, this.b, this.d, str);
    }
}