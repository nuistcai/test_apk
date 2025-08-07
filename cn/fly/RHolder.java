package cn.fly;

import cn.fly.tools.proguard.PublicMemberKeeper;

@Deprecated
/* loaded from: classes.dex */
public class RHolder implements PublicMemberKeeper {
    private static volatile RHolder d;
    protected int a;
    protected int b;
    protected int c;

    protected RHolder() {
    }

    public static RHolder getInstance() {
        if (d == null) {
            synchronized (RHolder.class) {
                if (d == null) {
                    d = new RHolder();
                }
            }
        }
        return d;
    }

    public RHolder setActivityThemeId(int i) {
        this.a = i;
        return d;
    }

    public int getActivityThemeId() {
        return this.a;
    }

    public RHolder setDialogLayoutId(int i) {
        this.b = i;
        return d;
    }

    public int getDialogLayoutId() {
        return this.b;
    }

    public RHolder setDialogThemeId(int i) {
        this.c = i;
        return d;
    }

    public int getDialogThemeId() {
        return this.c;
    }
}