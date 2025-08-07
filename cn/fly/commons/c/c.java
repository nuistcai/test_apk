package cn.fly.commons.c;

import android.content.Context;
import cn.fly.commons.c.h;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;

/* loaded from: classes.dex */
public class c extends h {
    public c(Context context) {
        super(context);
    }

    @Override // cn.fly.commons.c.h
    protected h.b b() {
        Object objInvokeInstanceMethodNoThrow;
        h.b bVar = new h.b();
        Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(cn.fly.commons.n.a("008McfMd@cachbe3bObhba"));
        if (systemServiceSafe != null && (objInvokeInstanceMethodNoThrow = ReflectHelper.invokeInstanceMethodNoThrow(systemServiceSafe, cn.fly.commons.n.a("010?biddHgb:bg6c;ef8bEbgba"), null, new Object[0])) != null) {
            bVar.a = objInvokeInstanceMethodNoThrow.toString();
        }
        return bVar;
    }
}