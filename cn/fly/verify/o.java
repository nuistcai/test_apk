package cn.fly.verify;

import android.os.SystemClock;
import android.util.Base64;
import androidx.core.app.NotificationCompat;
import cn.fly.FlySDK;
import cn.fly.tools.network.NetCommunicator;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.Data;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.verify.common.exception.VerifyException;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class o {
    private static o a;
    private final NetCommunicator b = new NetCommunicator(1024, "d008219b14c84872559aaf9e69d1348175289c186912da64b2393bab376bb0d6b471220cb29cbc9875b148b593eb9d7c4c359549a1aff22f6de9d18d22f0b6cb", "1f228b2b8fbb7317674db20bab1d4b0f0ddb3e1f3a93177f1821c026ffd7c6b782be720a308ab69bf6c631c3c0c4d68bf9d92ddaaf712a032d591ba1c296df13332a23e37b281e5fd9b93ab016dd3efc5de45e264ed692ac63ac40013f507cd272b7aeeb85be9fe2f31f11b8c55d904b5331932c70c7cf3f2b05cb802f6b89a7");

    private o() {
    }

    public static o a() {
        if (a == null) {
            synchronized (o.class) {
                if (a == null) {
                    a = new o();
                }
            }
        }
        return a;
    }

    public HashMap<String, Object> a(String str, String str2, u uVar) throws VerifyException {
        HashMap<String, Object> mapFromJson;
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("appkey", FlySDK.getAppkey());
            NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
            networkTimeOut.connectionTimeout = 3000;
            networkTimeOut.readTimout = 5000;
            String strHttpGetNew = new NetworkHelper().httpGetNew(str, null, map, networkTimeOut);
            if (uVar != null) {
                uVar.a((String) null, (String) null, "cdn_request");
            }
            long jUptimeMillis = SystemClock.uptimeMillis();
            boolean z = false;
            JSONObject jSONObject = new JSONObject(Data.AES128Decode(str2, Base64.decode(strHttpGetNew, 0)));
            try {
                mapFromJson = HashonHelper.fromJson(jSONObject.optString("res"));
            } catch (Throwable th) {
                z = true;
                mapFromJson = null;
            }
            int iOptInt = jSONObject.optInt(NotificationCompat.CATEGORY_STATUS);
            String strOptString = jSONObject.optString("error");
            if (uVar != null) {
                uVar.a((String) null, (String) null, "config_decode", String.valueOf(SystemClock.uptimeMillis() - jUptimeMillis));
            }
            if (iOptInt != 200 || z || z) {
                throw new VerifyException(m.C_INIT_SERVER_ERROR.a(), strOptString);
            }
            if (mapFromJson != null) {
                return mapFromJson;
            }
            throw new VerifyException(m.C_INIT_SERVER_ERROR.a(), strOptString);
        } catch (Throwable th2) {
            if (th2 instanceof VerifyException) {
                throw th2;
            }
            throw new VerifyException(m.C_INIT_UNEXPECTED_ERROR.a(), cn.fly.verify.util.l.a(th2));
        }
    }

    public HashMap<String, Object> a(HashMap<String, Object> map, String str) throws VerifyException {
        try {
            HashMap<String, String> map2 = new HashMap<>();
            map2.put("appkey", FlySDK.getAppkey());
            return (HashMap) this.b.requestSynchronized(map2, map, str, false);
        } catch (Throwable th) {
            throw new VerifyException(m.C_INIT_UNEXPECTED_ERROR.a(), cn.fly.verify.util.l.a(th));
        }
    }
}