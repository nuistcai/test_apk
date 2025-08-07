package cn.fly.apc;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import cn.fly.apc.a.f;
import cn.fly.tools.utils.d;
import java.util.HashMap;
import java.util.Set;

/* loaded from: classes.dex */
public class b {
    public static volatile boolean a = false;
    private static Context b;

    public interface a {
        cn.fly.apc.a a(String str, cn.fly.apc.a aVar, long j);
    }

    /* renamed from: cn.fly.apc.b$b, reason: collision with other inner class name */
    public interface InterfaceC0001b {
        HashMap<String, Object> a(int i, String str);

        boolean a(String str);
    }

    public interface c {
        void a(Bundle bundle);
    }

    static {
        f.a().b("APC : 2021.11.07", new Object[0]);
    }

    public static void a(Context context) {
        b = context.getApplicationContext();
    }

    public static Context a() {
        return b;
    }

    public static void a(String str, a aVar) {
        a = true;
        cn.fly.apc.a.c.a().a(str, aVar);
    }

    public static cn.fly.apc.a a(int i, String str, String str2, cn.fly.apc.a aVar, long j) throws Throwable {
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            f.a().b("[sendMessage] not allow main thread to invoke", new Object[0]);
            throw new APCException("not allow main thread to invoke");
        }
        return cn.fly.apc.a.c.a().a(i, str, str2, aVar, j);
    }

    public static void a(d<Set<String>> dVar) {
        cn.fly.apc.a.c.a().a(dVar);
    }

    public static void a(c cVar) {
        cn.fly.apc.a.c.a().a(cVar);
    }

    public static void a(InterfaceC0001b interfaceC0001b) {
        cn.fly.apc.a.c.a().a(interfaceC0001b);
    }
}