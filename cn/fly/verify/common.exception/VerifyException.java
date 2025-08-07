package cn.fly.verify.common.exception;

import cn.fly.tools.utils.ReflectHelper;
import cn.fly.verify.m;

/* loaded from: classes.dex */
public class VerifyException extends Exception {
    protected int code;
    private String extraDesc;
    private VerifyException inner;
    private String message;

    public VerifyException(int i, String str) {
        super(str);
        this.message = str;
        this.code = i;
    }

    @Deprecated
    public VerifyException(int i, String str, Throwable th) {
        super(str, th);
        this.message = str;
        this.code = i;
    }

    public VerifyException(m mVar) {
        super(mVar.b());
        this.message = mVar.b();
        this.code = mVar.a();
    }

    @Deprecated
    public VerifyException(m mVar, Throwable th) {
        super(mVar.b(), th);
        this.message = mVar.b();
        this.code = mVar.a();
    }

    @Deprecated
    public VerifyException(Throwable th) {
        super(th);
    }

    public static boolean isPrivacy(VerifyException verifyException) {
        return verifyException != null && verifyException.getCode() == m.C_PRIVACY_NOT_ACCEPTED_ERROR.a();
    }

    public int getCode() {
        return this.code;
    }

    public String getExtraDesc() {
        return this.extraDesc;
    }

    public VerifyException getInner() {
        return this.inner;
    }

    public void setExtraDesc(String str) {
        this.extraDesc = str;
        setMessage(this.message + ": " + str);
    }

    public void setInner(VerifyException verifyException) {
        this.inner = verifyException;
    }

    public void setMessage(String str) {
        this.message = str;
        try {
            ReflectHelper.setInstanceField(this, "detailMessage", str);
        } catch (Throwable th) {
        }
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "{\"code\": " + this.code + ", \"message\": \"" + getMessage() + "\"}";
    }
}