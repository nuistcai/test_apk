package cn.fly.tools.b;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import cn.fly.commons.a.l;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.tools.utils.ResHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class h {
    public static Object a(Context context, String str) throws Throwable {
        int i;
        Object objA;
        Parcel parcel;
        IBinder iBinder;
        if (!cn.fly.commons.e.f() || !cn.fly.tools.utils.e.a().a(str) || DH.SyncMtd.getOSVersionIntForFly() < 23) {
            return null;
        }
        cn.fly.tools.a.d dVarA = cn.fly.tools.a.d.a(context);
        if (DH.SyncMtd.getOSVersionIntForFly() >= 31) {
            Object objA2 = dVarA.a(l.a("036ef$edekelejedem_h,elTdej;ejelFf0emgfReVgjOj*gfel*dej^ejel9f8hk-g*efeh^g-gjHj") + "$" + l.a("0070gkehejLhVedFg(ek"));
            if (objA2 == null) {
                i = 0;
                objA = null;
            } else {
                i = 0;
                objA = dVarA.a(l.a("036ef(edekelejedem)h]el+dej*ejelSf+emgf1e:gjCj6gfelHdej@ejelRf4hkAgEefehWgTgjWj") + "$" + l.a("007Zgkehej_hYed1gWek"), objA2, l.a("005Iggehej*h,ed"), (Class[]) null, (Object[]) null, (Object[]) null);
            }
        } else {
            i = 0;
            objA = dVarA.a(l.a("032ef;edekelejedem$hLelPdejRejelHfHemgfel-dejBejelRf!hk;gLefehKgJgj.j"), (Object) null, l.a("028dIek,gejg>hdekeleggm,gk1ek(gdejg8edhmekeleeejed(gOek"), new Class[]{String.class, Long.TYPE, Float.TYPE, Boolean.TYPE}, new Object[]{str, 0, 0, true}, (Object[]) null);
        }
        int iIntValue = ((Integer) dVarA.a(l.a("033ef@edekelejedemOh0el5dej*ejelMf6emffgfel>dej7ejel%f<id.efe.fkLg=ek") + "$" + l.a("004+fmKjKehgg"), l.a("027TgdhkgefhfmgefegdffhifheifkLgjGgfCe^gjSj2gfelFdejJejelOf"), null, -1)).intValue();
        String strA = l.a("025efDedekelejedemelgjemfmZgHekeeejIdg6id:efe<fk9gKek");
        String strA2 = l.a("010<fk,gj>fmSg;ekeeejRdg");
        Class[] clsArr = new Class[1];
        clsArr[i] = String.class;
        Object[] objArr = new Object[1];
        objArr[i] = l.a("008hZel2dejLejelOf");
        IBinder iBinder2 = (IBinder) dVarA.a(strA, (Object) null, strA2, clsArr, objArr, (Object[]) null);
        if (objA != null && iBinder2 != null) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(l.a("033ef_edekelejedem3hOel(dejMejel1f!emffgfel@dej=ejelXfYidUefe3fkAgYek"));
                if (DH.SyncMtd.getOSVersionIntForFly() >= 31) {
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject((Parcelable) objA, i);
                    parcel = parcelObtain2;
                    iBinder = iBinder2;
                } else {
                    parcelObtain.writeInt(1);
                    Class<?> cls = objA.getClass();
                    String strA3 = l.a("013FghekejDjg5gdelhmHe[ek$dgh");
                    Class[] clsArr2 = new Class[2];
                    clsArr2[i] = Parcel.class;
                    clsArr2[1] = Integer.TYPE;
                    Integer numValueOf = Integer.valueOf(i);
                    Object[] objArr2 = new Object[2];
                    objArr2[i] = parcelObtain;
                    objArr2[1] = numValueOf;
                    Object obj = objA;
                    parcel = parcelObtain2;
                    iBinder = iBinder2;
                    try {
                        dVarA.a(cls, obj, strA3, clsArr2, objArr2, (Object[]) null);
                    } catch (Throwable th) {
                        th = th;
                        cn.fly.tools.a.d.a(context).b(context);
                        parcel.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                parcelObtain.writeString(context.getPackageName());
                if (DH.SyncMtd.getOSVersionIntForFly() >= 30) {
                    parcelObtain.writeString(context.getAttributionTag());
                }
                iBinder.transact(iIntValue, parcelObtain, parcel, i);
                parcel.readException();
                Object typedObject = parcel.readTypedObject(a());
                cn.fly.tools.a.d.a(context).b(context);
                parcel.recycle();
                parcelObtain.recycle();
                return typedObject;
            } catch (Throwable th2) {
                th = th2;
                parcel = parcelObtain2;
            }
        }
        return null;
    }

    private static Parcelable.Creator<?> a() throws Throwable {
        return (Parcelable.Creator) ReflectHelper.getStaticField(ReflectHelper.importClass(l.a("025ef)edekelejedem0hAel*dej<ejelWf)emgfel+dej2ejelCf")), l.a("007]fehkhjgegdhihk"));
    }

    public static Object a(Context context, String str, long j) throws Throwable {
        String str2;
        int i;
        int i2;
        Object objA;
        Parcel parcel;
        Parcel parcel2;
        Parcel parcel3;
        Parcel parcel4;
        Object objCreateProxy;
        Object objA2;
        Object objA3;
        if (!cn.fly.commons.e.e() || !cn.fly.tools.utils.e.a().a(str) || DH.SyncMtd.getOSVersionIntForFly() < 23) {
            return null;
        }
        cn.fly.tools.a.d dVarA = cn.fly.tools.a.d.a(context);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Object[] objArr = new Object[1];
        if (DH.SyncMtd.getOSVersionIntForFly() >= 31) {
            Object objA4 = dVarA.a(l.a("032efIedekelejedem_h'el4dej@ejel6fIemgfelYdej_ejel fShkDg+efehDg gj2j") + "$" + l.a("007RgkehejWh%edWg_ek"), new Class[]{Long.TYPE}, new Object[]{0L});
            if (objA4 != null) {
                i = 0;
                i2 = 2;
                str2 = "$";
                objA3 = dVarA.a(l.a("032efKedekelejedemBhDelSdej=ejel^f6emgfel%dej0ejel*f<hk:gGefeh^g=gj-j") + "$" + l.a("007<gkehej-h3ed,g$ek"), objA4, l.a("0057ggehejZhCed"), (Class[]) null, (Object[]) null, (Object[]) null);
            } else {
                str2 = "$";
                i = 0;
                i2 = 2;
                objA3 = null;
            }
            objA = objA3;
        } else {
            str2 = "$";
            i = 0;
            i2 = 2;
            objA = dVarA.a(l.a("032efLedekelejedem7hZelYdej:ejelRfSemgfel@dejEejel$fQhk_gXefehEg_gjSj"), (Object) null, l.a("028dSek gejgPhdekeleggm;gk=ek!gdejgDedhmekeleeejedCgWek"), new Class[]{String.class, Long.TYPE, Float.TYPE, Boolean.TYPE}, new Object[]{str, 0, 0, true}, (Object[]) null);
        }
        String strA = l.a("025efQedekelejedemelgjemfm:gXekeeejLdg$idQefeTfk%g(ek");
        String strA2 = l.a("010%fkCgj.fm g2ekeeej.dg");
        Class[] clsArr = new Class[1];
        clsArr[i] = String.class;
        Object[] objArr2 = new Object[1];
        objArr2[i] = l.a("008hIelQdej:ejel?f");
        IBinder iBinder = (IBinder) dVarA.a(strA, (Object) null, strA2, clsArr, objArr2, (Object[]) null);
        if (objA == null || iBinder == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(l.a("033efWedekelejedem$hAel%dej3ejelOf5emffgfel.dej-ejel+fBidNefe_fk=g!ek"));
            if (DH.SyncMtd.getOSVersionIntForFly() >= 31) {
                try {
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject((Parcelable) objA, i);
                    Consumer consumer = new Consumer() { // from class: cn.fly.tools.b.h.1
                        @Override // java.util.function.Consumer
                        public void accept(Object obj) {
                            try {
                                objArr[0] = obj;
                            } finally {
                                try {
                                } finally {
                                }
                            }
                        }
                    };
                    String str3 = str2;
                    String str4 = l.a("032ef;edekelejedem2hHelOdej'ejel]f@emgfel[dej8ejel7f.id9efe@fkZgVek") + str3 + l.a("027:je9gj'feehekekDgfjEgfel6dejCejelZf3gdek ef2gj!kXelek5j");
                    Class[] clsArr2 = new Class[3];
                    clsArr2[i] = Executor.class;
                    clsArr2[1] = Consumer.class;
                    clsArr2[i2] = CancellationSignal.class;
                    Object[] objArr3 = new Object[3];
                    objArr3[i] = Executors.newSingleThreadExecutor();
                    objArr3[1] = consumer;
                    objArr3[i2] = null;
                    Object objA5 = dVarA.a(str4, clsArr2, objArr3);
                    String strA3 = l.a("012jAelhk]gdg9ejee.gVekffed");
                    Class[] clsArr3 = new Class[1];
                    clsArr3[i] = Object.class;
                    Object[] objArr4 = new Object[1];
                    objArr4[i] = consumer;
                    parcel3 = parcelObtain2;
                    try {
                        String str5 = (String) dVarA.a(AppOpsManager.class, (Object) null, strA3, clsArr3, objArr4, (Object[]) null);
                        if (TextUtils.isEmpty(str5)) {
                            parcel4 = parcelObtain;
                        } else {
                            parcel4 = parcelObtain;
                            try {
                                parcel4.writeStrongInterface((IInterface) objA5);
                                parcel4.writeString(context.getPackageName());
                                parcel4.writeString(context.getAttributionTag());
                                parcel4.writeString(str5);
                                iBinder.transact(((Integer) dVarA.a(l.a("033ef:edekelejedem?h4elLdejJejel]f5emffgfel)dej1ejelQf'id efe^fkYg6ek") + str3 + l.a("0048fm?j=ehgg"), l.a("030:gdhkgefhfmgefegdffhifheifkYgj(feehekek4gfjIgfel;dej?ejel2f"), null, -1)).intValue(), parcel4, parcel3, 0);
                            } catch (Throwable th) {
                                th = th;
                                parcel = parcel3;
                                parcel2 = parcel4;
                                parcel.recycle();
                                parcel2.recycle();
                                cn.fly.tools.a.d.a(context).b(context);
                                throw th;
                            }
                        }
                        parcel = parcel3;
                        parcel2 = parcel4;
                        parcel.readException();
                        countDownLatch.await(j, TimeUnit.MILLISECONDS);
                        Object obj = objArr[0];
                        parcel.recycle();
                        parcel2.recycle();
                        cn.fly.tools.a.d.a(context).b(context);
                        return obj;
                    } catch (Throwable th2) {
                        th = th2;
                        parcel4 = parcelObtain;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    parcel3 = parcelObtain2;
                    parcel4 = parcelObtain;
                }
            } else {
                String str6 = str2;
                parcel4 = parcelObtain;
                try {
                    parcel4.writeInt(1);
                    Class<?> cls = objA.getClass();
                    String strA4 = l.a("013:ghekej7jgUgdelhm$eGek3dgh");
                    Class[] clsArr4 = new Class[i2];
                    clsArr4[0] = Parcel.class;
                    clsArr4[1] = Integer.TYPE;
                    Object[] objArr5 = new Object[i2];
                    objArr5[0] = parcel4;
                    objArr5[1] = 0;
                    parcel2 = parcel4;
                    try {
                        dVarA.a(cls, objA, strA4, clsArr4, objArr5, (Object[]) null);
                        HashMap map = new HashMap();
                        final int iIdentityHashCode = System.identityHashCode(map);
                        try {
                            map.put(l.a("017EelGfRgfelUdejJejel.fOfeEiefZfk5g3ed"), new ReflectHelper.a<Object[], Object>() { // from class: cn.fly.tools.b.h.2
                                @Override // cn.fly.tools.utils.ReflectHelper.a
                                public Object a(Object[] objArr6) {
                                    if (objArr6 != null) {
                                        try {
                                            if (objArr6.length > 0) {
                                                FlyLog.getInstance().d("[212] oncge" + objArr6[0], new Object[0]);
                                                if ((objArr6[0] instanceof List) && ((List) objArr6[0]).size() > 0) {
                                                    objArr[0] = ((List) objArr6[0]).get(r5.size() - 1);
                                                } else {
                                                    objArr[0] = objArr6[0];
                                                }
                                            }
                                        } finally {
                                            try {
                                            } catch (Throwable th4) {
                                            }
                                        }
                                    }
                                    countDownLatch.countDown();
                                    return null;
                                }
                            });
                            map.put("equals", new ReflectHelper.a<Object[], Object>() { // from class: cn.fly.tools.b.h.3
                                @Override // cn.fly.tools.utils.ReflectHelper.a
                                public Object a(Object[] objArr6) {
                                    if (objArr6 == null || objArr6[0] == null) {
                                        return false;
                                    }
                                    return Boolean.valueOf(objArr6[0].hashCode() == iIdentityHashCode);
                                }
                            });
                            map.put(l.a("008ieFgjGi:feeled9g"), new ReflectHelper.a<Object[], Object>() { // from class: cn.fly.tools.b.h.4
                                @Override // cn.fly.tools.utils.ReflectHelper.a
                                public Object a(Object[] objArr6) {
                                    return Integer.valueOf(iIdentityHashCode);
                                }
                            });
                            objCreateProxy = ReflectHelper.createProxy((Map<String, ReflectHelper.a<Object[], Object>>) map, (Class<?>[]) new Class[]{Class.forName(l.a("033ef3edekelejedemNhSel;dej[ejelGfFemgfel.dejAejelHfVgfejgj,jgfg<ek"))});
                        } catch (Throwable th4) {
                            FlyLog.getInstance().d(th4);
                            objCreateProxy = null;
                        }
                        if (DH.SyncMtd.getOSVersionIntForFly() >= 30) {
                            String str7 = l.a("032ef4edekelejedem9h5elUdejLejelFfYemgfelGdej'ejelRf idMefe5fk%g0ek") + str6 + l.a("025RgfelKdej7ejelDf2gfejgjZjgfg3ekgdekQefYgjZk:elek!j");
                            Class<?> cls2 = Class.forName(l.a("032ef9edekelejedemQh4elLdejQejelAfBemgfelIdejOejel(fSid-efe9fk]g]ek"));
                            Class<?> cls3 = Class.forName(l.a("033ef edekelejedem2hAel3dej!ejel!fKemgfel!dej@ejelVfIgfejgj[jgfg%ek"));
                            Class[] clsArr5 = new Class[i2];
                            clsArr5[0] = cls2;
                            clsArr5[1] = cls3;
                            Object[] objArr6 = new Object[i2];
                            objArr6[0] = DH.SyncMtd.getSystemServiceSafe(l.a("008h>el;dejOejel^f"));
                            objArr6[1] = objCreateProxy;
                            objA2 = dVarA.a(str7, clsArr5, objArr6);
                            dVarA.a(objA2.getClass(), objA2, l.a("0086ek%g$fkejgj>jgEek"), new Class[]{Executor.class}, new Object[]{Executors.newSingleThreadExecutor()}, (Object[]) null);
                        } else {
                            String str8 = l.a("032efQedekelejedemPhVelBdej ejel7f<emgfel)dejBejel9fIidGefe+fk$g<ek") + str6 + l.a("017+gfejgj!jgfg'ekgdek_ef-gj<kPelek^j");
                            Class<?> cls4 = Class.forName(l.a("032efDedekelejedem;h^elXdejMejelIfEemgfel!dej!ejel*f$id<efe.fkXg9ek"));
                            Class<?> cls5 = Class.forName(l.a("033ef,edekelejedem3hMel@dej ejel6f+emgfelMdejCejel7f6gfejgjZjgfg!ek"));
                            Class[] clsArr6 = new Class[3];
                            clsArr6[0] = cls4;
                            clsArr6[1] = cls5;
                            clsArr6[i2] = Looper.class;
                            Object systemServiceSafe = DH.SyncMtd.getSystemServiceSafe(l.a("008h elMdej@ejelPf"));
                            Looper looperC = l.a().c();
                            Object[] objArr7 = new Object[3];
                            objArr7[0] = systemServiceSafe;
                            objArr7[1] = objCreateProxy;
                            objArr7[i2] = looperC;
                            objA2 = dVarA.a(str8, clsArr6, objArr7);
                        }
                        parcel2.writeStrongBinder((IBinder) objA2);
                        parcel2.writeInt(0);
                        parcel2.writeString(context.getPackageName());
                        int iIntValue = ((Integer) dVarA.a(l.a("033efOedekelejedem)h1el(dej6ejel5fZemffgfel-dejBejelYf2id>efeBfk8g9ek") + str6 + l.a("004EfmEjRehgg"), l.a("034MgdhkgefhfmgefegdffhifheiekBgSefeh>gOgj8jYgfelAdej;ejelOf'fl?k0edOejgKgj"), null, -1)).intValue();
                        parcel = parcelObtain2;
                        try {
                            iBinder.transact(iIntValue, parcel2, parcel, 0);
                            parcel.readException();
                            countDownLatch.await(j, TimeUnit.MILLISECONDS);
                            Object obj2 = objArr[0];
                            parcel.recycle();
                            parcel2.recycle();
                            cn.fly.tools.a.d.a(context).b(context);
                            return obj2;
                        } catch (Throwable th5) {
                            th = th5;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        parcel = parcelObtain2;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    parcel = parcelObtain2;
                    parcel2 = parcel4;
                    parcel.recycle();
                    parcel2.recycle();
                    cn.fly.tools.a.d.a(context).b(context);
                    throw th;
                }
            }
        } catch (Throwable th8) {
            th = th8;
            parcel = parcelObtain2;
            parcel2 = parcelObtain;
        }
        parcel.recycle();
        parcel2.recycle();
        cn.fly.tools.a.d.a(context).b(context);
        throw th;
    }

    public static class a {
        private final Object a;

        public a(Object obj) {
            this.a = obj;
        }

        public float a() {
            return ((Float) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("011QfkEgj,ge0dd_ehek4edYfd"), Float.valueOf(0.0f), new Object[0])).floatValue();
        }

        public double b() {
            return ((Double) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("011EfkPgjCgf7ej.ejUjUehed.g"), Double.valueOf(0.0d), new Object[0])).doubleValue();
        }

        public double c() {
            return ((Double) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("012KfkXgj^gfel:f>fkejYjLehedIg"), Double.valueOf(0.0d), new Object[0])).doubleValue();
        }

        public long d() {
            return ((Long) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("0070fkQgj7gdejegQg"), 0L, new Object[0])).longValue();
        }

        public String e() {
            return (String) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("011Sfk5gjShmekeleeejedPgMek"), null, new Object[0]);
        }

        public double f() {
            return ((Double) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("011AfkIgjOge+hj!ej=jMehed@g"), Double.valueOf(0.0d), new Object[0])).doubleValue();
        }

        public float g() {
            return ((Float) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("010Kfk0gj5gk+geAekejYf]fk"), Float.valueOf(0.0f), new Object[0])).floatValue();
        }

        public float h() {
            return ((Float) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("008>fkZgj%fm]kgg<ed"), Float.valueOf(0.0f), new Object[0])).floatValue();
        }

        public boolean i() {
            if (DH.SyncMtd.getOSVersionIntForFly() >= 26) {
                return ((Boolean) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("019ieJgjhlNg]ek0j]ejRdeh(ge4ddYehek8edMfd"), false, new Object[0])).booleanValue();
            }
            return false;
        }

        public float j() {
            if (DH.SyncMtd.getOSVersionIntForFly() >= 26) {
                return ((Float) ReflectHelper.invokeInstanceMethodNoThrow(this.a, l.a("0253fkFgj2hl)g[ek j$ej=dehHge5dd!ehek8edDfdid^gjg>ekgj"), Float.valueOf(0.0f), new Object[0])).floatValue();
            }
            return 0.0f;
        }

        public float a(Object obj) {
            if (obj != null) {
                try {
                    return ((Float) ReflectHelper.invokeInstanceMethod(this.a, l.a("010 edejgjWjefdgDgdel"), new Object[]{obj}, new Class[]{Class.forName(l.a("025ef*edekelejedemCh5el[dej4ejelKfUemgfelYdejQejel6f"))})).floatValue();
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
            }
            return 0.0f;
        }

        public long k() {
            long jD = d();
            try {
                long jL = l();
                if (jL != -1) {
                    long caliSysTime = ResHelper.getCaliSysTime() - jL;
                    if (Math.abs(caliSysTime - jD) > 600000) {
                        return caliSysTime;
                    }
                    return jD;
                }
                return jD;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return jD;
            }
        }

        private long l() {
            if (DH.SyncMtd.getOSVersionIntForFly() < 17) {
                return -1L;
            }
            try {
                long jLongValue = ((Long) ReflectHelper.invokeInstanceMethodNoThrow(this.a, "getElapsedRealtimeNanos", -1L, new Object[0])).longValue();
                long millis = TimeUnit.NANOSECONDS.toMillis(SystemClock.elapsedRealtimeNanos() - jLongValue);
                if (millis < 0) {
                    return -1L;
                }
                return millis;
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return -1L;
            }
        }
    }
}