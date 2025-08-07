package cn.fly.verify;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import cn.fly.FlySDK;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.HashonHelper;
import java.util.HashMap;

/* loaded from: classes.dex */
public class q {
    public static HashMap<String, Object> a() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            String packageName = DH.SyncMtd.getPackageName();
            map.put("appkey", FlySDK.getAppkey());
            map.put("appVersion", DH.SyncMtd.getAppVersionName());
            map.put("plat", "1");
            map.put("sdkVersion", Integer.valueOf(f.a));
            map.put("appPackage", packageName);
            map.put("old", false);
            map.put("duid", 0);
            map.put("md5", cn.fly.verify.util.e.a());
            return map;
        } catch (Throwable th) {
            v.a(th, "buildInitParams");
            return map;
        }
    }

    public static HashMap<String, Object> a(t tVar) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            String strB = tVar.b();
            String strN = tVar.c().equals("preVerify") ? e.n() : tVar.c().equals("verify") ? e.o() : tVar.c().equals("authPageOpend") ? e.p() : null;
            if (strN != null && !strN.equals(strB)) {
                strB = strB + "," + strN;
            }
            map.put("serialId", strB);
            map.put("isFirstPre", Boolean.valueOf(tVar.a()));
            map.put("type", tVar.c());
            map.put("method", tVar.d());
            map.put("appkey", FlySDK.getAppkey());
            map.put("plat", "1");
            map.put("model", cn.fly.verify.util.e.k());
            String strJ = cn.fly.verify.util.e.j();
            map.put("deviceName", strJ);
            map.put(NotificationCompat.CATEGORY_SYSTEM, String.valueOf(cn.fly.verify.util.e.m()));
            map.put("duid", cn.fly.verify.util.l.j());
            if (TextUtils.isEmpty(tVar.q()) || "CMCC".equals(tVar.q())) {
                tVar.e(cn.fly.verify.util.l.h());
            }
            map.put("mnc", cn.fly.verify.util.l.b());
            map.put("operator", tVar.q());
            map.put("sdkver", Integer.valueOf(f.a));
            map.put("pkg", DH.SyncMtd.getPackageName());
            map.put("md5", cn.fly.verify.util.e.a());
            map.put("time", Long.valueOf(tVar.i()));
            map.put("sdkMode", "ui1376");
            map.put("romVersion", cn.fly.verify.util.e.e());
            map.put("costTime", Long.valueOf(tVar.j()));
            map.put("stepTime", Long.valueOf(tVar.k()));
            map.put("removeTelcom", Boolean.valueOf(tVar.m()));
            map.put("isCache", Boolean.valueOf(tVar.l()));
            map.put("appId", tVar.n());
            map.put("isCdn", Boolean.valueOf(tVar.p()));
            boolean zO = tVar.o();
            map.put("isError", Boolean.valueOf(zO));
            map.put("resCode", Integer.valueOf(tVar.e()));
            map.put("resDesc", tVar.f());
            map.put("innerCode", Integer.valueOf(tVar.g()));
            map.put("innerDesc", tVar.h());
            map.put("deviceId", cn.fly.verify.util.e.d());
            map.put("oaid", cn.fly.verify.util.e.h());
            map.put("slots", Integer.valueOf(cn.fly.verify.util.l.a(false)));
            map.put("slots2", Integer.valueOf(cn.fly.verify.util.l.b(false)));
            map.put("subids", cn.fly.verify.util.l.c(false));
            map.put("sysver", cn.fly.verify.util.e.l());
            map.put("factory", cn.fly.verify.util.e.i());
            map.put("brand", strJ);
            map.put("ui", 1);
            if (tVar.s() != null) {
                map.put("multiFlag", tVar.s());
            }
            if (!TextUtils.isEmpty(tVar.r())) {
                map.put("pmask", tVar.r());
            }
            if (tVar.d().equals("preVerify")) {
                map.put("netStatus", Integer.valueOf(cn.fly.verify.util.l.m()));
                map.put("net", cn.fly.verify.util.e.g());
            }
            if (tVar.u() != null) {
                map.put("preVerifyWay", tVar.u());
            }
            if (tVar.t() != null) {
                map.put("verifyWay", tVar.t());
            }
            if (tVar.v() != null) {
                map.put("cmPreType", tVar.v());
            }
            v.a("append: type = " + tVar.c() + ", method = " + tVar.d() + ", isError = " + zO + HashonHelper.fromHashMap(map));
        } catch (Throwable th) {
            v.a(th, "buildLogParams");
        }
        return map;
    }
}