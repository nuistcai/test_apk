package cn.fly.mcl;

import android.os.Bundle;
import cn.fly.mcl.b.b;
import cn.fly.tools.network.HttpConnection;
import cn.fly.tools.network.HttpResponseCallback;
import cn.fly.tools.utils.HashonHelper;

/* loaded from: classes.dex */
class Tmpc$2 implements HttpResponseCallback {
    Tmpc$2() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.fly.tools.network.HttpResponseCallback
    public void onResponse(HttpConnection httpConnection) throws Throwable {
        if (httpConnection instanceof b) {
            Bundle bundle = new Bundle();
            String str = cn.fly.mcl.a.a.a;
            new HashonHelper();
            bundle.putString(str, HashonHelper.fromHashMap(((b) httpConnection).a()));
            aVar.e = bundle;
        }
    }
}