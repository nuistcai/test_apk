package cn.fly.verify;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.utils.HashonHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class s {
    private static Boolean a;
    private static byte[] b;
    private static ArrayList<HashMap<String, Object>> c;
    private static ExecutorService d = Executors.newSingleThreadExecutor();
    private static String e;

    public static void a(final t tVar) {
        d.execute(new Runnable() { // from class: cn.fly.verify.s.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (tVar.d().equals("start")) {
                        cn.fly.verify.util.l.a(true);
                        cn.fly.verify.util.l.b(true);
                        cn.fly.verify.util.l.c(true);
                    }
                    s.h();
                    s.c.add(s.b(tVar, s.g()));
                    String strFromObject = HashonHelper.fromObject(s.c);
                    if (TextUtils.isEmpty(strFromObject)) {
                        return;
                    }
                    String[] strArrA = cn.fly.verify.util.f.a(s.i(), strFromObject);
                    e.b(strArrA[0] + "&&" + strArrA[1]);
                } catch (Throwable th) {
                    v.a(th);
                    e.b((String) null);
                }
            }
        });
    }

    public static void a(u uVar) {
        d.execute(new Runnable() { // from class: cn.fly.verify.s.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    s.h();
                    if (s.c.isEmpty()) {
                        return;
                    }
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("list", s.c);
                    if (TextUtils.isEmpty(s.e)) {
                        String unused = s.e = C0042r.a(4, null);
                    }
                    o.a().a(map, s.e);
                    ArrayList unused2 = s.c = null;
                    e.b((String) null);
                } catch (Throwable th) {
                    v.a(th);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HashMap<String, Object> b(t tVar, boolean z) {
        tVar.a(z);
        return q.a(tVar);
    }

    private static ArrayList<HashMap<String, Object>> f() {
        try {
            String strJ = e.j();
            if (TextUtils.isEmpty(strJ)) {
                return null;
            }
            String[] strArrSplit = strJ.split("&&");
            if (strArrSplit.length != 2) {
                e.b((String) null);
                return null;
            }
            String str = strArrSplit[0];
            return (ArrayList) HashonHelper.fromJson(cn.fly.verify.util.f.b(cn.fly.verify.util.a.a(str), strArrSplit[1]).trim(), ArrayList.class);
        } catch (Throwable th) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean g() throws IOException {
        boolean z;
        if (a == null) {
            File file = new File(FlySDK.getContext().getFilesDir(), ".preverfy_xhs");
            if (file.exists()) {
                z = false;
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e2) {
                }
                z = true;
            }
            a = Boolean.valueOf(z);
        }
        return a.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void h() {
        if (c == null) {
            c = f();
            if (c == null) {
                c = new ArrayList<>();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] i() {
        if (b == null) {
            try {
                b = cn.fly.verify.util.f.a();
            } catch (Throwable th) {
                v.a(th);
            }
        }
        return b;
    }
}