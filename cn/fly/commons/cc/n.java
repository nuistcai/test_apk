package cn.fly.commons.cc;

import android.os.Parcelable;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class n implements s<n> {
    @Override // cn.fly.commons.cc.s
    public boolean a(n nVar, Class<n> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if (objArr == null) {
            return false;
        }
        if (cn.fly.commons.m.a("002l2hk").equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (String) objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (String) objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if ("pbl".equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Boolean) objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Boolean) objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if (cn.fly.commons.m.a("002li").equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Long) objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Long) objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if ("pin".equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Integer) objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Integer) objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if ("pdou".equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Double) objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Double) objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if ("pparm".equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Map) objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Map) objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if ("ppar".equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Parcelable) objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Parcelable) objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if ("pparl".equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (List) objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (List) objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if ("ppararr".equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Parcelable[]) objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], (Parcelable[]) objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if ("p".equals(str)) {
            if (objArr.length == 2) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], objArr[1]);
            } else if (objArr.length == 3) {
                cn.fly.tools.utils.g.a().a((String) objArr[0], objArr[1], ((Long) objArr[2]).longValue());
            }
        } else if ("g".equals(str)) {
            try {
                if (objArr.length == 1) {
                    objArr2[0] = cn.fly.tools.utils.g.a().h((String) objArr[0]);
                } else if (objArr.length == 2) {
                    objArr2[0] = cn.fly.tools.utils.g.a().c((String) objArr[0], objArr[1]);
                }
            } catch (Throwable th) {
                thArr[0] = th;
            }
        } else if ("gs".equals(str)) {
            try {
                if (objArr.length == 1) {
                    objArr2[0] = cn.fly.tools.utils.g.a().b((String) objArr[0]);
                } else if (objArr.length == 2) {
                    objArr2[0] = cn.fly.tools.utils.g.a().c((String) objArr[0], (String) objArr[1]);
                }
            } catch (Throwable th2) {
                thArr[0] = th2;
            }
        } else if ("gbl".equals(str)) {
            try {
                if (objArr.length == 1) {
                    objArr2[0] = Boolean.valueOf(cn.fly.tools.utils.g.a().c((String) objArr[0]));
                } else if (objArr.length == 2) {
                    objArr2[0] = Boolean.valueOf(cn.fly.tools.utils.g.a().a((String) objArr[0], ((Boolean) objArr[1]).booleanValue()));
                }
            } catch (Throwable th3) {
                thArr[0] = th3;
            }
        } else if ("gl".equals(str)) {
            try {
                if (objArr.length == 1) {
                    objArr2[0] = Long.valueOf(cn.fly.tools.utils.g.a().e((String) objArr[0]));
                } else if (objArr.length == 2) {
                    objArr2[0] = Long.valueOf(cn.fly.tools.utils.g.a().a((String) objArr[0], ((Long) objArr[1]).longValue()));
                }
            } catch (Throwable th4) {
                thArr[0] = th4;
            }
        } else if ("gin".equals(str)) {
            try {
                if (objArr.length == 1) {
                    objArr2[0] = Integer.valueOf(cn.fly.tools.utils.g.a().f((String) objArr[0]));
                } else if (objArr.length == 2) {
                    objArr2[0] = Integer.valueOf(cn.fly.tools.utils.g.a().a((String) objArr[0], ((Integer) objArr[1]).intValue()));
                }
            } catch (Throwable th5) {
                thArr[0] = th5;
            }
        } else if ("gdou".equals(str)) {
            try {
                if (objArr.length == 1) {
                    objArr2[0] = Double.valueOf(cn.fly.tools.utils.g.a().g((String) objArr[0]));
                } else if (objArr.length == 2) {
                    objArr2[0] = Double.valueOf(cn.fly.tools.utils.g.a().a((String) objArr[0], ((Double) objArr[1]).doubleValue()));
                }
            } catch (Throwable th6) {
                thArr[0] = th6;
            }
        } else if ("gpar".equals(str)) {
            try {
                if (objArr.length == 2) {
                    objArr2[0] = cn.fly.tools.utils.g.a().a((String) objArr[0], (Class) objArr[1]);
                } else if (objArr.length == 3) {
                    objArr2[0] = cn.fly.tools.utils.g.a().a((String) objArr[0], (Class<Class>) objArr[1], (Class) objArr[2]);
                }
            } catch (Throwable th7) {
                thArr[0] = th7;
            }
        } else if ("gparm".equals(str)) {
            try {
                if (objArr.length == 2) {
                    objArr2[0] = cn.fly.tools.utils.g.a().b((String) objArr[0], (Class) objArr[1]);
                } else if (objArr.length == 3) {
                    objArr2[0] = cn.fly.tools.utils.g.a().a((String) objArr[0], (Class) objArr[1], (Map) objArr[2]);
                }
            } catch (Throwable th8) {
                thArr[0] = th8;
            }
        } else if ("gparl".equals(str)) {
            try {
                if (objArr.length == 2) {
                    objArr2[0] = cn.fly.tools.utils.g.a().c((String) objArr[0], (Class) objArr[1]);
                } else if (objArr.length == 3) {
                    objArr2[0] = cn.fly.tools.utils.g.a().a((String) objArr[0], (Class) objArr[1], (List) objArr[2]);
                }
            } catch (Throwable th9) {
                thArr[0] = th9;
            }
        } else if ("gpararr".equals(str)) {
            try {
                if (objArr.length == 2) {
                    objArr2[0] = cn.fly.tools.utils.g.a().d((String) objArr[0], (Class) objArr[1]);
                } else if (objArr.length == 3) {
                    objArr2[0] = cn.fly.tools.utils.g.a().a((String) objArr[0], (Class) objArr[1], (Parcelable[]) objArr[2]);
                }
            } catch (Throwable th10) {
                thArr[0] = th10;
            }
        } else if (!"rv".equals(str) || objArr.length != 1) {
            thArr[0] = new IllegalArgumentException("wrp");
        } else {
            cn.fly.tools.utils.g.a().i((String) objArr[0]);
        }
        return true;
    }
}