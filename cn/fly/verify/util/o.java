package cn.fly.verify.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import cn.fly.verify.aa;
import cn.fly.verify.ag;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.datatype.UiSettings;

/* loaded from: classes.dex */
public class o {
    public static int a;

    public static ag a(int i) {
        return i == 1 ? aa.a().b(cn.fly.verify.i.a().c()) : aa.a().a(cn.fly.verify.i.a().d());
    }

    public static void a(Activity activity) {
        if (activity != null && e.m() >= 28) {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            activity.getWindow().setAttributes(attributes);
        }
    }

    public static void a(Activity activity, ag agVar) {
        int animRes;
        String str;
        if (agVar != null) {
            if (agVar.am()) {
                activity.overridePendingTransition(agVar.ao(), agVar.ap());
                return;
            }
            if (agVar.ah()) {
                animRes = cn.fly.tools.utils.ResHelper.getAnimRes(activity, "fly_verify_translate_in");
                str = "fly_verify_translate_out";
            } else if (agVar.aj()) {
                animRes = cn.fly.tools.utils.ResHelper.getAnimRes(activity, "fly_verify_translate_right_in");
                str = "fly_verify_translate_left_out";
            } else if (agVar.ai()) {
                animRes = cn.fly.tools.utils.ResHelper.getAnimRes(activity, "fly_verify_translate_bottom_in");
                str = "fly_verify_translate_bottom_out";
            } else if (agVar.ak()) {
                animRes = cn.fly.tools.utils.ResHelper.getAnimRes(activity, "fly_verify_zoom_in");
                str = "fly_verify_zoom_out";
            } else {
                if (!agVar.al()) {
                    return;
                }
                animRes = cn.fly.tools.utils.ResHelper.getAnimRes(activity, "fly_verify_fade_in");
                str = "fly_verify_fade_out";
            }
            activity.overridePendingTransition(animRes, cn.fly.tools.utils.ResHelper.getAnimRes(activity, str));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void a(Activity activity, boolean z, boolean z2) {
        int i;
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case 0:
            default:
                i = 1;
                break;
            case 1:
                i = 0;
                break;
            case 2:
                i = 9;
                break;
            case 3:
                i = 8;
                break;
        }
        if (!z || z2) {
            if (!z2 || z || i == 1 || i == 9) {
                a = i;
            } else {
                a = 1;
            }
        } else if (i != 0 && i != 8) {
            a = 0;
        }
        activity.setRequestedOrientation(a);
    }

    public static void a(Context context, int i, int i2, int i3, int i4, int i5, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
        int iDipToPx = cn.fly.tools.utils.ResHelper.dipToPx(context, i3);
        int iDipToPx2 = cn.fly.tools.utils.ResHelper.dipToPx(context, i);
        int iDipToPx3 = cn.fly.tools.utils.ResHelper.dipToPx(context, i2);
        if (i2 == -1) {
            marginLayoutParams.leftMargin = iDipToPx2;
        } else {
            marginLayoutParams.rightMargin = iDipToPx3;
        }
        if (i3 != -1) {
            marginLayoutParams.topMargin = iDipToPx;
        }
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(marginLayoutParams);
        layoutParams2.addRule(i2 == -1 ? 9 : 11);
        layoutParams2.addRule(i3 == -1 ? 15 : 10);
        if (i4 != -1) {
            layoutParams2.width = cn.fly.tools.utils.ResHelper.dipToPx(context, i4);
        }
        if (i5 != -1) {
            layoutParams2.height = cn.fly.tools.utils.ResHelper.dipToPx(context, i5);
        }
        view.setLayoutParams(layoutParams2);
    }

    public static void a(Context context, View view) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        int iDipToPx = cn.fly.tools.utils.ResHelper.dipToPx(context, 30);
        layoutParams.leftMargin = iDipToPx;
        layoutParams.rightMargin = iDipToPx;
        view.setLayoutParams(layoutParams);
    }

