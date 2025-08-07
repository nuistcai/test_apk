package cn.fly.verify.datatype;

import android.text.TextUtils;
import cn.fly.verify.m;
import cn.fly.verify.util.l;
import cn.fly.verify.v;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class a extends cn.fly.verify.a {
    private int a;
    private C0028a b;

    /* renamed from: cn.fly.verify.datatype.a$a, reason: collision with other inner class name */
    private class C0028a extends c {
        private String b;
        private boolean c;
        private int d;
        private String e;

        private C0028a() {
        }
    }

    private a() {
        this.a = -1;
    }

    public a(int i, JSONObject jSONObject) {
        C0028a c0028a;
        String str;
        this.a = -1;
        this.a = i;
        this.b = new C0028a();
        if (jSONObject != null) {
            this.b.b = jSONObject.optString("resultCode");
            this.b.c = jSONObject.optBoolean("resultDesc");
            this.b.d = jSONObject.optInt("SDKRequestCode");
            String strOptString = jSONObject.optString("operatorType");
            if (!TextUtils.isEmpty(strOptString)) {
                try {
                    if (strOptString.equals(l.a("fly_verify_op_cm", "移动"))) {
                        c0028a = this.b;
                        str = "CMCC";
                    } else if (strOptString.equals(l.a("fly_verify_op_cu", "联通"))) {
                        c0028a = this.b;
                        str = "CUCC";
                    } else if (strOptString.equals(l.a("fly_verify_op_ct", "电信"))) {
                        c0028a = this.b;
                        str = "CTCC";
                    }
                    c0028a.e = str;
                } catch (Throwable th) {
                    v.a(th);
                }
            }
        }
        super.a("103000".equals(this.b.b));
        HashMap map = new HashMap();
        map.put("result", Integer.valueOf(i));
        map.put("jsonObject", jSONObject);
        super.b(new JSONObject(map).toString());
        super.a(System.currentTimeMillis() + 3600000);
    }

    public int i() {
        int iA = m.INNER_OTHER_EXCEPTION_ERR.a();
        if (this.b == null) {
            return iA;
        }
        try {
            return Integer.parseInt(this.b.b);
        } catch (Throwable th) {
            return m.INNER_OTHER_EXCEPTION_ERR.a();
        }
    }

    public String j() {
        if (this.b != null) {
            return this.b.e;
        }
        return null;
    }
}