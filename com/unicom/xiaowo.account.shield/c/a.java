package com.unicom.xiaowo.account.shield.c;

import android.content.Context;
import android.net.Network;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.unicom.xiaowo.account.shield.e.c;
import com.unicom.xiaowo.account.shield.e.e;
import com.unicom.xiaowo.account.shield.e.h;
import com.unicom.xiaowo.account.shield.e.j;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class a {
    private b c;
    private String d;
    private ExecutorService b = Executors.newSingleThreadExecutor();
    private ScheduledExecutorService a = Executors.newScheduledThreadPool(1);

    /* renamed from: com.unicom.xiaowo.account.shield.c.a$a, reason: collision with other inner class name */
    public interface InterfaceC0040a {
        void a(String str);
    }

    public void finalize() {
        if (this.a != null) {
            this.a.shutdownNow();
            this.a = null;
        }
        if (this.b != null) {
            this.b.shutdownNow();
            this.b = null;
        }
        this.c = null;
        this.d = null;
    }

    public void a(Context context, int i, int i2, InterfaceC0040a interfaceC0040a) {
        this.c = new b(interfaceC0040a);
        try {
            a();
            this.a = Executors.newScheduledThreadPool(1);
            this.a.schedule(new Runnable() { // from class: com.unicom.xiaowo.account.shield.c.a.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (a.this) {
                        if (a.this.c != null) {
                            a.this.c.a(10000, "请求超时");
                            a.this.c = null;
                            a.this.a();
                        }
                    }
                }
            }, i, TimeUnit.MILLISECONDS);
            a(context, i2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        try {
            if (this.a != null) {
                this.a.shutdownNow();
                this.a = null;
            }
        } catch (Exception e) {
        }
    }

    private void a(Context context, int i) {
        this.d = com.unicom.xiaowo.account.shield.a.a.a();
        a(context, i, new com.unicom.xiaowo.account.shield.d.a() { // from class: com.unicom.xiaowo.account.shield.c.a.2
            @Override // com.unicom.xiaowo.account.shield.d.a
            public void a(int i2, String str) {
                synchronized (a.this) {
                    if (a.this.c == null) {
                        return;
                    }
                    if (i2 != 0) {
                        if (a.this.c != null) {
                            a.this.c.a(i2, str);
                        }
                    } else {
                        try {
                            JSONObject jSONObject = new JSONObject(str);
                            int iOptInt = jSONObject.optInt("code");
                            String strOptString = jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE);
                            String strOptString2 = jSONObject.optString("data");
                            if (iOptInt == 0) {
                                String strDecode = URLDecoder.decode(com.unicom.xiaowo.account.shield.a.a.a(strOptString2, a.this.d), "UTF-8");
                                if (a.this.c != null) {
                                    a.this.c.a(strOptString, strDecode);
                                }
                            } else if (a.this.c != null) {
                                a.this.c.a(iOptInt, strOptString, strOptString2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (a.this.c != null) {
                                a.this.c.a(10002, "异常" + e.getMessage(), str);
                            }
                        }
                    }
                    a.this.c = null;
                    a.this.a();
                }
            }
        });
    }

    private String a(Context context, int i, String str) throws JSONException {
        String str2;
        try {
            String packageName = context.getPackageName();
            String strB = j.b(context, context.getPackageName());
            if (packageName == null) {
                packageName = "";
            }
            if (strB == null) {
                strB = "";
            }
            String strA = h.a();
            if (i == 2) {
                str2 = "";
            } else {
                str2 = "1";
            }
            String str3 = "" + System.currentTimeMillis();
            String strA2 = com.unicom.xiaowo.account.shield.a.b.a(j.c(context).getBytes());
            String strD = j.d(str);
            String strA3 = j.a(str2 + strA + "30100jsonp" + strA2 + strD + packageName + strB + str3 + "5.2.0AR002B1125" + h.b());
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("client_id", strA);
            jSONObject.put("client_type", "30100");
            jSONObject.put("format", com.unicom.xiaowo.account.shield.a.b.a("jsonp"));
            jSONObject.put("version", com.unicom.xiaowo.account.shield.a.b.a("5.2.0AR002B1125"));
            if (i != 2) {
                jSONObject.put("business_type", com.unicom.xiaowo.account.shield.a.b.a(str2));
            }
            jSONObject.put("packname", com.unicom.xiaowo.account.shield.a.b.a(packageName));
            jSONObject.put("packsign", com.unicom.xiaowo.account.shield.a.b.a(strB));
            jSONObject.put("timeStamp", com.unicom.xiaowo.account.shield.a.b.a(str3));
            jSONObject.put("key", com.unicom.xiaowo.account.shield.a.b.a(strD));
            jSONObject.put("fp", com.unicom.xiaowo.account.shield.a.b.a(strA2));
            jSONObject.put("sign", com.unicom.xiaowo.account.shield.a.b.a(strA3));
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HashMap<String, String> b() {
        HashMap<String, String> map = new HashMap<>();
        map.put("model", Build.MODEL);
        map.put("system", Build.VERSION.RELEASE);
        map.put("woodcock", h.i());
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, int i, String str, final Network network, final com.unicom.xiaowo.account.shield.d.a aVar) {
        synchronized (this) {
            if (this.b == null || this.c == null) {
                return;
            }
            try {
                final String str2 = str + e.a(a(context, i, this.d), "&");
                this.b.submit(new Runnable() { // from class: com.unicom.xiaowo.account.shield.c.a.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            String strA = new com.unicom.xiaowo.account.shield.d.b().a(str2, a.this.b(), network);
                            if (TextUtils.isEmpty(strA)) {
                                aVar.a(10022, "网络请求响应为空");
                            } else {
                                aVar.a(0, strA);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                aVar.a(10009, "10009" + e.getMessage());
            }
        }
    }

    public void a(final Context context, final int i, final com.unicom.xiaowo.account.shield.d.a aVar) {
        try {
            int iA = j.a(context.getApplicationContext());
            h.b(iA);
            if (iA == 1) {
                com.unicom.xiaowo.account.shield.e.c.a().a(context, "https://opencloud.wostore.cn/openapi/netauth/precheck/wp?", new c.a() { // from class: com.unicom.xiaowo.account.shield.c.a.4
                    @Override // com.unicom.xiaowo.account.shield.e.c.a
                    public void a(boolean z, Network network) {
                        if (a.this.c == null) {
                            return;
                        }
                        if (z) {
                            a.this.a(context, i, "https://opencloud.wostore.cn/openapi/netauth/precheck/wp?", network, aVar);
                        } else {
                            aVar.a(10003, "无法切换至数据网络");
                        }
                    }
                });
            } else if (iA == 0) {
                a(context, i, "https://opencloud.wostore.cn/openapi/netauth/precheck/wp?", null, aVar);
            } else {
                aVar.a(10004, "数据网络未开启");
            }
        } catch (Exception e) {
            e.printStackTrace();
            aVar.a(10005, "网络判断异常" + e.getMessage());
        }
    }
}