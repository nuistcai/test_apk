package cn.fly.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import cn.fly.FlySDK;
import cn.fly.commons.C0041r;
import cn.fly.commons.ab;
import cn.fly.commons.m;
import cn.fly.tools.FlyLog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class DH {
    public static final int GPI_STRATEGY_VALIDITY_3_MINUTE = 180000;
    public static final int GPI_STRATEGY_VALIDITY_ALL = 0;

    public interface DHResponder {
        void onResponse(DHResponse dHResponse) throws Throwable;
    }

    public static RequestBuilder requester(Context context) {
        return new RequestBuilder(context);
    }

    public static class RequestBuilder {
        private final Context a;
        private final LinkedList<a> b;

        private static class a {
            public final String a;
            public final Object[] b;

            private a(String str, Object... objArr) {
                this.a = str;
                this.b = objArr;
            }
        }

        private RequestBuilder(Context context) {
            this.b = new LinkedList<>();
            this.a = context;
        }

        public void request(final DHResponder dHResponder) {
            try {
                boolean z = Looper.getMainLooper() == Looper.myLooper();
                final Boolean bool = cn.fly.tools.c.a.b.get();
                final Boolean bool2 = cn.fly.tools.c.a.c.get();
                final boolean z2 = z;
                Runnable runnable = new Runnable() { // from class: cn.fly.tools.utils.DH.RequestBuilder.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            cn.fly.tools.c.a.a.set(true);
                            cn.fly.tools.c.a.b.set(bool);
                            cn.fly.tools.c.a.c.set(bool2);
                            final DHResponse dHResponseA = RequestBuilder.this.a();
                            if (dHResponder != null) {
                                if (z2) {
                                    UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.tools.utils.DH.RequestBuilder.1.1
                                        @Override // android.os.Handler.Callback
                                        public boolean handleMessage(Message message) {
                                            try {
                                                dHResponder.onResponse(dHResponseA);
                                            } catch (Throwable th) {
                                                FlyLog.getInstance().d(th, "Error from caller", new Object[0]);
                                            }
                                            return false;
                                        }
                                    });
                                } else {
                                    try {
                                        dHResponder.onResponse(dHResponseA);
                                    } catch (Throwable th) {
                                        FlyLog.getInstance().d(th, "Error from caller", new Object[0]);
                                    }
                                }
                            }
                            cn.fly.tools.c.a.a.set(false);
                            cn.fly.tools.c.a.b.set(false);
                            cn.fly.tools.c.a.c.set(false);
                        } catch (Throwable th2) {
                            FlyLog.getInstance().d(th2);
                            RequestBuilder.this.a(dHResponder);
                        }
                    }
                };
                if (z) {
                    ab.e.execute(runnable);
                } else {
                    runnable.run();
                }
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                if (dHResponder != null) {
                    a(dHResponder);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(DHResponder dHResponder) {
            if (dHResponder != null) {
                try {
                    dHResponder.onResponse(new DHResponse());
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th, "Error from caller", new Object[0]);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public DHResponse a() {
            DHResponse dHResponse = new DHResponse();
            for (int i = 0; i < this.b.size(); i++) {
                a aVar = this.b.get(i);
                try {
                    dHResponse.a(aVar.a, a(aVar.a, aVar.b));
                } catch (Throwable th) {
                    try {
                        FlyLog.getInstance().d(th);
                        dHResponse.a(aVar.a, (Object) null, true);
                    } catch (Throwable th2) {
                        FlyLog.getInstance().d(th2);
                    }
                }
            }
            return dHResponse;
        }

        private Object a(String str, Object[] objArr) throws Throwable {
            if ("gmpfo".equals(str)) {
                if (objArr != null && objArr.length == 2) {
                    return cn.fly.tools.b.c.a(this.a).d().b(false, 0, (String) objArr[0], ((Integer) objArr[1]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gmpfofce".equals(str)) {
                if (objArr != null && objArr.length == 3) {
                    return cn.fly.tools.b.c.a(this.a).d().b(((Boolean) objArr[0]).booleanValue(), 0, (String) objArr[1], ((Integer) objArr[2]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("getMpfos".equals(str)) {
                if (objArr != null && objArr.length == 3) {
                    return cn.fly.tools.b.c.a(this.a).d().b(false, ((Integer) objArr[0]).intValue(), (String) objArr[1], ((Integer) objArr[2]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("cird".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().a());
            }
            if ("gsimt".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().a(false);
            }
            if ("gsimtfce".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().a(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gbsi".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().b(false);
            }
            if ("gbsifce".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().b(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gstmpts".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().a((String) objArr[0]);
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gscsz".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().I();
            }
            if ("gcrie".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().c(false);
            }
            if ("gcriefce".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().c(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gcriefcestr".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().d(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gcrnm".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().e(false);
            }
            if ("gcrnmfce".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().e(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gcrnmfcestr".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().f(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gsnmd".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().Y();
            }
            if ("gsnmdfp".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().c((String) objArr[0]);
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gneyp".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().h(false);
            }
            if ("gneypnw".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().i(false);
            }
            if ("gneypfce".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().h(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("cknavbl".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().j(false));
            }
            if ("cknavblfc".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().j(((Boolean) objArr[0]).booleanValue()));
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gnktpfs".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().J();
            }
            if ("gdtlnktpfs".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().K();
            }
            if ("gdvk".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().W();
            }
            if ("gdvkfc".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().k(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gpnmmt".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().aa();
            }
            if ("gpnmfp".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().d((String) objArr[0]);
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gia".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().a(((Boolean) objArr[0]).booleanValue(), false);
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("giafce".equals(str)) {
                if (objArr != null && objArr.length == 2) {
                    return cn.fly.tools.b.c.a(this.a).d().a(((Boolean) objArr[0]).booleanValue(), ((Boolean) objArr[1]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gsl".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().V();
            }
            if ("gscpt".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().X();
            }
            if ("gavti".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().j();
            }
            if ("glctn".equals(str)) {
                if (objArr != null && objArr.length == 3) {
                    return cn.fly.tools.b.c.a(this.a).d().a(((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue(), ((Boolean) objArr[2]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gtecloc".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().t();
            }
            if ("gnbclin".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().u();
            }
            if ("gdvtp".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().s();
            }
            if ("wmcwi".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().v();
            }
            if ("ipgist".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().b((String) objArr[0]));
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gcuin".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().C();
            }
            if ("gtydvin".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().D();
            }
            if ("gqmkn".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().E();
            }
            if ("gszin".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().F();
            }
            if ("gmrin".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().G();
            }
            if ("gmivsn".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().k();
            }
            if ("gmivsnfly".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().l();
            }
            if ("cx".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().b());
            }
            if ("ckpd".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().c());
            }
            if ("ubenbl".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().h());
            }
            if ("dvenbl".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().g());
            }
            if ("ckua".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().f());
            }
            if ("vnmt".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().e());
            }
            if ("degb".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().d());
            }
            if ("iwpxy".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().i());
            }
            if ("gflv".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().O();
            }
            if ("gbsbd".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().P();
            }
            if ("gbfspy".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().Q();
            }
            if ("gbplfo".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().R();
            }
            if ("gdntp".equals(str)) {
                return Integer.valueOf(cn.fly.tools.b.c.a(this.a).d().L());
            }
            if ("gdntpstr".equals(str)) {
                return Integer.valueOf(cn.fly.tools.b.c.a(this.a).d().M());
            }
            if ("qritsvc".equals(str)) {
                if (objArr != null && objArr.length == 2) {
                    return cn.fly.tools.b.c.a(this.a).d().a((Intent) objArr[0], ((Integer) objArr[1]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("rsaciy".equals(str)) {
                if (objArr != null && objArr.length == 2) {
                    return cn.fly.tools.b.c.a(this.a).d().b((Intent) objArr[0], ((Integer) objArr[1]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gpgif".equals(str)) {
                if (objArr != null && objArr.length == 2) {
                    return cn.fly.tools.b.c.a(this.a).d().a(false, 0, (String) objArr[0], ((Integer) objArr[1]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gpgiffcin".equals(str)) {
                if (objArr != null && objArr.length == 3) {
                    return cn.fly.tools.b.c.a(this.a).d().a(((Boolean) objArr[0]).booleanValue(), 0, (String) objArr[1], ((Integer) objArr[2]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gpgifstrg".equals(str)) {
                if (objArr != null && objArr.length == 3) {
                    return cn.fly.tools.b.c.a(this.a).d().a(false, ((Integer) objArr[0]).intValue(), (String) objArr[1], ((Integer) objArr[2]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("giads".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().S();
            }
            if ("giadsstr".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().T();
            }
            if ("gdvda".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().ah();
            }
            if ("gdvdtnas".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().ai();
            }
            if ("galtut".equals(str)) {
                return Long.valueOf(cn.fly.tools.b.c.a(this.a).d().aj());
            }
            if ("gdvme".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().ak();
            }
            if ("gcrup".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().al();
            }
            if ("gcifm".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().am();
            }
            if ("godm".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().an();
            }
            if ("godhm".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().ao();
            }
            if ("galdm".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().ap();
            }
            if ("gtaif".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().aq();
            }
            if ("gtaifprm".equals(str)) {
                if (objArr != null && objArr.length == 2) {
                    return cn.fly.tools.b.c.a(this.a).d().a((String) objArr[0], ((Integer) objArr[1]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gtaifprmfce".equals(str)) {
                if (objArr != null && objArr.length == 3) {
                    return cn.fly.tools.b.c.a(this.a).d().a(((Boolean) objArr[0]).booleanValue(), (String) objArr[1], ((Integer) objArr[2]).intValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gtbdt".equals(str)) {
                return Long.valueOf(cn.fly.tools.b.c.a(this.a).d().as());
            }
            if ("gtscnin".equals(str)) {
                return Double.valueOf(cn.fly.tools.b.c.a(this.a).d().at());
            }
            if ("gtscnppi".equals(str)) {
                return Integer.valueOf(cn.fly.tools.b.c.a(this.a).d().au());
            }
            if ("ishmos".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().av());
            }
            if ("gthmosv".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().aw();
            }
            if ("gthmosdtlv".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().ax();
            }
            if ("gthmpmst".equals(str)) {
                return Integer.valueOf(cn.fly.tools.b.c.a(this.a).d().ay());
            }
            if ("gthmepmst".equals(str)) {
                return Integer.valueOf(cn.fly.tools.b.c.a(this.a).d().az());
            }
            if ("gtinnerlangmt".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().aA();
            }
            if ("gtgramgendt".equals(str)) {
                return Integer.valueOf(cn.fly.tools.b.c.a(this.a).d().aB());
            }
            if ("gtelcmefce".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().a(((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue(), ((Boolean) objArr[2]).booleanValue(), ((Boolean) objArr[3]).booleanValue());
            }
            if ("gtmwfo".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().g(false);
            }
            if ("wmcwifce".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().g(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gtaifok".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().ar();
            }
            if ("gtmcdi".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().a(false);
            }
            if ("gtmcdifce".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().a(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gtmbcdi".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().b(false);
            }
            if ("gtmbcdifce".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().b(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("miwpy".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().i());
            }
            if ("gtmnbclfo".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().u();
            }
            if ("ctedebbing".equals(str)) {
                return Boolean.valueOf(cn.fly.tools.b.c.a(this.a).d().aC());
            }
            if ("gteacifo".equals(str)) {
                return cn.fly.tools.b.c.a(this.a).d().aD();
            }
            if ("gtdm".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return cn.fly.tools.b.c.a(this.a).d().l(((Boolean) objArr[0]).booleanValue());
                }
                throw new Throwable("params illegal: " + objArr);
            }
            if ("gtlstactme".equals(str)) {
                if (objArr != null && objArr.length == 1) {
                    return Long.valueOf(cn.fly.tools.b.c.a(this.a).d().f((String) objArr[0]));
                }
                throw new Throwable("params illegal: " + objArr);
            }
            return null;
        }

        public RequestBuilder isRooted() {
            this.b.add(new a("cird", new Object[0]));
            return this;
        }

        public RequestBuilder getSSID() {
            this.b.add(new a("gsimt", new Object[0]));
            return this;
        }

        public RequestBuilder getSSIDForce(boolean z) {
            this.b.add(new a("gsimtfce", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getBssid() {
            this.b.add(new a("gbsi", new Object[0]));
            return this;
        }

        public RequestBuilder getBssidForce(boolean z) {
            this.b.add(new a("gbsifce", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getDeviceId() {
            return this;
        }

        public RequestBuilder getIMEI() {
            return this;
        }

        public RequestBuilder queryIMEI() {
            return this;
        }

        public RequestBuilder getSystemProperties(String str) {
            this.b.add(new a("gstmpts", new Object[]{str}));
            return this;
        }

        public RequestBuilder getSerialno() {
            return this;
        }

        public RequestBuilder getScreenSize() {
            this.b.add(new a("gscsz", new Object[0]));
            return this;
        }

        public RequestBuilder getCarrier() {
            this.b.add(new a("gcrie", new Object[0]));
            return this;
        }

        public RequestBuilder getCarrierForce(boolean z) {
            this.b.add(new a("gcriefce", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getCarrierStrict(boolean z) {
            this.b.add(new a("gcriefcestr", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getCarrierName() {
            this.b.add(new a("gcrnm", new Object[0]));
            return this;
        }

        public RequestBuilder getCarrierNameForce(boolean z) {
            this.b.add(new a("gcrnmfce", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getCarrierNameStrict(boolean z) {
            this.b.add(new a("gcrnmfcestr", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getSimSerialNumber() {
            return this;
        }

        public RequestBuilder getSignMD5() {
            this.b.add(new a("gsnmd", new Object[0]));
            return this;
        }

        public RequestBuilder getSignMD5ForPkg(String str) {
            this.b.add(new a("gsnmdfp", new Object[]{str}));
            return this;
        }

        public RequestBuilder getNetworkType() {
            this.b.add(new a("gneyp", new Object[0]));
            return this;
        }

        public RequestBuilder getNetworkTypeNew() {
            this.b.add(new a("gneypnw", new Object[0]));
            return this;
        }

        public RequestBuilder getNetworkTypeForce(boolean z) {
            this.b.add(new a("gneypfce", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder checkNetworkAvailable() {
            this.b.add(new a("cknavbl", new Object[0]));
            return this;
        }

        public RequestBuilder checkNetworkAvailableForce(boolean z) {
            this.b.add(new a("cknavblfc", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getNetworkTypeForStatic() {
            this.b.add(new a("gnktpfs", new Object[0]));
            return this;
        }

        public RequestBuilder getDetailNetworkTypeForStatic() {
            this.b.add(new a("gdtlnktpfs", new Object[0]));
            return this;
        }

        public RequestBuilder getDeviceKey() {
            this.b.add(new a("gdvk", new Object[0]));
            return this;
        }

        public RequestBuilder getDeviceKeyFromCache(boolean z) {
            this.b.add(new a("gdvkfc", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getAppName() {
            this.b.add(new a("gpnmmt", new Object[0]));
            return this;
        }

        public RequestBuilder getAppNameForPkg(String str) {
            this.b.add(new a("gpnmfp", new Object[]{str}));
            return this;
        }

        public RequestBuilder getIA(boolean z) {
            this.b.add(new a("gia", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getIAForce(boolean z, boolean z2) {
            this.b.add(new a("giafce", new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2)}));
            return this;
        }

        public RequestBuilder getSA() {
            this.b.add(new a("gsl", new Object[0]));
            return this;
        }

        public RequestBuilder getSdcardState() {
            return this;
        }

        public RequestBuilder getAdvertisingID() {
            this.b.add(new a("gavti", new Object[0]));
            return this;
        }

        public RequestBuilder getIMSI() {
            return this;
        }

        public RequestBuilder queryIMSI() {
            return this;
        }

        public RequestBuilder getLocation(int i, int i2, boolean z) {
            this.b.add(new a("glctn", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getCLoc() {
            this.b.add(new a("gtecloc", new Object[0]));
            return this;
        }

        public RequestBuilder getNeighboringCellInfo() {
            this.b.add(new a("gnbclin", new Object[0]));
            return this;
        }

        public RequestBuilder getDeviceType() {
            this.b.add(new a("gdvtp", new Object[0]));
            return this;
        }

        public RequestBuilder getTopActivity() {
            return this;
        }

        public RequestBuilder getCurrentWifiInfo() {
            this.b.add(new a("wmcwi", new Object[0]));
            return this;
        }

        public RequestBuilder isPackageInstalled(String str) {
            this.b.add(new a("ipgist", new Object[]{str}));
            return this;
        }

        public RequestBuilder getCPUInfo() {
            this.b.add(new a("gcuin", new Object[0]));
            return this;
        }

        public RequestBuilder getTTYDriversInfo() {
            this.b.add(new a("gtydvin", new Object[0]));
            return this;
        }

        public RequestBuilder getQemuKernel() {
            this.b.add(new a("gqmkn", new Object[0]));
            return this;
        }

        public RequestBuilder getSizeInfo() {
            this.b.add(new a("gszin", new Object[0]));
            return this;
        }

        public RequestBuilder getMemoryInfo() {
            this.b.add(new a("gmrin", new Object[0]));
            return this;
        }

        public RequestBuilder getMIUIVersion() {
            this.b.add(new a("gmivsn", new Object[0]));
            return this;
        }

        public RequestBuilder getMIUIVersionForFly() {
            this.b.add(new a("gmivsnfly", new Object[0]));
            return this;
        }

        public RequestBuilder cx() {
            this.b.add(new a("cx", new Object[0]));
            return this;
        }

        public RequestBuilder checkPad() {
            this.b.add(new a("ckpd", new Object[0]));
            return this;
        }

        public RequestBuilder usbEnable() {
            this.b.add(new a("ubenbl", new Object[0]));
            return this;
        }

        public RequestBuilder devEnable() {
            this.b.add(new a("dvenbl", new Object[0]));
            return this;
        }

        public RequestBuilder checkUA() {
            this.b.add(new a("ckua", new Object[0]));
            return this;
        }

        public RequestBuilder vpn() {
            this.b.add(new a("vnmt", new Object[0]));
            return this;
        }

        public RequestBuilder debugable() {
            this.b.add(new a("degb", new Object[0]));
            return this;
        }

        public RequestBuilder isWifiProxy() {
            this.b.add(new a("iwpxy", new Object[0]));
            return this;
        }

        public RequestBuilder getFlavor() {
            this.b.add(new a("gflv", new Object[0]));
            return this;
        }

        public RequestBuilder getBaseband() {
            this.b.add(new a("gbsbd", new Object[0]));
            return this;
        }

        public RequestBuilder getBoardFromSysProperty() {
            this.b.add(new a("gbfspy", new Object[0]));
            return this;
        }

        public RequestBuilder getBoardPlatform() {
            this.b.add(new a("gbplfo", new Object[0]));
            return this;
        }

        public RequestBuilder getDataNtType() {
            this.b.add(new a("gdntp", new Object[0]));
            return this;
        }

        public RequestBuilder getDataNtTypeStrict() {
            this.b.add(new a("gdntpstr", new Object[0]));
            return this;
        }

        public RequestBuilder queryIntentServices(Intent intent, int i) {
            this.b.add(new a("qritsvc", new Object[]{intent, Integer.valueOf(i)}));
            return this;
        }

        public RequestBuilder resolveActivity(Intent intent, int i) {
            this.b.add(new a("rsaciy", new Object[]{intent, Integer.valueOf(i)}));
            return this;
        }

        public RequestBuilder getPInfo(String str, int i) {
            this.b.add(new a("gpgif", new Object[]{str, Integer.valueOf(i)}));
            return this;
        }

        public RequestBuilder getPInfoForce(boolean z, String str, int i) {
            this.b.add(new a("gpgiffcin", new Object[]{Boolean.valueOf(z), str, Integer.valueOf(i)}));
            return this;
        }

        public RequestBuilder getPInfoStrategy(int i, String str, int i2) {
            this.b.add(new a("gpgifstrg", new Object[]{Integer.valueOf(i), str, Integer.valueOf(i2)}));
            return this;
        }

        public RequestBuilder getIPAddress() {
            this.b.add(new a("giads", new Object[0]));
            return this;
        }

        public RequestBuilder getIPAddressStrict() {
            this.b.add(new a("giadsstr", new Object[0]));
            return this;
        }

        public RequestBuilder getDeviceData() {
            this.b.add(new a("gdvda", new Object[0]));
            return this;
        }

        public RequestBuilder getDeviceDataNotAES() {
            this.b.add(new a("gdvdtnas", new Object[0]));
            return this;
        }

        public RequestBuilder getAppLastUpdateTime() {
            this.b.add(new a("galtut", new Object[0]));
            return this;
        }

        public RequestBuilder getDeviceName() {
            this.b.add(new a("gdvme", new Object[0]));
            return this;
        }

        public RequestBuilder getCgroup() {
            this.b.add(new a("gcrup", new Object[0]));
            return this;
        }

        public RequestBuilder getCInfo() {
            this.b.add(new a("gcifm", new Object[0]));
            return this;
        }

        public RequestBuilder getOD() {
            this.b.add(new a("godm", new Object[0]));
            return this;
        }

        public RequestBuilder getODH() {
            this.b.add(new a("godhm", new Object[0]));
            return this;
        }

        public RequestBuilder getALLD() {
            this.b.add(new a("galdm", new Object[0]));
            return this;
        }

        public RequestBuilder getAInfo() {
            this.b.add(new a("gtaif", new Object[0]));
            return this;
        }

        public RequestBuilder getAInfoForPkg(String str, int i) {
            this.b.add(new a("gtaifprm", new Object[]{str, Integer.valueOf(i)}));
            return this;
        }

        public RequestBuilder getAInfoForPkgForce(boolean z, String str, int i) {
            this.b.add(new a("gtaifprmfce", new Object[]{Boolean.valueOf(z), str, Integer.valueOf(i)}));
            return this;
        }

        public RequestBuilder getDrID() {
            return this;
        }

        public RequestBuilder getBtM() {
            return this;
        }

        public RequestBuilder getUpM() {
            return this;
        }

        public RequestBuilder getBdT() {
            this.b.add(new a("gtbdt", new Object[0]));
            return this;
        }

        public RequestBuilder getScreenInch() {
            this.b.add(new a("gtscnin", new Object[0]));
            return this;
        }

        public RequestBuilder getScreenPpi() {
            this.b.add(new a("gtscnppi", new Object[0]));
            return this;
        }

        public RequestBuilder isHmOs() {
            this.b.add(new a("ishmos", new Object[0]));
            return this;
        }

        public RequestBuilder getHmOsVer() {
            this.b.add(new a("gthmosv", new Object[0]));
            return this;
        }

        public RequestBuilder getHmOsDetailedVer() {
            this.b.add(new a("gthmosdtlv", new Object[0]));
            return this;
        }

        public RequestBuilder getHmPMState() {
            this.b.add(new a("gthmpmst", new Object[0]));
            return this;
        }

        public RequestBuilder getHmEPMState() {
            this.b.add(new a("gthmepmst", new Object[0]));
            return this;
        }

        public RequestBuilder getInnerAppLanguage() {
            this.b.add(new a("gtinnerlangmt", new Object[0]));
            return this;
        }

        public RequestBuilder getGrammaticalGender() {
            this.b.add(new a("gtgramgendt", new Object[0]));
            return this;
        }

        public RequestBuilder getPosCommForce(int i, int i2, boolean z, boolean z2) {
            this.b.add(new a("gtelcmefce", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z), Boolean.valueOf(z2)}));
            return this;
        }

        public RequestBuilder getMwfo() {
            this.b.add(new a("gtmwfo", new Object[0]));
            return this;
        }

        public RequestBuilder getMwfoForce(boolean z) {
            this.b.add(new a("wmcwifce", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getMwlfo() {
            this.b.add(new a("gtaifok", new Object[0]));
            return this;
        }

        public RequestBuilder getMcdi() {
            this.b.add(new a("gtmcdi", new Object[0]));
            return this;
        }

        public RequestBuilder getMcdiForce(boolean z) {
            this.b.add(new a("gtmcdifce", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getMbcdi() {
            this.b.add(new a("gtmbcdi", new Object[0]));
            return this;
        }

        public RequestBuilder getMbcdiForce(boolean z) {
            this.b.add(new a("gtmbcdifce", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder isMwpy() {
            this.b.add(new a("miwpy", new Object[0]));
            return this;
        }

        public RequestBuilder getMnbclfo() {
            this.b.add(new a("gtmnbclfo", new Object[0]));
            return this;
        }

        public RequestBuilder getMpfo(String str, int i) {
            this.b.add(new a("gmpfo", new Object[]{str, Integer.valueOf(i)}));
            return this;
        }

        public RequestBuilder getMpfof(boolean z, String str, int i) {
            this.b.add(new a("gmpfofce", new Object[]{Boolean.valueOf(z), str, Integer.valueOf(i)}));
            return this;
        }

        public RequestBuilder getMpfos(int i, String str, int i2) {
            this.b.add(new a("getMpfos", new Object[]{Integer.valueOf(i), str, Integer.valueOf(i2)}));
            return this;
        }

        public RequestBuilder checkDebbing() {
            this.b.add(new a("ctedebbing", new Object[0]));
            return this;
        }

        public RequestBuilder getACIfo() {
            this.b.add(new a("gteacifo", new Object[0]));
            return this;
        }

        public RequestBuilder getDM(boolean z) {
            this.b.add(new a("gtdm", new Object[]{Boolean.valueOf(z)}));
            return this;
        }

        public RequestBuilder getLATime(String str) {
            this.b.add(new a("gtlstactme", new Object[]{str}));
            return this;
        }
    }

    public static class DHResponse {
        private ArrayList<HashMap<String, String>> C;
        private String D;
        private Object F;
        private ArrayList<HashMap<String, Object>> G;
        private String H;
        private HashMap<String, Object> I;
        private HashMap<String, Object> K;
        private ArrayList<ArrayList<String>> L;
        private String M;
        private HashMap<String, HashMap<String, Long>> N;
        private HashMap<String, Long> O;
        private String P;
        private String Q;
        private boolean R;
        private boolean S;
        private boolean T;
        private boolean U;
        private boolean V;
        private boolean W;
        private boolean X;
        private boolean Y;
        private String Z;
        private boolean a;
        private String aA;
        private long aB;
        private double aC;
        private int aD;
        private boolean aE;
        private String aF;
        private String aG;
        private int aH;
        private int aI;
        private String aJ;
        private int aK;
        private HashMap<String, Object> aM;
        private ArrayList<HashMap<String, Object>> aO;
        private String aP;
        private String aR;
        private boolean aT;
        private ArrayList<HashMap<String, Object>> aU;
        private boolean aY;
        private ArrayList<HashMap<String, Object>> aZ;
        private String aa;
        private String ab;
        private String ac;
        private int ad;
        private int ae;
        private String ak;
        private String al;
        private String am;
        private String an;
        private long ao;
        private String ap;
        private String aq;
        private String ar;
        private String as;
        private String at;
        private HashMap<String, Object> au;
        private ApplicationInfo av;
        private String ay;
        private String az;
        private String b;
        private String ba;
        private String d;
        private String g;
        private String h;
        private String k;
        private String n;
        private String p;
        private String q;
        private boolean s;
        private String u;
        private String v;
        private String w;
        private String y;
        private LinkedList<String> c = new LinkedList<>();
        private LinkedList<String> e = new LinkedList<>();
        private LinkedList<String> f = new LinkedList<>();
        private LinkedList<String> i = new LinkedList<>();
        private LinkedList<String> j = new LinkedList<>();
        private LinkedList<String> l = new LinkedList<>();
        private LinkedList<String> m = new LinkedList<>();
        private LinkedList<String> o = new LinkedList<>();
        private LinkedList<String> r = new LinkedList<>();
        private LinkedList<Boolean> t = new LinkedList<>();
        private LinkedList<String> x = new LinkedList<>();
        private LinkedList<String> z = new LinkedList<>();
        private LinkedList<ArrayList<HashMap<String, String>>> A = new LinkedList<>();
        private LinkedList<ArrayList<HashMap<String, String>>> B = new LinkedList<>();
        private LinkedList<Location> E = new LinkedList<>();
        private LinkedList<Boolean> J = new LinkedList<>();
        private LinkedList<List<ResolveInfo>> af = new LinkedList<>();
        private LinkedList<ResolveInfo> ag = new LinkedList<>();
        private LinkedList<PackageInfo> ah = new LinkedList<>();
        private LinkedList<PackageInfo> ai = new LinkedList<>();
        private LinkedList<PackageInfo> aj = new LinkedList<>();
        private LinkedList<ApplicationInfo> aw = new LinkedList<>();
        private LinkedList<ApplicationInfo> ax = new LinkedList<>();
        private LinkedList<List> aL = new LinkedList<>();
        private LinkedList<HashMap<String, Object>> aN = new LinkedList<>();
        private LinkedList<String> aQ = new LinkedList<>();
        private LinkedList<String> aS = new LinkedList<>();
        private LinkedList<Object> aV = new LinkedList<>();
        private LinkedList<Object> aW = new LinkedList<>();
        private LinkedList<Object> aX = new LinkedList<>();
        private LinkedList<Long> bb = new LinkedList<>();

        void a(String str, Object obj) throws Throwable {
            a(str, obj, false);
        }

        void a(String str, Object obj, boolean z) throws Throwable {
            if ("gmpfo".equals(str)) {
                LinkedList<Object> linkedList = this.aV;
                if (z) {
                    obj = null;
                }
                linkedList.add(obj);
                return;
            }
            if ("gmpfofce".equals(str)) {
                LinkedList<Object> linkedList2 = this.aW;
                if (z) {
                    obj = null;
                }
                linkedList2.add(obj);
                return;
            }
            if ("getMpfos".equals(str)) {
                LinkedList<Object> linkedList3 = this.aX;
                if (z) {
                    obj = null;
                }
                linkedList3.add(obj);
                return;
            }
            boolean z2 = false;
            z2 = false;
            if ("cird".equals(str)) {
                this.a = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("gsimt".equals(str)) {
                this.b = z ? null : (String) obj;
                return;
            }
            if ("gsimtfce".equals(str)) {
                this.c.add(z ? null : (String) obj);
                return;
            }
            if ("gbsi".equals(str)) {
                this.d = z ? null : (String) obj;
                return;
            }
            if ("gbsifce".equals(str)) {
                this.e.add(z ? null : (String) obj);
                return;
            }
            if ("gstmpts".equals(str)) {
                this.f.add(z ? null : (String) obj);
                return;
            }
            if ("gscsz".equals(str)) {
                this.g = z ? null : (String) obj;
                return;
            }
            if ("gcrie".equals(str)) {
                this.h = z ? null : (String) obj;
                return;
            }
            if ("gcriefce".equals(str)) {
                this.i.add(z ? null : (String) obj);
                return;
            }
            if ("gcriefcestr".equals(str)) {
                this.j.add(z ? null : (String) obj);
                return;
            }
            if ("gcrnm".equals(str)) {
                this.k = z ? null : (String) obj;
                return;
            }
            if ("gcrnmfce".equals(str)) {
                this.l.add(z ? null : (String) obj);
                return;
            }
            if ("gcrnmfcestr".equals(str)) {
                this.m.add(z ? null : (String) obj);
                return;
            }
            if ("gsnmd".equals(str)) {
                this.n = z ? null : (String) obj;
                return;
            }
            if ("gsnmdfp".equals(str)) {
                this.o.add(z ? null : (String) obj);
                return;
            }
            if ("gneyp".equals(str)) {
                this.p = z ? null : (String) obj;
                return;
            }
            if ("gneypnw".equals(str)) {
                this.q = z ? null : (String) obj;
                return;
            }
            if ("gneypfce".equals(str)) {
                this.r.add(z ? null : (String) obj);
                return;
            }
            if ("cknavbl".equals(str)) {
                this.s = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("cknavblfc".equals(str)) {
                this.t.add(Boolean.valueOf(z ? false : ((Boolean) obj).booleanValue()));
                return;
            }
            if ("gnktpfs".equals(str)) {
                this.u = z ? null : (String) obj;
                return;
            }
            if ("gdtlnktpfs".equals(str)) {
                this.v = z ? null : (String) obj;
                return;
            }
            if ("gdvk".equals(str)) {
                this.w = z ? null : (String) obj;
                return;
            }
            if ("gdvkfc".equals(str)) {
                this.x.add(z ? null : (String) obj);
                return;
            }
            if ("gpnmmt".equals(str)) {
                this.y = z ? null : (String) obj;
                return;
            }
            if ("gpnmfp".equals(str)) {
                this.z.add(z ? null : (String) obj);
                return;
            }
            if ("gia".equals(str)) {
                this.A.add(z ? null : (ArrayList) obj);
                return;
            }
            if ("giafce".equals(str)) {
                this.B.add(z ? null : (ArrayList) obj);
                return;
            }
            if ("gsl".equals(str)) {
                this.C = z ? null : (ArrayList) obj;
                return;
            }
            if ("gavti".equals(str)) {
                this.D = z ? null : (String) obj;
                return;
            }
            if ("glctn".equals(str)) {
                this.E.add(z ? null : (Location) obj);
                return;
            }
            if ("gtecloc".equals(str)) {
                if (z) {
                    obj = null;
                }
                this.F = obj;
                return;
            }
            if ("gnbclin".equals(str)) {
                this.G = z ? null : (ArrayList) obj;
                return;
            }
            if ("gdvtp".equals(str)) {
                this.H = z ? null : (String) obj;
                return;
            }
            if ("wmcwi".equals(str)) {
                this.I = z ? null : (HashMap) obj;
                return;
            }
            if ("ipgist".equals(str)) {
                this.J.add(Boolean.valueOf(z ? false : ((Boolean) obj).booleanValue()));
                return;
            }
            if ("gcuin".equals(str)) {
                this.K = z ? null : (HashMap) obj;
                return;
            }
            if ("gtydvin".equals(str)) {
                this.L = z ? null : (ArrayList) obj;
                return;
            }
            if ("gqmkn".equals(str)) {
                this.M = z ? null : (String) obj;
                return;
            }
            if ("gszin".equals(str)) {
                this.N = z ? null : (HashMap) obj;
                return;
            }
            if ("gmrin".equals(str)) {
                this.O = z ? null : (HashMap) obj;
                return;
            }
            if ("gmivsn".equals(str)) {
                this.P = z ? null : (String) obj;
                return;
            }
            if ("gmivsnfly".equals(str)) {
                this.Q = z ? null : (String) obj;
                return;
            }
            if ("cx".equals(str)) {
                this.R = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("ckpd".equals(str)) {
                this.S = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("ubenbl".equals(str)) {
                this.T = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("dvenbl".equals(str)) {
                this.U = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("ckua".equals(str)) {
                this.V = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("vnmt".equals(str)) {
                this.W = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("degb".equals(str)) {
                this.X = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("iwpxy".equals(str)) {
                this.Y = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("gflv".equals(str)) {
                this.Z = z ? null : (String) obj;
                return;
            }
            if ("gbsbd".equals(str)) {
                this.aa = z ? null : (String) obj;
                return;
            }
            if ("gbfspy".equals(str)) {
                this.ab = z ? null : (String) obj;
                return;
            }
            if ("gbplfo".equals(str)) {
                this.ac = z ? null : (String) obj;
                return;
            }
            if ("gdntp".equals(str)) {
                this.ad = z ? 0 : ((Integer) obj).intValue();
                return;
            }
            if ("gdntpstr".equals(str)) {
                this.ae = z ? 0 : ((Integer) obj).intValue();
                return;
            }
            if ("qritsvc".equals(str)) {
                this.af.add(z ? null : (List) obj);
                return;
            }
            if ("rsaciy".equals(str)) {
                this.ag.add(z ? null : (ResolveInfo) obj);
                return;
            }
            if ("gpgif".equals(str)) {
                this.ah.add(z ? null : (PackageInfo) obj);
                return;
            }
            if ("gpgiffcin".equals(str)) {
                this.ai.add(z ? null : (PackageInfo) obj);
                return;
            }
            if ("gpgifstrg".equals(str)) {
                this.aj.add(z ? null : (PackageInfo) obj);
                return;
            }
            if ("giads".equals(str)) {
                this.ak = z ? null : (String) obj;
                return;
            }
            if ("giadsstr".equals(str)) {
                this.al = z ? null : (String) obj;
                return;
            }
            if ("gdvda".equals(str)) {
                this.am = z ? null : (String) obj;
                return;
            }
            if ("gdvdtnas".equals(str)) {
                this.an = z ? null : (String) obj;
                return;
            }
            if ("galtut".equals(str)) {
                this.ao = z ? 0L : ((Long) obj).longValue();
                return;
            }
            if ("gdvme".equals(str)) {
                this.ap = z ? null : (String) obj;
                return;
            }
            if ("gcrup".equals(str)) {
                this.aq = z ? null : (String) obj;
                return;
            }
            if ("gcifm".equals(str)) {
                this.ar = z ? null : (String) obj;
                return;
            }
            if ("godm".equals(str)) {
                this.as = z ? null : (String) obj;
                return;
            }
            if ("godhm".equals(str)) {
                this.at = z ? null : (String) obj;
                return;
            }
            if ("galdm".equals(str)) {
                this.au = z ? null : (HashMap) obj;
                return;
            }
            if ("gtaif".equals(str)) {
                this.av = z ? null : (ApplicationInfo) obj;
                return;
            }
            if ("gtaifprm".equals(str)) {
                this.aw.add(z ? null : (ApplicationInfo) obj);
                return;
            }
            if ("gtaifprmfce".equals(str)) {
                this.ax.add(z ? null : (ApplicationInfo) obj);
                return;
            }
            if ("gtbdt".equals(str)) {
                this.aB = z ? 0L : ((Long) obj).longValue();
                return;
            }
            if ("gtscnin".equals(str)) {
                this.aC = z ? 0.0d : ((Double) obj).doubleValue();
                return;
            }
            if ("gtscnppi".equals(str)) {
                this.aD = z ? 0 : ((Integer) obj).intValue();
                return;
            }
            if ("ishmos".equals(str)) {
                this.aE = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("gthmosv".equals(str)) {
                this.aF = z ? null : (String) obj;
                return;
            }
            if ("gthmosdtlv".equals(str)) {
                this.aG = z ? null : (String) obj;
                return;
            }
            if ("gthmpmst".equals(str)) {
                this.aH = z ? -1 : ((Integer) obj).intValue();
                return;
            }
            if ("gthmepmst".equals(str)) {
                this.aI = z ? -1 : ((Integer) obj).intValue();
                return;
            }
            if ("gtinnerlangmt".equals(str)) {
                this.aJ = z ? null : (String) obj;
                return;
            }
            if ("gtgramgendt".equals(str)) {
                this.aK = z ? 0 : ((Integer) obj).intValue();
                return;
            }
            if ("gtelcmefce".equals(str)) {
                this.aL.add(z ? null : (List) obj);
                return;
            }
            if ("gtmwfo".equals(str)) {
                this.aM = z ? null : (HashMap) obj;
                return;
            }
            if ("wmcwifce".equals(str)) {
                this.aN.add(z ? null : (HashMap) obj);
                return;
            }
            if ("gtaifok".equals(str)) {
                this.aO = z ? null : (ArrayList) obj;
                return;
            }
            if ("gtmcdi".equals(str)) {
                this.aP = z ? null : (String) obj;
                return;
            }
            if ("gtmcdifce".equals(str)) {
                this.aQ.add(z ? null : (String) obj);
                return;
            }
            if ("gtmbcdi".equals(str)) {
                this.aR = z ? null : (String) obj;
                return;
            }
            if ("gtmbcdifce".equals(str)) {
                this.aS.add(z ? null : (String) obj);
                return;
            }
            if ("miwpy".equals(str)) {
                this.aT = z ? false : ((Boolean) obj).booleanValue();
                return;
            }
            if ("gtmnbclfo".equals(str)) {
                this.aU = z ? null : (ArrayList) obj;
                return;
            }
            if ("ctedebbing".equals(str)) {
                if (!z && ((Boolean) obj).booleanValue()) {
                    z2 = true;
                }
                this.aY = z2;
                return;
            }
            if ("gteacifo".equals(str)) {
                this.aZ = z ? null : (ArrayList) obj;
            } else if ("gtdm".equals(str)) {
                this.ba = z ? null : (String) obj;
            } else {
                if ("gtlstactme".equals(str)) {
                    this.bb.add(Long.valueOf(z ? -1L : ((Long) obj).longValue()));
                    return;
                }
                throw new Throwable("Unknown name to set: " + str + ", value: " + obj);
            }
        }

        public boolean isRooted() {
            return this.a;
        }

        public String getSSID() {
            return this.b;
        }

        public String getSSIDForce(int... iArr) {
            return (String) a(this.c, (Object) null, iArr);
        }

        public String getBssid() {
            return this.d;
        }

        public String getBssidForce(int... iArr) {
            return (String) a(this.e, (Object) null, iArr);
        }

        public String getDeviceId() {
            return null;
        }

        public String getIMEI() {
            return null;
        }

        public String[] queryIMEI() {
            return null;
        }

        public String getSystemProperties(int... iArr) {
            return (String) a(this.f, (Object) null, iArr);
        }

        public String getSerialno() {
            return null;
        }

        public String getScreenSize() {
            return this.g;
        }

        public String getCarrier() {
            return this.h;
        }

        public String getCarrierForce(int... iArr) {
            return (String) a(this.i, "-1", iArr);
        }

        public String getCarrierStrict(int... iArr) {
            return (String) a(this.j, "-1", iArr);
        }

        public String getCarrierName() {
            return this.k;
        }

        public String getCarrierNameForce(int... iArr) {
            return (String) a(this.l, (Object) null, iArr);
        }

        public String getCarrierNameStrict(int... iArr) {
            return (String) a(this.m, (Object) null, iArr);
        }

        public String getSimSerialNumber() {
            return null;
        }

        public String getSignMD5() {
            return this.n;
        }

        public String getSignMD5ForPkg(int... iArr) {
            return (String) a(this.o, (Object) null, iArr);
        }

        public String getNetworkType() {
            return this.p;
        }

        public String getNetworkTypeNew() {
            return this.q;
        }

        public String getNetworkTypeForce(int... iArr) {
            return (String) a(this.r, m.a("004g@fm2gh"), iArr);
        }

        public boolean checkNetworkAvailable() {
            return this.s;
        }

        public boolean checkNetworkAvailableForce(int... iArr) {
            return ((Boolean) a(this.t, (Object) null, iArr)).booleanValue();
        }

        public String getNetworkTypeForStatic() {
            return this.u;
        }

        public String getDetailNetworkTypeForStatic() {
            return this.v;
        }

        public String getDeviceKey() {
            return this.w;
        }

        public String getDeviceKeyFromCache(int... iArr) {
            return (String) a(this.x, (Object) null, iArr);
        }

        public String getAppName() {
            return this.y;
        }

        public String getAppNameForPkg(int... iArr) {
            return (String) a(this.z, (Object) null, iArr);
        }

        public ArrayList<HashMap<String, String>> getIA(int... iArr) {
            return (ArrayList) a(this.A, new ArrayList(), iArr);
        }

        public ArrayList<HashMap<String, String>> getIAForce(int... iArr) {
            return (ArrayList) a(this.B, new ArrayList(), iArr);
        }

        public ArrayList<HashMap<String, String>> getSA() {
            return this.C;
        }

        public boolean getSdcardState() {
            return false;
        }

        public String getAdvertisingID() {
            return this.D;
        }

        public String getIMSI() {
            return null;
        }

        public String[] queryIMSI() {
            return null;
        }

        public Location getLocation(int... iArr) {
            return (Location) a(this.E, (Object) null, iArr);
        }

        public Object getCLoc() {
            return this.F;
        }

        public ArrayList<HashMap<String, Object>> getNeighboringCellInfo() {
            return this.G;
        }

        public String getDeviceType() {
            return this.H;
        }

        public Activity getTopActivity() {
            return null;
        }

        public HashMap<String, Object> getCurrentWifiInfo() {
            return this.I;
        }

        public boolean isPackageInstalled(int... iArr) {
            return ((Boolean) a((LinkedList<boolean>) this.J, false, iArr)).booleanValue();
        }

        public HashMap<String, Object> getCPUInfo() {
            return this.K;
        }

        public ArrayList<ArrayList<String>> getTTYDriversInfo() {
            return this.L;
        }

        public String getQemuKernel() {
            return this.M;
        }

        public HashMap<String, HashMap<String, Long>> getSizeInfo() {
            return this.N;
        }

        public HashMap<String, Long> getMemoryInfo() {
            return this.O;
        }

        public String getMIUIVersion() {
            return this.P;
        }

        public String getMIUIVersionForFly() {
            return this.Q;
        }

        public boolean cx() {
            return this.R;
        }

        public boolean checkPad() {
            return this.S;
        }

        public boolean usbEnable() {
            return this.T;
        }

        public boolean devEnable() {
            return this.U;
        }

        public boolean checkUA() {
            return this.V;
        }

        public boolean vpn() {
            return this.W;
        }

        public boolean debugable() {
            return this.X;
        }

        public boolean isWifiProxy() {
            return this.Y;
        }

        public String getFlavor() {
            return this.Z;
        }

        public String getBaseband() {
            return this.aa;
        }

        public String getBoardFromSysProperty() {
            return this.ab;
        }

        public String getBoardPlatform() {
            return this.ac;
        }

        public int getDataNtType() {
            return this.ad;
        }

        public int getDataNtTypeStrict() {
            return this.ae;
        }

        public List<ResolveInfo> queryIntentServices(int... iArr) {
            return (List) a(this.af, (Object) null, iArr);
        }

        public ResolveInfo resolveActivity(int... iArr) {
            return (ResolveInfo) a(this.ag, (Object) null, iArr);
        }

        public PackageInfo getPInfo(int... iArr) {
            return (PackageInfo) a(this.ah, (Object) null, iArr);
        }

        public PackageInfo getPInfoForce(int... iArr) {
            return (PackageInfo) a(this.ai, (Object) null, iArr);
        }

        public PackageInfo getPInfoStrategy(int... iArr) {
            return (PackageInfo) a(this.aj, (Object) null, iArr);
        }

        public String getIPAddress() {
            return this.ak;
        }

        public String getIPAddressStrict() {
            return this.al;
        }

        public String getDeviceData() {
            return this.am;
        }

        public String getDeviceDataNotAES() {
            return this.an;
        }

        public long getAppLastUpdateTime() {
            return this.ao;
        }

        public String getDeviceName() {
            return this.ap;
        }

        public String getCgroup() {
            return this.aq;
        }

        public String getCInfo() {
            return this.ar;
        }

        public String getOD() {
            return this.as;
        }

        public String getODH() {
            return this.at;
        }

        public HashMap<String, Object> getALLD() {
            return this.au;
        }

        public ApplicationInfo getAInfo() {
            return this.av;
        }

        public ApplicationInfo getAInfoForPkg(int... iArr) {
            return (ApplicationInfo) a(this.aw, (Object) null, iArr);
        }

        public ApplicationInfo getAInfoForPkgForce(int... iArr) {
            return (ApplicationInfo) a(this.ax, (Object) null, iArr);
        }

        public String getDrID() {
            return this.ay;
        }

        public String getBtM() {
            return this.az;
        }

        public String getUpM() {
            return this.aA;
        }

        public long getBdT() {
            return this.aB;
        }

        public double getScreenInch() {
            return this.aC;
        }

        public int getScreenPpi() {
            return this.aD;
        }

        public boolean isHmOs() {
            return this.aE;
        }

        public String getHmOsVer() {
            return this.aF;
        }

        public String getHmOsDetailedVer() {
            return this.aG;
        }

        public int getHmPMState() {
            return this.aH;
        }

        public int getHmEPMState() {
            return this.aI;
        }

        public String getInnerAppLanguage() {
            return this.aJ;
        }

        public int getGrammaticalGender() {
            return this.aK;
        }

        public List getPosCommForce(int... iArr) {
            return (List) a(this.aL, (Object) null, iArr);
        }

        public HashMap<String, Object> getMwfo() {
            return this.aM;
        }

        public HashMap<String, Object> getMwfoForce(int... iArr) {
            return (HashMap) a(this.aN, (Object) null, iArr);
        }

        public ArrayList<HashMap<String, Object>> getMwlfo() {
            return this.aO;
        }

        public String getMcdi() {
            return this.aP;
        }

        public String getMcdiForce(int... iArr) {
            return (String) a(this.aQ, (Object) null, iArr);
        }

        public String getMbcdi() {
            return this.aR;
        }

        public String getMbcdiForce(int... iArr) {
            return (String) a(this.aS, (Object) null, iArr);
        }

        public boolean isMwpy() {
            return this.aT;
        }

        public ArrayList<HashMap<String, Object>> getMnbclfo() {
            return this.aU;
        }

        public Object getMpfo(int... iArr) {
            return a(this.aV, (Object) null, iArr);
        }

        public Object getMpfof(int... iArr) {
            return a(this.aW, (Object) null, iArr);
        }

        public Object getMpfos(int... iArr) {
            return a(this.aX, (Object) null, iArr);
        }

        public boolean checkDebbing() {
            return this.aY;
        }

        public ArrayList<HashMap<String, Object>> getACIfo() {
            return this.aZ;
        }

        public String getDM() {
            return this.ba;
        }

        public long getLATime(int... iArr) {
            return ((Long) a((LinkedList<long>) this.bb, -1L, iArr)).longValue();
        }

        private static <T> T a(LinkedList<T> linkedList, T t, int... iArr) {
            if (linkedList != null) {
                try {
                    if (iArr.length == 0) {
                        return linkedList.get(0);
                    }
                    if (iArr[0] < linkedList.size()) {
                        return linkedList.get(iArr[0]);
                    }
                    FlyLog.getInstance().w("WARNING: " + iArr[0] + " out of bound, size: " + linkedList.size());
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                }
            }
            return t;
        }
    }

    public static final class SyncMtd {
        public static <T> T invokeInstanceMethod(Object obj, String str, Object... objArr) {
            return (T) ReflectHelper.invokeInstanceMethodNoThrow(obj, str, null, objArr);
        }

        public static <T> T invokeInstanceMethod(Object obj, String str, Object[] objArr, Class<?>[] clsArr) {
            try {
                return (T) ReflectHelper.invokeInstanceMethod(obj, str, objArr, clsArr);
            } catch (Throwable th) {
                if (!(th instanceof InvocationTargetException)) {
                    if (th instanceof PackageManager.NameNotFoundException) {
                        FlyLog.getInstance().d("Exception: " + th.getClass().getName() + ": " + th.getMessage(), new Object[0]);
                        return null;
                    }
                    FlyLog.getInstance().d(th);
                    return null;
                }
                String name = th.getClass().getName();
                String message = th.getMessage();
                Throwable cause = th.getCause();
                if (cause != null) {
                    name = cause.getClass().getName();
                    message = cause.getMessage();
                }
                FlyLog.getInstance().d("Exception: " + name + ": " + message, new Object[0]);
                return null;
            }
        }

        public static Object getSystemServiceSafe(String str) {
            return C0041r.d(str);
        }

        public static String getSandboxPath() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().X();
        }

        public static boolean checkPermission(String str) {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().e(str);
        }

        public static boolean isInMainProcess() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().ad();
        }

        public static String getPackageName() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().Z();
        }

        public static String getCurrentProcessName() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().ae();
        }

        public static int getPlatformCode() {
            return 1;
        }

        public static String getAppVersionName() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().ac();
        }

        public static String Base64AES(String str, String str2) {
            return Data.Base64AES(str, str2);
        }

        public static Object currentActivityThread() {
            return C0041r.b();
        }

        public static Context getApplication() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().ag();
        }

        public static int getOSVersionInt() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().w();
        }

        public static String getOSVersionName() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().y();
        }

        public static String getOSLanguage() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().A();
        }

        public static String getAppLanguage() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().H();
        }

        public static String getOSCountry() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().B();
        }

        public static String getTimezone() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().N();
        }

        public static void hideSoftInput(View view) {
            C0041r.a(view);
        }

        public static void showSoftInput(View view) {
            C0041r.b(view);
        }

        public static String getModel() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().m();
        }

        public static String getManufacturer() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().o();
        }

        public static String getBrand() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().q();
        }

        public static int getAppVersion() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().ab();
        }

        public static String getSystemProperties(String str) {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().a(str);
        }

        public static boolean isGooglePlayServicesAvailable() {
            return false;
        }

        public static boolean isAut() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().aF();
        }

        public static int getOSVersionIntForFly() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().x();
        }

        public static String getOSVersionNameForFly() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().z();
        }

        public static String getManufacturerForFly() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().p();
        }

        public static String getBrandForFly() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().r();
        }

        public static String getModelForFly() {
            return cn.fly.tools.b.c.a(FlySDK.getContext()).d().n();
        }
    }
}