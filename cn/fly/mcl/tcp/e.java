package cn.fly.mcl.tcp;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class e implements Serializable {
    public final int a;
    public final int b;
    public long c;
    public String d;

    public e(int i) {
        this(i, null);
    }

    public e(int i, String str) {
        this(str != null ? str.length() : 0, i, 0L, str);
    }

    e(int i, int i2, long j, String str) {
        this.a = i;
        this.b = i2;
        this.c = j;
        this.d = str;
    }

    public byte[] a() {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(b());
        byteBufferAllocate.put((byte) 1);
        byteBufferAllocate.putInt(this.a);
        byteBufferAllocate.putInt(this.b);
        byteBufferAllocate.putLong(this.c);
        if (this.d != null) {
            byteBufferAllocate.put(this.d.getBytes(Charset.forName("UTF-8")));
        }
        return byteBufferAllocate.array();
    }

    public static List<e> a(ByteBuffer byteBuffer) {
        e eVarB;
        ArrayList arrayList = new ArrayList();
        while (byteBuffer.remaining() >= 17 && (eVarB = b(byteBuffer)) != null) {
            arrayList.add(eVarB);
        }
        return arrayList;
    }

    static e b(ByteBuffer byteBuffer) {
        e eVarC = c(byteBuffer);
        if (eVarC != null && eVarC.a > 0) {
            if (eVarC.a > byteBuffer.remaining()) {
                return null;
            }
            byte[] bArr = new byte[eVarC.a];
            byteBuffer.get(bArr);
            eVarC.d = new String(bArr);
        }
        return eVarC;
    }

    static e c(ByteBuffer byteBuffer) {
        if (byteBuffer.get() != 1) {
            return null;
        }
        int i = byteBuffer.getInt();
        int i2 = byteBuffer.getInt();
        if (i2 > 9999) {
            return null;
        }
        return new e(i, i2, byteBuffer.getLong(), null);
    }

    public int b() {
        return this.a + 17;
    }
}