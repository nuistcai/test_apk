package cn.com.chinatelecom.account.sdk.a;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.widget.TextView;
import cn.com.chinatelecom.account.api.d.f;
import cn.com.chinatelecom.account.api.d.g;
import cn.com.chinatelecom.account.api.d.j;
import cn.com.chinatelecom.account.sdk.PrivacyAgreementConfig;
import cn.com.chinatelecom.account.sdk.ResultListener;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class a {
    private static final String a = a.class.getSimpleName();
    private static e b = null;
    private static e c = null;
    private static volatile a d;
    private ResultListener e;
    private Context f;
    private String g = "";
    private boolean h = false;

    public static a a() {
        if (d == null) {
            synchronized (a.class) {
                if (d == null) {
                    d = new a();
                }
            }
        }
        return d;
    }

    private String g() {
        synchronized (a.class) {
            if (b == null || b.b() == null) {
                return "CT";
            }
            return b.b();
        }
    }

    public Point a(Activity activity) {
        Point point = new Point();
        try {
            activity.getWindowManager().getDefaultDisplay().getSize(point);
            int i = point.x;
            int i2 = point.y;
            return point;
        } catch (Exception e) {
            e.printStackTrace();
            return point;
        }
    }

    public String a(PrivacyAgreementConfig privacyAgreementConfig) {
        String strG = g();
        return (!strG.equals("CM") || privacyAgreementConfig == null || TextUtils.isEmpty(privacyAgreementConfig.chinaMobileTitle)) ? (!strG.equals("CU") || privacyAgreementConfig == null || TextUtils.isEmpty(privacyAgreementConfig.chinaUnicomTitle)) ? b.a() : privacyAgreementConfig.chinaUnicomTitle : privacyAgreementConfig.chinaMobileTitle;
    }

    public void a(Context context) {
        if (b == null || b.e() == null) {
            a(j.g());
            return;
        }
        try {
            a(c.a(b.e(), cn.com.chinatelecom.account.api.b.a(context, b.d()).toLowerCase()));
        } catch (Throwable th) {
            a(j.h());
            cn.com.chinatelecom.account.api.a.a(a, "login exception ", th);
            f.a(this.g).g("login exception : " + th.getMessage()).b(g.f(context));
        }
        f();
    }

    public void a(Context context, String str, TextView textView) {
        try {
            c = c.a(str);
            a(c.a() == 0 ? c.a(c.e(), cn.com.chinatelecom.account.api.b.a(context, c.d()).toLowerCase()) : !TextUtils.isEmpty(c.e()) ? c.e() : j.h());
        } catch (Throwable th) {
            a(j.h());
            cn.com.chinatelecom.account.api.a.a(a, "login  exception 2", th);
            f.a(this.g).g("login exception 2 : " + th.getMessage());
        }
        f();
    }

    public void a(Context context, boolean z, String str) {
        this.f = context.getApplicationContext();
        this.h = z;
        this.g = str;
    }

    public synchronized void a(ResultListener resultListener) {
        this.e = resultListener;
    }

    public synchronized void a(String str) {
        if (this.h) {
            return;
        }
        if (this.e != null) {
            try {
                cn.com.chinatelecom.account.api.a.a(a, str);
                JSONObject jSONObject = new JSONObject(str);
                jSONObject.put("reqId", this.g);
                this.e.onResult(jSONObject.toString());
                this.h = true;
                this.e = null;
                f.a(this.g).f(g.d).b(g.f(this.f));
                f.b(this.g, jSONObject, "");
                f.c(this.g);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void a(String str, ResultListener resultListener) {
        if (resultListener != null) {
            b = c.a(str);
            resultListener.onResult(b.f());
        }
    }

    public String b(PrivacyAgreementConfig privacyAgreementConfig) {
        String strG = g();
        return (!strG.equals("CM") || privacyAgreementConfig == null || TextUtils.isEmpty(privacyAgreementConfig.chinaMobileUrl)) ? (!strG.equals("CU") || privacyAgreementConfig == null || TextUtils.isEmpty(privacyAgreementConfig.chinaUnicomUrl)) ? b.b() : privacyAgreementConfig.chinaUnicomUrl : privacyAgreementConfig.chinaMobileUrl;
    }

    public boolean b() {
        synchronized (a.class) {
            if (b == null) {
                return false;
            }
            return b.g();
        }
    }

    public String c() {
        synchronized (a.class) {
            if (b == null || b.c() == null) {
                return "以本机号码登录";
            }
            return b.c();
        }
    }

    public String d() {
        String strG = g();
        return strG.equals("CM") ? b.d() : strG.equals("CU") ? b.e() : b.c();
    }

    public boolean e() {
        String strG = g();
        if (strG == null || !strG.equals("CM")) {
            return strG != null && strG.equals("CU");
        }
        return true;
    }

    public void f() {
        synchronized (a.class) {
            b = null;
            c = null;
        }
    }
}