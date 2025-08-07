package cn.fly.verify;

import android.view.View;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.util.List;

/* loaded from: classes.dex */
public class CustomUIRegister implements PublicMemberKeeper {
    public static void addCustomizedUi(List<View> list, CustomViewClickListener customViewClickListener) {
        k.a().a(list, customViewClickListener);
    }

    public static void addTitleBarCustomizedUi(List<View> list, CustomViewClickListener customViewClickListener) {
        k.a().b(list, customViewClickListener);
    }

    public static void setCustomizeLoadingView(View view) {
        k.a().a(view);
    }

    public static void setLoadingViewHidden(boolean z) {
        k.a().a(z);
    }
}