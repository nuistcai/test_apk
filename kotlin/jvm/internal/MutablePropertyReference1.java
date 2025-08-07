package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty4;
import kotlin.reflect.KProperty7;

/* loaded from: classes.dex */
public abstract class MutablePropertyReference1 extends MutablePropertyReference implements KProperty4 {
    public MutablePropertyReference1() {
    }

    public MutablePropertyReference1(Object receiver) {
        super(receiver);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        return Reflection.mutableProperty1(this);
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object receiver) {
        return get(receiver);
    }

    @Override // kotlin.reflect.KProperty
    public KProperty7.Getter getGetter() {
        return ((KProperty4) getReflected()).getGetter();
    }

    @Override // kotlin.reflect.KProperty2
    public KProperty4.Setter getSetter() {
        return ((KProperty4) getReflected()).getSetter();
    }

    @Override // kotlin.reflect.KProperty7
    public Object getDelegate(Object receiver) {
        return ((KProperty4) getReflected()).getDelegate(receiver);
    }
}