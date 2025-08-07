package cn.fly.tools.network;

import cn.fly.tools.proguard.PublicMemberKeeper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public interface HttpConnection extends PublicMemberKeeper {
    InputStream getErrorStream() throws IOException;

    Map<String, List<String>> getHeaderFields() throws IOException;

    InputStream getInputStream() throws IOException;

    int getResponseCode() throws IOException;
}