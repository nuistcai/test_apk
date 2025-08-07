package cn.fly.commons;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.HashonHelper;
import cn.fly.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class k {
    private static k b = new k();
    private volatile boolean c = false;
    private volatile long d = 0;
    private final ConcurrentHashMap<String, Object> e = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Object> f = new ConcurrentHashMap<>();
    public final AtomicBoolean a = new AtomicBoolean(false);

    private k() {
    }

    public static k a() {
        return b;
    }

    public boolean b() {
        return a(false);
    }

    public synchronized boolean a(boolean z) {
        return !b(z);
    }

    public ConcurrentHashMap<String, Object> c() {
        return this.e;
    }

    private synchronized boolean b(boolean z) {
        long jLongValue;
        String str;
        try {
            if (!z) {
                jLongValue = ((Long) c.a(cn.fly.commons.a.l.a("004gdgVfj"), 5L)).longValue() * 1000;
                str = (String) c.a(cn.fly.commons.a.l.a("002dKed"), cn.fly.commons.a.l.a("006Aififigigigig"));
            } else {
                HashMap mapFromJson = HashonHelper.fromJson(ad.b().e());
                if (mapFromJson.isEmpty()) {
                    mapFromJson = HashonHelper.fromJson(ad.b().d());
                }
                jLongValue = ((Long) ResHelper.forceCast(mapFromJson.get(cn.fly.commons.a.l.a("004gdgNfj")), 5L)).longValue() * 1000;
                str = (String) ResHelper.forceCast(mapFromJson.get(cn.fly.commons.a.l.a("002d3ed")), cn.fly.commons.a.l.a("006Kififigigigig"));
            }
            if (this.d != 0 && System.currentTimeMillis() - this.d <= jLongValue) {
                return this.c;
            }
            boolean zA = a(str);
            if (this.d == 0 || zA != this.c) {
                c(zA);
            }
            this.d = System.currentTimeMillis();
            this.c = zA;
            return zA;
        } catch (Throwable th) {
            FlyLog.getInstance().e(th);
            return true;
        }
    }

    private boolean a(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            char[] charArray = str.toCharArray();
            HashMap map = new HashMap();
            boolean zA = false;
            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] == '1') {
                    zA |= a(i);
                } else if (charArray[i] != '0') {
                    List arrayList = (List) map.get(Character.valueOf(charArray[i]));
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(Integer.valueOf(i));
                    map.put(Character.valueOf(charArray[i]), arrayList);
                }
            }
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Iterator it2 = ((List) ((Map.Entry) it.next()).getValue()).iterator();
                boolean zA2 = true;
                while (it2.hasNext()) {
                    zA2 &= a(((Integer) it2.next()).intValue());
                }
                zA |= zA2;
            }
            return zA;
        } catch (Throwable th) {
            FlyLog.getInstance().e(th);
            return true;
        }
    }

    private boolean a(final int i) {
        final boolean[] zArr = {true};
        DH.RequestBuilder requestBuilderRequester = DH.requester(FlySDK.getContext());
        switch (i) {
            case 0:
                requestBuilderRequester.checkUA();
                break;
            case 1:
                requestBuilderRequester.usbEnable();
                break;
            case 2:
                requestBuilderRequester.vpn();
                break;
            case 3:
                requestBuilderRequester.isMwpy();
                break;
            case 4:
                requestBuilderRequester.isRooted();
                break;
            case 5:
                requestBuilderRequester.cx();
                break;
        }
        requestBuilderRequester.request(new DH.DHResponder() { // from class: cn.fly.commons.k.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                switch (i) {
                    case 0:
                        zArr[0] = dHResponse.checkUA();
                        k.this.e.put(cn.fly.commons.a.l.a("002<eh-d"), Integer.valueOf(zArr[0] ? 1 : 0));
                        break;
                    case 1:
                        zArr[0] = dHResponse.usbEnable();
                        k.this.e.put(cn.fly.commons.a.l.a("002@ehed"), Integer.valueOf(zArr[0] ? 1 : 0));
                        break;
                    case 2:
                        zArr[0] = dHResponse.vpn();
                        k.this.e.put(cn.fly.commons.a.l.a("002Mee5k"), Integer.valueOf(zArr[0] ? 1 : 0));
                        break;
                    case 3:
                        zArr[0] = dHResponse.isMwpy();
                        k.this.e.put(cn.fly.commons.a.l.a("002-ghRk"), Integer.valueOf(zArr[0] ? 1 : 0));
                        break;
                    case 4:
                        zArr[0] = dHResponse.isRooted();
                        k.this.e.put(cn.fly.commons.a.l.a("0021ek[j"), Integer.valueOf(zArr[0] ? 1 : 0));
                        break;
                    case 5:
                        zArr[0] = dHResponse.cx();
                        k.this.e.put(cn.fly.commons.a.l.a("002>fj+k"), Integer.valueOf(zArr[0] ? 1 : 0));
                        break;
                }
            }
        });
        return zArr[0];
    }

    private void c(boolean z) {
        HashMap map = new HashMap();
        map.put(cn.fly.commons.a.l.a("005dhge8ek"), Integer.valueOf(!z ? 1 : 0));
        map.put(cn.fly.commons.a.l.a("002'eh0d"), ResHelper.forceCast(this.e.get(cn.fly.commons.a.l.a("002'eh0d")), 0));
        map.put(cn.fly.commons.a.l.a("002Gehed"), ResHelper.forceCast(this.e.get(cn.fly.commons.a.l.a("002Gehed")), 0));
        map.put(cn.fly.commons.a.l.a("002(eeEk"), ResHelper.forceCast(this.e.get(cn.fly.commons.a.l.a("002(eeEk")), 0));
        map.put(cn.fly.commons.a.l.a("0027ghTk"), ResHelper.forceCast(this.e.get(cn.fly.commons.a.l.a("0027ghTk")), 0));
        map.put(cn.fly.commons.a.l.a("002Dek1j"), ResHelper.forceCast(this.e.get(cn.fly.commons.a.l.a("002Dek1j")), 0));
        map.put(cn.fly.commons.a.l.a("002Zfj:k"), ResHelper.forceCast(this.e.get(cn.fly.commons.a.l.a("002Zfj:k")), 0));
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put(cn.fly.commons.a.l.a("004jPfd?kg"), "ECMT");
        map2.put(cn.fly.commons.a.l.a("004Red1eje"), map);
        map2.put(cn.fly.commons.a.l.a("008'edPejgjOejegEg"), Long.valueOf(jCurrentTimeMillis));
        d.a().a(jCurrentTimeMillis, map2);
    }

    public boolean a(HashMap<String, Object> map) {
        try {
            List<String> list = (List) ResHelper.forceCast(map.get("j"), null);
            if (list != null && list.size() > 0) {
                boolean zA = false;
                for (String str : list) {
                    if (str.contains(",")) {
                        boolean zA2 = true;
                        for (String str2 : str.split(",")) {
                            zA2 &= a(str2, map);
                        }
                        zA |= zA2;
                    } else {
                        zA |= a(str, map);
                    }
                }
                this.e.put(cn.fly.commons.a.l.a("006IfkTkHeiek4g6gj"), Boolean.valueOf(zA ? false : true));
                return !zA;
            }
        } catch (Throwable th) {
            FlyLog.getInstance().e(th);
        }
        this.e.put(cn.fly.commons.a.l.a("006CfkBkMeiek=g5gj"), true);
        return true;
    }

    private boolean a(String str, HashMap<String, Object> map) {
        boolean z;
        if (TextUtils.equals(str, "a")) {
            z = ((Integer) ResHelper.forceCast(map.get("a"), 0)).intValue() == 1 && d();
            this.f.put("a", Boolean.valueOf(z));
            return z;
        }
        if (TextUtils.equals(str, "p")) {
            List<String> list = (List) ResHelper.forceCast(map.get("p"), null);
            ArrayList<Boolean> arrayList = new ArrayList<>();
            boolean zB = b(arrayList, list);
            this.f.put("p", arrayList);
            return zB;
        }
        if (TextUtils.equals(str, "fp")) {
            List<String> list2 = (List) ResHelper.forceCast(map.get("fp"), null);
            ArrayList<Boolean> arrayList2 = new ArrayList<>();
            boolean zB2 = b(arrayList2, list2);
            this.f.put("fp", arrayList2);
            return zB2;
        }
        if (TextUtils.equals(str, "s")) {
            boolean zA = a(new ArrayList<>(), (List<String>) ResHelper.forceCast(map.get("s"), null));
            this.f.put("s", Boolean.valueOf(zA));
            return zA;
        }
        if (TextUtils.equals(str, "fs")) {
            boolean zA2 = a(new ArrayList<>(), (List<String>) ResHelper.forceCast(map.get("fs"), null));
            this.f.put("fs", Boolean.valueOf(zA2));
            return zA2;
        }
        if (TextUtils.equals(str, "d")) {
            z = ((Integer) ResHelper.forceCast(map.get("d"), 0)).intValue() == 1 && cn.fly.tools.b.c.a(FlySDK.getContext()).d().aC();
            this.f.put("d", Boolean.valueOf(z));
            return z;
        }
        if (!TextUtils.equals(str, "bl")) {
            return false;
        }
        boolean zB3 = b((String) ResHelper.forceCast(map.get("bl"), ""));
        this.f.put("bl", Boolean.valueOf(zB3));
        return zB3;
    }

    private boolean b(String str) {
        String strD = C0041r.d();
        if (TextUtils.isEmpty(strD) || strD.length() < str.length()) {
            return false;
        }
        String[] strArrSplit = strD.split("");
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        boolean zEquals = false;
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '1') {
                zEquals |= TextUtils.equals(strArrSplit[i], "1");
            } else if (charArray[i] == '2') {
                arrayList.add(Integer.valueOf(i));
            }
        }
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            boolean zEquals2 = true;
            while (it.hasNext()) {
                zEquals2 &= TextUtils.equals(strArrSplit[((Integer) it.next()).intValue()], "1");
            }
            return zEquals | zEquals2;
        }
        return zEquals;
    }

    private boolean d() {
        final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        DH.requester(FlySDK.getContext()).getCarrierStrict(false).request(new DH.DHResponder() { // from class: cn.fly.commons.k.2
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                String carrierStrict = dHResponse.getCarrierStrict(new int[0]);
                if (!TextUtils.isEmpty(carrierStrict) && !TextUtils.equals("-1", carrierStrict)) {
                    linkedBlockingQueue.offer(Boolean.valueOf(!carrierStrict.startsWith("460")));
                }
                linkedBlockingQueue.offer(Boolean.valueOf(!k.this.a(FlySDK.getContext())));
            }
        });
        try {
            Boolean bool = (Boolean) linkedBlockingQueue.poll(120L, TimeUnit.MILLISECONDS);
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.getLanguage().startsWith("zh") && TextUtils.equals(locale.getCountry(), "CN");
    }

    private boolean a(ArrayList<Boolean> arrayList, final List<String> list) {
        final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        if (list != null && list.size() > 0) {
            DH.RequestBuilder requestBuilderRequester = DH.requester(FlySDK.getContext());
            for (int i = 0; i < list.size(); i++) {
                requestBuilderRequester.queryIntentServices(new Intent(list.get(i)), 0);
            }
            requestBuilderRequester.request(new DH.DHResponder() { // from class: cn.fly.commons.k.3
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        List<ResolveInfo> listQueryIntentServices = dHResponse.queryIntentServices(i2);
                        if (listQueryIntentServices != null && listQueryIntentServices.size() > 0) {
                            linkedBlockingQueue.offer(true);
                        }
                    }
                    linkedBlockingQueue.offer(false);
                }
            });
        }
        try {
            Boolean bool = (Boolean) linkedBlockingQueue.poll(150L, TimeUnit.MILLISECONDS);
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    private boolean b(final ArrayList<Boolean> arrayList, final List<String> list) {
        DH.RequestBuilder requestBuilderRequester = DH.requester(FlySDK.getContext());
        if (list == null || list.size() == 0) {
            return false;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            requestBuilderRequester.isPackageInstalled(it.next());
        }
        final boolean[] zArr = {false};
        requestBuilderRequester.request(new DH.DHResponder() { // from class: cn.fly.commons.k.4
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                for (int i = 0; i < list.size(); i++) {
                    boolean zIsPackageInstalled = dHResponse.isPackageInstalled(i);
                    arrayList.add(Boolean.valueOf(zIsPackageInstalled));
                    boolean[] zArr2 = zArr;
                    zArr2[0] = zIsPackageInstalled | zArr2[0];
                    if (zArr[0]) {
                        return;
                    }
                }
            }
        });
        return zArr[0];
    }

    public void a(HashMap<String, Object> map, HashMap<String, Object> map2, HashMap<String, Object> map3) {
        try {
            boolean zBooleanValue = ((Boolean) ResHelper.forceCast(this.e.get(cn.fly.commons.a.l.a("006g)egeiekIg-gj")), false)).booleanValue();
            boolean zBooleanValue2 = ((Boolean) ResHelper.forceCast(this.e.get(cn.fly.commons.a.l.a("006Kfk<k[eiek*gVgj")), false)).booleanValue();
            HashMap map4 = new HashMap(4);
            map4.put(cn.fly.commons.a.l.a("003)ek'gJgj"), Boolean.valueOf(zBooleanValue));
            map4.put(cn.fly.commons.a.l.a("003Rekejed"), ResHelper.forceCast(map.get(cn.fly.commons.a.l.a("003Rekejed")), null));
            if (!zBooleanValue && map2 != null) {
                map4.put(cn.fly.commons.a.l.a("0039gjejed"), ResHelper.forceCast(map2.get(cn.fly.commons.a.l.a("0039gjejed")), null));
            } else {
                map4.put(cn.fly.commons.a.l.a("003>gjejed"), ResHelper.forceCast(map.get(cn.fly.commons.a.l.a("003>gjejed")), null));
            }
            this.e.put(cn.fly.commons.a.l.a("006g-egeiek0gCgj"), HashonHelper.fromHashMap(map4));
            if (zBooleanValue) {
                HashMap map5 = new HashMap(4);
                map5.put(cn.fly.commons.a.l.a("003QekJgUgj"), Boolean.valueOf(zBooleanValue2));
                map5.put(cn.fly.commons.a.l.a("003Mekejed"), ResHelper.forceCast(map.get(cn.fly.commons.a.l.a("003Mekejed")), null));
                if (!zBooleanValue2 && map3 != null) {
                    map5.put(cn.fly.commons.a.l.a("003Vgjejed"), ResHelper.forceCast(map3.get(cn.fly.commons.a.l.a("003Vgjejed")), null));
                } else {
                    map5.put(cn.fly.commons.a.l.a("003Agjejed"), ResHelper.forceCast(map.get(cn.fly.commons.a.l.a("003Agjejed")), null));
                }
                map5.putAll(this.f);
                this.e.put(cn.fly.commons.a.l.a("006,fk+kWeiek[g0gj"), HashonHelper.fromHashMap(map5));
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }
}