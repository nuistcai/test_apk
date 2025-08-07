package cn.fly.mcl.tcp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class a {
    final Socket a;
    public final d b;
    public SocketAddress c;
    public final AtomicBoolean d = new AtomicBoolean(false);
    final Map<c, Long> e = new WeakHashMap();

    public a(Socket socket, d dVar) {
        this.a = socket;
        this.b = dVar;
        this.d.getAndSet(true);
        dVar.a(this);
        new C0012a("mlp-worker").start();
    }

    public c a(e eVar) {
        c cVar = new c();
        synchronized (this.e) {
            this.e.put(cVar, Long.valueOf(eVar.c));
        }
        try {
            OutputStream outputStream = this.a.getOutputStream();
            outputStream.write(eVar.a());
            outputStream.flush();
            return cVar;
        } catch (Throwable th) {
            this.b.a(this, th);
            return null;
        }
    }

    void a() {
        try {
            InputStream inputStream = this.a.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8096];
            while (true) {
                int i = inputStream.read(bArr);
                if (-1 != i) {
                    byteArrayOutputStream.write(bArr, 0, i);
                    if (i < 8096) {
                        byteArrayOutputStream.flush();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        ByteBuffer byteBufferWrap = ByteBuffer.wrap(byteArray);
                        int iB = 0;
                        while (byteBufferWrap.hasRemaining() && byteBufferWrap.get() != 1) {
                            iB++;
                        }
                        List<e> listA = e.a((ByteBuffer) ((Buffer) new Object[]{byteBufferWrap}[0]).position(iB));
                        Iterator<e> it = listA.iterator();
                        while (it.hasNext()) {
                            iB += it.next().b();
                        }
                        a(listA);
                        byteArrayOutputStream.reset();
                        if (byteArray.length - iB > 0) {
                            byteArrayOutputStream.write(byteArray, iB, byteArray.length - iB);
                        }
                    }
                } else {
                    return;
                }
            }
        } catch (Throwable th) {
            this.b.a(this, th);
            a(true);
        }
    }

    void a(List<e> list) {
        for (e eVar : list) {
            if (this.b != null && eVar.b >= 9001) {
                this.b.a(this, eVar);
            }
            if (eVar.b < 9001) {
                Iterator<Map.Entry<c, Long>> it = this.e.entrySet().iterator();
                while (true) {
                    if (it.hasNext()) {
                        Map.Entry<c, Long> next = it.next();
                        if (next.getValue().equals(Long.valueOf(eVar.c))) {
                            next.getKey().a(eVar);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void a(boolean z) {
        if (!this.d.getAndSet(false)) {
            return;
        }
        try {
            this.a.close();
            this.b.a(this, z);
        } catch (Throwable th) {
        }
        this.d.getAndSet(false);
        this.e.clear();
    }

    /* renamed from: cn.fly.mcl.tcp.a$a, reason: collision with other inner class name */
    class C0012a extends Thread {
        public C0012a(String str) {
            super(str);
            setDaemon(true);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            a.this.a();
        }
    }
}