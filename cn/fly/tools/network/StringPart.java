package cn.fly.tools.network;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* loaded from: classes.dex */
public class StringPart extends HTTPPart {
    private StringBuilder sb = new StringBuilder();

    public StringPart append(String str) {
        this.sb.append(str);
        return this;
    }

    @Override // cn.fly.tools.network.HTTPPart
    protected InputStream getInputStream() throws Throwable {
        return new ByteArrayInputStream(this.sb.toString().getBytes("utf-8"));
    }

    public String toString() {
        return this.sb.toString();
    }

    @Override // cn.fly.tools.network.HTTPPart
    protected long length() throws Throwable {
        return this.sb.toString().getBytes("utf-8").length;
    }
}