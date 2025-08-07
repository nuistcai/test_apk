package cn.fly.tools.utils;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import cn.fly.commons.C0041r;
import cn.fly.commons.w;
import cn.fly.tools.FlyLog;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/* loaded from: classes.dex */
public class c {
    private final Context a;

    public c(Context context) {
        this.a = context;
    }

    public String a() {
        StringBuilder sb = new StringBuilder("");
        try {
            if (d()) {
                sb.append("1");
            } else {
                sb.append("0");
            }
            if (e()) {
                sb.append("1");
            } else {
                sb.append("0");
            }
            if (c()) {
                sb.append("1");
            } else {
                sb.append("0");
            }
            if (b()) {
                sb.append("1");
            } else {
                sb.append("0");
            }
            if (f()) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        } catch (Throwable th) {
        }
        return sb.toString();
    }

    private boolean b() {
        try {
            Object objInvokeStaticMethodNoThrow = ReflectHelper.invokeStaticMethodNoThrow(ReflectHelper.importClassNoThrow(w.b("027cdUcbcicjchcbckcjehckdkdbeh=heIcefkcicj;ie8ci0hUchIe.eh"), null), w.b("003IdiWeh"), "", "ro.build.tags");
            String strValueOf = objInvokeStaticMethodNoThrow != null ? String.valueOf(objInvokeStaticMethodNoThrow) : null;
            if (!(strValueOf != null && strValueOf.contains(w.b("009he8eh2h'gjdg4eEdbeh")))) {
                if (!g()) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private boolean c() {
        return "0".equals(cn.fly.tools.b.b.a(this.a).a(w.b("020^cicjckeecjcjUhTckde2fcCehNgZck]f4cjVb:dg:e.cb")));
    }

    private boolean d() {
        String strA = cn.fly.tools.b.b.a(this.a).a(w.b("025Rcicjckeecjcj+h-ckcc(e[cichdechNeMcbeecjcj-hVeh)hche"));
        if (strA != null) {
            return TextUtils.equals(strA.toLowerCase(), "orange") || TextUtils.equals(strA.toLowerCase(), "red");
        }
        return false;
    }

    private boolean e() {
        String strA = cn.fly.tools.b.b.a(this.a).a(w.b("027$cicjckeecjcj]h+ckcceece ehcVckcb]eOccchLbeLcgeh!hche"));
        return strA != null && TextUtils.equals(w.b("008Rcf+df;cj*b9dg=e8cb"), strA.toLowerCase());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00a4  */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean f() {
        Object objC;
        InputStream inputStream;
        ?? bufferedReader;
        int iMyPid = Process.myPid();
        StringBuilder sb = new StringBuilder();
        try {
            objC = C0041r.c(w.b("010bchShe2ki6cicjUbk") + (iMyPid + w.b("007k+cecjcf-dh!eh")));
        } catch (Throwable th) {
            th = th;
            objC = null;
            inputStream = null;
        }
        try {
            inputStream = (InputStream) ReflectHelper.invokeInstanceMethodNoThrow(objC, w.b("014^di@ehCdd8di-cf0hNdkRh'ciLecKce"), null, new Object[0]);
            if (inputStream == null) {
                bufferedReader = 0;
            } else {
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line).append("\n");
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                FlyLog.getInstance().d(th);
                                C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
                                if (objC != null) {
                                    ReflectHelper.invokeInstanceMethodNoThrow(objC, w.b("007TcbGePeh>h<cicjdb"), null, new Object[0]);
                                }
                                return sb.toString().contains(w.b("006BceKcAdichehdg"));
                            } catch (Throwable th3) {
                                C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
                                if (objC != null) {
                                    ReflectHelper.invokeInstanceMethodNoThrow(objC, w.b("007TcbGePeh>h<cicjdb"), null, new Object[0]);
                                }
                                throw th3;
                            }
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    bufferedReader = 0;
                }
            }
            C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
            if (objC != null) {
                ReflectHelper.invokeInstanceMethodNoThrow(objC, w.b("007TcbGePeh>h<cicjdb"), null, new Object[0]);
            }
        } catch (Throwable th5) {
            th = th5;
            inputStream = null;
            bufferedReader = inputStream;
            FlyLog.getInstance().d(th);
            C0041r.a((Closeable[]) new Closeable[]{bufferedReader, inputStream});
            if (objC != null) {
            }
            return sb.toString().contains(w.b("006BceKcAdichehdg"));
        }
        return sb.toString().contains(w.b("006BceKcAdichehdg"));
    }

    private boolean g() {
        try {
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
        }
        if (new File(w.b("025k+ehdbehKheYce'kciikQdkcf$ieMcicfehSeVcick1ci'dg")).exists()) {
            return true;
        }
        String[] strArr = {w.b("012kWcb>chckfEcj'bcfk"), w.b("016k@cbAchckf4cj(bcfkWeech4dk"), w.b("017kBcb0chckfVcjCbcfk<dheech_dk"), w.b("006kQeheech;dk"), w.b("008k>ehcfTkOeech>dk"), w.b("012k'ehdbeh!hePce'kCeechDdk"), w.b("017kJehdbehCheUce k^eech7dkVck$eDdh5hk"), w.b("021k]ehdbeh%he%ce4k_eech>dk@deIc$chMfUehCcCde6ek"), w.b("016k_ehdbeh:heSce,kAehcb,kHdheech^dk"), w.b("025k5ehdbehKhe4ce?kDcfehci.k9efQe gj5deeScbgjcicjcj3hk"), w.b("013k8ehdbehFhe+ce9kKdheech2dk"), w.b("013kBehdbeh0he1ceRkQeheechXdk"), w.b("012kGccGedEcbcjciSk*eech?dk"), w.b("006kbcbge"), w.b("005kGcb,chc"), w.b("004kVcbBeIcc")};
        for (int i = 0; i < 16; i++) {
            if (new File(strArr[i], w.b("002.ehcf")).exists()) {
                return true;
            }
        }
        for (int i2 = 0; i2 < 16; i2++) {
            if (new File(strArr[i2], w.b("007[eecfehdbeecjdh")).exists()) {
                return true;
            }
        }
        for (int i3 = 0; i3 < 16; i3++) {
            if (new File(strArr[i3], w.b("006)ce3cYdichehdg")).exists()) {
                return true;
            }
        }
        return false;
    }
}