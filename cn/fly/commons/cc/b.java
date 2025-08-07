package cn.fly.commons.cc;

import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public class b implements s<cn.fly.commons.s> {
    @Override // cn.fly.commons.cc.s
    public boolean a(cn.fly.commons.s sVar, Class<cn.fly.commons.s> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if (cn.fly.commons.w.b("004>di4eh+ek").equals(str)) {
            objArr2[0] = sVar.a();
        } else if (cn.fly.commons.w.b("008VehTehHed@chbg").equals(str) && objArr != null && objArr.length == 1) {
            objArr2[0] = sVar.a((CountDownLatch) objArr[0]);
        } else {
            if (!cn.fly.commons.w.b("0056chehdk]ih").equals(str)) {
                return false;
            }
            objArr2[0] = Boolean.valueOf(sVar.b());
        }
        return true;
    }
}