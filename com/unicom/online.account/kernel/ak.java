package com.unicom.online.account.kernel;

import android.content.Context;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONException;

/* loaded from: classes.dex */
public final class ak {
    public static List<d> o;
    public static int q;
    public static String a = "123.125.99.31";
    public static final String b = new String(o.b("MTAwMTAuY29t"));
    public static final String c = new String(o.b("aWQ2Lm1l"));
    public static final String d = new String(o.b("Y21wYXNzcG9ydC5jb20="));
    public static final String e = new String(o.b("bXN2Ni53b3Ntcy5jbg=="));
    public static final String f = new String(o.b("YWxpLndvc21zLmNu"));
    public static final String g = new String(o.b("bS56enguY25rbG9nLmNvbQ=="));
    public static final String h = new String(o.b("dGVzdC53b3Ntcy5jbg=="));
    public static final String i = new String(o.b("YXV0aC53b3Ntcy5jbg=="));
    public static final String j = new String(o.b("c2RrbG9nLndvc21zLmNu"));
    public static final String k = new String(o.b("L2Ryby92MS9yZXBvcnQ="));
    public static final String l = new String(o.b("L3VuaWNvbUF1dGgvYW5kcm9pZC92My4wL3Fj"));
    public static final String m = new String(o.b("L3VuaWNvbUF1dGgvYW5kcm9pZC92My4wL3Fj"));
    public static final String n = new String(o.b("L3VuaWNvbVVhaWQvYW5kcm9pZC92MS4wL3Fj"));
    public static Set<String> p = new HashSet();
    public static String r = "0";
    private static String s = "";
    private static String t = "";
    private static String u = "";
    private static int v = 5;
    private static int w = -1;
    private static String x = "";
    private static String y = "";
    private static String z = "";

    public static int a() {
        switch (q) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 0;
            default:
                return -1;
        }
    }

    public static void a(int i2) {
        q = i2;
    }

    public static void a(Context context, String str) throws JSONException {
        d dVar = new d();
        dVar.a = str;
        if (o.isEmpty()) {
            o.add(dVar);
        } else {
            o.add(1, dVar);
            if (o.size() > 4) {
                o.remove(4);
            }
        }
        am.a(context, "DNSCache", new ArrayList(o));
    }

    public static boolean a(String str) {
        return str.startsWith(i) || str.startsWith(e) || str.startsWith(g);
    }

    public static boolean a(String str, String str2) throws UnknownHostException {
        aj.a("\nhostname : " + str + "\nsubjectName : " + str2);
        if (ac.b() && str != null) {
            try {
                if (str.length() > 0) {
                    InetAddress byName = InetAddress.getByName(str.replaceFirst("^\\[", "").replaceAll("\\]$", ""));
                    Iterator<String> it = p.iterator();
                    while (it.hasNext()) {
                        if (byName.equals(InetAddress.getByName(it.next()))) {
                            return true;
                        }
                    }
                }
            } catch (UnknownHostException e2) {
                aj.a(e2);
            }
        }
        return (str.endsWith(".10010.com") && b(str2, "CN=10010.com")) || (str.equals(i) && b(str2, new StringBuilder("CN=").append(i).toString())) || ((str.equals(e) && b(str2, new StringBuilder("CN=").append(e).toString())) || ((str.equals(f) && b(str2, new StringBuilder("CN=").append(f).toString())) || ((str.equals(h) && b(str2, new StringBuilder("CN=").append(h).toString())) || ((str.equals(g) && b(str2, new StringBuilder("CN=").append(g).toString())) || ((str.equals("id6.me") && b(str2, "CN=*.id6.me")) || (str.equals("cert.cmpassport.com") && b(str2, "CN=*.cmpassport.com")))))));
    }

    public static String b() {
        return r;
    }

    public static String b(int i2) {
        StringBuilder sbAppend;
        String str;
        if (ac.a(i2)) {
            sbAppend = new StringBuilder("https://").append(i.d());
            str = l;
        } else if (ac.b(i2)) {
            sbAppend = new StringBuilder("https://").append(i.d());
            str = n;
        } else {
            sbAppend = new StringBuilder("https://").append(i.d());
            str = m;
        }
        return sbAppend.append(str).append("?").toString();
    }

    public static void b(Context context, String str) throws JSONException {
        d dVarI = i(str);
        if (dVarI != null) {
            dVarI.b = "";
            dVarI.c = 0L;
            am.a(context, "DNSCache", new ArrayList(o));
        }
    }

    public static void b(String str) {
        r = str;
    }

    private static boolean b(String str, String str2) {
        return str.startsWith(str2) || str.endsWith(str2);
    }

    public static String c() {
        return s;
    }

    public static void c(int i2) {
        v = i2;
    }

    public static void c(String str) {
        s = str;
    }

    public static String d() {
        if (t.length() != 32) {
            e();
        }
        return t;
    }

    public static void d(int i2) {
        w = i2;
    }

    public static void d(String str) {
        aj.a("APN:".concat(String.valueOf(str)));
        u = str;
    }

    public static String e() {
        t = n.a();
        return d();
    }

    public static String e(String str) {
        return ("cmnet".equals(str) || "cmwap".equals(str)) ? "1" : ("3gwap".equals(str) || "uniwap".equals(str) || "3gnet".equals(str) || "uninet".equals(str)) ? "3" : ("ctnet".equals(str) || "ctwap".equals(str)) ? "2" : "0";
    }

    public static String f() {
        return u;
    }

    public static void f(String str) {
        x = str;
    }

    public static int g() {
        return v;
    }

    public static void g(String str) {
        y = str;
    }

    public static int h() {
        return w;
    }

    public static void h(String str) {
        z = str;
    }

    public static d i(String str) {
        for (int i2 = 0; i2 < o.size(); i2++) {
            d dVar = o.get(i2);
            if (dVar.a.equalsIgnoreCase(str)) {
                return dVar;
            }
        }
        return null;
    }
}