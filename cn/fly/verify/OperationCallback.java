package cn.fly.verify;

import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.verify.common.exception.VerifyException;

/* loaded from: classes.dex */
public abstract class OperationCallback<T> implements PublicMemberKeeper {
    private boolean canceled;

    @Deprecated
    public boolean isCanceled() {
        return this.canceled;
    }

    public abstract void onComplete(T t);

    public abstract void onFailure(VerifyException verifyException);

    @Deprecated
    public void setCanceled(boolean z) {
        this.canceled = z;
    }
}