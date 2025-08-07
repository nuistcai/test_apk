package cn.fly.commons.cc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class y {
    private String a;
    private int b;
    private r c;
    private int d;
    private int e;
    private v f;

    public y(String str, int i, ArrayList<x> arrayList, ArrayList<Object> arrayList2, int i2, int i3, r rVar) {
        this.a = str;
        this.b = i;
        this.f = new v(arrayList, arrayList2);
        this.d = i2;
        this.e = i3;
        this.c = rVar;
    }

    public y a(r rVar, String str, int i) {
        if (this.b <= 1) {
            return this;
        }
        ArrayList<x> arrayList = new ArrayList<>();
        a(str, i, arrayList, 0);
        return new y(null, 1, arrayList, new ArrayList(), 0, arrayList.size(), rVar);
    }

    private void a(String str, int i, ArrayList<x> arrayList, int i2) {
        if (i2 != 0) {
            x xVar = new x(29);
            xVar.b = str;
            xVar.c = i;
            xVar.i = 1;
            arrayList.add(xVar);
        }
        x xVar2 = new x(1);
        xVar2.b = str;
        xVar2.c = i;
        int i3 = i2 + 1;
        xVar2.h = "arg" + i3;
        arrayList.add(xVar2);
        if (i2 >= this.b - 1) {
            for (int i4 = this.b - 1; i4 >= 0; i4--) {
                x xVar3 = new x(3);
                xVar3.b = str;
                xVar3.c = i;
                xVar3.h = "arg" + (i4 + 1);
                arrayList.add(xVar3);
            }
            if (this.a == null) {
                x xVar4 = new x(2);
                xVar4.b = str;
                xVar4.c = i;
                xVar4.q = this;
                arrayList.add(xVar4);
                x xVar5 = new x(32);
                xVar5.b = str;
                xVar5.c = i;
                xVar5.i = this.b;
                arrayList.add(xVar5);
            } else {
                x xVar6 = new x(31);
                xVar6.b = str;
                xVar6.c = i;
                xVar6.h = this.a;
                xVar6.i = this.b;
                arrayList.add(xVar6);
            }
            Iterator<x> it = this.f.a().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (it.next().a == 28) {
                    x xVar7 = new x(28);
                    xVar7.b = str;
                    xVar7.c = i;
                    arrayList.add(xVar7);
                    break;
                }
            }
        } else {
            a(str, i, arrayList, i3);
            x xVar8 = new x(28);
            xVar8.b = str;
            xVar8.c = i;
            arrayList.add(xVar8);
        }
        if (i2 != 0) {
            x xVar9 = new x(30);
            xVar9.b = str;
            xVar9.c = i;
            arrayList.add(xVar9);
        }
    }

    public a a(Object... objArr) {
        a aVar = new a();
        try {
            LinkedList<Object> linkedListB = b(objArr);
            if (!linkedListB.isEmpty()) {
                aVar.b = linkedListB.get(0);
            }
        } catch (Throwable th) {
            aVar.a = th;
        }
        return aVar;
    }

    public LinkedList<Object> b(Object... objArr) throws Throwable {
        r rVarB = this.c.b();
        if (this.b != 0) {
            if (objArr.length == this.b) {
                for (int length = objArr.length - 1; length >= 0; length--) {
                    rVarB.a(objArr[length]);
                }
            } else if (objArr.length < this.b) {
                for (int length2 = objArr.length; length2 < this.b; length2++) {
                    rVarB.a((Object) null);
                }
                for (int length3 = objArr.length - 1; length3 >= 0; length3--) {
                    rVarB.a(objArr[length3]);
                }
            } else {
                ArrayList arrayList = new ArrayList(0);
                for (int i = this.b - 1; i < objArr.length; i++) {
                    arrayList.add(objArr[i]);
                }
                rVarB.a(arrayList);
                for (int i2 = this.b - 2; i2 >= 0; i2--) {
                    rVarB.a(objArr[i2]);
                }
            }
        }
        LinkedList<Object> linkedList = new LinkedList<>();
        this.f.a(this.d, this.e, rVarB, linkedList);
        return linkedList;
    }

    public static y a(String str, int i, ArrayList<x> arrayList, ArrayList<Object> arrayList2, int i2, int i3, r rVar) {
        return new y(str, i, arrayList, arrayList2, i2, i3, rVar) { // from class: cn.fly.commons.cc.y.1
            @Override // cn.fly.commons.cc.y
            public LinkedList<Object> b(Object... objArr) throws Throwable {
                return new LinkedList<>();
            }
        };
    }

    public static class a implements s<a> {
        public Throwable a;
        public Object b;

        public boolean a() {
            return this.a != null;
        }

        @Override // cn.fly.commons.cc.s
        public boolean a(a aVar, Class<a> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
            if ("isError".equals(str) && objArr.length == 0) {
                objArr2[0] = Boolean.valueOf(aVar.a());
                return true;
            }
            if ("getError".equals(str) && objArr.length == 0) {
                objArr2[0] = aVar.a;
                return true;
            }
            if (!"getResult".equals(str) || objArr.length != 0) {
                return false;
            }
            objArr2[0] = aVar.b;
            return true;
        }
    }
}