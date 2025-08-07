package com.tencent.bugly.crashreport.crash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.k;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.t;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class b {
    private static int a = 0;
    private Context b;
    private u c;
    private p d;
    private com.tencent.bugly.crashreport.common.strategy.a e;
    private o f;
    private BuglyStrategy.a g;

    public b(int i, Context context, u uVar, p pVar, com.tencent.bugly.crashreport.common.strategy.a aVar, BuglyStrategy.a aVar2, o oVar) {
        a = i;
        this.b = context;
        this.c = uVar;
        this.d = pVar;
        this.e = aVar;
        this.g = aVar2;
        this.f = oVar;
    }

    private static List<a> a(List<a> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        for (a aVar : list) {
            if (aVar.d && aVar.b <= jCurrentTimeMillis - 86400000) {
                arrayList.add(aVar);
            }
        }
        return arrayList;
    }

    private CrashDetailBean a(List<a> list, CrashDetailBean crashDetailBean) {
        List<CrashDetailBean> listB;
        String[] strArrSplit;
        if (list == null || list.size() == 0) {
            return crashDetailBean;
        }
        ArrayList arrayList = new ArrayList(10);
        for (a aVar : list) {
            if (aVar.e) {
                arrayList.add(aVar);
            }
        }
        CrashDetailBean crashDetailBean2 = null;
        if (arrayList.size() > 0 && (listB = b(arrayList)) != null && listB.size() > 0) {
            Collections.sort(listB);
            for (int i = 0; i < listB.size(); i++) {
                CrashDetailBean crashDetailBean3 = listB.get(i);
                if (i == 0) {
                    crashDetailBean2 = crashDetailBean3;
                } else if (crashDetailBean3.s != null && (strArrSplit = crashDetailBean3.s.split("\n")) != null) {
                    for (String str : strArrSplit) {
                        if (!crashDetailBean2.s.contains(str)) {
                            crashDetailBean2.t++;
                            crashDetailBean2.s += str + "\n";
                        }
                    }
                }
            }
        }
        if (crashDetailBean2 == null) {
            crashDetailBean.j = true;
            crashDetailBean.t = 0;
            crashDetailBean.s = "";
            crashDetailBean2 = crashDetailBean;
        }
        for (a aVar2 : list) {
            if (!aVar2.e && !aVar2.d && !crashDetailBean2.s.contains(new StringBuilder().append(aVar2.b).toString())) {
                crashDetailBean2.t++;
                crashDetailBean2.s += aVar2.b + "\n";
            }
        }
        if (crashDetailBean2.r != crashDetailBean.r && !crashDetailBean2.s.contains(new StringBuilder().append(crashDetailBean.r).toString())) {
            crashDetailBean2.t++;
            crashDetailBean2.s += crashDetailBean.r + "\n";
        }
        return crashDetailBean2;
    }

    public final boolean a(CrashDetailBean crashDetailBean) {
        return b(crashDetailBean);
    }

    public final boolean b(CrashDetailBean crashDetailBean) {
        ArrayList arrayList;
        if (crashDetailBean == null) {
            return true;
        }
        if (c.n != null && !c.n.isEmpty()) {
            x.c("Crash filter for crash stack is: %s", c.n);
            if (crashDetailBean.q.contains(c.n)) {
                x.d("This crash contains the filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (c.o != null && !c.o.isEmpty()) {
            x.c("Crash regular filter for crash stack is: %s", c.o);
            if (Pattern.compile(c.o).matcher(crashDetailBean.q).find()) {
                x.d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (crashDetailBean.b != 2) {
            r rVar = new r();
            rVar.b = 1;
            rVar.c = crashDetailBean.A;
            rVar.d = crashDetailBean.B;
            rVar.e = crashDetailBean.r;
            this.d.b(1);
            this.d.a(rVar);
            x.b("[crash] a crash occur, handling...", new Object[0]);
        } else {
            x.b("[crash] a caught exception occur, handling...", new Object[0]);
        }
        List<a> listB = b();
        if (listB != null && listB.size() > 0) {
            arrayList = new ArrayList(10);
            ArrayList arrayList2 = new ArrayList(10);
            arrayList.addAll(a(listB));
            listB.removeAll(arrayList);
            if (listB.size() > 20) {
                StringBuilder sb = new StringBuilder();
                sb.append("_id in ").append("(");
                sb.append("SELECT _id").append(" FROM t_cr").append(" order by _id").append(" limit 5");
                sb.append(")");
                String string = sb.toString();
                sb.setLength(0);
                try {
                    x.c("deleted first record %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", string, (String[]) null, (o) null, true)));
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
            if (!com.tencent.bugly.b.c && c.d) {
                boolean z = false;
                for (a aVar : listB) {
                    if (crashDetailBean.u.equals(aVar.c)) {
                        if (aVar.e) {
                            z = true;
                        }
                        arrayList2.add(aVar);
                    }
                }
                if (z || arrayList2.size() >= c.c) {
                    x.a("same crash occur too much do merged!", new Object[0]);
                    CrashDetailBean crashDetailBeanA = a(arrayList2, crashDetailBean);
                    for (a aVar2 : arrayList2) {
                        if (aVar2.a != crashDetailBeanA.a) {
                            arrayList.add(aVar2);
                        }
                    }
                    e(crashDetailBeanA);
                    c(arrayList);
                    x.b("[crash] save crash success. For this device crash many times, it will not upload crashes immediately", new Object[0]);
                    return true;
                }
            }
        } else {
            arrayList = null;
        }
        e(crashDetailBean);
        if (arrayList != null && !arrayList.isEmpty()) {
            c(arrayList);
        }
        x.b("[crash] save crash success", new Object[0]);
        return false;
    }

    public final List<CrashDetailBean> a() {
        StrategyBean strategyBeanC = com.tencent.bugly.crashreport.common.strategy.a.a().c();
        if (strategyBeanC == null) {
            x.d("have not synced remote!", new Object[0]);
            return null;
        }
        if (!strategyBeanC.g) {
            x.d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            x.b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jB = z.b();
        List<a> listB = b();
        x.c("Size of crash list loaded from DB: %s", Integer.valueOf(listB.size()));
        if (listB == null || listB.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(a(listB));
        listB.removeAll(arrayList);
        Iterator<a> it = listB.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.b < jB - c.g) {
                it.remove();
                arrayList.add(next);
            } else if (next.d) {
                if (next.b >= jCurrentTimeMillis - 86400000) {
                    it.remove();
                } else if (!next.e) {
                    it.remove();
                    arrayList.add(next);
                }
            } else if (next.f >= 3 && next.b < jCurrentTimeMillis - 86400000) {
                it.remove();
                arrayList.add(next);
            }
        }
        if (arrayList.size() > 0) {
            c(arrayList);
        }
        ArrayList arrayList2 = new ArrayList();
        List<CrashDetailBean> listB2 = b(listB);
        if (listB2 != null && listB2.size() > 0) {
            String str = com.tencent.bugly.crashreport.common.info.a.b().k;
            Iterator<CrashDetailBean> it2 = listB2.iterator();
            while (it2.hasNext()) {
                CrashDetailBean next2 = it2.next();
                if (!str.equals(next2.f)) {
                    it2.remove();
                    arrayList2.add(next2);
                }
            }
        }
        if (arrayList2.size() > 0) {
            d(arrayList2);
        }
        return listB2;
    }

    public final void c(CrashDetailBean crashDetailBean) {
        switch (crashDetailBean.b) {
            case 0:
                if (!c.a().o()) {
                    return;
                }
                break;
            case 1:
                if (!c.a().o()) {
                    return;
                }
                break;
            case 3:
                if (!c.a().p()) {
                    return;
                }
                break;
        }
        if (this.f != null) {
            x.c("Calling 'onCrashHandleEnd' of RQD crash listener.", new Object[0]);
            int i = crashDetailBean.b;
        }
    }

    public final void a(CrashDetailBean crashDetailBean, long j, boolean z) {
        if (c.l) {
            x.a("try to upload right now", new Object[0]);
            ArrayList arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            a(arrayList, 3000L, z, crashDetailBean.b == 7, z);
        }
    }

    public final void a(final List<CrashDetailBean> list, long j, boolean z, boolean z2, boolean z3) {
        ao aoVar;
        if (!com.tencent.bugly.crashreport.common.info.a.a(this.b).e || this.c == null) {
            return;
        }
        if (!z3 && !this.c.b(c.a)) {
            return;
        }
        StrategyBean strategyBeanC = this.e.c();
        if (!strategyBeanC.g) {
            x.d("remote report is disable!", new Object[0]);
            x.b("[crash] server closed bugly in this app. please check your appid if is correct, and re-install it", new Object[0]);
            return;
        }
        if (list == null || list.size() == 0) {
            return;
        }
        try {
            String str = this.c.a ? strategyBeanC.s : strategyBeanC.t;
            String str2 = this.c.a ? StrategyBean.c : StrategyBean.a;
            int i = this.c.a ? 830 : 630;
            Context context = this.b;
            com.tencent.bugly.crashreport.common.info.a aVarB = com.tencent.bugly.crashreport.common.info.a.b();
            if (context == null || list == null || list.size() == 0 || aVarB == null) {
                x.d("enEXPPkg args == null!", new Object[0]);
                aoVar = null;
            } else {
                aoVar = new ao();
                aoVar.a = new ArrayList<>();
                Iterator<CrashDetailBean> it = list.iterator();
                while (it.hasNext()) {
                    aoVar.a.add(a(context, it.next(), aVarB));
                }
            }
            if (aoVar == null) {
                x.d("create eupPkg fail!", new Object[0]);
                return;
            }
            byte[] bArrA = com.tencent.bugly.proguard.a.a((k) aoVar);
            if (bArrA == null) {
                x.d("send encode fail!", new Object[0]);
                return;
            }
            ap apVarA = com.tencent.bugly.proguard.a.a(this.b, i, bArrA);
            if (apVarA == null) {
                x.d("request package is null.", new Object[0]);
                return;
            }
            t tVar = new t() { // from class: com.tencent.bugly.crashreport.crash.b.1
                @Override // com.tencent.bugly.proguard.t
                public final void a(boolean z4) {
                    b.a(z4, (List<CrashDetailBean>) list);
                }
            };
            if (z) {
                this.c.a(a, apVarA, str, str2, tVar, j, z2);
            } else {
                this.c.a(a, apVarA, str, str2, tVar, false);
            }
        } catch (Throwable th) {
            x.e("req cr error %s", th.toString());
            if (!x.b(th)) {
                th.printStackTrace();
            }
        }
    }

    public static void a(boolean z, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            x.c("up finish update state %b", Boolean.valueOf(z));
            for (CrashDetailBean crashDetailBean : list) {
                x.c("pre uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
                crashDetailBean.l++;
                crashDetailBean.d = z;
                x.c("set uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
            }
            Iterator<CrashDetailBean> it = list.iterator();
            while (it.hasNext()) {
                c.a().a(it.next());
            }
            x.c("update state size %d", Integer.valueOf(list.size()));
        }
        if (!z) {
            x.b("[crash] upload fail.", new Object[0]);
        }
    }

    public final void d(CrashDetailBean crashDetailBean) {
        int i;
        Map<String, String> mapOnCrashHandleStart;
        String strSubstring;
        if (crashDetailBean == null) {
            return;
        }
        if (this.g == null && this.f == null) {
            return;
        }
        try {
            switch (crashDetailBean.b) {
                case 0:
                    if (c.a().o()) {
                        i = 0;
                        break;
                    } else {
                        return;
                    }
                case 1:
                    if (c.a().o()) {
                        i = 2;
                        break;
                    } else {
                        return;
                    }
                case 2:
                    i = 1;
                    break;
                case 3:
                    if (c.a().p()) {
                        i = 4;
                        break;
                    } else {
                        return;
                    }
                case 4:
                    if (c.a().q()) {
                        i = 3;
                        break;
                    } else {
                        return;
                    }
                case 5:
                    if (c.a().r()) {
                        i = 5;
                        break;
                    } else {
                        return;
                    }
                case 6:
                    if (c.a().s()) {
                        i = 6;
                        break;
                    } else {
                        return;
                    }
                case 7:
                    i = 7;
                    break;
                default:
                    return;
            }
            int i2 = crashDetailBean.b;
            String str = crashDetailBean.n;
            String str2 = crashDetailBean.p;
            String str3 = crashDetailBean.q;
            long j = crashDetailBean.r;
            byte[] bArrOnCrashHandleStart2GetExtraDatas = null;
            if (this.f != null) {
                x.c("Calling 'onCrashHandleStart' of RQD crash listener.", new Object[0]);
                x.c("Calling 'getCrashExtraMessage' of RQD crash listener.", new Object[0]);
                String strB = this.f.b();
                if (strB == null) {
                    mapOnCrashHandleStart = null;
                } else {
                    mapOnCrashHandleStart = new HashMap<>(1);
                    mapOnCrashHandleStart.put("userData", strB);
                }
            } else if (this.g == null) {
                mapOnCrashHandleStart = null;
            } else {
                x.c("Calling 'onCrashHandleStart' of Bugly crash listener.", new Object[0]);
                mapOnCrashHandleStart = this.g.onCrashHandleStart(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
            }
            if (mapOnCrashHandleStart != null && mapOnCrashHandleStart.size() > 0) {
                crashDetailBean.O = new LinkedHashMap(mapOnCrashHandleStart.size());
                for (Map.Entry<String, String> entry : mapOnCrashHandleStart.entrySet()) {
                    if (!z.a(entry.getKey())) {
                        String key = entry.getKey();
                        if (key.length() > 100) {
                            key = key.substring(0, 100);
                            x.d("setted key length is over limit %d substring to %s", 100, key);
                        }
                        if (!z.a(entry.getValue()) && entry.getValue().length() > 30000) {
                            strSubstring = entry.getValue().substring(entry.getValue().length() - BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                            x.d("setted %s value length is over limit %d substring", key, Integer.valueOf(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH));
                        } else {
                            strSubstring = entry.getValue();
                        }
                        crashDetailBean.O.put(key, strSubstring);
                        x.a("add setted key %s value size:%d", key, Integer.valueOf(strSubstring.length()));
                    }
                }
            }
            x.a("[crash callback] start user's callback:onCrashHandleStart2GetExtraDatas()", new Object[0]);
            if (this.f != null) {
                x.c("Calling 'getCrashExtraData' of RQD crash listener.", new Object[0]);
                bArrOnCrashHandleStart2GetExtraDatas = this.f.a();
            } else if (this.g != null) {
                x.c("Calling 'onCrashHandleStart2GetExtraDatas' of Bugly crash listener.", new Object[0]);
                bArrOnCrashHandleStart2GetExtraDatas = this.g.onCrashHandleStart2GetExtraDatas(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
            }
            crashDetailBean.U = bArrOnCrashHandleStart2GetExtraDatas;
            if (bArrOnCrashHandleStart2GetExtraDatas != null) {
                if (bArrOnCrashHandleStart2GetExtraDatas.length > 30000) {
                    x.d("extra bytes size %d is over limit %d will drop over part", Integer.valueOf(bArrOnCrashHandleStart2GetExtraDatas.length), Integer.valueOf(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH));
                    crashDetailBean.U = Arrays.copyOf(bArrOnCrashHandleStart2GetExtraDatas, BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                }
                x.a("add extra bytes %d ", Integer.valueOf(bArrOnCrashHandleStart2GetExtraDatas.length));
            }
            if (this.f != null) {
                x.c("Calling 'onCrashSaving' of RQD crash listener.", new Object[0]);
                o oVar = this.f;
                String str4 = crashDetailBean.o;
                String str5 = crashDetailBean.m;
                String str6 = crashDetailBean.e;
                String str7 = crashDetailBean.c;
                String str8 = crashDetailBean.A;
                String str9 = crashDetailBean.B;
                if (!oVar.c()) {
                    x.d("Crash listener 'onCrashSaving' return 'false' thus will not handle this crash.", new Object[0]);
                }
            }
        } catch (Throwable th) {
            x.d("crash handle callback something wrong! %s", th.getClass().getName());
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    private static ContentValues f(CrashDetailBean crashDetailBean) {
        if (crashDetailBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (crashDetailBean.a > 0) {
                contentValues.put("_id", Long.valueOf(crashDetailBean.a));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.r));
            contentValues.put("_s1", crashDetailBean.u);
            int i = 1;
            contentValues.put("_up", Integer.valueOf(crashDetailBean.d ? 1 : 0));
            if (!crashDetailBean.j) {
                i = 0;
            }
            contentValues.put("_me", Integer.valueOf(i));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.l));
            contentValues.put("_dt", z.a(crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static CrashDetailBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            CrashDetailBean crashDetailBean = (CrashDetailBean) z.a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean != null) {
                crashDetailBean.a = j;
            }
            return crashDetailBean;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public final void e(CrashDetailBean crashDetailBean) {
        ContentValues contentValuesF;
        if (crashDetailBean != null && (contentValuesF = f(crashDetailBean)) != null) {
            long jA = p.a().a("t_cr", contentValuesF, (o) null, true);
            if (jA >= 0) {
                x.c("insert %s success!", "t_cr");
                crashDetailBean.a = jA;
            }
        }
    }

    private List<CrashDetailBean> b(List<a> list) {
        Cursor cursorA;
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("_id in ").append("(");
        Iterator<a> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next().a).append(",");
        }
        if (sb.toString().contains(",")) {
            sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
        }
        sb.append(")");
        String string = sb.toString();
        sb.setLength(0);
        try {
            cursorA = p.a().a("t_cr", null, string, null, null, true);
            if (cursorA == null) {
                return null;
            }
            try {
                ArrayList arrayList = new ArrayList();
                sb.append("_id in ").append("(");
                int i = 0;
                while (cursorA.moveToNext()) {
                    CrashDetailBean crashDetailBeanA = a(cursorA);
                    if (crashDetailBeanA != null) {
                        arrayList.add(crashDetailBeanA);
                    } else {
                        try {
                            sb.append(cursorA.getLong(cursorA.getColumnIndex("_id"))).append(",");
                            i++;
                        } catch (Throwable th) {
                            x.d("unknown id!", new Object[0]);
                        }
                    }
                }
                if (sb.toString().contains(",")) {
                    sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
                }
                sb.append(")");
                String string2 = sb.toString();
                if (i > 0) {
                    x.d("deleted %s illegal data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", string2, (String[]) null, (o) null, true)));
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

    private static a b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            a aVar = new a();
            aVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            aVar.b = cursor.getLong(cursor.getColumnIndex("_tm"));
            aVar.c = cursor.getString(cursor.getColumnIndex("_s1"));
            aVar.d = cursor.getInt(cursor.getColumnIndex("_up")) == 1;
            aVar.e = cursor.getInt(cursor.getColumnIndex("_me")) == 1;
            aVar.f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return aVar;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private List<a> b() {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            Cursor cursorA = p.a().a("t_cr", new String[]{"_id", "_tm", "_s1", "_up", "_me", "_uc"}, null, null, null, true);
            if (cursorA == null) {
                if (cursorA != null) {
                    cursorA.close();
                }
                return null;
            }
            try {
                if (cursorA.getCount() <= 0) {
                    if (cursorA != null) {
                        cursorA.close();
                    }
                    return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("_id in ").append("(");
                int i = 0;
                while (cursorA.moveToNext()) {
                    a aVarB = b(cursorA);
                    if (aVarB != null) {
                        arrayList.add(aVarB);
                    } else {
                        try {
                            sb.append(cursorA.getLong(cursorA.getColumnIndex("_id"))).append(",");
                            i++;
                        } catch (Throwable th) {
                            x.d("unknown id!", new Object[0]);
                        }
                    }
                }
                if (sb.toString().contains(",")) {
                    sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
                }
                sb.append(")");
                String string = sb.toString();
                sb.setLength(0);
                if (i > 0) {
                    x.d("deleted %s illegal data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", string, (String[]) null, (o) null, true)));
                }
                if (cursorA != null) {
                    cursorA.close();
                }
                return arrayList;
            } catch (Throwable th2) {
                th = th2;
                cursor = cursorA;
                try {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                    return arrayList;
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static void c(List<a> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("_id in ").append("(");
        Iterator<a> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next().a).append(",");
        }
        StringBuilder sb2 = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
        sb2.append(")");
        String string = sb2.toString();
        sb2.setLength(0);
        try {
            x.c("deleted %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", string, (String[]) null, (o) null, true)));
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    private static void d(List<CrashDetailBean> list) {
        String strSubstring;
        if (list == null) {
            return;
        }
        try {
            if (list.size() == 0) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            Iterator<CrashDetailBean> it = list.iterator();
            while (it.hasNext()) {
                sb.append(" or _id").append(" = ").append(it.next().a);
            }
            String string = sb.toString();
            if (string.length() <= 0) {
                strSubstring = string;
            } else {
                strSubstring = string.substring(4);
            }
            sb.setLength(0);
            x.c("deleted %s data %d", "t_cr", Integer.valueOf(p.a().a("t_cr", strSubstring, (String[]) null, (o) null, true)));
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    private static an a(Context context, CrashDetailBean crashDetailBean, com.tencent.bugly.crashreport.common.info.a aVar) throws IOException {
        am amVarA;
        am amVarA2;
        am amVar;
        if (context == null || crashDetailBean == null || aVar == null) {
            x.d("enExp args == null", new Object[0]);
            return null;
        }
        an anVar = new an();
        switch (crashDetailBean.b) {
            case 0:
                anVar.a = crashDetailBean.j ? "200" : "100";
                break;
            case 1:
                anVar.a = crashDetailBean.j ? "201" : "101";
                break;
            case 2:
                anVar.a = crashDetailBean.j ? "202" : "102";
                break;
            case 3:
                anVar.a = crashDetailBean.j ? "203" : "103";
                break;
            case 4:
                anVar.a = crashDetailBean.j ? "204" : "104";
                break;
            case 5:
                anVar.a = crashDetailBean.j ? "207" : "107";
                break;
            case 6:
                anVar.a = crashDetailBean.j ? "206" : "106";
                break;
            case 7:
                anVar.a = crashDetailBean.j ? "208" : "108";
                break;
            default:
                x.e("crash type error! %d", Integer.valueOf(crashDetailBean.b));
                break;
        }
        anVar.b = crashDetailBean.r;
        anVar.c = crashDetailBean.n;
        anVar.d = crashDetailBean.o;
        anVar.e = crashDetailBean.p;
        anVar.g = crashDetailBean.q;
        anVar.h = crashDetailBean.z;
        anVar.i = crashDetailBean.c;
        anVar.j = null;
        anVar.l = crashDetailBean.m;
        anVar.m = crashDetailBean.e;
        anVar.f = crashDetailBean.B;
        anVar.t = com.tencent.bugly.crashreport.common.info.a.b().i();
        anVar.n = null;
        if (crashDetailBean.i != null && crashDetailBean.i.size() > 0) {
            anVar.o = new ArrayList<>();
            for (Map.Entry<String, PlugInBean> entry : crashDetailBean.i.entrySet()) {
                ak akVar = new ak();
                akVar.a = entry.getValue().a;
                akVar.c = entry.getValue().c;
                akVar.d = entry.getValue().b;
                akVar.b = aVar.r();
                anVar.o.add(akVar);
            }
        }
        if (crashDetailBean.h != null && crashDetailBean.h.size() > 0) {
            anVar.p = new ArrayList<>();
            for (Map.Entry<String, PlugInBean> entry2 : crashDetailBean.h.entrySet()) {
                ak akVar2 = new ak();
                akVar2.a = entry2.getValue().a;
                akVar2.c = entry2.getValue().c;
                akVar2.d = entry2.getValue().b;
                anVar.p.add(akVar2);
            }
        }
        if (crashDetailBean.j) {
            anVar.k = crashDetailBean.t;
            if (crashDetailBean.s != null && crashDetailBean.s.length() > 0) {
                if (anVar.q == null) {
                    anVar.q = new ArrayList<>();
                }
                try {
                    anVar.q.add(new am((byte) 1, "alltimes.txt", crashDetailBean.s.getBytes("utf-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    anVar.q = null;
                }
            }
            x.c("crashcount:%d sz:%d", Integer.valueOf(anVar.k), Integer.valueOf(anVar.q != null ? anVar.q.size() : 0));
        }
        if (crashDetailBean.w != null) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            try {
                anVar.q.add(new am((byte) 1, "log.txt", crashDetailBean.w.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                anVar.q = null;
            }
        }
        if (crashDetailBean.x != null) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            try {
                anVar.q.add(new am((byte) 1, "jniLog.txt", crashDetailBean.x.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e3) {
                e3.printStackTrace();
                anVar.q = null;
            }
        }
        if (!z.a(crashDetailBean.V)) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            try {
                amVar = new am((byte) 1, "crashInfos.txt", crashDetailBean.V.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e4) {
                e4.printStackTrace();
                amVar = null;
            }
            if (amVar != null) {
                x.c("attach crash infos", new Object[0]);
                anVar.q.add(amVar);
            }
        }
        if (crashDetailBean.W != null) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            am amVarA3 = a("backupRecord.zip", context, crashDetailBean.W);
            if (amVarA3 != null) {
                x.c("attach backup record", new Object[0]);
                anVar.q.add(amVarA3);
            }
        }
        if (crashDetailBean.y != null && crashDetailBean.y.length > 0) {
            am amVar2 = new am((byte) 2, "buglylog.zip", crashDetailBean.y);
            x.c("attach user log", new Object[0]);
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            anVar.q.add(amVar2);
        }
        if (crashDetailBean.b == 3) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            x.c("crashBean.anrMessages:%s", crashDetailBean.P);
            if (crashDetailBean.P != null && crashDetailBean.P.containsKey("BUGLY_CR_01")) {
                try {
                    if (!TextUtils.isEmpty(crashDetailBean.P.get("BUGLY_CR_01"))) {
                        anVar.q.add(new am((byte) 1, "anrMessage.txt", crashDetailBean.P.get("BUGLY_CR_01").getBytes("utf-8")));
                        x.c("attach anr message", new Object[0]);
                    }
                } catch (UnsupportedEncodingException e5) {
                    e5.printStackTrace();
                    anVar.q = null;
                }
                crashDetailBean.P.remove("BUGLY_CR_01");
            }
            if (crashDetailBean.v != null && (amVarA2 = a("trace.zip", context, crashDetailBean.v)) != null) {
                x.c("attach traces", new Object[0]);
                anVar.q.add(amVarA2);
            }
        }
        if (crashDetailBean.b == 1) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            if (crashDetailBean.v != null && (amVarA = a("tomb.zip", context, crashDetailBean.v)) != null) {
                x.c("attach tombs", new Object[0]);
                anVar.q.add(amVarA);
            }
        }
        if (aVar.E != null && !aVar.E.isEmpty()) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = aVar.E.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
            }
            try {
                anVar.q.add(new am((byte) 1, "martianlog.txt", sb.toString().getBytes("utf-8")));
                x.c("attach pageTracingList", new Object[0]);
            } catch (UnsupportedEncodingException e6) {
                e6.printStackTrace();
            }
        }
        if (crashDetailBean.U != null && crashDetailBean.U.length > 0) {
            if (anVar.q == null) {
                anVar.q = new ArrayList<>();
            }
            anVar.q.add(new am((byte) 1, "userExtraByteData", crashDetailBean.U));
            x.c("attach extraData", new Object[0]);
        }
        anVar.r = new HashMap();
        anVar.r.put("A9", new StringBuilder().append(crashDetailBean.C).toString());
        anVar.r.put("A11", new StringBuilder().append(crashDetailBean.D).toString());
        anVar.r.put("A10", new StringBuilder().append(crashDetailBean.E).toString());
        anVar.r.put("A23", crashDetailBean.f);
        anVar.r.put("A7", aVar.g);
        anVar.r.put("A6", aVar.s());
        anVar.r.put("A5", aVar.r());
        anVar.r.put("A22", aVar.h());
        anVar.r.put("A2", new StringBuilder().append(crashDetailBean.G).toString());
        anVar.r.put("A1", new StringBuilder().append(crashDetailBean.F).toString());
        anVar.r.put("A24", aVar.i);
        anVar.r.put("A17", new StringBuilder().append(crashDetailBean.H).toString());
        anVar.r.put("A3", aVar.k());
        anVar.r.put("A16", aVar.m());
        anVar.r.put("A25", aVar.n());
        anVar.r.put("A14", aVar.l());
        anVar.r.put("A15", aVar.w());
        anVar.r.put("A13", new StringBuilder().append(aVar.x()).toString());
        anVar.r.put("A34", crashDetailBean.A);
        if (aVar.y != null) {
            anVar.r.put("productIdentify", aVar.y);
        }
        try {
            anVar.r.put("A26", URLEncoder.encode(crashDetailBean.I, "utf-8"));
        } catch (UnsupportedEncodingException e7) {
            e7.printStackTrace();
        }
        if (crashDetailBean.b == 1) {
            anVar.r.put("A27", crashDetailBean.K);
            anVar.r.put("A28", crashDetailBean.J);
            anVar.r.put("A29", new StringBuilder().append(crashDetailBean.k).toString());
        }
        anVar.r.put("A30", crashDetailBean.L);
        anVar.r.put("A18", new StringBuilder().append(crashDetailBean.M).toString());
        anVar.r.put("A36", new StringBuilder().append(!crashDetailBean.N).toString());
        anVar.r.put("F02", new StringBuilder().append(aVar.r).toString());
        anVar.r.put("F03", new StringBuilder().append(aVar.s).toString());
        anVar.r.put("F04", aVar.e());
        anVar.r.put("F05", new StringBuilder().append(aVar.t).toString());
        anVar.r.put("F06", aVar.q);
        anVar.r.put("F08", aVar.w);
        anVar.r.put("F09", aVar.x);
        anVar.r.put("F10", new StringBuilder().append(aVar.u).toString());
        if (crashDetailBean.Q >= 0) {
            anVar.r.put("C01", new StringBuilder().append(crashDetailBean.Q).toString());
        }
        if (crashDetailBean.R >= 0) {
            anVar.r.put("C02", new StringBuilder().append(crashDetailBean.R).toString());
        }
        if (crashDetailBean.S != null && crashDetailBean.S.size() > 0) {
            for (Map.Entry<String, String> entry3 : crashDetailBean.S.entrySet()) {
                anVar.r.put("C03_" + entry3.getKey(), entry3.getValue());
            }
        }
        if (crashDetailBean.T != null && crashDetailBean.T.size() > 0) {
            for (Map.Entry<String, String> entry4 : crashDetailBean.T.entrySet()) {
                anVar.r.put("C04_" + entry4.getKey(), entry4.getValue());
            }
        }
        anVar.s = null;
        if (crashDetailBean.O != null && crashDetailBean.O.size() > 0) {
            anVar.s = crashDetailBean.O;
            x.a("setted message size %d", Integer.valueOf(anVar.s.size()));
        }
        x.c("%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d", crashDetailBean.n, crashDetailBean.c, aVar.e(), Long.valueOf((crashDetailBean.r - crashDetailBean.M) / 1000), Boolean.valueOf(crashDetailBean.k), Boolean.valueOf(crashDetailBean.N), Boolean.valueOf(crashDetailBean.j), Boolean.valueOf(crashDetailBean.b == 1), Integer.valueOf(crashDetailBean.t), crashDetailBean.s, Boolean.valueOf(crashDetailBean.d), Integer.valueOf(anVar.r.size()));
        return anVar;
    }

    private static am a(String str, Context context, String str2) throws IOException {
        FileInputStream fileInputStream;
        if (str2 == null || context == null) {
            x.d("rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}", new Object[0]);
            return null;
        }
        x.c("zip %s", str2);
        File file = new File(str2);
        File file2 = new File(context.getCacheDir(), str);
        if (!z.a(file, file2, 5000)) {
            x.d("zip fail!", new Object[0]);
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            fileInputStream = new FileInputStream(file2);
        } catch (Throwable th) {
            th = th;
            fileInputStream = null;
        }
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
                byteArrayOutputStream.flush();
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            x.c("read bytes :%d", Integer.valueOf(byteArray.length));
            am amVar = new am((byte) 2, file2.getName(), byteArray);
            try {
                fileInputStream.close();
            } catch (IOException e) {
                if (!x.a(e)) {
                    e.printStackTrace();
                }
            }
            if (file2.exists()) {
                x.c("del tmp", new Object[0]);
                file2.delete();
            }
            return amVar;
        } catch (Throwable th2) {
            th = th2;
            try {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e2) {
                        if (!x.a(e2)) {
                            e2.printStackTrace();
                        }
                    }
                }
                if (file2.exists()) {
                    x.c("del tmp", new Object[0]);
                    file2.delete();
                }
                return null;
            } catch (Throwable th3) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e3) {
                        if (!x.a(e3)) {
                            e3.printStackTrace();
                        }
                    }
                }
                if (file2.exists()) {
                    x.c("del tmp", new Object[0]);
                    file2.delete();
                }
                throw th3;
            }
        }
    }

    public static void a(String str, String str2, String str3, String str4, String str5, CrashDetailBean crashDetailBean) {
        com.tencent.bugly.crashreport.common.info.a aVarB = com.tencent.bugly.crashreport.common.info.a.b();
        if (aVarB == null) {
            return;
        }
        x.e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
        x.e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
        x.e("# PKG NAME: %s", aVarB.c);
        x.e("# APP VER: %s", aVarB.k);
        x.e("# SDK VER: %s", aVarB.f);
        x.e("# LAUNCH TIME: %s", z.a(new Date(com.tencent.bugly.crashreport.common.info.a.b().a)));
        x.e("# CRASH TYPE: %s", str);
        x.e("# CRASH TIME: %s", str2);
        x.e("# CRASH PROCESS: %s", str3);
        x.e("# CRASH THREAD: %s", str4);
        if (crashDetailBean != null) {
            x.e("# REPORT ID: %s", crashDetailBean.c);
            x.e("# CRASH DEVICE: %s %s", aVarB.h, aVarB.x().booleanValue() ? "ROOTED" : "UNROOT");
            x.e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.C), Long.valueOf(crashDetailBean.D), Long.valueOf(crashDetailBean.E));
            x.e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.F), Long.valueOf(crashDetailBean.G), Long.valueOf(crashDetailBean.H));
            if (!z.a(crashDetailBean.K)) {
                x.e("# EXCEPTION FIRED BY %s %s", crashDetailBean.K, crashDetailBean.J);
            } else if (crashDetailBean.b == 3) {
                x.e("# EXCEPTION ANR MESSAGE:\n %s", crashDetailBean.P == null ? "null" : crashDetailBean.P.get("BUGLY_CR_01"));
            }
        }
        if (!z.a(str5)) {
            x.e("# CRASH STACK: ", new Object[0]);
            x.e(str5, new Object[0]);
        }
        x.e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
    }
}