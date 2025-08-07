package cn.fly.verify.ui.component;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import cn.fly.tools.utils.ResHelper;
import cn.fly.verify.j;

/* loaded from: classes.dex */
public class CommonAlertDialog extends Dialog implements View.OnClickListener {
    private static CommonAlertDialog dialog;
    private j callback;
    private Context context;
    private String dialogText;
    private SpannableString spannableDialogText;
    private WindowManager windowManager;

    public CommonAlertDialog(Context context) {
        super(context, ResHelper.getStyleRes(context, "Dialog_Common"));
        this.context = context;
    }

    public CommonAlertDialog(Context context, int i) {
        super(context, i);
        this.context = context;
    }

    public CommonAlertDialog(Context context, SpannableString spannableString, j jVar) {
        super(context, ResHelper.getStyleRes(context, "Dialog_Common"));
        this.context = context;
        this.spannableDialogText = spannableString;
        this.callback = jVar;
    }

    public CommonAlertDialog(Context context, String str, j jVar) {
        super(context, ResHelper.getStyleRes(context, "Dialog_Common"));
        this.context = context;
        this.dialogText = str;
        this.callback = jVar;
    }

    public CommonAlertDialog(Context context, String str, String str2, j jVar) {
        super(context, ResHelper.getStyleRes(context, "Dialog_Common"));
        this.context = context;
        this.dialogText = str2;
        this.callback = jVar;
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

    private void initView() {
        CharSequence charSequence;
        findViewById(ResHelper.getIdRes(this.context, "fly_verify_alert_dialog_cancel")).setOnClickListener(this);
        findViewById(ResHelper.getIdRes(this.context, "fly_verify_alert_dialog_allow")).setOnClickListener(this);
        TextView textView = (TextView) findViewById(ResHelper.getIdRes(this.context, "fly_verify_alert_dialog_text"));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(this.context.getResources().getColor(R.color.transparent));
        if (!TextUtils.isEmpty(this.dialogText)) {
            charSequence = this.dialogText;
        } else if (TextUtils.isEmpty(this.spannableDialogText)) {
            return;
        } else {
            charSequence = this.spannableDialogText;
        }
        textView.setText(charSequence);
    }

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, "", (j) null);
    }

    public static void showProgressDialog(Context context, SpannableString spannableString, j jVar) {
        dismissProgressDialog();
        dialog = new CommonAlertDialog(context, spannableString, jVar);
        dialog.show();
    }

    public static void showProgressDialog(Context context, String str, j jVar) {
        dismissProgressDialog();
        dialog = new CommonAlertDialog(context, str, jVar);
        dialog.show();
    }

    public static void showProgressDialog(Context context, String str, String str2, j jVar) {
        dismissProgressDialog();
        dialog = new CommonAlertDialog(context, str, str2, jVar);
        dialog.show();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == ResHelper.getIdRes(this.context, "fly_verify_alert_dialog_cancel")) {
            dismissProgressDialog();
        } else if (id == ResHelper.getIdRes(this.context, "fly_verify_alert_dialog_allow")) {
            dismissProgressDialog();
            this.callback.onResult(null);
        }
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(getLayoutInflater().inflate(ResHelper.getLayoutRes(this.context, "fly_verify_common_alert_dialog"), (ViewGroup) null));
        Window window = getWindow();
        if (window != null) {
            window.setLayout(-1, -2);
        }
        initView();
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            dismissProgressDialog();
        }
        return super.onKeyDown(i, keyEvent);
    }
}