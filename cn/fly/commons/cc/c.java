package cn.fly.commons.cc;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;

/* loaded from: classes.dex */
public class c implements s<Context> {
    @Override // cn.fly.commons.cc.s
    public boolean a(Context context, Class<Context> cls, String str, Object[] objArr, boolean[] zArr, Object[] objArr2, Throwable[] thArr) {
        if (cn.fly.commons.m.a("016-glEhkQgngehkNkh fhgn,hRflfffkAeh").equals(str) && objArr.length == 1 && (objArr[0] instanceof String)) {
            try {
                objArr2[0] = context.getSystemService((String) objArr[0]);
            } catch (Throwable th) {
                objArr2[0] = null;
                thArr[0] = th;
            }
            return true;
        }
        if ("getApplicationInfo".equals(str) && objArr.length == 0) {
            objArr2[0] = context.getApplicationInfo();
            return true;
        }
        if (cn.fly.commons.m.a("0186gl%hk3gffm!gkhgk^ilYh-hkfm+iGff!h8fl").equals(str) && objArr.length == 0) {
            objArr2[0] = context.getContentResolver();
            return true;
        }
        if (cn.fly.commons.m.a("014JglRhkOin]fe:gj@fMglYh8giDf)fh4h").equals(str) && objArr.length == 0) {
            objArr2[0] = context.getPackageName();
            return true;
        }
        if (cn.fly.commons.m.a("017_glEhk*in7fe7gj,fWglOh jeXfgfFglSh9fl").equals(str) && objArr.length == 0) {
            objArr2[0] = context.getPackageManager();
            return true;
        }
        if (cn.fly.commons.m.a("013Rhk$kf5flJkZhfHek>fkfffkUk8ge").equals(str) && objArr.length == 1 && (objArr[0] instanceof Intent)) {
            context.startActivity((Intent) objArr[0]);
            return true;
        }
        if (cn.fly.commons.m.a("011ZglYhk9iefkFih)hkhnfkfl").equals(str)) {
            objArr2[0] = context.getFilesDir();
            return true;
        }
        if (cn.fly.commons.m.a("009-gl_hkIhfhkhkLhk8hk").equals(str)) {
            objArr2[0] = context.getAssets();
            return true;
        }
        if (cn.fly.commons.m.a("019ejhe]gjgn[hi%ghinOh*flfhfkhkhkfkfm,g").equals(str) && objArr.length == 1 && (objArr[0] instanceof String)) {
            if (Build.VERSION.SDK_INT >= 23) {
                objArr2[0] = Integer.valueOf(context.checkSelfPermission((String) objArr[0]));
            } else {
                objArr2[0] = Integer.valueOf(context.getPackageManager().checkPermission((String) objArr[0], context.getPackageName()));
            }
            return true;
        }
        if (cn.fly.commons.m.a("011$hhfk0g4fegnYhSflfffk?eh").equals(str) && objArr.length == 3) {
            objArr2[0] = Boolean.valueOf(context.bindService((Intent) objArr[0], (ServiceConnection) objArr[1], ((Integer) objArr[2]).intValue()));
            return true;
        }
        if (!cn.fly.commons.m.a("013'fiJgAhhfk,gGfegn_hVflfffkLeh").equals(str) || objArr.length != 1 || !(objArr[0] instanceof ServiceConnection)) {
            return false;
        }
        context.unbindService((ServiceConnection) objArr[0]);
        return true;
    }
}