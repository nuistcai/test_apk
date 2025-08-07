package com.squareup.leakcanary;

import com.squareup.leakcanary.ExcludedRefs;
import java.io.Serializable;

/* loaded from: classes.dex */
public final class Exclusion implements Serializable {
    public final boolean alwaysExclude;
    public final String matching;
    public final String name;
    public final String reason;

    Exclusion(ExcludedRefs.ParamsBuilder builder) {
        this.name = builder.name;
        this.reason = builder.reason;
        this.alwaysExclude = builder.alwaysExclude;
        this.matching = builder.matching;
    }
}