package cn.fly.commons;

import cn.fly.tools.proguard.EverythingKeeper;

/* loaded from: classes.dex */
public enum InternationalDomain implements EverythingKeeper {
    JP("jp", "Japan"),
    US(cn.fly.commons.a.l.a("002.ehgj"), "United States of America"),
    DEFAULT(null, null);

    private String domain;
    private String region;

    InternationalDomain(String str, String str2) {
        this.domain = str;
        this.region = str2;
    }

    public static InternationalDomain domainOf(String str) {
        if (str == null) {
            return DEFAULT;
        }
        for (InternationalDomain internationalDomain : values()) {
            if (str.equalsIgnoreCase(internationalDomain.domain)) {
                return internationalDomain;
            }
        }
        return DEFAULT;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getRegion() {
        return this.region;
    }
}