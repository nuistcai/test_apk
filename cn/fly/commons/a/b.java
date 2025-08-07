package cn.fly.commons.a;

import cn.fly.FlySDK;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ResHelper;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class b extends c {
    public b() {
        super(l.a("002<gggj"), 0L, l.a("005 gggjfk'ek"), 86400L, a(l.a("002<gggj"), (Long) 0L));
    }

    @Override // cn.fly.commons.a.c
    protected void a() {
        n();
    }

    private void n() {
        DH.RequestBuilder cLoc;
        DH.RequestBuilder mnbclfo = DH.requester(FlySDK.getContext()).getCarrierStrict(false).getCarrierNameStrict(false).getMnbclfo();
        final int oSVersionIntForFly = DH.SyncMtd.getOSVersionIntForFly();
        if (oSVersionIntForFly >= 17) {
            cLoc = mnbclfo.getACIfo();
        } else {
            cLoc = mnbclfo.getCLoc();
        }
        cLoc.request(new DH.DHResponder() { // from class: cn.fly.commons.a.b.1
            @Override // cn.fly.tools.utils.DH.DHResponder
            public void onResponse(DH.DHResponse dHResponse) {
                int i;
                HashMap<String, Object> map;
                HashMap map2;
                int iIntValue;
                long j;
                int iIntValue2;
                long jLongValue;
                long jLongValue2;
                int i2;
                int i3;
                int i4;
                int iIntValue3;
                int i5;
                try {
                    i = Integer.parseInt(dHResponse.getCarrierStrict(new int[0]));
                } catch (Throwable th) {
                    i = -1;
                }
                if (oSVersionIntForFly < 17 || dHResponse.getACIfo() == null || dHResponse.getACIfo().isEmpty()) {
                    map = null;
                    if (dHResponse.getCLoc() == null) {
                        map2 = null;
                    } else {
                        map2 = (HashMap) dHResponse.getCLoc();
                        map2.put("dbm", -1);
                    }
                } else {
                    ArrayList<HashMap<String, Object>> aCIfo = dHResponse.getACIfo();
                    map = new HashMap<>();
                    map.put("bsd", aCIfo);
                    HashMap<String, Object> map3 = aCIfo.get(0);
                    map2 = new HashMap();
                    if (((Integer) map3.get(l.a("004jUfd[kg"))).intValue() == 2) {
                        map2.put(l.a("016:feedeg$e,fe5ghh2gfelPdej>ejel7f"), 1);
                        map2.put(l.a("003Mggejed"), map3.get(l.a("004dghh")));
                        map2.put(l.a("003Lgjejed"), map3.get(l.a("003Lgjejed")));
                        map2.put(l.a("003fPejed"), map3.get(l.a("003fPejed")));
                        map2.put(l.a("003hej"), map3.get(l.a("003hej")));
                        map2.put(l.a("003hOelEf"), map3.get(l.a("003hOelEf")));
                    } else {
                        map2.put(l.a("016Qfeedeg)eIfe@ghh(gfelEdej[ejel$f"), -1);
                        map2.put(l.a("003k4gjHd"), map3.get(l.a("003k4gjHd")));
                        map2.put(l.a("003hed"), map3.get(l.a("003hed")));
                        map2.put(l.a("004dghh"), map3.get(l.a("004dghh")));
                    }
                    map2.put("dbm", map3.get("dbm"));
                }
                if (map2 != null) {
                    if (((Integer) ResHelper.forceCast(map2.get(l.a("016Dfeedeg1e%fe0ghh)gfel^dej_ejel0f")), -1)).intValue() == 1) {
                        int iIntValue4 = ((Integer) ResHelper.forceCast(map2.get(l.a("003hej")), -1)).intValue();
                        int iIntValue5 = ((Integer) ResHelper.forceCast(map2.get(l.a("003h%elIf")), -1)).intValue();
                        jLongValue = ((Long) ResHelper.forceCast(map2.get(l.a("003>ggejed")), -1L)).longValue();
                        int iIntValue6 = ((Integer) ResHelper.forceCast(map2.get(l.a("003>gjejed")), -1)).intValue();
                        i3 = iIntValue4;
                        i4 = iIntValue5;
                        iIntValue3 = ((Integer) ResHelper.forceCast(map2.get(l.a("003f?ejed")), -1)).intValue();
                        iIntValue2 = -1;
                        j = -1;
                        jLongValue2 = -1;
                        i2 = iIntValue6;
                        iIntValue = -1;
                    } else {
                        iIntValue = ((Integer) ResHelper.forceCast(map2.get(l.a("003k'gjYd")), -1)).intValue();
                        j = -1;
                        iIntValue2 = ((Integer) ResHelper.forceCast(map2.get(l.a("003hed")), -1)).intValue();
                        jLongValue = -1;
                        jLongValue2 = ((Long) ResHelper.forceCast(map2.get(l.a("004dghh")), -1L)).longValue();
                        i2 = -1;
                        i3 = -1;
                        i4 = -1;
                        iIntValue3 = -1;
                    }
                    if (i != -1 && iIntValue2 != -1 && jLongValue2 != j) {
                        if (map == null) {
                            map = new HashMap<>();
                        }
                        map.put(l.a("003hed"), Integer.valueOf(iIntValue2));
                        map.put(l.a("004dghh"), Long.valueOf(jLongValue2));
                        if (iIntValue != -1) {
                            map.put(l.a("003k+gjGd"), Integer.valueOf(iIntValue));
                        }
                    }
                    if (i != -1 && jLongValue != -1 && i2 != -1 && (i5 = iIntValue3) != -1) {
                        if (map == null) {
                            map = new HashMap<>();
                        }
                        map.put(l.a("003Tggejed"), Long.valueOf(jLongValue));
                        map.put(l.a("003)gjejed"), Integer.valueOf(i2));
                        map.put(l.a("003f0ejed"), Integer.valueOf(i5));
                        if (i3 != -1) {
                            map.put(l.a("003hej"), Integer.valueOf(i3));
                        }
                        int i6 = i4;
                        if (i6 != -1) {
                            map.put(l.a("003h+el?f"), Integer.valueOf(i6));
                        }
                    }
                    map.put("dbm", Integer.valueOf(((Integer) ResHelper.forceCast(map2.get("dbm"), -1)).intValue()));
                    if (map != null) {
                        map.put(l.a("007deCekekej1gOek"), Integer.valueOf(i));
                        map.put(l.a("009=gjejegel%kfe9egUg"), dHResponse.getCarrierNameStrict(new int[0]));
                        ArrayList<HashMap<String, Object>> mnbclfo2 = dHResponse.getMnbclfo();
                        if (mnbclfo2 != null && mnbclfo2.size() > 0) {
                            map.put(l.a("006fge3ekggfd"), mnbclfo2);
                        }
                        b.this.a("BSIOMT", map, true);
                    }
                }
            }
        });
    }
}