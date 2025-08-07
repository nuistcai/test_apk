package cn.fly.commons.cc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class h extends BroadcastReceiver implements s<h> {
    private l a;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (this.a != null) {
            try {
                ArrayList<Object> arrayList = new ArrayList<>(1);
                arrayList.add(intent);
                this.a.a("onReceive", arrayList);
            } catch (Throwable th) {
            }
        }
    }

    public void a(l lVar) {
        this.a = lVar;
    }

    @Override // cn.fly.commons.cc.s
    public boolean a(h hVar, Class<h> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if (!"setHandler".equals(str) || objArr.length != 1 || objArr[0] == null || !(objArr[0] instanceof l)) {
            return false;
        }
        hVar.a((l) objArr[0]);
        return true;
    }
}