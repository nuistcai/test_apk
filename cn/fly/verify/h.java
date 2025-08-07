package cn.fly.verify;

/* loaded from: classes.dex */
public class h {
    public static w a(int i, String str, String str2, int i2, Integer num, String str3, u uVar) {
        w wVarA;
        if (i2 == 1) {
            wVarA = ab.d().a(str, str2);
            wVarA.i = cn.fly.verify.util.l.a(i);
        } else {
            wVarA = i == 1 ? ab.d().a(str, str2) : i == 2 ? ad.d().a(str, str2) : i == 3 ? ac.d().a(str, str2) : null;
        }
        wVarA.d = i2;
        wVarA.e = num;
        wVarA.f = str3;
        wVarA.a(uVar);
        i.a().b(i);
        return wVarA;
    }
}