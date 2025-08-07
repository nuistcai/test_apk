package com.unicom.xiaowo.account.shield;

import android.content.Context;
import android.text.TextUtils;
import com.unicom.xiaowo.account.shield.c.c;
import com.unicom.xiaowo.account.shield.e.a;
import com.unicom.xiaowo.account.shield.e.g;
import com.unicom.xiaowo.account.shield.e.h;
import com.unicom.xiaowo.account.shield.e.j;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class UniAccountHelper {
    private static volatile UniAccountHelper s_instance = null;
    private Context mContext;

    private UniAccountHelper() {
    }

    public static UniAccountHelper getInstance() {
        if (s_instance == null) {
            synchronized (UniAccountHelper.class) {
                if (s_instance == null) {
                    s_instance = new UniAccountHelper();
                }
            }
        }
        return s_instance;
    }

    public boolean init(Context context, String str, String str2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            g.b("初始化参数不能为空");
            return false;
        }
        if (this.mContext != null) {
            g.b("重复初始化");
            return true;
        }
        this.mContext = context.getApplicationContext();
        h.a(str);
        h.b(str2);
        c.a().a(this.mContext, str, str2);
        h.f(j.b(this.mContext));
        h.g(a.a(this.mContext));
        return true;
    }

    public void releaseNetwork() {
        com.unicom.xiaowo.account.shield.e.c.a().b();
    }

    public void login(int i, ResultListener resultListener) throws JSONException {
        if (this.mContext == null || TextUtils.isEmpty(h.a()) || TextUtils.isEmpty(h.b())) {
            initFail(resultListener, "sdk未初始化");
        } else {
            h.a(i);
            c.a().a(this.mContext, i, 1, resultListener);
        }
    }

    public void mobileAuth(int i, ResultListener resultListener) throws JSONException {
        if (this.mContext == null || TextUtils.isEmpty(h.a()) || TextUtils.isEmpty(h.b())) {
            initFail(resultListener, "sdk未初始化");
        } else {
            h.a(i);
            c.a().a(this.mContext, i, 2, resultListener);
        }
    }

    public void setLogEnable(boolean z) {
        c.a().a(z);
    }

    public String getSdkVersion() {
        return c.b();
    }

    private void initFail(ResultListener resultListener, String str) throws JSONException {
        g.b(str);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("resultCode", 1);
            jSONObject.put("resultMsg", str);
            jSONObject.put("resultData", "");
            jSONObject.put("traceId", "");
            jSONObject.put("operatorType", "CU");
            if (resultListener != null) {
                resultListener.onResult(jSONObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Context getApplicationContext() {
        return this.mContext;
    }
}