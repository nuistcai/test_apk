package cn.fly.mcl;

import cn.fly.tools.network.HttpConnection;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
class Tmpc$1 implements HttpConnection {
    Tmpc$1() {
    }

    @Override // cn.fly.tools.network.HttpConnection
    public int getResponseCode() throws IOException {
        return bVar.b();
    }

    @Override // cn.fly.tools.network.HttpConnection
    public InputStream getInputStream() throws IOException {
        return bVar.c();
    }

    @Override // cn.fly.tools.network.HttpConnection
    public InputStream getErrorStream() throws IOException {
        return bVar.d();
    }

    @Override // cn.fly.tools.network.HttpConnection
    public Map<String, List<String>> getHeaderFields() throws IOException {
        return bVar.e();
    }
}