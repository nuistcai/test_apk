package com.unicom.online.account.kernel;

import android.content.Context;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.unicom.online.account.kernel.af;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class x {
    y a;
    private ExecutorService c = Executors.newSingleThreadExecutor();
    private ScheduledExecutorService b = Executors.newScheduledThreadPool(1);

    static String a(Context context, int i) throws JSONException {
        String str;
        String strA;
        String strA2;
        String strB;
        try {
            String strC = ak.c();
            String string = new StringBuilder().append(System.currentTimeMillis()).toString();
            String strD = ac.d();
            String packageName = context.getPackageName();
            String strA3 = ad.a(context, al.g());
            String strA4 = al.a();
            String strSubstring = strA4.substring(0, 16);
            String strSubstring2 = strA4.substring(16, 32);
            e.a().c = System.currentTimeMillis();
            if (ac.a) {
                str = "3.1";
                strA = o.a(r.a(strA3.getBytes("Utf-8"), strSubstring.getBytes(), strSubstring2.getBytes()));
                strA2 = o.a(r.a(strA4.getBytes(), p.a("045C5DD4890819CEB16B0A66ED62B2FFA29B08F3CBF344A52A3A100ECB271BBEF3A9BC3743E753CA16EF238A1E55B72E95659A70425064D506B48F8EE3442786F7")));
            } else {
                str = "2.1";
                strA = al.a(strA3, strSubstring, strSubstring2);
                strA2 = s.a(strA4, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVc1ecjpc5k7TkabF935iQONDZ0/E5XWPVv9FEsI59XTRW0+BCMK1MODRSWMvHFrPMh9ZilnRr7qXuAKCBEynQEghmpIVvMYhFu48FAI9bKfkI5lKuQK+tc4X0+zTbNrpedNoKXK4C7dDjTETBH6prwWE9j5WsAf0gbjUbIs3FxwIDAQAB");
            }
            String strA5 = ac.e.equalsIgnoreCase("sm3") ? al.a(context, context.getPackageName()) : al.a(context, context.getPackageName(), ac.e);
            if (ac.a(i)) {
                strB = ak.b();
                if (!TextUtils.isEmpty(strB)) {
                    strB = "0";
                }
            } else {
                strB = "";
            }
            String strReplaceAll = (strA5 + "\n" + strC + "\n" + str + "\njson\n" + strB + "\n" + packageName + "\n" + strA + "\n" + strD + "\n" + strA2 + "\n" + string).replaceAll("\n", "");
            String strC2 = ac.a ? al.c(strReplaceAll) : al.a(strReplaceAll);
            String strA6 = o.a(strA);
            String strA7 = o.a(strA2);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("androidMd5", strA5);
            jSONObject.put("apiKey", strC);
            jSONObject.put("apiVersion", str);
            jSONObject.put("format", "json");
            if (ac.a(i)) {
                jSONObject.put("operator", strB);
            }
            jSONObject.put("packName", packageName);
            jSONObject.put("privateIp", strA6);
            jSONObject.put("sdkVersion", strD);
            jSONObject.put("secretKey", strA7);
            jSONObject.put("timeStamp", string);
            jSONObject.put("sign", strC2);
            return jSONObject.toString();
        } catch (Exception e) {
            aj.a(e);
            return "";
        }
    }

    private void a(final Context context, final int i, final z zVar) {
        try {
            int iB = al.b(context.getApplicationContext());
            ak.d(iB);
            if (iB == 1) {
                final long jCurrentTimeMillis = System.currentTimeMillis();
                af.a().a(context, new af.a() { // from class: com.unicom.online.account.kernel.x.3
                    @Override // com.unicom.online.account.kernel.af.a
                    public final void a(boolean z, Object obj) {
                        if (!z) {
                            zVar.a(i, 410003, "无法切换至数据网络");
                            return;
                        }
                        long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                        e.a().b = jCurrentTimeMillis2;
                        aj.a("select Data Channel cost:" + jCurrentTimeMillis2 + "ms");
                        x.this.a(context, i, obj, zVar);
                    }
                });
            } else if (iB != 0) {
                zVar.a(i, 410004, "数据网络未开启");
            } else {
                e.a().b = 0L;
                a(context, i, (Object) null, zVar);
            }
        } catch (Exception e) {
            aj.a(e);
            zVar.a(i, 410005, "网络判断异常" + e.getMessage());
        }
    }

    static /* synthetic */ void a(x xVar) {
        try {
            if (xVar.b != null) {
                xVar.b.shutdownNow();
                xVar.b = null;
            }
        } catch (Exception e) {
            aj.a(e);
        }
    }

    private void b(Context context, int i) {
        a(context, i, new z() { // from class: com.unicom.online.account.kernel.x.2
            @Override // com.unicom.online.account.kernel.z
            public final void a(int i2, int i3, String str) {
                synchronized (x.this) {
                    if (x.this.a == null) {
                        return;
                    }
                    if (i3 == 1) {
                        try {
                            JSONObject jSONObject = new JSONObject(str);
                            int iOptInt = jSONObject.optInt("code");
                            String strOptString = jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE);
                            String strOptString2 = jSONObject.optString("data");
                            String strOptString3 = jSONObject.optString("seq");
                            if (an.a(strOptString3).booleanValue()) {
                                strOptString3 = i.g();
                            }
                            if (iOptInt == 100) {
                                String strA = al.a();
                                String strSubstring = strA.substring(0, 16);
                                String strSubstring2 = strA.substring(16, 32);
                                String str2 = ac.a ? new String(r.b(o.b(strOptString2), strSubstring.getBytes(), strSubstring2.getBytes())) : URLDecoder.decode(al.b(strOptString2, strSubstring, strSubstring2), "UTF-8");
                                if (TextUtils.isEmpty(str2)) {
                                    aj.a(2, "\nmsg=" + strOptString + "\ndata=" + strOptString2 + "\nseq=" + strOptString3 + "\n");
                                    x.this.a.a(i2, 410002, "返回数据异常", strOptString2, strOptString3);
                                } else {
                                    aj.a(2, "\nmsg=" + strOptString + "\ncontent=" + str2 + "\nseq=" + strOptString3 + "\n");
                                    y yVar = x.this.a;
                                    try {
                                        if (yVar.a != null) {
                                            if (an.a(strOptString3).booleanValue()) {
                                                strOptString3 = i.g();
                                            }
                                            JSONObject jSONObject2 = new JSONObject();
                                            jSONObject2.put("resultCode", 100);
                                            jSONObject2.put("resultMsg", strOptString);
                                            jSONObject2.put("seq", strOptString3);
                                            if (TextUtils.isEmpty(str2)) {
                                                jSONObject2.put("resultData", "");
                                            } else {
                                                try {
                                                    jSONObject2.put("resultData", new JSONObject(str2));
                                                } catch (JSONException e) {
                                                    jSONObject2.put("resultData", str2);
                                                }
                                                if (ac.b(i2)) {
                                                    jSONObject2.put("exp", System.currentTimeMillis() + 60000);
                                                }
                                            }
                                            yVar.a.onResult(jSONObject2.toString());
                                            yVar.a = null;
                                        }
                                    } catch (Exception e2) {
                                        aj.a(e2);
                                    }
                                }
                            } else {
                                if (iOptInt == -2 && !TextUtils.isEmpty(ak.f())) {
                                    strOptString = strOptString + "apn is " + ak.f();
                                }
                                String str3 = strOptString;
                                aj.a(2, "\nmsg=" + str3 + "\ndata=" + strOptString2 + "\nseq=" + strOptString3 + "\n");
                                x.this.a.a(i2, iOptInt, str3, strOptString2, strOptString3);
                            }
                        } catch (Exception e3) {
                            aj.a(2, "\nresponse=" + str + "\n");
                            x.this.a.a(i2, 410002, "返回数据异常" + e3.getMessage(), str, "");
                        }
                    } else {
                        aj.a(2, "\nresponse=" + str + "\n");
                        x.this.a.a(i2, i3, str, "", i.g());
                    }
                    x.this.a = null;
                    x.a(x.this);
                }
            }
        });
    }

    public final void a(Context context, int i, final int i2, h hVar) {
        this.a = new y();
        this.a.a = hVar;
        try {
            this.b.schedule(new Runnable() { // from class: com.unicom.online.account.kernel.x.1
                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (x.this) {
                        if (x.this.a != null) {
                            x.this.a.a(i2, 410000, "请求超时", "", i.g());
                            x.this.a = null;
                            aj.a(3, "end:" + System.currentTimeMillis());
                            x.a(x.this);
                        }
                    }
                }
            }, i, TimeUnit.MILLISECONDS);
            b(context, i2);
        } catch (Exception e) {
            aj.a(e);
        }
    }

    final void a(final Context context, final int i, final Object obj, final z zVar) {
        synchronized (this) {
            try {
                this.c.submit(new Runnable() { // from class: com.unicom.online.account.kernel.x.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        try {
                            switch (i) {
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 24:
                                case 25:
                                    String str = ak.b(i) + ah.a(x.a(context, i), "&");
                                    e.a().d = 0;
                                    aa aaVar = new aa();
                                    Context context2 = context;
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("user-agent", "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1");
                                    map.put("netType", "2");
                                    map.put("os", "android");
                                    map.put("Accept", "*/*");
                                    String strA = aaVar.a(context2, str, map, obj);
                                    if (ak.h() == 1) {
                                        try {
                                            af.a().b();
                                            aj.a("\ncall releaseNetwork() \n");
                                        } catch (Exception e) {
                                            aj.a(e);
                                        }
                                    }
                                    if (TextUtils.isEmpty(strA)) {
                                        zVar.a(i, 410002, "返回数据异常");
                                        return;
                                    } else {
                                        zVar.a(i, 1, strA);
                                        return;
                                    }
                                default:
                                    zVar.a(i, 410009, "410009no this type");
                                    return;
                            }
                        } catch (Exception e2) {
                            aj.a(e2);
                        }
                        aj.a(e2);
                    }
                });
            } catch (Exception e) {
                zVar.a(i, 410009, "410009" + e.getMessage());
            }
        }
    }
}