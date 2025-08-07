package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty5;
import kotlin.reflect.KProperty8;

/* loaded from: classes.dex */
public abstract class MutablePropertyReference2 extends MutablePropertyReference implements KProperty5 {
    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        return Reflection.mutableProperty2(this);
    }

    @Override // kotlin.jvm.functions.Function2
    public Object invoke(Object receiver1, Object receiver2) {
        return get(receiver1, receiver2);
    }

    @Override // kotlin.reflect.KProperty
    public KProperty8.Getter getGetter() {
        return ((KProperty5) getReflected()).getGetter();
    }

    @Override // kotlin.reflect.KProperty2
    public KProperty5.Setter getSetter() {
        return ((KProperty5) getReflected()).getSetter();
    }

    @Override // kotlin.reflect.KProperty8
    public Object getDelegate(Object receiver1, Object receiver2) {
        return ((KProperty5) getReflected()).getDelegate(receiver1, receiver2);
    }
}