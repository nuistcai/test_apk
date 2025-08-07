package cn.fly.apc;

/* loaded from: classes.dex */
public class APCException extends Exception {
    public int errorCode;

    public APCException(String str) {
        super(str);
        this.errorCode = 0;
    }

    public APCException(int i, String str) {
        super(str);
        this.errorCode = 0;
        this.errorCode = i;
    }

    public APCException(Throwable th) {
        super(th);
        this.errorCode = 0;
    }
}