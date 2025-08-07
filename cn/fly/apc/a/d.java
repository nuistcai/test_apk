package cn.fly.apc.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import cn.fly.commons.a.l;

/* loaded from: classes.dex */
public abstract class d extends Binder implements IInterface {
    private static final String a = l.a("031d]elegemegelggemBekd>emejegGkhXemffgeejedFh0ff2fjg-ekfgBedg");

    public abstract e a(e eVar) throws RemoteException;

    public d() {
        attachInterface(this, a);
    }

    public static d a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(a);
        if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof d)) {
            return (d) iInterfaceQueryLocalInterface;
        }
        return new a(iBinder);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        String str = a;
        switch (i) {
            case 1:
                parcel.enforceInterface(str);
                e eVarA = a(e.a(parcel));
                parcel2.writeNoException();
                if (eVarA != null) {
                    eVarA.a(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            case 1598968902:
                parcel2.writeString(str);
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }

    private static class a extends d {
        private IBinder a;

        a(IBinder iBinder) {
            this.a = iBinder;
        }

        @Override // cn.fly.apc.a.d, android.os.IInterface
        public IBinder asBinder() {
            return this.a;
        }

        @Override // android.os.Binder, android.os.IBinder
        public String getInterfaceDescriptor() {
            return d.a;
        }

        @Override // cn.fly.apc.a.d
        public e a(e eVar) throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(d.a);
                if (eVar != null) {
                    eVar.a(parcelObtain, 0);
                } else {
                    parcelObtain.writeInt(0);
                }
                this.a.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return e.a(parcelObtain2);
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }
    }
}