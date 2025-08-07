package cn.fly.commons.cc;

import android.content.Context;
import android.content.pm.PackageManager;
import cn.fly.commons.cc.l;
import cn.fly.commons.cc.w;
import cn.fly.tools.FlyHandlerThread;
import cn.fly.tools.network.NetCommunicator;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.FlyPersistence;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class a {
    private static final q a = new q();
    private static final i b = new i();
    private static final n c = new n();
    private static volatile l d;
    private static volatile l e;

    static {
        try {
            d = new l(new l.a() { // from class: cn.fly.commons.cc.a.1
                @Override // cn.fly.commons.cc.l.a
                public Object a(String str, ArrayList<Object> arrayList) {
                    try {
                        if (a.e != null) {
                            return a.e.a(str, arrayList);
                        }
                        return null;
                    } catch (Throwable th) {
                        return null;
                    }
                }
            });
            e = new l(new l.a() { // from class: cn.fly.commons.cc.a.2
                @Override // cn.fly.commons.cc.l.a
                public Object a(String str, ArrayList<Object> arrayList) {
                    return str + "" + arrayList;
                }
            });
            d.a("tt", null);
        } catch (Throwable th) {
        }
    }

    public static int a() {
        return w.a();
    }

    public static void a(Context context, byte[] bArr, String str, Method method) throws Throwable {
        a(w.a(bArr), context, str, method);
    }

    public static void a(Context context, String str, String str2, Method method) throws Throwable {
        a(w.a(str), context, str2, method);
    }

    public static void a(Context context, String str, String str2, HashMap<String, Object> map, HashMap<String, Object> map2) throws Throwable {
        w.c cVarA = w.a(str);
        cVarA.a("ss_dhMap", map).a("ss_dataMaps", map2);
        a(cVarA, context, str2, (Method) null);
    }

    public static LinkedList<Object> a(Object obj, Object... objArr) throws Throwable {
        return ((y) obj).b(objArr);
    }

    private static void a(w.c cVar, Context context, String str, Method method) throws Throwable {
        cVar.a(cn.fly.commons.w.b("012GfgOieXciMch,chcj3dAdk%eh"), i.class).a(cn.fly.commons.w.b("003!gbdfeb"), d.class).a("SBSP", n.class).a(cn.fly.commons.w.b("004Fgbdkfkej"), f.class).a(cn.fly.commons.w.b("015Odkeiej[cdWcbOfe!cieb^g$ciWecGcb"), FlyHandlerThread.class).a(cn.fly.commons.w.b("019<dkeieicicjYcAcb(bc_ehXh3fi1ebeOchcc,e]ci"), h.class).a(cn.fly.commons.w.b("017Xdkeidccj@dhedh;fi6eZehcj;fNcc@e@ci"), k.class).a(cn.fly.commons.w.b("019Sdkeidk-e_ciccch?be)dccj+ddebh3chcjZd"), o.class).a(cn.fly.commons.w.b("017:dkeidccjPdhedh3fgeeeh]eNciccYe.ci"), j.class).a(cn.fly.commons.w.b("017SdkeidfEeh6efcjcidgdc_cffQeeCcb7dg"), m.class).a(cn.fly.commons.w.b("009$dkeiejWcdYcbDfeZci"), l.class).a(cn.fly.commons.w.b("003?gbdfdc"), NetCommunicator.class).a(cn.fly.commons.w.b("004?gbdfebfg"), NetworkHelper.NetworkTimeOut.class).a("NoVaDataException", FlyPersistence.NoValidDataException.class).a(cn.fly.commons.w.b("003Pejcjdd"), cn.fly.commons.s.class).a(h.class, h.class).a(j.class, j.class).a(o.class, p.class).a(m.class, m.class).a(q.class, q.class).a(i.class, i.class).a(d.class, d.class).a(f.class, g.class).a(Context.class, c.class).a(PackageManager.class, e.class).a(n.class, n.class).a(cn.fly.commons.s.class, b.class).a("ss_sdh", c).a("ss_opSet", b).a("ss_suls", a).a(cn.fly.commons.w.b("015*ehehcgMbRcjNdheFdhVh.fk)c7ci@cAce"), context).a(cn.fly.commons.w.b("0147ehehcgeh$hc$ciBh?fkYc1ciWcHceeh"), str).a(cn.fly.commons.w.b("012?ehehcgehXhc2ci-h'ebchce?e"), Long.valueOf(System.currentTimeMillis())).a(cn.fly.commons.w.b("0069ehehcgcecbDi"), method).a(cn.fly.commons.w.b("016b9cjcececjAdci8ckehcbdgck!b2cb1b"));
        cVar.a();
    }
}