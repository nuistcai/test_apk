package com.unicom.xiaowo.account.shield.c;

import android.content.Context;
import android.text.TextUtils;
import com.unicom.xiaowo.account.shield.ResultListener;
import com.unicom.xiaowo.account.shield.c.a;
import com.unicom.xiaowo.account.shield.e.f;
import com.unicom.xiaowo.account.shield.e.g;
import com.unicom.xiaowo.account.shield.e.h;
import com.unicom.xiaowo.account.shield.e.i;
import com.unicom.xiaowo.account.shield.e.l;
import com.unicom.xiaowo.account.shield.e.m;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class c {
    private static volatile c a = null;

    private c() {
    }

    public static c a() {
        if (a == null) {
            synchronized (c.class) {
                if (a == null) {
                    a = new c();
                }
            }
        }
        return a;
    }

    public static String b() {
        return "5.2.0AR002B1125";
    }

    public void a(Context context, String str, String str2) {
        try {
            if (a(context)) {
                b(context, str, str2);
            } else {
                i.d(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean a(Context context) throws IOException {
        int i;
        InputStream inputStreamB = null;
        try {
            try {
                inputStreamB = i.b(context);
                byte[] bArrA = i.a(inputStreamB);
                String strA = i.a(bArrA);
                byte[] bArrA2 = l.a();
                if (TextUtils.isEmpty(strA)) {
                    i = 0;
                } else if (a("3.0.0A0000B0508", strA) != 1) {
                    g.b("Different SDK");
                    i = 0;
                } else {
                    i = "3.0.0A0000B0508".compareTo(strA) > 0 ? 0 : 1;
                }
                g.b("read " + i);
                if (i == 0) {
                    i.b(context, bArrA2);
                } else {
                    i.a(context, bArrA);
                }
                i.a(context, i.a(context));
                i.c(context);
                if (i == 0) {
                    i.d(context);
                }
                if (inputStreamB != null) {
                    try {
                        inputStreamB.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (inputStreamB != null) {
                    try {
                        inputStreamB.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            if (inputStreamB != null) {
                try {
                    inputStreamB.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    private void b(Context context, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Class clsLoadClass = i.a().loadClass(new String(m.b("Y29tLnVuaWNvbS54aWFvd28ubG9naW5jb3JlLlVuaUF1dGhIZWxwZXI=")));
            clsLoadClass.getMethod("init", Context.class, String.class, String.class).invoke(clsLoadClass.getMethod("getInstance", new Class[0]).invoke(clsLoadClass, new Object[0]), context, str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            i.d(context);
        }
    }

    public void a(Context context, int i, int i2, final ResultListener resultListener) {
        new a().a(context, i, i2, new a.InterfaceC0040a() { // from class: com.unicom.xiaowo.account.shield.c.c.1
            @Override // com.unicom.xiaowo.account.shield.c.a.InterfaceC0040a
            public void a(String str) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    int iOptInt = jSONObject.optInt("resultCode");
                    String strOptString = jSONObject.optString("resultMsg");
                    if (iOptInt >= 10000 && iOptInt <= 10099) {
                        if (iOptInt == 10000) {
                            strOptString = "" + h.c();
                        }
                        new f().a("" + iOptInt, strOptString);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resultListener.onResult(str);
            }
        });
    }

    public void a(boolean z) {
        g.a(z);
    }

    private int a(String str, String str2) {
        try {
            byte[] bytes = str.getBytes();
            byte[] bytes2 = str2.getBytes();
            for (int i = 0; i < 5; i++) {
                int i2 = 5 + i;
                if (bytes[i2] != bytes2[i2]) {
                    return 0;
                }
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}