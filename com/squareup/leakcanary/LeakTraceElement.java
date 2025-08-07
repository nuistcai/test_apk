package com.squareup.leakcanary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public final class LeakTraceElement implements Serializable {
    public final String className;
    public final Exclusion exclusion;
    public final String extra;
    public final List<String> fields;
    public final Holder holder;
    public final String referenceName;
    public final Type type;

    public enum Holder {
        OBJECT,
        CLASS,
        THREAD,
        ARRAY
    }

    public enum Type {
        INSTANCE_FIELD,
        STATIC_FIELD,
        LOCAL,
        ARRAY_ENTRY
    }

    LeakTraceElement(String referenceName, Type type, Holder holder, String className, String extra, Exclusion exclusion, List<String> fields) {
        this.referenceName = referenceName;
        this.type = type;
        this.holder = holder;
        this.className = className;
        this.extra = extra;
        this.exclusion = exclusion;
        this.fields = Collections.unmodifiableList(new ArrayList(fields));
    }

    public String toString() {
        String string;
        String string2 = this.type == Type.STATIC_FIELD ? "static " : "";
        if (this.holder == Holder.ARRAY || this.holder == Holder.THREAD) {
            string2 = string2 + this.holder.name().toLowerCase(Locale.US) + " ";
        }
        String string3 = string2 + this.className;
        if (this.referenceName != null) {
            string = string3 + "." + this.referenceName;
        } else {
            string = string3 + " instance";
        }
        if (this.extra != null) {
            string = string + " " + this.extra;
        }
        if (this.exclusion != null) {
            return string + " , matching exclusion " + this.exclusion.matching;
        }
        return string;
    }

    public String toDetailedString() {
        String string = this.holder == Holder.ARRAY ? "* Array of" : this.holder == Holder.CLASS ? "* Class" : "* Instance of";
        String string2 = string + " " + this.className + "\n";
        for (String field : this.fields) {
            string2 = string2 + "|   " + field + "\n";
        }
        return string2;
    }
}