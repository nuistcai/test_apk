package cn.fly.tools.utils;

import android.text.TextUtils;
import cn.fly.commons.n;
import cn.fly.tools.utils.DH;

/* loaded from: classes.dex */
public class f {
    private static f a;

    private f() {
    }

    public static f a() {
        if (a == null) {
            synchronized (f.class) {
                if (a == null) {
                    a = new f();
                }
            }
        }
        return a;
    }

    public String b() {
        String strA;
        switch (c()) {
            case MIUI:
                strA = a(n.a("023:bhbibjbdbgbebgbjbebgbjbb)d3bhdgbgbiUc2bj-cbVbd,d"));
                break;
            case EMUI:
                strA = a(n.a("0215bhbibjddbebgVeDbabjbb8d6bhdgbgbiVc9bj<dRbdbebg"));
                break;
            case AMIGO:
            case FLYME:
                strA = a(n.a("019Obhbibjddbebg]e_babjbabgdgGhebLcabjbgba"));
                break;
            case LENOVO:
            case ONEUI:
                strA = a(n.a("028(bhbibjddbebgSeTbabjbbVd)bhdgbgbiEcNbjbg5caIbhEd'bdEdcgbe"));
                break;
            case COLOR_OS:
                strA = a(n.a("024!bhbibjddbebg.e_babjbbCd,bhdgbgbi[cDbjbiMhhHbibhbibd"));
                break;
            case FUNTOUCH_OS:
                strA = a(n.a("027(bhbibjbbbgbbbibjbidgbjddbebg5e,babjbabgdgMheb'cabjbgba"));
                if (TextUtils.isEmpty(strA)) {
                    strA = a(n.a("018'bhbibjbbbgbbbibjbidgbjbbFd$bhdgbgbiLc"));
                    break;
                }
                break;
            case EUI:
                strA = a(n.a("023^bhbibj*edg8bbbjbhTdedb'dg]d_bjbbOdJbhdgbgbiCc"));
                break;
            case SENSE:
                strA = a(n.a("0225bhbibjddbebgXe4babjdg@dcDdgAd@bjbb!dEbhdgbgbiIc"));
                break;
            case GOOGLE:
                strA = a(n.a("024BbhbibjddbebgVeQbabjbbWd3bhdgbgbiZcGbjbh^dedbJdgTd"));
                break;
            case SMARTISAN:
                strA = a(n.a("020]bhbibjdgbdQbFbh3g8bgdg7bcLbjbb[d<bhdgbgbi3c"));
                break;
            case ONEPLUS:
                strA = a(n.a("014!bhbibjbhbibdbjbbDdTbhdgbgbi4c"));
                break;
            case YUNOS:
                strA = a(n.a("020@bhbibj=agb>bjcabeTc8bidgbjbbTdDbhdgbgbi<c"));
                break;
            case QIHOO:
                strA = a(n.a("018JbhbibjddbebgSePbabjbebgbbUdIbhdgbgbiGc"));
                break;
            case NUBIA:
                strA = a(n.a("0232bhbibjddbebg+e[babj7c%beddbg8b,bjbhbibdbj:aJbibaPd"));
                if (TextUtils.isEmpty(strA)) {
                    strA = a(n.a("015Zbhbibjddbebg^e1babjbhbibdbjbgba"));
                    break;
                }
                break;
            case LGE:
                strA = a(n.a("021<dgcadgbjNe$ch-d6bj8e1chbdbabdbfbb-d;bhdgbgbi:c"));
                break;
            default:
                strA = a(n.a("019FbhbibjddbebgHe-babjbabgdg^hebJcabjbgba"));
                break;
        }
        if (TextUtils.isEmpty(strA)) {
            return a(n.a("019(bhbibjddbebgXeHbabjbabgdg%heb cabjbgba"));
        }
        return strA;
    }

