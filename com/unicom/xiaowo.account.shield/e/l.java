package com.unicom.xiaowo.account.shield.e;

/* loaded from: classes.dex */
public class l {
    public static byte[] a() {
        try {
            byte[] bArr = new byte[15416];
            int[] iArr = new int[3854];
            int length = 0;
            for (int i = 0; i < 1; i++) {
                int[] iArr2 = (int[]) com.unicom.xiaowo.account.shield.b.a.a(i);
                System.arraycopy(iArr2, 0, iArr, length, iArr2.length);
                length += iArr2.length;
            }
            byte[] bArrA = m.a(iArr);
            System.arraycopy(bArrA, 0, bArr, 0, bArrA.length);
            return bArr;
        } catch (Exception e) {
            return null;
        }
    }
}