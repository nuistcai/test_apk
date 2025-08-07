package cn.fly.commons.cc;

/* loaded from: classes.dex */
public class p implements s<o> {
    @Override // cn.fly.commons.cc.s
    public boolean a(o oVar, Class<o> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if (!"setHandler".equals(str) || objArr.length != 1 || objArr[0] == null || !(objArr[0] instanceof l)) {
            return false;
        }
        oVar.a((l) objArr[0]);
        return true;
    }
}