package cn.fly.verify.ui.component;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ResHelper;
import cn.fly.verify.util.e;
import cn.fly.verify.v;

/* loaded from: classes.dex */
public class PopupDialog extends AlertDialog {
    private static final String TAG = "PopupDialog";
    private LinearLayout bottomLl;
    private TextView cancel;
    private ImageView close;
    private TextView confirm;
    private Context context;
    private TextView msg;
    private TextView title;
    private RelativeLayout topRl;
    private View verticalLine;
    private View view;
    private int width;

    protected PopupDialog(Context context, boolean z, boolean z2) {
        super(context, ResHelper.getStyleRes(context, "Dialog_Common"));
        this.context = context;
        double screenWidth = getScreenWidth(this.context);
        Double.isNaN(screenWidth);
        this.width = (int) (screenWidth * 0.7d);
        setCancelable(z);
        setCanceledOnTouchOutside(z2);
        this.view = LayoutInflater.from(this.context).inflate(ResHelper.getLayoutRes(context, "fly_verify_popup_dialog"), (ViewGroup) null);
        initView();
    }

    public static PopupDialog create(Context context, int i, int i2, int i3, View.OnClickListener onClickListener, int i4, View.OnClickListener onClickListener2, boolean z, boolean z2, boolean z3) {
        return create(context, i, i2, i3, onClickListener, i4, onClickListener2, z, z2, z3, null);
    }

    public static PopupDialog create(Context context, int i, int i2, int i3, View.OnClickListener onClickListener, int i4, View.OnClickListener onClickListener2, boolean z, boolean z2, boolean z3, DialogInterface.OnDismissListener onDismissListener) throws Resources.NotFoundException {
        String string;
        String string2;
        String string3;
        PopupDialog popupDialog = new PopupDialog(context, z, z2);
        if (onDismissListener != null) {
            popupDialog.setOnDismissListener(onDismissListener);
        }
        String string4 = null;
        if (i > 0) {
            try {
                string = context.getResources().getString(i);
            } catch (Resources.NotFoundException e) {
                v.a(e, "Resource not found. resId=" + i);
            }
        } else {
            string = null;
        }
        popupDialog.setDialogTitle(string, z3);
        if (i2 > 0) {
            try {
                string2 = context.getResources().getString(i2);
            } catch (Resources.NotFoundException e2) {
                v.a(e2, "Resource not found. resId=" + i2);
            }
        } else {
            string2 = null;
        }
        popupDialog.setDialogMessage(string2);
        if (i3 > 0) {
            try {
                string3 = context.getResources().getString(i3);
            } catch (Resources.NotFoundException e3) {
                e = e3;
                string3 = null;
                v.a(e, "Resource not found.");
                popupDialog.setDialogButton(string3, onClickListener, string4, onClickListener2);
                return popupDialog;
            }
        } else {
            string3 = null;
        }
        if (i4 > 0) {
            try {
                string4 = context.getResources().getString(i4);
            } catch (Resources.NotFoundException e4) {
                e = e4;
                v.a(e, "Resource not found.");
                popupDialog.setDialogButton(string3, onClickListener, string4, onClickListener2);
                return popupDialog;
            }
        }
        popupDialog.setDialogButton(string3, onClickListener, string4, onClickListener2);
        return popupDialog;
    }

    public static PopupDialog create(Context context, String str, String str2, String str3, View.OnClickListener onClickListener, String str4, View.OnClickListener onClickListener2, boolean z, boolean z2, boolean z3) {
        PopupDialog popupDialog = new PopupDialog(context, z, z2);
        popupDialog.setDialogTitle(str, z3);
        popupDialog.setDialogMessage(str2);
        popupDialog.setDialogButton(str3, onClickListener, str4, onClickListener2);
        return popupDialog;
    }

    private int getScreenHeight(Context context) {
        return getScreenSize(context)[1];
    }

    private int[] getScreenSize(Context context) {
        WindowManager windowManager;
        try {
            windowManager = (WindowManager) DH.SyncMtd.getSystemServiceSafe("window");
        } catch (Throwable th) {
            v.a(th, "get SCreenSize Exception");
            windowManager = null;
        }
        if (windowManager == null) {
            return new int[]{0, 0};
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        if (e.m() < 13) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
        }
        try {
            Point point = new Point();
            DH.SyncMtd.invokeInstanceMethod(defaultDisplay, "getRealSize", new Object[]{point}, new Class[]{Point.class});
            return new int[]{point.x, point.y};
        } catch (Throwable th2) {
            v.a(th2, "get SCreenSize Exception");
            return new int[]{0, 0};
        }
    }

    private int getScreenWidth(Context context) {
        return getScreenSize(context)[0];
    }

