package cn.fly.commons.cc;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class l {
    public a a;

    interface a {
        Object a(String str, ArrayList<Object> arrayList);
    }

    public l(a aVar) {
        this.a = aVar;
    }

    public Object a(String str, ArrayList<Object> arrayList) {
        if (this.a == null) {
            return null;
        }
        return this.a.a(str, arrayList);
    }
}