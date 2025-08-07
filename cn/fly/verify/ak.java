package cn.fly.verify;

/* loaded from: classes.dex */
public class ak {
    private static ak a = null;
    private String b;

    private ak() {
    }

    public static ak a() {
        if (a == null) {
            a = new ak();
        }
        return a;
    }

    public void a(int i) {
        String str;
        switch (i) {
            case 1:
                str = "CMCC";
                break;
            case 2:
                str = "CUCC";
                break;
            case 3:
                str = "CTCC";
                break;
            default:
                return;
        }
        this.b = str;
    }
}