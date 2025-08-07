package cn.fly.commons.c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import cn.fly.commons.c.h;
import cn.fly.commons.w;

/* loaded from: classes.dex */
public class b extends h {
    public b(Context context) {
        super(context);
    }

    @Override // cn.fly.commons.c.h
    protected Intent a() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(w.b("027b%cjceckYbJcjcjSficQcbckcb,e[ccchVbeHchcbehcf7ii:cjci-h"), w.b("043b<cjceck,b6cjcj6fic7cbckcbRe?ccch.beAchcbehcfIii@cjci0hYckek6eUccch5beLddcbdk0eAciccch2be")));
        return intent;
    }

    @Override // cn.fly.commons.c.h
    public h.b a(IBinder iBinder) {
        h.b bVar = new h.b();
        bVar.a = a(w.b("004YcjCcQchcb"), iBinder, w.b("044bMcjceck7b^cjcj=fic%cbckcbReEccch%be[chcbehcf iiScjci;h*ckddek'eLccchBbe:ddcbgbYcdc?di_eUci"), 2, this.b);
        return bVar;
    }
}