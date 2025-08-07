package com.squareup.leakcanary.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.squareup.leakcanary.R;

/* loaded from: classes.dex */
public class RequestStoragePermissionActivity extends Activity {
    public static PendingIntent createPendingIntent(Context context) {
        LeakCanaryInternals.setEnabledBlocking(context, RequestStoragePermissionActivity.class, true);
        Intent intent = new Intent(context, (Class<?>) RequestStoragePermissionActivity.class);
        intent.setFlags(335544320);
        return PendingIntent.getActivity(context, 1, intent, 134217728);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (hasStoragePermission()) {
                finish();
            } else {
                String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                requestPermissions(permissions, 42);
            }
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (!hasStoragePermission()) {
            Toast.makeText(getApplication(), R.string.leak_canary_permission_not_granted, 1).show();
        }
        finish();
    }

    @Override // android.app.Activity
    public void finish() {
        overridePendingTransition(0, 0);
        super.finish();
    }

    private boolean hasStoragePermission() {
        return checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }
}