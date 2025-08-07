package cn.fly.tools.a;

import cn.fly.commons.o;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
class e implements a {
    e() {
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(Class cls, Object obj, String str, Class[] clsArr, Object[] objArr) throws Throwable {
        Method method = (Method) Class.class.getDeclaredMethod(o.a("017Qej+fi'fl^fcgdXdj;f*dchcIfih*dkdc"), String.class, Class[].class).invoke(cls, str, clsArr);
        method.setAccessible(true);
        return (T) method.invoke(obj, objArr);
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str, Object obj, String str2, Class[] clsArr, Object[] objArr) throws Throwable {
        return (T) a((Class) Class.class.getDeclaredMethod(o.a("007?efdkdjeg!d*df f"), String.class).invoke(null, str), obj, str2, clsArr, objArr);
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str) throws Throwable {
        return (T) Class.class.getDeclaredMethod(o.a("011efGfgee)e:fiMidecf"), new Class[0]).invoke((Class) Class.class.getDeclaredMethod(o.a("007Defdkdjeg$d*df9f"), String.class).invoke(null, str), new Object[0]);
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str, String str2, Object obj) throws Throwable {
        Field field = (Field) Class.class.getDeclaredMethod(o.a("016Fej8fi)flJfcgd-djFfEdcgcdi4fgFdc"), String.class).invoke((Class) Class.class.getDeclaredMethod(o.a("0071efdkdjeg<d!dfKf"), String.class).invoke(null, str), str2);
        field.setAccessible(true);
        return (T) field.get(obj);
    }

    @Override // cn.fly.tools.a.a
    public <T> T a(String str, Class[] clsArr, Object[] objArr) throws Throwable {
        if (clsArr == null || clsArr.length == 0 || objArr == null || objArr.length == 0) {
            return (T) a(str);
        }
        Constructor constructor = (Constructor) Class.class.getDeclaredMethod(o.a("0226ej3fi_flVfcgd+dj$f3dceddkReQfiRiCdjdgOciLdkdj"), Class[].class).invoke((Class) Class.class.getDeclaredMethod(o.a("007@efdkdjeg<dSdfWf"), String.class).invoke(null, str), clsArr);
        constructor.setAccessible(true);
        return (T) constructor.newInstance(objArr);
    }
}