package cn.fly.commons.c;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import cn.fly.commons.c.h;

/* loaded from: classes.dex */
public class j extends h {
    public j(Context context) {
        super(context);
    }

    @Override // cn.fly.commons.c.h
    protected long c() {
        return 3000L;
    }

    @Override // cn.fly.commons.c.h
    protected Intent a() {
        Intent intent = new Intent();
        intent.setClassName(cn.fly.commons.m.a("023e!fmfhfniffifkfnfe5hWfffk]ehVfkfehk5h3flfffkHeh"), cn.fly.commons.m.a("039e$fmfhfniffifkfnfe6h?fffkOehOfkfehk$h;flfffk8ehSfnhnShQfffk eh5fkfegnIh_flfffk eh"));
        return intent;
    }

    @Override // cn.fly.commons.c.h
    public h.b a(IBinder iBinder) {
        String strA = cn.fly.commons.m.a("042e8fmfhfniffifkfnfeNhOfffk*eh0fkfehk^hYflfffkQeh.fngghn>hWfffkTeh9fkfegg_gkh9flgh:feh");
        h.b bVar = new h.b();
        bVar.a = a(cn.fly.commons.m.a("004!fmBf]fkfe"), iBinder, strA, 1, new String[0]);
        return bVar;
    }
}