    private void initView() {
        this.title = (TextView) this.view.findViewById(ResHelper.getIdRes(this.context, "common_dialog_title_tv"));
        this.close = (ImageView) this.view.findViewById(ResHelper.getIdRes(this.context, "common_dialog_close_iv"));
        this.msg = (TextView) this.view.findViewById(ResHelper.getIdRes(this.context, "common_dialog_message_tv"));
        this.msg.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.confirm = (TextView) this.view.findViewById(ResHelper.getIdRes(this.context, "common_dialog_confirm_tv"));
        this.cancel = (TextView) this.view.findViewById(ResHelper.getIdRes(this.context, "common_dialog_cancel_tv"));
        this.bottomLl = (LinearLayout) this.view.findViewById(ResHelper.getIdRes(this.context, "common_dialog_bottom_ll"));
        this.topRl = (RelativeLayout) this.view.findViewById(ResHelper.getIdRes(this.context, "common_dialog_top_rl"));
        this.verticalLine = this.view.findViewById(ResHelper.getIdRes(this.context, "common_dialog_vertical_line"));
        if (this.close != null) {
            this.close.setOnClickListener(new View.OnClickListener() { // from class: cn.fly.verify.ui.component.PopupDialog.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PopupDialog.this.dismiss();
                }
            });
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(this.view, new LinearLayout.LayoutParams(this.width, -2, 0.0f));
    }

    public void setCancel(int i) {
        this.cancel.setText(this.context.getResources().getString(i));
    }

    protected void setDialogButton(int i, CharSequence charSequence, final View.OnClickListener onClickListener) {
        TextView textView;
        StringBuilder sb;
        TextView textView2;
        View.OnClickListener onClickListener2;
        if (charSequence != null && !"".equals(charSequence)) {
            switch (i) {
                case -2:
                    if (this.cancel != null) {
                        this.cancel.setText(charSequence);
                        textView2 = this.cancel;
                        onClickListener2 = new View.OnClickListener() { // from class: cn.fly.verify.ui.component.PopupDialog.2
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                if (onClickListener != null) {
                                    onClickListener.onClick(view);
                                }
                                PopupDialog.this.dismiss();
                            }
                        };
                        textView2.setOnClickListener(onClickListener2);
                        break;
                    }
                    break;
                case -1:
                    if (this.confirm != null) {
                        this.confirm.setText(charSequence);
                        textView2 = this.confirm;
                        onClickListener2 = new View.OnClickListener() { // from class: cn.fly.verify.ui.component.PopupDialog.1
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                if (onClickListener != null) {
                                    onClickListener.onClick(view);
                                }
                                PopupDialog.this.dismiss();
                            }
                        };
                        textView2.setOnClickListener(onClickListener2);
                        break;
                    }
                    break;
                default:
                    sb = new StringBuilder();
                    break;
            }
            return;
        }
        switch (i) {
            case -2:
                if (this.cancel != null) {
                    textView = this.cancel;
                    textView.setVisibility(8);
                    return;
                }
                return;
            case -1:
                if (this.confirm != null) {
                    textView = this.confirm;
                    textView.setVisibility(8);
                    return;
                }
                return;
            default:
                sb = new StringBuilder();
                break;
        }
        v.a(sb.append("Button can not be found. whichButton=").append(i).toString());
    }

    public void setDialogButton(String str, View.OnClickListener onClickListener, String str2, View.OnClickListener onClickListener2) {
        if ((str == null || "".equals(str)) && (str2 == null || "".equals(str2))) {
            if (this.bottomLl != null) {
                this.bottomLl.setVisibility(8);
                return;
            }
            return;
        }
        if (str == null || "".equals(str) || str2 == null || "".equals(str2)) {
            this.verticalLine.setVisibility(8);
            setDialogButton(-1, null, null);
            if (str != null && !"".equals(str)) {
                setDialogButton(-2, str, onClickListener);
                return;
            }
        } else {
            setDialogButton(-1, str, onClickListener);
        }
        setDialogButton(-2, str2, onClickListener2);
    }

    public void setDialogMessage(CharSequence charSequence) {
        if (charSequence == null || "".equals(charSequence)) {
            if (this.msg != null) {
                this.msg.setVisibility(8);
            }
        } else if (this.msg != null) {
            this.msg.setText(charSequence);
        }
    }

    public void setDialogTitle(CharSequence charSequence, boolean z) {
        if (charSequence == null || "".equals(charSequence)) {
            if (this.title != null) {
                this.title.setVisibility(8);
            }
        } else if (this.title != null) {
            this.title.setText(charSequence);
        }
        if (!z || this.close == null) {
            return;
        }
        this.close.setVisibility(0);
    }
}