package cn.fly.tools.network;

import cn.fly.tools.proguard.EverythingKeeper;
import cn.fly.tools.utils.ReflectHelper;
import java.io.InputStream;

/* loaded from: classes.dex */
public abstract class HTTPPart implements EverythingKeeper {
    private OnReadListener listener;
    private long offset;

    protected abstract InputStream getInputStream() throws Throwable;

    protected abstract long length() throws Throwable;

    public InputStream toInputStream() throws Throwable {
        return new ByteCounterInputStream(getInputStream());
    }

    public Object getInputStreamEntity() throws Throwable {
        InputStream inputStream = toInputStream();
        long length = length() - this.offset;
        ReflectHelper.importClass("org.apache.http.entity.InputStreamEntity");
        return ReflectHelper.newInstance("InputStreamEntity", inputStream, Long.valueOf(length));
    }

    public void setOffset(long j) {
        this.offset = j;
    }

    public void setOnReadListener(OnReadListener onReadListener) {
        this.listener = onReadListener;
    }
}