package cn.fly.verify;

import android.os.SystemClock;
import cn.fly.verify.common.exception.VerifyException;
import com.alibaba.fastjson.asm.Opcodes;
import java.util.UUID;

/* loaded from: classes.dex */
public class u {
    private long b;
    private g c;
    private String e;
    private Integer f;
    private Integer g;
    private Integer h;
    private Integer i;
    private long a = 0;
    private String d = UUID.randomUUID().toString();

    public u(g gVar) {
        this.c = gVar;
        if (gVar == g.INIT) {
            e.c(this.d);
        } else if (gVar == g.PREVERIFY) {
            e.d(this.d);
        } else if (gVar == g.VERIFY) {
            e.e(this.d);
        }
    }

    private void b(t tVar) {
        s.a(tVar);
    }

    private m d(String str) {
        if ("CMCC".equals(str)) {
            return "preVerify".equals(f()) ? m.C_ONE_KEY_OBTAIN_CM_OPERATOR_ACCESS_CODE_ERR : m.C_ONE_KEY_OBTAIN_CM_OPERATOR_ACCESS_TOKEN_ERR;
        }
        if ("CTCC".equals(str)) {
            return "preVerify".equals(f()) ? m.C_ONE_KEY_OBTAIN_CT_OPERATOR_ACCESS_CODE_ERR : m.C_ONE_KEY_OBTAIN_CT_OPERATOR_ACCESS_TOKEN_ERR;
        }
        if ("CUCC".equals(str)) {
            return "preVerify".equals(f()) ? m.C_ONE_KEY_OBTAIN_CU_OPERATOR_ACCESS_CODE_ERR : m.C_ONE_KEY_OBTAIN_CU_OPERATOR_ACCESS_TOKEN_ERR;
        }
        return null;
    }

    private String f() {
        switch (this.c) {
            case INIT:
                return "init";
            case PREVERIFY:
                return "preVerify";
            case AUTHPAGE:
                return "authPageOpend";
            case VERIFY:
                return "verify";
            default:
                return null;
        }
    }

    public g a() {
        return this.c;
    }

    public m a(String str, String str2, VerifyException verifyException) {
        a(a(str, str2, verifyException.getCode(), verifyException.getMessage()));
        b();
        return d(str);
    }

    public t a(String str, int i, String str2, int i2, String str3) {
        t tVarB = b(str);
        tVarB.b(true);
        tVarB.a(i);
        tVarB.b(str2);
        tVarB.b(i2);
        tVarB.c(str3);
        return tVarB;
    }

    public t a(String str, String str2, int i, String str3) {
        t tVarB = b(f());
        tVarB.d(str2);
        tVarB.e(str);
        tVarB.b(true);
        m mVarD = d(str);
        if (mVarD != null) {
            tVarB.a(mVarD.a());
            tVarB.b(mVarD.b());
        }
        tVarB.b(i);
        tVarB.c(str3);
        return tVarB;
    }

    public void a(int i) {
        this.f = Integer.valueOf(i);
    }

    public void a(VerifyException verifyException, VerifyException verifyException2) {
        a(a(f(), verifyException.getCode(), verifyException.getMessage(), verifyException2.getCode(), verifyException2.getMessage()));
        b();
    }

    @Deprecated
    public void a(VerifyException verifyException, VerifyException verifyException2, String str) {
        t tVarA = a(f(), verifyException.getCode(), verifyException.getMessage(), verifyException2.getCode(), verifyException2.getMessage());
        tVarA.e(str);
        a(tVarA);
        b();
    }

    public void a(t tVar) {
        if (tVar != null) {
            b(tVar);
        }
    }

    public void a(Integer num) {
        this.g = num;
    }

    public void a(String str) {
        t tVarB = b(f());
        tVarB.a(Opcodes.GOTO_W);
        tVarB.b(str);
        a(tVarB);
        b();
    }

    public void a(String str, String str2) {
        a(b(str, str2));
        b();
    }

    public void a(String str, String str2, String str3) {
        t tVarB = b(str3);
        tVarB.a(Opcodes.GOTO_W);
        tVarB.b("success");
        tVarB.e(str);
        tVarB.d(str2);
        a(tVarB);
    }

    public void a(String str, String str2, String str3, String str4) {
        t tVarB = b(str3);
        tVarB.a(Opcodes.GOTO_W);
        tVarB.b("success");
        tVarB.e(str);
        tVarB.d(str2);
        tVarB.b(str4);
        a(tVarB);
    }

    public t b(String str) {
        long j;
        long j2 = this.a;
        long j3 = 0;
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (j2 == 0) {
            this.a = jUptimeMillis;
            this.b = this.a;
            j = 0;
        } else {
            long j4 = jUptimeMillis - this.a;
            long j5 = jUptimeMillis - this.b;
            this.b = jUptimeMillis;
            j = j4;
            j3 = j5;
        }
        t tVar = new t(this.c, str);
        tVar.a(this.d);
        tVar.c(j3);
        tVar.b(j);
        tVar.a(System.currentTimeMillis());
        tVar.f(this.e);
        if (this.f != null) {
            tVar.a(this.f);
        }
        if (this.g != null) {
            tVar.b(this.g);
        }
        if (this.h != null) {
            tVar.c(this.h.intValue());
        }
        if (this.i != null) {
            tVar.c(this.i);
        }
        return tVar;
    }

    public t b(String str, String str2) {
        t tVarB = b(f());
        tVarB.d(str2);
        tVarB.e(str);
        tVarB.a(Opcodes.GOTO_W);
        tVarB.b("success");
        return tVarB;
    }

    public void b() {
        s.a(this);
    }

    public void b(int i) {
        this.h = Integer.valueOf(i);
    }

    public String c() {
        return this.e;
    }

    public void c(String str) {
        this.e = str;
    }

    public Integer d() {
        return this.f;
    }

    public String e() {
        return this.d;
    }
}