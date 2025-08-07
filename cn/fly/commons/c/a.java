package cn.fly.commons.c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import cn.fly.commons.c.h;
import cn.fly.commons.w;

/* loaded from: classes.dex */
public class a extends h {
    public a(Context context) {
        super(context);
    }

    @Override // cn.fly.commons.c.h
    protected Intent a() {
        Intent intent = new Intent(w.b("030b9cjceckQc3ehcfehckceehCc2ck$cbhYchcj^dWckecdcdcfhdkdkcgekddek"));
        intent.setComponent(new ComponentName(w.b("029b9cjceckZc;ehcfehckceehXc$ckdkcf)iifeOceUedhc cidbekddek"), w.b("053b=cjceck<c2ehcfehckceehCcSckdkcf-iife7ceQedhc<cidbekddekckdkcf9iifeIce+edhc4cidbekddekdk@e?ciccchMbe")));
        return intent;
    }

    @Override // cn.fly.commons.c.h
    public h.b a(IBinder iBinder) {
        h.b bVar = new h.b();
        bVar.a = a(w.b("0044cjPc9chcb"), iBinder, w.b("047b]cjceck8c=ehcfehckceehBcEckdkcf%iifeUceBedhcMcidbekddekckddekchcbecchcb*f@dd[dhe:cide1cbe"), 3, new String[0]);
        return bVar;
    }
}