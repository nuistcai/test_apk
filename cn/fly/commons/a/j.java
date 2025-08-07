package cn.fly.commons.a;

import java.util.HashMap;

/* loaded from: classes.dex */
public class j extends c {
    private static cn.fly.commons.l c;

    public j() {
        super("p", 0L, null, 0L, 0L);
        a(0);
    }

    @Override // cn.fly.commons.a.c
    protected void d() {
        n();
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        if (!h()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(cn.fly.commons.o.a("004i^ec.jf"), "PVMT");
            map.put(cn.fly.commons.o.a("008+dc$difiUdidf-f"), this.b);
            if (!cn.fly.commons.k.a().a.get()) {
                map.putAll(cn.fly.commons.k.a().c());
                cn.fly.commons.k.a().a.compareAndSet(false, true);
            }
            cn.fly.commons.d.a().a(System.currentTimeMillis(), map);
        }
    }

    private synchronized boolean n() {
        if (c != null) {
            return false;
        }
        c = new cn.fly.commons.l() { // from class: cn.fly.commons.a.j.1
            @Override // cn.fly.commons.l
            public void a(boolean z, boolean z2, long j) {
                if (z) {
                    j.this.a(Long.valueOf(System.currentTimeMillis()));
                    d.a().a(j.this, 0L, 0);
                }
            }
        };
        cn.fly.commons.m.a().a(c);
        return true;
    }
}