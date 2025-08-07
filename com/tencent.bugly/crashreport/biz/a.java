package com.tencent.bugly.crashreport.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.au;
import com.tencent.bugly.proguard.k;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.t;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class a {
    private Context a;
    private long b;
    private int c;
    private boolean d;

    static /* synthetic */ void a(a aVar, UserInfoBean userInfoBean, boolean z) {
        List<UserInfoBean> listA;
        if (userInfoBean != null) {
            if (!z && userInfoBean.b != 1 && (listA = aVar.a(com.tencent.bugly.crashreport.common.info.a.a(aVar.a).d)) != null && listA.size() >= 20) {
                x.a("[UserInfo] There are too many user info in local: %d", Integer.valueOf(listA.size()));
                return;
            }
            long jA = p.a().a("t_ui", a(userInfoBean), (o) null, true);
            if (jA >= 0) {
                x.c("[Database] insert %s success with ID: %d", "t_ui", Long.valueOf(jA));
                userInfoBean.a = jA;
            }
        }
    }

    public a(Context context, boolean z) {
        this.d = true;
        this.a = context;
        this.d = z;
    }

    public final void a(int i, boolean z, long j) {
        com.tencent.bugly.crashreport.common.strategy.a aVarA = com.tencent.bugly.crashreport.common.strategy.a.a();
        if (aVarA != null && !aVarA.c().h && i != 1 && i != 3) {
            x.e("UserInfo is disable", new Object[0]);
            return;
        }
        if (i == 1 || i == 3) {
            this.c++;
        }
        com.tencent.bugly.crashreport.common.info.a aVarA2 = com.tencent.bugly.crashreport.common.info.a.a(this.a);
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.b = i;
        userInfoBean.c = aVarA2.d;
        userInfoBean.d = aVarA2.g();
        userInfoBean.e = System.currentTimeMillis();
        userInfoBean.f = -1L;
        userInfoBean.n = aVarA2.k;
        userInfoBean.o = i == 1 ? 1 : 0;
        userInfoBean.l = aVarA2.a();
        userInfoBean.m = aVarA2.q;
        userInfoBean.g = aVarA2.r;
        userInfoBean.h = aVarA2.s;
        userInfoBean.i = aVarA2.t;
        userInfoBean.k = aVarA2.u;
        userInfoBean.r = aVarA2.B();
        userInfoBean.s = aVarA2.G();
        userInfoBean.p = aVarA2.H();
        userInfoBean.q = aVarA2.I();
        w.a().a(new RunnableC0039a(userInfoBean, z), 0L);
    }

    public final void a() {
        this.b = z.b() + 86400000;
        w.a().a(new b(), (this.b - System.currentTimeMillis()) + 5000);
    }

    /* compiled from: BUGLY */
    /* renamed from: com.tencent.bugly.crashreport.biz.a$a, reason: collision with other inner class name */
    class RunnableC0039a implements Runnable {
        private boolean a;
        private UserInfoBean b;

        public RunnableC0039a(UserInfoBean userInfoBean, boolean z) {
            this.b = userInfoBean;
            this.a = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            com.tencent.bugly.crashreport.common.info.a aVarB;
            try {
                if (this.b != null) {
                    UserInfoBean userInfoBean = this.b;
                    if (userInfoBean != null && (aVarB = com.tencent.bugly.crashreport.common.info.a.b()) != null) {
                        userInfoBean.j = aVarB.e();
                    }
                    x.c("[UserInfo] Record user info.", new Object[0]);
                    a.a(a.this, this.b, false);
                }
                if (this.a) {
                    a aVar = a.this;
                    w wVarA = w.a();
                    if (wVarA != null) {
                        wVarA.a(aVar.new AnonymousClass2());
                    }
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void c() {
        boolean z;
        if (this.d) {
            u uVarA = u.a();
            if (uVarA == null) {
                return;
            }
            com.tencent.bugly.crashreport.common.strategy.a aVarA = com.tencent.bugly.crashreport.common.strategy.a.a();
            if (aVarA == null) {
                return;
            }
            if (!aVarA.b() || uVarA.b(1001)) {
                String str = com.tencent.bugly.crashreport.common.info.a.a(this.a).d;
                ArrayList arrayList = new ArrayList();
                final List<UserInfoBean> listA = a(str);
                if (listA != null) {
                    int size = listA.size() - 20;
                    if (size > 0) {
                        int i = 0;
                        while (i < listA.size() - 1) {
                            int i2 = i + 1;
                            for (int i3 = i2; i3 < listA.size(); i3++) {
                                if (listA.get(i).e > listA.get(i3).e) {
                                    UserInfoBean userInfoBean = listA.get(i);
                                    listA.set(i, listA.get(i3));
                                    listA.set(i3, userInfoBean);
                                }
                            }
                            i = i2;
                        }
                        for (int i4 = 0; i4 < size; i4++) {
                            arrayList.add(listA.get(i4));
                        }
                    }
                    Iterator<UserInfoBean> it = listA.iterator();
                    int i5 = 0;
                    while (it.hasNext()) {
                        UserInfoBean next = it.next();
                        if (next.f != -1) {
                            it.remove();
                            if (next.e < z.b()) {
                                arrayList.add(next);
                            }
                        }
                        if (next.e > System.currentTimeMillis() - 600000 && (next.b == 1 || next.b == 4 || next.b == 3)) {
                            i5++;
                        }
                    }
                    if (i5 <= 15) {
                        z = true;
                    } else {
                        x.d("[UserInfo] Upload user info too many times in 10 min: %d", Integer.valueOf(i5));
                        z = false;
                    }
                } else {
                    listA = new ArrayList<>();
                    z = true;
                }
                if (arrayList.size() > 0) {
                    a(arrayList);
                }
                if (z && listA.size() != 0) {
                    x.c("[UserInfo] Upload user info(size: %d)", Integer.valueOf(listA.size()));
                    au auVarA = com.tencent.bugly.proguard.a.a(listA, this.c == 1 ? 1 : 2);
                    if (auVarA == null) {
                        x.d("[UserInfo] Failed to create UserInfoPackage.", new Object[0]);
                        return;
                    }
                    byte[] bArrA = com.tencent.bugly.proguard.a.a((k) auVarA);
                    if (bArrA == null) {
                        x.d("[UserInfo] Failed to encode data.", new Object[0]);
                        return;
                    }
                    ap apVarA = com.tencent.bugly.proguard.a.a(this.a, uVarA.a ? 840 : 640, bArrA);
                    if (apVarA == null) {
                        x.d("[UserInfo] Request package is null.", new Object[0]);
                        return;
                    }
                    t tVar = new t() { // from class: com.tencent.bugly.crashreport.biz.a.1
                        @Override // com.tencent.bugly.proguard.t
                        public final void a(boolean z2) {
                            if (z2) {
                                x.c("[UserInfo] Successfully uploaded user info.", new Object[0]);
                                long jCurrentTimeMillis = System.currentTimeMillis();
                                for (UserInfoBean userInfoBean2 : listA) {
                                    userInfoBean2.f = jCurrentTimeMillis;
                                    a.a(a.this, userInfoBean2, true);
                                }
                            }
                        }
                    };
                    StrategyBean strategyBeanC = com.tencent.bugly.crashreport.common.strategy.a.a().c();
                    u.a().a(1001, apVarA, uVarA.a ? strategyBeanC.r : strategyBeanC.t, uVarA.a ? StrategyBean.b : StrategyBean.a, tVar, this.c == 1);
                    return;
                }
                x.c("[UserInfo] There is no user info in local database.", new Object[0]);
            }
        }
    }

    public final void b() {
        w wVarA = w.a();
        if (wVarA != null) {
            wVarA.a(new AnonymousClass2());
        }
    }

    /* compiled from: BUGLY */
    /* renamed from: com.tencent.bugly.crashreport.biz.a$2, reason: invalid class name */
    final class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                a.this.c();
            } catch (Throwable th) {
                x.a(th);
            }
        }
    }

    /* compiled from: BUGLY */
    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis < a.this.b) {
                w.a().a(a.this.new b(), (a.this.b - jCurrentTimeMillis) + 5000);
            } else {
                a.this.a(3, false, 0L);
                a.this.a();
            }
        }
    }

    /* compiled from: BUGLY */
    class c implements Runnable {
        private long a;

        public c(long j) {
            this.a = 21600000L;
            this.a = j;
        }

        @Override // java.lang.Runnable
        public final void run() {
            a aVar = a.this;
            w wVarA = w.a();
            if (wVarA != null) {
                wVarA.a(aVar.new AnonymousClass2());
            }
            a aVar2 = a.this;
            long j = this.a;
            w.a().a(aVar2.new c(j), j);
        }
    }

    public final List<UserInfoBean> a(String str) {
        Cursor cursorA;
        try {
            cursorA = p.a().a("t_ui", null, z.a(str) ? null : "_pc = '" + str + "'", null, null, true);
            if (cursorA == null) {
                return null;
            }
            try {
                StringBuilder sb = new StringBuilder();
                ArrayList arrayList = new ArrayList();
                while (cursorA.moveToNext()) {
                    UserInfoBean userInfoBeanA = a(cursorA);
                    if (userInfoBeanA != null) {
                        arrayList.add(userInfoBeanA);
                    } else {
                        try {
                            sb.append(" or _id").append(" = ").append(cursorA.getLong(cursorA.getColumnIndex("_id")));
                        } catch (Throwable th) {
                            x.d("[Database] unknown id.", new Object[0]);
                        }
                    }
                }
                String string = sb.toString();
                if (string.length() > 0) {
                    x.d("[Database] deleted %s error data %d", "t_ui", Integer.valueOf(p.a().a("t_ui", string.substring(4), (String[]) null, (o) null, true)));
                }
                if (cursorA != null) {
                    cursorA.close();
                }
                return arrayList;
            } catch (Throwable th2) {
                th = th2;
                try {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                    if (cursorA != null) {
                        cursorA.close();
                    }
                    return null;
                } finally {
                    if (cursorA != null) {
                        cursorA.close();
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
            cursorA = null;
        }
    }

    private static void a(List<UserInfoBean> list) {
        String strSubstring;
        if (list == null || list.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() && i < 50; i++) {
            sb.append(" or _id").append(" = ").append(list.get(i).a);
        }
        String string = sb.toString();
        if (string.length() <= 0) {
            strSubstring = string;
        } else {
            strSubstring = string.substring(4);
        }
        sb.setLength(0);
        try {
            x.c("[Database] deleted %s data %d", "t_ui", Integer.valueOf(p.a().a("t_ui", strSubstring, (String[]) null, (o) null, true)));
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    private static ContentValues a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (userInfoBean.a > 0) {
                contentValues.put("_id", Long.valueOf(userInfoBean.a));
            }
            contentValues.put("_tm", Long.valueOf(userInfoBean.e));
            contentValues.put("_ut", Long.valueOf(userInfoBean.f));
            contentValues.put("_tp", Integer.valueOf(userInfoBean.b));
            contentValues.put("_pc", userInfoBean.c);
            contentValues.put("_dt", z.a(userInfoBean));
            return contentValues;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static UserInfoBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            UserInfoBean userInfoBean = (UserInfoBean) z.a(blob, UserInfoBean.CREATOR);
            if (userInfoBean != null) {
                userInfoBean.a = j;
            }
            return userInfoBean;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}