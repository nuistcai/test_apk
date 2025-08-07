package cn.fly.mgs.a;

import android.text.TextUtils;
import cn.fly.tools.network.NetCommunicator;
import java.util.HashMap;

/* loaded from: classes.dex */
public class f {
    private static volatile f a;
    private volatile String c;
    private volatile boolean e;
    private volatile String f;
    private volatile boolean b = false;
    private byte[] d = new byte[0];

    private f() {
    }

    public static f a() {
        if (a == null) {
            synchronized (f.class) {
                if (a == null) {
                    a = new f();
                }
            }
        }
        return a;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [cn.fly.mgs.a.f$1] */
    public void b() {
        if (!this.b) {
            new h() { // from class: cn.fly.mgs.a.f.1
                @Override // cn.fly.tools.utils.j
                protected void a() throws Throwable {
                    e.a().a("MgsGlobal init: start");
                    f.this.f();
                    f.this.b = true;
                    e.a().a("MgsGlobal init: done");
                }
            }.start();
        } else {
            e.a().a("MgsGlobal already initialized");
        }
    }

    public String c() {
        if (TextUtils.isEmpty(this.c)) {
            e.a().b("WARNING: getDuidQuick got null!");
        }
        return this.c;
    }

    public boolean d() {
        return this.e;
    }

    public String e() {
        return this.f;
    }

    public String f() {
        HashMap<String, Object> mapB;
        if (TextUtils.isEmpty(this.c)) {
            synchronized (this.d) {
                if (TextUtils.isEmpty(this.c) && (mapB = cn.fly.commons.f.b(null)) != null) {
                    this.c = (String) mapB.get(NetCommunicator.KEY_DUID);
                    this.e = ((Boolean) mapB.get(NetCommunicator.KEY_IS_MODIFIED)).booleanValue();
                    this.f = (String) mapB.get(NetCommunicator.KEY_DUID_PREVIOUS);
                    e.a().a("MC Global -> duid: " + this.c + ", duidPre: " + this.f + ", isModified: " + this.e);
                }
            }
        }
        return this.c;
    }
}