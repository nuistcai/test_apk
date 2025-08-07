package cn.fly.commons.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.core.view.PointerIconCompat;
import cn.fly.commons.C0041r;
import cn.fly.commons.ab;
import cn.fly.commons.d;
import cn.fly.tools.FlyHandlerThread;
import cn.fly.tools.FlyLog;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes.dex */
public class l implements Handler.Callback {
    private static l a = new l();
    private Handler b;

    private l() {
        String str;
        if (TextUtils.isEmpty("M-")) {
            str = null;
        } else {
            str = "M-H-" + a("004[gdidilig");
        }
        this.b = FlyHandlerThread.newHandler(str, this);
    }

    public static l a() {
        return a;
    }

    public Handler b() {
        return this.b;
    }

    public Looper c() {
        if (this.b != null) {
            return this.b.getLooper();
        }
        return null;
    }

    public <T extends c> void a(long j, T t, int i) {
        a(j, t, i, true);
    }

    public <T extends c> void a(long j, T t, int i, boolean z) {
        if (!e()) {
            return;
        }
        int iA = a((l) t);
        if (i == 1) {
            this.b.removeMessages(iA);
        } else if (i == 2 && this.b.hasMessages(iA)) {
            return;
        }
        Message messageObtain = Message.obtain();
        messageObtain.what = iA;
        messageObtain.obj = t;
        if (!z) {
            messageObtain.arg2 = -1;
        }
        a(messageObtain, j * 1000);
    }

    public boolean a(long j, Runnable runnable) {
        return a(PointerIconCompat.TYPE_HELP, j * 1000, runnable);
    }

    public boolean b(long j, Runnable runnable) {
        return b(WebSocketProtocol.CLOSE_NO_STATUS_CODE, j, runnable);
    }

    private boolean e() {
        return this.b != null;
    }

    public void c(long j, Runnable runnable) {
        if (e() && !this.b.hasMessages(PointerIconCompat.TYPE_CROSSHAIR)) {
            b(PointerIconCompat.TYPE_CROSSHAIR, j, runnable);
        }
    }

    public void a(long j, int i, d.b bVar) {
        Message messageObtain = Message.obtain();
        messageObtain.what = PointerIconCompat.TYPE_HAND;
        messageObtain.arg1 = i;
        messageObtain.obj = bVar;
        a(messageObtain, j * 1000);
    }

    public void d() {
        if (e()) {
            this.b.removeMessages(PointerIconCompat.TYPE_HAND);
        }
    }

    private boolean a(int i, long j, Runnable runnable) {
        if (!e()) {
            return true;
        }
        if (this.b.hasMessages(i)) {
            return false;
        }
        b(i, j, runnable);
        return true;
    }

    private boolean b(int i, long j, Runnable runnable) {
        Message messageObtain = Message.obtain();
        messageObtain.what = i;
        messageObtain.obj = runnable;
        a(messageObtain, j);
        return true;
    }

    private <T extends c> int a(T t) {
        int iL = t.l();
        if (iL > 0) {
            return iL + 10000;
        }
        return iL - 10000;
    }

    private void a(Message message, long j) {
        if (e()) {
            if (j > 0) {
                this.b.sendMessageDelayed(message, j);
            } else {
                this.b.sendMessage(message);
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        try {
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
        }
        if (message.arg2 != -1 && !cn.fly.commons.c.d()) {
            Message messageObtain = Message.obtain();
            messageObtain.copyFrom(message);
            a(messageObtain, 60000L);
            return false;
        }
        if (message.what == 1003 || message.what == 1004 || message.what == 1006) {
            Runnable runnable = (Runnable) message.obj;
            if (runnable != null) {
                ab.b.execute(runnable);
            }
        } else if (message.what == 1002) {
            d.b bVar = (d.b) message.obj;
            if (bVar != null) {
                if (!bVar.a) {
                    bVar.a = true;
                }
                ab.b.execute(bVar);
                int i = message.arg1;
                Message messageObtain2 = Message.obtain();
                messageObtain2.what = PointerIconCompat.TYPE_HAND;
                messageObtain2.obj = bVar;
                messageObtain2.arg1 = i;
                a(messageObtain2, i * 1000);
            }
        } else if (message.what == 1005 || message.what == 1007) {
            Runnable runnable2 = (Runnable) message.obj;
            if (runnable2 != null) {
                ab.a.execute(runnable2);
            }
        } else if (message.what >= 10000 || message.what < -10000) {
            c cVar = (c) message.obj;
            if (cVar != null) {
                cVar.i();
            }
        }
        return false;
    }

    public static String a(String str) {
        return C0041r.a(str, 100);
    }
}