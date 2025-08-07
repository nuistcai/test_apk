package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty7;

/* loaded from: classes.dex */
public abstract class PropertyReference1 extends PropertyReference implements KProperty7 {
    public PropertyReference1() {
    }

    public PropertyReference1(Object receiver) {
        super(receiver);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        return Reflection.property1(this);
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object receiver) {
        return get(receiver);
    }

    @Override // kotlin.reflect.KProperty
    public KProperty7.Getter getGetter() {
        return ((KProperty7) getReflected()).getGetter();
    }

    @Override // kotlin.reflect.KProperty7
    public Object getDelegate(Object receiver) {
        return ((KProperty7) getReflected()).getDelegate(receiver);
    }
}