package cn.fly.mcl.tcp;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public class f {
    a a;
    final d b;
    final AtomicLong c = new AtomicLong();

    public f(d dVar) {
        this.b = dVar;
    }

    public void a() {
        if (this.a != null) {
            this.a.a(false);
        }
    }

    public void a(SocketAddress socketAddress, boolean z, boolean z2, int i) throws Throwable {
        if (this.a != null) {
            if (!socketAddress.equals(this.a.c)) {
                this.a.a(false);
            } else if (b()) {
                return;
            }
        }
        Socket socket = new Socket();
        socket.setKeepAlive(z);
        socket.setTcpNoDelay(z2);
        socket.connect(socketAddress, i);
        this.a = new a(socket, this.b);
        this.a.c = socketAddress;
    }

    public boolean b() {
        return this.a != null && this.a.d.get();
    }

    public c a(e eVar) {
        if (eVar.c == 0) {
            eVar.c = this.c.incrementAndGet();
        }
        return this.a.a(eVar);
    }
}