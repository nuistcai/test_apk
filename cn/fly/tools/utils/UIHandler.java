package cn.fly.tools.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import cn.fly.tools.proguard.PublicMemberKeeper;

/* loaded from: classes.dex */
public class UIHandler implements PublicMemberKeeper {
    private static Handler a;

    private static synchronized void a() {
        if (a == null) {
            b();
        }
    }

    private static void b() {
        a = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: cn.fly.tools.utils.UIHandler.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                UIHandler.b(message);
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Message message) {
        a aVar = (a) message.obj;
        Message message2 = aVar.a;
        Handler.Callback callback = aVar.b;
        if (callback != null) {
            callback.handleMessage(message2);
        }
    }

    private static Message a(Message message, Handler.Callback callback) {
        Message message2 = new Message();
        message2.obj = new a(message, callback);
        return message2;
    }

    private static Message a(int i, Handler.Callback callback) {
        Message message = new Message();
        message.what = i;
        return a(message, callback);
    }

    public static boolean sendEmptyMessage(int i, Handler.Callback callback) {
        a();
        return a.sendMessage(a(i, callback));
    }

    public static boolean sendEmptyMessageDelayed(int i, long j, Handler.Callback callback) {
        a();
        return a.sendMessageDelayed(a(i, callback), j);
    }

    public static boolean sendMessage(Message message, Handler.Callback callback) {
        a();
        return a.sendMessage(a(message, callback));
    }

    public static boolean sendMessageDelayed(Message message, long j, Handler.Callback callback) {
        a();
        return a.sendMessageDelayed(a(message, callback), j);
    }

    private static final class a {
        public final Message a;
        public final Handler.Callback b;

        public a(Message message, Handler.Callback callback) {
            this.a = message;
            this.b = callback;
        }
    }
}