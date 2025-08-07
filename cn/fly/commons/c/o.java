package cn.fly.commons.c;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import cn.fly.commons.c.h;

/* loaded from: classes.dex */
public class o extends h {
    public o(Context context) {
        super(context);
    }

    @Override // cn.fly.commons.c.h
    protected Intent a() {
        Intent intent = new Intent();
        intent.setClassName(cn.fly.commons.n.a("035aMbibdbjdg-b0bddgbe8cYchbj5bc@babhbibgbabjbaDdTbbbg0ad*bgbadg%d?bhbbbgBad"), cn.fly.commons.n.a("051a5bibdbjdgUbKbddgbeOc8chbj(bcUbabhbibgbabjba-dObbbg2ad,bgbadgRd;bhbbbgFadAbjdjYdBbbbgRadGccbacj$d;bhbbbg8ad"));
        return intent;
    }

    @Override // cn.fly.commons.c.h
    protected h.b a(IBinder iBinder) {
        h.b bVar = new h.b();
        bVar.a = a(cn.fly.commons.n.a("004Dbi^b-bgba"), iBinder, cn.fly.commons.n.a("052a<bibdbjdgZb[bddgbe!c2chbj$bc(babhbibgbabjba d1bbbgGadDbgbadg7dDbhbbbg8adZbjccdj_d'bbbgMad<ccbacjUdYbhbbbg8ad"), 1, new String[0]);
        return bVar;
    }
}