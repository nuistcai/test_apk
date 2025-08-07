package com.cmic.gen.sdk.c;

import android.net.Network;
import android.net.SSLCertificateSocketFactory;
import android.os.Build;
import com.cmic.gen.sdk.ConcurrentBundle;
import com.cmic.gen.sdk.e.LogUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/* compiled from: TlsSniSocketFactory.java */
/* renamed from: com.cmic.gen.sdk.c.d, reason: use source file name */
/* loaded from: classes.dex */
public class TlsSniSocketFactory extends CMICTlsSocketFactory {
    private static final String[] b = {"TLSv1.2"};
    private final HttpsURLConnection d;
    private final Network e;
    private final ConcurrentBundle f;
    private final String c = TlsSniSocketFactory.class.getSimpleName();
    HostnameVerifier a = HttpsURLConnection.getDefaultHostnameVerifier();

    public TlsSniSocketFactory(HttpsURLConnection httpsURLConnection, Network network, ConcurrentBundle concurrentBundle) {
        this.d = httpsURLConnection;
        this.e = network;
        this.f = concurrentBundle;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket plainSocket, String host, int port, boolean autoClose) throws IOException {
        String requestProperty = this.d.getRequestProperty("Host");
        if (requestProperty == null) {
            requestProperty = host;
        }
        LogUtils.b(this.c, "customized createSocket. host: " + requestProperty);
        LogUtils.b(this.c, "plainSocket localAddress: " + plainSocket.getLocalAddress().getHostAddress());
        if (autoClose) {
            LogUtils.b(this.c, "plainSocket close");
            plainSocket.close();
        }
        SSLCertificateSocketFactory sSLCertificateSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
        SSLSocket sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket();
        if (this.e != null && Build.VERSION.SDK_INT >= 21) {
            this.e.bindSocket(sSLSocket);
        }
        sSLSocket.connect(plainSocket.getRemoteSocketAddress());
        this.f.a("socketip", sSLSocket.getLocalAddress().getHostAddress());
        sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        if (Build.VERSION.SDK_INT < 20) {
            LogUtils.b(this.c, "5.0以下启动tls 1.2");
            sSLSocket.setEnabledProtocols(b);
            sSLSocket.setEnabledCipherSuites(new String[]{"TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA"});
        }
        LogUtils.b(this.c, "Setting SNI hostname");
        sSLCertificateSocketFactory.setHostname(sSLSocket, requestProperty);
        SSLSession session = sSLSocket.getSession();
        if (!this.a.verify(requestProperty, session)) {
            throw new SSLPeerUnverifiedException("Cannot verify hostname: " + requestProperty);
        }
        LogUtils.b(this.c, "Established " + session.getProtocol() + " connection with " + session.getPeerHost() + " using " + session.getCipherSuite());
        return sSLSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String host, int port) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return null;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return null;
    }
}