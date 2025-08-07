package cn.fly.commons.cc;

import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class m implements s<m> {
    private l a;

    private ConnectivityManager.NetworkCallback a() {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
        return new ConnectivityManager.NetworkCallback() { // from class: cn.fly.commons.cc.m.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                super.onAvailable(network);
                ArrayList<Object> arrayList = new ArrayList<>();
                arrayList.add(network);
                m.this.a.a(cn.fly.commons.a.l.a("0119elLf4geeeEe8ej1heTggQhg"), arrayList);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                super.onLost(network);
                ArrayList<Object> arrayList = new ArrayList<>();
                arrayList.add(network);
                m.this.a.a(cn.fly.commons.a.l.a("006Kel^fMgfelgjCj"), arrayList);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onUnavailable() {
                super.onUnavailable();
            }
        };
    }

    public void a(l lVar) {
        this.a = lVar;
    }

    @Override // cn.fly.commons.cc.s
    public boolean a(m mVar, Class<m> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if ("setHandler".equals(str) && objArr.length == 1 && objArr[0] != null && (objArr[0] instanceof l)) {
            mVar.a((l) objArr[0]);
        } else {
            if (!cn.fly.commons.a.l.a("019Rej.f^ej+jQfhMgj]ghelekfifeFehhYgg<edQfi").equals(str) || objArr.length != 0) {
                return false;
            }
            objArr2[0] = mVar.a();
        }
        return true;
    }
}