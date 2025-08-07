package cn.fly.commons.cc;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class w {
    private w() {
    }

    public static int a() {
        return 70;
    }

    public static c a(String... strArr) {
        return a((Object[]) strArr);
    }

    public static c a(byte[]... bArr) {
        return a((Object[]) bArr);
    }

    private static c a(Object[] objArr) {
        if (objArr.length == 0) {
            return null;
        }
        c cVar = new c(objArr[0]);
        for (int i = 1; i < objArr.length; i++) {
            cVar.a(objArr[i]);
        }
        return cVar;
    }

    public static class c {
        private d a;

        private c(Object obj) {
            this.a = new d(obj);
        }

        public c a(Object obj) {
            this.a.a(obj);
            return this;
        }

        public d a(String str, Object obj) {
            return this.a.a(str, obj);
        }

        public d a(String str, Class<?> cls) {
            return this.a.a(str, cls);
        }

        public void a() throws Throwable {
            this.a.a();
        }
    }

    public static class d {
        private ArrayList<Object> a;
        private ArrayList<Object> b;
        private HashMap<String, Object> c;
        private HashMap<String, Object> d;
        private String e;
        private HashMap<Class<?>, Class<? extends s<?>>> f;

        private d(Object obj) {
            this.a = new ArrayList<>();
            this.a.add(obj);
            this.b = new ArrayList<>();
            this.c = new HashMap<>();
            this.d = new HashMap<>();
            this.f = new HashMap<>();
            this.c.put("t_map", this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Object obj) {
            this.a.add(obj);
        }

        public d a(String str, Object obj) {
            this.c.put(str, obj);
            return this;
        }

        public d a(String str, Class<?> cls) {
            v.a.put(str, cls);
            return this;
        }

        public d a(String str) {
            this.e = str;
            return this;
        }

        public <T> d a(Class<T> cls, Class<? extends s<T>> cls2) {
            this.f.put(cls, cls2);
            return this;
        }

        public void a() throws Throwable {
            byte[] bytes;
            InputStream byteArrayInputStream;
            ArrayList<x> arrayList = new ArrayList<>();
            if (this.e == null) {
                bytes = null;
            } else {
                bytes = this.e.getBytes("UTF-8");
                System.arraycopy(bytes, 0, new byte[16], 0, Math.min(bytes.length, 16));
            }
            try {
                t tVar = new t();
                Iterator<Object> it = this.a.iterator();
                while (it.hasNext()) {
                    Object next = it.next();
                    if (next instanceof String) {
                        byteArrayInputStream = new FileInputStream((String) next);
                    } else if (next instanceof byte[]) {
                        byteArrayInputStream = new ByteArrayInputStream((byte[]) next);
                    } else {
                        throw new ClassCastException("program is not string or byte array");
                    }
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    a(byteArrayInputStream, arrayList, tVar);
                    this.d.put("l_t", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
                }
                for (Map.Entry<Class<?>, Class<? extends s<?>>> entry : this.f.entrySet()) {
                    tVar.a(entry.getKey(), entry.getValue());
                }
                new v(arrayList, this.b).a(this.c, tVar);
            } catch (Throwable th) {
                th = th;
                if (bytes == null) {
                    throw th;
                }
                String string = th.getMessage() == null ? th.getClass().toString() : th.getMessage();
                if (th instanceof u) {
                    th = th.getCause();
                }
                throw new u(a(bytes, string + " " + a(th)), th);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x0017, code lost:
        
            r0 = new java.io.StringWriter();
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x001c, code lost:
        
            r1 = new java.io.PrintWriter(r0);
            r5.printStackTrace(r1);
            r1.flush();
            r1.close();
            r5 = r0.toString();
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0030, code lost:
        
            r0.close();
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0036, code lost:
        
            r5 = th;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0037, code lost:
        
            r2 = r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private String a(Throwable th) {
            String string;
            if (th == null) {
                return "";
            }
            Throwable cause = th;
            while (true) {
                StringWriter stringWriter = null;
                if (cause == null) {
                    break;
                }
                try {
                    if (cause instanceof UnknownHostException) {
                        return "";
                    }
                    cause = cause.getCause();
                } catch (Throwable th2) {
                    th = th2;
                }
                th = th2;
                try {
                    if (th instanceof OutOfMemoryError) {
                        return cn.fly.commons.w.b("023]diCehEdk(hcbYdgebciFcbe?dkDh[cich-dHdihecjcjce");
                    }
                    String message = th.getMessage();
                    if (stringWriter != null) {
                        try {
                            stringWriter.close();
                        } catch (Throwable th3) {
                        }
                    }
                    return message;
                } finally {
                    if (stringWriter != null) {
                        try {
                            stringWriter.close();
                        } catch (Throwable th4) {
                        }
                    }
                }
            }
            return string;
        }

        private String a(byte[] bArr, String str) {
            Cipher cipher;
            if (bArr != null) {
                try {
                    byte[] bytes = str.getBytes("UTF-8");
                    SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, cn.fly.commons.w.b("003Qecfhdk"));
                    StringBuilder sb = new StringBuilder();
                    sb.append(cn.fly.commons.w.b("003Yecfhdk"));
                    sb.append(cn.fly.commons.w.b("003k<fhdc"));
                    sb.append(cn.fly.commons.w.b("008[eiAk?fkhbdcdkhjfk"));
                    sb.append(cn.fly.commons.w.b("006cZcbcbchMd(di"));
                    Provider provider = Security.getProvider(cn.fly.commons.w.b("002>eidc"));
                    if (provider != null) {
                        cipher = Cipher.getInstance(sb.toString(), provider);
                    } else {
                        cipher = Cipher.getInstance(sb.toString(), cn.fly.commons.w.b("0025eidc"));
                    }
                    cipher.init(1, secretKeySpec);
                    byte[] bArr2 = new byte[cipher.getOutputSize(bytes.length)];
                    cipher.doFinal(bArr2, cipher.update(bytes, 0, bytes.length, bArr2, 0));
                    return new BigInteger(1, bArr2).toString(16);
                } catch (Throwable th) {
                    return "";
                }
            }
            return str;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:114:0x0203 A[Catch: all -> 0x020b, TRY_ENTER, TryCatch #3 {all -> 0x020b, blocks: (B:114:0x0203, B:115:0x0207), top: B:128:0x0201 }] */
        /* JADX WARN: Removed duplicated region for block: B:115:0x0207 A[Catch: all -> 0x020b, TRY_LEAVE, TryCatch #3 {all -> 0x020b, blocks: (B:114:0x0203, B:115:0x0207), top: B:128:0x0201 }] */
        /* JADX WARN: Type inference failed for: r0v11, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v12, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r2v1, types: [cn.fly.commons.cc.w$1] */
        /* JADX WARN: Type inference failed for: r2v12 */
        /* JADX WARN: Type inference failed for: r2v3 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void a(InputStream inputStream, ArrayList<x> arrayList, t tVar) throws Throwable {
            InputStream bufferedInputStream;
            Throwable th;
            long jCurrentTimeMillis;
            int i;
            InputStream gZIPInputStream;
            DataInputStream dataInputStream;
            a aVar;
            ByteArrayInputStream byteArrayInputStream;
            DataInputStream dataInputStream2;
            ByteArrayInputStream byteArrayInputStream2;
            if (inputStream.read() != 70) {
                inputStream.close();
                return;
            }
            DataInputStream dataInputStream3 = 0;
            dataInputStream = null;
            DataInputStream dataInputStream4 = null;
            dataInputStream = null;
            DataInputStream dataInputStream5 = null;
            dataInputStream3 = 0;
            try {
                jCurrentTimeMillis = System.currentTimeMillis();
                i = inputStream.read();
                if (i == 1 || i == 2) {
                    bufferedInputStream = inputStream;
                    try {
                        gZIPInputStream = new GZIPInputStream(bufferedInputStream);
                    } catch (Throwable th2) {
                        th = th2;
                        th = th;
                        try {
                            if (dataInputStream3 == 0) {
                            }
                            throw th;
                        } catch (Throwable th3) {
                            throw th;
                        }
                    }
                } else {
                    gZIPInputStream = inputStream;
                }
                try {
                    bufferedInputStream = new BufferedInputStream(gZIPInputStream, 4096);
                    dataInputStream = new DataInputStream(bufferedInputStream);
                } catch (Throwable th4) {
                    th = th4;
                    bufferedInputStream = gZIPInputStream;
                }
            } catch (Throwable th5) {
                th = th5;
                bufferedInputStream = inputStream;
            }
            try {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(null);
                int i2 = dataInputStream.readInt();
                for (int i3 = 0; i3 < i2; i3++) {
                    arrayList2.add(Integer.valueOf(dataInputStream.readInt()));
                }
                int i4 = dataInputStream.readInt();
                for (int i5 = 0; i5 < i4; i5++) {
                    arrayList2.add(Long.valueOf(dataInputStream.readLong()));
                }
                int i6 = dataInputStream.readInt();
                for (int i7 = 0; i7 < i6; i7++) {
                    arrayList2.add(Float.valueOf(dataInputStream.readFloat()));
                }
                int i8 = dataInputStream.readInt();
                for (int i9 = 0; i9 < i8; i9++) {
                    arrayList2.add(Double.valueOf(dataInputStream.readDouble()));
                }
                int i10 = dataInputStream.readInt();
                for (int i11 = 0; i11 < i10; i11++) {
                    arrayList2.add(Boolean.valueOf(dataInputStream.readBoolean()));
                }
                int i12 = dataInputStream.readInt();
                if (i == 2) {
                    byte[] bArr = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(bArr);
                    try {
                        byteArrayInputStream2 = new ByteArrayInputStream(bArr);
                        try {
                            DataInputStream dataInputStream6 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(byteArrayInputStream2), 2048));
                            for (int i13 = 0; i13 < i12; i13++) {
                                try {
                                    arrayList2.add(dataInputStream6.readUTF());
                                } catch (Throwable th6) {
                                    th = th6;
                                    dataInputStream4 = dataInputStream6;
                                    if (dataInputStream4 != null) {
                                        dataInputStream4.close();
                                    } else if (byteArrayInputStream2 != null) {
                                        byteArrayInputStream2.close();
                                    }
                                    throw th;
                                }
                            }
                            dataInputStream6.close();
                        } catch (Throwable th7) {
                            th = th7;
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        byteArrayInputStream2 = null;
                    }
                } else {
                    for (int i14 = 0; i14 < i12; i14++) {
                        arrayList2.add(dataInputStream.readUTF());
                    }
                }
                if (dataInputStream.readByte() != 15) {
                    throw new RuntimeException("data has offset in pos 1");
                }
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                this.d.put("lc_t", Long.valueOf(jCurrentTimeMillis2 - jCurrentTimeMillis));
                if (dataInputStream.readBoolean()) {
                    aVar = new b(arrayList2, dataInputStream, arrayList.size());
                } else {
                    aVar = new a(arrayList2, dataInputStream, arrayList.size());
                }
                int i15 = dataInputStream.readInt();
                boolean z = dataInputStream.readBoolean();
                if (dataInputStream.readByte() != 25) {
                    throw new RuntimeException("data has offset in pos 2");
                }
                for (int i16 = 0; i16 < i15; i16++) {
                    x xVar = new x();
                    xVar.a = dataInputStream.readByte();
                    if (z) {
                        aVar.a(xVar);
                    }
                    xVar.a(aVar);
                    arrayList.add(xVar);
                }
                if (dataInputStream.readByte() != 39) {
                    throw new RuntimeException("data has offset in pos 3");
                }
                long jCurrentTimeMillis3 = System.currentTimeMillis();
                this.d.put("lcmd_t", Long.valueOf(jCurrentTimeMillis3 - jCurrentTimeMillis2));
                try {
                    byte[] bArr2 = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(bArr2);
                    if (i == 2) {
                        try {
                            byteArrayInputStream = new ByteArrayInputStream(bArr2);
                            try {
                                dataInputStream2 = new DataInputStream(new GZIPInputStream(byteArrayInputStream));
                            } catch (Throwable th9) {
                                th = th9;
                            }
                        } catch (Throwable th10) {
                            th = th10;
                            byteArrayInputStream = null;
                        }
                        try {
                            byte[] bArr3 = new byte[dataInputStream2.readInt()];
                            dataInputStream2.readFully(bArr3);
                            dataInputStream2.close();
                            bArr2 = bArr3;
                        } catch (Throwable th11) {
                            th = th11;
                            dataInputStream5 = dataInputStream2;
                            if (dataInputStream5 != null) {
                                dataInputStream5.close();
                            } else if (byteArrayInputStream != null) {
                                byteArrayInputStream.close();
                            }
                            throw th;
                        }
                    }
                    tVar.a(bArr2);
                    this.d.put("mreg_t", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis3));
                } catch (Throwable th12) {
                }
                try {
                    dataInputStream.close();
                } catch (Throwable th13) {
                }
            } catch (Throwable th14) {
                th = th14;
                dataInputStream3 = dataInputStream;
                if (dataInputStream3 == 0) {
                    dataInputStream3.close();
                } else {
                    bufferedInputStream.close();
                }
                throw th;
            }
        }
    }

    public static class a {
        protected ArrayList<Object> a;
        protected DataInputStream b;
        protected int c;

        private a(ArrayList<Object> arrayList, DataInputStream dataInputStream, int i) {
            this.a = arrayList;
            this.b = dataInputStream;
            this.c = i;
        }

        public void a() throws Throwable {
            this.b.readShort();
        }

        public <T> T b() throws Throwable {
            return (T) this.a.get(this.b.readShort());
        }

        public void a(x xVar) throws Throwable {
            xVar.b = (String) this.a.get(this.b.readShort());
            xVar.c = this.b.readShort();
        }

        public int c() {
            return this.c;
        }
    }

    public static class b extends a {
        private b(ArrayList<Object> arrayList, DataInputStream dataInputStream, int i) {
            super(arrayList, dataInputStream, i);
        }

        @Override // cn.fly.commons.cc.w.a
        public void a() throws Throwable {
            this.b.readInt();
        }

        @Override // cn.fly.commons.cc.w.a
        public <T> T b() throws Throwable {
            return (T) this.a.get(this.b.readInt());
        }

        @Override // cn.fly.commons.cc.w.a
        public void a(x xVar) throws Throwable {
            xVar.b = (String) this.a.get(this.b.readInt());
            xVar.c = this.b.readInt();
        }
    }
}