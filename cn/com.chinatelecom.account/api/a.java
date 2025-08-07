package cn.com.chinatelecom.account.api;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import cn.com.chinatelecom.account.api.d.f;
import cn.com.chinatelecom.account.api.d.g;
import cn.com.chinatelecom.account.api.d.j;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class a {
    public static Context c;
    public static d d;
    private static volatile a h;
    private static final String g = a.class.getSimpleName();
    public static String a = "";
    public static String b = "";
    public static boolean e = false;
    public static Handler f = new Handler(Looper.getMainLooper());

    public static a a() {
        if (h == null) {
            synchronized (a.class) {
                if (h == null) {
                    h = new a();
                }
            }
        }
        return h;
    }

    public static void a(String str, String str2) {
        if (d != null) {
            d.a("CT_" + str, str2);
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (d != null) {
            d.a("CT_" + str, str2, th);
        }
    }

    public static void a(final String str, final JSONObject jSONObject, final c cVar) {
        f.post(new Runnable() { // from class: cn.com.chinatelecom.account.api.a.1
            @Override // java.lang.Runnable
            public void run() {
                if (cVar != null) {
                    try {
                        if (str != null) {
                            jSONObject.put("reqId", str);
                        }
                        cVar.a(jSONObject.toString());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    f.c(str);
                }
            }
        });
    }

    public void a(Context context, String str, String str2, d dVar) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null!");
        }
        if (str == null) {
            throw new IllegalArgumentException("appId must not be null!");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("appSecret must not be null!");
        }
        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        c = context;
        cn.com.chinatelecom.account.api.c.c.a(c);
        a = str;
        b = str2;
        d = dVar;
    }

    public void a(CtSetting ctSetting, int i, c cVar) {
        JSONObject jSONObjectE;
        a(g, "called requestPreLogin()");
        if (cVar == null) {
            return;
        }
        if (c == null || TextUtils.isEmpty(a) || TextUtils.isEmpty(b)) {
            jSONObjectE = j.e();
        } else {
            if (g.b(c)) {
                if (g.c(c)) {
                    new cn.com.chinatelecom.account.api.b.a(c, a, b).a(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.e), ctSetting, i, cVar);
                    return;
                } else if (g.d(c)) {
                    new cn.com.chinatelecom.account.api.b.a(c, a, b).b(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.e), ctSetting, i, cVar);
                    return;
                } else {
                    a((String) null, j.d(), cVar);
                    return;
                }
            }
            jSONObjectE = j.a();
        }
        a((String) null, jSONObjectE, cVar);
    }

    public void a(CtSetting ctSetting, c cVar) {
        a(ctSetting, b.d, cVar);
    }

    public void a(String str, String str2, String str3) {
        g.a = str;
        g.b = str2;
        g.c = str3;
    }

    public boolean b() {
        if (c != null) {
            return g.d(c);
        }
        throw new IllegalArgumentException("Please call the init method");
    }

    public String c() {
        if (c != null) {
            return g.a(c, false);
        }
        throw new IllegalArgumentException("Please call the init method");
    }
}