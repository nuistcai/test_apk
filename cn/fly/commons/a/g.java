package cn.fly.commons.a;

import cn.fly.commons.ad;
import cn.fly.commons.w;
import cn.fly.tools.FlyLog;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class g extends c {
    private static cn.fly.commons.l c;
    private static final String d = w.b("014.dgXeKdbcg'cbh?chccReVcgJfRcjdi");

    public g() {
        super(w.b("002Jeech"), 0L, w.b("005EeechdiSci"), 30L, 0L);
    }

    @Override // cn.fly.commons.a.c
    protected void d() {
        o();
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        long jLongValue;
        if (!h()) {
            Long[] lArr = (Long[]) this.b;
            long jLongValue2 = lArr[0].longValue();
            long jLongValue3 = lArr[1].longValue();
            if (jLongValue2 == 3 && lArr.length < 3) {
                jLongValue = System.currentTimeMillis();
            } else {
                jLongValue = lArr[2].longValue();
            }
            if (jLongValue2 == 0) {
                n();
                a(jLongValue3, jLongValue);
                b(jLongValue3);
            } else if (jLongValue2 != 1 && jLongValue2 != 3) {
                if (jLongValue2 == 2) {
                    a(jLongValue3, jLongValue);
                }
            } else {
                if (jLongValue2 == 1) {
                    n();
                }
                a(jLongValue3, jLongValue);
                b(jLongValue3);
            }
        }
    }

    private void b(long j) {
        if (!cn.fly.commons.m.a().b()) {
            a(new Long[]{3L, Long.valueOf(j)});
            d.a().b(this, m(), 0);
        }
    }

    private void a(long j, long j2) {
        try {
            HashMap map = (HashMap) ad.b().c(d, null);
            if (map == null) {
                map = new HashMap();
            }
            map.put(Long.valueOf(j), Long.valueOf(j2));
            ad.b().b(d, map);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
    }

    private void n() {
        try {
            HashMap map = (HashMap) ad.b().c(d, null);
            if (map != null && !map.isEmpty()) {
                for (Map.Entry entry : map.entrySet()) {
                    long jLongValue = ((Long) entry.getKey()).longValue();
                    long jLongValue2 = ((Long) entry.getValue()).longValue();
                    long j = jLongValue2 - jLongValue;
                    if (j > 0) {
                        HashMap<String, Object> map2 = new HashMap<>();
                        map2.put(w.b("0058cfOdh_ch f"), Long.valueOf(jLongValue2));
                        map2.put(w.b("008Kcicf^dh@chce8e7eh"), Long.valueOf(j));
                        a("BKIOMT", map2);
                    }
                }
                ad.b().b(d);
            }
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
    }

    private synchronized boolean o() {
        if (c != null) {
            return false;
        }
        c = new cn.fly.commons.l() { // from class: cn.fly.commons.a.g.1
            private volatile long b = 0;

            @Override // cn.fly.commons.l
            public void a(boolean z, boolean z2, long j) {
                if (z2) {
                    this.b = System.currentTimeMillis();
                    g.this.a(new Long[]{0L, Long.valueOf(this.b), Long.valueOf(this.b)});
                    d.a().b(g.this, 0L, 1);
                }
                if (!z) {
                    if (j > 0) {
                        g.this.a(new Long[]{2L, Long.valueOf(this.b), Long.valueOf(System.currentTimeMillis())});
                        d.a().b(g.this, 0L, 1);
                        return;
                    }
                    return;
                }
                if (!z2) {
                    this.b = System.currentTimeMillis();
                    g.this.a(new Long[]{1L, Long.valueOf(this.b), Long.valueOf(this.b)});
                    d.a().b(g.this, 0L, 0);
                }
            }
        };
        cn.fly.commons.m.a().a(c);
        return true;
    }
}