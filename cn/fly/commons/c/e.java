package cn.fly.commons.c;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class e {
    private static h a = null;

    public static synchronized void a(Context context) {
        if (a != null) {
            return;
        }
        a aVarA = a(context, Build.MANUFACTURER, Build.BRAND);
        if (aVarA == a.UNSUPPORT) {
            return;
        }
        switch (aVarA) {
            case XIAOMI:
            case REDMI:
            case MEITU:
            case BLACKSHARK:
                a = new q(context);
                break;
            case IQOO:
            case VIVO:
                a = new p(context);
                break;
            case HUA_WEI:
                a = new g(context);
                break;
            case HORNOR:
                a = new f(context);
                break;
            case OPPO:
            case REALME:
                a = new m(context);
                break;
            case ONEPLUS:
                a = new l(context);
                break;
            case MOTO:
            case ZUK:
            case LENOVO:
                a = new j(context);
                break;
            case ASUS:
                a = new cn.fly.commons.c.a(context);
                break;
            case SAMSUNG:
                a = new o(context);
                break;
            case MEIZU:
            case MBLU:
            case ALPS:
                a = new i(context);
                break;
            case NUBIA:
                a = new k(context);
                break;
            case ZTE:
            case FERRMEOS:
            case SSUI:
                a = new r(context);
                break;
            case COOLPAD:
                a = new b(context);
                break;
            case QIKU:
                a = new n(context);
                break;
            case COOSEA:
                a = new c(context);
                break;
        }
    }

    public static String b(Context context) {
        a(context);
        if (a != null) {
            if (a instanceof f) {
                String strD = a.d();
                if (!TextUtils.isEmpty(strD) && !Pattern.compile("^[0fF\\-]+").matcher(strD).matches()) {
                    return strD;
                }
                a = new g(context);
            } else if (a instanceof l) {
                String strD2 = a.d();
                if (!TextUtils.isEmpty(strD2) && !Pattern.compile("^[0fF\\-]+").matcher(strD2).matches()) {
                    return strD2;
                }
                a = new m(context);
            }
            return a.d();
        }
        return null;
    }

    public static a a(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            for (a aVar : a.values()) {
                if (aVar.C.equalsIgnoreCase(str) || aVar.C.equalsIgnoreCase(str2) || (!TextUtils.isEmpty(aVar.D) && !TextUtils.isEmpty(DH.SyncMtd.getSystemProperties(aVar.D)))) {
                    return aVar;
                }
            }
        }
        if (a() || b()) {
            return a.ZTE;
        }
        if (c(context)) {
            return a.COOLPAD;
        }
        if (c()) {
            return a.COOSEA;
        }
        return a.UNSUPPORT;
    }

    private static boolean a() {
        String systemProperties = DH.SyncMtd.getSystemProperties(cn.fly.commons.a.l.a("021Xekelemggehej h+edemfgek(ggVegWg?em he?ggAgh"));
        return !TextUtils.isEmpty(systemProperties) && systemProperties.equalsIgnoreCase(cn.fly.commons.a.l.a("0083hdhkhjhjidhjhifm"));
    }

    private static boolean b() {
        String systemProperties = DH.SyncMtd.getSystemProperties(cn.fly.commons.a.l.a("015NekelemgjgjehejemUk$ekeledehCdj"));
        return (TextUtils.isEmpty(systemProperties) || systemProperties.equalsIgnoreCase(cn.fly.commons.a.l.a("007Deh,f?fi5f=elgh8f"))) ? false : true;
    }

    private static boolean c(Context context) {
        try {
            final Object[] objArr = new Object[1];
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            DH.requester(context).getMpfo(cn.fly.commons.a.l.a("027dEelegem-dQelelNhke7edemedCgFeeej5dgGejedgjeh?kk:elekUj"), 0).request(new DH.DHResponder() { // from class: cn.fly.commons.c.e.1
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) throws Throwable {
                    objArr[0] = dHResponse.getMpfo(new int[0]);
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await(3L, TimeUnit.SECONDS);
            return objArr[0] != null;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return false;
        }
    }

    private static boolean c() {
        return "PRIZE".equalsIgnoreCase(DH.SyncMtd.getSystemProperties("ro.odm.manufacturer"));
    }

    enum a {
        UNSUPPORT(-1, cn.fly.commons.a.l.a("009XehYf(gjeh4kkGelek>j")),
        HUA_WEI(0, cn.fly.commons.a.l.a("006Dglflgehghjff"), cn.fly.commons.a.l.a("021>ekelemggehejDhSedemeeCg0ekgjejelUfIemOgDegehej")),
        XIAOMI(1, cn.fly.commons.a.l.a("0063hhej3eQelegej"), cn.fly.commons.a.l.a("023*ekelemegejehejemehejemee>g7ekgjejel'f=em'fe'egKg")),
        VIVO(2, cn.fly.commons.a.l.a("004Ieeejeeel"), cn.fly.commons.a.l.a("018FekelemeeejeeelemelgjemeeVg^ekgjejel5f")),
        OPPO(3, cn.fly.commons.a.l.a("004TelBkk5el"), cn.fly.commons.a.l.a("024Kekelemggehej)hKedemeeVgRekgjejelVfSemelSkkIelekeleg")),
        MOTO(4, cn.fly.commons.a.l.a("008Negel.jSelekelIhe")),
        LENOVO(5, cn.fly.commons.a.l.a("006hgfSeleeel")),
        ASUS(6, cn.fly.commons.a.l.a("004e,gjehgj")),
        SAMSUNG(7, cn.fly.commons.a.l.a("007?gj3ePeggjehPf<fk")),
        MEIZU(8, cn.fly.commons.a.l.a("005?eg>g;ejheeh")),
        ALPS(9, cn.fly.commons.a.l.a("004ehk@gj")),
        NUBIA(10, cn.fly.commons.a.l.a("005fMehggejNe")),
        ONEPLUS(11, cn.fly.commons.a.l.a("0079el+fgkh8ehgj")),
        BLACKSHARK(12, cn.fly.commons.a.l.a("010Ygg,hedMfigj=ieRekfi")),
        ZTE(13, cn.fly.commons.a.l.a("003FheAjg")),
        FERRMEOS(14, cn.fly.commons.a.l.a("008,fgek]ggVeg5g]elgj")),
        SSUI(15, cn.fly.commons.a.l.a("004Vgjgjehej")),
        HORNOR(16, "HONOR"),
        REALME(17, "REALME"),
        REDMI(18, "REDMI"),
        MEITU(19, "MEITU"),
        ZUK(20, "ZUK"),
        MBLU(21, "MBLU"),
        COOLPAD(22, "COOLPAD"),
        COOSEA(23, "COOSEA"),
        QIKU(24, "360OS", cn.fly.commons.a.l.a("018IekelemggehejVhXedemehejee1gPekgjejel(f")),
        IQOO(25, "iqoo");

        private final int B;
        private String C;
        private String D;

        a(int i, String str) {
            this.B = i;
            this.C = str;
        }

        a(int i, String str, String str2) {
            this.B = i;
            this.C = str;
            this.D = str2;
        }
    }
}