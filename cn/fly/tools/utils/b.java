package cn.fly.tools.utils;

import cn.fly.FlySDK;
import cn.fly.commons.a.l;
import cn.fly.commons.ad;
import cn.fly.commons.y;
import java.io.File;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class b {
    private static final String a = l.a("003.idelgg");
    private static final String b = l.a("005%hdeeeefm<k");
    private static final String c = l.a("009=fgeeeeei@d>eggjeiig");
    private static final String d = l.a("0135egelggeiZdTelegegelMf3gjeiig");

    public static synchronized boolean a() {
        File file = new File(new File(FlySDK.getContext().getFilesDir(), a), d);
        if (!file.exists() || file.length() <= 0) {
            return false;
        }
        File file2 = new File(FlySDK.getContext().getFilesDir(), b);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        FileUtils.copyFile(file, new File(file2, c));
        ad.b().a((ArrayList<String>) null);
        return true;
    }

    public static synchronized boolean b() {
        File file = new File(new File(FlySDK.getContext().getFilesDir(), l.a("007%idelgghmLg-ekgj")), l.a("008Gegelggeied3i:eiig"));
        if (!file.exists() || file.length() <= 56320) {
            return false;
        }
        y.a().a(1);
        return true;
    }
}