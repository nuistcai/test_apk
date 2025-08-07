package cn.fly.tools;

import cn.fly.commons.ab;
import cn.fly.tools.proguard.PublicMemberKeeper;
import cn.fly.tools.utils.i;
import com.tencent.bugly.BuglyStrategy;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class MDP implements PublicMemberKeeper {
    private static Object a = new Object();

    public static Object get(String str, ArrayList<Object> arrayList) {
        return get(str, arrayList, false, 0);
    }

    public static Object get(String str, ArrayList<Object> arrayList, int i) {
        return get(str, arrayList, false, i);
    }

    public static Object get(String str, ArrayList<Object> arrayList, boolean z) {
        return get(str, arrayList, z, 0);
    }

    public static Object get(final String str, final ArrayList<Object> arrayList, boolean z, int i) {
        Object objPoll;
        if (z) {
            return cn.fly.tools.c.a.a(str, arrayList);
        }
        final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        ab.c.execute(new i() { // from class: cn.fly.tools.MDP.1
            @Override // cn.fly.tools.utils.i
            public void a() {
                Object objA = cn.fly.tools.c.a.a(str, arrayList);
                if (objA == null) {
                    objA = MDP.a;
                }
                linkedBlockingQueue.offer(objA);
            }
        });
        try {
            if (i <= 0) {
                objPoll = a(str, linkedBlockingQueue);
            } else {
                objPoll = linkedBlockingQueue.poll(i, TimeUnit.MILLISECONDS);
            }
            if (objPoll == a) {
                return null;
            }
            return objPoll;
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    private static Object a(String str, BlockingQueue blockingQueue) throws InterruptedException {
        int i;
        if ("gia".equals(str) || "gal".equals(str) || "gsl".equals(str) || "giafce".equals(str)) {
            i = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
        } else if (!"glctn".equals(str)) {
            i = 3000;
        } else {
            i = 60000;
        }
        return blockingQueue.poll(i, TimeUnit.MILLISECONDS);
    }
}