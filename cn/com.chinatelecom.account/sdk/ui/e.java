package cn.com.chinatelecom.account.sdk.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import cn.com.chinatelecom.account.sdk.R;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class e {
    private static final int a = R.id.fake_status_bar_view;

    public static int a(Context context) {
        try {
            int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                return context.getResources().getDimensionPixelSize(identifier);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void a(Context context, int i) {
        if (context instanceof Activity) {
            a(((Activity) context).getWindow(), i);
        }
    }

    public static void a(Context context, int i, boolean z) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (context instanceof Activity) {
            a(context, i);
            a(context, z);
        }
    }

    public static void a(Context context, boolean z) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (context instanceof Activity) {
            a(((Activity) context).getWindow(), z);
        }
    }

    public static void a(Window window, int i) {
        if (Build.VERSION.SDK_INT < 21) {
            a(window, i, false);
            return;
        }
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.clearFlags(134217728);
        window.getDecorView().setSystemUiVisibility(256);
        window.setStatusBarColor(i);
    }

    public static void a(Window window, int i, boolean z) {
        Context context = window.getContext();
        window.addFlags(67108864);
        window.clearFlags(134217728);
        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
        View viewFindViewById = viewGroup.findViewById(android.R.id.content);
        if (viewFindViewById != null) {
            viewFindViewById.setPadding(0, z ? 0 : a(context), 0, 0);
        }
        View viewFindViewById2 = viewGroup.findViewById(a);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setBackgroundColor(i);
            if (viewFindViewById2.getVisibility() == 8) {
                viewFindViewById2.setVisibility(0);
                return;
            }
            return;
        }
        View view = new View(context);
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, a(context)));
        view.setBackgroundColor(i);
        view.setId(a);
        viewGroup.addView(view);
    }

    private static void a(Window window, boolean z) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 23) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(z ? systemUiVisibility | 8192 : systemUiVisibility & (-8193));
        } else if (Build.VERSION.SDK_INT >= 21) {
            switch (d.a()) {
                case MIUI:
                    b(window, z);
                    break;
                case Flyme:
                    c(window, z);
                    break;
            }
        }
    }

    private static void b(Window window, boolean z) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = window.getClass();
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
            Class<?> cls3 = Integer.TYPE;
            cls.getMethod("setExtraFlags", cls3, cls3).invoke(window, Integer.valueOf(z ? i : 0), Integer.valueOf(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void c(Window window, boolean z) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (window != null) {
            try {
                WindowManager.LayoutParams attributes = window.getAttributes();
                Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                declaredField.setAccessible(true);
                declaredField2.setAccessible(true);
                int i = declaredField.getInt(null);
                int i2 = declaredField2.getInt(attributes);
                declaredField2.setInt(attributes, z ? i2 | i : (i ^ (-1)) & i2);
                window.setAttributes(attributes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}