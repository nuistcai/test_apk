package com.unicom.online.account.kernel;

import android.content.Context;
import android.util.Log;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class ai {
    private static ExecutorService a = Executors.newSingleThreadExecutor();
    private static ExecutorService b = Executors.newCachedThreadPool();

    public static String a(int i, int i2, String str, int i3, int i4, final int i5, final String str2) {
        if (!ac.a()) {
            return "";
        }
        if (an.a(str).booleanValue()) {
            str = i.g();
        }
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("result", Integer.valueOf(i));
            linkedHashMap.put("type", Integer.valueOf(i2));
            linkedHashMap.put("os", 1);
            linkedHashMap.put("apiKey", ak.c());
            linkedHashMap.put("seq", str);
            linkedHashMap.put("ret_code", Integer.valueOf(i3));
            linkedHashMap.put("sdk_v", ac.d());
            linkedHashMap.put("setTime", Integer.valueOf(i4));
            linkedHashMap.put("netType", Integer.valueOf(ak.a()));
            linkedHashMap.put("access_process", new LinkedHashMap<String, Object>() { // from class: com.unicom.online.account.kernel.ai.3
                {
                    put("ifProtal", new StringBuilder().append(e.a().a).toString());
                    put("InitTime", new StringBuilder().append(e.a().c).toString());
                    put("forcedTime", "1");
                    if (e.a().e != null) {
                        put("step1", e.a().e.a());
                    }
                    if (e.a().f != null) {
                        put("step2", e.a().f.a());
                    }
                    if (e.a().g != null) {
                        put("step3", e.a().g.a());
                    }
                    if (e.a().h != null) {
                        put("step4", e.a().h.a());
                    }
                }
            });
            linkedHashMap.put("err_info", new LinkedHashMap<String, Object>() { // from class: com.unicom.online.account.kernel.ai.4
                {
                    put("err_code", Integer.valueOf(i5));
                    put("err_msg", str2);
                }
            });
            return new JSONObject(linkedHashMap).toString();
        } catch (Exception e) {
            aj.a(e);
            return "";
        }
    }

    private static String a(TreeMap<String, Object> treeMap) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value != null && an.b(key).booleanValue()) {
                    sb.append(key).append("=").append(URLEncoder.encode(value.toString(), "UTF-8")).append("&");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (Exception e) {
            Log.e("TAG", "getURLParames: " + e.getMessage());
            throw new Exception("http请求参数出错");
        }
    }

    public static void a(final Context context, final String str) {
        if (ac.a()) {
            final ao aoVar = new ao();
            FutureTask futureTask = new FutureTask(new Runnable() { // from class: com.unicom.online.account.kernel.ai.1
                @Override // java.lang.Runnable
                public final void run() {
                    String str2;
                    String strB = ai.b(str);
                    if (an.b(strB).booleanValue()) {
                        String str3 = "https://" + ak.j + ak.k;
                        ao aoVar2 = aoVar;
                        new aa();
                        aoVar2.a = aa.a(str3, strB);
                        aoVar.b = strB;
                        str2 = "日志管理回复:" + aoVar.a;
                    } else {
                        str2 = "日志数据-上传异常:";
                    }
                    aj.a(str2);
                }
            }, aoVar);
            try {
                a.execute(futureTask);
                ao aoVar2 = (ao) futureTask.get();
                if (aoVar2.a.contains("\"code\":0")) {
                    b.execute(new FutureTask(new Runnable() { // from class: com.unicom.online.account.kernel.ai.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            String strA = l.a(context);
                            if (strA == null) {
                                return;
                            }
                            String str2 = "https://" + ak.j + ak.k;
                            new aa();
                            if (aa.a(str2, strA).contains("\"code\":0")) {
                                l.a(context, 1);
                            }
                        }
                    }, aoVar));
                } else {
                    l.a(context, aoVar2.b);
                }
                e.i.a = 0;
                e.i.b = 0L;
                e.i.c = 0L;
                if (e.i.e != null) {
                    e.i.e.b();
                }
                if (e.i.f != null) {
                    e.i.f.b();
                }
                if (e.i.g != null) {
                    e.i.g.b();
                }
                if (e.i.h != null) {
                    e.i.h.b();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str) {
        try {
            String strD = ak.d();
            String strA = al.a(str, strD.substring(0, 16), strD.substring(16, 32));
            String strA2 = s.a(strD, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqlsahFVMRr61qP/zUqXJlhwhEAR6ynO9ldpawnABDwDiHDymSzsSyyHKE2mS3178d0gGJ5v0TnpNh3+IiOQknpRUvAvXeA9P8DkCNBZBSwCEHo74xQ1+TLMk7f0Qn45GfMipLO0ryYCR4Xg4zE8TubqJ2oyy9pi/QoBdtjx+vJwIDAQAB");
            String strB = al.b(ak.k + "?params=" + strA + "&paramsKey=" + strA2);
            TreeMap treeMap = new TreeMap();
            HashMap map = new HashMap(16);
            treeMap.put("params", strA);
            treeMap.put("paramsKey", strA2);
            treeMap.put("sign", strB);
            map.put("sign", strB);
            map.put("api-protocol", "1.1");
            try {
                return a((TreeMap<String, Object>) treeMap);
            } catch (Exception e) {
                aj.a(e);
                return "";
            }
        } catch (Exception e2) {
            aj.a(e2);
            Log.d("TAG", "run: " + e2.toString());
            return null;
        }
    }
}