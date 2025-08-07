package com.squareup.haha.perflib;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class Field {
    private final String mName;
    private final Type mType;

    public Field(Type type, String name) {
        this.mType = type;
        this.mName = name;
    }

    public final Type getType() {
        return this.mType;
    }

    public final String getName() {
        return this.mName;
    }

    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Field)) {
            return false;
        }
        Field field = (Field) o;
        return this.mType == field.mType && this.mName.equals(field.mName);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.mType, this.mName});
    }
}