package com.cmic.gen.sdk.c;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* compiled from: CMICTlsSocketFactory.java */
/* renamed from: com.cmic.gen.sdk.c.a, reason: use source file name */
/* loaded from: classes.dex */
public abstract class CMICTlsSocketFactory extends SSLSocketFactory {
    protected SSLSocketFactory delegate = HttpsURLConnection.getDefaultSSLSocketFactory();
}