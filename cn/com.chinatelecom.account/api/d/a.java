package cn.com.chinatelecom.account.api.d;

import android.content.Context;
import android.net.Network;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import cn.com.chinatelecom.account.api.c.g;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class a {
    private static final String a = a.class.getSimpleName();
    private static HashMap<String, String> b = new HashMap<>();

    public static long a(Context context) {
        return c.b(context, "key_difference_time", 0L);
    }

    public static cn.com.chinatelecom.account.api.c.d a(Context context, HttpURLConnection httpURLConnection, boolean z) {
        String str;
        if (!z) {
            return null;
        }
        cn.com.chinatelecom.account.api.c.d dVar = new cn.com.chinatelecom.account.api.c.d();
        try {
            Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
            List<String> list = headerFields.get("p");
            if (list != null && list.size() > 0) {
                cn.com.chinatelecom.account.api.a.a(a, "request protocol : " + list.get(0));
                dVar.b = false;
            }
            List<String> list2 = headerFields.get("Set-Cookie");
            if (list2 != null && list2.size() > 0) {
                int i = 0;
                while (true) {
                    if (i >= list2.size()) {
                        break;
                    }
                    String str2 = list2.get(0);
                    if (!TextUtils.isEmpty(str2) && str2.contains("gw_auth")) {
                        dVar.a = a(str2, "gw_auth");
                        break;
                    }
                    i++;
                }
            }
            List<String> list3 = headerFields.get("Log-Level");
            if (list3 != null && !list3.isEmpty()) {
                for (int i2 = 0; i2 < list3.size(); i2++) {
                    String str3 = list3.get(0);
                    if (!TextUtils.isEmpty(str3)) {
                        f.a(context, str3);
                    }
                }
            }
            List<String> list4 = headerFields.get("p-reset");
            if (list4 != null && !list4.isEmpty()) {
                String str4 = list4.get(0);
                if (!TextUtils.isEmpty(str4)) {
                    a(context, str4);
                }
            }
            List<String> list5 = headerFields.get("p-ikgx");
            if (list5 != null && !list5.isEmpty()) {
                String str5 = list5.get(0);
                if (!TextUtils.isEmpty(str5)) {
                    dVar.c = str5;
                    g.d = str5;
                }
            }
            List<String> list6 = headerFields.get("dm");
            if (list6 != null && !list6.isEmpty() && (str = list6.get(0)) != null && (str.equals("1") || str.equals("2"))) {
                String strJ = g.j(context);
                if ((!TextUtils.isEmpty(strJ) && !strJ.equals(str)) || (TextUtils.isEmpty(strJ) && str.equals("1"))) {
                    dVar.e = true;
                }
                g.a(context, str);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return dVar;
    }

    public static cn.com.chinatelecom.account.api.c.d a(HttpURLConnection httpURLConnection) {
        cn.com.chinatelecom.account.api.c.d dVar = new cn.com.chinatelecom.account.api.c.d();
        try {
            Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
            List<String> list = headerFields.get("rdt_allow");
            if (list != null && list.size() > 0) {
                dVar.d = list.get(0);
                cn.com.chinatelecom.account.api.a.a(a, "request method : " + dVar.d);
            }
            List<String> list2 = headerFields.get("p-ikgx");
            if (list2 != null && !list2.isEmpty()) {
                String str = list2.get(0);
                if (!TextUtils.isEmpty(str)) {
                    dVar.c = str;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return dVar;
    }

    public static synchronized String a(int i) {
        return i == cn.com.chinatelecom.account.api.b.d ? "presdk" : "preauthIfaa";
    }

    private static String a(String str, String str2) {
        try {
            String[] strArrSplit = str.split(";");
            for (int i = 0; i < strArrSplit.length; i++) {
                if (strArrSplit[i].contains(str2)) {
                    return strArrSplit[i].split("=")[1];
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static JSONObject a(Context context, cn.com.chinatelecom.account.api.c.h hVar, String str, Network network, boolean z, String str2) throws JSONException {
        if (hVar == null || hVar.b == null) {
            return j.b();
        }
        JSONObject jSONObject = hVar.b;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (hVar.a != -1 && !TextUtils.isEmpty(str)) {
            int iOptInt = jSONObject.optInt("result");
            String strOptString = jSONObject.optString("data");
            if (!TextUtils.isEmpty(strOptString)) {
                try {
                    String strA = h.a(strOptString, str);
                    if (strA.isEmpty()) {
                        jSONObject.put("result", 80107);
                        jSONObject.put(NotificationCompat.CATEGORY_MESSAGE, cn.com.chinatelecom.account.api.a.d.a(j.r));
                    } else {
                        JSONObject jSONObject2 = new JSONObject(strA);
                        if (iOptInt == 0) {
                            jSONObject2.put("gwAuth", hVar.c);
                        }
                        if (iOptInt == -10020) {
                            jSONObject.put("taskId", str);
                        }
                        jSONObject.put("data", jSONObject2);
                    }
                } catch (Throwable th) {
                    cn.com.chinatelecom.account.api.a.a(a, "dct", th);
                    jSONObject.put("result", 80107);
                    jSONObject.put(NotificationCompat.CATEGORY_MESSAGE, cn.com.chinatelecom.account.api.a.d.a(j.r));
                    jSONObject.put("data", (Object) null);
                }
            }
            if (iOptInt != 30002 || !z) {
                if (iOptInt == -10009 || iOptInt == -30001) {
                    long jOptLong = jSONObject.optLong("timeStamp", -1L);
                    if (jOptLong == -1) {
                        c(context);
                    } else {
                        a(context, jOptLong);
                    }
                }
                return jSONObject;
            }
            JSONObject jSONObject3 = (JSONObject) jSONObject.opt("data");
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArrayOptJSONArray = jSONObject3.optJSONArray("urls");
            if (jSONArrayOptJSONArray != null) {
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    arrayList.add(jSONArrayOptJSONArray.getString(i));
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            return a(context, arrayList, str, network, str2);
        }
        return jSONObject;
    }

    private static JSONObject a(Context context, List<String> list, String str, Network network, String str2) {
        for (int i = 0; i < list.size(); i++) {
            try {
                String str3 = list.get(i);
                if (!TextUtils.isEmpty(str3)) {
                    if (!g.c(context) && Build.VERSION.SDK_INT < 21) {
                        cn.com.chinatelecom.account.api.c.f.a(context, str3);
                    }
                    g.a aVar = new g.a();
                    try {
                        aVar.b(str2);
                        try {
                            aVar.a(network);
                            JSONObject jSONObjectA = a(context, new cn.com.chinatelecom.account.api.c.b(context).a(str3, "", 0, aVar.a(), false), str, network, false, str2);
                            if (jSONObjectA != null && jSONObjectA.optInt("result") == 0) {
                                return jSONObjectA;
                            }
                        } catch (Throwable th) {
                            th = th;
                            th.printStackTrace();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        return j.b(80001, cn.com.chinatelecom.account.api.a.d.a(j.b) + "- redirect 30002 ");
    }

    private static void a(Context context, long j) {
        if (j > 0) {
            c.a(context, "key_difference_time", j - System.currentTimeMillis());
        }
    }

    private static void a(Context context, String str) {
        c.a(context, "key_p_rset_v4.5.9", str);
    }

    public static String b(Context context) {
        return c.b(context, "key_p_rset_v4.5.9", "0");
    }

    private static void c(Context context) {
        String strA = d.a();
        g.a aVar = new g.a();
        aVar.a("reqTimestamp");
        aVar.b(strA);
        JSONObject jSONObject = new cn.com.chinatelecom.account.api.c.b(context).a(g.b(), "", 1, aVar.a(), false).b;
        if (jSONObject != null) {
            a(context, jSONObject.optLong(NotificationCompat.CATEGORY_MESSAGE, -1L));
        }
    }
}