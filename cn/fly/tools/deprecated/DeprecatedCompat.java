package cn.fly.tools.deprecated;

import android.content.Context;
import cn.fly.commons.ac;
import cn.fly.commons.f;
import cn.fly.commons.k;
import cn.fly.tools.proguard.PublicMemberKeeper;

@Deprecated
/* loaded from: classes.dex */
public class DeprecatedCompat implements PublicMemberKeeper {
    public static boolean isFor() {
        return f.a();
    }

    public static String getMString(Context context) {
        return f.a(context);
    }

    public static String authorizeForOnce() {
        return f.b();
    }

    public static boolean isClear() {
        return k.a().b();
    }

    public static String getUserIdentity() {
        return ac.e();
    }
}