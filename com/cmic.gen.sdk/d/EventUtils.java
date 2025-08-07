package com.cmic.gen.sdk.d;

import android.content.Context;
import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.e.PackageUtils;
import com.cmic.gen.sdk.e.TelephonyUtils;
import com.cmic.gen.sdk.e.ThreadUtils;
import com.cmic.gen.sdk.e.TimeUtils2;
import com.cmic.gen.sdk.view.LoginAuthParameter;
import java.util.HashMap;
import org.json.JSONObject;

/* compiled from: EventUtils.java */
/* renamed from: com.cmic.gen.sdk.d.a, reason: use source file name */
/* loaded from: classes.dex */
public class EventUtils {
    private static a<String, String> a = new a<>();

    public static void a() {
        String strValueOf = String.valueOf(0);
        a.put("authPageIn", strValueOf);
        a.put("authPageOut", strValueOf);
        a.put("authClickFailed", strValueOf);
        a.put("authClickSuccess", strValueOf);
        a.put("timeOnAuthPage", strValueOf);
        a.put("PrivacyNotSelectedToast", strValueOf);
        a.put("PrivacyNotSelectedPopup", strValueOf);
        a.put("PrivacyNotSelectedCustom", strValueOf);
        a.put("PrivacyNotSelectedShake", strValueOf);
    }

    public static void a(String str) throws NumberFormatException {
        int i;
        try {
            String str2 = a.get(str);
            if (TextUtils.isEmpty(str2)) {
                i = 0;
            } else {
                i = Integer.parseInt(str2);
            }
            a.put(str, String.valueOf(i + 1));
            a.put(str + "Time", TimeUtils2.a());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(String str, String str2) {
        a.put(str, str2);
    }

    public static void a(Context context, final ConcurrentBundle concurrentBundle) {
        try {
            if (concurrentBundle.b().j()) {
                return;
            }
            LoginAuthParameter loginAuthParameter = new LoginAuthParameter();
            String strValueOf = String.valueOf(0);
            loginAuthParameter.j(!a.a("authPageIn", strValueOf).equals(strValueOf) ? a.get("authPageIn") : null);
            loginAuthParameter.k(!a.a("authPageOut", strValueOf).equals(strValueOf) ? a.get("authPageOut") : null);
            loginAuthParameter.c(!a.a("authClickSuccess", strValueOf).equals(strValueOf) ? a.get("authClickSuccess") : null);
            loginAuthParameter.b(!a.a("authClickFailed", strValueOf).equals(strValueOf) ? a.get("authClickFailed") : null);
            loginAuthParameter.d(!a.a("timeOnAuthPage", strValueOf).equals(strValueOf) ? a.get("timeOnAuthPage") : null);
            loginAuthParameter.e(!a.a("PrivacyNotSelectedToast", strValueOf).equals(strValueOf) ? a.get("PrivacyNotSelectedToast") : null);
            loginAuthParameter.f(!a.a("PrivacyNotSelectedPopup", strValueOf).equals(strValueOf) ? a.get("PrivacyNotSelectedPopup") : null);
            loginAuthParameter.g(!a.a("PrivacyNotSelectedShake", strValueOf).equals(strValueOf) ? a.get("PrivacyNotSelectedShake") : null);
            loginAuthParameter.h(a.a("PrivacyNotSelectedCustom", strValueOf).equals(strValueOf) ? null : a.get("PrivacyNotSelectedCustom"));
            loginAuthParameter.a(a.a("authPrivacyState", strValueOf));
            loginAuthParameter.i(concurrentBundle.b("displayLogo"));
            JSONObject jSONObjectA = loginAuthParameter.a();
            final LogBeanEx logBeanEx = new LogBeanEx();
            logBeanEx.b(concurrentBundle.b("appid", ""));
            logBeanEx.r(concurrentBundle.b("traceId"));
            logBeanEx.b(concurrentBundle.b("appid"));
            logBeanEx.i(PackageUtils.a(context));
            logBeanEx.j(PackageUtils.b(context));
            logBeanEx.k(concurrentBundle.b("timeOut"));
            logBeanEx.s(a.a("authPageInTime", ""));
            logBeanEx.t(a.a("authPageOutTime", ""));
            logBeanEx.u("eventTracking5");
            logBeanEx.n(concurrentBundle.b("operatortype", ""));
            logBeanEx.v(concurrentBundle.b("networktype", 0) + "");
            logBeanEx.e(TelephonyUtils.a());
            logBeanEx.o(TelephonyUtils.b());
            logBeanEx.p(TelephonyUtils.c());
            logBeanEx.m(concurrentBundle.b("simCardNum"));
            logBeanEx.a(jSONObjectA);
            logBeanEx.c(concurrentBundle.b("imsiState", "0"));
            logBeanEx.l((System.currentTimeMillis() - concurrentBundle.b("methodTimes", 0L)) + "");
            ThreadUtils.a(new ThreadUtils.a() { // from class: com.cmic.gen.sdk.d.a.1
                @Override // com.cmic.gen.sdk.e.ThreadUtils.a
                protected void a() {
                    new SendLog().a(logBeanEx.b(), concurrentBundle);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* compiled from: EventUtils.java */
    /* renamed from: com.cmic.gen.sdk.d.a$a */
    private static class a<K, V> extends HashMap<K, V> {
        private a() {
        }

        public V a(Object obj, V v) {
            if (containsKey(obj) && get(obj) != null) {
                return get(obj);
            }
            return v;
        }
    }
}