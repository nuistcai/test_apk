package cn.fly.verify.util;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.verify.v;

/* loaded from: classes.dex */
public class n extends cn.fly.tools.utils.ResHelper {
    private static Resources a;

    public static int a(int i) {
        if (a == null) {
            a = FlySDK.getContext().getResources();
        }
        return a.getDimensionPixelSize(i);
    }

    public static int a(int i, int i2) {
        if (i > 0) {
            try {
                return b(i);
            } catch (Resources.NotFoundException e) {
                v.a("getDimenDpSizeSafe Dimen resource not found. id: " + i);
                return i;
            }
        }
        if (i2 <= 0) {
            return i2;
        }
        try {
            return b(i2);
        } catch (Resources.NotFoundException e2) {
            return i2;
        }
    }

    public static int a(String str) {
        return getResId(FlySDK.getContext(), "dimen", str);
    }

    public static Drawable a(int i, Drawable drawable, int i2) {
        if (i <= 0 && drawable == null) {
            return e(i2);
        }
        if (i <= 0 && drawable != null) {
            return drawable;
        }
        if (i <= 0 || drawable != null) {
            try {
                return e(i);
            } catch (Resources.NotFoundException e) {
                v.a("getDrawableSafe Drawable resource not found. id: " + i);
                return drawable;
            }
        }
        try {
            return e(i);
        } catch (Resources.NotFoundException e2) {
            v.a("getDrawableSafe Drawable resource not found. id: " + i);
            return e(i2);
        }
    }

    public static String a(int i, String str, int i2) {
        if (i <= 0 && TextUtils.isEmpty(str)) {
            if (i2 <= 0) {
                return "";
            }
            try {
                return d(i2);
            } catch (Resources.NotFoundException e) {
                return "";
            }
        }
        if (i <= 0 || !TextUtils.isEmpty(str)) {
            if (i <= 0 && !TextUtils.isEmpty(str)) {
                return str;
            }
            try {
                return d(i);
            } catch (Resources.NotFoundException e2) {
                v.a("getStringSafe String resource not found. id: " + i);
                return str;
            }
        }
        try {
            return d(i);
        } catch (Resources.NotFoundException e3) {
            v.a("getStringSafe String resource not found. id: " + i);
            if (i2 <= 0) {
                return "";
            }
            try {
                return d(i2);
            } catch (Resources.NotFoundException e4) {
                return "";
            }
        }
    }

    public static int b(int i) {
        return pxToDip(FlySDK.getContext(), a(i));
    }

    public static int b(int i, int i2) {
        try {
            return c(i);
        } catch (Resources.NotFoundException e) {
            l.a(e);
            v.a("getColorSafe Color resource not found. id: " + i);
            return i;
        }
    }

    public static int c(int i) {
        if (a == null) {
            a = FlySDK.getContext().getResources();
        }
        return a.getColor(i);
    }

    public static String c(int i, int i2) {
        if (i <= 0) {
            return i2 <= 0 ? "" : d(i2);
        }
        try {
            return d(i);
        } catch (Resources.NotFoundException e) {
            v.a("getStringSafe String resource not found. id: " + i);
            return i2 <= 0 ? "" : d(i2);
        }
    }

    public static String d(int i) {
        if (a == null) {
            a = FlySDK.getContext().getResources();
        }
        return a.getString(i);
    }

    public static String d(int i, int i2) {
        int i3 = i <= 0 ? i2 : i;
        try {
            f(i3);
            i2 = i3;
        } catch (Resources.NotFoundException e) {
            v.a("getResAbsolutePathSafe Resource not found. id: " + i);
        }
        return g(i2);
    }

    public static int e(int i, int i2) throws Resources.NotFoundException {
        if (i <= 0) {
            return i2;
        }
        if (a == null) {
            a = FlySDK.getContext().getResources();
        }
        try {
            a.getAnimation(i);
            return i;
        } catch (Resources.NotFoundException e) {
            v.a("getColorIdSafe Cokor not found. id: " + i);
            return i2;
        }
    }

    public static Drawable e(int i) {
        if (a == null) {
            a = FlySDK.getContext().getResources();
        }
        try {
            return a.getDrawable(i);
        } catch (Throwable th) {
            return null;
        }
    }

    public static String f(int i) {
        if (a == null) {
            a = FlySDK.getContext().getResources();
        }
        return "android.resource://" + a.getResourcePackageName(i) + "/" + a.getResourceTypeName(i) + "/" + a.getResourceEntryName(i);
    }

    public static String g(int i) {
        if (a == null) {
            a = FlySDK.getContext().getResources();
        }
        return a.getResourceTypeName(i) + "/" + a.getResourceEntryName(i);
    }

    public static Drawable h(int i) {
        if (a == null) {
            a = FlySDK.getContext().getResources();
        }
        PackageManager packageManager = FlySDK.getContext().getPackageManager();
        try {
            return packageManager.getApplicationInfo(FlySDK.getContext().getPackageName(), 0).loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            v.a("getIconSafe No icon found");
            try {
                return a.getDrawable(cn.fly.tools.utils.ResHelper.getBitmapRes(FlySDK.getContext(), "ic_launcher"));
            } catch (Throwable th) {
                v.a("getIconSafe No icon named 'ic_launcher' found");
                return a.getDrawable(i);
            }
        }
    }

    public static int i(int i) {
        if (a == null) {
            a = FlySDK.getContext().getResources();
        }
        try {
            return FlySDK.getContext().getPackageManager().getApplicationInfo(FlySDK.getContext().getPackageName(), 0).icon;
        } catch (PackageManager.NameNotFoundException e) {
            v.a("getIconIdSafe No icon found");
            try {
                return cn.fly.tools.utils.ResHelper.getBitmapRes(FlySDK.getContext(), "ic_launcher");
            } catch (Throwable th) {
                v.a("getIconIdSafe No icon named 'ic_launcher' found");
                return i;
            }
        }
    }
}