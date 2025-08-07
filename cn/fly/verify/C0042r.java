package cn.fly.verify;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.network.NetCommunicator;

/* renamed from: cn.fly.verify.r, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0042r {
    public static String a(int i, u uVar) {
        String strA;
        String str;
        String strA2;
        String str2;
        String str3;
        switch (i) {
            case 1:
                strA = a("http://api-auth.zztfly.com");
                str = "api/initSec";
                return a(strA, str, "api", false, uVar);
            case 2:
                strA2 = a("http://conf-auth.zztfly.com");
                str2 = "api/usedMobile";
                str3 = "conf";
                break;
            case 3:
                strA2 = a("http://cdn-api-auth.zztfly.com");
                str2 = "api/initSecCdn/1/";
                str3 = "cdn";
                break;
            case 4:
                strA2 = a("http://log-auth.zztfly.com");
                str2 = "api/log";
                str3 = "log";
                break;
            case 5:
                strA = a("http://api-auth.zztfly.com");
                str = "api/pv";
                return a(strA, str, "api", false, uVar);
            default:
                return null;
        }
        return a(strA2, str2, str3, false, uVar);
    }

    private static String a(String str) {
        return (TextUtils.isEmpty(str) || str.endsWith("/")) ? str : str + "/";
    }

    private static String a(String str, String str2, String str3, boolean z, u uVar) {
        String strA;
        String strDynamicModifyUrl;
        if (uVar != null) {
            try {
                uVar.a((String) null, (String) null, "bsdm_start", str3);
            } catch (Throwable th) {
                v.a(th);
                strA = null;
            }
        }
        strA = FlySDK.syncGetBSDM("FLYVERIFY", str3, str, z);
        if (uVar != null) {
            uVar.a((String) null, (String) null, "bsdm_end", str3);
        }
        if (TextUtils.isEmpty(strA)) {
            strA = str;
        } else if (!strA.startsWith("http://")) {
            strA = a("http://" + strA);
        }
        try {
            strDynamicModifyUrl = NetCommunicator.dynamicModifyUrl(strA + str2);
        } catch (Throwable th2) {
            v.a(th2);
            strDynamicModifyUrl = "https://" + str + str2;
        }
        if (uVar != null) {
            uVar.a((String) null, (String) null, strDynamicModifyUrl.startsWith("https") ? "ck_is_https" : "ck_is_http", str3);
        }
        return strDynamicModifyUrl;
    }
}