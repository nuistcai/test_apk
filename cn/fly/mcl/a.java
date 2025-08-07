package cn.fly.mcl;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import cn.fly.mcl.b.b;
import cn.fly.tools.network.HttpConnection;
import cn.fly.tools.network.HttpResponseCallback;
import cn.fly.tools.utils.ActivityTracker;
import cn.fly.tools.utils.HashonHelper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class a {
    public static HttpConnection a(final b bVar) {
        return new HttpConnection() { // from class: cn.fly.mcl.Tmpc$1
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
        };
    }

    public static HttpResponseCallback a(String str, final cn.fly.apc.a aVar) {
        return new HttpResponseCallback() { // from class: cn.fly.mcl.Tmpc$2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // cn.fly.tools.network.HttpResponseCallback
            public void onResponse(HttpConnection httpConnection) throws Throwable {
                if (httpConnection instanceof b) {
                    Bundle bundle = new Bundle();
                    String str2 = cn.fly.mcl.a.a.a;
                    new HashonHelper();
                    bundle.putString(str2, HashonHelper.fromHashMap(((b) httpConnection).a()));
                    aVar.e = bundle;
                }
            }
        };
    }

    public static ActivityTracker.Tracker a(final C0009a c0009a) {
        return new ActivityTracker.Tracker() { // from class: cn.fly.mcl.Tmpc$3
            private long b;
            private String c;

            @Override // cn.fly.tools.utils.ActivityTracker.Tracker
            public void onCreated(Activity activity, Bundle bundle) {
            }

            @Override // cn.fly.tools.utils.ActivityTracker.Tracker
            public void onStarted(Activity activity) {
            }

            @Override // cn.fly.tools.utils.ActivityTracker.Tracker
            public void onResumed(Activity activity) {
                try {
                    if (this.b == 0) {
                        this.b = SystemClock.elapsedRealtime();
                        c0009a.a();
                    }
                    this.c = activity == null ? null : activity.toString();
                } catch (Throwable th) {
                }
            }

            @Override // cn.fly.tools.utils.ActivityTracker.Tracker
            public void onPaused(Activity activity) {
            }

            @Override // cn.fly.tools.utils.ActivityTracker.Tracker
            public void onStopped(Activity activity) {
                try {
                    if (this.c != null) {
                        if (!this.c.equals(activity == null ? null : activity.toString())) {
                            return;
                        }
                    }
                    this.b = 0L;
                    this.c = null;
                    c0009a.b();
                } catch (Throwable th) {
                }
            }

            @Override // cn.fly.tools.utils.ActivityTracker.Tracker
            public void onDestroyed(Activity activity) {
            }

            @Override // cn.fly.tools.utils.ActivityTracker.Tracker
            public void onSaveInstanceState(Activity activity, Bundle bundle) {
            }
        };
    }

    /* renamed from: cn.fly.mcl.a$a, reason: collision with other inner class name */
    public static class C0009a {
        public void a() {
        }

        public void b() {
        }
    }
}