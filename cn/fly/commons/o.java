package cn.fly.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class o {
    private static o a;
    private HashMap<String, Object> b;

    private o() {
        this.b = c();
        if (this.b == null) {
            this.b = new HashMap<>();
        }
        ArrayList<FlyProduct> arrayListB = ac.b();
        if (arrayListB != null && !arrayListB.isEmpty()) {
            Iterator<FlyProduct> it = arrayListB.iterator();
            while (it.hasNext()) {
                FlyProduct next = it.next();
                if (!this.b.containsKey(next.getProductTag())) {
                    this.b.put(next.getProductTag(), 0);
                }
            }
        }
    }

    public static o a() {
        if (a == null) {
            synchronized (o.class) {
                if (a == null) {
                    a = new o();
                }
            }
        }
        return a;
    }

    public void a(FlyProduct flyProduct, int i) {
        if (flyProduct != null) {
            this.b.put(flyProduct.getProductTag(), Integer.valueOf(i));
            a(this.b);
        }
    }

    public HashMap<String, Object> b() {
        return this.b;
    }

    private HashMap<String, Object> c() {
        try {
            return ad.b().h();
        } catch (Throwable th) {
            return null;
        }
    }

    private void a(HashMap<String, Object> map) {
        try {
            ad.b().b(map);
        } catch (Throwable th) {
        }
    }

    public static String a(String str) {
        return C0041r.a(str, 99);
    }
}