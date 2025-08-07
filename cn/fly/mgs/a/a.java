package cn.fly.mgs.a;

import cn.fly.FlySDK;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class a {
    private static a a = new a();
    private ExecutorService b = Executors.newSingleThreadExecutor();
    private ExecutorService c = Executors.newSingleThreadExecutor();
    private ConcurrentHashMap<String, LinkedBlockingQueue<Boolean>> d = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, String> e = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, SelectionKey> f = new ConcurrentHashMap<>();
    private Socket g = null;
    private int h = 5;
    private ScheduledExecutorService i = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture j;

    static /* synthetic */ int g(a aVar) {
        int i = aVar.h;
        aVar.h = i - 1;
        return i;
    }

    public static a a() {
        return a;
    }

    private a() {
    }

    public void a(final BlockingQueue<Boolean> blockingQueue) {
        this.b.execute(new Runnable() { // from class: cn.fly.mgs.a.a.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ServerSocketChannel serverSocketChannelOpen = ServerSocketChannel.open();
                    serverSocketChannelOpen.configureBlocking(false);
                    try {
                        serverSocketChannelOpen.socket().bind(new InetSocketAddress(59898));
                        e.a().a("[GdCon] registerServerSocket success");
                        blockingQueue.offer(true);
                        Selector selectorOpen = Selector.open();
                        serverSocketChannelOpen.register(selectorOpen, 16);
                        while (selectorOpen != null && selectorOpen.isOpen()) {
                            if (selectorOpen.select() > 0) {
                                Iterator<SelectionKey> it = selectorOpen.selectedKeys().iterator();
                                while (it.hasNext()) {
                                    SelectionKey next = it.next();
                                    it.remove();
                                    if (next.isValid() && next.isAcceptable()) {
                                        SocketChannel socketChannelAccept = ((ServerSocketChannel) next.channel()).accept();
                                        socketChannelAccept.configureBlocking(false);
                                        socketChannelAccept.register(selectorOpen, 1);
                                    }
                                    if (next.isValid() && next.isReadable()) {
                                        SocketChannel socketChannel = (SocketChannel) next.channel();
                                        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(1024);
                                        int i = socketChannel.read(byteBufferAllocate);
                                        e.a().a("[GdCon] serverSocket received bytes:" + i);
                                        if (i > 0) {
                                            String str = new String(byteBufferAllocate.array(), 0, i);
                                            e.a().a("[GdCon] serverSocket received msg:" + str);
                                            if (!"p".equals(str)) {
                                                if (str.startsWith("lg_")) {
                                                    try {
                                                        int port = ((InetSocketAddress) socketChannel.socket().getRemoteSocketAddress()).getPort();
                                                        String strSubstring = str.substring("lg_".length());
                                                        a.this.e.put(Integer.valueOf(port), strSubstring);
                                                        a.this.f.put(Integer.valueOf(port), next);
                                                        c.a().b(strSubstring);
                                                    } catch (Throwable th) {
                                                        e.a().a(th);
                                                    }
                                                } else if (str.startsWith("chk_cb_")) {
                                                    a.this.a(str.substring("chk_cb_".length()));
                                                }
                                            }
                                        } else {
                                            try {
                                                int port2 = ((InetSocketAddress) socketChannel.socket().getRemoteSocketAddress()).getPort();
                                                String str2 = (String) a.this.e.remove(Integer.valueOf(port2));
                                                a.this.f.remove(Integer.valueOf(port2));
                                                e.a().a("[GdCon] serverSocket received client disconnect pkg: " + str2);
                                                c.a().a(str2, false);
                                            } catch (Throwable th2) {
                                                e.a().a(th2);
                                            }
                                            try {
                                                socketChannel.close();
                                            } catch (Throwable th3) {
                                                e.a().a(th3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Throwable th4) {
                        e.a().a("[GdCon] registerServerSocket failed");
                        blockingQueue.offer(false);
                    }
                } catch (Throwable th5) {
                    e.a().a("[GdCon] serverSocket exception: " + th5.getMessage());
                    e.a().b(th5);
                    a.this.c();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        try {
            this.d.clear();
            this.e.clear();
            this.f.clear();
        } catch (Throwable th) {
            e.a().b(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        LinkedBlockingQueue<Boolean> linkedBlockingQueueRemove = this.d.remove(str);
        if (linkedBlockingQueueRemove != null) {
            linkedBlockingQueueRemove.offer(true);
        }
    }

    public int a(String str, LinkedBlockingQueue linkedBlockingQueue) {
        int i = 0;
        for (Map.Entry<Integer, String> entry : this.e.entrySet()) {
            if (entry.getValue().equals(str) && entry.getKey() != null) {
                this.d.put(str, linkedBlockingQueue);
                SelectionKey selectionKey = this.f.get(entry.getKey());
                if (selectionKey != null) {
                    if (a(selectionKey)) {
                        i = 1;
                    } else {
                        i = 2;
                    }
                }
            }
        }
        return i;
    }

    private boolean a(SelectionKey selectionKey) {
        try {
            if (selectionKey.isValid()) {
                ((SocketChannel) selectionKey.channel()).write(ByteBuffer.wrap("chk".getBytes("utf-8")));
                return true;
            }
            return false;
        } catch (Throwable th) {
            e.a().a(th);
            return false;
        }
    }

    public void b() {
        this.b.execute(new Runnable() { // from class: cn.fly.mgs.a.a.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    DH.requester(FlySDK.getContext()).getIPAddressStrict().request(new DH.DHResponder() { // from class: cn.fly.mgs.a.a.2.1
                        @Override // cn.fly.tools.utils.DH.DHResponder
                        public void onResponse(DH.DHResponse dHResponse) throws Throwable {
                            try {
                                if (a.this.g != null) {
                                    a.this.g.close();
                                    a.this.g = null;
                                }
                                a.this.g = new Socket(dHResponse.getIPAddressStrict(), 59898);
                                if (a.this.g.isConnected()) {
                                    a.this.h = 5;
                                    e.a().a("[GdCon] clientSocket connected");
                                    try {
                                        String packageName = FlySDK.getContext().getPackageName();
                                        OutputStream outputStream = a.this.g.getOutputStream();
                                        outputStream.write(("lg_" + packageName).getBytes("utf-8"));
                                        outputStream.flush();
                                    } catch (Throwable th) {
                                        e.a().b(th);
                                    }
                                    a.this.d();
                                    InputStream inputStream = a.this.g.getInputStream();
                                    while (a.this.g.isConnected() && !a.this.g.isClosed()) {
                                        byte[] bArr = new byte[1024];
                                        int i = inputStream.read(bArr);
                                        if (i == -1) {
                                            e.a().a("[GdCon] client received server disconnect");
                                            a.this.a(false);
                                        } else {
                                            String str = new String(bArr, 0, i);
                                            e.a().a("[GdCon] client received server msg: " + str);
                                            if ("chk".equals(str)) {
                                                try {
                                                    String packageName2 = FlySDK.getContext().getPackageName();
                                                    OutputStream outputStream2 = a.this.g.getOutputStream();
                                                    outputStream2.write(("chk_cb_" + packageName2).getBytes("utf-8"));
                                                    outputStream2.flush();
                                                    e.a().a("[GdCon] client send alive check msg callback to server: chk_cb_" + packageName2);
                                                } catch (Throwable th2) {
                                                    e.a().b(th2);
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (SocketException e) {
                                e.a().a("[GdCon] client received socket exception: " + e.getMessage());
                                e.a().a(e);
                                a.this.a(true);
                            }
                        }
                    });
                } catch (Throwable th) {
                    e.a().a("[GdCon] clientSocket exception: " + th.getMessage());
                    e.a().a(th);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final boolean z) {
        e.a().a("[GdCon] onServerDisconnect maxRegisterClientFailedCount: " + this.h + ", isConnectException: " + z);
        f();
        this.c.execute(new Runnable() { // from class: cn.fly.mgs.a.a.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (c.a().c()) {
                        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
                        a.this.a(linkedBlockingQueue);
                        try {
                            if (((Boolean) linkedBlockingQueue.take()).booleanValue()) {
                                c.a().a((String) null, true);
                                return;
                            }
                        } catch (Throwable th) {
                            e.a().a(th);
                        }
                    }
                    if (a.this.h > 0) {
                        if (z && a.this.h < 5) {
                            try {
                                Thread.sleep((5 - a.this.h) * 1000);
                            } catch (Throwable th2) {
                            }
                        }
                        a.g(a.this);
                        a.this.b();
                    }
                } catch (Throwable th3) {
                    e.a().a(th3);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        try {
            e();
            this.j = this.i.scheduleWithFixedDelay(new Runnable() { // from class: cn.fly.mgs.a.a.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        e.a().a("[GdCon] p cli sct: " + a.this.g);
                        if (a.this.g != null && a.this.g.isConnected()) {
                            OutputStream outputStream = a.this.g.getOutputStream();
                            outputStream.write("p".getBytes());
                            outputStream.flush();
                        }
                    } catch (Throwable th) {
                        e.a().a(th);
                    }
                }
            }, 0L, 240L, TimeUnit.SECONDS);
        } catch (Throwable th) {
            e.a().a("[GdCon] HB timer error", th);
        }
    }

    private boolean e() {
        boolean zCancel = false;
        try {
            if (this.j == null) {
                return false;
            }
            zCancel = this.j.cancel(true);
            e.a().a("[GdCon] HB restart, cancel: " + zCancel);
            return zCancel;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return zCancel;
        }
    }

    private void f() {
        try {
            if (this.g != null) {
                e();
                this.g.close();
                this.g = null;
            }
        } catch (Throwable th) {
            e.a().b(th);
        }
    }
}