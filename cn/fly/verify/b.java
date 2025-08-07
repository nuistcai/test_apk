package cn.fly.verify;

import cn.fly.FlySDK;
import cn.fly.verify.common.exception.VerifyException;

/* loaded from: classes.dex */
public class b {
    public static VerifyException a() {
        if (FlySDK.isForb()) {
            return new VerifyException(m.C_PRIVACY_NOT_ACCEPTED_ERROR);
        }
        return null;
    }
}