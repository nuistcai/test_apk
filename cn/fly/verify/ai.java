package cn.fly.verify;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.utils.DH;
import cn.fly.verify.common.exception.VerifyException;
import com.tencent.bugly.Bugly;
import java.util.List;

/* loaded from: classes.dex */
public class ai {
    private static String a(int i, Object... objArr) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            if (sb.length() > 0) {
                sb.append("\u0001");
            }
            String strValueOf = obj == null ? "" : String.valueOf(obj);
            sb.append(strValueOf);
            if (i == 1 || (i > 1 && i2 > 0)) {
                sb2.append("[").append(i2 + i).append("]").append(strValueOf);
            }
        }
        String string = sb.toString();
        v.a("token " + ((Object) sb2));
        return string;
    }

    public static String a(final String str, final int i, final Integer num, final String str2) throws Throwable {
        return (String) cn.fly.verify.util.l.a(new c<String>() { // from class: cn.fly.verify.ai.1
            @Override // cn.fly.verify.c
            /* renamed from: e, reason: merged with bridge method [inline-methods] */
            public String d() throws VerifyException {
                try {
                    String strC = ai.c(str, i, num, str2);
                    if (TextUtils.isEmpty(strC)) {
                        throw new VerifyException(m.INNER_TOKEN_NULL_ERR);
                    }
                    return "0:" + cn.fly.verify.util.f.a(strC);
                } catch (Throwable th) {
                    if (th instanceof VerifyException) {
                        throw th;
                    }
                    throw new VerifyException(m.INNER_TOKEN_NULL_ERR.a(), cn.fly.verify.util.l.a(th));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(String str, int i, Integer num, String str2) {
        try {
            String strJ = cn.fly.verify.util.l.j();
            String packageName = DH.SyncMtd.getPackageName();
            String appkey = FlySDK.getAppkey();
            String strA = cn.fly.verify.util.e.a();
            String appVersionName = DH.SyncMtd.getAppVersionName();
            if (appVersionName.contains("#")) {
                appVersionName = appVersionName.replace("#", "_");
            }
            String strC = !e.a("imsi") ? cn.fly.verify.util.e.c() : "";
            String strH = cn.fly.verify.util.e.h();
            String strD = !e.a("deviceId") ? cn.fly.verify.util.e.d() : "";
            if (TextUtils.isEmpty(strD)) {
                strD = "";
            }
            String strA2 = a(1, appkey, strJ, "1", packageName, appVersionName, Integer.valueOf(f.a), "", strA, strD, Long.valueOf(System.currentTimeMillis()), strC, strH, "", "", cn.fly.verify.util.l.b(), Bugly.SDK_IS_DEV, str, String.valueOf(e.l()), String.valueOf(e.m()), String.valueOf(e.k()), String.valueOf(cn.fly.verify.util.l.g()), "", "");
            int iA = cn.fly.verify.util.l.a(false);
            List<Integer> listC = cn.fly.verify.util.l.c(false);
            StringBuilder sb = new StringBuilder();
            if (listC != null && !listC.isEmpty()) {
                for (Integer num2 : listC) {
                    if (sb.length() > 0) {
                        sb.append(".");
                    }
                    sb.append(num2);
                }
            }
            return a(23, strA2, "", "", Integer.valueOf(i), num, str2, Integer.valueOf(iA), sb.toString(), cn.fly.verify.util.e.l(), cn.fly.verify.util.e.i(), cn.fly.verify.util.e.j(), cn.fly.verify.util.e.k(), Integer.valueOf(cn.fly.verify.util.e.m()), "", "", "", "", "", "", "", "", "", "") + "\u0001";
        } catch (Throwable th) {
            v.a(th, "getOriginToken");
            return "";
        }
    }
}