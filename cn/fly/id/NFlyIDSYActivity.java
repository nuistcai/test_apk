package cn.fly.id;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class NFlyIDSYActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            if (getIntent() != null) {
                for (int i : getIntent().getIntArrayExtra("fg")) {
                    getWindow().addFlags(i);
                }
            }
        } catch (Throwable th) {
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        try {
            if (((PowerManager) getSystemService("power")).isScreenOn()) {
                finish();
            }
        } catch (Throwable th) {
        }
        super.onResume();
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        try {
            finish();
            return true;
        } catch (Throwable th) {
            return true;
        }
    }
}