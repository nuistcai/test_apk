package cn.fly.verify;

import android.os.SystemClock;
import android.util.Log;
import cn.fly.FlySDK;
import cn.fly.tools.utils.DH;
import cn.fly.verify.aj;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.util.k;
import com.alibaba.fastjson.asm.Opcodes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class n {
    public static AtomicBoolean a = new AtomicBoolean(false);

    private static Integer a(HashMap map, String str, Integer num) {
        if (map != null && map.containsKey(str)) {
            Object obj = map.get(str);
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
        }
        return num;
    }

    private static String a(HashMap map, String str, String str2) {
        if (map != null && map.containsKey(str)) {
            Object obj = map.get(str);
            if (obj instanceof String) {
                return (String) obj;
            }
        }
        return str2;
    }

    private static ArrayList a(HashMap map, String str, ArrayList arrayList) {
        if (map != null && map.containsKey(str)) {
            Object obj = map.get(str);
            if (obj instanceof ArrayList) {
                return (ArrayList) obj;
            }
        }
        return arrayList;
    }

    public static HashMap<String, Object> a(u uVar) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        HashMap<String, Object> mapA = null;
        try {
            a.set(false);
            mapA = p.a(e.b(), uVar);
            if (uVar != null) {
                t tVarB = uVar.b("init");
                tVarB.c(true);
                tVarB.b(String.valueOf(SystemClock.uptimeMillis() - jUptimeMillis));
                tVarB.a(Opcodes.GOTO_W);
                uVar.a(tVarB);
                uVar.b();
            }
        } catch (Throwable th) {
            try {
                v.a(th);
                if (uVar != null) {
                    t tVarB2 = uVar.b("cdn_failure");
                    tVarB2.b(String.valueOf(SystemClock.uptimeMillis() - jUptimeMillis));
                    if (th instanceof VerifyException) {
                        VerifyException verifyException = th;
                        tVarB2.b(verifyException.getCode());
                        tVarB2.c(verifyException.getMessage());
                    }
                    uVar.a(tVarB2);
                }
                jUptimeMillis = SystemClock.uptimeMillis();
                mapA = p.a(uVar);
                if (uVar != null) {
                    uVar.a(String.valueOf(SystemClock.uptimeMillis() - jUptimeMillis));
                }
            } catch (Throwable th2) {
                v.a(th2);
                if (uVar != null && (th2 instanceof VerifyException)) {
                    uVar.a(new VerifyException(m.C_INIT_UNEXPECTED_ERROR.a(), String.valueOf(SystemClock.uptimeMillis() - jUptimeMillis)), th2);
                }
            }
        }
        a.set(mapA != null);
        return mapA;
    }

    private static HashMap a(HashMap map, String str, HashMap map2) {
        if (map != null && map.containsKey(str)) {
            Object obj = map.get(str);
            if (obj instanceof HashMap) {
                return (HashMap) obj;
            }
        }
        return map2;
    }

    public static void a() {
        cn.fly.verify.util.k.a.execute(new k.a() { // from class: cn.fly.verify.n.1
            @Override // cn.fly.verify.util.k.a
            public void a() {
                synchronized (n.a) {
                    if (n.a.get() && e.a(null, false) != null) {
                        v.a("already init, return");
                        return;
                    }
                    if (FlySDK.isForb()) {
                        Log.e("[FlyVerify] ==>%s", "privacy is forb");
                    } else {
                        if (!DH.SyncMtd.isInMainProcess()) {
                            Log.e("[FlyVerify] ==>%s", "not main process");
                            return;
                        }
                        final u uVar = new u(g.INIT);
                        uVar.a((String) null, (String) null, "start");
                        cn.fly.verify.util.e.a(new k.a() { // from class: cn.fly.verify.n.1.1
                            @Override // cn.fly.verify.util.k.a
                            public void a() {
                                if (!cn.fly.verify.util.l.d()) {
                                    if (uVar != null) {
                                        uVar.a(new VerifyException(m.C_INIT_NO_NET), new VerifyException(m.C_INIT_NO_NET));
                                    }
                                } else {
                                    v.a("cdn start");
                                    HashMap<String, Object> mapA = n.a(uVar);
                                    if (mapA != null) {
                                        v.a("init end " + mapA);
                                        n.a(mapA);
                                    }
                                }
                            }

                            @Override // cn.fly.verify.util.k.a
                            public void a(Throwable th) {
                                v.a(th);
                                if (uVar != null) {
                                    uVar.a(new VerifyException(m.C_INIT_SERVER_ERROR), new VerifyException(m.C_INIT_SERVER_ERROR.a(), cn.fly.verify.util.l.a(th)));
                                }
                            }
                        }, uVar);
                    }
                }
            }
        });
    }

    public static void a(int i) throws InterruptedException {
        v.a("init in " + i + " seconds");
        a();
        int i2 = (int) ((i * 1000) / 50);
        int i3 = 0;
        while (e.a() == null) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
            }
            i3++;
            if (i3 >= i2) {
                break;
            }
        }
        v.a("wait " + (i3 * 50) + "seconds");
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x02e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a(HashMap<String, Object> map) {
        String str;
        String str2;
        String str3;
        String str4;
        int i;
        int i2;
        int i3;
        aj.a aVar;
        aj.a aVar2;
        aj.a aVar3;
        String str5;
        String str6;
        String str7;
        String str8;
        aj.a aVar4;
        aj.a aVar5;
        String str9;
        String str10;
        String str11;
        String str12;
        aj.a aVar6;
        aj.a aVar7;
        HashMap mapA;
        HashMap mapA2;
        HashMap mapA3;
        int i4;
        int i5;
        int i6;
        aj.a aVar8;
        int i7;
        int i8;
        String strA = a(map, "cdnKey", "LphSZLqaUeFdyaQq");
        HashMap mapA4 = a(map, "clientConfig", new HashMap());
        if (a(mapA4, "oppoNet", (Integer) 0).intValue() == 0) {
            cn.fly.verify.util.h.a();
        }
        ArrayList arrayListA = a(mapA4, "notUpload", new ArrayList());
        int iIntValue = a(mapA4, "openTimeOut", (Integer) 4000).intValue();
        int iIntValue2 = a(mapA4, "preTimeOut", (Integer) 4000).intValue();
        int iIntValue3 = a(mapA4, "verifyTimeOut", (Integer) 4000).intValue();
        e.a(a(mapA4, "unknownTry", (Integer) 0).intValue() == 1);
        int iIntValue4 = a(mapA4, "preVerifyWay", (Integer) 0).intValue();
        int iIntValue5 = a(mapA4, "verifyWay", (Integer) 0).intValue();
        int iIntValue6 = a(mapA4, "subIdEnable", (Integer) 1).intValue();
        int iIntValue7 = a(mapA4, "subIdsEnable", (Integer) 0).intValue();
        int iIntValue8 = a(mapA4, "slotsEnable", (Integer) 1).intValue();
        String strA2 = a(mapA4, "factoryBlst", (String) null);
        HashMap mapA5 = a(map, "loginSwitch", (HashMap) null);
        int iIntValue9 = a(mapA5, "cucc", (Integer) 0).intValue();
        int iIntValue10 = a(mapA5, "ctcc", (Integer) 0).intValue();
        int iIntValue11 = a(mapA5, "cmcc", (Integer) 0).intValue();
        HashMap mapA6 = a(map, "multiLogin", (HashMap) null);
        if (mapA6 != null && mapA6.containsKey("clientId") && mapA6.containsKey("clientSecret")) {
            String strA3 = a(mapA6, "clientId", (String) null);
            String strA4 = a(mapA6, "clientSecret", (String) null);
            Integer numA = a(mapA6, "channel", (Integer) null);
            String strA5 = a(mapA6, "channelAccount", (String) null);
            if (iIntValue9 == 1) {
                str = "clientId";
                str2 = "clientSecret";
                str3 = "channel";
                str4 = "channelAccount";
                i4 = iIntValue10;
                i3 = iIntValue9;
                aVar8 = new aj.a(2, strA3, strA4, strA, arrayListA, iIntValue2, iIntValue, iIntValue3, 1, numA, strA5, iIntValue4, iIntValue5);
                v.a("cu multilogin");
                i5 = iIntValue11;
                i6 = 1;
            } else {
                str = "clientId";
                str2 = "clientSecret";
                str3 = "channel";
                str4 = "channelAccount";
                i4 = iIntValue10;
                i3 = iIntValue9;
                i5 = iIntValue11;
                i6 = 1;
                aVar8 = null;
            }
            if (i5 == i6) {
                i = i5;
                aVar2 = new aj.a(1, strA3, strA4, strA, arrayListA, iIntValue2, iIntValue, iIntValue3, 1, numA, strA5, iIntValue4, iIntValue5);
                v.a("cm multilogin");
                i7 = i4;
                i8 = 1;
            } else {
                i = i5;
                i7 = i4;
                i8 = 1;
                aVar2 = null;
            }
            if (i7 == i8) {
                i2 = i7;
                aVar3 = new aj.a(3, strA3, strA4, strA, arrayListA, iIntValue2, iIntValue, iIntValue3, 1, numA, strA5, iIntValue4, iIntValue5);
                v.a("ct multilogin");
                aVar = aVar8;
                if (i3 == 0 || (mapA3 = a(map, "cuccLogin", (HashMap) null)) == null) {
                    str5 = str;
                } else {
                    String str13 = str;
                    if (mapA3.containsKey(str13)) {
                        String str14 = str2;
                        if (!mapA3.containsKey(str14)) {
                            str6 = str14;
                            str5 = str13;
                            str7 = str3;
                            str8 = str4;
                            aVar4 = aVar;
                            if (i == 0) {
                                aVar5 = aVar4;
                                str9 = str8;
                                str10 = str6;
                                str11 = str5;
                                str12 = str7;
                            }
                            aVar6 = aVar2;
                            if (i2 != 0) {
                            }
                            aj ajVar = new aj(aVar7, aVar5, aVar3);
                            ajVar.a(strA2);
                            ajVar.a(Integer.valueOf(iIntValue6));
                            ajVar.b(Integer.valueOf(iIntValue7));
                            ajVar.c(Integer.valueOf(iIntValue8));
                            e.a(ajVar);
                        }
                        String str15 = str3;
                        String str16 = str4;
                        str8 = str16;
                        str6 = str14;
                        str5 = str13;
                        str7 = str15;
                        aVar4 = new aj.a(2, a(mapA3, str13, (String) null), a(mapA3, str14, (String) null), strA, arrayListA, iIntValue2, iIntValue, iIntValue3, 0, a(mapA3, str15, (Integer) null), a(mapA3, str16, (String) null), iIntValue4, iIntValue5);
                        if (i == 0 || (mapA2 = a(map, "cmccLogin", (HashMap) null)) == null) {
                            aVar5 = aVar4;
                            str9 = str8;
                            str10 = str6;
                            str11 = str5;
                        } else {
                            String str17 = str5;
                            if (mapA2.containsKey(str17)) {
                                String str18 = str6;
                                if (mapA2.containsKey(str18)) {
                                    String str19 = str7;
                                    String str20 = str8;
                                    str9 = str20;
                                    str12 = str19;
                                    str10 = str18;
                                    str11 = str17;
                                    aVar5 = aVar4;
                                    aVar2 = new aj.a(1, a(mapA2, str17, (String) null), a(mapA2, str18, (String) null), strA, arrayListA, iIntValue2, iIntValue, iIntValue3, 0, a(mapA2, str19, (Integer) null), a(mapA2, str20, (String) null), iIntValue4, iIntValue5);
                                    aVar6 = aVar2;
                                    if (i2 != 0 || (mapA = a(map, "ctccLogin", (HashMap) null)) == null) {
                                        aVar7 = aVar6;
                                    } else {
                                        String str21 = str11;
                                        if (mapA.containsKey(str21)) {
                                            String str22 = str10;
                                            if (mapA.containsKey(str22)) {
                                                String strA6 = a(mapA, str21, (String) null);
                                                String strA7 = a(mapA, str22, (String) null);
                                                Integer numA2 = a(mapA, str12, (Integer) null);
                                                String strA8 = a(mapA, str9, (String) null);
                                                aVar7 = aVar6;
                                                aVar3 = new aj.a(3, strA6, strA7, strA, arrayListA, iIntValue2, iIntValue, iIntValue3, 0, numA2, strA8, iIntValue4, iIntValue5);
                                            }
                                        }
                                    }
                                    aj ajVar2 = new aj(aVar7, aVar5, aVar3);
                                    ajVar2.a(strA2);
                                    ajVar2.a(Integer.valueOf(iIntValue6));
                                    ajVar2.b(Integer.valueOf(iIntValue7));
                                    ajVar2.c(Integer.valueOf(iIntValue8));
                                    e.a(ajVar2);
                                }
                                str10 = str18;
                                str11 = str17;
                                aVar5 = aVar4;
                                str9 = str8;
                            } else {
                                str11 = str17;
                                aVar5 = aVar4;
                                str9 = str8;
                                str10 = str6;
                            }
                        }
                        str12 = str7;
                        aVar6 = aVar2;
                        if (i2 != 0) {
                            aVar7 = aVar6;
                        }
                        aj ajVar22 = new aj(aVar7, aVar5, aVar3);
                        ajVar22.a(strA2);
                        ajVar22.a(Integer.valueOf(iIntValue6));
                        ajVar22.b(Integer.valueOf(iIntValue7));
                        ajVar22.c(Integer.valueOf(iIntValue8));
                        e.a(ajVar22);
                    }
                    str5 = str13;
                }
                str6 = str2;
                str7 = str3;
                str8 = str4;
                aVar4 = aVar;
                if (i == 0) {
                }
                aVar6 = aVar2;
                if (i2 != 0) {
                }
                aj ajVar222 = new aj(aVar7, aVar5, aVar3);
                ajVar222.a(strA2);
                ajVar222.a(Integer.valueOf(iIntValue6));
                ajVar222.b(Integer.valueOf(iIntValue7));
                ajVar222.c(Integer.valueOf(iIntValue8));
                e.a(ajVar222);
            }
            i2 = i7;
            aVar = aVar8;
        } else {
            str = "clientId";
            str2 = "clientSecret";
            str3 = "channel";
            str4 = "channelAccount";
            i = iIntValue11;
            i2 = iIntValue10;
            i3 = iIntValue9;
            aVar = null;
            aVar2 = null;
        }
        aVar3 = null;
        if (i3 == 0) {
            str5 = str;
            str6 = str2;
            str7 = str3;
            str8 = str4;
            aVar4 = aVar;
        }
        if (i == 0) {
        }
        aVar6 = aVar2;
        if (i2 != 0) {
        }
        aj ajVar2222 = new aj(aVar7, aVar5, aVar3);
        ajVar2222.a(strA2);
        ajVar2222.a(Integer.valueOf(iIntValue6));
        ajVar2222.b(Integer.valueOf(iIntValue7));
        ajVar2222.c(Integer.valueOf(iIntValue8));
        e.a(ajVar2222);
    }
}