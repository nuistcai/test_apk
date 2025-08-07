package cn.fly.commons.cc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/* loaded from: classes.dex */
public class r {
    private t a;
    private HashMap<String, Object> c;
    private r e;
    private boolean f;
    private LinkedList<Object> b = new LinkedList<>();
    private HashMap<String, Class<?>> d = new HashMap<>();

    public r(HashMap<String, Object> map, t tVar) {
        this.a = tVar;
        this.c = new HashMap<>(map);
    }

    public void a(Object obj) {
        this.b.push(obj);
    }

    public Object a() {
        return this.b.pop();
    }

    public void a(String str, Object obj) {
        if (this.c.containsKey(str)) {
            throw new RuntimeException("\"" + str + "\" has defined");
        }
        this.c.put(str, obj);
    }

    public void b(String str, Object obj) {
        if (this.c.containsKey(str)) {
            this.c.put(str, obj);
        } else {
            if (this.e != null) {
                this.e.b(str, obj);
                return;
            }
            throw new RuntimeException("\"" + str + "\" has not defined");
        }
    }

    public Object a(String str) {
        for (r rVar = this; rVar != null; rVar = rVar.e) {
            if (rVar.c.containsKey(str)) {
                return rVar.c.get(str);
            }
        }
        throw new RuntimeException("Can not find \"" + str + "\"");
    }

    public void a(String str, Class<?> cls) {
        this.d.put(str, cls);
    }

    public Class<?> b(String str) {
        for (r rVar = this; rVar != null; rVar = rVar.e) {
            if (rVar.d.containsKey(str)) {
                return rVar.d.get(str);
            }
        }
        throw new RuntimeException("Can not find class " + str);
    }

    public r b() {
        r rVar = new r(new HashMap(), this.a);
        rVar.e = this;
        return rVar;
    }

    public r c() {
        return this.e;
    }

    public int d() {
        return this.b.size();
    }

    public void e() {
        this.f = true;
    }

    public boolean f() {
        return this.f;
    }

    public t g() {
        return this.a;
    }

    public Object a(final Object obj, final boolean z, Class<?>... clsArr) {
        return Proxy.newProxyInstance(getClass().getClassLoader(), clsArr, new InvocationHandler() { // from class: cn.fly.commons.cc.r.1
            @Override // java.lang.reflect.InvocationHandler
            public Object invoke(Object obj2, Method method, Object[] objArr) throws Throwable {
                Throwable th;
                y yVar;
                LinkedList<Object> linkedListB;
                try {
                    if (obj == null) {
                        yVar = null;
                    } else if (obj instanceof y) {
                        yVar = (y) obj;
                    } else {
                        yVar = (y) ((Map) obj).get(method.getName());
                    }
                    if (yVar == null) {
                        th = null;
                    } else {
                        if (objArr == null) {
                            objArr = new Object[0];
                        }
                        if (z) {
                            linkedListB = yVar.b(objArr);
                        } else {
                            try {
                                linkedListB = yVar.b(objArr);
                            } finally {
                                th = th;
                                try {
                                } catch (Throwable th2) {
                                }
                            }
                        }
                        if (linkedListB.isEmpty()) {
                            return null;
                        }
                        return linkedListB.get(0);
                    }
                } catch (Throwable th3) {
                    th = null;
                }
                if (th == null) {
                    return null;
                }
                throw th;
            }
        });
    }

    public void a(Method method, int i) throws Throwable {
        Object[] objArr = new Object[i];
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = a();
        }
        a(method, objArr);
    }

    public void a(Method method, Object[] objArr) throws Throwable {
        Object obj;
        if (Modifier.isStatic(method.getModifiers())) {
            obj = null;
        } else if (objArr.length > 0) {
            obj = objArr[0];
            int length = objArr.length - 1;
            Object[] objArr2 = new Object[length];
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                objArr2[i] = objArr[i2];
                i = i2;
            }
            objArr = objArr2;
        } else {
            throw new RuntimeException("receiver not found");
        }
        method.setAccessible(true);
        for (int i3 = 0; i3 < objArr.length; i3++) {
            if (method.getParameterTypes()[i3].isInterface() && (objArr[i3] instanceof y)) {
                objArr[i3] = a(objArr[i3], true, method.getParameterTypes()[i3]);
            }
        }
        if (method.getReturnType() == Void.TYPE) {
            method.invoke(obj, objArr);
        } else {
            a(method.invoke(obj, objArr));
        }
    }
}