package com.tencent.bugly.proguard;

import com.mobile.lantian.util.DES;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class ai implements aj {
    private String a = null;

    @Override // com.tencent.bugly.proguard.aj
    public final byte[] a(byte[] bArr) throws Exception {
        if (this.a == null || bArr == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance(DES.PADDING);
        cipher.init(2, SecretKeyFactory.getInstance(DES.ALGORITHM).generateSecret(new DESKeySpec(this.a.getBytes("UTF-8"))), new IvParameterSpec(this.a.getBytes("UTF-8")));
        return cipher.doFinal(bArr);
    }

    @Override // com.tencent.bugly.proguard.aj
    public final byte[] b(byte[] bArr) throws Exception {
        if (this.a == null || bArr == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance(DES.PADDING);
        cipher.init(1, SecretKeyFactory.getInstance(DES.ALGORITHM).generateSecret(new DESKeySpec(this.a.getBytes("UTF-8"))), new IvParameterSpec(this.a.getBytes("UTF-8")));
        return cipher.doFinal(bArr);
    }

    @Override // com.tencent.bugly.proguard.aj
    public final void a(String str) {
        if (str != null) {
            this.a = str;
        }
    }
}