package com.squareup.leakcanary;

import com.squareup.haha.perflib.Instance;
import com.squareup.leakcanary.LeakTraceElement;

/* loaded from: classes.dex */
final class LeakNode {
    final Exclusion exclusion;
    final Instance instance;
    final LeakNode parent;
    final String referenceName;
    final LeakTraceElement.Type referenceType;

    LeakNode(Exclusion exclusion, Instance instance, LeakNode parent, String referenceName, LeakTraceElement.Type referenceType) {
        this.exclusion = exclusion;
        this.instance = instance;
        this.parent = parent;
        this.referenceName = referenceName;
        this.referenceType = referenceType;
    }
}