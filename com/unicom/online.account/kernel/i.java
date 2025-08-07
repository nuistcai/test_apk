package com.unicom.online.account.kernel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class i {
    private static volatile i d = null;
    public Context a;
    public String b = "cuPreGetToken";
    public String c = "checkOk";
    private ExecutorService e = Executors.newSingleThreadExecutor();

    private i() {
    }

    public static i a() {
        if (d == null) {
            synchronized (i.class) {
                if (d == null) {
                    d = new i();
                }
            }
        }
        return d;
    }

    public static String a(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 24:
            case 25:
                return "Type".concat(String.valueOf(i));
            default:
                return "";
        }
    }

    public static String a(int i, int i2, String str, int i3, int i4, int i5, String str2) {
        return ai.a(i, i2, str, i3, i4, i5, str2);
    }

    private static void a(int i, h hVar, String str) throws JSONException {
        aj.c("type:" + i + "\nmsg:" + str);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("resultCode", 1);
            jSONObject.put("resultMsg", str);
            jSONObject.put("resultData", "");
            jSONObject.put("seq", g());
            if (hVar != null) {
                hVar.onResult(jSONObject.toString());
            }
        } catch (Exception e) {
            aj.a(e);
        }
    }

    public static void a(boolean z) {
        aj.a(z);
    }

    public static boolean a(Context context) {
        a();
        k.b(context);
        return k.a(context);
    }

    public static String b() {
        return ac.d();
    }

    public static boolean b(int i) {
        if (1 == i) {
            ac.b(true);
            ac.a(true);
            ac.c(false);
        } else {
            ac.b(false);
            ac.a(false);
            ac.c(true);
        }
        return true;
    }

    public static boolean b(Context context) {
        int iB = al.b(context);
        return iB == 0 || iB == 1;
    }

    static /* synthetic */ int c(int i) {
        return Math.abs(new Random().nextInt() % i);
    }

    public static void c(Context context) {
        j.a(context);
    }

    public static boolean c() {
        return ac.a();
    }

    public static boolean c(String str) {
        if (d(str)) {
            ac.g = str;
            return true;
        }
        ac.g = ak.e;
        return false;
    }

    public static String d() {
        if (an.a(ac.g).booleanValue()) {
            ac.g = ak.e;
        }
        return ac.g;
    }

    private static boolean d(String str) {
        return str.equalsIgnoreCase(ak.f) || str.equalsIgnoreCase(ak.g) || str.equalsIgnoreCase(ak.e) || str.equalsIgnoreCase(ak.h);
    }

    public static String e() {
        return ak.i;
    }

    public static void f() {
        af.a().b();
    }

    public static String g() {
        return "seqEmpty" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6);
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String a(String str) throws Exception {
        char c;
        if (this.a == null) {
            return "sdk 未初始化, context 为空";
        }
        try {
            String lowerCase = str.toLowerCase();
            switch (lowerCase.hashCode()) {
                case -1705644026:
                    if (!lowerCase.equals("testversion")) {
                        c = 65535;
                        break;
                    } else {
                        c = '\t';
                        break;
                    }
                case -1411271163:
                    if (lowerCase.equals("apikey")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1077289829:
                    if (lowerCase.equals("debuginfocosttime")) {
                        c = 14;
                        break;
                    }
                    break;
                case -903629273:
                    if (lowerCase.equals("sha256")) {
                        c = 5;
                        break;
                    }
                    break;
                case -202146594:
                    if (lowerCase.equals("debuginforesult")) {
                        c = '\f';
                        break;
                    }
                    break;
                case -197617279:
                    if (lowerCase.equals("debuginfo")) {
                        c = '\n';
                        break;
                    }
                    break;
                case 107902:
                    if (lowerCase.equals("md5")) {
                        c = 3;
                        break;
                    }
                    break;
                case 113945:
                    if (lowerCase.equals("sm3")) {
                        c = 6;
                        break;
                    }
                    break;
                case 3528965:
                    if (lowerCase.equals("sha1")) {
                        c = 4;
                        break;
                    }
                    break;
                case 93029116:
                    if (lowerCase.equals("appid")) {
                        c = 1;
                        break;
                    }
                    break;
                case 667683678:
                    if (lowerCase.equals("sdkversion")) {
                        c = '\b';
                        break;
                    }
                    break;
                case 781805572:
                    if (lowerCase.equals("deviceinfo")) {
                        c = '\r';
                        break;
                    }
                    break;
                case 909712337:
                    if (lowerCase.equals("packagename")) {
                        c = 2;
                        break;
                    }
                    break;
                case 1183900800:
                    if (lowerCase.equals("debuginfoall")) {
                        c = 11;
                        break;
                    }
                    break;
                case 1285324646:
                    if (lowerCase.equals("bcproviderversion")) {
                        c = 7;
                        break;
                    }
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                    return ak.c();
                case 2:
                    return this.a.getApplicationContext().getPackageName();
                case 3:
                case 4:
                case 5:
                    return al.a(this.a, this.a.getPackageName(), str.toLowerCase());
                case 6:
                    return ac.a ? al.a(this.a, this.a.getPackageName()) : "sdk 未初始化支持国密";
                case 7:
                    return ac.a ? Security.getProvider("BC") != null ? new StringBuilder().append(Security.getProvider("BC").getVersion()).toString() : "Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) is null" : "sdk 未初始化支持国密";
                case '\b':
                    return ac.d();
                case '\t':
                    return ac.e();
                case '\n':
                    return al.a(0);
                case 11:
                    return al.a(1);
                case '\f':
                    return al.a(2);
                case '\r':
                    return ae.a();
                case 14:
                    return al.a(3);
                default:
                    throw new Exception("no info");
            }
        } catch (Exception e) {
            return "no info:" + e.toString();
        }
    }

    public final void a(Context context, int i) {
        j.b(context, a(i), this.b + this.c);
    }

    public final boolean a(Context context, int i, String str, String str2) {
        if (!str.equals(this.b)) {
            return false;
        }
        return j.a(context, a(i), str + this.c, str2);
    }

    public final boolean a(Context context, String str, String str2, boolean z) {
        al.b();
        al.e("cuPreGetToken");
        al.c();
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    if (!TextUtils.isEmpty(ak.c())) {
                        aj.c("不可重复初始化");
                        return false;
                    }
                    ac.c = true;
                    ac.a = z;
                    ac.d = true;
                    if (ac.a) {
                        aj.a(" MyApplication.enableGuoMi  ");
                    }
                    this.a = context.getApplicationContext();
                    ak.c(str);
                    ak.f(al.c(this.a));
                    Context context2 = this.a;
                    String strA = am.a(context2, "auth02");
                    if (TextUtils.isEmpty(strA)) {
                        strA = al.b(UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis());
                        try {
                            SharedPreferences.Editor editorEdit = context2.getSharedPreferences("cu_auth", 0).edit();
                            editorEdit.putString("auth02", strA);
                            editorEdit.commit();
                        } catch (Exception e) {
                            aj.a(e);
                        }
                    }
                    ak.g(strA);
                    ak.e();
                    c(new String(o.b("bXN2Ni53b3Ntcy5jbg==")));
                    aa.a = false;
                    aa.b = false;
                    if (ac.b()) {
                        try {
                            List<d> listB = am.b(this.a, "DNSCache");
                            ak.o = listB;
                            if (listB.isEmpty()) {
                                ak.a(this.a, d());
                            }
                        } catch (Exception e2) {
                            ak.o = new ArrayList();
                        }
                        this.e.submit(new Runnable() { // from class: com.unicom.online.account.kernel.i.1
                            @Override // java.lang.Runnable
                            public final void run() throws UnknownHostException {
                                try {
                                    long jCurrentTimeMillis = System.currentTimeMillis();
                                    aj.a("DNS cache size = " + ak.o.size() + "\n");
                                    for (int i = 0; i < ak.o.size(); i++) {
                                        d dVar = ak.o.get(i);
                                        InetAddress[] allByName = InetAddress.getAllByName(dVar.a);
                                        if (allByName != null && allByName.length > 0) {
                                            dVar.b = allByName[i.c(allByName.length)].getHostAddress();
                                            dVar.c = System.currentTimeMillis() + 259200000;
                                        }
                                        aj.a("DNS cache domain = " + dVar.a + "\n");
                                    }
                                    am.a(i.this.a, "DNSCache", new ArrayList(ak.o));
                                    aj.a("DNS cache refresh time = " + (System.currentTimeMillis() - jCurrentTimeMillis) + "\n");
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                            }
                        });
                    }
                    aj.a(ae.a());
                    ak.h(Build.BRAND);
                    return true;
                }
            } catch (Exception e3) {
                aj.a(e3);
                return false;
            }
        }
        aj.c("初始化参数不能为空");
        return false;
    }

    public final boolean a(String str, int i, int i2, h hVar) throws JSONException {
        if (this.a == null || TextUtils.isEmpty(ak.c()) || TextUtils.isEmpty(ak.d())) {
            a(i2, hVar, "sdk未初始化");
            return false;
        }
        al.b();
        al.e(str);
        al.c();
        ak.c(i);
        return true;
    }

    public final void b(String str) {
        ai.a(this.a, str);
    }
}