package cn.fly.commons.cc;

import cn.fly.commons.cc.x;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class v {
    static final HashMap<String, Class<?>> a = new HashMap<>();
    private final ArrayList<x> b;
    private final ArrayList<Object> c;

    public v(ArrayList<x> arrayList, ArrayList<Object> arrayList2) {
        this.b = arrayList;
        this.c = arrayList2;
    }

    public void a(HashMap<String, Object> map, t tVar) throws Throwable {
        r rVar = new r(map, tVar);
        a(rVar);
        a(0, this.b.size(), rVar, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001e, code lost:
    
        r0.d = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(int i, int i2, r rVar, List<Object> list) throws Throwable {
        String simpleName;
        x.a aVar = new x.a();
        aVar.a = i;
        aVar.b = rVar;
        aVar.c = list;
        aVar.f = this.b;
        aVar.g = this.c;
        while (true) {
            try {
                if (aVar.a >= i2) {
                    break;
                }
                if (rVar.f()) {
                    break;
                }
                this.b.get(aVar.a).a(aVar);
                if (aVar.e) {
                    break;
                } else {
                    aVar.a++;
                }
            } catch (Throwable th) {
                th = th;
                if (th instanceof u) {
                    simpleName = th.getMessage() == null ? th.getClass().getSimpleName() : th.getMessage();
                    th = th.getCause();
                } else {
                    simpleName = "Suba Runtime Error: " + (th.getMessage() == null ? th.getClass().getSimpleName() : th.getMessage());
                }
                throw new u(simpleName + "\r\n\tat " + this.b.get(aVar.a).b + " (" + this.b.get(aVar.a).c + ")", th);
            }
        }
        if (!aVar.d && rVar.d() > 0 && list != null) {
            try {
                list.add(rVar.a());
            } catch (Throwable th2) {
            }
        }
    }

    private void a(r rVar) {
        rVar.a("Object", Object.class);
        rVar.a("Class", Class.class);
        rVar.a("Method", Method.class);
        rVar.a("String", String.class);
        rVar.a("Thread", Thread.class);
        rVar.a(cn.fly.commons.o.a("008ZgjdgKeedRffIgf"), Runnable.class);
        rVar.a(cn.fly.commons.o.a("006!elecfiVif8df"), System.class);
        rVar.a("File", File.class);
        rVar.a("URL", URL.class);
        rVar.a("Double", Double.class);
        rVar.a("Float", Float.class);
        rVar.a("Long", Long.class);
        rVar.a("Integer", Integer.class);
        rVar.a(cn.fly.commons.o.a("0054elOh,dkdj+i"), Short.class);
        rVar.a("Byte", Byte.class);
        rVar.a("Number", Number.class);
        rVar.a(cn.fly.commons.o.a("0091ed>hdNdj;dcif3dj"), Character.class);
        rVar.a("Boolean", Boolean.class);
        rVar.a(cn.fly.commons.o.a("0060dcdkdgff-gf"), Double.TYPE);
        rVar.a(cn.fly.commons.o.a("0057ef[gRdk@di"), Float.TYPE);
        rVar.a("long", Long.TYPE);
        rVar.a(cn.fly.commons.o.a("003.diWei"), Integer.TYPE);
        rVar.a("short", Short.TYPE);
        rVar.a("byte", Byte.TYPE);
        rVar.a(cn.fly.commons.o.a("004chd3dj"), Character.TYPE);
        rVar.a("boolean", Boolean.TYPE);
        rVar.a("bigInt", BigInteger.class);
        rVar.a("BigInteger", BigInteger.class);
        rVar.a("bigDec", BigDecimal.class);
        rVar.a("BigDecimal", BigDecimal.class);
        rVar.a("List", List.class);
        rVar.a("Map", Map.class);
        rVar.a("Function", y.class);
        rVar.a("fun", y.class);
        rVar.a("Range", z.class);
        rVar.a("Array", Array.class);
        rVar.a("Suba", w.class);
        rVar.a("VM", w.class);
        for (Map.Entry<String, Class<?>> entry : a.entrySet()) {
            rVar.a(entry.getKey(), entry.getValue());
        }
    }

    public ArrayList<x> a() {
        return this.b;
    }
}