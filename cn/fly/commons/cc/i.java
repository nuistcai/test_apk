package cn.fly.commons.cc;

/* loaded from: classes.dex */
public class i implements s<i> {
    @Override // cn.fly.commons.cc.s
    public boolean a(i iVar, Class<i> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if ("andOperation".equals(str) && objArr.length == 2) {
            if (objArr[0] != null && (objArr[0] instanceof Integer) && objArr[1] != null && (objArr[1] instanceof Integer)) {
                objArr2[0] = Integer.valueOf(((Integer) objArr[0]).intValue() & ((Integer) objArr[1]).intValue());
            } else if (objArr[0] != null && (objArr[0] instanceof Long) && objArr[1] != null && (objArr[1] instanceof Long)) {
                objArr2[0] = Long.valueOf(((Long) objArr[0]).longValue() & ((Long) objArr[1]).longValue());
            }
        } else if ("orOperation".equals(str) && objArr.length == 2) {
            if (objArr[0] != null && (objArr[0] instanceof Integer) && objArr[1] != null && (objArr[1] instanceof Integer)) {
                objArr2[0] = Integer.valueOf(((Integer) objArr[0]).intValue() | ((Integer) objArr[1]).intValue());
            } else if (objArr[0] != null && (objArr[0] instanceof Long) && objArr[1] != null && (objArr[1] instanceof Long)) {
                objArr2[0] = Long.valueOf(((Long) objArr[0]).longValue() | ((Long) objArr[1]).longValue());
            }
        } else if ("rMoveOperation".equals(str) && objArr.length == 2) {
            if (objArr[0] != null && (objArr[0] instanceof Integer) && objArr[1] != null && (objArr[1] instanceof Integer)) {
                objArr2[0] = Integer.valueOf(((Integer) objArr[0]).intValue() >> ((Integer) objArr[1]).intValue());
            } else if (objArr[0] != null && (objArr[0] instanceof Long) && objArr[1] != null && (objArr[1] instanceof Long)) {
                objArr2[0] = Long.valueOf(((Long) objArr[0]).longValue() >> ((int) ((Long) objArr[1]).longValue()));
            }
        } else if ("rrrMoveOperation".equals(str) && objArr.length == 2) {
            if (objArr[0] != null && (objArr[0] instanceof Integer) && objArr[1] != null && (objArr[1] instanceof Integer)) {
                objArr2[0] = Integer.valueOf(((Integer) objArr[0]).intValue() >>> ((Integer) objArr[1]).intValue());
            } else if (objArr[0] != null && (objArr[0] instanceof Long) && objArr[1] != null && (objArr[1] instanceof Long)) {
                objArr2[0] = Long.valueOf(((Long) objArr[0]).longValue() >>> ((int) ((Long) objArr[1]).longValue()));
            }
        } else if ("lMoveOperation".equals(str) && objArr.length == 2) {
            if (objArr[0] != null && (objArr[0] instanceof Integer) && objArr[1] != null && (objArr[1] instanceof Integer)) {
                objArr2[0] = Integer.valueOf(((Integer) objArr[0]).intValue() << ((Integer) objArr[1]).intValue());
            } else if (objArr[0] != null && (objArr[0] instanceof Long) && objArr[1] != null && (objArr[1] instanceof Long)) {
                objArr2[0] = Long.valueOf(((Long) objArr[0]).longValue() << ((int) ((Long) objArr[1]).longValue()));
            }
        } else if ("xOperation".equals(str) && objArr.length == 1) {
            if (objArr[0] != null && (objArr[0] instanceof Integer)) {
                objArr2[0] = Integer.valueOf(((Integer) objArr[0]).intValue() ^ (-1));
            } else if (objArr[0] != null && (objArr[0] instanceof Long)) {
                objArr2[0] = Long.valueOf(((Long) objArr[0]).longValue() ^ (-1));
            }
        } else {
            if (!"xorOperation".equals(str) || objArr.length != 2) {
                return false;
            }
            if (objArr[0] != null && (objArr[0] instanceof Integer) && objArr[1] != null && (objArr[1] instanceof Integer)) {
                objArr2[0] = Integer.valueOf(((Integer) objArr[0]).intValue() ^ ((Integer) objArr[1]).intValue());
            } else if (objArr[0] != null && (objArr[0] instanceof Long) && objArr[1] != null && (objArr[1] instanceof Long)) {
                objArr2[0] = Long.valueOf(((Long) objArr[0]).longValue() ^ ((Long) objArr[1]).longValue());
            }
        }
        return true;
    }
}