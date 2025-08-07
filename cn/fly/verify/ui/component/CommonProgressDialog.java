package cn.fly.verify.ui.component;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ResHelper;
import cn.fly.verify.util.e;
import cn.fly.verify.v;

/* loaded from: classes.dex */
public class CommonProgressDialog extends Dialog {
    private static CommonProgressDialog dialog;
    private Context context;
    private WindowManager windowManager;

    public CommonProgressDialog(Context context) {
        super(context, ResHelper.getStyleRes(context, "Dialog_Common"));
        this.context = context;
    }

    public CommonProgressDialog(Context context, int i) {
        super(context, i);
        this.context = context;
    }

    public static void dismissProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
        } catch (Exception e2) {
        } catch (Throwable th) {
            dialog = null;
            throw th;
        }
        dialog = null;
    }

    private int getDeviceWidth(Context context) {
        if (this.windowManager == null) {
            this.windowManager = (WindowManager) DH.SyncMtd.getSystemServiceSafe("window");
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return context.getResources().getConfiguration().orientation == 1 ? displayMetrics.widthPixels : displayMetrics.heightPixels;
    }

    public static void showProgressDialog(Context context) {
        dismissProgressDialog();
        try {
            Activity activity = (Activity) context;
            if (e.m() >= 17 && (activity == null || activity.isFinishing() || activity.isDestroyed())) {
                v.a("activity is not running");
            } else {
                dialog = new CommonProgressDialog(context);
                dialog.show();
            }
        } catch (Throwable th) {
            v.a(th, "context should not be application: " + th.getMessage());
        }
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        double deviceWidth = getDeviceWidth(this.context);
        Double.isNaN(deviceWidth);
        int i = (int) (deviceWidth * 0.2d);
        setContentView(getLayoutInflater().inflate(ResHelper.getLayoutRes(this.context, "fly_verify_common_progress_dialog"), (ViewGroup) null), new RelativeLayout.LayoutParams(i, i));
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            dismissProgressDialog();
        }
        return super.onKeyDown(i, keyEvent);
    }
}