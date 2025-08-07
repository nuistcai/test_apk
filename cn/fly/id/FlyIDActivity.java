package cn.fly.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cn.fly.FlySDK;
import cn.fly.mgs.a.e;
import cn.fly.mgs.a.g;
import cn.fly.tools.proguard.ClassKeeper;
import cn.fly.tools.utils.DH;

/* loaded from: classes.dex */
public class FlyIDActivity extends Activity implements ClassKeeper {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            FlySDK.init(getApplicationContext());
            e.a().a("[MCL] FlyIDActivity onCreate");
            Intent intent = getIntent();
            if (intent == null) {
                return;
            }
            g.a(getApplicationContext(), intent, true);
            finish();
        } catch (Throwable th) {
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        if (DH.SyncMtd.getOSVersionIntForFly() >= 29) {
            try {
                if (!isFinishing()) {
                    finish();
                }
            } catch (Throwable th) {
                e.a().a(th);
            }
        }
        super.onResume();
    }
}