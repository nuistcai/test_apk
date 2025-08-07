package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty6;

/* loaded from: classes.dex */
public abstract class PropertyReference0 extends PropertyReference implements KProperty6 {
    public PropertyReference0() {
    }

    public PropertyReference0(Object receiver) {
        super(receiver);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        return Reflection.property0(this);
    }

    @Override // kotlin.jvm.functions.Function0
    public Object invoke() {
        return get();
    }

    @Override // kotlin.reflect.KProperty
    public KProperty6.Getter getGetter() {
        return ((KProperty6) getReflected()).getGetter();
    }

    @Override // kotlin.reflect.KProperty6
    public Object getDelegate() {
        return ((KProperty6) getReflected()).getDelegate();
    }
}