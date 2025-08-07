package cn.fly.commons.a;

import cn.fly.commons.ad;
import cn.fly.tools.FlyLog;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class a extends c {
    private static volatile long c;
    private static volatile HashMap<Long, Long> d;

    public a() {
        super(cn.fly.commons.o.a("002di"), 0L, cn.fly.commons.o.a("005di2ejIdj"), 900L, 0L);
        if (d == null) {
            c = System.currentTimeMillis();
            d = ad.b().g();
        }
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        if (d == null) {
            d = new HashMap<>();
        }
        for (Map.Entry<Long, Long> entry : d.entrySet()) {
            if (entry != null && entry.getKey().longValue() != c) {
                n();
            }
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - c;
        d.put(Long.valueOf(c), Long.valueOf(jCurrentTimeMillis));
        ad.b().a(d);
        long jB = ad.b().b(ad.f, 0L);
        long jM = m() * 1000;
        if (jCurrentTimeMillis >= jM && System.currentTimeMillis() - jB > jM) {
            n();
        }
    }

    @Override // cn.fly.commons.a.c
    protected void b() {
        long jLongValue = ((Long) a(e(), (String) 0L)).longValue();
        if (jLongValue > 0 && jLongValue < 604800) {
            a(jLongValue);
        }
    }

    private void n() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            for (Map.Entry<Long, Long> entry : d.entrySet()) {
                if (entry != null) {
                    map.put(cn.fly.commons.o.a("008gd:dg:echSfdAi"), entry.getKey());
                    map.put(cn.fly.commons.o.a("008$dcdgdj!di^didkRe"), entry.getValue());
                }
            }
            a("ARSTAMT", map);
            ad.b().a(ad.f, System.currentTimeMillis());
            if (d != null) {
                d.clear();
            }
            ad.b().a((HashMap<Long, Long>) null);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
    }
}