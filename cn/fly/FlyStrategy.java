package cn.fly;

import cn.fly.commons.ad;
import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.tools.utils.i;

/* loaded from: classes.dex */
public class FlyStrategy implements PublicMemberKeeper {
    public static void setStrategy(final int i) {
        new Thread(new i() { // from class: cn.fly.FlyStrategy.1
            @Override // cn.fly.tools.utils.i
            protected void a() {
                ad.b().a(i);
            }
        }).start();
    }
}