package cn.fly.commons.a;

import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.ad;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/* loaded from: classes.dex */
public class h extends c {
    public h() {
        super(null, null, a(cn.fly.commons.o.a("003dgg"), (Long) 0L));
    }

    @Override // cn.fly.commons.a.c
    protected boolean f() {
        return n() && g();
    }

    private boolean n() {
        return cn.fly.commons.c.a(cn.fly.commons.o.a("003dgg"));
    }

    private boolean o() {
        return cn.fly.commons.c.a(cn.fly.commons.o.a("002CdiLe"));
    }

    private boolean p() {
        return cn.fly.commons.c.a(cn.fly.commons.o.a("002%dgKe"));
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        boolean z;
        if (n()) {
            if (!o()) {
                p();
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jLongValue = ((Long) a(cn.fly.commons.o.a("004d!fiLjd"), (String) 2592000L)).longValue() * 1000;
            long jB = ad.b().b(ad.c, 0L);
            boolean zA = C0041r.a(jCurrentTimeMillis, jB);
            final boolean z2 = true;
            if (this.b != null && (this.b instanceof Boolean) && ((Boolean) this.b).booleanValue()) {
                z = true;
            } else {
                z = false;
            }
            if (jCurrentTimeMillis - jLongValue < jB && zA) {
                z2 = false;
            }
            if (z2 || z) {
                DH.requester(FlySDK.getContext()).getIAForce(false, z).request(new DH.DHResponder() { // from class: cn.fly.commons.a.h.1
                    @Override // cn.fly.tools.utils.DH.DHResponder
                    public void onResponse(DH.DHResponse dHResponse) {
                        ArrayList<HashMap<String, String>> iAForce = dHResponse.getIAForce(new int[0]);
                        if (iAForce != null && !iAForce.isEmpty() && z2) {
                            h.this.a(iAForce);
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ArrayList<HashMap<String, String>> arrayList) {
        a(((Long) a(cn.fly.commons.o.a("004dWdc:gf"), (String) 0L)).longValue(), "ALSAMT", arrayList);
        ad.b().a(ad.c, System.currentTimeMillis());
    }

    @Override // cn.fly.commons.a.c
    protected long m() {
        try {
            Calendar calendar = Calendar.getInstance();
            long timeInMillis = calendar.getTimeInMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
            calendar.add(5, 1);
            long timeInMillis2 = (calendar.getTimeInMillis() - timeInMillis) + new SecureRandom().nextInt(240000);
            return (timeInMillis2 / 1000) + (timeInMillis2 % 1000 == 0 ? 0 : 1);
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return 0L;
        }
    }
}