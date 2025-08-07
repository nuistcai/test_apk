package cn.fly.verify;

import cn.fly.commons.FLYVERIFY;

/* loaded from: classes.dex */
public class f {
    public static final int a;

    static {
        int i;
        int i2 = 0;
        for (String str : "13.7.6".split("\\.")) {
            try {
                i = Integer.parseInt(str);
            } catch (Throwable th) {
                i = 0;
            }
            i2 = (i2 * 100) + i;
        }
        a = i2;
        v.a("FLYVERIFY", a);
        new FLYVERIFY().init();
    }
}