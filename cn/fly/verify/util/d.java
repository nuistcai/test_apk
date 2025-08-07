package cn.fly.verify.util;

import cn.fly.verify.v;

/* loaded from: classes.dex */
public class d {
    public static int a() {
        int iA = l.a();
        v.a("operatorInt: " + iA);
        if (iA == 1) {
            return 1;
        }
        if (iA == 2) {
            return 2;
        }
        return iA == 3 ? 3 : 10;
    }

    public static int a(String str) {
        if ("CMCC".equals(str)) {
            return 1;
        }
        if ("CUCC".equals(str)) {
            return 2;
        }
        return "CTCC".equals(str) ? 3 : 10;
    }
}