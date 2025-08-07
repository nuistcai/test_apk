package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty8;

/* loaded from: classes.dex */
public abstract class PropertyReference2 extends PropertyReference implements KProperty8 {
    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        return Reflection.property2(this);
    }

    @Override // kotlin.jvm.functions.Function2
    public Object invoke(Object receiver1, Object receiver2) {
        return get(receiver1, receiver2);
    }

    @Override // kotlin.reflect.KProperty
    public KProperty8.Getter getGetter() {
        return ((KProperty8) getReflected()).getGetter();
    }

    @Override // kotlin.reflect.KProperty8
    public Object getDelegate(Object receiver1, Object receiver2) {
        return ((KProperty8) getReflected()).getDelegate(receiver1, receiver2);
    }
}