package cn.fly.verify.util;

import cn.fly.FlySDK;
import cn.fly.verify.v;

/* loaded from: classes.dex */
public class i {
    public static int a() {
        try {
            switch (FlySDK.isAuth()) {
                case 0:
                    v.a("privacy service unknow status");
                    break;
                case 1:
                    v.a("privacy service accepted");
                    break;
                case 2:
                    v.a("privacy service dont need be accepted");
                    break;
                default:
                    v.a("privacy service not accepted");
                    break;
            }
            return 0;
        } catch (Throwable th) {
            v.a(th, "privacy service");
            return 0;
        }
    }

    public static void b() {
        if (j.b()) {
            FlySDK.submitPolicyGrantResult(null, true);
            j.a(false);
        }
    }
}