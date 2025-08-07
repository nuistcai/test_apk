package cn.com.chinatelecom.account.api;

/* loaded from: classes.dex */
public class CtSetting {
    private static final int DEFAULT_CONN_TIMEOUT = 5000;
    private static final int DEFAULT_READ_TIMEOUT = 5000;
    private static final int DEFAULT_TOTAL_TIMEOUT = 10000;
    private int connTimeout;
    private int readTimeout;
    private int totalTimeout;

    public CtSetting() {
        this.totalTimeout = 0;
        this.connTimeout = 0;
        this.readTimeout = 0;
    }

    public CtSetting(int i, int i2, int i3) {
        this.totalTimeout = 0;
        this.connTimeout = 0;
        this.readTimeout = 0;
        this.connTimeout = i;
        this.readTimeout = i2;
        this.totalTimeout = i3;
    }

    public static int getConnTimeout(CtSetting ctSetting) {
        if (ctSetting == null || ctSetting.connTimeout <= 0) {
            return 5000;
        }
        return ctSetting.connTimeout;
    }

    public static int getReadTimeout(CtSetting ctSetting) {
        if (ctSetting == null || ctSetting.readTimeout <= 0) {
            return 5000;
        }
        return ctSetting.readTimeout;
    }

    public static int getTotalTimeout(CtSetting ctSetting) {
        return (ctSetting == null || ctSetting.totalTimeout <= 0) ? DEFAULT_TOTAL_TIMEOUT : ctSetting.totalTimeout;
    }

    public void setConnTimeout(int i) {
        this.connTimeout = i;
    }

    public void setReadTimeout(int i) {
        this.readTimeout = i;
    }

    public void setTotalTimeout(int i) {
        this.totalTimeout = i;
    }
}