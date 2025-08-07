package cn.fly.commons.cc;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class o implements ServiceConnection, s<o> {
    private l a;

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (this.a != null) {
            try {
                ArrayList<Object> arrayList = new ArrayList<>();
                arrayList.add(componentName);
                arrayList.add(iBinder);
                this.a.a("onServiceConnected", arrayList);
            } catch (Throwable th) {
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        if (this.a != null) {
            ArrayList<Object> arrayList = new ArrayList<>();
            arrayList.add(componentName);
            this.a.a("onServiceDisconnected", arrayList);
        }
    }

    public void a(l lVar) {
        this.a = lVar;
    }

    @Override // cn.fly.commons.cc.s
    public boolean a(o oVar, Class<o> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if (!"setHandler".equals(str) || objArr.length != 1) {
            return false;
        }
        oVar.a((l) objArr[0]);
        return true;
    }
}