package cn.fly.apc;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/* loaded from: classes.dex */
public class a {
    public Object d;
    public Bundle e;
    public int a = -1;
    public int b = -1;
    public int c = -1;
    public int f = -1;

    public void a(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        if (this.d != null) {
            if (this.d instanceof Serializable) {
                parcel.writeInt(2);
                parcel.writeSerializable((Serializable) this.d);
            } else if (this.d instanceof Parcelable) {
                parcel.writeInt(3);
                parcel.writeParcelable((Parcelable) this.d, i);
            }
        }
        if (this.e == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeBundle(this.e);
        }
    }

    public a a(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        int i = parcel.readInt();
        if (i == 2) {
            this.d = parcel.readSerializable();
            i = parcel.readInt();
        } else if (i == 3) {
            this.d = parcel.readParcelable(getClass().getClassLoader());
            i = parcel.readInt();
        }
        if (i == 1) {
            this.e = parcel.readBundle();
        }
        return this;
    }

    public String toString() {
        return "APCMessage{what=" + this.a + ", arg1=" + this.b + ", arg2=" + this.c + ", obj=" + this.d + ", data=" + this.e + '}';
    }
}