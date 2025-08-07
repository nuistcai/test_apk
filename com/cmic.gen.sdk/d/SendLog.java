package com.cmic.gen.sdk.d;

import android.content.Context;
import android.text.TextUtils;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.a.UmcConfigBean;
import com.cmic.gen.sdk.c.c.BaseRequest;
import com.cmic.gen.sdk.c.c.RequestCallback;
import com.cmic.gen.sdk.e.LogUtils;
import com.cmic.gen.sdk.e.PackageUtils;
import com.cmic.gen.sdk.e.SharedPreferencesUtil;
import com.cmic.gen.sdk.e.TelephonyUtils;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SendLog.java */
/* renamed from: com.cmic.gen.sdk.d.d, reason: use source file name */
/* loaded from: classes.dex */
public class SendLog {
    private ConcurrentBundle a;

    private static void a(LogBean logBean, ConcurrentBundle concurrentBundle) {
        if (logBean == null || concurrentBundle == null) {
            return;
        }
        logBean.b(concurrentBundle.b("appid", ""));
        logBean.e(TelephonyUtils.a());
        logBean.h(concurrentBundle.b("interfaceType", ""));
        logBean.g(concurrentBundle.b("interfaceCode", ""));
        logBean.f(concurrentBundle.b("interfaceElasped", ""));
        logBean.k(concurrentBundle.b("timeOut"));
        logBean.r(concurrentBundle.b("traceId"));
        logBean.m(concurrentBundle.b("simCardNum"));
        logBean.n(concurrentBundle.b("operatortype"));
        logBean.o(TelephonyUtils.b());
        logBean.p(TelephonyUtils.c());
        logBean.v(String.valueOf(concurrentBundle.b("networktype", 0)));
        logBean.s(concurrentBundle.b("starttime"));
        logBean.t(concurrentBundle.b("endtime"));
        logBean.l(String.valueOf(concurrentBundle.b("systemEndTime", 0L) - concurrentBundle.b("systemStartTime", 0L)));
        logBean.c(concurrentBundle.b("imsiState"));
        logBean.w(SharedPreferencesUtil.b("AID", ""));
        logBean.y(concurrentBundle.b("operatortype"));
        logBean.z(concurrentBundle.b("scripType"));
        logBean.A(concurrentBundle.b("networkTypeByAPI"));
        LogUtils.a("SendLog", "traceId" + concurrentBundle.b("traceId"));
    }

    public void a(Context context, String str, ConcurrentBundle concurrentBundle) {
        JSONArray jSONArray;
        String str2 = "";
        try {
            LogBean logBeanA = concurrentBundle.a();
            String strB = PackageUtils.b(context);
            logBeanA.d(str);
            logBeanA.u(concurrentBundle.b("loginMethod", ""));
            if (concurrentBundle.b("isCacheScrip", false)) {
                logBeanA.q("scrip");
            } else {
                logBeanA.q("pgw");
            }
            logBeanA.i(PackageUtils.a(context));
            if (!TextUtils.isEmpty(strB)) {
                str2 = strB;
            }
            logBeanA.j(str2);
            a(logBeanA, concurrentBundle);
            if (logBeanA.a.size() <= 0) {
                jSONArray = null;
            } else {
                jSONArray = new JSONArray();
                Iterator<Throwable> it = logBeanA.a.iterator();
                while (it.hasNext()) {
                    Throwable next = it.next();
                    StringBuffer stringBuffer = new StringBuffer();
                    JSONObject jSONObject = new JSONObject();
                    for (StackTraceElement stackTraceElement : next.getStackTrace()) {
                        stringBuffer.append("\n").append(stackTraceElement.toString());
                    }
                    jSONObject.put("message", next.toString());
                    jSONObject.put("stack", stringBuffer.toString());
                    jSONArray.put(jSONObject);
                }
                logBeanA.a.clear();
            }
            if (jSONArray != null && jSONArray.length() > 0) {
                logBeanA.a(jSONArray);
            }
            LogUtils.a("SendLog", "登录日志");
            a(logBeanA.b(), concurrentBundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void a(JSONObject jSONObject, ConcurrentBundle concurrentBundle) {
        this.a = concurrentBundle;
        a(jSONObject);
    }

    private void a(JSONObject jSONObject) {
        BaseRequest.a().a(jSONObject, this.a, new RequestCallback() { // from class: com.cmic.gen.sdk.d.d.1
            @Override // com.cmic.gen.sdk.c.c.RequestCallback
            public void a(String str, String str2, JSONObject jSONObject2) {
                UmcConfigBean umcConfigBeanB = SendLog.this.a.b();
                HashMap map = new HashMap();
                if (!str.equals("103000")) {
                    if (umcConfigBeanB.l() != 0 && umcConfigBeanB.k() != 0) {
                        int iA = SharedPreferencesUtil.a("logFailTimes", 0) + 1;
                        if (iA < umcConfigBeanB.k()) {
                            map.put("logFailTimes", Integer.valueOf(iA));
                        } else {
                            map.put("logFailTimes", 0);
                            map.put("logCloseTime", Long.valueOf(System.currentTimeMillis()));
                        }
                    }
                } else {
                    map.put("logFailTimes", 0);
                    map.put("logCloseTime", 0L);
                }
                SharedPreferencesUtil.a(map);
            }
        });
    }
}