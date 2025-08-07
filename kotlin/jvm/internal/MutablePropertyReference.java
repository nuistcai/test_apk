package kotlin.jvm.internal;

import kotlin.reflect.KProperty2;

/* loaded from: classes.dex */
public abstract class MutablePropertyReference extends PropertyReference implements KProperty2 {
    public MutablePropertyReference() {
    }

    public MutablePropertyReference(Object receiver) {
        super(receiver);
    }
}