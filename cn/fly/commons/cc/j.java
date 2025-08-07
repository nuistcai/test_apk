package cn.fly.commons.cc;

import android.database.ContentObserver;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class j extends ContentObserver implements s<j> {
    private l a;

    public j() {
        super(null);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        if (this.a != null) {
            ArrayList<Object> arrayList = new ArrayList<>(1);
            arrayList.add(Boolean.valueOf(z));
            this.a.a("onChange", arrayList);
        }
    }

    public void a(l lVar) {
        this.a = lVar;
    }

    @Override // cn.fly.commons.cc.s
    public boolean a(j jVar, Class<j> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if (!"setHandler".equals(str) || objArr.length != 1 || objArr[0] == null || !(objArr[0] instanceof l)) {
            return false;
        }
        jVar.a((l) objArr[0]);
        return true;
    }
}