    public static void a(Context context, View view, int i, int i2, int i3, int i4) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height);
        int iDipToPx = cn.fly.tools.utils.ResHelper.dipToPx(context, i);
        int iDipToPx2 = cn.fly.tools.utils.ResHelper.dipToPx(context, i2);
        int iDipToPx3 = cn.fly.tools.utils.ResHelper.dipToPx(context, i3);
        int iDipToPx4 = cn.fly.tools.utils.ResHelper.dipToPx(context, i4);
        if (i != -1) {
            layoutParams2.leftMargin = iDipToPx;
        }
        if (i2 != -1) {
            layoutParams2.rightMargin = iDipToPx2;
        }
        if (i3 != -1) {
            layoutParams2.topMargin = iDipToPx3;
        }
        if (i4 != -1) {
            layoutParams2.bottomMargin = iDipToPx4;
        }
        view.setLayoutParams(layoutParams2);
    }

    public static void a(Context context, View view, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        int i7;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
        int iDipToPx = cn.fly.tools.utils.ResHelper.dipToPx(context, i);
        int iDipToPx2 = cn.fly.tools.utils.ResHelper.dipToPx(context, i2);
        int iDipToPx3 = cn.fly.tools.utils.ResHelper.dipToPx(context, i3);
        int iDipToPx4 = cn.fly.tools.utils.ResHelper.dipToPx(context, i4);
        if (i != -1) {
            marginLayoutParams.leftMargin = iDipToPx;
        }
        if (i2 != -1) {
            marginLayoutParams.rightMargin = iDipToPx2;
        }
        if (i3 != -1) {
            marginLayoutParams.topMargin = iDipToPx3;
        }
        if (i4 != -1) {
            marginLayoutParams.bottomMargin = iDipToPx4;
        }
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(marginLayoutParams);
        if (z) {
            layoutParams2.addRule(11);
        } else {
            if (i2 != -1 || i != -1) {
                i7 = i != -1 ? 9 : 14;
                layoutParams2.addRule(11);
            }
            layoutParams2.addRule(i7);
        }
        layoutParams2.addRule(i4 != -1 ? 12 : 10);
        if (i5 != -1) {
            layoutParams2.width = cn.fly.tools.utils.ResHelper.dipToPx(context, i5);
        } else {
            layoutParams2.width = i5;
        }
        if (i6 != -1) {
            layoutParams2.height = cn.fly.tools.utils.ResHelper.dipToPx(context, i6);
        }
        view.setLayoutParams(layoutParams2);
    }

    public static void a(Context context, View view, int i, int i2, int i3, int i4, boolean z) {
        int i5;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
        int iDipToPx = cn.fly.tools.utils.ResHelper.dipToPx(context, i);
        int iDipToPx2 = cn.fly.tools.utils.ResHelper.dipToPx(context, i2);
        int iDipToPx3 = cn.fly.tools.utils.ResHelper.dipToPx(context, i3);
        int iDipToPx4 = cn.fly.tools.utils.ResHelper.dipToPx(context, i4);
        if (i != -1) {
            marginLayoutParams.leftMargin = iDipToPx;
        }
        if (i2 != -1) {
            marginLayoutParams.rightMargin = iDipToPx2;
        }
        if (i3 != -1) {
            marginLayoutParams.topMargin = iDipToPx3;
        }
        if (i4 != -1) {
            marginLayoutParams.bottomMargin = iDipToPx4;
        }
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(marginLayoutParams);
        if (z) {
            layoutParams2.addRule(11);
        } else {
            if (i2 != -1 || i != -1) {
                i5 = i != -1 ? 9 : 14;
                layoutParams2.addRule(11);
            }
            layoutParams2.addRule(i5);
        }
        layoutParams2.addRule(i4 != -1 ? 12 : 10);
        view.setLayoutParams(layoutParams2);
    }

    public static void b(Activity activity) {
        if (activity != null && cn.fly.verify.i.a().k()) {
            UiSettings uiSettingsC = cn.fly.verify.i.a().c();
            LandUiSettings landUiSettingsD = cn.fly.verify.i.a().d();
            if ((uiSettingsC == null && landUiSettingsD == null) || e.m() == 26) {
                activity.setRequestedOrientation(3);
                return;
            }
            if (uiSettingsC != null && landUiSettingsD != null) {
                a(activity, true, true);
            } else if (landUiSettingsD != null) {
                a(activity, true, false);
            } else {
                a(activity, false, true);
            }
        }
    }

    public static void b(Activity activity, ag agVar) {
        if (activity == null || agVar == null || !agVar.as() || e.m() < 21) {
            return;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(1280);
        activity.getWindow().clearFlags(67108864);
        activity.getWindow().addFlags(Integer.MIN_VALUE);
        activity.getWindow().setStatusBarColor(0);
        if (!agVar.at() || e.m() < 23) {
            return;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(9216);
    }

    public static void b(Context context, View view) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        int iDipToPx = cn.fly.tools.utils.ResHelper.dipToPx(context, 30);
        layoutParams.leftMargin = iDipToPx;
        layoutParams.rightMargin = iDipToPx;
        view.setLayoutParams(layoutParams);
    }
}