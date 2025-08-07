package cn.fly.commons;

import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.network.NetCommunicator;
import cn.fly.tools.network.NetworkHelper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ResHelper;
import com.tencent.bugly.BuglyStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class b {
    private static volatile b c = null;
    private byte[] b;
    private final HashMap<String, Object> d = new HashMap<>();
    private final File a = ResHelper.getDataCacheFile(FlySDK.getContext(), o.a("005:dlUccBdchf"));

    private b() {
        if (this.a.exists() && this.a.length() > 0) {
            try {
                Map<? extends String, ? extends Object> map = (Map) C0041r.a(this.a, b());
                if (map != null && !map.isEmpty()) {
                    this.d.putAll(map);
                }
            } catch (Throwable th) {
            }
        }
    }

    static b a() {
        if (c == null) {
            synchronized (b.class) {
                if (c == null) {
                    c = new b();
                }
            }
        }
        return c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] b() {
        if (this.b == null) {
            try {
                this.b = Arrays.copyOf(o.a("012_diflVf+fgdgicjfhjhcdkijdf").getBytes("UTF-8"), 16);
            } catch (Throwable th) {
            }
        }
        return this.b;
    }

    void a(final String str, final Object obj) {
        ab.a.execute(new cn.fly.tools.utils.i() { // from class: cn.fly.commons.b.1
            @Override // cn.fly.tools.utils.i
            protected void a() throws Throwable {
                synchronized (this) {
                    b.this.d.put(str, obj);
                    C0041r.a(b.this.a, b.this.b(), b.this.d);
                }
            }
        });
    }

    void a(String str, String str2) {
        if (!this.d.isEmpty()) {
            try {
                HashMap<String, Object> map = new HashMap<>();
                map.put(o.a("004jgdi"), Integer.valueOf(DH.SyncMtd.getPlatformCode()));
                map.put(o.a("005]dfdkdc0fg"), DH.SyncMtd.getModel());
                if (!TextUtils.isEmpty(str2)) {
                    map.put(o.a("004?dcdgdidc"), str2);
                }
                map.put("usridt", ac.i());
                map.put(o.a("004Ndfdkdidc"), str);
                ArrayList arrayList = new ArrayList();
                Iterator<Object> it = this.d.values().iterator();
                while (it.hasNext()) {
                    arrayList.add((HashMap) it.next());
                }
                map.put(o.a("0054dcHdidVfi"), arrayList);
                map.put(o.a("005i@dkehZfe"), null);
                NetworkHelper.NetworkTimeOut networkTimeOut = new NetworkHelper.NetworkTimeOut();
                networkTimeOut.readTimout = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
                networkTimeOut.connectionTimeout = 15000;
                if ("200".equals(String.valueOf(HashonHelper.fromJson((String) new NetCommunicator(1024, "ceeef5035212dfe7c6a0acdc0ef35ce5b118aab916477037d7381f85c6b6176fcf57b1d1c3296af0bb1c483fe5e1eb0ce9eb2953b44e494ca60777a1b033cc07", "191737288d17e660c4b61440d5d14228a0bf9854499f9d68d8274db55d6d954489371ecf314f26bec236e58fac7fffa9b27bcf923e1229c4080d49f7758739e5bd6014383ed2a75ce1be9b0ab22f283c5c5e11216c5658ba444212b6270d629f2d615b8dfdec8545fb7d4f935b0cc10b6948ab4fc1cb1dd496a8f94b51e888dd", networkTimeOut).requestWithoutEncode(false, null, map, j.a().a("gclg") + "/v6/gcl", false)).get(o.a("006^fi2idi)dgfi"))))) {
                    c();
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    private void c() {
        synchronized (this) {
            this.d.clear();
            C0041r.a(this.a, b(), this.d);
        }
    }
}