    private a c() {
        if (!TextUtils.isEmpty(a("ro.miui.ui.version.code")) || !TextUtils.isEmpty(a(n.a("023^bhbibjbdbgbebgbjbebgbjbb$dNbhdgbgbiAcVbj^cbUbdOd"))) || !TextUtils.isEmpty(a("ro.miui.internal.storage"))) {
            return a.MIUI;
        }
        if (!TextUtils.isEmpty(a(n.a("021<bhbibjddbebgMe babjbb(dQbhdgbgbi[cEbj]dBbdbebg"))) || !TextUtils.isEmpty(a("ro.build.hw_emui_api_level")) || !TextUtils.isEmpty(a("ro.confg.hw_systemversion"))) {
            return a.EMUI;
        }
        if (!TextUtils.isEmpty(a(n.a("026hdVbhdgbgdg3gSbjdgcadgbjbedg^dWbjcd9eIcabdFdObjbgFaAbi,c"))) || !TextUtils.isEmpty(a(n.a("026!bhbibjbd2dGbgebbebjdg3dgDbe@h,debgeb^b3bhbabjcd$e1cabdCd"))) || !TextUtils.isEmpty(a(n.a("0186bhbibjcdAeZcabdKdGbj6h_bedd5e:bgdgBfd)ba")))) {
            return a.FLYME;
        }
        if (!TextUtils.isEmpty(a(n.a("024a=bibdbjdg[b3bddgbeVcGchbjdgPhdCchbjbabgdgFb*ddMed"))) || !TextUtils.isEmpty(a("init.svc.health-hal-2-1-samsung"))) {
            return a.ONEUI;
        }
        if (!TextUtils.isEmpty(a(n.a("024>bhbibjddbebg>eBbabjbb)d?bhdgbgbi6cHbjbiFhh!bibhbibd")))) {
            return a.COLOR_OS;
        }
        if (!TextUtils.isEmpty(a(n.a("0271bhbibjbbbgbbbibjbidgbjddbebgZeWbabjbabgdg=hebJcabjbgba"))) || !TextUtils.isEmpty(a(n.a("018*bhbibjbbbgbbbibjbidgbjbb<d:bhdgbgbiQc")))) {
            return a.FUNTOUCH_OS;
        }
        if (!TextUtils.isEmpty(a(n.a("0236bhbibj>edgVbbbjbh;dedbSdgMdEbjbbZd*bhdgbgbi)c")))) {
            return a.EUI;
        }
        if (!TextUtils.isEmpty(a(n.a("022<bhbibjddbebgFe)babjdgFdc3dg;dHbjbbHd)bhdgbgbi2c")))) {
            return a.SENSE;
        }
        if (n.a("014bc!babhbibgbafichbibichVed").equals(a(n.a("026Ibhbibj8a;bibdbjchbibich.edAbjEaeTbg>dcg8bgbadd4b,dg;d")))) {
            return a.GOOGLE;
        }
        if (!TextUtils.isEmpty(a(n.a("020+bhbibjdgbd4b.bhXg%bgdgWbc5bjbb,d3bhdgbgbiHc")))) {
            return a.SMARTISAN;
        }
        if (!TextUtils.isEmpty(a(n.a("014.bhbibjbhbibdbjbbYd6bhdgbgbi)c")))) {
            return a.ONEPLUS;
        }
        if (!TextUtils.isEmpty(a(n.a("020RbhbibjZagbKbjcabeQc%bidgbjbb2d:bhdgbgbiSc")))) {
            return a.YUNOS;
        }
        if (!TextUtils.isEmpty(a(n.a("018YbhbibjddbebgBe!babjbebgbbQdEbhdgbgbiZc")))) {
            return a.QIHOO;
        }
        if (!TextUtils.isEmpty(a(n.a("023XbhbibjddbebgYeYbabjWcSbeddbgNbMbjbhbibdbj-a,bibaOd"))) || !TextUtils.isEmpty(a(n.a("015.bhbibjddbebg e3babjbhbibdbjbgba")))) {
            return a.NUBIA;
        }
        if (!TextUtils.isEmpty(a(n.a("021]dgcadgbj3e*ch;dWbjRe*chbdbabdbfbb;d bhdgbgbiTc")))) {
            return a.LGE;
        }
        if (!TextUtils.isEmpty(a(n.a("0190bhbibjddbebgQeTbabjbabgdgPhebEcabjbgba"))) && a(n.a("019Ubhbibjddbebg*eXbabjbabgdgKhebQcabjbgba")).matches("amigo([\\d.]+)[a-zA-Z]*")) {
            return a.AMIGO;
        }
        for (a aVar : a.values()) {
            if (aVar.a().equalsIgnoreCase(DH.SyncMtd.getManufacturerForFly())) {
                return aVar;
            }
        }
        return a.OTHER;
    }

    private String a(String str) {
        return DH.SyncMtd.getSystemProperties(str);
    }

    private enum a {
        MIUI(n.a("006_cgbg=bQbibdbg")),
        EMUI(n.a("006fKbeIb$de.d^bg")),
        FLYME(n.a("005ZbdLd6bgebbe")),
        ONEUI(n.a("007+dgBb+bddgbeKc7ch")),
        COLOR_OS(n.a("004Hbi8hh(bi")),
        FUNTOUCH_OS(n.a("004[bbbgbbbi")),
        EUI(n.a("004edgJbb")),
        SENSE(n.a("003fga")),
        GOOGLE(n.a("006Rchbibich6ed")),
        LENOVO(n.a("006edcVbibbbi")),
        SMARTISAN(n.a("006af3bebgebbg")),
        ONEPLUS(n.a("007Fbi$cdheLbedg")),
        YUNOS(n.a("005*cabeWc5bidg")),
        QIHOO(n.a("005Jbcbg:fGbibi")),
        NUBIA(n.a("005c;beddbg'b")),
        LGE(n.a("002eLch")),
        AMIGO(n.a("005?febg3ce<bg")),
        OTHER("");

        private String s;

        a(String str) {
            this.s = str;
        }

        public String a() {
            return this.s;
        }
    }
}