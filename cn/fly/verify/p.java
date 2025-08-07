package cn.fly.verify;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.utils.DH;
import cn.fly.verify.common.exception.VerifyException;
import java.util.HashMap;

/* loaded from: classes.dex */
public class p {
    public static HashMap<String, Object> a(u uVar) throws VerifyException {
        if (FlySDK.isForb()) {
            throw new VerifyException(m.C_PRIVACY_NOT_ACCEPTED_ERROR);
        }
        if (TextUtils.isEmpty(FlySDK.getAppkey())) {
            throw new VerifyException(m.C_APPKEY_NULL);
        }
        return o.a().a(q.a(), C0042r.a(1, uVar));
    }

    public static HashMap<String, Object> a(String str, u uVar) throws VerifyException {
        if (FlySDK.isForb()) {
            throw new VerifyException(m.C_PRIVACY_NOT_ACCEPTED_ERROR);
        }
        if (TextUtils.isEmpty(FlySDK.getAppkey())) {
            throw new VerifyException(m.C_APPKEY_NULL);
        }
        String strA = C0042r.a(3, uVar);
        StringBuilder sb = new StringBuilder();
        sb.append(FlySDK.getAppkey()).append("/").append(DH.SyncMtd.getPackageName()).append("/").append(cn.fly.verify.util.e.a());
        return o.a().a(strA + sb.toString(), str, uVar);
    }
}