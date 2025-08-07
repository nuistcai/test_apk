package cn.fly.commons.cc;

import android.content.Intent;
import android.content.pm.PackageManager;

/* loaded from: classes.dex */
public class e implements s<PackageManager> {
    @Override // cn.fly.commons.cc.s
    public boolean a(PackageManager packageManager, Class<PackageManager> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if (cn.fly.commons.a.l.a("019Yefeh?g!ekfdff^fjgfjUfmDgFekeeejAdg=gj").equals(str) && objArr.length == 2 && (objArr[0] instanceof Intent) && (objArr[1] instanceof Integer)) {
            objArr2[0] = packageManager.queryIntentServices((Intent) objArr[0], ((Integer) objArr[1]).intValue());
            return true;
        }
        if (cn.fly.commons.a.l.a("025!fkMgjOgf0e=eh0fdiBff1fjgfj8hdelekhmOed@fi eYfk-g").equals(str) && objArr.length == 1 && (objArr[0] instanceof String)) {
            objArr2[0] = packageManager.getLaunchIntentForPackage((String) objArr[0]);
            return true;
        }
        if (!cn.fly.commons.a.l.a("015YekRg2gjelHh*ee>g@geXdj0ejeeej(j'fd").equals(str) || objArr.length != 2 || !(objArr[0] instanceof Integer) || !(objArr[1] instanceof Integer)) {
            return false;
        }
        objArr2[0] = packageManager.resolveActivity((Intent) objArr[0], ((Integer) objArr[1]).intValue());
        return true;
    }
}