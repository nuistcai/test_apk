package com.unicom.online.account.kernel;

import java.util.HashMap;

/* loaded from: classes.dex */
public final class g {
    public String a;
    public long b;
    public long c;
    public long d;

    public final HashMap<String, Object> a() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("url", this.a);
        map.put("startTime", new StringBuilder().append(this.b).toString());
        map.put("endTime", new StringBuilder().append(this.c).toString());
        map.put("forcedTime", new StringBuilder().append(this.d).toString());
        return map;
    }

    public final void b() {
        this.a = "";
        this.b = 0L;
        this.c = 0L;
        this.d = 0L;
    }

    public final String toString() {
        return "{url':" + this.a + "', startTime:" + this.b + ", endTime:" + this.c + '}';
    }
}