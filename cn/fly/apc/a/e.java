package cn.fly.apc.a;

import android.os.Parcel;
import cn.fly.apc.APCException;

/* loaded from: classes.dex */
public class e {
    public cn.fly.apc.a a;
    public String b;
    public long e;
    public APCException d = null;
    public String c = cn.fly.apc.b.a().getPackageName();

    public e(cn.fly.apc.a aVar, String str, long j) {
        this.e = -1L;
        this.a = aVar;
        this.b = str;
        this.e = j;
    }

    public void a(Parcel parcel, int i) {
        parcel.writeLong(this.e);
        if (this.a != null) {
            parcel.writeInt(1);
            this.a.a(parcel, i);
        }
        if (this.b != null) {
            parcel.writeInt(2);
            parcel.writeString(this.b);
        }
        this.c = cn.fly.apc.b.a().getPackageName();
        parcel.writeInt(3);
        parcel.writeString(this.c);
    }

    public static e a(Parcel parcel) {
        e eVar = new e(null, null, parcel.readLong());
        int i = parcel.readInt();
        if (i == 1) {
            eVar.a = new cn.fly.apc.a().a(parcel);
            i = parcel.readInt();
        }
        if (i == 2) {
            eVar.b = parcel.readString();
            i = parcel.readInt();
        }
        if (i == 3) {
            eVar.c = parcel.readString();
        }
        return eVar;
    }

    public String toString() {
        return "InnerMessage{apcMessage=" + this.a + ", businessID='" + this.b + "', pkg='" + this.c + "'}";
    }
}