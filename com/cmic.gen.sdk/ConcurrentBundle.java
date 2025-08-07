package com.cmic.gen.sdk;

import com.cmic.gen.sdk.a.UmcConfigBean;
import com.cmic.gen.sdk.d.LogBean;
import com.cmic.gen.sdk.e.LogUtils;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ConcurrentBundle.java */
/* renamed from: com.cmic.gen.sdk.a, reason: use source file name */
/* loaded from: classes.dex */
public class ConcurrentBundle {
    private ConcurrentHashMap<String, Object> a;

    public ConcurrentBundle(int i) {
        this.a = new ConcurrentHashMap<>(i);
    }

    public void a(String str, byte[] bArr) {
        if (str != null && bArr != null) {
            this.a.put(str, bArr);
        }
    }

    public byte[] a(String str) {
        if (str != null) {
            return (byte[]) this.a.get(str);
        }
        return null;
    }

    public void a(String str, String str2) {
        if (str != null && str2 != null) {
            this.a.put(str, str2);
        }
    }

    public String b(String str) {
        return b(str, "");
    }

    public String b(String str, String str2) {
        if (str != null && this.a.containsKey(str)) {
            return (String) this.a.get(str);
        }
        return str2;
    }

    public void a(String str, boolean z) {
        if (str != null) {
            this.a.put(str, Boolean.valueOf(z));
        }
    }

    public boolean b(String str, boolean z) {
        if (str != null && this.a.containsKey(str)) {
            return ((Boolean) this.a.get(str)).booleanValue();
        }
        return z;
    }

    public void a(String str, int i) {
        if (str != null) {
            this.a.put(str, Integer.valueOf(i));
        }
    }

    public int c(String str) {
        return b(str, 0);
    }

    public int b(String str, int i) {
        if (str != null && this.a.containsKey(str)) {
            return ((Integer) this.a.get(str)).intValue();
        }
        return i;
    }

    public void a(String str, long j) {
        if (str != null) {
            this.a.put(str, Long.valueOf(j));
        }
    }

    public long b(String str, long j) {
        if (str != null && this.a.containsKey(str)) {
            return ((Long) this.a.get(str)).longValue();
        }
        return j;
    }

    public void a(LogBean logBean) {
        if (logBean != null) {
            this.a.put("logBean", logBean);
        }
    }

    public LogBean a() {
        LogBean logBean = (LogBean) this.a.get("logBean");
        if (logBean != null) {
            return logBean;
        }
        return new LogBean();
    }

    public void a(UmcConfigBean umcConfigBean) {
        if (umcConfigBean != null) {
            this.a.put("current_config", umcConfigBean);
        }
    }

    public UmcConfigBean b() {
        UmcConfigBean umcConfigBean = (UmcConfigBean) this.a.get("current_config");
        if (umcConfigBean != null) {
            return umcConfigBean;
        }
        LogUtils.a("UmcConfigBean为空", "请核查");
        return new UmcConfigBean.a().a();
    }
}