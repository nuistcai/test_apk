package cn.fly.commons;

import android.os.Looper;
import cn.fly.FlySDK;
import cn.fly.tools.proguard.ClassKeeper;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class FLYVERIFY implements FlyProduct, ClassKeeper {
    public static AtomicBoolean hasInit = new AtomicBoolean();

    /* JADX INFO: Access modifiers changed from: private */
    public void config() {
        if (FlySDK.isForb() || !hasInit.compareAndSet(false, true)) {
            return;
        }
        cn.fly.verify.n.a();
    }

    @Override // cn.fly.commons.FlyProduct
    public String getProductTag() {
        init();
        return "FLYVERIFY";
    }

    @Override // cn.fly.commons.FlyProduct
    public int getSdkver() {
        return cn.fly.verify.f.a;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [cn.fly.commons.FLYVERIFY$1] */
    public void init() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            config();
        } else {
            try {
                new Thread() { // from class: cn.fly.commons.FLYVERIFY.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            FLYVERIFY.this.config();
                        } catch (Throwable th) {
                        }
                    }
                }.start();
            } catch (Throwable th) {
            }
        }
    }
}