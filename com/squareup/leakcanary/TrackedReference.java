package com.squareup.leakcanary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class TrackedReference {
    public final String className;
    public final List<String> fields;
    public final String key;
    public final String name;

    public TrackedReference(String key, String name, String className, List<String> fields) {
        this.key = key;
        this.name = name;
        this.className = className;
        this.fields = Collections.unmodifiableList(new ArrayList(fields));
    }
}