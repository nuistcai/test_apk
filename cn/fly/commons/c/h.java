package cn.fly.commons.c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemClock;
import androidx.core.view.InputDeviceCompat;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public abstract class h {
    protected final Context a;
    protected final String b;
    private boolean c = false;
    private String d = null;
    private int e = 0;

    public static class b {
        String a;
    }

    public h(Context context) {
        this.a = context;
        this.b = context.getPackageName();
    }

    protected Intent a() {
        return null;
    }

    protected b b() {
        return null;
    }

    protected b a(IBinder iBinder) {
        return null;
    }

    protected long c() {
        return (((this.e - 1) * 2) + 2) * 1000;
    }

    protected synchronized void a(String str) {
        if (str != null) {
            if (!Pattern.compile("^[0fF\\-]+").matcher(str).matches()) {
                this.d = str;
            }
        }
    }

    private synchronized void e() {
        if (this.c) {
            return;
        }
        if (a(a()) || this.e >= 4) {
            this.c = true;
        }
    }

    private synchronized boolean a(Intent intent) {
        boolean z;
        z = true;
        this.e++;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        try {
            b bVarB = b();
            if (bVarB == null) {
                bVarB = a(this.a, intent);
            }
            if (bVarB == null) {
                z = false;
            } else {
                this.d = bVarB.a;
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            z = false;
        }
        FlyLog.getInstance().d("oa use time: " + (SystemClock.elapsedRealtime() - jElapsedRealtime), new Object[0]);
        return z;
    }

    public synchronized String d() {
        e();
        return this.d;
    }

    protected String a(String str, IBinder iBinder, String str2, int i, String... strArr) {
        Parcel parcelObtain;
        Parcel parcelObtain2;
        try {
            parcelObtain = Parcel.obtain();
            try {
                parcelObtain2 = Parcel.obtain();
            } catch (Throwable th) {
                th = th;
                parcelObtain2 = null;
            }
        } catch (Throwable th2) {
            th = th2;
            parcelObtain = null;
            parcelObtain2 = null;
        }
        try {
            parcelObtain.writeInterfaceToken(str2);
            if (strArr != null && strArr.length > 0) {
                for (String str3 : strArr) {
                    parcelObtain.writeString(str3);
                }
            }
            iBinder.transact(i, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readString();
        } catch (Throwable th3) {
            th = th3;
            try {
                FlyLog.getInstance().d("getStringValue: " + str + " failed! " + th.getMessage(), new Object[0]);
                if (parcelObtain2 != null) {
                    try {
                        parcelObtain2.recycle();
                    } catch (Throwable th4) {
                        return null;
                    }
                }
                if (parcelObtain != null) {
                    parcelObtain.recycle();
                }
                return null;
            } finally {
                if (parcelObtain2 != null) {
                    try {
                        parcelObtain2.recycle();
                    } catch (Throwable th5) {
                    }
                }
                if (parcelObtain != null) {
                    parcelObtain.recycle();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0073 A[Catch: all -> 0x006f, TRY_LEAVE, TryCatch #6 {all -> 0x006f, blocks: (B:35:0x006b, B:39:0x0073), top: B:46:0x006b }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected int a(String str, IBinder iBinder, String str2, int i) throws Throwable {
        Parcel parcelObtain;
        Parcel parcel;
        Parcel parcelObtain2 = null;
        try {
            parcelObtain = Parcel.obtain();
            try {
                parcelObtain2 = Parcel.obtain();
                parcelObtain.writeInterfaceToken(str2);
                iBinder.transact(i, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                int i2 = parcelObtain2.readInt();
                if (parcelObtain2 != null) {
                    try {
                        parcelObtain2.recycle();
                    } catch (Throwable th) {
                    }
                }
                if (parcelObtain != null) {
                    parcelObtain.recycle();
                }
                return i2;
            } catch (RemoteException e) {
                parcel = parcelObtain2;
                parcelObtain2 = parcelObtain;
                try {
                    FlyLog.getInstance().d("getIntValue: " + str + " failed! (remoteException)", new Object[0]);
                    if (parcel != null) {
                        try {
                            parcel.recycle();
                        } catch (Throwable th2) {
                            return 0;
                        }
                    }
                    if (parcelObtain2 != null) {
                        parcelObtain2.recycle();
                    }
                    return 0;
                } catch (Throwable th3) {
                    th = th3;
                    parcelObtain = parcelObtain2;
                    parcelObtain2 = parcel;
                    if (parcelObtain2 != null) {
                        try {
                            parcelObtain2.recycle();
                        } catch (Throwable th4) {
                            throw th;
                        }
                    }
                    if (parcelObtain != null) {
                        parcelObtain.recycle();
                    }
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                if (parcelObtain2 != null) {
                }
                if (parcelObtain != null) {
                }
                throw th;
            }
        } catch (RemoteException e2) {
            parcel = null;
        } catch (Throwable th6) {
            th = th6;
            parcelObtain = null;
        }
    }

    private b a(Context context, Intent intent) throws Throwable {
        boolean zBindService;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new Throwable("unable to invoke in main thread!");
        }
        a aVar = new a();
        try {
            if (DH.SyncMtd.getOSVersionIntForFly() >= 34) {
                zBindService = context.bindService(intent, aVar, InputDeviceCompat.SOURCE_DPAD);
            } else {
                zBindService = context.bindService(intent, aVar, 1);
            }
            if (intent != null && zBindService) {
                FlyLog.getInstance().d("wte " + c(), new Object[0]);
                IBinder iBinderA = aVar.a(c());
                if (iBinderA != null) {
                    return a(iBinderA);
                }
                throw new Throwable("get binder " + intent.getComponent() + " failed!");
            }
            throw new Throwable("bind service " + (intent == null ? "null" : intent.getComponent()) + " failed!");
        } finally {
            try {
                context.unbindService(aVar);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
    }

    private class a implements ServiceConnection {
        boolean a;
        private final BlockingQueue<IBinder> c;

        private a() {
            this.a = false;
            this.c = new LinkedBlockingQueue();
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.c.put(iBinder);
            } catch (Throwable th) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        public IBinder a(long j) throws InterruptedException {
            if (this.a) {
                throw new IllegalStateException();
            }
            this.a = true;
            BlockingQueue<IBinder> blockingQueue = this.c;
            if (j <= 0) {
                j = 1500;
            }
            return blockingQueue.poll(j, TimeUnit.MILLISECONDS);
        }
    }
}