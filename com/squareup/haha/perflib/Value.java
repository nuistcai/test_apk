package com.squareup.haha.perflib;

/* loaded from: classes.dex */
public class Value {
    private final Instance instance;
    private Object mValue;

    public Value(Instance instance) {
        this.instance = instance;
    }

    public Object getValue() {
        return this.mValue;
    }

    public void setValue(Object value) {
        this.mValue = value;
        if (value instanceof Instance) {
            ((Instance) value).addReference(null, this.instance);
        }
    }
}