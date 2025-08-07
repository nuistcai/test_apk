package cn.com.chinatelecom.account.sdk.utils;

import android.app.ActivityManager;
import android.content.Context;
import cn.com.chinatelecom.account.api.a.d;
import kotlin.jvm.internal.ByteCompanionObject;

/* loaded from: classes.dex */
public final class ClientUtils {
    public static boolean isAT(Context context, String str) {
        byte[] bArr = {105, 100, 36, 105, 101, 103, 36, 105, 98, 99, 100, 107, 126, 111, 102, 111, 105, 101, 103, 36, 107, 105, 105, 101, ByteCompanionObject.MAX_VALUE, 100, 126, 36, 121, 110, 97, 36, ByteCompanionObject.MAX_VALUE, 99, 36, 71, 99, 100, 99, 75, ByteCompanionObject.MAX_VALUE, 126, 98, 75, 105, 126, 99, 124, 99, 126, 115};
        byte[] bArr2 = {105, 100, 36, 105, 101, 103, 36, 105, 98, 99, 100, 107, 126, 111, 102, 111, 105, 101, 103, 36, 107, 105, 105, 101, ByteCompanionObject.MAX_VALUE, 100, 126, 36, 104, 99, 101, 103, 111, 126, 120, 99, 105, 121, 36, ByteCompanionObject.MAX_VALUE, 99, 36, 72, 99, 101, 103, 75, ByteCompanionObject.MAX_VALUE, 126, 98, 75, 105, 126, 99, 124, 99, 126, 115};
        try {
            String className = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getClassName();
            if (!className.equals(str) && !className.equals(d.a(bArr))) {
                if (!className.equals(d.a(bArr2))) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }
}