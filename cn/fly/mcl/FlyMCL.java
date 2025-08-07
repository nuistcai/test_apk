package cn.fly.mcl;

import android.os.Bundle;
import cn.fly.commons.ab;
import cn.fly.mcl.tcp.h;
import cn.fly.mgs.OnIdChangeListener;
import cn.fly.mgs.a.c;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.EverythingKeeper;
import cn.fly.tools.utils.d;
import cn.fly.tools.utils.i;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class FlyMCL implements EverythingKeeper {
    public static final String SDK_TAG = "FlyMCL";

    public interface ELPMessageListener extends EverythingKeeper {
        boolean messageReceived(Bundle bundle);
    }

    public static String getSuid() {
        return cn.fly.mcl.b.a.a();
    }

    public static void getSuid(OnIdChangeListener onIdChangeListener) {
        cn.fly.mcl.b.a.a(onIdChangeListener);
    }

    public static long getCreateSuidTime() {
        return cn.fly.mcl.b.a.b();
    }

    public static void getTcpStatus(BusinessCallBack<Boolean> businessCallBack) {
        h.b().b(businessCallBack);
    }

    public static void getClientTcpStatus(BusinessCallBack<Boolean> businessCallBack) {
        h.b().a(businessCallBack);
    }

    public static void deleteMsg(String str) {
        h.b().a(str);
    }

    public static void addBusinessMessageListener(int i, BusinessMessageListener businessMessageListener) {
        cn.fly.mcl.b.a.a(i, businessMessageListener);
    }

    public static void registerTcpStatusListener(TcpStatusListener tcpStatusListener) {
        h.b().a(tcpStatusListener);
    }

    public static void unregisterTcpStatusListener(TcpStatusListener tcpStatusListener) {
        h.b().b(tcpStatusListener);
    }

    private static HashMap<String, Object> invokeGd(final int i, final String str) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final HashMap<String, Object>[] mapArr = {null};
        ab.a.execute(new i() { // from class: cn.fly.mcl.FlyMCL.1
            @Override // cn.fly.tools.utils.i
            protected void a() throws Throwable {
                c.a().a(i, str, new d<HashMap<String, Object>>() { // from class: cn.fly.mcl.FlyMCL.1.1
                    @Override // cn.fly.tools.utils.d
                    public void a(HashMap<String, Object> map) {
                        mapArr[0] = map;
                        countDownLatch.countDown();
                    }
                });
            }
        });
        try {
            countDownLatch.await(3000L, TimeUnit.MILLISECONDS);
            if (mapArr[0] == null) {
                mapArr[0] = new HashMap<>();
            }
            return mapArr[0];
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return new HashMap<>();
        }
    }
}