package cn.fly.verify;

import cn.fly.verify.datatype.VerifyResult;

/* loaded from: classes.dex */
public interface y {
    void cancelLogin();

    void customizeLogin();

    void doOtherLogin();

    d<VerifyResult> getCallback();

    String getFakeNumber();
}