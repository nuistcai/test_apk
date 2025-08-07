package com.cmic.gen.sdk.c;

import android.os.Build;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.e.LogUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* compiled from: Tls12SocketFactory.java */
/* renamed from: com.cmic.gen.sdk.c.c, reason: use source file name */
/* loaded from: classes.dex */
public class Tls12SocketFactory extends CMICTlsSocketFactory {
    private static final String[] a = {"TLSv1.2"};
    private final ConcurrentBundle b;

    public Tls12SocketFactory(SSLSocketFactory sSLSocketFactory, ConcurrentBundle concurrentBundle) {
        this.delegate = sSLSocketFactory;
        this.b = concurrentBundle;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.delegate.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.delegate.getSupportedCipherSuites();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket() throws IOException {
        return a(this.delegate.createSocket());
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return a(this.delegate.createSocket(s, host, port, autoClose));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String host, int port) throws IOException {
        return a(this.delegate.createSocket(host, port));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return a(this.delegate.createSocket(host, port, localHost, localPort));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return a(this.delegate.createSocket(host, port));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return a(this.delegate.createSocket(address, port, localAddress, localPort));
    }

    private Socket a(Socket socket) {
        if ((socket instanceof SSLSocket) && Build.VERSION.SDK_INT < 20) {
            LogUtils.b("Tls12SocketFactory", "5.0以下启动tls 1.2");
            SSLSocket sSLSocket = (SSLSocket) socket;
            for (String str : sSLSocket.getEnabledProtocols()) {
                LogUtils.a("enableProtocol", str);
            }
            sSLSocket.setEnabledProtocols(a);
            sSLSocket.setEnabledCipherSuites(new String[]{"TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA"});
        }
        this.b.a("socketip", socket.getLocalAddress().getHostAddress());
        return socket;
    }

    public String toString() {
        return "Tls12SocketFactory";
    }
}