package cn.fly.commons.c;

import android.content.Context;
import cn.fly.commons.c.h;
import cn.fly.tools.FlyLog;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class q extends h {
    public q(Context context) {
        super(context);
    }

    @Override // cn.fly.commons.c.h
    protected h.b b() {
        Class<?> cls;
        Object objNewInstance;
        Method method = null;
        try {
            cls = Class.forName(cn.fly.commons.o.a("034cJdkdfdl9deKdcdjdkdidcdldidcdldidfXjgOdleedcgldjdkdddidcEfVdjeedfRjg"));
        } catch (Throwable th) {
            th = th;
            cls = null;
        }
        try {
            objNewInstance = cls.newInstance();
        } catch (Throwable th2) {
            th = th2;
            FlyLog.getInstance().d(th);
            objNewInstance = null;
            if (cls != null) {
                try {
                    method = cls.getMethod(cn.fly.commons.o.a("007TejQfi<ghfdeefl"), Context.class);
                } catch (Throwable th3) {
                    FlyLog.getInstance().d(th3);
                }
            }
            h.b bVar = new h.b();
            bVar.a = a(this.a, objNewInstance, method);
            return bVar;
        }
        if (cls != null && objNewInstance != null) {
            method = cls.getMethod(cn.fly.commons.o.a("007TejQfi<ghfdeefl"), Context.class);
        }
        h.b bVar2 = new h.b();
        bVar2.a = a(this.a, objNewInstance, method);
        return bVar2;
    }

    private String a(Context context, Object obj, Method method) {
        if (obj != null && method != null) {
            try {
                Object objInvoke = method.invoke(obj, context);
                if (objInvoke != null) {
                    return (String) objInvoke;
                }
                return null;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }
}