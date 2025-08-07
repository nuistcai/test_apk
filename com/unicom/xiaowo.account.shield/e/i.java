package com.unicom.xiaowo.account.shield.e;

import android.content.Context;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/* loaded from: classes.dex */
public class i {
    private static final String a = File.separator + new String(m.b("LnVuaWFjY291bnQ=")) + File.separator;
    private static final String b = new String(m.b("dW5pYWNjb3VudC5qYXI="));
    private static final String c = new String(m.b("dW5pY29tX3VwZGF0ZQ==")) + File.separator + new String(m.b("dW5pYWNjb3VudF9jb3JlLmRhdA=="));
    private static final String d = new String(m.b("dW5pYWNjb3VudF9jb3JlLmRhdA=="));
    private static DexClassLoader e = null;

    private static String e(Context context) {
        return context.getFilesDir().getParent() + a;
    }

    public static String a(Context context) {
        return e(context) + b;
    }

    public static DexClassLoader a() {
        return e;
    }

    public static InputStream b(Context context) {
        try {
            String str = e(context) + c;
            File file = new File(str);
            if (file.exists()) {
                g.a("find:" + str);
                return new FileInputStream(file);
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] a(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            return b.a(inputStream);
        } catch (Exception e2) {
            return null;
        }
    }

    public static DexClassLoader a(Context context, String str) {
        try {
            String str2 = e(context) + "optdex";
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            e = new DexClassLoader(str, str2, null, context.getClassLoader());
            return e;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean a(Context context, byte[] bArr) throws Throwable {
        try {
            int length = bArr.length - 16;
            int i = (length >> 2) << 2;
            byte[] bArr2 = new byte[i];
            byte[] bArr3 = new byte[length];
            for (int i2 = 0; i2 < i; i2++) {
                bArr2[i2] = bArr[i2 + 16];
            }
            for (int i3 = 0; i3 < length; i3++) {
                bArr3[i3] = bArr[i3 + 16];
            }
            byte[] bArrA = m.a(bArr2);
            for (int i4 = 0; i4 < i; i4++) {
                bArr3[i4] = bArrA[i4];
            }
            File file = new File(e(context));
            if (!file.exists()) {
                file.mkdirs();
            }
            String strA = a(context);
            File file2 = new File(strA);
            if (file2.exists()) {
                file2.delete();
            }
            file2.createNewFile();
            b.a(bArr3, strA);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void b(Context context, byte[] bArr) throws Throwable {
        try {
            File file = new File(e(context));
            if (!file.exists()) {
                file.mkdirs();
            }
            String strA = a(context);
            File file2 = new File(strA);
            if (file2.exists()) {
                file2.delete();
            }
            file2.createNewFile();
            b.a(bArr, strA);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void c(Context context) {
        try {
            b.a(new File(a(context)));
        } catch (Exception e2) {
        }
    }

    public static void d(Context context) {
        try {
            String str = e(context) + c;
            File file = new File(str);
            if (file.exists()) {
                g.a("delete " + str + " result:" + file.delete());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        try {
            byte[] bArr2 = new byte[15];
            for (int i = 0; i < 15; i++) {
                bArr2[i] = bArr[i];
            }
            return new String(bArr2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }
}