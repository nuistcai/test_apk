package cn.fly.mcl;

import cn.fly.tools.proguard.EverythingKeeper;

/* loaded from: classes.dex */
public class TcpStatus implements EverythingKeeper {
    public static final int TYPE_FORCE_CLOSE = 22;
    public static final int TYPE_INIT_FLOW_EXCEPTION = 23;
    public static final int TYPE_REGISTER_FAILED = 24;
    public static final int TYPE_REGISTER_SUCCESS = 10;
    public static final int TYPE_TCP_UNAVAILABLE = 21;
    public int code;
    public String detailedMsg;
    public String msg;

    private TcpStatus(int i, String str) {
        this.code = i;
        this.msg = str;
    }

    public TcpStatus setDetailedMsg(String str) {
        this.detailedMsg = str;
        return this;
    }

    public static TcpStatus obtain(int i) {
        String str;
        switch (i) {
            case 10:
                str = "10:tcp register success";
                break;
            case 21:
                str = "21:tcp unavailable";
                break;
            case 22:
                str = "22:tcp force close(rare status)";
                break;
            case 23:
                str = "23:tcp init flow exception(rare status)";
                break;
            case 24:
                str = "24:register failed";
                break;
            default:
                str = "0:unknown(rare status)";
                break;
        }
        String[] strArrSplit = str.split(":");
        return new TcpStatus(Integer.parseInt(strArrSplit[0]), strArrSplit[1]);
    }

    public String toString() {
        return "TcpStatus[code: " + this.code + ", msg: " + this.msg + ", detailedMsg: " + this.detailedMsg + "]";
    }
}