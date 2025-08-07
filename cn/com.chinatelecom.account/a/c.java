package cn.com.chinatelecom.account.a;

import android.content.Context;
import android.text.TextUtils;
import cn.com.chinatelecom.account.api.Helper;
import cn.com.chinatelecom.account.api.d.g;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class c extends cn.com.chinatelecom.account.api.c.a {
    private static final String b = c.class.getSimpleName();

    private static void a(Context context, int i) {
        try {
            cn.com.chinatelecom.account.api.d.c.a(context, "key_c_l_l_v", i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a(Context context, String str) {
        int i;
        i = 0;
        switch (str) {
            case "ERROR":
                i = -1;
                break;
            case "OFF":
                i = -2;
                break;
        }
        a(context, i);
    }

    public static void a(Context context, List<String> list) {
        int iB = b(context);
        if (iB == -2) {
            return;
        }
        b(context, list, iB);
    }

    private static int b(Context context) {
        try {
            return cn.com.chinatelecom.account.api.d.c.b(context, "key_c_l_l_v", 0);
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    private static String b(Context context, String str) {
        return a.a(context, g.c(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(Context context, Queue<String> queue) throws UnsupportedEncodingException {
        JSONArray jSONArray = new JSONArray();
        String string = jSONArray.toString();
        if (!queue.isEmpty()) {
            Iterator<String> it = queue.iterator();
            while (it.hasNext()) {
                try {
                    jSONArray.put(new JSONObject(it.next()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (jSONArray.length() <= 0) {
            return "";
        }
        String string2 = jSONArray.toString();
        if (!TextUtils.isEmpty(string2)) {
            try {
                string = URLEncoder.encode(Helper.guulam(context, string2), "UTF-8");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return b(context, string);
    }

    private static void b(final Context context, final List<String> list, final int i) {
        a(new Runnable() { // from class: cn.com.chinatelecom.account.a.c.1
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                try {
                    Queue queueC = c.c(context, list, i);
                    if (queueC.isEmpty()) {
                        return;
                    }
                    String strB = c.b(context, (Queue<String>) queueC);
                    JSONObject jSONObject = null;
                    int i2 = -1;
                    try {
                        if (!TextUtils.isEmpty(strB)) {
                            JSONObject jSONObject2 = new JSONObject(strB);
                            try {
                                i2 = jSONObject2.getInt("code");
                                jSONObject = jSONObject2;
                            } catch (Exception e) {
                                e = e;
                                jSONObject = jSONObject2;
                                e.printStackTrace();
                                if (jSONObject != null) {
                                }
                                c.b(context, (Queue<String>) queueC, i);
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                    if (jSONObject != null || i2 != 0) {
                        c.b(context, (Queue<String>) queueC, i);
                    } else {
                        c.c(context);
                        queueC.clear();
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, Queue<String> queue, int i) throws Throwable {
        String strEneulret;
        JSONObject jSONObject;
        JSONArray jSONArray = new JSONArray();
        if (queue != null && !queue.isEmpty()) {
            Iterator<String> it = queue.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                try {
                    jSONObject = new JSONObject(it.next());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (i != -1 || jSONObject.getInt("rt") != 0) {
                    jSONArray.put(jSONObject);
                    i2++;
                    if (i2 > 10) {
                        break;
                    }
                }
            }
        }
        if (jSONArray.length() > 0) {
            try {
                strEneulret = Helper.eneulret(jSONArray.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
                strEneulret = null;
            }
        } else {
            strEneulret = "";
        }
        if (TextUtils.isEmpty(strEneulret)) {
            return;
        }
        b.a(context, strEneulret);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004c A[Catch: all -> 0x0083, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0014, B:7:0x0021, B:10:0x002f, B:12:0x0035, B:13:0x003c, B:14:0x003f, B:17:0x0046, B:20:0x004c, B:21:0x0050, B:23:0x0056, B:24:0x005c, B:26:0x0069, B:29:0x006e, B:32:0x0077, B:34:0x007d, B:31:0x0074), top: B:45:0x0003, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007d A[Catch: all -> 0x0083, LOOP:2: B:32:0x0077->B:34:0x007d, LOOP_END, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0014, B:7:0x0021, B:10:0x002f, B:12:0x0035, B:13:0x003c, B:14:0x003f, B:17:0x0046, B:20:0x004c, B:21:0x0050, B:23:0x0056, B:24:0x005c, B:26:0x0069, B:29:0x006e, B:32:0x0077, B:34:0x007d, B:31:0x0074), top: B:45:0x0003, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static synchronized Queue<String> c(Context context, List<String> list, int i) {
        ConcurrentLinkedQueue concurrentLinkedQueue;
        concurrentLinkedQueue = new ConcurrentLinkedQueue();
        String strA = b.a(context);
        if (TextUtils.isEmpty(strA)) {
            if (i != -1) {
            }
            while (concurrentLinkedQueue.size() > 10) {
            }
        } else {
            try {
                JSONArray jSONArray = new JSONArray(new String(Helper.dneulret(cn.com.chinatelecom.account.api.a.c.a(strA))));
                int length = jSONArray.length();
                for (int i2 = 0; i2 < length && i2 <= 10; i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    if (jSONObject != null) {
                        concurrentLinkedQueue.add(jSONObject.toString());
                    }
                }
                b.a(context, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i != -1) {
                for (String str : list) {
                    try {
                        if (new JSONObject(str).getInt("rt") != 0) {
                            concurrentLinkedQueue.add(str);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } else if (i == 0) {
                concurrentLinkedQueue.addAll(list);
            }
            while (concurrentLinkedQueue.size() > 10) {
                concurrentLinkedQueue.poll();
            }
        }
        return concurrentLinkedQueue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context) throws Throwable {
        b.a(context, "");
    }
}