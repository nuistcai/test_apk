package cn.fly.tools.utils;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import cn.fly.commons.C0041r;
import cn.fly.commons.t;
import cn.fly.commons.u;
import cn.fly.commons.w;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.EverythingKeeper;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public class FlyPersistence {
    private static final int h = Process.myPid();
    private static volatile HashSet<String> i = new HashSet<>();
    private final i a;
    private final ScheduledExecutorService b;
    private final Map<String, j> c = new HashMap();
    private final ReentrantReadWriteLock d = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock e = this.d.writeLock();
    private final ReentrantReadWriteLock.ReadLock f = this.d.readLock();
    private final f g;

    public FlyPersistence(Context context, final String str, String str2) {
        this.g = new f(str2);
        this.a = new i(context, str, this.g);
        if (str != null && str.startsWith(".") && str.length() > 1) {
            str = str.substring(1);
        }
        this.b = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: cn.fly.tools.utils.FlyPersistence.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "M-PL-MP-" + str);
            }
        });
        this.b.schedule(new d(), 3000L, TimeUnit.MILLISECONDS);
    }

    public static boolean a(Context context, String str) {
        File file = new File(a(context), str);
        return file.exists() && file.length() > 0;
    }

    public static boolean b(Context context, String str) {
        String str2;
        boolean zDelete;
        if (!i.contains(str)) {
            File file = new File(a(context), str);
            if (file.exists()) {
                zDelete = file.delete();
                str2 = zDelete ? "succ" : "fail";
            } else {
                zDelete = true;
                str2 = "not exist";
            }
        } else {
            str2 = "oped";
            zDelete = false;
        }
        FlyLog.getInstance().d("[CKCMP] try del mpf '" + str + "': " + str2, new Object[0]);
        return zDelete;
    }

    public static File a(Context context) {
        return new File(context.getFilesDir(), w.b("004DfkCe]cieh"));
    }

    private class d implements Runnable {
        private d() {
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x00a4 A[Catch: all -> 0x00ee, TRY_LEAVE, TryCatch #11 {all -> 0x00ee, blocks: (B:30:0x0094, B:32:0x00a4, B:38:0x00cb, B:40:0x00d6, B:41:0x00df, B:33:0x00ad, B:34:0x00b1, B:36:0x00b7), top: B:88:0x0094, outer: #2, inners: #4 }] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            ArrayList arrayList;
            Throwable th;
            List<j> listA;
            try {
                try {
                    FlyPersistence.this.e.lock();
                    ArrayList arrayList2 = null;
                    try {
                        if (!FlyPersistence.this.c.isEmpty()) {
                            arrayList = new ArrayList(FlyPersistence.this.c.values());
                            try {
                                FlyPersistence.this.c.clear();
                                if (!arrayList.isEmpty()) {
                                    FlyPersistence.this.a.b().lock();
                                }
                                arrayList2 = arrayList;
                            } catch (Throwable th2) {
                                th = th2;
                                try {
                                    FlyPersistence.b(th, FlyPersistence.this.a.j);
                                    try {
                                    } catch (Throwable th3) {
                                        FlyPersistence.b(th3, FlyPersistence.this.a.j);
                                    }
                                    arrayList2 = arrayList;
                                    if (arrayList2 != null) {
                                        try {
                                            listA = FlyPersistence.this.a.a(arrayList2);
                                            if (!listA.isEmpty()) {
                                            }
                                            FlyPersistence.this.a.b().unlock();
                                        } catch (Throwable th4) {
                                            FlyPersistence.this.a.b().unlock();
                                            throw th4;
                                        }
                                    }
                                    FlyPersistence.this.b.schedule(this, 3000L, TimeUnit.MILLISECONDS);
                                } finally {
                                    try {
                                    } catch (Throwable th5) {
                                        FlyPersistence.b(th5, FlyPersistence.this.a.j);
                                    }
                                }
                            }
                        }
                    } catch (Throwable th6) {
                        arrayList = null;
                        th = th6;
                    }
                    if (arrayList2 != null && !arrayList2.isEmpty()) {
                        listA = FlyPersistence.this.a.a(arrayList2);
                        if (!listA.isEmpty()) {
                            FlyPersistence.this.e.lock();
                            try {
                                for (j jVar : listA) {
                                    FlyPersistence.this.c.put(jVar.a, jVar);
                                }
                                FlyPersistence.this.e.unlock();
                            } finally {
                                FlyPersistence.this.e.unlock();
                            }
                        }
                        FlyPersistence.this.a.b().unlock();
                    }
                    FlyPersistence.this.b.schedule(this, 3000L, TimeUnit.MILLISECONDS);
                } catch (Throwable th7) {
                    FlyPersistence.b(th7, FlyPersistence.this.a.j);
                }
            } catch (Throwable th8) {
                try {
                    FlyPersistence.b(th8, FlyPersistence.this.a.j);
                    FlyPersistence.this.b.schedule(this, 3000L, TimeUnit.MILLISECONDS);
                } catch (Throwable th9) {
                    try {
                        FlyPersistence.this.b.schedule(this, 3000L, TimeUnit.MILLISECONDS);
                    } catch (Throwable th10) {
                        FlyPersistence.b(th10, FlyPersistence.this.a.j);
                    }
                    throw th9;
                }
            }
        }
    }

    public void a(j jVar) {
        if (jVar == null) {
            throw new IllegalArgumentException("dataEntry is null");
        }
        String strA = jVar.a();
        long jD = jVar.d();
        if (TextUtils.isEmpty(strA) || jD < 0) {
            throw new IllegalArgumentException("Key: " + strA + ", expAt: " + jD);
        }
        jVar.a(Data.rawMD5(strA));
        this.e.lock();
        try {
            this.c.put(strA, jVar);
        } finally {
            try {
            } finally {
            }
        }
    }

    public boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Key: " + str);
        }
        byte[] bArrRawMD5 = Data.rawMD5(str);
        boolean[] zArr = {false};
        String[] strArr = {"f"};
        this.e.lock();
        try {
            if (!this.c.isEmpty() && this.c.containsKey(str)) {
                this.c.remove(str);
                zArr[0] = true;
                strArr[0] = "m";
            }
        } finally {
            try {
                zArr[0] = this.a.a(new g(bArrRawMD5), true);
                d("rmv: " + str + ", from: " + strArr[0] + ", succ is " + zArr[0], this.a.j);
                return zArr[0];
            } finally {
            }
        }
        zArr[0] = this.a.a(new g(bArrRawMD5), true);
        d("rmv: " + str + ", from: " + strArr[0] + ", succ is " + zArr[0], this.a.j);
        return zArr[0];
    }

    public boolean a() {
        d("cln", this.a.j);
        this.e.lock();
        try {
            if (!this.c.isEmpty()) {
                this.c.clear();
            }
        } finally {
            try {
                return this.a.d();
            } finally {
            }
        }
        return this.a.d();
    }

    public <T> T a(e<T> eVar) throws NoValidDataException {
        if (eVar == null) {
            throw new IllegalArgumentException("deserializer is null");
        }
        String strA = eVar.a();
        if (TextUtils.isEmpty(strA)) {
            throw new IllegalArgumentException("Key: " + strA);
        }
        this.f.lock();
        try {
            try {
                if (!this.c.isEmpty() && this.c.containsKey(strA)) {
                    j jVar = this.c.get(strA);
                    if (!jVar.e()) {
                        return (T) jVar.b();
                    }
                    this.c.remove(strA);
                    d("Get done, exp-m: " + strA, this.a.j);
                    throw new NoValidDataException();
                }
            } catch (NoValidDataException e2) {
                throw e2;
            } catch (Throwable th) {
                b(th, this.a.j);
            }
            try {
                return (T) this.a.a(new g(Data.rawMD5(strA)), eVar);
            } finally {
                NoValidDataException noValidDataException = new NoValidDataException();
            }
        } finally {
            this.f.unlock();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0067  */
    /* JADX WARN: Type inference failed for: r10v13, types: [android.os.Parcelable[]] */
    /* JADX WARN: Type inference failed for: r9v14, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v24 */
    /* JADX WARN: Type inference failed for: r9v25 */
    /* JADX WARN: Type inference failed for: r9v26 */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.util.Map] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public HashMap<String, Object> b() {
        List<byte[]> listE;
        a aVarA;
        Parcelable parcelableA;
        ?? map;
        ?? arrayList;
        HashMap map2 = new HashMap();
        this.f.lock();
        try {
            if (!this.c.isEmpty()) {
                Iterator<Map.Entry<String, j>> it = this.c.entrySet().iterator();
                while (it.hasNext()) {
                    j value = it.next().getValue();
                    if (!value.e()) {
                        map2.put(value.a(), value.b());
                    }
                }
            }
        } finally {
            try {
                HashMap<String, Object> map3 = new HashMap<>();
                HashMap map4 = new HashMap();
                listE = this.a.e();
                if (listE.size() > 0) {
                }
                map3.putAll(map4);
                map3.putAll(map2);
                d("GetA done: " + map3.size(), this.a.j);
                return map3;
            } finally {
            }
        }
        HashMap<String, Object> map32 = new HashMap<>();
        HashMap map42 = new HashMap();
        listE = this.a.e();
        if (listE.size() > 0) {
            Iterator<byte[]> it2 = listE.iterator();
            while (it2.hasNext()) {
                Object objA = this.g.a(it2.next(), (Object) null);
                if (objA instanceof KVEntry) {
                    KVEntry kVEntry = (KVEntry) objA;
                    aVarA = new a(kVEntry.getKey(), kVEntry.getValue());
                } else {
                    aVarA = a.a((HashMap<Byte, Object>) objA);
                }
                Object objA2 = aVarA.a();
                if (objA2 != null) {
                    try {
                        parcelableA = ((b) objA2).a((b) null);
                    } catch (Throwable th) {
                    }
                } else {
                    parcelableA = null;
                }
                if (objA2 != null && parcelableA == null) {
                    try {
                        b[] bVarArr = (b[]) objA2;
                        if (bVarArr != null && bVarArr.length > 0) {
                            ?? r10 = (Parcelable[]) Array.newInstance((Class<?>) bVarArr[0].a(), bVarArr.length);
                            for (int i2 = 0; i2 < r10.length; i2++) {
                                r10[i2] = bVarArr[i2].a((b) null);
                            }
                            parcelableA = r10;
                        }
                    } catch (Throwable th2) {
                    }
                }
                if (objA2 != null && parcelableA == null) {
                    try {
                        List list = (List) objA2;
                        if (list != null && !list.isEmpty()) {
                            if (!(list instanceof ArrayList) && (list instanceof LinkedList)) {
                                arrayList = new LinkedList();
                            } else {
                                arrayList = new ArrayList();
                            }
                            Iterator it3 = list.iterator();
                            while (it3.hasNext()) {
                                arrayList.add(((b) it3.next()).a((b) null));
                            }
                            parcelableA = arrayList;
                        }
                    } catch (Throwable th3) {
                    }
                }
                if (objA2 != null && parcelableA == null) {
                    try {
                        Map map5 = (Map) objA2;
                        if (map5 != null && !map5.isEmpty()) {
                            if (map5 instanceof HashMap) {
                                map = new HashMap();
                            } else if (map5 instanceof Hashtable) {
                                map = new Hashtable();
                            } else if (map5 instanceof TreeMap) {
                                map = new TreeMap();
                            } else {
                                map = new HashMap();
                            }
                            for (Map.Entry entry : map5.entrySet()) {
                                map.put(entry.getKey(), ((b) entry.getValue()).a((b) null));
                            }
                            parcelableA = map;
                        }
                    } catch (Throwable th4) {
                    }
                }
                if (parcelableA != null) {
                    objA2 = parcelableA;
                }
                map42.put(aVarA.a, objA2);
            }
        }
        map32.putAll(map42);
        map32.putAll(map2);
        d("GetA done: " + map32.size(), this.a.j);
        return map32;
    }

    private static class i {
        private File d;
        private volatile RandomAccessFile e;
        private volatile long f;
        private volatile LinkedList<a> g;
        private volatile HashMap<g, a> h;
        private final Context i;
        private final String j;
        private final File k;
        private final f l;
        private final ReentrantReadWriteLock a = new ReentrantReadWriteLock();
        private final ReentrantReadWriteLock.WriteLock b = this.a.writeLock();
        private final ReentrantReadWriteLock.ReadLock c = this.a.readLock();
        private final h m = new h(60);

        public i(Context context, String str, f fVar) {
            this.i = context;
            this.j = str;
            this.k = u.a(u.h + str);
            this.l = fVar;
            f();
        }

        public String a() {
            return this.j;
        }

        public ReentrantReadWriteLock.WriteLock b() {
            return this.b;
        }

        private void f() {
            this.b.lock();
            try {
                FlyPersistence.i.add(this.j);
                u.a(this.k.getAbsolutePath(), true, 1500L, 50L, new t() { // from class: cn.fly.tools.utils.FlyPersistence.i.1
                    @Override // cn.fly.commons.t
                    public boolean a(FileLocker fileLocker) {
                        try {
                            if (i.this.i != null) {
                                i.this.d = new File(FlyPersistence.a(i.this.i), i.this.j);
                                if (!i.this.d.getParentFile().exists()) {
                                    i.this.d.getParentFile().mkdirs();
                                }
                                if (i.this.d.exists() && i.this.d.length() < 43008) {
                                    FlyPersistence.c("Del dirty, size: " + i.this.d.length() + ", min: 43008", i.this.j);
                                    i.this.d.delete();
                                }
                                if (!i.this.d.exists()) {
                                    i.this.d.createNewFile();
                                    i.this.e = new RandomAccessFile(i.this.d, w.b("002)cief"));
                                    i.this.b(1024);
                                    return false;
                                }
                                i.this.e = new RandomAccessFile(i.this.d, w.b("002$cief"));
                                i.this.i();
                                FlyPersistence.d("ava sz " + i.this.g.size() + " useds " + i.this.h.size(), i.this.j);
                                return false;
                            }
                            return false;
                        } catch (Throwable th) {
                            FlyPersistence.b(th, i.this.j);
                            return false;
                        }
                    }
                });
            } finally {
                this.b.unlock();
            }
        }

        public void a(a aVar) {
            try {
                c(aVar.a);
                this.e.seek(aVar.a());
                aVar.a(this.e.readByte());
                aVar.a(a(aVar.g));
                aVar.a(b(aVar.g));
                aVar.b(c(aVar.g));
                aVar.c(d(aVar.g));
            } catch (Throwable th) {
                FlyPersistence.b(th, this.j);
            }
        }

        private byte[] a(long j) throws Throwable {
            byte[] bArr = new byte[16];
            this.e.seek(j + 1);
            this.e.read(bArr, 0, 16);
            return bArr;
        }

        private long b(long j) throws Throwable {
            try {
                this.e.seek(j + 17);
                return this.e.readLong();
            } catch (Throwable th) {
                FlyPersistence.b(th, a());
                return -1L;
            }
        }

        private long c(long j) throws Throwable {
            try {
                this.e.seek(j + 25);
                return this.e.readLong();
            } catch (Throwable th) {
                FlyPersistence.b(th, a());
                return -1L;
            }
        }

        private long d(long j) throws Throwable {
            try {
                this.e.seek(j + 33);
                return this.e.readLong();
            } catch (Throwable th) {
                FlyPersistence.b(th, a());
                return 0L;
            }
        }

        private void g() throws Throwable {
            FlyPersistence.d(" [trim] try ", this.j);
            long size = ((this.h.size() + this.g.size()) * 41) + 1024;
            long length = this.e.length();
            Iterator<a> it = this.h.values().iterator();
            double d = 0.0d;
            while (it.hasNext()) {
                double dC = it.next().c();
                Double.isNaN(dC);
                d += dC;
            }
            long j = length - size;
            double d2 = j;
            Double.isNaN(d2);
            if (d / d2 <= 0.5d) {
                Iterator<a> it2 = h().iterator();
                long jC = size;
                while (it2.hasNext()) {
                    a next = it2.next();
                    if (next.e()) {
                        e(next);
                    } else if (next.b() == jC) {
                        jC += next.c();
                    } else if (next.b() > jC) {
                        a(next, jC);
                        jC += next.c();
                    }
                }
                this.e.setLength(jC);
                FlyPersistence.d(" [trim] real over  before dataBlockSize " + j + " cur " + (jC - size), this.j);
            }
        }

        private ArrayList<a> h() {
            ArrayList<a> arrayList = new ArrayList<>(this.h.values());
            Collections.sort(arrayList);
            return arrayList;
        }

        private void a(a aVar, long j) throws Throwable {
            byte[] bArr = new byte[(int) aVar.e];
            this.e.seek(aVar.d);
            this.e.readFully(bArr);
            this.e.seek(j);
            this.e.write(bArr);
            this.e.seek(aVar.g + 17);
            this.e.writeLong(j);
            aVar.a(j);
            this.h.put(new g(aVar.c), aVar);
        }

        public int c() {
            try {
                this.e.seek(8L);
                return this.e.readInt();
            } catch (Throwable th) {
                FlyPersistence.b(th, this.j);
                return 0;
            }
        }

        public void a(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("indexNum : " + i);
            }
            try {
                this.e.seek(8L);
                this.e.writeInt(i);
            } catch (Throwable th) {
                try {
                    FlyPersistence.b(th, this.j);
                } catch (Throwable th2) {
                    FlyPersistence.b(th2, this.j);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) throws Throwable {
            this.g = new LinkedList<>();
            this.h = new HashMap<>();
            a(0, i);
            a(i);
            this.f = System.currentTimeMillis();
            this.e.seek(0L);
            this.e.writeLong(this.f);
            FlyPersistence.d("new a " + this.g.size() + " u " + this.h.size(), this.j);
        }

        private void a(int i, int i2) {
            while (i < i2) {
                a aVar = new a(i);
                this.g.add(aVar);
                a(aVar.g, (byte) 1);
                i++;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean i() throws Throwable {
            boolean[] zArr = {false};
            long jL = l();
            if (jL != this.f) {
                this.b.lock();
                try {
                    this.m.a();
                    this.f = jL;
                    this.g = new LinkedList<>();
                    this.h = new HashMap<>();
                    int iC = c();
                    for (int i = 0; i < iC; i++) {
                        a aVar = new a(i);
                        if (b(aVar) == 1) {
                            this.g.add(aVar);
                        } else {
                            a(aVar);
                            this.h.put(new g(aVar.c), aVar);
                        }
                    }
                    FlyPersistence.d("update lstt " + this.f + " a " + this.g.size() + " u " + this.h.size(), this.j);
                    zArr[0] = true;
                } finally {
                    this.b.unlock();
                }
            }
            return zArr[0];
        }

        public byte b(a aVar) throws Throwable {
            try {
                this.e.seek(aVar.g);
                return this.e.readByte();
            } catch (Throwable th) {
                FlyPersistence.b(th, this.j);
                return (byte) 0;
            }
        }

        public void a(long j, byte b) {
            try {
                this.e.seek(j);
                this.e.writeByte(b);
            } catch (Throwable th) {
            }
        }

        private void c(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("index : " + i);
            }
            int iC = c();
            if (i >= iC) {
                throw new IndexOutOfBoundsException(b(i, iC));
            }
        }

        private String b(int i, int i2) {
            return "Index: " + i + ", Size: " + i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(a aVar, j jVar) throws Throwable {
            FileOutputStream fileOutputStream;
            BufferedOutputStream bufferedOutputStream;
            byte[] bArrA = this.l.a(new a(jVar.a(), jVar.c()).b());
            long length = this.e.length();
            BufferedOutputStream bufferedOutputStream2 = null;
            try {
                fileOutputStream = new FileOutputStream(this.e.getFD());
                try {
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
            }
            try {
                this.e.seek(length);
                bufferedOutputStream.write(bArrA);
                bufferedOutputStream.flush();
                C0041r.a(bufferedOutputStream, fileOutputStream);
                aVar.a((byte) 0, jVar.d, bArrA.length, jVar.c);
                aVar.d = length;
                c(aVar);
            } catch (Throwable th3) {
                th = th3;
                bufferedOutputStream2 = bufferedOutputStream;
                C0041r.a(bufferedOutputStream2, fileOutputStream);
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public a a(g gVar) throws Throwable {
            a aVar = this.h.get(gVar);
            if (aVar == null) {
                if (this.g.isEmpty()) {
                    j();
                }
                a aVarRemoveFirst = this.g.removeFirst();
                aVarRemoveFirst.a((byte) 0);
                this.h.put(gVar, aVarRemoveFirst);
                return aVarRemoveFirst;
            }
            return aVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public List<j> a(final List<j> list) {
            this.b.lock();
            final ArrayList arrayList = new ArrayList();
            try {
                u.a(this.k.getAbsolutePath(), true, 2000L, 50L, new t() { // from class: cn.fly.tools.utils.FlyPersistence.i.2
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Removed duplicated region for block: B:35:0x0153  */
                    @Override // cn.fly.commons.t
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public boolean a(FileLocker fileLocker) {
                        long j;
                        AnonymousClass1 anonymousClass1;
                        FileOutputStream fileOutputStream;
                        boolean z;
                        BufferedOutputStream bufferedOutputStream;
                        try {
                            long jCurrentTimeMillis = System.currentTimeMillis();
                            i.this.i();
                            if (list.size() > 1) {
                                LinkedList linkedList = new LinkedList();
                                int size = list.size();
                                byte[][] bArr = new byte[size][];
                                int size2 = list.size();
                                int i = 0;
                                while (true) {
                                    anonymousClass1 = null;
                                    if (i >= size2) {
                                        break;
                                    }
                                    j jVar = (j) list.get(i);
                                    g gVar = new g(jVar.d);
                                    byte[] bArrA = i.this.l.a(new a(jVar.a(), jVar.c()).b());
                                    a aVarA = i.this.a(gVar);
                                    aVarA.a((byte) 0, jVar.d, bArrA.length, jVar.c);
                                    i.this.m.a(new g(jVar.f()), new c(jVar.c, jVar.b));
                                    linkedList.add(aVarA);
                                    bArr[i] = bArrA;
                                    i++;
                                    jCurrentTimeMillis = jCurrentTimeMillis;
                                }
                                j = jCurrentTimeMillis;
                                long length = i.this.e.length();
                                try {
                                    fileOutputStream = new FileOutputStream(i.this.e.getFD());
                                    try {
                                        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                                    } catch (Throwable th) {
                                        th = th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    fileOutputStream = null;
                                }
                                try {
                                    i.this.e.seek(i.this.e.length());
                                    for (int i2 = 0; i2 < size; i2++) {
                                        byte[] bArr2 = bArr[i2];
                                        bufferedOutputStream.write(bArr2, 0, bArr2.length);
                                    }
                                    bufferedOutputStream.flush();
                                    C0041r.a(bufferedOutputStream, fileOutputStream);
                                    z = true;
                                } catch (Throwable th3) {
                                    th = th3;
                                    anonymousClass1 = bufferedOutputStream;
                                    try {
                                        FlyPersistence.b(th, i.this.j);
                                        FlyPersistence.c("sta err sz " + list.size(), i.this.j);
                                        Iterator it = linkedList.iterator();
                                        while (it.hasNext()) {
                                            a aVar = (a) it.next();
                                            if (aVar.b == 0) {
                                                i.this.d(aVar);
                                            }
                                        }
                                        arrayList.addAll(list);
                                        C0041r.a(anonymousClass1, fileOutputStream);
                                        z = false;
                                        if (z) {
                                        }
                                        linkedList.clear();
                                        i.this.k();
                                        FlyPersistence.d(" all cost " + (System.currentTimeMillis() - j) + " size " + list.size(), i.this.j);
                                        return false;
                                    } catch (Throwable th4) {
                                        C0041r.a(anonymousClass1, fileOutputStream);
                                        throw th4;
                                    }
                                }
                                if (z) {
                                    for (int i3 = 0; i3 < size2; i3++) {
                                        a aVar2 = (a) linkedList.get(i3);
                                        aVar2.d = length;
                                        if (!i.this.c(aVar2)) {
                                            i.this.d(aVar2);
                                            arrayList.add(list.get(i3));
                                        } else {
                                            length += bArr[i3].length;
                                        }
                                    }
                                }
                                linkedList.clear();
                            } else {
                                j = jCurrentTimeMillis;
                                j jVar2 = (j) list.get(0);
                                a aVarA2 = i.this.a(new g(jVar2.d));
                                try {
                                    i.this.a(aVarA2, jVar2);
                                } catch (Throwable th5) {
                                    FlyPersistence.d("set fail " + th5, i.this.j);
                                    i.this.d(aVarA2);
                                    arrayList.add(jVar2);
                                }
                            }
                            i.this.k();
                            FlyPersistence.d(" all cost " + (System.currentTimeMillis() - j) + " size " + list.size(), i.this.j);
                            return false;
                        } catch (Throwable th6) {
                            FlyPersistence.b(th6, i.this.j);
                            return false;
                        }
                    }
                });
                return arrayList;
            } finally {
                this.b.unlock();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean c(a aVar) {
            try {
                byte[] bArr = new byte[41];
                bArr[0] = 0;
                System.arraycopy(aVar.c, 0, bArr, 1, 16);
                a(aVar.d, bArr, 17);
                a(aVar.e, bArr, 25);
                a(aVar.f, bArr, 33);
                this.e.seek(aVar.g);
                this.e.write(bArr);
                return true;
            } catch (Throwable th) {
                FlyPersistence.b(th, this.j);
                return false;
            }
        }

        private void a(long j, byte[] bArr, int i) {
            for (int i2 = i + 7; i2 >= i; i2--) {
                bArr[i2] = (byte) (255 & j);
                j >>= 8;
            }
        }

        public boolean d() {
            this.b.lock();
            final boolean[] zArr = new boolean[1];
            try {
                u.a(this.k.getAbsolutePath(), true, 1500L, 50L, new t() { // from class: cn.fly.tools.utils.FlyPersistence.i.3
                    @Override // cn.fly.commons.t
                    public boolean a(FileLocker fileLocker) {
                        try {
                            for (a aVar : i.this.h.values()) {
                                i.this.e.seek(aVar.a());
                                i.this.e.writeByte(1);
                                aVar.f();
                            }
                            i.this.g.addAll(i.this.h.values());
                            i.this.h.clear();
                            i.this.e.setLength((i.this.g.size() * 41) + 1024);
                            zArr[0] = true;
                            i.this.k();
                            FlyPersistence.d("Clear done, new size: ", i.this.j);
                        } catch (Throwable th) {
                            FlyPersistence.b(th, i.this.j);
                        }
                        return false;
                    }
                });
                this.b.unlock();
                return zArr[0];
            } catch (Throwable th) {
                this.b.unlock();
                throw th;
            }
        }

        private void j() throws Throwable {
            int iC = c();
            int i = iC + 1024;
            FlyPersistence.d("[exp] old " + iC + " new " + i, this.j);
            long j = (i * 41) + 1024;
            if (((this.h.size() + this.g.size()) * 41) + 1024 < j) {
                Iterator<a> it = h().iterator();
                while (it.hasNext()) {
                    a next = it.next();
                    if (next.b() >= j) {
                        break;
                    }
                    long jB = next.b() + next.c();
                    if (next.e()) {
                        e(next);
                    } else {
                        a(next, this.e.length());
                    }
                    if (jB >= j) {
                        break;
                    }
                }
            }
            this.e.seek(j);
            for (int i2 = iC - 1; i2 < i; i2++) {
                a aVar = new a(i2);
                this.g.add(aVar);
                a(aVar.g, (byte) 1);
            }
            FlyPersistence.d("[exp] ovr", this.j);
            a(i);
        }

        public <T> T a(final g gVar, e<T> eVar) throws Throwable {
            a aVarA;
            final byte[][] bArr = new byte[1][];
            final long[] jArr = new long[1];
            final int[] iArr = new int[1];
            final Object[] objArr = new Object[1];
            this.b.lock();
            try {
                u.a(this.k.getAbsolutePath(), true, 1500L, 50L, new t() { // from class: cn.fly.tools.utils.FlyPersistence.i.4
                    @Override // cn.fly.commons.t
                    public boolean a(FileLocker fileLocker) {
                        c cVarA;
                        try {
                            if (!i.this.i() && (cVarA = i.this.m.a(gVar)) != null && cVarA.b != null) {
                                if (cVarA.a()) {
                                    i.this.a(gVar, false);
                                    iArr[0] = 2;
                                } else {
                                    iArr[0] = 4;
                                    objArr[0] = cVarA.b;
                                }
                            }
                            a aVar = (a) i.this.h.get(gVar);
                            if (aVar == null) {
                                iArr[0] = 1;
                            } else if (aVar.e()) {
                                i.this.d(aVar);
                                iArr[0] = 2;
                            } else {
                                jArr[0] = aVar.f;
                                bArr[0] = i.this.f(aVar);
                                iArr[0] = 3;
                            }
                        } catch (Throwable th) {
                            FlyPersistence.b(th, i.this.j);
                        }
                        return false;
                    }
                });
                this.b.unlock();
                if (iArr[0] == 4) {
                    return (T) objArr[0];
                }
                if (iArr[0] != 3) {
                    throw new NoValidDataException();
                }
                Object objA = this.l.a(bArr[0], (Object) null);
                if (objA instanceof KVEntry) {
                    KVEntry kVEntry = (KVEntry) objA;
                    aVarA = new a(kVEntry.getKey(), kVEntry.getValue());
                } else {
                    aVarA = a.a((HashMap<Byte, Object>) objA);
                }
                if (aVarA != null) {
                    T tA = eVar.a(aVarA.a());
                    this.m.a(gVar, new c(Long.valueOf(jArr[0]).longValue(), tA));
                    return tA;
                }
                throw new NoValidDataException();
            } catch (Throwable th) {
                this.b.unlock();
                throw th;
            }
        }

        public List<byte[]> e() {
            this.b.lock();
            final ArrayList arrayList = new ArrayList();
            try {
                u.a(this.k.getAbsolutePath(), true, 1500L, 50L, new t() { // from class: cn.fly.tools.utils.FlyPersistence.i.5
                    @Override // cn.fly.commons.t
                    public boolean a(FileLocker fileLocker) {
                        try {
                            i.this.i();
                            if (i.this.h != null) {
                                Iterator it = i.this.h.values().iterator();
                                while (it.hasNext()) {
                                    arrayList.add(i.this.f((a) it.next()));
                                }
                                return false;
                            }
                            return false;
                        } catch (Throwable th) {
                            FlyPersistence.b(th, i.this.a());
                            return false;
                        }
                    }
                });
                return arrayList;
            } finally {
                this.b.unlock();
            }
        }

        public boolean a(final g gVar, boolean z) {
            this.b.lock();
            final boolean[] zArr = new boolean[1];
            try {
                if (!z) {
                    zArr[0] = b(gVar);
                } else {
                    u.a(this.k.getAbsolutePath(), true, 1500L, 50L, new t() { // from class: cn.fly.tools.utils.FlyPersistence.i.6
                        @Override // cn.fly.commons.t
                        public boolean a(FileLocker fileLocker) {
                            zArr[0] = i.this.b(gVar);
                            return false;
                        }
                    });
                }
                this.b.unlock();
                return zArr[0];
            } catch (Throwable th) {
                this.b.unlock();
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean b(g gVar) {
            try {
                i();
                a aVar = this.h.get(gVar);
                if (aVar != null) {
                    d(aVar);
                }
                this.m.b(gVar);
                return true;
            } catch (Throwable th) {
                FlyPersistence.b(th, a());
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(a aVar) throws Throwable {
            this.b.lock();
            try {
                e(aVar);
                k();
            } finally {
                this.b.unlock();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void k() throws Throwable {
            if (new Random().nextInt(10) < 1) {
                g();
            }
            this.f = System.currentTimeMillis();
            this.e.seek(0L);
            this.e.writeLong(this.f);
        }

        private long l() throws Throwable {
            this.e.seek(0L);
            return this.e.readLong();
        }

        private void e(a aVar) throws Throwable {
            this.h.remove(new g(aVar.c));
            this.e.seek(aVar.a());
            this.e.writeByte(1);
            this.g.add(aVar);
            aVar.f();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte[] f(a aVar) throws Throwable {
            this.e.seek(aVar.b());
            byte[] bArr = new byte[(int) aVar.e];
            this.e.readFully(bArr);
            return bArr;
        }

        private static class a implements Comparable<a> {
            private int a;
            private byte b;
            private byte[] c;
            private long d;
            private long e;
            private long f;
            private long g;

            public a(int i) {
                this.a = i;
                this.g = (i * 41) + 1024;
            }

            public long a() {
                return this.g;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void a(byte b) {
                this.b = b;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void a(byte[] bArr) {
                this.c = bArr;
            }

            public long b() {
                return this.d;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void a(long j) {
                this.d = j;
            }

            public long c() {
                return this.e;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void b(long j) {
                this.e = j;
            }

            public long d() {
                return this.f;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void c(long j) {
                this.f = j;
            }

            public boolean e() {
                return d() != 0 && d() <= System.currentTimeMillis();
            }

            public void a(byte b, byte[] bArr, long j, long j2) {
                this.b = b;
                this.c = bArr;
                this.e = j;
                this.f = j2;
            }

            public void f() {
                this.b = (byte) 1;
                this.c = null;
                this.f = -1L;
                this.d = 0L;
                this.e = 0L;
            }

            @Override // java.lang.Comparable
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compareTo(a aVar) {
                return Long.compare(b(), aVar.b());
            }
        }
    }

    public static class j {
        private String a;
        private Object b;
        private long c;
        private byte[] d;

        public j(String str, Object obj, long j) {
            this.a = str;
            this.b = obj;
            this.c = j;
        }

        public String a() {
            return this.a;
        }

        public Object b() {
            return this.b;
        }

        public Object c() {
            return this.b;
        }

        public long d() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte[] f() {
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(byte[] bArr) {
            this.d = bArr;
        }

        public boolean e() {
            return d() != 0 && d() <= System.currentTimeMillis();
        }
    }

    public static class e<T> {
        private String a;

        public e(String str) {
            this.a = str;
        }

        public String a() {
            return this.a;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T a(Object obj) {
            return obj;
        }
    }

    public static class NoValidDataException extends Exception {
        public NoValidDataException() {
            this(w.b("019[dfcjhecc?cfFchcbhecb9chcShedecjcfFdOcb"));
        }

        public NoValidDataException(String str) {
            super(str);
        }
    }

    private static final class a<T> {
        private String a;
        private T b;

        public a(String str, T t) {
            this.a = str;
            this.b = t;
        }

        public T a() {
            return this.b;
        }

        public HashMap<Byte, Object> b() {
            HashMap<Byte, Object> map = new HashMap<>();
            map.put((byte) 0, this.a);
            map.put((byte) 1, this.b);
            return map;
        }

        public static <T> a<T> a(HashMap<Byte, Object> map) {
            if (map != null) {
                return new a<>((String) map.get((byte) 0), map.get((byte) 1));
            }
            return null;
        }
    }

    private static final class KVEntry<T> implements EverythingKeeper, Serializable {
        private static final long serialVersionUID = -1538971823189206429L;
        private String key;
        private T value;

        public KVEntry(String str, T t) {
            this.key = str;
            this.value = t;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String str) {
            this.key = str;
        }

        public T getValue() {
            return this.value;
        }

        public void setValue(T t) {
            this.value = t;
        }
    }

    public static final class b<T extends Parcelable> {
        private Class<T> a;
        private byte[] b;

        public b(Parcelable parcelable) {
            this.a = (Class<T>) parcelable.getClass();
            this.b = b(parcelable);
        }

        public b(Class<T> cls, byte[] bArr) {
            this.a = cls;
            this.b = bArr;
        }

        public Class a() {
            return this.a;
        }

        public T a(T t) {
            return (T) a(this.b, this.a, t);
        }

        public HashMap<Byte, Object> b() {
            HashMap<Byte, Object> map = new HashMap<>();
            map.put((byte) 0, this.a);
            map.put((byte) 1, this.b);
            return map;
        }

        public static <T extends Parcelable> b<T> a(HashMap<Byte, Object> map) {
            if (map != null) {
                return new b<>((Class) map.get((byte) 0), (byte[]) map.get((byte) 1));
            }
            return null;
        }

        private byte[] b(Parcelable parcelable) {
            if (parcelable != null) {
                Parcel parcelObtain = Parcel.obtain();
                parcelable.writeToParcel(parcelObtain, 0);
                return parcelObtain.marshall();
            }
            return new byte[0];
        }

        private T a(byte[] bArr, Class<T> cls, T t) {
            if (bArr != null && bArr.length != 0) {
                try {
                    Parcel parcelObtain = Parcel.obtain();
                    parcelObtain.unmarshall(bArr, 0, bArr.length);
                    parcelObtain.setDataPosition(0);
                    return (T) ((Parcelable.Creator) cls.getDeclaredField(w.b("007Jdcfifhecebfgfi")).get(null)).createFromParcel(parcelObtain);
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                    return t;
                }
            }
            return t;
        }
    }

    public static final class SerializableParcel<T extends Parcelable> implements EverythingKeeper, Serializable {
        private static final long serialVersionUID = -2769878423373647357L;
        private Class<T> clazz;
        private byte[] data;

        public SerializableParcel(Parcelable parcelable) {
            this.clazz = (Class<T>) parcelable.getClass();
            this.data = parcelable2Byte(parcelable);
        }

        public Class getClazz() {
            return this.clazz;
        }

        private void setClazz(Class cls) {
            this.clazz = cls;
        }

        public byte[] getData() {
            return this.data;
        }

        public T getParcel(T t) {
            return (T) byte2Parcelable(this.data, this.clazz, t);
        }

        private void setData(byte[] bArr) {
            this.data = bArr;
        }

        private byte[] parcelable2Byte(Parcelable parcelable) {
            if (parcelable != null) {
                Parcel parcelObtain = Parcel.obtain();
                parcelable.writeToParcel(parcelObtain, 0);
                return parcelObtain.marshall();
            }
            return new byte[0];
        }

        private T byte2Parcelable(byte[] bArr, Class<T> cls, T t) {
            if (bArr != null && bArr.length != 0) {
                try {
                    Parcel parcelObtain = Parcel.obtain();
                    parcelObtain.unmarshall(bArr, 0, bArr.length);
                    parcelObtain.setDataPosition(0);
                    return (T) ((Parcelable.Creator) cls.getDeclaredField(w.b("007_dcfifhecebfgfi")).get(null)).createFromParcel(parcelObtain);
                } catch (Throwable th) {
                    FlyLog.getInstance().d(th);
                    return t;
                }
            }
            return t;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Throwable th, String str) {
        a(th, true, str);
    }

    private static void a(Throwable th, boolean z, String str) {
        if (z) {
            String str2 = "[MPF][" + h + "]";
            if (str != null) {
                str2 = str2 + "[" + str + "]";
            }
            FlyLog.getInstance().d(th, str2, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(String str, String str2) {
        a(str, true, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(String str, String str2) {
        a(str, false, str2);
    }

    private static void a(String str, boolean z, String str2) {
        if (z) {
            String str3 = "[MPF][" + h + "]";
            if (str2 != null) {
                str3 = str3 + "[" + str2 + "]";
            }
            FlyLog.getInstance().d(str3 + str, new Object[0]);
        }
    }

    private static final class f {
        private byte[] a;
        private final boolean b;

        private f(String str) {
            if (TextUtils.isEmpty(str)) {
                this.b = false;
                return;
            }
            this.b = true;
            try {
                this.a = str.getBytes("utf-8");
            } catch (Throwable th) {
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Object a(byte[] bArr, Object obj) {
            try {
                return a(bArr);
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return obj;
            }
        }

        private Object a(byte[] bArr) throws Throwable {
            if (bArr != null && bArr.length != 0) {
                if (this.b) {
                    if (bArr.length % 16 == 0) {
                        try {
                            return b(Data.paddingDecode(this.a, bArr));
                        } catch (Throwable th) {
                            FlyPersistence.d("decode fail ", "ENCIPER");
                            return b(bArr);
                        }
                    }
                    return b(bArr);
                }
                return b(bArr);
            }
            return null;
        }

        private static Object b(byte[] bArr) throws Throwable {
            ByteArrayInputStream byteArrayInputStream;
            Throwable th;
            ObjectInputStream objectInputStream;
            try {
                byteArrayInputStream = new ByteArrayInputStream(bArr);
                try {
                    objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    try {
                        Object object = objectInputStream.readObject();
                        C0041r.a(objectInputStream, byteArrayInputStream);
                        return object;
                    } catch (Throwable th2) {
                        th = th2;
                        C0041r.a(objectInputStream, byteArrayInputStream);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    objectInputStream = null;
                }
            } catch (Throwable th4) {
                byteArrayInputStream = null;
                th = th4;
                objectInputStream = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public byte[] a(Object obj) throws Throwable {
            ByteArrayOutputStream byteArrayOutputStream;
            ObjectOutputStream objectOutputStream;
            if (obj != null) {
                ObjectOutputStream objectOutputStream2 = null;
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    byteArrayOutputStream = null;
                }
                try {
                    objectOutputStream.writeObject(obj);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    if (this.b) {
                        byte[] bArrAES128Encode = Data.AES128Encode(this.a, byteArray);
                        C0041r.a(objectOutputStream, byteArrayOutputStream);
                        return bArrAES128Encode;
                    }
                    C0041r.a(objectOutputStream, byteArrayOutputStream);
                    return byteArray;
                } catch (Throwable th3) {
                    th = th3;
                    objectOutputStream2 = objectOutputStream;
                    C0041r.a(objectOutputStream2, byteArrayOutputStream);
                    throw th;
                }
            }
            return new byte[0];
        }
    }

    private static final class h {
        private final int a;
        private volatile LinkedHashMap<g, c> b;

        private h(int i) {
            this.a = i;
            this.b = new LinkedHashMap<g, c>(i, 0.75f, true) { // from class: cn.fly.tools.utils.FlyPersistence.h.1
                @Override // java.util.LinkedHashMap
                protected boolean removeEldestEntry(Map.Entry<g, c> entry) {
                    return size() > h.this.a;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public c a(g gVar) {
            return this.b.get(gVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(g gVar, c cVar) {
            this.b.put(gVar, cVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(g gVar) {
            this.b.remove(gVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            this.b.clear();
        }
    }

    private static final class g {
        private final byte[] a;

        public g(byte[] bArr) {
            this.a = bArr;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Arrays.equals(this.a, ((g) obj).a);
        }

        public int hashCode() {
            return Arrays.hashCode(this.a);
        }
    }

    private static final class c {
        private long a;
        private Object b;

        private c(long j, Object obj) {
            this.a = j;
            this.b = obj;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a() {
            return this.a != 0 && this.a <= System.currentTimeMillis();
        }
    }
}