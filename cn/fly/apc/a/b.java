package cn.fly.apc.a;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.PointerIconCompat;
import cn.fly.apc.APCException;
import cn.fly.apc.b;
import cn.fly.commons.m;
import cn.fly.tools.utils.DH;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class b implements ServiceConnection {
    private static final String[] a = {"cn.fly.FlyACService", m.a("020eFfmfhfnfhfmhhfnjefmhhhfgfgn?h flfffk$eh")};
    private static final ThreadPoolExecutor b = new ThreadPoolExecutor(8, 8, 60, TimeUnit.SECONDS, new LinkedBlockingDeque());
    private final ConcurrentHashMap<String, d> c = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, byte[]> d = new ConcurrentHashMap<>();

    static {
        try {
            b.allowCoreThreadTimeOut(true);
        } catch (Throwable th) {
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            String packageName = componentName.getPackageName();
            f.a().b("[AIDLMessager][onServiceConnected] pkg: %s", packageName);
            this.c.put(packageName, d.a(iBinder));
            byte[] bArrRemove = this.d.remove(packageName);
            if (bArrRemove != null) {
                synchronized (bArrRemove) {
                    bArrRemove.notifyAll();
                }
            }
        } catch (Throwable th) {
            f.a().b("[AIDLMessager][onServiceConnected] exception: %s", th.getMessage());
            f.a().a(th);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        try {
            String packageName = componentName.getPackageName();
            f.a().b("[AIDLMessager][onServiceDisconnected] pkg: %s", packageName);
            this.c.remove(packageName);
        } catch (Throwable th) {
            f.a().a(th);
            f.a().b("[AIDLMessager][onServiceDisconnected] exception: %s", th.getMessage());
        }
    }

    public cn.fly.apc.a a(String str, String str2, cn.fly.apc.a aVar, long j) throws Throwable {
        e eVarTake;
        f.a().b("[SDMG] pkg: %s, businessID: %s, apcMessage: %s, timeout: %s", str, str2, aVar, Long.valueOf(j));
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        try {
            Runnable runnableA = a(str, new e(aVar, str2, j), j, linkedBlockingQueue);
            if (j <= 0) {
                eVarTake = linkedBlockingQueue.take();
            } else {
                e eVarPoll = linkedBlockingQueue.poll(j, TimeUnit.MILLISECONDS);
                if (eVarPoll == null) {
                    b.remove(runnableA);
                }
                eVarTake = eVarPoll;
            }
            if (eVarTake != null) {
                if (eVarTake.a != null) {
                    return eVarTake.a;
                }
                if (eVarTake.d != null) {
                    throw eVarTake.d;
                }
            }
            throw new APCException("[SDMG] callback is null or timeout.");
        } catch (Throwable th) {
            f.a().b("[SDMG] exception: %s", th.getMessage());
            throw new APCException(th);
        }
    }

    private Runnable a(final String str, final e eVar, final long j, final BlockingQueue<e> blockingQueue) {
        Runnable runnable = new Runnable() { // from class: cn.fly.apc.a.b.1
            @Override // java.lang.Runnable
            public void run() {
                e eVar2;
                try {
                    try {
                        b.this.a(str, eVar);
                        blockingQueue.offer(b.this.a(str, eVar, j));
                    } catch (Throwable th) {
                        e eVar3 = null;
                        try {
                            eVar2 = new e(null, eVar.b, j);
                        } catch (Throwable th2) {
                            th = th2;
                        }
                        try {
                            if (th instanceof APCException) {
                                eVar2.d = th;
                            }
                            blockingQueue.offer(eVar2);
                        } catch (Throwable th3) {
                            th = th3;
                            eVar3 = eVar2;
                            blockingQueue.offer(eVar3);
                            throw th;
                        }
                    }
                } catch (Throwable th4) {
                    f.a().a(th4);
                }
            }
        };
        b.execute(runnable);
        return runnable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public e a(String str, e eVar, long j) throws Throwable {
        byte[] bArr;
        boolean zA;
        f.a().b("[SDMG] pkg: %s, InnerMessage: %s, timeout: %s", str, eVar, Long.valueOf(j));
        d dVar = this.c.get(str);
        if (dVar != null) {
            try {
                if (dVar.isBinderAlive()) {
                    f.a().b("[SDMG] serverBinder %s is alive.", str);
                    return dVar.a(eVar);
                }
            } catch (RemoteException e) {
                f.a().b("[SDMG] serverBinder send error: %s %s", str, e.getMessage());
                f.a().a(e);
            }
        }
        Intent intent = new Intent();
        boolean zBindService = false;
        for (String str2 : a) {
            intent.setClassName(str, str2);
            try {
                f.a().a("[SDMG] chk alv: " + str, new Object[0]);
                b.InterfaceC0001b interfaceC0001bB = c.a().b();
                if (interfaceC0001bB != null) {
                    zA = interfaceC0001bB.a(str);
                } else {
                    f.a().a("[SDMG] WARNING: mgsRequestListener null, can not chk alv", new Object[0]);
                    zA = false;
                }
                f.a().a("[SDMG] is [" + str + "] alv: " + zA, new Object[0]);
                if (zA) {
                    f.a().a("[SDMG] bnd to " + intent, new Object[0]);
                    if (DH.SyncMtd.getOSVersionIntForFly() >= 34) {
                        zBindService = cn.fly.apc.b.a().bindService(intent, this, InputDeviceCompat.SOURCE_DPAD);
                    } else {
                        zBindService = cn.fly.apc.b.a().bindService(intent, this, 1);
                    }
                } else {
                    f.a().a("[SDMG] can not bnd to " + intent, new Object[0]);
                }
                if (zBindService) {
                    break;
                }
            } catch (Throwable th) {
                throw new APCException(PointerIconCompat.TYPE_HAND, "[SDMG] svc bnd exception: " + th.getMessage());
            }
        }
        if (zBindService) {
            f.a().b("[SDMG] bnd to %s: s", str);
            try {
                byte[] bArr2 = this.d.get(str);
                if (bArr2 != null) {
                    bArr = bArr2;
                } else {
                    byte[] bArr3 = new byte[0];
                    this.d.put(str, bArr3);
                    bArr = bArr3;
                }
                synchronized (bArr) {
                    bArr.wait(j);
                }
                d dVar2 = this.c.get(str);
                f.a().b("[SDMG] svr bndr: %s %s", str, dVar2);
                if (dVar2 == null) {
                    throw new APCException(1001, String.format("svr bndr %s is null or timeout", str));
                }
                try {
                    return dVar2.a(eVar);
                } catch (RemoteException e2) {
                    throw new APCException(1004, String.format("[SDMG] snd to %s RemoteException: %s", str, e2.getMessage()));
                }
            } catch (Throwable th2) {
                f.a().b("[SDMG] snd to %s exception: %s", str, th2.getMessage());
                throw new APCException(th2);
            }
        }
        throw new APCException(PointerIconCompat.TYPE_HELP, String.format("[SDMG] bnd to %s: f", str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, e eVar) {
        cn.fly.apc.a aVar;
        if (eVar != null && (aVar = eVar.a) != null) {
            b.InterfaceC0001b interfaceC0001bB = c.a().b();
            int i = aVar.a;
            f.a().a("APCMessageType: " + i, new Object[0]);
            switch (i) {
                case 1:
                case 2:
                case PointerIconCompat.TYPE_HELP /* 1003 */:
                    f.a().a("No need to call GD.", new Object[0]);
                    break;
                case 1001:
                    f.a().a("Need GD. busType: 1", new Object[0]);
                    if (interfaceC0001bB != null) {
                        interfaceC0001bB.a(1, str);
                        break;
                    }
                    break;
                case 9004:
                    f.a().a("Need GD. busType: 2", new Object[0]);
                    if (interfaceC0001bB != null) {
                        interfaceC0001bB.a(2, str);
                        break;
                    }
                    break;
            }
        }
    }
}