package cn.fly.verify;

import cn.fly.verify.datatype.VerifyResult;

/* loaded from: classes.dex */
public abstract class VerifyCallback extends OperationCallback<VerifyResult> {
    public abstract void onOtherLogin();

    public abstract void onUserCanceled();
}