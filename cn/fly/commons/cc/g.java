package cn.fly.commons.cc;

/* loaded from: classes.dex */
public class g implements s<f> {
    @Override // cn.fly.commons.cc.s
    public boolean a(f fVar, Class<f> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if ("new".equals(str) && objArr.length == 2) {
            objArr2[0] = new f((String) objArr[0], ((Integer) objArr[1]).intValue());
        } else if (cn.fly.commons.m.a("009l1fi4kJgnYkNflfkEg5gl").equals(str) && objArr.length == 2) {
            fVar.a((String) objArr[0], (String) objArr[1]);
        } else if (cn.fly.commons.m.a("009SglAhkBgn?k'flfk)gQgl").equals(str) && objArr.length == 2) {
            objArr2[0] = fVar.b((String) objArr[0], (String) objArr[1]);
        } else if (cn.fly.commons.m.a("010l2fiWkPhlfmfm[ihfg").equals(str) && objArr.length == 2 && (objArr[1] instanceof Boolean)) {
            fVar.a((String) objArr[0], ((Boolean) objArr[1]).booleanValue());
        } else if (cn.fly.commons.m.a("0104gl2hk@hlfmfmDihfg").equals(str) && objArr.length == 2 && (objArr[1] instanceof Boolean)) {
            objArr2[0] = Boolean.valueOf(fVar.b((String) objArr[0], ((Boolean) objArr[1]).booleanValue()));
        } else if (cn.fly.commons.m.a("007lJfiUk[hgfm@gIgl").equals(str) && objArr.length == 2 && (objArr[1] instanceof Long)) {
            fVar.a((String) objArr[0], ((Long) objArr[1]).longValue());
        } else if (cn.fly.commons.m.a("007ZglDhk)hgfmMg'gl").equals(str) && objArr.length == 2 && (objArr[1] instanceof Long)) {
            objArr2[0] = Long.valueOf(fVar.b((String) objArr[0], ((Long) objArr[1]).longValue()));
        } else if (cn.fly.commons.m.a("006l[fiRk!ggWgk").equals(str) && objArr.length == 2 && (objArr[1] instanceof Integer)) {
            fVar.a((String) objArr[0], ((Integer) objArr[1]).intValue());
        } else if (cn.fly.commons.m.a("006;glGhkBgg*gk").equals(str) && objArr.length == 2) {
            objArr2[0] = Integer.valueOf(fVar.b((String) objArr[0], ((Integer) objArr[1]).intValue()));
        } else if (cn.fly.commons.m.a("006l0fiYk[ijhhji").equals(str) && objArr.length == 2) {
            fVar.a((String) objArr[0], objArr[1]);
        } else if (cn.fly.commons.m.a("006Pgl=hk ijhhji").equals(str) && objArr.length == 1) {
            objArr2[0] = fVar.a((String) objArr[0]);
        } else {
            if (!"clr".equals(str)) {
                return false;
            }
            fVar.a();
        }
        return true;
    }
}