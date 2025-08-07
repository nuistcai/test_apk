package cn.fly.verify;

import android.content.Context;
import android.view.View;
import cn.fly.verify.datatype.VerifyResult;
import java.util.List;

/* loaded from: classes.dex */
public class ae {
    private static ae a;
    private ah b;
    private a c = new a() { // from class: cn.fly.verify.ae.1
        @Override // cn.fly.verify.ae.a
        public void a() {
            ae.this.b = null;
            ae.this.d = null;
            ae.this.e = null;
            k.a().g();
        }
    };
    private List<View> d;
    private CustomViewClickListener e;

    public interface a {
        void a();
    }

    private ae() {
    }

    public static ae a() {
        if (a == null) {
            synchronized (ae.class) {
                if (a == null) {
                    a = new ae();
                }
            }
        }
        return a;
    }

    public void a(Context context, int i, boolean z, boolean z2, d<VerifyResult> dVar) {
        String strG;
        if (this.b == null) {
            cn.fly.verify.a aVarB = i.a().b();
            if (aVarB != null) {
                strG = aVarB.g();
                v.a("openOAuthActivity fakeNumber:" + strG);
            } else {
                strG = "";
            }
            this.b = new ah(i, z, z2, dVar, this.c, strG);
            this.b.show(context, null);
        }
    }

    public void b() {
        if (this.b != null) {
            this.b.b();
        }
    }

    public void c() {
        if (this.b != null) {
            this.b.a();
        }
    }
}