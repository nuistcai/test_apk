package cn.fly.tools.utils;

import cn.fly.commons.C0041r;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes.dex */
public class FlyRSA implements PublicMemberKeeper {
    private int a;

    public FlyRSA(int i) {
        this.a = i;
    }

    public byte[] encode(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) throws Throwable {
        int i = this.a / 8;
        int i2 = i - 11;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = null;
        try {
            DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
            int i3 = 0;
            while (bArr.length > i3) {
                try {
                    int iMin = Math.min(bArr.length - i3, i2);
                    byte[] bArrA = a(bArr, i3, iMin, bigInteger, bigInteger2, i);
                    dataOutputStream2.writeInt(bArrA.length);
                    dataOutputStream2.write(bArrA);
                    i3 += iMin;
                } catch (Throwable th) {
                    th = th;
                    dataOutputStream = dataOutputStream2;
                    C0041r.a(dataOutputStream, byteArrayOutputStream);
                    throw th;
                }
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            C0041r.a(dataOutputStream2, byteArrayOutputStream);
            return byteArray;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private byte[] a(byte[] bArr, int i, int i2, BigInteger bigInteger, BigInteger bigInteger2, int i3) throws Throwable {
        if (bArr.length != i2 || i != 0) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            bArr = bArr2;
        }
        BigInteger bigInteger3 = new BigInteger(a(bArr, i3));
        if (bigInteger3.compareTo(bigInteger2) > 0) {
            throw new Throwable("the message must be smaller than the modulue");
        }
        return bigInteger3.modPow(bigInteger, bigInteger2).toByteArray();
    }

    private byte[] a(byte[] bArr, int i) throws Throwable {
        if (bArr.length > i - 1) {
            throw new Throwable("Message too large");
        }
        byte[] bArr2 = new byte[i];
        bArr2[0] = 1;
        int length = bArr.length;
        bArr2[1] = (byte) (length >> 24);
        bArr2[2] = (byte) (length >> 16);
        bArr2[3] = (byte) (length >> 8);
        bArr2[4] = (byte) length;
        SecureRandom secureRandom = new SecureRandom();
        int i2 = 5;
        while (true) {
            int i3 = i - length;
            if (i2 < i3) {
                bArr2[i2] = (byte) (secureRandom.nextInt(256) - 128);
                i2++;
            } else {
                System.arraycopy(bArr, 0, bArr2, i3, length);
                return bArr2;
            }
        }
    }
}