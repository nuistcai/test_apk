package cn.fly.verify.util;

import android.content.Context;
import android.net.wifi.WifiManager;
import cn.fly.FlySDK;
import cn.fly.tools.utils.DH;
import cn.fly.verify.util.e;
import cn.fly.verify.v;

/* loaded from: classes.dex */
public class h {
    public static void a() {
        l.b(new e.a<Boolean>() { // from class: cn.fly.verify.util.h.1
            @Override // cn.fly.verify.util.e.a
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onResult(Boolean bool, Throwable th) {
                boolean zBooleanValue = bool.booleanValue();
                boolean zA = l.a(FlySDK.getContext());
                int iM = e.m();
                if (zBooleanValue && zA && "oppo".equalsIgnoreCase(e.j()) && iM >= 23 && iM <= 28) {
                    try {
                        v.a("opt for Oppo");
                        h.a(FlySDK.getContext().getApplicationContext());
                    } catch (Throwable th2) {
                        v.a(th2, "JNI invoke error,may be libverify so lost or obfuscate");
                    }
                }
            }
        });
    }

    public static void a(Context context) {
        if (context != null) {
            try {
                WifiManager wifiManager = (WifiManager) DH.SyncMtd.getSystemServiceSafe("wifi");
                if (wifiManager != null) {
                    v.a("oppo connect thread: " + Thread.currentThread().getName());
                    int networkId = wifiManager.getConnectionInfo().getNetworkId();
                    wifiManager.disableNetwork(networkId);
                    wifiManager.disconnect();
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        v.a("reconnect wifi interrupted " + e.getMessage());
                    }
                    wifiManager.enableNetwork(networkId, true);
                    wifiManager.reconnect();
                }
            } catch (Throwable th) {
                v.a(th, "reConnect wifi error");
            }
        }
    }
}