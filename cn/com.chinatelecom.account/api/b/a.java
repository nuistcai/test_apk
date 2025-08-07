package cn.com.chinatelecom.account.api.b;

import android.content.Context;
import android.net.Network;
import android.os.Build;
import cn.com.chinatelecom.account.api.CtSetting;
import cn.com.chinatelecom.account.api.c.g;
import cn.com.chinatelecom.account.api.d.f;
import cn.com.chinatelecom.account.api.d.g;
import cn.com.chinatelecom.account.api.d.h;
import cn.com.chinatelecom.account.api.d.j;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class a {
    private static final String a = a.class.getSimpleName();
    private boolean b = false;
    private Context c;
    private String d;
    private String e;
    private c f;

    public a(Context context, String str, String str2) {
        this.c = context;
        this.d = str;
        this.e = str2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject a(Context context, String str, String str2, String str3, CtSetting ctSetting, Network network, String str4, String str5, int i) {
        String strB;
        String strB2;
        boolean z;
        try {
            long jA = cn.com.chinatelecom.account.api.d.a.a(context);
            if (i == cn.com.chinatelecom.account.api.b.d) {
                strB = g.l(context);
                strB2 = h.a(context, str, str2, str3, jA, "");
            } else {
                strB = h.b();
                strB2 = h.b(context, str, str2, str3, jA, "");
            }
            if (g.a() != null) {
                strB = strB.replace(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.f), g.a());
            }
            String str6 = strB;
            JSONObject jSONObject = new JSONObject(strB2);
            String strOptString = jSONObject.optString("p");
            String strOptString2 = jSONObject.optString("k");
            g.a aVar = new g.a();
            aVar.a(str5);
            aVar.a(false, cn.com.chinatelecom.account.api.c.c.a(), cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.f));
            aVar.b(str4);
            aVar.a(network);
            aVar.a(CtSetting.getConnTimeout(ctSetting));
            aVar.b(CtSetting.getReadTimeout(ctSetting));
            cn.com.chinatelecom.account.api.c.g gVarA = aVar.a();
            cn.com.chinatelecom.account.api.c.b bVar = new cn.com.chinatelecom.account.api.c.b(context);
            cn.com.chinatelecom.account.api.c.h hVarA = bVar.a(str6, strOptString, 1, gVarA, true);
            if (hVarA.d) {
                synchronized (this) {
                    z = this.b;
                }
                if (!z) {
                    cn.com.chinatelecom.account.api.c.g gVarA2 = aVar.a(true).a(false, "", "").a();
                    String strA = h.a();
                    if (hVarA.e.equals("2")) {
                        strA = strA.replace(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.f), cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.g));
                    }
                    cn.com.chinatelecom.account.api.c.h hVarA2 = bVar.a(strA, strOptString, 1, gVarA2, false);
                    f.a(str4).b(1).b(cn.com.chinatelecom.account.api.d.g.f(context));
                    hVarA = hVarA2;
                }
            }
            JSONObject jSONObjectA = cn.com.chinatelecom.account.api.d.a.a(context, hVarA, strOptString2, network, true, str4);
            f.b(str4, jSONObjectA, strOptString);
            return jSONObjectA;
        } catch (Throwable th) {
            JSONObject jSONObjectI = j.i();
            f.a(str4).g("gpm ：" + th.getMessage()).b(cn.com.chinatelecom.account.api.d.g.f(context)).a(80102).e(cn.com.chinatelecom.account.api.a.d.a(j.k));
            cn.com.chinatelecom.account.api.a.a(a, "GPM Throwable", th);
            return jSONObjectI;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (this.f != null) {
            this.f.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str, String str2, long j, String str3, cn.com.chinatelecom.account.api.c cVar) throws JSONException {
        f.a(str2).a(i).e(str).b(j).g(str3).b(cn.com.chinatelecom.account.api.d.g.f(this.c));
        f.c(str2);
        String strA = j.a(i, str, str2);
        if (cVar != null) {
            cVar.a(strA);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final String str, final CtSetting ctSetting, final Network network, final cn.com.chinatelecom.account.api.c cVar, long j, final String str2, final String str3, final int i) {
        new d().a(new e(j) { // from class: cn.com.chinatelecom.account.api.b.a.3
            @Override // cn.com.chinatelecom.account.api.b.e
            public void runTask() {
                JSONObject jSONObjectA = a.this.a(a.this.c, a.this.d, a.this.e, str, ctSetting, network, str2, str3, i);
                synchronized (this) {
                    if (!isCompleted()) {
                        setCompleted(true);
                        removeTimeoutTask();
                        cn.com.chinatelecom.account.api.a.a(str2, jSONObjectA, cVar);
                    }
                }
                if (network != null) {
                    a.this.a();
                }
            }

            @Override // cn.com.chinatelecom.account.api.b.e
            public void timeout() {
                super.timeout();
                synchronized (a.this) {
                    a.this.b = true;
                }
                synchronized (this) {
                    if (!isCompleted()) {
                        setCompleted(true);
                        a.this.a(80000, cn.com.chinatelecom.account.api.a.d.a(j.a), str2, 0L, "", cVar);
                    }
                }
                if (network != null) {
                    a.this.a();
                }
            }
        });
    }

    public void a(String str, CtSetting ctSetting, int i, cn.com.chinatelecom.account.api.c cVar) {
        int totalTimeout = CtSetting.getTotalTimeout(ctSetting);
        String strA = cn.com.chinatelecom.account.api.d.d.a();
        String strA2 = cn.com.chinatelecom.account.api.d.d.a(this.c);
        String strA3 = cn.com.chinatelecom.account.api.d.a.a(i);
        f.a(strA).a(strA2).c(strA3).b(cn.com.chinatelecom.account.api.d.g.e(this.c)).f(cn.com.chinatelecom.account.api.d.g.i(this.c));
        a(str, ctSetting, null, cVar, totalTimeout, strA, strA3, i);
    }

    public void b(final String str, final CtSetting ctSetting, final int i, final cn.com.chinatelecom.account.api.c cVar) {
        final int totalTimeout = CtSetting.getTotalTimeout(ctSetting);
        final String strA = cn.com.chinatelecom.account.api.d.d.a();
        String strA2 = cn.com.chinatelecom.account.api.d.d.a(this.c);
        final String strA3 = cn.com.chinatelecom.account.api.d.a.a(i);
        f.a(strA).a(strA2).c(strA3).b("BOTH").f(cn.com.chinatelecom.account.api.d.g.i(this.c));
        if (Build.VERSION.SDK_INT >= 21) {
            this.f = new c(this.c);
            this.f.a(new b() { // from class: cn.com.chinatelecom.account.api.b.a.1
                @Override // cn.com.chinatelecom.account.api.b.b
                public void a() throws JSONException {
                    a.this.a();
                    a.this.a(80800, cn.com.chinatelecom.account.api.a.d.a(j.o), strA, 2500L, "", cVar);
                }

                @Override // cn.com.chinatelecom.account.api.b.b
                public void a(long j) throws JSONException {
                    a.this.a();
                    a.this.a(80801, cn.com.chinatelecom.account.api.a.d.a(j.p), strA, j, "", cVar);
                }

                @Override // cn.com.chinatelecom.account.api.b.b
                public void a(Network network, long j) {
                    long j2 = totalTimeout - j;
                    if (j2 > 100) {
                        a.this.a(str, ctSetting, network, cVar, j2, strA, strA3, i);
                    } else {
                        a.this.a();
                        cn.com.chinatelecom.account.api.a.a(strA, j.c(), cVar);
                    }
                    f.a(strA).b(j);
                }
            });
            return;
        }
        this.f = new c(this.c);
        String strL = cn.com.chinatelecom.account.api.d.g.l(this.c);
        if (cn.com.chinatelecom.account.api.d.g.a() != null) {
            strL = strL.replace(cn.com.chinatelecom.account.api.a.d.a(cn.com.chinatelecom.account.api.d.b.f), cn.com.chinatelecom.account.api.d.g.a());
        }
        this.f.a(new b() { // from class: cn.com.chinatelecom.account.api.b.a.2
            @Override // cn.com.chinatelecom.account.api.b.b
            public void a() throws JSONException {
                a.this.a(80800, cn.com.chinatelecom.account.api.a.d.a(j.o), strA, 2500L, "Switching network timeout (4.x)", cVar);
            }

            @Override // cn.com.chinatelecom.account.api.b.b
            public void a(long j) throws JSONException {
                a.this.a(80801, cn.com.chinatelecom.account.api.a.d.a(j.p), strA, j, "Switching network failed (4.x)", cVar);
            }

            @Override // cn.com.chinatelecom.account.api.b.b
            public void a(Network network, long j) {
                long j2 = totalTimeout - j;
                if (j2 > 100) {
                    a.this.a(str, ctSetting, null, cVar, j2, strA, strA3, i);
                } else {
                    cn.com.chinatelecom.account.api.a.a(strA, j.c(), cVar);
                }
                f.a(strA).b(j);
            }
        }, strL);
    }
}