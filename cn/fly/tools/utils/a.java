package cn.fly.tools.utils;

import android.text.TextUtils;
import cn.fly.commons.w;

/* loaded from: classes.dex */
public class a {
    public static boolean a(Object obj, String str) {
        if (obj != null && !TextUtils.isEmpty(str)) {
            return str.equals(obj.getClass().getName());
        }
        return false;
    }

    /* renamed from: cn.fly.tools.utils.a$a, reason: collision with other inner class name */
    public static class C0025a {
        public static boolean a(Object obj) {
            return ((Boolean) ReflectHelper.invokeInstanceMethodNoThrow(obj, "isRegistered", false, new Object[0])).booleanValue();
        }

        public static boolean b(Object obj) {
            return a.a(obj, w.b("029cdOcbcicjchcbck0hefeig<cj!d(dbckdcOeff:dd0d>decjhcehce"));
        }

        public static boolean c(Object obj) {
            return a.a(obj, w.b("030cd*cbcicjchcbckHhefeigVcj0d=dbckdc1eff(ddIdHdecjdccbceJc"));
        }

        public static boolean d(Object obj) {
            return a.a(obj, w.b("031cd!cbcicjchcbck'hefeigUcjIdGdbckdcUeff_dd]d[decjfe6b'cbce$c"));
        }

        public static boolean e(Object obj) {
            return a.a(obj, w.b("029cd^cbcicjchcbckWhefeigYcjRd5dbckdcLeff2ddTdOdecjed he"));
        }

        public static boolean f(Object obj) {
            return a.a(obj, w.b("028cdTcbcicjchcbckFhefeig%cjDdNdbckdc[eff3dd[d*decjdfci"));
        }

        public static Object g(Object obj) {
            return ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("015*diHehQdcTeff7ddcbNedh4ch4hAdb"), null, new Object[0]);
        }

        public static int h(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("006RdiWehVgbQbb"), -1, new Object[0])).intValue();
        }

        public static int i(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("0068di-eh1gb_db"), -1, new Object[0])).intValue();
        }

        public static int j(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("006=diReh=edUcb"), -1, new Object[0])).intValue();
        }

        public static int k(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("006Edi2ehQdcchcb"), -1, new Object[0])).intValue();
        }

        public static int l(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("006?di7eh,fkehUb"), -1, new Object[0])).intValue();
        }

        public static int m(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("011Zdi(ehXdkdbehDhe ceddcb"), -1, new Object[0])).intValue();
        }

        public static int n(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("012:diOeh=dfXehNefcjcidgddcb"), -1, new Object[0])).intValue();
        }

        public static int o(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("0117diFeh'edCchEch%h2cfcb0e"), -1, new Object[0])).intValue();
        }

        public static int p(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("012DdiCehXedcj+dGdichQhGcfcb8e"), -1, new Object[0])).intValue();
        }

        public static int q(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, "getBasestationId", -1, new Object[0])).intValue();
        }

        public static int r(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("006<diSehXeb%cb"), -1, new Object[0])).intValue();
        }

        public static int s(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("005Gdi eh^dcch"), -1, new Object[0])).intValue();
        }

        public static int t(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("006%di+ehCfk(bNch"), -1, new Object[0])).intValue();
        }

        public static int u(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("009GdiAeh4fhFc-cideQbd"), -1, new Object[0])).intValue();
        }

        public static String v(Object obj) {
            return (String) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("012XdiSehHgb$bbMdk-h)cichOdDdi"), null, new Object[0]);
        }

        public static String w(Object obj) {
            return (String) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("012Ndi^eh gb=dbRdkLhNcichXdVdi"), null, new Object[0]);
        }

        public static long x(Object obj) {
            return ((Long) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("006<diNeh;dfZbMch"), -1L, new Object[0])).longValue();
        }

        public static int y(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, w.b("010Idi@eh-dfci4c0cideKbd"), -1, new Object[0])).intValue();
        }

        public static Object z(Object obj) {
            return ReflectHelper.invokeInstanceMethodNoThrow(obj, "getCellSignalStrength", null, new Object[0]);
        }

        public static int A(Object obj) {
            return ((Integer) ReflectHelper.invokeInstanceMethodNoThrow(obj, "getDbm", -1, new Object[0])).intValue();
        }
    }
}