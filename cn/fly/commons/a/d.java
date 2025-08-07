package cn.fly.commons.a;

import cn.fly.commons.aa;
import cn.fly.commons.ab;
import cn.fly.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class d implements Runnable {
    private static volatile d a = null;
    private final AtomicBoolean b = new AtomicBoolean(false);
    private final ArrayList<c> c = new ArrayList<>();

    private d() {
    }

    public static d a() {
        if (a == null) {
            synchronized (aa.class) {
                if (a == null) {
                    a = new d();
                }
            }
        }
        return a;
    }

    public void b() {
        if (this.b.compareAndSet(false, true)) {
            a(new a());
            a(new b());
            a(new e());
            a(new f());
            a(new k());
            g gVar = new g();
            gVar.a(true);
            a(gVar);
            a(new h());
            a(new j());
            a(new i());
            a(new m());
            a(new n());
            a(new o());
            ab.a.execute(this);
        }
    }

    public <T extends c> void a(T t) {
        this.c.add(t);
    }

    public void a(c cVar, long j, int i) {
        l.a().a(j, (long) cVar, i);
    }

    public void b(c cVar, long j, int i) {
        l.a().a(j, cVar, i, false);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.c.size() > 0) {
            if (cn.fly.commons.c.d() && cn.fly.commons.e.j()) {
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    Iterator<c> it = this.c.iterator();
                    while (it.hasNext()) {
                        c next = it.next();
                        if (next.h() || (!next.c() && jCurrentTimeMillis >= next.k())) {
                            next.i();
                        }
                    }
                } catch (Throwable th) {
                }
                l.a().b(c(), this);
                return;
            }
            l.a().b(60000L, this);
            return;
        }
        l.a().b(c(), this);
    }

    private long c() {
        return ((Integer) ResHelper.forceCast(cn.fly.commons.c.a(cn.fly.commons.o.a("003XdjQji"), 300), 300)).intValue() * 1000;
    }
}