package cn.fly.commons;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import cn.fly.tools.FlyLog;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public class s extends Binder implements IInterface {
    private CountDownLatch a;
    private volatile String b;
    private volatile boolean c = false;
    private final String d = cn.fly.commons.a.l.a("043d5elegemFi0ej6iKel6f*elekemWdhCelehedgj;gAekeeej<dg7emelNe-ejedemffhigeffgmfe$ehhYgk2ed?fi");

    public s() {
        attachInterface(this, this.d);
    }

    public String a() {
        return this.b;
    }

    public boolean b() {
        return this.c;
    }

    public s a(CountDownLatch countDownLatch) {
        this.a = countDownLatch;
        return this;
    }

    public void a(int i, long j, boolean z, float f, double d, String str) {
    }

    public void a(int i, Bundle bundle) {
        try {
            if (bundle.containsKey(cn.fly.commons.a.l.a("010Zel!e eiejedeifg(heIfk"))) {
                this.b = bundle.getString(cn.fly.commons.a.l.a("010Eel-e>eiejedeifg:heOfk"));
            } else if (bundle.containsKey(cn.fly.commons.a.l.a("017Hel<e>eiejedei+h$ejegej+jHeigj6jejg"))) {
                this.c = bundle.getBoolean(cn.fly.commons.a.l.a("017XelHe eiejedeiBhMejegejFj*eigjJjejg"));
            }
            if (this.a != null) {
                this.a.countDown();
            }
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder, android.os.IBinder
    public String getInterfaceDescriptor() {
        return this.d;
    }

    @Override // android.os.Binder
    protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            if (i != 2) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString(this.d);
                return true;
            }
            parcel.enforceInterface(this.d);
            a(parcel.readInt(), parcel.readInt() > 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
        } else {
            parcel.enforceInterface(this.d);
            a(parcel.readInt(), parcel.readLong(), parcel.readInt() > 0, parcel.readFloat(), parcel.readDouble(), parcel.readString());
        }
        parcel2.writeNoException();
        return true;
    }
}